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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

public class TestBbSetting_Integration {
	private static final Logger logger = Logger.getLogger(TestBbSetting_Integration.class.getName());

    @Test
    public void testBbSetting_System() {
    	int scope = 0; //system scope
        String id = null; //null for system scope
        String key = "testKey";
        String value = "testValue";
    	//Create a new BbSetting
        BbSetting bb_setting = new BbSetting();
        bb_setting.setId(id);
        bb_setting.setKey(key);
        bb_setting.setScope(scope);
        bb_setting.setValue(value);
        //Persist
        Boolean result = (Boolean) bb_setting.persist(); 
        assertNotNull(result);
        assertTrue(result);
        assertEquals(key, bb_setting.getKey());
        assertEquals(scope, bb_setting.getScope());
        assertEquals(value, bb_setting.getValue());
        logger.info("successfully persisted BbSetting");
        //Retrieve
        bb_setting = null;
        bb_setting = new BbSetting(scope, id, key);
        bb_setting = (BbSetting) bb_setting.retrieve();
        assertNotNull(bb_setting);
        assertEquals(id, bb_setting.getId());
        assertEquals(key, bb_setting.getKey());
        assertEquals(scope, bb_setting.getScope());
        assertEquals(value, bb_setting.getValue());
        logger.info("successfully retrieved BbSetting");
        //Delete
        bb_setting = new BbSetting(scope, id, key);
        result = (Boolean) bb_setting.delete();
        assertNotNull(result);
        assertTrue(result);
        logger.info("successfully deleted BbSetting");
    }
}
