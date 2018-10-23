package com.yuchengtech.emp.biappframe.mtool.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.collect.Lists;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.mtool.entity.BioneDriverInfo;
import com.yuchengtech.emp.biappframe.mtool.entity.BioneDsInfo;
import com.yuchengtech.emp.biappframe.mtool.service.DataSourceBS;
import com.yuchengtech.emp.biappframe.mtool.util.MtoolUtils;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * 
 * 
 * <pre>
 * Title:
 * Description:
 * </pre>
 * 
 * @author gaofeng gaofeng5@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：高峰  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/mtool/datasource")
public class DataSourceController extends BaseController {
	protected static Logger log = LoggerFactory
			.getLogger(DataSourceController.class);
	@Autowired
	private DataSourceBS dataSourceBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/mtool/data-source-index";
	}

	// 数据源信息
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<BioneDsInfo> bioneDsInfo = dataSourceBS.getList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition());
		if (bioneDsInfo.getResult() != null) {
			for (int j = 0; j < bioneDsInfo.getResult().size(); j++) {
				String drId = bioneDsInfo.getResult().get(j).getDriverId();
				BioneDriverInfo driverInfo = this.dataSourceBS.getEntityById(
						BioneDriverInfo.class, drId);
				bioneDsInfo.getResult().get(j)
						.setDriverId(driverInfo.getDriverType());
				String passWord = bioneDsInfo.getResult().get(j).getConnPwd();
				String pwd = "";
				for (int i = 0; i < passWord.length(); i++) {
					pwd = pwd + "*";
				}
				bioneDsInfo.getResult().get(j).setConnPwd(pwd);
			}
		}
		Map<String, Object> temMap = new HashMap<String, Object>();
		temMap.put("Rows", bioneDsInfo.getResult());
		temMap.put("Total", bioneDsInfo.getTotalCount());
		return temMap;
	}

	// 跳转新建数据源页面
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/bione/mtool/data-source-edit";
	}

	// 跳转修改数据源页面
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/bione/mtool/data-source-edit", "id", id);
	}

	// 根据Id获取数据源信息
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BioneDsInfo show(@PathVariable("id") String id) {
		return dataSourceBS.getEntityById(id);
	}

	// 保存数据源信息
	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneDsInfo model) {
		String sys = BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo();
		model.setLogicSysNo(sys);
		if (model.getDsId() == null || model.getDsId().equals("")) {
			model.setDsId(RandomUtils.uuid2());
		}
		dataSourceBS.updateEntity(model);
	}

	// 删除数据源
	@RequestMapping("/removeAll")
	public void removeAll(String dsId) {
		dataSourceBS.removeDs(dsId);
		// this.bioneDsBS.removeEntityById(dsId);
	}

	// 重名验证
	@RequestMapping("/dsNameValid")
	@ResponseBody
	public boolean dsNameValid(String dsId, String dsName) {
		List<BioneDsInfo> ds = Lists.newArrayList();
		ds = dataSourceBS.checkedDsName(dsId, dsName);
		if (ds != null && ds.size() > 0)
			return false;
		else
			return true;
	}

	// 测试
	@RequestMapping("/getTest")
	@ResponseBody
	public boolean getTest(String driver, String connUrl, String connUser,
			String connPwd) {
		boolean flag = MtoolUtils.testConnection(driver, connUrl, connUser,
				connPwd);
		return flag;
	}

	// 获取驱动信息
	@RequestMapping("/getDriverData")
	@ResponseBody
	public Map<String, Object> getDriverData() {
		Map<String, Object> temMap = new HashMap<String, Object>();
		temMap.put("data", dataSourceBS.getDriverData());
		return temMap;
	}

	// 获取URL
	@RequestMapping("/getUrlData")
	@ResponseBody
	public Map<String, Object> getUrlData(String driverId) {
		Map<String, Object> temMap = new HashMap<String, Object>();
		temMap.put("data", dataSourceBS.getURLData(driverId));
		return temMap;
	}

}
