/**新拨及还款页面
 * @author Administrator
 */
imports([
     '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',//导出
     '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
 	'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js' ,// 机构放大镜
 	'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',	//用户放大镜
 	'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryFieldDK.js'	//客户放大镜（企商金营销用）
]);
Ext.QuickTips.init();
var needCondition = true;
var needGrid = true;
var url=basepath+'/ocrmFMkDepositDrawingAction.json';
var superUnit='';
var createView = true;
var editView = true;
var formViewers = true;

var lookupTypes=[
	'XD000052',//客户类别
	'IF_RE',//是否RE客户
	'DEP_OR_DRA',//存款/取款
	'IF_FLAG',//是否台商
	'DEPOSIT_TYPE',//存款类型
	'CURRENCY',//币种
	'PROBABILITY',
	'HAPPEN_REASON',//取款原因
	'CQPROGRESS',
	'APPROVE_STATE',
	{   TYPE : 'AREA',//区域中心数据字典
		url : '/ocrmFMkDepositDrawingAction!searchArea.json',//此url为pipeline阶段所有区域中心字段的数据字典访问地址，后台逻辑存放于mktprospectCAction中
		key : 'KEY',
		value : 'VALUE',
		root : 'data'},
	{
		TYPE : 'BRANCH',//自定义数据权限选项数据字典
		url : '/branchSearchAction!searchBranch.json',
		key : 'KEY',
		value : 'VALUE',
		jsonRoot : 'json.data'
	}
	,{   TYPE : 'CUST_TYPE1',//客户类别
		url : '/ocrmFMkDepositDrawingAction!searchCustType.json',//此url为pipeline阶段所有区域中心字段的数据字典访问地址，后台逻辑存放于mktprospectCAction中
		key : 'KEY',
		value : 'VALUE',
		root : 'data'}
	,{
		TYPE : 'CQPROGRESS1',//存取款进度
		url : '/branchSearchAction!searchCQProgress.json',
		key : 'KEY',
		value : 'VALUE',
		jsonRoot : 'json.data'
	}
	];

