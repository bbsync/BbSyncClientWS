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
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.course.BbStaffInfoContact;
import org.bbsync.webservice.client.course.BbStaffInfoFolder;
import org.bbsync.webservice.client.generated.CourseWSStub.StaffInfoVO;
import org.bbsync.webservice.client.generated.CourseWSStub.VersionVO;
import org.bbsync.webservice.client.proxytool.CourseProxyTool;

/**
 * The AbstractStaffInfo class is a representation of a staff information 
 * element within the Blackboard application. StaffInfo objects are used within
 * courses to store information about instructors, TAs, guest lecturers, etc.
 * 
 * In the Blackboard User Interface, StaffInfo objects are accessible in the
 * "Contacts" page of a Course's "Course Tools" menu.
 * 
 * In the Blackboard WebServices API, there is one concrete StaffInfo object
 * that performs two discrete functions: 1) StaffInfo as a contact stores
 * personal contact information.  2)  Staff Info as a folder serves as a 
 * container for StaffInfo contacts and other StaffInfo folders.
 * 
 * Because of these functional differences and minimal overlap of data fields,
 * BbSync.org has separated StaffInfo into two different concrete classes to 
 * make StaffInfo easier to work with.  The two classes are StaffInfoContact 
 * and StaffInfoFolder.
 */
public abstract class AbstractStaffInfo extends CourseProxyTool {
	private static final Logger logger = Logger.getLogger(AbstractStaffInfo.class.getName());
	private static final long serialVersionUID = 3333000000009999L;
	protected StaffInfoVO _staff_info_vo = null;
    protected String _course_id = null;
    
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////
    /** 
     * This method will save this StaffInfo object, be sure to set the 
     * Course ID that will contain the StaffInfo prior to calling this
     * method.
     * 
     * @return Returns the ID of the saved StaffInfo object.
     */
    public Serializable persist() {  
    	//If this is a StaffInfo Folder, save it. 
    	if(isFolder()) return super.saveStaffInfo(getCourseId(), _staff_info_vo);
    	//If this is StaffInfo Contact with a Parent ID, save it.
    	if(getParentId()!=null) return super.saveStaffInfo(getCourseId(), _staff_info_vo);
    	//Since there's no Parent ID set, we need to see if there is a parent folder available
    	AbstractStaffInfo[] infos = getAllBbStaffInfos(getCourseId());
    	//If there's no StaffInfo objects returned, create a StaffInfoFolder & use it as the parent ID
    	if(infos==null || infos.length==0){
    		setParentId(createDefaultParentFolder());
    		return super.saveStaffInfo(getCourseId(), _staff_info_vo);
    	}
    	//If there's one StaffInfo objects returned & it's a StaffInfoFolder, use it as the parent ID
    	if(infos.length==1 && infos[0].isFolder()){
    		setParentId(infos[0].getId());
    		return super.saveStaffInfo(getCourseId(), _staff_info_vo);
    	}
    	//if there's more than one StaffInfo object returned, find the default folder & use it as the parent ID
    	if(infos.length>1){
    		for(AbstractStaffInfo info:infos){
    			if(info.isFolder() && info.getSirTitle().equals("")){
    				return super.saveStaffInfo(info.getCourseId(), _staff_info_vo);
    			}
    		}
    	}
    	logger.error("Unable to persist StaffInfo Contact.  Cound't find determine the default StaffInfo Folder. ");
    	return null;
    }
    
    /** 
     * This method will return the specified StaffInfo object. If the StaffInfo
     * object is a StaffInfoContact, it can be specified by setting the Course 
     * ID and the StaffInfo ID -OR- by setting the Course ID and email address 
     * (the email address is a required field for StaffInfoContact objects).
     * 
     * If multiple StaffInfo objects are found (i.e. more than one 
     * StaffInfoContact may share the same email address) only the first 
     * StaffInfo object found will be returned.
     * 
     * @return Returns the specified BbStaffInfoFolder or StaffInfoContact.
     */
    public Object retrieve() {
    	StaffInfoVO[] staff_info_vos = null;
    	if(getCourseId()==null) return null;
    	staff_info_vos = super.getStaffInfo(this.getCourseId());
    	if(staff_info_vos==null || staff_info_vos.length==0) return null;
    	for(StaffInfoVO staff_info:staff_info_vos){
    		if(getId()!=null && getId().equals(staff_info.getId())){
    			if(staff_info.getFolder())return new BbStaffInfoFolder(staff_info);
    			if(!staff_info.getFolder())return new BbStaffInfoContact(staff_info);
    		}
    		if(getEmail()!=null && getEmail().equals(staff_info.getEmail())){
    			return new BbStaffInfoContact(staff_info);
    		}
    	}
    	return null;
    }
    
