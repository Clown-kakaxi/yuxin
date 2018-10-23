package com.yuchengtech.emp.biappframe.message.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.message.entity.BioneMsgTypeDef;
import com.yuchengtech.emp.biappframe.message.service.BioneMsgTypeDefBS;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * <pre>
 * Title: 消息模块-消息类型定义控制器
 * Description: 消息模块-消息类型定义控制器
 * </pre>
 * 
 * @author liucheng2@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/messagedef")
public class MessageTypeDefController extends BaseController {

	@Autowired
	private BioneMsgTypeDefBS messageDefBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/message/message-def-index";
	}

	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<BioneMsgTypeDef> searchResult = messageDefBS
				.getMessageDefList(pager.getPageFirstIndex(),
						pager.getPagesize(), pager.getSortname(),
						pager.getSortorder(), pager.getSearchCondition());

		Map<String, Object> msgTypeDefMap = Maps.newHashMap();
		msgTypeDefMap.put("Rows", searchResult.getResult());
		msgTypeDefMap.put("Total", searchResult.getTotalCount());
		return msgTypeDefMap;
	}

	/**
	 * 执行添加前页面跳转
	 * 
	 * @return String 用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/bione/message/message-def-edit";
	}

	/**
	 * 表单验证中的后台验证，验证变量标识是否已存在
	 */
	@RequestMapping("/msgTypeNoValid")
	@ResponseBody
	public boolean msgTypeNoValid(String msgTypeNo) {
		BioneMsgTypeDef model = messageDefBS.findUniqueEntityByProperty("msgTypeNo", msgTypeNo);
		if (model != null) 
			return false;
		else
			return true;
	}

	
	/**
	 * 执行修改前的数据加载
	 * 
	 * @return String 用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/bione/message/message-def-edit", "id", id);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BioneMsgTypeDef show(@PathVariable("id") String id) {
		return this.messageDefBS.getEntityById(id);
	}

	/**
	 * 用于保存添加或修改时的对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(BioneMsgTypeDef entity) {
		if (entity != null) {
			if (entity.getMsgTypeId() == null || "".equals(entity.getMsgTypeId())) {
				entity.setMsgTypeId(RandomUtils.uuid2());
			}
			messageDefBS.updateEntity(entity);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		messageDefBS.deleteBatch(ids);
	}
	

}
