














debugger;
var custContrastRecord = Ext.data.Record.create(
		[
		 {name:'custId',mapping:'CUST_ID'},
		 {name:'custZzdm',mapping:'CUST_ZZDM'},
		 {name:'custName',mapping:'CUST_NAME'}
		 ]
);

var custContrastStore = new Ext.data.Store({
//	restful : true,
//	proxy : new Ext.data.HttpProxy({
//		url : basepath + '/blocMemberShowList.json?groupNo=' + parent.document.getElementById("groupNo").value
//	}),
	reader : new Ext.data.JsonReader({
//										successProperty : 'success',
//										idProperty : 'id',
//										messageProperty : 'message',
										root : 'rows',
										totalProperty : 'num'
									 }, 
									 custContrastRecord
	)
});

var memberData = {
		TOTALCOUNT : 3,
		rows : [ {
			"CUST_ID" : "3098091",
			"CUST_NAME" : "集团对外担保用户",
			"CUST_ZZDM" : "DG3098091"
		}, {
			"CUST_ID" : "3098092",
			"CUST_NAME" : "擎宇科技有限公司",
			"CUST_ZZDM" : "DG3098092"
		}, {
			"CUST_ID" : "3098093",
			"CUST_NAME" : "圣杰科技有限公司",
			"CUST_ZZDM" : "DG3098093"
		}, {
			"CUST_ID" : "3098094",
			"CUST_NAME" : "俊材商贸有限公司",
			"CUST_ZZDM" : "DG3098094"
		}, {
			"CUST_ID" : "3098095",
			"CUST_NAME" : "建辉科技有限公司",
			"CUST_ZZDM" : "DG3098095"
		} ]
	};
custContrastStore.loadData(memberData);




 /*************************************列模型***********************************************/
 // 定义自动当前页行号
 var rownum = new Ext.grid.RowNumberer( {
     header : 'No.',
     width : 28
 });
 var sm = new Ext.grid.CheckboxSelectionModel();
 var custContrastColumns = new Ext.grid.ColumnModel([rownum, sm, 
                                     				{ header:'客户编号',dataIndex:'custId',sortable:true,width:150},
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
//        bbar:bbar,
        viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
	    },
	    loadMask : {
		  msg : '正在加载表格数据,请稍等...'
	    }
 });

// "客户类别"下拉框
	var chanceCategoryStore = new Ext.data.Store({
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
				fieldLabel : '产品名称',
				name : 'custName',
				readOnly:true,
				value:'金创黄金(100g)',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '客户ID',
				name : 'custId',
				hidden:true,
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '产品分类名称',
				name : 'custName',
				readOnly:true,
				value:'黄金',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}, {
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
			}), */ {
				xtype : 'textfield',
				fieldLabel : '期限',
				name : 'custContactName',
				readonly : true,
				value:'70(天)',
				anchor : '90%'
			}, {
				xtype : 'textfield',
				fieldLabel : '产品编号',
				name : 'custContactName',
				readonly : true,
				value:'PD20098912',
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
      title : '产品目标客户列表',
      collapsed:false,
      collapsible : true,
      layout : 'fit',
      buttonAlign : "center",
      items : [custContrastGrid]
  }],
	buttons : [ {
		text : '保存',
		handler : function(){
		Ext.Msg.alert('系统提示','操作成功');
		addBusiOpporWindow.hide();
		
	}
	}, {
		text : '提交',
		handler :  function(){
		Ext.Msg.alert('系统提示','操作成功');
		addBusiOpporWindow.hide();
	}
	}, {
		text : '关闭',
		handler : function() {
			addBusiOpporWindow.hide();
		}
	} ],
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