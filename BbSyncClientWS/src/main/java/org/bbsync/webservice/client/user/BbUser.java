package org.bbsync.webservice.client.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.generated.UserWSStub.UserExtendedInfoVO;
import org.bbsync.webservice.client.generated.UserWSStub.UserFilter;
import org.bbsync.webservice.client.generated.UserWSStub.UserVO;
import org.bbsync.webservice.client.proxytool.UserProxyTool;

public class BbUser extends UserProxyTool {
	private static final long serialVersionUID = 4444000000002222L;
	private static final Logger logger = Logger.getLogger(UserProxyTool.class.getName());
	private UserVO _user_vo = null;

	//API documentation states that System Roles are provided by 
	//blackboard.ws.user.UserType (see Building Block API).  However, the B2
	//API does not provide the actual static VALUES of these UserTypes; nor is 
	//there a web services mechanism in place to determine these values as 
	//there is for portal/institution roles.  For system roles, all we can do 
	//is use the getAllSystemRoles() method, which returns a String array of 
	//ALL system roles.  HOWEVER, the system role Strings returned in this
	//array DO NOT match the Role Names or Role IDs displayed in the Admin GUI
	//although the array strings can be used interchangeably with the Role IDs
	//in the Admin GUI - with the exception of SYSTEM_ROLE_NONE, which can only
	//be set with the String "USER".
	public static final String SYSTEM_ROLE_LMS_INTEGRATION_ADMIN = "LMS_INTEGRATION_ADMIN"; //Manages the front-end portal-e-commerce functionality related to purchases
	public static final String SYSTEM_ROLE_ACCOUNT_ADMIN  = "ACCOUNT_ADMIN";  //Account Administrator role.
	public static final String SYSTEM_ROLE_COURSE_CREATOR = "COURSE_CREATOR"; //Course Creator role.
	public static final String SYSTEM_ROLE_COURSE_SUPPORT = "COURSE_SUPPORT"; //Course Support role.
	public static final String SYSTEM_ROLE_GUEST          = "GUEST";          //Guest role.
	public static final String SYSTEM_ROLE_INTEGRATION    = "INTEGRATION";    //This role is private, used only for special processes that interact for data integration authentication.
	public static final String SYSTEM_ROLE_OBSERVER	      = "OBSERVER";       //Observer role.
	public static final String SYSTEM_ROLE_PORTAL         = "PORTAL";         //Portal Administrator role.
	public static final String SYSTEM_ROLE_SYSTEM_ADMIN   = "SYSTEM_ADMIN";   //System Administrator role.
	public static final String SYSTEM_ROLE_SYSTEM_SUPPORT = "SYSTEM_SUPPORT"; //System Support role.
	public static final String SYSTEM_ROLE_NONE           = "USER";           //THIS IS THE DEFAULT SYSTEM ROLE ASSIGNED TO NEW USERS may appear as "None" or "Default".	
	public static final String SYSTEM_ROLE_GOALS_MANAGER  = "BB_GOALS_ADMIN";
	public static final String SYSTEM_ROLE_RUBRIC_MANAGER = "BB_SYSTEM_RUBRIC_MANAGER";
	public static final String SYSTEM_ROLE_SURVEY_AUTHOR  = "BB_SURVEY_MANAGER";
	public static final String SYSTEM_ROLE_BB_OUTCOMES_ADMIN  = "BB_OUTCOMES_ADMIN";
	public static final String SYSTEM_ROLE_BB_TEMPLATES_ADMIN = "BB_TEMPLATES_ADMIN";
	
	//Gender types provided by UserVO API
	public static final String GENDER_TYPE_FEMALE  = "FEMALE";
	public static final String GENDER_TYPE_MALE    = "MALE";
	public static final String GENDER_TYPE_DEFAULT = "DEFAULT";
	public static final String GENDER_TYPE_UNKNOWN = "UNKNOWN";
	
