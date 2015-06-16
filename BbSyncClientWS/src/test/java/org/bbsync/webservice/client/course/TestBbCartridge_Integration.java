/* 
 * Copyright 2014 Kurt Faulknerloser
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bbsync.webservice.client.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Currently, there is no way to create a Cartridge or search for multiple 
 * Cartridges via web services.  Therefore, the get & delete methods for 
 * BbCartridge must be manually tested.  That is, first create a Course.  Next,
 * load a Cartridge into the Course.  Finally, test the BbCartridge get and
 * delete methods.
 */
public class TestBbCartridge_Integration {
	private static final Logger logger = Logger.getLogger(TestBbCartridge_Integration.class.getName());

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
    public void testInitializeCourseWS() {
    	BbCartridge bb_cart = new BbCartridge();
        assertTrue(bb_cart.initializeCourseWS());
        logger.debug("initialize Course web service succeded.");
    }
	
	@Ignore
    public void testGetServerVersion() {
        BbCartridge bb_cart = new BbCartridge();
        Long version = bb_cart.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
    
    /**
     * Tests BbCartridge using persist(), retrieve() and delete() methods.
     */
    @Test
    public void testCartridge_1() {
    	//persist
        BbCartridge bb_cart = new BbCartridge();
        bb_cart.setIdentifier("tst_cart_1");
        bb_cart.setPublisherName("bbsync.org");
        bb_cart.setTitle("Test Cartridge #1");
        String id = (String) bb_cart.persist();
        assertNotNull(id);
        //retrieve
        bb_cart = new BbCartridge();
        bb_cart.setId(id);
        bb_cart = (BbCartridge) bb_cart.retrieve();
        assertNotNull(bb_cart);
        assertEquals("tst_cart_1", bb_cart.getIdentifier());
        assertEquals("bbsync.org", bb_cart.getPublisherName());
        assertEquals("Test Cartridge #1", bb_cart.getTitle());
        //delete
        bb_cart = new BbCartridge();
        bb_cart.setId(id);
        assertTrue(bb_cart.delete());
    }
    
    /**
     * Tests BbCartridge using CourseWS methods.
     */
    @Test
    public void testCartridge_2() {
    	//persist
        BbCartridge bb_cart = new BbCartridge();
        bb_cart.setIdentifier("tst_cart_1");
        bb_cart.setPublisherName("bbsync.org");
        bb_cart.setTitle("Test Cartridge #1");
        String id = bb_cart.saveCartridge(bb_cart);
        assertNotNull(id);
        //retrieve
        bb_cart = new BbCartridge();
        bb_cart = bb_cart.getBbCartridge(id);
        assertNotNull(bb_cart);
        assertEquals("tst_cart_1", bb_cart.getIdentifier());
        assertEquals("bbsync.org", bb_cart.getPublisherName());
        assertEquals("Test Cartridge #1", bb_cart.getTitle());
        //delete
        bb_cart = new BbCartridge();
        assertEquals(id,bb_cart.deleteCartridge(id));
    }    
}