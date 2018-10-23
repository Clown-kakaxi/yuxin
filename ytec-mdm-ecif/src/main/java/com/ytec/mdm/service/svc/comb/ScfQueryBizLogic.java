package com.ytec.mdm.service.svc.comb;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ytec.fubonecif.domain.MCiAddress;
import com.ytec.fubonecif.domain.MCiBelongBranch;
import com.ytec.fubonecif.domain.MCiBelongManager;
import com.ytec.fubonecif.domain.MCiContmeth;
import com.ytec.fubonecif.domain.MCiCrossindex;
import com.ytec.fubonecif.domain.MCiCustomer;
import com.ytec.fubonecif.domain.MCiGroupInfo;
import com.ytec.fubonecif.domain.MCiGroupMember;
import com.ytec.fubonecif.domain.MCiIdentifier;
import com.ytec.fubonecif.domain.MCiOrg;
import com.ytec.fubonecif.domain.MCiOrgExecutiveinfo;
import com.ytec.fubonecif.domain.MCiPerson;
import com.ytec.mdm.base.bo.EcifData;
import com.ytec.mdm.base.bo.ErrorCode;
import com.ytec.mdm.base.dao.JPABaseDAO;
import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.base.util.SpringContextUtils;
import com.ytec.mdm.integration.transaction.facade.IEcifBizLogic;
import com.ytec.mdm.server.common.BusinessCfg;
/**
 * @类描述：供应链查询
 * @author
 *
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ScfQueryBizLogic implements IEcifBizLogic{
	protected static Logger log = LoggerFactory.getLogger(ScfQueryBizLogic.class);
	private JPABaseDAO baseDAO;
	
	@SuppressWarnings("unused")
	@Override
	public void process(EcifData ecifData) throws Exception {
			baseDAO = (JPABaseDAO) SpringContextUtils.getBean("baseDAO");
			Element body=ecifData.getBodyNode();//获取节点
			String txCode = body.element("txCode").getTextTrim();// 获取交易编号
			String txName = body.element("txName").getTextTrim();// 获取交易名称
			//String authType = body.element("authType").getTextTrim();// 获取权限控制类型
			//String authCode = body.element("authCode").getTextTrim();// 获取权限控制代码
			String identType=body.element("identType").getTextTrim();
			String identNo=body.element("identNo").getTextTrim();
			
			SimpleDateFormat df8 = new SimpleDateFormat("yyyyMMdd");
			Object obj = null;
			String custId=null;
			MCiCustomer customer = new MCiCustomer();
			MCiOrg org=new MCiOrg();
			MCiAddress address=new MCiAddress();
			MCiIdentifier ident=new MCiIdentifier();
			MCiOrgExecutiveinfo executiveInfo=new MCiOrgExecutiveinfo();
			MCiBelongManager belongManager=new MCiBelongManager();
			MCiBelongBranch belongBranch=new MCiBelongBranch();
			MCiGroupMember groupMember=new MCiGroupMember();
			MCiGroupInfo groupInfo=new MCiGroupInfo();
			Map<String, String> map = new HashMap<String, String>();
			try{
				obj=queryIdentEntiry(identType, identNo, "MCiIdentifier");//通过证件类型和证件号码查询证件信息表
				if(obj != null){
					ident=(MCiIdentifier)obj;
					custId=ident.getCustId();
					map.put("custId", custId);
				}else {
					String msg = String.format("查询客户证件信息错误，通过客户证件类型(%s)与证件号码(%s)未查询到证件信息 ", identType, identNo);
					log.error("{},{}", msg + "交易编码是：" + txCode);
					ecifData.setStatus(ErrorCode.WRN_NONE_FOUND.getCode(), "未查到任何记录");
					ecifData.setSuccess(true);
					return;
				}
				Object objCustomer = queryEntiry(custId, "MCiCustomer");
				if (objCustomer != null) {
					customer = (MCiCustomer) objCustomer;
					String coreNo = customer.getCoreNo();
					String custName=customer.getCustName();
					Map<String, String> values = new HashMap<String, String>();
					values.put("custId", custId);
					values.put("srcSysNo", "LN");
					MCiCrossindex crossIndex;
					try {
						crossIndex = (MCiCrossindex) baseDAO.findUniqueWithNameParam("from MCiCrossindex where custId=:custId and srcSysNo=:srcSysNo ", values);
						if(crossIndex != null){
							String lnCustNo=crossIndex.getSrcSysCustNo();
							map.put("lnCustNo", lnCustNo);
						}
					} catch (Exception e) {
						String msg = String.format("查询客户信息错误，通过ECIF客户号(%s)查询交叉索引表信贷客户号错误", custId);
						log.error(msg);
						log.error("{}", e);
						//throw new Exception(msg + e.getLocalizedMessage());
					}	
					map.put("coreNo", coreNo);
					map.put("custName", custName);
					map.put("inoutFlag", customer.getInoutFlag());
				}else{
					String msg = String.format("查询客户信息错误，通过ECIF客户号(%s)查询客户表信息空", custId);
					log.warn(msg);
				}
				String transDate=df8.format(new Date());//交易日期
				map.put("transDate", transDate);
				Object objOrg= queryEntiry(custId, "MCiOrg");
				if(objOrg != null){
					org=(MCiOrg)objOrg;
					map.put("lncustp",org.getLncustp());
				}
				List<MCiAddress> addrList=new ArrayList<MCiAddress>();//地址信息集合
				List<Object> addressList=returnEntiryAsList(custId, "MCiAddress");
				if(addressList != null && addressList.size() > 0){
					for(int i=0;i<addressList.size();i++){
						address=(MCiAddress) addressList.get(i);
						if(address.getAddrType() != null && "01".equals(address.getAddrType())){
							MCiAddress newAddr=new MCiAddress();//建立临时对象
							newAddr.setAddrType(address.getAddrType());
							newAddr.setAddr(address.getAddr());
							newAddr.setZipcode(address.getZipcode());
							addrList.add(newAddr);//放入集合
						}
						if(address.getAddrType() != null && "07".equals(address.getAddrType())){
							MCiAddress newAddr=new MCiAddress();
							newAddr.setAddrType(address.getAddrType());
							newAddr.setAddr(address.getAddr());
							newAddr.setZipcode(address.getZipcode());
							addrList.add(newAddr);
						}
						if(address.getAddrType() != null && "09".equals(address.getAddrType())){
							MCiAddress newAddr=new MCiAddress();
							newAddr.setAddrType(address.getAddrType());
							newAddr.setAddr(address.getAddr());
							newAddr.setZipcode(address.getZipcode());
							addrList.add(newAddr);
						}
					}
				}
//				String sql="select t.linkman_type,t.linkman_name,t.office_tel,t.mobile from acrm_f_ci_org_executiveinfo@fbcrm t where t.org_cust_id=? and and t.linkman_type in ('2','21','22','23')";//查询CRM数据库
//				List<Object[]> orgLinkmanInfo=baseDAO.findByNativeSQLWithIndexParam(sql, custId);
//				if(orgLinkmanInfo != null && orgLinkmanInfo.size() > 0){
//				}
				Object linkObject = queryAddress(custId, "linkmanType", "21", "MCiOrgExecutiveinfo");
				if (linkObject != null) {
					executiveInfo = (MCiOrgExecutiveinfo) linkObject;
					map.put("linkmanType", executiveInfo.getLinkmanType());
					map.put("linkmanName", executiveInfo.getLinkmanName());
					map.put("telephone", executiveInfo.getOfficeTel()== null ? executiveInfo.getMobile() : executiveInfo.getOfficeTel());
				}
				
				Object objGroupMember=queryEntiry(custId,"MCiGroupMember");
				if(objGroupMember != null){
					groupMember=(MCiGroupMember) objGroupMember;
					String groupNo=groupMember.getGroupNo();//获取集团编号
					map.put("groupNo", groupNo);
//					List<MCiGroupInfo> groupInfoList=baseDAO.findWithIndexParam("from MCiGroupInfo t where t.groupNo=?", groupNo);
					groupInfo=(MCiGroupInfo) baseDAO.findUniqueWithIndexParam("from MCiGroupInfo t where t.groupNo=?", groupNo);
//					if(groupInfoList != null && groupInfoList.size() > 0){
//						for(int i=0;i<groupInfoList.size();i++){
//							groupInfo=groupInfoList.get(i);
//						}
//					}
					if(groupInfo != null && !"".equals(groupInfo)){
						map.put("groupName", groupInfo.getGroupName());
					}
					
				}
				
				Object objManager=queryEntiry(custId,"MCiBelongManager");
				if(objManager != null){
					belongManager=(MCiBelongManager)objManager;
					String custManagerNo=belongManager.getCustManagerNo();
					map.put("custManagerNo", belongManager.getCustManagerNo());
					String sql="select t.USER_NAME from ADMIN_AUTH_ACCOUNT@FBCRM t where t.ACCOUNT_NAME='"+custManagerNo+"'";//查询CRM数据库
					List<Object> belongManagerInfo=baseDAO.findByNativeSQLWithIndexParam(sql, null);
					if(belongManagerInfo != null && belongManagerInfo.size() > 0){
						for(Object objArr : belongManagerInfo){
							String userName=(String)objArr;
							map.put("userName", userName);
						}
					}
				}else{
						String msg = String.format("查询客户(%s)归属客户经理失败", custId);
						log.error(msg);
				}
				Object objBranch=queryEntiry(custId, "MCiBelongBranch");
				if(objBranch != null){
					belongBranch=(MCiBelongBranch) objBranch;
					String orgId=belongBranch.getBelongBranchNo();
					map.put("belongBranchNo", orgId);
					String sql="select t.ORG_NAME from ADMIN_AUTH_ORG@FBCRM t where t.ORG_ID='"+orgId+"'";//查询CRM数据库
					List<Object> belongBranchInfo=baseDAO.findByNativeSQLWithIndexParam(sql, null);
					if(belongBranchInfo != null && belongBranchInfo.size() > 0){
						for(Object objArr : belongBranchInfo){
							String orgName=(String)objArr;
							map.put("orgName", orgName);
						}
					}
				}else{
					String msg = String.format("查询客户(%s)归属机构失败", custId);
					log.error(msg);
				}
				//拼接返回报文体
				Element responseEle = DocumentHelper.createElement(MdmConstants.MSG_RESPONSE_BODY);
				Element bodyCustomer=responseEle.addElement("customer");
				bodyCustomer.addElement("custId").setText(map.get("custId") == null ? "" : map.get("custId"));
				bodyCustomer.addElement("transDate").setText(map.get("transDate") == null ? "" : map.get("transDate"));
				bodyCustomer.addElement("coreNo").setText(map.get("coreNo") == null ? "" : map.get("coreNo"));
				bodyCustomer.addElement("lnCustNo").setText(map.get("lnCustNo") == null ? "" : map.get("lnCustNo"));
				bodyCustomer.addElement("identType").setText(identType);
				bodyCustomer.addElement("identNo").setText(identNo);
				bodyCustomer.addElement("inoutFlag").setText(map.get("inoutFlag") == null ? "" : map.get("inoutFlag"));
				bodyCustomer.addElement("lncustp").setText(map.get("lncustp") == null ? "" : map.get("lncustp"));
				bodyCustomer.addElement("custName").setText(map.get("custName") == null ? "" : map.get("custName"));
				
				Element bodyExecutive=responseEle.addElement("orgExecutiveinfo");
				bodyExecutive.addElement("linkmanType").setText(map.get("linkmanType") == null ? "" : map.get("linkmanType"));
				bodyExecutive.addElement("linkmanName").setText(map.get("linkmanName") == null ? "" : map.get("linkmanName"));
				bodyExecutive.addElement("telephone").setText(map.get("telephone") == null ? "" : map.get("telephone"));
				
				if(addrList != null && addrList.size()>0){
					for (int i=0;i<addrList.size();i++) {
						Element bodyAddress = responseEle.addElement("address");
						address=(MCiAddress)addrList.get(i);
						bodyAddress.addElement("addrType").setText(address.getAddrType() == null ? "" : address.getAddrType());
						bodyAddress.addElement("addr").setText(address.getAddr() == null ? "" : address.getAddr());
						bodyAddress.addElement("zipcode").setText(address.getZipcode() == null ? "" : address.getZipcode());
					}
				}
				
				Element bodyGroup=responseEle.addElement("groupInfo");
				bodyGroup.addElement("groupNo").setText(map.get("groupNo") == null ? "" : map.get("groupNo"));
				bodyGroup.addElement("groupName").setText(map.get("groupName") == null ? "" : map.get("groupName"));
				
				Element bodyBranch=responseEle.addElement("belongBranch");
				bodyBranch.addElement("belongBranchNo").setText(map.get("belongBranchNo") == null ? "" : map.get("belongBranchNo"));
				bodyBranch.addElement("orgName").setText(map.get("orgName") == null ? "" : map.get("orgName"));
				
				Element bodyManager=responseEle.addElement("belongManager");
				bodyManager.addElement("custManagerNo").setText(map.get("custManagerNo") == null ? "" : map.get("custManagerNo"));
				bodyManager.addElement("userName").setText(map.get("userName") == null ? "" : map.get("userName"));
				//封装整体报文
				ecifData.setRepNode(responseEle);//!!!
			
			}catch (Exception e) {
				String msg;
				if (e instanceof ParseException) {
					msg = String
							.format("日期/时间(%s)格式不符合规范,转换错误",
							e.getLocalizedMessage()
											.substring(
													e.getLocalizedMessage()
															.indexOf('"'))
											.replace("\"", ""));
					ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
							log.error("{},{}", msg + "交易编码是：" + txCode, e);
					} else if (e instanceof NumberFormatException) {
						msg = String
							.format("数值(%s)格式不符合规范,转换错误",
									e.getLocalizedMessage()
											.substring(
													e.getLocalizedMessage()
															.indexOf('"'))
											.replace("\"", ""));
					ecifData.setStatus(ErrorCode.ERR_CONVERT_ERROR.getCode(), msg);
					log.error("{},{}", msg + "交易编码是：" + txCode, e);
				} else {
					msg = "查询数据失败";
					log.error("{},{}", msg + "交易编码是：" + txCode, e);
					ecifData.setStatus(ErrorCode.ERR_DB_OPER_ERROR.getCode(), msg);
				}
				ecifData.setSuccess(false);
				return;
					}
							
				}
				
	
	public Object queryIdentEntiry(String identType, String identNo,String tableName) {
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// 查询表
		jql.append("FROM " + tableName + " a");
		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" AND a.identType =:identType");
		jql.append(" AND a.identNo =:identNo");
		// 将查询的条件放入到map集合里面
		paramMap.put("identType", identType);
		paramMap.put("identNo", identNo);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) { return result.get(0); }

		return null;

		}
	public Object queryAddress(String custId, String str, String addrType, String simpleNames) throws Exception {

			// 类名
			String simpleName = simpleNames;
			// 获得表名
			String tableName = simpleName;
			// 拼装JQL查询语句
			StringBuffer jql = new StringBuffer();
			Map<String, String> paramMap = new HashMap<String, String>();

			// 查询表
			jql.append("FROM " + tableName + " a");
			// 查询条件
			jql.append(" WHERE 1=1");
			jql.append(" AND a.orgCustId =:custId");
			jql.append(" AND a." + str + " =:" + str + "");
			// 将查询的条件放入到map集合里面
			paramMap.put("custId", custId);
			paramMap.put(str, addrType);
			List result = null;
			result = baseDAO.findWithNameParm(jql.toString(), paramMap);
			if (result != null && result.size() > 0) { return result.get(0); }
			
			return null;
	}
	public Object queryEntiry(String custId,String tableName) {
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// 查询表
		jql.append("FROM " + tableName + " a");
		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId");
		// 将查询的条件放入到map集合里面
		paramMap.put("custId", custId);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null && result.size() > 0) { return result.get(0); }

		return null;

		}
	public List<Object> returnEntiryAsList(String custId,String tableName) {
		// 拼装JQL查询语句
		StringBuffer jql = new StringBuffer();
		Map<String, String> paramMap = new HashMap<String, String>();

		// 查询表
		jql.append("FROM " + tableName + " a");

		// 查询条件
		jql.append(" WHERE 1=1");
		jql.append(" AND a.custId =:custId");
		// 将查询的条件放入到map集合里面
		paramMap.put("custId", custId);
		List result = null;
		result = baseDAO.findWithNameParm(jql.toString(), paramMap);
		if (result != null) { return result; }

		return null;
	}
}
