/**
 * 客户群基本信息
 * hujun
 * 2014-07-19
 * */

	var createView=false;
	var editView=false;
	var detailView=false;
	var __hiddeAble=false;
	var groupId=_busiId;//获取客户群ID
	var lookupTypes=[
		'CUSTOMER_GROUP_TYPE',
		'GROUP_MEMEBER_TYPE',
		'SHARE_FLAG',
		'CUSTOMER_SOURCE_TYPE'
	];
	WLJUTIL.suspendViews=false;  //自定义面板是否浮动
	var fields=[{name:'text',name:'',hidden:true}];
	var needGrid=false;
	 //证件类型
	 var certTypeStore = new Ext.data.Store({
		restful : true,
		sortInfo : {field : 'key',direction : 'ASC'},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=XD000040'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	 
//	 //客户群分类
//	 var customergroupTypeStore = new Ext.data.Store({
//		restful : true,
//		sortInfo : {field : 'key',direction : 'ASC'},
//		autoLoad : true,
//		proxy : new Ext.data.HttpProxy({
//			url : basepath + '/lookup.json?name=CUSTOMER_GROUP_TYPE'
//		}),
//		reader : new Ext.data.JsonReader({
//			root : 'JSON'
//		}, [ 'key', 'value' ])
//	});
   //客户来源
   var customerSourceTypeStore = new Ext.data.Store({
		restful : true,
		sortInfo : {field : 'key',direction : 'ASC'},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=CUSTOMER_SOURCE_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
   //群成员类型
   var groupMemeberTypeStore = new Ext.data.Store({
		restful : true,
		sortInfo : {field : 'key',direction : 'ASC'},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=GROUP_MEMEBER_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
  //客户群共享范围
	var shareFlagStore = new Ext.data.Store({
		restful : true,
		sortInfo : {field : 'key',direction : 'ASC'},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=SHARE_FLAG'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var record = Ext.data.Record.create(
			  [{name: 'id', mapping: 'ID'}
		      ,{name: 'custBaseNumber', mapping: 'CUST_BASE_NUMBER'}
		      ,{name: 'custBaseName', mapping: 'CUST_BASE_NAME'}
		      ,{name: 'createName',mapping:'CREATENAME'}
		      ,{name: 'custBaseCreateDate', mapping: 'CUST_BASE_CREATE_DATE'}
		      ,{name: 'custBaseCreateName', mapping: 'CUST_BASE_CREATE_NAME'} 	
		      ,{name: 'customerBaseMemberNum', mapping: 'MEMBERSNUM'}
		      ,{name: 'custFrom', mapping: 'CUST_FROM'}
		      ,{name: 'custFromName', mapping: 'CUST_FROM_NAME'}
		      ,{name: 'custBaseDesc', mapping: 'CUST_BASE_DESC'}
		      ,{name: 'shareFlag',mapping :'SHARE_FLAG'}
		      ,{name: 'custBaseCreateOrg',mapping : 'CUST_BASE_CREATE_ORG'}
		      ,{name: 'custBaseCreateOrgName',mapping : 'CUST_BASE_CREATE_ORG_NAME'}
		      ,{name: 'mainUserName',mapping : 'MAIN_USER_NAME'}
		      ,{name: 'mainOrgName',mapping : 'MAIN_ORG_NAME'}
		      ,{name: 'recentUpdateUser',mapping : 'RECENT_UPDATE_USER'}
		      ,{name: 'recentUpdateOrg',mapping : 'RECENT_UPDATE_ORG'}
		      ,{name: 'recentUpdateDate',mapping : 'RECENT_UPDATE_DATE'}
		      ,{name: 'groupType',mapping : 'GROUP_TYPE'}
		      ,{name: 'groupMemberType',mapping : 'GROUP_MEMBER_TYPE'}
		      ,{name: 'GROUP_TYPE_ORA'}
		      ,{name: 'SHARE_FLAG_ORA'}
		      ,{name: 'CUST_FROM_ORA'}
		      ,{name: 'GROUP_MEMBER_TYPE_ORA'}
		      ]);
	
	var blocBaseInfo = new Ext.FormPanel({
		frame : true,
//		autoScroll : true,
		region:'center',
		autoHeight:true,
		buttonAlign:'center',
		reader: new Ext.data.JsonReader({
          root:'json.data'
          },record),
		items : [{
	                layout : 'column',
	                items : [{
	                    columnWidth : .5,
	                    layout : 'form',
	                    labelWidth : 120,
	                    items : [ 
	                       {xtype : 'textfield',labelStyle : 'text-align:right;',fieldLabel : '客户群编号', name : 'custBaseNumber',readOnly:true,
							cls:'x-readOnly', anchor : '95%'},
	                       {
	                        xtype : 'textfield',
	                        labelStyle : 'text-align:right;',
	                        fieldLabel : '客户群名称',
	                        allowBlank : false,
	                        name : 'custBaseName',
	                        anchor : '95%'
	                    },{
							store : groupMemeberTypeStore,
							xtype : 'combo', 
							fieldLabel : '群成员类型',
							hiddenName : 'groupMemberType',
							name : 'groupMemberType',
							valueField : 'key',
							labelStyle : 'text-align:right;',
							displayField : 'value',
							allowBlank : false,
							mode : 'local',
							readOnly:true,
							cls:'x-readOnly',
							triggerAction : 'all',
							emptyText : '请选择',
							anchor : '95%'
						},{
	                        xtype : 'textfield',
	                        fieldLabel : '创建人',
	                        readOnly:true,
							cls:'x-readOnly',
	                        labelStyle : 'text-align:right;',
	                        name : 'createName',
	                        anchor : '95%'
	                    },{
	                        xtype : 'datefield',
	                        fieldLabel : '创建时间',
	                        format : 'Y-m-d',
	                        readOnly:true,
							cls:'x-readOnly',
	                        labelStyle : 'text-align:right;',
	                        name : 'custBaseCreateDate',
	                        anchor : '95%'
	                    } ]
	                },{
	                    columnWidth : .5,
	                    labelWidth : 120,
	                    layout : 'form',
	                    items : [ {store : customerSourceTypeStore,xtype : 'combo',	resizable : true,fieldLabel : '客户来源',hiddenName : 'custFrom',name : 'custFrom',
							valueField : 'key',
							labelStyle : 'text-align:right;',
							displayField : 'value',
							mode : 'local',
							forceSelection : true,
							readOnly:true,
							cls:'x-readOnly',
							triggerAction : 'all',
							emptyText : '请选择',
							anchor : '95%'
						},{
//							store : customergroupTypeStore,
//							xtype : 'combo', 
//							resizable : true,
//							fieldLabel : '客户群分类',
//							hiddenName : 'groupType',
//							name : 'groupType',
//							valueField : 'key',
//							allowBlank : false,
//							labelStyle : 'text-align:right;',
//							displayField : 'value',
//							mode : 'local',
//							editable :false,
//							forceSelection : true,
//							triggerAction : 'all',
//							emptyText : '请选择',
//							anchor : '95%'
//						}, {
							store : shareFlagStore,
							xtype : 'combo', 
							resizable : true,
							fieldLabel : '共享范围',
							hiddenName : 'shareFlag',
							name : 'shareFlag',
							valueField : 'key',
							labelStyle : 'text-align:right;',
							displayField : 'value',
							allowBlank : false,
							mode : 'local',
							editable :false,
							forceSelection : true,
							triggerAction : 'all',
							emptyText : '请选择',
							anchor : '95%'
						},{
	                        xtype : 'textfield',
	                        labelStyle : 'text-align:right;',
	                        hidden:true,
	                        fieldLabel : 'ID',
	                        name : 'id',
	                        anchor : '95%'
	                    },{
	                        xtype : 'textfield',
	                        fieldLabel : '创建人',
	                        hidden:true,
	                        name : 'custBaseCreateName',
	                        anchor : '95%'
	                    },{
	                    	xtype : 'textfield',
	                        fieldLabel : '群成员数',
	                        readOnly:true,
							cls:'x-readOnly',
	                        labelStyle : 'text-align:right;',
	                        name : 'customerBaseMemberNum',
	                        anchor : '95%'
	                    },{
	                        xtype : 'textfield',
	                        fieldLabel : '创建机构',
	                        hidden:true,
	                        labelStyle : 'text-align:right;',
	                        name : 'custBaseCreateOrg',
	                        anchor : '95%'
	                    },{
	                        xtype : 'textfield',
	                        readOnly:true,
							cls:'x-readOnly',
	                        fieldLabel : '创建机构',
	                        labelStyle : 'text-align:right;',
	                        name : 'custBaseCreateOrgName',
	                        anchor : '95%'
	                    }  ]
	                },{
	                    columnWidth : 1,
	                    labelWidth : 120,
	                    layout : 'form',
	                    items : [ {
	                        xtype : 'textarea',
	                        labelStyle : 'text-align:right;',
	                        fieldLabel : '客户群描述',
	                        name : 'custBaseDesc',
	                        anchor : '97.5%'
	                    } ]
	                } ]
		}],
          buttons:[
          {
  			text : '保存',
  			//hidden:__hiddeAble,
  			id:'saveButton',
  			handler : function() {
  			if (!blocBaseInfo.getForm().isValid()) {
                  Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                  return false;
              };
  			Ext.Ajax.request({
  				url : basepath + '/customergroupinfo.json',
  				params : {
  				operate:'add'
  				},
  				method : 'POST',
  				form : blocBaseInfo.getForm().id,
  				success : function() {
  					alert('保存成功');
  				},
  				failure : function(response) {
  					var resultArray = Ext.util.JSON.decode(response.status);
  				       if(resultArray == 403) {
  				           Ext.Msg.alert('系统提示', response.responseText);
  				  } else{
  					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
  				}}
  			});
  		}}]
	});
	var groupRecord = Ext.data.Record.create( [ {
		name : 'GROUP_MEMBER_TYPE',
		mapping : 'GROUP_MEMBER_TYPE'
	}, {
		name : 'CUST_FROM',
		mapping : 'CUST_FROM'
	} ]);
	//查询客户群信息
	var groupStore = new Ext.data.Store( {
		restful : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/customergroupinfo!queryGroupInfo.json'
		}),
		reader : new Ext.data.JsonReader( {
			successProperty : 'success',

			messageProperty : 'message',
			root : 'data',
			totalProperty : 'count'
		}, groupRecord)
	});
	groupStore.on('beforeload', function() {
			this.baseParams = {
					groupId: groupId
					
			  };
		    });
	var customerView=[{
			title:'基本信息',
			hideTitle:true,
			layout:'fit',
			items:[blocBaseInfo]
	}];
	//划入面板前
	var beforeviewshow = function(view){
		
		if(view._defaultTitle == '基本信息'){
			groupStore.load({
				 callback : function() {
					 title_count = groupStore.getCount();
					if(title_count>0){
						title = groupStore.getAt(0);
					var creatId=title.json.CUST_BASE_CREATE_NAME;
					 if(creatId!=__userId){//非创建人
						 Ext.getCmp('saveButton').setVisible(false);
							 __hiddeAble=true;
						 }else{
							 Ext.getCmp('saveButton').setVisible(true);
						 }
					}
					}
					});
			blocBaseInfo.getForm().load({
				 restful:true,	
			     url:basepath+'/customergroupinfo.json',
			     method: 'GET',
			     params:{
					'custBaseNumber':groupId
				}
			    });	
			}
	};