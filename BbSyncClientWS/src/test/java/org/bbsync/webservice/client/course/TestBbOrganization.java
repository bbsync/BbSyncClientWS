package org.bbsync.webservice.client.course;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbOrganization {
	BbOrganization bb_org = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bb_org = new BbOrganization();
	}

	@After
	public void tearDown() throws Exception {
		bb_org = null;
	}

	@Test
	public void testAllowGuests() {
		assertFalse(bb_org.isAllowGuests());
		bb_org.setAllowGuests(true);
		assertTrue(bb_org.isAllowGuests());
	}

	@Test
	public void testAllowObservers() {
		assertFalse(bb_org.isAllowObservers());
		bb_org.setAllowObservers(true);
		assertTrue(bb_org.isAllowObservers());
	}

	@Test
	public void testAvailable() {
		assertFalse(bb_org.isAvailable());
		bb_org.setAvailable(true);
		assertTrue(bb_org.isAvailable());
	}

	@Test
	public void testBatchUid() {
		assertNull(bb_org.getBatchUid());
		bb_org.setBatchUid("batch_uid");
		assertNotNull(bb_org.getBatchUid());
		assertEquals("batch_uid", bb_org.getBatchUid());
	}

	@Test
	public void testButtonStyleBbId() {
		assertNull(bb_org.getButtonStyleBbId());
		bb_org.setButtonStyleBbId("button_style_bbid");
		assertNotNull(bb_org.getButtonStyleBbId());
		assertEquals("button_style_bbid", bb_org.getButtonStyleBbId());
	}

	@Test
	public void testButtonStyleShape() {
		assertNull(bb_org.getButtonStyleShape());
		bb_org.setButtonStyleShape(BbOrganization.BUTTON_SHAPE_RECTANGLE);
		assertNotNull(bb_org.getButtonStyleShape());
		assertEquals(BbOrganization.BUTTON_SHAPE_RECTANGLE, bb_org.getButtonStyleShape());
	}

	@Test
	public void testButtonStyleType() {
		assertNull(bb_org.getButtonStyleType());
		bb_org.setButtonStyleType(BbOrganization.BUTTON_STYLE_SOLID);
		assertNotNull(bb_org.getButtonStyleType());
		assertEquals(BbOrganization.BUTTON_STYLE_SOLID, bb_org.getButtonStyleType());
	}

	@Test
	public void testCartridgeId() {
		assertNull(bb_org.getCartridgeId());
		bb_org.setCartridgeId("cartridge_id");
		assertNotNull(bb_org.getCartridgeId());
		assertEquals("cartridge_id", bb_org.getCartridgeId());
	}

	@Test
	public void testClassificationId() {
		assertNull(bb_org.getClassificationId());
		bb_org.setClassificationId("classification_id");
		assertNotNull(bb_org.getClassificationId());
		assertEquals("classification_id", bb_org.getClassificationId());
	}

	@Test
	public void testDuration() {
		assertNull(bb_org.getDuration());
		bb_org.setDuration(BbOrganization.DURATION_CONTINUOUS);
		assertNotNull(bb_org.getDuration());
		assertEquals(BbOrganization.DURATION_CONTINUOUS, bb_org.getDuration());
	}

	@Test
	public void testOrganizationId() {
		assertNull(bb_org.getOrganizationId());
		bb_org.setOrganizationId("org_id");
		assertNotNull(bb_org.getOrganizationId());
		assertEquals("org_id", bb_org.getOrganizationId());
	}

	@Test
	public void testPace() {
    	assertNull(bb_org.getPace());
    	bb_org.setPace(BbOrganization.PACE_INSTRUCTOR_LED);
    	assertNotNull(bb_org.getPace());
        assertEquals(BbOrganization.PACE_INSTRUCTOR_LED, bb_org.getPace());
	}
	
	@Test
	public void testServiceLevel() {
    	assertNotNull(bb_org.getServiceLevel());
        assertEquals(BbOrganization.SERVICE_LEVEL_ORG, bb_org.getServiceLevel());
	}

	@Test
	public void testDataSourceId() {
    	assertNull(bb_org.getDataSourceId());
    	bb_org.setDataSourceId("data_source_id");
    	assertNotNull(bb_org.getDataSourceId());
        assertEquals("data_source_id", bb_org.getDataSourceId());
	}

	@Test
	public void testDecAbsoluteLimit() {
    	assertEquals(0, bb_org.getDecAbsoluteLimit());
    	bb_org.setDecAbsoluteLimit(1L);
        assertEquals(1L, bb_org.getDecAbsoluteLimit());
	}

    @Test
    public void testDescription() {
    	assertNull(bb_org.getDescription());
    	bb_org.setDescription("description");
    	assertNotNull(bb_org.getDescription());
        assertEquals("description", bb_org.getDescription());
    }

    @Test
    public void testEndDate() {
    	long now = Calendar.getInstance().getTimeInMillis();
    	long now_no_millis = Long.parseLong(String.valueOf(now).substring(0, 10) + "000");
    	assertEquals(0, bb_org.getEndDate());
    	bb_org.setEndDate(now);
        assertEquals(now_no_millis, bb_org.getEndDate());
    }

    @Test
    public void testEnrollmentAccessCode() {
    	assertNull(bb_org.getEnrollmentAccessCode());
    	bb_org.setEnrollmentAccessCode("enrollment_access_code");
    	assertNotNull(bb_org.getEnrollmentAccessCode());
        assertEquals("enrollment_access_code", bb_org.getEnrollmentAccessCode());
    }

    @Test
    public void testEnrollmentEndDate() {
    	long now = Calendar.getInstance().getTimeInMillis();
    	long now_no_millis = Long.parseLong(String.valueOf(now).substring(0, 10) + "000");
    	assertEquals(0, bb_org.getEnrollmentEndDate());
    	bb_org.setEnrollmentEndDate(now);;
        assertEquals(now_no_millis, bb_org.getEnrollmentEndDate());
    }

    @Test
    public void testEnrollmentStartDate() {
    	long now = Calendar.getInstance().getTimeInMillis();
    	long now_no_millis = Long.parseLong(String.valueOf(now).substring(0, 10) + "000");
    	assertEquals(0, bb_org.getEnrollmentStartDate());
    	bb_org.setEnrollmentStartDate(now);;
        assertEquals(now_no_millis, bb_org.getEnrollmentStartDate());
    }

    @Test
    public void testEnrollmentType() {
    	assertNull(bb_org.getEnrollmentType());
    	bb_org.setEnrollmentType(BbOrganization.ENROLL_TYPE_SELF);
    	assertNotNull(bb_org.getEnrollmentType());
        assertEquals(BbOrganization.ENROLL_TYPE_SELF, bb_org.getEnrollmentType());
    }

    @Test
    public void testExpansionData() {
    	//RFC-4122 UUID
		bb_org.setExpansionData(new String[]{"COURSE.UUID=52ff2650-0560-11e4-9191-0800200c9a66"});
		assertEquals("COURSE.UUID=52ff2650-0560-11e4-9191-0800200c9a66", bb_org.getExpansionData()[0]);
    }

    @Test
    public void testFee() {
    	//to test float values:  assertEquals(expected, actual, delta);
    	assertEquals(0.00f, bb_org.getFee(), 0.001);
    	bb_org.setFee(1.11F);
        assertEquals(1.11F, bb_org.getFee(), 0.001);
        // - OR - convert to String & test
        String float_str = String.valueOf(bb_org.getFee()).substring(0, 4);
        assertEquals("1.11", float_str);
    }

	@Test
	public void testHasDescriptionPage() {
		assertFalse(bb_org.isHasDescriptionPage());
		bb_org.setHasDescriptionPage(true);
		assertTrue(bb_org.isHasDescriptionPage());
	}

    @Test
    public void testId() {
    	assertNull(bb_org.getId());
    	bb_org.setId("id");
    	assertNotNull(bb_org.getId());
        assertEquals("id", bb_org.getId());
    }

    @Test
    public void testInstitutionName() {
    	assertNull(bb_org.getInstitutionName());
    	bb_org.setInstitutionName("institution_name");
    	assertNotNull(bb_org.getInstitutionName());
        assertEquals("institution_name", bb_org.getInstitutionName());
    }

    @Test
    public void testLocale() {
    	Locale locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
    	assertNull(bb_org.getLocale());
    	bb_org.setLocale(locale.toString());
    	assertNotNull(bb_org.getLocale());
        assertEquals(bb_org.getLocale(), bb_org.getLocale());
    }

    @Test
    public void testLocaleEnforced() {
        assertFalse(bb_org.isLocaleEnforced());
        bb_org.setLocaleEnforced(true);
        assertTrue(bb_org.isLocaleEnforced());
    }
    
    @Test
    public void testLockedOut() {
        assertFalse(bb_org.isLockedOut());
        bb_org.setLockedOut(true);
        assertTrue(bb_org.isLockedOut());
    }

	@Test
    public void testName() {
    	assertNull(bb_org.getName());
    	bb_org.setName("name");
    	assertNotNull(bb_org.getName());
        assertEquals("name", bb_org.getName());
    }

    @Test
    public void testNavCollapsable() {
        assertFalse(bb_org.isNavCollapsable());
        bb_org.setNavCollapsable(true);
        assertTrue(bb_org.isNavCollapsable());
    }

    @Test
    public void testNavColorBg() {
    	assertNull(bb_org.getNavColorBg());
    	bb_org.setNavColorBg("#FFFFFF");
    	assertNotNull(bb_org.getNavColorBg());
        assertEquals("#FFFFFF", bb_org.getNavColorBg());
    }   

    @Test
    public void testNavColorFg() {
    	assertNull(bb_org.getNavColorFg());
    	bb_org.setNavColorFg("#FFFFFF");
    	assertNotNull(bb_org.getNavColorFg());
        assertEquals("#FFFFFF", bb_org.getNavColorFg());
    }

    @Test
    public void testNavigationStyle() {
    	assertNull(bb_org.getNavigationStyle());
    	bb_org.setNavigationStyle(BbOrganization.NAV_STYLE_TEXT);
    	assertNotNull(bb_org.getNavigationStyle());
        assertEquals(BbOrganization.NAV_STYLE_TEXT, bb_org.getNavigationStyle());

    }

    @Test
    public void testNumberOfDaysOfUse() {
        assertEquals(0, bb_org.getNumberOfDaysOfUse());
        bb_org.setNumberOfDaysOfUse(1);
    	assertEquals(1, bb_org.getNumberOfDaysOfUse());
    }

	@Test
	public void testOrganization() {
		assertTrue(bb_org.isOrganization());
	}

	@Test
    public void testShowInCatalog() {
        assertFalse(bb_org.isShowInCatalog());
        bb_org.setShowInCatalog(true);
        assertTrue(bb_org.isShowInCatalog());
    }

    @Test
    public void testSoftLimit() {
        assertEquals(0L, bb_org.getSoftLimit());
        bb_org.setSoftLimit(1L);
    	assertEquals(1L, bb_org.getSoftLimit());
    }

    @Test
    public void testStartDate() {
    	long now = Calendar.getInstance().getTimeInMillis();
    	long now_no_millis = Long.parseLong(String.valueOf(now).substring(0, 10) + "000");
    	assertEquals(0, bb_org.getStartDate());
    	bb_org.setStartDate(now);
        assertEquals(now_no_millis, bb_org.getStartDate());
    }

    @Test
    public void testUploadLimit() {
        assertEquals(0L, bb_org.getUploadLimit());
        bb_org.setUploadLimit(1L);
    	assertEquals(1L, bb_org.getUploadLimit());
    }
}
