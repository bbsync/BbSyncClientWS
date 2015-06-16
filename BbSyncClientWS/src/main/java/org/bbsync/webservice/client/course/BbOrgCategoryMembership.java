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

import org.bbsync.webservice.client.abstracts.AbstractCategoryMembership;
import org.bbsync.webservice.client.generated.CourseWSStub.CategoryMembershipFilter;
import org.bbsync.webservice.client.generated.CourseWSStub.CategoryMembershipVO;


public class BbOrgCategoryMembership extends AbstractCategoryMembership {
    private static final long serialVersionUID = 33333000016161616L;
    
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public BbOrgCategoryMembership() {
    	_cmvo = new CategoryMembershipVO();
    	_cmvo.setOrganization(true);
    }

    private BbOrgCategoryMembership(CategoryMembershipVO cmvo) {
    	_cmvo = cmvo;
    	_cmvo.setOrganization(true);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////
    public Serializable persist() {
    	return super.saveOrgCategoryMembership(_cmvo);
    }
   
    public Object retrieve() {
    	if(this.getId()==null) return null;
    	return getOrgCategoryMembershipById(this.getId());
    }
   
    public boolean delete() {
    	if(this.getId()==null) return false;
		String[] result = deleteOrgCategoryMembership(new String[]{this.getId()});
		if(result==null || result[0]==null) return false;
		if(this.getId().equals(result[0])) return true;
		return false;
    }
        
    ///////////////////////////////////////////////////////////////////////////
    //  Implemented ProxyTool Methods  ////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    
    public String[] deleteOrgCategoryMembership(String[] membership_ids) {
    	return super.deleteOrgCategoryMembership(membership_ids);
    }
    
    public BbOrgCategoryMembership getOrgCategoryMembershipById(String id){
    	if(id==null) return null;
    	CategoryMembershipVO template = new CategoryMembershipVO();
    	template.setId(id);
    	template.setOrganization(true);
    	CategoryMembershipFilter filter = new CategoryMembershipFilter();
    	filter.setFilterType(GET_CATEGORY_MEMBERSHIP_BY_ID);
    	filter.setTemplateCategoryMembership(new CategoryMembershipVO[]{template});
    	CategoryMembershipVO[] cm_vos = super.getOrgCategoryMembership(filter);
    	if(cm_vos==null || cm_vos[0]==null) return null;
    	return new BbOrgCategoryMembership(cm_vos[0]);
    }
    
    public BbOrgCategoryMembership[] getOrgCategoryMembershipsByOrgId(String org_id){
    	if(org_id==null) return null;
    	CategoryMembershipVO template = new CategoryMembershipVO();
    	template.setCourseId(org_id);
    	template.setOrganization(true);
    	CategoryMembershipFilter filter = new CategoryMembershipFilter();
    	filter.setFilterType(GET_CATEGORY_MEMBERSHIP_BY_COURSE_ID);
    	filter.setTemplateCategoryMembership(new CategoryMembershipVO[]{template});
    	CategoryMembershipVO[] cm_vos = super.getOrgCategoryMembership(filter);
    	if(cm_vos==null || cm_vos[0]==null) return null;
    	return convert_CategoryMembershipVOArray_to_BbOrgCategoryMembershipArray(cm_vos);
    }
    
    /**
     * Save an Organization Category Membership.
     * @param cm_vo - the Organization Category membership be saved.
     * @return  If successful, returns the ID of the Organization Category 
     *          Membership saved; else null.
     */
    public String saveOrgCategoryMembership(BbOrgCategoryMembership bb_ocm) {
    	return super.saveOrgCategoryMembership(bb_ocm.getVO());
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Implemented Abstract Methods  /////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////        
    /**
     * @return the Organization ID associated with this Category Membership.
     */
    public String getOrganizationId(){
    	return super.getCourseId();
    }
    
    /**
     * Sets the Organization ID associated with this Category Membership. 
     * 
     * @param org_id - the Organization ID associated with this Category 
     *                 Membership. The Course ID should be generated by 
     *                 Blackboard, in the form "_nnn_1" where nnn is an 
     *                 integer.
     */
    public void setOrganizationId(String org_id){
    	super.setCourseId(org_id);
    }

    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    
    private BbOrgCategoryMembership[] convert_CategoryMembershipVOArray_to_BbOrgCategoryMembershipArray(CategoryMembershipVO[] cm_vos){
    	if(cm_vos==null || cm_vos.length<1) return null;
		List<BbOrgCategoryMembership> bb_ocms = new ArrayList<BbOrgCategoryMembership>();
		for(CategoryMembershipVO cmvo : cm_vos){
			if(cmvo!=null && cmvo.getOrganization()) bb_ocms.add(new BbOrgCategoryMembership(cmvo));
		}
		return bb_ocms.toArray(new BbOrgCategoryMembership[]{});
    }
    
    private CategoryMembershipVO getVO(){
    	return _cmvo;
    }
}
