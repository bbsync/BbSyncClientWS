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

package org.bbsync.webservice.client.proxytool;

import java.io.Serializable;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.ClientWebService;
import org.bbsync.webservice.client.context.BbContext;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.CourseMembershipRoleVO;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.CourseMembershipVO;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.CourseRoleFilter;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.DeleteCourseMembership;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.DeleteCourseMembershipResponse;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.DeleteGroupMembership;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.DeleteGroupMembershipResponse;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.GetCourseMembership;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.GetCourseMembershipResponse;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.GetCourseRoles;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.GetCourseRolesResponse;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.GetGroupMembership;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.GetGroupMembershipResponse;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.GetServerVersion;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.GetServerVersionResponse;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.GroupMembershipVO;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.InitializeCourseMembershipWS;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.InitializeCourseMembershipWSResponse;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.MembershipFilter;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.SaveCourseMembership;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.SaveCourseMembershipResponse;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.SaveGroupMembership;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.SaveGroupMembershipResponse;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.VersionVO;

public abstract class CourseMembershipProxyTool extends AbstractProxyTool
implements ClientWebService, Serializable{
    private static final Logger logger = Logger.getLogger(AbstractProxyTool.class.getName());
    private static final long serialVersionUID = 5555000000000000L;
    private static String ws_url = null;
    private static String client_pw = null;
    private transient CourseMembershipWSStub membershipWS = null;
    private transient BbContext session = null;
    private static final String CLIENT_PROGRAM_ID = "CourseMembershipProxyTool";
    
	///////////////////////////////////////////////////////////////////////////
	//  Constructor  //////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
    protected CourseMembershipProxyTool() {
    	super();
    	if (client_pw == null)
    		client_pw = config.getConfigClass(CLIENT_PROGRAM_ID, "sharedSecret");
    	if (ws_url == null)
    		ws_url = config.getConfigClass(CLIENT_PROGRAM_ID, "webServiceURL");
    	}

    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    private void loginAsTool() {
    	session = new BbContext();
    	session.loginAsTool(CLIENT_PROGRAM_ID, client_pw);
    	membershipWS = (CourseMembershipWSStub) session.initializeClient(CourseMembershipWSStub.class, ws_url);
    }
        
    private void logout(){
    	session.logout();
    }

    ///////////////////////////////////////////////////////////////////////////
    //  All CourseMembershipWS Methods  ///////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    
    /**
     * This method allows specific CourseMembershipVO records to be deleted 
     * (i.e. remove users from a Course). If the Role of the user invoking this
     * method is *System Administrator* then the course_id parameter can be 
     * null, allowing for Course Memberships from different Courses. Otherwise, 
     * the course_id is required and all Course Memberships must be associated 
     * with the specified Course.
     * 
     * @param course_id - the ID of the course that is associated with the 
     *                    GroupMemberships to load.  This parameter can be 
     *                    null if invoked by a System Administrator.  
     *                    Otherwise, course_id is required. The course_id 
     *                    should be generated by Blackboard, in the form 
     *                    "_nnn_1" where nnn is an integer.
     * @param course_membership_ids - An array of CourseMembership IDs to be 
     *                                deleted.  The course_membership_ids 
     *                                should be generated by Blackboard, in the
     *                                form "_nnn_1" where nnn is an integer.
     * @return Returns an array of course_membership_ids that were successfully 
     *         deleted.
     */
    protected String[]	deleteCourseMembership(String course_id, String[] course_membership_ids){
    	loginAsTool();
    	DeleteCourseMembershipResponse response = null;
    	DeleteCourseMembership dcm = new DeleteCourseMembership();
    	dcm.setCourseId(course_id);
    	dcm.setIds(course_membership_ids);
    	try {
			response = membershipWS.deleteCourseMembership(dcm);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to delete Course Memberships: " + course_membership_ids);
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("DeleteCourseMembership returned null.");
    		return null;
    	}
    	return response.get_return();
    }
    
    /**
     * This method allows specific Group Membership records to be deleted (i.e.
     * remove users from a Course Group). If the Role of the user invoking this
     * method is *System Administrator* then the course_id parameter can be 
     * null, allowing for Group Memberships from different Courses. Otherwise, 
     * the course_id is required and all Group Memberships must be associated 
     * with the specified Course.
     * 
     * @param course_id - the ID of the course that is associated with the 
     *                    GroupMemberships to load.  This parameter can be 
     *                    null if invoked by a System Administrator.  
     *                    Otherwise, course_id is required. The course_id 
     *                    should be generated by Blackboard, in the form 
     *                    "_nnn_1" where nnn is an integer.
     * @param group_membership_ids - An array of GroupMembership IDs to be 
     *                               deleted.  The group_membership_ids should 
     *                               be generated by Blackboard, in the form 
     *                               "_nnn_1" where nnn is an integer.
     * @return Returns an array of group_membership_ids that were successfully 
     *         deleted.
     */
    protected String[]	deleteGroupMembership(String course_id, String[] group_membership_ids){
    	loginAsTool();
    	DeleteGroupMembershipResponse response = null;
    	DeleteGroupMembership dgm = new DeleteGroupMembership();
    	dgm.setCourseId(course_id);
    	dgm.setIds(group_membership_ids);
    	try {
			response = membershipWS.deleteGroupMembership(dgm);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to delete Group Memberships: " + group_membership_ids);
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("DeleteGroupMembership returned null.");
    		return null;
    	}
    	return response.get_return();
    }
    
    /**
     * This method allows CourseMembershipVO objects to be loaded based on a 
     * MembershipFilter. This method will return both Active and Inactive 
     * records. The MembershipFilter has a int filterType which should be set 
     * appropriately before invoking this method. The filterType defines the 
     * following predefined types of queries:
     *  - filterType = 1 will load GroupMembershipVO records by Id's.
     *  - filterType = 2 will load GroupMembershipVO records by course Id's.
     *  - filterType = 5 will load CourseMembershipVO records by User Id's.
     *  - filterType = 6 will load CourseMembershipVO records by course and 
     *                   user Id's.
     *  - filterType = 7 will load CourseMembershipVO records by course Id's 
     *                   and role Identifier.
     * If the Role of the user invoking this method is *System Administrator* 
     * then the course_id parameter can be null, allowing for Course 
     * Memberships from different Courses. Otherwise, the course_id is required
     * and all Course Memberships must be associated with the specified Course.
     *  
     * @param course_id - the ID of the course that is associated with the 
     *                    GroupMemberships to load.  This parameter can be 
     *                    null if invoked by a System Administrator.  
     *                    Otherwise, course_id is required. The course_id 
     *                    should be generated by Blackboard, in the form 
     *                    "_nnn_1" where nnn is an integer.
     * @param filter - the MembershipFilter that defines the type of query to
     *                 use.
     * @return Returns an array of CourseMembershipVO objects.
     */
    protected CourseMembershipVO[]	getCourseMembership(String course_id, MembershipFilter filter){
    	loginAsTool();
    	GetCourseMembershipResponse response = null;
    	GetCourseMembership gcm = new GetCourseMembership();
    	gcm.setCourseId(course_id);
    	gcm.setF(filter);
    	try {
			response = membershipWS.getCourseMembership(gcm);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to get Course Membership");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("getCourseMembership returned null.");
    		return null;
    	}
    	return response.get_return();
    }
    
    /**
     * This method will load CourseMembershipRoleVO objects from the Academic 
     * Suite based on the CourseRoleFilter.
     * 
     * @param filter - A CourseRoleFilter that will define specific 
     *                 CourseMembershipRole IDs.  If filter is null, an array 
     *                 containing all CourseMembershipRoles will be returned. 
     * 
     * @return  Returns an array of CourseMembershipRoleVOs.
     */
    protected CourseMembershipRoleVO[] getCourseRoles(CourseRoleFilter filter){
    	loginAsTool();
    	GetCourseRolesResponse response = null;
    	GetCourseRoles gcr = new GetCourseRoles();
    	gcr.setF(filter);
    	try {
			response = membershipWS.getCourseRoles(gcr);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to get Course Roles");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("getCourseMembership returned null.");
    		return null;
    	}
    	return response.get_return();
    }
    
    /**
     * This method allows GroupMembershipVO objects to be loaded based on a 
     * MembershipFilter. This method will return both Active and Inactive 
     * records. The MembershipFilter has a int filterType which should be set 
     * appropriately before invoking this method. The filterType defines the 
     * following predefined types of queries:
     *  - filterType = 1 will load GroupMembershipVO records by Id's.
     *  - filterType = 2 will load GroupMembershipVO records by course Id's.
     *  - filterType = 3 will load GroupMembershipVO records by 
     *                   CourseMembershipVO Id's.
     *  - filterType = 4 will load GroupMembershipVO records Group Id's.
     * If the Role of the user invoking this method is *System Administrator* 
     * then the course_id parameter can be null, allowing for Group Memberships
     * from different Courses. Otherwise, the course_id is required and all 
     * Group Memberships must be associated with the specified Course.
     *  
     * @param course_id - the ID of the course that is associated with the 
     *                    GroupMemberships to load.  This parameter can be 
     *                    null if invoked by a System Administrator.  
     *                    Otherwise, course_id is required. The course_id 
     *                    should be generated by Blackboard, in the form 
     *                    "_nnn_1" where nnn is an integer.
     * @param filter - the MembershipFilter that defines the type of query to
     *                 use.
     * @return Returns an array of GroupMembershipVO objects.
     */
    protected GroupMembershipVO[] getGroupMembership(String course_id, MembershipFilter filter){
    	loginAsTool();
    	GetGroupMembershipResponse response = null;
    	GetGroupMembership ggm = new GetGroupMembership();
    	ggm.setCourseId(course_id);
    	ggm.setF(filter);
    	try {
			response = membershipWS.getGroupMembership(ggm);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to get Group Membership");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("getGroupMembership returned null.");
    		return null;
    	}
    	return response.get_return();
    }
    
    /**
     * Returns the current version of this web service on the server.
     * @param unused - this is an optional parameter put here to make the 
     *                 generation of .net client applications from the wsdl 
     *                 'cleaner' (0-argument methods do not generate clean 
     *                 stubs and are much harder to have the same method name
     *                 across multiple Web Services in the same .net client)
     * @return  Returns the current version (VersionVO) of this web service on
     *          the server.
     */
    protected VersionVO	getServerVersion(VersionVO unused){
    	loginAsTool();
    	GetServerVersionResponse response = null;
    	GetServerVersion version = new GetServerVersion();
    	version.setUnused(unused);
    	try {
			response = membershipWS.getServerVersion(version);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to get Server Version");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("getServerVersion returned null.");
    		return null;
    	}
    	return response.get_return();
    }
    
    /**
     * Sets the client version to version 1 and returns an appropriate session.
     * 
     * @param ignore - this is an optional parameter put here to make the 
     *                 generation of .net client applications from the wsdl 
     *                 'cleaner' (0-argument methods do not generate clean 
     *                 stubs and are much harder to have the same method name
     *                 across multiple Web Services in the same .net client)
     * @return Returns true to indicate that the session has been initialized 
     *                 for the CourseMembership web service.
     */
    protected boolean	initializeCourseMembershipWS(boolean ignore){
    	loginAsTool();
    	boolean response = _initializeCourseMembershipWS(ignore);
    	logout();
    	return response;
    }

    private boolean	_initializeCourseMembershipWS(boolean ignore){
    	InitializeCourseMembershipWSResponse response = null;
    	InitializeCourseMembershipWS init = new InitializeCourseMembershipWS();
    	init.setIgnore(ignore);
    	try {
			response = membershipWS.initializeCourseMembershipWS(init);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to initialize Course Membership Web Service");
	        logger.error(e.getMessage());
	        return false;
		}
    	if(response==null){
    		logger.warn("initializeCourseMembershipWS returned null.");
    		return false;
    	}
    	return response.get_return();
    }
    
    /**
     * This method allows multiple CourseMembershipVO records to be added to a 
     * Course (i.e. add Users to a Course). If the Role of the user invoking 
     * this method is *System Administrator* then the course_id parameter can 
     * be null, allowing for Course Memberships from different Courses. 
     * Otherwise, the course_id is required and all Course Memberships must be 
     * associated with the specified Course.
     * 
     * @param course_id - the ID of the course that is associated with the 
     *                    CourseMemberships to save.  This parameter can be 
     *                    null if invoked by a System Administrator.  
     *                    Otherwise, course_id is required. The course_id 
     *                    should be generated by Blackboard, in the form 
     *                    "_nnn_1" where nnn is an integer.
     * @param memberships - an array Course Memberships to save
     * 
     * @return Returns an array of successfully saved CourseMembership IDs.
     */
    protected String[]	saveCourseMembership(String courseId, CourseMembershipVO[] memberships){
    	loginAsTool();
    	SaveCourseMembershipResponse response = null;
    	SaveCourseMembership save = new SaveCourseMembership();
    	save.setCmArray(memberships);
    	save.setCourseId(courseId);
    	try {
			response = membershipWS.saveCourseMembership(save);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to save Course Membership");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("saveCourseMembership returned null.");
    		return null;
    	}
    	return response.get_return();
    }
    
    /**
     * This method allows multiple GroupMembershipVO records to be added to a 
     * Course (i.e. add Course Users to Course Groups). If the Role of the user
     * invoking this method is *System Administrator* then the course_id 
     * parameter can be null, allowing for Group Memberships from different 
     * Courses. Otherwise, the course_id is required and all Group Memberships 
     * must be associated with the specified Course.
     * 
     * @param course_id - the ID of the course that is associated with the 
     *                    GroupMemberships to save.  This parameter can be 
     *                    null if invoked by a System Administrator.  
     *                    Otherwise, course_id is required. The course_id 
     *                    should be generated by Blackboard, in the form 
     *                    "_nnn_1" where nnn is an integer.
     * @param memberships - an array GroupMembershipVOs to save
     * 
     * @return Returns an array of successfully saved GroupMembership IDs.
     */
    protected String[] saveGroupMembership(String course_id, GroupMembershipVO[] memberships){
    	loginAsTool();
    	SaveGroupMembershipResponse response = null;
    	SaveGroupMembership save = new SaveGroupMembership();
    	save.setG(memberships);
    	save.setCourseId(course_id);
    	try {
			response = membershipWS.saveGroupMembership(save);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to save Group Membership");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("saveGroupMembership returned null.");
    		return null;
    	}
    	return response.get_return();
    }
}
