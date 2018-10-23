/**
*@description 360客户视图 对公证件信息
*@author:xiebz
*@since:2014-07-19
*@checkedby:
*/
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
		]);
var createView = !JsContext.checkGrant('papersCust_create');
var editView = !JsContext.checkGrant('papersCust_modify');
var detailView = !JsContext.checkGrant('papersCust_detail');
var needCondition = false;
var lookupTypes = [
	'XD000040',//证件类型
	'PAR1300083',//发证机构
	'XD000301',
	'XD000141',
	'XD000142',
	'XD000300'
];

var custId =_custId;
var createFormCfgs = true;
var editFormCfgs = true;

var url = basepath+'/acrmFCiOrgIdentifierInfo.json?custId='+custId;

var fields = [
    {name: 'IDENT_ID',hidden : true},
    {name: 'IDENT_TYPE', text : '证件类型',translateType:'XD000040',allowBlank:false}, 
    {name: 'IDENT_NO', text : '证件号码',allowBlank:false,vtype:'non-chinese'}, 
    {name: 'IDENT_CUST_NAME', text : '证件上名称',resutlWidth:200,allowBlank:false},                                 
    {name: 'IDEN_REG_DATE',text:'证件登记日期',xtype:'datefield',format:'Y-m-d',allowBlank:false,resutlWidth:70},  
    {name: 'IDENT_EXPIRED_DATE', text : '证件到期日期',xtype:'datefield',format:'Y-m-d',allowBlank:false,resutlWidth:70},
    {name: 'IDENT_ORG', text : '发证机构',allowBlank:false,resutlWidth:70,translateType:'PAR1300083'},
    {name: 'IDENT_CHECK_FLAG', text : '年检标识',allowBlank:false,resutlWidth:60,translateType:'XD000141'},

    {name: 'CUST_ID', text : '客户编号',hidden:true},
    {name: 'IDENT_DESC', text : '证件描述',hidden:true},
    {name: 'COUNTRY_OR_REGION',  text : '发证国家或地区',hidden:true},
    {name: 'IDENT_APPROVE_UNIT',  text : '证件批准单位',hidden:true},
    
    {name: 'IDENT_CHECKING_DATE', text : '证件年检到期日',hidden:true,xtype:'datefield',format:'Y-m-d'},
    {name: 'IDENT_CHECKED_DATE', text : '证件年检日期',hidden:true,xtype:'datefield',format:'Y-m-d'},
    {name: 'IDENT_VALID_PERIOD', text : '证件有效期',hidden:true,xtype:'datefield',format:'Y-m-d'},
    {name: 'IDENT_EFFECTIVE_DATE', text : '证件生效日期',hidden:true,xtype:'datefield',format:'Y-m-d'},
    {name: 'IDENT_VALID_FLAG', text : '证件有效标志',hidden:true,translateType:'XD000142'},
    {name: 'IDENT_PERIOD', text : '证件期限',hidden:true},
    
    {name: 'IS_OPEN_ACC_IDENT', text : '是否开户证件',hidden:true,translateType:'XD000300'},
    {name: 'OPEN_ACC_IDENT_MODIFIED_FLAG', text : '开户证件修改标志',hidden:true,translateType:'XD000301'},
    {name: 'IDENT_MODIFIED_TIMEE', text : '证件修改时间',hidden:true},
    {name: 'VERIFY_DATE', text : '校验日期',hidden:true,xtype:'datefield',format:'Y-m-d'},
    {name: 'VERIFY_EMPLOYEE', text : '校验员工',hidden:true},
    {name: 'VERIFY_RESULT', text : '校验结果',hidden:true},
    {name: 'LAST_UPDATE_SYS', text : '最后更新系统',hidden:true},
    {name: 'LAST_UPDATE_USER_NAME', text : '最后更新人',hidden:true},	
    {name: 'LAST_UPDATE_TMM', text : '最后更新时间',gridField:false},
    {name: 'TX_SEQ_NO', text : '交易流水号',hidden:true}
    
   ];

