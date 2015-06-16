package org.bbsync.webservice.client.coursemembership;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({TestBbCourseMembership.class, 
			   TestBbGroupMembership.class,
		       TestBbCourseMembershipRole.class,
		       TestBbOrganizationMembership.class})

public class All_CourseMembership_Unit_Tests {

}
