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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestBbUserExtendedInfo {
	private BbUserExtendedInfo bb_uxi = null;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bb_uxi = new BbUserExtendedInfo();
	}

	@After
	public void tearDown() throws Exception {
		bb_uxi = null;
	}
    			
	@Test
	public void testBusinessFax(){
		assertNull(bb_uxi.getBusinessFax());
		bb_uxi.setBusinessFax("1234567890_test_string");
		assertEquals("1234567890_test_string", bb_uxi.getBusinessFax());
	}
	
	@Test
	public void testBusinessPhone1(){
		assertNull(bb_uxi.getBusinessPhone1());
		bb_uxi.setBusinessPhone1("1234567890_test_string");
		assertEquals("1234567890_test_string", bb_uxi.getBusinessPhone1());
	}
	
	@Test
	public void testBusinessPhone2(){
		assertNull(bb_uxi.getBusinessPhone2());
		bb_uxi.setBusinessPhone2("1234567890_test_string");
		assertEquals("1234567890_test_string", bb_uxi.getBusinessPhone2());
	}
	
	@Test
	public void testCity(){
		assertNull(bb_uxi.getCity());
		bb_uxi.setCity("test_City_string");
		assertEquals("test_City_string", bb_uxi.getCity());
	}
	
	@Test
	public void testCompany(){
		assertNull(bb_uxi.getCompany());
		bb_uxi.setCompany("test_Company_string");
		assertEquals("test_Company_string", bb_uxi.getCompany());
	}
	
	@Test
	public void testCountry(){
		assertNull(bb_uxi.getCountry());
		bb_uxi.setCountry("test_Country_string");
		assertEquals("test_Country_string", bb_uxi.getCountry());
	}
	
	@Test
	public void testDepartment(){
		assertNull(bb_uxi.getDepartment());
		bb_uxi.setDepartment("test_Department_string");
		assertEquals("test_Department_string", bb_uxi.getDepartment());
	}
	
	@Test
	public void testEmailAddress(){
		assertNull(bb_uxi.getEmailAddress());
		bb_uxi.setEmailAddress("test_EmailAddress_string");
		assertEquals("test_EmailAddress_string", bb_uxi.getEmailAddress());
	}
	
	@Test
	public void testFamilyName(){
		assertNull(bb_uxi.getFamilyName());
		bb_uxi.setFamilyName("test_FamilyName_string");
		assertEquals("test_FamilyName_string", bb_uxi.getFamilyName());
	}
	
	@Test
	public void testGivenName(){
		assertNull(bb_uxi.getGivenName());
		bb_uxi.setGivenName("test_GivenName_string");
		assertEquals("test_GivenName_string", bb_uxi.getGivenName());
	}
	
	@Test
	public void testHomeFax(){
		assertNull(bb_uxi.getHomeFax());
		bb_uxi.setHomeFax("test_HomeFax_string");
		assertEquals("test_HomeFax_string", bb_uxi.getHomeFax());
	}
	
	@Test
	public void testHomePhone1(){
		assertNull(bb_uxi.getHomePhone1());
		bb_uxi.setHomePhone1("test_HomePhone1_string");
		assertEquals("test_HomePhone1_string", bb_uxi.getHomePhone1());
	}
	
	@Test
	public void testHomePhone2(){
		assertNull(bb_uxi.getHomePhone2());
		bb_uxi.setHomePhone2("test_HomePhone2_string");
		assertEquals("test_HomePhone2_string", bb_uxi.getHomePhone2());
	}
	
	@Test
	public void testJobTitle(){
		assertNull(bb_uxi.getJobTitle());
		bb_uxi.setJobTitle("test_JobTitle_string");
		assertEquals("test_JobTitle_string", bb_uxi.getJobTitle());
	}
	
	@Test
	public void testMiddleName(){
		assertNull(bb_uxi.getMiddleName());
		bb_uxi.setMiddleName("test_MiddleName_string");
		assertEquals("test_MiddleName_string", bb_uxi.getMiddleName());
	}
	
	@Test
	public void testMobilePhone(){
		assertNull(bb_uxi.getMobilePhone());
		bb_uxi.setMobilePhone("test_MobilePhone_string");
		assertEquals("test_MobilePhone_string", bb_uxi.getMobilePhone());
	}
	
	@Test
	public void testState(){
		assertNull(bb_uxi.getState());
		bb_uxi.setState("test_State_string");
		assertEquals("test_State_string", bb_uxi.getState());
	}
	
	@Test
	public void testStreet1(){
		assertNull(bb_uxi.getStreet1());
		bb_uxi.setStreet1("test_Street1_string");
		assertEquals("test_Street1_string", bb_uxi.getStreet1());
	}
	
	@Test
	public void testStreet2(){
		assertNull(bb_uxi.getStreet2());
		bb_uxi.setStreet2("test_Street2_string");
		assertEquals("test_Street2_string", bb_uxi.getStreet2());
	}
	
	@Test
	public void testWebPage(){
		assertNull(bb_uxi.getWebPage());
		bb_uxi.setWebPage("test_WebPage_string");
		assertEquals("test_WebPage_string", bb_uxi.getWebPage());
	}
	
	@Test
	public void testZipCode(){
		assertNull(bb_uxi.getZipCode());
		bb_uxi.setZipCode("test_ZipCode_string");
		assertEquals("test_ZipCode_string", bb_uxi.getZipCode());
	}	
}
