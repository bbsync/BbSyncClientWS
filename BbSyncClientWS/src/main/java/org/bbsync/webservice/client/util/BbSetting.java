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

import java.io.Serializable;

import org.bbsync.webservice.client.proxytool.UtilProxyTool;

public class BbSetting extends UtilProxyTool {
    private static final long serialVersionUID = 2222000000004444L;
    private int scope = 0;
    private String id = null; 
    private String key = null;
    private String value = null;

    public BbSetting(){}
    
    public BbSetting(int scope, String id, String key) {
        this.scope = scope;
        this.id = id;
        this.key = key;
    }
    
    public BbSetting(int scope, String id, String key, String value) {
        this.scope = scope;
        this.id = id;
        this.key = key;
        this.value = value;
    }

    
    public Serializable persist() {
        return super.saveSetting(scope, id, key, value);
    }

    public Object retrieve() {
    	value = super.loadSetting(scope, id, key);
        if (value == null) {
            return null;
        }
        return new BbSetting(scope, id, key, value);
    }

    public boolean delete() {
        boolean result = super.deleteSetting(scope, id, key);
        return result;
    }
    
	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
