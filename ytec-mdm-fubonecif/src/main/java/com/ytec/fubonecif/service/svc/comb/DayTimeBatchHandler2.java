/**
 * @项目名：ytec-mdm-fubonecif
 * @包名：com.ytec.fubonecif.service.svc
 * @文件名：DayTimeBatchHandler2.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:07:22
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-fubonecif 
 * @类名称：DayTimeBatchHandler2
 * @类描述：联机批量交易处理案例2
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:07:23   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:07:23
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),"远程目录为空");
			return;
		}
		batchReqFiles=new String[1];
		batchReqFiles[0]=data.getValueFromParameterMap("fileName");
		if(StringUtil.isEmpty(batchReqFiles[0])){
			this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),"批量文件名为空");
			return;
		}
		ip=data.getValueFromParameterMap("ip");
		port=Integer.valueOf(data.getValueFromParameterMap("port"));
		fp = new FtpOperator(ip, port, BusinessCfg.getString("ftpUser"), BusinessCfg.getString("ftpPass"),remotePath, localPath);
		
		separator='|'; // 分隔符
		noquotechar=true; // 是否有字符串引用符 如asd 还是“asd”
		infoLength=3; // 每行数据信息数
		
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
		log.info("联机批量文件下载");
		/*** 获取数据文件 **/
		for(String batchFile:batchReqFiles){
			if (!fp.downloadFile(batchFile)) {
				this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
						"下载文件%s失败", batchFile);
				return;
			}
		}
	}

	@Override
	public void putBatchFile() {
		// TODO Auto-generated method stub
		/*** 上传数据文件 **/
		log.info("联机批量文件上传");
		for(String bResFile:batchRspFiles){
			if (!fp.uploadFile(bResFile)) {
				this.data.setStatus(ErrorCode.ERR_BATCH_FILE_ERROR.getCode(),
						"上传文件%s失败", bResFile);
				return;
			}
		}
	}

	@Override
	public void synchroBatch() {
		// TODO Auto-generated method stub
		/*** 数据同步或通知其他系统取数据 **/
	}

	
	public void executeBatchOne(CsvBean batchLine) throws Exception{
		// TODO Auto-generated method stub
		String[] line= batchLine.getOpLineMsg();   //取得操作数据
		String ecifCustNo=null; 
		List result = baseDAO.findByNativeSQLWithIndexParam("SELECT C.CUST_NO,C.CUST_STAT  FROM M_CI_PER_IDENTIFIER I,M_CI_CUSTOMER C  WHERE I.IDENT_TYPE=?  AND I.IDENT_NO=?  AND I.IDENT_CUST_NAME=? AND C.CUST_ID=I.CUST_ID AND C.CUST_TYPE='1'",
				line[0],line[1],line[2]);
		List custEntityList=new ArrayList();
		if (result == null || result.size() == 0) {
			//开户
			// 生成contId
			Long custId=null;
			
			custId=Long.valueOf(util.getEcifCustId("1"));
				// 生成ECIF客户号
			ecifCustNo = util.getEcifCustNo("1");
			
			if (StringUtil.isEmpty(ecifCustNo)) {
				batchLine.setResultState(ErrorCode.ERR_ECIF_GEN_ECIFCUSTNO_ERROR.getCode(), ErrorCode.ERR_ECIF_GEN_ECIFCUSTNO_ERROR.getChDesc());
				return;
			}
			// 客户信息
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
			//跳过开户,取客户号
			Object[] ob=(Object[])result.get(0);
			ecifCustNo=(String)ob[0];
			//String custState=(String)ob[1];
			
		}
		/***
		 * 组装响应数据
		 */
		batchLine.setRspLineMsgs((String[])ArrayUtils.addAll(batchLine.getReqLineMsgs(),new String[]{"000000","成功",ecifCustNo}));
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
