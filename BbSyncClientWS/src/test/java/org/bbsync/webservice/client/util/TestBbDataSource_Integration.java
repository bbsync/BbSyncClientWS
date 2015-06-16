/**
 * 
 */
package org.bbsync.webservice.client.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kurt
 *
 */
public class TestBbDataSource_Integration {

	private BbDataSource bbds = null;
	private static final Logger logger = Logger.getLogger(TestBbDataSource_Integration.class.getName());
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		bbds = new BbDataSource();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		bbds = null;
	}
	
	@Test
	public void testGetDataSources() {
	    BbDataSource[] dataSources = bbds.getAllDataSources();
	    assertNotNull(dataSources);
	    assertTrue(dataSources.length > 0);
	    for(int i=0; i<dataSources.length; i++){
	      	logger.info("dataSource[" + i + "] = " + dataSources[i].getBatchUid());
	    }
	}
	  
	
	@Test
	public void testRetrieve_Id(){
		//get a list of all the DataSources
		BbDataSource[] dataSources = bbds.getAllDataSources();
		assertNotNull(dataSources);
		//Retrieve each dataSource in the list separately 
		for(BbDataSource ds : dataSources) {
			BbDataSource bbds = new BbDataSource();
			bbds.setId(ds.getId());
			bbds = (BbDataSource) bbds.retrieve();
			assertNotNull(bbds);
			assertEquals(ds.getBatchUid(), bbds.getBatchUid());
			assertEquals(ds.getDescription(), bbds.getDescription());
		}
	}
	
	@Test
	public void testRetrieve_BatchUid(){
		//get a list of all the DataSources
		BbDataSource[] dataSources = bbds.getAllDataSources();
		assertNotNull(dataSources);
		//Retrieve each dataSource in the list separately
		for(BbDataSource ds : dataSources) {
			BbDataSource bbds = new BbDataSource();
			bbds.setBatchUid(ds.getBatchUid());
			bbds = (BbDataSource) bbds.retrieve();
			assertNotNull(bbds);
			assertEquals(ds.getId(), bbds.getId());
			assertEquals(ds.getDescription(), bbds.getDescription());
		}
	}
}
