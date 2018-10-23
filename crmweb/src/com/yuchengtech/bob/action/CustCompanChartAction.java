package com.yuchengtech.bob.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;

@ParentPackage("json-default")
@Action(value = "/custcompanchart", results = { @Result(name = "success", type = "json") })
public class CustCompanChartAction {

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	private HttpServletRequest request;
	
	private Map member = new HashMap();
	
	private String dataXml; 


	@SuppressWarnings("unchecked")
	public String index() throws Exception {

		ActionContext ctx = ActionContext.getContext();

		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);

		// 集团客户ID
        String customerId = request.getParameter("customerId");

		//@SuppressWarnings("rawtypes")
	//	List list = QueryCustomerRelationAction.getList(customerId);
//		StringBuilder stringBuilder;
    //    dataXml = queryGroupImg(list);
//        	"<?xml version='1.0' encoding='gb2312'?>" +
//        		"<custnet> " +
//        		"<dot id='1' name='王一平' flag='1' rank=''/>" +
//        		"<dot id='2' name='李丽' flag='1' rank=''/>" +
//        		"<dot id='3' name='文博贸易有限公司' flag='0' rank=''/>" +
//        		"<dot id='4' name='君昊贸易有限公司' flag='0' rank=''/>" +
//        		"<dot id='5' name='李海' flag='1' rank=''/>" +
//        		"<dot id='6' name='张卓' flag='1' rank=''/>" +
//        		"<dot id='7' name='圣杰科技有限公司' flag='0' rank=''/>" +
//        		"<dot id='8' name='建辉科技有限公司' flag='0' rank=''/>" +
//        		"<line id='500' name='夫妻' orientation='正向' dot_start='1' dot_end='2' proportion=''/>" +
//        		"<line id='501' name='法定代表' orientation='正向' dot_start='1' dot_end='3' proportion=''/>" +
//        		"<line id='502' name='总经理' orientation='正向' dot_start='1' dot_end='4' proportion=''/>" +
//        		"<line id='503' name='同事' orientation='正向' dot_start='1' dot_end='5' proportion=''/>" +
//        		"<line id='504' name='旁系亲属' orientation='正向' dot_start='1' dot_end='6' proportion=''/>" +
//        		"<line id='505' name='联营' orientation='正向' dot_start='4' dot_end='7' proportion=''/>" +
//        		"<line id='506' name='供求关系' orientation='正向' dot_start='4' dot_end='8' proportion=''/>" +
//        		"<line id='507' name='投资企业' orientation='正向' dot_start='3' dot_end='7' proportion=''/>" +
//        		"</custnet>";
		
		return "success";
	}
	
	public String queryGroupImg(List list) {
		StringBuffer sb = new StringBuffer("");
		if (list.size() > 0) {
//			int rank = 0; //当前层级
//			TreeSet curParentSet = new TreeSet(); //当前父节点
//			TreeSet nextParentSet = new TreeSet(); //下层父节点
//			curParentSet.add("0");
			String memberCustName = "";
			String flag = "0";
			String relationName = "";
			sb.append("<?xml version='1.0' encoding='gb2312'?>");
			sb.append("<custnet>");
			for (int i = 0; i <= list.size(); i++) {
				if (i==0) {
					member = (Map) list.get(i);
					memberCustName=member.get("CUST_NAME").toString();
					flag=member.get("CUST_STAT").toString().equals("2")?"1":"0";
				}else {
					member = (Map) list.get(i-1);
					memberCustName=member.get("RELA_CUST_NAME").toString();
					flag=member.get("RELA_CUST_STAT").toString().equals("2")?"1":"0";
				}
				sb.append("<dot id='"+(i+1)+"' name='"+memberCustName+"' flag='"+flag+"' rank='0'/>");
			}
			for (int i = 0; i < list.size(); i++){
				member = (Map) list.get(i);
				relationName = member.get("F_VALUE").toString();
				sb.append("<line id='500"+i+"' name='"+relationName+"' orientation='正向' dot_start='1' dot_end='"+(i+2)+"' proportion=''/>");
			}
			sb.append("</custnet>");
		}
		return sb.toString();
		
	}
	public String getDataXml() {
		return dataXml;
	}
}