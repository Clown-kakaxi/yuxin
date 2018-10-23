package com.yuchengtech.bcrm.action;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.constance.fusioncharts.LineChart;

@ParentPackage("json-default")
@Action(value="/orgPerformanceCunKuan", results={
    @Result(name="success", type="json")
})
public class OrgPerformanceCunKuanChartAction extends CommonAction{
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

	
	public void prepare(){
		SQL = "select * from acrm_m_vip_busi_scale_detail";
		LineChart fcbo = new LineChart();
		fcbo.addDataColumn("BUSI_SCALE", "存款时点余额");
		fcbo.addDataColumn("COMP_PRE_MONTH", "存款日均余额");
		fcbo.setCategories("一月,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月,十二月", ",");
		//fcbo.addCategories("四月,五月,六月", ",");
		//yAxisMinValue=\"5000\" yaxisname=\"金额(单位: （亿元）)\" showValues=\"0\"  caption=\"贷款余额趋势图\" palette=\"1\" baseFontSize=\"13\" formatNumberScale=\"0\">
		fcbo.addAttribute("yAxisMinValue", "50000");
		fcbo.addAttribute("showValues", "0");
		fcbo.addAttribute("yaxisname", "金额(单位:万元)");
		fcbo.addAttribute("caption", "存款余额趋势图");
		fcbo.addAttribute("palette", "1");
		fcbo.addAttribute("formatNumberScale", "0");
		fcbo.addAttribute("baseFontSize", "13");
		fcbo.addAttribute("basefont", "宋体");
//		PieChart fcbo = new PieChart();
//		fcbo.setLabelColumn("BUSI_SCALE");
//		fcbo.setValueColumn("COMP_PRE_MONTH");
		
		chart = fcbo;
		datasource = ds;
	}

}

