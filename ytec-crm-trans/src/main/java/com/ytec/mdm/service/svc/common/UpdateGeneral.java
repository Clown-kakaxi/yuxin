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
		if(txCode!=null && !"".equals(txCode)){//判断拜访任务移动信贷推送信息类型：1.新增 2.维护
			if("addPotenCustVisitInfo".equals(txCode) || "addHistCustVisitInfo".equals(txCode)){//新增新户拜访记录或新增旧户拜访记录
				Element body = data.getBodyNode(); // 获取节点
				String taskNumber = body.element("interviewRecord").element("taskNumber").getTextTrim();//任务编号
				String customerSource = getCustomerSource(taskNumber);
				if(customerSource.equals("1") || customerSource.equals("2")){
					String sql = "insert into ocrm_f_interview_source_record values('"+taskNumber+"','"+customerSource+"')";
					baseDAO.batchExecuteNativeWithIndexParam(sql, null);
				}
			}
		}
		baseDAO.flush();
		
		if(txCode != null && !"".equals(txCode)){
			if("addPotenCustVisitInfo".equals(txCode) || "addHistCustVisitInfo".equals(txCode)){//新增新户拜访记录或新增旧户拜访记录
				Element body=data.getBodyNode();
				CommonService commonService = new CommonService();
				EchainCommonAction commonAction = new EchainCommonAction();
				String flag = body.element("flag") == null ? null:body.element("flag").getTextTrim();//判断移动信贷新增拜访信息提交是否发起流程标志
				String mgrId = body.element("interviewRecord").element("mgrId").getTextTrim();	//发起人
				String orgId = null;
				List mgrList = new ArrayList();
				Map<String,String> map = null;
				EVO vo=new EVO();
				if(flag != null && !"".equals(flag)){
					if("2".equals(flag)){			//移动信贷选择发起流程，则流程正常完成后保存数据
						String instanceId =null;
						try{
							String taskType = body.element("interviewRecord").element("taskType").getTextTrim();	//判断新户或者旧户，1新户、0旧户
							String visitType = body.element("interviewRecord").element("visitType").getTextTrim();//2表示旧户新案
							String taskNumber = body.element("interviewRecord").element("taskNumber").getTextTrim();
							String custName = body.element("interviewRecord").element("custName").getTextTrim();
							String jobName = null;
							//String instanceId =null;
							if(taskType != null && !"".equals(taskType)){
								if("1".equals(taskType)){				//新户
									jobName = "企商金新户拜访复核_"+custName;
									instanceId = "QSJVISIT_"+taskNumber.split(",")[0]+"_1"+"_"+new SimpleDateFormat("HHmmss").format(new java.util.Date());
									//通过wfid发起流程,实例号用传参的方式获取,创建echain工作流实例
									log.info("instanceId>>>:"+instanceId);
									log.info("mgrId>>>:"+mgrId);
									vo = commonService.initWorkflowByWfidAndInstanceid("95", jobName, null, instanceId,mgrId);
									String nodeId = vo.getNodeID();//发起人节点id
									String nextNodeId = null;      //下一个办理人节点id
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
											if(map.get("nextNodeId") == null){//这里是不是可能存在许多个办理人
												mgrInfo.addElement("nextMgrId").setText(map.get("userId") == null ? "" : map.get("userId").substring(2));
												mgrInfo.addElement("nextMgrName").setText(map.get("userName") == null ? "" : map.get("userName"));
											}else{
												nextNodeId=map.get("nextNodeId");
											}
										}
									}
									responseEle.addElement("nextNodeId").setText(nextNodeId == null ? "" : nextNodeId);
									data.setRepNode(responseEle);
								}else if("0".equals(taskType)){//旧户
									if("2".equals(visitType)){ //旧户新案
										jobName = "企商金旧户新案拜访复核_"+custName;
										instanceId = "QSJVISIT_"+taskNumber.split(",")[0]+"_01"+"_"+new SimpleDateFormat("HHmmss").format(new java.util.Date());
										//通过wfid发起流程,实例号用传参的方式获取,创建echain工作流实例
										vo = commonService.initWorkflowByWfidAndInstanceid("95", jobName, null, instanceId,mgrId);
										String nodeId = vo.getNodeID();//发起人节点id
										String nextNodeId = null;      //下一个办理人节点id
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
									}else{//旧户(除旧户新案)
										jobName = "企商金旧户拜访复核_"+custName;
										instanceId = "QSJVISIT_"+taskNumber.split(",")[0]+"_0"+"_"+new SimpleDateFormat("HHmmss").format(new java.util.Date());
										//通过wfid发起流程,实例号用传参的方式获取,创建echain工作流实例
										vo = commonService.initWorkflowByWfidAndInstanceid("95", jobName, null, instanceId,mgrId);
										String nodeId = vo.getNodeID();//发起人节点id
										String nextNodeId = null;      //下一个办理人节点id
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
							//异常处理，修改流程状态
							e.printStackTrace();
							log.info(e.getMessage());
							String sql="update wf_instance_whole_property set WFSTATUS='3' where INSTANCEID='"+instanceId+"'";
							baseDAO.batchExecuteNativeWithIndexParam(sql,null);
							ecifData.setStatus(ErrorCode.ERR_UNKNOWN_ERROR.getCode(), "流程异常中止");
							return;
						}
					}else if("3".equals(flag)){//移动信贷选择办理人返回CRM,CRM完成当前节点办理，提交工作任务，保存数据
						//获得移动信贷提交过来的流程变量信息
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
							ecifData.setStatus(ErrorCode.ERR_OTHER_ERROR.getCode(), "提交工作任务失败");
							return;
						}
							saveModel(ecifData);
							String sql = "update Ocrm_f_Interview_Record t set  t.review_State= '02'  where t.task_number='"+instanceId.split("_")[1]+"'";
							baseDAO.batchExecuteNativeWithIndexParam(sql,null);
						}catch(Exception e){		
							e.printStackTrace();
							if(conn == null){
								log.error("获取数据库连接失败");
							}
							log.error("当前节点办理，提交工作任务失败");
						}finally{
							if(conn !=  null){
								conn.close();
							}
						}
					}else if("4".equals(flag)){
						String instanceId = body.element("instanceId").getTextTrim();
						if(instanceId == null || instanceId.equals("")){
							ecifData.setStatus(ErrorCode.ERR_UNKNOWN_ERROR.getCode(), "流程实例号为空");
						}else{
							//移动信贷撤办，不发起流程，修改流程状态
							String sql="update wf_instance_whole_property set WFSTATUS='3' where INSTANCEID='"+instanceId+"'";
							baseDAO.batchExecuteNativeWithIndexParam(sql,null);
//							ecifData.setStatus(ErrorCode.SUCCESS.getCode(), "撤办处理成功，文档已经被撤销办理！");
							String sql1 = "update Ocrm_f_Interview_Record t set  t.review_State= '1'  where t.task_number='"+instanceId.split("_")[1]+"'";
							baseDAO.batchExecuteNativeWithIndexParam(sql1,null);
						}
						return;
						
					}else if("1".equals(flag)){
						saveModel(ecifData);//正常保存，移动信贷不发起流程
					}
				}else{
					saveModel(ecifData);//安装老版本的app
				}
			}else{
				saveModel(ecifData);
			}
		}
		