    public boolean delete() {
    	String[] ids = super.deleteStaffInfo(getCourseId(), new String[]{getId()});
		if(ids==null) return false;
		if(ids[0].equals(getId())) return true;
    	return false;
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

	/**
     * Given a Course ID, this method will save this StaffInfo object.
     *                    
     * @param course_id  - The ID of the course that will contain this StaffInfo
     *                     object.            
     * @return Returns the ID of the StaffInfo object.
     */
    public String saveStaffInfo(String course_id){
    	if(course_id==null) return null;
    	setCourseId(course_id);
    	return super.saveStaffInfo(course_id, _staff_info_vo);
    }
    
    /**
     * Returns all of the StaffInfo objects associated with the specified
     * Course.
     *  
     * @param course_id - the ID of a Course.
     * @return  Returns an array of AbstractStaffInfo objects.  These objects 
     *          can be either StaffInfoFolders or StaffInfoContacts.
     */
    public AbstractStaffInfo[] getAllBbStaffInfos(String course_id){
    	return convert_StaffInfoVOArray_to_AbstractStaffInfoArray(super.getStaffInfo(course_id));
    }
    
    public String[] deleteStaffInfo(String course_id, String[] staff_info_ids){
    	return super.deleteStaffInfo(course_id, staff_info_ids);
    }
   	    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
	 * Set the Course ID to associate this StaffInfo object with.  Setting the 
	 * Course ID is only required for the retrieve() method to work. The Course
	 * ID is not saved with the StaffInfo object.  
	 *  
	 * @param course_id - The Course ID to set. 
	 */
	public void setCourseId(String course_id){
		_course_id = course_id;
	}
	
	/**
	 * @return Returns the ID of the Course that contains this StaffInfo object. 
	 */
	public String getCourseId(){
		return _course_id;
	}
    
	/**
	 * @return Returns the sir title.
	 */
	protected String getSirTitle(){
		return _staff_info_vo.getSirTitle();
	}

	/**
	 * Set the staff's title for this StaffInfo object.
	 * @param sir_title - The sir title to set.  This attribute should not 
	 *                    exceed 100 characters.
	 */
	protected void setSirTitle(String sir_title){
		_staff_info_vo.setSirTitle(sir_title);
	}

	/**
	 * @return Returns the availability of this staff info object.
	 */
	public boolean isAvailable(){
		return _staff_info_vo.getAvailable();
	}

	/**
	 * Set available status for this StaffInfo object.
	 * 
	 * @param available - true if available, else false
	 */
	public void setAvailable(boolean available){
		_staff_info_vo.setAvailable(available);
	}

	/**
	 * Returns the value of the folder attribute.
	 * 
	 * @return Returns true if this StaffInfo object is a folder. 
	 */
	protected boolean isFolder(){
		return _staff_info_vo.getFolder();
	}

	/**
	 * Sets whether this StaffInfo should be treated as a folder of staff 
	 * information objects, or as an actual staff information object.  
	 * Manually setting this attribute should only be done when the final type
	 * of the staff information object is not known when the object is 
	 * instantiated.
	 * 
	 * @param folder - sets whether this StaffInfo should be treated as a 
	 *                 folder of staff information objects, or as an actual 
	 *                 staff information object
	 */
	protected void setFolder(boolean folder){
		_staff_info_vo.setFolder(folder);
	}	

	/**
	 * @return Returns the position of this StaffInfo object in its parent
	 *         folder.
	 */
	public int getPosition(){
		return _staff_info_vo.getPosition();
	}

	/**
	 * Set position for this StaffInfo object in the parent folder.
	 * @param position - the position to set.
	 */
	public void setPosition(int position){
		_staff_info_vo.setPosition(position);
	}

	/**
	 * @return Returns the title color.
	 */
	protected String getTitleColor(){
		return _staff_info_vo.getTitleColor();
	}

	/**
	 * Sets the color to render this object's title.
	 * 
	 * @param title_color - String in HTML RGB form. This value should not 
	 *                      exceed 7 characters.
	 */
	protected void setTitleColor(String title_color){
		_staff_info_vo.setTitleColor(title_color);
	}

	/**
	 * @return Returns the title for this object. This is typically only used 
	 *         for StaffInfo folders.
	 */
	protected String getPersistentTitle(){
		return _staff_info_vo.getPersistentTitle();
	}

	/**
	 * Set persistent title for this StaffInfo object. This is a read-only 
	 * attribute.
	 * 
	 * @param persistent_title - the persistent_title to set.
	 */
	protected void setPersistentTitle(String persistent_title){
		_staff_info_vo.setPersistentTitle(persistent_title);
	}

	/** 
	 * @return Returns the staff's title.
	 */
	protected String getTitle(){
		return _staff_info_vo.getTitle();
	}

	/**
	 * Set staff's title for this StaffInfo object.
	 * 
	 * @param title - the title to set
	 */
	protected void setTitle(String title){
		_staff_info_vo.setTitle(title);
	}

	/**
	 * @return Returns the staff's given name.
	 */
	protected String getGivenName(){
		return _staff_info_vo.getGivenName();
	}

	/**
	 * Set staff's given name for this StaffInfo.
	 * 
	 * @param given_name - the given name to set
	 */
	protected void setGivenName(String given_name){
		_staff_info_vo.setGivenName(given_name);
	}

	/**
	 * @return Returns the staff's family name.
	 */
	protected String getFamilyName(){
		return _staff_info_vo.getFamilyName();
	}
	
	/**
	 * Set staff's family name for this StaffInfo object.
	 * 
	 * @param family_name - the family name to set
	 */
	protected void setFamilyName(String family_name){
		_staff_info_vo.setFamilyName(family_name);
	}

	/**
	 * @return Returns the staff's phone number.
	 */
	protected String getPhone(){
		return _staff_info_vo.getPhone();
	}

	/**
	 * Set staff's phone number for this StaffInfo object.
	 * 
	 * @param phone - the phone number to set
	 */
	protected void setPhone(String phone){
		_staff_info_vo.setPhone(phone);
	}

	/**
	 * Email is a required field for StaffInfoContacts.
	 * 
	 * @return Returns the email address for this StaffInfo object.
	 */
	protected String getEmail(){
		return _staff_info_vo.getEmail();
	}

	/**
	 * Sets the email address for this StaffInfo object.  Email is a required
	 * field for StaffInfoContacts.
	 * 
	 * @param email - the email address for this StaffInfo object
	 */
	protected void setEmail(String email){
		_staff_info_vo.setEmail(email);
	}

	/**
	 * @return Returns the office hours.
	 */
	protected String getOfficeHours(){
		return _staff_info_vo.getOfficeHours();
	}

	/**
	 * Set staff's office hours for this StaffInfo object.
	 * 
	 * @param office_hours - the office hours to set
	 */
	protected void setOfficeHours(String office_hours){
		_staff_info_vo.setOfficeHours(office_hours);
	}

	/**
	 * @return Returns the office address
	 */
	protected String getOfficeAddress(){
		return _staff_info_vo.getOfficeAddress();
	}

	/**
	 * Set staff's office address for this StaffInfo Object
	 * 
	 * @param office_address - the office address to set
	 */
	protected void setOfficeAddress(String office_address){
		_staff_info_vo.setOfficeAddress(office_address);
	}
	
	/**
	 * @return Returns the home page URL.
	 */
	protected String getHomePageUrl(){
		return _staff_info_vo.getHomePageUrl();
	}

	/**
	 * Set staff's home page URL for this StaffInfo object.
	 * @param home_page_url - the home page URL to set
	 */
	protected void setHomePageUrl(String home_page_url){
		_staff_info_vo.setHomePageUrl(home_page_url);
	}

	/**
	 * @return Returns the biography.
	 */
	protected String getBiography(){
		return _staff_info_vo.getBiography();
	}

	/**
	 * Set staff's biography for this StaffInfo object.
	 * 
	 * @param biography - the biography to set.
	 */
	protected void setBiography(String biography){
		_staff_info_vo.setBiography(biography);
	}
	
	/**
	 * @return Returns the ID for this StaffInfo object.
	 */
	public String getId(){
		return _staff_info_vo.getId();
	}

	/**
	 * Set the staff ID for this StaffInfo object.
	 * 
	 * @param id - the ID to set. This ID should be generated by Blackboard, in 
	 *             the form "_nnn_1" where nnn is an integer.
	 */
	public void setId(String id){
		_staff_info_vo.setId(id);
	}

	/**
     * @return  Returns the text type representing the following fields: 
     * 			TEXT_TYPE_HTML = 'HTML'
     * 		    TEXT_TYPE_PLAIN_TEXT = 'PLAIN_TEXT' 
     *          TEXT_TYPE_SMART_TEXT = 'SMART_TEXT'
     */
	protected String getBiographyType(){
		return _staff_info_vo.getBiographyType();
	}

	/**  
	 * The Biography Type changes the way the biography text is interpreted.
     * If not specified or an invalid value is specified then SMART_TEXT is 
     * assumed.
     * @param biography_type -  Possible values are: 
     * 							TEXT_TYPE_HTML
     * 							TEXT_TYPE_PLAIN_TEXT 
     *                          TEXT_TYPE_SMART_TEXT
	 */
	protected void setBiographyType(String biography_type){
		_staff_info_vo.setBiographyType(biography_type);
	}

	/**
	 * Expansion data is currently ignored. In future versions it may be used 
	 * to add additional attributes without breaking the wsdl contract.
	 * 
	 * @return Returns the expansion data array.
	 */
	public String[] getExpansionData(){
		return _staff_info_vo.getExpansionData();
	}

	/**
	 * Expansion data is currently ignored. In future versions it may be used 
	 * to add additional attributes without breaking the wsdl contract.
	 * 
	 * @param expansion_data - the expansion data to set (For Future Use)
	 */
	public void setExpansionData(String[] expansion_data){
		_staff_info_vo.setExpansionData(expansion_data);
	}

	/**
	 * Gets the parent ID of this StaffInfo object. The provided ID should be the ID
	 * of a StaffInfoFolder object, since only folders can contain other objects.
	 * 
	 * @return Returns the ID of the containing StaffInfoFolder object. 
	 */
	protected String getParentId(){
		return _staff_info_vo.getParentId();
	}

	/**
	 * Sets the parent ID to the specified ID. The provided ID should be the ID
	 * of a StaffInfoFolder object, since only folders can contain other objects.
	 * 
	 * @param parent_id - the ID of the parent folder
	 */
	protected void setParentId(String parent_id){
		_staff_info_vo.setParentId(parent_id);
	}

	private AbstractStaffInfo[] convert_StaffInfoVOArray_to_AbstractStaffInfoArray(StaffInfoVO[] si_vos){
    	if(si_vos==null || si_vos.length<1) return null;
		List<AbstractStaffInfo> bb_sis = new ArrayList<AbstractStaffInfo>();
		for(StaffInfoVO staff_info : si_vos){
			if(staff_info!=null && staff_info.getFolder()) bb_sis.add(new BbStaffInfoFolder(staff_info));
			if(staff_info!=null && !staff_info.getFolder()) bb_sis.add(new BbStaffInfoContact(staff_info));
		}
		return bb_sis.toArray(new AbstractStaffInfo[]{});
    } 
	
	private String createDefaultParentFolder(){
    	StaffInfoVO staff_info = new StaffInfoVO();
    	staff_info.setAvailable(true);	
    	staff_info.setFolder(true);
    	staff_info.setPosition(0);
    	return super.saveStaffInfo(_course_id, staff_info);
    }
}
