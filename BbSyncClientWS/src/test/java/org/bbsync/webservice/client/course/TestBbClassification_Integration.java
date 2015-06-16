package org.bbsync.webservice.client.course;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbClassification_Integration {
	private static final Logger logger = Logger.getLogger(TestBbClassification_Integration.class.getName());
	
	private static final String CLASSIFICATION_ID = "_113_1";
	private static final String CLASSIFICATION_BATCH_UID = "Education:HigherEducation";

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
        BbClassification bb_class = new BbClassification();
        Long version = bb_class.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
    
    @Ignore
    public void testInitializeCourseWS() {
    	BbClassification bb_class = new BbClassification();
        assertTrue(bb_class.initializeCourseWS());
        logger.debug("initialize Course web service succeded.");
    }

	@Test
	public void testRetrieve_id() {
		BbClassification bb_class = new BbClassification();
		bb_class.setId(CLASSIFICATION_ID);
		bb_class = (BbClassification) bb_class.retrieve();
		assertNotNull(bb_class);
	}

	@Test
	public void testRetrieve_batch_uid() {
		BbClassification bb_class = new BbClassification();
		bb_class.setBatchUid(CLASSIFICATION_BATCH_UID);
		bb_class = (BbClassification) bb_class.retrieve();
		assertNotNull(bb_class);

	}
	
	@Test
	public void testGetClassifications() {
		BbClassification bb_class = new BbClassification();
		BbClassification[] results = bb_class.getClassifications();
		assertNotNull(results);
		assertTrue(results.length>0);
	}
	
	@Test
	public void testGetClassificationByBatchId()
	{
		BbClassification bb_class = new BbClassification();
		BbClassification result = bb_class.getClassificationById(CLASSIFICATION_ID);
		assertNotNull(result);
	}
	
	@Test
	public void testGetClassificationByBatchUid()
	{
		BbClassification bb_class = new BbClassification();
		BbClassification result = bb_class.getClassificationByBatchUid(CLASSIFICATION_BATCH_UID);
		assertNotNull(result);
	}
	
	@Test
	public void testFindClassifications() {
		BbClassification bb_class = new BbClassification();
		BbClassification[] results = bb_class.getClassifications();
		assertNotNull(results);
		for(BbClassification result: results){
			logger.info(result.getBatchUid());
		}
	}

}
