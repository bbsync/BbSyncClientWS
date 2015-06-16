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

public class TestBbTerm_Integration {
	private static final Logger logger = Logger.getLogger(TestBbTerm_Integration.class.getName());
	//Term Information
	private String basic_term_id = null;
	private static final String TERM_NAME = "Test Term #1";
	private static final String TERM_SOURCEDID = "test_term_1";
	//Course Information
	private String basic_course_id = null;
    private static final String COURSE_ID = "tst_csr_1";
    private static final String COURSE_NAME = "Test Course #1";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
        createBasicTerm();
	}

	@After
	public void tearDown() throws Exception {
		deleteTerm(basic_term_id);
	}

    @Ignore
    public void testGetServerVersion() {
        BbTerm bb_term = new BbTerm();
        Long version = bb_term.getServerVersion();
        assertNotNull(version);
        assertTrue(version > 0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
    
    @Ignore
    public void testInitializeCourseWS() {
    	BbTerm bb_term = new BbTerm();
        assertTrue(bb_term.initializeCourseWS());
        logger.debug("initialize Course web service succeded.");
    }
    
    @Test
    public void testGetAllTerms(){
    	BbTerm bb_term = new BbTerm();
    	BbTerm[] terms = bb_term.getAllTerms(false);
    	assertNotNull(terms);
    }
    
    @Ignore
    public void testGetTermsByName(){
    	BbTerm bb_term = new BbTerm();
    	BbTerm[] terms = bb_term.getTermsByName(TERM_NAME);
    	assertNotNull(terms);
    	assertEquals(1, terms.length);
    	assertEquals(TERM_NAME, terms[0].getName());
    	assertEquals(TERM_SOURCEDID, terms[0].getSourcedidId());
    	assertEquals(basic_term_id, terms[0].getId());
    }
    
    @Ignore
    public void testRetrieve(){
    	BbTerm bb_term = new BbTerm();
    	bb_term.setId(basic_term_id);
    	BbTerm term = (BbTerm) bb_term.retrieve();
    	assertNotNull(term);
    	assertEquals(basic_term_id, term.getId());
    	assertEquals(TERM_NAME, term.getName());
    	assertEquals(TERM_SOURCEDID, term.getSourcedidId());
    }
    
    @Ignore
    public void testCoursesInTerm_course_id(){
    	//create a new course
    	createBasicCourse();
    	assertNotNull(basic_course_id);
    	//retrieve the test term object
    	assertNotNull(basic_term_id);
    	BbTerm bb_term = new BbTerm();
    	bb_term.setId(basic_term_id);
    	bb_term = (BbTerm) bb_term.retrieve();
    	assertNotNull(bb_term);
    	assertTrue(bb_term.addCourseToTerm(basic_course_id));
    	BbCourse[] courses = bb_term.getAllCoursesInTerm();
    	assertNotNull(courses);
    	assertEquals(1, courses.length);
    	assertEquals(basic_course_id, courses[0].getId());
    	assertTrue(bb_term.removeCourseFromTerm(basic_course_id));
    	deleteCourse(basic_course_id);
    }
    
    @Ignore
    public void testCoursesInTerm_course_id_term_id(){
    	//create a new course
    	createBasicCourse();
    	assertNotNull(basic_course_id);
    	//retrieve the test term object
    	assertNotNull(basic_term_id);
    	BbTerm bb_term = new BbTerm();
    	assertTrue(bb_term.addCourseToTerm(basic_course_id, basic_term_id));
    	BbCourse[] courses = bb_term.getAllCoursesInTerm(basic_term_id);
    	assertNotNull(courses);
    	assertEquals(1, courses.length);
    	assertEquals(basic_course_id, courses[0].getId());
    	assertTrue(bb_term.removeCourseFromTerm(basic_course_id));
    	deleteCourse(basic_course_id);
    }
    
    @Ignore
    public void testGetTermByCourseId(){
    	//create a new course
    	createBasicCourse();
    	assertNotNull(basic_course_id);
    	//retrieve the test term object
    	assertNotNull(basic_term_id);
    	BbTerm bb_term = new BbTerm();
    	bb_term.setId(basic_term_id);
    	bb_term = (BbTerm) bb_term.retrieve();
    	assertNotNull(bb_term);
    	assertTrue(bb_term.addCourseToTerm(basic_course_id));
    	bb_term = bb_term.getTermByCourseId(basic_course_id);
    	assertNotNull(bb_term);
    	assertEquals(basic_term_id, bb_term.getId());
    	assertTrue(bb_term.removeCourseFromTerm(basic_course_id));
    	deleteCourse(basic_course_id);
    }
    
    private void deleteTerm(String id){
    	BbTerm bb_term = new BbTerm();
     	bb_term.setId(id);
    	assertTrue(bb_term.delete());
    	logger.debug("Deleted Term with ID: " + id);
    }
    
    private void createBasicTerm(){
    	BbTerm bb_term = new BbTerm();
    	bb_term.setName(TERM_NAME);
    	bb_term.setSourcedidId(TERM_SOURCEDID);
    	bb_term.setDuration(BbTerm.DURATION_CONTINUOUS);
    	String id = (String) bb_term.persist();
    	assertNotNull(id);
    	basic_term_id = id;
    	logger.debug("Created Term with ID: " + id);
    }
    
    private void createBasicCourse(){
        BbCourse bb_course = new BbCourse();
        bb_course.setCourseId(COURSE_ID);
        bb_course.setName(COURSE_NAME);
        //bb_course.setAvailable(true);
        String id = (String) bb_course.persist();
        assertNotNull(id);
        basic_course_id = id;
        logger.debug("Created Course with ID: " + id);
    }
    
    private void deleteCourse(String id) {
        BbCourse bb_course = new BbCourse();
        bb_course.setId(id);
        assertTrue(bb_course.delete());
        logger.debug("Deleted Course with ID: " + id);
    }
}
