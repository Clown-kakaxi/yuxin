/**
 * 营销管理->商机管理->商机池：新增商机表单及窗体定义JS文件；wzy；2013-02-22
 */


		// "客户类别"下拉框
debugger;	var chanceCategoryStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var chanceStatStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=BUSI_CHANCE_TYPE'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var custStatStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=CUSTOMER_STATUS'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
// 新增商机窗口From表单
var addBusiOpporForm = new Ext.FormPanel({
	labelWidth : 100,
	height : 200,
	frame : true,
//	autoScroll : true,
	labelAlign : 'right',
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .5,
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '*商机名称',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				name : 'opporName',
				anchor : '90%'
			}/*, new Com.yucheng.crm.common.ProductManage({
				xtype : 'productChoose',
				fieldLabel : '*商机产品',
				labelStyle : 'text-align:right;',
				name : 'prodName',
				hiddenName : 'prodId',
				singleSelect : false,
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '90%'
			})*/, {
				xtype : 'textfield',
				fieldLabel : '商机产品',
				name : 'prodId',
				hidden:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '产品名称',
				name : 'prodName',
				hidden:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'datefield',
				fieldLabel : '*商机开始日期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporStartDate',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '营销活动名称',
				name : 'activityQuery',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			} , {
				xtype : 'textfield',
				fieldLabel : '客户名称',
				name : 'custName',
				readOnly:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '客户ID',
				name : 'custId',
				hidden:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, new Ext.form.ComboBox({
				hiddenName : 'custType',
				fieldLabel : '客户类型',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : custStatStore,
				readOnly:true,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				emptyText : '请选择 ',
				resizable : true,
				readonly : true,
				anchor : '90%'
			}), {
				xtype : 'numberfield',
				fieldLabel : '预计金额(元)',
				name : 'planAmount',
				labelStyle : 'text-align:right;',
				value : '0',
				anchor : '90%'
			} ]
		}, {
			columnWidth : .5,
			layout : 'form',
			items : [ new Ext.form.ComboBox({
				hiddenName : 'opporType',
				fieldLabel : '商机类型',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceStatStore,
				displayField : 'value',
				valueField : 'key',
				mode : 'local',
				forceSelection : true,
				emptyText : '请选择 ',
				resizable : true,
				anchor : '90%'
			}), {
				xtype : 'datefield',
				fieldLabel : '*商机有效期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporDueDate',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '90%'
			}, {
				xtype : 'datefield',
				fieldLabel : '*商机完成日期',
				format : 'Y-m-d',
				editable : true,
				name : 'opporEndDate',
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '90%'
			},/* new Com.yucheng.bcrm.common.MktTaskTargetCommonQuery({
				xtype : 'taskTargetQuery',
				fieldLabel : '营销任务指标',
				labelStyle : 'text-align:right;',
				name : 'mktTargetName',
				hiddenName : 'mktTargetId',
				singleSelect : true,
				anchor : '90%'
			}), */new Ext.form.ComboBox({
				hiddenName : 'custCategory',
				fieldLabel : '客户类型',
				labelStyle : 'text-align:right;',
				triggerAction : 'all',
				store : chanceCategoryStore,
				displayField : 'value',
				valueField : 'key',
				readOnly:true,
				mode : 'local',
				forceSelection : true,
				emptyText : '请选择 ',
				resizable : true,
				readonly : true,
				anchor : '90%'
			}), {
				xtype : 'textfield',
				fieldLabel : '客户联系人',
				name : 'custContactName',
				readonly : true,
				anchor : '90%'
			}, {
				xtype : 'numberfield',
				fieldLabel : '费用预算(元)',
				name : 'planCost',
				value : '0',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			} ]
		} ]
	}, {
		layout : 'form',
		items : [ {
			xtype : 'textarea',
			fieldLabel : '商机内容',
			name : 'opporContent',
			anchor : '95%'
		}, {
			xtype : 'textarea',
			fieldLabel : '商机备注',
			name : 'memo',
			anchor : '95%'
		} ]
	} ]
});

