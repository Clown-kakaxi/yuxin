package com.yuchengtech.bcrm.callReport.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.callReport.model.OcrmFSeCallreportN;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * call report
 * @author agile
 *
 */
@Service
public class OcrmFSeCallreportNService extends CommonService {
	
	public OcrmFSeCallreportNService(){
		JPABaseDAO<OcrmFSeCallreportN,Long> baseDao = new JPABaseDAO<OcrmFSeCallreportN,Long>(OcrmFSeCallreportN.class);
		super.setBaseDAO(baseDao);
	}
	public Object save(Object obj){
		OcrmFSeCallreportN busi = (OcrmFSeCallreportN)obj;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	    String callId = request.getParameter("callIds");
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//根据Id是否为空判断是新增还是 更改
		if(busi.getId()==null){
			if(callId!=null && !callId.isEmpty()){
				busi.setCallId(BigDecimal.valueOf(Long.valueOf(callId)));
			}
			busi.setLastUpdateUser(auth.getUserId());
			busi.setLastUpdateTm(new Date());
			return super.save(busi);
		}else{
			if(callId!=null && !callId.isEmpty()){
				busi.setCallId(BigDecimal.valueOf(Long.valueOf(callId)));
			}
			busi.setLastUpdateUser(auth.getUserId());
			busi.setLastUpdateTm(new Date());
			return super.save(busi);
		}
	}
}
