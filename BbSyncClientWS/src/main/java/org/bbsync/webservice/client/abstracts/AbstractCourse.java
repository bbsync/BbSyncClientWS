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

import org.bbsync.webservice.client.generated.CourseWSStub.CourseVO;
import org.bbsync.webservice.client.generated.CourseWSStub.VersionVO;
import org.bbsync.webservice.client.proxytool.CourseProxyTool;


public abstract class AbstractCourse extends CourseProxyTool {
	private static final long serialVersionUID = 3333000000001111L;
	protected CourseVO _course_vo = null;
	
    protected static final int GET_ALL_COURSES            = 0; //see blackboard.ws.course.CourseWSConstants
    protected static final int GET_COURSE_BY_COURSEID     = 1; //see blackboard.ws.course.CourseWSConstants
    protected static final int GET_COURSE_BY_BATCHUID     = 2; //see blackboard.ws.course.CourseWSConstants
    protected static final int GET_COURSE_BY_ID           = 3; //see blackboard.ws.course.CourseWSConstants
    protected static final int GET_COURSES_BY_CATEGORY_ID = 4; //see blackboard.ws.course.CourseWSConstants
    protected static final int GET_COURSES_BY_SEARCH      = 5; //see blackboard.ws.course.CourseWSConstants

	//Search Operators
	public static final String SEARCH_OPERATOR_EQUALS = "Equals";
	public static final String SEARCH_OPERATOR_CONTAINS = "Contains";
	public static final String SEARCH_OPERATOR_STARTS_WITH = "StartsWith";
	public static final String SEARCH_OPERATOR_IS_NOT_BLANK = "IsNotBlank";
	//Search Date Operators
	public static final String SEARCH_DATE_OPERATOR_LESS_THAN = "GreaterThan";
	public static final String SEARCH_DATE_OPERATOR_GREATER_THAN = "LessThan";
	//Button Shape
	public static final String BUTTON_SHAPE_DEFAULT = "Default";
	public static final String BUTTON_SHAPE_RECTANGLE ="Rectangular";
	public static final String BUTTON_SHAPE_RND_CORNERS ="Rounded Corners"; 
	public static final String BUTTON_SHAPE_RND_ENDS ="Rounded Ends";
	//Button Style
	public static final String BUTTON_STYLE_DEFAULT = "Default";
	public static final String BUTTON_STYLE_PATTERN = "Pattern";
	public static final String BUTTON_STYLE_SOLID   = "Solid";
	public static final String BUTTON_STYLE_STRIPED = "Striped";
	//Enrollment Types
	public static final String ENROLL_TYPE_EMAIL = "EmailEnrollment";
	public static final String ENROLL_TYPE_INSTRUCTOR_LED = "InstructorLed";
	public static final String ENROLL_TYPE_SELF = "SelfEnrollment";
	//Navigation Style
	public static final String NAV_STYLE_BUTTON = "Button";
	public static final String NAV_STYLE_TEXT   = "Text";
	//Duration
	public static final String DURATION_CONTINUOUS = "Continuous";
	public static final String DURATION_DATE_RANGE = "DateRange";
	public static final String DURATION_FIXED_NUM_DAYS = "FixedNumDays";
	//Pace
	public static final String PACE_SELF_PACED = "SelfPaced";
	public static final String PACE_INSTRUCTOR_LED = "InstructorLed";
	//Service Level
	public static final String SERVICE_LEVEL_COURSE = "Course";
	public static final String SERVICE_LEVEL_ORG = "Organization";
	
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////
    /**
     * Saves or Updates this Course/Organization.
     * @return Returns the ID of the Course or Organization if successfully
     *         persisted to the AS, otherwise null.
     */
    public Serializable persist() {
        return super.saveCourse(_course_vo);
    }

	///////////////////////////////////////////////////////////////////////////
	//  Implemented ProxyTool Methods  ////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
    /**
     * @return Sets the client version to version 1 and returns "true" to 
     * indicate that the session has been initialized. 
     */
    public boolean initializeCourseWS() {
        boolean result = super.initializeCourseWS(true);
        return result;
    }
    
