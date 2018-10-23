/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.component.biz.identification
 * @文件名：GetContIdByEcifcustno.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:57:35
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.component.biz.identification;


import java.util.List;

import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.service.component.general.CustStatusMgr;


/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：GetContIdByEcifcustno
 * @类描述：按ECIF客户识别
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:57:36   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:57:36
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
@SuppressWarnings("rawtypes")
public class GetContIdByEcifcustno extends AbsBizGetContId {
	private JPABaseDAO baseDAO;
	/**
	 * 
	 * @param Map
	 *            <String,String> ecifCustNo Map中必要的键ecifCustNo
	 * @return ContMessage
	 */
	public void bizGetContId(EcifData ecifData) {
		// 获取ecif客户号
		String ecifCustNo = (String)ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_ECIFCUSTNO);

		// 获得不到数据错误
		if (ecifCustNo == null || ecifCustNo.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_ECIFCUSTNO.getCode(),ErrorCode.ERR_ECIF_NULL_ECIFCUSTNO.getChDesc());
			return;
		} else {
			// 根据ecif客户号，查找客户标识
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			List result = baseDAO.findByNativeSQLWithIndexParam("SELECT CUST_ID,CUST_TYPE,CUST_STAT FROM M_CI_CUSTOMER WHERE CUST_ID=?"
					,ecifCustNo);

			// 成功返回客户标识，失败返回数据不存在
			if (result == null || result.size() == 0) {
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
				return;
			} else if (result.size() == 1){
				ecifData.resetStatus();
				Object[] ob=(Object[])result.get(0);
				ecifData.setCustId(ob[0].toString());
//				ecifData.setEcifCustNo(ecifCustNo);
				ecifData.getCustId(ecifCustNo);
				ecifData.setCustType((String)ob[1]);
				ecifData.setCustStatus((String)ob[2]);
			}else{
				//判断查询出多个客户，排除失效客户(注销,合并等)。
				Object[] ob=null;
				int availableNum=0;
				CustStatus custStatCtrl=null;
				for(int i=0;i<result.size();i++){
					ob=(Object[]) result.get(i);
					if((custStatCtrl=CustStatusMgr.getInstance().getCustStatus((String)ob[2]))!=null&&custStatCtrl.isReOpen()){//失效客户
							continue;
					}
					availableNum++;
					if(availableNum==1){
						ecifData.setCustId(ob[0].toString());
//						ecifData.setEcifCustNo(ecifCustNo);
						ecifData.getCustId(ecifCustNo);
						ecifData.setCustType((String)ob[1]);
						ecifData.setCustStatus((String)ob[2]);
					}else{
						ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_NOT_EXPECT.getCode(),"返回客户不唯一");
						return;
					}
				}
				if(availableNum==0){
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
					return;
				}else{
					ecifData.resetStatus();
				}
			}
		}
		return;
	}
}
