package com.yuchengtech.bcrm.finService.service;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

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
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.yuchengtech.bcrm.finService.model.OcrmFFinCustDemand;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.upload.FileTypeConstance;
import com.yuchengtech.bob.vo.AuthUser;

@Service
public class CustDemandService extends CommonService {
	public CustDemandService(){
		JPABaseDAO<OcrmFFinCustDemand, Long> baseDAO = new JPABaseDAO<OcrmFFinCustDemand, Long>(OcrmFFinCustDemand.class);
		super.setBaseDAO(baseDAO);
	}
	
	public Object save(Object obj){
		AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		OcrmFFinCustDemand demand = (OcrmFFinCustDemand)obj;
		
		if(demand.getDemandId() == null){
			demand.setCreateDt(new Date());
			demand.setCreatorId(auth.getUserId());
		}
		demand.setAvailable("1");
		return super.save(demand);
	}
	
	/**
     * 生成产品配置建议方案
     * @param demandId 顾问式理财服务需求ID
	 * @throws Exception 
     */
    public void createReport(String demandId) throws Exception{
    	ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	String custId = "";
    	String path = FileTypeConstance.getImportTempaltePath();//文件存放路径
		String name = "LCGH_"+demandId+".pdf";//文件名
		//创建一个 iTextSharp.text.Document对象的实例：
		Document document = new Document(PageSize.A4, 36, 72, 80, 80);
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
			Paragraph title = new Paragraph("产品配置建议方案\n\n",fontChinese);
			title.setAlignment(Element.ALIGN_CENTER);//居中对齐
			document.add(title);
			
			//风险评估信息
			String sql1 = "SELECT T.cust_id,T.CUST_NAME,(SELECT PAPER_NAME FROM OCRM_F_SM_PAPERS T WHERE OPTION_TYPE = '1' AND AVAILABLE = '4') AS PAPER_NAME,I.F_VALUE,T.INDAGETE_QA_SCORING FROM OCRM_F_FIN_CUST_RISK T LEFT JOIN OCRM_SYS_LOOKUP_ITEM I ON I.F_LOOKUP_ID = 'CUST_RISK_CHARACT' AND I.F_CODE = T.CUST_RISK_CHARACT WHERE T.HIS_FLAG = '0' AND T.cust_id = (SELECT T1.CUST_ID FROM OCRM_F_FIN_CUST_DEMAND T1 WHERE T1.DEMAND_ID = '"+demandId+"')";
			List<Object[]> riskList = this.em.createNativeQuery(sql1).getResultList();
			if(riskList != null && riskList.size()>0){
				Object[] baseInfo = riskList.get(0);
				custId = String.valueOf(baseInfo[0]);
				PdfPCell tempCell = null;
				PdfPTable baseInfoTable = new PdfPTable(4);
				baseInfoTable.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
				PdfPCell titleCell = new PdfPCell(new Paragraph("风险承受能力评估结果", fontChinese));
				titleCell.setColspan(4);
				baseInfoTable.addCell(titleCell);
				tempCell = new PdfPCell(new Paragraph("客户号",fontChinese));
				baseInfoTable.addCell(tempCell);
				tempCell = new PdfPCell(new Paragraph(custId,fontChinese));
				baseInfoTable.addCell(tempCell);
				tempCell = new PdfPCell(new Paragraph("客户姓名",fontChinese));
				baseInfoTable.addCell(tempCell);
				baseInfoTable.addCell(new Paragraph(String.valueOf(baseInfo[1]),fontChinese));
				tempCell = new PdfPCell(new Paragraph("系统来源",fontChinese));
				baseInfoTable.addCell(tempCell);
				
				tempCell = new PdfPCell(new Paragraph("CRM系统评估",fontChinese));
				tempCell.setColspan(3);
				baseInfoTable.addCell(tempCell);
				
				//baseInfoTable.addCell(new Paragraph("",fontChinese));
				
				tempCell = new PdfPCell(new Paragraph("风险等级",fontChinese));
				tempCell.setRowspan(2);
				tempCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				baseInfoTable.addCell(tempCell);
				
				tempCell = new PdfPCell(new Paragraph("根据刚完成的《"+String.valueOf(baseInfo[2])+"》，你的风险测试结果为：",fontChinese));
				tempCell.setColspan(3);
				baseInfoTable.addCell(tempCell);
				
				
				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[3]),fontChinese));
				tempCell.setColspan(3);
				baseInfoTable.addCell(tempCell);
				
				tempCell = new PdfPCell(new Paragraph("总分",fontChinese));
				baseInfoTable.addCell(tempCell);
				
				tempCell = new PdfPCell(new Paragraph(String.valueOf(baseInfo[4]),fontChinese));
				tempCell.setColspan(3);
				baseInfoTable.addCell(tempCell);
				document.add(baseInfoTable);
			}
				
		
			//资产配置段落
			document.add(new Paragraph("\n"));
			PdfContentByte contentByte = writer.getDirectContent();
			int width = (int)PageSize.A4.getWidth()/2;
			PdfPTable chartTable = new PdfPTable(2);
		    chartTable.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
		    PdfPCell tempCell = new PdfPCell(new Paragraph("客户资产负债信息", fontChinese));
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
		    
		    List otherList = this.em.createNativeQuery("select 1 as TYPE,NVL(sum(a.amount_value),0) AS VAL from OCRM_F_FIN_BAL_SHEET a where a.BELONG_TYPE = '2' and a.asset_debt_type = '1' and a.cust_id = '"+custId+"' "
					+" union select 2 as TYPE,NVL(sum(a.amount_value),0) AS VAL from OCRM_F_FIN_BAL_SHEET a where a.BELONG_TYPE = '2' and a.asset_debt_type = '2' and a.cust_id = '"+custId+"' "
					+" union select 3 as TYPE,NVL(sum(case when a.asset_debt_type = '1' then NVL(a.amount_value,0) when a.asset_debt_type = '2' then -NVL(a.amount_value,0) end),0) AS VAL from OCRM_F_FIN_BAL_SHEET a where a.BELONG_TYPE = '2' and a.cust_id = '"+custId+"'").getResultList();
				    
		    List inoutList = this.em.createNativeQuery("select 1 as TYPE,NVL(sum(a.MONEY),0) AS VAL from OCRM_F_FIN_CUST_IO a where a.IO_TYPE = '1' and a.cust_id = '"+custId+"' "
					+" union select 2 as TYPE,NVL(sum(a.MONEY),0) AS VAL from OCRM_F_FIN_CUST_IO a where a.IO_TYPE = '2' and a.cust_id = '"+custId+"' "
					+" union select 3 as TYPE,NVL(sum(case when a.IO_TYPE = '1' then NVL(a.MONEY,0) when a.IO_TYPE = '2' then -NVL(a.MONEY,0) end),0) AS VAL from OCRM_F_FIN_CUST_IO a where a.cust_id = '"+custId+"'").getResultList();
			
		    PdfPTable countTable = new PdfPTable(8);
		    countTable.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
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
			tempCell = new PdfPCell(new Paragraph(String.valueOf(((Object[])currbankList.get(0))[1]), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("他行资产合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(((Object[])otherbankList.get(0))[1]), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("其它资产合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(((Object[])otherList.get(0))[1]), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("月度总收入：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(((Object[])inoutList.get(0))[1]), fontChinese));
			countTable.addCell(tempCell);
			
			
			tempCell = new PdfPCell(new Paragraph("本行负债合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(((Object[])currbankList.get(1))[1]), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("他行负债合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(((Object[])otherbankList.get(1))[1]), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("其它负债合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(((Object[])otherList.get(1))[1]), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("月度总支出：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(((Object[])inoutList.get(1))[1]), fontChinese));
			countTable.addCell(tempCell);
			
			
			tempCell = new PdfPCell(new Paragraph("本行净资产合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(Double.valueOf(String.valueOf(((Object[])currbankList.get(0))[1])) - Double.valueOf(String.valueOf(((Object[])currbankList.get(1))[1]))), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("他行净资产合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(((Object[])otherbankList.get(2))[1]), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("其它净资产合计：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(((Object[])otherList.get(2))[1]), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("盈余：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(((Object[])inoutList.get(2))[1]), fontChinese));
			countTable.addCell(tempCell);
			
			
			tempCell = new PdfPCell(new Paragraph("客户总资产：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(Double.valueOf(String.valueOf(((Object[])currbankList.get(0))[1]))+Double.valueOf(String.valueOf(((Object[])otherbankList.get(0))[1]))+Double.valueOf(String.valueOf(((Object[])otherList.get(0))[1]))), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("客户总负债资产：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(Double.valueOf(String.valueOf(((Object[])currbankList.get(1))[1]))+Double.valueOf(String.valueOf(((Object[])otherbankList.get(1))[1]))+Double.valueOf(String.valueOf(((Object[])otherList.get(1))[1]))), fontChinese));
			countTable.addCell(tempCell);
			
			tempCell = new PdfPCell(new Paragraph("客户净资产：", fontChinese));
			countTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph(String.valueOf(Double.valueOf(String.valueOf(((Object[])currbankList.get(0))[1])) - Double.valueOf(String.valueOf(((Object[])currbankList.get(1))[1]))+Double.valueOf(String.valueOf(((Object[])otherbankList.get(2))[1]))+Double.valueOf(String.valueOf(((Object[])otherList.get(2))[1]))), fontChinese));
			tempCell.setColspan(3);
			countTable.addCell(tempCell);

			document.add(countTable);
			
			document.add(new Paragraph("\n"));
			//资产配置
			PdfPTable chartTable2 = new PdfPTable(2);
		    chartTable2.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
			JFreeChart templateChart = this.templateChart();
			JFreeChart moneyConfigChart = this.moneyConfigChart(demandId);
			
		    Image templateChartPng = Image.getInstance(contentByte, templateChart.createBufferedImage(width, 300), 1);
		    chartTable2.addCell(templateChartPng);
		    Image moneyConfigChartPng = Image.getInstance(contentByte, moneyConfigChart.createBufferedImage(width, 300), 1);
		    chartTable2.addCell(moneyConfigChartPng);
			
		    document.add(chartTable2);
		    
		  //资产配置细项
			document.add(new Paragraph("\n"));
			
	        String riskType = request.getParameter("riskType");
		    String sql = "SELECT I.F_VALUE,t1.prod_rate as prod_rate1, NVL(SUM(T.CONF_SCALE), 0) prod_rate2  FROM OCRM_SYS_LOOKUP_ITEM I"
	        		+ " left join OCRM_F_FIN_TEMPLATE t1 on t1.prod_type = i.f_code and t1.risk_type='"+riskType+"'"
	        		+ " LEFT JOIN (SELECT P.PROD_TYPE_ID, ROUND(100*C.CONF_SCALE/c1.total,2) CONF_SCALE"
	        		+ " FROM OCRM_F_FIN_PROD_CONF C  LEFT JOIN OCRM_F_PD_PROD_INFO P ON P.PRODUCT_ID = C.PROD_ID "
	        		+ " left join (select DEMAND_ID,nvl(sum(CONF_SCALE),1) total from OCRM_F_FIN_PROD_CONF group by DEMAND_ID)  c1 on c1.DEMAND_ID = c.demand_id"
	        		+ " WHERE C.DEMAND_ID = '"+demandId+"' ) T ON TO_CHAR(T.PROD_TYPE_ID) = I.F_CODE WHERE I.F_LOOKUP_ID = 'PROD_TYPE_ID' GROUP BY I.F_VALUE,t1.prod_rate";
		    List moneyConfigList1 = this.em.createNativeQuery(sql).getResultList();
			
		    PdfPTable moneyConfigTable1 = new PdfPTable(3);
		    moneyConfigTable1.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
		    tempCell = new PdfPCell(new Paragraph("产品大类配置", fontChinese));
			tempCell.setColspan(3);
			moneyConfigTable1.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph("产品大类", fontChinese));
			moneyConfigTable1.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph("推荐占比", fontChinese));
			moneyConfigTable1.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph("实际占比", fontChinese));
			moneyConfigTable1.addCell(tempCell);
			for(int i=0;i<moneyConfigList1.size();i++){
				Object[] obj = (Object[]) moneyConfigList1.get(i);
				tempCell = new PdfPCell(new Paragraph(String.valueOf(obj[0]), fontChinese));
				moneyConfigTable1.addCell(tempCell);
				
				tempCell = new PdfPCell(new Paragraph(String.valueOf(obj[1])+"%", fontChinese));
				moneyConfigTable1.addCell(tempCell);
				
				tempCell = new PdfPCell(new Paragraph(String.valueOf(obj[2])+"%", fontChinese));
				moneyConfigTable1.addCell(tempCell);
				
			}
			document.add(moneyConfigTable1);
			document.add(new Paragraph("\n"));
		    //资产配置细项
		    List moneyConfigList = this.em.createNativeQuery("select i.f_value,p.prod_name,nvl(sum(c.conf_scale),0),round(100*nvl(sum(c.conf_scale),0)/c2.total,2) from OCRM_F_FIN_PROD_CONF c "
				+" left join ocrm_f_pd_prod_info p on p.product_id = c.prod_id "
				+" left join ocrm_sys_lookup_item i on i.f_code = p.prod_type_id and i.f_lookup_id = 'PROD_TYPE_ID' "
				+" left join (select c1.demand_id,nvl(sum(c1.conf_scale),1) as total from OCRM_F_FIN_PROD_CONF c1 where c1.demand_id = '"+demandId+"' group by c1.demand_id) c2 on c2.demand_id = c.demand_id "
				+" where c.demand_id = '"+demandId+"' group by i.f_value,p.prod_name,c2.total").getResultList();
			
		    PdfPTable moneyConfigTable = new PdfPTable(4);
		    moneyConfigTable.setWidthPercentage(98);//设置表格宽度占可用宽度的百分比,默认80%
		    tempCell = new PdfPCell(new Paragraph("产品建议细项", fontChinese));
			tempCell.setColspan(4);
			moneyConfigTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph("产品大类", fontChinese));
			moneyConfigTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph("产品名称", fontChinese));
			moneyConfigTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph("产品规模", fontChinese));
			moneyConfigTable.addCell(tempCell);
			tempCell = new PdfPCell(new Paragraph("产品占比", fontChinese));
			moneyConfigTable.addCell(tempCell);
			for(int i=0;i<moneyConfigList.size();i++){
				Object[] obj = (Object[]) moneyConfigList.get(i);
				tempCell = new PdfPCell(new Paragraph(String.valueOf(obj[0]), fontChinese));
				moneyConfigTable.addCell(tempCell);
				
				tempCell = new PdfPCell(new Paragraph(String.valueOf(obj[1]), fontChinese));
				moneyConfigTable.addCell(tempCell);
				
				tempCell = new PdfPCell(new Paragraph(String.valueOf(obj[2]), fontChinese));
				moneyConfigTable.addCell(tempCell);
				
				tempCell = new PdfPCell(new Paragraph(String.valueOf(obj[3])+"%", fontChinese));
				moneyConfigTable.addCell(tempCell);
			}
			document.add(moneyConfigTable);
		    
		    
		    //法律法规信息
		    String fagui ="          本方案严格遵守中华人民共和国相关法律、法规，按照您提供的需求及财务等信息制定的，如果其中任何信息不确切或发生改变，本方案将调整后才有效。 本方案严格以双方自愿为原则，严格遵守保密原则，保护您及家庭成员的隐私权。本产品配置方案仅供参考，最终的产品购入有赖于您的自主决策，本行对于您最终的产品配置无需承担任何责任。在购买产品前，请认真阅读相关产品销售文件，本方案对产品预期收益不作任何保证和承诺，也不保证您购买时产品的剩余额度能够满足您的购买需求。";
		    document.add(new Paragraph("\n"));
		    document.add(new Paragraph(fagui,fontChinese));
		}catch (Exception e) { 
			e.printStackTrace(); 
			throw e;
		}finally{
			//关闭Document对象
			if (document != null){
				document.close();
			}
		}
    }
    
    /**
     * 他行资产
     * @param id
     * @return
     */
    public JFreeChart otherBankChart(String custId){
    	String sql = "select o1.f_value, nvl(a.AMOUNT_VALUE,0) from OCRM_SYS_LOOKUP_ITEM o1  "
    			+ " left join OCRM_F_FIN_BAL_SHEET a on a.BELONG_TYPE = '1' and o1.F_CODE = a.ASSETS_TYPE and a.ASSET_DEBT_TYPE = '1' and a.cust_id = '"+custId+"' "
    			+ " where o1.F_LOOKUP_ID = 'OB_ASSETS_TYPE' union "
    			+ " select o1.f_value, nvl(a.AMOUNT_VALUE,0) from OCRM_SYS_LOOKUP_ITEM o1 "
    			+ " left join OCRM_F_FIN_BAL_SHEET a on a.BELONG_TYPE = '1' and o1.F_CODE = a.ASSETS_TYPE and a.ASSET_DEBT_TYPE = '2' and a.cust_id = '"+custId+"' "
    			+ " where o1.F_LOOKUP_ID = 'OB_LAIB_TYPE'";
    	DefaultPieDataset dataset = new DefaultPieDataset();
    	List<?> list = this.em.createNativeQuery(sql).getResultList();
    	if(list != null && list.size() >0){
    		for(int i=0;i<list.size();i++){
    			Object[] objArr = (Object[]) list.get(i);
        		dataset.setValue(String.valueOf(objArr[0]), Double.valueOf(String.valueOf(objArr[1])));
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
		
		JFreeChart chart = new JFreeChart("客户他行资产负责信息", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		chart.setTitle(new TextTitle("客户他行资产负责信息"));
		chart.getLegend().setItemFont(new java.awt.Font("黑体", 10, 8));//图例字体
		chart.setBackgroundPaint(Color.white);
		return chart;
    }
    
    /**
     * 本行资产
     * @param id
     * @return
     */
    public JFreeChart currBankChart(String custId){
    	String sql = "SELECT NVL(p.prod_name,A.PRODUCT_ID) AS prod_name,sum(a.MS_AC_BAL) as BAL FROM ACRM_F_DP_SAVE_INFO A "
    			+ " left join ocrm_f_pd_prod_info p on p.product_id = a.product_id where a.cust_id = '"+custId+"' "
    			+ " group by a.product_id,p.prod_name "
    			+ " UNION SELECT NVL(p.prod_name,A.PRODUCT_ID) AS prod_name,sum(a.BAL) as BAL FROM ACRM_F_CI_ASSET_BUSI_PROTO A "
    			+ " left join ocrm_f_pd_prod_info p on p.product_id = a.product_id where a.cust_id = '"+custId+"' "
    			+ " group by a.product_id,p.prod_name";
    	DefaultPieDataset dataset = new DefaultPieDataset();
    	List<?> list = this.em.createNativeQuery(sql).getResultList();
    	if(list != null && list.size() >0){
    		for(int i=0;i<list.size();i++){
    			Object[] objArr = (Object[]) list.get(i);
        		dataset.setValue(String.valueOf(objArr[0]), Double.valueOf(String.valueOf(objArr[1])));
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
		
		JFreeChart chart = new JFreeChart("客户本行资产负责信息", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		chart.setTitle(new TextTitle("客户本行资产负责信息"));
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
    	String sql = "select o1.f_value, nvl(a.AMOUNT_VALUE,0) from OCRM_SYS_LOOKUP_ITEM o1  "
    			+ " left join OCRM_F_FIN_BAL_SHEET a on a.BELONG_TYPE = '2' and o1.F_CODE = a.ASSETS_TYPE and a.ASSET_DEBT_TYPE = '1' and a.cust_id = '"+custId+"' "
    			+ " where o1.F_LOOKUP_ID = 'O_ASSETS_TYPE' union "
    			+ " select o1.f_value, nvl(a.AMOUNT_VALUE,0) from OCRM_SYS_LOOKUP_ITEM o1 "
    			+ " left join OCRM_F_FIN_BAL_SHEET a on a.BELONG_TYPE = '2' and o1.F_CODE = a.ASSETS_TYPE and a.ASSET_DEBT_TYPE = '2' and a.cust_id = '"+custId+"' "
    			+ " where o1.F_LOOKUP_ID = 'O_LAIB_TYPE'";
    	List<?> list = this.em.createNativeQuery(sql).getResultList();
    	DefaultCategoryDataset dataset=new DefaultCategoryDataset();
    	if(list != null && list.size() >0){
    		for(int i=0;i<list.size();i++){
    			Object[] objArr = (Object[]) list.get(i);
    			dataset.setValue(Double.valueOf(String.valueOf(objArr[1])),String.valueOf(objArr[0]),String.valueOf(objArr[0]));
    		}
    	}
        JFreeChart chart = ChartFactory.createBarChart3D("客户其它资产负责信息", "","", dataset,PlotOrientation.VERTICAL, false,false,false);
		chart.setTitle(new TextTitle("客户其它资产负责信息"));
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
    	String sql = "select o1.f_value, nvl(a.MONEY,0) from OCRM_SYS_LOOKUP_ITEM o1  "
    			+ " left join OCRM_F_FIN_CUST_IO a on a.IO_TYPE = '1' and o1.F_CODE = a.DETIAL_TYPE and a.cust_id = '"+custId+"' "
    			+ " where o1.F_LOOKUP_ID = 'IN_TYPE' union "
    			+ " select o1.f_value, nvl(a.MONEY,0) from OCRM_SYS_LOOKUP_ITEM o1 "
    			+ " left join OCRM_F_FIN_CUST_IO a on a.IO_TYPE = '2' and o1.F_CODE = a.DETIAL_TYPE and a.cust_id = '"+custId+"' "
    			+ " where o1.F_LOOKUP_ID = 'OUT_TYPE'";
    	List<?> list = this.em.createNativeQuery(sql).getResultList();
    	DefaultCategoryDataset dataset=new DefaultCategoryDataset();
    	if(list != null && list.size() >0){
    		for(int i=0;i<list.size();i++){
    			Object[] objArr = (Object[]) list.get(i);
    			dataset.setValue(Double.valueOf(String.valueOf(objArr[1])),String.valueOf(objArr[0]),String.valueOf(objArr[0]));
    		}
    	}
        JFreeChart chart = ChartFactory.createBarChart3D("客户家庭月度收支信息", "","", dataset,PlotOrientation.VERTICAL, false,false,false);
		chart.setTitle(new TextTitle("客户家庭月度收支信息"));
		CategoryPlot categoryplot = chart.getCategoryPlot(); // 获得 plot：CategoryPlot！！
		CategoryAxis categoryaxis = categoryplot.getDomainAxis(); // 横轴上的Lable45度倾斜,可以改成其他
		categoryaxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
	    categoryaxis.setTickLabelFont(new java.awt.Font("黑体", 10, 8));// 设定字体、类型、字号
		chart.setBackgroundPaint(Color.white); 
		return chart;
    }
    
    /**
     * 模板资产配置
     * @return
     */
    public JFreeChart templateChart(){
    	String sql = "select I.F_VALUE,t.prod_rate from ocrm_f_fin_template t left join ocrm_sys_lookup_item i on i.f_lookup_id ='PROD_TYPE_ID' and i.f_code = t.PROD_TYPE";
    	DefaultPieDataset dataset = new DefaultPieDataset();
    	List<?> list = this.em.createNativeQuery(sql).getResultList();
    	if(list != null && list.size() >0){
    		for(int i=0;i<list.size();i++){
    			Object[] objArr = (Object[]) list.get(i);
        		dataset.setValue(String.valueOf(objArr[0]), Double.valueOf(String.valueOf(objArr[1])));
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
		
		JFreeChart chart = new JFreeChart("模板资产配置", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		chart.setTitle(new TextTitle("模板资产配置"));
		chart.getLegend().setItemFont(new java.awt.Font("黑体", 10, 8));//图例字体
		chart.setBackgroundPaint(Color.white);
		return chart;
    }
    
    /**
     * 推荐资产配置
     * @return
     */
    public JFreeChart moneyConfigChart(String demandId){
    	String sql = "SELECT I.F_VALUE,NVL(SUM(T.CONF_SCALE),0) FROM OCRM_SYS_LOOKUP_ITEM I LEFT JOIN (SELECT P.PROD_TYPE_ID,C.CONF_SCALE FROM OCRM_F_FIN_PROD_CONF C LEFT JOIN OCRM_F_PD_PROD_INFO P ON P.PRODUCT_ID = C.PROD_ID WHERE C.DEMAND_ID = '"+demandId+"') T ON TO_CHAR(T.PROD_TYPE_ID) = I.F_CODE WHERE I.F_LOOKUP_ID = 'PROD_TYPE_ID' GROUP BY I.F_VALUE";
    	DefaultPieDataset dataset = new DefaultPieDataset();
    	List<?> list = this.em.createNativeQuery(sql).getResultList();
    	if(list != null && list.size() >0){
    		for(int i=0;i<list.size();i++){
    			Object[] objArr = (Object[]) list.get(i);
        		dataset.setValue(String.valueOf(objArr[0]), Double.valueOf(String.valueOf(objArr[1])));
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
		
		JFreeChart chart = new JFreeChart("推荐资产配置", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
		chart.setTitle(new TextTitle("推荐资产配置"));
		chart.getLegend().setItemFont(new java.awt.Font("黑体", 10, 8));//图例字体
		chart.setBackgroundPaint(Color.white);
		return chart;
    }
}
