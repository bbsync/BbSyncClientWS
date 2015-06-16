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

package org.bbsync.webservice.client.proxytool;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataHandler;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.ClientWebService;
import org.bbsync.webservice.client.context.BbContext;
import org.bbsync.webservice.client.course.BbCourse;
import org.bbsync.webservice.client.generated.*;
import org.bbsync.webservice.client.generated.CourseWSStub.AddCourseToTerm;
import org.bbsync.webservice.client.generated.CourseWSStub.AddCourseToTermResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.CartridgeVO;
import org.bbsync.webservice.client.generated.CourseWSStub.CategoryFilter;
import org.bbsync.webservice.client.generated.CourseWSStub.CategoryMembershipFilter;
import org.bbsync.webservice.client.generated.CourseWSStub.CategoryMembershipVO;
import org.bbsync.webservice.client.generated.CourseWSStub.CategoryVO;
import org.bbsync.webservice.client.generated.CourseWSStub.ChangeCourseBatchUid;
import org.bbsync.webservice.client.generated.CourseWSStub.ChangeCourseBatchUidResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.ChangeCourseCategoryBatchUid;
import org.bbsync.webservice.client.generated.CourseWSStub.ChangeCourseCategoryBatchUidResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.ChangeCourseDataSourceId;
import org.bbsync.webservice.client.generated.CourseWSStub.ChangeCourseDataSourceIdResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.ChangeOrgBatchUid;
import org.bbsync.webservice.client.generated.CourseWSStub.ChangeOrgBatchUidResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.ChangeOrgCategoryBatchUid;
import org.bbsync.webservice.client.generated.CourseWSStub.ChangeOrgCategoryBatchUidResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.ChangeOrgDataSourceId;
import org.bbsync.webservice.client.generated.CourseWSStub.ChangeOrgDataSourceIdResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.ClassificationVO;
import org.bbsync.webservice.client.generated.CourseWSStub.CourseFilter;
import org.bbsync.webservice.client.generated.CourseWSStub.CourseVO;
import org.bbsync.webservice.client.generated.CourseWSStub.CreateCourse;
import org.bbsync.webservice.client.generated.CourseWSStub.CreateCourseResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.CreateOrg;
import org.bbsync.webservice.client.generated.CourseWSStub.CreateOrgResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteCartridge;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteCartridgeResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteCourse;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteCourseCategory;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteCourseCategoryMembership;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteCourseCategoryMembershipResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteCourseCategoryResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteCourseResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteGroup;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteGroupResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteOrg;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteOrgCategory;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteOrgCategoryMembership;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteOrgCategoryMembershipResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteOrgCategoryResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteOrgResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteStaffInfo;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteStaffInfoResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteTerm;
import org.bbsync.webservice.client.generated.CourseWSStub.DeleteTermResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.GetAvailableGroupTools;
import org.bbsync.webservice.client.generated.CourseWSStub.GetAvailableGroupToolsResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.GetCartridge;
import org.bbsync.webservice.client.generated.CourseWSStub.GetCartridgeResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.GetCategories;
import org.bbsync.webservice.client.generated.CourseWSStub.GetCategoriesResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.GetClassifications;
import org.bbsync.webservice.client.generated.CourseWSStub.GetClassificationsResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.GetCourse;
import org.bbsync.webservice.client.generated.CourseWSStub.GetCourseCategoryMembership;
import org.bbsync.webservice.client.generated.CourseWSStub.GetCourseCategoryMembershipResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.GetCourseResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.GetGroup;
import org.bbsync.webservice.client.generated.CourseWSStub.GetGroupResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.GetOrg;
import org.bbsync.webservice.client.generated.CourseWSStub.GetOrgCategoryMembership;
import org.bbsync.webservice.client.generated.CourseWSStub.GetOrgCategoryMembershipResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.GetOrgResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.GetServerVersion;
import org.bbsync.webservice.client.generated.CourseWSStub.GetServerVersionResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.GetStaffInfo;
import org.bbsync.webservice.client.generated.CourseWSStub.GetStaffInfoResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.GroupFilter;
import org.bbsync.webservice.client.generated.CourseWSStub.GroupVO;
import org.bbsync.webservice.client.generated.CourseWSStub.InitializeCourseWS;
import org.bbsync.webservice.client.generated.CourseWSStub.InitializeCourseWSResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.LoadCoursesInTerm;
import org.bbsync.webservice.client.generated.CourseWSStub.LoadCoursesInTermResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.LoadTerm;
import org.bbsync.webservice.client.generated.CourseWSStub.LoadTermByCourseId;
import org.bbsync.webservice.client.generated.CourseWSStub.LoadTermByCourseIdResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.LoadTermResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.LoadTerms;
import org.bbsync.webservice.client.generated.CourseWSStub.LoadTermsByName;
import org.bbsync.webservice.client.generated.CourseWSStub.LoadTermsByNameResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.LoadTermsResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.RemoveCourseFromTerm;
import org.bbsync.webservice.client.generated.CourseWSStub.RemoveCourseFromTermResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveCartridge;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveCartridgeResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveCourse;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveCourseCategory;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveCourseCategoryMembership;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveCourseCategoryMembershipResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveCourseCategoryResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveCourseResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveGroup;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveGroupResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveOrgCategory;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveOrgCategoryMembership;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveOrgCategoryMembershipResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveOrgCategoryResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveStaffInfo;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveStaffInfoResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveTerm;
import org.bbsync.webservice.client.generated.CourseWSStub.SaveTermResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.SetCourseBannerImage;
import org.bbsync.webservice.client.generated.CourseWSStub.SetCourseBannerImageResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.StaffInfoVO;
import org.bbsync.webservice.client.generated.CourseWSStub.TermVO;
import org.bbsync.webservice.client.generated.CourseWSStub.UpdateCourse;
import org.bbsync.webservice.client.generated.CourseWSStub.UpdateCourseResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.UpdateOrg;
import org.bbsync.webservice.client.generated.CourseWSStub.UpdateOrgResponse;
import org.bbsync.webservice.client.generated.CourseWSStub.VersionVO;


