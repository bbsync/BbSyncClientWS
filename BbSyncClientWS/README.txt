How to work with this project:

####################################
#1 Setup a working Blackboard Server
####################################

By default, this project is configured to work with the Blackboard-licensed
developer virtual machine image.  See the following link for configuration
details:

https://help.blackboard.com/en-us/Learn/9.1_SP_14/Administrator/230_Developer_Resources/Developer_Virtual_Machine

########################################################
#2  Configure this project's two (2) configuration files
########################################################

 - src/main/resources/conf.xml  This is the main configuration file.  Be sure 
                                to set the path to Apache Rampart:
                                <param name="modulePath" value="/path/to/rampart/rampart-1.5.2"/>
                                Other variables in this file may be configured
                                to work with any Blackboard Learn server.
                                
 - src/main/log4j.properties    Configure this file as needed for logging options.

################################################################
#3  Generate required code using WSDL from the Blackboard Server
################################################################

 - Make sure that the Web Service on your server are accessible.  In the
   Administrator Panel, select the Building Blocks > Web Services link.
   Set all Web Services to "Available" & "Discoverable".  Only set 
   "SSL Required" if you have installed a valid security certificate on the 
   server.
   
 - Run the application org.bbsync.webservice.client.WsdlCodeGenerator. When the
   WsdlCodeGenerator completes, refresh the project.  This will cause the
   project to build and all build path errors should be resolved.

################################################################
#4  Register all Proxy Tools
################################################################

 - Ensure that you configured the Rampart "modulePath" parameter in the 
   conf.xml file (Step #2).
   
 - In the Administrator Panel, select the Building Blocks > Building Blocks
   link.  Then select the Proxy Tools link.  When the "Proxy Tools" page
   loads, select the "Manage Global Properties" button.  Ensure that the
   Proxy Tool Registration password matches the "toolRegistrationPassword"
   parameter in conf.xml.  
   
 - Run the application org.bbsync.webservice.client.RegisterAllProxyTools.
   This will register the following Proxy Tools with you Blackboard Learn
   Server:
     		- ContextProxyTool
    		- CourseMembershipProxyTool
    		- CourseProxyTool
    		- UserProxyTool
    		- UtilProxyTool

 - In the Administrator Panel, select the Building Blocks > Building Blocks
   link.  Then select the Proxy Tools link.  When the "Proxy Tools" page
   loads, you should see the Proxy Tools that were just registered.
   
 - Edit each Proxy Tool.  Set the Availability to "Yes (Permit use of this 
   Proxy Tool)".

###################################################
#5  Run the Unit & Integration Tests - and code on!
################################################### 
 
BbSync.org Eclipse project created using:
 - Eclipse Java EE IDE for Web Developers, Version: Luna Release (4.4.0)
 - Oracle Java SE 7 [1.7.0_55]
 - Tested with Blackboard Learn SP13 & SP14
 - July 2014