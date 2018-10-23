package com.yuchengtech.bcrm.customer.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.rest.DefaultHttpHeaders;
import org.apache.struts2.rest.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.exception.BizException;
		
@ParentPackage("json-default")
@Action("/getSearchCol")
public class CustSearchByDetailTypeAction extends CommonAction {
	
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	private HttpServletRequest request;
	
	//获取查询条件 
	public String loadTitleRs(){
 		json = loadTitle();
 		return "success";
 	}
	
	public Map<String, Object> loadTitle(){
		Connection conn = null ;
    	Statement stmt = null ;
    	ResultSet rs = null;
    	Statement stmt1 = null ;
    	ResultSet rs1 = null;
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		
		Map<String, Object> result = new HashMap<String, Object>();
        List<HashMap<String, Object>> rowsList = new ArrayList<HashMap<String, Object>>();
        
        String  DBTABLE_ID = request.getParameter("DBTABLE_ID");//获取查询数据集id
        
        //查询数据集中的字典映射字段和金额(添加order by，确保查询条件和前台传入的值顺序对应)
        String dicSearch = "select col_name_e,col_name_c,notes,decode(col_type,'NUMBER','num','DECIMAL','num','dic') as type" +
        		" from  mtool_dbcol m where dbtable_id='"+DBTABLE_ID+"' and (notes is not null or col_type in ('NUMBER','DECIMAL')) order by id";
        //字典编号
        String dicName = "";
        //查询字典
        String dic = "select f_value,f_code from OCRM_SYS_LOOKUP_ITEM where f_lookup_id='";
        int size = 0;//字段数
        try {
        	conn=ds.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(dicSearch);
            while(rs.next()){
            	++size;
            	HashMap<String, Object> map = new HashMap<String, Object>();
            	map.put("colNameE", rs.getObject("col_name_e"));
            	map.put("colNameC", rs.getObject("col_name_c"));
            	map.put("type", rs.getObject("type"));
            	if("dic".equals(rs.getString("type"))){//子典型的
            		dicName = rs.getString("notes");
                	stmt1 = conn.createStatement();
                	rs1 = stmt1.executeQuery(dic+dicName+"'");
                	 List r_mapList = new ArrayList();
                	 while(rs1.next()){
                		 HashMap<String, Object> rsmap = new HashMap<String, Object>();
                		 rsmap.put("fCode",rs1.getString("f_code"));
    					 rsmap.put("fValue",rs1.getString("f_value") );
    					 r_mapList.add(rsmap);
                	 }
                	 map.put("fCodeL", r_mapList);
            	}
            	 rowsList.add(map);	
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
        	JdbcUtil.close(rs1, stmt1, null);
        	JdbcUtil.close(rs, stmt, conn);
        }
        result.put("data", rowsList);
		result.put("count", size);
		return result;
		
	}

    public void prepare() {
		ActionContext ctx = ActionContext.getContext();
		Connection conn = null ;
    	Statement stmt = null ;
    	ResultSet rs = null;
    	Statement stmt1 = null ;
    	ResultSet rs1 = null;
    	
		request = (HttpServletRequest) ctx
				.get(ServletActionContext.HTTP_REQUEST);
		
		List r_mapList = new ArrayList();//用于存储需要添加字典转换的字段
		//获取DBTABLE_ID和pramas
		String DBTABLE_ID = request.getParameter("DBTABLE_ID");
		String pramas = request.getParameter("pramas");
		
		//查询语句
		StringBuilder sb = new StringBuilder("");
		sb.append(" select * from ");
		//拼接查询语句
		//表名
		String table = " select value from mtool_dbtable where id='"+DBTABLE_ID+"'";
		//字段
		String colunm = " select col_name_e,col_name_c,notes,decode(col_type,'NUMBER','num','DECIMAL','num','dic') as type" +
        		" from  mtool_dbcol m where dbtable_id='"+DBTABLE_ID+"' and (notes is not null or col_type in ('NUMBER','DECIMAL')) order by id";
		 try {
	        	conn=ds.getConnection();
	            stmt = conn.createStatement();
	            rs = stmt.executeQuery(table);
	            while(rs.next()){
	            	sb.append(rs.getString("value"));
	            }
	            sb.append(" where 1=1 ");
	            
	            if(pramas!=null&&!("".equals(pramas))){//pramas==null用于在点击查询之前(直接在bbar中点击刷新的时候）此时不拼接条件
	    			String parmaarray[] = pramas.split(":");
		            stmt1 = conn.createStatement();
		            rs1 = stmt1.executeQuery(colunm);
		            int i=0;
		            String type = "";
		            while(rs1.next()){
		            	type = rs1.getString("type");
		            	if("dic".equals(type)){
		            		if(!"empty".equals(parmaarray[i])){
			            		String parma = parmaarray[i].replace("a", "00|0");//将用a代替的0或00换回来
			            		sb.append("and ");
			            		sb.append(rs1.getString("col_name_e"));
			            		sb.append(" in ('");
			            		sb.append(parma.replace("|", "','"));
			            		sb.append("') ");
			            		if(rs1.getString("notes")!=null||!"".equals(rs1.getString("notes"))){
			            			HashMap<String, Object> map = new HashMap<String, Object>();
			            			map.put("name",rs1.getString("col_name_e") );
			            			map.put("note", rs1.getString("notes"));
			            			r_mapList.add(map);
			            		}
			            	}
		            	}else if("num".equals(type)){
		            		String text[] = parmaarray[i].replace("|", ",").split(",");//直接用|分割有问题
		            		if(!"empty".equals(text[0])){
		            			sb.append("and ");
			            		sb.append(rs1.getString("col_name_e"));
			            		sb.append(" >=");
			            		sb.append(text[0]);
			            		sb.append(" ");
		            		}
		            		if(!"empty".equals(text[1])){
		            			sb.append("and ");
			            		sb.append(rs1.getString("col_name_e"));
			            		sb.append(" <=");
			            		sb.append(text[1]);
			            		sb.append(" ");
		            		}
		            	}
		            	i++;
		            }
	    		}
	    		
		 }catch (SQLException e) {
	         e.printStackTrace();
        }finally{
        	JdbcUtil.close(rs1, stmt1, null);
        	JdbcUtil.close(rs, stmt, conn);
        }
        setPrimaryKey("cust_id");
        for(int i=0;i<r_mapList.size();i++)
        {
        	HashMap<String, Object> map = (HashMap<String, Object>)r_mapList.get(i);
        	addOracleLookup(map.get("name").toString(), map.get("note").toString());
        }
	        
        SQL=sb.toString();
        datasource = ds;
	}
    
  //获取结果字段
	public HttpHeaders getResult(){
		Connection conn = null ;
    	Statement stmt = null ;
    	ResultSet rs = null;
		ActionContext ctx = ActionContext.getContext();
		request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		
		String cols = "";//列模型
		String fileds = "";//取值
		String types = "";//类型
		StringBuilder colsb = new StringBuilder("");
		StringBuilder filedsb = new StringBuilder("");
		StringBuilder typesb = new StringBuilder("");
	
		String DBTABLE_ID = request.getParameter("DBTABLE_ID");
		String pramas = request.getParameter("pramas");
		String parmaarray[] = pramas.split(":");
		
		if("45273".equals(DBTABLE_ID)||"48531".equals(DBTABLE_ID)){//添加固定列
        	cols ="客户号|客户名称|";
        	fileds ="CUST_ID|CUST_ZH_NAME|";
        	types = "t|t|";
        }else if("48273".equals(DBTABLE_ID)){
        	cols ="客户号|客户名称|";
        	fileds ="CUST_ID|CUST_CN_NAME|";
        	types = "t|t|";
        }
		
		 try {
	        	conn=ds.getConnection();
	          //字段
	    		String colunm = " select col_name_e,col_name_c,notes,decode(col_type,'NUMBER','num','DECIMAL','num','dic') as type" +
        		" from  mtool_dbcol m where dbtable_id='"+DBTABLE_ID+"' and (notes is not null or col_type in ('NUMBER','DECIMAL')) order by id";
	    		stmt = conn.createStatement();
		        rs = stmt.executeQuery(colunm);
		        int i=0;
		        String type = "";
		            while(rs.next()){
		            	type = rs.getString("type");
		            	if("dic".equals(type)){
		            		if(!"empty".equals(parmaarray[i])){
			            		colsb.append(rs.getString("col_name_c"));
			            		colsb.append("|");
			            		if(rs.getString("notes")!=null||!"".equals(rs.getString("notes"))){
			            			filedsb.append(rs.getString("col_name_e"));
			            			filedsb.append("_ORA");
			            		}else
			            			filedsb.append(rs.getString("col_name_e"));
			            		filedsb.append("|");
			            		typesb.append("t|");
			            		
			            	}
		            	}else if("num".equals(type)){
		            		String text[] = parmaarray[i].replace("|", ",").split(",");//直接用|分割有问题
		            		if(!("empty".equals(text[0])&&"empty".equals(text[1]))){//非都为空则添加
		            			colsb.append(rs.getString("col_name_c"));
		            			colsb.append("|");
		            			filedsb.append(rs.getString("col_name_e"));
		            			filedsb.append("|");
		            			typesb.append("n|");
		            		}
		            		
		            	}
		            	
		            	i++;
		            }
	            }catch (SQLException e) {
	        		throw new BizException(1,2,"1002",e.getMessage());
		        }finally{
		        	JdbcUtil.close(rs, stmt, conn);
		        }
	
		cols+=colsb.toString();
		fileds+=filedsb.toString();
		types+=typesb.toString();
		
		if(this.json!=null)
			this.json.clear();
		else 
			this.json = new HashMap<String,Object>();  
		
		this.json.put("cols", cols.subSequence(0, cols.length()-1));
		this.json.put("fileds", fileds.subSequence(0, fileds.length()-1));
		this.json.put("types", types.subSequence(0, types.length()-1));
		
		return new DefaultHttpHeaders("success").disableCaching();
	}
	
}

