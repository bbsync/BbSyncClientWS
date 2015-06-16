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

import java.util.Calendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbTerm {
	private BbTerm bb_term = null;
	
	@BeforeClass
	public static void testUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void testUp() throws Exception {
		bb_term = new BbTerm();
	}

	@After
	public void tearDown() throws Exception {
		bb_term = null;
	}
	
	@Test
	public void testId(){
		assertNull(bb_term.getId());
    	bb_term.setId("id");
    	assertNotNull(bb_term.getId());
        assertEquals("id", bb_term.getId());
	}

	@Test
	public void testName(){
		assertNull(bb_term.getName());
    	bb_term.setName("name");
    	assertNotNull(bb_term.getName());
        assertEquals("name", bb_term.getName());
	}

	@Test
	public void testDescription(){
		assertNull(bb_term.getDescription());
    	bb_term.setDescription("description");
    	assertNotNull(bb_term.getDescription());
        assertEquals("description", bb_term.getDescription());
	}

	@Test
	public void testDescriptionType(){
		assertNull(bb_term.getDescriptionType());
    	bb_term.setDescriptionType(BbTerm.TEXT_TYPE_HTML);
    	assertNotNull(bb_term.getDescriptionType());
        assertEquals(BbTerm.TEXT_TYPE_HTML, bb_term.getDescriptionType());
	}

	@Test
	public void testSourcedidSource(){
		assertNull(bb_term.getSourcedidSource());
    	bb_term.setSourcedidSource("sourcedid_source");
    	assertNotNull(bb_term.getSourcedidSource());
        assertEquals("sourcedid_source", bb_term.getSourcedidSource());
	}

	@Test
	public void testSourcedidId(){
		assertNull(bb_term.getSourcedidId());
    	bb_term.setSourcedidId("sourcedid_id");
    	assertNotNull(bb_term.getSourcedidId());
        assertEquals("sourcedid_id", bb_term.getSourcedidId());		
	}

	@Test
	public void testDataSrcId(){
		assertNull(bb_term.getDataSrcId());
    	bb_term.setDataSrcId("data_source_id");
    	assertNotNull(bb_term.getDataSrcId());
        assertEquals("data_source_id", bb_term.getDataSrcId());
	}

	@Test
	public void testDuration(){
		assertNull(bb_term.getDuration());
		bb_term.setDuration(BbTerm.DURATION_CONTINUOUS);
		assertNotNull(bb_term.getDuration());
		assertEquals(BbTerm.DURATION_CONTINUOUS, bb_term.getDuration());
	}

	@Test
	public void testStartDate(){
		long now = Calendar.getInstance().getTimeInMillis();
    	long now_no_millis = Long.parseLong(String.valueOf(now).substring(0, 10) + "000");
    	assertEquals(0, bb_term.getStartDate());
    	bb_term.setStartDate(now);
        assertEquals(now_no_millis, bb_term.getStartDate());
	}

	@Test
	public void testEndDate(){
		long now = Calendar.getInstance().getTimeInMillis();
    	long now_no_millis = Long.parseLong(String.valueOf(now).substring(0, 10) + "000");
    	assertEquals(0, bb_term.getEndDate());
    	bb_term.setEndDate(now);
        assertEquals(now_no_millis, bb_term.getEndDate());
	}

	@Test
	public void testDaysOfUse(){
		assertEquals(0, bb_term.getDaysOfUse());
    	bb_term.setDaysOfUse(111);
        assertEquals(111, bb_term.getDaysOfUse());
	}

	@Test
	public void testAvailable(){
		assertFalse(bb_term.isAvailable());
		bb_term.setAvailable(true);
		assertTrue(bb_term.isAvailable());
	}

	@Test
	public void testExpansionData(){
		bb_term.setExpansionData(new String[]{"org.bbsync.BbCourse.ExpansionData=expansion_data"});
		assertEquals("org.bbsync.BbCourse.ExpansionData=expansion_data", bb_term.getExpansionData()[0]);
	}
}