//var createFormViewer =[{
//	fields : ['IDENT_ID','IDENT_TYPE','IDENT_NO','IDENT_CUST_NAME','IDEN_REG_DATE','IDENT_EXPIRED_DATE','IDENT_ORG','IDENT_CHECK_FLAG',
//	          'CUST_ID','IDENT_DESC','COUNTRY_OR_REGION','IDENT_APPROVE_UNIT','IDENT_CHECKING_DATE','IDENT_CHECKED_DATE','IDENT_VALID_PERIOD',
//	          'IDENT_EFFECTIVE_DATE','IDENT_VALID_FLAG','IDENT_PERIOD','IS_OPEN_ACC_IDENT','OPEN_ACC_IDENT_MODIFIED_FLAG','IDENT_MODIFIED_TIMEE',
//	          'VERIFY_DATE','VERIFY_EMPLOYEE','VERIFY_RESULT','LAST_UPDATE_SYS','LAST_UPDATE_USER','LAST_UPDATE_TMM','TX_SEQ_NO'],
//	fn : function(IDENT_ID,IDENT_TYPE,IDENT_NO,IDENT_CUST_NAME,IDEN_REG_DATE,IDENT_EXPIRED_DATE,IDENT_ORG,IDENT_CHECK_FLAG,
//	          CUST_ID,IDENT_DESC,COUNTRY_OR_REGION,IDENT_APPROVE_UNIT,IDENT_CHECKING_DATE,IDENT_CHECKED_DATE,IDENT_VALID_PERIOD,
//	          IDENT_EFFECTIVE_DATE,IDENT_VALID_FLAG,IDENT_PERIOD,IS_OPEN_ACC_IDENT,OPEN_ACC_IDENT_MODIFIED_FLAG,IDENT_MODIFIED_TIMEE,
//	          VERIFY_DATE,VERIFY_EMPLOYEE,VERIFY_RESULT,LAST_UPDATE_SYS,LAST_UPDATE_USER_NAME,LAST_UPDATE_TMM,TX_SEQ_NO){
//		LAST_UPDATE_TMM.hidden = true;
//		return [IDENT_ID,IDENT_TYPE,IDENT_NO,IDENT_CUST_NAME,IDEN_REG_DATE,IDENT_EXPIRED_DATE,IDENT_ORG,IDENT_CHECK_FLAG,
//		          CUST_ID,IDENT_DESC,COUNTRY_OR_REGION,IDENT_APPROVE_UNIT,IDENT_CHECKING_DATE,IDENT_CHECKED_DATE,IDENT_VALID_PERIOD,
//		          IDENT_EFFECTIVE_DATE,IDENT_VALID_FLAG,IDENT_PERIOD,IS_OPEN_ACC_IDENT,OPEN_ACC_IDENT_MODIFIED_FLAG,IDENT_MODIFIED_TIMEE,
//		          VERIFY_DATE,VERIFY_EMPLOYEE,VERIFY_RESULT,LAST_UPDATE_SYS,LAST_UPDATE_USER_NAME,LAST_UPDATE_TMM,TX_SEQ_NO];
//	}
//}];
//
//var createFormCfgs = {
//	formButtons:[{
//		text: '提交',
//		fn: function(formPanel, baseform){
//			if (!baseform.isValid()) {
//    			Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
//    		 	return false;
//    	    }
//    	    var json2 = baseform.getValues(false);
//			var perModel = [];
//			for(var key in json2){
//				var pcbhModel = {};
//				var field = baseform.findField(key);
//				if(field.getXType() == 'combo'){
//					var s = field.getValue();
//					if(!field.hidden && s != '' && s != undefined){
//						pcbhModel.custId = _custId;
//						pcbhModel.updateBeCont = '';
//						pcbhModel.updateAfCont = s;
//						pcbhModel.updateAfContView = field.getRawValue();//getStoreFieldValue(field.store,'key',s,'value');
//						pcbhModel.updateItem = field.fieldLabel;
//						pcbhModel.updateItemEn = field.name;
//						pcbhModel.fieldType = '1';
//						perModel.push(pcbhModel);
//					}
//				}else{
//					var s = field.getValue();
//					if(!field.hidden && s != '' && s != undefined){
//						pcbhModel.custId = _custId;
//						pcbhModel.updateBeCont = '';
//						pcbhModel.updateAfCont = s;
//						pcbhModel.updateAfContView = s;
//						pcbhModel.updateItem = field.fieldLabel;
//						pcbhModel.updateItemEn = field.name;
//						pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
//						perModel.push(pcbhModel);
//					}
//				}
//			}
//			if(perModel.length < 1){
//				Ext.Msg.alert('提示', '未作任何修改,不允许提交!');
//				return false;
//			}
//			addKeyFn(perModel,_custId,formPanel,'IDENT_ID','证件ID');
//			addFieldFn(perModel,_custId,_custId,_custId,'CUST_ID');
//			Ext.Msg.wait('正在提交，请稍后......','系统提示');
//			Ext.Ajax.request({
//	        	url : basepath + '/acrmFCiOrgIdentifierInfo!save.json',
//	            method : 'POST',
//	            params : {
//			    	'perModel':Ext.encode(perModel),
//			    	'custId':_custId
//				},
//	            success : function(response) {
//	             	var ret = Ext.decode(response.responseText);
//					var instanceid = ret.instanceid;//流程实例ID
//					var currNode = ret.currNode;//当前节点
//					var nextNode = ret.nextNode;//下一步节点
//					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
//	            },
//	            failure : function(response) {
//	                 Ext.Msg.alert('提示', '操作失败!');
//	            }
//			});
//		}
//	}]
//};
//
//var editFormViewer = [{
//	fields : ['IDENT_TYPE','IDENT_NO','IDENT_CUST_NAME','IDEN_REG_DATE','IDENT_EXPIRED_DATE','IDENT_ORG','IDENT_CHECK_FLAG'],
//	fn : function(IDENT_TYPE,IDENT_NO,IDENT_CUST_NAME,IDEN_REG_DATE,IDENT_EXPIRED_DATE,IDENT_ORG,IDENT_CHECK_FLAG){
//		return [IDENT_TYPE,IDENT_NO,IDENT_CUST_NAME,IDEN_REG_DATE,IDENT_EXPIRED_DATE,IDENT_ORG,IDENT_CHECK_FLAG];
//	}
//}];
//
//var editFormCfgs = {
//	formButtons:[{
//		/**
//		 * 修改任务-保存按钮
//		 */
//		text : '提交',
//		fn : function(contentPanel, baseform){
//		    var json1 = getSelectedData().data;
//			var json2 = contentPanel.form.getValues(false);
//			var perModel = [];
//			for(var key in json2){
//				var pcbhModel = {};
//				var field = contentPanel.getForm().findField(key);
//				if(field.getXType() == 'combo'){
//					var s = field.getValue();
//					if(json1[key] != s){
//						if(json1[key]!=null && s!=""){
//							pcbhModel.custId = _custId;
//							pcbhModel.updateBeCont = getStoreFieldValue(field.store,'key',json1[key],'value');
//							pcbhModel.updateAfCont = s;
//							pcbhModel.updateAfContView = field.getRawValue();//getStoreFieldValue(field.store,'key',s,'value');
//							pcbhModel.updateItem = field.fieldLabel;
//							pcbhModel.updateItemEn = field.name;
//							pcbhModel.fieldType = '1';
//							perModel.push(pcbhModel);
//						}
//					}
//				}else{
//					if(!((json1[key]==json2[key]) || (null==json1[key]&&null==json2[key]))){
//						var pcbhModel = {};
//						pcbhModel.custId = _custId;
//						pcbhModel.updateBeCont = json1[key];
//						pcbhModel.updateAfCont = json2[key];
//						pcbhModel.updateAfContView = json2[key];
//						pcbhModel.updateItem = field.fieldLabel;
//						pcbhModel.updateItemEn = field.name;
//						pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
//						perModel.push(pcbhModel);
//						var pcbhModel = {};
//					}
//				}
//			}
//			if(perModel.length < 1){
//				Ext.Msg.alert('提示', '未作任何修改,不允许提交!');
//				return false;
//			}
//			addKeyFn(perModel,_custId,contentPanel,'IDENT_ID','证件ID');
//			addFieldFn(perModel,_custId,_custId,_custId,'CUST_ID');
//		    Ext.Msg.wait('正在提交，请稍后......','系统提示');
//			Ext.Ajax.request({
//	            url : basepath + '/acrmFCiOrgIdentifierInfo!initFlow.json',
//	            method : 'POST',
//	            params : {
//			 		'perModel': Ext.encode(perModel),
//			        'custId': _custId,
//			     	'identId' : getSelectedData().data.IDENT_ID
//				},
//	            success : function(response) {
//	             	var ret = Ext.decode(response.responseText);
//					var instanceid = ret.instanceid;//流程实例ID
//					var currNode = ret.currNode;//当前节点
//					var nextNode = ret.nextNode;//下一步节点
//					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
//	            },
//	            failure : function(response) {
//	                 Ext.Msg.alert('提示', '操作失败!');
//	            }
//	    	});
//		}
//	}]
//};

