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

package org.bbsync.webservice.client.coursemembership;

import java.util.ArrayList;

import org.bbsync.webservice.client.abstracts.AbstractMembershipRole;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.CourseMembershipRoleVO;

/**
 * @author kurt
 * The UserRoleVO class associates the user data object with 
 * Academic Suite portal roles.
 */
public class BbCourseMembershipRole extends AbstractMembershipRole {
	private static final long serialVersionUID = 5555000000006666L;
	//Default Course Role Identifiers. See System Admin Panel.
	public static final String COURSE_ROLE_BUILDER           = "B";
	public static final String COURSE_ROLE_GRADER            = "G";
	public static final String COURSE_ROLE_INSTRUCTOR        = "P";
	public static final String COURSE_ROLE_STUDENT           = "S";
	public static final String COURSE_ROLE_TEACHING_ASSITANT = "T";
	public static final String COURSE_ROLE_GUEST             = "U";

	///////////////////////////////////////////////////////////////////////////
	//  Constructors  /////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	public BbCourseMembershipRole() {
		_mrvo = new CourseMembershipRoleVO();
    }
	
	private BbCourseMembershipRole(CourseMembershipRoleVO course_membership_role_vo) {
        _mrvo = course_membership_role_vo;
    }
	///////////////////////////////////////////////////////////////////////////
	//  Required ClientWebService Methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	/**
	 * Loads this course/organization role. The following field must be set 
	 * prior to calling retrieve():
	 * 
	 *  - Role Identifier: the unique identifier given to a course/organization
	 *                     membership role.  See the Amin GUI for a list of all
	 *                     roles with their identifiers,  Or call
	 *                     getAllCourseMembershipRoles().            
	 * @return Returns a BbMembershipRole, or null if the retrieve() was not 
     *         successful.
	 */
	public Object retrieve() {
		BbCourseMembershipRole[] bbmr = getMembershipRolesByIds(new String[]{getRoleIdentifier()});
		if(bbmr!=null && bbmr.length==1) return bbmr[0];
		return null;
	}
	///////////////////////////////////////////////////////////////////////////
	//  Implemented ProxyTool Methods  ////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	public BbCourseMembershipRole[] getAllMembershipRoles(){
		BbCourseMembershipRole[] bb_cmr = getMembershipRolesByIds(null);
		if(bb_cmr==null) return null;
		return bb_cmr;
	}
	/*	
	public BbCourseMembershipRole[] getMembershipRolesByIds_old(String[] role_ids){
		CourseRoleFilter filter = null;
		if(role_ids!=null){
			filter = new CourseRoleFilter();
			filter.setRoleIds(role_ids);
		}
		CourseMembershipRoleVO[] role_vos = super.getCourseRoles(filter);
		if(role_vos == null) return null;
		ArrayList<BbCourseMembershipRole> bb_cmr = null;
		bb_cmr = new ArrayList<BbCourseMembershipRole>();
		for(CourseMembershipRoleVO role_vo: role_vos){
			bb_cmr.add(new BbCourseMembershipRole(role_vo));
		}
		return bb_cmr.toArray(new BbCourseMembershipRole[]{});
	}
	*/
	
	public BbCourseMembershipRole[] getMembershipRolesByIds(String[] role_ids){
		CourseMembershipRoleVO[] role_vos = super.getCourseRoles(null);
		if(role_vos == null) return null;
		ArrayList<BbCourseMembershipRole> bb_cmr = new ArrayList<BbCourseMembershipRole>();
		if(role_ids==null){
			for(CourseMembershipRoleVO role_vo:role_vos){
				bb_cmr.add(new BbCourseMembershipRole(role_vo));
			}
		}
		else{
			for(String role_id:role_ids){
				for(CourseMembershipRoleVO role_vo:role_vos){
					if(role_id.equals(role_vo.getRoleIdentifier())){
						bb_cmr.add(new BbCourseMembershipRole(role_vo));
						break;
					}
				}
			}
		}
		if(bb_cmr.size()==0)return null;
		return bb_cmr.toArray(new BbCourseMembershipRole[]{});
	}	
}
