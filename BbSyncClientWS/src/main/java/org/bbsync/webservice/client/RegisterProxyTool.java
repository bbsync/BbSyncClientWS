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

package org.bbsync.webservice.client;

import org.apache.log4j.Logger;
import org.bbsync.utility.file.Configuration;
import org.bbsync.webservice.client.context.BbContext;
import org.bbsync.webservice.client.context.BbRegisterToolResult;
import org.bbsync.webservice.client.proxytool.AbstractProxyTool;

public class RegisterProxyTool extends AbstractProxyTool{
    private static final Logger logger = Logger.getLogger(RegisterProxyTool.class.getName());
    private static Configuration config = Configuration.getInstance();
    private String proxy_tool_name = null;

    @SuppressWarnings("unused")
    private RegisterProxyTool() {
    }

    public RegisterProxyTool(String class_config_name) {
        proxy_tool_name = class_config_name;
    }

    public boolean registerTool() {
        String clientVendorId = config.getConfigClass("GLOBAL", "clientVendorId");
        String registrationPassword = config.getConfigClass("GLOBAL", "toolRegistrationPassword");
        String initialSharedSecret = config.getConfigClass(proxy_tool_name, "sharedSecret");
        String description = config.getConfigClass(proxy_tool_name, "toolDescription");
        String[] requiredToolMethods = parseToolMethods(config.getConfigClass(proxy_tool_name, "toolMethods"));
        String clientProgramId = proxy_tool_name;

        BbContext bb_context = new BbContext();
        BbRegisterToolResult result = bb_context.registerProxyTool(clientVendorId, clientProgramId, 
        														   registrationPassword, description, 
        														   initialSharedSecret, requiredToolMethods, null);
        if(result==null){
        	logger.error("The proxy tool was not registered successfully with " +
                    "Blackboard web services. Registration returned null.");
        	return false;
        }
        if (result.getStatus() == true) {
            logger.info("The proxy tool named " + clientProgramId +
                    " was registered with Blackboard web services " +
                    "successfully.\nLogin to Blackboard as a System Administrator " +
                    "and authorize the proxy tool.");
        	if(result.getProxyToolGuid()!=null && !result.getProxyToolGuid().equals("")){
        		logger.info("<param name=\"ticket\" value=\"" + result.getProxyToolGuid() +"\"/>\n" +
                        "to the proxy tool's settings in conf.xml.");
        	}
        	return true;
        } else {
            logger.error("The proxy tool was not registered successfully with " +
                "Blackboard web services.  Review the error messages below " +
                "and also review the Context web service log in Blackboard.");

            for (String errorMessage : result.getFailureErrors()) {
                logger.error(errorMessage);
            }
            return false;
        }
    }

    private String[] parseToolMethods(String methodString) {
        String[] methods = methodString.split(",");

        for (int i = 0; i < methods.length; i++) {
            methods[i] = methods[i].trim();
        }

        return methods;
    }
}
