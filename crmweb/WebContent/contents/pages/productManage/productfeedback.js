 
//反馈信息的Grid
	
	var feedbackColumns=new Ext.grid.ColumnModel([
           new Ext.grid.RowNumberer(),
          {header :'反馈内容',dataIndex:'feedbackCont',id:"feedbackCont",sortable : true,width:280},
          {header :'反馈用户',dataIndex:'feedbackUser',id:"feedbackUser",sortable : true,width:125},
          {header :'反馈日期',dataIndex:'feedbackDate',id:"feedbackDate",sortable : true,width:132}
          
	      ]);
	var feedbackRecord=new Ext.data.Record.create([	
                   	{name:'feedbackCont',mapping:'FEEDBACK_CONT'},
                   	{name:'feedbackDate',mapping:'FEEDBACK_DATE'},
                   	{name:'feedbackUser',mapping:'FEEDBACK_USER'},
                   	{name:'feedbackId',mapping:'FEEDBACK_ID'},
                   	{name:'productId',mapping:'PRODUCT_ID'}
	                   ]);
	var feedbackReader=new Ext.data.JsonReader({//读取json数据的panel
			totalProperty:'json.count',
			root:'json.data'
		},feedbackRecord);
	var feedbackStore =new Ext.data.Store(
			{
				proxy:new Ext.data.HttpProxy({
				url:basepath+'/productfeedback.json',
				method:'GET'
				}),
				reader:feedbackReader
			});
	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
		fields : [ 'value', 'text' ],
		data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
		[ 50, '50条/页' ],[ 100, '100条/页' ]  ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '100',
		resizable : true,
		width : 85
});
	// 改变每页显示条数reload数据
	pagesize_combo.on("select", function(comboBox) {
		bbar.pageSize = parseInt(pagesize_combo.getValue()),
		feedbackStore.reload({
			params : {
				start : 0,
				limit : parseInt(pagesize_combo.getValue())
			}
		});
	});
