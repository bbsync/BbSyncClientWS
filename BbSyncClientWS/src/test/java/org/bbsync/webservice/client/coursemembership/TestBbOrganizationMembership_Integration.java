package org.bbsync.webservice.client.coursemembership;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.course.BbOrganization;
import org.bbsync.webservice.client.coursemembership.BbOrganizationMembership;
import org.bbsync.webservice.client.coursemembership.BbOrganizationMembershipRole;
import org.bbsync.webservice.client.user.BbUser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbOrganizationMembership_Integration {
	private static final Logger logger = Logger.getLogger(TestBbOrganizationMembership_Integration.class.getName());
	
	private String basic_user_id_1 = null;
	private String basic_user_id_2 = null;
	
    private static final String ORG_ID = "tst_org_id";
    private static final String ORG_BATCH_UID = "org_batch_uid";
    private static final String ORG_NAME = "Test Organization #1";
    private static final String DESCRIPTION = "This is the description for Test Organization #1";
    private String basic_organization_id = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		createBasicUser1();
		createBasicOrganization();
	}

	@After
	public void tearDown() throws Exception {
		deleteUser(basic_user_id_1);
		if(basic_user_id_2 !=null)deleteUser(basic_user_id_2);
		deleteOrganization();
	}

	@Ignore
	public void testGetServerVersion() {
		BbOrganizationMembership bb_mem = new BbOrganizationMembership();
		Long version = bb_mem.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("CourseMembership web service version: " + version);
    }
	
	@Ignore
    public void testInitializeCourseWS(){
		BbOrganizationMembership bb_mem = new BbOrganizationMembership();
		boolean result = bb_mem.initializeCourseMembershipWS();
		assertTrue(result);
		logger.debug("Initialize CourseMembership web service succeded.");
    }
	
	@Test
	public void testPersist() {
		BbOrganizationMembership bbom = new BbOrganizationMembership();
		bbom.setOrganizationId(basic_organization_id);
		bbom.setUserId(basic_user_id_1);
		bbom.setAvailable(true);
		bbom.setRoleId(BbOrganizationMembershipRole.ORG_ROLE_PARTICIPANT);
		String result = (String)bbom.persist();
		assertNotNull(result);
	}
	
	@Test
	public void testRetrieve_MembershipId() {
		BbOrganizationMembership bbom = new BbOrganizationMembership();
		bbom.setOrganizationId(basic_organization_id);
		bbom.setUserId(basic_user_id_1);
		bbom.setAvailable(true);
		bbom.setRoleId(BbOrganizationMembershipRole.ORG_ROLE_PARTICIPANT);		
		String result = (String)bbom.persist();
		assertNotNull(result);
		bbom = new BbOrganizationMembership();
		bbom.setOrganizationId(basic_organization_id);
		bbom.setId(result);
		BbOrganizationMembership membership = (BbOrganizationMembership) bbom.retrieve();
		assertNotNull(membership);
		assertEquals(result, membership.getId());
	}

	@Test
	public void testRetrieve_UserId() {
		BbOrganizationMembership bbom = new BbOrganizationMembership();
		bbom.setOrganizationId(basic_organization_id);
		bbom.setUserId(basic_user_id_1);
		bbom.setAvailable(true);
		bbom.setRoleId(BbOrganizationMembershipRole.ORG_ROLE_PARTICIPANT);		
		String result = (String)bbom.persist();
		assertNotNull(result);
		bbom = new BbOrganizationMembership();
		bbom.setOrganizationId(basic_organization_id);
		bbom.setUserId(basic_user_id_1);
		BbOrganizationMembership membership = (BbOrganizationMembership) bbom.retrieve();
		assertNotNull(membership);
		assertEquals(result, membership.getId());
	}
	
	@Test
	public void testDelete() {
		BbOrganizationMembership bbom = new BbOrganizationMembership();
		bbom.setOrganizationId(basic_organization_id);
		bbom.setUserId(basic_user_id_1);
		bbom.setAvailable(true);
		bbom.setRoleId(BbOrganizationMembershipRole.ORG_ROLE_PARTICIPANT);		
		String result = (String)bbom.persist();
		assertNotNull(result);
		bbom = new BbOrganizationMembership();
		bbom.setOrganizationId(basic_organization_id);
		bbom.setId(result);
		assertTrue(bbom.delete());
	}

	@Test
	public void testGetOrgMembershipsByIds(){
		String om_id = createBasicOrgMembership_Participant();
		BbOrganizationMembership bbom = new BbOrganizationMembership();
		BbOrganizationMembership[] memberships = bbom.getOrgMembershipsByIds(basic_organization_id, new String[]{om_id});
		assertNotNull(memberships);
		assertEquals(om_id, memberships[0].getId());
	}
	
	@Test
	public void testGetOrgMembershipsByOrgIds(){
		createBasicUser2();
		String om_id = createBasicOrgMembership_Participant();
		String om_id2 = createBasicOrgMembership_Leader();
		BbOrganizationMembership bbom = new BbOrganizationMembership();
		BbOrganizationMembership[] memberships = bbom.getOrgMembershipsByOrgIds(basic_organization_id, new String[]{basic_organization_id});
		assertNotNull(memberships);
		assertEquals(2, memberships.length);
		for(BbOrganizationMembership membership:memberships){
			assertEquals(basic_organization_id, membership.getOrganizationId());
			String id = membership.getId();
			assertTrue(id.equals(om_id) || id.equals(om_id2));
		}
	}
	
	/**
	 * Although this test is functionally equivalent to 
	 * testGetOrgMembershipsByOrgIdandUserIds(),
	 * If the user invoking the test is a System Administrator, then
	 * basic_organization_id could be null.  This would then return all of the
	 * Organization Memberships for each of the User IDs provided, not
	 * just the Memberships from the single Organization specified by
	 * basic_organization_id
	 */
	@Test
	public void testGetMembershipsByUserIds(){
		createBasicUser2();
		String om_id1 = createBasicOrgMembership_Participant();
		String om_id2 = createBasicOrgMembership_Leader();
		BbOrganizationMembership bbom = new BbOrganizationMembership();
		BbOrganizationMembership[] memberships = bbom.getOrgMembershipsByUserIds(basic_organization_id, new String[]{basic_user_id_1, basic_user_id_2});
		assertEquals(2, memberships.length);
		for(BbOrganizationMembership membership:memberships){
			assertEquals(basic_organization_id, membership.getOrganizationId());
			String id = membership.getId();
			assertTrue(id.equals(om_id1) || id.equals(om_id2));
		}
	}
		
	@Test
	public void testGetOrgMembershipsByOrgIdandUserIds(){
		createBasicUser2();
		String om_id1 = createBasicOrgMembership_Participant();
		String om_id2 = createBasicOrgMembership_Leader();
		BbOrganizationMembership bbom = new BbOrganizationMembership();
		BbOrganizationMembership[] memberships = bbom.getOrgMembershipsByOrgIdAndUserIds(basic_organization_id, new String[]{basic_user_id_1, basic_user_id_2});
		assertEquals(2, memberships.length);
		for(BbOrganizationMembership membership:memberships){
			assertEquals(basic_organization_id, membership.getOrganizationId());
			String id = membership.getId();
			assertTrue(id.equals(om_id1) || id.equals(om_id2));
		}
	}
	
	@Test
	public void testgetOrgMembershipsByOrgIdAndRoleId(){
		createBasicUser2();
		String om_id1 = createBasicOrgMembership_Participant();
		String om_id2 = createBasicOrgMembership_Leader();
		BbOrganizationMembership bbom = new BbOrganizationMembership();
		BbOrganizationMembership[] memberships = bbom.getOrgMembershipsByOrgIdAndRoleId(basic_organization_id, new String[]{BbOrganizationMembershipRole.ORG_ROLE_PARTICIPANT});
		assertNotNull(memberships);
		assertEquals(1, memberships.length);
		assertTrue(memberships[0].getId().equals(om_id1));
		bbom = new BbOrganizationMembership();
		memberships = bbom.getOrgMembershipsByOrgIdAndRoleId(basic_organization_id, new String[]{BbOrganizationMembershipRole.ORG_ROLE_LEADER});
		assertNotNull(memberships);
		assertEquals(1, memberships.length);
		assertTrue(memberships[0].getId().equals(om_id2));
		bbom = new BbOrganizationMembership();
		memberships = bbom.getOrgMembershipsByOrgIdAndRoleId(basic_organization_id, new String[]{BbOrganizationMembershipRole.ORG_ROLE_PARTICIPANT, BbOrganizationMembershipRole.ORG_ROLE_LEADER});
		assertNotNull(memberships);
		assertEquals(2, memberships.length);
		assertTrue(memberships[0].getId().equals(om_id1) || memberships[0].getId().equals(om_id2));		
		assertTrue(memberships[1].getId().equals(om_id1) || memberships[1].getId().equals(om_id2));
	}
	
	@Test
	public void testsaveOrganizationMembership(){
		BbOrganizationMembership bbom = new BbOrganizationMembership();
		bbom.setOrganizationId(basic_organization_id);
		bbom.setUserId(basic_user_id_1);
		bbom.setAvailable(true);
		bbom.setRoleId(BbOrganizationMembershipRole.ORG_ROLE_PARTICIPANT);
		String[] result = bbom.saveOrganizationMembership(basic_organization_id, new BbOrganizationMembership[]{bbom});
		assertNotNull(result);
	}

	private String createBasicOrgMembership_Participant(){
		BbOrganizationMembership bbom = new BbOrganizationMembership();
		bbom.setOrganizationId(basic_organization_id);
		bbom.setUserId(basic_user_id_1);
		bbom.setAvailable(Boolean.TRUE);
		bbom.setRoleId(BbOrganizationMembershipRole.ORG_ROLE_PARTICIPANT);
		return (String)bbom.persist();
	}

	private String createBasicOrgMembership_Leader(){
		BbOrganizationMembership bbom = new BbOrganizationMembership();
		bbom.setOrganizationId(basic_organization_id);
		bbom.setUserId(basic_user_id_2);
		bbom.setAvailable(Boolean.TRUE);
		bbom.setRoleId(BbOrganizationMembershipRole.ORG_ROLE_LEADER);
		return (String)bbom.persist();	
	}

	private void createBasicUser1(){
		BbUser bb_user = new BbUser();
	    bb_user.setGivenName("Johnnie");
	    bb_user.setFamilyName("Walker");
	    bb_user.setName("jw1111111");
	    bb_user.setPassword("password");
	    bb_user.setAvailable(Boolean.TRUE);
	    basic_user_id_1 = (String) bb_user.persist();
	    assertNotNull(basic_user_id_1);
	}
	
	private void createBasicUser2(){
		BbUser bb_user = new BbUser();
	    bb_user.setGivenName("Jack");
	    bb_user.setFamilyName("Daniels");
	    bb_user.setName("jd2222222");
	    bb_user.setPassword("password");
	    bb_user.setAvailable(Boolean.TRUE);
	    basic_user_id_2 = (String) bb_user.persist();
	    assertNotNull(basic_user_id_2);
	}

	private void deleteUser(String bb_user_id){
		BbUser bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		assertTrue(bb_user.delete());
    }
	
	private void createBasicOrganization(){
        BbOrganization bb_org = new BbOrganization();
	    bb_org.setOrganizationId(ORG_ID);
	    bb_org.setName(ORG_NAME);
	    bb_org.setDescription(DESCRIPTION);
	    bb_org.setBatchUid(ORG_BATCH_UID);
	    bb_org.setAvailable(Boolean.TRUE);
	    basic_organization_id = (String) bb_org.persist();
	    assertNotNull(basic_organization_id);
	}
	
	private void deleteOrganization(){
		BbOrganization bb_org = new BbOrganization();
		bb_org.setId(basic_organization_id);
		assertTrue(bb_org.delete());
    }
}
