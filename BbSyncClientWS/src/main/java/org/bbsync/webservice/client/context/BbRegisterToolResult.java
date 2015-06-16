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

import org.bbsync.webservice.client.generated.ContextWSStub.RegisterToolResultVO;
import org.bbsync.webservice.client.proxytool.ContextProxyTool;

import java.io.Serializable;

/**
 * This class encapsulates the result of a registerTool operation.
 */
public class BbRegisterToolResult extends ContextProxyTool {
    
    private static final long serialVersionUID = 1111000000003333L;
    private RegisterToolResultVO result = null;
    
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public BbRegisterToolResult() {
    	result = new RegisterToolResultVO();
    }
	
	public BbRegisterToolResult(RegisterToolResultVO register_tool_result_vo) {
		result = register_tool_result_vo;
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
    /**
     * If the registration operation fails there may be an optional list of 
     * reasons for it to have failed. This will include items such as invalid 
     * XML for a proxy tool.
     * @return
     */
    public String[] getFailureErrors() {
    	return result.getFailureErrors();
    }
    
    /**
     * This GUID is a unique identifier that Academic Suite has 
     * created for this tool.You should store this in your application 
     * so that you can uniquely identify conversations between you and 
     * this AS instance. (For example, if you connect to multiple AS instances, 
     * you should store the returned GUID for each instance so that you can use 
     * an appropriate shared secret in future sessions and so you can identify 
     * proxy-server callbacks as coming from the correct AS server.)
     * @return
     */
    public String getProxyToolGuid() {
    	return result.getProxyToolGuid();
    }
    
    /**
     * If status==true then the tool was successfully registered
     * If status==false then the tool was not registered. 
     * Refer to FailureErrors for a list of reasons as to why the 
     * registration may have failed.
     * @return
     */
    public boolean getStatus() {
    	return result.getStatus();
    }
    
    public void	setFailureErrors(String[] failure_errors) {
    	result.setFailureErrors(failure_errors);
    }
    
    public void	setProxyToolGuid(String proxy_tool_guid) {
    	result.setProxyToolGuid(proxy_tool_guid);
    }
    
    public void	setStatus(boolean status){
    	result.setStatus(status);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    

}
