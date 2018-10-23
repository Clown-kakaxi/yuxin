package com.yuchengtech.bcrm.customer.constance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yuchengtech.bcrm.system.model.DataSet;
import com.yuchengtech.bcrm.system.model.DataSetColumn;
import com.yuchengtech.bcrm.system.model.OcrmFASsRelation;

/**
 * 灵活查询SQL生成器，单态模式，线程非安全
 * @author WILLJOE
 * @since 2013-04-25
 */
public class AgileSearchConstance {
	
	/**
	 * 条件数据操作符
	 */
	public static final Map<String,String> conditonOperates = new HashMap<String,String>();
	static {
		conditonOperates.put("0000", "包含");
		conditonOperates.put("0001", "大于");
		conditonOperates.put("0002", "等于");
		conditonOperates.put("0003", "小于");
		conditonOperates.put("0004", "大于等于");
		conditonOperates.put("0005", "小于等于");
	}
	public static final String OP_INCLUDE = "0000";
	public static final String OP_LARGER = "0001";
	public static final String OP_EQUAL = "0002";
	public static final String OP_SMALLER = "0003";
	public static final String OP_E_LARGER = "0004";
	public static final String OP_E_SAMLLER = "0005";
	
	/**
	 * 连表类型
	 */
	public static final Map<String,String> joinType = new HashMap<String,String>();
	static {
		joinType.put("left", "LEFT JOIN");
		joinType.put("right", "RIGHT JOIN");
		joinType.put("inner", "INNER JOIN");
	}
	
	/**
	 * 客户基本信息表
	 */
	public static final String CUST_MAIN_INFO_TABLE = "ACRM_F_CI_CUSTOMER";
	
	/**
	 * 条件连接符
	 */
	public static final String OR = "OR";
	public static final String AND = "AND";
	
	/**
	 * 数据类型列表
	 */
	public static final List<String> dataTypes = new ArrayList<String>();
	static{
		dataTypes.add("VARCHAR2");
		dataTypes.add("DATE");
		dataTypes.add("NUMBER");
		dataTypes.add("DECIMAL");
		dataTypes.add("INTEGER");
		dataTypes.add("VARCHAR");
		dataTypes.add("CHAR");
		dataTypes.add("BIGINT");
	}
	
	/**
	 * 排序类型
	 */
	public static final String ASC = "ASC";
	public static final String DESC = "DESC";
	//分隔符
	public static final String seporator = ",";
	
	/**
	 * 创建表关联语句
	 * @param datasets ：表关联涉及的datasets对象列表
	 * @param relations ： 表关联需要的关联对象列表
	 * @param joinColumns ： 表关联涉及的列表列对象
	 * @return 返回连表的语句，同时返填datasets中数据里notes属性，该属性存储本次查询中的表别名
	 */
	public static final String createTableJoin(List<DataSet> datasets, List<OcrmFASsRelation> relations,List<DataSetColumn> joinColumns){
		
		String mainTable = CUST_MAIN_INFO_TABLE +" custinfo";
		if(datasets.size() == 1){
			datasets.get(0).setNotes("custinfo");
			return mainTable;
		}
		
		
		for(int i=0;i<datasets.size();i++){
			
			DataSet ds = datasets.get(i);
			if(ds.getValue().equals(CUST_MAIN_INFO_TABLE)){
				ds.setNotes("custinfo");
				continue;
			}
			ds.setNotes("t"+i);
			OcrmFASsRelation re = getRelationsByTable(relations,ds);
			mainTable += " LEFT JOIN "+ds.getValue()+" "+ds.getNotes()+" ON custinfo.";
			DataSetColumn leftColumn = getJoinColumnById(joinColumns ,re.getJoinLeftCol());
			DataSetColumn rightColumn = getJoinColumnById(joinColumns ,re.getJoinRightCol());
			mainTable += leftColumn.getColNameE() + " = ";
			mainTable += ds.getNotes()+"."+rightColumn.getColNameE();
		}
				
		return mainTable;
	}
	
