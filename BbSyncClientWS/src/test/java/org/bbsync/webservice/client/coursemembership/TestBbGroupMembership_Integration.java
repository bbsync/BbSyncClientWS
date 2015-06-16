package org.bbsync.webservice.client.coursemembership;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.course.BbCourse;
import org.bbsync.webservice.client.course.BbGroup;
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

public class TestBbGroupMembership_Integration {
	private static final Logger logger = Logger.getLogger(TestBbGroupMembership_Integration.class.getName());
	//Group Information
	private String basic_group_id = null;
	
	//CourseMembership Information
	private String basic_course_membership_id = null;
	
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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		createBasicCourse();
		createBasicUser();
		createBasicCourseMembership();
		createBasicGroup();
	}

	@After
	public void tearDown() throws Exception {
		deleteGroup(basic_group_id);
		deleteCourseMembership(basic_course_id, basic_course_membership_id);
		deleteUser(basic_user_id);
		deleteCourse(basic_course_id);
	}

	@Ignore
    public void testGetServerVersion() {
        BbGroupMembership membership = new BbGroupMembership();
        Long version = membership.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
    
    @Ignore
    public void testInitializeCourseMembershipWS() {
    	BbGroupMembership membership = new BbGroupMembership();
        assertTrue(membership.initializeCourseMembershipWS());
        logger.debug("initialize Course web service succeded.");
    }

	@Test
	public void testPersist() {
		BbGroupMembership bbgm = new BbGroupMembership();
		bbgm.setCourseId(basic_course_id);
		bbgm.setCourseMembershipId(basic_course_membership_id);
		bbgm.setGroupId(basic_group_id);
		String result = (String)bbgm.persist();
		assertNotNull(result);
	}

	@Test
	public void testRetrieve() {
		BbGroupMembership bbgm = new BbGroupMembership();
		bbgm.setCourseId(basic_course_id);
		bbgm.setCourseMembershipId(basic_course_membership_id);
		bbgm.setGroupId(basic_group_id);
		String result = (String)bbgm.persist();
		assertNotNull(result);
		bbgm = new BbGroupMembership();
		bbgm.setCourseId(basic_course_id);
		bbgm.setGroupMembershipId(result);
		BbGroupMembership membership = (BbGroupMembership) bbgm.retrieve();
		assertNotNull(membership);
		assertEquals(result, membership.getGroupMembershipId());
	}

	@Test
	public void testDelete() {
		BbGroupMembership bbgm = new BbGroupMembership();
		bbgm.setCourseId(basic_course_id);
		bbgm.setCourseMembershipId(basic_course_membership_id);
		bbgm.setGroupId(basic_group_id);
		String result = (String)bbgm.persist();
		assertNotNull(result);
		bbgm = new BbGroupMembership();
		bbgm.setCourseId(basic_course_id);
		bbgm.setGroupMembershipId(result);
		assertTrue(bbgm.delete());
	}

	@Test
	public void testDeleteGroupMembership() {
		BbGroupMembership bbgm = new BbGroupMembership();
		bbgm.setCourseId(basic_course_id);
		bbgm.setCourseMembershipId(basic_course_membership_id);
		bbgm.setGroupId(basic_group_id);
		String result = (String)bbgm.persist();
		assertNotNull(result);
		String[] ids = bbgm.deleteGroupMemberships(basic_course_id, new String[]{result});
		assertNotNull(ids);
		assertEquals(1, ids.length);
		assertEquals(result, ids[0]);
	}

	@Test
	public void testGetGroupMembershipByIds() {
		BbGroupMembership bbgm = new BbGroupMembership();
		bbgm.setCourseId(basic_course_id);
		bbgm.setCourseMembershipId(basic_course_membership_id);
		bbgm.setGroupId(basic_group_id);
		String result = (String)bbgm.persist();
		assertNotNull(result);
		BbGroupMembership[] memberships = bbgm.getGroupMembershipsByIds(basic_course_id, new String[]{result});
		assertNotNull(memberships);
		assertEquals(1, memberships.length);
		assertEquals(result, memberships[0].getGroupMembershipId());
	}

	@Test
	public void testGetGroupMembershipByCourseIds() {
		BbGroupMembership bbgm = new BbGroupMembership();
		bbgm.setCourseId(basic_course_id);
		bbgm.setCourseMembershipId(basic_course_membership_id);
		bbgm.setGroupId(basic_group_id);
		String result = (String)bbgm.persist();
		assertNotNull(result);
		BbGroupMembership[] memberships = bbgm.getGroupMembershipsByCourseIds(basic_course_id, new String[]{basic_course_id});
		assertNotNull(memberships);
		assertEquals(1, memberships.length);
		assertEquals(result, memberships[0].getGroupMembershipId());
	}

	@Test
	public void testGetGroupMembershipsByCourseMembershipIds() {
		BbGroupMembership bbgm = new BbGroupMembership();
		bbgm.setCourseId(basic_course_id);
		bbgm.setCourseMembershipId(basic_course_membership_id);
		bbgm.setGroupId(basic_group_id);
		String result = (String)bbgm.persist();
		assertNotNull(result);
		BbGroupMembership[] memberships = bbgm.getGroupMembershipsByCourseMembershipIds(basic_course_id, new String[]{basic_course_membership_id});
		assertNotNull(memberships);
		assertEquals(1, memberships.length);
		assertEquals(result, memberships[0].getGroupMembershipId());
	}

	@Test
	public void testGetGroupMembershipByGroupIds() {
		BbGroupMembership bbgm = new BbGroupMembership();
		bbgm.setCourseId(basic_course_id);
		bbgm.setCourseMembershipId(basic_course_membership_id);
		bbgm.setGroupId(basic_group_id);
		String result = (String)bbgm.persist();
		assertNotNull(result);
		BbGroupMembership[] memberships = bbgm.getGroupMembershipByGroupIds(basic_course_id, new String[]{basic_group_id});
		assertNotNull(memberships);
		assertEquals(1, memberships.length);
		assertEquals(result, memberships[0].getGroupMembershipId());
	}
	
	@Test
	public void testSaveGroupMembershipStringBbGroupMembershipArray() {
		BbGroupMembership bbgm = new BbGroupMembership();
		bbgm.setCourseId(basic_course_id);
		bbgm.setCourseMembershipId(basic_course_membership_id);
		bbgm.setGroupId(basic_group_id);
		String[] results = bbgm.saveGroupMembership(basic_course_id, new BbGroupMembership[]{bbgm});
		assertNotNull(results);
	}
    
	private void createBasicGroup(){
		BbGroup bb_group = new BbGroup();
		bb_group.setCourseId(basic_course_id);
		bb_group.setName("Test Group Name");
		bb_group.setDescription("Test Group Description");
		String id = (String) bb_group.persist();
		assertNotNull(id);
		basic_group_id = id;
		logger.debug("Created Group with ID: " + id);
	}

    private void deleteGroup(String id) {
    	assertNotNull(id);
        BbGroup bb_group = new BbGroup();
        bb_group.setId(id);
        assertTrue(bb_group.delete());
        logger.debug("Deleted Group with ID: " + id);
    }
    
    private void createBasicCourseMembership(){
		BbCourseMembership bbcm = new BbCourseMembership();
		bbcm.setCourseId(basic_course_id);
		bbcm.setUserId(basic_user_id);
		bbcm.setAvailable(Boolean.TRUE);
		bbcm.setRoleId(BbCourseMembershipRole.COURSE_ROLE_STUDENT);
		String id = (String) bbcm.persist();
		assertNotNull(id);
		basic_course_membership_id = id;
		logger.debug("Created CourseMembersip with ID: " + id);
    }
    
    private void deleteCourseMembership(String course_id, String membership_id) {
    	assertNotNull(course_id);
    	assertNotNull(membership_id);
    	BbCourseMembership bbcm = new BbCourseMembership();
        bbcm.setCourseId(course_id);
        bbcm.setId(basic_course_membership_id);
        assertTrue(bbcm.delete());
        logger.debug("Deleted CourseMembership with Course ID: " + course_id + " & User ID:" + membership_id);
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
    	assertNotNull(id);
        BbUser bb_user = new BbUser();
        bb_user.setId(id);
        assertTrue(bb_user.delete());
        logger.debug("Deleted User with ID: " + id);
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
    	assertNotNull(id);
        BbCourse bb_course = new BbCourse();
        bb_course.setId(id);
        assertTrue(bb_course.delete());
        logger.debug("Deleted Course with ID: " + id);
    }    
}