var customerView = [{
	title:'新增',
	hideTitle:true,
	type:'form',
	autoLoadSeleted : false,
	groups:[{
		columnCount : 2,
		fields:['IDENT_TYPE','IDENT_NO','IDENT_CUST_NAME','IDEN_REG_DATE','IDENT_EXPIRED_DATE','IDENT_ORG','IDENT_CHECK_FLAG','IDENT_ID'
		],
		fn:function(IDENT_TYPE,IDENT_NO,IDENT_CUST_NAME,IDEN_REG_DATE,IDENT_EXPIRED_DATE,IDENT_ORG,IDENT_CHECK_FLAG,IDENT_ID
	         ){
		return [IDENT_TYPE,IDENT_NO,IDENT_CUST_NAME,IDEN_REG_DATE,IDENT_EXPIRED_DATE,IDENT_ORG,IDENT_CHECK_FLAG,IDENT_ID];
		}
	}],
	formButtons:[{
		text:'提交',
		fn : function(formPanel,baseform){
			if (!baseform.isValid()) {
    			Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
    		 	return false;
    	    }
    	    var json2 = baseform.getValues(false);
			var perModel = [];
			for(var key in json2){
				var pcbhModel = {};
				var field = baseform.findField(key);
				if(field.getXType() == 'combo'){
					var s = field.getValue();
					if(!field.hidden && s != '' && s != undefined){
						pcbhModel.custId = _custId;
						pcbhModel.updateBeCont = '';
						pcbhModel.updateAfCont = s;
						pcbhModel.updateAfContView = field.getRawValue();//getStoreFieldValue(field.store,'key',s,'value');
						pcbhModel.updateItem = field.fieldLabel;
						pcbhModel.updateItemEn = field.name;
						pcbhModel.fieldType = '1';
						perModel.push(pcbhModel);
					}
				}else{
					var s = field.getValue();
					if(!field.hidden && s != '' && s != undefined){
						pcbhModel.custId = _custId;
						pcbhModel.updateBeCont = '';
						pcbhModel.updateAfCont = s;
						pcbhModel.updateAfContView = s;
						pcbhModel.updateItem = field.fieldLabel;
						pcbhModel.updateItemEn = field.name;
						pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
						perModel.push(pcbhModel);
					}
				}
			}
			if(perModel.length < 1){
				Ext.Msg.alert('提示', '未作任何修改,不允许提交!');
				return false;
			}
			addKeyFn(perModel,_custId,formPanel,'IDENT_ID','证件ID');
			addFieldFn(perModel,_custId,_custId,_custId,'CUST_ID');
			Ext.Msg.wait('正在提交，请稍后......','系统提示');
			Ext.Ajax.request({
	        	url : basepath + '/acrmFCiOrgIdentifierInfo!save.json',
	            method : 'POST',
	            params : {
			    	'perModel':Ext.encode(perModel),
			    	'custId':_custId
				},
	            success : function(response) {
	             	var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
	            },
	            failure : function(response) {
	                 Ext.Msg.alert('提示', '操作失败!');
	            }
			});
		}
	}]
},{
	title:'修改',	
	hideTitle:true,
	type:'form',
	autoLoadSeleted : true,
	groups:[{
		columnCount : 2,
		fields:['IDENT_TYPE','IDENT_NO','IDENT_CUST_NAME','IDEN_REG_DATE','IDENT_EXPIRED_DATE','IDENT_ORG','IDENT_CHECK_FLAG','IDENT_ID'
		],
		fn:function(IDENT_TYPE,IDENT_NO,IDENT_CUST_NAME,IDEN_REG_DATE,IDENT_EXPIRED_DATE,IDENT_ORG,IDENT_CHECK_FLAG,IDENT_ID
	         ){
		return [IDENT_TYPE,IDENT_NO,IDENT_CUST_NAME,IDEN_REG_DATE,IDENT_EXPIRED_DATE,IDENT_ORG,IDENT_CHECK_FLAG,IDENT_ID];
		}
	}],
	formButtons:[{
		text:'提交',
		fn : function(formPanel,baseform){
			if (!baseform.isValid()) {
    			Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
    		 	return false;
    	    }
			var json1 = getSelectedData().data;
			var json2 = this.contentPanel.form.getValues(false);
			var perModel = [];
			for(var key in json2){
				var pcbhModel = {};
				var field = this.contentPanel.getForm().findField(key);
				if(field.getXType() == 'combo'){
					var s = field.getValue();
					if(json1[key] != s){
						if(json1[key]!=null && s!=""){
							pcbhModel.custId = _custId;
							pcbhModel.updateBeCont = getStoreFieldValue(field.store,'key',json1[key],'value');
							pcbhModel.updateAfCont = s;
							pcbhModel.updateAfContView = field.getRawValue();//getStoreFieldValue(field.store,'key',s,'value');
							pcbhModel.updateItem = field.fieldLabel;
							pcbhModel.updateItemEn = field.name;
							pcbhModel.fieldType = '1';
							perModel.push(pcbhModel);
						}
					}
				}else{
					if(!((json1[key]==json2[key]) || (null==json1[key]&&null==json2[key]))){
						var pcbhModel = {};
						pcbhModel.custId = _custId;
						pcbhModel.updateBeCont = json1[key];
						pcbhModel.updateAfCont = json2[key];
						pcbhModel.updateAfContView = json2[key];
						pcbhModel.updateItem = field.fieldLabel;
						pcbhModel.updateItemEn = field.name;
						pcbhModel.fieldType = field.getXType() == 'datefield'?'2':'1';
						perModel.push(pcbhModel);
						var pcbhModel = {};
					}
				}
			}
			if(perModel.length < 1){
				Ext.Msg.alert('提示', '未作任何修改,不允许提交!');
				return false;
			}
			addKeyFn(perModel,_custId,this.contentPanel,'IDENT_ID','证件ID');
			addFieldFn(perModel,_custId,_custId,_custId,'CUST_ID');
		    Ext.Msg.wait('正在提交，请稍后......','系统提示');
			Ext.Ajax.request({
	            url : basepath + '/acrmFCiOrgIdentifierInfo!initFlow.json',
	            method : 'POST',
	            params : {
			 		'perModel': Ext.encode(perModel),
			        'custId': _custId,
			     	'identId' : getSelectedData().data.IDENT_ID
				},
	            success : function(response) {
	             	var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
	            },
	            failure : function(response) {
	                 Ext.Msg.alert('提示', '操作失败!');
	            }
	    	});
		}
	}]
}];

