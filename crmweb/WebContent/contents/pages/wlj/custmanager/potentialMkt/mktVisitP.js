/**
 * @description 个金客户营销流程 -  拜访信息页面
 * @author luyy
 * @since 2014-07-23
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'	//客户放大镜
]);

var url = basepath + '/mktVisitP.json';

var lookupTypes = ['IF_FLAG','PAR0400044','CHECK_STAT'];

var fields = [{name:"ID",text:'id',gridField:false},
              {name:"CALL_ID",text:'callId',gridField:false},
              {name:"USER_ID",text:'userId',gridField:false},
              {name:"CUST_ID",text:"客户编号",dataType:'string',gridField:false},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerquery',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'USER_NAME',text:'客户经理',xtype:'userchoose',hiddenName:'USER_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:"IF_NEW",text:"是否新客户拜访",translateType:"IF_FLAG",allowBlank:false},
              {name:"CHECK_STAT",text:"复核状态",translateType:"CHECK_STAT"},
              {name:"JOB_TYPE",text:"职业类别",translateType:"PAR0400044",searchField: true},
              {name:"VISIT_DATE",text:"拜访日期",dataType:'date',searchField: true},
              {name:"VISIT_INFO",text:"拜访内容",xtype:'textarea',resutlWidth:300,allowBlank:false},
              {name:"VISIT_RESULT",text:"拜访结果",xtype:'textarea',resutlWidth:300,allowBlank:false}
             ];

var tbar = [{
	text:'删除',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}

		var ID = '';
		for (var i=0;i<getAllSelects().length;i++){
			if(getAllSelects()[i].data.USER_ID != __userId || getAllSelects()[i].data.CHECK_STAT != '1'){
				Ext.Msg.alert('提示','只能选择本人[草稿]状态的拜访信息！');
				return false;
			}
			ID += getAllSelects()[i].data.ID;
			ID += ",";
		}
		ID = ID.substring(0, ID.length-1);
		Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
			if(buttonId.toLowerCase() == "no"){
			return false;
			} 
		    Ext.Ajax.request({
                url: basepath+'/mktVisitP!batchDel.json?idStr='+ID,                                
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
}];

var customerView = [{
	title:'修改',
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		columnCount : 2,
		fields:['ID','CUST_NAME','JOB_TYPE','IF_NEW','USER_NAME','VISIT_DATE'],
		fn:function(ID,CUST_NAME,JOB_TYPE,IF_NEW,USER_NAME,VISIT_DATE){
			ID.hidden = true;
			CUST_NAME.readOnly = true;
			JOB_TYPE.readOnly = true;
			IF_NEW.readOnly = true;
			USER_NAME.readOnly = true;
			VISIT_DATE.readOnly = true;
			CUST_NAME.cls = 'x-readOnly';
			JOB_TYPE.cls = 'x-readOnly';
			IF_NEW.cls = 'x-readOnly';
			USER_NAME.cls = 'x-readOnly';
			VISIT_DATE.cls = 'x-readOnly';
			return [CUST_NAME,JOB_TYPE,IF_NEW,USER_NAME,VISIT_DATE,ID];
		}
	},{
		   columnCount : 1,
		   fields:['VISIT_INFO','VISIT_RESULT'],
		   fn:function(VISIT_INFO,VISIT_RESULT){
			   return [CALL_INFO,VISIT_RESULT];
		   }
	}],
	formButtons : [{
		text:'提交',
		fn : function(formPanel,basicForm){
			 if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
	        var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
			Ext.Ajax.request({
					url : basepath + '/mktVisitP!save.json',
					method : 'GET',
					params : commintData,
					success : function(response) {
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
	title:'查看',
	type:'form',
	autoLoadSeleted : true,
	groups : [{
		columnCount : 2,
		fields:['CUST_NAME','JOB_TYPE','IF_NEW','USER_NAME','VISIT_DATE'],
		fn:function(CUST_NAME,JOB_TYPE,IF_NEW,USER_NAME,VISIT_DATE){
			return [CUST_NAME,JOB_TYPE,IF_NEW,USER_NAME,VISIT_DATE];
		}
	},{
		   columnCount : 1,
		   fields:['VISIT_INFO','VISIT_RESULT'],
		   fn:function(VISIT_INFO,VISIT_RESULT){
			   return [CALL_INFO,VISIT_RESULT];
		   }
	}]
}];

var beforeviewshow = function(view){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		if(view._defaultTitle == '修改'){
			if(getSelectedData().data.CHECK_STAT != '1'){
				Ext.Msg.alert('提示','只能修改[草稿]状态的拜访信息');
				return false;
			}
		}
};
