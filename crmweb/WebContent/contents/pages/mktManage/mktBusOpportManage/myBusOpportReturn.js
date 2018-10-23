/**
 * 营销管理->商机管理->我的商机->商机退回
 * 入口JS文件
 * wzy，2013-02-21
 */

//定义 商机退回窗口 From表单
var busiOpportReturnForm = new Ext.FormPanel({
	labelWidth : 100,
	height : 250,
	frame : true,
	autoScroll : true,
	labelAlign : 'right',
	buttonAlign : "center",
	items : [{
		layout : 'form',
		items : [{
			xtype : 'textarea',
			fieldLabel : '退回原因',
			name : 'OPR_CONTENT',
			id : 'opr_content_close',
			anchor : '95%'
		}]
	}],
	buttons:[{
  		text:'退回',
  		handler:function(){
	  		if(!busiOpportReturnForm.getForm().isValid()){
	  			Ext.Msg.alert('提示','输入信息有误，请重新输入！');
	  			return false;
	  		}
	  		Ext.Msg.alert('提示', '退回成功！');
	  		busiOpportReturnWindow.hide();
	  	}
  	},{
  		text: '取消',
	  	handler:function(){
	  		busiOpportReturnWindow.hide();
	  	}
  	}]
});

// 定义 商机退回 窗口
var busiOpportReturnWindow = new Ext.Window( {
	title : '商机退回',
	plain : true,
	layout : 'fit',
	width : 500,
	height : 200,
	resizable : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : 'right',
	border : false,
	constrain : true,
	items : [ busiOpportReturnForm ]
});

// 打开 商机退回 窗口
function busiOpportReturnWindowInit() {
	busiOpportReturnForm.getForm().reset();
	busiOpportReturnWindow.show();
}