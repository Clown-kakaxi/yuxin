package com.yuchengtech.bcrm.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@ParentPackage("json-default")
@Action(value="/orgPerformanceDaiKuan", results={
    @Result(name="success", type="json")
})
public class OrgPerformanceDaiKuanChartAction {
	
	private String dataXml = ""; 

	public String index() {
		StringBuilder depBalXml = new StringBuilder(
				"<dataset SeriesName=\"贷款时点余额\">");
		StringBuilder crmDtXml = new StringBuilder("<categories>");
		// 横轴日期

		crmDtXml.append("<category label=\"" + "一月" + "\" />");
		crmDtXml.append("<category label=\"" + "二月" + "\" />");
		crmDtXml.append("<category label=\"" + "三月" + "\" />");
		crmDtXml.append("<category label=\"" + "四月" + "\" />");
		crmDtXml.append("<category label=\"" + "五月" + "\" />");
		crmDtXml.append("<category label=\"" + "六月" + "\" />");
		crmDtXml.append("<category label=\"" + "七月" + "\" />");
		crmDtXml.append("<category label=\"" + "八月" + "\" />");
		crmDtXml.append("<category label=\"" + "九月" + "\" />");
		crmDtXml.append("<category label=\"" + "十月" + "\" />");
		crmDtXml.append("<category label=\"" + "十一月" + "\" />");
		crmDtXml.append("<category label=\"" + "十二月" + "\" />");
		depBalXml.append("<set value=\"" + "6115" + "\" />");
		depBalXml.append("<set value=\"" + "6302" + "\" />");
		depBalXml.append("<set value=\"" + "6424" + "\" />");
		depBalXml.append("<set value=\"" + "6221" + "\" />");
		depBalXml.append("<set value=\"" + "6350" + "\" />");
		depBalXml.append("<set value=\"" + "6533" + "\" />");
		depBalXml.append("<set value=\"" + "6734" + "\" />");
		depBalXml.append("<set value=\"" + "6409" + "\" />");
		depBalXml.append("<set value=\"" + "6360" + "\" />");
		depBalXml.append("<set value=\"" + "6486" + "\" />");
		depBalXml.append("<set value=\"" + "6580" + "\" />");
		depBalXml.append("<set value=\"" + "6706" + "\" />");

		depBalXml.append("</dataset>");

		StringBuilder depBalXml2 = new StringBuilder(
				"<dataset SeriesName=\"贷款日均余额\">");
		;
		depBalXml2.append("<set value=\"" + "5015" + "\" />");
		depBalXml2.append("<set value=\"" + "5402" + "\" />");
		depBalXml2.append("<set value=\"" + "5524" + "\" />");
		depBalXml2.append("<set value=\"" + "5321" + "\" />");
		depBalXml2.append("<set value=\"" + "5285" + "\" />");
		depBalXml2.append("<set value=\"" + "5370" + "\" />");
		depBalXml2.append("<set value=\"" + "5490" + "\" />");
		depBalXml2.append("<set value=\"" + "5634" + "\" />");
		depBalXml2.append("<set value=\"" + "5423" + "\" />");
		depBalXml2.append("<set value=\"" + "5532" + "\" />");
		depBalXml2.append("<set value=\"" + "5646" + "\" />");
		depBalXml2.append("<set value=\"" + "5706" + "\" />");
		depBalXml2.append("</dataset>");

		crmDtXml.append("</categories>");

		// 最后生成的xml
		StringBuilder xml = new StringBuilder(
				"<chart  yAxisMinValue=\"5000\" yaxisname=\"金额(单位: （亿元）)\" showValues=\"0\"  caption=\"贷款余额趋势图\" palette=\"1\" baseFontSize=\"13\" formatNumberScale=\"0\"> ");
		xml.append(crmDtXml.toString()).append(depBalXml.toString()).append(
				depBalXml2.toString()).append("</chart>");

		dataXml = xml.toString();
		// system.out.printlnln(dataXml);
		return "success";

	}
    	
	public String getDataXml() {
		return dataXml;
	}
}

