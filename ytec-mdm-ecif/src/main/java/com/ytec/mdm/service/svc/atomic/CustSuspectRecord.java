/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.service.svc.atomic
 * @�ļ�����CustSuspectRecord.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-12:02:05
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�CustSuspectRecord
 * @�����������ƿͻ��ϲ���ֲ�����¼
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����12:02:06
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����12:02:06
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 *       -------- -------- ----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 */
@Service
public class CustSuspectRecord {
	private static Logger log = LoggerFactory.getLogger(CustSuspectRecord.class);
	private static String MERGESTATE = "1";
	private static String SPLITSTATE = "2";
	private JPABaseDAO baseDAO;

	/***
	 * @param ecifData
	 * @param custId �����ͻ�ID
	 * @param custNo �����ͻ�ecif�ͻ���
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
				// record.setReserveCustId((Long)custId);// �����ͻ�ID
				OIdUtils.setIdValueByName(record, "reserveCustId", custId.toString());// �����ͻ�ID
				record.setReserveCustNo(custNo);// �����ͻ�ecif�ͻ���
				// record.setMergedCustId(Long.valueOf(ecifData.getCustId()));
				OIdUtils.setIdValueByName(record, "mergedCustId", ecifData.getCustId());
//				record.setMergedCustNo(ecifData.getEcifCustNo());
				record.setMergedCustNo(ecifData.getCustId());
			}
			record.setMergeStat(MERGESTATE);
			record.setMergeDesc("�Ѻϲ�");
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
				// splitRecord.setReserveCustId((Long)custId);// �����ͻ�ID
				OIdUtils.setIdValueByName(splitRecord, "reserveCustId", custId.toString());// �����ͻ�ID
				splitRecord.setReserveCustNo(custNo);// �����ͻ�ecif�ͻ���
				splitRecord.setMergedCustId(Long.valueOf(ecifData.getCustId()));
				OIdUtils.setIdValueByName(splitRecord, "mergedCustId", ecifData.getCustId());
//				splitRecord.setMergedCustNo(ecifData.getEcifCustNo());
				splitRecord.setMergedCustNo(ecifData.getCustId());
			}
			splitRecord.setSplitStat(SPLITSTATE);
			splitRecord.setSplitDesc("�Ѳ��");
			splitRecord.setSplitOperator(ecifData.getTlrNo());
			splitRecord.setImportOperbrc(ecifData.getBrchNo());
			splitRecord.setSplitOperTime(new Timestamp(System.currentTimeMillis()));
			baseDAO.merge(splitRecord);
			baseDAO.flush();
		}

	}
}
