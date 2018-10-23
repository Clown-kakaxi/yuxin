package com.yuchengtech.bob.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.service.CommonQueryService;

@ParentPackage("json-default")
@Action(value = "/rollMemberQuery", results = { @Result(name = "success", type = "json"), })
public class RollMemberQueryAction extends BaseAction {

	@Autowired
	private CommonQueryService cqs ;

	private HttpServletRequest request;

	public String index() {

		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		String rollId = request.getParameter("rollId");

		StringBuilder sb = new StringBuilder(
				"select rc.*,u1.username as CREATE_USER_NAME,u2.username as UPDATE_USER_NAME from OCRM_F_MM_RCUST_LIST rc "
						+ "left join sys_users u1 on rc.CREATE_USER = u1.userid "
						+ "left join sys_users u2 on rc.UPDATE_USER = u2.userid "
						+ "where rc.ROLL_ID = "
						+ rollId
						+ " order by rc.UPDATE_DATE desc");
		
		cqs.setPrimaryKey("rc.CUST_ID");
		this.setJson((Map<String, Object>) cqs.excuteQuery(sb.toString(),
				this.getStart(), this.getLimit()));
		return "success";
	}

	
	@SuppressWarnings("unchecked")
	public String getCustListStr(String rollId) {

		StringBuilder sb = new StringBuilder(
				"select rc.cust_id from OCRM_F_MM_RCUST_LIST rc where rc.roll_id = "
						+ rollId);

		cqs.setPrimaryKey("CUST_ID");
		Map<String, Object> map = (Map<String, Object>) cqs.excuteQuery(
				sb.toString(), 0, 999999999);

		List list = (List) map.get("data");

		Map<String, Object> tempMap;

		String tempCustId;
		
		StringBuilder tempIdBui = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			tempMap = (Map<String, Object>) list.get(i);
			tempIdBui.append("'");
			tempCustId = (String) tempMap.get("CUST_ID");
			tempIdBui.append(tempCustId);
			tempIdBui.append("'");
			if (i != list.size() - 1)
				tempIdBui.append(",");
		}
		return tempIdBui.toString();
	}
	
	@SuppressWarnings("unchecked")
    public List<String> getCustName(String rollId){
	    StringBuilder sb = new StringBuilder(
                "select rc.cust_name from OCRM_F_MM_RCUST_LIST rc where rc.roll_id = "
                        + rollId);

        cqs.setPrimaryKey("CUST_NAME");
        Map<String, Object> map = (Map<String, Object>) cqs.excuteQuery(
                sb.toString(), 0, 999999999);
        
        List list = (List) map.get("data");
        List<String> list2 = new ArrayList<String>();
        Map<String, Object> temp;
        for(int i=0;i<list.size();i++){
            temp = (Map<String, Object>)list.get(i);
            String string=(String) temp.get("CUST_NAME");
            list2.add(string);
        }
        return list2;
	}
}
