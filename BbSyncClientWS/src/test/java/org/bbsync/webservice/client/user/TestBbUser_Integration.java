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

package org.bbsync.webservice.client.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.course.BbCourse;
import org.bbsync.webservice.client.course.BbGroup;
import org.bbsync.webservice.client.coursemembership.BbCourseMembership;
import org.bbsync.webservice.client.coursemembership.BbCourseMembershipRole;
import org.bbsync.webservice.client.coursemembership.BbGroupMembership;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbUser_Integration {
	private static final Logger logger = Logger.getLogger(TestBbUser_Integration.class.getName());
	private DateFormat formatDateTime = new SimpleDateFormat("MM/dd/yyyy hh:mm:ssaa");
	private DateFormat formatDate = new SimpleDateFormat("MM/dd/yyyy");
    private Date start_date = null;
    private Date end_date = null;
	//Full User Info
	private static final String FIRST_NAME = "Henry";
	private static final String LAST_NAME  = "Jones";
	private static final String USER_NAME  = "hj1234567";
	private static final String PASSWORD   = "ilovearcheology";
	private static final boolean AVAILABLE   = true;
	private static final String BATCH_UID   = "test_batch_uid";
	private static final String BIRTH_DATE = "07/01/1889";
	private static final String BUSINESS_FAX = "(123)456-7777";
	private static final String BUSINESS_PH1 = "(123)456-7888";
	private static final String BUSINESS_PH2 = "(123)456-7899";
	private static final String CITY = "Chicago";
	private static final String COUNTRY = "USA";
	private static final String COMPANY = "University of Chicago";
	private static final String DEPARTMENT = "History";
	private static final String EMAIL = "hwjones@marshall.edu";
	private static final String HOME_FAX = "(123)456-7777";
	private static final String HOME_PH1 = "(123)456-7888";
	private static final String HOME_PH2 = "(123)456-7899";
	private static final String[] INSTITUTION_ROLES = new String[]{BbPortalRole.INSTITUTION_ROLE_FACULTY, BbPortalRole.INSTITUTION_ROLE_STAFF, BbPortalRole.INSTITUTION_ROLE_STUDENT};
	private static final String JOB_TITLE = "Professor";
	private static final String MIDDLE_NAME = "Walton";
	private static final String MOBILE_PHONE = "(123)456-9999";
	private static final String NICKNAME = "Indiana";
	private static final String SIS_ID = "1234567";
	private static final String STATE = "Illonois";
	private static final String STREET1 = "University of Chicago";
	private static final String STREET2 = "1101 East 58th Street";
	private static final String SUFFIX = "Jr.";
	private static final String TITLE = "Doctor";
	private static final String WEB_PAGE = "http://en.wikipedia.org/wiki/Indiana_Jones";
	private static final String ZIPCODE = "60637";
	//Basic User #1
    private static final String BASIC_GIVEN_NAME_1 = "Johnnie";
    private static final String BASIC_FAMILY_NAME_1 = "Walker";
    private static final String BASIC_USER_NAME_1 = "jw1111111";
    private static final String BASIC_USER_PW_1 = "password1";
	private String basic_user_id_1 = null;
	//Basic User #2
    private static final String BASIC_GIVEN_NAME_2 = "Jack";
    private static final String BASIC_FAMILY_NAME_2 = "Daniels";
    private static final String BASIC_USER_NAME_2 = "jd2222222";
    private static final String BASIC_USER_PW_2 = "password2";	
	private String basic_user_id_2 = null;
	//Basic Course #1
    private static final String COURSE_ID_1 = "tst_crs_id_1";
    private static final String COURSE_BATCH_UID_1 = "tst_crs_batch_uid_1";
    private static final String COURSE_NAME_1 = "Test Course Name #1";
    private static final String DESCRIPTION_1 = "This is the description for Test Course #1.";
    private String basic_course_id_1 = null;
    //Basic Course #2
    private static final String COURSE_ID_2 = "tst_crs_id_2";
    private static final String COURSE_BATCH_UID_2 = "tst_crs_batch_uid_2";
    private static final String COURSE_NAME_2 = "Test Course Name #2";
    private static final String DESCRIPTION_2 = "This is the description for Test Course #2.";
	private String basic_course_id_2 = null;
	//Basic Group #1
	private static final String GROUP_NAME_1 = "Test Group #1 Name";
	private static final String GROUP_DESCRPTION_1 = "Test Group #1 Description";
	private String basic_group_id_1 = null;
	//Basic Group #2
	private static final String GROUP_NAME_2 = "Test Group #2 Name";
	private static final String GROUP_DESCRPTION_2 = "Test Group #2 Description";
	private String basic_group_id_2 = null;
	//Basic Group #3
	private static final String GROUP_NAME_3 = "Test Group #3 Name";
	private static final String GROUP_DESCRPTION_3 = "Test Group #3 Description";
	private String basic_group_id_3 = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
    public void testGetServerVersion() {
        BbUser bb_user = new BbUser();
        Long version = bb_user.getServerVersion();
        assertNotNull(version);
        assertTrue(version>0L);
        Logger.getLogger(this.getClass().getName()).debug("Server Version: " + String.valueOf(version));
    }
	
	@Test
	public void testGetSystemRoles(){
    	BbUser bb_user = new BbUser();
    	String[] roles = bb_user.getAllSystemRoles();
    	assertNotNull(roles);
	}
	
    /**
     * This test creates a user with the minimum required User data fields.
     * Specifically, the minimum required fields are are:
     *  - Given/First Name
     *  - Family/Last Name
     *  - Login/User Name
     *  - Password
     */
    @Test
    public void testCreateUser_basic() {
        //Create a basic user with minimum user info
        BbUser bb_user = setupBasicUser();
        //Save the User to Blackboard
        String id = (String) bb_user.persist();
        assertNotNull(id);
        Logger.getLogger(this.getClass().getName()).debug("User created successfully.  Blackboard returned ID: " + id);
        //now retrieve the user from Blackboard so we can verify what was created
        bb_user = (BbUser) bb_user.retrieve();
        assertNotNull(bb_user);
        //test that the basic user info is intact
        testBasicUser(bb_user, id);
        //delete the user from Blackboard
        deleteUser(bb_user);
    }
    
    /**
     * The purpose of this test is to try to get users using the "GET_ALL_USERS_WITH_AVAILABILITY" filter.
     * 
     * Unfortunately, searching for users with this filter doesn't seem to work, the server just immediately 
     * returns a null.  No error messages & no exceptions.  The server just returns null - as if the search 
     * didn't find what it was looking for.
     * 
     * For that reason, I'm creating a negative test so that if this functionality ever DOES start working, 
     * it will be obvious.
     */
    @Test
    public void testGetAllUsers_Negative_Test() {
    	createBasicUser1();
    	BbUser bb_user = new BbUser();
    	BbUser[] bb_users = bb_user.getAllUsers();
        assertNull(bb_users); //negative test
        bb_users = bb_user.getAllUsersWithAvailability(true);
        assertNull(bb_users); //negative test
        bb_users = bb_user.getAllUsersWithAvailability(false);
        assertNull(bb_users); //negative test
        bb_user.setId(basic_user_id_1);
        bb_user.setName(BASIC_USER_NAME_1);
        deleteUser(bb_user);
    }
    
    /**
     * Don't use this test on a production server.  The "all users" query may cause the server to crash
     * if there are a large number of users
     */
    @Test
    public void testGetUsersBySystemRole() {
    	createBasicUser1();
    	BbUser bb_user = new BbUser();
        //BbUser[] bb_users = bb_user.getUsersBySystemRole(BbUser.SYSTEM_ROLE_NONE);
        BbUser[] bb_users = bb_user.getUsersBySystemRole("N");
        assertNotNull(bb_users); //negative test
        bb_user.setId(basic_user_id_1);
        bb_user.setName(BASIC_USER_NAME_1);
        deleteUser(bb_user);
    }
    
    /**
     * This test demonstrates how to get all of the users that are enrolled in 
     * a course.  Note that when the method availability is set to true, only 
     * users with availability set to true are returned.  When method 
     * availability is set to false, all users are returned regardless of their 
     * user availability setting.  
     */
    @Test
    public void testGetUsersByCourseId_w_UserAvailability(){
    	//setup 1 course with 2 users all availability set to true
    	createBasicCourse1();
    	createBasicUser1();
    	createBasicUser2();
    	createCourseMembership_Student(basic_course_id_1, basic_user_id_1, true);
    	createCourseMembership_Instructor(basic_course_id_1, basic_user_id_2, true);
    	//perform test with availability set to true
    	BbUser bb_user = new BbUser();
    	BbUser[] bb_users = bb_user.getUsersByCourseIdWithAvailability(new String[]{basic_course_id_1}, true);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	//perform test with availability set to false
    	bb_user = new BbUser();
    	bb_users = bb_user.getUsersByCourseIdWithAvailability(new String[]{basic_course_id_1}, false);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	//change one user's availability to false & try again
    	bb_user.setId(basic_user_id_1);
    	bb_user = (BbUser)bb_user.retrieve();
    	assertNotNull(bb_user);
    	bb_user.setAvailable(false);
    	bb_user.persist();
    	//perform test with availability set to true
    	bb_user = new BbUser();
    	bb_users = bb_user.getUsersByCourseIdWithAvailability(new String[]{basic_course_id_1}, true);
    	assertNotNull(bb_users);
    	assertEquals(1, bb_users.length);
    	assertEquals(basic_user_id_2, bb_users[0].getId());
    	//perform test with availability set to false
    	bb_user = new BbUser();
    	bb_users = bb_user.getUsersByCourseIdWithAvailability(new String[]{basic_course_id_1}, false);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	deleteUser(bb_users[0]);
        deleteUser(bb_users[1]);
    	deleteCourse(basic_course_id_1);
    }

    /**
     * This test demonstrates that when retrieving users by Course ID,  course
     * membership availability has no effect on the availability setting of 
     * this method.  That is all users enrolled in the course will be returned 
     * regardless of their membership availability - as long as the actual user
     * availability is set to true.
     */
    @Test
    public void testGetUsersByCourseId_w_MembershipAvailability(){
    	//setup 1 course with 2 users all availability set to true
    	createBasicCourse1();
    	createBasicUser1();
    	createBasicUser2();
    	createCourseMembership_Student(basic_course_id_1, basic_user_id_1, true);
    	createCourseMembership_Instructor(basic_course_id_1, basic_user_id_2, true);
    	//perform test with availability set to true
    	BbUser bb_user = new BbUser();
    	BbUser[] bb_users = bb_user.getUsersByCourseIdWithAvailability(new String[]{basic_course_id_1}, true);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	//perform test with availability set to false
    	bb_user = new BbUser();
    	bb_users = bb_user.getUsersByCourseIdWithAvailability(new String[]{basic_course_id_1}, false);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	//Change one membership's availability to false & try again
    	BbCourseMembership membership = new BbCourseMembership();
    	BbCourseMembership[] memberships = membership.getCourseMembershipsByUserIds(basic_course_id_1, new String[]{basic_user_id_1});
    	assertNotNull(memberships);
    	assertEquals(1, memberships.length);
    	memberships[0].setAvailable(false);
    	memberships[0].persist();
    	//perform test with availability set to true
    	bb_user = new BbUser();
    	bb_users = bb_user.getUsersByCourseIdWithAvailability(new String[]{basic_course_id_1}, true);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	//perform test with availability set to false
    	bb_user = new BbUser();
    	bb_users = bb_user.getUsersByCourseIdWithAvailability(new String[]{basic_course_id_1}, false);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	deleteUser(bb_users[0]);
        deleteUser(bb_users[1]);
    	deleteCourse(basic_course_id_1);
    }

    
    /**
     * This test demonstrates that users can be returned from multiple courses. 
     * Note that if a users are enrolled in multiple courses, duplicate users 
     * will be returned in the results.
     */
    @Test
    public void testGetUsersByCourseIdWithAvailability_multi_course(){
    	createBasicCourse1();
    	createBasicCourse2();
    	createBasicUser1();
    	createBasicUser2();
    	createCourseMembership_Student(basic_course_id_1, basic_user_id_1, true);
    	createCourseMembership_Instructor(basic_course_id_1, basic_user_id_2, true);
    	createCourseMembership_Instructor(basic_course_id_2, basic_user_id_2, true);
    	BbUser bb_user = new BbUser();
    	BbUser[] bb_users = bb_user.getUsersByCourseIdWithAvailability(new String[]{basic_course_id_1, basic_course_id_2}, true);
    	assertNotNull(bb_users);
    	assertEquals(3, bb_users.length);
    	//change one user's availability to false & try again
    	bb_user.setId(basic_user_id_1);
    	bb_user = (BbUser)bb_user.retrieve();
    	assertNotNull(bb_user);
    	bb_user.setAvailable(false);
    	bb_user.persist();
    	bb_user = new BbUser();
    	//Because user1 is not available, only user2 records are returned
    	bb_users = bb_user.getUsersByCourseIdWithAvailability(new String[]{basic_course_id_1, basic_course_id_2}, true);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	assertEquals(basic_user_id_2, bb_users[0].getId());
    	assertEquals(basic_user_id_2, bb_users[1].getId());
    	deleteUser(bb_users[0]); //delete user2
    	//Because user2 was deleted & we're not filtering for availability, the remaining user1 record is returned
    	bb_users = bb_user.getUsersByCourseIdWithAvailability(new String[]{basic_course_id_1, basic_course_id_2}, false);
    	assertNotNull(bb_users);
    	assertEquals(1, bb_users.length);
    	assertEquals(basic_user_id_1, bb_users[0].getId());
    	deleteUser(bb_users[0]); //delete user1
    	deleteCourse(basic_course_id_1);
    	deleteCourse(basic_course_id_2);
    }

    /**
     * This test demonstrates how to get all of the users that are members of 
     * a course group.  Note that when the method availability is set to true, 
     * only users with availability set to true are returned.  When method 
     * availability is set to false, all users are returned regardless of their 
     * user availability setting.  
     */
    @Test
    public void testGetUsersByGroupId_w_UserAvailability(){
    	//setup 1 course with 2 users all availability set to true
    	createBasicCourse1();
    	createBasicUser1();
    	createBasicUser2();
    	String cm_id_1 = createCourseMembership_Student(basic_course_id_1, basic_user_id_1, true);
    	String cm_id_2 = createCourseMembership_Student(basic_course_id_1, basic_user_id_2, true);
    	createBasicGroup1(basic_course_id_1);
    	createGroupMembership(basic_course_id_1, basic_group_id_1, cm_id_1);
    	createGroupMembership(basic_course_id_1, basic_group_id_1, cm_id_2);
    	//perform test with availability set to true
    	BbUser bb_user = new BbUser();
    	BbUser[] bb_users = bb_user.getUsersByGroupIdWithAvailability(new String[]{basic_group_id_1}, true);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	//perform test with availability set to false
    	bb_user = new BbUser();
    	bb_users = bb_user.getUsersByGroupIdWithAvailability(new String[]{basic_group_id_1}, false);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	//change one user's availability to false & try again
    	bb_user.setId(basic_user_id_1);
    	bb_user = (BbUser)bb_user.retrieve();
    	assertNotNull(bb_user);
    	bb_user.setAvailable(false);
    	bb_user.persist();
    	//perform test with availability set to true
    	bb_user = new BbUser();
    	bb_users = bb_user.getUsersByGroupIdWithAvailability(new String[]{basic_group_id_1}, true);
    	assertNotNull(bb_users);
    	assertEquals(1, bb_users.length);
    	assertEquals(basic_user_id_2, bb_users[0].getId());
    	//perform test with availability set to false
    	bb_user = new BbUser();
    	bb_users = bb_user.getUsersByGroupIdWithAvailability(new String[]{basic_group_id_1}, false);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	deleteUser(bb_users[0]);
        deleteUser(bb_users[1]);
        deleteGroup(basic_group_id_1);
    	deleteCourse(basic_course_id_1);
    }

    /**
     * This test demonstrates that when retrieving users by Group ID,  course
     * membership availability has no effect on the availability setting of 
     * this method.  That is all users enrolled in the group will be returned 
     * regardless of their course membership availability - as long as the 
     * actual user availability is set to true.
     */
    @Test
    public void testGetUsersByGroupId_w_MembershipAvailability(){
    	//setup 1 course with 2 users all availability set to true
    	createBasicCourse1();
    	createBasicUser1();
    	createBasicUser2();
    	String cm_id_1 = createCourseMembership_Student(basic_course_id_1, basic_user_id_1, true);
    	String cm_id_2 = createCourseMembership_Student(basic_course_id_1, basic_user_id_2, true);
    	createBasicGroup1(basic_course_id_1);
    	createGroupMembership(basic_course_id_1, basic_group_id_1, cm_id_1);
    	createGroupMembership(basic_course_id_1, basic_group_id_1, cm_id_2);
    	//perform test with availability set to true
    	BbUser bb_user = new BbUser();
    	BbUser[] bb_users = bb_user.getUsersByGroupIdWithAvailability(new String[]{basic_group_id_1}, true);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	//perform test with availability set to false
    	bb_user = new BbUser();
    	bb_users = bb_user.getUsersByGroupIdWithAvailability(new String[]{basic_group_id_1}, false);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	//Change one membership's availability to false & try again
    	BbCourseMembership membership = new BbCourseMembership();
    	BbCourseMembership[] memberships = membership.getCourseMembershipsByUserIds(basic_course_id_1, new String[]{basic_user_id_1});
    	assertNotNull(memberships);
    	assertEquals(1, memberships.length);
    	memberships[0].setAvailable(false);
    	memberships[0].persist();
    	//perform test with availability set to true
    	bb_user = new BbUser();
    	bb_users = bb_user.getUsersByGroupIdWithAvailability(new String[]{basic_group_id_1}, true);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	//perform test with availability set to false
    	bb_user = new BbUser();
    	bb_users = bb_user.getUsersByGroupIdWithAvailability(new String[]{basic_group_id_1}, false);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	deleteUser(bb_users[0]);
        deleteUser(bb_users[1]);
    	deleteCourse(basic_course_id_1);
    }

    
    /**
     * This test demonstrates that users can be returned from multiple groups. 
     * Note that if users are members of multiple groups, duplicate users will
     * be returned in the results.  This is true whether or not the specified
     * groups are part of the same course.
     */
    @Test
    public void testGetUsersByGroupIdWithAvailability_multi_group(){
    	createBasicCourse1();
    	createBasicCourse2();
    	createBasicUser1();
    	createBasicUser2();
    	String cm_id_1_1 = createCourseMembership_Student(basic_course_id_1, basic_user_id_1, true);
    	String cm_id_1_2 = createCourseMembership_Student(basic_course_id_1, basic_user_id_2, true);
    	String cm_id_2_1 = createCourseMembership_Student(basic_course_id_2, basic_user_id_1, true);
    	String cm_id_2_2 = createCourseMembership_Student(basic_course_id_2, basic_user_id_2, true);
    	createBasicGroup1(basic_course_id_1);
    	createBasicGroup2(basic_course_id_1);
    	createBasicGroup3(basic_course_id_2);
    	createGroupMembership(basic_course_id_1, basic_group_id_1, cm_id_1_1); //course1, group1, user1
    	createGroupMembership(basic_course_id_1, basic_group_id_2, cm_id_1_1); //course1, group2, user1
    	createGroupMembership(basic_course_id_1, basic_group_id_2, cm_id_1_2); //course1, group2, user2 
    	createGroupMembership(basic_course_id_2, basic_group_id_1, cm_id_2_1); //course2, group1, user1
    	createGroupMembership(basic_course_id_2, basic_group_id_1, cm_id_2_2); //course2, group1, user2
    	BbUser bb_user = new BbUser();
    	BbUser[] bb_users = bb_user.getUsersByGroupIdWithAvailability(new String[]{basic_group_id_1, basic_group_id_2, basic_group_id_3}, true);
    	assertNotNull(bb_users);
    	assertEquals(5, bb_users.length);
    	//change one user's availability to false & try again
    	bb_user.setId(basic_user_id_1);
    	bb_user = (BbUser)bb_user.retrieve();
    	assertNotNull(bb_user);
    	bb_user.setAvailable(false);
    	bb_user.persist();
    	bb_user = new BbUser();
    	//Because user1 is not available, only user2 records are returned
    	bb_users = bb_user.getUsersByGroupIdWithAvailability(new String[]{basic_group_id_1, basic_group_id_2, basic_group_id_3}, true);
    	assertNotNull(bb_users);
    	assertEquals(2, bb_users.length);
    	assertEquals(basic_user_id_2, bb_users[0].getId());
    	assertEquals(basic_user_id_2, bb_users[1].getId());
    	deleteUser(bb_users[0]); //delete user2
    	//Because user2 was deleted & we're not filtering for availability, all user1 records are returned
    	bb_users = bb_user.getUsersByGroupIdWithAvailability(new String[]{basic_group_id_1, basic_group_id_2, basic_group_id_3}, false);
    	assertNotNull(bb_users);
    	assertEquals(3, bb_users.length);
    	assertEquals(basic_user_id_1, bb_users[0].getId());
    	assertEquals(basic_user_id_1, bb_users[1].getId());
    	assertEquals(basic_user_id_1, bb_users[2].getId());
    	deleteUser(bb_users[0]); //delete user1
    	deleteCourse(basic_course_id_1);
    	deleteCourse(basic_course_id_2);
    }

    /**
     * This test creates a user with the minimum required User data fields.
     * Specifically, the minimum required fields are are:
     *  - Given/First Name
     *  - Family/Last Name
     *  - Login/User Name
     *  - Password
     *  It then attempts to set the Gender Type according to the Web Services API.  Since this
     *  operation doesn't seem to work, I'm creating this negative test so that if it ever DOES
     *  start working, it will be obvious.
     *  
     *  setGenderType() doesn't seem to work.  Any value entered here results in the following return string:
     * 	   blackboard.data.user.User$Gender:UNKNOWN
     * 	Even if the gender type is set manually in the Admin console, the above value is still returned.
     * 	See the Building Block API blackboard.data.user.User.Gender
     *  SP12, SP13, SP14
     */
    @Test
    public void testCreateUser_GenderType_Negative_Test() {
        //Create a basic user with minimum user info
        BbUser bb_user = setupBasicUser();
        //add a gender type
        bb_user.setGenderType(BbUser.GENDER_TYPE_MALE);
        //Save the User to Blackboard
        String id = (String) bb_user.persist();
        assertNotNull(id);
        Logger.getLogger(this.getClass().getName()).debug("User created successfully.  Blackboard returned ID: " + id);
        //now retrieve the user from Blackboard so we can verify what was created
        bb_user = (BbUser) bb_user.retrieve();
        assertNotNull(bb_user);
        //test that the basic user info is intact
        testBasicUser(bb_user, id);
        ///////////////////////////////
        //NEGATIVE TEST FOR GENDER TYPE
        ///////////////////////////////
        assertFalse(bb_user.getGenderType().contains(BbUser.GENDER_TYPE_MALE));
        assertFalse(bb_user.getGenderType().equals(BbUser.GENDER_TYPE_MALE));
        ///////////////////////////////
        //delete the user from Blackboard
        deleteUser(bb_user);
    }
    
    /**
     * This test creates a user with the minimum required User data fields.
     * Specifically, the minimum required fields are are:
     *  - Given/First Name
     *  - Family/Last Name
     *  - Login/User Name
     *  - Password
     *  It then attempts to set the EducationLevel according to the Web Services API.  Since this
     *  operation doesn't seem to work correctly, I'm creating this negative test so that if it ever DOES
     *  start working, it will be obvious.
     *  setEducationLevel() doesn't seem to work correctly.  For example, if you set the field as "SENIOR", 
     *  the correct value is set in the GUI, but the following string is returned by Web Services:
     *		blackboard.data.user.User$EducationLevel:SENIOR
     * 	This seems to be the result of incorrectly converting the enum value from the User.EducationLevel class
     *  to a String.
     *  See the Building Block API blackboard.data.user.User.EducationLevel
     *  SP12, SP13, SP14
     */
    @Test
    public void testCreateUser_EducationLevel_Negative_Test() {
        //Create a basic user with minimum user info
        BbUser bb_user = setupBasicUser();
        //add an Education Level
        bb_user.setEducationLevel(BbUser.EDUCATION_LEVEL_SENIOR);
        //Save the User to Blackboard
        String id = (String) bb_user.persist();
        assertNotNull(id);
        Logger.getLogger(this.getClass().getName()).debug("User created successfully.  Blackboard returned ID: " + id);
        //now retrieve the user from Blackboard so we can verify what was created
        bb_user = (BbUser) bb_user.retrieve();
        assertNotNull(bb_user);
        //test that the basic user info is intact
        testBasicUser(bb_user, id);
        ///////////////////////////////////
        //NEGATIVE TEST FOR EDUCATION LEVEL
        ///////////////////////////////////
        assertTrue(bb_user.getEducationLevel().contains(BbUser.EDUCATION_LEVEL_SENIOR));
        assertFalse(bb_user.getEducationLevel().equals(BbUser.EDUCATION_LEVEL_SENIOR));
        ///////////////////////////////////
        //delete the user from Blackboard
        deleteUser(bb_user);
    }
    
    /**
     * This test creates a user with the minimum required User data fields.
     * Specifically, the minimum required fields are are:
     *  - Given/First Name
     *  - Family/Last Name
     *  - Login/User Name
     *  - Password
     *  It then attempts to set the Home Fax according to the Web Services API.  Since this
     *  operation doesn't seem to work correctly, I'm creating this negative test so that if it ever DOES
     *  start working, it will be obvious.
     *  setHomeFax() doesn't seem to work.  The is no attribute for it in the GUI, and getHomeFax returns a null. 
     *  SP12, SP13, SP14
     */
    @Test
    public void testCreateUser_HomeFax_Negative_Test() {
        //Create a basic user with minimum user info
        BbUser bb_user = setupBasicUser();
        //add an Education Level
        bb_user.setHomeFax(HOME_FAX);
        //Save the User to Blackboard
        String id = (String) bb_user.persist();
        assertNotNull(id);
        Logger.getLogger(this.getClass().getName()).debug("User created successfully.  Blackboard returned ID: " + id);
        //now retrieve the user from Blackboard so we can verify what was created
        bb_user = (BbUser) bb_user.retrieve();
        assertNotNull(bb_user);
        //test that the basic user info is intact
        testBasicUser(bb_user, id);
        ////////////////////////////
        //NEGATIVE TEST FOR HOME FAX
        ////////////////////////////
        assertNull(bb_user.getHomeFax());
        ////////////////////////////
        //delete the user from Blackboard
        deleteUser(bb_user);
    }
    
    /**
     * This test creates a user with the minimum required User data fields.
     * Specifically, the minimum required fields are are:
     *  - Given/First Name
     *  - Family/Last Name
     *  - Login/User Name
     *  - Password
     *  It then attempts to set the User's Institution Roles according to the Web Services API. 
     *  SP12, SP13, SP14
     */
    @Test
    public void testCreateUser_InstitutionRoles() {
        //Create a basic user with minimum user info
        BbUser bb_user = setupBasicUser();
        //add the Institution Roles
        bb_user.setInsRoles(INSTITUTION_ROLES);
        //Save the User to Blackboard
        String id = (String) bb_user.persist();
        assertNotNull(id);
        Logger.getLogger(this.getClass().getName()).debug("User created successfully.  Blackboard returned ID: " + id);
        //now retrieve the user from Blackboard so we can verify what was created
        bb_user = (BbUser) bb_user.retrieve();
        assertNotNull(bb_user);
        //test that the basic user info is intact
        testBasicUser(bb_user, id);
        //ensure that all of the roles were persisted.
        List<String> roles = Arrays.asList(bb_user.getInsRoles());
        assertEquals(3, roles.size());
        assertTrue(roles.contains(BbPortalRole.INSTITUTION_ROLE_FACULTY));
        assertTrue(roles.contains(BbPortalRole.INSTITUTION_ROLE_STAFF));
        assertTrue(roles.contains(BbPortalRole.INSTITUTION_ROLE_STUDENT));
        //delete the user from Blackboard
        deleteUser(bb_user);
    }
    
    /**
     * Please see TestBbUser_SystemRoles_Integration.java.
     * 
     * Although System Role functionality is contained by BbUser, there are so
     * many System Role variations, that it made sense to provide a separate 
     * test class in order to perform integration tests against all known 
     * System Roles. 
     */
    @Test
    public void testCreateUser_SystemRoles() {
    	
    }
    
    /**
     * This test creates a User making use of all the User data fields except for
     * the Institution roles, System roles, gender type, education level, and home fax.
     */
    @Test
    public void testCreateUser_full() {
    	BbUser bb_user = setupBasicUser();
        /////////////////////////
        bb_user.setAvailable(AVAILABLE);
        bb_user.setBatchUid(BATCH_UID);
        Date birthdate = null;
		try {
			birthdate = (Date) formatDate.parse(BIRTH_DATE);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
        bb_user.setBirthDate(birthdate.getTime());
        bb_user.setBusinessFax(BUSINESS_FAX);
        bb_user.setBusinessPhone1(BUSINESS_PH1);
        bb_user.setBusinessPhone2(BUSINESS_PH2);
        bb_user.setCity(CITY);
        bb_user.setCompany(COMPANY);
        bb_user.setCountry(COUNTRY);
        bb_user.setDepartment(DEPARTMENT);
        bb_user.setEmailAddress(EMAIL);
        bb_user.setHomePhone1(HOME_PH1);
        bb_user.setHomePhone2(HOME_PH2);
        bb_user.setJobTitle(JOB_TITLE);
        bb_user.setMiddleName(MIDDLE_NAME);
        bb_user.setMobilePhone(MOBILE_PHONE);
        bb_user.setOtherName(NICKNAME);
        bb_user.setStudentId(SIS_ID);
        bb_user.setState(STATE);
        bb_user.setStreet1(STREET1);
        bb_user.setStreet2(STREET2);
        bb_user.setSuffix(SUFFIX);
        bb_user.setTitle(TITLE);
        bb_user.setWebPage(WEB_PAGE);
        bb_user.setZipCode(ZIPCODE);        
        //Save the User to Blackboard
        String id = (String) bb_user.persist();
        assertNotNull(id);
        Logger.getLogger(this.getClass().getName()).debug("User created successfully.  Blackboard returned ID: " + id);
        //now retrieve the user from Blackboard so we can verify what was created
        bb_user = (BbUser) bb_user.retrieve();
        assertNotNull(bb_user);
        //test that the basic user info is intact
        testBasicUser(bb_user, id);
        //test the remaining user info that was set
        assertEquals(true, bb_user.isAvailable());
        assertEquals(BATCH_UID, bb_user.getBatchUid());
        birthdate = new Date(bb_user.getBirthDate());
        assertEquals(BIRTH_DATE, formatDate.format(birthdate));
        assertEquals(BUSINESS_FAX, bb_user.getBusinessFax());
        assertEquals(BUSINESS_PH1, bb_user.getBusinessPhone1());
        assertEquals(BUSINESS_PH2, bb_user.getBusinessPhone2());
        assertEquals(CITY, bb_user.getCity());
        assertEquals(COUNTRY, bb_user.getCountry());
        assertEquals(COMPANY, bb_user.getCompany());
        assertEquals(DEPARTMENT, bb_user.getDepartment());
        assertEquals(EMAIL, bb_user.getEmailAddress());
        assertEquals(HOME_PH1, bb_user.getHomePhone1());
        assertEquals(HOME_PH2, bb_user.getHomePhone2());
        assertEquals(JOB_TITLE, bb_user.getJobTitle());
        assertEquals(MIDDLE_NAME, bb_user.getMiddleName());
        assertEquals(MOBILE_PHONE, bb_user.getMobilePhone());
        assertEquals(NICKNAME, bb_user.getOtherName());
        assertEquals(SIS_ID, bb_user.getStudentId());
        assertEquals(STATE, bb_user.getState());
        assertEquals(STREET1, bb_user.getStreet1());
        assertEquals(STREET2, bb_user.getStreet2());
        assertEquals(SUFFIX, bb_user.getSuffix());
        assertEquals(TITLE, bb_user.getTitle());
        assertEquals(WEB_PAGE, bb_user.getWebPage());
        assertEquals(ZIPCODE, bb_user.getZipCode());
        deleteUser(bb_user);
    }
    
    
    
    /**
     * This test creates a User making use of all the User data fields except for
     * the Institution and System roles.
     */
    public void testCreateUser_roles() {
        BbUser bb_user = new BbUser();
        //minimum required fields
        bb_user.setGivenName(FIRST_NAME);
        bb_user.setFamilyName(LAST_NAME);
        bb_user.setName(USER_NAME);
        bb_user.setPassword(PASSWORD);
        /////////////////////////
        bb_user.setAvailable(AVAILABLE);
        bb_user.setBatchUid(BATCH_UID);
        Date birthdate = null;
		try {
			birthdate = (Date) formatDate.parse(BIRTH_DATE);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
        bb_user.setBirthDate(birthdate.getTime());
        bb_user.setBusinessFax(BUSINESS_FAX);
        bb_user.setBusinessPhone1(BUSINESS_PH1);
        bb_user.setBusinessPhone2(BUSINESS_PH2);
        bb_user.setCity(CITY);
        bb_user.setCompany(COMPANY);
        bb_user.setCountry(COUNTRY);
        //bb_user.setDataSourceId(dataSourceId);
        bb_user.setDepartment(DEPARTMENT);
        /** 
         * setEducationLevel() doesn't seem to work correctly.  For example, if you set the field as "SENIOR", 
         * the correct value is set in the GUI, but the following string is returned by Web Services:
        /*		blackboard.data.user.User$EducationLevel:SENIOR
        /* This seems to be the result of incorrectly converting the enum value from the User.EducationLevel class
         * top a String
        /* See the Building Block API blackboard.data.user.User.EducationLevel
         */
        bb_user.setEducationLevel(BbUser.EDUCATION_LEVEL_POST_GRADUATE_SCHOOL);
        bb_user.setEmailAddress(EMAIL);
        /** setGenderType() doesn't seem to work.  Any value entered here results in the following return string:
        /*		blackboard.data.user.User$Gender:UNKNOWN
        /* Even if the gender type is set manually in the Admin console, the above value is still returned.
        /* See the Building Block API blackboard.data.user.User.Gender
         */
        bb_user.setGenderType(BbUser.GENDER_TYPE_MALE);
        bb_user.setHomeFax(HOME_FAX);
        bb_user.setHomePhone1(HOME_PH1);
        bb_user.setHomePhone2(HOME_PH2);
        bb_user.setJobTitle(JOB_TITLE);
        bb_user.setMiddleName(MIDDLE_NAME);
        bb_user.setMobilePhone(MOBILE_PHONE);
        bb_user.setState(STATE);
        bb_user.setStreet1(STREET1);
        bb_user.setStreet2(STREET2);
        bb_user.setSuffix(SUFFIX);
        bb_user.setTitle(TITLE);
        bb_user.setWebPage(WEB_PAGE);
        bb_user.setZipCode(ZIPCODE);        
        String id = (String) bb_user.persist();
        assertNotNull(id);
        Logger.getLogger(this.getClass().getName()).debug("User created successfully.  Blackboard returned ID: " + id);
        bb_user = (BbUser) bb_user.retrieve();
        assertNotNull(bb_user);
        //make sure the new lms id matches
        assertTrue(bb_user.getId().equals(id));
        //test that the values we set are intact
        assertEquals(FIRST_NAME, bb_user.getGivenName());
        assertEquals(LAST_NAME, bb_user.getFamilyName());
        assertEquals(USER_NAME, bb_user.getName());
        //the password should be encrypted now
        assertNotNull(bb_user.getPassword());
        assertTrue(bb_user.getPassword().length()>0);
        assertFalse(bb_user.getPassword().equals(PASSWORD));
        assertEquals(true, bb_user.isAvailable());
        assertEquals(BATCH_UID, bb_user.getBatchUid());
        birthdate = new Date(bb_user.getBirthDate());
        assertEquals(BIRTH_DATE, formatDate.format(birthdate));
        assertEquals(BUSINESS_FAX, bb_user.getBusinessFax());
        assertEquals(BUSINESS_PH1, bb_user.getBusinessPhone1());
        assertEquals(BUSINESS_PH2, bb_user.getBusinessPhone2());
        assertEquals(CITY, bb_user.getCity());
        assertEquals(COUNTRY, bb_user.getCountry());
        assertEquals(COMPANY, bb_user.getCompany());
        assertEquals(DEPARTMENT, bb_user.getDepartment());
        //doesn't work correctly - see comments above setEducationLevel()
        assertTrue(bb_user.getEducationLevel().contains(BbUser.EDUCATION_LEVEL_POST_GRADUATE_SCHOOL));
        assertEquals(EMAIL, bb_user.getEmailAddress());
        ///////////////////////////////////////////////////
        //doesn't work - see comments above setGenderType()
        //assertEquals(BbUser.GENDER_TYPE_MALE, bb_user.getGenderType());
        ///////////////////////////////////////////////////
        //Doesn't work... Setting this field results in a null - this field is not included in the admin interface
        //assertEquals(HOME_FAX, bb_user.getHomeFax());
        ///////////////////////////////////////////////////
        assertEquals(HOME_PH1, bb_user.getHomePhone1());
        assertEquals(HOME_PH2, bb_user.getHomePhone2());
        //make sure we still have three institution roles		
        //assertEquals(3, bb_user.getInsRoles().length);
		//////////////////////////////////////////////////////////////////////////////////////
        //NOT WORKING - Primary role is set correctly in Bb, but is returned in the last 
        //array position, not in the first array position...
        //make sure that BbUser.INSTITUTION_ROLE_FACULTY is still in the primary role position
		//assertEquals(BbUser.INSTITUTION_ROLE_FACULTY, bb_user.getInsRoles()[0]);		
        //////////////////////////////////////////////////////////////////////////////////////
        assertEquals(JOB_TITLE, bb_user.getJobTitle());
        //bb_user.getLmsId(lms_id);
        assertEquals(MIDDLE_NAME, bb_user.getMiddleName());
        assertEquals(MOBILE_PHONE, bb_user.getMobilePhone());
        assertEquals(NICKNAME, bb_user.getOtherName());
        //assertEquals(PRIMARY_INS_ROLE, bb_user.getPrimaryInsRole());
        //assertEquals(2, bb_user.getSecondaryInsRoles().length);
        assertEquals(SIS_ID, bb_user.getStudentId());
        assertEquals(STATE, bb_user.getState());
        assertEquals(STREET1, bb_user.getStreet1());
        assertEquals(STREET2, bb_user.getStreet2());
        assertEquals(SUFFIX, bb_user.getSuffix());
        //assertEquals(3, bb_user.getSystemRoles().length);
        assertEquals(TITLE, bb_user.getTitle());
        assertEquals(WEB_PAGE, bb_user.getWebPage());
        assertEquals(ZIPCODE, bb_user.getZipCode());
        /////////////////
        deleteUser(bb_user);
    }
    
    private BbUser setupBasicUser(){
    	BbUser bb_user = new BbUser();
        bb_user.setGivenName(FIRST_NAME);
        bb_user.setFamilyName(LAST_NAME);
        bb_user.setName(USER_NAME);
        bb_user.setPassword(PASSWORD);
        return bb_user;
    }
    
    private void testBasicUser(BbUser bb_user, String lms_id){
        assertNotNull(bb_user);
        //test the basic values
        assertEquals(FIRST_NAME, bb_user.getGivenName());
        assertEquals(LAST_NAME, bb_user.getFamilyName());
        assertEquals(USER_NAME, bb_user.getName());
        //the password should be encrypted now, so it won't match the original plain text
        assertNotNull(bb_user.getPassword());
        assertTrue(bb_user.getPassword().length()>0);
        assertFalse(bb_user.getPassword().equals(PASSWORD));
        //make sure the new lms id matches what was returned when the user was created
        assertTrue(bb_user.getId().equals(lms_id));
    }
    
    private void deleteUser(BbUser bb_user){
    	//preserve the user/login name
    	String username = bb_user.getName();
    	//preserve the lms_id
    	String lms_id = bb_user.getId();
    	Logger.getLogger(this.getClass().getName()).debug("Deleting user " + lms_id);
        assertTrue(bb_user.delete());
        Logger.getLogger(this.getClass().getName()).debug("Verifying that user " + lms_id + " was deleted.");
        bb_user = new BbUser();
        bb_user.setName(username);
        bb_user = (BbUser) bb_user.retrieve();
        assertTrue(bb_user==null);
        Logger.getLogger(this.getClass().getName()).debug("User " + lms_id + " deleted.");
    }
    
	private String createCourseMembership_Student(String course_id, String user_id, boolean availability){
		BbCourseMembership bbcm = new BbCourseMembership();
		bbcm.setCourseId(course_id);
		bbcm.setUserId(user_id);
		bbcm.setAvailable(availability);
		bbcm.setRoleId(BbCourseMembershipRole.COURSE_ROLE_STUDENT);
		return (String)bbcm.persist();
	}
	
	private String createCourseMembership_Instructor(String course_id, String user_id, boolean availability){
		BbCourseMembership bbcm = new BbCourseMembership();
		bbcm.setCourseId(course_id);
		bbcm.setUserId(user_id);
		bbcm.setAvailable(availability);
		bbcm.setRoleId(BbCourseMembershipRole.COURSE_ROLE_STUDENT);
		return (String)bbcm.persist();
	}

	private void createBasicUser1(){
		BbUser bb_user = new BbUser();
	    bb_user.setGivenName(BASIC_GIVEN_NAME_1);
	    bb_user.setFamilyName(BASIC_FAMILY_NAME_1);
	    bb_user.setName(BASIC_USER_NAME_1);
	    bb_user.setPassword(BASIC_USER_PW_1);
	    bb_user.setAvailable(Boolean.TRUE);
	    basic_user_id_1 = (String) bb_user.persist();
	    assertNotNull(basic_user_id_1);
	}
	
	private void createBasicUser2(){
		BbUser bb_user = new BbUser();
	    bb_user.setGivenName(BASIC_GIVEN_NAME_2);
	    bb_user.setFamilyName(BASIC_FAMILY_NAME_2);
	    bb_user.setName(BASIC_USER_NAME_2);
	    bb_user.setPassword(BASIC_USER_PW_2);
	    bb_user.setAvailable(Boolean.TRUE);
	    basic_user_id_2 = (String) bb_user.persist();
	    assertNotNull(basic_user_id_2);
	}
	
	private void createBasicCourse1(){
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
	    bb_course.setCourseId(COURSE_ID_1);
	    bb_course.setName(COURSE_NAME_1);
	    bb_course.setDescription(DESCRIPTION_1);
	    bb_course.setBatchUid(COURSE_BATCH_UID_1);
	    bb_course.setAvailable(Boolean.TRUE);
	    basic_course_id_1 = (String) bb_course.persist();
	    assertNotNull(basic_course_id_1);
	}

	private void createBasicCourse2(){
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
	    bb_course.setCourseId(COURSE_ID_2);
	    bb_course.setName(COURSE_NAME_2);
	    bb_course.setDescription(DESCRIPTION_2);
	    bb_course.setBatchUid(COURSE_BATCH_UID_2);
	    bb_course.setAvailable(Boolean.TRUE);
	    basic_course_id_2 = (String) bb_course.persist();
	    assertNotNull(basic_course_id_2);
	}
	
	private void deleteCourse(String basic_course_id){
		BbCourse bb_course = new BbCourse();
		bb_course.setId(basic_course_id);
		assertTrue(bb_course.delete());
    }
	
	private void createBasicGroup1(String course_id){
		BbGroup bb_group = new BbGroup();
		bb_group.setCourseId(course_id);
		bb_group.setName(GROUP_NAME_1);
		bb_group.setDescription(GROUP_DESCRPTION_1);
		String id = (String) bb_group.persist();
		assertNotNull(id);
		basic_group_id_1 = id;
	}
	
	private void createBasicGroup2(String course_id){
		BbGroup bb_group = new BbGroup();
		bb_group.setCourseId(course_id);
		bb_group.setName(GROUP_NAME_2);
		bb_group.setDescription(GROUP_DESCRPTION_2);
		String id = (String) bb_group.persist();
		assertNotNull(id);
		basic_group_id_2 = id;
	}

	private void createBasicGroup3(String course_id){
		BbGroup bb_group = new BbGroup();
		bb_group.setCourseId(course_id);
		bb_group.setName(GROUP_NAME_3);
		bb_group.setDescription(GROUP_DESCRPTION_3);
		String id = (String) bb_group.persist();
		assertNotNull(id);
		basic_group_id_3 = id;
	}
	
    private void deleteGroup(String group_id) {
    	assertNotNull(group_id);
        BbGroup bb_group = new BbGroup();
        bb_group.setId(group_id);
        assertTrue(bb_group.delete());
    }
    
	private String createGroupMembership(String course_id, String group_id, String course_membership_id) {
		BbGroupMembership bbgm = new BbGroupMembership();
		bbgm.setCourseId(course_id);
		bbgm.setCourseMembershipId(course_membership_id);
		bbgm.setGroupId(group_id);
		String result = (String)bbgm.persist();
		assertNotNull(result);
		return result;
	}
}
