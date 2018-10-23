package com.yuchengtech.bcrm.individual.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;

import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.bob.vo.AuthUser;
import com.yuchengtech.crm.constance.JdbcUtil;
import com.yuchengtech.crm.constance.fusioncharts.LineChart;

/**
 * @describtion: 贷款余额趋势图(连续30天)
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年7月21日 上午10:56:59
 */
@Action("/orgCreDay")
public class OrgCreDayAction extends CommonAction{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

	public void prepare(){
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SQL = "select ROUND(COALESCE(SUM(t.CRE_BAL),0)/10000,2) as CRE_BAL,ROUND(COALESCE(SUM(t.CRE_YEAR_AVG),0)/10000,2) as CRE_YEAR_AVG,t.ODS_DATE  from ACRM_F_CI_ORG_CRE_DAY t"
				+ " where t.ORG_ID in (SELECT SYSUNIT.UNITID FROM SYS_UNITS SYSUNIT WHERE SYSUNIT.UNITSEQ LIKE '"+auth.getUnitInfo().get("UNITSEQ")+"%')"
				+ " group by ODS_DATE ";
		this.setPrimaryKey("ODS_DATE");
		String categories = "";
		String first = "";
		String last = "";
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(" SELECT TO_CHAR(T.ODS_DATE,'YYYY-MM-DD') from ACRM_F_CI_ORG_CRE_DAY t where t.ORG_ID in (SELECT SYSUNIT.UNITID FROM SYS_UNITS SYSUNIT WHERE SYSUNIT.UNITSEQ LIKE '"+auth.getUnitInfo().get("UNITSEQ")+"%') group by ODS_DATE order by ODS_DATE");
			while(rs.next()){
				categories = categories + ","+ rs.getString(1).substring(5, 7)+"."+rs.getString(1).substring(8, 10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.close(rs,statement,conn); //释放连接池
		}
		String[] temp = categories.split(",");
		if(temp.length > 1){
			first = temp[0];
			last = temp[temp.length -1];
		}
		LineChart fcbo = new LineChart();
		fcbo.addDataColumn("CRE_YEAR_AVG", "贷款年日均（折人民币）");
		fcbo.addDataColumn("CRE_BAL", "贷款时点余额（折人民币）");
		fcbo.setCategories(categories.length()>0?categories.substring(1):"", ",");
		fcbo.addAttribute("yAxisMinValue", "50000");
		fcbo.addAttribute("showValues", "0");
		fcbo.addAttribute("yaxisname", "金额(单位:万元)");
//		fcbo.addAttribute("caption", "存款余额趋势图(连续30天)");
		fcbo.addAttribute("palette", "1");
		fcbo.addAttribute("formatNumberScale", "0");
		fcbo.addAttribute("baseFontSize", "13");
		fcbo.addAttribute("basefont", "宋体");
		chart = fcbo;
		datasource = ds;
	}

}

