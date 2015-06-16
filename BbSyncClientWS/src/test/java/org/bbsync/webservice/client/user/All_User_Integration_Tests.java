package org.bbsync.webservice.client.user;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestBbAddressBookEntry_Integration.class,
				TestBbObserverAssociation_Integration.class,
				TestBbPortalRole_Integration.class,
				TestBbUser_Integration.class,
				TestBbUser_SystemRoles_Integration.class,
				TestBbUserRole_Integration.class })
public class All_User_Integration_Tests {

}
