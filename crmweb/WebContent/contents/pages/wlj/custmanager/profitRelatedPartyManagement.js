/**
 * 利润关联方管理
 * @author wangmk1
 *
 */


/**
 * 	url : 
 * 		类型：string;
 * 		说明：数据查询URL地址;
 * 		必选：是;
 */					   
var url = basepath + "/profitRelatedPartyManagement.json";
imports([
			'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
				'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
	'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
	 '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',//导出
		'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField2.js'	//客户放大镜
	 ,'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldGLF.js'	//客户放大镜
      ]);
var lookupTypes=[
    'P_RELATION_TYPE',
    'STATE_TYPE',
    'CUS0100038'
];
var applyType='';
Ext.QuickTips.init();
var temp = {};
var type='';

var fields = [
              {	name:'ID',	text:'主键ID',hidden:true, resutlWidth:100},
              {	name:'CUST_ID',	text:'客户编号', resutlWidth:100},
              {	name:'CUST_NAME',	text:'客户名称',xtype: 'customerquery',hiddenName:'CUST_ID', resutlWidth:100,searchField:true,allowBlank:false},
              {	name:'CUST_NAME_R',	text:'关联客户名称',xtype: 'customerqueryGLF',hiddenName:'CUST_NO_R',searchField:true,resutlWidth:100,allowBlank:false},
              {	name:'CUST_NO_R',	text:'关联客户号',	resutlWidth:100},
              {	name:'RELATIONSHIP',	text:'关联关系',	resutlWidth:100, translateType:'CUS0100038',allowBlank:false},
              {	name:'R_DESC',	text:'关联描述',	resutlWidth:100,maxLength:50},
              {	name:'CREAT_DATE',	text:'创建日期',	resutlWidth:100},
              {	name:'R_STATE',	text:'审批状态',	resutlWidth:100,translateType:'STATE_TYPE'},
              { name:'CREATE_USER_ID',text:'创建者编号',hidden:true,resutlWidth:100},
              { name:'CREATE_USER_NAME',text:'创建者姓名',resutlWidth:100},
              { name:'CREATE_TIMES',text:'创建的时间戳',hidden:true,resutlWidth:100}
              ];


