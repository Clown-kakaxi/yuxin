package com.yuchengtech.bcrm.workplat.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.retrieval.IndexFiles;
import com.yuchengtech.bcrm.retrieval.SearchFiles;
import com.yuchengtech.bcrm.workplat.model.OcrmFWpInfo;
import com.yuchengtech.bcrm.workplat.service.WorkingplatformInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.DataType;
import com.yuchengtech.bob.model.WorkingplatformAnnexe;
import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.SystemConstance;
import com.yuchengtech.crm.exception.BizException;
/**
 * 知识库发布处理
 */
@SuppressWarnings("serial")
@Action("/workingplatformInfo")
public class WorkingplatformInfoAction extends CommonAction {
	@Autowired
	private WorkingplatformInfoService service;
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	@Autowired
	public void init() {
		model = new OcrmFWpInfo();
		setCommonService(service);
		needLog = false;;
	}
	
	public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		StringBuilder withSQLSb = new StringBuilder();
		StringBuilder sb = new StringBuilder(
				"select distinct G.ORG_NAME,'' as checkTitle,p.*,decode(SE.SECTION_NAME,'','全部目录',SE.SECTION_NAME) AS MESSAGE_TYPE_VALUE"
				+ " from ocrm_f_wp_info p  " +
				" LEFT JOIN ADMIN_AUTH_ORG G ON G.ORG_ID = P.PUBLISH_ORG " +
				" left join OCRM_F_WP_ANNEXE a on a.RELATION_INFO = to_char(p.MESSAGE_ID) " +
				" LEFT JOIN OCRM_F_WP_INFO_SECTION SE ON TO_CHAR(SE.SECTION_ID) = p.MESSAGE_TYPE where 1>0 "
		);
        AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currenUserId = auth.getUserId();
        String currendOrgId = auth.getUnitId();
        List orgList  = auth.getPathOrgList();
        Map b = null;
     
        StringBuffer c = new StringBuffer("");
        for(int a=0;a<orgList.size();a++){
        	b = (Map)orgList.get(a);     		
        	c.append((String)b.get("UNITSEQ"));	 	
        }
        sb.append(" and (p.publish_user in (select t.user_name from admin_auth_account t where t.account_name = '"+currenUserId+"')");
        sb.append(" or (p.public_type = '1') " +
        				"or (p.publish_org  = '"+currendOrgId+"' and p.public_type = '2')" +
        				" or (p.publish_org in('"+c.toString()+"') and p.public_type = '3'))");
        
