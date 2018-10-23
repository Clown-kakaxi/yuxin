package com.ecc.echain.wf;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;

import javax.servlet.jsp.jstl.sql.Result;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bcrm.echain.EChainCallbackCommon;
import com.yuchengtech.bcrm.sales.message.action.MailClient;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;
import com.yuchengtech.trans.client.TransClient;

public class TaskJob extends EChainCallbackCommon {
	private static Logger log = Logger.getLogger(TaskJob.class);
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	public void transMgrHandler(){
		log.info("处理任务开始[法金客户经理移交]");
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		//add by liuming 
		Statement stmt1 = null;
		ResultSet rs1 = null;
		
		String sql = "";
		String instanceId = "";
		Date beginDate = null;//移交生效日期  OCRM_F_CI_TRANS_APPLY （WORK_INTERFIX_DT）
		String userName="";//发起客户经理编号
		String TMgrId = "";//接受客户经理编号
		String TMgeName="";//接受客户经理姓名
		String TOrgId = "";//接受机构编号
		String TOrgName ="";//接受机构名称
	    String custId = "";//客户编号
	    String custName="";//客户名称
	    String applyNo="";//申请编号
	    String id = "";
	    String type="";//区分是法金还是个人客户经理移交
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
		String nowDate= fmt.format(new Date());
		String emailFormat = "客户经理：[%s] 将客户：[%s] 移交给客户经理：[%s]";
		try {
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//查询已复核但是未同步的
			//移交类型TYPE(1-法金客户经理移交;0-个人客户经理移交 ;2-潜在客户移交)
			//同步状态STATE(0-同步;1-未同步)
//			sql="SELECT Y.APPLY_NO,Y.TYPE,Y.USER_NAME, Y.T_MGR_ID,Y.T_MGR_NAME,Y.WORK_INTERFIX_DT,Y.T_ORG_ID,Y.T_ORG_NAME,T.CUST_ID,T.CUST_NAME,T.ID FROM  OCRM_F_CI_TRANS_APPLY Y" +
//				" LEFT JOIN OCRM_F_CI_TRANS_CUST T ON Y.APPLY_NO = T.APPLY_NO WHERE Y.APPROVE_STAT='2'  AND T.STATE='1'";
			//modify by liuming 20170522,增加申请人机构、申请日期、查询移交说明、移交类型（传信贷客户移交接口）
			sql="SELECT Y.APPLY_NO,Y.TYPE,Y.USER_NAME, Y.T_MGR_ID,Y.T_MGR_NAME,Y.WORK_INTERFIX_DT,"
					+ " Y.T_ORG_ID,Y.T_ORG_NAME,T.CUST_ID,T.CUST_NAME,T.ID,A.ORG_ID,to_char(Y.APPLY_DATE,'yyyy-mm-dd') APPLY_DATE,"
					+ " Y.HAND_OVER_REASON,Y.APPLY_TYPE ,Y.USER_ID,T.Mgr_Id s_mgr_id,t.institution s_org_id "
					+ " FROM OCRM_F_CI_TRANS_APPLY Y"
					+ " LEFT JOIN OCRM_F_CI_TRANS_CUST T ON Y.APPLY_NO = T.APPLY_NO "
					+ " LEFT JOIN admin_auth_account A on Y.USER_ID = A.ACCOUNT_NAME "
					+ " WHERE Y.APPROVE_STAT='2'  AND T.STATE='1'";//状态为：未同步
			log.info("查询移交申请表里已到期但还未同步的[法金客户经理移交]");
			rs = stmt.executeQuery(sql);
			StringBuffer sb = new StringBuffer();
			while(rs.next()){
				try {//捕获异常，避免因为某条数据导致整个移交过程终止
					applyNo=rs.getString("APPLY_NO");//申请编号
					type = rs.getString("TYPE");//区分是法金还是个人客户经理移交
					userName=rs.getString("USER_NAME");//发起的客户经理
					beginDate = rs.getDate("WORK_INTERFIX_DT");//移交生效日期
					TMgrId = rs.getString("T_MGR_ID");//接受的客户经理编号
					TMgeName = rs.getString("T_MGR_NAME");//接受客户经理的姓名
					TOrgId = rs.getString("T_ORG_ID");//接受的机构编号
					TOrgName=rs.getString("T_ORG_NAME");//接受的机构名称
					custId = rs.getString("CUST_ID");//移交的客户编号
					custName=rs.getString("CUST_NAME");//移交的客户名称
					id=rs.getString("ID");//编号ID
					String beginDates=fmt.format(beginDate);
					
					//本地测试先注释liuming
					if(beginDates!=null && nowDate.compareTo(beginDates)>=0){
						//add by liuming 20170522 
						if(type!=null && !"".equals(type) && "1".equals(type)){//法金客户经理移交
							//add by liuming 20170720 判断是否是信贷客户
							String sql1="SELECT 1 FROM ACRM_F_CI_CROSSINDEX C WHERE C.CUST_ID ='"+custId+"' AND C.SRC_SYS_NO='LN'";
						    rs1 = stmt1.executeQuery(sql1);
							rs1.last();
							if(rs1 != null && rs1.getRow() > 0){
								//调用信贷客户移交接口
								log.info("调用信贷客户移交接口移交客户");
								String responseXml4LN = TranCrmToLN(rs.getString("APPLY_TYPE"),custId,rs.getString("USER_ID"),rs.getString("ORG_ID"),TMgrId,TOrgId,rs.getString("APPLY_DATE"),rs.getString("HAND_OVER_REASON"),rs.getString("S_MGR_ID"),rs.getString("S_ORG_ID"));
								boolean responseFlag4LN;
								responseFlag4LN = doResXms(responseXml4LN);
								if(!responseFlag4LN){
									throw new BizException(1,0,"0000","Warning-168:数据信息同步信贷失败:"+getReturnMessage(responseXml4LN));
								}
							}
							//调用ECIF客户移交接口
							log.info("调用ECIF客户移交接口移交客户");
							String responseXml = TranCrmToEcifMgr(custId, TMgrId, "1", TOrgId);
							boolean responseFlag;
							responseFlag = doResXms(responseXml);
							if(!responseFlag){
								throw new BizException(1,0,"0000","Warning-168:数据信息同步核心失败,请及时联系IT部门!");
							}
							
							//修改同步状态
							sql = " UPDATE OCRM_F_CI_TRANS_CUST SET STATE ='0' WHERE APPLY_NO = "+applyNo+" AND ID = '"+id+"' ";
							stmt.execute(sql);
							
							sql ="update ocrm_f_ci_belong_custmgr mgr set mgr.mgr_id='"+TMgrId+"', mgr.mgr_name='"+TMgeName+"'"
							              +", mgr.institution='"+TOrgId+"' , mgr.institution_name='"+TOrgName+"' where mgr.cust_id='"+custId+"'";
							stmt.execute(sql);
							 
							sql =" update ocrm_f_ci_belong_org org set org.institution_code='"+TMgrId+"' , org.institution_name='"+TOrgName+"'  where org.cust_id='"+custId+"'";
							stmt.execute(sql);
							
							sql = "INSERT INTO OCRM_F_CI_BELONG_HIST " +
									"(BEFORE_INST_CODE,AFTER_INST_CODE,BEFORE_MGR_ID,BEFORE_INST_NAME,AFTER_MGR_ID,ASSIGN_USER,AFTER_INST_NAME,ASSIGN_DATE,ID,BEFORE_MGR_NAME,AFTER_MGR_NAME,BEFORE_MAIN_TYPE,AFTER_MAIN_TYPE,ASSIGN_USERNAME,ETL_DATE,WORK_TRAN_REASON,WORK_TRAN_LEVEL,WORK_TRAN_DATE,CUST_ID) " +
									"SELECT  C.INSTITUTION,A.T_ORG_ID,C.MGR_ID,C.INSTITUTION_NAME,A.T_MGR_ID,A.USER_ID,A.T_ORG_NAME," +
									"A.APPLY_DATE,ID_SEQUENCE.NEXTVAL,C.MGR_NAME,A.T_MGR_NAME,'1',C.MAIN_TYPE_NEW,A.USER_NAME,NULL,A.HAND_OVER_REASON," +
									"A.HAND_KIND,A.WORK_INTERFIX_DT,C.CUST_ID " +
									"fROM OCRM_F_CI_TRANS_CUST C LEFT JOIN OCRM_F_CI_TRANS_APPLY  A ON C.APPLY_NO = A.APPLY_NO WHERE C.APPLY_NO='"+applyNo+"' AND C.ID = '"+id+"'";
							stmt.execute(sql);
							conn.commit();
						}
						//add end 
					}
					if(!"".equals(sb.toString())){
						sb.append(" ; ");
					}
					//拼接移交信息，客户经理：xxx 将客户：xxx 移交给客户经理：xxx
					sb.append(String.format(emailFormat, userName,custName,TMgeName));
					sb.append("</br>");
                } catch (Exception e) {
                	log.error(String.format("客户移转失败，失败原因：", e.getMessage()), e);
                }
			}
			//邮件通知
			if(beginDate!=null && !"".equals(beginDate)){
				String beginDates=fmt.format(beginDate);
				if( beginDates!=null && nowDate.compareTo(beginDates)>=0){
					if(type!=null && !"".equals(type) && "1".equals(type)){//如果是法金客户经理移交
						boolean flag = checkRecevierMgrName(TMgrId,TOrgId);
						if(flag){
							List<String> list = getLeaders();
							if(sb!=null && !"".equals(sb.toString().trim()) && list!=null && list.size()>0){
							   int endIndex = sb.toString().lastIndexOf(",");
							   String custNames = sb.toString().substring(0, endIndex);
							   for(String email:list){
							     MailClient.getInstance().sendMsg(email, "法金客户移交给个金", sb.toString());
							   }
							}
						}
					}
				}
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs, stmt, conn);
			JdbcUtil.close(rs1, stmt1, conn);
		}
		log.info("处理任务结束[法金客户经理移交]");
	}
	
	
	/**
	 * 报文处理
	 * 客户归属经理 
	 * 客户归属机构
	 * @throws  
	 */
	public String TranCrmToEcifMgr(String custId,String mgrId,String mainType,String orgId){
		// AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		 String req = "";
		//注：工作流动态调用类中,是无法获取Session用户数据的
    	try {
			 com.yuchengtech.trans.bo.RequestHeader header = new com.yuchengtech.trans.bo.RequestHeader();
			 header.setReqSysCd("CRM");
			 header.setReqSeqNo(format.format(new Date()));//交易流水号
			 header.setReqDt(new SimpleDateFormat("yyyyMMdd").format(new Date()));//请求日期
			 header.setReqTm(new SimpleDateFormat("HHmmssSS").format(new Date()));//请求时间
			 
			 header.setDestSysCd("ECIF");
			 header.setChnlNo("82");
			 header.setBrchNo("503");
			 header.setBizLine("209");
			 header.setTrmNo("TRM10010");
			 header.setTrmIP("127.0.0.1");
			 header.setTlrNo("admin");//用户编号
			 
			 StringBuffer sb = new StringBuffer();
			 sb.append("<RequestBody>");
			 sb.append("<txCode>updateBelong</txCode>");
			 sb.append("<txName>修改客户归属信息</txName>");
			 sb.append("<authType>1</authType>");
			 sb.append("<authCode>1010</authCode>");
			 sb.append("<custNo>" + custId + "</custNo>");
			 sb.append("<belongBranch>");
			 sb.append("<belongBranchNo>"+orgId+"</belongBranchNo>");
			 if(mainType!=null){
				 sb.append("<mainType>"+mainType+"</mainType>");
			 }else{
				 sb.append("<mainType></mainType>");
			 }
			 sb.append("</belongBranch>");
			 sb.append("<belongManager>");
			 sb.append("<custManagerType></custManagerType>");
			 sb.append("<validFlag></validFlag>");
			 if(mainType!=null){
				 sb.append("<mainType>"+mainType+"</mainType>");
			 }else{
				 sb.append("<mainType></mainType>");
			 }
			 sb.append("<startDate></startDate>");
			 sb.append("<endDate></endDate>");
			 sb.append("<custManagerNo>"+mgrId+"</custManagerNo>"); //客户经理编号
			 sb.append("</belongManager>");
			 sb.append("</RequestBody>");
			 String Xml = new String(sb.toString().getBytes());
			 req  = TransClient.process(header, Xml);
		}catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,0,"0000","Warning-168:数据信息同步失败,请及时联系IT部门!");
		}
		return req;
	}

	/**
	 * 处理返回报文
	 * @param xml
	 * @return
	 */
	public boolean doResXms(String xml) throws Exception{
		try{
			xml=xml.substring(8);
			Document doc = DocumentHelper.parseText(xml);
			Element root = doc.getRootElement();
			String TxStatCode = root.element("ResponseTail").element("TxStatCode").getTextTrim();
			String TxStatDesc = root.element("ResponseTail").element("TxStatDesc").getTextTrim();
			if(TxStatCode!=null && !TxStatCode.trim().equals("") && (TxStatCode.trim().equals("000000"))){
				return true;
			}else{
				throw new BizException(1,0,"0000",String.format("Warning-168:数据信息同步核心失败,TxStatCode:%s , TxStatDesc:%s", TxStatCode,TxStatDesc));
			}
		}catch(Exception e){
			if(e instanceof BizException){
				throw e;
			}
		}
		return false;
	}

	public  boolean checkRecevierMgrName(String accountName,String orgId) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		String name="";//接受的客户经理的角色判断
		boolean flag = false;
		try{
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql="SELECT ACCOUNT_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.*" +
				" FROM (SELECT ACC.USER_NAME,ACC.ACCOUNT_NAME,ACC.ORG_ID, ORG.UNITNAME,AR.ID" +
				" FROM ADMIN_AUTH_ACCOUNT_ROLE AR" +
				" INNER JOIN ADMIN_AUTH_ACCOUNT ACC" +
				" ON ACC.ID = AR.ACCOUNT_ID" +
				" INNER JOIN SYS_UNITS ORG" +
				" ON ORG.UNITID = ACC.ORG_ID" +
				" WHERE AR.APP_ID = 62" +
				" AND AR.ROLE_ID = 303" +
				" ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY" +
				" WHERE ACCOUNT_NAME ='"+accountName+"' AND ORG_ID='"+orgId+"'";
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				name = rs.getString("ACCOUNT_NAME");
			}
		   if(name!= null && !"".equals(name)){
			   flag = true;
		   }
		   conn.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 查询角色为【个金部门主管】和【总行个金财管专员】的所有人员的邮箱，如果邮箱为空则排除
	 * @return List 邮箱
	 * @throws Exception
	 */
	public List<String> getLeaders() throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		List<String> list = new ArrayList<String>();
		String email="";
		try{
			conn = ds.getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			sql=" SELECT A.EMAIL  FROM ADMIN_AUTH_ACCOUNT A WHERE A.ACCOUNT_NAME IN(" +
					" SELECT ACCOUNT_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.*" +
					" FROM (SELECT ACC.USER_NAME, ACC.ACCOUNT_NAME, ACC.ORG_ID,ORG.UNITNAME, AR.ID" +
					" FROM ADMIN_AUTH_ACCOUNT_ROLE AR INNER JOIN ADMIN_AUTH_ACCOUNT ACC" +
					" ON ACC.ID = AR.ACCOUNT_ID" +
					" INNER JOIN SYS_UNITS ORG" +
					" ON ORG.UNITID = ACC.ORG_ID" +
					" WHERE AR.APP_ID = 62" +
					" AND AR.ROLE_ID = 122" +//个金部门主管
					" ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY) AND A.EMAIL IS NOT NULL" +
					" UNION ALL" +
					" SELECT A.EMAIL  FROM ADMIN_AUTH_ACCOUNT A WHERE A.ACCOUNT_NAME IN(" +
					" SELECT ACCOUNT_NAME FROM (SELECT ROW_NUMBER() OVER(ORDER BY 1) AS RN, BUSINESS_QUERY.*" +
					" FROM (SELECT ACC.USER_NAME, ACC.ACCOUNT_NAME,ACC.ORG_ID,ORG.UNITNAME, AR.ID" +
					" FROM ADMIN_AUTH_ACCOUNT_ROLE AR" +
					" INNER JOIN ADMIN_AUTH_ACCOUNT ACC" +
					" ON ACC.ID = AR.ACCOUNT_ID" +
					" INNER JOIN SYS_UNITS ORG" +
					" ON ORG.UNITID = ACC.ORG_ID" +
					" WHERE AR.APP_ID = 62" +
					" AND AR.ROLE_ID = 125" +//总行个金财管专员
					" ORDER BY ACC.ORG_ID) BUSINESS_QUERY) SUB_QUERY ) AND A.EMAIL IS NOT NULL";
			    rs = stmt.executeQuery(sql);
				while(rs.next()){
					email = rs.getString("EMAIL");
					list.add(email);
				}
				  conn.commit();
		}catch(Exception e){
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
	public String TranCrmToLN(String appleType,String custId,String userId,String userOrg, 
			String mgrId,String orgId,String applyDate,String handOverReason,String sMgrId,String sOrgId){
	    String req = "";
 	try {
		StringBuffer custInfoList = new StringBuffer("");//待移交的客户信息集合
		custInfoList.append("                 <CusInfoList>\n");
	    	custInfoList.append("                    <CusInfo>\n");
	    	if(handOverReason == null || handOverReason.equals("")){
	    		handOverReason = "无";
	    	}
		    custInfoList.append("                       <cus_id>"+custId+"</cus_id>\n");
		    custInfoList.append("                       <handover_type>10</handover_type>\n");//业务类型 默认为：10 客户资料，其他类型尚未使用
		    custInfoList.append("                    </CusInfo>\n");
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
			throw new BizException(1,0,"0000","Warning-168:定时任务数据信息同步信贷失败,请及时联系IT部门!");
		}
		return req;
	}
	/**
	 * 客户移交同步信贷
	 * add by liuming 20170522
	 * @throws  
	 */
	@SuppressWarnings("rawtypes")
	public String TranCrmListToLN(Result result){
		
	    if(result.getRowCount()<0){
	    	throw new BizException(1,0,"0000","没有待移交的客户信息!");
	    }
	    
	    String req = "";
 	try {
 		String appleType="";//移交类型
	    String custId = "";
	    String userId ="";//申请人编号
	    String userOrg ="";//申请人机构编号
	    String mgrId = "";//移交的客户经理编号
	    String orgId="";//移交的客户经理的机构编号
	    String applyDate="";//申请时间
	    String handOverReason="";//移交原因
	    String sMgrId = "";//被移交客户经理编号
	 
		StringBuffer custInfoList = new StringBuffer("");//待移交的客户信息集合
	    custInfoList.append("                 <CusInfoList>\n");
	    for (SortedMap item : result.getRows()){
	    	custInfoList.append("                    <CusInfo>\n");
	    	appleType = item.get("apply_type").toString();
		    custId = item.get("cust_id").toString();
		    userId = item.get("user_id").toString();
		    userOrg = item.get("org_id").toString();
		    mgrId = item.get("t_mgr_id").toString();
		    orgId = item.get("t_org_id").toString();
		    applyDate = item.get("apply_date").toString();
		    sMgrId = item.get("s_mgr_id").toString();
		    handOverReason = (item.get("hand_over_reason") == null || item.get("hand_over_reason").toString() == "")?"无":item.get("hand_over_reason").toString();
		    custInfoList.append("                       <cusId>"+custId+"</cusId>\n");
		    custInfoList.append("                       <handoverType>10</handoverType>\n");//业务类型 默认为：10 客户资料，其他类型尚未使用
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
		sb.append("                 <areaCode>1</areaCode>\n");//移交方式（必填） 1：移出客户(默认)；2：转入客户;
		sb.append("                 <orgType>"+orgType+"</orgType>\n");//移交范围(按机构)（必填） 10：支行内移交；21：跨支行移交
		sb.append("                 <handoverMode>2</handoverMode>\n");//移交内容（必填） 2：客户与业务移交（默认）
		sb.append("                 <handoverScope>1</handoverScope>\n");//移交范围(按客户经理)（必填） 1：单个客户移交（指定客户,个数有限制不能超出报文长度）；2：按客户经理所有客户
		sb.append(custInfoList.toString());//待移交客户信息
		sb.append("                 <handoverBrId>"+userOrg+"</handoverBrId>\n");//被移出客户经理机构（必填）
		sb.append("                 <handoverId>"+lnHandoverId+"</handoverId>\n");//被移出客户经理编号（必填）
		sb.append("                 <receiverBrId>"+orgId+"</receiverBrId>\n");//接收人客户经理机构（必填）
		sb.append("                 <receiverId>"+lnMgrId+"</receiverId>\n");//接收人客户经理编号（必填）
		sb.append("                 <superviseBrId></superviseBrId>\n");//监交机构（非必填）
		sb.append("                 <superviseId></superviseId>\n");//监交人（非必填）
		sb.append("                 <handoverDetail>"+handOverReason+"</handoverDetail>\n");//移交说明（必填）
		sb.append("                 <inputId>"+lnInputId+"</inputId>\n");//申请人编号（必填）
		sb.append("                 <inputBrId>"+userOrg+"</inputBrId>\n");//申请人机构（必填）
		sb.append("                 <inputDate>"+applyDate+"</inputDate>\n");//申请日期（必填）
		sb.append("           </Req>\n");
		sb.append("           <Pub>\n");
		sb.append("               <prcscd>CusHandOverInterface</prcscd>\n");
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
			throw new BizException(1,0,"0000","Warning-168:定时任务数据信息同步信贷失败,请及时联系IT部门!");
		}
		return req;
	}
	
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
