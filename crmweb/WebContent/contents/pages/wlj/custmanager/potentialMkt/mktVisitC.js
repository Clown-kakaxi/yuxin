/**
 * @description 企金客户营销流程-  拜访信息页面
 * @author luyy
 * @since 2014-07-25
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	,'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' // 机构放大镜
	,'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'	//用户放大镜
	,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'	//客户放大镜
]);

var url = basepath + '/mktVisitC.json';

var lookupTypes = ['IF_FLAG','HYFL','CHECK_STAT','CUST_SOURCE','VISIT_RESULT'];

var fields = [{name:"ID",text:'id',gridField:false},
              {name:"CALL_ID",text:'callId',gridField:false},
              {name:"USER_ID",text:'userId',gridField:false},
              {name:"CUST_ID",text:"客户编号",dataType:'string',gridField:false},
              {name:'CUST_NAME',text:'客户名称',xtype:'customerquery',hiddenName:'CUST_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:"INDUST_TYPE",text:"所属行业",translateType:"HYFL",searchField: true},
              {name:'USER_NAME',text:'客户经理',xtype:'userchoose',hiddenName:'USER_ID',resutlWidth:150,singleSelect: false,searchField: true},
              {name:"CHECK_STAT",text:"复核状态",translateType:"CHECK_STAT"},
              {name:"VISIT_DATE",text:"拜访日期",dataType:'date',searchField: true},
              {name:"VISIT_RESULT",text:"拜访结果",translateType:'VISIT_RESULT',allowBlank:false},
              {name:"CUST_SOURCE",text:"客户来源",translateType:'CUST_SOURCE',allowBlank:false},
              {name:"VISIT_INFO",text:"拜访内容",xtype:'textarea',resutlWidth:300,allowBlank:false},
              {name:'OWN_VISIT_MAN',text:'本行参加人员',gridField:false},
              {name:'VISIT_MAN',text:'受访人',gridField:false},
              {name:'CUST_SOURCE_MAN',text:'客户转介人',gridField:false},
              {name:'CUST_SOURCE_TEL',text:'客户转介人电话',gridField:false}
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
                url: basepath+'/mktVisitC!batchDel.json?idStr='+ID,                                
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
		fields:['ID','CUST_NAME','INDUST_TYPE',{name:'VISIT_MAN',text:'受访人',allowBlank:false},
		        {name:'CUST_SOURCE',text:'客户来源',translateType:'CUST_SOURCE',allowBlank:false,listeners:{
					select:function(){
						var value = getCurrentView().contentPanel.form.findField("CUST_SOURCE").getValue();
						if(value == '11'){
							getCurrentView().contentPanel.form.findField("CUST_SOURCE_MAN").show();
							getCurrentView().contentPanel.form.findField("CUST_SOURCE_TEL").show();
						}else{
							getCurrentView().contentPanel.form.findField("CUST_SOURCE_MAN").hide();
							getCurrentView().contentPanel.form.findField("CUST_SOURCE_TEL").hide();
						}
					}
				}},{name:'CUST_SOURCE_MAN',text:'客户转介人',xtype:'userchoose',singleSelect: true},
				{name:'CUST_SOURCE_TEL',text:'客户转介人电话'},
				{name:'VISIT_RESULT',text:'拜访结果',translateType:'VISIT_RESULT',allowBlank:false,listeners:{
					select:function(){
						var value = getCurrentView().contentPanel.form.findField("VISIT_RESULT").getValue();
						if(value == '2'){
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").show();
						}else{
							getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
						}
					}
				}},'VISIT_DATE'],
		fn:function(ID,CUST_NAME,INDUST_TYPE,VISIT_MAN,CUST_SOURCE,CUST_SOURCE_MAN,CUST_COURCE_TEL,VISIT_RESULT,VISIT_DATE){
			ID.hidden = true;
			CUST_NAME.readOnly = true;
			INDUST_TYPE.readOnly = true;
			VISIT_DATE.readOnly = true;
			CUST_NAME.cls = 'x-readOnly';
			INDUST_TYPE.cls = 'x-readOnly';
			VISIT_DATE.cls = 'x-readOnly';
			return [CUST_NAME,INDUST_TYPE,VISIT_MAN,CUST_SOURCE,CUST_SOURCE_MAN,CUST_COURCE_TEL,VISIT_RESULT,VISIT_DATE,ID];
		}
	},{
		   columnCount : 1,
		   fields:[{name:'OWN_VISIT_MAN',text:'本行参加人员',xtype:'userchoose',singleSelect: false},
		           'VISIT_INFO',
		           {name:'REFUSE_REASON',text:'拒绝理由',xtype:'textarea'}],
		   fn:function(OWN_VISIT_MAN,VISIT_INFO,REFUSE_REASON){
			   return [OWN_VISIT_MAN,VISIT_INFO,REFUSE_REASON];
		   }
	}],
	formButtons : [{
		text:'提交',
		fn : function(formPanel,basicForm){
			 if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
	        var source = formPanel.form.findField("CUST_SOURCE").getValue();
	        if(source == '11'){
	        	if(formPanel.form.findField("CUST_SOURCE_MAN").getValue()== ''
	        		||formPanel.form.findField("CUST_SOURCE_TEL").getValue() == ''){
	        		 Ext.MessageBox.alert('提示','请填写客户转介人信息');
		               return false;
	        	}
	        }
	        var value = formPanel.form.findField("VISIT_RESULT").getValue();
	        if(source == '2'){
	        	if(formPanel.form.findField("REFUSE_REASON").getValue()== ''){
	        		 Ext.MessageBox.alert('提示','请填写拒绝理由');
		               return false;
	        	}
	        }
	        var data = formPanel.getForm().getFieldValues();
			var commintData = translateDataKey(data,1);
			Ext.Msg.wait('正在处理，请稍后......','系统提示');
			Ext.Ajax.request({
					url : basepath + '/mktVisitC!save.json',
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
		fields:['ID','CUST_NAME','INDUST_TYPE',{name:'VISIT_MAN',text:'受访人'},
		        {name:'CUST_SOURCE',text:'客户来源',transalteType:'CUST_SOURCE'},{name:'CUST_SOURCE_MAN',text:'客户转介人'},
				{name:'CUST_SOURCE_TEL',text:'客户转介人电话'},
				{name:'VISIT_RESULT',text:'拜访结果',translateType:'VISIT_RESULT'},'VISIT_DATE'],
		fn:function(ID,CUST_NAME,INDUST_TYPE,VISIT_MAN,CUST_SOURCE,CUST_SOURCE_MAN,CUST_COURCE_TEL,VISIT_RESULT,VISIT_DATE){
			ID.hidden = true;
			CUST_NAME.readOnly = true;
			INDUST_TYPE.readOnly = true;
			VISIT_MAN.readOnly = true;
			CUST_SOURCE.readOnly = true;
			CUST_SOURCE_MAN.readOnly = true;
			CUST_COURCE_TEL.readOnly = true;
			VISIT_RESULT.readOnly = true;
			VISIT_DATE.readOnly = true;

			CUST_NAME.cls = 'x-readOnly';
			INDUST_TYPE.cls = 'x-readOnly';
			VISIT_MAN.cls = 'x-readOnly';
			CUST_SOURCE.cls = 'x-readOnly';
			CUST_SOURCE_MAN.cls = 'x-readOnly';
			CUST_COURCE_TEL.cls = 'x-readOnly';
			VISIT_RESULT.cls = 'x-readOnly';
			VISIT_DATE.cls = 'x-readOnly';
			return [CUST_NAME,INDUST_TYPE,VISIT_MAN,CUST_SOURCE,CUST_SOURCE_MAN,CUST_COURCE_TEL,VISIT_RESULT,VISIT_DATE,ID];
		}
	},{
		   columnCount : 1,
		   fields:[{name:'OWN_VISIT_MAN',text:'本行参加人员'},
		           'VISIT_INFO',
		           {name:'REFUSE_REASON',text:'拒绝理由',xtype:'textarea'}],
		   fn:function(OWN_VISIT_MAN,VISIT_INFO,REFUSE_REASON){
		   	OWN_VISIT_MAN.readOnly = true;VISIT_INFO.readOnly = true;REFUSE_REASON.readOnly = true;
		   	
		   	OWN_VISIT_MAN.cls = 'x-readOnly';VISIT_INFO.cls = 'x-readOnly';REFUSE_REASON.cls = 'x-readOnly';
			   return [OWN_VISIT_MAN,VISIT_INFO,REFUSE_REASON];
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

var viewshow = function(view){
	if(view._defaultTitle == '修改'){
		getCurrentView().contentPanel.form.findField("CUST_SOURCE_MAN").hide();
		getCurrentView().contentPanel.form.findField("CUST_SOURCE_TEL").hide();
		getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
	}else{
		if(getSelectedData().data.VISIT_RESULT == '2'){
			getCurrentView().contentPanel.form.findField("REFUSE_REASON").show();
		}else{
			getCurrentView().contentPanel.form.findField("REFUSE_REASON").hide();
		}
		if(getSelectedData().data.CUST_SOURCE == '11'){
			getCurrentView().contentPanel.form.findField("CUST_SOURCE_MAN").show();
			getCurrentView().contentPanel.form.findField("CUST_SOURCE_TEL").show();
		}else{
			getCurrentView().contentPanel.form.findField("CUST_SOURCE_MAN").hide();
			getCurrentView().contentPanel.form.findField("CUST_SOURCE_TEL").hide();
		}
	}
};
