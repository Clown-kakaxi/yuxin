/**
 * @description 产品信息维护
 *   注：本功能完全按新框架开发，除目标客户设定调用原有功能外，在产品目标客户界面，生成营销活动与营销商机功能未实现
 * @author helin
 * @since 2014-04-30
 */

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
]);

Ext.QuickTips.init();

var viewId = '99641';//保存产品类别对应的展示方案id
//储存表模型数据的store
var storeInfo = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/getViewInfo!getData.json',
				method:'get'
			}),
			reader : new Ext.data.JsonReader( {
	        	root:'data'
			}, [{name:'TABLE_OTH_NAME'},
			    {name:'COLUMN_NAME'},
			    {name:'COLUMN_OTH_NAME'},
			    {name:'COLUMN_TYPE'},
			    {name:'ALIGN_TYPE'},
			    {name:'DIC_NAME'},
			    {name:'SHOW_WIDTH'}])
});


//数据字典定义
var lookupTypes = [
	'CUST_LEVEL4',   //客户级别
	'IF_FLAG',       //是否标识
	'FEATURE_DISC',	 //特性项类别
	'PROD_STATE',    //产品状态
	'CON_TYPE',	//产品对照类型
	'PROD_TYPE_ID',	//大类
	'PROD_RISK_LEVEL',
	'XD000080',
	'XD000078',
	'XD000052',
	'XD000276',
	{
		TYPE : 'CATL_CODE',//产品类别
		url : '/product-list!searchPlan.json',
		key : 'KEY',
		value : 'VALUE',
		root : 'data'
	}
];
var localLookup = {
	'shiyongkehu':[
		{key : '1',value : '企业'},
	  	{key : '2',value : '个人'}
	]
};

/**
 * 树形结构的loader对象配置
 */
var treeLoaders = [{
	key : 'PRODUCT_TYPE_LOADER',
	url : basepath + '/productCatlTreeAction.json',
	parentAttr : 'CATL_PARENT',
	locateAttr : 'CATL_CODE',
	jsonRoot:'json.data',
	rootValue : '0',
	textField : 'CATL_NAME',
	idProperties : 'CATL_CODE'
}];
/**
 * 树形面板对象预配置
 */
