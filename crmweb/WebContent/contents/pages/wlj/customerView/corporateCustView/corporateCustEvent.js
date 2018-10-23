/**
*@description 360客户视图 对公事件信息
*@author:xiebz
*@since:2014-07-19
*@checkedby:
*/
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
        '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',//用户放大镜
        '/contents/pages/common/LovCombo.js',
        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
		]);
var createView = !JsContext.checkGrant('corCustEvent_create');
var editView = !JsContext.checkGrant('corCustEvent_modify');
var detailView = !JsContext.checkGrant('corCustEvent_detail');
var lookupTypes = ['EVENT_TYP',//事件类型
                   'IF_FLAG',//是否标志
                   'CUSTOMER_SOURCE_TYPE'//客户来源
                   ];
var custId =_custId;
//flag = 0 对私 flag = 1 对公
var url = basepath+'/acrmFCiEventInfo.json?custId='+custId+'&flag=1';

var fields = [
  		    {name: 'EVENT_ID',hidden : true},
  		    {name: 'EVENT_NAME', text : '事件名称', searchField: true,allowBlank:false}, 
  		    {name: 'EVENT_TYP', text : '事件类型',searchField: true,translateType:'EVENT_TYP',hidden : true}, 
  		    {name: 'EVENT_DESC', text : '事件内容',resutlWidth:350,xtype:'textarea',allowBlank:false},                                   
  		    {name: 'WARN_FLG',text:'是否提醒',translateType:'IF_FLAG',resutlWidth:60,
  		    	listeners:{
  		    		select:function(combo,record){
  		    			var v = this.getValue();
  		    			if(v=='1'){//1是0否
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_TIME').setVisible(true);
  		    			}else if(v=='0'){
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_TIME').setVisible(false);
  		    			}
  					}
	        	}},  
	        {name: 'REMIND_PPL', text : '提醒对象',hidden:true},
	        {name: 'REMIND_PPL_CM', text : '提醒客户经理',hidden:true},
	        {name: 'REMIND_PPL_DRC', text : '提醒机构主管',hidden:true},
  		    {name: 'REMIND_TIME', text : '提醒时间',resutlWidth:70,xtype:'datefield',format:'Y-m-d',dataType:'date'},
  		    {name: 'CUST_SOURCE', text : '客户来源',translateType:'CUSTOMER_SOURCE_TYPE',hidden:true},
  		    {name: 'WHRY',  text : '维护人',resutlWidth:70},
  		    {name: 'WHDT',  text : '维护时间',resutlWidth:70,xtype:'datefield',format:'Y-m-d',dataType:'date'}
  		   ];

var createFormViewer =[{
	fields : ['EVENT_ID','EVENT_NAME','EVENT_TYP','WARN_FLG','REMIND_PPL','REMIND_PPL_CM','REMIND_PPL_DRC','REMIND_TIME','CUST_SOURCE','WHRY','WHDT'],
	fn : function(EVENT_ID,EVENT_NAME,EVENT_TYP,WARN_FLG,REMIND_PPL,REMIND_PPL_CM,REMIND_PPL_DRC,REMIND_TIME,CUST_SOURCE,WHRY,WHDT){
		WHRY.hidden = true;
		WHDT.hidden = true;
		return [EVENT_ID,EVENT_NAME,EVENT_TYP,WARN_FLG,REMIND_PPL,REMIND_PPL_CM,REMIND_PPL_DRC,REMIND_TIME,CUST_SOURCE,WHRY,WHDT];
	}
},{
	columnCount : 1,
	fields:['EVENT_DESC'],
	fn :function(EVENT_DESC){
		return [EVENT_DESC];
	}
}];

var editFormViewer = [{
	fields : ['EVENT_ID','EVENT_NAME','EVENT_TYP','WARN_FLG','REMIND_PPL','REMIND_TIME','CUST_SOURCE'],
	fn : function(EVENT_ID,EVENT_NAME,EVENT_TYP,WARN_FLG,REMIND_PPL,REMIND_TIME,CUST_SOURCE){
		return [EVENT_ID,EVENT_NAME,EVENT_TYP,WARN_FLG,REMIND_PPL,REMIND_TIME,CUST_SOURCE];
	}
},{
	columnCount : 1,
	fields:['EVENT_DESC'],
	fn :function(EVENT_DESC){
		return [EVENT_DESC];
	}
}];

var detailFormViewer = [{
	fields : ['EVENT_ID','EVENT_NAME','EVENT_TYP','WARN_FLG','REMIND_PPL','REMIND_TIME','CUST_SOURCE','WHRY','WHDT'],
	fn : function(EVENT_ID,EVENT_NAME,EVENT_TYP,WARN_FLG,REMIND_PPL,REMIND_TIME,CUST_SOURCE,WHRY,WHDT){
		EVENT_ID.readOnly = true;
		EVENT_ID.cls = 'x-readOnly';
		EVENT_NAME.readOnly = true;
		EVENT_NAME.cls = 'x-readOnly';
		EVENT_TYP.readOnly = true;
		EVENT_TYP.cls = 'x-readOnly';
		WARN_FLG.readOnly = true;
		WARN_FLG.cls = 'x-readOnly';
		REMIND_PPL.readOnly = true;
		REMIND_PPL.cls = 'x-readOnly';
		REMIND_TIME.readOnly = true;
		REMIND_TIME.cls = 'x-readOnly';
		CUST_SOURCE.readOnly = true;
		CUST_SOURCE.cls = 'x-readOnly';
		WHRY.readOnly = true;
		WHRY.cls = 'x-readOnly';
		WHDT.readOnly = true;
		WHDT.cls = 'x-readOnly';
		return [EVENT_ID,EVENT_NAME,EVENT_TYP,WARN_FLG,REMIND_PPL,REMIND_TIME,CUST_SOURCE,WHRY,WHDT];
	}
},{
	columnCount : 1,
	fields:['EVENT_DESC'],
	fn :function(EVENT_DESC){
		EVENT_DESC.readOnly = true;
		EVENT_DESC.cls = 'x-readOnly';
		return [EVENT_DESC];
	}
}];

var tbar = [{
	text : '收起',
	handler : function(){
		collapseSearchPanel();
	}
},{
	text : '展开',
	handler : function(){
		expandSearchPanel();
	}
},{
	text : '删除',
	hidden:JsContext.checkGrant('corCustEvent_delete'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			var messageId=getSelectedData().data.EVENT_ID;
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/acrmFCiEventInfo!batchDestroy.json?messageId='+messageId,                                
                    success : function(){
                        Ext.Msg.alert('提示', '删除成功');
                        reloadCurrentData();
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '删除失败');
                        reloadCurrentData();
                    }
                });
		});
	}
}
},new Com.yucheng.crm.common.NewExpButton({
	    formPanel : 'searchCondition',
	    hidden:JsContext.checkGrant('corCustEvent_export'),
	    url : basepath+'/acrmFCiEventInfo.json?custId='+custId
	})];

/**修改和详情面板滑入之前判断是否选择了数据，如果以选择，加载查询附件列表信息**/
var beforeviewshow = function(view){
	if(view == getEditView()||view == getDetailView()){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}	
	    var f = getSelectedData().data.WARN_FLG;
	    if('1' == f){
	    	view.contentPanel.getForm().findField('REMIND_TIME').setVisible(true);
	    }else if('0' == f){
	        view.contentPanel.getForm().findField('REMIND_TIME').setVisible(false);
	    }
	}else {
		view.contentPanel.getForm().findField('REMIND_TIME').setVisible(false);
	}
};