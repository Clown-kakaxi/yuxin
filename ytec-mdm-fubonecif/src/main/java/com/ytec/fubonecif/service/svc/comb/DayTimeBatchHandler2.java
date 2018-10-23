/**
 * @��Ŀ����ytec-mdm-fubonecif
 * @������com.ytec.fubonecif.service.svc
 * @�ļ�����DayTimeBatchHandler2.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:07:22
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.fubonecif.service.svc.comb;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.FtpOperator;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.base.util.csv.CsvBean;
import com.ytec.mdm.server.common.BusinessCfg;
import com.ytec.mdm.service.facade.IMCIdentifying;
import com.ytec.mdm.service.svc.atomic.DayTimeBatchHelper;
import com.ytec.fubonecif.domain.MCiCustomer;
//import com.ytec.fubonecif.domain.MCiNametitle;
//import com.ytec.fubonecif.domain.MCiPerIdentifier;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.fubonecif.service.svc.atomic.DayTimeBatchHandlerDao;

/**
 * @��Ŀ���ƣ�ytec-mdm-fubonecif 
 * @�����ƣ�DayTimeBatchHandler2
 * @�������������������״�����2
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:07:23   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:07:23
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
@Service
@Scope("prototype")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DayTimeBatchHandler2 extends DayTimeBatchHelper {
	private static Logger log = LoggerFactory
			.getLogger(DayTimeBatchHandler2.class);
	private FtpOperator fp;
	private String remotePath;
	private String ip;
	private int port;
	
	private JPABaseDAO baseDAO;
	
	private IMCIdentifying util;
	
	private DayTimeBatchHandlerDao handlerDao;
	

	@Override
	public void getControlInfo() {
		// TODO Auto-generated method stub
		if(StringUtil.isEmpty(data.getValueFromParameterMap("asyn"))){
			this.asyn = Boolean.valueOf(data.getValueFromParameterMap("asyn"));
		}else{
			this.asyn=true;
		}
		localPath = BusinessCfg.getString("batchlocalPath");
		remotePath=data.getValueFromParameterMap("remotePath");
		if(StringUtil.isEmpty(remotePath)){
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),"Զ��Ŀ¼Ϊ��");
			return;
		}
		batchReqFiles=new String[1];
		batchReqFiles[0]=data.getValueFromParameterMap("fileName");
		if(StringUtil.isEmpty(batchReqFiles[0])){
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),"�����ļ���Ϊ��");
			return;
		}
		ip=data.getValueFromParameterMap("ip");
		port=Integer.valueOf(data.getValueFromParameterMap("port"));
		fp = new FtpOperator(ip, port, BusinessCfg.getString("ftpUser"), BusinessCfg.getString("ftpPass"),remotePath, localPath);
		
		separator='|'; // �ָ���
		noquotechar=true; // �Ƿ����ַ������÷� ��asd ���ǡ�asd��
		infoLength=3; // ÿ��������Ϣ��
		
		batchRspFiles=new String[1];
		batchRspFiles[0]="result_"+batchReqFiles[0];
		fileCharSet=data.getCharsetName();
		
		util = (IMCIdentifying) SpringContextUtils.getBean(MdmConstants.MCIDENTIFYING);
		handlerDao=(DayTimeBatchHandlerDao)SpringContextUtils.getBean("dayTimeBatchHandlerDao");
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");	
	}

	@Override
	public void getBatchFile() {
		// TODO Auto-generated method stub
		log.info("���������ļ�����");
		/*** ��ȡ�����ļ� **/
		for(String batchFile:batchReqFiles){
			if (!fp.downloadFile(batchFile)) {
				this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
						"�����ļ�%sʧ��", batchFile);
				return;
			}
		}
	}

	@Override
	public void putBatchFile() {
		// TODO Auto-generated method stub
		/*** �ϴ������ļ� **/
		log.info("���������ļ��ϴ�");
		for(String bResFile:batchRspFiles){
			if (!fp.uploadFile(bResFile)) {
				this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
						"�ϴ��ļ�%sʧ��", bResFile);
				return;
			}
		}
	}

	@Override
	public void synchroBatch() {
		// TODO Auto-generated method stub
		/*** ����ͬ����֪ͨ����ϵͳȡ���� **/
	}

	
	public void executeBatchOne(CsvBean batchLine) throws Exception{
		// TODO Auto-generated method stub
		String[] line= batchLine.getOpLineMsg();   //ȡ�ò�������
		String ecifCustNo=null; 
		List result = baseDAO.findByNativeSQLWithIndexParam("SELECT C.CUST_NO,C.CUST_STAT  FROM M_CI_PER_IDENTIFIER I,M_CI_CUSTOMER C  WHERE I.IDENT_TYPE=?  AND I.IDENT_NO=?  AND I.IDENT_CUST_NAME=? AND C.CUST_ID=I.CUST_ID AND C.CUST_TYPE='1'",
				line[0],line[1],line[2]);
		List custEntityList=new ArrayList();
		if (result == null || result.size() == 0) {
			//����
			// ����contId
			Long custId=null;
			
			custId=Long.valueOf(util.getEcifCustId("1"));
				// ����ECIF�ͻ���
			ecifCustNo = util.getEcifCustNo("1");
			
			if (StringUtil.isEmpty(ecifCustNo)) {
				batchLine.setResultState(ErrorCode.ERR_ECIF_GEN_ECIFCUSTNO_ERROR.getCode(), ErrorCode.ERR_ECIF_GEN_ECIFCUSTNO_ERROR.getChDesc());
				return;
			}
			// �ͻ���Ϣ
			MCiCustomer customer = null;
			customer = new MCiCustomer();
			customer.setCustId(custId.toString());
//			customer.setCustNo(ecifCustId);
			customer.setCustType("1");
			customer.setCreateDate(new Date());
			customer.setCreateTime(new Timestamp(System.currentTimeMillis()));
			customer.setCustStat(MdmConstants.STATE);
			customer.setCreateBranchNo(data.getBrchNo());
			customer.setCreateTellerNo(data.getTlrNo());
			custEntityList.add(customer);
			
			MCiIdentifier ident=new MCiIdentifier();
//			ident.setCustId(custId);
			ident.setIdentType(line[0]);
			ident.setIdentNo(line[1]);
			ident.setIdentCustName(line[2]);
			custEntityList.add(ident);
			
			handlerDao.saveCustomer(custEntityList, data);
			
		}else{
			//��������,ȡ�ͻ���
			Object[] ob=(Object[])result.get(0);
			ecifCustNo=(String)ob[0];
			//String custState=(String)ob[1];
			
		}
		/***
		 * ��װ��Ӧ����
		 */
		batchLine.setRspLineMsgs((String[])ArrayUtils.addAll(batchLine.getReqLineMsgs(),new String[]{"000000","�ɹ�",ecifCustNo}));
		return;
	}

	@Override
	public boolean checkBatchOne(CsvBean batchLine) {
		// TODO Auto-generated method stub
		batchLine.setOpLineMsg(batchLine.getReqLineMsgs());
		return true;
	}

	@Override
	public void responseWarnOne(CsvBean batchLine) {
		// TODO Auto-generated method stub
		batchLine.setRspLineMsgs((String[])ArrayUtils.addAll(batchLine.getReqLineMsgs(),new String[]{batchLine.getErrorCode(),batchLine.getErrorDesc(),""}));
		return;
	}

	@Override
	public boolean transRspBatchOne(CsvBean batchLine) {
		// TODO Auto-generated method stub
		return true;
	}

}
