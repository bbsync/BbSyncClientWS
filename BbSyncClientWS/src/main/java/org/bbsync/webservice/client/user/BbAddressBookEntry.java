package org.bbsync.webservice.client.user;

import java.io.Serializable;
import java.util.ArrayList;

import org.bbsync.webservice.client.generated.UserWSStub.AddressBookEntryVO;
import org.bbsync.webservice.client.generated.UserWSStub.UserExtendedInfoVO;
import org.bbsync.webservice.client.generated.UserWSStub.UserFilter;
import org.bbsync.webservice.client.proxytool.UserProxyTool;

/**
 * BbAddressBookEntry represents the address book entry (contacts) of a user in
 * the Academic Suite. 
 * 
 * It is important to understand that underlying web service methods used by 
 * this class to access and manipulate address book data require login as 
 * 'user'.  For this reason, all of these methods require the login information
 * of the user who owns the address book.
 */
public class BbAddressBookEntry extends UserProxyTool {
	private static final long serialVersionUID = 4444000000006666L;
	private AddressBookEntryVO _abe_vo = null;
	private String _password = null;
	private String _username = null;
    private static final int GET_ADDRESS_BOOK_ENTRY_BY_ID 				= 8;
    private static final int GET_ADDRESS_BOOK_ENTRY_BY_CURRENT_USERID	= 9;

	
	///////////////////////////////////////////////////////////////////////////
	//  Constructors  /////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	public BbAddressBookEntry() {
		_abe_vo = new AddressBookEntryVO();
	}

	public BbAddressBookEntry(AddressBookEntryVO address_book_entry_vo) {
		_abe_vo = address_book_entry_vo;
	}
	
    ///////////////////////////////////////////////////////////////////////////
    //  Required ClientWebService Methods  ////////////////////////////////////	
	///////////////////////////////////////////////////////////////////////////	
	/**
	 * This method allows you to persist the current BbAddressBookEntry.  In 
	 * addition to address book entry data, you must set the username and
	 * password for the user who owns the address book:
	 * setUserName() - username/login ID
	 * setPassword() - login password 
	 */
	public Serializable persist() {
		AddressBookEntryVO[] entries = new AddressBookEntryVO[]{_abe_vo};
		String[] result = super.saveAddressBookEntry(entries, _username, _password);
		if(result==null || result.length==0) return null;
		return result[0];
	}
	
	/**
	 * This method allows you to retrieve a single BbAddressBookEntry that
	 * matches the set address book entry ID.  In addition the address book 
	 * entry ID, you must set the username and password  for the user who owns
	 * the address book:
	 * setUserName() - username/login ID
	 * setPassword() - login password 
	 */
	public Object retrieve() {
		UserFilter filter = new UserFilter();
		filter.setFilterType(GET_ADDRESS_BOOK_ENTRY_BY_ID);
		filter.setId(new String[]{getId()});
		AddressBookEntryVO[] result = super.getAddressBookEntry(filter, _username, _password);
		if(result==null || result.length==0) return null;
		return new BbAddressBookEntry(result[0]);
	}

	/**
	 * This method allows you to delete a single BbAddressBookEntry that
	 * matches the set address book entry ID.  In addition to the address book 
	 * entry ID, you must set the username and password  for the user who owns 
	 * the address book:
	 * setUserName() - username/login ID
	 * setPassword() - login password 
	 */
	public boolean delete() {
		String[] result = super.deleteAddressBookEntry(new String[]{getId()}, _username, _password);
		if(result==null || result.length==0) return false;
		if(result[0]!=null && !result[0].isEmpty() ) return true;
		return false; //if we get here, something's not right!
	}
	
	///////////////////////////////////////////////////////////////////////////
    //  Implemented ProxyTool Methods  ////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
	public String[] saveAddressBookEntrys(BbAddressBookEntry[] entrys, String user_name, String user_password){
		return super.saveAddressBookEntry(convert_bb_abe_array_to_abe_vo_array(entrys), user_name, user_password);
	}
	
	public String[] deleteAddressBookEntrys(String[] entry_ids, String user_name, String password){
		return super.deleteAddressBookEntry(entry_ids, user_name, password);
	}
	
	public BbAddressBookEntry[] getAddressBookEntrysByUserName(String user_name, String user_password){
		UserFilter filter = new UserFilter();
		filter.setFilterType(GET_ADDRESS_BOOK_ENTRY_BY_CURRENT_USERID);
		filter.setId(new String[]{user_name});
		AddressBookEntryVO[] entries = super.getAddressBookEntry(filter, user_name, user_password);
		return convert_abe_vo_array_to_bb_abe_array(entries);
	}
	
	public BbAddressBookEntry[] getAddressBookEntrysById(String[] entry_ids, String user_name, String user_password){
		UserFilter filter = new UserFilter();
		filter.setFilterType(GET_ADDRESS_BOOK_ENTRY_BY_ID);
		filter.setId(entry_ids);
		AddressBookEntryVO[] abe_vos = super.getAddressBookEntry(filter, user_name, user_password);
		BbAddressBookEntry[] bb_abes = convert_abe_vo_array_to_bb_abe_array(abe_vos);
		if(bb_abes!=null && bb_abes.length==0) return null;
		return bb_abes;
	}

    ///////////////////////////////////////////////////////////////////////////
    //  Local Methods  ////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
	public BbUserExtendedInfo getAddressBookEntry(){
		UserExtendedInfoVO user_xinfo_vo = _abe_vo.getAddressBookEntry(); 
		if(user_xinfo_vo==null) return null;
		return new BbUserExtendedInfo(user_xinfo_vo);
	}
	
	public String[]	getExpansionData(){
		return _abe_vo.getExpansionData();
	}
	
	/**
	 * @return Returns the database Id of the AddressBookEntryVO object.
	 */
	public String getId(){
		return _abe_vo.getId();
	}
	
	public String getTitle(){
		return _abe_vo.getTitle();
	}
	
	public String getUserId(){
		return _abe_vo.getUserId();
	}

	/**
	 * The address book entry details of the user
	 * 
	 * @param addressBookEntry
	 */
	public void	setAddressBookEntry(BbUserExtendedInfo address_book_entry){
		if(address_book_entry!=null) _abe_vo.setAddressBookEntry(address_book_entry.getVO());
	}
	
	/**
	 * Expansion data is currently ignored.
	 * 
	 * @param expansionData
	 */
	public void	setExpansionData(String[] expansion_data){
		_abe_vo.setExpansionData(expansion_data);
	}
	
	/**
	 * You should not try to 'make up' an id here - only use the ids that you
	 * are given from another api call.
	 * 
	 * @param id
	 */
	public void	setId(String id){
		_abe_vo.setId(id);
	}
	
	/**
	 * Set the title of the AddressBookEntryVO object
	 * 
	 * @param title
	 */
	public void	setTitle(String title){
		_abe_vo.setTitle(title);
	}
	
	/**
	 * This is a read-only field. UserId in session will override the value set
	 * by this method and be used as the userId to which this 
	 * AddressBookEntryVO object belongs to of the, in case of login via user 
	 * Authentication For tool based login, the specified value is used, but 
	 * ONLY if the tool has also registered that it will be calling the 
	 * ContextWS.emulateUser method.
	 * 
	 * @param user_id - the internal Blackboard ID of the user.
	 */
	public void	setUserId(String user_id){
		_abe_vo.setUserId(user_id);
	}
	
	/**
	 * When logging in as a user and using the persist(), retrieve(), or 
	 * delete() methods, you must provide the user's login password.
	 *  
	 * @param user_password - the login password for the user authorized to
	 *                        persist(), retrieve(), or delete() the current
	 *                        BbAddressBookEntry.
	 */
	public void	setUserPassword(String user_password){
		_password = user_password;
	}

	/**
	 * When logging in as a user and using the persist(), retrieve(), or 
	 * delete() methods, you must provide the user's user name.
	 *  
	 * @param user_name - the username/login id for the user authorized to
	 *                    persist(), retrieve(), or delete() the current
	 *                    BbAddressBookEntry.
	 */
	public void	setUserName(String user_name){
		_username = user_name;
	}
	
	private AddressBookEntryVO getVO(){
		return _abe_vo;
	}
	
	private AddressBookEntryVO[] convert_bb_abe_array_to_abe_vo_array(BbAddressBookEntry[] bb_abes){
		if(bb_abes==null || bb_abes.length==0)return null;
		ArrayList<AddressBookEntryVO> abe_vos = new ArrayList<AddressBookEntryVO>();
		for(BbAddressBookEntry bb_abe:bb_abes){
			abe_vos.add(bb_abe.getVO());
		}
		return abe_vos.toArray(new AddressBookEntryVO[]{});
	}
	
	private BbAddressBookEntry[] convert_abe_vo_array_to_bb_abe_array(AddressBookEntryVO[] abe_vos){
		if(abe_vos==null)return null;
		ArrayList<BbAddressBookEntry> bb_abes = new ArrayList<BbAddressBookEntry>();
		for(AddressBookEntryVO abe_vo:abe_vos){
			BbAddressBookEntry bb_abe = new BbAddressBookEntry(abe_vo);
			bb_abes.add(bb_abe);
		}
		return bb_abes.toArray(new BbAddressBookEntry[]{});
	}	
}
