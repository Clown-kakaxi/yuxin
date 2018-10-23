/**
 * 
 */
package com.yuchengtech.emp.biappframe.authres.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.yuchengtech.emp.biappframe.authres.entity.BioneFuncInfo;
import com.yuchengtech.emp.biappframe.authres.web.vo.BioneMenuInfoVO;
import com.yuchengtech.emp.bione.common.CommonTreeNode;
import com.yuchengtech.emp.bione.util.ExStringUtils;


/**
 * <pre>
 * Title: 菜单工具类
 * Description: 负责组织、生成显示菜单所需的HTML
 * </pre>
 * 
 * @author mengzx
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:1.00.01     修改人：songxf  修改日期:2012-6-28     修改内容:修改单个菜单显示不正常bug
 * </pre>
 */
public class MenuUtils {

	private static final String MENU_ID_PREFIX = "menu_";

	/**
	 * 生成头部菜单栏的HTML
	 * 
	 * @param menuInfoList
	 * @return
	 */
	public static String list2HeaderMenu(List<CommonTreeNode> menuInfoList) {
		StringBuffer menuHtml = new StringBuffer("");

		menuHtml.append("<table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"menuMain\">\n");
		menuHtml.append(blank(2) + "<tr>\n");
		menuHtml.append(blank(2) + "<td width=\"15\" class=\"frameMenuLeft\">&nbsp;</td>\n");
		menuHtml.append(blank(3) + "<td class=\"frameMenu\">\n");
		menuHtml.append(blank(4) + "<table class=\"rootVoices\" cellspacing='0' cellpadding='0'	border='0'>\n");
		menuHtml.append(blank(5) + "<tr>\n");

		// 除一层菜单外的子菜单的内容
		StringBuffer subMenuHtml = new StringBuffer("");

		if (menuInfoList != null) {

			BioneMenuInfoVO menuInfo = null;

			int count = 1;
			int headerMenuSize = menuInfoList.size();
			String contentClass = "";
			@SuppressWarnings("unused")
			String prevSideClass = "";
			String nextSideClass = "";
			List<CommonTreeNode> subList = new ArrayList<CommonTreeNode>();
			// 生成第一层菜单
			for (CommonTreeNode treeNode : menuInfoList) {
				// 权限许可字符串
				menuInfo = (BioneMenuInfoVO) treeNode.getData();
				subList.add(treeNode);
				// 当前节点是否包含子节点
				boolean hasChild = treeNode.getChildren() == null ? false : true;
				menuHtml.append(blank(6));

				if (hasChild) {
					menuHtml.append("<td class=\"rootVoice {menu: '" + MENU_ID_PREFIX + treeNode.getId() + "'}\">"
							+ treeNode.getText() + "</td>\n");
				} else {
					// menuHtml.append("<td class=\"rootVoice {menu: 'empty'}\" onclick=\"doMenuClick('"
					// + menuInfo.getMenuId()
					// + "','"
					// + menuInfo.getFuncName()
					// + "','"
					// + menuInfo.getNavPath()
					// + "',true)\">"
					// + treeNode.getText() + "</td>\n");

					menuHtml.append("<td class=\"rootVoice {menu: 'empty'}\" onclick=\"doMenuClick('"
							+ menuInfo.getMenuId() + "','" + menuInfo.getFuncName() + "','" + menuInfo.getNavPath()
							+"','" + menuInfo.getFuncName()
							+ "',true)\">");

					menuHtml.append("<div id='rootVoiceDiv'>");

					contentClass = "";
					prevSideClass = "";
					nextSideClass = "noselected";

					if (count == 1) {

						contentClass = "selected";
						prevSideClass = "first_selected";
						nextSideClass = "selected_next";
						menuHtml.append("<div id='rootVoiceSideFirst' class='first first_selected'></div>");
					}

					menuHtml.append("<div id='rootVoiceContent' class='" + contentClass + "'>" + treeNode.getText()
							+ "</div>");

					if (count == headerMenuSize && headerMenuSize != 1) {
						menuHtml.append("<div class='rightside last'></div>");
					} else if (headerMenuSize == 1) {
						menuHtml.append("<div class='rightside last_selected'></div>");
					} else {
						menuHtml.append("<div class='rightside " + nextSideClass + "'></div>");
					}

					menuHtml.append("</div></td>\n");

					count++;
				}
			}

			// 生成子菜单
			for (CommonTreeNode treeNode : subList) {
				list2HeaderMenuByLevel(treeNode, subMenuHtml);
			}
		}

		menuHtml.append(blank(5) + "</tr>\n");
		menuHtml.append(blank(4) + "</table>\n");
		menuHtml.append(blank(3) + "</td>\n");
		menuHtml.append(blank(2) + "<td width=\"15\" class=\"frameMenuRight\">&nbsp;</td>\n");
		menuHtml.append(blank(2) + "</tr>\n");
		menuHtml.append("</table>\n");

		// 追加子菜单的内容
		menuHtml.append(subMenuHtml);

		return menuHtml.toString();
	}

