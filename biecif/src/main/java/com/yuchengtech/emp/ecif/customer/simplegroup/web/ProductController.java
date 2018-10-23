package com.yuchengtech.emp.ecif.customer.simplegroup.web;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.base.util.ResultUtil;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.ProductBS;
import com.yuchengtech.emp.ecif.customer.simplegroup.web.vo.CustProductVO;


/**
 * 
 * 
 * <pre>
 * Title:产品信息的Controller端
 * Description: 处理产品信息的CRUD操作
 * </pre>
 * 
 * @author zhengyukun zhengyk3@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/ecif/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductBS productBS;
	
	protected static Logger log = LoggerFactory
			.getLogger(ProductController.class);
	
	@Autowired
	private ResultUtil resultUtil;

	/**
	 * 跳转 修改 页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("custId") String custId,
			@RequestParam("URL") String URL) {
		return new ModelAndView(URL, "custId", custId);
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager,@RequestParam("custId") long custId) {
		Map<String, Object> taskMap = Maps.newHashMap();
		
		SearchResult<CustProductVO> searchResult = productBS.getProductList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), custId);
		if(searchResult != null && searchResult.getResult() != null){
			taskMap.put("Rows", searchResult.getResult());
			taskMap.put("Total", searchResult.getTotalCount());
			
			return taskMap;
		} else {
			return null;
		}
	}
}