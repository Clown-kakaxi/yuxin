/**
 * @description 客户资产配置
 * @author liliang5
 * @since 2016-01-06
 */

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
]);

Ext.QuickTips.init();

//数据字典定义
var lookupTypes = [
	{
		TYPE : 'RISK_TYPE',//区间
		url : '/customerAssetRiskType!searchLookup.json',
		key : 'KEY',
		value : 'VALUE',
		root : 'data'
	}
];

var riskTypeStore = new Ext.data.Store( {
    restful : true,
    autoLoad : false,
	proxy : new Ext.data.HttpProxy( {
		url :basepath + '/customerAssetRiskType!searchLookup.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'json.data'
	}, [ 'KEY', 'VALUE' ])
});
riskTypeStore.load();

var delGrantsMap = [];
var addGrantsMap = [];
var delGrantsStr = '';
var addGrantsStr = '';

/**
 * 树形结构的loader对象配置
 */
var treeLoaders = [{
	key : 'PRODUCT_TYPE_LOADER',
	url : basepath + '/personalAssetsProductInfoTree.json',
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
	key : 'PRODUCT_ADD_TREE',
	loaderKey : 'PRODUCT_TYPE_LOADER',
	autoScroll:true,
	checkBox:true,
	rootVisible: false,
	loadMaskWorking : true,
	expandedCount : 0,
	rootCfg : {
		expanded:true,
		id:'root',
		text:'银行产品树',
		autoScroll:true,
		children:[]
	},
	listeners:{
		'expandnode':function(node){
			if(!this.loadMaskWorking){
				return;
			}
			this.expandedCount++;
			if(this.expandedCount >= this.resloader.nodeArray.length){
				this.loadMaskWorking = false;
				return;
			}
		},
		'checkchange' : function(node, checked, source) {

			if (node.attributes.CATL_LEVEL == '2') {
				if (checked) {
					delGrantsMap.remove(node);
					addGrantsMap.push(node);
				} else {
					delGrantsMap.push(node);
					addGrantsMap.remove(node);
				}
			}

			if(source==undefined){									//操作节点状态，并调用父节点和子节点事件
				node.getUI().checkbox.indeterminate=false;
				node.getUI().checkbox.checked = checked;

				if(node.childNodes){
        			Ext.each(node.childNodes,function(cn){
        				cn.fireEvent('checkchange',cn,checked,'1');//若存在子节点，则传递参数以触发相应checkChange监听事件
        			});
        		}
        		if(node.parentNode && node.parentNode !== this.root){
        			node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');//若存在父节点，则传递参数以触发相应监听事件
        		}
        	}
			else if(source == '1'){								//操作节点状态，并调用子节点事件
        		node.getUI().checkbox.indeterminate=false;
        		node.getUI().checkbox.checked = checked;
        		if(node.childNodes){
        			Ext.each(node.childNodes,function(cn){
        				cn.fireEvent('checkchange',cn,checked,'1');//若存在子节点，则传递参数以触发相应checkChange监听事件
        			});
        		}
        	}else if(source == '2'){
        		if(node.childNodes && node.childNodes.length > 0){
        			var checkcount = 0;
        			var indeterminate = false;
        			for(var i=0; i<node.childNodes.length;i++){
        				if(node.childNodes[i].getUI().checkbox.indeterminate){
        					indeterminate = true;
        					break;
        				}
        				if(node.childNodes[i].getUI().checkbox.checked){
        					checkcount ++;
        				}
        			}
        			if(!indeterminate && checkcount==0){
        				node.getUI().checkbox.indeterminate = false;
        				node.getUI().checkbox.checked = false;
        			}else if(indeterminate || checkcount < node.childNodes.length){
        				node.getUI().checkbox.indeterminate = true;
        			}else if(checkcount == node.childNodes.length){
        				node.getUI().checkbox.indeterminate = false;
        				node.getUI().checkbox.checked = true;
        			}
        		}
        		if(node.parentNode && node.parentNode != this.root){//若存在父节点，则传递参数以触发相应监听事件
        			node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');
        		}
        	}
		}
	}
},{
	key : 'PRODUCT_ALTER_TREE',
	loaderKey : 'PRODUCT_TYPE_LOADER',
	autoScroll:true,
	checkBox:true,
	rootVisible: false,
	loadMaskWorking : true,
	expandedCount : 0,
	rootCfg : {
		expanded:true,
		id:'root',
		text:'银行产品树',
		autoScroll:true,
		children:[]
	},
	listeners:{
		'expandnode':function(node){
			if(!this.loadMaskWorking){
				return;
			}
			this.expandedCount++;
			if(this.expandedCount >= this.resloader.nodeArray.length){
				this.loadMaskWorking = false;
				return;
			}
		},
		'checkchange' : function(node, checked, source) {
			
			if (node.attributes.CATL_LEVEL == '2') {
				if (checked) {
					delGrantsMap.remove(node);
					addGrantsMap.push(node);
				} else {
					delGrantsMap.push(node);
					addGrantsMap.remove(node);
				}
			}

			if(source==undefined){									//操作节点状态，并调用父节点和子节点事件
				node.getUI().checkbox.indeterminate=false;
				node.getUI().checkbox.checked = checked;

				if(node.childNodes){
        			Ext.each(node.childNodes,function(cn){
        				cn.fireEvent('checkchange',cn,checked,'1');//若存在子节点，则传递参数以触发相应checkChange监听事件
        			});
        		}
        		if(node.parentNode && node.parentNode !== this.root){
        			node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');//若存在父节点，则传递参数以触发相应监听事件
        		}
        	}
			else if(source == '1'){								//操作节点状态，并调用子节点事件
        		node.getUI().checkbox.indeterminate=false;
        		node.getUI().checkbox.checked = checked;
        		if(node.childNodes){
        			Ext.each(node.childNodes,function(cn){
        				cn.fireEvent('checkchange',cn,checked,'1');//若存在子节点，则传递参数以触发相应checkChange监听事件
        			});
        		}
        	}else if(source == '2'){
        		if(node.childNodes && node.childNodes.length > 0){
        			var checkcount = 0;
        			var indeterminate = false;
        			for(var i=0; i<node.childNodes.length;i++){
        				if(node.childNodes[i].getUI().checkbox.indeterminate){
        					indeterminate = true;
        					break;
        				}
        				if(node.childNodes[i].getUI().checkbox.checked){
        					checkcount ++;
        				}
        			}
        			if(!indeterminate && checkcount==0){
        				node.getUI().checkbox.indeterminate = false;
        				node.getUI().checkbox.checked = false;
        			}else if(indeterminate || checkcount < node.childNodes.length){
        				node.getUI().checkbox.indeterminate = true;
        			}else if(checkcount == node.childNodes.length){
        				node.getUI().checkbox.indeterminate = false;
        				node.getUI().checkbox.checked = true;
        			}
        		}
        		if(node.parentNode && node.parentNode != this.root){//若存在父节点，则传递参数以触发相应监听事件
        			node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');
        		}
        	}
		}
	}
}];
//产品树
var productAddTree = TreeManager.createTree('PRODUCT_ADD_TREE');
var productAlterTree = TreeManager.createTree('PRODUCT_ALTER_TREE');

