/**
 *@description 产品查询页面
 *@author: dongyi
 *@modify:luyy 2014-06-27,helin 20140812增加产品反馈面板不可编辑及调整代码格式,20140828修改产品介绍变更为产品基本信息，特征信息，产品介绍，产品目标客户特征一个面板
 *@since: 2014-04-28
 */	
 
imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',//引用工具包
	'/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
	
]);
var viewId = '99641';//保存产品类别对应的展示方案id
//储存表模型数据的store
var storeInfo = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/getViewInfo!getData.json',
		method:'get'
	}),
	reader : new Ext.data.JsonReader( {
    	root:'data'
	}, [
		{name:'TABLE_OTH_NAME'},
	    {name:'COLUMN_NAME'},
	    {name:'COLUMN_OTH_NAME'},
	    {name:'COLUMN_TYPE'},
	    {name:'ALIGN_TYPE'},
	    {name:'DIC_NAME'},
	    {name:'SHOW_WIDTH'}
	])
});

var createView = false;
var editView = false;
var detailView = false;

var lookupTypes = ['PROD_RISK_LEVEL','PROD_STATE','FEATURE_DISC','PROD_TYPE_ID','XD000080',{
	TYPE : 'CATL_CODE',//产品类别
	url : '/product-list!searchPlan.json',
	key : 'KEY',
	value : 'VALUE',
	root : 'data'
}];
//操作符的静态数据字典
var ssColOpDs = new Ext.data.ArrayStore({
	data: [['0000', '包含'], ['0001', '大于'], ['0002', '等于'],['0003','小于'],['0004','大于等于'],['0005','小于等于']],
	fields: ['value','text'],
	sortInfo: {
		field: 'value',
       direction: 'ASC'
  	}
});


