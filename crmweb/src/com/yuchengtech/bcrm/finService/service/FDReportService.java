package com.yuchengtech.bcrm.finService.service;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.RectangleInsets;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.finService.model.OcrmFFinAnaAdviseVista;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.upload.FileTypeConstance;
/**
 * 诊断报告生成Service
 * @author YOYOGI
 * 2014-8-11
 */
@Service
public class FDReportService extends CommonService {
	public FDReportService(){
		JPABaseDAO<OcrmFFinAnaAdviseVista, Long> baseDAO = new JPABaseDAO<OcrmFFinAnaAdviseVista, Long>(OcrmFFinAnaAdviseVista.class);
		super.setBaseDAO(baseDAO);
	}
	//对于模板的demand, 予以保留..以观察

	public void createReport(String reportId, String custId, String instCode) throws Exception{
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
//    	String custId = "";
    	String path = FileTypeConstance.getImportTempaltePath();//【？--怎么定义？？】
		String name = "REPORT_"+reportId+".pdf";//文件名
		//创建一个 iTextSharp.text.Document对象的实例：
		Document document = new Document(PageSize.A4, 36, 72, 80, 80);//【？--分别的意义】
		
		try {
			//判断文件是否存在，存在即先删除
			File f1 = new File(path,name);
			if (f1.isFile() || f1.length()==0){ 
				f1.delete();  
			}
			//创建Document对象的一个Writer实例：
			PdfWriter writer = PdfWriter.getInstance(document,  new FileOutputStream(path + File.separator + name));
			//打开Document对象
			document.open();
			//为当前Document添加内容：
			BaseFont bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);       
			Font fontChinese = new Font(bfChinese);
			Paragraph title = new Paragraph("财务诊断报告书\n\n",fontChinese);
			title.setAlignment(Element.ALIGN_CENTER);//居中对齐
			document.add(title);
			
			//客户他行资产负债信息-资产
			String sql = " select o1.F_CODE, to_char(o2.AMOUNT_VALUE,'9,999,999,999,999.99'),o2.ID  " +
					"from OCRM_SYS_LOOKUP_ITEM o1 " +
					"left outer join ( " +
					"select a.ID,a.ASSETS_TYPE, a.AMOUNT_VALUE, a.CUST_ID  " +
					"from OCRM_F_FIN_BAL_SHEET a  " +
					"where a.BELONG_TYPE = '1' and a.ASSET_DEBT_TYPE = '1' and a.CUST_ID = '"+ custId +"') o2 " +
					"on o1.F_CODE = o2.ASSETS_TYPE " +
					"where o1.F_LOOKUP_ID = 'OB_ASSETS_TYPE' " +
					"order by o1.f_code";
			List<Object[]> otherList = this.em.createNativeQuery(sql).getResultList();
			if(otherList != null && otherList.size()>0){
				Object[] baseInfo = otherList.get(0);//导入baseInfo
//				custId = String.valueOf(baseInfo[0]);//转为字符串//已经有custId了
				PdfPCell tempCell = null;//TEMPCELL
				PdfPTable baseInfoTable = new PdfPTable(4);//BASEINFOTABLE【？】 
				baseInfoTable.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
				PdfPCell titleCell = new PdfPCell(new Paragraph("客户他行资产信息", fontChinese));//TABLECELL
				titleCell.setColspan(4);//居中, 四并一
				baseInfoTable.addCell(titleCell);//标题加入table
				
				//资产部分
				tempCell = new PdfPCell(new Paragraph("活期存款",fontChinese));
				baseInfoTable.addCell(tempCell);
				
				//声明bal，0值
				Integer bal = 0;
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(1);
				
				tempCell = new PdfPCell(new Paragraph("定期存款",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(2);
				
				tempCell = new PdfPCell(new Paragraph("基金",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(3);
				
				tempCell = new PdfPCell(new Paragraph("国债",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(4);
				
				tempCell = new PdfPCell(new Paragraph("其他债券",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(5);
				
				tempCell = new PdfPCell(new Paragraph("人民币理财",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(6);
				
				tempCell = new PdfPCell(new Paragraph("外币理财",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(7);
				
				tempCell = new PdfPCell(new Paragraph("贵金属",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(8);
				
				tempCell = new PdfPCell(new Paragraph("其他投资",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				document.add(baseInfoTable);
			}
			
			//客户他行资产负债信息-负债
			String sql2 = " select o1.F_CODE, to_char(o2.AMOUNT_VALUE,'9,999,999,999,999.99'),o2.ID  " +
					"from OCRM_SYS_LOOKUP_ITEM o1 " +
					"left outer join ( " +
					"select a.ID,a.ASSETS_TYPE, a.AMOUNT_VALUE, a.CUST_ID  " +
					"from OCRM_F_FIN_BAL_SHEET a  " +
					"where a.BELONG_TYPE = '1' and a.ASSET_DEBT_TYPE = '2' and a.CUST_ID = '"+ custId +"') o2 " +
					"on o1.F_CODE = o2.ASSETS_TYPE " +
					"where o1.F_LOOKUP_ID = 'OB_LAIB_TYPE' " +
					"order by o1.f_code";
			List<Object[]> otherList2 = this.em.createNativeQuery(sql).getResultList();
			if(otherList2 != null && otherList2.size()>0){
				Object[] baseInfo = otherList2.get(0);//导入baseInfo
//				custId = String.valueOf(baseInfo[0]);//转为字符串//已经有custId了
				PdfPCell tempCell = null;//TEMPCELL
				PdfPTable baseInfoTable = new PdfPTable(4);//BASEINFOTABLE【？】
				baseInfoTable.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
				PdfPCell titleCell = new PdfPCell(new Paragraph("客户他行负债信息", fontChinese));//TABLECELL
				titleCell.setColspan(4);//居中, 四并一
				baseInfoTable.addCell(titleCell);//标题加入table
				
				//声明bal，0值
				Integer bal = 0;
				
				tempCell = new PdfPCell(new Paragraph("信用卡透支余额",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(1);
				
				tempCell = new PdfPCell(new Paragraph("个人住房贷款",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(2);
				
				tempCell = new PdfPCell(new Paragraph("个人汽车贷款",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(3);
				
				tempCell = new PdfPCell(new Paragraph("其他贷款",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				document.add(baseInfoTable);
			}
			
			//客户其他资产负债信息-资产
			String sql3 = " select o1.F_CODE, to_char(o2.AMOUNT_VALUE,'9,999,999,999,999.99'),o2.ID  " +
					"from OCRM_SYS_LOOKUP_ITEM o1 " +
					"left outer join ( " +
					"select a.ID,a.ASSETS_TYPE, a.AMOUNT_VALUE, a.CUST_ID  " +
					"from OCRM_F_FIN_BAL_SHEET a  " +
					"where a.BELONG_TYPE = '2' and a.ASSET_DEBT_TYPE = '1' and a.CUST_ID = '"+ custId +"') o2 " +
					"on o1.F_CODE = o2.ASSETS_TYPE " +
					"where o1.F_LOOKUP_ID = 'O_ASSETS_TYPE' " +
					"order by o1.f_code";
			List<Object[]> anotherList = this.em.createNativeQuery(sql).getResultList();
			if(anotherList != null && anotherList.size()>0){
				Object[] baseInfo = anotherList.get(0);//导入baseInfo
//				custId = String.valueOf(baseInfo[0]);//转为字符串//已经有custId了
				PdfPCell tempCell = null;//TEMPCELL
				PdfPTable baseInfoTable = new PdfPTable(4);//BASEINFOTABLE【？】
				baseInfoTable.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
				PdfPCell titleCell = new PdfPCell(new Paragraph("客户其他资产信息", fontChinese));//TABLECELL
				titleCell.setColspan(4);//居中, 四并一
				baseInfoTable.addCell(titleCell);//标题加入table
				
				//声明bal，0值
				Integer bal = 0;
				
				//资产部分
				tempCell = new PdfPCell(new Paragraph("现金",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(1);
				
				tempCell = new PdfPCell(new Paragraph("股票",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(2);
				
				tempCell = new PdfPCell(new Paragraph("信托",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(3);
				
				tempCell = new PdfPCell(new Paragraph("自用房产",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(4);
				
				tempCell = new PdfPCell(new Paragraph("投资房产",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(5);
				
				tempCell = new PdfPCell(new Paragraph("车辆",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(6);
				
				tempCell = new PdfPCell(new Paragraph("其他家居资产",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(7);
				
				tempCell = new PdfPCell(new Paragraph("公积金与社保",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(8);
				
				tempCell = new PdfPCell(new Paragraph("债权",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				document.add(baseInfoTable);
			}
			
			//客户其他资产负债信息-负债
			String sql4 = " select o1.F_CODE,to_char(o2.AMOUNT_VALUE,'9,999,999,999,999.99'), o2.ID  " +
					"from OCRM_SYS_LOOKUP_ITEM o1 " +
					"left outer join ( " +
					"select a.ID,a.ASSETS_TYPE, a.AMOUNT_VALUE, a.CUST_ID  " +
					"from OCRM_F_FIN_BAL_SHEET a  " +
					"where a.BELONG_TYPE = '2' and a.ASSET_DEBT_TYPE = '2' and a.CUST_ID = '"+ custId +"') o2 " +
					"on o1.F_CODE = o2.ASSETS_TYPE " +
					"where o1.F_LOOKUP_ID = 'O_LAIB_TYPE' " +
					"order by o1.f_code";
			List<Object[]> anotherList2 = this.em.createNativeQuery(sql).getResultList();
			if(anotherList2 != null && anotherList2.size()>0){
				Object[] baseInfo = anotherList2.get(0);//导入baseInfo
//				custId = String.valueOf(baseInfo[0]);//转为字符串//已经有custId了
				PdfPCell tempCell = null;//TEMPCELL
				PdfPTable baseInfoTable = new PdfPTable(4);//BASEINFOTABLE【？】
				baseInfoTable.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
				PdfPCell titleCell = new PdfPCell(new Paragraph("客户其他负债信息", fontChinese));//TABLECELL
				titleCell.setColspan(4);//居中, 四并一
				baseInfoTable.addCell(titleCell);//标题加入table
				
				//声明bal，0值
				Integer bal = 0;
				
				tempCell = new PdfPCell(new Paragraph("应付租金",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(1);
				
				tempCell = new PdfPCell(new Paragraph("应付费用",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(2);
				
				tempCell = new PdfPCell(new Paragraph("其他流动性负债",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(3);
				
				tempCell = new PdfPCell(new Paragraph("其他长期负债",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(4);
				
				tempCell = new PdfPCell(new Paragraph("债务",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				document.add(baseInfoTable);
			}
			
			//客户家庭收入支出信息-收入
			String sql5 = "select o1.F_CODE,to_char(o2.MONEY,'9,999,999,999,999.99'), o2.ID  " +
					"from OCRM_SYS_LOOKUP_ITEM o1  " +
					"left outer join ( " +
					"select a.DETIAL_TYPE, a.MONEY, a.ID  " +
					"from OCRM_F_FIN_CUST_IO a  " +
					"where a.IO_TYPE = '1' and a.CUST_ID = '100') o2  " +
					"on o1.F_CODE =o2.DETIAL_TYPE  " +
					"where o1.F_LOOKUP_ID = 'IN_TYPE' order by o1.f_code";
			List<Object[]> monthInList = this.em.createNativeQuery(sql).getResultList();
			if(monthInList != null && monthInList.size()>0){
				Object[] baseInfo = monthInList.get(0);//导入baseInfo
//				custId = String.valueOf(baseInfo[0]);//转为字符串//已经有custId了
				PdfPCell tempCell = null;//TEMPCELL
				PdfPTable baseInfoTable = new PdfPTable(4);//BASEINFOTABLE【？】
				baseInfoTable.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
				PdfPCell titleCell = new PdfPCell(new Paragraph("客户家庭月度收入信息", fontChinese));//TABLECELL
				titleCell.setColspan(4);//居中, 四并一
				baseInfoTable.addCell(titleCell);//标题加入table
				
				//声明bal，0值
				Integer bal = 0;
				
				//资产部分
				tempCell = new PdfPCell(new Paragraph("现金",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(1);
				
				tempCell = new PdfPCell(new Paragraph("股票",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(2);
				
				tempCell = new PdfPCell(new Paragraph("信托",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(3);
				
				tempCell = new PdfPCell(new Paragraph("自用房产",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(4);
				
				tempCell = new PdfPCell(new Paragraph("投资房产",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(5);
				
				tempCell = new PdfPCell(new Paragraph("车辆",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(6);
				
				tempCell = new PdfPCell(new Paragraph("其他家居资产",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(7);
				
				tempCell = new PdfPCell(new Paragraph("公积金与社保",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(8);
				
				tempCell = new PdfPCell(new Paragraph("债权",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				document.add(baseInfoTable);
			}
			
			//客户家庭收入支出-支出
			String sql6 = "select o1.F_CODE, to_char(o2.MONEY,'9,999,999,999,999.99'), o2.ID  " +
					"from OCRM_SYS_LOOKUP_ITEM o1  " +
					"left outer join ( " +
					"select a.DETIAL_TYPE, a.MONEY, a.ID  " +
					"from OCRM_F_FIN_CUST_IO a  " +
					"where a.IO_TYPE = '2' and a.CUST_ID = '100') o2  " +
					"on o1.F_CODE =o2.DETIAL_TYPE  " +
					"where o1.F_LOOKUP_ID = 'OUT_TYPE' order by o1.f_code";
			List<Object[]> monthOutList = this.em.createNativeQuery(sql).getResultList();
			if(monthOutList != null && monthOutList.size()>0){
				Object[] baseInfo = monthOutList.get(0);//导入baseInfo
//				custId = String.valueOf(baseInfo[0]);//转为字符串//已经有custId了
				PdfPCell tempCell = null;//TEMPCELL
				PdfPTable baseInfoTable = new PdfPTable(4);//BASEINFOTABLE【？】
				baseInfoTable.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
				PdfPCell titleCell = new PdfPCell(new Paragraph("客户家庭月度支出信息", fontChinese));//TABLECELL
				titleCell.setColspan(4);//居中, 四并一
				baseInfoTable.addCell(titleCell);//标题加入table
				
				//声明bal，0值
				Integer bal = 0;
				
				tempCell = new PdfPCell(new Paragraph("应付租金",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(1);
				
				tempCell = new PdfPCell(new Paragraph("应付费用",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(2);
				
				tempCell = new PdfPCell(new Paragraph("其他流动性负债",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(3);
				
				tempCell = new PdfPCell(new Paragraph("其他长期负债",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				baseInfo = otherList.get(4);
				
				tempCell = new PdfPCell(new Paragraph("债务",fontChinese));
				baseInfoTable.addCell(tempCell);
				//判断是否null的逻辑
				if(baseInfo[1] == null){
					tempCell = new PdfPCell(new Paragraph(String.valueOf(bal),fontChinese));
				}else{
					tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				}
//				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				baseInfoTable.addCell(tempCell);
				
				document.add(baseInfoTable);
			}
			
			//财务分析部分
			document.add(new Paragraph("\n"));
			PdfContentByte contentByte = writer.getDirectContent();
			int width = (int)PageSize.A4.getWidth()/2;
			PdfPTable chartTable = new PdfPTable(2);
		    chartTable.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
		    PdfPCell tempCell = new PdfPCell(new Paragraph("财务分析", fontChinese));
			tempCell.setColspan(2);
			chartTable.addCell(tempCell);
			JFreeChart otherBankChart = this.otherBankChart(custId);
			JFreeChart currBankChart = this.currBankChart(custId);
			JFreeChart otherChart = this.otherChart(custId);
			JFreeChart inoutChart = this.inoutChart(custId);
			
			Image currBankChartPng = Image.getInstance(contentByte, currBankChart.createBufferedImage(width, 300), 1);
		    chartTable.addCell(currBankChartPng);
		    Image otherBankChartPng = Image.getInstance(contentByte, otherBankChart.createBufferedImage(width, 300), 1);
		    chartTable.addCell(otherBankChartPng);
		    Image otherChartPng = Image.getInstance(contentByte, otherChart.createBufferedImage(width, 300), 1);
		    chartTable.addCell(otherChartPng);
		    Image inoutChartPng = Image.getInstance(contentByte, inoutChart.createBufferedImage(width, 300), 1);
		    chartTable.addCell(inoutChartPng);
		    
		    document.add(chartTable);
		    
		    List currbankList = this.em.createNativeQuery("SELECT 1,NVL(SUM(MS_AC_BAL),0) BAL FROM ACRM_F_DP_SAVE_INFO A WHERE A.cust_id = '"+custId+"' "
					+" union SELECT 2,NVL(SUM(BAL),0) BAL FROM ACRM_F_CI_ASSET_BUSI_PROTO A WHERE A.cust_id = '"+custId+"'").getResultList();
				    
		    List otherbankList = this.em.createNativeQuery("select 1 as TYPE,NVL(sum(a.amount_value),0) AS VAL from OCRM_F_FIN_BAL_SHEET a where a.BELONG_TYPE = '1' and a.asset_debt_type = '1' and a.cust_id = '"+custId+"' "
			+" union select 2 as TYPE,NVL(sum(a.amount_value),0) AS VAL from OCRM_F_FIN_BAL_SHEET a where a.BELONG_TYPE = '1' and a.asset_debt_type = '2' and a.cust_id = '"+custId+"' "
			+" union select 3 as TYPE,NVL(sum(case when a.asset_debt_type = '1' then NVL(a.amount_value,0) when a.asset_debt_type = '2' then -NVL(a.amount_value,0) end),0) AS VAL from OCRM_F_FIN_BAL_SHEET a where a.BELONG_TYPE = '1' and a.cust_id = '"+custId+"'").getResultList();
		    
		    List jitaList = this.em.createNativeQuery("select 1 as TYPE,NVL(sum(a.amount_value),0) AS VAL from OCRM_F_FIN_BAL_SHEET a where a.BELONG_TYPE = '2' and a.asset_debt_type = '1' and a.cust_id = '"+custId+"' "
					+" union select 2 as TYPE,NVL(sum(a.amount_value),0) AS VAL from OCRM_F_FIN_BAL_SHEET a where a.BELONG_TYPE = '2' and a.asset_debt_type = '2' and a.cust_id = '"+custId+"' "
					+" union select 3 as TYPE,NVL(sum(case when a.asset_debt_type = '1' then NVL(a.amount_value,0) when a.asset_debt_type = '2' then -NVL(a.amount_value,0) end),0) AS VAL from OCRM_F_FIN_BAL_SHEET a where a.BELONG_TYPE = '2' and a.cust_id = '"+custId+"'").getResultList();
				    
		    List inoutList = this.em.createNativeQuery("select 1 as TYPE,NVL(sum(a.MONEY),0) AS VAL from OCRM_F_FIN_CUST_IO a where a.IO_TYPE = '1' and a.cust_id = '"+custId+"' "
					+" union select 2 as TYPE,NVL(sum(a.MONEY),0) AS VAL from OCRM_F_FIN_CUST_IO a where a.IO_TYPE = '2' and a.cust_id = '"+custId+"' "
					+" union select 3 as TYPE,NVL(sum(case when a.IO_TYPE = '1' then NVL(a.MONEY,0) when a.IO_TYPE = '2' then -NVL(a.MONEY,0) end),0) AS VAL from OCRM_F_FIN_CUST_IO a where a.cust_id = '"+custId+"'").getResultList();
			
		    PdfPTable countTable = new PdfPTable(8);
		    countTable.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
		    //调整数字格式
			NumberFormat nf2 = NumberFormat.getInstance(Locale.CHINA);
		    tempCell = new PdfPCell(new Paragraph("客户本行资产负责", fontChinese));
			tempCell.setColspan(2);
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph("客户他行资产负责", fontChinese));
			tempCell.setColspan(2);
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph("客户其它资产负责", fontChinese));
			tempCell.setColspan(2);
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph("月度收支信息", fontChinese));
			tempCell.setColspan(2);
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("本行资产合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(((Object[])currbankList.get(0))[1])), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("他行资产合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(((Object[])otherbankList.get(0))[1])), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("其它资产合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(((Object[])jitaList.get(0))[1])), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("月度总收入：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(((Object[])inoutList.get(0))[1])), fontChinese));
			countTable.addCell(tempCell);
			
			
			tempCell = new PdfPCell(new Paragraph("本行负债合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(((Object[])currbankList.get(1))[1])), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("他行负债合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(((Object[])otherbankList.get(1))[1])), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("其它负债合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(((Object[])jitaList.get(1))[1])), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("月度总支出：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(((Object[])inoutList.get(1))[1])), fontChinese));
			countTable.addCell(tempCell);
			
			
			tempCell = new PdfPCell(new Paragraph("本行净资产合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(Double.valueOf(String.valueOf(((Object[])currbankList.get(0))[1])) - Double.valueOf(String.valueOf(((Object[])currbankList.get(1))[1])))), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("他行净资产合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(((Object[])otherbankList.get(2))[1])), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("其它净资产合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(((Object[])jitaList.get(2))[1])), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("盈余：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(((Object[])inoutList.get(2))[1])), fontChinese));
			countTable.addCell(tempCell);
			
			
			tempCell = new PdfPCell(new Paragraph("客户总资产：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(Double.valueOf(String.valueOf(((Object[])currbankList.get(0))[1]))+Double.valueOf(String.valueOf(((Object[])otherbankList.get(0))[1]))+Double.valueOf(String.valueOf(((Object[])jitaList.get(0))[1])))), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("客户总负债：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(Double.valueOf(String.valueOf(((Object[])currbankList.get(1))[1]))+Double.valueOf(String.valueOf(((Object[])otherbankList.get(1))[1]))+Double.valueOf(String.valueOf(((Object[])jitaList.get(1))[1])))), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("客户净资产：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(nf2.format(Double.valueOf(String.valueOf(((Object[])currbankList.get(0))[1])) - Double.valueOf(String.valueOf(((Object[])currbankList.get(1))[1]))+Double.valueOf(String.valueOf(((Object[])otherbankList.get(2))[1]))+Double.valueOf(String.valueOf(((Object[])jitaList.get(2))[1])))), fontChinese));
			tempCell.setColspan(3);
			countTable.addCell(tempCell);

			document.add(countTable);
			
			//财务指标分析部分
			document.add(new Paragraph("\n"));
			PdfPTable anaTable = new PdfPTable(8);
		    countTable.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
		    PdfPCell anaCell = new PdfPCell(new Paragraph("财务指标分析", fontChinese));
			tempCell.setColspan(8);
			
			Map<String, Double> assetDebtMap = this.findCustAllAssetAndDebt(custId,instCode);
			Double v;
			//调整数字格式
			NumberFormat nf = NumberFormat.getInstance(Locale.CHINA);
			// 净资产
			v = assetDebtMap.get("newAsset");
			
			anaCell = new PdfPCell(new Paragraph("净资产(元)", fontChinese));
			anaCell.setColspan(2);
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph(String.valueOf(nf.format(v)), fontChinese));
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph("如果为负，则说明目前的财务状况不容乐观，有必要将近期的债务尽快偿还，同时尽快增加收入。", fontChinese));
			anaCell.setColspan(5);
			anaTable.addCell(anaCell);

			// 总资产自有权益比例=净资产/总资产
			if(assetDebtMap.get("assetSum")==0.00){
				v = 0.00;
			}else{
				v = (assetDebtMap.get("newAsset") / assetDebtMap.get("assetSum"))*100;
			}
			
			anaCell = new PdfPCell(new Paragraph("总资产自有权益比例", fontChinese));
			anaCell.setColspan(2);
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph(String.valueOf(nf.format(v)), fontChinese));
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph("建议控制在50%左右。如果净资产偿付比例太低，意味着现在的生活靠借债来维持，一旦债务到期或经济不景气时，资产出现损失，可能资不抵债。如果比例很高，接近1，意味信用额度未得到充分利用，应通过借款来优化其财务结构。", fontChinese));
			anaCell.setColspan(5);
			anaTable.addCell(anaCell);

			// 总资产负债比例=负债/总资产
			if(assetDebtMap.get("assetSum")==0.00){
				v = 0.00;
			}else{
				v = (assetDebtMap.get("debtSum") / assetDebtMap.get("assetSum"))*100;
			}
			
			
			anaCell = new PdfPCell(new Paragraph("总资产负债比例", fontChinese));
			anaCell.setColspan(2);
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph(String.valueOf(nf.format(v)), fontChinese));
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph("建议控制在50%以下，以减少由于资产流动性不足而出现财务危机的可能。", fontChinese));
			anaCell.setColspan(5);
			anaTable.addCell(anaCell);

			// 家庭偿债能力=总资产/总负债
			if(assetDebtMap.get("debtSum")==0.00){
				v = 0.00;
			}else{
				v = assetDebtMap.get("assetSum") / assetDebtMap.get("debtSum");
			}
			
			anaCell = new PdfPCell(new Paragraph("家庭偿债能力", fontChinese));
			anaCell.setColspan(2);
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph(String.valueOf(nf.format(v)), fontChinese));
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph("健康的家庭偿债能力在1.5以上。", fontChinese));
			anaCell.setColspan(5);
			anaTable.addCell(anaCell);

			// 储蓄投资能力=盈余/收入总额
			if(assetDebtMap.get("monthIn")==0.00){
				v = 0.00;
			}else{
				v = assetDebtMap.get("monthNet") / assetDebtMap.get("monthIn");
			}
			
			anaCell = new PdfPCell(new Paragraph("储蓄投资能力", fontChinese));
			anaCell.setColspan(2);
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph(String.valueOf(nf.format(v)), fontChinese));
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph("健康的储蓄投资能力要保持在0.2-0.3以上。", fontChinese));
			anaCell.setColspan(5);
			anaTable.addCell(anaCell);

			// 开源节流能力=支出/收入
			if(assetDebtMap.get("monthIn")==0.00){
				v = 0.00;
			}else{
				v = assetDebtMap.get("monthOut") / assetDebtMap.get("monthIn");
			}
			
			anaCell = new PdfPCell(new Paragraph("开源节流能力", fontChinese));
			anaCell.setColspan(2);
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph(String.valueOf(nf.format(v)), fontChinese));
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph("此指标逐年下降为好。", fontChinese));
			anaCell.setColspan(5);
			anaTable.addCell(anaCell);

			// 资产收入比率 =净资产/年收入
			if(assetDebtMap.get("monthIn")==0.00){
				v = 0.00;
			}else{
				v = assetDebtMap.get("newAsset") / (assetDebtMap.get("monthIn") * 12);
			}
			
			anaCell = new PdfPCell(new Paragraph("净资产收入比", fontChinese));
			anaCell.setColspan(2);
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph(String.valueOf(nf.format(v)), fontChinese));
			anaTable.addCell(anaCell);
			
			anaCell = new PdfPCell(new Paragraph("如净资产收入比<0.5，说明有必要控制开支，需要更多地进行储蓄或投资，同时努力工作使收入增加。如0.5<净资产收入比<3，适合较年轻客户，若已接近退休年龄，则有必要采取措施增加其净资产。如净资产收入比>3,说明目前的财务状况良好。", fontChinese));
			anaCell.setColspan(5);
			anaTable.addCell(anaCell);
			
			//下面是点评区
		    anaCell = new PdfPCell(new Paragraph("财务指标点评", fontChinese));
			tempCell.setColspan(8);
			
			v = assetDebtMap.get("newAsset");
			
			String sqlX = "SELECT V.FZZZCBLDP_ADVISE,V.FZSRBLDP_ADVISE,V.LDXBLDP_ADVISE, " +
					"V.CXBLDP_ADVISE,V.TZYJZCBLDPL_ADVISE,V.XFLDP_ADVISE,  " +
					"V.LCCJLDP_ADVISE,V.SYLDP_ADVISE,V.ZHDP_ADVISE  " +
					"FROM OCRM_F_FIN_ANA_ADVISE_VISTA V  " +
					"WHERE V.CUST_ID = '"+ custId +"'";
			List<Object[]> XList = this.em.createNativeQuery(sqlX).getResultList();
			if(XList != null && XList.size()>0){
				Object[] baseInfo = XList.get(0);//导入baseInfo
				
				anaCell = new PdfPCell(new Paragraph("负债总资产比率点评", fontChinese));
				anaCell.setColspan(3);
				anaTable.addCell(anaCell);
				anaCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[0]), fontChinese));
				anaCell.setColspan(5);
				anaTable.addCell(anaCell);
				
				anaCell = new PdfPCell(new Paragraph("负债收入比率点评", fontChinese));
				anaCell.setColspan(3);
				anaTable.addCell(anaCell);
				anaCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[1]), fontChinese));
				anaCell.setColspan(5);
				anaTable.addCell(anaCell);
				
				anaCell = new PdfPCell(new Paragraph("流动性比率点评", fontChinese));
				anaCell.setColspan(3);
				anaTable.addCell(anaCell);
				anaCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[2]), fontChinese));
				anaCell.setColspan(5);
				anaTable.addCell(anaCell);
				
				anaCell = new PdfPCell(new Paragraph("储蓄比率点评", fontChinese));
				anaCell.setColspan(3);
				anaTable.addCell(anaCell);
				anaCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[3]), fontChinese));
				anaCell.setColspan(5);
				anaTable.addCell(anaCell);
				
				anaCell = new PdfPCell(new Paragraph("投资与净资产比率点评", fontChinese));
				anaCell.setColspan(3);
				anaTable.addCell(anaCell);
				anaCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[4]), fontChinese));
				anaCell.setColspan(5);
				anaTable.addCell(anaCell);
				
				anaCell = new PdfPCell(new Paragraph("消费率点评", fontChinese));
				anaCell.setColspan(3);
				anaTable.addCell(anaCell);
				anaCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[5]), fontChinese));
				anaCell.setColspan(5);
				anaTable.addCell(anaCell);
				
				anaCell = new PdfPCell(new Paragraph("理财成就率点评", fontChinese));
				anaCell.setColspan(3);
				anaTable.addCell(anaCell);
				anaCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[6]), fontChinese));
				anaCell.setColspan(5);
				anaTable.addCell(anaCell);
				
				anaCell = new PdfPCell(new Paragraph("收益率点评", fontChinese));
				anaCell.setColspan(3);
				anaTable.addCell(anaCell);
				anaCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[7]), fontChinese));
				anaCell.setColspan(5);
				anaTable.addCell(anaCell);
				
				anaCell = new PdfPCell(new Paragraph("综合点评", fontChinese));
				anaCell.setColspan(3);
				anaTable.addCell(anaCell);
				anaCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[8]), fontChinese));
				anaCell.setColspan(5);
				anaTable.addCell(anaCell);
			}
			document.add(anaTable);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//关闭Document对象
			if (document != null){
				document.close();
			}
		}
	}
	
    /**
     * 本行资产
     * @param id
     * @return
     */
    public JFreeChart currBankChart(String custId){
    	String sql = "SELECT NVL(p.prod_name, A.PRODUCT_ID) AS prod_name, sum(a.MS_AC_BAL) as BAL " +
    			"FROM ACRM_F_DP_SAVE_INFO A left join ocrm_f_pd_prod_info p " +
    			"on p.product_id = a.product_id " +
    			"where A.CUST_ID = '"+custId+"' " +
    			"group by a.product_id, p.prod_name";
    	DefaultPieDataset dataset = new DefaultPieDataset();
    	List<?> list = this.em.createNativeQuery(sql).getResultList();
    	if(list != null && list.size() >0){
    		for(int i=0;i<list.size();i++){
    			Object[] objArr = (Object[]) list.get(i);
        		dataset.setValue(String.valueOf(objArr[0]), Double.valueOf(String.valueOf(objArr[1])));
    		}
    	}
    	// Create the chart object 搭建图表对象
		PiePlot3D plot = new PiePlot3D(dataset);//加入dataset
		plot.setInsets(new RectangleInsets(0, 5, 5, 5));
		plot.setForegroundAlpha(0.6f);
		plot.setNoDataMessage("无相关数据");
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{2}"));
		plot.setLabelBackgroundPaint(null);//标签背景颜色
		plot.setLabelOutlinePaint(null);//标签边框颜色
		plot.setLabelShadowPaint(null);//标签阴影颜色
		plot.setLabelFont(new java.awt.Font("黑体", 10, 10));//标签字体
		plot.setURLGenerator(new StandardPieURLGenerator("", "type"));
		plot.setToolTipGenerator(new StandardPieToolTipGenerator());
		
		JFreeChart chart = new JFreeChart("客户本行资产信息", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		chart.setTitle(new TextTitle("客户本行资产信息"));
		chart.getLegend().setItemFont(new java.awt.Font("黑体", 10, 8));//图例字体
		chart.setBackgroundPaint(Color.white);
		return chart;
    }
    
    /**
     * 他行资产
     * @param id
     * @return
     */
    public JFreeChart otherBankChart(String custId){
    	String sql = "select o1.F_VALUE, o2.AMOUNT_VALUE from OCRM_SYS_LOOKUP_ITEM o1  " +
    			"left outer join ( " +
    			"select a.ASSETS_TYPE, a.AMOUNT_VALUE  " +
    			"from OCRM_F_FIN_BAL_SHEET a  " +
    			"where a.BELONG_TYPE = '1' and a.ASSET_DEBT_TYPE = '1'  " +
    			"and a.CUST_ID = '"+ custId +"') o2  " +
    			"on o1.F_CODE = o2.ASSETS_TYPE  " +
    			"where o1.F_LOOKUP_ID = 'OB_ASSETS_TYPE'  " +
    			"order by o1.f_code";
    	DefaultPieDataset dataset = new DefaultPieDataset();
    	List<?> list = this.em.createNativeQuery(sql).getResultList();
    	if(list != null && list.size() >0){
    		for(int i=0;i<list.size();i++){
    			Object[] objArr = (Object[]) list.get(i);
    			if(objArr[1]==null){
    				dataset.setValue(String.valueOf(objArr[0]), Double.valueOf(String.valueOf(0.00)));
    			}else{
    				dataset.setValue(String.valueOf(objArr[0]), Double.valueOf(String.valueOf(objArr[1])));
    			}
    		}
    	}
    	// Create the chart object 
		PiePlot3D plot = new PiePlot3D(dataset);
		plot.setInsets(new RectangleInsets(0, 5, 5, 5));
		plot.setForegroundAlpha(0.6f);
		plot.setNoDataMessage("无相关数据");
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{2}"));
		plot.setLabelBackgroundPaint(null);//标签背景颜色
		plot.setLabelOutlinePaint(null);//标签边框颜色
		plot.setLabelShadowPaint(null);//标签阴影颜色
		plot.setLabelFont(new java.awt.Font("黑体", 10, 10));//标签字体
		plot.setURLGenerator(new StandardPieURLGenerator("", "type"));
		plot.setToolTipGenerator(new StandardPieToolTipGenerator());
		
		JFreeChart chart = new JFreeChart("客户他行资产信息", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		chart.setTitle(new TextTitle("客户他行资产信息"));
		chart.getLegend().setItemFont(new java.awt.Font("黑体", 10, 8));//图例字体
		chart.setBackgroundPaint(Color.white);
		return chart;
    }
    
    /**
     * 其它资产
     * @param id
     * @return
     */
    public JFreeChart otherChart(String custId){
    	String sql = "select o1.F_VALUE, o2.AMOUNT_VALUE from OCRM_SYS_LOOKUP_ITEM o1  " +
    			"left outer join ( " +
    			"select a.ASSETS_TYPE, a.AMOUNT_VALUE from OCRM_F_FIN_BAL_SHEET a  " +
    			"where a.BELONG_TYPE = '2' and a.ASSET_DEBT_TYPE = '1'  " +
    			"and a.CUST_ID = '"+ custId +"') o2   " +
    			"on o1.F_CODE = o2.ASSETS_TYPE  " +
    			"where o1.F_LOOKUP_ID = 'O_ASSETS_TYPE'  " +
    			"order by o1.f_code";
    	List<?> list = this.em.createNativeQuery(sql).getResultList();
    	DefaultCategoryDataset dataset=new DefaultCategoryDataset();
    	if(list != null && list.size() >0){
    		for(int i=0;i<list.size();i++){
    			Object[] objArr = (Object[]) list.get(i);
    			if(objArr[1]==null){
    				dataset.setValue(Double.valueOf(String.valueOf(0.00)),String.valueOf(objArr[0]),String.valueOf(objArr[0]));
    			}else{
    				dataset.setValue(Double.valueOf(String.valueOf(objArr[1])),String.valueOf(objArr[0]),String.valueOf(objArr[0]));
    			}
    		}
    	}
        JFreeChart chart = ChartFactory.createBarChart3D("客户其它资产信息", "","", dataset,PlotOrientation.VERTICAL, false,false,false);
		chart.setTitle(new TextTitle("客户其它资产信息"));
		CategoryPlot categoryplot = chart.getCategoryPlot(); // 获得 plot：CategoryPlot！！
		CategoryAxis categoryaxis = categoryplot.getDomainAxis(); // 横轴上的Lable45度倾斜,可以改成其他
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	    categoryaxis.setTickLabelFont(new java.awt.Font("黑体", 10, 8));// 设定字体、类型、字号
		chart.setBackgroundPaint(Color.white); 
		return chart;
    }
    
    /**
     * 收入支出
     * @return
     */
    public JFreeChart inoutChart(String custId){
    	String sql = "select o1.F_VALUE, o2.MONEY from OCRM_SYS_LOOKUP_ITEM o1  " +
    			"left outer join ( " +
    			"select a.DETIAL_TYPE, a.MONEY  " +
    			"from OCRM_F_FIN_CUST_IO a  " +
    			"where a.IO_TYPE = '1' and a.CUST_ID = '"+ custId +"') o2  " +
    			"on o1.F_CODE = o2.DETIAL_TYPE  " +
    			"where o1.F_LOOKUP_ID = 'IN_TYPE'  " +
    			"order by o1.f_code";
    	List<?> list = this.em.createNativeQuery(sql).getResultList();
    	DefaultCategoryDataset dataset=new DefaultCategoryDataset();
    	if(list != null && list.size() >0){
    		for(int i=0;i<list.size();i++){
    			Object[] objArr = (Object[]) list.get(i);
    			if(objArr[1]==null){
    				dataset.setValue(Double.valueOf(String.valueOf(0.00)),String.valueOf(objArr[0]),String.valueOf(objArr[0]));
    			}else{
    				dataset.setValue(Double.valueOf(String.valueOf(objArr[1])),String.valueOf(objArr[0]),String.valueOf(objArr[0]));
    			}
    		}
    	}
        JFreeChart chart = ChartFactory.createBarChart3D("客户家庭月度收入信息", "","", dataset,PlotOrientation.VERTICAL, false,false,false);
		chart.setTitle(new TextTitle("客户家庭月度收入信息"));
		CategoryPlot categoryplot = chart.getCategoryPlot(); // 获得 plot：CategoryPlot！！
		CategoryAxis categoryaxis = categoryplot.getDomainAxis(); // 横轴上的Lable45度倾斜,可以改成其他
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	    categoryaxis.setTickLabelFont(new java.awt.Font("黑体", 10, 8));// 设定字体、类型、字号
		chart.setBackgroundPaint(Color.white); 
		return chart;
    }
    
    //最终结果栏
    public Map findCustAllAssetAndDebt(String custId, String instCode) {
		Map map = new HashMap();
		Double bankAssetSum = 0.00;
		Double bankDebtSum = 0.00;
		Double bankNetAsset = 0.00;

		Double otherAssetSum = 0.00;
		Double otherDebtSum = 0.00;
		Double otherNetAsset = 0.00;

		Double anotherAssetSum = 0.00;
		Double anotherDebtSum = 0.00;
		Double anotherNetAsset = 0.00;

		Double monthIn = 0.00;
		Double monthOut = 0.00;
		Double monthNet = 0.00;

		Double assetSum = 0.00;
		Double debtSum = 0.00;
		Double newAsset = 0.00;

		List<Object[]> list = new ArrayList();
		StringBuffer sb = new StringBuffer();
		sb.append(" select  belong_type,asset_debt_type, sum(amount_value)");
		sb.append(" from OCRM_F_FIN_BAL_SHEET");
		sb.append(" where cust_id = '" + custId + "'");
		sb.append(" group by asset_debt_type, belong_type");
		list = baseDAO.findByNativeSQLWithIndexParam(sb.toString());
		for (Object[] o : list) {
			if (o[0].equals("1") && o[2] != null && !o[2].equals("")) {
				if (o[1].equals("1")) {
					otherAssetSum = Double.valueOf(o[2].toString());
				} else if (o[1].equals("2")) {
					otherDebtSum = Double.valueOf(o[2].toString());
				}
			} else if (o[0].equals("2") && o[2] != null && !o[2].equals("")) {
				if (o[1].equals("1")) {
					anotherAssetSum = Double.valueOf(o[2].toString());
				} else if (o[1].equals("2")) {
					anotherDebtSum = Double.valueOf(o[2].toString());
				}
			}
		}
		otherNetAsset = otherAssetSum - otherDebtSum;
		anotherNetAsset = anotherAssetSum - anotherDebtSum;
		sb = new StringBuffer();
		//资产
		sb.append(" SELECT NVL(p.prod_name, A.PRODUCT_ID) AS prod_name, sum(a.MS_AC_BAL) as BAL");
		sb.append(" FROM ACRM_F_DP_SAVE_INFO A");
		sb.append(" left join ocrm_f_pd_prod_info p");
		sb.append(" on p.product_id = a.product_id");
		sb.append(" where a.cust_ID = '"+ custId +"'");
		sb.append(" group by a.product_id, p.prod_name");
		list.clear();
		list = baseDAO.findByNativeSQLWithIndexParam(sb.toString());
		if (list != null && list.size() > 0) {
			Object[] o = list.get(0);
			if (o[1] != null && !o[1].equals("")) {
				bankAssetSum = Double.valueOf(o[1].toString());
			}
		}
		sb = new StringBuffer();
		//负债
		sb.append(" SELECT NVL(p.prod_name, A.PRODUCT_ID) AS prod_name, sum(a.BAL) as BAL");
		sb.append(" FROM ACRM_F_CI_ASSET_BUSI_PROTO A");
		sb.append(" left join ocrm_f_pd_prod_info p");
		sb.append(" on p.product_id = a.product_id");
		sb.append(" where a.cust_ID = '"+ custId +"'");
		sb.append(" group by a.product_id, p.prod_name");
		list.clear();
		list = baseDAO.findByNativeSQLWithIndexParam(sb.toString());
		if (list != null && list.size() > 0) {
			Object[] o = list.get(0);
			if (o[1] != null && !o[1].equals("")) {
				bankDebtSum = Double.valueOf(o[1].toString());
			}
		}
		bankNetAsset = bankAssetSum - bankDebtSum;
		

		sb = new StringBuffer();
		list.clear();
		sb.append(" select IO_TYPE, sum(MONEY)");
		sb.append(" from OCRM_F_FIN_CUST_IO");
		sb.append(" where CUST_ID = '" + custId + "'");
		sb.append(" group by IO_TYPE");
		list = baseDAO.findByNativeSQLWithIndexParam(sb.toString());
		for (Object[] o : list) {

			if (o[0].equals("1") && o[1] != null && !o[1].equals("")) {
				monthIn = Double.valueOf(o[1].toString());
			} else if (o[0].equals("2") && o[1] != null && !o[1].equals("")) {
				monthOut = Double.valueOf(o[1].toString());
			}
		}
		monthNet = monthIn - monthOut;
		assetSum = bankAssetSum + otherAssetSum + anotherAssetSum + monthIn;
		debtSum = bankDebtSum + otherDebtSum + anotherDebtSum + monthOut;
		newAsset = bankNetAsset + otherNetAsset + anotherNetAsset + monthNet;

		map.put("bankAssetSum", bankAssetSum);
		map.put("bankDebtSum", bankDebtSum);
		map.put("bankNetAsset", bankNetAsset);
		map.put("otherAssetSum", otherAssetSum);
		map.put("otherDebtSum", otherDebtSum);
		map.put("otherNetAsset", otherNetAsset);
		map.put("anotherAssetSum", anotherAssetSum);
		map.put("anotherDebtSum", anotherDebtSum);
		map.put("anotherNetAsset", anotherNetAsset);
		map.put("monthIn", monthIn);
		map.put("monthOut", monthOut);
		map.put("monthNet", monthNet);
		map.put("assetSum", assetSum);
		map.put("debtSum", debtSum);
		map.put("newAsset", newAsset);

		return map;
	}
    
    public String getReportId(String custId){
    	String reportId = "";
    	List<OcrmFFinAnaAdviseVista> list =  this.findByJql("select c from OcrmFFinAnaAdviseVista c where c.custId = '"+custId+"'", null);
    	for(OcrmFFinAnaAdviseVista a : list){
    		reportId = a.getId().toString();
    	}
    	return reportId;
    }
}