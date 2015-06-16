package org.bbsync.webservice.client.coursemembership;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.course.BbCourse;
import org.bbsync.webservice.client.coursemembership.BbCourseMembership;
import org.bbsync.webservice.client.coursemembership.BbCourseMembershipRole;
import org.bbsync.webservice.client.user.BbUser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbCourseMembership_Integration {
	private static final Logger logger = Logger.getLogger(TestBbCourseMembership_Integration.class.getName());
	
	private String basic_user_id_1 = null;
	private String basic_user_id_2 = null;
	
	private DateFormat formatDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mm:ssaa");
    private Date start_date = null;
    private Date end_date = null;
    private static final String COURSE_ID = "tst_crs_id";
    private static final String COURSE_BATCH_UID = "tst_crs_batch_uid";
    private static final String COURSE_NAME = "Test Course Name #1";
    private static final String DESCRIPTION = "This is the description for Test Course #1.";
    private String basic_course_id = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		createBasicUser1();
		createBasicCourse();
	}

	@After
	public void tearDown() throws Exception {
		deleteUser(basic_user_id_1);
		if(basic_user_id_2 !=null)deleteUser(basic_user_id_2);
		deleteCourse();
	}

	@Ignore
	public void testGetServerVersion() {
		BbCourseMembership bb_mem = new BbCourseMembership();
		Long version = bb_mem.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("CourseMembership web service version: " + version);
    }
	
	@Ignore
    public void testInitializeCourseMembershipWS(){
		BbCourseMembership bb_mem = new BbCourseMembership();
		boolean result = bb_mem.initializeCourseMembershipWS();
		assertTrue(result);
		logger.debug("initialize CourseMembership web service succeded.");
    }
	
	@Test
	public void testPersist() {
		BbCourseMembership bbcm = new BbCourseMembership();
		bbcm.setCourseId(basic_course_id);
		bbcm.setUserId(basic_user_id_1);
		bbcm.setAvailable(true);
		bbcm.setRoleId(BbCourseMembershipRole.COURSE_ROLE_STUDENT);
		String result = (String)bbcm.persist();
		assertNotNull(result);
	}
	
	@Test
	public void testRetrieve_MembershipId() {
		BbCourseMembership bbcm = new BbCourseMembership();
		bbcm.setCourseId(basic_course_id);
		bbcm.setUserId(basic_user_id_1);
		bbcm.setAvailable(true);
		bbcm.setRoleId(BbCourseMembershipRole.COURSE_ROLE_STUDENT);		
		String result = (String)bbcm.persist();
		assertNotNull(result);
		bbcm = new BbCourseMembership();
		bbcm.setCourseId(basic_course_id);
		bbcm.setId(result);
		BbCourseMembership membership = (BbCourseMembership) bbcm.retrieve();
		assertNotNull(membership);
		assertEquals(result, membership.getId());
	}

	@Test
	public void testRetrieve_UserId() {
		BbCourseMembership bbcm = new BbCourseMembership();
		bbcm.setCourseId(basic_course_id);
		bbcm.setUserId(basic_user_id_1);
		bbcm.setAvailable(true);
		bbcm.setRoleId(BbCourseMembershipRole.COURSE_ROLE_STUDENT);		
		String result = (String)bbcm.persist();
		assertNotNull(result);
		bbcm = new BbCourseMembership();
		bbcm.setCourseId(basic_course_id);
		bbcm.setUserId(basic_user_id_1);
		BbCourseMembership membership = (BbCourseMembership) bbcm.retrieve();
		assertNotNull(membership);
		assertEquals(result, membership.getId());
	}
	
	@Test
	public void testDelete() {
		BbCourseMembership bbcm = new BbCourseMembership();
		bbcm.setCourseId(basic_course_id);
		bbcm.setUserId(basic_user_id_1);
		bbcm.setAvailable(true);
		bbcm.setRoleId(BbCourseMembershipRole.COURSE_ROLE_STUDENT);		
		String result = (String)bbcm.persist();
		assertNotNull(result);
		bbcm = new BbCourseMembership();
		bbcm.setCourseId(basic_course_id);
		bbcm.setId(result);
		assertTrue(bbcm.delete());
	}

	@Test
	public void testGetCourseMembershipsByIds(){
		String cm_id = createBasicCourseMembership_Student();
		BbCourseMembership bbcm = new BbCourseMembership();
		BbCourseMembership[] memberships = bbcm.getCourseMembershipsByIds(basic_course_id, new String[]{cm_id});
		assertNotNull(memberships);
		assertEquals(cm_id, memberships[0].getId());
	}
	
	@Test
	public void testGetCourseMembershipsByCourseIds(){
		createBasicUser2();
		String cm_id = createBasicCourseMembership_Student();
		String cm_id2 = createBasicCourseMembership_Instructor();
		BbCourseMembership bbcm = new BbCourseMembership();
		BbCourseMembership[] memberships = bbcm.getCourseMembershipsByCourseIds(basic_course_id, new String[]{basic_course_id});
		assertNotNull(memberships);
		assertEquals(2, memberships.length);
		for(BbCourseMembership membership:memberships){
			assertEquals(basic_course_id, membership.getCourseId());
			String id = membership.getId();
			assertTrue(id.equals(cm_id) || id.equals(cm_id2));
		}
	}
	
	/**
	 * Although this test is functionally equivalent to 
	 * testGetCourseMembershipsByCourseIdandUserIds(),
	 * If the user invoking the test is a System Administrator, then
	 * basic_course_id could be null.  This would then return all of the Course
	 * (and Organization) Memberships for each of the User IDs provided, not
	 * just the Memberships from the single Course/Organization specified by
	 * basic_course_id
	 */
	@Test
	public void testGetMembershipsByUserIds(){
		createBasicUser2();
		String cm_id1 = createBasicCourseMembership_Student();
		String cm_id2 = createBasicCourseMembership_Instructor();
		BbCourseMembership bbcm = new BbCourseMembership();
		BbCourseMembership[] memberships = bbcm.getCourseMembershipsByUserIds(basic_course_id, new String[]{basic_user_id_1, basic_user_id_2});
		assertEquals(2, memberships.length);
		for(BbCourseMembership membership:memberships){
			assertEquals(basic_course_id, membership.getCourseId());
			String id = membership.getId();
			assertTrue(id.equals(cm_id1) || id.equals(cm_id2));
		}
	}
		
	@Test
	public void testGetCourseMembershipsByCourseIdandUserIds(){
		createBasicUser2();
		String cm_id1 = createBasicCourseMembership_Student();
		String cm_id2 = createBasicCourseMembership_Instructor();
		BbCourseMembership bbcm = new BbCourseMembership();
		BbCourseMembership[] memberships = bbcm.getCourseMembershipsByCourseIdAndUserIds(basic_course_id, new String[]{basic_user_id_1, basic_user_id_2});
		assertEquals(2, memberships.length);
		for(BbCourseMembership membership:memberships){
			assertEquals(basic_course_id, membership.getCourseId());
			String id = membership.getId();
			assertTrue(id.equals(cm_id1) || id.equals(cm_id2));
		}
	}
	
	@Test
	public void testGetCourseMembershipsByCourseIdAndRoleId(){
		createBasicUser2();
		String cm_id1 = createBasicCourseMembership_Student();
		String cm_id2 = createBasicCourseMembership_Instructor();
		BbCourseMembership bbcm = new BbCourseMembership();
		BbCourseMembership[] memberships = bbcm.getCourseMembershipsByCourseIdAndRoleId(basic_course_id, new String[]{BbCourseMembershipRole.COURSE_ROLE_STUDENT});
		assertNotNull(memberships);
		assertEquals(1, memberships.length);
		assertTrue(memberships[0].getId().equals(cm_id1));
		bbcm = new BbCourseMembership();
		memberships = bbcm.getCourseMembershipsByCourseIdAndRoleId(basic_course_id, new String[]{BbCourseMembershipRole.COURSE_ROLE_INSTRUCTOR});
		assertNotNull(memberships);
		assertEquals(1, memberships.length);
		assertTrue(memberships[0].getId().equals(cm_id2));
		bbcm = new BbCourseMembership();
		memberships = bbcm.getCourseMembershipsByCourseIdAndRoleId(basic_course_id, new String[]{BbCourseMembershipRole.COURSE_ROLE_STUDENT, BbCourseMembershipRole.COURSE_ROLE_INSTRUCTOR});
		assertNotNull(memberships);
		assertEquals(2, memberships.length);
		assertTrue(memberships[0].getId().equals(cm_id1) || memberships[0].getId().equals(cm_id2));		
		assertTrue(memberships[1].getId().equals(cm_id1) || memberships[1].getId().equals(cm_id2));
	}
	
	@Test
	public void testSaveCourseMembership(){
		BbCourseMembership bbcm = new BbCourseMembership();
		bbcm.setCourseId(basic_course_id);
		bbcm.setUserId(basic_user_id_1);
		bbcm.setAvailable(true);
		bbcm.setRoleId(BbCourseMembershipRole.COURSE_ROLE_STUDENT);
		String[] result = bbcm.saveCourseMembership(basic_course_id, new BbCourseMembership[]{bbcm});
		assertNotNull(result);
	}

	private String createBasicCourseMembership_Student(){
		BbCourseMembership bbcm = new BbCourseMembership();
		bbcm.setCourseId(basic_course_id);
		bbcm.setUserId(basic_user_id_1);
		bbcm.setAvailable(Boolean.TRUE);
		bbcm.setRoleId(BbCourseMembershipRole.COURSE_ROLE_STUDENT);
		return (String)bbcm.persist();
	}

	private String createBasicCourseMembership_Instructor(){
		BbCourseMembership bbcm = new BbCourseMembership();
		bbcm.setCourseId(basic_course_id);
		bbcm.setUserId(basic_user_id_2);
		bbcm.setAvailable(Boolean.TRUE);
		bbcm.setRoleId(BbCourseMembershipRole.COURSE_ROLE_INSTRUCTOR);
		return (String)bbcm.persist();	
	}

	private void createBasicUser1(){
		BbUser bb_user = new BbUser();
	    bb_user.setGivenName("Johnnie");
	    bb_user.setFamilyName("Walker");
	    bb_user.setName("jw1111111");
	    bb_user.setPassword("password");
	    bb_user.setAvailable(Boolean.TRUE);
	    basic_user_id_1 = (String) bb_user.persist();
	    assertNotNull(basic_user_id_1);
	}
	
	private void createBasicUser2(){
		BbUser bb_user = new BbUser();
	    bb_user.setGivenName("Jack");
	    bb_user.setFamilyName("Daniels");
	    bb_user.setName("jd2222222");
	    bb_user.setPassword("password");
	    bb_user.setAvailable(Boolean.TRUE);
	    basic_user_id_2 = (String) bb_user.persist();
	    assertNotNull(basic_user_id_2);
	}

	private void deleteUser(String bb_user_id){
		BbUser bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		assertTrue(bb_user.delete());
    }
	
	private void createBasicCourse(){
		try {
			start_date = (Date) formatDateTime.parse("10/15/2011 01:01:00AM");
			 end_date = (Date) formatDateTime.parse("10/16/2011 11:59:59PM");
		} catch (ParseException e) {
			logger.error("unable to create basic course");
			fail();
		}
        BbCourse bb_course = new BbCourse();
	    bb_course.setStartDate(start_date.getTime());
	    bb_course.setEndDate(end_date.getTime());
	    bb_course.setCourseId(COURSE_ID);
	    bb_course.setName(COURSE_NAME);
	    bb_course.setDescription(DESCRIPTION);
	    bb_course.setBatchUid(COURSE_BATCH_UID);
	    bb_course.setAvailable(Boolean.TRUE);
	    basic_course_id = (String) bb_course.persist();
	    assertNotNull(basic_course_id);
	}
	
	private void deleteCourse(){
		BbCourse bb_course = new BbCourse();
		bb_course.setId(basic_course_id);
		assertTrue(bb_course.delete());
    }
}