var flag=false;//此标识用于判定产品目标客户特征的链接关系         
var url = basepath+'/product-list.json';
var fields = [
	{name: 'PRODUCT_ID',text:'产品编号',allowBlank:false,searchField: true}, 
  	{name: 'PROD_NAME',text:'产品名称',allowBlank:false,searchField: true}, 
  	{name: 'CATL_CODE',text:'产品分类',searchField: true,hidden:true}, 
    {name: 'CATL_NAME', text : '产品分类名称'},
  	{name: 'PROD_START_DATE', text : '产品发布日期',xtype:'datefield',format:'Y-m-d',searchField: true,editable : false},
  	{name: 'PROD_END_DATE', text : '产品截止日期',xtype:'datefield',format:'Y-m-d',searchField: true,editable : false},
  	{name: 'RATE', text : '利率(%)',hidden:false}, 
  	{name: 'COST_RATE', text : '费率(%)',  hidden:false}, 
  	{name: 'LIMIT_TIME', text : '期限',readOnly : true},
  	{name: 'TYPE_FIT_CUST',text:'产品适用客户',translateType:'XD000080',hidden:true,multiSelect:true,allowBlank:false},
  	{name: 'PROD_STATE', text : '是否在售', resutlWidth: 80,translateType : 'PROD_STATE', allowBlank: false,searchField: true},
  	{name: 'OBJ_CUST_DISC', text : '目标客户描述', xtype:'textarea',maxLength : 200},
  	{name: 'PROD_CHARACT', text : '产品特点', xtype:'textarea',maxLength : 200},
  	{name: 'PROD_TYPE_ID', text : '产品大类', resutlWidth: 100,hidden:true,translateType : 'PROD_TYPE_ID',allowBlank:false},
  	{name: 'RISK_LEVEL', text : '风险等级',translateType : 'PROD_RISK_LEVEL'},
  	{name: 'DANGER_DISC', text : '风险提示描述', xtype:'textarea',maxLength : 200},
  	{name: 'ASSURE_DISC', text : '担保要求描述', xtype:'textarea',maxLength : 200},
  	{name: 'PROD_DESC', text : '产品描述', xtype:'textarea',maxLength : 200},
  	{name: 'OTHER_INFO', text : '其他说明',readOnly : true},
  	{name: 'PROD_CREATOR', text : '创建人',hidden: true, resutlWidth: 100},
  	{name: 'CREATE_DATE', text : '创建日期',hidden: true, resutlWidth: 100, xtype:'datefield', format:'Y-m-d'}
];
//产品基本信息面板from,在产品详情中展示
var productInfoForm = new Ext.form.FormPanel({
	title : '产品基本信息',
	frame : true,
	height:200,
	autoScroll : true,
	collapsible :true,
	collapseFirst:false, 
	split : true,
	items : [{
	    layout : 'column',
	    items : [{
    		layout:'form',
    		columnWidth:.5,
			items:[
				{name:'PRODUCT_ID',xtype:'textfield',hidden:true},
		    	{name : 'PROD_NAME',xtype : 'textfield',fieldLabel:'产品名称',readOnly:true,cls:'x-readOnly',anchor : '80%'},
		    	{name : 'PROD_START_DATE',xtype : 'datefield',fieldLabel:'产品发布日期',readOnly:true,width: 280,cls:'x-readOnly',anchor : '80%'},
		    	{name : 'PROD_STATE',xtype : 'combo',fieldLabel:'产品状态',readOnly:true,width: 280,cls:'x-readOnly',mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',valueField:'key',allowBlank:false,anchor : '80%'},
		    	{name : 'COST_RATE',xtype : 'textfield',fieldLabel:'费率(%)',readOnly:true,cls:'x-readOnly',anchor : '80%'},
		    	{name : 'PROD_TYPE_ID',xtype : 'combo',fieldLabel:'产品大类',width: 280,valueField:'key',readOnly:true,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',cls:'x-readOnly',anchor : '80%'},
    			{name :'PROD_DESC',xtype:'textarea',fieldLabel:'产品描述',readOnly:true,cls:'x-readOnly',width: 280,anchor:'80%'},
    			{name :'OBJ_CUST_DISC',xtype:'textarea',fieldLabel:'目标客户描述',readOnly:true,cls:'x-readOnly',width: 280,anchor:'80%'},
				{name :'ASSURE_DISC',xtype:'textarea',fieldLabel:'担保要求描述',readOnly:true,cls:'x-readOnly',width: 280,anchor:'80%'}
    		]
    	},{
			layout:'form',
			columnWidth:.5,
			items:[
			 {name : 'CATL_CODE',xtype : 'combo',fieldLabel:'产品分类',width: 280,valueField:'key',readOnly:true,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',cls:'x-readOnly',anchor : '80%'},
			 {name : 'PROD_END_DATE',xtype : 'datefield',fieldLabel:'产品截止日期',readOnly:true,width: 280,cls:'x-readOnly',anchor : '80%'},
			 {name : 'RATE',xtype : 'textfield',fieldLabel:'利率(%)',readOnly:true,cls:'x-readOnly',anchor : '80%'},
			 {name : 'LIMIT_TIME',xtype : 'textfield',fieldLabel:'期限',readOnly:true,cls:'x-readOnly',anchor : '80%'},
			 {name : 'TYPE_FIT_CUST',xtype : 'combo',fieldLabel:'产品适用客户',width: 280,valueField:'key',readOnly:true,mode : 'local',store: new Ext.data.Store(),triggerAction : 'all',displayField:'value',cls:'x-readOnly',anchor : '80%'},
			 {name :'OTHER_INFO',xtype:'textarea',fieldLabel:'其他说明',readOnly:true,cls:'x-readOnly',width: 280,anchor:'80%'},
			{name :'PROD_CHARACT',xtype:'textarea',fieldLabel:'产品特点',readOnly:true,cls:'x-readOnly',width: 280,anchor:'80%'},
			{name :'DANGER_DISC',xtype:'textarea',fieldLabel:'风险提示描述',readOnly:true,cls:'x-readOnly',width: 280,anchor:'80%'}
		 ]}	
		]
	}]
});
//产品的特征信息，在产品详情中展示
var productCharactrownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
	header : 'No.',
	width : 28
});
var productCharactcolumnmodel = new Ext.grid.ColumnModel([
    productCharactrownum,
    {header:'主键Id',dataIndex:'ID',width:100,sortable : true,hidden:true},
    {header:'产品ID',dataIndex:'PRODUCT_ID',width:100,sortable : true,hidden:true},
    {header:'特征项名称',dataIndex:'PRODUCT_PROPERTY_NAME',width:100},
    {header:'特征项类别',dataIndex:'PRODUCT_PROPERTY_TYPE',width:150,renderer:function(value){
		var val = translateLookupByKey("FEATURE_DISC",value);
		return val?val:"";
		},sortable : true},
	{header:'特征项描述',dataIndex:'PRODUCT_PROPERTY_DESC',width:100}			
]);
var productCharactRecord = new Ext.data.Record.create([
	{name:'ID'},
    {name:'PRODUCT_ID'},
    {name:'PRODUCT_PROPERTY_NAME'},
    {name:'PRODUCT_PROPERTY_TYPE'},
    {name:'PRODUCT_PROPERTY_DESC'}
]);
var productCharactInfoReader = new Ext.data.JsonReader({
	totalProperty:'json.count',
	root:'json.data'
},productCharactRecord);
var productCharactInfoProxy = new Ext.data.HttpProxy({
	url:basepath + '/product-property-list.json'
});	
var	productCharactInfoStore = new Ext.data.Store({
	restful : true,
	proxy : productCharactInfoProxy,
	reader :productCharactInfoReader,
	recordType:productCharactRecord
});	
var	productCharactpagesize_combo = new Ext.form.ComboBox({
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
		fields : [ 'value', 'text' ],
		data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
		         [ 100, '100条/页' ], [ 250, '250条/页' ],
		         [ 500, '500条/页' ] ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : '20',
	forceSelection : true,
	width : 85	
});
var searchrelateData = function(grid,cobo){//根据不同的分页加载数据
	grid.store.removeAll();
	grid.store.load({
		params:{
		    productId:getSelectedData().data.PRODUCT_ID,
			start:0,
			limit: parseInt(cobo.getValue())
		}
	});
};	
var productCharactnumber = parseInt(productCharactpagesize_combo.getValue());
		/**
		 * 监听分页下拉框选择事件
		 */
	productCharactpagesize_combo.on("select", function(comboBox) {
		productCharactbbar.pageSize = parseInt(productCharactpagesize_combo.getValue()),
		searchrelateData(productCharactgrid,productCharactpagesize_combo);//不同分页加载数据
});
		//分页工具条定义
var	productCharactbbar = new Ext.PagingToolbar({
	pageSize : productCharactnumber,
	store : productCharactInfoStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : ['-', '&nbsp;&nbsp;', productCharactpagesize_combo]
});		
var productCharactgrid =new Ext.grid.GridPanel({//
	title:'产品特征信息',
	frame:true,
	height:200,
	autoScroll : true,
	collapsible:true,
	collapseFirst:false, 
	bbar:productCharactbbar,
	stripeRows : true, // 斑马线
	store:productCharactInfoStore,
	loadMask:true,
	cm :productCharactcolumnmodel,
	region:'center',
	viewConfig:{
		forceFit:false,
		autoScroll:true
	},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
})	
//产品介绍，在产品详情中展示
var productIntrouduce = new Ext.form.FormPanel({
	title : '产品介绍',
	frame : true,
	height:200,
	autoScroll : true,
	collapsible :true,
	collapseFirst:false, 
	split : true
})
//产品目标客户特征,用于产品详情展示

var resultrownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
	header : 'No.',
	width : 28
});
var resultcolumnmodel = new Ext.grid.ColumnModel([
    resultrownum,
    {header:'属性',dataIndex:'COL_NAME_C',width:250,sortable : true},
    {header:'操作符',dataIndex:'SS_COL_OP',renderer:function(value){
    	for(var i=0;i<ssColOpDs.data.length;i++)
					if(value==ssColOpDs.data.items[i].data.value){
						return ssColOpDs.data.items[i].data.text;
					}
		},width:100},
	{header:'属性值',dataIndex:'SS_COL_VALUE',width:200},
	{header:'连接关系',dataIndex:'SS_COL_JOIN',width:100,rederer:function(value){
		flag=value;
	},hidden:true}
]);
var resultRecord = new Ext.data.Record.create([
	{name:'COL_NAME_C'},
    {name:'SS_COL_OP'},
    {name:'SS_COL_VALUE'},
    {name:'SS_COL_JOIN'}
]);
var resultInfoReader = new Ext.data.JsonReader({
	root:'JSON.data'
},resultRecord);
var resultInfoProxy = new Ext.data.HttpProxy({
	url:basepath+'/querytatgetcusquery!queryAgileCondition.json'
});	
var	resultInfoStore = new Ext.data.Store({
	restful : true,
	proxy : resultInfoProxy,
	reader :resultInfoReader
});	
var	resultpagesize_combo = new Ext.form.ComboBox({
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
		fields : [ 'value', 'text' ],
		data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
		         [ 100, '100条/页' ], [ 250, '250条/页' ],
		         [ 500, '500条/页' ] ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : '20',
	forceSelection : true,
	width : 85	
});
var searchrelateData = function(grid,cobo){//根据不同的分页加载数据
	grid.store.removeAll();
	grid.store.load({
		params:{
		    productId:getSelectedData().data.PRODUCT_ID,
			start:0,
			limit: parseInt(cobo.getValue())
		}
	});
};	
var resultnumber = parseInt(resultpagesize_combo.getValue());
		/**
		 * 监听分页下拉框选择事件
		 */
