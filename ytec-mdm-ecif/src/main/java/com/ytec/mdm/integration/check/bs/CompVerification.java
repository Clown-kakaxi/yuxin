/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.integration.check.bs
 * @�ļ�����CompVerification.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-10:43:13
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.integration.check.bs;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.base.bo.CheckModel;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.util.ReflectionUtils;
import com.ytec.mdm.integration.transaction.facade.IModelFilter;
import com.ytec.mdm.server.common.BusinessCfg;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�CompVerification
 * @�����������������֤
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����10:43:21   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����10:43:21
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class CompVerification implements IModelFilter {
	private Logger log = LoggerFactory
			.getLogger(CompVerification.class);
	private Map<String,CheckModel> checkModels=new HashMap<String,CheckModel>();
	
	public boolean execute(EcifData ecifData, Object entity) {
		CheckModel checkModel = null;
		if ((checkModel = checkModels.get(entity.getClass().getSimpleName())) == null) {
			return true;
		}
		String rulerCode = (String) ReflectionUtils.getFieldValue(entity,
				checkModel.getCheckCodeColum());
		if (rulerCode != null && !rulerCode.isEmpty()) {
			String ctRule = null;
			if ((ctRule = checkModel.getCheckRule(rulerCode)) != null) {
				try {
					String value = null;
					value = (String) ReflectionUtils.getFieldValue(entity,
							checkModel.getCheckColName());
					if (value != null && !value.isEmpty()) {
						String value_c = TxBizRuleFactory.doFilter(ecifData,
								ctRule, checkModel.getCheckColName(),value, new String[] {});
						if (!ecifData.isSuccess()) {
							log.warn("���������֤ʧ��:{}",ecifData.getDetailDes());
							return false;
						}
						if (value_c != null && !value_c.equals(value)) {
							ReflectionUtils.setFieldValue(entity,
									checkModel.getCheckColName(), value_c);
						}

					}
				} catch (Exception e) {
					log.error("���������֤�쳣",e);
					ecifData.setStatus(ErrorCode.ERR_SERVER_INTERNAL_ERROR);
					return false;
				}
			}
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see com.ytec.mdm.integration.transaction.facade.IModelFilter#init()
	 */
	@Override
	public void init(String ruleExpr) {
		// TODO Auto-generated method stub
		/********ȡ�������֤��ϵ*********/
		String checkModelsCfg[]=BusinessCfg.getStringArray("checkModels");
		if(checkModelsCfg!=null){
			checkModels.clear();
			for(int i=0;i<checkModelsCfg.length;i++){
				String value_i[]= checkModelsCfg[i].split("\\|");
				if(value_i!=null && value_i.length==3){
					CheckModel checkModel=new CheckModel(value_i[0],value_i[1],value_i[2]);
					String[] checkRuleStr=BusinessCfg.getStringArray(value_i[0]+".ctRule");
					if(checkRuleStr!=null){
						for(int j=0;j<checkRuleStr.length;j++){
							String value_r[]= checkRuleStr[j].split("\\|");
							if(value_r!=null&& value_r.length==2){
								checkModel.addCheckRule(value_r[0], value_r[1]);
							}
						}
					}
					checkModels.put(value_i[0], checkModel);
				}
			}
		}
	}

}
