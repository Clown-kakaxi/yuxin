/**
 * 
 * 创建教育规划
 * @author  zkl
 * 
 */

//教育阶段
var eduStore =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 1, '幼儿园' ],[ 2, '小学' ],[ 3, '初中' ] , [ 4, '高中' ],[5,'大学'],[6,'研究生']]
});

var zcStore =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 1, '一次性支出' ],[ 2, '每年支出' ]]
});

var eduNameStore =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 11, '幼儿园一次性费用' ],[ 12, '幼儿园第年费用' ],
	         [ 21, '初中次性费用' ], [ 22, '初中第年费用' ],
	         [ 31, '高中次性费用' ], [ 32, '高中第年费用' ],
	         [ 41, '大学次性费用' ], [ 42, '大学第年费用' ],
	         [ 51, '研究生次性费用'],[ 52, '研究生第年费用' ]
	]
});

var eduNameStore1 =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 11, '幼儿园一次性费用' ],[ 12, '幼儿园第年费用' ]]
});

var eduNamestore1 =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 21, '初中次性费用' ], [ 22, '初中第年费用' ]]
});

var eduNameStore3 =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 31, '高中次性费用' ], [ 32, '高中第年费用' ]]
});

var eduNameStore4 =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 41, '大学次性费用' ], [ 42, '大学第年费用' ]]
});
var eduNameStore5 =  new Ext.data.ArrayStore({
	fields : [ 'key', 'value'  ],
	data : [ [ 51, '研究生次性费用'],[ 52, '研究生第年费用' ]]
});

//DEMO 数据
var mesgFields = [ {name : 'a0'}, {name : 'a1'}, {name : 'a2'}, {name : 'a3'}, {name : 'a4'}];
var mesgData = [
            ['幼儿园一次性费用','2013-08-26','一次性支出','23,455','0'],
            ['幼儿园每年费用','2013-08-26','每年支出','43,321','4'],
            ['小学一次性费用','2013-08-26','一次性支出','23,455','0'],
            ['小学每年费用','2013-08-26','每年支出','43,321','4'],
            ['初中一次性费用','2013-08-26','一次性支出','23,455','0'],
            ['初中每年费用','2013-08-26','每年支出','43,321','4'],
            ['高中一次性费用','2013-08-26','一次性支出','23,455','0'],
            ['高中每年费用','2013-08-26','每年支出','43,321','4']
];
//定义自动当前页行号
var num = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var sm = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true
});
var productInfoStore = new Ext.data.ArrayStore({
	fields : mesgFields,
	data : mesgData
});

//gridtable中的列定义
var eduInfoColumns = new Ext.grid.ColumnModel([num,sm,
	//new Ext.grid.RowNumberer(),
	{header:'费用名称',dataIndex:'a0',id:"productId",sortable : true},
	{header:'<center>开始时间</center>',dataIndex:'a1',id:'productName',sortable : true,align:'right'},
	{header:'支出频率',dataIndex:'a2',id:'catlName',sortable : true},	
	{header:'<center>支出金额</center>',dataIndex:'a3',id:'productStartDate',sortable : true,align:'right'},
	{header:'持续时间(年)',dataIndex:'a4',id:'productEndDate',sortable : true}   		
]);

//理财产品查询结果grid
var eduInfoGrid =  new Ext.grid.GridPanel({
	title:'教育阶段',
	id:'产品列表',
	frame:true,
	height:210,
	id:'productGrid',
	store:productInfoStore,
	region:'center',
	loadMask:true,
	cm :eduInfoColumns,
	viewConfig:{
       forceFit : true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
	},
    loadMask : {
        msg : '正在加载表格数据,请稍等...'
    },
    tbar:[{
		text:'新增',
		iconCls:'addIconCss',
		handler:function() {
			addeduWinJD.show();
		}	
	},'-',{
		text:'编辑',
		iconCls:'editIconCss',
		handler:function() {
			var selectLength = eduInfoGrid.getSelectionModel().getSelections().length;
			var selectRe = eduInfoGrid.getSelectionModel().getSelections()[0];
			if (selectLength != 1) {
				Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
			} else {
				addeduPanelJD.getForm().loadRecord(selectRe);
				addeduWinJD.show();
			}
		}
	},'-',{
		text:'删除',
		iconCls:'deleteIconCss',
		handler:function() {
			var onGrid = Ext.getCmp("eduInfoGrid").getSelectionModel();
			if (onGrid.getSelections()) {
				var records = onGrid.getSelections();// 选择行的个数 
				var recordsLen = records.length;// 得到行数组的长度
				if (recordsLen < 1) {
					Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
				} else {
					Ext.Msg.alert("系统提示信息", "删除成功！");
				}
			}
		}
	}]
});


