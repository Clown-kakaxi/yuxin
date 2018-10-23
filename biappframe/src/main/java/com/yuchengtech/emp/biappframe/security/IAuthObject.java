/**
 * 
 */
package com.yuchengtech.emp.biappframe.security;

import java.util.List;

import com.yuchengtech.emp.bione.common.CommonTreeNode;

/**
 * <pre>
 * Title:授权对象接口
 * Description: 定义授权对象的数据接口，用于自定义扩展授权资源，系统默认支持的授权对象都实现了此接口
 * </pre>
 * @author mengzx  
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IAuthObject {
	
	/**
	 * 
	 * @return
	 */
	public String getAuthObjDefNo();
	
	/**
	 * 返回树形结构的授权对象数据
	 * 数据格式为Id-UpId的形式
	 * 
	 * @param user 用户认证信息 
	 * @return
	 */
	public List<CommonTreeNode> doGetAuthObjectInfo();
	
	
	/**
	 * 获取当前用户关联的有效的授权对象ID
	 * @param user
	 * @return
	 */
	public List<String> doGetAuthObjectIdListOfUser();

}
