package com.yuchengtech.bcrm.custview.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.service.OrgSearchService;
import com.yuchengtech.bcrm.customer.model.OcrmFCiBelongHist;
import com.yuchengtech.bcrm.custview.model.OcrmFCiBelongCustmgr;
import com.yuchengtech.bcrm.custview.service.CustBelCustmgrInfoService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * 客户归属客户经理调整	
 */
@SuppressWarnings("serial")
@Action("/cust_bel_custmgr_adjust")
public class CustBelCustmgrAdjustAction extends CommonAction {
	
	@Autowired
	private CustBelCustmgrInfoService custBelCustmgrInfoService;
	@Autowired
	private OrgSearchService orgSearchService;

	@Autowired
	public void init() {
		model = new OcrmFCiBelongCustmgr();
		setCommonService(custBelCustmgrInfoService);
		// 新增修改删除记录是否记录日志,默认为false，不记录日志
		needLog = false;;
	}
	/**
	 * 调整客户经理归属
	 * @return
	 */
	public String adjustCustmgr() {
		//取得前台传过来的参数
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		//str传过来的参数记录格式如：record|record
		String str = request.getParameter("str");
		String str1 = request.getParameter("str1");
		str = str.substring(0, str.length() - 1);
		str1 = str1.substring(0,str1.length() - 1);
		//将传过来的参数转化为数组
		String[] mgrs = str.split("&");
		String[] mgrs1 = str1.split("&");
		for(int i = 0; i < mgrs.length; i++){
			//将传过来的参数转化为数组cusdId,mgrId,mgrName,institutionCode,institutionName,mainType,cId
			//						客户编号，客户经理编号，客户经理名称，机构编号，机构名称，协办类型,cId
			String[] record = mgrs[i].split(",");
			String[] records = mgrs1[i].split(",");
			OcrmFCiBelongCustmgr bo = new OcrmFCiBelongCustmgr();
			OcrmFCiBelongHist to =  new OcrmFCiBelongHist();
			bo.setId(Long.parseLong(record[6]));
			bo.setCustId(record[0]);		//设置客户编号
			bo.setMgrId(record[1]);			//设置归属经理编号
			bo.setMgrName(record[2]);
			bo.setInstitution(record[3]);
			//通过客户经理所在的机构代码查找机构名称
			bo.setInstitutionName(record[4]);	//设置机构名称
			bo.setMainType(record[5]);		//设置主协办类型
			bo.setCheckRight("1");			//设置是否有查看权
			bo.setMaintainRight("1");		//设置是否有维护权
			bo.setAssignUser(auth.getUsername());	//设置分配人
			bo.setAssignUsername(auth.getCname());	//设置分配人名称
			bo.setAssignDate(new Date());			//设置分配日期
            if(!records[5].equals("")&& record[5].equals("3"))//如果为删除一个客户经理，保存之前的信息
            {
            	to.setCustId(records[0]);
            	to.setBeforeMgrId(records[1]);
            	to.setBeforeMgrName(records[2]);
            	to.setBeforeMainType(records[5]);
            	to.setBeforeInstCode(record[3]);
            	to.setBeforeInstName(records[4]);
            	to.setAssignUser(auth.getUsername());
            	to.setAssignUsername(auth.getCname());
            	to.setAssignDate(new Date());
            	custBelCustmgrInfoService.save(to);
            }else if(!records[5].equals("")&&!record[5].equals("3")&&!records[5].equals(record[5]))
            {
               	to.setCustId(records[0]);
            	to.setBeforeMgrId(records[1]);
            	to.setBeforeMgrName(records[2]);
            	to.setBeforeMainType(records[5]);
            	to.setBeforeInstCode(record[3]);
            	to.setBeforeInstName(records[4]);
            	to.setAssignUser(auth.getUsername());
            	to.setAssignUsername(auth.getCname());
            	to.setAssignDate(new Date());
                to.setAfterMgrId(record[1]);
                to.setAfterMgrName(record[2]);
                to.setAfterMainType(record[5]);
                to.setAfterInstCode(record[3]);
                to.setAfterInstName(record[4]);
            	custBelCustmgrInfoService.save(to);

            }else if(records[5].equals("")&&!record[5].equals("3")){
             	to.setCustId(records[0]);
            	to.setAssignUser(auth.getUsername());
            	to.setAssignUsername(auth.getCname());
            	to.setAssignDate(new Date());
                to.setAfterMgrId(record[1]);
                to.setAfterMgrName(record[2]);
                to.setAfterMainType(record[5]);
                to.setAfterInstCode(record[3]);
                to.setAfterInstName(record[4]);
            	custBelCustmgrInfoService.save(to);

            }

            
			if ("3".equals(bo.getMainType())) {
				custBelCustmgrInfoService.remove(bo.getId());
			} else {
				custBelCustmgrInfoService.save(bo);
			}
		}
		return "success";
	}

