<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template21.jsp">
<head>
<script type="text/javascript">
	var leftTreeObj;
	var dialog;
	var tabObj;
	var isLoad = null;
	var gridCenter;
	var custId = "${custId}";
	var queryName = "${queryName}";

	$(function() {

		gridCenter = $(document).height() - 38;

		$("#tab").ligerTab({
			contextmenu : true
		});

		tabObj = $("#tab").ligerGetTabManager();

		var setting = {
			async : {
				autoParam : [ "id", "level", "text" ],
				enable : true,
				contentType : "application/json",
				url : "${ctx}/ecif/orginfo/gettree",
				dataType : "json",
				type : "get",
				dataFilter : function filter(treeId, parentNode, childNodes) {
					if (childNodes) {
						for ( var i = 0; i < childNodes.length; i++) {
							childNodes[i].isParent = childNodes[i].isParent;
						}
					}
					return childNodes;
				}
			},
			data : {
				key : {
					name : "text"
				},
				simpleData : {
					enable : false,
					idKey : "id",
					pIdKey : "upId"

				}
			},
			view : {
				selectedMulti : false
			},
			callback : {
				onClick : zTreeOnClick
			}
		};

		$(function() {
			leftTreeObj = $.fn.zTree.init($("#tree"), setting);
		});

		function zTreeOnClick(event, treeId, treeNode) {
			var level = treeNode.params.level;
			if (level == "0") {
				src = "";
			} else if (level == "1") {
				//addTbs(treeNode);
			} else if (level == "2") {
				addOneTabs(treeNode);
			}
		}
		
		function addTbs(treeNode){
			var id = treeNode.id;
			var level = treeNode.params.level;
			var text = treeNode.text;
			$.ajax({
				cache : false,
				async : true,
				url : "${ctx}/ecif/perinfo/gettree.json",
				type : "get",
				data : {
					id : id,
					level : level,
					text : text
				},
				beforeSend : function() {
					BIONE.loading = true;
					BIONE.showLoading("正在加载数据中...");
				},
				complete : function(XMLHttpRequest) {
					BIONE.loading = false;
					BIONE.hideLoading();				
					BIONE.isSessionAlive(XMLHttpRequest);
				},
				success : function(result) {
					var $centerDom = $(document);
					height = $centerDom.height() - 38;
					var tabIdfirst = "";
					for(var i = 0; i < result.length; i ++){
						var tabPageId = result[i].id+"";
						var tabPageName = result[i].text+"";
						var tabUrl = "${ctx}"+result[i].params.tableUrl + "?custId="+custId+"&URL="+result[i].params.URL;
						if(i == 0){
							tabIdfirst = result[i].id;
						}
						tabObj.addTabItem({
							tabid : tabPageId,
							text : tabPageName,
							content : '<iframe frameborder=0 id='+tabPageId+' style="height:'+height+ 'px;" src='+tabUrl+'></iframe>'
						});
					}
					tabObj.selectTabItem(tabIdfirst);
				},
				error : function(result, b) {
					BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
				}
			});
		}
		
		function addOneTabs(treeNode) {
			var tabId = treeNode.id;
			var tabName = treeNode.text;
			var tabUrl = "${ctx}"+treeNode.params.tableUrl + "?custId="+custId+"&URL="+treeNode.params.URL;
			//prompt('',tabUrl);
			var $centerDom = $(document);
			height = $centerDom.height() - 38;
			tabObj.addTabItem({
				tabid : tabId,
				text : tabName,
				content : '<iframe frameborder="0" id="'+tabId+'" style="height:'+height+ 'px;" src="'+tabUrl+'"></iframe>'
			});
		}
		
		var buttons = [];
		
		buttons.push({
			text:'返回',
			onclick:function(){
				var rule = '{"rules":[';
				var tabTag = "${tabTag}";
				var custNo = "${custNo}";
				var existsRule = "false";
				if(custNo != null && custNo.length != 0){
					rule = rule + '{"field":"custNo", "value":"'+custNo+'"},';
					existsRule = "true";
				}
				var name = "${name}";
				if(name != null && name.length != 0){
					rule = rule + '{"field":"name", "value":"'+name+'"},';
					existsRule = "true";
				}
				var ebankRegNo = "${ebankRegNo}";
				if(ebankRegNo != null && ebankRegNo.length != 0){
					rule = rule + '{"field":"ebankRegNo", "value":"'+ebankRegNo+'"},';
					existsRule = "true";
				}
				var identType = "${identType}";
				if(identType != null && identType.length != 0){
					rule = rule + '{"field":"identType", "value":"'+identType+'"},';
					existsRule = "true";
				}
				var identNo = "${identNo}";
				if(identNo != null && identNo.length != 0){
					rule = rule + '{"field":"identNo", "value":"'+identNo+'"},';
					existsRule = "true";
				}
				var productType = "${productType}";
				if(productType != null && productType.length != 0){
					rule = rule + '{"field":"productType", "value":"'+productType+'"},';
					existsRule = "true";
				}
				var productNo = "${productNo}";
				if(productNo != null && productNo.length != 0){
					rule = rule + '{"field":"productNo", "value":"'+productNo+'"},';
					existsRule = "true";
				}
				var Contmeth = "${Contmeth}";
				if(Contmeth != null && Contmeth.length != 0){
					rule = rule + '{"field":"Contmeth", "value":"'+Contmeth+'"},';
					existsRule = "true";
				}
				var Address = "${Address}";
				if(Address != null && Address.length != 0){
					rule = rule + '{"field":"Address", "value":"'+Address+'"},';
					existsRule = "true";
				}
				rule = rule + ']}';
				if(existsRule == "false"){
					rule = "";
				}
				var pageName = "${pageName}";
				window.location.href = "${ctx}/ecif/orginfo/detailedreturn?tabTag="+ tabTag + "&rule="+rule+"&pageName="+pageName;
			}
		});
		BIONE.addFormButtons(buttons);
		if(queryName.length > 9){
			var reg = /^[a-z\d]+$/;
			
			if(reg.test(queryName)){
				if(queryName.length > 16){
					document.getElementById("custName").innerHTML =  "【"+queryName.substring(0, 16) +"..."+"】";
				} else {
					document.getElementById("custName").innerHTML = "【"+queryName+"】";
				}
			} else {
				if(queryName.length > 16 && reg.test(queryName.substring(0, 16))){
					document.getElementById("custName").innerHTML =  "【"+queryName.substring(0, 16) +"..."+"】";
				}else{
					document.getElementById("custName").innerHTML =  "【"+queryName.substring(0, 8) +"..."+"】";
				}
			}
		} else {
			document.getElementById("custName").innerHTML = "【"+queryName+"】";
		}
		$("#custName").ligerTip({ width: 500 });
	});
	function closeTabs(tabId) {
		tabObj.removeTabItem(tabId);
	}
</script>
</head>
<body>
	<div id="template.left.up.icon">
		<img src="${ctx}/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.left.up">
		<span style="font-size: 12"><div id="custName" onmouseover="" onmouseout="" title="${queryName}" style="float: left"></div> <div style="float: left">详细信息</div></span>
	</div>	
</body>
</html>