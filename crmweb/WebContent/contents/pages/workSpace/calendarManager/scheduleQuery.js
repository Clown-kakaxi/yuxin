/**
 * @constructor 日程查询
 * @author songxs
 * @since 2012-12-3
 */

Ext.onReady(function(){
	Ext.QuickTips.init(); 

	var remindTypeStore = new Ext.data.ArrayStore({
	    fields:['key','value'],
	    data:[['1000000001','客户拜访'],['1000000002','代办事项'],
	          ['1000000003','纪念日'],['1000000004','会议'],
	          ['1000000005','约会'],['1000000006','备忘']]
	});
	
	
	var scheduleQueryPanel = new  Ext.form.FormPanel({//日程查询条件PANEL
		
		title : '日程查询',
		height : 120,
		buttonAlign : 'center',
		labelWidth : 100,//label的宽度
		labelAlign : 'right',
		frame : true,
		autoScroll : true,
		region : 'north',
		split : true,
		items : [{
			layout:'column',
			items : [{
				 columnWidth:.25,
				 layout:'form',
				 items:[
				        new Com.yucheng.bcrm.common.CustomerQueryField({ 
				        	fieldLabel : '客户', 
				        	name : 'newcustname',
				        	singleSelected:true,//单选复选标志
				        	editable : false,
				        	allowBlank:false,//不允许为空
				        	blankText:"不能为空，请填写",
				        	anchor : '90%',
				        	hiddenName:'RELATION_CUST',
				  	      	labelStyle: 'text-align:right;'

				        })
					 ]

			},{
				 columnWidth:.25,
				 layout:'form',
				 items:[
				        new Com.yucheng.crm.common.OrgUserManage({ 
				        	xtype:'userchoose',
				        	fieldLabel : '用户', 
				  	      	labelStyle: 'text-align:right;',
				  	      	name : 'USER_NAME',
				  	      	hiddenName:'CREATOR',
				  	      	singleSelect:false,
				  	      	anchor : '90%'
				        })
				        ]
			},{
				columnWidth:.25,
				layout:'form',
				items:[{
					id:'dateFrom',
					name:'START_DATE_FROM',
					xtype:'datefield',
					fieldLabel : '日期从',
					format:'Y-m-d',
		  	      	labelStyle: 'text-align:right;',
					editable:false,
					anchor:'90%'
				}]
			},{
				columnWidth:.25,
				layout:'form',
				items:[{
					id:'toDate',
					name:'END_DATES_TO',
					xtype:'datefield',
					fieldLabel:'日期到',
					format:'Y-m-d',
		  	      	labelStyle: 'text-align:right;',
					editable:false,
					anchor:'90%'
				}]
			}]
		}],
		buttons:[{
			text:'查询',
			handler:function(){
			var start = Ext.getCmp('dateFrom').getValue();
			var end = Ext.getCmp('toDate').getValue();
			if(start==''&&end !=''){
				Ext.Msg.alert('消息框','请先选择开始时间！');
				Ext.getCmp('dateFrom').reset();
				return;
			}else if(end !=''&&start>end){
				Ext.Msg.alert('消息框','开始时间大于结束时间，请检查！');
				Ext.getCmp('toDate').reset();
				return;
			}
			var parameters = scheduleQueryPanel.getForm().getValues(false);
			scheduleQueryStore.removeAll();
			scheduleQueryStore.baseParams = {
					'condition':Ext.util.JSON.encode(parameters)
			};
			scheduleQueryStore.load({
				params:{
						start:0,
						limit: parseInt(spagesize_combo.getValue())
			}
			});
		}
		},{
			text : '重置',
			handler:function(){
			scheduleQueryPanel.getForm().reset();
		}
		}]		
	});
	
	var sm = new Ext.grid.CheckboxSelectionModel(); //复选框
	
	
	var rownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
		header : 'No.',
		width : 28
	});
	
	var scheduleQueryColumns = new Ext.grid.ColumnModel([rownum,//日程查询展示列表
	                                                     {header :'日程ID',dataIndex:'scheduleId',sortable : true,hidden:true},
	                                                     {header :'状态',dataIndex:'state',id:"state",sortable : true,width:120},
	                                                     {header :'日程标题',dataIndex:'scheduleTitle',sortable : true,width:120},
	                                                     {header :'日程类型',dataIndex:'remindBelong',sortable : true,width:120,
	                                                      renderer : function(value,metadata,record){
	                                                    	 if(value==undefined)
	                                                    		 return "";
	                                                    	 else{
	                                                    		 var index = remindTypeStore.find('key',value);   
	                                                    		 if(index!=-1){   
	                                                    			 return remindTypeStore.getAt(index).data.value;  
	                                                    		 } 
	                                                    		 return record.get('remindBelong');
	                                                    	 } 
	                                                     }
	                                                     },
	                                                     {header :'开始时间',dataIndex:'starDT',sortbable : true,width:150},
	                                                     {header :'结束时间',dataIndex:'endDT',sortable : true,width:150},
	                                                     {header :'用户',dataIndex:'creator',sortable : true,width:120,hidden:true},
	                                                     {header :'用户',dataIndex:'creatorName',sortable : true,width:120},
	                                                     {header :'客户',dataIndex:'relationCust',sortable : true,width:120,hidden:true},
	                                                     {header :'客户',dataIndex:'relationCustName',sortable : true,width:120},
	                                                     {header :'是否团队日程',dataIndex:'isTeam',sortable : true,width:120},
	                                                     {header :'团队ID',dataIndex:'mktTeamId',sortable : true,width:120,hidden:true}
	                                                     ]);
		
	var scheduleQueryRecord = new Ext.data.Record.create([	
	                                                      {name:'scheduleId',mapping:'SCHEDULE_ID'},
	                                                      {name:'state',mapping:'STATE'},
	                                                      {name:'scheduleTitle',mapping:'SCHEDULE_TITLE'},
	                                                      {name:'remindBelong',mapping:'REMIND_BELONG'},
	                                                      {name:'starDT',mapping:'STAR_DT'},
	                                                      {name:'endDT',mapping:'END_DT'},
	                                                      {name:'creator',mapping:'CREATOR'},
	                                                      {name:'creatorName',mapping:'CREATOR_NAME'},
	                                                      {name:'relationCust',mapping:'RELATION_CUST'},
	                                                      {name:'relationCustName',mapping:'RELATION_CUST_NAME'},
	                                                      {name:'isTeam',mapping:'IS_TEAM'},
	                                                      {name:'mktTeamId',mapping:'MKT_TEAM_ID'}
	                                                      ]);	 
	
	var scheduleQueryReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
	},scheduleQueryRecord);
	
	var scheduleQueryStore = new Ext.data.Store({
		
				proxy:new Ext.data.HttpProxy({
				url:basepath+'/scheduleQuery-action.json',
				method:'GET'
				}),
				reader:scheduleQueryReader
			});
		

	var spagesize_combo = new Ext.form.ComboBox({// 每页显示条数下拉选择框
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
			         [ 100, '100条/页' ], [ 250, '250条/页' ],
			         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85
	});
	
	spagesize_combo.on("select", function(comboBox) {	// 改变每页显示条数reload数据
		sbbar.pageSize = parseInt(spagesize_combo.getValue()),
		scheduleQueryStore.reload({
			params : {
				start : 0,
				limit : parseInt(spagesize_combo.getValue())
			}
		});
	});	
	
	scheduleQueryStore.load({params:{		
		start:0,
		limit: parseInt(spagesize_combo.getValue())
	}});
	
	
	
	var sbbar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : parseInt(spagesize_combo.getValue()),
		store : scheduleQueryStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});
	
	var scheduleQueryGrid =  new Ext.grid.GridPanel({//日程查询列表数据grid
	
		id:'日程列表',
		frame:true,
		id:'scheduleQueryGrid',
		store:scheduleQueryStore,
		region:'center',
		height:document.body.scrollHeight-150,
		loadMask:true,
		cm :scheduleQueryColumns,
    	bbar:sbbar,
        loadMask : {
            msg : '正在加载表格数据,请稍等...'
        }
	});
	
	var view = new Ext.Viewport({//页面展示
		layout : 'fit',
		frame : true,
		items : [{
		layout:'border',
		items:[scheduleQueryPanel,scheduleQueryGrid]
		}]
	});	
	
});