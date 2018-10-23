package com.yuchengtech.bcrm.action;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecc.echain.workflow.engine.EVO;
import com.ecc.echain.workflow.engine.WorkFlowClient;
import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.model.RestApply;
import com.yuchengtech.bcrm.service.RestApplyService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
@SuppressWarnings("serial")
@Action("/restapply")
public class RestApplyAction extends CommonAction {

	@Autowired
	private RestApplyService ras;

	@Autowired
	public void init() {
		model = new RestApply();
		setCommonService(ras);
	}
	
	/**
	 * 发起工作流
	 * */
	public void initFlow() throws Exception{

		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();        
		String userId = auth.getUserId();
		String orgId = auth.getUnitId();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
		.get(ServletActionContext.HTTP_REQUEST);
		String instanceid = request.getParameter("instanceid");
		String applyname = request.getParameter("applyname");
		EVO vo=new EVO();
    	vo.setCurrentUserID(userId);
    	vo.setOrgid(orgId);
    	vo.setInstanceID(instanceid);//设置当前任务实例号
//    	vo.setWFSign("kevinmxt");  //设置任务标识，对应流程定制中的任务标识
    	vo.setWFID("2");    //设置任务的文件名称（对应任务标识，如存储kevinmxt任务的文件名称为2.xml，就设置为2）
    	vo.setAppName(instanceid+"_"+applyname+"_请假申请");
    	vo.setExv10("mxtTest");              //
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new java.util.Date()).toString();
    	vo.setJobName(applyname+"_请假申请_"+date);  //  设置工作名称，显示在待办列表中
    	vo=WorkFlowClient.getInstance().initializeWFWholeDocUNID(vo);   //发起任务
    	WorkFlowClient.getInstance().wfSaveJob(vo);
	}
	
	public void denyApply(EVO vo){
//		RestApplyService raService = new RestApplyService();
//		setCommonService(raService);
	}
	
