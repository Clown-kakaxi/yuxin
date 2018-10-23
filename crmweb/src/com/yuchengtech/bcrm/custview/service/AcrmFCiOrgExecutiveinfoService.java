package com.yuchengtech.bcrm.custview.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiOrgExecutiveinfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 对公客户视图==联系人信息
 * @author agile
 *
 */
@Service
public class AcrmFCiOrgExecutiveinfoService extends CommonService {
	
	public AcrmFCiOrgExecutiveinfoService(){
		JPABaseDAO<AcrmFCiOrgExecutiveinfo,String> baseDao = new JPABaseDAO<AcrmFCiOrgExecutiveinfo,String>(AcrmFCiOrgExecutiveinfo.class);
		super.setBaseDAO(baseDao);
	}
	public Object save(Object obj){
		AcrmFCiOrgExecutiveinfo executive = (AcrmFCiOrgExecutiveinfo)obj;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String customerId = request.getParameter("custId");
		String extensionTel = request.getParameter("extensionTel");//分机号码
		String officeTelT = request.getParameter("officeTelFj");//办公室号码
		String flag = request.getParameter("flag");//是否高管标识1是0否
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//根据Id是否为空判断是新增还是 更改
		if(executive.getLinkmanId()==null){
			List<String> customers = super.findByJql("select c.custId from  AcrmFCiCustomer c where c.identType = '"+executive.getIdentType()+"'" +
					" and c.identNo = '"+executive.getIdentNo()+"' " +
					" and c.custName = '"+executive.getLinkmanName()+"' ", null);
			if(!customers.isEmpty() && customers.size()>0){
				executive.setIndivCusId(customers.get(0).toString());
				executive.setIsThisBankCust("1");//是否我行客户 1是0否
			}else{
				executive.setIsThisBankCust("0");
			}
			
			executive.setOrgCustId(customerId);
			executive.setLastUpdateUser(auth.getUsername());
			executive.setLastUpdateTm(new Timestamp(new Date().getTime()));
			if("1".equals(flag)){
				executive.setLinkmanType("3");//标识高管
			}else{
				executive.setLinkmanType("99");//其他
				if(extensionTel!=null){
					executive.setOfficeTel(officeTelT+"|"+extensionTel);
				}
			}
			if("1".equals(flag)){//高管
				checkExists(executive,true,true);
			}else{
				checkExists(executive,true,false);
			}
			return super.save(executive);
		}else{
			AcrmFCiOrgExecutiveinfo executiveinfo = (AcrmFCiOrgExecutiveinfo) this.find(executive.getLinkmanId());
			executiveinfo.setIdentType(executive.getIdentType());
			executiveinfo.setIdentNo(executive.getIdentNo());
			executiveinfo.setLinkmanName(executive.getLinkmanName());
			executiveinfo.setGender(executive.getGender());
			executiveinfo.setBirthday(executive.getBirthday());
			executiveinfo.setLinkmanTitle(executive.getLinkmanTitle());
			executiveinfo.setWorkPosition(executive.getWorkPosition());
			
			if("1".equals(flag)){//高管
				executiveinfo.setOfficeTel(executive.getOfficeTel());
			}else{
				if(extensionTel!=null){
					executiveinfo.setOfficeTel(officeTelT+"|"+extensionTel);
				};
			}
			executiveinfo.setMobile(executive.getMobile());
			executiveinfo.setHomeTel(executive.getHomeTel());
			executiveinfo.setFex(executive.getFex());
			executiveinfo.setEmail(executive.getEmail());
			executiveinfo.setRemark(executive.getRemark());
			executiveinfo.setLastUpdateUser(auth.getUsername());
			executiveinfo.setLastUpdateTm(new Timestamp(new Date().getTime()));
			if("1".equals(flag)){//高管
				checkExists(executiveinfo,false,true);
			}else{
				checkExists(executiveinfo,false,false);
			}
			return super.save(executiveinfo);
		}
	}
	