resultpagesize_combo.on("select", function(comboBox) {
	resultbbar.pageSize = parseInt(resultpagesize_combo.getValue()),
	searchrelateData(resultgird,resultpagesize_combo);//不同分页加载数据
});
		//分页工具条定义
var	resultbbar = new Ext.PagingToolbar({
	pageSize : resultnumber,
	store : resultInfoStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	emptyMsg : "没有符合条件的记录",
	items : ['-', '&nbsp;&nbsp;', resultpagesize_combo]
});		
var resultgird =new Ext.grid.GridPanel({//
	frame:true,
	height:200,
	autoScroll : true,
	collapsible:true,
	collapseFirst:false, 
	bbar:resultbbar,
	stripeRows : true, // 斑马线
	store:resultInfoStore,
	loadMask:true,
	cm :resultcolumnmodel,
	region:'center',
	viewConfig:{
		forceFit:false,
		autoScroll:true
	},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
})
var radiopanel = new Ext.Panel({
	layout : 'column',
	border : false,
	items : [ {
		columnWidth : .09,
		layout : 'form',
		labelWidth : 8,
		border : false,
		items : [ new Ext.form.Radio({
			boxLabel : "与",
			labelStyle: 'text-align:right;',
			name : "a",
			checked : flag
		})]
	}, {
	columnWidth : .09,
	layout : 'form',
	labelWidth :8,
	border : false,
	items : [ new Ext.form.Radio({
		boxLabel : "或",
		checked : !flag,
		labelStyle: 'text-align:right;',
		name : "a"
		}) 
		]
	}]
});
var productCustCharatpanel=new Ext.Panel({
	height : 280,
	frame : true,
	autoScroll : true,
	items : [ resultgird,radiopanel],
	title : '产品目标客户特征'
});	