//	/**
//	 * 打开任务详情
//	 * @param response 
//	 * @throws Exception 
//	 */
//	public void openForm() throws Exception {
//
//		HttpServletResponse response;
//		ActionContext ctx = ActionContext.getContext();
//		request = (HttpServletRequest) ctx
//		.get(ServletActionContext.HTTP_REQUEST);
//		response = (HttpServletResponse) ctx.get(ServletActionContext.HTTP_RESPONSE);
//		String orgid=request.getParameter("orgid");
//		String userid=request.getParameter("userid");
//
//		WorkFlowClient wfc=WorkFlowClient.getInstance();
//    	String instanceid = request.getParameter("instanceid");
//    	String nodeid = request.getParameter("nodeid");
//    	if(nodeid==null||nodeid.equals("")||nodeid.equals("null"))
//    		nodeid=null;
//    	String tab = request.getParameter("tab");
//    	if(tab==null||tab.equals("")){
//    		EVO vo=new EVO();
//        	vo.setCurrentUserID(userid);
//        	vo.setOrgid(orgid);
//        	vo.setInstanceID(instanceid);
//        	vo.setNodeID(nodeid);
//            vo=wfc.getInstanceInfo(vo);
//            String formid=vo.getFormid();
//            List list=FormFactory.getInstance().getFormClass().getFormWithField(formid);
//            request.setAttribute("instanceid", instanceid);
//            request.setAttribute("nodeid", nodeid);
//            request.setAttribute("preuser", vo.getPreUser());
//            request.setAttribute("formfieldlist", list);
//            HashMap hm=(HashMap)vo.paramMap.get("getNodeFormData");
//            request.setAttribute("getNodeFormData",hm);
//            HashMap hm2=(HashMap)vo.paramMap.get("getNodeControlFormAction");
//            request.setAttribute("getNodeControlFormAction",hm2);
//            HashMap hm3=(HashMap)vo.paramMap.get("getNodeControlFormField");
//            request.setAttribute("getNodeControlFormField",hm3);
//            request.getRequestDispatcher("./echain/flowdemo/form.jsp").forward(request, response);
//            return;
//    	}else if(tab.equals("ywxx")){
//        	EVO vo=new EVO();
//        	vo.setCurrentUserID(userid);
//        	vo.setOrgid(orgid);
//        	vo.setInstanceID(instanceid);
//        	vo.setNodeID(nodeid);
//            vo=wfc.getInstanceInfo(vo);
//            String formid=vo.getFormid();
//            List list=FormFactory.getInstance().getFormClass().getFormWithField(formid);
//            request.setAttribute("instanceid", instanceid);
//            request.setAttribute("nodeid", nodeid);
//            request.setAttribute("formfieldlist", list);
//            HashMap hm=(HashMap)vo.paramMap.get("getNodeFormData");
//            request.setAttribute("getNodeFormData",hm);
////            HashMap hm2=(HashMap)vo.paramMap.get("getNodeControlFormAction");
////            request.setAttribute("getNodeControlFormAction",hm2);
//            HashMap hm3=(HashMap)vo.paramMap.get("getNodeControlFormField");
//            request.setAttribute("getNodeControlFormField",hm3);		            
//    	}else if(tab.equals("lcsp")){
//    		EVO vo=new EVO();
//        	vo.setCurrentUserID(userid);
//        	vo.setOrgid(orgid);
//        	vo.setInstanceID(instanceid);
//        	vo.setNodeID(nodeid);
//        	{
//        		CommentVO cvo=new CommentVO();
//        		cvo.setUserID(userid);
//	        	cvo.setOrgid(orgid);
//	        	cvo.setInstanceID(instanceid);
//	        	cvo.setNodeID(nodeid);
//	        	cvo.setCommentType("0");
//	        	vo.setCommentVO(cvo);
////	        	CommentVO cvotmp=wfc.getUserComment(vo);
////	        	request.setAttribute("suggestContent_wf", cvotmp.getCommentContent()==null?"":cvotmp.getCommentContent());
//	        	cvo.setCommentType("1");
////	        	cvotmp=wfc.getUserComment(vo);
////	        	request.setAttribute("suggestContent_biz", cvotmp.getCommentContent()==null?"":cvotmp.getCommentContent());
//	        	cvo.setCommentType(null);
//	            Vector vectComment=wfc.getAllComments(vo);
//	            request.setAttribute("vectComment", vectComment);
//        	}	
//            request.setAttribute("instanceid", instanceid);
//            request.setAttribute("nodeid", nodeid);
//            vo=wfc.getInstanceInfo(vo);		
//            request.setAttribute("preuser", vo.getPreUser());
//            request.setAttribute("bizSeqNo", vo.getBizseqno());
//            HashMap hm=(HashMap)vo.paramMap.get("getNodeControlFormAction");
//            request.setAttribute("getNodeControlFormAction",hm);
//            
//    	}else if(tab.equals("lcgz")){
//    		EVO vo=new EVO();
//        	vo.setCurrentUserID(userid);
//        	vo.setOrgid(orgid);
//        	vo.setInstanceID(instanceid);
//        	vo.setNodeID(nodeid);
//            Vector vect=wfc.getWorkFlowHistory(vo);
//            request.setAttribute("instanceid", instanceid);
//            request.setAttribute("nodeid", nodeid);
//            request.setAttribute("vect", vect);
//    	
//    	}else if(tab.equals("lcyj")){
//    		EVO vo=new EVO();
//    		CommentVO cvo = new CommentVO();
//    		cvo.setUserID(userid);
//    		cvo.setOrgid(orgid);
//    		cvo.setInstanceID(instanceid);
//    		vo.setCommentVO(cvo);
//            Vector vectComment=wfc.getAllComments(vo);
//            request.setAttribute("vectComment", vectComment);
//    	}
//    	
//    	
//    	request.getRequestDispatcher("./echain/flowdemo/form_"+tab+".jsp").forward(request, response);
//
//    	
//	}
	
	
	

}
