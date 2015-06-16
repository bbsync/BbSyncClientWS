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

import java.io.Serializable;

/**
 * This Interface defines standard operations common to all ClientWebServices.
 */
public interface ClientWebService {
	
    ///////////////////////////////////////////////////////////////////////////
    //  The concrete implementation of this method is designed to save a     //
    //  single ClientWebService object to the LMS host system.               //
    //  @return See the implementing ClientWebService class for the precise  //
    //          return type.                                                 //
    ///////////////////////////////////////////////////////////////////////////
    public Serializable persist();

    ///////////////////////////////////////////////////////////////////////////
    //  The concrete implementation of this method is designed to get a      //
    //  single ClientWebService object to the LMS host system.               //
    //  @return See the implementing ClientWebService class for the precise  //
    //          return type.                                                 // 
    ///////////////////////////////////////////////////////////////////////////
    public Object retrieve();

    ///////////////////////////////////////////////////////////////////////////
    //  The concrete implementation of this method is designed to delete a   //
    //  single ClientWebService object from the LMS host system.             //
    //  @return Returns true if the ClientWebService object was successfully //
    //          deleted.  Otherwise, returns false.                          //
    /////////////////////////////////////////////////////////////////////////// 
    public boolean delete();
}