var custQuery = new Com.yucheng.bcrm.common.CustomerQueryField({
	fieldLabel : '客户名称',
	labelWidth : 100,
	name : 'custName',
	custtype : '1',// 客户类型:1:对私,2:对公,不设默认全部
	custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
	singleSelected : true,// 单选复选标志
	editable : false,
	allowBlank : false,
	anchor : '90%',
	hiddenName : 'custId',
	value:'李晓丽'
});


//第一步
var addEduPanel = new Ext.FormPanel({
    labelAlign:'right',
    labelWidth:150,
    buttonAlign:'center',
    bodyStyle:'padding:5px;',
    frame:true,
    items:[{
        layout:'column',border:false,labelSeparator:'：',xtype: 'fieldset',
        defaults:{layout: 'form',border:false,columnWidth:.5},
        items:[
            {items: [custQuery]},
            {items: [{xtype:'textfield',fieldLabel: '规划名称',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '受教育人',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'datefield',fieldLabel: '受教育人生日',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '通胀率',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '我国居民价格指数',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '回报率',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '风险偏好',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '留学国家',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '世界各地名校教育费用',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '当前拥有的教育储备金',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '现在起每年储备教育金',name: 'xming',anchor:'90%'}]},
       {
    	   columnWidth:1,items:[eduInfoGrid]
       }]
    }],
    buttons:[{text:'计算&下一步',
    	handler:function(){
    		eduWin1.hide();//hide()隐藏窗口，close()关闭窗口
    		eduWin2.show();
    	}
    }]
});
	
	 
//创建购车规划面板  第一步
var eduWin1 = new Ext.Window( {
	resizable : false,
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
	width : 800,
	height : 460,
	buttonAlign : "center",
	title : '创建教育规划 第一步',
	items : [ addEduPanel ]
});

var comEdu = new Ext.form.ComboBox({   
	   id:'comEduID',
	   fieldLabel:'教育阶段',
	   emptyText : "请选择",
	   store : eduStore,
	   valueField : "key",//这是实际的值可以通过getValue()方法取出来
	   displayField : "value",//这是显示值可以用getRawValue()方法取出来
	   mode : "local",//这里是remote代表取得远程服务器的值，如果为local则是取得本地的值
	   editable : false,
	   allowBlank : false,
	   listeners:{
		   select :function(comEdu){
			   var stro ;
			   if(comEdu.getValue() == '1'){
				   stro  = 'eduNameStore1';
			   }else if(comEdu.getValue() == '2'){
				   stroe  = 'eduNamestore1';
			   }else if(comEdu.getValue() == '3'){
				   stro  = 'eduNameStore3';
			   }else if(comEdu.getValue() == '4'){
				   stro  = 'eduNameStore4';
			   }else if(comEdu.getValue() == '5'){
				   stro  = 'eduNameStore5';
			   }
			   if(Ext.getCmp('comeudNameID').view){
                  Ext.getCmp('comeudNameID').view.setStore(stro);
               }
			   Ext.getCmp('comeudNameID').enable();
			   store.loadData(eduNameStore5);
		   }
	   }
});

