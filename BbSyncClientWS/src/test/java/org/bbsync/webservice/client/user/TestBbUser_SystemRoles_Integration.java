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

package org.bbsync.webservice.client.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This class performs integration tests against all known System Roles.  
 * Although System Role functionality is contained by BbUser, there are so
 * many System Role variations, that it made sense to provide a separate test 
 * class.  
 * 
 * System Roles control the administrative privileges assigned to a user. This
 * enables Administrators to share administrative privileges and functions with
 * other users in Blackboard Learn.
 * 
 * A set of System Roles are available for all licenses of Blackboard Learn, 
 * but some System Roles are provided only with specific licenses of Blackboard
 * Learn. Each user can be assigned one System Role, but most users will have a
 * System Role of "None", which indicates that the user has no access to 
 * Administrator Panel.
 * 
 * If your school licenses community engagement, multiple Secondary System 
 * Roles can be assigned to a user. Multiple System Roles grant the user the 
 * sum of their privileges. This makes it possible to create System Roles 
 * based on tasks and layer privileges instead of creating a System Role for 
 * every possible set of privileges.
 * 
 * Custom System Roles cannot be created in Blackboard Learn - Basic Edition.
 * 
 * A few things worth pointing our about System Roles:
 * 1) Like portal/institution roles, system roles can only be applied & removed
 *    using a BbUser object.  Certain portal/institution roles work with 
 *    certain system roles e.g. Observer.
 * 2) Most System roles can be identified three different ways:
 *     a. Role Name - This is the human-readable label provided in the Admin
 *                    GUI to reference the system role.  A role name is 
 *                    required when creating custom system roles. 
 *     b. Role ID - A role ID is required when creating a custom system role.
 *                  When created in the Admin GUI, the role ID will be the same 
 *                  as the Role String.  A role ID can usually be used with web
 *                  services to set a system role.
 *     c. Role String - Role Strings are returned in String Arrays as the 
 *                      result of making API calls.  With web services, role 
 *                      Strings are the best way to work with system roles.   
 * 3) The default System Role is named "None" in the Admin GUI, but is only 
 *    referenced in web services by the role string "USER".  The Role ID "N"
 *    does not work.
 * 4) Some system roles are "private" - meaning that they will not appear in
 *    the Admin GUI unless they have already been assigned to a user.
 * 5) Some System Roles will appear twice in a system roles String array when
 *    you perform a BbUser.getSystemRoles() lookup.  That is, the system role
 *    name String will be in the primary and secondary role positions of the 
 *    array.  Be sure to remove any duplicates before attempting to "set" new
 *    roles, or save changes to a BbUser after a lookup.  
 */
public class TestBbUser_SystemRoles_Integration {
			
	private BbUser bb_user = null;

	@Before
	public void setUp() throws Exception {
		createBasicUser();
	}

