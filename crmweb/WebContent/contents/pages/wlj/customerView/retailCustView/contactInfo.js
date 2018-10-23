/**
 *@description 360客户视图 零售客户联系信息
 *@author:xiebz
 *@since:2014-07-19
 *@checkedby:
*/
imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);
Ext.QuickTips.init();

var createView = !JsContext.checkGrant('contactInfo_create');
var editView = !JsContext.checkGrant('contactInfo_modify');
var detailView = !JsContext.checkGrant('contactInfo_detail');

var lookupTypes = [
	'XD000332',//是否标志 
	'XD000193'//联系信息类型
];

var custId =_custId;
var url = basepath+'/acrmFCiContmethInfo.json?custId='+custId+'&check=1';

var fields = [
	{name: 'CONTMETH_ID',hidden : true},
	{name: 'CUST_ID',hidden : true},
	{name: 'CONTMETH_TYPE', text : '联系方式类型',searchField: true,translateType:'XD000193',allowBlank:false,
    	listeners:{
    		'select':function(combo,record){
    			getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').vtype = '';
    			var v = this.getValue();
    			v = v.substring(0,1);
    			if(v=='1' || v=='2'){
    				getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').setVisible(true);
    				getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').vtype = 'telephone';
    				getCurrentView().contentPanel.getForm().findField('CONTMETH_INFON').setVisible(true);
    			}else if(v=='5'){
    				getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').setVisible(true);
    				getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').vtype = 'email';
    				getCurrentView().contentPanel.getForm().findField('CONTMETH_INFON').setVisible(false);
    			}else{
    				getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').setVisible(true);
    				getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX');
    				getCurrentView().contentPanel.getForm().findField('CONTMETH_INFON').setVisible(false);
    			}
			}
    	}
	}, 
    {name: 'CONTMETH_INFOO', text : '联系方式'},
    {name: 'CONTMETH_INFO', text : '联系方式内容',gridField:false,hidden:true},
    {name: 'CONTMETH_INFOX', text : '联系方式内容',allowBlank:false,gridField:false,
    	listeners:{
			blur : function(){
				var v = getCurrentView().contentPanel.getForm().findField('CONTMETH_TYPE').getValue();
				v = v.substring(0,1);
				if(v=='1' || v=='2'){
					if(getCurrentView()._defaultTitle == '新增'){
						var result1 = getCurrentView().contentPanel.getForm().findField('CONTMETH_INFON').getValue();
						var syuuseiB1 = result1+"/"+getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').getValue();
						getCurrentView().contentPanel.getForm().findField('CONTMETH_INFO').setValue(syuuseiB1);
					}
					
					if(getCurrentView()._defaultTitle == '修改'){
						var t = getCurrentView().contentPanel.getForm().findField('CONTMETH_TYPE').getValue();
						if(t=='500' || t=='804'){
							var syuuseiB1 = getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').getValue();
							getCurrentView().contentPanel.getForm().findField('CONTMETH_INFO').setValue(syuuseiB1);
						}else{
							var result2 = getCurrentView().contentPanel.getForm().findField('CONTMETH_INFON').getValue();
							var syuuseiB2 = result2+"/"+getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').getValue();
							getCurrentView().contentPanel.getForm().findField('CONTMETH_INFO').setValue(syuuseiB2);
						}
					}
					
					if(getCurrentView()._defaultTitle == '详情'){
						var result3 = getCurrentView().contentPanel.getForm().findField('CONTMETH_INFON').getValue();
						var syuuseiB3 = result3+"/"+getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').getValue();
						getCurrentView().contentPanel.getForm().findField('CONTMETH_INFO').setValue(syuuseiB3);
					}
				}else{
					if(getCurrentView()._defaultTitle == '新增'){
						var syuuseiB1 = getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').getValue();
						getCurrentView().contentPanel.getForm().findField('CONTMETH_INFO').setValue(syuuseiB1)
					}
					if(getCurrentView()._defaultTitle == '修改'){
						var syuuseiB1 = getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').getValue();
						getCurrentView().contentPanel.getForm().findField('CONTMETH_INFO').setValue(syuuseiB1)
					}
					if(getCurrentView()._defaultTitle == '详情'){
						var syuuseiB1 = getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').getValue();
						getCurrentView().contentPanel.getForm().findField('CONTMETH_INFO').setValue(syuuseiB1)
					}
				}
			}
		}
    },
    {name: 'CONTMETH_INFON', text : '国家地区区号',gridField:false,
    	listeners:{
			blur : function(){
				var reg = /^[0\+]\d{2,3}$/;
				if(getCurrentView()._defaultTitle == '新增'){
					var result = getCurrentView().contentPanel.getForm().findField('CONTMETH_INFON').getValue();
					if(!reg.test(result)){
						Ext.Msg.alert('提示','请输入正确格式的区号！');
						getCurrentView().contentPanel.getForm().findField('CONTMETH_INFON').setValue("")
						return false;
					}
					var syuuseiA1 = result+"/"+getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').getValue();
					getCurrentView().contentPanel.getForm().findField('CONTMETH_INFO').setValue(syuuseiA1);
				}
				if(getCurrentView()._defaultTitle == '修改'){
					var result1 = getCurrentView().contentPanel.getForm().findField('CONTMETH_INFON').getValue();
					if(!reg.test(result1)){
						Ext.Msg.alert('提示','请输入正确格式的区号！');
						getCurrentView().contentPanel.getForm().findField('CONTMETH_INFON').setValue("")
						return false;
					}
					var syuuseiA2 = result1+"/"+getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').getValue();
					getCurrentView().contentPanel.getForm().findField('CONTMETH_INFO').setValue(syuuseiA2);
				}
				if(getCurrentView()._defaultTitle == '详情'){
					var result2 = getCurrentView().contentPanel.getForm().findField('CONTMETH_INFON').getValue();
					if(!reg.test(result2)){
						Ext.Msg.alert('提示','请输入正确格式的区号！');
						getCurrentView().contentPanel.getForm().findField('CONTMETH_INFON').setValue("")
						return false;
					}
					var syuuseiA3 = result2+"/"+getCurrentView().contentPanel.getForm().findField('CONTMETH_INFOX').getValue();
					getCurrentView().contentPanel.getForm().findField('CONTMETH_INFO').setValue(syuuseiA3);
				}
			}
		}
    },
    {name: 'IS_PRIORI', text : '是否首选', resutlWidth:60,searchField: true,translateType:'XD000332',allowBlank:false}, 
    {name: 'CONTMETH_SEQ',text:'联系顺序号',gridField:false},  
    {name: 'REMARK', text : '备注',xtype:'textarea',resutlWidth:200},
    {name: 'STAT', text : '记录状态',gridField:false},
    {name: 'LAST_UPDATE_SYS', text : '来源系统'},
    {name: 'LAST_UPDATE_USER', text : '最后更新人',gridField:false},
    {name: 'LAST_UPDATE_TMM', text:'最后更新时间',gridField:false},
    {name: 'TX_SEQ_NO',  text : '交易流水号',gridField:false}
];

