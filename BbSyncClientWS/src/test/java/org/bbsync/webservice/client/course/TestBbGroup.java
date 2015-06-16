package org.bbsync.webservice.client.course;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbGroup {
	
	private BbGroup bb_group = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bb_group = new BbGroup();
	}

	@After
	public void tearDown() throws Exception {
		bb_group = null;
	}
	
	@Test
	public void testCourseId(){
		assertNull(bb_group.getCourseId());
		bb_group.setCourseId("course_id");
		assertEquals("course_id", bb_group.getCourseId());
	}

	@Test
	public void testTitle(){
		assertNull(bb_group.getName());
		bb_group.setName("name");
		assertEquals("name", bb_group.getName());
	}

	@Test
	public void testDescription(){
		assertNull(bb_group.getDescription());
		bb_group.setDescription("description");
		assertEquals("description", bb_group.getDescription());
	}

	@Test
	public void testAvailability(){
		assertFalse(bb_group.isVisibleToStudents());
		bb_group.setVisibleToStudents(true);
		assertTrue(bb_group.isVisibleToStudents());
	}

	@Test
	public void testId(){
		assertNull(bb_group.getId());
		bb_group.setId("id");
		assertEquals("id", bb_group.getId());
	}
	
	@Test
	public void testGroupTools(){
		assertNull(bb_group.getGroupTools());
		bb_group.setGroupTools(new String[]{BbGroup.GROUP_TOOL_BLOGS, BbGroup.GROUP_TOOL_EMAIL});
		assertEquals(BbGroup.GROUP_TOOL_BLOGS, bb_group.getGroupTools()[0]);
		assertEquals(BbGroup.GROUP_TOOL_EMAIL, bb_group.getGroupTools()[1]);
	}
     
    @Test
	public void testDescriptionType(){
		assertNull(bb_group.getDescriptionType());
		bb_group.setDescriptionType(BbGroup.TEXT_TYPE_HTML);
		assertEquals(BbGroup.TEXT_TYPE_HTML, bb_group.getDescriptionType());
	}
}
