package com.yuchengtech.bcrm.custview.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiAccountInfo;
import com.yuchengtech.bcrm.custview.model.AcrmFCiAccountInfoTemp;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 对私客户视图 开通账户信息
 * @author agile
 *
 */
@Service
public class AcrmFCiAccountInfoService extends CommonService {
	
	public AcrmFCiAccountInfoService(){
		JPABaseDAO<AcrmFCiAccountInfo,String> baseDao = new JPABaseDAO<AcrmFCiAccountInfo,String>(AcrmFCiAccountInfo.class);
		super.setBaseDAO(baseDao);
	}
	public Object save(Object obj){
		AcrmFCiAccountInfo info = (AcrmFCiAccountInfo)obj;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String jn = request.getParameter("jn");
		String jw = request.getParameter("jw");
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AcrmFCiAccountInfo info2  = (AcrmFCiAccountInfo)this.find(info.getCustId());
		if(info2==null){
			info.setLastUpdateTm(new Date());
			info.setLastUpdateUser(auth.getUserId());
			if(jn!=null && !jn.isEmpty()){
				info.setIsDomesticCust("0");//0 境内 1 境外
			}
			if(jw!=null && !jw.isEmpty()){
				info.setIsDomesticCust("1");
			}
			info.setState("0");//0 暂存 1审核中，2审核通过
		}else{
			info.setLastUpdateTm(new Date());
			info.setLastUpdateUser(auth.getUserId());
			if(jn!=null && !jn.isEmpty()){
				info.setIsDomesticCust("0");//0 境内 1 境外
			}
			if(jw!=null && !jw.isEmpty()){
				info.setIsDomesticCust("1");
			}
		}
		return super.save(info);
	}
	
	public  void saveTemp(String accountContents,String custId,String jn,String jw) {
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AcrmFCiAccountInfoTemp info = (AcrmFCiAccountInfoTemp)this.em.find(AcrmFCiAccountInfoTemp.class,custId);
		if(info!=null){
			info.setAccountContents(accountContents);
			info.setLastUpdateTm(new Date());
			info.setLastUpdateUser(auth.getUserId());
			if(jn!=null && !jn.isEmpty()){
				info.setIsDomesticCust("0");//0 境内 1 境外
			}
			if(jw!=null && !jw.isEmpty()){
				info.setIsDomesticCust("1");
			}
			super.save(info);
		}else{
			AcrmFCiAccountInfoTemp temp = new AcrmFCiAccountInfoTemp();
			temp.setCustId(custId);
			temp.setLastUpdateTm(new Date());
			temp.setLastUpdateUser(auth.getUserId());
			temp.setAccountContents(accountContents);
			if(jn!=null && !jn.isEmpty()){
				temp.setIsDomesticCust("0");//0 境内 1 境外
			}
			if(jw!=null && !jw.isEmpty()){
				temp.setIsDomesticCust("1");
			}
			super.save(temp);
		}
	}
}