    /**
     * @return Returns the current version (Long) of this web service on
     *         the server.
     */
    public Long getServerVersion() {
        Long v = super.getServerVersion(new VersionVO()).getVersion();
        return v;
    }
    
    /**
     * Loads all of the available Group tools for this course.  The following 
     * field must be set prior to calling this method:
     * 
     * Course ID: This CourseMembership's Course ID This parameter can be null 
     *            if invoked by a System Administrator. Otherwise, Course ID is
     *            required. The Course ID should be generated by Blackboard, in
     *            the form "_nnn_1" where nnn is an integer.
     * 
     * @return Returns an array of Group tool names.  See BbGroup for more 
     *         information on Groups and Group names.
     */
    public String[] getAvailableGroupTools(){
    	return super.getAvailableGroupTools(this.getCourseId());
    }
   	    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public String getBatchUid() {
        return _course_vo.getBatchUid();
    }

    public String getButtonStyleBbId() {
        return _course_vo.getButtonStyleBbId();
    }

    public String getButtonStyleShape() {
        return _course_vo.getButtonStyleShape();
    }

    public String getButtonStyleType() {
        return _course_vo.getButtonStyleType();
    }

    public String getCartridgeId() {
        return _course_vo.getCartridgeId();
    }

    public String getClassificationId() {
        return _course_vo.getClassificationId();
    }

    public String getDuration() {
        return _course_vo.getCourseDuration();
    }

    protected String getCourseId() {
        return _course_vo.getCourseId();
    }

    public String getPace() {
        return _course_vo.getCoursePace();
    }

    public String getServiceLevel() {
        return _course_vo.getCourseServiceLevel();
    }

    public String getDataSourceId() {
        return _course_vo.getDataSourceId();
    }

    public long getDecAbsoluteLimit() {
        return _course_vo.getDecAbsoluteLimit();
    }

    public String getDescription() {
        return _course_vo.getDescription();
    }

    public long getEndDate() {
    	return super.bbLong2javaLong(_course_vo.getEndDate());
    }

    public String getEnrollmentAccessCode() {
        return _course_vo.getEnrollmentAccessCode();
    }

    public long getEnrollmentEndDate() {
    	return super.bbLong2javaLong(_course_vo.getEnrollmentEndDate());
    }

    public long getEnrollmentStartDate() {
    	return super.bbLong2javaLong(_course_vo.getEnrollmentStartDate());
    }

    public String getEnrollmentType() {
        return _course_vo.getEnrollmentType();
    }

    public String[] getExpansionData() {
        return _course_vo.getExpansionData();
    }

    public float getFee() {
        return _course_vo.getFee();
    }

    public String getId() {
        return _course_vo.getId();
    }

    public String getInstitutionName() {
        return _course_vo.getInstitutionName();
    }

    public String getLocale() {
        return _course_vo.getLocale();
    }

    public String getName() {
        return _course_vo.getName();
    }

    public String getNavColorBg() {
        return _course_vo.getNavColorBg();
    }

    public String getNavColorFg() {
        return _course_vo.getNavColorFg();
    }

    public String getNavigationStyle() {
        return _course_vo.getNavigationStyle();
    }

    public int getNumberOfDaysOfUse() {
        return _course_vo.getNumberOfDaysOfUse();
    }

    public long getSoftLimit() {
        return _course_vo.getSoftLimit();
    }

    public long getStartDate() {
        return super.bbLong2javaLong(_course_vo.getStartDate());
    }

    public long getUploadLimit() {
        return _course_vo.getUploadLimit();
    }

    public boolean isAllowGuests() {
        return _course_vo.getAllowGuests();
    }

    public boolean isAllowObservers() {
        return _course_vo.getAllowObservers();
    }

    public boolean isAvailable() {
        return _course_vo.getAvailable();
    }

