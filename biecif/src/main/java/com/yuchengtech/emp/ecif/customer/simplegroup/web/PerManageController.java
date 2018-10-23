package com.yuchengtech.emp.ecif.customer.simplegroup.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.yuchengtech.emp.ecif.customer.entity.customermanage.Belongbranch;
import com.yuchengtech.emp.ecif.customer.entity.customermanage.BelongbranchVO;
import com.yuchengtech.emp.ecif.customer.entity.customermanage.Belonglinedept;
import com.yuchengtech.emp.ecif.customer.entity.customermanage.Belongmanager;
import com.yuchengtech.emp.ecif.customer.entity.customermanage.BelongmanagerVO;
import com.yuchengtech.emp.ecif.customer.entity.customermanage.Lifecycleinfo;
import com.yuchengtech.emp.ecif.customer.simplegroup.service.PerManageBS;


/**
 * 
 * 
 * <pre>
 * Title:个人类客户管理信息的Controller端
 * Description: 处理个人类客户管理信息的CRUD操作
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
@RequestMapping("/ecif/permanage/old")
public class PerManageController extends BaseController {

	@Autowired
	private PerManageBS PerManageBS;
	
	protected static Logger log = LoggerFactory
			.getLogger(PerManageController.class);
	
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
	
	//grid形式展示
	@RequestMapping(value = "/belongbranch/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showBelongbranch(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Belongbranch> searchResult = PerManageBS.getBelongbranchList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<BelongbranchVO> listVO = new ArrayList<BelongbranchVO>();
			try {
				List<Belongbranch> list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Belongbranch.class);
				if(list != null && list.size() > 0){
					for(Belongbranch bb : list){
						BelongbranchVO bbvo = new BelongbranchVO();
						bbvo.setBelongBranchId(bb.getBelongBranchId());
						bbvo.setBelongType(bb.getBelongType());
						bbvo.setBrccode1(bb.getBrccode1());
						bbvo.setBrccode2(bb.getBrccode2());
						bbvo.setBrcname1(resultUtil.getOrgName(bb.getBrccode1()));
						bbvo.setBrcname2(resultUtil.getOrgName(bb.getBrccode2()));
						listVO.add(bbvo);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", listVO);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	
	//显示grid数据
	@RequestMapping(value = "/belongmanager/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showBelongmanager(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Belongmanager> searchResult = PerManageBS.getBelongmanagerList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<BelongmanagerVO> listVO = new ArrayList<BelongmanagerVO>();
			try {
				List<Belongmanager> list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Belongmanager.class);
				if(list != null && list.size() > 0){
					for(Belongmanager bm : list){
						BelongmanagerVO bmvo = new BelongmanagerVO();
						bmvo.setAdminManagerNo(bm.getAdminManagerNo());
						bmvo.setAdminManagerName(resultUtil.getEmpName(bm.getAdminManagerNo()));
						bmvo.setBelongManagerId(bm.getBelongManagerId());
						bmvo.setBelongType(bm.getBelongType());
						bmvo.setCustId(bm.getCustId());
						bmvo.setEmpcode1(bm.getEmpcode1());
						bmvo.setEmpcode2(bm.getEmpcode2());
						bmvo.setEmpname1(resultUtil.getEmpName(bm.getEmpcode1()));
						bmvo.setEmpname2(resultUtil.getEmpName(bm.getEmpcode2()));
						bmvo.setMainType(bm.getMainType());
						listVO.add(bmvo);
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", listVO);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	//显示grid数据
	@RequestMapping(value = "/belonglinedebt/list.*", method = RequestMethod.POST)
	@ResponseBody
		public Map<String, Object> showBelonglinedept(Pager pager,@RequestParam("custId") long custId) {
			Map<String, Object> userMap = Maps.newHashMap();
			SearchResult<Belonglinedept> searchResult = PerManageBS.getBelonglinedeptList(
					pager.getPageFirstIndex(), pager.getPagesize(),
					pager.getSortname(), pager.getSortorder(),
					pager.getSearchCondition(),custId);
			List<Belonglinedept> list = null;
			try {
				list = resultUtil.jpaListObjectsDictTran(searchResult.getResult(), Belonglinedept.class);
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			userMap.put("Rows", list);
			userMap.put("Total", searchResult.getTotalCount());
			return userMap;
		}
	
	@RequestMapping(value = "/lifecycleinfo/{custId}", method = RequestMethod.GET)
	@ResponseBody
	public Lifecycleinfo showPersonProfile(@PathVariable("custId") long custId) {
		Lifecycleinfo model = PerManageBS.getEntityById(Lifecycleinfo.class, custId);
		try {
			model = resultUtil.jpaBeanDictTran(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}