public abstract class CourseProxyTool extends AbstractProxyTool
    implements ClientWebService, Serializable {
    private static final Logger logger = Logger.getLogger(CourseProxyTool.class.getName());
    private static final long serialVersionUID = 3333000000001111L;
    public static final String TEXT_TYPE_HTML = "HTML";
    public static final String TEXT_TYPE_PLAIN_TEXT = "PLAIN_TEXT"; 
    public static final String TEXT_TYPE_SMART_TEXT = "SMART_TEXT";
    
    private static String ws_url = null;
    private static String client_pw = null;
    private transient CourseWSStub courseWS = null;
    private transient BbContext session = null;
    private static final String CLIENT_PROGRAM_ID = "CourseProxyTool";
    
	///////////////////////////////////////////////////////////////////////////
	//  Constructor  //////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
    protected CourseProxyTool() {
        super();
        if (client_pw == null) {
            client_pw = config.getConfigClass(CLIENT_PROGRAM_ID, "sharedSecret");
        }
        if (ws_url == null) {
            ws_url = config.getConfigClass(CLIENT_PROGRAM_ID, "webServiceURL");
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    private void loginAsTool() {
    	session = new BbContext();
    	session.loginAsTool(CLIENT_PROGRAM_ID, client_pw);
    	courseWS = (CourseWSStub) session.initializeClient(CourseWSStub.class, ws_url);
    	_initializeCourseWS(true);
    }
    
    private void logout(){
    	session.logout();
    }
        
    protected BbCourse[] convert_CourseVOArray_to_BbCourseArray(CourseVO[] course_vos){
    	if(course_vos==null || course_vos.length==0) return null;
		List<BbCourse> bb_courses = new ArrayList<BbCourse>();
		for(CourseVO course : course_vos){
			if(course!=null) bb_courses.add(new BbCourse(course));
		}
		return bb_courses.toArray(new BbCourse[]{});
    }    

    ///////////////////////////////////////////////////////////////////////////
    //  All CourseWS Methods  /////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Associates a course to a term. As of 9.1 SP8, a course can only be 
     * associated to a single term.
     * 
     * @param course_id - ID of the course to add to a term. The course id 
     *                    should be in the form "_nnn_1" where nnn is an 
     *                    integer.
     * @param term_id - ID of the term to add the course to.
     * 
     * @return Returns true if the course is added to the term successfully,
     *         false otherwise.
     *         
     * @since 9.1sp8 (version=2)
     */
    protected boolean addCourseToTerm(String course_id, String term_id) {
        loginAsTool();
    	AddCourseToTermResponse response = null;
        AddCourseToTerm add = new AddCourseToTerm();
        add.setCourseID(course_id);
    	add.setTermID(term_id);
    	try {
			response = courseWS.addCourseToTerm(add);
        } catch (RemoteException e) {
            logger.error("addCourseToTerm() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return false;
        }
        logout();
		if(response==null){
        	logger.warn("addCourseToTerm() returned null.");
        	return false;
        }
        return response.get_return();
    }

    /**
     * Changes the batch UID of a course.
     * 
     * @param old_batch_uid - the old batch UID of a course.
     * @param new_batch_uid - the new batch UID to set.
     * 
     * @return Returns true if successful, otherwise false.
     */
    protected boolean changeCourseBatchUid(String old_batch_uid, String new_batch_uid) {
    	loginAsTool();
    	ChangeCourseBatchUidResponse response = null;
    	ChangeCourseBatchUid change = new ChangeCourseBatchUid();
        change.setNewBatchUid(new_batch_uid);
        change.setOldBatchUid(old_batch_uid);
    	try {
			response = courseWS.changeCourseBatchUid(change);
        } catch (RemoteException e) {
            logger.error("changeCourseBatchUid() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return false;
        }
        logout();
		if(response==null){
        	logger.warn("changeCourseBatchUid() returned null.");
        	return false;
        }
        return response.get_return();
    }

    /**
     * Changes a course category's external batch UID.
     * 
     * @param category_id - the id of the category to be changed.
     * @param new_batch_uid - the new category batch uid.
     * 
     * @return Returns true if the batch UID was changed, otherwise false.
     */
    protected boolean changeCourseCategoryBatchUid(String category_id, String new_batch_uid) {
    	loginAsTool();
    	ChangeCourseCategoryBatchUidResponse response = null;
    	ChangeCourseCategoryBatchUid change = new ChangeCourseCategoryBatchUid();
    	change.setCategoryId(category_id);
		change.setNewBatchUid(new_batch_uid);
		try {
			response = courseWS.changeCourseCategoryBatchUid(change);
        } catch (RemoteException e) {
            logger.error("changeCourseCategoryBatchUid() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return false;
        }
        logout();
		if(response==null){
        	logger.warn("changeCourseCategoryBatchUid() returned null.");
        	return false;
        }
        return response.get_return();
    }

    /**
     * Changes a Course's data source ID to a new one.
     * 
     * @param course_id - The ID of the Course. The Course ID should be in the 
     *                    form "_nnn_1" where nnn is an integer (PK1 ID value).
     * @param new_data_source_id - The new data source ID to set.
     * 
     * @return Returns true if data source ID is changed, otherwise false.
     */
    protected boolean changeCourseDataSourceId(String course_id, String new_data_source_id) {
    	loginAsTool();
    	ChangeCourseDataSourceIdResponse response = null;
    	ChangeCourseDataSourceId change = new ChangeCourseDataSourceId();
    	change.setCourseId(course_id);
    	change.setNewDataSourceId(new_data_source_id);
    	try {
			response = courseWS.changeCourseDataSourceId(change);
        } catch (RemoteException e) {
            logger.error("changeCourseDataSourceId() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return false;
        }
        logout();
		if(response==null){
        	logger.warn("changeCourseDataSourceId() returned null.");
        	return false;
        }
        return response.get_return();
    }

    /**
     * Changes the batch UID of an organization.
     * 
     * @param old_batch_uid - the old batch UID of the organization.
     * @param new_batch_uid - the new batch UID to set.
     * 
     * @return Returns true if successful, otherwise false.
     */
    protected boolean changeOrgBatchUid(String old_batch_uid, String new_batch_uid) {
    	loginAsTool();
    	ChangeOrgBatchUidResponse response = null;
    	ChangeOrgBatchUid change = new ChangeOrgBatchUid();
        change.setNewBatchUid(new_batch_uid);
        change.setOldBatchUid(old_batch_uid);
    	try {
			response = courseWS.changeOrgBatchUid(change);
        } catch (RemoteException e) {
            logger.error("changeOrgBatchUid() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return false;
        }
        logout();
		if(response==null){
        	logger.warn("changeOrgBatchUid() returned null.");
        	return false;
        }
        return response.get_return();
    }

    /**
     * Changes a organization category's external Batch UID.
     * 
     * @param category_id - the old batch UID of the category.
     * @param new_batch_uid - the new batch UID to set.
     * 
     * @return Returns true if the Batch UID was changed, otherwise false.
     */
    protected boolean changeOrgCategoryBatchUid(String category_id, String new_batch_uid) {
    	loginAsTool();
    	ChangeOrgCategoryBatchUidResponse response = null;
    	ChangeOrgCategoryBatchUid change = new ChangeOrgCategoryBatchUid();
    	change.setCategoryId(category_id);
		change.setNewBatchUid(new_batch_uid);
		try {
			response = courseWS.changeOrgCategoryBatchUid(change);
        } catch (RemoteException e) {
            logger.error("changeOrgCategoryBatchUid() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return false;
        }
        logout();
		if(response==null){
        	logger.warn("changeOrgCategoryBatchUid() returned null.");
        	return false;
        }
        return response.get_return();
    }

    /**
     * Changes an organization's data source ID to a new one.
     * 
     * @param course_id - The ID of the course. The course id should be in the 
     *                    form "_nnn_1" where nnn is an integer (PK1 ID value).
     * @param new_dataSource_id - The new data source ID to set.
     * 
     * @return Returns true if data source ID is changed, otherwise false.
     */
    protected boolean changeOrgDataSourceId(String org_id, String new_data_source_id) {
    	loginAsTool();
    	ChangeOrgDataSourceIdResponse response = null;
    	ChangeOrgDataSourceId change = new ChangeOrgDataSourceId();
    	change.setCourseId(org_id);
    	change.setNewDataSourceId(new_data_source_id);
    	try {
			response = courseWS.changeOrgDataSourceId(change);
        } catch (RemoteException e) {
            logger.error("changeOrgDataSourceId() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return false;
        }
        logout();
		if(response==null){
        	logger.warn("changeOrgDataSourceId() returned null.");
        	return false;
        }
        return response.get_return();
    }

    /**
     * Creates a new course in the Academic Suite.
     * 
     * @param course_vo - the course to create.
     * 
     * @return Returns the ID of the created Course.
     */
    protected String createCourse(CourseVO course_vo) {
    	loginAsTool();
    	CreateCourseResponse response = null;
    	CreateCourse create = new CreateCourse();
    	create.setC(course_vo);
    	try {
			response = courseWS.createCourse(create);
        } catch (RemoteException e) {
            logger.error("createCourse() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("createCourse() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Creates a new organization in the Academic Suite.
     * 
     * @param org_vo - the organization to create.
     * 
     * @return Returns the ID of the created Organization.
     */
    protected String createOrg(CourseVO org_vo) {
    	loginAsTool();
    	CreateOrgResponse response = null;
    	CreateOrg create = new CreateOrg();
    	create.setC(org_vo);
    	try {
			response = courseWS.createOrg(create);
        } catch (RemoteException e) {
            logger.error("createOrg() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("createOrg() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Deletes a Cartridge by ID.
     * 
     * @param cartridge_id - the ID of the cartridge to be deleted.  The ID 
     *                     should be in the form "_nnn_1" where nnn is an 
     *                     integer (PK1 ID value).
     * 
     * @return Returns the ID of the Cartridge if deleted, null otherwise.
     */
    protected String deleteCartridge(String cartridge_id) {
    	loginAsTool();
        DeleteCartridgeResponse response = null;
        DeleteCartridge delete = new DeleteCartridge();
        delete.setCartridgeId(cartridge_id);
        try {
			response = courseWS.deleteCartridge(delete);
        } catch (RemoteException e) {
            logger.error("deleteCartridge() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("deleteCartridge() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Deletes the courses specified in the course_ids array parameter.
     * 
     * @param course_ids - an array of course IDs to delete.  The course IDs 
     *                     should be in the form "_nnn_1" where nnn is an 
     *                     integer (PK1 ID value).
     *                     
     * @return Returns a list of IDs representing the Courses that were 
     *         deleted.
     */
    protected String[] deleteCourse(String[] course_ids) {
    	loginAsTool();
    	DeleteCourseResponse response = null;
        DeleteCourse delete = new DeleteCourse();
        delete.setIds(course_ids);
        try {
            response = courseWS.deleteCourse(delete);
        } catch (RemoteException e) {
            logger.error("deleteCourse() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("deleteCourse() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Deletes the course categories specified in the category_ids array 
     * parameter.
     * 
     * @param category_ids - an array of course category IDs to delete.  The 
     *                       course category IDs should be in the form "_nnn_1"
     *                       where nnn is an integer (PK1 ID value).
     *                     
     * @return Returns a list of IDs representing the Course Categories that 
     *         were deleted.
     */
    protected String[] deleteCourseCategory(String[] categoryIds) {
    	loginAsTool();
    	DeleteCourseCategoryResponse response = null;
    	DeleteCourseCategory delete = new DeleteCourseCategory();
        delete.setCategoryIds(categoryIds);
        try {
            response = courseWS.deleteCourseCategory(delete);
        } catch (RemoteException e) {
            logger.error("deleteCourseCategory() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("deleteCourseCategory() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Deletes the course category memberships specified in the membership_ids
     * array parameter.
     * 
     * @param membership_ids - the IDs of the course category memberships to 
     *                         delete.  The IDs should be in the form "_nnn_1" 
     *                         where nnn is an integer (the PK1 ID value).
     *                         
     * @return Returns a list of IDs representing the CourseCategoryMemberships
     *         that were deleted.
     */
    protected String[] deleteCourseCategoryMembership(String[] membership_ids) {
    	loginAsTool();
    	DeleteCourseCategoryMembershipResponse response = null;
    	DeleteCourseCategoryMembership delete = new DeleteCourseCategoryMembership();
        delete.setCategoryMembershipIds(membership_ids);
        try {
            response = courseWS.deleteCourseCategoryMembership(delete);
        } catch (RemoteException e) {
            logger.error("deleteCourseCategoryMembership() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("deleteCourseCategoryMembership() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Deletes groups in a course by group ID.
     * 
     * @param course_id - the Course which the group lives. The Course ID 
     *                    should be in the form "_nnn_1" where nnn is an 
     *                    integer (the PK1 ID value).
     * @param group_ids - the IDs of the groups to be deleted.  IDs should be
     *                    in the form "_nnn_1" where nnn is an integer (the PK1
     *                    ID value).
     *                    
     * @return Returns an array of the Group IDs actually deleted.
     */
    protected String[] deleteGroup(String course_id, String[] group_ids) {
    	loginAsTool();
    	DeleteGroupResponse response = null;
    	DeleteGroup delete = new DeleteGroup();
        delete.setCourseId(course_id);
        delete.setIds(group_ids);
        try {
            response = courseWS.deleteGroup(delete);
        } catch (RemoteException e) {
            logger.error("deleteGroup() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("deleteGroup() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Deletes the Organizations specified in the org_ids array parameter.
     * 
     * @param org_ids - an array of organization IDs to delete.  The IDs 
     *                  should be in the form "_nnn_1" where nnn is an integer
     *                  (PK1 ID value).
     *                     
     * @return Returns a list of IDs representing the Organizations that were 
     *         deleted.
     */
    protected String[] deleteOrg(String[] org_ids) {
    	loginAsTool();
    	DeleteOrgResponse response = null;
        DeleteOrg delete = new DeleteOrg();
        delete.setIds(org_ids);
        try {
            response = courseWS.deleteOrg(delete);
        } catch (RemoteException e) {
            logger.error("deleteOrg() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("deleteOrg() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Deletes the organization categories specified in the category_ids array 
     * parameter.
     * 
     * @param category_ids - an array of organization category IDs to delete.  
     *                       The category IDs should be in the form "_nnn_1"
     *                       where nnn is an integer (PK1 ID value).
     *                     
     * @return Returns a list of IDs representing the organization categories 
     *         that were deleted.
     */
    protected String[] deleteOrgCategory(String[] category_ids) {
    	loginAsTool();
    	DeleteOrgCategoryResponse response = null;
    	DeleteOrgCategory delete = new DeleteOrgCategory();
        delete.setCategoryIds(category_ids);
        try {
            response = courseWS.deleteOrgCategory(delete);
        } catch (RemoteException e) {
            logger.error("deleteOrgCategory() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("deleteOrgCategory() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Deletes the organization category memberships specified in the 
     * membership_ids array parameter.
     * 
     * @param membership_ids - the IDs of the organization category memberships
     *                         to delete.  The IDs should be in the form 
     *                         "_nnn_1" where nnn is an integer (the PK1 ID 
     *                         value).
     *                         
     * @return Returns a list of IDs representing the OrgCategoryMemberships
     *         that were deleted.
     */
    protected String[] deleteOrgCategoryMembership(String[] membership_ids) {
    	loginAsTool();
    	DeleteOrgCategoryMembershipResponse response = null;
    	DeleteOrgCategoryMembership delete = new DeleteOrgCategoryMembership();
        delete.setCategoryMembershipIds(membership_ids);
        try {
            response = courseWS.deleteOrgCategoryMembership(delete);
        } catch (RemoteException e) {
            logger.error("deleteOrgCategoryMembership() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("deleteOrgCategoryMembership() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Deletes staff info (aka contacts) in a course using the given IDs.
     * 
     * @param course_id- The course that contains the staff info/contacts. The 
     *                   course ID should be in the form "_nnn_1" where nnn is 
     *                   an integer.
     * @param staff_info_ids - the staff info IDs to be deleted The IDs 
     *                         should be in the form "_nnn_1" where nnn is an 
     *                         integer.
     *                         
     * @return Returns a list of IDs actually deleted.
     */
    protected String[] deleteStaffInfo(String course_id, String[] staff_info_ids) {
    	loginAsTool();
    	DeleteStaffInfoResponse response = null;
    	DeleteStaffInfo delete = new DeleteStaffInfo();
    	delete.setCourseId(course_id);
    	delete.setStaffInfoIds(staff_info_ids);
    	try {
            response = courseWS.deleteStaffInfo(delete);
        } catch (RemoteException e) {
            logger.error("deleteStaffInfo() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("deleteStaffInfo() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Deletes a term by ID.
     * 
     * @param term_id - the ID of the term to delete.  The term ID should be in
     *                  the form "_nnn_1" where nnn is an integer.
     *                  
     * @return Returns true if the term is deleted successfully, false
     *         otherwise.
     *         
     * @since 9.1sp8 (version=2)
     */
    protected boolean deleteTerm(String term_id) {
    	loginAsTool();
    	DeleteTermResponse response = null;
        DeleteTerm delete = new DeleteTerm();
        delete.setTermID(term_id);
        try {
            response = courseWS.deleteTerm(delete);
        } catch (RemoteException e) {
            logger.error("deleteTerm() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return false;
        }
        logout();
		if(response==null){
        	logger.warn("deleteTerm() returned null.");
        	return false;
        }
        return response.get_return();
    }

    /**
     * Load all available group tools for the given course or organization.
     * 
     * @param course_id - the course or organization that contains the 
     *                    group(s). The ID should be in the form "_nnn_1" where
     *                    nnn is an integer.
     *                    
     * @return Returns an array of group tool names.
     */
    protected String[] getAvailableGroupTools(String course_id) {
    	loginAsTool();
    	GetAvailableGroupToolsResponse response = null;
    	GetAvailableGroupTools get = new GetAvailableGroupTools();
    	get.setCourseId(course_id);
        try {
            response = courseWS.getAvailableGroupTools(get);
        } catch (RemoteException e) {
            logger.error("getAvailableGroupTools() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("getAvailableGroupTools() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Return a Cartridge object associated with the given ID.
     * 
     * @param cartridge_id - the ID of the cartridge. The ID should be in the 
     *                       form "_nnn_1" where nnn is an integer.
     *                       
     * @return Returns a Cartridge.
     */
    protected CartridgeVO getCartridge(String cartridge_id) {
    	loginAsTool();
    	GetCartridgeResponse response= null;
    	GetCartridge get = new GetCartridge();
        get.setCartridgeId(cartridge_id);
    	
    	try {
            response = courseWS.getCartridge(get);
        } catch (RemoteException e) {
            logger.error("getCartridge() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("getCartridge() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Retrieves categories from the AS that match the specified category filter
     * parameters.
     * 
     * @param filter - a CategoryFilter.
     *  
     * @return Returns an array of categories matching the filter criteria.
     */
    protected CategoryVO[] getCategories(CategoryFilter filter) {
    	loginAsTool();
    	GetCategories get = new GetCategories();
        get.setFilter(filter);
        GetCategoriesResponse response = null;
        try {
            response = courseWS.getCategories(get);
        } catch (RemoteException e) {
            logger.error("getCategories() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("getCategories() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Retrieves all course classifications in the AS.
     * 
     * @param classification_id_mask - ignored
     * 
     * @return Returns an array of ClassificationVOs.
     */
    protected ClassificationVO[] getClassifications(String classification_id_mask) {
    	loginAsTool();
    	GetClassificationsResponse response = null;
    	GetClassifications get = new GetClassifications();
    	get.setClassificationIdMask(classification_id_mask);
    	try {
            response = courseWS.getClassifications(get);
        } catch (RemoteException e) {
            logger.error("getClassifications() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("getClassifications() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Retrieve course(s) using the given course search criteria.
     * 
     * @param course_filter - contains the course search criteria.
     * 
     * @return Returns an array of CourseVOs matching the search criteria.
     */
    protected CourseVO[] getCourse(CourseFilter course_filter) {
    	loginAsTool();
    	GetCourseResponse response = null;
    	GetCourse get = new GetCourse();
        get.setFilter(course_filter);
        try {
        	//session_extend(60);
        	response = courseWS.getCourse(get);
        } catch (RemoteException e) {
            logger.error("getCourse() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("getCourse() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Retrieve course category membership(s) using a category membership 
     * filter.
     * 
     * @param filter - Defines the search criteria used to retrieve category 
     *                 membership - set filter.templateCategories.available to
     *                 true to get only enabled category memberships, else all
     *                 memberships that match the search criterion are 
     *                 returned.
     *                 
     * @return Returns an array of CourseCategoryMembershipVOs.
     */
    protected CategoryMembershipVO[] getCourseCategoryMembership(CategoryMembershipFilter filter) {
    	loginAsTool();
    	GetCourseCategoryMembership get = new GetCourseCategoryMembership();
        get.setFilter(filter);
        GetCourseCategoryMembershipResponse response = null;
        try {
            response = courseWS.getCourseCategoryMembership(get);
        } catch (RemoteException e) {
            logger.error("getCourseCategoryMembership() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("getCourseCategoryMembership() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Retrieve group(s) from a course or organization using the given search 
     * criteria.
     * 
     * @param course_id - The course/org containing the group(s). Required to 
     *                    check for valid Course and Group membership. The ID 
     *                    should be in the form "_nnn_1" where nnn is an 
     *                    integer (PK1 ID value).
     * @param group filter - contains search criteria to retrieve group(s). Set 
     *                       filter.available to true to get only enabled 
     *                       groups, else all groups that match the search 
     *                       criterion are returned.
     *                       
     * @return Returns an array of GroupVOs.
     */
    protected GroupVO[] getGroup(String course_id, GroupFilter group_filter) {
    	loginAsTool();
    	GetGroupResponse response = null;
    	GetGroup get = new GetGroup();
        get.setFilter(group_filter);
        get.setCourseId(course_id);
        try {
        	response = courseWS.getGroup(get);
        } catch (RemoteException e) {
            logger.error("getGroup() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("getGroup() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Retrieve organization(s) using the given Course search criteria.
     * 
     * @param course_filter - contains the course search criteria. Set 
     * 						  course_filter.available to true to get only 
     * 						  enabled orgs, else all orgs that match the search
     * 						  criterion are returned.
     * 
     * @return Returns an array of CourseVOs for the matching Organizations.
     */
    protected CourseVO[] getOrg(CourseFilter course_filter) {
    	loginAsTool();
    	GetOrgResponse response = null;
    	GetOrg get = new GetOrg();
        get.setFilter(course_filter);
        try {
        	response = courseWS.getOrg(get);
        } catch (RemoteException e) {
            logger.error("getOrg() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("getOrg() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Retrieves organization category membership(s) with given category 
     * membership filter.
     * 
     * @param cm_filter - defines the search criteria to retrieve category 
     *                    membership - set filter.templateCategories.available 
     *                    to true to get only enabled category memberships, 
     *                    else all memberships that match the search criterion 
     *                    are returned.
     *                    
     * @return Returns an array of CategoryMembershipVOs matching the filter 
     *         criteria.
     */
    protected CategoryMembershipVO[] getOrgCategoryMembership(CategoryMembershipFilter cm_filter) {
    	loginAsTool();
    	GetOrgCategoryMembershipResponse response = null;
    	GetOrgCategoryMembership get = new GetOrgCategoryMembership();
        get.setFilter(cm_filter);
        try {
        	response = courseWS.getOrgCategoryMembership(get);
        } catch (RemoteException e) {
            logger.error("getOrgCategoryMembership() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("getOrgCategoryMembership() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Returns the current version of this web service on the server.
     * 
     * @param unused - this is an optional parameter put here to make the 
     *                 generation of .net client applications from the wsdl 
     *                 'cleaner' (0-argument methods do not generate clean 
     *                 stubs and are much harder to have the same method name 
     *                 across multiple Web Services in the same .net client).
     *                 
     * @return Returns the current version of this web service on the server.
     * 
     * @since 1
     */
    protected VersionVO getServerVersion(VersionVO unused) {
    	loginAsTool();
    	GetServerVersionResponse response = null;
    	GetServerVersion version = new GetServerVersion();
        try {
            response = courseWS.getServerVersion(version);
        } catch (RemoteException e) {
            logger.error("getServerVersion() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("getServerVersion() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Retrieves all staff (contact) information contained a the given course.
     * 
     * @param course_id - the course containing the staff info. The course ID 
     *                    should be in the form "_nnn_1" where nnn is an 
     *                    integer (PK1 ID value).
     *                    
     * @return Returns array of StaffInfoVOs.
     */
    protected StaffInfoVO[] getStaffInfo(String course_id) {
    	loginAsTool();
    	GetStaffInfoResponse response = null;
    	GetStaffInfo get = new GetStaffInfo();
    	get.setCourseId(course_id);
        try {
            response = courseWS.getStaffInfo(get);
        } catch (RemoteException e) {
            logger.error("getStaffInfo() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("getStaffInfo() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Sets the client version to the appropriate version for this web service.
     * With each release of this web service we will implement a new 
     * initializeVersionXXX method.
     * 
     * @param ignore - this is an optional parameter put here to make the 
     *                 generation of .net client applications from the wsdl 
     *                 'cleaner' (0-argument methods do not generate clean 
     *                 stubs and are much harder to have the same method name 
     *                 across multiple Web Services in the same .net client).
     *                 
     * @return Returns true to indicate that the session has been initialized.
     */
    protected boolean initializeCourseWS(boolean ignore) {
    	loginAsTool();
        boolean response = _initializeCourseWS(ignore);
        logout();
        return response;
    }
    
    private boolean _initializeCourseWS(boolean ignore) {
    	InitializeCourseWSResponse response = null;
    	InitializeCourseWS init = new InitializeCourseWS();
        init.setIgnore(true);
        try {
            response = courseWS.initializeCourseWS(init);
        } catch (RemoteException e) {
            logger.error("initializeCourseWS() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return false;
        }
		if(response==null){
        	logger.warn("initializeCourseWS() returned null.");
        	return false;
        }
        return response.get_return();
    }

    /**
     * Load all courses associated with the term identified by term_id.
     * 
     * @param term_id - ID of the term to load the list of associated courses.
     *                  The term ID should be in the form "_nnn_1" where nnn is
     *                  an integer (PK1 ID value).
     * 
     * @return Returns an array of CourseVOs associated with the term 
     *         identified by term_id.
     *         
     * @since 9.1sp8 (version=2)        
     */
    protected CourseVO[] loadCoursesInTerm(String term_id) {
    	loginAsTool();
    	LoadCoursesInTermResponse response = null;
    	LoadCoursesInTerm load = new LoadCoursesInTerm();
        load.setTermID(term_id);
        try {
            response = courseWS.loadCoursesInTerm(load);
        } catch (RemoteException e) {
            logger.error("loadCoursesInTerm() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("loadCoursesInTerm() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Load a term by its ID.
     * 
     * @param term_id - the ID of the term to load. The term ID should be in 
     *                  the form "_nnn_1" where nnn is an integer (PK1 ID 
     *                  value).
     * 
     * @return Returns the specified Term.
     * 
     * @since 9.1sp8 (version=2)
     */
    protected TermVO loadTerm(String term_id) {
    	loginAsTool();
    	LoadTermResponse response = null;
    	LoadTerm load = new LoadTerm();
        load.setTermID(term_id);
        try {
            response = courseWS.loadTerm(load);
        } catch (RemoteException e) {
            logger.error("loadTerm() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("loadTerm() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Load the term associated to the given course ID.
     * 
     * @param course_id - The ID of the Course to load the Term for. The Course 
     *                    ID should be in the form "_nnn_1" where nnn is an 
     *                    integer (PK1 ID value).
     *                    
     * @return Returns the specified Term.
     * 
     * @since 9.1sp8 (version=2)
     */
    protected TermVO loadTermByCourseId(String course_id) {
    	loginAsTool();
    	LoadTermByCourseIdResponse response = null;
    	LoadTermByCourseId load = new LoadTermByCourseId();
        load.setCourseID(course_id);
        try {
            response = courseWS.loadTermByCourseId(load);
        } catch (RemoteException e) {
            logger.error("loadTermByCourseId() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("loadTermByCourseId() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Load all Terms in the system, optionally excluding the unavailable ones.
     * 
     * @param only_available - if true, only available Terms are returned. If 
     *                         false, returns all Terms.
     *                         
     * @return Returns an array of TermVOs.
     * 
     * @since 9.1sp8 (version=2)
     */
    protected TermVO[] loadTerms(boolean only_available) {
    	loginAsTool();
    	LoadTermsResponse response = null;
    	LoadTerms load = new LoadTerms();
        load.setOnlyAvailable(only_available);
        try {
            response = courseWS.loadTerms(load);
        } catch (RemoteException e) {
            logger.error("loadTerms() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("loadTerms() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Load all Terms in the system by name (exact match).
     * 
     * @param name - The name of the term to load (names are not unique so you 
     *               may get multiple terms back for the same name).
     *               
     * @return Returns an array of TermVOs.
     * 
     * @since 9.1sp8 (version=2)
     */
    protected TermVO[] loadTermsByName(String name) {
       	loginAsTool();
    	LoadTermsByNameResponse response = null;
    	LoadTermsByName load = new LoadTermsByName();
        load.setName(name);
        try {
            response = courseWS.loadTermsByName(load);
        } catch (RemoteException e) {
            logger.error("loadTermsByName() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("loadTermsByName() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Removes a course from it's Term.
     * 
     * @param course_id - ID of the course to remove from a term. The course ID
     *                    should be in the form "_nnn_1" where nnn is an 
     *                    integer (PK1 ID value).
     *                    
     * @return Returns true if the course is removed from the Term 
     *         successfully, false otherwise.
     *         
     * @since 9.1sp8 (version=2)
     */
    protected boolean removeCourseFromTerm(String course_id) {
       	loginAsTool();
       	RemoveCourseFromTermResponse response = null;
       	RemoveCourseFromTerm remove = new RemoveCourseFromTerm();
        remove.setCourseID(course_id);
        try {
            response = courseWS.removeCourseFromTerm(remove);
        } catch (RemoteException e) {
            logger.error("removeCourseFromTerm() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return false;
        }
        logout();
		if(response==null){
        	logger.warn("removeCourseFromTerm() returned null.");
        	return false;
        }
        return response.get_return();
    }

    /**
     * Save the given Cartridge object.
     * 
     * @param cartridge_vo - the Cartridge to be saved.
     * 
     * @return Returns the ID of the Cartridge created, null otherwise.
     */
    protected String saveCartridge(CartridgeVO cartridge_vo) {
       	loginAsTool();
       	SaveCartridgeResponse response = null;
       	SaveCartridge save = new SaveCartridge();
        save.setVo(cartridge_vo);
        try {
            response = courseWS.saveCartridge(save);
        } catch (RemoteException e) {
            logger.error("saveCartridge() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("removeCourseFromTerm() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Save the given Course (create or update a Course or Organization).
     * 
     * @param course_vo - the Course or Organization to save
     * 
     * @return Returns the ID of the Course or Organization if saved 
     *         successfully, otherwise null.
     */
    protected String saveCourse(CourseVO course_vo) {
        loginAsTool();
        SaveCourseResponse response = null;
        SaveCourse save = new SaveCourse();
        save.setC(course_vo);
        try {
            response = courseWS.saveCourse(save);
        } catch (RemoteException e) {
            logger.error("saveCourse() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("saveCourse() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Save a Course Category.
     * 
     * @param category_vo - the Category to be saved
     * 
     * @return Returns the ID of the Category saved if successful, else null.
     */
    protected String saveCourseCategory(CategoryVO category_vo) {
    	loginAsTool();
    	SaveCourseCategoryResponse response = null;
    	SaveCourseCategory save = new SaveCourseCategory();
        save.setAdminCategory(category_vo);
        try {
            response = courseWS.saveCourseCategory(save);
        } catch (RemoteException e) {
            logger.error("saveCourseCategory() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("saveCourseCategory() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Save the given course category membership/association.
     * 
     * @param membership - the course category membership to be saved.
     * 
     * @return Returns the ID of the course category membership saved if 
     *         successful, else null.
     */
    protected String saveCourseCategoryMembership(CategoryMembershipVO membership) {
    	loginAsTool();
    	SaveCourseCategoryMembershipResponse response = null;
    	SaveCourseCategoryMembership save = new SaveCourseCategoryMembership();
        save.setMembership(membership);
        try {
            response = courseWS.saveCourseCategoryMembership(save);
        } catch (RemoteException e) {
            logger.error("saveCourseCategoryMembership() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("saveCourseCategoryMembership() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Save a group within a course or organization.
     * 
     * @param course_id - The course or organization which will contain the 
     *                    group. The course ID should be in the form "_nnn_1" 
     *                    where nnn is an integer (created by Blackboard).
     * @param group_vo - the group that will be saved to the containing course 
     *                   or organization.
     *                   
     * @return  Returns true if successfully saved. Otherwise, returns false.
     */
    protected String saveGroup(String course_id, GroupVO group_vo) {
    	loginAsTool();
    	SaveGroupResponse response = null;
    	SaveGroup save = new SaveGroup();
        save.setCourseId(course_id);
        save.setVo(group_vo);
        try {
            response = courseWS.saveGroup(save);
        } catch (RemoteException e) {
            logger.error("saveGroup() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("saveGroup() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Save the given organization category.
     * 
     * @param category - the category to be saved.
     * 
     * @return Returns the ID of the category saved if successful, else null.
     */
    protected String saveOrgCategory(CategoryVO category_vo) {
    	loginAsTool();
    	SaveOrgCategoryResponse response = null;
    	SaveOrgCategory save = new SaveOrgCategory();
        save.setAdminCategory(category_vo);
        try {
            response = courseWS.saveOrgCategory(save);
        } catch (RemoteException e) {
            logger.error("saveOrgCategory() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("saveOrgCategory() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Save the given organization category membership/association.
     * 
     * @param membership - the organization category membership to be saved.
     * 
     * @return Returns the ID of the organization category membership saved if
     *         successful, else null.
     */
    protected String saveOrgCategoryMembership(CategoryMembershipVO cm_vo) {
    	loginAsTool();
    	SaveOrgCategoryMembershipResponse response = null;
    	SaveOrgCategoryMembership save = new SaveOrgCategoryMembership();
        save.setMembership(cm_vo);
        try {
            response = courseWS.saveOrgCategoryMembership(save);
        } catch (RemoteException e) {
            logger.error("saveOrgCategoryMembership() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("saveOrgCategoryMembership() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Saves a staff info (contact) object within the given course.
     * 
     * @param course_id - the course in which the staff info will be saved. The
     *                    course ID should be in the form "_nnn_1" where nnn is
     *                    an integer (PK1 ID value).
     * @param staff_info_vo - the StaffInfoVO to be saved.
     * 
     * @return Returns the ID of the staff info (contact) object created.
     */
    protected String saveStaffInfo(String course_id, StaffInfoVO staff_info_vo) {
    	loginAsTool();
    	SaveStaffInfoResponse response = null;
    	SaveStaffInfo save = new SaveStaffInfo();
        save.setCourseId(course_id);
        save.setVo(staff_info_vo);
        try {
            response = courseWS.saveStaffInfo(save);
        } catch (RemoteException e) {
            logger.error("saveStaffInfo() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("saveStaffInfo() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Saves a term; either new or updated (if term.id is set then it is 
     * treated as an update).
     * 
     * @param term_vo - the term to save.
     * 
     * @return Returns the ID value of the saved Term.
     */
    protected String saveTerm(TermVO term_vo) {
    	loginAsTool();
    	SaveTermResponse response = null;
    	SaveTerm save = new SaveTerm();
        save.setTermVo(term_vo);
        try {
            response = courseWS.saveTerm(save);
        } catch (RemoteException e) {
            logger.error("saveTerm() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("saveTerm() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Set the banner image for a Course.
     * 
     * @param course_id - the Course to set the image in. The Course ID should 
     *                    be in the form "_nnn_1" where nnn is an integer (PK1)
     *                    ID value).
     * @param remove_only - if true then just remove the current image: ignore 
     *                      filename.
     * @param file_name - the name of the image file.
     * @param file_contents - the contents of the image file.
     * 
     * @return Returns true on success, false on failure.
     */
    protected boolean setCourseBannerImage(String course_id, boolean remove_only, String file_name, DataHandler file_contents) {
    	loginAsTool();
    	SetCourseBannerImageResponse response = null;
    	SetCourseBannerImage save = new SetCourseBannerImage();
        save.setCourseId(course_id);
        save.setFileContents(file_contents);
        save.setFileName(file_name);
        save.setRemoveOnly(remove_only);
        try {
            response = courseWS.setCourseBannerImage(save);
        } catch (RemoteException e) {
            logger.error("setCourseBannerImage() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return false;
        }
        logout();
		if(response==null){
        	logger.warn("setCourseBannerImage() returned null.");
        	return false;
        }
        return response.get_return();

    }

    /**
     * Updates a course.
     * 
     * @param course_vo - the course to update.
     * 
     * @return - Returns the id of the updated Course.
     */
    protected String updateCourse(CourseVO course_vo) {
    	loginAsTool();
    	UpdateCourseResponse response = null;
    	UpdateCourse update = new UpdateCourse();
        update.setC(course_vo);
        try {
            response = courseWS.updateCourse(update);
        } catch (RemoteException e) {
            logger.error("updateCourse() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("updateCourse() returned null.");
        	return null;
        }
        return response.get_return();
    }

    /**
     * Updates an organization.
     * 
     * @param org_vo - the organization to update.
     * 
     * @return - Returns the ID of the updated organization.
     */
    protected String updateOrg(CourseVO org_vo) {
    	loginAsTool();
    	UpdateOrgResponse response = null;
    	UpdateOrg update = new UpdateOrg();
        update.setC(org_vo);
        try {
            response = courseWS.updateOrg(update);
        } catch (RemoteException e) {
            logger.error("updateOrg() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("updateOrg() returned null.");
        	return null;
        }
        return response.get_return();
    }  
}
