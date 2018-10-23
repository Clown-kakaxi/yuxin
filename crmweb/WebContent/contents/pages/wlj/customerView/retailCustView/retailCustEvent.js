/**
*@description 360客户视图 零售事件信息
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

var createView = !JsContext.checkGrant('custEvent_create');
var editView = !JsContext.checkGrant('custEvent_modify');
var detailView = !JsContext.checkGrant('custEvent_detail');

var lookupTypes = ['MARKET_EVENT_TYP',//事件类型
                   'IF_FLAG',//是否标志
                   'CUST_SOURCE',//客户来源
                   'REMIND_OBJ_TYPE'
                   ];
//var localLookup = {'REMIND_TYPE':
//					[
//                       {key:'1',value:'本人'},
//                       {key:'2',value:'本机构主管'}
//                    ]
//};

var custId =_custId;
//flag = 0 对私 flag = 1 对公
var url = basepath+'/acrmFCiEventInfo.json?custId='+custId+'&flag=0';

var fields = [
  		    {name: 'EVENT_ID',hidden : true},
  		    {name: 'EVENT_NAME', text : '事件名称', searchField: true,allowBlank:false}, 
  		    {name: 'EVENT_TYP', text : '事件类型',searchField: true,translateType:'MARKET_EVENT_TYP',allowBlank:false}, 
  		    {name: 'EVENT_DESC', text : '事件内容',resutlWidth:350,xtype:'textarea',allowBlank:false},                                   
  		    {name: 'WARN_FLG',text:'是否提醒',translateType:'IF_FLAG',resutlWidth:60,allowBlank:false,
  		    	listeners:{
  		    		select:function(combo,record){
  		    			var v = this.getValue();
  		    			if(v=='1'){//1是0否
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_TIME').setVisible(true);
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL').setVisible(true);
  		    			}else if(v=='0'){
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_TIME').setVisible(false);
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL').setVisible(false);
  		    			}
  					}
	        	}},  
  		    {name: 'REMIND_PPL_CM_NAME', text : '客户经理',readOnly:true,gridField:false},
  		    {name: 'REMIND_PPL_DRC_NAME', text : '本机构主管',gridField:false,xtype:'userchoose',singleSelect:true,
  		    	searchRoleType:('103,111,202,204,303,302,308,310,311,101562,110720'),hiddenName:'REMIND_PPL_DRC'},
  		    {name: 'REMIND_TIME', text : '提醒时间',resutlWidth:70,xtype:'datefield',format:'Y-m-d',dataType:'date'},
  		    {name: 'CUST_SOURCE', text : '客户来源',translateType:'CUST_SOURCE',allowBlank:false},
  		    {name: 'REMIND_PPL', text:'提醒对象',translateType:'REMIND_OBJ_TYPE',multiSelect:true,multiSeparator:',',resutlWidth: 100,
  		    	listeners:{
  		    		select:function(combo,record){
  		    			var v = this.getValue();
  		    			if(v=='1,2'){
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_CM_NAME').setVisible(true);
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_CM_NAME').setValue(__userName);
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_DRC_NAME').setVisible(true);
  		    			}else if(v=='1'){
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_DRC_NAME').setValue("");
  		    				
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_CM_NAME').setVisible(true);
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_CM_NAME').setValue(__userName);
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_DRC_NAME').setVisible(false);
  		    			}else if(v=='2'){
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_CM_NAME').setValue("");
  		    				
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_CM_NAME').setVisible(false);
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_DRC_NAME').setVisible(true);
  		    			}else{
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_CM_NAME').setValue("");
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_DRC_NAME').setValue("");
  		    				
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_CM_NAME').setVisible(false);
  		    				getCurrentView().contentPanel.getForm().findField('REMIND_PPL_DRC_NAME').setVisible(false);
  		    			}
  					}
	        	}},
  		    {name: 'WHRY',  text : '维护人',resutlWidth:70},
  		    {name: 'WHDT',  text : '维护时间',resutlWidth:70,xtype:'datefield',format:'Y-m-d',dataType:'date'}
  		   ];

var createFormViewer =[{
	fields : ['EVENT_ID','EVENT_NAME','EVENT_TYP','CUST_SOURCE','WARN_FLG','REMIND_TIME','REMIND_PPL','WHRY','WHDT'],
	fn : function(EVENT_ID,EVENT_NAME,EVENT_TYP,CUST_SOURCE,WARN_FLG,REMIND_TIME,REMIND_PPL,WHRY,WHDT){
		WHRY.hidden = true;
		WHDT.hidden = true;
		return [EVENT_ID,EVENT_NAME,EVENT_TYP,CUST_SOURCE,WARN_FLG,REMIND_TIME,REMIND_PPL,WHRY,WHDT];
	}
},{
	columnCount : 2,
	fields:['REMIND_PPL_CM_NAME','REMIND_PPL_DRC_NAME'],
	fn :function(REMIND_PPL_CM_NAME,REMIND_PPL_DRC_NAME){
		REMIND_PPL_DRC_NAME.text = '<font color=red>*</font>'+REMIND_PPL_DRC_NAME.text;
		return [REMIND_PPL_CM_NAME,REMIND_PPL_DRC_NAME];
	}
},{
	columnCount : 1,
	fields:['EVENT_DESC'],
	fn :function(EVENT_DESC){
		EVENT_DESC.anchor = '97%';
		return [EVENT_DESC];
	}
}];

var editFormViewer = [{
	fields : ['EVENT_ID','EVENT_NAME','EVENT_TYP','CUST_SOURCE','WARN_FLG','REMIND_PPL','REMIND_TIME'],
	fn : function(EVENT_ID,EVENT_NAME,EVENT_TYP,CUST_SOURCE,WARN_FLG,REMIND_PPL,REMIND_TIME){
		return [EVENT_ID,EVENT_NAME,EVENT_TYP,CUST_SOURCE,WARN_FLG,REMIND_PPL,REMIND_TIME];
	}
},{
	columnCount : 1,
	fields:['EVENT_DESC'],
	fn :function(EVENT_DESC){
		return [EVENT_DESC];
	}
},{
	columnCount : 2,
	fields:['REMIND_PPL_CM_NAME','REMIND_PPL_DRC_NAME'],
	fn :function(REMIND_PPL_CM_NAME,REMIND_PPL_DRC_NAME){
		return [REMIND_PPL_CM_NAME,REMIND_PPL_DRC_NAME];
	}
}];

var detailFormViewer = [{
	fields : ['EVENT_ID','EVENT_NAME','EVENT_TYP','WARN_FLG','REMIND_PPL','REMIND_TIME','CUST_SOURCE','WHRY','WHDT'],
	fn : function(EVENT_ID,EVENT_NAME,EVENT_TYP,WARN_FLG,REMIND_PPL,REMIND_TIME,CUST_SOURCE,WHRY,WHDT){
		EVENT_NAME.readOnly = true;
		EVENT_NAME.cls = 'x-readOnly';
		EVENT_TYP.readOnly = true;
		EVENT_TYP.cls = 'x-readOnly';
		WARN_FLG.readOnly = true;
		WARN_FLG.cls = 'x-readOnly';
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
},{
	columnCount : 2,
	fields:['REMIND_PPL_CM_NAME','REMIND_PPL_DRC_NAME'],
	fn :function(REMIND_PPL_CM_NAME,REMIND_PPL_DRC_NAME){
		return [REMIND_PPL_CM_NAME,REMIND_PPL_DRC_NAME];
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
	hidden:JsContext.checkGrant('custEvent_delete'),
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
    hidden:JsContext.checkGrant('custEvent_export'),
    url : basepath+'/acrmFCiEventInfo.json?custId='+custId+'&flag=0'
})];

/**修改和详情面板滑入之前判断是否选择了数据，如果以选择，加载查询附件列表信息**/
var beforeviewshow = function(view){
	if(view == getEditView()||view == getDetailView()){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		if(view == getEditView()){
			var whry = getSelectedData().data.WHRY;
			if(whry!=__userName){
				Ext.Msg.alert('提示','事件维护者才能维护此项!');
				return false;
			}
		}
		var c =  getSelectedData().data.REMIND_PPL;
	    if('1' == c){
	    	view.contentPanel.getForm().findField('REMIND_PPL_CM_NAME').setVisible(true);
	    	view.contentPanel.getForm().findField('REMIND_PPL_DRC_NAME').setVisible(false);
	    }else if('2'== c){
	    	view.contentPanel.getForm().findField('REMIND_PPL_DRC_NAME').setVisible(true);
	    	view.contentPanel.getForm().findField('REMIND_PPL_CM_NAME').setVisible(false);
	    }else if('1,2' == c){
	    	view.contentPanel.getForm().findField('REMIND_PPL_DRC_NAME').setVisible(true);
	    	view.contentPanel.getForm().findField('REMIND_PPL_CM_NAME').setVisible(true);
	    }else{
	    	view.contentPanel.getForm().findField('REMIND_PPL_DRC_NAME').setVisible(false);
	    	view.contentPanel.getForm().findField('REMIND_PPL_CM_NAME').setVisible(false);
	    }
	    
	    var f = getSelectedData().data.WARN_FLG;
	    if('1' == f){
	    	view.contentPanel.getForm().findField('REMIND_TIME').setVisible(true);
	    	view.contentPanel.getForm().findField('REMIND_PPL').setVisible(true);
	    }else if('0' == f){
	        view.contentPanel.getForm().findField('REMIND_TIME').setVisible(false);
	    	view.contentPanel.getForm().findField('REMIND_PPL').setVisible(false);
	    }
	}else {
		view.contentPanel.getForm().findField('REMIND_PPL_CM_NAME').setVisible(false);
		view.contentPanel.getForm().findField('REMIND_PPL_DRC_NAME').setVisible(false);
		
		view.contentPanel.getForm().findField('REMIND_TIME').setVisible(false);
		view.contentPanel.getForm().findField('REMIND_PPL').setVisible(false);
	}
};
/**新增、修改提交时,字段校验逻辑*/
var validates = [
{
	desc : '请选择提醒对象!',
	fn : function(WARN_FLG,REMIND_PPL){
		if(WARN_FLG != 0){//是否提醒 1是   0 否
			if(REMIND_PPL == null || REMIND_PPL == ''){
				return false;
			}
		}
	},
	dataFields : ['WARN_FLG','REMIND_PPL']
},{
	desc : '请选择本机构主管!',
	fn : function(WARN_FLG,REMIND_PPL,REMIND_PPL_DRC_NAME){
		if(WARN_FLG == 1){
			if(REMIND_PPL != '1' && REMIND_PPL_DRC_NAME == ''){
				return false;
			}
		}
	},
	dataFields : ['WARN_FLG','REMIND_PPL','REMIND_PPL_DRC_NAME']
}];