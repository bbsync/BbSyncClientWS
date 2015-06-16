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
import org.bbsync.webservice.client.generated.UserWSStub;
import org.bbsync.webservice.client.generated.UserWSStub.AddressBookEntryVO;
import org.bbsync.webservice.client.generated.UserWSStub.ChangeUserBatchUid;
import org.bbsync.webservice.client.generated.UserWSStub.ChangeUserBatchUidResponse;
import org.bbsync.webservice.client.generated.UserWSStub.ChangeUserDataSourceId;
import org.bbsync.webservice.client.generated.UserWSStub.ChangeUserDataSourceIdResponse;
import org.bbsync.webservice.client.generated.UserWSStub.DeleteAddressBookEntry;
import org.bbsync.webservice.client.generated.UserWSStub.DeleteAddressBookEntryResponse;
import org.bbsync.webservice.client.generated.UserWSStub.DeleteObserverAssociation;
import org.bbsync.webservice.client.generated.UserWSStub.DeleteObserverAssociationResponse;
import org.bbsync.webservice.client.generated.UserWSStub.DeleteUser;
import org.bbsync.webservice.client.generated.UserWSStub.DeleteUserByInstitutionRole;
import org.bbsync.webservice.client.generated.UserWSStub.DeleteUserByInstitutionRoleResponse;
import org.bbsync.webservice.client.generated.UserWSStub.DeleteUserResponse;
import org.bbsync.webservice.client.generated.UserWSStub.GetAddressBookEntry;
import org.bbsync.webservice.client.generated.UserWSStub.GetAddressBookEntryResponse;
import org.bbsync.webservice.client.generated.UserWSStub.GetInstitutionRoles;
import org.bbsync.webservice.client.generated.UserWSStub.GetInstitutionRolesResponse;
import org.bbsync.webservice.client.generated.UserWSStub.GetObservee;
import org.bbsync.webservice.client.generated.UserWSStub.GetObserveeResponse;
import org.bbsync.webservice.client.generated.UserWSStub.GetServerVersion;
import org.bbsync.webservice.client.generated.UserWSStub.GetServerVersionResponse;
import org.bbsync.webservice.client.generated.UserWSStub.GetSystemRoles;
import org.bbsync.webservice.client.generated.UserWSStub.GetSystemRolesResponse;
import org.bbsync.webservice.client.generated.UserWSStub.GetUser;
import org.bbsync.webservice.client.generated.UserWSStub.GetUserInstitutionRoles;
import org.bbsync.webservice.client.generated.UserWSStub.GetUserInstitutionRolesResponse;
import org.bbsync.webservice.client.generated.UserWSStub.GetUserResponse;
import org.bbsync.webservice.client.generated.UserWSStub.InitializeUserWS;
import org.bbsync.webservice.client.generated.UserWSStub.InitializeUserWSResponse;
import org.bbsync.webservice.client.generated.UserWSStub.ObserverAssociationVO;
import org.bbsync.webservice.client.generated.UserWSStub.PortalRoleVO;
import org.bbsync.webservice.client.generated.UserWSStub.SaveAddressBookEntry;
import org.bbsync.webservice.client.generated.UserWSStub.SaveAddressBookEntryResponse;
import org.bbsync.webservice.client.generated.UserWSStub.SaveObserverAssociation;
import org.bbsync.webservice.client.generated.UserWSStub.SaveObserverAssociationResponse;
import org.bbsync.webservice.client.generated.UserWSStub.SaveUser;
import org.bbsync.webservice.client.generated.UserWSStub.SaveUserResponse;
import org.bbsync.webservice.client.generated.UserWSStub.UserFilter;
import org.bbsync.webservice.client.generated.UserWSStub.UserRoleVO;
import org.bbsync.webservice.client.generated.UserWSStub.UserVO;
import org.bbsync.webservice.client.generated.UserWSStub.VersionVO;

public abstract class UserProxyTool extends AbstractProxyTool implements ClientWebService, Serializable{
	private static final Logger logger = Logger.getLogger(UserProxyTool.class.getName());
    private static final long serialVersionUID = 4444000000000000L;
    private static String ws_url = null;
    private static String client_pw = null;
    private transient UserWSStub userWS = null;
    private transient BbContext session = null;
    private static final String CLIENT_PROGRAM_ID = "UserProxyTool";

