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

package org.bbsync.webservice.client.proxytool;

import java.io.Serializable;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;
import org.bbsync.webservice.client.ClientWebService;
import org.bbsync.webservice.client.context.BbContext;
import org.bbsync.webservice.client.generated.UtilWSStub;
import org.bbsync.webservice.client.generated.UtilWSStub.CheckEntitlement;
import org.bbsync.webservice.client.generated.UtilWSStub.CheckEntitlementResponse;
import org.bbsync.webservice.client.generated.UtilWSStub.CourseIdVO;
import org.bbsync.webservice.client.generated.UtilWSStub.DataSourceVO;
import org.bbsync.webservice.client.generated.UtilWSStub.DeleteSetting;
import org.bbsync.webservice.client.generated.UtilWSStub.DeleteSettingResponse;
import org.bbsync.webservice.client.generated.UtilWSStub.GetDataSources;
import org.bbsync.webservice.client.generated.UtilWSStub.GetDataSourcesResponse;
import org.bbsync.webservice.client.generated.UtilWSStub.GetRequiredEntitlements;
import org.bbsync.webservice.client.generated.UtilWSStub.GetRequiredEntitlementsResponse;
import org.bbsync.webservice.client.generated.UtilWSStub.GetServerVersion;
import org.bbsync.webservice.client.generated.UtilWSStub.GetServerVersionResponse;
import org.bbsync.webservice.client.generated.UtilWSStub.InitializeUtilWS;
import org.bbsync.webservice.client.generated.UtilWSStub.InitializeUtilWSResponse;
import org.bbsync.webservice.client.generated.UtilWSStub.LoadSetting;
import org.bbsync.webservice.client.generated.UtilWSStub.LoadSettingResponse;
import org.bbsync.webservice.client.generated.UtilWSStub.SaveSetting;
import org.bbsync.webservice.client.generated.UtilWSStub.SaveSettingResponse;
import org.bbsync.webservice.client.generated.UtilWSStub.VersionVO;

public abstract class UtilProxyTool extends AbstractProxyTool implements ClientWebService, Serializable {
	private static final Logger logger = Logger.getLogger(AbstractProxyTool.class.getName());
	private static final long serialVersionUID = 2222000000000000L;
	private transient UtilWSStub utilWS = null;
	private transient BbContext session = null;
	private static final String CLIENT_PROGRAM_ID = "UtilProxyTool";
	private static String ws_url = null;
	private static String client_pw = null;
	