needCondition = false;
var url = basepath+'/customerAssetConf.json';
		
var fields = [
    {name: 'ID',text:'ID',hidden:true},
    {name: 'RISK_TYPE_ID',text:'风险类别',xtype:'combo',mode:'local',store:riskTypeStore,displayField:'VALUE',valueField:'KEY',triggerAction:'all',gridField:false},
	{name: 'RISK_TYPE_NAME',text:'风险类别',resutlWidth: 120},
    {name: 'RECOMMEND_PRO',text:'推荐产品码值',hidden:true},
    {name: 'RECOMMEND_PRO_ZM',text:'推荐产品',resutlWidth: 1150}
	];


var tbar=[{
	text:'风险类别配置管理',
	handler:function(){	
		showCustomerViewByTitle('风险类别配置管理');
	}
},{
	text : '删除',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			var selectedData = getSelectedData().data;
			var id = selectedData.ID;
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return;
				}
				Ext.Ajax.request({
					url: basepath + '/customerAssetConf!destroy.json?id=' + id,
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function() {
                        Ext.Msg.alert('提示', '删除成功');
						reloadCurrentData();
					},
					failure : function() {
						Ext.Msg.alert('提示', '删除失败');
						reloadCurrentData();
					}
				});
			});
		}
	}
},{
	text:'新增',
	handler:function(){		
		showCustomerViewByTitle('新增风险类别产品推荐');
	}
},{
	text:'修改',
	handler:function(){	
		if(getAllSelects().length != 1){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}
		showCustomerViewByTitle('修改风险类别产品推荐');
	}
}
//,{
//	text:'详情',
//	handler:function(){	
//		if(getAllSelects().length != 1){
//			Ext.Msg.alert('提示','请选择一条数据！');
//			return false;
//		}
//		showCustomerViewByTitle('风险类别产品推荐详情');
//	}
//}
];

