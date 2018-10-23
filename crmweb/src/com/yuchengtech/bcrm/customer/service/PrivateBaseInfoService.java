package com.yuchengtech.bcrm.customer.service;

import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerKeyflag;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiPerson;
import com.yuchengtech.bcrm.customer.model.AcrmFCiFinaBusi;
import com.yuchengtech.bcrm.customer.model.ReviewMapping;
import com.yuchengtech.bcrm.custview.model.AcrmFCiContmeth;
import com.yuchengtech.bcrm.custview.model.AcrmFCiPerFamily;
import com.yuchengtech.bcrm.custview.model.AcrmFCiPerPreference;
import com.yuchengtech.bcrm.model.OcrmFCiCustinfoUphi;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.bob.vo.AuthUser;
/**
 * 
* @ClassName: PrivateBaseInfoService 
* @Description: 对私客户基本信息保存
* @author wangmk1 
* @date 2014-7-29 
*
 */
@Service
public class PrivateBaseInfoService extends CommonService {
	private static Logger log = Logger.getLogger(PrivateBaseInfoService.class);
	
	public PrivateBaseInfoService(){
		JPABaseDAO<AcrmFCiPerson, String>  baseDAO=new JPABaseDAO<AcrmFCiPerson, String>(AcrmFCiPerson.class);  
		super.setBaseDAO(baseDAO);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Object save(Object obj) {
		// TODO Auto-generated method stub
		AcrmFCiPerson person = (AcrmFCiPerson)obj;
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String s1 = request.getParameter("AAA");
		JSONObject aa = JSONObject.fromObject(s1);
		
		String mobie=person.getMobilePhone();
		String email=person.getEmail();
		String homeTel=person.getHomeTel();
		String unitTel=person.getUnitTel();
		
		List<AcrmFCiContmeth> contmethList = this.em.createQuery("select t from AcrmFCiContmeth t where t.custId='"+person.getCustId()+"' and t.contmethType=100 and t.stat=1 and t.isPriori=1 ").getResultList();
		if(!"[]".equals(contmethList.toString())){
			AcrmFCiContmeth mcontmeth = contmethList.get(0);
//			String tele=mcontmeth.getContmethInfo();
			if(mcontmeth!=null){
//				mcontmeth.setIsPriori("0");
				mcontmeth.setLastUpdateSys("CRM");
				mcontmeth.setLastUpdateTm(new Timestamp(new Date().getTime()));
				mcontmeth.setLastUpdateUser(auth.getUserId());
				mcontmeth.setContmethInfo(mobie);
				super.save(mcontmeth);
//				AcrmFCiContmeth contmeth2=new AcrmFCiContmeth();
//				contmeth2.setContmethInfo(mobie);
//				contmeth2.setContmethSeq(mcontmeth.getContmethSeq());
//				contmeth2.setContmethType(mcontmeth.getContmethType());
//				contmeth2.setCustId(mcontmeth.getCustId());
//				contmeth2.setIsPriori(mcontmeth.getIsPriori());
//				contmeth2.setLastUpdateSys("CRM");
//				contmeth2.setLastUpdateTm(new Timestamp(new Date().getTime()));
//				contmeth2.setLastUpdateUser(auth.getUserId());
//				contmeth2.setStat("1");
//				super.save(contmeth2);
			}
		}else{
			AcrmFCiContmeth contmeth2=new AcrmFCiContmeth();
			contmeth2.setContmethInfo(mobie);
			contmeth2.setContmethType("100");
			contmeth2.setCustId(person.getCustId());
			contmeth2.setIsPriori("1");
			contmeth2.setLastUpdateSys("CRM");
			contmeth2.setLastUpdateTm(new Timestamp(new Date().getTime()));
			contmeth2.setLastUpdateUser(auth.getUserId());
			contmeth2.setStat("1");
			super.save(contmeth2);
		}
		
//		List<AcrmFCiContmeth> contmethList2 = this.em.createQuery("select t from AcrmFCiContmeth t where t.custId='"+person.getCustId()+"' and t.contmethType=500 and t.stat=1 ").getResultList();
//		if(!"[]".equals(contmethList2.toString())){
//			AcrmFCiContmeth econtmeth = contmethList2.get(0);
////			String email2=econtmeth.getContmethInfo();
//			if(econtmeth!=null){
//				econtmeth.setIsPriori("");
//				econtmeth.setLastUpdateSys("CRM");
//				econtmeth.setLastUpdateTm(new Timestamp(new Date().getTime()));
//				econtmeth.setContmethInfo(email);
//				econtmeth.setLastUpdateUser(auth.getUserId());
//				super.save(econtmeth);
////				AcrmFCiContmeth contmeth2=new AcrmFCiContmeth();
////				contmeth2.setContmethInfo(email);
////				contmeth2.setContmethSeq(econtmeth.getContmethSeq());
////				contmeth2.setContmethType(econtmeth.getContmethType());
////				contmeth2.setCustId(econtmeth.getCustId());
////				contmeth2.setIsPriori(econtmeth.getIsPriori());
////				contmeth2.setLastUpdateSys("CRM");
////				contmeth2.setLastUpdateTm(new Timestamp(new Date().getTime()));
////				contmeth2.setLastUpdateUser(auth.getUserId());
////				contmeth2.setStat("1");
////				super.save(contmeth2);
//			}
//		}else {
//			AcrmFCiContmeth contmeth2=new AcrmFCiContmeth();
//			contmeth2.setContmethInfo(email);
//			contmeth2.setContmethType("500");
//			contmeth2.setCustId(person.getCustId());
//			contmeth2.setIsPriori("");
//			contmeth2.setLastUpdateSys("CRM");
//			contmeth2.setLastUpdateTm(new Timestamp(new Date().getTime()));
//			contmeth2.setLastUpdateUser(auth.getUserId());
//			contmeth2.setStat("1");
//			super.save(contmeth2);
//		}
		List<AcrmFCiContmeth> contmethList3 = this.em.createQuery("select t from AcrmFCiContmeth t where t.custId='"+person.getCustId()+"' and t.contmethType=204 and t.stat=1 and t.isPriori=1 ").getResultList();
		if(!"[]".equals(contmethList3.toString())){
			AcrmFCiContmeth hcontmeth = contmethList3.get(0);
//			String homeTel2=hcontmeth.getContmethInfo();
			if(hcontmeth!=null){
				hcontmeth.setIsPriori("0");
				hcontmeth.setLastUpdateSys("CRM");
				hcontmeth.setContmethInfo(homeTel);
				hcontmeth.setLastUpdateTm(new Timestamp(new Date().getTime()));
				hcontmeth.setLastUpdateUser(auth.getUserId());
				super.save(hcontmeth);
//				AcrmFCiContmeth contmeth2=new AcrmFCiContmeth();
//				contmeth2.setContmethInfo(homeTel);
//				contmeth2.setContmethSeq(hcontmeth.getContmethSeq());
//				contmeth2.setContmethType(hcontmeth.getContmethType());
//				contmeth2.setCustId(hcontmeth.getCustId());
//				contmeth2.setIsPriori(hcontmeth.getIsPriori());
//				contmeth2.setLastUpdateSys("CRM");
//				contmeth2.setLastUpdateTm(new Timestamp(new Date().getTime()));
//				contmeth2.setLastUpdateUser(auth.getUserId());
//				contmeth2.setStat("1");
//				super.save(contmeth2);
			}
		}else {
			AcrmFCiContmeth contmeth2=new AcrmFCiContmeth();
			contmeth2.setContmethInfo(homeTel);
			contmeth2.setContmethType("204");
			contmeth2.setCustId(person.getCustId());
			contmeth2.setIsPriori("1");
			contmeth2.setLastUpdateSys("CRM");
			contmeth2.setLastUpdateTm(new Timestamp(new Date().getTime()));
			contmeth2.setLastUpdateUser(auth.getUserId());
			contmeth2.setStat("1");
			super.save(contmeth2);
		}
		
		List<AcrmFCiContmeth> contmethList4 = this.em.createQuery("select t from AcrmFCiContmeth t where t.custId='"+person.getCustId()+"' and t.contmethType=203 and t.stat=1 and t.isPriori=1 ").getResultList();
		if(!"[]".equals(contmethList4.toString())){
			AcrmFCiContmeth ucontmeth = contmethList4.get(0);
//			String unitTel2=ucontmeth.getContmethInfo();
			if(ucontmeth!=null){
				ucontmeth.setIsPriori("0");
				ucontmeth.setLastUpdateSys("CRM");
				ucontmeth.setContmethInfo(unitTel);
				ucontmeth.setLastUpdateTm(new Timestamp(new Date().getTime()));
				ucontmeth.setLastUpdateUser(auth.getUserId());
				super.save(ucontmeth);
//				AcrmFCiContmeth contmeth2=new AcrmFCiContmeth();
//				contmeth2.setContmethInfo(unitTel);
//				contmeth2.setContmethSeq(ucontmeth.getContmethSeq());
//				contmeth2.setContmethType(ucontmeth.getContmethType());
//				contmeth2.setCustId(ucontmeth.getCustId());
//				contmeth2.setIsPriori(ucontmeth.getIsPriori());
//				contmeth2.setLastUpdateSys("CRM");
//				contmeth2.setLastUpdateTm(new Timestamp(new Date().getTime()));
//				contmeth2.setLastUpdateUser(auth.getUserId());
//				contmeth2.setStat("1");
//				super.save(contmeth2);
			}
		}else {
			AcrmFCiContmeth contmeth2=new AcrmFCiContmeth();
			contmeth2.setContmethInfo(unitTel);
			contmeth2.setContmethType("203");
			contmeth2.setCustId(person.getCustId());
			contmeth2.setIsPriori("1");
			contmeth2.setLastUpdateSys("CRM");
			contmeth2.setLastUpdateTm(new Timestamp(new Date().getTime()));
			contmeth2.setLastUpdateUser(auth.getUserId());
			contmeth2.setStat("1");
			super.save(contmeth2);
		}
		
		List<AcrmFCiCustomer> list = this.em.createQuery("select t from AcrmFCiCustomer t where t.custId='"+person.getCustId()+"'").getResultList();
		AcrmFCiCustomer customer = list.get(0);
		customer.setShortName((String)aa.get("shortName"));
		customer.setCustName((String)aa.get("custName"));
		customer.setEnName((String)aa.get("enName"));
		customer.setRiskLevel((String)aa.get("riskLevel"));
		customer.setIndustType((String)aa.get("industType"));
		customer.setInoutFlag((String)aa.get("inoutFlag"));
		customer.setSourceChannel((String)aa.get("sourceChannel"));
		super.save(customer);
		
		List<AcrmFCiPerPreference> prefList = this.em.createQuery("select t from AcrmFCiPerPreference t where t.custId='"+ person.getCustId() +"'").getResultList();
		AcrmFCiPerPreference pref = prefList.get(0);
		pref.setContactFreqPrefer((String)aa.get("contactFreqPrefer"));
		pref.setLastUpdateSys("CRM");
		pref.setLastUpdateTm(new Timestamp(new Date().getTime()));
		pref.setLastUpdateUser(auth.getUserId());
		super.save(pref);
		
		List<AcrmFCiPerFamily> fmyList = this.em.createQuery("select t from AcrmFCiPerFamily t where t.custId='"+ person.getCustId() +"'").getResultList();
		AcrmFCiPerFamily fmy = fmyList.get(0);
		fmy.setFamilyAnnIncScope((String)aa.get("familyAnnIncScope"));
		fmy.setLastUpdateSys("CRM");
		fmy.setLastUpdateTm(new Timestamp(new Date().getTime()));
		fmy.setLastUpdateUser(auth.getUserId());
		super.save(fmy);
		
		return super.save(person);
	}
	/**
	 * 对私客户基本信息调整历史1(1参)
	 * @param jarray 从前台获取的要进行保存的历史记录
	 */
	public void bathsave(JSONArray jarray) {
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String currenUserId = auth.getUserId();
		Date date = new Date(0);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s=sdf.format(date);
		Date dateTime = null;
		try {
			dateTime = sdf.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (jarray.size() > 0){
			for (int i = 0; i < jarray.size(); ++i){
				JSONObject wa = (JSONObject)jarray.get(i);
				OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
				ws.setCustId((String)wa.get("custId"));
				wa.get("updateBeCont");
				ws.setUpdateBeCont((String)wa.get("updateBeCont"));
				ws.setUpdateAfCont((String)wa.get("updateAfCont"));
				ws.setUpdateItem((String)wa.get("updateItem"));
			    ws.setUpdateUser(currenUserId);
			    ws.setUpdateDate(dateTime);
			    ws.setApprFlag("0");//0审核中,1审核通过,2审核拒绝
			    super.save(ws);
			}
		}
		return;
	}
	
	/**
	 * 对私客户基本信息调整历史2(4参)
	 * @param jarray
	 * @param date
	 * @param p
	 */
	@SuppressWarnings("unchecked")
	public void bathsave2(JSONArray jarray,JSONArray jarray2, JSONArray jarray3, Date date, String flag) {
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String currenUserId = auth.getUserId();
		List<ReviewMapping>  list = this.findByJql("select c from ReviewMapping c where c.moduleItem='对私基本信息'", null);
		if (jarray.size() > 0){
			for (int i = 0; i < jarray.size(); ++i){
				if(jarray.get(i) == null || "[]".equals(jarray.get(i).toString())){
					continue;
				}
				JSONObject wa = (JSONObject)jarray.get(i);
				OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
				ws.setCustId(String.valueOf(wa.get("custId")));
				ws.setUpdateItem(wa.get("updateItem")==null?"":String.valueOf(wa.get("updateItem")));
				ws.setUpdateBeCont(wa.get("updateBeCont")==null?"":String.valueOf(wa.get("updateBeCont")));
				ws.setUpdateAfCont(wa.get("updateAfCont")==null?"":String.valueOf(wa.get("updateAfCont")));
				ws.setUpdateAfContView(wa.get("updateAfContView")==null?"":String.valueOf(wa.get("updateAfContView")));
				ws.setUpdateTableId(wa.get("updateTableId")==null?"":String.valueOf(wa.get("updateTableId")));
				ws.setApprFlag("0");//0审核中,1审核通过,2审核拒绝
				//1、文本，2、日期
				ws.setFieldType(wa.get("fieldType")==null?"1":String.valueOf(wa.get("fieldType")));
				for(int k=0;k<list.size();k++){
					if((String.valueOf(wa.get("updateItemEn"))).equals(list.get(k).getPageColumn())){
						ws.setUpdateItemEn(list.get(k).getOriginColumn());
						ws.setUpdateTable(list.get(k).getTableName());
					}
				}
			    ws.setUpdateUser(currenUserId);
			    ws.setUpdateDate(date);
			    ws.setUpdateFlag(flag);
			    if(ws.getUpdateTable() == null || "".equals(ws.getUpdateTable()) || ws.getUpdateItemEn() == null || "".equals(ws.getUpdateItemEn())){
			    	log.warn("对私基本信息-----字段映射未找到(忽略该字段): " + String.valueOf(wa.get("updateItemEn")));
			    	continue;
			    }
			    super.save(ws);
			}
		}
		if (jarray2.size() > 0){
			for (int i = 0; i < jarray2.size(); ++i){
				if(jarray2.get(i) == null || "[]".equals(jarray2.get(i).toString())){
					continue;
				}
				JSONObject wa = (JSONObject)jarray2.get(i);
				OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
				ws.setCustId(String.valueOf(wa.get("custId")));
				ws.setUpdateItem(wa.get("updateItem")==null?"":String.valueOf(wa.get("updateItem")));
				ws.setUpdateBeCont(wa.get("updateBeCont")==null?"":String.valueOf(wa.get("updateBeCont")));
				ws.setUpdateAfCont(wa.get("updateAfCont")==null?"":String.valueOf(wa.get("updateAfCont")));
				ws.setUpdateAfContView(wa.get("updateAfContView")==null?"":String.valueOf(wa.get("updateAfContView")));
				ws.setUpdateTableId(wa.get("updateTableId")==null?"":String.valueOf(wa.get("updateTableId")));
				ws.setApprFlag("0");//0审核中,1审核通过,2审核拒绝
				//1、文本，2、日期
				ws.setFieldType(wa.get("fieldType")==null?"1":String.valueOf(wa.get("fieldType")));
				for(int k=0;k<list.size();k++){
					if((String.valueOf(wa.get("updateItemEn"))).equals(list.get(k).getPageColumn())){
						ws.setUpdateItemEn(list.get(k).getOriginColumn());
						ws.setUpdateTable(list.get(k).getTableName());
					}
				}
			    ws.setUpdateUser(currenUserId);
			    ws.setUpdateDate(date);
			    ws.setUpdateFlag(flag);
			    if(ws.getUpdateTable() == null || "".equals(ws.getUpdateTable()) || ws.getUpdateItemEn() == null || "".equals(ws.getUpdateItemEn())){
			    	log.warn("对私基本信息-----字段映射未找到(忽略该字段): " + String.valueOf(wa.get("updateItemEn")));
			    	continue;
			    }
			    super.save(ws);
			}
		}
		if (jarray3.size() > 0){
			for (int i = 0; i < jarray3.size(); ++i){
				if(jarray3.get(i) == null || "[]".equals(jarray3.get(i).toString())){
					continue;
				}
				JSONObject wa = (JSONObject)jarray3.get(i);
				OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
				ws.setCustId(String.valueOf(wa.get("custId")));
				ws.setUpdateItem(wa.get("updateItem")==null?"":String.valueOf(wa.get("updateItem")));
				ws.setUpdateBeCont(wa.get("updateBeCont")==null?"":String.valueOf(wa.get("updateBeCont")));
				ws.setUpdateAfCont(wa.get("updateAfCont")==null?"":String.valueOf(wa.get("updateAfCont")));
				ws.setUpdateAfContView(wa.get("updateAfContView")==null?"":String.valueOf(wa.get("updateAfContView")));
				ws.setUpdateTableId(wa.get("updateTableId")==null?"":String.valueOf(wa.get("updateTableId")));
				ws.setApprFlag("0");//0审核中,1审核通过,2审核拒绝
				//1、文本，2、日期
				ws.setFieldType(wa.get("fieldType")==null?"1":String.valueOf(wa.get("fieldType")));
				for(int k=0;k<list.size();k++){
					if((String.valueOf(wa.get("updateItemEn"))).equals(list.get(k).getPageColumn())){
						ws.setUpdateItemEn(list.get(k).getOriginColumn());
						ws.setUpdateTable(list.get(k).getTableName());
					}
				}
			    ws.setUpdateUser(currenUserId);
			    ws.setUpdateDate(date);
			    ws.setUpdateFlag(flag);
			    if(ws.getUpdateTable() == null || "".equals(ws.getUpdateTable()) || ws.getUpdateItemEn() == null || "".equals(ws.getUpdateItemEn())){
			    	log.warn("对私基本信息-----字段映射未找到(忽略该字段): " + String.valueOf(wa.get("updateItemEn")));
			    	continue;
			    }
			    super.save(ws);
			}
		}
		return;
	}
	
	/**
	 * 将业务信息保存到数据库
	 */
	public void savebusi(JSONObject bb1,String custId){
		@SuppressWarnings("unchecked")
		List<AcrmFCiPerPreference> list = this.em.createQuery("select t from AcrmFCiPerPreference t where t.custId='"+custId+"'").getResultList();
		if(!"[]".equals(list.toString())){
			AcrmFCiPerPreference prefer = list.get(0);
			prefer.setReceiveSmsFlag((String)bb1.get("receiveSmsFlag"));
			super.save(prefer);
		}
		@SuppressWarnings("unchecked")
		List<AcrmFCiPerKeyflag> list2 = this.em.createQuery("select t from AcrmFCiPerKeyflag t where t.custId='"+custId+"'").getResultList();
		if(!"[]".equals(list2.toString())){
			AcrmFCiPerKeyflag keyflag = list2.get(0);
			keyflag.setUsaTaxFlag((String)bb1.get("usaTaxFlag"));
			super.save(keyflag);
		}
		@SuppressWarnings("unchecked")
		List<AcrmFCiPerson> list3 = this.em.createQuery("select t from AcrmFCiPerson t where t.custId='"+custId+"'").getResultList();
		if(!"[]".equals(list3.toString())){
			AcrmFCiPerson pers = list3.get(0);
			String s=(String)bb1.get("ustin");
			String p=pers.getUsaTaxIdenNo();
			pers.setUsaTaxIdenNo(s);
			super.save(pers);
		}
	}
	
	/**
	 * 对私视图基本信息-财务信息数据-保存save/Service
	 * @param cc1
	 * @param custId
	 */
	public void savefin(JSONObject cc1,String custId){
		@SuppressWarnings("unchecked")
		List<AcrmFCiFinaBusi> list = this.em.createQuery("select b from AcrmFCiFinaBusi b where b.custId='"+ custId +"'").getResultList();
		if(!"[]".equals(list.toString())){
			AcrmFCiFinaBusi busi = list.get(0);
			super.save(busi);
		}
	}
	
	/**
	 * 保存用户图片名称
	 */
	public void saveImage(String newImageName,String custId){
		@SuppressWarnings("unchecked")
		List<AcrmFCiCustomer> list = this.em.createQuery("select t from AcrmFCiCustomer t where t.custId='"+custId+"'").getResultList();
		AcrmFCiCustomer customer = list.get(0);
		String oldImageName=customer.getCustImage();
		if(oldImageName!=null&&!"".equals(oldImageName)){
			//删除过期图片
			String filepath = FileTypeConstance.getSystemProperty("uploadImgPath")+File.separator+oldImageName;
			File file =new File(filepath);
			if(file.exists()){
				file.delete();
			}
		}
		customer.setCustImage(newImageName);
		super.save(customer);
		@SuppressWarnings("unchecked")
		//同时设置是否有照片标志为1。
		List<AcrmFCiPerKeyflag> list2 = this.em.createQuery("select t from AcrmFCiPerKeyflag t where t.custId='"+custId+"'").getResultList();
		if(!"[]".equals(list2.toString())){
			AcrmFCiPerKeyflag keyflag = list2.get(0);
			keyflag.setHasPhoto("1");
			super.save(keyflag);
		}else{
			AcrmFCiPerKeyflag keyflag=new AcrmFCiPerKeyflag();
			keyflag.setCustId(custId);
			keyflag.setHasPhoto("1");
			super.save(keyflag);
		}
	}
	/**
	 * 设置HasPhoto字段为0
	 */
	public void setHasPhoto(String custId){
		@SuppressWarnings("unchecked")
		List<AcrmFCiPerKeyflag> list2 = this.em.createQuery("select t from AcrmFCiPerKeyflag t where t.custId='"+custId+"'").getResultList();
		if(!"[]".equals(list2.toString())){
			AcrmFCiPerKeyflag keyflag = list2.get(0);
			keyflag.setHasPhoto("0");
			super.save(keyflag);
		}
	}
	/**
	 * 判断该客户信息是否在审批中
	 * @param custId
	 * @return
	 */
	public  int judge(String instancePre){
		List list = this.em.createNativeQuery("SELECT DISTINCT A.USER_NAME||'['||T.AUTHOR||']' AS AUTHOR FROM WF_WORKLIST T LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.AUTHOR where t.WFSTATUS <> '3' and t.instanceid like '%"
				+ instancePre
				+ "%'"
				+ " union  "
				+ " select DISTINCT ac.USER_NAME || '[' || his.update_user || ']' AS AUTHOR  from OCRM_F_CI_CALLCENTER_UPHIS  his "
				+ " left join ADMIN_AUTH_ACCOUNT ac on his.update_user=ac.account_name "
				+ " where his.cust_id=substr('"+instancePre+"',instr ('"+instancePre+"','_')+1) and his.appr_flag='0' ").getResultList();
		if(list != null && list.size() >0){
			if(list.get(0)!=null){
				return 1;
			}
			
		}
		return -1;
	}
}
