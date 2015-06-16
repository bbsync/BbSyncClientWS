package org.bbsync.webservice.client.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbAddressBookEntry_Integration {
	
	private BbAddressBookEntry bb_abe = null;
	private BbAddressBookEntry bb_abe1 = null;
	private BbAddressBookEntry bb_abe2 = null;
	private BbUserExtendedInfo bb_uxi1 = null;
	private BbUserExtendedInfo bb_uxi2 = null;
	private BbUser bb_user = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bb_abe = new BbAddressBookEntry();
		createBasicUser();
	}

	@After
	public void tearDown() throws Exception {
		bb_abe = null;
		deleteUser(bb_user.getId());
	}
	
	@Test
	public void testPersist(){
		setupUser1ExtendedInfo();
		bb_abe1 = new BbAddressBookEntry();
		bb_abe1.setAddressBookEntry(bb_uxi1);
		bb_abe1.setTitle("User1 Address Book Entry");
		bb_abe1.setUserId(bb_user.getId());
		bb_abe1.setUserName(bb_user.getName());
		bb_abe1.setUserPassword(bb_user.getPassword());
		String result = (String) bb_abe1.persist();
		assertNotNull(result);
	}
	
	@Test
	public void testRetrieve(){
		setupUser1ExtendedInfo();
		//create a new address book entry
		bb_abe1 = new BbAddressBookEntry();
		bb_abe1.setAddressBookEntry(bb_uxi1);
		bb_abe1.setTitle("User1 Address Book Entry");
		bb_abe1.setUserId(bb_user.getId());
		bb_abe1.setUserName(bb_user.getName());
		bb_abe1.setUserPassword(bb_user.getPassword());
		String result = (String) bb_abe1.persist();
		assertNotNull(result);
		//retrieve the new address book entry
		bb_abe.setId(result);
		bb_abe.setUserName(bb_user.getName());
		bb_abe.setUserPassword(bb_user.getPassword());
		bb_abe = (BbAddressBookEntry) bb_abe.retrieve();
		assertNotNull(bb_abe);
		assertEquals(result, bb_abe.getId());
		assertEquals("User1 Address Book Entry", bb_abe.getTitle());
		assertEquals(bb_user.getId(), bb_abe.getUserId());
	}

	@Test
	public void testDelete(){
		setupUser1ExtendedInfo();
		//create a new address book entry
		bb_abe1 = new BbAddressBookEntry();
		bb_abe1.setAddressBookEntry(bb_uxi1);
		bb_abe1.setTitle("User1 Address Book Entry");
		bb_abe1.setUserId(bb_user.getId());
		bb_abe1.setUserName(bb_user.getName());
		bb_abe1.setUserPassword(bb_user.getPassword());
		String result = (String) bb_abe1.persist();
		assertNotNull(result);
		//delete the address book entry
		bb_abe1 = new BbAddressBookEntry();
		bb_abe1.setId(result);
		bb_abe1.setUserName(bb_user.getName());
		bb_abe1.setUserPassword(bb_user.getPassword());
		assertTrue(bb_abe1.delete());
		//try to retrieve the entry.
		bb_abe.setId(result);
		bb_abe.setUserName(bb_user.getName());
		bb_abe.setUserPassword(bb_user.getPassword());
		bb_abe = (BbAddressBookEntry) bb_abe.retrieve();
		assertNull(bb_abe);
	}

	
	@Test
	public void testGetAddressBookEntryById_single(){
		setupUser1ExtendedInfo();
		bb_abe1 = new BbAddressBookEntry();
		bb_abe1.setAddressBookEntry(bb_uxi1);
		bb_abe1.setTitle("User1 Address Book Entry");
		bb_abe1.setUserId(bb_user.getId());
		String[] result = bb_abe.saveAddressBookEntrys(new BbAddressBookEntry[]{bb_abe1}, bb_user.getName(), bb_user.getPassword());
		assertNotNull(result);
		assertEquals(1, result.length);
		assertNotNull(result[0]);
		BbAddressBookEntry[] entries = bb_abe.getAddressBookEntrysById(result, bb_user.getName(), bb_user.getPassword());
		assertNotNull(entries);
		assertEquals(1, entries.length);
		assertEquals(result[0], entries[0].getId());
	}
		
	@Test
	public void testGetAddressBookEntryById_multi(){
		setupUser1ExtendedInfo();
		bb_abe1 = new BbAddressBookEntry();
		bb_abe1.setAddressBookEntry(bb_uxi1);
		bb_abe1.setTitle("User1 Address Book Entry");
		bb_abe1.setUserId(bb_user.getId());
		setupUser2ExtendedInfo();
		bb_abe2 = new BbAddressBookEntry();
		bb_abe2.setAddressBookEntry(bb_uxi2);
		bb_abe2.setTitle("User2 Address Book Entry");
		bb_abe2.setUserId(bb_user.getId());
		String[] result = bb_abe.saveAddressBookEntrys(new BbAddressBookEntry[]{bb_abe1, bb_abe2}, bb_user.getName(), bb_user.getPassword());
		assertNotNull(result);
		assertEquals(2, result.length);
		assertNotNull(result[0]);
		assertNotNull(result[1]);
		assertNotEquals(result[0], result[1]);
		BbAddressBookEntry[] entries = bb_abe.getAddressBookEntrysById(result, bb_user.getName(), bb_user.getPassword());
		assertNotNull(entries);
		assertEquals(2, entries.length);
		assertNotEquals(entries[0].getId(), entries[1].getId());
	}
	
	@Test
	public void testGetAddressBookEntryByUserName_single(){
		setupUser1ExtendedInfo();
		bb_abe1 = new BbAddressBookEntry();
		bb_abe1.setAddressBookEntry(bb_uxi1);
		bb_abe1.setTitle("User1 Address Book Entry");
		bb_abe1.setUserId(bb_user.getId());
		String[] result = bb_abe.saveAddressBookEntrys(new BbAddressBookEntry[]{bb_abe1}, bb_user.getName(), bb_user.getPassword());
		assertNotNull(result);
		assertEquals(1, result.length);
		assertNotNull(result[0]);
		BbAddressBookEntry[] entries = bb_abe.getAddressBookEntrysByUserName(bb_user.getName(), bb_user.getPassword());
		assertNotNull(entries);
		assertEquals(1, entries.length);
		assertEquals(result[0], entries[0].getId());
	}
	
	@Test
	public void testGetAddressBookEntryByUserName_multi(){
		setupUser1ExtendedInfo();
		bb_abe1 = new BbAddressBookEntry();
		bb_abe1.setAddressBookEntry(bb_uxi1);
		bb_abe1.setTitle("User1 Address Book Entry");
		bb_abe1.setUserId(bb_user.getId());
		setupUser2ExtendedInfo();
		bb_abe2 = new BbAddressBookEntry();
		bb_abe2.setAddressBookEntry(bb_uxi2);
		bb_abe2.setTitle("User2 Address Book Entry");
		bb_abe2.setUserId(bb_user.getId());
		String[] result = bb_abe.saveAddressBookEntrys(new BbAddressBookEntry[]{bb_abe1, bb_abe2}, bb_user.getName(), bb_user.getPassword());
		assertNotNull(result);
		assertEquals(2, result.length);
		assertNotNull(result[0]);
		assertNotNull(result[1]);
		assertNotEquals(result[0], result[1]);
		BbAddressBookEntry[] entries = bb_abe.getAddressBookEntrysByUserName(bb_user.getName(), bb_user.getPassword());
		assertNotNull(entries);
		assertEquals(2, entries.length);
		assertNotEquals(entries[0].getId(), entries[1].getId());
	}

	@Test
	public void testSaveAddressBookEntry_single(){
		setupUser1ExtendedInfo();
		bb_abe1 = new BbAddressBookEntry();
		bb_abe1.setAddressBookEntry(bb_uxi1);
		bb_abe1.setTitle("User1 Address Book Entry");
		bb_abe1.setUserId(bb_user.getId());
		String[] result = bb_abe.saveAddressBookEntrys(new BbAddressBookEntry[]{bb_abe1}, bb_user.getName(), bb_user.getPassword());
		assertNotNull(result);
		assertEquals(1, result.length);
		assertNotNull(result[0]);
	}

	@Test
	public void testSaveAddressBookEntry_multi(){
		setupUser1ExtendedInfo();
		bb_abe1 = new BbAddressBookEntry();
		bb_abe1.setAddressBookEntry(bb_uxi1);
		bb_abe1.setTitle("User1 Address Book Entry");
		bb_abe1.setUserId(bb_user.getId());
		setupUser2ExtendedInfo();
		bb_abe2 = new BbAddressBookEntry();
		bb_abe2.setAddressBookEntry(bb_uxi2);
		bb_abe2.setTitle("User2 Address Book Entry");
		bb_abe2.setUserId(bb_user.getId());
		String[] result = bb_abe.saveAddressBookEntrys(new BbAddressBookEntry[]{bb_abe1, bb_abe2}, bb_user.getName(), bb_user.getPassword());
		assertNotNull(result);
		assertEquals(2, result.length);
		assertNotNull(result[0]);
		assertNotNull(result[1]);
		assertNotEquals(result[0], result[1]);
	}
	
	@Test
	public void testDeleteAddressBookEntry_single(){
		//First, create an address book entry
		setupUser1ExtendedInfo();
		bb_abe1 = new BbAddressBookEntry();
		bb_abe1.setAddressBookEntry(bb_uxi1);
		bb_abe1.setTitle("User1 Address Book Entry");
		bb_abe1.setUserId(bb_user.getId());
		String[] result_save = bb_abe.saveAddressBookEntrys(new BbAddressBookEntry[]{bb_abe1}, bb_user.getName(), bb_user.getPassword());
		assertNotNull(result_save);
		assertEquals(1, result_save.length);
		assertNotNull(result_save[0]);
		//Next, delete the address book entry
		String[] result_delete = bb_abe.deleteAddressBookEntrys(result_save, bb_user.getName(), bb_user.getPassword());
		assertNotNull(result_delete);
		assertEquals(1, result_delete.length);
		assertNotNull(result_delete[0]);
		assertEquals(result_save[0], result_delete[0]);

	}

	
	private void setupUser1ExtendedInfo(){
		this.bb_uxi1 = new BbUserExtendedInfo();
		bb_uxi1.setGivenName("Jack");
		bb_uxi1.setFamilyName("Daniels");
		bb_uxi1.setStreet1("182 Lynchburg Highway");
		bb_uxi1.setCity("Lynchburg");
		bb_uxi1.setState("Tennessee");
		bb_uxi1.setZipCode("37352");
		bb_uxi1.setHomePhone1("(888) 551-5225");
		bb_uxi1.setEmailAddress("lucknumber7@jackdaniels.com");
		bb_uxi1.setWebPage("www.jackdaniels.com");			
	}
	
	private void setupUser2ExtendedInfo(){
		this.bb_uxi2 = new BbUserExtendedInfo();
		bb_uxi2.setGivenName("Jim");
		bb_uxi2.setFamilyName("Beam");
		bb_uxi2.setStreet1("526 Happy Hollow Road");
		bb_uxi2.setCity("Clermont");
		bb_uxi2.setState("Kentucky");
		bb_uxi2.setZipCode("40110");
		bb_uxi2.setHomePhone1("(502) 543-9877");
		bb_uxi2.setEmailAddress("thereverend@jimbeam.com");
		bb_uxi2.setWebPage("www.jimbeam.com");			
	}

	
	private void createBasicUser(){
		bb_user = new BbUser();
	    bb_user.setGivenName("Johnnie");
	    bb_user.setFamilyName("Walker");
	    bb_user.setName("jw1111111");
	    bb_user.setPassword("password");
	    bb_user.setAvailable(Boolean.TRUE);
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
	    bb_user.setId(bb_user_id);
	}
	
	private void deleteUser(String bb_user_id){
		BbUser bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		assertTrue(bb_user.delete());
    }

}
