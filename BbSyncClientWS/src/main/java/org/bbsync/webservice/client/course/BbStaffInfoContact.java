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
import java.util.List;

import org.bbsync.webservice.client.abstracts.AbstractStaffInfo;
import org.bbsync.webservice.client.generated.CourseWSStub.StaffInfoVO;


/**
 * StaffInfoContact objects are used within courses to store contact 
 * information about instructors, TAs, guest lecturers, etc.
 * 
 * In the Blackboard User Interface, StaffInfoContact objects are accessible in
 * the "Contacts" page of a Course's "Course Tools" menu.
 * 
 * A few important notes about using StaffInfoContact objects:
 * 
 * 1)  A newly created Course won't have any StaffInfoContact Objects.
 * 2)  All StaffInfoContacts MUST be contained by a StaffInfoFolder.  The 
 *     StaffInfoContact.persist() method will determine if a StaffInfoFolder 
 *     already exists and create one if needed. Then, it will automatically 
 *     save the StaffInfoContact using the newly created StaffInfoFolder ID.
 *     If you don't use the StaffInfoContact.persist() method, you should
 *     specify a StaffInfoFolderId. 
 * 3)  To persist (or save) a StaffInfoContact, you'll need to set the 
 *     following information fields:
 *        - Course ID  - only required to be set if using the persist() 
 *                       method, otherwise supply the Course ID as the method 
 *                       parameter for the saveStaffInfo() method.
 *        - Email      - Required field for StaffInfoContacts.
 *        - Title      - Required field for StaffInfoContacts UNLESS you set 
 *                       First Name or Last Name.
 *        - First Name - Required field for StaffInfoContacts UNLESS you set 
 *                       Last Name or Title.
 *        - Last Name  - Required field for StaffInfoContacts UNLESS you set 
 *                       First Name or Title.
 */
public class BbStaffInfoContact extends AbstractStaffInfo {
    private static final long serialVersionUID = 3333000011111111L;
    
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Creates a new BbStaffInfoContact.
     * New BbStaffInfoContacts are made available by default. 
     */
    public BbStaffInfoContact() {
    	_staff_info_vo = new StaffInfoVO();
    	_staff_info_vo.setFolder(false);
    	_staff_info_vo.setAvailable(true);
    }

    /** 
     * This constructor converts a StaffInfoVO to a BbStaffInfoContact.
     * 
     * @param staff_info_vo - the virtual object used to initialize a new 
     *                        BbStaffInfoContact.
     */
    public BbStaffInfoContact(StaffInfoVO staff_info_vo) {
    	_staff_info_vo = staff_info_vo;
    	_staff_info_vo.setFolder(false);
    }
   
