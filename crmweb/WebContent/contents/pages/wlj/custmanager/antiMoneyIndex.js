/**
 * @description 反洗钱指标处理
 * @author luyy
 * @since 2014-07-15
 */

var url = basepath + '/antiMoney.json';

var lookupTypes = [
	'IF_FLAG',	//是否标志
	'VERIFLER_STAT',//审核状态
	'INDEX_TYPE'	//指标类型
];

var indexId = "";//指标id
var indexDic = "";//指标引用字典

var createView = false;
var editView = false;
var detailView = false;

var fields = [
	{name:'INDEX_ID',text:''},
	{name:'INDEX_CODE',text:'指标编号',dataType:'string',searchField:true},
	{name:'INDEX_NAME',text:'指标名称',dataType:'string',searchField:true},
	{name:'INDEX_TYPE',text:'指标类型',translateType:'INDEX_TYPE',searchField:true},
	{name:'INDEX_STATE',text:'是否启用',translateType:'IF_FLAG',searchField:true},
	{name:'INDEX_DIC',text:'指标引用字典',hidden:true},
	{name:'LAST_UPDATE_USER',text:''},
	{name:'LAST_UPDATE_NAME',text:'最近修改人'},
	{name:'LAST_UPDATE_TM',text:'最近修改日期',dataType:'date'},
	{name:'LAST_VERIFIER',text:''},
	{name:'VERIFLER_STAT',text:'审核状态',translateType:'VERIFLER_STAT'},
	{name:'LAST_VERIFIER_NAME',text:'最近审核人',dataType:'string'},
	{name:'LAST_VERIFY_TM',text:'最近审核日期',dataType:'date'}
];

var tbar = [{
	text:'指标配置',
	hidden:JsContext.checkGrant('fxq_zbpz'),
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			if(getSelectedData().data.VERIFLER_STAT == '1'){//审核中
				Ext.Msg.alert('提示','该指标正在复核中！');
				return false;
			}else{
				indexId = getSelectedData().data.INDEX_ID;
				indexDic = getSelectedData().data.INDEX_DIC;
				//加载指标细项
				storeAll.load({
					params:{
						indexId:indexId,
						indexDic:indexDic
					}
				});
				showCustomerViewByIndex(0);
			}
		}
	}
},{
	text:'启用',
	hidden:JsContext.checkGrant('fxq_qy'),
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			var ID = '';
			for (var i=0;i<getAllSelects().length;i++){
				if(getAllSelects()[i].data.INDEX_STATE != '0' || getAllSelects()[i].data.VERIFLER_STAT != '2'){
					Ext.Msg.alert('提示','只能选择[复核通过]并且未启用的记录！');
					return false;
				}
				ID += getAllSelects()[i].data.INDEX_ID;
				ID += ",";
			}
			ID = ID.substring(0, ID.length-1);
			Ext.MessageBox.confirm('提示','确定启用吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/antiMoney!updateStat.json?type=1&idStr='+ID,                                
                    success : function(){
                        Ext.Msg.alert('提示', '启用成功');
                        reloadCurrentData();
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '启用失败');
                        reloadCurrentData();
                    }
                });
			});			
		
		}
	}
},{
	text:'禁用',
	hidden:JsContext.checkGrant('fxq_jy'),
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			var ID = '';
			for (var i=0;i<getAllSelects().length;i++){
				if(getAllSelects()[i].data.INDEX_STATE != '1' || getAllSelects()[i].data.VERIFLER_STAT != '2'){
					Ext.Msg.alert('提示','只能选择[复核通过]并且启用的记录！');
					return false;
				}
				ID += getAllSelects()[i].data.INDEX_ID;
				ID += ",";
			}
			ID = ID.substring(0, ID.length-1);
			Ext.MessageBox.confirm('提示','确定禁用吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/antiMoney!updateStat.json?type=0&idStr='+ID,                                
                    success : function(){
                        Ext.Msg.alert('提示', '禁用成功');
                        reloadCurrentData();
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '禁用失败');
                        reloadCurrentData();
                    }
                });
			});			
		
		}
	}
},{
	text:'查看指标子项',
	hidden:JsContext.checkGrant('fxq_ckzbzx'),
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			indexId = getSelectedData().data.INDEX_ID;
			indexDic = getSelectedData().data.INDEX_DIC;
			if(getSelectedData().data.VERIFLER_STAT == '1'){//审核中
				//加载指标细项  当前值 和审批中的值
				storeOld.load({
					params:{
						indexId:indexId,
						indexDic:indexDic
					}
				});
				storeApply.load({
					params:{
						indexId:indexId,
						indexDic:indexDic
					}
				});
				showCustomerViewByIndex(1);
			}else{
				//加载指标细项
				storeOld.load({
					params:{
						indexId:indexId,
						indexDic:indexDic
					}
				});
				showCustomerViewByIndex(2);
			}
		}
	}
}];


