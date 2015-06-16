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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bbsync.webservice.client.generated.UserWSStub.UserRoleVO;
import org.bbsync.webservice.client.proxytool.UserProxyTool;

/**
 * BbUserRole associates BbPortalRoles with BbUsers.
 * 
 * You can think of a BbUserRole object as a membership object that
 * associates BbUser objects with BbPortalRole Objects. The major difference 
 * being that you cannot use a BbUserRole object to persist or delete 
 * BbUserRoles like you can with other membership objects.
 * 
 * BbUserRoles are actually created and deleted by using the BbUser method, 
 * setInsRoles(String[] ins_roles).  Note that this method takes an array of 
 * BbPortalRole "Role IDs" and the role in position [0] of the array is the 
 * primary role.  All other roles in the array are considered secondary roles.
 * Note that BbUser's getInsRoles():String[] method returns an array of
 * BbPortalRole "Role IDs".  The order of roles in this array is not 
 * guaranteed.  That is, YOU CANNOT RELIABLY DETERMINE THE PRIMARY ROLE USING
 * getInsRoles()!
 * 
 * A primary role (and secondary roles) can be determined by performing the 
 * following steps:
 * 1 - Use BbUser's getInsRoles() method to get ALL of the BbPortalRole 
 *     "Role IDs" associated with that user.
 * 2 - Convert the array of "Role IDs" to actual BbPortalRole objects using
 *     BbPortalRole's getInstitutionRolesByRoleId(String role_ids) method.
 * 3 - Use the getSecondaryInsRolesByUserId() method to get an array of 
 *     secondary BbUserRole objects - keeping in mind that for each BbUserRole
 *     in the array, a call to getInsRoleId() will return a BbPortalRole ID
 *     (the internal Blackboard ID, NOT the "Role ID").
 * 4 - Subtract the BbPortalRoles referenced by ID in the step #3 array from 
 *     the BbPortalRoles array generated in step #2.  This will identify the
 *     BbPortalRole representing the user's primary portal/institution role.
 * 
 * This convoluted process is the most direct way to reliably determine a 
 * user's primary role vs. any secondary roles. It stems from the design of the
 * underlying Blackboard web services API.  All of my attempts to re-engineer
 * this process using Blackboard's default web services objects have resulted
 * in excessive API calls and confusing source code.  So for now, I've decided 
 * to implement portal/institution roles as they are in the underlying 
 * Blackboard web services API - with some changes to method names (for 
 * clarity) and A LOT of documentation in the comments. In the future, 
 * portal/institution roles may need to be addressed with a custom web service.  
 */
public class BbUserRole extends UserProxyTool {
	private static final long serialVersionUID = 4444000000004444L;
	private UserRoleVO user_role = null;
	
	///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
	public BbUserRole() {
        user_role = new UserRoleVO();
    }
	
	public BbUserRole(UserRoleVO user_role_vo) {
        user_role = user_role_vo;
    }
	
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////	
	/**
	 * Currently not implemented.  A UserRole represents an association between
	 * a user and a portal/institution role.  UserRoles are added using the 
	 * BbUser method setInsRoles(String[] ins_roles) where "ins_roles" is an 
	 * array of BbPortalRole "Role IDs" NOT BbPortalRole IDs - as are used in
	 * this class.  NOTE:  When adding UserRoles, be sure to understand the
	 * rules governing primary and secondary roles. 
	 * 
	 * @return Returns null.
	 */
	public Serializable persist() {
		return null;
	}
	
	/**
	 * Currently not implemented.  A UserRole represents an association between
	 * a user and a portal/institution role.  UserRoles are retrieved using 
	 * two methods: 1) getSecondaryInsRolesByUserId(String user_id) implemented
	 * in this class does what its name implies.  2) the BbUser method 
	 * getInsRoles() returns an array of ALL portal/institution roles 
	 * associated with that user.  Determining the difference between the 
	 * result of these two method calls will reliably revile the primary
	 * portal/institution role.
	 * 
	 * NOTE: getSecondaryInsRolesByUserId(String user_id) returns an array of
	 * BbUserRole objects that each contain a BbPortalRole ID, while BbUser's
	 * getInsRoles returns an array of BbPortalRole "Role ID" Strings (not 
	 * BbPortalRole IDs).  Use BbPortalRole to match up these two different 
	 * identifiers. 
	 * 
	 * @return Returns null.
	 */
	public Object retrieve() {
		return null;
	}
	
