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

import java.util.Date;

import javax.activation.DataHandler;

import org.bbsync.webservice.client.abstracts.AbstractCourse;
import org.bbsync.webservice.client.generated.CourseWSStub.CourseFilter;
import org.bbsync.webservice.client.generated.CourseWSStub.CourseVO;
import org.bbsync.webservice.client.generated.CourseWSStub.TermVO;


public class BbCourse extends AbstractCourse {
    private static final long serialVersionUID = 3333000000002222L;
    
    //Search Keys
    public static final String SEARCH_KEY_COURSE_ID = "CourseId";
	public static final String SEARCH_KEY_COURSE_NAME = "CourseName";
	public static final String SEARCH_KEY_COURSE_DESCRIPTION = "CourseDescription";
	public static final String SEARCH_KEY_COURSE_INSTRUCTOR_USERNAME = "CourseInstructor";
    
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public BbCourse() {
        _course_vo = new CourseVO();
        setOrganization(false);
        setServiceLevel(SERVICE_LEVEL_COURSE);
    }

    public BbCourse(CourseVO course_vo) {
    	_course_vo = course_vo;
    	setOrganization(false);
    	setServiceLevel(SERVICE_LEVEL_COURSE);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////
    /**
     * Deletes this Course.  Must have a Course ID set - For Example:
     * BbCourse bb_course = new BbCourse();
     * bb_course.setId("the_id"); 
     * - OR - bb_course.setCourseId("course_id");
     * - OR - bb_course.setBatchUid("batch_id");
     * bb_course = (BbCourse) bb_course.retrieve();
     * @return Returns a BbCourse object if successful; else returns null.  
     */
    public Object retrieve() {
    	if (this.getId() != null) {
            return getCourseById(this.getId());
        } 
    	else if (this.getCourseId() != null) {
            return getCourseByCourseId(this.getCourseId());
        } 
    	else if (this.getBatchUid() != null) {
            return getCourseByBatchUid(this.getBatchUid());
        }
    	return null;
    }

    /** 
     * Deletes this Course.  Must have the PK1 ID value of this course set.
     * @return Returns true if this Course is successfully deleted; else false.
     */
    public boolean delete() { 
        if(_course_vo.getId()==null) return false;
		String[] result = super.deleteCourse(new String[]{_course_vo.getId()});
		if(result==null || result[0]==null) return false;
		if(_course_vo.getId().equals(result[0])) return true;
		return false;
    }
    
	///////////////////////////////////////////////////////////////////////////
	//  Implemented ProxyTool Methods  ////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

    public boolean addCourseToTerm(String course_id, String term_id){
    	return super.addCourseToTerm(course_id, term_id);
    }
    
    public boolean changeCourseBatchUid(String old_batch_uid, String new_batch_uid){
    	return super.changeCourseBatchUid(old_batch_uid, new_batch_uid);
    }
    
    public boolean changeCourseDataSourceId(String course_id, String new_data_source_id){
    	return super.changeCourseDataSourceId(course_id, new_data_source_id);
    }
    
    public String createCourse(BbCourse bb_course){
    	return createCourse(bb_course.getVO());
    }

            
    public String[] deleteCourse(String[] course_ids){
    	return super.deleteCourse(course_ids);
    }
        
    /**
     * Returns the Group tools available to this course.  This Course must have 
     * a Course ID set (PK1 ID value).
     * @return Returns an array of Group tool names.
     */
    public String[] getAvailableGroupTools(){
    	return getAvailableGroupTools(this.getId());
    }
   	
   	public BbCourse[] getAllCourses() {
        CourseFilter courseFilter = new CourseFilter();
        courseFilter.setFilterType(GET_ALL_COURSES);
        return convert_CourseVOArray_to_BbCourseArray(super.getCourse(courseFilter));
    }
   	
    public BbCourse getCourseByCourseId(String course_id) {
        CourseFilter courseFilter = new CourseFilter();
        courseFilter.setFilterType(GET_COURSE_BY_COURSEID);
        courseFilter.setCourseIds(new String[] { course_id });
        BbCourse[] result = convert_CourseVOArray_to_BbCourseArray(super.getCourse(courseFilter));
        if(result!=null && result.length==1) return result[0];
        return null;
    }
    
    public BbCourse getCourseByBatchUid(String batch_uid) {
        CourseFilter courseFilter = new CourseFilter();
        courseFilter.setFilterType(GET_COURSE_BY_BATCHUID);
        courseFilter.setBatchUids(new String[] { batch_uid });
        BbCourse[] result = convert_CourseVOArray_to_BbCourseArray(super.getCourse(courseFilter));
        if(result!=null && result.length==1) return result[0];
        return null;
    }
    
	public BbCourse getCourseById(String id) {
        CourseFilter courseFilter = new CourseFilter();
        courseFilter.setFilterType(GET_COURSE_BY_ID);
        courseFilter.setIds(new String[] { id });
        BbCourse[] result = convert_CourseVOArray_to_BbCourseArray(super.getCourse(courseFilter));
        if(result!=null && result.length==1) return result[0];
        return null;
    }

   	public BbCourse[] getCourseByCategoryId(String[] category_ids) {
        CourseFilter courseFilter = new CourseFilter();
        courseFilter.setFilterType(GET_COURSES_BY_CATEGORY_ID);
        courseFilter.setCategoryIds(category_ids);
        return convert_CourseVOArray_to_BbCourseArray(super.getCourse(courseFilter));
    }
   	   	
   	/**
   	 * This class will return courses according to a supplied search value, and
   	 * one of several pre-configured search keys: 
   	 *  - SEARCH_KEY_COURSE_ID
   	 *  - SEARCH_KEY_COURSE_NAME
   	 * 	- SEARCH_KEY_COURSE_DESCRIPTION
   	 *  - SEARCH_KEY_COURSE_INSTRUCTOR_USERNAME  
   	 * This method uses "contains" as its search operator, so it will search 
   	 * for all or part of the supplied search value.
   	 * @param search_value
   	 * @param search_key
   	 * @return Returns BbCourses that match the provides search criteria. 
   	 */
   	public BbCourse[] getCoursesBySearchValue(String search_value, String search_key){
   		return getCoursesBySearchValue(search_value, search_key, SEARCH_OPERATOR_CONTAINS, null, null);
   	}
   	
    private BbCourse[] getCoursesBySearchValue(String search_value, String search_key, String search_operator, Date search_date, String search_date_operator){
		CourseFilter courseFilter = new CourseFilter();
		courseFilter.setFilterType(GET_COURSES_BY_SEARCH);
		courseFilter.setSearchKey(search_key);
		courseFilter.setSearchOperator(search_operator);
		courseFilter.setSearchValue(search_value);
		//TODO: for now, we're using the following defaults for the date ranges
		courseFilter.setSearchDate(new Long(0));
		courseFilter.setSearchDateOperator("GreaterThan");
		return convert_CourseVOArray_to_BbCourseArray(super.getCourse(courseFilter));
	}
    
   	/**
   	 * @return Returns the Term associated with this Course. Course ID (PK1 ID)
   	 *         must be set for this method to work.
   	 */
   	public BbTerm getTerm(){
   		TermVO term_vo = super.loadTermByCourseId(this.getId());
   		if(term_vo==null) return null;
   		return new BbTerm(term_vo);
   	}
   	/**
   	 * Removes this Course from the Term that it is associated with. The Course 
   	 *         ID (PK1 ID) must be set for this method to work.
   	 * @return True if successfully removed from Term; false otherwise. 
   	 */   	
    public boolean removeFromTerm(){
    	return super.removeCourseFromTerm(this.getId());
    }
   	//TODO:  AXIS2 DataHandler implementation. 
    public boolean setCourseBannerImage(String course_id, boolean remove_only, String file_name, DataHandler file_contents){
    	return super.setCourseBannerImage(course_id, remove_only, file_name, file_contents);
    }
   	/**
   	 * This class will return courses according to a supplied search value, one
   	 * of several pre-configured search keys, and a search operator.  The 
   	 * search keys are: 
   	 *  - SEARCH_KEY_COURSE_ID
   	 *  - SEARCH_KEY_COURSE_NAME 
   	 *  - SEARCH_KEY_COURSE_DESCRIPTION 
   	 *  - SEARCH_KEY_COURSE_INSTRUCTOR_USERNAME  
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
   	public BbCourse[] getCoursesBySearchValue(String search_value, String search_key, String search_operator){
   		return getCoursesBySearchValue(search_value, search_key, search_operator, null, null);
   	}
   	
    /**
     * Update a Course.
     * 
     * @param bb_course - the Course to update.
     * @return - Returns the ID of the updated Course.
     */
   	public String updateCourse(BbCourse bb_course) {
   		return super.updateCourse(bb_course.getVO());
   	}
   	
    ///////////////////////////////////////////////////////////////////////////
    //  Implemented Abstract Methods  /////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////   	   	
    public void setCourseId(String course_id) {
        super.setCourseId(course_id);
    }
    
    public String getCourseId(){
    	return super.getCourseId();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    private CourseVO getVO(){
    	return _course_vo;
    }
}
