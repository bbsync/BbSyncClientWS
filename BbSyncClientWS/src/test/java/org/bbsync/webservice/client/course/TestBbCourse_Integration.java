package org.bbsync.webservice.client.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.coursemembership.BbCourseMembership;
import org.bbsync.webservice.client.coursemembership.BbCourseMembershipRole;
import org.bbsync.webservice.client.user.BbUser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbCourse_Integration {
	private static final Logger logger = Logger.getLogger(TestBbCourse_Integration.class.getName());
	private static final String BASIC_COURSE_NAME = "Test Course #1";
	private static final String BASIC_COURSE_ID = "tst_crs_1";
	private static final String BASIC_COURSE_DESCRIPTION = "This is a test course."; //not required to create a Course
	private String course_id = null;
	//Term Information
	private String basic_term_id = null;
	private static final String TERM_NAME = "Test Term #1";
	private static final String TERM_SOURCEDID = "test_term_1";
	//Category Information
	private String basic_category_id = null;
	private static final String CATEGORY_TITLE = "Test Category #1";
	private static final String CATEGORY_BATCH_UID = "test_cat_1";
	//User Information
	private static final String USER_FIRST_NAME = "FIRSTNAME";
	private static final String USER_LAST_NAME = "LASTNAME";
	private static final String USER_USERNAME = "USERNAME";
	private static final String USER_PASSWORD = "PASSWORD";
    private String basic_user_id = null;
    //Course Membership
    private String instructor_membership_id = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		createBasicCourse();
	}

	@After
	public void tearDown() throws Exception {
		deleteCourse(course_id);
	}
	
    @Ignore
    public void testGetServerVersion() {
        BbCourse bb_course = new BbCourse();
        Long version = bb_course.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
    
    @Ignore
    public void testInitializeCourseWS() {
    	BbCourse bb_course = new BbCourse();
        assertTrue(bb_course.initializeCourseWS());
        logger.debug("initialize Course web service succeded.");
    }
    
    @Test
    public void testPersist_new(){
    	 BbCourse bb_course = new BbCourse();
         bb_course.setCourseId("tst_prst_crs");
         bb_course.setName("Test Persist Course");
         String id = (String) bb_course.persist();
         assertNotNull(id);
         deleteCourse(id);
    }
    
    @Test
    public void testPersist_update(){
        BbCourse bb_course = new BbCourse();
        bb_course.setId(course_id);
        bb_course = (BbCourse) bb_course.retrieve();
        assertEquals(BASIC_COURSE_ID,bb_course.getBatchUid());
        bb_course.setBatchUid("tst_batch_uid_1"); //update a populated field
        bb_course.setDescription("This is a test description"); //populate a blank field
        String id = (String) bb_course.persist();
        assertNotNull(id);
        assertEquals(course_id, id);
        //retrieve the updated course & check variables
        bb_course = (BbCourse) bb_course.retrieve();
        assertNotNull(bb_course);
        assertEquals(BASIC_COURSE_ID, bb_course.getCourseId());
        assertEquals(BASIC_COURSE_NAME, bb_course.getName());
        assertEquals("tst_batch_uid_1", bb_course.getBatchUid());
        assertEquals("This is a test description", bb_course.getDescription());
    }
    
    @Test
	public void testRetrieve_id() {
        BbCourse bb_course = new BbCourse();
        bb_course.setId(course_id);
        bb_course = (BbCourse) bb_course.retrieve();
        assertNotNull(bb_course);
        assertEquals(BASIC_COURSE_ID, bb_course.getCourseId());
        assertEquals(BASIC_COURSE_ID, bb_course.getBatchUid());
        assertEquals(BASIC_COURSE_NAME, bb_course.getName());
        assertEquals(course_id, bb_course.getId());
	}

    @Test
	public void testRetrieve_course_id() {
        BbCourse bb_course = new BbCourse();
        bb_course.setCourseId(BASIC_COURSE_ID);
        bb_course = (BbCourse) bb_course.retrieve();
        assertNotNull(bb_course);
        assertEquals(BASIC_COURSE_ID, bb_course.getCourseId());
        assertEquals(BASIC_COURSE_ID, bb_course.getBatchUid());
        assertEquals(BASIC_COURSE_NAME, bb_course.getName());
        assertEquals(course_id, bb_course.getId());
	}
    
    @Test
	public void testRetrieve_batch_uid() {
        BbCourse bb_course = new BbCourse();
        bb_course.setBatchUid(BASIC_COURSE_ID);
        bb_course = (BbCourse) bb_course.retrieve();
        assertNotNull(bb_course);
        assertEquals(BASIC_COURSE_ID, bb_course.getCourseId());
        assertEquals(BASIC_COURSE_ID, bb_course.getBatchUid());
        assertEquals(BASIC_COURSE_NAME, bb_course.getName());
        assertEquals(course_id, bb_course.getId());
	}

    @Test
	public void testDelete() {
		BbCourse bb_course = new BbCourse();
        bb_course.setCourseId("tst_prst_crs");
        bb_course.setName("Test Persist Course");
        String id = (String) bb_course.persist();
        assertNotNull(id);
        bb_course.setId(id); //setId() to use delete()
        assertTrue(bb_course.delete());
	}

	@Test
	public void testChangeCourseBatchUid() {
		BbCourse bb_course = new BbCourse();
		assertTrue(bb_course.changeCourseBatchUid(BASIC_COURSE_ID, "new_batch_uid"));
		bb_course.setId(course_id);
		bb_course = (BbCourse) bb_course.retrieve();
		assertNotNull(bb_course);
		assertEquals("new_batch_uid", bb_course.getBatchUid());
	}

	@Ignore
	public void testChangeCourseDataSourceId() {
		/*
		 * There's currently no way via web services to create & delete 
		 * data sources for testing.  This test will need to be performed
		 * manually.
		 */
		assertTrue(true);
	}
	
	@Test
	public void testcreateCourse() {
		BbCourse bb_course = new BbCourse();
        bb_course.setCourseId("tst_create_crs");
        bb_course.setName("Test Create Course");
        String id = bb_course.createCourse(bb_course);
        assertNotNull(id);
        deleteCourse(id);
	}
	
	@Test
	public void testdeleteCourse() {
		BbCourse bb_course = new BbCourse();
        bb_course.setCourseId("tst_create_crs");
        bb_course.setName("Test Create Course");
        String id = bb_course.createCourse(bb_course);
        assertNotNull(id);
        String[] results = bb_course.deleteCourse(new String[]{id});
        assertNotNull(results);
        assertEquals(id, results[0]);
	}	

	@Test
	public void testAddCourseToTerm() {
		createBasicTerm();
		BbCourse bb_course = new BbCourse();
		assertTrue(bb_course.addCourseToTerm(course_id, basic_term_id));
		deleteTerm(basic_term_id);
	}

	@Test
	public void testGetAllCourses() {
		BbCourse bb_course = new BbCourse();
    	BbCourse[] bb_courses = bb_course.getAllCourses();
    	assertNotNull(bb_courses);
    	assertTrue(bb_courses.length>=1);
	}
	
	@Test
	public void testGetCourseByCourseId() {
		BbCourse bb_course = new BbCourse();
    	bb_course = bb_course.getCourseByCourseId(BASIC_COURSE_ID);
    	assertNotNull(bb_course);
        assertEquals(BASIC_COURSE_ID, bb_course.getCourseId());
        assertEquals(BASIC_COURSE_ID, bb_course.getBatchUid());
        assertEquals(BASIC_COURSE_NAME, bb_course.getName());
        assertEquals(course_id, bb_course.getId());
	}

	@Test
	public void testGetCourseByBatchUid() {
		BbCourse bb_course = new BbCourse();
    	bb_course = bb_course.getCourseByBatchUid(BASIC_COURSE_ID);
    	assertNotNull(bb_course);
        assertEquals(BASIC_COURSE_ID, bb_course.getCourseId());
        assertEquals(BASIC_COURSE_ID, bb_course.getBatchUid());
        assertEquals(BASIC_COURSE_NAME, bb_course.getName());
        assertEquals(course_id, bb_course.getId());
	}

	@Test
	public void testGetCourseById() {
		BbCourse bb_course = new BbCourse();
    	bb_course = bb_course.getCourseById(course_id);
    	assertNotNull(bb_course);
        assertEquals(BASIC_COURSE_ID, bb_course.getCourseId());
        assertEquals(BASIC_COURSE_ID, bb_course.getBatchUid());
        assertEquals(BASIC_COURSE_NAME, bb_course.getName());
        assertEquals(course_id, bb_course.getId());
	}

	@Test
	public void testGetCourseByCategoryId() {
		createBasicCourseCategory();
		BbCourseCategoryMembership bb_ocm = new BbCourseCategoryMembership();
		bb_ocm.setCategoryId(basic_category_id);
		bb_ocm.setCourseId(course_id);
		String id = (String) bb_ocm.persist();
		assertNotNull(id);
		bb_ocm = new BbCourseCategoryMembership();
		bb_ocm.setId(id);
		assertTrue(bb_ocm.delete());
		deleteCourseCategory(basic_category_id);
	}

	@Test
	public void testGetCourseBySearchValue_course_id() {
		BbCourse bb_course = new BbCourse();
		BbCourse[] result = bb_course.getCoursesBySearchValue(BASIC_COURSE_ID, BbCourse.SEARCH_KEY_COURSE_ID);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(course_id, result[0].getId());
		assertEquals(BASIC_COURSE_ID, result[0].getCourseId());
		assertEquals(BASIC_COURSE_NAME, result[0].getName());
	}

	@Test
	public void testGetCourseBySearchValue_course_name() {
		BbCourse bb_course = new BbCourse();
		BbCourse[] result = bb_course.getCoursesBySearchValue(BASIC_COURSE_NAME, BbCourse.SEARCH_KEY_COURSE_NAME);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(course_id, result[0].getId());
		assertEquals(BASIC_COURSE_ID, result[0].getCourseId());
		assertEquals(BASIC_COURSE_NAME, result[0].getName());
	}

	@Test
	public void testGetCourseBySearchValue_description() {
		BbCourse bb_course = new BbCourse();
		BbCourse[] result = bb_course.getCoursesBySearchValue("test course", BbCourse.SEARCH_KEY_COURSE_DESCRIPTION);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(course_id, result[0].getId());
		assertEquals(BASIC_COURSE_ID, result[0].getCourseId());
		assertEquals(BASIC_COURSE_NAME, result[0].getName());
		assertEquals(BASIC_COURSE_DESCRIPTION, result[0].getDescription());
	}

	@Test
	public void testGetCourseBySearchValue_instructor() {
		createBasicUser();
		createInstructorMembership();
		BbCourse bb_course = new BbCourse();
		BbCourse[] result = bb_course.getCoursesBySearchValue(USER_USERNAME, BbCourse.SEARCH_KEY_COURSE_INSTRUCTOR_USERNAME);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(course_id, result[0].getId());
		assertEquals(BASIC_COURSE_ID, result[0].getCourseId());
		assertEquals(BASIC_COURSE_NAME, result[0].getName());
		assertEquals(BASIC_COURSE_DESCRIPTION, result[0].getDescription());
		deleteInstructorMembership();
		deleteUser();
	}

	@Test
	public void testGetCourseBySearchValue_contains() {
		BbCourse bb_course = new BbCourse();
		BbCourse[] result = bb_course.getCoursesBySearchValue("is a test", BbCourse.SEARCH_KEY_COURSE_DESCRIPTION, BbCourse.SEARCH_OPERATOR_CONTAINS);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(course_id, result[0].getId());
		assertEquals(BASIC_COURSE_ID, result[0].getCourseId());
		assertEquals(BASIC_COURSE_NAME, result[0].getName());
		assertEquals(BASIC_COURSE_DESCRIPTION, result[0].getDescription());
	}
	
	@Test
	public void testGetCourseBySearchValue_equals() {
		BbCourse bb_course = new BbCourse();
		BbCourse[] result = bb_course.getCoursesBySearchValue(BASIC_COURSE_DESCRIPTION, BbCourse.SEARCH_KEY_COURSE_DESCRIPTION, BbCourse.SEARCH_OPERATOR_EQUALS);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(course_id, result[0].getId());
		assertEquals(BASIC_COURSE_ID, result[0].getCourseId());
		assertEquals(BASIC_COURSE_NAME, result[0].getName());
		assertEquals(BASIC_COURSE_DESCRIPTION, result[0].getDescription());
	}

	@Test
	public void testGetCourseBySearchValue_is_not_blank() {
		BbCourse bb_course = new BbCourse();
		BbCourse[] result = bb_course.getCoursesBySearchValue(BASIC_COURSE_DESCRIPTION, BbCourse.SEARCH_KEY_COURSE_DESCRIPTION, BbCourse.SEARCH_OPERATOR_IS_NOT_BLANK);
		assertNotNull(result);
		assertTrue(result.length>=1);
	}

	@Test
	public void testGetCourseBySearchValue_starts_with() {
		BbCourse bb_course = new BbCourse();
		BbCourse[] result = bb_course.getCoursesBySearchValue("This is a test", BbCourse.SEARCH_KEY_COURSE_DESCRIPTION, BbCourse.SEARCH_OPERATOR_STARTS_WITH);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(course_id, result[0].getId());
		assertEquals(BASIC_COURSE_ID, result[0].getCourseId());
		assertEquals(BASIC_COURSE_NAME, result[0].getName());
		assertEquals(BASIC_COURSE_DESCRIPTION, result[0].getDescription());
	}

	@Test
	public void testUpdateCourse(){
		//retrieve the test Course 
		BbCourse bb_course = new BbCourse();
		bb_course.setId(course_id);
		bb_course = (BbCourse) bb_course.retrieve();
		assertNotNull(bb_course);
		//check basic values
		assertEquals(course_id, bb_course.getId());
		assertEquals(BASIC_COURSE_ID, bb_course.getCourseId());
		assertEquals(BASIC_COURSE_NAME, bb_course.getName());
		assertEquals(BASIC_COURSE_DESCRIPTION, bb_course.getDescription());
		//update some values
		bb_course.setDescription("This is an updated Description.");
		bb_course.setInstitutionName("institution_name");
		String result = bb_course.updateCourse(bb_course);
		assertEquals(course_id, result);
		//retrieve the test Course 
		bb_course = new BbCourse();
		bb_course.setId(course_id);
		bb_course = (BbCourse) bb_course.retrieve();
		assertNotNull(bb_course);
		//re-check all values
		assertEquals(course_id, bb_course.getId());
		assertEquals(BASIC_COURSE_ID, bb_course.getCourseId());
		assertEquals(BASIC_COURSE_NAME, bb_course.getName());
		assertEquals("This is an updated Description.", bb_course.getDescription());
		assertEquals("institution_name", bb_course.getInstitutionName());
	}
	
	private void deleteCourse(String id) {
        BbCourse bb_course = new BbCourse();
        String[] results = bb_course.deleteCourse(new String[]{id});
        assertNotNull(results);
        assertEquals(id, results[0]);
        if(course_id==id) course_id=null;
        logger.debug("Deleted Course with ID: " + id);
    }
    
    private void createBasicCourse(){
        BbCourse bb_course = new BbCourse();
        bb_course.setCourseId(BASIC_COURSE_ID);
        bb_course.setName(BASIC_COURSE_NAME);
        bb_course.setDescription(BASIC_COURSE_DESCRIPTION); //not required to create a Course
        String id = bb_course.createCourse(bb_course);
        assertNotNull(id);
        course_id = id;
        logger.debug("Created Course with ID: " + id);
    }
    
    private void deleteTerm(String id){
    	BbTerm bb_term = new BbTerm();
    	bb_term.setId(id);
    	assertTrue(bb_term.delete());
    	logger.debug("Deleted Term with ID: " + id);
    }
    
    private void createBasicTerm(){
    	BbTerm bb_term = new BbTerm();
    	bb_term.setName(TERM_NAME);
    	bb_term.setSourcedidId(TERM_SOURCEDID);
    	bb_term.setDuration(BbTerm.DURATION_CONTINUOUS);
    	String id = (String) bb_term.persist();
    	assertNotNull(id);
    	basic_term_id = id;
    	logger.debug("Created Term with ID: " + id);
    }
    
    private void createBasicCourseCategory(){
		BbCourseCategory bb_category = new BbCourseCategory();
		bb_category.setTitle(CATEGORY_TITLE);
		bb_category.setBatchUid(CATEGORY_BATCH_UID);
		bb_category.setOrganization(false);
		String id = bb_category.saveCourseCategory(bb_category);
		assertNotNull(id);
		basic_category_id = id;
	}
		
	private String[] deleteCourseCategory(String id){
		BbCourseCategory bb_category = new BbCourseCategory();
		return bb_category.deleteCourseCategory(new String[]{id});
	}
	
    private void createBasicUser(){
    	BbUser bb_user = new BbUser();
        bb_user.setGivenName(USER_FIRST_NAME);
        bb_user.setFamilyName(USER_LAST_NAME);
        bb_user.setName(USER_USERNAME);
        bb_user.setPassword(USER_PASSWORD);
        String id = (String) bb_user.persist();
        assertNotNull(id);
        basic_user_id = id;
    }
    
    private void deleteUser(){
    	BbUser bb_user = new BbUser();
        bb_user.setId(basic_user_id);
        assertTrue(bb_user.delete());
    }
    
    private void createInstructorMembership(){
		BbCourseMembership bbcm = new BbCourseMembership();
		bbcm.setCourseId(course_id);
		bbcm.setUserId(basic_user_id);
		bbcm.setAvailable(true);
		bbcm.setRoleId(BbCourseMembershipRole.COURSE_ROLE_INSTRUCTOR);		
		String id = (String)bbcm.persist();
		assertNotNull(id);
		instructor_membership_id = id;
    }
    
    private void deleteInstructorMembership(){
    	BbCourseMembership bbcm = new BbCourseMembership();
		bbcm.setCourseId(course_id);
		bbcm.setId(instructor_membership_id);
		assertTrue(bbcm.delete());
    }
}
