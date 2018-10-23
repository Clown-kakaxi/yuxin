/**
 * 
 * 创建购车规划
 * @author  zkl
 * 
 */


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
var addCarPanel = new Ext.FormPanel({
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
            {items: [{xtype:'textfield',fieldLabel: '通货膨胀率',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'datefield',fieldLabel: '购车日期',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '车辆价格',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'combo',fieldLabel: '购车用途',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '车辆购置税',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '保险费(每年)',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '油费养路费(每年)',name: 'xming',anchor:'90%'}]}
         ]
    },{
        xtype: 'fieldset',
        title: '费用总计',
        autoHeight: true,
        height:90,
        layout: "column", 
        labelSeparator:'',
        border: true,
        items:[
          {layout: "form", border: false, items: { xtype: "label", fieldLabel: "购车总费用" ,anchor:'90%'}, columnWidth: .25},
          {layout: "form", border: false, items: { xtype: "label", fieldLabel: "877654.00",anchor:'90%'}, columnWidth: .25,labelAlign:'left'},
          {layout: "form", border: false, items: { xtype: "label", fieldLabel: "购车后总费用" ,anchor:'90%'}, columnWidth: .25},
          {layout: "form", border: false, items: { xtype: "label", fieldLabel: "877654.00" ,anchor:'90%'}, columnWidth: .25,labelAlign:'left'}
        ]
    }],
    buttons:[{text:'计算'},{text:'下一步',
    	handler:function(){
    		carWin1.hide();//hide()隐藏窗口，close()关闭窗口
    		carWin2.show();
    	}
    }]
});
	
	 
//创建购车规划面板  第一步
var carWin1 = new Ext.Window( {
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
	height : 300,
	buttonAlign : "center",
	title : '创建购车规划 第一步',
	items : [ addCarPanel ]
});


//第二步
var addCarPane2 = new Ext.FormPanel({
    labelAlign:'right',
    labelWidth:180,
    buttonAlign:'center',
    bodyStyle:'padding:5px;',
    frame:true,
    items:[{
        layout:'column',border:false,labelSeparator:'：',xtype: 'fieldset',
        defaults:{layout: 'form',border:false,columnWidth:.5},
        items:[
            {items: [{xtype:'textfield',fieldLabel: '期望投资的收益率',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '现在为购车已经准备的投资金额',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '将来每年为购车继续投资的金额',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'datefield',fieldLabel: '购车时获得的资助金额',name: 'xming',anchor:'90%'}]},
            {columnWidth:1.05,items: [{xtype:'textarea',height:120,enableAlignments:false,enableLists:false,fieldLabel: '备注',name: 'xming',anchor:'90%'}]}
         ]
    }],
    buttons:[{
    		text:'上一步',
    		handler:function(){
	    		carWin1.show();//hide()隐藏窗口，close()关闭窗口
	    		carWin2.hide();
	    	}
    	 },{text:'保存&下一步',
	    	handler:function(){
	    		carWin2.hide();//hide()隐藏窗口，close()关闭窗口
	    		carWin3.show();
	    	}
    }]
});


//创建购车规划面板  第二步
var carWin2 = new Ext.Window( {
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
	height : 280,
	buttonAlign : "center",
	title : '创建购车规划 第二步',
	items : [ addCarPane2 ]
});


