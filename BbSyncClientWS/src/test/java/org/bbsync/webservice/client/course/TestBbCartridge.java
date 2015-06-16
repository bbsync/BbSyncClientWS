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
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbCartridge {
	BbCartridge bb_cart = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bb_cart = new BbCartridge();
	}

	@After
	public void tearDown() throws Exception {
		bb_cart = null;
	}

	@Test
    public void testDescription(){
    	assertNull(bb_cart.getDescription());
    	bb_cart.setDescription("description");
    	assertNotNull(bb_cart.getDescription());
        assertEquals("description", bb_cart.getDescription());
    }
    
	@Test
    public void testExpansionData(){
		bb_cart.setExpansionData(new String[]{"org.bbsync.BbCartridge.ExpansionData=expansion_data"});
		assertEquals("org.bbsync.BbCartridge.ExpansionData=expansion_data", bb_cart.getExpansionData()[0]);
    }
    
	@Test
    public void testId(){
    	assertNull(bb_cart.getId());
    	bb_cart.setId("id");
    	assertNotNull(bb_cart.getId());
        assertEquals("id", bb_cart.getId());
    }
    
	@Test
    public void testIdentifier(){
    	assertNull(bb_cart.getIdentifier());
    	bb_cart.setIdentifier("identifier");
    	assertNotNull(bb_cart.getIdentifier());
        assertEquals("identifier", bb_cart.getIdentifier());
    }
    
	@Test
    public void testPublisherName(){
    	assertNull(bb_cart.getPublisherName());
    	bb_cart.setPublisherName("publisher_name");
    	assertNotNull(bb_cart.getPublisherName());
        assertEquals("publisher_name", bb_cart.getPublisherName());
    }
    
	@Test
    public void testTitle(){
    	assertNull(bb_cart.getTitle());
    	bb_cart.setTitle("title");
    	assertNotNull(bb_cart.getTitle());
        assertEquals("title", bb_cart.getTitle());
    }
}
