package com.ytec.mdm.service.component.biz.identification;

import java.util.List;

import com.ytec.mdm.base.bo.CustStatus;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.service.component.general.CustStatusMgr;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：GetAccountOnlyByIdent
 * @类描述：仅按证件类型和证件号码识别
 * @功能描述:仅按证件类型和证件号码识别 -------- --------
 *                     ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
public class GetAccountOnlyByIdent extends AbsBizGetContId {

	private JPABaseDAO baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");

	/**
	 * 按证件类型和证件号码作为客户识别规则，同一证件类型和证件号码的都视为一个客户
	 * 
	 */
	@Override
	public void bizGetContId(EcifData ecifData) {
		System.err.println("按证件类型和证件号码作为客户识别规则，同一证件类型和证件号码的都视为一个客户");
		// 获取信息--证件类型、证件号码、证件客户名
		String identTpCd = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_IDENTTPCD);//证件类型
		String identNo = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_IDENTNO);//证件号码
		String identName = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_IDENTNAME);//客户姓名
		String custType = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_CUST_TYPE);//客户类型
		
		// 三证中有一个数据为空，则认为数据不正确
		
		if (identTpCd == null || identTpCd.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_IDENTTP.getCode(), ErrorCode.ERR_ECIF_NULL_IDENTTP.getChDesc());
			return;
		} else if (identNo == null || identNo.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_IDENTNO.getCode(), ErrorCode.ERR_ECIF_NULL_IDENTNO.getChDesc());
			return;
		} else if (identName == null || identName.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_IDENTNAME.getCode(),ErrorCode.ERR_ECIF_NULL_IDENTNAME.getChDesc());
			return;
		} else {
			String identifierName = null;//数据库中的证件类型
			if (MdmConstants.TX_CUST_PER_TYPE.equals(custType)) {
				identifierName = MdmConstants.MODEL_PERSONIDENTIFIER;
			} else {
				identifierName = MdmConstants.MODEL_ORGIDENTIFIER;
			}
			// 根据数据在数据库中查找客户标识
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			StringBuffer jql = new StringBuffer("");
			jql.append("SELECT distinct I.custId,C.custType,C.custStat FROM ");
			jql.append(identifierName);
			//只根据证件类型和证件号码查询，不查询客户姓名
			jql.append(" I,MCiCustomer C WHERE I.identType=? AND I.identNo=? AND C.custId=I.custId ");//AND C.custType=?");
			List result = baseDAO.findWithIndexParam(jql.toString(), identTpCd, identNo);
			
			// 封装查询结果
			if (result == null || result.size() == 0) {
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
				return;
			} else if (result.size() == 1) {
				ecifData.resetStatus();
				Object[] ob = (Object[]) result.get(0);
				ecifData.setCustId(ob[0].toString());
				// ecifData.setEcifCustNo((String)ob[1]);
				ecifData.setCustType((String) ob[1]);
				ecifData.setCustStatus((String) ob[2]);
			} else {
				// 判断查询出多个客户，排除失效客户(注销,合并等)。
				Object[] ob = null;
				int availableNum = 0;
				CustStatus custStatCtrl = null;
				for (int i = 0; i < result.size(); i++) {
					ob = (Object[]) result.get(i);
					System.out.println("ob:" + ob + ", size:" + ob.length);
					// if((custStatCtrl=CustStatusMgr.getInstance().getCustStatus((String)ob[3]))!=null&&custStatCtrl.isReOpen()){//失效客户
					if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus((String) ob[2])) != null
							&& custStatCtrl.isReOpen()) {// 失效客户
						// if(!MdmConstants.TX_CODE_K.equals((ecifData.getTxType()))){
						continue;
						// }
					}
					availableNum++;
					if (availableNum == 1) {
						ecifData.setCustId(ob[0].toString());
						// ecifData.setEcifCustNo((String)ob[1]);
						ecifData.setCustType((String) ob[1]);
						ecifData.setCustStatus((String) ob[2]);
					} else {
						ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_NOT_EXPECT.getCode(), "返回客户不唯一");
						return;
					}
				}
				if (availableNum == 0) {
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
					return;
				} else {
					ecifData.resetStatus();
				}
			}
		}
		// 返回识别信息
		return;
	}

}
