Ext.onReady(function() {
	
	var typeStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '保守型' ], [ 2, '稳健型' ],[3,'平衡型'], [4,'成长型'],[5,'进取型']]
	});
	

	//客户信息form		
	var infoForm = new Ext.form.FormPanel( {
		labelWidth : 100,
		labelAlign : 'right',
		height:350,
		frame : true,
		region : 'north',
		autoScroll : true,
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			items : [new Com.yucheng.bcrm.common.CustomerQueryField({
				fieldLabel : '客户名称',
				labelWidth : 100,
				name : 'custName',
				custtype : '',// 客户类型:1:对私,2:对公,不设默认全部
				custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
				singleSelected : true,// 单选复选标志
				editable : false,
				allowBlank : false,
				anchor : '90%',
				hiddenName : 'custId',
				value:'李晓丽'
			}),{
				fieldLabel : '通胀率',
				name : 'q2',
				xtype : 'textfield', 
				value:'35%',
				anchor : '90%'
			},{
				fieldLabel : '回报率',
				name : 'q2',
				xtype : 'textfield', 
				value:'25%',
				anchor : '90%'
			},{
				fieldLabel : '期望退休年龄',
				name : 'q2',
				xtype : 'textfield', 
				value:'55',
				anchor : '90%'
			},{
				fieldLabel : '当前资产',
				name : 'q2',
				xtype : 'textfield', 
				value:'1,000,000.00',
				anchor : '90%'
			},{
				fieldLabel : '当前年收入',
				name : 'q2',
				xtype : 'textfield', 
				value:'100,000.00',
				anchor : '90%'
			},{
				fieldLabel : '当前净资产',
				name : 'q2',
				xtype : 'textfield', 
				value:'980,000.00',
				anchor : '90%'
			}]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				fieldLabel : '规划名称',
				name : 'q3',
				value:'李晓丽_养老规划',
				xtype : 'textfield', 
				anchor : '90%'
			},{
				fieldLabel : '我国居民价格指数',
				name : 'q4',
				xtype : 'textfield', 
				value:'105',
				anchor : '90%'
			},{
				store : typeStore ,
				xtype : 'combo',
				resizable : true,
				name : 'DBTABLE_ID',
				hiddenName : 'DBTABLE_ID',
				fieldLabel : '风险偏好',
				valueField : 'key',
				displayField : 'value',
				mode : 'local',
				value:'2',
				triggerAction : 'all',
				emptyText : '请选择',
				selectOnFocus : true,
				anchor : '90%'
			
		},{
			fieldLabel : '养老年数',
			name : 'q4',
			xtype : 'textfield', 
			value:'20',
			anchor : '90%'
		},{
			fieldLabel : '当前负债',
			name : 'q2',
			xtype : 'textfield', 
			value:'20,000.00',
			anchor : '90%'
		},{
			fieldLabel : '当前年支出',
			name : 'q2',
			xtype : 'textfield', 
			value:'20,000.00',
			anchor : '90%'
		},{
			fieldLabel : '当前月净收入',
			name : 'q2',
			xtype : 'textfield', 
			value:'10,000.00',
			anchor : '90%'
		}]
		}]
	});
	
	//养老金需求
	var needForm = new Ext.form.FormPanel( {
		labelWidth : 100,
		labelAlign : 'right',
		height:350,
		frame : true,
		region : 'north',
		autoScroll : true,
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			items : [{
				fieldLabel : '退休后支出占当前的百分比',
				name : 'q1',
				xtype : 'textfield', 
				value:'75%',
				anchor : '90%'
			},{
				fieldLabel : '投资回报为当前的',
				name : 'q2',
				xtype : 'textfield', 
				value:'80%',
				anchor : '90%'
			},{
				fieldLabel : '退休时每年获得社保',
				name : 'q2',
				xtype : 'textfield', 
				value:'250,000.00',
				anchor : '90%'
			}]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				fieldLabel : '退休后年支出',
				name : 'q3',
				value:'15,000.00',
				xtype : 'textfield', 
				anchor : '90%'
			},{
				fieldLabel : '投资回报',
				name : 'q4',
				xtype : 'textfield', 
				value:'16,000.00',
				anchor : '90%'
			},{
			fieldLabel : '退休时每年可获的养老保险金',
			name : 'q2',
			xtype : 'textfield', 
			value:'10,000.00',
			anchor : '90%'
		}]
		}]
	});
	
	
	//页面布局
	var view = new Ext.Viewport( {
		layout : "fit",
		frame : true,
		items : [ {
			layout : 'border',
			items : [{
				xtype:'portal',
	            id:'center',
	            region:'center',
	            items:[{
		            	 columnWidth:.5,
			                border:false,
			                autoHeight:true,
			                items:[{
			                    title: '客户基本信息',
			                    collapsible:true,
			                    layout:'fit',
			                    style:'padding:0px 0px 0px 0px',
			                    items:[infoForm]
			                }]
		            },{
		            	 columnWidth:.5,
			                border:false,
			                autoHeight:true,
			                items:[{
			                    title: '养老金需求',
			                    collapsible:true,
			                    layout:'fit',
			                    style:'padding:0px 0px 0px 0px',
			                    items:[needForm]
			                }]
		            }]
			}
			         ]
		} ]
	});
});