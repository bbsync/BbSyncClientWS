package org.bbsync.webservice.client.coursemembership;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestBbCourseMembership_Integration.class,
				TestBbGroupMembership_Integration.class,
				TestBbCourseMembershipRole_Integration.class,
				TestBbOrganizationMembership_Integration.class,
				TestBbOrganizationMembershipRole_Integration.class})
public class All_CourseMembership_Integration_Tests {

}