var comEduName = new Ext.form.ComboBox({   
	   id:'comeudNameID',
	   name:'a0',
	   fieldLabel:'费用名称',
	   emptyText : "请选择",
	   store : eduNameStore, 
	   valueField : "key",//这是实际的值可以通过getValue()方法取出来
	   displayField : "value",//这是显示值可以用getRawValue()方法取出来
	   mode : "local",//这里是remote代表取得远程服务器的值，如果为local则是取得本地的值
	   editable : false,
	   allowBlank : false
});

//创建教育阶段面板
var addeduPanelJD = new Ext.form.FormPanel({
    height: 200,
    width: 350,
    labelWidth: 80,
    labelAlign: "right",
    frame: true,
    defaults:{
       xtype:"textfield",
       anchor:'90%'
    },
    items: [
       comEdu,comEduName, 
       {xtype:"datefield",name: "a1", fieldLabel: "开始时间"},
       {xtype:"combo",name: "a2", fieldLabel: "支出频率",store : zcStore,displayField : 'value',valueField : 'key',mode : 'local',emptyText:'请选择 '},  
       {xtype:"textfield",name: "a3", fieldLabel: "支出金额"},
       {xtype:"textfield",name: "a4", fieldLabel: "持续时间(年)"}
    ],
    buttons:[{
    	text:"保存",
    	handler:function(){
			Ext.Msg.alert("系统提示信息", "保存成功！",function(){addeduWinJD.hide();});	
		}
	}, {text:"取消",handler:function(){addeduWinJD.hide();}}]
 });


//新增教育阶段
var addeduWinJD = new Ext.Window( {
	resizable : false,
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
	width : 350,
	height : 260,
	buttonAlign : "center",
	title : '教育阶段信息',
	items : [ addeduPanelJD ]
});


//资金流

var eduFields = [ {name : 'b0'}, {name : 'b1'}, {name : 'b2'}, {name : 'b3'}, {name : 'b4'},
                  {name : 'b5'}, {name : 'b6'}, {name : 'b7'}, {name : 'b8'}, {name : 'b9'},
                  {name : 'c0'}, {name : 'c1'}, {name : 'c2'}, {name : 'c3'}, {name : 'c4'},];
var eduData = [
            ['期末资产余额','100,000','20,009','2,334,555','22,345,555','100,000','20,009','2,334,555','22,345,555','1,000,00','20,009','2,334,555','2,2345,555','343,536','3,433,536'],
            ['投入资金','100,000','20,009','2,334,555','22,345,555','100,000','20,009','2,334,555','22,345,555','1,000,00','20,009','2,334,555','2,2345,555','343,536','3,433,536'],
            ['教育支出','100,000','20,009','2,334,555','22,345,555','100,000','20,009','2,334,555','22,345,555','1,000,00','20,009','2,334,555','2,2345,555','343,536','3,433,536'],
            ['生活支出','100,000','20,009','2,334,555','22,345,555','100,000','20,009','2,334,555','22,345,555','1,000,00','20,009','2,334,555','2,2345,555','343,536','3,433,536'],
            ['投资收益','100,000','20,009','2,334,555','22,345,555','100,000','20,009','2,334,555','22,345,555','1,000,00','20,009','2,334,555','2,2345,555','343,536','3,433,536']
];

var eduListColumns = new Ext.grid.ColumnModel([num,
	{header:'时间',dataIndex:'b0',id:"productId",sortable : true},
	{header:'2010',dataIndex:'b1',id:'productName',sortable : true},
	{header:'2011',dataIndex:'b2',id:'catlName',sortable : true},	
	{header:'2012',dataIndex:'b3',id:'productStartDate',sortable : true},
	{header:'2013',dataIndex:'b4',id:'productEndDate',sortable : true},   
	{header:'2014',dataIndex:'b5',id:'productEndDate',sortable : true},
	{header:'2015',dataIndex:'b6',id:'productEndDate',sortable : true},
	{header:'2016',dataIndex:'b7',id:'productStartDate',sortable : true},
	{header:'2017',dataIndex:'b8',id:'productEndDate',sortable : true},   
	{header:'2018',dataIndex:'b9',id:'productEndDate',sortable : true},
	{header:'2019',dataIndex:'c0',id:'productEndDate',sortable : true},
	{header:'2020',dataIndex:'c1',id:'productStartDate',sortable : true},
	{header:'2021',dataIndex:'c2',id:'productEndDate',sortable : true},   
	{header:'2022',dataIndex:'c3',id:'productEndDate',sortable : true},
	{header:'2023',dataIndex:'c4',id:'productEndDate',sortable : true}
]);