//所有的指标子项
var storeAll = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/antiMoney!all.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'data'
	}, [
		{name:'id',mapping:'ID'},
	    {name:'indexValue',mapping:'INDEX_VALUE'},
	    {name:'indexValueName',mapping:'INDEX_VALUE_NAME'},
	    {name:'indexScore',mapping:'INDEX_SCORE'},
	    {name:'indexRight',mapping:'INDEX_RIGHT'},
	    {name:'highFlag',mapping:'HIGH_FLAG'}
	])
});
	
//当前指标配置
var storeOld = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/antiMoney!old.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'data'
	}, [
		{name:'id',mapping:'ID'},
	    {name:'indexValue',mapping:'INDEX_VALUE'},
	    {name:'indexValueName',mapping:'INDEX_VALUE_NAME'},
	    {name:'indexScore',mapping:'INDEX_SCORE'},
	    {name:'indexRight',mapping:'INDEX_RIGHT'},
	    {name:'highFlag',mapping:'HIGH_FLAG'}
	])
});
	
//复核中的指标配置
var storeApply = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/antiMoney!apply.json'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'data'
	}, [
		{name:'id',mapping:'ID'},
	    {name:'indexValue',mapping:'INDEX_VALUE'},
	    {name:'indexValueName',mapping:'INDEX_VALUE_NAME'},
	    {name:'indexScore',mapping:'INDEX_SCORE'},
	    {name:'indexRight',mapping:'INDEX_RIGHT'},
	    {name:'highFlag',mapping:'HIGH_FLAG'}
	])
});


/***********子项配置面板**************/
//备注（数据字典类型）store
var columnTypeStore = new Ext.data.Store( {
	restful : true,
	autoLoad : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/lookup.json?name=IF_FLAG'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

var columnrownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 35
	});   
//数据集方案字段设置CM
var cmodel = new Ext.grid.ColumnModel([
     columnrownum, 
    {header :'id',dataIndex:'ID',width:130,sortable : false,hidden:true},
	{header :'indexValue',dataIndex:'indexValue',width:130,sortable : false,hidden:true},
	{header :'指标取值',dataIndex:'indexValueName',width:130,sortable : false},
	{header :'指标得分',dataIndex:'indexScore',width:130,sortable : false
		,editor:new Ext.form.NumberField({allowBlank : true,decimalPrecision : 0})},
	{header :'指标权重(%)',dataIndex:'indexRight',width:130,sortable : false,
		editor:new Ext.form.NumberField({allowBlank : true,minValue:0,maxValue:100,decimalPrecision : 4})},
	{header :'是否高风险',dataIndex:'highFlag',width:130,sortable : false,
			editor :{
		        	xtype:'combo',
		        	store : columnTypeStore,
		        	mode : 'local',
		        	triggerAction : 'all',
		        	valueField : 'key',
		        	displayField : 'value',
		        	forceSelection:true,
					resizable:true,
					typeAhead : true,
					emptyText : '请选择',
		        	listeners:{
		        	select:function(){
		        		var valuefind = this.value;
		        		this.fireEvent('blur',this);
		        	}
		        	}},sortable : false,
		        	renderer:function(val){
		        	if(val!=''){
		        		var stolength = columnTypeStore.data.items;
		        		var i=0;
		        		for(i=0;i< stolength.length;i++){
		        			if(stolength[i].data.key==val){
		        					return stolength[i].data.value;
		        			}
		        		}
		        	}
		        return val;	
		        }
	 }
]);
var AllGrid = new Ext.grid.EditorGridPanel( {
	frame : true,
	clicksToEdit : 1,
	height : 400,
	store : storeAll,
	loadMask : true,
	cm : cmodel,
	buttonAlign : 'center',
	buttons : [{
		text : '保存',
		handler : function() {
			var json1 = {'INDEX_VALUE':[]};
			var json2 = {'INDEX_SCORE':[]};
			var json3 = {'INDEX_RIGHT':[]};
			var json7 = {'HIGH_FLAG':[]};
			var count = 0;
			for(var i=0;i<storeAll.getCount();i++){
	             var temp=storeAll.getAt(i);
	             if((temp.data.indexScore != '' || temp.data.indexScore == '0') && (temp.data.indexRight != '' || temp.data.indexRight == '0')){
	            	 json1.INDEX_VALUE.push(temp.data.indexValue);
	                 json2.INDEX_SCORE.push(temp.data.indexScore);
	                 json3.INDEX_RIGHT.push(temp.data.indexRight);
	                 json7.HIGH_FLAG.push(temp.data.highFlag);
	                 count ++;
	             }else if((temp.data.indexScore == '' && temp.data.indexRight != '') || (temp.data.indexScore != '' && temp.data.indexRight == '')){
	            	 Ext.Msg.alert('提示','指标项:'+temp.data.indexValueName+' 的指标分值和指标权重没有一起设置！');
	 				 return false;
	             }
	        }
			if(count == 0){
				Ext.Msg.alert('提示','没有设置指标子项信息！');
	 			return false;
			}
			Ext.Msg.wait('正在保存，请稍后......','系统提示');
			Ext.Ajax.request({
	             url : basepath + '/antiMoney!save.json?indexId='+indexId,
	             method : 'GET',
	             waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
	             params:{
	            	 INDEX_VALUEs:Ext.encode(json1),
					 INDEX_SCOREs:Ext.encode(json2),
					 INDEX_RIGHTs:Ext.encode(json3),
					 HIGH_FLAGs:Ext.encode(json7)
	             },
	             success : function(response) {
						var ret = Ext.decode(response.responseText);
						var instanceid = ret.instanceid;//流程实例ID
						var currNode = ret.currNode;//当前节点
						var nextNode = ret.nextNode;//下一步节点
						selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
				 },
	             failure : function(response) {
	                 Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	             }
	         });
		}
	}]
});	
/***********子项查看面板**************/
var colold= new Ext.grid.RowNumberer({
	header : 'No.',
	width : 35
	});   
