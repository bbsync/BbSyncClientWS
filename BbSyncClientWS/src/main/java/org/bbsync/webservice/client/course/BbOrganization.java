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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bbsync.webservice.client.abstracts.AbstractCourse;
import org.bbsync.webservice.client.generated.CourseWSStub.CourseFilter;
import org.bbsync.webservice.client.generated.CourseWSStub.CourseVO;


public class BbOrganization extends AbstractCourse {
    private static final long serialVersionUID = 3333000000007777L;
    
    //Search Keys
    public static final String SEARCH_KEY_ORGANIZATION_ID = "CourseId";
	public static final String SEARCH_KEY_ORGANIZATION_NAME = "CourseName";
	public static final String SEARCH_KEY_ORGANIZATION_DESCRIPTION = "CourseDescription";
	public static final String SEARCH_KEY_ORGANIZATION_LEADER_USERNAME = "CourseInstructor";
    
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public BbOrganization() {
        _course_vo = new CourseVO();
        setOrganization(true);
        setServiceLevel(SERVICE_LEVEL_ORG);
    }

    private BbOrganization(CourseVO course_vo) {
        _course_vo = course_vo;
        setOrganization(true);
        setServiceLevel(SERVICE_LEVEL_ORG);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////
    /**
     * Saves or Updates this Organization.
     * @return Returns the ID of the Organization if successfully persisted to 
     *         the Academic Suite.  Otherwise returns null.
     */
    @Override
    public String persist(){
    	if(getId()!=null) return super.createOrg(getVO());
    	return super.saveCourse(getVO());
    }
    
    public Object retrieve() {
    	if (this.getId() != null) {
            return getOrganizationById(this.getId());
        } 
    	else if (this.getCourseId() != null) {
            return getOrganizationByOrgId(this.getCourseId());
        } 
    	else if (this.getBatchUid() != null) {
            return getOrganizationByBatchUid(this.getBatchUid());
        }
    	return null;
    }

    /** 
     * @return true - if the PK1 id of the course matches the PK1 id deleted course.
     */
    public boolean delete() { 
        if(_course_vo.getId()==null) return false;
		String[] result = super.deleteOrg(new String[]{_course_vo.getId()});
		if(result==null || result[0]==null) return false;
		if(_course_vo.getId().equals(result[0])) return true;
		return false;
    }
    
	///////////////////////////////////////////////////////////////////////////
	//  Implemented ProxyTool Methods  ////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
    
    //TODO: Will this work??? - Test!
    public boolean addOrgToTerm(String org_id, String term_id){
    	return super.addCourseToTerm(org_id, term_id);
    }
        
    public boolean initializeCourseWS() {
        boolean result = initializeCourseWS(true);
        return result;
    }
    
    public boolean changeOrgBatchUid(String old_batch_uid, String new_batch_uid){
    	return super.changeOrgBatchUid(old_batch_uid, new_batch_uid);
    }
    
    public boolean changeOrgDataSourceId(String org_id, String new_data_source_id){
    	return changeOrgDataSourceId(org_id, new_data_source_id);
    }
       
    public String createOrg(BbOrganization bb_org){
    	return createOrg(bb_org.getVO());
    }
        
    public String[] deleteOrg(String[] org_ids){
    	return super.deleteOrg(org_ids);
    }
    
    /**
     * Returns the Group tools available to this course.  This Course must have 
     * a Course ID set (PK1 ID value).
     * @return Returns an array of Group tool names.
     */
    
   	
   	//filter value 0
   	public BbOrganization[] getAllOrganizations() {
        CourseFilter courseFilter = new CourseFilter();
        courseFilter.setFilterType(GET_ALL_COURSES);
        return convert_CourseVOArray_to_BbOrgArray(super.getOrg(courseFilter));
    }
   	
   	//filter value 1
    public BbOrganization getOrganizationByOrgId(String org_id) {
        CourseFilter courseFilter = new CourseFilter();
        courseFilter.setFilterType(GET_COURSE_BY_COURSEID);
        courseFilter.setCourseIds(new String[] { org_id });
        BbOrganization[] result = convert_CourseVOArray_to_BbOrgArray(super.getOrg(courseFilter));
        if(result!=null && result.length==1) return result[0];
        return null;
    }
    
    //filter value 2
    public BbOrganization getOrganizationByBatchUid(String batch_uid) {
        CourseFilter courseFilter = new CourseFilter();
        courseFilter.setFilterType(GET_COURSE_BY_BATCHUID);
        courseFilter.setBatchUids(new String[] { batch_uid });
        BbOrganization[] result = convert_CourseVOArray_to_BbOrgArray(super.getOrg(courseFilter));
        if(result!=null && result.length==1) return result[0];
        return null;
    }
    
    //filter value 3
	public BbOrganization getOrganizationById(String id) {
        CourseFilter courseFilter = new CourseFilter();
        courseFilter.setFilterType(GET_COURSE_BY_ID);
        courseFilter.setIds(new String[] { id });
        BbOrganization[] result = convert_CourseVOArray_to_BbOrgArray(super.getOrg(courseFilter));
        if(result!=null && result.length==1) return result[0];
        return null;
    }

   	//filter value 4
   	public BbOrganization[] getOrganizationByCategoryId(String[] category_ids) {
        CourseFilter courseFilter = new CourseFilter();
        courseFilter.setFilterType(GET_COURSES_BY_CATEGORY_ID);
        courseFilter.setCategoryIds(category_ids);
        return convert_CourseVOArray_to_BbOrgArray(super.getOrg(courseFilter));
    }
   	
   	/**
   	 * This class will return courses according to a supplied search value, and
   	 * one of several pre-configured search keys: 
   	 *  - SEARCH_KEY_ORGANIZATION_ID
   	 *  - SEARCH_KEY_ORGANIZATION_NAME
   	 * 	- SEARCH_KEY_ORGANIZATION_DESCRIPTION
   	 *  - SEARCH_KEY_ORGANIZATION_LEADER_USERNAME.  
   	 * This method uses "contains" as its search operator, so it will search 
   	 * for all or part of the supplied search value.
   	 * @param search_value
   	 * @param search_key
   	 * @return Returns BbCourses that match the provides search criteria. 
   	 */
   	public BbOrganization[] getOrgsBySearchValue(String search_value, String search_key){
   		return getOrgsBySearchValue(search_value, search_key, SEARCH_OPERATOR_CONTAINS, null, null);
   	}
   	
   	/**
   	 * This class will return courses according to a supplied search value, one
   	 * of several pre-configured search keys, and a search operator.  The 
   	 * search keys are: 
   	 *  - SEARCH_KEY_ORGANIZATION_ID
   	 *  - SEARCH_KEY_ORGANIZATION_NAME
   	 * 	- SEARCH_KEY_ORGANIZATION_DESCRIPTION
   	 *  - SEARCH_KEY_ORGANIZATION_LEADER_USERNAME.  
   	 * The search operators are:
   	 *  - SEARCH_OPERATOR_EQUALS
   	 *  - SEARCH_OPERATOR_CONTAINS
   	 *  - SEARCH_OPERATOR_STARTS_WITH
   	 *  - SEARCH_OPERATOR_IS_NOT_BLANK
   	 * @param search_value
   	 * @param search_key
   	 * @param search_operator
   	 * @return Returns BbCourses that match the provides search criteria.
   	 */
   	public BbOrganization[] getOrgsBySearchValue(String search_value, String search_key, String search_operator){
   		return getOrgsBySearchValue(search_value, search_key, search_operator, null, null);
   	}
   	
    private BbOrganization[] getOrgsBySearchValue(String search_value, String search_key, String search_operator, Date search_date, String search_date_operator){
		CourseFilter courseFilter = new CourseFilter();
		courseFilter.setFilterType(GET_COURSES_BY_SEARCH);
		courseFilter.setSearchKey(search_key);
		courseFilter.setSearchOperator(search_operator);
		courseFilter.setSearchValue(search_value);
		//TODO: for now, we're using the following defaults for the date ranges
		courseFilter.setSearchDate(new Long(0));
		courseFilter.setSearchDateOperator("GreaterThan");
		return convert_CourseVOArray_to_BbOrgArray(super.getOrg(courseFilter));
	}
    
    /**
     * Saves the given Organization (creates or updates the Organization).
     * 
     * @param bb_org - the Organization to save
     * @return Returns the ID of the Organization if saved successfully. 
     *         Otherwise, returns null.
     */
    public String saveOrg(BbOrganization bb_org){
    	return super.saveCourse(bb_org.getVO());
    }
    
    /**
     * Update an Organization.
     * 
     * @param bb_org - the Organization to update.
     * @return - Returns the id of the updated Organization.
     */
    public String updateOrganization(BbOrganization bb_org){
    	return super.updateOrg(bb_org.getVO());
    }

    ///////////////////////////////////////////////////////////////////////////
    //  Implemented Abstract Methods  /////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////   	   	
    /**
     * Sets the Organization ID (not the Blackboard generated ID) associated with 
     * this Organization.
     * 
     * @param org_id - the ID to set for this Organization
     */
    public void setOrganizationId(String org_id) {
        super.setCourseId(org_id);
    }
    
    public String getOrganizationId(){
    	return super.getCourseId();
    }
        
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    private BbOrganization[] convert_CourseVOArray_to_BbOrgArray(CourseVO[] course_vos){
    	if(course_vos==null || course_vos.length==0) return null;
		List<BbOrganization> bb_courses = new ArrayList<BbOrganization>();
		for(CourseVO course : course_vos){
			if(course!=null) bb_courses.add(new BbOrganization(course));
		}
		return bb_courses.toArray(new BbOrganization[]{});
    } 
    
    private CourseVO getVO(){
    	return _course_vo;
    }
}
