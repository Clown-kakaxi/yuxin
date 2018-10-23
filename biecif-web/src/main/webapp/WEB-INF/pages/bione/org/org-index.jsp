<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template3.jsp">
<head>
<script type="text/javascript">
	var isStart = eval('(${isStart})');
	var currentNode;
	var tabObj;
	var leftTreeObj;
	$(function() {
		$("#mainformdiv").ligerTab({
			contextmenu:true
		});
		tabObj = $("#mainformdiv").ligerGetTabManager();
		var setting={
				data : {
				    key : {
					name : "text"
				    }
				},
				view : {
				    selectedMulti : false,
				    showLine : false
				},
				callback : {
				    onClick : f_clickNode
				}
		};
		
		leftTreeObj = $.fn.zTree.init($("#tree"),setting);
		//加载数据
		loadTree("${ctx}/bione/admin/org/list.json?d=" + new Date().getTime(), leftTreeObj);
		
	    if (isStart) {
			$("#treeToolbar").ligerTreeToolBar({
			    items : [{
					icon : 'add',
					text : '新建',
					click :addOrg
			    },{
			    	icon:'delete',
			    	text:'删除',
			    	click:deleteOrg
			    }]
			});
	    }
	    
	    function deleteOrg(){
	    	if(!currentNode){
	    		BIONE.tip("请选择机构");
	    		return;
	    	}
	    	if (!currentNode.children || currentNode.children.length == 0) {
				    $.ligerDialog.confirm('确认删除', '是否确认删除',
				    		function(yes) {
				    	if (yes) {
				    		debugger;
						    var id = currentNode.data.orgId;
						    if (id != null) {
								BIONE.ajax({
								    type : 'DELETE',
								    url : '${ctx}/bione/admin/org/'
									    + id,
								    dataType : 'scriptType'
								}, function() {
									loadTree("${ctx}/bione/admin/org/list.json?d="+new Date().getTime(), leftTreeObj);
									tabObj.removeTabItem("orgTab");
									$(".l-dialog-btn").remove();
									 currentNode=null;
								});
					    	}
						}
				    });
				   
				} else {
				    BIONE.tip("当前节点含有子节点，请将子节点删除后再进行删除");
				    return;
				}
	    	
	    }
	    
		function addOrg(){
			var id,orgName;
			if(!currentNode){
				id="0";
				orgName="";
			}else if(currentNode.id=="0"){
				id=currentNode.id;
				orgName=currentNode.text;				
			}else{
				id = currentNode.data.orgNo;
				orgName = currentNode.data.orgName;
			}
			var tabUri="${ctx}/bione/admin/org/new?orgNo="+id+"&upName="+orgName;
			var buttons=[];
		
			buttons.push({
				text:'取消',
				onclick:function(){
					tabObj.removeTabItem("orgTab");
					$(".l-dialog-btn").remove();
				}
			});
			
			buttons.push({
				text:'保存',
				onclick:saveOrg
			});
			newOrgInfo(tabUri,buttons);
		}
		
		function saveOrg(){
			document.getElementById("orgTab").contentWindow.f_save();
			currentNode=null;
		}
		
		
		function f_clickNode(event, treeId, treeNode) {
			currentNode=treeNode;
			if(currentNode.id=="0"){
				return;
			}
			var id = treeNode.data.orgId;
			var parentNode=treeNode.getParentNode();
			var orgNo=null;
			var orgName=null;
			if(parentNode){
				orgNo = parentNode.data.orgNo;
				orgName = parentNode.data.orgName;
			}
			var tabUri="${ctx}/bione/admin/org/"+id+"/edit?orgNo="+orgNo+"&upName="+orgName;
			var buttons=[];
			buttons.push({
				text:'取消',
				onclick:function(){
					tabObj.removeTabItem("orgTab");
					$(".l-dialog-btn").remove();
				}
			});
			
			buttons.push({
				text:'修改',
				onclick:saveOrg
			});
			newOrgInfo(tabUri,buttons); 
		}
	
		function newOrgInfo(tabUri,buttons){
			var curTabUri = tabUri + "&isStart=" + isStart;
			height = $("#mainformdiv").height() - 30;
			tabObj.removeTabItem("orgTab");
			tabObj.addTabItem ({
				tabid:"orgTab",
				text:"机构基本信息",
				showClose:false,
				content:'<iframe frameborder="0" id="orgTab" style="height:'+height+'px;" src='+curTabUri+' ></iframe>'
			});
			tabObj.selectTabItem("orgTab");
			if(isStart){
				$(".l-dialog-btn").remove();
				BIONE.addFormButtons(buttons);
			}
		} 
	});
	function saveSuccess(){
		tabObj.removeTabItem("orgTab");
		$(".l-dialog-btn").remove();
		loadTree("${ctx}/bione/admin/org/list.json?d=" + new Date().getTime(), leftTreeObj);
	}
	
	function loadTree(url, component, data, nodeId, parentNodeId) {
		$.ajax({
			cache : false,
			async : true,
			url : url,
			dataType : 'json',
			data : data,
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
				var nodes = component.getNodes();
				var num = nodes.length;
				for ( var i = 0; i < num; i++) {
					component.removeNode(nodes[0], false);
				}
				if (result.length > 0) {
					component.addNodes(null, result, false);
					if (nodeId) {
						var currNode = component.getNodeByParam("id", nodeId,
								null);
						var parentNode = component.getNodeByParam("id",
								parentNodeId, null);

						if (!currNode && parentNode) {
							component.expandNode(parentNode, true, true, true);
							component.selectNode(parentNode);
						} else {
							component.selectNode(currNode);
						}
					} else {
						component.expandAll(false);
					}
				}
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	} 
</script>

<title>机构管理</title>
</head>
<body>
	<div id="template.left.up.icon">
		<img src="${ctx }/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.left.up">
		<span style="font-size: 12">机构信息</span>
	</div>
	<div id="template.right"></div>
</body>
</html>