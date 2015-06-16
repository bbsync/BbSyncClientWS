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

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbOrgCategory_Integration {
	private static final Logger logger = Logger.getLogger(TestBbOrgCategory_Integration.class.getName());
	
	private String oc_title = "Test Organization Category";
	private String oc_batch_uid = "test_org_cat_1";
	private String oc_id = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		oc_id = createBasicOrgCategory(oc_title, oc_batch_uid);
	}

	@After
	public void tearDown() throws Exception {
		String[] org_cat_ids = deleteOrgCategory(oc_id);
		assertNotNull(org_cat_ids);
		assertEquals(1, org_cat_ids.length);
		assertEquals(oc_id, org_cat_ids[0]);
	}

	@Ignore
    public void testGetServerVersion() {
        BbOrgCategory bb_category = new BbOrgCategory();
        Long version = bb_category.getServerVersion();
        assertFalse(version == 0L);
        logger.info("Course web service version: " + version);
    }
	
	@Ignore
    public void testInitialize(){
		BbOrgCategory bb_category = new BbOrgCategory();
		assertTrue(bb_category.initializeCourseWS());
		logger.info("initialize Course web service succeded.");
    }
	
	@Test
	public void testBasicOrgCategory() {
		BbOrgCategory bb_category = new BbOrgCategory();
		bb_category.setOrganization(true);
		bb_category.setId(oc_id);
		bb_category = (BbOrgCategory) bb_category.retrieve();
		assertNotNull(bb_category);
		assertFalse(bb_category.isAvailable());
		assertEquals(oc_batch_uid, bb_category.getBatchUid());
		assertEquals("_2_1", bb_category.getDataSourceId());
		assertEquals("", bb_category.getDescription());
		assertNotNull(bb_category.getExpansionData());
		assertNull(bb_category.getExpansionData()[0]);
		assertFalse(bb_category.isFrontPage());
		assertEquals(oc_id, bb_category.getId());
		assertTrue(bb_category.isOrganization());
		assertEquals("{unset id}",bb_category.getParentId());
		assertFalse(bb_category.isRestricted());
		assertEquals(oc_title, bb_category.getTitle());
	}
		
	@Test
	public void testGetAllOrganizationCategories(){
		BbOrgCategory bb_category = new BbOrgCategory();
		BbOrgCategory[] categories = (BbOrgCategory[])bb_category.getAllOrganizationCategories();
		assertNotNull(categories);
		assertTrue(categories.length>0);
		for(BbOrgCategory category:categories){
			assertTrue(category.isOrganization());
		}
	}
		
	@Test
	public void testGetOrgCategoryById(){
		BbOrgCategory bb_category = new BbOrgCategory();
		bb_category = (BbOrgCategory) bb_category.getOrgCategoryById(oc_id);
		assertNotNull(bb_category);
		assertTrue(bb_category.isOrganization());
	}
		
	@Test
	public void testGetOrgCategoriesByParentId(){
		//create another category & set the parent id
		BbOrgCategory bb_category = new BbOrgCategory();
		bb_category.setTitle("Test Organization Category 2");
		bb_category.setBatchUid("test_org_cat_2");
		bb_category.setOrganization(true);
		bb_category.setParentId(oc_id);
		String category_id2 = (String) bb_category.persist();
		assertNotNull(category_id2);
		//reset bb_category & get categories by parent id 
		bb_category = new BbOrgCategory();
		BbOrgCategory[] categories = bb_category.getOrgCategoriesByParentId(oc_id);
		assertNotNull(categories);
		assertEquals(1, categories.length);
		//delete the category that we just created
		String[] category_ids = deleteOrgCategory(category_id2);
		assertNotNull(category_ids);
		assertEquals(1, category_ids.length);
		assertEquals(category_id2, category_ids[0]);
	}
		
	@Test
	public void testChangeOrgCatergoryBatchUid(){
		BbOrgCategory bb_category = new BbOrgCategory();
		assertTrue(bb_category.changeOrgCategoryBatchUid(oc_id, "new_org_cat_1"));
		bb_category.setId(oc_id);
		bb_category.setOrganization(true);
		bb_category = (BbOrgCategory) bb_category.retrieve();
		assertEquals("new_org_cat_1", bb_category.getBatchUid());
	}
		
	private String createBasicOrgCategory(String title, String batch_uid){
		BbOrgCategory bb_category = new BbOrgCategory();
		bb_category.setTitle(title);
		bb_category.setBatchUid(batch_uid);
		bb_category.setOrganization(true);
		String id = bb_category.saveOrgCategory(bb_category);
		assertNotNull(id);
		return id;
	}
		
	private String[] deleteOrgCategory(String id){
		BbOrgCategory bb_category = new BbOrgCategory();
		return bb_category.deleteOrgCategory(new String[]{id});
	}
}