    public boolean isHasDescriptionPage() {
        return _course_vo.getHasDescriptionPage();
    }

    public boolean isLocaleEnforced() {
        return _course_vo.getLocaleEnforced();
    }

    public boolean isLockedOut() {
        return _course_vo.getLockedOut();
    }

    public boolean isNavCollapsable() {
        return _course_vo.getNavCollapsable();
    }

    public boolean isOrganization() {
        return _course_vo.getOrganization();
    }

    public boolean isShowInCatalog() {
        return _course_vo.getShowInCatalog();
    }
    
    /**
     * Allows control of guest access to this Course/Org. 
     * 
     * @param allow_guests - "true" turns guest access on, "false" turns guest
     *                       access off 
     */
    public void setAllowGuests(boolean allow_guests) {
        _course_vo.setAllowGuests(allow_guests);
    }

    /**
     * Allows control of observer access to this Course/Org.
     * 
     * @param allow_observers - "true" turns observer access on, "false" turns 
     *                          observer access off. 
     */
    public void setAllowObservers(boolean allow_observers) {
        _course_vo.setAllowObservers(allow_observers);
    }

    /**
     * Allows control of Course/Org availability.
     * 
     * @param available - "true" turns Course/Org availability on, "false" 
     *                    turns Course/Org availability off. 
     */
    public void setAvailable(boolean available) {
        _course_vo.setAvailable(available);
    }

    /**
     * Sets the BatchUID for this Course/Org.
     * 
     * @param batch_uid - the BatchUID to set.
     */
    public void setBatchUid(String batch_uid) {
        _course_vo.setBatchUid(batch_uid);
    }

    /**
     * Sets the button style Bb ID value for this Course/Org.
     * 
     * @param button_style_bbid - This value should be generated by Blackboard,
     *                            in the form "_nnn_1" where nnn is an integer.
     */
    public void setButtonStyleBbId(String button_style_bbid) {
        _course_vo.setButtonStyleBbId(button_style_bbid);
    }

    /**
     * Sets the button shape for this Course/Org. Possible values are: 
     *  - "Default"         = BUTTON_SHAPE_DEFAULT
     *  - "Rectangular"     = BUTTON_SHAPE_RECTANGLE
     *  - "Rounded Corners" = BUTTON_SHAPE_RND_CORNERS 
     *  - "Rounded Ends"    = BUTTON_SHAPE_RND_ENDS
     *  
     * @param button_style_shape - the button style shape to set
     */
    public void setButtonStyleShape(String button_style_shape) {
        _course_vo.setButtonStyleShape(button_style_shape);
    }

    /**
     * Sets the button style type value for this Course/Org. Possible values 
     * are: 
     *  - "Default" = BUTTON_STYLE_DEFAULT
     *  - "Pattern" = BUTTON_STYLE_PATTERN
     *  - "Solid"   = BUTTON_STYLE_SOLID
     *  - "Striped" = BUTTON_STYLE_STRIPED
     *  
     * @param button_style_type - the button style type to set
     */
    public void setButtonStyleType(String button_style_type) {
        _course_vo.setButtonStyleType(button_style_type);
    }

    /**
     * Sets the Cartridge ID value for this Course/Org.  See BbCartridge for
     * more information.
     * 
     * @param cartridge_id - the Cartridge ID to set. This value should be 
     *                       generated by Blackboard, in the form "_nnn_1" 
     *                       where nnn is an integer.
     */
    public void setCartridgeId(String cartridge_id) {
        _course_vo.setCartridgeId(cartridge_id);
    }

    /**
     * Sets the Classification ID value for this Course/Org.  See 
     * BbClassification for more information.
     * 
     * @param classification_id - the Classification ID to set. This value 
     *                            should be generated by Blackboard, in the 
     *                            form "_nnn_1" where nnn is an integer.
     */
    public void setClassificationId(String classification_id) {
        _course_vo.setClassificationId(classification_id);
    }

