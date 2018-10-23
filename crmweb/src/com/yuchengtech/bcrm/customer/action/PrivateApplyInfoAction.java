package com.yuchengtech.bcrm.customer.action;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.sql.Result;
import javax.sql.DataSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.common.DateUtils;
import com.yuchengtech.bcrm.customer.service.PrivateApplyInfoService;
import com.yuchengtech.bcrm.custview.model.AcrmFCiAccountInfo;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.core.QueryHelper;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.exception.BizException;

/**
 * @deprecated: 个人账户新增/变更/销户申请表Action
 * @author denghj
 * @since 2015-11-25
 */
@Action("/privateApplyInfo")
public class PrivateApplyInfoAction extends CommonAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;
	
	@Autowired
	private PrivateApplyInfoService privateApplyInfoService;

	@Autowired
	public void init(){
		setCommonService(privateApplyInfoService);
	}
	 
	/**
	 * 查询客户基本信息
	 * @return
	 */
	public String queryBasicInfo(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		StringBuffer sb = new StringBuffer("");
		sb.append("select c.cust_id,c.cust_name,c.ident_type,c.ident_no,i.ident_id from acrm_f_ci_customer c " +
				"left join ACRM_F_CI_CUST_IDENTIFIER i on c.cust_id = i.cust_id and c.ident_type = i.ident_type where c.cust_id = '"+custId+"'");
		
		try {
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			Map<String, Object> result = query.getJSON();
			if(this.json != null){
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("json",result);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 查询B客户信息数据
	 * @return
	 */
	public String queryCustChangedInfo(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		StringBuffer sb = new StringBuffer("select p.cust_id,p.personal_name,p.pinyin_name,p.gender,i.ident_expired_date,p.birthday,p.citizenship,p.birthlocale,");
		sb.append("c1.contmeth_info contmeth_info_sj,c1.contmeth_id contmeth_info_sj_id,c2.contmeth_info contmeth_info_yd,c2.contmeth_id contmeth_info_yd_id,c4.contmeth_info contmeth_info_jt,c4.contmeth_id contmeth_info_jt_id,");
		sb.append("a1.addr home_addr,a1.zipcode home_zipcode,a1.addr_id home_addr_id,a2.addr post_addr,a2.zipcode post_zipcode,a2.addr_id post_addr_id,");
		sb.append("a.home_style,c3.contmeth_info email,c3.contmeth_id email_id,a.if_update_mail,a.profession_style, a.profession_style_remark,");
		sb.append("p.unit_name,p.duty,k.usa_tax_flag,p.usa_tax_iden_no,k.is_related_bank ");
		sb.append("from ACRM_F_CI_PERSON p ");
		sb.append("left join (select * from ACRM_F_CI_PERSON_ADDITIONAL) a on p.cust_id = a.cust_id ");
		sb.append("left join (select * from ACRM_F_CI_CUST_IDENTIFIER) i on p.cust_id = i.cust_id ");
		sb.append("left join (select * from ACRM_F_CI_PER_KEYFLAG) k on p.cust_id = k.cust_id "); 
		sb.append("left join (select * from ACRM_F_CI_CONTMETH where contmeth_type = '102') c1 on p.cust_id = c1.cust_id "); 
		sb.append("left join (select * from ACRM_F_CI_CONTMETH where contmeth_type = '209') c2 on p.cust_id = c2.cust_id "); 
		sb.append("left join (select * from ACRM_F_CI_CONTMETH where contmeth_type = '500') c3 on p.cust_id = c3.cust_id "); 
		sb.append("left join (select * from ACRM_F_CI_CONTMETH where contmeth_type = '2031') c4 on p.cust_id = c4.cust_id ");
		sb.append("left join (select * from ACRM_F_CI_ADDRESS where addr_type = '04') a1 on p.cust_id = a1.cust_id "); 
		sb.append("left join (select * from ACRM_F_CI_ADDRESS where addr_type = '01') a2 on p.cust_id = a2.cust_id "); 
		sb.append("where p.cust_id = '"+custId+"'");
		
		try{
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			Map<String, Object> result = query.getJSON();
			if(this.json != null){
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("json",result);
		} catch(SQLException e){
			e.printStackTrace();
		}
		return  "success";
	}
	
	/**
	 * 查询C账户信息数据
	 * @return
	 */
	public String queryAccountChangedInfo(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		String contentType = request.getParameter("contentType");//账户类型
		StringBuffer sb = new StringBuffer("select a from AcrmFCiAccountInfo a ");
		sb.append("where a.custId = '"+custId+"'");
		ArrayList<AcrmFCiAccountInfo> list = (ArrayList<AcrmFCiAccountInfo>) privateApplyInfoService.findByJql(sb.toString(), null);
		//用以存放转换后的查询信息
		List result = new ArrayList<String>();
		if(list.size()>0){
			for(AcrmFCiAccountInfo info : list){
				HashMap<String, Object> map = new HashMap<String, Object>();
				if(contentType == null){
					map.put("IS_DOMESTIC_CUST", info.getIsDomesticCust());
					map.put("ACCOUNT_CONTENTS", info.getAccountContents());
					map.put("ACCOUNT_NUMBERS", info.getAccountNumbers());
					map.put("CUST_ID", info.getCustId());
					map.put("SERIALNO",info.getSerialno());
					result.add(map);
				}else{
					String accountCotent = info.getAccountContents();
					String accountNumber = info.getAccountNumbers();
					if(accountCotent != null && accountNumber != null){
						String[] cotentStrs = accountCotent.split(",");
						for(String cotent : cotentStrs){
							if(cotent.equals(contentType)){//判断查询结果是否包含目标账户类型
								String[] numberStrs = accountNumber.split(",");
								for(String number : numberStrs){//判断查询结果是否包含目标账户账号
									if(!(number.indexOf(contentType+"-")<0)){
										//存放转换后的结果
										map.put("CUST_ID", info.getCustId());
										map.put("ACCOUNT_CONTENTS", cotent);
										map.put("ACCOUNT_FLAG", "");
										map.put("ACCOUNT_SIGN", "1");
										map.put("ACCOUNT_NUMBERS", number.split("-")[1]);
										result.add(map);
									}
								}
							}
						}
					}
				}
			}
			if(this.json != null){
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			//若list无结果集，则json为空，避免前台出现空数据
			if(result.isEmpty()){
				this.json.put("json","");
			}else{
				JSONArray accountJson = JSONArray.fromObject(result);
				this.json.put("json",accountJson);
			}
		}

		return "success";
	}
	
	/**
	 * 查询D服务信息数据
	 * @return
	 */
	public String queryServiceChangedInfo(){
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		String custId = request.getParameter("custId");
		StringBuffer sb = new StringBuffer("select * from acrm_f_ci_bank_service b ");
		sb.append("where b.cust_id = '"+custId+"'");
		
		try{
			QueryHelper query = new QueryHelper(sb.toString(), ds.getConnection());
			Map<String, Object> result = query.getJSON();
			if(this.json != null){
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			this.json.put("json",result);
		} catch(SQLException e){
			e.printStackTrace();
		}
		return "success";
	}
	
	/**
	 * 判断custId信息是否已被锁定
	 * @return
	 */
	public String isLocked(){
		try{
			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        String custId = "BGSQ_"+request.getParameter("custId");//BGSQ_:变更申请
	        
			StringBuffer sb = new StringBuffer("SELECT WM_CONCAT(DISTINCT A.USER_NAME||'['||T.AUTHOR||']') AS AUTHOR FROM WF_WORKLIST T LEFT JOIN ADMIN_AUTH_ACCOUNT A ON A.ACCOUNT_NAME = T.AUTHOR where t.WFSTATUS <> '3' and t.instanceid like '%"+custId+"%'");
			QueryHelper query;
			query = new QueryHelper(sb.toString(), ds.getConnection());
			Map<String, Object> result = query.getJSON();
			//"客户记录被锁定，操作员"+list.get(0)
			if(this.json != null)
				this.json.clear();
			else
				this.json = new HashMap<String,Object>(); 
			this.json.put("json",result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
		}
		return "success";
	}
	
	/**
	 * 个人账户新增/变更/销户申请书提交审批
	 * @return
	 */
	public String initApplyInfo() throws Exception{
		try{
  			ActionContext ctx = ActionContext.getContext();
	        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
	        HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);
	        AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        DecimalFormat df = new DecimalFormat("00");
	        String custId = request.getParameter("custId");
			String custName = request.getParameter("custName");
			String identType = request.getParameter("identType");
			String custChangedStr = request.getParameter("custChangedInfo");
			String accountChangedStr = request.getParameter("accountChangedInfo");
			String serviceChangedStr = request.getParameter("serviceChangedInfo");
			String addrChangedStr = request.getParameter("addrChangedInfo");
			String contChangedStr = request.getParameter("contChangedInfo");
			String identChangedStr = request.getParameter("identChangedInfo");
			String keyChangedStr = request.getParameter("keyChangedInfo");
			String custAddInfoStr = request.getParameter("custAddInfo");
			JSONArray custChangedJson = JSONArray.fromObject(custChangedStr);
			JSONArray accountChangedJson = JSONArray.fromObject(accountChangedStr);
			JSONArray serviceChangedJson = JSONArray.fromObject(serviceChangedStr);
			JSONArray addrChangedJson = JSONArray.fromObject(addrChangedStr);
			JSONArray contChangedJson = JSONArray.fromObject(contChangedStr);
			JSONArray identChangedJson = JSONArray.fromObject(identChangedStr);
			JSONArray keyChangedJson = JSONArray.fromObject(keyChangedStr);
			JSONArray custAddInfoJson = JSONArray.fromObject(custAddInfoStr);
			
			//判断该case是否已经提交审核
			privateApplyInfoService.judgeExist("BGSQ_"+custId);
			
			//用于存放用户角色
			Map<String, Object> paramMap = new HashMap<String, Object>();
			//当前结点
			String currNode = "126_a3";
			//定义默认下一节点126_a4
			String nextNode = "126_a4";
			//获取用户角色列表
			List<?> list = auth.getRolesInfo();
			//遍历用户角色列表    存放于paramMap  并分别选取nextNode
			for(Object m : list){
				Map<?, ?> map = (Map<?, ?>) m;
				paramMap.put("role",map.get("ROLE_CODE"));
				if("R.302".equals(map.get("ROLE_CODE")) || "R.303".equals(map.get("ROLE_CODE"))){//个金RM 法金ARM
					nextNode = "126_a4";
					continue;
				}
			}
			//修改标识，更改为毫秒级
			String flag = DateUtils.currentTimeMillis();
			//此处为组装流程实例号，通过自定义标识加上业务主键id组装，在流程办理时候可以通过截取业务id查询业务信息
			String instanceid = "BGSQ"+"_"+custId+"_"+flag;//BGSQ:变更申请
			//自定义流程名称   自定义标识+业务custName
			String jobName = "个人账户变更申请书"+"_"+custName;
			
			//修改历史记录为：flag+"|0000" 
			//第一位表示	         对应各表 
			//第二位表示          修改标识 1新增 0修改
			//第三,四位对表ACRM_F_CI_ADDRESS,ACRM_F_CI_CONTMETH表示修改条数
			if(!custChangedJson.isEmpty()){//ACRM_F_CI_PERSON表变化0
				String isChange = judgeInsert("select * from ACRM_F_CI_PERSON p where p.cust_id = '"+custId+"'");
				String tempFlag = flag + "|0" + isChange + "00";
				privateApplyInfoService.bathsave(custChangedJson, new Date(), tempFlag, "个人账户变更申请书");
			}
			if(!custAddInfoJson.isEmpty()){//ACRM_F_CI_PERSON_ADDITIONAL表变化1
				String isChange = judgeInsert("select * from ACRM_F_CI_PERSON_ADDITIONAL a where a.cust_id = '"+custId+"'");
				String tempFlag = flag + "|1" + isChange + "00";
				privateApplyInfoService.bathsave(custAddInfoJson, new Date(), tempFlag, "个人账户变更申请书");
			}
			if(identChangedJson.size() > 0){//ACRM_F_CI_CUST_IDENTIFIER表变化2
				String isChange = judgeInsert("select * from ACRM_F_CI_CUST_IDENTIFIER i where i.cust_id = '"+custId+"' and i.ident_type = '"+identType+"'");
				String tempFlag = flag + "|2" + isChange + "00";
				privateApplyInfoService.bathsave(identChangedJson, new Date(), tempFlag, "个人账户变更申请书");
			}
			if(!addrChangedJson.isEmpty()){//ACRM_F_CI_ADDRESS表变化3
				for(int i=0;i<addrChangedJson.size();i++){
					JSONObject tempObject = JSONObject.fromObject(addrChangedJson.get(i));
					JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
					String isChange = "1".equals(tempObject.get("IS_ADD_FLAG"))?"1":"0";
					String tempFlag = flag + "|3" + isChange + df.format(i);
					privateApplyInfoService.bathsave(tempJsonArr,new Date(),tempFlag,"个人账户变更申请书");
				}
			}
			if(!contChangedJson.isEmpty()){//ACRM_F_CI_CONTMETH表变化4
				for(int i=0;i<contChangedJson.size();i++){
					JSONObject tempObject = JSONObject.fromObject(contChangedJson.get(i));
					JSONArray tempJsonArr = (JSONArray) tempObject.get("permodel");
					String isChange = "1".equals(tempObject.get("IS_ADD_FLAG"))?"1":"0";
					String tempFlag = flag + "|4" + isChange + df.format(i);
					privateApplyInfoService.bathsave(tempJsonArr, new Date(), tempFlag, "个人账户变更申请书");
				}
			}
			if(!keyChangedJson.isEmpty()){//ACRM_F_CI_PER_KEYFLAG表变化5
				String isChange = judgeInsert("select * from ACRM_F_CI_PER_KEYFLAG k where k.cust_id = '"+custId+"' ");
				String tempFlag = flag + "|5" + isChange + "00";
				privateApplyInfoService.bathsave(keyChangedJson, new Date(), tempFlag, "个人账户变更申请书");
			}
			if(!accountChangedJson.isEmpty()){//ACRM_F_CI_ACCOUNT_INFO表变化6
				String isChange = judgeInsert("select * from ACRM_F_CI_ACCOUNT_INFO a where a.cust_id ='"+custId+"' ");
				String tempFlag = flag + "|6" + isChange + "00";
				privateApplyInfoService.bathsave(accountChangedJson, new Date(), tempFlag, "个人账户变更申请书");
			}
			if(!serviceChangedJson.isEmpty()){//ACRM_F_CI_BANK_SERVICE表变化7
				String isChange = judgeInsert("select * from ACRM_F_CI_BANK_SERVICE s where s.cust_id ='"+custId+"' ");
				String tempFlag = flag + "|7" + isChange + "00";
				privateApplyInfoService.bathsave(serviceChangedJson, new Date(), tempFlag, "个人账户变更申请书");
			}
			
			//调用CommonService中的方法 发起工作流  第三个参数可以自定义一些变量 用于路由器条件等
			privateApplyInfoService.initWorkflowByWfidAndInstanceid("126", jobName, paramMap, instanceid);
			
			//创建响应内容
			response.getWriter().write("{\"instanceid\":\""+instanceid+"\",\"currNode\":\""+currNode+"\",\"nextNode\":\""+nextNode+"\"}");
			response.getWriter().flush();
		}catch (BizException e){
    		throw e;
    	} catch (Exception e){
    		e.printStackTrace();
			throw new BizException(1,2,"1002",e.getMessage());
    	}
		return "success";
	}
	
	/**
	 * 判断客户是否已在审批中
	 */
	public void judgeExistIn(){
		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String custId = request.getParameter("custId");
        String instancePre = "BGSQ"+custId;
        privateApplyInfoService.judgeExist("BGSQ_"+custId);
	}
	
	/**
	 * 判断修改信息insert or update
	 * @param sql
	 * @return 1:insert 0:update
	 * @throws SQLException
	 */
	public String judgeInsert(String sql) throws SQLException{
		StringBuffer sb = new StringBuffer(sql);
		QueryHelper query;
		query = new QueryHelper(sb.toString(), ds.getConnection());
		Result result = query.getResult();
		if(result.getRowCount() < 1){
			return "1";
		}
		return "0";
	}
	
	/**
	 * 利用session转换数据
	 */
	public String sessionForward(){
		ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
        String changedKeys = request.getParameter("changedKeys");
        JSONArray changedKeysJson = JSONArray.fromObject(changedKeys);
        HttpSession session = request.getSession(true);
        
        session.setAttribute("changedKeys", changedKeysJson);
        
        
		return "success";
	}
	
}