/**
 * 新增风险类别产品时，选择风险类别combo
 */
var addRiskTypePanel = new Ext.form.FormPanel({
	title : '选择风险类别',
	frame:false,
	autoHeight:true,
	autoWidth:true,
	items:[{
		layout:'column',
		height : 30,
		items:[{
			layout:'form',
			labelWidth:60,
			columnWidth : .5,
			items:[{xtype:'combo',fieldLabel:'<font color=red>*</font>风险类别',name:'RISK_TYPE_NAME',anchor:'90%',
					store:riskTypeStore,valueField : 'KEY',displayField : 'VALUE',mode : 'local',
	                typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',
	                labelStyle:'text-align:right;',selectOnFocus : true}]
		}]
	}]
});

/*
 * tree加入Panel，便于页面布局
 */
var addTreePanel = new Ext.form.FormPanel({
	title : '选择推荐产品',
	frame:false,
	autoHeight:true,
	autoWidth:true,
	items:[productAddTree],
	buttonAlign : 'center',
	buttons : [{
		text: '保存',
		handler: function(){
			var riskTypeId = addRiskTypePanel.getForm().findField('RISK_TYPE_NAME').getValue();
			if(riskTypeId == ""){
				Ext.Msg.alert('提示','请选择风险类别！');
				return false;
			}

			if(addGrantsMap.length < 1){
				Ext.Msg.alert('提示','请选择推荐产品！');
				return false;
			}
			
			beforeSaveSet();
			Ext.Ajax.request({//执行保存设置
				//增量数据操作url
				url : basepath + '/customerAssetConf!save.json',
				method:'POST',
				params :{
					riskTypeId : riskTypeId,
					addGrantsStr : addGrantsStr,
					delGrantsStr : delGrantsStr
				},
				success:function(response){
//					clearCheckItem(productAddTree);
//					addRiskTypePanel.getForm().findField('RISK_TYPE_NAME').setValue("");
//					reloadCurrentData();
					window.location.reload();
				},
				failure:function(){
					Ext.Msg.alert('提示','操作失败！');
				}
			});
		}
	},{
		text: '清空',
		handler: function(){
			clearCheckItem(productAddTree);
		}
	}]
});

/**
 * 修改风险类别产品时，选择风险类别combo
 */
var alterRiskTypePanel = new Ext.form.FormPanel({
	title : '修改风险类别',
	frame:false,
	autoHeight:true,
	autoWidth:true,
	items:[{
		layout:'column',
		height : 30,
		items:[{
			layout:'form',
			labelWidth:60,
			columnWidth : .5,
			items:[{xtype:'combo',fieldLabel:'<font color=red>*</font>风险类别',name:'RISK_TYPE_NAME',anchor:'90%',
					store:riskTypeStore,valueField : 'KEY',displayField : 'VALUE',mode : 'local',
	                typeAhead : true,forceSelection : true,triggerAction : 'all',emptyText : '请选择',
	                labelStyle:'text-align:right;',selectOnFocus : true}]
		}]
	}]
});

/*
 * tree加入Panel，便于页面布局
 */
