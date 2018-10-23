/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.component.biz.identification
 * @文件名：GetContIdByIdent.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:57:51
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
 * @类名称：GetContIdByIdent
 * @类描述：按三证识别
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:57:52
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:57:52
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
@SuppressWarnings("rawtypes")
public class GetContIdByIdent extends AbsBizGetContId {
	private JPABaseDAO baseDAO;

	/**
	 * @param Map
	 *        <String,String> ident Map中三个必要的键identTpCd,idengNo,identName
	 * @return ContMessage
	 */
	public void bizGetContId(EcifData ecifData) {
		// 获取三证信息，证件类型，证件号码，证件名称

		/**
		 * WriteModel model = ecifData.getWriteModelObj();
		 * Map map = model.getOperMap();
		 * Set<Object> keys = map.keySet();
		 * Iterator itr = keys.iterator();
		 * while(itr.hasNext()){
		 * Object key = itr.next();
		 * System.out.println("key:"+key.toString()+",value:"+map.get(key));
		 * System.err.println("instance class:" +map.get(key).getClass().getName());
		 * if(map.get(key) instanceof List){
		 * System.out.println("instanceof list");
		 * }
		 * if(map.get(key) instanceof MCiIdentifier){
		 * MCiIdentifier ident = (MCiIdentifier) map.get(key);
		 * System.out.println("instanceof MCiIdentifier-->> identType:"+ident.getIdentType()+", identNo:"+ident.getIdentNo());
		 * }
		 * if(map.get(key) instanceof Identifier){
		 * Identifier ident = (Identifier) map.get(key);
		 * System.out.println("instanceof Identifier-->> identType:"+ident.getIdentType()+", identNo:"+ident.getIdentNo());
		 * }
		 * }
		 * //
		 */
		String identTpCd = (String) ecifData.getWriteModelObj().getOperMap()
				.get(MdmConstants.TX_DEF_GETCONTID_IDENTTPCD);
		String identNo = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_IDENTNO);
		String identName = (String) ecifData.getWriteModelObj().getOperMap()
				.get(MdmConstants.TX_DEF_GETCONTID_IDENTNAME);
		String custType = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_CUST_TYPE);

		// 三证中有一个数据为空，则认为数据不正确
		if (identTpCd == null || identTpCd.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_IDENTTP.getCode(), ErrorCode.ERR_ECIF_NULL_IDENTTP.getChDesc());
			return;
		} else if (identNo == null || identNo.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_IDENTNO.getCode(), ErrorCode.ERR_ECIF_NULL_IDENTNO.getChDesc());
			return;
		} else if (identName == null || identName.equals("")) {
			ecifData.setStatus(ErrorCode.ERR_ECIF_NULL_IDENTNAME.getCode(),
					ErrorCode.ERR_ECIF_NULL_IDENTNAME.getChDesc());
			return;
		} else {
			String identifierName = null;
			if (MdmConstants.TX_CUST_PER_TYPE.equals(custType)) {
				identifierName = MdmConstants.MODEL_PERSONIDENTIFIER;
			} else {
				identifierName = MdmConstants.MODEL_ORGIDENTIFIER;
			}
			// 根据数据在数据库中查找库户标识
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			StringBuffer jql = new StringBuffer("");
			jql.append("SELECT distinct I.custId,C.custType,C.custStat FROM ");
			jql.append(identifierName);
//			jql.append(" I,MCiCustomer C WHERE I.identType=? AND I.identNo=? AND I.identCustName=? AND C.custId=I.custId AND C.custType=?");
//			List result = baseDAO.findWithIndexParam(jql.toString(), identTpCd, identNo, identName, custType);
			jql.append(" I,MCiCustomer C WHERE I.identType=? AND I.identNo=? AND I.identCustName=? AND C.custId=I.custId ");//AND C.custType=?");
			List result = baseDAO.findWithIndexParam(jql.toString(), identTpCd, identNo, identName);
			// System.out.println("jql:"+jql);
			// System.out.println("identTpCd:"+identTpCd);
			// System.out.println("identNo:"+identNo);
			// System.out.println("identName:"+identName);
			// System.out.println("custType:"+custType);

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
