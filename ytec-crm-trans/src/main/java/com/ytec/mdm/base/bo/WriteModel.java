package com.ytec.mdm.base.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�WriteModel
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-16 ����8:09:41   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-16 ����8:09:41
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */

public class WriteModel {
	
	
	/**
	 * @��������:opModelList
	 * @��������:����ģ�����б�
	 * @since 1.0.0
	 */
	private List opModelList=new ArrayList();
	/**
	 * @��������:resultMap
	 * @��������:���ؽ��
	 * @since 1.0.0
	 */
	private Map<String, Object> resultMap=new HashMap<String, Object>();
	/**
	 * @��������:operMap
	 * @��������:TODO
	 * @since 1.0.0
	 */
	private Map operMap=new HashMap();    
	/**
	 * @��������:divInsUpd
	 * @��������:TODO
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
	 * @��������:setResult
	 * @��������:
	 * @�����뷵��˵��:
	 * 		@param key
	 * 		@param value
	 * @�㷨����:
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
	 * @��������:containsOpModel
	 * @��������:���������б����Ƿ����key������
	 * @�����뷵��˵��:
	 * @�㷨����:
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
	 * @��������:getOpModelByName
	 * @��������:��ȡ���������б���key������
	 * @�����뷵��˵��:
	 * @�㷨����:
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
	 * @��������:getOpModelByName
	 * @��������:��ȡ���������б���key�ĵ�һ������
	 * @�����뷵��˵��:
	 * 		@param key
	 * 		@return
	 * @�㷨����:
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