    /**
     * Sets the duration type for this Course/Org.  Possible values are:
     *  - "Continuous"   = DURATION_CONTINUOUS
     *  - "DateRange"    = DURATION_DATE_RANGE
     *  - "FixedNumDays" = DURATION_FIXED_NUM_DAYS
     * 
     * @param duration - the duration type to set
     */
    public void setDuration(String duration) {
        _course_vo.setCourseDuration(duration);
    }

    /**
     * Sets the Course ID (not the Blackboard generated ID) associated with 
     * this Course.
     * 
     * @param course_id - the ID to set for this Course/Org
     */
    protected void setCourseId(String course_id) {
        _course_vo.setCourseId(course_id);
    }

    /**
     * Indicates if the course is Instructor-led or self-paced according to the
     * student. Possible values are:
     *  - "InstructorLed" = PACE_INSTRUCTOR_LED
     *  - "SelfPaced"     = PACE_SELF_PACED
     *  
     * @param course_pace - the course pace to set.
     */
    public void setPace(String course_pace) {
        _course_vo.setCoursePace(course_pace);
    }

    /**
     * Sets the service level for this Course/Org. Possible values are:
     *  - Course = SERVICE_LEVEL_COURSE - is set by default for all BbCourse 
     *             objects.
     *  - Organization = SERVICE_LEVEL_ORGANIZATION - is set by default for all
     *                   BbOrganization objects.
     *  
     * @param service_level
     */
    public void setServiceLevel(String service_level) {
        _course_vo.setCourseServiceLevel(service_level);
    }

    /**
     * Sets the DataSource ID value for this Course/Org. See BbDataSource for
     * more information.
     * @param data_source_id - the ID of the DataSource associated with this
     *                         Course/Org.  This value should be generated by 
     *                         Blackboard, in the form "_nnn_1" where nnn is an
     *                         integer.
     */
    public void setDataSourceId(String data_source_id) {
        _course_vo.setDataSourceId(data_source_id);
    }

    /**
     * Sets the dec absolute limit for this Course/Org. Quota: absolute limit 
     * on content uploads in bytes.
     * 
     * @param dec_absolute_limit - the dec absolute limit to set.
     */
    public void setDecAbsoluteLimit(long dec_absolute_limit) {
        _course_vo.setDecAbsoluteLimit(dec_absolute_limit);
    }

    /**
     * Sets the description for this Course/Org.
     * 
     * @param description - the description to set.
     */
    public void setDescription(String description) {
        _course_vo.setDescription(description);
    }

    /**
     * Sets the end date value for this Course/Org.  Be aware that Blackboard 
     * does not use milliseconds.  If you include milliseconds in this Long 
     * value, they will be stripped off.
     * 
     * @param end_date - a long representation of the end date.
     */
    public void setEndDate(long end_date) {
        if (String.valueOf(end_date).length() > 10) {
            _course_vo.setEndDate(super.javaLong2bbLong(end_date));
        } else {
            _course_vo.setEndDate(end_date);
        }
    }

    /**
     * Sets the enrollment access code for this Course/Org.
     * 
     * @param enrollment_access_code - the enrollment access code to set for
     *                                 this Course/Org.
     */
    public void setEnrollmentAccessCode(String enrollment_access_code) {
        _course_vo.setEnrollmentAccessCode(enrollment_access_code);
    }

    /**
     * Sets the enrollment end date for this Course/Org. Be aware that 
     * Blackboard does not use milliseconds.  If you include milliseconds in 
     * this Long value, they will be stripped off.
     * 
     * @param enrollment_end_date - a Long representation of the enrollment
     *                              end date.
     */
    public void setEnrollmentEndDate(long enrollment_end_date) {
        if (String.valueOf(enrollment_end_date).length() > 10) {
            _course_vo.setEnrollmentEndDate(super.javaLong2bbLong(enrollment_end_date));
        } else {
            _course_vo.setEnrollmentEndDate(enrollment_end_date);
        }
    }

