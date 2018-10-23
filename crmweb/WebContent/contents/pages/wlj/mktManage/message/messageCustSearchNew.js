/**
 * 渠道自动营销管理-客户查询
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js', // 机构放大镜
	    		'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',	//用户放大镜
	    		'/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js'
		        ]);
    
    var url = basepath+'/ocrmCustPersonQuery.json';
    var needCondition = true;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = true;
    
	var lookupTypes=[
	                 'XD000040',//证件类型
	                 'XD000080',//客户类型
	                 'P_CUST_GRADE',
	                 {
	                	 TYPE : 'MODEL_LOOKUP',
	         			url : '/ocrmFMmSysMsg!searchMktDic.json',
	         			key : 'KEY',
	         			value : 'VALUE',
	         			jsonRoot : 'json.data'
	                 },
	                 'MODEL_TYPE',
	                 'FXQ_RISK_LEVEL',//反洗钱风险等级
	                 'CUST_RISK_CHARACT',
	                 'CDE0100033',
	                 'PRE_CUST_LEVEL',
	                 'CUSTOMER_GROUP_TYPE',
	                 'CUSTOMER_SOURCE_TYPE',
	                 'GROUP_MEMEBER_TYPE',
	                 'SHARE_FLAG'
	                 ];
    
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	//短信模板
	var modelstore = new Ext.data.Store({
		sortInfo: {
		    field: 'key',
		    direction: 'ASC' 
		},
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath + '/ocrmFMmSysMsg!searchMktDic.json',
				success:function(response){
//					alert(response.responseText);
				}
			}),
			reader : new Ext.data.JsonReader({
				root : 'data'
			}, [ {name: 'key', mapping: 'KEY'},
			      {name: 'value', mapping: 'VALUE'}])
	});
	/**
	 * 适用渠道
	 * @type 
	 */
	
	var modelTypeStore = new Ext.data.Store({
		sortInfo: {
		    field: 'modelType',
		    direction: 'ASC' 
		},
			restful:true,   
			autoLoad :true,
			proxy : new Ext.data.HttpProxy({
				url :basepath + '/ocrmFMmSysMsg!searchModelType.json',
				success:function(response){
//					alert(response.responseText);
				}
			}),
			reader : new Ext.data.JsonReader({
				root : 'data'
			}, [ {name: 'modelType', mapping: 'MODEL_TYPE'}
			      ])
	});
	
	var modelSource=null;
	
	var fields = [
					 {name:'CUST_ID',text:'客户编号',resutlWidth:60,searchField:true,dataType:'string'},
					 {name:'CUST_NAME',text:'客户名称',resutlWidth:120,searchField:true,dataType:'string'},
					 {name:'CUST_TYPE',text:'客户类型',resutlWidth:60,translateType:'XD000080',searchField:true},
					 {name:'IDENT_TYPE',text:'证件类型',resutlWidth:60,translateType:'XD000040',searchField:true},
					 {name:'IDENT_NO',text:'证件号码',resutlWidth:60,searchField:true,dataType:'string'},
					 {name:'CUST_LEVEL',text:'客户级别',resutlWidth:60,searchField:true,translateType:'PRE_CUST_LEVEL'},
					 {name:'MOBILE_PHONE',text:'联系电话',resutlWidth:60,searchField:true},
					 {name:'EMAIL',text:'邮件',resutlWidth:60,searchField:true},
					 {name:'WEIXIN',text:'微信',resutlWidth:60,searchField:true},
					 {name:'LINKMAN_NAME',text:'联系人姓名',resutlWidth:60,dataType:'string'},
					 {name:'LINKMAN_TEL',text:'联系人电话',resutlWidth:60,dataType:'string'},
					 //{name:'ACCT_NO',text:'账号',resutlWidth:60,gridField:false,searchField:true},
					 //{name:'ORG_NAME',text:'归属机构',xtype : 'wcombotree',innerTree:'ORGTREE',resutlWidth:80,searchField: true, showField:'text',hideField:'UNITID',allowBlank:false},	   
					 {name: 'MGR_NAME',text:'所属客户经理',xtype:'userchoose',hiddenName:'MGR_ID',resutlWidth:150,singleSelect: false,searchField: true},
					 {name: 'BELONG_TEAM_HEAD_NAME',text:'所属团队负责人',xtype:'userchoose',hiddenName:'MGR_ID1',resutlWidth:160,singleSelect: false,searchField: true}
//					 {name:'RISK_LEVEL',text:'风险等级',resutlWidth:60,translateType:'CUST_RISK_CHARACT'},
//					 {name:'CREDIT_LEVEL',text:'客户信用评级',resutlWidth:80,searchField:true,translateType:'CDE0100033'},
//					 {name:'TOTAL_DEBT',text:'总负债',resutlWidth:60,dataType:'number'},
//					 {name:'BL_NAME',text:'业务条线',resutlWidth:80 ,xtype : 'wcombotree',innerTree:'BLTREE',searchField: true, showField:'text',hideField:'BL_ID',allowBlank:false},
//					 {name:'FAXTRADE_NOREC_NUM',text:'传真交易正本未回收数量',resutlWidth:150,dataType:'numberNoDot'},
//					 {name:'CURRENT_AUM',text:'管理总资产时点值',resutlWidth:150,dataType:'number'}
	              ];
	var treeLoaders = [{
		key : 'PRODUCTLOADER',
		url : basepath + '/productCatlTreeAction.json',
		parentAttr : 'CATL_PARENT',
		locateAttr : 'CATL_CODE',
		rootValue : '0',
		textField : 'CATL_NAME',
		idProperties : 'CATL_CODE',
		jsonRoot:'json.data'
	}];
	
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
	},new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('custmktMsg_export'),
        formPanel : 'searchCondition',
        url : basepath+'/ocrmCustPersonQuery.json'
    })];
	
	//var messageRemark = new Ext.form.textarea({name:'',fieldLabel:'',anchor:'90%'})
	var modelNameGloble='';
	var modelIdGloble='';
	var customerView = [{
	                	title:'自动营销推送',
	                	hideTitle:JsContext.checkGrant('custMktMsg'),
	                	type: 'form',
	                	groups:[{
	                		columnCount : 1,
	                		fields : ['CUST_ID','CUST_NAME',
	                		{name: 'PROD_NAME',dataType:'productChoose',hiddenName:'PRODUCT_ID',singleSelect:true,allowBlank:false,is_per:true,
	      		        	  text:"产品名称",searchField : true,prodState:(''),riskLevel:(''),callback:function(a){
	      		        		modelstore.load({
	      		        			params:{
	      		        				'code':a[0].data.CATL_CODE
	      		        			},
	      		        			callback:function(){
	      		        				if(modelstore.getCount()!=0){
	      		        				}else{
	      		        					Ext.Msg.alert("提示", "本产品没有相关的营销模板!");
	      		        					
	      		        				}
	      		        			}
	      		        		});
	      		        		  
	      		        	  }},
	                		{name:'MODEL_TYPE',text:'推送渠道',translateType:'MODEL_TYPE',allowBlank:false,multiSelect:true,multiSeparator:',',
	                			listeners : {
	                				"select" : function() {
	                					var telNum_p=getCurrentView().contentPanel.form.findField("MOBILE_PHONE").getValue();
	                					var PROD_NAME_p=getCurrentView().contentPanel.form.findField("PROD_NAME").getValue();
	                					var MODEL_SOURCE_p=getCurrentView().contentPanel.form.findField("modelSource").getValue();
	                					//var MODEL_ID_p=getCurrentView().contentPanel.form.findField("modelId").getValue();
	                					if(PROD_NAME_p==''){
	                						Ext.Msg.alert('提示', '请先选择产品！');
	                						this.value='';
	                						return false;
	                					};
	                					if(MODEL_SOURCE_p==''){
	                						Ext.Msg.alert('提示', '请先选择推送内容来源！');
	                						this.value='';
	                						return false;
	                					};
//	                					if(1==this.value && telNum_p==''){
//	                						Ext.Msg.alert('提示', '本客户没有手机信息！');
//	                						return false;
//	                					}
    								}
	                			}
	                		},
	                		{name:'MESSAGE_REMARK',text:'模板内容',xtype:'textarea'},'MOBILE_PHONE','EMAIL','WEIXIN'
	                		 ],
	                		fn : function(CUST_ID,PERSONAL_NAME,PROD_NAME,MODEL_TYPE,MESSAGE_REMARK,MOBILE_PHONE,EMAIL,WEIXIN){
	                			var MODEL_SOURCE=new Ext.form.ComboBox({
	                				store : ['模板','自定义'],
	        						xtype : 'combo', 
	        						resizable : true,
	        						width:100,
	        						anchor:'90%',
	        						fieldLabel : '<span style="color:red">*</span>推送内容来源',
	        						hiddenName : 'modelSource',
	        						name : 'modelSource',
	        						valueField : 'key',
	        						labelStyle : 'text-align:right;',
	        						displayField : 'value',
	        						mode : 'local',
	        						allowBlank:false,
	        						forceSelection : true,
	        						triggerAction : 'all',
	        						emptyText : '请选择',
	        						listeners : {
	        							"select" : function() {
	        								var MODEL_TYPE_p=getCurrentView().contentPanel.form.findField("MODEL_TYPE").getValue();
	        								var PROD_NAME_p=getCurrentView().contentPanel.form.findField("PROD_NAME").getValue();
	        								if(PROD_NAME_p==''){
	        									Ext.Msg.alert('提示', '请先选择产品！');
	        									this.value='';
	        									return false;
	        								}
	        								var v=getCurrentView().getValues().modelSource;
		        							if(v=='自定义'){
		        								MODEL_ID.hide();
		        								getCurrentView().contentPanel.form.findField("MESSAGE_REMARK").setValue();
		        								getCurrentView().contentPanel.form.findField("MESSAGE_REMARK").setDisabled(false);
		        								getCurrentView().contentPanel.form.findField("MODEL_TYPE").setReadOnly(false);
		        								getCurrentView().contentPanel.form.items.items[4].allowBlank=true;
		        							}else if(v=='模板'){
		        								MODEL_ID.show();
		        								getCurrentView().contentPanel.form.findField("MESSAGE_REMARK").setValue();
		        								getCurrentView().contentPanel.form.findField("MESSAGE_REMARK").setDisabled(true);
		        								getCurrentView().contentPanel.form.items.items[4].allowBlank=false;
		        							}
	        							}
	        						}
	                			});
	                			var MODEL_ID = new Ext.form.ComboBox({
	        		            	store : modelstore, 
	        						xtype : 'combo', 
	        						hidden:true,
	        						width:100,
	        						anchor:'90%',
	        						fieldLabel : '<span style="color:red">*</span>营销模板',
	        						hiddenName : 'modelName',
	        						name : 'modelId',
	        						valueField : 'key',
	        						labelStyle : 'text-align:right;',
	        						displayField : 'value',
	        						mode : 'local',
	        						allowBlank:true,
	        						forceSelection : true,
	        						triggerAction : 'all',
	        						emptyText : '请选择',
	        						listeners : {
	        							"select" : function() {
	        								//var model = getCurrentView().getValues().modelId;
	        								var PERSONAL_NAME = getCurrentView().getValues().PERSONAL_NAME;
	        								var PROD_NAME=getCurrentView().getValues().PROD_NAME;
	        								var PRODUCT_ID=getCurrentView().getValues().PRODUCT_ID;
	        								modelNameGloble=MODEL_ID.getRawValue();
	        								modelIdGloble=MODEL_ID.getValue();
	        								modelTypeStore.load({
												params : {
													'id' : MODEL_ID.getValue()
												},
												callback : function() {
													var modelType=modelTypeStore.data.items[0].data.modelType;
													getCurrentView().contentPanel.form.findField("MODEL_TYPE").setValue(modelType);
													getCurrentView().contentPanel.form.findField("MODEL_TYPE").setReadOnly(true);
												}
											});
	        			        			 // Ext.Msg.wait('正在生成短信内容......','系统提示');
	        			        			 Ext.Ajax.request({
	        		     						url : basepath + '/ocrmFMmSysMsg!getMessage.json',
	        		     						method : 'POST',
	        		     						params:{
	        						            	 'modelId':this.value,
	        						            	 'custName':PERSONAL_NAME,
	        						            	 'prodName':PROD_NAME,
	        						            	 'prodId':PRODUCT_ID
	        						             },
	        		     						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
	        		     						success : function(response) {
	        		     							var mr = response.responseText;
	        		     							getCurrentView().setValues({
	        											MESSAGE_REMARK:mr
	        										});
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
	        							}
	        						}
	        		            
	        					});
	                			MOBILE_PHONE.hidden=true;
	                			WEIXIN.hidden=true;
	                			EMAIL.hidden=true;
	                			return [CUST_ID,PERSONAL_NAME,PROD_NAME,MODEL_SOURCE,MODEL_ID,MODEL_TYPE,MESSAGE_REMARK,MOBILE_PHONE,WEIXIN,EMAIL];
	                		}
	                	}],
	                	formButtons:[{
	                		text : '提交',
	                		hidden:JsContext.checkGrant('custmktMsg_commit'),
	                		fn : function(contentPanel, baseform){
	                			if (!baseform.isValid()) {
	                  	    		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
	                  	    		 return false;
	                  	    	    }
	                			var custId_v=getCurrentView().contentPanel.form.findField("CUST_ID").getValue();
	                			var custName_v=getCurrentView().contentPanel.form.findField("CUST_NAME").getValue();
	                			var messageRemark_v=getCurrentView().contentPanel.form.findField("MESSAGE_REMARK").getValue();
	                			var telNum_v=getCurrentView().contentPanel.form.findField("MOBILE_PHONE").getValue();
	                			//var modelId_v=baseform.findField("modelId").getValue();
	                			var modelSource=getCurrentView().contentPanel.form.findField("modelSource").getValue();
	                			var modelName_v=modelNameGloble;
	                			var modelType_v=getCurrentView().contentPanel.form.findField("MODEL_TYPE").getValue();
	                			var prodName_v=getCurrentView().contentPanel.form.findField("PROD_NAME").getValue();
	                			var prodId_v=getCurrentView().contentPanel.form.findField("PRODUCT_ID").getValue();
	                			var EMAIL_v=getCurrentView().contentPanel.form.findField("EMAIL").getValue();
	                			var WEIXIN_v=getCurrentView().contentPanel.form.findField("WEIXIN").getValue();
	                			Ext.Msg.wait('正在提交申请,请等待...','提示');
	                			Ext.Ajax.request({
	        						url : basepath + '/ocrmFMmSysMsg!initFlow.json',
	        						method : 'GET',
	        						params : {
	        							'custId':custId_v,
	                                	 'custName':custName_v,
	                                	 'messageRemark':messageRemark_v,
	                                	 'telNum':telNum_v,
	                                	 'prodName':prodName_v,
	                                	 'prodId':prodId_v,
	                                	 'modelId':modelIdGloble,
	                                	 'modelType':modelType_v,
	                                	 'modelSource':modelSource,
	                                	 'modelName':modelName_v,
	                                	 'email':EMAIL_v,
	                                	 'weixin':WEIXIN_v
	        						},
	        						waitMsg : '正在提交申请,请等待...',		 								
	        						success : function(response) {
	        							var ret = Ext.decode(response.responseText);
										var instanceid = ret.instanceid;//流程实例ID
										var currNode = ret.currNode;//当前节点
										var nextNode = ret.nextNode;//下一步节点
										selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
//	        							var ret = Ext.decode(response.responseText);
//	        							var instanceid = ret.instanceid;//流程实例ID
//	        							Ext.Ajax.request({
//	        								url : basepath + '/ocrmFMmSysMsg!initFlowJob.json',
//	        								method : 'POST',
//	        								params : {
//	        									instanceid:instanceid //将id传给后台关联流程的实例号（唯一）
//	        								},success : function() {
//	        									Ext.Msg.alert('提示', '提交成功!');	
//	        									reloadCurrentData();
//	        									hideCurrentView();
//	        								},	
//	        								failure : function() {
//	        									Ext.Msg.alert('提示', '提交失败,请手动到代办任务中提交!');	
//	        								}
//	        							});
	        						},
	        						failure : function() {
	        							Ext.Msg.alert('提示', '操作失败');
	        							reloadCurrentData();
	        						}
	        					});
	                		}
	                	},{
	                		text : '保存',
	                		hidden:JsContext.checkGrant('custmktMsg_save'),
	                		fn : function(contentPanel, baseform){
	                			if (!baseform.isValid()) {
	                  	    		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
	                  	    		 return false;
	                  	    	    }
	                			var custId_v=getCurrentView().contentPanel.form.findField("CUST_ID").getValue();
	                			var custName_v=getCurrentView().contentPanel.form.findField("CUST_NAME").getValue();
	                			var messageRemark_v=getCurrentView().contentPanel.form.findField("MESSAGE_REMARK").getValue();
	                			var telNum_v=getCurrentView().contentPanel.form.findField("MOBILE_PHONE").getValue();
	                			//var modelId_v=baseform.findField("modelId").getValue();
	                			var modelSource=getCurrentView().contentPanel.form.findField("modelSource").getValue();
	                			var modelName_v=modelNameGloble;
	                			var modelType_v=getCurrentView().contentPanel.form.findField("MODEL_TYPE").getValue();
	                			var prodName_v=getCurrentView().contentPanel.form.findField("PROD_NAME").getValue();
	                			var prodId_v=getCurrentView().contentPanel.form.findField("PRODUCT_ID").getValue();
	                			var EMAIL_v=getCurrentView().contentPanel.form.findField("EMAIL").getValue();
	                			var WEIXIN_v=getCurrentView().contentPanel.form.findField("WEIXIN").getValue();
	                			Ext.Msg.wait('正在保存,请等待...','提示');
	                            Ext.Ajax.request({
	                                url: basepath + '/ocrmFMmSysMsg!saveData.json',
	                                method: 'POST',
	                                params: {
	                                	 'custId':custId_v,
	                                	 'custName':custName_v,
	                                	 'messageRemark':messageRemark_v,
	                                	 'telNum':telNum_v,
	                                	 'prodName':prodName_v,
	                                	 'prodId':prodId_v,
	                                	 'modelId':modelIdGloble,
	                                	 'modelType':modelType_v,
	                                	 'modelSource':modelSource,
	                                	 'modelName':modelName_v,
	                                	 'email':EMAIL_v,
	                                	 'weixin':WEIXIN_v
	                                },
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
	                }];
	
	var beforeviewshow=function(view){
		if(view._defaultTitle=='自动营销推送'){ 
			if(!getSelectedData()){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}
		}
	};
	/**
	 * 结果域面板滑入后触发,系统提供listener事件方法
	 */
	var viewshow = function(theview){
		if(theview._defaultTitle == '自动营销推送'){
			if(getSelectedData()){
				var tempData = getSelectedData().data;
				theview.contentPanel.getForm().reset();
				theview.contentPanel.getForm().loadRecord(getSelectedData());
			}
		}
	};
