/* 
 * Copyright 2014 Kurt Faulknerloser
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

package org.bbsync.webservice.client.context;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.bbsync.utility.file.Configuration;
import org.bbsync.webservice.client.course.BbCourse;
import org.bbsync.webservice.client.course.BbOrganization;
import org.bbsync.webservice.client.coursemembership.BbCourseMembership;
import org.bbsync.webservice.client.coursemembership.BbCourseMembershipRole;
import org.bbsync.webservice.client.coursemembership.BbOrganizationMembership;
import org.bbsync.webservice.client.coursemembership.BbOrganizationMembershipRole;
import org.bbsync.webservice.client.generated.ContextWSStub;
import org.bbsync.webservice.client.user.BbUser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;


public class TestBbContext_Integration {
	private static final Logger logger = Logger.getLogger(TestBbContext_Integration.class.getName());
    private static Configuration config = Configuration.getInstance();
    private static final String PROXY_TOOL_NAME = "ContextProxyTool";
    private String shared_secret = null;
    //Basic User Data
    private static final String GIVEN_NAME = "Johnnie";
    private static final String FAMILY_NAME = "Walker";
    private static final String USER_NAME = "jw1111111";
    private static final String USER_PW = "password";
    private String basic_user_id = null;
    //Basic Course Data 
	private DateFormat formatDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mm:ssaa");
    private Date start_date = null;
    private Date end_date = null;
    private static final String COURSE_ID = "tst_crs_id";
    private static final String COURSE_BATCH_UID = "tst_crs_batch_uid";
    private static final String COURSE_NAME = "Test Course Name #1";
    private static final String DESCRIPTION = "This is the description for Test Course #1.";
    private String basic_course_id = null;
    //Basic Organization Data
	private static final String ORG_NAME = "Test Organization #1";
	private static final String ORG_ID = "tst_org_1";
	private static final String ORG_DESCRIPTION = "This is a test organization."; //not required to create an Organization
	private String basic_org_id = null; 

    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        shared_secret = config.getConfigClass(PROXY_TOOL_NAME, "sharedSecret");
    }

    @After
    public void tearDown() throws Exception {
    }
    
    /**
     * Can't get this test to work.  Server log shows that a null pointer 
     * exception is encountered in the ContextWSImpl.class, 
     * deactivateTool(String) method.  The class can be found on the server
     * in bb-ws-context.jar.  Here is the error:
     * 
     * "Error deactivating proxy tool -- Client Vendor ID: bbsync.org; 
     * Client Program ID: ContextProxyTool - java.lang.NullPointerException"
     * 
     * Since the error message's Vendor ID & Program ID are filled in, the 
     * session seems to be initialized correctly...
     * 
     * Be sure that the tool to be deactivated has been configured to use the 
     * tool method "Context.WS:deactivateTool" in conf.xml.
     */
    @Ignore
    public void testDeactivateTool(){
        BbContext bb_context = new BbContext();
        assertTrue(bb_context.loginAsTool(PROXY_TOOL_NAME, shared_secret));
        //assertTrue(bb_context.loginAsTool("UtilProxyTool", shared_secret));
        BbDeactivateToolResult result = bb_context.deactivateTool();
        assertTrue(bb_context.logout());
        assertNotNull(result);
        assertTrue(result.getStatus());
    }
    
    @Test
    public void testEmulateUser(){
    	String[] course_ids = null;
    	createBasicCourse();
    	createBasicUser();
    	createBasicCourseMembership_student();
        BbContext bb_context = new BbContext();
        //login as a tool
        assertTrue(bb_context.loginAsTool(PROXY_TOOL_NAME, shared_secret));
        //perform a tool-session task (this will work)
        course_ids = bb_context.getUserCourses(USER_NAME);
        assertNotNull(course_ids);
        assertTrue(course_ids.length==1);
        //perform a user-session task (this won't work)
        course_ids = bb_context.getMyCourses();
        assertNotNull(course_ids);
        assertTrue(course_ids.length==0);
        
        //** Now, emulate a regular user ************
        assertTrue(bb_context.emulateUser(USER_NAME));
        
        //perform a tool-session task (this won't work)
        course_ids = bb_context.getUserCourses(USER_NAME);
        assertNull(course_ids);
        //perform a user-session task (this will work)
        course_ids = bb_context.getMyCourses();
        assertNotNull(course_ids);
        assertTrue(course_ids.length==1);
        
        //** turn user emulation off by passing null **
        assertTrue(bb_context.emulateUser(null));
        
        //perform a tool-session task (this will work)
        course_ids = bb_context.getUserCourses(USER_NAME);
        assertNotNull(course_ids);
        assertTrue(course_ids.length==1);
        //perform a user-session task (this won't work)
        course_ids = bb_context.getMyCourses();
        assertNotNull(course_ids);
        assertTrue(course_ids.length==0);
        //logout the tool & end the session
        assertTrue(bb_context.logout());
        deleteUser(basic_user_id);
        deleteCourse(basic_course_id);
    }

    
    
    /**
     * Default session life is configured in the global section of the 
     * configuration file: <param name="sessionTimeOutSeconds" value="30"/>.
     */
    @Test
    public void testExtendSessionLife_Tool() {
        BbContext bb_context = new BbContext();
        assertTrue(bb_context.loginAsTool(PROXY_TOOL_NAME, shared_secret));
        assertTrue(bb_context.extendSessionLife(10L));
        assertTrue(bb_context.logout());
    }

    /**
     * Default session life is configured in the global section of the 
     * configuration file: <param name="sessionTimeOutSeconds" value="30"/>.
     */
    @Test
    public void testExtendSessionLife_User() {
        BbContext bb_context = new BbContext();
        assertTrue(bb_context.loginAsUser(PROXY_TOOL_NAME, config.getConfigClass("GLOBAL", "admin_user"), config.getConfigClass("GLOBAL", "admin_pw")));
        assertTrue(bb_context.extendSessionLife(10L));
        assertTrue(bb_context.logout());
    }    

    /**
     * This test illustrates that an unprivileged user can use 
     * BbContext.getMyCourseEnrollments() to login & view their own 
     * enrollments.
     */
    @Test
    public void testGetMyCourseEnrollments_user_login(){
    	createBasicCourse();
    	createBasicOrganization();
    	createBasicUser();
    	createBasicCourseMembership_student();
    	createBasicOrgMembership_participant();
        BbContext bb_context = new BbContext();
        assertTrue(bb_context.loginAsUser(PROXY_TOOL_NAME, USER_NAME, USER_PW));
        String[] course_ids = bb_context.getMyCourses();
        assertNotNull(course_ids);
        assertTrue(course_ids.length==2);
        assertTrue(bb_context.logout());
        deleteUser(basic_user_id);
        deleteCourse(basic_course_id);
        deleteOrganization(basic_org_id);
    }

    /**
     * This test demonstrates that when logged in as a tool, 
     * getMyCourseEnrollments() will return an empty set as stated in the
     * Blackboard API for ContextProxyTool.getMyMemberships().
     */
    @Test
    public void testGetMyCourseEnrollments_tool_login(){
        BbContext bb_context = new BbContext();
        assertTrue(bb_context.loginAsTool(PROXY_TOOL_NAME, shared_secret));
        String[] course_ids = bb_context.getMyCourses();
        assertNotNull(course_ids);
        assertTrue(course_ids.length==0);
        assertTrue(bb_context.logout());
    }
    
    @Test
    public void testGetCourseEnrollments_tool_login_no_enrollments(){
    	createBasicUser();
        BbContext bb_context = new BbContext();
        assertTrue(bb_context.loginAsTool(PROXY_TOOL_NAME, shared_secret));
        String[] course_ids = bb_context.getUserCourses(USER_NAME);
        assertNull(course_ids);
        assertTrue(bb_context.logout());
        deleteUser(basic_user_id);
    }

    @Test
    public void testGetCourseEnrollments_tool_login(){
    	createBasicCourse();
    	createBasicUser();
    	createBasicCourseMembership_student();
        BbContext bb_context = new BbContext();
        assertTrue(bb_context.loginAsTool(PROXY_TOOL_NAME, shared_secret));
        String[] course_ids = bb_context.getUserCourses(USER_NAME);
        assertNotNull(course_ids);
        assertTrue(course_ids.length==1);
        assertTrue(bb_context.logout());
        deleteUser(basic_user_id);
        deleteCourse(basic_course_id);
    }
    
    /**
     * This test demonstrates that when logged in as a privileged user (in this
     * case, the system administrator), getCourseEnrollments(USER_NAME) behaves
     * exactly as if we had logged in using tool login.
     */
    @Test
    public void testGetCourseEnrollments_admin_login(){
    	createBasicCourse();
    	createBasicUser();
    	createBasicCourseMembership_student();
        BbContext bb_context = new BbContext();
        assertTrue(bb_context.loginAsUser(PROXY_TOOL_NAME, config.getConfigClass("GLOBAL", "admin_user"), config.getConfigClass("GLOBAL", "admin_pw")));
        String[] course_ids = bb_context.getUserCourses(USER_NAME);
        assertNotNull(course_ids);
        assertTrue(course_ids.length==1);
        assertTrue(bb_context.logout());
        deleteUser(basic_user_id);
        deleteCourse(basic_course_id);
    }

    /**
     * This test illustrates that an unprivileged user cannot use 
     * BbContext.getCourseEnrollments(String user_name) to login & view their 
     * own enrollments.  To do this, the user must have permissions for
     * webservices.tools.getMemberships.VIEW.
     * 
     * This test will generate an "Access Denied" error.
     */
    @Test
    public void testGetCourseEnrollments_user_login(){
    	createBasicCourse();
    	createBasicUser();
    	createBasicCourseMembership_student();
        BbContext bb_context = new BbContext();
        assertTrue(bb_context.loginAsUser(PROXY_TOOL_NAME, USER_NAME, USER_PW));
        String[] course_ids = bb_context.getUserCourses(USER_NAME);
        assertNull(course_ids);
        assertTrue(bb_context.logout());
        deleteUser(basic_user_id);
        deleteCourse(basic_course_id);
    }
    
    /**
     * This method gets a list of all of the ContextWSStub methods and will
     * list all of the required entitlements for each method.  Entitlements can
     * be viewed by adjusting the logging method. 
     */
    @Test
    public void testGetRequiredEntitlements() {
        BbContext bb_context = new BbContext();
        String[] entitlements = null;
        Method[] methods = ContextWSStub.class.getMethods();

        for (int i = 0; i < methods.length; i++) {
            if ((methods[i].getDeclaringClass() == ContextWSStub.class) &&
                    !methods[i].getName().startsWith("start")) {
                String name = methods[i].getName();
                assertNotNull(name);
                entitlements = bb_context.getRequiredEntitlements(name);

                if (entitlements != null) {
                    for (int j = 0; j < entitlements.length; j++) {
                    	logger.debug(name + "() entitlements: " +
                            entitlements[j]);
                    }
                }
            }
        }
    }

    @Test
    public void testGetServerVersion() {
        BbContext bb_context = new BbContext();
        Long result = bb_context.getServerVersion();
        assertNotNull(result);
        assertTrue(result > 0L);
        logger.debug("Server Version: " + String.valueOf(result));
    }
    
    @Test
    public void testGetSystemInstallationId(){
        BbContext bb_context = new BbContext();
        String result = bb_context.getSystemInstallationId();
        assertNotNull(result);
        logger.debug("System Installation ID: " + result);
    }

    @Test
    public void testInitialize() {
        BbContext bb_context = new BbContext();
        String result = bb_context.initialize();
        assertNotNull(result);
        logger.debug("v1 Session ID: " + result);
    }

    @Test
    public void testInitializeVersion2() {
        BbContext bb_context = new BbContext();
        String result = bb_context.initializeVersion2();
        assertNotNull(result);
        assertTrue(result.length()==32);
        logger.debug("V2 Session ID: " + result);
    }

    @Test
    public void testLoginAsUser() {
        BbContext bb_context = new BbContext();
        assertTrue(bb_context.loginAsUser(PROXY_TOOL_NAME, config.getConfigClass("GLOBAL", "admin_user"), config.getConfigClass("GLOBAL", "admin_pw")));
        assertTrue(bb_context.logout());
    }
    
    /**
     * Because ticket login is used by external proxy tools, it is difficult
     * to write a simple, self-contained test.
     */
    @Ignore
    public void testLoginAsTicket() {
    	/*
    	 * When registering a Proxy Tool, the description field must be the 
    	 * Proxy Tool XML description for your tool. Refer to the sample Proxy
    	 * Tool server code, ProxyUtil.buildToolDescription(...) for some 
    	 * sample code that builds an XML description for a proxy tool.  
    	 * http://www.edugarage.com/display/BBDN/Proxy+Tool+XML+Description
    	 * 
    	 * When registering a Proxy Tool the result will give you both a 
    	 * true/false success and a proxytoolguid field. This guid is a unique 
    	 * identifier generated by Academic Suite associated with this tool 
    	 * registration. You must record this GUID and use it to correlate 
    	 * future requests from the server with your local configuration (for 
    	 * example, you will need to store a shared secret per server to 
    	 * validate requests from the server - you would store this keyed on 
    	 * the GUID returned). Refer to RegisterAction.java + ProxyUtil.register() 
    	 * for a sample of tool registration and the storage of the GUID. 
    	 */
    	BbContext bb_context = new BbContext();
        //assertTrue(bb_context.loginWithTicket(PROXY_TOOL_NAME, "ticket value"));
        assertTrue(bb_context.logout());
    }
    
    @Test
    public void testLoginAsTool() {
        BbContext bb_context = new BbContext();
        assertTrue(bb_context.loginAsTool(PROXY_TOOL_NAME, shared_secret));
        assertTrue(bb_context.logout());
    }
    
    /**
     * Logout is tested along with each of the login tests.
     */
    @Ignore
    public void testLogout(){
    	
    }
    
	private void createBasicUser(){
		BbUser bb_user = new BbUser();
	    bb_user.setGivenName(GIVEN_NAME);
	    bb_user.setFamilyName(FAMILY_NAME);
	    bb_user.setName(USER_NAME);
	    bb_user.setPassword(USER_PW);
	    bb_user.setAvailable(Boolean.TRUE);
	    basic_user_id = (String) bb_user.persist();
	    assertNotNull(basic_user_id);
	}
	
	private void deleteUser(String bb_user_id){
		BbUser bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		assertTrue(bb_user.delete());
    }
	
	private void createBasicCourse(){
		try {
			start_date = (Date) formatDateTime.parse("10/15/2011 01:01:00AM");
			 end_date = (Date) formatDateTime.parse("10/16/2011 11:59:59PM");
		} catch (ParseException e) {
			logger.error("unable to create basic course");
			fail();
		}
        BbCourse bb_course = new BbCourse();
	    bb_course.setStartDate(start_date.getTime());
	    bb_course.setEndDate(end_date.getTime());
	    bb_course.setCourseId(COURSE_ID);
	    bb_course.setName(COURSE_NAME);
	    bb_course.setDescription(DESCRIPTION);
	    bb_course.setBatchUid(COURSE_BATCH_UID);
	    bb_course.setAvailable(Boolean.TRUE);
	    basic_course_id = (String) bb_course.persist();
	    assertNotNull(basic_course_id);
	}
	
	private void deleteCourse(String bb_course_id){
		BbCourse bb_course = new BbCourse();
		bb_course.setId(bb_course_id);
		assertTrue(bb_course.delete());
    }
	
	private String createBasicCourseMembership_student(){
		BbCourseMembership bbcm = new BbCourseMembership();
		bbcm.setCourseId(basic_course_id);
		bbcm.setUserId(basic_user_id);
		bbcm.setAvailable(Boolean.TRUE);
		bbcm.setRoleId(BbCourseMembershipRole.COURSE_ROLE_STUDENT);
		return (String)bbcm.persist();
	}
	
	private void deleteOrganization(String bb_org_id) {
        BbOrganization bb_org = new BbOrganization();
        bb_org.setId(bb_org_id);
        assertTrue(bb_org.delete());
    }

    private void createBasicOrganization(){
        BbOrganization bb_org = new BbOrganization();
        bb_org.setOrganizationId(ORG_ID);
        bb_org.setName(ORG_NAME);
        bb_org.setDescription(ORG_DESCRIPTION); //not required to create an Organization
        String id = bb_org.createOrg(bb_org);
        assertNotNull(id);
        basic_org_id = id;
        logger.debug("Created Organization with ID: " + id);
    }
    
    private String createBasicOrgMembership_participant(){
		BbOrganizationMembership bbom = new BbOrganizationMembership();
		bbom.setOrganizationId(basic_org_id);
		bbom.setUserId(basic_user_id);
		bbom.setAvailable(Boolean.TRUE);
		bbom.setRoleId(BbOrganizationMembershipRole.ORG_ROLE_PARTICIPANT);
		return (String)bbom.persist();
	}
}
