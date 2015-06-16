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

package org.bbsync.webservice.client.course;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.bbsync.webservice.client.generated.CourseWSStub.GroupFilter;
import org.bbsync.webservice.client.generated.CourseWSStub.GroupVO;
import org.bbsync.webservice.client.generated.CourseWSStub.VersionVO;
import org.bbsync.webservice.client.proxytool.CourseProxyTool;


public class BbGroup extends CourseProxyTool {
    private static final long serialVersionUID = 3333000000008888L;
    private GroupVO _group_vo = null;
    private String _course_id = null;
    public static final String	GROUP_TOOL_BLOGS = "blogs";
    public static final String	GROUP_TOOL_COLLABORATION = "collaboration";
    public static final String	GROUP_TOOL_DISCUSSION_BOARD = "discussion_board";
    public static final String	GROUP_TOOL_EMAIL = "course_email";
    public static final String	GROUP_TOOL_FILE_EXCHANGE = "file_exchange";
    public static final String	GROUP_TOOL_JOURNALS = "journal";
    public static final String	GROUP_TOOL_TASKS = "tasks";
    public static final String	GROUP_TOOL_WIKIS = "Bb-wiki";
        
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public BbGroup() {
    	_group_vo = new GroupVO();
    }

    private BbGroup(GroupVO group_vo) {
    	_group_vo = group_vo;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////
    public Serializable persist() {
    	return super.saveGroup(getCourseId(), _group_vo);
    }
   
    public Object retrieve() {
    	return getGroupById(_course_id, getId());
    }
   
    public boolean delete() {
    	String[] result = super.deleteGroup(getCourseId(), new String[]{getId()});
    	if(result==null || result.length==0) return false;
    	if(result[0]==null || result[0].equals("")) return false;
    	return true;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Implemented ProxyTool Methods  ////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public boolean initializeCourseWS() {
        return super.initializeCourseWS(true);
    }
    
    public Long getServerVersion() {
        return super.getServerVersion(new VersionVO()).getVersion();
    }
    
    public String[] deleteGroup(String course_id, String[] group_ids){
    	return super.deleteGroup(course_id, group_ids);
    }
    
    public BbGroup getGroupById(String course_id, String group_id){
    	GroupFilter filter = new GroupFilter();
    	//From blackboard.ws.course.CourseWSConstants:
    	//public static final int GET_GROUP_BY_ID = 1;
    	filter.setFilterType(1);
    	filter.setGroupIds(new String[]{group_id});
    	BbGroup[] groups = convert_GroupVOArray_to_BbGroupArray(super.getGroup(course_id, filter));
    	if(groups==null || groups.length==0) return null;
    	return groups[0];
    }
    
    public BbGroup[] getGroupsByCourseId(String course_id){
    	GroupFilter filter = new GroupFilter();
    	//From blackboard.ws.course.CourseWSConstants:
    	//public static final int GET_GROUP_BY_COURSE_ID = 2;
    	filter.setFilterType(2);
    	return convert_GroupVOArray_to_BbGroupArray(super.getGroup(course_id, filter));
    }
    
    public BbGroup[] getGroupsByEnrolledUserId(String course_id, String user_id){
    	GroupFilter filter = new GroupFilter();
    	//From blackboard.ws.course.CourseWSConstants:
    	//public static final int GET_ENROLLED_GROUP_BY_USER_ID = 3;
    	filter.setFilterType(3);
    	filter.setUserIds(new String[]{user_id});
    	return convert_GroupVOArray_to_BbGroupArray(super.getGroup(course_id, filter));
    }
    
    public String saveGroup(String course_id, BbGroup bb_group) {
    	return super.saveGroup(course_id, bb_group.getVO());
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * @return Returns the ID of the Course that contains this Group.
     */
    public String getCourseId(){
    	if(_group_vo.getCourseId()!=null) return _group_vo.getCourseId();
    	return _course_id;
    }

    /**
     * Sets the Course ID for this Group.
     * 
     * @param course_id - the ID of the Course that contains this Group
     */
    public void setCourseId(String course_id){
    	_course_id = course_id;
    }

    /**
     * @return Returns the name of this Group.
     */
    public String getName(){
    	return _group_vo.getTitle();
    }

    /**
     * Sets this Group's name.  This is a required field.
     * 
     * @param name - the title of this group
     */
    public void setName(String name){
    	_group_vo.setTitle(name);
    }

    /**
     * @return Returns the description of this Group.
     */
    public String getDescription(){
    	return _group_vo.getDescription();
    }

    /**
     * The description of this Group.  This is a required field.
     * 
     * @param description sets this group's description
     */
    public void setDescription(String description){
    	_group_vo.setDescription(description);
    }

    /**
     * @return Returns true if the group is visible to students, else false.
     */
    public boolean isVisibleToStudents(){
    	return _group_vo.getAvailable();
    }

    /**
     * Sets the visibility of this Group to students
     * 
     * @param visible - visibility of this Group to students
     */
    public void setVisibleToStudents(boolean visible){
    	_group_vo.setAvailable(visible);
    }

    /**
     * @return Returns this Group's ID 
     */
    public String getId(){
    	return _group_vo.getId();
    }

    /**
     * Sets the ID for this Group
     * 
     * @param id - the ID to set for this Group. This ID should be generated by
     *             Blackboard, in the form "_nnn_1" where nnn is an integer.
     */
    public void setId(String id){
    	_group_vo.setId(id);
    }

    /**
     * @return Returns the group tools available to this Group
     */
    public String[] getGroupTools(){
    	return _group_vo.getGroupTools();
    }

    /**
     * Set group tools available for this Group.
     * 
     * @param group_tools - the group tools to set.  To set group tools as
     *                      available to this Group, use the following:
     *                      GROUP_TOOL_BLOGS
     *                      GROUP_TOOL_COLLABORATION
     *                      GROUP_TOOL_DISCUSSION_BOARD
     *                      GROUP_TOOL_EMAIL
     *                      GROUP_TOOL_FILE_EXCHANGE
     *                      GROUP_TOOL_JOURNALS
     *                      GROUP_TOOL_TASKS
     *                      GROUP_TOOL_WIKIS
     */
    public void setGroupTools(String[] group_tools){
    	_group_vo.setGroupTools(group_tools);
    }

    /**
     * @return  Returns the expansion data.
     */
    public String[] getExpansionData(){
    	return _group_vo.getExpansionData();
    }

    /**
     * Expansion data is currently ignored. In future versions it may be used 
     * to add additional attributes without breaking the wsdl contract.
     * 
     * @param expansion_data - the expansionData to set (For Future Use)
     */
    public void setExpansionData(String[] expansion_data){
    	_group_vo.setExpansionData(expansion_data);
    }
 
	/**
     * @return  Returns the text type representing the following fields: 
     * 			TEXT_TYPE_HTML       = 'HTML'
     * 		    TEXT_TYPE_PLAIN_TEXT = 'PLAIN_TEXT' 
     *          TEXT_TYPE_SMART_TEXT = 'SMART_TEXT'
     */
    public String getDescriptionType(){
    	return _group_vo.getDescriptionType();
    }

	/**  
	 * The description_type changes the way the description field is 
	 * interpreted.  If not specified or an invalid value is specified,
	 * SMART_TEXT is assumed.
	 * 
     * @param description_type - Possible values are: 
     * 						     TEXT_TYPE_HTML
     * 						     TEXT_TYPE_PLAIN_TEXT 
     *                           TEXT_TYPE_SMART_TEXT
	 */
    public void setDescriptionType(String description_type){
    	_group_vo.setDescriptionType(description_type);
    }
    
    private GroupVO getVO(){
    	return _group_vo;
    }
    
	private BbGroup[] convert_GroupVOArray_to_BbGroupArray(GroupVO[] group_vos){
    	if(group_vos==null || group_vos.length<1) return null;
		List<BbGroup> bb_groups = new ArrayList<BbGroup>();
		for(GroupVO group : group_vos){
			if(group!=null) bb_groups.add(new BbGroup(group));
		}
		return bb_groups.toArray(new BbGroup[]{});
    } 

}
