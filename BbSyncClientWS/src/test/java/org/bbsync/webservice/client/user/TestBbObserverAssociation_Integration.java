package org.bbsync.webservice.client.user;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbObserverAssociation_Integration {
	
	private static final Logger logger = Logger.getLogger(TestBbUserRole_Integration.class.getName());
	private String observer_user_id     = null;
	private String observee_user_id_1   = null;
	private String observee_user_id_2   = null;
	private String observee_user_name_1 = null;
	private String observee_user_name_2 = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		createObserverUser();
		createBasicUser2();
		createBasicUser3();
	}

	@After
	public void tearDown() throws Exception {
		deleteUser(observer_user_id);
		deleteUser(observee_user_id_1);
		deleteUser(observee_user_id_2);
	}

	@Ignore
	public void testGetServerVersion() {
		BbObserverAssociation obs = new BbObserverAssociation();
		Long version = obs.getServerVersion();
        assertNotNull(version);
        assertTrue(version>0L);
        logger.debug("Server Version: " + String.valueOf(version));
	}

	@Ignore
	public void testInitializeUserWS() {
		BbObserverAssociation obs = new BbObserverAssociation();
        assertTrue(obs.initializeUserWS());
        logger.debug("initialize User web service succeded.");
	}	
	
	@Ignore
	public void testPersist_single() {
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setObserverId(observer_user_id);
		obs.setObserveeIds(new String[]{observee_user_id_1});
		String obs_id = (String)obs.persist();
		assertNotNull(obs_id);
	}
	
	/**
	 * By definition, the persist() method of any BbSync object will only save
	 * changes to itself.  This "multi" method demonstrates that you can 
	 * persist a BbObserverAssociation object using multiple observees - and 
	 * only get back a single ID (unlike the underlying save method that returns
	 * the same ID multiple times for each observee).  
	 */
	@Ignore
	public void testPersist_multi_observee() {
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setObserverId(observer_user_id);
		obs.setObserveeIds(new String[]{observee_user_id_1, observee_user_id_2});
		String obs_id = (String)obs.persist();
		assertNotNull(obs_id);
	}

	@Ignore
	public void testRetrieve_single() {
		//create an observer association
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setObserverId(observer_user_id);
		obs.setObserveeIds(new String[]{observee_user_id_1});
		String obs_id = (String)obs.persist();
		assertNotNull(obs_id);
		//fill in an observer association "template" and retrieve
		obs = new BbObserverAssociation();
		obs.setObserverId(observer_user_id);
		BbObserverAssociation[] obs_array = (BbObserverAssociation[]) obs.retrieve();
		//check for results 
		assertNotNull(obs_array);
		assertEquals(1, obs_array.length);
		BbObserverAssociation result = obs_array[0];
		assertNotNull(result);
		//test for observer
		assertEquals(observer_user_id,result.getObserverId());
		//test for observees by ID
		String[] observee_ids = result.getObserveeId();
		assertNotNull(observee_ids);
		assertEquals(1, observee_ids.length);
		assertTrue(observee_user_id_1.equals(observee_ids[0]));
		//test for observees by name
		String[] observee_names = result.getObserveeIdByName();
		assertNotNull(observee_names);
		assertEquals(1, observee_names.length);
		assertTrue(observee_user_name_1.equals(observee_names[0]));
	}

	@Ignore
	public void testRetrieve_multi_observee() {
		//create an observer association
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setObserverId(observer_user_id);
		obs.setObserveeIds(new String[]{observee_user_id_1, observee_user_id_2});
		String obs_id = (String)obs.persist();
		assertNotNull(obs_id);
		//fill in an observer association "template" and retrieve
		obs = new BbObserverAssociation();
		obs.setObserverId(observer_user_id);
		BbObserverAssociation[] obs_array = (BbObserverAssociation[]) obs.retrieve();
		//check for results 
		assertNotNull(obs_array);
		assertEquals(1, obs_array.length);
		BbObserverAssociation result = obs_array[0];
		assertNotNull(result);
		//test for observer
		assertEquals(observer_user_id,result.getObserverId());
		//test for observees by ID
		String[] observee_ids = result.getObserveeId();
		assertNotNull(observee_ids);
		assertEquals(2, observee_ids.length);
		assertTrue(observee_user_id_1.equals(observee_ids[0]) || observee_user_id_1.equals(observee_ids[1]));
		assertTrue(observee_user_id_2.equals(observee_ids[0]) || observee_user_id_2.equals(observee_ids[1]));
		//test for observees by name
		String[] observee_names = result.getObserveeIdByName();
		assertNotNull(observee_names);
		assertEquals(2, observee_names.length);
		assertTrue(observee_user_name_1.equals(observee_names[0]) || observee_user_name_1.equals(observee_names[1]));
		assertTrue(observee_user_name_2.equals(observee_names[0]) || observee_user_name_2.equals(observee_names[1]));
	}

	@Test
	public void testDelete_single() {
		//first, create an Observer Association
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setObserveeIds(new String[]{observee_user_id_1});
		obs.setObserverId(observer_user_id);
		String obs_id = (String)obs.persist();
		assertNotNull(obs_id);
		//Next recreate the observer association with only the "template" data
		obs = new BbObserverAssociation();
		obs.setObserveeIds(new String[]{observee_user_id_1});
		obs.setObserverId(observer_user_id);
		//next, delete the Observer Association 
		boolean result = obs.delete();
		assertTrue(result);
	}

	@Test
	public void testDelete_multi_observee() {
		//first, create an Observer Association
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setObserveeIds(new String[]{observee_user_id_1, observee_user_id_2});
		obs.setObserverId(observer_user_id);
		String obs_id = (String)obs.persist();
		assertNotNull(obs_id);
		//Next recreate the observer association with only the "template" data
		obs = new BbObserverAssociation();
		obs.setObserveeIds(new String[]{observee_user_id_1, observee_user_id_2});
		obs.setObserverId(observer_user_id);
		//next, delete the Observer Association 
		boolean result = obs.delete();
		assertTrue(result);
	}

	@Ignore
	public void testGetObserveeAssociations_single() {
		//first, create an Observer Association
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setObserverId(observer_user_id);
		obs.setObserveeIds(new String[]{observee_user_id_1});
		String obs_id = (String)obs.persist();
		assertNotNull(obs_id);
		//next, get the observee associations 
		BbObserverAssociation[] results = obs.getObserveeAssociations(new String[]{observer_user_id});
		assertNotNull(results);
		assertEquals(1, results.length);
		BbObserverAssociation result = results[0];
		//test the result - observer IDs should match
		assertNotNull(result);
		assertNotNull(result.getObserverId());
		assertEquals(observer_user_id, result.getObserverId());
		//test for observees by ID
		String[] observee_ids = result.getObserveeId();
		assertNotNull(observee_ids);
		assertEquals(1, observee_ids.length);
		assertTrue(observee_user_id_1.equals(observee_ids[0]));
		//test for observees by name
		String[] observee_names = result.getObserveeIdByName();
		assertNotNull(observee_names);
		assertEquals(1, observee_names.length);
		assertTrue(observee_user_name_1.equals(observee_names[0]));
	}
	
	/**
	 * This test shows that when you create two different Observer 
	 * Associations, if the observer user of each association is the same,
	 * the associations will actually get saved to the same observer
	 * association.  That is, in this test the observee specified in observer 
	 * association #2 will get added to observer association #1. 
	 */
	@Ignore
	public void testGetObserveeAssociations_multi_1() {
		//Observer Association #1
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setObserverId(observer_user_id);
		obs.setObserveeIds(new String[]{observee_user_id_1});
		String obs_id_1 = (String)obs.persist();
		assertNotNull(obs_id_1);
		//Observer Association #2
		obs = new BbObserverAssociation();
		obs.setObserverId(observer_user_id);
		obs.setObserveeIds(new String[]{observee_user_id_2});
		String obs_id_2 = (String)obs.persist();
		assertNotNull(obs_id_2);
		assertEquals(obs_id_1, obs_id_2);
		//next, get the observee associations 
		obs = new BbObserverAssociation();
		BbObserverAssociation[] results = obs.getObserveeAssociations(new String[]{observer_user_id});
		assertNotNull(results);
		assertEquals(1, results.length);
		//test the result - observer IDs should match
		BbObserverAssociation result = results[0];
		assertNotNull(result);
		assertNotNull(result.getObserverId());
		assertEquals(observer_user_id, result.getObserverId());
		//test for observees by ID
		String[] observee_ids = result.getObserveeId();
		assertNotNull(observee_ids);
		assertEquals(2, observee_ids.length);
		assertTrue(observee_user_id_1.equals(observee_ids[0]) || observee_user_id_1.equals(observee_ids[1]));
		assertTrue(observee_user_id_2.equals(observee_ids[0]) || observee_user_id_2.equals(observee_ids[1]));
		//test for observees by name
		String[] observee_names = result.getObserveeIdByName();
		assertNotNull(observee_names);
		assertEquals(2, observee_names.length);
		assertTrue(observee_user_name_1.equals(observee_names[0]) || observee_user_name_1.equals(observee_names[1]));
		assertTrue(observee_user_name_2.equals(observee_names[0]) || observee_user_name_2.equals(observee_names[1]));
	}

	/**
	 * This test proves that observer associations remain distinct if the
	 * observer users are different.
	 */
	@Ignore
	public void testGetObserveeAssociations_multi_2() {
		//Observer Association #1
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setObserverId(observer_user_id);
		obs.setObserveeIds(new String[]{observee_user_id_1});
		String obs_id_1 = (String)obs.persist();
		assertNotNull(obs_id_1);
		//Observer Association #2
		obs = new BbObserverAssociation();
		obs.setObserverId(observee_user_id_1);
		obs.setObserveeIds(new String[]{observee_user_id_2});
		String obs_id_2 = (String)obs.persist();
		assertNotNull(obs_id_2);
		//prove that the two observer association IDs are different
		assertNotEquals(obs_id_1, obs_id_2);
		//next, get the observee associations 
		obs = new BbObserverAssociation();
		BbObserverAssociation[] results = obs.getObserveeAssociations(new String[]{observer_user_id, observee_user_id_1});
		//prove that two associations were returned
		assertNotNull(results);
		assertEquals(2, results.length);
		assertNotNull(results[0]);
		assertNotNull(results[1]);
		//prove that the two associations have different observers
		assertNotEquals(results[0].getObserverId(), results[1].getObserverId());
	}
	
	@Ignore
	public void testSaveObserverAssociations_single() {
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setObserverId(observer_user_id);
		obs.setObserveeIds(new String[]{observee_user_id_1});
		String[] results = obs.saveObserverAssociations(new BbObserverAssociation[]{obs});
		assertNotNull(results);
		assertEquals(1,results.length);
		assertNotNull(results[0]);
	}
	
	/**
	 * This test shows that when you create two different Observer 
	 * Associations, if the observer user of each association is the same,
	 * the associations will actually get saved to the same observer
	 * association.  That is, the observer association IDs that are returned
	 * after the save operation are the same.
	 */
	@Ignore
	public void testSaveObserverAssociations_multi_1() {
		//OBS 1
		BbObserverAssociation obs1 = new BbObserverAssociation();
		obs1.setObserverId(observer_user_id);
		obs1.setObserveeIds(new String[]{observee_user_id_1});
		//OBS 2
		BbObserverAssociation obs2 = new BbObserverAssociation();
		obs2.setObserverId(observer_user_id);
		obs2.setObserveeIds(new String[]{observee_user_id_2});		
		BbObserverAssociation obs = new BbObserverAssociation();
		String[] results = obs.saveObserverAssociations(new BbObserverAssociation[]{obs1, obs2});
		assertNotNull(results);
		assertEquals(2, results.length);
		assertNotNull(results[0]);
		assertNotNull(results[1]);
		assertEquals(results[0], results[1]);
	}

	/**
	 * This test shows that when you create two different Observer 
	 * Associations, if the observer user of each association is not the same,
	 * the associations will get saved as different observer associations.  
	 * That is, the observer association IDs that are returned after the save 
	 * operation will be different.
	 */
	@Ignore
	public void testSaveObserverAssociations_multi_2() {
		//OBS 1
		BbObserverAssociation obs1 = new BbObserverAssociation();
		obs1.setObserverId(observer_user_id);
		obs1.setObserveeIds(new String[]{observee_user_id_1});
		//OBS 2
		BbObserverAssociation obs2 = new BbObserverAssociation();
		obs2.setObserverId(observee_user_id_1);
		obs2.setObserveeIds(new String[]{observee_user_id_2});		
		BbObserverAssociation obs = new BbObserverAssociation();
		String[] results = obs.saveObserverAssociations(new BbObserverAssociation[]{obs1, obs2});
		assertNotNull(results);
		assertEquals(2, results.length);
		assertNotNull(results[0]);
		assertNotNull(results[1]);
		assertNotEquals(results[0], results[1]);
	}
	
	@Ignore
	public void testDeleteObserverAssociations_single() {
		//first, create an Observer Association
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setObserveeIds(new String[]{observer_user_id});
		obs.setObserverId(observee_user_id_1);
		String obs_id = (String)obs.persist();
		assertNotNull(obs_id);
		//next, delete the Observer Association 
		String[] results = obs.deleteObserverAssociations(new BbObserverAssociation[]{obs});
		assertNotNull(results);
		assertEquals(1, results.length);
		//test the result - IDs from persist & delete should match
		String result = results[0];
		assertNotNull(result);
		assertEquals(obs_id, result);
	}
	
	@Ignore
	public void testDeleteObserverAssociations_multi() {
		//Observer Association #1
		BbObserverAssociation obs1 = new BbObserverAssociation();
		obs1.setObserveeIds(new String[]{observer_user_id});
		obs1.setObserverId(observee_user_id_1);
		String obs_id_1 = (String)obs1.persist();
		assertNotNull(obs_id_1);
		//Observer Association #2
		BbObserverAssociation obs2 = new BbObserverAssociation();
		obs2.setObserveeIds(new String[]{observee_user_id_1});
		obs2.setObserverId(observee_user_id_2);
		String obs_id_2 = (String)obs2.persist();
		assertNotNull(obs_id_2);
		assertNotEquals(obs_id_1, obs_id_2); //these IDs are different
		//next, delete both Observer Associations
		BbObserverAssociation obs = new BbObserverAssociation();
		String[] results = obs.deleteObserverAssociations(new BbObserverAssociation[]{obs1, obs2});
		assertNotNull(results);
		assertEquals(2, results.length);
		assertNotNull(results[0]);
		assertTrue(obs_id_1.equals(results[0]) || obs_id_1.equals(results[1]));
		assertTrue(obs_id_2.equals(results[0]) || obs_id_2.equals(results[1]));
	}

	
	private void createObserverUser(){
		BbUser bb_user = new BbUser();
	    bb_user.setGivenName("Johnnie");
	    bb_user.setFamilyName("Walker");
	    bb_user.setName("jw1111111");
	    bb_user.setPassword("password");
	    bb_user.setAvailable(Boolean.TRUE);
	    //IMPORTANT:  Set the primary system role to OBSERVER
	    bb_user.setSystemRoles(new String[]{BbUser.SYSTEM_ROLE_OBSERVER});
	    //IMPORTANT:  Set the primary portal/institution role to OBSERVER
	    bb_user.setInsRoles(new String[]{BbPortalRole.INSTITUTION_ROLE_OBSERVER});
	    observer_user_id = (String) bb_user.persist();
	    assertNotNull(observer_user_id);
	}
	
	private void createBasicUser2(){
		BbUser bb_user = new BbUser();
	    bb_user.setGivenName("Jack");
	    bb_user.setFamilyName("Daniels");
	    bb_user.setName("jd2222222");
	    bb_user.setPassword("password");
	    bb_user.setAvailable(Boolean.TRUE);
	    observee_user_id_1 = (String) bb_user.persist();
	    assertNotNull(observee_user_id_1);
	    observee_user_name_1 = bb_user.getName();
	}
	
	private void createBasicUser3(){
		BbUser bb_user = new BbUser();
	    bb_user.setGivenName("Jim");
	    bb_user.setFamilyName("Beam");
	    bb_user.setName("jb3333333");
	    bb_user.setPassword("password");
	    bb_user.setAvailable(Boolean.TRUE);
	    observee_user_id_2 = (String) bb_user.persist();
	    assertNotNull(observee_user_id_2);
	    observee_user_name_2 = bb_user.getName();
	}

	private void deleteUser(String bb_user_id){
		BbUser bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		assertTrue(bb_user.delete());
    }


}