//自定义面板，包括新增，修改，产品反馈信息，产品详情。		
var customerView = [{
	title : '产品反馈信息',
	type : 'grid',
	url  : basepath+'/productfeedback.json',//产品反馈信息访问后台数据的路径
	frame : true,
	fields: {
		fields : [
			{name: 'FEEDBACK_ID',hidden : true},
			{name: 'PRODUCT_ID',hidden : true},
			{name: 'FEEDBACK_CONT',text: '反馈内容',width:240},
			{name: 'FEEDBACK_USER',text: '反馈用户',width:240,hidden:true},
			{name: 'FEEDBACK_USER_NAME',text: '反馈用户',width:240},
			{name: 'FEEDBACK_DATE', text : '反馈日期',xtype:'datefield',format:'Y-m-d'}     
		]
	},
	gridButtons :[{
		text : '新增',
		fn:function(grid){
			//新增反馈选项
			showCustomerViewByTitle('新增反馈信息');
		}			
	},{
		text : '修改',
		fn : function(grid){
			//修改反馈信息项
			var selectLength = grid.getSelectionModel().getSelections().length;
	        var selectRecord = grid.getSelectionModel().getSelections()[0];
		        if(selectLength != 1){
		            Ext.Msg.alert('提示','请选择一条数据进行操作!');
		            return false;
		        }else{
		        		var tempCreater = selectRecord.data.FEEDBACK_USER;
	 					if(tempCreater!=__userId){
							Ext.Msg.alert('系统提示','只能修改本人创建的反馈信息！');
							return false;
						}
	 					showCustomerViewByTitle('修改反馈信息');
	 			}
	 	}	       
	},{
		text:'删除',
		fn :function(grid){
			//删除反馈信息项
			var selectLength = grid.getSelectionModel().getSelections().length;
   		 	var selectRecords = grid.getSelectionModel().getSelections();
    		if(selectLength != 1){
	 			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
			}
        	var tempIdStr = '';
        	for(var i=0; i < selectLength; i++){
        		var selectRecord = selectRecords[i];
        		//临时变量，保存要删除的ID
        		tempIdStr +=  selectRecord.data.FEEDBACK_ID;
   			 	if( i != selectLength - 1){
               		tempIdStr += ',';
            	}
			}
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
        		if(buttonId.toLowerCase() == 'no'){
            		return false;
    			}
           	 	Ext.Ajax.request({
           	 		//删除指定数据访问后台路径
                    url : basepath+ '/productfeedback!destroy.json?idStr='+ tempIdStr,
                    method : 'POST',
                    waitMsg: '正在删除，请稍等...',
                    params:{
                    	idStr : tempIdStr
                	},
                	success: function(){
                    	Ext.Msg.alert('提示','删除操作成功!');
                    	grid.store.reload();
                	},
            		failure: function(){
                   	 	Ext.Msg.alert('提示','删除操作失败!');
                	}
            	});
    		});
		}
	}]
},{
	title : '新增反馈信息',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	groups : [{
		columnCount : 1 ,
		fields : [
			{name: 'PRODUCT_ID',xtype : 'textfield',hidden : true},
			{name : 'FEEDBACK_USER',xtype : 'textfield',text : '反馈人',value:__userId,hidden : true},
			{name : 'FEEDBACK_USER_NAME',xtype : 'textfield',text : '反馈人',value:__userName,readOnly : true,cls:'x-readOnly'},
			{name : 'FEEDBACK_DATE',xtype : 'datefield',text : '反馈日期',format:'Y-m-d',value:new Date(),readOnly : true,cls:'x-readOnly'},
			{name: 'PROD_NAME',dataType:'productChoose',hiddenName:'PRODUCT_ID',singleSelect:true,
		        text:"产品名称",callback : function(checkedNodes) {
					var tempform=getCurrentView().contentPanel.getForm();
					setfield(tempform,checkedNodes[0].data);},prodState:(''),riskLevel:('')},
			{name : 'FEEDBACK_CONT',xtype : 'textarea',text : '反馈内容'	}
		],
		fn : function(PRODUCT_ID,FEEDBACK_USER,FEEDBACK_USER_NAME, FEEDBACK_DATE,PROD_NAME,FEEDBACK_CONT){
			FEEDBACK_USER.hidden = true;
			FEEDBACK_DATE.allowBlank = false;
			FEEDBACK_CONT.allowBlank = false;
			return [PRODUCT_ID,FEEDBACK_USER,FEEDBACK_USER_NAME, FEEDBACK_DATE,PROD_NAME,FEEDBACK_CONT];
		}
	}],
	formButtons:[{
		text : '保存',
		//保存数据					 
		fn : function(formPanel,baseform){
			if(!baseform.isValid()){
				Ext.Msg.alert('提示','请输入完整！');
				return false;
			}
			var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Ajax.request({
 				url : basepath + '/productfeedback.json',
 				method : 'POST',
 				params : commintData,
 				// 显示读盘的动画效果，执行完成后效果消失
 				waitMsg : '正在保存数据,请等待...', 
				success : function() {
 					Ext.Msg.alert('提示', '操作成功');
 					showCustomerViewByTitle('产品反馈信息');
					},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
			 		if(resultArray == 403) {
		           		Ext.Msg.alert('提示', response.responseText);
			 		}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	 				}
				}
			});
		}
	},{
		text : '返回',
		fn : function(grid){
			//返回到产品反馈信息页面
			showCustomerViewByTitle('产品反馈信息');
		}
	}]	
},{
	title : '产品详情',
	layout: 'form',
	items: [productInfoForm,productCharactgrid,productIntrouduce,productCustCharatpanel]
},{
	title : '修改反馈信息',
	hideTitle : true,
	type : 'form',
	groups : [{
		columnCount : 1 ,
		fields : [
			{name: 'FEEDBACK_ID',hidden : true},
			{name: 'PRODUCT_ID',hidden : true},
			{name : 'FEEDBACK_USER',xtype : 'textfield',text : '反馈人',value:__userId},
			{name : 'FEEDBACK_USER_NAME',xtype : 'textfield',text : '反馈人',value:__userName,readOnly : true,cls:'x-readOnly'},
			{name : 'FEEDBACK_DATE',xtype : 'datefield',text : '反馈日期',format:'Y-m-d',readOnly : true,cls:'x-readOnly'},
			{name : 'FEEDBACK_CONT',xtype : 'textarea',text : '反馈内容'}
			
		],
		fn : function(FEEDBACK_ID,PRODUCT_ID,FEEDBACK_USER,FEEDBACK_USER_NAME, FEEDBACK_DATE,FEEDBACK_CONT){
			FEEDBACK_USER.hidden = true;
			FEEDBACK_CONT.allowBlank = false;
			FEEDBACK_DATE.allowBlank = false;
			return [FEEDBACK_ID,PRODUCT_ID,FEEDBACK_USER,FEEDBACK_USER_NAME, FEEDBACK_DATE,FEEDBACK_CONT];
		}			
	}],
	formButtons:[{
		text : '保存',
		fn : function(formPanel,baseform){
			//统一获取字段信息
			var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Ajax.request({
 				url : basepath + '/productfeedback.json',
 				method : 'POST',
 				params : commintData,
 				// 显示读盘的动画效果，执行完成后效果消失
 				waitMsg : '正在保存数据,请等待...', 
 				success : function() {
 					Ext.Msg.alert('提示', '操作成功');
 					showCustomerViewByTitle('产品反馈信息');
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
			 		if(resultArray == 403) {
		           		Ext.Msg.alert('提示', response.responseText);
			 		}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
					}
				}
			});
		}
	},{
		text : '返回',
		//返回产品反馈信息页面
		fn : function(grid){
			showCustomerViewByTitle('产品反馈信息');
		}
	}]
}];