	/**
	 * 生成侧边菜单栏的HTML
	 * 
	 * @param parentMenuInfo
	 *            一级菜单的
	 * @param menuInfoList
	 * @return
	 */
	public static String list2AccordionMenu(BioneFuncInfo funcInfo, List<CommonTreeNode> menuInfoList) {

		StringBuffer menuHtml = new StringBuffer("");
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String basePath = request.getContextPath();
		menuHtml.append("<div id=\"currentTitle\" >\n");
		menuHtml.append("<div style=\"width:30px;float:left\"><img src=\"" + basePath + "/images/classics/icons/menu_title_default.png"
				+ "\" style=\"margin-top:10px\"></div><div style=\"width:150px;float:left\">&nbsp;&nbsp;" + funcInfo.getFuncName() + "</div>\n");
		menuHtml.append("</div>\n");
		menuHtml.append("<ul id=\"navigation\" >\n");

		if (menuInfoList != null) {

			BioneMenuInfoVO menuInfo = null;

			String navPathName;// 导航路径的名称

			// 生成第一层菜单
			for (CommonTreeNode treeNode : menuInfoList) {
				menuInfo = (BioneMenuInfoVO) treeNode.getData();
				// 当前节点是否包含子节点
				boolean hasChild = treeNode.getChildren() == null ? false : true;

				String itemCssClass = "";// header的样式
				String containerCssClass = "";// 内容样式
				String headerHref = "";

				navPathName = funcInfo.getFuncName() + ">>" + menuInfo.getFuncName();

				// 设置菜单item的样式，用于控制菜单是否可以展开
				if (hasChild) {
					headerHref = "#";

				} else {
					itemCssClass += " single-menu-item";
					containerCssClass += " single-menu-container";
					//headerHref = "javascript:doMenuFuncClick('" + menuInfo.getNavPath() + "','" + navPathName + "')";
					//headerHref = "javascript:doMenuFuncClick('" + menuInfo.getNavPath() + "','" + navPathName +"','" + menuInfo.getFuncName()
					//		+ "')";
					headerHref = "#";
				}

				// 图标初始化，没有设置时使用默认图标
				String imgPath = basePath;

				if (StringUtils.isBlank(menuInfo.getNavIcon())) {
					imgPath += "/images/classics/icons/26.png";
				} else {
					imgPath += "/" + menuInfo.getNavIcon();
				}

				// 限制菜单名称的中文字数，保证最好的显示效果
				String menuName = menuInfo.getFuncName();
				menuName = ExStringUtils.subString(menuInfo.getFuncName(), 20, "...");

				menuHtml.append(blank(2) + "<li>");
				menuHtml.append("<div class=\"space\">\n");
				menuHtml.append("</div>\n");
				menuHtml.append("<div class=\"head " + itemCssClass + "\">\n");
				if (hasChild) {
					menuHtml.append(blank(3) + "<a class=\"" + itemCssClass + "\" href=\"" + headerHref
							+ "\"><img class=\"single-menu-item-icon\" src=\"" + imgPath
							+ "\" align=\"absmiddle\"><span >&nbsp;&nbsp;" + menuName + "</span></a>\n");
				}else{
					menuHtml.append(blank(3) + "<a class=\"" + itemCssClass + "\" href=\"" + headerHref + "\" onclick=\"doMenuFuncClick('" + menuInfo.getNavPath() + "','" + navPathName +"','" + menuInfo.getFuncName()+ "',this)\"" 
							+ "\"><img class=\"single-menu-item-icon\" src=\"" + imgPath
							+ "\" align=\"absmiddle\"><span >&nbsp;&nbsp;" + menuName + "</span></a>\n");
	
				}
				menuHtml.append("<div class='headstateicon'></div>\n");
				menuHtml.append(blank(2) + "</div>\n");

				// 侧边栏菜单目前只支持三级菜单

				if (hasChild) {
					List<CommonTreeNode> childNodes = treeNode.getChildren();
					int height = childNodes.size() * 28;
					menuHtml.append(blank(3) + "<ul class=\"container " + containerCssClass + "\" style=\"height:"
							+ height + "\">\n");

					if (childNodes != null) {
						menuHtml.append("<div class=\"space\">\n");
						menuHtml.append("</div>\n");
						for (CommonTreeNode childTreeNode : childNodes) {

							menuInfo = (BioneMenuInfoVO) childTreeNode.getData();

							imgPath = basePath;

							if (StringUtils.isBlank(menuInfo.getNavIcon())) {
								imgPath += "/images/icons/menu_default.png";
							} else {
								imgPath += "/" + menuInfo.getNavIcon();
							}

							// 限制菜单名称的中文字数，保证最好的显示效果
							menuName = menuInfo.getFuncName();
							menuName = ExStringUtils.subString(menuInfo.getFuncName(), 30, "...");

							menuHtml.append(blank(3) + "<li>");
//							menuHtml.append("<a href=\"javascript:doMenuFuncClick('" + menuInfo.getNavPath() + "','"
//									+ (navPathName + ">>" + menuInfo.getFuncName()) + "')\">");
							menuHtml.append("<a href=\"#\" onclick=\"javascript:doMenuFuncClick('" + menuInfo.getNavPath() + "','"
									+ (navPathName + ">>" + menuInfo.getFuncName()) + "','"+menuInfo.getFuncName()+"',this)\" >");

							menuHtml.append("<img class=\"submenuicon\" src=\"" + imgPath + "\" align=\"absmiddle\"> ");
							menuHtml.append("<span>&nbsp;&nbsp;" + menuName + "</span>");
							menuHtml.append("</a></li>\n");

						}

					}

				} else {
					menuHtml.append(blank(3) + "<ul class=\"container " + containerCssClass + "\" style=\"height:" + 0
							+ "\">\n");
				}

				menuHtml.append(blank(3) + "</ul>\n");
				menuHtml.append(blank(2) + "</li>\n");
			}

		}
		menuHtml.append("<div class=\"space\">\n");
		menuHtml.append("</div>\n");
		menuHtml.append("</ul>\n");

		return menuHtml.toString();
	}

