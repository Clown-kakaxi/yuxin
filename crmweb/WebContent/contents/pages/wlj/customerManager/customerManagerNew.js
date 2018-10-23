/**
 * 客户经理信息管理
 */   

imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',//导出
		        '/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldx.js'// 机构放大镜
		        ]);
    
    var url = basepath+'/CustomerManagerInfoAction1.json';
    var comitUrl=basepath+'/CustomerManagerInfoAction1!saveData.json';
    var needCondition = true;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = true;
   // WLJUTIL.alwaysLockCurrentView = true;
	var lookupTypes=[
	                 
	                 ];
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
	
	var treeCfgs = [ {
		key : 'DATASETMANAGERTREE1',
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

	
	var fields = [
				  {name:'USER_ID',hidden:true},
				  {name:'CUST_MANAGER_ID',text:'客戶经理编号',dataType:'string', searchField: true},
				  {name:'CUST_MANAGER_NAME',text:'客户经理名称',dataType:'string',searchField: true},
				  {name:'ORG_NAME',text:'归属机构',hiddenName:'ORG_ID',xtype:'orgchoose',singleSelected:true,searchField: true},
				  {name:'DPT_NAME',text:'归属部门',dataType:'string',searchField: false},
				  {name:'EDUCATION',text:'学历',dataType:'string',searchField: false},
				  {name:'BIRTHDAY',text:'出生日期',dataType:'date',searchField: false},
				  {name:'ENTRANTS_DATE',text:'入行时间',dataType:'date',searchField: false},
				  {name:'POSITION_TIME',text:'任职时间',dataType:'string',searchField: false},
				  {name:'FINANCIAL_JOB_TIME',text:'金融从业时间',dataType:'string',searchField: false},
				  {name:'CUST_MANAGER_LEVEL',text:'客户经理分级',dataType:'string',searchField: false},
				  {name:'EVA_RESULT',text:'历年考评结果',dataType:'string',searchField: false},
				  {name:'WORK_PERFORMANCE',text:'工作表现',dataType:'string',searchField: false},
				  {name:'AWARD',text:'所获奖励',dataType:'string',searchField: false},
				  {name:'POSITION_CHANGE',text:'岗位变动',dataType:'string',searchField: false},
				  {name:'CERTIFICATE',text:'资格证书',dataType:'string',searchField: false,maxLength:20}
	              ];
	
	var tbar = [{
		text:'客户经理视图',
		hidden:JsContext.checkGrant('custManager_view'),
		handler:function(){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择数据！');
				return false;
			}
			var mgrId = getSelectedData().data.CUST_MANAGER_ID;
			var mgrName = getSelectedData().data.CUST_MANAGER_NAME;
			parent.Wlj.ViewMgr.openViewWindow(3,mgrId,mgrName);
		}
	},{
		text : '退出客户经理',
		hidden:JsContext.checkGrant('custManager_exit'),
		handler : function(){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据！');
				return false;
			}else{
				Ext.MessageBox.confirm( "提示" , "确定退出该客户经理？" ,function (e){
					if(e=='yes'){
						Ext.Ajax.request({
							url:basepath + '/CustomerManagerInfoAction1!cancelCustMgr.json',
							method:"POST",
							params : {"CUST_MANAGER_ID":getSelectedData().data.USER_ID},
							success:function(){
								Ext.Msg.alert("提示","操作成功");
								reloadCurrentData();
							},
							failure:function(response){
								var resultArray=Ext.util.JSON.decode(response.status);
								if (resultArray == 403) {
										Ext.Msg.alert('提示',
												response.responseText);
									}else {
										Ext.Msg.alert('提示','操作失败，失败原因：'+ response.responseText);
									}
							}
						});
						
					}
				}); 
			}
		}
	}
	,new Com.yucheng.crm.common.NewExpButton({
		 hidden:JsContext.checkGrant('mgrInfo_export'),
		 formPanel : 'searchCondition',
		    url : basepath+'/CustomerManagerInfoAction1.json'
    })];
	
	
	
	var customerView=[{
		title:'修改客户经理信息',
		hideTitle:JsContext.checkGrant('custManager_modify'),
		autoLoadSeleted:true,
		type:'form',
		groups:[{
			fields:['CUST_MANAGER_ID','CUST_MANAGER_NAME','ORG_NAME','DPT_NAME','EDUCATION','BIRTHDAY','ENTRANTS_DATE','POSITION_TIME'
			        ,'FINANCIAL_JOB_TIME','CUST_MANAGER_LEVEL','EVA_RESULT','WORK_PERFORMANCE','AWARD','POSITION_CHANGE'],
			fn:function(CUST_MANAGER_ID,CUST_MANAGER_NAME,ORG_NAME,DPT_NAME,EDUCATION,BIRTHDAY,ENTRANTS_DATE,POSITION_TIME
			        ,FINANCIAL_JOB_TIME,CUST_MANAGER_LEVEL,EVA_RESULT,WORK_PERFORMANCE,AWARD,POSITION_CHANGE){
				CUST_MANAGER_ID.readOnly=true;
				CUST_MANAGER_ID.cls='x-readOnly';
				CUST_MANAGER_NAME.readOnly=true;
				CUST_MANAGER_NAME.cls='x-readOnly';
				ORG_NAME.readOnly=true;
				ORG_NAME.cls='x-readOnly';
				DPT_NAME.readOnly=true;
				DPT_NAME.cls='x-readOnly';
				EDUCATION.readOnly=true;
				EDUCATION.cls='x-readOnly';
				BIRTHDAY.readOnly=true;
				BIRTHDAY.cls='x-readOnly';
				ENTRANTS_DATE.readOnly=true;
				ENTRANTS_DATE.cls='x-readOnly';
				POSITION_TIME.readOnly=true;
				POSITION_TIME.cls='x-readOnly';
				FINANCIAL_JOB_TIME.readOnly=true;
				FINANCIAL_JOB_TIME.cls='x-readOnly';
				CUST_MANAGER_LEVEL.readOnly=true;
				CUST_MANAGER_LEVEL.cls='x-readOnly';
				EVA_RESULT.readOnly=true;
				EVA_RESULT.cls='x-readOnly';
				WORK_PERFORMANCE.readOnly=true;
				WORK_PERFORMANCE.cls='x-readOnly';
				AWARD.readOnly=true;
				AWARD.cls='x-readOnly';
				POSITION_CHANGE.readOnly=true;
				POSITION_CHANGE.cls='x-readOnly';
				return [CUST_MANAGER_ID,CUST_MANAGER_NAME,ORG_NAME,DPT_NAME,EDUCATION,BIRTHDAY,ENTRANTS_DATE,POSITION_TIME
				        ,FINANCIAL_JOB_TIME,CUST_MANAGER_LEVEL,EVA_RESULT,WORK_PERFORMANCE,AWARD,POSITION_CHANGE];
			}
		},{ 
			columnCount:0.95,
			fields:['CERTIFICATE'],
			fn:function(CERTIFICATE){
				return [CERTIFICATE];
			}
		}],
		formButtons:[{
    		text : '保存',
    		hidden:JsContext.checkGrant('mgrInfo_save'),
    		fn : function(contentPanel, basicForm){
    			if (!basicForm.isValid()) {
    	    		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
    	    		 return false;
    	    	}
    			var commitData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
                Ext.Ajax.request({
                    url:basepath+'/CustomerManagerInfoAction1!saveData.json',
                    method: 'POST',
                    params: commitData,
                    success: function(response) {
                    	Ext.Msg.alert('提示', '操作成功');
                        reloadCurrentData();
                    },
                    failure: function(){
                    	Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
                    }
                });
                        
    		}
    	},{
			text : '提交',
			hidden:JsContext.checkGrant('mgrInfo_commit'),
			fn : function(formPanel, basicForm){
				if (!basicForm.isValid()) {
   	    		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
   	    		 return false;
   	    	    }
				var commitData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
				Ext.Ajax.request({
					url : basepath + '/CustomerManagerInfoAction1!initFlow.json',
					method : 'GET',
					params : commitData,
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
		
	}];
	
	
    
//	var editFormCfgs = {
//			formButtons : [{
//				text : '提交',
//				//hidden:JsContext.checkGrant('orgAchHis'),
//				fn : function(formPanel, basicForm){
//					Ext.Ajax.request({
//						url : cUrl.split('!')[0]+'!getPid.json',
//						method : 'GET',
//						success : function(response) {
//							var nodeArra = Ext.util.JSON.decode(response.responseText);
//							tempPid = nodeArra.pid;
//							//lockGrid();
//							//showCustomerViewByTitle('用户角色配置');
//						}
//					});
//					
//				}
//			}]
//		};
	
	var beforeviewshow=function(view){
		if(view._defaultTitle == '修改客户经理信息'){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}
		
		}
	};
	
	var tempPid = '';
	/**
	 * 数据提交之后触发,系统提供listener事件方法
	 * @param {} data
	 * @param {} cUrl
	 * @param {} result
	 */
//	var afertcommit = function(data, cUrl, result){
//		var tempView = getCurrentView();
//		if(tempView.baseType == 'editView'){
//			Ext.Ajax.request({
//				url : cUrl.split('!')[0]+'!getPid.json',
//				method : 'GET',
//				success : function(response) {
//					var nodeArra = Ext.util.JSON.decode(response.responseText);
//					tempPid = nodeArra.pid;
//					lockGrid();
//					//showCustomerViewByTitle('用户角色配置');
//				}
//			});
//		}
//	};
	
	
	
	
	
	
	
