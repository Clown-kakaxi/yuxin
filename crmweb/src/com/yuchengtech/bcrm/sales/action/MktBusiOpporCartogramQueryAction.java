package com.yuchengtech.bcrm.sales.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.sales.service.MktBusiOpporCartogramQueryService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * @描述：商机图表统计查询Action
 * @author wzy
 * @date:2013-04-02
 */
@ParentPackage("json-default")
@Action(value = "/mktBusiOpporCartogramQueryAction", results = { @Result(name = "success", type = "json"), })
public class MktBusiOpporCartogramQueryAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private MktBusiOpporCartogramQueryService mktBusiOpporCartogramQueryService;

	// 查询销售漏斗图形化展示的数据
	public void getQueryResultJsonData() {
		String temp = null;
		String result = null;
		ActionContext ctx = null;
		HttpServletResponse response = null;
		result = mktBusiOpporCartogramQueryService.getZTZBJsonData();
		result = (result == null ? "" : result);
		temp = mktBusiOpporCartogramQueryService.getLYZBJsonData();
		temp = (temp == null ? "" : temp);
		result = result + ";" + temp;
		temp = mktBusiOpporCartogramQueryService.getQSZBJsonData();
		temp = (temp == null ? "" : temp);
		result = result + ";" + temp;
		ctx = ActionContext.getContext();
		response = (HttpServletResponse) ctx
				.get(ServletActionContext.HTTP_RESPONSE);
		try {
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(result);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}