	/**
	 * 通过获得ID查询客户归属客户经理
	 * 
	 * @return
	 * @throws Exception
	 */
	public HttpHeaders indexPage() throws Exception {
		try {
			AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			//查找该机构下用户（客户经理）
			Map<String,Object> custmgrMap = orgSearchService.SearchOrgUsers(auth.getUnitId(),new HashMap());
			Map<String, Object> custBelMgrMap = null;
			StringBuilder sb = new StringBuilder(
					"select bc from OcrmFCiBelongCustmgr bc where 1=1 ");
			Map<String, Object> values = new HashMap<String, Object>();
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx
					.get(ServletActionContext.HTTP_REQUEST);
			// 获取传过来的参数
			String custId = request.getParameter("cust_id");
//			sb.append(" and bc.custId = " + custId);
			sb.append("and bc.custId = :custId");
			values.put("custId",request.getParameter("cust_id"));
			super.indexPageByJql(sb.toString(), values);
			custBelMgrMap = (Map<String, Object>) this.json.get("json");
			List<OcrmFCiBelongCustmgr> custBelMgrList = (List<OcrmFCiBelongCustmgr>) custBelMgrMap.get("data");
			List custmgrList = (List<OcrmFCiBelongCustmgr>) custmgrMap.get("data");
			List<CustBelMgr> resultList = new ArrayList<CustBelMgr>();
			int count = 0;
			//合并归属客户经理信息和机构客户经理表信息到resultList中
			for (int i = 0; i < custmgrList.size(); i++) {
				CustBelMgr cbm = new CustBelMgr();
				Map res = (Map)custmgrList.get(i);
				String mgrId = (String) res.get("USERID");
				OcrmFCiBelongCustmgr obc = null;
				cbm.custId = custId;
				cbm.mgrId = mgrId;
				cbm.mgrName = (String) res.get("USERNAME");
				cbm.institutionCode = auth.getUnitId();
				cbm.institutionName = auth.getUnitName();
				cbm.mainType = "";
				//该客户经理对同一个客户只能分配一次
				for (int j = 0; j < custBelMgrList.size(); j++) {
					//如果客户经理已经分配了，则查找相应的记录并赋对应的值
					if (mgrId.equals(custBelMgrList.get(j).getMgrId())) {
						obc = custBelMgrList.get(j);
						cbm.mainType = obc.getMainType();
						cbm.cId = obc.getId();
					} 
				}
				count = i + 1;
				resultList.add(cbm);
			}
			//设置为JSON格式的map
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", resultList);
			map.put("count", count);
			Map <String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("json", map);
			this.setJson(resultMap);
			return new DefaultHttpHeaders("success")
		    .disableCaching();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	//客户归属经理
	public class CustBelMgr {
		private Long cId;		//id
		private String custId;	//客户编号
		private String mgrId;	//归属客户经理编号
		private String mgrName;	//归属客户经理名称
		private String institutionCode;	//归属机构编号
		private String institutionName;	//归属机构名称
		private String mainType;		//主协办类型
		public String getCustId() {
			return custId;
		}
		public Long getcId() {
			return cId;
		}
		public void setcId(Long cId) {
			this.cId = cId;
		}
		public void setCustId(String custId) {
			this.custId = custId;
		}
		public String getMgrId() {
			return mgrId;
		}
		public void setMgrId(String mgrId) {
			this.mgrId = mgrId;
		}
		public String getMgrName() {
			return mgrName;
		}
		public void setMgrName(String mgrName) {
			this.mgrName = mgrName;
		}
		public String getInstitutionCode() {
			return institutionCode;
		}
		public void setInstitutionCode(String institutionCode) {
			this.institutionCode = institutionCode;
		}
		public String getInstitutionName() {
			return institutionName;
		}
		public void setInstitutionName(String institutionName) {
			this.institutionName = institutionName;
		}
		public String getMainType() {
			return mainType;
		}
		public void setMainType(String mainType) {
			this.mainType = mainType;
		}
		
	}
}