	/**
	 * 创建查询SELECT 子句
	 * @param datasets ：查询结果涉及的数据集对象
	 * @param dscs： 查询结果列对象
	 * @param jaColumns：结果列属性，用于别名生成
	 * @return
	 */
	private static final String createSelectColumns(List<DataSet> datasets,List<DataSetColumn> dscs,JSONArray jaColumns){
		String select = "";
		for(int i=0;i<dscs.size();i++){
			JSONObject jo = (JSONObject)jaColumns.get(i);
			Long columnId = jo.getLong("columnId");
			DataSetColumn dsc =getJoinColumnById(dscs, columnId);
			DataSet ds = getDatasetById(datasets,dsc.getDbtableId());
			String columnName = ds.getNotes()+"."+dsc.getColNameE()+" AS "+dsc.getColNameE()+"_"+jo.getString("columnTotle");
			if(i==0){
				select += columnName;
			}else{
				select += ","+columnName;
			}
		}
		return select;
	}
	/**
	 * 创建查询SELECT 子句
	 * @param datasets ：查询结果涉及的数据集对象
	 * @param dscs： 查询结果列对象
	 * @param jaColumns：结果列属性，用于别名生成
	 * @param groupParams：分组参数
	 * @param sumParams：统计参数
	 * @return 根据分组参数和统计参数创建SELECT 子句
	 */
	public static final String createSelectColumns(List<DataSet> datasets,List<DataSetColumn> dscs,JSONArray jaColumns,String groupParams,String sumParams){
		if(null == groupParams || "".equals(groupParams)){
			return  createSelectColumns(datasets,dscs,jaColumns);
		}else {
			String countSelects = "";
			for(int i=0;i<dscs.size();i++){
				JSONObject jo = (JSONObject)jaColumns.get(i);
				Long columnId = jo.getLong("columnId");
				DataSetColumn dsc =getJoinColumnById(dscs, columnId);
				DataSet ds = getDatasetById(datasets,dsc.getDbtableId());
				if(groupParams.indexOf(dsc.getId()+"")>=0){
					if(countSelects.equals("")){
						countSelects += ds.getNotes()+"."+dsc.getColNameE()+" AS "+dsc.getColNameE()+"_"+jo.getString("columnTotle");
					}else {
						countSelects += seporator +ds.getNotes()+"."+dsc.getColNameE()+" AS "+dsc.getColNameE()+"_"+jo.getString("columnTotle");
					}
				}
				if(sumParams.indexOf(dsc.getId()+"")>=0){
					if(countSelects.equals("")){
						countSelects += getCountColumn(dsc,ds,jo.getString("columnTotle"));
					}else{
						countSelects += seporator + getCountColumn(dsc,ds,jo.getString("columnTotle"));
					}
				}
			}
			return countSelects;
		}
	}
	
	/**
	 * 创建查询条件语句
	 * @param ja ： 查询条件JSON对象
	 * @param datasets：查询条件涉及的数据集对象
	 * @param conditonsColumn：查询条件涉及的列对象
	 * @param radio：查询条件交、并关系
	 * @return
	 */
	public static final String createWhereColumns(JSONArray ja, List<DataSet> datasets,List<DataSetColumn> conditonsColumn ,String radio){
		
		String condition = "";
		
		String connect = "";
		
		if("true".equals(radio)){
			connect = AND;
		}else {
			connect = OR;
		}
		
		for(Object o : ja){
			JSONObject jo = (JSONObject) o;
			Long columnId = jo.getLong("ss_col_item");
			String op = jo.getString("ss_col_op");
			String value = jo.getString("ss_col_value");
			DataSetColumn dsc = getJoinColumnById(conditonsColumn,columnId);
			DataSet ds = getDatasetById(datasets,dsc.getDbtableId());
			String conditionValue = getConditonString(op,value,dsc);
			if(null == conditionValue){
				continue;
			}
			
			if(!condition.equals("")){
				condition += " "+connect+" ";
			}
			
			condition += " "+ ds.getNotes()+"."+dsc.getColNameE();
			
			condition += conditionValue;
		}
		
		return condition;
		
	}
	/**
	 * 创建排序ORDER BY 子句，
	 * @param datasets ：涉及数据集
	 * @param dscs ：涉及列
	 * @param jaColumns：结果集数据属性，取排序类型
	 * @return
	 */
	public static String createOrderSQL(List<DataSet> datasets,List<DataSetColumn> dscs,JSONArray jaColumns){
		
		String returns = "";
		for(Object o : jaColumns){
			JSONObject jo = (JSONObject) o;
			DataSetColumn dsc = getJoinColumnById(dscs,jo.getLong("columnId"));
			DataSet ds = getDatasetById(datasets,dsc.getDbtableId());
			Integer orderType = jo.getInt("sortType");
			String order = "";
			switch (orderType){
				case 0:
					break;
				case 1:
					order = ds.getNotes()+"."+dsc.getColNameE()+" "+ASC;
					break;
				case 2:
					order = ds.getNotes()+"."+dsc.getColNameE()+" "+DESC;
					break;
				default : break;
			}
			if(order.equals("")){
				continue;
			}
			
			if(returns.equals("")){
				returns = order;
			}else{
				returns += ","+order;
			}
		}
		
		return returns;
	}
	/**
	 * 创建分组GROUP BY 子句
	 * @param datasets ：涉及数据集
	 * @param dscs ： 涉及列
	 * @param groupParams：分组参数
	 * @return
	 */
	public static final String createGroupByColumns(List<DataSet> datasets,List<DataSetColumn> dscs,String groupParams){
		
		if(null == groupParams || groupParams.equals("")){
			return "";
		}
		
		String groupBy = "";
		String[] groupParamArray = groupParams.split(seporator);
		for(int i=0;i<groupParamArray.length;i++){
			DataSetColumn dsc = getJoinColumnById(dscs, Long.valueOf(groupParamArray[i]));
			DataSet ds = getDatasetById(datasets,dsc.getDbtableId());
			String columnName = ds.getNotes()+"."+dsc.getColNameE();
			if(i==0){
				groupBy += columnName;
			}else{
				groupBy += ","+columnName;
			}
		}
		
		return groupBy;
	}
	
