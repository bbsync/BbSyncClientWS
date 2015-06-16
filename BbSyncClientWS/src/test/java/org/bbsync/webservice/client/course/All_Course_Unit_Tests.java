package org.bbsync.webservice.client.course;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestBbCartridge.class, 
				TestBbClassification.class,
				TestBbCourse.class, 
				TestBbCourseCategory.class,
				TestBbCourseCategoryMembership.class, 
				TestBbGroup.class,
				TestBbOrganization.class, 
				TestBbOrgCategory.class,
				TestBbOrgCategoryMembership.class, 
				TestBbStaffInfoContact.class,
				TestBbStaffInfoFolder.class, 
				TestBbTerm.class })
public class All_Course_Unit_Tests {

}
