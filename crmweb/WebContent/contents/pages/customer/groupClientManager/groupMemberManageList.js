Ext.onReady(function() {	
    Ext.QuickTips.init(); 
    
	Ext.override(Ext.form.ComboBox, {
	      onViewClick : function(doFocus) {
	        var index = this.view.getSelectedIndexes()[0], s = this.store, r = s.getAt(index);
	        if (r) {
	          this.onSelect(r, index);
	        } else if (s.getCount() === 0) {
	          this.collapse();
	              
	        }
	        if (doFocus !== false) {
	          this.el.focus();
	        }
	      }
	    });	    
	 
	 //递归收起子节点
	 function childCollapse(node){
	 	 node.eachChild(function(currNode)
	 	 {
	 	 	if(!currNode.isLeaf())
	 	 	{
		 	 	currNode.collapse();		 	 	         
		 	 	childCollapse(currNode);
	 	 	}
	 	 }
	 	 );
	 }
	 //选中节点后判断，所有被选中节点是否有跨行的情况
	 function spanBank(orgTreePanel_p){
	 	if(orgTreePanel_p.root.getUI().isChecked()){
	 		var rootId = orgTreePanel_p.root.id;
	 		var checkLevel = rootId.substring(rootId.length-1);
	 		return checkLevel;	 		
	 	}
	 	var level2Nodes = orgTreePanel_p.root.childNodes;//二级节点数组
	 	var level3Nodes = new Array();//三级节点数组
	 	var level4Nodes = new Array();//四级节点数组
	 	//for 循环得到所有3级节点
	 	for(var m=0;m<level2Nodes.length;m++){
	 		level2Nodes[m].eachChild(function(level3Node){	 		
	 			level3Nodes[level3Nodes.length] = level3Node;	 		
	 		} );
	 	}
	 	//for 循环得到所有4级节点
	 	for(var n=0;n<level3Nodes.length;n++){
	 		level3Nodes[n].eachChild(function(level4Node){
	 			level4Nodes[level4Nodes.length] = level4Node;
	 		});
	 	}	 	
	 	var num=0;
	 	var level2NodeChecked;
	 	var level3Checked = new Array();
	 	for(var i=0;i<level2Nodes.length;i++){
	 		if(level2Nodes[i].getUI().isChecked()){
	 			level2NodeChecked = level2Nodes[i];//得到第二层级被选择的节点
	 			num++;
	 			if(num >=2){
//	 				alert(" 一级没选,2级选择 有跨行:"+"4");
	 				return "4";//表示跨行选择
	 			}
	 		}
	 	}
		
	 	if(num ==1){	 	
			for(var j=0;j<level3Nodes.length;j++){			
				if(level3Nodes[j].getUI().isChecked()){
					level3Checked[level3Checked.length] = level3Nodes[j];
					if(level3Nodes[j].parentNode != level2NodeChecked){//如果被选择的三级节点的父节点不是同一个被选择二级节点,则表示有跨行选择
//						alert(" 二级选择了一个,三级选择有跨行:" + "4");
						return "4";
					}					
				}
			}			
			for(var k=0;k<level4Nodes.length;k++){
				
				if(level4Nodes[k].getUI().isChecked()){
					var flag=false;
					for(var a=0;a<level3Checked.length;a++){
						if(level4Nodes[k].parentNode == level3Checked[a]){
							flag = true;
							break;
						}
					}
					if(!flag){
//						alert(level2NodeChecked.text);
//						alert(" 二级选择了一个,三级选择了一个,四级选择有跨行:" + "4");
						return "4";
					}
				}
			}
			var check2NodeId = level2NodeChecked.id;
			var checkLevel = check2NodeId.substring(check2NodeId.length-1);
//			alert("选中2级节点统计: "+checkLevel	);
			return checkLevel;
			
		}else if(num==0){
			
			var nodeNum3=0;
			var tempChecked3Node;
			for(var b=0;b<level3Nodes.length;b++){
				if(level3Nodes[b].getUI().isChecked()){
					tempChecked3Node = level3Nodes[b];
					nodeNum3++;
					if(nodeNum3>=2){
//						alert("2级没选,三级选择有跨行: "+4);
						return "4";
					}
				}
			}			
			if(nodeNum3==1){
				
				for(var c = 0;c<level4Nodes.length;c++){
					if( (level4Nodes[c].getUI().isChecked()) && (level4Nodes[c].parentNode!=tempChecked3Node) ){
//							alert("二级没选,3级选择了一个,4级选择有跨行.");
							return "4";					
					}
				}			
				var check3NodeId = tempChecked3Node.id;
				var checkLevel = check3NodeId.substring(check3NodeId.length-1);
//						alert("选择了三级节点统计:"+checkLevel);
				return checkLevel;			
			}else if(nodeNum3==0){
				for(var d=0;d<level4Nodes.length;d++){
					if(level4Nodes[d].getUI().isChecked()){
						var check4NodeId = level4Nodes[d].id;
						var checkLevel = check4NodeId.substring(check4NodeId.length-1);
//						alert("选择了四级节点统计: "+checkLevel);
						return checkLevel;
					}
				}
			}
		}
			 	
	 } 
	 
	 function getCheckedStr(checkedStr,node){
	 		node.eachChild(function(tempNode){
	 			if(tempNode!=null)
	 			{	
	 				if( tempNode.getUI().isChecked())
	 				{
				 		var idCode = tempNode.id;
				 		var id = idCode.substring(0,idCode.length-1);
			 			checkedStr = checkedStr+"'"+id+"',";
			 			checkedStr = getCheckedStr(checkedStr,tempNode);
			 			return checkedStr;
	 				}else
	 				{
	 					checkedStr = getCheckedStr(checkedStr,tempNode);
	 					return checkedStr;
	 				}
	 			}
	 		});	 		

		 	return checkedStr;
	 }
	 
	 function getCheckedNode(orgTreePanel_p){
		var rootNode = orgTreePanel_p.root;	
		var checkedStr="";
		if(rootNode.getUI().isChecked()){
	 		var idCode = rootNode.id;
	 		var id_2 = idCode.substring(0,idCode.length-1);			
		 	checkedStr=checkedStr+"'" + id_2+"'"+",";	
		}	 	
	 	checkedStr = getCheckedStr(checkedStr,rootNode);
	 	
	 	
	 	return checkedStr.substring(0,checkedStr.length-1);
	 }
	
	 var orgTreePanel = new Ext.tree.TreePanel(
			 	{	 		
			 	autoScroll:true,
			 	height:350,
			 	width:200,
				listeners:{
					'click':function(node)
					{
						node.getUI().toggleCheck(true);
						var level = spanBank(orgTreePanel);
						var checkedNodes = getCheckedNode(orgTreePanel);
						alert(node.id);
						tempCombo = groupMemberSearchForm.getForm().findField("GROUP_HOST_ORG_NO_NAME");
						groupMemberSearchForm.getForm().findField("GROUP_HOST_ORG_NO").setValue(node.id);
						tempCombo.setRawValue(node.text);
						tempCombo.collapse();
						
					},
					'checkchange':function(node,checked){
						if(checked){					
							var childNodes = node.childNodes;
							for(var i=0;i<childNodes.length;i++){
								childNodes[i].getUI().toggleCheck(true);					
							}
							
						}				
						else
						{	
							var childNodes = node.childNodes;
							for(var i=0;i<childNodes.length;i++){						
								childNodes[i].getUI().toggleCheck(false);
							}
						}			
					}
				},
				root:new Ext.tree.AsyncTreeNode({
					id:orgId,
					text:orgName,			
					autoScroll:true,
					expanded:true,
					leaf:false,
//					checked:false,
					loader:new Ext.tree.TreeLoader({
						url:basepath+'/system-unit-recursive-plain.json',
						requestMethod:'GET',
						listeners:{
							'load':function(){
								var rootNode = orgTreePanel.root;
								rootNode.eachChild(function(node){
									if(!node.isLeaf()){
										node.collapse();
										childCollapse(node);
									}
								});						
							}
						}
					})
				}),
				animate : false,
				useArrows : false,
				border : false
			 }
			 );	
	//*********************************

	
	var boxstore = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['全行型', '0001'], ['区域型', '0002']]
	});
	var boxstore2 = new Ext.data.SimpleStore({
		fields : ['name', 'code'],
		data : [['请选择', '0000'],['启用', '0001'], ['停用', '0002']]
	});
	
	var appStatusStore = new Ext.data.Store({  
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=APP_STATUS'
			}),
			reader : new Ext.data.JsonReader({
				root : 'JSON'
			}, [ 'key', 'value' ])
		});
	
	
	var groupMemberSearchForm = new Ext.form.FormPanel({
		labelWidth : 100, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		region:'north',
		height : 100,
		items : [{
			layout : 'column',
			items : [
					{
						columnWidth : .20,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
                            labelStyle: 'text-align:right;',
							fieldLabel : '集团客户编号',
							id : 'GROUP_NO',
							name : 'GROUP_NO',
							anchor : '90%'
						} ]
					},{
						columnWidth : .20,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
                            labelStyle: 'text-align:right;',
							fieldLabel : '集团客户名称',
							id : 'GROUP_NAME',
							name : 'GROUP_NAME',
							anchor : '90%'
						}]
					},{
						columnWidth : .20,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
                            labelStyle: 'text-align:right;',
							fieldLabel : '集团母公司名称',
							id : 'CUST_ZH_NAME',
							name : 'CUST_ZH_NAME',
							anchor : '90%'
						}]
					},{
						columnWidth : .20,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
                            labelStyle: 'text-align:right;',
							fieldLabel : '申请企业名称',
							id : 'PARENT_CUST_ZH_NAME',
							name : 'PARENT_CUST_ZH_NAME',
							anchor : '90%'
						}]
					},
					{
						columnWidth : .20,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
                            labelStyle: 'text-align:right;',
							fieldLabel : '上级企业名称',
							hidden :true,
							id : 'UP_NAME',
							name : 'UP_NAME',
							anchor : '90%'
						}]
					},{
						name:"GROUP_HOST_ORG_NO",
						hidden:true,
						xtype:'textfield'
					},
					{
						columnWidth : .20,
						layout : 'form',
						items : [ {
							xtype:'combo',					
							store : new Ext.data.SimpleStore( {
								fields : [],
								data : [ [] ]
							}),
							name:'GROUP_HOST_ORG_NO_NAME',
							id:'GROUP_HOST_ORG_NO_NAME',
							emptyText : '请选择',
							fieldLabel : '授信主办行',
                            labelStyle: 'text-align:right;',
							editable:false,
							resizable:true,
							anchor : '90%',
							mode : 'local',
							triggerAction : 'all',
							maxHeight : 390,
							// 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
							tpl:"<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div></tpl>",
//							tpl:"<div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div>",
							onSelect : Ext.emptyFn,
							listeners:{
								'expand':function(combo){			
//									combo.doLayout();
									orgTreePanel.render('addOrgTreeDivForAdd');
								}
							}
						} ]
					}
					,{
						columnWidth : .20,
						layout : 'form',
						items : [
							{
							store: appStatusStore,
							xtype : 'combo',
							name : 'APP_STATUS',
							fieldLabel : '审批状态',
							valueField:'key',
							displayField:'value',
							mode : 'local',
							typeAhead: true,
                            labelStyle: 'text-align:right;',
							forceSelection : true,
							resizable:true,
							triggerAction: 'all',
							emptyText:'请选择',
							selectOnFocus:true,
							width : '100',
							anchor : '90%'
							                 }

						    ]
					}]
		}],
		buttons : [{
					text : '查询',
					
					handler : function() {
						var conditionStr = groupMemberSearchForm.getForm().getFieldValues();
						groupMemberStore.baseParams = {
								"condition" : Ext.encode(conditionStr)
							};
						groupMemberStore.load({
							params : {
								start : 0,
								limit : parseInt(pagesize_combo.getValue())
							}
						});

					}
				},{
					text : '重置',
					
					handler : function() {
						groupMemberSearchForm.getForm().reset();
					}
				}
				]
	});
	 //单选
	var groupMemberSM = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var groupMemberCM = new Ext.grid.ColumnModel(
			[
				rownum,
				groupMemberSM,
/*
				{
					dataIndex:'ID',
					header:'ID'
				},
*/
	           {
				header : '集团客户编号', // 列标题
				dataIndex : 'groupNo', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '集团客户名称', // 列标题
				dataIndex : 'groupName', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '集团母公司ID', // 列标题
				dataIndex : 'rootCompanyId', // 数据索引:和Store模型对应
				hidden : true,
				sortable : true,// 是否可排序
				width : 150
		    },{
		    	header :'集团母公司名称',
		    	dataIndex : 'groupRootCustName1',
		    	width : 150
		    },{
				header : '申请企业名称', // 列标题
				dataIndex : 'parentCustZhName', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '授信主办行', // 列标题
				dataIndex : 'groupHostOrgNoName', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },{
				header : '主办行意见', // 列标题
				dataIndex : 'creditOpinion', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				hidden : true,
				width : 150
		    },
		    	{
				header : '上级企业名称', // 列标题
				dataIndex : 'upName', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },
		    	/*{
				header : '申请企业名称', // 列标题
				dataIndex : 'applyCom', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    },*/		    
		    	{
				header : '状态',
				dataIndex : 'appStatus1',
				sortable : true,
				width : 150
			},{
				header : '提交申请时间',
				dataIndex : 'submitDate'
			},{
				header : '时间',
				dataIndex : 'appDate'
			}
			]);

	/**
	 * 数据存储
	 */
	 
	var record = Ext.data.Record.create([
	      {name :'groupNo',mapping : 'GROUP_NO'},
	      {name :'custId',mapping : 'CUST_ID'},
	      {name : 'groupName',mapping :'GROUP_NAME'},
	      {name:'groupRootCustName1', mapping :'CUST_ZH_NAME'},
	      {name : 'groupHostOrgNoName',mapping :'GROUP_HOST_ORG_NO_NAME'},
	      {name : 'parentCustZhName',mapping : 'PARENT_CUST_ZH_NAME'},
	      {name :'memberType',mapping : 'MEMBER_TYPE'},
	      {name : 'upName',mapping :'UP_NAME'},
	      {name : 'creditOpinion',mapping : 'CREDIT_OPINION'},
	      {name :'appStatus1',mapping : 'APP_STATUS'},
	      {name :'APP_STATUS_ORA'},
	      {name :'appStatus',mapping : 'APP_STATUS'},
	      {name :'appDate',mapping : 'APP_DATE'},
	      {name :'submitDate',mapping : 'SUBMIT_DATE'},
	      {name :'id',mapping : 'ID'}
	      
	]);
	

	
	var groupMemberStore = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/groupMemberAppInfoQuery.json'
		}),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'id',
			messageProperty : 'message',
			root : 'json.data',
			totalProperty : 'json.count'
		}, record)
	});


	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 100, '100条/页' ], [ 200, '200条/页' ], [ 500, '500条/页' ],
					[ 1000, '1000条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		editable : false,
		width : 85
	});

	// 默认加载数据
	groupMemberStore.load({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
	});

	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()), groupMemberStore.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});

	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : groupMemberStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});

	// 修改基本信息展示的form
	var appGroupInfoForm = new Ext.form.FormPanel({
		labelWidth : 80,
		height : 300,
		frame : true,
		region : 'center',
		autoScroll : true,
		buttonAlign : "center",
		items : [{ 
			xtype:'fieldset',
			title: '待客户概要信息', 
			layout : 'column',
			items : [
					{
							columnWidth : .33,
							layout : 'form',
							items : [{
										columnWidth:.25,
										xtype:'textfield',
										readOnly : true,
										labelStyle: 'text-align:right;',
										name : 'groupName',
										triggerAction:'all',
										anchor:'90%',
										fieldLabel : '集团母公司'
									}]
						},{
							columnWidth : .33,
							layout : 'form',
							items : [{
										columnWidth:.25,
										xtype:'textfield',
										readOnly : true,
										labelStyle: 'text-align:right;',
										name : 'parentCustZhName',
										triggerAction:'all',
										anchor:'90%',
										fieldLabel : '申请企业名称'
									}]
						},{
							columnWidth : .33,
							layout : 'form',
							items : [
								{
								store: appStatusStore,
								xtype : 'combo',
								name : 'appStatus',
								hiddenName : 'appStatus',
								labelStyle: 'text-align:right;',
								fieldLabel : '状态',
								valueField:'key',
								displayField:'value',
								mode : 'local',
								readOnly : true,
								typeAhead: true,
								forceSelection : true,
								resizable:true,
								triggerAction: 'all',
								emptyText:'请选择',
								selectOnFocus:true,
								width : '100',
								anchor : '90%'
								                 }

							    ]
						},{
							columnWidth : .33,
							layout : 'form',
							items : [{
										columnWidth:.25,
										xtype:'textfield',
										readOnly : true,
										name : 'id',
										hidden : true,
										triggerAction:'all',
										anchor:'90%',
										fieldLabel : 'id'
									}]
						}
						]
		},{ 
			xtype:'fieldset',
			title: '主办行意见', 
			layout : 'column',
			items : [{
				columnWidth : .99,
				layout : 'form',
				items : [{
							columnWidth:.80,
							xtype:'textarea',
							name : 'creditOpinion',
							readOnly:false,
							id : 'creditOpinion',
							triggerAction:'all',
							allowBlank : false,
							maxLength : '150',
							minLength : '0',
							anchor:'90%'
//							fieldLabel : '意见'
						}]
			}
						]
		},{ 
			xtype:'fieldset',
			title: '总行意见', 
			layout : 'column',
			items : [{
				columnWidth : .99,
				layout : 'form',
				items : [{
							columnWidth:.80,
							xtype:'textarea',
							readOnly:true,
							name : 'hostOpinion',
							id : 'hostOpinion',
							triggerAction:'all',
							anchor:'90%',
//							allowBlank : false,
							maxLength : '150',
							minLength : '0'
//							fieldLabel : '意见'
						}]
			}
						]
		}],buttons : [
					              {text : '通过',
										handler : function() {
											var opinion1 = Ext.getCmp('hostOpinion').getValue();
											var opinion2 = Ext.getCmp('creditOpinion').getValue();
											if(opinion1.length>150||opinion2.length>150){
												Ext.Msg.alert("提醒","您输入的意见太长，请输出150字以内");
											}else{	
												if(!appGroupInfoForm.getForm().isValid()){
													Ext.Msg.alert("系统提醒","输入有误，请重新输入!");
												}else{
											var appStatus = appGroupInfoForm.getForm().getFieldValues().appStatus;
											var id = appGroupInfoForm.getForm().getFieldValues().id;
											var creditOpinion = appGroupInfoForm.getForm().getFieldValues().creditOpinion;
											var hostOpinion = appGroupInfoForm.getForm().getFieldValues().hostOpinion;
											if((!(__units=="10001"||__units=="00001")&&appStatus=="1")||((__units=="10001"||__units=="00001")&&appStatus=="2")||((__units=="10001"||__units=="00001")&&appStatus=="1"))
											{
												Ext.Ajax.request({
												url : basepath + '/GroupMemberForAppAction.json',
												method : 'POST',
												params :{
													creditOpinion : creditOpinion,
													hostOpinion : hostOpinion,
													id : id,
													appStatus:appStatus
												},
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												success :checkResult,
												failure :checkResult
											});
											appGroupInfoForm.getForm().reset();
											appGroupInfoWindow.hide();
											
											function checkResult(response) {
												var resultArray = Ext.util.JSON.decode(response.status);
												var resultError = response.responseText;
												if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
													Ext.Msg.alert('提示', '已成功');
													groupMemberStore.reload({
											params : {
											start : 0,
											limit :bbar.pageSize
											                    }
											                });
												} else {
													Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
													groupMemberStore.reload({
											params : {
											start : 0,
											limit :bbar.pageSize
											                    }
											                });
												}
											};
												}else{
													Ext.Msg.alert("系统提醒","您无权限！");
												}
											}
											}
										}

									},{
										text : '拒  绝',
										handler : function() {
											var opinion1 = Ext.getCmp('hostOpinion').getValue();
											var opinion2 = Ext.getCmp('creditOpinion').getValue();
											
											var appStatus = appGroupInfoForm.getForm().getFieldValues().appStatus;
											var id = appGroupInfoForm.getForm().getFieldValues().id;
											var remark = appGroupInfoForm.getForm().getFieldValues().remark;
											if(opinion1.length>150||opinion2.length>150){
												Ext.Msg.alert("提醒","您输入的意见太长，请输出150字以内");
											}else{		
											Ext.Ajax.request({

												url : basepath + '/GroupMemberAction.json',
												method : 'POST',
//												params:appGroupInfoForm.getForm().getFieldValues(),
												params :{
													remark : remark,
													id : id,
													appStatus:appStatus
												},
												//form : appGroupInfoForm.getForm().id,
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												success :checkResult,
												failure :checkResult
											});
											appGroupInfoForm.getForm().reset();
											appGroupInfoWindow.hide();
											
											function checkResult(response) {
												var resultArray = Ext.util.JSON.decode(response.status);
												var resultError = response.responseText;
												if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
													Ext.Msg.alert('提示', '操作成功');
													groupMemberStore.reload({
											params : {
											start : 0,
											limit :bbar.pageSize
											                    }
											                });
												} else {
													Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
													groupMemberStore.reload({
											params : {
											start : 0,
											limit :bbar.pageSize
											                    }
											                });
												}
											};
										}
										}
									}, {
										text : '取  消',
										handler : function() {
											appGroupInfoWindow.hide();
										}
									} ]

	});
	debugger;
	if(__units=="10001"||__units=="00001"){
		 Ext.getCmp('hostOpinion').setReadOnly(false);
	}
	
	
	// 修改窗口展示的from
	var appGroupInfoPanel = new Ext.Panel({
		labelWidth : 80,
		height : 300,
		layout : 'fit',
		autoScroll : true,
		buttonAlign : "center",
		items : [ appGroupInfoForm ]
	});
	
	// 定义修改窗口
	var appGroupInfoWindow = new Ext.Window({
		title : '集团客户',
		plain : true,
		layout : 'fit',
		width : 900,
		height : 400,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		border : false,
		items : [ appGroupInfoPanel ]
	});
	
	// 展示修改窗口
	function editInit() {
		appGroupInfoWindow.show();
	}
	
	var groupMemberGrid = new Ext.grid.GridPanel({
				height : 500,
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : groupMemberStore, // 数据存储
				stripeRows : true, // 斑马线
				cm : groupMemberCM, // 列模型
				sm : groupMemberSM, // 复选框
				tbar : [
				{
					text:'审批',
					handler : function() {

						var selectLength = groupMemberGrid
								.getSelectionModel()
								.getSelections().length;
						var selectRe = groupMemberGrid
								.getSelectionModel()
								.getSelections()[0];
						if (selectLength != 1) {
							Ext.Msg.alert("系统提醒","请选择一条记录！");
						} else {
							var tempAppStatus = groupMemberGrid.getSelectionModel().selections.items[0].data.appStatus;
							if(tempAppStatus>=3){
								Ext.Msg.alert("提醒","该客户已经【通过审批】，不需要再审批！");
							}else{
								if(tempAppStatus<=0){
									Ext.Msg.alert("提醒","该客户为【拒绝】状态，请先提交申请！");
								}else{
								appGroupInfoForm.getForm().loadRecord(selectRe);
								document.getElementById('idStr').value = selectRe.data.id;
								editInit();
								}
							
						}}
					}
				}/*,
				{
					text:'退回'
				}*/
				], // 表格工具栏
				bbar : bbar,// 分页工具栏
				viewConfig : {
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	groupMemberGrid.on('rowdblclick', function(grid, rowIndex, event) {
//		 window.location.href = 'groupView.jsp' ;
	});
	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'border',
				items: [
					groupMemberSearchForm,
			    
			     groupMemberGrid
			    ] 
			});

});