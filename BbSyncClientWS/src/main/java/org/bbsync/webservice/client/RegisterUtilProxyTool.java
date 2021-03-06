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

public class RegisterUtilProxyTool {
    RegisterProxyTool regTool = null;

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        RegisterUtilProxyTool app = new RegisterUtilProxyTool();
        app.run();
    }

    private void run() {
        regTool = new RegisterProxyTool("UtilProxyTool");

        if (regTool.registerTool()) {
            System.out.println("UtilProxyTool was sucessfully registered.");
        } else {
            System.out.println(
                "UtilProxyTool was not sucessfully registered.  See log for details.");
        }
    }
}
