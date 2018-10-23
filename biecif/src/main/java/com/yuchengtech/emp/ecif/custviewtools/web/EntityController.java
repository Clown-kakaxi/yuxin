package com.yuchengtech.emp.ecif.custviewtools.web;


import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.custviewtools.entity.EntityVO;
import com.yuchengtech.emp.ecif.custviewtools.service.CustomerViewGen;
import com.yuchengtech.emp.ecif.custviewtools.service.MakeConstants;
import com.yuchengtech.emp.ecif.custviewtools.service.MakeUtils;

/**
 * <pre>
 * Title:CRUD操作演示
 * Description: 完成表的CRUD操作 
 * </pre>	
 * @author shangjf  shangjf@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：尚吉峰		  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/custviewtools")
public class EntityController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(EntityController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/custviewtools/entity-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		
		List<EntityVO> entityList = MakeUtils.getEntityList(  MakeUtils.class.getResource("/").getPath()+ MakeConstants.PER_XMLFILE) ;
				
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", entityList);	
		resDefMap.put("Total", entityList.size());
		return resDefMap;
	}
	
	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	@ResponseBody
	public void genfiles(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		String xml = MakeUtils.readFile(MakeUtils.class.getResource("/").getPath() + MakeConstants.PER_XMLFILE);

		for(int i=0;i<ids.length;i++){
			CustomerViewGen.makeOneEntityFiles(xml,ids[i]);
		}
		
	}

}
