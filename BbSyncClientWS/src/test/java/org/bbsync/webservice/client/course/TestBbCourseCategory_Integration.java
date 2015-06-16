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

public class TestBbCourseCategory_Integration {
	private static final Logger logger = Logger.getLogger(TestBbCourseCategory_Integration.class.getName());
	
	private String cc_title = "Test Course Category";
	private String cc_batch_uid = "test_crs_cat_1";
	private String cc_id = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		cc_id = createBasicCourseCategory(cc_title, cc_batch_uid);
	}

	@After
	public void tearDown() throws Exception {
		String[] crs_cat_ids = deleteCourseCategory(cc_id);
		assertNotNull(crs_cat_ids);
		assertEquals(1, crs_cat_ids.length);
		assertEquals(cc_id, crs_cat_ids[0]);
	}

	@Ignore
    public void testGetServerVersion() {
        BbCourseCategory bb_category = new BbCourseCategory();
        Long version = bb_category.getServerVersion();
        assertFalse(version == 0L);
        logger.info("Course web service version: " + version);
    }
	
	@Ignore
    public void testInitialize(){
		BbCourseCategory bb_category = new BbCourseCategory();
		assertTrue(bb_category.initializeCourseWS());
		logger.info("initialize Course web service succeded.");
    }

	@Test
	public void testBasicCourseCategory() {
		BbCourseCategory bb_category = new BbCourseCategory();
		bb_category.setId(cc_id);
		bb_category = (BbCourseCategory) bb_category.retrieve();
		assertNotNull(bb_category);
		assertFalse(bb_category.isAvailable());
		assertEquals(cc_batch_uid, bb_category.getBatchUid());
		assertEquals("_2_1", bb_category.getDataSourceId());
		assertEquals("", bb_category.getDescription());
		assertNotNull(bb_category.getExpansionData());
		assertNull(bb_category.getExpansionData()[0]);
		assertFalse(bb_category.isFrontPage());
		assertEquals(cc_id, bb_category.getId());
		assertFalse(bb_category.isOrganization());
		assertEquals("{unset id}",bb_category.getParentId());
		assertFalse(bb_category.isRestricted());
		assertEquals(cc_title, bb_category.getTitle());
	}
		
	@Test
	public void testGetAllCourseCategories(){
		BbCourseCategory bb_category = new BbCourseCategory();
		BbCourseCategory[] categories = (BbCourseCategory[])bb_category.getAllCourseCategories();
		assertNotNull(categories);
		assertTrue(categories.length>0);
		for(BbCourseCategory category:categories){
			assertFalse(category.isOrganization());
		}
	}
	
	@Test
	public void testGetCourseCategoryById(){
		BbCourseCategory bb_category = new BbCourseCategory();
		bb_category = (BbCourseCategory) bb_category.getCourseCategoryById(cc_id);
		assertNotNull(bb_category);
		assertFalse(bb_category.isOrganization());
	}
		
	@Test
	public void testGetCourseCategoriesByParentId(){
		//create another category & set the parent id
		BbCourseCategory bb_category = new BbCourseCategory();
		bb_category.setTitle("Test Course Category 2");
		bb_category.setBatchUid("test_crs_cat_2");
		bb_category.setParentId(cc_id);
		String category_id2 = (String) bb_category.persist();
		assertNotNull(category_id2);
		//reset bb_category & get categories by parent id 
		bb_category = new BbCourseCategory();
		BbCourseCategory[] categories = bb_category.getCategoriesByParentId(cc_id);
		assertNotNull(categories);
		assertEquals(1, categories.length);
		//delete the category that we just created
		String[] category_ids = deleteCourseCategory(category_id2);
		assertNotNull(category_ids);
		assertEquals(1, category_ids.length);
		assertEquals(category_id2, category_ids[0]);
	}
		
	@Test
	public void testChangeCourseCatergoryBatchUid(){
		BbCourseCategory bb_category = new BbCourseCategory();
		assertTrue(bb_category.changeCourseCategoryBatchUid(cc_id, "new_crs_cat_1"));
		bb_category.setId(cc_id);
		bb_category.setOrganization(false);
		bb_category = (BbCourseCategory) bb_category.retrieve();
		assertEquals("new_crs_cat_1", bb_category.getBatchUid());
	}
		
	private String createBasicCourseCategory(String title, String batch_uid){
		BbCourseCategory bb_category = new BbCourseCategory();
		bb_category.setTitle(title);
		bb_category.setBatchUid(batch_uid);
		String id = bb_category.saveCourseCategory(bb_category);
		assertNotNull(id);
		return id;
	}
	
	private String[] deleteCourseCategory(String id){
		BbCourseCategory bb_category = new BbCourseCategory();
		bb_category.setId(cc_id);
		return bb_category.deleteCourseCategory(new String[]{id});
	}	
}
