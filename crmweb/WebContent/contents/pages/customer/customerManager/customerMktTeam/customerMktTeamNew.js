/**
*@description 客户经理团队
*/ 
imports( [
'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js', // 机构放大镜
'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js'//用户放大镜
]);
var editValidates = true;
var d = '';

var lookupTypes = ['CUSTMANAGE_TEAM_STATUS','CUSTMANAGER_TEAM_TYPE','TEAM_CUSTMANAGER_STATUS','XD000080','XD000040','P_CUST_GRADE'];
// 机构树加载条件
var condition = {
	searchType : 'SUBTREE' // 查询子机构
};
var treeLoaders = [ {
	key : 'DATASETMANAGERLOADER',
	url : basepath + '/commsearch.json?condition=' + Ext.encode(condition),
	parentAttr : 'SUPERUNITID',
	locateAttr : 'UNITID',
	jsonRoot : 'json.data',
	rootValue : JsContext._orgId,
	textField : 'UNITNAME',
	idProperties : 'UNITID'
} ];

/**
 * 左侧机构树配置
 */
var treeCfgs = [ {
	key : 'ORGTREE',
	loaderKey : 'DATASETMANAGERLOADER',
	autoScroll : true,
	rootCfg : {
		expanded:true,
		id:JsContext._orgId,
		text:JsContext._unitname,
		autoScroll:true,
		children:[],
		UNITID:JsContext._orgId,
		UNITNAME:JsContext._unitname
	}
} ];

var url=basepath+'/customerMktTeamInformationAdd.json';
var comitUrl=basepath + '/customerMktTeamInformationAdd.json';

var fields=[
            {name:'MKT_TEAM_NAME',text:'团队名称',searchField:true,resutlFloat:'right',allowBlank : false},
            {name:'ORG_ID',text:'归属机构',searchField:true,resutlWidth:80,hidden:true}, 
//            {name:'ORG_NAME',text:'归属机构',searchField:true,resutlWidth:120,xtype:'wcombotree',innerTree:'ORGTREE', showField:'text',hideField:'UNITID',editable:false,allowBlank: false},
            {name:'ORG_NAME',text:'归属机构',searchField:true,resutlWidth:120,xtype:'orgchoose',hiddenName:'ORG_ID',searchType:'SUBTREE',allowBlank: false},
            {name:'TEAM_TYPE',text:'团队类型',searchField:true,translateType : 'CUSTMANAGER_TEAM_TYPE',resutlWidth:60},
            {name:'CUST_MANAGER_NAME',text:'客户经理名称',searchField:true,resutlWidth:60,gridField:false},
            {name:'CREATE_USER_NAME',text:'创建人',xtype:'textfield',searchField:false,resutlWidth:100},
            {name:'CREATE_DATE',text:'创建时间',searchField:false,resutlFloat:'right',resutlWidth:80,format:'Y-m-d',xtype:'datefield',dataType : 'date'},
            {name:'LAST_MAINTAIN_TIME',text:'最后维护时间',resutlWidth:80,format:'Y-m-d',xtype:'datefield',dataType : 'date'},
            {name:'TEAM_SCALE',text:'团队人数',xtype:'textfield',resutlWidth:60},
            {name:'TEAM_LEADER',text:'负责人',searchField:false,xtype:'userchoose',singleSelect:true,hiddenName:'TEAM_LEADER_ID',resutlFloat:'right',resutlWidth:100,allowBlank: false},
            {name:'MKT_TEAM_ID',text:'营销团队编号',xtype:'textfield',searchField:false,gridField:false},
            {name:'TEAM_NO',text:'营销团队编号',xtype:'textfield',resutlFloat:'right',searchField:false,gridField:false},
            {name:'TEAM_CUS_NO',text:'团队客户数',xtype:'textfield',gridField:false},
            {name:'LEAD_TELEPHONE',text:'负责人手机号',xtype:'textfield',gridField:false},
            {name:'TEAM_STATUS',text:'状态',xtype:'textfield',resutlWidth:80,translateType:'CUSTMANAGE_TEAM_STATUS'},
            {name:'CREATE_USER_ID',text:'创建人ID',xtype:'textfield',gridField:false},
            {name:'CREATE_USER_ORG_ID',text:'创建人所属机构',xtype:'textarea',gridField:false},
            {name:'TEAM_LEADER_ID',text:'营销团队负责人ID',xtype:'textfield',gridField:false},
            {name:'CREATE_USER',gridField:false}
            ];
