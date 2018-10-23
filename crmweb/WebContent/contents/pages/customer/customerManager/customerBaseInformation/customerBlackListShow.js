Ext.onReady(function() {
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'qtip';
	var listPanel = new Mis.Ext.CrudPanel({
		id : "listPanel",
	    title : "客户黑名单信息",
		stUrl : basepath + '/AcrmFCiBlacklistAction-info!query.json?condition='+oCustInfo.cust_id,
		detailUrl : basepath + '/AcrmFCiCustLawInfoAction-info!query.json?condition='+oCustInfo.cust_id,
		primary : "id",
	    checkbox : true,
		//height : 450,
		layout:'fit',
		gclms : [{name:'id'},
		  	    {name:'customId',header:'客户编号',width:400},
			    {name : 'custName',header:'客户名称',width:400},
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
				], 	
		pagesize : 20,
		fclms:[ {
			xtype : 'fieldset',
			title : '客户黑名单信息',
			titleCollapse : true,
			//collapsible : true,
			autoHeight : true,
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .3,
					labelWidth : 60,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '编号',
						name : 'id',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '客户编号',
						name : 'customId',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '客户名称',
						name : 'custName',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					layout : 'form',
					columnWidth : .3,
					labelWidth : 60,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '证件号码',
						name : 'certNo',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '证件类型',
						name : 'certTyp',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					layout : 'form',
					columnWidth : .3,
					labelWidth : 60,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '类型',
						name : 'type',
						//value:affiCustManager,
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '登记人',
						name : 'registrant',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}]
				} ]
			} ]
		}, {
			xtype : 'fieldset',
			title : '其他信息',
//			collapsed : true,
//			collapsible : true,
			titleCollapse : true,
			// hideCollapseTool:true,
			autoHeight : true,
			items : [ {
				layout : 'column',
				items : [ {
					layout : 'form',
					columnWidth : .3,
					labelWidth : 60,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '联系电话',
						name : 'telephone',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '来源',
						name : 'origin',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '原因',
						name : 'cause',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}, 
					{
						xtype : 'datefield',
						fieldLabel : '登记日期',
						name : 'recordDate',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					layout : 'form',
					columnWidth : .3,
					labelWidth : 60,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '地址',
						name : 'address',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '管理机构',
						name : 'manageBran',
						// style:'text-align:right;',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '责任人',
						name : 'dutyOfficer',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}, {
						xtype : 'textfield',
						fieldLabel : '状态',
						name : 'state',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}]
				}, {
					layout : 'form',
					columnWidth : .3,
					labelWidth : 60,
					items : [ {
						xtype : 'textfield',
						fieldLabel : '经办机构',
						name : 'agencyBran',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}, {
						xtype : 'datefield',
						fieldLabel :'平台数据日期',
						name : 'odsStDate',
						labelStyle : 'text-align:right;',
						anchor : '95%'
					}]
				}]
			}
			]
		}


		]

});
	var viewport_center = new Ext.Panel({
		 renderTo:'viewport_center',
//		 id:'viewport_center',
			frame:true,
			height:document.body.scrollHeight-30,
			layout:'fit',
			autoScroll:true,
		
				items: [listPanel] 

			});
	});