	///////////////////////////////////////////////////////////////////////////
	//  Constructor & Setup  //////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
    protected UserProxyTool() {
        super();

        if (client_pw == null) {
            client_pw = config.getConfigClass(CLIENT_PROGRAM_ID, "sharedSecret");
        }

        if (ws_url == null) {
            ws_url = config.getConfigClass(CLIENT_PROGRAM_ID, "webServiceURL");
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////    
    private void loginAsTool() {
    	session = new BbContext();
    	session.loginAsTool(CLIENT_PROGRAM_ID, client_pw);
    	userWS = (UserWSStub) session.initializeClient(UserWSStub.class, ws_url);
    	_initializeUserWS(true);
    }
    
    private void loginAsUser(String user_name, String password) {
    	session = new BbContext();
    	session.loginAsUser(CLIENT_PROGRAM_ID, user_name, password);
    	userWS = (UserWSStub) session.initializeClient(UserWSStub.class, ws_url);
    	_initializeUserWS(true);
    }

    private void logout(){
    	session.logout();
    }
	
    ///////////////////////////////////////////////////////////////////////////
    //  All UserWS Methods  ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
	/**
	 * Returns the current version of this web service on the server.
	 * The VersionVO parameter is unused.
	 * 
	 * @param unused - this is an optional parameter put here to make the 
	 *                 generation of .net client applications from the wsdl 
	 *                 'cleaner' (0-argument methods do not generate clean 
	 *                 stubs and are much harder to have the same method name 
	 *                 across multiple Web Services in the same .net client).
	 *                 
	 * @return Returns the current version of this web service on the server. 
	 */
	protected VersionVO getServerVersion(VersionVO unused){
		loginAsTool();
		GetServerVersion version = new GetServerVersion();
		GetServerVersionResponse response = null;
        try {
            response = userWS.getServerVersion(version);
        } catch (RemoteException e) {
            logger.error("getServerVersion() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("getServerVersion() returned null.");
        	return null;
        }
        return response.get_return();
	}


	/**
	 * Sets the client version to version 1 and returns an appropriate session.
	 * With each release of this web service we will implement a new 
	 * initializeVersionXXX method.
	 * 
	 * @param ignore - this is an optional parameter put here to make the 
	 *                 generation of .net client applications from the wsdl 
	 *                 'cleaner' (0-argument methods do not generate clean stubs 
	 *                 and are much harder to have the same method name across 
	 *                 multiple Web Services in the same .net client).
	 *                 
	 * @return Returns true to indicate that the session has been initialized 
	 *         for the user web service.
	 */
	protected boolean initializeUserWS(boolean ignore){
		loginAsTool();
        boolean response = _initializeUserWS(ignore);
        logout();
        return response;
	}

	private boolean _initializeUserWS(boolean ignore){
    	InitializeUserWSResponse response = null;
    	InitializeUserWS init = new InitializeUserWS();
        init.setIgnore(ignore);
        try {
            response = userWS.initializeUserWS(init);
        } catch (RemoteException e) {
            logger.error("initializeUserWS() Encountered a remote exception.");
            logger.error(e);
            return false;
        }
		if(response==null){
        	logger.warn("initializeUserWS() returned null.");
        	return false;
        }
        return response.get_return();
	}
	
	/**
	 * Create/update one or more users.
	 * 
	 * @param user_vos - array of the users to be added/updated.
	 * 
	 * @return Returns the array of user ids that was created/updated - a null 
	 *         array indicates no operation.
	 */
	protected String[] saveUser(UserVO[] user_vos){
		loginAsTool();
		SaveUserResponse response = null;
		SaveUser save = new SaveUser();
		save.setUser(user_vos);
		try {
			response = userWS.saveUser(save);
        } catch (RemoteException e) {
            logger.error("saveUser() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("saveUser() returned null.");
        	return null;
        }
        return response.get_return();
	}


	/**
	 * Add an observer to the observee(user) association.
	 * 
	 * @param association - represents the observee(user) IDs to be associated 
	 *                      with observer ID.
	 *                      
	 * @return Returns array of observerIds that were successfully associated 
	 *         with corresponding observee ids - a null array indicates no 
	 *         operation - a null array value indicates that the observer user 
	 *         was not associated with observees due to invalid observer user 
	 *         ID.
	 */
	protected String[] saveObserverAssociation(ObserverAssociationVO[] associations){
		loginAsTool();
		SaveObserverAssociationResponse response = null;
		SaveObserverAssociation save = new SaveObserverAssociation();
		save.setAssociation(associations);
		try{
			response = userWS.saveObserverAssociation(save);
        } catch (RemoteException e) {
            logger.error("saveObserverAssociation() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("saveObserverAssociation() returned null.");
        	return null;
        }
        return response.get_return();
	}

	/**
	 * Create/update address book entries for the given user.  Login as 'user'
	 * is mandatory and this method cannot be used with login as 'tool'.
	 *  
	 * @param entry - book entry array to be added/updated.
	 * @param user_name - the username/login ID of the user with permission to
	 *                    save the address book entry.
	 * @param password  - the password of the user with permission to
	 *                    save the address book entry.
	 *                    
	 * @return Returns the array of address book entries IDs that were 
	 *         created/updated - a null array indicates no operation. 
	 */
	protected String[] saveAddressBookEntry(AddressBookEntryVO[] entrys, String user_name, String password){
		loginAsUser(user_name, password);
		SaveAddressBookEntryResponse response = null;
		SaveAddressBookEntry save = new SaveAddressBookEntry();
		save.setUser(entrys);
		try{
			response = userWS.saveAddressBookEntry(save);
        } catch (RemoteException e) {
            logger.error("saveAddressBookEntry() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("saveAddressBookEntry() returned null.");
        	return null;
        }
        return response.get_return();
	}

	/**
	 * Deletes the users specified by the user_ids parameter.
	 * 
	 * @param user_ids - an array of user IDs representing the users to be 
	 *                   deleted.  The IDs should be in the form "_nnn_1" where
	 *                   nnn is an integer.
	 * 
	 * @return  Returns an array of user IDs representing the users that were 
	 *          successfully deleted.  A null array indicates no operation - an 
	 *          empty/null string value indicates user has no permission to 
	 *          delete.
	 */
	protected String[] deleteUser(String[] user_ids){
		loginAsTool();
		DeleteUserResponse response = null;
		DeleteUser delete = new DeleteUser();
		delete.setUserId(user_ids);
		try{
			response = userWS.deleteUser(delete);
        } catch (RemoteException e) {
            logger.error("deleteUser() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("deleteUser() returned null.");
        	return null;
        }
        return response.get_return();
	}

	/**
	 * Removes users with the given institution/portal role.
	 * 
	 * @param ins_role_ids - the institution/portal role that will determine
	 *                       which users to delete.  The IDs should be in the 
	 *                       form "_nnn_1" where nnn is an integer.
	 *                       
	 * @return Returns an array of user IDs that were successfully removed. A 
	 *         null value indicates no-operation since no users qualified to 
	 *         be removed based on the given values.
	 */
	protected String[] deleteUsersByInstitutionRole(String[] ins_role_ids){
		loginAsTool();
		DeleteUserByInstitutionRoleResponse response = null;
		DeleteUserByInstitutionRole delete = new DeleteUserByInstitutionRole();
		delete.setInsRoleId(ins_role_ids);
		try {
			response = userWS.deleteUserByInstitutionRole(delete);
        } catch (RemoteException e) {
            logger.error("deleteUserByInstitutionRole() Encountered a remote exception.");
            logger.error(e);
	        logout(); //be sure to logout before returning
	        return null;
        }
        logout();
		if(response==null){
        	logger.warn("deleteUserByInstitutionRole() returned null.");
        	return null;
        }
        return response.get_return();
	}
	

	/**
	 * Removes the address book entries for the given user The login as 'user' 
	 * is mandatory and this method cannot be used with login as 'tool'. 
	 * 
	 * @param entry_ids - an array of address book entries to be removed.  The 
	 *                    IDs should be in the form "_nnn_1" where nnn is an 
	 *                    integer.
	 * @param user_name - the username/login ID of the user with permission to
	 *                    delete the address book entry.
	 * @param password  - the password of the user with permission to
	 *                    delete the address book entry. 
	 * 
	 * @return Returns array of address book entry_ids - a null array indicates 
	 *         no operation - a valid array value indicates success for that 
	 *         corresponding entryId - a empty/null string value indicates user
	 *         has no permission to delete.
	 */
	protected String[] deleteAddressBookEntry(String[] entry_ids, String user_name, String password){
		loginAsUser(user_name, password);
		DeleteAddressBookEntryResponse response = null;
		DeleteAddressBookEntry delete = new DeleteAddressBookEntry();
		delete.setEntryId(entry_ids);
		try {
			response = userWS.deleteAddressBookEntry(delete);
		} catch (RemoteException e) {
			logger.error("deleteAddressBookEntry() Encountered a remote exception.");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("deleteAddressBookEntry() returned null.");
    		return null;
    	}
    	return response.get_return();
	}

	/**
	 * Removes the specified observer to observee associations.
	 * 
	 * @param associations - an array of the observer associations to be 
	 *                       deleted.
	 *                        
	 * @return Returns an array of observer association IDs that were 
	 *         successfully deleted.  A null array indicates no operation.  A 
	 *         null array value indicates that the observer/observee 
	 *         disassociation failed.
	 */
	protected String[] deleteObserverAssociation(ObserverAssociationVO[] associations){
		loginAsTool();
		DeleteObserverAssociationResponse response = null;
		DeleteObserverAssociation delete = new DeleteObserverAssociation();
		delete.setAssociation(associations);
		try {
			response = userWS.deleteObserverAssociation(delete);
		} catch (RemoteException e) {
			logger.error("deleteObserverAssociation() Encountered a remote exception.");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("deleteObserverAssociation() returned null.");
    		return null;
    	}
    	return response.get_return();
	}

	/**
	 * Load users based on the filter parameters.
	 * 
	 * @param user_filter - contains the user search criteria.
     * 
     * @return Returns an array of UserVOs matching the search criteria.
     */
    protected UserVO[] getUser(UserFilter user_filter){
		loginAsTool();
		GetUserResponse response = null;
		GetUser get = new GetUser();
		get.setFilter(user_filter);
		try {
			response = userWS.getUser(get);
		} catch (RemoteException e) {
			logger.error("getUser() Encountered a remote exception.");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("getUser() returned null.");
    		return null;
    	}
    	return response.get_return();
    }

    /**
     * Loads address book entries for the given user based on the filter 
     * parameters. Login as 'user' is mandatory and this method cannot be 
     * used with login as 'tool'.
     * 
     * @param filter    - The UserFilter configured for the get operation.
     * 					  According to blackboard.ws.user.UserWSConstants, 
     *                    there are two get configurations available:
     *                    GET_ADDRESS_BOOK_ENTRY_BY_ID (int 8)
     *                    GET_ADDRESS_BOOK_ENTRY_BY_CURRENT_USERID (int 9)
     *                    
     * @param user_name - the username/login ID of the user with permission to
	 *                    load the address book entry.
	 * @param password  - the password of the user with permission to
	 *                    load the address book entry. 
     *  
     * @return Returns an array of address book entries matching the UserFilter
     */
    protected AddressBookEntryVO[] getAddressBookEntry(UserFilter filter, String user_name, String password){
		loginAsUser(user_name, password);
		GetAddressBookEntryResponse response = null;
		GetAddressBookEntry get = new GetAddressBookEntry();
		get.setFilter(filter);
		try {
			response = userWS.getAddressBookEntry(get);
		} catch (RemoteException e) {
			logger.error("getAddressBookEntry() Encountered a remote exception.");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("getAddressBookEntry() returned null.");
    		return null;
    	}
    	return response.get_return();
    }
    
	/**
	 * Load observer association objects for the given observer user IDs.
	 * 
	 * @param observer_ids - array of observer user ids.  The IDs should be in 
	 *                       the form "_nnn_1" where nnn is an integer.
	 * @return Returns an array of observer/observee association matching the 
	 *         UserFilter.
	 */
	protected ObserverAssociationVO[] getObservee(String[] observer_ids){
		loginAsTool();
		GetObserveeResponse response = null;
		GetObservee get = new GetObservee();
		get.setObserverId(observer_ids);
		try {
			response = userWS.getObservee(get);
		} catch (RemoteException e) {
			logger.error("getObservee() Encountered a remote exception.");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("getgetObservee() returned null.");
    		return null;
    	}
    	return response.get_return();
	}

	/**
	 * Changes a user's batch UID.
	 * 
	 * @param original_batch_uid - the user's original batch UID.
	 * @param batch_uid - new batch UID.
	 * 
	 * @return Returns true if saved successfully, otherwise retruns false.
	 */
	protected boolean changeUserBatchUid(String original_batch_uid, String batch_uid){
		loginAsTool();
		ChangeUserBatchUidResponse response = null;
		ChangeUserBatchUid change = new ChangeUserBatchUid();
		change.setBatchUid(batch_uid);
		change.setOriginalBatchUid(original_batch_uid);
		try {
			response = userWS.changeUserBatchUid(change);
		} catch (RemoteException e) {
			logger.error("changeUserBatchUid() Encountered a remote exception.");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return false;
		}
    	logout();
    	if(response==null){
    		logger.warn("changeUserBatchUid() returned null.");
    		return false;
    	}
    	return response.get_return();
	}
    
	/**
	 * Change a user's data source ID.
	 * 
	 * @param user_id - user's ID for which the data source is replaced.  The 
	 *                  ID should be in the form "_nnn_1" where nnn is an 
	 *                  integer.
	 * @param new_data_source_id - new data source ID.  The IDs should be in 
	 *                             the form "_nnn_1" where nnn is an integer.
	 *                             
	 * @return Returns true if save successfully, otherwise returns false.
	 */
	protected boolean changeUserDataSourceId(String user_id, String new_data_source_id){
		loginAsTool();
		ChangeUserDataSourceIdResponse response = null;
		ChangeUserDataSourceId change = new ChangeUserDataSourceId();
		change.setDataSourceId(new_data_source_id);
		change.setUserId(user_id);
		try {
			response = userWS.changeUserDataSourceId(change);
		} catch (RemoteException e) {
			logger.error("changeUserDataSourceId() Encountered a remote exception.");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return false;
		}
    	logout();
    	if(response==null){
    		logger.warn("changeUserDataSourceId() returned null.");
    		return false;
    	}
    	return response.get_return();
	}

	/**
	 * Loads all system roles in Academic Suite.
	 * 
	 * @param filter - ignored.
	 * 
	 * @return Returns an array of all system roles defined in the AS.
	 */
	protected String[] getSystemRoles(String[] filter){
		loginAsTool();
		GetSystemRolesResponse response = null;
		GetSystemRoles get = new GetSystemRoles();
		get.setFilter(filter);
		try{
			response = userWS.getSystemRoles(get);
		} catch (RemoteException e){
			logger.error("getSystemRoles() Encountered a remote exception.");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("getSystemRoles() returned null.");
    		return null;
    	}
    	return response.get_return();
	}

	/**
	 * Loads all portal/institution roles in Academic Suite
	 * 
	 * @param ids - ignored.
	 * 
	 * @return Returns an array of portal/institution roles.
	 */
	protected PortalRoleVO[] getInstitutionRoles(String[] ids){
		loginAsTool();
		GetInstitutionRolesResponse response = null;
		GetInstitutionRoles get = new GetInstitutionRoles();
		get.setIds(ids);
		try{
			response = userWS.getInstitutionRoles(get);
		} catch (RemoteException e){	
			logger.error("getInstitutionRoles(String[] ids) Encountered a remote exception.");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
    	logout();
    	if(response==null){
    		logger.warn("getInstitutionRoles() returned null.");
    		return null;
    	}
    	return response.get_return();
	}

	/**
	 * Load portal/institution roles for a given user.  For SP13 and higher, 
	 * this method only returns secondary institution roles.
	 * 
	 * @param user_id - array of user IDs for whom the portal roles are 
	 *                  requested.  The IDs should be in the form "_nnn_1" 
	 *                  where nnn is an integer.
	 *                  
	 * @return Returns an array of institution/portal roles for the requested 
	 *         users - if userId array null, no-operation, return null.
	 */
	protected UserRoleVO[] getUserInstitutionRoles(String[] user_id){
		loginAsTool();
		GetUserInstitutionRolesResponse response = null;
		GetUserInstitutionRoles get = new GetUserInstitutionRoles();
		get.setUserId(user_id);
		try{
			response = userWS.getUserInstitutionRoles(get);
		} catch (RemoteException e){
			logger.error("getInstitutionRoles(String[] userIds) Encountered a remote exception.");
	        logger.error(e.getMessage());
	        logout(); //be sure to logout before returning
	        return null;
		}
		logout();
    	if(response==null){
    		logger.warn("getInstitutionRoles() returned null.");
    		return null;
    	}
		return response.get_return();
	}
}