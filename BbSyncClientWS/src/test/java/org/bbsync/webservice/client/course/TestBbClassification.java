package org.bbsync.webservice.client.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbClassification {
	private BbClassification bb_class = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bb_class = new BbClassification();
	}

	@After
	public void tearDown() throws Exception {
		bb_class = null;
	}
	
    public void testBatchUid(){
    	assertNull(bb_class.getBatchUid());
    	bb_class.setBatchUid("batch_uid");
    	assertNotNull(bb_class.getBatchUid());
    	assertEquals("batch_uid", bb_class.getBatchUid());
    }

   
    public void testId(){
    	assertNull(bb_class.getId());
    	bb_class.setBatchUid("id");
    	assertNotNull(bb_class.getId());
    	assertEquals("id", bb_class.getId());
    }

    
    public void testDataSourceId(){
    	assertNull(bb_class.getDataSourceId());
    	bb_class.setDataSourceId("data_source_id");
    	assertNotNull(bb_class.getDataSourceId());
    	assertEquals("data_source_id", bb_class.getDataSourceId());
    }

    
    public void testParentId(){
    	assertNull(bb_class.getParentId());
    	bb_class.setParentId("parent_id");
    	assertNotNull(bb_class.getParentId());
    	assertEquals("parent_id", bb_class.getParentId());
    }
    
    public void testTitle(){
    	assertNull(bb_class.getTitle());
    	bb_class.setTitle("title");
    	assertNotNull(bb_class.getTitle());
    	assertEquals("title", bb_class.getTitle());	
    }

    @Test
	public void testExpansionData() {
		bb_class.setExpansionData(new String[]{"expansion_data"});
		assertEquals("expansion_data", bb_class.getExpansionData()[0]);
	}
}
