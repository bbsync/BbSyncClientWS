package org.bbsync.webservice.client.coursemembership;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.coursemembership.BbCourseMembershipRole;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbCourseMembershipRole_Integration {
	private static final Logger logger = Logger.getLogger(TestBbCourseMembershipRole_Integration.class.getName());
	private BbCourseMembershipRole bbmr = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bbmr = new BbCourseMembershipRole();
	}

	@After
	public void tearDown() throws Exception {
		bbmr = null;
	}

	@Ignore
    public void testGetServerVersion() {
		BbCourseMembershipRole role = new BbCourseMembershipRole();
        Long version = role.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
    
    @Ignore
    public void testInitializeCourseMembershipWS() {
    	BbCourseMembershipRole role = new BbCourseMembershipRole();
        assertTrue(role.initializeCourseMembershipWS());
        logger.debug("initialize Course web service succeded.");
    }
    
    @Test
    public void testRetrieve(){
    	bbmr.setRoleIdentifier(BbCourseMembershipRole.COURSE_ROLE_STUDENT);
    	bbmr = (BbCourseMembershipRole) bbmr.retrieve();
    	assertNotNull(bbmr);
    }
    
    @Test
    public void testGetAllMembershipRoles(){
    	BbCourseMembershipRole[] roles = bbmr.getAllMembershipRoles();
    	assertNotNull(roles);
    	assertTrue(roles.length>=6); //there are 6 default roles
    }
    
    @Test
    public void testGetMembershipRolesByIds(){
    	BbCourseMembershipRole[] roles = bbmr.getMembershipRolesByIds(new String[]{BbCourseMembershipRole.COURSE_ROLE_STUDENT, BbCourseMembershipRole.COURSE_ROLE_INSTRUCTOR});
    	assertNotNull(roles);
    	assertEquals(2, roles.length);
    }

}