var treeCfgs = [{
	key : 'PRODUCT_TYPE_TREE',
	loaderKey : 'PRODUCT_TYPE_LOADER',
	autoScroll:true,
	rootCfg : {
		expanded:true,
		id:'0',
		text:'银行产品树',
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
},{
	key : 'PRODUCT_TYPE_COMBO_TREE',
	loaderKey : 'PRODUCT_TYPE_LOADER',
	autoScroll:true,
	rootCfg : {
		expanded:true,
		id:'root',
		text:'银行产品树',
		autoScroll:true,
		children:[]
	},
	clickFn : function(node){
	}
}];

var url = basepath+'/prodInfo.json';
var comitUrl = basepath+'/prodInfo.json';

var fields = [
    {name: 'PRODUCT_ID', text : '产品编号', searchField: true, allowBlank: false, resutlWidth: 100},
    {name: 'PROD_NAME', text : '产品名称', searchField: true, allowBlank: false},
    {name: 'CATL_CODE', text : '产品分类', hidden : true,xtype: 'wcombotree',
    	innerTree:'PRODUCT_TYPE_COMBO_TREE',allowBlank:false,showField:'text',
    	hideField:'CATL_CODE',editable:false,searchField: true},
    {name: 'CATL_NAME', text : '产品分类名称'},
    {name: 'PROD_TYPE_ID', text : '产品大类', resutlWidth: 100,translateType : 'PROD_TYPE_ID',allowBlank:false},
    {name: 'TYPE_FIT_CUST',text:'产品适用',translateType:'shiyongkehu',multiSelect:true,multiSeparator:',',allowBlank:false},
    {name: 'TARGET_CUST_NUM', text : '是否已设定目标客户', resutlWidth: 100,translateType : 'IF_FLAG'},
    {name: 'PROD_START_DATE', text: '产品发布日期',searchField: true, resutlWidth: 100, xtype:'datefield', format:'Y-m-d',editable:false},
    {name: 'PROD_END_DATE', text: '产品截止日期',searchField: true, resutlWidth: 100, xtype:'datefield', format:'Y-m-d',editable:false},
    {name: 'RATE', text : '利率(%)', resutlWidth: 80, dataType:'string',maxLength:100},
    {name: 'COST_RATE', text : '费率(%)', resutlWidth: 80, dataType:'string',maxLength:100},
    {name: 'LIMIT_TIME', text : '期限', resutlWidth: 80, dataType:'string',maxLength:100},
    {name: 'PROD_STATE', text : '产品状态',searchField: true, resutlWidth: 80,translateType : 'PROD_STATE', allowBlank: false},
    {name: 'OBJ_CUST_DISC', text : '目标客户描述', xtype:'textarea',maxLength:500},
    {name: 'PROD_CHARACT', text : '产品特点', xtype:'textarea',maxLength:500},
    {name: 'RISK_LEVEL', text : '风险等级',translateType : 'PROD_RISK_LEVEL'},
    {name: 'DANGER_DISC', text : '风险提示描述', xtype:'textarea',maxLength:500},
    {name: 'ASSURE_DISC', text : '担保要求描述', xtype:'textarea',maxLength:500},
    {name: 'PROD_DESC', text : '产品描述', xtype:'textarea',maxLength:500},
   {name: 'PROD_FITCUST_INFO', text : '产品适用客户描述',xtype:'textarea',maxLength:500},
    {name: 'OTHER_INFO', text : '其他说明',xtype:'textarea',maxLength:500},
    {name: 'PROD_CREATOR', text : '创建人',hidden: true, resutlWidth: 100},
    {name: 'CREATE_DATE', text : '创建日期',hidden: true, resutlWidth: 100, xtype:'datefield', format:'Y-m-d'}
];
	
var createView = true;
var editView = true;
var detailView = false;

var createFormViewer =[{
	columnCount : 2,
	fields : ['PRODUCT_ID','PROD_NAME','CATL_CODE','PROD_START_DATE','PROD_END_DATE','PROD_STATE','RATE','COST_RATE','LIMIT_TIME','PROD_TYPE_ID','TYPE_FIT_CUST'],
	fn : function(PRODUCT_ID,PROD_NAME,CATL_CODE,PROD_START_DATE,PROD_END_DATE,PROD_STATE,RATE,COST_RATE,LIMIT_TIME,PROD_TYPE_ID,TYPE_FIT_CUST){
		PRODUCT_ID.hidden = false;
		CATL_CODE.hidden = false;
		return [PRODUCT_ID,PROD_NAME,CATL_CODE,PROD_START_DATE,PROD_END_DATE,PROD_STATE,RATE,COST_RATE,LIMIT_TIME,PROD_TYPE_ID,TYPE_FIT_CUST];
	}
},{
	columnCount : 2,
	fields : ['PROD_DESC','OBJ_CUST_DISC','PROD_FITCUST_INFO','PROD_CHARACT','ASSURE_DISC','DANGER_DISC','CHANNEL_DISC','PROD_CREATOR','CREATE_DATE','OTHER_INFO'],
	fn : function(PROD_DESC,OBJ_CUST_DISC,OTHER_INFO,PROD_CHARACT,ASSURE_DISC,DANGER_DISC,CHANNEL_DISC,PROD_CREATOR,CREATE_DATE,PROD_FITCUST_INFO){
		return [PROD_DESC,OBJ_CUST_DISC,OTHER_INFO,PROD_CHARACT,ASSURE_DISC,DANGER_DISC,CHANNEL_DISC,PROD_CREATOR,CREATE_DATE,PROD_FITCUST_INFO];
	}
}];

var editFormViewer =[{
	columnCount : 2,
	fields : ['PRODUCT_ID','PROD_NAME','CATL_CODE','PROD_START_DATE','PROD_END_DATE','PROD_STATE','RATE','COST_RATE','LIMIT_TIME','PROD_TYPE_ID','TYPE_FIT_CUST'],
	fn : function(PRODUCT_ID,PROD_NAME,CATL_CODE,PROD_START_DATE,PROD_END_DATE,PROD_STATE,RATE,COST_RATE,LIMIT_TIME,PROD_TYPE_ID,TYPE_FIT_CUST){
		PRODUCT_ID.hidden = true;
		CATL_CODE.hidden = false;
		return [PRODUCT_ID,PROD_NAME,CATL_CODE,PROD_START_DATE,PROD_END_DATE,PROD_STATE,RATE,COST_RATE,LIMIT_TIME,PROD_TYPE_ID,TYPE_FIT_CUST];
	}
},{
	columnCount : 2,
	fields : ['PROD_DESC','OBJ_CUST_DISC','PROD_FITCUST_INFO','PROD_CHARACT','ASSURE_DISC','DANGER_DISC','CHANNEL_DISC','PROD_CREATOR','CREATE_DATE','OTHER_INFO'],
	fn : function(PROD_DESC,OBJ_CUST_DISC,OTHER_INFO,PROD_CHARACT,ASSURE_DISC,DANGER_DISC,CHANNEL_DISC,PROD_CREATOR,CREATE_DATE,PROD_FITCUST_INFO){
		return [PROD_DESC,OBJ_CUST_DISC,OTHER_INFO,PROD_CHARACT,ASSURE_DISC,DANGER_DISC,CHANNEL_DISC,PROD_CREATOR,CREATE_DATE,PROD_FITCUST_INFO];
	}
}];



//边缘面板配置
var edgeVies = {
	left : {
		width : 200,
		layout : 'form',
		items : [TreeManager.createTree('PRODUCT_TYPE_TREE')]
	}
};

//结果域扩展功能面板
var customerView = [{
	title : '产品特征项',
	type : 'grid',
	url : basepath + '/product-property-list.json',
	pageable : true,
	fields: {
		fields : [
			{name: 'ID',hidden: true},
			{name: 'PRODUCT_ID',hidden: true},
			{name: 'PRODUCT_PROPERTY_NAME',text: '特征项名称'},
			{name: 'PRODUCT_PROPERTY_TYPE',text: '特征项类别',resutlWidth: 100,translateType: 'FEATURE_DISC',renderer:function(value){
				var val = translateLookupByKey("FEATURE_DISC",value);
				return val?val:"";
				}
			},
			{name: 'PRODUCT_PROPERTY_DESC',text: '特征项描述'}
		]
	},
	gridButtons:[{
		/**
		 * 新增特征项
		 */
		text : '新增',
		fn : function(grid){
			showCustomerViewByTitle('新增特征项');
		}
	},{
		/**
		 * 修改特征项
		 */
		text : '修改',
		fn : function(grid){
			var selectLength = grid.getSelectionModel().getSelections().length;
            var selectRecord = grid.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条数据进行操作!');
                return false;
            }
			showCustomerViewByTitle('修改特征项');
		}
	},{
		/**
		 * 删除特征项
		 */
		text : '删除',
		fn : function(grid){
			var selectLength = grid.getSelectionModel().getSelections().length;
            var selectRecords = grid.getSelectionModel().getSelections();
            if(selectLength < 1){
                Ext.Msg.alert('提示','请选择一条数据进行操作!');
                return false;
            }
            var idStr = '';
            for(var i=0; i < selectLength; i++){
                var selectRecord = selectRecords[i];
                idStr +=  selectRecord.data.ID;
                if( i != selectLength - 1){
                    idStr += ',';
                }
            }
            Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
                if(buttonId.toLowerCase() == 'no'){
                    return false;
                }
                var tempId = 123;
                Ext.Ajax.request({
                    url : basepath+'/ProdProperty/'+tempId+'.json?idStr='+idStr,
                    method : 'DELETE',
                    waitMsg: '正在删除，请稍等...',
                    params:{
                        idStr : idStr
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
	/**
	 * 自定义新增特征项面板
	 */
	title:'新增特征项',
	type: 'form',
	hideTitle: true,
	groups:[{
		columnCount : 1,
		fields : [
			{name: 'ID',hidden: true},
			{name: 'PRODUCT_ID',hidden: true},
			{name: 'PRODUCT_PROPERTY_NAME',text: '特征项名称',allowBlank: false},
			{name: 'PRODUCT_PROPERTY_TYPE',text: '特征项类别',allowBlank: false,translateType: 'FEATURE_DISC'},
			{name: 'PRODUCT_PROPERTY_DESC',text: '特征项描述', xtype:'textarea'}
		],
		/**
		 *新增特征项面板字段初始化处理
		 */
		fn : function(ID,PRODUCT_ID,PRODUCT_PROPERTY_NAME,PRODUCT_PROPERTY_TYPE,PRODUCT_PROPERTY_DESC){
			return [ID,PRODUCT_ID,PRODUCT_PROPERTY_NAME,PRODUCT_PROPERTY_TYPE,PRODUCT_PROPERTY_DESC];
		}
	}],
	formButtons:[{
		/**
		 * 新增特征项-保存按钮
		 */
		text : '保存',
		fn : function(contentPanel, baseform){
			if(!baseform.isValid()){
				Ext.Msg.alert('提示', '字段校验失败，请检查输入项!');
				return false;
			}
			var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
            Ext.Ajax.request({
                url: basepath + '/ProdProperty.json',
                method: 'POST',
                params : commintData,
                success: function(response) {
                    Ext.Msg.alert('提示', '新增特征项成功！');
                    showCustomerViewByTitle('产品特征项');
                },
                failure: function(){
                	Ext.Msg.alert('提示', '新增特征项失败！');
                }
            });
                    
		}
	},{
		/**
		 * 返回产品特征项界面
		 */
		text: '返回',
		fn : function(contentPanel, baseform){
			showCustomerViewByTitle('产品特征项');
		}
	}]
},{
	/**
	 * 自定义修改特征项面板
	 */
	title:'修改特征项',
	type: 'form',
	hideTitle: true,
	groups:[{
		columnCount : 1,
		fields : [
			{name: 'ID',hidden: true},
			{name: 'PRODUCT_ID',hidden: true},
			{name: 'PRODUCT_PROPERTY_NAME',text: '特征项名称',allowBlank: false},
			{name: 'PRODUCT_PROPERTY_TYPE',text: '特征项类别',allowBlank: false,translateType: 'FEATURE_DISC'},
			{name: 'PRODUCT_PROPERTY_DESC',text: '特征项描述', xtype:'textarea'}
		],
		/**
		 *修改特征项面板 字段初始化处理
		 */
		fn : function(ID,PRODUCT_ID,PRODUCT_PROPERTY_NAME,PRODUCT_PROPERTY_TYPE,PRODUCT_PROPERTY_DESC){
			return [ID,PRODUCT_ID,PRODUCT_PROPERTY_NAME,PRODUCT_PROPERTY_TYPE,PRODUCT_PROPERTY_DESC];
		}
	}],
	formButtons:[{
		/**
		 * 修改特征项-保存按钮
		 */
		text : '保存',
		fn : function(contentPanel, baseform){
			if(!baseform.isValid()){
				Ext.Msg.alert('提示', '字段校验失败，请检查输入项!');
				return false;
			}
			var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
            Ext.Ajax.request({
                url: basepath + '/ProdProperty.json',
                method: 'POST',
                params : commintData,
                success: function(response) {
                    Ext.Msg.alert('提示', '修改特征项成功！');
                    showCustomerViewByTitle('产品特征项');
                },
                failure: function(){
                	Ext.Msg.alert('提示', '修改特征项失败！');
                }
            });
		}
	},{
		/**
		 * 返回产品特征项界面
		 */
		text: '返回',
		fn : function(contentPanel, baseform){
			showCustomerViewByTitle('产品特征项');
		}
	}]
},{
	title : '产品对照关系',
	type : 'grid',
	url : basepath + '/productContrastRelationInfo.json',
	pageable : true,
	fields: {
		fields : [
			{name: 'ID',hidden: true},
			{name: 'PRODUCT_ID',hidden: true},
			{name: 'REL_TYPE',text: '对照类型',resutlWidth: 100,translateType: 'CON_TYPE',renderer:function(value){
				var val = translateLookupByKey("CON_TYPE",value);
				return val?val:"";
				}
			},
			{name: 'KEY',text: '对照关键字'},
			{name: 'REL_DESC',text: '对照描述'}
		]
	},
	gridButtons:[{
		/**
		 * 新增产品对照关系
		 */
		text : '新增',
		fn : function(grid){
			showCustomerViewByTitle('新增对照关系');
		}
	},{
		/**
		 * 修改产品对照关系
		 */
		text : '修改',
		fn : function(grid){
			var selectLength = grid.getSelectionModel().getSelections().length;
            var selectRecord = grid.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条数据进行操作!');
                return false;
            }
			showCustomerViewByTitle('修改对照关系');
		}
	},{
		/**
		 * 删除产品对照关系
		 */
		text : '删除',
		fn : function(grid){
			var selectLength = grid.getSelectionModel().getSelections().length;
            var selectRecords = grid.getSelectionModel().getSelections();
            if(selectLength < 1){
                Ext.Msg.alert('提示','请选择一条数据进行操作!');
                return false;
            }
            var idStr = '';
            for(var i=0; i < selectLength; i++){
                var selectRecord = selectRecords[i];
                idStr +=  selectRecord.data.ID;
                if( i != selectLength - 1){
                    idStr += ',';
                }
            }
            Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
                if(buttonId.toLowerCase() == 'no'){
                    return false;
                }
                var tempId = 123;
                Ext.Ajax.request({
                    url:basepath+'/productContrastRelationInfo!destroy.json',
					method: 'POST',
                    waitMsg: '正在删除，请稍等...',
                    params:{
                        idStr : idStr
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
	/**
	 * 自定义新增对照关系面板
	 */
	title:'新增对照关系',
	type: 'form',
	hideTitle: true,
	groups:[{
		columnCount : 1,
		fields : [
			{name: 'ID',hidden: true},
			{name: 'PRODUCT_ID',hidden: true},
			{name: 'REL_TYPE',text: '对照类型',resutlWidth: 100,allowBlank: false,translateType: 'CON_TYPE'},
			{name: 'KEY',text: '对照关键字',allowBlank: false},
			{name: 'REL_DESC',text: '对照描述', xtype:'textarea'}
		],
		/**
		 *新增对照关系面板 字段初始化处理
		 */
		fn : function(ID,PRODUCT_ID,REL_TYPE,KEY,REL_DESC){
			return [ID,PRODUCT_ID,REL_TYPE,KEY,REL_DESC];
		}
	}],
	formButtons:[{
		/**
		 * 新增对照关系-保存按钮
		 */
		text : '保存',
		fn : function(contentPanel, baseform){
			if(!baseform.isValid()){
				Ext.Msg.alert('提示', '字段校验失败，请检查输入项!');
				return false;
			}
			var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
            Ext.Ajax.request({
                url: basepath + '/productContrastRelationInfo.json',
                method: 'POST',
                params : commintData,
                success: function(response) {
                    Ext.Msg.alert('提示', '新增对照关系成功！');
                    showCustomerViewByTitle('产品对照关系');
                },
                failure: function(){
                	Ext.Msg.alert('提示', '新增对照关系失败！');
                }
            });
                    
		}
	},{
		/**
		 * 返回产品对照关系界面
		 */
		text: '返回',
		fn : function(contentPanel, baseform){
			showCustomerViewByTitle('产品对照关系');
		}
	}]
},{
	/**
	 * 自定义修改对照关系面板
	 */
	title:'修改对照关系',
	type: 'form',
	hideTitle: true,
	groups:[{
		columnCount : 1,
		fields : [
			{name: 'ID',hidden: true},
			{name: 'PRODUCT_ID',hidden: true},
			{name: 'REL_TYPE',text: '对照类型',resutlWidth: 100,allowBlank: false,translateType: 'CON_TYPE'},
			{name: 'KEY',text: '对照关键字',allowBlank: false},
			{name: 'REL_DESC',text: '对照描述', xtype:'textarea'}
		],
		/**
		 *修改对照关系面板 字段初始化处理
		 */
		fn : function(ID,PRODUCT_ID,REL_TYPE,KEY,REL_DESC){
			return [ID,PRODUCT_ID,REL_TYPE,KEY,REL_DESC];
		}
	}],
	formButtons:[{
		/**
		 * 修改对照关系-保存按钮
		 */
		text : '保存',
		fn : function(contentPanel, baseform){
			if(!baseform.isValid()){
				Ext.Msg.alert('提示', '字段校验失败，请检查输入项!');
				return false;
			}
			var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
            Ext.Ajax.request({
                url: basepath + '/productContrastRelationInfo.json',
                method: 'POST',
                params : commintData,
                success: function(response) {
                    Ext.Msg.alert('提示', '修改对照关系成功！');
                    showCustomerViewByTitle('产品对照关系');
                },
                failure: function(){
                	Ext.Msg.alert('提示', '修改对照关系失败！');
                }
            });
		}
	},{
		/**
		 * 返回产品对照关系界面
		 */
		text: '返回',
		fn : function(contentPanel, baseform){
			showCustomerViewByTitle('产品对照关系');
		}
	}]
},{
	/**
	 * 自定义产品目标客户面板
	 */
	title : '产品目标客户',
	type : 'grid',
	url : basepath + '/product-targetCust.json',
	pageable : true,
	fields: {
		fields : [
			{name: 'ID',hidden: true},
			{name: 'CUST_ID',text: '客户号'},
			{name: 'CUST_ZH_NAME',text: '客户名称'},
			{name: 'CUST_LEV',text: '客户级别',resutlWidth: 100,translateType: 'CUST_LEVEL4',renderer:function(value){
				var val = translateLookupByKey("CUST_LEVEL4",value);
				return val?val:"";
				}
			},
			{name: 'INSTITUTION_NAME',text: '主办机构'},
			{name: 'MGR_NAME',text: '主办客户经理'},
			{name: 'IS_BUY_THE_PROD',text: '是否买该产品',resutlWidth: 100,translateType: 'IF_FLAG',renderer:function(value){
				var val = translateLookupByKey("IF_FLAG",value);
				return val?val:"";
				}
			}
		]
	},
	gridButtons:[{
		/**
		 * 生成商机
		 */
		text : '生成商机',
		fn : function(grid){
			var selectLength = grid.getSelectionModel().getSelections().length;
            var selectRecord = grid.getSelectionModel().getSelections()[0];
            if(selectLength != 1){
                Ext.Msg.alert('提示','请选择一条数据进行操作!');
                return false;
            }
            //生成商机调用组件得重写，否则功能不能实现
            Ext.Msg.alert('提示','新框架代码还未添加“生成商机”功能!');
		}
	},{
		/**
		 * 生成营销活动
		 */
		text : '生成营销活动',
		fn : function(grid){
			var selectLength = grid.getSelectionModel().getSelections().length;
			var checkedNodes = grid.getSelectionModel().selections.items;
            if(selectLength < 1){
                Ext.Msg.alert('提示','请选择数据!');
                return false;
            }
            else{
            	var custIdStrs = '';
            	for (var i = 0; i < checkedNodes.length; i++) {
            		custIdStrs +=checkedNodes[i].data.CUST_ID + ',';
  				}
            	custIdStrs = custIdStrs.substring(0, custIdStrs.length-1);
            	var prodIdStrs = getSelectedData().data.PRODUCT_ID;
            	Ext.ScriptLoader.loadScript({
        			scripts: [
        				basepath+'/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js',
        				basepath+'/contents/pages/wlj/mktManage/mktActivityManager/mktAddFunction.js',
        				basepath+'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
        				basepath+'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
        				basepath+'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',
        				basepath+'/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
        			],
        		    finalCallback: function() {
        		    	getActiveAddWindowShow(custIdStrs,prodIdStrs,'',false,false,false,false,false,false);
        		    }
        		});
            	
            }
		}
	}]
},
// {
//	/**
//	 * 自定义产品介绍面板
//	 */
//	title:'产品介绍',
//	type: 'form'
//},
{
	title : '产品介绍',
	type : 'form',
	autoLoadSeleted : false,
	frame : true,
	groups : [{
		columnCount:0.94,
		fields : ['TEST'],
		fn : function(TEST){
			return [prodTabs];
	}
},{/**附件信息**/
	columnCount:0.94,
	fields:['TEST'],
	fn:function(TEST){
		createAnna = createAnnGrid(false,false,false,false);
		return [createAnna];
	}
		}]
},{
	/**
	 * 自定义持有该产品的客户信息
	 */
	title : '持有产品客户',
	type : 'grid',
	url : basepath + '/acrmFAgAgreement.json',
	pageable : true,
	fields: {
		fields : [
			{name: 'ID',hidden: true},
			{name: 'CUST_ID',text: '客户号'},
			{name: 'CUST_NAME',text: '客户名称'},
			{name: 'CUST_TYPE',text: '客户类型',resutlWidth: 100,translateType: 'XD000080',renderer:function(value){
				var val = translateLookupByKey("XD000080",value);
				return val?val:"";
				}
			},
			{name: 'IDENT_TYPE',text: '证件类型',resutlWidth: 100,translateType: 'XD000078',renderer:function(value){
				var val = translateLookupByKey("XD000078",value);
				return val?val:"";
				}
			},
			{name: 'IDENT_NO',text: '证件名称'},
			{name: 'INSTITUTION_NAME',text: '归属机构'},
			{name: 'MGR_NAME',text: '归属客户经理'},
			{name: 'ORG_BIZ_CUST_TYPE',text: '业务条线',translateType: 'XD000052',renderer:function(value){
				var val = translateLookupByKey("XD000052",value);
				return val?val:"";
				}
			},
			{name: 'ENT_SCALE_CK',text: '行业分类(企业规模)',resutlWidth: 100,translateType: 'XD000276',renderer:function(value){
				var val = translateLookupByKey("XD000276",value);
				return val?val:"";
			}
			},
			{name: 'AMT',text: '余额(RMB)',renderer: money('0,000.00')}
		]
	}
}];

/**
 * 用户扩展工具栏按钮
 */
var tbar =[{
	/**
	 * 目标客户设定配置
	 */
	text: '目标客户设定',
	handler: function(){
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}
		var data = getSelectedData().data;
		//定义产品编码全局变量
		pProductId = data.PRODUCT_ID;
		productName = data.PROD_NAME;
//		try{
			if(Com.yucheng.crm.query){
				   agileQueryWindow.show();
				   right_panel.currentSolutionsId = pProductId;
				   selectItems(0);
				   return;
			   }
//		}catch(e){
			Ext.ScriptLoader.loadScript({
				scripts: [
					basepath+'/contents/pages/productManage/productAgileQueryDatasets.js',
					basepath+'/contents/pages/productManage/productAgileQueryItems.js',
					basepath+'/contents/pages/productManage/productAgileQuery.js'
				],
			    finalCallback: function() {
			    	agileQueryWindow.addListener('hide',function(){
			    		reloadCurrentData();
			    	});
					agileQueryWindow.show();
			   	}
			});
//		}
	}
}];

/**
 * 结果域面板滑入前触发,系统提供listener事件方法
 * @param {} view
 * @return {Boolean}
 */
var beforeviewshow = function(view){
	if(view.baseType != 'createView'){
		if(view._defaultTitle == '产品特征项'){
			if(getSelectedData()){
				view.setParameters({
					productId : getSelectedData().data.PRODUCT_ID
				});
			    return true;
			}else{
				Ext.Msg.alert('提示','请选择一条数据进行操作！');
				return false;
			}
		}else if(view._defaultTitle == '新增特征项'){
			var tempData = getSelectedData().data;
			view.contentPanel.getForm().reset();
			view.contentPanel.getForm().findField('PRODUCT_ID').setValue(tempData.PRODUCT_ID);
		}else if(view._defaultTitle == '修改特征项'){
			var tempGridView = getCustomerViewByTitle('产品特征项');
			var tempRecord = tempGridView.grid.getSelectionModel().getSelections()[0];
			view.contentPanel.getForm().loadRecord(tempRecord);
		}else if(view._defaultTitle == '产品对照关系'){
			if(getSelectedData()){
				view.setParameters({
					productId : getSelectedData().data.PRODUCT_ID
				});
			    return true;
			}else{
				Ext.Msg.alert('提示','请选择一条数据进行操作！');
				return false;
			}
		}else if(view._defaultTitle == '新增对照关系'){
			var tempData = getSelectedData().data;
			view.contentPanel.getForm().reset();
			view.contentPanel.getForm().findField('PRODUCT_ID').setValue(tempData.PRODUCT_ID);
		}else if(view._defaultTitle == '修改对照关系'){
			var tempGridView = getCustomerViewByTitle('产品对照关系');
			var tempRecord = tempGridView.grid.getSelectionModel().getSelections()[0];
			view.contentPanel.getForm().loadRecord(tempRecord);
		}else if(view._defaultTitle == '产品目标客户'){
			if(getSelectedData()){
				Ext.Ajax.request({
					url:basepath+'/querytatgetcusquery!queryAgileCondition.json?SS_ID='+getSelectedData().data.PRODUCT_ID,
                	method: 'GET',
					success : function(response) {
						var conditionData = Ext.util.JSON.decode(response.responseText);
						var conditionArray=conditionData.JSON.data;
						if(conditionArray.length>0){
							var conditions = new Array();
							Ext.each(conditionArray,function(con){
								var conAtt = {};
								conAtt.ss_col_item = con.SS_COL_ITEM;
								conAtt.ss_col_op = con.SS_COL_OP;
								conAtt.ss_col_value = con.SS_COL_VALUE;
								conditions.push(conAtt);
							});
							view.setParameters({
								conditionAttrs : Ext.encode(conditions),
				         		radio: conditionArray[0].SS_COL_JOIN,
								productId : getSelectedData().data.PRODUCT_ID
							});
	    				}else {
	    					Ext.Msg.alert('提示','请先设定目标客户!');
	    					return false;
	    				}
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示','查询失败!');
						} else {
							Ext.Msg.alert('提示','操作失败!');
						}
					}
				});
			    return true;
			}else{
				Ext.Msg.alert('提示','请选择一条数据进行操作！');
				return false;
			}
		}else if(!getSelectedData()){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}else if(view._defaultTitle == '持有产品客户'){
			if(getSelectedData()){
				view.setParameters({productId : getSelectedData().data.PRODUCT_ID});
			}else{
				Ext.Msg.alert('提示','请选择一条数据进行操作！');
				return false;
			}
		}else if(view._defaultTitle == '产品介绍'){
			loadQueryData1();
			var	messageIdStr = getSelectedData().data.PRODUCT_ID;
		    uploadForm.relaId = messageIdStr;
            uploadForm.modinfo = 'product';
            var condi = {};
            condi['relationInfo'] = messageIdStr;
            condi['relationMod'] = 'product';
            Ext.Ajax.request({
                url:basepath+'/queryanna.json',
                method : 'GET',
                params : {
                    "condition":Ext.encode(condi)
                },
                failure : function(a,b,c){
                    Ext.MessageBox.alert('查询异常', '查询失败！');
                },
                success : function(response){
                    var anaExeArray = Ext.util.JSON.decode(response.responseText);
                    createAnna.store.loadData(anaExeArray.json.data);
                    createAnna.getView().refresh();
                }
            });
		}
	}
};

/**
 * 结果域面板滑入后触发,系统提供listener事件方法
 * @param {} theview
 */
var viewshow = function(theview){
//	if(theview._defaultTitle == '产品介绍'){
//		var productid = getSelectedData().data.PRODUCT_ID;
//		theview.contentPanel.el.dom.innerHTML = '<iframe id="content3" name="content3" style="width:100%;height:440px;" frameborder="no"" src=\"'
//			+ basepath + '/contents/pages/demo/docs/doc1.jsp?nodeId='+productid+ '\" scrolling="auto"/> ';
//	}
	if(theview._defaultTitle == '产品介绍'){
		var productid = getSelectedData().data.PRODUCT_ID;
		loadQueryData();
	}
};
var prodTabs = new Ext.form.FieldSet({
	collapsible:true,
	items : [{html:'<iframe id="content3" name="content3" style="width:100%;height:440px;" frameborder="no"" src=\"'
		+ basepath + '/contents/pages/demo/docs/doc4.jsp?nodeId='+0+'\" scrolling="auto"/> '}]
});


function loadQueryData() {
	if(content3.window.queryData == undefined){
		var task = new Ext.util.DelayedTask(loadQueryData); 
    	task.delay(100);
    	return false;
	}
	var id = getSelectedData().data.PRODUCT_ID;
	content3.window.queryData(id);
	return id;
}

function loadQueryData1() {
	if(content3.window.queryData == undefined){
		var task = new Ext.util.DelayedTask(loadQueryData1); 
    	task.delay(100);
    	return false;
	}
	content3.window.queryData('0');
}

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
	    				if(e.get('COLUMN_NAME') == 'CATL_CODE'){//产品分类需要特殊处理
	    					addMetaField({name:'CATL_CODE',text:'产品分类',translateType : 'CATL_CODE',resutlWidth:e.get("SHOW_WIDTH")});
	    					addMetaField({name: 'TARGET_CUST_NUM', text : '是否已设定目标客户', resutlWidth: 100,translateType : 'IF_FLAG'});
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
				Ext.Msg.alert('提示', '产品均未配置展示方案，请到[产品类别管理]部分配置');
			}else {
				getCloumnShow(results[1]);
			}
		}
	});
};