    ///////////////////////////////////////////////////////////////////////////
    //  Implemented ProxyTool Methods  ////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Returns all of the StaffInfoContact objects associated with the 
     * specified Course.
     *  
     * @param course_id - the ID of a Course.
     * @return  Returns an array of BbStaffInfoContact objects.
     */
    public BbStaffInfoContact[] getAllBbStaffInfoContacts(String course_id){
    	return convert_StaffInfoVOArray_to_BbStaffInfoContactArray(super.getStaffInfo(course_id));
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    
	/**
	 * Title is a required field for StaffInfoContacts UNLESS you set First 
	 * Name or Last Name.
	 * 
	 * @return Returns the title for this StaffInfoContact.
	 */
	public String getTitle(){
		return super.getSirTitle();
	}

	/**
	 * Set the title for this StaffInfoContact.  Title is a required field for 
	 * StaffInfoContacts UNLESS you set First Name or Last Name.
	 * 
	 * @param title - The title to set.  This attribute should not exceed 100
	 *                characters.
	 */
	public void setTitle(String title){
		super.setSirTitle(title);
	}

	/**
	 * First Name is a required field for StaffInfoContacts UNLESS you set Last
	 * Name or Title.
	 * 
	 * @return Returns the first name/given name for this StaffInfoContact.
	 */
	public String getFirstName(){
		return super.getGivenName();
	}

	/**
	 * Sets the first name/given name for this StaffInfoContact. First Name is 
	 * a required field for StaffInfoContacts UNLESS you set Last Name or 
	 * Title.
	 * 
	 * @param first_name - - the first name/given name to set
	 */
	public void setFirstName(String first_name){
		super.setGivenName(first_name);
	}

	/**
	 * Last Name is a required field for StaffInfoContacts UNLESS you set First
	 * Name or Title.
	 * 
	 * @return Returns the last name/family name for this StaffInfoContact.
	 */
	public String getLastName(){
		return super.getFamilyName();
	}
	
	/**
	 * Sets the last name/family name for this StaffInfoContact.
	 * 
	 * @param last_name - the last name/family name to set
	 */
	public void setLastName(String last_name){
		super.setFamilyName(last_name);
	}

	public String getPhone(){
		return super.getPhone();
	}

	public void setPhone(String phone){
		super.setPhone(phone);
	}

	public String getEmail(){
		return super.getEmail();
	}

	public void setEmail(String email){
		super.setEmail(email);
	}

	public String getOfficeHours(){
		return super.getOfficeHours();
	}

	public void setOfficeHours(String office_hours){
		super.setOfficeHours(office_hours);
	}

	/**
	 * @return Returns the office location for this StaffInfoContact.
	 */
	public String getOfficeLocation(){
		return super.getOfficeAddress();
	}

	/**
	 * Sets the office location for this StaffInfoContact.
	 * 
	 * @param office_location - the office location to set
	 */
	public void setOfficeLocation(String office_location){
		super.setOfficeAddress(office_location);
	}
	
	/**
	 * @return Returns the personal link for this StaffInfoContact.
	 */
	public String getPersonalLink(){
		return super.getHomePageUrl();
	}

	/**
	 * Set the personal link (e.g. home page URL) for this StaffInfoContact.
	 * 
	 * @param home_page_url - the the personal link to set.
	 */
	public void setPersonalLink(String personal_link){
		super.setHomePageUrl(personal_link);
	}

	/**
	 * @return Returns the notes for this StaffInfoContact.
	 */
	public String getNotes(){
		return super.getBiography();
	}

	/**
	 * Sets the notes for this StaffInfoContact.
	 * 
	 * @param notes - the notes to set.
	 */
	public void setNotes(String notes){
		super.setBiography(notes);
	}

	/**
     * @return  Returns the text type representing the following fields: 
     * 			TEXT_TYPE_HTML       = 'HTML'
     * 		    TEXT_TYPE_PLAIN_TEXT = 'PLAIN_TEXT' 
     *          TEXT_TYPE_SMART_TEXT = 'SMART_TEXT'
     */
	public String getNotesType(){
		return super.getBiographyType();
	}

	/**  
	 * The notes_type changes the way the StaffInfoContact notes field is 
	 * interpreted.  If not specified or an invalid value is specified,
	 * SMART_TEXT is assumed.
     * @param notes_type -  Possible values are: 
     * 						TEXT_TYPE_HTML
     * 						TEXT_TYPE_PLAIN_TEXT 
     *                      TEXT_TYPE_SMART_TEXT
	 */
	public void setNotesType(String notes_type){
		super.setBiographyType(notes_type);
	}

	/**
	 * Gets the ID of the StaffInfoFolder that contains this StaffInfoContact.
	 * 
	 * @return Returns the ID of the containing StaffInfoFolder. 
	 */
	public String getStaffInfoFolderId(){
		return super.getParentId();
	}

	/**
	 * Every StaffInfoContact REQUIRES a StaffInfoFolder ID.  If this field is
	 * left as null, this StaffInfoContact will be saved in the default, top-
	 * level StaffInfoFolder 
	 * 
	 * @param staff_info_folder_id - the ID of the containing StaffInfoFolder
	 */
	public void setStaffInfoFolderId(String staff_info_folder_id){
		super.setParentId(staff_info_folder_id);
	} 
	
	private BbStaffInfoContact[] convert_StaffInfoVOArray_to_BbStaffInfoContactArray(StaffInfoVO[] si_vos){
    	if(si_vos==null || si_vos.length<1) return null;
		List<BbStaffInfoContact> bb_sis = new ArrayList<BbStaffInfoContact>();
		for(StaffInfoVO staff_info : si_vos){
			if(staff_info!=null && !staff_info.getFolder()) bb_sis.add(new BbStaffInfoContact(staff_info));
		}
		return bb_sis.toArray(new BbStaffInfoContact[]{});
    } 
}
