package org.bbsync.webservice.client.user;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbPortalRole {
	
	private BbPortalRole role = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		role = new BbPortalRole();
	}

	@After
	public void tearDown() throws Exception {
		role = null;
	}

	@Test
	public void testDataSourceId() {
		assertNull(role.getDataSourceId());
		role.setDataSourceId("dataSourceId");
		assertNotNull(role.getDataSourceId());
		assertEquals("dataSourceId", role.getDataSourceId());
	}
	
	@Test
	public void testDescription() {
		assertNull(role.getDescription());
		role.setDescription("description");
		assertNotNull(role.getDescription());
		assertEquals("description", role.getDescription());
	}
	
	@Test
	public void testExpansionData() {
		assertNull(role.getExpansionData());
		role.setExpansionData(new String[]{"org.bbsync.BbPortalRole.ExpansionData=expansion_data"});
		assertNotNull(role.getExpansionData());
		assertEquals(1, role.getExpansionData().length);
		assertNotNull(role.getExpansionData()[0]);
		assertEquals("org.bbsync.BbPortalRole.ExpansionData=expansion_data", role.getExpansionData()[0]);
	}
	
	@Test
	public void testId() {
		assertNull(role.getId());
		role.setId("id");
		assertNotNull(role.getId());
		assertEquals("id", role.getId());
	}

	@Test
	public void testRoleId() {
		assertNull(role.getRoleId());
		role.setRoleId("role_id");
		assertNotNull(role.getRoleId());
		assertEquals("role_id", role.getRoleId());
	}
}