var treeLoaders = [{
	key : 'PRODUCTLOADER',
	url : basepath + '/productCatlTreeAction.json',
	parentAttr : 'CATL_PARENT',
	locateAttr : 'CATL_CODE',
	jsonRoot : 'json.data',
	rootValue : '0',
	textField : 'CATL_NAME',
	idProperties : 'CATL_CODE'
}];
	
//面板左边的配置信息		
var treeCfgs = [{
	key : 'PRODUCTTREE',
	loaderKey : 'PRODUCTLOADER',
	autoScroll:true,
	rootCfg : {
		expanded:true,
		text:'银行产品树',
		id:'0',
		autoScroll:true,
		children:[]
	},
	clickFn : function(node){
		
		if(node.attributes.CATL_CODE == undefined){
			setSearchParams({
				CATL_CODE : node.attributes.CATL_CODE
			});
		}else{
			//首先处理字段展示
			getCloumnShow(node.attributes.PROD_VIEW);
			//然后重新查询
			setSearchParams({
				CATL_CODE : node.attributes.CATL_CODE
			});
		}
		getConditionField('CATL_CODE').setValue(node.attributes.CATL_CODE);
	}
}];

//面板边缘配置信息
var edgeVies = {
	left : {
		width : 200,
		layout : 'form',
		items : [TreeManager.createTree('PRODUCTTREE')]
	}
};

