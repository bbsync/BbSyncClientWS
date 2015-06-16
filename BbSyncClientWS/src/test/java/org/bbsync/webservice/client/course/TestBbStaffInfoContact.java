package org.bbsync.webservice.client.course;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbStaffInfoContact {
	
	private BbStaffInfoContact staff_info = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		staff_info = new BbStaffInfoContact();
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
	public void testTitle() {
		staff_info.setTitle("title");
		assertEquals("title", staff_info.getTitle());
	}

	@Test
	public void testFirstName() {
		staff_info.setFirstName("first_name");
		assertEquals("first_name", staff_info.getFirstName());
	}

	@Test
	public void testLastName() {
		staff_info.setLastName("last_name");
		assertEquals("last_name", staff_info.getLastName());
	}

	@Test
	public void testPhone() {
		staff_info.setPhone("phone");
		assertEquals("phone", staff_info.getPhone());
	}

	@Test
	public void testEmail() {
		staff_info.setEmail("email");
		assertEquals("email", staff_info.getEmail());
	}

	@Test
	public void testOfficeHours() {
		staff_info.setOfficeHours("office_hours");
		assertEquals("office_hours", staff_info.getOfficeHours());
	}

	@Test
	public void testOfficeLocation() {
		staff_info.setOfficeLocation("office_location");
		assertEquals("office_location", staff_info.getOfficeLocation());
	}

	@Test
	public void testPersonalLink() {
		staff_info.setPersonalLink("personal_link");
		assertEquals("personal_link", staff_info.getPersonalLink());
	}

	@Test
	public void testNotes() {
		staff_info.setNotes("notes");
		assertEquals("notes", staff_info.getNotes());
	}

	@Test
	public void testId() {
		staff_info.setId("id");
		assertEquals("id", staff_info.getId());
	}

	@Test
	public void testNotesType() {
		staff_info.setNotesType(BbStaffInfoContact.TEXT_TYPE_HTML);
		assertEquals(BbStaffInfoContact.TEXT_TYPE_HTML, staff_info.getNotesType());
	}

	@Test
	public void testExpansionData() {
		staff_info.setExpansionData(new String[]{"expansion_data"});
		assertEquals("expansion_data", staff_info.getExpansionData()[0]);
	}

	@Test
	public void testStaffInfoFolderId() {
		staff_info.setStaffInfoFolderId("staff_info_folder_id");
		assertEquals("staff_info_folder_id", staff_info.getStaffInfoFolderId());
	}
}
