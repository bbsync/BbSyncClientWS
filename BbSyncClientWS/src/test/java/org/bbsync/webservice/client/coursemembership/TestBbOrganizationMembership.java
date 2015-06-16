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

import org.bbsync.webservice.client.coursemembership.BbOrganizationMembership;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbOrganizationMembership {
	private BbOrganizationMembership bbom = null;
	private DateFormat formatDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mmaa");

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bbom = new BbOrganizationMembership();
	}

	@After
	public void tearDown() throws Exception {
		bbom = null;
	}
		
	@Test
	public void testAvailability(){
		assertFalse(bbom.getAvailable());
		bbom.setAvailable(true);
		assertTrue(bbom.getAvailable());
		bbom.setAvailable(false);
	}
	
	@Test
	public void testOrganizationId(){
		assertNull(bbom.getOrganizationId());
		bbom.setOrganizationId("org_id");
		assertEquals("org_id", bbom.getOrganizationId());
	}
	
	@Test
	public void testDataSourceId(){
		assertNull(bbom.getDataSourceId());
		bbom.setDataSourceId("dataSourceId");
		assertEquals("dataSourceId", bbom.getDataSourceId());
	}
		
	@Test
	public void testEnrolllmentDate(){
		assertEquals(0, bbom.getEnrollmentDate());
		long enrollment_date = 0;
		try {
			enrollment_date = ((Date)formatDateTime.parse("1/22/2014 10:15PM")).getTime();
		} catch (ParseException e) {
			fail();
		}
		bbom.setEnrollmentDate(enrollment_date);
		assertEquals(enrollment_date, bbom.getEnrollmentDate());
	}
	
	@Test
	public void testExpansionData(){
		assertNull(bbom.getExpansionData());	
		bbom.setExpansionData(new String[]{"Test", "Expansion Data"});
		String[] ex_data = bbom.getExpansionData();
		assertEquals("Test", ex_data[0]);
		assertEquals("Expansion Data", ex_data[1]);
	}
		
	@Test
	public void testHasCartridgeAccess(){
		assertEquals(0, bbom.getEnrollmentDate());
		bbom.setHasCartridgeAccess(true);
		assertTrue(bbom.getHasCartridgeAccess());
		bbom.setHasCartridgeAccess(false);
		assertFalse(bbom.getHasCartridgeAccess());
	}
	
	@Test
	public void testId(){
		assertEquals(0, bbom.getEnrollmentDate());
		bbom.setId("test_id_1");
		assertEquals("test_id_1", bbom.getId());
	}	
		
	@Test
	public void testImageFile(){
		assertNull(bbom.getImageFile());
		bbom.setImageFile("this is a test string");
		assertEquals("this is a test string", bbom.getImageFile());
	}
		
	@Test
	public void testRoleId(){
		assertNull(bbom.getRoleId());
		bbom.setRoleId("role_id");
		assertEquals("role_id", bbom.getRoleId());
	}
		
	@Test
	public void testUserId(){
		assertNull(bbom.getUserId());
		bbom.setUserId("user_id");
		assertEquals("user_id", bbom.getUserId());
	}	
}
