/**
 * @description 企金客户营销流程 -  电访信息流程审批页面
 * @modify dongyi 2014-11-25
 */
Ext.onReady(function() {
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var type = instanceid.split('_')[2];
		var isNew =instanceid.split('_')[3];//此标志用于区别是否为新旧客户（1为新客户，2为既有客户）
		var nodeid = curNodeObj.nodeid;
		var store;
		var infoForm;
		if(type == '1'){//对私
			store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktCallP.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_ID'},
		            {name:'CUST_NAME'},
		            {name:'JOB_TYPE_ORA'},
		            {name:'LINK_PHONE'},
		            {name:'IF_NEW_ORA'},
		            {name:'CUST_SOURCE_ORA'},
		            {name:'PHONE_DATE'},
		            {name:'USER_NAME'},
		            {name:'CALL_RESULT'},
		            {name:'CALL_RESULT_ORA'},
		            {name:'VISIT_DATE'},
		            {name:'REFUSE_REASON'},
		            {name:'RECALL_DATE'},
		            {name:'CALL_INFO'}]
			)
			});
			infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_ID',xtype : 'textfield',fieldLabel : '客户编号',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'JOB_TYPE_ORA',xtype : 'textfield',fieldLabel : '职业类别',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_NEW_ORA',xtype : 'textfield',fieldLabel : '是否新客户',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'PHONE_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '电访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'CALL_RESULT_ORA',xtype : 'textfield',fieldLabel : '电访结果',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'RECALL_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '回拨日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'LINK_PHONE',xtype : 'textfield',fieldLabel : '联系电话',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'CUST_SOURCE_ORA',xtype : 'textfield',fieldLabel : '客户来源',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'USER_NAME',xtype : 'textfield',fieldLabel : '电访人',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'VISIT_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '拜访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					}]
				},{
					layout : 'form',labelWidth:100,
					items:[{xtype : 'textarea',name : 'CALL_INFO',fieldLabel : '电访内容',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textarea',name : 'REFUSE_REASON',fieldLabel : '拒绝原因',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
				}]
			});
			}
		if(type == '2'){//对公
			if(isNew =='1'){//新客户
				store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/ocrmFCallNewRecord.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_NAME'},
		            {name:'CUS_OWNBUSI_ORA'},
		            {name:'LINKMAN'},
		            {name:'LINK_TEL'},
		            {name:'CUST_SOURCE_ORA'},
		            {name:'CALL_DATE'},
		            {name:'MGR_NAME'},
		            {name:'CALL_RESULT_ORA'},
		            {name:'TEL_CONTACTER_ORA'},
		            {name:'CUST_REVENUE_ORA'},
		            {name:'OTHERBANK_TRADE'},
		            {name:'MARKT_PRODUCT'},
		            {name:'TEL_CONTACTER_REMARK'},
		            {name:'CUST_REVENUE_REMARK'},
		            {name:'MARKT_PRODUCT_REMARK'},
		            {name:'CUST_SOURCE_REMARK'},
		            {name:'OTHER'}]
			)
			});
			
			infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'LINKMAN',xtype : 'textfield',fieldLabel : '联系人',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'CUST_SOURCE_ORA',xtype : 'textfield',fieldLabel : '客户来源',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'CALL_RESULT_ORA',xtype : 'textfield',fieldLabel : '电访结果',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'OTHERBANK_TRADE',xtype : 'textfield',fieldLabel : '他行往来情况',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'CALL_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '电访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'CUS_OWNBUSI_ORA',xtype : 'textfield',fieldLabel : '所属行业',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'LINK_TEL',xtype : 'textfield',fieldLabel : '联系号码',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'TEL_CONTACTER_ORA',xtype : 'textfield',fieldLabel : '电访接洽人',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'CUST_REVENUE_ORA',xtype : 'textfield',fieldLabel : '公司营收',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
								 {name : 'MARKT_PRODUCT',xtype : 'textfield',fieldLabel : '拟营销产品',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
								 {name : 'MGR_NAME',xtype : 'textfield',fieldLabel : '客户经理',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					}]
				},{
					layout : 'form',labelWidth:100,
					items:[{xtype : 'textarea',name : 'TEL_CONTACTER_REMARK',fieldLabel : '电访接洽人备注',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textarea',name : 'CUST_REVENUE_REMARK',fieldLabel : '公司营收备注',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textarea',name : 'MARKT_PRODUCT_REMARK',fieldLabel : '拟营销产品备注',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textarea',name : 'CUST_SOURCE_REMARK',fieldLabel : '客户来源备注',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
					       {xtype : 'textarea',name : 'OTHER',fieldLabel : '其他',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
				}]
			});
			}
			if(isNew =='2'){//既有客户
			store = new Ext.data.Store({
				restful:true,	
		        proxy : new Ext.data.HttpProxy(
		        		{
		        			url:basepath+'/mktCallOldFRecord.json'
		        		}),
		        reader: new Ext.data.JsonReader({
		        	root : 'json.data'
		        }, [{name:'CUST_NAME'},
		            {name:'RESPONDENTS_POSITION'},
		            {name:'RESPONDENTS_NAME'},
		            {name:'RESPONDENTS_CONTACT'},
		            {name:'BANK_PARTICIPANTS'},
		            {name:'CALL_PURPOSE_ORA'},
		            {name:'CALL_DATE'},
		            {name:'MGR_NAME'},
		            {name:'CUST_BUSI_CONDITION_ORA'},
		            {name:'MAIN_BUSI_CHANGE_ORA'},
		            {name:'REVENUE_CHANGE_ORA'},
		            {name:'PROFI_CHANGE_ORA'},
		            {name:'MAIN_SUPPLIER_CHANGE_ORA'},
		            {name:'MAIN_BUYER_CHANGE_ORA'},
		            {name:'EQUITY_STRUC_CHANGE_ORA'},
		            {name:'MANAGEMENT_CHANGE_ORA'},
		            {name:'COLLATERAL_CONDITION_ORA'},
		            {name:'COOPERATION_CHANGE_ORA'},
		            {name:'IF_PRECONTRACT_ORA'},
		            {name:'MARKT_RESULT'},
		            {name:'MARKT_PRODUCT'},
		            
		            {name:'CALL_REASON'},
		            {name:'MAIN_BUSI_CHANGE_REMARK'},
		            {name:'REVENUE_CHANGE_REMARK'},
		            {name:'PROFI_CHANGE_REMARK'},
		            {name:'MAIN_SUPPLIER_CHANGE_REMARK'},
		            {name:'MAIN_BUYER_CHANGE_REMARK'},
		            {name:'EQUITY_STRUC_CHANGE_REMARK'},
		            {name:'MANAGEMENT_CHANGE_REMARK'},
		            {name:'COLLATERAL_CONDITION_REMARK'},
		            {name:'COOPERATION_CHANGE_REMARK'},
		            {name:'OTHER'},
		            {name:'MATTERS_FOLLOW'}]
			)
			});
			infoForm = new Ext.FormPanel( {
				frame : true,
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [ {name : 'CUST_NAME',xtype : 'textfield',fieldLabel : '客户名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'RESPONDENTS_NAME',xtype : 'textfield',fieldLabel : '受访人名称',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'BANK_PARTICIPANTS',xtype : 'textfield',fieldLabel : 'BANK_PARTICIPANTS',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'CALL_DATE',xtype : 'datefield',format : 'Y-m-d',fieldLabel : '电访日期',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'CUST_BUSI_CONDITION_ORA',xtype : 'textfield',fieldLabel : '客户营运状况',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'REVENUE_CHANGE_ORA',xtype : 'textfield',fieldLabel : '营收是否大幅变化',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'MAIN_SUPPLIER_CHANGE_ORA',xtype : 'textfield',fieldLabel : '主要供应商是否调整',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'EQUITY_STRUC_CHANGE_ORA',xtype : 'textfield',fieldLabel : '股权结构是否变更',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'COLLATERAL_CONDITION_ORA',xtype : 'textfield',fieldLabel : '担保品状况',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'IF_PRECONTRACT_ORA',xtype : 'textfield',fieldLabel : '是否预约拜访',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						          {name : 'MARKT_PRODUCT',xtype : 'textfield',fieldLabel : '本次拜访拟营销产品',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					},{
						layout : 'form',columnWidth : .5,labelWidth:100,
						items : [{name : 'RESPONDENTS_POSITION',xtype : 'textfield',fieldLabel : '受访人职位',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'RESPONDENTS_CONTACT',xtype : 'textfield',fieldLabel : '受访人联系方式',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'CALL_PURPOSE_ORA',xtype : 'textfield',fieldLabel : '电访目的',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'MGR_NAME',xtype : 'textfield',fieldLabel : '客户经理',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'MAIN_BUSI_CHANGE_ORA',xtype : 'textfield',fieldLabel : '主营业务是否变更',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'PROFI_CHANGE_ORA',xtype : 'textfield',fieldLabel : '获利率是否大幅变化',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'MAIN_BUYER_CHANGE_ORA',xtype : 'textfield',fieldLabel : '主要买方是否调整',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'MANAGEMENT_CHANGE_ORA',xtype : 'textfield',fieldLabel : '经营层是否有变更',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'COOPERATION_CHANGE_ORA',xtype : 'textfield',fieldLabel : '与银行合作状况是否有变化',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'},
						         {name : 'MARKT_RESULT',xtype : 'textfield',fieldLabel : '营销结果',labelStyle : 'text-align:right;',disabled:true,anchor : '95%'}]
					}]
				},{
					layout : 'form',labelWidth:100,
					items:[ {xtype : 'textarea',name : 'CALL_REASON',fieldLabel : '电访目的补充说明',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
							{xtype : 'textarea',name : 'MAIN_BUSI_CHANGE_REMARK',fieldLabel : '主营业务是否变更说明',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
							{xtype : 'textarea',name : 'REVENUE_CHANGE_REMARK',fieldLabel : '营收是否大幅变化说明',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
							{xtype : 'textarea',name : 'PROFI_CHANGE_REMARK',fieldLabel : '获利率是否大幅变化说明',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
							{xtype : 'textarea',name : 'MAIN_SUPPLIER_CHANGE_REMARK',fieldLabel : '主要供应商是否调整说明',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
							{xtype : 'textarea',name : 'MAIN_BUYER_CHANGE_REMARK',fieldLabel : '主要买方是否调整说明',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
							{xtype : 'textarea',name : 'EQUITY_STRUC_CHANGE_REMARK',fieldLabel : '股权结构是否变更说明',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
							{xtype : 'textarea',name : 'MANAGEMENT_CHANGE_REMARK',fieldLabel : '经营层是否有变更说明',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
							{xtype : 'textarea',name : 'COLLATERAL_CONDITION_REMARK',fieldLabel : '担保品状况说明',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
							{xtype : 'textarea',name : 'COOPERATION_CHANGE_REMARK',fieldLabel : '与银行合作状况是否有变化说明',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
							{xtype : 'textarea',name : 'OTHER',fieldLabel : '其他补充说明',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'},
							{xtype : 'textarea',name : 'MATTERS_FOLLOW',fieldLabel : '跟进事项',labelStyle : 'text-align:right;',disabled:true,anchor : '97%'}]
				}]
			});
			}
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
    		if(type == '1'){
    			if(store.getAt(0).data.CALL_RESULT =='1'){
        			infoForm.form.findField("VISIT_DATE").show();
        			infoForm.form.findField("RECALL_DATE").hide();
        			infoForm.form.findField("REFUSE_REASON").hide();
    			}
        		if(store.getAt(0).data.CALL_RESULT =='2'){
        			infoForm.form.findField("VISIT_DATE").hide();
        			infoForm.form.findField("RECALL_DATE").hide();
        			infoForm.form.findField("REFUSE_REASON").show();
    			}
        		if(store.getAt(0).data.CALL_RESULT =='3'){
        			infoForm.form.findField("VISIT_DATE").hide();
        			infoForm.form.findField("RECALL_DATE").show();
        			infoForm.form.findField("REFUSE_REASON").hide();
    			}
		}
		}
		
	});
