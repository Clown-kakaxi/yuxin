/**
 * @description 机构管理
 * @author helin
 * @since 2014-04-24
 */

imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
    '/contents/pages/common/Com.yucheng.bcrm.common.CountryCode.js'
]);

var nodeNow = '';//被选中的节点
var nodeNowCheck = '';//下拉框被选中的节点

var opWay = '';//操作方式  add 新增,update 修改,info 查看
var needReset = false;//用于判断新增时是否需要重置基本信息页面    （因为上一步时不需要）
var tempSchemeInfo = {data:{}};//临时保存方案信息
var tempSchemeId = '';//临时方案编号

/**
 * 全局变量 用于实现保存
 * */
var delGrantsMap = [];
var addGrantsMap = [];
var delGrantsStr = '';
var addGrantsStr = '';

Ext.QuickTips.init();

//树配置查询类型
//var treeCondition = {searchType:'SUBTREE'};

/**
 * 树形结构的loader对象配置
 */
var treeLoaders = [{
	key : 'CUSTATTRLOADER',
	url : basepath + '/customerAttriTree.json',
	parentAttr : 'UP_ATTRI_ID',
	locateAttr : 'ATTRI_ID',
	jsonRoot:'json.data',
	rootValue : '0',
	textField : 'ATTRI_NAME',
	idProperties : 'ATTRI_ID'
},{
	key : 'CUSTATTRCHECKLOADER',
	url : basepath + '/customerAttriCheckTree.json',
	parentAttr : 'UP_ATTRI_ID',
	locateAttr : 'ATTRI_ID',
	jsonRoot:'json.data',
	rootValue : '0',
	textField : 'ATTRI_NAME',
	idProperties : 'ATTRI_ID'
}];
/**
 * 树形面板对象配置
 */
var treeCfgs = [{
	key : 'CUSTATTRTREE',
	loaderKey : 'CUSTATTRLOADER',
	autoScroll:true,
	height:600,
	rootCfg : {
		expanded:true,
		text:'客户属性',
		autoScroll:true,
		children:[]
	},clickFn : function(node){
		nodeNow = node;
		//设置一个变量，供放大镜使用
		JsContext.indexId = node.attributes.ATTRI_ID;
		
		setSearchParams({
			attriId : node.attributes.ATTRI_ID
		});
	},
	tbar:[{
		text : '新增',
		handler : function() {
			showCustomerViewByTitle('新增客户属性');
		}
	},{
		text : '修改',
		handler : function() {
			showCustomerViewByTitle('修改客户属性');
		}
	},{
		text : '详情',
		handler : function() {
			showCustomerViewByTitle('客户属性详情');
		}
	},{
		text : '删除',
		handler : function() {
			if (nodeNow == '' || nodeNow == null) {
				Ext.Msg.alert('提示', '请选择一条数据！');
				return false;
			} else {
				var attriId = nodeNow.attributes.ATTRI_ID;
				var attriName = nodeNow.attributes.ATTRI_NAME;
				if (!nodeNow.leaf) {
					Ext.Msg.alert('提示', '本类别下含有子类别，不能删除！');
					return false;
				}
				Ext.MessageBox.confirm('提示', '删除该属性会同时删除关联的指标及指标值配置，确认删除吗？', function(buttonId) {
					if (buttonId.toLowerCase() == "no") {
						return false;
					}
					Ext.Ajax.request({
						url : basepath + '/customerAttriTree!destroy.json',
						params : {
							'attriId':attriId,
							'attriName':attriName
						},
						success : function() {
							Ext.Msg.alert('提示', '删除成功');
							leftTree.deleteNode(nodeNow);
							nodeNow = '';
							reloadCurrentData();
						},
						failure : function() {
							Ext.Msg.alert('提示', '删除失败');
						}
					});
				});
			}
		}
	}]
},
{
	key : 'CUSTATTRTREECOMBO',
	loaderKey : 'CUSTATTRLOADER',
	autoScroll:true,
	rootCfg : {
		expanded:true,
		text:'客户属性',
		autoScroll:true,
		children:[]
	},clickFn : function(node){
		nodeNowCheck = node;
	}
},
{
	key : 'CUSTATTRCHECKADDTREE',
	loaderKey : 'CUSTATTRCHECKLOADER',
	autoScroll:true,
	checkBox:true,
	rootVisible: false,
	loadMaskWorking : true,
	expandedCount : 0,
	rootCfg : {
		expanded:true,
		id : 'root',
		text:'客户属性',
		autoScroll:true,
		children:[]
	},
	tbar:[{
		text : '上一步',
		handler:function(){
			showCustomerViewByTitle('新增方案');
		}
	},{
		text : '保存',
		handler : function(){
			saveSet();
		}
	},{
		text : '返回',
		handler : function(){
			showCustomerViewByTitle('方案管理');
		}
	},{
		text : '清空',
		handler : function(){
			for(var i=0;i<checkAddTree.root.childNodes.length;i++){
				checkAddTree.root.childNodes[i].fireEvent('checkchange',checkAddTree.root.childNodes[i],false,undefined);
			}
			checkAddTree.expandAll();
		}
	}],
	listeners:{
		'expandnode':function(node){
			if(!this.loadMaskWorking){
				return;
			}
			this.expandedCount++;
			if(this.expandedCount >= this.resloader.nodeArray.length){
//				lm.hide();
				this.loadMaskWorking = false;
				return;
			}
		},
		'checkchange' : function(node, checked, source) {

//			if (node.attributes.ATTRI_LEVEL == '1') {
				if (checked) {
					delGrantsMap.remove(node);
					addGrantsMap.push(node);
				} else {
					delGrantsMap.push(node);
					addGrantsMap.remove(node);
				}
//			}
			if(source==undefined){									//操作节点状态，并调用父节点和子节点事件
				node.getUI().checkbox.indeterminate=false;
				node.getUI().toggleCheck(checked);
        		if(node.childNodes){
        			Ext.each(node.childNodes,function(cn){
        				cn.fireEvent('checkchange',cn,checked,'1');//若存在子节点，则传递参数以触发相应checkChange监听事件
        			});
        		}
        		if(node.parentNode && node.parentNode !== this.root){
//        			if (node.attributes.ATTRI_LEVEL == '0') { //控制点勾选特殊处理
//        				node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');//若存在父节点，则传递参数以触发相应监听事件
//					} else {
						if (checked) {
							delGrantsMap.remove(node);
        					addGrantsMap.push(node);
        					node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');//若存在父节点，则传递参数以触发相应监听事件
        				} else {
        					delGrantsMap.push(node);
        					addGrantsMap.remove(node);
        				}
//					}
        		}
        	}else if(source == '1'){								//操作节点状态，并调用子节点事件
        		node.getUI().checkbox.indeterminate=false;
        		node.getUI().toggleCheck(checked);
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
        				node.getUI().toggleCheck(false);
        			}else if(indeterminate || checkcount < node.childNodes.length){
        				node.getUI().checkbox.indeterminate = true;
        			}else if(checkcount == node.childNodes.length){
        				node.getUI().checkbox.indeterminate = false;
        				node.getUI().toggleCheck(true);
        			}
        		}
        		if(node.parentNode && node.parentNode != this.root){//若存在父节点，则传递参数以触发相应监听事件
        			node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');
        		}
        	}
		}
	}
},{
	key : 'CUSTATTRCHECKALTERTREE',
	loaderKey : 'CUSTATTRCHECKLOADER',
	autoScroll:true,
	checkBox:true,
	rootVisible: false,
	loadMaskWorking : true,
	expandedCount : 0,
	rootCfg : {
		expanded:true,
		id : 'root',
		text:'客户属性',
		autoScroll:true,
		children:[]
	},
	tbar:[{
		text : '上一步',
		handler:function(){
			showCustomerViewByTitle('修改方案');
		}
	},{
		text : '保存',
		handler : function(){
			saveSet();
		}
	},{
		text : '返回',
		handler : function(){
			showCustomerViewByTitle('方案管理');
		}
	},{
		text : '清空',
		handler : function(){
			for(var i=0;i<checkAlterTree.root.childNodes.length;i++){
				checkAlterTree.root.childNodes[i].fireEvent('checkchange',checkAlterTree.root.childNodes[i],false,undefined);
			}
			checkAlterTree.expandAll();
		}
	},{
	    text : '重置',
	    handler : function(){
	    	checkAlterTree.expandAll();//默认展开树，解决前台取不到子节点，从而不能递归check问题
			var selectValue = getCustomerViewByTitle("方案管理").grid.getSelectionModel().getSelections()[0];
			var schemeId = selectValue.data.SCHEME_ID;
			
			Ext.Ajax.request({//获取方案属性指标
				url : basepath + '/customerSchemeAttri!getSchemeAttri.json',
				method:'GET',
				params :{
					schemeId : schemeId
				},
				success:function(response){
					refreshCheckItem(response);
				},
				failure:function(){
					Ext.Msg.alert('提示','重置失败！');
				}
			});
	    }
	}],
	listeners:{
		'expandnode':function(node){
			if(!this.loadMaskWorking){
				return;
			}
			this.expandedCount++;
			if(this.expandedCount >= this.resloader.nodeArray.length){
//				lm.hide();
				this.loadMaskWorking = false;
				return;
			}
		},
		'checkchange' : function(node, checked, source) {

//			if (node.attributes.ATTRI_LEVEL == '1') {
				if (checked) {
					delGrantsMap.remove(node);
					addGrantsMap.push(node);
				} else {
					delGrantsMap.push(node);
					addGrantsMap.remove(node);
				}
//			}
			if(source==undefined){									//操作节点状态，并调用父节点和子节点事件
				node.getUI().checkbox.indeterminate=false;
				node.getUI().toggleCheck(checked);
        		if(node.childNodes){
        			Ext.each(node.childNodes,function(cn){
        				cn.fireEvent('checkchange',cn,checked,'1');//若存在子节点，则传递参数以触发相应checkChange监听事件
        			});
        		}
        		if(node.parentNode && node.parentNode !== this.root){
//        			if (node.attributes.ATTRI_LEVEL == '0') { //控制点勾选特殊处理
//        				node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');//若存在父节点，则传递参数以触发相应监听事件
//					} else {
						if (checked) {
							delGrantsMap.remove(node);
        					addGrantsMap.push(node);
        					node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');//若存在父节点，则传递参数以触发相应监听事件
        				} else {
        					delGrantsMap.push(node);
        					addGrantsMap.remove(node);
        				}
//					}
        		}
        	}else if(source == '1'){								//操作节点状态，并调用子节点事件
        		node.getUI().checkbox.indeterminate=false;
        		node.getUI().toggleCheck(checked);
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
        				node.getUI().toggleCheck(false);
        			}else if(indeterminate || checkcount < node.childNodes.length){
        				node.getUI().checkbox.indeterminate = true;
        			}else if(checkcount == node.childNodes.length){
        				node.getUI().checkbox.indeterminate = false;
        				node.getUI().toggleCheck(true);
        			}
        		}
        		if(node.parentNode && node.parentNode != this.root){//若存在父节点，则传递参数以触发相应监听事件
        			node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');
        		}
        	}
		}
	}
}];