var roleDataCombo= new Ext.form.ComboBox({
	editable: false,
	mode : 'local',
	store: new Ext.data.Store(),
	triggerAction : 'all',
	displayField:'value',
	valueField:'key'
});
var roleDataCombo1= new Ext.form.ComboBox({
	editable: false,
	mode : 'local',
	store: new Ext.data.Store(),
	triggerAction : 'all',
	displayField:'value',
	valueField:'key'
});
var fields=[
        {name:'ID',hidden:true},
        {name:'MGR_ID',hidden:true},       
        {name:'MGR_NAME',text:'客户经理名称',searchField:false,maxLength:38,xtype:'userchoose',hiddenName:'MGR_ID',gridField:false,resutlWidth:80},
    	{name:'APPROVE_STATE',text:'复核状态',maxLength:10,searchField:false,translateType:'APPROVE_STATE',resutlWidth:60},
        
        //用两个下拉框
    	{name:'REGION',hidden:true},
        {name:'REGION1',text:'区域',searchField:false,allowBlank:false,maxLength:200,translateType:'AREA',resutlWidth:80,hiddenName:'REGION',
	        	listeners:{
	         		select:function(combo,record){
	        			 var ownerForm=this;
	        			 while(ownerForm && !ownerForm.form){
	        				 ownerForm=ownerForm.ownerCt;
	        			 }
	    		    		var value = combo.value;
	    		    		if(ownerForm && ownerForm.form){
	    		    			var _store = findLookupByType('BRANCH');
	    			        	_store.load({
	    			                params : {
	    			                    'superUnit' :value 
	    			                }
	    			            });
	    			        	ownerForm.form.findField('ORG_ID1').setValue(null);
	    			            roleDataCombo.bindStore(_store);
	    		    		}	
	        		}
	    	}},
	    {name:'ORG_ID',hidden:true},
	    {name:'ORG_ID1',text:'分支行',searchField:false,allowBlank:false,maxLength:48,translateType : 'BRANCH',editor:roleDataCombo,hiddenName:'ORG_ID',
	    		listeners:{
	         		focus:function(combo,record){
	         			 var ownerForm=this;
	        			 while(ownerForm && !ownerForm.form){
	        				 ownerForm=ownerForm.ownerCt;
	        			 }
	         			var region = ownerForm.form.findField('REGION1').getValue();
	         			if(null==region||region==''){
	         				Ext.Msg.alert('提示', '请先选择区域！');
	        				return false;
	         			}
	        		}
	        	}},	   
	    {name:'CUST_TYPE',text:'客户类别',searchField:true,allowBlank:false,maxLength:100,translateType:'XD000052'
	    	,listeners: {  
			    'focus': {  
			        fn: function(e) {  
			            e.expand();  
			            this.doQuery(this.allQuery, true);  
			        },  
			        buffer:200  
			 }
		  }},
	    {name:'IF_TAIWAN',text:'是否台商',maxLength:5,translateType:'IF_FLAG',resutlWidth:60
	    	,listeners: {  
			    'focus': {  
			        fn: function(e) {  
			            e.expand();  
			            this.doQuery(this.allQuery, true);  
			        },  
			        buffer:200  
			 }
		  }},
	    {name:'IF_RE',text:'是否RE客户',allowBlank:false,maxLength:5,translateType:'IF_RE',resutlWidth:80
	    	,listeners: {  
			    'focus': {  
			        fn: function(e) {  
			            e.expand();  
			            this.doQuery(this.allQuery, true);  
			        },  
			        buffer:200  
			 }
		  }},
	    {name:'CUST_ID',hidden:true},
		{name:'CUST_NAME',text:'客户名称',searchField:false,allowBlank:false,xtype:'customerquerydk',hiddenName:'CUST_ID',maxLength:100,resutlWidth:200,singleSelected : true},
		{name:'DEP_OR_DRA',text:'存款/取款',allowBlank:false,maxLength:9,translateType:'DEP_OR_DRA',resutlWidth:60,
			listeners:{
				select:function(combo,record){
		    		 var v = this.getValue();
		    		 var ownerForm=this;
	       			 while(ownerForm && !ownerForm.form){
	       				 ownerForm=ownerForm.ownerCt;
	       			 }
		    		if(ownerForm && ownerForm.form){
		    			if(v=='0'){//存款,取款原因隐藏
	 		    			getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').setVisible(false); 
	 		    			getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').setValue(""); 
	 		    			getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').allowBlank=true; 
	 		    		}else {//取款,取款原因必填
	 		    			getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').setVisible(true); 
	 		    			getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').allowBlank=false; 
	 		    			}
		    		}	
   		
					}
    	}},
    	{name:'DEPOSIT_TYPE',text:'存款类型',maxLength:200,translateType:'DEPOSIT_TYPE'
    		,listeners: {  
			    'focus': {  
			        fn: function(e) {  
			            e.expand();  
			            this.doQuery(this.allQuery, true);  
			        },  
			        buffer:200  
			 }
		  }},
    	{name:'CURRENCY',text:'币种',allowBlank:false,maxLength:20,translateType:'CURRENCY',resutlWidth:60
    		,listeners: {  
			    'focus': {  
			        fn: function(e) {  
			            e.expand();  
			            this.doQuery(this.allQuery, true);  
			        },  
			        buffer:200  
			 }
		  }},
    	{name:'AMOUNT',text:'金额（RMB千元)',allowBlank:false,maxLength:8,
			regex:/^[-]?[0-9]+([.][0-9]+){0,1}$/,
			regexText:'格式有误,例：999999.9，-9999999.9'},
		{name:'ESTIMATE_DATE',text:'预计存取款日期',allowBlank:false,xtype:'datefield',format:'Y-m-d',maxLength:48,editable:false},
		{name:'ESTIMATE_DATE_START',text:'预计存取款开始日期',allowBlank:true,searchField:true,xtype:'datefield',format:'Y-m-d',maxLength:48,gridField:false,editable:false},
		{name:'ESTIMATE_DATE_END',text:'预计存取款结束日期',allowBlank:true,searchField:true,xtype:'datefield',format:'Y-m-d',maxLength:48,gridField:false,editable:false},
		{name:'PROBABILITY',text:'当月存款/取款概率(%)',maxLength:9,translateType:'PROBABILITY',allowBlank:false},
		{name:'DISCOUNT_OCCUR_AMT',text:'折后金额（RMB千元）',maxLength:30,readOnly:true,cls:'x-readOnly'},
		{name:'HAPPEN_REASON',text:'取款原因',maxLength:249,translateType:'HAPPEN_REASON'
			,listeners: {  
			    'focus': {  
			        fn: function(e) {  
			            e.expand();  
			            this.doQuery(this.allQuery, true);  
			        },  
			        buffer:200  
			 }
		  }},
		{name:'PROGRESS',text:'存取款进度',maxLength:9,translateType:'CQPROGRESS',allowBlank:false,resutlWidth:80
//			,editor:roleDataCombo1
			},
		{name:'REMARK',text:'备注',maxLength:200,xtype : 'textarea',gridField:true},
    	{name:'BL_NO',text:'业务条线',maxLength:5,hidden:true},		
		{name:'POTENTIAL_FLAG',hidden:true},
		{name:'POTENTIAL_FLAG_ORA',text:'是否潜在客户',hidden:true},		
		{name:'CREATE_USER',hidden:true},
		{name:'CREATE_USERNAME',text:'创建人',hiddenName:'CREATE_USER',hidden:true,maxLength:12,readOnly:true,cls:'x-readOnly'},
		{name:'CREATE_ORG',hidden:true},
		{name:'CREATE_ORGNAME',text:'创建机构',hiddenName:'CREATE_ORG',hidden:true,maxLength:48,readOnly:true,cls:'x-readOnly'},
		{name:'CREATE_TM',text:'创建时间',hidden:true,maxLength:48,readOnly:true,cls:'x-readOnly'},
		{name:'LAST_UPDATE_USER',hidden:true},
		{name:'LAST_UPDATE_USERNAME',text:'最近修订人',hiddenName:'LAST_UPDATE_USER',hidden:true,maxLength:12,readOnly:true,cls:'x-readOnly'},
		{name:'LAST_UPDATE_ORG',hidden:true},
		{name:'LAST_UPDATE_ORGNAME',hiddenName:'LAST_UPDATE_ORG',text:'最近修订机构',hidden:true,maxLength:48,readOnly:true,cls:'x-readOnly'},
		{name:'LAST_UPDATE_TM',text:'最近修订时间',hidden:true,maxLength:48,readOnly:true,cls:'x-readOnly'}
	];

