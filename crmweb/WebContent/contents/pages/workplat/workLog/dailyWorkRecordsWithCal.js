/*
 * 客户经理日工作记录
 */	
Ext.onReady(function() {
			var dailyStore = new Ext.data.ArrayStore({
				fields:['myId','displayText'],
				data:[['1','现场拜访'],['2','电话拜访'],['3','接待来访'],
				      ['4','客户联谊'],['5','座谈沟通']]
			});			
			var search_cust_add = new Com.yucheng.bcrm.common.CustomerQueryField({ 
				fieldLabel : '新客户名称', 
				labelStyle: 'text-align:right;',
				labelWidth : 100,
				name : 'newcustname',
				id:'add_aimCustomerName',
				//custtype :'1',//客户类型：  1：对私, 2:对公,  不设默认全部
			    //custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
			    singleSelected:false,//单选复选标志
				editable : false,
				allowBlank:false,//不允许为空
				blankText:"不能为空，请填写",
				anchor : '95%',
				hiddenName:'abcd',
				callback :function(a,b){
					var cust_name = null;
					var linkNum = '';
					cust_name = a.getValue();
					if (cust_name != null && cust_name != '') {
						linkNum = a.mobileNum;
						dailyForm.getForm().findField('newcusttel').setValue(linkNum);
					}
			}
			});
			debugger;
			var search_cust_adds = new Ext.ux.form.CustomerQueryField({ 
				fieldLabel : '老客户名称', 
				labelStyle: 'text-align:right;',
				labelWidth : 100,
				name : 'oldcustname',
				id:'add_oldcustname',
				editable : false,
				allowBlank:false,//不允许为空
				blankText:"不能为空，请填写",
				singleSelected:false,           //单选复选标志
				custtype :'1',//客户类型：  1：对私, 2:对公,  不设默认全部
			    custStat:'1',//客户状态: 1:正式 2：潜在     , 不设默认全部
				anchor : '95%',
				callback :function(){
				var cust_name = null;
				var linkNum = '';
				cust_name = Ext.getCmp('add_oldcustname').getValue();
				if (cust_name != null && cust_name != '') {
					linkNum = Ext.getCmp('add_oldcustname').mobileNum.mobilenum[0];
					dailyForm.getForm().findField('oldcusttel').setValue(linkNum);
				}
			}
			});
	
			var dailyForm = new Ext.FormPanel({
				frame : true,
				autoScroll : true,
				region:'center',
				items : [ {
					xtype : 'fieldset',
					title : '日工作记录',
					titleCollapse : true,
					collapsible : true,
					autoHeight : true,
					items : [ {
						layout : 'column',
						items : [ {
							layout : 'form',
							columnWidth : .32,
							labelWidth : 80,
							items : [
							         {xtype:'textfield',fieldLabel:'日工作记录',name:'logid',labelStyle:'text-align:right;',hidden:true,anchor:'90%'},
							         {xtype:'datefield',fieldLabel:'工作日期	',id:'etldates',editable : false,labelStyle:'text-align:right;',value:new Date(),format : 'Y-m-d',anchor:'90%',listeners:{'change':function(a,b,c){
							        	 Ext.getCmp('etldate').setValue(a.getValue());
							         }}},
							         {xtype:'datefield',fieldLabel:'工作日期	',editable : false,name:'etldate',id:'etldate',hidden:true,value:new Date(),labelStyle:'text-align:right;',format : 'Y-m-d',anchor:'90%'},
							         {xtype:'datefield',fieldLabel:'补录日期	',editable : false,name:'adddate',id:'adddate',labelStyle:'text-align:right;',value:new Date(),hidden:true,format : 'Y-m-d',anchor:'90%'}
							         ]
						}, {
							layout : 'form',
							columnWidth : .32,
							labelWidth : 100,
							items : [ 
							         {xtype:'textfield',fieldLabel:'客户经理编号',id:'owenerids',value:__userId,labelStyle:'text-align:right;',readOnly:true,anchor:'95%'},
							         {xtype:'textfield',fieldLabel:'客户经理编号',name:'owenerid',id:'owenerid',value:__userId,labelStyle:'text-align:right;',readOnly:true,anchor:'95%',hidden:true}
							         ]
						}, {
							layout : 'form',
							columnWidth : .32,
							labelWidth : 100,
							items : [
							         {xtype:'textfield',fieldLabel:'客户经理姓名',id:'owenernames',value:__userName,labelStyle:'text-align:right;',readOnly:true,anchor:'95%'},
							         {xtype:'textfield',fieldLabel:'客户经理姓名',name:'owenername',id:'owenername',value:__userName,labelStyle:'text-align:right;',readOnly:true,anchor:'95%',hidden:true}
							         ]
						} ]
					} ]
				}	,{
					xtype : 'fieldset',
					title : '一、新客户或目标客户联系和拜访 ',
					titleCollapse : true,
					collapsible : true,
					autoHeight : true,
					buttonAlign :'center',
					items : [ {
						layout : 'column',
						items : [ {
							layout : 'form',
							columnWidth : .48,
							labelWidth : 150,
							items : [
							         search_cust_add,
							         {xtype:'numberfield',fieldLabel:'联系电话',name:'newcusttel',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
							         ]
						}, {
							layout : 'form',
							columnWidth : .48,
							labelWidth : 150,
							items : [{
								store : dailyStore,xtype : 'combo', resizable : true,name : 'newctvitme',hiddenName : 'newctvitme',
								fieldLabel : '拜访方式',valueField : 'myId',displayField : 'displayText',mode : 'local',editable : false,
								typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',
								selectOnFocus : true,anchor : '95%'
							},
							{xtype:'textfield',fieldLabel:'拜访人',name:'newcustvtman',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
							]
						},{
							layout : 'form',
							columnWidth : .99,
							labelWidth : 150,
							items : [  
							         {xtype:'textarea',fieldLabel:'新客户联系和拜访情况',name:'newcustthing',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
							         ]
						} ]
					} ]
				},{
					xtype : 'fieldset',
					title : '二、老客户日常联系 ',
					titleCollapse : true,
					collapsible : true,
					autoHeight : true,
					buttonAlign :'center',
					items : [ {
						layout : 'column',
						items : [ {
							layout : 'form',
							columnWidth : .48,
							labelWidth : 150,
							items : [
							         search_cust_adds,
							         {xtype:'numberfield',fieldLabel:'联系电话',name:'oldcusttel',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
							         ]
						}, {
							layout : 'form',
							columnWidth : .48,
							labelWidth : 150,
							items : [{
								store : dailyStore,xtype : 'combo', resizable : true,name : 'oldctvitme',hiddenName : 'oldctvitme',
								fieldLabel : '拜访方式',valueField : 'myId',displayField : 'displayText',mode : 'local',editable : false,
								typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',labelStyle:'text-align:right;',
								selectOnFocus : true,anchor : '95%'
							},
							{xtype:'textfield',fieldLabel:'拜访人',name:'oldcustvtman',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
							]
						},{
							layout : 'form',
							columnWidth : .99,
							labelWidth : 150,
							items : [  
							         {xtype:'textarea',fieldLabel:'老客户联系和拜访情况',name:'oldcustthing',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
							         ]
						} ]
					} ]
				},{
					xtype : 'fieldset',
					title : '三、月度、周计划进度和完成情况（截至当日情况)',
					titleCollapse : true,
					collapsible : true,
					autoHeight : true,
					buttonAlign :'center',
					items : [ {
						layout : 'column',
						items : [{
							layout : 'form',
							columnWidth : .99,
							labelWidth : 150,
							items : [  
							         {xtype:'textarea',name:'monweekplan',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
							         ]
						} ]
					} ]
				},{  	
					xtype : 'fieldset',
					title : '四、贷后检查工作 ',
					titleCollapse : true,
					collapsible : true,
					autoHeight : true,
					buttonAlign :'center',
					items : [{
						layout : 'column',
						items : [ {
							layout : 'form',
							columnWidth : .48,
							labelWidth : 150,
							items : [
					         {xtype:'textfield',fieldLabel:'企业名称',name:'loancomname',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
					         ]
						}, {
							layout : 'form',
							columnWidth : .48,
							labelWidth : 150,
							items : [  
							         {xtype:'numberfield',fieldLabel:'贷款余额',name:'loancombal',value:0,labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
							         ]
						},{
							layout : 'form',
							columnWidth : .99,
							labelWidth : 150,
							items : [  
							         {xtype:'textarea',fieldLabel:'检查落实情况',name:'loanproce',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
							         ]
						} ]
					} ]
				},{
					xtype : 'fieldset',
					title : '五、关注和需要解决的问题以及对分行的建议 ',
					titleCollapse : true,
					collapsible : true,
					autoHeight : true,
					buttonAlign :'center',
					baseCls :'x-fieldset',
					items : [ {
						layout : 'column',
						items : [{
							layout : 'form',
							columnWidth : .99,
							labelWidth : 150,
							items : [  
							         {xtype:'textarea',name:'needtodo',labelStyle:'text-align:right;',readOnly:false,anchor:'95%'}
							         ]
						} ]
					} ]
				}],
				buttonAlign:'center',
				buttons:[{
					text :'保存',
					disabled:true,
					id :'Add',
					handler: function(){
					if (!dailyForm.getForm().isValid()) {
						Ext.Msg.alert("系统提示信息", "输入有误或存在漏输项，请重新输入!");
						return false;
					}
					var	test = Ext.getCmp('etldates').getValue();
					var test1 = test.format('Y-m-d');
					var test2 = new Date();
					var test3 = test2.format('Y-m-d');
					if(test1 > test3){
						Ext.Msg.alert("系统提示信息", "工作日期不能大于 当前日期，请重新输入!");
						return false;
					}
					Ext.Ajax.request( {
						url : basepath + '/dailyWork-Action.json',
						method : 'POST',
						form : dailyForm.getForm().id,
						success : checkResult,
						failure: checkResult,
						params : {
						'operate':'add'
					}
					});
					function checkResult(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						var resultError = response.responseText;
						if ((resultArray == 200 || resultArray == 201) && resultError == '') {
							Ext.Msg.alert('系统提示信息', '操作成功');
							Ext.getCmp('Add').setDisabled(true);
							Ext.getCmp('rewrite').setDisabled(true);
							eventStore.load({
								params:{ 
								start:calPanel.activeView.viewStart.dateFormat('Y-m-d'),
								end:calPanel.activeView.viewEnd.dateFormat('Y-m-d')
							},callback:function(){
							}
							});
						} else   {
							Ext.Msg.alert('提示', '当日的工作记录已添加，请取消！');
						}
					}
				}
				},'-',{
					id :'rewrite',
					text :'重置',
					disabled:true,
					handler: function(){
					dailyForm.getForm().reset();
				}
				}]
			});
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
					EventId:     {name: 'logid', type:'string'},
					CalendarId:  {name: 'CalID', type: 'string'}, 
					Title:       {name: 'owenername'},
					StartDate:   {name: 'etldate', type: 'date', dateFormat: 'c'},
					EndDate:     {name: 'etldate', type: 'date', dateFormat: 'c'},
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
			                  {name:'logid',mapping:'LOGID'},
			                  {name:'owenerid',mapping:'OWENERID'},
			                  {name:'owenername',mapping:'OWENERNAME'},
			                  {name:'newcustname',mapping:'NEWCUSTNAME'},
			                  {name:'newctvitme',mapping:'NEWCTVITME'},
			                  {name:'newcusttel',mapping:'NEWCUSTTEL'},
			                  {name:'newcustvtman',mapping:'NEWCUSTVTMAN'},
			                  {name:'newcustthing',mapping:'NEWCUSTTHING'},
			                  {name:'oldcustname',mapping:'OLDCUSTNAME'},
			                  {name:'oldctvitme',mapping:'OLDCTVITME'},
			                  {name:'oldcusttel',mapping:'OLDCUSTTEL'},
			                  {name:'oldcustvtman',mapping:'OLDCUSTVTMAN'},
			                  {name:'oldcustthing',mapping:'OLDCUSTTHING'},
			                  {name:'monweekplan',mapping:'MONWEEKPLAN'},
			                  {name:'loancomname',mapping:'LOANCOMNAME'},
			                  {name:'loancombal',mapping:'LOANCOMBAL'},
			                  {name:'loanproce',mapping:'LOANPROCE'},
			                  {name:'needtodo',mapping:'NEEDTODO'},
			                  {name:'etldate',mapping:'ETLDATE',type: 'date', dateFormat: 'c'},
			                  {name:'adddate',mapping:'ADDDATE'},
			                  {name:'all_day',mapping:'ALL_DAY', type:'boolean'}
			                  ];
			Ext.ensible.cal.EventRecord.reconfigure();  
			var eventProxy = new Ext.data.HttpProxy({
				api:{
				read:{
				url:basepath+'/dailyWorkCal-Action.json',
				method:'GET'
			}
			}
			});
    
			var eventReader = new Ext.data.JsonReader({
				idProperty: 'logid',
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
			calPanel.on('dayclick',function(a,b,c,d,f){debugger;
			dailyForm.getForm().reset();	
			Ext.getCmp('etldates').setValue(b);
			Ext.getCmp('etldate').setValue(b);
			dailyForm.setDisabled(false);
			Ext.getCmp('Add').setDisabled(false);
			Ext.getCmp('rewrite').setDisabled(false);
			return false;
			});
			calPanel.on('eventclick',function(a,b,c,d,f){
				Ext.getCmp('rewrite').setDisabled(true);
				dailyForm.getForm().loadRecord(b);
				Ext.getCmp('etldates').setValue(b.data.etldate);
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
				layout : 'fit',
				frame : true,
				items : [{
				layout:'border',
				frame:true,
				items:[{
					region : 'west',
					layout : 'fit',
					width :'380',
					items : [calPanel]
				},
				dailyForm]
				}]
			});	
			eventStore.load({
				params:{ 
				start:calPanel.activeView.viewStart.dateFormat('Y-m-d'),
				end:calPanel.activeView.viewEnd.dateFormat('Y-m-d')
			},callback:function(){
			}
			});
	});
