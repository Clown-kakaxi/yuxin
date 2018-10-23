/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.service.svc.atomic
 * @�ļ�����SuspectCustDao.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-8-����4:27:19
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.atomic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.stereotype.Service;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�SuspectCustDao
 * @��������
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-8 ����4:27:19   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-8 ����4:27:19
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
public class SuspectCustDao {
	private static List<SuspectCustRule> rules_p=new ArrayList<SuspectCustRule>();
	private static List<SuspectCustRule> rules_o=new ArrayList<SuspectCustRule>();
	public void init(Map<String,String> arg) throws Exception{
		rules_o.clear();
		rules_o.clear();
		if(!arg.isEmpty()){
			SuspectCustRule rule=null;
			for(Entry<String,String> entry:arg.entrySet()){
				rule=new SuspectCustRule();
				rule.setRuleCode(entry.getKey());
				rule.setRuleSql(entry.getValue());
				if(rule.getRuleCode().startsWith("P_")){
					rules_p.add(rule);
				}else if(rule.getRuleCode().startsWith("O_")){
					rules_o.add(rule);
				}else{
					rules_p.add(rule);
					rules_o.add(rule);
				}
			}
		}
	}
	public Map<String,Map> process(EcifData ecifData) throws Exception {
		JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		Map<String,Map> suspectCustMap= new HashMap<String,Map>();
		String sql="SELECT C.CUST_ID,C.CUST_NO,C.CUST_TYPE,C.CUST_STAT FROM M_CI_CUSTOMER C ,(";
		String sql1=") T WHERE C.CUST_ID=T.CUST_ID";
		List<SuspectCustRule> rules=null;
		if(MdmConstants.TX_CUST_PER_TYPE.equals(ecifData.getCustType())){
			rules=rules_p;
		}else{
			rules=rules_o;
		}
		StringBuffer sb=null;
		List<Object[]> resultList=null;
		Map maptemp=null;
		for(SuspectCustRule rule:rules){
			sb=new StringBuffer("");
			sb.append(sql).append(rule.getRuleSql()).append(sql1);
			resultList=(List<Object[]>)baseDAO.findByNativeSQLWithIndexParam(sb.toString(), ecifData.getCustId(MdmConstants.CUSTID_TYPE));
			if(resultList!=null && !resultList.isEmpty()){
				for(Object[] objects:resultList){
					if(suspectCustMap.get(objects[0].toString())==null){
						maptemp=new HashMap();
						maptemp.put("custId", objects[0].toString());
						maptemp.put("custNo", objects[1]);
						maptemp.put("custType", objects[2]);
						maptemp.put("custStat", objects[3]);
						maptemp.put("suspectCustRule", rule.getRuleCode());
						suspectCustMap.put(objects[0].toString(), maptemp);
					}
				}
			}
		}
		return suspectCustMap;
	}
}

class SuspectCustRule{
	/**
	 * @��������:ruleCode
	 * @��������:���ƿͻ��ж��������
	 * @since 1.0.0
	 */
	private String ruleCode;
	/**
	 * @��������:ruleName
	 * @��������:���ƿͻ��ж���������
	 * @since 1.0.0
	 */
	private String ruleName;
	/**
	 * @��������:ruleSql
	 * @��������:���ƿͻ��ж������ѯ���
	 * @since 1.0.0
	 */
	private String ruleSql;
	/**
	 *@���캯�� 
	 */
	public SuspectCustRule() {
		// TODO Auto-generated constructor stub
	}
	
	public SuspectCustRule(String ruleCode, String ruleName, String ruleSql) {
		this.ruleCode = ruleCode;
		this.ruleName = ruleName;
		this.ruleSql = ruleSql;
	}

	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleSql() {
		return ruleSql;
	}
	public void setRuleSql(String ruleSql) {
		this.ruleSql = ruleSql;
	}
}
