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

import org.apache.axis2.wsdl.WSDL2Java;

import org.bbsync.utility.file.Configuration;

import java.util.logging.Logger;


/**
 * This application will retrieve web service WSDL files
 * and convert them to Java source files
 */
public class WsdlCodeGenerator {
    private static Configuration config = Configuration.getInstance();

    public static void main(String[] args) throws Exception {
        wsdl2code(config.getConfigClass("ContextProxyTool", "webServiceURL"));
        wsdl2code(config.getConfigClass("UtilProxyTool", "webServiceURL"));
        wsdl2code(config.getConfigClass("CourseProxyTool", "webServiceURL"));
        wsdl2code(config.getConfigClass("UserProxyTool", "webServiceURL"));
    	wsdl2code(config.getConfigClass("CourseMembershipProxyTool", "webServiceURL"));
    }

    private static void wsdl2code(String wsdlURI) throws Exception {
        /*
         * The arguments and their values below represent:
         * S - where to place the generated code
         * R - where to place any resources generated
         * p - the package to use for the generated code
         * d - create client classes compatible with the specified data binding framework
         * uri - location of the WSDL file to use
         *
         * See:  http://axis.apache.org/axis2/java/core/docs/reference.html
         */
        Logger.getLogger(WsdlCodeGenerator.class.getName())
              .info("Generating Java code from WSDL...");
        WSDL2Java.main(new String[] {
                "-S", "src/main/java", "-R", "src/main/resources", "-p",
                "org.bbsync.webservice.client.generated", "-d", "adb",
                "-uri", wsdlURI + "?wsdl"
            });
        Logger.getLogger(WsdlCodeGenerator.class.getName()).info("Done!");
    }
}