var detailFormViewer = [{
	fields : ['IDENT_ID','IDENT_TYPE','IDENT_NO','IDENT_CUST_NAME','IDEN_REG_DATE','IDENT_EXPIRED_DATE','IDENT_ORG','IDENT_CHECK_FLAG',
	          'CUST_ID','IDENT_DESC','COUNTRY_OR_REGION','IDENT_APPROVE_UNIT','IDENT_CHECKING_DATE','IDENT_CHECKED_DATE','IDENT_VALID_PERIOD',
	          'IDENT_EFFECTIVE_DATE','IDENT_VALID_FLAG','IDENT_PERIOD','IS_OPEN_ACC_IDENT','OPEN_ACC_IDENT_MODIFIED_FLAG','IDENT_MODIFIED_TIMEE',
	          'VERIFY_DATE','VERIFY_EMPLOYEE','VERIFY_RESULT','LAST_UPDATE_SYS','LAST_UPDATE_USER_NAME','LAST_UPDATE_TMM','TX_SEQ_NO'],
	fn : function(IDENT_ID,IDENT_TYPE,IDENT_NO,IDENT_CUST_NAME,IDEN_REG_DATE,IDENT_EXPIRED_DATE,IDENT_ORG,IDENT_CHECK_FLAG,
	          CUST_ID,IDENT_DESC,COUNTRY_OR_REGION,IDENT_APPROVE_UNIT,IDENT_CHECKING_DATE,IDENT_CHECKED_DATE,IDENT_VALID_PERIOD,
	          IDENT_EFFECTIVE_DATE,IDENT_VALID_FLAG,IDENT_PERIOD,IS_OPEN_ACC_IDENT,OPEN_ACC_IDENT_MODIFIED_FLAG,IDENT_MODIFIED_TIMEE,
	          VERIFY_DATE,VERIFY_EMPLOYEE,VERIFY_RESULT,LAST_UPDATE_SYS,LAST_UPDATE_USER_NAME,LAST_UPDATE_TMM,TX_SEQ_NO){
//				CUST_ID.hidden =false;
				IDENT_DESC.hidden =false;
//				IDENT_DESC.readOnly = true;
//				IDENT_DESC.cls = 'x-readOnly';
				COUNTRY_OR_REGION.hidden =false;
				IDENT_APPROVE_UNIT.hidden =false;
				IDENT_CHECKING_DATE.hidden = false;
				IDENT_CHECKED_DATE.hidden = false;
				IDENT_VALID_PERIOD.hidden = false;
				IDENT_EFFECTIVE_DATE.hidden = false;
				IDENT_VALID_FLAG.hidden =false;
				IDENT_PERIOD.hidden = false;
				IS_OPEN_ACC_IDENT.hidden = false;
				OPEN_ACC_IDENT_MODIFIED_FLAG.hidden = false;
				IDENT_MODIFIED_TIMEE.hidden = false;
			    VERIFY_DATE.hidden = false;
			    VERIFY_EMPLOYEE.hidden = false;
			    VERIFY_RESULT.hidden = false;
			    LAST_UPDATE_SYS.hidden = false;
			    LAST_UPDATE_USER_NAME.hidden = false;
			    LAST_UPDATE_TMM.hidden = false;
			    TX_SEQ_NO.hidden = false;
			    
			    IDENT_TYPE.readOnly = true;IDENT_NO.readOnly = true;IDENT_CUST_NAME.readOnly = true;IDEN_REG_DATE.readOnly = true;IDENT_EXPIRED_DATE.readOnly = true;IDENT_ORG.readOnly = true;IDENT_CHECK_FLAG.readOnly = true;
		        CUST_ID.readOnly = true;IDENT_DESC.readOnly = true;COUNTRY_OR_REGION.readOnly = true;IDENT_APPROVE_UNIT.readOnly = true;IDENT_CHECKING_DATE.readOnly = true;IDENT_CHECKED_DATE.readOnly = true;IDENT_VALID_PERIOD.readOnly = true;
		        IDENT_EFFECTIVE_DATE.readOnly = true;IDENT_VALID_FLAG.readOnly = true;IDENT_PERIOD.readOnly = true;IS_OPEN_ACC_IDENT.readOnly = true;OPEN_ACC_IDENT_MODIFIED_FLAG.readOnly = true;IDENT_MODIFIED_TIMEE.readOnly = true;
		        VERIFY_DATE.readOnly = true;VERIFY_EMPLOYEE.readOnly = true;VERIFY_RESULT.readOnly = true;LAST_UPDATE_SYS.readOnly = true;LAST_UPDATE_USER_NAME.readOnly = true;LAST_UPDATE_TMM.readOnly = true;TX_SEQ_NO.readOnly = true;
		          
		        IDENT_TYPE.cls = 'x-readOnly';IDENT_NO.cls = 'x-readOnly';IDENT_CUST_NAME.cls = 'x-readOnly';IDEN_REG_DATE.cls = 'x-readOnly';IDENT_EXPIRED_DATE.cls = 'x-readOnly';IDENT_ORG.cls = 'x-readOnly';IDENT_CHECK_FLAG.cls = 'x-readOnly';
		        CUST_ID.cls = 'x-readOnly';IDENT_DESC.cls = 'x-readOnly';COUNTRY_OR_REGION.cls = 'x-readOnly';IDENT_APPROVE_UNIT.cls = 'x-readOnly';IDENT_CHECKING_DATE.cls = 'x-readOnly';IDENT_CHECKED_DATE.cls = 'x-readOnly';IDENT_VALID_PERIOD.cls = 'x-readOnly';
		        IDENT_EFFECTIVE_DATE.cls = 'x-readOnly';IDENT_VALID_FLAG.cls = 'x-readOnly';IDENT_PERIOD.cls = 'x-readOnly';IS_OPEN_ACC_IDENT.cls = 'x-readOnly';OPEN_ACC_IDENT_MODIFIED_FLAG.cls = 'x-readOnly';IDENT_MODIFIED_TIMEE.cls = 'x-readOnly';
		        VERIFY_DATE.cls = 'x-readOnly';VERIFY_EMPLOYEE.cls = 'x-readOnly';VERIFY_RESULT.cls = 'x-readOnly';LAST_UPDATE_SYS.cls = 'x-readOnly';LAST_UPDATE_USER_NAME.cls = 'x-readOnly';LAST_UPDATE_TMM.cls = 'x-readOnly';TX_SEQ_NO.cls = 'x-readOnly';
		        
		return [IDENT_ID,IDENT_TYPE,IDENT_NO,IDENT_CUST_NAME,IDEN_REG_DATE,IDENT_EXPIRED_DATE,IDENT_ORG,IDENT_CHECK_FLAG,
		          CUST_ID,IDENT_DESC,COUNTRY_OR_REGION,IDENT_APPROVE_UNIT,IDENT_CHECKING_DATE,IDENT_CHECKED_DATE,IDENT_VALID_PERIOD,
		          IDENT_EFFECTIVE_DATE,IDENT_VALID_FLAG,IDENT_PERIOD,IS_OPEN_ACC_IDENT,OPEN_ACC_IDENT_MODIFIED_FLAG,IDENT_MODIFIED_TIMEE,
		          VERIFY_DATE,VERIFY_EMPLOYEE,VERIFY_RESULT,LAST_UPDATE_SYS,LAST_UPDATE_USER_NAME,LAST_UPDATE_TMM,TX_SEQ_NO];
	}
}];

