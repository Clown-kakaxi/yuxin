package com.ytec.mdm.base.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：WriteModel
 * @类描述：
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-16 下午8:09:41   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-16 下午8:09:41
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */

public class WriteModel {
	
	
	/**
	 * @属性名称:opModelList
	 * @属性描述:操作模型类列表
	 * @since 1.0.0
	 */
	private List opModelList=new ArrayList();
	/**
	 * @属性名称:resultMap
	 * @属性描述:返回结果
	 * @since 1.0.0
	 */
	private Map<String, Object> resultMap=new HashMap<String, Object>();
	/**
	 * @属性名称:operMap
	 * @属性描述:TODO
	 * @since 1.0.0
	 */
	private Map operMap=new HashMap();    
	/**
	 * @属性名称:divInsUpd
	 * @属性描述:TODO
	 * @since 1.0.0
	 */
	private boolean divInsUpd=false;
	
	
	public List getOpModelList() {
		return opModelList;
	}
	public void setOpModelList(Object opModel) {
		this.opModelList.add(opModel);
	}
	public Map getResultMap() {
		return resultMap;
	}
	
	public void clearOpModelList(){
		this.opModelList.clear();
	}
	
	public void clearResultMap(){
		this.resultMap.clear();
	}
	public Map getOperMap() {
		return operMap;
	}
	public void setOperMap(Map operMap) {
		this.operMap = operMap;
	}
	
	public boolean isDivInsUpd() {
		return divInsUpd;
	}
	public void setDivInsUpd(boolean divInsUpd) {
		this.divInsUpd = divInsUpd;
	}
	
	
	/**
	 * @函数名称:setResult
	 * @函数描述:
	 * @参数与返回说明:
	 * 		@param key
	 * 		@param value
	 * @算法描述:
	 */
	public void setResult(String key, Object value) {

		this.resultMap.put(key, value);
	}
	
	public String getOperMapValue(String key){
		return (String)operMap.get(key);
	}
	
	public void putOperMapValue(String key, String value){
		operMap.put(key, value);
	}
	
	/**
	 * Contains op model.
	 * 
	 * @param key
	 *            the key
	 * @return true, if contains op model
	 * @函数名称:containsOpModel
	 * @函数描述:操作对象列表中是否存在key的数据
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public boolean containsOpModel(String key){
		for(Object o:opModelList){
			if(o.getClass().getSimpleName().equals(key)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the op model by name.
	 * 
	 * @param key
	 *            the key
	 * @return the op model by name
	 * @函数名称:getOpModelByName
	 * @函数描述:获取操作对象列表中key的数据
	 * @参数与返回说明:
	 * @算法描述:
	 */
	public List getOpModelListByName(String key){
		List p=new ArrayList();
		for(Object o:opModelList){
			if(o.getClass().getSimpleName().equals(key)){
				p.add(o);
			}
		}
		return p;
	}
	
	/**
	 * @函数名称:getOpModelByName
	 * @函数描述:获取操作对象列表中key的第一个数据
	 * @参数与返回说明:
	 * 		@param key
	 * 		@return
	 * @算法描述:
	 */
	public Object getOpModelByName(String key){
		for(Object o:opModelList){
			if(o.getClass().getSimpleName().equals(key)){
				return o;
			}
		}
		return null;
	}

}
