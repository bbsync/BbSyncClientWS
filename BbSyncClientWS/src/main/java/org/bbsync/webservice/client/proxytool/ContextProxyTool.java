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

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.client.Stub;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.log4j.Logger;
import org.apache.rampart.handler.WSSHandlerConstants;
import org.apache.rampart.handler.config.OutflowConfiguration;
import org.apache.ws.security.WSPasswordCallback;
import org.apache.ws.security.handler.WSHandlerConstants;
import org.bbsync.webservice.client.ClientWebService;
import org.bbsync.webservice.client.generated.ContextWSStub;
import org.bbsync.webservice.client.generated.ContextWSStub.CourseIdVO;
import org.bbsync.webservice.client.generated.ContextWSStub.DeactivateTool;
import org.bbsync.webservice.client.generated.ContextWSStub.DeactivateToolResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.DeactivateToolResultVO;
import org.bbsync.webservice.client.generated.ContextWSStub.EmulateUser;
import org.bbsync.webservice.client.generated.ContextWSStub.EmulateUserResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.ExtendSessionLife;
import org.bbsync.webservice.client.generated.ContextWSStub.ExtendSessionLifeResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.GetMemberships;
import org.bbsync.webservice.client.generated.ContextWSStub.GetMembershipsResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.GetMyMembershipsResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.GetRequiredEntitlements;
import org.bbsync.webservice.client.generated.ContextWSStub.GetRequiredEntitlementsResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.GetServerVersion;
import org.bbsync.webservice.client.generated.ContextWSStub.GetServerVersionResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.GetSystemInstallationIdResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.InitializeResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.InitializeVersion2Response;
import org.bbsync.webservice.client.generated.ContextWSStub.Login;
import org.bbsync.webservice.client.generated.ContextWSStub.LoginResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.LoginTicket;
import org.bbsync.webservice.client.generated.ContextWSStub.LoginTicketResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.LoginTool;
import org.bbsync.webservice.client.generated.ContextWSStub.LoginToolResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.LogoutResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.RegisterTool;
import org.bbsync.webservice.client.generated.ContextWSStub.RegisterToolResponse;
import org.bbsync.webservice.client.generated.ContextWSStub.RegisterToolResultVO;
import org.bbsync.webservice.client.generated.ContextWSStub.VersionVO;


/**
 * A Blackboard Context provides the initial methods required for session 
 * creation and is therefore required to be used before any other Web Services
 * can be utilized. The basic usage for a user session is as follows:
 * 
 * //setup UserToken WS-Security header with userid="session", password="nosession", type="PasswordText"
 * String sessionId = initialize();
 * 
 * // change your UserToken.password to be sessionId and make sure it is passed on all subsequent requests
 * login("user", "password", "clientvendor", "clientapp", null, 3600);
 * ...
 * logout(); // This will invalidate your session.
 * 
 * But there are other ways to login as well (loginTool and loginTicket). In 
 * addition to having to login to get an authenticated session, you also have 
 * to be aware of session lifetime. When logging in you can declare how long 
 * you expect to need this session (up to a max of 24 hours). After that time 
 * period your session will no longer work. You can use extendSessionLife to 
 * extend the life of your session at any time so it is possible to have a 
 * long-running client application using the same session, but you must 
 * explicitly keep extending the session life. Note that all login methods 
 * require a vendor and program identifier. Leading and trailing spaces will be
 * trimmed and they are case-insensitive. 
 */
