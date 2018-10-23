/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.service.svc.atomic
 * @文件名：CustSuspectRecord.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-12:02:05
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.service.svc.atomic;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.OIdUtils;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.domain.txp.AppCustMergeRecord;
import com.ytec.mdm.domain.txp.AppCustSplitRecord;

/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：CustSuspectRecord
 * @类描述：疑似客户合并拆分操作记录
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 下午12:02:06
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 下午12:02:06
 * @修改备注：
 * @修改日期 修改人员 修改原因
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 */
@Service
public class CustSuspectRecord {
	private static Logger log = LoggerFactory.getLogger(CustSuspectRecord.class);
	private static String MERGESTATE = "1";
	private static String SPLITSTATE = "2";
	private JPABaseDAO baseDAO;

	/***
	 * @param ecifData
	 * @param custId 保留客户ID
	 * @param custNo 保留客户ecif客户号
	 * @throws Exception
	 */
	@Transactional
	public void process(EcifData ecifData, Object custId, String custNo, boolean merge) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		if (merge) {
			AppCustMergeRecord record = null;
			// List<AppCustMergeRecord> mergeRecordList=baseDAO.findWithIndexParam("FROM AppCustMergeRecord T where T.mergedCustNo=? AND T.reserveCustNo=?",ecifData.getEcifCustNo(),custNo);
			List<AppCustMergeRecord> mergeRecordList = baseDAO.findWithIndexParam(
					"FROM AppCustMergeRecord T where T.mergedCustNo=? AND T.reserveCustNo=?", ecifData.getCustId(),
					custNo);
			if (mergeRecordList != null && !mergeRecordList.isEmpty()) {
				record = mergeRecordList.get(0);
			} else {
				record = new AppCustMergeRecord();
				record.setMergeRecordId(OIdUtils.getIdOfLong());
				// record.setReserveCustId((Long)custId);// 保留客户ID
				OIdUtils.setIdValueByName(record, "reserveCustId", custId.toString());// 保留客户ID
				record.setReserveCustNo(custNo);// 保留客户ecif客户号
				// record.setMergedCustId(Long.valueOf(ecifData.getCustId()));
				OIdUtils.setIdValueByName(record, "mergedCustId", ecifData.getCustId());
//				record.setMergedCustNo(ecifData.getEcifCustNo());
				record.setMergedCustNo(ecifData.getCustId());
			}
			record.setMergeStat(MERGESTATE);
			record.setMergeDesc("已合并");
			record.setMergeOperator(ecifData.getTlrNo());
			record.setImportOperbrc(ecifData.getBrchNo());
			record.setMergeOperTime(new Timestamp(System.currentTimeMillis()));
			baseDAO.merge(record);
			baseDAO.flush();
		} else {
			AppCustSplitRecord splitRecord = null;
//			List<AppCustSplitRecord> splitRecordList = baseDAO.findWithIndexParam(
//					"FROM AppCustSplitRecord T where T.mergedCustNo=? AND T.reserveCustNo=?", ecifData.getEcifCustNo(),
//					custNo);
			List<AppCustSplitRecord> splitRecordList = baseDAO.findWithIndexParam(
					"FROM AppCustSplitRecord T where T.mergedCustNo=? AND T.reserveCustNo=?", ecifData.getCustId(),
					custNo);
			if (splitRecordList != null && !splitRecordList.isEmpty()) {
				splitRecord = splitRecordList.get(0);
			} else {
				splitRecord = new AppCustSplitRecord();
				splitRecord.setSplitRecordId(OIdUtils.getIdOfLong());
				// splitRecord.setReserveCustId((Long)custId);// 保留客户ID
				OIdUtils.setIdValueByName(splitRecord, "reserveCustId", custId.toString());// 保留客户ID
				splitRecord.setReserveCustNo(custNo);// 保留客户ecif客户号
				splitRecord.setMergedCustId(Long.valueOf(ecifData.getCustId()));
				OIdUtils.setIdValueByName(splitRecord, "mergedCustId", ecifData.getCustId());
//				splitRecord.setMergedCustNo(ecifData.getEcifCustNo());
				splitRecord.setMergedCustNo(ecifData.getCustId());
			}
			splitRecord.setSplitStat(SPLITSTATE);
			splitRecord.setSplitDesc("已拆分");
			splitRecord.setSplitOperator(ecifData.getTlrNo());
			splitRecord.setImportOperbrc(ecifData.getBrchNo());
			splitRecord.setSplitOperTime(new Timestamp(System.currentTimeMillis()));
			baseDAO.merge(splitRecord);
			baseDAO.flush();
		}

	}
}
