package org.bbsync.webservice.client.course;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbCourseCategoryMembership_Integration {
	private static final Logger logger = Logger.getLogger(TestBbCourseCategoryMembership_Integration.class.getName());
	
	private String course_category_membership_id = null;
	//Category Information
	private String basic_category_id = null;
	private static final String CATEGORY_TITLE = "Test Category #1";
	private static final String CATEGORY_BATCH_UID = "test_cat_1";
	//Organization Information
	private String basic_course_id = null;
	private static final String COURSE_ID = "tst_crs_1";
	private static final String COURSE_NAME = "Test Course #1";


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		createBasicCourse();
		createBasicCourseCategory();
	}

	@After
	public void tearDown() throws Exception {
		deleteCourseCategory(basic_category_id);
		deleteCourse(basic_course_id);
	}

    @Ignore
    public void testGetServerVersion() {
        BbCourseCategoryMembership bb_ccm = new BbCourseCategoryMembership();
        Long version = bb_ccm.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
    
    @Ignore
    public void testInitializeCourseWS() {
    	BbCourseCategoryMembership bb_ccm = new BbCourseCategoryMembership();
        assertTrue(bb_ccm.initializeCourseWS());
        logger.debug("initialize Course web service succeded.");
    }

	@Test
	public void testDelete() {
		createCourseCategoryMembership();
		BbCourseCategoryMembership bb_ccm = new BbCourseCategoryMembership();
		bb_ccm.setId(course_category_membership_id);
		assertTrue(bb_ccm.delete());
	}

	@Test
	public void testPersist() {
		BbCourseCategoryMembership bb_ccm = new BbCourseCategoryMembership();
		bb_ccm.setCategoryId(basic_category_id);
		bb_ccm.setCourseId(basic_course_id);
		course_category_membership_id = (String) bb_ccm.persist();
		assertNotNull(course_category_membership_id);
		deleteCourseCategoryMembership(course_category_membership_id);
	}

	@Test
	public void testRetrieve() {
		createCourseCategoryMembership();
		BbCourseCategoryMembership bb_ccm = new BbCourseCategoryMembership();
		bb_ccm.setId(course_category_membership_id);
		bb_ccm = (BbCourseCategoryMembership)bb_ccm.retrieve();
		assertNotNull(bb_ccm);
		assertEquals(course_category_membership_id, bb_ccm.getId());
		assertEquals(basic_category_id, bb_ccm.getCategoryId());
		assertEquals(basic_course_id, bb_ccm.getCourseId());
		deleteCourseCategoryMembership(course_category_membership_id);
	}

	@Test
	public void testGetCourseCategoryMembershipById() {
		createCourseCategoryMembership();
		BbCourseCategoryMembership bb_ccm = new BbCourseCategoryMembership();
		bb_ccm = bb_ccm.getCourseCategoryMembershipById(course_category_membership_id);
		assertNotNull(bb_ccm);
		assertEquals(course_category_membership_id, bb_ccm.getId());
		assertEquals(basic_category_id, bb_ccm.getCategoryId());
		assertEquals(basic_course_id, bb_ccm.getCourseId());
		deleteCourseCategoryMembership(course_category_membership_id);
	}

	@Test
	public void testGetCourseCategoryMembershipsByCourseId() {
		createCourseCategoryMembership();
		BbCourseCategoryMembership bb_ccm = new BbCourseCategoryMembership();
		BbCourseCategoryMembership[] result = bb_ccm.getCourseCategoryMembershipsByCourseId(basic_course_id);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(course_category_membership_id, result[0].getId());
		assertEquals(basic_category_id, result[0].getCategoryId());
		assertEquals(basic_course_id, result[0].getCourseId());
	}
	
	@Test
	public void testDeleteCourseCategoryMembershipBbCourseCategoryMembership() {
		createCourseCategoryMembership();
		BbCourseCategoryMembership bb_ccm = new BbCourseCategoryMembership();
		String[] result = bb_ccm.deleteCourseCategoryMembership(new String[] {course_category_membership_id});
		assertNotNull(result);
		assertEquals(course_category_membership_id, result[0]);
		course_category_membership_id =null; 
	}
	
	@Test
	public void testSaveCourseCategoryMembershipBbCourseCategoryMembership() {
		BbCourseCategoryMembership bb_ccm = new BbCourseCategoryMembership();
		bb_ccm.setCategoryId(basic_category_id);
		bb_ccm.setCourseId(basic_course_id);
		course_category_membership_id = bb_ccm.saveCourseCategoryMembership(bb_ccm);
		assertNotNull(course_category_membership_id);
		deleteCourseCategoryMembership(course_category_membership_id);
	}
	
	private void createCourseCategoryMembership(){
		BbCourseCategoryMembership bb_ccm = new BbCourseCategoryMembership();
		bb_ccm.setCategoryId(basic_category_id);
		bb_ccm.setCourseId(basic_course_id);
		course_category_membership_id = bb_ccm.saveCourseCategoryMembership(bb_ccm);
		assertNotNull(course_category_membership_id);
	}
	
	private void deleteCourseCategoryMembership(String id){
		BbCourseCategoryMembership bb_ccm = new BbCourseCategoryMembership();
		String[] result = bb_ccm.deleteCourseCategoryMembership(new String[]{id});
		assertNotNull(result);
		assertEquals(id, result[0]);
		course_category_membership_id =null; 
	}
	
    private void createBasicCourse(){
    	BbCourse bb_course = new BbCourse();
    	bb_course.setCourseId(COURSE_ID);
    	bb_course.setName(COURSE_NAME);
    	String id = (String) bb_course.persist();
    	assertNotNull(id);
    	basic_course_id = id;
	}
		
	private void deleteCourse(String id){
		BbCourse bb_course = new BbCourse();
		bb_course.setId(id);
		assertTrue(bb_course.delete());
		basic_course_id = null;
	}

    
    private void createBasicCourseCategory(){
		BbCourseCategory bb_category = new BbCourseCategory();
		bb_category.setTitle(CATEGORY_TITLE);
		bb_category.setBatchUid(CATEGORY_BATCH_UID);
		bb_category.setOrganization(true);
		String id = bb_category.saveCourseCategory(bb_category);
		assertNotNull(id);
		basic_category_id = id;
	}
		
	private void deleteCourseCategory(String id){
		BbCourseCategory bb_category = new BbCourseCategory();
		bb_category.setId(id);
		assertTrue(bb_category.delete());
		basic_category_id = null;
	}
}