//左边树
var leftTree = TreeManager.createTree('CUSTATTRTREE');
//客户属性check新增树
var checkAddTree = TreeManager.createTree('CUSTATTRCHECKADDTREE');
//客户属性check修改树
var checkAlterTree = TreeManager.createTree('CUSTATTRCHECKALTERTREE');

var url = basepath+'/customerAttriManager.json';
//var comitUrl = basepath+'/customerAttriManager.json';

var lookupTypes=['ATTR001'];
needCondition = false;

var fields = [
    {name: 'INDEX_ID',text:'指标ID',resutlWidth:100,hidden:true},
    {name: 'ATTRI_ID',text:'属性ID',hidden:true},
    {name: 'ATTRI_NAME',text:'属性名称',searchField:true,allowBlank:false},
    {name: 'INDEX_NAME',text:'指标名称',searchField:true,allowBlank:false},
  	{name: 'INDEX_VALUE', text : '指标值'},
  	{name: 'INDEX_VALUE_NAME', text:'指标值名称', xtype :'countryCodeChoose', singleSelected : true, hiddenName : 'INDEX_VALUE', allowBlank : false, gridField: false},
  	
//    {name: 'INDEX_SCORE',text:'指标分值',editable:true,resutlWidth:100,allowBlank:false},
    {name: 'INDEX_STATE',text:'指标状态',translateType:'ATTR001',searchField: true,editable:true,resutlWidth:100,allowBlank:false}
    
//    {name: 'ATTRI_NAME',text:'属性名称',hidden:true}
];

var createView = true;
var editView = true;
var detailView = true;
var afertcommit = true;

/**
 * 新增设计
 */
var createFormViewer = [{
	columnCount: 1,
	fields : ['INDEX_ID','ATTRI_ID','INDEX_NAME','INDEX_VALUE','INDEX_VALUE_NAME','INDEX_STATE'],
	fn : function(INDEX_ID,ATTRI_ID,INDEX_NAME,INDEX_VALUE,INDEX_VALUE_NAME,INDEX_STATE){		
		ATTRI_ID.hidden=true;
		INDEX_ID.hidden=true;
		INDEX_VALUE.hidden=true;
		
		INDEX_VALUE.readOnly = true;
		INDEX_VALUE.cls = "x-readOnly";
		return [INDEX_ID,ATTRI_ID,INDEX_NAME,INDEX_VALUE,INDEX_VALUE_NAME,INDEX_STATE];
	}
}];

