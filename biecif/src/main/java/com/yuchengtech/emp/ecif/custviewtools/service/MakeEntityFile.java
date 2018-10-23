package com.yuchengtech.emp.ecif.custviewtools.service;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import com.yuchengtech.emp.ecif.core.entity.ColDefVO;
import com.yuchengtech.emp.utils.SpringContextHolder;

public class MakeEntityFile {

	/**
	 * 生成的实体名称后自动加上CUSTOM
	 * @param doc
	 * @param entityname
	 * @return
	 */
	public String make(Document doc,String entityname){
		
		Element e = MakeUtils.getElementByEntity(doc,entityname);

		String bizName = e.getParent().attributeValue("BizName");		//上级节点的bizName
		String tabName = e.attributeValue("tabName");					//本级节点的tabName,如果xml缺失则进行提醒
		
		StringBuffer sb = new StringBuffer("");
		sb.append("");
		
		String packagename = MakeUtils.getEntityPackage(doc, bizName.toLowerCase());
		sb.append("package ").append(packagename).append(";");sb.append("\r\n");
		sb.append("import java.io.Serializable;");sb.append("\r\n");
		sb.append("import javax.persistence.*;");sb.append("\r\n");
		sb.append("import java.math.*;");sb.append("\r\n");
		sb.append("import org.codehaus.jackson.map.annotate.JsonSerialize;");sb.append("\r\n");
		sb.append("import com.yuchengtech.emp.biappframe.base.web.BioneLongSerializer;");sb.append("\r\n");

		sb.append("@Entity");sb.append("(name=\""+ entityname +"Custom" +"\")");sb.append("\r\n");
		sb.append("@Table(name=\""+tabName +"\")");sb.append("\r\n");
		sb.append("public class "+entityname+" implements Serializable {");sb.append("\r\n");
		sb.append("	private static final long serialVersionUID = 1L;");sb.append("\r\n");
			
		DBHelperBS bs = SpringContextHolder.getBean(DBHelperBS.class);
		String schema = MakeUtils.getDefautSchema(doc);
		List<ColDefVO> list = bs.getColList(schema,tabName);
		List keyList = bs.getKeyList(schema, tabName);
		
		//生成列
		for(ColDefVO vo :list){
			String colName = vo.getColName().toUpperCase();
			String fieldName = convert2JavaFieldName(colName);
			if(keyList.contains(colName)){
				//主键（暂不支持复合主键）
				sb.append("	@Id");sb.append("\r\n");
				sb.append("	@Column(name=\"" + colName + "\", unique=true, nullable=false)");sb.append("\r\n");
			}else{
				//非主键	
				sb.append("	@Column(name=\"" + colName + "\")");sb.append("\r\n");
				//sb.append("	private String enName;");
			}
			String javaDataType = convert2JavaType(vo);
			sb.append("	private "+javaDataType + " "+ fieldName +";");sb.append("\r\n");
		}
		
		//生成get、set
		for(ColDefVO vo :list){
			String colName = vo.getColName().toUpperCase();
			String fieldName = convert2JavaFieldName(colName);

			String javaDataType = convert2JavaType(vo);
			if(javaDataType.equals("Long")){
				sb.append(" @JsonSerialize(using=BioneLongSerializer.class) ");sb.append("\r\n");
			}
			sb.append(" public "+ javaDataType +" get" + convert2JavaMethodName(colName) +"() {");sb.append("\r\n");
			sb.append(" return this." + fieldName +";");sb.append("\r\n");
			sb.append(" }");sb.append("\r\n");

			sb.append(" public void set" + convert2JavaMethodName(colName) +"("+javaDataType +" " + fieldName +") {");sb.append("\r\n");
			sb.append("  this." + fieldName +"=" + fieldName+";");sb.append("\r\n");
			sb.append(" }");sb.append("\r\n");
		
		}
		
		sb.append(" }");sb.append("\r\n");
		return sb.toString();

	}
	
	public String convert2JavaType(ColDefVO vo){
		if(vo.getDataType().equals("CHAR")||vo.getDataType().equals("VARCHAR2")||vo.getDataType().equals("DATE")||vo.getDataType().equals("TIMESTAMP(6)")||vo.getDataType().equals("CLOB")){
			return "String";
		}else if(vo.getDataType().equals("NUMBER")){
			if(vo.getDataPrec()!=null&&vo.getDataPrec()!=0){
				return "BigDecimal";
			}else {
				return "Long";
			}
		}
		
		return null;
	}
	
	public static String convert2JavaFieldName(String colName){
		
		String fieldName = "";
		String strs[] = colName.split("_");
		fieldName = strs[0].toLowerCase();
		for(int i=1;i<strs.length;i++){
			String str = strs[i];
			fieldName += str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
		}
		
		return fieldName;
	}
	
	public String convert2JavaMethodName(String colName){
		
		String fieldName  = convert2JavaFieldName(colName);
		String methodName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
		
		return methodName;
	}
	
}