/**
* 在页面跳转前根据view的_defaultTitle做相应的逻辑判断
*/		
var beforeviewshow = function(view){
	if(view._defaultTitle == '产品反馈信息'){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
				return false;
		}else{//加载数据
			productID = getSelectedData().data.PRODUCT_ID;
			view.setParameters ({
    			productId : productID
    		}); 		
		}			
	}else if(view._defaultTitle == '新增反馈信息'){
		var tempData = getSelectedData().data;
		view.contentPanel.getForm().reset();
		view.contentPanel.getForm().findField('PRODUCT_ID').setValue(tempData.PRODUCT_ID);
	}else if(view._defaultTitle == '修改反馈信息'){
		var tempGridView = getCustomerViewByTitle('产品反馈信息');
		var tempRecord = tempGridView.grid.getSelectionModel().getSelections()[0];
		view.contentPanel.getForm().loadRecord(tempRecord);
	}
	if(view._defaultTitle == '产品详情'){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;}
		else{
			//1.加载产品基本信息数据getSelectedData().data.PRODUCT_ID
			productInfoForm.getForm().findField('PROD_TYPE_ID').bindStore(findLookupByType('PROD_TYPE_ID'));
			productInfoForm.getForm().findField('TYPE_FIT_CUST').bindStore(findLookupByType('XD000080'));
			productInfoForm.getForm().findField('PROD_STATE').bindStore(findLookupByType('PROD_STATE'));
			productInfoForm.getForm().findField('CATL_CODE').bindStore(findLookupByType('CATL_CODE'));
			productInfoForm.getForm().loadRecord(getSelectedData());
			//2.加载产品特征信息
			productCharactInfoStore.load({
			params:{
				productId : getSelectedData().data.PRODUCT_ID,
				start:0,
				limit: parseInt(productCharactpagesize_combo.getValue())
			}
		});
		//3.加载产品介绍的数据
			Ext.Ajax.request({
				//产品介绍访问的后台路径	
				url:basepath+'/ocrmSysRicheditInfoTemp!indexPage.json',
				method:'GET',
				params:{
					relId:getSelectedData().data.PRODUCT_ID
				},
				success:function(response){
					if(Ext.decode(response.responseText).json.data.length>0){
						var context = Ext.decode(response.responseText).json.data[0].content;
						productIntrouduce.body.update(context);
					}else{
						productIntrouduce.body.update('暂无产品介绍');//没有介绍，返回空页面
						//	view.contentPanel.body
					}
				},
				failure:function(){
				}
			});	
			//4.加载产品目标客户特征信息
			resultInfoStore.baseParams={
				SS_ID : getSelectedData().data.PRODUCT_ID
			};
			resultInfoStore.load({
				params:{
				start:0,
				limit: parseInt(resultpagesize_combo.getValue())
			}
			});
		}
	}
};
//字段显示方法
function getCloumnShow(viewNow){
	if(viewNow != viewId){//进行了修改，重新绘制列表
		viewId = viewNow;
		//1.删除现有字段显示
		var fields = getFieldsCopy();
		for(var i=0;i<fields.length;i++){
			removeMetaField(fields[i].name+"");
		}
		//2.重新加载字段信息
		storeInfo.load({
	    	params:{
	    		planId:viewId
	    	},
	    	callback:function(){
	    		if(storeInfo.getCount() != 0){//展示新字段
	    			storeInfo.each(function(e){
	    				if(e.get('COLUMN_NAME') == 'CATL_CODE'){//产品分类需要特殊处理    因为引用动态字典
	    					addMetaField({name:'CATL_CODE',text:'产品分类',translateType : 'CATL_CODE',resutlWidth:e.get("SHOW_WIDTH")});
	    				}else{
	    					if(e.get('COLUMN_TYPE') == 3 ){//数值型
		    					addMetaField({name:e.get('COLUMN_NAME'),text:e.get('COLUMN_OTH_NAME'),dataType : 'number',resutlWidth:e.get("SHOW_WIDTH")});
		    				}else if(e.get('COLUMN_TYPE') == 2 ){//字典映射
		    					addMetaField({name:e.get('COLUMN_NAME')+'_ORA',text:e.get('COLUMN_OTH_NAME'),dataType : 'string',resutlWidth:e.get("SHOW_WIDTH")});
		    				}if(e.get('COLUMN_TYPE') == 1 ){//字符串
		    					addMetaField({name:e.get('COLUMN_NAME'),text:e.get('COLUMN_OTH_NAME'),dataType : 'string',resutlWidth:e.get("SHOW_WIDTH")});
		    				}
	    				}
	    			});
	    		}else{
	    			Ext.Msg.alert('提示', '产品视图展示方案配置错误,没有展示属性列信息！');
	    			return false;
	    		}
	    	}
	    });
	}
}

//页面完成初始化之后，首先查询得到默认的显示方案(因为页面最初展示全部产品，所以此时查询运用最多的方案)，然后根据方案显示字段
var afterinit = function(){
	Ext.Ajax.request({
		url : basepath + '/product-list!findViewId.json',
		success : function(response) {
			var result = response.responseText;
			var results = result.split("#");
			if(results[0] == 'no'){
				Ext.Msg.alert('提示', '产品均为配置展示方案，请到[产品类别管理]部分配置');
			}else {
				getCloumnShow(results[1]);
			}
		}
	});
};

var setfield = function(form,checkedNodes){//选定申报人，回填与申报人有关的其他字段
	form.findField('PRODUCT_ID').setValue(checkedNodes.productId);
}