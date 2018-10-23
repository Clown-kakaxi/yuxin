package com.ecc.echain.wf;

import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.apache.log4j.Logger;

import com.ecc.echain.workflow.engine.EVO;
import com.ecc.echain.workflow.model.CommentVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.bcrm.sales.message.action.MailClient;

/**
 * @describtion: 客户经理报告审批
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年9月10日 上午10:33:12
 */
public class ReportHandle extends EChainCallbackCommon{
	/**
	 * log4g日志输出
	 */
	private static Logger log = Logger.getLogger(ReportHandle.class);
	
	/**
	 * 个金主管审批工作报告
	 * @param vo
	 */
	public void apprReportF(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String id = instanceid.split("_")[1];
			CommentVO cvo = vo.getCommentVO();
			String comment = "";
			if(cvo != null){
				comment = cvo.getCommentContent();
			}
			sendMessage(id, vo, comment);//发送邮件
			SQL = "UPDATE OCRM_F_WP_WORK_REPORT T SET T.REMARK1 = '"+comment+"' WHERE WORK_REPORT_ID = '"+id+"'";
			execteSQL(vo);
		} catch (Exception e) {
			log.error("执行SQL报错,个金主管审批工作报告流程");
			e.printStackTrace();
		}
	}
	
	/**
	 * 区主管审批工作报告
	 * @param vo
	 */
	public void apprReportS(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String id = instanceid.split("_")[1];
			CommentVO cvo = vo.getCommentVO();
			String comment = "";
			if(cvo != null){
				comment = cvo.getCommentContent();
			}
			sendMessage(id, vo, comment);//发送邮件
			//更新报告状态 //APPR_STATUS：01未提交，02审批中，03已完成
			SQL = "UPDATE OCRM_F_WP_WORK_REPORT T SET T.REPORT_STAT = '03',T.REMARK2 = '"+comment+"' WHERE WORK_REPORT_ID = '"+id+"'";
			execteSQL(vo);
		} catch (Exception e) {
			log.error("执行SQL报错,区主管审批工作报告流程");
			e.printStackTrace();
		}
	}
	@SuppressWarnings("rawtypes")
	public void sendMessage(String reportId,EVO vo,String mailContent) throws Exception{
		try{
			SQL="SELECT * FROM ADMIN_AUTH_ACCOUNT WHERE ACCOUNT_NAME =(select reporter_id from OCRM_F_WP_WORK_REPORT where work_report_id='"+reportId+"')";
			Result result = querySQL(vo) ;
			String email=null;
			for (SortedMap item : result.getRows()){
				email = item.get("email").toString();     
			}
			if(email!=null && !email.trim().equals("")){
				MailClient.getInstance().sendMsg(email, "富邦华一工作报告", mailContent);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.error("邮件发送失败"+e.getMessage());
		}
	}
	
	/**
	 * 撤销办理
	 * @param vo
	 */
	public void endCB(EVO vo){
		try {
			String instanceid = vo.getInstanceID();
			String id = instanceid.split("_")[1];
			//更新报告状态 //APPR_STATUS：01未提交，02审批中，03已完成 04已撤办
			SQL = "UPDATE OCRM_F_WP_WORK_REPORT T SET T.REPORT_STAT = '04' WHERE WORK_REPORT_ID = '"+id+"'";
			execteSQL(vo);
		} catch (Exception e) {
			log.error("执行SQL报错,区主管审批工作报告流程");
			e.printStackTrace();
		}
	}
}
