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

package org.bbsync.webservice.client.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestBbUtil_Integration {
	private static final Logger logger = Logger.getLogger(TestBbUtil_Integration.class.getName());

	@Test
    public void testGetServerVersion() {
        BbUtil bb_util = new BbUtil();
        Long version = bb_util.getServerVersion();
        assertFalse(version == 0L);
        logger.info("Util web service version: " + version);
    }
		
	@Test
    public void testInitializeUtilWS(){
		BbUtil bb_util = new BbUtil();
		boolean result = bb_util.initializeUtilWS();
		assertTrue(result);
		logger.info("initialize Util web service succeded.");
    }

    @Test
    public void testGetRequiredEntilements() {
        BbUtil bb_util = new BbUtil();
        String[] entitlements = bb_util.getRequiredEntitlements("getDataSources");
        assertNotNull(entitlements);
        assertTrue(entitlements.length>0);
        for(int i=0; i<entitlements.length; i++){
        	logger.info("entitlement[" + i + "] = " + entitlements[i]);
        }
    }
  
    @Test
    public void testcheckEntitlement(){
    	//get existing entitlements
        BbUtil bb_util = new BbUtil();
        String[] entitlements = bb_util.getRequiredEntitlements("getDataSources");
        assertNotNull(entitlements);
        assertTrue(entitlements.length>0);
        //test the existing entitlements
        for(int i=0; i<entitlements.length; i++){
        	assertTrue(bb_util.checkEntitlement(null, entitlements[i]));
        	logger.info("session entitlement check: " + entitlements[i] + " = OK");
        }
    }
    
    @Test
    public void testSettingsSystem() {
    	int scope = 0; //system scope
        String id = null; //null for system scope
        String key = "testKey";
        String value = "testValue";
    	//make sure the setting doesn't already exist
        assertNull(loadSetting(scope, id, key));
        assertTrue(saveSetting(scope, id, key, value));
        logger.info("System setting <<" + key + ">>, <<" + value + ">> saved successfully");
        assertEquals(value, loadSetting(scope, id, key));
        logger.info("System setting <<" + key + ">>, <<" + value + ">> loaded successfully");
        assertTrue(deleteSetting(scope, id, key));
        logger.info("System setting <<" + key + ">>, <<" + value + ">> deleted successfully");
        //make sure the setting was deleted
        assertNull(loadSetting(scope, id, key));
    }
        
    private boolean deleteSetting(int scope, String id, String key){
    	BbUtil bb_util = new BbUtil();
    	return bb_util.deleteSetting(scope, id, key);
    }
        
    private String loadSetting(int scope, String id, String key){
    	BbUtil bb_util = new BbUtil();
    	return bb_util.loadSetting(scope, id, key);
    }
    
    private boolean saveSetting(int scope, String id, String key, String value){
    	BbUtil bb_util = new BbUtil();
    	return bb_util.saveSetting(scope, id, key, value);
    }

}
