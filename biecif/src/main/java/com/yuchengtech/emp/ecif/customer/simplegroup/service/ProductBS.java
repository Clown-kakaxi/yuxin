package com.yuchengtech.emp.ecif.customer.simplegroup.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuchengtech.emp.biappframe.base.service.BaseBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.ecif.base.util.DictTranslationUtil;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.CustProductVO;

@Service
@Transactional(readOnly = true)
public class ProductBS extends BaseBS<Object> {
	@Autowired
	private DictTranslationUtil dictTranslationUtil;

	/**
	 * @param firstResult
	 *            分页的开始游标
	 * @param pageSize
	 *            游标大小
	 * @param orderBy
	 *            排序字段
	 * @param orderType
	 *            排序方式（升序/降序）
	 * @param conditionMap
	 *            参数列表（其他的参数，键值对形式）
	 * @return 任务实例集合
	 */
	@SuppressWarnings("unchecked")
	public SearchResult<CustProductVO> getProductList(int firstResult,
			int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap, Long custId) {

		StringBuffer sql = new StringBuffer("");

		sql.append(" select ");
		sql.append(" product.PROD_CODE,");
		sql.append(" product.PROD_NAME,");
		sql.append(" product.PROD_TYPE,");
		sql.append(" product.PROD_SUMMARY,");
		sql.append(" proBasicInfo.BRAND_NAME,");
		sql.append(" proBasicInfo.BUSI_CHAR,");
		sql.append(" proBasicInfo.PROD_CLASS,");
		sql.append(" proBasicInfo.PROD_FORM,");
		sql.append(" proBasicInfo.PROD_STAT,");
		sql.append(" proBasicInfo.PROD_PATENT,");
		sql.append(" proBasicInfo.START_DATE,");
		sql.append(" proBasicInfo.END_DATE,");
		sql.append(" proBasicInfo.OWN_SALE_FLAG");
		sql.append(" from ");
		sql.append(" M_CI_CUSTOMER customer");
		sql.append(" inner join M_CI_CUSTPRODUCTREL custProRel on customer.CUST_ID = custProRel.CUST_ID");
		sql.append(" inner join M_CI_PRODUCT product on custProRel.PROD_ID = product.PROD_ID");
		sql.append(" inner join M_CI_PRODUCTBASICINFO proBasicInfo on proBasicInfo.PROD_ID = product.PROD_ID");

		if (!"".equals(custId)) {
			sql.append(" and customer.CUST_ID = " + custId + "");
		}

		Map<String, ?> values = (Map<String, ?>) conditionMap.get("params");

		List<CustProductVO> temps = new ArrayList<CustProductVO>();
		SearchResult<Object[]> temp = this.baseDAO
				.findPageWithNameParamByNativeSQL(firstResult, pageSize,
						sql.toString(), values);

		List<Object[]> objList = temp.getResult();

		for (Object[] obj : objList) {
			CustProductVO custProduct = new CustProductVO();
			

			custProduct.setProdCode(obj[0] != null ? obj[0].toString() : ""); // 产品代码
			custProduct.setProdName(obj[1] != null ? obj[1].toString() : ""); // 产品名称
			custProduct.setProdType(obj[2] != null ? obj[2].toString() : ""); // 产品类型
			custProduct.setProdSummary(obj[3] != null ? obj[3].toString() : ""); // 产品简介
			custProduct.setBrandName(obj[4] != null ? obj[4].toString() : ""); // 品牌名称
			custProduct.setBusiChar(obj[5] != null ? obj[5].toString() : ""); // 业务性质
			custProduct.setProdClass(obj[6] != null ? obj[6].toString() : ""); // 产品分类
			custProduct.setProdForm(obj[7] != null ? obj[7].toString() : ""); // 产品形态
			custProduct.setProdStat(obj[8] != null ? obj[8].toString() : ""); // 产品状态
			custProduct.setProdPatent(obj[9] != null ? obj[9].toString() : ""); // 产品专利
			custProduct.setStartDate(obj[10] != null ? obj[10].toString() : ""); // 上线日期
			custProduct.setEndDate(obj[11] != null ? obj[11].toString() : ""); // 下线日期
			custProduct.setOwnSaleFlag(obj[12] != null ? obj[12].toString() : ""); // 自主营销标识
			temps.add(custProduct);
		}

		SearchResult<CustProductVO> taskVOResult = new SearchResult<CustProductVO>();
		temps = this.dictTranslationUtil.setDictCustProduct(temps);
		if(temps != null){
			taskVOResult.setResult(temps);
			taskVOResult.setTotalCount(temps.size());
			return taskVOResult;
		}else{
			return null;
		}
	}

}