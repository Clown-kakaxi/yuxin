/**
 * @description 个金客户营销流程 -  电访信息页面
 * @author luyy
 * @since 2014-07-22
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'	//客户放大镜
]);

var url = basepath + '/mktCallP.json';

var lookupTypes = ['IF_FLAG','PAR0400044','CUST_SOURCE','CALL_RESULT','CHECK_STAT'];

var fields = [{name:"ID",text:'id',gridField:false},
              {name:"CUST_ID",text:"客户编号",dataType:'string',gridField:false},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerquery',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:'USER_NAME',text:'客户经理',xtype:'userchoose',hiddenName:'USER_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:"JOB_TYPE",text:"职业类别",translateType:"PAR0400044",searchField: true},
              {name:"PHONE_DATE",text:"电访日期",dataType:'date',searchField: true},
              {name:"CALL_RESULT",text:"电访结果",translateType:"CALL_RESULT",searchField: true,allowBlank:false},
              {name:"CALL_INFO",text:"电访内容",xtype:'textarea',resutlWidth:300,allowBlank:false},
              {name:"LINK_PHONE",text:"联系电话",dataType:'string',gridField:false},
              {name:"IF_NEW",text:"是否新客户",translateType:"IF_FLAG",gridField:false,allowBlank:false},
              {name:"CUST_SOURCE",text:"客户来源",translateType:"CUST_SOURCE",gridField:false,allowBlank:false},
              {name:"VISIT_DATE",text:"拜访日期",dataType:'date',gridField:false},
              {name:"REFUSE_REASON",text:"拒绝理由",xtype:'textarea',gridField:false},
              {name:"RECALL_DATE",text:"回拨日期",dataType:'date',gridField:false},
              {name:"CHECK_STAT",text:"复核状态",translateType:"CHECK_STAT"}];

var customerView = [{
	title:'新增',
	type:'form',
	groups:[{
		columnCount : 2,
		fields:[{name:'CUST_NAME',text:'客户名称',dataType:'customerquery',hiddenName:'CUST_ID',allowBlank:false,custType:'2',
			singleSelected:true,newCust:true,callback:function(a){
				getCurrentView().setValues({
					'JOB_TYPE':getCurrentView().contentPanel.form.findField("CUST_NAME").jobType,
					'LINK_PHONE':getCurrentView().contentPanel.form.findField("CUST_NAME").mobileNum
				});
		}},'JOB_TYPE','LINK_PHONE','IF_NEW','CUST_SOURCE',
		{name:'CALL_RESULT',text:'电访结果',translateType:'CALL_RESULT',listeners:{
			select:function(){
				var value = getCurrentView().contentPanel.form.findField("CALL_RESULT").getValue();
				if(value == '1'){//同意拜访
					getCurrentView().contentPanel.form.findField("VISIT_DATE").show();
					getCurrentView().contentPanel.form.findField("RECALL_DATE").hide();
					getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
				}else if(value == '2'){//拒绝拜访
					getCurrentView().contentPanel.form.findField("VISIT_DATE").hide();
					getCurrentView().contentPanel.form.findField("RECALL_DATE").hide();
					getCurrentView().contentPanel.form.findField("REFUSE_REASON").show();
				}else if(value == '3'){//改期回拨
					getCurrentView().contentPanel.form.findField("VISIT_DATE").hide();
					getCurrentView().contentPanel.form.findField("RECALL_DATE").show();
					getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
				}
			}
		}},'VISIT_DATE','RECALL_DATE'],
		fn:function(CUST_NAME,JOB_TYPE,LINK_PHONE,IF_NEW,CUST_SOURCE,CALL_RESULE,VISIT_DATE,RECALL_DATE){
			JOB_TYPE.readOnly = true;
			JOB_TYPE.cls = 'x-readOnly';
			LINK_PHONE.readOnly = true;
			LINK_PHONE.cls = 'x-readOnly';
			VISIT_DATE.hidden = true;
			RECALL_DATE.hidden = true;
			return [CUST_NAME,JOB_TYPE,LINK_PHONE,IF_NEW,CUST_SOURCE,CALL_RESULE,VISIT_DATE,RECALL_DATE];
		}
	},{
	   columnCount : 1,
	   fields:['CALL_INFO','REFUSE_REASON'],
	   fn:function(CALL_INFO,REFUSE_REASON){
		   REFUSE_REASON.hidden = true;
		   return [CALL_INFO,REFUSE_REASON];
	   }
	}],
	formButtons : [{
		text:'提交',
		fn : function(formPanel,basicForm){
			 if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
	           var value = getCurrentView().contentPanel.form.findField("CALL_RESULT").getValue();
	           if(value == '1'){//同意拜访
	        	   if(getCurrentView().contentPanel.form.findField("VISIT_DATE").getValue() == ''){
	        		   Ext.MessageBox.alert('提示','请填写[拜访日期]');
		               return false;
	        	   }
				}else if(value == '2'){//拒绝拜访
					if(getCurrentView().contentPanel.form.findField("REFUSE_REASON").getValue() == ''){
		        		   Ext.MessageBox.alert('提示','请填写[拒绝理由]');
			               return false;
		        	   }
				}else if(value == '3'){//改期回拨
					if(getCurrentView().contentPanel.form.findField("RECALL_DATE").getValue() == ''){
		        		   Ext.MessageBox.alert('提示','请填写[回拨日期]');
			               return false;
		        	   }
				}
	        var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
			Ext.Ajax.request({
					url : basepath + '/mktCallP!save.json',
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
	groups:[{
		columnCount : 2,
		fields:['CUST_NAME','JOB_TYPE','LINK_PHONE','IF_NEW','CUST_SOURCE','CALL_RESULT'
		,'VISIT_DATE','RECALL_DATE'],
		fn:function(CUST_NAME,JOB_TYPE,LINK_PHONE,IF_NEW,CUST_SOURCE,CALL_RESULE,VISIT_DATE,RECALL_DATE){
			return [CUST_NAME,JOB_TYPE,LINK_PHONE,IF_NEW,CUST_SOURCE,CALL_RESULE,VISIT_DATE,RECALL_DATE];
		}
	},{
		   columnCount : 1,
		   fields:['CALL_INFO','REFUSE_REASON'],
		   fn:function(CALL_INFO,REFUSE_REASON){
			   REFUSE_REASON.hidden = true;
			   return [CALL_INFO,REFUSE_REASON];
		   }
	}]
}];

var beforeviewshow = function(view){
	if(view._defaultTitle == '查看'){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
	}
};
var viewshow = function(view){
	if(view._defaultTitle == '新增'){
		getCurrentView().contentPanel.form.findField("VISIT_DATE").hide();
		getCurrentView().contentPanel.form.findField("RECALL_DATE").hide();
		getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
	}else if(view._defaultTitle == '查看'){
		if(getSelectedData().data.CALL_RESULT == '1'){
			getCurrentView().contentPanel.form.findField("VISIT_DATE").show();
			getCurrentView().contentPanel.form.findField("RECALL_DATE").hide();
			getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
		}else if(getSelectedData().data.CALL_RESULT == '2'){
			getCurrentView().contentPanel.form.findField("VISIT_DATE").hide();
			getCurrentView().contentPanel.form.findField("RECALL_DATE").hide();
			getCurrentView().contentPanel.form.findField("REFUSE_REASON").show();
		}else{
			getCurrentView().contentPanel.form.findField("VISIT_DATE").hide();
			getCurrentView().contentPanel.form.findField("RECALL_DATE").show();
			getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
		}
		
		
	}
};