var tbar = [{
	text : '删除',
	hidden:true,//JsContext.checkGrant('contactInfo_delete'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			var selectRecords = getAllSelects();
			var messageId = '';
			for(var i=0;i<selectRecords.length;i++){
				if(i == 0){
					messageId = "'"+selectRecords[i].data.CONTMETH_ID+"'";
				}else{
					messageId +=",'"+ selectRecords[i].data.CONTMETH_ID+"'";
				}
			}
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/acrmFCiContmethInfo!batchDestroy.json?messageId='+messageId,                                
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
},{
	text : '删除审批',
	hidden:true,//JsContext.checkGrant('contactInfo_delete'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			var selectRecords = getAllSelects();
			var messageId = '';
			for(var i=0;i<selectRecords.length;i++){
				if(i == 0){
					messageId = "'"+selectRecords[i].data.CONTMETH_ID+"'";
				}else{
					messageId +=",'"+ selectRecords[i].data.CONTMETH_ID+"'";
				}
			}
            Ext.Ajax.request({
				url : basepath + '/acrmFCiContmethInfo!initFlowDI.json',
				method : 'GET',
				params : {
					'custId' : _custId,
					'messageId':messageId
				},
				waitMsg : '正在提交申请,请等待...',
				success : function(response) {
					var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
					reloadCurrentData();
//					hideCurrentView();
				}
			});
		}
	}
},{
	text:'设为首选',
	hidden:true,
	//hidden:JsContext.checkGrant('contactInfo_priori'),
	handler:function(){
		if(getSelectedData() == false || getAllSelects().length > 1){
			Ext.Msg.alert('提示','请选择一条数据!');
			return false;
		}else{
			var b = getSelectedData().data.IS_PRIORI;
			var a = getSelectedData().data.CONTMETH_TYPE;
			//a = a.substring(0,1);
			if(b == 'Y'){
				Ext.Msg.alert('提示','已为首选项!');
				return false;
			}
			if(a != '100' && a != '202'){
				Ext.Msg.alert('提示','只手机和固定电话可以设置为首选!');
				return false;
			}
			var messageId = getSelectedData().data.CONTMETH_ID;
            Ext.Ajax.request({
               url: basepath+'/acrmFCiContmethInfo!setPreference.json?messageId='+messageId,                                
               success : function(){
                   Ext.Msg.alert('提示', '设置成功');
                   reloadCurrentData();
               },
               failure : function(){
                   Ext.Msg.alert('提示', '设置失败');
                   reloadCurrentData();
               }
           
           }); 			
		}
	}
},new Com.yucheng.crm.common.NewExpButton({
    formPanel : 'searchCondition',
    hidden:JsContext.checkGrant('contactInfo_export'),
    url : basepath+'/acrmFCiContmethInfo.json?custId='+custId+'&check=1'
})];

