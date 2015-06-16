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

package org.bbsync.webservice.client.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.client.Stub;
import org.bbsync.webservice.client.generated.ContextWSStub.CourseIdVO;
import org.bbsync.webservice.client.generated.ContextWSStub.DeactivateToolResultVO;
import org.bbsync.webservice.client.generated.ContextWSStub.RegisterToolResultVO;
import org.bbsync.webservice.client.generated.ContextWSStub.VersionVO;
import org.bbsync.webservice.client.proxytool.ContextProxyTool;


/**
 * @author kurt
 *
 */
public class BbContext extends ContextProxyTool {
	
    private static final long serialVersionUID = 1111000000002222L;
    
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public BbContext(){
    	super();
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////
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
    
    ///////////////////////////////////////////////////////////////////////////
    //  Implemented ProxyTool Methods  ////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public BbDeactivateToolResult deactivateTool() {
    	DeactivateToolResultVO result = super.deactivateTool(null);
    	return new BbDeactivateToolResult(result);
    }

    public boolean emulateUser(String user_name){
    	return super.emulateUser(user_name);
    }

    public boolean extendSessionLife(Long additional_seconds) {
    	return super.extendSessionLife(additional_seconds);
    }
            
    public String[] getUserCourses(String user_name){
    	CourseIdVO[] course_id_vos = super.getMemberships(user_name);
    	return convert_CourseIdVOArray_to_StringArray(course_id_vos);
    }
    
    public String[] getMyCourses(){
    	CourseIdVO[] course_id_vos = super.getMyMemberships();
    	return convert_CourseIdVOArray_to_StringArray(course_id_vos);
    }
    
    public String[] getRequiredEntitlements(String method){
    	return super.getRequiredEntitlements(method);
    }
    
    public Long getServerVersion() {
        VersionVO version = super.getServerVersion(null);
        if(version==null) return null; 
        return version.getVersion();
    }
    
    public String getSystemInstallationId(){
    	return super.getSystemInstallationId();
    }
    
    public String initialize(){
    	return super.initialize();
    }
    
    public String initializeVersion2(){
    	return super.initializeVersion2();
    }

    public BbRegisterToolResult registerProxyTool(String clientVendorId, 
    											  String clientProgramId,
    											  String registrationPassword, 
    											  String description,
    											  String initialSharedSecret, 
    											  String[] requiredToolMethods,
    											  String[] requiredTicketMethods){
    	RegisterToolResultVO result = registerTool(clientVendorId, 
    			                                clientProgramId, 
			                                    registrationPassword, 
			                                    description, 
			                                    initialSharedSecret, 
			                                    requiredToolMethods, 
			                                    requiredTicketMethods);
    	if(result==null) return null;
    	return new BbRegisterToolResult(result);
    }
                
    public boolean loginAsUser(String tool_name, String user_name, String user_password) {
    	return super.login(user_name, user_password, client_vendor_id, tool_name, null, default_timeout_seconds);
    }

    public boolean loginAsTool(String tool_name, String tool_password) {
    	return super.loginTool(tool_password, client_vendor_id, tool_name, null, default_timeout_seconds);
    }
    
    public boolean loginWithTicket(String tool_name, String ticket) {
    	return super.loginTicket(ticket, client_vendor_id, tool_name, null, default_timeout_seconds);
    }
    
    public boolean logout(){
    	return super.logout();    	
    }
    
    public Stub initializeClient(Class<?extends Stub> client_stub, String tool_url) {
    	Stub ws_stub = super.createClientWS(client_stub, tool_url);
    	return super.configureClientOptions(ws_stub);
    }

    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////// 
    private String[] convert_CourseIdVOArray_to_StringArray(CourseIdVO[] course_id_vos){
    	if(course_id_vos==null || course_id_vos.length<1) return null;
		List<String> course_ids = new ArrayList<String>();
		for(CourseIdVO course_id_vo : course_id_vos){
			if(course_id_vo!=null && course_id_vo.getExternalId()!=null) course_ids.add(course_id_vo.getExternalId());
		}
		return course_ids.toArray(new String[]{});
    } 
}
