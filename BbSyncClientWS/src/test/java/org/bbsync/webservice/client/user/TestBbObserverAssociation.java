package org.bbsync.webservice.client.user;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbObserverAssociation {

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

	@Test
	public void testExpansionData() {
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setExpansionData(new String[]{"org.bbsync.BbObserverAssociation.ExpansionData=expansion_data"});
		assertNotNull(obs.getExpansionData());
		assertEquals(1, obs.getExpansionData().length);
		assertNotNull(obs.getExpansionData()[0]);
		assertEquals("org.bbsync.BbObserverAssociation.ExpansionData=expansion_data", obs.getExpansionData()[0]);
	}

	@Test
	public void testObserveeId() {
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setObserveeIds(new String[]{"observee_id1", "observee_id2", "observee_id3"});
		assertNotNull(obs.getObserveeId());
		assertEquals(3, obs.getObserveeId().length);
		assertEquals("observee_id1", obs.getObserveeId()[0]);
		assertEquals("observee_id2", obs.getObserveeId()[1]);
		assertEquals("observee_id3", obs.getObserveeId()[2]);
	}

	@Test
	public void testGetObserverId() {
		BbObserverAssociation obs = new BbObserverAssociation();
		obs.setObserverId("observer_id");
		assertNotNull(obs.getObserverId());
		assertEquals("observer_id", obs.getObserverId());
	}
}