var custContrastRecord = Ext.data.Record.create(
		[
		 {name:'custid',mapping:'CUST_ID'},
		 {name:'custZzdm',mapping:'CUST_ZZDM'},
		 {name:'custName',mapping:'CUST_NAME'}
		 ]
);
var custContrastReader = new Ext.data.JsonReader(//读取jsonReader
		{
//			successProperty : 'success',
//			idProperty : 'ID',
			totalProperty : 'num',
			root:'rows'
		},custContrastRecord
);
var custContrastStore = new Ext.data.Store({//产品对照关系store
//        restful : true, 
//        proxy : new Ext.data.HttpProxy({ 
//        	url:basepath+'/mktactivityrelateinfoaction.json',
//        	method:'get'
//        }),
		reader:custContrastReader	
});

var memberData = {
		TOTALCOUNT : 3,
		rows : [ {
			"CUST_ID" : "34521",
			"CUST_NAME" : "集团对外担保用户",
			"CUST_ZZDM" : "DG09865"
		}, {
			"CUST_ID" : "34521",
			"CUST_NAME" : "擎宇科技有限公司",
			"CUST_ZZDM" : "DG09865"
		}, {
			"CUST_ID" : "34521",
			"CUST_NAME" : "圣杰科技有限公司",
			"CUST_ZZDM" : "DG09865"
		}, {
			"CUST_ID" : "34521",
			"CUST_NAME" : "俊材商贸有限公司",
			"CUST_ZZDM" : "DG09865"
		}, {
			"CUST_ID" : "34521",
			"CUST_NAME" : "建辉科技有限公司",
			"CUST_ZZDM" : "DG09865"
		} ]
	};
custContrastStore.loadData(memberData);

// 每页显示条数下拉选择框
var pagesize_combo = new Ext.form.ComboBox({
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
		fields : [ 'value', 'text' ],
		data : [ [ 10, '10条/页' ], [ 20, '20条/页' ],
		         [ 50, '50条/页' ],[ 100, '100条/页' ]  ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : '100',
	resizable : true,
	width : 85
});

//custContrastStore.reload();
// 改变每页显示条数reload数据
pagesize_combo.on("select", function(comboBox) {
	bbar.pageSize = parseInt(pagesize_combo.getValue()),
	custContrastStore.reload({
		params : {
			start : 0,
			limit : parseInt(pagesize_combo.getValue())
		}
	});
});

var bbar= new Ext.PagingToolbar({//gridTable 底部工具栏	
		pageSize : parseInt(pagesize_combo.getValue()),
		store : custContrastStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
});



 /*************************************列模型***********************************************/
 // 定义自动当前页行号
 var rownum = new Ext.grid.RowNumberer( {
     header : 'No.',
     width : 28
 });
 var sm = new Ext.grid.CheckboxSelectionModel();
 var custContrastColumns = new Ext.grid.ColumnModel([rownum, sm, 
                                     				{ header:'客户编号',dataIndex:'custId',sortable:true,hidden:true},
                                     				{ header:'客户名称',dataIndex:'custName',sortable:true,width:150},
                                     				{ header:'组织机构代码',dataIndex:'custZzdm',sortable:true,width:150}
                                     				]
                                      );
 var custContrastGrid = new Ext.grid.EditorGridPanel({			
		store:custContrastStore, 
		frame:true,
		height : 300,
		cm:custContrastColumns,
		region:'center',
		sm:sm,
        bbar:bbar,
        viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
	    },
	    loadMask : {
		  msg : '正在加载表格数据,请稍等...'
	    }
 });


