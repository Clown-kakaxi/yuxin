package com.yuchengtech.emp.bione.message.service;

import java.util.Map;

import com.yuchengtech.emp.bione.dao.SearchResult;
import com.yuchengtech.emp.bione.message.entity.AbsBioneMessageEntity;

/**
 * <pre>
 * Title: 通用的消息服务接口
 * Description: 通用的消息服务接口
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
public interface IBioneMessageService<T extends AbsBioneMessageEntity> {

	/** 新增消息 */
	public void saveMsg(T entity);

	/** 查询单行消息 */
	public T queryMsg(String msgId);

	/** 分页查询 */
	public SearchResult<T> getMsgListWithPage(String logicSysNo, String userId,
			int firstResult, int pageSize, String orderBy, String orderType,
			Map<String, Object> conditionMap);

	/** 修改消息 */
	public T updateMsg(T entity);

	/** 删除消息 */
	public void deleteMsg(String id);

	/** 批量删除消息 */
	public void deleteBatch(String[] ids);

}
