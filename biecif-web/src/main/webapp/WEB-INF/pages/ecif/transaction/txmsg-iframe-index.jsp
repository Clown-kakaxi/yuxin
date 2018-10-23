<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<meta name="decorator" content="/template/template31.jsp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	var zTreeObj;
	var rootId;
	var msgId = null;
	var rootName;
	var currentNode = null;
	var templateTab ;
	var rightinited = 0;
	
	var gridTable,gridTable2,gridTable3,gridTable4;
	
    var groupicon = "${ctx}/images/classics/icons/communication.gif";
	var buttons = [];
	
	var tabdefData = [];
	var tableData = [];
	var colData = [];	
	var col1Data = [];
	var col2Data = [];
	
	var filterData = [];
	var filterConData = [];

/* 	buttons.push({
		text : '关闭',
		onclick : closeCallBack
	});
	buttons.push({
		text : '保存',
		onclick : savedCallback
	});
	 */
	$(init);
	
	function init() {
		initRoot();
		beforeTree();
		initTree();
		initMenubar();
		
		//初始化右侧tab
		//initTab();
		//initBtns();
			
	}
	
	function initRoot() {
		
		rootId = "${id}";
		if(rootId.length != 0) {
			$.get('${ctx}/ecif/transaction/txdef/'+rootId, {}, function(returnedData, status) {
				rootName = returnedData.txName;
				$("#treeTitle").text(rootName);
			});
		}
	}
	
	//构造右侧tab
	function initTab(node){
		
		$('#tab').ligerTab({
			onBeforeSelectTabItem : f_selectTab
		});
		
		templateTab = $("#tab").ligerGetTabManager();

		//debugger;
		
		initForms();	
		initGrid_tables(node);
		initGrid_tablesrel(node);
		
		initGrid_filters(node);
		initGrid_nodeattrs(node);
		
		//document.getElementById("maingrid1").parentNode.style.display = 'none';
		//document.getElementById("maingrid1").parentNode.disabled = true;
		//var t2 = templateTab.removeTabItem('styles');

		
		//第一次点击树节点时加载按钮
		if(rightinited==1){
			initBtns();		
			initToolBar_tables();
			initToolBar_tablesrel();
			initToolBar_tablesfilter();
			initToolBar_nodeattrs();

		}
		
		if(rightinited==1){
			initGrid_nodeattrs(node);
		}
	}
	
	function f_addTab(TabId){

	}
	
	function f_selectTab(TabId){
	
		if($('#form_basic').valid() ) {
			 //debugger;
			//return true;
		} else {
			 //debugger;
			return false;
		} 
		
		
		if(TabId=='styles'){
			
			//initGrid_tables();
		}
		
		if(TabId=='styles2'||TabId=='styles3'||TabId=='verify'){
			achieveTableIds();
		}
		
		
/* 		var st = $.ligerui.find($.ligerui.controls.ComboBox);
		$.each(st, function(i, n) {
			if (n.selectBox.is(":visible")) {
				n._toggleSelectBox(true);
			}
		});
*/		

	}	
	
	function initBtns(){
		buttons.push({
			text: '取消',
			onclick: function(){
				BIONE.closeDialog("authResWin");
			}
		});
		buttons.push({
			text: '保存',
			onclick: f_save
		});
		//$(".l-dialog-btn").remove();
		BIONE.addFormButtons(buttons);
	}

	function f_save() {
		
			//封装json
			var msgnodeArray = [];
			var nodeId = $("#form_basic input[name=nodeId]").val();
			var msgId = $("#form_basic input[name=msgId]").val();
			var upNodeId = $("#form_basic input[name=upNodeId]").val();
			var nodeCode = $("#form_basic input[name=nodeCode]").val();
			var nodeName = $("#form_basic input[name=nodeNamexx]").val();
			var nodeTp = $("#form_basic input[name=nodeTp]").val();
			var nodeSeq = $("#form_basic input[name=nodeSeq]").val();
			var nodeGroup = $("#form_basic input[name=nodeGroup]").val();

			msgnodeArray.push({
						nodeId :nodeId,
						msgId : msgId,
						upNodeId : upNodeId,
						nodeCode : nodeCode,
						nodeName : nodeName,
						nodeGroup : nodeGroup,
						nodeSeq :nodeSeq,
						nodeTp : nodeTp 
					});

			var msgnodeObj = {
					msgnodeArray : []
				};
			msgnodeObj.msgnodeArray = msgnodeArray;
			
			var gridData1 = gridTable.getData();
			if (!gridData1 || gridData1.length <= 0) {
				//BIONE.tip("查询项不能为空");
				//return false;
			}
			var queryArray = [];
			if (gridData1 != null) {
				var orderNo1 = 0;
				for ( var i = 0; i < gridData1.length; i++) {
					queryArray.push({
						nodeTabMapId : gridData1[i].nodeTabMapId,
						nodeId :currentNode.id,
						tabId : Number(gridData1[i].tabId),
						state : gridData1[i].state
					});
					orderNo1++;
				}
			}
			var queryArrayObj = {
				queryArray : []
			};
			queryArrayObj.queryArray = queryArray;

			
			var gridData2 = gridTable2.getData();
			//封装json
			var tabrelArray = [];
			if (gridData2 != null) {
				var orderNo1 = 0;
				for ( var i = 0; i < gridData2.length; i++) {
					
					//判断是否为空
					if(gridData2[i].tabId1!=null&&gridData2[i].colId1!=null&&gridData2[i].tabId2!=null&&gridData2[i].colId2!=null){
						
					}else{
						BIONE.tip("请填写完整的表间关联关系！");
						return false;
					}
					
					tabrelArray.push({
						nodeTabsRelId : gridData2[i].nodeTabsRelId,
						nodeId : currentNode.id,
						tabId1 : Number(gridData2[i].tabId1),
						tabId2 : Number(gridData2[i].tabId2),
						colId1 : Number(gridData2[i].colId1),
						colId2 : Number(gridData2[i].colId2),
						rel :    gridData2[i].rel,
						state :  gridData2[i].state
						
					});
					orderNo1++;
				}
			}
			var tabrelArrayObj = {
					tabrelArray : []
			};
			tabrelArrayObj.tabrelArray = tabrelArray;

			var gridData3 = gridTable3.getData();
			//封装json
			var tabfilterArray = [];
			if (gridData3 != null) {
				var orderNo1 = 0;
				for ( var i = 0; i < gridData3.length; i++) {
					
					//判断是否为空
					if(gridData3[i].tabId!=null&&gridData3[i].colId!=null&&gridData3[i].attrId!=null&&gridData3[i].rel!=null){
						
					}else{
						BIONE.tip("请填写完整的过滤条件！");
						return false;
					}
					
					//debugger;
					tabfilterArray.push({
						filterId : gridData3[i].filterId,
						nodeId :currentNode.id,
						tabId : Number(gridData3[i].tabId),
						colId : Number(gridData3[i].colId),
						attrId : Number(gridData3[i].attrId),
						rel : gridData3[i].rel,
						state : gridData3[i].state
					});
					orderNo1++;
				}
			}
			var tabfilterArrayObj = {
					tabfilterArray : []
			};
			tabfilterArrayObj.tabfilterArray = tabfilterArray;
			
			var gridData4 = gridTable4.getData();
			//封装json
			var nodeattrArray = [];
			if (gridData4 != null) {
				var orderNo1 = 0;
				for ( var i = 0; i < gridData4.length; i++) {
					
					//判断是否为空
					if(gridData4[i].attrCode!=null&&gridData4[i].attrName!=null){
						
					}else{
						BIONE.tip("请填写完整的节点属性！");
						return false;
					}
					
					
					nodeattrArray.push({
						attrId : gridData4[i].attrId,
						nodeId :currentNode.id,
						attrCode : gridData4[i].attrCode,
						attrName : gridData4[i].attrName,
						tabId : Number(gridData4[i].tabId),
						colId : Number(gridData4[i].colId),
						nulls : gridData4[i].nulls,
						dataType : gridData4[i].dataType,
						dataLen : Number(gridData4[i].dataLen),
						dataFmt : gridData4[i].dataFmt,
						checkRule : gridData4[i].checkRule,
						cateId : Number(gridData4[i].cateId),
						defaultVal : gridData4[i].defaultVal,
						fkAttrId : Number(gridData4[i].fkAttrId),
						attrSeq : gridData4[i].attrSeq,
						state : gridData4[i].state ,
						ctRule : gridData4[i].ctRule  
						
					});
					orderNo1++;
				}
			}
			var nodeattrArrayObj = {
					nodeattrArray : []
			};
			nodeattrArrayObj.nodeattrArray = nodeattrArray;
		
			
		if ($('#form_basic').valid()) {
			$.ajax({
				cache : false,
				url: '${ctx}/ecif/transaction/txmsg/save.json',
				type: 'post',
				data : {
					"msgnodeObj": JSON2
						.stringify(msgnodeObj),
					"queryArrayObj" : JSON2
							.stringify(queryArrayObj),
					"tabrelArrayObj" : JSON2
							.stringify(tabrelArrayObj),
					"tabfilterArrayObj" : JSON2
							.stringify(tabfilterArrayObj),
					"nodeattrArrayObj" : JSON2
							.stringify(nodeattrArrayObj)

				},
				dataType : 'json',
				success: function(){
					BIONE.tip('保存成功');
/* 					var userAttr = window.parent.userAttr;
					BIONE.closeDialog("userAttrWin");
					if(userAttr){
						userAttr.changeGrp($('#grpId').val());
						userAttr = null;
					} */
				},
				error : function(){
					BIONE.tip('保存失败,请联系管理员');
				}
			});
		}
	}	
	// 组织树对象
	function beforeTree() {
		var setting = {
			data : {
				key : {
					name : 'text'
				},
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "upId"
				}
			},
			callback : {
				onClick : zTreeOnClick
			}
		};
		zTreeObj = $.fn.zTree.init($("#tree"), setting);
	}
	
	// 获得树结点数据, 追加到树上
	function initTree() {
		$.ajax({
			cache : false,
			async : false,
			url : '${ctx}/ecif/transaction/txmsg/'+rootId+'/list.json',
			success : function(result) {
				var id = null;
				$(result).each(function() {
				/* 	if(this.params.open) {
						this.open = true;
					} else {
						this.open = false;
					} */
					/*
					if(this.ischecked) {
						id = this.id;
					}
					*/
				});
				
				var rootTmp = zTreeObj.getNodeByParam("id","0",null);
				if(rootTmp){
					zTreeObj.removeNode(rootTmp);
				}
				
				zTreeObj.addNodes(null, result, false);
				zTreeObj.expandAll(true);

				
			 	if(id != null && id != "") {
			 		zTreeObj.selectNode(currentNode = zTreeObj.getNodeByParam("id", id));
			 		getCurrentInfo(currentNode);
				}
			},
			error : function(result, b) {
				BIONE.tip('发现系统错误 <BR>错误码：' + result.status);
			}
		});
	}
	
	// init menu toolbar
	function initMenubar() {
		$("#treeToolbar").ligerTreeToolBar({
			items : [ {
				icon:'add',
				text : '新增节点',
				click : addFunc	
			}, {
				line : true
			}, {
				icon : 'delete',
				text : '删除节点',
				click : deleteCallback
			}]
		});
	}
	
	// zTree onClick
	function zTreeOnClick(event, treeId, treeNode) {
		currentNode = treeNode;
		getCurrentInfo(treeNode);
	}
	
	// 刷新树
	function refreshTree() {
		var rootTmp = zTreeObj.getNodeByParam("id","0",null);
		if(rootTmp){
			zTreeObj.removeNode(rootTmp);
		}
		initTree();
	}

	function getCurrentInfo(node) {					//load current info
		
		if(node.id == "0") return false;
		if($(".l-group l-group-hasicon").length == 0)	{		//judge the form whether exist where id='mainform' 
			
		}	
		
		//var parentNode =  getParent(node);
		msgId = node.data['msgId'];
		
		BIONE.loadForm($("#form_basic"), {url : "${ctx}/ecif/transaction/txmsgnode/"+node.id+""});
		rightinited++;
		initTab(node);	
		
/* 		$("#mainform input[name]").val("");
		$("#mainform textarea[name]").val("");
		$("#mainform input[name=upFunc]").attr("disabled", "disabled");
		BIONE.loadForm($("#mainform"), { url : '${ctx}/ecif/transaction/txmsg/' + node.id });
		// load upFunc
	 	$.ajax({
			type: 'get',
			url: '${ctx}/ecif/transaction/txmsg/' + node.upId+'/up',
			dataType: 'json',
			success: function(data) {
				if (data&&data.funcName != "")
					$("#mainform input[name='upFunc']").val(data.funcName);
				else 
					$("#mainform input[name='upFunc']").val("");
			},
			error: function() {
				$("#mainform input[name='upFunc']").val(rootName);
			}
		});
		$(".l-dialog-btn").remove();
		BIONE.addFormButtons(buttons);		//generate the 'update' and 'delete' buttons
		jQuery.metadata.setType("attr", "validate");
		BIONE.validate($("#mainform")); */
	}
	
	
	function initForms(){
		var initData = [ {
			field : 'attrSts',
			value : '1'
		}, {
			field : 'elementType',
			value : '01'
		}, {
			field : 'labelAlign',
			value : 'left'
		}, {
			field : 'elementAlign',
			value : 'left'
		}, {
			field : 'isNewline',
			value : '0'
		}, {
			field : 'isAllowNull',
			value : '1'
		}, {
			field : 'isReadonly',
			value : '0'
		} ];
		
		basic = $('#form_basic').ligerForm({
	        fields: [
	 	            {name : "nodeId", type : "hidden"},
	 	            {name : "msgId", type : "hidden"},
	 	            {name : "upNodeId", type : "hidden"},
	 	            {name : "nodeSeq", type : "hidden"},
	 	            { display: "节点代码", name: "nodeCode", newline: true, type: "text", group: "节点信息", groupicon: groupicon, 
	 	            	validate : {
	 						required : true,
	 						maxlength : 50
	 					} 
	 	            }, 
	 	            { display: "节点名称", name: "nodeNamexx", newline: false, type: "text", 
	 	            	validate : {
	 						required : true,
	 						maxlength : 5
	 					} 	
	 	            },
	 	            
	 	            { display: "节点类型 ", name: "nodeTpBox", newline: true,
	 					type : "select",
	 					options : {
	 						valueFieldID : 'nodeTp',
	 						data : [{id : 'C', text : '普通'}, {id : 'T', text : '基于数据库表'}]
	 					},
	 	            	validate : {
	 						required : true,
	 						maxlength : 32
	 					}	
	 	            },
	 	            { display: "是否节点组", name: "nodeGroupBox", newline: false, 
	 	            	type: "select", 
	 					options : {
	 						valueFieldID : 'nodeGroup',
	 						data : [{id : '0', text : '否'}, {id : '1', text : '是'}]
	 					},	 	            	
	 					validate : {
	 						maxlength : 100
	 					} 	
	 	            }
	 	        ]
		});
		if("${id}") {
			BIONE.loadForm($("#form_basic"), {url : "${ctx}/ecif/transaction/txmsgnode/${id}"});
		}
	}	
	
	
	function initGrid_tables(node){
		//debugger;
		gridTable = $("#maingrid1").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
								{
									name : 'nodeTabMapId',
									hide :1
								},{
									name : 'nodeId',
									hide :1
								},{
									name : 'state',
									hide :1
								},{
									name : 'tabId_text',
									hide :1
								},{
								display : '表中文名称',
								name : 'tabId',
								align : 'center',
								width : '30%',
								editor : {
									type : 'select',
									//data : tabdefData,
									valueColumnName : 'tabId',
									ext : function(rowdata) {
										return {
											textField : 'tabDesc',
											onBeforeOpen : function(){
												
												var sel = this;
												if (tabdefData.length > 0) {
													sel.setData(tabdefData);
													return true;
												} else {
													//debugger;
													$.ajax({
														url : '${ctx}/ecif/core/tabdef/listall.json',
														async : false,
														type : 'get',
														success : function(data) {
															$.each(data, function(i, n){
																tabdefData.push(n);
															});
														}
													});
												}
												//return false;
											},
											render : function(){
												return rowdata.tabDesc;
											}

										};
									}
								},
								render : function(item) {
									//debugger;
									for ( var i = 0; i < tabdefData.length; i++) {
										if (tabdefData[i].tabId == item.tabId) {
											item.tabDesc = tabdefData[i].tabDesc;
											return tabdefData[i].tabDesc;
										}
									}
									return item.tabDesc;
								},	
								minWidth : '30%'
							} ],
					checkbox : true,
					usePager : false,
					isScroll : true,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'get',
					url : '${ctx}/ecif/transaction/txmsgnodetabmap/list.json?nodeId='+ node.id,
					sortName : 'tabId', //第一次默认排序的字段
					sortOrder : 'asc',
					enabledEdit : true,     //列表进入可编辑模式
					toolbar : {}
		});
		

	}
	
	
	function initGrid_tablesrel(node){
		
		gridTable2 = $("#maingrid2").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
								{
									name : 'nodeTabsRelId',
									hide :1
								},{
									name : 'state',
									hide :1
								},{
								display : '表名',
								name : 'tabId1',
								align : 'center',
								editor : {
									type : 'select',
									data : tableData,
									valueColumnName : 'tabId1',
									ext : function(rowdata) {
										
										return {
											
											textField : 'tabDesc1',
											onBeforeOpen : function(){
												var sel = this;
												if (tableData.length > 0) {
													sel.setData(tableData);
													return true;
												} else {

												}
												return false;
											}
										};
									}
								},
								render : function(item) {
									for ( var i = 0; i < tableData.length; i++) {
										if (tableData[i].tabId1 == item.tabId1) {
											return tableData[i].tabDesc1;
										}
									}
									return item.tabDesc1;
								},	
								width : '20%'
							},
							{
								display : '字段名',
								name : 'colId1',
								align : 'center',
								editor : {
									type : 'select',
									data : col1Data,
									valueColumnName : 'colId',
									ext : function(rowdata) {
										return {
											textField : 'colChName',
											onBeforeOpen : function(){
												
												var sel = this;
												col1Data = [];
												
												$.ajax({
													url : '${ctx}/ecif/core/coldef/alltablecollist.json?tabId=' + rowdata.tabId1,
													async : false,
													type : 'get',
													success : function(data) {
														
														$.each(data, function(i, n){
															col1Data.push(n);
														});
														
													}
												});
												sel.setData(col1Data);
												return true;
											}
										};

									}
								},
								render : function(item) {
									
									for ( var i = 0; i < col1Data.length; i++) {
										if (col1Data[i].colId == item.colId1) {
											return col1Data[i].colChName;
										}
									}
									return item.colName1;
								},	

								width : '20%'
							},
							{
								display : '关系',
								name : 'rel',
								align : 'center',
								render : function(item) {
									return '=';
								},
								width : '10%'
							},
							{
								display : '表名',
								name : 'tabId2',
								align : 'center',
								editor : {
									type : 'select',
									data : tableData,
									valueColumnName : 'tabId1',
									ext : function(rowdata) {
										return {
											textField : 'tabDesc1',
											onBeforeOpen : function(){
												var sel = this;
												if (tableData.length > 0) {
													sel.setData(tableData);
													return true;
												} else {

												}
												return false;
											}
										};
									}
								},
								render : function(item) {
									for ( var i = 0; i < tableData.length; i++) {
										if (tableData[i].tabId1 == item.tabId2) {
											return tableData[i].tabDesc1;
										}
									}
									return item.tabDesc2;
								},	
								width : '20%'
							},
							{
								display : '字段名',
								name : 'colId2',
								align : 'center',
								editor : {
									type : 'select',
									data : col2Data,
									valueColumnName : 'colId',
									ext : function(rowdata) {
										return {
											textField : 'colChName',
											onBeforeOpen : function(){
												//debugger;
												var sel = this;
												col2Data = [];

												$.ajax({
													url : '${ctx}/ecif/core/coldef/alltablecollist.json?tabId=' + rowdata.tabId2,
													async : false,
													type : 'get',
													success : function(data) {

														$.each(data, function(i, n){
															col2Data.push(n);
														});
													}
												});
												sel.setData(col2Data);
												return true;
											}
								
										};
									}
								},
								render : function(item) {
									for ( var i = 0; i < col2Data.length; i++) {
										if (col2Data[i].colId == item.colId2) {
											return col2Data[i].colChName;
										}
									}
									return item.colName2;
								},	
								width : '20%'
							} ],
					checkbox : true,
					usePager : false,
					isScroll : true,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'get',
					url : '${ctx}/ecif/transaction/txmsgnodetabsrel/list.json?nodeId=' + node.id,
					sortName : 'nodeTabsRelId', //第一次默认排序的字段
					sortOrder : 'asc',
					enabledEdit : true,     //列表进入可编辑模式
					toolbar : {}
		});
		
		
	}
	
	
	function initGrid_filters(node){
		
		gridTable3 = $("#maingrid3").ligerGrid(
				{
					height : '100%',
					width : '100%',
					columns : [
								{
									display : '节点ID',
									name : 'nodeId',
									align : 'center',
									hide :1

								},{

								display : '表名',
								name : 'tabId',
								align : 'center',
								editor : {
									type : 'select',
									data : tableData,
									valueColumnName : 'tabId1',
									ext : function(rowdata) {
										return {
											textField : 'tabDesc1',
											onBeforeOpen : function(){
												var sel = this;
												if (tableData.length > 0) {
													sel.setData(tableData);
													return true;
												} else {

												}
												return false;
											}
										
										};
									}
								},
								render : function(item) {
									
									for ( var i = 0; i < tableData.length; i++) {
										if (tableData[i].tabId1 == item.tabId) {
											return tableData[i].tabDesc1;
										}
									}
									return item.tabDesc;
								},	

								width : '20%'
							},
							{
								display : '字段名',
								name : 'colId',
								align : 'center',
								editor : {
									type : 'select',
									data : col1Data,
									valueColumnName : 'colId',
									ext : function(rowdata) {
										return {
											textField : 'colChName',
											onBeforeOpen : function(){
												
												var sel = this;
												col1Data = [];
												
												$.ajax({
													url : '${ctx}/ecif/core/coldef/alltablecollist.json?tabId=' + rowdata.tabId,
													async : false,
													type : 'get',
													success : function(data) {
														
														$.each(data, function(i, n){
															col1Data.push(n);
														});
														
													}
												});
												sel.setData(col1Data);
												return true;
											}
										};

									}
								},
								render : function(item) {
									
									for ( var i = 0; i < col1Data.length; i++) {
										if (col1Data[i].colId == item.colId) {
											return col1Data[i].colChName;
										}
									}
									return item.colChName;
								},	
								width : '20%'
							},
							{
								display : '关系',
								name : 'rel',
								align : 'center',
								editor : {
									type : 'select',
									data : relArray,
									valueColumnName : 'id',
									displayColumnName : 'text'
								},
								render : function(item) {
									for ( var i = 0; i < relArray.length; i++) {
										if (relArray[i].id == item.rel) {
											return relArray[i].text;
										}
									}
									return item.rel;
								},
								width : '10%'
							},
							{
								display : '请求报文',
								name : 'attrIdBox',
								align : 'center',
								editor : {
									type : 'select',
									valueColumnName : 'attrId',
									ext : function(rowdata) {
										return {
											textField : 'attrName',
											onBeforeOpen : function(){
												//alert(rowdata.nodeId);
												var dom = window.parent||window;
												var subWinHeight = dom.$("#center").height()-50;
												var subWinWidth=dom.$("#center").width()-200;
												dom.ruleEqual = window;
												dom.ruleEqualRow = rowdata;
												curData = rowdata.grpField;
												dom.BIONE.commonOpenDialog("报文树", "ditargetManageWin",subWinWidth,subWinHeight, '${ctx}/ecif/transaction/txmsg/treeindex?id='+ rootId,null);
												return false;
											}
										};
									}
								},
								render : function(item) {
									return item.attrName;

								},
								width : '20%'
							} ],
					checkbox : true,
					usePager : false,
					isScroll : true,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'get',
					url : '${ctx}/ecif/transaction/txmsgnodefilter/list.json?nodeId=' + node.id,
					sortName : 'filterId', //第一次默认排序的字段
					sortOrder : 'asc',
					enabledEdit : true,     //列表进入可编辑模式
					toolbar : {}
		});
		
	
	}
	
	
	function initGrid_nodeattrs(node){
		//debugger;
		gridTable4 = $("#nodeattrgrid").ligerGrid(
				{
					height : '92%',
					width : '100%',
					headerRowHeight : 40,
					//columnWidth :150,
					columns : [
							{
								display : '节点ID',
								name : 'nodeId',
								align : 'center',
								hide :1

							},{
								display : '属性ID',
								name : 'attrId',
								align : 'center',
								hide :1
							},{
								display : '节点序号',
								name : 'attrSeq',
								align : 'center',
								hide :1

							},	{
								display : '属性代码',
								name : 'attrCode',
								align : 'center',
								editor : {
									type : 'text'
								},
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '属性名称',
								name : 'attrName',
								align : 'center',
								editor : {
									type : 'text'
								},
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '所属表',
								name : 'tabId',
								align : 'center',
								width : '10%',
								editor : {
									type : 'select',
									valueColumnName : 'tabId1',
									ext : function(rowdata) {
										return {
											textField : 'tabDesc1',
											onBeforeOpen : function(){
												var sel = this;
												if (tableData.length > 0) {
													sel.setData(tableData);
													return true;
												} else {

												}
												return false;
											}											

										};
									}
								},
								render : function(item) {
									
									for ( var i = 0; i < tableData.length; i++) {
										if (tableData[i].tabId1 == item.tabId) {
											return tableData[i].tabDesc1;
										}
									}
									return item.tabDesc;
								},	

								minWidth : '10%'
							},
							{
								display : '所属字段',
								name : 'colId',
								align : 'center',
								width : '10%',
								editor : {
									type : 'select',
									data : col1Data,
									valueColumnName : 'colId',
									ext : function(rowdata) {
										return {
											textField : 'colChName',
											onBeforeOpen : function(){
												
												var sel = this;
												col1Data = [];
												
												$.ajax({
													url : '${ctx}/ecif/core/coldef/alltablecollist.json?tabId=' + rowdata.tabId,
													async : false,
													type : 'get',
													success : function(data) {
														
														$.each(data, function(i, n){
															col1Data.push(n);
														});
														
													}
												});
												sel.setData(col1Data);
												return true;
											}
									
										
										};

									}
								},
								render : function(item) {
									
									for ( var i = 0; i < col1Data.length; i++) {
										if (col1Data[i].colId == item.colId) {
											return col1Data[i].colChName;
										}
									}
									return item.colChName;
								},	

								minWidth : '10%'
							},
							{
								display : '非空标志',
								name : 'nulls',
								align : 'center',
								width : '10%',
								minWidth : '10%',
								editor : {
									type : 'select',
									data : nullArray,
									valueColumnName : 'id',
									displayColumnName : 'text'
								},
								render : function(item) {
									for ( var i = 0; i < nullArray.length; i++) {
										if (nullArray[i].id == item.nulls) {
											return nullArray[i].text;
										}
									}
									return item.nulls;
								}
							},
							{
								display : '数据类型',
								name : 'dataType',
								align : 'center',
								width : '10%',
								editor : {
									type : 'select',
									data : datatypeArray,
									valueColumnName : 'id',
									displayColumnName : 'text'
								},
								render : function(item) {
									for ( var i = 0; i < datatypeArray.length; i++) {
										if (datatypeArray[i].id == item.dataType) {
											return datatypeArray[i].text;
										}
									}
									return item.dataType;
								}
							},
							{
								display : '长度',
								name : 'dataLen',
								align : 'center',
								editor : {
									type : 'text'
								},
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '格式',
								name : 'dataFmt',
								align : 'center',
								editor : {
									type : 'text'
								},
								width : '10%',
								minWidth : '10%'
							},
							{
								display : '对应父节点属性',
								name : 'cateId',
								align : 'center',
								width : '10%',
								minWidth : 100 
							},
							{
								display : '数据过滤、转换',
								name : 'ctRuleBox',
								align : 'center',
								editor : {
									type : 'select',
									valueColumnName : 'ctRule',
									ext : function(rowdata) {
										return {
											textField : 'ctDesc',
											onBeforeOpen : function(){
												var dom = window.parent||window;
												var subWinHeight = dom.$("#center").height()-50;
												var subWinWidth=dom.$("#center").width()-200;
												dom.ruleEqual = window;
												dom.ruleEqualRow = rowdata;
												curData = rowdata.grpField;
												dom.BIONE.commonOpenDialog("选择校验规则", "checkRuleWin",subWinWidth,subWinHeight, '${ctx}/ecif/transaction/txmsgnodeattr/rule',null);
												return false;
											}
										};
									}
								},
								render : function(item) {
									return item.ctDesc;

								},
								width : '10%',
								minWidth : 200 
							},
							{
								display : '标准代码分类',
								name : 'cateId',
								align : 'center',
								editor : {
									type : 'text'
								},
								width : '10%',
								minWidth : 200 
							},
							{
								display : '备注',
								name : 'defaultVal',
								align : 'center',
								editor : {
									type : 'text'
								},
								width : '10%',
								minWidth : '10%'
							} ],
					checkbox : true,
					usePager : true,
					isScroll : false,
					rownumbers : true,
					alternatingRow : true,//附加奇偶行效果行
					colDraggable : true,
					dataAction : 'server',//从后台获取数据
					method : 'get',
					url : '${ctx}/ecif/transaction/txmsgnodeattr/list.json?nodeId=' + node.id,
					sortName : 'tabId', //第一次默认排序的字段
					sortOrder : 'asc',
					enabledEdit : true,     //列表进入可编辑模式
					toolbar : {}
		});
		
	}
	
	function initToolBar_tables(){
		 var btns2 = [
		 	{
		 		text :  '添加' ,
		 		icon :  'add',
				click : f_add_tables
		 	},
		 	{
		 		text :  '删除' ,
				icon :  'delete',
				click : f_delete_tables
				
		 	}
		 ];
		 BIONE.loadToolbar(gridTable,btns2,function() { });
	};	
	
	
	function initToolBar_tablesrel(){
		 var btns2 = [
		 	{
		 		text :  '添加' ,
		 		icon :  'add',
				click : f_add_tablesrel
		 	},
		 	{
		 		text :  '删除' ,
				icon :  'delete',
				click : f_delete_tablesrel
				
		 	}
		 ];
		 BIONE.loadToolbar(gridTable2,btns2,function() { });
	};	
	
	function initToolBar_tablesfilter(){
		 var btns2 = [
		 	{
		 		text :  '添加' ,
		 		icon :  'add',
				click : f_add_tablesfilter
		 	},
		 	{
		 		text :  '删除' ,
				icon :  'delete',
				click : f_delete_tablesfilter
				
		 	}
		 ];
		 BIONE.loadToolbar(gridTable3,btns2,function() { });
	};	
	
	function initToolBar_nodeattrs(){
		 var btns2 = [
		 	{
		 		text :  '添加' ,
		 		icon :  'add',
				click : f_add_nodeattrs 
		 	},
		 	{
		 		text :  '删除' ,
				icon :  'delete',
				click : f_delete_nodeattrs 
				
		 	}
		 ];
		 BIONE.loadToolbar(gridTable4,btns2,function() { });
	};		
	//新增一行信息
	function f_add_tables(datacolId,datacolName,datacolCName,dataType){
		
			//gridTable.addRow();	
		    gridTable.addEditRow(
		    		{
		    			
		    			
		    		}
		    		
		    		);
	}
	
	//删除勾选项
	function f_delete_tables(){
  		if(gridTable.getSelectedRows().length>0){
  			gridTable.deleteSelectedRow();
		}else{
			BIONE.tip("请选择需要删除的行");
		}
	}	
	
	//新增一行信息
	function f_add_tablesrel(datacolId,datacolName,datacolCName,dataType){
			gridTable2.addRow();			
	}
	
	//删除勾选项
	function f_delete_tablesrel(){
  		if(gridTable2.getSelectedRows().length>0){
  			gridTable2.deleteSelectedRow();
		}else{
			BIONE.tip("请选择需要删除的行");
		}
	}	
	
	//新增一行信息
	function f_add_tablesfilter(){
			gridTable3.addRow();			
	}

	//删除勾选项
	function f_delete_tablesfilter(){
  		if(gridTable3.getSelectedRows().length>0){
  			gridTable3.deleteSelectedRow();
		}else{
			BIONE.tip("请选择需要删除的行");
		}
	}	
	
	//新增一行信息
	function f_add_nodeattrs(){
			gridTable4.addRow();			
	}

	//删除勾选项
	function f_delete_nodeattrs(){
  		if(gridTable4.getSelectedRows().length>0){
  			gridTable4.deleteSelectedRow();
		}else{
			BIONE.tip("请选择需要删除的行");
		}
	}		
	//DELETE
	function deleteCallback() {
		if(currentNode) {
			if(currentNode.id != '0') {
				$.ligerDialog.confirm('确实要删除该记录吗?', function(yes) {
					if(yes) {
						var flag = false;
						$.ajax({
							async : false,
							cache : false,
							type : "DELETE",
							url : "${ctx}/ecif/transaction/txmsgnode/" + currentNode.id ,
							dataType : "script",
							success : function() {
								flag = true;
							}
						});
						if (flag == true) {
							BIONE.tip('删除成功');
							currentNode = null;
							refreshTree();
						} else {
							BIONE.tip('删除失败');
						}
					}
				});
			} else {
				BIONE.tip('此节点不能删除');
			}	
		} 
	};
	//SAVE
	function savedCallback() {
		BIONE.submitForm($("#mainform"), function(data) {
			BIONE.tip("保存成功");
			refreshTree();
		});
	};
	
	//CANCLE
	function closeCallBack() {
		BIONE.closeDialog("moduleFunc");
	};
	
	function addFunc() {
		
		//var msgId = "${id}";
     	var upNodeId = null;
		
		if(currentNode == null) {
			BIONE.tip("请先选择一个节点！");
			return;
		}else{
			upNodeId = currentNode.id;
		}
		
	    dialog = BIONE.commonOpenDialog("增加下级节点", "resDefManage",800,400,
			    "${ctx}/ecif/transaction/txmsgnode/new?msgId="+msgId+"&upNodeId="+upNodeId);



	}
	
	// 获取节点对应表tab中选中的表
	function achieveTableIds() {
		tableData = [];
		
		var all = gridTable.getData();
		$.each(all, function(i, n) {
			var x = gridTable.getRowObj(i);
			//debugger;
			if(!gridTable.isSelected(x)){
				gridTable.select(gridTable.getRow(i));
			}
		});
		
		var rows = gridTable.getSelectedRows();
		for(var i in rows) {
			
			tableData.push({
				tabId1 : rows[i].tabId,
				tabDesc1 : rows[i].tabDesc
			});
		}
		
	}
	
	function RenderFlag(rowdata){
		if(rowdata.dataType == '10'){
			return '字符型(固定长度)';
		}else if(rowdata.dataType == '20'){
			return '字符型(可变长度)';
		}else if(rowdata.dataType == '30'){
			return '整型';
		}else if(rowdata.dataType == '40'){
			return '浮点型';
		}else if(rowdata.dataType == '50'){
			return '日期型';
		}else if(rowdata.dataType == '60'){
			return '时间型';
		}else if(rowdata.dataType == '70'){
			return '时间戳型';
		}else{
			return rowdata.dataType;
		}
	}
	
	function RenderNullFlag(rowdata){
		if(rowdata.nulls == 'Y'){
			return '非空';
		}else if(rowdata.nulls == 'N'){
			return '可空';
		}else{
			return rowdata.nulls;
		}
	}
	
	var nullArray = [ {
		id : 'Y',
		text : '非空'
	}, {
		id : 'N',
		text : '可为空'
	} ];	
	
	
	var relArray = [ {
		id : '=',
		text : '='
	}, {
		id : 'like',
		text : 'like'
	} ];	
		
		
	var datatypeArray = [ {
		id : '10',
		text : '字符型(固定长度)'
	}, {
		id : '20',
		text : '字符型(可变长度)'
	}, {
		id : '30',
		text : '整型'
	}, {
		id : '40',
		text : '浮点型'
	}, {
		id : '50',
		text : '日期型'
	}, {
		id : '60',
		text : '时间型'
	}, {
		id : '70',
		text : '时间戳型'
	} ];	
	
	
	
