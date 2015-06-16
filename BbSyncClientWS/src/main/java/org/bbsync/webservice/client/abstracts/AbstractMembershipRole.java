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

package org.bbsync.webservice.client.abstracts;

import java.io.Serializable;

import org.bbsync.webservice.client.generated.CourseMembershipWSStub.CourseMembershipRoleVO;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.VersionVO;
import org.bbsync.webservice.client.proxytool.CourseMembershipProxyTool;

/**
 * @author kurt
 * The UserRoleVO class associates the user data object with 
 * Academic Suite portal roles.
 */
public abstract class AbstractMembershipRole extends CourseMembershipProxyTool {
	private static final long serialVersionUID = 5555000000002222L;
	protected CourseMembershipRoleVO _mrvo = null;

	///////////////////////////////////////////////////////////////////////////
	//  Required ClientWebService Methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Currently not implemented.  Adding course/organization roles to the 
	 * Blackboard server must be done through the Admin GUI.
	 * 
	 * @return null;
	 */
	public Serializable persist() {
		return null;
	}
		
	/**
	 * Currently not implemented.  Deleting course/organization roles from the
	 * Blackboard server must be done through the Admin GUI.
	 * 
	 * @return false;
	 */
	public boolean delete() {
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////
	//  Implemented ProxyTool Methods  ////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
    /**
     * @return Returns the current version (Long) of this web service on
     *         the server.
     */
    public Long getServerVersion(){
    	VersionVO version = null;
    	version = super.getServerVersion(version);
    	return version.getVersion();
    }
    	
	/**
	 * Sets the client version to version 1 and returns an appropriate session.
	 * 
	 * @return Returns true to indicate that the session has been initialized 
     *         for the CourseMembership web service.
	 */
	public boolean	initializeCourseMembershipWS(){
		return super.initializeCourseMembershipWS(true);
	}
	
	///////////////////////////////////////////////////////////////////////////
	//  Local Methods  ////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * @return Returns the identifier for the role.
	 */
	public String getRoleIdentifier(){
		return _mrvo.getRoleIdentifier();
	}

	/**
	 * @param roleIdentifier - the identifier for the role.
	 */
	public void setRoleIdentifier(String role_identifier){
		_mrvo.setRoleIdentifier(role_identifier);
	}

	/**
	 * @return Returns the course-description for the role.
	 */
	public String getCourseRoleDescription(){
		return _mrvo.getCourseRoleDescription();
	}

	/**
	 * @param courseRoleDescription - the course-description for the role.
	 */
	public void setCourseRoleDescription(String courseRoleDescription){
		_mrvo.setCourseRoleDescription(courseRoleDescription);
	}

	/**
	 * @return Returns the org-description for the role.
	 */
	public String getOrgRoleDescription(){
		return _mrvo.getOrgRoleDescription();
	}

	/**
	 * @param orgRoleDescription - Sets the org-description for the role.
	 */
	public void setOrgRoleDescription(String orgRoleDescription){
		_mrvo.setOrgRoleDescription(orgRoleDescription);
	}

	/**
	 * @return - Returns true if the role is default; false if user created.
	 */
	public boolean getDefaultRole(){
		return _mrvo.getDefaultRole();
	}

	/**
	 * @param defaultRole - Set to true if the role is default; set to false if user created role.
	 */
	public void setDefaultRole(boolean defaultRole){
		_mrvo.setDefaultRole(defaultRole);
	}

	/**
	 * Expansion data is currently ignored. In future versions it may be used to 
	 * add additional attributes without breaking the wsdl contract.
	 * @return Returns the expansionData.
	 */
	public String[] getExpansionData(){
		return _mrvo.getExpansionData();
	}

	/**
	 * Expansion data is currently ignored. In future versions it may be used to 
	 * add additional attributes without breaking the wsdl contract.
	 * @param expansionData - the expansionData to set (For Future Use)
	 */
	public void setExpansionData(String[] expansionData){
		_mrvo.setExpansionData(expansionData);
	}
}
