package com.yuchengtech.bcrm.workplat.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.workplat.service.ReportMangerService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 *  工作报告 查询 action
 *  * @author luyy
 * @since 2014-06-25
 */
@SuppressWarnings("serial")
@ParentPackage("json-default")
@Action(value = "/reportManger", results = { @Result(name = "success", type = "json")})
public class ReportMangerAction extends CommonAction {
	
	//数据源
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	private  ReportMangerService service;
	
	private Configuration configuration = null;
	
	AuthUser auth= (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
	
	/**
	 *信息查询SQL
	 * @throws IOException 
	 */
	public void prepare(){
		 ActionContext ctx = ActionContext.getContext();
    	 request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
    	 String type = request.getParameter("type");
    	 StringBuilder sb1 = new StringBuilder("" +
    	 		"select m.WORK_REPORT_ID as REPORT_ID,m.WORK_REPORT_TYPE,m.WORK_REPORT_BUSI_TYPE,m.REPORTER_ID," +
    	 		"m.REPORTER_NAME,m.REPORTER_ORG,m.REPORTER_ORG_NAME,m.REPORTER_CYCLE,m.REPORT_DATE,m.REPORT_STAT,p.* " +
    	 		"from OCRM_F_WP_WORK_REPORT m,OCRM_F_WP_WORK_REPORT_PER p where m.work_report_id = p.work_report_id " );
    	 StringBuilder sb2 = new StringBuilder("union all " +
    	 		"select m.WORK_REPORT_ID as REPORT_ID,m.WORK_REPORT_TYPE,m.WORK_REPORT_BUSI_TYPE,m.REPORTER_ID,m.REPORTER_NAME," +
    	 		"m.REPORTER_ORG,m.REPORTER_ORG_NAME,m.REPORTER_CYCLE,m.REPORT_DATE,m.REPORT_STAT,c.* " +
    	 		"from OCRM_F_WP_WORK_REPORT m,OCRM_F_WP_WORK_REPORT_COM c where m.work_report_id = c.work_report_id ");
    	 
    	 if("day".equals(type)){
    		 sb1.append(" and m.WORK_REPORT_TYPE = '01'");
    		 sb2.append(" and m.WORK_REPORT_TYPE = '01'");
    	 }
    	 if("week".equals(type)){
    		 sb1.append(" and m.WORK_REPORT_TYPE = '02'");
    		 sb2.append(" and m.WORK_REPORT_TYPE = '02'");
    	 }
    	 if("month".equals(type)){
    		 sb1.append(" and m.WORK_REPORT_TYPE = '03'");
    		 sb2.append(" and m.WORK_REPORT_TYPE = '03'");
    	 }
     	
    	 
    	 for(String key : this.getJson().keySet()){
 			if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){	
 				if (key.equals("REPORT_DATE")){
 					sb1.append("   AND m.REPORT_DATE =to_char("+" '"+this.getJson().get(key).toString().substring(0, 10)+"','YYYY-MM-dd')");
 					sb2.append("   AND m.REPORT_DATE =to_char("+" '"+this.getJson().get(key).toString().substring(0, 10)+"','YYYY-MM-dd')");
 				}else{
 					sb1.append("  AND m."+key+" like"+" '%"+this.getJson().get(key)+"%'");
 					sb2.append("  AND m."+key+" like"+" '%"+this.getJson().get(key)+"%'");
 				}
 			}
 		}
		
		SQL = "select * from (" + sb1.toString()+sb2.toString() + " ) t where 1=1 ";
		datasource = ds;
	}
	
	public String queryReport(){
		try {
   			ActionContext ctx = ActionContext.getContext();
   			request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
   			String id = request.getParameter("id");
   			String type = "day";//request.getParameter("type");
   			StringBuilder sb = new StringBuilder("select m.WORK_REPORT_ID as REPORT_ID,m.WORK_REPORT_TYPE,m.WORK_REPORT_BUSI_TYPE,m.REPORTER_ID,");
   			sb.append(" m.REPORTER_NAME,m.REPORTER_ORG,m.REPORTER_ORG_NAME,m.REPORTER_CYCLE,m.REPORT_DATE,m.REPORT_STAT,p.* " );
   			sb.append(" from OCRM_F_WP_WORK_REPORT m,OCRM_F_WP_WORK_REPORT_PER p where m.work_report_id = p.work_report_id " );
   			if("day".equals(type)){
	       		 sb.append(" and m.WORK_REPORT_TYPE = '01'");
	       	 }
   			sb.append(" and  m.work_report_id = '"+ id +"'");
   			
   			QueryHelper queryHelper = new QueryHelper(sb.toString(), ds.getConnection());
   			if(this.json!=null){
           		this.json.clear();
   			}else {
           		this.json = new HashMap<String,Object>(); 
           	}
   			this.json.put("json",queryHelper.getJSON());
   		} catch (Exception e) {
   			e.printStackTrace();
   			throw new BizException(1,2,"1002",e.getMessage());
   		}
		return "success";
	}
	
