package com.yuchengtech.emp.biappframe.security;

import java.util.List;

import com.yuchengtech.emp.biappframe.auth.entity.BioneAuthObjResRel;
import com.yuchengtech.emp.biappframe.authres.entity.BioneResOperInfo;
import com.yuchengtech.emp.bione.common.CommonTreeNode;

/**
 * 
 * <pre>
 * Title: 授权资源接口
 * Description: 定义授权资源的数据接口，用于自定义扩展授权资源，系统默认支持的授权资源都实现了此接口
 * </pre>
 * 
 * @author mengzx
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface IResObject {

	/**
	 * 返回授权资源的标识
	 * 
	 * @return
	 */
	public String getResObjDefNo();

	/**
	 * 返回树形结构的授权资源数据 数据格式为Id-UpId的形式
	 * 
	 * @param user
	 *            用户认证信息
	 * @return
	 */
	public List<CommonTreeNode> doGetResInfo();

	/**
	 * 获取当前用户有权限访问的资源许可字符串 格式如：
	 * 
	 * OPER:admin_user_edit 表示用户管理的编辑权限,其中 OPER：标识操作权限，对应DATA表示数据权限， admin_user_edit对应操作类型编码
	 * 
	 * 操作类型编码必须保证全局唯一性，建议采取以下的命名规则：
	 * 比如用户管理的URL为 /bione/user.action,则对应的操作类型命名为
	 * 
	 * admin_user_edit
	 * admin_user_delete
	 * .......
	 * 
	 * @param authObjResRelList
	 *            授权对象和当前资源的关系
	 * @return
	 */
	public List<String> doGetResPermissions(
			List<BioneAuthObjResRel> authObjResRelList);

	/**
	 * 返回树形结构的资源操作信息（新增、修改、删除、查看、等） 数据格式为Id-UpId的形式
	 * 
	 * @param resId
	 *            资源ID
	 * @return
	 */
	public List<CommonTreeNode> doGetResOperateInfo(Long resId);

	/**
	 * 返回树形结构的资源数据访问规则信息 数据格式为Id-UpId的形式
	 * 
	 * @param resId
	 *            资源ID
	 * @return
	 */
	public List<CommonTreeNode> doGetResDataRuleInfo(Long resId);
	
	/**
	 * 查询某个授权资源的所有操作许可
	 * @param resDefNo
	 * 	
	 * @param resIdList
	 * 			授权资源标识
	 * @return
	 */
	public List<BioneResOperInfo> findResOperList(String resDefNo, List<String> resIdList);

}
