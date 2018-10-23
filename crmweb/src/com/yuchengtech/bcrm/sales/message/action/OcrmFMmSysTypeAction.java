package com.yuchengtech.bcrm.sales.message.action;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.sales.message.model.OcrmFMmSysType;
import com.yuchengtech.bcrm.sales.message.service.OcrmFMmSysTypeService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 
 * 渠道营销模板管理
 * @author luyy
 * @since 2014-2-25
 */

@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/ocrmFMmSysType", results = { @Result(name = "success", type = "json")})
public class OcrmFMmSysTypeAction extends CommonAction {
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
   
	 @Autowired
	    private  OcrmFMmSysTypeService  ocrmFMmSysTypeService;
	 
	 AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	 @Autowired
		public void init(){
		  	model = new OcrmFMmSysType(); 
			setCommonService(ocrmFMmSysTypeService);
			//新增修改删除记录是否记录日志,默认为false，不记录日志
			needLog=true;
		}

	/**
	 *信息查询SQL
	 */
	public void prepare() {
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 //String catlCode = request.getParameter("catlCode");
    	 String catlCode = null;
    	 if(this.json.get("catlCode")!=null){
    		 catlCode=this.json.get("catlCode").toString();
    	 }
		StringBuilder sb = new StringBuilder(" select * from OCRM_F_MM_SYS_TYPE where 1>0");
		for(String key:this.getJson().keySet()){
			if(null!=this.getJson().get(key)&& !this.getJson().get(key).equals("")){
				if(key.equals("MODEL_NAME") || key.equals("MODEL_TYPE")){
					sb.append("AND " +key +" LIKE '%"+this.getJson().get(key)+"%'");
				}else if(key.equals("CATL_NAME")){
					sb.append("AND CATL_CODE"  +" LIKE '%"+this.getJson().get(key)+"%'");
				}
			}
		}
		if(!"".equals(catlCode)&&catlCode!=null){
			sb.append(" and catl_code='"+catlCode+"'");
		}
		addOracleLookup("MODEL_TYPE", "MODEL_TYPE");
		addOracleLookup("MODEL_STATE", "MODEL_STATE");
		SQL=sb.toString();
		datasource = ds;
		configCondition("MODEL_TYPE", "=", "MODEL_TYPE",DataType.String);
		configCondition("IS_ENABLE", "=", "IS_ENABLE",DataType.String);
		configCondition("MODEL_INFO", "like", "MODEL_INFO",DataType.String);
		configCondition("CREAT_USER", "like", "CREAT_USER",DataType.String);
		configCondition("UPDATA_USER", "like", "UPDATA_USER",DataType.String);
		configCondition("UPDATA_DATE", "=", "UPDATA_DATE",DataType.Date);
		
	}
	
	/**
	 * 数据保存
	 */
	public DefaultHttpHeaders saveData(){
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 if(((OcrmFMmSysType)model).getId() == null){
    		((OcrmFMmSysType)model).setCreatUser(auth.getUserId());
 			((OcrmFMmSysType)model).setUpdataUser(auth.getUserId());
 			((OcrmFMmSysType)model).setUpdataUserName(auth.getUsername());
 			((OcrmFMmSysType)model).setCreatUserName(auth.getUsername());
 			((OcrmFMmSysType)model).setCreatDate(new Date());
 			((OcrmFMmSysType)model).setUpdataDate(new Date());
 			
 			ocrmFMmSysTypeService.save(model);
    	 } else if(((OcrmFMmSysType)model).getId()!=null){
    		String id = ((OcrmFMmSysType)model).getId().toString();
 			String jql = "update  OcrmFMmSysType p set p.modelName=:modelName,p.modelInfo=:modelInfo,p.modelType=:modelType,p.isEnable=:isEnable,p.updataUserName=:updataUserName,p.updataUser=:updataUser,p.updataDate=:updataDate where p.id='"+id+"'";
 	        Map<String,Object> values = new HashMap<String,Object>();
 	        values.put("modelName",((OcrmFMmSysType)model).getModelName());
 	        values.put("modelInfo",((OcrmFMmSysType)model).getModelInfo());
 	        values.put("modelType",((OcrmFMmSysType)model).getModelType());
 	        values.put("isEnable",((OcrmFMmSysType)model).getIsEnable());
 	        values.put("updataUserName",auth.getUsername());
 	        values.put("updataUser", auth.getUserId());
 	        values.put("updataDate", new Date());
 	        ocrmFMmSysTypeService.batchUpdateByName(jql, values);
    	 }
		String oprate = request.getParameter("oprate");
		/**
		if("add".equals(oprate)){
			((OcrmFMmSysType)model).setCreatUser(auth.getUserId());
 			((OcrmFMmSysType)model).setUpdataUser(auth.getUserId());
 			((OcrmFMmSysType)model).setCreatDate(new Date());
 			((OcrmFMmSysType)model).setUpdataDate(new Date());
 			
 			ocrmFMmSysTypeService.save(model);
		}else if("update".equals(oprate)){
			String id = ((OcrmFMmSysType)model).getId().toString();
 			String jql = "update  OcrmFMmSysType p set p.modelName=:modelName,p.modelInfo=:modelInfo where p.id='"+id+"'";
 	        Map<String,Object> values = new HashMap<String,Object>();
 	        values.put("modelName",((OcrmFMmSysType)model).getModelName());
 	        values.put("modelInfo",((OcrmFMmSysType)model).getModelInfo());
 	        
 	        ocrmFMmSysTypeService.batchUpdateByName(jql, values);
		}*/
		return new DefaultHttpHeaders("success");
	}
	
	//删除
    public DefaultHttpHeaders batchDel(){
    	ActionContext ctx = ActionContext.getContext();
    	request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	//ocrmFMmSysTypeService.batchDel(request);
    	String custIds=request.getParameter("ids");
    	String jql="DELETE FROM OcrmFMmSysType C WHERE C.id IN ("+custIds+")";
		Map<String,Object> values=new HashMap<String,Object>();
    	ocrmFMmSysTypeService.batchUpdateByName(jql, values);
    	
	return new DefaultHttpHeaders("success");
    }
    
}


