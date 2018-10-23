/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.integration.check.bs
 * @文件名：CompVerification.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-10:43:13
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：CompVerification
 * @类描述：数据组合验证
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午10:43:21   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午10:43:21
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
							log.warn("数据组合验证失败:{}",ecifData.getDetailDes());
							return false;
						}
						if (value_c != null && !value_c.equals(value)) {
							ReflectionUtils.setFieldValue(entity,
									checkModel.getCheckColName(), value_c);
						}

					}
				} catch (Exception e) {
					log.error("数据组合验证异常",e);
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
		/********取出组合验证关系*********/
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
