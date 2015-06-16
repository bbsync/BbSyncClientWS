package org.bbsync.webservice.client.coursemembership;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.bbsync.webservice.client.coursemembership.BbCourseMembershipRole;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbCourseMembershipRole {
	private BbCourseMembershipRole bbmr = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bbmr = new BbCourseMembershipRole();
	}

	@After
	public void tearDown() throws Exception {
		bbmr = null;
	}

	@Test
	public void testRoleIdentifier() {
		assertNull(bbmr.getRoleIdentifier());
		bbmr.setRoleIdentifier(BbCourseMembershipRole.COURSE_ROLE_STUDENT);
		assertNotNull(bbmr.getRoleIdentifier());
		assertEquals(BbCourseMembershipRole.COURSE_ROLE_STUDENT, bbmr.getRoleIdentifier());
	}

	@Test
	public void testCourseRoleDescription() {
		assertNull(bbmr.getCourseRoleDescription());
		bbmr.setCourseRoleDescription("course_role_description");
		assertNotNull(bbmr.getCourseRoleDescription());
		assertEquals("course_role_description", bbmr.getCourseRoleDescription());
	}

	@Test
	public void testDefaultRole() {
		assertFalse(bbmr.getDefaultRole());
		bbmr.setDefaultRole(true);
		assertTrue(bbmr.getDefaultRole());
	}

	@Test
	public void testExpansionData() {
		assertNull(bbmr.getExpansionData());
		bbmr.setExpansionData(new String[]{"expansion_data"});
		assertNotNull(bbmr.getExpansionData());
		assertEquals("expansion_data", bbmr.getExpansionData()[0]);
	}
}
