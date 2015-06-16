package org.bbsync.webservice.client.user;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbUserRole {
	
	private BbUserRole role = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		role = new BbUserRole();
	}

	@After
	public void tearDown() throws Exception {
		role = null;
	}

	@Test
	public void testExpansionData() {
		assertNull(role.getExpansionData());
		role.setExpansionData(new String[]{"org.bbsync.BbUserRole.ExpansionData=expansion_data"});
		assertNotNull(role.getExpansionData());
		assertEquals(1, role.getExpansionData().length);
		assertNotNull(role.getExpansionData()[0]);
		assertEquals("org.bbsync.BbUserRole.ExpansionData=expansion_data", role.getExpansionData()[0]);
	}
	
	@Test
	public void testId() {
		assertNull(role.getId());
		role.setId("id");
		assertNotNull(role.getId());
		assertEquals("id", role.getId());
	}
	
	@Test
	public void testInsRoleId() {
		assertNull(role.getInsRoleId());
		role.setInsRoleId("ins_role_id");
		assertNotNull(role.getInsRoleId());
		assertEquals("ins_role_id", role.getInsRoleId());
	}

	@Test
	public void testUserId() {
		assertNull(role.getUserId());
		role.setUserId("user_id");
		assertNotNull(role.getUserId());
		assertEquals("user_id", role.getUserId());
	}

}
