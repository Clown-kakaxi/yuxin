package com.ytec.mdm.service.svc.common;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.beanutils.MethodUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecc.echain.workflow.engine.EVO;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.service.bo.ObjectReturnMessage;
import com.ytec.mdm.service.component.biz.identification.GetObjectByBusinessKey;
import com.ytec.mdm.service.svc.atomic.QueryCustState;
import com.yuchengtech.bcrm.common.action.EchainCommonAction;
import com.yuchengtech.bob.common.CommonService;

@Service
public class UpdateGeneral implements IEcifBizLogic {
	private static Logger log = LoggerFactory.getLogger(UpdateGeneral.class);
	private JPABaseDAO baseDAO;
	private EcifData ecifData;

	@Override
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public void process(EcifData data) throws Exception {
		baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
		this.ecifData = data;
		
		String txCode = data.getTxCode();
		if(txCode!=null && !"".equals(txCode)){//�жϰݷ������ƶ��Ŵ�������Ϣ���ͣ�1.���� 2.ά��
			if("addPotenCustVisitInfo".equals(txCode) || "addHistCustVisitInfo".equals(txCode)){//�����»��ݷü�¼�������ɻ��ݷü�¼
				Element body = data.getBodyNode(); // ��ȡ�ڵ�
				String taskNumber = body.element("interviewRecord").element("taskNumber").getTextTrim();//������
				String customerSource = getCustomerSource(taskNumber);
				if(customerSource.equals("1") || customerSource.equals("2")){
					String sql = "insert into ocrm_f_interview_source_record values('"+taskNumber+"','"+customerSource+"')";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
		}
		baseDAO.flush();
		
		if(txCode != null && !"".equals(txCode)){
			if("addPotenCustVisitInfo".equals(txCode) || "addHistCustVisitInfo".equals(txCode)){//�����»��ݷü�¼�������ɻ��ݷü�¼
				Element body=data.getBodyNode();
				CommonService commonService = new CommonService();
				EchainCommonAction commonAction = new EchainCommonAction();
				String flag = body.element("flag") == null ? null:body.element("flag").getTextTrim();//�ж��ƶ��Ŵ������ݷ���Ϣ�ύ�Ƿ������̱�־
				String mgrId = body.element("interviewRecord").element("mgrId").getTextTrim();	//������
				String orgId = null;
				List mgrList = new ArrayList();
				Map<String,String> map = null;
				EVO vo=new EVO();
				if(flag != null && !"".equals(flag)){
					if("2".equals(flag)){			//�ƶ��Ŵ�ѡ�������̣�������������ɺ󱣴�����
						String instanceId =null;
						try{
							String taskType = body.element("interviewRecord").element("taskType").getTextTrim();	//�ж��»����߾ɻ���1�»���0�ɻ�
							String visitType = body.element("interviewRecord").element("visitType").getTextTrim();//2��ʾ�ɻ��°�
							String taskNumber = body.element("interviewRecord").element("taskNumber").getTextTrim();
							String custName = body.element("interviewRecord").element("custName").getTextTrim();
							String jobName = null;
							//String instanceId =null;
							if(taskType != null && !"".equals(taskType)){
								if("1".equals(taskType)){				//�»�
									jobName = "���̽��»��ݷø���_"+custName;
									instanceId = "QSJVISIT_"+taskNumber.split(",")[0]+"_1"+"_"+new SimpleDateFormat("HHmmss").format(new java.util.Date());
									//ͨ��wfid��������,ʵ�����ô��εķ�ʽ��ȡ,����echain������ʵ��
									log.info("instanceId>>>:"+instanceId);
									log.info("mgrId>>>:"+mgrId);
									vo = commonService.initWorkflowByWfidAndInstanceid("95", jobName, null, instanceId,mgrId);
									String nodeId = vo.getNodeID();//�����˽ڵ�id
									String nextNodeId = null;      //��һ�������˽ڵ�id
									mgrList = commonAction.getNextNodeList(vo);
									orgId = vo.getOrgid();
									Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
									responseEle.addElement("instanceId").setText(vo.getInstanceID());
									responseEle.addElement("nodeId").setText(nodeId);
									responseEle.addElement("nodeStatus").setText("0");
									if(mgrList != null && mgrList.size() > 0){
										for(int i=0;i<mgrList.size();i++){
											Element mgrInfo = responseEle.addElement("nextMgrInfo");
											map=(Map) mgrList.get(i);
											if(map.get("nextNodeId") == null){//�����ǲ��ǿ��ܴ�������������
												mgrInfo.addElement("nextMgrId").setText(map.get("userId") == null ? "" : map.get("userId").substring(2));
												mgrInfo.addElement("nextMgrName").setText(map.get("userName") == null ? "" : map.get("userName"));
											}else{
												nextNodeId=map.get("nextNodeId");
											}
										}
									}
									responseEle.addElement("nextNodeId").setText(nextNodeId == null ? "" : nextNodeId);
									data.setRepNode(responseEle);
								}else if("0".equals(taskType)){//�ɻ�
									if("2".equals(visitType)){ //�ɻ��°�
										jobName = "���̽�ɻ��°��ݷø���_"+custName;
										instanceId = "QSJVISIT_"+taskNumber.split(",")[0]+"_01"+"_"+new SimpleDateFormat("HHmmss").format(new java.util.Date());
										//ͨ��wfid��������,ʵ�����ô��εķ�ʽ��ȡ,����echain������ʵ��
										vo = commonService.initWorkflowByWfidAndInstanceid("95", jobName, null, instanceId,mgrId);
										String nodeId = vo.getNodeID();//�����˽ڵ�id
										String nextNodeId = null;      //��һ�������˽ڵ�id
										mgrList = commonAction.getNextNodeList(vo);
										orgId = vo.getOrgid();
										Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
										responseEle.addElement("instanceId").setText(vo.getInstanceID());
										responseEle.addElement("nodeId").setText(nodeId);
										responseEle.addElement("nodeStatus").setText("0");
										if(mgrList != null && mgrList.size() > 0){
											for(int i=0;i<mgrList.size();i++){
												Element mgrInfo = responseEle.addElement("nextMgrInfo");
												map=(Map) mgrList.get(i);
												if(map.get("nextNodeId") == null){
													mgrInfo.addElement("nextMgrId").setText(map.get("userId") == null ? "" : map.get("userId").substring(2));
													mgrInfo.addElement("nextMgrName").setText(map.get("userName") == null ? "" : map.get("userName"));
												}else{
													nextNodeId=map.get("nextNodeId");
												}
											}
										}
										responseEle.addElement("nextNodeId").setText(nextNodeId == null ? "" : nextNodeId);
										data.setRepNode(responseEle);
									}else{//�ɻ�(���ɻ��°�)
										jobName = "���̽�ɻ��ݷø���_"+custName;
										instanceId = "QSJVISIT_"+taskNumber.split(",")[0]+"_0"+"_"+new SimpleDateFormat("HHmmss").format(new java.util.Date());
										//ͨ��wfid��������,ʵ�����ô��εķ�ʽ��ȡ,����echain������ʵ��
										vo = commonService.initWorkflowByWfidAndInstanceid("95", jobName, null, instanceId,mgrId);
										String nodeId = vo.getNodeID();//�����˽ڵ�id
										String nextNodeId = null;      //��һ�������˽ڵ�id
										mgrList = commonAction.getNextNodeList(vo);
										Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
										orgId = vo.getOrgid();
										responseEle.addElement("instanceId").setText(vo.getInstanceID());
										responseEle.addElement("nodeId").setText(nodeId);
										responseEle.addElement("nodeStatus").setText("0");
										if(mgrList != null && mgrList.size() > 0){
											for(int i=0;i<mgrList.size();i++){
												Element mgrInfo = responseEle.addElement("nextMgrInfo");
												map=(Map) mgrList.get(i);
												if(map.get("nextNodeId") == null){
													mgrInfo.addElement("nextMgrId").setText(map.get("userId") == null ? "" : map.get("userId").substring(2));
													mgrInfo.addElement("nextMgrName").setText(map.get("userName") == null ? "" : map.get("userName"));
												}else{
													nextNodeId=map.get("nextNodeId");
												}
											}
										}
										responseEle.addElement("nextNodeId").setText(nextNodeId == null ? "" : nextNodeId);
										data.setRepNode(responseEle);
									}
								}
							}	
						}catch(Exception e){
							//�쳣�����޸�����״̬
							e.printStackTrace();
							log.info(e.getMessage());
							String sql="update wf_instance_whole_property set WFSTATUS='3' where INSTANCEID='"+instanceId+"'";
							baseDAO.batchExecuteNativeWithIndexParam(sql,null);
							ecifData.setStatus(ErrorCode.ERR_UNKNOWN_ERROR.getCode(), "�����쳣��ֹ");
							return;
						}
					}else if("3".equals(flag)){//�ƶ��Ŵ�ѡ������˷���CRM,CRM��ɵ�ǰ�ڵ�����ύ�������񣬱�������
						//����ƶ��Ŵ��ύ���������̱�����Ϣ
						Connection conn=null;
						try {
							Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
							Properties properties = new Properties();
							try {
								InputStream is = QueryCustState.class
										.getResourceAsStream("/jdbc.properties");
								properties.load(is);
								is.close(); 
							} catch (IOException e) {
								e.printStackTrace();
							}
							String url = properties.getProperty("jdbc.url");
							String user = properties.getProperty("jdbc.username");
							String password = properties.getProperty("jdbc.password");
							conn = DriverManager.getConnection(url, user, password);
						
						String instanceId = body.element("instanceId").getTextTrim();
						String nodeId = body.element("nodeId").getTextTrim();
						String nodeStatus = body.element("nodeStatus").getTextTrim();
						String nextNodeId = body.element("nextNodeId").getTextTrim();
						String nextMgrId = body.element("nextMgrInfo").element("nextMgrId").getTextTrim();
						//String nextMgrName = body.element("nextMgrInfo").element("nextMgrName").getTextTrim();
						vo.setConnection(conn);
						vo.setInstanceID(instanceId);
						vo.setNodeID(nodeId);
						vo.setNextNodeID(nextNodeId);
						vo.setNodeStatus(nodeStatus);
						vo.setNextNodeUser("U."+nextMgrId);
						vo.setOrgid(mgrId.substring(0, 3));
						vo.setCurrentUserID(mgrId);
						try{
							commonAction.wfCompleteJob(vo);
						}catch(Exception e){
							e.printStackTrace();
							ecifData.setStatus(ErrorCode.ERR_OTHER_ERROR.getCode(), "�ύ��������ʧ��");
							return;
						}
							saveModel(ecifData);
							String sql = "update Ocrm_f_Interview_Record t set  t.review_State= '02'  where t.task_number='"+instanceId.split("_")[1]+"'";
							baseDAO.batchExecuteNativeWithIndexParam(sql,null);
						}catch(Exception e){		
							e.printStackTrace();
							if(conn == null){
								log.error("��ȡ���ݿ�����ʧ��");
							}
							log.error("��ǰ�ڵ�����ύ��������ʧ��");
						}finally{
							if(conn !=  null){
								conn.close();
							}
						}
					}else if("4".equals(flag)){
						String instanceId = body.element("instanceId").getTextTrim();
						if(instanceId == null || instanceId.equals("")){
							ecifData.setStatus(ErrorCode.ERR_UNKNOWN_ERROR.getCode(), "����ʵ����Ϊ��");
						}else{
							//�ƶ��Ŵ����죬���������̣��޸�����״̬
							String sql="update wf_instance_whole_property set WFSTATUS='3' where INSTANCEID='"+instanceId+"'";
							baseDAO.batchExecuteNativeWithIndexParam(sql,null);
//							ecifData.setStatus(ErrorCode.SUCCESS.getCode(), "���촦��ɹ����ĵ��Ѿ�����������");
							String sql1 = "update Ocrm_f_Interview_Record t set  t.review_State= '1'  where t.task_number='"+instanceId.split("_")[1]+"'";
							baseDAO.batchExecuteNativeWithIndexParam(sql1,null);
						}
						return;
						
					}else if("1".equals(flag)){
						saveModel(ecifData);//�������棬�ƶ��Ŵ�����������
					}
				}else{
					saveModel(ecifData);//��װ�ϰ汾��app
				}
			}else{
				saveModel(ecifData);
			}
		}
		
//		// ��ȡ���ݲ����洢����
//		List<Object> objList = this.ecifData.getWriteModelObj().getOpModelList();
//		for (Object obj : objList) {
//
//			GetObjectByBusinessKey findObj = (GetObjectByBusinessKey) SpringContextUtils.getBean("getObjectByBusinessKey");
//			ObjectReturnMessage objMessage = findObj.bizGetObject(obj, false);
//			Object oldObj = objMessage.getObject();
//			if (oldObj != null) {
//				obj = this.cover(obj, oldObj);
//			}
//			log.info("����ʵ����({})���󣬴洢�����ݿ�", obj.getClass().getName());
//			baseDAO.save(obj);
//		}
//		baseDAO.flush();
		
	     if(txCode!=null && !"".equals(txCode)){
		   if("addPotenCustVisitInfo".equals(txCode)){
			   Element body = data.getBodyNode(); // ��ȡ�ڵ�
			   String custId= body.element("custNo").getTextTrim();//�ͻ����
			   String industType = body.element("interviewNewRecord").element("cusOwnbusi").getTextTrim();//������ҵ
			   if(custId!=null && !"".equals(custId)){
				   if(industType!=null &&!"".equals(industType)){
			          String sql ="update acrm_f_ci_pot_cus_com set indust_type='"+industType+"' where cus_id='"+custId+"'";
			          baseDAO.batchExecuteNativeWithIndexParam(sql,null);
				   }
			   }
		   }
	     }
	     if(txCode!=null && !"".equals(txCode)){
	    	 if("addPotenCust".equals(txCode)){
				   Element body = data.getBodyNode(); // ��ȡ�ڵ�
				   String custId = body.element("postcuscom").element("cusId").getTextTrim();//������ҵ
				   if(custId!=null && !"".equals(custId)){
				          String sql ="update acrm_f_ci_pot_cus_com set FILTER_DATE=sysdate where cus_id='"+custId+"'";
				          baseDAO.batchExecuteNativeWithIndexParam(sql,null);
				   }
			   }
	     }
		
		baseDAO.flush();
	}
	/**
	 * ��ȡ���ݲ����洢����
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 */
	private void saveModel(EcifData data)throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, ParseException{
		
		// ��ȡ���ݲ����洢����
		List<Object> objList = data.getWriteModelObj().getOpModelList();
		for (Object obj : objList) {

		GetObjectByBusinessKey findObj = (GetObjectByBusinessKey) SpringContextUtils.getBean("getObjectByBusinessKey");
		ObjectReturnMessage objMessage = findObj.bizGetObject(obj, false);
		Object oldObj = objMessage.getObject();
		if (oldObj != null) {
			obj = this.cover(obj, oldObj);
			}
		log.info("����ʵ����({})���󣬴洢�����ݿ�", obj.getClass().getName());
		baseDAO.save(obj);
		}
		baseDAO.flush();
	}
	
	/**
	 * @param newObj �������ݣ�ȡ��������
	 * @param oldObj ��ʷ���ݣ�ȡ�����ݿ�
	 * @return Object�� ���ݲ�������������ԭ����и��ǣ�
	 *         1������ֵ������ֵ(��ֵ����)
	 *         2������ֵ������ʷ����(ʱ������)
	 *         3������ϵͳ���⣬����ϵͳ�串��(ϵͳ����ԭ��һ������ETL)
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 */
	private Object cover(Object newObj, Object oldObj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, ParseException {
		if (oldObj == null) {
			log.info("��ʷ����Ϊ�գ����������ǣ����ظ��ݱ������ɵ���������");
			return newObj;
		}
		if (newObj == null) {
			// ԭ���ϲ������������������˴����뻹���ȱ��������ɾ��
		}

		if (newObj != null && oldObj != null && (!newObj.getClass().getName().equals(oldObj.getClass().getName()))) {
			String err = String.format("�������Ͳ�ƥ�䣺{} �� {}�������Ͳ�ͬ", newObj.getClass().getName(), oldObj.getClass().getName());
			log.error("{}", err);
			this.ecifData.setSuccess(false);
			this.ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_ERROR.getCode(), err);

		}

		String clazzName = newObj.getClass().getName();// ������������
		Object clazz = Class.forName(clazzName).newInstance();

		Field[] field_s = newObj.getClass().getDeclaredFields();
		for (Field field : field_s) {
			// ����������������ȡ��ƴ��
			String fieldName = field.getName();
			Class fieldType = field.getType();

			// ԭ������������setter�������Զ����ɣ���������������������������Զ�Ӧsetter�����������ϴ˹淶�������쳣��
			String methodGet = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			String methodSet = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
			Object newVal = MethodUtils.invokeMethod(newObj, methodGet, null);
			Object oldVal = MethodUtils.invokeMethod(oldObj, methodGet, null);
			Object fieldValue = null;
			if (fieldType.equals(String.class) || fieldType.equals(Date.class) || fieldType.equals(Timestamp.class) || fieldType.equals(java.util.Date.class)|| fieldType.equals(java.sql.Timestamp.class)) {
				if ((newVal == null || "".equals(newVal)) && (oldVal == null || "".equals(oldVal))) {
					continue;
				} else if (newVal == null || "".equals(newVal)) {
					fieldValue = oldVal;
				} else if("com.ytec.mdm.domain.biz.OcrmFInterviewRecord".equals(clazzName) && "createTime".equals(fieldName) && oldVal!=null){
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
					fieldValue = df.parse(oldVal.toString());
				}else {
					fieldValue = newVal.toString();
				}
			} else if (fieldType.equals(Long.class) || fieldType.equals(long.class) || fieldType.equals(BigDecimal.class) || fieldType.equals(BigInteger.class)) {
				if ((newVal == null || "".equals(newVal)) && (oldVal == null || "".equals(oldVal))) {
					continue;
				} else if (newVal == null || "".equals(newVal)) {
					fieldValue = oldVal;
				} else {
					fieldValue = newVal;
				}
			}
			
//			if(fieldType.equals(Date.class) ||fieldType.equals(java.util.Date.class) ){
//				fieldValue = ((Date)fieldValue);
//			}
			
			if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
				// LONG ����
				MethodUtils.invokeMethod(clazz, methodSet, Long.parseLong(fieldValue.toString()));
			} else if (fieldType.equals(String.class)) {
				// �ַ�����
				MethodUtils.invokeMethod(clazz, methodSet, fieldValue);
			} else if (fieldType.equals(BigInteger.class)) {
				// BigInteger ����
				MethodUtils.invokeMethod(clazz, methodSet, BigInteger.valueOf(Long.parseLong(fieldValue.toString())));
			} else if (fieldType.equals(BigDecimal.class)) {
				// BigDecimal ����
				// ��new BigDecimal������ֵ������ת���������������������
				MethodUtils.invokeMethod(clazz, methodSet, new BigDecimal(fieldValue.toString()));
			} else if (fieldType.equals(java.util.Date.class) || fieldType.equals(java.sql.Date.class)) {
				// ��������
//				MethodUtils.invokeMethod(clazz, methodSet, MdmConstants.DATE_FORMAT_10.format( new java.util.Date(fieldValue.toString())));
				MethodUtils.invokeMethod(clazz, methodSet, new java.util.Date(fieldValue.toString()));
			} else if (fieldType.equals(Timestamp.class)) {
				// ʱ������(ʱ���)
				MethodUtils.invokeMethod(clazz, methodSet, Timestamp.valueOf(fieldValue.toString()));
			} else {
				// ��������
				MethodUtils.invokeMethod(clazz, methodSet, fieldValue);
			}

		}

		return clazz;
	}
	
	/**
	 * ����taskNumber�ж��ƶ��Ŵ��������ͣ�1.���� 2.ά��
	 * @param taskNumber
	 * @return
	 */
	public String getCustomerSource(String taskNumber){
		String sqlA = "select * from ocrm_f_interview_record r where r.task_number = '"+taskNumber+"'";
		String sqlB = "select * from ocrm_f_interview_source_record s where s.task_number = '"+taskNumber+"'";
		String sourceRecord = "";
		int recordA = baseDAO.batchExecuteNativeWithIndexParam(sqlA, null);
		int recordB = baseDAO.batchExecuteNativeWithIndexParam(sqlB, null);
		if(recordA < 1 && recordB < 1){//����
			sourceRecord = "1";
		}else if(!(recordA < 1) && recordB < 1){//ά��
			sourceRecord = "2";
		}
		return sourceRecord;
	}
}
