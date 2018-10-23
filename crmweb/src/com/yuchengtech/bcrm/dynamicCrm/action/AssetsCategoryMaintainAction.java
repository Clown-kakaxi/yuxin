package com.yuchengtech.bcrm.dynamicCrm.action;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.dynamicCrm.model.AssetsCategoryMaintain;
import com.yuchengtech.bcrm.dynamicCrm.service.AssetsCategoryMaintainService;
import com.yuchengtech.bcrm.product.model.ProductCategory;
import com.yuchengtech.bob.common.CommonAction;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 *  Created by Tracy on 16/1/18.
 */

@SuppressWarnings("serial")
@Action("assetsCategoryMaintain")
public class AssetsCategoryMaintainAction extends CommonAction {


    @Autowired
    @Qualifier("dsOracle")
    private DataSource ds;


    @Autowired
    private AssetsCategoryMaintainService service;

    @Autowired
    public void init() {
        model = new AssetsCategoryMaintain ();
        setCommonService (service);
    }


    @Override
    public void prepare() {
        ActionContext ctx = ActionContext.getContext ();
        request = (HttpServletRequest) ctx.get (ServletActionContext.HTTP_REQUEST);

        String assetId = "";
        StringBuffer sb = new StringBuffer ();

        sb.append(" select t.ASSET_ID, t.ASSET_NAME, t.ASSET_PARENT, t.ASSET_LEVEL, t.ASSET_ORDER, ");
        sb.append(" decode(a.ASSET_NAME, '', '银行产品数', null, '银行产品数', a.ASSET_NAME) AS ASSET_NAME_ID ");
        sb.append(" from OCRM_F_CI_CUST_ASSET_CATL t ");
        sb.append(" left join OCRM_F_CI_CUST_ASSET_CATL a on a.ASSET_ID=T.ASSET_PARENT ");
        sb.append(" where 1=1");


        for (String key : this.getJson ().keySet ()) {
            if (null != this.getJson ().get ( key ) && !"".equals ( this.getJson ().get ( key ) )) {
                if ("assetId".equals ( key )) {
                    assetId = this.getJson().get(key).toString();
                    sb.append ( " and t.ASSET_ID ='" + this.getJson ().get ( key ) + "'" );
                }
            }
        }

        sb.append ( " order by t.ASSET_ID" );
        SQL = sb.toString ();
        datasource = ds;

    }


   // 删除资产类别
//    public void delete() {
//        ActionContext actionContext = ActionContext.getContext ();
//        request = (HttpServletRequest) actionContext.get ( ServletActionContext.HTTP_REQUEST );
//        String assetId = request.getParameter ( "ASSET_ID" );
//        service.delete ( assetId );
//
//
//    }


    public void delete(){
        ActionContext ctx = ActionContext.getContext();
        request = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        String indexId = request.getParameter("id");
        service.delete(indexId);
    }




}

