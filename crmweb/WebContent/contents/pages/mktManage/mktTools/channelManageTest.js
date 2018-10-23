Ext.onReady(function() {
			Ext.QuickTips.init(); 
			//渠道类型下拉框的数据查询
			var channelTypeStore = new Ext.data.JsonStore({
				id : channelTypeStore,
				restful : true,
				autoLoad : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/channel-type!indexAll.json'
				}),
				fields : [ 'channelTypeId', 'channelTypeName' ],
				reader : new Ext.data.JsonReader({
					totalProperty : 'list'
				}, [ {
					name : 'channelTypeId'
				}, {
					name : 'channelTypeName'
				} ])
			});
			//最终展现的panel
			var listPanel = new Mis.Ext.CrudPanel({
				id : "listPanel",
				title : "渠道管理",
				stUrl : basepath + '/channelInfoQuery.json',
				//demoData : {"json":{"count":9,"data":[{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45396","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-27","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"发改委","CHANNEL_NAME":"23","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10327","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-27","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45386","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"7777777","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45391","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"商会","CHANNEL_NAME":"444444","CHANNEL_FEATURE":"","ACCESS_CONDITION":"123123","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10328","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45376","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-24","GUARANTEE":"1","REMARK":"1","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"测试2","CHANNEL_FEATURE":"1","ACCESS_CONDITION":"1","CHANNEL_POLICY":"1","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-09","UPDATE_USER":"admin"},{"UNITNAME":"北京管理部","CREATE_ORG":"00021","CHANNEL_ID":"17251","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"新渠道22223333","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"008755","USERNAME":"范*","CREATE_DATE":"2011-10-14","UPDATE_USER":"008755"},{"UNITNAME":"白云支行","CREATE_ORG":"04101","CHANNEL_ID":"17148","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"白云山","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"000727","USERNAME":"刘*","CREATE_DATE":"2011-10-09","UPDATE_USER":"000727"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"12367","CHANNEL_CODE":"","UPDATE_DATE":"2011-09-24","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"委办局","CHANNEL_NAME":"啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊1","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"10352","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-09-24","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"45390","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-25","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"12212","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"admin","USERNAME":"系统管理员*","CREATE_DATE":"2011-10-25","UPDATE_USER":"admin"},{"UNITNAME":"总行","CREATE_ORG":"00001","CHANNEL_ID":"17253","CHANNEL_CODE":"","UPDATE_DATE":"2011-10-14","GUARANTEE":"","REMARK":"","CHANNEL_TYPE_NAME":"财政","CHANNEL_NAME":"总行客户经理","CHANNEL_FEATURE":"","ACCESS_CONDITION":"","CHANNEL_POLICY":"","CHANNEL_TYPE_ID":"11006","CREATE_USER":"010514","USERNAME":"韩*","CREATE_DATE":"2011-10-14","UPDATE_USER":"010514"}]}},
				addUrl : basepath + '/channel-info.json',
				updateUrl : basepath + '/channel-info.json',
				deUrl : basepath+ '/channel-info!batchDestroy.json',
				primary : "channelId",
				checkbox : true,
				winHeight : 450,
				winWidth : 800,
				saveSubmitFun: function(){
					return true;
				},
				gclms : [{name : 'channelId',mapping : 'CHANNEL_ID'},
				        {name : 'accessCondition',mapping : 'ACCESS_CONDITION'},
						{name : 'channelName',header:'渠道名称',mapping :'CHANNEL_NAME'},
						{name : 'channelFeature',mapping : 'CHANNEL_FEATURE'},
						{name : 'channelPolicy',mapping : 'CHANNEL_POLICY'},
						{name : 'createUser',mapping : 'CREATE_USER'}, 
						{name : 'updateDate',mapping : 'UPDATE_DATE'},
						{name : 'updateUser',mapping : 'UPDATE_USER'},
						{name : 'guarantee',mapping : 'GUARANTEE'}, 
						{name : 'remark',mapping : 'REMARK'},
						{name : 'channelTypeName',header:'渠道类型',mapping : 'CHANNEL_TYPE_NAME'},
						{name : 'createUserName',header:'渠道建立人',mapping : 'USERNAME'},
						{name : 'createOrganizationName',header:'渠道建立机构',mapping : 'UNITNAME'},
						{name : 'createDate',header:'渠道建立日期',mapping : 'CREATE_DATE',type : 'date'},
						{name : 'channelTypeId',mapping : 'CHANNEL_TYPE_ID'},
						{name : 'createOrganization',mapping : 'CREATE_ORG'}],
				pagesize : 20,
				//查询字段
				selectItems:{layout:'column',items:
					[{columnWidth:.25,layout:'form',defaultType:'textfield',border:false,items:[{name:'CHANNEL_NAME',xtype:'textfield',fieldLabel:'渠道名称',width :'100',anchor:'90%'}]}, 
					 {columnWidth:.25,layout:'form',labelWidth:80,defaultType:'textfield',border : false,items : [{store : channelTypeStore,xtype : 'combo', resizable : true,fieldLabel : '渠道类型',name : 'CHANNEL_TYPE_ID',hiddenName : 'CHANNEL_TYPE_ID',valueField : 'channelTypeId',displayField : 'channelTypeName',mode : 'local',typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true,width : '100',anchor : '90%'}]}, 
					 {columnWidth:.4,layout:'column',xtype:'panel',items : [{columnWidth :.5,layout:'form',labelWidth:80,items:{fieldLabel:'渠道建立日期',xtype:'datefield',format:'Y-m-d',editable:false,name:'createDateS',anchor : '100%'}},
                     {columnWidth:.5,layout : 'form',labelStyle : 'text-align:center',labelAlign : 'right',labelSeparator : '',labelWidth : 40,items : {xtype : 'datefield',resizable : true,fieldLabel : '至',name : 'createDateE',format : 'Y-m-d',editable : false,	anchor : '90%'}}]}
					]},
				//from的字段
				fclms : [{layout : 'column',items : [ 
						          {columnWidth : .5,layout : 'form',items : [{name : 'channelName',fieldLabel:'渠道名称',xtype : 'textfield',width:100,allowBlank : false,maxLength:200,anchor : '90%'}]},
								  {columnWidth : .5,layout : 'form',items :	[{name : 'channelTypeName',fieldLabel:'渠道类型',xtype : 'combo',store : channelTypeStore,width:100,allowBlank : false,valueField : 'channelTypeId',hiddenName : 'channelTypeId',displayField : 'channelTypeName',mode : 'local',typeAhead : true,resizable : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',selectOnFocus : true,anchor : '90%'}]}]
					     },{layout : 'form',items : [
					              {name : 'accessCondition',fieldLabel:'准入条件',xtype : 'textarea',width:200,maxLength:400,anchor : '90%'},
								  {name : 'channelFeature',fieldLabel : '渠道特点',xtype : 'textarea',width:200,maxLength:400,anchor : '90%'},
							      {name : 'channelPolicy',fieldLabel : '渠道政策',xtype : 'textarea',width:200,maxLength:400,anchor : '90%'},
							      {name : 'guarantee',fieldLabel : '担保',xtype : 'textarea',width:200,maxLength:400,anchor : '90%'},
							      {name : 'remark',fieldLabel : '备注',xtype : 'textarea',width:200,maxLength:400,anchor : '90%'}]
					     },{layout : 'column',items :
					    	 [{columnWidth : .5,layout : 'form',items : [{name : 'createUserName',fieldLabel:'渠道建立人',width:100,xtype : 'textfield',readOnly : true,anchor : '90%'}]},
						      {columnWidth : .5,layout : 'form',items : [{name : 'createOrganizationName',fieldLabel:'渠道建立机构',width:100,xtype : 'textfield',readOnly : true,anchor : '90%'}]}]
						 },{layout : 'column',items :
							 [{columnWidth : .5,layout : 'form',items : [{name : 'createDate',fieldLabel : '渠道建立日期',width:100,xtype:'textfield',readOnly : true,anchor : '90%'}]},
							  {columnWidth : .5,layout : 'form',items : [{name : 'updateDate',fieldLabel : '最近更新日期',width:100,xtype:'textfield',readOnly : true,anchor : '90%'}]},
							  {name : 'createUser',xtype : 'hidden'},{name:'createOrganization',xtype:'hidden'},{name : 'updateUser',xtype:'hidden'},{name:'channelId',xtype:'hidden'}]
						 }]
			});
			// 布局模型
			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [ listPanel ]
			});
		});