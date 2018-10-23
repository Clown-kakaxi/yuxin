/**
 * 面谈营销界面
 * @author luyy
 */
//this.phoneNum = '';//保存当前的电话号码
this.CustdataInfo ={
	custId:false,//客户id
	custZhName:false,//客户名称
	custTyp:false,
	ContactInfo:false,
	bizNo:false
};
var recordStore = new Ext.data.Store();
/**
 * flex 录音上传之后回调此函数
 * @param {} filepath flex录音文件保存的路径
 */
var uploadRecorder_callback = function(filepath){
	Ext.Ajax.request({
		url: basepath + '/interviewMain.json',
		method: 'POST',
		waitMsg: '正在保存，请稍等...',
		params:{
			custId : CustdataInfo.custId,
			physicalAddr : filepath,
			bizNo : CustdataInfo.bizNo
		},
		success: function(){
			recordStore.load();
			//Ext.Msg.alert('提示','操作成功!');
		},
		failure: function(){
			Ext.Msg.alert('提示','操作失败!');
		}
	});
};

//性别
var sexstore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=DEM0100005'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//类型
var typstore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000080'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var induCodeStore = new Ext.data.Store({//所属单位行业的store
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=PAR2100001'
	}),	
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var entScaleStore = new Ext.data.Store({//企业规模store
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=DEM0200004'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var telStore = new Ext.data.Store({//通话类型
	restful:true,   
	autoLoad :true,
	sortInfo: {
    	field: 'key',
    	direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=TEL_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var bsiStore = new Ext.data.Store({//业务类型
	restful:true,   
	autoLoad :true,
	sortInfo: {
    	field: 'key',
    	direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=BIS_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var followStore = new Ext.data.Store({//后续处理
	restful:true,   
	autoLoad :true,
	sortInfo: {
    	field: 'key',
    	direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=FOLLOW_DO'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

//进入本页面的初始化过程
function interview_initdata(CustdataInfo,phoneId){
	// 对私客户基本信息
	var ncFormIndiv = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		//frame : true, // 是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		items : [{
			items : [{
				layout : 'column',
				items : [{
					columnWidth : .25,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						fieldLabel : '客户姓名',
						name : 'custZhName',
						labelStyle : 'text-align:right;',
						anchor : '90%'
					}, {
						xtype : 'textfield',
						fieldLabel : '客户号',
						labelStyle : 'text-align:right;',
						name : 'custId',
						anchor : '90%'
					}, {
						xtype : 'textfield',
						fieldLabel : '证件号码',
						labelStyle : 'text-align:right;',
						name : 'certNum',
						anchor : '90%'
					}]
				}, {
					columnWidth : .25,
					layout : 'form',
					items : [new Ext.form.ComboBox({
						hiddenName : 'sex',
						fieldLabel : '性别',
						labelStyle : 'text-align:right;',
						triggerAction : 'all',
						name : 'sex',
						store : sexstore,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						emptyText : '请选择',
						resizable : true,
						anchor : '90%'
					}), new Ext.form.ComboBox({
						hiddenName : 'custTyp',
						fieldLabel : '客户类型',
						labelStyle : 'text-align:right;',
						triggerAction : 'all',
						name : 'custTyp',
						store : typstore,
						displayField : 'value',
						valueField : 'key',
						mode : 'local',
						forceSelection : true,
						typeAhead : true,
						emptyText : '请选择',
						resizable : true,
						anchor : '90%'
					}), {
						xtype : 'textfield',
						fieldLabel : '单位名称',
						labelStyle : 'text-align:right;',
						name : 'workUnit',
						anchor : '90%'
					}]
				}, {
					columnWidth : .25,
					layout : 'form',
					items : [{
						xtype : 'datefield',
						fieldLabel : '出生日期',
						name : 'birthday',
						labelStyle : 'text-align:right;',
						format : 'Y-m-d',
						anchor : '90%'
					}, {
						xtype : 'combo',
						store : induCodeStore,
						resizable : true,
						name : 'induCode',
						hiddenName : 'induCode',
						fieldLabel : '所属单位行业',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						editable : false,
						typeAhead : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						labelStyle : 'text-align:right;',
						selectOnFocus : true,
						anchor : '90%'
					}, {
						xtype : 'textfield',
						fieldLabel : '备注',
						labelStyle : 'text-align:right;',
						name : 'remark',
						anchor : '90%'
					}]
				}]
			}]
		}]
	});
	
	// 对公客户基本信息
	var ncFormCom = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		//frame : true, // 是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		items : [{
			items : [{
				layout : 'column',
				items : [{
					columnWidth : .25,
					layout : 'form',
					items : [
						{xtype : 'textfield',fieldLabel : '客户姓名',labelStyle : 'text-align:right;',name : 'custZhName',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : '客户号',labelStyle : 'text-align:right;',name : 'custId',anchor : '90%'},
						{xtype : 'textfield',fieldLabel : '客户法人姓名',labelStyle : 'text-align:right;',name : 'entMaster',anchor : '90%'}
					]
				}, {
					columnWidth : .25,
					layout : 'form',
					items : [
						{xtype : 'textfield',fieldLabel : '资产总额',labelStyle : 'text-align:right;',name : 'entAssets',anchor : '90%'},
						new Ext.form.ComboBox({
							hiddenName : 'custTyp',
							fieldLabel : '客户类型',
							labelStyle : 'text-align:right;',
							triggerAction : 'all',
							name : 'custTyp',
							store : typstore,
							displayField : 'value',
							valueField : 'key',
							mode : 'local',
							forceSelection : true,
							typeAhead : true,
							emptyText : '请选择',
							resizable : true,
							anchor : '90%'
						}),
						{
							xtype : 'combo',
							store : entScaleStore,
							resizable : true,
							name : 'entScale',
							hiddenName : 'entScale',
							fieldLabel : '企业规模',
							valueField : 'key',
							displayField : 'value',
							mode : 'local',
							editable : false,
							typeAhead : true,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							labelStyle : 'text-align:right;',
							selectOnFocus : true,
							anchor : '90%'
						}]
				}, {
					columnWidth : .25,
					layout : 'form',
					items : [{
						xtype : 'textfield',
						fieldLabel : '注册地址',
						name : 'entRegAddr',
						labelStyle : 'text-align:right;',
						anchor : '90%'
					}, {
						xtype : 'textfield',
						fieldLabel : '经营范围',
						labelStyle : 'text-align:right;',
						name : 'busiRage',
						anchor : '90%'
					}, {
						xtype : 'textfield',
						fieldLabel : '备注',
						labelStyle : 'text-align:right;',
						name : 'remark',
						anchor : '90%'
					}]
				}]
			}]
		}]
	});
	
	
	
	// 对私客户信息
	var storeIndiv = new Ext.data.Store( {
		  restful:true,
		  proxy : new Ext.data.HttpProxy({url:basepath+'/custIndiv.json'
		  }),
		  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'custZhName', mapping: 'CUST_ZH_NAME'},
		      {name: 'custId', mapping: 'CUST_ID'},
		      {name: 'certNum', mapping: 'CERT_NUM'},
		      {name: 'sex', mapping: 'SEX'},
		      {name: 'custTyp', mapping: 'CUST_TYP'},
		      {name: 'workUnit', mapping: 'WORK_UNIT'},
		      {name: 'birthday', mapping: 'BIRTHDAY'},
		      {name: 'induCode', mapping: 'INDU_CODE'},
		      {name: 'remark', mapping: 'REMARK'}
		      
		      ])
	});
	
	// 对公客户信息
	var storeCom = new Ext.data.Store( {
		  restful:true,
		  proxy : new Ext.data.HttpProxy({url:basepath+'/custCom.json'
		  }),
		  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'custZhName', mapping: 'CUST_ZH_NAME'},
		      {name: 'custId', mapping: 'CUST_ID'},
		      {name: 'entMaster', mapping: 'ENT_MASTER'},
		      {name: 'entAssets', mapping: 'ENT_ASSETS'},
		      {name: 'custTyp', mapping: 'CUST_TYP'},
		      {name: 'entScale', mapping: 'ENT_SCALE'},
		      {name: 'entRegAddr', mapping: 'ENT_REG_ADDR'},
		      {name: 'busiRage', mapping: 'BUSIRAGE'},
		      {name: 'remark', mapping: 'REMARK'}
		      ])
	});
	
	//通话记录部分**********
	recordStore = new Ext.data.Store( {
		  restful:true,
		  proxy : new Ext.data.HttpProxy({
		  	url:basepath+'/interviewMain.json'
		  }),
		  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'id', mapping: 'ID'},
		      {name: 'userId', mapping: 'USER_ID'},
		      {name: 'userName', mapping: 'USER_NAME'},
		      {name: 'interviewDate', mapping: 'INTERVIEW_DATE'},
		      {name: 'bisType', mapping: 'BIS_TYPE'},
		      {name: 'BIS_TYPE_ORA'},
		      {name: 'TEL_TYPE_ORA'},
		      {name: 'FOLLOW_DO_ORA'},
		      {name: 'telType', mapping: 'TEL_TYPE'},
		      {name: 'followDo', mapping: 'FOLLOW_DO'},
		      {name:'physicalAddr',mapping:'PHYSICAL_ADDR'}
		      ])
	});
	
	 var recordcm =  new Ext.grid.ColumnModel([
        {header : '流水号',dataIndex : 'id',sortable : true,width : 150},
        {header : '面谈日期',dataIndex : 'interviewDate',sortable : true,width : 150},
        {header : '业务类型',dataIndex : 'BIS_TYPE_ORA',sortable : true,width : 150},
        {header : '面谈类型',dataIndex : 'TEL_TYPE_ORA',sortable : true,width : 150},
        {header : '后续处理',dataIndex : 'FOLLOW_DO_ORA',sortable : true,width : 150},
        {header : '通话客户经理',dataIndex : 'userName',sortable : true,width : 150},
        //{header:'物理地址',dataIndex:'physicalAddr',hidden:true},
        {header : '操作',dataIndex : '',sortable : true,width : 150,renderer:function(v,p,record){
        	var nl = record.data.physicalAddr;
        	if(nl==null||nl=='')
        		return  '<font color="red"><b>暂无录音</b></font>';
        	else
        		return '<font color="green"><b>双击播放录音</b></font>'; 
        	}}
		]);
	 var phonePagesize_combo = new Ext.form.ComboBox({
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
	 var phoneNumber = parseInt(phonePagesize_combo.getValue());
	 phonePagesize_combo.on("select", function(comboBox) {
		 phoneBar.pageSize = parseInt(phonePagesize_combo.getValue()),
		 recordStore.load({
			params : {
				start : 0,
				limit : parseInt(phonePagesize_combo.getValue())
			}
		});
	});
	 var phoneBar = new Ext.PagingToolbar({
	     pageSize : phoneNumber,
	     store : recordStore,
	     displayInfo : true,
	     displayMsg : '显示{0}条到{1}条,共{2}条',
	     emptyMsg : "没有符合条件的记录",
	     items : ['-', '&nbsp;&nbsp;', phonePagesize_combo]
	 });
	 var recordGrid = new Ext.grid.GridPanel({
		 	layout:'fit',
			frame : true,
			autoScroll : true,
			store : recordStore, // 数据存储
			stripeRows : true, // 斑马线
			cm : recordcm, // 列模型
			bbar:phoneBar,
			viewConfig:{
				   forceFit:false,
				   autoScroll:true
				},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
			});
	 
	//双击播放录音
	 recordGrid.on('rowdblclick', function(recordGrid, rowIndex, event) {
			var selectRe = recordGrid.getSelectionModel().getSelections()[0];
			if (selectRe == null|| selectRe == "undefined") {
				Ext.Msg.alert('提示','请选择一条记录!');
			} else if(selectRe.data.physicalAddr==''||selectRe.data.physicalAddr==null){
				Ext.Msg.alert('提示','该次通话没有录音文件!');
			}else {
				var playerWindow = new Ext.Window({
					title : '播放录音',
					closeAction : 'close',
					constrain : true,
					modal : true,
					width : 325,
					height : 160,
					draggable : true,
					layout : 'fit',
					html:'<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" id="Mp3WavPlayer" width="100%" height="100%" codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">'
						+'<param name="movie" value="'+basepath+'/flex-debug/Mp3WavPlayer.swf" />'
						+'<param name="quality" value="high" />'
						+'<param name="wmode" value="opaque" />'
						+'<param name="bgcolor" value="#ffffff" />'
						+'<param name="allowScriptAccess" value="sameDomain" />'
						+'<param name="flashVars" value="basepath='+basepath+'&busiId='+basepath+'/flexHandlerAction!playRecorder.json?filepath='+selectRe.data.physicalAddr+'"/>'
						+'<embed src="'+basepath+'/flex-debug/Mp3WavPlayer.swf" quality="high" bgcolor="#ffffff"' 
						+'	width="100%" height="100%" name="Mp3WavPlayer" align="middle" flashVars="basepath='+basepath+'&busiId='+basepath+'/flexHandlerAction!playRecorder.json?filepath='+selectRe.data.physicalAddr+'" '
						+'	play="true" loop="false" quality="high" allowScriptAccess="sameDomain" type="application/x-shockwave-flash"'
						+'	pluginspage="http://www.adobe.com/go/getflashplayer">'
						+'</embed>'
						+'</object>'
				});
				playerWindow.show();
			}
		});
	
	//通话记录部分**********
	
		//通话内容panel
		var recordPanel = new  Ext.form.FormPanel({
			labelWidth : 90,                       // 标签宽度
			frame : true,                          // 是否渲染表单面板背景色
			labelAlign : 'middle',                 // 标签对齐方式
			items: [{
		        items :[
		            	{  
		            		 layout:'column',
		                     items:[{
		                         columnWidth:.33,
		                         layout: 'form',
		                         items: [new Ext.form.ComboBox({
		    							hiddenName : 'telType',
		    							fieldLabel : '面谈类型',
		    							labelStyle: 'text-align:right;',
		    							triggerAction : 'all',
		    							name:'telType',
		    							store : telStore,
		    							displayField : 'value',
		    							valueField : 'key',
		    							mode : 'local',
		    							forceSelection : true,
		    							allowBlank:false,
		    							typeAhead : true,
		    							emptyText:'请选择',
		    							resizable : true,
		    							anchor : '100%',
		    							listeners:{
		    								'select':function(){
		    									bsiStore.proxy.conn.url = basepath + '/lookup.json?name='+this.getValue();;
		    									bsiStore.load();
		    								}
		    							}
		    						})]
		                     },{
		                         columnWidth:.33,
		                         layout: 'form',
		                         items: [new Ext.form.ComboBox({
		    							hiddenName : 'bisType',
		    							fieldLabel : '业务类型',
		    							labelStyle: 'text-align:right;',
		    							triggerAction : 'all',
		    							name:'bisType',
		    							store : bsiStore,
		    							displayField : 'value',
		    							valueField : 'key',
		    							mode : 'local',
		    							forceSelection : true,
		    							typeAhead : true,
		    							emptyText:'请选择',
		    							allowBlank:false,
		    							resizable : true,
		    							anchor : '100%'
		    						})]}, {
		                             columnWidth:.34,
		                             layout: 'form',
		                             items: [new Ext.form.ComboBox({
			    							hiddenName : 'followDo',
			    							fieldLabel : '后续处理',
			    							labelStyle: 'text-align:right;',
			    							triggerAction : 'all',
			    							name:'followDo',
			    							store : followStore,
			    							displayField : 'value',
			    							valueField : 'key',
			    							mode : 'local',
			    							forceSelection : true,
			    							allowBlank:false,
			    							typeAhead : true,
			    							emptyText:'请选择',
			    							resizable : true,
			    							anchor : '100%'
			    						})]},
			    						{
				                             columnWidth:1,
				                             layout: 'form',
				                             items: [
				                             	{xtype:'textarea',name:'remark',fieldLabel : '业务处理备注',anchor : '100%'},
				                             	{xtype:'textfield',name:'bizNo',hidden:true}
				                             ]
				                        }
		            ]}]} ],
		            buttonAlign:'center',
		        	buttons:[{
		        		text:'保存',
		        		handler: function(){
		   	        	 if (!recordPanel.getForm().isValid()) {
		   	        		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
		   	        		 return false;
		   	        	 }
		   	        	recordPanel.getForm().findField('bizNo').setValue(CustdataInfo.bizNo);
		   	        	var interviewJson = Ext.encode(recordPanel.getForm().getValues(false));
		   	        	Ext.Ajax.request({
							url : basepath + '/interviewMain!updateInterview.json',
							params : {
								interviewJson : interviewJson
							},
							method : 'POST',
							form : recordPanel.getForm().id,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								recordStore.load();
								Ext.Msg.alert('提示', '保存成功');
							},
							failure : function(response) {
								Ext.Msg.alert('提示', '更新面谈记录失败');
							}
						});
		   	        	 
		        		}
		        	}]
		    });
		//客户视图链接
		var url = basepath+"/contents/pages/customer/customerManager/mktPhone/CommonCustomerView.jsp?resId="+__resId
		+"&custId="+CustdataInfo.custId
		+"&custName="+CustdataInfo.custZhName
		+"&custTyp="+CustdataInfo.custTyp;
	
	var tabmain = new Ext.TabPanel({
	id:'tabmain',
	activeTab: 3,
	frame:true,
	autoScroll:true,
	defaults:{autoHeight: true},
	items:[
	    { title: '客户视图',items:[{
					columnWidth : .55,
					items : [{
					region: 'center',
					width:'100%',
					collapsible: false,
					items:{
						collapsible: false,
						height:350,
						html:'<iframe id="reporter-iframes" src='+url+' width="100%" height="100%" name="main"  frameborder="0" scrolling="auto"></iframe>'
					}
				}]}]},
	    { title: '面谈记录',items:[{
					columnWidth : .55,
					items : [{
					layout:'fit',
					region: 'center',
					width:'100%',
					height:350,
					collapsible: false,
					items:[recordGrid]
				}]}]},
	    { title: '推荐产品',items:[{
			columnWidth : .55,
			items : [{
			region: 'east',
			width:'100%',
			collapsible: false,
			height:340,
			html:'<iframe id="reporter-iframes" src="'+basepath+'/contents/pages/productManage/productInfoList.jsp" width="100%" height="100%" name="main"  frameborder="0" scrolling="auto"></iframe>'
		}]}]},
	    { title: '面谈概要',items:[{
					columnWidth : .55,
					items : [{
					region: 'east',
					width:'100%',
					collapsible: false,
					height:350,
					items:[recordPanel]
				}]}]},
	    { title: '日程安排',items:[{
					columnWidth : .55,
					items : [{
					region: 'east',
					width:'100%',
					collapsible: false,
					height:350,
					html:'<iframe id="reporter-iframes" src="'+basepath+'/contents/pages/workSpace/calendarManager/schedulePlanIndex.jsp" width="100%" height="100%" name="main"  frameborder="0" scrolling="auto"></iframe>'
				}]}]}
	],
	listeners:{
		'tabchange':function(){
			if (tabmain.getActiveTab().title == '通话记录') {
				recordStore.reload();
			}
		}
	}
	});
	
	/**
	 * 录音Panel,调用flex swf
	 */
	var recordingPanel = new Ext.Panel({
		html:'<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" id="recording" width="220" height="140" codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">'
			+'<param name="movie" value="'+basepath+'/flex-debug/recording.swf" />'
			+'<param name="quality" value="high" />'
			+'<param name="wmode" value="opaque" />'
			+'<param name="bgcolor" value="#ffffff" />'
			+'<param name="allowScriptAccess" value="sameDomain" />'
			+'<param name="flashVars" value="basepath='+basepath+'&busiId='+basepath+'/flexHandlerServlet?busiId=2222"/>'
			+'<embed src="'+basepath+'/flex-debug/recording.swf" quality="high" bgcolor="#ffffff"'
			+'	width="220" height="140" name="recording" align="middle" flashVars="basepath='+basepath+'&busiId='+basepath+'/flexHandlerServlet?busiId=2222"' 
			+'	play="true" loop="false" quality="high" allowScriptAccess="sameDomain" type="application/x-shockwave-flash"'
			+'	pluginspage="http://www.adobe.com/go/getflashplayer">'
			+'</embed>'
			+'</object>'
	});
	
	// 布局模型
	var interviewWindow = new Ext.Window({
		title : '面谈营销',
		closeAction : 'hide',
		constrain : true,
		maximized:true, //默认最大化
		modal : true,
		width : 1000,
		height : 450,
		draggable : true,
		layout : 'fit',
		items : [{
			layout : 'border',
			items : [{
				region : 'north',
				id : 'north-panels',
				height : 150,
				hidden : false,
				layout : "column",
				items : [{
					columnWidth : .7,
					frame : true,
					height : 150,
					items : [ncFormIndiv, ncFormCom]
				}, {
					columnWidth : .3,
					frame : true,
					height : 150,
					items : [recordingPanel]
					//items : [phonePanel]
				}]
			 },{
				region : 'center',
				id : 'center-panels',
				height : document.body.scrollHeight - 150,
				hidden : false,
				items : [tabmain]
			}]
		}],
		buttonAlign : 'center',
		buttons : [{
			text : '关闭',
			handler : function() {
				interviewWindow.hide();
			}
		}]
	});
	
	//关闭窗口时删除文件
	interviewWindow.on('hide', function() {
//		Ext.Ajax.request({
//			url : basepath + '/ocrmFMmTelMain!deleteFile.json',
//			params : {
//				'file':'PHONE'+phoneId+'.wav'
//			},
//			success : function(response) {
////				Ext.Msg.alert('提示', '成功');
//			},
//			failure : function(response) {
//				Ext.Msg.alert('提示', '删除临时文件失败');
//			}
//		});
	});
	interviewWindow.show();
	//数据初始化********************	
    recordStore.on('beforeload', function() {
        this.baseParams = {
                "custId":CustdataInfo.custId
        };
	});
    recordStore.reload();
	    
	if(CustdataInfo.custTyp=='1'){//对私客户
		ncFormCom.hide();
		ncFormIndiv.show();
		storeIndiv.on('beforeload', function() {
            this.baseParams = {
                    "custId":CustdataInfo.custId
            };
		});
		storeIndiv.load({
		  callback : function(){
			  if(storeIndiv.getCount()!=0){
				  ncFormIndiv.getForm().loadRecord(storeIndiv.getAt(0));
	        	}
		  }});
		
	}else if(CustdataInfo.custTyp=='2'){//对公客户
		ncFormCom.show();
		ncFormIndiv.hide();
		storeCom.on('beforeload', function() {
            this.baseParams = {
                    "custId":CustdataInfo.custId
            };
		});
		storeCom.load({
		  callback : function(){
			  if(storeCom.getCount()!=0){
				  ncFormCom.getForm().loadRecord(storeCom.getAt(0));
	        	}
		  }});
	}
	//数据初始化********************	
}
