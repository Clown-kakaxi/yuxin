package com.yuchengtech.emp.ecif.transaction.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import com.yuchengtech.emp.ecif.transaction.entity.CustomMsgException;
import com.yuchengtech.emp.ecif.transaction.entity.TxClientAuth;
import com.yuchengtech.emp.ecif.transaction.entity.TxClientAuthVO;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgCheckinfo;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeTabMap;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeTabMapVO;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgCheckinfoBS;

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
@RequestMapping("/ecif/transaction/txmsgcheckinfo")
public class TxMsgCheckinfoController extends BaseController {
	protected static Logger log = LoggerFactory.getLogger(TxMsgCheckinfoController.class);
	
	@Autowired
	private TxMsgCheckinfoBS txMsgCheckinfoBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		
		return "/ecif/transaction/txmsgnodetab-index";
	}



	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView editNew(String txId) {
		
//		TxMsgCheckinfo checkinfo = new TxMsgCheckinfo();
//		checkinfo.setCheckId(Long.parseLong("100011"));
//		//checkinfo.setCheckinfo(this.getXSDByMsgId(msgId));
//		//checkinfo.setDescinfo(this.getXMLByMsgId(msgId));
//		checkinfo.setMsgId(Long.parseLong("100011"));
//		checkinfo.setState("1");
//		this.txMsgCheckinfoBS.saveEntity(checkinfo);
        String[] txids = txId.split(",");
        for(int i=0;i<txids.length;i++){
			try {
				this.txMsgCheckinfoBS.createXMLAndXSDByTxCode(new Long(txids[i]));
			} catch (CustomMsgException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
		return new ModelAndView("/ecif/transaction/txdef-index");
		//return null;
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			this.txMsgCheckinfoBS.removeEntityByProperty("nodeTabMapId", id);
		} else {
			this.txMsgCheckinfoBS.removeEntityById(new Long(id));
		}
	}

}
