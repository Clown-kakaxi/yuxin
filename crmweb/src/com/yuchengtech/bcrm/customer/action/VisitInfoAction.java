package com.yuchengtech.bcrm.customer.action;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPotCusCom;
import com.yuchengtech.bcrm.customer.service.VisitInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.exception.BizException;

@Action("/visitInfo")
public class VisitInfoAction extends CommonAction {
	private static final long serialVersionUID = -2010621122837504304L;
	@Autowired
	private VisitInfoService service;

	// add by liuming 20170722
	private static Logger log = Logger.getLogger(VisitInfoAction.class);
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Autowired
	public void init() {
		model = new AcrmFCiPotCusCom();
		setCommonService(service);
	}
	/**
	 * 新建潜在客户
	 */
	public DefaultHttpHeaders create() throws BizException {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		// 客户信息
		String custZhName = request.getParameter("custName") == null ? "" : request.getParameter("custName").trim();// 客户名称
		String linkUser = request.getParameter("linkmanName") == null ? "" : request.getParameter("linkmanName");// 联系人
		String hy = request.getParameter("hy") == null ? "" : request.getParameter("hy");// 行业
		String ifhy = request.getParameter("ifhy") == null ? "" : request.getParameter("ifhy");// 是否目标行业
		String linkPhone = request.getParameter("callNo") == null ? "" : request.getParameter("callNo");// 手机号码
		// add by liuming 20170722校验客户名称是否存在
		if (custZhName == null || custZhName.equals("")) {
			throw new BizException(1, 0, "1000", "客户名称不能为空!");
		}
		// add by liuyx 20171205 增加归属客户经理的提示
		String custInfoStr = service.isCustExists(custZhName);
		if (custInfoStr == null) {// 客户名称不存在
			String checkStr = service.save(custZhName, linkUser, hy, ifhy, linkPhone);
			if (null == checkStr) {
				return new DefaultHttpHeaders("success").setLocationId(((AcrmFCiPotCusCom) model).getCusId());
			} else {
				throw new BizException(1, 2, "1000", checkStr);
			}
		} else {
			String[] mgrArr = custInfoStr.split(",");
			String mgrId = mgrArr[0];
			String mgrName = mgrArr[1];
			throw new BizException(1, 0, "10001", "客户:【" + custZhName + "】已存在，归属于客户经理：" + mgrId + "【" + mgrName + "】，如有需要，请联系此客户经理！");
		}
	}
}
