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
import com.yuchengtech.bcrm.calc.model.OcrmORmbRate;
import com.yuchengtech.bcrm.calc.service.CalcPriceQueryRmbRateService;
import com.yuchengtech.bob.common.CommonAction;
import com.yuchengtech.crm.exception.BizException;


/**
 * @description: 价格计算器--查询RMBRATE
 * @author denghj
 * @data 2015-08-13
 */
@ParentPackage("json-default")
@Action("/calcPriceQueryRmbRate")
public class CalcPriceQueryRmbRateAction extends CommonAction {
	

//	private static final long serialVersionUID = 1L;

	@Autowired
	@Qualifier("dsOracle")
	private DataSource ds;
	
	private float rmbRate;
	@Autowired
	private CalcPriceQueryRmbRateService service;

	/**
	 * 获取RMBRATE
	 * @return success
	 * @throws IOException
	 */	
	public String getRmbRate(){
		try{
			
			ActionContext ctx = ActionContext.getContext();
			request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
			float limitTY = Float.parseFloat(request.getParameter("limitTY")); // 得到额度期限(年)----float
			String tenor;
			if(limitTY < 1 ||limitTY == 1){
				tenor = "U1Y";
			}else if(limitTY < 5 || limitTY == 5){
				tenor = "A1YU5Y";
			}else{
				tenor = "A5Y";
			}
			String sql = "select r from OcrmORmbRate r where r.tenor = '"+tenor+"' and r.currDat=(select MAX(T.currDat) FROM OcrmORmbRate T)";
			List<OcrmORmbRate> list = service.findByJql(sql, null);
			for(OcrmORmbRate r : list){
				rmbRate = r.getRate();
			}
			if(this.json != null){
				this.json.clear();
			}else{
				this.json = new HashMap<String, Object>();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				map.put("rmbRate", rmbRate);
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
