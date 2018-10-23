
    imports([
                '/FusionCharts/FusionCharts.js',
                '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
                '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js',//产品放大镜
		        '/contents/pages/common/Com.yucheng.bcrm.common.MarketTarget.js',	//指标选择放大镜,
		        '/contents/pages/mktManage/mktBusiOppor/Com.yucheng.bcrm.common.MktActivityCommonQuery.js',//营销活动放大镜
		        '/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',//客户放大镜
		        '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',//用户放大镜
		        '/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldx.js'//组织机构放大镜
		        ]);
    
    var url = basepath + '/mktBusiOpporFunnelQueryAction.json';
    var formCfgs = false;
    var needCondition = true;
    var needGrid = true;
    var createView = true;
    var editView = true;
    var detailView = true;
	var lookupTypes=[
	                 'BUSI_CHANCE_STAGE',        
	                 'BUSI_CHANCE_PROB',         
	                 'BUSI_CHANCE_STATUS',       
	                 'BUSI_CHANCE_SOURCE',       
	                 'BUSI_CHANCE_TYPE',         
	                 'CUSTOMER_STATUS',          
	                 'XD000080',
	                 'SALES_STAGE',
	                 'EXEC_WAY'
	                 ];
	var fields = [
				  {name:'ID',hidden:true},
				  {name:'OPPOR_END_DATE',text:'商机完成日期',searchField:true,gridField:false,dataType:'date',format : 'Y-m-d'},
				  {name:'OPPOR_START_DATE',text:'商机开始日期',searchField:true,gridField:false,dataType:'date',format : 'Y-m-d'},
				  {name:'CREATE_DATE_TIME',text:'创建时间',dataType:'date',searchField:true,gridField:false},
				  {name:'OPPOR_DUE_DATE',text:'商机有效期',searchField:true,gridField:false,dataType:'date',format : 'Y-m-d'},
				  {name:'EXECUTE_ORG_NAME',text:'商机执行机构',searchField:true,gridField:false,hideenName:'EXECUTE_ORG_ID',xtype:'orgchoose',singleSelect:true},
				  {name:'EXECUTE_USER_NAME',text:'商机执行人',searchField:true,gridField:false,hiddenName:'EXECUTE_USER_ID',xtype:'userchoose',singleSelect:true},
				  {name:'CUST_NAME',text:'客户名称',xtype:'customerquery',hiddenName:'CUST_ID',singleSelected: true,gridField:false,searchField:true},
				  {name:'F_CODE',text:'商机阶段编码',hidden:true},
				  {name:'F_C_V',text:'商机阶段'},
				  {name:'COUNT_STAGE',text:'销售阶段'},
				  {name:'COUNT_PERCENT',resutlFloat:'right',text:'达成概率'},
				  {name:'COUNT_NUMBER',text:'商机数量' ,viewFn:function(data){
					  //var F_CODE=getCurrentView().contentPanel.form.findField("F_CODE").getValue();
					  //alert(F_CODE);
					  return  "<h1 onclick='' style='color:red' >"+data+"</h1>";
				  }},
				  {name:'COUNT_AMOUNT',text:'预计金额',dataType:'number'},
				  {name:'COUNT_WEIGHT',text:'权重金额',dataType:'number'}
	              ];
	
	var tbar = [{
		text : '收起',
		handler : function(){
			collapseSearchPanel();
		}
	},{
		text : '展开',
		handler : function(){
			expandSearchPanel();
		}
	},{
		text:'展示销售漏斗',
		hidden:JsContext.checkGrant('opporFunnel_show'),
		handler:function(){
			var store = getResultStore();
			// 查询漏斗图形
			displayFunnel(store);
		}
	}];
	
	// 查询漏斗图形
	function displayFunnel(store) {
		var swfUrl = basepath + "/FusionCharts/Funnel.swf";
		var chart = new FusionCharts(swfUrl, "ChartId", "100%", "100%", "0", "0");
		chart.setJSONData(praseData(store));
		chart.render("chartdiv");
	}

	// 数据解析转换
	function praseData(store) {
		var arr=[];
		for(var i=0;i<store.data.items.length;i++){
			arr.push(store.data.items[i].data.F_C_V);
			arr.push(store.data.items[i].data.COUNT_NUMBER);
		}
		
		var caption = "";
		if (arr[1] == 0 && arr[3] == 0 && arr[5] == 0 && arr[7] == 0 && arr[9] == 0) {
			caption = "各阶段商机数量全为0，漏斗不能展示。";
		}
		var jsonData = {
			"chart" : {
				"manageresize" : "1",
				"caption" : caption,
				"subcaption" : "",
				"showpercentvalues" : "0",
				"decimals" : "2",
				"basefontsize" : "12",
				"issliced" : "1",
				"connectNullData" : "1"
			},
			"data" : [{
						"label" : arr[0],
						"value" : arr[1] == 0 ? "0.000001" : arr[1]
					}, {
						"label" : arr[2],
						"value" : arr[3] == 0 ? "0.000001" : arr[3]
					}, {
						"label" : arr[4],
						"value" : arr[5] == 0 ? "0.000001" : arr[5]
					}, {
						"label" : arr[6],
						"value" : arr[7] == 0 ? "0.000001" : arr[7]
					}, {
						"label" : arr[8],
						"value" : arr[9] == 0 ? "0.000001" : arr[9]
					}],
			"styles" : {
				"definition" : [{
							"type" : "font",
							"name" : "captionFont",
							"size" : "15"
						}],
				"application" : [{
							"toobject" : "CAPTION",
							"styles" : "captionFont"
						}]
			}
		};
		return jsonData;
	}
	
	// 销售漏斗图形
	var funnelPicPanel = new Ext.form.FormPanel({
				width : '27%',
				height : '100%',
				frame : true,
				autoScroll : true,
				region : 'west',
				split : true,
				text : '正在加载漏斗数据,请稍等...',
				html : '<div id="chartdiv" style="width:100%;height:100%"></div>'
			});
	
	var edgeVies = {
			left : {
				width : 300,
				layout : 'fit',
				title:'销售漏斗图形',
				items : [funnelPicPanel]
			}
		};
	
	var param='';
	/**
	 * 自定义
	 * @param data
	 * @param cUrl
	 * @returns
	 */
	var customerView=[{
		title:'展示商机列表',
		hideTitle:JsContext.checkGrant('opporList_show'),
		type:'grid',
		url:basepath
		+ '/mktBusiOpporListSuperLinkQueryAction.json',
		frame:true,
		fields : {fields:[
		  		  {name:'OPPOR_ID',text:'商机编号',allowBlank:false},
				  {name:'OPPOR_NAME',text:'商机名称',searchField:true,allowBlank:false},
				  {name:'OPPOR_STAT',text:'商机状态',renderer:function(value){
						var val = translateLookupByKey("BUSI_CHANCE_STATUS",value);
						return val?val:"";
				   },searchField:true,allowBlank:false},
				  {name:'OPPOR_STAGE',text:'商机阶段',renderer:function(value){
						var val = translateLookupByKey("BUSI_CHANCE_STAGE",value);
						return val?val:"";
				   },searchField:true,allowBlank:false},
				  {name:'OPPOR_SOURCE',text:'商机来源',renderer:function(value){
						var val = translateLookupByKey("BUSI_CHANCE_SOURCE",value);
						return val?val:"";
				   },searchField:true,allowBlank:false},
				  {name:'OPPOR_TYPE',text:'商机类型',renderer:function(value){
						var val = translateLookupByKey("BUSI_CHANCE_TYPE",value);
						return val?val:"";
				   },searchField:true,allowBlank:false},
				  {name:'OPPOR_START_DATE',text:'商机开始日期',format:'Y-m-d',searchField:true,allowBlank:false},
				  {name:'OPPOR_END_DATE',text:'商机完成日期',searchField:true,allowBlank:false},
				  {name:'OPPOR_DUE_DATE',text:'商机有效期',searchField:true,allowBlank:false},
				  {name:'EXECUTE_USER_ID',text:'商机执行人ID',allowBlank:false,hidden:true},
				  {name:'EXECUTE_USER_NAME',text:'商机执行人',hiddenName:'EXECUTE_USER_ID',allowBlank:false,xtype:'userchoose',singleSelect:true,searchRoleType:''},
				  {name:'EXECUTE_ORG_ID',text:'商机执行机构ID',allowBlank:false,hidden:true},
				  {name:'EXECUTE_ORG_NAME',text:'商机执行机构',hiddenName:'EXECUTE_ORG_ID',allowBlank:false,xtype:'orgchoose',singleSelected:true},
				  {name:'OPPOR_CONTENT',text:'商机内容',allowBlank:false,xtype:'textarea'},
				  {name:'CUST_NAME',text:'客户名称',allowBlank:false,xtype:'customerquery',hiddenName:'CUST_ID',singleSelected: true},
				  {name:'CUST_TYPE',text:'客户状态',renderer:function(value){
						var val = translateLookupByKey("CUSTOMER_STATUS",value);
						return val?val:"";
				   },allowBlank:false,readOnly:true},
				  {name:'CUST_CATEGORY',text:'客户类型',renderer:function(value){
						var val = translateLookupByKey("XD000080",value);
						return val?val:"";
				   },allowBlank:false,readOnly:true},
				  {name:'CUST_CONTACT_NAME',text:'客户联系人',allowBlank:false,readOnly:true},
				  {name:'PROD_NAME',text:'商机产品名称',dataType:'productChoose',hiddenName:'PROD_ID',searchField:true,allowBlank:false,singleSelect:true},
				  {name:'MKT_TARGET_NAME',text:'营销任务指标名称',xtype:'targetchoose',hiddenName:'TARGET_CODE',singleSelect: true},
				  {name:'MKT_ACTIV_NAME',text:'营销活动名称',xtype:'activityQuery',hiddenName:'MKT_ACTIV_CODE',singleSelect: true},
				  {name:'PLAN_AMOUNT',text:'预计金额',renderer:money('0,0.00'),align:'right'},
				  {name:'REACH_AMOUNT',text:'达成金额',renderer:money('0,0.00')},
				  {name:'REACH_PROB',text:'达成概率',translateType:'BUSI_CHANCE_PROB',searchField:true,allowBlank:false},
				  {name:'PLAN_COST',text:'费用预算',renderer:money('0,0.00'),resutlFloat:'right'},
				  {name:'CREATER_ID',text:'创建人ID',allowBlank:false,hidden:true},
				  {name:'CREATER_NAME',text:'创建人',allowBlank:false},
				  {name:'CREATE_ORG_ID',text:'创建机构ID',allowBlank:false,hidden:true},
				  {name:'CREATE_ORG_NAME',text:'创建机构',allowBlank:false},
				  {name:'CREATE_DATE_TIME',text:'创建时间',dataType:'string',allowBlank:false},
				  {name:'UPDATE_USER_NAME',text:'最近更新人',allowBlank:false},
				  {name:'UPDATE_ORG_NAME',text:'最近更新机构',dataType:'string',allowBlank:false},
				  {name:'UPDATE_DATE_TIME',text:'最近更新时间',allowBlank:false},
				  {name:'ASSIGN_OGR_ID',text:'待分配机构ID',allowBlank:false,hidden:true},
				  {name:'ASSIGN_ORG_NAME',text:'待分配机构',allowBlank:false,xtype:'orgchoose',singleSelect:true,hiddenName:'ASSIGN_OGR_ID'},
//				  {name:'CLAIM_USER_NAME',text:'认领人',allowBlank:false},
//				  {name:'CLAIM_ORG_NAME',text:'认领机构',allowBlank:false},
				  {name:'MEMO',text:'商机备注',xtype:'textarea'}
		        ]},
		gridButtons:[{
			text:'详情',
			hidden:JsContext.checkGrant('opporList_detail'),
			fn:function(grid){
				   var selectLength = grid.getSelectionModel().getSelections().length;
				   var selectRe= grid.getSelectionModel().getSelections()[0];
				   if (selectLength < 1) {
						Ext.Msg.alert('提示', '请先选择一条记录！');
						return false;
					} else if (selectLength > 1) {
						Ext.Msg.alert('提示', '只能选择一条记录！');
						return false;
					}
				   getCustomerViewByTitle('商机详情').contentPanel.form.loadRecord(selectRe);
				   getCustomerViewByTitle('商机详情').contentPanel.getForm().findField('PLAN_AMOUNT').setValue(Ext.util.Format.number(selectRe.data.PLAN_AMOUNT, '0,000.00'));
				   getCustomerViewByTitle('商机详情').contentPanel.getForm().findField('REACH_AMOUNT').setValue(Ext.util.Format.number(selectRe.data.REACH_AMOUNT, '0,000.00'));
				   getCustomerViewByTitle('商机详情').contentPanel.getForm().findField('PLAN_COST').setValue(Ext.util.Format.number(selectRe.data.PLAN_COST, '0,000.00'));
				   showCustomerViewByTitle('商机详情');
			}
		},{
			text:'销售活动',
			hidden:JsContext.checkGrant('opporList_saleActivity'),
			fn:function(grid){
				 var selectLength = grid.getSelectionModel().getSelections().length;
				 var selectRe= grid.getSelectionModel().getSelections()[0];
				 if (selectLength < 1) {
						Ext.Msg.alert('提示', '请先选择一条记录！');
						return false;
				 } else if (selectLength > 1) {
						Ext.Msg.alert('提示', '只能选择一条记录！');
						return false;
				 }
				 param=grid.getSelectionModel().getSelections()[0].data.OPPOR_ID;
				 showCustomerViewByTitle('销售活动');
			}
		}]       
		  				
	},{
		title:'商机详情',
		hideTitle : true,
		type:'form',
		groups:[{
			fields:[{name:'OPPOR_ID',text:'商机编号',readOnly:true},
					  {name:'OPPOR_NAME',text:'商机名称',readOnly:true},
					  {name:'OPPOR_STAT',text:'商机状态',translateType:'BUSI_CHANCE_STATUS',readOnly:true},
					  {name:'OPPOR_STAGE',text:'商机阶段',translateType:'BUSI_CHANCE_STAGE',readOnly:true},
					  {name:'OPPOR_SOURCE',text:'商机来源',translateType:'BUSI_CHANCE_SOURCE',readOnly:true},
					  {name:'OPPOR_TYPE',text:'商机类型',translateType:'BUSI_CHANCE_TYPE',readOnly:true},
					  {name:'OPPOR_START_DATE',text:'商机开始日期',searchField:true,dataType:'date',readOnly:true,format : 'Y-m-d'},
					  {name:'OPPOR_END_DATE',text:'商机完成日期',searchField:true,dataType:'date',readOnly:true,format : 'Y-m-d'},
					  {name:'OPPOR_DUE_DATE',text:'商机有效期',searchField:true,dataType:'date',readOnly:true,format : 'Y-m-d'},
					  {name:'EXECUTE_USER_NAME',text:'商机执行人',hiddenName:'EXECUTE_USER_ID',readOnly:true,xtype:'userchoose',singleSelect:true,searchRoleType:''},
					  {name:'EXECUTE_ORG_NAME',text:'商机执行机构',hiddenName:'EXECUTE_ORG_ID',readOnly:true,xtype:'orgchoose',singleSelected:true},
					  {name:'CUST_NAME',text:'客户名称',readOnly:true,xtype:'customerquery',hiddenName:'CUST_ID',singleSelected: true},
					  {name:'CUST_TYPE',text:'客户状态',translateType:'CUSTOMER_STATUS',readOnly:true},
					  {name:'CUST_CATEGORY',text:'客户类型',translateType:'XD000080',readOnly:true},
					  {name:'CUST_CONTACT_NAME',text:'客户联系人',readOnly:true},
					  {name:'PROD_NAME',text:'商机产品名称',dataType:'productChoose',hiddenName:'PROD_ID',readOnly:true,singleSelect:true},
					  {name:'MKT_TARGET_NAME',text:'营销任务指标名称',xtype:'targetchoose',hiddenName:'TARGET_CODE',singleSelect: true,readOnly:true},
					  {name:'MKT_ACTIV_NAME',text:'营销活动名称',xtype:'activityQuery',hiddenName:'MKT_ACTIV_CODE',singleSelect: true,readOnly:true},
					  {name:'PLAN_AMOUNT',text:'预计金额',renderer:money('0,0.00'),readOnly:true},
					  {name:'REACH_AMOUNT',text:'达成金额',renderer:money('0,0.00'),readOnly:true},
					  {name:'REACH_PROB',text:'达成概率',translateType:'BUSI_CHANCE_PROB',readOnly:true},
					  {name:'PLAN_COST',text:'费用预算',renderer:money('0,0.00'),readOnly:true},
					  {name:'CREATER_NAME',text:'创建人',readOnly:true},
					  {name:'CREATE_ORG_NAME',text:'创建机构',readOnly:true},
					  {name:'CREATE_DATE_TIME',text:'创建时间',dataType:'string',readOnly:true},
					  {name:'UPDATE_USER_NAME',text:'最近更新人',readOnly:true},
					  {name:'UPDATE_ORG_NAME',text:'最近更新机构',dataType:'string',readOnly:true},
					  {name:'UPDATE_DATE_TIME',text:'最近更新时间',readOnly:true},
					  {name:'ASSIGN_ORG_NAME',text:'待分配机构',xtype:'orgchoose',readOnly:true,hiddenName:'ASSIGN_OGR_ID'}
//					  {name:'CLAIM_USER_NAME',text:'认领人',allowBlank:false},
//					  {name:'CLAIM_ORG_NAME',text:'认领机构',allowBlank:false},]
		 ],
		 fn:function(OPPOR_ID,OPPOR_NAME,OPPOR_STAT,OPPOR_STAGE,OPPOR_SOURCE,OPPOR_TYPE,OPPOR_START_DATE,
			        OPPOR_END_DATE,OPPOR_DUE_DATE,EXECUTE_USER_NAME,EXECUTE_ORG_NAME,CUST_NAME,CUST_TYPE,CUST_CATEGORY,CUST_CONTACT_NAME,PROD_NAME,
			        MKT_TARGET_NAME,MKT_ACTIV_NAME,PLAN_AMOUNT,REACH_AMOUNT,REACH_PROB,PLAN_COST,CREATER_NAME,CREATE_ORG_NAME,
			        CREATE_DATE_TIME,UPDATE_USER_NAME,UPDATE_ORG_NAME,UPDATE_DATE_TIME,ASSIGN_ORG_NAME){
			        	OPPOR_ID.readOnly= true;
						OPPOR_ID.cls='x-readOnly';
						OPPOR_NAME.readOnly= true;
						OPPOR_NAME.cls='x-readOnly';
						OPPOR_STAT.readOnly= true;
						OPPOR_STAT.cls='x-readOnly';
						OPPOR_STAGE.readOnly= true;
						OPPOR_STAGE.cls='x-readOnly';
						OPPOR_SOURCE.readOnly= true;
						OPPOR_SOURCE.cls='x-readOnly';
						OPPOR_TYPE.readOnly= true;
						OPPOR_TYPE.cls='x-readOnly';
						OPPOR_START_DATE.readOnly= true;
						OPPOR_START_DATE.cls='x-readOnly';
						OPPOR_END_DATE.readOnly= true;
						OPPOR_END_DATE.cls='x-readOnly';
						OPPOR_DUE_DATE.readOnly= true;
						OPPOR_DUE_DATE.cls='x-readOnly';
						EXECUTE_USER_NAME.readOnly= true;
						EXECUTE_USER_NAME.cls='x-readOnly';
						EXECUTE_ORG_NAME.readOnly= true;
						EXECUTE_ORG_NAME.cls='x-readOnly';
						CUST_NAME.readOnly= true;
						CUST_NAME.cls='x-readOnly';
						CUST_TYPE.readOnly= true;
						CUST_TYPE.cls='x-readOnly';
						CUST_CATEGORY.readOnly= true;
						CUST_CATEGORY.cls='x-readOnly';
						CUST_CONTACT_NAME.readOnly= true;
						CUST_CONTACT_NAME.cls='x-readOnly';
						PROD_NAME.readOnly= true;
						PROD_NAME.cls='x-readOnly';
						MKT_TARGET_NAME.readOnly= true;
						MKT_TARGET_NAME.cls='x-readOnly';
						MKT_ACTIV_NAME.readOnly= true;
						MKT_ACTIV_NAME.cls='x-readOnly';
						PLAN_AMOUNT.readOnly= true;
						PLAN_AMOUNT.cls='x-readOnly';
						REACH_AMOUNT.readOnly= true;
						REACH_AMOUNT.cls='x-readOnly';
						REACH_PROB.readOnly= true;
						REACH_PROB.cls='x-readOnly';
						PLAN_COST.readOnly= true;
						PLAN_COST.cls='x-readOnly';
						CREATER_NAME.readOnly= true;
						CREATER_NAME.cls='x-readOnly';
						CREATE_ORG_NAME.readOnly= true;
						CREATE_ORG_NAME.cls='x-readOnly';
						CREATE_DATE_TIME.readOnly= true;
						CREATE_DATE_TIME.cls='x-readOnly';
						UPDATE_USER_NAME.readOnly= true;
						UPDATE_USER_NAME.cls='x-readOnly';
						UPDATE_ORG_NAME.readOnly= true;
						UPDATE_ORG_NAME.cls='x-readOnly';
						UPDATE_DATE_TIME.readOnly= true;
						UPDATE_DATE_TIME.cls='x-readOnly';
						ASSIGN_ORG_NAME.readOnly= true;
						ASSIGN_ORG_NAME.cls='x-readOnly';
			        	
			 return [OPPOR_ID,OPPOR_NAME,OPPOR_STAT,OPPOR_STAGE,OPPOR_SOURCE,OPPOR_TYPE,OPPOR_START_DATE,
				        OPPOR_END_DATE,OPPOR_DUE_DATE,EXECUTE_USER_NAME,EXECUTE_ORG_NAME,CUST_NAME,CUST_TYPE,CUST_CATEGORY,CUST_CONTACT_NAME,PROD_NAME,
				        MKT_TARGET_NAME,MKT_ACTIV_NAME,PLAN_AMOUNT,REACH_AMOUNT,REACH_PROB,PLAN_COST,CREATER_NAME,CREATE_ORG_NAME,
				        CREATE_DATE_TIME,UPDATE_USER_NAME,UPDATE_ORG_NAME,UPDATE_DATE_TIME,ASSIGN_ORG_NAME]
		 }},{
			 columnCount:1,
			 fields:[{name:'OPPOR_CONTENT',text:'商机内容',allowBlank:false,xtype:'textarea'}],
			 fn:function(OPPOR_CONTENT){
			 		OPPOR_CONTENT.readOnly= true;
					OPPOR_CONTENT.cls='x-readOnly';
				 return [OPPOR_CONTENT];
			 }
		 },{
			 columnCount:1,
			 fields:[{name:'MEMO',text:'商机备注',xtype:'textarea'}],
			 fn:function(MEMO){
				 	MEMO.readOnly= true;
					MEMO.cls='x-readOnly';
				 return [MEMO];
			 }
		 }],
		formButtons:[{
		 	text:'返回',
		 	fn : function(contentPanel, baseform) {
		 		showCustomerViewByTitle('展示商机列表');
		 	}
		 	
		 }]
	},{
		title:'销售活动',
		hideTitle:true,
		type:'grid',
		url:basepath + '/mktBusiOpporSalesActivQueryAction.json',
		frame : true,
		fields : {fields:[
		        {name: 'OPPOR_ID', text : '商机ID',hidden:true},           
		        {name: 'SALES_ACTIV_ID', text : '销售活动ID',hidden:true},          
				{name: 'SALES_ACTIV_NAME', text : '销售活动名称'},  
				{name: 'EXEC_DATE', text : '活动执行日期'},  
				{name: 'SALES_STAGE', text : '销售阶段ID',hidden:true},
				{name: 'SALES_STAGE_ORA', text : '销售阶段'},  
				{name: 'EXEC_WAY_ORA', text : '活动执行方式'},  
				{name: 'EXEC_WAY', text : '活动执行方式ID',hidden:true},  
				{name: 'EXEC_USER_NAME', text : '活动执行人'},  
				{name: 'EXEC_ORG_NAME', text : '活动执行机构'},  
				{name: 'NEXT_CONTACT_TIME', text : '下次联系时间'},  
				{name: 'NEXT_EXEC_WAY', text : '下次执行方式ID',hidden:true},
				{name: 'NEXT_EXEC_WAY_ORA', text : '下次执行方式'},
				{name: 'ACTIV_CONTENT', text : '活动内容'},
				{name: 'NEXT_EXEC_CONTENT', text : '下次执行内容'},
				{name: 'ACTIV_MEMO', text : '备注'}
				]
		},
		gridButtons:[{
			text:'活动详情',
			hidden:JsContext.checkGrant('opporList_saleActivityDetail'),
			fn:function(grid){
				var selectLength = grid.getSelectionModel().getSelections().length;
				 var selectRe= grid.getSelectionModel().getSelections()[0];
				 if (selectLength < 1) {
						Ext.Msg.alert('提示', '请先选择一条记录！');
						return false;
				 } else if (selectLength > 1) {
						Ext.Msg.alert('提示', '只能选择一条记录！');
						return false;
				 }
				 getCustomerViewByTitle('活动详情').contentPanel.form.loadRecord(selectRe);
				 showCustomerViewByTitle('活动详情');
			}
		}]
	},{
		title:'活动详情',
		hideTitle:true,
		type:'form',
		frame : true,
		groups :[
		        {fields:[
				{name: 'SALES_ACTIV_NAME', text : '销售活动名称',readOnly:true},  
				{name: 'EXEC_DATE', text : '活动执行日期',dataType:'date',readOnly:true},  
				{name: 'SALES_STAGE_ORA', text : '销售阶段',readOnly:true},  
				{name: 'EXEC_WAY_ORA', text : '活动执行方式',readOnly:true},  
				{name: 'EXEC_USER_NAME', text : '活动执行人',readOnly:true},  
				{name: 'EXEC_ORG_NAME', text : '活动执行机构',readOnly:true},  
				{name: 'NEXT_CONTACT_TIME', text : '下次联系时间',dataType:'date',readOnly:true},  
				{name: 'NEXT_EXEC_WAY_ORA', text : '下次执行方式',readOnly:true}
				],
				fn:function(SALES_ACTIV_NAME,EXEC_DATE,SALES_STAGE_ORA,EXEC_WAY_ORA,EXEC_USER_NAME,EXEC_ORG_NAME,NEXT_CONTACT_TIME,NEXT_EXEC_WAY_ORA){
					
						SALES_ACTIV_NAME.readOnly= true;
						SALES_ACTIV_NAME.cls='x-readOnly';
						EXEC_DATE.readOnly= true;
						EXEC_DATE.cls='x-readOnly';
						SALES_STAGE_ORA.readOnly= true;
						SALES_STAGE_ORA.cls='x-readOnly';
						EXEC_WAY_ORA.readOnly= true;
						EXEC_WAY_ORA.cls='x-readOnly';
						EXEC_USER_NAME.readOnly= true;
						EXEC_USER_NAME.cls='x-readOnly';
						EXEC_ORG_NAME.readOnly= true;
						EXEC_ORG_NAME.cls='x-readOnly';
						NEXT_CONTACT_TIME.readOnly= true;
						NEXT_CONTACT_TIME.cls='x-readOnly';
						NEXT_EXEC_WAY_ORA.readOnly= true;
						NEXT_EXEC_WAY_ORA.cls='x-readOnly';
					return[SALES_ACTIV_NAME,EXEC_DATE,SALES_STAGE_ORA,EXEC_WAY_ORA,EXEC_USER_NAME,EXEC_ORG_NAME,NEXT_CONTACT_TIME,NEXT_EXEC_WAY_ORA];
				}},{
					columnCount:1,
					fields:[{name: 'ACTIV_CONTENT', text : '活动内容',xtype:'textarea',readOnly:true}],
					fn:function(ACTIV_CONTENT){
							ACTIV_CONTENT.readOnly= true;
							ACTIV_CONTENT.cls='x-readOnly';
						return[ACTIV_CONTENT]
					}
				},{
					columnCount:1,
					fields:[{name: 'NEXT_EXEC_CONTENT', text : '下次活动内容',xtype:'textarea',readOnly:true}],
					fn:function(NEXT_EXEC_CONTENT){
							NEXT_EXEC_CONTENT.readOnly= true;
							NEXT_EXEC_CONTENT.cls='x-readOnly';
						return[NEXT_EXEC_CONTENT]
					}
				},{
					columnCount:1,
					fields:[{name: 'ACTIV_MEMO', text : '备注',xtype:'textarea',readOnly:true}],
					fn:function(ACTIV_MEMO){
							ACTIV_MEMO.readOnly= true;
							ACTIV_MEMO.cls='x-readOnly';
						return[ACTIV_MEMO]
					}
				}
		],
		formButtons:[{
		 	text:'返回',
		 	fn : function(contentPanel, baseform) {
		 		showCustomerViewByTitle('销售活动');
		 	}
		 	
		 }]
	}];
	 
	var beforecommit = function(data,cUrl){
		
	};
	
	
	var beforeviewshow=function(view){
		if(view._defaultTitle=='展示商机列表'){
			if(!getSelectedData()){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			};
			var F_CODE=getSelectedData().data.F_CODE;
			view.setParameters ({
				opporStage : F_CODE
    		});
		}
		if(view._defaultTitle=='销售活动'){
			view.setParameters ({
				oppor_id : param
    		}); 
		}
	};
	
	/**
	 * 结果域面板滑入后触发,系统提供listener事件方法
	 */
	var viewshow = function(theview){
		
	};	
	



