Ext.onReady(function (){
var groupNo =  parent.document.getElementById("groupNo").value;
var hostOrgNo = parent.document.getElementById("hostOrgNo").value;
var groupCreditAudCM = new Ext.grid.ColumnModel(
								[
									new Ext.grid.RowNumberer(),
	                    	        {
	                    				header : '客户名称', // 列标题
	                    				dataIndex : 'applyComName', // 数据索引:和Store模型对应
	                    				width : 200,
										id:'applyCom'
	                    		    },
	                    		    {
	                    				header : '授信类型',
	                    				dataIndex : 'creditType_ORA',
	                    				width : 100
	                    			},	                    		    
	                    		    {
	                    				header : '组织机构代码',
	                    				dataIndex : 'custZzdm',
	                    				width : 100
	                    			},	                    		    
									{
										header:'授信批次号',
										dataIndex:'batchNo',
										width:150
									},
	                    	        {
	                    				header : '现有业务余额', // 列标题
	                    				dataIndex : 'currBizValue', // 数据索引:和Store模型对应
	                    				align:'right',
										align:'right',renderer: money('0,000.0000'),	                    				
	                    				width : 200
	                    		    },									
	                    	        {
	                    				header : '本期授信申请额度', // 列标题
	                    				dataIndex : 'currCreditApplyValue', // 数据索引:和Store模型对应
	                    				align:'right',
	                    				align:'right',renderer: money('0,000.0000'),
	                    				width : 200
	                    		    },
									{
	                    				header : '本期授信批复额度', // 列标题
	                    				dataIndex : 'currCreditApprovalValue', // 数据索引:和Store模型对应
	                    				align:'right',
	                    				align:'right',renderer: money('0,000.0000'),
	                    				width : 150
									},	                    		    
									{
	                    				header : '本期授信调整后额度', // 列标题
	                    				dataIndex : 'afterAdjustCreditValue', // 数据索引:和Store模型对应
	                    				align:'right',
	                    				align:'right',renderer: money('0,000.0000'),
	                    				width : 150
									},
									{
	                    				header : '未分配额度', // 列标题
	                    				dataIndex : 'creditRestValue', // 数据索引:和Store模型对应
	                    				align:'right',
	                    				align:'right',renderer: money('0,000.0000'),
	                    				width : 150
									},									
									{
	                    				header : '业务品种及额度分配情况', // 列标题
	                    				dataIndex : 'currBizDistribute', // 数据索引:和Store模型对应
	                    				width : 150
									},											
									{
	                    				header : '担保方式', // 列标题
	                    				dataIndex : 'currGuaranteeType', // 数据索引:和Store模型对应
	                    			
	                    				width : 200
	                    		    },	 	  
									{
										header:'额度使用计划',
										dataIndex:'usePlan'
									},
									{
										header:'其他声明事项',
										dataIndex:'otherItems'
									}

	                    			]);
	var groupCreditAudRecord= new Ext.data.Record.create([
						{name:"applyComName"},
						{name:"custZzdm"},
						{name:'groupNo'},
						{name:'groupName'},
						{name:'parentId'},
						{name:'relationId'},
						{name:'custId'},
						{name:'creditInfoId'},
						{name:'groupType'},
						{name:'groupType_ORA'},
						{name:'groupIndustry'},							
						{name:'hyClass'},
						{name:'groupCreditName'},
						{name:'groupStatus'},
						{name:'groupRootCustId'},
						{name:'groupRootCustName'},
						{name:'groupHostOrgNo'},
						{name:'operatorOrgId'},
						{name:'applyDate'},
						{name:'applyStatus'},
						{name:'applyStatus_ORA'},
						{name:'currCreditApplyValue'},
						{name:'currCreditApprovalValue'},
						{name:'afterAdjustCreditValue'},
						{name:'currBizValue'},
						{name:'currGuaranteeType'},
						{name:'currBizDistribute'},
						{name:'currFeeLevel'},
						{name:'lastCreditValue'},
						{name:'lastGuaranteeType'},
						{name:'lastBizDistribute'},
						{name:'lastFeeLevel'},
						{name:'lastCreditStartDate'},
						{name:'operatorId'},
						{name:'applySubmitDate'},
						{name:'topBankApprovalDate'},
						{name:'endAdjustDate'},
						{name:'creditRestValue'},
						{name:'creditStatus'},
						{name:'usePlan'},
						{name:'otherItems'},
						{name:'hostOrgApprovalOppion'},
						{name:'topBankApprovalOppion'},
						{name:'creditType'},			
						{name:'creditType_ORA'},	
						{name:'memberType'},
						{name:'memberType_ORA'},
						{name:'batchNo'},
						{name:'lastApplyCom'},
						{name:'updateDate'},
						{name:'currRestValue'},
						{name:'applyComId'},
						{name:'hostOrgName'},
						{name:'operatorOrgName'},
						{name:'operatorName'},
						{name:'updateUserName'},
				     	{name: '_id'},
				     	{name: '_level', type: 'int'},
				     	{name: '_parent'},
				     	{name: '_lft', type: 'int'},
				     	{name: '_rgt', type: 'int'},
				     	{name: '_is_leaf', type: 'bool'}
		]);
		     

    var groupCreditAudStore = new Ext.ux.maximgb.tg.NestedSetStore({
 //   		autoLoad : true,
//			reader: new Ext.data.JsonReader(
//			{
//			id: '_id',
//			root:'depAndLonTreeList'
//			}, groupCreditAudRecord),
//			listeners:{
//				'load':function(){
//					groupCreditAudStore.expandAll();					
//				}
//			},
//			proxy: new Ext.data.HttpProxy({
//				url:basepath+"/group-credit-audit-tree",
//
//				method:'GET'
//			})//,

		reader : new Ext.data.JsonReader({
											root : 'rows',
											totalProperty : 'num'
										 }, 
										 groupCreditAudRecord
		)
    });	
	
   var exportFormCredit = new Ext.form.FormPanel({
   	 id:'exportFormCredit',
   	 hidden:true,
	 items:[
	 {
	 	id:'batchNo',
	 	name:'batchNo',
	 	hidden:true,
	 	xtype:'textfield'
	 },{
	 	
	 	name:'groupNo',
	 	hidden:true,
	 	xtype:'textfield',
	 	value:groupNo
	 },
	 {
	 	name:'hostOrgNo',
	 	id:'hostOrgNo',
	 	xtype:'textfield',
	 	hidden:true
	 },{
	 	name:'authFlag',
	 	xtype:'textfield',
	 	hidden:true,
	 	value:'YES'
	 },
	 {
	 	name:'exportFlag',
	 	xtype:'textfield',
	 	hidden:true,
	 	value:'YES'
	 }	 
	 ]
   	 
   });

    var groupCreditAudPanel = new Ext.ux.maximgb.tg.EditorGridPanel({
      store: groupCreditAudStore,
	  region:'center',
	  selModel:new Ext.grid.RowSelectionModel({
	  	singleSelection:true
	  }),
	  loadMask:{
	  	msg:'数据正在加载,请稍后...'
	  },
	  split:true,
//	  autoExpandColumn:'company',
      master_column_id : 'applyCom',
      cm:groupCreditAudCM,
      stripeRows: true, 
//      title: '集团客户授信',
      tbar:[
      		'->',
			new Ext.form.Label({
				text:'授信批次号:'
			}),
			{
				xtype:'combo',
				id:'batchNum',
				name:'batchNo',
				allowBlank:false,
				resizable:true,
				hiddenName:'batchNo',
				mode:'local',																			
             	store: new Ext.data.Store({
             		autoLoad:true,
         			baseParams:{
         				limit:99999999,
         				start:0,
         				'condition':'{"GROUP_NO":"'+groupNo+'"}'
         			},             		
             		proxy:new Ext.data.HttpProxy({
             			url:basepath+'/group-credit-manage-query',
             			method:'GET'
             		}),
             		fields:['ID','BATCH_NO'],
             		reader:new Ext.data.JsonReader({
             		root:'groupCreditMap.data'	                         		
             		},[{name:'GROUP_HOST_ORG_NO'},{name:'BATCH_NO'}])                         		
             	}),																			
				emptyText:'请选择',
				triggerAction:'all',
				valueField:'GROUP_HOST_ORG_NO',
				displayField:'BATCH_NO',
				anchor : '90%'
    		},		    
		    {
		    	width:80,
				text : '查询',
				handler : function() {
				var hostOrgId_No = Ext.getCmp("batchNum").getValue();
				var batchNo = Ext.getCmp("batchNum").getRawValue()
				
				exportFormCredit.getForm().findField("batchNo").setValue(batchNo);
				exportFormCredit.getForm().findField("hostOrgNo").setValue(hostOrgId_No);
				if(batchNo==null || batchNo==""||batchNo=="undefined"){
					
					Ext.MessageBox.alert('提示',"请选择授信批次号");
					return;
				}
				
				groupCreditAudStore.load({
					params:{
						start:0,
						limit:9999999,
						'condition':'{"batchNo":"'+batchNo+'","groupNo":"'+groupNo+'","hostOrgNo":"'+hostOrgId_No+'","authFlag":"YES"}'
					}
				});
		    }
		},
		'-',
		new Com.yucheng.bob.ExpButton({
		width:60,
		formPanel:'exportFormCredit',
		url:basepath+'/group-credit-audit-tree.json'
		})			
		],      
      viewConfig : {
      	enableRowBody : true,
      	getRowClass:function(record,rowIndex,rp,ds)
      	{
      		
      		if(!record.get("_is_leaf"))
      		{
      			return 'blocLoanAndDepositNodeClass';
      		}
      	}
      }
    });

//      groupCreditAudStore.load({
//		params:{
//			start:0,
//			limit:9999999,
//			'condition':'{"batchNo":"'+batchNo+'","groupNo":"'+groupNo+'"}'
//		}     	
//      });
    

	var memberData= {
			TOTALCOUNT:1,
			rows:[{"applyComName":"擎宇数据有限公司",
				"custZzdm":"XC57878345",
				"groupNo":"7343462",
				"groupName":"擎宇数据有限公司",
				"parentId":"1",
				"relationId":"1",
				"custId":"467873",
				"creditInfoId":"",
				"groupType":"",
				"groupType_ORA":"",
				"groupIndustry":"",
				"hyClass":"金融类",
				"groupCreditName":"",
				"groupStatus":"",
				"groupRootCustId":"",
				"groupRootCustName":"",
				"groupHostOrgNo":"",
				"operatorOrgId":"",
				"applyDate":"2012-02-03",
				"applyStatus":"",
				"applyStatus_ORA":"正常",
				"currCreditApplyValue":"345626",
				"currCreditApprovalValue":"4523421",
				"afterAdjustCreditValue":"55652344",
				"currBizValue":"2345521",
				"currGuaranteeType":"56677324",
				"currBizDistribute":"7889334",
				"currFeeLevel":"2",
				"lastCreditValue":"545",
				"lastGuaranteeType":"23",
				"lastBizDistribute":"5",
				"lastFeeLevel":"23",
				"lastCreditStartDate":"2012-01-04",
				"operatorId":"",
				"applySubmitDate":"",
				"topBankApprovalDate":"",
				"endAdjustDate":"",
				"creditRestValue":"56772",
				"creditStatus":"",
				"usePlan":"",
				"otherItems":"",
				"hostOrgApprovalOppion":"",
				"topBankApprovalOppion":"",
				"creditType":"",
				"creditType_ORA":"正式",
				"memberType":"",
				"memberType_ORA":"集团客户",
				"batchNo":"45",
				"lastApplyCom":"",
				"updateDate":"",
				"currRestValue":"",
				"applyComId":"",
				"hostOrgName":"",
				"operatorOrgName":"",
				"operatorName":"",
				"updateUserName":"",
				"_id":"345",
				"_level":"1",
				"_parent":"0",
				"_lft":"1",
				"_rgt":"0",
				"_is_leaf":"1"}	
			]
		};
	groupCreditAudStore.loadData(memberData);
      
    var groupCreditAudView = new Ext.Viewport({
    	layout : 'fit',
     	items : {
				layout:'fit',
    			items:groupCreditAudPanel
		}
	
		
    });
  })  