//分页工具栏
var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : feedbackStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
}); 

	//产品反馈信息新建的panel
	var FeedbackAddPanel = new Ext.form.FormPanel({
		
		labelWidth:60,
		region : 'center',
		autoScroll : true,
		frame:true,
		items:[
		{
			xtype:'textfield',
			fieldLabel:'反馈人',
			anchor:'90%',
			readOnly:true,
			value:__userName,
			id:'feedbackUser1',
			name:'feedbackUser'
		},
		{
			xtype:'datefield',
			fieldLabel:'反馈日期', 
			anchor:'90%',
			format:'Y年m月d日',
			readOnly:true,
			id:'feedbackDate1',
			value:new Date(),
			name:'feedbackDate'
		},
		{
			xtype:'textarea',
			fieldLabel:'反馈内容',
			anchor:'90%',
			height:150,
			id:'feedbackCont1',
			name:'feedbackCont'
		},{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth:.5,
				items:[{
					xtype:'textfield',
					fieldLabel:'产品ID', 
					anchor:'90%',
					readOnly:true,
					hidden:true,
					id:'productId3',
					name:'productId'
				}]
			},{
				layout:'form',
				columnWidth:.5,
				items:[{
					xtype:'textfield',
					fieldLabel:'反馈ID', 
					anchor:'90%',
					hidden:true,
					readOnly:true,
					id:'feedbackId1',
					name:'feedbackId'
				}]
			}]
		}		
		],
		buttonAlign:'center',
		buttons:[
		 		{ 
		 			text:'保  存',
		 			handler:function()
		 			{
		 			debugger;
		 			if(!FeedbackAddPanel.getForm().isValid()) { 
		 				return false;
		 			}
		 			
		 			Ext.Ajax.request({
		 				url : basepath + '/productfeedback.json',
		 				method : 'POST',
		 				params : FeedbackAddPanel.getForm().getFieldValues(),
		 				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		 				success : function() {
		 					Ext.Msg.alert('提示', '操作成功');
		 					feedbackStore.reload();
		 				},
		 				failure : function(response) {
		 					var resultArray = Ext.util.JSON.decode(response.status);
		 					 if(resultArray == 403) {
		 				           Ext.Msg.alert('提示', response.responseText);
		 					 }else{
		 					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
		 				}
		 				}
		 			});
		 			Feedbackaddwind.hide();
		 			Ext.getCmp("feedbackCont1").setValue();
		 			}
		 		},
		 		{
		 			text:'取  消',
		 			handler:function()
		 			{
		 			Feedbackaddwind.hide();
		 			}
		 		}
		 		]	
		
	});

	//产品反馈信息修改的form
	var FeedbackeditPanel=new Ext.form.FormPanel({
		labelWidth:60,
		region : 'center',
		autoScroll : true,
		frame:true,
		items:[
		{
			xtype:'textfield',
			fieldLabel:'反馈人',
			anchor:'90%',
			readOnly:true,
			id:'feedbackUser2',
			name:'feedbackUser'
		},
		{
			xtype:'datefield',
			fieldLabel:'反馈日期', 
			anchor:'90%',
			format:'Y年m月d日',
			readOnly:true,
			id:'feedbackDate2',
			name:'feedbackDate'
		},
		{
			xtype:'textarea',
			fieldLabel:'反馈内容',
			anchor:'90%',
			height:150,
			id:'feedbackCont2',
			name:'feedbackCont'
		},{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth:.5,
				items:[{
					xtype:'textfield',
					fieldLabel:'产品ID', 
					anchor:'90%',
					readOnly:true,
					hidden:true,
					id:'productId1',
					name:'productId'
				}]
			},{
				layout:'form',
				columnWidth:.5,
				items:[{
					xtype:'textfield',
					fieldLabel:'反馈ID', 
					anchor:'90%',
					hidden:true,
					readOnly:true,
					id:'feedbackId2',
					name:'feedbackId'
				}]
			}]
		}				
		],
		buttonAlign:'center',
		buttons:[
		
					{

						text : '保  存',
						handler : function() {
							if(!FeedbackeditPanel.getForm().isValid()) { 
								return false;
							}
							Ext.Ajax.request({
								url : basepath + '/productfeedback.json',
								method : 'POST',
								params : FeedbackeditPanel.getForm().getFieldValues(),
								waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
								success : function() {
									Ext.Msg.alert('提示', '操作成功');
									feedbackStore.reload();
								},
								failure : function(response) {
									var resultArray = Ext.util.JSON.decode(response.status);
									if(resultArray == 403) {
								           Ext.Msg.alert('提示', response.responseText);
									} else{
									Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
									}
									}
							});
							editerfeedbackwind.hide();
							FeedbackeditPanel.getForm().reset();
						}

					}, {
						text : '取  消',
						handler : function() {
						editerfeedbackwind.hide();
						}
					}
		]	
	});
	
	
	//反馈信息详情form
	var detailsPanel=new Ext.form.FormPanel({
		labelWidth:60,
		region : 'center',
		autoScroll : true,
		frame:true,
		items:[{
			layout:'column',
			items:[{
				layout:'form',
				columnWidth:.5,
				items:[{
					xtype:'textfield',
					fieldLabel:'反馈人',
					anchor:'90%',
					readOnly:true,
					id:'feedbackUser3',
					name:'feedbackUser'
				},{
					xtype:'textfield',
					fieldLabel:'反馈ID',
					anchor:'90%',
					readOnly:true,
					id:'feedbackId3',
					name:'feedbackId'
				}]
			},{
				layout:'form',
				columnWidth:.5,
				items:[{
					xtype:'datefield',
					fieldLabel:'反馈时间',
					anchor:'90%',
					readOnly:true,
					id:'feedbackDate3',
					name:'feedbackDate'
				},{
					xtype:'textfield',
					fieldLabel:'产品ID',
					anchor:'90%',
					readOnly:true,
					id:'productId2',
					name:'productId'
				}]
			}]
		},{
			layout:'column',
			items:[{
				layout:'form',
				items:[{
					xtype:'textarea',
					fieldLabel:'反馈内容',
					anchor:'95%',
					height:150,
					readOnly:true,
					id:'feedbackCont3',
					name:'feedbackCont'
				}]
			}]
		}]
	});
	
	//产品反馈新建 的window
	var Feedbackaddwind = new Ext.Window(
	{
		title:'新增反馈信息',
		width:400,
		height:300,
		closeAction:'hide',
		closable:true,
		maximizable:true,
		buttonAlign:'right',
		border:false,
		layout:'fit',
		draggable:true,
		collapsible:true,
		titleCollapse:true,
		items:[FeedbackAddPanel]
	}
	);
	//产品反馈信息修改的window
	var editerfeedbackwind=new Ext.Window({
		title:'修改反馈信息',
		width:400,
		height:300,
		closeAction:'hide',
		closable:true,
		maximizable:true,
		buttonAlign:'right',
		border:false,
		layout:'fit',
		draggable:true,
		collapsible:true,
		titleCollapse:true,
		items:[FeedbackeditPanel]
		
	});
	//反馈信息详情window
	var detailswindow=new Ext.Window({
		title:'反馈信息详情',
		width:600,
		height:300,
		closeAction:'hide',
		closable:true,
		maximizable:true,
		buttonAlign:'center',
		border:false,
		layout:'fit',
		draggable:true,
		collapsible:true,
		titleCollapse:true,
		items:[detailsPanel],
		buttons:[{
			text:'返回',
			handler:function(){
			detailswindow.hide();
		}
		}]
	});
	function details(){
		detailswindow.show();
	}
	//新建的function
	function addfeedback(){
		Feedbackaddwind.show();
	}
	//修改的function
	function editerfeedback(){
		var record = feedbackgrid.getSelectionModel().getSelected();
		if(record==null || record=="undefined"){
				Ext.MessageBox.alert('提示','请选择一条记录');
				return;
			} 
		editerfeedbackwind.show();
	}
      //表格panel
	var feedbackgrid=new Ext.grid.GridPanel({
		id:'feedbackgrid',
		frame:true,
		store:feedbackStore,
		cm:feedbackColumns,
		height:340,
		loadMask:true,
		bbar:bbar,
		tbar:[{
			text:'新建',
			iconCls:'addIconCss',
			handler:function(){
			addfeedback();
		}
		},'-',{
			text:'修改',
			iconCls:'editIconCss',
			handler:function(){
			var record = feedbackgrid.getSelectionModel().getSelected();
			if (record==null || record=="undefined") {
				Ext.Msg.alert('提示','请选择一条记录!');
			} else {
				var tempCreater = feedbackgrid.getSelectionModel().getSelections()[0].data.feedbackUser;
				if(tempCreater!=__userName){
					Ext.Msg.alert('系统提示','只能修改本人创建的反馈信息！');
					return false;
				}
				FeedbackeditPanel.getForm().loadRecord(record);
				editerfeedback();
			}
			
		}
		},'-',{
			text:'删除',
			iconCls:'deleteIconCss',
			handler:function(){
			var selectLength = feedbackgrid.getSelectionModel().getSelections().length;
			
	  if (selectLength < 1) {
		Ext.Msg.alert('提示','请选择需要删除的记录!');
	   }
	  else {
		  var tempSign = feedbackgrid.getSelectionModel().getSelections()[0].data.feedbackUser;
		  if(tempSign!=__userName){
				Ext.Msg.alert('系统提示','只能删除本人创建的反馈信息！');
				return false;
			}
	 	    Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
			if(buttonId.toLowerCase() == "no"){
					return;
				}      
			var selectRe;
			
			var idStr = '';
			
				selectRe = feedbackgrid.getSelectionModel().getSelections()[0].data.feedbackId;;
				idStr = selectRe;
			if(idStr=='undefind'||idStr==null){
				Ext.Msg.alert("无效的feedbackId");
			}
			Ext.Ajax.request({
						url : basepath
								+ '/productfeedback!destroy.json?idStr='
								+ idStr,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
							feedbackStore.reload();
						},
						
						failure : function(response) {
							var resultArray = Ext.util.JSON.decode(response.status);
							if(resultArray == 403) {
							window.location = basepath + '/403.jsp';
							} else {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
							}
							feedbackStore.reload();
						}
					});

		})
		;
	 }
		}
		}],
		loadMask:{
            msg : '正在加载数据,请稍等...'
        }
	});
	//双击弹出详情窗口
	feedbackgrid.on('rowdblclick', function(feedbackgrid, rowIndex, event) {

		var selectRe = feedbackgrid.getSelectionModel().getSelections()[0];

		if (selectRe == null
				|| selectRe == "undefined") {
			Ext.Msg.alert('提示','请选择一条记录!');
		} else {
			
			detailsPanel.getForm().loadRecord(selectRe);
			details();
		}
	});
//产品信息的form
	var feedbackpanel=new Ext.form.FormPanel({
		frame : true,
		region : 'center',
		autoScroll : true,
		height:500,
      	autoScroll : true,
		split:true,
		items:[feedbackgrid],
		buttonAlign:'center',
		 buttons:[{
			 text:'返回',
			 handler:function(){
			 feedbackwind.hide();
		 }
		 }]
	});
	
 //产品信息反馈弹出窗口window
	var feedbackwind=new Ext.Window({
		id:'feedbackwind',
		title : '产品信息反馈',
		plain : true,
		layout : 'fit',
		width : 600,
		height : 425,
		resizable : true,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : true,
		collapsible : true,
		titleCollapse : true,
		buttonAlign : 'center',
		border : false,
		items:[
		       	feedbackpanel
		       ]
		 
	});
	