/**
 * 修改设计
 */
var editFormViewer = [{
	columnCount: 1,
	fields : ['INDEX_ID','ATTRI_ID','INDEX_NAME','INDEX_VALUE_NAME','INDEX_VALUE','INDEX_STATE'],
	fn : function(INDEX_ID,ATTRI_ID,INDEX_NAME,INDEX_VALUE_NAME,INDEX_VALUE,INDEX_STATE){		
		ATTRI_ID.hidden=true;
		INDEX_ID.hidden=true;
		INDEX_VALUE.hidden=true;
		return [INDEX_ID,ATTRI_ID,INDEX_NAME,INDEX_VALUE_NAME,INDEX_VALUE,INDEX_STATE];
	}
}];

var formCfgs = {
	formButtons:[{
		text : '返回',
		fn : function(contentPanel, baseform){			
			hideCurrentView();
		}
	}]
};

/**
 * 详情设计
 */
detailFormViewer = [{
	columnCount: 1,
	fields : ['INDEX_ID','ATTRI_ID','INDEX_NAME','INDEX_VALUE_NAME','INDEX_VALUE','INDEX_STATE'],
	fn : function(INDEX_ID,ATTRI_ID,INDEX_NAME,INDEX_VALUE_NAME,INDEX_VALUE,INDEX_STATE){
		INDEX_ID.hidden=true;
		ATTRI_ID.hidden=true;
		INDEX_VALUE.hidden=true;
		
		INDEX_NAME.readOnly = true;
		INDEX_NAME.cls = "x-readOnly";
		
		INDEX_VALUE_NAME.readOnly = true;
		INDEX_VALUE_NAME.cls = "x-readOnly";
		
//		INDEX_SCORE.readOnly = true;
//		INDEX_SCORE.cls = "x-readOnly";
		
		INDEX_STATE.readOnly = true;
		INDEX_STATE.cls = "x-readOnly";
		return [INDEX_ID,ATTRI_ID,INDEX_NAME,INDEX_VALUE_NAME,INDEX_VALUE,INDEX_STATE];
	}
}];

