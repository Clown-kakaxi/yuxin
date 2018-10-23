/**
 * @description 客户卡片优惠信息
 * @author likai
 * @since 2014-09-09
 */

imports([
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);

Ext.QuickTips.init();//提示信息
var _custId;

var needGrid = false;
WLJUTIL.suspendViews=false;  //自定义面板是否浮动

var fields = [//形式FIELDS
	{name : 'TEST', text : 'TEST', resutlWidth: 100}
];
//
///**
// * 中华航空会籍信息
// */
//var hkhjrownum = new Ext.grid.RowNumberer( {
//	header : 'No.',
//	width : 35
//});
//var hkhjsm = new Ext.grid.CheckboxSelectionModel();
//var hkhjcm = new Ext.grid.ColumnModel( [
//    hkhjrownum,hkhjsm,
//    {header:'主键ID', dataIndex:'ID', hidden: true},
//    {header:'客户编号', dataIndex:'CUST_ID', sortable:true, align:'left', width : 180},
//    {header:'客户航空意外保险生效日期', dataIndex:'INSURANCE_START_DATE', sortable:true, align:'left', width : 180},
//    {header:'中华航空会籍银行卡号', dataIndex:'CARD_NO', sortable:true, align:'left', width : 180},
//    {header:'中华航空金卡会籍卡号', dataIndex:'MEMBER_CARD_NO', sortable:true, align:'left', width : 180},
//    {header:'中华航空金卡会籍有效日期', dataIndex:'MEMBER_USING_DATE', sortable:true, align:'left', width : 180}
//]);
//var hkhjstore = new Ext.data.Store( {
//	restful : true,
//	proxy : new Ext.data.HttpProxy( {
//		url : basepath + '/acrmACardService.json?custId='+_custId,
//		method: 'POST',
//		failure : function(response) {//状态码解码.
//			var resultArray = Ext.util.JSON
//					.decode(response.status);
//			if (resultArray == 403) {
//				Ext.Msg.alert('提示', response.responseText);
//			}
//		}
//	}),
//	reader : new Ext.data.JsonReader( {
//		successProperty : 'success',
//		totalProperty : 'json.count',
//		root : 'json.data'
//	}, [
//		{name : 'ID'},
//	    {name : 'CUST_ID'},
//		{name : 'INSURANCE_START_DATE'},
//		{name : 'CARD_NO'},
//		{name : 'MEMBER_CARD_NO'},
//		{name : 'MEMBER_USING_DATE'}
//	])
//});
//hkhjstore.load();
//
///*以下是combo-分页代码*/
//var hkhjpagesize_combo = new Ext.form.ComboBox({//“一页几行”下拉选
//	name : 'pagesize',
//	triggerAction : 'all',
//	mode : 'local',
//	store : new Ext.data.ArrayStore({//页行下拉选的 store
//		fields : [ 'value', 'text' ],
//		data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
//				[ 100, '100条/页' ], [ 250, '250条/页' ], [ 500, '500条/页' ] ]
//	}),
//	valueField : 'value',
//	displayField : 'text',
//	value : '20',
//	editable : false,
//	width : 85
//});
//hkhjpagesize_combo.on("select", function(comboBox) { //激发时...
//	mtbbar.pageSize = parseInt(hkhjpagesize_combo.getValue()), //bbar显示进程
//	mtstore.reload({//grid更新显示进程
//		params : {
//			start : 0,
//			limit : parseInt(hkhjpagesize_combo.getValue())//就是number
//		}
//	});
//});
//
//var hkhjnumber = parseInt(hkhjpagesize_combo.getValue());
//
//var hkhjbbar = new Ext.PagingToolbar({//箭头前进后退一页, 为内置
//	pageSize : hkhjnumber,//对应number
//	store : hkhjstore,//正store
//	displayInfo : true,
//	displayMsg : '显示{0}条到{1}条,共{2}条',
//	emptyMsg : "没有符合条件的记录",
//	items : [ '-', '&nbsp;&nbsp;', hkhjpagesize_combo ]//“一页几行”下拉选
//});
///*combo-分页代码   到此*/
//var hkhjGrid = new Ext.grid.GridPanel( {
//	title : '中华航空会籍信息',
//	collapsible : true,
//	frame : true,
//	store : hkhjstore, // 数据存储
//	stripeRows : true, // 斑马线
//	cm : hkhjcm, // 列模型
//	sm : hkhjsm,
//	height : 200,
//	bbar : hkhjbbar,
//	tbar: [{
//    	id : 'addTarget1Form',
//        text: '新增',
////        hidden:JsContext.checkGrant('privateFmyMemberInfo_create'),
//        handler: function() {
//            add_form1.form.reset();
//            add_form1.getForm().findField('CUST_ID').setValue(_custId);
//            showCustomerViewByTitle('新增信息1');
//        }
//    },{
//    	id : 'modTarget1Form',
//        text: '修改',
////        hidden:JsContext.checkGrant('privateFmyMemInfo_modify'),
//        handler: function() {
//           if(hkhjGrid.getSelectionModel().getSelected() == null){
//        		Ext.Msg.alert('提示','请选择一条数据!');
//				return false;
//        	}
//            mod_form1.form.reset();
//            var record = hkhjGrid.getSelectionModel().getSelected();
//            mod_form1.getForm().findField('ID').setValue(record.data.ID);
//            mod_form1.getForm().findField('CUST_ID').setValue(record.data.CUST_ID);
//            mod_form1.getForm().findField('INSURANCE_START_DATE').setValue(record.data.INSURANCE_START_DATE);
//            mod_form1.getForm().findField('CARD_NO').setValue(record.data.CARD_NO);
//            mod_form1.getForm().findField('MEMBER_CARD_NO').setValue(record.data.MEMBER_CARD_NO);
//            mod_form1.getForm().findField('MEMBER_USING_DATE').setValue(record.data.MEMBER_USING_DATE);
//            showCustomerViewByTitle('修改信息1');
//        }
//    },{
//    	id : 'delTarget1Form',
//        text: '删除',
////        hidden:JsContext.checkGrant('privateFmyMemInfo_delete'),
//        handler: function() {
//        	if(hkhjGrid.getSelectionModel().getSelected() == null){
//        		Ext.Msg.alert('提示','请选择一条数据!');
//				return false;
//        	}
//			Ext.MessageBox.confirm('提示','确定删除吗？',function(buttonId){
//				if(buttonId.toLowerCase() == "no"){
//					return false;
//				}
//				var record = sosGrid.getSelectionModel().getSelected();
//				var id = record.data.ID;
//				Ext.Ajax.request({
//			    	url : basepath + '/acrmACardService!batchDel.json',  
//			    	params : {
//						id : id
//					},
//                    success : function(){
//                        sosStore.load({
//		                    params:{
//		                    	id: id
//		                    }
//	                    });
//                        Ext.Msg.alert('提示', '删除成功');
//                    },
//                    failure : function(){
//                        Ext.Msg.alert('提示', '删除失败');
//                    }
//                });
//			})
//        }
//    },new Com.yucheng.bob.ExpButton({
////		hidden:JsContext.checkGrant('privateFmyMemInfo_excel'),
//      formPanel : 'searchCondition',
//      url :  basepath + '/acrmACardService.json?custId='+_custId
//  })
//    ],
//	loadMask : {
//		msg : '正在加载表格数据,请稍等...'
//	}
//});
//
//var add_form1 = new Ext.form.FormPanel({
//    labelWidth: 100,	// 标签宽度
//    frame: true,	// 是否渲染表单面板背景色
//    labelAlign: 'middle',	// 标签对齐方式
//    buttonAlign: 'center',
//    items: [{
//    	layout:'column',
//    	items:[{
//    		columnWidth: 0.5,
//	        layout: 'form',
//	        items: [
//	        	{xtype:'textfield', fieldLabel:'客户编号', name:'CUST_ID', labelStyle: 'text-align:right;',anchor: '85%',allowBlank: false, readOnly:true, cls:'x-readOnly'},
//			    {xtype:'datefield', fieldLabel:'客户航空意外保险生效日期', name:'INSURANCE_START_DATE', labelStyle: 'text-align:right;',anchor: '85%'},
//			    {xtype:'textfield', fieldLabel:'中华航空会籍银行卡号', name:'CARD_NO', labelStyle: 'text-align:right;',anchor: '85%'}
//			    
//       		]
//    	},{
//    		columnWidth: 0.5,
//	        layout: 'form',
//	        items: [
//			    {xtype:'textfield', fieldLabel:'中华航空金卡会籍卡号', name:'MEMBER_CARD_NO', labelStyle: 'text-align:right;',anchor: '85%'},
//			    {xtype:'datefield', fieldLabel:'中华航空金卡会籍有效日期', name:'MEMBER_USING_DATE', labelStyle: 'text-align:right;',anchor: '85%'}
//       		]
//    	}]
//    	}
//    ],
//    buttons: [{
//        text: '保存',
//        handler: function() {
//        	if (!add_form.getForm().isValid()) {
//        		Ext.MessageBox.alert('提示', '输入错误,请重新输入！');
//        		return false;
//        	}
//        	var commintData = translateDataKey(add_form.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);//全部获取
//            Ext.Ajax.request({
//                url: basepath + '/acrmACardService!saveData.json',
//                method: 'POST',
//                params : commintData,
//                success: function(response) {
//                   	 Ext.Msg.alert('提示', '操作成功！');
//                     add_form.form.reset();
//                     fmyStore.load({
//                         params: {
//                            custId: commintData.custId
//                         }
//                     });
//                     showCustomerViewByTitle('优惠信息面板');
//                },
//                failure: function(){
//                	Ext.Msg.alert('提示', '操作失败！');
//                }
//            });
//        }
//    },{
//        text: '返回',
//        handler: function() {
//            showCustomerViewByTitle('优惠信息面板');
//        }
//    }]
//});
//
//var mod_form1 = new Ext.form.FormPanel({
//    labelWidth: 100,	// 标签宽度
//    frame: true,	// 是否渲染表单面板背景色
//    labelAlign: 'middle',	// 标签对齐方式
//    buttonAlign: 'center',
//    items: [{
//    	layout:'column',
//    	items:[{
//    		columnWidth: 0.5,
//	        layout: 'form',
//	        items: [
//	      		{xtype:'textfield', fieldLabel:'主键ID', name:'ID', labelStyle: 'text-align:right;',anchor: '85%',hidden: true},
//	        	{xtype:'textfield', fieldLabel:'客户编号', name:'CUST_ID', labelStyle: 'text-align:right;',anchor: '85%',allowBlank: false, readOnly:true, cls:'x-readOnly'},
//			    {xtype:'datefield', fieldLabel:'客户航空意外保险生效日期', name:'INSURANCE_START_DATE', labelStyle: 'text-align:right;',anchor: '85%'},
//			    {xtype:'textfield', fieldLabel:'中华航空会籍银行卡号', name:'CARD_NO', labelStyle: 'text-align:right;',anchor: '85%'}
//			    
//       		]
//    	},{
//    		columnWidth: 0.5,
//	        layout: 'form',
//	        items: [
//			    {xtype:'textfield', fieldLabel:'中华航空金卡会籍卡号', name:'MEMBER_CARD_NO', labelStyle: 'text-align:right;',anchor: '85%'},
//			    {xtype:'datefield', fieldLabel:'中华航空金卡会籍有效日期', name:'MEMBER_USING_DATE', labelStyle: 'text-align:right;',anchor: '85%'}
//       		]
//    	}]
//    	}
//    ],
//    buttons: [{
//        text: '保存',
//        handler: function() {
//        	if (!add_form.getForm().isValid()) {
//        		Ext.MessageBox.alert('提示', '输入错误,请重新输入！');
//        		return false;
//        	}
//        	var commintData = translateDataKey(add_form.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);//全部获取
//            Ext.Ajax.request({
//                url: basepath + '/acrmACardService!saveData.json',
//                method: 'POST',
//                params : commintData,
//                success: function(response) {
//                   	 Ext.Msg.alert('提示', '操作成功！');
//                     add_form.form.reset();
//                     fmyStore.load({
//                         params: {
//                            custId: commintData.custId
//                         }
//                     });
//                     showCustomerViewByTitle('优惠信息面板');
//                },
//                failure: function(){
//                	Ext.Msg.alert('提示', '操作失败！');
//                }
//            });
//        }
//    },{
//        text: '返回',
//        handler: function() {
//            showCustomerViewByTitle('优惠信息面板');
//        }
//    }]
//});

/**
 *  钻石卡SOS接机服务信息
 */

var sosrownum = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 35
});
var sossm = new Ext.grid.CheckboxSelectionModel();
var soscm = new Ext.grid.ColumnModel( [
    sosrownum,sossm,
    {header:'主键ID', dataIndex:'ID', align:'left', width : 160, hidden:true},
    {header:'核心客户号', dataIndex:'CUST_CORE_ID', sortable:true, align:'left', width : 160},
    {header:'客户名称', dataIndex:'CUST_NAME', sortable:true, align:'left', width : 160},
    {header:'服务使用日', dataIndex:'SERVICE_DAY', sortable:true, align:'left', width : 160},
    {header:'本周期可免费使用次数', dataIndex:'SERVICE_TIMES', sortable:true, align:'left', width : 160},
    {header:'剩余次数', dataIndex:'SERVICE_REMNANT', sortable:true, align:'left', width : 160},
    {header:'本周期服务起始日期', dataIndex:'SERVICE_STARTTIME', sortable:true, align:'left', width : 160},
    {header:'本周期服务截止日期', dataIndex:'SERVICE_ENDTIME', sortable:true, align:'left', width : 160}
]);

