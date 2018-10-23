/**
 * 联盟商增值服务映射关系
 * hujun
 * 2014-06-19
 */
	imports([
	         '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	         '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
	         '/contents/pages/common/Com.yucheng.crm.common.AlianceProg.js',
	         '/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js'
	         ]);
	Ext.QuickTips.init();

	var localLookup = {
		//增值服务标识数据字典
		'SERVICE_IDTYPE' : [
			{key : '0',value : '否'},
			{key : '1',value : '是'}
		]
	};
	
	var createView=true;
	var editView=true;
	var detailView=true;
	
	var url=basepath+'/alianceProgramRaleQueryAction.json';
	var comitUrl=basepath+'/alianceProgramRaleAction.json';
	
	var fields=[
	            {name:'ID',text:'编号',hidden:true},
	            {name:'ALIANCE_PROGRAM_ID',text:'联盟商代码',xtype:'textfield',hidden:true},
	            {name:'ALIANCE_PROGRAM_NAME',text:'联盟商名称',xtype:'alianceProgChoose',hiddenName:'ALIANCE_PROGRAM_ID',searchField:true},
	            {name:'ADD_SERVICE_NAME',text:'增值服务名称',gridField:false},
	            {
			name : 'ADD_SERVICE_IDENTIFY',
			text : '增值服务标识',
			translateType:'SERVICE_IDTYPE',
			searchField : true,
			xtype : 'combo',
			width : 180,
			mode : 'local',
			store : new Ext.data.Store(),
			triggerAction : 'all',
			displayField : 'value',
			valueField : 'key',
			editable : false,
			allowBlank : false},
	            {name:'PROVD_MRK',text:'提供商标识',searchField:true},
	            {name:'SERVICE_CONTENT',text:'增值服务内容',gridField:false,xtype:'textarea',resutlWidth:350},
	            {name:'ORG_NAME',text:'适用范围',xtype: 'wcombotree',gridField:true,searchField:true,
	            	innerTree:'DATASETMANAGERTREE',allowBlank:false,showField:'text',hideField:'UNITID',editable:false},
	            {name:'RANGE_APPLY',text:'适用范围',xtype: 'wcombotree',gridField:false,
	            	innerTree:'DATASETMANAGERTREE',allowBlank:false,showField:'text',hideField:'UNITID',editable:false},
	            {name:'CREATE_DATE',text:'创建日期',xtype:'datefield',format:'Y-m-d',searchField:true},
	            {name:'CREATE_USER',text:'创建人',searchField:true,gridField:false},
	            {name:'CREATE_ORG',text:'创建机构',xtype: 'wcombotree',searchField:true,
	            	innerTree:'DATASETMANAGERTREE1',allowBlank:false,showField:'text',editable:false,gridField:false},
	            {name:'CREATE_USER_NAME',text:'创建人'},
	            {name:'CREATE_ORG_NAME',text:'创建机构'},
	            {name:'REMARK',text:'备注',xtype:'textarea',resutlWidth:350}
	            ];
	
	var createFormViewer=[{
		columnCount:2,
		fields:['ALIANCE_PROGRAM_ID','ALIANCE_PROGRAM_NAME','ADD_SERVICE_NAME','ADD_SERVICE_IDENTIFY',
		        'RANGE_APPLY'],
		fn:function(ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ADD_SERVICE_NAME,ADD_SERVICE_IDENTIFY,
				RANGE_APPLY){
			ALIANCE_PROGRAM_ID.hidden=true;
			return [ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ADD_SERVICE_NAME,ADD_SERVICE_IDENTIFY,
					RANGE_APPLY];
		}						
		},{columnCount:1,
			fields:['SERVICE_CONTENT','REMARK'],
			fn:function(SERVICE_CONTENT,REMARK){
				return [SERVICE_CONTENT,REMARK];
			}
	}];
	
	var editFormViewer=[{
		columnCount:2,
		fields:['ID','ALIANCE_PROGRAM_ID','ALIANCE_PROGRAM_NAME','ADD_SERVICE_NAME','ADD_SERVICE_IDENTIFY',
		        'RANGE_APPLY','CREATE_DATE','CREATE_USER','CREATE_ORG'],
		fn:function(ID,ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ADD_SERVICE_NAME,ADD_SERVICE_IDENTIFY,
				RANGE_APPLY,CREATE_DATE,CREATE_USER,CREATE_ORG){
			ID.hidden=true;
			ALIANCE_PROGRAM_ID.hidden=true;
			CREATE_DATE.hidden=true;
			CREATE_USER.hidden=true;
			CREATE_ORG.hidden=true;
			return [ID,ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ADD_SERVICE_NAME,ADD_SERVICE_IDENTIFY,
					RANGE_APPLY,CREATE_DATE,CREATE_USER,CREATE_ORG];
		}						
		},{columnCount:1,
			fields:['SERVICE_CONTENT','REMARK'],
			fn:function(SERVICE_CONTENT,REMARK){
				return [SERVICE_CONTENT,REMARK];
			}
	}];
	var detailFormViewer=[{
		columnCount:2,
		fields:['ALIANCE_PROGRAM_ID','ALIANCE_PROGRAM_NAME','ADD_SERVICE_NAME','ADD_SERVICE_IDENTIFY',
		        'RANGE_APPLY'],
		fn:function(ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ADD_SERVICE_NAME,ADD_SERVICE_IDENTIFY,
				RANGE_APPLY){
			ALIANCE_PROGRAM_ID.hidden=true;
			ALIANCE_PROGRAM_NAME.disabled = true;
			ADD_SERVICE_NAME.disabled = true;
			ADD_SERVICE_IDENTIFY.disabled = true;
			RANGE_APPLY.disabled = true;
			ALIANCE_PROGRAM_NAME.cls = 'x-readOnly';
			ADD_SERVICE_NAME.cls = 'x-readOnly';
			ADD_SERVICE_IDENTIFY.cls = 'x-readOnly';
			RANGE_APPLY.cls = 'x-readOnly';
			return [ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ADD_SERVICE_NAME,ADD_SERVICE_IDENTIFY,
					RANGE_APPLY];
		}						
		},{columnCount:1,
			fields:['SERVICE_CONTENT','REMARK'],
			fn:function(SERVICE_CONTENT,REMARK){
				SERVICE_CONTENT.disabled = true;
				REMARK.disabled = true;
				SERVICE_CONTENT.cls = 'x-readOnly';
				REMARK.cls = 'x-readOnly';
				return [SERVICE_CONTENT,REMARK];
			}
	}];
	var condition = {searchType:'SUBTREE'};
	var treeLoaders = [{
		key : 'DATASETMANAGERLOADER',
		url : basepath + '/commsearch.json?condition='+Ext.encode(condition),
		parentAttr : 'SUPERUNITID',
		locateAttr : 'UNITID',
		jsonRoot:'json.data',
		rootValue : JsContext._orgId,
		textField : 'UNITNAME',
		idProperties : 'UNITID'
	}];
	var treeCfgs = [{
		key : 'DATASETMANAGERTREE',
		loaderKey : 'DATASETMANAGERLOADER',
		autoScroll:true,
		rootCfg : {
			expanded:true,
			id:JsContext._orgId,
			text:JsContext._unitname,
			UNITID: JsContext._orgId,
			UNITNAME: JsContext._unitname,
			autoScroll:true,
			children:[]
		}
	},{
		key : 'DATASETMANAGERTREE1',
		loaderKey : 'DATASETMANAGERLOADER',
		autoScroll:true,
		rootCfg : {
			expanded:true,
			id:JsContext._orgId,
			text:JsContext._unitname,
			UNITID: JsContext._orgId,
			UNITNAME: JsContext._unitname,
			autoScroll:true,
			children:[]
		}
	}];
	var tbar=[{
		text:'删除',
		handler:function(){
			var records =getAllSelects();// 得到被选择的行的数组
			var selectLength = getAllSelects().length;// 得到行数组的长度
			if (selectLength < 1) {
				Ext.Msg.alert('提示信息', '请至少选择一条记录！');
			} else {
				var serviceId;
				var idStr = '';
				for ( var i = 0; i < selectLength; i++) {
					selectRe = records[i];
					serviceId = selectRe.data.ID;// 获得选中记录的id
					idStr += serviceId;
					if (i != selectLength - 1)
						idStr += ',';
				};
				if (idStr != '') {
					Ext.MessageBox.confirm('提示','确认进行删除操作吗？',
					function(buttonobj) {
						if (buttonobj == 'yes'){
						Ext.Ajax.request({
							url : basepath+ '/alianceProgramRaleAction!batchDestroy.json',
							params : {
								idStr : idStr
							},
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							method : 'GET',
							scope : this,
							success : function() {
								Ext.Msg.alert('提示信息', '操作成功！');
								reloadCurrentData();
							}
						});}
				 }, this);
				}
			}
		
		}
		}];
	var beforeviewshow = function(view){
		view.contentPanel.getForm().findField('ADD_SERVICE_IDENTIFY').bindStore(findLookupByType('SERVICE_IDTYPE'));
		if(view.baseType == 'createView'){
			//view.contentPanel.getForm().findField('UP_ORG_ID').setValue('');
		}else{
			if(!getSelectedData()){
				Ext.Msg.alert('提示信息','请选择一条数据后操作！');
				return false;
			}
		}
	};
	