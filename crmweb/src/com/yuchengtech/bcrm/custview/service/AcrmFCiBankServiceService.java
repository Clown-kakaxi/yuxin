package com.yuchengtech.bcrm.custview.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custview.model.AcrmFCiBankService;
import com.yuchengtech.bcrm.custview.model.AcrmFCiBankServiceTemp;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 对私客户视图 银行服务信息
 * @author agile
 *
 */
@Service
public class AcrmFCiBankServiceService extends CommonService {
	
	public AcrmFCiBankServiceService(){
		JPABaseDAO<AcrmFCiBankService,String> baseDao = new JPABaseDAO<AcrmFCiBankService,String>(AcrmFCiBankService.class);
		super.setBaseDAO(baseDao);
	}
	public Object save(Object obj){
		AcrmFCiBankService service = (AcrmFCiBankService)obj;
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AcrmFCiBankService service2  = (AcrmFCiBankService)this.find(service.getCustId());
		String atm = request.getParameter("ATM_LIMIT");//默认每日累计限额
		String pos = request.getParameter("POS_LIMIT");//默认单笔限额
		String ukey = request.getParameter("id_ukey");//u-key
		String dx = request.getParameter("id_dx");//短信认证码
		String dh = request.getParameter("id_dh");//电话银行
		String sj = request.getParameter("id_sj");//手机银行
		String wx = request.getParameter("id_wx");//微信银行
		String dz = request.getParameter("id_dz");//电子对账单
		String cz = request.getParameter("id_cz");//传真交易服务
		String zw = request.getParameter("id_zw");//账务变动通知
		String yy = request.getParameter("id_yy");//若符合我行的审核条件，愿意接受我行的信贷额度
		String dy = request.getParameter("id_dy");//同电邮地址
		
		String jiejika = request.getParameter("jiejika");//借记卡申请
		String atm2 = request.getParameter("id_atm2");//每日累计转账最高限额RMB
		String pos2 = request.getParameter("id_pos2");//单笔消费限额RMB
		String dzyh = request.getParameter("dzyh");//电子银行服务
		String wlyh = request.getParameter("wlyh");//网络银行
		
		if(service2==null){
			service.setAtmLimit(atm);
			service.setPosLimit(pos);
			service.setUkey(ukey);
			service.setMessageCode(dx);
			service.setPhoneBanking(dh);
			service.setMobileBanking(sj);
			service.setMicroBanking(wx);
			service.setStatements(dz);
			service.setTransactionService(cz);
			service.setChangeNotice(zw);
			service.setAccept(yy);
			service.setMailAddress(dy);
			service.setIsCardApply(jiejika);
			service.setIsAtmHigh(atm2);
			service.setIsPosHigh(pos2);
			service.setIsElebankSer(dzyh);
			service.setIsNtBank(wlyh);
			service.setLastUpdateTm(new Date());
			service.setLastUpdateUser(auth.getUserId());
			service.setState("0");//0 暂存 1审核中，2审核通过
		}else{
			service.setAtmLimit(atm);
			service.setPosLimit(pos);
			service.setUkey(ukey);
			service.setMessageCode(dx);
			service.setPhoneBanking(dh);
			service.setMobileBanking(sj);
			service.setMicroBanking(wx);
			service.setStatements(dz);
			service.setTransactionService(cz);
			service.setChangeNotice(zw);
			service.setAccept(yy);
			service.setMailAddress(dy);
			service.setIsCardApply(jiejika);
			service.setIsAtmHigh(atm2);
			service.setIsPosHigh(pos2);
			service.setIsElebankSer(dzyh);
			service.setIsNtBank(wlyh);
			service.setLastUpdateTm(new Date());
			service.setLastUpdateUser(auth.getUserId());
		}
		return super.save(service);	
	}
	
