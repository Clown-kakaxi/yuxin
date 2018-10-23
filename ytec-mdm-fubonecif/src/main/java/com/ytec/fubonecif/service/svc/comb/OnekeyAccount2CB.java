package com.ytec.fubonecif.service.svc.comb;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;

import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.PropertyPlaceholderConfigurerExt;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;

/**
 * ECIF������Ŀ���
 * 
 * @author Administrator
 * 
 */
@Service
public class OnekeyAccount2CB implements IEcifBizLogic {

	private static Logger log = LoggerFactory.getLogger(OnekeyAccount2CB.class);
	private JPABaseDAO baseDAO;
	private String TlrNo;
	
	
	public void process(EcifData ecifData) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		String reqMsg = ecifData.getPrimalMsg();// �����ĵ�����
		// ��ˮ��--ReqSeqNo
		String reqSeqNo = ecifData.getReqSeqNo();
		if (StringUtils.isEmpty(reqSeqNo)) {
			String msg = "��Ϣ����������������ڵ���reqSeqNo������Ϊ��";
			log.error(msg);
			ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}
		Element body = ecifData.getBodyNode();// ��ȡ�ڵ�
		TlrNo = ecifData.getTlrNo();
		
		String txCode = body.element("txCode").getTextTrim();// ��ȡ���ױ��
		if (StringUtils.isEmpty(txCode)) {
			String msg = "��Ϣ����������������ڵ���txCode������Ϊ��";
			log.error(msg);
			ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
			return;
		}
		//��ȡ֤�����͡�֤������
		String custId = body.element("CUSTID").getTextTrim();// ��ȡ֤������
		String identNo = body.element("BRAIDID").getTextTrim();// ��ȡ֤������
		String ONLYPUSHINFO = body.element("ONLYPUSHINFO").getTextTrim();//�Ƿ�ֻ������Ϣ������
		boolean isOnlyPushInfo = ONLYPUSHINFO != null && ONLYPUSHINFO.equals("true") ? true : false;
		// У��ǿ��ֶ�
		boolean checkRes = this.checkNodeContent(ecifData);
		if (checkRes) {
			String reqCBMsg = this.packageReqMsg(ecifData);//������
			String resCBMsg = this.process(reqCBMsg);//��Ӧ����
			if (StringUtils.isEmpty(resCBMsg)) {
				String msg = "����ϵͳ���ر���Ϊ��";
				log.error(msg);
				ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), msg);
				return;
			}
			//������һЩ�ؼ��ֶ�
			String textTxStatCode = "";
			String textTxStatString = "";
			String textTxStatDesc = "";
			String textResult = "";
			String textCUSTCOD = "";
			String textACCNO = "";
			String textACCCY = "";
			String textErrNo = "";
			String textErrMsg = "";
			// ������Ӧ����
			resCBMsg = resCBMsg.substring(8);
			SAXReader reader = new SAXReader();
			StringReader sr = new StringReader(resCBMsg);
			InputSource is = new InputSource(sr);
			Document doc = reader.read(is);
			Element root = doc.getRootElement();
			//ResponseTail����
			Element resTail = root.element("ResponseTail");
			if(resTail != null){
				textTxStatCode = resTail.elementTextTrim("TxStatCode");
				textTxStatDesc = resTail.elementTextTrim("TxStatDesc");
			}
			//ResponseBody����
			Element resBody = root.element("ResponseBody");
			if(resBody != null){
				textResult = resBody.elementTextTrim("Result");
				textCUSTCOD = resBody.elementTextTrim("CUSTCOD");
				textACCNO = resBody.elementTextTrim("ACCNO");
				textACCCY = resBody.elementTextTrim("ACCCY");
				textErrNo = resBody.elementTextTrim("ErrNo");
				textErrMsg = resBody.elementTextTrim("ErrMsg");
			}
			if(StringUtils.isEmpty(textTxStatCode)){//û�з���״̬
				dealResponse(ecifData, textResult, textACCCY, textCUSTCOD, custId, textACCNO, textErrNo, textErrMsg, isOnlyPushInfo);
			}else{
				if(textTxStatCode.equals("000000")){
					dealResponse(ecifData, textResult, textACCCY, textCUSTCOD, custId, textACCNO, textErrNo, textErrMsg, isOnlyPushInfo);
				}else{
					if(!StringUtils.isEmpty(textTxStatCode) && !StringUtils.isEmpty(textTxStatDesc)){
						log.error(textTxStatDesc);
						ecifData.setStatus(textTxStatCode, textTxStatDesc);
						ecifData.setSuccess(false);
						return;
					}else{
						String errorMsg = "EAI��Ӧ�����ֶ�{TxStatCode,TxStatDesc}��ȫ";
						log.error(errorMsg);
						ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), errorMsg);
						ecifData.setSuccess(false);
						return;
					}
				}
			}
		}
		return;
	}

	/**
	 * ��鱨���ֶ�
	 * 
	 * @param ecifData
	 * @return
	 */
	private boolean checkNodeContent(EcifData ecifData) {
		boolean flag = true;
		try {
			Element body = ecifData.getBodyNode();// ��ȡ�ڵ�
			String[] checkNodeNms = new String[] { "IDCODE", "BRAIDID", "CUFULNM", "CUSNM", "CORCOUN", "RISCOUN",
					"BRANCH", "ACCODE" };
			for (int i = 0; i < checkNodeNms.length; i++) {
				Element ele = body.element(checkNodeNms[i]);
				if (ele == null || StringUtils.isEmpty(ele.getTextTrim())) {
					String msg = "��Ϣ����������������ڵ���{" + checkNodeNms[i] + "}������Ϊ��";
					log.error(msg);
					ecifData.setStatus(ErrorCode.ERR_XML_FILTER_VALUE_IS_NULL.getCode(), msg);
					ecifData.setSuccess(false);
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * ƴ�ӱ���
	 * 
	 * @param ecifData
	 * @return
	 */
	private String packageReqMsg(EcifData ecifData) {
		StringBuffer sb_msg = new StringBuffer();
		SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat df14 = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat df20 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		SimpleDateFormat df10 = new SimpleDateFormat("hhmmss");
		int ranI = new Random().nextInt(90) + 10;
		try {
			sb_msg.append("<?xml version=\"1.0\" encoding=\"GBK\"?>\n");
			sb_msg.append("<TransBody>\n");
			StringBuffer sb_msgHeader = new StringBuffer();
			// ��ȡ��Ҫ���͵����ĵ������ֶ�
			sb_msgHeader.append("    <RequestHeader>\n");
			sb_msgHeader.append("        <ReqSysCd>ECF</ReqSysCd>\n");
			sb_msgHeader.append("        <ReqSeqNo>" + df20.format(new Date()) + "</ReqSeqNo>\n");
			sb_msgHeader.append("        <ReqDt>" + df8.format(new Date()) + "</ReqDt>\n");
			sb_msgHeader.append("        <ReqTm>" + df10.format(new Date()) + "</ReqTm>\n");
			sb_msgHeader.append("        <DestSysCd>CB</DestSysCd>\n");
			sb_msgHeader.append("        <ChnlNo>82</ChnlNo>\n");
			sb_msgHeader.append("        <BrchNo>6801</BrchNo>\n");
			sb_msgHeader.append("        <BizLine>6491</BizLine>\n");
			sb_msgHeader.append("        <TrmNo>TRM10010</TrmNo>\n");
			sb_msgHeader.append("        <TrmIP>127.0.0.1</TrmIP>\n");
			sb_msgHeader.append("        <TlrNo>0000</TlrNo>\n");
			sb_msgHeader.append("    </RequestHeader>\n");
			sb_msg.append(sb_msgHeader);
			Element body = ecifData.getBodyNode();// ��ȡ�ڵ� 
			//��֤�����ͽ���ת��
			String identType = body.elementTextTrim("IDCODE");
			//�Ե�ַ�������⴦��
			String addrInfo = body.elementTextTrim("MAILAD1");
			String[] fullAddrInfo = this.getAddrInfo(addrInfo);
			StringBuffer sb_body = new StringBuffer();
			sb_body.append("    <RequestBody>\n");
			sb_body.append("        <txCode>CRM1</txCode>\n");// ϵͳ����
			sb_body.append("        <SysFun>ECF</SysFun>\n");// ϵͳ����
			sb_body.append("        <TranFun>CRM1</TranFun>\n");// ���״���
			sb_body.append("        <TranType>1</TranType>\n");// ��������1
			sb_body.append("        <VerNo>" + df14.format(new Date()) + ranI + "</VerNo>\n");// ��ˮ��
			sb_body.append("        <Filler>" + this.getStringByLimitLen(body.elementTextTrim("Filler"), 31) + "</Filler>\n");// ��ˮ��
			sb_body.append("        <IDCODE>" + identType + "</IDCODE>\n");// ֤������
			sb_body.append("        <BRAIDID>" + this.getStringByLimitLen(body.elementTextTrim("BRAIDID"), 20) + "</BRAIDID>\n");// ֤������
			sb_body.append("        <REFID>" + this.getStringByLimitLen(body.elementTextTrim("REFID"), 20) + "</REFID>\n");// ̨��֤/�۰�֤����
			sb_body.append("        <CUFULNM>" + this.getStringByLimitLen(body.elementTextTrim("CUFULNM"), 50) + "</CUFULNM>\n");// ������
			sb_body.append("        <CUSNM>" + this.getStringByLimitLen(body.elementTextTrim("CUSNM"), 35) + "</CUSNM>\n");// Ӣ����
			sb_body.append("        <CORCOUN>" + this.getStringByLimitLen(body.elementTextTrim("CORCOUN"), 3) + "</CORCOUN>\n");// ����
			sb_body.append("        <RISCOUN>" + this.getStringByLimitLen(body.elementTextTrim("RISCOUN"), 3) + "</RISCOUN>\n");// ���չ���
			sb_body.append("        <SEXIND>" + this.getStringByLimitLen(body.elementTextTrim("SEXIND"), 1) + "</SEXIND>\n");// �Ա�
			sb_body.append("        <ESTDATE>" + this.getStringByLimitLen(body.elementTextTrim("ESTDATE"), 8) + "</ESTDATE>\n");// ��������
			sb_body.append("        <MAILAD1>" + fullAddrInfo[0] + "</MAILAD1>\n");// ��ַ1
			sb_body.append("        <MAILAD2>" + fullAddrInfo[1] + "</MAILAD2>\n");// ��ַ1
			sb_body.append("        <MAILAD3>" + fullAddrInfo[2] + "</MAILAD3>\n");// ��ַ1
			sb_body.append("        <CUSTEL1>" + this.getStringByLimitLen(body.elementTextTrim("CUSTEL1"), 14) + "</CUSTEL1>\n");// �绰1
			sb_body.append("        <CUSTEL2>" + this.getStringByLimitLen(body.elementTextTrim("CUSTEL2"), 14) + "</CUSTEL2>\n");// �绰1
			sb_body.append("        <CUSTEL3>" + this.getStringByLimitLen(body.elementTextTrim("CUSTEL3"), 14) + "</CUSTEL3>\n");// �绰1
			sb_body.append("        <EMAILAD>" + this.getStringByLimitLen(body.elementTextTrim("EMAILAD"), 35) + "</EMAILAD>\n");// �ʼ���ַ
			sb_body.append("        <BRANCH>" + this.getStringByLimitLen(body.elementTextTrim("BRANCH"), 3) + "</BRANCH>\n");// �к�
			sb_body.append("        <ACCODE>" + this.getStringByLimitLen(body.elementTextTrim("ACCODE"), 10) + "</ACCODE>\n");// �ͻ��������
			sb_body.append("        <COCODE>" + this.getStringByLimitLen(body.elementTextTrim("COCODE"), 10) + "</COCODE>\n");// ����Ա����
//			sb_body.append("        <NEWOLD>" + this.getStringByLimitLen(body.elementTextTrim("NEWOLD"), 1) + "</NEWOLD>\n");// �¾ɻ���ʶ
			sb_body.append("        <ACCATE>" + this.getStringByLimitLen(body.elementTextTrim("ACCATE"), 3) + "</ACCATE>\n");// �˺�����
			sb_body.append("    </RequestBody>\n");
			sb_msg.append(sb_body);
			sb_msg.append("</TransBody>\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb_msg.toString();
	}

	private String process(String mxlmsg) {
		System.out.println("���Ŀ���������:" + mxlmsg);
		log.info("���Ŀ���������:" + mxlmsg);
		String msg = mxlmsg;
		/*String ip = "10.20.35.242";
		int port = 12027;*/
		String ip = PropertyPlaceholderConfigurerExt.getContextProperty("CBS.IP");
		String portStr = PropertyPlaceholderConfigurerExt.getContextProperty("CBS.PORT");
		int port = Integer.parseInt(portStr);
		
		NioClient cl = new NioClient(ip, port);
		String resp = null;
		try {
			resp = cl.SocketCommunication(String.format("%08d", msg.getBytes("GBK").length) + msg);
		} catch (Exception e) {
			log.info("���ú���ϵͳ��ʱ!");
		}
		log.info("���Ŀ������ر���:" + resp);
		return resp;
	}
	
	/**
	 * ����Ҫ�󣬶�ȫ����ַ��������⴦��
	 * @param oriStr
	 * @param limitLen
	 * @return
	 */
	public String getStringByLimitLen(String oriStr, int limitLen){
		String retStr = "";
		if(oriStr == null){
			oriStr = "";
		}
		oriStr = oriStr.trim();
		int oriLen = oriStr.length();
		if(oriLen > limitLen){
			oriStr = oriStr.substring(0, (oriLen - limitLen));
		}
		int fullCharCount = 0;
		for(int i = 0; i < oriLen; i++){
			String str1 = oriStr.substring(i, i+1);
			boolean isFull1 = str1.length() == str1.getBytes().length ? false : true;//���Ϊ��ǣ�����Ϊȫ��
			if(isFull1){
				fullCharCount++;
			}
			if((i < (oriLen - 1))){//����������һ��
				String str2 = oriStr.substring(i+1, i+2);
				boolean isFull2 = str2.length() == str2.getBytes().length ? false : true;
				if((isFull1 && !isFull2) || (!isFull1 && isFull2)){
					retStr += str1 + " ";
				}else{
					retStr += str1;
				}
			}else if(i == (oriLen - 1)){
				retStr += oriStr.substring(i, i+1);
			}
		}
		if(fullCharCount > 0){
			retStr = " " + retStr + " ";
		}
		int endLen = retStr.getBytes().length;
		if(endLen <= limitLen){
			for (int i = 0; i < (limitLen - endLen); i++) {
				retStr += " ";
			}
		}else{
			log.error("�������ֶ�ʱ�����ֶ�����["+oriStr+"],��������["+limitLen+"]");
			return "";
		}
		return retStr;
	}
	
	
	
	/**
	 * �Ե�ַ�������⴦��
	 * @param oriAddr
	 * @return
	 */
	public String[] getAddrInfo(String oriAddr){
		String[] addrs = new String[3];
		int oriLen = oriAddr.length();
		int fullCount = 0;
		String endStr = "";
		for (int i = 0; i < oriLen; i++) {
			String str1 = oriAddr.substring(i, (i+1));
			boolean isFull1 = str1.length() == str1.getBytes().length ? false : true;//�Ƿ���ȫ��
			if(isFull1){
				fullCount++;
			}
			if(i < (oriLen - 1)){//�������һ��
				String str2 = oriAddr.substring((i + 1), (i + 2));
				boolean isFull2 = str2.length() == str2.getBytes().length ? false : true;//�Ƿ���ȫ��
				if((isFull1 && !isFull2) || (!isFull1 && isFull2)){
					endStr += (str1 + " ");
				}else{
					endStr += str1;
				}
			}else if(i == (oriLen - 1)){
				endStr += oriAddr.substring(i, i+1);
			}
		}
		if(fullCount > 0){
			endStr = " " + endStr + " ";
		}
		int desLen = endStr.getBytes().length;
		for (int i = 0; i < (105 - desLen); i++) {
			endStr += " ";
		}
		byte[] endB = endStr.getBytes();
		byte[] b1 = Arrays.copyOfRange(endB, 0, 35);
		byte[] b2 = Arrays.copyOfRange(endB, 35, 70);
		byte[] b3 = Arrays.copyOfRange(endB, 70, 105);
		addrs[0] = new String(b1);
		addrs[1] = new String(b2);
		addrs[2] = new String(b3);
		return addrs;
	}
	
	
	
	/**
	 * ������Ӧ����
	 * @param ecifData		��������
	 * @param textResult	���ķ���״̬
	 * @param textACCCY		����
	 * @param textCUSTCOD	���Ŀͻ���
	 * @param identType		֤������
	 * @param identNo		֤������
	 * @param textACCNO		���Ŀͻ��˺�
	 * @param textErrNo		���ķ��ش�����
	 * @param textErrMsg	���ķ��ش�����Ϣ
	 */
	public void dealResponse(EcifData ecifData, String textResult, String textACCCY, String textCUSTCOD, String custId, String textACCNO, String textErrNo, String textErrMsg, boolean isOnlyPushInfo){
		//EAI���ر�����û���κ���Ӧ��Ϣ
		if(StringUtils.isEmpty(textResult)){
			String errorMsg = "EAI���ر�����û���κ���Ӧ��Ϣ";
			log.error(errorMsg);
			ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), errorMsg);
			ecifData.setSuccess(false);
			return;
		}else{//��Ȼû��ResponseTail������Ϣ������ResponseBody���з�����Ϣ
			if(textResult.equals("OK")){//�ɹ�����
				String hxAcccy1 = "";
				String hxAcccy2 = "";
				if(!isOnlyPushInfo){
					if(textACCCY.length() >= 6){
						hxAcccy1 = textACCCY.substring(0, 3);
						hxAcccy2 = textACCCY.substring(3, 6);
					}
					//���º��Ŀͻ���
					String upJql = "update MCiCustomer t set t.coreNo=:coreNo where t.custId=:custId";
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("coreNo", textCUSTCOD);
					params.put("custId", custId);
					int upRes = this.baseDAO.batchExecuteWithNameParam(upJql, params);
					if(upRes < 0){
						String errorMsg = "���Ŀ����ɹ���������ECIF�ͻ���Ϣʱ��������ϵ����Ա";
						log.error(errorMsg);
						ecifData.setSuccess(false);
						ecifData.getWriteModelObj().setResult("TxStatCode", ErrorCode.ERR_ALL.getCode());
						ecifData.getWriteModelObj().setResult("TxStatDesc", errorMsg);
						ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), errorMsg);
						return;
					}
					//��ӽ�����������Ϣ
					String crossIndexId = "100000000";
					String seqSql = "select SEQ_CROSSINDEX_ID.Nextval from dual";//����
					List<Object> seqL = this.baseDAO.findByNativeSQLWithNameParam(seqSql, null);
					if(seqL != null && seqL.size() == 1){
						crossIndexId = seqL.get(0).toString();
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
					String format = sdf.format(new Date());
					String insertSql = "INSERT INTO M_CI_CROSSINDEX";
					insertSql += "(CROSSINDEX_ID, SRC_SYS_NO, SRC_SYS_CUST_NO, CUST_ID, LAST_UPDATE_SYS, LAST_UPDATE_USER, LAST_UPDATE_TM, TX_SEQ_NO)";
					insertSql += " VALUES('" + crossIndexId + "', 'CB', '" + textCUSTCOD + "',";
					insertSql += " '" + custId + "', 'ECIF', 'CB',";
					insertSql += " TO_TIMESTAMP('"+format+"', 'yyyy--mm-dd hh24:mi:ss:ff'),'" + ecifData.getReqSeqNo() + "')";
					this.baseDAO.batchExecuteNativeWithIndexParam(insertSql);
				}
				ecifData.resetStatus();
				ecifData.getWriteModelObj().setResult("TxStatCode", "000000");
				ecifData.getWriteModelObj().setResult("TxStatDesc", "���Ŀ����ɹ�");
				ecifData.getWriteModelObj().setResult("CUSTCOD", textCUSTCOD);
				ecifData.getWriteModelObj().setResult("ACCNO", textACCNO);
				ecifData.getWriteModelObj().setResult("ACCCY1", hxAcccy1);
				ecifData.getWriteModelObj().setResult("ACCCY2", hxAcccy2);
//				ecifData.setStatus(ErrorCode.SUCCESS);
				ecifData.setStatus("000000", "���Ŀ����ɹ�");
				ecifData.setSuccess(true);
				log.info("���Ŀ����ɹ�");
				return;
			}else if("ER".equals(textResult)){
				//ErrNo ErrMsg
				//�����0014����˵�����ϻ�����Ҫ��ECIF��ȡ���Ŀͻ��ŷ��ص�CRM
				if(textErrNo.equals("0014")){
					String custCoreNoJql = "select t from MCiCustomer t where t.custId=:custId";
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("custId", custId);
					List<MCiCustomer> custInfoList = this.baseDAO.findWithNameParm(custCoreNoJql, params);
					String ecifCoreNo = "";
					if(custInfoList != null && custInfoList.size() == 1){//���н���һ����Ч����
						ecifCoreNo = custInfoList.get(0).getCoreNo();
					}
					ecifData.getWriteModelObj().setResult("CoreNo", ecifCoreNo == null ? "" : ecifCoreNo);//��Ӻ��Ŀͻ��ţ����ǿ���Ϊ��
				}
				//��ӷ�����Ϣ
				ecifData.getWriteModelObj().setResult("TxStatCode", textErrNo);
				ecifData.getWriteModelObj().setResult("TxStatDesc", textErrMsg);
				ecifData.setStatus(textErrNo, textErrMsg);
				ecifData.setSuccess(false);
				log.error("���Ŀ���ʧ�ܣ�["+textErrNo+"--"+textErrMsg+"]");
				return;
			}else{
				String errorMsg = "����ʧ��";
				log.error("����ʧ��");
				ecifData.setStatus(ErrorCode.ERR_ALL.getCode(), errorMsg);
				ecifData.setSuccess(false);
				return;
			}
		}
	}
}
