/**
 * 我的营销活动
 * hujun
 * 2014-07-02
 * */

	imports([
//		'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
//		'/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js',
//		'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',
//		'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
//		'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
//		'/contents/pages/mktManage/mktBusiOppor/Com.yucheng.bcrm.common.MktActivityCommonQuery.js',
//		'/contents/pages/mktManage/mktBusiOppor/Com.yucheng.bcrm.common.MktTaskTargetCommonQuery.js'
		
        '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
	    '/contents/pages/mktManage/mktBusiOppor/Com.yucheng.bcrm.common.MktTaskTargetCommonQuery.js',
	    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js',//产品放大镜
        '/contents/pages/common/Com.yucheng.bcrm.common.MarketTarget.js',	//指标选择放大镜,
        '/contents/pages/mktManage/mktBusiOppor/Com.yucheng.bcrm.common.MktActivityCommonQuery.js',//营销活动放大镜
        '/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',//客户放大镜
        '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',//用户放大镜
        '/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldx.js', // 机构放大镜
        '/contents/pages/common/Com.yucheng.bcrm.common.MarketTask.js'	,
        '/contents/pages/common/Com.yucheng.bcrm.common.MktTaskTarget.js',//营销任务指标明细放大镜
//        '/contents/pages/common/Com.yucheng.bcrm.common.CreateMktOppor.js',
        '/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js'  //客户群放大镜
        ,'/contents/pages/wlj/mktManage/myMktActivity/Com.yucheng.bcrm.common.CreateMktOppor.js'//创建商机组件
	]);
	Ext.QuickTips.init();
	var createView=false;//是否需要新增form
	var editView=false;//是否需要修改form
	var detailView=false;//是否需要详情form
	var mktActiveId='';
	var appAnna = null;
	
	//要用到的变量以及参数
	var canSee = false;
	var activityId=false;//明细查询时用到的活动ID
	var lookupTypes=[
	                 'IF_FLAG',
	                 'STAGE_LEAVL',
	                 'MACTI_APPROVE_STAT',
	                 'MKT_WAY',
	                 'ACTI_TYPE',
	                 'FWQD',
	                 'MACTI_STATUS',
	                 'P_CUST_GRADE',
	                 'MODEL_TYPE'
	                 ];
	var url=basepath+'/MktMyActiListQueryAction.json';//查询URL
	var fields=[
	            {name:'MKT_ACTI_ID',text:'营销活动ID',hidden:true},
	            {name:'MKT_ACTI_NAME',text:'营销活动名称',searchField:true},
	            {name:'MKT_ACTI_TYPE',text:'营销活动类型',searchField:true,translateType:'ACTI_TYPE'},
	            {name:'MKT_ACTI_STAT',text:'营销活动状态',searchField:true,translateType:'MACTI_STATUS'},
	            {name:'CUST_NUM',text:'关联客户数',dataType:'numberNoDot'},
	            {name:'PRODUCT_NUM',text:'产品数',dataType:'numberNoDot'},
	            {name:'PSTART_DATE',text:'计划开始时间'},
	            {name:'PEND_DATE',text:'计划结束时间'},
	            {name:'MKT_ACTI_COST',text:'预算费用',dataType:'money'},
	            {name:'CREATE_DATE',text:'创建日期',searchField:true,xtype:'datefield',format:'Y-m-d'},
	            {name:'MKT_ACTI_MODE',text:'营销方式',hidden:true,allowBlank:false,resutlWidth:150,translateType : 'MKT_WAY'},
				{name:'ACTI_CUST_DESC',text:'涉及客户群描述',hidden:true,allowBlank:false,xtype:'textarea',resultWidth:350},		
				{name:'ACTI_OPER_DESC',text:'涉及执行人描述',hidden:true,allowBlank:false,xtype:'textarea',resultWidth:350},		
				{name:'ACTI_PROD_DESC',text:'涉及产品描述',hidden:true,xtype:'textarea',resultWidth:350},
				{name:'MKT_ACTI_AIM',text:'营销活动目的',hidden:true,xtype:'textarea',resultWidth:350},
				{name:'MKT_ACTI_ADDR',text:'活动地点',hidden:true,allowBlank:false,xtype:'textarea',resultWidth:350},
				{name:'ACTI_REMARK',text:'备注',hidden:true,xtype:'textarea',resultWidth:350},
				{name:'MKT_ACTI_CONT',text:'营销活动内容',hidden:true,allowBlank:false,xtype:'textarea',resultWidth:350}
	            ];
	
	var tbar=[{
		text:'活动明细',
		handler:function(){
			hideCurrentView();
			if(!getSelectedData()){
				Ext.Msg.alert('提示','请选择一条数据进行操作！');
				return false;
			}else{
				if(getSelectedData().data.MKT_ACTI_STAT == 3)
					canSee = true;
				else{
					canSee = false;
				}
				showCustomerViewByTitle('营销活动明细表');
			}
		}
	},{
		text:'创建商机',
		handler:function(){
			if(!getSelectedData()){
				Ext.Msg.alert('提示','请选择一条数据进行操作！');
				return false;
			}
			var viewWin = new Ext.Window({
							title : '创建商机',
							width : 800,
							height : 480,
							closeAction : 'hide',
							modal : false, // 模态窗口
							plain : true,
							layout : 'fit',
							collapsible : true,
							maximizable : true,
							items : [{xtype:'createMktOppor'}]
						});
			viewWin.show();
		}
	},{
		text:'详情',
		handler:function(){
			hideCurrentView();
			if(!getSelectedData()){
				Ext.Msg.alert('提示','请选择一条数据进行操作！');
				return false;
			}else{ 
				mktActiveId=getSelectedData().data.MKT_ACTI_ID;
				showCustomerViewByTitle('基本信息');
			}
		}
	}];
	
	var customerView=[{
		title : '营销活动明细表',
		url : basepath + '/MktMyActiListAction.json',
		type : 'grid',
		hideTitle: true,
		pageable : true,
		frame : true,
		fields : {
			fields : [
			            {name:'MY_ACTI_ID',text:'活动编号',hidden:true},
			            {name:'MY_ACTI_NAME',text:'活动名称',searchField:true},
			            {name:'MKT_ACTI_ID',text:'所属主营销活动编号',hidden:true},
			            {name:'MKT_ACTI_NAME',text:'所属主营销活动名称'},
			            {name:'MKT_ACTI_TYPE',text:'活动类型',hidden:true},
			            {name:'MKT_ACTI_STAT',text:'活动状态',hidden:true},
			            {name:'MKT_ACTI_AIM',text:'活动目的',hidden:true},
			            {name:'MKT_ACTI_CONT',text:'活动内容',hidden:true},
			            {name:'PSTART_DATE',text:'计划开始时间',hidden:true},
			            {name:'PEND_DATE',text:'计划结束时间',hidden:true},
			            {name:'MKT_ACTI_COST',text:'费用预算',hidden:true},
			            {name:'CUST_ID',text:'客户号'},
			            {name:'CUST_NAME',text:'客户名称'},
			            {name:'PRODUCT_ID',text:'产品编号'},
			            {name:'PRODUCT_NAME',text:'产品名称'},
			            {name:'EXECUTOR_ID',text:'活动执行人员编号'},
			            {name:'EXECUTOR_NAME',text:'活动执行人名称'},
			            {name:'PROGRESS_STAGE',text:'进展阶段',translateType:'STAGE_LEAVL',
			            	renderer:function(v){
								if(v == "0") return "未开始";
								if(v == "2") return "成功完成";
								if(v == "3") return "失败完成";
								if(v == "1") return "执行中";
								
							}},
			            {name:'IS_CRE_CHANCE',text:'是否已创建商机',translateType:'IF_FLAG',
								renderer:function(v){
									if(v == "0") return "否";
									if(v == "1") return "是";
									
								}},
			            {name:'USER_NAME',text:'创建人'},
			            {name:'CREATE_DATE',text:'创建日期',searchField:true,xtype:'datefield',format:'Y-m-d'}
			            ],
					fn : function(MY_ACTI_ID,MY_ACTI_NAME, MKT_ACTI_ID,MKT_ACTI_NAME,MKT_ACTI_TYPE,
							MKT_ACTI_STAT,MKT_ACTI_AIM,MKT_ACTI_CONT,PSTART_DATE,PEND_DATE,MKT_ACTI_COST,
							CUST_NAME,EXECUTOR_ID,EXECUTOR_NAME,PROGRESS_STAGE,IS_CRE_CHANCE,USER_NAME,CREATE_DATE){
						return [MY_ACTI_ID,MY_ACTI_NAME, MKT_ACTI_ID,MKT_ACTI_NAME,MKT_ACTI_TYPE,
								MKT_ACTI_STAT,MKT_ACTI_AIM,MKT_ACTI_CONT,PSTART_DATE,PEND_DATE,MKT_ACTI_COST,
								CUST_NAME,EXECUTOR_ID,EXECUTOR_NAME,PROGRESS_STAGE,IS_CRE_CHANCE,USER_NAME,CREATE_DATE];
				}
				},
		gridButtons : [{
			text:'查看明细记录',
			fn:function(grid){ 
				var selectLength = grid.getSelectionModel().getSelections().length;
				if(selectLength != 1){
					Ext.Msg.alert('提示','请选择需一条记录!');
				} else {
					showCustomerViewByTitle('活动明细记录表');
				}
				
			}
		}]
	},{
		title : '活动明细记录表',
		url : basepath + '/mktMyActiDetailManage.json',
		type : 'grid',
		hideTitle: true,
		pageable : true,
		frame : true,
		fields : {
			fields : [{name:'RECORD_ID',text:'ID',hidden:true},
			          {name:'MKT_ACTI_ID',text:'营销活动id',hidden:true},
			          {name:'ACTI_DATE',text:'活动日期',xtype:'datefield',format:'Y-m-d'},
			          {name:'ACTI_CONT',text:'活动内容'},
			          {name:'EXECUTOR_NAME',text:'活动执行人'},
			          {name:'EXECUTOR_ID',text:'活动执行人ID',hidden:true},
			          {name:'CUST_ID',text:'客户ID'},
			          {name:'CUST_NAME',text:'客户名称'},
			          {name:'ACTI_RESULT',text:'活动结果'},
			          {name:'FOLLOW_EVENT',text:'待跟进事项'},
			          {name:'PROGRESS_STAGE',text:'进展阶段',translateType:'STAGE_LEAVL'},
			          {name:'CREATE_USER',text:'创建人Id',hidden:true},
			          {name:'USER_NAME',text:'创建人'},
			          {name:'CREATE_DATE',text:'创建日期',xtype:'datefield',format:'Y-m-d'}],
					fn : function(RECORD_ID,MKT_ACTI_ID, ACTI_DATE,ACTI_CONT,EXECUTOR_NAME,
							EXECUTOR_ID,CUST_NAME,ACTI_RESULT,FOLLOW_EVENT,PROGRESS_STAGE,CREATE_USER,
							USER_NAME,CREATE_DATE,CUST_ID){
						RECORD_ID.hidden=true;
						CREATE_USER.hidden=true;
						MKT_ACTI_ID.hidden=true;
						EXECUTOR_ID.hidden=true;
						CUST_ID.hidden=true;
						return [RECORD_ID,MKT_ACTI_ID, ACTI_DATE,ACTI_CONT,EXECUTOR_NAME,
								EXECUTOR_ID,CUST_NAME,ACTI_RESULT,FOLLOW_EVENT,PROGRESS_STAGE,CREATE_USER,
								USER_NAME,CREATE_DATE,CUST_ID];
				}
				},
		gridButtons : [{
			text:'新增',
			id:'001',
			fn : function(grid){
					showCustomerViewByTitle('新增活动记录');	
				}
			},{
				text:'修改',
				id:'002',
				fn:function(grid){ 
					var selectLength = grid.getSelectionModel().getSelections().length;
					if(selectLength != 1){
						Ext.Msg.alert('提示','请选择需要一条修改的记录!');
					} else {
						showCustomerViewByTitle('修改活动记录');
					}
					
				}
			},{
		    	text:'删除',
		    	id:'003',
		    	fn:function(grid){
					 var selectLength = grid.getSelectionModel().getSelections().length;
					 var selectRe;
					 var idStr = '';
					if(selectLength < 1){
						Ext.Msg.alert('提示','请选择需要删除的记录!');
					} else {
						if(selectLength>0){
						idStr = grid.getSelectionModel().getSelections()[0].data.RECORD_ID;
						}
						for(var i = 1; i<selectLength;i++)
						{
							selectRe = grid.getSelectionModel().getSelections()[i];
							idStr 	+= ','+ selectRe.data.RECORD_ID;
						}						
						Ext.Ajax.request({
							url : basepath
							+ '/mktMyActiDetailManage!batchDestroy.json?idStr='+ idStr,
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								Ext.Msg.alert('提示', '操作成功');
								getCustomerViewByTitle('活动明细记录表').grid.store.reload();
							},
							failure : function(response) {
								var resultArray = Ext.util.JSON.decode(response.status);
								if(resultArray == 403) {
							           Ext.Msg.alert('提示', response.responseText);
							  } else {

								Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
								getCustomerViewByTitle('活动明细记录表').grid.store.reload();
							  }
							}
						});	
					}}
		      },{
		    	  text:'返回',
		    	  fn:function(){
		    		  showCustomerViewByTitle('营销活动明细表');
		    	  }
		      }]
	},{
		title : '新增活动记录',
		type : 'form',
		hideTitle : true,
		groups : [{
			columnCount : 2 ,
			fields : [{name:'RECORD_ID',text:'ID'},
			          {name:'MKT_ACTI_ID',text:'营销活动id'},
			          {name:'ACTI_DATE',text:'活动日期',editable : false,xtype:'datefield',format:'Y-m-d'},
			          {name:'EXECUTOR_NAME',text:'活动执行人',readOnly:true,editable : false},
			          {name:'EXECUTOR_ID',text:'活动执行人ID'},
			          {name:'CUST_ID',text:'客户ID'},
			          {name:'CUST_NAME',text:'客户名称',readOnly:true,editable : false},
			          {name:'PROGRESS_STAGE',text:'进展阶段',translateType:'STAGE_LEAVL'},
			          {name:'CREATE_USER',text:'创建人Id'},
			          {name:'USER_NAME',text:'创建人'},
			          {name:'CREATE_DATE',text:'创建日期',xtype:'datefield',format:'Y-m-d'}],
					fn : function( RECORD_ID,MKT_ACTI_ID,
							ACTI_DATE,EXECUTOR_NAME,EXECUTOR_ID,
							CUST_ID,CUST_NAME,PROGRESS_STAGE,CREATE_USER,USER_NAME,CREATE_DATE){
								RECORD_ID.hidden=true;
								CREATE_USER.hidden=true;
								MKT_ACTI_ID.hidden=true;
								EXECUTOR_ID.hidden=true;
								CUST_ID.hidden=true;
								CREATE_USER.hidden=true;
								USER_NAME.hidden=true;
								CREATE_DATE.hidden=true;
						return [RECORD_ID,MKT_ACTI_ID,
								ACTI_DATE,EXECUTOR_NAME,EXECUTOR_ID,
								CUST_ID,CUST_NAME,PROGRESS_STAGE,CREATE_USER,USER_NAME,CREATE_DATE];
				}
				
		},{
			columnCount:1,
			fields:[ {name:'ACTI_CONT',text:'活动内容',xtype:'textarea',resultWidth:350},
			         {name:'ACTI_RESULT',text:'活动结果',xtype:'textarea',resultWidth:350},
			          {name:'FOLLOW_EVENT',text:'待跟进事项',xtype:'textarea',resultWidth:350}
			         ],
			fn:function(ACTI_CONT,ACTI_RESULT,FOLLOW_EVENT){
				return [ACTI_CONT,ACTI_RESULT,FOLLOW_EVENT];
			}
		}],
		formButtons : [{
       	 text:'保存',
    	 fn: function(formPanel,basicForm){
    	 if (!basicForm.isValid()) {
    		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
    		 return false;
    	 }
    	 var commintData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
 		 Ext.Msg.wait('正在保存，请稍后......','系统提示');
    	 Ext.Ajax.request({
				url : basepath + '/mktMyActiDetailManage!saveData.json?operate=add',
				params :commintData ,
				method : 'POST',
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function(response) {
					Ext.Msg.alert('提示', '操作成功!');
					getCustomerViewByTitle('活动明细记录表').grid.store.reload();
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
				       if(resultArray == 403) {
				           Ext.Msg.alert('提示', response.responseText);
				  } else{

					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
				}
				}
			});
    	 showCustomerViewByTitle('活动明细记录表');
     }
     },{
			text : '取消',
			fn : function(formPanel,basicForm){
			 		showCustomerViewByTitle('活动明细记录表');
			}
		}]	
	},{
		title :'修改活动记录',
		type : 'form',
		hideTitle : true,
		groups : [{
			columnCount : 2 ,
			fields : [{name:'RECORD_ID',text:'ID'},
			          {name:'MKT_ACTI_ID',text:'营销活动id'},
			          {name:'ACTI_DATE',text:'活动日期',xtype:'datefield',format:'Y-m-d'},
			          {name:'EXECUTOR_NAME',text:'活动执行人',readOnly:true,editable : false},
			          {name:'EXECUTOR_ID',text:'活动执行人ID'},
			          {name:'CUST_ID',text:'客户ID'},
			          {name:'CUST_NAME',text:'客户名称',readOnly:true,editable : false},
			          {name:'PROGRESS_STAGE',text:'进展阶段',translateType:'STAGE_LEAVL'},
			          {name:'CREATE_USER',text:'创建人Id'},
			          {name:'USER_NAME',text:'创建人'},
			          {name:'USER_NAME',text:'创建日期',xtype:'datefield',format:'Y-m-d'}],
					fn : function( RECORD_ID,MKT_ACTI_ID,
							ACTI_DATE,EXECUTOR_NAME,EXECUTOR_ID,
							CUST_ID,CUST_NAME,PROGRESS_STAGE,CREATE_USER,USER_NAME,CREATE_DATE){
								RECORD_ID.hidden=true;
								CREATE_USER.hidden=true;
								MKT_ACTI_ID.hidden=true;
								EXECUTOR_ID.hidden=true;
								CUST_ID.hidden=true;
								CREATE_USER.hidden=true;
								USER_NAME.hidden=true;
								CREATE_DATE.hidden=true;
						return [ RECORD_ID,MKT_ACTI_ID,
									ACTI_DATE,EXECUTOR_NAME,EXECUTOR_ID,
									CUST_ID,CUST_NAME,PROGRESS_STAGE,CREATE_USER,USER_NAME,CREATE_DATE];
				}
				
		},{
			columnCount:1,
			fields:[ {name:'ACTI_CONT',text:'活动内容',xtype:'textarea',resultWidth:350},
			         {name:'ACTI_RESULT',text:'活动结果',xtype:'textarea',resultWidth:350},
			          {name:'FOLLOW_EVENT',text:'待跟进事项',xtype:'textarea',resultWidth:350}
			         ],
			fn:function(ACTI_CONT,ACTI_RESULT,FOLLOW_EVENT){
				return [ACTI_CONT,ACTI_RESULT,FOLLOW_EVENT];
			}
		}],
		formButtons : [{
       	 text:'保存',
    	 fn: function(formPanel,basicForm){
    	 if (!basicForm.isValid()) {
    		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
    		 return false;
    	 }
    	 var commintData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
 		 Ext.Msg.wait('正在保存，请稍后......','系统提示');
    	 Ext.Ajax.request({
				url : basepath + '/mktMyActiDetailManage!saveData.json?operate=update',
				params :commintData ,
				method : 'POST',
				waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				success : function(response) {
					Ext.Msg.alert('提示', '操作成功!');
					getCustomerViewByTitle('活动明细记录表').grid.store.reload();
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
				       if(resultArray == 403) {
				           Ext.Msg.alert('提示', response.responseText);
				  } else{

					Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
				}
				}
			});
    	 showCustomerViewByTitle('活动明细记录表');
     }
     },{
			text : '取消',
			fn : function(formPanel,basicForm){
			 		showCustomerViewByTitle('活动明细记录表');
			}
		}]	
	},{
		title : '基本信息',
		type : 'form',
		hideTitle : true,
		autoLoadSeleted : true,
		groups:[{
				columnCount : 2 ,
				fields : [	
				          	{name:'MKT_ACTI_NAME',text:'营销活动名称',allowBlank:false},
				          	{name:'MKT_ACTI_TYPE',text:'营销活动类型',allowBlank:false,resutlWidth:150,translateType : 'ACTI_TYPE'},	
				          	{name:'MKT_ACTI_STAT',text:'营销活动状态',editable : false,allowBlank:false,translateType : 'MACTI_STATUS'},
				          	{name:'MKT_ACTI_MODE',text:'营销方式',allowBlank:false,resutlWidth:150,translateType : 'MKT_WAY'},
				          	{name:'MKT_ACTI_COST',text:'费用预算',allowBlank:false,viewFn:money('0,000.00')},
				          	{name:'PEND_DATE',text:'计划结束时间',allowBlank:false,xtype:'datefield',format:'Y-m-d'},
				          	{name:'PSTART_DATE',text:'计划开始时间',allowBlank:false,xtype:'datefield',format:'Y-m-d'},
				          	{name:'ASTART_DATE',text:'实际开始日期',xtype:'datefield',format:'Y-m-d',hidden:true},
				          	{name:'AEND_DATE',text:'实际结束日期',xtype:'datefield',format:'Y-m-d',hidden:true},
							{name:'CREATE_DATE',text:'创建日期',xtype:'datefield',format:'Y-m-d',hidden:true},
							{name:'MKT_ACTI_ID',text:'营销活动ID',hidden:true},
							{name:'CREATE_USER',text:'创建人编号',hidden:true},
							{name:'USERNAME',text:'创建人',hidden:true},
							{name:'MKT_APP_STATE',id:'MKT_APP_STATE',text:'审批状态',translateType : 'MACTI_APPROVE_STAT',hidden:true},
							{name:'UPDATE_DATE',text:'更新时间',xtype:'datefield',format:'Y-m-d',hidden:true},
							{name:'UPDATE_USER',text:'更新人',hidden:true}],
				fn : function(MKT_ACTI_NAME,MKT_ACTI_TYPE,MKT_ACTI_STAT ,MKT_ACTI_MODE,
						MKT_ACTI_COST,PSTART_DATE,PEND_DATE,ASTART_DATE,AEND_DATE,
						CREATE_DATE,MKT_ACTI_ID,CREATE_USER,USERNAME,MKT_APP_STATE,UPDATE_DATE,UPDATE_USER){
							MKT_ACTI_ID.hidden=true;
							ASTART_DATE.hidden=true;
							AEND_DATE.hidden=true;
							CREATE_DATE.hidden=true;
							CREATE_USER.hidden=true;
							UPDATE_DATE.hidden=true;
							UPDATE_USER.hidden=true;
							USERNAME.hidden=true;
							MKT_APP_STATE.hidden=true;
							MKT_ACTI_STAT.hidden=true;
							MKT_APP_STATE.value='1';
							MKT_ACTI_STAT.value='1';
					return [MKT_ACTI_NAME,MKT_ACTI_TYPE,MKT_ACTI_STAT ,MKT_ACTI_MODE,
					        MKT_ACTI_COST,PSTART_DATE,PEND_DATE,ASTART_DATE,AEND_DATE,
							CREATE_DATE,MKT_ACTI_ID,CREATE_USER,USERNAME,MKT_APP_STATE,UPDATE_DATE,UPDATE_USER];
				}
			},{
				columnCount : 1 ,
				fields : [	
				          	{name:'MKT_ACTI_ADDR',text:'活动地点',allowBlank:false,xtype:'textarea',resultWidth:350},
				          	{name:'MKT_ACTI_CONT',text:'营销活动内容',allowBlank:false,xtype:'textarea',resultWidth:350},
				          	{name:'ACTI_CUST_DESC',text:'涉及客户群描述',allowBlank:false,xtype:'textarea',resultWidth:350},		
							{name:'ACTI_OPER_DESC',text:'涉及执行人描述',allowBlank:false,xtype:'textarea',resultWidth:350},		
							{name:'ACTI_PROD_DESC',text:'涉及产品描述',xtype:'textarea',resultWidth:350},
							{name:'MKT_ACTI_AIM',text:'营销活动目的',xtype:'textarea',resultWidth:350},
							{name:'ACTI_REMARK',text:'备注',xtype:'textarea',resultWidth:350}
							],
				fn : function(MKT_ACTI_ADDR, MKT_ACTI_CONT,ACTI_CUST_DESC,ACTI_OPER_DESC,
						ACTI_PROD_DESC,MKT_ACTI_AIM,ACTI_REMARK){
					MKT_ACTI_ADDR.anchor = '95%';
					MKT_ACTI_CONT.anchor = '95%';
					ACTI_CUST_DESC.anchor = '95%';
					ACTI_OPER_DESC.anchor = '95%';
					ACTI_PROD_DESC.anchor = '95%';
					MKT_ACTI_AIM.anchor = '95%';
					ACTI_REMARK.anchor = '95%';
					return [MKT_ACTI_ADDR, MKT_ACTI_CONT,ACTI_CUST_DESC,ACTI_OPER_DESC,
						ACTI_PROD_DESC,MKT_ACTI_AIM,ACTI_REMARK];
				}
			}],
		formButtons:[{
    			text : '下一步',
    			fn : function(formPanel,basicForm) {
    				showCustomerViewByTitle('关联产品信息');
    		}
    		},{
    			text : '关  闭',
    			fn : function() {
    				hideCurrentView();
    			}
    		}]
	},{
		title : '关联产品信息',
		url : basepath + '/mktactivityrelateinfoaction.json?querysign=prod',
		type : 'grid',
		hideTitle: true,
		pageable : true,
		frame : true,
		fields : {
			fields : [{name : 'AIM_PROD_ID',text:'目标产品ID'
					},{name : 'CREATE_USER',text:'创建人ID'
					},{name:'MKT_ACTI_ID',text:'营销活动ID'
					},{name:'PRODUCT_ID',text:'产品编号'
					},{name:'PRODUCT_NAME',text:'产品名称'
					},{name : 'CREATE_USER_NAME',text:'创建人'
					},{name : 'CREATE_DATE',text : '创建日期'
					}],
					fn : function(AIM_PROD_ID,CREATE_USER,MKT_ACTI_ID,
							PRODUCT_ID,PRODUCT_NAME,CREATE_USER_NAME,CREATE_DATE){
						AIM_PROD_ID.hidden=true;
						CREATE_USER.hidden=true;
						MKT_ACTI_ID.hidden=true;
						return [AIM_PROD_ID,CREATE_USER,MKT_ACTI_ID,
								PRODUCT_ID,PRODUCT_NAME,CREATE_USER_NAME,CREATE_DATE];
				}
				}
	},{
		title : '关联客户信息',
		url : basepath+'/mktactivityrelateinfoaction.json?querysign=customer',
		type : 'grid',
		pageable : true,
		frame : true,
		hideTitle: true,
		fields : {
			fields : [{name :'AIM_CUST_ID',text:'目标客户ID',hidden:true
					},{name :'CUST_ID',text:'客户编号'
					},{name :'CUST_NAME',text:'客户名称'
					},{name :'MGR_NAME',text : '主办客户经理'
					},{name:'INSTITUTION_NAME',text:'主办机构'
					},{name:'AIM_CUST_SOURCE',text:'目标客户来源',translateType : 'AIM_CUST_SOURCE',renderer:function(value){
						if(value=="01"){
							return "自定义筛选";
						}else if(value=="02"){
							return "客户群导入";
						}
						}
					},{name:'PROGRESS_STEP',text:'进展阶段',renderer:function(v){
						if(v == "0") return "未开始";
						if(v == "2") return "成功完成";
						if(v == "3") return "失败完成";
						if(v == "1") return "执行中";
						
					}
					},{name:'CREATE_USER_NAME',text:'创建人'
					},{name:'CREATE_DATE',text:'创建日期',xtype:'datafield',format:'Y-m_d'
					}],
					fn : function(AIM_CUST_ID, CUST_ID,CUST_NAME,MGR_NAME,
							INSTITUTION_NAME,AIM_CUST_SOURCE,PROGRESS_STEP,CREATE_USER_NAME,
							CREATE_DATE){
						return [AIM_CUST_ID, CUST_ID,CUST_NAME,MGR_NAME,
								INSTITUTION_NAME,AIM_CUST_SOURCE,PROGRESS_STEP,CREATE_USER_NAME,
								CREATE_DATE];
				}
				}
//	},{
//		title : '关联渠道信息',
//		url : basepath+'/mktactivityrelateinfoaction.json?querysign=chanel',
//		type : 'grid',
//		pageable : true,
//		frame : true,
//		hideTitle: true,
//		fields : {
//			fields : [{name :'ACTI_CHANNEL_ID',text:'目标渠道ID',hidden:true
//					},{name :'APP_CUST_LEVER',text:'',hidden:true
//					},{name :'CAHN_TEM_CONT',text:'',hidden:true
//					},{name :'CAHN_TEM_NAME',text : '',hidden:true
//					},{name:'CHANNEL_ID',text:'渠道编号',hidden:true
//					},{name:'CHANNEL_NAMES',text:'渠道名称'
//					},{name:'TEMPLETNAME',text:'模板名称'
//					},{name:'MKT_ACTI_ID',text:'营销活动编号',hidden:true
//					},{name:'CREATE_USER',text:'创建人编号'
//					},{name:'CREATE_USER_NAME',text:'创建人'
//					},{name:'CREATE_DATE',text:'创建日期',xtype:'datafield',format:'Y-m_d'
//					}],
//					fn : function(ACTI_CHANNEL_ID, APP_CUST_LEVER,CAHN_TEM_CONT,CAHN_TEM_NAME,
//							PRODUCT_ID,PRODUCT_NAME,TEMPLETNAME,MKT_ACTI_ID,CREATE_USER,
//							CREATE_USER_NAME,CREATE_DATE){
//						return [ACTI_CHANNEL_ID, APP_CUST_LEVER,CAHN_TEM_CONT,CAHN_TEM_NAME,
//								PRODUCT_ID,PRODUCT_NAME,TEMPLETNAME,MKT_ACTI_ID,CREATE_USER,
//								CREATE_USER_NAME,CREATE_DATE];
//				}
//				}
	},{
		title : '附件信息',
		type : 'form',
		hideTitle : true,
		groups : [{
			columnCount:1,
			fields:[{name:'MKT_ACTI_ID'}],
			fn:function(MKT_ACTI_ID){
				 appAnna = createAnnGrid(false,true,false,false);
				return [appAnna];
			}
		}],
		formButtons:[{
			text:'上一步',
			fn:function(){
				showCustomerViewByTitle("关联客户信息");
			}
		},{
			text:'关闭',
			fn:function(){
				hideCurrentView();
				reloadCurrentData();
			}
		}]
	}];	
	var viewshow = function(view){
		if(view._defaultTitle == '营销活动明细表'){
			view.setParameters({
				ActiId : getSelectedData().data.MKT_ACTI_ID
			});
		}
		if(view._defaultTitle == '活动明细记录表'){
			var tempGridView = getCustomerViewByTitle('营销活动明细表');
			var tempRecord = tempGridView.grid.getSelectionModel().getSelections()[0];
				if(canSee){
					Ext.getCmp('001').show();
					Ext.getCmp('002').show();
					Ext.getCmp('003').show();
				}else{
					Ext.getCmp('001').hide();
					Ext.getCmp('002').hide();
					Ext.getCmp('003').hide();
				}
			view.setParameters({
				myActiId : tempRecord.data.MY_ACTI_ID
			});
			
		}
		if(view._defaultTitle == '新增活动记录'){
			var tempGridView = getCustomerViewByTitle('营销活动明细表');
			var tempRecord = tempGridView.grid.getSelectionModel().getSelections()[0];
			var tempData = tempRecord.data;
			view.contentPanel.getForm().reset();
			view.contentPanel.getForm().findField('MKT_ACTI_ID').setValue(tempData.MY_ACTI_ID);
			view.contentPanel.getForm().findField('EXECUTOR_NAME').setValue(tempData.EXECUTOR_NAME);
			view.contentPanel.getForm().findField('EXECUTOR_ID').setValue(tempData.EXECUTOR_ID);
			view.contentPanel.getForm().findField('CUST_NAME').setValue(tempData.CUST_NAME);
			view.contentPanel.getForm().findField('CUST_ID').setValue(tempData.CUST_ID);
		}
		if(view._defaultTitle == '修改活动记录'){
			var tempGridView = getCustomerViewByTitle('活动明细记录表');
			var tempRecord = tempGridView.grid.getSelectionModel().getSelections()[0];
			view.contentPanel.getForm().loadRecord(tempRecord);
		}
		if(view._defaultTitle == '附件信息'){
			appAnna.toolbars[0].items.items[0].show();
			appAnna.toolbars[0].items.items[1].hide();
			appAnna.toolbars[0].items.items[2].hide();
			uploadForm.relaId = getSelectedData().data.MKT_ACTI_ID;
            uploadForm.modinfo = 'mktActive';
            var condi = {};
            condi['relationInfo'] =  getSelectedData().data.MKT_ACTI_ID;
            condi['relationMod'] = 'mktActive';
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

		if(view._defaultTitle == '关联产品信息'){
			view.setParameters({
				mktActiId:mktActiveId
			});
		}
		if(view._defaultTitle =='关联客户信息'){
			view.setParameters({
					mktActiId:mktActiveId
			});
		}
//		if(view._defaultTitle == '关联渠道信息'){
//			view.setParameters({
//				mktActiId:mktActiveId
//			});
//		}
	};
	
	var firstLayout1 = -1;//用于控制在自定义grid上添加查询form
	var buttons1 = new Ext.Panel({
		buttonAlign:'center',
		buttons:[{
    	  text:'上一步',
    	  handler:function(){
    		  showCustomerViewByTitle("基本信息");
    		  needReset = false;
    	  }
      	},{
    		text:'下一步',
    		handler:function(){
    			 showCustomerViewByTitle("关联客户信息");
    		}
     	},{
    		  text:'关闭',
    		  handler:function(){
    			  hideCurrentView();
    			  reloadCurrentData();
    		  }
      	}]
	});
	var firstLayout2 = -1;//用于控制在自定义grid上添加查询form
	var buttons2 = new Ext.Panel({
		buttonAlign:'center',
		buttons:[{
    	  text:'上一步',
    	  handler:function(){
    		  showCustomerViewByTitle("关联产品信息");
    	  }
      	},{
    		text:'下一步',
    		handler:function(){
    			 showCustomerViewByTitle("附件信息");
    		}
     	},{
		  text:'关闭',
		  handler:function(){
			  hideCurrentView();
			  reloadCurrentData();
		  }
      }]
	});
//	var firstLayout3 = -1;//用于控制在自定义grid上添加查询form
//	var buttons3 = new Ext.Panel({
//		buttonAlign:'center',
//		buttons:[{
//	    	  text:'上一步',
//	    	  handler:function(){
//	    		  showCustomerViewByTitle("关联客户信息");
//	    	  }
//	      },{
//	    		  text:'关闭',
//	    		  handler:function(){
//	    			  hideCurrentView();
//	    			  reloadCurrentData();
//	    		  }
//	      },{
//	    		text:'下一步',
//	    		handler:function(){
//	    			 showCustomerViewByTitle("附件信息");
//	    		}
//	    	  }]
//	});
	
	var beforeviewshow =function(view){
		if(view._defaultTitle == '关联产品信息'){
			if(firstLayout1 <0){
				firstLayout1++;
				view.remove(view.grid,false);
				view.doLayout();
				view.grid.setHeight(view.grid.getHeight() - 35);
				view.add({
					xtype:'panel',
					layout:'form',
					items:[view.grid,buttons1]
					});
					view.doLayout();
			}
		}
		if(view._defaultTitle =='关联客户信息'){
			if(firstLayout2 <0){
				firstLayout2++;
				view.remove(view.grid,false);
				view.doLayout();
				view.grid.setHeight(view.grid.getHeight() - 35);
				view.add({
					xtype:'panel',
					layout:'form',
					items:[view.grid,buttons2]
					});
					view.doLayout();
			}
		}
//		if(view._defaultTitle == '关联渠道信息'){
//			if(firstLayout3 <0){
//				firstLayout3++;
//				view.remove(view.grid,false);
//				view.doLayout();
//				view.grid.setHeight(view.grid.getHeight() - 35);
//				view.add({
//					xtype:'panel',
//					layout:'form',
//					items:[view.grid,buttons3]
//					});
//					view.doLayout();
//			}
//		}
	};
	
	
	
	