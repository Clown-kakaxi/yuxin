/**
 * @description 营销任务
 * @author helin
 * @since 2014-07-03
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.MarketTarget.js'	//指标选择放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.MarketCycle.js'	//指标周期选择放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.MarketTask.js'		//营销任务放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.MarketTeam.js'		//营销团队放大镜
]);

Ext.QuickTips.init();

//数据字典定义
var lookupTypes = [
	'TARGET_TYPE'		//营销指标类型
	,'MTASK_OPER_TYPE'	//营销任务执行对象类型
	,'MTASK_STAT'	//营销任务状态
	,'MTASK_TYPE'	//营销任务类型
];

var url = basepath+'/marketTask.json';
var comitUrl = basepath+'/marketTask.json';

var fields = [
	{name: 'TASK_ID', text: '任务编号',resutlWidth: 100},
    {name: 'TASK_NAME', text : '任务名称', searchField: true,allowBlank: false},
    {name: 'TASK_PARENT_ID', text : '上级任务ID',resutlWidth: 100,hidden: true},
    {name: 'TASK_PARENT_NAME',text:'上级任务名称',xtype:'taskchoose',hiddenName:'TASK_PARENT_ID', searchField: true,searchType: 'ALL',singleSelect: false},
    {name: 'TASK_TYPE',text:'任务类型',translateType : 'MTASK_TYPE', searchField: true, resutlWidth: 100,allowBlank: false}, 
	{name: 'TASK_STAT',text:'任务状态',translateType : 'MTASK_STAT', searchField: true, resutlWidth: 100},
	{name: 'TARGET_TYPE',text:'指标类型',translateType : 'TARGET_TYPE',hidden: true, resutlWidth: 100,allowBlank: false
		,listeners:{
			select:function(combo,record){
				targetTypeSelect(record.data.key,getCurrentView().contentPanel);
			}
		}
	},
	{name: 'DIST_TASK_TYPE',text:'执行对象类型',translateType : 'MTASK_OPER_TYPE', searchField: true, resutlWidth: 100,allowBlank: false
    	,listeners:{
			select:function(combo,record){
				distTaskTypeSelect(record.data.key,getCurrentView().contentPanel);
			}
		}
    },
	{name: 'TASK_BEGIN_DATE',text:'任务开始时间',  resutlWidth:80,dataType:'date',format:'Y-m-d',editable: false, searchField: true}, 
	{name: 'TASK_END_DATE',text:'任务结束时间',  resutlWidth:80,dataType:'date',format:'Y-m-d',editable: false, searchField: true}, 
	{name: 'TASK_DIST_DATE',text:'任务下达时间',  resutlWidth:80,dataType:'date',format:'Y-m-d',editable: false}, 
    {name: 'DIST_ORG',text:'下达机构编号', resutlWidth: 100,hidden: true},
    {name: 'DIST_ORG_NAME',text:'下达机构名称', resutlWidth: 100},
    {name: 'DIST_USER',text:'下达人编号', resutlWidth: 100,hidden: true},
    {name: 'DIST_USER_NAME',text:'下达人名称', resutlWidth: 100},
    {name: 'OPER_OBJ_ID',text:'执行对象ID', resutlWidth: 100},
    {name: 'OPER_OBJ_NAME',text:'执行对象名称', resutlWidth: 100},
    {name: 'CREATE_USER',text:'创建人ID', resutlWidth: 100,hidden: true},
    {name: 'CREATE_USER_NAME',text:'创建人名称', resutlWidth: 100},
    {name: 'CREATE_ORG_ID',text:'创建机构ID', resutlWidth: 100,hidden: true},
    {name: 'CREATE_ORG_NAME',text:'创建机构名称', resutlWidth: 100},
    {name: 'CREATE_DATE',text:'创建时间', resutlWidth: 100},
    {name: 'RECENTLY_UPDATE_ID',text:'最近更新人ID', resutlWidth: 100,hidden: true},
    {name: 'RECENTLY_UPDATE_NAME',text:'最近更新人名称', resutlWidth: 100},
    {name: 'RECENTLY_UPDATE_DATE',text:'最近更新日期', resutlWidth: 100},
    {name: 'MEMO',text:'备注',xtype: 'textarea', resutlWidth: 150}
];

var createView = false;
var editView = false;
var detailView = false;

/**
 * 自定义工具条上按钮
 * 注：批量选择未实现,目前只支持单条删除
 */
var tbar = [{
	/**
	 * 营销任务下达
	 */
	text : '下达',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}
		if(getSelectedData().data.TASK_STAT != '1' && getSelectedData().data.TASK_STAT != '2'){
			Ext.Msg.alert('提示','只能下达暂存/执行中状态的任务！');
			return false;
		}
		if(getSelectedData().data.DIST_TASK_TYPE == '2' && getSelectedData().data.TASK_STAT != '1'){
			Ext.Msg.alert('提示','不能下达执行对象类型为客户经理的任务！');
			return false;
		}
		var id=getSelectedData().data.TASK_ID;
		Ext.MessageBox.confirm('提示','确定下达吗?',function(buttonId){
			if(buttonId.toLowerCase() == "no"){
				return;
			}  
			Ext.Ajax.request({
				url: basepath + '/marketTask!transTask.json',
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                params: {
                    'taskId': id
                },
				success : function() {
                    Ext.Msg.alert('提示', '操作成功' );
					reloadCurrentData();
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败' );
					reloadCurrentData();
				}
			});
		});
	}
},{
	/**
	 * 营销任务关闭
	 */
	text : '关闭',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}
		if(getSelectedData().data.TASK_STAT != '3' 
			&& !(getSelectedData().data.TASK_STAT == '2' && getSelectedData().data.DIST_TASK_TYPE == '2')){
			Ext.Msg.alert('提示','只能关闭已下达或执行对象类型为客户经理且状态为执行中的任务！');
			return false;
		}
		var id=getSelectedData().data.TASK_ID;
		Ext.MessageBox.confirm('提示','确定关闭吗?',function(buttonId){
			if(buttonId.toLowerCase() == "no"){
				return;
			}  
			Ext.Ajax.request({
				url: basepath + '/marketTask!closeTask.json',
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                params: {
                    'taskId': id
                },
				success : function() {
                    Ext.Msg.alert('提示', '操作成功' );
					reloadCurrentData();
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败' );
					reloadCurrentData();
				}
			});
		});
	}
},{
	/**
	 * 营销任务删除
	 */
	text : '删除',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}
		if(getSelectedData().data.TASK_STAT != '1'){
			Ext.Msg.alert('提示','只能删除暂存状态的任务！');
			return false;
		}
		var id=getSelectedData().data.TASK_ID;
		Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
			if(buttonId.toLowerCase() == "no"){
				return;
			}  
			Ext.Ajax.request({
				url: basepath + '/marketTask!batchDestroy.json',
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                params: {
                    'idStr': id
                },
				success : function() {
                    Ext.Msg.alert('提示', '操作成功' );
					reloadCurrentData();
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败' );
					reloadCurrentData();
				}
			});
		});
	}
}];

