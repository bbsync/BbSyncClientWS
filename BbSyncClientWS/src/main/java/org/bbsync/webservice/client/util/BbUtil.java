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
import java.util.ArrayList;

import org.bbsync.webservice.client.generated.UtilWSStub.CourseIdVO;
import org.bbsync.webservice.client.generated.UtilWSStub.DataSourceVO;
import org.bbsync.webservice.client.generated.UtilWSStub.VersionVO;
import org.bbsync.webservice.client.proxytool.UtilProxyTool;

public class BbUtil extends UtilProxyTool {
    private static final long serialVersionUID = 2222000000003333L;

    
	public Serializable persist() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object retrieve() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public boolean checkEntitlement(String course_batch_uid, String entitlement) {
        CourseIdVO course_id_vo = new CourseIdVO();
        course_id_vo.setExternalId(course_batch_uid);
		return super.checkEntitlement(course_id_vo, entitlement);	
	}
	
	public boolean deleteSetting(int scope, String id, String key){
		return super.deleteSetting(scope, id, key);
	}
	
	public BbDataSource[] getAllDataSources() {
        ArrayList<BbDataSource> data_sources = null;
    	DataSourceVO[] dsvo_array = super.getDataSources(null);
        if (dsvo_array==null) return null;
        data_sources = new ArrayList<BbDataSource>();
        for (DataSourceVO dsvo : dsvo_array) {
        	data_sources.add(new BbDataSource(dsvo));
        }
        return data_sources.toArray(new BbDataSource[]{});
    }
	
	public String[] getRequiredEntitlements(String method){
		return super.getRequiredEntitlements(method);
	}

	public long getServerVersion(){
    	VersionVO version = null;
    	version = super.getServerVersion(version);
    	return version.getVersion();
    }

	public boolean initializeUtilWS(){
		return super.initializeUtilWS(true);
	}

	public String loadSetting(int scope, String id, String key){
		return super.loadSetting(scope, id, key);
	}
	
	public boolean saveSetting(int scope, String id, String key, String value){
		return super.saveSetting(scope, id, key, value);
	}
}
