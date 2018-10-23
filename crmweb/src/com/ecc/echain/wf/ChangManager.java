package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.security.core.context.SecurityContextHolder;

import com.ecc.echain.workflow.engine.EVO;
import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.bcrm.sales.message.action.MailClient;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.bo.RequestHeader;
import com.yuchengtech.trans.client.TransClient;

public class ChangManager extends EChainCallbackCommon {
	/**
	 * 获取次月第一天
	 * 
	 * @return yyyy-MM-dd
	 */
	public String getEffectDate() {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(2);
		int year = cal.get(1);
		if (month == 11) {
			month = 1;
			year++;
		} else {
			month += 2;
		}
		String toDate = year
				+ "-"
				+ (month > 9 ? Integer.valueOf(month) : new StringBuilder("0")
						.append(month).toString()) + "-01";
		return toDate;
	}

	public void endY(EVO vo) throws Exception {
		String instanceid = vo.getInstanceID();
		String[] instanceids = instanceid.split("_");
		this.SQL = (" select t_mgr_id,t_mgr_name,t_org_id,t_org_name,apply_type from OCRM_F_CI_TRANS_APPLY  a   where  a.apply_no='"
				+ instanceids[1] + "'");
		Result result = querySQL(vo);
		String t_mgr_id = "";
		String t_mgr_name = "";
		String t_org_id = "";
		String t_org_name = "";
		String type = "";
		for (SortedMap item : result.getRows()) {
			t_mgr_id = item.get("t_mgr_id").toString();
			t_mgr_name = item.get("t_mgr_name").toString();
			t_org_id = item.get("t_org_id").toString();
			t_org_name = item.get("t_org_name").toString();
			type = item.get("apply_type").toString();
		}
		switch (Integer.valueOf(type).intValue()) {
		case 3:
			endY1(vo);
			break;
		case 4:
			endY2(vo);
			break;
		case 5:
			endY3(vo);
			break;
		}
	}

