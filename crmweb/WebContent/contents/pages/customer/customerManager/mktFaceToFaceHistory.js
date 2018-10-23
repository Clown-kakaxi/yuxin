Ext.onReady(function(){
	//面谈营销记录**********
	var custid =oCustInfo.cust_id;//当前用户所查看的客户的客户号
	//通话记录部分**********
	recordStore = new Ext.data.Store( {
		  restful:true,
		  proxy : new Ext.data.HttpProxy({
		  	url:basepath+'/interviewMain.json?custId='+custid
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
		      {name:'FR_ID_ORA'},
		      {name: 'telType', mapping: 'TEL_TYPE'},
		      {name:'remark',mapping:'REMARK'},
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
        	}}//,
//    	 {header : '所属机构法人ID',dataIndex : 'FR_ID_ORA',sortable : true,hidden:(__frId==''?false:true),width : 150}
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
							name : 'BIS_TYPE_ORA',
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
							name : 'userName',
							disabled:true,
							fieldLabel : '通话客户经理',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}
		
								]
							}]
					},{
	    		 layout:'form',
	    		 items:[{
	    			 xtype:'textarea',
	    			 fieldLabel: '内容',
                     maxLength:200,
                     disabled:true,
                     allowBlank:false,
					 labelStyle: 'text-align:right;',
					 name: 'remark',
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
		            title : '<span style="font-weight:normal">过程纪要</span>',
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
		 width : document.body.clientWidth-240,
			frame : true,
			autoScroll : true,
			store : recordStore, // 数据存储
			stripeRows : true, // 斑马线
			cm : recordcm, // 列模型
			bbar:phoneBar,
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
				}
			}],
			viewConfig:{},
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
	 recordStore.load();
	//通话记录部分**********
		var viewport_center = new Ext.Panel({
			renderTo:'viewport_center',
			height:document.body.clientHeight-30,
			layout:'fit',
			autoScroll:true,
			items: [recordGrid] 
		});
});