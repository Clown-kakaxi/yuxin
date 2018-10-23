<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template4.jsp">
<head>
<script type="text/javascript">
	//当前授权对象，全局变量
	var curObjDefNo = "";

	//许可树，数据备份(切换资源tab页时的缓存)
	//[{tabId,[{permissionTreeId,permissionTreeData}]}]
	var permissionTreeDatas = new Array();

	//授权资源根节点图标
	var auth_obj_root_icon = "${ctx}/images/classics/icons/house.png";
	//资源操作根节点图标
	var res_oper_root_icon = "${ctx}/images/classics/icons/house.png";

	//资源操作当前选中tabid
	var selectedResTab = "";

	//记录当前点击的授权对象id
	var selectedObjId = "";

	//许可类型
	var RES_PERMISSION_TYPE_OPER = '1';//操作
	var RES_PERMISSION_TYPE_NAME = '2';//数据规则

	var PERMISSION_ALL = "*";//全部许可
	var PERMISSION_NONE = "-";//没有任何许可

	var authObjMapping = new Array();

	var groupicon = "${ctx}/images/classics/icons/find.png";

	var menuResNo = "AUTH_RES_MENU";

	function onClick() {

	}

	//展开全部节点
	function openAllNodes(treeObj) {
		if (treeObj) {
			treeObj.expandAll(true);
		}
	}

	//折叠全部节点
	function closeAllNodes(treeObj) {
		if (treeObj) {
			treeObj.expandAll(false);
		}
	}

	$(function() {
		$("#treeToolbar").ligerTreeToolBar({
			items : [ {
				icon : 'refresh',
				text : '刷新',
				click : function() {
					if (leftTree && authLeftCombobox) {
						var objDefNo = authLeftCombobox.getValue();
						if (objDefNo && objDefNo != "") {
							refreshObjDefTree(objDefNo);
						}
					}
				}
			}, {
				line : true
			}, {
				icon : 'plus',
				text : '展开',
				click : function() {
					openAllNodes(leftTree);
				}
			}, {
				line : true
			}, {
				icon : 'lock',
				text : '折叠',
				click : function() {
					closeAllNodes(leftTree);
				}
			} ],
			treemenu : false
		});
	});

	//刷新左侧授权对象树
	function refreshObjDefTree(objDefNo) {
		if (leftTree && leftTree.setting) {
			//先移除所有授权对象节点
			leftTree.removeChildNodes(leftTree.getNodeByParam("id", '0', null));
			//查询该授权对象并更新树
			$.ajax({
				cache : false,
				async : true,
				url : "${ctx}/bione/admin/auth/getAuthObjDefTree.json?d="
						+ new Date().getTime(),
				dataType : 'json',
				type : "post",
				data : {
					"objDefNo" : objDefNo
				},
				beforeSend : function() {
					BIONE.loading = true;
					BIONE.showLoading("正在加载数据中...");
				},
				complete : function() {
					BIONE.loading = false;
					BIONE.hideLoading();
				},
				success : function(result) {
					if (!result)
						return;
					leftTree.addNodes(leftTree.getNodeByParam("id", '0', null),
							result, true);
					//展开树
					leftTree.expandAll(true);
				},
				error : function(result, b) {
					BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
				}
			});
		}
	}

	//左侧combobox值改变
	function leftComboChange(value, text) {
		//刷新左侧树
		refreshObjDefTree(value);
		//刷新资源树
		if (navtab1) {
			//刷新资源树选中
			var tabList = navtab1.getTabidList();
			if (!tabList) {
				return;
			}
			//刷新资源树之前取消之前对象资源授权的勾选
			for ( var m = 0; m < tabList.length; m++) {
				var tabTree = eval("resTree_" + tabList[m]);
				if (tabTree) {
					removePermissionByTree(tabTree);
				}
			}
		}
		//初始化资源操作树
		if (rightOperTree) {
			rightOperTree.removeChildNodes(rightOperTree.getNodeByParam("id",
					0, null));
			//重置checkbox
			rightOperTree.checkAllNodes(false);
			//刷新根节点
			rightOperTree.updateNode(rightOperTree.getNodeByParam("id", 0, null), true);
		}
	}

	//移除树中所有节点的授权相关信息
	//参数:树对象
	function removePermissionByTree(treeObj) {
		if (treeObj && treeObj.getCheckedNodes(true)) {
			var treeNodes = treeObj.getCheckedNodes(true);
			if (treeNodes) {
				for ( var i = 0; i < treeNodes.length; i++) {
					treeNodes[i].permissionId = "";
					treeNodes[i].permissionType = "";
				}
			}
			treeObj.checkAllNodes(false);
		}
	}

	//初始化操作树
	//参数:授权资源tabid,
	//     某一个资源对象；若某一个资源对象不为空，则加载所有初始化勾选资源对象的授权操作，反之加载指定资源对象
	function initOperTree(selectTabId, resNodes) {
		var resTree = eval("resTree_" + selectTabId);
		var oneResClick = false;
		if (resTree) {
			//获取当前初始化选中的节点
			var resSelNodes = new Array();
			if (!resNodes || typeof resNodes == 'undefined') {
				resSelNodes = resTree.getCheckedNodes(true);
			} else {
				oneResClick = true;
				for ( var i = 0; i < resNodes.length; i++) {
					resSelNodes.push(resNodes[i]);
				}
			}
			if (resSelNodes) {
				var newDataArray = new Array();
				for ( var k = 0; k < resSelNodes.length; k++) {
					var n = resSelNodes[k];
					if (n.id == "0") {
						//若是根节点，跳过
						continue;
					}
					if ((RES_PERMISSION_TYPE_OPER == n.permissionType
							&& n.permissionId != null && n.permissionId != "")
							|| oneResClick) {
						//若是资源操作，且许可id不为空，或者是单一资源对象点击动作
						//将该资源节点放入操作树中
						var newData = {
							id : selectTabId + "_" + n.id,
							realId : n.id,
							upId : 0,
							text : n.text,
							nodeType : selectTabId,
							permissionId : n.permissionId,
							icon : n.icon,
							isParent : true
						};
						//若是菜单资源，用功能id进行操作判断---2013-1-30 新模型 用菜单Id 即可
						if (newData.nodeType == menuResNo) {
							newData.id = selectTabId + "_" + n.params.menuId;
						}
						newDataArray.push(newData);
					}
				}
				if (rightOperTree) {
					var operTreeRoot = rightOperTree.getNodeByParam("id", 0,
							null);
					if (newDataArray.length > 0) {
						var resIds = "";
						var permissionIds = "";
						for ( var l = 0; l < newDataArray.length; l++) {
							//拼接授权资源编号
							if (resIds != "") {
								resIds += ",";
							}
							resIds += newDataArray[l].realId;
							//拼接勾选的授权操作编号
							if (permissionIds != "") {
								permissionIds += ",";
							}
							if (newDataArray[l].permissionId == PERMISSION_NONE) {
								continue;
							}
							permissionIds += newDataArray[l].permissionId;
						}
						$
								.ajax({
									cache : false,
									async : false,
									url : "${ctx}/bione/admin/auth/getMenuOperTree.json?d="
											+ new Date().getTime(),
									dataType : 'json',
									type : "post",
									data : {
										"resIds" : resIds,
										"resDefNo" : selectTabId,
										"permissionIds" : permissionIds
									},
									beforeSend : function() {
										BIONE.loading = true;
										BIONE.showLoading("正在加载数据中...");
									},
									complete : function() {
										BIONE.loading = false;
										BIONE.hideLoading();
									},
									success : function(result) {
										if (!result)
											return;
										for ( var i = 0; i < result.length; i++) {
											var subNode = result[i];
											newDataArray.push(subNode);
										}
										if (!oneResClick
												&& selectedResTab != selectTabId
												&& treeTmp) {
											//先清空临时树
											//从当前树中移除
											var tmpTreeRoot = treeTmp
													.getNodeByParam("id", 0,
															null);
											treeTmp
													.removeChildNodes(tmpTreeRoot);
											treeTmp.checkAllNodes(false);
											//若不是具体资源点击，而是由刷新全部资源动作发起;
											//并且，该tab并不是当前选中tab
											treeTmp.addNodes(tmpTreeRoot,
													newDataArray, true);
											//展开树
											treeTmp.expandAll(true);
											//初始化勾选
											for ( var j = 0; j < result.length; j++) {
												var checkNode = treeTmp
														.getNodeByParam("id",
																result[j].id,
																null);
												if (!checkNode.isParent) {
													//叶子节点
													treeTmp
															.checkNode(
																	checkNode,
																	checkNode.ischecked,
																	true, false);
												}
											}
											//将数据放入缓存
											var treeDatas = new Array();
											if ((treeTmp.getNodes())[0].children) {
												var nodeChildren = (treeTmp
														.getNodes())[0].children;
												treeDatas["rightOperTree"] = nodeChildren;
											}
											permissionTreeDatas[selectTabId] = treeDatas;
											//从当前树中移除
											treeTmp
													.removeChildNodes(tmpTreeRoot);
											treeTmp.checkAllNodes(false);

										} else {
											rightOperTree.addNodes(
													operTreeRoot, newDataArray,
													true);
											//展开树
											rightOperTree.expandAll(true);
											//初始化勾选
											for ( var j = 0; j < result.length; j++) {
												var checkNode = rightOperTree
														.getNodeByParam("id",
																result[j].id,
																null);
												if (!checkNode.isParent) {
													//叶子节点
													rightOperTree
															.checkNode(
																	checkNode,
																	checkNode.ischecked,
																	true, false);
												}
											}
										}
									},
									error : function(result, b) {
										BIONE.tip('发现系统错误 <BR>错误码：'
												+ result.status);
									}
								});
					}

				}
			}
		}
	}

	//授权对象节点点击
	function authObjNodeClick(treeId, treeNode) {
		//判断点击节点是否是已选中节点，若是选择的新节点
		if (treeNode.id != selectedObjId) {
			//清空缓存
			permissionTreeDatas = new Array();
		}
		selectedObjId = treeNode.id
		//先清空授权操作树
		if (rightOperTree) {
			rightOperTree.removeChildNodes(rightOperTree.getNodeByParam("id",
					0, null));
			//重置checkbox
			rightOperTree.checkAllNodes(false);
			//刷新根节点
			rightOperTree.updateNode(rightOperTree
					.getNodeByParam("id", 0, null), true);
		}
		if (authLeftCombobox && authLeftCombobox.getValue()
				&& authLeftCombobox.getValue() != "" && treeNode) {
			//授权对象编号
			var objDefNo = authLeftCombobox.getValue();
			//授权具体对象id
			var objDefId = treeNode.params.id;
			//查询该授权对象对应资源
			$
					.ajax({
						cache : false,
						async : true,
						url : "${ctx}/bione/admin/auth/getAuthObjResRel.json?d="
								+ new Date().getTime(),
						dataType : 'json',
						type : "get",
						data : {
							"objDefNo" : objDefNo,
							"objDefId" : objDefId
						},
						beforeSend : function() {
							BIONE.loading = true;
							BIONE.showLoading("正在加载数据中...");
						},
						complete : function() {
							BIONE.loading = false;
							BIONE.hideLoading();
						},
						success : function(result) {
							if (navtab1) {
								//刷新资源树选中
								var tabList = navtab1.getTabidList();
								if (!tabList) {
									return;
								}
								if (!result) {
									return;
								}
								var data = result.Data;
								if (!data || data.length <= 0) {
									return;
								}
								for ( var i = 0; i < tabList.length; i++) {
									var tabId = tabList[i];
									var resTreeObj = eval("resTree_" + tabId);
									for ( var j = 0; j < data.length; j++) {
										var relObj = data[j];
										if (relObj.id.resDefNo == tabId) {
											if (resTreeObj) {
												var resTreeNode = resTreeObj
														.getNodeByParam(
																"id",
																relObj.id.resId,
																null);
												if (resTreeNode == null) {
													continue;
												}
												resTreeNode.permissionType = relObj.id.permissionType;
												if (typeof resTreeNode.permissionId == "undefined") {
													resTreeNode.permissionId = "";
												}
												if (resTreeNode.permissionId
														&& resTreeNode.permissionId != ""
														&& resTreeNode.permissionId != null) {
													resTreeNode.permissionId += ",";
												}
												resTreeNode.permissionId += relObj.id.permissionId;
												if (resTreeNode
														&& resTreeNode != null) {
													//只勾选存在关联关系的资源节点
													resTreeObj.checkNode(
															resTreeNode, true,
															false, false);
												}
											}
										}
									}
								}
								var selectTabId = navtab1
										.getSelectedTabItemID();
								//刷新操作树,初始化是刷新当前选中tab(即第一个tab)的资源操作
								if (navtab1) {
									var idList = navtab1.getTabidList();
									if (idList) {
										for ( var i = 0; i < idList.length; i++) {
											initOperTree(idList[i], null);
										}
									}
								}
								//刷新数据规则树(暂没实现)
							}

						},
						error : function(result, b) {
							BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
						}
					});
		}
	}

	$(function() {

		BIONE.createButton({
					text : '保 存',
					width : '80px',
					appendTo : '#bottom',
					operNo : 'saveButton',
					icon : 'save',
					click : function() {
						if (leftTree) {
							var leftSelNodes = leftTree.getSelectedNodes();
							if (!leftSelNodes.length
									|| leftSelNodes.length <= 0) {
								//若没有选择授权对象
								BIONE.tip('请选择有效授权对象');
								return;
							} else {
								var leftSelNode = leftSelNodes[0];
								if (leftSelNode.id == '0') {
									//若选择的是根节点
									BIONE.tip('请选择有效授权对象');
									return;
								} else {
									var resSelNodes = new Array();
									//获取资源
									if (navtab1) {
										var tabList = navtab1.getTabidList();
										if (tabList) {
											for ( var i = 0; i < tabList.length; i++) {
												var tabTree = eval("resTree_"
														+ tabList[i]);
												if (tabTree) {
													var tabTreeSelNodes = tabTree
															.getCheckedNodes(true);
													for ( var j = 0; j < tabTreeSelNodes.length; j++) {
														var operSelNodes = new Array();
														var operIds = "";
														if (tabTreeSelNodes[j].id == '0') {
															//不包含根节点
															continue;
														}
														//获取具体某个菜单操作许可
														var selectId = tabTreeSelNodes[j].id;
														if (tabList[i] == menuResNo) {
															//若是菜单资源，用功能id进行操作关联
															selectId = tabTreeSelNodes[j].params.menuId;
														}
														//1、若是当前选中tab，直接从操作树中选取
														if (tabList[i] == navtab1
																.getSelectedTabItemID()) {
															if (rightOperTree) {
																var resNode = rightOperTree
																		.getNodeByParam(
																				"id",
																				(tabList[i]
																						+ "_" + selectId),
																				null);
																if (resNode) {
																	var resNodeChildren = resNode.children;

																	if (resNodeChildren
																			&& resNodeChildren.length > 0) {
																		var childArray = rightOperTree
																				.transformToArray(resNodeChildren);
																		for ( var m = 0; m < childArray.length; m++) {
																			//若是勾选的，并且是叶子节点
																			if (childArray[m].checked
																					&& !childArray[m].isParent) {
																				if (operIds != "") {
																					operIds += ",";
																				}
																				operIds += childArray[m].params.realId;
																			}
																		}

																	}
																}
															}
														}
														//2、若不是当前选中tab，从内存缓存(permissionTreeDatas)中获取
														else {
															var treeDatas = permissionTreeDatas[tabList[i]];
															if (treeDatas) {
																var datas = treeDatas["rightOperTree"];
																if (datas
																		&& datas.length > 0) {
																	if (treeTmp) {
																		//先删掉临时树的节点
																		treeTmp
																				.removeChildNodes(treeTmp
																						.getNodeByParam(
																								"id",
																								'0',
																								null));
																		//添加
																		treeTmp
																				.addNodes(
																						treeTmp
																								.getNodeByParam(
																										'id',
																										'0',
																										null),
																						datas,
																						false);
																		treeTmp
																				.updateNode(treeTmp
																						.getNodeByParam(
																								'id',
																								'0',
																								null));
																		var resNode = treeTmp
																				.getNodeByParam(
																						"id",
																						(tabList[i]
																								+ "_" + selectId),
																						null);
																		if (resNode) {
																			var resNodeChildren = resNode.children;

																			if (resNodeChildren
																					&& resNodeChildren.length > 0) {
																				var childArray = treeTmp
																						.transformToArray(resNodeChildren);
																				for ( var m = 0; m < childArray.length; m++) {
																					//若是勾选的，并且是叶子节点
																					if (childArray[m].checked
																							&& !childArray[m].isParent) {
																						if (operIds != "") {
																							operIds += ",";
																						}
																						operIds += childArray[m].params.realId;
																					}

																				}
																			}
																		}
																	}
																}
															}
														}
														operSelNodes
																.push({
																	resId : tabTreeSelNodes[j].id,
																	operIds : operIds
																});
														resSelNodes
																.push({
																	//资源资源定义标志
																	resDefNo : tabList[i],
																	//被授权资源
																	//ids:tabTreeSelIds,
																	ids : tabTreeSelNodes[j].id,
																	//授权资源许可
																	permissionIds : operSelNodes
																});
													}
												}
											}
											var nodes = {
												objDefNo : authLeftCombobox
														.getValue(),
												objId : leftSelNode.params.id,
												resSelNodes : []
											};
											nodes.resSelNodes = resSelNodes;
											if (treeTmp) {
												//从当前树中移除
												treeTmp
														.removeChildNodes(treeTmp
																.getNodesByParam(
																		"id",
																		"0",
																		null));
												treeTmp.checkAllNodes(false);
											}
											$
													.ajax({
														cache : false,
														async : true,
														url : "${ctx}/bione/admin/auth/saveAuth?d="
																+ new Date()
																		.getTime(),
														dataType : 'json',
														type : "post",
														data : {
															nodes : JSON2
																	.stringify(nodes)
														},
														beforeSend : function() {
															BIONE.loading = true;
															BIONE
																	.showLoading("正在加载数据中...");
														},
														complete : function() {
															BIONE.loading = false;
															BIONE.hideLoading();
														},
														success : function(
																result) {
															//将相应资源tab中节点的许可id属性更新
															for ( var i = 0; i < resSelNodes.length; i++) {
																var tmp = resSelNodes[i];
																var perIds = "";
																if (tmp.permissionIds != "") {
																	perIds = tmp.permissionIds
																			.join(",");
																}
																if (eval("resTree_"
																		+ tmp.resDefNo)) {
																	var nTmp = eval(
																			"resTree_"
																					+ tmp.resDefNo)
																			.getNodesByParam(
																					"id",
																					tmp.ids,
																					null);
																	if (nTmp != null
																			&& nTmp.length > 0) {
																		nTmp[0].permissionId = perIds;
																	}
																}
															}

															BIONE.tip('保存成功!');
														},
														error : function(
																result, b) {
															BIONE
																	.tip('保存失败,发现系统错误 <BR>错误码：'
																			+ result.status);
														}
													});
										}
									}
								}
							}
						}
					}
				});

		//BIONE.createButton({
		//	text : '刷新',
		//	width : '50px',
		//	appendTo : '#btn2',
		//	operNo:'resetButton',
		//	icon:'refresh',
		//	click : function() {
		//		
		//	}
		//});

		window['leftTree'] = $.fn.zTree
				.init(
						$("#leftTree"),
						{
							data : {
								keep : {
									parent : true
								},
								key : {
									name : "text"
								},
								simpleData : {
									enable : true,
									idKey : "id",
									pIdKey : "upId",
									rootPId : null
								}
							},
							view : {
								selectedMulti : false,
								showLine : false
							},
							callback : {
								beforeClick : function(treeId, treeNode,
										clickFlag) {
									if (treeNode.id == "0"
											|| treeNode.params.cantClick) {
										//若点击的是根节点,或者点击的节点设置了不能点击的属性
										return false;
									}
									var leftTreeSelNodes = leftTree
											.getSelectedNodes();
									if (leftTreeSelNodes.length
											&& leftTreeSelNodes.length > 0) {
										if (treeNode.id != leftTreeSelNodes[0].id) {
											if (navtab1) {
												//刷新资源树选中
												var tabList = navtab1
														.getTabidList();
												if (!tabList) {
													return;
												}
												//刷新资源树之前取消之前对象资源授权的勾选
												for ( var m = 0; m < tabList.length; m++) {
													var tabTree = eval("resTree_"
															+ tabList[m]);
													if (tabTree) {
														removePermissionByTree(tabTree);
													}
												}
											}
											var authTree = $.fn.zTree
													.getZTreeObj(treeId);
											//if(authTree){
											//	authTree.selectNode(treeNode,false);
											//}
											//authObjNodeClick(treeId,treeNode);
										}
									} else {
										return true;
									}
								},
								onClick : function(event, treeId, treeNode) {
									if (treeNode.id == "0") {
										return;
									}
									authObjNodeClick(treeId, treeNode);
								}
							}
						}, [ {
							id : "0",
							text : "授权对象树",
							icon : auth_obj_root_icon
						} ]);

		//初始化授权资源tab
		window['navtab1'] = $("#navtab1").ligerTab();
		//授权资源tab切换之前的授权操作备份,暂只缓存了资源操作树信息
		navtab1.bind("beforeSelectTabItem", function(tabId) {
			//放入permissionTreeDatas
			if (rightOperTree) {
				var treeDatas = new Array();
				if ((rightOperTree.getNodes())[0].children) {
					var nodeChildren = (rightOperTree.getNodes())[0].children;
					var nodeChildJson = rightOperTree
							.transformToArray(nodeChildren);
					//这里transformtoarray在children处理上貌似有点问题，暂不用
					//treeDatas["rightOperTree"] = nodeChildJson;
					treeDatas["rightOperTree"] = nodeChildren;
				}
				permissionTreeDatas[selectedResTab] = treeDatas;
			}
		}, this);
		navtab1.bind("afterSelectTabItem", function(tabId) {
			//放入permissionTreeDatas
			selectedResTab = tabId;
			if (rightOperTree) {
				var treeDatas = permissionTreeDatas[tabId];
				var rootNodes = rightOperTree.getNodesByParam("id", "0", null);
				if (rootNodes != null && rootNodes.length > 0) {
					var rootNodeTmp = rootNodes[0];
					if (treeDatas != null && typeof treeDatas != "undefined") {
						if (treeDatas["rightOperTree"]
								&& treeDatas["rightOperTree"] != null) {
							rightOperTree.removeChildNodes(rootNodeTmp);
							rightOperTree.checkAllNodes(false);
							rightOperTree.addNodes(rootNodeTmp,
									treeDatas["rightOperTree"]);
							rightOperTree.updateNode(rootNodeTmp);
							rightOperTree.expandAll(true);

							return;
						}
					}
					rightOperTree.removeChildNodes(rootNodeTmp);
					rightOperTree.checkAllNodes(false);
					rightOperTree.expandAll(true);
				}
			}
		}, this);
		$
				.ajax({
					cache : false,
					async : true,
					url : "${ctx}/bione/admin/auth/getAuthResDefTabs.json?d="
							+ new Date().getTime(),
					dataType : 'json',
					type : "post",
					beforeSend : function() {
						BIONE.loading = true;
						BIONE.showLoading("正在加载数据中...");
					},
					complete : function() {
						BIONE.loading = false;
						BIONE.hideLoading();
					},
					success : function(result) {
						var data = null;
						if (result) {
							data = result.Data;
						}
						if (data && data.length > 0) {
							for ( var i = 0; i < data.length; i++) {
								var appendHtml = "<div style='overflow: auto;'><div id='"
										+ data[i].resDefNo
										+ "_Container' style='width: 100%; overflow: auto; clear: both; background-color: #FFFFFF;'><div id='authRes_"
										+ data[i].resDefNo
										+ "' class='ztree' style='font-size: 12; background-color: #FFFFFF; width: 95%''></div></div></div>";
								navtab1.addTabItem({
									tabid : data[i].resDefNo,
									content : appendHtml,
									text : data[i].resName,
									showClose : false
								});
								var subTreeId = "#authRes_" + data[i].resDefNo;

								if (eval($(subTreeId))) {
									//构造资源树	
									var resDefNoTmp = data[i].resDefNo;
									window['resTree_' + data[i].resDefNo] = $.fn.zTree
											.init(
													eval($(subTreeId)),
													{
														check : {
															chkStyle : 'checkbox',
															enable : true,
															chkboxType : {
																"Y" : "ps",
																"N" : "s"
															}
														},
														data : {
															key : {
																name : 'text'
															},
															simpleData : {
																enable : true,
																idKey : "id",
																pIdKey : "upId",
																rootPId : null
															}
														},
														view : {
															selectedMulti : false,
															showLine : true
														},
														callback : {
															onCheck : function(
																	event,
																	treeId,
																	treeNode) {
																var curTabId = navtab1
																		.getSelectedTabItemID();
																var treeObj = $.fn.zTree
																		.getZTreeObj(treeId);
																var treeNodesTmp = treeObj
																		.transformToArray(treeNode);
																if (treeNode.checked) {
																	//若是勾选操作
																	var treeNodes = new Array();
																	for ( var i = 0; i < treeNodesTmp.length; i++) {
																		var treeNodeTmp = treeNodesTmp[i];
																		//先判断所点击节点是否已经在操作树中存在
																		var rightTreeNodeId = curTabId
																				+ "_"
																				+ treeNodeTmp.id;
																		var node = rightOperTree
																				.getNodeByParam(
																						"id",
																						rightTreeNodeId,
																						null);
																		if (node
																				&& node != null) {
																			//若该节点已存在,退出
																			//rightOperTree.selectNode(node,false);
																			continue;
																		}
																		treeNodes
																				.push(treeNodeTmp);
																	}
																	//更新操作树
																	initOperTree(
																			curTabId,
																			treeNodes);
																} else {
																	//若是取消勾选操作
																	for ( var i = 0; i < treeNodesTmp.length; i++) {
																		var node = rightOperTree
																				.getNodeByParam(
																						"id",
																						curTabId
																								+ "_"
																								+ treeNodesTmp[i].id,
																						null);
																		if (node != null) {
																			rightOperTree
																					.removeNode(node);
																		}
																	}
																}
															}
														}
													});

									//构造高度
									var treeContainer = data[i].resDefNo
											+ "_Container";
									if ($("#" + treeContainer) && $("#center")) {
										$("#" + treeContainer).height(
												$("#center").height() - 28);
									}

									window['resTreeInit_' + data[i].resDefNo] = function(
											resObjNo) {
										var data = {
											"resDefNo" : resObjNo
										};
										//var rootTmp = eval('resTree_'+ resObjNo).getNodesByParam("id", '0', null);
										//eval('resTree_'+ resObjNo).removeChildNodes(rootTmp);
										//eval('resTree_'+ resObjNo).removeNode(rootTmp[0]);
										$
												.ajax({
													cache : false,
													async : true,
													url : "${ctx}/bione/admin/auth/getAuthResDefTree.json?d="
															+ new Date()
																	.getTime(),
													dataType : 'json',
													type : "post",
													data : data,
													beforeSend : function() {
														BIONE.loading = true;
														BIONE
																.showLoading("正在加载数据中...");
													},
													complete : function() {
														BIONE.loading = false;
														BIONE.hideLoading();
													},
													success : function(result) {
														if (!result)
															return;
														if (eval('resTree_'
																+ resObjNo)) {
															for ( var i = 0; i < result.length; i++) {
																var r = result[i];
																r.icon = "${ctx}"
																		+ "/"
																		+ r.icon;
															}
															eval(
																	'resTree_'
																			+ resObjNo)
																	.addNodes(
																			null,
																			result,
																			false);
															eval(
																	'resTree_'
																			+ resObjNo)
																	.expandAll(
																			true);
														}
													},
													error : function(result, b) {
														BIONE
																.tip('发现系统错误 <BR>错误码：'
																		+ result.status);
													}
												});
									};
									//追加节点
									if (eval('resTreeInit_' + data[i].resDefNo)) {
										eval('resTreeInit_' + data[i].resDefNo
												+ "('" + data[i].resDefNo
												+ "');");
									}

								}
							}
							//设置默认选中tab为第一个tab
							navtab1.selectTabItem(data[0].resDefNo);
						}
					},
					error : function(result, b) {
						BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
					}
				});

		window['navtab2'] = $("#navtab2").ligerTab();

		//构造操作树
		window['rightOperTree'] = $.fn.zTree.init($("#template_operatorTree"),
				{
					data : {
						keep : {
							parent : true
						},
						key : {
							name : "text"
						},
						simpleData : {
							enable : true,
							idKey : "id",
							pIdKey : "upId",
							rootPId : null
						}
					},
					check : {
						chkStyle : 'checkbox',
						enable : true,
						nocheckInherit : true,
						chkboxType : {
							"Y" : "ps",
							"N" : "ps"
						}
					},
					view : {
						selectedMulti : false,
						showLine : true,
						expandSpeed : ""
					}
				}

				, [ {
					id : "0",
					text : "全部操作",
					icon : res_oper_root_icon
				} ]);

		//构造数据规则树
		window['rightDataRuleTree'] = $.fn.zTree.init(
				$("#template_dataRuleTree"), {
					check : {
						chkStyle : 'checkbox',
						enable : true,
						chkboxType : {
							"Y" : "ps",
							"N" : "ps"
						}
					},
					data : {
						key : {
							name : 'text'
						},
						simpleData : {
							enable : true,
							idKey : "id",
							pIdKey : "upId",
							rootPId : null
						}
					},
					view : {
						selectedMulti : false,
						showLine : true
					}
				}, [ {
					id : "0",
					text : "全部规则",
					icon : res_oper_root_icon
				} ]);

		//临时树，备份缓存使用
		window['treeTmp'] = $.fn.zTree.init($("#treeTmp"), {
			check : {
				chkStyle : 'checkbox',
				enable : true,
				chkboxType : {
					"Y" : "ps",
					"N" : "ps"
				}
			},
			data : {
				key : {
					name : 'text'
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "upId",
					rootPId : null
				}
			},
			view : {
				selectedMulti : false,
				showLine : true
			}
		}, [ {
			id : "0",
			text : "临时树"
		} ]);

		window['authLeftCombobox'] = $("#template_left_combobox")
				.ligerComboBox(
						{
							url : "${ctx}/bione/admin/auth/getAuthObjCombo.json?d="
									+ new Date().getTime(),
							ajaxType : "post",
							labelAligh : "center",
							slide : false,
							onSuccess : function(data) {
								if (data && data.length > 0) {
									//初始化后设置combobox默认值
									authLeftCombobox.selectValue(data[0].id);
									//加载相应授权对象树
									if (leftTree && leftTree.setting) {
										refreshObjDefTree(data[0].id)
									}
								}
							}
						});
		//选择授权对象事件
		authLeftCombobox.bind('beforeSelect', function(value, text) {
			//刷新授权对象树
			if (leftTree) {
				if (!leftTree.getSelectedNodes().length
						|| leftTree.getSelectedNodes().length <= 0) {
					leftComboChange(value, text);
					return true;
				}
			}
			$.ligerDialog.confirm("切换授权对象将丢失当前对象已修改\n资源、操作等信息，是否继续？", function(
					yes) {
				if (yes) {
					authLeftCombobox.selectValue(value);
					leftComboChange(value, text);
					leftTree.cancelSelectedNode(leftTree.getNodeByParam("id",
							'0', null));
					//清空缓存
					permissionTreeDatas = new Array();
				}
			});
			return false;
		}, this);
	})