    /**
     * Sets the enrollment start date for this Course/Org. Be aware that 
     * Blackboard does not use milliseconds.  If you include milliseconds in 
     * this Long value, they will be stripped off.
     * 
     * @param enrollment_end_date - a Long representation of the enrollment
     *                              start date.
     */
    public void setEnrollmentStartDate(long enrollment_start_date) {
        if (String.valueOf(enrollment_start_date).length() > 10) {
            _course_vo.setEnrollmentStartDate(super.javaLong2bbLong(enrollment_start_date));
        } else {
            _course_vo.setEnrollmentStartDate(enrollment_start_date);
        }
    }

    /**
     * Sets the enrollment type value for this Course/Org. Possible values are:
     *  - "EmailEnrollment" = ENROLL_TYPE_EMAIL
     *  - "InstructorLed"   = ENROLL_TYPE_INSTRUCTOR_LED
     *  - "SelfEnrollment"  = ENROLL_TYPE_SELF
     *  
     * @param enrollment_type - the enrollment type to set.
     */
    public void setEnrollmentType(String enrollment_type) {
        _course_vo.setEnrollmentType(enrollment_type);
    }

    /**
     * Expansion data contains a list of key=value strings. Current keys:
     *  - COURSE.UUID = A unique generated ID for the course which never changes 
     *                  (This value is read-only)
     *
     * @param expansion_data - the expansion data to set.
     */
    public void setExpansionData(String[] expansion_data) {
        _course_vo.setExpansionData(expansion_data);
    }

    /**
     * Sets a fee value for this Course/Org.
     * NOTE: This attribute is reserved for future use.
     * 
     * @param fee - the fee to set for this course
     */
    public void setFee(float fee) {
        _course_vo.setFee(fee);
    }

    /**
     * Set the "has description page" flag for this Course/Org.
     * NOTE: This attribute is reserved for future use.
     * 
     * @param has_description_page
     */
    public void setHasDescriptionPage(boolean has_description_page) {
        _course_vo.setHasDescriptionPage(has_description_page);
    }

    /**
     * Sets the ID for this Course/Org.
     * 
     * @param id - the ID value of this Course/Org should be generated by 
     *             Blackboard, in the form "_nnn_1" where nnn is an integer.
     */
    public void setId(String id) {
        _course_vo.setId(id);
    }

    /**
     * Sets the Insitution name value for this Course/Org.
     * 
     * @param institution_name - the Institution name to set.
     */
    public void setInstitutionName(String institution_name) {
        _course_vo.setInstitutionName(institution_name);
    }

    /**
     * Set the locale for this Course/Org.
     * 
     * @param locale
     */
    public void setLocale(String locale) {
        _course_vo.setLocale(locale);
    }

    /**
     * Sets the "enforced locale" flag for this Course/Org. If a course is 
     * identified as enforced, all students will be "forced" to use the locale
     * specified by the course.
     * 
     * @param locale_enforced
     */
    public void setLocaleEnforced(boolean locale_enforced) {
        _course_vo.setLocaleEnforced(locale_enforced);
    }

    /**
     * Sets the "locked out" flag for this Course. If a course is marked as 
     * locked out, no one (not even the instructor) can access it. If the 
     * desired behavior is to only lock out students, see 
     * setIsAvailable(boolean). The system admin should still be able to access
     * the course.  NOTE: This attribute is reserved for future use.
     * 
     * @param locked_out - true if the course is to be locked, false otherwise.
     */
    public void setLockedOut(boolean locked_out) {
        _course_vo.setLockedOut(locked_out);
    }

    /**
     * Sets the name for this Course/Org.
     * 
     * @param name - the name to set for this Course/Org.
     */
    public void setName(String name) {
        _course_vo.setName(name);
    }

    /**
     * Sets the "is navigation collapsable" flag for this Course/Org. This flag
     * determines whether or not users of a course should be allowed to 
     * "collapse" (slide into the side) the navigation links which appear on 
     * the left hand side of the course page.
     * 
     * @param nav_collapsable - true if users should be allowed to collapse the
     *                          navigation buttons, false otherwise
     */
    public void setNavCollapsable(boolean nav_collapsable) {
        _course_vo.setNavCollapsable(nav_collapsable);
    }

