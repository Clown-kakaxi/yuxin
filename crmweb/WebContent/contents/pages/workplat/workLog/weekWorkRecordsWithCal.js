/**
 * 客户经理周工作记录
 */	
Ext.onReady(function() {
		var deleteId = '';
		var rownum1 = new Ext.grid.RowNumberer( {
			header : 'No.',
			width : 28
		});
		var sm = new Ext.grid.CheckboxSelectionModel();
		// 列模型
		var columns1 = new Ext.grid.ColumnModel( [rownum1, sm,{
			header : 'ID',
			dataIndex : 'weekId',
			id:'weekId',
			sortable : true,
			hidden : true,
			width : 120
		}, {
			header : '工作类型',
			dataIndex : 'workType',
			sortable : true,
			width : 120,
			renderer : function(value) {
				if (value == 1) {
					return '新客户拜访工作安排';
				} else if (value == 2) {
					return '存量客户维护工作安排';
				} else if (value == 3) {
					return '贷后检查工作安排';
				} else if (value == 4) {
					return '其他工作';
				} else
					return '请选择';
			},
			editor : new Ext.form.ComboBox( {
				displayField : 'kind',
				valueField : 'number',
				selectOnFocus : true,
				triggerAction : 'all',
				editable : false,
				mode : 'local',
				allowBlank : false,
				store : new Ext.data.SimpleStore( {
					fields : [ 'number', 'kind' ],
					data : [ [ 1, '新客户拜访工作安排' ], [ 2, '存量客户维护工作安排' ],
					         [ 3, '贷后检查工作安排' ], [ 4, '其他工作' ] ]
				}),
				lazyRender : false
			})
		},{
			header : '计划工作内容',
			dataIndex : 'workPlan',
			width : 220,
			editor : new Ext.grid.GridEditor(new Ext.form.TextArea( {
				allowBlank : false,
				height : '150',
				width : '210'
			}))
		}, {
			header : '实际完成情况',
			dataIndex : 'workExecute',
			width : 220,
			editor : new Ext.grid.GridEditor(new Ext.form.TextArea( {
				allowBlank : false,
				height : '150',
				width : '180'
			}))
		} ]);
			
		var record = new Ext.data.Record.create( [ {
			name : 'weekId',
			mapping : 'ID'
		}, {
			name : 'workLogId',
			mapping : 'WORKLOG_ID'
		}, {
			name : 'workType',
			mapping : 'WORK_TYPE'
		}, {
			name : 'workPlan',
			mapping : 'WORK_PLAN'
		}, {
			name : 'workOrder',
			mapping : 'WORK_ORDER'
		}, {
			name : 'workExecute',
			mapping : 'WORK_EXECUTE'
		} ]);
		
		var queryStore = new Ext.data.Store( {
			restful : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/weekQuery-Action.json',
				method : 'GET'
			}),
			reader : new Ext.data.JsonReader( {
				successProperty : 'success',
				root : 'json.data',
				totalProperty : 'json.count'
			}, record)
		});
		/**
		 * 展示详细信息的grid
		 */
		var weekPanel = new Ext.grid.EditorGridPanel( {
			store : queryStore,
			frame : true,
			sm : sm,
			cm : columns1,
			clicksToEdit : 1,
			stripeRows : true,
			height : 340,
			tbar : [ {
				text : '添加记录',
				handler : function() {
				var data = { 
						json : {
							data : [ {
								workType : '',
								workPlan : '',
								workExecute : '',
								weekId : ''
							} ]
				}
				};
				queryStore.loadData(data, true);
			} },
			'-',
			{
				text : '删除记录',
				handler : function() {
				Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId) {
					if (buttonId.toLowerCase() == "no") {
						return;
					}
					var length = weekPanel.view.grid.selModel.getSelections().length;
					var selections = weekPanel.view.grid.selModel.getSelections();
					for ( var int = 0; int < length; int++) {
						var wId = weekPanel.view.grid.selModel.selections.items[0].data.weekId;
						if (wId != "") {
							deleteId = deleteId + wId + ',';// 记录要删除的记录ID，点击保存按钮的时候提交后台
						}
						queryStore.remove(selections[int]);
					}
				});
			}
			} ],
			frame : true,
			viewConfig : {
			// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
		});
			
		/**
		 * 周记录信息panel
		 */
		var weekDiaryForm = new Ext.FormPanel( {
			frame : true,
			autoScroll : true,
			region : 'center',
			items : [ {
				xtype : 'fieldset',
				title : '客户开发与维护工作计划及完成情况',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				items : [ {
					layout : 'column',
					items : [ {
						layout : 'form',
						columnWidth : .40,
						labelWidth : 100,
						items : [ {
							xtype : 'textfield',
							fieldLabel : '周工作记录',
							name : 'weekId',
							labelStyle : 'text-align:right;',
							hidden : true,
							anchor : '90%'
						},{
							xtype : 'datefield',
							fieldLabel : '日期',
							editable : false,
							name : 'createDate',
							id : 'createDate',
							labelStyle : 'text-align:right;',
							value : new Date(),
							format : 'Y-m-d',
							anchor : '90%'
						},
						{
							xtype : 'textfield',
							fieldLabel : '周期',
							id : 'workTyId',
							labelStyle : 'text-align:right;',
							readOnly : true,
							anchor : '90%',
							listeners  :{'render':function(){
								
								/*var ym=dt.format("y年m月d日");
								 var dm=dt.format("d") ;
								 alert('--'+dm/7);
								if((dm/7)>0&&(dm/7)<=1){
								alert(ym+"第1周");
								}
								else if((dm/7)>1&&(dm/7)<=2){
								alert(ym+"第2周");
								}
								else if((dm/7)>2&&(dm/7)<=3){
								alert(ym+"第3周");
								}
								else
								{
								alert(ym+"第4周");
								}*/
								var time = Ext.getCmp('createDate').getValue();
								alert(time);
								var dt = time.format('d');
								var ym=time.format("y年m月d日").substr(0,4);
								
								if ((dt / 7) > 0 && (dt / 7) <= 1) {
									Ext.getCmp('workTyId').setValue(ym+'第1周');
									Ext.getCmp('workTyIdhide').setValue('01');} 
								else if ((dt / 7) > 1 && (dt / 7) <= 2) {
									Ext.getCmp('workTyId').setValue(ym+'第2周');
									Ext.getCmp('workTyIdhide').setValue('02');
								} else if ((dt / 7) > 2 && (dt / 7) <= 3) {
									Ext.getCmp('workTyId').setValue(ym+'第3周');
									Ext.getCmp('workTyIdhide').setValue('03');
								} else {
									Ext.getCmp('workTyId').setValue(ym+'第4周');
									Ext.getCmp('workTyIdhide').setValue('04');
								}
							}}
						},
						{
							xtype : 'textfield',
							fieldLabel : '周期',
							id : 'workTyIdhide',
							name :'workTy',
							labelStyle : 'text-align:right;',
							hidden : true,
							anchor : '90%'
						} ]
					}, {
						layout : 'form',
						columnWidth : .40,
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
			}, weekPanel ],
			buttonAlign : 'center',
			buttons : [ {
				text : '保存',
				disabled : true,
				id : 'Add',
				handler : function() {
				var json1 = {'workType' : []};
				var json2 = {'workPlan' : []};
				var json3 = {'workExecute' : []};
				var json4 = {'weekId' : []};
				for ( var i = 0; i < queryStore.getCount(); i++) {
					var temp = queryStore.getAt(i);
					json1.workType.push(temp.data.workType);
					json2.workPlan.push(temp.data.workPlan);
					json3.workExecute.push(temp.data.workExecute);
					json4.weekId.push(temp.data.weekId);
				}
				var temp1 = Ext.encode(json1).toString();
				var temp2 = Ext.encode(json2).toString();
				var temp3 = Ext.encode(json3).toString();
				var	temp4 = Ext.encode(json4).toString();
				var str = Ext.getCmp('createDate').getValue().format('Y-m-d').substr(0,8)
							+ Ext.getCmp('workTyIdhide').getValue();
				var temp1str = temp1.substr(temp1.length-4,temp1.length);
				var temp2str = temp2.substr(temp2.length-4,temp1.length);
				if (temp1str=="\"\"]}") {
					Ext.Msg.alert('提示','工作类型不许为空……');
					return;
				}
				if (temp2str=="\"\"]}") {
					Ext.Msg.alert('提示','工作计划内容不许为空……');
					return;
				}
				Ext.Ajax.request( {
					url : basepath + '/WeekWork!weekSave.json',
					method : 'POST',
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					params : {
						'workTypeshow'		: 	temp1,
						'workPlanshow' 		: 	temp2,
						'workExecuteshow' 	:	temp3,
						'workIdshow' 		: 	temp4,
						'workcreateDateshow': 	Ext.getCmp('createDate').getValue(),
						'workTyshow' 	    : 	str
				},
				success : function() {
					if (deleteId != '') {
						var delIds = deleteId.substring(0,deleteId.length - 1);
						Ext.Ajax.request( {
							url : basepath + '/WeekWork!batchDestroy.json',
							method : 'post',
							params : {
								idStr : deleteId
						},
						success : function() {
							deleteId = '';// 清空待删除的ID
							Ext.Msg.alert('提示',	'操作成功');
							queryStore.reload();
						}
						});
					} else {
						Ext.Msg.alert('提示', '操作成功');
						queryStore.reload();
					}
				},
				failure : function(response) {
					deleteId = '';// 清空待删除的ID
					Ext.Msg.alert('提示','操作失败,失败原因:' + response.responseText);
					queryStore.reload();
				}
				});
			}
			} ]
		});
		
		/**
		 * 日历展示信息
		 */
			Ext.ensible.cal.CalendarMappings = {
					CalendarId:   {name:'ID', mapping: 'remindBelong', type: 'string'},
					Title:        {name:'CalTitle', mapping: 'cal_title', type: 'string'},
					Description:  {name:'Desc', mapping: 'cal_desc', type: 'string'},
					ColorId:      {name:'Color', mapping: 'cal_color', type: 'int'},
					IsHidden:     {name:'Hidden', mapping: 'hidden', type: 'boolean'}
			};
			Ext.ensible.cal.CalendarRecord.reconfigure();
			var calendarStore = new Ext.ensible.sample.CalendarStore({
				data: Ext.ensible.sample.CalendarDataCustom
			});  
			Ext.ensible.cal.EventMappings = {
					EventId:     {name: 'id', type:'string'},
					CalendarId:  {name: 'CalID', type: 'string'}, 
					Title:       {name: 'userName'},
					StartDate:   {name: 'createDate', type: 'date', dateFormat: 'c'},
					EndDate:     {name: 'createDate', type: 'date', dateFormat: 'c'},
					RRule:       {name: 'RecurRule', mapping: 'recur_rule'},
					Location:    {name: 'Location', mapping: 'location'},
					Notes:       {name: 'Desc', mapping: 'SCHEDULE_CONTENT'},
					Url:         {name: 'LinkUrl', mapping: 'link_url'},
					IsAllDay:    {name: 'all_day', type: 'boolean'},
					Reminder:    {name: 'Reminder', mapping: 'reminder'},    
					RemindType:  {name: 'RemindType', mapping: 'REMIND_TYPE', type: 'string'},
					relatedCustomer:{name: 'relatedCustomer', mapping:'RELATION_CUST', type:'string'},                
					CreatedBy:   {name: 'CreatedBy', mapping: 'CREATOR'},
					IsPrivate:   {name: 'Private', mapping:'private', type:'boolean'}
			};
			var dataFields = [
	                  {name : 'id',mapping : 'ID'}, 
	                  {name : 'workLogId',mapping : 'WORKLOG_ID'}, 
	                  {name : 'workType',mapping : 'WORK_TYPE'},
	                  {name : 'workPlan',mapping : 'WORK_PLAN'},
	                  {name : 'workOrder',mapping : 'WORK_ORDER'}, 
	                  {name : 'workExecute',mapping : 'WORK_EXECUTE'},
	                  
	                  {name :'userName',mapping :'USER_NAME'},
	                  {name:'all_day',mapping:'ALL_DAY', type:'boolean'}
		   ];
			Ext.ensible.cal.EventRecord.reconfigure();  
			var eventProxy = new Ext.data.HttpProxy({
				api:{
				read:{
				url:basepath+'/weekCal-Action.json',
				method:'GET'
			}
			}
			});
    
			var eventReader = new Ext.data.JsonReader({
				idProperty: 'id',
				root: 'json.data',
				fields: dataFields
			});
			
			var eventStore = new Ext.ensible.cal.EventStore({
				proxy : eventProxy,
				reader : eventReader
			});
			Ext.ensible.cal.EventRecord.reconfigure();  
			var calPanel = new Ext.ensible.cal.CalendarPanel({
				eventStore : eventStore,
				calendarStore : calendarStore,
				showDayView : false,
				showWeekView : false
			});
			calPanel.on('dayclick',function(a,b,c,d,f){
				Ext.getCmp('createDate').setValue(b);
				weekDiaryForm.setDisabled(false);
				var dt = b.format('d');
				if ((dt / 7) > 0 && (dt / 7) <= 1) {
					Ext.getCmp('workTyId').setValue('第1周');
					Ext.getCmp('workTyIdhide').setValue('01');
				} else if ((dt / 7) > 1 && (dt / 7) <= 2) {
					Ext.getCmp('workTyId').setValue('第2周');
					Ext.getCmp('workTyIdhide').setValue('02');
				} else if ((dt / 7) > 2 && (dt / 7) <= 3) {
					Ext.getCmp('workTyId').setValue('第3周');
					Ext.getCmp('workTyIdhide').setValue('03');
				} else {
					Ext.getCmp('workTyId').setValue('第4周');
					Ext.getCmp('workTyIdhide').setValue('04');
				}
				Ext.getCmp('Add').setDisabled(false);
				queryStore.reload({
					params:{
					'workLogDate' : Ext.getCmp('createDate').getValue().format('Y-m-d').substr(0,8)
									+ Ext.getCmp('workTyIdhide').getValue() //工作周期
					}
				});
				return false;
			});
			calPanel.on('eventclick',function(a,b,c,d,f){
				weekDiaryForm.getForm().loadRecord(b);
				if(b.data.owenerid==__userId){
					Ext.getCmp('Add').setDisabled(false);
				}else{
					Ext.getCmp('Add').setDisabled(true);
				}
				return false;
			});
			calPanel.items.items[1].enableDD = false;
			Ext.each(calPanel.items.items,function(it){
				it.enableDD = false;
				it.enableContextMenus=false;
			});
	
			var view = new Ext.Viewport({
				layout:'border',
				items:[{
					region : 'west',
					layout : 'fit',
					width :'460',
					items : [calPanel]
				},
				weekDiaryForm]
			});	
			eventStore.load({
				params:{ 
				'workLogDate' :Ext.getCmp('createDate').getValue().format('Y-m-d').substr(0,8)
								+ Ext.getCmp('workTyIdhide').getValue(), //工作周期
				'start' :  calPanel.activeView.viewStart.dateFormat('Y-m-d'),
				'end'   :  calPanel.activeView.viewEnd.dateFormat('Y-m-d')
			},callback:function(){
			}
			});
	});
