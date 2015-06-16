package org.bbsync.webservice.client.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.coursemembership.BbCourseMembership;
import org.bbsync.webservice.client.coursemembership.BbCourseMembershipRole;
import org.bbsync.webservice.client.coursemembership.BbGroupMembership;
import org.bbsync.webservice.client.user.BbUser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbGroup_Integration {
	private static final Logger logger = Logger.getLogger(TestBbGroup_Integration.class.getName());
	//User Information
	private String basic_user_id = null;
	private static final String FIRST_NAME = "Henry";
	private static final String LAST_NAME  = "Jones";
	private static final String USER_NAME  = "hj1234567";
	private static final String PASSWORD   = "ilovearcheology";
	//Course Information
	private String basic_course_id = null;
    private static final String COURSE_ID = "tst_crs_1";
    private static final String COURSE_NAME = "Test Course #1";
	//Course Enrollment 
    private String basic_crs_enroll_id = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		createBasicCourse();
		//createBasicUser();
	}

	@After
	public void tearDown() throws Exception {
		deleteCourse(basic_course_id);
		//deleteUser(basic_user_id);
	}
	
	@Ignore
    public void testGetServerVersion() {
        BbGroup bb_group = new BbGroup();
        Long version = bb_group.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
    
    @Ignore
    public void testInitializeCourseWS() {
    	BbGroup bb_group = new BbGroup();
        assertTrue(bb_group.initializeCourseWS());
        logger.debug("initialize Course web service succeded.");
    }
	
	@Test
	public void testPersist(){
		assertNotNull(basic_course_id);
		BbGroup bb_group = new BbGroup();
		bb_group.setCourseId(basic_course_id);
		bb_group.setName("Test Group Name");
		bb_group.setDescription("Test Group Description");
		String id = (String) bb_group.persist();
		assertNotNull(id);
	}
	
	@Test
	public void testRetrieve(){
		assertNotNull(basic_course_id);
		BbGroup bb_group = new BbGroup();
		bb_group.setCourseId(basic_course_id);
		bb_group.setName("Test Group Name");
		bb_group.setDescription("Test Group Description");
		String id = (String) bb_group.persist();
		assertNotNull(id);
		bb_group = new BbGroup();
		bb_group.setId(id);
		bb_group = (BbGroup) bb_group.retrieve();
		assertNotNull(bb_group);
		assertEquals(id, bb_group.getId());
	}
	
	@Test
	public void testDelete(){
		assertNotNull(basic_course_id);
		BbGroup bb_group = new BbGroup();
		bb_group.setCourseId(basic_course_id);
		bb_group.setName("Test Group Name");
		bb_group.setDescription("Test Group Description");
		String id = (String) bb_group.persist();
		assertNotNull(id);
		bb_group = new BbGroup();
		bb_group.setId(id);
		assertTrue(bb_group.delete());
	}
	
	@Test
	public void testCreateGroup(){
		assertNotNull(basic_course_id);
		BbGroup bb_group = new BbGroup();
		bb_group.setCourseId(basic_course_id);
		bb_group.setName("Test Group Name");
		bb_group.setDescription("Test Group Description");
		bb_group.setDescriptionType(BbGroup.TEXT_TYPE_HTML);
		bb_group.setGroupTools(new String[]{BbGroup.GROUP_TOOL_BLOGS, BbGroup.GROUP_TOOL_EMAIL, BbGroup.GROUP_TOOL_WIKIS});
		bb_group.setVisibleToStudents(true);
		String id = (String) bb_group.persist();
		assertNotNull(id);
	}
	
	@Test
	public void testGetGroupById(){
		assertNotNull(basic_course_id);
		BbGroup bb_group = new BbGroup();
		bb_group.setCourseId(basic_course_id);
		bb_group.setName("Test Group Name");
		bb_group.setDescription("Test Group Description");
		String id = (String) bb_group.persist();
		assertNotNull(id);
		bb_group = new BbGroup();
		bb_group = bb_group.getGroupById(basic_course_id, id);
		assertNotNull(bb_group);
		assertEquals(id, bb_group.getId());
	}
	
	@Test
	public void testGetGroupsByCourseId() {
		assertNotNull(basic_course_id);
		BbGroup group = new BbGroup();
		BbGroup[] groups = group.getGroupsByCourseId(basic_course_id);
		assertNull(groups);
		BbGroup bb_group = new BbGroup();
		bb_group.setCourseId(basic_course_id);
		bb_group.setName("Test Group Name");
		bb_group.setDescription("Test Group Description");
		String id = (String) bb_group.persist();
		assertNotNull(id);
		groups = group.getGroupsByCourseId(basic_course_id);
		assertNotNull(groups);
		assertEquals(1, groups.length);
	}
	
	@Test
	public void testGetGroupsByEnrolledUserId(){
		assertNotNull(basic_course_id);
		createBasicUser();
		assertNotNull(basic_user_id);
		enrollUserInCourse();
		assertNotNull(basic_crs_enroll_id);
		BbGroup bb_group = new BbGroup();
		bb_group.setCourseId(basic_course_id);
		bb_group.setName("Test Group Name");
		bb_group.setDescription("Test Group Description");
		String group_id = (String) bb_group.persist();
		assertNotNull(group_id);
		BbGroupMembership bbgm = new BbGroupMembership();
		bbgm.setCourseId(basic_course_id);
		bbgm.setCourseMembershipId(basic_crs_enroll_id);
		bbgm.setGroupId(group_id);
		String bbgm_id = (String)bbgm.persist();
		assertNotNull(bbgm_id);
		bb_group = new BbGroup();
		BbGroup[] groups = bb_group.getGroupsByEnrolledUserId(basic_course_id, basic_user_id);
		assertNotNull(groups);
		assertEquals(1, groups.length);
		bb_group.setId(group_id);
		assertTrue(bb_group.delete());
		deleteUser(basic_user_id);
	}
	
	
    private void createBasicUser(){
    	BbUser bb_user = new BbUser();
        bb_user.setGivenName(FIRST_NAME);
        bb_user.setFamilyName(LAST_NAME);
        bb_user.setName(USER_NAME);
        bb_user.setPassword(PASSWORD);
        String id = (String) bb_user.persist();
        assertNotNull(id);
        basic_user_id = id;
        logger.debug("Created User with ID: " + id);
    }

    private void deleteUser(String id) {
        BbUser bb_user = new BbUser();
        bb_user.setId(id);
        assertTrue(bb_user.delete());
        logger.debug("Deleted User with ID: " + id);
    }
    
    private void enrollUserInCourse(){
    	assertNotNull(basic_course_id);
    	assertNotNull(basic_user_id);
    	BbCourseMembership bbcm = new BbCourseMembership();
    	bbcm.setCourseId(basic_course_id);
		bbcm.setUserId(basic_user_id);
		bbcm.setAvailable(true);
		bbcm.setRoleId(BbCourseMembershipRole.COURSE_ROLE_STUDENT);
		String id = (String) bbcm.persist();
		assertNotNull(id);
		basic_crs_enroll_id = id;
    }
	
	private void createBasicCourse(){
		BbCourse bb_course = new BbCourse();
        bb_course.setCourseId(COURSE_ID);
        bb_course.setName(COURSE_NAME);
        String id = (String) bb_course.persist();
        assertNotNull(id);
        basic_course_id = id;
        logger.debug("Created Course with ID: " + id);
    }
    
    private void deleteCourse(String id) {
        BbCourse bb_course = new BbCourse();
        bb_course.setId(id);
        assertTrue(bb_course.delete());
        logger.debug("Deleted Course with ID: " + id);
    }    
}
