	//*********************
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
			return checkLevel;
			
		}else if(num==0){
			
			var nodeNum3=0;
			var tempChecked3Node;
			for(var b=0;b<level3Nodes.length;b++){
				if(level3Nodes[b].getUI().isChecked()){
					tempChecked3Node = level3Nodes[b];
					nodeNum3++;
					if(nodeNum3>=2){
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
	 
	 var orgTreePanel1 = new Ext.tree.TreePanel(
			 	{	 		
			 	autoScroll:true,
			 	height:350,
			 	width:200,
				listeners:{
					'click':function(node)
					{
						node.getUI().toggleCheck(true);
						var level = spanBank(orgTreePanel1);
						var checkedNodes = getCheckedNode(orgTreePanel1);
						tempCombo = yuangongSearch.getForm().findField("UNITNAME");
						alert(tempCombo);
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
								var rootNode = orgTreePanel1.root;
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
	//*****************************************
var yuangongSearch = new Ext.form.FormPanel({
				labelWidth : 105,
				title : "成员信息查询",
				labelAlign : 'right',
				height : 100,
				frame : true,
				region : 'north',
				autoScroll : true,
				items : [ {
					layout : 'column',
					items : [
							{
								columnWidth : .45,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '员工号',
									id : 'USER_ID',
									name : 'USER_ID',
									width : '100',
									anchor : '100%'
								} ]
							},{
								columnWidth : .45,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '成员姓名',
									id : 'USER_NAME',
									name : 'USER_NAME',
									width : '100',
									anchor : '100%'
								} ]
							},{
								columnWidth : .3,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : 'sdf',
									hidden :true,
									id : 'MKT_TEAM_ID1',
									name : 'MKT_TEAM_ID1',
									width : '100',
									anchor : '100%'
								} ]
							}/*,{
								columnWidth : .3,
								layout : 'form',
								items : [ {
									xtype:'combo',					
									store : new Ext.data.SimpleStore( {
										fields : [],
										data : [ [] ]
									}),
									name:'UNITNAME',
									id:'UNITNAME',
									emptyText : '请选择',
									fieldLabel : '归属机构',
									editable:false,
									resizable:true,
									anchor : '90%',
									mode : 'local',
									triggerAction : 'all',
									maxHeight : 390,
									// 下拉框的显示模板,addDeptTreeDiv作为显示下拉树的容器
									tpl:"<tpl for='.' <div style='height:390px'> <div id='addOrgTreeDivForAdd1'></div></div></tpl>",
//									tpl:"<div style='height:390px'> <div id='addOrgTreeDivForAdd'></div></div>",
									onSelect : Ext.emptyFn,
									listeners:{
										'expand':function(combo){			
//											combo.doLayout();
											orgTreePanel1.render('addOrgTreeDivForAdd1');
										}
									}
								} ]
							}*/]
				} ],
				buttonAlign : 'center',
				buttons : [ {
					text : '查询',
					handler : function() {
						var conditionStr = yuangongSearch.getForm().getFieldValues(
								false);
						yuangongStore.baseParams = {
								"condition" : Ext.encode(conditionStr)
							};
						yuangongStore.load({
							params : {
								'marketTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
								start : 0,
								limit : parseInt(pagesize_combo.getValue())
							}
						});

					}
				},{
					text : '重置',
					handler : function() {
						yuangongSearch.getForm().reset();
						 var ssss1 = Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId;
							Ext.getCmp("MKT_TEAM_ID1").setValue(ssss1);
					}
				} ]

			});

			 // 复选框
			var mtmsm = new Ext.grid.CheckboxSelectionModel();

			// 定义自动当前页行号
			var mtmrownum = new Ext.grid.RowNumberer({
						header : 'NO',
						width : 28
					});
			
			// 定义列模型
			var mtmcm = new Ext.grid.ColumnModel([mtmrownum,mtmsm,
			        {header : 'id',dataIndex : 'id',sortable : true,width : 200,hidden :true},
			        {header : '员工号',dataIndex : 'userId',sortable : true,width : 200},
			        {header : '员工姓名',dataIndex : 'userName',sortable : true,width : 200},
			        {header : '角色名称',dataIndex : 'role',sortable : true,width : 200},
			        {header : '归属机构',dataIndex : 'unitName',sortable : true,width : 200}
					]);
			
			var SstoreRecord = Ext.data.Record.create([
  				           {name: 'id', mapping: 'ID'},
                           {name: 'userId', mapping: 'ACCOUNT_NAME'},
                           {name: 'userName', mapping: 'USER_NAME'},
                           {name: 'role', mapping : 'ROLE_NAME'}, 
                           {name: 'unitName', mapping: 'ORG_NAME'}
			             ]);
			
			 var Sstore = new Ext.data.Store({
				    restful : true,
				    baseParams:{
						'org_id':__units,
						'role_id':''
					},
					proxy : new Ext.data.HttpProxy({
						url : basepath + '/orgusermanage.json' 
//						success : function(response) {
//							Ext.Msg.alert('提示', response.responseText);
//						},
//						failure : function(response) {
//							Ext.Msg.alert('提示','加入失败' );
//						}

					}),
					reader : new Ext.data.JsonReader({
						successProperty: 'success',
				        idProperty: 'ID',
				        messageProperty: 'message',
						root : 'json.data',
						totalProperty: 'json.count'
					},SstoreRecord)
				});
			 
			var queryMarketTeamMemberForm = new Ext.form.FormPanel({
				labelWidth : 100, // 标签宽度
				frame : true, // 是否渲染表单面板背景色
				labelAlign : 'right', // 标签对齐方式
				region:'north',
				// bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
				buttonAlign : 'center',
				height : 80,
				items : [{
					layout : 'column',
					border : false,
					items : [{
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '员工号',
							id : 'USER_ID1',
							name : 'USER_ID1',
							width : '100',
							anchor : '100%'
						} ]
					},{
						columnWidth : .5,
						layout : 'form',
						items : [ {
							xtype : 'textfield',
							fieldLabel : '员工姓名',
							id : 'USER_NAME1',
							name : 'USER_NAME1',
							width : '100',
							anchor : '100%'
						} ]
					}]
				}],
			buttons : [{
						text : '查询',
						handler : function() {
							var conditionStr = queryMarketTeamMemberForm.getForm().getFieldValues();
							Sstore.baseParams = {
									"condition" : Ext.encode(conditionStr),
									'org_id':__units,
									'role_id':''
								};
							Sstore.reload({
								  params : {
									  'mktTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
	                                   start : 0,
	                                   limit : sbbar.pageSize }} );
					
					   }},{
						text : '重置',
							handler : function() {
								queryMarketTeamMemberForm.getForm().reset();
							}
						}]
			});

			
			//***********************

			// 每页显示条数下拉选择框
			var spagesize_combo = new Ext.form.ComboBox({
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
				forceSelection : true,
				width : 85
			});

			// 改变每页显示条数reload数据
			spagesize_combo.on("select", function(comboBox) {
				sbbar.pageSize = parseInt(spagesize_combo.getValue()),
				Sstore.reload({
					params : {
						start : 0,
						limit : parseInt(spagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var sbbar = new Ext.PagingToolbar({
				pageSize : parseInt(spagesize_combo.getValue()),
				store : Sstore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
			});

			//***********************
				
			
			
			// 表格实例
			var MarketTeamMemberGrid = new Ext.grid.GridPanel({
						height :310,
						width : 200,
						frame : true,
						autoScroll : true,
						region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
						store : Sstore, // 数据存储
						stripeRows : true, // 斑马线
						cm : mtmcm, // 列模型
						sm : mtmsm, // 复选框
						tbar :  [
									{
										text : '加入团队',
										iconCls : 'addIconCss',
										handler : function()  {
											 var selectLength = MarketTeamMemberGrid.getSelectionModel().getSelections().length;
											if(selectLength<1){
												Ext.Msg.alert("系统提醒","请选择至少一条记录");
												return false;
											}
											var checkedNodes = MarketTeamMemberGrid.getSelectionModel().selections.items;
											var sujm = Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId;
											var json = {'userId':[]};
											var json1 = {'marketTeamId':[]};
											for(var i=0;i<checkedNodes.length;i++){
												json.userId.push(checkedNodes[i].data.userId);
												checkedNodes[i].data.marketTeamId=sujm;
												json1.marketTeamId.push(checkedNodes[i].data.marketTeamId);
											}
											
											Ext.Ajax.request({

												url : basepath + '/TeamCustomerManager.json?a=1',
												method : 'POST',
												params : {
													
													'userId' :Ext.encode(json),
													'marketTeamId':Ext.encode(json1),
													'operate': 'add'
												},
												//form : editGroupInfoForm.getForm().id,
												waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
												success :checkResult,
												failure :checkResult
											});
											function checkResult(response) {
												var resultArray = Ext.util.JSON.decode(response.status);
												var resultError = response.responseText;
												if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
													Ext.Msg.alert('提示', '操作成功');
													Sstore.reload({
														  params : {
															  'mktTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
							                                   start : 0,
							                                   limit : sbbar.pageSize }});
													addMarketTeamMemberWindow.hide();
													
													yuangongStore.reload({
											params : {
											'marketTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
											start : 0,
											limit :bbar.pageSize
											                    }
											                });
												}else{
													if(resultArray == 403){
														Ext.Msg.alert('提示', response.responseText);
														}
													else {
													Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
													yuangongStore.reload({
											params : {
											'marketTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
											start : 0,
											limit :bbar.pageSize
											                    }
											                });
												}
											}
											};
//											}
										}
									}], // 表格工具栏
						bbar:sbbar,
						viewConfig:{
							   forceFit:false,
							   autoScroll:true
							},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
			
			var mtmsm1 = new Ext.grid.CheckboxSelectionModel();
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'NO',
				width : 28
			});
			//列模型 
			var columns = new Ext.grid.ColumnModel([ rownum,mtmsm1,{
				header : 'id', // 列标题
				dataIndex : 'id', // 数据索引:和Store模型对应
				sortable : true,
				hidden : true,
				width : 150
			// 是否可排序
			},{
				header : '员工号', // 列标题
				dataIndex : 'userId', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			}, {
				header : '成员姓名', // 列标题
				dataIndex : 'userName', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			}, {
				header : '联系电话',
				dataIndex : 'telephone',
				sortable : true,
				width : 150
			}, {
				header : '归属机构 ', // 列标题
				dataIndex : 'unitName', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			}, {
				header : '加入时间 ', // 列标题
				dataIndex : 'joinDate', // 数据索引:和Store模型对应
				sortable : true,
				width : 150
			// 是否可排序
			}]);
			
			var record = Ext.data.Record.create([
			     {name: 'id', mapping: 'ID'},                 
			     {name: 'userId', mapping: 'USER_ID'},
			     {name :'userName', mapping :'USERNAME'},
                 {name: 'joinDate', mapping: 'JOIN_DATE'},        
                 {name:'telephone',mapping :'TELEPHONE'},
                 {name: 'unitName', mapping: 'UNITNAME'}
                 ]);
			
			var yuangongStore = new Ext.data.Store({
				restful : true,
				id:'su_yuangongStore',
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/marketTeamMemberInfoQuery.json',
					failure : function(response){
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						}
					}
//					params : {
//					'marketTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
//					start : 0,
//					limit : parseInt(pagesize_combo.getValue())
//					}
					,success : function(response) {
				}
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'ID',
			        messageProperty: 'message',
					root : 'json.data',
					totalProperty: 'json.count'
				},record)
			});
			
			// 每页显示条数下拉选择框
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 100, '100条/页' ], [ 200, '200条/页' ], [ 500, '500条/页' ],
							[ 1000, '1000条/页' ]]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '100',
				editable : false,
				width : 85
			});

