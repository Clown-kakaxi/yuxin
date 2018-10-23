Ext.onReady(function(){
	var loader1 = new Com.yucheng.bcrm.ArrayTreeLoader({
		parentAttr : 'PARENT_ID',
		locateAttr : 'NODEID',
		rootValue : '0',
		textField : 'NAME',
		idProperties : 'NODEID'
	});
	Ext.Ajax.request({
		url : basepath + '/queryagilequery.json',
		method : 'GET',
		success : function(response) {
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			loader1.nodeArray = nodeArra.JSON.data;
			nodeArrays=nodeArra.JSON.data;
			for ( var item in loader1.nodeArray) {
				if (typeof loader1.nodeArray[item] === 'object') {
					if (loader1.nodeArray[item].TABLES == '2')
						loader1.nodeArray[item].NODEID = 'b' + loader1.nodeArray[item].NODEID;
				}
			}
			var children = loader1.loadAll();
			treeOfPoroduct1.appendChild(children);
			treeOfPoroduct1.root.deepExpand(false,function(){ 
				Ext.Ajax.request({
					url:basepath+'/queryagilequery!queryGroupCondition.json?groupId='+oCustInfo.groupId,
					method: 'GET',
					success : function(response) {
						simple1.removeAllItems();
						simple12.removeAllItems();
						var conditionData = Ext.util.JSON.decode(response.responseText);
						var conditionArray=conditionData.JSON.data;
						if(conditionArray.length>0){
							ifadd = 'false';
							Ext.each(conditionArray,function(con){
								var node = treeOfPoroduct1.root.findChild("id", "b"+con.SS_COL_ITEM, true);
								if(node){
									simple1.addItems(node,con.SS_COL_OP,con.SS_COL_VALUE);
								}
							});
							if(conditionArray[0].SS_COL_JOIN=='true'){
								radio1.items.items[0].items.items[0].setValue(true);
								right_panel1.conditionJoinType = 'true';
							}else{
								radio1.items.items[1].items.items[0].setValue(true);
								right_panel1.conditionJoinType = 'false';
							}
						}else{
							ifadd = 'true';
						}
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示','您没有此权限!');
						} else {
							Ext.Msg.alert('提示','操作失败!');
						}
					}
				});
				
				
				},this);
		}
	});
	
	 Ext.override(Ext.tree.TreeNode, {  
	     deepExpand : function(anim, callback, scope){  
	        // 先展开本节点  
	         this.expand(false, anim, function(){  
	            // 然后展开子节点  
	             var cs = this.childNodes,   
	                 expanded = 0,  
	                len = cs.length,  
	                 taskDone = function(){  
	                     // 每展开成功一个子节点，计数+1  
	                     expanded++;  
	                     // 如果所有子节点都展开，调用最终回调  
	                     if(expanded >= len){  
	                         this.runCallback(callback, scope || this, [this]);  
	                     }  
	                 };  
					 if(len<=0){  
					     taskDone.call(this);    
					     return;  
					 } 

	            // 递归展开  
	             for(var i = 0, len = cs.length; i < len; i++) {  
	                 cs[i].deepExpand(anim, taskDone, this);  
	             }  
	         }, this);  
	     }  
	 }); 
	var treeOfPoroduct1 = new Com.yucheng.bcrm.TreePanel({
		title : '条件字段',
//		width : 350,
		height : 490,
		autoScroll : true,
		rootVisible : false,
		ddGroup : 'rightPanel1',
		split : true,
		enableDrag:true,
		/** 虚拟树形根节点 */
		root : new Ext.tree.AsyncTreeNode({
			id : 'root',
			expanded : true,
			text : '客户视图',
			autoScroll : true,
			children : []
		}),
		resloader : loader1
	});
	
	
		
	
	
		//查询条件对象
		var simple1 = new Com.yucheng.crm.query.SearchPanel({
			title : '查询条件',
			listeners:{
				"activate":function(){
					this.doLayout();
				}
			}
		});
		//结果列对象
		var simple12 = new Com.yucheng.crm.query.ColumnsPanel({
			title : '显示列',
			hidden:true,
			listeners:{
				"activate":function(){
					this.doLayout();
				}
			}
		});
		
		var tabmain1 = new Ext.TabPanel({
			autoScroll : true,
			id : 'tabmain1',
			height : 350,
			activeTab : 0,
			frame : true,
			defaults : {
				autoHeight : true
			},
			items : [simple1/*,simple12*/]
		});
		//条件链接符面板
		var radio1 = new Ext.Panel({
			layout : 'column',
			border : false,
			items : [ {
				columnWidth : .09,
				layout : 'form',
				labelWidth : 8,
				border : false,
				items : [ new Ext.form.Radio({
					boxLabel : "与",
					labelStyle: 'text-align:right;',
					id : "Radio11",
					name : "a",
					checked : true,
					listeners : {
						check : function(r,v){
							if(v)
								right_panel1.conditionJoinType = 'true';
							else
								right_panel1.conditionJoinType = 'false';
						}
					}
				})]
			}, {
				columnWidth : .09,
				layout : 'form',
				labelWidth :8,
				border : false,
				items : [ new Ext.form.Radio({
					boxLabel : "或",
					labelStyle: 'text-align:right;',
					id : "Radio12",
					name : "a"
				}) ]
			} ]
		});
		
		//保存方案
		var fnBatchSave1= function(){
			var solutionAttr = {};
			solutionAttr.ss_results = simple12.getResultsIds();
			solutionAttr.ss_sort = simple12.getSortTypes();
			var conditions = simple1.getConditionsAttrs();
			Ext.Ajax.request({
				url:basepath+'/agilesearch!create1.json',
				success : function(response) {
					Ext.Msg.alert('提示', '操作成功');
					ifadd = 'false';
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
					if(resultArray == 403) {
						Ext.Msg.alert('提示','您没有此权限!');
					} else {
						Ext.Msg.alert('提示','操作失败!');
					}
				},
				params : {
					solutionAttr:Ext.encode(solutionAttr),
					conditionCols : Ext.encode(conditions),
					group_id:oCustInfo.groupId,
					flag:ifadd,
					group_name:oCustInfo.groupName,
					'radio':right_panel1.conditionJoinType
				}
			});
		};
		
		//查询结果数据源
		var getCustStore = new Ext.data.Store({
			restful : true,
			url:basepath+'/queryagileresult.json',
			reader:new Ext.data.JsonReader({
				successProperty: 'success',
				messageProperty: 'message',
				fields : [{
					name:'CUST_ID'
				}]
			})
		});
		
		/**
	 * 查询结果分页
	 */ 
	var getCustsize_combo = new Ext.form.ComboBox({
		  	name : 'pagesize',
		  	triggerAction : 'all',
		  	mode : 'local',
		  	store : new Ext.data.ArrayStore({
		  				fields : ['value', 'text'],
		  				data : [ 
		  						[ 20, '20条/页' ],[ 50, '50条/页' ],[ 100, '100条/页' ], [ 250, '250条/页' ],
		  						[ 500, '500条/页' ] ]
		  			}),
		  	valueField : 'value',
		  	displayField : 'text',
		  	value : '20',
		  	editable : false,
		  	width : 85,
		  	listeners : {
				select : function(combo){
					custBar.pageSize = parseInt(getCustsize_combo.getValue());
					getCustStore.reload({
						params : {
							groupMemberType:oCustInfo.groupMemberType,
							start : 0,
							limit : parseInt(getCustsize_combo.getValue())
						}
					});
				}
			}
		});
		
	//查询结果列模型
	var getCustCm = new Ext.grid.ColumnModel(simple12.getResultColumnHeaders());
	getCustCm.on("configchange",function(){
		getCustGrid.view.refresh(true);
	}); 

	//分页工具栏
		var custBar = new Ext.PagingToolbar({
			pageSize : getCustsize_combo.getValue(),
			store : getCustStore,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', getCustsize_combo]
		});
		
	//查询结果列表面板
		var getCustGrid = new Ext.grid.GridPanel({
			frame : true,
			region : 'center', // 返回给页面的div
			store : getCustStore, // 数据存储
			stripeRows : true, // 斑马线
			cm : getCustCm, // 列模型
			sm : simple12.selectModel,
			bbar : custBar,// 分页工具栏
			viewConfig : {
			},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});


	//查询结果窗口
		var resultWindow = new Ext.Window({
			layout : 'fit',
			maximized : true,
			closable : true,// 是否可关闭
	        title : '查询结果展示',
			modal : true,
			closeAction : 'hide',
			titleCollapse : true,
			buttonAlign : 'center',
			border : false,
			animCollapse : true,
			animateTarget : Ext.getBody(),
			constrain : true,
			items : [{
				layout:'border',
				items:[
					getCustGrid
				]
			}],
			buttons : [{
				text : '关闭',
				handler : function() {
					resultWindow.hide();
					getCustStore.removeAll();
				}
			}]
		});
		
		function getResultReaderMetas1(){
			var readerMetas = {
					successProperty: 'success',
					messageProperty: 'message',
					idProperty: 'CUST_ID',
					root:'json.data',
					totalProperty: 'json.count'
			};
			var readerFields = [];
			Ext.each(simple12.resultColumns,function(column){
				var t = {};
				t.name = column.columnName+'_'+column.columnTotle;
				readerFields.push(t);
				if(column.columnLookup){
					var t2 = {};
					t2.name = column.columnName+'_'+column.columnTotle+'_ORA';
					readerFields.push(t2);
				}
			});
			readerMetas.fields = readerFields;
			return readerMetas;
		
		};
		
		function getResultColumnHeaders1() {
			var columnHeaders = [];
			Ext.each(simple12.resultColumns,function(column){
				
				var columnHead = {};
				
				columnHead.header = column.textName;
				columnHead.hidden = column.hidden;
				columnHead.hideable  = !column.hidden;
				columnHead.dataIndex = column.columnName+'_'+column.columnTotle;
				columnHeaders.push(columnHead);
				
				if(column.columnLookup){
					var columnHeadLooked = {};
					columnHeadLooked.header = column.textName;
					columnHeadLooked.hidden = column.hidden;
					columnHeadLooked.hideable  = !column.hidden;
					columnHeadLooked.dataIndex = column.columnName+'_'+column.columnTotle+'_ORA';
					columnHeaders.push(columnHeadLooked);
					columnHead.hidden = true;
					columnHead.hideable = false;
				}
			});
			return columnHeaders;
		};
		
		//查询结果方法
		var fnSearchResult = function(){
			if(simple1.conditions.length==0){
				Ext.Msg.alert('提示', '未加入任何条件列！');
				return;
			}
			if(!simple1.getForm().isValid()){
				Ext.Msg.alert('提示', '查询条件输入有误！');
				return;
			}
			
			//初始化客户ID、客户名称、证件类型，证件号码，客户类型字段节点，从数据集树上获取
			if(QUERYUTIL.custBaseInfo.dbNode === false){
				QUERYUTIL.custBaseInfo.dbNode = treeOfPoroduct1.root.findChild('VALUE',QUERYUTIL.custBaseInfo.dbTable,true);
				QUERYUTIL.custBaseInfo.idNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.idColumn);
				QUERYUTIL.custBaseInfo.nameNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.nameColumn);
				QUERYUTIL.custBaseInfo.typeNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.typeColumn);
				QUERYUTIL.custBaseInfo.certTypeNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.certTypeColumn);
				QUERYUTIL.custBaseInfo.certNoNode = QUERYUTIL.custBaseInfo.dbNode.findChild('ENAME',QUERYUTIL.custBaseInfo.certNoColumn);
			}
			//结果列添加客户ID、名称、类型字段
			simple12.addItems(QUERYUTIL.custBaseInfo.idNode,'0',false,false);
			simple12.addItems(QUERYUTIL.custBaseInfo.nameNode,'0',false,false);
			simple12.addItems(QUERYUTIL.custBaseInfo.typeNode,'0',false,false);
			simple12.addItems(QUERYUTIL.custBaseInfo.certTypeNode,'0',false,false);
			simple12.addItems(QUERYUTIL.custBaseInfo.certNoNode,'0',false,false);
			//弹出窗口
			resultWindow.show();
			
			//根据查询结果列配置，修改查询结果数据源reader字段
			getCustGrid.store.reader.onMetaChange(getResultReaderMetas1());
			//根据查询结果列配置，修改查询结果列表面板列模型
			getCustGrid.colModel.setConfig(getResultColumnHeaders1(), false) ;
			
			
			//获取动态查询所需要的各个数据参数
			getCustGrid.store.baseParams = {
				conditionAttrs : Ext.encode(simple1.getConditionsAttrs()),
				results :  Ext.encode(simple12.getResults()),
				radio : right_panel1.conditionJoinType
			};
			
			//查询数据
			getCustGrid.store.load({
				params: {
					groupMemberType:oCustInfo.groupMemberType,
					start : 0,
					limit : getCustsize_combo.getValue()
				}
			});
		};
		
		//右侧面板
		var right_panel1 = new Ext.Panel({
			conditionJoinType : 'true',//条件连接符数据，根据radio1对象点选情况
			frame : true,
			autoScroll : true,
			items : [ tabmain1,radio1],
			title : '查询设置',
			buttonAlign : 'center',
			buttons : [ {
				text : '保存',
				handler : function() {
					
					if(simple1.conditions.length==0){
						Ext.Msg.alert('提示', '未加入任何条件列！');
						return;
					}
					if(!simple1.getForm().isValid()){
						Ext.Msg.alert('提示', '查询条件输入有误！');
						return;
					}
						fnBatchSave1();
				}
			},{
				text : '试查询',
				handler : function() {
					fnSearchResult();
				}
			},{
				text : '返回客户列表',
				handler : function() {
					agileQueryPanel1.getLayout().setActiveItem(0);
					//查询数据
					custJustStore.load({
						params: {
							start : 0,
							limit : getCustsize_combo.getValue()
						}
					});
					
				}
			}]
		});
		right_panel1.on('afterrender',function(){
			/**数据字段拖拽代理*/
			new Ext.dd.DropTarget(right_panel1.body.dom, {
		    	ddGroup : 'rightPanel1',
		    	notifyDrop : function(ddSource, e, data) {
		    	if (!data.node.leaf) {
		    		return;
		    	}
		    	var changeFlag=false;
		    	if(tabmain1.activeTab==simple1){
		    		simple1.addItems(data.node);
		    		tabmain1.setActiveTab(1);	
		    		changeFlag = true;
		    	}
//		    	simple12.addItems(data.node);
		    	if(changeFlag){
		    		tabmain1.setActiveTab(0);	
		    	}
		    	return true;
		    	}
		    });
		});
		
		
		 
	     	
	     	
	
	var custJustStore = new Ext.data.Store({
		restful:true,	
		proxy : new Ext.data.HttpProxy({
		url:basepath+'/getJustCust.json',
			failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if(resultArray == 403) {
				Ext.Msg.alert('提示','您没有此权限!');
			} 
		}
		}),
		reader: new Ext.data.JsonReader({
			totalProperty : 'json.count',
			root:'json.data'
		}, [ 'ID','CUST_ID','CUST_ZH_NAME','CERT_TYPE','CERT_TYPE_ORA','CERT_NUM','CUST_LEV','CUST_TYP','CUST_TYP_ORA'])
	});
	    custJustStore.on('beforeload', function() {
		this.baseParams = {
				groupMemberType:oCustInfo.groupMemberType,
				groupId: oCustInfo.groupId
				
		  };
	    });
	
	// 每页显示条数下拉选择框
	var custJustpagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
								fields : ['value', 'text'],
								data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								         [ 100, '100条/页' ], [ 250, '250条/页' ],
								         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});
	var custJustnumber = parseInt(custJustpagesize_combo.getValue());
	custJustpagesize_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
		custJustBar.pageSize = parseInt(custJustpagesize_combo.getValue());
		custJustStore.reload({
			params : {
			start : 0,
			limit : parseInt(custJustpagesize_combo.getValue())
		}
		});
	});
	var custJustrownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	// 定义列模型
	var custJustCm = new Ext.grid.ColumnModel([custJustrownum,
		{header : 'ID', dataIndex : 'ID',sortable : true,width : 150,hidden:true,hideable:false}, 
        {header : '客户号', dataIndex : 'CUST_ID',sortable : true,width : 150}, 
        {header : '客户名称', dataIndex : 'CUST_ZH_NAME',sortable : true,width : 150 }, 
        {header : '证件类型',dataIndex : 'CERT_TYPE',sortable : true,width : 150,hidden:true,hideable:false},
        {header : '证件类型',dataIndex : 'CERT_TYPE_ORA',sortable : true,width : 150},
        {header : '证件号码',dataIndex : 'CERT_NUM',sortable : true,width : 150}, 
        {header : '客户类型',dataIndex : 'CUST_TYP',sortable : true,width : 150,hidden:true},
        {header : '客户类型',dataIndex : 'CUST_TYP_ORA',sortable : true,width : 150}
    ]);

	
	var custJustBar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : custJustnumber,
		store : custJustStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', custJustpagesize_combo]
	});
	
	
	var agileQueryPanel1=new Ext.Panel({
		width : document.body.scrollWidth,
		height : document.body.scrollHeight-40,
		layout:"card",
		activeItem: 0,
		autoScroll:true,
		layoutConfig: {
		animate: true 
		},
		items:[
		    {title:"匹配客户列表",
			layout : 'border',
			items : [{
		             region : 'center',
		             layout : 'fit',
		             items : [ new Ext.grid.GridPanel({
		 				frame : true,
						autoScroll : true,
						store : custJustStore, // 数据存储
						stripeRows : true, // 斑马线
						cm : custJustCm, // 列模型
						bbar : custJustBar,
				        tbar:[{
							text:'维护筛选方案',
							hidden:__hiddeAble,
							handler : function() {
								agileQueryPanel1.getLayout().setActiveItem(1);
						}
						}],
				        viewConfig : {
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					}) ]
		    }]
		},
		 {title:"筛选方案维护",
			layout : 'column',
	    	items : [ 
			{
	    		columnWidth : .25,
	    		layout : 'form',
	    		border : false,
	    		items : [ treeOfPoroduct1 ]
	    	}, {
	    		columnWidth : .7,
	    		layout : 'form',
	    		border : false,
	    		items : [ right_panel1 ]
	    	} ]	}
]
});
	
	
	

			
        var queryStr = '';
	 	  var batchAdd= function(){
		  var checkedNodes = customerInfoGrid.getSelectionModel().selections.items;
		  var json={'cust_id':[]};
		  var json1={'cust_zh_name':[]};
		  var json2={'mgr_id':[]};
		  var json3={'mgr_name':[]};
		  var json4={'institution':[]};
		  var json5={'institution_name':[]};
		  if(checkedNodes.length==0)
		  {
			  Ext.Msg.alert('提示', '未选择任何客户');
			  return false;
		  }
		  Ext.MessageBox.confirm('提示','确定将所选客户加入到该群吗?',function(buttonId){
			if(buttonId.toLowerCase() == "no"){
			return false;
			}else{
			for(var i=0;i<checkedNodes.length;i++)
		  {
			  json.cust_id.push(checkedNodes[i].data.CUST_ID);
			  json1.cust_zh_name.push(checkedNodes[i].data.CUST_ZH_NAME);
			  json2.mgr_id.push(checkedNodes[i].data.MGR_ID);
			  json3.mgr_name.push(checkedNodes[i].data.MGR_NAME);
			  json4.institution.push(checkedNodes[i].data.INSTITUTION);
			  json5.institution_name.push(checkedNodes[i].data.INSTITUTION_NAME);
		  }
		
		  Ext.Msg.wait('正在保存，请稍后......','系统提示');
		  Ext.Ajax.request({
			  url:basepath+'/groupmemberedit!saveData.json',
			  method: 'POST',
			  success : function(response) {
			  Ext.Msg.alert('提示', '加入成功');
			  groupLeaguerStore.reload({
		  		  params : {
				  start : 0,
				  limit : parseInt(groupLeaguerpagesize_combo.getValue())
				  }
				  });
			  customerInfoStore.reload({
				  params : {
				  start : 0,
				  limit : parseInt(customerInfopagesize_combo.getValue())
			      }
		});
		  },	
		  failure : function(response) {
			  var resultArray = Ext.util.JSON.decode(response.status);
			  if(resultArray == 403) {
				  Ext.Msg.alert('提示','您没有此权限!');
			  } else {
				  Ext.Msg.alert('提示','加入失败!');
			  }
		  },
		  params : {
			  'CUST_ID': Ext.encode(json),
			  'CUST_ZH_NAME': Ext.encode(json1),
			  'CUST_BASE_ID':oCustInfo.groupId,
			  'MGR_ID':Ext.encode(json2),
			  'MGR_NAME':Ext.encode(json3),
			  'INSTITUTION':Ext.encode(json4),
			  'INSTITUTION_NAME':Ext.encode(json5)
		  }});
			}});
		  
	  };	
	  
	  //客户群成员删除功能
	   var batchDelete=function(){
		  var checkedNodes = groupLeaguerGrid.getSelectionModel().selections.items;
				if(checkedNodes.length==0)
				{
					Ext.Msg.alert('提示', '未选择任何客户');
					return ;
				}
				var json={'id':[]};
				for(var i=0;i<checkedNodes.length;i++)
				{
					json.id.push(checkedNodes[i].data.ID);
				}
				var id =checkedNodes[0].data.ID;
				Ext.Ajax.request({url: basepath+'/groupmemberedit!dropData.json',
					method: 'POST',
					success : function(response) {
					Ext.Msg.alert('提示', '删除成功');
					groupLeaguerStore.reload({
							params : {
							start : 0,
							limit : parseInt(groupLeaguerpagesize_combo.getValue())
						}
						});
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
					if(resultArray == 403) {
						Ext.Msg.alert('提示','您没有此权限!');
					} else {
						Ext.Msg.alert('提示','删除失败!');
					}
				},
				params : {
					'delStr':Ext.encode(json)
				}
				});
	  };
	  
	//新增客户群成员的表格面板 
	var customerInfoTar = new Ext.Toolbar({
		items:[{
		    text:'归入客户群',
		    iconCls:'guiRuIconCss',
		    handler:function(){
		        batchAdd();
		    }
		}]
	});	
	
	//客户类型
	var customerTypeStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});	
	var searchPanel1 = new Ext.form.FormPanel( {
		title : '待加入客户',
		region : 'north',
	    height : 100,
	    labelWidth : 60,
	    labelAlign : 'right',
	    frame : true,
	    autoScroll : true,
	    layout : 'column',
	    items : [ {
	    	id:'custZhName',
	        columnWidth : .33,
	        layout : 'form',
	        items : [{
	            xtype : 'textfield',
	            fieldLabel : '客户名称',
	            name : 'CUST_ZH_NAME',
	            anchor : '95%'
	        }]
	    }, {
	    	id:'custId',
	        columnWidth : .33,
	        layout : 'form',
	        items : [{
	            xtype : 'textfield',
	            fieldLabel : '客户编号',
	            name : 'CUST_ID',
	            anchor : '95%'
	        } ]
	    }, {
	        columnWidth : .33,
	        layout : 'form',
	        items : [new Ext.form.ComboBox({
				hiddenName : 'CUST_TYP',
				name : 'CUST_TYP',
				fieldLabel : '客户类型',
				labelStyle: 'text-align:right;',
				triggerAction : 'all',
				store : customerTypeStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				hidden:false,
				forceSelection : true,
				typeAhead : true,
				emptyText:'请选择',
				resizable : true,
				anchor : '95%'
	        })]
	    }],
        buttonAlign : 'center',
        buttons : [ {
            text : '查询',
            handler : function() {
            if (!searchPanel1.getForm().isValid()) {
                Ext.Msg.alert("提醒", "请填写必填项");
                return false;
            }
            queryStr = searchPanel1.getForm().getValues(false);
            customerInfoStore.load( {
                params : {
                    start : 0,
                    limit : parseInt(customerInfopagesize_combo.getValue())
                }
            });
        }
        }, {
            text : '重置',
            handler : function() {
                searchPanel1.getForm().reset();
            }
        } ]
    });
        
        //判定，当群成员类型为对公或对私时，客户类型不展示
    	if('1'==oCustInfo.groupMemberType){
    		searchPanel1.form.findField('CUST_TYP').setVisible(false);
    		Ext.getCmp('custZhName').columnWidth=0.5;
    		Ext.getCmp('custId').columnWidth=0.5;
        }else if('2'==oCustInfo.groupMemberType){
        	searchPanel1.form.findField('CUST_TYP').setVisible(false);
        	Ext.getCmp('custZhName').columnWidth=0.5;
    		Ext.getCmp('custId').columnWidth=0.5;
        }else{
        	searchPanel1.form.findField('CUST_TYP').setVisible(true);
        	Ext.getCmp('custZhName').columnWidth=0.33;
    		Ext.getCmp('custId').columnWidth=0.33;
        };
        
	var custTypStore = new Ext.data.Store( {//客户类型代码
		restful : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	
	//客户群成员分页，列模型等
	var groupLeaguerSm = new Ext.grid.CheckboxSelectionModel();
	var groupLeaguerrownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	// 定义列模型
	var groupLeaguerCm = new Ext.grid.ColumnModel([groupLeaguerrownum,groupLeaguerSm,
		{header : 'ID', dataIndex : 'ID',sortable : true,width : 150,hidden:true,hideable:false}, 
        {header : '客户号', dataIndex : 'CUST_ID',sortable : true,width : 150}, 
        {header : '客户名称', dataIndex : 'CUST_ZH_NAME',sortable : true,width : 150 }, 
        {header : '证件类型',dataIndex : 'CERT_TYPE',sortable : true,width : 150,hidden:true,hideable:false},
        {header : '证件类型',dataIndex : 'CERT_TYPE_ORA',sortable : true,width : 150},
        {header : '证件号码',dataIndex : 'CERT_NUM',sortable : true,width : 150}, 
        {header : '客户类型',dataIndex : 'CUST_TYP',sortable : true,width : 150,hidden:true
//            renderer : function(value){
//            if(!value)
//                return '';
//            else if(custTypStore.query('key',value,false,true).first()==undefined)
//                return '';
//            else	
//                return custTypStore.query('key',value,false,true).first().get('value');
//            }
        },
        {header : '客户类型',dataIndex : 'CUST_TYP_ORA',sortable : true,width : 150}
    ]);

	/**
	 * 数据存储
	 */
	var groupLeaguerStore = new Ext.data.Store({
		restful:true,	
		proxy : new Ext.data.HttpProxy({
		url:basepath+'/groupmemberedit.json',
			failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if(resultArray == 403) {
				Ext.Msg.alert('提示','您没有此权限!');
			} 
		}
		}),
		reader: new Ext.data.JsonReader({
			totalProperty : 'json.count',
			root:'json.data'
		}, [ 'ID','CUST_ID','CUST_ZH_NAME','CERT_TYPE','CERT_TYPE_ORA','CERT_NUM','CUST_LEV','CUST_TYP','CUST_TYP_ORA'])
	});
		
	    groupLeaguerStore.on('beforeload', function() {
		this.baseParams = {
				querySign:'queryGroupMember',
				groupId: oCustInfo.groupId
				
		  };
	    });
	
	// 每页显示条数下拉选择框
	var groupLeaguerpagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
								fields : ['value', 'text'],
								data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								         [ 100, '100条/页' ], [ 250, '250条/页' ],
								         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});
	var groupLeaguernumber = parseInt(groupLeaguerpagesize_combo.getValue());
	groupLeaguerpagesize_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
		groupLeaguerBar.pageSize = parseInt(groupLeaguerpagesize_combo.getValue());
		groupLeaguerStore.reload({
			params : {
			start : 0,
			limit : parseInt(groupLeaguerpagesize_combo.getValue())
		}
		});
	});
	
	var groupLeaguerBar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : groupLeaguernumber,
		store : groupLeaguerStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', groupLeaguerpagesize_combo]
	});
	//end
	
	//待加入成员列表
	var customerInfoSm = new Ext.grid.CheckboxSelectionModel();
	var customerInforownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
