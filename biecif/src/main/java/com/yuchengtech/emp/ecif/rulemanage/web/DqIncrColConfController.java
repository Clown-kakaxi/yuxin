package com.yuchengtech.emp.ecif.rulemanage.web;


import java.util.ArrayList;
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

import com.yuchengtech.emp.ecif.rulemanage.entity.DqIncrColConfPK;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.ecif.base.util.ResultUtil;
import com.yuchengtech.emp.ecif.core.entity.TxStdCode;
import com.yuchengtech.emp.ecif.core.entity.TxStdCodePK;
import com.yuchengtech.emp.ecif.customer.entity.event.EventInfo;
import com.yuchengtech.emp.ecif.rulemanage.entity.DqIncrColConf;
import com.yuchengtech.emp.ecif.rulemanage.entity.DqIncrColConfVO;
import com.yuchengtech.emp.ecif.rulemanage.service.DqIncrColConfBS;

/**
 * <pre>
 * Description: 通用覆盖规则管理Controller 
 * </pre>	
 * @author pengsenlin pengsl@yuchengtech.com
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：彭森林		  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("/ecif/rulemanage/dqincrcolconf")
public class DqIncrColConfController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(DqIncrColConfController.class);

	@Autowired
	private DqIncrColConfBS dqIncrColConfBS;
	
	@Autowired
	private ResultUtil resultUtil;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/ecif/rulemanage/dqincrcolconf-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Map<String, Object> list(Pager pager, String tid) {
		Map<String, Object> userMap = Maps.newHashMap();
		SearchResult<DqIncrColConf> searchResult = dqIncrColConfBS.getDqIncrColConfList(
				pager.getPageFirstIndex(), pager.getPagesize(),
				pager.getSortname(), pager.getSortorder(),
				pager.getSearchCondition(), tid);
		List<EventInfo> list = null;
		try {
			
			List voList = new ArrayList();
			for(int i=0;i<searchResult.getResult().size() ;i++){
				DqIncrColConf o = searchResult.getResult().get(i);
				DqIncrColConfVO vo = new DqIncrColConfVO();
				vo.setTid(o.getId().getTid());
				vo.setDstCol(o.getId().getDstCol());
				vo.setSrcCol(o.getSrcCol());
				vo.setInsOnl(o.getInsOnl());
				vo.setSys(o.getSys());
				
				voList.add(vo);
			}			
			
			list = resultUtil.jpaListObjectsDictTran(voList, DqIncrColConfVO.class);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		userMap.put("Rows", list);
		userMap.put("Total", searchResult.getTotalCount());
		return userMap;
	}
	
	/**
	 * 执行新增前页面跳转 
	 * @return String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView editNew(String tid) {
		ModelMap mm = new ModelMap();
		mm.put("tid", tid);
		
		return new ModelAndView("/ecif/rulemanage/dqincrcolconf-edit",mm);
	}
	
	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(DqIncrColConfVO vo) {
		
		DqIncrColConfPK pk = new DqIncrColConfPK();
		pk.setTid(vo.getTid());
		pk.setDstCol(vo.getDstCol());	
		
		DqIncrColConf model  = new DqIncrColConf();
		model.setId(pk);
		model.setSrcCol(vo.getSrcCol());
		model.setInsOnl(vo.getInsOnl());
		model.setSys(vo.getSys());
		
		this.dqIncrColConfBS.updateEntity(model);
	}
	
	/**
	 * 跳转到修改页面
	 * 
	 * @return String	用于匹配结果页面
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(String tid,String dstCol) {
		ModelMap mm = new ModelMap();
		mm.put("tid", tid);
		mm.put("dstCol", dstCol);
		
		return new ModelAndView("/ecif/rulemanage/dqincrcolconf-edit",mm);
	}
	
	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	@ResponseBody
	public DqIncrColConfVO show(String tid,String dstCol) {
		
		DqIncrColConfPK pk = new DqIncrColConfPK();
		pk.setTid(tid.trim());
		pk.setDstCol(dstCol.trim());	
		
		DqIncrColConf o = this.dqIncrColConfBS.getEntityById(pk);
		
		DqIncrColConfVO vo = new DqIncrColConfVO();
		vo.setTid(o.getId().getTid());
		vo.setDstCol(o.getId().getDstCol());
		vo.setSrcCol(o.getSrcCol());
		vo.setInsOnl(o.getInsOnl());
		vo.setSys(o.getSys());
		
		return vo;
	}
	
	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			DqIncrColConfPK pk = new DqIncrColConfPK();
			pk.setTid(ids[0].trim());
			pk.setDstCol(ids[1].trim());
			
			this.dqIncrColConfBS.removeEntityById(pk);
			
		}
	}
}
