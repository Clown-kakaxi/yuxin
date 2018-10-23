Ext.onReady(function() {
	var rsRecord = Ext.data.Record.create( [ 
	    {name:'id'},
	    {name:'customId'},
	    {name : 'custName'},
	    {name : 'address'},
	    {name : 'agencyBran'},
	    {name : 'cause'},
	    {name : 'certNo'},
	    {name : 'certTyp'},
	    {name : 'dutyOfficer'},
	    {name : 'manageBran'},
	    {name : 'odsStDate'},
	    {name : 'origin'},
	    {name : 'recordDate'},
	    {name : 'registrant'},
	    {name : 'state'},
	    {name : 'telephone'},
	    {name : 'type'},
	    {name:'recordDate'}
	]);
	var rsreader = new Ext.data.JsonReader( {
		successProperty : 'success',
//		idProperty : 'CUST_ID',
		messageProperty : 'message',
		root : 'json.data',
		totalProperty : 'json.count'
	}, rsRecord);


	//alert(123);
		var simple = new Ext.FormPanel({
//			store:simpleStore,
			reader : rsreader,
			frame : true,
			height : 475,
			autoScroll : true,
			tbar:[{text:'编辑',
				iconCls : 'page_editIcon',
				handler:function(){
				Ext.getCmp('id').enable();
				Ext.getCmp('customId').enable();
				Ext.getCmp('address').enable();
				Ext.getCmp('agencyBran').enable();
				Ext.getCmp('cause').enable();
				Ext.getCmp('certNo').enable();
				Ext.getCmp('certTyp').enable();
				Ext.getCmp('dutyOfficer').enable();
				Ext.getCmp('manageBran').enable();
				Ext.getCmp('odsStDate').enable();
				Ext.getCmp('origin').enable();
				Ext.getCmp('recordDate').enable();
				Ext.getCmp('registrant').enable();
				Ext.getCmp('state').enable();
				Ext.getCmp('telephone').enable();
				Ext.getCmp('type').enable();
				Ext.getCmp('custName').enable();
			}
			},{text:'保存',
				handler:function(){
				save();
				setDisable();
			}}],
			items : [ {
				xtype : 'fieldset',
				title : '客户黑名单信息',
				titleCollapse : true,
				//collapsible : true,
				autoHeight : true,
				items : [ {
					layout : 'column',
					items : [ {
						layout : 'form',
						columnWidth : .25,
						labelWidth : 120,
						items : [ {
							xtype : 'textfield',
							fieldLabel : '编号',
							name : 'id',
							id:'id',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '客户编号',
							name : 'customId',
							id:'customId',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '客户名称',
							name : 'custName',
							id:'custName',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}]
					}, {
						layout : 'form',
						columnWidth : .25,
						labelWidth : 120,
						items : [ {
							xtype : 'textfield',
							fieldLabel : '证件号码',
							id:'certNo',
							name : 'certNo',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '证件类型',
							id:'certTyp',
							name : 'certTyp',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}]
					}, {
						layout : 'form',
						columnWidth : .5,
						labelWidth : 120,
						items : [ {
							xtype : 'textfield',
							fieldLabel : '类型',
							name : 'type',
							id:'type',
							//value:affiCustManager,
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '登记人',
							name : 'registrant',
							id:'registrant',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}]
					} ]
				} ]
			}, {
				xtype : 'fieldset',
				title : '其他信息',
//				collapsed : true,
//				collapsible : true,
				titleCollapse : true,
				// hideCollapseTool:true,
				autoHeight : true,
				items : [ {
					layout : 'column',
					items : [ {
						layout : 'form',
						columnWidth : .25,
						labelWidth : 120,
						items : [ {
							xtype : 'textfield',
							fieldLabel : '联系电话',
							name : 'telephone',
							id:'telephone',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '来源',
							name : 'origin',
							id:'origin',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '原因',
							name : 'cause',
							id:'cause',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, 
						{
							xtype : 'datefield',
							fieldLabel : '登记日期',
							name : 'recordDate',
							id:'recordDate',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}]
					}, {
						layout : 'form',
						columnWidth : .25,
						labelWidth : 120,
						items : [ {
							xtype : 'textfield',
							fieldLabel : '地址',
							name : 'address',
							id:'address',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '管理机构',
							name : 'manageBran',
							id:'manageBran',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '责任人',
							disabled : true,
							id:'dutyOfficer',
							name : 'dutyOfficer',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}, {
							xtype : 'textfield',
							fieldLabel : '状态',
							disabled : true,
							name : 'state',
							id:'state',
							labelStyle : 'text-align:right;',
							anchor : '95%'
						}]
					}, {
						layout : 'form',
						columnWidth : .25,
						labelWidth : 120,
						items : [ {
							xtype : 'textfield',
							fieldLabel : '经办机构',
							name : 'agencyBran',
							id:'agencyBran',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}, {
							xtype : 'datefield',
							fieldLabel :'平台数据日期',
							name : 'odsStDate',
							id:'odsStDate',
							// style:'text-align:right;',
							labelStyle : 'text-align:right;',
							disabled : true,
							anchor : '95%'
						}]
					}]
				}
				]
			}


			]
		});
		simple.on('afterlayout',function(){
			Ext.getCmp('customId').setValue(oCustInfo.cust_id);
			Ext.getCmp('custName').setValue(oCustInfo.cust_name);
		}
		
		);
		simple.getForm().load( {
			
			restful : true,
			url : basepath + '/AcrmFCiBlacklistAction-info!query.json',
			params : {
					'condition' : oCustInfo.cust_id
			},
			method : 'GET'
		});
		var simpleStore = new Ext.data.Store({
			restful : true,
			proxy : new Ext.data.HttpProxy({
				url : basepath + '/AcrmFCiBlacklistAction-info!query.json',
				params:{
				'condition':'1'
			}
			}),
			reader : new Ext.data.JsonReader({
				successProperty: 'success',
//		        idProperty: 'APPLY_ID',
		        messageProperty: 'message',
				root : 'json.data',
				totalProperty: 'json.count'
			}, rsRecord)
		});
		var viewport_center = new Ext.Panel({
			 renderTo:'viewport_center',
//			 id:'viewport_center',
				frame:true,
				height:document.body.scrollHeight-30,
				layout:'fit',
				autoScroll:true,
			
					items: [simple] 

				});
		function save(){
			Ext.Ajax.request({
				url:basepath + '/AcrmFCiBlacklistAction-info!update.json',
				form:simple.getForm().id,
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function() {
					Ext.Msg.alert('提示', '操作成功');
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
				}
			});
		}
		function setDisable(){
			Ext.getCmp('id').disable();
			Ext.getCmp('customId').disable();
			Ext.getCmp('address').disable();
			Ext.getCmp('agencyBran').disable();
			Ext.getCmp('cause').disable();
			Ext.getCmp('certNo').disable();
			Ext.getCmp('certTyp').disable();
			Ext.getCmp('dutyOfficer').disable();
			Ext.getCmp('manageBran').disable();
			Ext.getCmp('odsStDate').disable();
			Ext.getCmp('origin').disable();
			Ext.getCmp('recordDate').disable();
			Ext.getCmp('registrant').disable();
			Ext.getCmp('state').disable();
			Ext.getCmp('telephone').disable();
			Ext.getCmp('type').disable();
			Ext.getCmp('custName').disable();
		}
		/*
		 * function editInit(){ window.location.href =
		 * 'familyMemberInformation.html' ; };
		 */
	});