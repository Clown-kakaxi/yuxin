Ext.onReady(function(){
	//短信营销记录**********
	var custid =oCustInfo.cust_id;//当前用户所查看的客户的客户号
	recordStore = new Ext.data.Store( {
		  restful:true,
		  proxy : new Ext.data.HttpProxy({
		  	url:basepath+'/custMessageMktQueryAction.json?custId='+custid
		  }),
		  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'id', mapping: 'ID'},
		      {name: 'custId', mapping: 'CUST_ID'},
		      {name: 'custName', mapping: 'CUST_NAME'},
		      {name: 'IF_SEND_ORA'},
		      {name: 'FR_ID_ORA'},
		      {name: 'MESSAGE_REMARK'},
		      {name: 'PROD_NAME'},
		      {name: 'sendDate', mapping: 'CTR_DATE'},
		      {name:'cellNumber',mapping:'CELL_NUMBER'},
		      {name: 'modelName', mapping: 'MODEL_NAME'},
		      {name:'userName',mapping:'USER_ID'}
		      ])
	});
	
	 var recordcm =  new Ext.grid.ColumnModel([
	    {header : 'ID',dataIndex : 'id',sortable : true,hidden:true,width : 150},
        {header : '短信发送人',dataIndex : 'userName',sortable : true,width : 150},
        {header : '短信发送时间',dataIndex : 'sendDate',sortable : true,width : 150},
        {header : '短信营销产品',dataIndex : 'PROD_NAME',sortable : true,width : 150},
        {header : '短信内容模板',dataIndex : 'modelName',sortable : true,width : 150},
        {header:'是否已发送',dataIndex:'IF_SEND_ORA',sortable : true,width : 150},
        {header : '短信内容',dataIndex : 'MESSAGE_REMARK',sortable : true,width : 150}//,
//	    {header : '所属机构法人ID',dataIndex : 'FR_ID_ORA',sortable : true,hidden:(__frId==''?false:true),width : 150}
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
							name : 'userName',
							disabled:true,
							labelStyle : 'text-align:right;',
							fieldLabel : '短信发送人',
							anchor : '95%'
						},{
							xtype : 'textfield',
							name : 'PROD_NAME',
							disabled:true,
							labelStyle : 'text-align:right;',
							fieldLabel : '短信营销产品',
							anchor : '95%'
						}]
							},{
							columnWidth:0.5,
							layout: 'form',
							items: [
 								{
							xtype : 'datefield',
							format:'Y-m-d',
							name : 'sendDate',
							disabled:true,
							fieldLabel : '短信发送时间',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						},{
							xtype : 'textfield',
							name : 'modelName',
							disabled:true,
							labelStyle : 'text-align:right;',
							fieldLabel : '短信内容模板',
							anchor : '95%'
						}]
							}]
					},{
	    		 layout:'form',
	    		 items:[{
	    			 xtype:'textarea',
	    			 fieldLabel: '短信内容',
                     maxLength:200,
                     disabled:true,
                     allowBlank:false,
					 labelStyle: 'text-align:right;',
					 name: 'MESSAGE_REMARK',
					 anchor:'97.5%'
	    		 }]
	    	 }]
	     }]
	 });
	   var win = new Ext.Window(
		        {
		            //layout : 'fit',
		            height : 210,
		            width:600,
		            title : '<span style="font-weight:normal">信息</span>',
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
				text:'详情',
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
			}],
			viewConfig:{},
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
			});
	 recordStore.load();
		var viewport_center = new Ext.Panel({
			renderTo:'viewport_center',
			height:document.body.clientHeight-30,
			layout:'fit',
			autoScroll:true,
			items: [recordGrid] 
		});
});