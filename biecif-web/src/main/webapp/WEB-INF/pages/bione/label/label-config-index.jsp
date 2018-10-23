<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template2.jsp">
<head>
<script type="text/javascript">
	var root = {
		id: 'root',
		icon: '/bicustrisk-web/images/...cs/icons/tag_yellow.png',
		params: {
			type: 'root'
		}, 
		text: '全部', 
		isChecked: false, 
		isexpand: true, 
		isParent: true
	};
	var grid, form, tree;
	var type = "root";
	var item = {'root': {'realId': 'labelObjId','form': [{
		display : '标识',
		name : "objNo",
		newline : true,
		type : "text",
		attr : {
			field : 'labelObjNo',
			op : "="
		}
	}, {
		display : '名称',
		name : "objName",
		newline : false,
		type : "text",
		attr : {
			field : 'labelObjName',
			op : "like"
		}
	}], 'grid': {'uri': '${ctx}/bione/label/labelConfig/list.json?type=root', 'field': [{
		display : '标识',
		name : 'labelObjNo',
		width : "30%",
		align : 'left'
	}, {
		display : '名称',
		name : 'labelObjName',
		width : "30%",
		align : 'left'
	}, {
		display : '备注',
		name : 'remark',
		width : "30%",
		align : 'left'
	}]}}, 'obj': {'realId': 'typeId','form': [{
		display : '类型名称',
		name : "name",
		newline : true,
		type : "text",
		attr : {
			field : 't.typeName',
			op : "like"
		}
	}], 'grid': {'uri': '', 'field': [{
		display : '名称',
		name : 'typeName',
		width : "45%",
		align : 'left'
	}, {
		display : '备注',
		name : 'remark',
		width : "45%",
		align : 'left'
	}]}}, 'type': {'realId': 'labelId','form': [{
		display : '标签名称',
		name : "name",
		newline : true,
		type : "text",
		attr : {
			field : 'labelName',
			op : "like"
		}
	}], 'grid': {'uri': '', 'field': [{
		display : '名称',
		name : 'labelName',
		width : "45%",
		align : 'left'
	}, {
		display : '备注',
		name : 'remark',
		width : "45%",
		align : 'left'
	}]}}};
	$(function() {
		$("#treeToolbar").remove();
		flushWin('root');
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
		initTree();
// 		$("#treeContainer").height($("#left").height() - 21);
		BIONE.loadToolbar(grid, [{
			text : '增加',
			click : f_add,
			icon : 'add'
		}, {
			text : '修改',
			click : f_modify,
			icon : 'modify'
		}, {
			text : '删除',
			click : f_delete,
			icon : 'delete'
		}]);
	});
  
	function searchForm(fields) {
		ligerForm(fields);
	}
	var flag = true;
  	function initGrid(uri, fields, toolBars) {
	  grid = $("#maingrid").ligerGrid({
		    url : uri,
		    columns : fields,
			width:"100%",
			dataAction : 'server', //从后台获取数据
			usePager : true, //服务器分页
			alternatingRow : true, //附加奇偶行效果行
			colDraggable : true,
			method : 'get',
			pageParmName : 'page',
			pagesizeParmName : 'pagesize',
			rownumbers : true,
			toolbar : {}
		});
	}
  	
  	function initTree() {
  		tree = $.fn.zTree.init($("#tree"), {
  			async : {
			    dataFilter : function(treeId, parentNode, childNodes) {
			    	if(childNodes) {
			    		$.each(childNodes, function(i, n) {
			    			childNodes[i].type = n.params.type;
			    			childNodes[i].realId = n.params.realId;
			    		});
			    	}
			    	return childNodes;
			    },
			    contentType: "application/json",
			    dataType : "json",
			    enable : true,
			    type : "get",
			    autoParam : ['realId', 'type'],
			    url : "${ctx}/bione/label/labelConfig/treeNode.json"
			},
			data:{
				keep:{
					parent : true
				},
				key : {
					name : "text"
				}
			},
			callback: {
				onClick: function(event, treeId, treeNode) {
					if (type == treeNode.type) {
						grid.set('url', '${ctx}/bione/label/labelConfig/list.json?' + (treeNode.realId == null ? "" : 'realId=' +treeNode.realId + '&') + 'type=' + treeNode.type);
						grid.loadData();
					} else {
						type = treeNode.type;
						grid.set('parms', {
							condition : ''
						});
						grid.set('newPage', 1);
						item[treeNode.type].grid.uri = '${ctx}/bione/label/labelConfig/list.json?' + (treeNode.realId == null ? "" : 'realId=' +treeNode.realId + '&') + 'type=' + treeNode.type;
						flushWin(treeNode.type);
					}
			    },
				onAsyncSuccess: function(event, treeId, treeNode, msg) {
					if (treeNode == null) {
						var nodes = tree.getNodes();
						tree.reAsyncChildNodes(nodes[0], "refresh");
					}
				}
			}
  		});
  	}
  	
  	function flushWin(itemName) {
  		var entity = item[itemName];
  		clear();
  		searchForm(entity.form);
  		initGrid(entity.grid.uri, entity.grid.field);
  	}
  	
  	
  	function f_add(){
  		var node = tree.getSelectedNodes()[0];
  		if (node == null) {
			node = tree.getNodeByParam("id", "root");
		}
  		BIONE.commonOpenLargeDialog("添加", "dialog",
  				"${ctx}/bione/label/labelConfig/new?type=" + node.type + (node.realId == null ? "" : "&realId=" + node.realId ), f_beforeClose);
  	}
  	function f_modify(){
  		var node = tree.getSelectedNodes()[0];
  		if (node == null) {
			node = tree.getNodeByParam("id", "root");
		}
  		var realId = item[node.type].realId;
  		var rows = grid.getCheckedRows();
  		if(rows && rows.length != 1) {
  			BIONE.tip("请选择一条数据！");
  			return;
  		}
  		var id = rows[0][realId];
  		BIONE.commonOpenLargeDialog("修改", "dialog",
  				"${ctx}/bione/label/labelConfig/" + id + "/edit?type=" + node.type, f_beforeClose);
  	}
  	function f_delete(t){
  		var rows = grid.getCheckedRows();
  		if (rows.length < 1) {
  			BIONE.tip("请选择要删除的数据！");
  			return;
  		}
  		$.ligerDialog.confirm("确定删除" + rows.length + "条数据？", function(flag) {
  			var node = tree.getSelectedNodes()[0];
  			if (node == null) {
  				node = tree.getNodeByParam("id", "root");
  			}
  			var realId = item[node.type].realId;
  			if (flag) {
  				var ids = [];
  	  			$.each(rows, function(i, n) {
  	  	  			ids.push(n[realId]);
  	  	  		});
  	  	  		var del = JSON2.stringify(ids);
  				$.ajax({
					type : "DELETE",
					url : '${ctx}/bione/label/labelConfig/'+ node.type +'/' + del,
					dataType : "json",
					success : function() {
						type = t;
						BIONE.tip("删除成功！");
						f_beforeClose();
					},
					error: function() {
						BIONE.tip("删除失败！");
					}
				});
  			}
  		});
  	}
  	
  	function f_beforeClose() {
  		grid.loadData();
  		var node = tree.getSelectedNodes()[0];
		if (node == null) {
			node = tree.getNodeByParam("id", "root");
		}
		if (node.params.type != "type") {
			if (node.isParent == false) {
				node.isParent = true;
			}
			tree.reAsyncChildNodes(node, "refresh");
		}
  	}
    //清空管理器ID
	function clear()
    {
        var managers = $.ligerui.find($.ligerui.controls.Input);
        if(managers.length){
        	for (var i = 0, l = managers.length; i < l; i++)
        	{
           		if(managers[i].id){
            		$.ligerui.remove(managers[i].id);
            	}
       		}
        }
    }
    

	//渲染表单
	function ligerForm(fields) {
		var formManagers = $.ligerui.find($.ligerui.controls.Form);
		if(formManagers.length && formManagers.length > 0){
			for(var i = 0 ; i < formManagers.length ; i++){
				if(formManagers[i].id = "search"){
					//删除旧dom
					$("#search").children().remove();
					//更新manager
					formManagers[i].options.fields = fields;
					formManagers[i]._render();
					var managers = $.ligerui.find($.ligerui.controls.Input);
					for ( var i = 0, l = managers.length; i < l; i++) {
						// 改变了表单的值，需要调用这个方法来更新ligerui样式
						var o = managers[i];
						o.updateStyle();
						if (managers[i] instanceof $.ligerui.controls.TextBox)
							o.checkValue();
					}
				}
			}
		}else{
			mainform = 
			$("#search").ligerForm({
				fields : fields
			});
		}
	}
	
	function f_exmaple() {
		var node = tree.getSelectedNodes()[0];
		if (node.type != "obj") {
			BIONE.tip("请选择标签对象节点");
			return;
		}
		BIONE.commonOpenLargeDialog("exmaple", "frame",
				"${ctx}/bione/label/apply/exmaple?objId=" + (node.realId != null ? node.realId : "") + "&labelObjId=" + (node.realId != null ? node.realId : ""));
	}
	
	function f_test() {
		var node = tree.getSelectedNodes()[0];
		if (node.type != "obj") {
			BIONE.tip("请选择标签对象节点");
			return;
		}
		BIONE.commonOpenLargeDialog("test", "frame",
				"${ctx}/bione/label/apply/test?labelObjId=" + (node.realId != null ? node.realId : ""));
	}
	
	function f_getId() {
		var node = tree.getSelectedNodes()[0];
		if (node.type != "obj") {
			BIONE.tip("请选择标签对象节点");
			return;
		} else {
			return node.realId;
		}
	}
</script>
</head>
<body>
  <div id="template.left.up.icon">
	<img src="${ctx}/images/classics/icons/application_side_tree.png" />
  </div>
  <div id="template.left.up">
	<span style="font-size: 12">标签</span>
  </div>
</body>
</html>