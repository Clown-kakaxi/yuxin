package com.yuchengtech.bcrm.custmanager.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.AcrmFCrDeclarantInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 申报人信息
 *
 * @author : dongyi
 * @date : 2014-07-12 9:10:57
 */
@Service
public class DeclarantNameQueryService extends CommonService {
    public DeclarantNameQueryService(){
        JPABaseDAO<AcrmFCrDeclarantInfo, String> baseDao = new JPABaseDAO<AcrmFCrDeclarantInfo, String>(AcrmFCrDeclarantInfo.class);
        super.setBaseDAO(baseDao);
    }
    public Object save(Object obj) {
    	ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	
    	AcrmFCrDeclarantInfo acrmfcrReatepartyinfo = (AcrmFCrDeclarantInfo)obj;
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return super.save(acrmfcrReatepartyinfo);
        
    }
}