package org.bbsync.webservice.client.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbPortalRole_Integration {
	private static final Logger logger = Logger.getLogger(TestBbPortalRole_Integration.class.getName());
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Ignore
    public void testGetServerVersion() {
		BbPortalRole bbpr = new BbPortalRole();
        Long version = bbpr.getServerVersion();
        assertNotNull(version);
        assertTrue(version>0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
	
	@Ignore
    public void testInitializeUserWS() {
		BbPortalRole bbpr = new BbPortalRole();
        assertTrue(bbpr.initializeUserWS());
        logger.debug("initialize User web service succeded.");
    }
	
	@Test
	public void testGetAllInstitutionRoles(){
		BbPortalRole bbpr = new BbPortalRole();
		BbPortalRole[] roles = bbpr.getAllInstitutionRoles();
		assertNotNull(roles);
		assertTrue(roles.length>19);
	}
	
	@Test
	public void TestGetInstitutionRolesByRoleId_single(){
		BbPortalRole bbpr = new BbPortalRole();
		BbPortalRole[] roles = bbpr.getInstitutionRolesByRoleId(new String[]{BbPortalRole.INSTITUTION_ROLE_STUDENT});
		assertNotNull(roles);
		assertTrue(roles.length==1);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_STUDENT, roles[0].getRoleId());
		assertNotNull(roles[0].getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Test
	public void TestGetInstitutionRolesByRoleId_multiple(){
		BbPortalRole bbpr = new BbPortalRole();
		BbPortalRole[] roles = bbpr.getInstitutionRolesByRoleId(new String[]{
				BbPortalRole.INSTITUTION_ROLE_STUDENT,
				BbPortalRole.INSTITUTION_ROLE_FACULTY,
				BbPortalRole.INSTITUTION_ROLE_19
				});
		assertNotNull(roles);
		assertTrue(roles.length==3);
		assertNotNull(roles[0].getId()); //this is the internal Blackboard ID retrieved from the server
		assertNotNull(roles[1].getId()); //this is the internal Blackboard ID retrieved from the server
		assertNotNull(roles[2].getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Test
	public void testRetrieve_STUDENT() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_STUDENT);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_STUDENT, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server  
	}
	
	@Test
	public void testRetrieve_FACULTY() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_FACULTY);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_FACULTY, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Test
	public void testRetrieve_STAFF() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_STAFF);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_STAFF, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Test
	public void testRetrieve_ALUMNI() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_ALUMNI);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_ALUMNI, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Test
	public void testRetrieve_PROSPECTIVE_STUDENT() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_PROSPECTIVE_STUDENT);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_PROSPECTIVE_STUDENT, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Test
	public void testRetrieve_GUEST() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_GUEST);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_GUEST, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Test
	public void testRetrieve_OTHER() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_OTHER);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_OTHER, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Test
	public void testRetrieve_OBSERVER() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_OBSERVER);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_OBSERVER, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Ignore
	public void testRetrieve_ROLE_9() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_9);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_9, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Ignore
	public void testRetrieve_ROLE_10() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_10);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_10, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Ignore
	public void testRetrieve_ROLE_11() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_11);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_11, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Ignore
	public void testRetrieve_ROLE_12() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_12);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_12, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Ignore
	public void testRetrieve_ROLE_13() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_13);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_13, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Ignore
	public void testRetrieve_ROLE_14() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_14);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_14, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Ignore
	public void testRetrieve_ROLE_15() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_15);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_15, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Ignore
	public void testRetrieve_ROLE_16() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_16);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_16, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Ignore
	public void testRetrieve_ROLE_17() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_17);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_17, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Ignore
	public void testRetrieve_ROLE_18() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_18);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_18, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Ignore
	public void testRetrieve_ROLE_19() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_19);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_19, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}
	
	@Ignore
	public void testRetrieve_ROLE_20() {
		BbPortalRole bbpr = new BbPortalRole();
		bbpr.setRoleId(BbPortalRole.INSTITUTION_ROLE_20);
		bbpr = (BbPortalRole) bbpr.retrieve();
		assertNotNull(bbpr);
		assertEquals(BbPortalRole.INSTITUTION_ROLE_20, bbpr.getRoleId());
		assertNotNull(bbpr.getId()); //this is the internal Blackboard ID retrieved from the server
	}	
}
