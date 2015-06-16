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

import org.bbsync.webservice.client.generated.UserWSStub.PortalRoleVO;
import org.bbsync.webservice.client.proxytool.UserProxyTool;

/**
 * BbPortalRole represents the portal/institution roles defined the Academic 
 * Suite.
 * 
 * Blackboard's web services use the terms “Portal” & “Institution”
 * interchangeably to describe the “Institution Roles” that are found in the 
 * Admin GUI.  These Institution Roles permit content and services to be 
 * delivered to subsets of users. 
 * 
 * These roles are different than Course Membership roles.  Course membership
 * roles only apply to a particular course.  For example, a person defined as a
 * Teaching Assistant in a course may have the similar access to the course as 
 * the Course Instructor.  However, on the Institution level, that person would 
 * be identified as a student, not an instructor.  Likewise, a person defined 
 * as an Instructor at the institution level, may also be a student in a course 
 * or a participant in a group via a Course Membership role.
 * 
 * The default Institution Role assigned to new users is "Student", unless 
 * otherwise specified when the user is created.  Blackboard provides 20 pre-
 * defined Portal/Institution and although additional roles may be created.  
 * Note that none of the pre-defined roles may be deleted.
 * 
 * NOTE:  There is currently no way to save or update portal/institution roles 
 * via the web services API.  This class only functions to lookup pre-existing
 * portal/institution roles or custom roles that have been created in the 
 * Admin GUI.  Pay close attention to the comments associated with the get/set 
 * methods for this class for proper use.
 */
public class BbPortalRole extends UserProxyTool {
	private static final long serialVersionUID = 4444000000003333L;
	private PortalRoleVO portal_role = null;
	
	//Default institution roles provided by the UserVO API.  Additional 
	//(custom) Institution roles are also available.  The String values 
	//represented here are the "Role IDs" defined in Blackboard's Admin GUI.
	//These "Role IDs" can't be altered and none of these default roles can be
	//deleted using the Admin GUI - so the way these roles are referenced
	//should never change.
	public static final String INSTITUTION_ROLE_PROSPECTIVE_STUDENT = "PROSPECTIVE_STUDENT";
	public static final String INSTITUTION_ROLE_STUDENT  = "STUDENT";
	public static final String INSTITUTION_ROLE_FACULTY  = "FACULTY";
	public static final String INSTITUTION_ROLE_STAFF    = "STAFF";
	public static final String INSTITUTION_ROLE_ALUMNI   = "ALUMNI";
	public static final String INSTITUTION_ROLE_GUEST    = "GUEST";
	public static final String INSTITUTION_ROLE_OTHER    = "OTHER";
	public static final String INSTITUTION_ROLE_OBSERVER = "OBSERVER";
	public static final String INSTITUTION_ROLE_UNKNOWN  = "UNKNOWN";
	public static final String INSTITUTION_ROLE_9        = "ROLE_9";
	public static final String INSTITUTION_ROLE_10       = "ROLE_10";
	public static final String INSTITUTION_ROLE_11       = "ROLE_11";
	public static final String INSTITUTION_ROLE_12       = "ROLE_12";
	public static final String INSTITUTION_ROLE_13       = "ROLE_13";
	public static final String INSTITUTION_ROLE_14       = "ROLE_14";
	public static final String INSTITUTION_ROLE_15       = "ROLE_15";
	public static final String INSTITUTION_ROLE_16       = "ROLE_16";
	public static final String INSTITUTION_ROLE_17       = "ROLE_17";
	public static final String INSTITUTION_ROLE_18       = "ROLE_18";
	public static final String INSTITUTION_ROLE_19       = "ROLE_19";
	public static final String INSTITUTION_ROLE_20       = "ROLE_20";
	
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
	public BbPortalRole() {
        portal_role = new PortalRoleVO();
    }
	
	public BbPortalRole(PortalRoleVO portal_role_vo) {
        portal_role = portal_role_vo;
    }
	
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////	
	/**
	 * Currently not implemented in Blackboard's web services API.  Adding or 
	 * updating portal/institution roles must be done through the Admin GUI.
	 * 
	 * @return - Always returns null;
	 */
	public Serializable persist() {
		return null;
	}
	
	/**
	 * Returns the portal/institution role specified by the "Role ID" field. 
	 * You must setRoleId(String role_id) prior to calling this method.
	 *  
	 * @return Returns the BbPortalRole object representing the 
	 *         portal/institution role specified by setting the "Role ID"
	 *         using the setRoleID(String role_id) method. 
	 */
	public Object retrieve() {
		BbPortalRole[] roles =  getInstitutionRolesByRoleId(new String[]{getRoleId()});
		if(roles!=null && roles.length==1) return roles[0];
		return null;
	}
	
