package com.yuchengtech.bcrm.custmanager.service;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.AcrmFCrDeclarantInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPAAnnotationMetadataUtil;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 申报人的新增修改
 *
 * @author : dongyi
 * @date : 2014-07-22 9:45:57
 */
@Service
public class RelateInfoTempService extends CommonService {
    public RelateInfoTempService(){
        JPABaseDAO<AcrmFCrDeclarantInfo, String> baseDao = new JPABaseDAO<AcrmFCrDeclarantInfo, String>(AcrmFCrDeclarantInfo.class);
        super.setBaseDAO(baseDao);
    }
    
    public Object save(Object obj) {
    	ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	//创建人。创建日期，申报状态，注销状态
    	
    	AcrmFCrDeclarantInfo acrmfcrReatepartyinfo = (AcrmFCrDeclarantInfo)obj;
    	 AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	Date currDate = new Date();
    	acrmfcrReatepartyinfo.setCreator(auth.getUsername());//设置创建人
    	acrmfcrReatepartyinfo.setCreateDate(currDate);//创建日期
    	acrmfcrReatepartyinfo.setDeclareStatus("1");//设置申报状态，未注销1
    	acrmfcrReatepartyinfo.setCancelState("1");//设置注销状态
    	acrmfcrReatepartyinfo.setChangeType("1");//设置变更类型为新增
    	acrmfcrReatepartyinfo.setDeclareDate(currDate);//设置申报日期
    	
         String mainId =null;
        mainId =(request.getParameter("MAIN_ID")); 
        	
        	return super.save(acrmfcrReatepartyinfo);
        
        	
        
    }


	public void updataData(AcrmFCrDeclarantInfo acrmFCrRelatePartyInfo) {
		AcrmFCrDeclarantInfo temprelateDeclarant = em.find(AcrmFCrDeclarantInfo.class,acrmFCrRelatePartyInfo.getMainId());//从数据库中获取关联方信息
	    	AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//	    	temprelateDeclarant.setCancelState("2");//设置注销状态
//	    	temprelateDeclarant.setCreator(auth.getUsername());//设置创建人
//	    	
	    	Date currDate = new Date();
	    	if (temprelateDeclarant != null) {
	    		
	    		temprelateDeclarant.setDeclarantName(acrmFCrRelatePartyInfo.getDeclarantName());//设置申报人名字
	    		temprelateDeclarant.setDeclarantAttr(acrmFCrRelatePartyInfo.getDeclarantAttr());//设置申报人属性
	    		temprelateDeclarant.setIdentType(acrmFCrRelatePartyInfo.getIdentType());//设置证件人类型
	    		temprelateDeclarant.setIdentNo(acrmFCrRelatePartyInfo.getIdentNo());//设置申报人证件号码
	    		temprelateDeclarant.setIsCommecialBank(acrmFCrRelatePartyInfo.getIsCommecialBank());//设置是否为商业银行
	    		temprelateDeclarant.setStockRatio(acrmFCrRelatePartyInfo.getStockRatio());//设置持股比例
	    		temprelateDeclarant.setStartDate(acrmFCrRelatePartyInfo.getStartDate());//设置生效日期
	    		temprelateDeclarant.setDeclarantBankRel(acrmFCrRelatePartyInfo.getDeclarantBankRel());//设置申报人与银行的关系
	    		temprelateDeclarant.setCancleCause(acrmFCrRelatePartyInfo.getCancleCause());//设置变更原因
	    		temprelateDeclarant.setTel(acrmFCrRelatePartyInfo.getTel());//设置联系电话
	    		temprelateDeclarant.setEmail(acrmFCrRelatePartyInfo.getEmail());//设置邮箱
	    		temprelateDeclarant.setContactAddr(acrmFCrRelatePartyInfo.getContactAddr());//设置联系地址
	    		temprelateDeclarant.setCancelState(acrmFCrRelatePartyInfo.getCancelState());//设置注销状态
	    		temprelateDeclarant.setChangeType("2");//把更改类型变为修改，2
	    		temprelateDeclarant.setLastUpdateTm(currDate);
	    		temprelateDeclarant.setDeclareDate(currDate);
	    		temprelateDeclarant.setLastUpdateUser(auth.getUsername());
	    	   
	    		super.save(temprelateDeclarant);
//	    		em.createNativeQuery("delete from ACRM_F_CR_DECLARANT_INFO where main_id ="+acrmFCrRelatePartyInfo.getMainId()).executeUpdate();//一经修改删除正式表相应的数据
	        	JPAAnnotationMetadataUtil metadataUtil = new JPAAnnotationMetadataUtil();
	        	String id = metadataUtil.getId((AcrmFCrDeclarantInfo)acrmFCrRelatePartyInfo).toString();
	    		auth.setPid(id);
	    		
	    	}else{
	    		AcrmFCrDeclarantInfo temprelateDeclarant1 = em.find(AcrmFCrDeclarantInfo.class,acrmFCrRelatePartyInfo.getMainId());//从数据库中获取关联方信息
	    		
	    		acrmFCrRelatePartyInfo.setStartDate(temprelateDeclarant1.getStartDate());//设置生效日期
	    		acrmFCrRelatePartyInfo.setCancelState(temprelateDeclarant1.getCancelState());//设置注销状态
	    		acrmFCrRelatePartyInfo.setChangeType("2");//把更改类型变为修改，2
	    		acrmFCrRelatePartyInfo.setDeclareStatus(temprelateDeclarant1.getDeclareStatus());//设置申报状态
	    		acrmFCrRelatePartyInfo.setLastUpdateTm(currDate);
	    		acrmFCrRelatePartyInfo.setDeclareDate(currDate);
	    		acrmFCrRelatePartyInfo.setLastUpdateUser(auth.getUsername());
	    		acrmFCrRelatePartyInfo.setRemark(temprelateDeclarant1.getRemark());
	    		acrmFCrRelatePartyInfo.setCreateDate(temprelateDeclarant1.getCreateDate());
	    		acrmFCrRelatePartyInfo.setCreator(temprelateDeclarant1.getCreator());
	    		super.save(acrmFCrRelatePartyInfo);
	    		
	    	}
	}
    
    /**
     * 修改注销状态 由未注销1->已注销2
     **/
    public Object updcancelStat(String id) {
    	Date currDate = new Date();//注销时间
    	SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
    	return this.em.createNativeQuery("update ACRM_F_CR_DECLARANT_INFO t set t.cancel_State = '2',t.cancel_date =to_date('"+f.format(currDate)+"','yyyy-MM-dd') where t.main_id in ("+id+")").executeUpdate();
    }
}

