package org.bbsync.webservice.client.course;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbStaffInfoFolder {
	
	private BbStaffInfoFolder staff_info = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		staff_info = new BbStaffInfoFolder();
		assertNotNull(staff_info);
	}

	@After
	public void tearDown() throws Exception {
		staff_info = null;
		assertNull(staff_info);
	}

	@Test
	public void testCourseId() {
		staff_info.setCourseId("course_id");
		assertEquals("course_id", staff_info.getCourseId());
	}
	
	@Test
	public void testAvailable() {
		staff_info.setAvailable(true);
		assertTrue(staff_info.isAvailable());
	}

	@Test
	public void testPosition() {
		staff_info.setPosition(3);
		assertEquals(3, staff_info.getPosition());
	}

	@Test
	public void testName() {
		staff_info.setName("name");
		assertEquals("name", staff_info.getName());
	}

	@Test
	public void testNameColor() {
		staff_info.setNameColor("FF0303");
		assertEquals("FF0303", staff_info.getNameColor());
	}
	
	@Test
	public void testId() {
		staff_info.setId("id");
		assertEquals("id", staff_info.getId());
	}

	@Test
	public void testExpansionData() {
		staff_info.setExpansionData(new String[]{"expansion_data"});
		assertEquals("expansion_data", staff_info.getExpansionData()[0]);
	}

	@Test
	public void testDescription() {
		staff_info.setDescription("description");
		assertEquals("description", staff_info.getDescription());
	}
	
	@Test
	public void testParentFolderId() {
		staff_info.setParentFolderId("parent_folder_id");
		assertEquals("parent_folder_id", staff_info.getParentFolderId());
	}
}
