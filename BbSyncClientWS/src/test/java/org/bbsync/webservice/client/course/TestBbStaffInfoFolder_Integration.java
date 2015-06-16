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

public class TestBbStaffInfoFolder_Integration {
	private static final Logger logger = Logger.getLogger(TestBbStaffInfoFolder_Integration.class.getName());
	//StaffInfoFolder Information
	private String basic_staff_info_folder_id = null;
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
		BbStaffInfoFolder folder = new BbStaffInfoFolder();
        Long version = folder.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
    
    @Ignore
    public void testInitializeCourseWS() {
    	BbStaffInfoFolder folder = new BbStaffInfoFolder();
        assertTrue(folder.initializeCourseWS());
        logger.debug("initialize Course web service succeded.");
    }
    
    @Test
    public void testPersist_Folder(){
    	createBasicStaffInfoFolder();
    	assertNotNull(basic_staff_info_folder_id);
    	BbStaffInfoFolder folder = new BbStaffInfoFolder();
    	BbStaffInfoFolder[] infos = folder.getAllBbStaffInfoFolders(basic_course_id);
    	assertNotNull(infos);
    	assertEquals(1, infos.length);
    	assertEquals("BbStaffInfoFolder", infos[0].getClass().getSimpleName());
    }
    
    @Test
    public void testRetrieve(){
    	createBasicStaffInfoFolder();
    	assertNotNull(basic_staff_info_folder_id);
    	BbStaffInfoFolder folder = new BbStaffInfoFolder();
    	folder.setId(basic_staff_info_folder_id);
    	folder.setCourseId(basic_course_id);
    	BbStaffInfoFolder info = (BbStaffInfoFolder) folder.retrieve();
    	assertNotNull(info);
    	assertEquals("BbStaffInfoFolder", info.getClass().getSimpleName());
    	assertEquals(basic_staff_info_folder_id, info.getId());
    }
    
    @Test
    public void testDelete(){
    	createBasicStaffInfoFolder();
    	assertNotNull(basic_staff_info_folder_id);
    	BbStaffInfoFolder folder = new BbStaffInfoFolder();
    	AbstractStaffInfo[] infos = folder.getAllBbStaffInfos(basic_course_id);
    	assertNotNull(infos);
    	assertEquals(1, infos.length);
    	assertEquals("BbStaffInfoFolder", infos[0].getClass().getSimpleName());
    	folder.setCourseId(basic_course_id);
    	folder.setId(basic_staff_info_folder_id);
    	assertTrue(folder.delete());
    	infos = folder.getAllBbStaffInfos(basic_course_id);
    	assertNull(infos);
    }
    
    @Test
    public void testCreateNestedFolders(){
    	//create a top level folder
    	createBasicStaffInfoFolder();
    	assertNotNull(basic_staff_info_folder_id);
    	//create a nested folder
    	BbStaffInfoFolder folder = new BbStaffInfoFolder();
    	folder.setCourseId(basic_course_id);
    	folder.setDescription("Folder description");
    	folder.setParentFolderId(basic_staff_info_folder_id);
    	folder.setName("Folder Name");
    	folder.setNameColor("00FF00");
    	String id = (String)folder.persist();
    	assertNotNull(id);
    	BbStaffInfoFolder[] infos = folder.getAllBbStaffInfoFolders(basic_course_id);
    	assertNotNull(infos);
    	assertEquals(2, infos.length);
    	assertEquals("BbStaffInfoFolder", infos[0].getClass().getSimpleName());
    	assertEquals("BbStaffInfoFolder", infos[1].getClass().getSimpleName());
    }
    
    private void createBasicStaffInfoFolder(){
    	BbStaffInfoFolder staff_info = new BbStaffInfoFolder();
    	staff_info.setCourseId(basic_course_id);
    	String id = (String)staff_info.persist();
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