		//查询本栏目类别及其子类别的数据
		for(String key:this.getJson().keySet()){
            if(null!=this.getJson().get(key)&&!this.getJson().get(key).equals("")){
                if((key.equals("MESSAGE_TYPE")||key.equals("MESSAGE_TYPE_VALUE"))&&!"root".equals(this.getJson().get(key))){
                	boolean boo = isNumeric(String.valueOf(this.getJson().get(key)));
                	sb.append(" and p.MESSAGE_TYPE in ");
                	if("DB2".equals(SystemConstance.DB_TYPE)) {
                		withSQLSb.append(" with T1(SECTION_ID,SECTION_NAME,PARENT_SECTION) as (" +
        				"	select distinct to_char(section_id),SECTION_NAME, PARENT_SECTION from ocrm_f_wp_info_section" +
        				" ),T2(PId,SECTION_NAME,PARENT_SECTION) as (" +
        				" select distinct to_char(section_id),SECTION_NAME,PARENT_SECTION from T1 where ");
            			if(boo){
            				withSQLSb.append(" SECTION_ID = '"+this.getJson().get(key)+"' ");
    	            	}else{
    	            		withSQLSb.append(" SECTION_NAME ='"+this.getJson().get(key)+"' ");
    	            	}
            			withSQLSb.append(" union all " +
        				" select T1.section_id,T1.SECTION_NAME,T1.PARENT_SECTION from T1,T2 where T2.PId = T1.PARENT_SECTION " +
        				" ) ");
                		sb.append("( select pid from T2) ");
                	} else {
                		sb.append("(select distinct to_char(section_id) from ocrm_f_wp_info_section where 1=1 " +
            			"start with ");
		            	if(boo){
		            		sb.append(" SECTION_ID = '"+this.getJson().get(key)+"' ");
		            	}else{
		            		sb.append(" SECTION_NAME ='"+this.getJson().get(key)+"' ");
		            	}
		            	sb.append("connect by parent_section= prior to_char(section_id) )");
                	}
                			
                }
                if(key.equals("PUBLISH_DATE")){
                	sb.append(" and p.PUBLISH_DATE >= to_date('"+this.getJson().get(key).toString().substring(0,10)+"','yyyy-mm-dd')");
                }
                if(key.equals("PUBLISH_DATE_END")){
                	sb.append(" and p.PUBLISH_DATE <= to_date('"+this.getJson().get(key).toString().substring(0,10)+"','yyyy-mm-dd')");
                }
                if("PUBLISH_USER".equals(key)){
                	sb.append(" and p.PUBLISH_USER like  '%"+this.getJson().get(key)+"%'");
                }
                if("MESSAGE_TITLE".equals(key)){
                	sb.append(" and p.MESSAGE_TITLE like  '%"+this.getJson().get(key)+"%'");
                }
                //检索信息
                if("CHECKTITLE".equals(key) && this.getJson().get("CHECKMSG") != null){
                	if("01".equals(this.getJson().get(key))){//全部
                		List<?> list =SearchFiles.getInstance().serach("contents", (String) this.getJson().get("CHECKMSG"));
        				if(list != null && list.size() >0){
        					StringBuilder docName =new StringBuilder();
        					for(int i=0;i<list.size();i++){
        						docName.append(",'");
        						docName.append(list.get(i));
        						docName.append("'");
        					}
        					sb.append(" and (MESSAGE_TITLE like '%"+ this.getJson().get("CHECKMSG") + "%' or a.PHYSICAL_ADDRESS in ("+docName.toString().substring(1)+") ) ");
        				}else{
        					sb.append(" and (MESSAGE_TITLE like '%"+ this.getJson().get("CHECKMSG") + "%')");
        				}
                	}else if("02".equals(this.getJson().get(key))){//标题
                		sb.append(" and (MESSAGE_TITLE like '%"+ this.getJson().get("CHECKMSG") + "%')");
                	}else if("03".equals(this.getJson().get(key))){//内容
                		List<?> list =SearchFiles.getInstance().serach("contents", (String) this.getJson().get("CHECKMSG"));
                		if(list != null && list.size() >0){
        					StringBuilder docName =new StringBuilder();
        					for(int i=0;i<list.size();i++){
        						docName.append(",'");
        						docName.append(list.get(i));
        						docName.append("'");
        					}
        					sb.append(" and (a.PHYSICAL_ADDRESS in ("+docName.toString().substring(1)+") ) ");
        				}else{
        					sb.append(" and (a.PHYSICAL_ADDRESS like '%"+ this.getJson().get("CHECKMSG") + "%')");
        				}
                	}
                }
               
            }
        }
		