var eduStore = new Ext.data.ArrayStore({
	fields : eduFields,
	data : eduData
});

var eduListGrid =  new Ext.grid.GridPanel({
	frame:true,
	height:180,
	id:'productGrid',
	store:eduStore,
	region:'center',
	loadMask:true,
	cm :eduListColumns,
	autoScroll : true,
	viewConfig:{
       //forceFit : true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
		scrollOffset:2 // Grid按钮将不会显示上下滚动条
	},
    loadMask : {
        msg : '正在加载表格数据,请稍等...'
    }
});


var addEduPanel2 = new Ext.FormPanel({
	//region:'north',
	height:100,
	labelWidth:1,//label的宽度
	labelAlign:'left',
	frame:true,
	autoScroll : true,
	split:true,	
	items:[{
		layout:'column',
		items:[{
			columnWidth: 1,
			layout:'form',
			title:'',
			items:[{
					height:370,
					autoScroll : true,
					frame:true,
					items:[{
						html:
							'<p style="line-height:30px;font-size:13px;">教育目标需求总金额:<b>51,000 </b>  教育金储备金额：<b>41,000</b>  教育金缺口金额：<b>10,000</b> </p>'+
							'<iframe id="contentFrame3" name="content5" height="180" frameborder="no" width="100%" src=\"chartsDemo/Pie3D.jsp\" "/> scrolling="no"> </iframe>'+
							'<p style="line-height:30px;font-size:13px;">现金流图:</p>'+
							'<iframe id="contentFrame4" name="content" height="260" frameborder="no" width="100%" src=\"chartsDemo/Column3D.jsp\" "/> scrolling="no"> </iframe>'
						},eduListGrid,{
							html:'<p style="line-height:30px;font-size:13px;">为实现上述规划中的投资回报率，你可以购买产品来实现，或者通过专业的投资规划来选择产品实现。<br/>附：<a href="javascript:" onclick="cpiWin.show();">我国居民消费价格指数(CPI)</a></p>'
						}]
					}],
			 buttonAlign: 'center',//居中
			 buttons:[{
		    		text:'上一步',
		    		handler:function(){
		    			eduWin2.hide();
		    			eduWin1.show();
		    		 }
		    	 },{
		    		 text:'下一步',
		    		 handler:function(){
		    			 eduWin2.hide();
			    		 eduWin3.show();
		    		 }
		    	 }]	
		}]
	}]    
});

//创建购车规划面板  第二步 规划策略
var eduWin2 = new Ext.Window( {
	resizable : false,
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
	width : 800,
	height : 460,
	buttonAlign : "center",
	title : '创建教育规划 第二步',
	items : [ addEduPanel2 ]
});

//创建购车规划面板  第二步 规划策略
var cpiWin = new Ext.Window( {
	resizable : false,
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
	width : 700,
	height : 460,
	buttonAlign : "center",
	title : 'CPI指数',
	items : [{
		autoScroll : true,
		frame:true,
		html:'<p><img src="cpi.bmp"></img></p>'
	}]
});



//DEMO 数据
var prodFields = [ {name : 'a0'}, {name : 'a1'}, {name : 'a2'}];
var prodData = [
            ['理财产品一','10000','些理财产品不错，收益高相对稳定。'],
            ['理财产品二','20000','些理财产品不错，收益高相对稳定。'],
            ['理财产品三','10000','些理财产品不错，收益高相对稳定。'],
            ['理财产品四','30000','些理财产品不错，收益高相对稳定。'],
            ['理财产品五','25000','些理财产品不错，收益高相对稳定。']
];
//定义自动当前页行号
var num1 = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var sm1 = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true
});

