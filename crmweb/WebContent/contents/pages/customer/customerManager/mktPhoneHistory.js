Ext.onReady(function(){
	//电话营销记录**********
	var custid =oCustInfo.cust_id;//当前用户所查看的客户的客户号
	var recordStore = new Ext.data.Store( {
		  restful:true,
		  proxy : new Ext.data.HttpProxy({url:basepath+'/ocrmFMmTelMain.json?custId='+custid
		  }),
		  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'id', mapping: 'ID'},
		      {name: 'userId', mapping: 'USER_ID'},
		      {name: 'userName', mapping: 'USER_NAME'},
		      {name: 'startTime', mapping: 'TIME_S'},
		      {name: 'endTime', mapping: 'TIME_E'},
		      {name: 'timeLong', mapping: 'TIME_LONG'},
		      {name: 'bsiTypeName', mapping: 'BIS_TYPE_NAME'},
		      {name:'content',mapping:'CONTENT'},
		      {name:'physicalAddress',mapping:'PHYSICAL_ADDRESS'},
		      {name:'FR_ID_ORA'}
		      ])
	});

	//复选框
	var sm=new Ext.grid.CheckboxSelectionModel();
	// 定义自动当前页行号
	var rowNum=new Ext.grid.RowNumberer({
		header:'No',
		width:28
	});
	 var recordcm =  new Ext.grid.ColumnModel([rowNum,sm,
	                               	        {header : '流水号',dataIndex : 'id',sortable : true,width : 130},
	                               	        {header : '开始时间',dataIndex : 'startTime',sortable : true,width : 140},
	                               	        {header : '结束时间',dataIndex : 'endTime',sortable : true,width : 140},
	                               	        {header : '录音时长',dataIndex : 'timeLong',sortable : true,width : 140},
	                               	        {header : '业务类型',dataIndex : 'bsiTypeName',sortable : true,width : 130},
	                               	        {header : '通话客户经理',dataIndex : 'userName',sortable : true,width : 130},
	                               	        {header:'physicalAddress',dataIndex:'physicalAddress',hidden:true},
	                               	        {header:'content',dataIndex:'content',hidden:true},
	                               	        {header : '播放',dataIndex : '',sortable : true,width : 130,renderer:function(v,p,record){
	                               	        	var nl = record.data.physicalAddress;
	                               	        	if(nl==null||nl=='')
	                               	        		return  '<font color="red"><b>暂无录音</b></font>';
	                               	        	else
	                               	        		return '<font color="green"><b>双击播放录音</b></font>'; 
	                               	        	}}//,
//	                               	        {header:'所属机构法人ID',dataIndex:'FR_ID_ORA',sortable : true,width : 130,hidden:(__frId==""?false:true)}
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
	 var form=new Ext.form.FormPanel({
		 frame:true,
		 frame: true,
		 autoScroll: true,
	     items:[{
	    	 layout:'column',
	    	 items:[{
					layout: 'column',
					items: [{
							columnWidth:0.5,
							layout: 'form',
							items: [
 								{
							xtype : 'textfield',
							name : 'bsiTypeName',
							disabled:true,
							labelStyle : 'text-align:right;',
							fieldLabel : '业务类型',
							anchor : '95%'
						}

								]
							},{
							columnWidth:0.5,
							layout: 'form',
							items: [
 								{
							xtype : 'textfield',
							name : 'timeLong',
							disabled:true,
							fieldLabel : '通话时长',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}
		
								]
							}]
					},{
	    		 layout:'form',
	    		 items:[{
	    			 xtype:'textarea',
	    			 fieldLabel: '*内容',
                     maxLength:200,
                     disabled:true,
                     allowBlank:false,
					 labelStyle: 'text-align:right;',
					 name: 'content',
					 anchor:'97.5%'
	    		 }]
	    	 }]
	     }]
	 });
	   var win = new Ext.Window(
		        {
		            //layout : 'fit',
		            height : 190,
		            width:600,
		            title : '<span style="font-weight:normal">过程纪要信息</span>',
		            draggable : true,//是否可以拖动
		            closable : true,// 是否可关闭
		            modal : true,
		            autoScroll:true,
		            closeAction : 'hide',
		            collapsible : true,// 是否可收缩
		            titleCollapse : true,
		            buttonAlign : 'center',
		            border : false,
		            constrain : true,
		            items : [form],
		            buttons : [{ // 窗口底部按钮配置
		                text : '关    闭', // 按钮文本
		                handler : function() { // 按钮响应函数
		                	form.getForm().reset();
		                	win.hide();
		                }
		            }]
		        });
	 var recordGrid = new Ext.grid.GridPanel({
		 	//layout:'fit',
		 	width : document.body.clientWidth-240,
			frame : true,
			autoScroll : true,
			store : recordStore, // 数据存储
			stripeRows : true, // 斑马线
			cm : recordcm, // 列模型
			sm:sm,
			tbar:[{
				text:'通话纪要',
				iconCls:'detailIconCss',
				handler:function(){
					 var selectLength = recordGrid.getSelectionModel()
				        .getSelections().length;
				        
				        if(selectLength > 1){
				            alert('请选择一条记录!');
				        } else{
					    var infoRecord = recordGrid.getSelectionModel().getSelected();
				        if(infoRecord == null||infoRecord == ''){
				            Ext.Msg.alert('提示','请选择一行数据');
				        }else{
				        	form.getForm().loadRecord(infoRecord);
				            win.show();
				        }}
				}
			},{
				text:'播放',
				iconCls:'editIconCss',
				handler:function(){

					var selectRe = recordGrid.getSelectionModel().getSelections()[0];
					if (selectRe == null|| selectRe == "undefined") {
						Ext.Msg.alert('提示','请选择一条记录!');
					} else if(selectRe.data.physicalAddress==''||selectRe.data.physicalAddress==null){
						Ext.Msg.alert('提示','该次通话没有录音文件!');
					}else {
						//复制音频文件
						Ext.Ajax.request({
							url : basepath + '/ocrmFMmTelMain!copyFile.json',
							params : {
							'file':selectRe.data.physicalAddress
							},
							success : function(response) {
								var exit = 'no';//文件是否存在
								var file = selectRe.data.physicalAddress;//文件名
								exit =  response.responseText;
								if(exit=='no'){
									Ext.Msg.alert('提示', '音频文件已转存，如需访问请联系科技人员，文件名：'+file);
								}else
								 window.open( basepath+'/contents/pages/customer/customerManager/mktPhone/'+selectRe.data.physicalAddress,''
						    			 , 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
							},
							failure : function(response) {
								Ext.Msg.alert('提示', '获取录音文件失败');
							}
						});				    	 
					}
				
				}
			}],
			bbar:phoneBar,
			viewConfig:{},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
			});
	 //初始化数据
	 recordStore.load({
			params : {
				start : 0,
				limit : parseInt(phonePagesize_combo.getValue())
			}
		});
	//双击播放录音
	 recordGrid.on('rowdblclick', function(recordGrid, rowIndex, event) {
			var selectRe = recordGrid.getSelectionModel().getSelections()[0];
			if (selectRe == null|| selectRe == "undefined") {
				Ext.Msg.alert('提示','请选择一条记录!');
			} else if(selectRe.data.physicalAddress==''||selectRe.data.physicalAddress==null){
				Ext.Msg.alert('提示','该次通话没有录音文件!');
			}else {
				//复制音频文件
				Ext.Ajax.request({
					url : basepath + '/ocrmFMmTelMain!copyFile.json',
					params : {
					'file':selectRe.data.physicalAddress
					},
					success : function(response) {
						var exit = 'no';//文件是否存在
						var file = selectRe.data.physicalAddress;//文件名
						exit =  response.responseText;
						if(exit=='no'){
							Ext.Msg.alert('提示', '音频文件已转存，如需访问请联系科技人员，文件名：'+file);
						}else
						 window.open( basepath+'/contents/pages/customer/customerManager/mktPhone/'+selectRe.data.physicalAddress,''
				    			 , 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
					},
					failure : function(response) {
						Ext.Msg.alert('提示', '获取录音文件失败');
					}
				});
				
		    	 
			}
		});
		var viewport_center = new Ext.Panel({
			renderTo:'viewport_center',
			height:document.body.clientHeight-30,
			layout:'fit',
			autoScroll:true,
			items: [recordGrid] 
		});
});