/**
 * 自定义工具条上按钮
 * 注：批量选择未实现,目前只支持单条 、启用、停用
 */
var tbar = [{
	text : '删除',
	hidden : JsContext.checkGrant('cust_mktTeam_delete'),
	handler : function() {
		if (getSelectedData() == false) {
			Ext.Msg.alert('提示', '请选择一条数据！');
			return false;
		} else {
			var selectRecord = getSelectedData();
			var id = '';
			// 0暂存，1 新增审批中 ，2 生效(审批通过)，3 失效（审批拒绝）4 修改审批中
			id = selectRecord.data.MKT_TEAM_ID;
			if (selectRecord.data.TEAM_STATUS == '1') {// 审批中的不能删除
				Ext.Msg.alert('提示', '团队审批中不能删除！');
				return false;
			}
			if(selectRecord.data.TEAM_SCALE!=0 ){
				Ext.Msg.alert('提示', '请先删除团队成员再删除团队');
				return false;
			}
			// if(selectRecords[i].data.TEAM_STATUS == '2'){//审批中的不能删除
			// Ext.Msg.alert('提示','请先删除团队成员再删除团队');
			// return false;
			// }
			

			Ext.MessageBox.confirm('提示', '确定删除吗?', function(buttonId) {
				if (buttonId.toLowerCase() == "no") {
					return;
				}
				var rolesArr = __roles.split('$');
				for (var i = 0; i < rolesArr.length; i++) {
					if (rolesArr[i] == '109') {
						Ext.Ajax.request({
							url : basepath
									+ '/customerMktTeamInformationAdd!batchDestroy.json?idStr='
									+ id,
							success : function() {
								Ext.Msg.alert('提示', '删除成功');
								reloadCurrentData();
							},
							failure : function() {
								Ext.Msg.alert('提示', '删除失败');
								reloadCurrentData();
							}
						});
						break;
					} else {
						var commintData = translateDataKey(getSelectedData().data,_app.VIEWCOMMITTRANS);
						Ext.Msg.wait('正在提交数据，请稍等...', '提示');
						Ext.Ajax.request({
							url : basepath
									+ '/customerMktTeamInformationAdd!deleteTeam.json',
							method : 'GET',
							params : commintData,
							success : function(response) {
								var ret = Ext.decode(response.responseText);
								var instanceid = ret.instanceid;// 流程实例ID
								var currNode = ret.currNode;// 当前节点
								var nextNode = ret.nextNode;// 下一步节点
								selectUserList(instanceid, currNode, nextNode);// 选择下一步办理人
								reloadCurrentData();
								hideCurrentView();
							},
							failure : function() {
								Ext.Msg.alert('提示', '提交失败!');
							}
						});
						break;
					}
				}

			});
		}
	}
}];
//自定义面板，包括新增，修改客户经理团队成员信息		
var customerView = [{
	title : '新增',
	type : 'form',
	hideTitle:JsContext.checkGrant('cust_mktTeam_create'),
	groups: [{
		columnCount : 1,
		fields : ['ORG_ID','TEAM_STATUS','MKT_TEAM_ID','MKT_TEAM_NAME','ORG_NAME','TEAM_TYPE','TEAM_LEADER'],
		fn : function(ORG_ID,TEAM_STATUS,MKT_TEAM_ID,MKT_TEAM_NAME,ORG_NAME,TEAM_TYPE,TEAM_LEADER){
			TEAM_TYPE.value = '00';
			MKT_TEAM_ID.hidden = true;
			TEAM_STATUS.hidden = true;
			ORG_ID.hidden = true;
			return [ORG_ID,TEAM_STATUS,MKT_TEAM_ID,MKT_TEAM_NAME,ORG_NAME,TEAM_TYPE,TEAM_LEADER];
		}
	}],
  formButtons:[{
	  /**
	   * 新增--保存按鈕
	   */
	text : '保存',
	//保存数据		
	hidden:JsContext.checkGrant('mktTeam_create_save'),
	fn : function(formPanel,baseform){						
		if(!baseform.isValid())
			{
				Ext.Msg.alert('提示','请输入完整！');
				return false;
			}
			var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在提交数据，请稍等...','提示');
			Ext.Ajax.request({
 				url : basepath + '/customerMktTeamInformationAdd.json?ifsave=1',
 				method : 'POST',
 				params : commintData,
				success : function() {
					Ext.Msg.alert('提示',"操作成功!");
					reloadCurrentData();
					hideCurrentView();
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
		}
		},{
		/**
		 * 新增---提交審批
		 */
		id:'createTo',
		text : '提交',
		hidden:JsContext.checkGrant('mktTeam_create_pub'),
		fn : function(formPanel,baseform){
			if(formPanel.getForm().getValues().MKT_TEAM_NAME == null || formPanel.getForm().getValues().MKT_TEAM_NAME == "" ){
				Ext.Msg.alert('提示','请填写团队名称');
				return false;
			}
			if(formPanel.getForm().getValues().ORG_NAME == null || formPanel.getForm().getValues().ORG_NAME == "" ){
				Ext.Msg.alert('提示','请填写机构名称');
				return false;
			}
			if(formPanel.getForm().getValues().TEAM_LEADER == null || formPanel.getForm().getValues().TEAM_LEADER == "" ){
				Ext.Msg.alert('提示','请填写负责人');
				return false;
			}
			var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在提交数据，请稍等...','提示');
			Ext.Ajax.request({
				url : basepath + '/customerMktTeamInformationAdd!saveTeam.json',
				method : 'GET',
				params : commintData,
				success : function(response) {
					var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
					reloadCurrentData();
					hideCurrentView();
				},
				failure : function() {
					Ext.Msg.alert('提示', '提交失败!');	
				}
			});
		}
	}]

},{
	title : '修改',
	type : 'form',
	hidden:JsContext.checkGrant('cust_mktTeam_modify'),
	groups: [{
		columnCount : 1,
		fields : ['CREATE_USER','CREATE_USER_ORG_ID','MKT_TEAM_NAME','TEAM_TYPE','ORG_ID','ORG_NAME','CREATE_USER_ID','CREATE_DATE','CREATE_USER_NAME','MKT_TEAM_ID','TEAM_SCALE','TEAM_LEADER','TEAM_NO','TEAM_CUS_NO','LEAD_TELEPHONE','TEAM_STATUS','TEAM_LEADER_ID'],
		fn : function(CREATE_USER,CREATE_USER_ORG_ID,MKT_TEAM_NAME,TEAM_TYPE,ORG_ID,ORG_NAME,CREATE_USER_ID,CREATE_DATE,CREATE_USER_NAME,MKT_TEAM_ID,TEAM_SCALE,TEAM_LEADER,TEAM_NO,TEAM_CUS_NO,LEAD_TELEPHONE,TEAM_STATUS,TEAM_LEADER_ID){
			ORG_ID.hidden=true;
			TEAM_SCALE.hidden = true;
			TEAM_LEADER.hidden = false;
			TEAM_NO.hidden = true;
			TEAM_CUS_NO.hidden = true;
			LEAD_TELEPHONE.hidden = true;
			TEAM_STATUS.hidden = true;
			TEAM_LEADER_ID.hidden = true;
			MKT_TEAM_ID.hidden = true;
			CREATE_USER_NAME.hidden = true;
			CREATE_DATE.hidden = true;
			CREATE_USER_ID.hidden = true;
			CREATE_USER_ORG_ID.hidden =true;
			CREATE_USER.hidden = true;
			return [CREATE_USER,CREATE_USER_ORG_ID,MKT_TEAM_NAME,TEAM_TYPE,ORG_ID,ORG_NAME,CREATE_USER_ID,CREATE_DATE,CREATE_USER_NAME,MKT_TEAM_ID,TEAM_SCALE,TEAM_LEADER,TEAM_NO,TEAM_CUS_NO,LEAD_TELEPHONE,TEAM_STATUS,TEAM_LEADER_ID];
		}
	}],
	formButtons:[{
		/**
		 * 修改任务-保存按钮
		 */
		text : '保存',
		hidden:JsContext.checkGrant('mktTeam_create_save'),
		fn : function(contentPanel, baseform){
			if(!baseform.isValid()){
				Ext.Msg.alert('提示','输入格式错误或存在漏输入项,请重新输入！');
				return false;
			}
			var status = getSelectedData().data.TEAM_STATUS; 
			var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在提交数据，请稍等...','提示');
			Ext.Ajax.request({
 				url : basepath + '/customerMktTeamInformationAdd.json?ifsave=1',
 				method : 'POST',
 				params : commintData,
				success : function() {
					Ext.Msg.alert('提示',"操作成功!");
					reloadCurrentData();
					hideCurrentView();
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
		}
	},{
		/**
		 * 新增---提交審批
		 */
		text : '提交',
		hidden:JsContext.checkGrant('mktTeam_create_pub'),
		fn : function(formPanel,baseform){
			if(formPanel.getForm().getValues().MKT_TEAM_NAME == null || formPanel.getForm().getValues().MKT_TEAM_NAME == "" ){
				Ext.Msg.alert('提示','请填写团队名称');
				return false;
			}
			if(formPanel.getForm().getValues().ORG_NAME == null || formPanel.getForm().getValues().ORG_NAME == "" ){
				Ext.Msg.alert('提示','请填写机构名称');
				return false;
			}
			if(formPanel.getForm().getValues().TEAM_LEADER == null || formPanel.getForm().getValues().TEAM_LEADER == "" ){
				Ext.Msg.alert('提示','请填写负责人');
				return false;
			}
			var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在提交数据，请稍等...','提示');
			Ext.Ajax.request({
				url : basepath + '/customerMktTeamInformationAdd!saveTeam.json',
				method : 'GET',
				params : commintData,
				success : function(response) {
					var ret = Ext.decode(response.responseText);
					var instanceid = ret.instanceid;//流程实例ID
					var currNode = ret.currNode;//当前节点
					var nextNode = ret.nextNode;//下一步节点
					selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
					reloadCurrentData();
					hideCurrentView();
				},
				failure : function() {
					Ext.Msg.alert('提示', '提交失败!');	
				}
			});
		}
	}]

},{
	title : '客户信息',
	type : 'grid',
	hideTitle:true,
	url  : basepath+'/customerMktTeam.json',//团队客户信息访问后台数据的路径
	frame : true,
	isCsm:false,
	fields: {
		fields : [
		          {name:'CUST_ID',text:'客户编号'},
		          {name:'CUST_NAME',text:'客户名称'},
		          {name:'CUST_TYPE',text:'客户类型',translateType:'XD000080',hidden:true},
		          {name:'CUST_TYPE_ORA',text:'客户类型'},
		          {name:'IDENT_TYPE',text:'证件类型',translateType:'XD000040',hidden:true},
		          {name:'IDENT_TYPE_ORA',text:'证件类型'},
		          {name:'IDENT_NO',text:'证件号码'},
		          {name:'CUST_LEVEL',text:'客户级别',translateType:'P_CUST_GRADE',hidden:true},
		          {name:'CUST_LEVEL_ORA',text:'客户级别'},
		          {name:'LINKMAN_NAME',text:'联系人姓名'},
		          {name:'LINKMAN_TEL',text:'联系人电话'},
		          {name:'ACCT_NO',text:'账号'},
		          {name:'ORG_NAME',text:'归属机构'},	   
		          {name: 'MGR_NAME',text:'所属客户经理'}
				]
		}
},{
	title : '团队成员信息',
	type : 'grid',
	singleSelected:true,
	//hideTitle:JsContext.checkGrant('cust_mktTeam_teams'),
	url  : basepath+'/customerMktTeamMembers.json',//团队成员信息访问后台数据的路径
	frame : true,
	fields: {
		fields : [
		    {name: 'USER_ID',hidden : true},
			{name: 'ID',hidden : true},
			{name: 'MKT_TEAM_ID',hidden : true},
			{name: 'TEAM_NAME',text: '团队名称',hidden : true},
			{name: 'BELONG_ORG',text: '归属机构',xtype:'orgchoose',hiddenName:'ORG_ID',searchType:'SUBTREE',hidden : true},
			{name: 'TEAM_TYPE',text: '团队类型',translateType : 'CUSTMANAGER_TEAM_TYPE',hidden : true},
			{name: 'CUST_MANAGER_ID', text : '客户经理工号'},     
			{name: 'CUST_MANAGER_NAME', text : '客户经理名称'},    
			{name: 'CUST_MANAGER_ORG', text : '客户经理归属机构',xtype:'orgchoose',hiddenName:'ORG_ID',searchType:'SUBTREE',hidden : true},    
			//{name: 'CUST_MANAGER_STATE', text : '客户经理状态',translateType:'TEAM_CUSTMANAGER_STATUS',hidden:true},   
			//{name: 'CUST_MANAGER_STATE_ORA', text : '客户经理状态'},   
			{name: 'JOIN_DATE', text : '加入团队时间',xtype:'datefield',format:'Y-m-d'},
			{name: 'TASKNUM', xtype:'datefield',hidden:true}
				]
		},
	gridButtons :[{
		text : '新增成员',
		id:'addTp',
		fn:function(grid){
			//团队负责人才能添加删除
			if(teamLeaderId != __userId && creatUserId!=__userId){
				Ext.Msg.alert('提示','团队负责人或创建人才能新增成员!');
				return false;
			}
			if(getSelectedData().data.TEAM_STATUS != "2"){
				Ext.Msg.alert('提示','团队未生效无法新增成员!');
				return false;
			}
			showCustomerViewByTitle('新增成员信息');
		}			
   },{
	text:'删除成员',
	id:'deleteTp',
	fn :function(grid){
		//删除
		var selectLength = grid.getSelectionModel().getSelections().length;
	 	var selectRecords = grid.getSelectionModel().getSelections()[0];
	 	
		if(selectLength < 1){
 			Ext.Msg.alert('提示','请选择一条数据进行操作!');
			return false;
		}
		if(teamLeaderId != __userId && creatUserId!=__userId){
			Ext.Msg.alert('提示','团队负责人或创建人才能删除成员!');
			return false;
		}
		if(selectRecords.data.TASKNUM!=0){
			Ext.Msg.alert('提示','团队成员的营销任务未关闭不能删除!');
			return false;
		}
		var tempIdStr = selectRecords.data.ID;
		var tempStatus = '';
		
//		for(var i=0; i < selectLength; i++){
//			var selectRecord = selectRecords[i];
//			//临时变量，保存要删除的ID
//			tempIdStr +=  selectRecord.data.ID;
//		 		if( i != selectLength - 1){
//		   		 	tempIdStr += ',';
//					}
//		 	//0.待加入 ，1 待删除  ，2  生效 ，3失效
//		 	if(selectRecord.data.CUST_MANAGER_STATE_ORA != '生效'){
//		 		tempStatus = selectRecord.data.CUST_MANAGER_STATE_ORA;
//		 	}
//		 }
		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
		if(buttonId.toLowerCase() == 'no'){
    		return false;
		}
		Ext.Msg.wait('正在提交数据，请稍等...','提示');
			Ext.Ajax.request({
				url : basepath + '/customerMktTeamMembers!batchDes.json?idStr='+ tempIdStr,
				method : 'GET',
				params : {
					instanceid :getSelectedData().data.MKT_TEAM_ID ,
					name:getSelectedData().data.MKT_TEAM_NAME,
					idStr : tempIdStr
				},
				success : function(response) {
					Ext.Msg.alert('提示','删除成功!');
					reloadCurrentData();
					hideCurrentView();
				}
			});
	   });
	}
  }]
},{
	title : '新增成员信息',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 1 ,
	fields : [	
            {name : 'MKT_TEAM_ID',hidden : true},
            {name : 'USER_ID',hidden : true},
            {name : 'CUST_MANAGER_NAME',text  : '客户经理名称',hiddenName:'USER_ID',dataType:'userchoose',
            	searchField : true,singleSelect:false},
			{name : 'TEAM_TYPE',hidden : true},
			{name : 'MKT_TEAM_NAME',hidden : true},
			{name : 'BELONG_ORG',hidden : true,xtype:'orgchoose',hiddenName:'ORG_ID',searchType:'SUBTREE'}
			],
	fn : function(USER_ID,MKT_TEAM_ID,TEAM_NAME,BELONG_ORG,TEAM_TYPE){
		return [USER_ID,MKT_TEAM_ID,TEAM_NAME,BELONG_ORG,TEAM_TYPE];
	}
			}],
formButtons:[{
	text : '保存',
	//保存数据					 
	fn : function(formPanel,baseform){						
		if(!baseform.isValid())
			{
				Ext.Msg.alert('提示','请输入完整！');
				return false;
			}
			var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在提交数据，请稍等...','提示');
			Ext.Ajax.request({
 				url : basepath + '/customerMktTeamMembers.json',
 				method : 'POST',
 				params : commintData,
				success : function() {
//					Ext.Ajax.request({
//						url : basepath + '/customerMktTeamMembers!initFlow.json',
//						method : 'GET',
//						params : {
//							instanceid :getSelectedData().data.MKT_TEAM_ID ,
//							name:getSelectedData().data.MKT_TEAM_NAME 
//						},
//						success : function(response) {
//							 var ret = Ext.decode(response.responseText);
//								var instanceid = ret.instanceid;//流程实例ID
//								var currNode = ret.currNode;//当前节点
//								var nextNode = ret.nextNode;//下一步节点
//								selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
//								reloadCurrentData();
//								hideCurrentView();
//						}
//					});
					Ext.Msg.alert('提示', '保存成功');
					reloadCurrentData();
					hideCurrentView();
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
		}
		},{
			text : '返回',
			fn : function(grid){
    			showCustomerViewByTitle('团队成员信息');
				}
			}]	
	}];

/**
* 在页面跳转前根据view的_defaultTitle做相应的逻辑判断
*/		
var beforeviewshow = function(view){
	if(view._defaultTitle == '修改'){
		if(getSelectedData() == false|| getAllSelects().length > 1){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		////0暂存，1 新增审批中 ，2 生效(审批通过)，3 失效（审批拒绝）4 修改审批中
		if(getSelectedData().data.TEAM_STATUS != '0' && getSelectedData().data.TEAM_STATUS == '1'){
			Ext.Msg.alert('提示','团队审批中不能修改!');
			return false;
		}
		var tempForm = view.contentPanel.getForm();
		tempForm.reset();
		tempForm.loadRecord(getSelectedData());
	}
	
	if(view._defaultTitle == '团队成员信息' || view._defaultTitle == "客户信息"){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}else{//加载数据
			teamLeaderId = getSelectedData().data.TEAM_LEADER_ID;
			creatUserId=getSelectedData().data.CREATE_USER_ID;
			if(teamLeaderId != __userId){
//				Ext.getCmp("addTp").hide();
//				Ext.getCmp("deleteTp").hide();
			}else{
				Ext.getCmp("addTp").show();
				Ext.getCmp("deleteTp").show();
			}
			if(getSelectedData().data.TEAM_STATUS == '3'){
				Ext.Msg.alert('提示','团队审批未通过不能进行此操作!');
				return false;
			}
			if(getSelectedData().data.TEAM_STATUS == '1'){
				Ext.Msg.alert('提示','团队审批中不能进行此操作!');
				return false;
			}
			mktTeamId = getSelectedData().data.MKT_TEAM_ID;
			teamName =  getSelectedData().data.MKT_TEAM_NAME;
			teamLeaderId  = teamLeaderId;
			c_teamStatus = getSelectedData().data.TEAM_STATUS;
			view.setParameters ({
				mktTeamId : mktTeamId,
				teamLeaderId:teamLeaderId,
				creatUserId:creatUserId
    		}); 		
		}			
	}
};