//gridtable中的列定义
var finaInfoColumns = new Ext.grid.ColumnModel([num1,sm1,
		{header:'产品名称',dataIndex:'a0',id:"productId",sortable : true},
		{header:'<center>购买金额</center>',dataIndex:'a1',id:'productName',sortable : true,align:'right'},
		{header:'推荐理由',dataIndex:'a2',id:'catlName',sortable : true}
	]);

var productListStore = new Ext.data.ArrayStore({
	fields : prodFields,
	data : prodData
});

//***********************
// 每页显示条数下拉选择框
var spagesize_combo = new Ext.form.ComboBox({
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
		fields : [ 'value', 'text' ],
		data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],[ 100, '100条/页' ], [ 250, '250条/页' ],[ 500, '500条/页' ] ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : '20',
	forceSelection : true,
	width : 85
});

// 分页工具栏
var sbbar = new Ext.PagingToolbar({
	pageSize : parseInt(spagesize_combo.getValue()),
	store : productListStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
});
//***********************

//产品介绍
var dateails = new Ext.form.FormPanel({
    frame : true,
    buttonAlign : "center",
    region : 'center',
    autoScroll : true,
    labelWidth : 100,
    items:[{
    	layout : 'column',
        items:[{columnWidth : .5,
	            layout : 'form',
	            items :[{
	            	xtype : 'textfield',
					fieldLabel : '产品编号',
					name : 'scoreName',
					value:'CP001',
					labelStyle: 'text-align:right;',
					allowBlank:false,
					//disabled:true,
					anchor : '90%'
	            },{
	            	xtype : 'textfield',
					fieldLabel : '风险等级',
					name : 'scoreName',
					value:'中',
					labelStyle: 'text-align:right;',
					allowBlank:false,
					//disabled:true,
					anchor : '90%'
	            }]
               },{columnWidth : .5,
    	            layout : 'form',
    	            items :[{
		            	xtype : 'textfield',
						fieldLabel : '产品名称',
						name : 'scoreName',
						value:'理财产品A',
						labelStyle: 'text-align:right;',
						allowBlank:false,
						//disabled:true,
						anchor : '90%'
		            },{
		            	xtype : 'textfield',
						fieldLabel : '逾期收益',
						name : 'scoreName',
						value:'4%',
						labelStyle: 'text-align:right;',
						allowBlank:false,
						//disabled:true,
						anchor : '90%'
		            }]
                   },{columnWidth : 1,
	    	            layout : 'form',
	    	            items :[{
			            	xtype : 'textarea',
							fieldLabel : '产品介绍',
							name : 'scoreName',
							value:'得利宝天天利A......',
							labelStyle: 'text-align:right;',
							allowBlank:false,
							//disabled:true,
							anchor : '95%'
			            }]
           }]
    }],
    buttons : [ {
		text : '关闭',
		handler  : function() {
			prodInfoWin.hide();
		}
	} ]
});

var prodInfoWin = new Ext.Window({
	title:'产品详情',
	layout : 'fit',
    autoScroll : true,
    draggable : true,
    closable : true,
    closeAction : 'hide',
    modal : true,
    width : 600,
    height : 240,
    loadMask : true,
    border : false,
    items : [ {
        buttonAlign : "center",
        layout : 'fit',
        items : [dateails]
    }]
});

//****************************精选库**********************************************
//精品选择
var ruleSetForm1 = new Ext.form.FormPanel({
  frame : true,
  buttonAlign : "center",
  region : 'north',
  autoScroll : true,
  height:100,
  labelWidth : 100,
  items:[{
  	layout : 'column',
      items:[
              {columnWidth : .5,
	            layout : 'form',
	            items :[{
	            	xtype : 'textfield',
					fieldLabel : '<span style="color:red">*</span>产品编号',
					name : 'scoreName',
					labelStyle: 'text-align:right;',
					allowBlank:false,
					anchor : '100%'
	            }]
          },{columnWidth : .5,
  	            layout : 'form',
  	            items :[{
		            	xtype : 'textfield',
						fieldLabel : '<span style="color:red">*</span>产品名称',
						name : 'scoreName',
						labelStyle: 'text-align:right;',
						allowBlank:false,
						anchor : '100%'
		            }]
          }]
  }],
	buttonAlign : 'center',
		buttons : [ {
			text : '查询',
			handler : function() {

			}
		}, {
			text : '重置',
			handler : function() {
				ruleSetForm1.form.reset();
			}
		}]
});