newSm = new Ext.grid.CheckboxSelectionModel();
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});
var newCm =  new Ext.grid.ColumnModel([editRrownum,newSm,	
                                   {header:'主键ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                   {header:'客户编号', dataIndex : 'CUST_ID',sortable : true,width : 120,hidden:true},
                                   {header:'客户名称', dataIndex : 'CUST_NAME',sortable : true,width : 120,hidden:true},
                                   {header:'关联客户名称',dataIndex:'CUST_NAME_R',sortable:true,width:120},
                                   {header:'关联客户编号',dataIndex:'CUST_NO_R',sortable:true,width:120,hidden:true},
                                   {header:'关联关系',dataIndex:'RELATIONSHIP_ORA',sortable:true,width:120,translateType:'CUS0100038'},
                                   {header:'关联描述',dataIndex:'R_DESC',sortable:true,width:120},
                                   {header:'创建者编号',dataIndex:'CREATE_USER_ID',sortable:true,width:120},
                                   {header:'创建者姓名',dataIndex:'CREATE_USER_NAME',sortable:true,width:120},
                                   {header:'创建时间戳',dataIndex:'CREATE_TIMES',sortable:true,width:120,hidden:true}
        	                                     ]); 
var newPanelStroe_1 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/profitRelatedShip.json' 
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'CUST_ID'},
			    {name:'CUST_NAME'},
			    {name:'CUST_NAME_R'},
			    {name:'CUST_NO_R'},
			    {name:'RELATIONSHIP_ORA'},
			    {name:'R_DESC'},
			    {name:'CREATE_USER_ID'},
			    {name:'CREATE_USER_NAME'},
			    {name:'CREATE_TIMES'}
			     ])
});	
var newPanel_1 = new Ext.grid.GridPanel({
	title : '关联客户',
	autoScroll: true,
	height:300,
    tbar : [{
    	 text : '新增',
		 handler:function() {
			saveBaseValue(getCurrentView().contentPanel.getForm());
			showCustomerViewByTitle('新增');
		}
    },{
    	text : '修改',
		handler:function(){
		var selectLength = newPanel_1.getSelectionModel().getSelections().length;	
		if (selectLength != 1) {
			Ext.Msg.alert('提示', '请选择一条记录！');
			return false;
		} 
		record = newPanel_1.getSelectionModel().getSelections()[0];	
		saveBaseValue(getCurrentView().contentPanel.getForm());
		showCustomerViewByTitle("修改关联客户");
		getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
		getCurrentView().contentPanel.getForm().findField('CUST_NO_R').setValue(record.data.CUST_NO_R);
		getCurrentView().contentPanel.getForm().findField('CUST_NAME_R').setValue(record.data.CUST_NAME_R);
		getCurrentView().contentPanel.getForm().findField('RELATIONSHIP').setValue(record.data.RELATIONSHIP_ORA);
		getCurrentView().contentPanel.getForm().findField('R_DESC').setValue(record.data.R_DESC);
		}
    },{
    	text:'删除',
    	handler :function(){
    		var selectLength = newPanel_1.getSelectionModel().getSelections().length;
    	 	var selectRecords = newPanel_1.getSelectionModel().getSelections();
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var times = '';
    		var ids='';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			if(i==0){
    			  times += "'"+selectRecord.data.CREATE_TIMES+"'";
    			  ids+=selectRecord.data.ID;
    			}else{
    			  times += ",'"+selectRecord.data.CREATE_TIMES+"'";
    			  ids+=","+selectRecord.data.ID;
    			}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
	    		if(buttonId.toLowerCase() == 'no'){
	        		return false;
	    		}
    			Ext.Ajax.request({
    				url : basepath + '/profitRelatedShip!batchDel.json',
    				method : 'GET',
    				params:{
    					'idStr':ids 
    				},
    				success : function() {
    					Ext.Msg.hide(); 
    					showCustomerViewByTitle('新增客户间关联关系');
//						setBaseValue(getCurrentView().contentPanel.getForm());
     					newPanelStroe_1.reload({params:{ID:times}});
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
  	store : newPanelStroe_1,
	frame : true,
	sm:newSm,
	cm : newCm,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

//newSm2 = new Ext.grid.CheckboxSelectionModel();
var editRrownum2 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 28
});
var newCm2 =  new Ext.grid.ColumnModel([editRrownum2,	
                                   {header:'主键ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                   {header:'客户编号', dataIndex : 'CUST_ID',sortable : true,width : 120},
                                   {header:'客户名称', dataIndex : 'CUST_NAME',sortable : true,width : 120},
                                   {header:'关联客户名称',dataIndex:'CUST_NAME_R',sortable:true,width:120},
                                   {header:'关联客户编号',dataIndex:'CUST_NO_R',sortable:true,width:120},
                                   {header:'关联关系',dataIndex:'RELATIONSHIP_ORA',sortable:true,width:120,translateType:'CUS0100038'},
                                   {header:'关联描述',dataIndex:'R_DESC',sortable:true,width:120},
                                   {header:'创建者编号',dataIndex:'CREATE_USER_ID',sortable:true,width:120},
                                   {header:'创建者姓名',dataIndex:'CREATE_USER_NAME',sortable:true,width:120},
                                   {header:'创建时间戳',dataIndex:'CREATE_TIMES',sortable:true,width:120,hidden:true}
        	                                     ]); 

var newPanelStroe_2 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/profitRelatedShips.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'CUST_ID'},
			    {name:'CUST_NAME'},
			    {name:'CUST_NAME_R'},
			    {name:'CUST_NO_R'},
			    {name:'RELATIONSHIP_ORA'},
			    {name:'R_DESC'},
			    {name:'CREATE_USER_ID'},
			    {name:'CREATE_USER_NAME'},
			    {name:'CREATE_TIMES'}
			     ])
});

var newPanel_2 = new Ext.grid.GridPanel({
	title : '关联客户',
	autoScroll: true,
	height:250,
	store : newPanelStroe_2,
	frame : true,
	//sm:newSm2,
	cm : newCm2,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
var customerView= [{
	title:'新增',
	hideTitle : true,
	type:'form',
	groups: [{
		columnCount : 1 ,
		fields : ['ID','CUST_NAME','CUST_ID','CUST_NAME_R','CUST_NO_R','RELATIONSHIP','CREAT_DATE','R_STATE','CREATE_USER_ID','CREATE_USER_NAME','CREATE_TIMES'],
		fn:function(ID,CUST_NAME,CUST_ID,CUST_NAME_R,CUST_NO_R,RELATIONSHIP,CREAT_DATE,R_STATE,CREATE_USER_ID,CREATE_USER_NAME,CREATE_TIMES){
			CREAT_DATE.hidden = true ;
			CUST_ID.hidden=true;
			CUST_NO_R.hidden=true;
			CUST_NAME.hidden=true;
			CUST_NAME.allowBlank=false;
			CUST_NAME_R.allowBlank=false;
			RELATIONSHIP.allowBlank=false;
			ID.hidden = true ;
			R_STATE.hidden = true ;
			CREATE_USER_ID.hidden = true ;
			CREATE_USER_NAME.hidden = true ;
			CREATE_TIMES.hidden=true;
			return [ID,CUST_NAME,CUST_ID,CUST_NAME_R,CUST_NO_R,RELATIONSHIP,CREAT_DATE,R_STATE,CREATE_USER_ID,CREATE_USER_NAME,CREATE_TIMES];
		}
	}, {
		columnCount : 1 ,
		fields : ['R_DESC'],
		fn:function(R_DESC){
			R_DESC.xtype='textarea';
			return [R_DESC];
		}
	}] ,
	formButtons : [{
		text:'保存',
		fn : function(formPanel,basicForm){
			  formPanel.getForm().findField('CUST_ID').setValue(temp.CUST_ID);
			  formPanel.getForm().findField('CUST_NAME').setValue(temp.CUST_NAME);
			  if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
	           var data = formPanel.getForm().getFieldValues();
				var commintData = translateDataKey(data,1);
				Ext.Msg.wait('正在处理，请稍后......','系统提示');
				Ext.Ajax.request({
						url : basepath + '/profitRelatedPartyManagement!tempsave.json?createTimes='+temp.CREATE_TIMES,
						method : 'GET',
						params : commintData,
						success : function(response) {
							var ret = Ext.decode(response.responseText);
							var times=ret.times;
							temp.CREATE_TIMES=times;
							Ext.Msg.hide(); 
							showCustomerViewByTitle('新增客户间关联关系');
							setBaseValue(getCurrentView().contentPanel.getForm());
							newPanelStroe_1.load({params:{ID:times}});		
						},
						failure:function(){
							var resultArray = Ext.util.JSON.decode(response.status);
					 		if(resultArray == 403) {
				           		Ext.Msg.alert('提示', response.responseText);
					 		}else{
								Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
			 				}
						}
				}); 
		}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('新增客户间关联关系');
			setBaseValue(getCurrentView().contentPanel.getForm());
			newPanelStroe_1.load({params:{ID:temp.CREATE_TIMES}});
		}
	}]
},{	
	
	title:'修改',
	type:'form',
	autoLoadSeleted : true,
	groups:[{
		columnCount : 1 ,
		fields : ['ID','CUST_NAME','CUST_ID','CUST_NAME_R','CUST_NO_R','RELATIONSHIP','CREAT_DATE','R_STATE','CREATE_USER_ID','CREATE_USER_NAME','CREATE_TIMES'],
		fn:function(ID,CUST_NAME,CUST_ID,CUST_NAME_R,CUST_NO_R,RELATIONSHIP,CREAT_DATE,R_STATE,CREATE_USER_ID,CREATE_USER_NAME,CREATE_TIMES){
			ID.hidden = true ;
			CREAT_DATE.hidden=true;
			CUST_NO_R.hidden=true;
			CUST_ID.hidden=true;
			CUST_NAME.allowBlank=false;
			CUST_NAME_R.cls= "x-readOnly";
			CUST_NAME.cls= "x-readOnly";
			CUST_NAME_R.allowBlank=false;
			RELATIONSHIP.allowBlank=false;
			R_STATE.hidden = true ;
			CREATE_USER_ID.hidden = true ;
			CREATE_USER_NAME.hidden = true ;
			CREATE_TIMES.hidden=true;
			return [ID,CUST_NAME,CUST_ID,CUST_NAME_R,CUST_NO_R,RELATIONSHIP,CREAT_DATE,R_STATE,CREATE_USER_ID,CREATE_USER_NAME,CREATE_TIMES];
		}
	}, {
		columnCount : 1 ,
		fields : ['R_DESC'],
		fn:function(R_DESC){
			R_DESC.xtype='textarea';
			return [R_DESC];
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_2];//
		}
	}],
	formButtons : [{
		text:'提交',
		fn : function(formPanel,basicForm){	
			 if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
	           applyType='2';
	           var data = formPanel.getForm().getFieldValues();
				var commintData = translateDataKey(data,1);
				Ext.Msg.wait('正在处理，请稍后......','系统提示');
				Ext.Ajax.request({
						url : basepath + '/profitRelatedPartyManagement!updatesaveObj.json?type='+applyType,
						method : 'GET',
						params : commintData,
						success : function(response) {
							var ret = Ext.decode(response.responseText);
							var instanceid = ret.instanceid;//流程实例ID
							var currNode = ret.currNode;//当前节点
							var nextNode = ret.nextNode;//下一步节点
							selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
						},
						failure:function(){
							Ext.Msg.alert('提示','提交审批失败!');
							reloadCurrentData();
						}
				}); 
		}
	},{
		text:'取消',
		fn : function(){
			temp.CREATE_TIMES='';
			reloadCurrentData();
		}
	}]
		
},{
	title:'新增客户间关联关系',
	type:'form',
	groups: [{
		columnCount : 1 ,
		fields : ['ID','CUST_NAME','CUST_ID','CREATE_TIMES'],
//	    fields : [
//	              {	name:'ID',	text:'主键ID',hidden:true, resutlWidth:100},
//	              {	name:'CUST_NAME',text:'客户名称',xtype: 'customerquery',hiddenName:'CUST_ID', resutlWidth:100,searchField:true,allowBlank:false},
//	              {	name:'CUST_ID',	text:'客户编号',hidden:true, resutlWidth:100},
//	              { name:'CREATE_TIMES',text:'创建的时间戳',hidden:true,resutlWidth:100},
//		          ],
		fn:function(ID,CUST_NAME,CUST_ID,CREATE_TIMES){
			ID.hidden = true ;
			CREATE_TIMES.hidden=true;
			CUST_ID.hidden=true;
			CUST_NAME.allowBlank=false;
			return [ID,CUST_NAME,CUST_ID,CREATE_TIMES];
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [newPanel_1];//
		}
	}] ,
	formButtons : [{
		text:'提交',
		fn : function(formPanel,basicForm){
			 if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
	           applyType='1';
	           var data = formPanel.getForm().getFieldValues();
				var commintData = translateDataKey(data,1);
				Ext.Msg.wait('正在处理，请稍后......','系统提示');
				Ext.Ajax.request({
						url : basepath + '/profitRelatedPartyManagement!saveObj.json',
						method : 'GET',
						params : {
							createTimes:temp.CREATE_TIMES,
							custName:temp.CUST_NAME,
							type:applyType
						},
						success : function(response) {
							var ret = Ext.decode(response.responseText);
							var instanceid = ret.instanceid;//流程实例ID
							var currNode = ret.currNode;//当前节点
							var nextNode = ret.nextNode;//下一步节点
							selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
							temp.CREATE_TIMES='';
						},
						failure:function(){
							Ext.Msg.alert('提示','提交审批失败!');
							reloadCurrentData();
						}
				}); 
		}
	},{
		text:'取消',
		fn : function(){
		   temp.CREATE_TIMES='';
		   reloadCurrentData();
		}
	}]
},{
	title:'详情',
	type:'form',
	autoLoadSeleted : true,
	groups: [{
		columnCount : 2 ,
		fields : ['ID','CUST_NAME','CUST_ID','CUST_NAME_R','CUST_NO_R','RELATIONSHIP','CREAT_DATE','R_STATE','CREATE_USER_ID','CREATE_USER_NAME','CREATE_TIMES'],
		fn:function(ID,CUST_NAME,CUST_ID,CUST_NAME_R,CUST_NO_R,RELATIONSHIP,CREAT_DATE,R_STATE,CREATE_USER_ID,CREATE_USER_NAME,CREATE_TIMES){
			ID.hidden = true ;
			CUST_NAME.disabled=true;
			CUST_NAME.cls= "x-readOnly";
			CUST_ID.disabled=true;
			CUST_ID.cls= "x-readOnly";
			CUST_NAME_R.disabled=true;
			CUST_NAME_R.cls= "x-readOnly";
			CUST_NO_R.disabled=true;
			CUST_NO_R.cls= "x-readOnly";
			RELATIONSHIP.disabled=true;
			RELATIONSHIP.cls= "x-readOnly";
			CREAT_DATE.disabled=true;
			CREAT_DATE.cls= "x-readOnly";
			R_STATE.hidden = true ;
			CREATE_USER_ID.hidden = true ;
			CREATE_USER_NAME.hidden = true ;
			CREATE_TIMES.hidden=true;
			return [ID,CUST_NAME,CUST_ID,CUST_NAME_R,CUST_NO_R,RELATIONSHIP,CREAT_DATE,R_STATE,CREATE_USER_ID,CREATE_USER_NAME,CREATE_TIMES];
		}
	}, {
		columnCount : 1 ,
		fields : ['R_DESC'],
		fn:function(R_DESC){
			R_DESC.xtype='textarea';
			R_DESC.disabled=true;
			R_DESC.cls= "x-readOnly";
			return [R_DESC];
		}
	}] 
},{

	title:'修改关联客户',
	hideTitle : true,
	type:'form',
	groups: [{
		columnCount : 1 ,
		fields : ['ID','CUST_NAME','CUST_ID','CUST_NAME_R','CUST_NO_R','RELATIONSHIP','CREAT_DATE','R_STATE','CREATE_USER_ID','CREATE_USER_NAME','CREATE_TIMES'],
		fn:function(ID,CUST_NAME,CUST_ID,CUST_NAME_R,CUST_NO_R,RELATIONSHIP,CREAT_DATE,R_STATE,CREATE_USER_ID,CREATE_USER_NAME,CREATE_TIMES){
			ID.hidden = true ;
			CREAT_DATE.hidden=true;
			CUST_NO_R.hidden=true;
			CUST_ID.hidden=true;
			CUST_NAME.hidden=true;
			CUST_NAME_R.allowBlank=false;
			RELATIONSHIP.allowBlank=false;
			R_STATE.hidden = true ;
			CUST_NAME_R.cls= "x-readOnly";
			CREATE_USER_ID.hidden = true ;
			CREATE_USER_NAME.hidden = true ;
			CREATE_TIMES.hidden=true;
			return [ID,CUST_NAME,CUST_ID,CUST_NAME_R,CUST_NO_R,RELATIONSHIP,CREAT_DATE,R_STATE,CREATE_USER_ID,CREATE_USER_NAME,CREATE_TIMES];
		}
	},{
		columnCount : 1 ,
		fields : ['R_DESC'],
		fn:function(R_DESC){
			R_DESC.xtype='textarea';
			return [R_DESC];
		}
	}] ,
	formButtons : [{
		text:'保存',
		fn : function(formPanel,basicForm){
			  formPanel.getForm().findField('CUST_ID').setValue(temp.CUST_ID);
			  formPanel.getForm().findField('CUST_NAME').setValue(temp.CUST_NAME);
			  formPanel.getForm().findField('CREATE_TIMES').setValue(temp.CREATE_TIMES);
			  if (!formPanel.getForm().isValid()) {
	               Ext.MessageBox.alert('提示','输入有误,请检查输入项');
	               return false;
	           };
	           var data = formPanel.getForm().getFieldValues();
				var commintData = translateDataKey(data,1);
				Ext.Msg.wait('正在处理，请稍后......','系统提示');
				Ext.Ajax.request({
						url : basepath + '/profitRelatedPartyManagement!updatetempsave.json',
						method : 'GET',
						params : commintData,
						success : function(response) {
							var ret = Ext.decode(response.responseText);
							var times=ret.times;
							temp.CREATE_TIMES=times;
							Ext.Msg.hide(); 
							showCustomerViewByTitle('新增客户间关联关系');
							setBaseValue(getCurrentView().contentPanel.getForm());
							newPanelStroe_1.load({params:{ID:times}});
						},
						failure:function(){
							var resultArray = Ext.util.JSON.decode(response.status);
					 		if(resultArray == 403) {
				           		Ext.Msg.alert('提示', response.responseText);
					 		}else{
								Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
			 				}
						}
				}); 
		}
	},{
		text : '取消',
		fn : function(formPanel,basicForm){
			showCustomerViewByTitle('新增客户间关联关系');
			setBaseValue(getCurrentView().contentPanel.getForm());
			newPanelStroe_1.load({params:{ID:temp.CREATE_TIMES}});
		}
	}]

},{
	title:'删除',
	type:'form',
	autoLoadSeleted : true,
	groups:[{
		columnCount : 1 ,
		fields : ['ID','CUST_NAME','CUST_ID','CUST_NAME_R','CUST_NO_R','RELATIONSHIP','CREAT_DATE','R_STATE','CREATE_USER_ID','CREATE_USER_NAME','CREATE_TIMES'],
		fn:function(ID,CUST_NAME,CUST_ID,CUST_NAME_R,CUST_NO_R,RELATIONSHIP,CREAT_DATE,R_STATE,CREATE_USER_ID,CREATE_USER_NAME,CREATE_TIMES){
			ID.hidden = true ;
			CREAT_DATE.hidden=true;
			CUST_NO_R.hidden=true;
			CUST_ID.hidden=true;
			CUST_NAME.allowBlank=false;
			CUST_NAME.cls= "x-readOnly";
			CUST_NAME_R.allowBlank=false;
			CUST_NAME_R.cls= "x-readOnly";
			RELATIONSHIP.allowBlank=false;
			RELATIONSHIP.cls= "x-readOnly";
			R_STATE.hidden = true ;
			CREATE_USER_ID.hidden = true ;
			CREATE_USER_NAME.hidden = true ;
			CREATE_TIMES.hidden =true;
			return [ID,CUST_NAME,CUST_ID,CUST_NAME_R,CUST_NO_R,RELATIONSHIP,CREAT_DATE,R_STATE,CREATE_USER_ID,CREATE_USER_NAME,CREATE_TIMES];
		}
	}, {
		columnCount : 1 ,
		fields : ['R_DESC'],
		fn:function(R_DESC){
			R_DESC.xtype='textarea';
			R_DESC.cls= "x-readOnly";
			return [R_DESC];
		}
	}],
	formButtons : [{
		text:'提交',
		fn : function(formPanel,basicForm){
			    var data = formPanel.getForm().getFieldValues();
				var commintData = translateDataKey(data,1);
	            applyType='3';
				Ext.Msg.wait('正在处理，请稍后......','系统提示');
				Ext.Ajax.request({
						url : basepath + '/profitRelatedPartyManagement!batchDestory.json?type='+applyType,
						method : 'GET',
						params : commintData,
						success : function(response) {
							var ret = Ext.decode(response.responseText);
							var instanceid = ret.instanceid;//流程实例ID
							var currNode = ret.currNode;//当前节点
							var nextNode = ret.nextNode;//下一步节点
							selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
							temp.CREATE_TIMES='';
						},
						failure:function(){
							Ext.Msg.alert('提示','提交审批失败!');
							reloadCurrentData();
						}
				}); 
		}
	},{
		text:'取消',
		fn : function(){
		   temp.CREATE_TIMES='';
		   reloadCurrentData();
		}
	}]
}];
var tbar = [
//            {
//	text:'删除',
//	hidden:JsContext.checkGrant("_profitManageDelet"),
//	autoLoadSeleted : true,
//	handler:function(){
//		if(!getSelectedData()){
//			Ext.Msg.alert('提示','请至少选择一条数据进行操作!');
//			return false;
//		}
//		if(getAllSelects().length > 1){
//			   Ext.Msg.alert('提示','只能选择一个数据!');
//				return false;
//		}
//		if(getAllSelects()){
//			var ids ='';
//			var states='';
//			var createUserId='';
//			var data=getAllSelects();
//			for(var key in data){
//				if(data[key].data!=null){
//					ids+=data[key].data.ID+',';
//					states+=data[key].data.R_STATE+',';
//					createUserId+=data[key].data.CREATE_USER_ID+',';
//				}
//			}
//			var createList= createUserId.split(',');
//			var userState='';
//			for(var i=0;i<(createList.length-1);i++){
//				    var str=createList[i];
//					if(str==__userId){
//					   userState+='1,';
//			        }else{
//			          userState+='-1,';
//			        }
//			}
//			
//			var userFlag = userState.indexOf('-1');
//			if(userFlag>(-1)){
//				Ext.Msg.alert('提示','只能删除审批本人创建的');
//				return false;
//			}
//			
//			var flag = states.indexOf('1,');
//			if(states!='' && flag>(-1)){
//				Ext.Msg.alert('提示','只能删除审批通过的');
//				return false;
//			}
//		}
//		Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
//			if(buttonId.toLowerCase()=="no"){
//				return;
//			}
//			Ext.Ajax.request({									
//				url:basepath + '/profitRelatedPartyManagement!batchDestory.json',
//				method:'post',
//				waitMsg:'正在删除数据,请等待...',//显示读盘的动画效果,执行完成后效果消失
//				params:{
//					'idStr':ids 
//				},
//				success:function (){
//					Ext.Msg.alert('提示','删除成功');
//					reloadCurrentData();
//				},
//				failure:function(){
//					Ext.Msg.alert('提示','删除失败');
//					reloadCurrentData();
//				}
//			});
//		});
//	}
//},
/**************导出*******************/
new Com.yucheng.crm.common.NewExpButton({
    formPanel : 'searchCondition',
    url : basepath+'/profitRelatedPartyManagement.json'
})

];

var beforeviewshow = function (theview){
	if(theview._defaultTitle== '修改'){
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请选择一条数据进行操作!');
			return false;
		}
	   if(getAllSelects().length > 1){
		   Ext.Msg.alert('提示','只能选择一个数据!');
			return false;
	   }
		if(getSelectedData().data.R_STATE == '1'){
			Ext.Msg.alert('提示','只能修改审批通过的！');
			return false;
		}
		if(getSelectedData().data.CREATE_USER_ID!=__userId){
			Ext.Msg.alert('提示','只能修改本人创建的！');
			return false;
		}
		var custId=getSelectedData().data.CUST_ID;
		newPanelStroe_2.reload(
				{params:{
					     'CUST_ID':custId
			            }
			     });
	}
	if(theview._defaultTitle== '新增'){
		Ext.Msg.alert('提示','与公司本身有下列关系者，可纳入利润关联关系：① 有投资关系的（直接投资、间接投资、转投资等）；② 有金钱、动产、不动产作为借贷关系人或抵/质押的关系人；③ 该公司的股东及员工');
	}
	if(theview._defaultTitle == '新增客户间关联关系'){
    	//var custName=theview.contentPanel.getForm().findField('CUST_NAME').getRawValue();//|| custId=='' || custId=='null' || custId==undefined
		var times=temp.CREATE_TIMES;
		if(times=='' || times=='null' || times==undefined ){
			times='0';
			newPanelStroe_1.reload({params:{ID:times}});
		}
	}
	if(theview._defaultTitle== '详情'){
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请选择一条数据进行操作!');
			return false;
		}
	  if(getAllSelects().length > 1){
		   Ext.Msg.alert('提示','只能选择一个数据!');
			return false;
	   }
	}
	if(theview._defaultTitle== '删除'){
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请至少选择一条数据进行操作!');
			return false;
		}
		if(getAllSelects().length > 1){
			   Ext.Msg.alert('提示','只能选择一个数据!');
				return false;
		}
		if(getAllSelects()){
			var ids ='';
			var states='';
			var createUserId='';
			var data=getAllSelects();
			for(var key in data){
				if(data[key].data!=null){
					ids+=data[key].data.ID+',';
					states+=data[key].data.R_STATE+',';
					createUserId+=data[key].data.CREATE_USER_ID+',';
				}
			}
			var createList= createUserId.split(',');
			var userState='';
			for(var i=0;i<(createList.length-1);i++){
				    var str=createList[i];
					if(str==__userId){
					   userState+='1,';
			        }else{
			          userState+='-1,';
			        }
			}
			
			var userFlag = userState.indexOf('-1');
			if(userFlag>(-1)){
				Ext.Msg.alert('提示','只能删除审批本人创建的');
				return false;
			}
			
			var flag = states.indexOf('1,');
			if(states!='' && flag>(-1)){
				Ext.Msg.alert('提示','只能删除不在审批中的');
				return false;
			}
		}
	}
};

