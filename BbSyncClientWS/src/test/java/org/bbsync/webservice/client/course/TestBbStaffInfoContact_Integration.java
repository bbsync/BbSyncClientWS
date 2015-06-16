package org.bbsync.webservice.client.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.abstracts.AbstractStaffInfo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbStaffInfoContact_Integration {
	private static final Logger logger = Logger.getLogger(TestBbCourse.class.getName());
	//StaffInfo Information
	private String basic_staff_info_folder_id = null;
	private String basic_staff_info_contact_id = null;
	private static final String STAFF_INFO_EMAIL = "StaffInfoTest@opencampus.com";
	private static final String STAFF_INFO_TITLE = "Staff Information Title";
	//Course Information
	private String basic_course_id = null;
    private static final String COURSE_ID = "tst_crs_1";
    private static final String COURSE_NAME = "Test Course #1";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		createBasicCourse();
	}

	@After
	public void tearDown() throws Exception {
		deleteCourse(basic_course_id);
	}

	@Ignore
    public void testGetServerVersion() {
		BbStaffInfoContact contact = new BbStaffInfoContact();
        Long version = contact.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
    
    @Ignore
    public void testInitializeCourseWS() {
    	BbStaffInfoContact contact = new BbStaffInfoContact();
        assertTrue(contact.initializeCourseWS());
        logger.debug("initialize Course web service succeded.");
    }
        
    @Test
    public void testPersist_Contact_no_Folder(){
    	createBasicStaffInfoContact(null);
    	assertNotNull(basic_staff_info_contact_id);
    	BbStaffInfoContact contact = new BbStaffInfoContact();
    	BbStaffInfoContact[] contacts = contact.getAllBbStaffInfoContacts(basic_course_id);
    	assertNotNull(contacts);
    	assertEquals(1, contacts.length);
    }
    
    @Test
    public void testPersist_Contact_with_folder_no_Parent(){
    	createBasicStaffInfoFolder();
    	assertNotNull(basic_staff_info_folder_id);
    	createBasicStaffInfoContact(null);
    	assertNotNull(basic_staff_info_contact_id);
    	BbStaffInfoContact contact = new BbStaffInfoContact();
    	BbStaffInfoContact[] contacts = contact.getAllBbStaffInfoContacts(basic_course_id);
    	assertNotNull(contacts);
    	assertEquals(1, contacts.length);
    }
    
    @Test
    public void testRetrieve(){
    	createBasicStaffInfoFolder();
    	assertNotNull(basic_staff_info_folder_id);
    	createBasicStaffInfoContact(null);
    	assertNotNull(basic_staff_info_contact_id);
    	//test retrieve StaffInfo Contact *uses Unique ID
    	BbStaffInfoContact contact = new BbStaffInfoContact();
    	contact.setId(basic_staff_info_contact_id);
    	contact.setCourseId(basic_course_id);
    	contact = (BbStaffInfoContact) contact.retrieve();
    	assertNotNull(contact);
    }
    
    @Test
    public void testRetrieve_with_email(){
    	createBasicStaffInfoFolder();
    	assertNotNull(basic_staff_info_folder_id);
    	createBasicStaffInfoContact(null);
    	assertNotNull(basic_staff_info_contact_id);
    	BbStaffInfoContact contact = new BbStaffInfoContact();
    	contact.setEmail(STAFF_INFO_EMAIL);
    	contact.setCourseId(basic_course_id);
    	contact = (BbStaffInfoContact) contact.retrieve();
    	assertNotNull(contact);
    	assertEquals(STAFF_INFO_TITLE, contact.getTitle());
    }
    
    @Test
    public void testDelete(){
    	createBasicStaffInfoFolder();
    	assertNotNull(basic_staff_info_folder_id);
    	createBasicStaffInfoContact(null);
    	assertNotNull(basic_staff_info_contact_id);
    	BbStaffInfoContact contact = new BbStaffInfoContact();
    	AbstractStaffInfo[] infos = contact.getAllBbStaffInfos(basic_course_id);
    	assertNotNull(infos);
    	assertEquals(2, infos.length);
    	//delete the Contact first
    	contact.setId(basic_staff_info_contact_id);
    	contact.setCourseId(basic_course_id);
    	assertTrue(contact.delete());
    	infos = contact.getAllBbStaffInfos(basic_course_id);
    	assertNotNull(infos);
    	assertEquals(1, infos.length);
    	//delete the folder
    	BbStaffInfoFolder folder = new BbStaffInfoFolder();
    	folder.setId(basic_staff_info_folder_id);
    	folder.setCourseId(basic_course_id);
    	assertTrue(folder.delete());
    	infos = folder.getAllBbStaffInfos(basic_course_id);
    	assertNull(infos);
    }
    
    @Test
    public void testDelete_parent_first(){
    	createBasicStaffInfoFolder();
    	assertNotNull(basic_staff_info_folder_id);
    	createBasicStaffInfoContact(null);
    	assertNotNull(basic_staff_info_contact_id);
    	BbStaffInfoContact contact = new BbStaffInfoContact();
    	AbstractStaffInfo[] infos = contact.getAllBbStaffInfos(basic_course_id);
    	assertNotNull(infos);
    	assertEquals(2, infos.length);
    	infos = contact.getAllBbStaffInfoContacts(basic_course_id);
    	assertNotNull(infos);
    	assertEquals(1, infos.length);
    	assertEquals("BbStaffInfoContact", infos[0].getClass().getSimpleName());
    	//deleting the parent Folder also deletes the child Contacts
    	BbStaffInfoFolder folder = new BbStaffInfoFolder();
    	folder.setId(basic_staff_info_folder_id);
    	folder.setCourseId(basic_course_id);
    	assertTrue(folder.delete());
    	infos = folder.getAllBbStaffInfos(basic_course_id);
    	assertNull(infos);
    }
    
    private void createBasicStaffInfoContact(String parent_id){
    	BbStaffInfoContact contact = new BbStaffInfoContact();
    	//must set title and/or first name and/or last name
    	contact.setTitle(STAFF_INFO_TITLE);
    	contact.setEmail(STAFF_INFO_EMAIL);
    	if(parent_id!=null)contact.setStaffInfoFolderId(parent_id);
    	contact.setCourseId(basic_course_id);
    	String id = (String)contact.persist();
    	assertNotNull(id);
    	basic_staff_info_contact_id = id;
    }
    
    private void createBasicStaffInfoFolder(){
    	BbStaffInfoFolder folder = new BbStaffInfoFolder();
    	folder.setCourseId(basic_course_id);
    	String id = (String)folder.persist();
    	assertNotNull(id);
    	basic_staff_info_folder_id = id;
    }
	
	private void createBasicCourse(){
		BbCourse bb_course = new BbCourse();
        bb_course.setCourseId(COURSE_ID);
        bb_course.setName(COURSE_NAME);
        String id = (String) bb_course.persist();
        assertNotNull(id);
        basic_course_id = id;
        logger.debug("Created Course with ID: " + id);
    }
    
    private void deleteCourse(String id) {
        BbCourse bb_course = new BbCourse();
        bb_course.setId(id);
        assertTrue(bb_course.delete());
        logger.debug("Deleted Course with ID: " + id);
    }
}
