Ext.onReady(function() {
	
		var rownums = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});
		//周工作记录
		var grid = new Ext.grid.ColumnModel( [ rownums, {
			header : 'ID',
			dataIndex : 'workId',
			hidden : true,
			sortable : true
		}, {
			header : '周期',
			dataIndex : 'workLogTerm',
			width : 130,
			sortable : true
		}, {
			header : '工作目标',
			dataIndex : 'weekLogHanded',
			width : 145,
			renderer : function(value, metaData, record, rowIndex, colIndex, store) {
				if(value != '未录入')
				{
				return "<span style = 'color:blue;text-decoration:underline;'>"+value+"</span>";
				}
				else 
				{
					return "<span style = 'text-decoration:underline;'>"+value+"</span>";
				}
			},
			cellclick:true,
			sortable : true
		}, {
			header : '工作总结',
			dataIndex : 'workPlan',
			cellclick:true,
			width : 145,
			renderer : function(value, metaData, record, rowIndex, colIndex, store) {
				if(value != '未录入')
				{
				return "<span style = 'color:blue;text-decoration:underline;'>"+value+"</span>";
				}
				else 
				{
					return "<span style = 'color:red;text-decoration:underline;'>"+value+"</span>";
				}
			},
			sortable : true
		} ]);

		var weekWorkInfoRecord = new Ext.data.Record.create([ 
            { name : 'idNewCust', mapping : 'ID_NEWCUST' }, 
			{ name : 'workLogIdNewCust', mapping : 'WORKLOG_ID_NEWCUST' }, 
			{ name : 'workTypeNewCust', mapping : 'WORK_TYPE_NEWCUST' },
			{ name : 'workPlanNewCust', mapping : 'WORK_PLAN_NEWCUST' },
			{ name : 'workOrderNewCust', mapping : 'WORK_ORDER_NEWCUST' }, 
			{ name : 'workExecuteNewCust', mapping : 'WORK_EXECUTE_NEWCUST' },
			
			{ name : 'idCredit', mapping : 'ID_CREDIT' },
			{ name : 'workLogIdCredit', mapping : 'WORKLOG_ID_CREDIT' }, 
			{ name : 'workTypeCredit', mapping : 'WORK_TYPE_CREDIT' },
			{ name : 'workPlanCredit', mapping : 'WORK_PLAN_CREDIT' },
			{ name : 'workOrderCredit', mapping : 'WORK_ORDER_CREDIT' }, 
			{ name : 'workExecuteCredit', mapping : 'WORK_EXECUTE_CREDIT' },
			
			{ name : 'idStock', mapping : 'ID_STOCK' },
			{ name : 'workLogIdStock', mapping : 'WORKLOG_ID_STOCK' }, 
			{ name : 'workTypeStock', mapping : 'WORK_TYPE_STOCK' },
			{ name : 'workPlanStock', mapping : 'WORK_PLAN_STOCK' },
			{ name : 'workOrderStock', mapping : 'WORK_ORDER_STOCK' }, 
			{ name : 'workExecuteStock', mapping : 'WORK_EXECUTE_STOCK' },
			
			{ name : 'idOther', mapping : 'ID_OTHER' },
			{ name : 'workLogIdOther', mapping : 'WORKLOG_ID_OTHER' }, 
			{ name : 'workTypeOther', mapping : 'WORK_TYPE_OTHER' },
			{ name : 'workPlanOther', mapping : 'WORK_PLAN_OTHER' },
			{ name : 'workOrderOther', mapping : 'WORK_ORDER_OTHER' }, 
			{ name : 'workExecuteOther', mapping : 'WORK_EXECUTE_OTHER' },
			
			{ name : 'weekId',mapping : 'ID'}, 
			{ name : 'workLogType', mapping : 'WORKLOG_TYPE' }, 
			{ name : 'workLogStat', mapping : 'WORKLOG_STAT' },
			{ name : 'userId', mapping : 'USER_ID' },
			{ name : 'userName', mapping : 'USER_NAME' },
			{ name : 'createDate', mapping : 'CREATE_DATE' },
			{ name : 'auditUser', mapping : 'AUDIT_USER' },
			{ name : 'auditDate', mapping : 'AUDIT_DATE' },
			{ name : 'auditComment', mapping : 'AUDIT_COMMENT' } 
			]);
		
		var workLogRecord = new Ext.data.Record.create([{
			 name : 'workId',mapping : 'WORK_ID'}, 
			{ name : 'workLogTerm', mapping : 'WORKLOG_TERM' }, //周期
			{ name : 'weekLogHanded', mapping : 'WEEKLOG_HANDED' },//工作目标
			{ name : 'workPlan', mapping : 'WORKPLAN' }//工作目标
			]);
		var workLogReader = new Ext.data.JsonReader({// 读取json数据的panel
			totalProperty : 'json.count',
			root : 'json.data'
		}, workLogRecord);
		var workLogStore = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/workLogInfos-query.json',
				failure : function(response) {
				var resultArray = Ext.util.JSON.decode(response.status);
				if (resultArray == 403) {
					Ext.Msg.alert('提示', response.responseText);
				}
			},
			method : 'GET'
			}),
			reader : workLogReader
		});
		/*******end**********/
		// 每页显示条数下拉选择框
		var spagesize_combo = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
				fields : [ 'value', 'text' ],
				data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
				         [ 50, '50条/页' ], [ 100, '100条/页' ],
				         [ 250, '250条/页' ], [ 500, '500条/页' ] ]
			}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			forceSelection : true,
			width : 85
		});

		// 改变每页显示条数reload数据
		spagesize_combo.on("select", function(comboBox){
			sbbar.pageSize = parseInt(spagesize_combo.getValue()),
			workLogStore.reload( {
				params : {
				start : 0,
				limit : parseInt(spagesize_combo.getValue())
			}
			});
		});
		// 分页工具栏
		var sbbar = new Ext.PagingToolbar({
			pageSize 	: parseInt(spagesize_combo.getValue()),
			store		: workLogStore,
			displayInfo : true,
			displayMsg	: '显示{0}条到{1}条,共{2}条',
			emptyMsg 	: "没有符合条件的记录",
			items 		: [ '-', '&nbsp;&nbsp;', spagesize_combo ]
		});

		workLogStore.load({
			params : {
				start : 0,
				limit : parseInt(spagesize_combo.getValue())
			}
		});
		/**
		 * 周工作记录录入信息panel
		 */
		var weekForm = new Ext.FormPanel({
			frame : true,
			autoScroll : true,
			region : 'center',
			reader: new Ext.data.JsonReader({
	            root:'json.data'
	            },weekWorkInfoRecord),
			items : [ {
				xtype : 'fieldset',
				title : '客户开发与维护工作计划及完成情况',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				border :true,
				items : [{
					layout : 'column',
					items : [{
						layout : 'form',
						columnWidth : .43,
						labelWidth : 100,
						items : [{
							xtype : 'textfield',
							fieldLabel : 'ID',
							id : 'weekId',
							name : 'weekId',
							labelStyle : 'text-align:right;',
							hidden : true,
							anchor : '90%'
						},{
							xtype : 'textfield',
							fieldLabel : '周期',
							id : 'showWeek',
							readOnly : true,
							labelStyle : 'text-align:right;',
							anchor : '90%'
						},{
							xtype : 'textfield',
							fieldLabel : '隐藏周期',
							id : 'workLogDate',
							readOnly : true,
							name : 'workLogDate',
							labelStyle : 'text-align:right;',
							hidden : true,
							anchor : '90%'
						},{
							xtype : 'textfield',
							fieldLabel : '填写日期',
							readOnly : true,
							name : 'createDate',
							hidden : true,
							id : 'createDate',
							labelStyle : 'text-align:right;',
							anchor : '90%'
						},{
							xtype : 'datefield',
							fieldLabel : '填写日期',
							editable : false,
							name : 'createDateNew',
							id : 'createDateNew',
							labelStyle : 'text-align:right;',
							value : new Date(),
							format : 'Y-m-d',
							anchor : '90%'
						} ]
					}, {
						layout : 'form',
						columnWidth : .43,
						labelWidth : 100,
						items : [ {
							xtype : 'textfield',
							fieldLabel : '客户经理编号',
							name : 'userId',
							value : __userId,
							labelStyle : 'text-align:right;',
							readOnly : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '客户经理姓名',
							name : 'userName',
							value : __userName,
							labelStyle : 'text-align:right;',
							readOnly : true,
							anchor : '95%'
						} ]
					} ]
				} ]
			},{
				xtype : 'fieldset',
				title : '一、新客户拜访工作安排',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				border :true,
				buttonAlign :'center',
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',
						columnWidth : .99,
						labelWidth : 100,
						items : [ {
							xtype:'textfield',
							name:'idNewCust',
							hidden : true,
							id:'idNewCust'
						},{
							xtype:'textarea',
							name:'workPlanNewCust',
							id:'workPlanNewCust',
							fieldLabel : '计划工作内容',
							labelStyle:'text-align:right;',
							anchor:'95%'
						},{
							xtype:'textarea',
							name:'workExecuteNewCust',
							id:'workExecuteNewCust',
							fieldLabel : '实际完成情况',
							labelStyle:'text-align:right;',
							anchor:'95%'
						} ]
					} ]
				} ]
			},{
				xtype : 'fieldset',
				title : '二、存量客户维护工作安排',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				border :true,
				buttonAlign :'center',
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',
						columnWidth : .99,
						labelWidth : 100,
						items : [ {
							xtype:'textfield',
							name:'idCredit',
							hidden : true,
							id:'idCredit'
						},{
							xtype:'textarea',
							name:'workPlanCredit',
							id:'workPlanCredit',
							fieldLabel : '计划工作内容',
							labelStyle:'text-align:right;',
							anchor:'95%'
						},{
							xtype:'textarea',
							name:'workExecuteCredit',
							id:'workExecuteCredit',
							fieldLabel : '实际完成情况',
							labelStyle:'text-align:right;',
							anchor:'95%'
						} ]
					} ]
				} ]
			},{
				xtype : 'fieldset',
				title : '三、贷后检查工作安排',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				border :true,
				buttonAlign :'center',
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',
						columnWidth : .99,
						labelWidth : 100,
						items : [ {
							xtype:'textfield',
							name:'idStock',
							hidden : true,
							id:'idStock'
						},{
							xtype:'textarea',
							name:'workPlanStock',
							id:'workPlanStock',
							fieldLabel : '计划工作内容',
							labelStyle:'text-align:right;',
							anchor:'95%'
						},{
							xtype:'textarea',
							name:'workExecuteStock',
							id:'workExecuteStock',
							fieldLabel : '实际完成情况',
							labelStyle:'text-align:right;',
							anchor:'95%'
						} ]
					} ]
				} ]
			},{
				xtype : 'fieldset',
				title : '四、其他工作',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				border :true,
				buttonAlign :'center',
				items : [{
					layout : 'column',
					items : [{
						layout : 'form',
						columnWidth : .99,
						labelWidth : 100,
						items : [{
							xtype:'textfield',
							name:'idOther',
							hidden : true,
							id:'idOther'
						},{
							xtype:'textarea',
							name:'workPlanOther',
							id:'workPlanOther',
							fieldLabel : '计划工作内容',
							labelStyle:'text-align:right;',
							anchor:'95%'
						},{
							xtype:'textarea',
							name:'workExecuteOther',
							id:'workExecuteOther',
							fieldLabel : '实际完成情况',
							labelStyle:'text-align:right;',
							anchor:'95%'
						} ]
					} ]
				} ]
			} ],
			buttonAlign:'center',
			buttons:[{
				text :'保存',
				id :'Add',
				disabled:true,
				handler : function() {
					var workPlanNewCusts = Ext.getCmp('workPlanNewCust').getValue();
					var workPlanCredits  = Ext.getCmp('workPlanCredit').getValue();
					var workPlanStocks   = Ext.getCmp('workPlanStock').getValue();
					var workPlanOthers   = Ext.getCmp('workPlanOther').getValue();

					var idNewCusts = Ext.getCmp('idNewCust').getValue();
					var idCredits  = Ext.getCmp('idCredit').getValue();
					var idStocks   = Ext.getCmp('idStock').getValue();
					var idOthers   = Ext.getCmp('idOther').getValue();
					
					var workExecuteNewCusts = Ext.getCmp('workExecuteNewCust').getValue();
					var workExecuteCredits  = Ext.getCmp('workExecuteCredit').getValue();
					var workExecuteStocks   = Ext.getCmp('workExecuteStock').getValue();
					var workExecuteOthers   = Ext.getCmp('workExecuteOther').getValue();
					
					var workLogDates   = Ext.getCmp('workLogDate').getValue();
					var createDateNews  = Ext.getCmp('createDateNew').getValue();
					var weekIds   = Ext.getCmp('weekId').getValue();
					if (workPlanNewCusts==''&&workPlanCredits==''&&workPlanStocks==''&&workPlanOthers=='') {
						Ext.Msg.alert('提示','计划内容不允许都为空……');
						return;
					}
					Ext.Ajax.request( {
						url : basepath + '/WeekWork!weekSave.json',
						method : 'POST',
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						params : {
						 
						'workPlanNewCusts'       : 	workPlanNewCusts,
						'workPlanCredits'        : 	workPlanCredits ,
						'workPlanStocks'         :	workPlanStocks  ,
						'workPlanOthers'         : 	workPlanOthers  ,
						                         	
						'workExecuteNewCusts'	 : 	 workExecuteNewCusts,
						'workExecuteCredits'     : 	 workExecuteCredits ,
						'workExecuteStocks'      :	 workExecuteStocks  ,
						'workExecuteOthers'      : 	 workExecuteOthers  ,
						'workLogDates'			 :   workLogDates,
						'createDateNews'			 :   createDateNews,
						'weekIds'				 :   weekIds,
						
						'idNewCusts'	: 	 idNewCusts,
						'idCredits'     : 	 idCredits ,
						'idStocks'      :	 idStocks  ,
						'idOthers'      : 	 idOthers 
					},
					success : function() {
						 {
							Ext.Msg.alert('提示', '操作成功');
							workLogStore.reload();
						}
					},
					failure : function(response) {
						Ext.Msg.alert('提示','操作失败,失败原因:' + response.responseText);
					}
					});
				}
			},'-',{
				id :'rewrite',
				text :'重置',
				disabled:true,
				handler: function(){
				Ext.getCmp('workExecuteNewCust').reset();  
				Ext.getCmp('workExecuteCredit').reset();   
				Ext.getCmp('workExecuteStock').reset();    
				Ext.getCmp('workExecuteOther').reset();    
				Ext.getCmp('workPlanNewCust').reset(); 
				Ext.getCmp('workPlanCredit').reset();  
				Ext.getCmp('workPlanStock').reset();   
				Ext.getCmp('workPlanOther').reset();   
			}
			}]	
		});
		/**
		 * 条件查询panel
		 */
		var weekWorkSearchPanel = new Ext.form.FormPanel({
			title : '周工作记录查询',
			height : 135,
			width : 440,
			labelWidth : 80,// label的宽度
			labelAlign : 'right',
			frame : true,
			autoScroll : true,
			region : 'north',
			split : true,
			items : [{
				layout : 'column',
				items : [{
					columnWidth : .50,
					layout : 'form',
					items : [{
						fieldLabel: '起始时间',
						xtype : 'datefield',
						name: 'startdt',
						id: 'startdt',
						editable : false,
						vtype: 'daterange',
						endDateField: 'enddt',
						anchor : '100%'
					}, new Ext.form.ComboBox( {
						fieldLabel : '日志类型',
						labelStyle : 'text-align:right;',
						displayField : 'kind',
						valueField : 'weekTy',
						id : 'weekType',
						selectOnFocus : true,
						triggerAction : 'all',
						mode : 'local',
						anchor : '100%',
						allowBlank : false,
						store : new Ext.data.SimpleStore({
							fields : [ 'weekTy', 'kind' ],
							data : [ [ 1, '日工作记录' ],
							         [ 2, '周工作记录' ],
							         [ 3, '月工作记录' ],
							         [ 4, '季工作记录' ] ]
						}),	
						lazyRender : false
					}) ]
				}, {	
					columnWidth : .50,
						layout : 'form',
						items : [{
							fieldLabel: '结束时间',
							xtype : 'datefield',
							name: 'enddt',
							id: 'enddt',
							vtype: 'daterange',
							startDateField: 'startdt',
							anchor : '95%',
							editable : false
						}
						] }
				]
			} ],
			buttonAlign : 'center',
			buttons : [{
				text : '查询',
				handler : function() {
				var parameters = weekWorkSearchPanel.getForm().getValues(false);
				workLogStore.removeAll();
				workLogStore.baseParams = {
						'condition' : Ext.util.JSON.encode(parameters)};
				workLogStore.load( {
					params : {
						start  : 0,
						limit  : parseInt(spagesize_combo.getValue()),
						startdt:Ext.getCmp('startdt').getValue(),
						enddt  :Ext.getCmp('enddt').getValue(),
						weekType:Ext.getCmp('weekType').getValue()
				}
				});
			}
			}, {
				text : '重置',
				handler : function() {
					weekWorkSearchPanel.getForm().reset();
					Ext.getCmp('enddt').setMinValue(null);
					Ext.getCmp('startdt').setMaxValue(null);
			}
			} ]
		});
		var weekWorkGrid = new Ext.grid.GridPanel({// 产品列表数据grid
			title : '周工作记录',
			frame : true,
			region : "center",
			store : workLogStore,
			loadMask : true,
			cm : grid,
			bbar : sbbar,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			},
			listeners : {'cellclick' : function(a,b,c) {
				Ext.getCmp('Add').setDisabled(false);
				Ext.getCmp('rewrite').setDisabled(false);
				var record = weekWorkGrid.store.getAt(b);
				if (c==3){
					var record = weekWorkGrid.store.getAt(b);
					var weekId = record.data.workId;
					if (weekId != '') {
						Ext.getCmp('createDate').show();
						Ext.getCmp('createDateNew').hide();
						weekForm.getForm().load({
							restful:true,	
							url:basepath+'/week-query.json?weekId='+weekId,
							method: 'GET'
						});
					} else {
						Ext.getCmp('createDate').hide();
						Ext.getCmp('createDateNew').show();
						weekForm.getForm().reset();
					}
				}
				if (c==4){
					alert('创建中……');
				}
				Ext.getCmp('workLogDate').setValue(record.data.workLogTerm);
				var time = Ext.getCmp('workLogDate').getValue();
				var weekStry = time.substr(0,4)+'年';
				var weekStrm = time.substr(5,2)+'月第';
				var weekStrw = time.substr(8,1)+'周';
				Ext.getCmp('showWeek').setValue(weekStry+weekStrm+weekStrw);
			}
		}
		});
		// 布局模型
		var view = new Ext.Viewport( {// 页面展示
			layout : 'fit',
			frame : true,
			items : [{
			layout : 'border',
			frame : true,
			items : [ {
				region : 'west',
				layout : 'border',
				width : '500',
				items : [ weekWorkSearchPanel, weekWorkGrid ]
			}, weekForm ]
			}]
		});
		
		//判断起始时间、结束时间大小
		Ext.apply(Ext.form.VTypes, {
		    daterange : function(val, field) {
		        var date = field.parseDate(val);
		        if(!date){
		            return false;
		        }
		        if (field.startDateField) {
		            var start = Ext.getCmp(field.startDateField);
		            if (!start.maxValue || (date.getTime() != start.maxValue.getTime())) {
		                start.setMaxValue(date);
		                start.validate();
		            }
		        }
		        else if (field.endDateField) {
		            var end = Ext.getCmp(field.endDateField);
		            if (!end.minValue || (date.getTime() != end.minValue.getTime())) {
		                end.setMinValue(date);
		                end.validate();
		            }
		        }
		        return true;
		    }
		});
	});