	///////////////////////////////////////////////////////////////////////////
	//  Constructor  //////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	protected UtilProxyTool() {
		super();
		if (client_pw == null)
			client_pw = config
					.getConfigClass(CLIENT_PROGRAM_ID, "sharedSecret");
		if (ws_url == null)
			ws_url = config.getConfigClass(CLIENT_PROGRAM_ID, "webServiceURL");
	}

    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    private void loginAsTool() {
    	session = new BbContext();
    	session.loginAsTool(CLIENT_PROGRAM_ID, client_pw);
    	utilWS = (UtilWSStub) session.initializeClient(UtilWSStub.class, ws_url);
    	_initializeUtilWS(true);
    }
        
    private void logout(){
    	session.logout();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  All UtilWS Methods  ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
	/**
	 * Check the given entitlement for the given session/course.
	 * 
	 * @param 	course - null is OK - means just check session level 
	 *                   entitlements. If non-null then course-specific 
	 *                   entitlements will be included.
	 * @param 	entitlement - an entitlement_uid string.
	 * 
	 * @return	true  - the given session has the requested entitlement; 
	 *          false - it does not.
	 */
	protected boolean checkEntitlement(CourseIdVO course, String entitlement) {
		loginAsTool();
		CheckEntitlementResponse response = null;
		CheckEntitlement chk_ent = new CheckEntitlement();
		chk_ent.setEntitlement(entitlement);
		chk_ent.setCourse(course);
		try {
			response = utilWS.checkEntitlement(chk_ent);
		} catch (RemoteException e) {
			logger.error("Unable to get Entitlement Response.");
			logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return false;
		}
		logout();
		if(response==null){
        	logger.warn("checkEntitlement returned null.");
        	return false;
        }
        return response.get_return();	
    }
	
	/**
	 * This method deletes a setting.
	 * 
	 * @param scope - the scope of the setting: 0=System, 1=Course, 2=Content
	 * @param id - for scope=0, set this to null; for scope=1, set this to the id of 
	 *             the course; for scope=2, set this to the id of the content item.
	 * @param key - a key to identify the setting. NOTE that it must use only the 
	 *              characters [a-z0-9] and must not exceed 200 characters. This is 
	 *              not enforced at runtime but behavior is unpredictable if you do 
	 *              not follow this recommendation.
	 *              
	 * @return  Returns true if the setting was deleted.  Otherwise returns false.
	 */
	protected boolean deleteSetting(int scope, String id, String key){
    	loginAsTool();
    	DeleteSettingResponse response = null;
        DeleteSetting delete = new DeleteSetting();
        delete.setId(id);
        delete.setKey(key);
        delete.setScope(scope);
        try {
            response = utilWS.deleteSetting(delete);
        } catch (RemoteException e) {
            logger.error("Encountered a remote exception while trying to delete setting: " + e.getMessage());
            logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return false;
       	}
        logout();
        if(response==null){
        	logger.warn("deleteSetting returned null.");
        	return false;
        }
        return response.get_return();
	}
	

	/**
	 * Load all the dataSources in AS.
	 * 
	 * @param ids - ignored.
	 * 
	 * @return	Returns an array of dataSource objects
	 */
	protected DataSourceVO[] getDataSources(String[] ids) {
		loginAsTool();
		GetDataSourcesResponse response = null;
		GetDataSources gds = new GetDataSources();
		try {
			response = utilWS.getDataSources(gds);
		} catch (RemoteException e) {
            logger.error("Encountered a remote exception while trying to Get Data Sources: " + e.getMessage());
            logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
       	}
        logout();
        if(response==null){
        	logger.warn("getDataSources returned null.");
        	return null;
        }
        return response.get_return();
	}

	/**
	 * This will return a list of the entitlements the specified method requires for 
	 * successful completion.
	 * 
	 * @param method - the name of a method on this Web Service that you plan 
	 *                 to call while using tool based authentication.
	 *                 
	 * @return Returns an array of entitlement UIDs.
	 */
	protected String[] getRequiredEntitlements(String method) {
		loginAsTool();
		GetRequiredEntitlementsResponse response = null;
		GetRequiredEntitlements gre = new GetRequiredEntitlements();
		try {
			gre.setMethod(method);
			response = utilWS.getRequiredEntitlements(gre);
		} catch (RemoteException e) {
			logger.error("Unable to get Required Entitlements Response.");
			logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
       	}
        logout();
        if(response==null){
        	logger.warn("getRequiredEntitlements returned null.");
        	return null;
        }
        return response.get_return();
	}

	/**
	 * Returns the current version of this web service on the server
	 *
	 * @param unused - this is an optional parameter put here to make the 
	 *                 generation of .net client applications from the wsdl 
	 *                 'cleaner' (0-argument methods do not generate clean 
	 *                 stubs and are much harder to have the same method 
	 *                 name across multiple Web Services in the same .net 
	 *                 client).
	 *                 
	 * @return Returns the current version of this web service on the server
	 */
	protected VersionVO getServerVersion(VersionVO unused) {
		loginAsTool();
		GetServerVersionResponse response = null;
		GetServerVersion version = new GetServerVersion();
		version.setUnused(null);
		try {
			response = utilWS.getServerVersion(version);
		} catch (RemoteException e) {
			logger.error("Unable to get Server Version.");
			logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
       	}
        logout();
        if(response==null){
        	logger.warn("getServerVersion returned null.");
        	return null;
        }
        return response.get_return();
	}

	/**
	 * Sets the client version to version 1 and returns an appropriate session. 
	 * With each release of this web service we will implement a new 
	 * initializeVersionXXX method
	 * 
	 * @param ignore - this is an optional parameter put here to make the 
	 *                 generation of .net client applications from the wsdl 
	 *                 'cleaner' (0-argument methods do not generate clean stubs 
	 *                 and are much harder to have the same method name across 
	 *                 multiple Web Services in the same .net client).
	 *                 
	 * @return true to indicate that the session has been initialized for the 
	 *         util web service.
	 */
	protected boolean initializeUtilWS(boolean ignore) {
		loginAsTool();
        boolean result = _initializeUtilWS(ignore);
		logout();
		return result;
	}

	private boolean _initializeUtilWS(boolean ignore) {
		InitializeUtilWSResponse response = null;
		InitializeUtilWS init = new InitializeUtilWS();
		init.setIgnore(ignore);
		try {
			response = utilWS.initializeUtilWS(init);
		} catch (RemoteException e) {
			logger.error("Unable to Initialize Util Web Service.");
			logger.error(e.getMessage());
            return false;
       	}
        if(response==null){
        	logger.warn("initializeUtilWS returned null.");
        	return false;
        }
        return response.get_return();
	}

	/**
 	 * This method loads a setting.
 	 * 
	 * @param scope - the scope of the setting: 0=System, 1=Course, 2=Content
	 * @param id - for scope=0, set this to null; for scope=1, set this to the id of 
	 *             the course; for scope=2, set this to the id of the content item.
	 * @param key - a key to identify the setting. NOTE that it must use only the 
	 *              characters [a-z0-9] and must not exceed 200 characters. This is 
	 *              not enforced at runtime but behaviour is unpredictable if you do 
	 *              not follow this recommendation.
	 *              
	 * @return Returns the value associated with the given key.
	 */
	protected String loadSetting(int scope, String id, String key){
		loginAsTool();
		LoadSettingResponse response = null;
		LoadSetting load = new LoadSetting();
		load.setId(id);
		load.setKey(key);
		load.setScope(scope);
		try {
			response = utilWS.loadSetting(load);
		} catch (RemoteException e) {
			logger.error("Unable to Load Setting.");
			logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
       	}
        logout();
        if(response==null){
        	logger.warn("loadSetting returned null.");
        	return null;
        }
        return response.get_return();
	}
	
	/**
	 * This method can be used to store various settings for your tool at various 
	 * levels within the system. You can get at these settings either through the 
	 * loadSetting method or implicitly in proxy tool actions as they will be passed 
	 * along with the other generic arguments during a proxy tool launch. NOTE that 
	 * students or really 'anyone' if they know how to get at the ppg directory 
	 * within a course can see the values for these settings so do not put anything 
	 * "private" in here. Also, students who are legitimately accessing the proxy 
	 * tool through the UI will see all of the values for these settings if they 
	 * view-source on the proxy launch page.
	 * 
	 * @param scope - the scope of the setting: 0=System, 1=Course, 2=Content
	 * @param id - for scope=0, set this to null; for scope=1, set this to the id of 
	 *             the course; for scope=2, set this to the id of the content item.
	 * @param key - a key to identify the setting. NOTE that it must use only the 
	 *              characters [a-z0-9] and must not exceed 200 characters. This is 
	 *              not enforced at runtime but behaviour is unpredictable if you do 
	 *              not follow this recommendation.
	 * @param value - a value to associate with this key. No explicit limits are 
	 *                placed on this but you should try to keep the value small 
	 *                (i.e. no more than a few hundred characters).
	 *                
	 * @return Returns true if the setting was saved.
	 */
	protected boolean saveSetting(int scope, String id, String key, String value){
		loginAsTool();
		SaveSettingResponse response = null;
		SaveSetting save = new SaveSetting();
		save.setId(id);
		save.setKey(key);
		save.setScope(scope);
		save.setValue(value);
		try {
			response = utilWS.saveSetting(save);
		} catch (RemoteException e) {
			logger.error("Unable to Load Setting.");
			logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return false;
       	}
        logout();
        if(response==null){
        	logger.warn("loadSetting returned null.");
        	return false;
        }
        return response.get_return();
	}
}