//结果域扩展功能面板
var customerView = [{
	/**
	 * 自定义新增面板
	 */
	title:'新增',
	type: 'form',
	suspendWidth: 860,
	groups:[{
		columnCount : 3,
		fields : ['TASK_NAME','TASK_TYPE','DIST_TASK_TYPE','TARGET_TYPE'
			,{name: 'TARGET_NAME',text:'营销指标',xtype:'targetchoose',hiddenName:'TARGET_CODE',singleSelect: true,allowBlank: false,callback : function() {
				addTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,addTaskTargetGrid);
			}}
			,{name: 'ORG_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'orgchoose',hiddenName:'ORG_OPER_OBJ_ID',searchType:'SUBTREE',checkBox: true,callback : function() {
				addTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,addTaskTargetGrid);
			}}
			,'TASK_BEGIN_DATE','TASK_END_DATE'
			,{name: 'USER_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'userchoose',hiddenName:'USER_OPER_OBJ_ID',singleSelect: false,callback : function() {
				addTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,addTaskTargetGrid);
			}}
			,'OPER_OBJ_ID','OPER_OBJ_NAME'
			,{name: 'TEAM_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'teamchoose',hiddenName:'TEAM_OPER_OBJ_ID',singleSelect: false,callback : function() {
				addTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,addTaskTargetGrid);
			}}
			,{name: 'MARKET_CYCLE',text:'<font color=red>*</font>指标周期',xtype: 'cyclechoose',hiddenName:'MARKET_CYCLE_ID',checkBox:true,hidden:true,callback : function(){
				addTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,addTaskTargetGrid);
			}}
			,'TASK_ID'
		],
		/**
		 *营销任务面板字段初始化处理
		 */
		fn : function(TASK_NAME,TASK_TYPE,DIST_TASK_TYPE,TARGET_TYPE,TARGET_NAME,ORG_OPER_OBJ,TASK_BEGIN_DATE,TASK_END_DATE,USER_OPER_OBJ,OPER_OBJ_ID,OPER_OBJ_NAME,TEAM_OPER_OBJ,MARKET_CYCLE,TASK_ID){
			TASK_BEGIN_DATE.hidden = true;
			TASK_END_DATE.hidden = true;
			ORG_OPER_OBJ.hidden = true;
			USER_OPER_OBJ.hidden = true;
			TEAM_OPER_OBJ.hidden = true;
			OPER_OBJ_ID.hidden = true;
			OPER_OBJ_NAME.hidden = true;
			TASK_BEGIN_DATE.fieldLabel = '<font color=red>*</font>' + TASK_BEGIN_DATE.text;
			TASK_END_DATE.fieldLabel = '<font color=red>*</font>' + TASK_END_DATE.text;
			MARKET_CYCLE.hidden = true;
			TASK_ID.hidden = true;
			return [TASK_NAME,TASK_TYPE,DIST_TASK_TYPE,TARGET_TYPE,TARGET_NAME,ORG_OPER_OBJ,TASK_BEGIN_DATE,TASK_END_DATE,USER_OPER_OBJ,OPER_OBJ_ID,OPER_OBJ_NAME,TEAM_OPER_OBJ,MARKET_CYCLE,TASK_ID];
		}
	},{
		columnCount : 1,
		fields : ['MEMO'],
		/**
		 *营销任务面板字段初始化处理
		 */
		fn : function(MEMO){
			MEMO.anchor = '97%';
			return [MEMO];
		}
	},{
		columnCount : 1,
		fields : [],
		/**
		 *营销任务面板字段初始化处理
		 */
		fn : function(){
			return [addTaskTargetGrid];
		}
	}],
	formButtons:[{
		/**
		 * 新增任务-保存按钮
		 */
		text : '保存',
		fn : function(contentPanel, baseform){
			if(!baseform.isValid()){
				Ext.Msg.alert('提示','输入格式错误或存在漏输入项,请重新输入！');
				return false;
			}
			saveTaskFn(baseform,addTaskTargetGrid,'/marketTask.json');
		}
	}]
},{
	/**
	 * 自定义修改面板
	 */
	title:'修改',
	type: 'form',
	suspendWidth: 860,
	groups:[{
		columnCount : 3,
		fields : ['TASK_NAME','TASK_TYPE','DIST_TASK_TYPE','TARGET_TYPE'
			,{name: 'TARGET_NAME',text:'营销指标',xtype:'targetchoose',hiddenName:'TARGET_CODE',singleSelect: true,allowBlank: false,callback : function() {
				updateTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,updateTaskTargetGrid);
			}}
			,{name: 'ORG_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'orgchoose',hiddenName:'ORG_OPER_OBJ_ID',searchType:'SUBTREE',checkBox: true,callback : function() {
				updateTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,updateTaskTargetGrid);
			}}
			,'TASK_BEGIN_DATE','TASK_END_DATE'
			,{name: 'USER_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'userchoose',hiddenName:'USER_OPER_OBJ_ID',singleSelect: false,callback : function() {
				updateTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,updateTaskTargetGrid);
			}}
			,'OPER_OBJ_ID','OPER_OBJ_NAME'
			,{name: 'TEAM_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'teamchoose',hiddenName:'TEAM_OPER_OBJ_ID',singleSelect: false,callback : function() {
				updateTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,updateTaskTargetGrid);
			}}
			,{name: 'MARKET_CYCLE',text:'<font color=red>*</font>指标周期',xtype: 'cyclechoose',hiddenName:'MARKET_CYCLE_ID',checkBox:true,hidden:true,callback : function(){
				updateTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,updateTaskTargetGrid);
			}}
			,'TASK_ID'
		],
		/**
		 *营销任务修改面板字段初始化处理
		 */
		fn : function(TASK_NAME,TASK_TYPE,DIST_TASK_TYPE,TARGET_TYPE,TARGET_NAME,ORG_OPER_OBJ,TASK_BEGIN_DATE,TASK_END_DATE,USER_OPER_OBJ,OPER_OBJ_ID,OPER_OBJ_NAME,TEAM_OPER_OBJ,MARKET_CYCLE,TASK_ID){
			TASK_BEGIN_DATE.hidden = true;
			TASK_END_DATE.hidden = true;
			ORG_OPER_OBJ.hidden = true;
			USER_OPER_OBJ.hidden = true;
			TEAM_OPER_OBJ.hidden = true;
			OPER_OBJ_ID.hidden = true;
			OPER_OBJ_NAME.hidden = true;
			TASK_BEGIN_DATE.fieldLabel = '<font color=red>*</font>' + TASK_BEGIN_DATE.text;
			TASK_END_DATE.fieldLabel = '<font color=red>*</font>' + TASK_END_DATE.text;
			MARKET_CYCLE.hidden = true;
			TASK_ID.hidden = true;
			return [TASK_NAME,TASK_TYPE,DIST_TASK_TYPE,TARGET_TYPE,TARGET_NAME,ORG_OPER_OBJ,TASK_BEGIN_DATE,TASK_END_DATE,USER_OPER_OBJ,OPER_OBJ_ID,OPER_OBJ_NAME,TEAM_OPER_OBJ,MARKET_CYCLE,TASK_ID];
		}
	},{
		columnCount : 1,
		fields : ['MEMO'],
		/**
		 *营销任务修改面板字段初始化处理
		 */
		fn : function(MEMO){
			return [MEMO];
		}
	},{
		columnCount : 1,
		fields : [],
		/**
		 *营销任务修改面板字段初始化处理
		 */
		fn : function(){
			return [updateTaskTargetGrid];
		}
	}],
	formButtons:[{
		/**
		 * 修改任务-保存按钮
		 */
		text : '保存',
		fn : function(contentPanel, baseform){
			if(!baseform.isValid()){
				Ext.Msg.alert('提示','输入格式错误或存在漏输入项,请重新输入！');
				return false;
			}
			saveTaskFn(baseform,updateTaskTargetGrid,'/marketTask.json');
		}
	}]
},{
	/**
	 * 自定义详情面板
	 */
	title:'详情',
	type: 'form',
	suspendWidth: 860,
	groups:[{
		columnCount : 3,
		fields : ['TASK_NAME','TASK_TYPE','DIST_TASK_TYPE','TARGET_TYPE'
			,{name: 'TARGET_NAME',text:'营销指标',xtype:'targetchoose',hiddenName:'TARGET_CODE',singleSelect: true,allowBlank: false,callback : function() {
			}}
			,{name: 'ORG_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'orgchoose',hiddenName:'ORG_OPER_OBJ_ID',searchType:'SUBTREE',checkBox: true,callback : function() {
			}}
			,'TASK_BEGIN_DATE','TASK_END_DATE'
			,{name: 'USER_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'userchoose',hiddenName:'USER_OPER_OBJ_ID',singleSelect: false,callback : function() {
			}}
			,'OPER_OBJ_ID','OPER_OBJ_NAME'
			,{name: 'TEAM_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'teamchoose',hiddenName:'TEAM_OPER_OBJ_ID',singleSelect: false,callback : function() {
			}}
			,{name: 'MARKET_CYCLE',text:'<font color=red>*</font>指标周期',xtype: 'cyclechoose',hiddenName:'MARKET_CYCLE_ID',checkBox:true,hidden:true,callback : function(){
			}}
			,'TASK_ID'
		],
		/**
		 *营销任务详情面板字段初始化处理
		 */
		fn : function(TASK_NAME,TASK_TYPE,DIST_TASK_TYPE,TARGET_TYPE,TARGET_NAME,ORG_OPER_OBJ,TASK_BEGIN_DATE,TASK_END_DATE,USER_OPER_OBJ,OPER_OBJ_ID,OPER_OBJ_NAME,TEAM_OPER_OBJ,MARKET_CYCLE,TASK_ID){
			TASK_BEGIN_DATE.hidden = true;
			TASK_END_DATE.hidden = true;
			ORG_OPER_OBJ.hidden = true;
			USER_OPER_OBJ.hidden = true;
			TEAM_OPER_OBJ.hidden = true;
			OPER_OBJ_ID.hidden = true;
			OPER_OBJ_NAME.hidden = true;
			TASK_BEGIN_DATE.fieldLabel = '<font color=red>*</font>' + TASK_BEGIN_DATE.text;
			TASK_END_DATE.fieldLabel = '<font color=red>*</font>' + TASK_END_DATE.text;
			MARKET_CYCLE.hidden = true;
			TASK_ID.hidden = true;
			
			TASK_NAME.disabled = true;
			TASK_NAME.cls = "x-readOnly";
			DIST_TASK_TYPE.disabled = true;
			DIST_TASK_TYPE.cls = "x-readOnly";
			TASK_TYPE.disabled = true;
			TASK_TYPE.cls = "x-readOnly";
			TARGET_TYPE.disabled = true;
			TARGET_TYPE.cls = "x-readOnly";
			TARGET_NAME.disabled = true;
			TARGET_NAME.cls = "x-readOnly";
			TASK_BEGIN_DATE.disabled = true;
			TASK_BEGIN_DATE.cls = "x-readOnly";
			TASK_END_DATE.disabled = true;
			TASK_END_DATE.cls = "x-readOnly";
			MARKET_CYCLE.disabled = true;
			MARKET_CYCLE.cls = "x-readOnly";
			ORG_OPER_OBJ.disabled = true;
			ORG_OPER_OBJ.cls = "x-readOnly";
			USER_OPER_OBJ.disabled = true;
			USER_OPER_OBJ.cls = "x-readOnly";
			USER_OPER_OBJ.disabled = true;
			USER_OPER_OBJ.cls = "x-readOnly";
			return [TASK_NAME,TASK_TYPE,DIST_TASK_TYPE,TARGET_TYPE,TARGET_NAME,ORG_OPER_OBJ,TASK_BEGIN_DATE,TASK_END_DATE,USER_OPER_OBJ,OPER_OBJ_ID,OPER_OBJ_NAME,TEAM_OPER_OBJ,MARKET_CYCLE,TASK_ID];
		}
	},{
		columnCount : 1,
		fields : ['MEMO'],
		/**
		 *营销任务详情面板字段初始化处理
		 */
		fn : function(MEMO){
			MEMO.anchor = '97%';
			MEMO.disabled = true;
			return [MEMO];
		}
	},{
		columnCount : 1,
		fields : [],
		/**
		 *营销任务详情面板字段初始化处理
		 */
		fn : function(){
			return [detailTaskTargetGrid];
		}
	},{
		columnCount : 1,
		fields : [],
		/**
		 *营销任务详情面板字段初始化处理
		 */
		fn : function(){
			return [detailSubTaskTargetGrid];
		}
	}],
	formButtons:[{
		/**
		 * 详情任务-保存按钮
		 */
		text : '返回',
		fn : function(contentPanel, baseform){
			hideCurrentView();
		}
	}]
},{
	/**
	 * 自定义分解面板
	 */
	title:'分解',
	type: 'form',
	suspendWidth: 860,
	groups:[{
		columnCount : 3,
		fields : ['TASK_NAME','TASK_TYPE','DIST_TASK_TYPE','TARGET_TYPE'
			,{name: 'TARGET_NAME',text:'营销指标',xtype:'targetchoose',hiddenName:'TARGET_CODE',singleSelect: true,allowBlank: false,callback : function() {
			}}
			,{name: 'ORG_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'orgchoose',hiddenName:'ORG_OPER_OBJ_ID',searchType:'SUBTREE',checkBox: true,callback : function() {
				resolveSubTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,resolveSubTaskTargetGrid);
			}}
			,'TASK_BEGIN_DATE','TASK_END_DATE'
			,{name: 'USER_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'userchoose',hiddenName:'USER_OPER_OBJ_ID',singleSelect: false,callback : function() {
				resolveSubTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,resolveSubTaskTargetGrid);
			}}
			,'OPER_OBJ_ID','OPER_OBJ_NAME'
			,{name: 'TEAM_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'teamchoose',hiddenName:'TEAM_OPER_OBJ_ID',singleSelect: false,callback : function() {
				resolveSubTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,resolveSubTaskTargetGrid);
			}}
			,{name: 'MARKET_CYCLE',text:'<font color=red>*</font>指标周期',xtype: 'cyclechoose',hiddenName:'MARKET_CYCLE_ID',checkBox:true,hidden:true,callback : function(){
				resolveSubTaskTargetGrid = makeTaskTargetGrid(getCurrentView().contentPanel,resolveSubTaskTargetGrid);
			}}
			,'TASK_ID'
		],
		/**
		 *营销任务分解面板字段初始化处理
		 */
		fn : function(TASK_NAME,TASK_TYPE,DIST_TASK_TYPE,TARGET_TYPE,TARGET_NAME,ORG_OPER_OBJ,TASK_BEGIN_DATE,TASK_END_DATE,USER_OPER_OBJ,OPER_OBJ_ID,OPER_OBJ_NAME,TEAM_OPER_OBJ,MARKET_CYCLE,TASK_ID){
			TASK_BEGIN_DATE.hidden = true;
			TASK_END_DATE.hidden = true;
			ORG_OPER_OBJ.hidden = true;
			USER_OPER_OBJ.hidden = true;
			TEAM_OPER_OBJ.hidden = true;
			OPER_OBJ_ID.hidden = true;
			OPER_OBJ_NAME.hidden = true;
			TASK_BEGIN_DATE.fieldLabel = '<font color=red>*</font>' + TASK_BEGIN_DATE.text;
			TASK_END_DATE.fieldLabel = '<font color=red>*</font>' + TASK_END_DATE.text;
			TASK_ID.hidden = true;
			MARKET_CYCLE.hidden = true;
			
			TASK_NAME.disabled = true;
			TASK_NAME.cls = "x-readOnly";
			TASK_TYPE.disabled = true;
			TASK_TYPE.cls = "x-readOnly";
			TARGET_TYPE.disabled = true;
			TARGET_TYPE.cls = "x-readOnly";
			TARGET_NAME.disabled = true;
			TARGET_NAME.cls = "x-readOnly";
			TASK_BEGIN_DATE.disabled = true;
			TASK_BEGIN_DATE.cls = "x-readOnly";
			TASK_END_DATE.disabled = true;
			TASK_END_DATE.cls = "x-readOnly";
			MARKET_CYCLE.disabled = true;
			MARKET_CYCLE.cls = "x-readOnly";
			return [TASK_NAME,TASK_TYPE,DIST_TASK_TYPE,TARGET_TYPE,TARGET_NAME,ORG_OPER_OBJ,TASK_BEGIN_DATE,TASK_END_DATE,USER_OPER_OBJ,OPER_OBJ_ID,OPER_OBJ_NAME,TEAM_OPER_OBJ,MARKET_CYCLE,TASK_ID];
		}
	},{
		columnCount : 1,
		fields : ['MEMO'],
		/**
		 *营销任务分解面板字段初始化处理
		 */
		fn : function(MEMO){
			MEMO.disabled = true;
			return [MEMO];
		}
	},{
		columnCount : 1,
		fields : [],
		/**
		 *营销任务分解面板字段初始化处理
		 */
		fn : function(){
			return [resolveTaskTargetGrid];
		}
	},{
		columnCount : 1,
		fields : [],
		/**
		 *营销任务分解面板字段初始化处理
		 */
		fn : function(){
			return [resolveSubTaskTargetGrid];
		}
	}],
	formButtons:[{
		/**
		 * 分解任务-保存按钮
		 */
		text : '保存',
		fn : function(contentPanel, baseform){
			if(baseform.findField('DIST_TASK_TYPE').getValue() == '' 
				|| (baseform.findField('ORG_OPER_OBJ').getValue() == '' && baseform.findField('USER_OPER_OBJ').getValue() == '' && baseform.findField('DIST_TASK_TYPE').getValue() != '3' )
				|| (baseform.findField('DIST_TASK_TYPE').getValue() == '' && baseform.findField('DIST_TASK_TYPE').getValue() == '3' )){
				baseform.isValid();
				Ext.Msg.alert('提示','输入格式错误或存在漏输入项,请重新输入！');
				return false;
			}
			debugger;
			var fjbrwzb= contentPanel.getComponent(9);
			var fjzrwzb=contentPanel.getComponent(10);
			var fjbrwzb_store=contentPanel.getComponent(9).getStore();
			var fjzrwzb_store=contentPanel.getComponent(10).getStore();
			var sum=0;
			var fjbrwzb_store_sum=contentPanel.getComponent(9).getStore().getAt(0).data.target_value_0;
			for(var i=0;i<fjzrwzb_store.getCount();i++){
				sum+=fjzrwzb_store.getAt(i).data.target_value_0;
			};
			if(sum>fjbrwzb_store_sum){
				Ext.Msg.alert('提示','子任务指标和不允许大于本任务指标');
				return false;
			};
			saveTaskFn(baseform,resolveSubTaskTargetGrid,'/marketTask!resolveTask.json');
		}
	}]
},{
	/**
	 * 自定义调整面板
	 */
	title:'调整',
	type: 'form',
	suspendWidth: 860,
	groups:[{
		columnCount : 2,
		fields : ['TASK_ID','DIST_TASK_TYPE','OPER_OBJ_ID','OPER_OBJ_NAME','TASK_BEGIN_DATE','TASK_END_DATE','TARGET_TYPE'
			,{name: 'TARGET_NAME',text:'营销指标',xtype:'targetchoose',hiddenName:'TARGET_CODE',singleSelect: true,allowBlank: false,callback : function() {
			}}
			,{name: 'ORG_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'orgchoose',hiddenName:'ORG_OPER_OBJ_ID',searchType:'SUBTREE',checkBox: true,callback : function() {
			}}
			,{name: 'USER_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'userchoose',hiddenName:'USER_OPER_OBJ_ID',singleSelect: false,callback : function() {
			}}
			,{name: 'TEAM_OPER_OBJ',text:'<font color=red>*</font>执行对象',xtype:'teamchoose',hiddenName:'TEAM_OPER_OBJ_ID',singleSelect: false,callback : function() {
			}}
			,{name: 'MARKET_CYCLE',text:'<font color=red>*</font>指标周期',xtype: 'cyclechoose',hiddenName:'MARKET_CYCLE_ID',checkBox:true,hidden:true,callback : function(){
			}}
		],
		/**
		 *营销任务调整面板字段初始化处理
		 */
		fn : function(TASK_ID,DIST_TASK_TYPE,OPER_OBJ_ID,OPER_OBJ_NAME,TASK_BEGIN_DATE,TASK_END_DATE,TARGET_NAME,TARGET_TYPE,ORG_OPER_OBJ,USER_OPER_OBJ,TEAM_OPER_OBJ,MARKET_CYCLE){
			TASK_ID.hidden = true;
			DIST_TASK_TYPE.hidden = true;
			OPER_OBJ_ID.hidden = true;
			OPER_OBJ_NAME.hidden = true;
			TASK_BEGIN_DATE.hidden = true;
			TASK_END_DATE.hidden = true;
			TARGET_NAME.hidden = true;
			TARGET_TYPE.hidden = true;
			ORG_OPER_OBJ.hidden = true;
			USER_OPER_OBJ.hidden = true;
			TEAM_OPER_OBJ.hidden = true;
			MARKET_CYCLE.hidden = true;
			return [TASK_ID,DIST_TASK_TYPE,OPER_OBJ_ID,OPER_OBJ_NAME,TASK_BEGIN_DATE,TASK_END_DATE,TARGET_NAME,TARGET_TYPE,ORG_OPER_OBJ,USER_OPER_OBJ,TEAM_OPER_OBJ,MARKET_CYCLE];
		}
	},{
		columnCount : 1,
		fields : [],
		/**
		 *营销任务调整面板字段初始化处理
		 */
		fn : function(){
			return [adjustTaskTargetGrid];
		}
	}],
	formButtons:[{
		/**
		 * 调整任务-保存按钮
		 */
		text : '保存',
		fn : function(contentPanel, baseform){
			saveTaskFn(baseform,adjustTaskTargetGrid,'/marketTask!adjustTask.json');
		}
	}]
}];