	/**
	 * 从菜单第二层开始分层次生成菜单的html
	 * 
	 * @param treeNode
	 * @param subMenuHtml
	 */
	private static void list2HeaderMenuByLevel(CommonTreeNode parentTreeNode, StringBuffer subMenuHtml) {

		List<CommonTreeNode> childNodes = parentTreeNode.getChildren();

		if (childNodes != null) {

			boolean firstNode = true;
			BioneFuncInfo menuInfo = null;

			// 包含子菜单的菜单
			List<CommonTreeNode> hasChildTreeNodeList = new ArrayList<CommonTreeNode>();

			for (CommonTreeNode treeNode : childNodes) {
				menuInfo = (BioneFuncInfo) treeNode.getData();
				if (firstNode) {
					subMenuHtml.append("\n<div id=\"" + MENU_ID_PREFIX + treeNode.getUpId() + "\" class=\"menu\">\n");
					firstNode = false;
				} else {
					subMenuHtml.append(blank(1) + "<a rel=\"separator\"> </a>\n");
				}

				String navIcon = menuInfo.getNavIcon();

				if (navIcon == null)
					navIcon = "";
				else {
					navIcon = ",img: '" + navIcon + "'";

				}
				;

				// 当前节点是否包含子节点
				boolean hasChild = treeNode.getChildren() == null ? false : true;

				if (hasChild) {
					hasChildTreeNodeList.add(treeNode);
					subMenuHtml.append(blank(1) + "<a class=\"{menu: '" + MENU_ID_PREFIX + treeNode.getId() + "'"
							+ navIcon + "}\">" + treeNode.getText() + "</a>\n");
				} else {

					subMenuHtml.append(blank(1) + "<a class=\"{action: 'doMenuClick(\\'" + menuInfo.getFuncId()
							+ "\\',\\'" + menuInfo.getFuncName() + "\\',\\'" + menuInfo.getNavPath() + "\\',false)'"
							+ navIcon + "}\">" + treeNode.getText() + "</a>\n");
				}
			}

			subMenuHtml.append("</div>");

			// 生成下层的菜单
			for (CommonTreeNode treeNode : hasChildTreeNodeList) {
				list2HeaderMenuByLevel(treeNode, subMenuHtml);
			}
		}
	}

	/**
	 * 空格
	 * 
	 * @param num
	 * @return
	 */
	private static String blank(int num) {

		StringBuffer buf = new StringBuffer("");

		for (int i = 0; i < num; i++)
			buf.append("\t");

		return buf.toString();
	}
}
