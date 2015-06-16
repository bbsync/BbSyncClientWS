package org.bbsync.webservice.client.user;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbAddressBookEntry {

	private BbAddressBookEntry bb_abe = null;
	private BbUserExtendedInfo bb_uxi = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bb_abe = new BbAddressBookEntry();
	}

	@After
	public void tearDown() throws Exception {
		bb_abe = null;
	}

	@Test
	public void testAddressBookEntry() {
		assertNull(bb_abe.getAddressBookEntry());
		setupUserExtendedInfo();
		bb_abe.setAddressBookEntry(bb_uxi);
		assertNotNull(bb_abe.getAddressBookEntry());
	}

	@Test
	public void testExpansionData() {
		assertNull(bb_abe.getExpansionData());
		bb_abe.setExpansionData(new String[] { "org.bbsync.BbAddressBookEntry.ExpansionData=expansion_data" });
		assertNotNull(bb_abe.getExpansionData());
		assertEquals(1, bb_abe.getExpansionData().length);
		assertNotNull(bb_abe.getExpansionData()[0]);
		assertEquals(
				"org.bbsync.BbAddressBookEntry.ExpansionData=expansion_data",
				bb_abe.getExpansionData()[0]);
	}

	@Test
	public void testId() {
		assertNull(bb_abe.getId());
		bb_abe.setId("id");
		assertNotNull(bb_abe.getId());
		assertEquals("id", bb_abe.getId());
	}

	@Test
	public void testTitle() {
		assertNull(bb_abe.getTitle());
		bb_abe.setTitle("title");
		assertNotNull(bb_abe.getTitle());
		assertEquals("title", bb_abe.getTitle());
	}

	@Test
	public void testUserId() {
		assertNull(bb_abe.getUserId());
		bb_abe.setUserId("user_id");
		assertNotNull(bb_abe.getUserId());
		assertEquals("user_id", bb_abe.getUserId());
	}

	private void setupUserExtendedInfo() {
		this.bb_uxi = new BbUserExtendedInfo();
		bb_uxi.setBusinessFax("1234567890_test_string");
		bb_uxi.setBusinessPhone1("1234567890_test_string");
		bb_uxi.setBusinessPhone2("1234567890_test_string");
		bb_uxi.setCity("test_City_string");
		bb_uxi.setCompany("test_Company_string");
		bb_uxi.setCountry("test_Country_string");
		bb_uxi.setDepartment("test_Department_string");
		bb_uxi.setEmailAddress("test_EmailAddress_string");
		bb_uxi.setFamilyName("test_FamilyName_string");
		bb_uxi.setGivenName("test_GivenName_string");
		bb_uxi.setHomeFax("test_HomeFax_string");
		bb_uxi.setHomePhone1("test_HomePhone1_string");
		bb_uxi.setHomePhone2("test_HomePhone2_string");
		bb_uxi.setJobTitle("test_JobTitle_string");
		bb_uxi.setMiddleName("test_MiddleName_string");
		bb_uxi.setMobilePhone("test_MobilePhone_string");
		bb_uxi.setState("test_State_string");
		bb_uxi.setStreet1("test_Street1_string");
		bb_uxi.setStreet2("test_Street2_string");
		bb_uxi.setWebPage("test_WebPage_string");
		bb_uxi.setZipCode("test_ZipCode_string");
	}
}
