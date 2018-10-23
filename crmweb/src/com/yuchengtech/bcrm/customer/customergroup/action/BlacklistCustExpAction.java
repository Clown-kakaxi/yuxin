package com.yuchengtech.bcrm.customer.customergroup.action;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;

/**
 * @date 2014-09-24
 * @author wzy
 * @desc 客户群管理功能模块中的黑名单客户导出成excel文件处理类
 */
@SuppressWarnings("serial")
@Action("/blacklistCustExpAction")
public class BlacklistCustExpAction extends CommonAction {
	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	// 查询要导出的黑名单客户数据
	public void prepare() {
		String custBaseNumber = request.getParameter("custBaseNumber");// 获取到客户群编号
		StringBuffer sb = new StringBuffer("");
		sb.append("select distinct c.cust_id,\n");
		sb.append("                c.cust_name,\n");
		sb.append("                e.f_value   as ident_type_name,\n");
		sb.append("                c.ident_no,\n");
		sb.append("                d.f_value   as cust_type_name\n");
		sb.append("  from ocrm_f_ci_base a,\n");
		sb.append("       ocrm_f_ci_relate_cust_base b,\n");
		sb.append("       acrm_f_ci_customer c,\n");
		sb.append("       (select *\n");
		sb.append("          from ocrm_sys_lookup_item t\n");
		sb.append("         where t.f_lookup_id = 'XD000080') d,\n");
		sb.append("       (select *\n");
		sb.append("          from ocrm_sys_lookup_item t\n");
		sb.append("         where t.f_lookup_id = 'XD000040') e\n");
		sb.append(" where a.id = b.cust_base_id\n");
		sb.append("   and b.cust_id = c.cust_id\n");
		sb.append("   and c.cust_type = d.f_code(+)\n");
		sb.append("   and c.ident_type = e.f_code(+)\n");
		sb.append("   and a.cust_base_number = '" + custBaseNumber + "'\n");
		setPrimaryKey("c.cust_name asc ");// 按客户姓名升序排序
		SQL = sb.toString();
		datasource = ds;
	}

	// 覆盖父类的导出方法：主要是为了在此处设置Excel文件表头需要的字段
	public String export() {
		Map<String, String> fieldMap = new HashMap<String, String>();// 导出文件列映射
		fieldMap.put("cust_id", "客户编号");
		fieldMap.put("cust_name", "客户名称");
		fieldMap.put("ident_type_name", "证件类型");
		fieldMap.put("ident_no", "证件号码");
		fieldMap.put("cust_type_name", "客户类型");
		try {
			super.setFieldMap(JSONUtil.serialize(fieldMap));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		return super.export();
	}
}
