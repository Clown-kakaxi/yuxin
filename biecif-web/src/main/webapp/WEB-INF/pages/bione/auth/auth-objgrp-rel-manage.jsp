<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta name="decorator" content="/template/template5.jsp">
<script type="text/javascript">

	$(function() {
		var auth_obj_root_icon = "${ctx}/images/classics/icons/house.png";

		var objgrpId = "${objgrpId}";
		var logicSysNo = "${logicSysNo}";

		//构建授权对象tab及树
		window['navtab'] = $("#navtab").ligerTab();
		//循环tab构建相应树
		var tabIds = navtab.getTabidList();
		if (tabIds && tabIds.length > 0) {
			for ( var i = 0, l = tabIds.length; i < l; i++) {
				var objDefNo = tabIds[i];
				var treeId = tabIds[i] + "_tree";
				if ($("#" + treeId)) {
					window[treeId] = $.fn.zTree.init($("#" + treeId), {
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
							showLine : true
						},
						check : {
							chkStyle : 'checkbox',
							enable : true,
							chkboxType : {
								"Y" : "",
								"N" : ""
							}
						}
					}, [ {
						id : "0",
						text : "授权对象树",
						icon : auth_obj_root_icon,
						nocheck : true
					} ]);
					//初始化树
					initAuthObjTree(objDefNo, treeId);
				}
			}
		}

		//初始化按钮
		var btns = [{
			text : '取消',
			onclick : function() {
				BIONE.closeDialog("objgrpModifyWin");
			}
		},{
			text : '保存',
			onclick : saveRels
		}];
		BIONE.addFormButtons(btns);
		
		

		//初始化授权对象树结构方法
		function initAuthObjTree(objDefNo, treeId) {
			$.ajax({
				cache : false,
				async : true,
				url : "${ctx}/bione/admin/objgrpRelManage/getAuthObjTree.json",
				dataType : 'json',
				data : {
					objDefNo : objDefNo
				},
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
					if (!result)
						return;
					var curTree = eval(treeId);
					if (curTree) {
						for ( var i = 0, l = result.length; i < l; i++) {
							if (result[i].id != "0") {
								result[i].objId = result[i].params.id;
								if (result[i].params.cantClick) {
									result[i].nocheck = true;
								}
							}
						}
						curTree.addNodes(curTree.getNodeByParam("id", "0", null),
								result, false);
						curTree.expandAll(true);
					}

					//后台查询已存在关系并初始化树数据
					initTreeData(objDefNo);
				},
				error : function(result, b) {
					BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
				}
			});
		}

		//初始化授权对象树数据方法
		function initTreeData(objDefNo) {
			$
					.ajax({
						type : "POST",
						url : "${ctx}/bione/admin/objgrpRelManage/findRelByAuthObjgrpAndAuthObj.json",
						data : {
							logicSysNo : logicSysNo,
							objgrpId : objgrpId,
							objDefNo : objDefNo
						},
						success : function(rels) {
							if (!rels)
								return;
							//得到tree
							var treeObj = $.fn.zTree
									.getZTreeObj(objDefNo + "_tree");
							for ( var i = 0, l = rels.length; i < l; i++) {
								var node = treeObj.getNodeByParam("objId",
										rels[i].id.objId);
								if (!node)
									continue;
								treeObj.checkNode(node, true, true);
							}

						}
					});
		}

		//保存维护结果
		function saveRels() {
			if (!navtab)
				return;
			//最终集合
			var rels = [];
			var tabIds = navtab.getTabidList();
			for ( var i = 0, l = tabIds.length; i < l; i++) {
				var objDefNo = tabIds[i];
				//得到tree
				var treeId = objDefNo + "_tree";
				var treeObj = $.fn.zTree.getZTreeObj(treeId);

				var selObjs = treeObj.getCheckedNodes(true);
				for ( var j = 0, ll = selObjs.length; j < ll; j++) {
					var obj = {
						id : {
							logicSysNo : logicSysNo,
							objDefNo : objDefNo,
							objgrpId : objgrpId,
							objId : selObjs[j].objId
						}
					};
					rels[rels.length] = obj;
				}
			}
			var relsJson = '{relsJson:' + JSON2.stringify(rels) + '}';
			$.ajax({
				type : "POST",
				url : "${ctx}/bione/admin/objgrpRelManage/saveObjgrpRels",
				data : {
					logicSysNo : logicSysNo,
					relsJson : relsJson,
					objgrpId : objgrpId
				},
				success : function() {
					BIONE.closeDialog("objgrpModifyWin", "保存成功！");
				}
			});
		}

	});

</script>
<script type="text/javascript">
	$(function() {
		var centerHeight = $("#center").height();

		//treetoolbar高度 为 26
		var $container1 = $("#container1");
		$container1.height(centerHeight - 32 );
		var $container2 = $("#container2");
		$container2.height(centerHeight - 32 );
		var $container3 = $("#container3");
		$container3.height(centerHeight - 32 );
	});
</script>
</head>
<body>
	<div id="template.center">
		<div id="navtab" style="overflow: hidden;">
			<div tabid="AUTH_OBJ_ROLE" title="角色" lselected="true"
				style="overflow: auto;">
				<div id="container1"
					style="width: 98%; overflow: auto; clear: both;">
					<div id="AUTH_OBJ_ROLE_tree" class="ztree"
						style="font-size: 12; background-color: F1F1F1; width: 95%"></div>
				</div>
			</div>
			<div tabid="AUTH_OBJ_DEPT" title="部门" lselected="false"
				style="overflow: auto;">
				<div id="container2"
					style="width: 98%; overflow: auto; clear: both;">
					<div id="AUTH_OBJ_DEPT_tree" class="ztree"
						style="font-size: 12; background-color: F1F1F1; width: 95%"></div>
				</div>
			</div>
			<div tabid="AUTH_OBJ_ORG" title="机构" lselected="true"
				style="overflow: auto;">
				<div id="container3"
					style="width: 98%; overflow: auto; clear: both;">
					<div id="AUTH_OBJ_ORG_tree" class="ztree"
						style="font-size: 12; background-color: F1F1F1; width: 95%"></div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>