//		// 获取数据并做存储操作
//		List<Object> objList = this.ecifData.getWriteModelObj().getOpModelList();
//		for (Object obj : objList) {
//
//			GetObjectByBusinessKey findObj = (GetObjectByBusinessKey) SpringContextUtils.getBean("getObjectByBusinessKey");
//			ObjectReturnMessage objMessage = findObj.bizGetObject(obj, false);
//			Object oldObj = objMessage.getObject();
//			if (oldObj != null) {
//				obj = this.cover(obj, oldObj);
//			}
//			log.info("处理实体类({})对象，存储至数据库", obj.getClass().getName());
//			baseDAO.save(obj);
//		}
//		baseDAO.flush();
		
	     if(txCode!=null && !"".equals(txCode)){
		   if("addPotenCustVisitInfo".equals(txCode)){
			   Element body = data.getBodyNode(); // 获取节点
			   String custId= body.element("custNo").getTextTrim();//客户编号
			   String industType = body.element("interviewNewRecord").element("cusOwnbusi").getTextTrim();//所属行业
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
				   Element body = data.getBodyNode(); // 获取节点
				   String custId = body.element("postcuscom").element("cusId").getTextTrim();//所属行业
				   if(custId!=null && !"".equals(custId)){
				          String sql ="update acrm_f_ci_pot_cus_com set FILTER_DATE=sysdate where cus_id='"+custId+"'";
				          baseDAO.batchExecuteNativeWithIndexParam(sql,null);
				   }
			   }
	     }
		
		baseDAO.flush();
	}
	/**
	 * 获取数据并做存储操作
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws ParseException
	 */
	private void saveModel(EcifData data)throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, ParseException{
		
		// 获取数据并做存储操作
		List<Object> objList = data.getWriteModelObj().getOpModelList();
		for (Object obj : objList) {

		GetObjectByBusinessKey findObj = (GetObjectByBusinessKey) SpringContextUtils.getBean("getObjectByBusinessKey");
		ObjectReturnMessage objMessage = findObj.bizGetObject(obj, false);
		Object oldObj = objMessage.getObject();
		if (oldObj != null) {
			obj = this.cover(obj, oldObj);
			}
		log.info("处理实体类({})对象，存储至数据库", obj.getClass().getName());
		baseDAO.save(obj);
		}
		baseDAO.flush();
	}
	
	/**
	 * @param newObj 最新数据，取自请求报文
	 * @param oldObj 历史数据，取自数据库
	 * @return Object， 根据参数，根据以下原则进行覆盖：
	 *         1、以有值覆盖无值(有值优先)
	 *         2、最新值覆盖历史数据(时间优先)
	 *         3、忽略系统问题，不做系统间覆盖(系统优先原则一般用于ETL)
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws ParseException
	 */
	private Object cover(Object newObj, Object oldObj) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException, ParseException {
		if (oldObj == null) {
			log.info("历史数据为空，无需做覆盖，返回根据报文生成的最新数据");
			return newObj;
		}
		if (newObj == null) {
			// 原则上不会出现这种情况，但此处代码还是先保留，后可删除
		}

		if (newObj != null && oldObj != null && (!newObj.getClass().getName().equals(oldObj.getClass().getName()))) {
			String err = String.format("数据类型不匹配：{} 与 {}数据类型不同", newObj.getClass().getName(), oldObj.getClass().getName());
			log.error("{}", err);
			this.ecifData.setSuccess(false);
			this.ecifData.setStatus(ErrorCode.ERR_ECIF_DATA_ERROR.getCode(), err);

		}

		String clazzName = newObj.getClass().getName();// 含包名的类名
		Object clazz = Class.forName(clazzName).newInstance();

		Field[] field_s = newObj.getClass().getDeclaredFields();
		for (Field field : field_s) {
			// 属性名、方法名获取、拼接
			String fieldName = field.getName();
			Class fieldType = field.getType();

			// 原则上裸体类中setter方法名自动生成，与属性名相关联，如有特殊属性对应setter方法名不符合此规范，则会出异常。
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
				// LONG 类型
				MethodUtils.invokeMethod(clazz, methodSet, Long.parseLong(fieldValue.toString()));
			} else if (fieldType.equals(String.class)) {
				// 字符类型
				MethodUtils.invokeMethod(clazz, methodSet, fieldValue);
			} else if (fieldType.equals(BigInteger.class)) {
				// BigInteger 类型
				MethodUtils.invokeMethod(clazz, methodSet, BigInteger.valueOf(Long.parseLong(fieldValue.toString())));
			} else if (fieldType.equals(BigDecimal.class)) {
				// BigDecimal 类型
				// 用new BigDecimal方法将值做类型转换，其他方法会出点问题
				MethodUtils.invokeMethod(clazz, methodSet, new BigDecimal(fieldValue.toString()));
			} else if (fieldType.equals(java.util.Date.class) || fieldType.equals(java.sql.Date.class)) {
				// 日期类型
//				MethodUtils.invokeMethod(clazz, methodSet, MdmConstants.DATE_FORMAT_10.format( new java.util.Date(fieldValue.toString())));
				MethodUtils.invokeMethod(clazz, methodSet, new java.util.Date(fieldValue.toString()));
			} else if (fieldType.equals(Timestamp.class)) {
				// 时间类型(时间戳)
				MethodUtils.invokeMethod(clazz, methodSet, Timestamp.valueOf(fieldValue.toString()));
			} else {
				// 其他类型
				MethodUtils.invokeMethod(clazz, methodSet, fieldValue);
			}

		}

		return clazz;
	}
	
	/**
	 * 根据taskNumber判断移动信贷推送类型：1.新增 2.维护
	 * @param taskNumber
	 * @return
	 */
	public String getCustomerSource(String taskNumber){
		String sqlA = "select * from ocrm_f_interview_record r where r.task_number = '"+taskNumber+"'";
		String sqlB = "select * from ocrm_f_interview_source_record s where s.task_number = '"+taskNumber+"'";
		String sourceRecord = "";
		int recordA = baseDAO.batchExecuteNativeWithIndexParam(sqlA, null);
		int recordB = baseDAO.batchExecuteNativeWithIndexParam(sqlB, null);
		if(recordA < 1 && recordB < 1){//新增
			sourceRecord = "1";
		}else if(!(recordA < 1) && recordB < 1){//维护
			sourceRecord = "2";
		}
		return sourceRecord;
	}
}