// 保存商机
// 对商机数据做临时存储，只控制必须输入“商机名称”，在提交时，判断必填项是否完全填写
function saveAddBusiOppor() {
	var opporName = addBusiOpporForm.form.findField('opporName').getValue();
	if (opporName == null || opporName == "") {
		Ext.Msg.alert('提示', '商机名称不能为空！');
		return false;
	}
	var selectStr= custContrastGrid.getSelectionModel().getSelections();
	var tempLength = custContrastGrid.getSelectionModel().getSelections().length;
	var tempProdId = '';
	var tempProdName = '';
	if(tempLength<=0){
		Ext.Msg.alert('提示','请选择关联产品');
		return false;
	}else{
		tempProdId=selectStr[0].data.prodId;	
		tempProdName=selectStr[0].data.prodName;
		for(var i = 1;i<tempLength;i++){
			tempProdId = tempProdId+','+selectStr[i].data.prodId;
			tempProdName = tempProdName+','+selectStr[i].data.prodName;
			addBusiOpporForm.form.findField('prodId').setValue(tempProdId);
			addBusiOpporForm.form.findField('prodName').setValue(tempProdName);
			
			var saveUrl = basepath + '/mktBusiOpporOperationAction!'
			+ 'saveOrUpdateBusiOppor.json';
	Ext.Ajax.request({
		url : saveUrl,
		method : 'POST',
		form : addBusiOpporForm.getForm().id,
		waitMsg : '正在保存数据,请等待...',
		success : function(response) {
			Ext.Msg.alert('提示', '保存成功！');
			store.load({
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
		},
		failure : function(response) {
			Ext.Msg.alert('提示', '保存失败！');
		}
	});
	addBusiOpporWindow.hide();
		}
	}
}

// 提交商机
function submitAddBusiOppor() {
	if (!addBusiOpporForm.getForm().isValid()) {
		Ext.Msg.alert('提示', '输入信息有误，请重新输入！');
		return false;
	}
	if (!dateCheck()) {
		return false;
	}
	var saveUrl = basepath + '/mktBusiOpporOperationAction!'
			+ 'submitBusiOppor.json';
	Ext.Ajax.request({
		url : saveUrl,
		method : 'POST',
		form : addBusiOpporForm.getForm().id,
		waitMsg : '正在保存数据,请等待...',
		success : function(response) {
			Ext.Msg.alert('提示', '提交成功！');
			store.load({
				params : {
					start : 0,
					limit : bbar.pageSize
				}
			});
		},
		failure : function(response) {
			Ext.Msg.alert('提示', '提交失败！');
		}
	});
	addBusiOpporWindow.hide();
}

// 日期判断
function dateCheck() {
	var opporStartDate = addBusiOpporForm.form.findField('opporStartDate')
			.getValue();
	var opporEndDate = addBusiOpporForm.form.findField('opporEndDate')
			.getValue();
	var opporDueDate = addBusiOpporForm.form.findField('opporDueDate')
			.getValue();
	// 1、商机“开始日期”不能晚于“完成日期”
	if (opporStartDate >= opporEndDate) {
		Ext.Msg.alert('提示', '商机“开始日期”不能晚于或等于“完成日期”！');
		return false;
	}
	// 2、商机“开始日期”不能晚于“商机有效期”
	if (opporStartDate >= opporDueDate) {
		Ext.Msg.alert('提示', '商机“开始日期”不能晚于或等于“商机有效期”！');
		return false;
	}
	// 3、商机“完成日期”不能晚于“邮寄有效期”
	if (opporEndDate > opporDueDate) {
		Ext.Msg.alert('提示', '商机“完成日期”不能晚于或等于“商机有效期”！');
		return false;
	}
	return true;
}


//定义新增窗口
var addBusiOpporWindow = new Ext.Window({
	title : '商机新增',
	plain : true,
	layout : 'fit',
	width : 750,
	height : 420,
	resizable : true,
	draggable : true,
	closable : true,
	autoScroll : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : "center",
	border : false,
	constrain : true,
	 items : [{
      xtype : 'fieldset',
      title : '新建商机基本信息',
      collapsed:false,
		 collapsible : true,
      layout : 'fit',
      buttonAlign : "center",
      items : [addBusiOpporForm]
  },{
      xtype : 'fieldset',
      title : '营销活动关联产品列表',
      collapsed:false,
      collapsible : true,
      layout : 'fit',
      buttonAlign : "center",
      items : [custContrastGrid]
  }],
//	buttons : [ {
//		text : '保存',
//		handler : saveAddBusiOppor
//	}, {
//		text : '提交',
//		handler : submitAddBusiOppor
//	}, {
//		text : '关闭',
//		handler : function() {
//			addBusiOpporWindow.hide();
//		}
//	} ],
	listeners : {
		"hide" : function() {
			addBusiOpporForm.getForm().reset();
		},
		"show" : function() {// 窗体显示时间，进行一些数据设置初始化操作
			addBusiOpporForm.getForm().reset();
		}
	}
});

// 打开 新增商机 窗口
function busiOpportAddWindowInit() {
	addBusiOpporWindow.show();
}