    //See blackboard.ws.user.UserFilter & blackboard.ws.user.UserWSConstants
    //For information on constants used with blackboard.ws.user.UserVO
    //If called by getUser:
    private static final int GET_ALL_USERS_WITH_AVAILABILITY 		= 1;
    private static final int GET_USER_BY_ID_WITH_AVAILABILITY 		= 2;
    private static final int GET_USER_BY_BATCH_ID_WITH_AVAILABILITY = 3;
    private static final int GET_USER_BY_COURSE_ID_WITH_AVAILABILITY= 4;
    private static final int GET_USER_BY_GROUP_ID_WITH_AVAILABILITY = 5;
    private static final int GET_USER_BY_NAME_WITH_AVAILABILITY 	= 6;
    private static final int GET_USER_BY_SYSTEM_ROLE 				= 7;
    
	//Education levels provided by UserVO API
	public static final String EDUCATION_LEVEL_K_8         = "K_8";
	public static final String EDUCATION_LEVEL_HIGH_SCHOOL = "HIGH_SCHOOL";
	public static final String EDUCATION_LEVEL_FRESHMAN    = "FRESHMAN";
	public static final String EDUCATION_LEVEL_SOPHOMORE   = "SOPHOMORE";
	public static final String EDUCATION_LEVEL_JUNIOR      = "JUNIOR";
	public static final String EDUCATION_LEVEL_SENIOR      = "SENIOR";
	public static final String EDUCATION_LEVEL_UNKNOWN     = "UNKNOWN";
	public static final String EDUCATION_LEVEL_DEFAULT     = "DEFAULT";
	public static final String EDUCATION_LEVEL_GRADUATE_SCHOOL      = "GRADUATE_SCHOOL";
	public static final String EDUCATION_LEVEL_POST_GRADUATE_SCHOOL = "POST_GRADUATE_SCHOOL";
	
	public static final String PRIMARY_SYSTEM_ROLE_KEY = "org.bbsync.webservice.client.user.BbUser.PrimarySystemRole";
	public static final String PRIMARY_INS_ROLE_KEY = "org.bbsync.webservice.client.user.BbUser.PrimaryInsRole";

    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public BbUser() {
    	_user_vo = new UserVO();
    }

