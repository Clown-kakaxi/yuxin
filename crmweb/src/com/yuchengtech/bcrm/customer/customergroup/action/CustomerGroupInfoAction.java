package com.yuchengtech.bcrm.customer.customergroup.action;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customergroup.model.OcrmFCiBase;
import com.yuchengtech.bcrm.customer.customergroup.service.CustomerGroupInfoService;
import com.yuchengtech.bcrm.customer.service.CustGroupMemberGraphOperationService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.common.JPAAnnotationMetadataUtil;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 客户群管理-查询、维护、新增
 * @author sujm
 * @since 2013-04-03 
 */
@SuppressWarnings("serial")
@Action("/customergroupinfo")
public class CustomerGroupInfoAction  extends CommonAction {
	
    @Autowired
	private CustomerGroupInfoService customerGroupInfoService;
    @Autowired
	private CustGroupMemberGraphOperationService custGroupMemberGraphOperationService;    
    
    @Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
    AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    @Autowired
	public void init(){
	  	model = new OcrmFCiBase(); 
		setCommonService(customerGroupInfoService);
		//新增修改删除记录是否记录日志,默认为false，不记录日志
		//needLog=true;
	}
    
    /*
     * 客户群主体信息维护，包括新增，修改，删除(删除客户群同时，删除客户群对应的群成员信息)
     * */
    public DefaultHttpHeaders create() {
     	ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        //新增,修改
    	if(request.getParameter("operate").equals("add")){
    		customerGroupInfoService.save(model);
    		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
    		auth.setPid(metadataUtil.getId(model).toString());//获取新增操作产生的最新记录的ID
    	}
    	//从客户细分创建时的默认保存操作
    	if(request.getParameter("operate").equals("addBySearch")){
    		String groupMemberType = (String)request.getParameter("groupMemberType");
    		((OcrmFCiBase)model).setGroupMemberType(groupMemberType);
    		
    		((OcrmFCiBase)model).setCustFrom("2");
    		customerGroupInfoService.save(model);
    		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
    		auth.setPid(metadataUtil.getId(model).toString());//获取新增操作产生的最新记录的ID
    	}
    	 //删除
    	else if(request.getParameter("operate").equals("delete"))
    	{      String s=  request.getParameter("cbid");
    			
               JSONObject jsonObject =JSONObject.fromObject(s);
               JSONArray jarray =  jsonObject.getJSONArray("id");
               for(int i=0;i<jarray.size();i++){
               customerGroupInfoService.remove(Long.parseLong(jarray.get(i).toString()));//删除掉所选择的群信息

               //wzy，20130418，add：删除群成员关系图数据
               custGroupMemberGraphOperationService.delRelationGraphData(Long.parseLong(jarray.get(i).toString()));
               }
               StringBuffer delStr= new StringBuffer("");
               if(jarray.size()>0){
            	   delStr = delStr.append(jarray.getString(0).toString());  
               }
               for(int i = 1;i<jarray.size();i++){
            	   delStr = delStr.append(",");
            	   delStr = delStr.append(jarray.getString(i).toString());
               }
               String jql = "delete from OcrmFCiRelateCustBase p where p.custBaseId in (" + delStr.toString() + ")";
               Map<String,Object> values = new HashMap<String,Object>();
               customerGroupInfoService.batchUpdateByName(jql, values);//删除掉对应的群成员的信息
               
               
               String jql2 = "delete from OcrmFCiBaseSearchsolution p where p.groupId in (" + delStr.toString() + ")";
               Map<String,Object> values2 = new HashMap<String,Object>();
               customerGroupInfoService.batchUpdateByName(jql2, values2);//删除筛选方案
               
               
    	}
        return new DefaultHttpHeaders("success").setLocationId(((OcrmFCiBase) model).getId());
    }
    
    
   /**
    * 查询客户群基本信息
    */
    public void prepare() {
    	   String custBaseNumber = request.getParameter("custBaseNumber");//获取到客户群编号，查询单个客户群信息---客户视图基本信息使用
        StringBuffer builder = new StringBuffer("select t1.*," +
        		"CASE t1.SHARE_FLAG" +
        		" WHEN '0' THEN '私有'" +
        		" WHEN '1' THEN '全行共享'" +
        		" WHEN '2' THEN '本机构共享'" +
        		" WHEN '3' THEN '辖内机构共享' END 	SHARE_FLAG_NAME," +
        		" case when t1.CUST_FROM ='2' then '动态' else (SELECT count(1)  from ocrm_f_ci_relate_cust_base where cust_base_id=t1.id)||'人' end AS MEMBERSNUM ," +
        		" t2.ORG_NAME as cust_base_create_org_name, t3.USER_NAME as createName  " +
        		" from OCRM_F_CI_BASE t1  " +
        		" LEFT JOIN ADMIN_AUTH_ORG t2 on t2.ORG_ID = t1.CUST_BASE_CREATE_ORG " +
        		" LEFT JOIN ADMIN_AUTH_ACCOUNT t3 on t1.CUST_BASE_CREATE_NAME = t3.ACCOUNT_NAME " +
        		"  where 1>0 ");
        
        if(null!=custBaseNumber&&(!"".equals(custBaseNumber))){
        	builder.append(" and t1.id='"+custBaseNumber+"' ");//拼接条件，查询单个客户群信息---客户视图基本信息使用
        }
        
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
        builder.append(" and (t1.Cust_base_create_name='" + currenUserId+ "'");
        builder.append(" or (t1.share_flag = '1') " +
        				"or (t1.cust_base_create_org  = '"+currendOrgId+"' and t1.share_flag = '2')" +
        				" or (t1.cust_base_create_org in('"+c.toString()+"') and t1.share_flag = '3'))");
        
        for (String key : getJson().keySet()){
        	String value = getJson().get(key).toString();
        	if (! "".equals(value)) {
        		if("GROUP_TYPE".equals(key)||"CUST_FROM".equals(key)||"GROUP_MEMBER_TYPE".equals(key)||"SHARE_FLAG".equals(key)){
        			builder.append(" and t1." + key + " = " + " '" + value + "'");
        		}else if("CUST_BASE_NAME".equals(key)||"CUST_BASE_NUMBER".equals(key)){
        			builder.append(" and t1." + key + " like " + "'%" + value +"%'");
        		}else if("custMgrId".equals(key)){
        			builder.append(" and t1.CUST_BASE_CREATE_NAME in('"+value.replace(",", "','")+"')");
        		}else if("CUST_ORG_ID".equals(key)){
        			builder.append(" and t1.CUST_BASE_CREATE_ORG in('"+value.replace(",", "','")+"')");
        		}else if("CUST_BASE_CREATE_DATE_S".equals(key)){
        			builder.append(" and t1.CUST_BASE_CREATE_DATE >= " + "to_date('" + value.substring(0,10) + "', 'YYYY-MM-DD')");            
        		}else if("CUST_BASE_CREATE_DATE_E".equals(key)){
        			builder.append(" and t1.CUST_BASE_CREATE_DATE <= " + "to_date('" + value.substring(0,10) + "', 'YYYY-MM-DD')");  
        		}else if("CUST_ID".equals(key)){
        			builder.append(" and t1.id in(select base.cust_base_id from ocrm_f_ci_relate_cust_base base where base.cust_id = '"+value+"' ) ");
        		}else if("CUST_NAME".equals(key)){
        			builder.append(" and t1.id in(select base.cust_base_id from ocrm_f_ci_relate_cust_base base where base.cust_zh_name like '%"+value+"%' ) ");
        		}else if("CERT_TYPE".equals(key)){
        			builder.append(" and t1.id in(select base.cust_base_id from ocrm_f_ci_relate_cust_base base where base.cust_id in(select cust_id from ACRM_F_CI_CUSTOMER de where de.IDENT_TYPE='"+value+"')  ) ");
        		}else if("CERT_NUM".equals(key)){
        			builder.append(" and t1.id in(select base.cust_base_id from ocrm_f_ci_relate_cust_base base where base.cust_id in(select cust_id from ACRM_F_CI_CUSTOMER de where de.IDENT_NO='"+value+"')  ) ");
        		}
        	}
        }
        setPrimaryKey("t1.CUST_BASE_CREATE_DATE desc ");
        SQL = builder.toString();
        
        datasource = ds;
    }  
    //客户查询部分 选择客户新建客户群
    public void saveWithMenber(){
    	ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");//客户id信息
		String ids[] = custId.split(",");
		((OcrmFCiBase)model).setCustBaseCreateDate(new Date());
		((OcrmFCiBase)model).setCustBaseMemberNum(BigDecimal.valueOf(ids.length));
		((OcrmFCiBase)model).setCustBaseCreateName(auth.getUsername());
		((OcrmFCiBase)model).setCustBaseCreateOrg(auth.getUnitId());
		((OcrmFCiBase)model).setRecentUpdateDate(new Date());
		((OcrmFCiBase)model).setRecentUpdateOrg(auth.getUnitId());
		((OcrmFCiBase)model).setRecentUpdateUser(auth.getUserId());
		if(((OcrmFCiBase)model).getGroupMemberType() == "" || ((OcrmFCiBase)model).getGroupMemberType() == null )
			((OcrmFCiBase)model).setGroupMemberType("3");//如果为3，页面设为disable  所以需要处理一下
		customerGroupInfoService.save(model);
		Long id = ((OcrmFCiBase)model).getId();
		String code = "C0" + id;
		((OcrmFCiBase)model).setCustBaseNumber(code);
		customerGroupInfoService.save(model);
		
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
		auth.setPid(metadataUtil.getId(model).toString());//获取新增操作产生的最新记录的ID
    }
    //反查对应客户群信息
	public String queryGroupInfo(){
 		ActionContext ctx = ActionContext.getContext();
 		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
 		String mktActiId = request.getParameter("groupId");
 		json = customerGroupInfoService.loadGroupInfo(mktActiId);
 		return "success";
 	}
}