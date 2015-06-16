/**
 * 
 */
package org.bbsync.webservice.client.util;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kurt
 *
 */
public class TestBbDataSource {

	private BbDataSource bbds = null;

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
	public void testBatchUid() {
		bbds.setBatchUid("batch_uid");
		assertEquals("batch_uid", bbds.getBatchUid());
	}

	@Test
	public void testDescription() {
		bbds.setDescription("description");
		assertEquals("description", bbds.getDescription());
	}

	@Test
	public void testId() {
		bbds.setId("id");
		assertEquals("id", bbds.getId());
	}	
}
