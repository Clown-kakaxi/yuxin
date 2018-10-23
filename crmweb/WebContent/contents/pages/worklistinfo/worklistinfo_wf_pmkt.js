	Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var type = instanceid.split('_')[2];
		var nodeid = curNodeObj.nodeid;
		
		var store;
		var infoForm;
		if(type == '11'){//个金prospect
			store = new Ext.data.Store({
					restful:true,	
			        proxy : new Ext.data.HttpProxy(
			        		{
			        			url:basepath+'/mktprospectP.json'
			        		}),
			        reader: new Ext.data.JsonReader({
			        	root : 'json.data'
			        }, [{name:'CUST_ID'},
			            {name:'CUST_NAME'},
			            {name:'CUST_SOURCE_ORA'},
			            {name:'CUST_SOURCE_INFO'},
			            {name:'CUST_SOURCE_DATE'},
			            {name:'LINK_MAN'},
			            {name:'LINK_PHONE'},
			            {name:'VISIT_DATE'},
			            {name:'VISIT_WAY_ORA'},
			            {name:'AREA_NAME'},
			            {name:'DEPT_NAME'},
			            {name:'RM'},
			            {name:'PRODUCT_NEED'},
			            {name:'IF_TRANS_CUST_ORA'},
			            {name:'IF_PIPELINE'},
			            {name:'IF_PIPELINE_ORA'},
			            {name:'RUFUSE_REASON'},
			            {name:'USER_NAME'}]
				)
			 });
			infoForm = new Ext.FormPanel( {
					frame : true,
					items : [ {
						layout : 'column',
						items : [{
							layout : 'form',columnWidth : .5,labelWidth:100,
							items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							          {name : 'AREA_NAME',xtype : 'textfield',fieldLabel : '区域中心',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							          {name : 'VISIT_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '拜访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							          {name : 'VISIT_WAY_ORA',xtype : 'textfield',fieldLabel : '拜访方式',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							          {name : 'IF_PIPELINE_ORA',xtype : 'textfield',fieldLabel : '是否加入PIPELINE',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
						},{
							layout : 'form',columnWidth : .5,labelWidth:100,
							items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							         {name : 'DEPT_NAME',xtype : 'textfield',fieldLabel : '营业部门',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							         {name : 'LINK_PHONE',xtype : 'textfield',fieldLabel : '联系电话',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
							         {name : 'CUST_SOURCE_ORA',xtype : 'textfield',fieldLabel : '客户来源',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
						}]
					},{
						layout : 'form',labelWidth:100,
						items:[{xtype : 'textarea',name : 'PRODUCT_NEED',fieldLabel : '客户产品需求',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
						       {xtype : 'textarea',name : 'RUFUSE_REASON',fieldLabel : '拒绝原因',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
					}]
				});
		}
		if(type == '12'){//个金合作意向
			store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktIntentP.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_ID'},
		            {name:'CUST_NAME'},
		            {name:'VISIT_DATE'},
		         	{name:'AREA_NAME'},
		            {name:'DEPT_NAME'},
		            {name:'RM'},
		            {name:'PRODUCT_NAME'},
		            {name:'PRODUCT_NEED'},
		            {name:'CASE_TYPE_ORA'},
		            {name:'RISK_LEVEL_PERSECT_ORA'},
		            {name:'IF_SECOND_STEP_ORA'},
		            {name:'HARD_INFO'}]
			)
		 });
		infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'AREA_NAME',xtype : 'textfield',fieldLabel : '区域中心',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'VISIT_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '首次拜访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'CASE_TYPE_ORA',xtype : 'textfield',fieldLabel : '案件类型',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_SECOND_STEP_ORA',xtype : 'textfield',fieldLabel : '是否进入第二阶段',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'DEPT_NAME',xtype : 'textfield',fieldLabel : '营业部门',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'PRODUCT_NAME',xtype : 'textfield',fieldLabel : '意向产品',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'RISK_LEVEL_PERSECT_ORA',xtype : 'textfield',fieldLabel : '客户风险预估',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					}]
				},{
					layout : 'form',labelWidth:100,
					items:[{xtype : 'textarea',name : 'PRODUCT_NEED',fieldLabel : '客户产品需求',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textarea',name : 'HARD_INFO',fieldLabel : '销售难点',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
				}]
			});
	}
	 if(type == '13'){//个金产品建议书准备
			store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktAdviseP.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_ID'},
		            {name:'CUST_NAME'},
		         	{name:'AREA_NAME'},
		            {name:'DEPT_NAME'},
		            {name:'RM'},
		            {name:'PRODUCT_NAME'},
		            {name:'RISK_LEVEL_ORA'},
		            {name:'IF_THIRD_STEP_ORA'},
		            {name:'PRODUCT_CATL_NAME'},
		            {name:'SALE_AMT'}]
			)
		 });
		infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'AREA_NAME',xtype : 'textfield',fieldLabel : '区域中心',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'PRODUCT_CATL_NAME',xtype : 'textfield',fieldLabel : '产品类别',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name:'SALE_AMT',xtype:'numberfield',fieldLabel:'预售金额',labelStylelabelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_THIRD_STEP_ORA',xtype : 'textfield',fieldLabel : '是否进入第三阶段',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'DEPT_NAME',xtype : 'textfield',fieldLabel : '营业部门',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'PRODUCT_NAME',xtype : 'textfield',fieldLabel : '预售产品',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'RISK_LEVEL_ORA',xtype : 'textfield',fieldLabel : '客户风险等级',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					}]
				}]
			});
	 }  
	 if(type == '14'){//个金修正产品准备书
			store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktChangeP.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_ID'},
		            {name:'CUST_NAME'},
		         	{name:'AREA_NAME'},
		            {name:'DEPT_NAME'},
		            {name:'RM'},
		            {name:'PRODUCT_NAME'},
		            {name:'RISK_LEVEL_ORA'},
		            {name:'IF_FOURTH_STEP_ORA'},
		            {name:'CHANGE_INFO'},
		            {name:'PRODUCT_CATL_NAME'},
		            {name:'SALE_AMT'}]
			)
		 });
		infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'AREA_NAME',xtype : 'textfield',fieldLabel : '区域中心',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'PRODUCT_CATL_NAME',xtype : 'textfield',fieldLabel : '产品类别',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name:'SALE_AMT',xtype:'numberfield',fieldLabel:'预售金额',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_FOURTH_STEP_ORA',xtype : 'textfield',fieldLabel : '是否进入第四阶段',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'DEPT_NAME',xtype : 'textfield',fieldLabel : '营业部门',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'PRODUCT_NAME',xtype : 'textfield',fieldLabel : '预售产品',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'RISK_LEVEL_ORA',xtype : 'textfield',fieldLabel : '客户风险等级',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					}]
				},{
						layout : 'form',labelWidth:100,
						items:[{xtype : 'textarea',name : 'CHANGE_INFO',fieldLabel : '建议书修改内容',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
					}]
			});
	 }  
	 if(type == '15'){//个金结案
			store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktEndP.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_ID'},
		            {name:'CUST_NAME'},
		         	{name:'AREA_NAME'},
		            {name:'DEPT_NAME'},
		            {name:'RM'},
		            {name:'PRODUCT_NAME'},
		            {name:'IF_DEAL_ORA'},
		            {name:'IF_DEAL'},
		            {name:'REFUSE_REASON'},
		            {name:'DEAL_DATE'},
		            {name:'BUY_AMT'},
		            {name:'ACCOUNT_DATE'}]
			)
		 });
		infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'AREA_NAME',xtype : 'textfield',fieldLabel : '区域中心',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_DEAL_ORA',xtype : 'textfield',fieldLabel : '是否成交',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name: 'BUY_AMT',xtype:'numberfield',fieldLabel:'申购金额',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'DEAL_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '成交日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}
						          ]
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'DEPT_NAME',xtype : 'textfield',fieldLabel : '营业部门',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'PRODUCT_NAME',xtype : 'textfield',fieldLabel : '申购产品',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'ACCOUNT_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '开户日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					}]
				},{
					layout : 'form',labelWidth:100,
					items:[{xtype : 'textarea',name : 'REFUSE_REASON',fieldLabel : '拒绝理由',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
				}]
		});
	 }  
	 if(type == '21'){//企商金 prospect信息
			store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktprospectC.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_ID'},
		            {name:'CUST_NAME'},
		            {name:'CUST_SOURCE_ORA'},
		            {name:'CUST_SOURCE_INFO'},
		            {name:'CUST_SOURCE_DATE'},
		            {name:'LINK_MAN'},
		            {name:'LINK_PHONE'},
		            {name:'VISIT_DATE'},
		            {name:'VISIT_WAY_ORA'},
		            {name:'AREA_NAME'},
		            {name:'DEPT_NAME'},
		            {name:'RM'},
		            {name:'PRODUCT_NEED'},
		            {name:'IF_FILES_ORA'},
		            {name:'IF_TRANS_CUST_ORA'},
		            {name:'IF_PIPELINE'},
		            {name:'IF_PIPELINE_ORA'},
		            {name:'RUFUSE_REASON'},
		            {name:'USER_NAME'}]
			)
		 });
		infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'AREA_NAME',xtype : 'textfield',fieldLabel : '区域中心',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'VISIT_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '拜访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'VISIT_WAY_ORA',xtype : 'textfield',fieldLabel : '拜访方式',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_PIPELINE_ORA',xtype : 'textfield',fieldLabel : '是否加入PIPELINE',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_TRANS_CUST_ORA',xtype : 'textfield',fieldLabel : '若无法取得财务资料能否转为我行存款户',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'DEPT_NAME',xtype : 'textfield',fieldLabel : '营业部门',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'LINK_PHONE',xtype : 'textfield',fieldLabel : '联系电话',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'CUST_SOURCE_ORA',xtype : 'textfield',fieldLabel : '客户来源',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'IF_FILES_ORA',xtype : 'textfield',fieldLabel : '是否能取得财务资料',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					}]
				},{
					layout : 'form',labelWidth:100,
					items:[{xtype : 'textarea',name : 'PRODUCT_NEED',fieldLabel : '客户产品需求',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textarea',name : 'RUFUSE_REASON',fieldLabel : '拒绝原因',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
				}]
			});
	}
	 if(type == '22'){//企商金合作意向
			store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktIntentC.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_ID'},
		            {name:'CUST_NAME'},
		            {name:'VISIT_DATE'},
		            {name:'AREA_NAME'},
		            {name:'DEPT_NAME'},
		            {name:'RM'},
		            {name:'GROUP_NAME'},
		            {name:'APPLY_AMT'},
		            {name:'CASE_TYPE_ORA'},
		            {name:'VISIT_DATE'},
		            {name:'MAIN_INSURE_ORA'},
		            {name:'MAIN_AMT'},
		            {name:'COMBY_AMT'},
		            {name:'GRADE_PERSECT_ORA'},
		            {name:'COMP_TYPE_ORA'},
		            {name:'HARD_INFO'},
		            {name:'CP_HARD_INFO'},
		            {name:'IF_SECOND_STEP_ORA'}]
			)
		 });
		infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'GROUP_NAME',xtype : 'textfield',fieldLabel : '集团名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'AREA_NAME',xtype : 'textfield',fieldLabel : '区域中心',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'VISIT_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '第一次上门拜访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'APPLY_AMT',xtype : 'textfield',fieldLabel : '申请额度',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'MAIN_AMT',xtype : 'textfield',fieldLabel : '主授信额度',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'DEPT_NAME',xtype : 'textfield',fieldLabel : '营业部门',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'GRADE_PERSECT_ORA',xtype : 'textfield',fieldLabel : '客户预评级',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}, 
						         {name : 'COMP_TYPE_ORA',xtype : 'textfield',fieldLabel : '企业类型',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'MAIN_INSURE_ORA',xtype : 'textfield',fieldLabel : '主担保方式',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'COMBY_AMT',xtype : 'textfield',fieldLabel : '搭配授信额度',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_SECOND_STEP_ORA',xtype : 'textfield',fieldLabel : '是否进入第二阶段',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					}]
				},{
					layout : 'form',labelWidth:100,
					items:[{xtype : 'textarea',name : 'HARD_INFO',fieldLabel : '营销难点',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textarea',name : 'CP_HARD_INFO',fieldLabel : 'CP-Project营销难点',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
				}]
			});
	}
	 if(type == '23'){//企商金CA准备
			store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktCaC.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_ID'},
		            {name:'CUST_NAME'},
		            {name:'AREA_NAME'},
		            {name:'DEPT_NAME'},
		            {name:'RM'},
		            {name:'GROUP_NAME'},
		            {name:'APPLY_AMT'},
		            {name:'CASE_TYPE_ORA'},
		            {name:'IF_ADD'},
		            {name:'IF_ADD_ORA'},
		            {name:'ADD_AMT'},
		            {name:'DD_DATE'},
		            {name:'SX_DATE'},
		            {name:'GRADE_LEVEL_ORA'},
		            {name:'COCO_DATE'},
		            {name:'COCO_INFO'},
		            {name:'CA_DATE_P'},
		            {name:'CA_DATE_R'},
		            {name:'CA_HARD_INFO'},
		            {name:'IF_THIRD_STEP_ORA'}]
			)
		 });
		infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'GROUP_NAME',xtype : 'textfield',fieldLabel : '集团名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'AREA_NAME',xtype : 'textfield',fieldLabel : '区域中心',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'APPLY_AMT',xtype : 'textfield',fieldLabel : '申请额度',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_ADD_ORA',xtype : 'textfield',fieldLabel : '若为旧案是否为增贷',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'DD_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : 'DD文件收齐日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'COCO_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : 'CO-CO MEETING 日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'CA_DATE_R',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '实际CA提交日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
						          
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'DEPT_NAME',xtype : 'textfield',fieldLabel : '营业部门',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'GRADE_LEVEL_ORA',xtype : 'textfield',fieldLabel : '客户评级',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'CASE_TYPE_ORA',xtype : 'textfield',fieldLabel : '案件类型',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'ADD_AMT',xtype : 'textfield',fieldLabel : '增贷金额',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'SX_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '授信文件收齐日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'CA_DATE_P',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '预计CA提交日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'IF_THIRD_STEP_ORA',xtype : 'textfield',fieldLabel : '是否进入第三阶段',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					}]
				},{
					layout : 'form',labelWidth:100,
					items:[{xtype : 'textarea',name : 'CA_HARD_INFO',fieldLabel : 'CA准备阶段难点',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textarea',name : 'COCO_INFO',fieldLabel : 'CO-CO MEETING结论',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
				}]
			});
	}
	 if(type == '24'){//信用审查
			store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktCheckC.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_ID'},
		            {name:'CUST_NAME'},
		            {name:'AREA_NAME'},
		            {name:'DEPT_NAME'},
		            {name:'RM'},
		            {name:'GROUP_NAME'},
		            {name:'APPLY_AMT'},
		            {name:'CASE_TYPE_ORA'},
		            {name:'IF_ADD'},
		            {name:'IF_ADD_ORA'},
		            {name:'ADD_AMT'},
		            {name:'XD_CA_DATE'},
		            {name:'CA_FORM'},
		            {name:'QA_DATE'},
		            {name:'RM_DATE'},
		            {name:'CC_DATE'},
		            {name:'XS_CC_DATE'},
		            {name:'RM_C_DATE'},
		            {name:'CO'},
		            {name:'MEMO'},
		            {name:'SP_LEVEL_ORA'},
		            {name:'IF_FOURTH_STEP_ORA'}]
			)
		 });
		infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'GROUP_NAME',xtype : 'textfield',fieldLabel : '集团名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'AREA_NAME',xtype : 'textfield',fieldLabel : '区域中心',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'APPLY_AMT',xtype : 'textfield',fieldLabel : '申请额度',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_ADD_ORA',xtype : 'textfield',fieldLabel : '若为旧案是否为增贷',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'SP_LEVEL_ORA',xtype : 'textfield',fieldLabel : '审批层级',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'XD_CA_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '信贷系统CA提交日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'RM_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : 'RM回复信审提问日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'XS_CC_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '信审提出CreditCall意见日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
						          
						          
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'DEPT_NAME',xtype : 'textfield',fieldLabel : '营业部门',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'GRADE_LEVEL_ORA',xtype : 'textfield',fieldLabel : '客户评级',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'CASE_TYPE_ORA',xtype : 'textfield',fieldLabel : '案件类型',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'ADD_AMT',xtype : 'textfield',fieldLabel : '增贷金额',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'IF_FOURTH_STEP_ORA',xtype : 'textfield',fieldLabel : '是否进入第四阶段',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'QA_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '信审提问Q&A日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'CC_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : 'CreditCall日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'RM_C_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : 'RM反馈CreditCall意见日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
						         
					}]
				},{
					layout : 'form',labelWidth:100,
					items:[{xtype : 'textfield',name : 'CO',fieldLabel : '对应CO',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textfield',name : 'CA_FORM',fieldLabel : '信贷系统CA提交额度框架',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textfield',name : 'MEMO',fieldLabel : '备注',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
				}]
			});
	}
	 if(type == '25'){// 核批阶段
			store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktApprovlC.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_ID'},
		            {name:'CUST_NAME'},
		            {name:'AREA_NAME'},
		            {name:'DEPT_NAME'},
		            {name:'RM'},
		            {name:'GROUP_NAME'},
		            {name:'APPLY_AMT'},
		            {name:'CASE_TYPE_ORA'},
		            {name:'IF_ADD'},
		            {name:'IF_ADD_ORA'},
		            {name:'ADD_AMT'},
		            {name:'XZ_CA_DATE'},
		            {name:'XZ_CA_FORM'},
		            {name:'USE_DATE_P'},
		            {name:'CC_OPEN_DATE'},
		            {name:'IF_SURE'},
		            {name:'IF_SURE_ORA'},
		            {name:'INSURE_AMT'},
		            {name:'INSURE_FORM'},
		            {name:'DELINE_REASON'},
		            {name:'SP_LEVEL_ORA'},
		            {name:'IF_FIFTH_STEP_ORA'}]
			)
		 });
		infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'GROUP_NAME',xtype : 'textfield',fieldLabel : '集团名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'AREA_NAME',xtype : 'textfield',fieldLabel : '区域中心',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'APPLY_AMT',xtype : 'textfield',fieldLabel : '申请额度',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_ADD_ORA',xtype : 'textfield',fieldLabel : '若为旧案是否为增贷',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'SP_LEVEL_ORA',xtype : 'textfield',fieldLabel : '审批层级',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_SURE_ORA',xtype : 'textfield',fieldLabel : '额度是否核准',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'XZ_CA_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '信贷系统CA修正日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'CC_OPEN_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : 'CC召开日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
						          
						          
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'DEPT_NAME',xtype : 'textfield',fieldLabel : '营业部门',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'GRADE_LEVEL_ORA',xtype : 'textfield',fieldLabel : '客户评级',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'CASE_TYPE_ORA',xtype : 'textfield',fieldLabel : '案件类型',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'ADD_AMT',xtype : 'textfield',fieldLabel : '增贷金额',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'IF_FIFTH_STEP_ORA',xtype : 'textfield',fieldLabel : '是否进入第五阶段',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'INSURE_AMT',xtype : 'textfield',fieldLabel : '核准金额',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'USE_DATE_P',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '客户预计用款日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
						         
					}]
				},{
					layout : 'form',labelWidth:100,
					items:[{xtype : 'textfield',name : 'INSURE_FORM',fieldLabel : '最终核准额度框架',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textfield',name : 'XZ_CA_FORM',fieldLabel : '信贷系统CA修正额度框架',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textarea',name : 'DELINE_REASON',fieldLabel : '未核准原因及说明',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
				}]
			});
	}
	 
	 if(type == '26'){// 已核批动拨
			store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktApprovedC.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_ID'},
		            {name:'CUST_NAME'},
		            {name:'AREA_NAME'},
		            {name:'DEPT_NAME'},
		            {name:'RM'},
		            {name:'GROUP_NAME'},
		            {name:'APPLY_AMT'},
		            {name:'CASE_TYPE_ORA'},
		            {name:'IF_ADD'},
		            {name:'IF_ADD_ORA'},
		            {name:'ADD_AMT'},
		            {name:'USE_DATE_P'},
		            {name:'IF_ACCEPT'},
		            {name:'IF_ACCEPT_ORA'},
		            {name:'USE_DATE_P'},
		            {name:'NOACCEPT_REASON'},
		            {name:'CTR_C_DATE'},
		            {name:'CTR_S_DATE'},
		            {name:'MORTGAGE_DATE'},
		            {name:'FILE_UP_DATE'},
		            {name:'SX_CTR_DATE'},
		            {name:'CTR_PROBLEM'},
		            {name:'PROBLEM_DATE'},
		            {name:'AMT_USE_DATE'},
		            {name:'ACCOUNT_DATE'},
		            {name:'PAY_DATE'}
		           ]
			)
		 });
		infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'GROUP_NAME',xtype : 'textfield',fieldLabel : '集团名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'AREA_NAME',xtype : 'textfield',fieldLabel : '区域中心',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'APPLY_AMT',xtype : 'textfield',fieldLabel : '申请额度',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_ADD_ORA',xtype : 'textfield',fieldLabel : '若为旧案是否为增贷',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_ACCEPT_ORA',xtype : 'textfield',fieldLabel : '额度是否核准',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'CTR_C_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '合同出具日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'MORTGAGE_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '抵质押登记日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'SX_CTR_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '授信合同部出具瑕疵单或启用单日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'AMT_USE_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '系统额度启用日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'PAY_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '实际拨款日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
						          
						          
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'DEPT_NAME',xtype : 'textfield',fieldLabel : '营业部门',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'GRADE_LEVEL_ORA',xtype : 'textfield',fieldLabel : '客户评级',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'CASE_TYPE_ORA',xtype : 'textfield',fieldLabel : '案件类型',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'ADD_AMT',xtype : 'textfield',fieldLabel : '增贷金额',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'USE_DATE_P',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '客户预计用款日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'CTR_S_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '合同签约日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'FILE_UP_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : 'RM提交所有对保文件至授信合同部日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'PROBLEM_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : 'RM反馈瑕疵日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'ACCOUNT_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '开户日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
						         
					}]
				},{
					layout : 'form',labelWidth:100,
					items:[{xtype : 'textarea',name : 'CTR_PROBLEM',fieldLabel : '合同若有瑕疵请列明',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textarea',name : 'NOACCEPT_REASON',fieldLabel : '客户不接受核准额度原因',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
				}]
			});
	}
	    var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[infoForm]
	   }); 
		var EchainPanel = new Mis.Echain.EchainPanel({
			instanceID:instanceid,
			nodeId:nodeid,
			nodeName:curNodeObj.nodeName,
			fOpinionFlag:curNodeObj.fOpinionFlag,
			approvalHistoryFlag:curNodeObj.approvalHistoryFlag,
			WindowIdclode:curNodeObj.windowid,
			callbackCustomFun:'3_a10##1'
		});
		var view = new Ext.Panel( {
			renderTo : 'viewEChian',
			 frame : true,
			width : document.body.scrollWidth,
			height : document.body.scrollHeight-40,
			autoScroll : true,
			layout : 'form',
			items : [bussFieldSetGrid,EchainPanel]

		});
			
		store.load({params : {
			id:id
        },
        callback:function(){
        	if(store.getCount()!=0){
        		loadFormData();
        	}
		}});
		function loadFormData(){
    		infoForm.getForm().loadRecord(store.getAt(0));
    		if(type== '11' && store.getAt(0).data.IF_PIPELINE =='1'){
    			infoForm.form.findField("RUFUSE_REASON").hide();
			}
    		if(type== '15' && store.getAt(0).data.IF_DEAL =='1'){
    			infoForm.form.findField("REFUSE_REASON").hide();
			}
    		if(type== '15' && store.getAt(0).data.IF_DEAL =='0'){
    			infoForm.form.findField("DEAL_DATE").hide();
    			infoForm.form.findField("ACCOUNT_DATE").hide();
    			infoForm.form.findField("BUY_AMT").hide();
			}
    		if(type== '21' && store.getAt(0).data.IF_PIPELINE =='1'){
    			infoForm.form.findField("RUFUSE_REASON").hide();
			}
    		if((type== '23' || type== '24' ||type== '25' )&& store.getAt(0).data.IF_ADD =='0'){
    			infoForm.form.findField("ADD_AMT").hide();
			}
    		if(type== '25' && store.getAt(0).data.IF_SURE =='1'){
    			infoForm.form.findField("INSURE_FORM").show();
    			infoForm.form.findField("INSURE_AMT").show();
    			infoForm.form.findField("DELINE_REASON").hide();
			}
    		if(type== '25' && store.getAt(0).data.IF_SURE =='0'){
    			infoForm.form.findField("INSURE_FORM").hide();
    			infoForm.form.findField("INSURE_AMT").hide();
    			infoForm.form.findField("DELINE_REASON").show();
			}
    		if(type== '26' && store.getAt(0).data.IF_ACCEPT =='0'){
    			infoForm.form.findField("CTR_C_DATE").hide();
    			infoForm.form.findField("CTR_S_DATE").hide();
    			infoForm.form.findField("MORTGAGE_DATE").hide();
    			infoForm.form.findField("FILE_UP_DATE").hide();
    			infoForm.form.findField("SX_CTR_DATE").hide();
    			infoForm.form.findField("CTR_PROBLEM").hide();
    			infoForm.form.findField("PROBLEM_DATE").hide();
    			infoForm.form.findField("AMT_USE_DATE").hide();
    			infoForm.form.findField("ACCOUNT_DATE").hide();
    			infoForm.form.findField("PAY_DATE").hide();
    			infoForm.form.findField("CTR_PROBLEM").hide();
			}
    		if(type== '26' && store.getAt(0).data.IF_ACCEPT =='1'){
    			infoForm.form.findField("NOACCEPT_REASON").hide();
			}
		}
		
		
	});
