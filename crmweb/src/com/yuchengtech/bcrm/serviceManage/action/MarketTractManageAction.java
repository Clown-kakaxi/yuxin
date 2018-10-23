package com.yuchengtech.bcrm.serviceManage.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.serviceManage.model.MarketTrackRecord;
import com.yuchengtech.bcrm.serviceManage.service.MarketTractManageService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * 跟踪记录管理Action
 * 
 * @author yuyz
 * @since 2012-12-17
 */

@SuppressWarnings("serial")
@Action("/MarketTractManage")
public class MarketTractManageAction extends CommonAction {

	@Autowired
	private MarketTractManageService service;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性

	@Autowired
	public void init() {
		model = new MarketTrackRecord();
		setCommonService(service);
		needLog = true;// 新增修改删除记录是否记录日志,默认为false，不记录日志
	}

	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		//this.setJson(request.getParameter("condition"));
		String mktId=request.getParameter("mkt_Id");
		StringBuilder sb = new StringBuilder(
				"select s.*,a.user_name AS EXECUTOR_NAME,b.user_name as updateuser_name from OCRM_F_MKT_TRACK_RECORD s " +
				"left join admin_auth_account a on s.create_user = a.account_name " +
				"left join admin_auth_account b on s.update_user = b.account_name where 1=1");
		sb.append(" and s.MKT_ID ='"+mktId+"'");
//		for (String key : this.getJson().keySet()) {// 查询条件判断
//			if (null != this.getJson().get(key)
//					&& !this.getJson().get(key).equals("")) {
////				if (key.equals("MKT_ID")) {
////					sb.append(" AND s." + key + " = '"
////							+ this.getJson().get(key) + "'");
//				//} 
//				if (key.equals("recordId")) { // 获得id，用于查看、修改
//					sb.append(" AND s.RECORD_ID =" + this.getJson().get(key));
//				}
//			}
//		}

		SQL = sb.toString();
		this.setPrimaryKey("s.UPDATE_DATE DESC,s.RECORD_ID DESC");
		addOracleLookup("CANTACT_CHANNEL", "SERVICE_CHANNEL");
		datasource = ds;
	}

	/**
	 * （自定义）批量删除
	 * 
	 * @return 成功标识
	 */
	public String batchDestroy() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String idStr = request.getParameter("idStr");
		String jql = "delete from MarketTrackRecord s WHERE s.recordId IN (" + idStr
				+ ")";
		Map<String, Object> values = new HashMap<String, Object>();
		service.batchUpdateByName(jql, values);
		addActionMessage("batch removed successfully");

		return "success";
	}

	/**
	 * 保存
	 * 
	 * @return 成功标识
	 */
	public DefaultHttpHeaders create() {
		String message = "";// 抛出异常信息
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String currenUserId = auth.getUserId();
		try {
			MarketTrackRecord marketTrackRecord = (MarketTrackRecord) model;
			if (marketTrackRecord.getRecordId() == null) { // 新增
				marketTrackRecord.setCreateUser(currenUserId);
				marketTrackRecord.setCreateDate(new Date());
			} else {
				marketTrackRecord.setUpdateUser(currenUserId);
			}
			marketTrackRecord.setUpdateDate(new Date());
			marketTrackRecord.setUpdateUser(currenUserId);

			service.save(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(0, 0, "1002", message);
		}

		return new DefaultHttpHeaders("success");
	}
}