var sosstore = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/acrmFSosService.json?custId='+_custId,
		method: 'POST',
		failure : function(response) {//状态码解码.--报错码
			var resultArray = Ext.util.JSON
					.decode(response.status);
			if (resultArray == 403) {
				Ext.Msg.alert('提示', response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader( {
		successProperty : 'success',
		root : 'json.data'
	}, [ 
	    {name : 'ID'},
	    {name : 'CUST_CORE_ID'},
		{name : 'CUST_NAME'},
		{name : 'SERVICE_DAY'},
		{name : 'SERVICE_TIMES'},
		{name : 'SERVICE_REMNANT'},
		{name : 'SERVICE_STARTTIME'},
		{name : 'SERVICE_ENDTIME'}
	])
});
sosstore.load();

/*以下是combo-分页代码*/
var sospagesize_combo = new Ext.form.ComboBox({//“一页几行”下拉选
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({//页行下拉选的 store
		fields : [ 'value', 'text' ],
		data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
				[ 100, '100条/页' ], [ 250, '250条/页' ], [ 500, '500条/页' ] ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : '20',
	editable : false,
	width : 85
});
sospagesize_combo.on("select", function(comboBox) { //激发时...
	sosbbar.pageSize = parseInt(sospagesize_combo.getValue()), //bbar显示进程
	sosstore.reload({//grid更新显示进程
		params : {
			start : 0,
			limit : parseInt(sospagesize_combo.getValue())//就是number
		}
	});
});

var sosnumber = parseInt(sospagesize_combo.getValue());

var sosbbar = new Ext.PagingToolbar({//箭头前进后退一页, 为内置
	pageSize : sosnumber,//对应number
	store : sosstore,//正store
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', sospagesize_combo ]//“一页几行”下拉选
});
/*combo-分页代码   到此*/

var sosGrid = new Ext.grid.GridPanel( {
	title : '钻石卡SOS接机服务信息',
	collapsible : true,
	frame : true,
	store : sosstore, // 数据存储
	stripeRows : true, // 斑马线
	cm : soscm, // 列模型
	sm : sossm,
	height : 500,
	bbar : sosbbar,
	tbar: [{
    	id : 'modTargetForm',
        text: '修改',
        handler: function() {
           if(sosGrid.getSelectionModel().getSelected() == null){
        		Ext.Msg.alert('提示','请选择一条数据!');
				return false;
        	}
            mod_form.form.reset();
            var record = sosGrid.getSelectionModel().getSelected();
            mod_form.getForm().findField('ID').setValue(record.data.ID);
            mod_form.getForm().findField('CUST_CORE_ID').setValue(record.data.CUST_CORE_ID);
            mod_form.getForm().findField('CUST_NAME').setValue(record.data.CUST_NAME);
            mod_form.getForm().findField('SERVICE_DAY').setValue(record.data.SERVICE_DAY);
            mod_form.getForm().findField('SERVICE_TIMES').setValue(record.data.SERVICE_TIMES);
            mod_form.getForm().findField('SERVICE_REMNANT').setValue(record.data.SERVICE_REMNANT);
            mod_form.getForm().findField('SERVICE_STARTTIME').setValue(record.data.SERVICE_STARTTIME);
            mod_form.getForm().findField('SERVICE_ENDTIME').setValue(record.data.SERVICE_ENDTIME);
            showCustomerViewByTitle('修改信息');
        }
    },{
    	id : 'delTargetForm',
        text: '删除',
        handler: function() {
        	if(sosGrid.getSelectionModel().getSelected() == null){
        		Ext.Msg.alert('提示','请选择一条数据!');
				return false;
        	}
			Ext.MessageBox.confirm('提示','确定删除吗？',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return false;
				}
				var record = sosGrid.getSelectionModel().getSelected();
				var id = record.data.ID;
				Ext.Ajax.request({
			    	url : basepath + '/acrmFSosService!batchDestroy.json',  
			    	params : {
						id : id
					},
                    success : function(){
                        sosstore.load({
		                    params:{
		                    	id: id
		                    }
	                    });
                        Ext.Msg.alert('提示', '删除成功');
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '删除失败');
                    }
                });
			})
        }
    },new Com.yucheng.bob.ExpButton({
      formPanel : 'searchCondition',
      url :  basepath + '/acrmFSosService.json?custId='+_custId
  })
    ],
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var mod_form = new Ext.form.FormPanel({
    labelWidth: 100,	// 标签宽度
    frame: true,	// 是否渲染表单面板背景色
    labelAlign: 'middle',	// 标签对齐方式
    buttonAlign: 'center',
    items: [{
    	layout:'column',
    	items:[{
    		columnWidth: 0.5,
	        layout: 'form',
	        items: [
	      		{xtype:'textfield', fieldLabel:'主键ID', name:'ID', labelStyle: 'text-align:right;',anchor: '85%',hidden: true},
	        	{xtype:'textfield', fieldLabel:'核心客户号', name:'CUST_CORE_ID', labelStyle: 'text-align:right;',anchor: '85%',allowBlank: false, readOnly:true, cls:'x-readOnly'},
			    {xtype:'textfield', fieldLabel:'客户名称', name:'CUST_NAME', labelStyle: 'text-align:right;',anchor: '85%',allowBlank: false, readOnly:true, cls:'x-readOnly'},
			    {xtype:'datefield', fieldLabel:'服务使用日', name:'SERVICE_DAY', labelStyle: 'text-align:right;',anchor: '85%'},
			    {xtype:'numberfield', fieldLabel:'本周期可免费使用次数', name:'SERVICE_TIMES', labelStyle: 'text-align:right;',anchor: '85%'}
			    
       		]
    	},{
    		columnWidth: 0.5,
	        layout: 'form',
	        items: [
			    {xtype:'numberfield', fieldLabel:'剩余次数', name:'SERVICE_REMNANT', labelStyle: 'text-align:right;',anchor: '85%'},
			    {xtype:'datefield', fieldLabel:'本周期服务起始日期', name:'SERVICE_STARTTIME', labelStyle: 'text-align:right;',anchor: '85%'},
			    {xtype:'datefield', fieldLabel:'本周期服务截止日期', name:'SERVICE_ENDTIME', labelStyle: 'text-align:right;',anchor: '85%'}
       		]
    	}]
    	}
    ],
    buttons: [{
        text: '保存',
        handler: function() {
        	if (!mod_form.getForm().isValid()) {
        		Ext.MessageBox.alert('提示', '输入错误,请重新输入！');
        		return false;
        	}
        	var commintData = translateDataKey(mod_form.getForm().getFieldValues(),_app.VIEWCOMMITTRANS);//全部获取
            Ext.Ajax.request({
                url: basepath + '/acrmFSosService!saveData.json',
                method: 'POST',
                params : commintData,
                success: function(response) {
                   	 Ext.Msg.alert('提示', '操作成功！');
                     mod_form.form.reset();
                     sosstore.load({
                         params: {
                            custId: commintData.custId
                         }
                     });
                     showCustomerViewByTitle('优惠信息面板');
                },
                failure: function(){
                	Ext.Msg.alert('提示', '操作失败！');
                }
            });
        }
    },{
        text: '返回',
        handler: function() {
            showCustomerViewByTitle('优惠信息面板');
        }
    }]
});

/**
 * 自定义面板
 * @type 
 */
var customerView =[
	{
	title:'优惠信息面板',
	hideTitle:true,
	type: 'form',
	items:[sosGrid]
},
//	{
//  	title:'新增信息1',
//	hideTitle:true,
//	type: 'form',
//	items:[add_form1]
//},{
//  	title:'修改信息1',
//	hideTitle:true,
//	type: 'form',
//	items:[mod_form1]
//},
	{
  	title:'修改信息',
	hideTitle:true,
	type: 'form',
	items:[mod_form]
}
];
