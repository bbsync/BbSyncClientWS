package org.bbsync.webservice.client.course;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbOrgCategoryMembership_Integration {
	private static final Logger logger = Logger.getLogger(TestBbOrgCategoryMembership_Integration.class.getName());
	
	private String org_category_membership_id = null;
	//Category Information
	private String basic_category_id = null;
	private static final String CATEGORY_TITLE = "Test Category #1";
	private static final String CATEGORY_BATCH_UID = "test_cat_1";
	//Organization Information
	private String basic_org_id = null;
	private static final String ORGANIZATION_ID = "tst_org_1";
	private static final String ORGANIZATION_NAME = "Test Organization #1";


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		createBasicOrg();
		createBasicOrgCategory();
	}

	@After
	public void tearDown() throws Exception {
		deleteOrgCategory(basic_category_id);
		deleteOrg(basic_org_id);
	}

    @Ignore
    public void testGetServerVersion() {
        BbOrgCategoryMembership bb_ocm = new BbOrgCategoryMembership();
        Long version = bb_ocm.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
    
    @Ignore
    public void testInitializeCourseWS() {
    	BbOrgCategoryMembership bb_ocm = new BbOrgCategoryMembership();
        assertTrue(bb_ocm.initializeCourseWS());
        logger.debug("initialize Course web service succeded.");
    }

	@Test
	public void testDelete() {
		createOrgCategoryMembership();
		BbOrgCategoryMembership bb_ocm = new BbOrgCategoryMembership();
		bb_ocm.setId(org_category_membership_id);
		assertTrue(bb_ocm.delete());
	}

	@Test
	public void testPersist() {
		BbOrgCategoryMembership bb_ocm = new BbOrgCategoryMembership();
		bb_ocm.setCategoryId(basic_category_id);
		bb_ocm.setOrganizationId(basic_org_id);
		org_category_membership_id = (String) bb_ocm.persist();
		assertNotNull(org_category_membership_id);
		deleteOrgCategoryMembership(org_category_membership_id);
	}

	@Test
	public void testRetrieve() {
		createOrgCategoryMembership();
		BbOrgCategoryMembership bb_ocm = new BbOrgCategoryMembership();
		bb_ocm.setId(org_category_membership_id);
		bb_ocm = (BbOrgCategoryMembership)bb_ocm.retrieve();
		assertNotNull(bb_ocm);
		assertEquals(org_category_membership_id, bb_ocm.getId());
		assertEquals(basic_category_id, bb_ocm.getCategoryId());
		assertEquals(basic_org_id, bb_ocm.getOrganizationId());
		deleteOrgCategoryMembership(org_category_membership_id);
	}

	@Test
	public void testGetOrgCategoryMembershipById() {
		createOrgCategoryMembership();
		BbOrgCategoryMembership bb_ocm = new BbOrgCategoryMembership();
		bb_ocm = bb_ocm.getOrgCategoryMembershipById(org_category_membership_id);
		assertNotNull(bb_ocm);
		assertEquals(org_category_membership_id, bb_ocm.getId());
		assertEquals(basic_category_id, bb_ocm.getCategoryId());
		assertEquals(basic_org_id, bb_ocm.getOrganizationId());
		deleteOrgCategoryMembership(org_category_membership_id);
	}

	@Test
	public void testGetOrgCategoryMembershipsByCourseId() {
		createOrgCategoryMembership();
		BbOrgCategoryMembership bb_ocm = new BbOrgCategoryMembership();
		BbOrgCategoryMembership[] result = bb_ocm.getOrgCategoryMembershipsByOrgId(basic_org_id);
		assertNotNull(result);
		assertEquals(1, result.length);
		assertEquals(org_category_membership_id, result[0].getId());
		assertEquals(basic_category_id, result[0].getCategoryId());
		assertEquals(basic_org_id, result[0].getOrganizationId());
	}
	
	@Test
	public void testDeleteOrgCategoryMembershipBbOrgCategoryMembership() {
		createOrgCategoryMembership();
		BbOrgCategoryMembership bb_ocm = new BbOrgCategoryMembership();
		String[] result = bb_ocm.deleteOrgCategoryMembership(new String[] {org_category_membership_id});
		assertNotNull(result);
		assertEquals(org_category_membership_id, result[0]);
		org_category_membership_id =null; 
	}
	
	@Test
	public void testSaveOrgCategoryMembershipBbOrgCategoryMembership() {
		BbOrgCategoryMembership bb_ocm = new BbOrgCategoryMembership();
		bb_ocm.setCategoryId(basic_category_id);
		bb_ocm.setOrganizationId(basic_org_id);
		org_category_membership_id = bb_ocm.saveOrgCategoryMembership(bb_ocm);
		assertNotNull(org_category_membership_id);
		deleteOrgCategoryMembership(org_category_membership_id);
	}
	
	private void createOrgCategoryMembership(){
		BbOrgCategoryMembership bb_ocm = new BbOrgCategoryMembership();
		bb_ocm.setCategoryId(basic_category_id);
		bb_ocm.setOrganizationId(basic_org_id);
		org_category_membership_id = bb_ocm.saveOrgCategoryMembership(bb_ocm);
		assertNotNull(org_category_membership_id);
	}
	
	private void deleteOrgCategoryMembership(String id){
		BbOrgCategoryMembership bb_ocm = new BbOrgCategoryMembership();
		String[] result = bb_ocm.deleteOrgCategoryMembership(new String[]{id});
		assertNotNull(result);
		assertEquals(id, result[0]);
		org_category_membership_id =null; 
	}
	
    private void createBasicOrg(){
    	BbOrganization bb_org = new BbOrganization();
    	bb_org.setOrganizationId(ORGANIZATION_ID);
    	bb_org.setName(ORGANIZATION_NAME);
    	String id = (String) bb_org.persist();
    	assertNotNull(id);
    	basic_org_id = id;
	}
		
	private void deleteOrg(String id){
		BbOrganization bb_org = new BbOrganization();
		bb_org.setId(id);
		assertTrue(bb_org.delete());
		basic_org_id = null;
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
		
	private void deleteOrgCategory(String id){
		BbOrgCategory bb_category = new BbOrgCategory();
		bb_category.setId(id);
		assertTrue(bb_category.delete());
		basic_category_id = null;
	}
}