</script>
<script type="text/javascript">
	$(function() {
		var rightHeight = $("#right").height();
		//treetoolbar高度 为 26
		var $home1Container = $("#home1Container");
		$home1Container.height(rightHeight - 28);
		var $home2Container = $("#home2Container");
		$home2Container.height(rightHeight - 28);
	});
</script>
</head>
<body>
	<div id="template.left.up">授权对象</div>
	<div id="template.left.up.right">
		<input type="text" id="template_left_combobox"></input>
	</div>
	<div id="template.left.up.icon">
		<img src="${ctx}/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.center">
		<div id="navtab1" style="overflow: hidden;"></div>
	</div>
	<div id="template.right">
		<div id="navtab2" style="overflow: hidden;">
			<div tabid="home1" title="操作" lselected="true"
				style="overflow: auto;">
				<div id="ruleToolbar1"
					style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #D6D6D6;"></div>
				<div id="home1Container"
					style="width: 100%; overflow: auto; clear: both; background-color: #FFFFFF;">
					<div id="template_operatorTree" class="ztree"
						style="font-size: 12; background-color: #FFFFFF; width: 95%"></div>
				</div>
			</div>
			<div tabid="home2" title="数据规则" lselected="false"
				style="overflow: auto;">
				<div id="ruleToolbar2"
					style="border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #D6D6D6;"></div>
				<div id="home2Container"
					style="width: 100%; overflow: auto; clear: both; background-color: #FFFFFF;">
					<div id="template_dataRuleTree" class="ztree"
						style="font-size: 12; background-color: #FFFFFF; width: 95%"></div>
				</div>
			</div>
		</div>
	</div>
	<div id="treeTmp"></div>
</body>
</html>