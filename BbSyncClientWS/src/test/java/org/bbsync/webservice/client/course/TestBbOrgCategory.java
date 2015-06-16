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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbOrgCategory {
	private BbOrgCategory bb_category = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bb_category = new BbOrgCategory();
	}

	@After
	public void tearDown() throws Exception {
		bb_category = null;
	}

	@Test
	public void testBatchUID(){
		assertNull(bb_category.getBatchUid());
		bb_category.setBatchUid("batch_uid");
		assertNotNull(bb_category.getBatchUid());
		assertEquals("batch_uid", bb_category.getBatchUid());
	}
	
	@Test
	public void testDataSourceID(){
		assertNull(bb_category.getDataSourceId());
		bb_category.setDataSourceId("data_source_id");
		assertNotNull(bb_category.getDataSourceId());
		assertEquals("data_source_id", bb_category.getDataSourceId());
	}

	@Test
	public void testDescription(){
		assertNull(bb_category.getDescription());
		bb_category.setDescription("description");
		assertNotNull(bb_category.getDescription());
		assertEquals("description", bb_category.getDescription());
	}

	@Test
	public void testParentID(){
		assertNull(bb_category.getParentId());
		bb_category.setParentId("parent_id");
		assertNotNull(bb_category.getParentId());
		assertEquals("parent_id", bb_category.getParentId());
	}

	@Test
	public void testTitle(){
		assertNull(bb_category.getTitle());
		bb_category.setTitle("title");
		assertNotNull(bb_category.getTitle());
		assertEquals("title", bb_category.getTitle());
	}
	
	@Test
	public void testAvailable(){
		assertFalse(bb_category.isAvailable());
		bb_category.setAvailable(true);
		assertTrue(bb_category.isAvailable());	
	}
	
	@Test
	public void testFrontPage(){
		assertFalse(bb_category.isFrontPage());
		bb_category.setFrontPage(true);
		assertTrue(bb_category.isFrontPage());	
	}
	
	@Test
	public void testOrganization(){
		assertTrue(bb_category.isOrganization());
	}
	
	@Test
	public void testRestricted(){
		assertFalse(bb_category.isRestricted());
		bb_category.setRestricted(true);
		assertTrue(bb_category.isRestricted());	
	}
	
	@Test
	public void testExpansionData() {
		bb_category.setExpansionData(new String[]{"org.bbsync.BbCategory.ExpansionData=expansion_data"});
		assertEquals("org.bbsync.BbCategory.ExpansionData=expansion_data", bb_category.getExpansionData()[0]);
	}
}