var alterTreePanel = new Ext.form.FormPanel({
	title : '修改推荐产品',
	frame:false,
	autoHeight:true,
	autoWidth:true,
	items:[productAlterTree],
	buttonAlign : 'center',
	buttons : [{
		text: '保存',
		handler: function(){
			var selectRecord = getAllSelects()[0];
			var id = selectRecord.data.ID;//修改前ID
			var recommendPro = selectRecord.data.RECOMMEND_PRO;//修改前推荐产品
			var selectRiskTypeId = selectRecord.data.RISK_TYPE_ID;//修改前风险类别ID
			var riskTypeId = alterRiskTypePanel.getForm().findField('RISK_TYPE_NAME').getValue();
						
			beforeSaveSet();
			Ext.Ajax.request({//执行保存设置
				//增量数据操作url
				url : basepath + '/customerAssetConf!save.json',
				method:'POST',
				params :{
					id : id,
					riskTypeId : riskTypeId,
					recommendPro : recommendPro,
					addGrantsStr : addGrantsStr,
					delGrantsStr : delGrantsStr
				},
				success:function(response){
//					reloadCurrentData();
					window.location.reload();
				},
				failure:function(){
					Ext.Msg.alert('提示','操作失败！');
				}
			});
		}
	},{
		text: '清空',
		handler: function(){
			clearCheckItem(productAlterTree);
		}
	},{
		text: '重置',
		handler: function(){
			clearCheckItem(productAlterTree);
			refreshCheckItem(productAlterTree);
		}
	}],
});

var customerView = [{
	title : '风险类别配置管理',
	hideTitle : true,
	type : 'grid',
	url : basepath + '/customerAssetRiskType.json',
	isCsm : false,
	frame : true,
	fields : {
		fields:[
			{name:'RISK_TYPE_ID',text:'风险类别ID'},
			{name:'RISK_TYPE_NAME',text:'风险类别',allowBlank:false}
		]
	},
	gridButtons:[{
		text:'新增',
		id:'targetAdd',
		fn : function(grid){
			opWay = 'add';
			showCustomerViewByTitle('风险类别配置编辑');
		}
	},{
		text:'修改',
		fn : function(grid){
			opWay = 'update';
			var selectLength = grid.getSelectionModel().getSelections().length;
			var selectValue = grid.getSelectionModel().getSelections()[0];
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条数据！');
				return false;
			}
			showCustomerViewByTitle('风险类别配置编辑');
		}
	},{
		text:'删除',
		fn : function(grid){
			var selectLength = grid.getSelectionModel().getSelections().length;
			var selectValue = grid.getSelectionModel().getSelections()[0];
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条数据！');
				return false;
			}
			var riskTypeId = selectValue.data.RISK_TYPE_ID;			
			var riskTypeName = selectValue.data.RISK_TYPE_NAME;
			Ext.MessageBox.confirm('提示','确认删除选中的记录吗？该操作会同时删除关联风险类别产品推荐信息！',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				 	return false;
				}
				Ext.Msg.wait('正在保存，请稍后......','系统提示');
		    	Ext.Ajax.request({
					url : basepath + '/customerAssetRiskType!delete.json',
					params : {
						'riskTypeId':riskTypeId,						
						'riskTypeName':riskTypeName						
					},
					method : 'POST',
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function(response) {
						Ext.Msg.alert('提示', '操作成功!');
						grid.store.remove(selectValue);
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						} else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
					}
				});
		 	});
		}
	},{
		text:'关闭',
		fn : function(grid){
			hideCurrentView();
			reloadCurrentData();
		}
	}]	
},{
	title : '风险类别配置编辑',
	type : 'form',
	hideTitle : true,
	autoLoadSeleted : true,
	groups : [{
		columnCount : 1 ,
		fields : [
			{name:'RISK_TYPE_ID',text:'风险类别'},
			{name:'RISK_TYPE_NAME',text:'风险类别',allowBlank:false}
		],
		fn : function(RISK_TYPE_ID, RISK_TYPE_NAME){
			RISK_TYPE_ID.hidden = true;
			return [RISK_TYPE_ID, RISK_TYPE_NAME];
		}
	}],
	formButtons : [{
		text:'保存',
		fn: function(formPanel, basicForm){			
			if (!basicForm.isValid()) {
				Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
    		 	return false;
    	 	}
    	 	var riskTypeId = basicForm.findField('RISK_TYPE_ID').getValue();
    	 	var riskTypeName = basicForm.findField('RISK_TYPE_NAME').getValue();
    	 	Ext.MessageBox.confirm('提示','确认保存上述信息？',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				 	return false;
				} 
				Ext.Msg.wait('正在保存，请稍后......','系统提示');
		    	Ext.Ajax.request({
					url : basepath + '/customerAssetRiskType!save.json',
					params : {
						'riskTypeId':riskTypeId,
						'riskTypeName':riskTypeName
					},
					method : 'POST',
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function(response) {
						Ext.Msg.alert('提示', '操作成功!');
						//保存成功后，重新加载RISK_TYPETypeStore，更新下拉框数据
						riskTypeStore.load();
						showCustomerViewByTitle('风险类别配置管理');
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						} else{
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
						}
					}
				});
		 	});
		}
	},{
		text:'返回',
		fn: function(formPanel,basicForm){
			showCustomerViewByTitle('风险类别配置管理');
		}
	}]
},{
	title : '新增风险类别产品推荐',
	hideTitle : true,
	type : 'form',
	items : [addRiskTypePanel,addTreePanel]
},{
	title : '修改风险类别产品推荐',
	hideTitle : true,
	type : 'form',
	items : [alterRiskTypePanel,alterTreePanel]
}
//,{
//	title : '风险类别产品推荐详情',
//	hideTitle : true,
//	type : 'form',
//	items : []
//}
];