//数据集方案字段设置CM
var cmold = new Ext.grid.ColumnModel([
     colold, 
    {header :'id',dataIndex:'ID',width:130,sortable : false,hidden:true},
	{header :'indexValue',dataIndex:'indexValue',width:130,sortable : false,hidden:true},
	{header :'指标取值',dataIndex:'indexValueName',width:130,sortable : false},
	{header :'指标得分',dataIndex:'indexScore',width:130,sortable : false},
	{header :'指标权重(%)',dataIndex:'indexRight',width:130,sortable : false},
	{header :'高风险标识',dataIndex:'highFlag',width:130,sortable : false,renderer:function(value){
			var val = translateLookupByKey("IF_FLAG",value);
			return val?val:"";
	}}
]);
var oldGrid = new Ext.grid.GridPanel( {
	frame : true,
	clicksToEdit : 1,
	height : 400,
	store : storeOld,
	loadMask : true,
	cm : cmold
});	
/***********子项查看面板（复核中的）**************/
var colold1= new Ext.grid.RowNumberer({
	header : 'No.',
	width : 35
	});   
//数据集方案字段设置CM
var cmold1 = new Ext.grid.ColumnModel([
     colold1, 
    {header :'id',dataIndex:'ID',width:130,sortable : false,hidden:true},
	{header :'indexValue',dataIndex:'indexValue',width:130,sortable : false,hidden:true},
	{header :'指标取值',dataIndex:'indexValueName',width:130,sortable : false},
	{header :'指标得分',dataIndex:'indexScore',width:130,sortable : false},
	{header :'指标权重(%)',dataIndex:'indexRight',width:130,sortable : false},
	{header :'高风险标识',dataIndex:'highFlag',width:130,sortable : false,renderer:function(value){
		var val = translateLookupByKey("IF_FLAG",value);
		return val?val:"";
    }}
]);
var oldGrid1 = new Ext.grid.GridPanel( {
	frame : true,
	height : 400,
	title:'原指标配置',
	store : storeOld,
	loadMask : true,
	cm : cmold1
});	


var colapply= new Ext.grid.RowNumberer({
	header : 'No.',
	width : 35
	});   
//数据集方案字段设置CM
var cmapply = new Ext.grid.ColumnModel([
     colapply, 
    {header :'id',dataIndex:'ID',width:130,sortable : false,hidden:true},
	{header :'indexValue',dataIndex:'indexValue',width:130,sortable : false,hidden:true},
	{header :'指标取值',dataIndex:'indexValueName',width:130,sortable : false},
	{header :'指标得分',dataIndex:'indexScore',width:130,sortable : false},
	{header :'指标权重(%)',dataIndex:'indexRight',width:130,sortable : false},
	{header :'高风险标识',dataIndex:'highFlag',width:130,sortable : false,renderer:function(value){
		var val = translateLookupByKey("IF_FLAG",value);
		return val?val:"";
     }}
]);
var applyGrid = new Ext.grid.GridPanel( {
	frame : true,
	height : 400,
	title:'新指标配置',
	store : storeApply,
	loadMask : true,
	cm : cmapply
});	
var applyPanel = new Ext.Panel({
	autoScroll : true,
	layout : 'column',
	items : [  
		{columnWidth : .5,items :[oldGrid1]},
		{columnWidth : .5,items :[applyGrid]}
	]
});

var customerView = [{
	title:'子项配置',
	hideTitle:true,
	xtype:'panel',
	items:[AllGrid]
},{
	title:'查看指标子项(审核中)',
	hideTitle:true,
	xtype:'panel',
	items:[applyPanel]
},{
	title:'查看指标子项',
	hideTitle:true,
	xtype:'panel',
	items:[oldGrid]
}];