var fields2 = [ {name : 'a0'}, {name : 'a1'}, {name : 'a2'}, {name : 'a3'}];

//定义自动当前页行号
var num2 = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var sm2 = new Ext.grid.CheckboxSelectionModel({
	singleSelect : true
});

var columns2 = new Ext.grid.ColumnModel([num2, sm2,{
	dataIndex : 'a0',
	header : '产品编号',
	sortable : true,
	width : 80
}, {
	dataIndex : 'a1',
	header : '产品名称',
	sortable : true,
	width : 140
}, {
	dataIndex : 'a2',
	header : '<center>起购金额</center>',
	sortable : true,
	width : 140,
	align:'right'
}, {
	dataIndex : 'a3',
	header : '推荐原因',
	sortable : true,
	width : 140
}]);


var data2 = [
          ['101','得利宝天添利Ａ款','1,000,000','收益可观'],
          ['102','得利宝天添利B款','1,000,000','收益可观'],
          ['103','得利宝天添利C款','100,000','收益可观'],
          ['104','天添利A款至尊版','2,000,000','收益可观'],
          ['105','天添利D款至尊版','100,000','收益可观']
];

//每页显示条数下拉选择框
var combo2 = new Ext.form.ComboBox({
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
				fields : ['value', 'text'],
				data : [[10, '10条/页'], [20, '20条/页'],
						[50, '50条/页'], [100, '100条/页'],
						[250, '250条/页'], [500, '500条/页']]
			}),
	valueField : 'value',
	displayField : 'text',
	value : '20',
	editable : false,
	width : 85
});

var store1 = new Ext.data.ArrayStore({
	fields : fields2,
	data : data2
});

var number2 = parseInt(combo2.getValue());

//分页工具栏
var bbar2 = new Ext.PagingToolbar({
	pageSize : number2,
	store : store1,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : ['-', '&nbsp;&nbsp;', combo2]
});


var ruleGrid1 = new Ext.grid.GridPanel({
		region : 'center',
		frame : true,
		store : store1,
		stripeRows : true,
		cm : columns2,
		sm : sm2,
		buttonAlign : "center",
		bbar : bbar2
});

var ruleSetPanel1 = new Ext.Panel({
  layout : 'border',
  autoScroll : true,
  buttonAlign : "center",
  items : [ ruleSetForm1,ruleGrid1],
  buttons : [{ 
	     	text : '添加', 
	     	handler  : function() {
		    	 var records = ruleGrid1.selModel.getSelections();// 得到被选择的行的数组
		    	 var selectLength = records.length;
	             if (selectLength <= 0 ) {
	             	alert("请选择产品");
	                 return false;
	             }
	             var tempName = '';
	             for(var i = 0; i<selectLength;i++){
						selectRe = ruleGrid1.getSelectionModel().getSelections()[i];
						tempName = selectRe.data.a1;
						tempID = selectRe.data.a0;
						 var u = new store1.recordType({
						    	"a0" :tempID,             
								"a1" :tempName,
								"a2" :"10,000"
						    });
						 productGrid.stopEditing();
						 store1.insert(0, u);
						 productGrid.startEditing(0, 0);
				}
	            Ext.MessageBox.alert('系统提示信息', '操作成功');
	            selGoodProdWin.hide();
	     	}
	    }, {
			text : '关闭',
			handler  : function() {
				selGoodProdWin.hide();
			}
		}]
});

var selGoodProdWin = new Ext.Window({
	title:'精选产品',
	layout : 'fit',
	autoScroll : true,
	draggable : true,
	closable : true,
	closeAction : 'hide',
	modal : true,
	width : 600,
	height : 400,
	loadMask : true,
	border : false,
	items : [ {
      buttonAlign : "center",
      layout : 'fit',
      items : [ruleSetPanel1]
  }]
});

