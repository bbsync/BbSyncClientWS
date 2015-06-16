package org.bbsync.webservice.client.user;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestBbAddressBookEntry.class,
				TestBbObserverAssociation.class,
				TestBbPortalRole.class,
				TestBbUser.class,
				TestBbUserExtendedInfo.class,
				TestBbUserRole.class })
public class All_User_Unit_Tests {

}
