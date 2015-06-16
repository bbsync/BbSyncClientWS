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


public class BbOrgCategory extends AbstractCategory {
    private static final long serialVersionUID = 3333000014141414L;
    
    //field values from blackboard.ws.course.CourseWSConstants 
    private static final int FILTER_TYPE_GET_ALL_ORG_CATEGORY      = 1;
    private static final int FILTER_TYPE_GET_CATEGORY_BY_ID        = 2;
    private static final int FILTER_TYPE_GET_CATEGORY_BY_PARENT_ID = 3;

	///////////////////////////////////////////////////////////////////////////
	//  Constructors  /////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

    public BbOrgCategory() {
        _category_vo = new CategoryVO();
        setOrganization(true);
    }

    private BbOrgCategory(CategoryVO category_vo) {
    	_category_vo = category_vo;
    	setOrganization(true);
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
		return super.saveOrgCategory(this.getVO());
	}

	/**
	 * Gets a BbOrgCategory by its ID.  The Category's ID field must be set. If
	 *  
	 * @return Returns a BbOrgCategory if successful, else returns null.
	 */
	public Object retrieve() {
		return getOrgCategoryById(this.getId());
	}

	/**
	 * Deletes this OrgCategory from the AS.  The Category's ID field must be set.
	 * @return Returns true if successful, else returns false.
	 */
	public boolean delete() {
		if(this.getId()==null) return false;
		String[] result = super.deleteOrgCategory(new String[]{this.getId()});
		if(result==null || result[0]==null) return false;
		if(this.getId().equals(result[0])) return true;
		return false;
	}
	
	///////////////////////////////////////////////////////////////////////////
	//  Implemented ProxyTool Methods  ////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
        
	public boolean changeOrgCategoryBatchUid(String category_id, String new_batch_uid) {
        return super.changeOrgCategoryBatchUid(category_id, new_batch_uid);
	}
	
	public String[] deleteOrgCategory(String[] category_ids) {
		return super.deleteOrgCategory(category_ids);
	}
	
	/**
     * Save organization category
     * @param bb_category - the category to be saved
     * @return If successful, returns the PK1 ID of the Category saved,  
     *         else returns null.
     */
    public String saveOrgCategory(BbOrgCategory bb_category){
    	CategoryVO category = super.getVO();
    	if(!category.getOrganization()) category.setOrganization(true);
    	return super.saveOrgCategory(category);
    }
    
    public BbOrgCategory[] getAllOrganizationCategories(){
    	CategoryFilter filter = new CategoryFilter();
    	filter.setFilterType(FILTER_TYPE_GET_ALL_ORG_CATEGORY);
    	filter.setTemplateCategories(new CategoryVO[]{null});
    	CategoryVO[] category_vos = super.getCategories(filter);
    	if(category_vos==null || category_vos.length<1) return null;
    	return convert_CategoryVOArray_to_BbOrgCategoryArray(category_vos);
    }
    
    public BbOrgCategory getOrgCategoryById(String category_id){
    	if(category_id==null) return null;
    	CategoryVO template = new CategoryVO();
    	template.setId(category_id);
    	template.setOrganization(true);
    	CategoryFilter filter = new CategoryFilter();
    	filter.setFilterType(FILTER_TYPE_GET_CATEGORY_BY_ID);
    	filter.setTemplateCategories(new CategoryVO[]{template});
    	CategoryVO[] category_vos = super.getCategories(filter);
    	if(category_vos==null || category_vos[0]==null) return null;
    	return new BbOrgCategory(category_vos[0]);
    }
    
    public BbOrgCategory[] getOrgCategoriesByParentId(String parent_category_id){
    	if(parent_category_id==null) return null;
    	CategoryVO template = new CategoryVO();
    	template.setId(parent_category_id);
    	template.setOrganization(true);
    	CategoryFilter filter = new CategoryFilter();
    	filter.setFilterType(FILTER_TYPE_GET_CATEGORY_BY_PARENT_ID);
    	filter.setTemplateCategories(new CategoryVO[]{template});
    	CategoryVO[] category_vos = super.getCategories(filter);
    	if(category_vos==null || category_vos.length<1) return null;
    	return convert_CategoryVOArray_to_BbOrgCategoryArray(category_vos);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    private BbOrgCategory[] convert_CategoryVOArray_to_BbOrgCategoryArray(CategoryVO[] category_vos){
    	if(category_vos==null || category_vos.length<1) return null;
		List<BbOrgCategory> bbo_cats = new ArrayList<BbOrgCategory>();
		for(CategoryVO category : category_vos){
			if(category!=null && category.getOrganization()) bbo_cats.add(new BbOrgCategory(category));
		}
		return bbo_cats.toArray(new BbOrgCategory[]{});
    } 
}
