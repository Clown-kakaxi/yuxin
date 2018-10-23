/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.component.biz.identification
 * @文件名：GetContIdByIdent.java
 * @版本信息：1.0.0
 * @日期：2017-07-29-11:57:51
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.component.biz.identification;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @类名称：GetCustInfoBycustno
 * @类描述：按三证识别
 * @功能描述:
 * @创建人：sunjing5@yusys.com.cn
 * @创建时间：2017-07-29 
 * @修改人：sunjing5@yusys.com.cn
 * @修改时间：2017-07-29 
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
@SuppressWarnings("rawtypes")
public class GetCustInfoBycustno extends AbsBizGetContId {
	private static Logger log = LoggerFactory.getLogger(GetCustInfoBycustno.class);
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
				.get(MdmConstants.TX_DEF_GETCONTID_IDENTTPCD);//证件类型
		String identNo = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_IDENTNO);
		String identName = (String) ecifData.getWriteModelObj().getOperMap()
				.get(MdmConstants.TX_DEF_GETCONTID_IDENTNAME);
		String custType = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_CUST_TYPE);
		log.debug("从交易数据中获取证件类型：{},证件号：{},证件名称：{},客户类型：{}",identTpCd,identNo,identName,custType);
		log.debug("校验三证数据，三证中如果有一个数据为空，则认为数据不正确");
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
			log.debug("三证校验合法");
			String identifierName = null;
			if (MdmConstants.TX_CUST_PER_TYPE.equals(custType)) {
				identifierName = MdmConstants.MODEL_PERSONIDENTIFIER;
			} else {
				identifierName = MdmConstants.MODEL_ORGIDENTIFIER;
			}
			// 根据数据在数据库中查找库户标识
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			//jql:查出相同证件类型和证件号码的客户名，修改客户名
			StringBuffer jql = new StringBuffer("");
			jql.append("SELECT distinct I.custId,C.custType,C.custStat,C.custName FROM ");
			jql.append(identifierName);
//			jql.append(" I,MCiCustomer C WHERE I.identType=? AND I.identNo=? AND I.identCustName=? AND C.custId=I.custId AND C.custType=?");
//			List result = baseDAO.findWithIndexParam(jql.toString(), identTpCd, identNo, identName, custType);
			jql.append(" I,MCiCustomer C WHERE I.identType=? AND I.identNo=?  AND C.custId=I.custId AND C.coreNo is null ");//AND C.custType=?");
			log.debug("根据证件类型和证件号查询核心客户号为空的客户信息");
			List result = baseDAO.findWithIndexParam(jql.toString(), identTpCd, identNo);
			// System.out.println("jql:"+jql);
			// System.out.println("identTpCd:"+identTpCd);
			// System.out.println("identNo:"+identNo);
			// System.out.println("identName:"+identName);
			// System.out.println("custType:"+custType);
			//查出相同客户名称的客户
			log.debug("根据客户名称查询核心客户号为空的客户信息");
			StringBuffer jq2 = new StringBuffer("");
			jq2.append("SELECT distinct I.custId,C.custType,C.custStat,C.custName FROM ");
			jq2.append(identifierName);
//			jql.append(" I,MCiCustomer C WHERE I.identType=? AND I.identNo=? AND I.identCustName=? AND C.custId=I.custId AND C.custType=?");
//			List result = baseDAO.findWithIndexParam(jql.toString(), identTpCd, identNo, identName, custType);
			jq2.append(" I,MCiCustomer C WHERE I.identCustName=?  AND C.custId=I.custId AND C.coreNo is null ");//AND C.custType=?");
			List result2 = baseDAO.findWithIndexParam(jq2.toString(),   identName);
			// System.out.println("jql:"+jql);
			// System.out.println("identTpCd:"+identTpCd);
			// System.out.println("identNo:"+identNo);
			// System.out.println("identName:"+identName);
			// System.out.println("custType:"+custType);
			// 封装查询结果
			if ((result == null || result.size() == 0)&&(result2 == null || result2.size() == 0)) {//该客户不存在
				log.debug("系统中不存在该客户");
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
				return;
			} else if ((result.size() == 1)&&(result2 == null || result2.size() == 0)) {//存在相同证件的客户
				log.debug("系统中存在相同证件的客户，将交易数据中的客户号、客户类型、客户状态设置成系统中的");
				ecifData.resetStatus();
//				ecifData.setStatus(ErrorCode.ERR_SAME_IDENT_EXIST);
				Object[] ob = (Object[]) result.get(0);
				ecifData.setCustId(ob[0].toString());
				// ecifData.setEcifCustNo((String)ob[1]);
				ecifData.setCustType((String) ob[1]);
				ecifData.setCustStatus((String) ob[2]);
			} else if ((result2.size() == 1)&&(result == null || result.size() == 0)) {//存在相同名称的客户
				log.debug("系统中存在相同名称的客户，将交易数据中的客户号、客户类型、客户状态设置成系统中的");
				ecifData.resetStatus();
//				ecifData.setStatus(ErrorCode.ERR_SAME_IDENT_EXIST);
				Object[] ob = (Object[]) result2.get(0);
				ecifData.setCustId(ob[0].toString());
				// ecifData.setEcifCustNo((String)ob[1]);
				ecifData.setCustType((String) ob[1]);
				ecifData.setCustStatus((String) ob[2]);
			}else if ((result2.size() == 1)&&(result.size() == 1)) {//完全相同
				log.debug("系统中存在证件类型、证件号、客户名称完全相同的客户，将交易数据中的客户号、客户类型、客户状态设置成系统中的");
				ecifData.resetStatus();
//				ecifData.setStatus(ErrorCode.ERR_SAME_IDENT_EXIST);
				Object[] ob = (Object[]) result.get(0);
				ecifData.setCustId(ob[0].toString());
				// ecifData.setEcifCustNo((String)ob[1]);
				ecifData.setCustType((String) ob[1]);
				ecifData.setCustStatus((String) ob[2]);
			}else {
				log.debug("同证件或者同客户名的查询出多个客户，需要排除失效客户(注销,合并等)。");
				// 判断查询出多个客户，排除失效客户(注销,合并等)。
				Object[] ob = null;
				int availableNum = 0;
				CustStatus custStatCtrl = null;
				log.debug("开始处理同证件类型和证件号的客户");
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
						log.debug("排除失效客户后，任然存在多条数据");
						ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_NOT_EXPECT.getCode(), "返回客户不唯一");
						return;
					}
				}
				if (availableNum == 0) {
					log.debug("排除失效客户后，发现没有符合的数据，客户不存在");
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
