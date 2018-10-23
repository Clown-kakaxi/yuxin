/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.component.biz.identification
 * @�ļ�����GetContIdByIdent.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2017-07-29-11:57:51
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�GetCustInfoBycustno
 * @������������֤ʶ��
 * @��������:
 * @�����ˣ�sunjing5@yusys.com.cn
 * @����ʱ�䣺2017-07-29 
 * @�޸��ˣ�sunjing5@yusys.com.cn
 * @�޸�ʱ�䣺2017-07-29 
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
@SuppressWarnings("rawtypes")
public class GetCustInfoBycustno extends AbsBizGetContId {
	private static Logger log = LoggerFactory.getLogger(GetCustInfoBycustno.class);
	private JPABaseDAO baseDAO;

	/**
	 * @param Map
	 *        <String,String> ident Map��������Ҫ�ļ�identTpCd,idengNo,identName
	 * @return ContMessage
	 */
	public void bizGetContId(EcifData ecifData) {
		// ��ȡ��֤��Ϣ��֤�����ͣ�֤�����룬֤������

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
				.get(MdmConstants.TX_DEF_GETCONTID_IDENTTPCD);//֤������
		String identNo = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_DEF_GETCONTID_IDENTNO);
		String identName = (String) ecifData.getWriteModelObj().getOperMap()
				.get(MdmConstants.TX_DEF_GETCONTID_IDENTNAME);
		String custType = (String) ecifData.getWriteModelObj().getOperMap().get(MdmConstants.TX_CUST_TYPE);
		log.debug("�ӽ��������л�ȡ֤�����ͣ�{},֤���ţ�{},֤�����ƣ�{},�ͻ����ͣ�{}",identTpCd,identNo,identName,custType);
		log.debug("У����֤���ݣ���֤�������һ������Ϊ�գ�����Ϊ���ݲ���ȷ");
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
			log.debug("��֤У��Ϸ�");
			String identifierName = null;
			if (MdmConstants.TX_CUST_PER_TYPE.equals(custType)) {
				identifierName = MdmConstants.MODEL_PERSONIDENTIFIER;
			} else {
				identifierName = MdmConstants.MODEL_ORGIDENTIFIER;
			}
			// �������������ݿ��в��ҿ⻧��ʶ
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			//jql:�����֤ͬ�����ͺ�֤������Ŀͻ������޸Ŀͻ���
			StringBuffer jql = new StringBuffer("");
			jql.append("SELECT distinct I.custId,C.custType,C.custStat,C.custName FROM ");
			jql.append(identifierName);
//			jql.append(" I,MCiCustomer C WHERE I.identType=? AND I.identNo=? AND I.identCustName=? AND C.custId=I.custId AND C.custType=?");
//			List result = baseDAO.findWithIndexParam(jql.toString(), identTpCd, identNo, identName, custType);
			jql.append(" I,MCiCustomer C WHERE I.identType=? AND I.identNo=?  AND C.custId=I.custId AND C.coreNo is null ");//AND C.custType=?");
			log.debug("����֤�����ͺ�֤���Ų�ѯ���Ŀͻ���Ϊ�յĿͻ���Ϣ");
			List result = baseDAO.findWithIndexParam(jql.toString(), identTpCd, identNo);
			// System.out.println("jql:"+jql);
			// System.out.println("identTpCd:"+identTpCd);
			// System.out.println("identNo:"+identNo);
			// System.out.println("identName:"+identName);
			// System.out.println("custType:"+custType);
			//�����ͬ�ͻ����ƵĿͻ�
			log.debug("���ݿͻ����Ʋ�ѯ���Ŀͻ���Ϊ�յĿͻ���Ϣ");
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
			// ��װ��ѯ���
			if ((result == null || result.size() == 0)&&(result2 == null || result2.size() == 0)) {//�ÿͻ�������
				log.debug("ϵͳ�в����ڸÿͻ�");
				ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
				return;
			} else if ((result.size() == 1)&&(result2 == null || result2.size() == 0)) {//������֤ͬ���Ŀͻ�
				log.debug("ϵͳ�д�����֤ͬ���Ŀͻ��������������еĿͻ��š��ͻ����͡��ͻ�״̬���ó�ϵͳ�е�");
				ecifData.resetStatus();
//				ecifData.setStatus(ErrorCode.ERR_SAME_IDENT_EXIST);
				Object[] ob = (Object[]) result.get(0);
				ecifData.setCustId(ob[0].toString());
				// ecifData.setEcifCustNo((String)ob[1]);
				ecifData.setCustType((String) ob[1]);
				ecifData.setCustStatus((String) ob[2]);
			} else if ((result2.size() == 1)&&(result == null || result.size() == 0)) {//������ͬ���ƵĿͻ�
				log.debug("ϵͳ�д�����ͬ���ƵĿͻ��������������еĿͻ��š��ͻ����͡��ͻ�״̬���ó�ϵͳ�е�");
				ecifData.resetStatus();
//				ecifData.setStatus(ErrorCode.ERR_SAME_IDENT_EXIST);
				Object[] ob = (Object[]) result2.get(0);
				ecifData.setCustId(ob[0].toString());
				// ecifData.setEcifCustNo((String)ob[1]);
				ecifData.setCustType((String) ob[1]);
				ecifData.setCustStatus((String) ob[2]);
			}else if ((result2.size() == 1)&&(result.size() == 1)) {//��ȫ��ͬ
				log.debug("ϵͳ�д���֤�����͡�֤���š��ͻ�������ȫ��ͬ�Ŀͻ��������������еĿͻ��š��ͻ����͡��ͻ�״̬���ó�ϵͳ�е�");
				ecifData.resetStatus();
//				ecifData.setStatus(ErrorCode.ERR_SAME_IDENT_EXIST);
				Object[] ob = (Object[]) result.get(0);
				ecifData.setCustId(ob[0].toString());
				// ecifData.setEcifCustNo((String)ob[1]);
				ecifData.setCustType((String) ob[1]);
				ecifData.setCustStatus((String) ob[2]);
			}else {
				log.debug("֤ͬ������ͬ�ͻ����Ĳ�ѯ������ͻ�����Ҫ�ų�ʧЧ�ͻ�(ע��,�ϲ���)��");
				// �жϲ�ѯ������ͻ����ų�ʧЧ�ͻ�(ע��,�ϲ���)��
				Object[] ob = null;
				int availableNum = 0;
				CustStatus custStatCtrl = null;
				log.debug("��ʼ����֤ͬ�����ͺ�֤���ŵĿͻ�");
				for (int i = 0; i < result.size(); i++) {
					ob = (Object[]) result.get(i);
					System.out.println("ob:" + ob + ", size:" + ob.length);
					// if((custStatCtrl=CustStatusMgr.getInstance().getCustStatus((String)ob[3]))!=null&&custStatCtrl.isReOpen()){//ʧЧ�ͻ�
					if ((custStatCtrl = CustStatusMgr.getInstance().getCustStatus((String) ob[2])) != null
							&& custStatCtrl.isReOpen()) {// ʧЧ�ͻ�
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
						log.debug("�ų�ʧЧ�ͻ�����Ȼ���ڶ�������");
						ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_NOT_EXPECT.getCode(), "���ؿͻ���Ψһ");
						return;
					}
				}
				if (availableNum == 0) {
					log.debug("�ų�ʧЧ�ͻ��󣬷���û�з��ϵ����ݣ��ͻ�������");
					ecifData.setStatus(ErrorCode.ERR_ECIF_NOT_EXIST_CONTID);
					return;
				} else {
					ecifData.resetStatus();
				}
			}
		}

		// ����ʶ����Ϣ
		return;
	}
}