var tbar = [{
	text : '删除',
	hidden:true,//JsContext.checkGrant('papersCust_delete'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			var cretTypeStore = findLookupByType('COM_CRET_TYPE');
			var perModel = [];
			var selectRecords = getAllSelects();
			for(var i=0;i<selectRecords.length;i++){
				var pcbhModel = {};
				pcbhModel.custId = _custId;
				pcbhModel.updateBeCont = '证件ID：'+selectRecords[i].data.IDENT_ID+",证件类型："+getStoreFieldValue(cretTypeStore,'key',selectRecords[i].data.IDENT_TYPE,'value')+",证件号码： "+selectRecords[i].data.IDENT_NO;
				pcbhModel.updateAfCont = selectRecords[i].data.IDENT_ID+","+selectRecords[i].data.IDENT_TYPE+","+selectRecords[i].data.IDENT_NO;
				pcbhModel.updateAfContView = ''
				pcbhModel.updateItem = '证件删除';
				pcbhModel.updateItemEn = 'IDENT_ID';
				pcbhModel.fieldType = '1';
				perModel.push(pcbhModel);
			}
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return false;
				} 
				Ext.Msg.wait('正在提交数据，请稍等...','提示');
				Ext.Ajax.request({
					url : basepath + '/acrmFCiOrgIdentifierInfo!delete.json',
					method : 'POST',
					params : {
						'perModel': Ext.encode(perModel),
			        	'custId': _custId
					},
					success : function(response) {
						var ret = Ext.decode(response.responseText);
						var instanceid = ret.instanceid;//流程实例ID
						var currNode = ret.currNode;//当前节点
						var nextNode = ret.nextNode;//下一步节点
						selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
						reloadCurrentData();
						hideCurrentView();
					}
				});
			});
		}
	}
},new Com.yucheng.crm.common.NewExpButton({
    formPanel : 'searchCondition',
    hidden:JsContext.checkGrant('papersCust_export'),
    url : basepath+'/acrmFCiOrgIdentifierInfo.json?custId='+custId
})];


