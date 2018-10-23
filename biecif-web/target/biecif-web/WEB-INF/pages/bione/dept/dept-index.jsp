<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
<%@ include file="/common/meta.jsp"%>
<meta name="decorator" content="/template/template2.jsp">
<script type="text/javascript">
	var items, currentNode,grid;
	
	$(function(){
		initTree();
		searchForm();
		initGrid();
		initButtons();
		BIONE.addSearchButtons("#search", grid, "#searchbtn");
		$("#search input[name=orgName]").attr("readonly", "readonly");
		$("#search input[name=orgNo]").attr("readonly", "readonly");
	});
	
	function initTree() {
		window['orgTreeInfo'] = $.fn.zTree.init($("#tree"),{
			async:{
				enable : true,
				contentType : "application/json",
				url : "${ctx}/bione/admin/org/list.json",
				dataType : "json",
				type : "get"
			},
			data:{
				key:{
					name:"text"
				}
			},
			view:{
				selectedMulti: false,
				showLine: false
			},
			callback:{
				onClick : function(event,treeId,treeNode){
					currentNode = treeNode;
					if(currentNode.id=="0"){
						return;
					}
					grid.set('parms', {
						orgId : currentNode.data.orgId,
						orgNo : currentNode.data.orgNo
					});
					$("#search input[name=deptNo]").val("");
					$("#search input[name=deptName]").val("");
					$("#search input[name=orgId]").val(currentNode.data.orgId);
					$("#search input[name=orgNo]").val(currentNode.data.orgNo);
					grid.loadData();
				}
			}
		});
	}
	
	function searchForm() {
		$("#search").ligerForm({
			fields:[{
				display:'部门标识',
				name : "deptNo",
				newline : true,
				type : "text",
				cssClass : "field",
				attr : {
					field:'deptNo',
					op : "="
				}
			},{
				display:'部门名称',
				name:'deptName',
				newline:false,
				type : "text",
				cssClass : "field",
				attr : {
					field:'deptName',
					op : "like"
				}
			},{
				name:'orgNo',
				type:'hidden',
				attr:{
					field:'orgNo',
					op : "="
				}
			}]
		});
	}

	function initGrid() {
		grid = $("#maingrid").ligerGrid({
			//height:"100%",
			width:"100%",
			columns : [{
				display : '部门标识',
				name : 'deptNo',
				id:'deptNo',
				width : '46%',
				align : 'left'
			}, {
				display : '部门名称',
				name : 'deptName',
				width : '50%',
				align : 'left'
			} ],
			usePager : true,
			checkbox : false,
			tree: { columnId: 'deptNo' },
			dataAction : 'server', 	//从后台获取数据
			usePager : false, 		//服务器分页
			alternatingRow : true, 	//附加奇偶行效果行
			colDraggable : true,
			url : "${ctx}/bione/admin/dept/findDeptInfoByOrg.json?d=" + new Date().getTime(),
			method : 'post', // get
			sortName : 'deptNo',	//第一次默认排序的字段
			sortOrder : 'asc', 		//排序的方式
			rownumbers : true,
			toolbar : {}
		});
		
	}

	function initButtons() {
		if("${canEdit}" == "0") {
			initToolbar_1();
		} else {
			initToolbar_2();
		}
	}
	
	function initToolbar_1() {
		items = [ {
 			text : '查看',
 			click : function(){
 				var selectedDept=grid.getSelectedRow();
 				if(!selectedDept){
 					BIONE.tip("请先选择要查看的部门");
 					return;
 				}
 				BIONE.commonOpenLargeDialog("部门信息", "deptModifyWin", "${ctx}/bione/admin/dept/"+selectedDept.deptId+"/edit?isBasicDept=${isBasicDept}&flag=scan");
 			},
 			icon : 'calendar'
 		} ];
 		BIONE.loadToolbar(grid, items, function() { });
	}
	
	function initToolbar_2() {
		items = [ {
			text : '增加',
			click : function(){
				if(!currentNode){
					BIONE.tip("请先选择机构");
					return;
				}
				if(currentNode.id=="0"){
					BIONE.tip("请先选择机构");
					return;
				}
				var selectedDept=grid.getSelectedRow ();
				var deptId;
				if(!selectedDept){
					deptId='';
					BIONE.commonOpenLargeDialog("部门添加", "deptAddWin", "${ctx}/bione/admin/dept/new?orgId="+currentNode.data.orgId+"&deptId="+deptId);
				}else{
					deptId=selectedDept.deptId;
					BIONE.commonOpenLargeDialog("部门修改", "deptAddWin", "${ctx}/bione/admin/dept/new?orgId="+currentNode.data.orgId+"&deptId="+deptId);
				}
				
			},
			icon : 'add'
		}, {
			text : '修改',
			click : function(){
				var selectedDept=grid.getSelectedRow ();
				if(!selectedDept){
					BIONE.tip("请先选择需要修改的部门");
					return;
				}
				BIONE.commonOpenLargeDialog("部门修改", "deptModifyWin", "${ctx}/bione/admin/dept/"+selectedDept.deptId+"/edit");
			},
			icon : 'modify'
		}, {
			text : '删除',
			click : f_delete,
			icon : 'delete'
		} ];
		BIONE.loadToolbar(grid, items, function() { });
	}
	
	var str="";
	function getDeptIdAndChildIdByInfo(rowInfo){
		 if(rowInfo==1){
			 str="";
		 }
		var children=grid.getChildren(rowInfo);
		str+=rowInfo.deptId+",";
		if(children){
			for(var i=0;i<children.length;i++){
				getDeptIdAndChildIdByInfo(children[i]);
			}
		}
	}
	function f_delete() {
		var selectedRow = grid.getSelecteds();
		if(selectedRow.length == 0){
			BIONE.tip('请选择行');
			return;
		}
		for(var k=0;k<selectedRow.length;k++){
			getDeptIdAndChildIdByInfo(selectedRow[k]);	
		}
		
		$.ligerDialog.confirm('确实要删除这些记录及其子记录吗!', function(yes) {
			if(yes){
				BIONE.ajax({
				type : "DELETE",
				url : '${ctx}/bione/admin/dept/' + str,
				dataType : "scriptType"
			},function(){
				BIONE.tip('删除成功');
				str="";
				grid.loadData();
			});
				}else{
				BIONE.tip("取消删除");
			}
			
		//	var length=selectedRow.length;
		//	for ( var i = 0; i < length; i++) {
		//		if(yes){
		//		var children=grid.getChildren(selectedRow[i]);
		//		if(children){
		//			for(var j=0;j<children.length;j++){
		//				$.ajax({
		//					type : "DELETE",
		//					url : '${ctx}/bione/admin/dept/' + children[j].deptId,
		//					dataType : "scriptType"
		//				});
		//			}		
		//		}
				
		//		$.ajax({
		//				type : "DELETE",
		//				url : '${ctx}/bione/admin/dept/' + selectedRow[i].deptId,
		//				dataType : "scriptType"
		//			});
		//		}
		//	}
		//	if(yes){
		//		BIONE.tip('删除成功');
		//		grid.loadData();
		//	}
		//	if(!yes){
		//		alert(str);
		//		str="";
		//	}
		});
	}
	
</script>

<title>部门管理</title>
</head>
<body>
	<div id="template.left.up.icon">
		<img src="${ctx }/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.left.up">
		<span style="font-size: 12">机构信息</span>
	</div>
</body>
</html>