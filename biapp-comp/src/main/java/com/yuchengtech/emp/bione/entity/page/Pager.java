package com.yuchengtech.emp.bione.entity.page;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yuchengtech.emp.bione.util.ArrayUtils;

public class Pager implements Serializable {

	private static final long serialVersionUID = 5472321653620726832L;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected int totalCount = 0;// 总记录数
	protected int page = 1;// 当前页数
	protected int pagesize = 2;// 每页显示行数
	protected String sortname;// 排序的属性名
	protected String sortorder;// 排序的方式 desc or asc
	protected String condition;// 保存前台传递来的过滤参数

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getSortname() {
		return sortname;
	}

	public void setSortname(String sortname) {
		this.sortname = sortname;
	}

	public String getSortorder() {
		return sortorder;
	}

	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}

	/**
	 * 当前页数据的开始索引
	 * 
	 * @return
	 */
	public int getPageFirstIndex() {

		return (page - 1) * pagesize;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	/**
	 * 生成搜索查询语句 生成按名称绑定的语句
	 * 
	 * @return MAP get(jql)获得查询语句 get(params)获得参数Map
	 */
	public Map<String, Object> getSearchCondition() {
		Map<String, Object> conditionMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> fieldValues = new HashMap<String, Object>();
		Collection<String> oplist = ArrayUtils
				.asCollection("=,>,<,>=,<=,<>,!=,like,in");
		Collection<String> typelist = ArrayUtils
				.asCollection("string,date,number,checkboxGroup");
		StringBuffer conditionnew = new StringBuffer();
		String op = "";
		String field = "";
		String value = "";
		String type = "";
		int i = 0;
		if (condition != null && !condition.equals("")) {
			JSONObject groupJson = JSONObject.fromObject(condition);
			JSONArray rulesJson = groupJson.getJSONArray("rules");
			for (Iterator<?> conditioniter = rulesJson.iterator(); conditioniter
					.hasNext();) {
				JSONObject rule = (JSONObject) conditioniter.next();
				op = (String) rule.get("op");
				field = (String) rule.get("field");
				value = (String) rule.get("value");
				type = (String) rule.get("type");
				if (field.equals("null") || field.equals("")
						|| value.equals("null") || value.equals("")) {
					logger.debug("搜索条件生成失败!");
				} else if (!oplist.contains(op)) {
					logger.debug("搜索条件符号配置错误!");
				} else if (!typelist.contains(type)) {
					logger.debug("搜索条件类型配置错误!");
				} else {
					if (type.equals("date")) {
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
						try {
							params.put("param" + i, df.parse(value));
							fieldValues.put(field, df.parse(value));
						} catch (ParseException e) {
							logger.debug("搜索条件[" + value.toString()
									+ "]的日期格式错误!");
						}
					}else if (type.equals("number")) {
						if (StringUtils.isNumeric(value)) {
							BigDecimal bd = new BigDecimal(value);
							params.put("param" + i, bd);
							fieldValues.put(field, bd);
						} else {
							logger.debug("搜索条件[" + value.toString()
									+ "]的数字格式错误!");
						}
					} else {
						if (op.equals("like")) {
							params.put("param" + i, "%" + value + "%");
						}else {
							params.put("param" + i, value);
						}
						fieldValues.put(field, value);
					}
					if (params.get("param" + i) != null) {
						if (conditionnew.length() != 0) {
							conditionnew.append(" and ");
						}
						conditionnew.append(field);
						conditionnew.append(" " + op);
						conditionnew.append(":param" + i);
					}
					i++;
				}
			}
		}
		conditionMap.put("jql", conditionnew.toString());
		conditionMap.put("params", params);
		conditionMap.put("fieldValues", fieldValues);
		return conditionMap;
	}

}
