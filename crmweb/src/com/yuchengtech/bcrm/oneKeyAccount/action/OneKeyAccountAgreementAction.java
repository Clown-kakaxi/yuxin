package com.yuchengtech.bcrm.oneKeyAccount.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.yuchengtech.bcrm.oneKeyAccount.service.OneKeyAccountAgreementService;
import com.yuchengtech.bob.common.CommonAction;
/**
 * 获取一键开户协议word转换后的html
 * @author wx
 *
 */
@Action("/oneKeyAccountCustAgreementAction")
public class OneKeyAccountAgreementAction extends CommonAction{

	/**
	 * 将word转换成html
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private OneKeyAccountAgreementService agreementService;

	/**
	 * 获取一键开户协议word转换后的html
	 * @return
	 */
	public void convertWord2Html(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html:charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			Map<String, Object> agreementWordHtml = agreementService.getAgreementWordHtml();
			String retStr = JSONObject.fromObject(agreementWordHtml).toString();
			writer.println(retStr);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
