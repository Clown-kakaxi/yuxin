Ext.onReady(function(){
	 var groupCustMgrStr='';
	  var rowNo=-1;
	  var deleteId = '';
	  // 下拉框(分配机构弹窗)
	  var combo = new Ext.form.ComboBox({
		    typeAhead: true,
		    triggerAction: 'all',
		    emptyText:'请选择',
		    forceSelection : true,
		    mode: 'local',
		    store: new Ext.data.ArrayStore({
		        id: 0,
		        fields: [
		            'mainType',
		            'displayText'
		        ],
		        data: [[1, '主办'], [2, '协办']]
		    }),
		    valueField: 'mainType',
		    displayField: 'displayText',
		    hiddenName : 'mainType',
		    allowBlank : false,
		    listeners: {
		    	select: function (a, b) {
		  			var v = a.value;	//取到valueField中的值
		  			combo.setValue(v);
	  			}
	  		}
	  });
	  Ext.util.Format.comboRenderer = function(combo){
		    return function(value){
		        var record = combo.findRecord(combo.valueField, value);
		        return record ? record.get(combo.displayField) : combo.valueNotFoundText;
		    };
		};
		combo.addListener('select',function(){
				groupCustMgrGrid.stopEditing();
			});
								
	  var batchAdd= function(){
		  var checkedNodes = groupCustMgrGrid.getSelectionModel().selections.items;
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
			  groupCustMgrStore.reload({
				  params : {
				  start : 0,
				  limit : parseInt(groupCustMgrpagesize_combo.getValue())
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
	  
	  //添加客户群与客户经理关系信息
	  var addGroupCustMgr = function(addGrid){
		var selectRe = addGrid.getSelectionModel().getSelections()[0];
		for(var i=0;i<groupCustMgrStore.getCount();i++){//对要添加的管理团队成员做验证，不再重复添加数据
			if(selectRe.data.userId==groupCustMgrStore.data.items[i].data.custManagerId){
				Ext.Msg.alert('系统提示','已经存在,不能添加重复数据!');
				return false;
			}
		}
		var data={
			json: { 
				data : [{
					USER_ID:selectRe.data.userId,
					   CUST_MANAGER_NAME:selectRe.data.userName,
					   CUST_MANAGER_ID:selectRe.data.userId,
					   WORK_UNIT:selectRe.data.unitName,
					   MOBILEPHONE:selectRe.data.mobilephone,
					   INSTITUTION:selectRe.data.orgId,
					   MAIN_TYPE:2
				}]
			}
		};
		groupCustMgrStore.loadData(data,true);
	};
	//新增客户群成员的表格面板 
	var groupCustMgrTar = new Ext.Toolbar({
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
				url :basepath+'/lookup.json?name=CUST_MANAGER_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});	
//var searchGroupCustMgrPanel = new Ext.form.FormPanel({
//            labelWidth : 80,
//            hight:'80',
//            labelAlign : 'right',
//            frame : true,
//            region : 'north',
//            autoScroll : true,
//            layout : 'column',
//            items : [ {
//                columnWidth : .33,
//                layout : 'form',
//                items : [{
//                    xtype : 'textfield',
//                    fieldLabel : '客户经理名称',
//                    name : 'CUST_MANAGER_NAME',
//                    anchor : '99%'
//                }]
//            }, {
//                columnWidth : .33,
//                layout : 'form',
//                items : [{
//                    xtype : 'textfield',
//                    fieldLabel : '客户经理编号',
//                    name : 'CUST_MANAGER_ID',
//                    anchor : '99%'
//                } ]
//            }, {
//                columnWidth : .33,
//                layout : 'form',
//                items : [new Ext.form.ComboBox({
//							hiddenName : 'CUST_MANAGER_TYPE',
//							fieldLabel : '客户经理类型',
//							labelStyle: 'text-align:right;',
//							triggerAction : 'all',
//							store : customerTypeStore,
//							displayField : 'value',
//							valueField : 'key',
//							mode : 'local',
//							forceSelection : true,
//							typeAhead : true,
//							emptyText:'请选择',
//							resizable : true,
//							anchor : '99%'
//						})]
//            }],
//            buttonAlign : 'center',
//            buttons : [ {
//                text : '查询',
//                handler : function() {
//                if (!searchGroupCustMgrPanel.getForm().isValid()) {
//                    Ext.Msg.alert("提醒", "请填写必填项");
//                    return false;
//                }
//                groupCustMgrStr = searchGroupCustMgrPanel.getForm().getFieldValues();
//	            groupCustMgrStore.load({
//	                params : {
//	                    start : 0,
//	                    limit : parseInt(groupCustMgrpagesize_combo.getValue())
//	                }
//	            });
//            }
//            }, {
//                text : '重置',
//                handler : function() {
//                    searchGroupCustMgrPanel.getForm().reset();
//                }
//            } ]
//        });
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

	//窗口右侧客户经理列表
	var groupCustMgrSm = new Ext.grid.CheckboxSelectionModel();
	var groupCustMgrrownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	//窗口右侧模型
	var groupCustMgrCm = new Ext.grid.ColumnModel([groupCustMgrrownum,/*groupCustMgrSm,*/
       			 {dataIndex : 'mainType',header : '主协办类型',
				  sortable : true,
				  width :80,
				  editor: combo,
				  renderer : function(value, p, r) {
								if (value == "1")
									return "主办";
								else 
									return "协办";
							}
					}
				 ,{header : '编号', dataIndex : 'userId',sortable : true,width : 120,hidden:true } 
                 ,{header : '客户经理名称',dataIndex : 'custManagerName',sortable : true,width : 120}
                 ,{header : '客户经理编号',dataIndex : 'custManagerId',sortable : true,width : 120} 
                 ,{header : '联系电话',dataIndex : 'mobilephone',sortable : true,width : 120}
                 ,{header : '归属机构ID',dataIndex : 'institution',sortable : true,width : 120,hidden:true,hideable:false} 
                 ,{header : '归属机构',dataIndex : 'workUnit',sortable : true,width : 120} 
                 
                 ]);

	/**
	 * 数据存储
	 */
	var groupCustMgrStore = new Ext.data.Store({//数据存储
		  	restful:true,
		  	proxy : new Ext.data.HttpProxy({url:basepath+'/custmgrgroupinfo.json?querySign='+'queryTeamMgr'
		  	}),
		  	  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  },  [{name: 'userId', mapping: 'USER_ID'}
		      ,{name: 'custManagerName', mapping: 'CUST_MANAGER_NAME'}
		      ,{name: 'custManagerId', mapping: 'CUST_MANAGER_ID'}
		      ,{name: 'custManagerType',mapping:'CUST_MANAGER_TYPE'}
		      ,{name: 'custManagerLevel', mapping: 'CUST_MANAGER_LEVEL'}
		      ,{name: 'workUnit', mapping: 'WORK_UNIT'}
		      ,{name: 'institution', mapping: 'INSTITUTION'}
		      ,{name: 'mobilephone', mapping: 'MOBILEPHONE'}
		      ,{name: 'mainType',mapping:'MAIN_TYPE'}
		      ,{name: 'CUST_MANAGER_TYPE_ORA'}
		      ,{name: 'CUST_MANAGER_LEVEL_ORA'}
		      ])
	 		  });
	 		  
	    groupCustMgrStore.on('beforeload', function() {
	    		if(''!=groupCustMgrStr){
	    	__Str = Ext.encode(groupCustMgrStr);	
	    	}else{
	    	__Str='';	
	    	}
		this.baseParams = {
				"condition":__Str,
				querySign:'queryCustomer',
				groupId: oCustInfo.groupId
		  };
	    });
	
	// 每页显示条数下拉选择框
	var groupCustMgrpagesize_combo = new Ext.form.ComboBox({
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
	var groupCustMgrnumber = parseInt(groupCustMgrpagesize_combo.getValue());
	groupCustMgrpagesize_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
		groupCustMgrBar.pageSize = parseInt(groupCustMgrpagesize_combo.getValue());
		groupCustMgrStore.reload({
			params : {
			start : 0,
			limit : parseInt(groupCustMgrpagesize_combo.getValue())
		}
		});
	});
	
	var groupCustMgrBar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : groupCustMgrnumber,
		store : groupCustMgrStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', groupCustMgrpagesize_combo]
	});

	//****************************************************************************************************************************************************
	// 定义列模型
	var custMgrTeamMemberCm = new Ext.grid.ColumnModel([groupCustMgrrownum,/*groupCustMgrSm,*/
       			 {header : '客户经理编号', dataIndex : 'userId',sortable : true,width : 100} 
                 ,{header : '客户经理名称',dataIndex : 'userName',sortable : true,width : 100} 
                 ,{header : '联系电话',dataIndex : 'mobilephone',sortable : true,width : 100}
                 ,{header : '所在机构',dataIndex : 'unitName',sortable : true,width : 120}
                 ,{header : '所在机构ID',dataIndex : 'orgId',sortable : true,width : 120,hidden:true}
                 ]);
                 
	 		  /**
	 * 营销团队成员store
	 */
	var custMgrTeamMemberStore = new Ext.data.Store({//数据存储
		  	restful:true,
		  	proxy : new Ext.data.HttpProxy({url:basepath+'/marketTeamMemberInfoQuery.json'
		  	}),
		  	  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  	 },[{name: 'userId', mapping: 'USER_ID'}
		      ,{name: 'userName', mapping: 'USERNAME'}
		      ,{name: 'unitName', mapping: 'UNITNAME'}
		      ,{name: 'mobilephone', mapping: 'TELEPHONE'}
		      ,{name: 'orgId', mapping: 'ORG_ID'}
		      ])
	 		  });
	  
    
       var mktTeamStore = new Ext.data.Store({//渠道类型的store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/chaneltypeinfo.json?tableName='+'OCRM_F_CM_MKT_TEAM'
		}),
		reader : new Ext.data.JsonReader({
			root : 'json.data'
		}, [ 'MKT_TEAM_ID', 'MKT_TEAM_NAME' ])
	});   
	
	//左侧展示的营销团队成员列表
	var custMgrTeamMemberGrid = new Ext.grid.GridPanel({
		height :310,
		width : 200,
		autoScroll : true,
		loadMask:true,
		store : custMgrTeamMemberStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : custMgrTeamMemberCm, // 列模型
		tbar : [new Ext.form.Label({
			text:'客户经理团队:'
			}),	{	
                name: 'MARKET_TEAM',
                id : 'MARKET_TEAM',
                hiddenName:'MARKET_TEAM_ID',
                forceSelection : true,
				resizable:true,
                xtype:'combo',
//              	  width:170,
                labelStyle: 'text-align:right;',
                triggerAction:'all',
                mode:'local',
                store:mktTeamStore,
                valueField:'MKT_TEAM_ID',
                displayField:'MKT_TEAM_NAME',
                emptyText:'请选择',
                anchor : '85%'
            	},new Ext.form.Label({
        			text:'',
        			width:50
    			}),'-',{
//			    	width:80,
					text : ' 添加-> ',
					iconCls:'resetIconCss',
					handler : function() {
					 	var selectLength = custMgrTeamMemberGrid.getSelectionModel().getSelections().length;
						if (selectLength != 1) {
		                    Ext.Msg.alert("提示", "请选择一条记录!");
		                    return false;
						}
						addGroupCustMgr(custMgrTeamMemberGrid);
	            	}
            	}],
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	var custMgrTeamMemberGridPanel = new Ext.Panel({
    	height: document.body.scrollHeight-30,
		title : '客户经理团队-搜索', 
		layout:'fit',
		frame : true,
		items:[custMgrTeamMemberGrid]
    });
	Ext.getCmp('MARKET_TEAM').addListener('select',function(){
	custMgrTeamMemberStore.on('beforeload',function(){
	this.baseParams = {
				marketTeamId:Ext.getCmp('MARKET_TEAM').getValue()
		  };
	});
	custMgrTeamMemberStore.load();
	});
	
	
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
	
	var mtmsm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var mtmrownum = new Ext.grid.RowNumberer({
				header : 'NO',
				width : 28
			});
			
	// 定义列模型
	var mtmcm = new Ext.grid.ColumnModel([mtmrownum,mtmsm,
	        {header : 'id',dataIndex : 'id',sortable : true,width : 100,hidden :true},
	        {header : '客户经理编号',dataIndex : 'userId',sortable : true,width : 80},
	        {header : '客户经理名称',dataIndex : 'userName',sortable : true,width : 100},
	        {header : '联系电话',dataIndex : 'mobilephone',sortable : true,width : 100},
	        {header : '角色名称',dataIndex : 'role',sortable : true,width : 200,hidden:true},
	        {header : '归属机构ID',dataIndex : 'orgId',sortable : true,width : 100,hidden:true,hideable:false},
	        {header : '所在机构',dataIndex : 'unitName',sortable : true,width : 100}
			]);
	var SstoreRecord = Ext.data.Record.create([
  				           {name: 'id', mapping: 'ID'},
                           {name: 'userId', mapping: 'ACCOUNT_NAME'},
                           {name: 'userName', mapping: 'USER_NAME'},
                           {name: 'mobilephone', mapping: 'MOBILEPHONE'},
                           {name: 'role', mapping : 'ROLE_NAME'}, 
                           {name: 'orgId', mapping : 'ORG_ID'}, 
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
			}),
			reader : new Ext.data.JsonReader({
				successProperty: 'success',
		        idProperty: 'ID',
		        messageProperty: 'message',
				root : 'json.data',
				totalProperty: 'json.count'
			},SstoreRecord)
		});
		
	var yuangongSearch = new Ext.form.FormPanel({
				labelWidth : 105,
//				title : "查询条件",
				labelAlign : 'right',
//				height : 100,
				frame : true,
				region : 'north',
				autoScroll : true,
				items : [ {
					layout : 'column',
					items : [{
								columnWidth : .33,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '客户经理编号',
									id : 'USER_ID',
									name : 'USER_ID',
									anchor : '100%'
								} ]
							},{
								columnWidth : .33,
								layout : 'form',
								items : [ {
									xtype : 'textfield',
									fieldLabel : '客户经理姓名',
									id : 'USER_NAME',
									name : 'USER_NAME',
									anchor : '100%'
								} ]
							}, {
							columnWidth : .33,
							layout : 'form',
							labelWidth : 90,
							border : false,
							items : [new Com.yucheng.bcrm.common.OrgField({
								searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
								fieldLabel : '所属机构',
								labelStyle : 'text-align:right;',
								id : '__CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
								name : 'CUST_ORG', 
								hiddenName: 'ORG_ID',   //后台获取的参数名称
								anchor : '100%',
								checkBox:true //复选标志
							})]
			}]
				} ],
				buttonAlign : 'center',
				buttons : [ {
					text : '查询',
					handler : function() {
						var conditionStr = yuangongSearch.getForm().getFieldValues(false);
						Sstore.baseParams = {
								"condition" : Ext.encode(conditionStr),
								'org_id':__units,
								'role_id':''
							};
						Sstore.load({
							params : {
								start : 0,
								limit : parseInt(spagesize_combo.getValue())
							}
						});
						addMarketTeamMemberWindow.hide();

					}
				},{
					text : '重置',
					handler : function() {
						Ext.getCmp('__CUST_ORG').setValue('');
						yuangongSearch.getForm().reset();
					}
				} ]

			});
			
			// 定义展示员工基本信息窗口
			var addMarketTeamMemberWindow = new Ext.Window({
				title : '客户群维护团队成员查询',
				layout : 'fit',
				width : 650,
				height : 120,
				closable : true,
				closeAction : 'hide',
				modal : true, // 模态窗口
				loadMask : true,
				titleCollapse : true,
				buttonAlign : 'right',
				border : false,
				items: [yuangongSearch]
			});
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
		bbar:sbbar,
		tbar : [{
			text : '客户经理筛选',
			iconCls:'closeIconCss',
			xtype : 'button',
			name : 'USER_NAME',
			width : 50,
			handler : function(){
				addMarketTeamMemberWindow.show();
			}
				},'-',new Ext.form.Label({
        			text:'',
        			width:150
    			}),'-',{
				text : '添加->',
				align:'right',
				iconCls:'resetIconCss',
				handler : function() {
			 	var selectLength = MarketTeamMemberGrid.getSelectionModel().getSelections().length;
				 if (selectLength != 1) 
				 	{
                    Ext.Msg.alert("提示", "请选择一条记录!");
                    return false;
                    }
				 addGroupCustMgr(MarketTeamMemberGrid);
		    		}
				}],
				viewConfig:{
					   forceFit:false,
					   autoScroll:true
					},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
			var MarketTeamMemberGridPanel = new Ext.Panel({
				layout:'fit',	
				height: 310,
				frame : true,
				title:'客户经理-搜索',
				items:[MarketTeamMemberGrid],
				listeners:{
					'expand':function(){
					MarketTeamMemberGrid.doLayout();
					}
				}
			});
	
	
	/*********
	   * 折叠布局
	   * 定义多个子面板，在任何状态下，只会展示一个子面板，
	   * 其他面板保持闭合状态
	   * 
	   * ***/
	var singleMgr = new Ext.Panel({ 
		layout : 'accordion', 
		layoutConfig :{ 
//			activeOnTop : true,             		//设置打开的子面板置顶 
		    fill : true,                     		//子面板充满父面板的剩余空间 
		    hideCollapseTool: false,         		//显示“展开收缩”按钮 
		    titleCollapse : true,     				//允许通过点击子面板的标题来展开或收缩面板 
		    animate:true          					//使用动画效果 
		}, 
		title:'待加入成员搜索', 	//面板表一
		frame:true,                				//渲染面板 
		height : document.body.scrollHeight-30, 							//自定义高度
		items: [ custMgrTeamMemberGridPanel, 
		    MarketTeamMemberGridPanel
	   ] 
  }); 
	
	//满足条件的客户经理列表
	var groupCustMgrGrid = new Ext.grid.EditorGridPanel({
		layout : 'fit',
        border : false,
		height: document.body.scrollHeight-30,
		frame : true,
		autoScroll : true,
		loadMask:true,
		store : groupCustMgrStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : groupCustMgrCm, // 列模型
		bbar : groupCustMgrBar,
        tbar:groupCustMgrTar,
        viewConfig : {
		},
		tbar : [{
				text:'删除记录',
				iconCls:'deleteIconCss',
				handler:function(){
				if(rowNo<0){
					Ext.Msg.alert('提示','请先选择一条记录');
				}else{
						if(groupCustMgrStore.getAt(rowNo).data.userId!=''&&groupCustMgrStore.getAt(rowNo).data.userId!=null){
							deleteId = deleteId+groupCustMgrStore.getAt(rowNo).data.userId+',';//记录要删除的记录ID，点击保存按钮的时候提交后台
						}
						groupCustMgrStore.removeAt(rowNo);
						rowNo = -1;
						Ext.Msg.alert('提示','删除后请保存记录！');
				}
			}
			},{
			text:'保存记录',
			iconCls:'addIconCss',
			handler:function(){
			 var json0 = {'CUSTMANAGER_ID':[]};
			 var json1 = {'MAIN_TYPE':[]};
			 var json2 = {'ORG_ID':[]};
			 var countNum=0;
			for(var i=0;i<groupCustMgrStore.getCount();i++){
                    var temp=groupCustMgrStore.getAt(i);
                    if(temp.data.custManagerId!=''){
                        json0.CUSTMANAGER_ID.push(temp.data.custManagerId);
                        json1.MAIN_TYPE.push(temp.data.mainType);
                      if(temp.data.mainType=='1'){
                       countNum++;
                      } 
                        json2.ORG_ID.push(temp.data.institution);
                    }
                }
             if(countNum>1){
                	Ext.Msg.alert('系统提示','主办客户经理至多有一个，请重新调整!当前主办个数：'+countNum);
                	return false;
              }
			Ext.Ajax.request({
                    url : basepath + '/groupteammgredit!saveData.json',
                    method : 'POST',
                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                    params:{
                        'CUSTMANAGER_ID':Ext.encode(json0),
                        'MAIN_TYPE':Ext.encode(json1),
                        'ORG_ID':Ext.encode(json2),
                        'groupBaseId':oCustInfo.groupId
                    },
                    success : function() {
                        Ext.Msg.alert('提示', '操作成功');
                    },
                    failure : function(response) {
                        Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
                    }
                });
			}
			}],
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
		groupCustMgrGrid.on('cellclick',function(grid,row,col){//获取编辑的行数，从0开始，
		rowNo=row;
	});
	
	    groupCustMgrStore.load({
		params : {
		start : 0,
		limit : parseInt(groupCustMgrpagesize_combo.getValue())
		}
		});
	var viewBlocBaseInfo = new Ext.Panel({
		renderTo:'group_viewport_center',
		autoScroll:true,
		height:document.body.scrollHeight-30,
		layout : 'fit',
		items : [{
        	layout : 'column',
			border : false,
			items : [{
				 columnWidth : __modelSign1,
	        	 layout : 'form',
	        	 hidden:__hiddeAble,
	        	 border : false,
	        	 items : [{
		        	 layout : 'fit',
		        	 border : false,
		        	 items : [singleMgr]
	        	 }]
			},{
				 columnWidth : __modelSign2,
	        	 layout : 'form',
	        	 border : false,
	        	 items : [groupCustMgrGrid]
			}]
		}]
	});
});