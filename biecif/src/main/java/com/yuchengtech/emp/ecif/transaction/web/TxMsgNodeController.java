package com.yuchengtech.emp.ecif.transaction.web;


import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yuchengtech.emp.biappframe.base.web.BaseController;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNode;
import com.yuchengtech.emp.ecif.transaction.entity.TxMsgNodeVO;
import com.yuchengtech.emp.ecif.transaction.service.TxMsgNodeBS;

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
@RequestMapping("/ecif/transaction/txmsgnode")
public class TxMsgNodeController extends BaseController {
	@Autowired
	private TxMsgNodeBS txMsgNodeBS;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		
		return "/ecif/transaction/txmsgnode-index";
	}

	
	/**
	 * 用于添加，或修改时的保存对象
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void create(TxMsgNodeVO vo) {
		TxMsgNode t = new TxMsgNode();
		t.setNodeId(vo.getNodeId());
		t.setMsgId(vo.getMsgId());
		t.setUpNodeId(vo.getUpNodeId());
		t.setNodeCode(vo.getNodeCode());
		t.setNodeName(vo.getNodeNamexx());
		t.setNodeDesc(vo.getNodeDesc());
		t.setNodeGroup(vo.getNodeGroup());
		t.setNodeLabel(vo.getNodeLabel());
		t.setNodeDisplay(vo.getNodeDisplay());
		t.setNodeTp(vo.getNodeTp());
		t.setNodeSeq(vo.getNodeSeq());
		t.setState(vo.getState());
		t.setCreateTm(vo.getCreateTm());
		t.setCreateUser(vo.getCreateUser());
		
		this.txMsgNodeBS.updateEntity(t);
	}

	/**
	 * 根据ID，加载数据
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public TxMsgNodeVO show(@PathVariable("id") Long id) {
		
		TxMsgNode model = this.txMsgNodeBS.getEntityById(id);
		if(model==null) return null;
		
		TxMsgNodeVO vo = new TxMsgNodeVO();
		BeanUtils.copyProperties(model, vo);
		vo.setNodeNamexx(vo.getNodeName());
		
		return vo;
	}

	/**
	 * 执行修改前的数据加载
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") Long id) {
		return new ModelAndView("/ecif/transaction/txmsgnode-edit", "id", new Long(id));
	}

	/**
	 * 执行添加前页面跳转
	 * 
	 * @return
	 * 		String	用于匹配结果页面
	 */
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public ModelAndView editNew(Long msgId,Long upNodeId) {
		ModelMap mm = new ModelMap();
		mm.put("msgId", msgId);
		mm.put("upNodeId", upNodeId);
		
		return new ModelAndView("/ecif/transaction/txmsgnode-edit", mm);
	}

	/**
	 * 执行删除操作，可进行指删除
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public void destroy(@PathVariable("id") String id) {
		String[] ids = id.split(",");
		if (ids.length > 1) {
			this.txMsgNodeBS.removeEntityByProperty("attrId", id);
		} else {
			//this.txMsgNodeBS.removeEntityById(new Long(id));
			this.txMsgNodeBS.deleteBatch(new Long(id));
		}
	}


	/**
	 * 获取combobox
	 */
	@ResponseBody
	@RequestMapping("getComBoBox.*")
	public List<Map<String,String>> getComBoBox() {
		return this.txMsgNodeBS.getComBoBox();
	}
	
}