	/**
	 * 支行内移交
	 * 
	 * @param vo
	 * @throws Exception
	 */
	public void endY1(EVO vo) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		String sql = "";
		try {
			conn = vo.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			String instanceid = vo.getInstanceID();
			String[] instanceids = instanceid.split("_");

			this.SQL = ("SELECT WORK_INTERFIX_DT FROM OCRM_F_CI_TRANS_APPLY WHERE APPLY_NO='"
					+ instanceids[1] + "'");
			Result result1 = querySQL(vo);
			Date beginDate = null;//移交生效日期  OCRM_F_CI_TRANS_APPLY （WORK_INTERFIX_DT）
			for (SortedMap item : result1.getRows()) {
				beginDate = (Date) item.get("WORK_INTERFIX_DT");
			}
			SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");

			String beginDates = fmt.format(beginDate);

			String nowDate = fmt.format(new Date());//当前日期

			if ((beginDate != null) && (!"".equals(beginDate))
					&& (nowDate.compareTo(beginDates) < 0)) {//如果当前日期小于移交生效日期
				sql = " update OCRM_F_CI_TRANS_APPLY set APPROVE_STAT ='2' where APPLY_NO = "
						+ instanceids[1] + " ";
				stmt.execute(sql);
			} else {
				//修改状态
//				this.SQL = ("select c.cust_id, c.cust_name,a.user_name,a.t_mgr_id,a.t_mgr_name,a.t_org_id,a.t_org_name, c.MAIN_TYPE_NEW from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY a on c.apply_no = a.apply_no where c.apply_no ='"
//						+ instanceids[1] + "'");
				
				//modify by liuming 20170522
				SQL="select a.apply_type,c.cust_id, c.cust_name,a.user_id,a.user_name,b.org_id,a.t_mgr_id,a.t_mgr_name,a.t_org_id,a.t_org_name,a.hand_over_reason,to_char(a.APPLY_DATE,'yyyy-mm-dd') APPLY_DATE,a.work_interfix_dt,c.MAIN_TYPE_NEW,c.mgr_id s_mgr_id,c.institution s_org_id "+
		                 "from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY a on c.apply_no = a.apply_no left join admin_auth_account b on a.user_id = b.account_name where c.apply_no ='"+instanceids[1]+"'";

				Result result = querySQL(vo);
				
				//add by liuming 20170720
				//排除非信贷客户
				SQL="select a.apply_type,c.cust_id, c.cust_name,a.user_id,a.user_name,b.org_id,a.t_mgr_id,a.t_mgr_name,a.t_org_id,a.t_org_name,a.hand_over_reason,to_char(a.APPLY_DATE,'yyyy-mm-dd') APPLY_DATE,a.work_interfix_dt,c.MAIN_TYPE_NEW,c.mgr_id s_mgr_id,c.institution s_org_id "+
		                 "from OCRM_F_CI_TRANS_CUST c  join acrm_f_ci_crossindex ac on c.cust_id = ac.cust_id  and ac.src_sys_no = 'LN' left join OCRM_F_CI_TRANS_APPLY a on c.apply_no = a.apply_no left join admin_auth_account b on a.user_id = b.account_name where c.apply_no ='"+instanceids[1]+"'";
				Result resultNew = querySQL(vo);
				
				String custId = "";
				String custName = "";
			    String userName ="";//申请的客户经理
			    String mgrId = "";//移交的客户经理编号
			    String mgrName="";//移交的客户经理名称
			    String mainType = "";//主协办类型
			    String orgId="";//移交的客户经理的机构编号
			    String orgName="";//移交的客户经理的机构名称
				StringBuffer sb = new StringBuffer();
				boolean responseFlag;
				
			    /***
			     * 客户移交
			     * 同步信贷系统
			     * add by liuming 20170522
			     */
				if(resultNew != null && resultNew.getRowCount() > 0){
				    String  responseXmlFromLN = TranCrmListToLN(resultNew);
				    System.out.println("responseFromLN:"+responseXmlFromLN);
				    boolean responseFlagFromLN;
				    responseFlagFromLN = doResXms(responseXmlFromLN);
				    String retMessage = getReturnMessage(responseXmlFromLN);
				    if(!responseFlagFromLN){
//				    	 throw new BizException(1,0,"0000","Warning-168:数据信息同步信贷失败,请及时联系IT部门!");
				    	 throw new BizException(1,0,"0000", "Warning-168:信贷系统客户移交失败:"+retMessage);
				    }
				}
			    /***
			     * add by end
			     */
				
				for (SortedMap item : result.getRows()) {
					custId = item.get("CUST_ID").toString();
					custName = item.get("cust_name").toString();
					userName = item.get("user_name").toString();
					mgrId = item.get("T_MGR_ID").toString();
					mgrName = item.get("t_mgr_name").toString();
					mainType = item.get("MAIN_TYPE_NEW") != null ? item.get(
							"MAIN_TYPE_NEW").toString() : "1";
					orgId = item.get("t_org_id").toString();
					orgName = item.get("t_org_name").toString();
					sb.append(custName + ",");

					/***
				     * 客户归属客户经理&归属机构
				     * 同步给ECIF
				     * 注释该段代码是为了本地测试
				     */
					String responseXml = TranCrmToEcifMgr(custId, mgrId,
							mainType, orgId, vo);

					responseFlag = doResXms(responseXml);
					if (!responseFlag) {
						throw new BizException(1, 0, "0000",
								"Warning-168:数据信息同步失败,请及时联系IT部门!",
								new Object[0]);
					}

					this.SQLS
							.add("update ocrm_f_ci_belong_custmgr mgr set mgr.mgr_id='"
									+ mgrId
									+ "' , mgr.main_type='"
									+ mainType
									+ "' , mgr.mgr_name='"
									+ mgrName
									+ "'"
									+ ", mgr.institution='"
									+ orgId
									+ "' , mgr.institution_name='"
									+ orgName
									+ "' where mgr.cust_id='" + custId + "'");
					this.SQLS
							.add(" update ocrm_f_ci_belong_org org set org.institution_code='"
									+ orgId
									+ "' , org.institution_name='"
									+ orgName
									+ "' , org.main_type='"
									+ mainType
									+ "' where org.cust_id='"
									+ custId + "'");
				}
				
			    //修改状态
				sql = " update OCRM_F_CI_TRANS_APPLY set APPROVE_STAT ='2' where APPLY_NO = "
						+ instanceids[1] + " ";
				stmt.execute(sql);
				//修改状态
				sql = "UPDATE OCRM_F_CI_TRANS_CUST  SET STATE='0'  where APPLY_NO = "
						+ instanceids[1] + " ";
				stmt.execute(sql);
				//写入历史表
				sql = "insert into OCRM_F_CI_BELONG_HIST (BEFORE_INST_CODE,AFTER_INST_CODE,BEFORE_MGR_ID,BEFORE_INST_NAME,AFTER_MGR_ID,ASSIGN_USER,AFTER_INST_NAME,ASSIGN_DATE,ID,BEFORE_MGR_NAME,AFTER_MGR_NAME,BEFORE_MAIN_TYPE,AFTER_MAIN_TYPE,ASSIGN_USERNAME,ETL_DATE,WORK_TRAN_REASON,WORK_TRAN_LEVEL,WORK_TRAN_DATE,CUST_ID) select  c.INSTITUTION,a.t_org_id,c.mgr_id,c.INSTITUTION_NAME,a.t_mgr_id,a.user_id,a.t_org_name,a.apply_date,ID_SEQUENCE.NEXTVAL,c.mgr_name,a.t_mgr_name,'1',c.MAIN_TYPE_NEW,a.user_name,null,a.HAND_OVER_REASON,a.HAND_KIND,a.WORK_INTERFIX_DT,c.cust_id from OCRM_F_CI_TRANS_CUST c left join OCRM_F_CI_TRANS_APPLY  a on c.apply_no = a.apply_no where c.apply_no='"
						+ instanceids[1] + "'";
				stmt.execute(sql);

				//获取拼装的所有报文、发起与ECIF的交易、根据交易状态返回结果、判定是否抛出异常
				//throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
				boolean flag = checkRecevierMgrName(mgrId, orgId, vo);
				if (flag) {
					List<String> list = getLeaders(vo);
					if ((sb != null) && (!"".equals(sb.toString().trim()))
							&& (list != null) && (list.size() > 0)) {
						int endIndex = sb.toString().lastIndexOf(",");
						String custNames = sb.toString().substring(0, endIndex);
						for (String email : list) {
							MailClient.getInstance().sendMsg(
									email,
									"法金客户移交给个金",
									"法金客户经理：" + userName + "将客户：" + custNames
											+ "移交给个金客户经理：" + mgrName);
						}

					}

				}

				if (this.SQLS.size() > 0) {
					executeBatch(vo);
				}
				conn.commit();
			}
		} catch (BizException e) {
			conn.rollback();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
			throw new BizException(1, 0, "10001", "工作流动态调用通过客户移交逻辑失败!",
					new Object[0]);
		} finally {
			if (stmt != null)
				stmt.close();
		}
	}

	/**
	 * 区域中心内/分行内移交
	 * @param vo
	 * @throws Exception
	 */
	public void endY2(EVO vo) throws Exception {
		endY1(vo);
	}

	/**
	 * 跨区域/分行
	 * @param vo
	 * @throws Exception
	 */
	public void endY3(EVO vo) throws Exception {
		endY1(vo);
	}

	public void endN(EVO vo) {
		try {
			String instanceid = vo.getInstanceID();
			String[] instanceids = instanceid.split("_");
			this.SQL = (" update OCRM_F_CI_TRANS_APPLY set APPROVE_STAT ='3' where APPLY_NO = "
					+ instanceids[1] + " ");
			execteSQL(vo);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("执行SQL出错");
		}
	}

	public String TranCrmToEcifMgr(String custId, String mgrId,
			String mainType, String orgId, EVO vo) {
		AuthUser auth = (AuthUser) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String req = "";
		try {
			RequestHeader header = new RequestHeader();
			header.setReqSysCd("CRM");
			header.setReqSeqNo(format.format(new Date()));
			header.setReqDt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
			header.setReqTm(new SimpleDateFormat("HHmmssSS").format(new Date()));

			header.setDestSysCd("ECIF");
			header.setChnlNo("82");
			header.setBrchNo("503");
			header.setBizLine("209");
			header.setTrmNo("TRM10010");
			header.setTrmIP("127.0.0.1");
			header.setTlrNo(auth.getUserId());

			StringBuffer sb = new StringBuffer();
			sb.append("<RequestBody>");
			sb.append("<txCode>updateBelong</txCode>");
			sb.append("<txName>修改客户归属信息</txName>");
			sb.append("<authType>1</authType>");
			sb.append("<authCode>1010</authCode>");
			sb.append("<custNo>" + custId + "</custNo>");
			sb.append("<belongBranch>");
			sb.append("<belongBranchNo>" + orgId + "</belongBranchNo>");
			if (mainType != null)
				sb.append("<mainType>" + mainType + "</mainType>");
			else {
				sb.append("<mainType></mainType>");
			}
			sb.append("</belongBranch>");
			sb.append("<belongManager>");
			sb.append("<custManagerType></custManagerType>");
			sb.append("<validFlag></validFlag>");
			if (mainType != null)
				sb.append("<mainType>" + mainType + "</mainType>");
			else {
				sb.append("<mainType></mainType>");
			}
			sb.append("<startDate></startDate>");
			sb.append("<endDate></endDate>");
			sb.append("<custManagerNo>" + mgrId + "</custManagerNo>");
			sb.append("</belongManager>");
			sb.append("</RequestBody>");
			String Xml = new String(sb.toString().getBytes());
			req = TransClient.process(header, Xml);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 0, "0000",
					"Warning-168:数据信息同步失败,请及时联系IT部门!", new Object[0]);
		}
		return req;
	}

	public boolean doResXms(String xml) throws Exception {
		try {
			xml = xml.substring(8);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			String TxStatCode = root.element("ResponseTail")
					.element("TxStatCode").getTextTrim();
			if ((TxStatCode != null) && (!TxStatCode.trim().equals(""))
					&& (TxStatCode.trim().equals("000000")))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean checkRecevierMgrName(String accountName, String orgId, EVO vo)
			throws Exception {
		boolean flag = false;
		try {
			this.SQL = ("SELECT ACCOUNT_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.* FROM (SELECT ACC.USER_NAME,ACC.ACCOUNT_NAME,ACC.ORG_ID, ORG.UNITNAME,AR.ID FROM ADMIN_AUTH_ACCOUNT_ROLE AR INNER JOIN ADMIN_AUTH_ACCOUNT ACC ON ACC.ID = AR.ACCOUNT_ID INNER JOIN SYS_UNITS ORG ON ORG.UNITID = ACC.ORG_ID WHERE AR.APP_ID = 62 AND AR.ROLE_ID = 303 ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY WHERE ACCOUNT_NAME ='"
					+ accountName + "' AND ORG_ID='" + orgId + "'");
			Result result = querySQL(vo);
			String Name = "";
			for (SortedMap item : result.getRows()) {
				Name = item.get("ACCOUNT_NAME").toString();
			}
			if ((Name != null) && (!"".equals(Name)))
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public List getLeaders(EVO vo) throws Exception {
		List list = new ArrayList();
		this.SQL = " SELECT A.EMAIL  FROM ADMIN_AUTH_ACCOUNT A WHERE A.ACCOUNT_NAME IN( SELECT ACCOUNT_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.* FROM (SELECT ACC.USER_NAME, ACC.ACCOUNT_NAME, ACC.ORG_ID,ORG.UNITNAME, AR.ID FROM ADMIN_AUTH_ACCOUNT_ROLE AR INNER JOIN ADMIN_AUTH_ACCOUNT ACC ON ACC.ID = AR.ACCOUNT_ID INNER JOIN SYS_UNITS ORG ON ORG.UNITID = ACC.ORG_ID WHERE AR.APP_ID = 62 AND AR.ROLE_ID = 122 ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY) AND A.EMAIL IS NOT NULL UNION ALL SELECT A.EMAIL  FROM ADMIN_AUTH_ACCOUNT A WHERE A.ACCOUNT_NAME IN( SELECT ACCOUNT_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.* FROM (SELECT ACC.USER_NAME, ACC.ACCOUNT_NAME,ACC.ORG_ID,ORG.UNITNAME, AR.ID FROM ADMIN_AUTH_ACCOUNT_ROLE AR INNER JOIN ADMIN_AUTH_ACCOUNT ACC ON ACC.ID = AR.ACCOUNT_ID INNER JOIN SYS_UNITS ORG ON ORG.UNITID = ACC.ORG_ID WHERE AR.APP_ID = 62 AND AR.ROLE_ID = 125 ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY ) AND A.EMAIL IS NOT NULL";
		try {
			Result result = querySQL(vo);
			String email = "";
			for (SortedMap item : result.getRows()) {
				email = item.get("EMAIL").toString();
				list.add(email);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 客户移交同步信贷
	 * add by liuming 20170522
	 * @throws  
	 */
	@SuppressWarnings("rawtypes")
	public String TranCrmListToLN(Result result){
		    
		    if(result.getRowCount()<=0){
		    	throw new BizException(1,0,"0000","没有待移交的客户信息!");
		    }
		    
		    String req = "";
	 	try {
	 		String appleType="";//移交类型
		    String custId = "";
		    String userId ="";//申请人编号
		    String userOrg ="";//申请人机构编号
		    String sMgrId = "";//被移交客户经理编号
		    String sOrgId = "";//被移交客户经理的机构编号
		    String mgrId = "";//接收的客户经理编号
		    String orgId="";//接收的客户经理的机构编号
		    String applyDate="";//申请时间
		    String handOverReason="";//移交原因
		 
			StringBuffer custInfoList = new StringBuffer("");//待移交的客户信息集合
		    custInfoList.append("                 <CusInfoList>\n");
		    for (SortedMap item : result.getRows()){
		    	custInfoList.append("                    <CusInfo>\n");
		    	appleType = item.get("apply_type").toString();
			    custId = item.get("cust_id").toString();
			    userId = item.get("user_id").toString();
			    userOrg = item.get("org_id").toString();
			    sMgrId = item.get("s_mgr_id").toString();
			    sOrgId = item.get("s_org_id").toString();
			    mgrId = item.get("t_mgr_id").toString();
			    orgId = item.get("t_org_id").toString();
			    applyDate = item.get("apply_date").toString();
			    handOverReason = (item.get("hand_over_reason") == null || item.get("hand_over_reason").toString() == "")?"无":item.get("hand_over_reason").toString();
			    custInfoList.append("                       <cus_id>"+custId+"</cus_id>\n");
			    custInfoList.append("                       <handover_type>10</handover_type>\n");//业务类型 默认为：10 客户资料，其他类型尚未使用
			    custInfoList.append("                    </CusInfo>\n");
		    }
		    custInfoList.append("                 </CusInfoList>\n");
		    //CRM系统与信贷系统用户编码规则不一致，需要转换。CRM:511N1456,信贷：5111456
		    String lnInputId = chargeUserIdForLN(userId);
		    String lnHandoverId = chargeUserIdForLN(sMgrId);
		    String lnMgrId = chargeUserIdForLN(mgrId);
		    String orgType = "";
		    if(appleType != null && appleType.equals("3")){
		    	 orgType = "10";
		    }else{
		    	 orgType = "21";
		    }
		    //组装请求报文
			StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"GB2312\"?>\n");
			sb.append("<TransBody>\n");
			sb.append("  <RequestHeader>\n");
			sb.append("      <DestSysCd>LN</DestSysCd>\n");
			sb.append("  </RequestHeader>\n");
			sb.append("  <RequestBody>\n");
			sb.append("      <Packet>\n");
			sb.append("         <Data>\n");
			sb.append("           <Req>\n");
			sb.append("                 <area_code>1</area_code>\n");//移交方式（必填） 1：移出客户(默认)；2：转入客户;
			sb.append("                 <org_type>"+orgType+"</org_type>\n");//移交范围(按机构)（必填） 10：支行内移交；21：跨支行移交
			sb.append("                 <handover_mode>2</handover_mode>\n");//移交内容（必填） 2：客户与业务移交（默认）
			sb.append("                 <handover_scope>1</handover_scope>\n");//移交范围(按客户经理)（必填） 1：单个客户移交（指定客户,个数有限制不能超出报文长度）；2：按客户经理所有客户
			sb.append(custInfoList.toString());//待移交客户信息
			sb.append("                 <handover_br_id>"+sOrgId+"</handover_br_id>\n");//被移出客户经理机构（必填）
			sb.append("                 <handover_id>"+lnHandoverId+"</handover_id>\n");//被移出客户经理编号（必填）
			sb.append("                 <receiver_br_id>"+orgId+"</receiver_br_id>\n");//接收人客户经理机构（必填）
			sb.append("                 <receiver_id>"+lnMgrId+"</receiver_id>\n");//接收人客户经理编号（必填）
			sb.append("                 <supervise_br_id></supervise_br_id>\n");//监交机构（非必填）
			sb.append("                 <supervise_id></supervise_id>\n");//监交人（非必填）
			sb.append("                 <handover_detail>"+handOverReason+"</handover_detail>\n");//移交说明（必填）
			sb.append("                 <input_id>"+lnInputId+"</input_id>\n");//申请人编号（必填）
			sb.append("                 <input_br_id>"+userOrg+"</input_br_id>\n");//申请人机构（必填）
			sb.append("                 <input_date>"+applyDate+"</input_date>\n");//申请日期（必填）
			sb.append("           </Req>\n");
			sb.append("           <Pub>\n");
			sb.append("               <prcscd>CusHandoverByCrm</prcscd>\n");
			sb.append("           </Pub>\n");
			sb.append("         </Data>\n");
			sb.append("     </Packet>\n");
			sb.append("   </RequestBody>\n");
			sb.append("</TransBody>\n");

    	    StringBuffer sbReq = new StringBuffer();
    	    sbReq.append(String.format("%08d", sb.toString().getBytes("GBK").length));
    	    sbReq.append(sb.toString());
    	    System.out.println("requestToLN:"+sbReq.toString());
    	    //调用信贷客户移交 接口,得到返回报文。
    	    req= TransClient.processLN(sbReq.toString());//调用信贷
		}catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,0,"0000","Warning-168:数据信息同步信贷失败,请及时联系IT部门!");
		}
		return req;
	}
	//add by liuming 20170720
	public String chargeUserIdForLN(String userid){
		   String useridForLN = "";
		   if(userid.toUpperCase().equals("ADMIN")){
			   useridForLN = userid;
		    }else{
		    	useridForLN = userid.substring(0,3)+userid.substring(4,8);
		    }
		return useridForLN;
	}
	
	//获取响应报文中的返回信息
	public String getReturnMessage(String xml) throws Exception {
		String retMessage = ""; 
		try {
			xml = xml.substring(8);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			retMessage = root.element("ResponseTail")
					.element("TxStatDesc").getTextTrim();
		} catch (Exception e) {
			e.printStackTrace();
			retMessage = "解析信贷系统返回报文失败";
		}
		return retMessage;
	}
}