var addCarPane3 = new Ext.FormPanel({
    labelAlign:'right',
    labelWidth:180,
    buttonAlign:'center',
    bodyStyle:'padding:5px;',
    frame:true,
    items:[{
        layout:'column',border:false,labelSeparator:'：',xtype: 'fieldset',
        defaults:{layout: 'form',border:false,columnWidth:.5},
        items:[
            {items: [{xtype:'textfield',fieldLabel: '客户姓名',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '规划名称',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '购车总费用',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '购车后总费用',name: 'xming',anchor:'90%'}]},
            {columnWidth:1.05,items: [{xtype:'datefield',fieldLabel: '购车日期',name: 'xming',anchor:'43%'}]},
            
            {items: [{xtype:'textfield',fieldLabel: '期望投资的收益率',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '现在为购车已经准备的投资金额',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '将来每年为购车继续投资的金额',name: 'xming',anchor:'90%'}]},
            {items: [{xtype:'textfield',fieldLabel: '购车时获得的资助金额',name: 'xming',anchor:'90%'}]},
            {columnWidth:1.05,items: [{xtype:'textfield',fieldLabel: '通过投资可获得的购车资金',name: 'xming',anchor:'43%'}]}
         ]
    },{
        xtype: 'fieldset',
        title: '贷款计算器',
        autoHeight: true,
        height:90,
        layout: "column", 
        labelSeparator:'',
        border: true,
        items:[
          {layout: "form", border: false, items: { xtype:'textfield',fieldLabel: '最低贷款额',name: 'xming',anchor:'90%'}, columnWidth: .5},
          {layout: "form", border: false, items: { xtype:'textfield',fieldLabel: '贷款期限',name: 'xming',anchor:'90%'}, columnWidth: .5},   
          
          {layout: "form", border: false, items: { xtype:'textfield',fieldLabel: '首付比例',name: 'xming',anchor:'90%'}, columnWidth: .5},           
          {layout: "form", border: false, items: { xtype:'combo',fieldLabel: '还款方式',name: 'xming',anchor:'90%'}, columnWidth: .5},  
          
          {layout: "form", border: false, items: { xtype:'combo',fieldLabel: '贷款种类',name: 'xming',anchor:'90%'}, columnWidth: .5},           
          {layout: "form", border: false, items: { xtype:'textfield',fieldLabel: '贷款利率',name: 'xming',anchor:'90%'}, columnWidth: .5},  
          
          {layout: "form", border: false, items: { xtype:'textfield',fieldLabel: '月还款支出额',name: 'xming',anchor:'90%'}, columnWidth: .5}
        ]
    },{
    	layout:'column',border:false,labelSeparator:'：',xtype: 'fieldset',
        defaults:{layout: 'form',border:false,columnWidth:.5},
        items:[
              {columnWidth:1.05,items: [{xtype:'textarea',height:60,enableAlignments:false,enableLists:false,fieldLabel: '备注',name: 'xming',anchor:'100%'}]}    
            //{columnWidth:1.05,items: [{xtype:'htmleditor',height:80,enableAlignments:false,enableLists:false,fieldLabel: '备注',name: 'xming',anchor:'90%'}]}
        ]
    }],
    buttons:[{
    		text:'上一步',
    		handler:function(){
        		carWin3.hide();//hide()隐藏窗口，close()关闭窗口
        		carWin2.show();
    		}
    	},{
    		text:'计算'
    	},{text:'保存&下一步',
    		handler:function(){
    		carWin3.hide();//hide()隐藏窗口，close()关闭窗口
    		carWin4.show();
    	}
    }]
});

//创建购车规划面板  第三步
var carWin3 = new Ext.Window( {
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
	height : 480,
	buttonAlign : "center",
	title : '创建购车规划 第三步',
	items : [ addCarPane3 ]
});

//DEMO 数据
var prodFields = [ {name : 'a0'}, {name : 'a1'}, {name : 'a2'}];
var prodData = [
            ['理财产品一','10,000','些理财产品不错，收益高相对稳定。'],
            ['理财产品二','20,000','些理财产品不错，收益高相对稳定。'],
            ['理财产品三','10,000','些理财产品不错，收益高相对稳定。'],
            ['理财产品四','30,000','些理财产品不错，收益高相对稳定。'],
            ['理财产品五','25,000','些理财产品不错，收益高相对稳定。']
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
	align:'right',
	width : 140
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
	height:160,
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

//第四步
var addCarPane4 = new Ext.FormPanel({
  labelAlign:'right',
  labelWidth:150,
  buttonAlign:'center',
  bodyStyle:'padding:5px;',
  frame:true,
  items:[{
      layout:'column',border:false,labelSeparator:'：',xtype: 'fieldset',
      defaults:{layout: 'form',border:false,columnWidth:.33},
      items:[
          {items: [{xtype:'textfield',fieldLabel: '规划名称',name: 'xming',anchor:'90%',value:'张三'}]},
          {items: [{xtype:'textfield',fieldLabel: '客户姓名',name: 'xming',anchor:'90%',value:'张三购车规划'}]},
          {items: [{xtype:'textfield',fieldLabel: '预期收益率',name: 'xming',anchor:'90%',value:'8%'}]},
          {items: [{xtype:'textfield',fieldLabel: '投资日期',name: 'xming',anchor:'90%',value:'2013-8-7'}]},      
          {items: [{xtype:'textfield',fieldLabel: '继续投资金额',name: 'xming',anchor:'90%',value:'3099798'}]}, 
          {items: [{xtype:'textfield',fieldLabel: '风险偏好',name: 'xming',anchor:'90%',value:'低风险'}]} 
       ]
  },{
  	xtype: 'fieldset',
      title: '投资与风险',
      autoHeight: true,
      height:90,
      layout: "column", 
      labelSeparator:'',
      border: true,
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
					carWin3.show();
					carWin4.hide();
				}
  		},{
  			text:'保存',
			handler:function(){
				Ext.Msg.alert("系统提示信息", "保存成功！",function(){carWin4.hide();});	
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
var carWin4 = new Ext.Window( {
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
	title : '创车购房规划 第四步',
	items : [ addCarPane4 ]
});
