package com.yuchengtech.bcrm.callReport.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.callReport.model.OcrmFSeCallreportBusi;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * call report
 * @author agile
 *
 */
@Service
public class OcrmFSeCallreportBusiService extends CommonService {
	
	public OcrmFSeCallreportBusiService(){
		JPABaseDAO<OcrmFSeCallreportBusi,Long> baseDao = new JPABaseDAO<OcrmFSeCallreportBusi,Long>(OcrmFSeCallreportBusi.class);
		super.setBaseDAO(baseDao);
	}
	public Object save(Object obj){
		OcrmFSeCallreportBusi busi = (OcrmFSeCallreportBusi)obj;
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
	public void saveBusi(Object obj){
		OcrmFSeCallreportBusi busi = (OcrmFSeCallreportBusi)obj;
		super.save(busi);
	}
}
