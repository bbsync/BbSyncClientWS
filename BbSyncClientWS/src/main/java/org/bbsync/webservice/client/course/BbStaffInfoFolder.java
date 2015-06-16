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
 * StaffInfoFolder objects are used within courses to store & organize 
 * StaffInfoContact objects.
 * 
 * In the Blackboard User Interface, StaffInfoFolder objects are accessible in
 * the "Contacts" page of a Course's "Course Tools" menu.
 * 
 * A few important notes about using StaffInfoFolder objects:
 * 
 * 1)  A newly created Course won't have any StaffInfoFolder objects unless a 
 *     User has accessed the Course's Contacts page.  If that has happened, 
 *     a default (no title) StaffInfoFolder object will have been created.
 * 2)  All StaffInfoContacts MUST be contained by a StaffInfoFolder.  The 
 *     StaffInfoContact.persist() method will determine if a StaffInfoFolder 
 *     already exists and create one if needed. Then, it will automatically 
 *     save the StaffInfoContact using the newly created StaffInfoFolder ID.
 *     If you don't use the StaffInfoContact.persist() method, you should
 *     specify a StaffInfoFolderId. 
 * 3)  StaffInfoFolders can be nested by specifying the parent folder ID and a
 *     distinct folder name.
 */
public class BbStaffInfoFolder extends AbstractStaffInfo {
    private static final long serialVersionUID = 3333000012121212L;
    
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Creates a new BbStaffInfoFolder.
     * New BbStaffInfoFolders are made available by default. 
     */
    public BbStaffInfoFolder() {
    	_staff_info_vo = new StaffInfoVO();
    	_staff_info_vo.setFolder(true);
    	_staff_info_vo.setAvailable(true);
    }

    /** 
     * This constructor converts a StaffInfoVO to a BbStaffInfoFolder.
     * 
     * @param staff_info_vo - the virtual object used to initialize a new 
     *                        BbStaffInfoFolder.
     */
    public BbStaffInfoFolder(StaffInfoVO staff_info_vo) {
    	_staff_info_vo = staff_info_vo;
    	_staff_info_vo.setFolder(true);
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
    public BbStaffInfoFolder[] getAllBbStaffInfoFolders(String course_id){
    	return convert_StaffInfoVOArray_to_BbStaffInfoFolderArray(super.getStaffInfo(course_id));
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    
	/**
	 * Name is a required field for StaffInfoFolder UNLESS you're creating the
	 * top-level "default" folder.  In this case, don't set this field.
	 * 
	 * @return Returns the name of this StaffInfoFolder.
	 */
	public String getName(){
		return super.getTitle();
	}

	/**
	 * Sets the name of this StaffInfoFolder.  Name is a required field for 
	 * StaffInfoFolder UNLESS you're creating the top-level "default" folder.  
	 * In this case, don't set this field.
	 * 
	 * @param name - The folder name to set.
	 */
	public void setName(String name){
		super.setTitle(name);
	}
	
	/**
	 * @return Returns the name color.
	 */
	public String getNameColor(){
		return super.getTitleColor();
	}
	
	/**
	 * Sets the color to render the StaffInfoFolder's name.
	 * 
	 * @param color - String in HTML RGB form. This value should not 
	 *                      exceed 7 characters.
	 */
	public void setNameColor(String color){
		super.setTitleColor(color);
	}

	/**
	 * @return Returns the description for this StaffInfoFolder.
	 */
	public String getDescription(){
		return super.getBiography();
	}

	/**
	 * Sets the description for this StaffInfoFolder.
	 * 
	 * @param notes - the description to set.
	 */
	public void setDescription(String description){
		super.setBiography(description);
	}
	
	/**
     * @return  Returns the text type representing the following fields: 
     * 			TEXT_TYPE_HTML = 'HTML'
     * 		    TEXT_TYPE_PLAIN_TEXT = 'PLAIN_TEXT' 
     *          TEXT_TYPE_SMART_TEXT = 'SMART_TEXT'
     */
	public String getDescriptionType(){
		return super.getBiographyType();
	}

	/**  
	 * The description_type changes the way the StaffInfoFolder notes 
	 * description is interpreted.  If not specified or an invalid value is 
	 * specified, SMART_TEXT is assumed.
	 * 
     * @param description_type -  Possible values are: 
     * 					   		  TEXT_TYPE_HTML
     * 							  TEXT_TYPE_PLAIN_TEXT 
     *                            TEXT_TYPE_SMART_TEXT
	 */
	public void setDescriptionType(String description_type){
		super.setBiographyType(description_type);
	}

	/**
	 * Gets the ID of the StaffInfoFolder that contains this StaffInfoFolder.
	 * 
	 * @return Returns the ID of the containing StaffInfoFolder. 
	 */
	public String getParentFolderId(){
		return super.getParentId();
	}

	/**
	 * If this StaffInfoFolder will be nested inside of another StaffInfoFolder
	 * put the ID of the parent StaffInfoFolder here. 
	 * 
	 * @param staff_info_folder_id - the ID of the parent StaffInfoFolder
	 */
	public void setParentFolderId(String staff_info_folder_id){
		super.setParentId(staff_info_folder_id);
	} 
	
	private BbStaffInfoFolder[] convert_StaffInfoVOArray_to_BbStaffInfoFolderArray(StaffInfoVO[] si_vos){
    	if(si_vos==null || si_vos.length<1) return null;
		List<BbStaffInfoFolder> bb_sis = new ArrayList<BbStaffInfoFolder>();
		for(StaffInfoVO staff_info : si_vos){
			if(staff_info!=null && staff_info.getFolder()) bb_sis.add(new BbStaffInfoFolder(staff_info));
		}
		return bb_sis.toArray(new BbStaffInfoFolder[]{});
    }
}
