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
 * @describtion: 机构贡献度连续12个月趋势
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年7月24日 上午10:54:48
 */
@Action("/orgContributeMonth")
public class OrgContributeMonthAction extends CommonAction{
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
    @Qualifier("dsOracle")
    private DataSource ds;

	public void prepare(){
		AuthUser auth=(AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		SQL ="SELECT T.COUNT_YEAR,T.COUNT_MONTH, ROUND(COALESCE(SUM(T.CONTRIBUTE),0)/10000,2) AS CONTRIBUTE FROM ACRM_F_CI_ORG_CONTRIBUTE_MONTH T "
				+ " WHERE T.ORG_ID IN (SELECT SYSUNIT.UNITID FROM SYS_UNITS SYSUNIT WHERE SYSUNIT.UNITSEQ LIKE '"+auth.getUnitInfo().get("UNITSEQ")+"%') "
				+ " GROUP BY COUNT_YEAR, COUNT_MONTH " ;
		this.setPrimaryKey("COUNT_YEAR,COUNT_MONTH");
		String categories = "";
		String first = "";
		String last = "";
		Connection conn = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			conn = ds.getConnection();
			statement = conn.createStatement();
			rs = statement.executeQuery(" SELECT t.count_year|| '.' || t.count_month from ACRM_F_CI_ORG_CONTRIBUTE_MONTH t where t.ORG_ID in (SELECT SYSUNIT.UNITID FROM SYS_UNITS SYSUNIT WHERE SYSUNIT.UNITSEQ LIKE '"+auth.getUnitInfo().get("UNITSEQ")+"%') GROUP BY COUNT_YEAR, COUNT_MONTH order by COUNT_YEAR, COUNT_MONTH");
			while(rs.next()){
				categories = categories + ","+ rs.getString(1);
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
		fcbo.addDataColumn("CONTRIBUTE", "贡献度总量");
		fcbo.setCategories(categories.length()>0?categories.substring(1):"", ",");
		//fcbo.setCategories("一月,二月,三月,四月,五月,六月,七月,八月,九月,十月,十一月,十二月", ",");
		fcbo.addAttribute("yAxisMinValue", "10");
		fcbo.addAttribute("showValues", "0");
		fcbo.addAttribute("yaxisname", "(单位:万元)");
	//	fcbo.addAttribute("caption", "机构贡献度连续12个月趋势");
		fcbo.addAttribute("palette", "1");
		fcbo.addAttribute("formatNumberScale", "0");
		fcbo.addAttribute("baseFontSize", "13");
		fcbo.addAttribute("basefont", "宋体");
		
		chart = fcbo;
		datasource = ds;
	}

}

