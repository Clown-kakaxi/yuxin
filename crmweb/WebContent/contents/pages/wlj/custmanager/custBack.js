/**
 * @description 客户退回处理页面
 * @author luyy
 * @since 2014-07-10
 */
	
var url = basepath+'/custBack.json?type=1';

var createView = false;
var editView = false;
var detailView = false;

var lookupTypes = ['XD000080','MAINTAIN_TYPE','XD000081'];

var fields = [
	{name:'ID',hidden:true},
    {name:'CUST_ID',text:'客户编号',dataType:'string',searchField:true},
    {name:'CUST_NAME',text:'客户名称',dataType:'string',searchField:true},
    {name:'CUST_TYPE',text:'客户类别',translateType:'XD000080'},
    {name:'CUST_STAT',text:'客户状态',translateType:'XD000081',searchField:true},
    {name:'MGR_ID',hidden:true},
    {name:'MGR_NAME',text:'客户经理',dataType:'string'},
    {name:'INSTITUTION',hidden:true},
    {name:'INSTITUTION_NAME',text:'机构',dataType:'string'},
    {name:'MAIN_TYPE',text:'主协办类型',translateType:'MAINTAIN_TYPE'}
];


var customerView = [{
	title:'客户退回',
	type:'form',
	groups : [{
		columnCount :2,
		fields : [
			{name:'CUST_ID',text:'客户编号',allowBlank:false},
            {name:'CUST_NAME',text:'客户名称',allowBlank:false},
            {name:'ORG_ID',text:'',hidden:true},
		    {name:'RECORD_ID',text:'',hidden:true},
            {name:'MGR_ID',text:'',hidden:true},
            {name:'ORG_NAME',text:'',allowBlank:true},
            {name:'MGR_NAME',text:'',allowBlank:true},
		    'CUST_TYPE','MAIN_TYPE'
		],
	   fn : function(CUST_ID,CUST_NAME,ORG_ID,MGR_ID,ORG_NAME,MGR_NAME,RECORD_ID,CUST_TYPE,MAIN_TYPE){
		  CUST_TYPE.hidden = true;
		  MAIN_TYPE.hidden = true;
		  //wzy,20140926,add：客户号、客户名称设置成只读，不能编辑
		  CUST_ID.readOnly = true;
		  CUST_ID.cls = 'x-readOnly';
		  CUST_NAME.readOnly = true;
		  CUST_NAME.cls = 'x-readOnly';
		  return [CUST_ID,CUST_NAME,CUST_TYPE,MGR_NAME,ORG_NAME,MAIN_TYPE,RECORD_ID,ORG_ID,MGR_ID];
		}
	},{
		columnCount :1,
		fields : [
			{name:'BACK_REASON',text:'退回原因',xtype:'textarea'}
		],
		fn : function(BACK_REASON){
			return [BACK_REASON];
		}
	}],
	formButtons:[{
		text:'确定',
		fn : function(formPanel,basicForm){
			if (!formPanel.getForm().isValid()) {
	        	Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	            return false;
	        };
			var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
			Ext.Ajax.request({
				url : basepath + '/custBack!save.json',
				method : 'POST',
				params : commintData,
				success : function(response) {
//					Ext.Msg.alert('提示','操作成功！');
//					reloadCurrentData();
				 	var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
				}
			});
		}
	}]
},{
	title:'我的退回记录',
	type:'grid',
	pageable:true,
	url : basepath + '/custBack.json?type=2',
	fields : {
		fields : [
			{name : 'CUST_ID',text : '客户编号'},
            {name : 'CUST_NAME',text : '客户名称',width:250},
            {name:'CUST_TYPE_ORA',text:'客户类别'},
            {name:'MAIN_TYPE_ORA',text:'主协办类型'},
            {name : 'BACK_DATE',text : '申请日期'},
            {name : 'BACK_STAT_ORA',text : '退回状态'},
            {name : 'BACK_REASON',text : '退回原因',width:250}
		],
		fn : function(CUST_ID,CUST_NAME,CUST_TYPE,MAIN_TYPE,BACK_DATE,BACK_STAT,BACK_REASON){
			return [CUST_ID,CUST_NAME,CUST_TYPE,MAIN_TYPE,BACK_DATE,BACK_STAT,BACK_REASON];
		}
	}
},{//wzy,20140925,add:增加退回记录
	title:'客户退回记录',
	type:'grid',
	pageable:true,
	url : basepath + '/custBack.json?type=3',
	fields : {
		fields : [
			{name : 'MGR_ID',text: '退回客户经理编号',width:120},
            {name : 'MGR_NAME',text:'退回客户经理姓名',width:120},
            {name : 'MAIN_TYPE_ORA',text:'主协办类型'},
            {name : 'BACK_DATE',text : '申请日期'},
            {name : 'BACK_STAT_ORA',text : '退回状态'},
            {name : 'BACK_REASON',text : '退回原因',width:250}
		],
		fn : function(MGR_ID,MGR_NAME,MAIN_TYPE,BACK_DATE,BACK_STAT,BACK_REASON){
			return [MGR_ID,MGR_NAME,MAIN_TYPE,BACK_DATE,BACK_STAT,BACK_REASON];
		}
	}
}];

var beforeviewshow = function(view){
	if(view._defaultTitle == '客户退回'){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
	}else if(view._defaultTitle == '我的退回记录'){
		view.setParameters (); 
	}else if(view._defaultTitle == '客户退回记录'){
		var selData = getSelectedData();
		if(!selData){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		var cust_id = selData.data.CUST_ID;
		view.setParameters ({
			cust_id : cust_id
		}); 
	}
};

var viewshow = function(view){
	if(view._defaultTitle == '客户退回'){
		 getCurrentView().setValues({
			 CUST_ID:getSelectedData().data.CUST_ID,
			 CUST_NAME:getSelectedData().data.CUST_NAME,
			 ORG_ID:getSelectedData().data.INSTITUTION,
			 ORG_NAME:getSelectedData().data.INSTITUTION_NAME,
			 MGR_ID:getSelectedData().data.MGR_ID,
			 MGR_NAME:getSelectedData().data.MGR_NAME,
			 RECORD_ID:getSelectedData().data.ID,
			 CUST_TYPE:getSelectedData().data.CUST_TYPE,
			 MAIN_TYPE:getSelectedData().data.MAIN_TYPE
		});
	}
};