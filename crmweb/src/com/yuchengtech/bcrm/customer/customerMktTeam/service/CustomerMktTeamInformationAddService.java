package com.yuchengtech.bcrm.customer.customerMktTeam.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerMktTeam.model.OcrmFCmMktTeam;
import com.yuchengtech.bcrm.customer.customerMktTeam.model.OcrmFCmMktTeamTemp;
import com.yuchengtech.bcrm.system.model.AdminAuthAccount;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @description:客户经理团队管理 新增，修改，删除处理
 * @author xiebz
 * @date 2014-07-02
 */
@Service
public class CustomerMktTeamInformationAddService extends CommonService{
	public CustomerMktTeamInformationAddService(){
        JPABaseDAO<OcrmFCmMktTeam, Long> baseDao = new JPABaseDAO<OcrmFCmMktTeam, Long>(OcrmFCmMktTeam.class);
        super.setBaseDAO(baseDao);
    }

    /**
     * 修改，保存数据处理
     */
    public Object save(Object obj) {
        ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String ifsave=request.getParameter("ifsave");
		OcrmFCmMktTeam marketTeam = (OcrmFCmMktTeam)obj;
		//新增
		String mobilephone = "";
		List<AdminAuthAccount> userAccounts = super.findByJql("select t from AdminAuthAccount t where t.accountName ='"+marketTeam.getTeamLeaderId()+"'", null);
		for(AdminAuthAccount t:userAccounts){
			mobilephone = t.getMobilephone();
		}
			marketTeam.setLeadTelephone(mobilephone);
			marketTeam.setCreateUser(auth.getUserId());
			marketTeam.setCreateDate(new Date());
			marketTeam.setLastMaintainTime(new Date());
			marketTeam.setTeamScale("0");
			marketTeam.setCreateUserId(auth.getUserId());
			marketTeam.setCreateUserName(auth.getUsername());
			marketTeam.setCreateUserOrgId(auth.getUnitId());
			if("1".equals(ifsave)){//营销部主管直接保存
				marketTeam.setTeamStatus("2");
			}else{
				marketTeam.setTeamStatus("1");
			}
			
			//修改时存临时表 审批通过才能看到修改后的结果
			marketTeam.setLastMaintainTime(new Date());
			this.getEntityManager().clear();
			return  super.save(marketTeam);
    }
    
    public void deleteTeam(OcrmFCmMktTeam marketTeam){
    	marketTeam.setTeamStatus("1");
    	super.save(marketTeam);
    }
    /**
     * 团队审批创建临时表
     * @param marketTeam
     */
    public  void createTemp(OcrmFCmMktTeam marketTeam){
    	OcrmFCmMktTeamTemp  temp = new OcrmFCmMktTeamTemp();
		List<OcrmFCmMktTeamTemp> ocrmFCmMktTeamTemp = super.findByJql("select t from OcrmFCmMktTeamTemp t where t.mktTeamId ='"+marketTeam.getMktTeamId()+"'", null);
		boolean flag = false;
		for(OcrmFCmMktTeamTemp t:ocrmFCmMktTeamTemp){
			flag = true;
		}
		if(flag){
			super.batchUpdateByName("update OcrmFCmMktTeamTemp  set " +
					" mktTeamName = '"+marketTeam.getMktTeamName()+"' ," +
					" teamType ='"+marketTeam.getTeamType()+"' ,"+
					" orgId ='"+marketTeam.getOrgId()+"' ,"+
					" teamLeaderId ='"+marketTeam.getTeamLeaderId()+"' "+
					" where mktTeamId = '"+marketTeam.getMktTeamId()+"'",null);
		}else{
			temp.setMktTeamId(marketTeam.getMktTeamId());
			temp.setMktTeamName(marketTeam.getMktTeamName());
			temp.setTeamType(marketTeam.getTeamType());
			temp.setOrgId(marketTeam.getOrgId());
			temp.setTeamLeaderId(marketTeam.getTeamLeaderId());
			super.save(temp);
		}
    }
    /**
     * 删除
     */
    public void batchRemove(String ids) {
        Map<String, Object> values = new HashMap<String, Object>();
//        Map<String, Object> values2 = new HashMap<String, Object>();
        super.batchUpdateByName("DELETE FROM OcrmFCmMktTeam t WHERE t.mktTeamId IN("+ ids +")", values);
//        super.batchUpdateByName("DELETE FROM AcrmFCmContriPara t WHERE t.mktTeamId IN("+ ids +")", values);	
    }
}
