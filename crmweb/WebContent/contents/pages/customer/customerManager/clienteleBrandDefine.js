Ext.onReady(function() {
	function setValue(a) {
		Ext.getCmp('wancheng_1').setValue(a), listPanel.save();
	}
	Ext.QuickTips.init();
	/*品牌大类*/
	var p_cust_grade = new Ext.data.Store( {
		restful : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : false,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=P_CUST_GRADE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	p_cust_grade.load();
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
	customer_Type.load();
	var listPanel = new Mis.Ext.CrudPanel( {
		id : "listPanel",
		title : "客户管理->评级管理->客户品牌定义",
		stUrl : basepath + '/ocrmFCiBrandDefine-info!indexPage.json',
		deUrl : basepath + '/ocrmFCiBrandDefine-info!batchDestroy.json',
		addUrl : basepath + '/ocrmFCiBrandDefine-info.json',
		updateUrl : basepath + '/ocrmFCiBrandDefine-info.json',
		pagesize : 20,
		buts : [],
		primary : "id",
		checkbox : true,
		seFormHeight : 0,
		winHeight : 250,
		winWidth : 500,
		gclms : [ {
			name:'id',
			hidden :true
		},{
			width : 150,
			name : 'custTyp',
			header : '客户类型',
			type : 'mapping',
			store : customer_Type,
			mappingkey : 'key',
			mappingvalue : 'value'
		}, {
			width : 150,
			name : 'brand',
			header : '客户品牌'
		}, {
			width : 150,
			name : 'brandTyp',
			header : '客户品牌大类',
			type : 'mapping',
			store : p_cust_grade,
			mappingkey : 'key',
			mappingvalue : 'value'
		}, {
			width : 150,
			name : 'countLower',
			header : '指标总分数下线(包含)'
		}, {
			width : 150,
			name : 'countUpper',
			header : '指标总分数上线(不包含)'
		}],
		fclms : [ {
			layout : 'form',
		    labelAlign:'right',
			items : [ {
				columnWidth : .5,
				labelWidth : 150,
				layout : 'form',
				items : [ {
					store : customer_Type,
					xtype : 'combo',
					editable:false,
					resizable : true,
					fieldLabel : '客户类型',
					name : 'custTyp',
					hiddenName : 'custTyp',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					width : '100',
					anchor : '95%'
				} ]
			}, {
				columnWidth : .5,
				labelWidth : 150,
				layout : 'form',
				items : [ {
					name : 'brand',
					fieldLabel : '客户品牌',
					xtype : 'textfield',
					width : 100,
					allowBlank : false,
					maxLength : 200,
					anchor : '95%'
				} ]
			},  {
				columnWidth : .5,
				labelWidth : 150,
				layout : 'form',
				items : [ {
					store : p_cust_grade,
					xtype : 'combo',
					resizable : true,
					fieldLabel : '客户品牌大类',
					editable:false,
					name : 'brandTyp',
					hiddenName : 'brandTyp',
					valueField : 'key',
					displayField : 'value',
					mode : 'local',
					typeAhead : true,
					forceSelection : true,
					triggerAction : 'all',
					emptyText : '请选择',
					selectOnFocus : true,
					width : '100',
					anchor : '95%'
				} ]
			}, {
				columnWidth : .5,
				labelWidth : 150,
				layout : 'form',
				items : [ {
					name : 'countLower',
					fieldLabel : '指标总分数下线(包含)',
					xtype : 'numberfield',
					decimalPrecision:10,
					width : 100,
					allowBlank : false,
					maxLength : 200,
					anchor : '95%'
				} ]
			}, {
				columnWidth : .5,
				labelWidth : 150,
				layout : 'form',
				items : [ {
					name : 'countUpper',
					fieldLabel : '指标总分数上线(不包含)',
					xtype : 'numberfield',
					decimalPrecision:10,
					width : 100,
					allowBlank : false,
					maxLength : 200,
					anchor : '95%'
				} ]
			} ]
		}, {
			layout : 'column',
			items : [ {// 特别注意：须放置隐藏域的主键
				name : 'id',
				xtype : 'textfield',
				header : 'id',
				hidden : true
			} ]
		} ]
	});
	var viewport = new Ext.Viewport( {
		items : [ listPanel ]
	});
});