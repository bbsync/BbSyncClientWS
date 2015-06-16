package org.bbsync.webservice.client.coursemembership;

import static org.junit.Assert.*;

import org.bbsync.webservice.client.coursemembership.BbGroupMembership;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbGroupMembership {
	private BbGroupMembership bbgm = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bbgm = new BbGroupMembership();
	}

	@After
	public void tearDown() throws Exception {
		bbgm = null;
	}

	@Test
	public void testCourseId() {
		assertNull(bbgm.getCourseId());
		bbgm.setCourseId("course_id");
		assertEquals("course_id", bbgm.getCourseId());

	}

	@Test
	public void testGroupMembershipId() {
		assertNull(bbgm.getGroupMembershipId());
		bbgm.setGroupMembershipId("group_membership_id");
		assertEquals("group_membership_id", bbgm.getGroupMembershipId());
	}

	@Test
	public void testCourseMembershipId() {
		assertNull(bbgm.getCourseMembershipId());
		bbgm.setCourseMembershipId("course_membership_id");
		assertEquals("course_membership_id", bbgm.getCourseMembershipId());
	}

	@Test
	public void testGroupId() {
		assertNull(bbgm.getGroupId());
		bbgm.setGroupId("group_id");
		assertEquals("group_id", bbgm.getGroupId());
	}
	
	@Test
	public void testExpansionData() {
		assertNull(bbgm.getExpansionData());
		bbgm.setExpansionData(new String[]{"expansion_data"});
		assertNotNull(bbgm.getExpansionData());
		assertEquals("expansion_data", bbgm.getExpansionData()[0]);
	}

}
