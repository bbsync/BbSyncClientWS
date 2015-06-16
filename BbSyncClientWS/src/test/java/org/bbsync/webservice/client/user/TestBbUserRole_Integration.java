package org.bbsync.webservice.client.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class TestBbUserRole_Integration {
	private static final Logger logger = Logger.getLogger(TestBbUserRole_Integration.class.getName());
	private static final String FIRST_NAME = "Henry";
	private static final String LAST_NAME  = "Jones";
	private static final String USER_NAME  = "hj1234567";
	private static final String PASSWORD   = "ilovearcheology";
	private BbUser user = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.user = createBasicUser();
		assertNotNull(this.user);
	}

	@After
	public void tearDown() throws Exception {
		deleteUser(this.user);
		this.user = null;
	}
	
	@Ignore
    public void testGetServerVersion() {
		BbUserRole bbur = new BbUserRole();
        Long version = bbur.getServerVersion();
        assertNotNull(version);
        assertTrue(version>0L);
        logger.debug("Server Version: " + String.valueOf(version));
    }
	
	@Ignore
    public void testInitializeUserWS() {
		BbUserRole bbur = new BbUserRole();
        assertTrue(bbur.initializeUserWS());
        logger.debug("initialize UserRole web service succeded.");
    }
	
	/**
	 * After creating a default user (no secondary roles), check for secondary
	 * roles.  There shouldn't be any. 
	 */
	@Test
    public void testGetSecondaryInsRolesByUserId_negative() {
        assertNotNull(user);
        assertNotNull(user.getId());
        BbUserRole user_role = new BbUserRole();
        BbUserRole[] roles = user_role.getSecondaryInsRolesByUserId(user.getId());
        assertNull(roles);
    }

	/**
	 * After creating a default user (no secondary roles), add a secondary
	 * portal/institution role.  Test to make sure that the role was added. 
	 */
	@Test
    public void testGetSecondaryInsRolesByUserId(){
        assertNotNull(user);
        assertNotNull(user.getId());
        //first add a secondary institution role (role 11)
        user.setInsRoles(new String[]{BbPortalRole.INSTITUTION_ROLE_STUDENT, BbPortalRole.INSTITUTION_ROLE_11});
        user.persist();
        BbUserRole user_role = new BbUserRole();
        //get all of the secondary user roles for the user
        BbUserRole[] user_roles = user_role.getSecondaryInsRolesByUserId(user.getId());
        assertNotNull(user_roles);
        BbPortalRole portal_role = new BbPortalRole();
        //knowing that we added role 11 as the secondary role, we need to figure out the 
        //blackboard id for role 11
        BbPortalRole[] portal_roles = portal_role.getInstitutionRolesByRoleId(new String[]{BbPortalRole.INSTITUTION_ROLE_11});
        assertEquals(1, portal_roles.length);
        assertEquals(BbPortalRole.INSTITUTION_ROLE_11, portal_roles[0].getRoleId());
        //now we'll make sure that the secondary role returned matches the blackboard ID of role 11. 
        assertEquals(1, user_roles.length);
        assertEquals(portal_roles[0].getId(), user_roles[0].getInsRoleId());
    }
	
	/**
	 * THIS TEST IS DISABLED BY DEFAULT BECAUSE IT IS DANGEROUS!
	 * 
	 * At this time, the underlying Blackboard web services API does not 
	 * provide a way to query for users by portal/institution.  Therefore, any 
	 * attempt to use this test may result in non-test users being deleted.  
	 * Don't use this test unless you're certain that it will not delete users
	 * that may be associated with the specified portal/institution role.
	 * 
	 * This test proves that you CAN delete a user with multiple roles using
	 * an array of portal/institution roles.  NOTE: role order does not matter 
	 * when specifying roles to delete user(s).  ALSO NOTE: although the user
	 * is deleted, the resulting user ID array returns the user ID twice.  If 
	 * there were three roles, the user's ID would be returned three times, etc.
	 */
	@Ignore
	public void testDeleteUserByInstitutionRole_Multi_Multi(){
        assertNotNull(user);
        assertNotNull(user.getId());
        /////////////////////////////////////////////////////////
        //Add a secondary institution role (role 13) to the user.
        /////////////////////////////////////////////////////////
        user.setInsRoles(new String[]{BbPortalRole.INSTITUTION_ROLE_STUDENT, BbPortalRole.INSTITUTION_ROLE_13});
        user.persist();
        ///////////////////////////////////////////////////////
        //Reload the user and verify that the new role was set.
        ///////////////////////////////////////////////////////
        user = (BbUser)user.retrieve();
        String[] ins_roles = user.getInsRoles();
        assertNotNull(ins_roles);
        assertTrue(ins_roles.length==2);
        assertTrue(ins_roles[0].equals(BbPortalRole.INSTITUTION_ROLE_13)||ins_roles[1].equals(BbPortalRole.INSTITUTION_ROLE_13));
        //////////////////////////////////////////////
        //Get the internal Blackboard ID for "Student".
        //////////////////////////////////////////////
        BbPortalRole role_student = new BbPortalRole();
        role_student.setRoleId(BbPortalRole.INSTITUTION_ROLE_STUDENT);
        role_student = (BbPortalRole) role_student.retrieve();
        assertNotNull(role_student);
        /////////////////////////////////////////////
        //Get the internal Blackboard ID for Role 13.
        /////////////////////////////////////////////
        BbPortalRole role_13 = new BbPortalRole();
        role_13.setRoleId(BbPortalRole.INSTITUTION_ROLE_13);
        role_13 = (BbPortalRole) role_13.retrieve();
        assertNotNull(role_13);

        //////////////////////////////////////////////////////////////////////
        //Attempt to delete the user using the internal Blackboard ID for Role
        //13 and Student.  This does not work, returning "NULL" - which according to the
        //Blackboard web services API is a "no-operation".
        //////////////////////////////////////////////////////////////////////
        BbUserRole user_role = new BbUserRole();
        String[] deleted_users = user_role.deleteUserByInstitutionRole(new String[]{role_student.getId(), role_13.getId()});
        assertNotNull(deleted_users);
        //////////////////////////////////////////////////////////////////////
        //The user *IS* deleted, but for some reason the user's ID is returned
        //twice.  If there were three roles, the ID would be returned three
        //times, etc.
        //////////////////////////////////////////////////////////////////////
        assertTrue(deleted_users.length==2);
        assertEquals(user.getId(), deleted_users[0]);
        assertEquals(user.getId(), deleted_users[1]);
	}
	
	/**
	 * THIS TEST IS DISABLED BY DEFAULT BECAUSE IT IS DANGEROUS!
	 * 
	 * At this time, the underlying Blackboard web services API does not 
	 * provide a way to query for users by portal/institution.  Therefore, any 
	 * attempt to use this test may result in non-test users being deleted.  
	 * Don't use this test unless you're certain that it will not delete users
	 * that may be associated with the specified portal/institution role.
	 * 
	 * This test creates a user and grants it the secondary role, 
	 * BbPortalRole.INSTITUTION_ROLE_13.  It then attempts to delete the user
	 * using the secondary portal/institution role.  
	 * 
	 * This NEGATIVE test shows that attempting to delete users with a secondary
	 * portal/institution role DOES NOT WORK.
	 */
	@Ignore
	public void testDeleteUserByInstitutionRole_Multi_Secondary_Negative(){
        assertNotNull(user);
        assertNotNull(user.getId());
        /////////////////////////////////////////////////////////
        //Add a secondary institution role (role 13) to the user.
        /////////////////////////////////////////////////////////
        user.setInsRoles(new String[]{BbPortalRole.INSTITUTION_ROLE_STUDENT, BbPortalRole.INSTITUTION_ROLE_13});
        user.persist();
        ///////////////////////////////////////////////////////
        //Reload the user and verify that the new role was set.
        ///////////////////////////////////////////////////////
        user = (BbUser)user.retrieve();
        String[] ins_roles = user.getInsRoles();
        assertNotNull(ins_roles);
        assertTrue(ins_roles.length==2);
        assertTrue(ins_roles[0].equals(BbPortalRole.INSTITUTION_ROLE_13)||ins_roles[1].equals(BbPortalRole.INSTITUTION_ROLE_13));
        /////////////////////////////////////////////
        //Get the internal Blackboard ID for Role 13.
        /////////////////////////////////////////////
        BbPortalRole role_13 = new BbPortalRole();
        role_13.setRoleId(BbPortalRole.INSTITUTION_ROLE_13);
        role_13 = (BbPortalRole) role_13.retrieve();
        assertNotNull(role_13);
        //////////////////////////////////////////////////////////////////////
        //Attempt to delete the user using the internal Blackboard ID for Role
        //13.  This does not work, returning "NULL" - which according to the
        //Blackboard web services API is a "no-operation".
        //////////////////////////////////////////////////////////////////////
        BbUserRole user_role = new BbUserRole();
        String[] deleted_users = user_role.deleteUserByInstitutionRole(new String[]{role_13.getId()});
        assertNotNull(deleted_users);
        assertTrue(deleted_users.length==1);
        assertNull(deleted_users[0]); //the result is null (no-operation)
	}

	/** 
	 * THIS TEST IS DISABLED BY DEFAULT BECAUSE IT IS DANGEROUS!
	 * 
	 * At this time, the underlying Blackboard web services API does not 
	 * provide a way to query for users by portal/institution.  Therefore, any 
	 * attempt to use this test may result in non-test users being deleted.  
	 * Don't use this test unless you're certain that it will not delete users
	 * that may be associated with the specified portal/institution role.
	 * 
	 * This test proves that you CAN delete a user with multiple roles using
	 * nothing but the primary portal/institution role.  NOTE: although the user
	 * is deleted, the resulting user ID array returns the user ID twice.  If 
	 * there were three roles, the user's ID would be returned three times, etc.
	 */
	@Ignore
	public void testDeleteUserByInstitutionRole_Multi_Primary(){
        assertNotNull(user);
        assertNotNull(user.getId());
        /////////////////////////////////////////////////////////
        //Add a secondary institution role (role 13) to the user.
        /////////////////////////////////////////////////////////
        user.setInsRoles(new String[]{BbPortalRole.INSTITUTION_ROLE_STUDENT, BbPortalRole.INSTITUTION_ROLE_13});
        user.persist();
        ///////////////////////////////////////////////////////
        //Reload the user and verify that the new role was set.
        ///////////////////////////////////////////////////////
        user = (BbUser)user.retrieve();
        String[] ins_roles = user.getInsRoles();
        assertNotNull(ins_roles);
        assertTrue(ins_roles.length==2);
        assertTrue(ins_roles[0].equals(BbPortalRole.INSTITUTION_ROLE_13)||ins_roles[1].equals(BbPortalRole.INSTITUTION_ROLE_13));
        ///////////////////////////////////////////////
        //Get the internal Blackboard ID for "Student".
        ///////////////////////////////////////////////
        BbPortalRole role_student = new BbPortalRole();
        role_student.setRoleId(BbPortalRole.INSTITUTION_ROLE_STUDENT);
        role_student = (BbPortalRole) role_student.retrieve();
        assertNotNull(role_student);
        /////////////////////////////////////////////////////////////////////
        //Attempt to delete the user using the internal Blackboard ID for the
        //"Student" role.  This does not work, returning "NULL" - which 
        //according to the Blackboard web services API is a "no-operation".
        /////////////////////////////////////////////////////////////////////
        BbUserRole user_role = new BbUserRole();
        String[] deleted_users = user_role.deleteUserByInstitutionRole(new String[]{role_student.getId()});
        assertNotNull(deleted_users);
        //////////////////////////////////////////////////////////////////////
        //The user *IS* deleted, but for some reason the user's ID is returned
        //twice.  If there were three roles, the ID would be returned three
        //times, etc.
        //////////////////////////////////////////////////////////////////////
        assertTrue(deleted_users.length==2);
        assertEquals(user.getId(), deleted_users[0]);
        assertEquals(user.getId(), deleted_users[1]);
	}
	
	/** 
	 * THIS TEST IS DISABLED BY DEFAULT BECAUSE IT IS DANGEROUS!
	 * 
	 * At this time, the underlying Blackboard web services API does not 
	 * provide a way to query for users by portal/institution.  Therefore, any 
	 * attempt to use this test may result in non-test users being deleted.  
	 * Don't use this test unless you're certain that it will not delete users
	 * that may be associated with the specified portal/institution role.
	 */
	@Ignore
	public void testDeleteUserByInstitutionRole_PrimaryOnly(){
		///////////////////////////////////////////////////////////////
        //Verify that the default portal/institution role is "Student".
		///////////////////////////////////////////////////////////////
		assertNotNull(user);
        assertNotNull(user.getId());
        String[] ins_roles = user.getInsRoles();
        assertNotNull(ins_roles);
        assertTrue(ins_roles.length==1);
        assertTrue(ins_roles[0].equals(BbPortalRole.INSTITUTION_ROLE_STUDENT));
        ///////////////////////////////////////////////////
        //Get the internal Blackboard ID for Role "Student"
        ///////////////////////////////////////////////////
        BbPortalRole role_student = new BbPortalRole();
        role_student.setRoleId(BbPortalRole.INSTITUTION_ROLE_STUDENT);
        role_student = (BbPortalRole) role_student.retrieve();
        assertNotNull(role_student);
        ////////////////////////////////////////////////////////////////
        //Delete the user using the internal Blackboard ID for "Student"
        ////////////////////////////////////////////////////////////////
        BbUserRole user_role = new BbUserRole();
        String[] deleted_users = user_role.deleteUserByInstitutionRole(new String[]{role_student.getId()});
        assertNotNull(deleted_users);
        assertTrue(deleted_users.length==1);
        assertEquals(user.getId(), deleted_users[0]);
	}
	
	private BbUser createBasicUser(){
    	BbUser bb_user = new BbUser();
        bb_user.setGivenName(FIRST_NAME);
        bb_user.setFamilyName(LAST_NAME);
        bb_user.setName(USER_NAME);
        bb_user.setPassword(PASSWORD);
        String id = (String)bb_user.persist();
        assertNotNull(id);
        bb_user.setId(id);
        return (BbUser) bb_user.retrieve();
    }
	
	private void deleteUser(BbUser bb_user){
    	//preserve the user/login name
    	String username = bb_user.getName();
    	//preserve the lms_id
    	String lms_id = bb_user.getId();
    	logger.debug("Deleting user " + lms_id);
        bb_user.delete();
    	//assertTrue(bb_user.delete()); //doesn't work with testDeleteUserByInstitutionRole()
        logger.debug("Verifying that user " + lms_id + " was deleted.");
        bb_user = new BbUser();
        bb_user.setName(username);
        bb_user = (BbUser) bb_user.retrieve();
        assertTrue(bb_user==null);
        logger.debug("User " + lms_id + " deleted.");
    }
}
