/**
 * 渠道自动营销管理-客户查询
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js',//产品放大镜
		        '/contents/pages/common/Com.yucheng.bcrm.common.MarketTarget.js',	//指标选择放大镜,
		        '/contents/pages/mktManage/mktBusiOppor/Com.yucheng.bcrm.common.MktActivityCommonQuery.js',//营销活动放大镜
		        '/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',//客户放大镜
		        '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',//用户放大镜
		        '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',//组织机构放大镜
		        //'/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldx.js', // 机构放大镜
		        '/contents/pages/common/Com.yucheng.bcrm.common.MarketTask.js'	,
		        '/contents/pages/common/Com.yucheng.bcrm.common.MktTaskTarget.js'//营销任务指标明细放大镜
		        ,'/contents/pages/common/Com.yucheng.bcrm.common.CreateMktOppor.js'
		        ,'/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js'  //客户群放大镜
		        ]);
    
    var url = basepath+'/mktBusiOpporListQueryAction.json';
    var comitUrl=basepath+'/mktBusiOpporOperationAction!saveOrUpdateBusiOppor.json';
    var formCfgs = false;
    var needCondition = true;
    var needGrid = true;
    var createView = true;
    var editView = !JsContext.checkGrant('mktBusiOppor_edit');
    var detailView = !JsContext.checkGrant('mktBusiOppor_detail');
	var lookupTypes=[
	                 'BUSI_CHANCE_STAGE',        
	                 'BUSI_CHANCE_PROB',         
	                 'BUSI_CHANCE_STATUS',       
	                 'BUSI_CHANCE_SOURCE',       
	                 'BUSI_CHANCE_TYPE',         
	                 'CUSTOMER_STATUS',          
	                 'XD000080'                
	                 ];
	var localLookup = { 
	    'ALLOCATE' : [
	             {key:'1',value:'分配到机构'},
	             {key:'2',value:'分配到客户经理'}
	             
          ],
        'BUSI_CHANCE_CUST_SOURCE':[{key:'1',value:'客户'},
                                   {key:'2',value:'客户群'}]
	};
	var ifgroup;
	var custStr;
	var fields = [
				  {name:'ID',hidden:true},
				  {name:'OPPOR_ID',text:'商机编号',hidden:true},
				  {name:'OPPOR_NAME',text:'商机名称',searchField:true,allowBlank:false},
				  {name:'OPPOR_CUST_SOURCE',text:'商机客户来源',hidden:true,allowBlank:false,translateType:'BUSI_CHANCE_CUST_SOURCE',
					  listeners:{
						  'select':function(){
							  if(this.value=='1'){
								  getCurrentView().contentPanel.form.findField("EXECUTE_ORG_NAME").setVisible(true);
								  getCurrentView().contentPanel.form.findField("EXECUTE_USER_NAME").setVisible(true);
								  getCurrentView().contentPanel.form.findField("CUST_TYPE").setVisible(true);
								  getCurrentView().contentPanel.form.findField("CUST_CATEGORY").setVisible(true);
								  getCurrentView().contentPanel.form.findField("CUST_NAME").setVisible(true);
								  getCurrentView().contentPanel.form.findField("CUST_CONTACT_NAME").setVisible(true);
								  getCurrentView().contentPanel.form.findField("CUST_GROUP").setVisible(false);
								  getCurrentView().contentPanel.buttons[1].show();
								  ifgroup=false;
							  }else if(this.value=='2'){
								  getCurrentView().contentPanel.form.findField("EXECUTE_ORG_NAME").setVisible(false);
								  getCurrentView().contentPanel.form.findField("EXECUTE_USER_NAME").setVisible(false);
								  getCurrentView().contentPanel.form.findField("CUST_TYPE").setVisible(false);
								  getCurrentView().contentPanel.form.findField("CUST_CATEGORY").setVisible(false);
								  getCurrentView().contentPanel.form.findField("CUST_NAME").setVisible(false);
								  getCurrentView().contentPanel.form.findField("CUST_CONTACT_NAME").setVisible(false);
								  getCurrentView().contentPanel.form.findField("CUST_GROUP").setVisible(true);
								  getCurrentView().contentPanel.buttons[1].hide();
								  ifgroup=true;
							  }
						  }
					  }},
				  {name:'OPPOR_STAT',text:'商机状态',translateType:'BUSI_CHANCE_STATUS',searchField:true,allowBlank:false},
				  {name:'OPPOR_STAGE',text:'商机阶段',translateType:'BUSI_CHANCE_STAGE',searchField:true,allowBlank:false},
				  {name:'OPPOR_SOURCE',text:'商机来源',translateType:'BUSI_CHANCE_SOURCE',searchField:true,allowBlank:false,
					  listeners : {
          				"select" : function() {
          					if(this.value=='1' || this.value=='2'|| this.value=='3'){
          						getCurrentView().contentPanel.form.findField("PROD_NAME").allowBlank=true;
          					}
						}
          			}},
				  {name:'OPPOR_TYPE',text:'商机类型',translateType:'BUSI_CHANCE_TYPE',searchField:true,allowBlank:false},
				  {name:'OPPOR_START_DATE',text:'商机开始日期',searchField:true,dataType:'date',allowBlank:false,format : 'Y-m-d'},
				  {name:'OPPOR_END_DATE',text:'商机完成日期',searchField:true,dataType:'date',allowBlank:false,format : 'Y-m-d'},
				  {name:'OPPOR_DUE_DATE',text:'商机有效期',searchField:true,dataType:'date',allowBlank:false,format : 'Y-m-d'},
				  {name:'EXECUTE_USER_ID',text:'商机执行人ID',allowBlank:true,hidden:true},
				  {name:'EXECUTE_USER_NAME',text:'商机执行人',hiddenName:'EXECUTE_USER_ID',allowBlank:true,xtype:'userchoose',singleSelect:true,searchRoleType:'',callback:function(c){
					  getCurrentView().contentPanel.form.findField('EXECUTE_USER_ID').setValue(c[0].data.userId);
				  }},
				  {name:'EXECUTE_ORG_ID',text:'商机执行机构ID',allowBlank:false,hidden:true},
				  {name:'EXECUTE_ORG_NAME',text:'商机执行机构',hiddenName:'EXECUTE_ORG_ID',allowBlank:true,xtype:'orgchoose',singleSelected:true},
				  {name:'OPPOR_CONTENT',text:'商机内容',allowBlank:false,xtype:'textarea'},
				  {name:'CUST_ID',text:'客户ID',hidden:true},
				  {name:'CUST_NAME',text:'客户名称',allowBlank:false,xtype:'customerquery',hiddenName:'CUST_ID',chooseLinkMan:true,singleSelected: true,callback : function(a,b) {
					  var CUST_CATEGORY_P= getCurrentView().contentPanel.form.findField("CUST_CATEGORY");
					  var CUST_TYPE_P= getCurrentView().contentPanel.form.findField("CUST_TYPE");
					  CUST_CATEGORY_P.setValue(b[0].data.CUST_TYPE);
					  CUST_TYPE_P.setValue(b[0].data.CUST_STAT);
//					  CUST_CATEGORY_P.setDisabled(true);
//					  CUST_TYPE_P.setDisabled(true);
					  var EXECUTE_USER_ID_P=getCurrentView().contentPanel.form.findField("EXECUTE_USER_ID");
					  var EXECUTE_USER_NAME_P=getCurrentView().contentPanel.form.findField("EXECUTE_USER_NAME");
					  var EXECUTE_ORG_ID_P= getCurrentView().contentPanel.form.findField("EXECUTE_ORG_ID");
					  var EXECUTE_ORG_NAME_P= getCurrentView().contentPanel.form.findField("EXECUTE_ORG_NAME");
					  var CUST_CONTACT_NAME_P=getCurrentView().contentPanel.form.findField("CUST_CONTACT_NAME");
					  var CUST_ID_P=getCurrentView().contentPanel.form.findField("CUST_ID");
					  EXECUTE_USER_ID_P.setValue(b[0].data.MGR_ID);
					  EXECUTE_USER_NAME_P.setValue(b[0].data.MGR_NAME);
					  EXECUTE_ORG_ID_P.setValue(b[0].data.ORG_ID);
					  EXECUTE_ORG_NAME_P.setValue(b[0].data.ORG_NAME);
					  CUST_CONTACT_NAME_P.setValue(b[0].data.LINKMAN_NAME);
					  CUST_ID_P.setValue(b[0].data.CUST_ID);
					  var vm= b[0].data.MGR_ID.substring(0,2);
					  if(vm!='VM'){//如果客户有正式客户经理，则商机执行人默认为正式客户经理，商机执行机构为客户归属机构，且不能更改
						  EXECUTE_USER_NAME_P.setDisabled(true);   
						  EXECUTE_ORG_NAME_P.setDisabled(true);
					  }
					  if(vm=='VM'){//如果客户挂在虚拟客户经理名下，则商机执行机构，商机执行人可选择
						  EXECUTE_USER_NAME_P.setDisabled(false);
						  EXECUTE_ORG_NAME_P.setDisabled(false);
					  }					  
//					  else if(b[0].data.MGR_ID==''){
//						  EXECUTE_USER_NAME_P.searchRoleType='100009';
//					  }
				  }},
				  {name:'CUST_GROUP',text:'客户群',xtype:'custgroupquery',allowBlank:false,hiddenName:'CUST_GROUP_ID',callback:function(a,b){
					  custStr=a.custStr;
				  }},
				  {name:'CUST_TYPE',text:'客户状态',translateType:'CUSTOMER_STATUS',allowBlank:false,readOnly:true},
				  {name:'CUST_CATEGORY',text:'客户类型',translateType:'XD000080',allowBlank:false,readOnly:true},
				  {name:'CUST_CONTACT_NAME',text:'客户联系人',allowBlank:true,readOnly:true},
				  {name:'PROD_ID',text:'商机产品ID',allowBlank:false,hidden:true},
				  {name:'PROD_NAME',text:'商机产品名称',dataType:'productChoose',hiddenName:'PROD_ID',searchField:true,allowBlank:false,singleSelect:true},
				  {name:'MKT_TARGET_ID',text:'营销任务指标',allowBlank:false,hidden:true},
				  {name:'MKT_TARGET_NAME',text:'营销任务指标名称',xtype:'mktTaskTarget',hiddenName:'MKT_TARGET_ID',singleSelect: true,callback : function(a,b) {
				  }},
				  {name:'MKT_ACTIV_ID',text:'营销活动ID',allowBlank:false,hidden:true},
				  {name:'MKT_ACTIV_NAME',text:'营销活动名称',xtype:'activityQuery',hiddenName:'MKT_ACTIV_ID',singleSelect: true,callback : function(a,b) {
				  }},
				  {name:'PLAN_AMOUNT',text:'预计金额',viewFn:money('0,000.00'),xtype: 'numberfield',minValue:0},
				  {name:'REACH_AMOUNT',text:'达成金额',viewFn:money('0,000.00'),xtype: 'numberfield',minValue:0},
				  {name:'REACH_PROB',text:'达成概率',translateType:'BUSI_CHANCE_PROB',searchField:true},
				  {name:'PLAN_COST',text:'费用预算',viewFn:money('0,000.00'),xtype: 'numberfield',minValue:0},
				  {name:'CREATER_ID',text:'创建人ID',hidden:true},
				  {name:'CREATER_NAME',text:'创建人'},
				  {name:'CREATE_ORG_ID',text:'创建机构ID',hidden:true},
				  {name:'CREATE_ORG_NAME',text:'创建机构'},
				  {name:'CREATE_DATE_TIME',text:'创建时间',dataType:'string'},
				  {name:'UPDATE_USER_NAME',text:'最近更新人'},
				  {name:'UPDATE_ORG_NAME',text:'最近更新机构',dataType:'string'},
				  {name:'UPDATE_DATE_TIME',text:'最近更新时间'},
				  {name:'ASSIGN_OGR_ID',text:'待分配机构ID',hidden:true},
				  {name:'ASSIGN_ORG_NAME',text:'待分配机构',xtype:'orgchoose',singleSelect:true,hiddenName:'ASSIGN_OGR_ID',hidden:true},
//				  {name:'CLAIM_USER_NAME',text:'认领人',allowBlank:false},
//				  {name:'CLAIM_ORG_NAME',text:'认领机构',allowBlank:false},
				  {name:'MEMO',text:'商机备注',xtype:'textarea'}
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
		text:'删除商机',
		hidden:JsContext.checkGrant('mktBusiOppor_delete'),
		handler:function(){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据！');
				return false;
			}else{
				 delRec();
			}
		}
	}
//	,
//		{
//		text:'创建商机',
//		handler:function(){
//			var createMktOppor = new Com.yucheng.bcrm.common.CreateMktOppor ({
//				groupId:100,isgroup:true
//			})
//			createMktOppor.show();
//		}
//	}
	];
	
	// 删除商机数据
	// 1、手动创建的商机，只能由创建人和执行人删除；
	// 2、系统创建的商机，只能由待分配机构主管删除；
	// 3、只有暂存、退回、到期退回的商机才能删除；
	function delRec() {
		var oppor_stat = null;// 商机状态
		var opporId = null;// 商机ID
		var opporSource = null;// 商机来源
		var createrId = null;// 创建人ID
		var executeUserId = null;// 执行人ID
		var assingOrgId = null;// 待分配机构ID
			// 1、控制只能删除“暂存(0)、退回(5)、到期退回(6)”状态的商机
			oppor_stat =getSelectedData().data.OPPOR_STAT;
			if (oppor_stat != "0" && oppor_stat != "5" && oppor_stat != "6") {
				Ext.Msg.alert('提示', '只能删除“暂存、退回、到期退回”状态的商机！');
				return false;
			}
			opporId = getSelectedData().data.OPPOR_ID;
			// 2、控制删除权限：
			// “暂存”状态的商机，由“创建人”删除
			// 非“暂存”状态的商机，有执行人的商机只能由执行人删除，没有执行人的商机只能由待分配机构主管删除；
			opporSource = getSelectedData().data.OPPOR_SOURCE;
			createrId = getSelectedData().data.CREATER_ID;
			executeUserId = getSelectedData().data.EXECUTE_USER_ID;
			if (oppor_stat == "0") {
				// “暂存”状态的商机
				if (createrId != __userId) {
					// 当前用户不是商机创建人
					Ext.Msg.alert('提示', '你没有权限删除当前选中的商机！');
					return false;
				}
			} else {
				// 非“暂存”状态的商机
				if (opporSource == "0") {
					// 商机为手动创建
					if (createrId != __userId && executeUserId != __userId) {
						Ext.Msg.alert('提示', '你没有权限删除当前选中的商机！');
						return false;
					}
				} else {
					// 商机为系统创建
					assingOrgId = getSelectedData().data.ASSIGN_OGR_ID;
					var queryUrl = basepath + '/mktBusiOpporOperationAction!'
							+ 'getOrgManager.json';
					Ext.Ajax.request({
						url : queryUrl,
						method : 'POST',
						sync : true,// 同步
						waitMsg : '正在查询数据,请等待...',
						params : {
							'orgId' : assingOrgId
						},
						success : function(response) {
							var orgManagerId = response.responseText;
							if (orgManagerId != __userId) {
								// 当前用户不是商机待分配机构主管
								Ext.Msg.alert('提示', '你没有权限删除当前选中的商机！');
								return false;
							}
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '你没有权限删除当前选中的商机！');
							return false;
						}
					});
				}
			}
		
		// 3、执行删除操作
		doDelete(opporId);
	}

	// 执行商机删除操作
	function doDelete(opporId) {
		var delUrl = basepath
				+ '/mktBusiOpporOperationAction!delBusiOpporById.json?';
		Ext.MessageBox.confirm('提示', '您确定要删除吗？', function(buttonId) {
			if (buttonId.toLowerCase() == "no") {// 不删除
				return false;
			} else {// 要删除
				Ext.Ajax
						.request({
							url : delUrl,
							params : {
								'opporIdS' : opporId
							},
							waitMsg : '正在删除数据,请等待...',
							success : function() {
								Ext.Msg.alert('提示', '操作成功！');
								reloadCurrentData();
							},
							failure : function(response) {
								var resultArray = Ext.util.JSON
										.decode(response.status);
								if (resultArray == 403) {
									Ext.Msg.alert('提示', response.responseText);
								} else {
									Ext.Msg.alert('提示', '操作失败，失败原因：'
											+ response.responseText);
									reloadCurrentData();
								}
							}
						});
			}
		});
	}
	
	/**
	 * 商机详情
	 */
	var detailFormViewer =[{
		fields:['OPPOR_ID','OPPOR_NAME','OPPOR_STAT','OPPOR_STAGE','OPPOR_SOURCE','OPPOR_TYPE','OPPOR_START_DATE',
		        'OPPOR_END_DATE','OPPOR_DUE_DATE','MKT_TARGET_NAME','MKT_ACTIV_NAME','OPPOR_CONTENT','CUST_NAME','CUST_CONTACT_NAME',
		        'PROD_NAME','CUST_TYPE','CUST_CATEGORY','PLAN_AMOUNT','REACH_AMOUNT','REACH_PROB','PLAN_COST','CREATER_NAME','CREATE_ORG_NAME',
		        'CREATE_DATE_TIME','UPDATE_USER_NAME','UPDATE_ORG_NAME','UPDATE_DATE_TIME','ASSIGN_ORG_NAME'],
		fn:function(OPPOR_ID,OPPOR_NAME,OPPOR_STAT,OPPOR_STAGE,OPPOR_SOURCE,OPPOR_TYPE,OPPOR_START_DATE,
		        OPPOR_END_DATE,OPPOR_DUE_DATE,MKT_TARGET_NAME,MKT_ACTIV_NAME,OPPOR_CONTENT,CUST_NAME,CUST_CONTACT_NAME,
		        PROD_NAME,CUST_TYPE,CUST_CATEGORY,PLAN_AMOUNT,REACH_AMOUNT,REACH_PROB,PLAN_COST,CREATER_NAME,CREATE_ORG_NAME,
		        CREATE_DATE_TIME,UPDATE_USER_NAME,UPDATE_ORG_NAME,UPDATE_DATE_TIME,ASSIGN_ORG_NAME){
			OPPOR_ID.readOnly=true;
			OPPOR_NAME.readOnly=true;
			OPPOR_NAME.cls='x-readOnly';
			OPPOR_NAME.readOnly=true;
			OPPOR_NAME.cls='x-readOnly';
			OPPOR_STAT.readOnly=true;
			OPPOR_STAT.cls='x-readOnly';
			OPPOR_STAGE.readOnly=true;
			OPPOR_STAGE.cls='x-readOnly';
			OPPOR_SOURCE.readOnly=true;
			OPPOR_SOURCE.cls='x-readOnly';
			OPPOR_TYPE.readOnly=true;
			OPPOR_TYPE.cls='x-readOnly';
			OPPOR_START_DATE.readOnly=true;
			OPPOR_START_DATE.cls='x-readOnly';
			OPPOR_END_DATE.readOnly=true;
			OPPOR_END_DATE.cls='x-readOnly';
			OPPOR_DUE_DATE.readOnly=true;
			OPPOR_DUE_DATE.cls='x-readOnly';
			MKT_TARGET_NAME.readOnly=true;
			MKT_TARGET_NAME.cls='x-readOnly';
			MKT_ACTIV_NAME.readOnly=true;
			MKT_ACTIV_NAME.cls='x-readOnly';
			CUST_NAME.readOnly=true;
			CUST_NAME.cls='x-readOnly';
			CUST_CONTACT_NAME.readOnly=true;
			CUST_CONTACT_NAME.cls='x-readOnly';
			PROD_NAME.readOnly=true;
			PROD_NAME.cls='x-readOnly';
			CUST_TYPE.readOnly=true;
			CUST_TYPE.cls='x-readOnly';
			CUST_CATEGORY.readOnly=true;
			CUST_CATEGORY.cls='x-readOnly';
			PLAN_AMOUNT.readOnly=true;
			PLAN_AMOUNT.cls='x-readOnly';
			REACH_AMOUNT.readOnly=true;
			REACH_AMOUNT.cls='x-readOnly';
			REACH_PROB.readOnly=true;
			REACH_PROB.cls='x-readOnly';
			PLAN_COST.readOnly=true;
			PLAN_COST.cls='x-readOnly';
			CREATER_NAME.readOnly=true;
			CREATER_NAME.cls='x-readOnly';
			CREATE_ORG_NAME.readOnly=true;
			CREATE_ORG_NAME.cls='x-readOnly';
			CREATE_DATE_TIME.readOnly=true;
			CREATE_DATE_TIME.cls='x-readOnly';
			UPDATE_USER_NAME.readOnly=true;
			UPDATE_USER_NAME.cls='x-readOnly';
			UPDATE_ORG_NAME.readOnly=true;
			UPDATE_ORG_NAME.cls='x-readOnly';
			UPDATE_DATE_TIME.readOnly=true;
			UPDATE_DATE_TIME.cls='x-readOnly';
			ASSIGN_ORG_NAME.cls='x-readOnly';
			ASSIGN_ORG_NAME.readOnly=true;
			return[OPPOR_ID,OPPOR_NAME,OPPOR_STAT,OPPOR_STAGE,OPPOR_SOURCE,OPPOR_TYPE,OPPOR_START_DATE,
			        OPPOR_END_DATE,OPPOR_DUE_DATE,MKT_TARGET_NAME,MKT_ACTIV_NAME,CUST_NAME,CUST_CONTACT_NAME,
			        PROD_NAME,CUST_TYPE,CUST_CATEGORY,PLAN_AMOUNT,REACH_AMOUNT,REACH_PROB,PLAN_COST,CREATER_NAME,CREATE_ORG_NAME,
			        CREATE_DATE_TIME,UPDATE_USER_NAME,UPDATE_ORG_NAME,UPDATE_DATE_TIME,ASSIGN_ORG_NAME]
		}
	},{
		columnCount:0.95,
		fields:['OPPOR_CONTENT'],
		fn:function(OPPOR_CONTENT){
			OPPOR_CONTENT.readOnly=true;
			OPPOR_CONTENT.cls='x-readOnly';
			return[OPPOR_CONTENT]
		}
	},{
		columnCount:0.95,
		fields:['MEMO'],
		fn:function(MEMO){
			MEMO.readOnly=true;
			MEMO.cls='x-readOnly';
			return[MEMO];
		}
	}];
	        
	/**
	 * 新增商机
	 */
	//var createFormViewer =[];
	
	var createFormCfgs = {
			formButtons : []
		};
	
	/**
	 * 修改商机
	 * @param data
	 * @param cUrl
	 * @returns
	 */
	var editFormViewer =[{
		fields:['OPPOR_ID','OPPOR_NAME','OPPOR_STAT','OPPOR_STAGE','OPPOR_SOURCE','OPPOR_TYPE','OPPOR_START_DATE',
		        'OPPOR_END_DATE','OPPOR_DUE_DATE','MKT_TARGET_ID','MKT_TARGET_NAME','MKT_ACTIV_ID','MKT_ACTIV_ID','MKT_ACTIV_NAME','OPPOR_CONTENT','CUST_ID','CUST_NAME','CUST_CONTACT_NAME','PROD_ID',
		        'PROD_NAME','CUST_TYPE','CUST_CATEGORY','PLAN_AMOUNT','REACH_AMOUNT','REACH_PROB','PLAN_COST','CREATER_NAME','CREATE_ORG_NAME',
		        'CREATE_DATE_TIME','UPDATE_USER_NAME','UPDATE_ORG_NAME','UPDATE_DATE_TIME','ASSIGN_ORG_NAME','CLAIM_USER_NAME','CLAIM_ORG_NAME','EXECUTE_USER_ID','EXECUTE_USER_NAME',
		        'EXECUTE_ORG_ID','EXECUTE_ORG_NAME'],
		fn:function(OPPOR_ID,OPPOR_NAME,OPPOR_STAT,OPPOR_STAGE,OPPOR_SOURCE,OPPOR_TYPE,OPPOR_START_DATE,
		        OPPOR_END_DATE,OPPOR_DUE_DATE,MKT_TARGET_ID,MKT_TARGET_ID,MKT_TARGET_NAME,MKT_ACTIV_ID,MKT_ACTIV_NAME,OPPOR_CONTENT,CUST_ID,CUST_NAME,CUST_CONTACT_NAME,PROD_ID,
		        PROD_NAME,CUST_TYPE,CUST_CATEGORY,PLAN_AMOUNT,REACH_AMOUNT,REACH_PROB,PLAN_COST,CREATER_NAME,CREATE_ORG_NAME,
		        CREATE_DATE_TIME,UPDATE_USER_NAME,UPDATE_ORG_NAME,UPDATE_DATE_TIME,ASSIGN_ORG_NAME,CLAIM_USER_NAME,CLAIM_ORG_NAME,EXECUTE_USER_ID,EXECUTE_USER_NAME,
		        EXECUTE_ORG_ID,EXECUTE_ORG_NAME){
			OPPOR_ID.hidden=true;
			return[OPPOR_ID,OPPOR_NAME,OPPOR_SOURCE,OPPOR_TYPE,PROD_ID,PROD_NAME,OPPOR_DUE_DATE,OPPOR_START_DATE,OPPOR_END_DATE,MKT_TARGET_ID,
			        MKT_TARGET_NAME,MKT_ACTIV_ID,MKT_ACTIV_NAME,CUST_ID,CUST_NAME,CUST_CATEGORY,CUST_TYPE,CUST_CONTACT_NAME,EXECUTE_USER_NAME,EXECUTE_ORG_NAME,
			        PLAN_AMOUNT,PLAN_COST]
		}
	   },{
		   columnCount:0.95,
		   fields:['OPPOR_CONTENT'],
		   fn:function(OPPOR_CONTENT){
			   return[OPPOR_CONTENT]
		   }
	   },{
			columnCount:0.95,
			fields:['MEMO'],
			fn:function(MEMO){
				return[MEMO];
			}
	   }];
	
	var editFormCfgs = {
			formButtons : [{
				text : '提交',
				fn : function(formPanel, basicForm){
					var commitData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
					debugger;
					commitData.executeUserName=basicForm.findField('EXECUTE_USER_NAME').getValue();
					commitData.executeOrgName=basicForm.findField('EXECUTE_ORG_NAME').getValue();
					Ext.Ajax.request({
						url : basepath + '/mktBusiOpporOperationAction!'
						+ 'submitBusiOppor.json',
						method : 'POST',
					    params:commitData ,
						waitMsg : '正在保存数据,请等待...',
						success : function(response) {
							// Ext.Msg.alert('提示', '提交成功！');
							debugger;
							var msg = response.responseText;
							if (msg != null && msg != "") {
								if (msg.substring(0, 1) == "0") {
									msg = "商机成功分配给客户经理“" + msg.substring(1) + "”。";
								} else if (msg.substring(0, 1) == "1") {
									msg = "商机成功分配给机构“" + msg.substring(1) + "”。";
								}
							}
							Ext.Msg.alert('提示', msg);
							reloadCurrentData();
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '提交失败！');
							reloadCurrentData();
						}
					});
				}
			}]
		};
	
	/**
	 * 自定义
	 * @param data
	 * @param cUrl
	 * @returns
	 */
	var customerView=[{
		title:'新增',
		hideTitle:JsContext.checkGrant('mktBusiOppor_create'),
		type:'form',
		groups:[{
			fields:['OPPOR_NAME','OPPOR_CUST_SOURCE','OPPOR_SOURCE','OPPOR_TYPE','OPPOR_START_DATE',
			        'OPPOR_END_DATE','OPPOR_DUE_DATE','MKT_TARGET_NAME','MKT_ACTIV_NAME','OPPOR_CONTENT','CUST_GROUP','CUST_NAME','CUST_CONTACT_NAME',
			        'PROD_NAME','CUST_TYPE','CUST_CATEGORY','PLAN_AMOUNT','PLAN_COST',
			        'EXECUTE_USER_ID','EXECUTE_USER_NAME',
			        'EXECUTE_ORG_ID','EXECUTE_ORG_NAME'],
			fn:function(OPPOR_NAME,OPPOR_CUST_SOURCE,OPPOR_SOURCE,OPPOR_TYPE,OPPOR_START_DATE,
			        OPPOR_END_DATE,OPPOR_DUE_DATE,MKT_TARGET_NAME,MKT_ACTIV_NAME,OPPOR_CONTENT,CUST_GROUP,CUST_NAME,CUST_CONTACT_NAME,
			        PROD_NAME,CUST_TYPE,CUST_CATEGORY,PLAN_AMOUNT,PLAN_COST,
			        EXECUTE_USER_ID,EXECUTE_USER_NAME,
			        EXECUTE_ORG_ID,EXECUTE_ORG_NAME){
				EXECUTE_USER_ID.hidden=true;
				EXECUTE_ORG_ID.hidden=true;
				OPPOR_CUST_SOURCE.hidden=false;
				return[OPPOR_NAME,OPPOR_CUST_SOURCE,OPPOR_SOURCE,OPPOR_TYPE,PROD_NAME,OPPOR_DUE_DATE,OPPOR_START_DATE,OPPOR_END_DATE,
				        MKT_TARGET_NAME,MKT_ACTIV_NAME,CUST_NAME,CUST_CATEGORY,CUST_TYPE,CUST_CONTACT_NAME,EXECUTE_USER_NAME,EXECUTE_ORG_NAME,
				        PLAN_AMOUNT,PLAN_COST,CUST_GROUP,EXECUTE_USER_ID,EXECUTE_ORG_ID]
			}
		   },{
			   columnCount:0.95,
			   fields:['OPPOR_CONTENT'],
			   fn:function(OPPOR_CONTENT){
				   return[OPPOR_CONTENT]
			   }
		   },{
				columnCount:0.95,
				fields:['MEMO'],
				fn:function(MEMO){
					return[MEMO];
				}
		   }],
		   formButtons:[{
			   text:'保存',
			   fn:function(formPanel, basicForm){
				   var commitData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
				   commitData.executeUserName=basicForm.findField('EXECUTE_USER_NAME').getValue();
				   commitData.executeOrgName=basicForm.findField('EXECUTE_ORG_NAME').getValue();
				   var OPPOR_DUE_DATE_P=basicForm.findField('OPPOR_DUE_DATE').getValue();
				   var OPPOR_START_DATE_P=basicForm.findField('OPPOR_START_DATE').getValue();
				   var OPPOR_END_DATE_P=basicForm.findField('OPPOR_END_DATE').getValue();
				   var OPPOR_CUST_SOURCE=basicForm.findField('OPPOR_CUST_SOURCE').getValue();
				   var OPPOR_SOURCE=basicForm.findField('OPPOR_SOURCE').getValue();
				   if(OPPOR_CUST_SOURCE=='1' && OPPOR_SOURCE=='0'){
					   if(!validateForm("CUST","手动创建")){
						   return false;
					   }
				   }else if(OPPOR_CUST_SOURCE=='1' && OPPOR_SOURCE!='0'){
					   if(!validateForm("CUST","其他")){
						   return false;
					   }
				   }else if(OPPOR_CUST_SOURCE=='2' && OPPOR_SOURCE=='0' ){
					   if(!validateForm("CUSTGROUP","手动创建")){
						   return false;
					   }
				   }else if(OPPOR_CUST_SOURCE=='2' && OPPOR_SOURCE!='0'){
					   if(!validateForm("CUSTGROUP","其他")){
						   return false;
					   }
				   }
				   
				   if(!dateValidate(OPPOR_START_DATE_P,OPPOR_END_DATE_P,OPPOR_DUE_DATE_P)){
					   return false;
				   }
				   var saveUrl=basepath+'/mktBusiOpporOperationAction!saveOrUpdateBusiOppor.json?ifgroup='+ifgroup+'&&custStr='+custStr;
				   Ext.Ajax.request({
           			url : saveUrl,
           			method : 'POST',
           			//form : _this.opporPanel.getForm().id,
           			params:commitData,
           			waitMsg : '正在保存数据,请等待...',
           			success : function(response) {
           				Ext.Msg.alert('提示', '保存成功！');
//           				if(!ifGroup&&!ifProd){//如果是视图部分的，不查询
//           					store.load({
//           						params : {
//           							start : 0,
//           							limit : bbar.pageSize
//           						}
//           					});
//           				}
           				reloadCurrentData();
           			},
           			failure : function(response) {
           				Ext.Msg.alert('提示', '保存失败！');
           			}
           		});
			   }
		   },{
				text : '提交',
				fn : function(formPanel, basicForm){
					var commitData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
					commitData.executeUserName=basicForm.findField('EXECUTE_USER_NAME').getValue();
					commitData.executeOrgName=basicForm.findField('EXECUTE_ORG_NAME').getValue();
					var OPPOR_DUE_DATE_P=basicForm.findField('OPPOR_DUE_DATE').getValue();
					var OPPOR_START_DATE_P=basicForm.findField('OPPOR_START_DATE').getValue();
					var OPPOR_END_DATE_P=basicForm.findField('OPPOR_END_DATE').getValue();
					var OPPOR_CUST_SOURCE=basicForm.findField('OPPOR_CUST_SOURCE').getValue();
					var OPPOR_SOURCE=basicForm.findField('OPPOR_SOURCE').getValue();
					if(OPPOR_CUST_SOURCE=='1' && OPPOR_SOURCE=='0'){
						   if(!validateForm("CUST","手动创建")){
							   return false;
						   }
					}else if(OPPOR_CUST_SOURCE=='1' && OPPOR_SOURCE!='0'){
						   if(!validateForm("CUST","其他")){
							   return false;
						   }
					}else if(OPPOR_CUST_SOURCE=='2' && OPPOR_SOURCE=='0' ){
						   if(!validateForm("CUSTGROUP","手动创建")){
							   return false;
						   }
					}else if(OPPOR_CUST_SOURCE=='2' && OPPOR_SOURCE!='0'){
						   if(!validateForm("CUSTGROUP","其他")){
							   return false;
						   }
					}
					if(!dateValidate(OPPOR_START_DATE_P,OPPOR_END_DATE_P,OPPOR_DUE_DATE_P)){
						  return false;
					}
					var commitData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
					commitData.executeUserName=basicForm.findField('EXECUTE_USER_NAME').getValue();
					commitData.executeOrgName=basicForm.findField('EXECUTE_ORG_NAME').getValue();
					Ext.Ajax.request({
						url : basepath + '/mktBusiOpporOperationAction!'
						+ 'submitBusiOppor.json',
						method : 'POST',
					    params:commitData ,
						waitMsg : '正在保存数据,请等待...',
						success : function(response) {
							// Ext.Msg.alert('提示', '提交成功！');
							var msg = response.responseText;
							if (msg != null && msg != "") {
								if (msg.substring(0, 1) == "0") {
									msg = "商机成功分配给客户经理“" + msg.substring(1) + "”。";
								} else if (msg.substring(0, 1) == "1") {
									msg = "商机成功分配给机构“" + msg.substring(1) + "”。";
								}
							}
							Ext.Msg.alert('提示', msg);
							reloadCurrentData();
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '提交失败！');
							reloadCurrentData();
						}
					});
				}
			}]
	},{
		title:'销售活动',
		hideTitle:JsContext.checkGrant('mktBusiOppor_saleActivity'),
		type:'grid',
		url:basepath + '/mktBusiOpporSalesActivQueryAction.json',
		fields : {fields:[
				{name: 'SALES_ACTIV_NAME', text : '销售活动名称'},  
				{name: 'EXEC_DATE', text : '活动执行日期'},  
				{name: 'SALES_STAGE_ORA', text : '销售阶段'},  
				{name: 'EXEC_WAY_ORA', text : '活动执行方式'},  
				{name: 'EXEC_USER_NAME', text : '活动执行人'},  
				{name: 'EXEC_ORG_NAME', text : '活动执行机构'},  
				{name: 'NEXT_CONTACT_TIME', text : '下次联系时间'},  
				{name: 'NEXT_EXEC_WAY_ORA', text : '下次执行方式'},
				{name: 'ACTIV_CONTENT', text : '活动内容'},
				{name: 'NEXT_EXEC_CONTENT', text : '下次执行内容'},
				{name: 'ACTIV_MEMO', text : '备注'}
				]}
			
	},{
		title:'商机跟踪',
		hideTitle:JsContext.checkGrant('mktBusiOppor_opporFollow'),
		type:'grid',
		url:basepath + '/mktBusiOpporHisQueryAction.json',
		fields : {fields:[
		  				{name: 'OPR_USER_NAME', text : '操作人名称'},  
		  				{name: 'OPR_ORG_NAME', text : '操作人机构'},  
		  				{name: 'OPR_DATE_TIME', text : '操作时间'},  
		  				{name: 'OPR_CONTENT', text : '操作内容'}
		  				]}
	},{
		title:'分配商机',
		hideTitle:JsContext.checkGrant('mktBusiOppor_opporAllot'),
		type:'form',
		groups:[{
			columnCount:1,
			fields:[{name:'ALLOCATE_TYPE',text:'分配',translateType:'ALLOCATE',xtype:'combo',
				listeners:{
					'select':function(){
						var a= getCurrentView().contentPanel.form.findField('ASSIGN_ORG_NAME');
						var e= getCurrentView().contentPanel.form.findField('EXECUTE_USER_NAME');
						if(this.value=='1'){
							a.setDisabled(false);
							e.setDisabled(true);
					    }else if(this.value=='2'){
					    	a.setDisabled(true);
							e.setDisabled(false);
					    }
				}}},'ASSIGN_ORG_NAME','EXECUTE_USER_NAME','OPPOR_ID'],
			fn:function(ALLOCATE_TYPE,ASSIGN_ORG_NAME,EXECUTE_USER_NAME,OPPOR_ID){
				OPPOR_ID.hidden=true;
				return[ALLOCATE_TYPE,ASSIGN_ORG_NAME,EXECUTE_USER_NAME,OPPOR_ID];
			}
		}],
		formButtons:[{
    		text : '分配',
    		fn : function(contentPanel, basicForm){
    			var commitData = translateDataKey(basicForm.getFieldValues(),_app.VIEWCOMMITTRANS);
                Ext.Ajax.request({
                    url:basepath
					+ '/mktBusiOpporOperationAction!'
					+ 'allocatBusiOppor.json',
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
    	}]
		
	},{
		title:'商机退回',
		hideTitle:JsContext.checkGrant('mktBusiOppor_back'),
		type:'form',
		groups:[{
			columnCount:1,
			fields:['OPPOR_ID',{name:'REASON',text:'退回理由',xtype:'textarea',allowBlank:false}],
			fn:function(OPPOR_ID,REASON){
				OPPOR_ID.hidden=true;
				return[OPPOR_ID,REASON];
			}
		}],
		formButtons:[{
			text:'退回',
			fn:function(contentPanel,baseform){
				var commitData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
				var reason=baseform.findField('REASON').getValue();
				var saveUrl = basepath + '/mktBusiOpporOperationAction!'
				+ 'backBusiOppor.json?reason='+reason;
				Ext.Ajax.request({
					url : saveUrl,
					method : 'POST',
					params : commitData,
					waitMsg : '正在保存数据,请等待...',
					success : function(response) {
						Ext.Msg.alert('提示', '退回成功！');
						reloadCurrentData();
					},
					failure : function(response) {
						Ext.Msg.alert('提示', '退回失败！');
					}
				});
			}
		}]
		
	}];
	
	var param=null;
	
	
	var beforeviewshow=function(view){
		if(view==getDetailView()||view==getEditView()||view._defaultTitle=='销售活动'||view._defaultTitle=='商机跟踪'||view._defaultTitle=='分配商机'||view._defaultTitle=='商机退回'){ 
			if(!getSelectedData()){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}
			if(view==getEditView()){
				// 1、只有暂存、退回、到期退回、待分配的商机才能修改；
				// 3、有执行人的商机只能由执行人修改，没有执行人的商机只能由待分配机构主管修改；
				var opporStates = getSelectedData().data.OPPOR_STAT;
				// 1、状态控制：控制“0-暂存、5-退回、6-到期退回”状态的商机才能进行维护
				if(opporStates!=0 && opporStates!=1 && opporStates!=5 && opporStates!=6 ){
					Ext.Msg.alert('提示', '只能维护“暂存、待分配、退回、到期退回”状态的商机！');
					return false;
				}
				// 2、控制修改权限：有执行人的商机只能有执行人修改，没有执行人的商机只能由待分配机构主管修改；
				var executeUserId = getSelectedData().data.EXECUTE_USER_ID;// 执行人ID
				var createrId = getSelectedData().data.CREATER_ID;// 创建人ID
				// 有执行人的商机，如果当前用户不是执行人，不能修改商机
				//暂存的商机由创建人或执行人修改
				if(opporStates==0){
					if(executeUserId!=__userId && createrId!=__userId){
						Ext.Msg.alert('提示', '您没有权限维护该商机！');
						return false;
					}
				}
				//待分配的商机的执行人为虚拟客户经理，所以由创建人修改
				if(opporStates!=0){
					if(createrId!=__userId){
						Ext.Msg.alert('提示', '您没有权限维护该商机！');
						return false;
					}
				}
				
//				if(executeUserId!=null && executeUserId!=''){
//					if(executeUserId!=__userId){
//						Ext.Msg.alert('提示', '您没有权限维护该商机！');
//						return false;
//					}
//				}else if(assingOrgId!=null && assingOrgId!=''){ 
//					var queryUrl = basepath + '/mktBusiOpporOperationAction!'
//					+ 'getOrgManager.json';
//					Ext.Ajax.request({
//						url : queryUrl,
//						method : 'POST',
//						sync : true,// 同步
//						waitMsg : '正在查询数据,请等待...',
//						params : {
//							'orgId' : assingOrgId
//						},
//						success : function(response) {
//							var orgManagerId = response.responseText;
//							if (orgManagerId == __userId) {
//							} else {
//								Ext.Msg.alert('提示', '您没有权限维护该商机！');
//								return false;
//							}
//						},
//						failure : function(response) {
//							Ext.Msg.alert('提示', '您没有权限维护该商机！');
//							return false;
//						}
//					});
//				}else if(opporStates=='0'){//商机状态为'暂存'，只能由创建人修改
//					if (createrId != __userId) {
//						Ext.Msg.alert('提示', '您没有权限维护该商机！');
//						return false;
//					}
//				}
			}
			if(view._defaultTitle=='销售活动'){
				var oppor_stat = getSelectedData().data.OPPOR_STAT;
					var OPPOR_ID=getSelectedData().data.OPPOR_ID;
					view.setParameters ({
						oppor_id : OPPOR_ID
		    		}); 	
				
			};
			if(view._defaultTitle=='销售活动'){
				var OPPOR_ID=getSelectedData().data.OPPOR_ID;
				view.setParameters ({
					oppor_id : OPPOR_ID
	    		}); 	
			}
			if(view._defaultTitle=='商机跟踪'){
				var OPPOR_ID=getSelectedData().data.OPPOR_ID;
				view.setParameters ({
					oppor_id : OPPOR_ID
	    		}); 
			};
			if(view._defaultTitle=='分配商机'){
				var oppor_stat = null;// 商机状态
				var opporIdS = '';// 选中的商机记录ID的集合
				var opporId = null;// 商机ID
				var had_6 = false;// 是否有选择“到期退回”的商机
				var had_no_6 = false;// 是否有选择非“到期退回”的商机
				var assignOrgName = null;// 执行机构名称
				var had_allocat_org = false;// 是否有“待分配到机构”的商机
				var had_allocat_no_org = false;// 是否有不是“待分配到机构”的商机
				var assignOrgId = null;// 待分配机构ID
				// 1、控制只能分配“待分配(1)”、“退回(5)”、“到期退回(6)”的商机
				oppor_stat = getSelectedData().data.OPPOR_STAT;
				if(oppor_stat!=1 && oppor_stat!=5 && oppor_stat!=6){
					Ext.Msg.alert('提示', '只能分配“待分配、退回、到期退回”状态的商机！');
					return false;
				}
				if(oppor_stat==6){
					had_6=true;
				}else{
					had_6_no=true;
				}
				assignOrgId=getSelectedData().data.ASSIGN_OGR_ID;
				if(assignOrgId != __units){
					Ext.Msg.alert('提示', '您没有权限对选中的商机进行分配！');
					return false;
				}
				
			}
			if(view._defaultTitle=='商机退回'){
				var oppor_stat = null;// 商机状态、
				// 备注：由于在此判断逻辑中，角色编码是“写死”的，而在不同的实施现场，角色编码可能不同，
				// 所以，此处需要根据具体情况修改角色编码，
				// 在产品中，客户经理角色编码：zhhcm；总行业务经理：zhbm；分行业务经理：fhbm
				var roleCodes = __roleCodes;
				var roleArrs = null;
				var isCustManager = false;// 是否是客户经理
				var isOrgManager = false;// 是否是机构主管
				var userType = null;// 用户角色类型，0：客户经理；1：机构主管；2：客户经理+机构主管
				var opporId = null;
				oppor_stat = getSelectedData().data.OPPOR_STAT;
				opporId=getSelectedData().data.OPPOR_ID;
				if (oppor_stat != 1 && oppor_stat != 4) {
					// Ext.Msg.alert('提示', '只能退回“待分配、执行中”状态的商机！');
					// modify：“商机池”功能中，不能查询出“执行中”状态的商机，所以，此处提示中，状态只有“待分配”
					Ext.Msg.alert('提示', '只能退回“待分配”状态的商机！');
					return false;
			    }
//				if (roleCodes != null && roleCodes != "") {
//					roleArrs = roleCodes.split('$');
//					if (roleArrs != null && roleArrs.length > 0) {
//						for (var i = 0; i < roleArrs.length; i++) {
//							if (roleArrs[i] == 'R107') {// 支行客户经理
//								isCustManager = true;
//								userType = 0;
//							} 
////							else if (roleArrs[i] == 'zhbm' || roleArrs[i] == 'fhbm') {// 机构主管
////								isOrgManager = true;
////								userType = 1;
////							}
//						}
//					}
//					if (isCustManager && isOrgManager) {
//						userType = 2;
//					}
//				};
//				// 3、权限判断
//				var queryUrl = basepath + '/mktBusiOpporOperationAction!'
//						+ 'canReturn.json';
//				Ext.Ajax.request({
//							url : queryUrl,
//							method : 'POST',
//							waitMsg : '正在查询数据,请等待...',
//							params : {
//								'userType' : userType,
//								'opporId' : opporId
//							},
//							success : function(response) {
//								var canReturn = response.responseText;
//								if (canReturn == "true") {
//									// 4、展示窗口
//								} else {
//									Ext.Msg.alert('提示', '您没有权限退回选中的商机！');
//									return false;
//								}
//							},
//							failure : function(response) {
//								Ext.Msg.alert('提示', '您没有权限退回选中的商机！');
//								return false;
//							}
//						});
		}
		}};
	
	/**
	 * 结果域面板滑入后触发,系统提供listener事件方法
	 */
	var viewshow = function(theview){
		if(theview._defaultTitle == '分配商机'){
			if(getSelectedData()){
				var tempData = getSelectedData().data;
				theview.contentPanel.getForm().reset();
				theview.contentPanel.getForm().findField('OPPOR_ID').setValue(tempData.OPPOR_ID);
			}
		}
		if(theview._defaultTitle == '商机退回'){
			if(getSelectedData()){
				theview.contentPanel.getForm().reset();
				theview.contentPanel.getForm().findField('OPPOR_ID').setValue(getSelectedData().data.OPPOR_ID);
			}
		}
		if(theview._defaultTitle == '新增'){
			theview.contentPanel.getForm().findField('OPPOR_CUST_SOURCE').setValue("1");
			theview.contentPanel.getForm().findField('OPPOR_SOURCE').setValue("0");
			getCurrentView().contentPanel.form.findField("EXECUTE_ORG_NAME").setVisible(true);
			  getCurrentView().contentPanel.form.findField("EXECUTE_USER_NAME").setVisible(true);
			  getCurrentView().contentPanel.form.findField("CUST_TYPE").setVisible(true);
			  getCurrentView().contentPanel.form.findField("CUST_CATEGORY").setVisible(true);
			  getCurrentView().contentPanel.form.findField("CUST_NAME").setVisible(true);
			  getCurrentView().contentPanel.form.findField("CUST_CONTACT_NAME").setVisible(true);
			  getCurrentView().contentPanel.form.findField("CUST_GROUP").setVisible(false);
			  getCurrentView().contentPanel.buttons[1].show();
			  ifgroup=false;
			theview.contentPanel.getForm().findField('CUST_GROUP').setVisible(false);
		}
		
	};	
	
	function dateValidate(OPPOR_START_DATE_P,OPPOR_END_DATE_P,OPPOR_DUE_DATE_P){
		// 1、商机“开始日期”不能晚于“完成日期”
		if (OPPOR_START_DATE_P >= OPPOR_END_DATE_P) {
			Ext.Msg.alert('提示', '商机“开始日期”不能晚于或等于“完成日期”！');
			return false;
		}
		// 2、商机“开始日期”不能晚于“商机有效期”
		if (OPPOR_START_DATE_P >= OPPOR_DUE_DATE_P) {
			Ext.Msg.alert('提示', '商机“开始日期”不能晚于或等于“商机有效期”！');
			return false;
		}
		// 3、商机“完成日期”不能晚于“商机有效期”
		if (OPPOR_END_DATE_P >= OPPOR_DUE_DATE_P) {
			Ext.Msg.alert('提示', '商机“完成日期”不能晚于或等于“商机有效期”！');
			return false;
		}
		return true;
	}
	
	/**
	 * 页面数据验证方法
	 */
	function validateForm(P,K){
		var OPPOR_NAME=getCurrentView().contentPanel.form.findField("OPPOR_NAME").getValue();
		var OPPOR_TYPE=getCurrentView().contentPanel.form.findField("OPPOR_TYPE").getValue();
		var PROD_ID=getCurrentView().contentPanel.form.findField("PROD_ID").getValue();
		var OPPOR_DUE_DATE=getCurrentView().contentPanel.form.findField("OPPOR_DUE_DATE").getValue();
		var OPPOR_START_DATE=getCurrentView().contentPanel.form.findField("OPPOR_START_DATE").getValue();
		var OPPOR_END_DATE=getCurrentView().contentPanel.form.findField("OPPOR_END_DATE").getValue();
		var CUST_ID=getCurrentView().contentPanel.form.findField("CUST_ID").getValue();
		var CUST_CATEGORY=getCurrentView().contentPanel.form.findField("CUST_CATEGORY").getValue();
		var CUST_TYPE=getCurrentView().contentPanel.form.findField("CUST_TYPE").getValue();
		var EXECUTE_USER_ID=getCurrentView().contentPanel.form.findField("EXECUTE_USER_ID").getValue();
		var EXECUTE_ORG_ID=getCurrentView().contentPanel.form.findField("EXECUTE_ORG_ID").getValue();
		var OPPOR_CONTENT=getCurrentView().contentPanel.form.findField("OPPOR_CONTENT").getValue();
		var CUST_GROUP=getCurrentView().contentPanel.form.findField("CUST_GROUP").getValue(); 
		if(P=='CUST' && K=='手动创建'){
			if(OPPOR_NAME=='' || OPPOR_TYPE=='' || PROD_ID=='' || OPPOR_DUE_DATE=='' || OPPOR_START_DATE=='' || OPPOR_END_DATE=='' 
				|| CUST_ID=='' || CUST_CATEGORY=='' || CUST_TYPE=='' ||  EXECUTE_USER_ID=='' || EXECUTE_ORG_ID=='' || OPPOR_CONTENT=='' ){
				
				 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
   	    		 return false;
			} 
		}
		if(P=='CUST' && K!='手动创建'){
			if(OPPOR_NAME=='' || OPPOR_TYPE==''  || OPPOR_DUE_DATE=='' || OPPOR_START_DATE=='' || OPPOR_END_DATE=='' 
				|| CUST_ID=='' || CUST_CATEGORY=='' || CUST_TYPE=='' ||  EXECUTE_USER_ID=='' || EXECUTE_ORG_ID=='' || OPPOR_CONTENT==''){
				 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
		    		 return false;
			}
		}
		if(P=='CUSTGROUP' && K=='手动创建'){
			if(OPPOR_NAME=='' || OPPOR_TYPE=='' || PROD_ID==''  || OPPOR_DUE_DATE=='' || OPPOR_START_DATE=='' || OPPOR_END_DATE=='' 
				  || OPPOR_CONTENT=='' || CUST_GROUP==''){
				 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
		    		 return false;
			}
		}
		if(P=='CUSTGROUP' && K!='手动创建'){
			if(OPPOR_NAME=='' || OPPOR_TYPE=='' || OPPOR_DUE_DATE=='' || OPPOR_START_DATE=='' || OPPOR_END_DATE=='' 
				  || OPPOR_CONTENT=='' || CUST_GROUP==''){
				 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
		    		 return false;
			}
		}
		
		return true;
	}