/**
 * 结果域面板滑入前触发,系统提供listener事件方法
 * @param {} theView
 * @return {Boolean}
 */
var beforeviewshow = function(theView){
	if(theView._defaultTitle != '新增'){
		if(!getSelectedData()){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}
		if(theView._defaultTitle == '修改'){
			if(getSelectedData().data.TASK_STAT != '1'){
				Ext.Msg.alert('提示','只能修改暂存状态的任务！');
				return false;
			}
			var tempForm = theView.contentPanel.getForm();
			tempForm.reset();
			updateTaskTargetGrid = resetTaskTargetGrid(theView.contentPanel,updateTaskTargetGrid);
			distTaskTypeSelect(getSelectedData().data.DIST_TASK_TYPE,theView.contentPanel);
			targetTypeSelect(getSelectedData().data.TARGET_TYPE,theView.contentPanel);
			tempForm.loadRecord(getSelectedData());
			tempForm.findField('TARGET_NAME').marketType = getSelectedData().data.TARGET_TYPE;
			Ext.Ajax.request({
				url : basepath + '/marketTask!queryTaskTarget.json',
				method : 'GET',
				params :{
					taskId : getSelectedData().data.TASK_ID
				},
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText).json;
					setViewData(tempForm,getSelectedData(),json)
    				if(json.oper_obj_ids != '' && json.target_values != ''){
	    				updateTaskTargetGrid = makeTaskTargetGrid(theView.contentPanel,updateTaskTargetGrid);
	    				taskTargetStore.removeAll();
	    				taskTargetStore.loadData(__TaskTargetData(json.oper_obj_ids,json.oper_obj_names,json.target_ids,json.target_values,json.cycle_ids));
    				}
				},
				failure : function() {
					Ext.Msg.alert('提示', '查询任务指标失败！');
				}
			});
		}
		if(theView._defaultTitle == '详情'){
			var tempForm = theView.contentPanel.getForm();
			tempForm.reset();
			detailTaskTargetGrid = resetTaskTargetGrid(theView.contentPanel,detailTaskTargetGrid,true);
			detailSubTaskTargetGrid = resetTaskTargetGrid(theView.contentPanel,detailSubTaskTargetGrid,true);
			distTaskTypeSelect(getSelectedData().data.DIST_TASK_TYPE,theView.contentPanel);
			targetTypeSelect(getSelectedData().data.TARGET_TYPE,theView.contentPanel);
			tempForm.loadRecord(getSelectedData());
			tempForm.findField('TARGET_NAME').marketType = getSelectedData().data.TARGET_TYPE;
			Ext.Ajax.request({
				url : basepath + '/marketTask!queryTaskTarget.json',
				method : 'GET',
				params :{
					taskId : getSelectedData().data.TASK_ID
				},
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText).json;
					setViewData(tempForm,getSelectedData(),json);
    				if(json.oper_obj_ids != '' && json.curr_target_values != ''){
	    				//主任务
	    				detailTaskTargetGrid = makeTaskTargetGrid(theView.contentPanel,detailTaskTargetGrid,true);
	    				taskTargetStore.removeAll();
	    				var arr = json.oper_obj_ids.split(",");
	    				if(!getSelectedData().data.OPER_OBJ_NAME){
	    					taskTargetStore.loadData(__TaskTargetData('-1',getSelectedData().data.TASK_NAME,json.target_ids,json.curr_target_values,json.cycle_ids));
	    				}else{
	    					taskTargetStore.loadData(__TaskTargetData('-1',getSelectedData().data.OPER_OBJ_NAME,json.target_ids,json.curr_target_values,json.cycle_ids));
	    				}
    				}
					if(json.oper_obj_ids != '' && json.target_values != ''){
	    				//子任务
	    				detailSubTaskTargetGrid = makeTaskTargetGrid(theView.contentPanel,detailSubTaskTargetGrid,true);
	    				taskTargetStore.removeAll();
						taskTargetStore.loadData(__TaskTargetData(json.oper_obj_ids,json.oper_obj_names,json.target_ids,json.target_values,json.cycle_ids));
					}else{
						//移除原有表格对象
						var temp = detailSubTaskTargetGrid.ownerCt;
						if(temp){
							try{
								temp.remove(detailSubTaskTargetGrid);
								temp.doLayout();
							}catch(e){
								//详情面板第一次进入时,会报找不到对应的length属性，可能原因是由于同时两次调用了此方法，
								//创造contentPanel里面的items顺序已经变更,却没有及时取最新的contentPanel来进行remove
							}
						}
					}
				},
				failure : function() {
					Ext.Msg.alert('提示', '查询任务指标失败！');
				}
			});
		}
		if(theView._defaultTitle == '分解'){
			if(getSelectedData().data.TASK_STAT != '2'){
				Ext.Msg.alert('提示','只能分解执行中的任务！');
				return false;
			}
			if(getSelectedData().data.DIST_TASK_TYPE == '2'){
				Ext.Msg.alert('提示','只能分解执行对象类型为机构或营销团队的任务！');
				return false;
			}
			
			var tempForm = theView.contentPanel.getForm();
			tempForm.reset();
			resolveTaskTargetGrid = resetTaskTargetGrid(theView.contentPanel,resolveTaskTargetGrid,true);
			resolveSubTaskTargetGrid = resetTaskTargetGrid(theView.contentPanel,resolveSubTaskTargetGrid);
			distTaskTypeSelect(getSelectedData().data.DIST_TASK_TYPE,theView.contentPanel);
			targetTypeSelect(getSelectedData().data.TARGET_TYPE,theView.contentPanel);
			tempForm.loadRecord(getSelectedData());
			tempForm.findField('TARGET_NAME').marketType = getSelectedData().data.TARGET_TYPE;
			Ext.Ajax.request({
				url : basepath + '/marketTask!queryTaskTarget.json',
				method : 'GET',
				params :{
					taskId : getSelectedData().data.TASK_ID
				},
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText).json;
					setViewData(tempForm,getSelectedData(),json);
					if(json.oper_obj_ids != '' && json.target_values != ''){
	    				//子任务
	    				resolveSubTaskTargetGrid = makeTaskTargetGrid(theView.contentPanel,resolveSubTaskTargetGrid);
	    				taskTargetStore.removeAll();
						taskTargetStore.loadData(__TaskTargetData(json.oper_obj_ids,json.oper_obj_names,json.target_ids,json.target_values,json.cycle_ids));
					}
    				if(json.oper_obj_ids != '' && json.curr_target_values != ''){
	    				//主任务
	    				resolveTaskTargetGrid = makeTaskTargetGrid(theView.contentPanel,resolveTaskTargetGrid,true);
	    				taskTargetStore.removeAll();
	    				var arr = json.oper_obj_ids.split(",");
	    				if(!getSelectedData().data.OPER_OBJ_NAME){
	    					taskTargetStore.loadData(__TaskTargetData('-1',getSelectedData().data.TASK_NAME,json.target_ids,json.curr_target_values,json.cycle_ids));
	    				}else{
	    					taskTargetStore.loadData(__TaskTargetData('-1',getSelectedData().data.OPER_OBJ_NAME,json.target_ids,json.curr_target_values,json.cycle_ids));
	    				}
    				}
					if(getSelectedData().data.DIST_TASK_TYPE == '3'){
						tempForm.findField('DIST_TASK_TYPE').setDisabled(true);//如果执行对象是团队,则禁止选择执行对象类型,且选择执行对象更改为客户经理的执行对象
						tempForm.findField('TEAM_OPER_OBJ').setVisible(false);
						tempForm.findField('USER_OPER_OBJ').setVisible(true);
					}else{
						tempForm.findField('DIST_TASK_TYPE').setDisabled(false);//如果执行对象是团队,则禁止选择执行对象
					}
    				//将执行对象重置为空
    				if(getSelectedData().data.DIST_TASK_TYPE == '1'){
	    				tempForm.findField('ORG_OPER_OBJ').setValue('');
    				}else if(getSelectedData().data.DIST_TASK_TYPE == '2'){
    					tempForm.findField('USER_OPER_OBJ').setValue('');
    				}else{
    					tempForm.findField('DIST_TASK_TYPE').setValue('2');
    					tempForm.findField('TEAM_OPER_OBJ').setValue('');
    				}
				},
				failure : function() {
					Ext.Msg.alert('提示', '查询任务指标失败！');
				}
			});
		}
		if(theView._defaultTitle == '调整'){
			if(getSelectedData().data.TASK_STAT != '3'){
				Ext.Msg.alert('提示','只能调整已下达状态的任务！');
				return false;
			}
			var tempForm = theView.contentPanel.getForm();
			tempForm.reset();
			adjustTaskTargetGrid = resetTaskTargetGrid(theView.contentPanel,adjustTaskTargetGrid);
			distTaskTypeSelect(getSelectedData().data.DIST_TASK_TYPE,theView.contentPanel);
			targetTypeSelect(getSelectedData().data.TARGET_TYPE,theView.contentPanel);
			tempForm.findField('ORG_OPER_OBJ').setVisible(false);
			tempForm.findField('USER_OPER_OBJ').setVisible(false);
			tempForm.findField('TEAM_OPER_OBJ').setVisible(false);
			tempForm.findField('TASK_BEGIN_DATE').setVisible(false);
			tempForm.findField('TASK_END_DATE').setVisible(false);
			tempForm.findField('MARKET_CYCLE').setVisible(false);
			tempForm.loadRecord(getSelectedData());
			tempForm.findField('TARGET_NAME').marketType = getSelectedData().data.TARGET_TYPE;
			Ext.Ajax.request({
				url : basepath + '/marketTask!queryTaskTarget.json',
				method : 'GET',
				params :{
					taskId : getSelectedData().data.TASK_ID
				},
				waitMsg : '正在加载数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function(response) {
					var json = Ext.util.JSON.decode(response.responseText).json;
					setViewData(tempForm,getSelectedData(),json);
    				if(json.oper_obj_ids != '' && json.target_values != ''){
	    				adjustTaskTargetGrid = makeTaskTargetGrid(theView.contentPanel,adjustTaskTargetGrid);
	    				taskTargetStore.removeAll();
	    				//反显数据
	    				taskTargetStore.loadData(__TaskTargetData(json.oper_obj_ids,json.oper_obj_names,json.target_ids,json.target_values,json.cycle_ids));
    				}
				},
				failure : function() {
					Ext.Msg.alert('提示', '查询任务指标失败！');
				}
			});
		}
	}
	if(theView._defaultTitle == '新增'){
		theView.contentPanel.getForm().reset();
		addTaskTargetGrid = resetTaskTargetGrid(theView.contentPanel,addTaskTargetGrid,true);
	}
};

