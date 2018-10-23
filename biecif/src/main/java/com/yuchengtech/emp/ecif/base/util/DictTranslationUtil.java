package com.yuchengtech.emp.ecif.base.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.CustProductVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.OrgBasicVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.OrgRiskVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerBasicVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerFocusVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerProductVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerRelativeVO;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.PerRiskVO;

/**
 * <p>
 * Description: 业务字典转译
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Create Date: 2009-2-19
 * </p>
 * <p>
 * Company: CITIC BANK
 * </p>
 * 
 * @author pengsenlin
 * @version $Id: ResultUtil.java,v 1.1 2009/07/14 03:23:22 pengsenlin Exp $
 */
@Service
@Transactional(readOnly = true)
public class DictTranslationUtil extends BaseBS<Object> {

	@Autowired
	private CodeUtil codeUtil;

	/**
	 * 转译机构客户基本信息业务字典
	 * @param perRiskVOList
	 * @return
	 */
	public List<OrgBasicVO> setDictOrgBasic(List<OrgBasicVO> orgBasicVOList) {
		if(orgBasicVOList != null && orgBasicVOList.size() > 0){
			/** 
			 *	orgCustType 机构客户类型：CD010007	isGroupCust 集团客户标识：ZQ000285		adminZone 行政区划：ZQ000002	
			 *	nationCode 国家代码：CD000005	entProperty 企业性质：ZQ000001		entScale 企业规模：ZQ000064	
			 *	economicType 经济类型：CD010006		lifecycleStatType 客户生命周期状态类型:ZQ000201
			 */	
			String codeTypes = "CD010007,ZQ000285,ZQ000002,CD000005,ZQ000001,ZQ000064,CD010006,ZQ000201";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(OrgBasicVO orgBasicVO : orgBasicVOList){
				if(dictMap.get("CD010007") != null && dictMap.get("CD010007").get(orgBasicVO.getOrgCustType()) != null){
					orgBasicVO.setOrgCustType(dictMap.get("CD010007").get(orgBasicVO.getOrgCustType()));
				}
				if(dictMap.get("ZQ000285") != null && dictMap.get("ZQ000285").get(orgBasicVO.getIsGroupCust()) != null){
					orgBasicVO.setIsGroupCust(dictMap.get("ZQ000285").get(orgBasicVO.getIsGroupCust()));
				}
				if(dictMap.get("ZQ000002") != null && dictMap.get("ZQ000002").get(orgBasicVO.getAdminZone()) != null){
					orgBasicVO.setAdminZone(dictMap.get("ZQ000002").get(orgBasicVO.getAdminZone()));
				}
				if(dictMap.get("CD000005") != null && dictMap.get("CD000005").get(orgBasicVO.getNationCode()) != null){
					orgBasicVO.setNationCode(dictMap.get("CD000005").get(orgBasicVO.getNationCode()));
				}
				if(dictMap.get("ZQ000001") != null && dictMap.get("ZQ000001").get(orgBasicVO.getEntProperty()) != null){
					orgBasicVO.setEntProperty(dictMap.get("ZQ000001").get(orgBasicVO.getEntProperty()));
				}
				if(dictMap.get("ZQ000064") != null && dictMap.get("ZQ000064").get(orgBasicVO.getEntScale()) != null){
					orgBasicVO.setEntScale(dictMap.get("ZQ000064").get(orgBasicVO.getEntScale()));
				}
				if(dictMap.get("CD010006") != null && dictMap.get("CD010006").get(orgBasicVO.getEconomicType()) != null){
					orgBasicVO.setEconomicType(dictMap.get("CD010006").get(orgBasicVO.getEconomicType()));
				}
				if(dictMap.get("ZQ000201") != null && dictMap.get("ZQ000201").get(orgBasicVO.getLifecycleStatType()) != null){
					orgBasicVO.setLifecycleStatType(dictMap.get("ZQ000201").get(orgBasicVO.getLifecycleStatType()));
				}
			}
			return orgBasicVOList;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译机构客户基本信息业务字典
	 * @param perRiskVOList
	 * @return
	 */
	public List<Object[]> setDictOrgBasicByListObject(List<Object[]> orgBasicVOList) {
		if(orgBasicVOList != null && orgBasicVOList.size() > 0){
			/** 
			 *	orgCustType 机构客户类型：CD010007	isGroupCust 集团客户标识：ZQ000285		adminZone 行政区划：ZQ000002	
			 *	nationCode 国家代码：CD000005	entProperty 企业性质：ZQ000001		entScale 企业规模：ZQ000064	
			 *	economicType 经济类型：CD010006		lifecycleStatType 客户生命周期状态类型:ZQ000201
			 */	
			String codeTypes = "CD010007,ZQ000285,ZQ000002,CD000005,ZQ000001,ZQ000064,CD010006,ZQ000201";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(Object[] orgBasicVO : orgBasicVOList){
				if(orgBasicVO[2] != null && dictMap.get("CD010007") != null && dictMap.get("CD010007").get(orgBasicVO[2].toString()) != null){
					orgBasicVO[2] = dictMap.get("CD010007").get(orgBasicVO[2].toString());
				}
				if(orgBasicVO[3] != null && dictMap.get("ZQ000285") != null && dictMap.get("ZQ000285").get(orgBasicVO[3].toString()) != null){
					orgBasicVO[3] = dictMap.get("ZQ000285").get(orgBasicVO[3].toString());
				}
				if(orgBasicVO[4] != null && dictMap.get("ZQ000002") != null && dictMap.get("ZQ000002").get(orgBasicVO[4].toString()) != null){
					orgBasicVO[4] = dictMap.get("ZQ000002").get(orgBasicVO[4].toString());
				}
				if(orgBasicVO[5] != null && dictMap.get("CD000005") != null && dictMap.get("CD000005").get(orgBasicVO[5].toString()) != null){
					orgBasicVO[5] = dictMap.get("CD000005").get(orgBasicVO[5].toString());
				}
				if(orgBasicVO[6] != null && dictMap.get("ZQ000001") != null && dictMap.get("ZQ000001").get(orgBasicVO[6].toString()) != null){
					orgBasicVO[6] = dictMap.get("ZQ000001").get(orgBasicVO[6].toString());
				}
				if(orgBasicVO[7] != null && dictMap.get("ZQ000064") != null && dictMap.get("ZQ000064").get(orgBasicVO[7].toString()) != null){
					orgBasicVO[7] = dictMap.get("ZQ000064").get(orgBasicVO[7].toString());
				}
				if(orgBasicVO[8] != null && dictMap.get("CD010006") != null && dictMap.get("CD010006").get(orgBasicVO[8].toString()) != null){
					orgBasicVO[8] = dictMap.get("CD010006").get(orgBasicVO[8].toString());
				}
				if(orgBasicVO[13] != null && dictMap.get("ZQ000201") != null && dictMap.get("ZQ000201").get(orgBasicVO[13].toString()) != null){
					orgBasicVO[13] = dictMap.get("ZQ000201").get(orgBasicVO[13].toString());
				}
			}
			return orgBasicVOList;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译客户关注事件业务字典
	 * @param perFocusVOList
	 * @return
	 */
	public List<PerFocusVO> setDictPerFocus(List<PerFocusVO> perFocusVOList) {
		if(perFocusVOList != null && perFocusVOList.size() > 0){
			/** 
			 *	eventType事件类型：ZQ000586	
			 */	
			String codeTypes = "ZQ000586";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(PerFocusVO perFocusVO : perFocusVOList){
				if(dictMap.get("ZQ000586") != null && dictMap.get("ZQ000586").get(perFocusVO.getEventType()) != null){
					perFocusVO.setEventType(dictMap.get("ZQ000586").get(perFocusVO.getEventType()));
				}
				if(perFocusVO.getSubEventType() != null && dictMap.get("ZQ000586") != null && dictMap.get("ZQ000586").get(perFocusVO.getSubEventType()) != null){
					perFocusVO.setSubEventType(dictMap.get("ZQ000586").get(perFocusVO.getSubEventType()));
				}
			}
			return perFocusVOList;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译客户关注事件业务字典
	 * @param perFocusVOList
	 * @return
	 */
	public List<Object[]> setDictPerFocusByListObject(List<Object[]> perFocusVOList) {
		if(perFocusVOList != null && perFocusVOList.size() > 0){
			/** 
			 *	eventType事件类型：ZQ000586	
			 */	
			String codeTypes = "ZQ000586";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(Object[] perFocusVO : perFocusVOList){
				if(perFocusVO[2] != null && dictMap.get("ZQ000586") != null && dictMap.get("ZQ000586").get(perFocusVO[2].toString()) != null){
					perFocusVO[2] = dictMap.get("ZQ000586").get(perFocusVO[2].toString());
				}
				if(perFocusVO[3] != null && dictMap.get("ZQ000586") != null && dictMap.get("ZQ000586").get(perFocusVO[3].toString()) != null){
					perFocusVO[3] = dictMap.get("ZQ000586").get(perFocusVO[3].toString());
				}
			}
			return perFocusVOList;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译客户产品信息业务字典
	 * @param perProductVOList
	 * @return
	 */
	public List<PerProductVO> setDictPerProduct(List<PerProductVO> perProductVOList) {
		if(perProductVOList != null && perProductVOList.size() > 0){
			/** 
			 *	prodType 产品类型：ZQ000421		busiChar 业务性质：ZQ000174		prodClass产品分类：ZQ000416	
			 *	prodForm 产品形态：CD020002		prodStat 产品状态：CD020004		
			 */	
			String codeTypes = "ZQ000421,ZQ000174,ZQ000416,CD020002,CD020004";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(PerProductVO perProductVO : perProductVOList){
				if(dictMap.get("ZQ000421") != null && dictMap.get("ZQ000421").get(perProductVO.getProdType()) != null){
					perProductVO.setProdType(dictMap.get("ZQ000421").get(perProductVO.getProdType()));
				}
				if(dictMap.get("ZQ000174") != null && dictMap.get("ZQ000174").get(perProductVO.getBusiChar()) != null){
					perProductVO.setBusiChar(dictMap.get("ZQ000174").get(perProductVO.getBusiChar()));
				}
				if(dictMap.get("ZQ000416") != null && dictMap.get("ZQ000416").get(perProductVO.getProdClass()) != null){
					perProductVO.setProdClass(dictMap.get("ZQ000416").get(perProductVO.getProdClass()));
				}
				if(dictMap.get("CD020002") != null && dictMap.get("CD020002").get(perProductVO.getProdForm()) != null){
					perProductVO.setProdForm(dictMap.get("CD020002").get(perProductVO.getProdForm()));
				}
				if(dictMap.get("CD020004") != null && dictMap.get("CD020004").get(perProductVO.getProdStat()) != null){
					perProductVO.setProdStat(dictMap.get("CD020004").get(perProductVO.getProdStat()));
				}
			}
			return perProductVOList;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译客户产品信息业务字典
	 * @param perProductVOList
	 * @return
	 */
	public List<Object[]> setDictPerProductByListObject(List<Object[]> perProductVOList) {
		if(perProductVOList != null && perProductVOList.size() > 0){
			/** 
			 *	prodType 产品类型：ZQ000421		busiChar 业务性质：ZQ000174		prodClass产品分类：ZQ000416	
			 *	prodForm 产品形态：CD020002		prodStat 产品状态：CD020004		
			 */	
			String codeTypes = "ZQ000421,ZQ000174,ZQ000416,CD020002,CD020004";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(Object[] perProductVO : perProductVOList){
				if(perProductVO[4] != null && dictMap.get("ZQ000421") != null && dictMap.get("ZQ000421").get(perProductVO[4].toString()) != null){
					perProductVO[4] = dictMap.get("ZQ000421").get(perProductVO[4].toString());
				}
				if(perProductVO[6] != null && dictMap.get("ZQ000174") != null && dictMap.get("ZQ000174").get(perProductVO[6].toString()) != null){
					perProductVO[6] = dictMap.get("ZQ000174").get(perProductVO[6].toString());
				}
				if(perProductVO[7] != null && dictMap.get("ZQ000416") != null && dictMap.get("ZQ000416").get(perProductVO[7].toString()) != null){
					perProductVO[7] = dictMap.get("ZQ000416").get(perProductVO[7].toString());
				}
				if(perProductVO[8] != null && dictMap.get("CD020002") != null && dictMap.get("CD020002").get(perProductVO[8].toString()) != null){
					perProductVO[8] = dictMap.get("CD020002").get(perProductVO[8].toString());
				}
				if(perProductVO[9] != null && dictMap.get("CD020004") != null && dictMap.get("CD020004").get(perProductVO[9].toString()) != null){
					perProductVO[9] = dictMap.get("CD020004").get(perProductVO[9].toString());
				}
			}
			return perProductVOList;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译客户产品信息业务字典
	 * @param temps
	 * @return
	 */
	public List<CustProductVO> setDictCustProduct(List<CustProductVO> temps) {
		if(temps != null && temps.size() > 0){
			/** 
			 *	prodType 产品类型：ZQ000421		busiChar 业务性质：ZQ000174		prodClass产品分类：ZQ000416	
			 *	prodForm 产品形态：CD020002		prodStat 产品状态：CD020004		
			 */	
			String codeTypes = "ZQ000421,ZQ000174,ZQ000416,CD020002,CD020004";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(CustProductVO custProductVO : temps){
				if(dictMap.get("ZQ000421") != null && dictMap.get("ZQ000421").get(custProductVO.getProdType()) != null){
					custProductVO.setProdType(dictMap.get("ZQ000421").get(custProductVO.getProdType()));
				}
				if(dictMap.get("ZQ000174") != null && dictMap.get("ZQ000174").get(custProductVO.getBusiChar()) != null){
					custProductVO.setBusiChar(dictMap.get("ZQ000174").get(custProductVO.getBusiChar()));
				}
				if(dictMap.get("ZQ000416") != null && dictMap.get("ZQ000416").get(custProductVO.getProdClass()) != null){
					custProductVO.setProdClass(dictMap.get("ZQ000416").get(custProductVO.getProdClass()));
				}
				if(dictMap.get("CD020002") != null && dictMap.get("CD020002").get(custProductVO.getProdForm()) != null){
					custProductVO.setProdForm(dictMap.get("CD020002").get(custProductVO.getProdForm()));
				}
				if(dictMap.get("CD020004") != null && dictMap.get("CD020004").get(custProductVO.getProdStat()) != null){
					custProductVO.setProdStat(dictMap.get("CD020004").get(custProductVO.getProdStat()));
				}
			}
			return temps;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译客户产品信息业务字典
	 * @param temps
	 * @return
	 */
	public List<Object[]> setDictCustProductByListObject(List<Object[]> temps) {
		if(temps != null && temps.size() > 0){
			/** 
			 *	prodType 产品类型：ZQ000421		busiChar 业务性质：ZQ000174		prodClass产品分类：ZQ000416	
			 *	prodForm 产品形态：CD020002		prodStat 产品状态：CD020004		
			 */	
			String codeTypes = "ZQ000421,ZQ000174,ZQ000416,CD020002,CD020004";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(Object[] custProductVO : temps){
				if(custProductVO[2] != null && dictMap.get("ZQ000421") != null && dictMap.get("ZQ000421").get(custProductVO[2].toString()) != null){
					custProductVO[2] = dictMap.get("ZQ000421").get(custProductVO[2].toString());
				}
				if(custProductVO[5] != null && dictMap.get("ZQ000174") != null && dictMap.get("ZQ000174").get(custProductVO[5].toString()) != null){
					custProductVO[5] = dictMap.get("ZQ000174").get(custProductVO[5].toString());
				}
				if(custProductVO[6] != null && dictMap.get("ZQ000416") != null && dictMap.get("ZQ000416").get(custProductVO[6].toString()) != null){
					custProductVO[6] = dictMap.get("ZQ000416").get(custProductVO[6].toString());
				}
				if(custProductVO[7] != null && dictMap.get("CD020002") != null && dictMap.get("CD020002").get(custProductVO[7].toString()) != null){
					custProductVO[7] = dictMap.get("CD020002").get(custProductVO[7].toString());
				}
				if(custProductVO[8] != null && dictMap.get("CD020004") != null && dictMap.get("CD020004").get(custProductVO[8].toString()) != null){
					custProductVO[8] = dictMap.get("CD020004").get(custProductVO[8].toString());
				}
			}
			return temps;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译机构客户分析信息业务字典
	 * @param orgRiskVOList
	 * @return
	 */
	public List<OrgRiskVO> setDictOrgRisk(List<OrgRiskVO> orgRiskVOList) {
		if(orgRiskVOList != null && orgRiskVOList.size() > 0){
			/** 
			 *	orgGradeType 机构客户等级类型：ZQ004060		industryPosition 行业地位：ZQ000215		isTop500 500强标识：ZQ000155	
			 *	manageLevel 管理水平：ZQ000156		innerLindeCustGrade 各条线内的客户等级：ZQ000157	orgGrade 法人客户等级：CD010022
			 *	orgMarktingGrade 法人客户分层营销等级：	ZQ000160	guarantyGrade 担保公司等级：ZQ000158		vipGrade VIP等级：ZQ000021
			 *	occuIndustryType 规模性行业分类：ZQ000106		innerEnvIndustryType 企业内部环境行业分类：ZQ000107		
			 *	orgGrade 机构客户等级 ZQ000406
			 */	
			String codeTypes = "ZQ004060,ZQ000215,ZQ000155,ZQ000156,ZQ000157,CD010022,ZQ000160,ZQ000158,ZQ000021,ZQ000106,ZQ000107,ZQ000406";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(OrgRiskVO orgRiskVO : orgRiskVOList){
				if(dictMap.get("ZQ004060") != null && dictMap.get("ZQ004060").get(orgRiskVO.getOrgGradeType()) != null){
					orgRiskVO.setOrgGradeType(dictMap.get("ZQ004060").get(orgRiskVO.getOrgGradeType()));
				}
				if(dictMap.get("ZQ000406") != null && dictMap.get("ZQ000406").get(orgRiskVO.getOrgGrade()) != null){
					orgRiskVO.setOrgGrade(dictMap.get("ZQ000406").get(orgRiskVO.getOrgGrade()));
				}
				if(dictMap.get("ZQ000215") != null && dictMap.get("ZQ000215").get(orgRiskVO.getIndustryPosition()) != null){
					orgRiskVO.setIndustryPosition(dictMap.get("ZQ000215").get(orgRiskVO.getIndustryPosition()));
				}
				if(dictMap.get("ZQ000155") != null && dictMap.get("ZQ000155").get(orgRiskVO.getIsTop500()) != null){
					orgRiskVO.setIsTop500(dictMap.get("ZQ000155").get(orgRiskVO.getIsTop500()));
				}
				if(dictMap.get("ZQ000156") != null && dictMap.get("ZQ000156").get(orgRiskVO.getManageLevel()) != null){
					orgRiskVO.setManageLevel(dictMap.get("ZQ000156").get(orgRiskVO.getManageLevel()));
				}
				if(dictMap.get("ZQ000157") != null && dictMap.get("ZQ000157").get(orgRiskVO.getInnerLindeCustGrade()) != null){
					orgRiskVO.setInnerLindeCustGrade(dictMap.get("ZQ000157").get(orgRiskVO.getInnerLindeCustGrade()));
				}
				if(dictMap.get("CD010022") != null && dictMap.get("CD010022").get(orgRiskVO.getOrgGrade()) != null){
					orgRiskVO.setOrgGrade(dictMap.get("CD010022").get(orgRiskVO.getOrgGrade()));
				}
				if(dictMap.get("ZQ000160") != null && dictMap.get("ZQ000160").get(orgRiskVO.getOrgMarktingGrade()) != null){
					orgRiskVO.setOrgMarktingGrade(dictMap.get("ZQ000160").get(orgRiskVO.getOrgMarktingGrade()));
				}
				if(dictMap.get("ZQ000158") != null && dictMap.get("ZQ000158").get(orgRiskVO.getGuarantyGrade()) != null){
					orgRiskVO.setGuarantyGrade(dictMap.get("ZQ000158").get(orgRiskVO.getGuarantyGrade()));
				}
				if(dictMap.get("ZQ000021") != null && dictMap.get("ZQ000021").get(orgRiskVO.getVipGrade()) != null){
					orgRiskVO.setVipGrade(dictMap.get("ZQ000021").get(orgRiskVO.getVipGrade()));
				}
				if(dictMap.get("ZQ000106") != null && dictMap.get("ZQ000106").get(orgRiskVO.getOccuIndustryType()) != null){
					orgRiskVO.setOccuIndustryType(dictMap.get("ZQ000106").get(orgRiskVO.getOccuIndustryType()));
				}
				if(dictMap.get("ZQ000107") != null && dictMap.get("ZQ000107").get(orgRiskVO.getInnerEnvIndustryType()) != null){
					orgRiskVO.setInnerEnvIndustryType(dictMap.get("ZQ000107").get(orgRiskVO.getInnerEnvIndustryType()));
				}
			}
			return orgRiskVOList;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译机构客户分析信息业务字典
	 * @param orgRiskVOList
	 * @return
	 */
	public List<Object[]> setDictOrgRiskByListObject(List<Object[]> orgRiskVOList) {
		if(orgRiskVOList != null && orgRiskVOList.size() > 0){
			/** 
			 *	orgGradeType 机构客户等级类型：ZQ000232		industryPosition 行业地位：ZQ000215		isTop500 500强标识：ZQ000155	
			 *	manageLevel 管理水平：ZQ000156		innerLindeCustGrade 各条线内的客户等级：ZQ000157	orgGrade 法人客户等级：CD010022
			 *	orgMarktingGrade 法人客户分层营销等级：	ZQ000160	guarantyGrade 担保公司等级：ZQ000158		vipGrade VIP等级：ZQ000021
			 *	occuIndustryType 规模性行业分类：ZQ000106		innerEnvIndustryType 企业内部环境行业分类：ZQ000107			
			 *	orgGrade 机构客户等级 ZQ000406
			 */	
			String codeTypes = "ZQ000232,ZQ000215,ZQ000155,ZQ000156,ZQ000157,CD010022,ZQ000160,ZQ000158,ZQ000021,ZQ000106,ZQ000107,ZQ000406";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(Object[] orgRiskVO : orgRiskVOList){
				if(orgRiskVO[2] != null && dictMap.get("ZQ000232") != null && dictMap.get("ZQ000232").get(orgRiskVO[2].toString()) != null){
					orgRiskVO[2] = dictMap.get("ZQ000232").get(orgRiskVO[2].toString());
				}
				if(orgRiskVO[3] != null && dictMap.get("ZQ000406") != null && dictMap.get("ZQ000406").get(orgRiskVO[3].toString()) != null){
					orgRiskVO[3] = dictMap.get("ZQ000406").get(orgRiskVO[3].toString());
				}
			}
			return orgRiskVOList;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译个人客户分析信息业务字典
	 * @param orgRiskVOList
	 * @return
	 */
	public List<PerRiskVO> setDictPerRisk(List<PerRiskVO> perRiskVOList) {
		if(perRiskVOList != null && perRiskVOList.size() > 0){
			/** 
			 *  grade 个人客户等级：ZQ000233
			 *	gradeType 个人客户等级类型：ZQ000232		assetGrade 资产等级：CD010046		serviceStarLevel 个人客户服务星级：CD010047	
			 *	amlRiskGrade 反洗钱风险等级：CD010048		loanRiskGrade 信贷风险等级：CD010055		ebankGrade 个人网银级别：ZQ000130
			 *	ebankKind 个人网银类别：	CD010049	mobileBankKind 个人手机银行类别：CD010050		vipGrade VIP等级：ZQ000021
			 *	creditGrade 信用等级：ZQ000045		ruralAssetGrade 农金资产等级分类：ZQ000132		
			 */	
			String codeTypes = "ZQ000233,ZQ000232,CD010046,CD010047,ZQ000154,CD010048,CD010055,ZQ000130,CD010049,CD010050,ZQ000021,ZQ000045,ZQ000132";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(PerRiskVO perRiskVO : perRiskVOList){
				if(dictMap.get("ZQ000232") != null && dictMap.get("ZQ000232").get(perRiskVO.getGradeType()) != null){
					perRiskVO.setGradeType(dictMap.get("ZQ000232").get(perRiskVO.getGradeType()));
				}
				if(dictMap.get("ZQ000233") != null && dictMap.get("ZQ000233").get(perRiskVO.getGrade()) != null){
					perRiskVO.setGrade(dictMap.get("ZQ000233").get(perRiskVO.getGrade()));
				}
				if(dictMap.get("CD010046") != null && dictMap.get("CD010046").get(perRiskVO.getAssetGrade()) != null){
					perRiskVO.setAssetGrade(dictMap.get("CD010046").get(perRiskVO.getAssetGrade()));
				}
				if(dictMap.get("CD010047") != null && dictMap.get("CD010047").get(perRiskVO.getServiceStarLevel()) != null){
					perRiskVO.setServiceStarLevel(dictMap.get("CD010047").get(perRiskVO.getServiceStarLevel()));
				}
				if(dictMap.get("CD010048") != null && dictMap.get("CD010048").get(perRiskVO.getAmlRiskGrade()) != null){
					perRiskVO.setAmlRiskGrade(dictMap.get("CD010048").get(perRiskVO.getAmlRiskGrade()));
				}
				if(dictMap.get("CD010055") != null && dictMap.get("CD010055").get(perRiskVO.getLoanRiskGrade()) != null){
					perRiskVO.setLoanRiskGrade(dictMap.get("CD010055").get(perRiskVO.getLoanRiskGrade()));
				}
				if(dictMap.get("ZQ000130") != null && dictMap.get("ZQ000130").get(perRiskVO.getEbankGrade()) != null){
					perRiskVO.setEbankGrade(dictMap.get("ZQ000130").get(perRiskVO.getEbankGrade()));
				}
				if(dictMap.get("CD010049") != null && dictMap.get("CD010049").get(perRiskVO.getEbankKind()) != null){
					perRiskVO.setEbankKind(dictMap.get("CD010049").get(perRiskVO.getEbankKind()));
				}
				if(dictMap.get("CD010050") != null && dictMap.get("CD010050").get(perRiskVO.getMobileBankKind()) != null){
					perRiskVO.setMobileBankKind(dictMap.get("CD010050").get(perRiskVO.getMobileBankKind()));
				}
				if(dictMap.get("ZQ000021") != null && dictMap.get("ZQ000021").get(perRiskVO.getVipGrade()) != null){
					perRiskVO.setVipGrade(dictMap.get("ZQ000021").get(perRiskVO.getVipGrade()));
				}
				if(dictMap.get("ZQ000045") != null && dictMap.get("ZQ000045").get(perRiskVO.getCreditGrade()) != null){
					perRiskVO.setCreditGrade(dictMap.get("ZQ000045").get(perRiskVO.getCreditGrade()));
				}
				if(dictMap.get("ZQ000132") != null && dictMap.get("ZQ000132").get(perRiskVO.getRuralAssetGrade()) != null){
					perRiskVO.setRuralAssetGrade(dictMap.get("ZQ000132").get(perRiskVO.getRuralAssetGrade()));
				}
			}
			return perRiskVOList;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译个人客户分析信息业务字典
	 * @param orgRiskVOList
	 * @return
	 */
	public List<Object[]> setDictPerRiskByListObject(List<Object[]> perRiskVOList) {
		if(perRiskVOList != null && perRiskVOList.size() > 0){
			/** 
			 *  grade 个人客户等级：ZQ000233
			 *	gradeType 个人客户等级类型：ZQ000232		assetGrade 资产等级：CD010046		serviceStarLevel 个人客户服务星级：CD010047	
			 *	amlRiskGrade 反洗钱风险等级：CD010048		loanRiskGrade 信贷风险等级：CD010055		ebankGrade 个人网银级别：ZQ000130
			 *	ebankKind 个人网银类别：	CD010049	mobileBankKind 个人手机银行类别：CD010050		vipGrade VIP等级：ZQ000021
			 *	creditGrade 信用等级：ZQ000045		ruralAssetGrade 农金资产等级分类：ZQ000132		
			 */	
			String codeTypes = "ZQ000233,ZQ000232,CD010046,CD010047,ZQ000154,CD010048,CD010055,ZQ000130,CD010049,CD010050,ZQ000021,ZQ000045,ZQ000132";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(Object[] perRiskVO : perRiskVOList){
				if(perRiskVO[2] != null && dictMap.get("ZQ000232") != null && dictMap.get("ZQ000232").get(perRiskVO[2].toString()) != null){
					perRiskVO[2] = dictMap.get("ZQ000232").get(perRiskVO[2].toString());
				}
				if(perRiskVO[3] != null && dictMap.get("ZQ000233") != null && dictMap.get("ZQ000233").get(perRiskVO[3].toString()) != null){
					perRiskVO[3] = dictMap.get("ZQ000233").get(perRiskVO[3].toString());
				}
			}
			return perRiskVOList;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译客户关联信息业务字典
	 * @param perRelativeVOList
	 * @return
	 */
	public List<PerRelativeVO> setDictPerRelative(List<PerRelativeVO> perRelativeVOList) {
		if(perRelativeVOList != null && perRelativeVOList.size() > 0){
			String columns = "CUST_REL_TYPE,CUST_REL_STAT";
			String attr = columns.replaceAll("_", "");
			String[] attrs = attr.split(","); 
			String tableName = "M_CI_CUSTREL";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMapByColumns(tableName,columns);
			for(PerRelativeVO perRelativeVO : perRelativeVOList){
				if(dictMap.get(attrs[0].toString()) != null && dictMap.get(attrs[0].toString()).get(perRelativeVO.getRelType()) != null){
					perRelativeVO.setRelType(dictMap.get(attrs[0].toString()).get(perRelativeVO.getRelType()));
				}
				if(dictMap.get(attrs[1].toString()) != null && dictMap.get(attrs[1].toString()).get(perRelativeVO.getRelStat()) != null){
					perRelativeVO.setRelStat(dictMap.get(attrs[1].toString()).get(perRelativeVO.getRelStat()));
				}
			}
			return perRelativeVOList;
		} else {
			return null;
		}
	}
	
	
	/**
	 * 转译客户关联信息业务字典
	 * @param perRelativeVOList
	 * @return
	 */
	public List<Object[]> setDictPerRelativeByListObject(List<Object[]> perRelativeVOList) {
		if(perRelativeVOList != null && perRelativeVOList.size() > 0){
			/** 
			 *  relType 客户间关系类型：ZQ000042
			 *	relStat 客户间关系状态：ZQ000245		
			 */	
			String codeTypes = "ZQ000042,ZQ000245";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(Object[] perRelativeVO : perRelativeVOList){
				if(perRelativeVO[4] != null && dictMap.get("ZQ000042") != null && dictMap.get("ZQ000042").get(perRelativeVO[4].toString()) != null){
					perRelativeVO[4] = dictMap.get("ZQ000042").get(perRelativeVO[4].toString());
				}
				if(perRelativeVO[5] != null && dictMap.get("ZQ000245") != null && dictMap.get("ZQ000245").get(perRelativeVO[5].toString()) != null){
					perRelativeVO[5] = dictMap.get("ZQ000245").get(perRelativeVO[5].toString());
				}
			}
			return perRelativeVOList;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译客户基本信息业务字典
	 * @param perBasicVOList
	 * @return
	 */
	public List<PerBasicVO> setDictPerBasic(List<PerBasicVO> perBasicVOList) {
		if(perBasicVOList != null && perBasicVOList.size() > 0){
			/** 
			 *	gender 性别：CD010031		citizenship 国家代码：CD000005		nationality 民族：CD010036
			 *	marriage 婚姻状况：CD010032		career 职业：CD010041		duty 职务：ZQ000003
			 *	highestDegree 学位：CD010057	highestSchooling 学历：CD010033		isEmployee 是否本行职工：ZQ000244
			 *	isImportantCust 是否重要客户：ZQ000265	lifecycleStatType 生命周期状态类型：ZQ000201	
			 */	
			String codeTypes = "CD010031,CD000005,CD010036,CD010032,CD010041,ZQ000003,CD010057,CD010033,ZQ000244,ZQ000265,ZQ000201";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(PerBasicVO perBasicVO : perBasicVOList){
				if(dictMap.get("CD010031") != null && dictMap.get("CD010031").get(perBasicVO.getGender()) != null){
					perBasicVO.setGender(dictMap.get("CD010031").get(perBasicVO.getGender()));
				}
				if(dictMap.get("CD000005") != null && dictMap.get("CD000005").get(perBasicVO.getCitizenship()) != null){
					perBasicVO.setCitizenship(dictMap.get("CD000005").get(perBasicVO.getCitizenship()));
				}
				if(dictMap.get("CD010036") != null && dictMap.get("CD010036").get(perBasicVO.getNationality()) != null){
					perBasicVO.setNationality(dictMap.get("CD010036").get(perBasicVO.getNationality()));
				}
				if(dictMap.get("CD010032") != null && dictMap.get("CD010032").get(perBasicVO.getMarriage()) != null){
					perBasicVO.setMarriage(dictMap.get("CD010032").get(perBasicVO.getMarriage()));
				}
				if(dictMap.get("CD010041") != null && dictMap.get("CD010041").get(perBasicVO.getCareer()) != null){
					perBasicVO.setCareer(dictMap.get("CD010041").get(perBasicVO.getCareer()));
				}
				if(dictMap.get("ZQ000003") != null && dictMap.get("ZQ000003").get(perBasicVO.getDuty()) != null){
					perBasicVO.setDuty(dictMap.get("ZQ000003").get(perBasicVO.getDuty()));
				}
				if(dictMap.get("CD010057") != null && dictMap.get("CD010057").get(perBasicVO.getHighestDegree()) != null){
					perBasicVO.setHighestDegree(dictMap.get("CD010057").get(perBasicVO.getHighestDegree()));
				}
				if(dictMap.get("CD010033") != null && dictMap.get("CD010033").get(perBasicVO.getHighestSchooling()) != null){
					perBasicVO.setHighestSchooling(dictMap.get("CD010033").get(perBasicVO.getHighestSchooling()));
				}
				if(dictMap.get("ZQ000244") != null && dictMap.get("ZQ000244").get(perBasicVO.getIsEmployee()) != null){
					perBasicVO.setIsEmployee(dictMap.get("ZQ000244").get(perBasicVO.getIsEmployee()));
				}
				if(dictMap.get("ZQ000265") != null && dictMap.get("ZQ000265").get(perBasicVO.getIsImportantCust()) != null){
					perBasicVO.setIsImportantCust(dictMap.get("ZQ000265").get(perBasicVO.getIsImportantCust()));
				}
				if(dictMap.get("ZQ000201") != null && dictMap.get("ZQ000201").get(perBasicVO.getLifecycleStatType()) != null){
					perBasicVO.setLifecycleStatType(dictMap.get("ZQ000201").get(perBasicVO.getLifecycleStatType()));
				}
			}
			return perBasicVOList;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译客户基本信息业务字典
	 * @param perBasicVOList
	 * @return
	 */
	public List<Object[]> setDictPerBasicByListObject(List<Object[]> perBasicVOList) {
		if(perBasicVOList != null && perBasicVOList.size() > 0){
			/** 
			 *	gender 性别：CD010031		citizenship 国家代码：CD000005		nationality 民族：CD010036
			 *	marriage 婚姻状况：CD010032		career 职业：CD010041		duty 职务：ZQ000003
			 *	highestDegree 学位：CD010057	highestSchooling 学历：CD010033		isEmployee 是否本行职工：ZQ000244
			 *	isImportantCust 是否重要客户：ZQ000265		lifecycleStatType 生命周期状态类型：ZQ000201
			 */	
			String codeTypes = "CD010031,CD000005,CD010036,CD010032,CD010041,ZQ000003,CD010057,CD010033,ZQ000244,ZQ000265,ZQ000201";
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMap(codeTypes);
			for(Object[] perBasicVO : perBasicVOList){
				if(perBasicVO[2] != null && dictMap.get("CD010031") != null && dictMap.get("CD010031").get(perBasicVO[2].toString()) != null){
					perBasicVO[2] = dictMap.get("CD010031").get(perBasicVO[2].toString());
				}
				if(perBasicVO[4] != null && dictMap.get("CD000005") != null && dictMap.get("CD000005").get(perBasicVO[4].toString()) != null){
					perBasicVO[4] = dictMap.get("CD000005").get(perBasicVO[4].toString());
				}
				if(perBasicVO[5] != null && dictMap.get("CD010036") != null && dictMap.get("CD010036").get(perBasicVO[5].toString()) != null){
					perBasicVO[5] = dictMap.get("CD010036").get(perBasicVO[5].toString());
				}
				if(perBasicVO[6] != null && dictMap.get("CD010032") != null && dictMap.get("CD010032").get(perBasicVO[6].toString()) != null){
					perBasicVO[6] = dictMap.get("CD010032").get(perBasicVO[6].toString());
				}
				if(perBasicVO[7] != null && dictMap.get("CD010041") != null && dictMap.get("CD010041").get(perBasicVO[7].toString()) != null){
					perBasicVO[7] = dictMap.get("CD010041").get(perBasicVO[7].toString());
				}
				if(perBasicVO[8] != null && dictMap.get("ZQ000003") != null && dictMap.get("ZQ000003").get(perBasicVO[8].toString()) != null){
					perBasicVO[8] = dictMap.get("ZQ000003").get(perBasicVO[8].toString());
				}
				if(perBasicVO[9] != null && dictMap.get("CD010057") != null && dictMap.get("CD010057").get(perBasicVO[9].toString()) != null){
					perBasicVO[9] = dictMap.get("CD010057").get(perBasicVO[9].toString());
				}
				if(perBasicVO[10] != null && dictMap.get("CD010033") != null && dictMap.get("CD010033").get(perBasicVO[10].toString()) != null){
					perBasicVO[10] = dictMap.get("CD010033").get(perBasicVO[10].toString());
				}
				if(perBasicVO[11] != null && dictMap.get("ZQ000244") != null && dictMap.get("ZQ000244").get(perBasicVO[11].toString()) != null){
					perBasicVO[11] = dictMap.get("ZQ000244").get(perBasicVO[11].toString());
				}
				if(perBasicVO[12] != null && dictMap.get("ZQ000265") != null && dictMap.get("ZQ000265").get(perBasicVO[12].toString()) != null){
					perBasicVO[12] = dictMap.get("ZQ000265").get(perBasicVO[12].toString());
				}
				if(perBasicVO[13] != null && dictMap.get("ZQ000201") != null && dictMap.get("ZQ000201").get(perBasicVO[13].toString()) != null){
					perBasicVO[13] = dictMap.get("ZQ000201").get(perBasicVO[13].toString());
				}
			}
			return perBasicVOList;
		} else {
			return null;
		}
	}
	
	/**
	 * 转译个人客户维度统计信息
	 * @param returnListMaps
	 * @return
	 */
	public List<Map<String, String>> setDictRptPersonInfoDetail(List<Map<String, String>> returnListMaps) {
		String columns = "RPT_MONTH,RPT_TYPE,RPT_SIGN1,RPT_SIGN2,CREATE_BRANCH_NO,BRCNAME,CUST_SUM";
		String tableName = "RPT_PERSON_INFO_DETAIL";
		return getListMap(columns, tableName, returnListMaps);
	}
	
	/**
	 * 转译对公AUM值分布情况信息
	 * @param returnListMaps
	 * @return
	 */
	public List<Map<String, String>> setDictRptCustAcctChangeInfo(List<Map<String, String>> returnListMaps) {
		String columns = "RPT_DATE,CUST_ACCT_STS,CREATE_BRANCH_NO,BRANCH_NAME,CUST_NO,CUST_NAME,DEPOSIT_BAL,DEPOSIT_BAL_DAY_AVG,CREATE_TIME";
		String tableName = "RPT_CUST_ACCT_CHANGE_INFO";
		return getListMap(columns, tableName, returnListMaps);
	}	
	
	public List<Map<String, String>> getListMap(String columns, String tableName, List<Map<String, String>> returnListMaps){
		if(returnListMaps != null && returnListMaps.size() > 0) {
			Map<String, Map<String, String>> dictMap = this.codeUtil.getDictListMapByColumns(tableName,columns);
			for(Map<String, String> returnMap : returnListMaps){
				Set<Entry<String, String>> returnMapEntries = returnMap.entrySet();
				Iterator<Entry<String, String>> returnMapIterator = returnMapEntries.iterator();
				while(returnMapIterator.hasNext()){
					Map.Entry<String, String> returnMapEntry = (Entry<String, String>) returnMapIterator.next();
					String key = returnMapEntry.getKey();
					String value = returnMapEntry.getValue();
					if(dictMap.get(key.toUpperCase()) != null && dictMap.get(key.toUpperCase()).get(value) != null){
						String valueName = dictMap.get(key.toUpperCase()).get(value);
						if(valueName == null || valueName.length() == 0){
							valueName = value;
						}
						returnMap.put(key, valueName);
					}
				}
			}
			return returnListMaps;
		} else {
			return null;
		}
	}
}
