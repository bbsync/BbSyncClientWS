package org.bbsync.webservice.client.abstracts;

import org.bbsync.webservice.client.generated.CourseMembershipWSStub.CourseMembershipVO;
import org.bbsync.webservice.client.generated.CourseMembershipWSStub.VersionVO;
import org.bbsync.webservice.client.proxytool.CourseMembershipProxyTool;

public abstract class AbstractCourseMembership extends CourseMembershipProxyTool {
	private static final long serialVersionUID = 5555000000001111L;
	protected CourseMembershipVO _cmvo = null;
	
	protected static final int FILTER_TYPE_BY_ID                 = 1;
	protected static final int FILTER_TYPE_BY_CRS_ID             = 2;
	protected static final int FILTER_TYPE_BY_USER_ID            = 5;
	protected static final int FILTER_TYPE_BY_CRS_ID_AND_USER_ID = 6;
	protected static final int FILTER_TYPE_BY_CRS_ID_AND_ROLE_ID = 7;
	
	///////////////////////////////////////////////////////////////////////////
	//  Implemented ProxyTool Methods  ////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////  
    /**
     * @return Returns the current version (Long) of this web service on
     *         the server.
     */
	public Long getServerVersion(){
    	VersionVO version = null;
    	version = super.getServerVersion(version);
    	return version.getVersion();
    }
    
	/**
	 * Sets the client version to version 1 and returns an appropriate session.
	 * 
	 * @return Returns true to indicate that the session has been initialized 
     *         for the CourseMembership web service.
	 */
	public boolean	initializeCourseMembershipWS(){
		return super.initializeCourseMembershipWS(true);
	}
	///////////////////////////////////////////////////////////////////////////
	//  Local Methods  ////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	/**
	 * @return Returns the availability flag for this CourseMembership.
	 */
	public boolean getAvailable(){
		return _cmvo.getAvailable();
	}
	
	/**
	 * @return
	 */
	protected String getCourseId(){
		return _cmvo.getCourseId();
	}
	
	/**
	 * @return Returns the data source Id of this CourseMembership.
	 */
	public String getDataSourceId(){
		return _cmvo.getDataSourceId();
	}
	
	/**
	 * @return Returns the date on which this CourseMembership (enrollment record) 
	 *         object was created.
	 */
	public long getEnrollmentDate(){
		return super.bbLong2javaLong(_cmvo.getEnrollmentDate());
	}
	
	/**
	 * @return
	 */
	public String[]	getExpansionData() {
		return _cmvo.getExpansionData();
	}
	
	/**
	 * @return Returns the has cartridge access flag for this CourseMembership.
	 */
	public boolean getHasCartridgeAccess(){
		return _cmvo.getHasCartridgeAccess();
	}
	
	/**
	 * @return Returns the database Id of the CourseMembership object.
	 */
	public String getId(){
		return _cmvo.getId();
	}
	
	/**
	 * @return Returns the image of this CourseMembership object.
	 */
	public String getImageFile(){
		return _cmvo.getImageFile();
	}
	
	/**
	 * @return Returns the role Id for this CourseMembership.
	 */
	public String getRoleId(){
		return _cmvo.getRoleId();
	}
	
	/**
	 * @return Returns the user Id associated with this CourseMembership.
	 */
	public String getUserId(){
		return _cmvo.getUserId();
	}
	
	/**
	 * @param available - Sets the availability flag for this CourseMembership.
	 */
	public void	setAvailable(boolean available){
		_cmvo.setAvailable(available);
	}
	
	/**
	 * @param courseId
	 */
	protected void	setCourseId(String courseId){
		_cmvo.setCourseId(courseId);
	}
	
	/**
	 * @param dataSourceId - Sets the data source Id value for this CourseMembership.
	 */
	public void	setDataSourceId(String dataSourceId){
		_cmvo.setDataSourceId(dataSourceId);
	}
	
	/**
	 * @param enrollmentDate - Sets the enrollment date for this CourseMembership.
	 */
	public void	setEnrollmentDate(long enrollmentDate){
		if (String.valueOf(enrollmentDate).length() > 10) {
			_cmvo.setEnrollmentDate(super.javaLong2bbLong(enrollmentDate));
        } else {
        	_cmvo.setEnrollmentDate(enrollmentDate);
        }
	}
	
	/**
	 * @param expansionData - Expansion data is currently ignored.
	 */
	public void	setExpansionData(String[] expansionData){
		_cmvo.setExpansionData(expansionData);
	}
	
	/**
	 * @param hasCartridgeAccess - Sets the has cartridge flag for this 
	 *                             CourseMembership.
	 */
	public void	setHasCartridgeAccess(boolean hasCartridgeAccess){
		_cmvo.setHasCartridgeAccess(hasCartridgeAccess);
	}
	
	/**
	 * @param id - You should not try to 'make up' an id here - only use the ids 
	 *             that you are given from another api call.
	 */
	public void	setId(String id){
		_cmvo.setId(id);
	}
	
	/**
	 * @param imageFile - Sets the image value for this CourseMembership object.
	 */
	public void	setImageFile(String imageFile){
		_cmvo.setImageFile(imageFile);
	}
	
	/**
	 * @param roleId - Sets the role for this CourseMembership.
	 */
	public void	setRoleId(String roleId){
		_cmvo.setRoleId(roleId);
	}
	
	/**
	 * @param userId - Sets the user Id value for this CourseMembership.
	 */
	public void	setUserId(String userId){
		_cmvo.setUserId(userId);
	}
}
