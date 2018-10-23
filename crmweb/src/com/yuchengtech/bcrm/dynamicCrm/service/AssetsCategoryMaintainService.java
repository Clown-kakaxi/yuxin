package com.yuchengtech.bcrm.dynamicCrm.service;


import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import org.springframework.stereotype.Service;
import com.yuchengtech.bcrm.dynamicCrm.model.AssetsCategoryMaintain;


/**
 * Created by Tracy on 16/1/18.
 */


@Service
public class AssetsCategoryMaintainService extends CommonService {

    public AssetsCategoryMaintainService(){
        JPABaseDAO<AssetsCategoryMaintain, Long> baseDAO = new JPABaseDAO<AssetsCategoryMaintain, Long>(AssetsCategoryMaintain.class);
        super.setBaseDAO ( baseDAO );
    }



    //新增/修改资产类别
    @SuppressWarnings("unused")
    public Object save(Object obj){

        AssetsCategoryMaintain acm = (AssetsCategoryMaintain) obj;
        if (acm.getAssetParent () != null && acm.getAssetParent ().equals("银行产品树")){
            acm.setAssetParent ("0");
        }

        return super.save (acm);
    }




    //删除资产类别
    @SuppressWarnings("unchecked")
    public void delete(String id){
        baseDAO.removeById(id);
    }
}











