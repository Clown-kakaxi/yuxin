package com.yuchengtech.bcrm.custview.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiCustElecMgrInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 客户视图档案管理
 * @author agile
 *
 */
@Service
public class FilesCustInfoService extends CommonService {
	
	public FilesCustInfoService(){
		JPABaseDAO<AcrmFCiCustElecMgrInfo,Long> baseDao = new JPABaseDAO<AcrmFCiCustElecMgrInfo,Long>(AcrmFCiCustElecMgrInfo.class);
		super.setBaseDAO(baseDao);
	}
	public Object save(Object obj){
		AcrmFCiCustElecMgrInfo event = (AcrmFCiCustElecMgrInfo)obj;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String customerId = request.getParameter("custId");
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//根据Id是否为空判断是新增还是 更改
		if(event.getId()==null){
			event.setCreateDate(new Date());
			event.setCreateUser(auth.getUsername());
			return super.save(event);
		}else{
			AcrmFCiCustElecMgrInfo info = (AcrmFCiCustElecMgrInfo)this.find(event.getId());
			info.setFileType(event.getFileType());
			info.setArchiveDate(event.getArchiveDate());
			info.setFileName(event.getFileName());
			info.setCreateDate(new Date());
			info.setCreateUser(auth.getUsername());
			return super.save(info);
		}
	}
}
