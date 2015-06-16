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

import org.bbsync.webservice.client.generated.ContextWSStub.DeactivateToolResultVO;
import org.bbsync.webservice.client.proxytool.ContextProxyTool;

import java.io.Serializable;


/**
 * This class encapsulates the result of a deactivateTool operation.
 */
public class BbDeactivateToolResult extends ContextProxyTool {
    
    private static final long serialVersionUID = 1111000000004444L;
    private DeactivateToolResultVO result = null;
    
    ///////////////////////////////////////////////////////////////////////////
    //  Constructors  /////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    public BbDeactivateToolResult() {
    	result = new DeactivateToolResultVO();
    }
	
	public BbDeactivateToolResult(DeactivateToolResultVO deactivate_tool_result_vo) {
		result = deactivate_tool_result_vo;
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
     * Returns true if the tool is successfully deactivated. Returns false if 
     * the deactivation fails; in which case, consult failure errors to 
     * determine what happened.
     * 
     * @return Returns the deactivation status.
     */
    public boolean getStatus(){
    	return result.getStatus();
    }

    /**
     * Set the status of the deactivation operation.
     * 
     * @param status - the status to set.
     */
    public void setStatus(boolean status){
    	result.setStatus(status);
    }
    
    /**
     * If the deactivation fails, returns the reasons for that failure. If the 
     * it succeeds (or if no failure reason could be determined) returns null. 
     * All error messages are in English.
     * 
     * @return Returns failure errors.
     */
    public String[] getFailureErrors(){
    	return result.getFailureErrors();
    }

    /**
     * Set deactivation failure errors.
     * 
     * @param failure_errors - the failure errors to set.
     */
    public void setFailureErrors(String[] failure_errors){
    	result.setFailureErrors(failure_errors);
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    

}
