/**
 * 
 */
package com.yuchengtech.emp.biappframe.authobj.web;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuchengtech.emp.biappframe.authobj.entity.BioneLogInfo;
import com.yuchengtech.emp.biappframe.authobj.service.LogBS;
import com.yuchengtech.emp.biappframe.authobj.web.vo.BioneLogInfoVO;
import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.biappframe.security.BiOneSecurityUtils;
import com.yuchengtech.emp.biappframe.user.entity.BioneUserInfo;
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
 * @author huangye huangye@yuchengtech.com
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("/bione/admin/log")
public class LogController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(LogController.class);
	@Autowired
	private LogBS logBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "/bione/log/log-index";
	}

	@RequestMapping("/list.*")
	@ResponseBody
	public Map<String, Object> list(Pager pager) {
		SearchResult<BioneLogInfo> searchResult=logBS.getLogList(pager.getPageFirstIndex(), pager.getPagesize(), pager.getSortname(), pager.getSortorder(), pager.getSearchCondition());
		SearchResult<BioneLogInfoVO> showResult = logBS
				.changeIdToNameList(searchResult);

		
		Map<String, Object> logMap = new HashMap<String, Object>();
		logMap.put("Rows", showResult.getResult());
		logMap.put("Total", showResult.getTotalCount());
		return logMap;
	}

	@RequestMapping("/addLog")
	@ResponseBody
	public void addLog(String logEvent) {
		BioneLogInfo logInfo = new BioneLogInfo();
		logInfo.setLogNo(RandomUtils.uuid2());
		logInfo.setLogicSysNo(BiOneSecurityUtils.getCurrentUserInfo()
				.getCurrentLogicSysNo());
		BioneUserInfo user = this.logBS.getEntityById(BioneUserInfo.class,
				BiOneSecurityUtils.getCurrentUserId());
		logInfo.setOperUser(user.getUserNo());
		logInfo.setLoginIP(this.getRequest().getRemoteAddr());
		logInfo.setOccurTime(new Timestamp(System.currentTimeMillis()));
		logInfo.setLogEvent(logEvent);
		this.logBS.saveEntity(logInfo);
	}

}