</script>
</head>
<body>
	<div id="template.left.up.icon">
		<img src="${ctx}/images/classics/icons/application_side_tree.png" />
	</div>
	<div id="template.left.up">
		<span id="treeTitle" style="font-size: 12">功能树</span>
	</div>
	<div id="template.right">
		<div id="tab" style="overflow:hidden;height:92%">
		  	  <div tabid="basic" title="基本属性" lselected="true" style="overflow:hidden;">
		  	  	<form id="form_basic" method="post" action=""></form>
		  	  </div>
		  	  <div tabid="styles" title="节点对应表" style="overflow:hidden;">
		  	  	<div id="maingrid1" class="maingrid"></div>
		  	  </div>
		  	  <div tabid="styles2" title="表间关联关系" style="overflow:hidden;">
		  	  	<div id="maingrid2" class="maingrid"></div>
		  	  </div>
		  	  <div tabid="styles3" title="过滤条件" style="overflow:hidden;">
		  	  	<div id="maingrid3" class="maingrid"></div>
		  	  </div>
		  	  
		  	  <div tabid="verify" title="节点属性" style="overflow:hidden">
		  	  	<div id="nodeattrgrid" class="maingrid"></div>
		  	  </div>	
		 </div>

	</div>
	
	<div id="template.bottom">
			<div class="form-bar">
				<div class="form-bar-inner" style="padding-top: 5px"></div>
			</div>
		</div>
	</div>
	

  	
</body>
</html>