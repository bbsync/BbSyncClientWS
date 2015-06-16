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

import org.bbsync.webservice.client.generated.CourseWSStub.CartridgeVO;
import org.bbsync.webservice.client.generated.CourseWSStub.VersionVO;
import org.bbsync.webservice.client.proxytool.CourseProxyTool;

import java.io.Serializable;


public class BbCartridge extends CourseProxyTool {
    private static final long serialVersionUID = 3333000000003333L;
    private CartridgeVO _cartridge_vo = null;
    
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public BbCartridge() {
    	_cartridge_vo = new CartridgeVO();
    }

    private BbCartridge(CartridgeVO cartridge_vo) {
    	_cartridge_vo = cartridge_vo;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////
    public Serializable persist() {
    	return super.saveCartridge(_cartridge_vo);
    }
    /**
	 * Gets a BbCartridge by its ID.  The Cartridge's ID field must be set.
	 * Use setUniqueId 
	 * @return Returns a BbCartridge object if successful, else returns null.
	 */
    public Object retrieve() {
    	return getBbCartridge(_cartridge_vo.getId());
    }
    
    /**
	 * Deletes this Cartridge from the AS.  The Cartridge's ID field must be 
	 * set. Use setUniqueID().
	 * @return Returns true if successful, else returns false.
	 */
    public boolean delete() {
    	String cartridge_id = deleteCartridge(_cartridge_vo.getId());
    	if(cartridge_id==null || !cartridge_id.equals(_cartridge_vo.getId()))return false;
    	return true;
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
    
    public String saveCartridge(BbCartridge bb_cartridge){
    	return super.saveCartridge(bb_cartridge.getVO());
    }

    public String deleteCartridge(String cartridge_id) {
    	return super.deleteCartridge(cartridge_id);
    }
    
    /**
     * Return a Cartridge object associated with the given ID.
     * 
     * @param cartridge_id - the Cartridge ID
     * @return Returns a BbCartridge.
     */
    public BbCartridge getBbCartridge(String cartridge_id){
    	CartridgeVO cartridge_vo = super.getCartridge(cartridge_id);
    	if(cartridge_vo==null)return null;
    	return new BbCartridge(cartridge_vo);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public String getDescription(){
    	return _cartridge_vo.getDescription();
    }
    
    public String[] getExpansionData(){
    	return _cartridge_vo.getExpansionData();
    }
    
    /**
     * Returns the LMS_ID of the Course Cartridge.
     * @return
     */
    public String getId(){
    	return _cartridge_vo.getId();
    }
    
    public String getIdentifier(){
    	return _cartridge_vo.getIdentifier();
    }
    
    public String getPublisherName(){
    	return _cartridge_vo.getPublisherName();
    }
    
    public String getTitle(){
    	return _cartridge_vo.getTitle();
    }
    
    /**
     * set cartridge description for this CartridgeVO
     * @param description
     */
    public void setDescription(String description){
    	_cartridge_vo.setDescription(description);
    }
    
    /**
     * Expansion data is currently ignored.
     * @param expansionData
     */
    public void setExpansionData(String[] expansion_data){
    	_cartridge_vo.setExpansionData(expansion_data);
    }
    
    /**
     * You should not try to 'make up' an id here - only use 
     * the ids that you are given from another api call.
     * 
     * @param id - the ID to set. This ID should be generated by Blackboard, in 
	 *             the form "_nnn_1" where nnn is an integer.
     */
    public void setId(String id){
    	_cartridge_vo.setId(id);
    }
    
    /**
     * set cartridge identifier
     * @param identifier
     */
    public void setIdentifier(String identifier){
    	_cartridge_vo.setIdentifier(identifier);
    }
    
    /**
     * set publisher name for this CartridgeVO
     * @param publisherName
     */
    public void setPublisherName(String publisher_name){
    	_cartridge_vo.setPublisherName(publisher_name);
    }
    
    /**
     * set cartridge title for this CartridgeVO
     * @param title
     */
    public void setTitle(String title){
    	_cartridge_vo.setTitle(title);
    }
    
    private CartridgeVO getVO(){
    	return _cartridge_vo;
    }
}
