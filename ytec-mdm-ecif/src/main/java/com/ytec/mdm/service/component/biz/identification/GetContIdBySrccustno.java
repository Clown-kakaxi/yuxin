/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.component.biz.identification
 * @文件名：GetContIdBySrccustno.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:58:05
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
import com.ytec.mdm.service.facade.IBizGetContId;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：GetContIdBySrccustno
 * @类描述：按核心客户号识别
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:58:05   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:58:05
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
@Service
@SuppressWarnings("rawtypes")
public class GetContIdBySrccustno extends AbsBizGetContId {
	private JPABaseDAO baseDAO;

	/**
	 * 
	 * @param Map
	 *            <String,String> srccustno Map中两个必要的键srcSysCd,srcSysCustNo
	 * @return ContMessage
	 */
	public void bizGetContId(EcifData ecifData) {
		// 从Map中获取源系统号、 源系统客户号
		String srcSysCd = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_SRCSYSCD);
		String srcSysCustNo = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_SRCSYSCUSTNO);
		String conTpCd=(String)ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_CUST_TYPE);

		// 源系统号或源客户号有任意一个为空，则数据不正确
		if (srcSysCd == null || srcSysCd.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_SRCSYSCD.getCode(),ErrorCode.ERR_ECIF_NULL_SRCSYSCD.getChDesc());
			return;
		} else if (srcSysCustNo == null || srcSysCustNo.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_SRCSYSCUSTNO.getCode(),ErrorCode.ERR_ECIF_NULL_SRCSYSCUSTNO.getChDesc());
			return;
		} else {
			// 按源系统号和源客户号在数据库中查找客户标识
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			StringBuffer jql = new StringBuffer("");
			List result=null;
			jql.append(" SELECT distinct X.CUST_ID,C.CUST_NO,C.CUST_TYPE,C.CUST_STAT");
			jql.append(" FROM M_CI_CROSSINDEX X,M_CI_CUSTOMER C");
			jql.append(" WHERE X.SRC_SYS_NO=?");
			jql.append(" AND X.SRC_SYS_CUST_NO=?");
			jql.append(" AND C.CUST_ID=X.CUST_ID");
			if(conTpCd!=null && !conTpCd.isEmpty()){
				jql.append(" AND C.CUST_TYPE=?");
				result = baseDAO.findByNativeSQLWithIndexParam(jql.toString(),srcSysCd,srcSysCustNo,conTpCd);
			}else{
				result = baseDAO.findByNativeSQLWithIndexParam(jql.toString(),srcSysCd,srcSysCustNo);
			}

			// 封装查询结果
			if (result == null || result.size() == 0) {
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
				return;
			} else if (result.size() == 1)  {
				ecifData.resetStatus();
				Object[] ob=(Object[])result.get(0);
				ecifData.setCustId(ob[0].toString());
//				ecifData.setEcifCustNo((String)ob[1]);
				ecifData.getCustId((String)ob[1]);
				ecifData.setCustType((String)ob[2]);
				ecifData.setCustStatus((String)ob[3]);
			}else{
				//判断查询出多个客户，排除失效客户(注销,合并等)。
				Object[] ob=null;
				int availableNum=0;
				CustStatus custStatCtrl=null;
				for(int i=0;i<result.size();i++){
					ob=(Object[]) result.get(i);
					if((custStatCtrl=CustStatusMgr.getInstance().getCustStatus((String)ob[3]))!=null&&custStatCtrl.isReOpen()){//失效客户
						continue;
					}
					availableNum++;
					if(availableNum==1){
						ecifData.setCustId(ob[0].toString());
//						ecifData.setEcifCustNo((String)ob[1]);
						ecifData.getCustId((String)ob[1]);
						ecifData.setCustType((String)ob[2]);
						ecifData.setCustStatus((String)ob[3]);
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