	/**
	 * Currently not implemented.  A UserRole represents an association between
	 * a user and a portal/institution role.  UserRoles are deleted using the 
	 * BbUser method setInsRoles(String[] ins_roles) where "ins_roles" is an 
	 * array of BbPortalRole "Role IDs" NOT BbPortalRole IDs - as are used in
	 * this class. Deleting a role is done by first calling BbUser's 
	 * getInsRoles() method, removing role(s) from the resulting "ins_roles"
	 * array, then using BbUser's setInsRoles(String[] ins_roles) method to
	 * save the updated array of portal/institution roles.When deleting 
	 * UserRoles, be sure to understand the rules governing primary and 
	 * secondary roles. 
	 * 
	 * @return Returns null.
	 */
	public boolean delete() {
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

	/**
	 * Removes users with the given institution/portal role.
	 * 
	 * @param ins_role_id - the institution/portal role that will determine
	 *                      which users to delete.
	 * @return Returns an array of user IDs that were successfully removed. A 
	 *         null value indicates no-operation since no users qualified to 
	 *         be removed based on the given values.
	 */
	public String[] deleteUserByInstitutionRole(String[] ins_role_ids){
		return super.deleteUsersByInstitutionRole(ins_role_ids);
	}
	
	/**
	 * Returns the secondary portal/institution roles for a given user
	 * @param userId
	 * @return
	 */
	public BbUserRole[] getSecondaryInsRolesByUserId(String user_id){
		UserRoleVO[] user_roles = super.getUserInstitutionRoles(new String[]{user_id});
		if(user_roles==null || user_roles.length<=0) return null;
		List<BbUserRole> bb_roles = new ArrayList<BbUserRole>();
		for(UserRoleVO role : user_roles){
			if(role!=null && role.getId()!=null){
				bb_roles.add(new BbUserRole(role));
			}	
		}
		if(bb_roles==null || bb_roles.size()==0) return null;
		return bb_roles.toArray(new BbUserRole[]{});
	}
	
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////	
	/**
	 * @return Returns the expansion data.
	 */
	public String[] getExpansionData(){
		return user_role.getExpansionData();
	}
	
	/**
	 * @return Returns the internal Blackboard ID for this user & 
	 * 	       portal/institution role association.
	 */
	public String getId(){
		return user_role.getId();
	}
	
	/**
	 * @return Returns the internal Blackboard ID for the portal/institution
	 *         role associated with this UserRole.
	 */	
	public String getInsRoleId(){
		return user_role.getInsRoleId();
	} 
	
	/**
	 * @return Returns the internal Blackboard ID for the user associated with
	 *         this UserRole.
	 */		
	public String getUserId(){
		return user_role.getUserId();
	} 
	
    /**
     * Expansion data is currently ignored. In future versions it may be used 
     * to add additional attributes without breaking the wsdl contract.
     * 
     * @param expansion_data - the expansionData to set (For Future Use)
     */
	public void setExpansionData(String[] expansionData){
		user_role.setExpansionData(expansionData);
	}
	
	/**
     * Sets the ID for this user & portal/institution role association.
     * 
     * @param id - the ID to set for this user & portal/institution role
     *             association. This ID should be generated by Blackboard, in 
     *             the form "_nnn_1" where nnn is an integer.
	 */
	public void setId(String id){
		user_role.setId(id);
	}
	
	/**
     * Sets the ID for the portal/institution role associated with this 
     * UserRole.
     * 
     * @param id - the ID to set for this portal/institution role associated 
     *             with this UserRole. This ID should be generated by 
     *             Blackboard, in form "_nnn_1" where nnn is an integer.
	 */
	public void setInsRoleId(String insRoleId){
		user_role.setInsRoleId(insRoleId);
	}
	
	/**
     * Sets the ID for the user associated with this UserRole.
     * 
     * @param id - the ID to set for thes user associated with this UserRole.
     *             This ID should be generated by Blackboard, in form "_nnn_1" 
     *             where nnn is an integer.
	 */
	public void setUserId(String userId){
		user_role.setUserId(userId);
	}
}
