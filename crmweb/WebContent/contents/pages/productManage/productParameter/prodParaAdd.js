//风险等级
var a2 =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 1, '1级' ],[ 2, '2级' ],[ 3, '3级' ] , [ 4, '4级' ],[5,'5级']]
});
//流动性
var a3 =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 1, '高' ],[ 2, '中' ],[ 3, '低' ]]
});
//市场方向
var a5 =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 1, '货币市场方向' ],[ 2, '固定收益市场方向' ],[ 3, '股票市场方向' ],[4,'其他市场方向']]
});

//流动性
var a6 =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 1, '是' ],[ 2, '否' ]]
});
//销售范围
var a14 =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 1, '全行' ],[ 2, '分行' ],[ 3, '支行' ],[4,'网点']]
});


var addProdParaPanel = new Ext.FormPanel({
    labelAlign:'right',
    labelWidth:80,
    buttonAlign:'center',
    bodyStyle:'padding:5px;',
    frame:true,
    items:[{
        layout:'column',border:false,labelSeparator:'：',xtype: 'fieldset',
        defaults:{layout: 'form',border:false,columnWidth:.25},
        items:[
            {items: [{xtype:'textfield',fieldLabel: '产品名称',name: 'a1',anchor:'90%',value:'理财产品信托1'}]},
            {items: [{xtype:'textfield',fieldLabel: '产品树代码',name: 'a19',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '上级分类',name: 'a13',anchor:'90%'}]},
            {items: [{xtype:'combo',fieldLabel: '风险级别',name: 'a2',anchor:'90%',store : a2,displayField : 'value',
            		  valueField : 'key',mode : 'local',emptyText:'请选择 '}]},
            {items: [{xtype:'combo',fieldLabel: '流动性',name: 'a3',anchor:'90%',store : a3,displayField : 'value',
            	      valueField : 'key',mode : 'local',emptyText:'请选择 '}]},
           
            {items: [{xtype:'textfield',fieldLabel: '收益率',name: 'a4',anchor:'90%'}]},
            {items: [{xtype:'combo',fieldLabel: '市场方向',name: 'a5',anchor:'90%',store : a5,displayField : 'value',
            	      valueField : 'key',mode : 'local',emptyText:'请选择 '}]},
            {items: [{xtype:'combo',fieldLabel: '是否推荐',name: 'a6',anchor:'90%',store : a6,displayField : 'value',
            	      valueField : 'key',mode : 'local',emptyText:'请选择 '}]},
            {items: [{xtype:'datefield',fieldLabel: '推荐结束日期',name: 'a7',anchor:'90%',format:'Y-m-d'}]},
            
            {columnWidth:1,items: [{xtype:'textarea',fieldLabel: '推荐理由',name: 'a8',anchor:'97%'}]},
           
            {items: [{xtype:'combo',fieldLabel: '是否新品',name: 'a9',anchor:'90%',store : a6,displayField : 'value',
            	      valueField : 'key',mode : 'local',emptyText:'请选择 '}]},
            {items: [{xtype:'datefield',fieldLabel: '新品结束日期',name: 'a10',anchor:'90%',format:'Y-m-d'}]},
            {items: [{xtype:'datefield',fieldLabel: '上架日期',name: 'a11',anchor:'90%',format:'Y-m-d'}]},            
            {items: [{xtype:'datefield',fieldLabel: '下架日期',name: 'a12',anchor:'90%',format:'Y-m-d'}]},
            
            
            {items: [{xtype:'combo',fieldLabel: '销售范围',name: 'a14',anchor:'90%',store : a14,displayField : 'value',
            		  valueField : 'key',mode : 'local',emptyText:'请选择 '}]},
            {items: [{xtype:'combo',fieldLabel: '是否行内优惠',name: 'a15',anchor:'90%',store : a6,displayField : 'value',
            		  valueField : 'key',mode : 'local',emptyText:'请选择 '}]},
            {items: [{xtype:'combo',fieldLabel: '是否优惠',name: 'a16',anchor:'90%',store : a6,displayField : 'value',
            		  valueField : 'key',mode : 'local',emptyText:'请选择 '}]},
            {items: [{xtype:'datefield',fieldLabel: '优惠结束日期',name: 'a18',anchor:'90%',format:'Y-m-d'}]},
            
            {columnWidth:1,items: [{xtype:'textarea',fieldLabel: '优惠信息',name: 'a17',anchor:'97%'}]}
            //{columnWidth:1,items:[appendixGridPanel2]} 附件列表
       ]
    }],
    buttons:[{text:'保存',
    	id:'saveID',
    	handler:function(){
    		Ext.Msg.alert('提示', '保存成功',function(){addProdParaWin.hide();});
    		//eduWin1.hide();//hide()隐藏窗口，close()关闭窗口
    		//eduWin2.show();
    	}
    },{
    	text:'关闭',
    	handler:function(){
    		addProdParaWin.hide();//hide()隐藏窗口，close()关闭窗口
    	}
    }]
});

//创建购车规划面板  第一步
var addProdParaWin = new Ext.Window( {
	resizable : true,
	collapsible : false,
	draggable : true,
	closeAction : 'hide',
	modal : true, // 模态窗口
	animCollapse : false,
	border : false,
	loadMask : true,
	closable : true,
	constrain : true,
	layout : 'fit',
	width : 850,
	height : 360,
	buttonAlign : "center",
	title : '产品参数维护',
	items : [ addProdParaPanel ]
});