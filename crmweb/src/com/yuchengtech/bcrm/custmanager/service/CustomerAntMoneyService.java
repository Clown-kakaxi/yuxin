package com.yuchengtech.bcrm.custmanager.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.model.OcrmFCiCustinfoUphi;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

@Service
@Transactional(value="postgreTransactionManager")
public class CustomerAntMoneyService extends CommonService {

	public CustomerAntMoneyService() {
        JPABaseDAO<AcrmFCiCustomer, Long> baseDao = new JPABaseDAO<AcrmFCiCustomer, Long>(AcrmFCiCustomer.class);
        super.setBaseDAO(baseDao);
	}

    /**
     * 增加修改历史信息
     * @param jarray 具体修改项
     * @param date 修改日期
     * @param flag 修改标识   毫秒级日期long
     * @param type 修改类型 
     */
	public void bathsave(HttpServletRequest request) {
    	AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	String currenUserId = auth.getUserId();
    	
		OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
		ws.setCustId(request.getParameter("instanceid"));
		ws.setUpdateItem("反洗钱风险等级");
		ws.setUpdateItemEn("FXQ_RISK_LEVEL");
		ws.setUpdateBeCont(request.getParameter("fxqRiskLevel"));
		ws.setUpdateAfCont(request.getParameter("fxqRiskLevelAfter"));
		ws.setUpdateAfContView(request.getParameter("fxqRiskLevelAfter"));
		ws.setUpdateTable("ACRM_F_CI_GRADE");
		ws.setApprFlag("0");
		ws.setUpdateUser(currenUserId);
		ws.setFieldType("1");
		
		Date date = new Date();
		ws.setUpdateDate(date);
		String flag = DateUtils.currentTimeMillis();// 修改标识更改为毫秒级
		ws.setUpdateFlag(flag + "|0000");

//				//1、文本，2、日期
//				ws.setFieldType(wa.get("fieldType")==null?"1":String.valueOf(wa.get("fieldType")));
//			    ws.setUpdateDate(date);
//			    ws.setUpdateFlag(flag);
		
		super.save(ws);
	}

}
