package org.bbsync.webservice.client.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.coursemembership.BbOrganizationMembership;
import org.bbsync.webservice.client.coursemembership.BbOrganizationMembershipRole;
import org.bbsync.webservice.client.user.BbUser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbOrganization_Integration {
	private static final Logger logger = Logger.getLogger(TestBbOrganization_Integration.class.getName());
	private static final String BASIC_ORG_NAME = "Test Organization #1";
	private static final String BASIC_ORG_ID = "tst_org_1";
	private static final String BASIC_ORG_DESCRIPTION = "This is a test organization."; //not required to create an Organization
	private String organization_id = null;
	//Term Information
	private String basic_term_id = null;
	private static final String TERM_NAME = "Test Term #1";
	private static final String TERM_SOURCEDID = "test_term_1";
	//Category Information
	private String basic_category_id = null;
	private static final String CATEGORY_TITLE = "Test Category #1";
	private static final String CATEGORY_BATCH_UID = "test_cat_1";
	//User Information
	private static final String USER_FIRST_NAME = "FIRSTNAME";
	private static final String USER_LAST_NAME = "LASTNAME";
	private static final String USER_USERNAME = "USERNAME";
	private static final String USER_PASSWORD = "PASSWORD";
    private String basic_user_id = null;
    //Organization Membership
    private String leader_membership_id = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		createBasicOrganization();
	}

	@After
	public void tearDown() throws Exception {
		deleteOrganization(organization_id);
	}
	
    @Ignore
    public void testGetServerVersion() {
        BbOrganization bb_org = new BbOrganization();
        Long version = bb_org.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
    
    @Ignore
    public void testInitializeCourseWS() {
    	BbOrganization bb_org = new BbOrganization();
        assertTrue(bb_org.initializeCourseWS());
        logger.debug("initialize Course web service succeded.");
    }
    
    @Test
    public void testPersist_new(){
    	 BbOrganization bb_org = new BbOrganization();
         bb_org.setOrganizationId("tst_prst_org");
         bb_org.setName("Test Persist Organization");
         String id = (String) bb_org.persist();
         assertNotNull(id);
         deleteOrganization(id);
    }
    
    @Test
    public void testPersist_update(){
        BbOrganization bb_org = new BbOrganization();
        bb_org.setId(organization_id);
        bb_org = (BbOrganization) bb_org.retrieve();
        assertEquals(BASIC_ORG_ID,bb_org.getBatchUid());
        bb_org.setBatchUid("tst_batch_uid_1"); //update a populated field
        bb_org.setDescription("This is a test description"); //populate a blank field
        String id = (String) bb_org.persist();
        assertNotNull(id);
        assertEquals(organization_id, id);
        //retrieve the updated org & check variables
        bb_org = (BbOrganization) bb_org.retrieve();
        assertNotNull(bb_org);
        assertEquals(BASIC_ORG_ID, bb_org.getOrganizationId());
        assertEquals(BASIC_ORG_NAME, bb_org.getName());
        assertEquals("tst_batch_uid_1", bb_org.getBatchUid());
        assertEquals("This is a test description", bb_org.getDescription());
    }
    
    @Test
	public void testRetrieve_id() {
        BbOrganization bb_org = new BbOrganization();
        bb_org.setId(organization_id);
        bb_org = (BbOrganization) bb_org.retrieve();
        assertNotNull(bb_org);
        assertEquals(BASIC_ORG_ID, bb_org.getOrganizationId());
        assertEquals(BASIC_ORG_ID, bb_org.getBatchUid());
        assertEquals(BASIC_ORG_NAME, bb_org.getName());
        assertEquals(organization_id, bb_org.getId());
	}

    @Test
	public void testRetrieve_org_id() {
        BbOrganization bb_org = new BbOrganization();
        bb_org.setOrganizationId(BASIC_ORG_ID);
        bb_org = (BbOrganization) bb_org.retrieve();
        assertNotNull(bb_org);
        assertEquals(BASIC_ORG_ID, bb_org.getOrganizationId());
        assertEquals(BASIC_ORG_ID, bb_org.getBatchUid());
        assertEquals(BASIC_ORG_NAME, bb_org.getName());
        assertEquals(organization_id, bb_org.getId());
	}
    
    @Test
	public void testRetrieve_batch_uid() {
        BbOrganization bb_org = new BbOrganization();
        bb_org.setBatchUid(BASIC_ORG_ID);
        bb_org = (BbOrganization) bb_org.retrieve();
        assertNotNull(bb_org);
        assertEquals(BASIC_ORG_ID, bb_org.getOrganizationId());
        assertEquals(BASIC_ORG_ID, bb_org.getBatchUid());
        assertEquals(BASIC_ORG_NAME, bb_org.getName());
        assertEquals(organization_id, bb_org.getId());
	}

    @Test
	public void testDelete() {
		BbOrganization bb_org = new BbOrganization();
        bb_org.setOrganizationId("tst_prst_org");
        bb_org.setName("Test Persist Organization");
        String id = (String) bb_org.persist();
        assertNotNull(id);
        bb_org.setId(id); //setId() to use delete()
        assertTrue(bb_org.delete());
	}

	@Test
	public void testChangeOrgBatchUid() {
		BbOrganization bb_org = new BbOrganization();
		assertTrue(bb_org.changeOrgBatchUid(BASIC_ORG_ID, "new_batch_uid"));
		bb_org.setId(organization_id);
		bb_org = (BbOrganization) bb_org.retrieve();
		assertNotNull(bb_org);
		assertEquals("new_batch_uid", bb_org.getBatchUid());
	}

	@Ignore
	public void testChangeOrgDataSourceId() {
		/*
		 * There's currently no way via web services to create & delete 
		 * data sources for testing.  This test will need to be performed
		 * manually.
		 */
		assertTrue(true);
	}
	
	@Test
	public void testCreateOrg() {
		BbOrganization bb_org = new BbOrganization();
        bb_org.setOrganizationId("tst_create_org");
        bb_org.setName("Test Create Organization");
        String id = bb_org.createOrg(bb_org);
        assertNotNull(id);
        deleteOrganization(id);
	}
	
	@Test
	public void testDeleteOrg() {
		BbOrganization bb_org = new BbOrganization();
        bb_org.setOrganizationId("tst_create_org");
        bb_org.setName("Test Create Organization");
        String id = bb_org.createOrg(bb_org);
        assertNotNull(id);
        String[] results = bb_org.deleteOrg(new String[]{id});
        assertNotNull(results);
        assertEquals(id, results[0]);
	}	

	@Test
	public void testAddOrgToTerm() {
		createBasicTerm();
		BbOrganization bb_org = new BbOrganization();
		assertTrue(bb_org.addOrgToTerm(organization_id, basic_term_id));
		deleteTerm(basic_term_id);
	}

	@Test
	public void testGetAllOrganizations() {
		BbOrganization bb_org = new BbOrganization();
    	BbOrganization[] bb_orgs = bb_org.getAllOrganizations();
    	assertNotNull(bb_orgs);
    	assertTrue(bb_orgs.length>=1);
	}
	
	@Test
	public void testGetOrganizationByOrgId() {
		BbOrganization bb_org = new BbOrganization();
    	bb_org = bb_org.getOrganizationByOrgId(BASIC_ORG_ID);
    	assertNotNull(bb_org);
        assertEquals(BASIC_ORG_ID, bb_org.getOrganizationId());
        assertEquals(BASIC_ORG_ID, bb_org.getBatchUid());
        assertEquals(BASIC_ORG_NAME, bb_org.getName());
        assertEquals(organization_id, bb_org.getId());
	}

	@Test
	public void testGetOrganizationByBatchUid() {
		BbOrganization bb_org = new BbOrganization();
    	bb_org = bb_org.getOrganizationByBatchUid(BASIC_ORG_ID);
    	assertNotNull(bb_org);
        assertEquals(BASIC_ORG_ID, bb_org.getOrganizationId());
        assertEquals(BASIC_ORG_ID, bb_org.getBatchUid());
        assertEquals(BASIC_ORG_NAME, bb_org.getName());
        assertEquals(organization_id, bb_org.getId());
	}

	@Test
	public void testGetOrganizationById() {
		BbOrganization bb_org = new BbOrganization();
    	bb_org = bb_org.getOrganizationById(organization_id);
    	assertNotNull(bb_org);
        assertEquals(BASIC_ORG_ID, bb_org.getOrganizationId());
        assertEquals(BASIC_ORG_ID, bb_org.getBatchUid());
        assertEquals(BASIC_ORG_NAME, bb_org.getName());
        assertEquals(organization_id, bb_org.getId());
	}

	@Test
	public void testGetOrganizationByCategoryId() {
		createBasicOrgCategory();
		BbOrgCategoryMembership bb_ocm = new BbOrgCategoryMembership();
		bb_ocm.setCategoryId(basic_category_id);
		bb_ocm.setOrganizationId(organization_id);
		String id = (String) bb_ocm.persist();
		assertNotNull(id);
		bb_ocm = new BbOrgCategoryMembership();
		bb_ocm.setId(id);
		assertTrue(bb_ocm.delete());
		deleteOrgCategory(basic_category_id);
	}

	@Test
	public void testGetOrganizationBySearchValue_org_id() {
		BbOrganization bb_org = new BbOrganization();
		BbOrganization[] result = bb_org.getOrgsBySearchValue(BASIC_ORG_ID, BbOrganization.SEARCH_KEY_ORGANIZATION_ID);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(organization_id, result[0].getId());
		assertEquals(BASIC_ORG_ID, result[0].getOrganizationId());
		assertEquals(BASIC_ORG_NAME, result[0].getName());
	}

	@Test
	public void testGetOrganizationBySearchValue_org_name() {
		BbOrganization bb_org = new BbOrganization();
		BbOrganization[] result = bb_org.getOrgsBySearchValue(BASIC_ORG_NAME, BbOrganization.SEARCH_KEY_ORGANIZATION_NAME);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(organization_id, result[0].getId());
		assertEquals(BASIC_ORG_ID, result[0].getOrganizationId());
		assertEquals(BASIC_ORG_NAME, result[0].getName());
	}

	@Test
	public void testGetOrganizationBySearchValue_description() {
		BbOrganization bb_org = new BbOrganization();
		BbOrganization[] result = bb_org.getOrgsBySearchValue("test organization", BbOrganization.SEARCH_KEY_ORGANIZATION_DESCRIPTION);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(organization_id, result[0].getId());
		assertEquals(BASIC_ORG_ID, result[0].getOrganizationId());
		assertEquals(BASIC_ORG_NAME, result[0].getName());
		assertEquals(BASIC_ORG_DESCRIPTION, result[0].getDescription());
	}

	@Test
	public void testGetOrganizationBySearchValue_leader() {
		createBasicUser();
		createLeaderMembership();
		BbOrganization bb_org = new BbOrganization();
		BbOrganization[] result = bb_org.getOrgsBySearchValue(USER_USERNAME, BbOrganization.SEARCH_KEY_ORGANIZATION_LEADER_USERNAME);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(organization_id, result[0].getId());
		assertEquals(BASIC_ORG_ID, result[0].getOrganizationId());
		assertEquals(BASIC_ORG_NAME, result[0].getName());
		assertEquals(BASIC_ORG_DESCRIPTION, result[0].getDescription());
		deleteLeaderMembership();
		deleteUser();
	}

	@Test
	public void testGetOrganizationBySearchValue_contains() {
		BbOrganization bb_org = new BbOrganization();
		BbOrganization[] result = bb_org.getOrgsBySearchValue("is a test", BbOrganization.SEARCH_KEY_ORGANIZATION_DESCRIPTION, BbOrganization.SEARCH_OPERATOR_CONTAINS);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(organization_id, result[0].getId());
		assertEquals(BASIC_ORG_ID, result[0].getOrganizationId());
		assertEquals(BASIC_ORG_NAME, result[0].getName());
		assertEquals(BASIC_ORG_DESCRIPTION, result[0].getDescription());
	}
	
	@Test
	public void testGetOrganizationBySearchValue_equals() {
		BbOrganization bb_org = new BbOrganization();
		BbOrganization[] result = bb_org.getOrgsBySearchValue(BASIC_ORG_DESCRIPTION, BbOrganization.SEARCH_KEY_ORGANIZATION_DESCRIPTION, BbOrganization.SEARCH_OPERATOR_EQUALS);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(organization_id, result[0].getId());
		assertEquals(BASIC_ORG_ID, result[0].getOrganizationId());
		assertEquals(BASIC_ORG_NAME, result[0].getName());
		assertEquals(BASIC_ORG_DESCRIPTION, result[0].getDescription());
	}

	@Test
	public void testGetOrganizationBySearchValue_is_not_blank() {
		BbOrganization bb_org = new BbOrganization();
		BbOrganization[] result = bb_org.getOrgsBySearchValue(BASIC_ORG_DESCRIPTION, BbOrganization.SEARCH_KEY_ORGANIZATION_DESCRIPTION, BbOrganization.SEARCH_OPERATOR_IS_NOT_BLANK);
		assertNotNull(result);
		assertTrue(result.length>=1);
	}

	@Test
	public void testGetOrganizationBySearchValue_starts_with() {
		BbOrganization bb_org = new BbOrganization();
		BbOrganization[] result = bb_org.getOrgsBySearchValue("This is a test", BbOrganization.SEARCH_KEY_ORGANIZATION_DESCRIPTION, BbOrganization.SEARCH_OPERATOR_STARTS_WITH);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(organization_id, result[0].getId());
		assertEquals(BASIC_ORG_ID, result[0].getOrganizationId());
		assertEquals(BASIC_ORG_NAME, result[0].getName());
		assertEquals(BASIC_ORG_DESCRIPTION, result[0].getDescription());
	}

	@Test
	public void testUpdateOrg(){
		//retrieve the test organization 
		BbOrganization bb_org = new BbOrganization();
		bb_org.setId(organization_id);
		bb_org = (BbOrganization) bb_org.retrieve();
		assertNotNull(bb_org);
		//check basic values
		assertEquals(organization_id, bb_org.getId());
		assertEquals(BASIC_ORG_ID, bb_org.getOrganizationId());
		assertEquals(BASIC_ORG_NAME, bb_org.getName());
		assertEquals(BASIC_ORG_DESCRIPTION, bb_org.getDescription());
		//update some values
		bb_org.setDescription("This is an updated Description.");
		bb_org.setInstitutionName("institution_name");
		String result = bb_org.updateOrganization(bb_org);
		assertEquals(organization_id, result);
		//retrieve the test organization 
		bb_org = new BbOrganization();
		bb_org.setId(organization_id);
		bb_org = (BbOrganization) bb_org.retrieve();
		assertNotNull(bb_org);
		//re-check all values
		assertEquals(organization_id, bb_org.getId());
		assertEquals(BASIC_ORG_ID, bb_org.getOrganizationId());
		assertEquals(BASIC_ORG_NAME, bb_org.getName());
		assertEquals("This is an updated Description.", bb_org.getDescription());
		assertEquals("institution_name", bb_org.getInstitutionName());
	}
	
	private void deleteOrganization(String id) {
        BbOrganization bb_org = new BbOrganization();
        String[] results = bb_org.deleteOrg(new String[]{id});
        assertNotNull(results);
        assertEquals(id, results[0]);
        if(organization_id==id) organization_id=null;
        logger.debug("Deleted Organization with ID: " + id);
    }
    
    private void createBasicOrganization(){
        BbOrganization bb_org = new BbOrganization();
        bb_org.setOrganizationId(BASIC_ORG_ID);
        bb_org.setName(BASIC_ORG_NAME);
        bb_org.setDescription(BASIC_ORG_DESCRIPTION); //not required to create an Organization
        String id = bb_org.createOrg(bb_org);
        assertNotNull(id);
        organization_id = id;
        logger.debug("Created Organization with ID: " + id);
    }
    
    private void deleteTerm(String id){
    	BbTerm bb_term = new BbTerm();
    	bb_term.setId(id);
    	assertTrue(bb_term.delete());
    	logger.debug("Deleted Term with ID: " + id);
    }
    
    private void createBasicTerm(){
    	BbTerm bb_term = new BbTerm();
    	bb_term.setName(TERM_NAME);
    	bb_term.setSourcedidId(TERM_SOURCEDID);
    	bb_term.setDuration(BbTerm.DURATION_CONTINUOUS);
    	String id = (String) bb_term.persist();
    	assertNotNull(id);
    	basic_term_id = id;
    	logger.debug("Created Term with ID: " + id);
    }
    
    private void createBasicOrgCategory(){
		BbOrgCategory bb_category = new BbOrgCategory();
		bb_category.setTitle(CATEGORY_TITLE);
		bb_category.setBatchUid(CATEGORY_BATCH_UID);
		bb_category.setOrganization(true);
		String id = bb_category.saveOrgCategory(bb_category);
		assertNotNull(id);
		basic_category_id = id;
	}
		
	private String[] deleteOrgCategory(String id){
		BbOrgCategory bb_category = new BbOrgCategory();
		return bb_category.deleteOrgCategory(new String[]{id});
	}
	
    private void createBasicUser(){
    	BbUser bb_user = new BbUser();
        bb_user.setGivenName(USER_FIRST_NAME);
        bb_user.setFamilyName(USER_LAST_NAME);
        bb_user.setName(USER_USERNAME);
        bb_user.setPassword(USER_PASSWORD);
        String id = (String) bb_user.persist();
        assertNotNull(id);
        basic_user_id = id;
    }
    
    private void deleteUser(){
    	BbUser bb_user = new BbUser();
        bb_user.setId(basic_user_id);
        assertTrue(bb_user.delete());
    }
    
    private void createLeaderMembership(){
    	BbOrganizationMembership bbcm = new BbOrganizationMembership();
		bbcm.setOrganizationId(organization_id);
		bbcm.setUserId(basic_user_id);
		bbcm.setAvailable(true);
		bbcm.setRoleId(BbOrganizationMembershipRole.ORG_ROLE_LEADER);		
		String id = (String)bbcm.persist();
		assertNotNull(id);
		leader_membership_id = id;
    }
    
    private void deleteLeaderMembership(){
    	BbOrganizationMembership bbcm = new BbOrganizationMembership();
		bbcm.setOrganizationId(organization_id);
		bbcm.setId(leader_membership_id);
		assertTrue(bbcm.delete());
    }
}
