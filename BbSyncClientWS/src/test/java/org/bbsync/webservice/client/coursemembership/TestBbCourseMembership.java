package org.bbsync.webservice.client.coursemembership;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bbsync.webservice.client.coursemembership.BbCourseMembership;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbCourseMembership {
	private BbCourseMembership bbcm = null;
	private DateFormat formatDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mmaa");

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bbcm = new BbCourseMembership();
	}

	@After
	public void tearDown() throws Exception {
		bbcm = null;
	}
		
	@Test
	public void testAvailability(){
		assertFalse(bbcm.getAvailable());
		bbcm.setAvailable(true);
		assertTrue(bbcm.getAvailable());
		bbcm.setAvailable(false);
	}
	
	@Test
	public void testCourseId(){
		assertNull(bbcm.getCourseId());
		bbcm.setCourseId("course_id");
		assertEquals("course_id", bbcm.getCourseId());
	}
	
	@Test
	public void testDataSourceId(){
		assertNull(bbcm.getDataSourceId());
		bbcm.setDataSourceId("dataSourceId");
		assertEquals("dataSourceId", bbcm.getDataSourceId());
	}
		
	@Test
	public void testEnrolllmentDate(){
		assertEquals(0, bbcm.getEnrollmentDate());
		long enrollment_date = 0;
		try {
			enrollment_date = ((Date)formatDateTime.parse("1/22/2014 10:15PM")).getTime();
		} catch (ParseException e) {
			fail();
		}
		bbcm.setEnrollmentDate(enrollment_date);
		assertEquals(enrollment_date, bbcm.getEnrollmentDate());
	}
	
	@Test
	public void testExpansionData(){
		assertNull(bbcm.getExpansionData());	
		bbcm.setExpansionData(new String[]{"Test", "Expansion Data"});
		String[] ex_data = bbcm.getExpansionData();
		assertEquals("Test", ex_data[0]);
		assertEquals("Expansion Data", ex_data[1]);
	}
		
	@Test
	public void testHasCartridgeAccess(){
		assertEquals(0, bbcm.getEnrollmentDate());
		bbcm.setHasCartridgeAccess(true);
		assertTrue(bbcm.getHasCartridgeAccess());
		bbcm.setHasCartridgeAccess(false);
		assertFalse(bbcm.getHasCartridgeAccess());
	}
	
	@Test
	public void testId(){
		assertEquals(0, bbcm.getEnrollmentDate());
		bbcm.setId("test_id_1");
		assertEquals("test_id_1", bbcm.getId());
	}	
		
	@Test
	public void testImageFile(){
		assertNull(bbcm.getImageFile());
		bbcm.setImageFile("this is a test string");
		assertEquals("this is a test string", bbcm.getImageFile());
	}
		
	@Test
	public void testRoleId(){
		assertNull(bbcm.getRoleId());
		bbcm.setRoleId("role_id");
		assertEquals("role_id", bbcm.getRoleId());
	}
		
	@Test
	public void testUserId(){
		assertNull(bbcm.getUserId());
		bbcm.setUserId("user_id");
		assertEquals("user_id", bbcm.getUserId());
	}	
}