	public  void saveTemp(Map<String, String> map) {
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AcrmFCiBankServiceTemp info = (AcrmFCiBankServiceTemp)this.em.find(AcrmFCiBankServiceTemp.class,map.get("custId"));
		if(info!=null){
			info.setAtmLimit(map.get("atm"));
			info.setPosLimit(map.get("pos"));
			info.setAtmHigh(map.get("atmHigh"));
			info.setPosHigh(map.get("posHigh"));
			info.setUkey(map.get("ukey"));
			info.setMessageCode(map.get("dx"));
			info.setPhoneBanking(map.get("dh"));
			info.setMobileBanking(map.get("sj"));
			info.setMicroBanking(map.get("wx"));
			info.setStatements(map.get("dz"));
			info.setTransactionService(map.get("cz"));
			info.setChangeNotice(map.get("zw"));
			info.setAccept(map.get("yy"));
			info.setMailAddress(map.get("dy"));
			info.setMail(map.get("mail"));
			info.setIsCardApply(map.get("jiejika"));
			info.setIsAtmHigh(map.get("atm2"));
			info.setIsPosHigh(map.get("pos2"));
			info.setIsElebankSer(map.get("dzyh"));
			info.setIsNtBank(map.get("wlyh"));
			info.setLastUpdateTm(new Date());
			info.setLastUpdateUser(auth.getUserId());
			super.save(info);
		}else{
			AcrmFCiBankServiceTemp temp = new AcrmFCiBankServiceTemp();
			temp.setCustId(map.get("custId"));
			temp.setAtmLimit(map.get("atm"));
			temp.setPosLimit(map.get("pos"));
			temp.setAtmHigh(map.get("atmHigh"));
			temp.setPosHigh(map.get("posHigh"));
			temp.setUkey(map.get("ukey"));
			temp.setMessageCode(map.get("dx"));
			temp.setPhoneBanking(map.get("dh"));
			temp.setMobileBanking(map.get("sj"));
			temp.setMicroBanking(map.get("wx"));
			temp.setStatements(map.get("dz"));
			temp.setTransactionService(map.get("cz"));
			temp.setChangeNotice(map.get("zw"));
			temp.setAccept(map.get("yy"));
			temp.setMailAddress(map.get("dy"));
			temp.setMail(map.get("mail"));
			temp.setIsCardApply(map.get("jiejika"));
			temp.setIsAtmHigh(map.get("atm2"));
			temp.setIsPosHigh(map.get("pos2"));
			temp.setIsElebankSer(map.get("dzyh"));
			temp.setIsNtBank(map.get("wlyh"));
			temp.setLastUpdateTm(new Date());
			temp.setLastUpdateUser(auth.getUserId());
			super.save(temp);
		}
	}
	
	public  int check(String jName){
		List list = this.em.createNativeQuery("select t.* from wf_worklist t where t.WFSTATUS <> '3' and t.WFJOBNAME like '%"+jName+"%'").getResultList();
		if(list != null && list.size() >0){
			return 1;
		}
		return -1;
	}
	//获取卡号
	public List<Map<String, Object>> getCardNo(String custId){
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String sql = "SELECT NVL(BS.CARDNO,OP.CARD_NO) AS CARD_NO FROM  ACRM_F_CI_CUSTOMER CUS LEFT JOIN ACRM_F_CI_BANK_SERVICE BS ON BS.CUST_ID = CUS.CUST_ID LEFT JOIN OCRM_F_CI_OPEN_INFO  OP ON CUS.CUST_ID = OP.CUST_ID where CUS.cust_id = '"+custId+"'";
		//Map<String,Object> modelMap = new HashMap<String, Object>();
		//modelMap.put("custId", custId);
		List<Object> tempList = this.baseDAO.findByNativeSQLWithIndexParam(sql, null);
		if(tempList != null && tempList.size() > 0){
			for (int i = 0; i < tempList.size(); i++) {
				//JSONObject retmap = new JSONObject();
				Map<String, Object> retmap = new HashMap<String, Object>();
				retmap.put("key", tempList.get(i));
				retmap.put("value", tempList.get(i));
				list.add(retmap);
			}
		}
		return list;	
	}
}
