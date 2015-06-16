/* 
 * Copyright 2015 Kurt Faulknerloser
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bbsync.webservice.client;

import org.bbsync.webservice.client.context.TestBbContext_Integration;
import org.bbsync.webservice.client.course.All_Course_Integration_Tests;
import org.bbsync.webservice.client.coursemembership.All_CourseMembership_Integration_Tests;
import org.bbsync.webservice.client.user.All_User_Integration_Tests;
import org.bbsync.webservice.client.util.All_Util_Integration_Tests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	TestBbContext_Integration.class,
	All_Course_Integration_Tests.class,
	All_CourseMembership_Integration_Tests.class,
	All_User_Integration_Tests.class,
	All_Util_Integration_Tests.class
})
public class All_Integration_Tests {

}
