Ext.ns('Com.yucheng.bcrm.common');
imports([
      	,'/contents/resource/ext3/ux/Ext.ux.Notification.js'
      ]);
/**
 * CallReport放大镜
 * 
 * @author denghj
 * @since 2015-9-11
 */
Com.yucheng.bcrm.common.CallReportQueryFieldSME = Ext.extend(
		Ext.form.TwinTriggerField, {
			initComponent : function() {
				Ext.ux.form.SearchField.superclass.initComponent.call(this);
				this.on('specialkey', function(f, e) {
							if (e.getKey() == e.ENTER) {
								this.onTrigger2Click();
							}
						}, this);
			},
			
			onRender : function(ct, position) {
				Com.yucheng.bcrm.common.CallReportQueryFieldSME.superclass.onRender.call(this, ct, position);
				if (this.hiddenName) {
					var ownerForm = this;
					while (ownerForm.ownerCt && !Ext.instanceOf(ownerForm.ownerCt, 'form')) { // 根据条件查询放大镜控件的最外层容器
						ownerForm = ownerForm.ownerCt;
					};
					if (Ext.instanceOf(ownerForm.ownerCt, 'form')) { // 判断父容器是否为form类型
						ownerForm = ownerForm.ownerCt;
						if (ownerForm.getForm().findField(this.hiddenName)) { // 如果已经创建隐藏域
							this.hiddenField = ownerForm.getForm().findField(this.hiddenName);
						} else { // 如果未创建隐藏域，则根据hiddenName属性创建隐藏域
							this.hiddenField = ownerForm.add({
										xtype : 'hidden',
										id : this.hiddenName,
										name : this.hiddenName
									});
						}
					}
				}
			},
		
			autoLoadFlag: false,
		    singleSelected:false,//记录标志 true单选,false多选
		    callback:false,
		    custId:'',//客户ID
		    custName:'',//客户名称
		    mgrId:'',//客户经理ID
		    mgrName:'',//客户经理姓名
		    visitType:'',//拜访类型
		    visitTime:'',//预约拜访日期
		    callTime:'',//实际拜访日期
		    intervieweeName:'',//受访人
		    intervieweePost:'',//受访人职务
		    intervieweePhone:'',//受访人电话
		    joinPerson:'',//参与人员
		    resCustSource:'',//客户来源
		    potentialFlag:'',
		    isSxCust:'',
		    jobType:'',
		    industType:'',
		    validationEvent:false,
		    validateOnBlur:false,
		    trigger1Class:'x-form-clear-trigger',
		    trigger2Class:'x-form-search-trigger',
		    hideTrigger1:true,
		    width:180,
		    hasSearch : false,
		    paramName : 'query',
		    hiddenName:false, //用于存隐藏ID字段
		    oCallReportQueryWindow : false,
		    editable:false,
		    listeners:{//增加鼠标点击放大镜输入框触发onTrigger2Click事件
				focus:function(){
					if(!this.disabled){ //禁用的放大镜不允许弹出选择					
						this.onTrigger2Click();
					}
				}
			},
		  onTrigger2Click : function(){
		    	//禁用的放大镜不允许弹出选择
				if(this.disabled){
					return ;
				}
				if(CUST_ID == ''){
					Ext.Msg.alert("提示","请先选择客户！");
   				    return ;
				}
		    	var _this=this;
//		    	if(_this.oCallReportQueryWindow){
//		    		_this.oCallReportQueryWindow.show();
//		    		return;
//		    	}
		    	
		    	
		    	var oThisSearchField=_this;
		    	//定义store------存储拜访类型VISIT_TYPE--------------
		    	_this.boxstore = new Ext.data.Store({  
		    		restful:true,   
		    		autoLoad :true,		    		
		    		proxy : new Ext.data.HttpProxy({
		    				url :basepath+'/lookup.json?name=VISIT_TYPE_ALL'
		    		}),
		    		reader : new Ext.data.JsonReader({
		    			root : 'JSON'
		    		}, [ 'key', 'value' ])
		    		
		    	});
		    	_this.boxstore.load();		    			  
		    	_this.oCallReportQueryForm = new Ext.form.FormPanel({//查询Form定义
		    		id:'oCallReportQueryForm',
					frame : true, //是否渲染表单面板背景色
					labelAlign : 'middle', // 标签对齐方式
					buttonAlign : 'center',
					region:'north',
					height : 80,
					width : 1000,
					items : [{
						layout : 'column',
						border : false,
						items : [{
							columnWidth : .25,
							layout : 'form',
							labelWidth : 80, // 标签宽度
							border : false,
							items : [
							    new Ext.form.ComboBox({
								hiddenName : 'VISIT_TYPE',
								name : 'VISIT_TYPE',
								fieldLabel : '拜访类型',
//								value:oThisSearchField.visitType,
								labelStyle: 'text-align:right;',
								triggerAction : 'all',
								store : _this.boxstore,
								displayField : 'value',
								valueField : 'key',
								mode : 'local',
								editable:false,
								forceSelection : true,
								typeAhead : true,
//								emptyText:'请选择',
								resizable : true,
								anchor : '90%'
							})]
						},{
							columnWidth : .25,
							layout : 'form',
							labelWidth : 80, // 标签宽度
							border : false,
							items : [{
								fieldLabel : '实际拜访日期',
								name : 'CALL_TIME',
								xtype : 'datefield', // 设置为日期输入框类型
								format : 'Y-m-d',
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}]
						},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80, // 标签宽度
						border : false,
						items : [{
							fieldLabel : '受访人',
							name : 'INTERVIEWEE_NAME',
							xtype : 'textfield', // 设置为文本输入框类型
							labelStyle: 'text-align:right;',
							anchor : '90%'
					    }]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80, // 标签宽度
						border : false,
						items : [{
							fieldLabel : '参与人员',
							name : 'JOIN_PERSON',
							xtype : 'textfield', // 设置为文本输入框类型
							labelStyle: 'text-align:right;',
							anchor : '90%'
					    }]
					}]
				}],
//					listeners :{
//		    			'render':function(){}
//					},
					buttons : [{
						text : '查询',
						handler : function(){
//							_this.oCallReportQueryStore.removeAll();
							_this.oCallReportQueryStore.on('beforeload', function() {
								var conditionStr =  _this.oCallReportQueryForm.getForm().getValues(false);
								this.baseParams = {
										"condition":Ext.encode(conditionStr)										
								};
							});
							_this.oCallReportQueryStore.reload({
								params : {
									start : 0,
									limit : _this.oCallReportQueryBbar.pageSize
								},
								callback : function(r, options, success) {   
									   if(_this.oCallReportQueryStore.getTotalCount() <= 0){
											Ext.Msg.alert('提示信息', '未查询到3个月内有效的拜访信息,请先拜访客户！');
//							        		showMsgNotification('未查询到3个月内有效的拜访信息,请先拜访客户！',300000);
									   }
							      }
							});
						}
					},{
						text : '重置',
						handler : function() {
							_this.oCallReportQueryForm.getForm().reset();  
//							_this.oCallReportQueryForm.getForm().findField('MGR_ID').setValue('');//客户经理ID为空
//							_this.oCallReportQueryForm.getForm().findField('CUST_ID').setValue('');//客户ID为空
						}
					}]
				});
		    	_this.sm = new Ext.grid.CheckboxSelectionModel({singleSelect:oThisSearchField.singleSelected});
		    	// 定义自动当前页行号
		    	_this.rownum = new Ext.grid.RowNumberer({
		    		header : 'No.',
		    		width : 28
		    	});
			    
		    	// 定义列模型
		    	_this.cm = new Ext.grid.ColumnModel([_this.rownum,_this.sm, 
		    	    {header : '客户号',dataIndex : 'CUST_ID',sortable : true,width : 150},
		    	    {header : '客户名称',dataIndex : 'CUST_NAME',width : 200,sortable : true},
		    	    {header : '客户经理ID',dataIndex : 'MGR_ID',width : 150,sortable : true,hidden : true},
		    	    {header : '客户经理',dataIndex : 'MGR_NAME',width : 150,sortable : true},
		    	    {header : '拜访类型',dataIndex : 'VISIT_TYPE',width : 150,sortable : true,hidden:true},
		    	    {header : '拜访类型',dataIndex : 'VISIT_TYPE_ORA',width : 150,sortable : true},
		    	    {header : '预约拜访日期',dataIndex : 'VISIT_TIME',width : 150,sortable : true},
		    	    {header : '实际拜访日期',dataIndex : 'CALL_TIME',width : 150,sortable : true},
		    	    {header : '受访人',dataIndex : 'INTERVIEWEE_NAME',width : 150,sortable : true},
		    	    {header : '受访人职务',dataIndex : 'INTERVIEWEE_POST',width : 150,sortable : true},
		    	    {header : '受访人电话',dataIndex : 'INTERVIEWEE_PHONE',width : 200,sortable : true},		    	   
		    	    {header : '参与人员',dataIndex : 'JOIN_PERSON',width : 200,sortable : true},
		    	    {header : '客户来源',dataIndex : 'RES_CUSTSOURCE',width : 200,sortable : true}
		    	]);
		    	
		    	
		    	/**
		    	 * 数据存储
		    	 */	
		    	_this.oCallReportQueryStore = new Ext.data.Store({
		    		restful:true,	
		    		proxy : new Ext.data.HttpProxy({url : basepath+'/smeInterviewVisitTime.json?custId='+CUST_ID}),
		    		reader: new Ext.data.JsonReader({
		    			totalProperty : 'json.count',
		    			root:'json.data'
		    		}, [{name : 'CUST_ID'},
		    		    {name : 'CUST_NAME'},
		    		    {name : 'MGR_ID'},
		    		    {name : 'MGR_NAME'},
		    		    {name : 'VISIT_TYPE'},
		    		    {name : 'VISIT_TYPE_ORA'},
		    		    {name : 'VISIT_TIME'},
		    		    {name : 'CALL_TIME'},
		    		    {name : 'INTERVIEWEE_NAME'},
		    		    {name : 'INTERVIEWEE_POST'},
		    		    {name : 'INTERVIEWEE_PHONE'},
		    		    {name : 'JOIN_PERSON'},
		    		    {name : 'RES_CUSTSOURCE'}
		    		    ])
		    	});

		    	_this.oPagesizeCombo = new Ext.form.ComboBox({
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
		    	
		    	_this.number = parseInt(_this.oPagesizeCombo.getValue());
		    	_this.oPagesizeCombo.on("select", function(comboBox){
		    		_this.oCallReportQueryBbar.pageSize = parseInt(_this.oPagesizeCombo.getValue()),
		    		_this.oCallReportQueryStore.load({
		    			params : {
		    				start : 0,
		    				limit : parseInt(_this.oPagesizeCombo.getValue())
		    			}
		    		});
		    	});
		    	_this.oCallReportQueryBbar = new Ext.PagingToolbar({
		    		id:'oCallReportQueryBbarId', 
		    		pageSize : _this.number,
		    		store : _this.oCallReportQueryStore,
		    		displayInfo : true,
		    		displayMsg : '显示{0}条到{1}条,共{2}条',
		    		emptyMsg : "没有符合条件的记录",
		    		items : ['-', '&nbsp;&nbsp;', _this.oPagesizeCombo]
		    	});
				// 表格实例
		    	_this.oCallReportQueryGrid = new Ext.grid.GridPanel({
		    		id:'oCallReportQueryGridId',
		    		height : 275,
					width:1000,
					region:'center',
					frame : true,
					autoScroll : true,
					tbar:[{text:'客户来源及日期',
						 hidden:!_this.chooseCustSource,
						 handler:function(){
							 chooseCustSource();
					}}],
					store : _this.oCallReportQueryStore, // 数据存储
					stripeRows : true, // 斑马线
					cm : _this.cm, // 列模型
					sm : _this.sm, // 复选框
					bbar:_this.oCallReportQueryBbar,
					viewConfig:{
		    			forceFit:false,
		    			autoScroll:true
		    		},
		    		loadMask : {
		    			msg : '正在加载表格数据,请稍等...'
		    		}
		    	});
		    	/**
		    	 * 添加双击监听事件，跳转至callreport页面
		    	 */
		    	_this.oCallReportQueryGrid.addListener("rowdblclick",function(){
		    		var resId = '113494'; //页面功能资源号，CNT_MENU,ID可通过页面右键菜单获取 
		    		var taskMgr = Wlj?Wlj.TaskMgr:undefined; 	
		    		var p = parent; 		
		    		for(var i=0;i<10 && !taskMgr;i++){ 		
		    		    p = p.parent; 		
		    		    taskMgr = p.Wlj?p.Wlj.TaskMgr:undefined; 		
		    		} 		
		    		if(taskMgr.getTask('task_'+resId)){ 		
		    		    taskMgr.getTask('task_'+resId).close(); 		
		    		} 		
		    		parent._APP.taskBar.openWindow({ 		
		    		    name : '拜访信息', 		//所跳转页面的名称
		    		    action : basepath+ '/contents/pages/wlj/custmanager/potentialMkt/mktVisitInfo.js?KEY_WORD_NAME='+102, 		
		    		    resId : resId, 		
		    		    id : 'task_'+resId 
		    		});
		    	});

		    	_this.oCallReportQueryWindow=new Ext.Window({
		    		title : 'CallReport查询',
		    		closable : true,
		    		resizable : true,
		    		height:435,
		    		width:1013,
		    		draggable : true,
		    		closeAction : 'hide',
		    		modal : true, // 模态窗口 
		    		border : false,
		    		closable : true,
		    		layout : 'border',
		    		listeners : {
		    			'show':function(){		    				
							_this.oCallReportQueryForm.form.reset();
							_this.oCallReportQueryStore.removeAll();
							if(_this.autoLoadFlag)
								_this.oCallReportQueryStore.load({//如果自动加载，需要对数据进行分页
									params : {
										start : 0,
										limit : _this.oCallReportQueryBbar.pageSize
									}
								});		
		    			}  
		    		},
		    		items : [_this.oCallReportQueryForm,_this.oCallReportQueryGrid],
		    		buttonAlign:'center',
		    		buttons:[{
		    			text:'确定',
		    			handler:function(){
		    				var sTime='';
		    				var checkedNodes = _this.oCallReportQueryGrid.getSelectionModel().selections.items;
		    				if(!(_this.oCallReportQueryGrid.getSelectionModel().selections==null)){
		    					if(oThisSearchField.hiddenField){
//			    						checkedNodes = _this.oCallReportQueryGrid.getSelectionModel().selections.items;
		    						if(oThisSearchField.singleSelected&&checkedNodes.length>1){
		    							Ext.Msg.alert('提示', '您只能选择一条CallReport');
		    							return ;
		    						}
									for(var i=0;i<checkedNodes.length;i++){
										if(i==0){
											sTime=checkedNodes[i].data.CALL_TIME;
											oThisSearchField.hiddenField.setValue(checkedNodes[i].data.CUST_ID);
										}else{
											sTime=sTime+','+checkedNodes[i].data.CALL_TIME;
											oThisSearchField.hiddenField.setValue(_this.hiddenField.value+','+checkedNodes[i].data.CUST_ID);
										}
									}
									oThisSearchField.setRawValue(sTime);	
								
									if(checkedNodes.length==1){//如果单选，则设置该客户相应的附属属性
										oThisSearchField.callTime=checkedNodes[0].data.CALL_TIME;
										oThisSearchField.visitType=checkedNodes[0].data.VISIT_TYPE;
										oThisSearchField.resCustSource=checkedNodes[0].data.RES_CUSTSOURCE;
									}
		    					}

		    					if(_this.oCallReportQueryGrid.getSelectionModel().selections.length==0){//如果未选择任何客户，清空隐藏域中的客户号 
		    						oThisSearchField.hiddenField.setValue("");
		    					}	
		    					
		    				}
							if (typeof oThisSearchField.callback == 'function') {
								oThisSearchField.callback(oThisSearchField,checkedNodes);
							}
							_this.oCallReportQueryWindow.close();
		    			}
		    	},{
		    		text: '取消',
		    		handler:function(){
						_this.oCallReportQueryWindow.close();
		    		}
		    	}]
		    });
	
	var chooseCustSource=function(){};//
	_this.oCallReportQueryWindow.show();
    return;
    }			
});
/**
 * 右下角显示提示信息
 * @param {} msg 提示信息
 * @param {} error
 * @param {} hideDelay
 */
var showMsgNotification = function(msg, hideDelay, error) {
	new Ext.ux.Notification( {
		iconCls : 'ico-g-51',
		title : error ? '<font color=red>错误</font>' : '<font color=red>提示</font>',
		html : "<br><font style='font-weight:bold;' color=red text-align=left>" + msg
				+ '</font><br><br>',
		autoDestroy : true,
		plain : false,
		shadow : false,
		draggable : false,
		hideDelay : hideDelay || (error ? 30000 : 30000),
		width : 400
	}).show(document);
};
Ext.reg('callreportquerysme',Com.yucheng.bcrm.common.CallReportQueryFieldSME);