/**
 * 
 * helin
 * 添加隐藏主键字段
 * @param {} perModel 要添加进的修改列表
 * @param {} _tempCustId 客户号
 * @param {} formpanel form面板
 * @param {} key 字段
 * @param {} fieldLabel 字段label
 */
var addKeyFn = function(perModel,_tempCustId,formpanel,key,fieldLabel){
	var field = formpanel.getForm().findField(key);
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = field.getValue();
	pcbhModel.updateAfCont = field.getValue();
	pcbhModel.updateAfContView = field.getValue();
	pcbhModel.updateItem = fieldLabel;
	pcbhModel.updateItemEn = key;
	pcbhModel.fieldType = '1';
	pcbhModel.updateTableId = '1';
	perModel.push(pcbhModel);
};
/**
 * helin
 * 添加隐藏字段
 * @param {} perModel 要添加进的修改列表
 * @param {} _tempCustId 客户号
 * @param {} beforeValue 修改前值
 * @param {} afterValue 修改后值
 * @param {} key 字段
 * @param {} fieldLabel 字段label
 * @param {} updateTableId 是否主键字段:1是，''否
 * @param {} fieldType 字段类型:1文本框，'2'日期框
 */
var addFieldFn = function(perModel,_tempCustId,beforeValue,afterValue,key,updateTableId,fieldType){
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = beforeValue;
	pcbhModel.updateAfCont = afterValue;
	pcbhModel.updateAfContView = afterValue;
	pcbhModel.updateItem = '';
	pcbhModel.updateItemEn = key;
	pcbhModel.fieldType = fieldType == "2"?"2":"1";
	pcbhModel.updateTableId = updateTableId == "1"?"1":"";
	perModel.push(pcbhModel);
};
/**
 * helin
 * @param store 要遍历的store
 * @param keyField 要比较的字段
 * @param keyValue 要比较的字段的值
 * @param valueField 要获取值的字段
 */
