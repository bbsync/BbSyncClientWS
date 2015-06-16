package org.bbsync.webservice.client.coursemembership;

import static org.junit.Assert.*;

import org.bbsync.webservice.client.coursemembership.BbOrganizationMembershipRole;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbOrganizationMembershipRole {
	private BbOrganizationMembershipRole bbmr = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bbmr = new BbOrganizationMembershipRole();
	}

	@After
	public void tearDown() throws Exception {
		bbmr = null;
	}

	@Test
	public void testRoleIdentifier() {
		assertNull(bbmr.getRoleIdentifier());
		bbmr.setRoleIdentifier(BbOrganizationMembershipRole.ORG_ROLE_PARTICIPANT);
		assertNotNull(bbmr.getRoleIdentifier());
		assertEquals(BbOrganizationMembershipRole.ORG_ROLE_PARTICIPANT, bbmr.getRoleIdentifier());
	}

	@Test
	public void testCourseRoleDescription() {
		assertNull(bbmr.getCourseRoleDescription());
		bbmr.setCourseRoleDescription("course_role_description");
		assertNotNull(bbmr.getCourseRoleDescription());
		assertEquals("course_role_description", bbmr.getCourseRoleDescription());
	}

	@Test
	public void testOrgRoleDescription() {
		assertNull(bbmr.getOrgRoleDescription());
		bbmr.setOrgRoleDescription("org_role_description");
		assertNotNull(bbmr.getOrgRoleDescription());
		assertEquals("org_role_description", bbmr.getOrgRoleDescription());
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
