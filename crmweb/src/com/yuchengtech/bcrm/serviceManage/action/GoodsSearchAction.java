package com.yuchengtech.bcrm.serviceManage.action;



import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 礼品查询Action  放大镜使用
 * 
 * @author luyy
 * @since 2014-07-12
 */

@SuppressWarnings("serial")
@Action("/goodsSearch")
public class GoodsSearchAction extends CommonAction {

	

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;// 定义数据源属性

	@Autowired
	public void init() {
	}
	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		StringBuilder sb = new StringBuilder("");
			sb.append("select g.* from OCRM_F_SE_GOODS g where g.GOODS_STATE='6' and g.goods_number>0 ");
			
			for (String key : this.getJson().keySet()) {// 查询条件判断
				if (null != this.getJson().get(key)&& !this.getJson().get(key).equals("")) {
					if (key.equals("GOODS_NAME")) {
						sb.append(" AND g."+key+" like '%"+this.getJson().get(key)+"%'");
					} 
				}
			}

		SQL = sb.toString();
		datasource = ds;
	}
	
	
 
}
