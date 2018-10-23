package com.yuchengtech.bob.common;


import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.ecc.echain.workflow.engine.EVO;
import com.ecc.echain.workflow.engine.WfEngine;
import com.ecc.echain.workflow.engine.WorkFlowClient;

@Transactional(value="postgreTransactionManager")
public class CommonService{
	

	/**
	 * 通过wfid发起流程,实例号用传参的方式获取,创建echain工作流实例
	 * 
	 * @param wfId
	 *            流程标识id
	 * @param jobName
	 *            任务名称
	 * @param paramMap
	 *            存放流程变量
	 * @param instanceId
	 *            实例ID
	 * @return EVO
	 * @throws Exception
	 */
	public EVO initWorkflowByWfidAndInstanceid(String wfId, String jobName,
			Map paramMap, String instanceId) throws Exception {
		WfEngine we = WfEngine.getInstance();
		EVO vo = new EVO();
		try {
			vo.setWFID(wfId);
//			vo.setCurrentUserID(auth.getUserId());
//			vo.setOrgid(auth.getUnitId());
			vo.setCurrentUserID("503N1150");
			vo.setOrgid("503");
			if ((instanceId != null) && (instanceId.length() > 0))
				vo.setInstanceID(instanceId);
			// vo.setBizseqno(bizseqno);
			if ((jobName != null) && (jobName.length() > 0))
				vo.setJobName(jobName);
			if (paramMap != null)
				vo.paramMap.putAll(paramMap);
			vo = we.initializeWFWholeDocUNID(vo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vo;
	}


	/**
	 * 流程提交
	 * 
	 * @param instanceId
	 *            ：流程实例号
	 * @param nodeId
	 *            节点ID（可选）
	 * @param nextNodeId
	 *            ：下一节点ID（可选）
	 * @param nextNodeUser
	 *            ：下一节点办理人（可选）形如 : U.admin
	 * @param paramMap
	 *            ：流程参数（可选,_emp_context,SuggestContent,SuggestControl）
	 * @return EVO
	 * @throws Exception
	 */
	public EVO wfCompleteJob(String instanceId, String nodeId,
			String nextNodeId, String nextNodeUser, Map paramMap)
			throws Exception {

		WorkFlowClient wfc = WorkFlowClient.getInstance();
		EVO vo = new EVO();
		vo.setInstanceID(instanceId);
		if ((nodeId != null) && (nodeId.length() > 0))
			vo.setNodeID(nodeId);
//		vo.setCurrentUserID(auth.getUserId());
//		vo.setOrgid(auth.getUnitId());
		if ((nextNodeId != null) && (nextNodeId.length() > 0))
			vo.setNextNodeID(nextNodeId);
		if ((nextNodeUser != null) && (nextNodeUser.length() > 0))
			vo.setNextNodeUser(nextNodeUser);
		if (paramMap != null)
			vo.paramMap.putAll(paramMap);
		if ((paramMap != null) && (!(paramMap.isEmpty()))
				&& (paramMap.get("AnnounceUser") != null)) {
			vo.setAnnounceUser((String) paramMap.get("AnnounceUser"));
		}
		vo = wfc.wfCompleteJob(vo);
		return vo;
	}
	
	/**
	 * 通过wfid发起流程,实例号利用传参的方式获取,创建echain工作流实例
	 * @param wfId
	 *            流程标识id
	 * @param jobName
	 *            任务名称
	 * @param paramMap
	 *            存放流程变量
	 * @param instanceId
	 *            实例ID
	 * @return EVO
	 * @throws Exception
	 * add by liuming 20170223
	 */
	public EVO initWorkflowByWfidAndInstanceid(String wfId, String jobName,
			Map paramMap, String instanceId,String mgrId) throws Exception {
		WfEngine we = WfEngine.getInstance();
		EVO vo = new EVO();
		try {
			vo.setWFID(wfId);
			vo.setCurrentUserID(mgrId);
			String orgId=mgrId.substring(0, 3);
			vo.setOrgid(orgId);
			if ((instanceId != null) && (instanceId.length() > 0))
				vo.setInstanceID(instanceId);
			if ((jobName != null) && (jobName.length() > 0))
				vo.setJobName(jobName);
			if (paramMap != null)
				vo.paramMap.putAll(paramMap);
			vo = we.initializeWFWholeDocUNID(vo);
			vo.setCurrentUserID(mgrId);
			vo.setOrgid(orgId);
			return vo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
