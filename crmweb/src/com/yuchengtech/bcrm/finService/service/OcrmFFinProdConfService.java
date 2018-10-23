package com.yuchengtech.bcrm.finService.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.finService.model.OcrmFFinProdConf;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * @describtion: 
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014年7月23日 上午10:01:34
 */
@Service
public class OcrmFFinProdConfService extends CommonService {
	public OcrmFFinProdConfService(){
		JPABaseDAO<OcrmFFinProdConf, Long> baseDAO = new JPABaseDAO<OcrmFFinProdConf, Long>(OcrmFFinProdConf.class);
		super.setBaseDAO(baseDAO);
	}
	
	public Object save(Object obj){
		ActionContext ctx = ActionContext.getContext();
		HttpServletRequest request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
		String ids = request.getParameter("ids");
		String vals = request.getParameter("vals");
		OcrmFFinProdConf target = (OcrmFFinProdConf)obj;
		//如果只是变更规模
		if(ids != null && !"".equals(ids)){
			ids = ids.substring(1);
			vals = vals.substring(1);
			String[] idsArr = ids.split(",");
			String[] valsArr = vals.split(",");
			for(int i=0;i<idsArr.length;i++){
				this.em.createNativeQuery("update OCRM_F_FIN_PROD_CONF c set c.conf_scale = '"+valsArr[i]+"' where c.conf_id = '"+idsArr[i]+"'").executeUpdate();
			}
			this.em.createNativeQuery("UPDATE OCRM_F_FIN_TARGET T SET T.TARGET_SCALE = (SELECT SUM(T1.CONF_SCALE) FROM OCRM_F_FIN_PROD_CONF T1 WHERE T1.TARGET_ID = T.TARGET_ID) where t.target_id = '"+target.getTargetId()+"'").executeUpdate();
			target.setConfId(-1l);
			return target;
		}
		String prodId = target.getProdId();
		String prodRisk = target.getProdRisk();
		String[] prodIdArr = prodId.split(",");
		String[] prodRiskArr = prodRisk.split(",");
		for(int i=0;i<prodIdArr.length;i++){
			OcrmFFinProdConf prod = new OcrmFFinProdConf();
			prod.setConfScale(target.getConfScale() != null ? target.getConfScale():BigDecimal.valueOf(0));
			prod.setDemandId(target.getDemandId());
			prod.setTargetId(target.getTargetId());
			prod.setProdId(prodIdArr[i]);
			if(prodRiskArr.length > i){
				prod.setProdRisk(prodRiskArr[i]);
			}
			obj = super.save(prod);
		}
		this.em.createNativeQuery("UPDATE OCRM_F_FIN_TARGET T SET T.TARGET_SCALE = (SELECT SUM(T1.CONF_SCALE) FROM OCRM_F_FIN_PROD_CONF T1 WHERE T1.TARGET_ID = T.TARGET_ID) where t.target_id = '"+target.getTargetId()+"'").executeUpdate();
		return obj;
	}
	
	/**
	 * 删除产品组合配置
	 * 重新计算目标规模
	 */
	public void batchRemove(String ids) {
        Map<String, Object> values = new HashMap<String, Object>();
        List list = this.em.createQuery("select t from OcrmFFinProdConf t where t.confId IN("+ ids +")").getResultList();
        super.batchUpdateByName("DELETE FROM OcrmFFinProdConf t WHERE t.confId IN("+ ids +")", values);
        if(list != null && list.size() > 0){
        	OcrmFFinProdConf conf= (OcrmFFinProdConf)list.get(0);
        	this.em.createNativeQuery("UPDATE OCRM_F_FIN_TARGET T SET T.TARGET_SCALE = (SELECT NVL(SUM(T1.CONF_SCALE),0) FROM OCRM_F_FIN_PROD_CONF T1 WHERE T1.TARGET_ID = T.TARGET_ID) where t.target_id = '"+conf.getTargetId()+"'").executeUpdate();
        }
    }
}