var customerView=[{
	title:'新增',
	type:'form',
	hideTitle:true,
	suspendWidth:0.75,
	groups:[{
		columnCount: 2,
		fields:[ 'ID','APPROVE_STATE',	
		         {name:'CUST_NAME',text:'客户名称',allowBlank:false,xtype:'customerquerydk',hiddenName:'CUST_ID',maxLength:100,resutlWidth:200,singleSelected : true,
					searchParams : [{name : 'bizType',value:'depositOrDrawing'}],
					callback : function(a,b) {	
						CUST_ID = a.customerId;		
						getCurrentView().contentPanel.form.findField("REGION").setValue(a.region);//区域
						getCurrentView().contentPanel.form.findField("ORG_ID").setValue(a.orgId);//分支行
						getCurrentView().contentPanel.form.findField("REGION1").setValue(a.region);//区域
						getCurrentView().contentPanel.form.findField("ORG_ID1").setValue(a.orgId);//分支行
						if(a.potentialFlag=='0' ){//既有客户
							getCurrentView().contentPanel.form.findField("CUST_TYPE").setValue(a.custType1);//客户类别	
							if('Y'==a.isTaiwanCorp){
								getCurrentView().contentPanel.form.findField("IF_TAIWAN").setValue('1');//是否台商
							}
							else{
								getCurrentView().contentPanel.form.findField("IF_TAIWAN").setValue('0');//是否台商
							}							
							getCurrentView().contentPanel.form.findField("CUST_TYPE").readOnly=true;
							getCurrentView().contentPanel.form.findField("CUST_TYPE").cls='x-readOnly';
							getCurrentView().contentPanel.form.findField("IF_TAIWAN").readOnly=true;
							getCurrentView().contentPanel.form.findField("IF_TAIWAN").cls='x-readOnly';
						}
						if(a.potentialFlag=='1' ){//潜在客户
							getCurrentView().contentPanel.form.findField("IF_TAIWAN").allowBlank=false;
							getCurrentView().contentPanel.form.findField("CUST_TYPE").readOnly=false;
							getCurrentView().contentPanel.form.findField("IF_TAIWAN").readOnly=false;
							getCurrentView().contentPanel.form.findField("CUST_TYPE").setValue("");
							getCurrentView().contentPanel.form.findField("IF_TAIWAN").setValue("");						
						}
					}},
			 'CUST_ID','REGION','REGION1','ORG_ID', 'ORG_ID1', 'CUST_TYPE','IF_RE','IF_TAIWAN', 'HAPPEN_REASON',        
		    {name:'DEP_OR_DRA',text:'存款/取款',allowBlank:false,maxLength:9,translateType:'DEP_OR_DRA',
 			listeners:{
				select:function(combo,record){
					var v = this.getValue();
					var ownerForm=this;
					while(ownerForm && !ownerForm.form){
						ownerForm=ownerForm.ownerCt;
					}
					if(ownerForm && ownerForm.form){
						var Amount =ownerForm.form.findField('AMOUNT').getValue();
						var probability =ownerForm.form.findField('PROBABILITY').getValue();
						if(v=='0'){//存款,取款原因隐藏
							getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').setVisible(false); 
							getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').setValue(""); 
 		    				getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').allowBlank=true; 
 		    				var a=Amount.substring(Amount.lastIndexOf('-')+1,Amount.length)
							ownerForm.form.findField('AMOUNT').setValue(a);
							ownerForm.form.findField('DISCOUNT_OCCUR_AMT').setValue(parseFloat((a*probability*0.01).toPrecision(12)));
						}else {//取款,取款原因必填
							getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').setVisible(true); 
							getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').allowBlank=false; 
							
							var a=Amount.substring(Amount.lastIndexOf('-')+1,Amount.length)
							ownerForm.form.findField('AMOUNT').setValue("-"+a);
							ownerForm.form.findField('DISCOUNT_OCCUR_AMT').setValue("-"+parseFloat((parseFloat((a*probability*0.01).toPrecision(12))).toPrecision(12)));
	 		    			}
		    			var _store1 = findLookupByType('CQPROGRESS1');
		    			_store1.load({
		    				params : {
		    					'deptOrDra' :v 
		    				}
		    			});
		    			ownerForm.form.findField('PROGRESS').setValue(null);
		    			roleDataCombo1.bindStore(_store1);
					}	  		
				},	   
				'focus': {  
			        fn: function(e) {  
			            e.expand();  
			            this.doQuery(this.allQuery, true);  
			        },  
			        buffer:200  
			 }
			}},'ESTIMATE_DATE' , 'DEPOSIT_TYPE',
		{name:'PROGRESS',text:'存取款进度',maxLength:9,translateType:'CQPROGRESS1',allowBlank:false,editor:roleDataCombo1
			,listeners: {  
			    'focus': {  
			        fn: function(e) {  
			            e.expand();  
			            this.doQuery(this.allQuery, true);  
			        },  
			        buffer:200  
			 }}},
		'CURRENCY',
		{name:'PROBABILITY',text:'当月存款/取款概率(%)',maxLength:9,translateType:'PROBABILITY',allowBlank:false,listeners:{
			blur:function(){
				var v = this.getValue();
				var ownerForm=this;
				while(ownerForm && !ownerForm.form){
					ownerForm=ownerForm.ownerCt;
				}
				if(ownerForm && ownerForm.form){
					var amount =ownerForm.form.findField('AMOUNT').getValue();					
					ownerForm.form.findField('DISCOUNT_OCCUR_AMT').setValue(parseFloat((v*amount*0.01).toPrecision(12)));
				}						
			},
			'focus': {  
		        fn: function(e) {  
		            e.expand();  
		            this.doQuery(this.allQuery, true);  
		        },  
		        buffer:200  
		 }
		}}, 
		{name:'AMOUNT',text:'金额（RMB千元)',allowBlank:false,maxLength:8,
			regex:/^[-]?[0-9]+([.][0-9]+){0,1}$/,
			regexText:'格式有误,例：999999.9，-9999999.9',listeners:{
					blur:function(){
						var v = this.getValue();
						var ownerForm=this;
						while(ownerForm && !ownerForm.form){
							ownerForm=ownerForm.ownerCt;
						}
						if(ownerForm && ownerForm.form){
							var depOrDra =ownerForm.form.findField('DEP_OR_DRA').getValue();
							var probability =ownerForm.form.findField('PROBABILITY').getValue();
			     			if(null==depOrDra||depOrDra==''){
			     				Ext.Msg.alert('提示', '请先选择存款/取款！');
			    				return false;
			     			}
			     			else if(depOrDra=='0'){
			     				var a=v.substring(v.lastIndexOf('-')+1,v.length)
								this.setValue(a);			     				
								ownerForm.form.findField('DISCOUNT_OCCUR_AMT').setValue(parseFloat((parseFloat((a*probability*0.01).toPrecision(12))).toPrecision(12)));
							}
							else{
								var a=v.substring(v.lastIndexOf('-')+1,v.length)
								this.setValue("-"+a);								
								ownerForm.form.findField('DISCOUNT_OCCUR_AMT').setValue("-"+parseFloat((parseFloat((a*probability*0.01).toPrecision(12))).toPrecision(12)));
							}
						}						
					}
				}},'DISCOUNT_OCCUR_AMT','REMARK', 'BL_NO', 'POTENTIAL_FLAG','POTENTIAL_FLAG_ORA',
			'CREATE_USER', 'CREATE_USERNAME','CREATE_ORG', 'CREATE_ORGNAME','CREATE_TM', 'LAST_UPDATE_USER','LAST_UPDATE_USERNAME', 'LAST_UPDATE_ORG','LAST_UPDATE_ORGNAME', 'LAST_UPDATE_TM'],
		fn: function(ID,APPROVE_STATE,CUST_NAME ,CUST_ID, REGION,REGION1, ORG_ID,ORG_ID1,CUST_TYPE ,IF_RE, IF_TAIWAN ,
				HAPPEN_REASON ,DEP_OR_DRA,ESTIMATE_DATE,  DEPOSIT_TYPE , PROGRESS ,CURRENCY,PROBABILITY ,AMOUNT,  DISCOUNT_OCCUR_AMT,REMARK, BL_NO ,POTENTIAL_FLAG,POTENTIAL_FLAG_ORA, 
				CREATE_USER , CREATE_USERNAME,CREATE_ORG, CREATE_ORGNAME,CREATE_TM , LAST_UPDATE_USER,LAST_UPDATE_USERNAME, LAST_UPDATE_ORG , LAST_UPDATE_ORGNAME,LAST_UPDATE_TM ){
						BL_NO.hidden=true;
						APPROVE_STATE.hidden=true;
						CREATE_USERNAME.hidden=true;
						CREATE_ORGNAME.hidden=true;
						CREATE_TM.hidden=true;
						LAST_UPDATE_USERNAME.hidden=true;
						LAST_UPDATE_ORGNAME.hidden=true;
						LAST_UPDATE_TM.hidden=true;
						POTENTIAL_FLAG_ORA.hidden=true;
						REGION1.readOnly=true;
						REGION1.cls='x-readOnly';
						ORG_ID1.readOnly=true;
						ORG_ID1.cls='x-readOnly';
			return [ID,APPROVE_STATE,CUST_NAME ,CUST_ID, REGION,REGION1, ORG_ID,ORG_ID1,CUST_TYPE ,IF_RE, IF_TAIWAN ,
					HAPPEN_REASON ,DEP_OR_DRA,ESTIMATE_DATE,  DEPOSIT_TYPE , PROGRESS ,CURRENCY,PROBABILITY ,AMOUNT,  DISCOUNT_OCCUR_AMT,REMARK, BL_NO ,POTENTIAL_FLAG,POTENTIAL_FLAG_ORA, 
					CREATE_USER , CREATE_USERNAME,CREATE_ORG, CREATE_ORGNAME,CREATE_TM , LAST_UPDATE_USER,LAST_UPDATE_USERNAME, LAST_UPDATE_ORG , LAST_UPDATE_ORGNAME,LAST_UPDATE_TM  ]
		}
	}
//	,{
//		columnCount : 1,
//		fields : [ 'REMARK' ],
//		fn : function(REMARK) {
//			return [ REMARK ];
//		}}
	],
	formButtons:[{
		text:'保存',
		fn:function(contentPanel,baseform){
			showCustomerViewByTitle('新增');
			if(!baseform.isValid()){
				Ext.Msg.alert('提醒','字段检验失败，请检查输入项');
				return false;
			}
			var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Ajax.request({
				url:basepath+'/ocrmFMkDepositDrawingAction!create.json',
				method:'POST',
				params :
					commintData	,
				success:function(response){
					Ext.Msg.alert('提示','保存成功！');
					reloadCurrentData();
				},
	 		failure:function(){
	 			Ext.Msg.alert('提示','保存失败！');
	 			}
			});
		}
	}
//	,{
//		text:'返回',
//		fn:function(contentPanel,baseform){
//			reloadCurrentData();}}
	]
},{
	title:'修改',
	type:'form',
	hideTitle:true,
	suspendWidth:0.75,
	autoLoadSeleted:true,//自动加载
	groups:[{
		columnCount: 2,
		fields:[ 'ID', 'APPROVE_STATE',
		         {name:'CUST_NAME',text:'客户名称',allowBlank:false,hiddenName:'CUST_ID',maxLength:100,resutlWidth:200}, 
		         'CUST_ID','REGION','REGION1','ORG_ID', 'ORG_ID1', 'CUST_TYPE','IF_RE','IF_TAIWAN',
		         'HAPPEN_REASON',
		         {name:'DEP_OR_DRA',text:'存款/取款',allowBlank:false,maxLength:9,translateType:'DEP_OR_DRA',
					listeners:{
					select:function(combo,record){
						var v = this.getValue();
						var ownerForm=this;
						while(ownerForm && !ownerForm.form){
							ownerForm=ownerForm.ownerCt;
						}
						if(ownerForm && ownerForm.form){
							var Amount =ownerForm.form.findField('AMOUNT').getValue();
							var probability =ownerForm.form.findField('PROBABILITY').getValue();
							if(v=='0'){//存款,取款原因隐藏
								getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').setVisible(false); 
								getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').setValue(""); 
				    				getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').allowBlank=true; 
				    				
				    				var a=Amount.substring(Amount.lastIndexOf('-')+1,Amount.length)
								ownerForm.form.findField('AMOUNT').setValue(a);
								ownerForm.form.findField('DISCOUNT_OCCUR_AMT').setValue(parseFloat((parseFloat((a*probability*0.01).toPrecision(12))).toPrecision(12)));
							}else {//取款,取款原因必填
								getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').setVisible(true); 
								getCurrentView().contentPanel.getForm().findField('HAPPEN_REASON').allowBlank=false; 
								
								var a=Amount.substring(Amount.lastIndexOf('-')+1,Amount.length)
								ownerForm.form.findField('AMOUNT').setValue("-"+a);
								ownerForm.form.findField('DISCOUNT_OCCUR_AMT').setValue("-"+parseFloat((parseFloat((a*probability*0.01).toPrecision(12))).toPrecision(12)));
					    			}
			    			var _store1 = findLookupByType('CQPROGRESS1');
			    			_store1.load({
			    				params : {
			    					'deptOrDra' :v 
			    				}
			    			});
			    			ownerForm.form.findField('PROGRESS').setValue(null);
			    			roleDataCombo1.bindStore(_store1);
						}	  		
					},	   
					'focus': {  
				        fn: function(e) {  
				            e.expand();  
				            this.doQuery(this.allQuery, true);  
				        },  
				        buffer:200  
				 }
				}},'ESTIMATE_DATE',  'DEPOSIT_TYPE', 
				{name:'PROGRESS',text:'存取款进度',maxLength:9,translateType:'CQPROGRESS1',allowBlank:false,editor:roleDataCombo1
					,listeners:{	   
						'focus': {  
					        fn: function(e) {  
					            e.expand();  
					            this.doQuery(this.allQuery, true);  
					        },  
					        buffer:200  
					 }
					}},
				'CURRENCY', 	
				{name:'PROBABILITY',text:'当月存款/取款概率(%)',maxLength:9,translateType:'PROBABILITY',allowBlank:false,listeners:{
					blur:function(){
						var v = this.getValue();
						var ownerForm=this;
						while(ownerForm && !ownerForm.form){
							ownerForm=ownerForm.ownerCt;
						}
						if(ownerForm && ownerForm.form){
							var amount =ownerForm.form.findField('AMOUNT').getValue();
							ownerForm.form.findField('DISCOUNT_OCCUR_AMT').setValue(parseFloat((v*amount*0.01).toPrecision(12)));
						}						
					},	   
					'focus': {  
				        fn: function(e) {  
				            e.expand();  
				            this.doQuery(this.allQuery, true);  
				        },  
				        buffer:200  
				 }
				}},
				{name:'AMOUNT',text:'金额（RMB千元)',allowBlank:false,maxLength:8,
					regex:/^[-]?[0-9]+([.][0-9]+){0,1}$/,
					regexText:'格式有误,例：999999.9，-9999999.9',listeners:{
							blur:function(){
								
								var v = this.getValue();
								var ownerForm=this;
								while(ownerForm && !ownerForm.form){
									ownerForm=ownerForm.ownerCt;
								}
								if(ownerForm && ownerForm.form){
									var depOrDra =ownerForm.form.findField('DEP_OR_DRA').getValue();
									var probability =ownerForm.form.findField('PROBABILITY').getValue();
					     			if(null==depOrDra||depOrDra==''){
					     				Ext.Msg.alert('提示', '请先选择存款/取款！');
					    				return false;
					     			}
					     			else if(depOrDra=='0'){
					     				var a=v.substring(v.lastIndexOf('-')+1,v.length);
										this.setValue(a);
										ownerForm.form.findField('DISCOUNT_OCCUR_AMT').setValue(parseFloat((a*probability*0.01).toPrecision(12)));
									}
									else{
										var a=v.substring(v.lastIndexOf('-')+1,v.length)
										this.setValue("-"+a);										
										ownerForm.form.findField('DISCOUNT_OCCUR_AMT').setValue("-"+parseFloat((parseFloat((a*probability*0.01).toPrecision(12))).toPrecision(12)));
									}
								}						
							}
						}},
			    'DISCOUNT_OCCUR_AMT', 'REMARK', 'BL_NO',   'POTENTIAL_FLAG','POTENTIAL_FLAG_ORA',
				'CREATE_USER', 'CREATE_USERNAME','CREATE_ORG', 'CREATE_ORGNAME','CREATE_TM', 'LAST_UPDATE_USER','LAST_UPDATE_USERNAME', 'LAST_UPDATE_ORG','LAST_UPDATE_ORGNAME', 'LAST_UPDATE_TM'],
		fn: function(ID,APPROVE_STATE,CUST_NAME ,CUST_ID, REGION,REGION1, ORG_ID,ORG_ID1,CUST_TYPE ,IF_RE, IF_TAIWAN ,
				HAPPEN_REASON ,DEP_OR_DRA,ESTIMATE_DATE,  DEPOSIT_TYPE , PROGRESS ,CURRENCY,PROBABILITY ,AMOUNT,DISCOUNT_OCCUR_AMT,REMARK, BL_NO ,POTENTIAL_FLAG,POTENTIAL_FLAG_ORA,
				CREATE_USER , CREATE_USERNAME,CREATE_ORG, CREATE_ORGNAME,CREATE_TM , LAST_UPDATE_USER,LAST_UPDATE_USERNAME, LAST_UPDATE_ORG , LAST_UPDATE_ORGNAME,LAST_UPDATE_TM ){
						BL_NO.hidden=true;
						APPROVE_STATE.hidden=true;
						CREATE_USERNAME.hidden=true;
						CREATE_ORGNAME.hidden=true;
						CREATE_TM.hidden=true;
						LAST_UPDATE_USERNAME.hidden=true;
						LAST_UPDATE_ORGNAME.hidden=true;
						LAST_UPDATE_TM.hidden=true;
						REGION1.readOnly=true;
						REGION1.cls='x-readOnly';
						ORG_ID1.readOnly=true;
						ORG_ID1.cls='x-readOnly';
						CUST_NAME.readOnly=true;
						CUST_NAME.cls='x-readOnly';
						CUST_TYPE.readOnly=true;
						CUST_TYPE.cls='x-readOnly';
						IF_TAIWAN.readOnly=true;
						IF_TAIWAN.cls='x-readOnly';
//						POTENTIAL_FLAG_ORA.readOnly=true;
//						POTENTIAL_FLAG_ORA.cls='x-readOnly';
						POTENTIAL_FLAG_ORA.hidden=true;
			return [ID,APPROVE_STATE,CUST_NAME ,CUST_ID, REGION,REGION1, ORG_ID,ORG_ID1,CUST_TYPE ,IF_RE, IF_TAIWAN ,
					HAPPEN_REASON ,DEP_OR_DRA,ESTIMATE_DATE,  DEPOSIT_TYPE , PROGRESS ,CURRENCY,PROBABILITY ,AMOUNT,DISCOUNT_OCCUR_AMT,REMARK, BL_NO ,POTENTIAL_FLAG,POTENTIAL_FLAG_ORA,
					CREATE_USER , CREATE_USERNAME,CREATE_ORG, CREATE_ORGNAME,CREATE_TM , LAST_UPDATE_USER,LAST_UPDATE_USERNAME, LAST_UPDATE_ORG , LAST_UPDATE_ORGNAME,LAST_UPDATE_TM ]
		}
	}],
	formButtons:[{
		text:'保存',
		fn:function(contentPanel,baseform){
			showCustomerViewByTitle('修改');
			debugger;
			if(!baseform.isValid()){
				Ext.Msg.alert('提醒','字段检验失败，请检查输入项');
				return false;
			}
			baseform.findField('APPROVE_STATE').setValue('1');
			var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Ajax.request({
				url:basepath+'/ocrmFMkDepositDrawingAction!create.json',
				method:'POST',
				params :
					commintData	,
				success:function(response){
					Ext.Msg.alert('提示','保存成功！');
					reloadCurrentData();
				},
	 		failure:function(){
	 			Ext.Msg.alert('提示','保存失败！');
	 			}
			});
		}
	}]
},{
	title:'详情',
	type:'form',
	suspendWidth:0.75,
	autoLoadSeleted:true,//自动加载
	hideTitle:true,
	groups:[{
		//textboxWidth : 0.70,
		columnCount:2,
		fields:['ID','APPROVE_STATE', 'CUST_NAME','CUST_ID','REGION','REGION1', 'ORG_ID', 'ORG_ID1','CUST_TYPE','IF_RE', 'IF_TAIWAN',  
		        'HAPPEN_REASON','DEP_OR_DRA','ESTIMATE_DATE','DEPOSIT_TYPE','PROGRESS', 'CURRENCY', 'PROBABILITY','AMOUNT','DISCOUNT_OCCUR_AMT','BL_NO', 'POTENTIAL_FLAG','POTENTIAL_FLAG_ORA', 
		        'CREATE_USER','CREATE_USERNAME', 'CREATE_ORG', 'CREATE_ORGNAME','LAST_UPDATE_TM' , 'LAST_UPDATE_USER','LAST_UPDATE_USERNAME', 'LAST_UPDATE_ORG','LAST_UPDATE_ORGNAME','CREATE_TM' ,'REMARK'],

		fn:function(ID,APPROVE_STATE, CUST_NAME ,CUST_ID , REGION,REGION1, ORG_ID,ORG_ID1,CUST_TYPE , IF_RE ,IF_TAIWAN ,	
				HAPPEN_REASON ,DEP_OR_DRA,ESTIMATE_DATE , DEPOSIT_TYPE,PROGRESS, CURRENCY,PROBABILITY ,AMOUNT, DISCOUNT_OCCUR_AMT,  BL_NO ,POTENTIAL_FLAG,POTENTIAL_FLAG_ORA,
				CREATE_USER , CREATE_USERNAME,CREATE_ORG, CREATE_ORGNAME,LAST_UPDATE_TM, LAST_UPDATE_USER,LAST_UPDATE_USERNAME, LAST_UPDATE_ORG , LAST_UPDATE_ORGNAME,CREATE_TM  ,REMARK)
		{		
			for(var i=0;i<arguments.length;i++){//只读
				   arguments[i].disabled=true;//只能展示
				   arguments[i].cls='x-readOnly';
			   }
			BL_NO.hidden=true;
			APPROVE_STATE.hidden=true;
			return [ID,APPROVE_STATE, CUST_NAME ,CUST_ID , REGION,REGION1, ORG_ID,ORG_ID1,CUST_TYPE , IF_RE ,IF_TAIWAN ,	
					HAPPEN_REASON ,DEP_OR_DRA,ESTIMATE_DATE , DEPOSIT_TYPE,PROGRESS, CURRENCY,PROBABILITY ,AMOUNT, DISCOUNT_OCCUR_AMT,  BL_NO ,POTENTIAL_FLAG,POTENTIAL_FLAG_ORA,
					CREATE_USER , CREATE_USERNAME,CREATE_ORG, CREATE_ORGNAME,LAST_UPDATE_TM, LAST_UPDATE_USER,LAST_UPDATE_USERNAME, LAST_UPDATE_ORG , LAST_UPDATE_ORGNAME,CREATE_TM  ,REMARK];
		}
	}],
	formButtons:[{
		text:'返回',
		fn:function(contentPanel,baseform){
			reloadCurrentData();
			}
	}]	}];