/**
 * 结果域面板滑入前触发,系统提供listener事件方法
 * @param {} view
 * @return {Boolean}
 */
var beforeviewshow = function(view){
	if(view._defaultTitle=='风险类别配置管理'){
		view.setParameters ({//确保grid的url正确执行
		});
	}
};

/**
 * 结果域面板滑入后触发,系统提供listener事件方法
 * @param {} view
 * @return {Boolean}
 */
var viewshow = function(view){
	if(view._defaultTitle=='风险类别配置编辑'){
		if(opWay == 'add'){
			view.contentPanel.getForm().reset();
		}
		if(opWay == 'update'){
			var selectValue = getCustomerViewByTitle("风险类别配置管理").grid.getSelectionModel().getSelections()[0];
			view.contentPanel.getForm().findField('RISK_TYPE_ID').setValue(selectValue.data.RISK_TYPE_ID);
			view.contentPanel.getForm().findField('RISK_TYPE_NAME').setValue(selectValue.data.RISK_TYPE_NAME);
		}
	}
	if(view._defaultTitle == '新增风险类别产品推荐'){
		clearCheckItem(productAddTree);
		productAddTree.expandAll();
	}
	if(view._defaultTitle == '修改风险类别产品推荐'){
		clearCheckItem(productAlterTree);
		var riskTypeId = getAllSelects()[0].data.RISK_TYPE_ID;
		alterRiskTypePanel.getForm().findField("RISK_TYPE_NAME").setValue(riskTypeId);
		refreshCheckItem(productAlterTree);
	}
};

var beforecommit = function(data, cUrl, result) {}

/**
 * 保存之前设置
 */
var beforeSaveSet = function(){
	
	for(var i=0;i<delGrantsMap.length;i++){
		if(delGrantsMap[i].attributes.CATL_LEVEL == '2'){
			var obj = {};
			obj.catlCode   = delGrantsMap[i].attributes.CATL_CODE;
			if (delGrantsStr.length == 0) {
				delGrantsStr = obj.catlCode;
			} else {
				delGrantsStr += ',' + obj.catlCode;
			}
		}
	}
	for(var i=0;i<addGrantsMap.length;i++){
		if(addGrantsMap[i].attributes.CATL_LEVEL == '2'){
			var obj = {};
			obj.catlCode   = addGrantsMap[i].attributes.CATL_CODE;
			if (addGrantsStr.length == 0) {
				addGrantsStr = obj.catlCode;
			} else {
				addGrantsStr += ',' + obj.catlCode;
			}
		}
	}
}

/**
 * 刷新产品树
 */
var refreshCheckItem = function(productTree){
	productTree.expandAll();
	productTree.expandAll();
	productTree.expandAll();

	var selectRecord = getAllSelects()[0];
	var recommendPro = selectRecord.data.RECOMMEND_PRO;
	var recommendPros = recommendPro.split(',');
	for(var i=0;i<recommendPros.length;i++){
		var tn = productTree.root.findChild('id',recommendPros[i],true);//树节点中包含产品的CATL_CODE
		if (tn!=undefined && tn!= null) {
			tn.fireEvent('checkchange',tn,true);
		}
	}
};

/**
 * 清空产品树
 */
var clearCheckItem = function(productTree){
	for(var i=0;i<productTree.root.childNodes.length;i++){
		productTree.root.childNodes[i].fireEvent('checkchange',productTree.root.childNodes[i],false,undefined);
	}
	productTree.expandAll();
};