debugger;
	// 定义列模型
	var customerInfoCm = new Ext.grid.ColumnModel([customerInforownum,customerInfoSm,
        {header : '客户号', dataIndex : 'CUST_ID',sortable : true,width : 150}, 
        {header : '客户名称', dataIndex : 'CUST_ZH_NAME',sortable : true,width : 150 }, 
        {header : '归属客户经理ID', dataIndex : 'MGR_ID',sortable : true,width : 150 ,hidden:true,hideable:false},
        {header : '归属客户经理', dataIndex : 'MGR_NAME',sortable : true,width : 150,hidden:true,hideable:false }, 
        {header : '归属机构ID', dataIndex : 'INSTITUTION',sortable : true,width : 150 ,hidden:true,hideable:false},       
        {header : '客户归属机构', dataIndex : 'INSTITUTION_NAME',sortable : true,width : 150 ,hidden:true,hideable:false},
        {header : '客户类型',dataIndex : 'CUST_TYP',sortable : true,width : 150,hidden:true
//          renderer : function(value){
//          if(!value)
//              return '';
//          else if(custTypStore.query('key',value,false,true).first()==undefined)
//              return '';
//          else	
//              return custTypStore.query('key',value,false,true).first().get('value');
//          }
      },
      {header : '客户类型',dataIndex : 'CUST_TYP_ORA',sortable : true,width : 150}
    ]);

	/**
	 * 数据存储
	 */
	var customerInfoStore = new Ext.data.Store({
		restful:true,	
		proxy : new Ext.data.HttpProxy({
		url:basepath+'/groupmemberedit.json',
			failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if(resultArray == 403) {
				Ext.Msg.alert('提示','您没有此权限!');
			} 
		}
		}),
		reader: new Ext.data.JsonReader({
			totalProperty : 'json.count',
			root:'json.data'
		}, [ 'CUST_ID','CUST_ZH_NAME','CERT_TYPE','CERT_NUM','CUST_LEV','CUST_TYP','CUST_TYP_ORA','MGR_ID','MGR_NAME','INSTITUTION','INSTITUTION_NAME'])
	});
	    customerInfoStore.on('beforeload', function() {
	    	if(''!=queryStr){
	    	__Str = Ext.encode(queryStr);	
	    	}else{
	    	__Str='';	
	    	}
		this.baseParams = {
				"condition":__Str,
				custType:oCustInfo.groupMemberType,
				querySign:'queryCustomer',
				groupId: oCustInfo.groupId
		  };
	    });
	
	// 每页显示条数下拉选择框
	var customerInfopagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
								fields : ['value', 'text'],
								data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
								         [ 100, '100条/页' ], [ 250, '250条/页' ],
								         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});
	var customerInfonumber = parseInt(customerInfopagesize_combo.getValue());
	customerInfopagesize_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
		customerInfoBar.pageSize = parseInt(customerInfopagesize_combo.getValue());
		customerInfoStore.reload({
			params : {
			start : 0,
			limit : parseInt(customerInfopagesize_combo.getValue())
		}
		});
	});
	
	var customerInfoBar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : customerInfonumber,
		store : customerInfoStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', customerInfopagesize_combo]
	});
	//end

	var groupLeaguerGrid = new Ext.grid.GridPanel({
		height: document.body.scrollHeight-30,
		title : '客户群成员列表',
		frame : true,
		autoScroll : true,
		store : groupLeaguerStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : groupLeaguerCm, // 列模型
		sm : groupLeaguerSm, // 复选框
		bbar : groupLeaguerBar,
		tbar:[{
					text : '客户视图',
					iconCls :'custGroupMemIconCss',
					handler : function() {
			        var checkedNodes = groupLeaguerGrid.getSelectionModel().selections.items;
						if(checkedNodes.length==0)
							{
								Ext.Msg.alert('提示', '未选择任何客户');
								return ;
							}
						else if(checkedNodes.length>1)
						{
							Ext.Msg.alert('提示', '您只能选中一个客户进行查看');
							return ;
						}
						var custViewWindow = new Com.yucheng.crm.cust.ViewWindow({
							id:'custViewWindow',
							custId:checkedNodes[0].data.CUST_ID,
							custName:checkedNodes[0].data.CUST_ZH_NAME,
							custTyp:checkedNodes[0].data.CUST_TYP
						});
						
						Ext.Ajax.request({
							url : basepath + '/commsearch!isMainType.json',
							method : 'GET',
							params : {
							'mgrId' : __userId,
							'custId' : checkedNodes[0].data.CUST_ID
						},
						success : function(response) {
							var anaExeArray = Ext.util.JSON.decode(response.responseText); 
						if(anaExeArray.json != null){
							if(anaExeArray.json.MAIN_TYPE=='1'){
								oCustInfo.omain_type=true;
							}else{
								oCustInfo.omain_type=false;
							}}
						else {
							oCustInfo.omain_type=false;
						}
							oCustInfo.cust_id = checkedNodes[0].data.CUST_ID;
							oCustInfo.cust_name = checkedNodes[0].data.CUST_ZH_NAME;
							oCustInfo.cust_type = checkedNodes[0].data.CUST_TYP;
							oCustInfo.view_source = 'viewport_center';
							custViewWindow.show();
						
						},
						failure : function(form, action) {}
						});
					
			
					}
				},{'text':'移除客户群',iconCls:'deleteIconCss',hidden:__hiddeAble,handler:function(){
			  batchDelete();
		  }}				 
		  ],
        viewConfig : {
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var customerInfoGrid = new Ext.grid.GridPanel({
		region : 'center',
        layout : 'fit',
		frame : true,
		autoScroll : true,
		store : customerInfoStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : customerInfoCm, // 列模型
		sm : customerInfoSm, // 复选框
		bbar : customerInfoBar,
        tbar:customerInfoTar,
        viewConfig : {
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	if(oCustInfo.custFrom!='2'){
		groupLeaguerStore.load({
			params : {
			start : 0,
			limit : parseInt(groupLeaguerpagesize_combo.getValue())
		}
		});
	}else{
		custJustStore.load({
			params : {
				start : 0,
				limit : parseInt(custJustpagesize_combo.getValue())
			}
	});
	}
	

		
        var replacePanel = new Ext.Panel( {
            autoScroll : true,
            height : document.body.scrollHeight-30, 
            buttonAlign : "center",
            layout:'border',
            items : [  searchPanel1 ,customerInfoGrid]
			           
        });
		
        	var viewBlocBaseInfo = new Ext.Panel({
        		autoScroll:true,
        		height:document.body.scrollHeight-30,
        		layout : 'fit',
        		items : [{
        			 layout : 'column',
        			 border : false,
        			 items : [{
        	        	 columnWidth :__modelSign1,
        	        	 layout : 'form',
        	        	 hidden:__hiddeAble,
        	        	 border : false,
        	        	 items : [{
        	        		 layout : 'fit',
        		        	 border : false,
        		        	 items : [replacePanel]
        	        	 }]
        			 },{
        	        	 columnWidth :__modelSign2,
        	        	 layout : 'form',
        	        	 border : false,
        	        	 items : [{
        		             region : 'center',
        		             layout : 'fit',
        		             items : [ groupLeaguerGrid ]
        	        	 }]
        			 }]
        		}]
        	});
		
		 if(oCustInfo.custFrom=='2'){
			 viewBlocBaseInfo = agileQueryPanel1; 
	        }
			 
		 var Panel = new Ext.Panel( {
             renderTo:'group_viewport_center',//仅客户群视图使用
             autoScroll:true,
     		height:document.body.scrollHeight-30,
     		layout : 'fit',
               items : [ viewBlocBaseInfo ]
           });
		 
		
		
});