/*********************************指标值嵌入form开始****************************************/
//指标值新增form
var createForm = new Ext.form.FormPanel({
    frame: true,	// 是否渲染表单面板背景色
    labelAlign: 'right',	// 标签对齐方式
    buttonAlign: 'center',
//    autoHeight: true,
    height: '100%',
    defaults: {
        anchor:'0'
    },
    items: [
        {	xtype: 'compositefield',
	        fieldLabel: '指标值',
	        anchor:'-20',
	        defaults: {
            	flex: 1
            },
		    items: [
	        	{xtype:'textfield',name:'INDEX_VALUE1',labelStyle:'text-align:right;',width:'270'},
		    	{xtype:'displayfield',value: '-'},
	        	{xtype:'textfield',name:'INDEX_VALUE2',labelStyle:'text-align:right;',width:'270'}
		    ]
		},
        {xtype:'textfield',fieldLabel:'指标值名称',name:'INDEX_VALUE_NAME',labelStyle:'text-align:right;',anchor:'-20',allowBlank:false},
        {xtype:'textfield',fieldLabel:'编号',name:'ID',labelStyle:'text-align:right;',hidden:true},
        {xtype:'textfield',fieldLabel:'属性编码',name:'ATTRI_ID',labelStyle:'text-align:right;',hidden:true},
        {xtype: 'displayfield',fieldLabel:'提示',name:'QTIPS',labelStyle:'text-align:right;',
        	value:'指标值填写规则：如果是普通值，请在指标值前框输入内容；如果是数值范围，请在前后框输入区间数值。'},
	],
	buttons: [{
		text:'保存',
        handler:function(){
			if (!createForm.form.isValid()) {
				Ext.Msg.alert('提示', '输入不合法，请重新输入！');
				return false;
			}
    	 	var indexValue;
    	 	var id = createForm.form.findField('ID').getValue();
    	 	var attriId = nodeNow.attributes.ATTRI_ID;
    	 	var indexValueName = createForm.form.findField('INDEX_VALUE_NAME').getValue();
        	var minv = createForm.form.findField('INDEX_VALUE1').getValue();//获取字段值
        	var maxv = createForm.form.findField('INDEX_VALUE2').getValue();
        	
        	if(minv == '' && maxv == ''){
				Ext.Msg.alert('提示','指标值不可为空！');
				return false;
        	}
        	var reg = /^[0-9]+$/;
        	if(minv == '' && !reg.test(maxv)){
				Ext.Msg.alert('提示','数值型指标值应是区间！');
				return false;
        	}        	

        	if(minv != '' && maxv != ''){
	        	if(reg.test(minv) && reg.test(maxv)){
        			indexValue = minv + '-' + maxv;
	        	}else if(reg.test(minv) && !reg.test(maxv)){
					Ext.Msg.alert('提示','数值型指标值应都是是数字！');
					return false;
	        	}else if(reg.test(maxv) && !reg.test(minv)){
					Ext.Msg.alert('提示','数值型指标值应都是数字！');
					return false;
	        	}else{
        			indexValue = minv;	        	
	        	}
        	}else{
        		indexValue = minv;
        	}
        	
        	Ext.MessageBox.confirm('提示','确认保存上述信息？',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				 	return false;
				} 
				Ext.Msg.wait('正在保存，请稍后......','系统提示');
		    	Ext.Ajax.request({
					url : basepath + '/customerAttriItem!save.json',
					params : {
						'id':id,
						'attriId':attriId,
						'indexValue':indexValue,
						'indexValueName':indexValueName
					},
					method : 'POST',
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function(response) {
						Ext.Msg.alert('提示', '操作成功!');
						createForm.form.reset();
						showCustomerViewByTitle('指标值管理');
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
        handler:function(){
			showCustomerViewByTitle('指标值管理');
        }
	}]
	
});

//指标值修改form
var editForm = new Ext.form.FormPanel({
    frame: true,	// 是否渲染表单面板背景色
    labelAlign: 'right',	// 标签对齐方式
    buttonAlign: 'center',
//    autoHeight: true,
    height: '100%',
    defaults: {
        anchor:'0'
    },
    items: [
        {	xtype: 'compositefield',
	        fieldLabel: '指标值',
	        anchor:'-20',
	        defaults: {
            	flex: 1
            },
		    items: [
	        	{xtype:'textfield',name:'INDEX_VALUE1',labelStyle:'text-align:right;',width:'270',id:'INDEX_VALUE1'},
		    	{xtype:'displayfield',value: '-'},
	        	{xtype:'textfield',name:'INDEX_VALUE2',labelStyle:'text-align:right;',width:'270',id:'INDEX_VALUE2'}
		    ]
		},
        {xtype:'textfield',fieldLabel:'指标值名称',name:'INDEX_VALUE_NAME',labelStyle:'text-align:right;',anchor:'-20',allowBlank:false,id:'INDEX_VALUE_NAME'},
        {xtype:'textfield',fieldLabel:'编号',name:'ID',labelStyle:'text-align:right;',hidden:true,id:'ID'},
        {xtype:'textfield',fieldLabel:'属性编码',name:'ATTRI_ID',labelStyle:'text-align:right;',hidden:true,id:'ATTRI_ID'},
        {xtype: 'displayfield',fieldLabel:'提示',name:'QTIPS',labelStyle:'text-align:right;',
        	value:'指标值填写规则：如果是普通值，请在指标值前框输入内容；如果是数值范围，请在前后框输入区间数值。'},
	],
	buttons: [{
		text:'保存',
        handler:function(){
			if (!editForm.form.isValid()) {
				Ext.Msg.alert('提示', '输入不合法，请重新输入！');
				return false;
			}
    	 	var indexValue;
    	 	var id = editForm.form.findField('ID').getValue();
    	 	var attriId = nodeNow.attributes.ATTRI_ID;
    	 	var indexValueName = editForm.form.findField('INDEX_VALUE_NAME').getValue();
        	var minv = editForm.form.findField('INDEX_VALUE1').getValue();//获取字段值
        	var maxv = editForm.form.findField('INDEX_VALUE2').getValue();
        	
        	if(minv == '' && maxv == ''){
				Ext.Msg.alert('提示','指标值不可为空！');
				return false;
        	}
        	var reg = /^[0-9]+$/;
        	if(minv == '' && !reg.test(maxv)){
				Ext.Msg.alert('提示','数值型指标值应是范围！');
				return false;
        	}
        	
        	if(minv != '' && maxv != ''){
	        	if(reg.test(minv) && reg.test(maxv)){
        			indexValue = minv + '-' + maxv;
	        	}else if(reg.test(minv) && !reg.test(maxv)){
					Ext.Msg.alert('提示','数值型指标值应都是数字！');
					return false;
	        	}else if(reg.test(maxv) && !reg.test(minv)){
					Ext.Msg.alert('提示','数值型指标值应都是数字！');
					return false;
	        	}else{
        			indexValue = minv;
	        	}
        	}else{
        		indexValue = minv;
        	}
        	
        	Ext.MessageBox.confirm('提示','确认保存上述信息？',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				 	return false;
				} 
				Ext.Msg.wait('正在保存，请稍后......','系统提示');
		    	Ext.Ajax.request({
					url : basepath + '/customerAttriItem!save.json',
					params : {
						'id':id,
						'attriId':attriId,
						'indexValue':indexValue,
						'indexValueName':indexValueName
					},
					method : 'POST',
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function(response) {
						Ext.Msg.alert('提示', '操作成功!');
						editForm.form.reset();
						showCustomerViewByTitle('指标值管理');
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
        handler:function(){
			showCustomerViewByTitle('指标值管理');
        }
	}]	
});
/*********************************指标值嵌入form结束****************************************/

var customerView = [
/*********************************客户属性维护开始(tree操作)****************************************/
{
	title:'新增客户属性',
	hideTitle:true,
	type:'form',
	autoLoadSeleted : true,
	groups:[{
		columnCount : 1,
		fields:[
				{name:'ATTRI_ID',text:'属性ID'},
				{name:'ATTRI_NAME',text:'属性名称',allowBlank:false},
		        {name:'ATTRI_STATE',text:'属性状态',translateType:'ATTR001',allowBlank:false},
		        {name:'UP_ATTRI_ID',text:'上层类别',xtype: 'wcombotree',innerTree:'CUSTATTRTREECOMBO',showField:'text',hideField:'ATTRI_ID',allowBlank:false},
		        {name:'ATTRI_LEVEL',text:'属性层级'}
		],
		fn:function(ATTRI_ID,ATTRI_NAME,ATTRI_STATE,UP_ATTRI_ID,ATTRI_LEVEL){
			ATTRI_ID.hidden = true;
			ATTRI_LEVEL.hidden = true;
			return [ATTRI_NAME,ATTRI_STATE,UP_ATTRI_ID,ATTRI_LEVEL,ATTRI_ID];
		}
	}],
	formButtons:[{
		text:'保存',
		fn : function(formPanel,basicForm){
			if (!formPanel.getForm().isValid()) {
                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                return false;
            }
            if(nodeNowCheck.attributes.text == '客户属性'){
            	formPanel.getForm().findField('ATTRI_LEVEL').setValue(1);
            }else{
            	formPanel.getForm().findField('ATTRI_LEVEL').setValue(Number(nodeNowCheck.attributes.ATTRI_LEVEL) + 1);            
            }
			var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data, 1);
			Ext.Ajax.request({
				url : basepath + '/customerAttriTree!create.json',
				method : 'POST',
				params : commintData,
				success : function() {
					Ext.Msg.alert('提示','操作成功！');
					window.location.reload();
					//更新左侧树
//					Ext.Ajax.request({
//						url : basepath + '/customerAttriTree!getPid.json',
//						method : 'GET',
//						success : function(response) {
//							var nodeArra = Ext.util.JSON.decode(response.responseText);
//							var node = {};
//							node.id = nodeArra.pid;
//				            node.ATTRI_ID = nodeArra.pid;
//							node.ATTRI_NAME = data.ATTRI_NAME;
//							node.UP_ATTRI_ID = data.UP_ATTRI_ID;
//							node.ATTRI_LEVEL = data.ATTRI_LEVEL;
//							node.ATTRI_STATE = data.ATTRI_STATE;
//							leftTree.resloader.addNode(node);
//							leftTree.addNode(node);
//						}
//					});
				}
			});
		}
	},{
		text:'关闭',
		fn : function(formPanel){
			 hideCurrentView();
		}
	}]	
},
{
	title:'修改客户属性',
	hideTitle:true,
	type:'form',
	autoLoadSeleted : true,
	groups:[{
		columnCount : 1,
		fields:[
				{name:'ATTRI_ID',text:'属性ID'},
				{name:'ATTRI_NAME',text:'属性名称',allowBlank:false},
		        {name:'ATTRI_STATE',text:'属性状态',translateType:'ATTR001',allowBlank:false},
		        {name:'ATTRI_LEVEL',text:'属性层级'},
		        {name:'UP_ATTRI_ID',text:'上层类别',xtype: 'wcombotree',innerTree:'CUSTATTRTREECOMBO',showField:'text',hideField:'ATTRI_ID',allowBlank:false
//			        ,listeners:{//本段代码无效
//			        	'change' : function(attr){
//			        		var v = attr.value;nodeNowCheck
//			        		if(v != undefined && v.indexOf("2") == 0){		        			
//						 		Ext.Msg.alert('提示','该节点不可选择！');
//			        			return false;
//			        		}
//			        		if(v == undefined){
//								getCurrentView().contentPanel.getForm().findField('ATTRI_LEVEL').setValue(1);
//			        		}else{
//			        			getCurrentView().contentPanel.getForm().findField('ATTRI_LEVEL').setValue(Number(v.substr(0,1))+1);
//			        		}
//			        	}
//			        }
		        }
		],
		fn:function(ATTRI_ID,ATTRI_NAME,ATTRI_STATE,ATTRI_LEVEL,UP_ATTRI_ID){
			ATTRI_ID.hidden = true;
			ATTRI_LEVEL.hidden = true;
			return [ATTRI_ID,ATTRI_NAME,ATTRI_STATE,ATTRI_LEVEL,UP_ATTRI_ID];
		}
	}],
	formButtons:[{
		text:'保存',
		fn : function(formPanel,basicForm){
			if (!formPanel.getForm().isValid()) {
                Ext.MessageBox.alert('提示','输入有误,请检查输入项');
                return false;
            }
            if(nodeNowCheck == ''){
            	//说明修改属性的时候没有更改上级节点
            }else{
	            if(nodeNowCheck.attributes.text == '客户属性'){
	            	formPanel.getForm().findField('ATTRI_LEVEL').setValue(1);
	            }else{
	            	formPanel.getForm().findField('ATTRI_LEVEL').setValue(Number(nodeNowCheck.attributes.ATTRI_LEVEL) + 1);            
	            }
            }
			var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data, 1);
			Ext.Ajax.request({
				url : basepath + '/customerAttriTree!create.json',
				method : 'POST',
				params : commintData,
				success : function() {
					Ext.Msg.alert('提示','操作成功！');
					//更新左侧树
//					var node = {};
//		            node.id = data.ATTRI_ID;
//		            node.ATTRI_ID = data.ATTRI_ID;
//					node.ATTRI_NAME = data.ATTRI_NAME;
//					node.UP_ATTRI_ID = data.UP_ATTRI_ID;
//					node.ATTRI_LEVEL = data.ATTRI_LEVEL;
//					node.ATTRI_STATE = data.ATTRI_STATE;
//					leftTree.editNode(node);
//			 		hideCurrentView();
					window.location.reload();
				}
			});
		}
	},{
		text:'关闭',
		fn : function(formPanel){
			 hideCurrentView();
		}
	}]	
},
{
	title:'客户属性详情',
	hideTitle:true,
	type:'form',
	autoLoadSeleted : true,
	groups:[{
		columnCount : 1,
		fields:[   
				{name:'ATTRI_ID',text:'属性ID'},
				{name:'ATTRI_NAME',text:'属性名称',allowBlank:false},
		        {name:'ATTRI_STATE',text:'属性状态',translateType:'ATTR001',allowBlank:false},
		        {name:'ATTRI_LEVEL',text:'属性层级'},
		        {name:'UP_ATTRI_ID',text:'上层类别',xtype: 'wcombotree',innerTree:'CUSTATTRTREECOMBO',showField:'text',hideField:'ATTRI_ID',allowBlank:false}
		],
		fn:function(ATTRI_ID,ATTRI_NAME,ATTRI_STATE,ATTRI_LEVEL,UP_ATTRI_ID){
			ATTRI_ID.hidden = true;
			ATTRI_LEVEL.hidden = true;
			
			ATTRI_NAME.readOnly = true;
			ATTRI_NAME.cls = 'x-readOnly';
			
			ATTRI_STATE.readOnly = true;
			ATTRI_STATE.cls = 'x-readOnly';
			
			UP_ATTRI_ID.readOnly = true;
			UP_ATTRI_ID.cls = 'x-readOnly';
			return [ATTRI_ID,ATTRI_NAME,ATTRI_STATE,ATTRI_LEVEL,UP_ATTRI_ID];
		}
	}],
	formButtons:[{
		text:'关闭',
		fn : function(formPanel){
			 hideCurrentView();
		}
	}]
	
},
/*********************************客户属性维护结束(tree操作)****************************************/

/*********************************指标值管理开始****************************************/
{
	title : '指标值管理',
	hideTitle : true,
	type : 'grid',
	url : basepath + '/customerAttriItem.json',
	isCsm : false,
	frame : true,
	fields : {
		fields:[
			{name:'ID',text:'指标值ID',allowBlank:false,editable:true,hidden:true},
			{name:'ATTRI_ID',text:'属性ID',allowBlank:false,hidden:true},
			{name:'INDEX_VALUE',text:'指标值',resutlWidth:150,allowBlank:false},
			{name:'INDEX_VALUE_NAME',text:'指标值名称',resutlWidth:150,allowBlank:false},
			{name:'INDEX_VALUE1',text:'指标值1',resutlWidth:150,hidden:true},
			{name:'INDEX_VALUE2',text:'指标值2',resutlWidth:150,hidden:true}
		]
	},
	gridButtons:[{
		text:'新增',
		id:'targetAdd',
		fn : function(grid){
			createForm.form.reset();
			showCustomerViewByTitle('指标值新增');
		}
	},{
		text:'修改',
		fn : function(grid){
			var selectLength = grid.getSelectionModel().getSelections().length;
			var selectValue = grid.getSelectionModel().getSelections()[0];
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条数据！');
				return false;
			}
			showCustomerViewByTitle('指标值修改');
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
			var id = selectValue.data.ID;
			Ext.MessageBox.confirm('提示','确认删除选中的记录！',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				 	return false;
				} 
				Ext.Msg.wait('正在保存，请稍后......','系统提示');
		    	Ext.Ajax.request({
					url : basepath + '/customerAttriItem!delete.json',
					params : {
						'id':id
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
		}
	}]
},{
  	title:'指标值新增',
	type: 'form',
	hideTitle : true,
	items:[createForm]
}
,{
  	title:'指标值修改',
	type: 'form',
	hideTitle : true,
	items:[editForm]
}
/*********************************指标值管理结束****************************************/

/*********************************方案管理开始****************************************/
,{
	title : '方案管理',
	hideTitle : true,
	type : 'grid',
	url : basepath + '/customerScheme.json',
	isCsm : false,
	frame : true,
	fields : {
		fields:[
			{name:'SCHEME_ID',text:'方案编号',hidden:true},
			{name:'SCHEME_NAME',text:'方案名称', width:150},
			{name:'SCHEME_STATE',text:'方案状态',hidden:true},
			{name:'F_VALUE',text:'方案状态'}
		]
	},
	gridButtons:[{
		text:'新增',
		fn : function(grid){
			needReset = true;
			showCustomerViewByTitle('新增方案');
		}
	},{
		text:'修改',
		fn : function(grid){
			var selectLength = grid.getSelectionModel().getSelections().length;
			var selectValue = grid.getSelectionModel().getSelections()[0];
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条数据！');
				return false;
			}
			showCustomerViewByTitle('修改方案');
		}
	},{
		text:'查看',
		fn : function(grid){
			var selectLength = grid.getSelectionModel().getSelections().length;
			var selectValue = grid.getSelectionModel().getSelections()[0];
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条数据！');
				return false;
			}
			showCustomerViewByTitle('查看方案');
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
			var schemeId = selectValue.data.SCHEME_ID;
			Ext.MessageBox.confirm('提示','确认删除选中的记录吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				 	return false;
				}
				Ext.Msg.wait('正在保存，请稍后......','系统提示');
		    	Ext.Ajax.request({
					url : basepath + '/customerScheme!delete.json',
					params : {
						'id':schemeId
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
		}
	}]
	
},{
	title : '新增方案',
	type : 'form',
	hideTitle : true,
	autoLoadSeleted : true,
	groups : [{
		columnCount : 1 ,
		fields : [
			{name:'SCHEME_ID',text:'方案编号'},
			{name:'SCHEME_NAME',text:'方案名称',allowBlank:false},
			{name:'SCHEME_STATE',text:'方案状态',translateType:'ATTR001',allowBlank:false}
		],
		fn : function(SCHEME_ID, SCHEME_NAME,SCHEME_STATE){
			SCHEME_ID.hidden = true;
			return [SCHEME_ID, SCHEME_NAME,SCHEME_STATE];
		}
	}],
	formButtons:[{
		text:'下一步',
		fn: function(formPanel,basicForm){
			needReset = false;
			if (!basicForm.isValid()) {
				Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
    		 	return false;
    	 	}
    	 	var schemeId = basicForm.findField('SCHEME_ID').getValue();
    	 	var schemeName = basicForm.findField('SCHEME_NAME').getValue();
    	 	var schemeState = basicForm.findField('SCHEME_STATE').getValue();
			//执行保存，返回SCHEME_ID
			Ext.MessageBox.confirm('提示','你填写的信息将要被保存，确定要执行吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				 	return false;
				}
				Ext.Msg.wait('正在保存，请稍后......','系统提示');
		    	Ext.Ajax.request({
					url : basepath + '/customerScheme!create.json',
					params : {
						'schemeId':schemeId,
						'schemeName':schemeName,
						'schemeState':schemeState
					},
					method : 'POST',
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function(response) {
						//成功保存后获取方案编号
    					Ext.Ajax.request({
    				    	url: basepath +'/customerScheme!getPid.json',
    				        waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
    					    success:function(response){
    							var tempId = Ext.util.JSON.decode(response.responseText).pid;
    							tempSchemeId = tempId;
								Ext.Msg.alert('提示', '操作成功');
								//设置方案编号
								basicForm.findField('SCHEME_ID').setValue(tempId);
								tempSchemeInfo.data = basicForm.getFieldValues();
								showCustomerViewByTitle('方案属性新增');
					        }
    					});
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
			showCustomerViewByTitle('方案管理');
		}		
	}]
},{
	title : '方案属性新增',
	hideTitle : true,
	type : 'form',
	items : [checkAddTree]
},{
	title : '修改方案',
	type : 'form',
	hideTitle : true,
	autoLoadSeleted : true,
	groups : [{
		columnCount : 1 ,
		fields : [
			{name:'SCHEME_ID',text:'方案编号'},
			{name:'SCHEME_NAME',text:'方案名称',allowBlank:false},
			{name:'SCHEME_STATE',text:'方案状态',translateType:'ATTR001',allowBlank:false}
		],
		fn : function(SCHEME_ID, SCHEME_NAME, SCHEME_STATE){
			SCHEME_ID.hidden = true;
			return [SCHEME_ID, SCHEME_NAME, SCHEME_STATE];
		}
	}],
	formButtons:[{
		text:'下一步',
		fn: function(formPanel,basicForm){
			if (!basicForm.isValid()) {
				Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
    		 	return false;
    	 	}
    	 	var schemeId = basicForm.findField('SCHEME_ID').getValue();
    	 	var schemeName = basicForm.findField('SCHEME_NAME').getValue();
    	 	var schemeState = basicForm.findField('SCHEME_STATE').getValue();
			//执行保存，返回SCHEME_ID
			Ext.MessageBox.confirm('提示','你填写的信息将要被保存，确定要执行吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				 	return false;
				}
				Ext.Msg.wait('正在保存，请稍后......','系统提示');
		    	Ext.Ajax.request({
					url : basepath + '/customerScheme!create.json',
					params : {
						'schemeId':schemeId,
						'schemeName':schemeName,
						'schemeState':schemeState
					},
					method : 'POST',
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function(response) {
						//成功保存后获取方案编号
    					Ext.Ajax.request({
    				    	url: basepath +'/customerScheme!getPid.json',
    				        waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
    					    success:function(response){
    							var tempId = Ext.util.JSON.decode(response.responseText).pid;
    							tempSchemeId = tempId;
								Ext.Msg.alert('提示', '操作成功');
								//设置方案编号
								basicForm.findField('SCHEME_ID').setValue(tempId);
								tempSchemeInfo.data = basicForm.getFieldValues();
								showCustomerViewByTitle('方案属性修改');
					        }
    					});
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
			showCustomerViewByTitle('方案管理');
		}		
	}]
},{
	title : '方案属性修改',
	hideTitle : true,
	type : 'form',
	items : [checkAlterTree]
},{
	title : '查看方案',
	type : 'form',
	hideTitle : true,
	autoLoadSeleted : true,
	groups : [{
		columnCount : 1 ,
		fields : [
			{name:'SCHEME_ID',text:'方案编号'},
			{name:'SCHEME_NAME',text:'方案名称',allowBlank:false},
			{name:'SCHEME_STATE',text:'方案状态',translateType:'ATTR001',allowBlank:false}
		],
		fn : function(SCHEME_ID, SCHEME_NAME, SCHEME_STATE){
			SCHEME_ID.hidden = true;
			
			SCHEME_NAME.readOnly = true;
			SCHEME_NAME.cls = 'x-readOnly';
			
			SCHEME_STATE.readOnly = true;
			SCHEME_STATE.cls = 'x-readOnly';
			return [SCHEME_ID, SCHEME_NAME, SCHEME_STATE];
		}
	},{
		columnCount : 1,
		fields : [],
		fn : function(){
			return [schemeAttriGrid];
		}
	}],
	formButtons:[{
		text:'返回',
		fn: function(formPanel,basicForm){
			showCustomerViewByTitle('方案管理');
		}		
	}]
}
/*********************************方案管理结束****************************************/
];

/******************************方案属性配置查看grid开始************************************/
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 35
});

var schemeAttriStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/customerSchemeAttri.json'
	}),
	reader : new Ext.data.JsonReader(
		{
			totalProperty : 'json.count', 
			root : 'json.data'
		},
		[
			{name : 'SA_ID', mapping : 'SA_ID'},
			{name : 'SCHEME_ID', mapping : 'SCHEME_ID'},
			{name : 'ATTRI_ID', mapping : 'ATTRI_ID'},
			{name : 'INDEX_ID', mapping : 'INDEX_ID'},
			{name : 'ATTRI_NAME', mapping : 'ATTRI_NAME'},
			{name : 'INDEX_NAME', mapping : 'INDEX_NAME'}
		]
	)
});

//gridtable中的列定义
var schemeAttri_cm = new Ext.grid.ColumnModel([
	rownum,
	{header:'方案属性编号', dataIndex:'SA_ID', width:120, sortable:true, hidden:true},
	{header:'方案编号', dataIndex:'SCHEME_ID', width:120, sortable:true, hidden:true},
	{header:'属性编号', dataIndex:'ATTRI_ID', width:120, sortable:true, hidden:true},
	{header:'指标编号', dataIndex:'INDEX_ID', width:120, sortable:true, hidden:true},
	{header:'属性名称', dataIndex:'ATTRI_NAME', width:120, sortable:true},
	{header:'指标名称', dataIndex:'INDEX_NAME', width:120, sortable:true}
]);

//每页显示条数下拉选择框
//var schemeAttriPagesize_combo = new Ext.form.ComboBox({
//	name : 'pagesize',
//	triggerAction : 'all',
//	mode : 'local',
//	store : new Ext.data.ArrayStore({
//		fields : [ 'value', 'text' ],
//		data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
//				[ 100, '100条/页' ], [ 250, '250条/页' ], [ 500, '500条/页' ]]
//	}),
//	valueField : 'value',
//	displayField : 'text',
//	value : '100',
//	editable : false,
//	width : 85
//});

//改变每页显示条数reload数据
//schemeAttriPagesize_combo.on("select", function(comboBox) {
//	schemeAttriBbar.pageSize = parseInt(schemeAttriPagesize_combo.getValue());
//	schemeAttriStore.reload({
//		params : {
//			start : 0,
//			limit : parseInt(schemeAttriPagesize_combo.getValue())
//		}
//	});
//});

//分页工具栏
//var schemeAttriBbar = new Ext.PagingToolbar({
//	pageSize : 20,
//	store : schemeAttriStore,
//	displayInfo : true,
//	displayMsg : '显示{0}条到{1}条,共{2}条',
//	emptyMsg : "没有符合条件的记录",
//	items : [ '-', '&nbsp;&nbsp;', schemeAttriPagesize_combo ]
//});

//GRID
var schemeAttriGrid = new Ext.grid.GridPanel({
//	title : '交易信息',
	height : 320,
	frame : true,
	store : schemeAttriStore,
	stripeRows : true, // 斑马线
	loadMask : true,
	cm : schemeAttri_cm,
//	bbar:schemeAttriBbar,
	region : 'center',
//	clicksToEdit : 1,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
/******************************方案属性配置查看结束************************************/

/**
 * 自定义工具条上按钮
 * 注：批量选择未实现,目前只支持单条删除
 */
var tbar = [{
	text : '指标值管理',
	handler : function(){
		if (nodeNow == '' || nodeNow == null) {
			Ext.Msg.alert('提示', '请选择客户属性！');
			return false;
		}
		if (!nodeNow.leaf) {
			Ext.Msg.alert('提示', '请选择客户属性！');
			return false;			
		}
		showCustomerViewByTitle('指标值管理');
	}
},{
	text : '方案管理',
	handler : function(){
		showCustomerViewByTitle('方案管理');
	}	
},{
	/**
	 * 删除
	 */
	text : '删除指标',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			var selectedData = getSelectedData().data;
			var id = selectedData.INDEX_ID;
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return;
				}
				Ext.Ajax.request({
					url: basepath + '/customerAttriManager!delete.json?id=' + id,
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
}];

/**
 * 边缘面板配置
 */
var edgeVies = {
	left : {
		/**
		 * 左边机构树展示
		 */
		width : 200,
		layout : 'form',
		items : [leftTree]
	}
};


/**
 * 结果域面板滑入前触发,系统提供listener事件方法
 * @param {} view
 * @return {Boolean}
 */
var beforeviewshow = function(view){	
	if (view._defaultTitle == '修改客户属性' || view._defaultTitle == '客户属性详情') {
		if (nodeNow == '' || nodeNow == null) {
			Ext.Msg.alert('提示', '请选择客户属性！');
			return false;
		}else if (nodeNow.text == '客户属性') {
			Ext.Msg.alert('提示', '根类别无法修改和查看！');
			return false;
		}
	}
	
	if(view.baseType == 'createView'){
		if (nodeNow == '' || nodeNow == null || !nodeNow.leaf) {
			Ext.Msg.alert('提示', '请选择客户属性！');
			return false;
		}
		view.contentPanel.getForm().findField('ATTRI_ID').setValue(nodeNow.attributes.ATTRI_ID);
	}else if(view.baseType == 'editView' || view.baseType == 'detailView') {
		if(!getSelectedData() || getAllSelects().length > 1){
			Ext.Msg.alert('提示信息','请选择一条数据后操作！');
			return false;
		}
		JsContext.indexId = getSelectedData().data.ATTRI_ID;
	}
	
	if(view._defaultTitle=='指标值管理'){
		var fLookupId = nodeNow.attributes.ATTRI_ID;
		view.setParameters ({
			fLookupId:fLookupId
		});
	}
	
	if(view._defaultTitle=='指标值修改'){
		var selectValue = getCustomerViewByTitle("指标值管理").grid.getSelectionModel().getSelections()[0];
		Ext.getCmp('ID').setValue(selectValue.data.ID);
		Ext.getCmp('ATTRI_ID').setValue(selectValue.data.ATTRI_ID);
		Ext.getCmp('INDEX_VALUE1').setValue(selectValue.data.INDEX_VALUE1);
		Ext.getCmp('INDEX_VALUE2').setValue(selectValue.data.INDEX_VALUE2);
		Ext.getCmp('INDEX_VALUE_NAME').setValue(selectValue.data.INDEX_VALUE_NAME);
	}
	
	if(view._defaultTitle == '方案管理'){
		view.setParameters ({
		});		
	}
	
	if(view._defaultTitle == '查看方案'){
		var selectValue = getCustomerViewByTitle("方案管理").grid.getSelectionModel().getSelections()[0];
		var schemeId = selectValue.data.SCHEME_ID;
		schemeAttriStore.load({
			params:{schemeId:schemeId}
		});
	}
};

/**
 * 结果域面板滑入后触发,系统提供listener事件方法
 * @param {} view
 * @return {Boolean}
 */
var viewshow = function(view){
	if(view._defaultTitle == '客户属性详情' || view._defaultTitle == '修改客户属性'){
		view.contentPanel.getForm().findField('ATTRI_ID').setValue(nodeNow.attributes.ATTRI_ID);
		view.contentPanel.getForm().findField('ATTRI_NAME').setValue(nodeNow.attributes.ATTRI_NAME);
		view.contentPanel.getForm().findField('ATTRI_STATE').setValue(nodeNow.attributes.ATTRI_STATE);
		if(nodeNow.attributes.UP_ATTRI_ID == '0'){
			view.contentPanel.getForm().findField('UP_ATTRI_ID').setValue('客户属性');
		}else{
			view.contentPanel.getForm().findField('UP_ATTRI_ID').setValue(nodeNow.attributes.UP_ATTRI_ID);
		}
		view.contentPanel.getForm().findField('ATTRI_LEVEL').setValue(nodeNow.attributes.ATTRI_LEVEL);
	}
	
	if(view._defaultTitle=='新增方案'){
		if(needReset){
			if(view.contentPanel.form.getEl()){
				view.contentPanel.form.getEl().dom.reset();
			}
		}else{
			view.contentPanel.form.loadRecord(tempSchemeInfo);
		}
	}
	
	if(view._defaultTitle=='修改方案' || view._defaultTitle == '查看方案'){
		var selectValue = getCustomerViewByTitle("方案管理").grid.getSelectionModel().getSelections()[0];
		view.contentPanel.getForm().findField('SCHEME_ID').setValue(selectValue.data.SCHEME_ID);
		view.contentPanel.getForm().findField('SCHEME_NAME').setValue(selectValue.data.SCHEME_NAME);
		view.contentPanel.getForm().findField('SCHEME_STATE').setValue(selectValue.data.SCHEME_STATE);
	}
	
	if(view._defaultTitle == '方案属性新增'){
		for(var i=0;i<checkAlterTree.root.childNodes.length;i++){//清空原角色选择情况
			checkAddTree.root.childNodes[i].fireEvent('checkchange',checkAlterTree.root.childNodes[i],false,undefined);
		}
		checkAddTree.expandAll();//默认展开树，解决前台取不到子节点，从而不能递归check问题
	}
	if(view._defaultTitle == '方案属性修改'){
		checkAlterTree.expandAll();//默认展开树，解决前台取不到子节点，从而不能递归check问题
		checkAlterTree.expandAll();
		var selectValue = getCustomerViewByTitle("方案管理").grid.getSelectionModel().getSelections()[0];
		var schemeId = selectValue.data.SCHEME_ID;
		
		Ext.Ajax.request({//获取方案属性指标
			url : basepath + '/customerSchemeAttri!getSchemeAttri.json',
			method:'GET',
			params :{
				schemeId : schemeId
			},
			success:function(response){
				refreshCheckItem(response);
			},
			failure:function(){
				Ext.Msg.alert('提示','操作失败！');
			}
		});
	}
};

//遮罩层定义
//var lm = new Ext.LoadMask (document.body,{
//   	msg : '正在加载菜单数据,请稍等...'
//});
//lm.show();

/**
 * 保存之前处理发生变化数据
 */
var beforeSaveSet = function(){
	for(var i=0;i<delGrantsMap.length;i++){
		if(delGrantsMap[i].attributes.ATTRI_LEVEL == 3){
			var obj = {};
			obj.parentCode = delGrantsMap[i].attributes.UP_ATTRI_ID;
			obj.attriCode   = delGrantsMap[i].attributes.ATTRI_ID;
			if (delGrantsStr.length == 0) {
				delGrantsStr = obj.parentCode + ' ' +obj.attriCode;
			} else {
				delGrantsStr += ',' + obj.parentCode + ' ' +obj.attriCode;
			}
		}
	}
	for(var i=0;i<addGrantsMap.length;i++){
		if(addGrantsMap[i].attributes.ATTRI_LEVEL == 3){
			var obj = {};
			obj.parentCode  = addGrantsMap[i].attributes.UP_ATTRI_ID;
			obj.attriCode    = addGrantsMap[i].attributes.ATTRI_ID;
			if (addGrantsStr.length == 0) {
				addGrantsStr = obj.parentCode + ' ' +obj.attriCode;
			} else {
				addGrantsStr += ',' + obj.parentCode + ' ' +obj.attriCode;
			}
		}
	}
};
/**
 * 保存设置
 */
var saveSet = function(){
	beforeSaveSet();
	var schemeId = tempSchemeId;
	Ext.Ajax.request({//执行保存设置
		//增量数据操作url
		url : basepath + '/customerSchemeAttri!saveSchemeSet.json',
		method:'POST',
		params :{
			schemeId : schemeId,
			addGrantsStr : addGrantsStr,
			delGrantsStr : delGrantsStr
		},
		success:function(response){
			Ext.Msg.alert('提示','操作成功！');
				Ext.Ajax.request({//重新加载当前树
					url : basepath + '/customerSchemeAttri!getSchemeAttri.json',
					method:'GET',
					params:{
						schemeId:schemeId
						},
					success:function(response){
						refreshCheckItem(response);
					},
    				failure:function(){
    				}
				});
		},
		failure:function(){
			Ext.Msg.alert('提示','操作失败！');
		}
	});
};

/**
 * 刷新选择项
 * @param {} response
 */
var refreshCheckItem = function (response){
	schemeLoader = Ext.util.JSON.decode(response.responseText).json.data;//获取选择方案的菜单项
	for(var i=0;i<checkAlterTree.root.childNodes.length;i++){//清空原角色选择情况
		checkAlterTree.root.childNodes[i].fireEvent('checkchange',checkAlterTree.root.childNodes[i],false,undefined);
	}

	for(var i=0;i<schemeLoader.length;i++){
		var tn = checkAlterTree.root.findChild('id',schemeLoader[i].INDEX_ID,true);//树节点中包含方案的指标项
		if (tn!=undefined && tn.attributes.ATTRI_LEVEL == 3) {
			tn.fireEvent('checkchange',tn,true);
		}
	}
	delGrantsMap = [];
	addGrantsMap = [];
	addGrantsStr = '';
	delGrantsStr = '';		
};

/**
 * 重写Ext.tree.TreeNodeUI.toggleCheck方法，使其不再触发TreeNode的checkchange事件！
 * 仅限本页面使用。 
 */
Ext.override(Ext.tree.TreeNodeUI,{
	toggleCheck : function(value){
    	var cb = this.checkbox;
    	if(cb){
    		cb.checked = (value === undefined ? !cb.checked : value);
    	}
    	this.checkbox.defaultChecked = value;
    	this.node.attributes.checked = value;
	}	
});