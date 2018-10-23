package com.yuchengtech.bcrm.customer.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.OcrmSysLookupItemtemp;
import com.yuchengtech.bcrm.customer.service.LatentSourceChannelTempService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.model.LookupMappingItem;
/**
 * 来源渠道临时存储
 * mamusa
 * 206-02-04
 * @author Administrator
 *
 */
@Action("/latentSourceChannelTempAction")
public class LatentSourceChannelTempAction extends CommonAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private LatentSourceChannelTempService latentSourceChannelTempService;
	
	@Autowired
	public void init() {
		model = new OcrmSysLookupItemtemp();
		setCommonService(latentSourceChannelTempService);
	}
	   public void saveDateCommit(){
	    	try {
				ActionContext ctx = ActionContext.getContext();
				request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
				HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
				String codedate = request.getParameter("code");
				String codeorg = request.getParameter("codeF");
				int largestNumber = Integer.parseInt(request.getParameter("largestNumber")==null?"":request.getParameter("largestNumber"));
				OcrmSysLookupItemtemp lmitemp=(OcrmSysLookupItemtemp)model;
				lmitemp.setCode(""+codedate.replaceAll("-", "")+codeorg+largestNumber);
				String temps=lmitemp.getValue();
				lmitemp.setValue(lmitemp.getCode()+"-"+temps);
				OcrmSysLookupItemtemp lmi=(OcrmSysLookupItemtemp) latentSourceChannelTempService.save(lmitemp);
				 Date date = new Date();
				 SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
				 String p = sdf.format(date);
				 String instanceid = "SOURCE"+"_"+lmi.getId()+"_"+p;//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
				 String jobName = "个金潜在客户来源渠道复核_"+lmi.getValue();//自定义流程名称
				 latentSourceChannelTempService.initWorkflowByWfidAndInstanceid("134", jobName, null, instanceid);//调用CommonService中的该方法发起工作流，第三个参数可以自定义一些变量，用于路由器条件等
				 String nextNode = "134_a4";
				 response.getWriter().write("{\"instanceid\":\""+instanceid+"\",\"currNode\":\"134_a3\",\"nextNode\":\""+nextNode+"\"}");
				 response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
}
