package org.bbsync.webservice.client.course;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbCourse {
	BbCourse bb_course = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bb_course = new BbCourse();
	}

	@After
	public void tearDown() throws Exception {
		bb_course = null;
	}

	@Test
	public void testAllowGuests() {
		assertFalse(bb_course.isAllowGuests());
		bb_course.setAllowGuests(true);
		assertTrue(bb_course.isAllowGuests());
	}

	@Test
	public void testAllowObservers() {
		assertFalse(bb_course.isAllowObservers());
		bb_course.setAllowObservers(true);
		assertTrue(bb_course.isAllowObservers());
	}

	@Test
	public void testAvailable() {
		assertFalse(bb_course.isAvailable());
		bb_course.setAvailable(true);
		assertTrue(bb_course.isAvailable());
	}

	@Test
	public void testBatchUid() {
		assertNull(bb_course.getBatchUid());
		bb_course.setBatchUid("batch_uid");
		assertNotNull(bb_course.getBatchUid());
		assertEquals("batch_uid", bb_course.getBatchUid());
	}

	@Test
	public void testButtonStyleBbId() {
		assertNull(bb_course.getButtonStyleBbId());
		bb_course.setButtonStyleBbId("button_style_bbid");
		assertNotNull(bb_course.getButtonStyleBbId());
		assertEquals("button_style_bbid", bb_course.getButtonStyleBbId());
	}

	@Test
	public void testButtonStyleShape() {
		assertNull(bb_course.getButtonStyleShape());
		bb_course.setButtonStyleShape(BbCourse.BUTTON_SHAPE_RECTANGLE);
		assertNotNull(bb_course.getButtonStyleShape());
		assertEquals(BbCourse.BUTTON_SHAPE_RECTANGLE, bb_course.getButtonStyleShape());
	}

	@Test
	public void testButtonStyleType() {
		assertNull(bb_course.getButtonStyleType());
		bb_course.setButtonStyleType(BbCourse.BUTTON_STYLE_SOLID);
		assertNotNull(bb_course.getButtonStyleType());
		assertEquals(BbCourse.BUTTON_STYLE_SOLID, bb_course.getButtonStyleType());
	}

	@Test
	public void testCartridgeId() {
		assertNull(bb_course.getCartridgeId());
		bb_course.setCartridgeId("cartridge_id");
		assertNotNull(bb_course.getCartridgeId());
		assertEquals("cartridge_id", bb_course.getCartridgeId());
	}

	@Test
	public void testClassificationId() {
		assertNull(bb_course.getClassificationId());
		bb_course.setClassificationId("classification_id");
		assertNotNull(bb_course.getClassificationId());
		assertEquals("classification_id", bb_course.getClassificationId());
	}

	@Test
	public void testDuration() {
		assertNull(bb_course.getDuration());
		bb_course.setDuration(BbCourse.DURATION_CONTINUOUS);
		assertNotNull(bb_course.getDuration());
		assertEquals(BbCourse.DURATION_CONTINUOUS, bb_course.getDuration());
	}

	@Test
	public void testCourseId() {
		assertNull(bb_course.getCourseId());
		bb_course.setCourseId("course_id");
		assertNotNull(bb_course.getCourseId());
		assertEquals("course_id", bb_course.getCourseId());
	}

	@Test
	public void testPace() {
    	assertNull(bb_course.getPace());
    	bb_course.setPace(BbCourse.PACE_INSTRUCTOR_LED);
    	assertNotNull(bb_course.getPace());
        assertEquals(BbCourse.PACE_INSTRUCTOR_LED, bb_course.getPace());
	}
	
	@Test
	public void testServiceLevel() {
    	assertNotNull(bb_course.getServiceLevel());
        assertEquals(BbCourse.SERVICE_LEVEL_COURSE, bb_course.getServiceLevel());
	}

	@Test
	public void testDataSourceId() {
    	assertNull(bb_course.getDataSourceId());
    	bb_course.setDataSourceId("data_source_id");
    	assertNotNull(bb_course.getDataSourceId());
        assertEquals("data_source_id", bb_course.getDataSourceId());
	}

	@Test
	public void testDecAbsoluteLimit() {
    	assertEquals(0, bb_course.getDecAbsoluteLimit());
    	bb_course.setDecAbsoluteLimit(1L);
        assertEquals(1L, bb_course.getDecAbsoluteLimit());
	}

    @Test
    public void testDescription() {
    	assertNull(bb_course.getDescription());
    	bb_course.setDescription("description");
    	assertNotNull(bb_course.getDescription());
        assertEquals("description", bb_course.getDescription());
    }

    @Test
    public void testEndDate() {
    	long now = Calendar.getInstance().getTimeInMillis();
    	long now_no_millis = Long.parseLong(String.valueOf(now).substring(0, 10) + "000");
    	assertEquals(0, bb_course.getEndDate());
    	bb_course.setEndDate(now);
        assertEquals(now_no_millis, bb_course.getEndDate());
    }

    @Test
    public void testEnrollmentAccessCode() {
    	assertNull(bb_course.getEnrollmentAccessCode());
    	bb_course.setEnrollmentAccessCode("enrollment_access_code");
    	assertNotNull(bb_course.getEnrollmentAccessCode());
        assertEquals("enrollment_access_code", bb_course.getEnrollmentAccessCode());
    }

    @Test
    public void testEnrollmentEndDate() {
    	long now = Calendar.getInstance().getTimeInMillis();
    	long now_no_millis = Long.parseLong(String.valueOf(now).substring(0, 10) + "000");
    	assertEquals(0, bb_course.getEnrollmentEndDate());
    	bb_course.setEnrollmentEndDate(now);;
        assertEquals(now_no_millis, bb_course.getEnrollmentEndDate());
    }

    @Test
    public void testEnrollmentStartDate() {
    	long now = Calendar.getInstance().getTimeInMillis();
    	long now_no_millis = Long.parseLong(String.valueOf(now).substring(0, 10) + "000");
    	assertEquals(0, bb_course.getEnrollmentStartDate());
    	bb_course.setEnrollmentStartDate(now);;
        assertEquals(now_no_millis, bb_course.getEnrollmentStartDate());
    }

    @Test
    public void testEnrollmentType() {
    	assertNull(bb_course.getEnrollmentType());
    	bb_course.setEnrollmentType(BbCourse.ENROLL_TYPE_SELF);
    	assertNotNull(bb_course.getEnrollmentType());
        assertEquals(BbCourse.ENROLL_TYPE_SELF, bb_course.getEnrollmentType());
    }

    @Test
    public void testExpansionData() {
    	//RFC-4122 UUID
		bb_course.setExpansionData(new String[]{"COURSE.UUID=52ff2650-0560-11e4-9191-0800200c9a66"});
		assertEquals("COURSE.UUID=52ff2650-0560-11e4-9191-0800200c9a66", bb_course.getExpansionData()[0]);
    }

    @Test
    public void testFee() {
    	//to test float values:  assertEquals(expected, actual, delta);
    	assertEquals(0.00f, bb_course.getFee(), 0.001);
    	bb_course.setFee(1.11F);
        assertEquals(1.11F, bb_course.getFee(), 0.001);
        // - OR - convert to String & test
        String float_str = String.valueOf(bb_course.getFee()).substring(0, 4);
        assertEquals("1.11", float_str);
    }

	@Test
	public void testHasDescriptionPage() {
		assertFalse(bb_course.isHasDescriptionPage());
		bb_course.setHasDescriptionPage(true);
		assertTrue(bb_course.isHasDescriptionPage());
	}

    @Test
    public void testId() {
    	assertNull(bb_course.getId());
    	bb_course.setId("id");
    	assertNotNull(bb_course.getId());
        assertEquals("id", bb_course.getId());
    }

    @Test
    public void testInstitutionName() {
    	assertNull(bb_course.getInstitutionName());
    	bb_course.setInstitutionName("institution_name");
    	assertNotNull(bb_course.getInstitutionName());
        assertEquals("institution_name", bb_course.getInstitutionName());
    }

    @Test
    public void testLocale() {
    	Locale locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
    	assertNull(bb_course.getLocale());
    	bb_course.setLocale(locale.toString());
    	assertNotNull(bb_course.getLocale());
        assertEquals(bb_course.getLocale(), bb_course.getLocale());
    }

    @Test
    public void testLocaleEnforced() {
        assertFalse(bb_course.isLocaleEnforced());
        bb_course.setLocaleEnforced(true);
        assertTrue(bb_course.isLocaleEnforced());
    }
    
    @Test
    public void testLockedOut() {
        assertFalse(bb_course.isLockedOut());
        bb_course.setLockedOut(true);
        assertTrue(bb_course.isLockedOut());
    }

	@Test
    public void testName() {
    	assertNull(bb_course.getName());
    	bb_course.setName("name");
    	assertNotNull(bb_course.getName());
        assertEquals("name", bb_course.getName());
    }

    @Test
    public void testNavCollapsable() {
        assertFalse(bb_course.isNavCollapsable());
        bb_course.setNavCollapsable(true);
        assertTrue(bb_course.isNavCollapsable());
    }

    @Test
    public void testNavColorBg() {
    	assertNull(bb_course.getNavColorBg());
    	bb_course.setNavColorBg("#FFFFFF");
    	assertNotNull(bb_course.getNavColorBg());
        assertEquals("#FFFFFF", bb_course.getNavColorBg());
    }   

    @Test
    public void testNavColorFg() {
    	assertNull(bb_course.getNavColorFg());
    	bb_course.setNavColorFg("#FFFFFF");
    	assertNotNull(bb_course.getNavColorFg());
        assertEquals("#FFFFFF", bb_course.getNavColorFg());
    }

    @Test
    public void testNavigationStyle() {
    	assertNull(bb_course.getNavigationStyle());
    	bb_course.setNavigationStyle(BbCourse.NAV_STYLE_TEXT);
    	assertNotNull(bb_course.getNavigationStyle());
        assertEquals(BbCourse.NAV_STYLE_TEXT, bb_course.getNavigationStyle());

    }

    @Test
    public void testNumberOfDaysOfUse() {
        assertEquals(0, bb_course.getNumberOfDaysOfUse());
        bb_course.setNumberOfDaysOfUse(1);
    	assertEquals(1, bb_course.getNumberOfDaysOfUse());
    }

	@Test
	public void testOrganization() {
		assertFalse(bb_course.isOrganization());
	}

	@Test
    public void testShowInCatalog() {
        assertFalse(bb_course.isShowInCatalog());
        bb_course.setShowInCatalog(true);
        assertTrue(bb_course.isShowInCatalog());
    }

    @Test
    public void testSoftLimit() {
        assertEquals(0L, bb_course.getSoftLimit());
        bb_course.setSoftLimit(1L);
    	assertEquals(1L, bb_course.getSoftLimit());
    }

    @Test
    public void testStartDate() {
    	long now = Calendar.getInstance().getTimeInMillis();
    	long now_no_millis = Long.parseLong(String.valueOf(now).substring(0, 10) + "000");
    	assertEquals(0, bb_course.getStartDate());
    	bb_course.setStartDate(now);
        assertEquals(now_no_millis, bb_course.getStartDate());
    }

    @Test
    public void testUploadLimit() {
        assertEquals(0L, bb_course.getUploadLimit());
        bb_course.setUploadLimit(1L);
    	assertEquals(1L, bb_course.getUploadLimit());
    }
}
