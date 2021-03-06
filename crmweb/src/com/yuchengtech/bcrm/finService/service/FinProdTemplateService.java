package com.yuchengtech.bcrm.finService.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.finService.model.OcrmFFinTemplate;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;

/**
 * @describtion: autogenerated by lhqheli's Tools
 *
 * @author : lhqheli (email: lhqheli@gmail.com)
 * @date : 2014-07-24 13:45:29
 */
@Service
public class FinProdTemplateService extends CommonService {
    public FinProdTemplateService(){
        JPABaseDAO<OcrmFFinTemplate, Long> baseDao = new JPABaseDAO<OcrmFFinTemplate, Long>(OcrmFFinTemplate.class);
        super.setBaseDAO(baseDao);
    }
    
    /**
     * create or update finProdTemplate
     */
    public Object save(Object obj) {
        OcrmFFinTemplate finProdTemplate = (OcrmFFinTemplate)obj;
        ActionContext ctx = ActionContext.getContext();
        HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String riskType = request.getParameter("riskType");
        List<OcrmFFinTemplate>  list = this.em.createQuery("select t from OcrmFFinTemplate t where t.riskType = '"+riskType+"' order by t.prodType").getResultList();
        for(int i=1;i<=list.size();i++){
        	OcrmFFinTemplate tmpelate = list.get(i-1);
        	tmpelate.setProdRate(BigDecimal.valueOf(Double.valueOf(String.valueOf(request.getParameter("prodType"+i)))));
        	super.save(tmpelate);
        }
        
        finProdTemplate.setId(-1l);
        return finProdTemplate;
    }
    
    /**
     * bacth delete finProdTemplate
     */
    public void batchRemove(String ids) {
        Map<String, Object> values = new HashMap<String, Object>();
        super.batchUpdateByName("DELETE FROM OcrmFFinTemplate t WHERE t.id IN("+ ids +")", values);
    }
}