var rowdblclick = function(tile, record){
	showCustomerViewByTitle('详情');
};
/**
 * 自定义工具条上按钮
 * 选择至少一条数据!只能删除暂存的新拨及还款信息
 */
var tbar = [{
	id:'add',
	text:'新增',
	hidden:JsContext.checkGrant('cunkuan1_add'),
	handler:function(){
		showCustomerViewByTitle('新增');
	}	
},{
	id:'update',
	text:'修改',
	hidden:JsContext.checkGrant('cunkuan1_update'),
	handler:function(){
		  var tbartext='修改';
		  if (getSelectedData() == false || getAllSelects().length > 1) {
				Ext.Msg.alert('提示信息', '请选择一条数据进行操作！');
				return false;
			}
		  else{
		  var records =getAllSelects(); //获取选择记录
          var idsStr =  records[0].data.ID;//获取id
          var createUser =  records[0].data.CREATE_USER;//获取id
       	  if (idsStr != '') {
//       		va(idsStr,tbartext);
       		if(createUser==JsContext._userId){
       		 showCustomerViewByTitle(tbartext);
       		}
       		else{
       		  Ext.Msg.alert('提示信息', '当前用户不拥有权限！');
       		}
       	  }	}		
	}		
},{
	id:'detail',
	text:'详情',
//	hidden:JsContext.checkGrant('cunkuan1_detail'),
	handler:function(){
		showCustomerViewByTitle('详情');
	}		
},{//按钮：删除
	id:'delete',
    text:'删除',
    hidden:JsContext.checkGrant('cunkuan1_delete'),
	handler:function(){
        var records =getAllSelects(); //获取选择记录
        var selectLength = getAllSelects().length; //获取选择记录数
        if (selectLength < 1) {
        	Ext.Msg.alert('提示信息', '请至少选择一条记录！');
        } else{
               var selectedId; 
               var idsStr = ''; //待删除记录id串，如：10001,10002
               var users='';
               for ( var i = 0; i < selectLength; i++) {
                    selectRe = records[i];
                    selectedId = selectRe.data.ID;//获取ID                        
                    idsStr += selectedId;
                    if (i != selectLength - 1) 
               		idsStr += ','; 
                    selectedCreater = selectRe.data.CREATE_USER;//获取创建人
                    if(selectedCreater!=JsContext._userId){//不等时有值
                    var j=i+1;
             		   users += j;
             		   if (i != selectLength - 1) 
             			   users += ','; 
                    }
               };
               for(var j = 0; j < selectLength; j++){
            	   selectRe = records[j];
                   selectedSys = selectRe.data.APPROVE_STATE;//获取审批状态
                 
                   if(selectedSys=='1'){ //当状态是“暂存”时
                	  if(users==''){
                   		   Ext.MessageBox.confirm('系统提示信息','确认进行删除吗？',function(btn) {
                   			   if (btn == 'yes'){//确认进行删除时
                   				   Ext.Ajax.request({
                   					   url : basepath+ '/ocrmFMkDepositDrawingAction!batchDestroy.json',
                   					   params : {
                   						   idsStr : idsStr					
                   					   },
                   					   waitMsg : '正在删除数据,请等待...', 
                   					   method : 'POST', // method : 'GET'
                   					   scope : this,
                   					   success : function(response) {
                   						   Ext.Msg.alert('提示信息', '删除成功！');
                   						   reloadCurrentData();
                   					   }
                   				   });
                   			   }
                   		   }, this);
                   	  
                	  }
                	  else{
                		  Ext.Msg.alert('提示信息', '当前用户不拥有删除所选中的第【'+users+'】条数据的权限！');
                	  }                   	  
                }else{
                    Ext.Msg.alert('提示','只能删除审批状态为"暂存"的数据！');
                    break;}                    	  
               };                
        }
	}
}
,{
	id:'tijiao',
    text:'提交',
    hidden:JsContext.checkGrant('cunkuan1_tijiao'),
	handler:function(){
        var records =getAllSelects(); //获取选择记录
        var selectLength = getAllSelects().length; 
        if (selectLength < 1) {
        	Ext.Msg.alert('提示信息', '请至少选择一条记录！');
        } else{
               var selectedId; //待提交记录的id
               var idsStr = ''; //待提交记录id串，如：10001,10002
               var users='';
               for ( var i = 0; i < selectLength; i++) {
                    selectRe = records[i];
                    selectedId = selectRe.data.ID;//获取id
                    idsStr += selectedId;
                    if (i != selectLength - 1) 
               		idsStr += ','; 
                    selectedCreater = selectRe.data.CREATE_USER;//获取创建人
                    if(selectedCreater!=JsContext._userId){//不等时有值
                    var j=i+1;
             		   users += j;
             		   if (i != selectLength - 1) 
             			   users += ','; 
                    }
               };
               for(var j = 0; j < selectLength; j++){
            	   selectRe = records[j];
                   selectedSys = selectRe.data.APPROVE_STATE;//获取提交状态
              if(selectedSys=='1'){ //当状态是“暂存”时
               if (idsStr != '') {
            	   //当状态是“暂存”时
             	  if(users==''){            		   
               	   Ext.Msg.wait('正在处理，请稍后......','系统提示');
          			Ext.Ajax.request({
          					url : basepath + '/ocrmFMkDepositDrawingAction!submit.json?idsStr='+idsStr,
          					method : 'GET',
          					success : function(response) {
          						 var ret = Ext.decode(response.responseText);
          						 var instanceid = ret.instanceid;//流程实例ID
          						 var currNode = ret.currNode;//当前节点
          						 var nextNode = ret.nextNode;//下一步节点
          						 selectUserList(instanceid,currNode,nextNode);//选择下一步办理人
          					}
          			});
             	  }
             	  else{
             		  Ext.Msg.alert('提示信息', '当前用户不拥有提交所选中的第【'+users+'】条数据的权限！');
             	  }                   	            
               }
        }else{
        	Ext.Msg.alert('提示','只能提交审批状态为"暂存"的数据！');
        	break;}
          }
	}
  } }
,new Com.yucheng.crm.common.NewExpButton({
		 hidden:JsContext.checkGrant('cunkuan1_export'),
		 formPanel : 'searchCondition',
		 url : basepath+'/ocrmFMkDepositDrawingAction.json'
    })];

