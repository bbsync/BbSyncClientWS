package org.bbsync.webservice.client.util;

import java.io.Serializable;
import java.util.ArrayList;

import org.bbsync.webservice.client.generated.UtilWSStub.DataSourceVO;
import org.bbsync.webservice.client.proxytool.UtilProxyTool;

public class BbDataSource extends UtilProxyTool {
	private static final long serialVersionUID = 2222000000002222L;
	private DataSourceVO data_source = null;
	
	public BbDataSource() {
		data_source = new DataSourceVO();
    }
	
	public BbDataSource(DataSourceVO data_source_vo) {
		data_source = data_source_vo;
    }
	
	/**
	 * @return
	 */
	public String getBatchUid(){
		return data_source.getBatchUid();
	}
	
	/**
	 * @return
	 */
	public String getDescription(){
		return data_source.getDescription();
	}
	
	/**
	 * Returns the database Id of the DataSource object.
	 * @return id
	 */
	public String getId(){
		return data_source.getId();
	}
	
	/**
	 * @param batchUid
	 */
	public void	setBatchUid(String batchUid){
		data_source.setBatchUid(batchUid);
	}
	
	/**
	 * @param description
	 */
	public void	setDescription(String description){
		data_source.setDescription(description);
	}
	
	/**
	 * You should not try to 'make up' an id here - only use the ids that you are given from another api call. 
	 * The actual format of the id is Internal to AS: Treat an id as a series of characters.
	 * 
	 * @param id - the id to set - only required if you want to update an existing data source item
	 */
	public void	setId(String id){
		data_source.setId(id);
	}
	
    public BbDataSource[] getAllDataSources() {
        ArrayList<BbDataSource> data_sources = null;
    	DataSourceVO[] dsvo_array = super.getDataSources(null);
        if (dsvo_array==null) return null;
        data_sources = new ArrayList<BbDataSource>();
        for (DataSourceVO dsvo : dsvo_array) {
        	data_sources.add(new BbDataSource(dsvo));
        }
        return data_sources.toArray(new BbDataSource[]{});
    }
	
	public Serializable persist() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object retrieve() {
		DataSourceVO[] dsvo_array = super.getDataSources(null);
		if(dsvo_array==null) return null;
		BbDataSource result = null;
		if(data_source.getBatchUid()!=null){
			result = findDataSourceByBatchUid(dsvo_array, data_source.getBatchUid());
		}
		if(result!=null) return result;
		if(data_source.getId()!=null){
			return findDataSourceById(dsvo_array, data_source.getId());
		}
		return null;
	}

	public boolean delete() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private BbDataSource findDataSourceById(DataSourceVO[] dsvo_array, String id){
		for (DataSourceVO dsvo : dsvo_array) {
    		if(dsvo.getId().equals(id)) return new BbDataSource(dsvo);
    	}
		return null;
	}
	
	private BbDataSource findDataSourceByBatchUid(DataSourceVO[] dsvo_array, String batch_uid){
		for (DataSourceVO dsvo : dsvo_array) {
    		if(dsvo.getBatchUid().equals(batch_uid)) return new BbDataSource(dsvo);
    	}
		return null;
	}
}
