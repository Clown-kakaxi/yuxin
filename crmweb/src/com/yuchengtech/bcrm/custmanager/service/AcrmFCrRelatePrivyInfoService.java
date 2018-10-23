package com.yuchengtech.bcrm.custmanager.service;



import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.AcrmFCrRelatePrivyInfo;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 关联人信息
 *
 * @author : dongyi
 * @date : 2014-07-18 10:21:57
 */
@Service
public class AcrmFCrRelatePrivyInfoService extends CommonService {
    public AcrmFCrRelatePrivyInfoService(){
        JPABaseDAO<AcrmFCrRelatePrivyInfo, String> baseDao = new JPABaseDAO<AcrmFCrRelatePrivyInfo, String>(AcrmFCrRelatePrivyInfo.class);
        super.setBaseDAO(baseDao);
    }
    
    /**
     * 新增或修改关系人信息
     */
    public Object save(Object obj) {
    	ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	
    	AcrmFCrRelatePrivyInfo acrmfcrrelateprivyinfo = (AcrmFCrRelatePrivyInfo)obj;
        AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String relateid =null;
        relateid =(request.getParameter("relateId")); 
        String  relateidenty =(request.getParameter("identType2")); 
        String  relateattribute =(request.getParameter("privyAttribute")).toString();
        if(relateattribute.equals("A")||relateattribute.equals("R")){//若属性为法人
    		if(relateidenty!=null)
    		acrmfcrrelateprivyinfo.setIdentType(relateidenty);
    	}
        if(relateid == null){//新增
        	
        	acrmfcrrelateprivyinfo.setCancelState("1");//设置注销状态
        	return super.save(acrmfcrrelateprivyinfo);
        }else{//修改
        	return updateRelaPartyInfo(acrmfcrrelateprivyinfo,relateid,auth);
        }
        
    }
    
    private Object updateRelaPartyInfo(
    		AcrmFCrRelatePrivyInfo acrmfcrReatepartyinfo, String relateid,AuthUser auth) {
    	AcrmFCrRelatePrivyInfo tempRelatePartyInfo = em.find(AcrmFCrRelatePrivyInfo.class,acrmfcrReatepartyinfo.getRelateId());//从数据库中获取关联方信息
    	Object obj = null;
    	Date currDate = new Date();
//    	tempRelatePartyInfo.setCreateDate(currDate);
//    	tempRelatePartyInfo.setLastUpdateUser(auth.getUsername());
    	if (tempRelatePartyInfo != null) {
    		tempRelatePartyInfo.setPrivyName(acrmfcrReatepartyinfo.getPrivyName());//修改关联方名称
    		tempRelatePartyInfo.setPrivyAttribute(acrmfcrReatepartyinfo.getPrivyAttribute());//修改关联方属性
    		tempRelatePartyInfo.setIdentType(acrmfcrReatepartyinfo.getIdentType());//修改关联方证件类型
    		tempRelatePartyInfo.setIdentNo(acrmfcrReatepartyinfo.getIdentNo());//修改关联方证件号码
    		tempRelatePartyInfo.setTel(acrmfcrReatepartyinfo.getTel());//修改关联方电话
    		tempRelatePartyInfo.setEmail(acrmfcrReatepartyinfo.getEmail());//修改关联方email
    		tempRelatePartyInfo.setContactAddr(acrmfcrReatepartyinfo.getContactAddr());//修改关联方信息地址
    		tempRelatePartyInfo.setDeclarantBankRel(acrmfcrReatepartyinfo.getDeclarantBankRel());//修改关联方与银行的关系
    		tempRelatePartyInfo.setRelateDeclarantRel(acrmfcrReatepartyinfo.getRelateDeclarantRel());//修改申报人与关联人关系
    		tempRelatePartyInfo.setIsCommecialBank(acrmfcrReatepartyinfo.getIsCommecialBank());//设置是否为商业银行
    		tempRelatePartyInfo.setStockRatio(acrmfcrReatepartyinfo.getStockRatio());//设置持股比例
    		obj = super.save(tempRelatePartyInfo);
    	}
		return obj ;
	}
    /**
     * 保存在关联方申报时创建的关联方信息
     */
    public Object saveDataservice() {
//    	ActionContext ctx = ActionContext.getContext();
//    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST); 
//    	String id = request.getParameter("RELATE_ID");
    	
    	AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    	AcrmFCrDeclarantInfo temprelateprivyinfo = em.find(AcrmFCrRelatePrivyInfo.class,acrmfcrdeclarantinfo.getMainId());//从数据库中获取关联方信息
    	Object obj = null;
    	Date currDate = new Date();
//    	acrmfcrrelateprivyinfo.setCancelState("2");//设置注销状态
//    	acrmfcrrelateprivyinfo.setc(auth.getUsername());//设置创建人
//    	acrmfcrrelateprivyinfo.setCreateDate(currDate);
//    	if (acrmfcrrelateprivyinfo != null) {
//    		acrmfcrrelateprivyinfo.setRelateId(null);
//    		acrmfcrrelateprivyinfo.setDeclarantName(temprelateprivyinfo.getDeclarantName());//设置申报人名字
//    		acrmfcrrelateprivyinfo.setDeclarantAttr(temprelateprivyinfo.getDeclarantAttr());//设置申报人属性
//    		acrmfcrrelateprivyinfo.setDeclarantIdentType(temprelateprivyinfo.getDeclarantIdentType());//设置证件人类型
//    		acrmfcrrelateprivyinfo.setDeclarantIdentNo(temprelateprivyinfo.getDeclarantIdentNo());//设置申报人证件号码
//    		acrmfcrrelateprivyinfo.setIfCommecialBank(temprelateprivyinfo.getIfCommecialBank());//设置是否为商业银行
//    		acrmfcrrelateprivyinfo.setStockRatio(temprelateprivyinfo.getStockRatio());//设置持股比例
//    		acrmfcrrelateprivyinfo.setEffectDate(temprelateprivyinfo.getEffectDate());//设置生效日期
//    		acrmfcrrelateprivyinfo.setDeclarantBankRel(temprelateprivyinfo.getDeclarantBankRel());//设置申报人与银行的关系
//    		obj = super.save(acrmfcrrelateprivyinfo);
//    	}
		return obj ;
	}


	/**
     *  修改关联方注销状态：未注销2->已注销1
     */
	public Object updcancelStat(String id) {
    	Date currDate = new Date();
    	SimpleDateFormat f=new SimpleDateFormat("yyyy-MM-dd");
    	return this.em.createNativeQuery("update ACRM_F_CR_RELATE_PRIVY_INFO t set t.cancel_State = '2' where t.relate_id in ("+id+")").executeUpdate();
    }
}
    /**
     * 批量删除营销指标
     */
//    public void batchRemove(String ids) {
//        Map<String, Object> values = new HashMap<String, Object>();
//        super.batchUpdateByName("DELETE FROM OcrmFMmTarget t WHERE t.targetCode IN('"+ ids +"')", values);
//    }
//}