//理财产品查询结果grid
var productGrid =  new Ext.grid.EditorGridPanel({
	title:'推荐产品',
	frame:true,
	height:200,
	id:'productGrid',
	store:store1,
	region:'center',
	loadMask:true,
	cm :columns2,
	sm : sm2,
	clicksToEdit:1,  //单击可编辑  默认是2 为双击
	viewConfig:{
       forceFit : true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
	},
	tbar:[{
		 text : '从产品库选择',
	     iconCls:'addIconCss',
	     handler:function() {
	     	productManageInfoStore.load({
	 			params:{start:0,limit: 100}
	 		});
	     	productManageWindow.show();
		 }	
	},'-',{
		text:'从精选库选择',
		iconCls:'addIconCss',
		handler:function() {
			selGoodProdWin.show();
		}
	},'-',{
		text:'产品详情',
		iconCls:'detailIconCss',
		handler:function() {
			prodInfoWin.show();
		}
	},'-',{
		text:'删除',
		iconCls:'deleteIconCss',
		handler:function() {
			var onGrid = Ext.getCmp("productGrid").getSelectionModel();
			if (onGrid.getSelections()) {
				var records = onGrid.getSelections();// 选择行的个数 
				var recordsLen = records.length;// 得到行数组的长度
				if (recordsLen < 1) {
					Ext.Msg.alert("系统提示信息", "请选择其中一条记录！");
				} else {
					Ext.Msg.alert("系统提示信息", "删除推荐产品成功！");
				}
			}
		}
	}],
	loadMask : {
      msg : '正在加载表格数据,请稍等...'
	}
});

//第三步
var addEduPanel3 = new Ext.FormPanel({
    labelAlign:'right',
    labelWidth:150,
    buttonAlign:'center',
    bodyStyle:'padding:5px;',
    frame:true,
    items:[{
    	xtype: 'fieldset',
        title: '投资与风险',
        autoHeight: true,
        height:90,
        layout: "column", 
        labelSeparator:'',
        border: true,
        //labelWidth:400, 
        width:800,
        items:[
          {layout: "form", border: false, items: { 
        	  html: '&nbsp;&nbsp;&nbsp;&nbsp;投资风险是指在投资过程中的回报的波动,一般用统计学的标准差来准确计量.回报与风险成正向相关,风险还可以分系统风险和非系统风险.<br><br>&nbsp;&nbsp;&nbsp;&nbsp;投资规划是客户经理利用专业知识和模型对投资进行资产的配置,构成一个适合客户自身特点的产品组合.这个产品组合首先匹配客户的风险承受能力,然后尽量通过风险分散，把非系统风险降到最低,形成一个"高回报,低风险组合",使客户的利益最大化.'
        	  }, columnWidth: .5},
          {layout: "form", border: false, items: { 
        	  html:'<image src="aa.bmp" width=350  height=90/>'
        	 }, columnWidth: .5} 
        ]
    },productGrid],
    buttons:[{
		    	text:'上一步',
				handler:function(){
					eduWin2.show();
					eduWin3.hide();
				}
    		},{
    			text:'保存',
    			handler:function(){
    				Ext.Msg.alert("系统提示信息", "保存成功！",function(){eduWin3.hide();});	
    			}
    		},{
    			text:'生成报告',
    			handler:function(){
    				var winPara = 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no,'+ ' scrollbars=no, resizable=no,location=no, status=no';
					var fileName = 'readme.txt';// 电子杂志文件名称
					var uploadUrl = basepath + '/AnnexeDownload?filename='+ fileName + '&annexeName=' + fileName;
					window.open(uploadUrl, '', winPara);
    			}
    }]
});



//创建购房规划面板  第四步
var eduWin3 = new Ext.Window( {
	resizable : false,
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
	width : 800,
	height : 420,
	buttonAlign : "center",
	title : '创建教育规划 第三步',
	items : [ addEduPanel3 ]
});
