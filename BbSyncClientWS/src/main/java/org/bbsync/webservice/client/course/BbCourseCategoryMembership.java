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


public class BbCourseCategoryMembership extends AbstractCategoryMembership {
    private static final long serialVersionUID = 3333000015151515L;
    
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public BbCourseCategoryMembership() {
    	_cmvo = new CategoryMembershipVO();
    	_cmvo.setOrganization(false);
    }

    private BbCourseCategoryMembership(CategoryMembershipVO cmvo) {
    	_cmvo = cmvo;
    	_cmvo.setOrganization(false);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////
    public Serializable persist() {
    	return super.saveCourseCategoryMembership(_cmvo);
    }
   
    public Object retrieve() {
    	if(this.getId()==null) return null;
    	return getCourseCategoryMembershipById(this.getId());
    }
    
    public boolean delete() {
    	if(this.getId()==null) return false;
		String[] result = deleteCourseCategoryMembership(new String[]{this.getId()});
		if(result==null || result[0]==null) return false;
		if(this.getId().equals(result[0])) return true;
		return false;
    }
        
    ///////////////////////////////////////////////////////////////////////////
    //  Implemented ProxyTool Methods  ////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    
    public String[] deleteCourseCategoryMembership(String[] membership_ids) {
    	return super.deleteCourseCategoryMembership(membership_ids);
    }
    
    public BbCourseCategoryMembership getCourseCategoryMembershipById(String membership_id){
    	if(membership_id==null) return null;
    	CategoryMembershipVO template = new CategoryMembershipVO();
    	template.setId(membership_id);
    	template.setOrganization(false);
    	CategoryMembershipFilter filter = new CategoryMembershipFilter();
    	filter.setFilterType(GET_CATEGORY_MEMBERSHIP_BY_ID);
    	filter.setTemplateCategoryMembership(new CategoryMembershipVO[]{template});
    	CategoryMembershipVO[] cm_vos = super.getCourseCategoryMembership(filter);
    	if(cm_vos==null || cm_vos[0]==null) return null;
    	return new BbCourseCategoryMembership(cm_vos[0]);
    }
    
    public BbCourseCategoryMembership[] getCourseCategoryMembershipsByCourseId(String course_id){
    	if(course_id==null) return null;
    	CategoryMembershipVO template = new CategoryMembershipVO();
    	template.setCourseId(course_id);
    	template.setOrganization(false);
    	CategoryMembershipFilter filter = new CategoryMembershipFilter();
    	filter.setFilterType(GET_CATEGORY_MEMBERSHIP_BY_COURSE_ID);
    	filter.setTemplateCategoryMembership(new CategoryMembershipVO[]{template});
    	CategoryMembershipVO[] cm_vos = super.getCourseCategoryMembership(filter);
    	if(cm_vos==null || cm_vos[0]==null) return null;
    	return convert_CategoryMembershipVOArray_to_BbCourseCategoryMembershipArray(cm_vos);
    }
    
    /**
     * Save an Organization Category Membership.
     * @param cm_vo - the Organization Category membership be saved.
     * @return  If successful, returns the ID of the Organization Category 
     *          Membership saved; else null.
     */
    public String saveCourseCategoryMembership(BbCourseCategoryMembership bb_ccm) {
    	return super.saveCourseCategoryMembership(bb_ccm.getVO());
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Implemented Abstract Methods  /////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////        
    /**
     * @return the Course ID associated with this Category Membership.
     */
    public String getCourseId(){
    	return super.getCourseId();
    }

    /**
     * Sets the Course ID associated with this Category Membership.  Setting
     * this parameter will also setOrganization(false).
     * 
     * @param course_id - the Course ID associated with this Category 
     *                    Membership. The Course ID should be generated by 
     *                    Blackboard, in the form "_nnn_1" where nnn is an 
     *                    integer.
     */
    public void setCourseId(String course_id){
    	super.setCourseId(course_id);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    
    private BbCourseCategoryMembership[] convert_CategoryMembershipVOArray_to_BbCourseCategoryMembershipArray(CategoryMembershipVO[] cm_vos){
    	if(cm_vos==null || cm_vos.length<1) return null;
		List<BbCourseCategoryMembership> bb_ccms = new ArrayList<BbCourseCategoryMembership>();
		for(CategoryMembershipVO cmvo : cm_vos){
			if(cmvo!=null && !cmvo.getOrganization()) bb_ccms.add(new BbCourseCategoryMembership(cmvo));
		}
		return bb_ccms.toArray(new BbCourseCategoryMembership[]{});
    }
    
    private CategoryMembershipVO getVO(){
    	return _cmvo;
    }
}
