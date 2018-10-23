   
/**
 * 渠道自动营销管理-客户群查询
 */
imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',
	         	'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
	         	'/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js',
	         	'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
	         	'/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js'
		        ]);
    
    var url = basepath+'/ocrmCustGroupQuery.json';
    var needCondition = true;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = true;
	var lookupTypes=[
	                 'CUSTOMER_GROUP_TYPE',
	                 'CUSTOMER_SOURCE_TYPE',
	                 {
	                	TYPE : 'MODEL_LOOKUP',
	         			url : '/ocrmFMmSysMsg!searchMktDic.json',
	         			key : 'KEY',
	         			value : 'VALUE',
	         			jsonRoot : 'json.data'
	                 },
	                 'MODEL_TYPE',
	                 'GROUP_MEMEBER_TYPE',
	                 'SHARE_FLAG',
	                 'XD000040',
	                 'XD000078'
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
			      {name: 'value', mapping: 'VALUE'} ])
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
	
	var fields = [
				{name:'ID',text:'客户群ID',hidden:true},
	            {name:'CUST_BASE_NUMBER',text:'客户群编号'},
	            {name:'CUST_BASE_NAME',text:'客户群名称',searchField:true},
	            {name:'GROUP_TYPE',text:'客户群分类',translateType:'CUSTOMER_GROUP_TYPE',searchField:true},
	            {name:'CUST_FROM',text:'客户来源',id:'custFrom',translateType:'CUSTOMER_SOURCE_TYPE',searchField:true},
	            {name:'GROUP_MEMBER_TYPE',text:'群成员类型',translateType:'GROUP_MEMEBER_TYPE',searchField:true},
	            {name:'SHARE_FLAG',text:'共享范围',translateType:'SHARE_FLAG',searchField:true},
	            {name:'MEMBERSNUM',text:'成员数'},
	            {name:'CREATENAME',text:'创建人'},
	            {name:'CUST_BASE_CREATE_DATE',text:'创建日期',format:'Y-m-d'},
	            {name:'CUST_BASE_CREATE_ORG',text:'创建机构ID',hidden:true},
	            {name:'CUST_BASE_CREATE_ORG_NAME',text:'创建机构'},
	            {name:'CUST_BASE_CREATE_NAME',text:'客户群创建人',hidden:true},
	            {name:'RECENT_UPDATE_USER',text:'最近更新人',gridField:false},
	            {name:'RECENT_UPDATE_ORG',text:'最近更新机构',gridField:false},
	            {name:'RECENT_UPDATE_DATE',text:'最近更新日期',format:'Y-m-d'},
	            {name:'CUST_BASE_DESC',text:'客户群描述',hidden:true} ,
	            {name:'CUST_ID',text:'客户号',searchField:true,gridField:false},
	            {name:'CUST_NAME',text:'客户名称',searchField:true,gridField:false} ,
	            {name:'CERT_TYPE',text:'证件类型',searchField:true,hidenName:'CERT_TYPE',translateType:'XD000040',gridField:false} ,
	            {name:'CERT_NUM',text:'证件号码',gridField:false} ,
	            {name:'CUST_BASE_CREATE_DATE_S',gridField:false,text:'开始创建日期',xtype:'datefield',format:'Y-m-d',searchField:true} ,
	            {name:'CUST_BASE_CREATE_DATE_E',text:'结束创建日期',gridField:false,xtype:'datefield',format:'Y-m-d',searchField:true},
	            {name:'MAIN_USER_NAME',text:'客户经理',hiddenName:'custMgrId',searchField:true,xtype:'userchoose',gridField:false} ,
	            {name:'MIAN_ORG_NAME',text:'归属机构',hiddenName:'CUST_ORG_ID',xtype:'orgchoose',searchField:true,gridField:false} 
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
	},new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('custGroupMktMsg_export'),
        formPanel : 'searchCondition',
        url : basepath+'/ocrmCustGroupQuery.json'
    })];
	
	//定义复选框
	var sm = new Ext.grid.CheckboxSelectionModel();
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
        {header : '客户号',dataIndex : 'custId',sortable : true,width : 150},
	    {header : '客户名称',dataIndex : 'personalName',width : 200,sortable : true},
	    {header : '证件类型',dataIndex : 'identType',width : 150,sortable : true,renderer:function(value){
 			var val = translateLookupByKey("XD000078",value);
			return val?val:"";
		 }},
	    {header : '证件号码',dataIndex : 'identNo',width : 150,sortable : true},
	    {header : '客户级别',dataIndex : 'custGrade',width : 100,sortable : true,hidden:true},
	    {header : '手机号码',dataIndex : 'mobilePhone',width : 100,sortable : true,hidden:true},
	    {header : '邮箱',dataIndex : 'email',width : 100,sortable : true,hidden:true},
	    {header : '微信',dataIndex : 'weixin',width : 100,sortable : true,hidden:true}
	]);
	
	/**
	 * 客户查询结果数据存储
	 */
	var store = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/ocrmCustGroupMemberQuery.json'}),
        reader: new Ext.data.JsonReader({
        	totalProperty : 'json.count',
        	root:'json.data'
        }, [
			{name: 'custId',mapping :'CUST_ID'},
			{name: 'personalName',mapping :'CUST_NAME'},
			{name: 'identType',mapping :'IDENT_TYPE'},//证件类型
			{name: 'identNo',mapping :'IDENT_NO'},//证件号码
			{name: 'custGrade',mapping :'CUST_GRADE'},//客户级别
			{name: 'mobilePhone',mapping :'MOBILE_PHONE'},//手机号码
			{name: 'email',mapping :'EMAIL'},//邮箱
			{name: 'weixin',mapping :'WEIXIN'}//微信
		])
	});
	var custGroupMemberGrid = new Ext.grid.GridPanel({
		title : '客户群成员',
		store : store,
		frame : true,
		height : 200,
		cm : cm,
		sm:sm,
		region : 'center',
		clicksToEdit : 1,
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	var modelNameGloble='';
	var modelIdGloble='';
	var customerView = [{
	                	title:'自动营销推送',
	                	hideTitle:JsContext.checkGrant('custGroupMktMsg'),
	                	type: 'form',
	                	groups:[{
	                		columnCount : 1,
	                		fields : ['CUST_BASE_NUMBER','CUST_BASE_NAME',
	                		//{name: 'PRODUCT_ID',dataType:'string',text:"产品名称ID",hidden:true},
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
	                					var PROD_NAME_p=getCurrentView().contentPanel.form.findField("PROD_NAME").getValue();
	                					var MODEL_SOURCE_p=getCurrentView().contentPanel.form.findField("modelSource").getValue();
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
	                		{name:'MESSAGE_REMARK',text:'模板内容',xtype:'textarea',allowBlank:false}
	                		 ],
	                		fn : function(CUST_BASE_NUMBER,CUST_BASE_NAME,PROD_NAME,MODEL_TYPE,MESSAGE_REMARK){
	                			//MESSAGE_REMARK.hidden=true;
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
		        								//getCurrentView().contentPanel.form.findField("MESSAGE_REMARK").setHidden(false);
		        								getCurrentView().contentPanel.form.findField("MESSAGE_REMARK").setValue();
		        								getCurrentView().contentPanel.form.findField("MESSAGE_REMARK").setDisabled(false);
		        								getCurrentView().contentPanel.form.findField("MODEL_TYPE").setReadOnly(false);
		        								getCurrentView().contentPanel.form.items.items[4].allowBlank=true;
		        							}else if(v=='模板'){
		        								MODEL_ID.show();
		        								//getCurrentView().contentPanel.form.findField("MESSAGE_REMARK").setHidden(false);
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
	        								modelNameGloble=MODEL_ID.getRawValue();
	        								modelIdGloble=MODEL_ID.getValue();
	        			        			 //Ext.Msg.wait('正在生成短信内容......','系统提示');
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
	        			        			 Ext.Ajax.request({
	        		     						url : basepath + '/ocrmFMmSysMsg!getMessage.json',
	        		     						method : 'POST',
	        		     						params:{
	        						            	 'modelId':this.value
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
	                			return [CUST_BASE_NUMBER,CUST_BASE_NAME,PROD_NAME,MODEL_SOURCE,MODEL_ID,MODEL_TYPE,MESSAGE_REMARK];
	                		}
	                	},{
	                		columnCount:1,
	                		fields:[],
	                		fn:function(){
	                			return[custGroupMemberGrid];
	                		}
	                	}],
	                	formButtons:[{
	                		text : '提交',
	                		hidden:JsContext.checkGrant('custGroupMktMsg_commit'),
	                		fn : function(contentPanel, baseform){
	                			if (!baseform.isValid()) {
	                  	    		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
	                  	    		 return false;
	                  	    	    }
	                  	    	var checkedNodes = custGroupMemberGrid.getSelectionModel().selections.items;
	                  	    	if(checkedNodes.length==0){
	                  	    		Ext.MessageBox.alert('系统提示信息', '请选择客户群成员！');
	                  	    		 return false;
	                  	    	}
	                			var productId=baseform.findField("PRODUCT_ID").getValue();
	                			var productName=baseform.findField("PROD_NAME").getValue();
	                			var groupId=baseform.findField("CUST_BASE_NUMBER").getValue();
	                			var modelSource=baseform.findField("modelSource").getValue();
	                			var modelType=baseform.findField("MODEL_TYPE").getValue();
	                			var message=baseform.findField("MESSAGE_REMARK").getValue();
	                			var checkedNodes = custGroupMemberGrid.getSelectionModel().selections.items;
	                			//var custIds= new Array();
	                			//var modelId=baseform.findField("modelId").getValue();
	                			var custIds='';
	                			var personalNames='';
	                			var mobilePhones='';
	                			for(var i=0;i<checkedNodes.length;i++){
	                				custIds+=checkedNodes[i].data.custId+',';
	                				personalNames+=checkedNodes[i].data.personalName+',';
	                				mobilePhones+=checkedNodes[i].data.mobilePhone+',';
	                			}
	                			custIds.toString();
	                			Ext.Msg.wait('正在提交申请,请等待...','提示');
	                			Ext.Ajax.request({
	        						url : basepath + '/ocrmFMmSysMsg!send.json',
	        						method : 'GET',
	        						params : {
	        							'prodId':productId,
	                                	'modelId':modelIdGloble,
	                                	'custIds':custIds,
	                                	'prodName':productName,
	                                	'groupId':groupId,
	                                	'modelSource':modelSource,
	                                	'message':message,
	                                	'modelType':modelType,
	                                	'modelName':modelNameGloble,
	                                    'personalNames':personalNames,
	                                    'mobilePhones':mobilePhones
	        						},
	        						waitMsg : '正在提交申请,请等待...',		 								
	        						success : function(response) {
	        							Ext.Msg.alert('提示', '操作成功');
	        						},
	        						failure : function() {
	        							Ext.Msg.alert('提示', '操作失败');
	        							reloadCurrentData();
	        						}
	        					});
	                		}
	                	},{
	                		text : '保存',
	                		hidden:JsContext.checkGrant('custGroupMktMsg_save'),
	                		fn : function(contentPanel, baseform){
	                			if (!baseform.isValid()) {
	                  	    		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
	                  	    		 return false;
	                  	    	    }
	                			var productId=baseform.findField("PRODUCT_ID").getValue();
	                			var productName=baseform.findField("PROD_NAME").getValue();
	                			var groupId=baseform.findField("CUST_BASE_NUMBER").getValue();
	                			var modelSource=baseform.findField("modelSource").getValue();
	                			var modelType=baseform.findField("MODEL_TYPE").getValue();
	                			var message=baseform.findField("MESSAGE_REMARK").getValue();
	                			var checkedNodes = custGroupMemberGrid.getSelectionModel().selections.items;
	                			//var custIds= new Array();
	                			//var modelId=baseform.findField("modelId").getValue();
	                			var custIds='';
	                			for(var i=0;i<checkedNodes.length;i++){
	                				custIds+=checkedNodes[i].data.custId+',';
	                			}
	                			custIds.toString();
	                			Ext.Msg.wait('正在保存,请等待...','提示');
	                            Ext.Ajax.request({
	                                url: basepath + '/ocrmFMmSysMsg!saveData2.json',
	                                method: 'POST',
	                                params: {
	                                	'prodId':productId,
	                                	'modelId':modelIdGloble,
	                                	'custIds':custIds,
	                                	'prodName':productName,
	                                	'groupId':groupId,
	                                	'modelSource':modelSource,
	                                	'message':message,
	                                	'modelType':modelType,
	                                	'modelName':modelNameGloble
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
		if(view._defaultTitle == '自动营销推送'){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}
		}
	}
	
	/**
	 * 结果域面板滑入后触发,系统提供listener事件方法
	 */
	var viewshow = function(theview){
		if(theview._defaultTitle == '自动营销推送'){
			if(getSelectedData()){
				var tempData = getSelectedData().data;
				theview.contentPanel.getForm().reset();
				theview.contentPanel.getForm().findField('CUST_BASE_NUMBER').setValue(tempData.CUST_BASE_NUMBER);
				theview.contentPanel.getForm().findField('CUST_BASE_NAME').setValue(tempData.CUST_BASE_NAME);
			}
			store.load({params:{'id':getSelectedData().data.ID,'groupMemberType':getSelectedData().data.GROUP_MEMBER_TYPE,'custFrom':getSelectedData().data.CUST_FROM}});
		}
	};