	public String commitApprReport(){
		try {
   			ActionContext ctx = ActionContext.getContext();
   			request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
   			String id = request.getParameter("id");
   			String instanceid = service.commitApprReport(id);
   			Map<String,Object> map=new HashMap<String,Object>();
   			map.put("instanceid", instanceid);
   			this.setJson(map);
		} catch (Exception e) {
   			e.printStackTrace();
   			throw new BizException(1,2,"1002",e.getMessage());
   		}
		return "success";
	}
	
	//制作word报告
	public void getWordReport() throws IOException{
		ActionContext ctx = ActionContext.getContext();
   	    request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
   	    
   	    String id = request.getParameter("ID");
   	    String report = request.getParameter("reportType");
   	    String name = request.getParameter("name");
   	    String cycle = request.getParameter("cycle");
   	    
   	    configuration = new Configuration();  
		configuration.setDefaultEncoding("UTF-8");  
		configuration.setClassForTemplateLoading(this.getClass(), "template");  //FTL文件所存在的位置
		Template t=null; 
		Writer out = null;  
		
   	 
		Map<String,Object> dataMap=new HashMap<String,Object>();  
		dataMap.put("name", name);  
		dataMap.put("cycle", cycle);  
		
		List<Object[]> list = null;
		if("dayReportP".equals(report)||"weekReportP".equals(report)||"monthReportP".equals(report)){
			list = service.getBaseDAO().findByNativeSQLWithIndexParam(" select p.ID,p.WORK_REPORT_ID,p.REPORT_SUB1,p.REPORT_SUB2,p.REPORT_SUB3," +
					"p.REPORT_SUB4,p.REPORT_SUB5,p.REPORT_SUB6,p.REPORT_SUB7,p.REPORT_SUB8,p.REPORT_SUB9,p.REPORT_SUB10,p.REPORT_SUB11,p.REPORT_SUB12,p.REPORT_SUB13,p.REPORT_SUB14,p.REPORT_SUB15,p.REPORT_SUB16,p.REPORT_SUB17,r.remark1,r.remark2 " +
					"from OCRM_F_WP_WORK_REPORT_PER p left join OCRM_F_WP_WORK_REPORT r on r.work_report_id = p.work_report_id where p.id='"+id+"'");
			if(list != null && list.size()>0){
				Object[] o = list.get(0);
				for(int i=2;i<19;i++){
					dataMap.put("REPORT_SUB"+(i-1), o[i]!=null?o[i].toString():"");  
				}
				dataMap.put("REMARK1", o[19]!=null?o[19].toString():"");
				dataMap.put("REMARK2", o[20]!=null?o[20].toString():"");
			}else{
				for(int i=2;i<20;i++){
					dataMap.put("REPORT_SUB"+(i-1),"0");  
				}
				dataMap.put("REMARK1","0");
				dataMap.put("REMARK2", "0");
				
			}
		}else{
			list = service.getBaseDAO().findByNativeSQLWithIndexParam(" select c.ID,c.WORK_REPORT_ID,c.REPORT_SUB1,c.REPORT_SUB2,c.REPORT_SUB3," +
					"c.REPORT_SUB4,c.REPORT_SUB5,c.REPORT_SUB6,c.REPORT_SUB7,c.REPORT_SUB8,c.REPORT_SUB9,c.REPORT_SUB10,c.REPORT_SUB11,c.REPORT_SUB12,c.REPORT_SUB13," +
					"c.REPORT_SUB14,c.REPORT_SUB15,c.REPORT_SUB16,c.REPORT_SUB17,c.REPORT_SUB18,r.remark1,r.remark2 " +
					"from OCRM_F_WP_WORK_REPORT_COM c left join OCRM_F_WP_WORK_REPORT r on r.work_report_id = c.work_report_id where c.id='"+id+"'");
			if(list != null && list.size()>0){
				
				Object[] o = list.get(0);
				for(int i=2;i<20;i++){
					dataMap.put("REPORT_SUB"+(i-1), o[i]!=null?o[i].toString():"");  
				}
				dataMap.put("REMARK1", o[20]!=null?o[20].toString():"");
				dataMap.put("REMARK2", o[21]!=null?o[21].toString():"");
			}else{
				for(int i=2;i<20;i++){
					dataMap.put("REPORT_SUB"+(i-1),"0");  
				}
				dataMap.put("REMARK1","0");
				dataMap.put("REMARK2", "0");
				
			}
		}
		
		try {  
			t = configuration.getTemplate(report+".ftl"); //文件名  
			String path = FileTypeConstance.getImportTempaltePath();//文件路径
//			String path=FileTypeConstance.getSystemProperty("sysExport");
			//创建文件
			File outFile = new File(path+File.separator+report+id+".doc");//文件名
		    out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
		    t.process(dataMap, out);  //填写模板中的标签处
		} catch (Exception e) {  
			e.printStackTrace();  
		}finally{
			out.flush();
			out.close();
		}

	}
}


