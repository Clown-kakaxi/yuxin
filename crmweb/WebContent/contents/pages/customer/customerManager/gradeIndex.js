Ext.onReady(function() {
		Ext.QuickTips.init();
		/*********客户品牌大类********/
		var finishStateStore = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=FORBID_FLAG'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON',
				totalProperty : 'list'
			}, [ 'key', 'value' ])
		});
		/*********客户类型定义********/
		var  customer_Type = new Ext.data.Store( {
			restful : true,
			sortInfo : {
				field : 'key',
				direction : 'ASC'
			},
			autoLoad : true,
			proxy : new Ext.data.HttpProxy( {
				url : basepath + '/lookup.json?name=XD000080'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'JSON',
				totalProperty : 'list'
			}, [ 'key', 'value' ])
		});
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "客户管理->客户分级管理->客户分级指标定义",
			stUrl : basepath + '/ocrmFCiGradeSetting-info!indexPage.json',
			deUrl : basepath + '/ocrmFCiGradeSetting-info!batchDestroy.json',
			addUrl : basepath + '/ocrmFCiGradeSetting-info.json',
			updateUrl : basepath + '/ocrmFCiGradeSetting-info.json',
			pagesize : 20,
			buts : [ ],
			primary : "id",
			checkbox : true,
			seFormHeight : 0,
			winHeight : 220,
			winWidth : 500,
			gclms : [ {
				name : 'id',
				hidden:true
			}, {
				width : 150,
				name : 'custType',
				header : '客户类型',
				type : 'mapping',
				store : customer_Type,
				mappingkey : 'key',
				mappingvalue : 'value'
			}, {
				width : 150,
				name : 'targetId',
				header : '指标号'
			}, {
				width : 150,
				name : 'targetName',
				header : '指标名称'
			}, {
				width : 150,
				name : 'isDisabled',
				header : '是否禁用',
				type : 'mapping',
				store : finishStateStore,
				mappingkey : 'key',
				mappingvalue : 'value'
			}, {
				width : 150,
				name : 'convertRate',
				header : '折算率'
			}],
			fclms : [ {
				layout : 'form',
				labelAlign:'right',
				items : [ {
					columnWidth : .5,
					labelWidth : 60,
					layout : 'form',
					items : [ {
						store : customer_Type,
						xtype : 'combo',
						editable:false,
						resizable : true,
						fieldLabel : '客户类型',
						name : 'custType',
						hiddenName : 'custType',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						typeAhead : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						selectOnFocus : true,
						width : '100',
						anchor : '90%'
					} ]
				}, {
					columnWidth : .5,
					labelWidth : 60,
					layout : 'form',
					items : [ {
						name : 'targetId',
						fieldLabel : '指标号',
						xtype : 'textfield',
						width : 100,
						allowBlank : false,
						maxLength : 200,
						anchor : '90%'
					} ]
				}, {
					columnWidth : .5,
					labelWidth : 60,
					layout : 'form',
					items : [ {
						name : 'targetName',
						fieldLabel : '指标名称',
						xtype : 'textfield',
						width : 100,
						allowBlank : false,
						maxLength : 200,
						anchor : '90%'
					} ]
				} , {
					columnWidth : .5,
					layout : 'form',
					labelWidth : 60,
					defaultType : 'textfield',
					border : false,
					items : [ {
						store : finishStateStore,
						xtype : 'combo',
						resizable : true,
						fieldLabel : '是否禁用',
						name : 'isDisabled',
						hiddenName : 'isDisabled',
						valueField : 'key',
						displayField : 'value',
						mode : 'local',
						editable:false,
						typeAhead : true,
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						selectOnFocus : true,
						width : '100',
						anchor : '90%'
					} ]
				}, {
					columnWidth : .5,
					labelWidth : 60,
					layout : 'form',
					items : [ {
						name : 'convertRate',
						fieldLabel : '折算率',
						xtype : 'numberfield', 
						decimalPrecision:10,
						//maxValue:100,
						width : 100,
						allowBlank : false,
						maxLength : 200,
						anchor : '90%'
					} ]
				} ]
			}, {
				layout : 'column',
				items : [  {// 特别注意：须放置隐藏域的主键
							name : 'id',
							xtype : 'textfield',
							header:'Id',
							hidden:true
						} ]
			} ]
		});
		var viewport = new Ext.Viewport( {
			items : [ listPanel ]
		});
	});