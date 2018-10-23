package com.yuchengtech.bcrm.customer.customerView.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.customer.customerView.model.AcrmFCiCustomer;
import com.yuchengtech.bcrm.customer.model.ReviewMapping;
import com.yuchengtech.bcrm.model.OcrmFCiCustinfoUphi;
import com.yuchengtech.bcrm.model.OcrmSysLookupItem;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * @description:客户全行查询
 * @author xiebz
 * @data 2014-07-07
 */
@Service
public class CustomerQueryAllNewService extends CommonService {

	private static Logger log = Logger
			.getLogger(CustomerQueryAllNewService.class);

	public CustomerQueryAllNewService() {
		JPABaseDAO<AcrmFCiCustomer, Long> baseDao = new JPABaseDAO<AcrmFCiCustomer, Long>(
				AcrmFCiCustomer.class);
		super.setBaseDAO(baseDao);
	}

	/**
	 * 增加修改历史信息
	 * 
	 * @param jarray
	 *            具体修改项
	 * @param date
	 *            修改日期
	 * @param flag
	 *            修改标识 毫秒级日期long
	 * @param type
	 *            修改类型
	 */
	public void bathsave(JSONArray jarray, Date date, String flag, String type) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		String currenUserId = auth.getUserId();
		@SuppressWarnings("unchecked")
		List<ReviewMapping> list = this.em.createQuery(
				"select c from ReviewMapping c where c.moduleItem='" + type
						+ "' order by c.tableName,c.pageColumn")
				.getResultList();
		if (jarray.size() > 0) {
			for (int i = 0; i < jarray.size(); ++i) {
				JSONObject wa = (JSONObject) jarray.get(i);
				OcrmFCiCustinfoUphi ws = new OcrmFCiCustinfoUphi();
				ws.setCustId(String.valueOf(wa.get("custId")));
				ws.setUpdateItem(wa.get("updateItem") == null ? "" : String
						.valueOf(wa.get("updateItem")));
				ws.setUpdateBeCont(wa.get("updateBeCont") == null ? "" : String
						.valueOf(wa.get("updateBeCont")));
				ws.setUpdateAfCont(wa.get("updateAfCont") == null ? "" : String
						.valueOf(wa.get("updateAfCont")));
				ws.setUpdateAfContView(wa.get("updateAfContView") == null ? ""
						: String.valueOf(wa.get("updateAfContView")));
				ws.setUpdateTableId(wa.get("updateTableId") == null ? ""
						: String.valueOf(wa.get("updateTableId")));
				// 1、文本，2、日期
				ws.setFieldType(wa.get("fieldType") == null ? "1" : String
						.valueOf(wa.get("fieldType")));
				ws.setApprFlag("0");// 0审核中,1审核通过,2审核拒绝
				for (int k = 0; k < list.size(); k++) {
					if ((String.valueOf(wa.get("updateItemEn"))).equals(list
							.get(k).getPageColumn())) {
						ws.setUpdateItemEn(list.get(k).getOriginColumn());
						ws.setUpdateTable(list.get(k).getTableName());
					}
				}
				ws.setUpdateUser(currenUserId);
				ws.setUpdateDate(date);
				ws.setUpdateFlag(flag);
				if (ws.getUpdateTable() == null
						|| "".equals(ws.getUpdateTable())
						|| ws.getUpdateItemEn() == null
						|| "".equals(ws.getUpdateItemEn())) {
					log.warn(type + "-----字段映射未找到(忽略该字段): "
							+ String.valueOf(wa.get("updateItemEn")));
					continue;
				}
				super.save(ws);
			}
		}
	}
	/**
	 * 证件类型1
	 * @param fcodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getIdentType(String fcodes) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		String jql = "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and it.fCode != 'X3'";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fLookupId", "XD000361");
		List<OcrmSysLookupItem> items = this.findByJql(jql, map);
		
		if (items != null && !items.isEmpty()) {
			for (OcrmSysLookupItem item : items) {
				Map<String, Object> retmap = new HashMap<String, Object>();
				retmap.put("key", item.getFCode());
				retmap.put("value", item.getFValue());
				
				list.add(retmap);
			}
		}
		return list;
	}
	/**
	 * 证件类型2
	 * @param fcodes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getIdentType2(String fCode) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String jql = "";
		if(fCode != null){
			if(fCode.equals("X2")){
				jql += "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and it.fCode in ('6','X3','X24')";
			}else if(fCode.equals("X1")){
				jql += "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and it.fCode in ('5')";
			}else{ 	 	
				jql += "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId and it.fCode not in('6','X3','X24','5')";
			}
		}else{
			jql += "select it from OcrmSysLookupItem it where it.fLookupId = :fLookupId";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fLookupId", "XD000362");
		List<OcrmSysLookupItem> items = this.findByJql(jql, map);
		
		if (items != null && !items.isEmpty()) {
			for (OcrmSysLookupItem item : items) {
				Map<String, Object> retmap = new HashMap<String, Object>();
				retmap.put("key", item.getFCode());
				retmap.put("value", item.getFValue());
				list.add(retmap);
			}
		}
		return list;
	}

	/**
	 * 判断该客户信息是否正在走流程中
	 * 
	 * @param jobName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public int judgeExist(String instancePre) {
		List list = this.em
				.createNativeQuery(
						"SELECT DISTINCT A.USER_NAME||'['||T.AUTHOR||']' AS AUTHOR FROM WF_WORKLIST T LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.AUTHOR where t.WFSTATUS <> '3' and t.instanceid like '%"
								+ instancePre
								+ "%'"
								+ " union  "
								+ " select DISTINCT ac.USER_NAME || '[' || his.update_user || ']' AS AUTHOR  from OCRM_F_CI_CALLCENTER_UPHIS  his "
								+ " left join ADMIN_AUTH_ACCOUNT ac on his.update_user=ac.account_name "
								+ " where his.cust_id=substr('"+instancePre+"',instr ('"+instancePre+"','_')+1) and his.appr_flag='0' ")
				.getResultList();
		if (list != null && list.size() > 0 && list.get(0) != null) {
			throw new BizException(1, 0, "1002", "客户记录被锁定，操作员" + list.get(0));
		}
		return 1;
	}

	/**
	 * 校验证件1号码是否唯一
	 * 
	 * @param jFirst
	 * @param custId
	 */
	public void identNoExist(JSONArray jFirst, String custId) {
		if (jFirst == null) {
			return;
		}
		// 校验证件1号码是否唯一
		String identNo = "";
		for (int i = 0; i < jFirst.size(); ++i) {
			JSONObject tempObj = (JSONObject) jFirst.get(i);
			if ("IDENT_NO".equals(tempObj.get("updateItemEn"))) {
				identNo = tempObj.get("updateAfCont") == null ? "" : String
						.valueOf(tempObj.get("updateAfCont"));
				break;
			}
		}
		if (!"".equals(identNo)) {
			List<?> list = this.em.createNativeQuery(
					"SELECT C.CUST_NAME FROM ACRM_F_CI_CUSTOMER C WHERE C.IDENT_NO = '"
							+ identNo + "'").getResultList();
			if (list != null && list.size() > 0 && list.get(0) != null) {
				throw new BizException(1, 0, "1002", "证件号码1不能与客户（"
						+ list.get(0) + "）重复！");
			}
		}
	}

	/**
	 * 对私非授信一次提交 注：一次提交,一次复核,流程实例号拼接规则如下： instanceid = "CI_"+custId+"_"+flag;
	 * 对应的修改历史记录为：flag+"|0000"
	 * 加竖线及4位数字,第一位数字：0表示第一屏,1表示第二屏地址,2表示第二屏联系人,3表示第二屏联系信息,4表示第三屏
	 * 第二位数字,若是第二屏的,1表示新增、0表示修改 第三、四位数字,若是第二屏的，表示对应的修改记录条数
	 * 
	 * @param jFsxFirst
	 * @param jFsxSec_1
	 * @param jFsxSec_2
	 * @param jFsxSec_3
	 * @param custId
	 * @param custName
	 * @return
	 * @throws Exception
	 */
	public List<?> commitFsxPerAll(JSONArray jFsxFirst, JSONArray jFsxSec_1,
			JSONArray jFsxSec_2, JSONArray jFsxSec_3, String custId,
			String custName) throws Exception {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		List<Map> returnList = new ArrayList();
		Map<String, String> returnMap = null;
		DecimalFormat df = new DecimalFormat("00");
		String flag = DateUtils.currentTimeMillis();// 修改标识更改为毫秒级
		String instanceid = "CI_" + custId + "_" + flag;
		String jobName = "对私非授信修改_" + custName;// 自定义流程名称
		Date date = new Date();

		String wfid_1 = "79"; // 废弃流程82
		String currNode_1 = "79_a3";
		Map<String, Object> paramMap = new HashMap<String, Object>();

		String nextNode_1 = "79_a4";
		List<?> list = auth.getRolesInfo();
		for (Object m : list) {
			Map<?, ?> map = (Map<?, ?>) m;
			paramMap.put("role", map.get("ROLE_CODE"));
			if ("R300".equals(map.get("ROLE_CODE"))) {// OP经办
				nextNode_1 = "79_a4";
				// continue;
			} else if ("R302".equals(map.get("ROLE_CODE"))
					|| "R303".equals(map.get("ROLE_CODE"))) {// 个法金ARM,RM
				nextNode_1 = "79_a5";
				// continue;
			} else if ("R104".equals(map.get("ROLE_CODE"))
					|| "R105".equals(map.get("ROLE_CODE"))
					|| "R304".equals(map.get("ROLE_CODE"))
					|| "R305".equals(map.get("ROLE_CODE"))) {// 总分行法金ARM，RM
				nextNode_1 = "79_a7";
				// continue;
			}
		}
		returnMap = new HashMap<String, String>();
		returnMap.put("wfid", wfid_1);
		returnMap.put("currNode", currNode_1);
		returnMap.put("nextNode", nextNode_1);
		returnMap.put("instanceid", instanceid);
		returnMap.put("jobName", jobName);
		returnList.add(returnMap);

		if (jFsxSec_1.size() > 99 || jFsxSec_2.size() > 99
				|| jFsxSec_3.size() > 99) {
			throw new BizException(1, 0, "0000",
					"对私非授信第二屏地址、联系人、联系信息一次修改记录不允许超过99条！");
		}
		// 非授信第一屏
		if (jFsxFirst.size() > 0) {
			String tempFlag = flag + "|0000";
			this.bathsave(jFsxFirst, date, tempFlag, "对私非授信信息第一屏");
		}
		// 地址
		if (jFsxSec_1.size() > 0) {
			for (int i = 0; i < jFsxSec_1.size(); i++) {
				JSONObject tempObject = JSONObject.fromObject(jFsxSec_1.get(i));
				JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
				String isChange = "1".equals(tempObject.get("IS_ADD_FLAG")) ? "1"
						: "0";// 1表示新增,0表示修改
				String tempFlag = flag + "|1" + isChange + df.format(i);
				this.bathsave(tempJsonArr, date, tempFlag, "对私非授信信息第二屏");
			}
		}
		// 联系人
		if (jFsxSec_2.size() > 0) {
			for (int i = 0; i < jFsxSec_2.size(); i++) {
				JSONObject tempObject = JSONObject.fromObject(jFsxSec_2.get(i));
				JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
				String isChange = "1".equals(tempObject.get("IS_ADD_FLAG")) ? "1"
						: "0";
				String tempFlag = flag + "|2" + isChange + df.format(i);
				this.bathsave(tempJsonArr, date, tempFlag, "对私非授信信息第二屏");

			}
		}
		// 联系信息
		if (jFsxSec_3.size() > 0) {
			for (int i = 0; i < jFsxSec_3.size(); i++) {
				JSONObject tempObject = JSONObject.fromObject(jFsxSec_3.get(i));
				JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
				String isChange = "1".equals(tempObject.get("IS_ADD_FLAG")) ? "1"
						: "0";
				String tempFlag = flag + "|3" + isChange + df.format(i);
				this.bathsave(tempJsonArr, date, tempFlag, "对私非授信信息第二屏");

			}
		}
		/**
		 * 统一初始化所有流程
		 */
		for (int i = 0; i < returnList.size(); i++) {
			Map<String, String> map = returnList.get(i);
			super.initWorkflowByWfidAndInstanceid(map.get("wfid"),
					map.get("jobName"), paramMap, map.get("instanceid"));
			map.remove("jobName");// 避免太长传值到前台会造成问题
		}
		return returnList;
	}

	/**
	 * 对公非授信一次提交 注：一次提交,一次复核,流程实例号拼接规则如下： instanceid = "CI_"+custId+"_"+flag;
	 * 对应的修改历史记录为：flag+"|0000"
	 * 加竖线及4位数字,第一位数字：0表示第一屏,1表示第二屏地址,2表示第二屏联系人,3表示第二屏联系信息,4表示第三屏
	 * 第二位数字,若是第二屏的,1表示新增、0表示修改 第三、四位数字,若是第二屏的，表示对应的修改记录条数
	 * 
	 * @param jFsxFirst
	 * @param jFsxSec_1
	 * @param jFsxSec_2
	 * @param jFsxSec_3
	 * @param jfsxThird
	 * @param custId
	 * @param custName
	 * @return
	 * @throws Exception
	 */
	public List<?> commitFsxComAll(JSONArray jFsxFirst, JSONArray jFsxSec_1,
			JSONArray jFsxSec_2, JSONArray jFsxSec_3, JSONArray jFsxSec_4, JSONArray jfsxThird,
			String custId, String custName) throws Exception {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		List<Map> returnList = new ArrayList();
		Map<String, String> returnMap = null;
		DecimalFormat df = new DecimalFormat("00");
		String flag = DateUtils.currentTimeMillis();// 修改标识更改成为毫秒级
		String instanceid = "CI_" + custId + "_" + flag;
		String jobName = "对公非授信修改_" + custName;// 自定义流程名称
		Date date = new Date();

		String wfid_1 = "78";// 废弃流程：83/85
		String currNode_1 = "78_a3";
		Map<String, Object> paramMap = new HashMap<String, Object>();

		String nextNode_1 = "78_a4";
		List<?> list = auth.getRolesInfo();
		for (Object m : list) {
			Map<?, ?> map = (Map<?, ?>) m;
			paramMap.put("role", map.get("ROLE_CODE"));
			if ("R300".equals(map.get("ROLE_CODE"))) {// OP经办
				nextNode_1 = "78_a4";
				// continue;
			} else if ("R302".equals(map.get("ROLE_CODE"))
					|| "R303".equals(map.get("ROLE_CODE"))) {// 个法金ARM,RM
				nextNode_1 = "78_a5";
				// continue;
			} else if ("R104".equals(map.get("ROLE_CODE"))
					|| "R105".equals(map.get("ROLE_CODE"))
					|| "R304".equals(map.get("ROLE_CODE"))
					|| "R305".equals(map.get("ROLE_CODE"))) {// 总分行法金ARM，RM
				nextNode_1 = "78_a7";
				// continue;
			}
		}
		returnMap = new HashMap<String, String>();
		returnMap.put("wfid", wfid_1);
		returnMap.put("currNode", currNode_1);
		returnMap.put("nextNode", nextNode_1);
		returnMap.put("instanceid", instanceid);
		returnMap.put("jobName", jobName);
		returnList.add(returnMap);

		// 非授信第一屏
		if (jFsxFirst.size() > 0) {
			String tempFlag = flag + "|0000";
			this.bathsave(jFsxFirst, date, tempFlag, "对公非授信信息第一屏");
		}
		// 地址
		if (jFsxSec_1.size() > 0) {
			for (int i = 0; i < jFsxSec_1.size(); i++) {
				JSONObject tempObject = JSONObject.fromObject(jFsxSec_1.get(i));
				JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
				String isChange = "1".equals(tempObject.get("IS_ADD_FLAG")) ? "1"
						: "0";// 1表示新增,0表示修改
				String tempFlag = flag + "|1" + isChange + df.format(i);
				this.bathsave(tempJsonArr, date, tempFlag, "对公非授信信息第二屏");
			}
		}
		// 联系人信息
		if (jFsxSec_2.size() > 0) {
			for (int i = 0; i < jFsxSec_2.size(); i++) {
				JSONObject tempObject = JSONObject.fromObject(jFsxSec_2.get(i));
				JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
				String isChange = "1".equals(tempObject.get("IS_ADD_FLAG")) ? "1"
						: "0";// 1表示新增,0表示修改
				String tempFlag = flag + "|2" + isChange + df.format(i);
				this.bathsave(tempJsonArr, date, tempFlag, "对公非授信信息第二屏");
			}
		}
		// 股东信息
		if (jFsxSec_4.size() > 0) {
			for (int i = 0; i < jFsxSec_4.size(); i++) {
				JSONObject tempObject = JSONObject.fromObject(jFsxSec_4.get(i));
				JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
				String isChange = "1".equals(tempObject.get("IS_ADD_FLAG")) ? "1"
						: "0";// 1表示新增,0表示修改
				String tempFlag = flag + "|5" + isChange + df.format(i);
				this.bathsave(tempJsonArr, date, tempFlag, "对公非授信信息第二屏");
			}
		}
		// 联系信息
		if (jFsxSec_3.size() > 0) {
			for (int i = 0; i < jFsxSec_3.size(); i++) {
				JSONObject tempObject = JSONObject.fromObject(jFsxSec_3.get(i));
				JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
				String isChange = "1".equals(tempObject.get("IS_ADD_FLAG")) ? "1"
						: "0";// 1表示新增,0表示修改
				String tempFlag = flag + "|3" + isChange + df.format(i);
				this.bathsave(tempJsonArr, date, tempFlag, "对公非授信信息第二屏");
			}
		}
		// 非授信第三屏
		if (jfsxThird.size() > 0) {
			String tempFlag = flag + "|4000";
			this.bathsave(jfsxThird, date, tempFlag, "对公非授信信息第三屏");
		}

		/**
		 * 统一初始化所有流程
		 */
		for (int i = 0; i < returnList.size(); i++) {
			Map<String, String> map = returnList.get(i);
			super.initWorkflowByWfidAndInstanceid(map.get("wfid"),
					map.get("jobName"), paramMap, map.get("instanceid"));
			map.remove("jobName");// 避免太长传值到前台会造成问题
		}
		return returnList;
	}
}