    private BbUser(UserVO user_vo) {
    	_user_vo = user_vo;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////
    public Serializable persist() { 
		String[] result = super.saveUser(new UserVO[]{_user_vo});
		if(result!=null && result.length==1) return result[0];
		return null;
	}
	
	public Object retrieve() {
		UserVO user = getUser(_user_vo);
		if(user!=null) return new BbUser(user);
		return null;
	}
	
	public boolean delete() {
		String[] deleted_ids = super.deleteUser(new String[]{getId()});		
		if(deleted_ids!=null && deleted_ids.length==1) return true;
		return false;
	}
    
	///////////////////////////////////////////////////////////////////////////
    //  Implemented ProxyTool Methods  ////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
	public long getServerVersion(){
		return super.getServerVersion(null).getVersion();
	}
	
	public boolean initializeUserWS(){
		return super.initializeUserWS(true);
	}
	
	public String[] saveUsers(BbUser[] bb_users){
		UserVO[] users = BbUserArray_to_UserVOArray(bb_users);
		return super.saveUser(users);
	}
	
	public String[] deleteUser(String[] user_ids){
		return super.deleteUser(user_ids);
	}
	public String[] deleteUsersByInstitutionRole(String[] ins_role_ids){
		return super.deleteUsersByInstitutionRole(ins_role_ids);
	}
	public boolean changeUserBatchUid(String original_batch_uid, String batch_uid){
		return super.changeUserBatchUid(original_batch_uid, batch_uid);
	}
	
	public boolean changeUserDataSourceId(String user_id, String new_data_source_id){
		return super.changeUserDataSourceId(user_id, new_data_source_id);
	}

	public BbUser[] getAllUsers(){
		return getAllUsersWithAvailability(null);
	}
	
	public BbUser[] getAllUsersWithAvailability(boolean availability){
		return getAllUsersWithAvailability(Boolean.valueOf(availability));
	}
	
	private BbUser[] getAllUsersWithAvailability(Boolean availability){
		UserFilter filter = new UserFilter();
		filter.setFilterType(GET_ALL_USERS_WITH_AVAILABILITY);
		if(availability!=null) filter.setAvailable(availability.booleanValue());
		return UserVOArray_to_BbUserArray(super.getUser(filter));
	}
	
	public BbUser[] getUsersBySystemRole(String system_role){
		UserFilter filter = new UserFilter();
		filter.setFilterType(GET_USER_BY_SYSTEM_ROLE);
		filter.setSystemRoles(new String[]{system_role});
		return UserVOArray_to_BbUserArray(super.getUser(filter));
	}
	
	/**
	 * Given a single username/login, this method will return a single BbUser. 
	 * @param username
	 * @return
	 */
	public BbUser getUserByName(String username){
		BbUser[] bb_users = getUsers(GET_USER_BY_NAME_WITH_AVAILABILITY, new String[]{username}, null);
		if(bb_users!=null && bb_users.length>0){
			return bb_users[0];
		}
		return null;
	}
	
	/**
	 * Given multiple usernames/logins, this method will return multiple BbUsers.
	 * @param usernames
	 * @return
	 */
	public BbUser[] getUsersByName(String[] usernames){
		return getUsers(GET_USER_BY_NAME_WITH_AVAILABILITY, usernames, null);
		
	}
	
	/**
	 * Given multiple usernames/logins, this method will return multiple BbUsers.
	 * This method allows for additional filtering with the availability parameter.
	 * @param usernames
	 * @param availability
	 * @return
	 */
	public BbUser[] getUsersByNameWithAvailability(String[] usernames, boolean availability){
		return getUsers(GET_USER_BY_NAME_WITH_AVAILABILITY, usernames, Boolean.valueOf(availability));
	}
	
	/**
	 * Given a single ID (LMS ID), this method will return a single BbUser. 
	 * @param id
	 * @return
	 */
	public BbUser getUserById(String id){
		BbUser[] bb_users = getUsers(GET_USER_BY_ID_WITH_AVAILABILITY, new String[]{id}, null);
		if(bb_users!=null && bb_users.length>0){
			return bb_users[0];
		}
		return null;
	}
	
	/**
	 * Given multiple IDs (LMS IDs), this method will return multiple BbUsers.
	 * @param ids
	 * @return
	 */
	public BbUser[] getUsersById(String[] ids){
		return getUsers(GET_USER_BY_ID_WITH_AVAILABILITY, ids, null);
	}
	
	/**
	 * Given multiple IDs (LMS IDs), this method will return multiple BbUsers.
	 * This method allows for additional filtering with the availability parameter.
	 * @param ids
	 * @param availability
	 * @return
	 */
	public BbUser[] getUsersByIdWithAvailability(String[] ids, boolean availability){
		return getUsers(GET_USER_BY_ID_WITH_AVAILABILITY, ids, Boolean.valueOf(availability));
	}
	
	/**
	 * Given a single Batch ID, this method will return a single BbUser. 
	 * @param batch_id
	 * @return
	 */
	public BbUser getUserByBatchId(String batch_id){
		BbUser[] bb_users = getUsers(GET_USER_BY_BATCH_ID_WITH_AVAILABILITY, new String[]{batch_id}, null);
		if(bb_users!=null && bb_users.length>0){
			return bb_users[0];
		}
		return null;
	}
	
	/**
	 * Given multiple Batch IDs, this method will return multiple BbUsers.
	 * @param usernames
	 * @return
	 */
	public BbUser[] getUsersByBatchId(String[] batch_ids){
		return getUsers(GET_USER_BY_BATCH_ID_WITH_AVAILABILITY, batch_ids, null);
		
	}
	
	/**
	 * Given multiple Batch IDs, this method will return multiple BbUsers.
	 * This method allows for additional filtering with the availability parameter.
	 * @param usernames
	 * @param availability
	 * @return
	 */
	public BbUser[] getUsersByBatchIdWithAvailability(String[] batch_ids, boolean availability){
		return getUsers(GET_USER_BY_BATCH_ID_WITH_AVAILABILITY, batch_ids, Boolean.valueOf(availability));
	}
	
	public BbUser[] getUsersByCourseIdWithAvailability(String[] course_ids, boolean availability){
		UserFilter filter = new UserFilter();
		filter.setFilterType(GET_USER_BY_COURSE_ID_WITH_AVAILABILITY);
		filter.setCourseId(course_ids);
		filter.setAvailable(availability);
		return UserVOArray_to_BbUserArray(super.getUser(filter));
	}

	public BbUser[] getUsersByGroupIdWithAvailability(String[] group_ids, boolean availability){
		UserFilter filter = new UserFilter();
		filter.setFilterType(GET_USER_BY_GROUP_ID_WITH_AVAILABILITY);
		filter.setGroupId(group_ids);
		filter.setAvailable(availability);
		return UserVOArray_to_BbUserArray(super.getUser(filter));
	}

	private BbUser[] getUsers(int filter_type, String[] ids, Boolean availability){
		UserFilter filter = new UserFilter();
		filter.setFilterType(filter_type);
		filter.setId(ids);
		if(availability!=null) filter.setAvailable(availability.booleanValue());
		return UserVOArray_to_BbUserArray(super.getUser(filter));
	}

	public String[] getAllSystemRoles(){
		return super.getSystemRoles(null);
	}
	
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
	/**
	 * @return Returns the system roles associated with this User. 
	 */
	public String[] getSystemRoles(){
		return _user_vo.getSystemRoles();
	}
	
	/**
	 * Sets the System role(s) for this user - When setting System Roles, 
	 * systemRoles[0] is considered the primary role. Possible values:  
	 *  - "LmsIntegrationAdmin" = SYSTEM_ROLE_LMS_INTEGRATION_ADMIN
	 *  - "AccountAdmin"        = SYSTEM_ROLE_ACCOUNT_ADMIN
	 *  - "CourseCreator"       = SYSTEM_ROLE_COURSE_CREATOR
	 *  - "CourseSupport"       = SYSTEM_ROLE_COURSE_SUPPORT
	 *  - "Guest"               = SYSTEM_ROLE_GUEST
	 *  - "Integration"         = SYSTEM_ROLE_INTEGRATION
	 *  - "Observer"            = SYSTEM_ROLE_OBSERVER
	 *  - "Portal"              = SYSTEM_ROLE_PORTAL
	 *  - "SystemAdmin"         = SYSTEM_ROLE_SYSTEM_ADMIN
	 *  - "SystemSupport"       = SYSTEM_ROLE_SYSTEM_SUPPORT
	 *  - "User"                = SYSTEM_ROLE_USER - this is the default/"None" role
	 * 
	 * @param system_roles - an array of system roles - systemRoles[0] is 
	 *                       considered the primary role.
	 */
	public void setSystemRoles(String[] system_roles){
			_user_vo.setSystemRoles(system_roles);
	}
		
	/**
	 * @return Returns all of the portal/institution roles associated with this
	 *         User. Note that this method returns an array of BbPortalRole 
	 *         "Role IDs" and that the order of roles in this array is not
	 *         guaranteed.  That is, YOU CANNOT RELIABLY DETERMINE A USER'S 
	 *         PRIMARY ROLE USING this method!  See BbUserRole for more
	 *         information on how to reliably determine primary and secondary
	 *         portal/institution roles for a given user. 
	 */
	public String[] getInsRoles(){
		return _user_vo.getInsRoles();
	}
	
	/**
	 * Sets the Institution/Portal roles for this user. When setting 
	 * Institution/Portal roles, ins_roles[0] is considered the primary 
	 * Institution/Portal role.  Possible value include BbPortalRole static 
	 * fields: 
	 *  - "PROSPECTIVE_STUDENT" = BbPortalRole.INSTITUTION_ROLE_PROSPECTIVE_STUDENT 
	 *  - "STUDENT"  = BbPortalRole.INSTITUTION_ROLE_STUDENT 
	 *  - "FACULTY"  = BbPortalRole.INSTITUTION_ROLE_FACULTY 
	 *  - "STAFF"    = BbPortalRole.INSTITUTION_ROLE_STAFF 
	 *  - "ALUMNI"   = BbPortalRole.INSTITUTION_ROLE_ALUMNI
	 *  - "GUEST"    = BbPortalRole.INSTITUTION_ROLE_GUEST 
	 *  - "OTHER"    = BbPortalRole.INSTITUTION_ROLE_OTHER 
	 *  - "OBSERVER" = BbPortalRole.INSTITUTION_ROLE_OBSERVER
	 *  - "UNKNOWN"  = BbPortalRole.INSTITUTION_ROLE_UNKNOWN 
	 * 
	 * @param ins_roles - insRoles[0] is considered the primary role.  All 
	 * other roles are secondary.  Order is not guaranteed for secondary roles.
	 * Additional (custom) roles may also be available.  Use BbPortalRole's
	 * getAllInstitutionRoles() method to determine all possible 
	 * portal/institution roles.
	 */
	public void setInsRoles(String[] ins_roles){
			_user_vo.setInsRoles(ins_roles);
	}
			
	/**
	 * @return Returns the gender type.
	 */
	public String getGenderType(){
		return _user_vo.getGenderType();
	}

	/**
	 * Sets the gender type for this User Possible values are:
	 *  - "FEMALE"  = GENDER_TYPE_FEMALE
	 *  - "MALE"    = GENDER_TYPE_MALE
	 *  - "DEFAULT" = GENDER_TYPE_DEFAULT
	 *  - "UNKNOWN" = GENDER_TYPE_UNKNOWN
	 *  
	 * @param gender_type - the gender type to set for this User.
	 */
	public void setGenderType(String gender_type){
		_user_vo.setGenderType(gender_type);
	}

	/**
	 * @return Returns this User's password.  When retrieved from Blackboard,
	 *         this value will be encrypted.
	 */
	public String getPassword(){
		return _user_vo.getPassword();
	}

	/**
	 * When creating a user, this is the raw text of the desired password. If 
	 * left blank then the username will be used as the password. When updating
	 * a user, this may have three possible values:
	 *  - if left blank/empty: the password is unchanged
	 *  - if set as the same value retrieved from an earlier getUser() call: 
	 *    the password is unchanged
	 *  - Anything else: Will be used as the new password
	 *  
	 * @param password - the password to set for this User
	 */
	public void setPassword(String password){
		_user_vo.setPassword(password);
	}

	/**
	 * @return Returns this User's batch UID.
	 */
	public String getBatchUid(){
		return _user_vo.getUserBatchUid();
	}

	/**
	 * When creating a user, if this field is not set/left blank, the batch UID
	 * field will be set the same as the User's Name.
	 *  
	 * @param user_batch_uid - the batch UID for this user.
	 */
	public void setBatchUid(String user_batch_uid){
		_user_vo.setUserBatchUid(user_batch_uid);
	}

	/**
	 * @return Returns the ID of the DataSource associated with this User.
	 */
	public String getDataSourceId(){
		return _user_vo.getDataSourceId();
	}

	/**
	 * Sets the ID of the DataSource associated with this User.
	 * 
	 * @param data_source_id - the the ID of the DataSource associated with 
	 *                         this User.
	 */
	public void setDataSourceId(String data_source_id){
		_user_vo.setDataSourceId(data_source_id);
	}

	/**
	 * @return Return true if this User is available.  Otherwise, returns 
	 *         false.
	 */
	public boolean isAvailable(){
		return _user_vo.getIsAvailable();
	}

	/**
	 * Set's this User's availability.
	 * 
	 * @param available - set true if this User is available.  Otherwise, set 
	 *                    false.
	 */
	public void setAvailable(boolean available){
		_user_vo.setIsAvailable(available);
	}

	/**
	 * @return
	 */
	public long getBirthDate(){
		return super.bbLong2javaLong(_user_vo.getBirthDate());
	}

	/**
	 * Sets the birth date value for this User.  Be aware that Blackboard does 
     * not use milliseconds.  If you include milliseconds in this long value, 
     * they will be stripped off.
     * 
     * @param birth_date - a long representation of the end date.
	 */
	public void setBirthDate(long birth_date){
        if (String.valueOf(birth_date).length() > 10) {
            _user_vo.setBirthDate(super.javaLong2bbLong(birth_date));
        } else {
            _user_vo.setBirthDate(birth_date);
        }
	}

	/**
	 * @return Returns this User's ID.
	 */
	public String getId(){
		return _user_vo.getId();
	}

	/**
	 * Sets the ID for this User.
	 * @param id - the ID value for this User should be generated by 
	 *             Blackboard, in the form "_nnn_1" where nnn is an integer.
	 */
	public void setId(String id){
		_user_vo.setId(id);
	}

	/**
	 * @return Returns this User's name (aka username/login name).
	 */
	public String getName(){
		return _user_vo.getName();
	}

	/**
	 * Sets this User's name (aka username/login name).
	 * 
	 * @param name - the username/login name for this User.
	 */
	public void setName(String name){
		_user_vo.setName(name);
	}	
			
	/**
	 * @return Returns all ExtendedInfo for this User.
	 */
	public BbUserExtendedInfo getExtendedInfo(){
		UserExtendedInfoVO user_xinfo_vo = _user_vo.getExtendedInfo();
		if(user_xinfo_vo==null) return null;
		return new BbUserExtendedInfo(user_xinfo_vo);
	}

	/**
	 * Sets the ExtendedInfo for this User. The ExtendedInfo includes all 
	 * 'extended' information about a user such as full name, email, address, 
	 * etc.
	 * 
	 * @param extended_info - the ExtendedInfo to set for this User
	 */
	public void setExtendedInfo(BbUserExtendedInfo extended_info){
		_user_vo.setExtendedInfo(extended_info.getVO());
	}

	/**
	 * @return Returns the Education level associated with this User.
	 */
	public String getEducationLevel(){
		return _user_vo.getEducationLevel();
	}

	/**
	 * Sets the education level for this User. Possible values are:
	 * 	- "K_8"                  = EDUCATION_LEVEL_K_8
	 * 	- "HIGH_SCHOOL"          = EDUCATION_LEVEL_HIGH_SCHOOL
	 * 	- "FRESHMAN"             = EDUCATION_LEVEL_FRESHMAN
	 * 	- "SOPHOMORE"            = EDUCATION_LEVEL_SOPHOMORE
	 * 	- "JUNIOR"               = EDUCATION_LEVEL_JUNIOR
	 * 	- "SENIOR"               = EDUCATION_LEVEL_SENIOR
	 * 	- "UNKNOWN"              = EDUCATION_LEVEL_UNKNOWN
	 * 	- "DEFAULT"              = EDUCATION_LEVEL_DEFAULT
	 * 	- "GRADUATE_SCHOOL"      = EDUCATION_LEVEL_GRADUATE_SCHOOL
	 * 	- "POST_GRADUATE_SCHOOL" = EDUCATION_LEVEL_POST_GRADUATE_SCHOOL
	 * 
	 * @param education_level - the education level to set for this User.
	 */
	public void setEducationLevel(String education_level){
		_user_vo.setEducationLevel(education_level);
	}

	/**
	 * @return Returns this User's student ID.  
	 */
	public String getStudentId(){
		return _user_vo.getStudentId();
	}

	/**
	 * Sets this User's student ID (e.g. the ID generated by a Student 
	 * Information System).
	 * 
	 * @param student_id - the student ID to set for this User.
	 */
	public void setStudentId(String student_id){
		_user_vo.setStudentId(student_id);
	}

	/**
	 * @return Returns this User's title (e.g. "Mr." or "Mrs.").
	 */
	public String getTitle(){
		return _user_vo.getTitle();
	}

	/**
	 * Sets a title for this user (e.g. "Mr." or "Mrs.").
	 * 
	 * @param title - the title to set for this User.
	 */
	public void setTitle(String title){
		_user_vo.setTitle(title);
	}

	/**
	 * @return Returns the expansion data for this User.
	 */
	public String[] getExpansionData(){
		return _user_vo.getExpansionData();
	}

    /**
     * Expansion data contains a list of key=value strings. Current keys:
     *  - USER.UUID = A unique generated ID for this User which never changes 
     *                  (This value is read-only)
     *
     * @param expansion_data - the expansion data to set.
     */
 	public void setExpansionData(String[] expansion_data){
		_user_vo.setExpansionData(expansion_data);
	}
			
	private UserVO getUser(UserVO user_vo){
		UserVO[] users = null;
		if(user_vo.getId()!=null && user_vo.getId().length()>0){
			users = getUsersById(new UserVO[]{user_vo});
		}
		else if(user_vo.getUserBatchUid()!=null && user_vo.getUserBatchUid().length()>0){
			users = getUsersByBatchUid(new UserVO[]{user_vo});
		}
		else if(user_vo.getName()!=null && user_vo.getName().length()>0){
			users = getUsersByName(new UserVO[]{user_vo});
		}
		else{
			logger.error("Unable to get user. Missing required information.");
			return null;
		}
		if(users!=null && users.length>0){
			return users[0];
		}
		return null;
	}
	
	private UserVO[] getUsersByName(UserVO[] users){
		UserFilter userFilter = new UserFilter();
		userFilter.setFilterType(GET_USER_BY_NAME_WITH_AVAILABILITY);
		List<String> usernames = new ArrayList<String>();
		for(UserVO bb_user : users){
			String username = bb_user.getName();
			if(username!=null && username.length()>0) usernames.add(username);
		}
		userFilter.setName(usernames.toArray(new String[]{}));
		//userFilter.setAvailable(availability);
		return super.getUser(userFilter);
	}
	
	private UserVO[] getUsersById(UserVO[] users){		
		UserFilter userFilter = new UserFilter();
		userFilter.setFilterType(GET_USER_BY_ID_WITH_AVAILABILITY);
		List<String> ids = new ArrayList<String>();
		for(UserVO bb_user : users){
			String id = bb_user.getId();
			if(id!=null && id.length()>0) ids.add(id);
		}
		userFilter.setId(ids.toArray(new String[]{}));
		//userFilter.setAvailable(availability);
		return super.getUser(userFilter);
	}
	
	private UserVO[] getUsersByBatchUid(UserVO[] users){		
		UserFilter userFilter = new UserFilter();
		userFilter.setFilterType(GET_USER_BY_BATCH_ID_WITH_AVAILABILITY);
		List<String> batch_uids = new ArrayList<String>();
		for(UserVO bb_user : users){
			String batch_uid = bb_user.getUserBatchUid();
			if(batch_uid!=null && batch_uid.length()>0) batch_uids.add(batch_uid);
		}
		userFilter.setBatchId(batch_uids.toArray(new String[]{}));
		//userFilter.setAvailable(availability);
		return super.getUser(userFilter);
	}
	
	private BbUser[] UserVOArray_to_BbUserArray(UserVO[] vo_users){
		if(vo_users==null) return null;
		List<BbUser> bb_users = new ArrayList<BbUser>();
		for(UserVO bb_user : vo_users){
			if(bb_user!=null) bb_users.add(new BbUser(bb_user));
		}
		if(bb_users==null || bb_users.size()==0) return null;
		return bb_users.toArray(new BbUser[]{});
	}
	
	private UserVO[] BbUserArray_to_UserVOArray(BbUser[] bb_users){
		if(bb_users==null) return null;
		List<UserVO> users = new ArrayList<UserVO>();
		for(BbUser user : bb_users){
			if(user!=null) users.add(user.getVO());
		}
		if(users==null || users.size()==0) return null;
		return users.toArray(new UserVO[]{});
	}	
	
	private UserVO getVO(){
		return _user_vo;
	}

    ///////////////////////////////////////////////////////////////////////////
    //  Convenience Methods for accessing UserExtendedInfo  ///////////////////
    ///////////////////////////////////////////////////////////////////////////
	
	public String getFamilyName(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getFamilyName(); 
	}
	
	public void setFamilyName(String family_name){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setFamilyName(family_name);
	}
	
	public String getGivenName(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getGivenName();
	}
	
	public void setGivenName(String given_name){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setGivenName(given_name);
	}
	
	public String getMiddleName(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getMiddleName();
	}
	
	public void setMiddleName(String middle_name){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setMiddleName(middle_name);
	}
	
	public String getEmailAddress(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getEmailAddress();
	}
	
	public void setEmailAddress(String email_address){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setEmailAddress(email_address);
	}
	
	public String getJobTitle(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getJobTitle();
	}
	
	public void setJobTitle(String job_title){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setJobTitle(job_title);
	}
	
	public String getCompany(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getCompany();
	}
	
	public void setCompany(String company){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setCompany(company);
	}
	
	public String getDepartment(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getDepartment();
	}
	
	public void setDepartment(String department){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setDepartment(department);
	}
	
	public String getStreet1(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getStreet1();
	}
	
	public void setStreet1(String street_1){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setStreet1(street_1);
	}
	
	public String getStreet2(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getStreet2();
	}
	
	public void setStreet2(String street_2){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setStreet2(street_2);
	}
	
	public String getCity(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getCity();
	}
	
	public void setCity(String city){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setCity(city);
	}
	
	public String getState(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getState();
	}
	
	public void setState(String state){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setState(state);
	}
	
	public String getCountry(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getCountry();
	}
	
	public void setCountry(String country){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setCountry(country);
	}
	
	public String getZipCode(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getZipCode();
	}
	
	public void setZipCode(String zip_code){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setZipCode(zip_code);
	}
	
	public String getHomePhone1(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getHomePhone1();
	}
	
	public void setHomePhone1(String home_phone_1){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setHomePhone1(home_phone_1);
	}
	
	public String getHomePhone2(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getHomePhone2();
	}
	
	public void setHomePhone2(String home_phone_2){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setHomePhone2(home_phone_2);
	}
	
	public String getBusinessPhone1(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getBusinessPhone1();
	}
	
	public void setBusinessPhone1(String business_phone_1){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setBusinessPhone1(business_phone_1);
	}
	
	public String getBusinessPhone2(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getBusinessPhone2();
	}
	
	public void setBusinessPhone2(String business_phone_2){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setBusinessPhone2(business_phone_2);
	}
	
	public String getMobilePhone(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getMobilePhone();
	}
	
	public void setMobilePhone(String mobile_phone){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setMobilePhone(mobile_phone);
	}
	
	public String getHomeFax(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getHomeFax();
	}
	
	public void setHomeFax(String home_fax){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setHomeFax(home_fax);
	}
	
	public String getBusinessFax(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getBusinessFax();
	}
	
	public void setBusinessFax(String business_fax){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setBusinessFax(business_fax);
	}
	
	public String getWebPage(){
		if(_user_vo.getExtendedInfo()==null) return null;
		return _user_vo.getExtendedInfo().getWebPage();
	}
	
	public void setWebPage(String web_page){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		_user_vo.getExtendedInfo().setWebPage(web_page);
	}
	
	public String getOtherName(){
		if(_user_vo.getExtendedInfo()==null) return null;
		if(getExtendedInfo().getExpansionData()==null) return null;
		return super.getExpansionData(this.getExtendedInfo().getExpansionData(), "USER.OTHERNAME");
	}

	public void setOtherName(String other_name){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		if(getExtendedInfo().getExpansionData()==null) {
			_user_vo.getExtendedInfo().setExpansionData(new String[]{"USER.OTHERNAME=" + other_name});
			return;
		}
		_user_vo.getExtendedInfo().setExpansionData(super.setExpansionData(getExtendedInfo().getExpansionData(), "USER.OTHERNAME", other_name));
	}

	public String getSuffix(){
		if(_user_vo.getExtendedInfo()==null) return null;
		if(getExtendedInfo().getExpansionData()==null) return null;
		return super.getExpansionData(this.getExtendedInfo().getExpansionData(), "USER.SUFFIX");	
	}
	
	public void setSuffix(String suffix){
		if(_user_vo.getExtendedInfo()==null) _user_vo.setExtendedInfo(new UserExtendedInfoVO());
		if(getExtendedInfo().getExpansionData()==null) {
			_user_vo.getExtendedInfo().setExpansionData(new String[]{"USER.SUFFIX=" + suffix});
			return;
		}
		_user_vo.getExtendedInfo().setExpansionData(super.setExpansionData(getExtendedInfo().getExpansionData(), "USER.SUFFIX", suffix));
	}
	
    ///////////////////////////////////////////////////////////////////////////
    //  Convenience Methods for Accessing Expansion Data  /////////////////////
    ///////////////////////////////////////////////////////////////////////////
	
	public String getUserUUID(){
		if(getExpansionData()==null) return null;
		return super.getExpansionData(this.getExpansionData(), "USER.UUID");
	}
}
