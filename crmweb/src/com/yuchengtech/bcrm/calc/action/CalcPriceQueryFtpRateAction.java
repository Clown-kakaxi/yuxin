package com.yuchengtech.bcrm.calc.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.calc.model.OcrmOFtpRate;
import com.yuchengtech.bcrm.calc.service.CalcPriceQueryFtpRateService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.exception.BizException;

/**
 * @description: 价格计算器--查询FTP
 * @author denghj
 * @data 2015-07-24
 */

@ParentPackage("json-default")
@Action("/calcPriceQueryFtpRate")
public class CalcPriceQueryFtpRateAction extends CommonAction {

	// private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;

	private float ftpRate;
	@Autowired
	private CalcPriceQueryFtpRateService service;

	/**
	 * 获取FTPRATE
	 * @return success
	 * @throws IOException
	 */
	public String getFtpRate(){
		try {
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			float limitTY = Float.parseFloat(request.getParameter("limitTY")); // 得到额度期限(年)----int
			String tenor = null;
//			if (limitTY < 1) { // 根据limitTY得出SQL-TENQR
//				tenor = "U1Y";
//			} else if (limitTY < 3) {
//				tenor = "A1YU3Y";
//			} else if (limitTY < 5) {
//				tenor = "A3YU5Y";
//			} else {
//				tenor = "A5Y";
//			}
			if(limitTY < 0.1 || limitTY == 0.1){//利率调整
				tenor = "1M";
			}else if(limitTY < 0.3 || limitTY == 0.3){
				tenor = "3M";
			}else if(limitTY < 0.6 || limitTY == 0.6){
				tenor = "6M";
			}else if(limitTY < 0.9 || limitTY == 0.9){
				tenor = "9M";
			}else if(limitTY < 1 || limitTY == 1){
				tenor = "1Y";
			}else if(limitTY < 2 || limitTY == 2){
				tenor = "2Y";
			}else if(limitTY < 3 || limitTY == 3){
				tenor = "3Y";
			}else if(limitTY < 5 || limitTY == 5){
				tenor = "5Y";
			}else if(limitTY < 7 || limitTY == 7){
				tenor = "7Y";
			}else if(limitTY < 10 || limitTY == 10){
				tenor = "10Y";
			}else if(limitTY < 15 || limitTY == 15){
				tenor = "15Y";
			}else if(limitTY < 20 || limitTY == 20){
				tenor = "20Y";
			}else if(limitTY < 30 || limitTY == 30){
				tenor = "30Y";
			}
			
			String sql = "SELECT f FROM OcrmOFtpRate f where f.tenor = '" + tenor
					+ "' and f.psnCusf = 'N' and  f.type='LOAN' and f.ccy='RMB' and f.currDat=(select MAX(T.currDat) FROM OcrmOFtpRate T)";
			List<OcrmOFtpRate> list = service.findByJql(sql, null);
			for (OcrmOFtpRate f : list) {
				ftpRate = f.getRate();
			}
			if (this.json != null){
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				map.put("ftpRate", ftpRate);
				this.json.put("json", map);
			} catch (Exception e) {
				throw new BizException(1, 2, "1006",
						"userOnlineAction onlineMax exception!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(1, 2, "1002", e.getMessage());
		}
		return "success";
	}
}