    /**
     * Sets the background color that should be used for rendering the 
     * navigation links that appear on the left hand side of the course page.
     * This value is only used if the navigation style 
     * (see getNavigationStyle()) for the course is set to "Text".
     * 
     * @param nav_color_bg -  a String containing the color value that should 
     *                        be used for rendering the background of the 
     *                        navigation links. The expected value is an RGB 
     *                        color value specified as a hexadecimal number 
     *                        prefixed by a hash mark (i.e. white is "#FFFFFF",
     *                        black is "#000000").
     */
    public void setNavColorBg(String nav_color_bg) {
        _course_vo.setNavColorBg(nav_color_bg);
    }

    /**
     * Sets the foreground color (color of text) that should be used for 
     * rendering the navigation links that appear on the left hand side of the
     * course page. This value is only used if the navigation style 
     * (see getNavigationStyle()) for the course is set to "Text".
     * 
     * @param nav_color_fg - a String containing the color value that should be
     *                       used for rendering text on the navigation links. 
     *                       The expected value is an RGB color value specified
     *                       as a hexadecimal number prefixed by a hash mark 
     *                       (i.e. white is "#FFFFFF", black is "#000000").
     */
    public void setNavColorFg(String nav_color_fg) {
        _course_vo.setNavColorFg(nav_color_fg);
    }

    /**
     * Set the navigation style value for this Course/Org. Legal values are:
     *  - Button (NAV_STYLE_BUTTON)
     *  - Text   (NAV_STYLE_TEXT)
     *  
     * @param navigation_style - the navigation style to set
     */
    public void setNavigationStyle(String navigation_style) {
        _course_vo.setNavigationStyle(navigation_style);
    }

    /**
     * Sets the number of days of use for this Course. This setting is only 
     * applicable if the course duration is a fixed number of days.
     * 
     * @param number_of_days_of_use - the number of days of use to set
     */
    public void setNumberOfDaysOfUse(int number_of_days_of_use) {
        _course_vo.setNumberOfDaysOfUse(number_of_days_of_use);
    }

    /**
     * Set to true if this Course object is an Organization. NOTE that this is 
     * a read-only field. To create an Organization you must set the 
     * Course Service Level to "Community".
     * 
     * @param organization - true if an organization, otherwise false.
     */
    public void setOrganization(boolean organization) {
        _course_vo.setOrganization(organization);
    }

    /**
     * Sets the "show in Catalog" flag for this Course/Org.
     * NOTE: This attribute is reserved for future use.
     * 
     * @param show_in_catalog
     */
    public void setShowInCatalog(boolean show_in_catalog) {
        _course_vo.setShowInCatalog(show_in_catalog);
    }

    /**
     * Sets the soft storage limit for this Course/Org. Quota: soft limit on 
     * content uploads in bytes.
     * 
     * @param soft_limit - the new soft storage limit to set.
     */
    public void setSoftLimit(long soft_limit) {
        _course_vo.setSoftLimit(soft_limit);
    }

    /**
     * Sets the start date value for this Course/Org.  Be aware that Blackboard 
     * does not use milliseconds.  If you include milliseconds in this Long 
     * value, they will be stripped off.
     * 
     * @param start_date - a Long representation of the start date.
     */
    public void setStartDate(long start_date) {
        if (String.valueOf(start_date).length() > 10) {
        	_course_vo.setStartDate(super.javaLong2bbLong(start_date));
        } else {
            _course_vo.setStartDate(start_date);
        }
    }

    /**
     * Sets the upload storage limit for this Course/Org.  Quota: limit on size
     * of a single upload in bytes.
     * 
     * @param upload_limit - the new upload storage limit to set.
     */
    public void setUploadLimit(long upload_limit) {
        _course_vo.setUploadLimit(upload_limit);
    }    
}
