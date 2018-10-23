/**
 * @description 客户托管处理页面
 * @author luyy
 * @since 2014-07-10
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
    '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
    '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'
]);
	
var url = basepath+'/custTrust.json';

var createView = false;
var editView = false;
var detailView = false;

var ifCharge = "mgr";
var roleCodes = __roleCodes;// 当前用户拥有的据角色编码
if (roleCodes != null && roleCodes != "") {
	var roleArrs = roleCodes.split('$');
	for ( var i = 0; i < roleArrs.length; i++) {
		if (roleArrs[i] == "R310") {
			ifCharge = "charge";//主管角色
		}
	}
}

var lookupTypes = ['TRUST_STAT','MAINTAIN_TYPE'];

var fields = [
	{name:'ID',text:'ID',hidden:true},
  	{name:'TID',text:'TID',hidden:true},//托管记录id
  	{name:'CUST_ID',text:'客户编号',dataType:'string',searchField:true},
  	{name:'CUST_NAME',text:'客户名称',dataType:'string',searchField:true},
  	{name:'MGR_NAME',text:'客户经理',dataType:'string',searchField : true},
  	{name:'MAIN_TYPE',text:'主协办类型',translateType:'MAINTAIN_TYPE'},
  	{name:'MGR_ID',hidden:true},
  	{name:'INSTITUTION',hidden:true},
  	{name:'INSTITUTION_NAME',text:'机构',searchField:true,hiddenName:'INSTITUTION',xtype:'orgchoose'},
  	{name:'TRUST_STAT',text:'托管状态',translateType:'TRUST_STAT',searchField:true},
  	{name:'TRUST_MGR_NAME',text:'托管客户经理',dataType:'string'},
  	{name:'DEAD_LINE',text:'托管有效期',dataType:'date'}
];
var tbar = [{
	text:'托管回收',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			var ID = '';
			for (var i=0;i<getAllSelects().length;i++){
				if(getAllSelects()[i].data.TRUST_STAT != '02'){
					Ext.Msg.alert('提示','只能选择[托管中]的数据！');
					return false;
				}else{
					ID += getAllSelects()[i].data.TID;
					ID += ",";
				}
			}
			ID = ID.substring(0, ID.length-1);
			Ext.MessageBox.confirm('提示','确定回收吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return false;
				}
			    Ext.Ajax.request({
                    url: basepath+'/custTrust!batchUpdate.json?idStr='+ID,                                
                    success : function(){
                        Ext.Msg.alert('提示', '回收成功');
                        reloadCurrentData();
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '回收失败');
                    }
                });
			});
		}
	}
}];

var customerView = [{
	title:'客户托管',
	type:'form',
	groups : [{
		columnCount :2,
		fields : [
		    {name:'CUST_ID',text:'客户编号',allowBlank:false,readOnly:true,cls:'x-readOnly'},
            {name:'CUST_NAME',text:'客户名称',allowBlank:false,readOnly:true,cls:'x-readOnly'},
            {name:'ORG_ID',text:'',hidden:true},
            {name:'MGR_ID',text:'',hidden:true},
            {name:'ORG_NAME',text:'机构',allowBlank:false,readOnly:true,cls:'x-readOnly'},
            {name:'MGR_NAME',text:'客户经理',allowBlank:false,readOnly:true,cls:'x-readOnly'},
            {name:'TRUST_MGR_NAME',text:'<font color=red>*</font>托管客户经理',xtype:'userchoose',hiddenName:'TRUST_MGR_ID',
        	    singleSelect: true,allowBlank:false,orgId:__units,searchRoleType:('303,305'),
        	    callback:function(b){
					if(b[0].data.userId == getCurrentView().getValues().MGR_ID ){
						 Ext.MessageBox.alert('提示','不能托管给原客户经理');
						 getCurrentView().setValues({
							 TRUST_MGR_NAME:'',
							 TRUST_MGR_ID:''
						    });
					}
				}},
            {name:'SET_DATE',text:'托管日期',allowBlank:false,xtype : 'datefield',format : 'Y-m-d'},
            {name:'DEAD_LINE',text:'托管有效期',allowBlank:false,xtype : 'datefield',format : 'Y-m-d'}],
		fn : function(CUST_ID,CUST_NAME,ORG_ID,MGR_ID,ORG_NAME,MGR_NAME,TRUST_MGR_NAME,SET_DATE,DEAD_LINE){
			return [CUST_ID,CUST_NAME,ORG_NAME,MGR_NAME,TRUST_MGR_NAME,SET_DATE,DEAD_LINE,ORG_ID,MGR_ID];
		}
	},{
		columnCount :1,
		fields : [{name:'TRUST_REASON',text:'托管原因',xtype:'textarea'}],
		fn : function(TRUST_REASON){
			TRUST_REASON.anchor = '95%';
			return [TRUST_REASON];
		}
	}],
	formButtons:[{
		text:'确定',
		id:'charge',
		fn : function(formPanel,basicForm){
			if (!formPanel.getForm().isValid()) {
	        	Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	            return false;
	        }
	        var SET_DATE = formPanel.form.findField('SET_DATE').getValue();
	   		var DEAD_LINE = formPanel.form.findField('DEAD_LINE').getValue();
	   		if (SET_DATE <= new Date()) {
	   			Ext.Msg.alert('提示', '“托管日期”不能早于当前日期！');
	   			return false;
	   		}
	   		
	   		if (SET_DATE >= DEAD_LINE) {
	   			Ext.Msg.alert('提示', '“托管日期”不能晚于或等于“托管有效期”！');
	   			return false;
	   		}
			var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
			Ext.Ajax.request({
				url : basepath + '/custTrust!save.json?type=charge',
				method : 'POST',
				params : commintData,
				success : function(r) {
					Ext.Msg.alert('提示','操作成功！');
					reloadCurrentData();
				}
			});
		}
	},{
		text:'确定',
		id:'mgr',
		fn : function(formPanel,basicForm){
			if (!formPanel.getForm().isValid()) {
               	Ext.MessageBox.alert('提示','输入有误,请检查输入项');
               	return false;
           	};
           	var SET_DATE = formPanel.form.findField('SET_DATE').getValue();
			var DEAD_LINE = formPanel.form.findField('DEAD_LINE').getValue();
	   		if (SET_DATE <= new Date()) {
	   			Ext.Msg.alert('提示', '“托管日期”不能早于当前日期！');
	   			return false;
	   		}
	   		
	   		if (SET_DATE >= DEAD_LINE) {
	   			Ext.Msg.alert('提示', '“托管日期”不能晚于或等于“托管有效期”！');
	   			return false;
	   		}
			var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
			Ext.Ajax.request({
				url : basepath + '/custTrust!save.json?type=mgr',
				method : 'POST',
				params : commintData,
				success : function(response) {
					Ext.Msg.alert('提示','托管成功！');
					reloadCurrentData();
				 	/*var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
					*/					
				}
			});
		}
	}]
}];

var beforeviewshow = function(view){
	if(view._defaultTitle == '客户托管'){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}else{
			if(getSelectedData().data.TRUST_STAT != ''){
				Ext.Msg.alert('提示','所选数据不能设置托管');
				return false;
			}
		}
	}
};

var viewshow = function(view){
	if(view._defaultTitle == '客户托管'){
		if(ifCharge == 'mgr'){
			Ext.get('charge').hide();
			Ext.get('mgr').show();
		}else{
			Ext.get('charge').show();
			Ext.get('mgr').hide();
		}
		getCurrentView().setValues({
			 CUST_ID:getSelectedData().data.CUST_ID,
			 CUST_NAME:getSelectedData().data.CUST_NAME,
			 ORG_ID:getSelectedData().data.INSTITUTION,
			 ORG_NAME:getSelectedData().data.INSTITUTION_NAME,
			 MGR_ID:getSelectedData().data.MGR_ID,
			 MGR_NAME:getSelectedData().data.MGR_NAME
		});
	}
};