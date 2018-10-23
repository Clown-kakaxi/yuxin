Ext.onReady(function() {
	var shareFlagStore1 =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 0, '私有' ],
				[ 1, '全行共享' ],[ 2, '本机构共享' ] , [ 3, '辖内机构共享' ]]
	});
	
	var typeStore1 =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '汽车金融供应链' ],
				[ 2, '日化用品金融供应链' ],[ 3, '家用电器金融供应链' ] , [ 4, '其他' ]]
	});
	
	 var baseForm = new Ext.form.FormPanel({
		 height : 430,
		 width : document.body.clientWidth-200,
         frame : true,
         buttonAlign : "center",
         autoScroll : true,
         labelWidth : 100,
         items : [{ 
			   xtype:'fieldset',
	           title: '供应链基本信息', 
				layout : 'column',
             items : [ {
                 columnWidth : .33,
                 layout : 'form',
                 items : [ {
                     xtype : 'textfield',
                     labelStyle : 'text-align:right;',
                     fieldLabel : '<span style="color:red">*</span>供应链名称',
                     allowBlank : false,
                     value:name,
                     name : 'custBaseName',
                     anchor : '99%'
                 }]
             },{ columnWidth : .33,
                 layout : 'form',
            	 items:[{
						store : shareFlagStore1,
						xtype : 'combo', 
						resizable : true,
						fieldLabel : '<span style="color:red">*</span>共享范围',
						hiddenName : 'shareFlag',
						name : 'shareFlag',
						valueField : 'key',
						labelStyle : 'text-align:right;',
						displayField : 'value',
						allowBlank : false,
						mode : 'local',
						editable :false,
						value:'1',
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '99%'
					}]
             },{
                 columnWidth : .33,
                 layout : 'form',
                 items : [{
						store : typeStore1,
						xtype : 'combo', 
						resizable : true,
						fieldLabel : '<span style="color:red">*</span>供应链分类',
						hiddenName : 'groupType',
						name : 'groupType',
						valueField : 'key',
						allowBlank : false,
						labelStyle : 'text-align:right;',
						displayField : 'value',
						mode : 'local',
						editable :false,
						value:'1',
						forceSelection : true,
						triggerAction : 'all',
						emptyText : '请选择',
						anchor : '99%'
					}]
             },{
                 columnWidth : 1,
                 layout : 'form',
                 items : [ {
                     xtype : 'textarea',
                     labelStyle : 'text-align:right;',
                     fieldLabel : '供应链描述',
                     name : 'custBaseDesc',
                     value:'演示使用供应链',
                     anchor : '95%'
                 } ]
             } ]
         }],
         buttonAlign : "center",
 		buttons:[{
 			text : '保存',
 			handler : function() {
 	                	Ext.Msg.alert("提示", "操作成功!");
 	                }
 			}]
 	});
 	
	
	var view=new Ext.Panel({
		 renderTo:oCustInfo.view_source,		 
		 height : document.body.clientHeight - 30,
			autoScroll : true,
			items : [baseForm]
	});
	
});