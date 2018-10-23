package com.yuchengtech.bcrm.custview.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiOrgHolderinfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
/**
 * 对公客户视图==股东信息
 * @author agile
 *
 */
@Service
public class AcrmFCiOrgHolderinfoService extends CommonService {
	
	public AcrmFCiOrgHolderinfoService(){
		JPABaseDAO<AcrmFCiOrgHolderinfo,String> baseDao = new JPABaseDAO<AcrmFCiOrgHolderinfo,String>(AcrmFCiOrgHolderinfo.class);
		super.setBaseDAO(baseDao);
	}
	public Object save(Object obj){
		AcrmFCiOrgHolderinfo holder = (AcrmFCiOrgHolderinfo)obj;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String customerId = request.getParameter("custId");
		//查询提醒人员
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//根据Id是否为空判断是新增还是 更改
		if(holder.getHolderId()==null){
			holder.setLastUpdateUser(auth.getUsername());
			holder.setLastUpdateTm(new Timestamp(new Date().getTime()));
			check(holder,true);
			return super.save(holder);
		}else{
			AcrmFCiOrgHolderinfo holderinfo = (AcrmFCiOrgHolderinfo) this.find(holder.getHolderId());
			holderinfo.setHolderName(holder.getHolderName());
			holderinfo.setHolderType(holder.getHolderType());
			holderinfo.setIdentType(holder.getIdentType());
			holderinfo.setIdentNo(holder.getIdentNo());
			holderinfo.setLegalReprName(holder.getLegalReprName());
			holderinfo.setSponsorKind(holder.getSponsorKind());
			holderinfo.setSponsorCurr(holder.getSponsorCurr());
			holderinfo.setSponsorAmt(holder.getSponsorAmt());
			holderinfo.setStockPercent(holder.getStockPercent());
			holderinfo.setActualStockPercent(holder.getActualStockPercent());
			holderinfo.setIsRegAtUsa(holder.getIsRegAtUsa());
			holderinfo.setLastUpdateUser(auth.getUsername());
			holderinfo.setLastUpdateTm(new Timestamp(new Date().getTime()));
			check(holderinfo,false);
			return super.save(holderinfo);
		}
	}
  public void check(AcrmFCiOrgHolderinfo info,boolean flag){
	  String jql = "";
	  if(flag){//新增
		  jql = "select f from AcrmFCiOrgHolderinfo f where 1=1 " +
		  		" and f.identType = '"+info.getIdentType()+"'" +
		  		" and f.identNo = '"+info.getIdentNo()+"'";
	  }else{
		  jql = "select f from AcrmFCiOrgHolderinfo f where 1=1 " +
	  		" and f.identType = '"+info.getIdentType()+"'" +
	  		" and f.identNo = '"+info.getIdentNo()+"'" +
	  	    " and f.holderId <> '"+info.getHolderId()+"'";
	  }
	  List<AcrmFCiOrgHolderinfo> infos = this.findByJql(jql, null);
	  for(AcrmFCiOrgHolderinfo acrmFCiOrgHolderinfo : infos){
		  throw new BizException(1, 0, "1002", "股东成员已经存在，请勿重复创建!");
	  }
  }
	
}
