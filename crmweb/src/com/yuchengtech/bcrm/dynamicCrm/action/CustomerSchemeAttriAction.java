package com.yuchengtech.bcrm.dynamicCrm.action;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.dynamicCrm.model.CustomerSchemeAttri;
import com.yuchengtech.bcrm.dynamicCrm.service.CustomerSchemeAttriService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;

/**
 * 方案属性action
 * @author 亮
 *
 */

@SuppressWarnings("serial")
@Action("customerSchemeAttri")
public class CustomerSchemeAttriAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private CustomerSchemeAttriService service;

	@Autowired
	public void init(){
		model = new CustomerSchemeAttri();
		setCommonService(service);
	}

	@Override
	public void prepare() {
		StringBuffer sb = new StringBuffer();
		sb.append("select t.SA_ID,t.SCHEME_ID,t.ATTRI_ID,t.INDEX_ID,a.ATTRI_NAME,s.INDEX_NAME ");
		sb.append("from OCRM_F_CI_CUST_SCHEME_ATTRI t ");
		sb.append("left join OCRM_F_CI_CUST_ATTRI_CONF a on a.ATTRI_ID=t.ATTRI_ID ");
		sb.append("left join OCRM_F_CI_CUST_ATTRI_SCORE s on s.INDEX_ID = t.INDEX_ID ");
		sb.append("where 1=1 ");

		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String schemeId = request.getParameter("schemeId");
		if(schemeId != null && !"".equals(schemeId)){
			sb.append("and t.SCHEME_ID='" + schemeId + "'");
		}
		
		sb.append(" order by t.SA_ID");
		SQL = sb.toString();
		datasource = ds;
	}
	
	/**
	 * 获取方案属性指标
	 * @return
	 */
	public String getSchemeAttri(){
		StringBuffer sb = new StringBuffer();
		sb.append("select t.SA_ID,t.SCHEME_ID,t.ATTRI_ID,t.INDEX_ID,a.ATTRI_NAME,s.INDEX_NAME ");
		sb.append("from OCRM_F_CI_CUST_SCHEME_ATTRI t ");
		sb.append("left join OCRM_F_CI_CUST_ATTRI_CONF a on a.ATTRI_ID=t.ATTRI_ID ");
		sb.append("left join OCRM_F_CI_CUST_ATTRI_SCORE s on s.INDEX_ID = t.INDEX_ID ");
		sb.append("where 1=1 ");

		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String schemeId = request.getParameter("schemeId");
		if(schemeId != null && !"".equals(schemeId)){
			sb.append("and t.SCHEME_ID='" + schemeId + "'");
		}
		sb.append(" order by t.SA_ID");
		
		if(this.json!=null)
    		this.json.clear();
    	else 
    		this.json = new HashMap<String,Object>(); 
		try {
			Map<String, Object> result=new QueryHelper(sb.toString(), ds.getConnection()).getJSON();
			this.json.put("json",result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	
	/**
	 * 保存方案属性指标配置
	 * @return
	 */
	public String  saveSchemeSet(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("id"); 
		String schemeId = request.getParameter("schemeId");
		String addGrantsStr = request.getParameter("addGrantsStr");
		String delGrantsStr = request.getParameter("delGrantsStr");
		CustomerSchemeAttri csa = new CustomerSchemeAttri();
		
		if(addGrantsStr != ""){
			String addAttrisStr[] = addGrantsStr.split(",");
			for(int i=0;i<addAttrisStr.length;i++){//新增选中节点
				String addAttris[] = addAttrisStr[i].split(" ");
				if(addAttris.length>1){
					String attriId = addAttris[0];
					String indexId = addAttris[1];
					if(id != null && id.equals("")){
						csa.setSaId(null);			
					}else{
						csa.setSaId(id);
					}
					csa.setAttriId(attriId);
					csa.setSchemeId(schemeId);
					csa.setIndexId(indexId);
					service.save(csa);
				}
			}
		}
		if(delGrantsStr != ""){
			String delAttrisStr[] = delGrantsStr.split(",");
			for(int i=0;i<delAttrisStr.length;i++){//删除取消的节点
				String delAttris[] = delAttrisStr[i].split(" ");
				if(delAttris.length>1){
					String attriId = delAttris[0];
					String indexId = delAttris[1];
					String sql = "delete from CustomerSchemeAttri a where a.schemeId = '"+schemeId+"' and a.attriId = '"+attriId+"' and a.indexId = '"+indexId+"' ";
					service.deleteIndex(sql);
				}
			}
		}
		
		return "success";
	}
	
	public void save(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String id = request.getParameter("id");
		String attriId = request.getParameter("attriId");
		String schemeId = request.getParameter("schemeId");
		String weight = request.getParameter("weight");
		CustomerSchemeAttri csa = new CustomerSchemeAttri();
		if(id != null && id.equals("")){
			csa.setSaId(null);			
		}else{
			csa.setSaId(id);
		}
		csa.setAttriId(attriId);
		csa.setSchemeId(schemeId);
		csa.setSchemeAttriWeight(weight);
		service.save(csa);
	}

	public void delete(){
		ActionContext ctx = ActionContext.getContext();
	    request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
	    String id = request.getParameter("id");
		service.delete(id);
	}
}
