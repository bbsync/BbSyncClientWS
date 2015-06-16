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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.bbsync.utility.file.Configuration;

public abstract class AbstractProxyTool {
    private static final Logger logger = Logger.getLogger(AbstractProxyTool.class.getName());
    protected static Configuration config = Configuration.getInstance();
    
    protected String getExpansionData(String[] expansion_data, String key){
		String value = null;
		if(expansion_data==null || expansion_data.length==0) return null;
		for(int i=0; i<expansion_data.length; i++){
			if(expansion_data[i]!=null && expansion_data[i].length()>0){
				value = extractExpansionDataValue(expansion_data[i], key);
			}
			if(value != null) return value;
		}
		return null;
	}
	
	private String extractExpansionDataValue(String expansion_data, String key){
		String[] kv = expansion_data.split("=");
		//if the key doesn't exist, return null
		if(kv[0]==null || kv[0].length()==0) return null;
		//if this isn't the key we're looking for, return null
		if(!kv[0].equals(key)) return null;
		//if this is the key we're looking for, return the value
		return kv[1];
	}
	
	private void deleteExpansionData(String[] expansion_data, String key){
		String value = null;
		if(expansion_data==null || expansion_data.length==0) return;
		for(int i=0; i<expansion_data.length; i++){
			if(expansion_data[i]!=null && expansion_data[i].length()>0){
				value = extractExpansionDataValue(expansion_data[i], key);
			}
			if(value != null) expansion_data[i]=null;
		}
		return;
	}
	
	protected String[] setExpansionData(String[] expansion_data, String key, String value){
		//retrieve expansion data using the key provided
		String ex_string = getExpansionData(expansion_data, key);
		//if the expansion data already exists, check to see if it should be updated
		if(ex_string!=null){
			String ex_value = extractExpansionDataValue(ex_string, key);
			//if the expansion data hasn't changed, we're done.
			if(value.equals(ex_value)) return expansion_data;
			//if the value has changed, delete the original expansion data string
			else deleteExpansionData(expansion_data, key);
		}
		//add the expansion data
		List<String> ex_list = arrayToMutableList(expansion_data);
		ex_list.add(key + "=" + value);
		//remove any duplicate values
		expansion_data = removeDuplicates(ex_list.toArray(new String[]{}));
		return expansion_data;
	}
	
	/**
	 * The List interface defines many of it's methods as "optional operations".
	 * add() & remove() are optional & often not implemented for Lists that are created 
	 * from arrays.  This leaves you with an immutable list - which isn't very helpful.
	 * This method converts String arrays into String ArrayLists - which implement add()
	 * and remove(). 
	 * 
	 * @param array - The String array to convert.
	 * @return Returns a mutable ArrayList based on the array paramenter.
	 */
	protected List<String> arrayToMutableList(String[] array){
		return new ArrayList<String>((List<String>)Arrays.asList(array));
	}
	
	/**
	 * Takes a String array and removes any duplicate strings in the array.
	 * The underlying implementation of this method uses a Hashset, so
	 * expect the resulting array to be ordered differently than the
	 * original array.
	 * 
	 * @param array - an array of String items.  Duplicates will be removed.
	 * @return Returns an array of unique String items.
	 */
	protected String[] removeDuplicates(String[] array){
		HashSet<String> roles = new HashSet<String>();
		Iterator<String> i_roles = Arrays.asList(array).iterator();
		while(i_roles.hasNext()){
			roles.add(i_roles.next());
		}
		return roles.toArray(new String[]{});
	}
	
	/**
	 * Blackboard uses a 10 digit date (no milliseconds). This method adds 
	 * milliseconds (three digits) to the end of the long Date to create a 13 
	 * digit Java long primitive value.
	 * 
	 * @param bb_long_date - A 10 digit date without milliseconds.
	 * @return Returns a date with milliseconds appended to the end.
	 */
	protected long bbLong2javaLong(long bb_long_date) {
		return Long.parseLong(new String(bb_long_date + "000"));
	}
	
	/**
	 * Blackboard uses a 10 digit date (no milliseconds).  This method strips 
	 * off the milliseconds (last three digits), creating a 10 digit Date from 
	 * a Java long primitive type.
	 * @param java_long_date - a long primitive type.
	 * @return Returns a 10 digit long primitive type with the milliseconds 
	 *         removed.
	 */
	protected long javaLong2bbLong(long java_long_date) {
		String date_string = String.valueOf(java_long_date);
		if(date_string.length()>10){
			//dates prior to the epoch are negative (begin with "-").  For these,
			//we need to convert using 11 characters.
			if(date_string.startsWith("-")){
				return Long.parseLong(String.valueOf(java_long_date).substring(0, 11));
			} 
			//if the date is after the epoch, we need to convert using 10 digits 
			else if(Character.isDigit(date_string.charAt(0))){
				return Long.parseLong(String.valueOf(java_long_date).substring(0, 10));
			} 
		}
		logger.warn("The Java long <<"+ java_long_date +">> was not converted to a Blackboard compatable long (no millis).");
		return java_long_date;
	}
	
	protected String[] getSecondaryRoles(String[] roles){
		if(roles!=null && roles.length>1){
			List<String> s_roles = arrayToMutableList(roles);
			s_roles.remove(0);
			return (s_roles.toArray(new String[]{}));
		}
		return null;
	}
	
	protected String getPrimaryRole(String[] roles){
		if(roles!=null && roles.length!=0) return roles[0];
		return null;
	}
}