@SuppressWarnings("deprecation")
public abstract class ContextProxyTool extends AbstractProxyTool
    implements ClientWebService, Serializable {
    private static final long serialVersionUID = 1111000000000000L;
    private static final String CLIENT_PROGRAM_ID = "ContextProxyTool";
    private static final Logger logger = Logger.getLogger(ContextProxyTool.class.getName());
    private transient ContextWSStub contextWS   = null;
    private static String proxy_tool_password   = null;
    private static String context_ws_url        = null;
    protected static String client_vendor_id      = null;
    protected static Long default_timeout_seconds = null;
    private static Long server_version          = null;
    private ContextProxyTool.PasswordCallbackClass pwcb = null;
    private HttpClient _httpClient = null;
    private ConfigurationContext ctx = null;
    private static String modulePath = null;
    private static String session_id = null;
    private static String blackboardServerURL = null;
    
	///////////////////////////////////////////////////////////////////////////
	//  Constructor  //////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
    protected ContextProxyTool() {
        if (context_ws_url == null) {
            context_ws_url = config.getConfigClass("ContextProxyTool",
                    "webServiceURL");
        }
        if (default_timeout_seconds == null) {
        	String long_str = config.getConfigClass("GLOBAL", "sessionTimeOutSeconds"); 
        	default_timeout_seconds = new Long(long_str);
        }
        if (client_vendor_id == null) {
        	client_vendor_id = config.getConfigClass("GLOBAL", "clientVendorId");
        }
        if (proxy_tool_password == null) {
        	proxy_tool_password = config.getConfigClass(CLIENT_PROGRAM_ID, "sharedSecret");
        }
        if (blackboardServerURL == null) {
            blackboardServerURL = config.getConfigClass("GLOBAL", "blackboardServerURL");
        }        
        if (modulePath == null) {
            modulePath = config.getConfigClass("GLOBAL", "modulePath");
        }
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  All ContextWS Methods  ////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Deactivates the proxy tool associated with the current session. The 
     * caller must be logged in as the tool that it wishes to deactivate. The 
     * status field of the returned object will be true if the deactivation 
     * succeeds, and false otherwise. In case of failure, consult the 
     * failureError field for the reasons why.
     * 
     * Because you must be logged in as the tool to be deactivated in order to
     * use this method, be sure that the tool has been configured to use the
     * tool method "Context.WS:deactivateTool" in conf.xml.
     * 
     * @param ignore - Currently unused
     * @return Returns the results of deactivation (true, false or error code).
     */
    protected DeactivateToolResultVO deactivateTool(String ignore) {
    	DeactivateToolResponse response = null;
    	DeactivateTool deactivate = new DeactivateTool();
    	deactivate.setIgnore(ignore);
    	//Don't setupSession() or initializeSession().
    	//To use this method, your session should already be active.
    	try {
			response = contextWS.deactivateTool(deactivate);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Deactivate Tool");
	        logger.error(e.getMessage());
	        return null;
		}
    	//don't cleanupSession() here.  Do it at logout().
    	if(response==null){
    		logger.warn("deactivateTool returned null.");
    		return null;
    	}
    	return response.get_return();
    }

    /**
     * emulateUser can be used to switch the 'current' user for your Web 
     * Service session to someone else. Once you have emulated a user you will 
     * lose all your current entitlements (including tool based entitlements if
     * you logged in as a 'tool'). To regain those tool entitlements you either
     * have to logout/initialize/logintool again or 
     * 
     * session = emulateUser(session,null); 
     * 
     * (If you were originally logged in as a user with this entitlement (i.e. 
     * 'administrator') then you cannot emulate a null user - null is only valid 
     * if originally logged in as a tool.  
     * 
     * If you use emulate user with tool login, be sure to grant the following
     * entitlement: webservices.tools.EMULATE.  This can be done by adding
     * "Context.WS:emulateUser" to the list of entitlements for the particular 
     * tool in conf.xml.  You'll then have to re-register the tool for the
     * entitlement to take effect.  
     * 
     * When emulating a user, make sure that the emulated user has the correct 
     * System privileges to perform the task. This means that the user should 
     * have a system role assigned that is not "None", "Guest", or "User".  
     * System privileges can be assigned to System roles by using the Admin GUI. 
     * 
     * @param user_to_emulate - username/login id of the user you wish to 
     *                          become.  Null will 'unemulate' any user.
     * @return Returns true if successful.  Otherwise returns false.
     */
    protected boolean emulateUser(String user_to_emulate) {
    	EmulateUserResponse response = null;
    	EmulateUser emulate_user = new EmulateUser();
    	emulate_user.setUserToEmulate(user_to_emulate);
    	//Don't setupSession() or initializeSession().
    	//To use this method, your session should already be active.
    	try {
			response = contextWS.emulateUser(emulate_user);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Emulate User");
	        logger.error(e.getMessage());
	        return false;
		}
    	//don't cleanupSession() here.  Do it at logout().
    	if(response==null){
    		logger.warn("emulateUser returned null.");
    		return false;
    	}
    	return response.get_return();
    }

    /**
     * Call this to extend the life of your Web Service session.  Note that you
     * cannot set a session to be live for more than 24 hours from the current 
     * time.
     * 
     * @param additional_seconds - the number of additional seconds you want to 
     *                             add to the life of your session.
     * @return Returns true if successful.  Otherwise returns false.
     */
    protected boolean extendSessionLife(Long additional_seconds) {
    	ExtendSessionLifeResponse response = null;
    	ExtendSessionLife extend = new ExtendSessionLife();
    	extend.setAdditionalSeconds(additional_seconds);
    	//Don't setupSession() or initializeSession().
    	//To use this method, your session should already be active.
    	try {
			response = contextWS.extendSessionLife(extend);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Extend SessionLife");
	        logger.error(e.getMessage());
	        return false;
		}
    	//Don't cleanupSession() here.  Do it at logout().
    	if(response==null){
    		logger.warn("extendSessionLife returned null.");
    		return false;
    	}
    	return response.get_return();
    }

    /**
     * If session is a user then return the courses they are enrolled in.
     * If session is a tool then return an empty set.
     * 
     * @return Returns an array of courseidvo's representing the currently 
     *         logged-in user's memberships.
     */
    protected CourseIdVO[] getMyMemberships() {
    	GetMyMembershipsResponse response = null;
    	//Don't setupSession() or initializeSession().
    	//To use this method, your session should already be active.
    	try {
			response = contextWS.getMyMemberships();
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Get My Memberships");
	        logger.error(e.getMessage());
	        return null;
		}
    	//don't cleanupSession() here.  Do it at logout().
    	if(response==null){
    		logger.warn("getMyMemberships returned null.");
    		return null;
    	}
    	return response.get_return();
    }

    /**
     * If session is a user and userid==session.userid then return the courses 
     * they are enrolled in.
     * If session is a user and userid!=session.userid then return an empty 
     * set.
     * If session is a tool then return the courses the given user is enrolled 
     * in.
     * 
     * Entitlement Required: webservices.tools.getMemberships.VIEW
     * 
     * @param user_id - the user you wish to get memberships for
     * @return Returns an array of courseidvo's representing the user's 
     *         memberships.
     */
    protected CourseIdVO[] getMemberships(String user_id) {
    	GetMembershipsResponse response = null;
    	GetMemberships memberships = new GetMemberships();
    	memberships.setUserid(user_id);
    	//Don't setupSession() or initializeSession().
    	//To use this method, your session should already be active.
    	try {
			response = contextWS.getMemberships(memberships);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Get Memberships");
	        logger.error(e.getMessage());
	        return null;
		}
    	//don't cleanupSession() here.  Do it at logout().
    	if(response==null){
    		logger.warn("getMemberships returned null.");
    		return null;
    	}
    	return response.get_return();
    }

    /**
     * This will return a list of the entitlements the specified method
     * requires for successful completion. Typical use of this method is during
     * client application installation to build up the arguments required when 
     * calling registerTool.
     * 
     * @param method - the name of a method on this Web Service that you plan 
     *                 to call while using tool based authentication.
     * @return Returns a String[] array of entitlement UIDs.
     */
    protected String[] getRequiredEntitlements(String method) {
        GetRequiredEntitlementsResponse response = null;
        GetRequiredEntitlements gre = new GetRequiredEntitlements();
        gre.setMethod(method);
        setupSession();
        try {
            response = contextWS.getRequiredEntitlements(gre);
        } catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Get Required Entitlements");
	        logger.error(e.getMessage());
	        return null;
		}
    	cleanupSession();
    	if(response==null){
    		logger.warn("getRequiredEntitlements returned null.");
    		return null;
    	}
    	return response.get_return();
    }

    /**
     * Returns the current version of this web service on the server. In the 
     * future this can be used to adjust client behavior to match the 
     * capabilities of the server you are talking to.
     * 
     * @param unused - this is an optional parameter put here to make the 
     *                 generation of .net client applications from the wsdl 
     *                 'cleaner' (0-argument methods do not generate clean 
     *                 stubs and are much harder to have the same method name 
     *                 across multiple Web Services in the same .net client)
     * @return Returns a VersionVO representing the current version of this web
     *         service on the server.
     */
    protected VersionVO getServerVersion(VersionVO unused) {
        setupSession();
        VersionVO response = _getServerVersion(unused);
        cleanupSession();
    	return response;
    }

    private VersionVO _getServerVersion(VersionVO unused) {
    	GetServerVersionResponse response = null;
        GetServerVersion version = new GetServerVersion();
        version.setUnused(unused);
    	//Don't setupSession() or initializeSession().
    	//To use this method, your session should already be active.
        try {
             response = contextWS.getServerVersion(version);
        } catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Get Server Version");
	        logger.error(e.getMessage());
	        return null;
		}
        //don't cleanupSession() here.  Do it at logout().
    	if(response==null){
    		logger.warn("getServerVersion returned null.");
    		return null;
    	}
    	return response.get_return();
    }
    
    /**
     * Each installation of Academic Suite has a unique 32-character identifier 
     * associated with it.  You can use this method to retrieve this value for 
     * the target server.
     * 
     * @return Returns a 32 character String of hex digits.
     */
    protected String getSystemInstallationId() {
    	GetSystemInstallationIdResponse response = null;
    	setupSession();
    	try {
			response = contextWS.getSystemInstallationId();
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Get System Installation ID");
	        logger.error(e.getMessage());
	        return null;
		}
    	cleanupSession();
    	if(response==null){
    		logger.warn("getSystemInstallationId returned null.");
    		return null;
    	}
    	return response.get_return();
    }

    /**
     * Sets the client version to version 1 and returns an appropriate session.
     * One of initialize or initializeVersion2 must be called prior to logging 
     * in so that the server knows what version of the web service the client 
     * was developed against and can adjust behavior to match the expected 
     * behavior from the client. NOTE that this session is only valid for 5 
     * minutes - in other words you must login within 5 minutes or call 
     * initialize again. According to the Blackboard web services API, with 
     * each release of this web service, a new initializeVersionXXX method will
     * be implemented.
     * 
     * Setting the GLOBAL "serverVersion" parameter in conf.xml to "1" will 
     * make this the default initialization method.  If serverVersion is not 
     * set, or is set to a value other than "1" or "2", the server version will
     * be determined automatically.
     * 
     * @return Returns a a 32 character String of hex digits representing the 
     *         unique session ID.  This ID will be passed in all subsequent 
     *         requests as the session password.
     */
    protected String initialize() {
    	setupSession();
    	String response = _initialize();
    	cleanupSession();
    	return response;
    }
    
    private String _initialize() {
    	InitializeResponse response = null;
    	//Don't setupSession() or initializeSession().
    	//To use this method, your session should already be active.
    	try {
            response = contextWS.initialize();
        } catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Initialize");
	        logger.error(e.getMessage());
	        return null;
		}
    	//don't cleanupSession() here.  Do it at logout().
    	if(response==null){
    		logger.warn("initialize returned null.");
    		return null;
    	}
    	return response.get_return();
    }

    /**
     * Sets the client version to version 2 and returns an appropriate session.
     * One of initialize or initializeVersion2 must be called prior to logging
     * in so that the server knows what version of the web service the client 
     * was developed against and can adjust behavior to match the expected 
     * behavior from the client. NOTE that this session is only valid for 5 
     * minutes - in other words you must login within 5 minutes or call 
     * initializeVersion2 again.
     * 
     * Setting the GLOBAL "serverVersion" parameter in conf.xml to "2" will 
     * make this the default initialization method.  If serverVersion is not 
     * set, or is set to a value other than "1" or "2", the server version will
     * be determined automatically.
     * 
     * @return Returns a 32 character String of hex digits representing the 
     *         unique session ID.  This ID will be passed in all subsequent 
     *         requests as the session password.
     */
    protected String initializeVersion2() {
    	setupSession();
    	String response = _initializeVersion2();
    	cleanupSession();
    	return response;
    }
    
    private String _initializeVersion2() {
    	InitializeVersion2Response response = null;
    	//Don't setupSession() or initializeSession().
    	//To use this method, your session should already be active.
    	try {
            response = contextWS.initializeVersion2();
        } catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Initialize Version2");
	        logger.error(e.getMessage());
	        return null;
		}
    	//don't cleanupSession() here.  Do it at logout().
    	if(response==null){
    		logger.warn("initializeVersion2 returned null.");
    		return null;
    	}
    	return response.get_return();
    }


    /**
     * Logs in user using the given password. ONLY works if the authentication 
     * source is the internal AS database.
     * 
     * @param user_name - The user name/login ID of the user, not the internal
     *                    Blackboard ID.
     * @param password - The password associated with the given user name.
     * @param client_vendor_id - the "clientVendorId" as defined in conf.xml
     * @param proxy_tool_name  - the registered name of the proxy tool.  See 
     *                           <ClassConfig name=" ... "> in conf.xml
     * @param login_extra_info - not used at this time.
     * @param timeout_seconds  - The number of seconds to keep this session
     *                           object valid - see "sessionTimeOutSeconds" in
     *                           conf.xml to configure the default timeout 
     *                           value.  You can call extendSessionLife() to 
     *                           keep a session alive longer.
     *                           
     * @return If successful, establishes a user-authenticated session valid for 
     *         use in other calls and returns true. Otherwise returns false.
     */
    protected boolean login(String user_name, String user_password,
    						String client_vendor_id, String tool_name, 
    						String login_extra_info, Long timeout_seconds) {
    	LoginResponse response = null;
    	Login login = new Login();
    	login.setClientProgramId(tool_name);
    	login.setClientVendorId(client_vendor_id);
    	login.setExpectedLifeSeconds(timeout_seconds);
    	login.setLoginExtraInfo(login_extra_info);
    	login.setPassword(user_password);
    	login.setUserid(user_name);
    	setupSession();
    	initializeSession();
    	try {
			response = contextWS.login(login);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Login");
	        logger.error(e.getMessage());
	        return false;
		}
    	//don't cleanupSession() here.  Do it at logout().
    	if(response==null){
    		logger.warn("login returned null.");
    		return false;
    	}
    	return response.get_return();
    }
    
    /**
     * Logs in a session based on the given ticket. The ticket will contain 
     * information about the user it is authenticated for as well as timestamp
     * information. It is only valid for a short time after it is generated 
     * (i.e. approximately 5 minutes) so you should be sure to use it 
     * immediately to get a sessionid if you are unsure how long it will be in
     * your client application before you need the Web Service session.
     * 
     * @param ticket - Ticket provided during proxy tool negotiation.
     * @param client_vendor_id - the "clientVendorId" as defined in conf.xml
     * @param proxy_tool_name  - the registered name of the proxy tool.  See 
     *                           <ClassConfig name=" ... "> in conf.xml
     * @param login_extra_info - not used at this time.
     * @param timeout_seconds  - The number of seconds to keep this session
     *                           object valid - see "sessionTimeOutSeconds" in
     *                           conf.xml to configure the default timeout 
     *                           value.  You can call extendSessionLife() to 
     *                           keep a session alive longer.
     *                           
     * @return If successful, establishes an authenticated session valid for 
     *         use in other calls (either tool or user based - depends on 
     *         ticket) and returns true.  Otherwise, returns false.
     */
    protected boolean loginTicket(String ticket, String client_vendor_id,
    							  String proxy_tool_name, String login_extra_info, 
    							  Long timeout_seconds) {
    	LoginTicketResponse response = null;
    	LoginTicket login = new LoginTicket();
    	login.setClientProgramId(proxy_tool_name);
    	login.setClientVendorId(client_vendor_id);
    	login.setExpectedLifeSeconds(timeout_seconds);
    	login.setLoginExtraInfo(login_extra_info);
    	login.setTicket(ticket);
    	setupSession();
    	initializeSession();
    	try {
			response = contextWS.loginTicket(login);
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Login Ticket");
	        logger.error(e.getMessage());
	        return false;
		}
    	//don't cleanupSession() here.  Do it at logout().
    	if(response==null){
    		logger.warn("loginTicket returned null.");
    		return false;
    	}
    	return response.get_return();
    }

    /**
     * Login using tool-based authentication.
     * @param shared_secret - see the "sharedSecret" parameter as defined in
     *                        conf.xml for the specific proxy tool.  
     * @param client_vendor_id - the "clientVendorId" as defined in conf.xml
     * @param proxy_tool_name  - the registered name of the proxy tool.  See 
     *                           <ClassConfig name=" ... "> in conf.xml
     * @param login_extra_info - not used at this time.
     * @param timeout_seconds  - The number of seconds to keep this session
     *                           object valid - see "sessionTimeOutSeconds" in
     *                           conf.xml to configure the default timeout 
     *                           value.  You can call extendSessionLife() to 
     *                           keep a session alive longer.
     *                           
     * @return If successful, establishes a tool-authenticated session valid for 
     *         use in other calls and returns true.  Otherwise, returns false.
     */
    protected boolean loginTool(String shared_secret, String client_vendor_id,
    							String tool_name, String login_extra_info, 
    							Long timeout_seconds) {
    	LoginTool loginArgs = new LoginTool();
        loginArgs.setPassword(shared_secret);
        loginArgs.setClientVendorId(client_vendor_id);
        loginArgs.setClientProgramId(tool_name);
        loginArgs.setLoginExtraInfo(login_extra_info);
        loginArgs.setExpectedLifeSeconds(timeout_seconds);
        LoginToolResponse response = null;
        setupSession();
        initializeSession();
        try {
            response = contextWS.loginTool(loginArgs);
        } catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Login Tool");
	        logger.error(e.getMessage());
	        return false;
		}
        //don't cleanupSession() here.  Do it at logout().
    	if(response==null){
    		logger.warn("loginTool returned null.");
    		return false;
    	}
    	return response.get_return();
    }
    
    /**
     * This method will destroy the current session rendering 
     * it invalid for use in subsequent operations.
     * @return
     */
    protected boolean logout() {
    	LogoutResponse response = null;
        try {
			response = contextWS.logout();
		} catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Logout");
	        logger.error(e.getMessage());
	        return false;
		}
        
        cleanupSession();
    	if(response==null){
    		logger.warn("logout returned null.");
    		return false;
    	}
    	return response.get_return();
    }
    
    /**
     * Register your client program with the Academic Suite server. The list of
     * required methods is a list of "webservicename:operation" for each 
     * operation you intend to call on each webservice. The requiredToolMethods
     * are those you call from a loginTool session while the methods in 
     * requiredTicketMethods are those you call from a loginTicket session. You
     * do not have to declare requiredUserMethods because you will get whatever
     * entitlements the user has if you provide their password as part of the 
     * login method. 
     * After calling this registration method from your client program 
     * installer, the Academic Suite administrator will have to login and make 
     * the tool available before you can login as a tool (via loginTool) and 
     * get the entitlements associated with the methods you requested.
     * 
     * NOTE that this call will only succeed BEFORE a password is assigned to 
     * your tool. Once a password is assigned you cannot re-register to change 
     * your desired entitlements (by declaring a different set of methods being
     * used). If you are testing your tool and wish to change entitlements you 
     * have to first clear the password in the UI, then call registerTool, then
     * set the password again. Be careful setting the initial password as there 
     * are several sets of rules around this: 
     * 1) If you register without a password and without placements (i.e no 
     *    tool-profile xml in the description) then your proxy tool is 
     *    'available' by default, but you can only use the login(user/pw) 
     *    method. 
     * 2) If you register with a password or with placements then your proxy 
     *    tool is either 'unavailable' (no placements) or 'inactive' (with 
     *    placements). You cannot login using any methods in this state. 
     * 3) Once your tool is registered, you cannot call this method as long as 
     *    there is a password set on your tool unless you are being asked to 
     *    via the reregister proxy-tool callback operation. 
     * 4) If you are calling this as part of the reregister operation, the 
     *    initialsharedsecret is ignored.
     *
     *@param client_vendor_id - Your vendor ID. You will use this same ID in 
     *                          any login calls.
     *@param client_program_id - Your program ID (should be unique per client 
     *                           program/Web Service agent you develop). You 
     *                           will use this ID in any login calls.
     *@param registration_password - If required by the AS administrator you 
     *                               may need a special password to register 
     *                               tools.
     *@param description - A longer description of your client program 
     *                     (displayed to the administrator on the manage agent 
     *                     page). When registering a Proxy Tool, the 
     *                     description field must be the Proxy Tool XML 
     *                     description for your tool. When registering a Proxy 
     *                     Tool with an XML descriptor, specify the operations 
     *                     in the registerTool method or in the XML or both. If
     *                     you specify in both places, the list of operations 
     *                     is merged together. Blackboard recommends specifying
     *                     them in the XML because that is the only place where
     *                     the list of 'webservices requested' is generated in 
     *                     the administrator UI.
     *@param initial_shared_secret - the initial shared secret to be used by 
     *                               this tool (note that the administrator has
     *                               to make the tool 'available' before you 
     *                               can use this password and they can also 
     *                               change it through the edit proxy tool UI). 
     *                               Also, if you are re-registering then this 
     *                               password is ignored - only valid for the 
     *                               Initial registration.
     *@param required_tool_methods - An array of webservice methods that you 
     *                               will use when logged in as a tool.
     *@param required_ticket_methods - An array of webservice methods that you 
     *                                 will use when logged in as a user via 
     *                                 the loginTicket method.
     *@return Returns a RegisterToolResultVO: 
     *        success=true  - successfully registered
     *        success=false - failed to register
     *        proxyToolGuid = the GUID for this proxy tool if the tool being 
     *                        registered is a proxy tool.  This guid is a 
     *                        unique identifier generated by Academic Suite 
     *                        associated with this tool registration. You must 
     *                        record this GUID and use it to correlate future 
     *                        requests from the server with your local 
     *                        configuration.
     */
    protected RegisterToolResultVO registerTool(String client_vendor_id, String client_program_id,
            									String registration_password, String description,
            									String initial_shared_secret, String[] required_tool_methods,
            									String[] required_ticket_methods){
    	RegisterTool register_tool = new RegisterTool();
    	register_tool.setClientProgramId(client_program_id);
    	register_tool.setClientVendorId(client_vendor_id);
    	register_tool.setDescription(description);
    	register_tool.setInitialSharedSecret(initial_shared_secret);
    	register_tool.setRegistrationPassword(registration_password);
    	register_tool.setRequiredToolMethods(required_tool_methods);
    	register_tool.setRequiredTicketMethods(required_ticket_methods);
        RegisterToolResponse response = null;
        setupSession();
        try {
            response = contextWS.registerTool(register_tool);
        } catch (RemoteException e) {
			logger.error("Encountered a remote exception while trying to Register Tool");
	        logger.error(e.getMessage());
	        return null;
		}
    	cleanupSession();
    	if(response==null){
    		logger.warn("registerTool returned null.");
    		return null;
    	}
    	return response.get_return();
    }
        
    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    private void setupSession() {
    	try {
    		ctx = ConfigurationContextFactory.createConfigurationContextFromFileSystem(modulePath);
        } catch (AxisFault af) {
        	logger.error("Failed to create an initial ConfigurationContext: " +	af.getMessage());
        }
        pwcb = new ContextProxyTool.PasswordCallbackClass();
        contextWS = (ContextWSStub) createClientWS(ContextWSStub.class, context_ws_url);
        contextWS = (ContextWSStub) configureClientOptions(contextWS);
    }
    	
    /**
     * Determines the version of the ContextWS service ad initializes the 
     * session accordingly.  You can set the service version of 
     * ContextProxyTool manually in the main configuration file using:
     * <param name="serverVersion" value="1"/> (value can be different)
     * Otherwise, the version number will be determined by calling the
     * _getServerVersion() method, resulting in another server hit.
     * 
     * Note: Since we're trying to initialize the session, the private methods
     * _getServerVersion(), _initialize() and _initializeVersion2() are used.
     * This prevents a session initialization loop & premature session cleanup. 
     */
    private void initializeSession(){
    	//First, check to see if we've already determined the version number
    	if(server_version!=null){
    		switch(server_version.intValue()){
    			case 2 : pwcb.setSessionId(_initializeVersion2());
    		         break;
    			default: pwcb.setSessionId(_initialize());
	                 break;      
    		}
    		return;
    	}
    	//If we made it here, we'll try to get the server version number from the config file.
    	String version = config.getConfigClass("ContextProxyTool", "serverVersion");
    	if(version!=null && version.equals("2")){
    		server_version = 2L;
    		pwcb.setSessionId(_initializeVersion2());
    		return;
    	}
    	if(version!=null && version.equals("1")){
    		server_version = 1L;
    		pwcb.setSessionId(_initialize());
    		return;
    	}
    	//If we made it here, we'll try getting the version number from the server.
    	server_version = _getServerVersion(null).getVersion();
    	if(server_version!=null && server_version==2L){
    		pwcb.setSessionId(_initializeVersion2());
    		return;
    	}
    	if(server_version!=null && server_version==1L){
    		pwcb.setSessionId(_initialize());
    		return;
    	}
    	//If we can't determine the version number, return an error
    	logger.error("Unable to determine server version number & generate a session ID");
    }
        
    /**
     * This method will instantiate a client Stub object from a Stub class
     * template and a web service URL using the existing ConfigurationContext.
     * 
     * @param client_stub - the class template used to determine the appropriate object
     *                      to instantiate.
     * @param service_url - the location of the server-side web service.
     * 
     * @return Returns a client Stub object based on the parameters provided.
     */
    protected Stub createClientWS(Class<?extends Stub> client_stub, String service_url) {
        Stub wsStub = null;
        //we're creating a generic class array and loading it with our 2 constructor object Classes
        Class<?>[] constructor_params = {ConfigurationContext.class, String.class};
        try {
            //we're creating a parameterized constructor for a class that extends Stub.
            Constructor<?extends Stub> constructor = client_stub.getConstructor(constructor_params);
            //now we're instantiating the the actual subclass using the constructor that we just created.
            wsStub = constructor.newInstance(ctx, service_url);
        } catch (NoSuchMethodException e) {
        	logger.error("Unable to create the client web service stub object: " + e.getMessage());
        	return null;
        } catch (SecurityException e) {
        	logger.error("Unable to create the client web service stub object: " + e.getMessage());
        	return null;
        } catch (InstantiationException e) {
        	logger.error("Unable to create the client web service stub object: " + e.getMessage());
        	return null;
        } catch (IllegalAccessException e) {
        	logger.error("Unable to create the client web service stub object: " + e.getMessage());
        	return null;
        } catch (IllegalArgumentException e) {
        	logger.error("Unable to create the client web service stub object: " + e.getMessage());
        	return null;
        } catch (InvocationTargetException e) {
        	logger.error("Unable to create the client web service stub class: " + e.getMessage());
        	return null;
        }
        return wsStub;
    }
    
    /**
     * After instantiating a Stub object, we need to configure how it will
     * communicate with the server-side web service.  As the name implies, 
     * this method configures various client options.  These options include
     * setting up Rampart-based security for user login, tool login and ticket
     * login.
     * 
     * @param wsStub - a newly created Stub object.  See createClientWS().
     * @return Returns a fully-configured client Stub that will be able to
     *         communicate with it's server-side counterpart.
     */
    protected Stub configureClientOptions(Stub wsStub) {
    	if(_httpClient==null) _httpClient = new HttpClient(new MultiThreadedHttpConnectionManager());
        ServiceClient client = wsStub._getServiceClient();
        Options options = client.getOptions();
        options.setProperty(HTTPConstants.AUTO_RELEASE_CONNECTION, Constants.VALUE_TRUE);
        //This is where we configure the password callback handler
        options.setProperty(WSHandlerConstants.PW_CALLBACK_REF, pwcb);
        /*
         * Setup ws-security settings.  Must use deprecated class of setting
         * up security because the SOAP response doesn't include a security
         * header. Using the deprecated OutflowConfiguration class we can
         * specify that the security header is only for the outgoing SOAP message.
         */
        OutflowConfiguration ofc = new OutflowConfiguration();
        ofc.setActionItems("UsernameToken Timestamp");
        ofc.setUser("session");
        ofc.setPasswordType("PasswordText");
        options.setProperty(WSSHandlerConstants.OUTFLOW_SECURITY, ofc.getProperty());
        //You might want to use the same HTTPClient instance for multiple 
        //invocations. This flag will notify the engine to use the same 
        //HTTPClient between invocations.
        options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Constants.VALUE_TRUE);

        //If user had requested to re-use an HTTPClient using the above 
        //property, this property can be used to set a custom HTTPClient to 
        //be re-used.
        options.setProperty(HTTPConstants.CACHED_HTTP_CLIENT, _httpClient);
        try {
            client.engageModule("rampart");
        } catch (AxisFault e) {
            logger.error("Unable to engage the rampart security module: " + e.getMessage());
            return null;
        }
        return wsStub;
    }
    
    /**
     * Whenever a session is successfully logged in, resources are allocated in
     * the Axis2 ConfigurationContext.  Unfortunately, these resources will
     * persist even after invoking the logout() method.  After invoking 
     * logout(), this method will cleanup the ConfigurationContext and release 
     * all of it's resources.
     * 
     * @return Returns true when the configuration Context has been cleaned up 
     *         successfully. Otherwise, returns false. 
     */
    private boolean cleanupSession() {
        try {
        	ctx.cleanupContexts(); //Called during shutdown to clean up all Contexts
        	ctx.flush();           //(part of superclass AbstractContext)
        	ctx.terminate();       //Invoked during shutdown to stop the ListenerManager and perform configuration cleanup
        } catch (RemoteException e) {
        	logger.error("Encountered an error while attempting to cleanup the configuration context: " + e.getMessage());
        	session_id = null;
        	ctx = null;
        	return false;
        }
        ctx = null;
        session_id = null;
        return true;
    }
    
    ///////////////////////////////////////////////////////////////////////////
    //  Inner Class - Password Callback Class  ////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Whenever the Rampart Engine needs a password to create a username token, 
     * whether it is to build a signature, validate username token or decrypt 
     * encrypted content, it will create a  WSPasswordCallback instance setting
     * the appropriate identifier which it extracts from the <ramp:user> 
     * parameter of the Rampart configuration (see the OutflowConfiguration 
     * setUser() method in configureClientOptions()) and pass it to the password 
     * callback class via the handle method. Then password callback fills the 
     * password relevant to the given identifier so that the Rampart Engine can
     * use it for further processing.
     */
    public static class PasswordCallbackClass implements CallbackHandler {
        public void setSessionId(String _session_id) {
            session_id = _session_id;
        }
        public void handle(Callback[] callbacks)
            throws IOException, UnsupportedCallbackException {
            for (int i = 0; i < callbacks.length; i++) {
                WSPasswordCallback pwcb = (WSPasswordCallback) callbacks[i];
                String pw = "nosession";
                if (session_id != null) {
                    pw = session_id;
                }
                pwcb.setPassword(pw);
            }
        }
    }
}
