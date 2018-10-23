Ext.onReady(function() {
	var h= document.body.clientHeight;
	var searchPanel = new Ext.form.FormPanel({
		region:'center',
		margins: '0 0 0 0',
		autoScroll:true,
		labelWidth : 120, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		height : 120,
		items : [{
			layout : 'column',
			border : false,
			items : [{
				columnWidth : .4,
				layout : 'form',
				labelWidth : 120, // 标签宽度
				defaultType : 'textfield',
				border : false,
				items : [
				    new Ext.ux.form.LovCombo({
				    	fieldLabel: '今天中午吃啥？',
				    	id:'tablePkField',
//							    	name: 'tablePkField',
				    	displayField:'value',
				    	valueField:'key',
				    	hideOnSelect:false,
				    	store : new Ext.data.ArrayStore({
							fields : [ 'key', 'value' ],
							data : [ [ 10, '拉面' ], [ 20, '蛋炒饭' ], [ 50, '鸡腿饭' ],
									[ 100, '炒河粉' ], [ 250, '利群' ],
									[ 455, '煲仔饭' ] ]
						}),
				    	triggerAction:'all',
				    	mode:'local',
				    	allowBlank:false,
				    	editable:true
				    })
			    ]}
			]
		}],
		buttons : [{
			text : '查询'
			/*handler : function() {
				queryBalanceInfo(qForm.getForm());
			}*/
		}, {
			text : '重置'
			/*handler : function() {
				qForm.getForm().reset();
			}*/
		}]
	});
	
	var view = new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items : [ {
			layout : 'border',
			items : [searchPanel]
		} ]
	});
	Ext.getCmp('tablePkField').on('select',function(a,b,c,d){
//		debugger;
	});
})