    public Map check(String identType,String identNo,String linkmanName){
    	Map<String, String> map = new HashMap<String, String>();
    	
    	String gender = "";
    	String birthday = "";
    	String unit_tel = "";
    	String mobile_phone = "";
    	String home_tel = "";
    	String unit_fex = "";
    	String email = "";
    	String remark = "";
		
    	List taskList = this.em.createNativeQuery("SELECT  p.gender,p.birthday,p.unit_tel,p.mobile_phone,p.home_tel,p.unit_fex,p.email,p.remark" +
    			" FROM ACRM_F_CI_CUSTOMER c " +
    			" left join ACRM_F_CI_PERSON p on c.cust_id = p.cust_id " +
    			" WHERE c.ident_type = '"+identType+"'" +
    		    " and c.ident_no = '"+identNo+"'" +
    		    " and c.cust_name = '"+linkmanName+"'").getResultList();
    	
    	if(taskList != null && taskList.size() > 0){
    		for(int i=0;i<taskList.size();i++){
    			Object[] taskObj = (Object[]) taskList.get(i);
    			gender = String.valueOf(taskObj[0]);
    			birthday = String.valueOf(taskObj[1]);
    			unit_tel =String.valueOf(taskObj[2]);
    			mobile_phone =String.valueOf(taskObj[3]);
    			home_tel =String.valueOf(taskObj[4]);
    			unit_fex =String.valueOf(taskObj[5]);
    			email =String.valueOf(taskObj[6]);
    			remark =String.valueOf(taskObj[7]);
    		}
    	}
    	
    	map.put("gender", gender);
    	map.put("birthday", birthday);
    	map.put("unit_tel", unit_tel);
    	map.put("mobile_phone", mobile_phone);
    	map.put("home_tel", home_tel);
    	map.put("unit_fex", unit_fex);
    	map.put("email", email);
    	map.put("remark", remark);
    	return map;
    }
    //验证联系人信息 和高管信息是否已经存在
    public void checkExists(AcrmFCiOrgExecutiveinfo info,boolean flag,boolean flag1){//flag 新增修改  flag1 是否高管
    	StringBuffer jql = new StringBuffer();
    	jql.append("select f from AcrmFCiOrgExecutiveinfo f where 1=1  ")
    	   .append(" and f.orgCustId = '"+info.getOrgCustId()+"' ")
    	   .append(" and f.identType = '"+info.getIdentType()+"' ")
    	   .append(" and f.identNo = '"+info.getIdentNo()+"' ")
    	   .append(" and f.linkmanName = '"+info.getLinkmanName()+"' ");
    	if(flag){//新增
    		if(flag1){//高管
    			jql.append(" and f.linkmanType = '3'");
    		}else{
    			jql.append(" and f.linkmanType = '99'");
    		}
    	}else{
    		if(flag1){//高管
    			jql.append(" and f.linkmanId <> '"+info.getLinkmanId()+"' ");
    			jql.append(" and f.linkmanType = '3'");
    		}else{
    			jql.append(" and f.linkmanId <> '"+info.getLinkmanId()+"' ");
    			jql.append(" and f.linkmanType = '99'");
    		}
    	}
    	List<AcrmFCiOrgExecutiveinfo> acrmFCiOrgExecutiveinfos = this.findByJql(jql.toString(), null);
    	for(AcrmFCiOrgExecutiveinfo a : acrmFCiOrgExecutiveinfos){
    		throw new BizException(1, 0, "1002", "联系人信息已经存在，请勿重复创建!");
    	}
    }
    //创建潜在客户时 更新高管表
    public void updateExecutiveinfo(String identType,String identNo,String custName){
    	String custId = "";
    	List<String> customers = this.findByJql("select c.custId from  AcrmFCiCustomer c where c.identType = '"+identType+"'" +
				" and c.identNo = '"+identNo+"' " +
				" and c.custName = '"+custName+"' " +
				" and c.custStat = '2' " +
				" and c.potentialFlag = '1' ", null);
    	if(customers != null && customers.size()>0){
    		custId = customers.get(0);
    	}
    	this.batchUpdateByName("update AcrmFCiOrgExecutiveinfo f set f.indivCusId = '"+custId+"' where 1=1 " +
    			" and f.identType = '"+identType+"' " +
    			" and f.identNo = '"+identNo+"' " +
    			" and f.linkmanName = '"+custName+"' " +
    			"  and f.linkmanType = '3' ", null);
    }
}
