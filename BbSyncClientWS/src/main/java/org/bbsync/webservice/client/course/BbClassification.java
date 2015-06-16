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

import org.bbsync.webservice.client.generated.CourseWSStub.ClassificationVO;
import org.bbsync.webservice.client.generated.CourseWSStub.VersionVO;
import org.bbsync.webservice.client.proxytool.CourseProxyTool;

/**
 * According to "Course and Organization Data Feed Elements" (see SP14 
 * Administrator Help), Classifications are an association [of a Course or
 * Organization] with a subject area and discipline within the Blackboard 
 * Resource Center. This is a deprecated feature in Blackboard Learn (since
 * Release 7), but the value is still provided for backward compatibility.
 * 
 * As such, BbClassification is a read-only data object that lists the 
 * classification of a Course or Organization. That is, there is currently no 
 * way to add, delete or edit the existing Subject Area & Discipline 
 * classifications. 
 * 
 * With web services, classifications are applied to Courses or Organizations 
 * using the setClassificationId() method found in BbCourse & BbOrganization
 * objects.  This class allows you to determine what those ID values are
 */
public class BbClassification extends CourseProxyTool {
    private static final long serialVersionUID = 3333000000006666L;
    private ClassificationVO _classification_vo = null;
    
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Creates a new BbClassification. 
     */
    public BbClassification() {
    	_classification_vo = new ClassificationVO();
    }

    /** 
     * This constructor converts a ClassificationVO to a BbClassification.
     * 
     * @param staff_info_vo - the virtual object used to initialize a new 
     *                        BbStaffInfoContact.
     */
    private BbClassification(ClassificationVO classification_vo) {
    	_classification_vo = classification_vo;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////
    public Serializable persist() {
    	return null;
    }
   
    public Object retrieve() {
    	if(getId()!=null) return getClassificationById(getId());
    	if(getBatchUid()!=null) return getClassificationByBatchUid(getBatchUid());
    	return null;
    }
   
    public boolean delete() {
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

    public BbClassification[] getClassifications(){
    	ClassificationVO[] result = super.getClassifications(null);
    	return convert_ClassifiactionVOArray_to_BbClassificationArray(result);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    
    public BbClassification getClassificationById(String id){
    	BbClassification[] bb_classes = getClassifications();
    	for(BbClassification bb_class:bb_classes){
    		if(bb_class.getId()!=null && bb_class.getId().equals(id)) return bb_class;
    	}
    	return null;
    }
    
    public BbClassification getClassificationByBatchUid(String batch_uid){
    	BbClassification[] bb_classes = getClassifications();
    	for(BbClassification bb_class:bb_classes){
    		if(bb_class.getBatchUid()!=null && bb_class.getBatchUid().equals(batch_uid)) return bb_class;
    	}
    	return null;
    }
    
    /**
     * @return Returns this Classifiaction's Batch UID.
     */
    public String getBatchUid(){
    	return _classification_vo.getBatchUid();
    }

    /**
     * Sets the Classifications Batch UID.
     * 
     * @param batch_uid - the Batch UID to set.  Batch UIDs for the default
     *                    classifications are formatted as 
     *                    "Subject Area:Discipline".
     */
    public void setBatchUid(String batch_uid){
    	_classification_vo.setBatchUid(batch_uid);
    }

    /**
     * @return Returns this Classifiaction's ID.
     */
    public String getId(){
    	return _classification_vo.getId();
    }

    /**
     * Sets the Classification's ID.
     * 
     * @param id - the ID to set. This ID should be generated by Blackboard, in 
	 *             the form "_nnn_1" where nnn is an integer.
     */
    public void setId(String id){
    	_classification_vo.setId(id);
    }

    /**
     * @return Returns this Classification's data source ID.
     */
    public String getDataSourceId(){
    	return _classification_vo.getDataSourceId();
    }

    /**
     * Sets the Classification's data source ID.
     * 
     * @param data_source_id - the data source ID to set.
     */
    public void setDataSourceId(String data_source_id){
    	_classification_vo.setDataSourceId(data_source_id);
    }

    /**
     * @return Returns this Classification's parent ID.
     */
    public String getParentId(){
    	return _classification_vo.getParentId();
    }

    /**
     * Sets the Classification's parent ID.
     * 
     * @param parent_id - the parent ID to set.
     */
    public void setParentId(String parent_id){
    	_classification_vo.setParentId(parent_id);
    }

    /**
     * @return Returns this Classification's title.
     */
    public String getTitle(){
    	return _classification_vo.getTitle();
    }

    /**
     * Sets the Classification's title.
     * 
     * @param title - the title to set.
     */
    public void setTitle(String title){
    	_classification_vo.setTitle(title);
    }

    /**
     * @return Returns this Classification's expansion data.
     */
    public String[] getExpansionData(){
    	return _classification_vo.getExpansionData();
    }

    /**
     * Expansion data is currently ignored. In future versions it may be used 
     * to add additional attributes without breaking the wsdl contract.
     * 
     * @param expansion_data - the expansionData to set (For Future Use)
     */
    public void setExpansionData(String[] expansion_data){
    	_classification_vo.setExpansionData(expansion_data);
    }
    
    private BbClassification[] convert_ClassifiactionVOArray_to_BbClassificationArray(ClassificationVO[] classifcation_vos){
    	if(classifcation_vos==null || classifcation_vos.length<1) return null;
		List<BbClassification> bb_class = new ArrayList<BbClassification>();
		for(ClassificationVO classification : classifcation_vos){
			if(classification!=null) bb_class.add(new BbClassification(classification));
		}
		return bb_class.toArray(new BbClassification[]{});
    }
}