	@After
	public void tearDown() throws Exception {
		deleteUser(bb_user.getId());
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	//System Roles that can ONLY be set using a Role String. 
	//Role Strings are provided in the String array that results after calling
	//BbUser.getAllSystemRoles():String[] 
	//OR
	//BbUser.getSystemRoles().
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Role Name:  None
	 * Role ID (Admin GUI):  N
	 * Role String:  USER
	 * 
	 * Note:  ONLY the Role String "USER" can be used to set this this System 
	 * Role.  The Role ID "N" DOES NOT WORK! Only one role is returned in the 
	 * system roles array when this role is set.
	 */
	@Test
	public void testSystemRole_USER() {
	    bb_user.setSystemRoles(new String[]{"USER"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(1, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_NONE, system_roles[0]);
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	//System Roles that have a Role Name and a Role ID in the Admin GUI, but do 
	//not appear in the Role String array when 
	//BbUser.getAllSystemRoles():String[] is invoked.
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Role Name:  Goals Manager
	 * Role ID (Admin GUI):  BB_GOALS_ADMIN
	 * Role String:  BB_GOALS_ADMIN
	 * 
	 * Note:  This role shows up in the Admin GUI as Role Name "Goals Manager"
	 * and Role ID "BB_GOALS_ADMIN".  The Role String for this System Role is
	 * also "BB_GOALS_ADMIN".  However, it does not appear in the role String
	 * array when BbUser.getAllSystemRoles() is invoked.  It
	 * does appear (twice) in the role String array that results when 
	 * BbUser.getSystemRoles() is invoked.
	 */
	@Test
	public void testSystemRole_BB_GOALS_ADMIN() {
	    bb_user.setSystemRoles(new String[]{"BB_GOALS_ADMIN"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_GOALS_MANAGER, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_GOALS_MANAGER, system_roles[1]);
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	//System Roles that have the same Role ID (Admin GUI) and  Role String.
	//Role IDs are viewable in the Admin GUI.
	//Role Strings are provided in the String array that results after calling
	//BbUser.getAllSystemRoles():String[] 
	//OR
	//BbUser.getSystemRoles().
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Role Name:  Survey Author
	 * Role ID (Admin GUI):  BB_SURVEY_MANAGER
	 * Role String:  BB_SURVEY_MANAGER
	 * 
	 * Note:  ONLY the Role String "USER" can be used to set this this System 
	 * Role.  The Role ID "N" DOES NOT WORK! Only one role is returned in the 
	 * system roles array when this role is set.
	 */
	@Test
	public void testSystemRole_BB_SURVEY_AUTHOR() {
	    bb_user.setSystemRoles(new String[]{"BB_SURVEY_MANAGER"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_SURVEY_AUTHOR, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_SURVEY_AUTHOR, system_roles[1]);
	}

	/**
	 * Role Name:  Rubric Manager
	 * Role ID (Admin GUI):  BB_SYSTEM_RUBRIC_MANAGER
	 * Role String:  BB_SYSTEM_RUBRIC_MANAGER
	 * 
	 * Note:  ONLY the Role String "USER" can be used to set this this System 
	 * Role.  The Role ID "N" DOES NOT WORK! Only one role is returned in the 
	 * system roles array when this role is set.
	 */
	@Test
	public void testSystemRole_BB_SYSTEM_RUBRIC_MANAGER() {
	    bb_user.setSystemRoles(new String[]{"BB_SYSTEM_RUBRIC_MANAGER"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_RUBRIC_MANAGER, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_RUBRIC_MANAGER, system_roles[1]);
	}
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	//"PRIVATE" System Roles.  These are roles that appear in the System Roles
	//Array that results after calling BbUser.getAllSystemRoles():String[] OR
	//BbUser.getSystemRoles().  These roles are not available in the Admin GUI
	//unless they have been set though another process (like web services).
	//
	//As the API documentation states for the Integration System Role, these 
	//roles are used for special processes and probably should not be assigned
	//to a "normal" user. 
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Role Name:  [Integration]
	 * Role ID (Admin GUI):  N/A
	 * Role String:  INTEGRATION
	 * 
	 * Note:  From the Building Block API (blackboard.ws.user.UserType), This 
	 * role is private, used only for special processes that interact for data 
	 * integration authentication.  Only one role is returned in the system 
	 * roles array when this role is set.  The Role Name "Integration" will
	 * only be visible in the Admin GUI if this System Role has already been 
	 * set. 
	 */
	@Test
	public void testSystemRole_INTEGRATION() {
	    bb_user.setSystemRoles(new String[]{"INTEGRATION"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(1, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_INTEGRATION, system_roles[0]);
	}
	
	/**
	 * Role Name:  [Outcomes Administrator]
	 * Role ID (Admin GUI):  N/A
	 * Role String:  BB_OUTCOMES_ADMIN
	 * 
	 * Note: This role is private.  The role String "BB_OUTCOMES_ADMIN" is 
	 * returned twice in the system roles array when this role is set.  The 
	 * Role Name "Outcomes Administrator" will only be visible in the Admin GUI
	 * if this System Role has already been set. 
	 */
	@Test
	public void testSystemRole_BB_OUTCOMES_ADMIN() {
	    bb_user.setSystemRoles(new String[]{"BB_OUTCOMES_ADMIN"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_BB_OUTCOMES_ADMIN, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_BB_OUTCOMES_ADMIN, system_roles[1]);
	}
	
	/**
	 * Role Name:  [Template Administrator]
	 * Role ID (Admin GUI):  N/A
	 * Role String:  BB_TEMPLATES_ADMIN
	 * 
	 * Note: This role is private.  The role String "BB_TEMPLATES_ADMIN" is 
	 * returned twice in the system roles array when this role is set. The Role
	 * Name "Template Administrator" will only be visible in the Admin GUI if
	 * this System Role has already been set.
	 */
	@Test
	public void testSystemRole_BB_TEMPLATES_ADMIN() {
	    bb_user.setSystemRoles(new String[]{"BB_TEMPLATES_ADMIN"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_BB_TEMPLATES_ADMIN, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_BB_TEMPLATES_ADMIN, system_roles[1]);
	}

	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	//System Roles that can be set using either a Role ID (Admin GUI) or the
	//Role String.
	//Role IDs are viewable in the Admin GUI.
	//Role Strings are provided in the String array that results after calling
	//BbUser.getAllSystemRoles():String[] 
	//OR
	//BbUser.getSystemRoles().
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Role Name:  User Administrator
	 * Role ID (Admin GUI):  A
	 * 
	 * Note:  Role ID can be used to set this this System Role.  However, only 
	 * the Role String ("ACCOUNT_ADMIN") will be returned.  "ACCOUNT_ADMIN" 
	 * is returned twice in the system roles array when this role is set. 
	 */
	@Test
	public void testSystemRole_A() {
	    bb_user.setSystemRoles(new String[]{"A"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_ACCOUNT_ADMIN, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_ACCOUNT_ADMIN, system_roles[1]);
	}
	
	/**
	 * Role Name:  User Administrator
	 * Role String (from System Roles array):  ACCOUNT_ADMIN
	 * 
	 * Note:  "SYSTEM_SUPPORT" is returned twice in the system roles array when this 
	 * role is set.  
	 */	
	@Test
	public void testSystemRole_ACCOUNT_ADMIN() {
	    bb_user.setSystemRoles(new String[]{"ACCOUNT_ADMIN"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_ACCOUNT_ADMIN, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_ACCOUNT_ADMIN, system_roles[1]);
	}
	
	/**
	 * Role Name:  Course Administrator
	 * Role ID (Admin GUI):  C
	 * 
	 * Note:  Role ID can be used to set this this System Role.  However, only 
	 * the Role String ("COURSE_CREATOR") will be returned.  "COURSE_CREATOR" 
	 * is returned twice in the system roles array when this role is set. 
	 */
	@Test
	public void testSystemRole_C() {
	    bb_user.setSystemRoles(new String[]{"C"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_COURSE_CREATOR, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_COURSE_CREATOR, system_roles[1]);
	}
	
	/**
	 * Role Name:  Course Administrator
	 * Role String (from System Roles array):  COURSE_CREATOR
	 * 
	 * Note:  "COURSE_CREATOR" is returned twice in the system roles array when this 
	 * role is set.  
	 */	
	@Test
	public void testSystemRole_COURSE_CREATOR() {
	    bb_user.setSystemRoles(new String[]{"COURSE_CREATOR"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_COURSE_CREATOR, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_COURSE_CREATOR, system_roles[1]);
	}
	
	/**
	 * Role Name:  System Support
	 * Role ID (Admin GUI):  H
	 * 
	 * Note:  Role ID can be used to set this this System Role.  However, only 
	 * the Role String ("SYSTEM_SUPPORT") will be returned.  "SYSTEM_SUPPORT" 
	 * is returned twice in the system roles array when this role is set. 
	 */
	@Test
	public void testSystemRole_H() {
	    bb_user.setSystemRoles(new String[]{"H"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_SYSTEM_SUPPORT, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_SYSTEM_SUPPORT, system_roles[1]);
	}
	
	/**
	 * Role Name:  System Support
	 * Role String (from System Roles array):  SYSTEM_SUPPORT
	 * 
	 * Note:  "SYSTEM_SUPPORT" is returned twice in the system roles array when this 
	 * role is set.  
	 */	
	@Test
	public void testSystemRole_SYSTEM_SUPPORT() {
	    bb_user.setSystemRoles(new String[]{"SYSTEM_SUPPORT"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_SYSTEM_SUPPORT, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_SYSTEM_SUPPORT, system_roles[1]);
	}
	
	/**
	 * Role Name:  Observer
	 * Role ID (Admin GUI):  O
	 * 
	 * Note:  Role ID can be used to set this this System Role.  However, only 
	 * the Role String ("OBSERVER") will be returned.  Only one role is 
	 * returned in the system roles array when this role is set.  
	 */
	@Test
	public void testSystemRole_O() {
	    bb_user.setSystemRoles(new String[]{"O"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(1, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_OBSERVER, system_roles[0]);
	}
	
	/**
	 * Role Name:  Observer
	 * Role String (from System Roles array):  OBSERVER
	 * 
	 * Note:  Only one role is returned in the system roles array when this 
	 * role is set.  
	 */	
	@Test
	public void testSystemRole_OBSERVER() {
	    bb_user.setSystemRoles(new String[]{"OBSERVER"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(1, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_OBSERVER, system_roles[0]);
	}
		
	/**
	 * Role Name:  Support
	 * Role ID (Admin GUI):  R
	 * 
	 * Note:  Role ID can be used to set this this System Role.  However, only 
	 * the Role String ("COURSE_SUPPORT") will be returned.  "COURSE_SUPPORT" 
	 * is returned twice in the system roles array when this role is set. 
	 */
	@Test
	public void testSystemRole_R() {
	    bb_user.setSystemRoles(new String[]{"R"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_COURSE_SUPPORT, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_COURSE_SUPPORT, system_roles[1]);
	}
	
	/**
	 * Role Name:  Support
	 * Role String (from System Roles array):  COURSE_SUPPORT
	 * 
	 * Note:  "COURSE_SUPPORT" is returned twice in the system roles array when this 
	 * role is set.  
	 */	
	@Test
	public void testSystemRole_COURSE_SUPPORT() {
	    bb_user.setSystemRoles(new String[]{"COURSE_SUPPORT"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_COURSE_SUPPORT, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_COURSE_SUPPORT, system_roles[1]);
	}
	
	/**
	 * Role Name:  Guest
	 * Role ID (Admin GUI):  U
	 * 
	 * Note:  Role ID can be used to set this this System Role.  However, only 
	 * the Role String ("GUEST") will be returned.  Only one role is returned 
	 * in the system roles array when this role is set.  
	 */
	@Test
	public void testSystemRole_U() {
	    bb_user.setSystemRoles(new String[]{"U"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(1, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_GUEST, system_roles[0]);
	}
	
	/**
	 * Role Name:  Guest
	 * Role String (from System Roles array):  GUEST
	 * 
	 * Note:  Only one role is returned in the system roles array when this 
	 * role is set.  
	 */	
	@Test
	public void testSystemRole_GUEST() {
	    bb_user.setSystemRoles(new String[]{"GUEST"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(1, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_GUEST, system_roles[0]);
	}
	
	/**
	 * Role Name:  Community Administrator
	 * Role ID (Admin GUI):  Y
	 * 
	 * Note:  Role ID can be used to set this this System Role.  However, only 
	 * the Role String ("PORTAL") will be returned.  "PORTAL" is returned twice 
	 * in the system roles array when this role is set.  
	 */
	@Test
	public void testSystemRole_Y() {
	    bb_user.setSystemRoles(new String[]{"Y"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_PORTAL, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_PORTAL, system_roles[1]);
	}
	
	/**
	 * Role Name:  Community Administrator
	 * Role String (from System Roles array):  PORTAL
	 * 
	 * Note:  "PORTAL" is returned twice in the system roles array when this 
	 * role is set.  
	 */	
	@Test
	public void testSystemRole_PORTAL() {
	    bb_user.setSystemRoles(new String[]{"PORTAL"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_PORTAL, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_PORTAL, system_roles[1]);
	}

	/**
	 * Role Name:  System Administrator
	 * Role ID (Admin GUI):  Z
	 * 
	 * Note:  Role ID can be used to set this this System Role.  However, only 
	 * the Role String ("SYSTEM_ADMIN") will be returned.  "SYSTEM_ADMIN" 
	 * is returned twice in the system roles array when this role is set. 
	 */
	@Test
	public void testSystemRole_Z() {
	    bb_user.setSystemRoles(new String[]{"Z"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_SYSTEM_ADMIN, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_SYSTEM_ADMIN, system_roles[1]);
	}
	
	/**
	 * Role Name:  System Administrator
	 * Role String (from System Roles array):  SYSTEM_ADMIN
	 * 
	 * Note:  "SYSTEM_ADMIN" is returned twice in the system roles array when this 
	 * role is set.  
	 */	
	@Test
	public void testSystemRole_SYSTEM_ADMIN() {
	    bb_user.setSystemRoles(new String[]{"SYSTEM_ADMIN"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_SYSTEM_ADMIN, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_SYSTEM_ADMIN, system_roles[1]);
	}
	
	/**
	 * Role Name:  Learning Environment Administrator
	 * Role ID (Admin GUI):  BB_LE_ADMIN
	 * 
	 * Note:  Role ID can be used to set this this System Role.  However, only 
	 * the Role String ("LMS_INTEGRATION_ADMIN") will be returned.  
	 * "LMS_INTEGRATION_ADMIN" is returned twice in the system roles array when
	 * this role is set. 
	 */
	@Test
	public void testSystemRole_BB_LE_ADMIN() {
	    bb_user.setSystemRoles(new String[]{"BB_LE_ADMIN"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_LMS_INTEGRATION_ADMIN, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_LMS_INTEGRATION_ADMIN, system_roles[1]);
	}
	
	/**
	 * Role Name:  Learning Environment Administrator
	 * Role String (from System Roles array):  LMS_INTEGRATION_ADMIN
	 * 
	 * Note:  "LMS_INTEGRATION_ADMIN" is returned twice in the system roles array when this 
	 * role is set.  
	 */	
	@Test
	public void testSystemRole_LMS_INTEGRATION_ADMIN() {
	    bb_user.setSystemRoles(new String[]{"LMS_INTEGRATION_ADMIN"});
	    String bb_user_id = (String) bb_user.persist();
	    assertNotNull(bb_user_id);
		bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		bb_user = (BbUser)bb_user.retrieve();
		assertNotNull(bb_user);
		String[] system_roles = bb_user.getSystemRoles();
		assertNotNull(system_roles);
		assertEquals(2, system_roles.length);
		assertEquals(BbUser.SYSTEM_ROLE_LMS_INTEGRATION_ADMIN, system_roles[0]);
		assertEquals(BbUser.SYSTEM_ROLE_LMS_INTEGRATION_ADMIN, system_roles[1]);
	}		
		
	private void createBasicUser(){
		bb_user = new BbUser();
	    bb_user.setGivenName("Johnnie");
	    bb_user.setFamilyName("Walker");
	    bb_user.setName("jw1111111");
	    bb_user.setPassword("password");
	    bb_user.setAvailable(Boolean.TRUE);
	}
	
	private void deleteUser(String bb_user_id){
		BbUser bb_user = new BbUser();
		bb_user.setId(bb_user_id);
		assertTrue(bb_user.delete());
    }
}