var getStoreFieldValue = function(store,keyField,keyValue,valueField){
	for(var i=0;i<store.getCount();i++){
		if(store.getAt(i).data[keyField] == keyValue){
			return store.getAt(i).data[valueField];
		}
	}
	return keyValue;
};

//var setQueryA = function(st){//st--false
//	getEditView().contentPanel.getForm().findField('IDENT_ORG').setDisabled(!st);
//	getEditView().contentPanel.getForm().findField('IDENT_CHECK_FLAG').setDisabled(!st);
//	getEditView().contentPanel.getForm().findField('IDEN_REG_DATE').setDisabled(!st);
//	getEditView().contentPanel.getForm().findField('IDENT_EXPIRED_DATE').setDisabled(!st);
//	getCreateView().contentPanel.getForm().findField('IDENT_ORG').setDisabled(!st);
//	getCreateView().contentPanel.getForm().findField('IDENT_CHECK_FLAG').setDisabled(!st);
//	getCreateView().contentPanel.getForm().findField('IDEN_REG_DATE').setDisabled(!st);
//	getCreateView().contentPanel.getForm().findField('IDENT_EXPIRED_DATE').setDisabled(!st);
//};

/**修改和详情面板滑入之前判断是否选择了数据**/
var beforeviewshow = function(view){
	if(view == getEditView()||view == getDetailView()){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}	
	}
	if(view._defaultTitle=='修改'){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}	
	}
//	if(view == getEditView()||view == getCreateView()){
//		var stp = JsContext.checkGrant('publicPaperInfo_mtain_OP');
//		var sta = JsContext.checkGrant('publicPaperInfo_mtain_AOnisei');
//		if(sta == true && stp == false){
//			setQueryA(stp);
//		}
//	}
};

//var viewshow = function(){
//	var sta = JsContext.checkGrant('p1111');
//	alert(sta);
//}