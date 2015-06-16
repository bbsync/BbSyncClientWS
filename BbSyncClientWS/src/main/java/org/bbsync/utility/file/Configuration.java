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

package org.bbsync.utility.file;

import org.apache.log4j.Logger;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;

import org.jdom2.input.SAXBuilder;

import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;
import java.util.List;


public class Configuration {
    private static Configuration myInstance = new Configuration();

    //TODO:  Need to refactor this so that is makes some sense! Maybe an actual plist format?...
    private String localHome = null;
    private Document config = null;

    /**
     * This private constructor is defined so the compiler won't generate a
     * default public constructor.
     */
    private Configuration() {
        /*
         * * Using Maven build now, don't need to load config info directly from file system * *
         * XMLFileReader reader = new XMLFileReader();
         * config = reader.read("conf.xml");
         */

        //This code is how Maven loads resources
        InputStream is = getClass().getResourceAsStream("/conf.xml");

        try {
            config = new SAXBuilder().build(is);
        } catch (JDOMException e) {
            Logger.getLogger(this.getClass().getName())
                  .error("Error processing conf.xml.");
            Logger.getLogger(this.getClass().getName()).error(e);
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName())
                  .error("Couldn't load conf.xml.");
            Logger.getLogger(this.getClass().getName()).error(e);
        }
    }

    /**
     * Return a reference to the only instance of this class.
     */
    public static Configuration getInstance() {
        return myInstance;
    } // getInstance()

    public String getLocalHome() {
        return this.localHome;
    }

    public void setLocalHome(String path) {
        this.localHome = path + System.getProperty("file.separator");
    }

    private static boolean isNamedElement(Element elem, String name) {
        Attribute elemName = elem.getAttribute("name");

        if (elemName.getValue().equals(name)) {
            return true;
        }

        return false;
    }

    public static String getNamedParameterValue(List<Element> parameters,
        String paramName) {
        Iterator<Element> params = parameters.iterator();

        while (params.hasNext()) {
            Element param = params.next();

            if (isNamedElement(param, paramName)) {
                return param.getAttribute("value").getValue();
            }
        }

        return null;
    }

    public String getConfigClass(String className, String configName) {
        List<Element> configs = config.getRootElement()
                                      .getChildren("ClassConfig");
        Iterator<Element> classConfigs = configs.iterator();

        while (classConfigs.hasNext()) {
            Element classConfig = classConfigs.next();

            if (isNamedElement(classConfig, className)) {
                return getNamedParameterValue(classConfig.getChildren("param"),
                    configName);
            }
        }

        return null;
    }

    public Element getConfigClassByValue(String className, String configName,
        String configValue) {
        List<Element> configs = config.getRootElement()
                                      .getChildren("ClassConfig");
        Iterator<Element> classConfigs = configs.iterator();

        while (classConfigs.hasNext()) {
            Element classConfig = classConfigs.next();

            if (isNamedElement(classConfig, className) &&
                    getNamedParameterValue(classConfig.getChildren("param"),
                        configName).equals(configValue)) {
                return classConfig;
            }
        }

        return null;
    }
}
