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

import org.bbsync.webservice.client.abstracts.AbstractCategory;
import org.bbsync.webservice.client.generated.CourseWSStub.CategoryFilter;
import org.bbsync.webservice.client.generated.CourseWSStub.CategoryVO;
import org.bbsync.webservice.client.generated.CourseWSStub.VersionVO;


public class BbCourseCategory extends AbstractCategory {
    private static final long serialVersionUID = 3333000013131313L;
    
    //field values from blackboard.ws.course.CourseWSConstants
    private static final int FILTER_TYPE_GET_ALL_COURSE_CATEGORY   = 0;
    private static final int FILTER_TYPE_GET_CATEGORY_BY_ID        = 2;
    private static final int FILTER_TYPE_GET_CATEGORY_BY_PARENT_ID = 3;

	///////////////////////////////////////////////////////////////////////////
	//  Constructors  /////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

    public BbCourseCategory() {
        _category_vo = new CategoryVO();
    }

    private BbCourseCategory(CategoryVO category_vo) {
        _category_vo = category_vo;
    }
    
	///////////////////////////////////////////////////////////////////////////
	//  Required ClientWebService Methods  ////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////	
	/** 
	 * Saves this Category to the AS.  If this category is an Organization
	 * Category, it will be saved as such.  Otherwise, the category will be
	 * saved as a Course Category 
	 * @return If successful, returns the PK1 ID of the Category saved,  
     *         else returns null.
	 */
	public Serializable persist() {
		return super.saveCourseCategory(this.getVO());
	}

	/**
	 * Gets a BbCategory by its ID.  The Category's ID field must be set. If
	 * The Category is an Organization, the isOrganization() should be true. 
	 * @return Returns a BbCategory object if successful, else returns null.
	 */
	public Object retrieve() {
		return getCourseCategoryById(this.getId());
	}

	/**
	 * Deletes this Category from the AS.  The Category's ID field must be set.
	 * @return Returns true if successful, else returns false.
	 */
	public boolean delete() {
		if(this.getId()==null) return false;
		String[] result = super.deleteCourseCategory(new String[]{this.getId()});
		if(result==null || result[0]==null) return false;
		if(this.getId().equals(result[0])) return true;
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
	
	public boolean changeCourseCategoryBatchUid(String categoryId, String newBatchUid) {
        return super.changeCourseCategoryBatchUid(categoryId, newBatchUid);
	}

	public String[] deleteCourseCategory(String[] categoryIds) {
		return super.deleteCourseCategory(categoryIds);
	}
	
	/**
     * Save course category
     * @param category - the category to be saved
     * @return If successful, returns the PK1 ID of the Category saved,  
     *         else returns null.
     */
    public String saveCourseCategory(BbCourseCategory bb_category){
    	CategoryVO category = bb_category.getVO();
    	if(category.getOrganization()) category.setOrganization(false);
    	return super.saveCourseCategory(category);
    }
    
    public BbCourseCategory[] getAllCourseCategories(){
    	CategoryFilter filter = new CategoryFilter();
    	filter.setFilterType(FILTER_TYPE_GET_ALL_COURSE_CATEGORY);
    	filter.setTemplateCategories(new CategoryVO[]{null});
    	CategoryVO[] category_vos = super.getCategories(filter);
    	if(category_vos==null || category_vos.length<1) return null;
    	return convert_CategoryVOArray_to_BbCourseCategoryArray(category_vos);
    }

    public BbCourseCategory getCourseCategoryById(String category_id){
    	if(category_id==null) return null;
    	CategoryVO template = new CategoryVO();
    	template.setId(category_id);
    	template.setOrganization(false);
    	CategoryFilter filter = new CategoryFilter();
    	filter.setFilterType(FILTER_TYPE_GET_CATEGORY_BY_ID);
    	filter.setTemplateCategories(new CategoryVO[]{template});
    	CategoryVO[] category_vos = super.getCategories(filter);
    	if(category_vos==null || category_vos[0]==null) return null;
    	return new BbCourseCategory(category_vos[0]);
    }
    
    public BbCourseCategory[] getCategoriesByParentId(String parent_category_id){
    	if(parent_category_id==null) return null;
    	CategoryVO template = new CategoryVO();
    	template.setId(parent_category_id);
    	template.setOrganization(false);
    	CategoryFilter filter = new CategoryFilter();
    	filter.setFilterType(FILTER_TYPE_GET_CATEGORY_BY_PARENT_ID);
    	filter.setTemplateCategories(new CategoryVO[]{template});
    	CategoryVO[] category_vos = super.getCategories(filter);
    	if(category_vos==null || category_vos.length<1) return null;
    	return convert_CategoryVOArray_to_BbCourseCategoryArray(category_vos);
    }     
    
	///////////////////////////////////////////////////////////////////////////
	//  Local Methods  ////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
    private BbCourseCategory[] convert_CategoryVOArray_to_BbCourseCategoryArray(CategoryVO[] category_vos){
    	if(category_vos==null || category_vos.length<1) return null;
		List<BbCourseCategory> bbc_cats = new ArrayList<BbCourseCategory>();
		for(CategoryVO category : category_vos){
			if(category!=null && !category.getOrganization()) bbc_cats.add(new BbCourseCategory(category));
		}
		return bbc_cats.toArray(new BbCourseCategory[]{});
    } 
}
