package org.bbsync.webservice.client.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestBbDataSource_Integration.class,
				TestBbSetting_Integration.class,
				TestBbUtil_Integration.class })
public class All_Util_Integration_Tests {
}
