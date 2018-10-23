package com.yuchengtech.bcrm.customer.customerMktTeam.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.OcrmFCmCustMgrInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @description:新增客户经理团队管理成员信息保存，修改 ，删除
 * @author xiebz
 * @data 2014-07-02
 */
@Service
public class CustomerMktTeamService extends CommonService{
	public CustomerMktTeamService(){
        JPABaseDAO<OcrmFCmCustMgrInfo, Long> baseDao = new JPABaseDAO<OcrmFCmCustMgrInfo, Long>(OcrmFCmCustMgrInfo.class);
        super.setBaseDAO(baseDao);
    }

    /**
     * 修改，保存数据处理
     */
    public Object save(Object obj) {
        ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		OcrmFCmCustMgrInfo marketTeam = (OcrmFCmCustMgrInfo)obj;
		return super.save(marketTeam);
    }
}
