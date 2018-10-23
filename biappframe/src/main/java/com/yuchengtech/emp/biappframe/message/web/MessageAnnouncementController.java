package com.yuchengtech.emp.biappframe.message.web;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.yuchengtech.emp.biappframe.base.common.GlobalConstants;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.message.MessageConstants;
import com.yuchengtech.emp.biappframe.message.entity.BioneMsgAnnouncementInfo;
import com.yuchengtech.emp.biappframe.message.sender.push.Pusher;
import com.yuchengtech.emp.biappframe.message.service.BioneMsgAnnouncementBS;
import com.yuchengtech.emp.biappframe.message.service.BioneMsgAttachmentBS;
import com.yuchengtech.emp.biappframe.message.service.BioneMsgAuthRelBS;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.security.BiOneUser;
import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.entity.page.Pager;
import com.yuchengtech.emp.bione.util.RandomUtils;

/**
 * <pre>
 * Title: 消息模块-公告控制器
 * Description: 消息模块-公告控制器
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
@RequestMapping("/bione/msg/announcement")
public class MessageAnnouncementController extends BaseController {

	@Autowired
	private BioneMsgAnnouncementBS msgAnnoBS;


	@Autowired
	private BioneMsgAttachmentBS msgAttachBS;
	
	
	@Autowired
	private BioneMsgAuthRelBS msgAuthRelBS;
	
	
	/** 消息推送器 */
	@Autowired
	private Pusher push ;
	
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/message/msg-announcement-index";
	}

	@RequestMapping(value = "/viewIdx", method = RequestMethod.GET)
	public String viewIdx() {
		return "/bione/message/msg-announcement-viewIdx";
	}
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
		SearchResult<BioneMsgAnnouncementInfo> searchResult = msgAnnoBS
				.getMsgListWithPage(userObj.getCurrentLogicSysNo(), userObj.getUserId(),
						pager.getPageFirstIndex(), pager.getPagesize(),
						pager.getSortname(), pager.getSortorder(),
						pager.getSearchCondition());
		Map<String, Object> msgAnnoMap = Maps.newHashMap();
		msgAnnoMap.put("Rows", searchResult.getResult());
		msgAnnoMap.put("Total", searchResult.getTotalCount());
		return msgAnnoMap;
	}
	
	
	/**
	 * 获取用于加载grid的数据
	 */
	@RequestMapping("/listView.*")
	@ResponseBody
	public Map<String, Object> listView(Pager pager) {
		BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
		SearchResult<BioneMsgAnnouncementInfo> searchResult = msgAnnoBS
				.getMsgListWithPageForView(userObj.getCurrentLogicSysNo(), userObj.getUserId(),
						pager.getPageFirstIndex(), pager.getPagesize(),
						pager.getSortname(), pager.getSortorder(),
						pager.getSearchCondition());
		Map<String, Object> msgAnnoMap = Maps.newHashMap();
		msgAnnoMap.put("Rows", searchResult.getResult());
		msgAnnoMap.put("Total", searchResult.getTotalCount());
		return msgAnnoMap;
	}

	

	/**
	 * 跳转新增页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String editNew() {
		return "/bione/message/msg-announcement-edit";
	}

	/**
	 * 跳转编辑页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") String id) {
		return new ModelAndView("/bione/message/msg-announcement-edit", "id",
				id);
	}

	/**
	 * 跳转浏览页面
	 */
	@RequestMapping(value = "/{id}/view", method = RequestMethod.GET)
	public ModelAndView view(@PathVariable("id") String id) {
		return new ModelAndView("/bione/message/msg-announcement-view", "id",
				id);
	}
	
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public BioneMsgAnnouncementInfo show(@PathVariable("id") String id) {
		return this.msgAnnoBS.getEntityById(id);
	}
	
	/**
	 * 用于保存添加或修改时的对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String create(BioneMsgAnnouncementInfo entity) {
		BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if (entity != null) {
			if (entity.getMsgId() == null || "".equals(entity.getMsgId())) {
				entity.setMsgId(RandomUtils.uuid2());
				entity.setCreateTime(now);
				entity.setCreateUser(userObj.getUserName());
				entity.setLogicSysNo(userObj.getCurrentLogicSysNo());
				entity.setMsgSrc(userObj.getUserName());
			}
			entity.setMsgTypeNo("ANNOUNCEMENT");
			entity.setMsgSts(MessageConstants.MESSAGE_ANNOUNCEMENT_STATUS_DRAFT);
			entity.setLastUpdateTime(now);
			entity.setLastUpdateUser(userObj.getUserName());
			msgAnnoBS.updateEntity(entity);
			//
			return entity.getMsgId();
		}
		return null;
	}

	/**
	 * 批量删除公告
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		msgAnnoBS.deleteBatch(ids);
	}
	
	
	
	
	/**
	 * 批量发布公告
	 */
	@RequestMapping(value = "/{id}/publish", method = RequestMethod.POST)
	@ResponseBody
	public void publish(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
		if(ids!=null && ids.length>0) {
			for(String cid : ids) {
				BioneMsgAnnouncementInfo entity = msgAnnoBS.getEntityById(cid);
				entity.setLastUpdateTime(now);
				entity.setLastUpdateUser(userObj.getUserName());
				entity.setMsgSts(MessageConstants.MESSAGE_ANNOUNCEMENT_STATUS_PUBLISHED);
				msgAnnoBS.updateEntity(entity);
			}
		}
		//=====================================================
		// 消息推送
		//=====================================================
		Map<String, List<String>> pushList = this.buildPushCollection(ids);
		push.push(pushList, MessageConstants.PUSH_JOIN_LISTEN_ID, MessageConstants.PUSH_MESSAGE_KEY);
	}
	
	
	private Map<String, List<String>> buildPushCollection(String[] ids) {
		BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
		String logicSysNo = userObj.getCurrentLogicSysNo();
		String msgTypeNo = "announce";
		// 
		Map<String, List<String>> pushList = new HashMap<String, List<String>>();
		if(ids!=null && ids.length>0) {
			for(String cid : ids) {
				BioneMsgAnnouncementInfo entity = msgAnnoBS.getEntityById(cid);
				String message = entity.getMsgTitle();
				// 计算要发送到的人
				List<String> users = this.msgAuthRelBS.getReceiverByMsgId(logicSysNo, cid, msgTypeNo);
				users.add(userObj.getUserId());
				// 
				pushList.put(message, users);
			}
		}
		return pushList;
	}
	
	
	
	/**
	 * 批量取消发布公告
	 */
	@RequestMapping(value = "/{id}/unPublish", method = RequestMethod.POST)
	@ResponseBody
	public void unPublish(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		Timestamp now = new Timestamp(System.currentTimeMillis());
		BiOneUser userObj = BiOneSecurityUtils.getCurrentUserInfo();
		if(ids!=null && ids.length>0) {
			for(String cid : ids) {
				BioneMsgAnnouncementInfo entity = msgAnnoBS.getEntityById(cid);
				entity.setLastUpdateTime(now);
				entity.setLastUpdateUser(userObj.getUserName());
				entity.setMsgSts(MessageConstants.MESSAGE_ANNOUNCEMENT_STATUS_DRAFT);
				msgAnnoBS.updateEntity(entity);
			}
		}
	}
	
	
	
	/** @TODO Attachment */

	/**
	 * 跳转到附件上传的页面(这个页面路径是【公告】专属的)
	 * @param msgId
	 * @return
	 */
	@RequestMapping("/{id}/newAttach")
	public ModelAndView newAttach(@PathVariable("id") String msgId) {
		return new ModelAndView("/bione/message/msg-announcement-newattach", "id", msgId);
	}
	
	
	/** @TODO 标签跳转 */
	
	
	/** 标签BaseInfo跳转  */
	@RequestMapping(value="/msg_announcement_edit_info")
	public String gotoBaseinfo() {
		return "/bione/message/msg-announcement-edit-info";
	}
	@RequestMapping(value="/msg_announcement_edit_info/{id}")
	public ModelAndView gotoBaseinfoWithId(@PathVariable("id") String msgId) {
		return new ModelAndView("/bione/message/msg-announcement-edit-info", "id", msgId);
	}
	
	/** 标签Auth跳转  */
	@RequestMapping(value="/msg_announcement_edit_auth/{id}")
	public ModelAndView gotoAuthWithId(@PathVariable("id") String msgId) {
		return new ModelAndView("/bione/message/msg-announcement-edit-auth", "id", msgId);
	}
	
	/** 标签Attach跳转  */
	@RequestMapping(value="/msg_announcement_edit_attach/{id}")
	public ModelAndView gotoAttachWithId(@PathVariable("id") String msgId) {
		return new ModelAndView("/bione/message/msg-announcement-edit-attach", "id", msgId);
	}
	
	
}