//自定义面板		
var customerView = [{
	title:'新增',
	hideTitle:true,
	type: 'form',
	groups:[{
	fields : ['CONTMETH_ID','CUST_ID','CONTMETH_TYPE','CONTMETH_INFO','CONTMETH_INFON','CONTMETH_INFOX'],
		fn : function(CONTMETH_ID,CUST_ID,CONTMETH_TYPE,CONTMETH_INFO,CONTMETH_INFON,CONTMETH_INFOX){
			CONTMETH_INFO.hidden = true;
			return [CONTMETH_ID,CUST_ID,CONTMETH_TYPE,CONTMETH_INFOX,CONTMETH_INFON,CONTMETH_INFO];
		}
	},{
		columnCount : 0.95,
		fields:['REMARK'],
		fn :function(REMARK){
			return [REMARK];
		}
	}],
	formButtons:[{
		text: '提交审批',
		fn: function(formPanel, baseform){
			if (!baseform.isValid()) {
    		 	Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
    		 	return false;
    	    }
  	    	json2 = formPanel.form.getFieldValues();
  	    	Ext.Msg.wait('正在提交，请稍后......','系统提示');
  	    	var perModel = [];//perModel是一个数组/集合
			for(var key in json2){
				var pcbhModel = {};
				var field = formPanel.getForm().findField(key);
				if(field.getXType() == 'combo'){
					var s = field.getValue();
					if(s!=""){
						pcbhModel.custId = _custId;//自定义属性并赋值之..
						pcbhModel.updateBeCont = '';
						pcbhModel.updateAfCont = s;//修改后的：...json2/S
						pcbhModel.updateAfContView = field.getRawValue();
						pcbhModel.updateItem = field.text;//修改项
						pcbhModel.updateItemEn = field.name;
						pcbhModel.fieldType = '1';
						perModel.push(pcbhModel);
					}
				}else{
					if(!(field.name=='CONTMETH_TYPE'|| field.name=='REMARK'|| field.name=='CONTMETH_INFO')){
						continue;
					}
					var s = field.getValue();
					if(s!=""){
						pcbhModel.custId = _custId;
						pcbhModel.updateBeCont = '';
						pcbhModel.updateAfCont = json2[key];//等价于s...一样的..
						pcbhModel.updateAfContView = json2[key];
						pcbhModel.updateItem = field.text;
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
  	    	addKeyFn(perModel,_custId,formPanel,'CONTMETH_ID','联系信息ID');
  	    	addFieldFn(perModel,_custId,_custId,_custId,'CONTMETH_CUST_ID','');
  	    	addFieldFn(perModel,_custId,'1','1','STAT');
  	    	addFieldFn(perModel,_custId,'','CRM','CONTMETH_LAST_UPDATE_SYS','');
  	    	addFieldFn(perModel,_custId,'',JsContext._userId,'CONTMETH_LAST_UPDATE_USER','');
			addFieldFn(perModel,_custId,'',new Date().format('Y-m-d'),'CONTMETH_LAST_UPDATE_TM','','2');
			
  	    	Ext.Msg.wait('正在提交，请稍后......','系统提示');
  	    	Ext.Ajax.request({
				url : basepath + '/acrmFCiContmethInfo!initFlowZI.json',
				method : 'GET',
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
				failure : function() {
					Ext.Msg.alert('提示', '操作失败');
					reloadCurrentData();
				}
			});
		}
	},{
		text : '关闭',
		fn : function(formPanel){
			 hideCurrentView();
		}
	}]
},{
	title:'修改',
	hideTitle:true,
	type: 'form',
	autoLoadSeleted : true,
	groups:[{
	fields : ['CONTMETH_ID','CUST_ID','CONTMETH_TYPE','CONTMETH_INFO','CONTMETH_INFON','CONTMETH_INFOX','LAST_UPDATE_SYS'],
		fn : function(CONTMETH_ID,CUST_ID,CONTMETH_TYPE,CONTMETH_INFO,CONTMETH_INFON,CONTMETH_INFOX,LAST_UPDATE_SYS){
			CONTMETH_TYPE.readOnly = true;
			CONTMETH_TYPE.cls = 'x-readOnly';
			LAST_UPDATE_SYS.hidden = true;
			CONTMETH_INFO.hidden = true;
			return [CONTMETH_ID,CUST_ID,CONTMETH_TYPE,CONTMETH_INFO,CONTMETH_INFON,CONTMETH_INFOX,LAST_UPDATE_SYS];
		}
	},{
		columnCount : 0.95,
		fields:['REMARK'],
		fn :function(REMARK){
			return [REMARK];
		}
	}],
	formButtons:[{
		text: '提交审批',
		fn: function(formPanel, baseform){
			if (!baseform.isValid()) {
    		 	Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
    		 	return false;
    	    }
  	    	json1 = getSelectedData().data;
  	    	json2 = formPanel.form.getFieldValues();
			
  	    	Ext.Msg.wait('正在提交，请稍后......','系统提示');
  	    	var perModel = [];//perModel是一个数组/集合
  	    	var contMethId = json1['CONTMETH_ID']
  	    	for(var key in json2){
				var pcbhModel = {};
				var field = formPanel.getForm().findField(key);
				if(field.getXType() == 'combo'){
					var s = field.getValue();
					if(json1[key] != s){
						if(json1[key]!=null && s!=""){
							pcbhModel.custId = _custId;//自定义属性并赋值之..
							pcbhModel.updateBeCont = json1[key];//未修改时的：...json1
							pcbhModel.updateAfCont = s;//修改后的：...json2/S
							pcbhModel.updateAfContView = field.getRawValue();
							pcbhModel.updateItem = field.text;//修改项
							pcbhModel.updateItemEn = field.name;
							pcbhModel.fieldType = '1';
							perModel.push(pcbhModel);
						}
					}
				}else{
					if(!(field.name=='CONTMETH_TYPE'|| field.name=='REMARK'|| field.name=='CONTMETH_INFO')){
						continue;
					}
					if(!((json1[key]==json2[key]) || (null==json1[key]&&null==json2[key]))){
						var pcbhModel = {};
						pcbhModel.custId = _custId;
						pcbhModel.updateBeCont = json1[key];
						pcbhModel.updateAfCont = json2[key];//等价于s...一样的..
						pcbhModel.updateAfContView = json2[key];
						pcbhModel.updateItem = field.text;
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
  	    	addKeyFn(perModel,_custId,formPanel,'CONTMETH_ID','联系信息ID');
  	    	addFieldFn(perModel,_custId,_custId,_custId,'CONTMETH_CUST_ID','');
  	     	addFieldFn(perModel,_custId,'',JsContext._userId,'CONTMETH_LAST_UPDATE_USER','');
			addFieldFn(perModel,_custId,'',new Date().format('Y-m-d'),'CONTMETH_LAST_UPDATE_TM','','2');
  	    	
  	    	Ext.Msg.wait('正在提交，请稍后......','系统提示');
  	    	Ext.Ajax.request({
				url : basepath + '/acrmFCiContmethInfo!initFlowCI.json',
				method : 'GET',
				params : {
					'perModel':Ext.encode(perModel),
					'custId':_custId,
					'contMethId':contMethId
				},
				success : function(response) {
					var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
				},
				failure : function() {
					Ext.Msg.alert('提示', '操作失败');
					reloadCurrentData();
				}
			});
		}
	},{
		text : '关闭',
		fn : function(formPanel){
			 hideCurrentView();
		}
	}]
},{
	title:'详情',
	type: 'form',
	autoLoadSeleted : true,
	groups:[{
		fields : ['CONTMETH_ID','CUST_ID','CONTMETH_TYPE','CONTMETH_INFO','CONTMETH_INFOX','LAST_UPDATE_SYS','LAST_UPDATE_USER','LAST_UPDATE_TMM','TX_SEQ_NO'],
		fn : function(CONTMETH_ID,CUST_ID,CONTMETH_INFOX,CONTMETH_TYPE,CONTMETH_INFO,LAST_UPDATE_SYS,LAST_UPDATE_USER,LAST_UPDATE_TMM,TX_SEQ_NO){
			CONTMETH_TYPE.readOnly = true;
			CONTMETH_TYPE.cls = 'x-readOnly';
			CONTMETH_INFO.readOnly = true;
			CONTMETH_INFO.cls = 'x-readOnly';
			CONTMETH_INFOX.readOnly = true;
			CONTMETH_INFOX.cls = 'x-readOnly';
			LAST_UPDATE_SYS.readOnly = true;
			LAST_UPDATE_SYS.cls = 'x-readOnly';
			LAST_UPDATE_USER.readOnly = true;
			LAST_UPDATE_USER.cls = 'x-readOnly';
			LAST_UPDATE_TMM.readOnly = true;
			LAST_UPDATE_TMM.cls = 'x-readOnly';
			TX_SEQ_NO.readOnly = true;
			TX_SEQ_NO.cls = 'x-readOnly';
			CONTMETH_INFO.hidden = true;
			return [CONTMETH_ID,CUST_ID,CONTMETH_TYPE,CONTMETH_INFO,CONTMETH_INFOX,LAST_UPDATE_SYS,TX_SEQ_NO,LAST_UPDATE_USER,LAST_UPDATE_TMM];
		}
	},{
		columnCount : 0.95,
		fields:['REMARK'],
		fn :function(REMARK){
			REMARK.readOnly = true;
			REMARK.cls = 'x-readOnly';
			return [REMARK];
		}
	}]
},{
	title : '查看历史',
	hideTitle:true,
	type : 'grid',
	//hideTitle:JsContext.checkGrant('contactInfo_history'),
	url  : basepath+'/acrmFCiContmethInfo.json',
	isCsm:false,
	frame : true,
	fields: {
		fields : [
			{name: 'CONTMETH_ID',hidden : true},
  		    {name: 'CUST_ID',hidden : true},
  		    {name: 'CONTMETH_TYPE', text : '联系方式类型',translateType:'CONTMETH_TYPES',renderer:function(value){
				var val = translateLookupByKey("CONTMETH_TYPES",value);
				return val?val:"";
			}},
  		    {name: 'CONTMETH_INFO', text : '联系方式内容'},                                   
  		    {name: 'IS_PRIORI', text : '是否首选',translateType:'IF_FLAG',renderer:function(value){
				var val = translateLookupByKey("IF_FLAG",value);
				return val?val:"";
			}}, 
  		    {name: 'REMARK', text : '备注'},
  		    {name: 'LAST_UPDATE_SYS', text : '最后更新系统'},
  		    {name: 'LAST_UPDATE_USER', text : '最后更新人'},
  		    {name: 'LAST_UPDATE_TMM', text:'最后更新时间'},
  		    {name: 'TX_SEQ_NO',  text : '交易流水号'}
		]
	}
}];

/**修改和详情面板滑入之前判断是否选择了数据，如果以选择，加载查询附件列表信息**/
var beforeviewshow = function(view){
	if(view._defaultTitle == '修改' || view._defaultTitle == '新增'){
		view.contentPanel.getForm().findField('CONTMETH_INFON').setVisible(false);
	}
	if(view._defaultTitle == '修改' || view._defaultTitle == '详情'){
		var baseform = view.contentPanel.getForm();
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		if(view._defaultTitle == '修改'){
			var c = getSelectedData().data.CONTMETH_TYPE;
			c = c.substring(0,1);
			if(c == '1' || c == '2'){
				baseform.findField('CONTMETH_INFOX').vtype = 'telephone';
			}
			if(c == '5'){
				baseform.findField('CONTMETH_INFOX').vtype = 'email';
			}
		}
	}
	if(view._defaultTitle == "查看历史"){
		view.setParameters ({
			check : '0',
			custId:custId
		}); 		
	}			
};

var viewshow = function(view){
	if( view._defaultTitle == '修改' || view._defaultTitle == '详情'){
		var baseform = view.contentPanel.getForm();
		if(view._defaultTitle == '修改'){
			var c = getSelectedData().data.CONTMETH_TYPE;
			c = c.substring(0,1);
			if(c == '1' || c == '2'){
				var moto = baseform.findField('CONTMETH_INFO').getValue();
				if(moto.indexOf("/",0)>0){
					baseform.findField('CONTMETH_INFOX').setVisible(true);
					baseform.findField('CONTMETH_INFON').setVisible(true);
					var Arr = moto.split("/");
					baseform.findField('CONTMETH_INFON').setValue(Arr[0]);
					baseform.findField('CONTMETH_INFOX').setValue(Arr[1]);
				}else{
					baseform.findField('CONTMETH_INFOX').setVisible(true);
					baseform.findField('CONTMETH_INFON').setVisible(true);
					baseform.findField('CONTMETH_INFOX').setValue(moto);
				}
			}
			else if(c == '5'){
				baseform.findField('CONTMETH_INFON').setVisible(false);
				baseform.findField('CONTMETH_INFOX').setVisible(true);
				var moto = baseform.findField('CONTMETH_INFO').getValue();
				baseform.findField('CONTMETH_INFOX').setValue(moto);
			}
			else{
				baseform.findField('CONTMETH_INFON').setVisible(false);
				baseform.findField('CONTMETH_INFOX').setVisible(true);
				var moto = baseform.findField('CONTMETH_INFO').getValue();
				baseform.findField('CONTMETH_INFOX').setValue(moto);
			}
		}
		if(view._defaultTitle == '详情'){
			var c = getSelectedData().data.CONTMETH_TYPE;
			var moto = baseform.findField('CONTMETH_INFO').getValue();
			c = c.substring(0,1);
			if(c == '1' || c == '2'){
				var Arr = moto.split("/");
				if(Arr.length>1){
					var fns = Arr[0]+"-"+Arr[1];
				}else{
					var fns = Arr[0];
				}
				baseform.findField('CONTMETH_INFOX').setValue(fns);
			}else{
				baseform.findField('CONTMETH_INFOX').setValue(moto);
			}
		}
	}
};

/**
 * 
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