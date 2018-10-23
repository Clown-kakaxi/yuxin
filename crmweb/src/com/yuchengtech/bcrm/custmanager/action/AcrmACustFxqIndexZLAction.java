package com.yuchengtech.bcrm.custmanager.action;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.custmanager.model.AcrmAAntiTargetFact;
import com.yuchengtech.bcrm.custmanager.model.AcrmACustFxqIndex;
import com.yuchengtech.bcrm.custmanager.model.AcrmAntiIndexInstruction;
import com.yuchengtech.bcrm.custmanager.service.AcrmACustFxqIndexService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;

/**
 * @describe 反洗钱指标查询--指标录入--合规处经办
 * @since 2014-09-12
 *
 */
@Action("/acrmACustFxqIndexZL")
public class AcrmACustFxqIndexZLAction extends CommonAction {
	private static final long serialVersionUID = 1L;
	@Autowired
    private  AcrmACustFxqIndexService  service;

	AuthUser auth = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	@Autowired
	public void init() {
		model = new AcrmACustFxqIndex();
		setCommonService(service);
	}
	/**
	 * 保存客户反洗钱设置
	 * @throws ParseException 
	 */
	 public void save() throws ParseException {
			ActionContext ctx = ActionContext.getContext();
			HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
//		    String createDate = request.getParameter("createDate");//新旧客户
		    String flag = request.getParameter("flag");//3 新客户 1,2 老客户
		    String custId = request.getParameter("custId");
//		    String custName = request.getParameter("custName");
		    String custType = request.getParameter("custType");//2对私，1对公
		    String kyjybgSbsj=request.getParameter("kyjybgSbsj");
		    String instructtonContent1=request.getParameter("instructtonContent1");//保存记录说明
		   if((instructtonContent1!=null&!"".equals(instructtonContent1))||((!"".equals(kyjybgSbsj))&kyjybgSbsj!=null)){
		    	//指标说明
		    	SimpleDateFormat simpleTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//定义日期格式  默认时间格式：yyyy-MM-dd HH:mm:ss  
		    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    	
		    	
		    	AcrmAntiIndexInstruction ws=new AcrmAntiIndexInstruction();
		    	if(!"".equals(kyjybgSbsj)&&kyjybgSbsj!=null){
			    	ws.setKyjybgSbsj(sdf.parse(kyjybgSbsj));
		    	}
		    	ws.setCustId(custId);
		    	ws.setInstructionContent(instructtonContent1);
		    	ws.setInstrTime(simpleTime.format(new Date()).toString());
		    	ws.setInstrUser(auth.getUserId());
		    	service.save(ws);
		    	
		    }
		    
		    
		    // 是1否0
		    Map<String, String> map = new HashMap<String, String>();
		    
		   //是否为代理开户更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
		   // map.put("fxq006", request.getParameter("FXQ006"));
		    map.put("fxq007", request.getParameter("FXQ007"));
		    map.put("fxq008", request.getParameter("FXQ008"));
		    map.put("fxq009", request.getParameter("FXQ009"));
		    map.put("fxq010", request.getParameter("FXQ010"));
		    
		    map.put("fxq011", request.getParameter("FXQ011"));
		    map.put("fxq012", request.getParameter("FXQ012"));
		    map.put("fxq013", request.getParameter("FXQ013"));
		    map.put("fxq014", request.getParameter("FXQ014"));
		    map.put("fxq015", request.getParameter("FXQ015"));
		    map.put("fxq016", request.getParameter("FXQ016"));
		    
		    map.put("fxq021", request.getParameter("FXQ021"));
		    map.put("fxq022", request.getParameter("FXQ022"));
		    map.put("fxq023", request.getParameter("FXQ023"));
		    map.put("fxq024", request.getParameter("FXQ024"));
		    map.put("fxq025", request.getParameter("FXQ025"));
		    map.put("fxq026", request.getParameter("FXQ026"));
		    AcrmACustFxqIndex index = new AcrmACustFxqIndex();
		    // 删除原有AcrmACustFxqIndex
	    	service.batchUpdateByName(" delete from AcrmACustFxqIndex s where s.custId='"+custId+"'", null);
		    //添加AcrmACustFxqIndex
	    	index.setCustId(custId);
	    	//是否为代理开户更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
			//index.setFxq006(request.getParameter("FXQ006"));
	    	index.setFxq007(request.getParameter("FXQ007"));
	    	index.setFxq008(request.getParameter("FXQ008"));
	    	index.setFxq009(request.getParameter("FXQ009"));
	    	index.setFxq010(request.getParameter("FXQ010"));
	    	index.setFxq011(request.getParameter("FXQ011"));
	    	index.setFxq012(request.getParameter("FXQ012"));
	    	index.setFxq013(request.getParameter("FXQ013"));
	    	index.setFxq014(request.getParameter("FXQ014"));
	    	index.setFxq015(request.getParameter("FXQ015"));
	    	index.setFxq016(request.getParameter("FXQ016"));
	    	index.setFxq021(request.getParameter("FXQ021"));
	    	index.setFxq022(request.getParameter("FXQ022"));
	    	index.setFxq023(request.getParameter("FXQ023"));
	    	index.setFxq024(request.getParameter("FXQ024"));
	    	index.setFxq025(request.getParameter("FXQ025"));
	    	index.setFxq026(request.getParameter("FXQ026"));
	    	service.save(index);
		    
		    Connection conn=null;
    		Statement statement=null;
    		ResultSet rs = null;
    		String nowDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    		System.out.println(nowDate);
		    for(String key:map.keySet()){
		    	if(null!=map.get(key)&&!map.get(key).equals("")){
		    		try {
			    		conn = ds.getConnection();
						statement = conn.createStatement();
						
			    		// 删除客户原有客户反洗钱指标
//				    	service.batchUpdateByName(" delete from AcrmATargetFact s where s.custId='"+custId+"'", null);
				    	//添加新的客户反洗钱指标
				    	String sql = "select * from OCRM_F_CI_BELONG_ORG o where o.cust_id='"+custId+"'"; 
				    	rs = statement.executeQuery(sql);
				    	String orgId = "";
				    	if(rs.next()){
				    		orgId = rs.getString("INSTITUTION_CODE");
				    	}
				    	AcrmAAntiTargetFact list = new AcrmAAntiTargetFact();
				    	list.setCustId(custId);
				    	list.setEtlDate(new Date());
				    	/**
				    	 * 客户是否为代理开户
				    	 */
				    	//是否为代理开户更改为：ocrm_f_ci_agent_tmp 字段：FLAG_AGENT 
						   //
				    	/*if("fxq006".equals(key)){
				    		if("3".equals(flag)){//新客户
				    			saveList(list,"FXQ11006",rs,statement,custId,orgId,map.get(key),false);
					    	}else{
					    		saveList(list,"FXQ12006",rs,statement,custId,orgId,map.get(key),false);
					    	}
				    	}*/
				    	/**
				    	 * 客户办理的业务FXQ007(对私),FXQ025(对公)
				    	 */
				    	if("fxq007".equals(key) || "fxq025".equals(key)){
				    		//新客户
				    		if("3".equals(flag)){
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11007",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21011",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		//老客户	
					    	}else{
					    		if("2".equals(custType)){//自然人老客户
					    			saveList(list,"FXQ12007",rs,statement,custId,orgId,map.get(key),false);
					    		}
					    		if("1".equals(custType)){//非自然人老客户
					    			saveList(list,"FXQ22011",rs,statement,custId,orgId,map.get(key),false);
					    		}
					    	}
				    	}
				    	/**
				    	 * 是否涉及风险提示信息或权威媒体报道信息  FXQ008
				    	 */
				    	if("fxq008".equals(key)){
				    		//新客户
				    		if("3".equals(flag)){
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11008",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21012",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		//老客户	
					    	}else{
					    		if("2".equals(custType)){//自然人老客户
					    			saveList(list,"FXQ12008",rs,statement,custId,orgId,map.get(key),false);
					    		}
					    		if("1".equals(custType)){//非自然人老客户
					    			saveList(list,"FXQ22012",rs,statement,custId,orgId,map.get(key),false);
					    		}
					    	}
				    	}
				    	/**
				    	 * 客户或其亲属、关系密切人等是否属于外国政要  FXQ009
				    	 */
				    	if("fxq009".equals(key)){
//				    		if("1".equals(flag)){//新客户
//				    			saveList(list,"FXQ11009",rs,statement,custId,orgId,map.get(key),false);
//					    	}else{
//					    		saveList(list,"FXQ12009",rs,statement,custId,orgId,map.get(key),false);
//					    	}
				    		//新客户
				    		if("3".equals(flag)){
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11009",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21010",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		//老客户	
					    	}else{
					    		if("2".equals(custType)){//自然人老客户
					    			saveList(list,"FXQ12009",rs,statement,custId,orgId,map.get(key),false);
					    		}
					    		if("1".equals(custType)){//非自然人老客户
					    			saveList(list,"FXQ22010",rs,statement,custId,orgId,map.get(key),false);
					    		}
					    	}
				    	}
				    	/**
				    	 * 反洗钱交易监测记录 FXQ010
				    	 */
				    	if("fxq010".equals(key)){
				    		if(!"3".equals(flag)){//老客户
				    			if("2".equals(custType)){//自然人老客户
				    				saveList(list,"FXQ12010",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人老客户
				    				saveList(list,"FXQ22013",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}else if("3".equals(flag)){//新客户
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11010",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21013",rs,statement,custId,orgId,map.get(key),true);
				    			}				    			
				    		}
				    	}
				    	/**
				    	 * 是否被列入中国发布或承认的应实施反洗钱监控措施的名单 FXQ011
				    	 */
				    	if("fxq011".equals(key)){
				    		if(!"3".equals(flag)){//老客户
				    			if("2".equals(custType)){//自然人老客户
				    				saveList(list,"FXQ12011",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人老客户
				    				saveList(list,"FXQ22014",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}else if("3".equals(flag)){//新客户
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11011",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21014",rs,statement,custId,orgId,map.get(key),true);
				    			}				    			
				    		}
				    	}
				    	/**
				    	 * 是否发生具有异常特征的大额现金交易 FXQ012
				    	 */
				    	if("fxq012".equals(key)){
				    		if(!"3".equals(flag)){//老客户
				    			if("2".equals(custType)){//自然人老客户
				    				saveList(list,"FXQ12012",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人老客户
				    				saveList(list,"FXQ22015",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}else if("3".equals(flag)){//新客户
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11012",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21015",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}
				    	}
				    	/**
				    	 * 是否发生具有异常特征的非面对面交易FXQ013
				    	 */
				    	if("fxq013".equals(key)){
				    		if(!"3".equals(flag)){//老客户
				    			if("2".equals(custType)){//自然人老客户
				    				saveList(list,"FXQ12013",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人老客户
				    				saveList(list,"FXQ22016",rs,statement,custId,orgId,map.get(key),true);
				    		    }  
				    		}else if("3".equals(flag)){//新客户
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11013",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21016",rs,statement,custId,orgId,map.get(key),true);
				    		    }  
				    		}	
				    	}
				    	/**
				    	 * 是否存在多次涉及跨境异常交易报告FXQ014
				    	 */
				    	if("fxq014".equals(key)){
				    		if(!"3".equals(flag)){//老客户
					    		if("2".equals(custType)){//自然人老客户
					    				saveList(list,"FXQ12014",rs,statement,custId,orgId,map.get(key),true);
					    		   }
					    		if("1".equals(custType)){//非自然人老客户
					    			saveList(list,"FXQ22017",rs,statement,custId,orgId,map.get(key),true);
					    		 }
				    		}else if("3".equals(flag)){//新客户
					    		if("2".equals(custType)){//自然人新客户
					    				saveList(list,"FXQ11014",rs,statement,custId,orgId,map.get(key),true);
					    		   }
					    		if("1".equals(custType)){//非自然人新客户
					    			saveList(list,"FXQ21017",rs,statement,custId,orgId,map.get(key),true);
					    		 }
				    		}
				    	}
				    	
				    	/**
				    	 * 代办业务是否存在异常情况FXQ015
				    	 */
				    	if("fxq015".equals(key)){
				    		if(!"3".equals(flag)){//老客户
					    		if("2".equals(custType)){//自然人老客户
					    			saveList(list,"FXQ12015",rs,statement,custId,orgId,map.get(key),true);
					    		}
					    		if("1".equals(custType)){//非自然人老客户
					    			saveList(list,"FXQ22018",rs,statement,custId,orgId,map.get(key),true);
					    		}
				    		}else if("3".equals(flag)){//新客户
					    		if("2".equals(custType)){//自然人新客户
					    			saveList(list,"FXQ11015",rs,statement,custId,orgId,map.get(key),true);
					    		}
					    		if("1".equals(custType)){//非自然人新客户
					    			saveList(list,"FXQ21018",rs,statement,custId,orgId,map.get(key),true);
					    		}
				    		}
				       }
				    	
				    	/**
				    	 * 是否频繁进行异常交易 FXQ016
				    	 */
				    	if("fxq016".equals(key)){
				    		if(!"3".equals(flag)){//老客户
				    			if("2".equals(custType)){//自然人老客户
				    				saveList(list,"FXQ12016",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人老客户
				    				saveList(list,"FXQ22019",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}else if("3".equals(flag)){//新客户
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11016",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21019",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}
				    	}
				    	
				    	/**
				    	 * 与客户建立业务关系的渠道FXQ021
				    	 */
				    	if("fxq021".equals(key)){
				    		if("1".equals(custType)){//非自然人
				    			if("3".equals(flag)){//非自然人新客户
				    				saveList(list,"FXQ21006",rs,statement,custId,orgId,map.get(key),false);
				    			}else{//非自然人老客户
				    				saveList(list,"FXQ22006",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		}
				    	}
				    	/**
				    	 * 是否在规范证券市场上市FXQ022
				    	 */
				    	if("fxq022".equals(key)){
				    		if("1".equals(custType)){//非自然人
				    			if("3".equals(flag)){//非自然人新客户
				    				saveList(list,"FXQ21007",rs,statement,custId,orgId,map.get(key),false);
				    			}else{//非自然人老客户
				    				saveList(list,"FXQ22007",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		}
				    	}
				    	
				    	/**
				    	 * 客户的股权或控制权结构FXQ023
				    	 */
				    	if("fxq023".equals(key)){
				    		if("1".equals(custType)){//非自然人
				    			if("3".equals(flag)){//非自然人新客户
				    				saveList(list,"FXQ21008",rs,statement,custId,orgId,map.get(key),false);
				    			}else{//非自然人老客户
				    				saveList(list,"FXQ22008",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		}
				    	}
				    	
				    	/**
				    	 * 客户是否存在隐名股东或匿名股东FXQ024
				    	 */
				    	if("fxq024".equals(key)){
				    		if("1".equals(custType)){//非自然人
				    			if("3".equals(flag)){//非自然人新客户
				    				saveList(list,"FXQ21009",rs,statement,custId,orgId,map.get(key),false);
				    			}else{//非自然人老客户
				    				saveList(list,"FXQ22009",rs,statement,custId,orgId,map.get(key),false);
				    			}
				    		}
				    	}
				    	

				      	/**
				    	 * 客户所在行政区域是否存在严重犯罪 FXQ026
				    	 */
				    	if("fxq026".equals(key)){
				    		if(!"3".equals(flag)){//老客户
				    			if("2".equals(custType)){//自然人老客户
				    				saveList(list,"FXQ12020",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人老客户
				    				saveList(list,"FXQ22020",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}else if("3".equals(flag)){//新客户
				    			if("2".equals(custType)){//自然人新客户
				    				saveList(list,"FXQ11020",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    			if("1".equals(custType)){//非自然人新客户
				    				saveList(list,"FXQ21020",rs,statement,custId,orgId,map.get(key),true);
				    			}
				    		}
				    	}

				    	
		    		} catch (SQLException e) {
						e.printStackTrace();
					}finally{
						JdbcUtil.close(rs, statement, conn);
					}
		    	}
		    }
		 }
	 /**
	  * 客户反洗钱指标页面设值保存到ACRM_A_ANTI_TARGET_FACT
	  * @param list
	  * @param indexCode
	  * @param rs
	  * @param statement
	  * @param custId
	  * @param orgId
	  * @throws SQLException
	  */
	 public void saveList(AcrmAAntiTargetFact list,String indexCode,ResultSet rs,Statement statement,String custId,String orgId,String key,boolean flag) throws SQLException{
		    list.setIndexCode(indexCode);
			list.setIndexId(indexCode+new SimpleDateFormat("yyyyMMdd").format(new Date())+custId);
			list.setOrgId(orgId);
//			if(flag){//合规处指标不需要flag标记
//				list.setFlag(null);
//			}else{
				list.setFlag(key);
//			}
			String[] str = key.split(",");
			StringBuffer sb = new StringBuffer();
			for(String s:str){
				sb.append(",'").append(s.toString()).append("'");
			}
			String sql = " select decode(sum((decode(v.high_flag,'1',1,0))),null,0,sum((decode(v.high_flag,'1',1,0)))) as high_flag," +
					" decode(sum(v.index_score*v.index_right*0.01),null,0,sum(v.index_score*v.index_right*0.01)) as amount"+
					" from OCRM_F_CI_ANTI_MONEY_INDEX_VAR v" +
					" where v.index_code ='"+indexCode+"' and v.index_value in ("+sb.toString().substring(1)+")";
			rs = statement.executeQuery(sql);
			if(rs.next()){
				list.setHighFlag(BigDecimal.valueOf(Double.parseDouble(rs.getString("HIGH_FLAG").toString())));
				list.setIndexValue(BigDecimal.valueOf(Double.parseDouble(rs.getString("AMOUNT").toString())));
			}
			// 删除客户原有客户反洗钱指标
	    	service.batchUpdateByName(" delete from AcrmAAntiTargetFact s where s.custId='"+custId+"' and s.indexCode = '"+indexCode+"'", null);
			
	    	service.save(list);
	 }
}