//修改面板滑出之前：
/**
 * @description面板弹出之前进行的校验：
 * 1、详情面板：只能修改已经停用的协议；选择一条数据
 */
var beforeviewshow = function(view) {
	if (view._defaultTitle == '修改') {
		
		if (getSelectedData() == false || getAllSelects().length > 1) {
			Ext.Msg.alert('提示信息', '请至少选择一条数据！');
			return false;
		} else {
			var records = getAllSelects(); 
			var tempRecord = records[0];		
			var demp = records[0].data.APPROVE_STATE;//审批状态
			var v = records[0].data.DEP_OR_DRA;//存款或取款
			var potential = records[0].data.POTENTIAL_FLAG;//是否潜在客户
			if (demp == '2') {
				Ext.Msg.alert('提示信息', '只有审批状态不为“待审批”的数据可以修改！');
				return false;
			}
			else{
				if(v=='0'){//存款,取款原因隐藏
					view.contentPanel.getForm().findField('HAPPEN_REASON').setVisible(false); 
					view.contentPanel.getForm().findField('HAPPEN_REASON').setValue(""); 
					view.contentPanel.getForm().findField('HAPPEN_REASON').allowBlank=true; 
				}else {//取款,取款原因必填
					view.contentPanel.getForm().findField('HAPPEN_REASON').setVisible(true); 
					view.contentPanel.getForm().findField('HAPPEN_REASON').allowBlank=false; 
		    	}
				if(potential=='0'){
					view.contentPanel.getForm().findField('IF_TAIWAN').readOnly=true; 
//					view.contentPanel.getForm().findField('BL_NO').readOnly=true; 
					view.contentPanel.getForm().findField('CUST_TYPE').readOnly=true;						
				}
				else{
					view.contentPanel.getForm().findField('IF_TAIWAN').readOnly=false; 
//					view.contentPanel.getForm().findField('BL_NO').readOnly=false; 
					view.contentPanel.getForm().findField('CUST_TYPE').readOnly=false;	
				}
				view.contentPanel.getForm().loadRecord(tempRecord);
			}
		}
	}
//	if (view._defaultTitle == '新增') {
//		var upOrgId='';
//		Ext.Ajax.request({
//				url : basepath + '/ocrmFMkDepositDrawingAction!QuYu.json',
//				method : 'GET',
//				success : function(response, options) {
//					var temp = Ext.decode(response.responseText);
//					upOrgId = temp.data[0].UP_ORG_ID;// 从后台获取是否满足时间期间的数据
//					view.contentPanel.getForm().findField('REGION').setValue(upOrgId);
//				}
//		});		
//		view.contentPanel.getForm().findField('ORG_ID').setValue(JsContext._orgId);
//	}
	if (view._defaultTitle == '详情') {		
		if (getSelectedData() == false || getAllSelects().length > 1) {
			Ext.Msg.alert('提示信息', '请至少选择一条数据！');
			return false;
		} else {
			var records = getAllSelects(); 
			var tempRecord = records[0];		
			var v = records[0].data.DEP_OR_DRA;//存款或取款
		
			if(v=='0'){//存款,取款原因隐藏
				view.contentPanel.getForm().findField('HAPPEN_REASON').setVisible(false); 
				view.contentPanel.getForm().findField('HAPPEN_REASON').setValue(""); 
				view.contentPanel.getForm().findField('HAPPEN_REASON').allowBlank=true; 
			}else {//取款,取款原因必填
				view.contentPanel.getForm().findField('HAPPEN_REASON').setVisible(true); 
				view.contentPanel.getForm().findField('HAPPEN_REASON').allowBlank=false; 
	    	}
			view.contentPanel.getForm().loadRecord(tempRecord);
		}
	}
};
/**
 * 设置admin不能新增，修改和删除，提交
 */
var afterconditionrender  = function(app){
	if(__roleCodes.indexOf('admin') >= 0 ){
		Ext.getCmp('add').setVisible(false);//新增
		Ext.getCmp('update').setVisible(false);//修改
		Ext.getCmp('delete').setVisible(false);//删除
		Ext.getCmp('tijiao').setVisible(false);//提交
	}  
	else{
	}			
};	

		