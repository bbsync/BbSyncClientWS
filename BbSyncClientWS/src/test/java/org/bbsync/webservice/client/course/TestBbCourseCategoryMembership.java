package org.bbsync.webservice.client.course;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbCourseCategoryMembership {
	BbCourseCategoryMembership bb_ccm = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bb_ccm = new BbCourseCategoryMembership();
	}

	@After
	public void tearDown() throws Exception {
		bb_ccm = null;
	}

	@Test
	public void testId() {
		assertNull(bb_ccm.getId());
		bb_ccm.setId("id");
		assertNotNull(bb_ccm.getId());
		assertEquals("id", bb_ccm.getId());
	}

	@Test
	public void testCategoryId() {
		assertNull(bb_ccm.getCategoryId());
		bb_ccm.setCategoryId("category_id");
		assertNotNull(bb_ccm.getCategoryId());
		assertEquals("category_id", bb_ccm.getCategoryId());
	}

	@Test
	public void testAvailable() {
		assertFalse(bb_ccm.isAvailable());
		bb_ccm.setAvailable(true);
		assertTrue(bb_ccm.isAvailable());
	}

	@Test
	public void testDataSourceId() {
    	assertNull(bb_ccm.getDataSourceId());
    	bb_ccm.setDataSourceId("data_source_id");
    	assertNotNull(bb_ccm.getDataSourceId());
        assertEquals("data_source_id", bb_ccm.getDataSourceId());
	}

	@Test
	public void testOrganization() {
		assertFalse(bb_ccm.isOrganization());
	}
	
	@Test
	public void testCourseId(){
    	assertNull(bb_ccm.getCourseId());
    	bb_ccm.setCourseId("course_id");;
    	assertNotNull(bb_ccm.getCourseId());
        assertEquals("course_id", bb_ccm.getCourseId());
	}

	@Test
	public void testExpansionDataStringArray() {
		bb_ccm.setExpansionData(new String[]{"org.bbsync.BbCourseCategoryMembership.ExpansionData=expansion_data"});
		assertEquals("org.bbsync.BbCourseCategoryMembership.ExpansionData=expansion_data", bb_ccm.getExpansionData()[0]);
	}
}
