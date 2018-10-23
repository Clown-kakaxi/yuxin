package com.yuchengtech.bcrm.common.action;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import com.ecc.echain.workflow.engine.EVO;
import com.ytec.mdm.service.svc.atomic.QueryCustState;
import com.yuchengtech.bcrm.common.service.EchainCommonService;

/**
 * echain公共action
 * 
 * @author km 20130411
 * 
 */
@SuppressWarnings("serial")
public class EchainCommonAction {
	private EchainCommonService service;
	protected Object model;

	public EchainCommonAction() {
		model = new EVO();
		service = new EchainCommonService();
	}

	/**
	 * 将前台接收的参数 paramMap1放入model的paramMap中; 接收参数名称:paramMap1
	 */
	public void setParamMap() {
		String paramMapString = null;
		JSONObject jsonObject = JSONObject.fromObject(paramMapString);
		((EVO) model).paramMap.putAll(jsonObject);// 流程变量
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
		String biz = instenceId.split("_")[0];// 业务类型
		String id = "-1";
		// 处理流程定义时未加“-”
		if (instenceId.split("_").length > 1) {
			id = instenceId.split("_")[1];// 主键
		}
		List tempList = new ArrayList();
		// 流程自行获取下一节点办理人员
		model = service.getNodeUserList((EVO) model);
		Iterator it = ((EVO) model).paramMap.keySet().iterator();
		while (it.hasNext()) {
			String key = it.next().toString();
			Map tempMap = new HashMap();
			// 去掉角色
			if (key.startsWith("R.")) {
				continue;
			}
			tempMap.put("userId", key);
			tempMap.put("userName", ((EVO) model).paramMap.get(key));
			tempList.add(tempMap);
		}
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
	 * add by liuming 20170222
	 */
	public List getNodeUserList(EVO vo) throws Exception {
		System.out.println(vo.getOrgid() + "------>");
		// String instenceId = ((EVO) model).getInstanceID();
		String instenceId = vo.getInstanceID();
		// String nodeId = ((EVO) model).getNodeID();
		String nodeId = vo.getNodeID();
		String biz = instenceId.split("_")[0];// 业务类型
		String id = "-1";
		// 处理流程定义时未加“-”
		if (instenceId.split("_").length > 1) {
			id = instenceId.split("_")[1];// 主键
		}
		List tempList = new ArrayList();
		// 流程自动获取下一节点办理人员
		model = service.getNodeUserList(vo);
		Iterator it = ((EVO) model).paramMap.keySet().iterator();
		// Map tempMap = new HashMap();
		while (it.hasNext()) {
			String key = it.next().toString();
			Map tempMap = new HashMap();
			// 去掉角色
			if (key.startsWith("R.")) {
				continue;
			}
			tempMap.put("userId", key);
			tempMap.put("userName", ((EVO) model).paramMap.get(key));
			tempList.add(tempMap);
		}

		Map voMap = new HashMap();
		voMap.put("nextNodeId", vo.getNodeID());// 下一个节点Id
		tempList.add(voMap);
		System.out.println(vo.getOrgid());
		return tempList;
	}
	
	/**
	 * 获取可以使用的流程列表
	 * 
	 * @param 前台传参
	 *            ,model接收参数名：
	 * @param instanceID
	 *            流程实例号
	 * @param NodeID节点ID
	 * @throws Exception
	 * add by liuming 20170222
	 */
	public List getNextNodeList(EVO vo) throws Exception {
		//流程实例号
		String instenceId=vo.getInstanceID();
		String mgrId=vo.getCurrentUserID();
		model = service.getNextNodeList(vo);
		String nodeRouterType = ((EVO)model).getNodeRouterType();
		Iterator it = ((EVO)model).paramMap.keySet().iterator();
		Map<String, Object> map= new HashMap<String, Object>();
		List tempList = new ArrayList();			//存取下一个办理人节点信息
		List nodeList = new ArrayList();
		while(it.hasNext()){
			tempList.add(it.next());
		}
		//System.out.println(tempList);				//获取下一个节点[95_a8, 95_a7, 95_a4]
		for(int i=0,len=tempList.size();i<len;i++){
			nodeList.add(((EVO) model).paramMap.get(tempList.get(i)));
		}
		map.put("nextNodes", nodeList);
		map.put("nodeRouterType", nodeRouterType);	//0.一般处理；1.单选处理；2.多选处理；3.条件单选处理;4.条件多选处理
													// 节点类型nodetype： S：“起始” E：“结束” A：“活动” C：“控制”
		//查询判断发起人角色，获取下一个办理人节点
		List roleList=queryRoleInfo(mgrId,"1");
		String nextNode="";
		for(Object roles:roleList){
		String role=(String) roles;
			if("R309".equals(role)){//法金Team head 
				nextNode = "95_a7";//支行行长
			}else if("R106".equals(role)) {// 总行法金Team head 
				nextNode = "95_a8";//总行法金业务条线
			}else if("R304".equals(role) || "R305".equals(role)){// 法金ARM,RM
				//RM没有直属上级Team Head，支行行长直接审批
				List teamHeadList=this.queryRoleInfo(mgrId, "2");
				if(teamHeadList != null && teamHeadList.size() > 0){
					String teamHead = (String) teamHeadList.get(0);
					if(mgrId.equals(teamHead)){
						//客户经理的teamHead是他自己
						nextNode = "95_a7";//支行行长
					}else{
						nextNode = "95_a4";//直属上级Team Head
					}
				}else{
					nextNode = "95_a7";//支行行长
				}
//				if(teamHead != null && !teamHead.isEmpty()){
//				nextNode = "95_a4";//直属上级Team Head
//				}
			}else if("R104".equals(role) || "R105".equals(role)){
					//总行法金RM没有直接上级Team Head,法金业务条线主管审批
					List teamHeadList=this.queryRoleInfo(mgrId, "2");
					if(teamHeadList != null && teamHeadList.size() > 0){
						String teamHead = (String) teamHeadList.get(0);
						nextNode = "95_a4";//直属上级Team Head
						if(mgrId.equals(teamHead)){
							//总行RM、ARM的teamHead是他自己
							nextNode = "95_a8";//法金业务条线主管审批
						}else{
							nextNode = "95_a4";//直属上级Team Head
						}
					}else{
						nextNode = "95_a8";//法金业务条线主管审批
					}
			}else{
				nextNode = "95_a4";
			}
		}
		vo.setNodeID(nextNode);
		return this.getNodeUserList(vo);
	}
	
	/**
	 * 获得角色
	 * @param mgrId
	 * @return
	 * add by liuming 20170222
	 */
	public List queryRoleInfo(String mgrId,String type) {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			Properties properties = new Properties();
			try {
				InputStream inputStream = QueryCustState.class
						.getResourceAsStream("/jdbc.properties");
				properties.load(inputStream);
				inputStream.close(); // 关闭流
			} catch (IOException e) {
				e.printStackTrace();
			}
			String url = properties.getProperty("jdbc.url");
			String user = properties.getProperty("jdbc.username");
			String password = properties.getProperty("jdbc.password");
			Connection conn = DriverManager.getConnection(url, user, password);
			try {
				String role="";
				Statement  stmt = null;
				ResultSet rs = null;  
				stmt = conn.createStatement();  
				List<String> roles=new ArrayList<String>();
				String sql="";
				if(type != null && !"".equals(type)){
					if("1".equals(type)){
							sql="select r.role_code from admin_auth_account author"                                                
								+" left join ADMIN_AUTH_ACCOUNT_ROLE ar on author.id=ar.account_id"
								+" left join ADMIN_AUTH_ROLE r on ar.role_id=r.id"
								+" where author.account_name='"+mgrId+"' order by r.role_code desc ";
					}else if("2".equals(type)){
							sql="select t.belong_team_head from Admin_Auth_Account t where t.account_Name = '"+mgrId+"'";
					}
				}
				rs=stmt.executeQuery(sql);
				
				while(rs.next()){
					 role=rs.getString(1);
					 roles.add(role);
				}
				return roles;
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				conn.close();
			}

		} catch (Exception e) {
				e.printStackTrace();
		}
		return null;
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
	 * @param commentType
	 *            必选参数. 会签:2,其他:1;
	 * @param commentSign
	 *            ;
	 * @param commentContent
	 *            ;
	 * @param commentReaders
	 *            :意见查看权限（可选,"1"放开查看意见权限,其他的为加密）
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
		System.out.println("提交的流程节点信息:");
		System.out.println(model.toString());
		model = service.wfCompleteJob((EVO) model);
		Map<String, Object> json = new HashMap<String, Object>();
		json.put("tip", ((EVO) model).getTip());
		json.put("nextNodeName", ((EVO) model).getNextNodeName() == null ? "-"
				: ((EVO) model).getNextNodeName());
		json.put("nextUserName", ((EVO) model).getNextNodeUser() == null ? "-"
				: ((EVO) model).getNextNodeUser());
		// TODO model转成json对象到前台可能会转换异常,必要时可将所需信息put到json对象中返回前台
	}
	
	/**
	 * 完成当前节点办理，提交工作任务
	 * @param userObject
	 *            (可选，Vector类型，存放应用需要执行的sql语句) 完成当前节点办理，提交工作任务
	 * @throws SQLException
	 * add by liuming 20170222
	 */
	public void wfCompleteJob(EVO vo) throws SQLException {
		setParamMap(); // 将前台接收的参数 paramMap1放入model的paramMap中
		System.out.println("提交的流程节点信息:");
		System.out.println(vo.toString());
		//model = service.wfCompleteJob((EVO) model);
		model=service.wfCompleteJob(vo);
		Map<String, Object> json  = new HashMap<String, Object>();
		json.put("tip", ((EVO)model).getTip());
		json.put("nextNodeName", ((EVO)model).getNextNodeName()==null?"-":((EVO)model).getNextNodeName());
		json.put("nextUserName", ((EVO)model).getNextNodeUser()==null?"-":((EVO)model).getNextNodeUser());
	}

}
