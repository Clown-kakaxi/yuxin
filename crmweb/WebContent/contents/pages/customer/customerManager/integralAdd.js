


    var typeStore = new Ext.data.ArrayStore({
        fields:['myId','displayText'],
        data:[['1','家用电器'],['2','手机数码'],
              ['3','图书期刊'],['4','音像制品'],
              ['5','汽车用品'],['6','运动健康'],
              ['7','医疗保健'],['8','箱包服装'],
              ['9','食品饮料'],['10','优惠礼券']]});
// 新增窗口展示的from
var addProForm = new Ext.form.FormPanel({
	labelWidth : 100,
	width:800,
	height : 300,
	frame : true,
	labelAlign : 'right',
	region : 'center',
	autoScroll : true,
	buttonAlign : "center",
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .3,
			layout : 'form',
			items : [  {
				name : 'id',
				xtype : 'textfield',
				fieldLabel : '订单编号',
				hidden : true
			},{
				name : 'giftName',
				xtype : 'textfield',
				fieldLabel : '礼品名称',
				anchor : '90%'

				//hidden : true
			},{
				name : 'giftPoint',
				xtype : 'numberfield',
				fieldLabel : '单位礼品积分',
				anchor : '90%'


			},{
				name : 'orderStatus',
				id : 'orderStatus',
				xtype : 'textfield',
				fieldLabel : '订单状态',
				hidden:true,
				anchor : '90%'

				//hidden : true
			},	   {
				name : 'custId',
				xtype : 'textfield',
				fieldLabel : '客户号',
				anchor : '90%'}]
		}, {
			columnWidth : .3,
			layout : 'form',
			items : [{
				store : typeStore,
				xtype : 'combo', 
				resizable : true,
				name : 'giftType',
				hiddenName : 'giftType',
				fieldLabel : '礼品种类',
				valueField : 'myId',
				displayField : 'displayText',
				mode : 'local',
				editable : false,
				typeAhead : true,
				forceSelection : true,
				triggerAction : 'all',
				emptyText : '请选择',
				selectOnFocus : true,
				width : '100',
				anchor : '90%'
			},{
				name : 'exchangeCount',
				xtype : 'numberfield',
				fieldLabel : '兑换数量',
				anchor : '90%'
			},{
				name : 'custName',
				xtype : 'textfield',
				fieldLabel : '客户名称',
				anchor : '90%'
			}]
		},{
			layout : 'form',
			columnWidth : .3,
			items : [ {
				name : 'orderDate',
				xtype : 'textfield',
				fieldLabel : '订单生成日期',
				value : DateFor(new Date()),
				anchor : '90%'
			},
			          
		
				{
					name : 'exchangeTotle',
					xtype : 'numberfield',
					fieldLabel : '消费积分总额',
					anchor : '90%'
				},{
					name : 'custMgr',
					xtype : 'textfield',
					fieldLabel : '客户经理',
					anchor : '90%'
						
				  }]
		     }
		]
	}]

});
var addProWindow = new Ext.Window({
	title : '新增积分兑换',
	plain : true,
	layout : 'fit',
	width : 900,
	height : 220,
	resizable : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	loadMask : true,
	maximizable : true,
	collapsible : true,
	titleCollapse : true,
	buttonAlign : 'center',
	border : false,
	items : [ addProForm ],
	buttons : [

				{

					text : '保  存',
					handler : function() {
		
						addProWindow.hide();
						addProForm.getForm().reset();
					}

				}, {
					text : '取  消',
					handler : function() {
						addProWindow.hide();
					}
				} ]
});