	/**
	 * 创建一个条件子句
	 * @param op ：操作符
	 * @param value：条件值 
	 * @param dsc：涉及列
	 * @return
	 */
	private static String getConditonString(String op, String value,DataSetColumn dsc){
		
		String columnType = dsc.getColType();
		
		String result = "";
		
		if(op.equals(OP_INCLUDE)){
			
			switch(dataTypes.indexOf(columnType)){
				case 6 :
				case 5 :
				case 0 :
					result += " LIKE '%"+value+"%'";break;
				case 1 :
				case 2 : return null;
				case 3 : return null;
				case 4 : return null;
				case 7 : return null;
			}
		}else if(op.equals(OP_LARGER)){
			switch(dataTypes.indexOf(columnType)){
				case 6 :
				case 5 :
				case 0 : return null;
				case 1 : 
					result += " > to_date('"+value+"','YYYY-MM-DD')";break;
				case 2 : 
				case 3 :
				case 4 :
				case 7 : 
					result += " > "+value;break;
			}
		}else if(op.equals(OP_E_SAMLLER)){
			switch(dataTypes.indexOf(columnType)){
				case 6 :
				case 5 :
				case 0 : return null;
				case 1 : 
					result += " <= to_date('"+value+"','YYYY-MM-DD')";break;
				case 2 : 
				case 3 :
				case 4 :
				case 7 : 
					result += " <= "+value;break;
			}
		} else if(op.equals(OP_SMALLER)){
			switch(dataTypes.indexOf(columnType)){
			case 6 :
			case 5 :
			case 0 : return null;
			case 1 : 
				result += " < to_date('"+value+"','YYYY-MM-DD')";break;
			case 2 : 
			case 3 :
			case 4 :
			case 7 : 
				result += " < "+value;break;
			}
			
		}else if(op.equals(OP_E_LARGER)){
			
			switch(dataTypes.indexOf(columnType)){
			case 6 :
			case 5 :
			case 0 : return null;
			case 1 : 
				result += " >= to_date('"+value+"','YYYY-MM-DD')";break;
			case 2 : 
			case 3 :
			case 4 :
			case 7 : 
				result += " >= "+value;break;
			}
		}else{
			switch(dataTypes.indexOf(columnType)){
			case 6 :
			case 5 :
			case 0 : 
				result += " = '"+value+"'";break;
			case 1 : 
				result += " = to_date('"+value+"','YYYY-MM-DD')";break;
			case 2 : 
			case 3 :
			case 4 :
			case 7 : 
				result += " = "+value;break;
			}
		}
		return result;
	}
	
	/**
	 * 根据ID，从数组中获取该数据集
	 * @param datasets
	 * @param id
	 * @return
	 */
	private static DataSet getDatasetById(List<DataSet> datasets,Long id){
		for(DataSet ds : datasets){
			if(ds.getId().equals(id)){
				return ds;
			}
		}
		return null;
	}
	
	/**
	 * 根据数据集，获取该数据集与客户基础信息表的关联对象（OCRM_F_CI_CUST_DESC）
	 * @param relations
	 * @param ds
	 * @return
	 */
	private static OcrmFASsRelation getRelationsByTable(List<OcrmFASsRelation> relations, DataSet ds){
		for(OcrmFASsRelation re : relations){
			if(re.getJoinRightTable().equals(ds.getId())){
				return re;
			}
		}
		return null;
	}
	
	/**
	 * 根据ID，从数组中获取数据列对象
	 * @param joinColumns
	 * @param id
	 * @return
	 */
	private static DataSetColumn getJoinColumnById(List<DataSetColumn> joinColumns,Long id){
		for(DataSetColumn column : joinColumns){
			if(column.getId().equals(id)){
				return column;
			}
		}
		return null;
	}
	
	/**
	 * 根据字段类型，创建统计子句，数字型求和，字符型和日期型求个数
	 * @param dsc：列对象
	 * @param ds：数据集
	 * @param totle：别名后缀
	 * @return
	 */
	private static String getCountColumn(DataSetColumn dsc,DataSet ds,String totle){
		if(dsc.getColType().equals("INTEGER") || dsc.getColType().equals("DECIMAL")||dsc.getColType().equals("NUMBER")){
			String item = "SUM("+ds.getNotes()+"."+dsc.getColNameE()+") AS "+dsc.getColNameE()+"_"+totle+"_SUM";
			return item;
		}else {
			String item = "COUNT("+ds.getNotes()+"."+dsc.getColNameE()+") AS "+dsc.getColNameE()+"_"+totle+"_SUM";
			return item;
		}
	}
	
}
