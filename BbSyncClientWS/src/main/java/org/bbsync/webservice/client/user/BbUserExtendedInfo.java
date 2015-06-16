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

package org.bbsync.webservice.client.user;

import java.io.Serializable;

import org.bbsync.webservice.client.generated.UserWSStub.UserExtendedInfoVO;
import org.bbsync.webservice.client.proxytool.UserProxyTool;

public class BbUserExtendedInfo extends UserProxyTool {
	private static final long serialVersionUID = 4444000000005555L;
	private UserExtendedInfoVO _user_xinfo_vo = null;
	
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
	public BbUserExtendedInfo() {
		_user_xinfo_vo = new UserExtendedInfoVO();
    }
	
	protected BbUserExtendedInfo(UserExtendedInfoVO user_xinfo_vo) {
		_user_xinfo_vo = user_xinfo_vo;
    }
	
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////	
	/**
	 * Currently not implemented.  Adding UserExtendedInfo is done through the
	 * BbUser & BbAddressBookEntry classes.
	 * @return - Always returns null;
	 */
	public Serializable persist() {
		return null;
	}
	
	/**
	 * Currently not implemented.  Retrieving UserExtendedInfo is done through
	 * the BbUser & BbAddressBookEntry classes.
	 * @return - Always returns null;
	 */
	public Object retrieve() {
		return null;
	}
	
	/**
	 * Currently not implemented.  Deleting UserExtendedInfo is done through 
	 * the BbUser & BbAddressBookEntry classes.
	 * @return - Always returns null;
	 */
	public boolean delete() {
		return false;
	}
	
    ///////////////////////////////////////////////////////////////////////////
    //  Implemented ProxyTool Methods  ////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

	///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////

	public String getFamilyName(){
		return _user_xinfo_vo.getFamilyName(); 
	}
	
	public void setFamilyName(String family_name){
		_user_xinfo_vo.setFamilyName(family_name);
	}
	
	public String getGivenName(){
		return _user_xinfo_vo.getGivenName();
	}
	
	public void setGivenName(String given_name){
		_user_xinfo_vo.setGivenName(given_name);
	}
	
	public String getMiddleName(){
		return _user_xinfo_vo.getMiddleName();
	}
	
	public void setMiddleName(String middle_name){
		_user_xinfo_vo.setMiddleName(middle_name);
	}
	
	public String getEmailAddress(){
		return _user_xinfo_vo.getEmailAddress();
	}
	
	public void setEmailAddress(String email_address){
		_user_xinfo_vo.setEmailAddress(email_address);
	}
	
	public String getJobTitle(){
		return _user_xinfo_vo.getJobTitle();
	}
	
	public void setJobTitle(String job_title){
		_user_xinfo_vo.setJobTitle(job_title);
	}
	
	public String getCompany(){
		return _user_xinfo_vo.getCompany();
	}
	
	public void setCompany(String company){
		_user_xinfo_vo.setCompany(company);
	}
	
	public String getDepartment(){
		return _user_xinfo_vo.getDepartment();
	}
	
	public void setDepartment(String department){
		_user_xinfo_vo.setDepartment(department);
	}
	
	public String getStreet1(){
		return _user_xinfo_vo.getStreet1();
	}
	
	public void setStreet1(String street_1){
		_user_xinfo_vo.setStreet1(street_1);
	}
	
	public String getStreet2(){
		return _user_xinfo_vo.getStreet2();
	}
	
	public void setStreet2(String street_2){
		_user_xinfo_vo.setStreet2(street_2);
	}
	
	public String getCity(){
		return _user_xinfo_vo.getCity();
	}
	
	public void setCity(String city){
		_user_xinfo_vo.setCity(city);
	}
	
	public String getState(){
		return _user_xinfo_vo.getState();
	}
	
	public void setState(String state){
		_user_xinfo_vo.setState(state);
	}
	
	public String getCountry(){
		return _user_xinfo_vo.getCountry();
	}
	
	public void setCountry(String country){
		_user_xinfo_vo.setCountry(country);
	}
	
	public String getZipCode(){
		return _user_xinfo_vo.getZipCode();
	}
	
	public void setZipCode(String zip_code){
		_user_xinfo_vo.setZipCode(zip_code);
	}
	
	public String getHomePhone1(){
		return _user_xinfo_vo.getHomePhone1();
	}
	
	public void setHomePhone1(String home_phone_1){
		_user_xinfo_vo.setHomePhone1(home_phone_1);
	}
	
	public String getHomePhone2(){
		return _user_xinfo_vo.getHomePhone2();
	}
	
	public void setHomePhone2(String home_phone_2){
		_user_xinfo_vo.setHomePhone2(home_phone_2);
	}
	
	public String getBusinessPhone1(){
		return _user_xinfo_vo.getBusinessPhone1();
	}
	
	public void setBusinessPhone1(String business_phone_1){
		_user_xinfo_vo.setBusinessPhone1(business_phone_1);
	}
	
	public String getBusinessPhone2(){
		return _user_xinfo_vo.getBusinessPhone2();
	}
	
	public void setBusinessPhone2(String business_phone_2){
		_user_xinfo_vo.setBusinessPhone2(business_phone_2);
	}
	
	public String getMobilePhone(){
		return _user_xinfo_vo.getMobilePhone();
	}
	
	public void setMobilePhone(String mobile_phone){
		_user_xinfo_vo.setMobilePhone(mobile_phone);
	}
	
	public String getHomeFax(){
		return _user_xinfo_vo.getHomeFax();
	}
	
	public void setHomeFax(String home_fax){
		_user_xinfo_vo.setHomeFax(home_fax);
	}
	
	public String getBusinessFax(){
		return _user_xinfo_vo.getBusinessFax();
	}
	
	public void setBusinessFax(String business_fax){
		_user_xinfo_vo.setBusinessFax(business_fax);
	}
	
	public String getWebPage(){
		return _user_xinfo_vo.getWebPage();
	}
	
	public void setWebPage(String web_page){
		_user_xinfo_vo.setWebPage(web_page);
	}
	
	/**
	 * @return Returns the expansion data for this UserExtendedInfo object.
	 */
	public String[] getExpansionData(){
		return _user_xinfo_vo.getExpansionData();
	}
    /**
     * Expansion data contains additional attributes added since this web 
     * service was released. The individual String objects in the array are of
     * the form: "key=value".  Current keys are:
     *  - USER.OTHERNAME - Example: ["USER.OTHERNAME=Nickname"]
     *  - USER.SUFFIX    - Example: ["USER.SUFFIX=esq."]
     *
     * @param expansion_data - the expansion data to set.
     */
	public void setExpansionData(String[] expansion_data){
		_user_xinfo_vo.setExpansionData(expansion_data);
	}
	
	protected UserExtendedInfoVO getVO(){
		return _user_xinfo_vo;
	}
}
