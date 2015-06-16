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

import org.bbsync.webservice.client.generated.CourseWSStub.TermVO;
import org.bbsync.webservice.client.generated.CourseWSStub.VersionVO;
import org.bbsync.webservice.client.proxytool.CourseProxyTool;


public class BbTerm extends CourseProxyTool {
    private static final long serialVersionUID = 3333000010101010L;
    private TermVO _term_vo = null;
    
    public static final String DURATION_CONTINUOUS = "C";
    public static final String DURATION_DATE_RANGE = "R";
    public static final String DURATION_NUMBER_OF_DAYS = "D";
    
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public BbTerm() {
    	_term_vo = new TermVO();
    }

    public BbTerm(TermVO term_vo) {
    	_term_vo = term_vo;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////
    public Serializable persist() {
    	return super.saveTerm(_term_vo);
    }
   
    public Object retrieve() {
    	TermVO term = super.loadTerm(this.getId());
    	if(term==null) return null;
    	return new BbTerm(term);
    }
    
    public boolean delete() {
        if(_term_vo.getId()==null) return false;
		return super.deleteTerm(_term_vo.getId());
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
    
    public boolean addCourseToTerm(String course_id, String term_id){
    	return super.addCourseToTerm(course_id, term_id);
    }
    
    /**
     * Associate a course to this Term. 
     * @param courseId - Id of the Course to add to this Term. The course id 
     *                   should be in the form "_nnn_1" where nnn is an integer
     * @return Returns true if the course is added to the term successfully,
     *         false otherwise.
     */
    public boolean addCourseToTerm(String course_id){
    	return super.addCourseToTerm(course_id, this.getId());
    }
            
    /**
     * Returns all Courses for associated with the specified Term.
     * @param term_id - ID of the Term to load the list of associated Course for
     * @return Returns an array of Courses associated with the term identified 
     *         by term_id.
     */
    public BbCourse[] getAllCoursesInTerm(String term_id){
    	return convert_CourseVOArray_to_BbCourseArray(super.loadCoursesInTerm(term_id));
    }
    
    /**
     * Returns all Courses for associated with this Term.  Term ID must be set.
     * @return Returns an array of Courses associated with this Term.
     */
    public BbCourse[] getAllCoursesInTerm(){
    	return convert_CourseVOArray_to_BbCourseArray(super.loadCoursesInTerm(this.getId()));
    }
    
    public BbTerm getTermByCourseId(String course_id){
    	TermVO term = super.loadTermByCourseId(course_id);
    	if(term==null) return null;
    	return new BbTerm(term);
    }
    
    public BbTerm[] getAllTerms(boolean only_available) {
    	return convert_TermVOArray_to_BbTermArray(super.loadTerms(only_available));
    }
    
    public BbTerm[] getTermsByName(String name){
    	return convert_TermVOArray_to_BbTermArray(super.loadTermsByName(name));
    }
    
    public boolean removeCourseFromTerm(String course_id){
    	return super.removeCourseFromTerm(course_id);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * @return Returns the ID of this Term.
     */
    public String getId(){
    	return _term_vo.getId();
    }

    /**
     * @param id - ID for this Term - only set this when updating existing Terms.
     */
    public void setId(String id){
    	_term_vo.setId(id);
    }

    /**
     * @return Returns the name of this term.
     */
    public String getName(){
    	return _term_vo.getName();
    }

    /**
     * @param name - name for this term.
     */
    public void setName(String name){
    	_term_vo.setName(name);
    }

    /**
     * @return Returns the description for this Term. See setDescriptionType to
     * interpret contents.
     */
    public String getDescription(){
    	return _term_vo.getDescription();
    }

    /**
     * Set the description for this Term. See setDescriptionType to interpret
     * contents.
     * @param description - the description to set
     */
    public void setDescription(String description){
    	_term_vo.setDescription(description);
    }

    /**
     * @return  Returns the text type representing the following fields: 
     * 			TEXT_TYPE_HTML = 'HTML'
     * 		    TEXT_TYPE_PLAIN_TEXT = 'PLAIN_TEXT' 
     *          TEXT_TYPE_SMART_TEXT = 'SMART_TEXT'
     */
    public String getDescriptionType(){
    	return _term_vo.getDescriptionType();
    }

    /**
     * The descriptionType changes the way the description text is interpreted.
     * If not specified or an invalid value is specified then SMART_TEXT is 
     * assumed.
     * @param description_type -  Possible values are: 
     * 							  TEXT_TYPE_HTML
     * 							  TEXT_TYPE_PLAIN_TEXT 
     *                            TEXT_TYPE_SMART_TEXT
     */
    public void setDescriptionType(String description_type){
    	_term_vo.setDescriptionType(description_type);
    }

    /**
     * @return Returns the sourcedid.source value.
     */
    public String getSourcedidSource(){
    	return _term_vo.getSourcedidSource();
    }

    /**
     * @param sourcedid_source - the sourcedid.source value
     */
    public void setSourcedidSource(String sourcedid_source){
    	_term_vo.setSourcedidSource(sourcedid_source);
    }

    /**
     * @return Returns the sourcedid.id value.
     */
    public String getSourcedidId(){
    	return _term_vo.getSourcedidId();
    }

    /**
     * @param sourcedid_id - the sourcedid.id value
     */
    public void setSourcedidId(String sourcedid_id){
    	_term_vo.setSourcedidId(sourcedid_id);
    }

    /**
     * @return Returns the Data Source ID (See BbDataSource).
     */
    public String getDataSrcId(){
    	return _term_vo.getDataSrcId();
    }

    /**
     * @param data_source_id - Data Source ID (See BbDataSource)
     */
    public void setDataSrcId(String data_source_id){
    	_term_vo.setDataSrcId(data_source_id);
    }

    /**
     * @return Returns values represented by the following fields:
     *         'C' - DURATION_CONTINUOUS
     *         'R' - DURATION_DATE_RANGE - Date Range
     *         'D' - DURATION_NUMBER_OF_DAYS - Fixed number of days
     */
    public String getDuration(){
    	return _term_vo.getDuration();
    }

    /**
     * Set the duration of the Term.  Use the following values to set the
     * appropriate option:
     *     DURATION_CONTINUOUS
     *     DURATION_DATE_RANGE - Date Range (use setStartDate/setEndDate)
     *     DURATION_NUMBER_OF_DAYS - Fixed number of days (use setDaysOfUse)
     * @param duration
     */
    public void setDuration(String duration){
    	_term_vo.setDuration(duration);
    }

    /**
     * @return Returns the start date
     *         (when duration = DURATION_DATE_RANGE)
     */
    public long getStartDate(){
    	return super.bbLong2javaLong(_term_vo.getStartDate());
    }

    /**
     * @param start_date - the start date 
     *                     (when duration = DURATION_DATE_RANGE)
     */
    public void setStartDate(long start_date){
    	if (String.valueOf(start_date).length() > 10) {
    		_term_vo.setStartDate(super.javaLong2bbLong(start_date));
        } else {
        	_term_vo.setStartDate(start_date);
        }
    }

    /**
     * @return Returns the end date 
     *         (when duration = DURATION_DATE_RANGE).
     */
    public long getEndDate(){
    	return super.bbLong2javaLong(_term_vo.getEndDate());
    }

    /**
     * @param end_date - the Term end date 
     *                   (when duration = DURATION_DATE_RANGE)
     */
    public void setEndDate(long end_date){
    	if (String.valueOf(end_date).length() > 10) {
    		_term_vo.setEndDate(super.javaLong2bbLong(end_date));
        } else {
        	_term_vo.setEndDate(end_date);
        }
    }

    /**
     * @return Returns number of days for Term 
     *         (duration = DURATION_NUMBER_OF_DAYS).
     */
    public int getDaysOfUse(){
    	return _term_vo.getDaysOfUse();
    }

    /**
     * @param days_of_use - number of days for the Term 
     *                      (when duration = DURATION_NUMBER_OF_DAYS)
     */
    public void setDaysOfUse(int days_of_use){
    	_term_vo.setDaysOfUse(days_of_use);
    }

    /**
     * @return Returns the availability of the Term.
     */
    public boolean isAvailable(){
    	return _term_vo.getAvailable();
    }

    /**
     * @param is_available - true if the Term is available for use; 
     *                       false it is not.
     */
    public void setAvailable(boolean is_available){
    	_term_vo.setAvailable(is_available);
    }

    /**
     * @return Returns the expansion data.
     */
    public String[] getExpansionData(){
    	return _term_vo.getExpansionData();
    }

    /**
     * Expansion data is currently ignored. In future versions it may be used 
     * to add additional attributes without breaking the wsdl contract.
     * @param expansion_data - the expansionData to set (For Future Use).
     */
    public void setExpansionData(String[] expansion_data){
    	_term_vo.setExpansionData(expansion_data);
    }
    
    private BbTerm[] convert_TermVOArray_to_BbTermArray(TermVO[] term_vos){
    	if(term_vos==null || term_vos.length<1) return null;
		List<BbTerm> bb_terms = new ArrayList<BbTerm>();
		for(TermVO term : term_vos){
			if(term!=null) bb_terms.add(new BbTerm(term));
		}
		return bb_terms.toArray(new BbTerm[]{});
    }  
}
