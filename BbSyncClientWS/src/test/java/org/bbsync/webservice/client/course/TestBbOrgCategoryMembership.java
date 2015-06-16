package org.bbsync.webservice.client.course;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbOrgCategoryMembership {
	BbOrgCategoryMembership bb_ocm = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bb_ocm = new BbOrgCategoryMembership();
	}

	@After
	public void tearDown() throws Exception {
		bb_ocm = null;
	}

	@Test
	public void testId() {
		assertNull(bb_ocm.getId());
		bb_ocm.setId("id");
		assertNotNull(bb_ocm.getId());
		assertEquals("id", bb_ocm.getId());
	}

	@Test
	public void testCategoryId() {
		assertNull(bb_ocm.getCategoryId());
		bb_ocm.setCategoryId("category_id");
		assertNotNull(bb_ocm.getCategoryId());
		assertEquals("category_id", bb_ocm.getCategoryId());
	}

	@Test
	public void testAvailable() {
		assertFalse(bb_ocm.isAvailable());
		bb_ocm.setAvailable(true);
		assertTrue(bb_ocm.isAvailable());
	}

	@Test
	public void testDataSourceId() {
    	assertNull(bb_ocm.getDataSourceId());
    	bb_ocm.setDataSourceId("data_source_id");
    	assertNotNull(bb_ocm.getDataSourceId());
        assertEquals("data_source_id", bb_ocm.getDataSourceId());
	}

	@Test
	public void testOrganization() {
		assertTrue(bb_ocm.isOrganization());
	}
	
	@Test
	public void testOrganizationId(){
    	assertNull(bb_ocm.getOrganizationId());
    	bb_ocm.setOrganizationId("org_id");;
    	assertNotNull(bb_ocm.getOrganizationId());
        assertEquals("org_id", bb_ocm.getOrganizationId());
	}

	@Test
	public void testExpansionDataStringArray() {
		bb_ocm.setExpansionData(new String[]{"org.bbsync.BbOrgCategoryMembership.ExpansionData=expansion_data"});
		assertEquals("org.bbsync.BbOrgCategoryMembership.ExpansionData=expansion_data", bb_ocm.getExpansionData()[0]);
	}
}