//			// 默认加载数据
//			yuangongStore.load({
//				params : {
//					start : 0,
//					limit : parseInt(pagesize_combo.getValue())
//				}
//			});

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()),
				yuangongStore.reload({
					params : {
						'marketTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : yuangongStore,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			
			var SMEOCyPanel = new Ext.grid.GridPanel(
					{
						store : yuangongStore,
						title : "成员列表",
						frame : true,
						height:300,
						cm : columns,
						sm : mtmsm1,
						stripeRows : true,
						tbar : [
								{
									text : '新增',
									iconCls : 'addIconCss',
									handler : function() {
										var tempId = Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.teamLeaderId;
										if(tempId!=__userId){
											Ext.Msg.alert("系统提醒","您不是该团队负责人，无权维护该项！");
											return false;
										}
										addMarketTeamMemberInit();
									}
								},
								'-',
								{
									text : '删除',
									iconCls : 'deleteIconCss',
									handler : function() {
										var tempId = Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.teamLeaderId;
										if(tempId!=__userId){
											Ext.Msg.alert("系统提醒","您不是该团队负责人，无权维护该项！");
											return false;
										}
										
										var  _record = SMEOCyPanel.getSelectionModel().getSelected();
										
//										var selectRe = SMEOCyPanel.getSelectionModel().getSelections()[0];
//										var marketPlanStatement = selectRe.data.marketPlanStatement;
										if (!_record) {
										 	Ext.MessageBox.alert('删除操作', '请选择要操作的一列！');
										} else {
											
												if (confirm("确定删除吗?")) {
													
													var checkedNodes = SMEOCyPanel.getSelectionModel().selections.items;
													var json={'id':[]};
													for(var i=0;i<checkedNodes.length;i++)
									    			{
									    				json.id.push(checkedNodes[i].data.id);
									    			}

													Ext.Ajax.request({
//																url : basepath+ '/TeamCustomerManager/'+ selectRe.get('id'),
//																method : 'DELETE',
														
																url:basepath+'/TeamCustomerManager.json?a=2',
									                            method: 'POST',
									                            params : {
									    							cbid:Ext.encode(json),
									    							'operate':'delete'
									    						},
																waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
																success :checkResult,
																failure :checkResult
															});
													function checkResult(response) {
														var resultArray = Ext.util.JSON.decode(response.status);
														var resultError = response.responseText;
														debugger;
														if ((resultArray == 200 ||resultArray == 201||resultArray == 404)) {
															Ext.Msg.alert('提示', '操作成功');
															yuangongStore.reload({
													params : {
														'marketTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
													start : 0,
													limit :bbar.pageSize
													                    }
													                });
														} else{
															if(resultArray == 403){
																Ext.Msg.alert('提示', response.responseText);
																}
															else {
															Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
															yuangongStore.reload({
													params : {
														'marketTeamId':Ext.getCmp("sujm").getSelectionModel().selections.items[0].data.marketTeamId,
													start : 0,
													limit :bbar.pageSize
													                    }
													                });
														}
													}};
												};
											}
									}
								}],
						region : 'center',
						frame : true,
						bbar : bbar,// 分页工具栏
						viewConfig : {
						// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
			

			// 定义展示员工基本信息窗口
			var addMarketTeamMemberWindow = new Ext.Window({
				title : '客户经理查询',
				plain : true,
				layout : 'border',
				width : 800,
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
				buttonAlign : 'right',
				border : false,
				items: [queryMarketTeamMemberForm,MarketTeamMemberGrid]
			});
			
			function addMarketTeamMemberInit() {
				addMarketTeamMemberWindow.show();
			}

			
			
			