	/**
	 * Currently not implemented in Blackboard's web services API.  Deleting 
	 * portal/institution roles from the Blackboard server must be done through
	 * the Admin GUI.
	 * 
	 * @return - Always returns false.
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
	 * @return Returns a BbPortalRole array representing all of the 
	 *         portal/institution roles defined in Blackboard.
	 */
	public BbPortalRole[] getAllInstitutionRoles(){
		PortalRoleVO[] portal_roles = super.getInstitutionRoles(null);
		List<BbPortalRole> bb_roles = new ArrayList<BbPortalRole>();
		for(PortalRoleVO role : portal_roles){
			if(role!=null) bb_roles.add(new BbPortalRole(role));
		}
		if(bb_roles==null || bb_roles.size()==0) return null;
		return bb_roles.toArray(new BbPortalRole[]{});
	}
	
	/**
	 * For a given array of portal/institution role IDs, returns the array of
	 * matching BbPortalRoles defined on the Blackboard server.
	 * 
	 * @param role_ids - refers to the "Role ID" as defined in the Admin GUI.
	 * 					 Note that the "Role ID" defined in the GUI is accessed
	 * 					 via the getRoleName() method in the web services API.
	 *   
	 * @return Returns an array of BbPortalRoles that match the provided 
	 *         role_ids.
	 */	
	public BbPortalRole[] getInstitutionRolesByRoleId(String[] role_ids){
		PortalRoleVO[] portal_roles = super.getInstitutionRoles(null);
		if(portal_roles==null || portal_roles.length<=0) return null;
		List<BbPortalRole> bb_roles = new ArrayList<BbPortalRole>();
		for(PortalRoleVO role : portal_roles){
			if(role!=null && role.getRoleName()!=null){
				for(String id : role_ids){
					if(id!=null && id.equals(role.getRoleName())){
						bb_roles.add(new BbPortalRole(role));
					}
				}
			}	
		}
		if(bb_roles==null || bb_roles.size()==0) return null;
		return bb_roles.toArray(new BbPortalRole[]{});			
	}
	
	///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////	
	/**
	 * @return Returns the Data Source ID [i.e id of the DataSourceVO] for this 
	 *         PortalRoleVO. NOTE:  there is currently no way to set this value 
	 *         via the Admin GUI or the web services API.
	 */
	public String getDataSourceId(){
		return portal_role.getDataSourceId();
	}
	
	/**
	 * @return Returns the portal/institution role description as defined in 
	 *         the Admin GUI.
	 */
	public String getDescription(){
		return portal_role.getDescription();
	}
	
	/**
	 * @return Returns the expansion data.
	 */
	public String[] getExpansionData(){
		return portal_role.getExpansionData();
	}
	
	/**
	 * @return Returns the internal Blackboard ID for this portal/institution
	 *         role.  NOTE:  This is not the "Role ID" represented in the
	 *         Admin GUI.
	 */
	public String getId(){
		return portal_role.getId();
	}
	
	/**
	 * @return Returns the "Role ID" as represented in the Admin GUI.
	 *         NOTE: There is currently no way to retrieve the "Role Name" as
	 *         represented in the Admin GUI via web services. 
	 */
	public String getRoleId(){
		return portal_role.getRoleName();
	}
	
	/**
	 * Sets the ID of the Data Source for this PortalRole.
	 * NOTE:  there is currently no way to save a portal/institution role via
	 * Blackboard's web services API nor is there a way to set this value via
	 * the Admin GUI.
	 *   
	 * @param data_source_id - this is not used.
	 */
	public void setDataSourceId(String data_source_id){
		portal_role.setDataSourceId(data_source_id);
	}
	
	/**
	 * Sets the description for this PortalRole.
	 * NOTE:  there is currently no way to save a portal/institution role via
	 * Blackboard's web services API.
	 * 
	 * @param description
	 */
	public void setDescription(String description){
		portal_role.setDescription(description);
	}
	
    /**
     * Expansion data is currently ignored. In future versions it may be used 
     * to add additional attributes without breaking the wsdl contract.
     * 
     * @param expansion_data - the expansionData to set (For Future Use)
     */
	public void setExpansionData(String[] expansion_data){
		portal_role.setExpansionData(expansion_data);
	}
	
	/**
     * Sets the ID for this portal/institution role.  Note:  This is not the
     * "Role ID" as represented in the Admin GUI.
     * 
     * @param id - the ID to set for this portal/institution role. This ID 
     *             should be generated by Blackboard, in the form "_nnn_1" 
     *             where nnn is an integer.
	 */
	public void setId(String id){
		portal_role.setId(id);
	}
	
	/**
	 * Note: this does NOT set the "Role Name" as represented in the Admin GUI. 
	 * Rather, this sets the "Role ID" for this portal/institution role.
	 *    
	 * @param role_id - use the static fields associate with this class or the 
	 *                  portal/institution "Role ID" as represented in the 
	 *                  Admin GUI.
	 */
	public void setRoleId(String role_id){
		portal_role.setRoleName(role_id);
	}
}
