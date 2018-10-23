package com.yuchengtech.bcrm.custview.action;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiDepandloan;
import com.yuchengtech.bcrm.custview.service.AcrmFCiDepandloanService;
import com.yuchengtech.bob.common.CommonAction;

/**
 * 
 * @author huwei 存贷比信息
 * 
 */
@SuppressWarnings("serial")
@Action("/acrmFCiDepandloan-info")
public class AcrmFCiDepandloanAction extends CommonAction {
	@Autowired
	private AcrmFCiDepandloanService service;

	@Autowired
	public void init() {
		model = new AcrmFCiDepandloan();
		setCommonService(service);
	}

	public HttpHeaders indexPage() throws Exception {
		try {
			StringBuilder sb = new StringBuilder(
					"select c from AcrmFCiDepandloan c where 1=1 ");
			Map<String, Object> values = new HashMap<String, Object>();
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			// if(request.getParameter("start")!=null)
			// start = new Integer(request.getParameter("start")).intValue();
			// if(request.getParameter("limit")!=null)
			// limit = new Integer(request.getParameter("limit")).intValue();
			this.setJson(request.getParameter("condition"));

			// 获取客户号
			if (request.getParameter("custid") != null) {
				sb.append(" and c.custId = :custId" );
				values.put("custId", request.getParameter("custid"));
			}
			return super.indexPageByJql(sb.toString(), values);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
