package com.yuchengtech.emp.ecif.core.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.core.entity.ColDef;
import com.yuchengtech.emp.ecif.core.entity.TabDef;
import com.yuchengtech.emp.ecif.core.entity.TxColDefPK;
import com.yuchengtech.emp.ecif.core.entity.TxMetadataCheckResult;
import com.yuchengtech.emp.ecif.core.service.ColDefBS;
import com.yuchengtech.emp.ecif.core.service.TabDefBS;
import com.yuchengtech.emp.ecif.core.service.TxMetadataCheckResultBS;

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
@RequestMapping("/ecif/core/tabdef")
public class TabDefController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(TabDefController.class);

	@Autowired
	private TabDefBS tabDefBS;

	@Autowired
	private ColDefBS colDefBS;
	
	@Autowired
	TxMetadataCheckResultBS txMetadataCheckResultBS;	

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/core/tabdef-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<TabDef> searchResult = this.tabDefBS.getTabDefList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		
		List<TabDef> tabDefList = searchResult.getResult();
				
		Map<String, Object> resDefMap = Maps.newHashMap();
//		resDefMap.put("Rows", searchResult.getResult());
		resDefMap.put("Rows", tabDefList);	
		resDefMap.put("Total", searchResult.getTotalCount());
		return resDefMap;
		
	}
	
	@RequestMapping("/batchimport")
	public ModelAndView batchimport() {
		//return "/ecif/core/tabdef-batchimport";
		ModelMap mm = new ModelMap();
		String dbType =  tabDefBS.getDbtype();
		mm.put("dbType", dbType);
		
		return new ModelAndView("/ecif/core/tabdef-batchimport", mm);
		
	}
	
	/**
	 * 获取用于加载schema下的数据
	 */
	@RequestMapping("/listschematables.*")
	@ResponseBody
	public Map<String, Object> listschematables(Pager pager) {
//		SearchResult<TabDef> searchResult = this.tabDefBS.getTabDefList(pager.getPageFirstIndex(),
//			Integer.MAX_VALUE, pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		
//		List<TabDef> tabDefList = searchResult.getResult();
		Map<String,Object> condition = pager.getSearchCondition();
		Map<String, String> fieldValues = (Map)condition.get("fieldValues");
		String tabSchema = fieldValues.get("tabdef.tabSchema");
		String tabName = fieldValues.get("tabdef.tabName");

		List<Map<String, String>> tabDefList = tabDefBS.getSchemaTableList(tabSchema,tabName);
				
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", tabDefList);	
		resDefMap.put("Total", tabDefList.size());
		return resDefMap;
	}
	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping(value ="/listall.*",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object>  listall() {
			
		List<TabDef> list = this.tabDefBS.getAllEntityList("tabDesc",false);
		
		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", list);
		resDefMap.put("Total", list.size());
		return resDefMap;		
	}

	/**
	 * 获取元数据的改变
	 * @return
	 */
	@RequestMapping(value = "/metadatachange")
	public ModelAndView metadatachange() {
		
		return new ModelAndView("/ecif/core/txmetadata-index");
	}	
	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping(value ="/listmetadatachange.*",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object>  listmetadatachange(Pager pager) {
			
		//List<TxMetaDataVO> list = this.tabDefBS.queryMetaDataChangeList();
		
		SearchResult<TxMetadataCheckResult> searchResult = this.txMetadataCheckResultBS.getResultList(pager.getPageFirstIndex(),
				pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		
		List<TxMetadataCheckResult> list = searchResult.getResult();

		Map<String, Object> resDefMap = Maps.newHashMap();
		resDefMap.put("Rows", list);
		resDefMap.put("Total", list.size());
		return resDefMap;		
	}

	/**
	 * 执行同步操作
	 */
	@RequestMapping(value = "/changemetadata/{id}", method = RequestMethod.POST)
	@ResponseBody
	public void changemetadata(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			for(int i=0;i<ids.length;i++){
				
				TxMetadataCheckResult tmcr = this.txMetadataCheckResultBS.getEntityById(new Long(ids[i]));
				
				this.txMetadataCheckResultBS.changeMetadata(tmcr);
			}

		} else {
			TxMetadataCheckResult tmcr = this.txMetadataCheckResultBS.getEntityById(new Long(id));
			
			this.txMetadataCheckResultBS.changeMetadata(tmcr);
		}
	}
	
	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/changemetadata/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroyMetadataChanges(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			for(int i=0;i<ids.length;i++){
				this.txMetadataCheckResultBS.removeEntityById(new Long(ids[i]));
			}

		} else {
			this.txMetadataCheckResultBS.removeEntityById(new Long(id));
		}
	}
	
	
	@RequestMapping(value = "/listalltables")
	
	public ModelAndView listalltables() {
		
		return new ModelAndView("/ecif/transaction/txmsgnodetab-listalltables");
	}	

	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(TabDef model){
		String tabName = model.getTabName();
		String objName = "";
		if(tabName!=null){
			String[] names = tabName.split("_");
			objName = names[0].substring(0,1).toUpperCase() + names[0].substring(1).toLowerCase() ;
		    for(int i=1;i<names.length;i++){
		    	objName += names[i].substring(0,1).toUpperCase() + names[i].substring(1).toLowerCase();
		    }
		}
		model.setObjName(objName);
		this.tabDefBS.updateEntity(model);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TabDef show(@PathVariable("id") Long id) {
		TabDef model = this.tabDefBS.getEntityById(id);
		return model;
	}

	/**
	 * 执行修改前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/ecif/core/tabdef-edit", "id", id);
	}

	/**
	 * 执行数据表表的批量导入
	 * 
	 * @return
	 * 	
	 */
	@RequestMapping(value = "/batchimportexec", method = RequestMethod.POST)
	@ResponseBody	
	public void batchimport(String id,String tabSchema) {
		
		this.tabDefBS.batchimportTabs(id,tabSchema);
	}	
	
	/**
	 * 执行数据列的同步
	 * 
	 * @return
	 * 	
	 */
	@RequestMapping(value = "/sync/{id}", method = RequestMethod.GET)
	@ResponseBody	
	public void sync(@PathVariable("id") String id) {
		
		String[] ids = id.split(",");
		if (ids.length > 1) {
			for(int i=0;i<ids.length;i++){
				this.tabDefBS.syncTabColumns(ids[i]);			
			}
		} else {
			  this.tabDefBS.syncTabColumns(id);
		}
	}
	
	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/ecif/core/tabdef-edit";
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			for(int i=0;i<ids.length;i++){
				this.tabDefBS.removeEntityById(new Long(ids[i]));
				this.colDefBS.delTabColumns(ids[i]);				
			}

		} else {
			this.tabDefBS.removeEntityById(new Long(id));
			this.colDefBS.removeEntityByProperty("tabId", id);
		}
	}
	
	/**
	 * 跳转 添加服务授权页面
	 * 
	 * @return
	 */
	@RequestMapping("/authservice")
	public ModelAndView authservice(String tabId) {
		return new ModelAndView("/ecif/core/coldef-index", "tabId",
				tabId);
	}
	
	/**
	 * 表单验证中的后台验证，验证模块标识是否已存在
	 */
	@RequestMapping(value = "resDefNoValid")
	@ResponseBody
	public boolean resDefNoValid(Long tabId) {
		TabDef model = tabDefBS.findUniqueEntityByProperty("tabId", tabId);
		if (model != null)
			return false;
		else
			return true;
	}

	/**
	 * 获取combobox
	 */
	@ResponseBody
	@RequestMapping("getComBoBox.*")
	public List<Map<String,String>> getComBoBox() {
		List<Map<String,String>> list = this.tabDefBS.getComBoBox();
		return list;
	}
	
	public Map<String, String> convertList2Map(List<Map<String,String>> list){
		Map<String, String> newmap = new HashMap<String, String>();
		for(Map<String,String> map:list ){
			String id =    (String)map.get("id");
			String text =(String)map.get("text");
			
			newmap.put(id, text);
		}
		
		return newmap;
	}
	
}