var saveBaseValue = function(form){
	temp.ID = form.findField('ID').getValue();
	temp.CUST_ID = form.findField('CUST_ID').getValue();
	temp.CUST_NAME = form.findField('CUST_NAME').getValue();
//	temp.CUST_NAME_R = form.findField('CUST_NAME_R').getValue();
//	temp.CUST_NO_R = form.findField('CUST_NO_R').getValue();
//	temp.RELATIONSHIP = form.findField('RELATIONSHIP').getValue();
//	temp.R_DESC = form.findField('R_DESC').getValue();
//	temp.R_STATE = form.findField('R_STATE').getValue();
//	temp.CREATE_USER_ID = form.findField('CREATE_USER_ID').getValue();
//	temp.CREATE_USER_NAME = form.findField('CREATE_USER_NAME').getValue();
	temp.CREATE_TIMES = form.findField('CREATE_TIMES').getValue();
}


var setBaseValue = function(form) {
	form.findField('ID').setValue(temp.ID);
	form.findField('CUST_ID').setValue(temp.CUST_ID);
	form.findField('CUST_NAME').setValue(temp.CUST_NAME);
//	form.findField('CUST_NAME_R').setValue(temp.CUST_NAME_R);
//	form.findField('CUST_NO_R').setValue(temp.CUST_NO_R);
//	form.findField('RELATIONSHIP').setValue(temp.RELATIONSHIP);
//	form.findField('R_DESC').setValue(temp.R_DESC);
//	form.findField('R_STATE').setValue(temp.R_STATE);
//	form.findField('CREATE_USER_ID').setValue(temp.CREATE_USER_ID);
	//form.findField('CREATE_USER_NAME').setValue(temp.CREATE_USER_NAME);
	form.findField('CREATE_TIMES').setValue(temp.CREATE_TIMES);
}

var beforeconditioninit = function(panel, app){
	app.pageSize = 100;
};