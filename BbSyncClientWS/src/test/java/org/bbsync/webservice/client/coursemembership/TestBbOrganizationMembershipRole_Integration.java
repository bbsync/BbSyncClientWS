package org.bbsync.webservice.client.coursemembership;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.coursemembership.BbOrganizationMembershipRole;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbOrganizationMembershipRole_Integration {
	private static final Logger logger = Logger.getLogger(TestBbOrganizationMembershipRole_Integration.class.getName());
	private BbOrganizationMembershipRole bbmr = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bbmr = new BbOrganizationMembershipRole();
	}

	@After
	public void tearDown() throws Exception {
		bbmr = null;
	}

	@Ignore
    public void testGetServerVersion() {
		BbOrganizationMembershipRole role = new BbOrganizationMembershipRole();
        Long version = role.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
    
    @Ignore
    public void testInitializeCourseMembershipWS() {
    	BbOrganizationMembershipRole role = new BbOrganizationMembershipRole();
        assertTrue(role.initializeCourseMembershipWS());
        logger.debug("initialize Course web service succeded.");
    }
    
    @Test
    public void testRetrieve(){
    	bbmr.setRoleIdentifier(BbOrganizationMembershipRole.ORG_ROLE_PARTICIPANT);
    	bbmr = (BbOrganizationMembershipRole) bbmr.retrieve();
    	assertNotNull(bbmr);
    }
    
    @Test
    public void testGetAllMembershipRoles(){
    	BbOrganizationMembershipRole[] roles = bbmr.getAllMembershipRoles();
    	assertNotNull(roles);
    	assertTrue(roles.length>=6); //there are 6 default roles
    }
    
    @Test
    public void testGetMembershipRolesByIds(){
    	BbOrganizationMembershipRole[] roles = bbmr.getMembershipRolesByIds(new String[]{BbOrganizationMembershipRole.ORG_ROLE_PARTICIPANT, BbOrganizationMembershipRole.ORG_ROLE_LEADER});
    	assertNotNull(roles);
    	assertEquals(2, roles.length);
    }

}
