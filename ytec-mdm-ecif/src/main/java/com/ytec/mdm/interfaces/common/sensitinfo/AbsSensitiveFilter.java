/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.sensitinfo
 * @�ļ�����AbsSensitiveFilter.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-6-26-����2:17:32
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.common.sensitinfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.NameUtil;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�AbsSensitiveFilter
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-6-26 ����2:17:32   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-6-26 ����2:17:32
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public abstract class AbsSensitiveFilter{
	/**
	 * The sensit infor map.
	 * 
	 * @��������:����������Ϣ
	 */
	protected Set sensitInforMap=new HashSet();
	
	/**
	 * @��������:init
	 * @��������:��ʼ������������Ϣ
	 * @�����뷵��˵��:
	 * 		@param arg
	 * @�㷨����:
	 */
	public void initXmlSensitive(Map arg)  throws Exception{
		sensitInforMap.clear();
		String sensitiveInfors = (String) arg.get("sensitiveXmlInfor");
		if (sensitiveInfors != null) {
			String[] sensitiveInfor = sensitiveInfors.split("\\,");
			JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils
					.getBean("baseDAO");
			int length = sensitiveInfor.length;
			String sensit = null;
			int p = 0;
			String sql = "select distinct attr_code from tx_msg_node_attr t where";
			StringBuffer buf = null;
			List<String> result=null;
			Map<String,String> values=new HashMap<String,String>();
			for (int index = 0; index < length; index++) {
				sensit = sensitiveInfor[index];
				if (!StringUtil.isEmpty(sensit)) {
					if (StringUtil.isChinese(sensit)) {
						if (p == 0) {
							buf = new StringBuffer(sql);
							values.clear();
							buf.append(" attr_name like :P_").append(p);
						} else {
							buf.append(" or attr_name like :P_").append(p);
						}
						values.put("P_"+p,"%"+sensit+"%");
						p++;
					}else{
						if(!sensitInforMap.contains(sensit)){
							sensitInforMap.add(sensit);
						}
					}
				}
				
				if(p!=0&&(p==5 || index==length-1)){
					result = baseDAO.findByNativeSQLWithNameParam(buf.toString(),values);
					if(result!=null && result.size()>0){
						for(String sensitCode:result){
							if(!sensitInforMap.contains(sensitCode)){
								sensitInforMap.add(sensitCode);
							}
						}
					}
					p=0;
				}
			}
		}
	}
	
	/**
	 * @��������:initDbSensitive
	 * @��������:��ʼ�����ݿ��Ӧ��������Ϣ�ֶ�
	 * @�����뷵��˵��:
	 * 		@param arg
	 * 		@throws Exception
	 * @�㷨����:
	 */
	public void initDbSensitive(Map arg)  throws Exception{
		sensitInforMap.clear();
		String sensitiveInfors = (String) arg.get("sensitiveDbInfor");
		if (sensitiveInfors != null) {
			String[] sensitiveInfor = sensitiveInfors.split("\\,");
			JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils
					.getBean("baseDAO");
			int length = sensitiveInfor.length;
			String sensit = null;
			int p = 0;
			String sql = "select distinct COL_NAME from tx_col_def t where";
			StringBuffer buf = null;
			List<String> result=null;
			Map<String,String> values=new HashMap<String,String>();
			for (int index = 0; index < length; index++) {
				sensit = sensitiveInfor[index];
				if (!StringUtil.isEmpty(sensit)) {
					if (StringUtil.isChinese(sensit)) {
						if (p == 0) {
							buf = new StringBuffer(sql);
							values.clear();
							buf.append(" t.col_ch_name like :P_").append(p);
						} else {
							buf.append(" or t.col_ch_name like :P_").append(p);
						}
						values.put("P_"+p,"%"+sensit+"%");
						p++;
					}else{
						if(!sensitInforMap.contains(sensit)){
							sensitInforMap.add(sensit);
						}
					}
				}
				
				if(p!=0&&(p==5 || index==length-1)){
					result = baseDAO.findByNativeSQLWithNameParam(buf.toString(),values);
					if(result!=null && result.size()>0){
						for(String sensitCode:result){
							sensitCode=NameUtil.getColumToJava(sensitCode);
							if(!sensitInforMap.contains(sensitCode)){
								sensitInforMap.add(sensitCode);
							}
						}
					}
					p=0;
				}
			}
		}
	}

}
