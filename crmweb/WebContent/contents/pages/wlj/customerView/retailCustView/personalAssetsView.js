/**
*@description 个人理财规划概览图 .360客户视图
*@author: denghj
*@since: 2016-2-23
*/
imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.CountryCode.js'
]);

Ext.QuickTips.init();
//var needGrid = false;
//WLJUTIL.suspendViews=true;  //自定义面板是否浮动

var custId =_custId;
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
	key : 'PRODUCT_TYPE_TREE',
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
	tbar: [{
		text: '保存',
		handler: function(){
			saveSet();
		}
	},{
		text: '清空',
		handler: function(){
			clearCheckItem();
		}
	},{
		text: '重置',
		handler: function(){
			clearCheckItem();
			refreshCheckItem();
		}
	}],
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
//				node.getUI().toggleCheck(checked);
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
//        		node.getUI().toggleCheck(checked);
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
//        				node.getUI().toggleCheck(false);
        				node.getUI().checkbox.checked = false;
        			}else if(indeterminate || checkcount < node.childNodes.length){
        				node.getUI().checkbox.indeterminate = true;
        			}else if(checkcount == node.childNodes.length){
        				node.getUI().checkbox.indeterminate = false;
//        				node.getUI().toggleCheck(true);
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
var productTree = TreeManager.createTree('PRODUCT_TYPE_TREE');

needCondition = false;
var url = basepath+'/personalAssetsView.json?custId='+custId;

var fields = [
          	{name: 'FINANCE_ID', text: '产品方案ID',hidden:true},
		    {name: 'CUST_ID', text: '客户ID',hidden:true},
			{name: 'CUST_NAME', text: '客户名称',resutlWidth:60},
			{name: 'CUST_AGE', text: '年龄',resutlWidth: 60},
			{name: 'FAMILY_INCOME', text: '家庭年收入',resutlWidth: 90},
			{name: 'CURRENT_AUM', text: 'AUM（万元）',resutlWidth: 90},
			{name: 'INVEST_EXPR', text: '投资经验',resutlWidth: 90},
			{name: 'INVEST_CYCLE', text: '预期投资期限',resutlWidth: 90},
			{name: 'SCORE_ALL', text: '风险投资问卷总分',resutlWidth: 100},
			{name: 'SYSTEM_PRO', text: '系统推荐产品MZ',hidden:true},
			{name: 'SYSTEM_PRO_ZM', text: '系统推荐产品',resutlWidth: 240},
			{name: 'MANAGER_PRO', text: '客户经理推荐产品MZ',hidden:true},
			{name: 'MANAGER_PRO_ZM', text: '客户经理推荐产品',resutlWidth: 240},
			{name: 'LAST_UPDATE_TM', text: '最后保存时间',resutlWidth: 160}
          ];

var tbar = [{
	text : '生成客户数据',
	handler : function(){
		
		function createSystemProducts(){
			Ext.Ajax.request({
				url : basepath + '/personalAssetsView!saveSystemProducts.json',
				method : 'POST',
				params : {
					custId : custId
				},
				success : function(response){
					Ext.Msg.alert("提示","系统推荐成功！");
					reloadCurrentData();
				},
				failure : function(response){
					Ext.Msg.alert("提示","系统推荐失败！");
				}
			});
		}
		
		Ext.Ajax.request({
			url : basepath + '/personalAssetsView!judgeRiskInfo.json',
			method : 'GET',
			params : {
				custId : custId
			},
			success : function(response){
				var score = Ext.util.JSON.decode(response.responseText).json.data;
				if(score[0].SCORE_ALL == ''){
					Ext.MessageBox.show({//自定义提示框
						title:'提示',
						msg:'由于该客户未进行风险评估问卷测试，本规划建议只显示部分资产配置！',
						width:300,
						buttons:Ext.MessageBox.OK,//选择按钮类型
						fn: createSystemProducts,//调用函数
						icon:Ext.MessageBox.INFO
					});
				}else{
					createSystemProducts();
				}
			},
			failure : function(response){
				Ext.Msg.alert("提示","系统异常，请重新尝试！");
			}
		});
		
	}
},{
	text : '进入个人理财规划建议',
	handler : function(){
		var selectLength = getAllSelects().length;
		if(selectLength != 1){
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		}
		var selectRecord = getAllSelects()[0];
		var custId = selectRecord.data.CUST_ID;
		var financeId = selectRecord.data.FINANCE_ID;
		
		//跳转至个人理财规划书页面
		window.location.href = basepath + '/contents/pages/wlj/customerView/retailCustView/personalAssetsPlanning.jsp?custId='+custId+'&financeId='+financeId;
	}
},{
	text : '修改推荐产品',
	handler : function(){
		var selectLength = getAllSelects().length;
		if(selectLength != 1){
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		}
		showCustomerViewByTitle('修改');
	}
},{
	text : '删除单条客户数据',
	handler : function(){
		var selectLength = getAllSelects().length;
		if(selectLength < 1){
			Ext.Msg.alert('提示', '请至少选择一条数据！');
			return false;
		}

		var record = getAllSelects();
		var financeId;
		for(var i=0;i<record.length;i++){
			if(financeId == undefined){
				financeId = record[i].data.FINANCE_ID;
			}else{
				financeId += ","+ record[i].data.FINANCE_ID;
			}
		}

		Ext.Ajax.request({
			url : basepath + '/personalAssetsView!delete.json',
			method : 'POST',
			params : {
				financeId : financeId
			},
			success : function(response){
				reloadCurrentData();
			},
			failure : function(response){
				Ext.Msg.alert("提示","删除失败！");
			}
		});
	}
}];

var customerView = [{
	title : '修改',
	hideTitle : true,
	type : 'form',
	items : [productTree]
}]

/**
 * 结果域面板滑入前触发,系统提供listener事件方法
 * @param {} view
 * @return {Boolean}
 */
var viewshow = function(view){
	if(view._defaultTitle == '修改'){
		clearCheckItem();
		refreshCheckItem();
	}
};

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
 * 保存设置
 */
var saveSet = function(){
	beforeSaveSet();
	var selectRecord = getAllSelects()[0];
	var financeId = selectRecord.data.FINANCE_ID;
	Ext.Ajax.request({//执行保存设置
		//增量数据操作url
		url : basepath + '/personalAssetsView!saveManagerProducts.json',
		method:'POST',
		params :{
			financeId : financeId,
			addGrantsStr : addGrantsStr,
			delGrantsStr : delGrantsStr
		},
		success:function(response){
//			reloadCurrentData();
			window.location.reload();
		},
		failure:function(){
			Ext.Msg.alert('提示','操作失败！');
		}
	});
};

/**
 * 刷新产品树
 */
var refreshCheckItem = function(){
	productTree.expandAll();
	productTree.expandAll();
	productTree.expandAll();

	var selectRecord = getAllSelects()[0];
	var managerPro = selectRecord.data.MANAGER_PRO;
	var systemPro = selectRecord.data.SYSTEM_PRO;

	if(managerPro == "" && systemPro != ""){
		var systemPros = systemPro.split(',');
		for(var i=0;i<systemPros.length;i++){
			var tn = productTree.root.findChild('id',systemPros[i],true);//树节点中包含产品的CATL_CODE
			if (tn!=undefined && tn!= null) {
				tn.fireEvent('checkchange',tn,true);
			}
		}
	}else{
		var managerPros = managerPro.split(',');
		for(var i=0;i<managerPros.length;i++){
			var tn = productTree.root.findChild('id',managerPros[i],true);//树节点中包含产品的CATL_CODE
			if (tn!=undefined && tn!= null) {
				tn.fireEvent('checkchange',tn,true);
			}
		}
	}
};

/**
 * 清空产品树
 */
var clearCheckItem = function(){
	for(var i=0;i<productTree.root.childNodes.length;i++){
		productTree.root.childNodes[i].fireEvent('checkchange',productTree.root.childNodes[i],false,undefined);
	}
	productTree.expandAll();
};

/**
 * 双击跳转
 */
var rowdblclick = function(tile, record){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择数据！');
		return false;
	}
	var selectRecord = getSelectedData();
	var custId = selectRecord.data.CUST_ID;
	var financeId = selectRecord.data.FINANCE_ID;
	//跳转至个人理财规划书页面
	window.location.href = basepath + '/contents/pages/wlj/customerView/retailCustView/personalAssetsPlanning.jsp?custId='+custId+'&financeId='+financeId;
};