//指标周期数量
var targetCycleCount = 0;
//执行对象数量
var targetAddCount = 0;
//任务指标列模型
var taskTargetCm = new Ext.grid.ColumnModel([
	new Ext.grid.RowNumberer({
		header : 'No.',
		width : 35
	})
]);
// 列表字段映射
var taskTargetRecord = Ext.data.Record.create([]);
// 读取jsonReader
var taskTargetReader = new Ext.data.JsonReader();
// 数据查询store
var taskTargetStore = new Ext.data.Store();
//新增任务指标表格
var addTaskTargetGrid = new Ext.grid.EditorGridPanel({
	title : '任务指标',
	store : new Ext.data.Store(),
	frame : true,
	height : 200,
	cm : new Ext.grid.ColumnModel([
       	new Ext.grid.RowNumberer({
    		header : 'No.',
    		width : 35
    	})
    ]),
	region : 'center',
	clicksToEdit : 1,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//修改任务指标表格
var updateTaskTargetGrid = new Ext.grid.EditorGridPanel({
	title : '任务指标',
	store : new Ext.data.Store(),
	frame : true,
	height : 200,
	cm : new Ext.grid.ColumnModel([
       	new Ext.grid.RowNumberer({
    		header : 'No.',
    		width : 35
    	})
    ]),
	region : 'center',
	clicksToEdit : 1,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//详情任务指标表格
var detailTaskTargetGrid = new Ext.grid.GridPanel({
	title : '本任务指标',
	store : new Ext.data.Store(),
	frame : true,
	height : 135,
	cm : new Ext.grid.ColumnModel([
       	new Ext.grid.RowNumberer({
    		header : 'No.',
    		width : 35
    	})
    ]),
	region : 'center',
	clicksToEdit : 1,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//详情任务指标表格
var detailSubTaskTargetGrid = new Ext.grid.GridPanel({
	title : '子任务指标',
	store : new Ext.data.Store(),
	frame : true,
	height : 200,
	cm : new Ext.grid.ColumnModel([
       	new Ext.grid.RowNumberer({
    		header : 'No.',
    		width : 35
    	})
    ]),
	region : 'center',
	clicksToEdit : 1,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//分解任务指标表格
var resolveTaskTargetGrid = new Ext.grid.GridPanel({
	title : '本任务指标',
	store : new Ext.data.Store(),
	frame : true,
	height : 135,
	cm : new Ext.grid.ColumnModel([
       	new Ext.grid.RowNumberer({
    		header : 'No.',
    		width : 35
    	})
    ]),
	region : 'center',
	clicksToEdit : 1,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//分解任务指标表格
var resolveSubTaskTargetGrid = new Ext.grid.EditorGridPanel({
	title : '分解子任务指标',
	store : new Ext.data.Store(),
	frame : true,
	height : 200,
	cm : new Ext.grid.ColumnModel([
       	new Ext.grid.RowNumberer({
    		header : 'No.',
    		width : 35
    	})
    ]),
	region : 'center',
	clicksToEdit : 1,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//调整任务指标表格
var adjustTaskTargetGrid = new Ext.grid.EditorGridPanel({
	title : '任务指标',
	store : new Ext.data.Store(),
	frame : true,
	height : 340,
	cm : new Ext.grid.ColumnModel([
       	new Ext.grid.RowNumberer({
    		header : 'No.',
    		width : 35
    	})
    ]),
	region : 'center',
	clicksToEdit : 1,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

/**
 * 指标类型选择事件
 * @param {} record 选择的下拉框数据对象
 */
var targetTypeSelect = function(selectValue,contentPanel){
	var tempForm = contentPanel.getForm();
	/**
	 * 设置可见及是否允许为空
	 */
	var tempFn = function(a){
		tempForm.findField('TASK_BEGIN_DATE').setVisible(a);
		tempForm.findField('TASK_END_DATE').setVisible(a);
		tempForm.findField('MARKET_CYCLE').setVisible(!a);
		
		tempForm.findField('TASK_BEGIN_DATE').allowBlank = !a;
		tempForm.findField('TASK_END_DATE').allowBlank = !a;
		tempForm.findField('MARKET_CYCLE').allowBlank = a;
	};
	//重置指标
	tempForm.findField('TARGET_NAME').setValue('');
	tempForm.findField('TARGET_CODE').setValue('');
	tempForm.findField('TASK_BEGIN_DATE').setValue('');
	tempForm.findField('TASK_END_DATE').setValue('');
	tempForm.findField('MARKET_CYCLE').setValue('');
	tempForm.findField('MARKET_CYCLE_ID').setValue('');
	if(selectValue == '01'){
		tempForm.findField('TARGET_NAME').marketType = '01';
		tempFn(false);
	}else{
		tempForm.findField('TARGET_NAME').marketType = '02';
		tempFn(true);
	}
}

/**
 * 执行对象类型选择事件
 * @param {} record 选择的下拉框数据对象
 * @param {} contentPanel 当此参数值未定义时，才去取getCurrentView().contentPanel
 */
var distTaskTypeSelect = function(selectValue,contentPanel){
	if(!contentPanel){
		return false;//当作为查询条件时，不必往下执行
	}
	var tempForm = contentPanel.getForm();
	/**
	 * 设置可见及是否允许为空
	 */
	var tempFn = function(a,b,c){
		tempForm.findField('ORG_OPER_OBJ').setVisible(a);
		tempForm.findField('USER_OPER_OBJ').setVisible(b);
		tempForm.findField('TEAM_OPER_OBJ').setVisible(c);
		
		tempForm.findField('ORG_OPER_OBJ').allowBlank = !a;
		tempForm.findField('USER_OPER_OBJ').allowBlank = !b;
		tempForm.findField('TEAM_OPER_OBJ').allowBlank = !c;
	};
	//重置执行对象
	tempForm.findField('ORG_OPER_OBJ').setValue('');
	tempForm.findField('USER_OPER_OBJ').setValue('');
	tempForm.findField('TEAM_OPER_OBJ').setValue('');
	tempForm.findField('ORG_OPER_OBJ_ID').setValue('');
	tempForm.findField('USER_OPER_OBJ_ID').setValue('');
	tempForm.findField('TEAM_OPER_OBJ_ID').setValue('');
	if(selectValue == '1'){
		tempFn(true,false,false);
	}else if(selectValue == '2'){
		tempFn(false,true,false);
	}else{
		tempFn(false,false,true);
	}
};

/**
 * 保存数据，新增、修改、调整、分解
 * @param {} baseform
 * @param {} taskTargetGrid
 * @param {} saveUrl
 */
var saveTaskFn = function(baseform,taskTargetGrid,saveUrl){
	taskTargetGrid.stopEditing();//停止编辑
	var targetDataValue = "";
	for (var i = 0; i < taskTargetStore.getCount(); i++) {
		var temp = taskTargetStore.getAt(i);
		for (var j = 0; j < targetAddCount; j++) {
			if(targetCycleCount > 0){
				for(var k=0;k<targetCycleCount;k++){
					var tempValue = temp.data['target_value_'+j+''+k];	//获取对应列的指标值
					if (tempValue == null || tempValue === "") {	//此处写三个===是为了避免0与""相等
						tempValue = "-1";
						Ext.Msg.alert('提示', '任务指标值存在漏输入项,请输入！');
						return false;
					}
					targetDataValue += tempValue;
					targetDataValue += ",";
				}
			}else{
				var tempValue = temp.data['target_value_'+j];	//获取对应列的指标值
				if (tempValue == null || tempValue === "") {	//此处写三个===是为了避免0与""相等
					tempValue = "-1";
					Ext.Msg.alert('提示', '任务指标值存在漏输入项,请输入！');
					return false;
				}
				targetDataValue += tempValue;
				targetDataValue += ",";
			}
		}
		targetDataValue = targetDataValue.substring(0,targetDataValue.length -1);
		if (i != taskTargetStore.getCount() - 1) {
			targetDataValue += ";";
		}
	}
	var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
	commintData.targetDataValue = targetDataValue;
	commintData.targetIds = baseform.findField('TARGET_CODE').getValue();
	commintData.targetNames = baseform.findField('TARGET_NAME').getValue();
	commintData.cycleIds = baseform.findField('MARKET_CYCLE_ID').getValue();
	commintData.cycleNames = baseform.findField('MARKET_CYCLE').getValue();
	Ext.Msg.wait('正在保存，请稍等...', '提示');
	Ext.Ajax.request({
		url : basepath + saveUrl,
		method : 'POST',
		params :commintData,
		waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
		success : function() {
			Ext.Msg.alert('提示', '操作成功！');
            reloadCurrentData();
            hideCurrentView();
		},
		failure : function() {
			Ext.Msg.alert('提示', '操作失败');
			hideCurrentView();
		}
	});
};

/**
 * 返显特殊项数据
 * @param {} data 选择的数据行数据
 * @param {} json {json:{target_ids:'', target_names:'',oper_obj_ids :'',oper_obj_names:'',cycle_ids:'',cycle_names:''}
 */
var setViewData = function(tempForm,selectRecord,json){
	tempForm.loadRecord(selectRecord); //故意载入两次,解决未收缩面板时,选择其它数据行,js错误
	tempForm.findField('TARGET_CODE').setValue(json.target_ids);
	tempForm.findField('TARGET_NAME').setValue(json.target_names);
	tempForm.findField('MARKET_CYCLE_ID').setValue(json.cycle_ids);
	tempForm.findField('MARKET_CYCLE').setValue(json.cycle_names);
	
	if(selectRecord.data.DIST_TASK_TYPE == '1'){
		tempForm.findField('ORG_OPER_OBJ').setValue(json.oper_obj_names);
		tempForm.findField('ORG_OPER_OBJ_ID').setValue(json.oper_obj_ids);
	}else if(selectRecord.data.DIST_TASK_TYPE == '2'){
		tempForm.findField('USER_OPER_OBJ').setValue(json.oper_obj_names);
		tempForm.findField('USER_OPER_OBJ_ID').setValue(json.oper_obj_ids);
	}else{
		tempForm.findField('TEAM_OPER_OBJ').setValue(json.oper_obj_names);
		tempForm.findField('TEAM_OPER_OBJ_ID').setValue(json.oper_obj_ids);
	}
};

/**
 * 重置指标表格
 * @param {} contentPanel 表格父容器
 * @param {} taskTargetGrid 要重置的表格
 * @param {} editable 是否禁止编辑  false可编辑,true不可编辑
 * @return {OBJECT} 返回新NEW的表格,必须返回即使未创建表格
 */
var resetTaskTargetGrid = function(contentPanel,taskTargetGrid,editable){
	// 重新设置列表（包括表头、字段映射等）
	taskTargetStore = new Ext.data.Store();
	taskTargetCm = new Ext.grid.ColumnModel([
		{header : '',width : 240}	//此处设置此行的理由是为了让“没有查询到任何记录”这句话横向显示完全
	]);
	var _title = taskTargetGrid.title;
	var _height = taskTargetGrid.height;
	//移除原有表格对象
	var temp = taskTargetGrid.ownerCt;
	if(temp){
		try{
			temp.remove(taskTargetGrid);
			temp.doLayout();
		}catch(e){
			//详情面板第一次进入时,会报找不到对应的length属性，可能原因是由于同时两次调用了此方法，
			//创造contentPanel里面的items顺序已经变更,却没有及时取最新的contentPanel来进行remove
		}
	}
	if(!editable){
		taskTargetGrid = new Ext.grid.EditorGridPanel({
			title : _title,
			height : _height,
			store : taskTargetStore,
			cm : taskTargetCm,
			region : 'center',
			frame : true,
			clicksToEdit : 1,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
	}else{
		taskTargetGrid = new Ext.grid.GridPanel({
			title : _title,
			height : _height,
			store : taskTargetStore,
			cm : taskTargetCm,
			region : 'center',
			frame : true,
			clicksToEdit : 1,
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
		});
	}
	contentPanel.add(taskTargetGrid);
	contentPanel.doLayout();
	return taskTargetGrid;
};

/**
 * 
 * 生成任务指标表头列
 * @param {} contentPanel 表格父容器
 * @param {} taskTargetGrid 指标表格
 * @param {} editable 是否禁止编辑  false可编辑,true不可编辑
 * @return {OBJECT} 返回新NEW的表格,必须返回即使未创建表格
 */
var makeTaskTargetGrid = function(contentPanel,taskTargetGrid,editable) {
	var tempForm = contentPanel.getForm();// 本页面（新增页面）新增表单Form对象
	var targetIds = null;// 指标ID集合
	var targetNames = null;// 指标名称集合
	var operIds = null;// 执行对象ID集合
	var operNames = null;// 执行对象名称集合
	var cycleIds = null;  //周期ID集合
	var cycleNames = null;//周期名称集合
	var distTaskType = tempForm.findField('DIST_TASK_TYPE').getValue();// 执行对象类型
	var targetType = tempForm.findField('TARGET_TYPE').getValue();	//指标类型
	
	targetIds = tempForm.findField('TARGET_CODE').getValue();
	targetNames = tempForm.findField('TARGET_NAME').getValue();
	
	cycleIds = tempForm.findField('MARKET_CYCLE_ID').getValue();
	cycleNames = tempForm.findField('MARKET_CYCLE').getValue();
	
	if (distTaskType == "1") {	//机构
		operIds = tempForm.findField('ORG_OPER_OBJ_ID').getValue();
		operNames = tempForm.findField('ORG_OPER_OBJ').getValue();
	} else if (distTaskType == "2") {	//客户经理
		operIds = tempForm.findField('USER_OPER_OBJ_ID').getValue();
		operNames = tempForm.findField('USER_OPER_OBJ').getValue();
	} else {	//营销团队
		operIds = tempForm.findField('TEAM_OPER_OBJ_ID').getValue();
		operNames = tempForm.findField('TEAM_OPER_OBJ').getValue();
	}
	// 如果执行对象或者任务指标有为空的，不生成任务指标表格
	if (operIds == null || operIds == "" || targetIds == null || targetIds == ""
		|| (targetType == '01' && (cycleIds == null || cycleIds == "") )) {
		taskTargetGrid = resetTaskTargetGrid(contentPanel,taskTargetGrid,true);
		return taskTargetGrid;
	}
	// 给隐藏的“执行对象ID”、“执行对象名称”赋值
	tempForm.findField("OPER_OBJ_ID").setValue(operIds);
	tempForm.findField("OPER_OBJ_NAME").setValue(operNames);
	
	var _groupRows = [
		{header: '', colspan: 1, align: 'center'},
		{header: '', colspan: 1, align: 'center'},
		{header: '', colspan: 1, align: 'center'}
	];
	var propColumn = [new Ext.grid.RowNumberer({
		header : 'No.',
		width : 35
	}),
		{header : '执行对象ID',dataIndex : 'obj_id',sortable : true,hidden : true,width : 200},
		{header : '执行对象',dataIndex : 'oper_obj_name',sortable : true,width : 200}
	];
	var propFieldName = [
		{name : 'obj_id'},
		{name : 'oper_obj_name'}
	];
	var targetNameArr = targetNames.split(",");
	targetAddCount = targetNameArr.length;
	var targetCycleArr = [];
	if(targetType == '01'){
		targetCycleArr = cycleNames.split(",");
		targetCycleCount = targetCycleArr.length;
	}else{
		targetCycleCount = 0;
	}
	for (var i = 0; i < targetAddCount; i++) {
		if(targetType == '01'){
			_groupRows.push({header: targetNameArr[i], colspan: targetCycleCount, align: 'center'});
			for(var j=0;j<targetCycleCount;j++){
				var tempTargetObj = {header : targetCycleArr[j],dataIndex : 'target_value_' + i+''+j,sortable : true,width : 120,editor : new Ext.form.NumberField()};
				var tempTargetField = {name : 'target_value_'  + i+''+j};
				propColumn.push(tempTargetObj);
				propFieldName.push(tempTargetField);
			}
		}else{
			var tempTargetObj = {header : targetNameArr[i],dataIndex : 'target_value_' + i,sortable : true,width : 120,editor : new Ext.form.NumberField()};
			var tempTargetField = {name : 'target_value_' + i};
			propColumn.push(tempTargetObj);
			propFieldName.push(tempTargetField);
		}
	}
	taskTargetRecord = Ext.data.Record.create(propFieldName);
	taskTargetReader = new Ext.data.JsonReader({
			root : 'rows',
			totalProperty : 'num'
		}, taskTargetRecord);
	taskTargetStore = new Ext.data.Store({
		restful : true,
		reader : taskTargetReader
	});
	var _title = taskTargetGrid.title;
	var _height = taskTargetGrid.height;
	//移除原有表格对象
	var temp = taskTargetGrid.ownerCt;
	if(temp){
		try{
			temp.remove(taskTargetGrid);
			temp.doLayout();
		}catch(e){
			//详情面板第一次进入时,会报找不到对应的length属性，可能原因是由于同时两次调用了此方法，
			//创造contentPanel里面的items顺序已经变更,却没有及时取最新的contentPanel来进行remove
		}
	}
	//周期性指标
	if(targetType == '01'){
		if(!editable){
			taskTargetGrid = new Ext.grid.EditorGridPanel({
				title : _title,
				height : _height,
				store : taskTargetStore,
				cm : new Ext.grid.ColumnModel(propColumn),
				plugins: new Ext.ux.grid.ColumnHeaderGroup({
				    rows: [_groupRows]
				}),
				region : 'center',
				frame : true,
				clicksToEdit : 1,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
		}else{
			taskTargetGrid = new Ext.grid.GridPanel({
				title : _title,
				height : _height,
				store : taskTargetStore,
				cm : new Ext.grid.ColumnModel(propColumn),
				plugins: new Ext.ux.grid.ColumnHeaderGroup({
				    rows: [_groupRows]
				}),
				region : 'center',
				frame : true,
				clicksToEdit : 1,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
		}
	}else{
		if(!editable){
			taskTargetGrid = new Ext.grid.EditorGridPanel({
				title : _title,
				height : _height,
				store : taskTargetStore,
				cm : new Ext.grid.ColumnModel(propColumn),
				region : 'center',
				frame : true,
				clicksToEdit : 1,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
		}else{
			taskTargetGrid = new Ext.grid.GridPanel({
				title : _title,
				height : _height,
				store : taskTargetStore,
				cm : new Ext.grid.ColumnModel(propColumn),
				region : 'center',
				frame : true,
				clicksToEdit : 1,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
		}
	}
	contentPanel.add(taskTargetGrid);
	contentPanel.doLayout();
	taskTargetStore.loadData(makeTaskTargetData(operIds, operNames,targetIds,cycleIds));
	return taskTargetGrid;
};

/**
 * 构造任务指标数据对象
 * @param {} operIds 执行对象ID
 * @param {} operNames 执行对象名称
 * @param {} targetIds 指标ID
 * @param {} cycleIds 指标周期ID
 * @return {}
 */
var makeTaskTargetData = function(operIds, operNames, targetIds, cycleIds) {
	var tempRowData = [];
	var tempColData = null;
	var operIdArr = operIds.split(",");
	var operNameArr = operNames.split(",");
	var targetIdArr = targetIds.split(",");
	var cycleIdArr = [];
	if(cycleIds != null && cycleIds != ""){
		cycleIdArr = cycleIds.split(",");
	}
	for (var i = 0; i < operIdArr.length; i++) {
		tempColData = {
			"obj_id" : operIdArr[i],
			"oper_obj_name" : operNameArr[i]
		};
		for (var j = 0; j < targetIdArr.length; j++) {
			if(cycleIdArr.length > 0){
				for(var k=0;k< cycleIdArr.length;k++){
					tempColData["target_value_" + j+''+k] = "";
				}
			}else{
				tempColData["target_value_" + j] = "";
			}
		}
		tempRowData.push(tempColData);
	}
	var targetData = {
		rows : tempRowData,
		num : tempRowData.length
	};
	return targetData;
};

/**
 * 构造任务指标数据对象-修改及详情时反显
 * @param {} operIds 执行对象ID
 * @param {} operNames 执行对象名称
 * @param {} targetIds 指标ID
 * @param {} cycleIds 指标周期ID
 * @return {}
 */
var __TaskTargetData = function(operIds, operNames, targetIds, targetValues, cycleIds) {
	var tempRowData = [];
	var tempColData = null;
	var operIdArr = operIds.split(",");
	var operNameArr = operNames.split(",");
	var targetIdArr = targetIds.split(",");
	var targetValueRowArr = targetValues.split(";");
	var cycleIdArr = [];
	if(cycleIds != null && cycleIds !== ""){
		cycleIdArr = cycleIds.split(",");
	}
	for (var i = 0; i < operIdArr.length; i++) {
		tempColData = {
			"obj_id" : operIdArr[i],
			"oper_obj_name" : operNameArr[i]
		};
		var targetValueArr = targetValueRowArr[i].split(",");
		for (var j = 0; j < targetIdArr.length; j++) {
			if(cycleIdArr.length > 0){
				for(var k=0;k< cycleIdArr.length;k++){
					tempColData["target_value_" + j+''+k] = targetValueArr[(j * cycleIdArr.length) + k];
				}
			}else{
				tempColData["target_value_" + j] = targetValueArr[j];
			}
		}
		tempRowData.push(tempColData);
	}
	var targetData = {
		rows : tempRowData,
		num : tempRowData.length
	};
	return targetData;
};

