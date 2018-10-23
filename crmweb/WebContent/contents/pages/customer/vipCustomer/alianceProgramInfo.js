/**
 * 联盟商信息
 * hujun
 * 2014-06-20
 */
	imports([
	         '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	         '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js'
	         ]);
	Ext.QuickTips.init();
	
	var createAnna;  //新增面板中的附件列表部分
	var editAnna;    //修改面板中的附件列表部分
	var detailAnna;  //详情面板中的附件列表部分
	var appAnna ;    //审批面板中的附件列表部分
	var createView=true;
	var editView=true;
	var detailView=true;
	WLJUTIL.alwaysLockCurrentView = true;//由于在保存之后，还需要操作附件列表，所以本功能页面锁定悬浮面板滑出

	var url=basepath+'/alianceProgramQueryAction.json';
	var comitUrl=basepath+'/alianceProgramInfoAction.json';
	
	var lookupTypes=[
	                 'PAR2100001',
	                 'DEM0200002',
	                 'ALIANCE_STATE',
	                 'CDE0100016',
	                 'SERVICE_CHARACT',
	                 'ALIANCE_PROG_LEVEL'
	                 ];
	var fields=[
	            {name:'ID',text:'编号',hidden:true},
				{name:'ALIANCE_PROGRAM_ID',text:'联盟商代码',allowBlank:false,searchField:true,editable:false},
				{name:'ALIANCE_PROGRAM_NAME',text:'联盟商名称',allowBlank:false,searchField:true},
				{name:'ALIANCE_PROG_LEVEL',text:'联盟商等级',searchField:true,allowBlank:false,translateType:'ALIANCE_PROG_LEVEL'},
				{name:'ALIANCE_EN_NAME',text:'英文名称',gridField:false},
				{name:'CORP_NO',text:'法人代码',gridField:false,allowBlank:false},     
				{name:'ECONOMIC_TYPE',text:'经济类型',allowBlank:false,translateType:'DEM0200002',gridField:false},
				{name:'ORGAN_CODE',text:'组织机构代码',allowBlank:false,gridField:false},
				{name:'COMPANY_TYPE',text:'公司类型',allowBlank:false,translateType:'PAR2100001'},
				{name:'SERVICE_CHARACT',text:'服务特性',allowBlank:false,gridField:false,translateType:'SERVICE_CHARACT'},
//				{name:'SERVICE_RANGE',gridField:false,hidden:true},
//				{name:'ORG_NAME',text:'服务范围',xtype: 'wcombotree',gridField:true,searchField:true,
//	            	innerTree:'DATASETMANAGERTREE',allowBlank:false,showField:'text',hideField:'UNITID',editable:false},
//	            {name:'SERVICE_RANGE_NAME',text:'服务范围',xtype: 'wcombotree',gridField:false,
//	            	innerTree:'DATASETMANAGERTREE',allowBlank:false,showField:'text',hideField:'UNITID',editable:false},
				{name:'SERVICE_RANGE',text:'服务范围',
					xtype: 'wcombotree',editable:false,allowBlank:false,
					searchField:true,showField:'text',
					innerTree:'DATASETMANAGERTREE',hideField:'UNITID',hidden:true},
				{name:'ORG_NAME',text:'服务范围',xtype: 'wcombotree',editable:false,allowBlank:false,
						searchField:true,showField:'text',
						innerTree:'DATASETMANAGERTREE',hideField:'UNITID'},	 
	            {name:'START_DATE',text:'合作开始日期',searchField:true,xtype:'datefield', format:'Y-m-d'},
				{name:'END_DATE',text:'合作结束日期',searchField:true,xtype:'datefield', format:'Y-m-d'},
				{name:'CALL_NUM',text:'联系电话'},
				{name:'POST_NO',text:'邮政编码',gridField:false},
				{name:'REGIST_CAPITAL',text:'注册资本',xtype:'numberfield',gridField:false},
				{name:'REGIST_ADDRESS',text:'注册地址',gridField:false},
				{name:'WORK_ADDRESS',text:'办公地址'},
				{name:'OPERATE_SITE',text:'经营场所',gridField:false},
				{name:'DELAY_END_DATE',text:'延期截止日期',gridField:false,xtype:'datefield', format:'Y-m-d'},
				{name:'APPLI_MAN',text:'审批人',gridField:false},
				{name:'REMARK',text:'备注',gridField:false},
				{name:'OUT_REASON',text:'退出理由',gridField:false},
				{name:'STATE',text:'状态',translateType:'ALIANCE_STATE'}
	            ];
	
	var createFormViewer=[{
		columnCount:2,
		fields:['ID','ALIANCE_PROGRAM_ID','ALIANCE_PROGRAM_NAME','ALIANCE_PROG_LEVEL','ALIANCE_EN_NAME',
		        'CORP_NO','ECONOMIC_TYPE','ORGAN_CODE','COMPANY_TYPE','SERVICE_CHARACT','SERVICE_RANGE',
		        'START_DATE','END_DATE','CALL_NUM','POST_NO','REGIST_CAPITAL',
		        'DELAY_END_DATE','STATE'],
		fn:function(ID,ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ALIANCE_PROG_LEVEL,
				ALIANCE_EN_NAME,CORP_NO,ECONOMIC_TYPE,ORGAN_CODE,COMPANY_TYPE,
				SERVICE_CHARACT,SERVICE_RANGE,START_DATE,END_DATE,CALL_NUM,
				POST_NO,REGIST_CAPITAL,DELAY_END_DATE,STATE){
			DELAY_END_DATE.hidden=true;
			SERVICE_RANGE.hidden=false;
			ID.hidden=true;
			STATE.value='01';
			STATE.hidden=true;
			return [ID,ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ALIANCE_PROG_LEVEL,
					ALIANCE_EN_NAME,CORP_NO,ECONOMIC_TYPE,ORGAN_CODE,COMPANY_TYPE,
					SERVICE_CHARACT,SERVICE_RANGE,START_DATE,END_DATE,CALL_NUM,
					POST_NO,REGIST_CAPITAL,DELAY_END_DATE,STATE];
		}						
		},{columnCount:1,
			fields:['REGIST_ADDRESS','WORK_ADDRESS','OPERATE_SITE','REMARK'],
			fn:function(REGIST_ADDRESS,WORK_ADDRESS,OPERATE_SITE,REMARK){
				return [REGIST_ADDRESS,WORK_ADDRESS,OPERATE_SITE,REMARK];
			}
	},{
		columnCount:1,
		fields:['OUT_REASON'],
		fn:function(OUT_REASON){
			createAnna = createAnnGrid(false,true,false,'<font color="red">(保存信息后可操作附件列表)</font>');
			return [createAnna];
		}
	}];
	
	var editFormViewer=[{
		columnCount:2,
		fields:['ID','ALIANCE_PROGRAM_ID','ALIANCE_PROGRAM_NAME','ALIANCE_PROG_LEVEL','ALIANCE_EN_NAME',
		        'CORP_NO','ECONOMIC_TYPE','ORGAN_CODE','COMPANY_TYPE','SERVICE_CHARACT','SERVICE_RANGE',
		        'START_DATE','END_DATE','CALL_NUM','POST_NO','REGIST_CAPITAL',
		        'DELAY_END_DATE','STATE'],
		fn:function(ID,ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ALIANCE_PROG_LEVEL,
				ALIANCE_EN_NAME,CORP_NO,ECONOMIC_TYPE,ORGAN_CODE,COMPANY_TYPE,
				SERVICE_CHARACT,SERVICE_RANGE,START_DATE,END_DATE,CALL_NUM,
				POST_NO,REGIST_CAPITAL,DELAY_END_DATE,STATE){
			DELAY_END_DATE.hidden=true;
			ID.hidden=true;
			STATE.hidden=true;
			SERVICE_RANGE.hidden=false;
			return [ID,ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ALIANCE_PROG_LEVEL,
					ALIANCE_EN_NAME,CORP_NO,ECONOMIC_TYPE,ORGAN_CODE,COMPANY_TYPE,
					SERVICE_CHARACT,SERVICE_RANGE,START_DATE,END_DATE,CALL_NUM,
					POST_NO,REGIST_CAPITAL,DELAY_END_DATE,STATE];
		}						
		},{columnCount:1,
			fields:['REGIST_ADDRESS','WORK_ADDRESS','OPERATE_SITE','REMARK'],
			fn:function(REGIST_ADDRESS,WORK_ADDRESS,OPERATE_SITE,REMARK){
				return [REGIST_ADDRESS,WORK_ADDRESS,OPERATE_SITE,REMARK];
			}
	},{
		columnCount:1,
		fields:['OUT_REASON'],
		fn:function(OUT_REASON){
			editAnna = createAnnGrid(false,true,false,'');
			return [editAnna];
		}
	}];
	var detailFormViewer=[{
		columnCount:2,
		fields:['ID','ALIANCE_PROGRAM_ID','ALIANCE_PROGRAM_NAME','ALIANCE_PROG_LEVEL','ALIANCE_EN_NAME',
		        'CORP_NO','ECONOMIC_TYPE','ORGAN_CODE','COMPANY_TYPE','SERVICE_CHARACT','SERVICE_RANGE',
		        'START_DATE','END_DATE','CALL_NUM','POST_NO','REGIST_CAPITAL',
		        'DELAY_END_DATE','STATE'],
		fn:function(ID,ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ALIANCE_PROG_LEVEL,
				ALIANCE_EN_NAME,CORP_NO,ECONOMIC_TYPE,ORGAN_CODE,COMPANY_TYPE,
				SERVICE_CHARACT,SERVICE_RANGE,START_DATE,END_DATE,CALL_NUM,
				POST_NO,REGIST_CAPITAL,DELAY_END_DATE,STATE){
			DELAY_END_DATE.hidden=true;
			ID.hidden=true;
			ALIANCE_PROGRAM_ID.disabled = true;
			ALIANCE_PROGRAM_NAME.disabled = true;
			ALIANCE_PROG_LEVEL.disabled = true;
			ALIANCE_EN_NAME.disabled = true;
			CORP_NO.disabled = true;
			ECONOMIC_TYPE.disabled = true;
			ORGAN_CODE.disabled = true;
			COMPANY_TYPE.disabled = true;
			SERVICE_CHARACT.disabled = true;
			START_DATE.disabled = true;
			END_DATE.disabled = true;
			CALL_NUM.disabled = true;
			POST_NO.disabled = true;
			REGIST_CAPITAL.disabled = true;
			STATE.disabled = true;
			SERVICE_RANGE.hidden=false;
			SERVICE_RANGE.disabled=true;
			ALIANCE_PROGRAM_ID.cls = 'x-readOnly';
			ALIANCE_PROGRAM_NAME.cls = 'x-readOnly';
			ALIANCE_PROG_LEVEL.cls = 'x-readOnly';
			ALIANCE_EN_NAME.cls = 'x-readOnly';
			CORP_NO.cls = 'x-readOnly';
			ECONOMIC_TYPE.cls = 'x-readOnly';
			ORGAN_CODE.cls = 'x-readOnly';
			COMPANY_TYPE.cls = 'x-readOnly';
			SERVICE_CHARACT.cls = 'x-readOnly';
			SERVICE_RANGE_NAME.cls = 'x-readOnly';
			START_DATE.cls = 'x-readOnly';
			END_DATE.cls = 'x-readOnly';
			CALL_NUM.cls = 'x-readOnly';
			POST_NO.cls = 'x-readOnly';
			REGIST_CAPITAL.cls = 'x-readOnly';
			STATE.cls = 'x-readOnly';
			return [ID,ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ALIANCE_PROG_LEVEL,
					ALIANCE_EN_NAME,CORP_NO,ECONOMIC_TYPE,ORGAN_CODE,COMPANY_TYPE,
					SERVICE_CHARACT,SERVICE_RANGE,START_DATE,END_DATE,CALL_NUM,
					POST_NO,REGIST_CAPITAL,DELAY_END_DATE,STATE];
		}						
		},{columnCount:1,
			fields:['REGIST_ADDRESS','WORK_ADDRESS','OPERATE_SITE','REMARK','OUT_REASON'],
			fn:function(REGIST_ADDRESS,WORK_ADDRESS,OPERATE_SITE,REMARK,OUT_REASON){
				REGIST_ADDRESS.disabled = true;
				WORK_ADDRESS.disabled = true;
				OPERATE_SITE.disabled = true;
				REMARK.disabled = true;
				OUT_REASON.disabled = true;
				REGIST_ADDRESS.cls = 'x-readOnly';
				WORK_ADDRESS.cls = 'x-readOnly';
				OPERATE_SITE.cls = 'x-readOnly';
				REMARK.cls = 'x-readOnly';
				OUT_REASON.cls = 'x-readOnly';
				return [REGIST_ADDRESS,WORK_ADDRESS,OPERATE_SITE,REMARK,OUT_REASON];
			}
	},{
		columnCount:1,
		fields:['OUT_REASON'],
		fn:function(OUT_REASON){
			detailAnna = createAnnGrid(true,false,true,false);
			return [detailAnna];
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
	var treeLoaders1 = [{
		key : 'DATASETMANAGERLOADER1',
		url : basepath + '/lookupEntIndustry.json?name=PAR2100001',
		parentAttr : 'PARENT',
		//locateAttr : 'UNITID',
		jsonRoot:'json.data',
		rootValue : "0",
		textField : 'VALUE',
		idProperties : 'ID'
	}];
//	var treeCfgs1 = [{
//		key : 'DATASETMANAGERTREE2',
//		loaderKey : 'DATASETMANAGERLOADER1',
//		autoScroll:true,
//		rootCfg : {
//			expanded:true,
//			id:'0',
//			text:"公司类型",
//			//UNITID: JsContext._id,
//			//UNITNAME: JsContext._unitname,
//			autoScroll:true,
//			children:[]
//		}
//	},{
//		key : 'DATASETMANAGERTREE3',
//		loaderKey : 'DATASETMANAGERLOADER3',
//		autoScroll:true,
//		rootCfg : {
//			expanded:true,
//			id:"0",
//			text:"公司类型",
////			UNITID: JsContext._orgId,
////			UNITNAME: JsContext._unitname,
//			autoScroll:true,
//			children:[]
//		}
//	}];
	var customerView=[{
		title : '审批',
		type : 'form',
		hideTitle : true,
		autoLoadSeleted : true,
		groups : [{
			columnCount:2,
			fields:[
			        {name:'ID',text:'编号',hidden:true},
			        {name:'ALIANCE_PROGRAM_ID',text:'联盟商代码',disabled:true},
					{name:'ALIANCE_PROGRAM_NAME',text:'联盟商名称',disabled:true},
					{name:'ALIANCE_PROG_LEVEL',text:'联盟商等级',disabled:true,allowBlank:false,translateType:'ALIANCE_PROG_LEVEL'},
					{name:'ALIANCE_EN_NAME',text:'英文名称',disabled:true},
					{name:'CORP_NO',text:'法人代码',disabled:true,allowBlank:false},     
					{name:'ECONOMIC_TYPE',text:'经济类型',allowBlank:false,translateType:'DEM0200002',disabled:true},
					{name:'ORGAN_CODE',text:'组织机构代码',allowBlank:false,disabled:true},
					{name:'COMPANY_TYPE',text:'公司类型',allowBlank:false,translateType:'PAR2100001',disabled:true},
					{name:'SERVICE_CHARACT',text:'服务特性',allowBlank:false,disabled:true,translateType:'SERVICE_CHARACT'},
					{name:'ORG_NAME',text:'服务范围',xtype: 'wcombotree',editable:false,allowBlank:false,
						showField:'text',disabled:true,
						innerTree:'DATASETMANAGERTREE',hideField:'UNITID'},	 
					{name:'START_DATE',text:'合作开始日期',xtype:'datefield', format:'Y-m-d',disabled:true},
					{name:'END_DATE',text:'合作结束日期',xtype:'datefield', format:'Y-m-d',disabled:true},
					{name:'CALL_NUM',text:'联系电话',disabled:true},
					{name:'POST_NO',text:'邮政编码',disabled:true},
					{name:'REGIST_CAPITAL',text:'注册资本',xtype:'numberfield',disabled:true},
					{name:'DELAY_END_DATE',text:'延期截止日期',xtype:'datefield', format:'Y-m-d',disabled:true}
			],
			fn : function(ID,ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ALIANCE_PROG_LEVEL,
					ALIANCE_EN_NAME,CORP_NO,ECONOMIC_TYPE,COMPANY_TYPE,SERVICE_CHARACT,SERVICE_RANGE,
					START_DATE,END_DATE,CALL_NUM,POST_NO,REGIST_CAPITAL,
					DELAY_END_DATE){
				ID.hidden=true;
				return [ID,ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ALIANCE_PROG_LEVEL,
						ALIANCE_EN_NAME,CORP_NO,ECONOMIC_TYPE,COMPANY_TYPE,SERVICE_CHARACT,SERVICE_RANGE,
						START_DATE,END_DATE,CALL_NUM,POST_NO,REGIST_CAPITAL,
						DELAY_END_DATE];
			}
		},{
			columnCount:1,
			field:[{name:'REGIST_ADDRESS',text:'注册地址',disabled:true},
					{name:'WORK_ADDRESS',text:'办公地址',disabled:true}],
			fn:function(REGIST_ADDRESS,WORK_ADDRESS){
				return [REGIST_ADDRESS,WORK_ADDRESS];
			}
		},{
			columnCount:1,
			field:[{name:'ALIANCE_PROGRAM_ID'}],
			fn:function(ALIANCE_PROGRAM_ID){
				 appAnna = createAnnGrid(true,false,true,false);
				return [appAnna];
			}
		}],
		formButtons : [{
			text : '提交审批',
			fn : function(formPanel,basicForm){
					 Ext.Msg.wait('正在处理，请稍后......','系统提示');
					Ext.Ajax.request({
						url : basepath + '/alianceProgramInfoAction!initFlow.json',
						method : 'GET',
						params : {
							instanceid:formPanel.getForm().getValues().ID, //将id传给后台关联流程的实例号（唯一）
							name:formPanel.getForm().findField("ALIANCE_PROGRAM_NAME").getValue()
						},
						waitMsg : '正在提交申请,请等待...',										
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
		}]
	},{
		title : '退出',
		type : 'form',
		hideTitle : true,
		autoLoadSeleted : true,
		groups : [{
			columnCount:2,
			fields:[
			        {name:'ID',text:'编号',hidden:true},
			        {name:'ALIANCE_PROGRAM_ID',text:'联盟商代码',disabled:true},
					{name:'ALIANCE_PROGRAM_NAME',text:'联盟商名称',disabled:true},
					{name:'ALIANCE_PROG_LEVEL',text:'联盟商等级',disabled:true,allowBlank:false,translateType:'ALIANCE_PROG_LEVEL'},
					{name:'ALIANCE_EN_NAME',text:'英文名称',disabled:true},
					{name:'CORP_NO',text:'法人代码',disabled:true},     
					{name:'ECONOMIC_TYPE',text:'经济类型',allowBlank:false,translateType:'DEM0200002',disabled:true},
					{name:'ORGAN_CODE',text:'组织机构代码',allowBlank:false,disabled:true},
					{name:'COMPANY_TYPE',text:'公司类型',allowBlank:false,translateType:'PAR2100001',disabled:true},
					{name:'SERVICE_CHARACT',text:'服务特性',allowBlank:false,disabled:true,translateType:'SERVICE_CHARACT'},
					{name:'ORG_NAME',text:'服务范围',xtype: 'wcombotree',editable:false,allowBlank:false,
						showField:'text',disabled:true,
						innerTree:'DATASETMANAGERTREE',hideField:'UNITID'},	 
					{name:'START_DATE',text:'合作开始日期',xtype:'datefield', format:'Y-m-d',disabled:true},
					{name:'END_DATE',text:'合作结束日期',xtype:'datefield', format:'Y-m-d',disabled:true}
			],
			fn : function(ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ALIANCE_PROG_LEVEL,
					ALIANCE_EN_NAME,CORP_NO,ECONOMIC_TYPE,COMPANY_TYPE,SERVICE_CHARACT,
					SERVICE_RANGE1,START_DATE,END_DATE,CALL_NUM,POST_NO,REGIST_CAPITAL,
					DELAY_END_DATE){
				return [ALIANCE_PROGRAM_ID,ALIANCE_PROGRAM_NAME,ALIANCE_PROG_LEVEL,
						ALIANCE_EN_NAME,CORP_NO,ECONOMIC_TYPE,COMPANY_TYPE,SERVICE_CHARACT,
						SERVICE_RANGE1,START_DATE,END_DATE,CALL_NUM,POST_NO,REGIST_CAPITAL,
						DELAY_END_DATE];
			}
		},{
			columnCount:1,
			fields:[{name:'OUT_REASON',text:'退出原因',xtype:'textarea'}],
			fn:function(OUT_REASON){
				return [OUT_REASON];
			}
		}],
		formButtons : [{
			text : '退出',
			fn : function(formPanel,basicForm){
				var id = this.contentPanel.getForm().getValues().ID;
				var reason= this.contentPanel.getForm().getValues().OUT_REASON;
				Ext.Ajax.request({
					url : basepath + '/alianceProgramInfoAction!outAliance.json',
					method : 'POST',
					params : {
						'id' :id,
						'reason' : reason
					},
					success : function() {
						 Ext.Msg.alert('提示','操作成功！');
						 reloadCurrentData();
					}
				});
			}
		}]
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
	}];
	
	var tbar=[{
		  text:'审批提交',
		  handler:function(){
			  showCustomerViewByTitle('审批');	
		  }
	    },{
	    	text:'退出',
	    	handler:function(){
	    		 showCustomerViewByTitle('退出');	
	    	}
	    },{
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
					Ext.MessageBox.confirm('系统提示信息','确认进行删除操作吗？',
					function(buttonobj) {
						if (buttonobj == 'yes'){
							if(getSelectedData().data.STATE = '02'){
								Ext.Msg.alert('提示','审批中的信息不能被删除!');
								return false;
							}
							Ext.Ajax.request({
								url : basepath+ '/alianceProgramInfoAction!batchDestroy.json',
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
							});
						}
				 }, this);
				}
			}
		
		}
		}];
	/**修改和详情面板滑入之前判断是否选择了数据，如果以选择，加载查询附件列表信息**/
	var beforeviewshow = function(view){
		if(view == getEditView()||view == getDetailView()){
				if(getSelectedData() == false){
					Ext.Msg.alert('提示','请选择一条数据');
					return false;
				}else{//加载数据
					if(view == getEditView()&&getSelectedData().data.STATE=='02'){
						Ext.Msg.alert('提示','审批中的数据不能修改！');
						return false;
					}
					var	messageIdStr = getSelectedData().data.ID;
				    uploadForm.relaId = messageIdStr;
	                uploadForm.modinfo = 'aliance';
	                var condi = {};
	                condi['relationInfo'] = messageIdStr;
	                condi['relationMod'] = 'aliance';
	                Ext.Ajax.request({
	                    url:basepath+'/queryanna.json',
	                    method : 'GET',
	                    params : {
	                        "condition":Ext.encode(condi)
	                    },
	                    failure : function(a,b,c){
	                        Ext.MessageBox.alert('查询异常', '查询失败！');
	                    },
	                    success : function(response){
	                        var anaExeArray = Ext.util.JSON.decode(response.responseText);
	                        if(view == getEditView()){
	                        	editAnna.store.loadData(anaExeArray.json.data);
		                        editAnna.getView().refresh();
	                        }else{
	                        	detailAnna.store.loadData(anaExeArray.json.data);
		                        detailAnna.getView().refresh();
	                        }
	                    }
	                });
				}		
		}
		if(view == getCreateView()){
			uploadForm.relaId = '';
			uploadForm.modinfo = 'aliance';
			createAnna.tbar.setDisplayed(false);
		}
		if(view._defaultTitle=='审批'){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}else{
				var	state = getSelectedData().data.STATE;
				if(state!='01'){
					Ext.Msg.alert('提示','只能选择未审批的数据!');
					return false;
				}//加载数据
				var	messageIdStr = getSelectedData().data.ID;
			    uploadForm.relaId = messageIdStr;
                uploadForm.modinfo = 'aliance';
                var condi = {};
                condi['relationInfo'] = messageIdStr;
                condi['relationMod'] = 'aliance';
                Ext.Ajax.request({
                    url:basepath+'/queryanna.json',
                    method : 'GET',
                    params : {
                        "condition":Ext.encode(condi)
                    },
                    failure : function(a,b,c){
                        Ext.MessageBox.alert('查询异常', '查询失败！');
                    },
                    success : function(response){
                        var anaExeArray = Ext.util.JSON.decode(response.responseText);
                        	appAnna.store.loadData(anaExeArray.json.data);
	                        appAnna.getView().refresh();
                       
                    }
                });
			
			}
		}if(view._defaultTitle=='退出'){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}else{
				var	state = getSelectedData().data.STATE;
				if(state!='03'){
					Ext.Msg.alert('提示','只能选择审批通过的数据!');
					return false;
				}
			}
		}
	};	
	/**数据提交之后：锁定结果列表，如果是新增面板，返回新增的信息id，显示附件列表的tbar**/
	var afertcommit = function(){
		lockGrid();//锁定结果列表
		if(getCurrentView() == getCreateView()){
			Ext.Ajax.request({
				url : basepath+'/session-info!getPid.json',
				method : 'GET',
				success : function(a,b,v) {
				    var noticeIdStr = Ext.decode(a.responseText).pid;
				    getCurrentView().setValues({
				    	ID:noticeIdStr
				    });
				    uploadForm.relaId = noticeIdStr;
				    createAnna.tbar.setDisplayed(true);
				}
			});
		}
	};
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	