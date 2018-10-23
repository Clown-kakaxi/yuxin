package com.yuchengtech.bcrm.common.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecc.echain.workflow.engine.EVO;
import com.ecc.echain.workflow.exception.WFException;
import com.ecc.echain.workflow.model.CommentVO;
import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.service.EchainCommonService;
import com.yuchengtech.bcrm.customer.belong.model.OcrmFCiTransApply;
import com.yuchengtech.bcrm.customer.belong.service.OcrmFCiTransApplyService;
import com.yuchengtech.bob.common.CommonAction;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * echain公共action
 * 
 * @author km 20130411
 * 
 */
@SuppressWarnings("serial")
@Action("/EchainCommon")
public class EchainCommonAction extends CommonAction {
	@Autowired
	private EchainCommonService service;

	@Autowired
	private OcrmFCiTransApplyService aservice;
	
	@Autowired
	public void init() {
		model = new EVO();
		setCommonService(service);

	}

	/**
	 * 将前台接收的参数 paramMap1放入model的paramMap中; 接收参数名称:paramMap1
	 */
	public void setParamMap() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String paramMapString = request.getParameter("paramMap1");
		JSONObject jsonObject = JSONObject.fromObject(paramMapString);
		((EVO) model).paramMap.putAll(jsonObject);// 流程变量
	}

	/**
	 * 将前台接收的流程意见参数放入到model的commentVO中; 接收参数名称:commentType 必选参数. 会签:2,其他:1;
	 * commentSign;commentContent;commentReaders;
	 */
	public void setCommentVO() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String commentType = request.getParameter("commentType"); // 流程意见类别
		String commentSign = request.getParameter("commentSign"); // 流程意见标识
		String commentContent = request.getParameter("commentContent"); // 流程意见内容
		String commentReaders = request.getParameter("commentReaders"); // 流程意见阅读者
		if (StringUtils.isNotBlank(commentType)
				|| StringUtils.isNotBlank(commentSign)
				|| StringUtils.isNotBlank(commentContent)
				|| StringUtils.isNotBlank(commentReaders)) {// 是否有流程意见

			CommentVO cvo = new CommentVO();
			cvo.setCommentType(commentType);
			cvo.setCommentSign(commentSign);
			cvo.setCommentContent(commentContent);
			cvo.setCommentReaders(commentReaders);
			((EVO) model).setCommentVO(cvo);
		}

	}

	/**
	 * 取得用户全部待办列表(第一个节点的用户看不到待办任务,因为流程处在拟稿状态)
	 * 
	 * @param limit
	 * @param start
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param formid
	 *            (可选),
	 * @param WFSign
	 *            (可选),
	 * @param NodeStatus节点状态
	 *            (可选) 0：正常办理 1：催办 2：办理结束 为空表示所有状态 ,
	 * @param fromRow
	 *            (可选), 分页起始记录位置
	 * @param toRow
	 *            (可选), 分页结束记录位置
	 * @param bDraft
	 *            (可选)
	 * @param NodeID
	 *            (可选，若有的话只取当前节点为NodeID的工作列表)
	 * @param paramMap1
	 *            (可选，存放查询条件，如:name=ljy;key=2)
	 * @throws SQLException
	 */
	public void getUserAllTodoWorkList() throws SQLException {
		setParamMap();
		list = service.getUserAllTodoWorkList((EVO) model);
	}

	/**
	 * 完成当前节点办理，提交工作任务
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @param nodeID节点ID
	 *            (可选，可以是5或者12_5这样的格式)
	 * @param nodeStatus
	 *            (可选，节点状态；0：正常办理1：催办2：办理结束3：待签收4：拿回5：退回6：挂起）
	 * @param jobname
	 *            (可选）
	 * @param nextNodeID
	 *            (可选,可能存在多值，用"@"分割，可以是5或者12_5这样的格式)
	 * @param nextNodeUser
	 *            (可选,可能存在多值，用"@"分割，每个单值内部的多人用";"隔开)形如 U.admin
	 * @param nodePlanEndTime
	 *            (可选，下一节点办结时限，必须是yyyy-MM-dd
	 *            HH:mm:ss格式，或者D6（6日），d6（6工作日），H6（6小时），h6（6工作小时）这样的格式)
	 * @param depid
	 *            (可选,系统自动选择时过滤非本部门的人员)
	 * @param formFlow
	 *            (可选）
	 * @param commentType 必选参数. 会签:2,其他:1;
	 * @param commentSign;
	 * @param commentContent;
	 * @param commentReaders:意见查看权限（可选,"1"放开查看意见权限,其他的为加密）
	 * @param isNextNodeChange
	 *            (可选,跨组织流转，这里可以设置下一环节组织号,如evo.setIsNextNodeChange("xmccb");）
	 * @param paramMap1
	 *            (可选，存放表单数据)
	 * @param userObject
	 *            (可选，Vector类型，存放应用需要执行的sql语句) 完成当前节点办理，提交工作任务
	 * @throws SQLException
	 */
	public void wfCompleteJob() throws SQLException {
		setParamMap(); // 将前台接收的参数 paramMap1放入model的paramMap中
		setCommentVO(); // 将前台接收的流程意见参数放入到model的commentVO中
		System.out.println("提交的流程节点信息:");
		System.out.println(model.toString());
		model = service.wfCompleteJob((EVO) model);
		json = new HashMap<String, Object>();
		json.put("tip", ((EVO)model).getTip());
		json.put("nextNodeName", ((EVO)model).getNextNodeName()==null?"-":((EVO)model).getNextNodeName());
		json.put("nextUserName", ((EVO)model).getNextNodeUser()==null?"-":((EVO)model).getNextNodeUser());
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
	}

	/**
	 * 保存当前实例
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            ,jobname(可选）,formFlow(可选）,appSign(可选),paramMap1(可选，存放表单数据)
	 * @param userObject
	 *            (可选，Vector类型，存放应用需要执行的sql语句)
	 * @param commentVO
	 *            (可选,意见内容VO,包括:commentType;commentSign;commentContent;commentReaders
	 *            ;）
	 * @throws SQLException
	 */
	public void wfSaveJob() throws SQLException {
		setParamMap(); // 将前台接收的参数 paramMap1放入model的paramMap中
		setCommentVO(); // 将前台接收的流程意见参数放入到model的commentVO中
		System.out.println("保存的流程节点信息:");
		System.out.println(model.toString());
		model = service.wfSaveJob((EVO) model);
		json = new HashMap<String, Object>();
		json.put("tip", ((EVO) model).getTip());
	}

	/**
	 * 获取工作流版本信息
	 */
	public void getWorkFlowVersion() {
		String version = service.getWorkFlowVersion();
		json = new HashMap<String, Object>();
		json.put("version", version);
	}

	/**
	 * 获取可用流程列表
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param AppID
	 *            应用模块PK（可选）
	 * @param WFAdmin
	 *            （可选）
	 * @param FormID
	 *            （可选）
	 * @param WFSign
	 *            （可选）
	 * @throws SQLException
	 * @throws WFException 
	 */
	public void getWFNameList() throws SQLException, WFException {
		list = service.getWFNameList((EVO) model);
	}

	/**
	 * 根据流程id获取所有的流程实例
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param wfid
	 *            流程id
	 * @param WFStatus
	 *            流程状态（可选）
	 * @throws SQLException
	 * @throws WFException 
	 */
	public void getInstanceList() throws SQLException, WFException {
		list = service.getInstanceList((EVO) model);
	}

	/**
	 * 启动流程
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param WFID
	 *            工作流全局属性ID（允许为空,根据流程标识启动，或者先建表单后选流程）
	 * @param WFSign
	 *            流程标识，可选
	 * @param AppID
	 *            先建表单后选流程、自由流程时必须填入
	 * @param WFJobName
	 *            工作任务名称（允许为空）
	 * @throws Exception
	 */
	public void initializeWFWholeDocUNID() throws Exception {
		model = service.initializeWFWholeDocUNID((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
	}

	/**
	 * 删除流程实例
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @throws Exception
	 */
	public void wfDelInstance() throws Exception {
		model = service.wfDelInstance((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
	}

	/**
	 * 重办流程实例
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @throws Exception
	 */
	public void wfTakeBack() throws Exception {
		model = service.wfTakeBack((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
		json = new HashMap<String, Object>();
		json.putAll(((EVO) model).paramMap);// HashMap,哈希表里面对应人员id（格式为U.XXX）、名称;
		// 0：成功；1：失败；2：成功但有异常；3：部分成功；4：未知;
		json.put("tip", ((EVO) model).getTip()); // Tip：结果提示信息;
	}
	
	/**
	 * 打回
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @throws Exception
	 */
	public void wfCallBack() throws Exception {
		model = service.wfCallBack((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
	}

	/**
	 * 撤办流程实例
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @throws Exception
	 */
	public void wfCancel() throws Exception {
		model = service.wfCancel((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
		json = new HashMap<String, Object>();
		json.putAll(((EVO) model).paramMap);// HashMap,哈希表里面对应人员id（格式为U.XXX）、名称;
		// 0：成功；1：失败；2：成功但有异常；3：部分成功；4：未知;
		json.put("tip", ((EVO) model).getTip()); // Tip：结果提示信息;
	}

	/**
	 * 退回
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @throws Exception
	 */
	public void wfReturnBack() throws Exception {
		model = service.wfReturnBack((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
	}

	/**
	 * 手工催办
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @throws Exception
	 */
	public void wfUrge() throws Exception {
		model = service.wfUrge((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
	}

	/**
	 * 流程挂起
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @throws Exception
	 */
	public void wfHang() throws Exception {
		model = service.wfHang((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
	}

	/**
	 * 流程唤醒
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @throws Exception
	 */
	public void wfWake() throws Exception {
		model = service.wfWake((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
		json = new HashMap<String, Object>();
		json.putAll(((EVO) model).paramMap);// HashMap,哈希表里面对应人员id（格式为U.XXX）、名称;
		// 0：成功；1：失败；2：成功但有异常；3：部分成功；4：未知;
		json.put("tip", ((EVO) model).getTip()); // Tip：结果提示信息;
	}

	/**
	 * 设置流程审批状态
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @param SPStatus
	 *            审批状态
	 * @throws Exception
	 */
	public void setSPStatus() throws Exception {
		model = service.setSPStatus((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
	}

	/**
	 * 获取可用流程列表
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @param NodeID节点ID
	 * @throws Exception
	 */
	public void getNextNodeList() throws Exception {
		model = service.getNextNodeList((EVO) model);
		String nodeRouterType = ((EVO) model).getNodeRouterType();
		Iterator it = ((EVO) model).paramMap.keySet().iterator();
		json = new HashMap<String, Object>();
		List tempList = new ArrayList();
		List nodeList = new ArrayList();
		while(it.hasNext()){
			tempList.add(it.next());
		}
		Collections.sort(tempList);//对节点进行排序
		for(int i=0,len=tempList.size();i<len;i++){
			nodeList.add(((EVO) model).paramMap.get(tempList.get(i)));
		}
		json.put("nextNodes", nodeList);
		json.put("nodeRouterType", nodeRouterType);//“0”一般处理；“1”单选处理；“2”多选处理；3.条件单选处理;4.条件多选处理
//		json.putAll(((EVO) model).paramMap);// 哈希表里面对应节点id、节点Map,Map里面放. nodeid
		// nodename
		// nodetype routename ifselectuser：1是，其他否. 节点类型nodetype： S：“起始”
		// E：“结束” A：“活动” C：“控制”
	}

	/**
	 * 获取节点办理用户列表
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @param NodeID节点ID
	 * @param depid
	 *            (可选,过滤非本部门的人员)
	 * @throws Exception
	 */
	public void getNodeUserList() throws Exception {
		String instenceId = ((EVO) model).getInstanceID();
		String nodeId = ((EVO) model).getNodeID();
		String biz = instenceId.split("_")[0];//业务类型
		String id = "-1";
		//处理流程定义时未加“-”
		if(instenceId.split("_").length > 1 ) {
			id = instenceId.split("_")[1];//主键
		}
		
		json = new HashMap<String, Object>();
		List tempList = new ArrayList();
		//在流程实例号中获取业务类型，然后根据业务类型判断是否需要自己获取办理人员
		if("YJ".equals(biz)&&("28_a4".equals(nodeId)||"28_a9".equals(nodeId)||"28_a17".equals(nodeId)||"28_a23".equals(nodeId))){//客户移交流程   对公区域内移交/对公跨区域移交   获取接收支行行长作为办理人
			OcrmFCiTransApply apply = (OcrmFCiTransApply) aservice.find(Long.parseLong(id));
			String tOrgId = apply.getTOrgId();
			List<Object[]> list = aservice.getBaseDAO().findByNativeSQLWithIndexParam(" select a.account_name ,a.user_name " +
					"from admin_auth_account a  where a.org_id = '"+tOrgId+"' and a.id in  " +
					"(select account_id from ADMIN_AUTH_ACCOUNT_ROLE where role_id in ('126','201','202','311'))");
			if(list!=null&&list.size()>0){
				for(Object[] o:list){
					Map tempMap = new HashMap();
					tempMap.put("userId", "U."+o[0]);
					tempMap.put("userName", o[1]);
					tempList.add(tempMap);
				}
			}
		}else if("YJ".equals(biz)&&("28_a9".equals(nodeId))){//客户移交流程   获取原营业单位行长/副行长
//			OcrmFCiTransApply apply = (OcrmFCiTransApply) aservice.find(Long.parseLong(id));
//			String userId = apply.getUserId();
//			List<Object[]> list = aservice.getBaseDAO().findByNativeSQLWithIndexParam(" select a.account_name ,a.user_name " +
//					"from admin_auth_account a  where a.org_id in (select org_id from admin_auth_account a where a.account_name ='"+userId+"') and a.id in  " +
//					"(select account_id from ADMIN_AUTH_ACCOUNT_ROLE where role_id in ('126','201','202','311'))");
//			if(list!=null&&list.size()>0){
//				for(Object[] o:list){
//					Map tempMap = new HashMap();
//					tempMap.put("userId", "U."+o[0]);
//					tempMap.put("userName", o[1]);
//					tempList.add(tempMap);
//				}
//			}
		}else{//流程自行获取下一节点办理人员
			model = service.getNodeUserList((EVO) model);
			Iterator it = ((EVO) model).paramMap.keySet().iterator();
			while(it.hasNext()){
				String key = it.next().toString();
				Map tempMap = new HashMap();
				//去掉角色
				if(key.startsWith("R.")){
					continue;
				}
				tempMap.put("userId", key);
				tempMap.put("userName", ((EVO) model).paramMap.get(key));
				tempList.add(tempMap);
			}
		}
		json.put("nodeUsers", tempList);
		json.put("multeitFlag", ((EVO) model).getMulteitFlag());// multeitFlag:节点单人或是多人标志1：“单人办理”n：“多人办理”;
	}

	/**
	 * 删除所有流程实例
	 */
	public void ClearAllWFInstance() {
		boolean result = service.ClearAllWFInstance();
		json = new HashMap<String, Object>();
		json.put("success", result);
	}

	/**
	 * 删除流程以及相关实例
	 */
	public void DelWF() {
		boolean result = service.DelWF(((EVO) model).getWFID()); // 删除相应流程id的流程及实例
		json = new HashMap<String, Object>();
		json.put("success", result);
	}

	/**
	 * 删除所有流程以及实例
	 */
	public void ClearAllWF() {
		boolean result = service.ClearAllWF();
		json = new HashMap<String, Object>();
		json.put("success", result);
	}

	/**
	 * 填写意见提交 接收参数名称:
	 * @param instanceID
	 *            实例号;
	 *            nodeID 节点号;
	 *            commentType 必选参数. 会签:2,其他:1;
	 * commentSign;commentContent;commentReaders;
	 * 
	 * @throws Exception
	 */
	public void setComment() throws Exception {
		setCommentVO(); // 将前台接收的流程意见参数放入到model的commentVO中
		boolean result = service.setComment((EVO) model);
		json = new HashMap<String, Object>();
		json.put("success", result);
	}

	/**
	 * 获取节点用户输入的意见
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            ,实例号
	 * @param nodeID
	 *            ,节点号
	 * @param commentType
	 *            意见类型 必选参数. 会签:2,其他:1;
	 * @throws Exception
	 */
	public void getUserComment() throws Exception {
		setCommentVO(); // 将前台接收的流程意见参数放入到model的commentVO中
		model = service.getUserComment((EVO) model);
		model = ((EVO) model).getCommentVO(); // 返回CommentVO对象
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
	}

	/**
	 * 获取当前实例所有的意见列表
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            实例号
	 * @throws Exception
	 */
	public void getAllComments() throws Exception {
		((EVO) model).setCommentVO(new CommentVO());// 新建一个commentVo对象,在service中插入session信息
		list = service.getAllComments((EVO) model);
	}

	/**
	 * 获取打回节点列表
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            实例号
	 *        nodeID 节点号
	 * @throws Exception
	 */
	public void getWFTreatedNodeList() throws Exception {
		list = service.getWFTreatedNodeList((EVO) model);
	}

	/**
	 * 流程跳转
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号 InstanceID
	 * @param NodeID
	 * @param NextNodeID
	 * @param NextNodeUser
	 *            (可选)
	 * @param AppSign
	 *            ：（可选，应用数据标识）
	 * @param nodePlanEndTime
	 *            (可选，下一节点办结时限，必须是yyyy-MM-dd
	 *            HH:mm:ss格式，或者D6（6日），d6（6工作日），H6（6小时），h6（6工作小时）这样的格式)
	 * @param depid
	 *            (可选,系统自动选择时过滤非本部门的人员)
	 * @param commentVO中
	 *            ; 接收参数名称:commentType 必选参数. 会签:2,其他:1;
	 *            commentSign;commentContent;commentReaders;
	 * @param NextNextNodeID
	 *            、NextNextNodeName、NextNextNodeUser(可选，下下节点指定）
	 * 
	 * @throws Exception
	 */
	public void wfJump() throws Exception {
		setCommentVO(); // 将前台接收的流程意见参数放入到model的commentVO中
		model = service.wfJump((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
	}

	/**
	 * 获取流程节点列表
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号 InstanceID
	 * @param wfID
	 *            流程id (可选)
	 * 
	 * @throws Exception
	 */
	public void getWFNodeList() throws Exception {
		setCommentVO(); // 将前台接收的流程意见参数放入到model的commentVO中
		json = new HashMap<String, Object>();
		json.putAll(service.getWFNodeList((EVO) model));
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
	}

	/**
	 * 获取文本列表流程跟踪信息
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            实例号
	 * @throws Exception
	 */
	public void getWorkFlowHistory() throws Exception {
		list = service.getWorkFlowHistory((EVO) model);
	}

	/**
	 * 转办人员列表
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @param NodeID节点ID
	 * @throws Exception
	 */
	public void wfChange() throws Exception {
		model = service.wfChange((EVO) model);
		json = new HashMap<String, Object>();
		json.putAll(((EVO) model).paramMap);// HashMap,哈希表里面对应人员id（格式为U.XXX）、名称;
		json.put("multeitFlag", ((EVO) model).getMulteitFlag());// multeitFlag:节点单人或是多人标志1：“单人办理”n：“多人办理”;
		json.put("sign", ((EVO) model).getSign()); // Sign：处理结果标识
		// 0：成功；1：失败；2：成功但有异常；3：部分成功；4：未知;
		json.put("tip", ((EVO) model).getTip()); // Tip：结果提示信息;
	}

	/**
	 * 获取可调用子流列表
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            实例号
	 * @param nodeID
	 *            节点号
	 * @throws Exception
	 */
	public void getSubFlow() throws Exception {
		list = service.getSubFlow((EVO) model);
	}

	/**
	 * 子流设置完毕
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @param nodeID
	 *            节点ID
	 * @param wfID
	 *            流程ID
	 * @throws Exception
	 */
	public void SubFlowSetSubmit() throws Exception {
		model = service.SubFlowSetSubmit((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
	}

	/**
	 * 取得实例相关属性
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号 InstanceID
	 * @param nodeID
	 *            节点id (可选)
	 * 
	 * @throws Exception
	 */
	public void getInstanceInfo() throws Exception {
		json = new HashMap<String, Object>();
		model = service.getInstanceInfo((EVO) model);
		json.putAll(((EVO) model).paramMap); // 哈希表里面放 "getNextNodeList",EVO
		json.put("nodeId", ((EVO) model).getNodeID());
		// "getNodeControlFormField",HashMap
		// "getNodeControlFormAction",HashMap "getNodeFormData",HashMap
		// "getAllComments",Vector
	}

	/**
	 * 取得用户全部待办列表(第一个节点的用户看不到待办任务,因为流程处在拟稿状态)
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param formid
	 *            (可选),
	 * @param WFSign
	 *            (可选),
	 * @param NodeStatus节点状态
	 *            (可选) 0：正常办理 1：催办 2：办理结束 为空表示所有状态 ,
	 * @param fromRow
	 *            (可选), 分页起始记录位置
	 * @param toRow
	 *            (可选), 分页结束记录位置
	 * @param bDraft
	 *            (可选)
	 * @param NodeID
	 *            (可选，若有的话只取当前节点为NodeID的工作列表)
	 * @param paramMap1
	 *            (可选，存放查询条件，如:name=ljy;key=2)
	 * @throws SQLException
	 */
	public void getUserTodoWorkList() throws SQLException {
		setParamMap();
		list = service.getUserTodoWorkList((EVO) model);
	}

	/**
	 * 获取用户已办工作列表
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param formid
	 *            (可选),
	 * @param WFSign
	 *            (可选),
	 * @param NodeStatus节点状态
	 *            (可选) 0：正常办理 1：催办 2：办理结束 为空表示所有状态 ,
	 * @param fromRow
	 *            (可选), 分页起始记录位置
	 * @param toRow
	 *            (可选), 分页结束记录位置
	 * @param bDraft
	 *            (可选)
	 * @param NodeID
	 *            (可选，若有的话只取当前节点为NodeID的工作列表)
	 * @param paramMap1
	 *            (可选，存放查询条件，如:name=ljy;key=2)
	 * @throws SQLException
	 */
	public void getUserDoneWorkList() throws SQLException {
		setParamMap();
		list = service.getUserDoneWorkList((EVO) model);
	}

	/**
	 * 取得用户全部已办工作列表
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param formid
	 *            (可选),
	 * @param WFSign
	 *            (可选),
	 * @param NodeStatus节点状态
	 *            (可选) 0：正常办理 1：催办 2：办理结束 为空表示所有状态 ,
	 * @param fromRow
	 *            (可选), 分页起始记录位置
	 * @param toRow
	 *            (可选), 分页结束记录位置
	 * @param bDraft
	 *            (可选)
	 * @param NodeID
	 *            (可选，若有的话只取当前节点为NodeID的工作列表)
	 * @param paramMap1
	 *            (可选，存放查询条件，如:name=ljy;key=2)
	 * @throws SQLException
	 */
	public void getUserAllDoneWorkList() throws SQLException {
		setParamMap();
		list = service.getUserAllDoneWorkList((EVO) model);
	}
	

	/**
	 * 取得用户有权限的文件列表（按模块）
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param formid
	 *            (可选),
	 * @param WFSign
	 *            (可选),
	 * @param NodeStatus节点状态
	 *            (可选) 0：正常办理 1：催办 2：办理结束 为空表示所有状态 ,
	 * @param fromRow
	 *            (可选), 分页起始记录位置
	 * @param toRow
	 *            (可选), 分页结束记录位置
	 * @param bDraft
	 *            (可选)
	 * @param NodeID
	 *            (可选，若有的话只取当前节点为NodeID的工作列表)
	 * @param paramMap1
	 *            (可选，存放查询条件，如:name=ljy;key=2)
	 * @throws SQLException
	 */
	public void getUserAvailableWorkList() throws SQLException {
		setParamMap();
		list = service.getUserAvailableWorkList((EVO) model);
	}

	/**
	 * 取得用户所有有权限的文件列表
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param formid
	 *            (可选),
	 * @param WFSign
	 *            (可选),
	 * @param NodeStatus节点状态
	 *            (可选) 0：正常办理 1：催办 2：办理结束 为空表示所有状态 ,
	 * @param fromRow
	 *            (可选), 分页起始记录位置
	 * @param toRow
	 *            (可选), 分页结束记录位置
	 * @param bDraft
	 *            (可选)
	 * @param NodeID
	 *            (可选，若有的话只取当前节点为NodeID的工作列表)
	 * @param paramMap1
	 *            (可选，存放查询条件，如:name=ljy;key=2)
	 * @throws SQLException
	 */
	public void getUserAllAvailableWorkList() throws SQLException {
		setParamMap();
		list = service.getUserAllAvailableWorkList((EVO) model);
	}

	/**
	 * 取得用户全部抄送列表
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param formid
	 *            (可选),
	 * @param WFSign
	 *            (可选),
	 * @param NodeStatus节点状态
	 *            (可选) 0：正常办理 1：催办 2：办理结束 为空表示所有状态 ,
	 * @param fromRow
	 *            (可选), 分页起始记录位置
	 * @param toRow
	 *            (可选), 分页结束记录位置
	 * @param bDraft
	 *            (可选)
	 * @param NodeID
	 *            (可选，若有的话只取当前节点为NodeID的工作列表)
	 * @param paramMap1
	 *            (可选，存放查询条件，如:name=ljy;key=2)
	 * @throws SQLException
	 */
	public void getUserAllAnnounceWorkList() throws SQLException {
		setParamMap();
		list = service.getUserAllAnnounceWorkList((EVO) model);
	}

	/**
	 * 取得异常状态文档列表
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param formid
	 *            (可选),
	 * @param WFSign
	 *            (可选),
	 * @param NodeStatus节点状态
	 *            (可选) 0：正常办理 1：催办 2：办理结束 为空表示所有状态 ,
	 * @param fromRow
	 *            (可选), 分页起始记录位置
	 * @param toRow
	 *            (可选), 分页结束记录位置
	 * @param bDraft
	 *            (可选)
	 * @param NodeID
	 *            (可选，若有的话只取当前节点为NodeID的工作列表)
	 * @param paramMap1
	 *            (可选，存放查询条件，如:name=ljy;key=2)
	 * @throws SQLException
	 */
	public void getExceptionWorkList() throws SQLException {
		setParamMap();
		list = service.getExceptionWorkList((EVO) model);
	}

	/**
	 * 取得已办结文件视图
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param formid
	 *            (可选),
	 * @param WFSign
	 *            (可选),
	 * @param NodeStatus节点状态
	 *            (可选) 0：正常办理 1：催办 2：办理结束 为空表示所有状态 ,
	 * @param fromRow
	 *            (可选), 分页起始记录位置
	 * @param toRow
	 *            (可选), 分页结束记录位置
	 * @param bDraft
	 *            (可选)
	 * @param NodeID
	 *            (可选，若有的话只取当前节点为NodeID的工作列表)
	 * @param paramMap1
	 *            (可选，存放查询条件，如:name=ljy;key=2)
	 * @throws SQLException
	 */
	public void getWFStatusEndWorkList() throws SQLException {
		setParamMap();
		list = service.getWFStatusEndWorkList((EVO) model);
	}

	/**
	 * 取得用户工作列表
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param formid
	 *            (可选),
	 * @param WFSign
	 *            (可选),
	 * @param NodeStatus节点状态
	 *            (可选) 0：正常办理 1：催办 2：办理结束 为空表示所有状态 ,
	 * @param fromRow
	 *            (可选), 分页起始记录位置
	 * @param toRow
	 *            (可选), 分页结束记录位置
	 * @param bDraft
	 *            (可选)
	 * @param NodeID
	 *            (可选，若有的话只取当前节点为NodeID的工作列表)
	 * @param paramMap1
	 *            (可选，存放查询条件，如:name=ljy;key=2)
	 * @throws SQLException
	 */
	public void getUserWorkList() throws SQLException {
		setParamMap();
		list = service.getUserWorkList((EVO) model);
	}

	/**
	 * 个人待办任务统计
	 * @throws SQLException 
	 */
	public void StatUserTodo() throws SQLException {
		json = service.StatUserTodo((EVO) model);
	}
	
	/**
	 * 个人已办任务统计
	 * @throws SQLException 
	 */
	public void StatUserDone() throws SQLException {
		list = service.StatUserDone((EVO) model);
	}

	/**
	 * 过期任务统计
	 * @throws SQLException 
	 */
	public void StatOverdue() throws SQLException {
		json = service.StatOverdue((EVO) model);
	}

	/**
	 * 实例签收
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @throws Exception
	 */
	public void instanceSignIn() throws Exception {
		model = service.instanceSignIn((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
		json = new HashMap<String, Object>();
		json.putAll(((EVO) model).paramMap);// HashMap,哈希表里面对应人员id（格式为U.XXX）、名称;
		// 0：成功；1：失败；2：成功但有异常；3：部分成功；4：未知;
		json.put("tip", ((EVO) model).getTip()); // Tip：结果提示信息;
	}
	/**
	 * 撤销签收
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @throws Exception
	 */
	public void instanceSignOff() throws Exception {
		model = service.instanceSignOff((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
		json = new HashMap<String, Object>();
		json.putAll(((EVO) model).paramMap);// HashMap,哈希表里面对应人员id（格式为U.XXX）、名称;
		// 0：成功；1：失败；2：成功但有异常；3：部分成功；4：未知;
		json.put("tip", ((EVO) model).getTip()); // Tip：结果提示信息;
	}	
	
	/**
	 * 撤销任务认领
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @throws Exception
	 */
	public void taskSignOff() throws Exception {
		model = service.taskSignOff((EVO) model);
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
		json = new HashMap<String, Object>();
		json.putAll(((EVO) model).paramMap);// HashMap,哈希表里面对应人员id（格式为U.XXX）、名称;
		// 0：成功；1：失败；2：成功但有异常；3：部分成功；4：未知;
		json.put("tip", ((EVO) model).getTip()); // Tip：结果提示信息;
	}	
}
