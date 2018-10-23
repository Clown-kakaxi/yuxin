package com.yuchengtech.bcrm.notice.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.notice.model.OcrmSysMarqueeNotice;
import com.yuchengtech.bcrm.notice.service.OcrmSysMarqueeNoticeService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;

/***
 * @author liuyx
 * 跑马灯信息管理--控制层
 * @date: 2017年10月18日 下午5:03:54 
 */
@ParentPackage("json-default")
@SuppressWarnings("serial")
@Action("/ocrmSysMarqueeNoticeAction")
public class OcrmSysMarqueeNoticeAction extends CommonAction {
	@Autowired
	private OcrmSysMarqueeNoticeService ocrmSysNoticeService;
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Autowired
	public void init(){
		model = new OcrmSysMarqueeNotice();
		setCommonService(ocrmSysNoticeService);
	}
	/**
	 *跑马灯信息查询
	 */
    public void prepare() {
    	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	StringBuilder sb = new StringBuilder();
    	sb.append("select a.id,a.text,to_char(a.valid_dt,'yyyy-MM-dd hh24:mi') valid_dt");
    	sb.append(" ,to_char(a.valid_dt,'yyyy-MM-dd') valid_dt_date ");
    	sb.append(" ,to_char(a.valid_dt,'hh24:mi') valid_dt_time ");
    	sb.append(" ,to_char(a.create_dt,'yyyy-MM-dd hh24:mi:ss') create_dt,b.user_name create_user  ");
    	sb.append(" from OCRM_SYS_MARQUEE_NOTICE a ");
    	sb.append(" left outer join admin_auth_account b on a.create_user=b.account_name ");
    	sb.append(" where 1=1 ");
		SQL=sb.toString();
		datasource = ds;
		for (String key : this.getJson().keySet()) {
	        if (null != this.getJson().get(key) && !this.getJson().get(key).equals("")) {
	            if (key.equals("TEXT")){
	            	configCondition("a.text", "like", "TEXT",DataType.String);
            	}else if (key.equals("VALID_DT")){
            		configCondition("to_char(a.valid_dt,'yyyy-MM-dd hh24:mi')", "like", "VALID_DT",DataType.String);
            	}
            }
		}
		setPrimaryKey("a.create_dt desc");
	}
    
    /**
	 *查询在有效日期之内的跑马灯信息
	 */
    @SuppressWarnings("unchecked")
    public void findValidNotice() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("select a from OcrmSysMarqueeNotice a ");
    	sb.append(" where a.validDt >=:currentDate");
    	sb.append(" order by a.createDt desc ");
    	
    	Map<String, Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("currentDate", new Date());
    	List<OcrmSysMarqueeNotice> list = ocrmSysNoticeService.findByJql(sb.toString(), paramMap);
    	if(this.json==null){
			this.json = new HashMap<String,Object>();
		}
		this.json.put("data", list);
	}
	
    /**
     * 批量删除跑马灯信息
     * @return
     */
	public String batchDestory(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String idStr = request.getParameter("idStr");
		ocrmSysNoticeService.batchRemove(idStr);
		return "success";
	}
}
