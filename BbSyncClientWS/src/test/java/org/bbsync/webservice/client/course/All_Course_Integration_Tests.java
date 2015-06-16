package org.bbsync.webservice.client.course;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestBbCartridge_Integration.class,
				TestBbClassification_Integration.class, 
				TestBbCourse_Integration.class,
				TestBbCourseCategory_Integration.class,
				TestBbCourseCategoryMembership_Integration.class,
				TestBbGroup_Integration.class, 
				TestBbOrganization_Integration.class,
				TestBbOrgCategory_Integration.class,
				TestBbOrgCategoryMembership_Integration.class,
				TestBbStaffInfoContact_Integration.class,
				TestBbStaffInfoFolder_Integration.class, 
				TestBbTerm_Integration.class })
public class All_Course_Integration_Tests {

}