		SQL = sb.toString();
		withSQL = withSQLSb.toString();
		this.setPrimaryKey(" p.MESSAGE_ID desc");
		addOracleLookup("p.PUBLIC_TYPE","SHARE_FLAG");
		datasource = ds;
        configCondition("p.MESSAGE_TITLE"," like ","'%"+"MESSAGE_TITLE"+"%'",DataType.String);
        configCondition("p.MESSAGE_SUMMARY","=","MESSAGE_SUMMARY",DataType.String);
        configCondition("p.MESSAGE_INTRODUCE","=","MESSAGE_INTRODUCE",DataType.String);
        configCondition("p.PUBLISH_ORG","=","ORG_NAME",DataType.String);
        
	}
	
	public static boolean isNumeric(String str){
		  for (int i = str.length();--i>=0;){   
		   if (!Character.isDigit(str.charAt(i))){
		    return false;
		   }
		  }
		  return true;
    }
	
	public HttpHeaders findWithType() throws Exception {
		try {
			StringBuilder sb = new StringBuilder(
					"select c from  OcrmFWpInfo c where 1=1 ");
			Map<String, Object> values = new HashMap<String, Object>();
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			if (request.getParameter("start") != null)
				start = new Integer(request.getParameter("start")).intValue();
			if (request.getParameter("limit") != null)
				limit = new Integer(request.getParameter("limit")).intValue();
			String messageType = request.getParameter("messageType");
			if (messageType != null&&messageType.equals("root")==false) {
				if (messageType.length() > 0) {
					sb.append("and c.messageType = :messageType");
					values.put("messageType", messageType);
				}
			} else {
				this.setJson(request.getParameter("ddddd"));
				for (String key : this.getJson().keySet()) {
					sb.append(" and c." + key + " = :" + key);
					values.put(key, this.getJson().get(key));
				}
			}
			
			/**-添加知识库发布 查看详情-start*/
			String msgId = request.getParameter("msgId");
			if(msgId !=null && !"".equals(msgId)){
				sb.append(" and c.messageId = '" +msgId+ "' ");
			}
			/**-添加知识库发布 查看详情-end*/

			return super.indexPageByJql(sb.toString(), values);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	// 删除
	public String batchDestroy() {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			long idStr = Long.parseLong(request.getParameter("messageId"));
			String jql = "delete from OcrmFWpInfo c where c.messageId in ("
					+ idStr + ")";
			Map<String, Object> values = new HashMap<String, Object>();
			service.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
			return "success";
	}
	//修改栏目属性表
	public String update_productType() throws Exception {
		try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
			.get(ServletActionContext.HTTP_REQUEST);
			String messageType = request.getParameter("messageType");
			String productType = request.getParameter("productType");
			String jql = "update OcrmFWpInfo c set c.productType ='"+productType+"' where c.messageType = ("+ messageType + ")";
			Map<String, Object> values = new HashMap<String, Object>();
			service.batchUpdateByName(jql, values);
			addActionMessage("batch removed successfully");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * 初始化索引
	 */
	@SuppressWarnings("unchecked")
	public void createIndexFiles(String physicalAddress){
		try {
			String isInit = FileTypeConstance.getSystemProperty("isInitIndex");
			if("T".equals(isInit)){
//				IndexFiles.getInstance().delete(physicalAddress);
				Map<String,Object> values = new HashMap<String, Object>();
				values.put("relationMod", "infomation");
//				List<WorkingplatformAnnexe> list1 = (List<WorkingplatformAnnexe>) service.findByJql("select t from WorkingplatformAnnexe t where t.relationMod=:relationMod", values);
				StringBuilder builder = new StringBuilder();
				builder.append(FileTypeConstance.getUploadPath());
				if (!builder.toString().endsWith(File.separator)) {
				    builder.append(File.separator);
				}
				builder.append(File.separator);
				String path = builder.toString();
//				for(int i=0;i<list1.size();i++){
//					String filePath = path + list1.get(i).getPhysicalAddress();
//					IndexFiles.getInstance().createIndex(new File(filePath));
//				}
				String filePath = path + physicalAddress;
				IndexFiles.getInstance().createIndex(new File(filePath));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化索引
	 */
	@SuppressWarnings("unchecked")
	public void createIndexFiles(){
		try {
			String isInit = FileTypeConstance.getSystemProperty("isInitIndex");
			if("T".equals(isInit)){
				IndexFiles.getInstance().deleteAll();
				Map<String,Object> values = new HashMap<String, Object>();
				values.put("relationMod", "infomation");
				List<OcrmFWpInfo> list = 
					(List<OcrmFWpInfo>) service.findByJql("select f from OcrmFWpInfo f ", null);
				StringBuffer msgId = new StringBuffer();
				String jql = "";
				for(int i=0;i<list.size();i++){
					msgId.append(",'").append(list.get(i).getMessageId().toString()).append("'");
					 
				}
				if(msgId == null || msgId.length()<=0){
					jql ="select t from WorkingplatformAnnexe t where 1=1 " +
					"  and t.relationMod=:relationMod ";
				}else{
					jql ="select t from WorkingplatformAnnexe t where 1=1 " +
					"  and t.relationInfo in ("+msgId.toString().substring(1)+")" +
					"  and t.relationMod=:relationMod";
				}
				List<WorkingplatformAnnexe> list1 = (List<WorkingplatformAnnexe>) service.findByJql(jql, values);
				StringBuilder builder = new StringBuilder();
				builder.append(FileTypeConstance.getUploadPath());
				if (!builder.toString().endsWith(File.separator)) {
				    builder.append(File.separator);
				}
				builder.append(File.separator);
				String path = builder.toString();
				for(int i=0;i<list1.size();i++){
					String filePath = path + list1.get(i).getPhysicalAddress();
					IndexFiles.getInstance().createIndex(new File(filePath));
				}
			}
		} catch (Exception e) {
			throw new BizException(0, 1, "1002", e.getMessage());
		}
	}
}
