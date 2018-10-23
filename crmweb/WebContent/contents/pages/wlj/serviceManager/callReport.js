/**
 * callReport
 * 2014/08/22
 */
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
    	'/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js',
    	'/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',
    	'/contents/pages/common/Com.yucheng.bcrm.common.CustomerQueryField.js',
    	'/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js',//产品放大镜
        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'//导出
        ]);
Ext.QuickTips.init();
var needGrid = true;
var createView = true;//!JsContext.checkGrant('callReport_create');//是否启用新增面板
var editView =  true;//!JsContext.checkGrant('callReport_modify');//是否启用修改面板
var detailView = true;// !JsContext.checkGrant('callReport_detail');//是否启用详情面板
var formViewers = true;
var cID ={};
var lookupTypes = ['CALLREPORT_VISIT_TYPE',//callreport拜访方式
                   'CALLREPORT_CUST_TYPE',//callreport客户类型
                   'TYPE_RANGE',//客户类型
                   'IF_READ',
                   'REMIND_TYPE',
                   'CALLREPORT_REMIND_REASON',//call Repor汇款原因
                   'CALLREPORT_CASE_STAGE',//call Repor是否结案
                   'CALLREPORT_DQ_REASON',//call Repor出账原因
                   'XD000353',//既有客户渠道
                   'CALLREPORT_CUST_CHANNEL',//call Report客户渠道
                   'CALLREPORT_SAVES_STAGE',//call Report销售阶段
                   'CALLREPORT_FAIL_REASON',//call Report失败原因
                   'CALLREPORT_SEQUEL_STAGE',//call Report是否续作
                   'CALLREPORT_PRODUCT_NAME',
                   'XD000040',//证件类型
                   'INTERVIEW_RESULTS'//访谈结果
                   ];

var url = basepath+'/ocrmFSeCallreport.json';//查询
WLJUTIL.alwaysLockCurrentView = true;//由于在保存之后，还需要操作附件列表，所以本功能页面锁定悬浮面板滑出
var callIds ='';
var fields = [
            {name: 'CALL_ID',hidden:true},
  		    {name: 'CUST_ID',text:'客户编号',gridField:true,searchField: true,resutlWidth:100},
  		    {name: 'CUST_TYPE',text :'客户类型',translateType:'CALLREPORT_CUST_TYPE',searchField: true,resutlWidth:50},
  		    {name: 'CUST_NAME_TEST',gridField:false,searchField: true,resutlWidth:50,hidden:true},	
	        {name: 'CUST_NAME',text:'客户名称',xtype:'customerquery',isNew:true,hiddenName:'CUST_ID',singleSelected:true,searchField:true,resutlWidth:100,allowBlank:false,
	        		callback:function(a,b){
	        			var qz = b[0].json.POTENTIAL_FLAG; //1潜在  0 非潜在 9 未知
	        			var gj = b[0].json.CUST_TYPE;
	        			var rs = b[0].json.RECORD_SESSION;
	        			var tel = b[0].json.LINKMAN_TEL.replace("#-","");
//	        			debugger;
	        			if(getCurrentView() != null && getCurrentView() != undefined){
	        				if(getCurrentView().contentPanel != null && getCurrentView().contentPanel != undefined){
	        					if(qz == '1'){//潜在
			        				getCurrentView().contentPanel.getForm().findField('CUST_TYPE').setValue("2");
//			        				getCurrentView().contentPanel.getForm().findField('LINK_PHONE').setReadOnly(false);
//			        				getCurrentView().contentPanel.getForm().findField('LINK_PHONE').setValue(b[0].json.LINKMAN_TEL);
			        				
			        				getCurrentView().contentPanel.getForm().findField('CUST_CHANNEL').setReadOnly(true);
			        				getCurrentView().contentPanel.getForm().findField('CUST_CHANNEL').bindStore(findLookupByType('XD000353'));
			        				getCurrentView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(b[0].json.SOURCE_CHANNEL);
			        				
			        				if(gj == '2'){
			        					getCurrentView().contentPanel.getForm().findField('IDENT_TYPE').setVisible(true);
				        				getCurrentView().contentPanel.getForm().findField('IDENT_NO').setVisible(true);
			        				}else{
			        					getCurrentView().contentPanel.getForm().findField('IDENT_TYPE').setVisible(false);
				        				getCurrentView().contentPanel.getForm().findField('IDENT_NO').setVisible(false);
			        				}
			        				getCurrentView().contentPanel.getForm().findField('RECORD_SESSION').setValue(rs);
			        				getCurrentView().contentPanel.getForm().findField('LINK_PHONE').setValue(tel);
			        				
			        			}else if(qz == '0'){//既有
			        				getCurrentView().contentPanel.getForm().findField('CUST_TYPE').setValue("1");
//			        				getCurrentView().contentPanel.getForm().findField('LINK_PHONE').setReadOnly(true);
//			        				getCurrentView().contentPanel.getForm().findField('LINK_PHONE').setValue(b[0].json.LINKMAN_TEL);
			        				
			        				getCurrentView().contentPanel.getForm().findField('CUST_CHANNEL').setReadOnly(true);
			        				getCurrentView().contentPanel.getForm().findField('CUST_CHANNEL').bindStore(findLookupByType('XD000353'));
			        				getCurrentView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(b[0].json.SOURCE_CHANNEL);
			        				
			        				getCurrentView().contentPanel.getForm().findField('IDENT_TYPE').setVisible(false);
			        				getCurrentView().contentPanel.getForm().findField('IDENT_NO').setVisible(false);
			        				
			  		    			if(b[0].json.SOURCE_CHANNEL=='14'){//MGM
			  		    				getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setVisible(true);
			  		    			}else{
			  		    				getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setVisible(false);
			  		    				getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue("");
			  		    			}
			  		    			getCurrentView().contentPanel.getForm().findField('RECORD_SESSION').setValue("");
			  		    			getCurrentView().contentPanel.getForm().findField('LINK_PHONE').setValue(tel);
			        			}
	        				}
	        			}
	        			
	        			createStore_dq.load({params:{callReport_custId:b[0].json.CUST_ID}});//到期提醒
	        			createStore_ls.load({params:{callReport_custId:b[0].json.CUST_ID}});//大额流失
	        			cID.CUST_ID = b[0].json.CUST_ID;
						}
	        },
	        {name: 'CREATE_USER',text:'客户经理名称',resutlWidth:100,allowBlank: false,gridField:true},
	        {name: 'LINK_PHONE',text:'联系电话',resutlWidth:100,maxLength:32},
	        {name: 'CUST_CHANNEL',text:'客户渠道',resutlWidth:80,translateType:'XD000353',
	        	listeners:{
  		    		select:function(combo,record){
  		    			var v = this.getValue();
  		    			if(v=='14'){//MGM
  		    				getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setVisible(true);
  		    			}else{
  		    				getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setVisible(false);
  		    				getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue("");
  		    			}
  					}
		    	}
	        },
  		    {name: 'VISIT_DATE',text :'拜访日期',format:'Y-m-d',xtype:'datefield',allowBlank: false,resutlWidth:80,value:new Date()},
  		    {name: 'BEGIN_DATE',text :'起始时间',format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'17:45',allowBlank: false,value:new Date(),resutlWidth:60},
  		    {name: 'END_DATE',text :'结束时间',format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'18:00',allowBlank: false,resutlWidth:60,value:new Date(),
  		    	listeners:{
  		    		select:function(combo,record){
  		    			//结束时间
  		    			var v = this.getValue().split(":");
  		    			var h1 = parseInt(v[0]);
  		    			var s1 = parseInt(v[1]);
  		    			//起始时间
  		    			var o = getCurrentView().contentPanel.getForm().findField('BEGIN_DATE').getValue().split(":");
  		    			var h2 = parseInt(o[0]);
  		    			var s2 = parseInt(o[1]);
  		    			if(h1<h2){
  		    				alert("结束时间["+this.getValue()+"]不能小于起始时间["+getCurrentView().contentPanel.getForm().findField('BEGIN_DATE').getValue()+"]");
  		    				getCurrentView().contentPanel.getForm().findField('END_DATE').setValue('');
  		    			}else if(h1==h2){
  		    				if(s1<s2){
  		    					alert("结束时间["+this.getValue()+"]不能小于起始时间["+getCurrentView().contentPanel.getForm().findField('BEGIN_DATE').getValue()+"]");
  		    					getCurrentView().contentPanel.getForm().findField('END_DATE').setValue('');
  		    				}
  		    			}
  					}
		    	}},
  		    {name: 'VISIT_WAY',text:'拜访方式',translateType:'CALLREPORT_VISIT_TYPE',allowBlank: false,resutlWidth:50},
  		    {name: 'RECOMMEND_CUST_ID',text : 'MGM推荐客户',hiddenName: 'RECOMMEND_CUST_ID',xtype: 'customerquery',singleSelected: true,resutlFloat:'right',resutlWidth:100},
//  		    {name: 'VISIT_CONTENT', text : '访谈内容',xtype:'textarea',allowBlank: false,maxLength:1000},
  		    {name: 'VISIT_CONTENT', text : '访谈结果',translateType:'INTERVIEW_RESULTS',allowBlank: false},
  		    {name:'MKT_BAK_NOTE',text:'访谈内容',xtype:'textarea',maxLength:200,allowBlank: false},
  		    {name:'RECORD_SESSION',text:'沟通话术',xtype:'textarea',gridField:false,maxLength:200},
  		    {name: 'NEXT_VISIT_DATE',text:'下次拜访时间',resutlWidth:100,format:'Y-m-d',xtype:'datefield'},
  		    {name: 'NEXT_VISIT_WAY',text:'下次拜访方式',resutlWidth:100,translateType:'CALLREPORT_VISIT_TYPE'},
  		    {name: 'LAST_UPDATE_USER',text:'最后更新人',resutlWidth:60,hidden:true},
  		    {name: 'LAST_UPDATE_TM',text:'最后更新时间',gridField:false,hidden:true},
  		    {name: 'TEST'},
  		    {name: 'IDENT_TYPE',text:'证件类型',resutlWidth:100,gridField:false,translateType:'XD000040'},
  		    {name: 'IDENT_NO',text:'证件编号',resutlWidth:100,gridField:false},
  		    {name: 'POT_CUS_ID',text:'潜在客户编号',resutlWidth:100,gridField:false}
  		];
var createFormViewer = [{
	columnCount :2,
	fields : ['CALL_ID','CUST_ID','CUST_NAME','CUST_TYPE','IDENT_TYPE','IDENT_NO','VISIT_DATE','BEGIN_DATE','END_DATE','LINK_PHONE',
	          'VISIT_WAY','CUST_CHANNEL','RECOMMEND_CUST_ID','NEXT_VISIT_DATE','NEXT_VISIT_WAY'],
	fn : function(CALL_ID,CUST_ID,CUST_NAME,CUST_TYPE,IDENT_TYPE,IDENT_NO,VISIT_DATE,BEGIN_DATE,END_DATE,LINK_PHONE,
	          VISIT_WAY,CUST_CHANNEL,RECOMMEND_CUST_ID,NEXT_VISIT_DATE,NEXT_VISIT_WAY){
		CALL_ID.hidden= true;
		CUST_ID.hidden =true;
		CUST_TYPE.readOnly = true;
		CUST_TYPE.cls = 'x-readOnly';
//		LINK_PHONE.readOnly = true;
//		LINK_PHONE.cls = 'x-readOnly';
		CUST_CHANNEL.readOnly = true;
		CUST_CHANNEL.cls = 'x-readOnly';
		LINK_PHONE.readOnly = true;
		LINK_PHONE.cls = 'x-readOnly';
		return [CALL_ID,CUST_ID,CUST_NAME,CUST_TYPE,IDENT_TYPE,IDENT_NO,VISIT_DATE,BEGIN_DATE,END_DATE,LINK_PHONE,
		          VISIT_WAY,CUST_CHANNEL,RECOMMEND_CUST_ID,NEXT_VISIT_DATE,NEXT_VISIT_WAY];
	}
},{/**内容富文本编辑框**/
	columnCount:2,
	fields : ['VISIT_CONTENT'],//访谈结果
	fn : function(VISIT_CONTENT){
		VISIT_CONTENT.anchor = '95%';
		return [VISIT_CONTENT];
	}
},{/**内容富文本编辑框**/
	columnCount:1,
	fields : ['MKT_BAK_NOTE'],
	fn : function(MKT_BAK_NOTE){
		MKT_BAK_NOTE.anchor = '95%';
		return [MKT_BAK_NOTE];
	}
},{/**内容富文本编辑框**/
	columnCount:1,
	fields : ['RECORD_SESSION'],
	fn : function(RECORD_SESSION){
		RECORD_SESSION.anchor = '95%';
		RECORD_SESSION.readOnly = true;
		RECORD_SESSION.cls = 'x-readOnly';
		return [RECORD_SESSION];
	}
},{
	columnCount:0.945,
	fields : ['TEST'],
	fn : function(TEST){
		return [createPanel_SJ];//商机信息
	}
},{
	columnCount:0.945,
	fields : ['TEST'],
	fn : function(TEST){
		return [createPanel_DQ];//到期通知信息
	}
},{
	columnCount:0.945,
	fields : ['TEST'],
	fn : function(TEST){
		return [createPanel_LS];//大额流失
	}
},{
	columnCount:0.945,
	fields : ['TEST'],
	fn : function(TEST){
		return [createPanel_SAN];//近三次联系内容createPanel_SAN
	}
}];
var editFormViewer = [{
	columnCount :2,
	fields : ['CALL_ID','CUST_ID','CUST_NAME','CUST_TYPE','IDENT_TYPE','IDENT_NO','VISIT_DATE','BEGIN_DATE','END_DATE','LINK_PHONE',
	          'VISIT_WAY','CUST_CHANNEL','RECOMMEND_CUST_ID','NEXT_VISIT_DATE','NEXT_VISIT_WAY','LAST_UPDATE_TM'],
	fn : function(CALL_ID,CUST_ID,CUST_NAME,CUST_TYPE,IDENT_TYPE,IDENT_NO,VISIT_DATE,BEGIN_DATE,END_DATE,LINK_PHONE,
	          VISIT_WAY,CUST_CHANNEL,RECOMMEND_CUST_ID,NEXT_VISIT_DATE,NEXT_VISIT_WAY,LAST_UPDATE_TM){
		CALL_ID.hidden= true;
		CUST_ID.hidden =true;
		CUST_TYPE.readOnly = true;
		CUST_TYPE.cls = 'x-readOnly';
//		LINK_PHONE.readOnly = true;
//		LINK_PHONE.cls = 'x-readOnly';
		CUST_CHANNEL.readOnly = true;
		CUST_CHANNEL.cls = 'x-readOnly';
		LINK_PHONE.readOnly = true;
		LINK_PHONE.cls = 'x-readOnly';
		return [CALL_ID,CUST_ID,CUST_NAME,CUST_TYPE,IDENT_TYPE,IDENT_NO,VISIT_DATE,BEGIN_DATE,END_DATE,LINK_PHONE,
		          VISIT_WAY,CUST_CHANNEL,RECOMMEND_CUST_ID,NEXT_VISIT_DATE,NEXT_VISIT_WAY,LAST_UPDATE_TM];
	}
},{/**内容富文本编辑框**/
	columnCount:2,
	fields : ['VISIT_CONTENT'],//访谈结果
	fn : function(VISIT_CONTENT){
		VISIT_CONTENT.anchor = '95%';
		return [VISIT_CONTENT];
	}
},{/**内容富文本编辑框**/
	columnCount:1,
	fields : ['MKT_BAK_NOTE'],
	fn : function(MKT_BAK_NOTE){
		MKT_BAK_NOTE.anchor = '95%';
		return [MKT_BAK_NOTE];
	}
},{/**内容富文本编辑框**/
	columnCount:1,
	fields : ['RECORD_SESSION'],
	fn : function(RECORD_SESSION){
		RECORD_SESSION.anchor = '95%';
		RECORD_SESSION.readOnly = true;
		RECORD_SESSION.cls = 'x-readOnly';
		return [RECORD_SESSION];
	}
},{
	columnCount:0.945,
	fields : ['TEST'],
	fn : function(TEST){
		return [editPanel_SJ];//商机
	}
},{
	columnCount:0.945,
	fields : ['TEST'],
	fn : function(TEST){
		return [editPanel_DQ];//到期通知信息
	}
},{
	columnCount:0.945,
	fields : ['TEST'],
	fn : function(TEST){
		return [editPanel_LS];//大额流失
	}
},{
	columnCount:0.945,
	fields : ['TEST'],
	fn : function(TEST){
		return [editPanel_SAN];//近三次联系内容createPanel_SAN
	}
}];
var editFormCfgs = {
		suspendWidth: 1200,
		formButtons:[{
			text : '返回',
			fn : function(formPanel){
				hideCurrentView();
			}
		}]
		};
//====================详情信息======================
var detailFormViewer = [{
	columnCount :2,
	fields : ['CALL_ID','CUST_ID','CUST_NAME','CUST_TYPE','VISIT_DATE','BEGIN_DATE','END_DATE','LINK_PHONE',
	          'VISIT_WAY','CUST_CHANNEL','RECOMMEND_CUST_ID'],
	fn : function(CALL_ID,CUST_ID,CUST_NAME,CUST_TYPE,VISIT_DATE,BEGIN_DATE,END_DATE,LINK_PHONE,
	          VISIT_WAY,CUST_CHANNEL,RECOMMEND_CUST_ID){
		CALL_ID.hidden= true;
		CUST_ID.hidden =true;
		CUST_NAME.readOnly = true;CUST_TYPE.readOnly = true;VISIT_DATE.readOnly = true;LINK_PHONE.readOnly = true;
        VISIT_WAY.readOnly = true;CUST_CHANNEL.readOnly = true;RECOMMEND_CUST_ID.readOnly = true;BEGIN_DATE.readOnly=true;
        END_DATE.readOnly=true;
        BEGIN_DATE.cls='x-readOnly';END_DATE.cls='x-readOnly';
        CUST_NAME.cls='x-readOnly';CUST_TYPE.cls='x-readOnly';VISIT_DATE.cls='x-readOnly';LINK_PHONE.cls='x-readOnly';
        VISIT_WAY.cls='x-readOnly';CUST_CHANNEL.cls='x-readOnly';RECOMMEND_CUST_ID.cls='x-readOnly';
        LINK_PHONE.readOnly = true;
		LINK_PHONE.cls = 'x-readOnly';
		return [CALL_ID,CUST_ID,CUST_NAME,CUST_TYPE,VISIT_DATE,BEGIN_DATE,END_DATE,LINK_PHONE,
		          VISIT_WAY,CUST_CHANNEL,RECOMMEND_CUST_ID];
	}
},{
	columnCount:2,
	fields : ['NEXT_VISIT_DATE','NEXT_VISIT_WAY'],
	fn : function(NEXT_VISIT_DATE,NEXT_VISIT_WAY){
		NEXT_VISIT_DATE.readOnly=true;NEXT_VISIT_WAY.readOnly=true;
		NEXT_VISIT_DATE.cls='x-readOnly';NEXT_VISIT_WAY.cls='x-readOnly';
		return [NEXT_VISIT_DATE,NEXT_VISIT_WAY];
	}
},{/**内容富文本编辑框**/
	columnCount:2,
	fields : ['VISIT_CONTENT'],//访谈结果
	fn : function(VISIT_CONTENT){
		VISIT_CONTENT.readOnly = true;
		VISIT_CONTENT.cls = 'x-readOnly';
		return [VISIT_CONTENT];
	}
},{/**内容富文本编辑框**/
	columnCount:1,
	fields : ['MKT_BAK_NOTE'],
	fn : function(MKT_BAK_NOTE){
		MKT_BAK_NOTE.readOnly = true;
		MKT_BAK_NOTE.cls = 'x-readOnly';
		return [MKT_BAK_NOTE];
	}
},{/**内容富文本编辑框**/
	columnCount:1,
	fields : ['RECORD_SESSION'],
	fn : function(RECORD_SESSION){
		RECORD_SESSION.anchor = '95%';
		RECORD_SESSION.readOnly = true;
		RECORD_SESSION.cls = 'x-readOnly';
		return [RECORD_SESSION];
	}
}];
var formCfgs = {
		suspendWidth: 900,
		formButtons : [{
			text : '关闭',
			fn : function(formPanel){
				 hideCurrentView();
			}
		}]
};
//=====================商机详情===========================
var createSm = new Ext.grid.CheckboxSelectionModel();
var createRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 30
});
var createCm =  new Ext.grid.ColumnModel([createRrownum,createSm,	
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'CALL_ID',dataIndex:'CALL_ID',sortable:true,width:120,hidden:true},
                                     {header:'商机名称',dataIndex:'BUSI_NAME',sortable:true,width:120},
                                     {header:'产品编号',dataIndex:'PRODUCT_ID',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_PRODUCT_NAME",value);
                            			return val?val:"";
                            			}},
//                                     {header:'产品编码',dataIndex:'PRODUCT_NAME',sortable:true,width:120},
                                     {header:'销售阶段',dataIndex:'SALES_STAGE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_SAVES_STAGE",value);
                            			return val?val:"";
                            			}},
                                     {header:'金额(元)',dataIndex:'AMOUNT',sortable:true,width:120},
                                     {header:'失败原因',dataIndex:'FAIL_REASON',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_FAIL_REASON",value);
                            			return val?val:"";
                            			}},
                                     {header:'预计成交时间',dataIndex:'ESTIMATED_TIME',sortable:true,width:120},
                                     {header:'备注',dataIndex:'REMARK',sortable:true,width:120}
        	                                     ]); 
var createStore_sj_xq = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFSeCallreportBusi.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'CALL_ID'},
			    {name:'PRODUCT_ID'},
//			    {name:'PRODUCT_NAME'},
			    {name:'SALES_STAGE'},
			    {name:'AMOUNT'},
			    {name:'FAIL_REASON'},
			    {name:'BUSI_NAME'},
			    {name:'ESTIMATED_TIME'},
			    {name:'LAST_UPDATE_USER'},
			    {name:'LAST_UPDATE_TM'},
			    {name:'REMARK'}
			     ])
});

var createPanel_SJ_XQ = new Ext.grid.GridPanel({
	title : '访谈结果(商机)',
	autoScroll: true,
	height:200,
    tbar : [],
	store : createStore_sj_xq,
	frame : true,
	sm:createSm,
	cm : createCm,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//===================新增商机=====================
var createSm = new Ext.grid.CheckboxSelectionModel();
var createRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 30
});
var createCm =  new Ext.grid.ColumnModel([createRrownum,createSm,	
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'CALL_ID',dataIndex:'CALL_ID',sortable:true,width:120,hidden:true},
                                     {header:'商机名称',dataIndex:'BUSI_NAME',sortable:true,width:120},
                                     {header:'产品编号',dataIndex:'PRODUCT_ID',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_PRODUCT_NAME",value);
                            			return val?val:"";
                            			}},
//                                     {header:'产品编码',dataIndex:'PRODUCT_NAME',sortable:true,width:120},
                                     {header:'销售阶段',dataIndex:'SALES_STAGE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_SAVES_STAGE",value);
                            			return val?val:"";
                            			}},
                                     {header:'金额(元)',dataIndex:'AMOUNT',sortable:true,width:120},
                                     {header:'失败原因',dataIndex:'FAIL_REASON',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_FAIL_REASON",value);
                            			return val?val:"";
                            			}},
                                     {header:'预计成交时间',dataIndex:'ESTIMATED_TIME',sortable:true,width:120},
                                     {header:'备注',dataIndex:'REMARK',sortable:true,width:120}
        	                                     ]); 
var createStore_sj = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFSeCallreportBusi.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'CALL_ID'},
			    {name:'PRODUCT_ID'},
//			    {name:'PRODUCT_NAME'},
			    {name:'SALES_STAGE'},
			    {name:'AMOUNT'},
			    {name:'FAIL_REASON'},
			    {name:'BUSI_NAME'},
			    {name:'ESTIMATED_TIME'},
			    {name:'LAST_UPDATE_USER'},
			    {name:'LAST_UPDATE_TM'},
			    {name:'REMARK'}
			     ])
});

var createPanel_SJ = new Ext.grid.GridPanel({
	title : '访谈结果(商机)',
	autoScroll: true,
	height:200,
    tbar : [{
    	text : '新增',
		id:'addSJ',
		 handler:function() {
			showCustomerViewByTitle('新增商机');
		}
    },{
    	text : '修改',
		id:'addSJ_Edits',
		 handler:function() {
				var selectLength = createPanel_SJ.getSelectionModel().getSelections().length;
				
				if (selectLength != 1) {
					Ext.Msg.alert('提示', '请选择一条记录！');
					return false;
				} 
				
			    record = createPanel_SJ.getSelectionModel().getSelections()[0];
			    showCustomerViewByTitle("修改商机_新增面板");
			    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
				getCurrentView().contentPanel.getForm().findField('CALL_ID').setValue(record.data.CALL_ID);
				getCurrentView().contentPanel.getForm().findField('PRODUCT_ID').setValue(record.data.PRODUCT_ID);
				getCurrentView().contentPanel.getForm().findField('BUSI_NAME').setValue(record.data.BUSI_NAME);
//				getCurrentView().contentPanel.getForm().findField('PRODUCT_NAME').setValue(record.data.PRODUCT_NAME);
				getCurrentView().contentPanel.getForm().findField('SALES_STAGE').setValue(record.data.SALES_STAGE);
				if(record.data.SALES_STAGE != '6'){
					getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
					getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
					getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(false);
				}else{
					getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(true);
					getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
					getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setValue(record.data.FAIL_REASON);
					getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(true);
					getCurrentView().contentPanel.getForm().findField('REMARK').setValue(record.data.REMARK);
				}
				getCurrentView().contentPanel.getForm().findField('AMOUNT').setValue(record.data.AMOUNT);
				getCurrentView().contentPanel.getForm().findField('ESTIMATED_TIME').setValue(record.data.ESTIMATED_TIME);
			}
    },{
    	text:'删除',
    	id:'addSJ_Del',
    	handler :function(){
    		//删除
    		var selectLength = createPanel_SJ.getSelectionModel().getSelections().length;
    	 	var selectRecords = createPanel_SJ.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    			tempStatus = selectRecord.data.CALL_ID;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFSeCallreportBusi!batchDestroy.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.alert('提示',"操作成功!");
    					createStore_sj.reload();
//    					createStore_sj.load({params:{callId:tempStatus}});
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      },{
        	text : '下一步',
      		id:'addSJ_Next',
      		handler:function(){
      			var selectLength = createPanel_SJ.getSelectionModel().getSelections().length;
      			record = createPanel_SJ.getSelectionModel().getSelections()[0];
      			if (selectLength != 1) {
      				Ext.Msg.alert('提示', '请选择一条记录！');
      				return false;
      			}
      			
      		    //点击下一步修改商机阶段之前，判断该选定商机的商机阶段是不是最新的，如不是，弹框提示“该商机不是最新阶段，请选择该商机的最新阶段进行下一步操作”
      			var callid = createPanel_SJ.getSelectionModel().getSelections()[0].get("CALL_ID");
      			var businame = createPanel_SJ.getSelectionModel().getSelections()[0].get("BUSI_NAME");
      			var salesstage = createPanel_SJ.getSelectionModel().getSelections()[0].get("SALES_STAGE");
    		    Ext.Ajax.request({
                    url: basepath+'/ocrmFSeCallreportBusi!checkBusiStage.json',                                
                    mthod : 'POST',
                    params : {
                        'callId': callid,
    		            'busiName': businame,
    		            'salesStage' : salesstage
                    }, 
                    success : function(){
              		    showCustomerViewByTitle("新增商机_下一步");
              			getCurrentView().contentPanel.getForm().findField('CALL_ID').setValue(record.data.CALL_ID);
              			getCurrentView().contentPanel.getForm().findField('PRODUCT_ID').setValue(record.data.PRODUCT_ID);
              			getCurrentView().contentPanel.getForm().findField('BUSI_NAME').setValue(record.data.BUSI_NAME);
              			getCurrentView().contentPanel.getForm().findField('SALES_STAGE').setValue(record.data.SALES_STAGE);
              			if(record.data.SALES_STAGE != '6'){
              				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
            				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
              				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(false);
              			}else{
              				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(true);
            				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
              				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setValue(record.data.FAIL_REASON);
              				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(true);
              				getCurrentView().contentPanel.getForm().findField('REMARK').setValue(record.data.REMARK);
              			}
              			getCurrentView().contentPanel.getForm().findField('AMOUNT').setValue(record.data.AMOUNT);
              			getCurrentView().contentPanel.getForm().findField('ESTIMATED_TIME').setValue(record.data.ESTIMATED_TIME);
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '操作失败');
                        reloadCurrentData();
                    }
                });
      		}
          }],
	store : createStore_sj,
	frame : true,
	sm:createSm,
	cm : createCm,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var editSm = new Ext.grid.CheckboxSelectionModel();
var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 30
});
var editCm =  new Ext.grid.ColumnModel([editRrownum,editSm,	
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'CALL_ID',dataIndex:'CALL_ID',sortable:true,width:120,hidden:true},
                                     {header:'商机名称',dataIndex:'BUSI_NAME',sortable:true,width:120},
                                     {header:'产品编号',dataIndex:'PRODUCT_ID',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_PRODUCT_NAME",value);
                            			return val?val:"";
                            			}},
//                                     {header:'产品编码',dataIndex:'PRODUCT_NAME',sortable:true,width:120},
                                     {header:'销售阶段',dataIndex:'SALES_STAGE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_SAVES_STAGE",value);
                            			return val?val:"";
                            			}},
                                     {header:'金额(元)',dataIndex:'AMOUNT',sortable:true,width:120},
                                     {header:'失败原因',dataIndex:'FAIL_REASON',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_FAIL_REASON",value);
                            			return val?val:"";
                            			}},
                                     {header:'预计成交时间',dataIndex:'ESTIMATED_TIME',sortable:true,width:120},
                                     {header:'备注',dataIndex:'REMARK',sortable:true,width:120}
        	                                     ]); 
var editStore_sj = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFSeCallreportBusi.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'CALL_ID'},
			    {name:'PRODUCT_ID'},
//			    {name:'PRODUCT_NAME'},
			    {name:'SALES_STAGE'},
			    {name:'AMOUNT'},
			    {name:'BUSI_NAME'},
			    {name:'FAIL_REASON'},
			    {name:'ESTIMATED_TIME'},
			    {name:'LAST_UPDATE_USER'},
			    {name:'LAST_UPDATE_TM'},
			    {name:'REMARK'}
			     ])
});
var editPanel_SJ = new Ext.grid.GridPanel({
	title : '访谈结果(商机)',
	autoScroll: true,
	height:200,
    tbar : [{
    	text : '新增',
		id:'addSJ_edit',
		 handler:function() {
			showCustomerViewByTitle('新增商机_修改');
		}
    },{
    	text : '修改',
		id:'modifySJ',
		handler:function(){
			var selectLength = editPanel_SJ.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
			
		    record = editPanel_SJ.getSelectionModel().getSelections()[0];
		    showCustomerViewByTitle("修改商机");
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('CALL_ID').setValue(record.data.CALL_ID);
			getCurrentView().contentPanel.getForm().findField('PRODUCT_ID').setValue(record.data.PRODUCT_ID);
			getCurrentView().contentPanel.getForm().findField('BUSI_NAME').setValue(record.data.BUSI_NAME);
//			getCurrentView().contentPanel.getForm().findField('PRODUCT_NAME').setValue(record.data.PRODUCT_NAME);
			getCurrentView().contentPanel.getForm().findField('SALES_STAGE').setValue(record.data.SALES_STAGE);
			if(record.data.SALES_STAGE != '6'){
				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(false);
			}else{
				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(true);
				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setValue(record.data.FAIL_REASON);
				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(true);
				getCurrentView().contentPanel.getForm().findField('REMARK').setValue(record.data.REMARK);
			}
			getCurrentView().contentPanel.getForm().findField('AMOUNT').setValue(record.data.AMOUNT);
			getCurrentView().contentPanel.getForm().findField('ESTIMATED_TIME').setValue(record.data.ESTIMATED_TIME);
		}
    },{
    	text:'删除',
    	id:'deleteSJ',
    	handler :function(){
    		//删除
    		var selectLength = editPanel_SJ.getSelectionModel().getSelections().length;
    	 	var selectRecords = editPanel_SJ.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    			tempStatus = selectRecord.data.CALL_ID;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFSeCallreportBusi!batchDestroy.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.alert('提示',"操作成功!");
    					editStore_sj.reload();
    					createStore_sj.load({params:{callId:tempStatus}});
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      },{
      	text : '下一步',
  		id:'nextSJ',
  		handler:function(){
  			var selectLength = editPanel_SJ.getSelectionModel().getSelections().length;
  			record = editPanel_SJ.getSelectionModel().getSelections()[0];
  			if (selectLength != 1) {
  				Ext.Msg.alert('提示', '请选择一条记录！');
  				return false;
  			}
  			
  		    //点击下一步修改商机阶段之前，判断该选定商机的商机阶段是不是最新的，如不是，弹框提示“该商机不是最新阶段，请选择该商机的最新阶段进行下一步操作”
  			var callid = editPanel_SJ.getSelectionModel().getSelections()[0].get("CALL_ID");
  			var businame = editPanel_SJ.getSelectionModel().getSelections()[0].get("BUSI_NAME");
  			var salesstage = editPanel_SJ.getSelectionModel().getSelections()[0].get("SALES_STAGE");
		    Ext.Ajax.request({
                url: basepath+'/ocrmFSeCallreportBusi!checkBusiStage.json',                                
                mthod : 'POST',
                params : {
                    'callId': callid,
		            'busiName': businame,
		            'salesStage' : salesstage
                }, 
                success : function(){
          		    showCustomerViewByTitle("修改商机_下一步");
//          		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
          			getCurrentView().contentPanel.getForm().findField('CALL_ID').setValue(record.data.CALL_ID);
          			getCurrentView().contentPanel.getForm().findField('PRODUCT_ID').setValue(record.data.PRODUCT_ID);
          			getCurrentView().contentPanel.getForm().findField('BUSI_NAME').setValue(record.data.BUSI_NAME);
//          			getCurrentView().contentPanel.getForm().findField('PRODUCT_NAME').setValue(record.data.PRODUCT_NAME);
          			getCurrentView().contentPanel.getForm().findField('SALES_STAGE').setValue(record.data.SALES_STAGE);
          			if(record.data.SALES_STAGE != '6'){
          				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
        				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
          				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(false);
          			}else{
          				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(true);
        				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
          				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setValue(record.data.FAIL_REASON);
          				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(true);
          				getCurrentView().contentPanel.getForm().findField('REMARK').setValue(record.data.REMARK);
          			}
          			getCurrentView().contentPanel.getForm().findField('AMOUNT').setValue(record.data.AMOUNT);
          			getCurrentView().contentPanel.getForm().findField('ESTIMATED_TIME').setValue(record.data.ESTIMATED_TIME);
                },
                failure : function(){
                    Ext.Msg.alert('提示', '操作失败');
                    reloadCurrentData();
                }
            });
  		}
      }],
	store : editStore_sj,
	frame : true,
	sm:editSm,
	cm : editCm,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var detailRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 30
});
var detailCm =  new Ext.grid.ColumnModel([detailRrownum,	
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'CALL_ID',dataIndex:'CALL_ID',sortable:true,width:120,hidden:true},
                                     {header:'商机名称',dataIndex:'BUSI_NAME',sortable:true,width:120},
                                     {header:'产品编号',dataIndex:'PRODUCT_ID',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_PRODUCT_NAME",value);
                            			return val?val:"";
                            			}},
//                                     {header:'产品编码',dataIndex:'PRODUCT_NAME',sortable:true,width:120},
                                     {header:'销售阶段',dataIndex:'SALES_STAGE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_SAVES_STAGE",value);
                            			return val?val:"";
                            			}},
                                     {header:'金额(元)',dataIndex:'AMOUNT',sortable:true,width:120},
                                     {header:'失败原因',dataIndex:'FAIL_REASON',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_FAIL_REASON",value);
                            			return val?val:"";
                            			}},
                                     {header:'预计成交时间',dataIndex:'ESTIMATED_TIME',sortable:true,width:120},
                                     {header:'备注',dataIndex:'REMARK',sortable:true,width:120}
        	                                     ]); 
var detailPanel_SJ = new Ext.grid.GridPanel({
	title : '访谈结果(商机)',
	autoScroll: true,
	height:200,
    tbar : [],
	store : createStore_sj,
	frame : true,
	cm : detailCm,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
var detailRrownum2 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 30
});
var detailCm2 =  new Ext.grid.ColumnModel([detailRrownum2,	
                                   {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                   {header:'CALL_ID',dataIndex:'CALL_ID',sortable:true,width:120,hidden:true},
                                   {header:'商机名称',dataIndex:'BUSI_NAME',sortable:true,width:120},
                                   {header:'产品编号',dataIndex:'PRODUCT_ID',sortable:true,width:120,renderer:function(value){
                           			var val = translateLookupByKey("CALLREPORT_PRODUCT_NAME",value);
                          			return val?val:"";
                          			}},
//                                   {header:'产品编码',dataIndex:'PRODUCT_NAME',sortable:true,width:120},
                                   {header:'销售阶段',dataIndex:'SALES_STAGE',sortable:true,width:120,renderer:function(value){
                           			var val = translateLookupByKey("CALLREPORT_SAVES_STAGE",value);
                          			return val?val:"";
                          			}},
                                   {header:'金额(元)',dataIndex:'AMOUNT',sortable:true,width:120},
                                   {header:'失败原因',dataIndex:'FAIL_REASON',sortable:true,width:120,renderer:function(value){
                           			var val = translateLookupByKey("CALLREPORT_FAIL_REASON",value);
                          			return val?val:"";
                          			}},
                                   {header:'预计成交时间',dataIndex:'ESTIMATED_TIME',sortable:true,width:120},
                                   {header:'备注',dataIndex:'REMARK',sortable:true,width:120}
      	                                     ]); 
var detailPanel_SJ2 = new Ext.grid.GridPanel({
	title : '访谈结果(商机)',
	autoScroll: true,
	height:200,
  tbar : [],
	store : createStore_sj,
	frame : true,
	cm : detailCm2,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//===================新增到期提醒=====================
var createSm_dq = new Ext.grid.CheckboxSelectionModel();
var createRrownum_dq = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 30
});
var createCm_dq =  new Ext.grid.ColumnModel([createRrownum_dq,createSm_dq,	
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'CALL_ID',dataIndex:'CALL_ID',sortable:true,width:120,hidden:true},
                                     {header:'CUST_ID',dataIndex:'CUST_ID',sortable:true,width:120,hidden:true},
                                     {header:'产品编号',dataIndex:'PRODUCT_ID',sortable:true,width:120},
                                     {header:'产品编码',dataIndex:'PRODUCT_NAME',sortable:true,width:120},
                                     {header:'金额(元)',dataIndex:'AMOUNT',sortable:true,width:120},
                                     {header:'到期日',dataIndex:'END_DATE1',sortable:true,width:120},
                                     
                                     {header:'是否续作',dataIndex:'SEQUEL_STAGE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_SEQUEL_STAGE",value);
                            			return val?val:"";
                            			}},
                            			
                        			 {header:'续增金额(元)',dataIndex:'SEQUEL_AMOUNT',sortable:true,width:120},
                                     {header:'出账金额(元)',dataIndex:'OUT_AMOUNT',sortable:true,width:120},
                                     
                                     {header:'出账原因',dataIndex:'FAIL_REASON',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_DQ_REASON",value);
                            			return val?val:"";
                            			}},
                            			
                                     {header:'备注',dataIndex:'REMARK',sortable:true,width:120},
                            		 {header:'是否结案',dataIndex:'CASE_STAGE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_CASE_STAGE",value);
                            			return val?val:"";
                            		  }}
        	                                     ]); 
var createStore_dq = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFSeCallreportN.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'CALL_ID'},
			    {name:'CUST_ID'},
			    {name:'PRODUCT_ID'},
			    {name:'PRODUCT_NAME'},
			    {name:'SEQUEL_STAGE'},
			    {name:'SEQUEL_AMOUNT'},
			    {name:'OUT_AMOUNT'},
			    {name:'AMOUNT'},
			    {name:'FAIL_REASON'},
			    {name:'CASE_STAGE'},
			    {name:'REMARK'},
			    {name:'END_DATE1'},
			    {name:'LAST_UPDATE_USER'},
			    {name:'LAST_UPDATE_TM'}
			     ])
});

var createPanel_DQ = new Ext.grid.GridPanel({
	title : '访谈结果(到期提醒)',
	autoScroll: true,
	height:200,
    tbar : [{
    	text : '修改',
		id:'addDQ_up',
		handler:function(){
			var selectLength = createPanel_DQ.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
			
		    record = createPanel_DQ.getSelectionModel().getSelections()[0];
		    showCustomerViewByTitle("修改到期提醒_新增");
		    
		    var t = record.data.SEQUEL_STAGE;
			if(t == '2'||t == '3'){
				getCurrentView().contentPanel.getForm().findField('OUT_AMOUNT').allowBlank = false;
				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
			}else{
				getCurrentView().contentPanel.getForm().findField('OUT_AMOUNT').allowBlank = true;
				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
			}
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('CALL_ID').setValue(record.data.CALL_ID);
			if(cID.CUST_ID != undefined){
				var custId = cID.CUST_ID;
				getCurrentView().contentPanel.getForm().findField('CUST_ID').setValue(custId);
			}
//			getCurrentView().contentPanel.getForm().findField('CUST_ID').setValue(record.data.CUST_ID);
			getCurrentView().contentPanel.getForm().findField('PRODUCT_ID').setValue(record.data.PRODUCT_ID);
			getCurrentView().contentPanel.getForm().findField('PRODUCT_NAME').setValue(record.data.PRODUCT_NAME);
			getCurrentView().contentPanel.getForm().findField('SEQUEL_STAGE').setValue(record.data.SEQUEL_STAGE);
			getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setValue(record.data.FAIL_REASON);
			getCurrentView().contentPanel.getForm().findField('SEQUEL_AMOUNT').setValue(record.data.SEQUEL_AMOUNT);
			getCurrentView().contentPanel.getForm().findField('OUT_AMOUNT').setValue(record.data.OUT_AMOUNT);
			getCurrentView().contentPanel.getForm().findField('AMOUNT').setValue(record.data.AMOUNT);
			getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setValue(record.data.FAIL_REASON);
			getCurrentView().contentPanel.getForm().findField('CASE_STAGE').setValue(record.data.CASE_STAGE);
			getCurrentView().contentPanel.getForm().findField('REMARK').setValue(record.data.REMARK);
			getCurrentView().contentPanel.getForm().findField('END_DATE1').setValue(record.data.END_DATE1);
		}
    },{
    	text:'删除',
    	id:'addDQ_de',
    	handler :function(){
    		//删除
    		var selectLength = createPanel_DQ.getSelectionModel().getSelections().length;
    	 	var selectRecords = createPanel_DQ.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFSeCallreportN!batchDestroy.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.alert('提示',"操作成功!");
    					createStore_dq.reload();
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
	store : createStore_dq,
	frame : true,
	sm:createSm_dq,
	cm : createCm_dq,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var editSm_dq = new Ext.grid.CheckboxSelectionModel();
var editRrownum_dq = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 30
});
var editCm_dq =  new Ext.grid.ColumnModel([editRrownum_dq,editSm_dq,	
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'CALL_ID',dataIndex:'CALL_ID',sortable:true,width:120,hidden:true},
                                     {header:'CUST_ID',dataIndex:'CUST_ID',sortable:true,width:120,hidden:true},
                                     {header:'产品编号',dataIndex:'PRODUCT_ID',sortable:true,width:120},
                                     {header:'产品编码',dataIndex:'PRODUCT_NAME',sortable:true,width:120},
                                     {header:'金额(元)',dataIndex:'AMOUNT',sortable:true,width:120},
                                     {header:'到期日',dataIndex:'END_DATE1',sortable:true,width:120},
                                     
                                     {header:'是否续作',dataIndex:'SEQUEL_STAGE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_SEQUEL_STAGE",value);
                            			return val?val:"";
                            			}},
                            			
                        			 {header:'续增金额(元)',dataIndex:'SEQUEL_AMOUNT',sortable:true,width:120},
                                     {header:'出账金额(元)',dataIndex:'OUT_AMOUNT',sortable:true,width:120},
                                     
                                     {header:'出账原因',dataIndex:'FAIL_REASON',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_DQ_REASON",value);
                            			return val?val:"";
                            			}},
                            			
                                     {header:'备注',dataIndex:'REMARK',sortable:true,width:120},
                            		 {header:'是否结案',dataIndex:'CASE_STAGE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_CASE_STAGE",value);
                            			return val?val:"";
                            		  }}
        	                                     ]); 
var editStore_dq = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFSeCallreportN.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'CALL_ID'},
			    {name:'CUST_ID'},
			    {name:'PRODUCT_ID'},
			    {name:'PRODUCT_NAME'},
			    {name:'SEQUEL_STAGE'},
			    {name:'SEQUEL_AMOUNT'},
			    {name:'OUT_AMOUNT'},
			    {name:'AMOUNT'},
			    {name:'FAIL_REASON'},
			    {name:'CASE_STAGE'},
			    {name:'REMARK'},
			    {name:'END_DATE1'},
			    {name:'LAST_UPDATE_USER'},
			    {name:'LAST_UPDATE_TM'}
			     ])
});
var editPanel_DQ = new Ext.grid.GridPanel({
	title : '访谈结果(到期提醒)',
	autoScroll: true,
	height:200,
    tbar : [{
    	text : '修改',
		id:'modifyDQ',
		handler:function(){
			var selectLength = editPanel_DQ.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
			
		    record = editPanel_DQ.getSelectionModel().getSelections()[0];
		    showCustomerViewByTitle("修改到期提醒");
		    
			var t = record.data.SEQUEL_STAGE;
			if(t == '2'||t == '3'){
				getCurrentView().contentPanel.getForm().findField('OUT_AMOUNT').allowBlank = false;
				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
			}else{
				getCurrentView().contentPanel.getForm().findField('OUT_AMOUNT').allowBlank = true;
				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
			}
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('CALL_ID').setValue(record.data.CALL_ID);
//			getCurrentView().contentPanel.getForm().findField('CUST_ID').setValue(record.data.CUST_ID);
			getCurrentView().contentPanel.getForm().findField('PRODUCT_ID').setValue(record.data.PRODUCT_ID);
			getCurrentView().contentPanel.getForm().findField('PRODUCT_NAME').setValue(record.data.PRODUCT_NAME);
			getCurrentView().contentPanel.getForm().findField('SEQUEL_STAGE').setValue(record.data.SEQUEL_STAGE);
			getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setValue(record.data.FAIL_REASON);
			getCurrentView().contentPanel.getForm().findField('SEQUEL_AMOUNT').setValue(record.data.SEQUEL_AMOUNT);
			getCurrentView().contentPanel.getForm().findField('OUT_AMOUNT').setValue(record.data.OUT_AMOUNT);
			getCurrentView().contentPanel.getForm().findField('AMOUNT').setValue(record.data.AMOUNT);
			getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setValue(record.data.FAIL_REASON);
			getCurrentView().contentPanel.getForm().findField('CASE_STAGE').setValue(record.data.CASE_STAGE);
			getCurrentView().contentPanel.getForm().findField('REMARK').setValue(record.data.REMARK);
			getCurrentView().contentPanel.getForm().findField('END_DATE1').setValue(record.data.END_DATE1);
		}
    },{
    	text:'删除',
    	id:'deleteDQ',
    	handler :function(){
    		//删除
    		var selectLength = editPanel_DQ.getSelectionModel().getSelections().length;
    	 	var selectRecords = editPanel_DQ.getSelectionModel().getSelections();
    	 	
    		if(selectLength < 1){
     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
    			return false;
    		}
    		var tempIdStr = '';
    		var tempStatus = '';
    		for(var i=0; i < selectLength; i++){
    			var selectRecord = selectRecords[i];
    			//临时变量，保存要删除的ID
    			tempIdStr +=  selectRecord.data.ID;
    		 		if( i != selectLength - 1){
    		   		 	tempIdStr += ',';
    					}
    		 }
    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
    		if(buttonId.toLowerCase() == 'no'){
        		return false;
    		}
    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
    			Ext.Ajax.request({
    				url : basepath + '/ocrmFSeCallreportN!batchDestroy.json?idStr='+ tempIdStr,
    				method : 'GET',
    				success : function() {
    					Ext.Msg.alert('提示',"操作成功!");
    					editStore_dq.reload();
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
    	   });
    	}
      }],
	store : editStore_dq,
	frame : true,
	sm:editSm_dq,
	cm : editCm_dq,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var detailRrownum_dq = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 30
});
var detailCm_dq =  new Ext.grid.ColumnModel([detailRrownum_dq,	
                                   {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                   {header:'CALL_ID',dataIndex:'CALL_ID',sortable:true,width:120,hidden:true},
                                   {header:'CUST_ID',dataIndex:'CUST_ID',sortable:true,width:120,hidden:true},
                                   {header:'产品编号',dataIndex:'PRODUCT_ID',sortable:true,width:120},
                                   {header:'产品编码',dataIndex:'PRODUCT_NAME',sortable:true,width:120},
                                   {header:'金额(元)',dataIndex:'AMOUNT',sortable:true,width:120},
                                   {header:'到期日',dataIndex:'END_DATE1',sortable:true,width:120},
                                   
                                   {header:'是否续作',dataIndex:'SEQUEL_STAGE',sortable:true,width:120,renderer:function(value){
                           			var val = translateLookupByKey("CALLREPORT_SEQUEL_STAGE",value);
                          			return val?val:"";
                          			}},
                          			
                      			 {header:'续增金额(元)',dataIndex:'SEQUEL_AMOUNT',sortable:true,width:120},
                                   {header:'出账金额(元)',dataIndex:'OUT_AMOUNT',sortable:true,width:120},
                                   
                                   {header:'出账原因',dataIndex:'FAIL_REASON',sortable:true,width:120,renderer:function(value){
                           			var val = translateLookupByKey("CALLREPORT_DQ_REASON",value);
                          			return val?val:"";
                          			}},
                          			
                                   {header:'备注',dataIndex:'REMARK',sortable:true,width:120},
                          		 {header:'是否结案',dataIndex:'CASE_STAGE',sortable:true,width:120,renderer:function(value){
                           			var val = translateLookupByKey("CALLREPORT_CASE_STAGE",value);
                          			return val?val:"";
                          		  }}
      	                                     ]);
var detailPanel_DQ = new Ext.grid.GridPanel({
	title : '访谈结果(到期提醒)',
	autoScroll: true,
	height:200,
    tbar : [],
	store : createStore_dq,
	frame : true,
	cm : detailCm_dq,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
var detailRrownum_dq2 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 30
});
var detailCm_dq2 =  new Ext.grid.ColumnModel([detailRrownum_dq2,	
                                 {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                 {header:'CALL_ID',dataIndex:'CALL_ID',sortable:true,width:120,hidden:true},
                                 {header:'CUST_ID',dataIndex:'CUST_ID',sortable:true,width:120,hidden:true},
                                 {header:'产品编号',dataIndex:'PRODUCT_ID',sortable:true,width:120},
                                 {header:'产品编码',dataIndex:'PRODUCT_NAME',sortable:true,width:120},
                                 {header:'金额(元)',dataIndex:'AMOUNT',sortable:true,width:120},
                                 {header:'到期日',dataIndex:'END_DATE1',sortable:true,width:120},
                                 
                                 {header:'是否续作',dataIndex:'SEQUEL_STAGE',sortable:true,width:120,renderer:function(value){
                         			var val = translateLookupByKey("CALLREPORT_SEQUEL_STAGE",value);
                        			return val?val:"";
                        			}},
                        			
                    			 {header:'续增金额(元)',dataIndex:'SEQUEL_AMOUNT',sortable:true,width:120},
                                 {header:'出账金额(元)',dataIndex:'OUT_AMOUNT',sortable:true,width:120},
                                 
                                 {header:'出账原因',dataIndex:'FAIL_REASON',sortable:true,width:120,renderer:function(value){
                         			var val = translateLookupByKey("CALLREPORT_DQ_REASON",value);
                        			return val?val:"";
                        			}},
                        			
                                 {header:'备注',dataIndex:'REMARK',sortable:true,width:120},
                        		 {header:'是否结案',dataIndex:'CASE_STAGE',sortable:true,width:120,renderer:function(value){
                         			var val = translateLookupByKey("CALLREPORT_CASE_STAGE",value);
                        			return val?val:"";
                        		  }}
    	                                     ]);
var detailPanel_DQ2 = new Ext.grid.GridPanel({
	title : '访谈结果(到期提醒)',
	autoScroll: true,
	height:200,
  tbar : [],
	store : createStore_dq,
	frame : true,
	cm : detailCm_dq2,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//===================大额流失=====================
var createSm_ls = new Ext.grid.CheckboxSelectionModel();
var createRrownum_ls = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 30
});
var createCm_ls =  new Ext.grid.ColumnModel([createRrownum_dq,createSm_ls,	
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'CALL_ID',dataIndex:'CALL_ID',sortable:true,width:120,hidden:true},
                                     {header:'CUST_ID',dataIndex:'CUST_ID',sortable:true,width:120,hidden:true},
                                     {header:'汇款时间',dataIndex:'REMIND_DATE',sortable:true,width:120},
                                     {header:'汇款金额(元)',dataIndex:'REMIND_AMOUNT',sortable:true,width:120},
                                     {header:'收款人',dataIndex:'RECEIVER',sortable:true,width:120},
                                     {header:'收款行',dataIndex:'RECEIVE_BANK',sortable:true,width:120},
                                     
                                     {header:'汇款原因',dataIndex:'REMIND_REASON',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_REMIND_REASON",value);
                            			return val?val:"";
                            			}},
                            			
                                     {header:'备注',dataIndex:'REMIND_REMARK',sortable:true,width:120},
                                     {header:'预计回流时间',dataIndex:'BACKFLOW_DATE',sortable:true,width:120},
                                     {header:'预计回流金额(元)',dataIndex:'BACKFLOW_AMOUNT',sortable:true,width:120},
                            		 {header:'是否结案',dataIndex:'REMIND_CASE_STAGE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_CASE_STAGE",value);
                            			return val?val:"";
                            		  }}
        	                                     ]); 
var createStore_ls = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFSeCallreportRemind.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'CALL_ID'},
			    {name:'CUST_ID'},
			    {name:'REMIND_DATE'},
			    {name:'REMIND_AMOUNT'},
			    {name:'RECEIVER'},
			    {name:'RECEIVE_BANK'},
			    {name:'REMIND_REASON'},
			    {name:'REMIND_REMARK'},
			    {name:'BACKFLOW_DATE'},
			    {name:'BACKFLOW_AMOUNT'},
			    {name:'REMIND_CASE_STAGE'},
			    {name:'LAST_UPDATE_USER'},
			    {name:'LAST_UPDATE_TM'}
			     ])
});

var createPanel_LS = new Ext.grid.GridPanel({
	title : '访谈结果(大额流失)',
	autoScroll: true,
	height:200,
	 tbar : [{
	    	text : '修改',
			id:'addLS_Edits',
			 handler:function() {
					var selectLength = createPanel_LS.getSelectionModel().getSelections().length;
					
					if (selectLength != 1) {
						Ext.Msg.alert('提示', '请选择一条记录！');
						return false;
					} 
					
				    record = createPanel_LS.getSelectionModel().getSelections()[0];
				    showCustomerViewByTitle("新增大额流失");
				    
				    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
					getCurrentView().contentPanel.getForm().findField('CALL_ID').setValue(record.data.CALL_ID);
					getCurrentView().contentPanel.getForm().findField('CUST_ID').setValue(record.data.CUST_ID);
					getCurrentView().contentPanel.getForm().findField('REMIND_DATE').setValue(record.data.REMIND_DATE);
					getCurrentView().contentPanel.getForm().findField('REMIND_AMOUNT').setValue(record.data.REMIND_AMOUNT);
					getCurrentView().contentPanel.getForm().findField('RECEIVER').setValue(record.data.RECEIVER);
					getCurrentView().contentPanel.getForm().findField('RECEIVE_BANK').setValue(record.data.RECEIVE_BANK);
					getCurrentView().contentPanel.getForm().findField('REMIND_REASON').setValue(record.data.REMIND_REASON);
					getCurrentView().contentPanel.getForm().findField('REMIND_REMARK').setValue(record.data.REMIND_REMARK);
					getCurrentView().contentPanel.getForm().findField('BACKFLOW_DATE').setValue(record.data.BACKFLOW_DATE);
					getCurrentView().contentPanel.getForm().findField('BACKFLOW_AMOUNT').setValue(record.data.BACKFLOW_AMOUNT);
					getCurrentView().contentPanel.getForm().findField('REMIND_CASE_STAGE').setValue(record.data.REMIND_CASE_STAGE);
				}
	    }],
	store : createStore_ls,
	frame : true,
	sm:createSm_ls,
	cm : createCm_ls,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var editSm_ls = new Ext.grid.CheckboxSelectionModel();
var editRrownum_ls = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 30
});
var editCm_ls =  new Ext.grid.ColumnModel([editRrownum_dq,editSm_ls,	
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'CALL_ID',dataIndex:'CALL_ID',sortable:true,width:120,hidden:true},
                                     {header:'CUST_ID',dataIndex:'CUST_ID',sortable:true,width:120,hidden:true},
                                     {header:'汇款时间',dataIndex:'REMIND_DATE',sortable:true,width:120},
                                     {header:'汇款金额(元)',dataIndex:'REMIND_AMOUNT',sortable:true,width:120},
                                     {header:'收款人',dataIndex:'RECEIVER',sortable:true,width:120},
                                     {header:'收款行',dataIndex:'RECEIVE_BANK',sortable:true,width:120},
                                     
                                     {header:'汇款原因',dataIndex:'REMIND_REASON',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_REMIND_REASON",value);
                            			return val?val:"";
                            			}},
                            			
                                     {header:'备注',dataIndex:'REMIND_REMARK',sortable:true,width:120},
                                     {header:'预计回流时间',dataIndex:'BACKFLOW_DATE',sortable:true,width:120},
                                     {header:'预计回流金额(元)',dataIndex:'BACKFLOW_AMOUNT',sortable:true,width:120},
                            		 {header:'是否结案',dataIndex:'REMIND_CASE_STAGE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_CASE_STAGE",value);
                            			return val?val:"";
                            		  }}
        	                                     ]); 
var editStore_ls = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFSeCallreportRemind.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'ID'},
			    {name:'CALL_ID'},
			    {name:'CUST_ID'},
			    {name:'REMIND_DATE'},
			    {name:'REMIND_AMOUNT'},
			    {name:'RECEIVER'},
			    {name:'RECEIVE_BANK'},
			    {name:'REMIND_REASON'},
			    {name:'REMIND_REMARK'},
			    {name:'BACKFLOW_DATE'},
			    {name:'BACKFLOW_AMOUNT'},
			    {name:'REMIND_CASE_STAGE'},
			    {name:'LAST_UPDATE_USER'},
			    {name:'LAST_UPDATE_TM'}
			     ])
});
var editPanel_LS = new Ext.grid.GridPanel({
	title : '访谈结果(大额流失)',
	autoScroll: true,
	height:200,
    tbar : [
            {
//    	text : '新增',
//		id:'addLS_edit',
//		 handler:function() {
////			showCustomerViewByTitle('新增大额流失_修改');
//		}
//    },
//    {
    	text : '修改',
		id:'modifyLS',
		handler:function(){
			var selectLength = editPanel_LS.getSelectionModel().getSelections().length;
			
			if (selectLength != 1) {
				Ext.Msg.alert('提示', '请选择一条记录！');
				return false;
			} 
			
		    record = editPanel_LS.getSelectionModel().getSelections()[0];
		    showCustomerViewByTitle("修改大额流失");
		    
		    getCurrentView().contentPanel.getForm().findField('ID').setValue(record.data.ID);
			getCurrentView().contentPanel.getForm().findField('CALL_ID').setValue(record.data.CALL_ID);
//			getCurrentView().contentPanel.getForm().findField('CUST_ID').setValue(record.data.CUST_ID);
			getCurrentView().contentPanel.getForm().findField('REMIND_DATE').setValue(record.data.REMIND_DATE);
			getCurrentView().contentPanel.getForm().findField('REMIND_AMOUNT').setValue(record.data.REMIND_AMOUNT);
			getCurrentView().contentPanel.getForm().findField('RECEIVER').setValue(record.data.RECEIVER);
			getCurrentView().contentPanel.getForm().findField('RECEIVE_BANK').setValue(record.data.RECEIVE_BANK);
			getCurrentView().contentPanel.getForm().findField('REMIND_REASON').setValue(record.data.REMIND_REASON);
			getCurrentView().contentPanel.getForm().findField('REMIND_REMARK').setValue(record.data.REMIND_REMARK);
			getCurrentView().contentPanel.getForm().findField('BACKFLOW_DATE').setValue(record.data.BACKFLOW_DATE);
			getCurrentView().contentPanel.getForm().findField('BACKFLOW_AMOUNT').setValue(record.data.BACKFLOW_AMOUNT);
			getCurrentView().contentPanel.getForm().findField('REMIND_CASE_STAGE').setValue(record.data.REMIND_CASE_STAGE);
		}
//    }
//    ,
//    {
//    	text:'删除',
//    	id:'deleteLS',
//    	handler :function(){
//    		//删除
//    		var selectLength = editPanel_LS.getSelectionModel().getSelections().length;
//    	 	var selectRecords = editPanel_LS.getSelectionModel().getSelections();
//    	 	
//    		if(selectLength < 1){
//     			Ext.Msg.alert('提示','请选择一条数据进行操作!');
//    			return false;
//    		}
//    		var tempIdStr = '';
//    		var tempStatus = '';
//    		for(var i=0; i < selectLength; i++){
//    			var selectRecord = selectRecords[i];
//    			//临时变量，保存要删除的ID
//    			tempIdStr +=  selectRecord.data.ID;
//    		 		if( i != selectLength - 1){
//    		   		 	tempIdStr += ',';
//    					}
//    		 }
//    		Ext.MessageBox.confirm('提示','你确定删除吗!',function(buttonId){
//    		if(buttonId.toLowerCase() == 'no'){
//        		return false;
//    		}
//    		Ext.Msg.wait('正在提交数据，请稍等...','提示');
//    			Ext.Ajax.request({
//    				url : basepath + '/ocrmFSeCallreportRemind!batchDestroy.json?idStr='+ tempIdStr,
//    				method : 'GET',
//    				success : function() {
//    					Ext.Msg.alert('提示',"操作成功!");
//    					editStore_ls.reload();
//    				},
//    				failure : function(response) {
//    					var resultArray = Ext.util.JSON.decode(response.status);
//    			 		if(resultArray == 403) {
//    		           		Ext.Msg.alert('提示', response.responseText);
//    			 		}else{
//    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//    	 				}
//    				}
//    			});
//    	   });
//    	}
      }
    ],
	store : editStore_ls,
	frame : true,
	sm:editSm_ls,
	cm : editCm_ls,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var detailRrownum_ls = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 30
});
var detailCm_ls =  new Ext.grid.ColumnModel([detailRrownum_dq,	
                                     {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                     {header:'CALL_ID',dataIndex:'CALL_ID',sortable:true,width:120,hidden:true},
                                     {header:'CUST_ID',dataIndex:'CUST_ID',sortable:true,width:120,hidden:true},
                                     {header:'汇款时间',dataIndex:'REMIND_DATE',sortable:true,width:120},
                                     {header:'汇款金额(元)',dataIndex:'REMIND_AMOUNT',sortable:true,width:120},
                                     {header:'收款人',dataIndex:'RECEIVER',sortable:true,width:120},
                                     {header:'收款行',dataIndex:'RECEIVE_BANK',sortable:true,width:120},
                                     
                                     {header:'汇款原因',dataIndex:'REMIND_REASON',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_REMIND_REASON",value);
                            			return val?val:"";
                            			}},
                            			
                                     {header:'备注',dataIndex:'REMIND_REMARK',sortable:true,width:120},
                                     {header:'预计回流时间',dataIndex:'BACKFLOW_DATE',sortable:true,width:120},
                                     {header:'预计回流金额(元)',dataIndex:'BACKFLOW_AMOUNT',sortable:true,width:120},
                            		 {header:'是否结案',dataIndex:'REMIND_CASE_STAGE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_CASE_STAGE",value);
                            			return val?val:"";
                            		  }}
        	                                     ]); 
var detailPanel_LS = new Ext.grid.GridPanel({
	title : '访谈结果(大额流失)',
	autoScroll: true,
	height:200,
    tbar : [],
	store : createStore_ls,
	frame : true,
	cm : detailCm_ls,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
var detailRrownum_ls2 = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 30
});
var detailCm_ls2 =  new Ext.grid.ColumnModel([detailRrownum_dq2,	
                                   {header : 'ID', dataIndex : 'ID',sortable : true,width : 120,hidden:true},
                                   {header:'CALL_ID',dataIndex:'CALL_ID',sortable:true,width:120,hidden:true},
                                   {header:'CUST_ID',dataIndex:'CUST_ID',sortable:true,width:120,hidden:true},
                                   {header:'汇款时间',dataIndex:'REMIND_DATE',sortable:true,width:120},
                                   {header:'汇款金额(元)',dataIndex:'REMIND_AMOUNT',sortable:true,width:120},
                                   {header:'收款人',dataIndex:'RECEIVER',sortable:true,width:120},
                                   {header:'收款行',dataIndex:'RECEIVE_BANK',sortable:true,width:120},
                                   
                                   {header:'汇款原因',dataIndex:'REMIND_REASON',sortable:true,width:120,renderer:function(value){
                           			var val = translateLookupByKey("CALLREPORT_REMIND_REASON",value);
                          			return val?val:"";
                          			}},
                          			
                                   {header:'备注',dataIndex:'REMIND_REMARK',sortable:true,width:120},
                                   {header:'预计回流时间',dataIndex:'BACKFLOW_DATE',sortable:true,width:120},
                                   {header:'预计回流金额(元)',dataIndex:'BACKFLOW_AMOUNT',sortable:true,width:120},
                          		 {header:'是否结案',dataIndex:'REMIND_CASE_STAGE',sortable:true,width:120,renderer:function(value){
                           			var val = translateLookupByKey("CALLREPORT_CASE_STAGE",value);
                          			return val?val:"";
                          		  }}
      	                                     ]); 
var detailPanel_LS2 = new Ext.grid.GridPanel({
	title : '访谈结果(大额流失)',
	autoScroll: true,
	height:200,
  tbar : [],
	store : createStore_ls,
	frame : true,
	cm : detailCm_ls2,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
//====================近三次联系内容==============================
var createSm_asn = new Ext.grid.CheckboxSelectionModel();
var createCm_asn =  new Ext.grid.ColumnModel([createRrownum,createSm_asn,
                                     {header:'客户编号',dataIndex:'CUST_ID',sortable:true,width:120},
                                     {header:'客户名称',dataIndex:'CUST_NAME',sortable:true,width:120},
	                                 {header:'日期',dataIndex:'VISIT_DATE',sortable:true,width:120},
                                     {header:'开始时间',dataIndex:'BEGIN_DATE',sortable:true,width:120},
                                     {header:'结束时间',dataIndex:'END_DATE',sortable:true,width:120},
                                     {header:'电话号码',dataIndex:'LINK_PHONE',sortable:true,width:120},
                                     {header:'客户类型',dataIndex:'CUST_TYPE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_CUST_TYPE",value);
                            			return val?val:"";
                            			}},
                                     {header:'拜访方式',dataIndex:'VISIT_WAY',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_VISIT_TYPE",value);
                            			return val?val:"";
                            			}},
                                     {header:'访谈结果',dataIndex:'VISIT_CONTENT',sortable:true,width:120,renderer:function(value){
                                    	 var val = translateLookupByKey("INTERVIEW_RESULTS",value);
                             			return val?val:"";
                                     }},
                                     {header:'访谈内容',dataIndex:'MKT_BAK_NOTE',sortable:true,width:120},
                                     {header:'客户渠道',dataIndex:'CUST_CHANNEL',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_CUST_CHANNEL",value);
                            			return val?val:"";
                            			}},
                                     {header:'MGM推荐客户',dataIndex:'RECOMMEND_CUST_ID',sortable:true,width:120},
                                     {header:'下次拜访时间',dataIndex:'NEXT_VISIT_DATE',sortable:true,width:120},
                                     {header:'下次拜访方式',dataIndex:'NEXT_VISIT_WAY',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_VISIT_TYPE",value);
                            			return val?val:"";
                            			}}
//                                     {header:'访谈内容',dataIndex:'VISIT_CONTENT',sortable:true,width:120},
        	                                     ]); 

var createStore_asn = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFSeCallreport.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'CALL_ID'},
			    {name:'CUST_NAME'},
			    {name:'CUST_ID'},
			    {name:'CUST_TYPE'},
			    {name:'BEGIN_DATE'},
			    {name:'END_DATE'},
			    {name:'VISIT_DATE'},
			    {name:'LINK_PHONE'},
			    {name:'VISIT_WAY'},
			    {name:'CUST_CHANNEL'},
			    {name:'RECOMMEND_CUST_ID'},
			    {name:'VISIT_CONTENT'},
			    {name:'LAST_UPDATE_USER'},
			    {name:'NEXT_VISIT_WAY'},
			    {name:'NEXT_VISIT_DATE'},
			    {name:'LAST_UPDATE_TM'},
			    {name:'MKT_BAK_NOTE'}
			     ])
});

var createPanel_SAN = new Ext.grid.GridPanel({
	title : '联系内容概览',
	autoScroll: true,
	height:200,
	tbar : [{
    	id:'createDetail',
        text : '详情',
        handler:function() {
        	feedback_asn('联系内容详情_新增面板',createPanel_SAN);
        }
	}],
	store : createStore_asn,
	frame : true,
	sm:createSm_asn,
	cm : createCm_asn,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

var editSm_asn = new Ext.grid.CheckboxSelectionModel();
var editCm_asn =  new Ext.grid.ColumnModel([editRrownum,editSm_asn,
                                     {header:'客户编号',dataIndex:'CUST_ID',sortable:true,width:120},
                                     {header:'客户名称',dataIndex:'CUST_NAME',sortable:true,width:120},
	                                 {header:'日期',dataIndex:'VISIT_DATE',sortable:true,width:120},
                                     {header:'开始时间',dataIndex:'BEGIN_DATE',sortable:true,width:120},
                                     {header:'结束时间',dataIndex:'END_DATE',sortable:true,width:120},
                                     {header:'电话号码',dataIndex:'LINK_PHONE',sortable:true,width:120},
                                     {header:'客户类型',dataIndex:'CUST_TYPE',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_CUST_TYPE",value);
                            			return val?val:"";
                            			}},
                                     {header:'拜访方式',dataIndex:'VISIT_WAY',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_VISIT_TYPE",value);
                            			return val?val:"";
                            			}},
                        			{header:'访谈结果',dataIndex:'VISIT_CONTENT',sortable:true,width:120,renderer:function(value){
                                   	 var val = translateLookupByKey("INTERVIEW_RESULTS",value);
                            			return val?val:"";
                                    }},
                                    {header:'访谈内容',dataIndex:'MKT_BAK_NOTE',sortable:true,width:120},
                                     {header:'客户渠道',dataIndex:'CUST_CHANNEL',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_CUST_CHANNEL",value);
                            			return val?val:"";
                            			}},
                                     {header:'MGM推荐客户',dataIndex:'RECOMMEND_CUST_ID',sortable:true,width:120},
                                     {header:'下次拜访时间',dataIndex:'NEXT_VISIT_DATE',sortable:true,width:120},
                                     {header:'下次拜访方式',dataIndex:'NEXT_VISIT_WAY',sortable:true,width:120,renderer:function(value){
                             			var val = translateLookupByKey("CALLREPORT_VISIT_TYPE",value);
                            			return val?val:"";
                            			}}
//                                     {header:'访谈内容',dataIndex:'VISIT_CONTENT',sortable:true,width:120},
        	                                     ]); 

var editStore_asn = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
			{
				url : basepath + '/ocrmFSeCallreport.json'
			}),
			reader : new Ext.data.JsonReader( {
				root : 'json.data'
			}, [{name:'CALL_ID'},
			    {name:'CUST_NAME'},
			    {name:'CUST_ID'},
			    {name:'CUST_TYPE'},
			    {name:'BEGIN_DATE'},
			    {name:'END_DATE'},
			    {name:'VISIT_DATE'},
			    {name:'LINK_PHONE'},
			    {name:'VISIT_WAY'},
			    {name:'CUST_CHANNEL'},
			    {name:'RECOMMEND_CUST_ID'},
			    {name:'VISIT_CONTENT'},
			    {name:'LAST_UPDATE_USER'},
			    {name:'NEXT_VISIT_WAY'},
			    {name:'NEXT_VISIT_DATE'},
			    {name:'LAST_UPDATE_TM'},
			    {name:'MKT_BAK_NOTE'}
			     ])
});

var editPanel_SAN = new Ext.grid.GridPanel({
	title : '联系内容概览',
	autoScroll: true,
	height:200,
	tbar : [{
    	id:'editDetail',
        text : '详情',
        handler:function() {
        	feedback_asn('联系内容详情',editPanel_SAN);
        }
	}],
	store : editStore_asn,
	frame : true,
	sm:editSm_asn,
	cm : editCm_asn,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
function feedback_asn(type,grid){
	   var selectLength = grid.getSelectionModel().getSelections().length;
		if (selectLength != 1) {
			Ext.Msg.alert('提示', '请选择一条记录！');
			return false;
		} 
		
	    record = grid.getSelectionModel().getSelections()[0];
	    
	    showCustomerViewByTitle(type);
	    
		getCurrentView().contentPanel.getForm().findField('CUST_NAME').setValue(record.data.CUST_NAME);
		getCurrentView().contentPanel.getForm().findField('CUST_ID').setValue(record.data.CUST_ID);
		getCurrentView().contentPanel.getForm().findField('CUST_TYPE').setValue(record.data.CUST_TYPE);
		getCurrentView().contentPanel.getForm().findField('BEGIN_DATE').setValue(record.data.BEGIN_DATE);
		getCurrentView().contentPanel.getForm().findField('END_DATE').setValue(record.data.END_DATE);
		getCurrentView().contentPanel.getForm().findField('VISIT_DATE').setValue(record.data.VISIT_DATE);
		getCurrentView().contentPanel.getForm().findField('LINK_PHONE').setValue(record.data.LINK_PHONE);
		getCurrentView().contentPanel.getForm().findField('VISIT_WAY').setValue(record.data.VISIT_WAY);
		getCurrentView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(record.data.CUST_CHANNEL);
		if(record.data.CUST_CHANNEL=='14'){//MGM
			getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setVisible(true);
		}else{
			getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setVisible(false);
		}
		getCurrentView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue(record.data.NEXT_VISIT_DATE);
		getCurrentView().contentPanel.getForm().findField('NEXT_VISIT_WAY').setValue(record.data.NEXT_VISIT_WAY);
		getCurrentView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue(record.data.RECOMMEND_CUST_ID);
		getCurrentView().contentPanel.getForm().findField('VISIT_CONTENT').setValue(record.data.VISIT_CONTENT);
		getCurrentView().contentPanel.getForm().findField('MKT_BAK_NOTE').setValue(record.data.MKT_BAK_NOTE);
		var callIds = record.data.CALL_ID;
		var custId = record.data.CUST_ID;
		//加载商机面板
		createStore_sj.load({params:{callId:callIds}});
		//到期提醒信息
		createStore_dq.load({params:{callReport_custId:custId}});
		//大额流失
		createStore_ls.load({params:{callReport_custId:custId}});
}
/*************导入窗口定义模块start****************/
var _tempImpFileName = "";
var pkHead = "";
/**
 * 导入表单对象，此对象为全局对象，页面直接调用。
 */
var importForm = new Ext.FormPanel(
		{
			height : 200,
			width : '100%',
			title : '文件导入',
			fileUpload : true,
			dataName : 'file',
			frame : true,
			tradecode : "",

			/** 是否显示导入状态 */
			importStateInfo : '',
			importStateMsg : function(state) {
				var titleArray = [ 'excel数据转化为SQL脚本', '执行SQL脚本...',
						'正在将临时表数据导入到业务表中...', '导入成功！' ];
				this.importStateInfo = "当前状态为：[<font color='red'>"
						+ titleArray[state] + "</font>];<br>";
			},
			/** 是否显示 当前excel数据转化为SQL脚本成功记录数 */
			curRecordNumInfo : '',
			curRecordNumMsg : function(o) {
				this.curRecordNumInfo = "当前excel数据转化为SQL脚本成功记录数[<font color='red'>"
						+ o + "</font>];<br>";
			},
			/** 是否显示 当前sheet页签记录数 */
			curSheetRecordNumInfo : '',
			curSheetRecordNumMsg : function(o) {
				this.curSheetRecordNumInfo = "当前sheet页签记录数：[<font color='red'>"
						+ o + "</font>];<br>";
			},
			/** 是否显示 当前sheet页签号 */
			sheetNumInfo : '',
			sheetNumMsg : function(o) {
				this.sheetNumInfo = "当前sheet页签号为：[<font color='red'>" + o
						+ "</font>];<br>";
			},
			/** 是否显示 sheet页签总数 */
			totalSheetNumInfo : '',
			totalSheetNumMsg : function(o) {
				this.totalSheetNumInfo = "sheet页签总数：[<font color='red'>" + o
						+ "</font>];<br>";
			},
			/** 是否显示 已导入完成sheet数 */
			finishSheetNumInfo : '',
			finishSheetNumMsg : function(o) {
				this.finishSheetNumInfo = "已导入完成sheet数[<font color='red'>" + o
						+ "</font>];<br>";
			},
			/** 是否显示 已导入完成记录数 */
			finishRecordNumInfo : '',
			finishRecordNumMsg : function(o) {
				this.finishRecordNumInfo = "已导入完成记录数[<font color='red'>" + o
						+ "</font>];<br>";
			},
			/** 当前excel数据转化为SQL脚本成功记录数 */
			curRecordNum : 0,
			/** 导入总数 */
			totalNum : 1,
			/** 进度条信息 */
			progressBarText : '',
			/** 进度条Msg */
			progressBartitle : '',
			proxyStore : undefined,
			items : [],
			/** 进度条 */
			progressBar : null,
			/** *import成功句柄 */
			importSuccessHandler : function(json) {
				if (json != null) {
					if (typeof (json.curRecordNum) != 'undefined'
							&& this.curRecordNumMsg) {
						this.curRecordNumMsg(json.curRecordNum);
						this.curRecordNum = json.curRecordNum;
					}
					if (typeof (json.importState) != 'undefined'
							&& this.importStateMsg) {
						this.importStateMsg(json.importState);
					}
					if (typeof (json.curSheetRecordNum) != 'undefined'
							&& this.curSheetRecordNumMsg) {
						this.curSheetRecordNumMsg(json.curSheetRecordNum);
					}
					if (typeof (json.sheetNum) != 'undefined'
							&& this.sheetNumMsg) {
						this.sheetNumMsg(json.sheetNum);
					}
					if (typeof (json.totalSheetNum) != 'undefined'
							&& this.totalSheetNumMsg) {
						this.totalSheetNumMsg(json.totalSheetNum);
					}
					if (typeof (json.finishSheetNum) != 'undefined'
							&& this.finishSheetNumMsg) {
						this.finishSheetNumMsg(json.finishSheetNum);
					}
					if (typeof (json.finishRecordNum) != 'undefined'
							&& this.finishRecordNumMsg) {
						this.finishRecordNumMsg(json.finishRecordNum);
					}
				} else {
					this.curRecordNumInfo = '';
					this.importStateInfo = '';
					this.curSheetRecordNumInfo = '';
					this.sheetNumInfo = '';
					this.totalSheetNumInfo = '';
					this.finishSheetNumInfo = '';
					this.finishRecordNumInfo = '';
				}

				this.progressBartitle = '';
				/** 进度条Msg信息配置：各信息项目显示内容由各自方法配置 */
				this.progressBartitle += this.curRecordNumMsg ? this.curRecordNumInfo
						: '';
				this.progressBartitle += this.importStateMsg ? this.importStateInfo
						: '';
				this.progressBartitle += this.curSheetRecordNumMsg ? this.curSheetRecordNumInfo
						: '';
				this.progressBartitle += this.sheetNumMsg ? this.sheetNumInfo
						: '';
				this.progressBartitle += this.totalSheetNumMsg ? this.totalSheetNumInfo
						: '';
				this.progressBartitle += this.finishSheetNumMsg ? this.finishSheetNumInfo
						: '';
				this.progressBartitle += this.finishRecordNumMsg ? this.finishRecordNumInfo
						: '';

				showProgressBar(this.totalNum, this.curRecordNum,
						this.progressBarText, this.progressBartitle,
						"上传成功，正在导入数据，请稍候");
			},
			buttons : [ {
				text : '导入文件',
				handler : function() {
					var tradecode = this.ownerCt.ownerCt.tradecode;
					var proxyStorePS = this.ownerCt.ownerCt.proxyStore;
					var proxyHttpPH = this.ownerCt.ownerCt.proxyHttp;
					if (tradecode == undefined || tradecode == '') {
						Ext.MessageBox
								.alert('Debugging！',
										'You forgot to define the tradecode for the import form!');
						return false;
					}
					var impRefreshHandler = 0;
					if (this.ownerCt.ownerCt.getForm().isValid()) {
						this.ownerCt.ownerCt.ownerCt.hide();
						var fileNamesFull = this.ownerCt.ownerCt.items.items[0]
								.getValue();
						var extPoit = fileNamesFull.substring(fileNamesFull
								.indexOf('.'));
						if (extPoit == '.xls' || extPoit == '.XLS'
								|| extPoit == '.xlsx' || extPoit == '.XLSX') {
						} else {
							Ext.MessageBox.alert("文件错误", "导入文件不是XLS或XLSX文件。");
							return false;
						}
						showProgressBar(1, 0, '', '', '正在上传文件...');

						this.ownerCt.ownerCt
								.getForm()
								.submit(
										{
											url : basepath
													+ '/FileUpload?isImport=true',
											success : function(form, o) {
												_tempImpFileName = Ext.util.JSON
														.decode(o.response.responseText).realFileName;
												var condi = {};
												condi['filename'] = _tempImpFileName;
												condi['tradecode'] = tradecode;
												Ext.Ajax
														.request({
															url : basepath
																	+ "/ImportAction.json",
															method : 'GET',
															params : {
																"condition" : Ext
																		.encode(condi)
															},
															success : function() {
																importForm
																		.importSuccessHandler(null);
																var importFresh = function() {
																	Ext.Ajax
																			.request({
																				url : basepath
																						+ "/ImportAction!refresh.json",
																				method : 'GET',
																				success : function(
																						a) {
																					if (a.status == '200'
																							|| a.status == '201') {
																						var res = Ext.util.JSON
																								.decode(a.responseText);
																						if (res.json.result != undefined
																								&& res.json.result == '200') {
																							window
																									.clearInterval(impRefreshHandler);
																							if (res.json.BACK_IMPORT_ERROR
																									&& res.json.BACK_IMPORT_ERROR == 'FILE_ERROR') {
																								Ext.Msg
																										.alert(
																												"提示",
																												"导出文件格式有误，请下载导入模版。");
																								return;
																							}
																							if (proxyStorePS != undefined) {
																								var condiFormP = {};
																								condiFormP['pkHaed'] = res.json.PK_HEAD;
																								pkHead = res.json.PK_HEAD;
																								proxyStorePS
																										.load({
																											params : {
																												pkHead : pkHead
																											}
																										});
																							} else {
																								importForm
																										.importSuccessHandler(res.json);
																								showSuccessWinImp(res.json.curRecordNum);// 导入数据条数
																							}
																						} else if (res.json.result != undefined
																								&& res.json.result == '900') {

																							window
																									.clearInterval(impRefreshHandler);
																							importState = true;
																							progressWin
																									.hide();// 隐藏导入进度窗口
																							Ext.Msg
																									.alert(
																											"导入失败",
																											"失败原因：\n"
																													+ res.json.BACK_IMPORT_ERROR);
																						} else if (res.json.result != undefined
																								&& res.json.result == '999') {
																							importForm
																									.importSuccessHandler(res.json);
																						}
																					}
																				}
																			});
																};
																impRefreshHandler = window
																		.setInterval(
																				importFresh,
																				1000);
															},
															failure : function() {
															}
														});

											},
											failure : function(form, o) {
												Ext.Msg.show({
													title : 'Result',
													msg : '数据文件上传失败，请稍后重试!',
													buttons : Ext.Msg.OK,
													icon : Ext.Msg.ERROR
												});
											}
										});
					}
				}
			} ]
		});
/**
 * 导入弹出窗口，此对象为全局对象，由各页面直接调用。
 */

var importWindow = new Ext.Window({
	width : 700,
	hideMode : 'offsets',
	modal : true,
	height : 250,
	closeAction : 'hide',
	items : [ importForm ]
});
importWindow.on('show', function(upWindow) {
	if (Ext.getCmp('littleup')) {
		importForm.remove(Ext.getCmp('littleup'));
	}
	importForm.removeAll(true);
	importForm.add(new Ext.form.TextField({
		xtype : 'textfield',
		id : 'littleim',
		name : 'annexeName',
		inputType : 'file',
		fieldLabel : '文件名称',
		anchor : '90%'
	}));
	importForm.doLayout();
});
var progressBar = {};
var importState = false;
var progressWin = new Ext.Window({
	width : 300,
	hideMode : 'offsets',
	closable : true,
	modal : true,
	autoHeight : true,
	closeAction : 'hide',
	items : [],
	listeners : {
		'beforehide' : function() {
			return importState;
		},
		'hide' : function(){
			reloadCurrentData();
		}
	}
});
function showProgressBar(count, curnum, bartext, msg, title) {
	importState = false;
	progressBar = new Ext.ProgressBar({
		width : 285
	});
	progressBar.wait({
		interval : 200, // 每次更新的间隔周期
		duration : 5000, // 进度条运作时候的长度，单位是毫秒
		increment : 5, // 进度条每次更新的幅度大小，默示走完一轮要几次（默认为10）。
		fn : function() { // 当进度条完成主动更新后履行的回调函数。该函数没有参数。
			progressBar.reset();
		}
	});
	progressWin.removeAll();
	progressWin.setTitle(title);
	if (msg.length == 0) {
		msg = '正在导入...';
	}
	var importContext = new Ext.Panel({
		title : '',
		frame : true,
		region : 'center',
		height : 100,
		width : '100%',
		autoScroll : true,
		html : '<span>' + msg + '</span>'
	});
	progressWin.add(importContext);
	progressWin.add(progressBar);
	progressWin.doLayout();
	progressWin.show();

}
function showSuccessWinImp(curRecordNum) {
	importState = true;
	progressWin.removeAll();
	progressWin.setTitle("成功导入记录数为[" + curRecordNum + "]");
	progressWin.add(new Ext.Panel({
		title : '',
		width : 300,
		layout : 'fit',
		autoHeight : true,
		bodyStyle : 'text-align:center',
		html : '<img src="' + basepath + '/contents/img/UltraMix55.gif" />'
	}));
	progressWin.doLayout();
	progressWin.show();
}
/*************导入窗口定义模块end*****************/
var tbar = [{
	text : '客户视图',
	handler:function(){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择数据！');
				return false;
			}
			var custId = getSelectedData().data.CUST_ID;
			var custName = getSelectedData().data.CUST_NAME;
			var custType = getSelectedData().data.CUST_TYPE;
			parent.Wlj.ViewMgr.openViewWindow(5,custId,custName);
		}
	},{
	text : '删除',
//	hidden:JsContext.checkGrant('callReport_delete'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
	        var ID = '';
			for (var i=0;i<getAllSelects().length;i++){
				ID += getAllSelects()[i].data.CALL_ID;
				ID += ",";
			}
			ID = ID.substring(0, ID.length-1);
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
	                url: basepath+'/ocrmFSeCallreport!batchDestroy.json',                                
	                method : 'POST',
	                params : {
	                    idStr:ID
	                },
	                success : function(){
	                    Ext.Msg.alert('提示', '删除成功');
	                    reloadCurrentData();
	                },
	                failure : function(){
	                    Ext.Msg.alert('提示', '删除失败');
	                    reloadCurrentData();
	                }
	            });
			});
		}
	}
//},
//
///**************导出*******************/
//new Com.yucheng.crm.common.NewExpButton({
//    formPanel : 'searchCondition',
////    hidden:JsContext.checkGrant('callReport_export'),
//    url : basepath+'/ocrmFSeCallreport.json'
//}),{
},{
	text:'导出',
	handler : function(){
		debugger;
		var userId = __userId;
		if(getAllSelects().length > 0){
			var custId = '';
			for(var i=0;i<getAllSelects().length;i++){
				custId += getAllSelects()[i].data.CUST_ID;
				if(i != getAllSelects().length-1){
					custId += ",";
				}
			}
//			var custId=getSelectedData().data.CUST_ID;
			Url = basepath+'/reportJsp/callReportExport.jsp?raq=callreport_st.raq&custId='+custId+'&mgr='+userId;
		}else{
			 Url = basepath+'/reportJsp/callReportExport.jsp?raq=callreport_st.raq&mgr='+userId;
		}
		window.open(Url);
			}
},{
	text : '模板下载',
	handler : function() {
		var winPara = 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no,'
				+ ' scrollbars=no, resizable=no,location=no, status=no';
		var fileName = 'callReportImport.xlsx';// 模板名称
		var uploadUrl = basepath + '/TempDownload?filename=' + fileName;
		window.open(uploadUrl, '', winPara);
	}
},{
	text : '批量导入',
	handler : function() {
		importForm.tradecode = 'importCallReport';
		importWindow.show();
	}
},{
	text:'商机访谈结果',
	handler : function(){
		if(!getSelectedData()){
			Ext.Msg.alert('提示','请先选择一条记录！');
			return false;
		}
		var custId=getSelectedData().data.CUST_ID;
		var orgId = __units;
        var Url =' http://10.20.34.107:7001/crmweb/reportJsp/showReport.jsp?raq=qianzaikehu.raq&eee='+custId+'&ccc='+orgId;
		window.open(Url);
			}
		}];
/**view 滑入前控制**/
var beforeviewshow = function(view){
	if(view.baseType=='createView'){
		view.contentPanel.getForm().findField('RECOMMEND_CUST_ID').setVisible(false);
		//隐藏商机按钮
		Ext.getCmp("addSJ").hide();
		Ext.getCmp("addSJ_Edits").hide();
		Ext.getCmp("addSJ_Del").hide();
		Ext.getCmp("addSJ_Next").hide();
		Ext.getCmp("modifySJ").hide();
		Ext.getCmp("deleteSJ").hide();
		createStore_sj.removeAll();
		//隐藏到期提醒信息
		Ext.getCmp("addDQ_up").hide();
		Ext.getCmp("addDQ_de").hide();
		Ext.getCmp("modifyDQ").hide();
		Ext.getCmp("deleteDQ").hide();
		createStore_dq.removeAll();
		//隐藏大额流失
		Ext.getCmp("addLS_Edits").hide();
//		Ext.getCmp("modifyLS").hide();
//		Ext.getCmp("deleteLS").hide();
		createStore_ls.removeAll();
		createStore_asn.removeAll();
		
	}
	if(view.baseType=='editView'){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
		//激活商机按钮
		Ext.getCmp("addSJ").hide();
		Ext.getCmp("modifySJ").show();
		Ext.getCmp("deleteSJ").show();
		var id = getSelectedData().data.CALL_ID;
		editStore_sj.load({params:{callId:id}});
		//激活到期提醒信息
		Ext.getCmp("modifyDQ").show();
		Ext.getCmp("deleteDQ").show();
		var custId = getSelectedData().data.CUST_ID;
		editStore_dq.load({params:{callReport_custId:custId}});
		//激活大额流失
//		Ext.getCmp("addLS").show();
//		Ext.getCmp("modifyLS").show();
//		Ext.getCmp("deleteLS").show();
		editStore_ls.load({params:{callReport_custId:custId}});
		//联系内容概览
		var visitDate = getSelectedData().data.VISIT_DATE;
		editStore_asn.load({params:{callReport_custId:custId,visitDate:visitDate}});
		
//		if(view._defaultTitle=='新增大额流失'){
//			view.contentPanel.getForm().findField('CUST_ID').setValue(custId);
//		}
//		if(view._defaultTitle=='新增大额流失_修改'){
//			view.contentPanel.getForm().findField('CUST_ID').setValue(custId);
//		}
		//如果是潜在客户就显示证件类型和ID字段列，否则不显示
		var custType = getSelectedData().data.CUST_TYPE;
		var potCusId = getSelectedData().data.POT_CUS_ID;
		if(custType == '1' || potCusId == ""){
			view.contentPanel.getForm().findField('IDENT_TYPE').setVisible(false);
			view.contentPanel.getForm().findField('IDENT_NO').setVisible(false);
		}else{
			view.contentPanel.getForm().findField('IDENT_TYPE').setVisible(true);
			view.contentPanel.getForm().findField('IDENT_NO').setVisible(true);
		}
	}
	/*修改查询面板滑入时，做相应判断，并加载相关附件信息*/
	if(view.baseType == 'detailView'){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}
			Ext.getCmp("addSJ").hide();
			Ext.getCmp("modifySJ").hide();
			Ext.getCmp("deleteSJ").hide();
			//激活到期提醒信息
			Ext.getCmp("modifyDQ").hide();
			Ext.getCmp("deleteDQ").hide();
			//激活大额流失
//			Ext.getCmp("addLS").hide();
//			Ext.getCmp("modifyLS").hide();
//			Ext.getCmp("deleteLS").hide();
			//加载商机
			var id = getSelectedData().data.CALL_ID;
			createStore_sj.load({params:{callId:id}});
			
			var custId = getSelectedData().data.CUST_ID;
			var visitDate = getSelectedData().data.VISIT_DATE;
			//到期提醒信息
			createStore_dq.load({params:{callReport_custId:custId}});
			//大额流失
			createStore_ls.load({params:{callReport_custId:custId}});
			//联系内容概览
			createStore_asn.load({params:{callReport_custId:custId,visitDate:visitDate}});
			
	}
	if(view._defaultTitle=='访谈结果(商机)'){
		callId = getSelectedData().data.CALL_ID;
		view.setParameters ({
			callId : callId
		}); 
	}
	
	if(view._defaultTitle=='新增商机'){
		view.contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
//		view.contentPanel.getForm().findField('PRODUCT_ID').setVisible(false);
		view.contentPanel.getForm().findField('REMARK').setVisible(false);
	}
	if(view._defaultTitle=='新增商机_修改'){
		view.contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
//		view.contentPanel.getForm().findField('PRODUCT_ID').setVisible(false);
		view.contentPanel.getForm().findField('REMARK').setVisible(false);
	}
//	if(view._defaultTitle=='新增大额流失'){
//		view.contentPanel.getForm().findField('CUST_ID').setValue(cID.CUST_ID);
//	}
//	if(view._defaultTitle=='新增大额流失_修改'){
//		view.contentPanel.getForm().findField('CUST_ID').setValue(cID.CUST_ID);
//	}
	
};	
/**
 * 查询条件域对象渲染之后触发；
 * params ：con：查询条件面板对象；
 * 			app：当前APP对象；
 */
var afterconditionrender = function() {
	getConditionField('CUST_NAME_TEST').fieldLabel = "客户名称";
};
/********面板滑出后隐藏相关字段***********/
var viewshow = function(view){
	if(view.baseType=='editView'||view.baseType == 'detailView'){
		var recomment = view.contentPanel.getForm().findField('CUST_CHANNEL').getValue();
		if(recomment == '14'){
			view.contentPanel.getForm().findField('RECOMMEND_CUST_ID').setVisible(true);
		}else{
			view.contentPanel.getForm().findField('RECOMMEND_CUST_ID').setVisible(false);
		}
	}
	
};
/********新增面板保存数据后激活附件面板***********/
var afertcommit = function(){
	lockGrid();//锁定结果列表
	if(getCurrentView().baseType=='createView'){//激活
		Ext.Ajax.request({
			url : basepath+'/session-info!getPid.json',
			method : 'GET',
			success : function(a,b,v) {//返回id值，显示tbar
			    var noticeIdStr = Ext.decode(a.responseText).pid;
			    getCurrentView().contentPanel.getForm().findField('CALL_ID').setValue(noticeIdStr);
			    callIds = noticeIdStr;
			}
		});
		//激活新增商机按钮
		Ext.getCmp("addSJ").show();
		Ext.getCmp("addLS_Edits").show();
		Ext.getCmp("addDQ_up").show();
		Ext.getCmp("addDQ_de").show();
		//加载近三次联系内容
		var  custId = getCurrentView().contentPanel.getForm().findField('CUST_ID').getValue();
		var  visitDate = getCurrentView().contentPanel.getForm().findField('VISIT_DATE').getValue();
		//激活到期提醒信息
		createStore_dq.load({params:{callReport_custId:custId}});
		//激活大额流失
		createStore_ls.load({params:{callReport_custId:custId}});
		//联系内容概览
		createStore_asn.load({params:{callReport_custId:custId,visitDate:visitDate}});
	}
	if(getCurrentView().baseType=='editView'){
		var  id = getCurrentView().contentPanel.getForm().findField('CALL_ID').getValue();
		var  custId = getCurrentView().contentPanel.getForm().findField('CUST_ID').getValue();
		var  visitDate = getCurrentView().contentPanel.getForm().findField('VISIT_DATE').getValue()
		createStore_sj.load({params:{callId:id}});
		//激活到期提醒信息
		createStore_dq.load({params:{callReport_custId:custId}});
		//激活大额流失
		createStore_ls.load({params:{callReport_custId:custId}});
		//联系内容概览
		createStore_asn.load({params:{callReport_custId:custId,visitDate:visitDate}});
	}
};
var customerView=[{
	title : '新增商机',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : true},
	            {name : 'CALL_ID',hidden : true},
	            {name : 'BUSI_NAME',text  : '商机名称',hidden : false},
	            {name : 'PRODUCT_ID',text  : '产品编号',translateType:'CALLREPORT_PRODUCT_NAME',allowBlank: false},
//	            {name : 'PRODUCT_NAME',text  : '产品编码',dataType:'productChoose',hiddenName:'PRODUCT_ID',searchField: true,singleSelect:true,allowBlank: false},
				{name : 'SALES_STAGE',text  : '销售阶段',hidden : false,translateType:'CALLREPORT_SAVES_STAGE',allowBlank: false,
					listeners:{
			    		select:function(combo,record){
			    			var v = this.getValue();
			    			if(v=='6'){//6失败
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(true);
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
			    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(true);
			    			}else{
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
			    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(false);
			    			}
						}
	        	}},
				{name : 'AMOUNT',text  : '金额(元)',hidden : false,xtype:'numberfield',allowBlank: false},
				{name : 'ESTIMATED_TIME',text  : '预计成交时间',hidden : false,xtype:'datefield',allowBlank: false}
				],
			fn : function(ID,CALL_ID,PRODUCT_ID,SALES_STAGE,AMOUNT,ESTIMATED_TIME,FAIL_REASON,REMARK,BUSI_NAME){
				return [ID,CALL_ID,PRODUCT_ID,SALES_STAGE,AMOUNT,ESTIMATED_TIME,FAIL_REASON,REMARK,BUSI_NAME];
			}
	},{
		columnCount :1 ,
		fields : [{name : 'FAIL_REASON',text  : '失败原因',hidden : true,translateType:'CALLREPORT_FAIL_REASON'},
				{name : 'REMARK',text  : '备注',hidden : true,maxLength:500,xtype:'textarea'}],
		fn : function(FAIL_REASON,REMARK){
			return [FAIL_REASON,REMARK];
		}
   }],
formButtons:[{
	text : '保存',
	//保存数据					 
	fn : function(formPanel,baseform){	
		if(!baseform.isValid())
			{
				Ext.Msg.alert('提示','请输入完整！');
				return false;
			}
		    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在提交数据，请稍等...','提示');
		    Ext.Ajax.request({
 				url : basepath + '/ocrmFSeCallreportBusi.json?callIds='+callIds,
 				method : 'POST',
 				params : commintData,
				success : function() {
					Ext.Msg.alert('提示',"操作成功!");
					showCreateView();
					//加载商机面板
					createStore_sj.load({params:{callId:callIds}});
					//回写新增面板
					Ext.Ajax.request({
		 				url : basepath + '/ocrmFSeCallreport.json?callIds='+callIds,
		 				method : 'GET',
						success : function(response) {
							var resultArray = Ext.util.JSON.decode(response.responseText)
							getCreateView().contentPanel.getForm().findField('CALL_ID').setValue(resultArray.json.data[0].CALL_ID);
							getCreateView().contentPanel.getForm().findField('CUST_ID').setValue(resultArray.json.data[0].CUST_ID);
							getCreateView().contentPanel.getForm().findField('CUST_NAME').setValue(resultArray.json.data[0].CUST_NAME);
							getCreateView().contentPanel.getForm().findField('CUST_TYPE').setValue(resultArray.json.data[0].CUST_TYPE);
							getCreateView().contentPanel.getForm().findField('VISIT_DATE').setValue(resultArray.json.data[0].VISIT_DATE);
							getCreateView().contentPanel.getForm().findField('BEGIN_DATE').setValue(resultArray.json.data[0].BEGIN_DATE);
							getCreateView().contentPanel.getForm().findField('END_DATE').setValue(resultArray.json.data[0].END_DATE);
							getCreateView().contentPanel.getForm().findField('LINK_PHONE').setValue(resultArray.json.data[0].LINK_PHONE);
							getCreateView().contentPanel.getForm().findField('VISIT_WAY').setValue(resultArray.json.data[0].VISIT_WAY);
							getCreateView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(resultArray.json.data[0].CUST_CHANNEL);
							getCreateView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue(resultArray.json.data[0].RECOMMEND_CUST_ID);
							getCreateView().contentPanel.getForm().findField('VISIT_CONTENT').setValue(resultArray.json.data[0].VISIT_CONTENT);
							getCreateView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue(resultArray.json.data[0].NEXT_VISIT_DATE);
							getCreateView().contentPanel.getForm().findField('NEXT_VISIT_WAY').setValue(resultArray.json.data[0].NEXT_VISIT_WAY);
							getCreateView().contentPanel.getForm().findField('MKT_BAK_NOTE').setValue(resultArray.json.data[0].MKT_BAK_NOTE);
							getCreateView().contentPanel.getForm().findField('IDENT_TYPE').setValue(resultArray.json.data[0].IDENT_TYPE);
							getCreateView().contentPanel.getForm().findField('IDENT_NO').setValue(resultArray.json.data[0].IDENT_NO);
						}
					});
					if(cID.CUST_ID != undefined){
						var custId = cID.CUST_ID;
						Ext.getCmp("addSJ").show();
//						Ext.getCmp("addLS").show();
						Ext.getCmp("addSJ_Edits").show();
						Ext.getCmp("addSJ_Del").show();
						Ext.getCmp("addSJ_Next").show();
						Ext.getCmp("addLS_Edits").show();
						Ext.getCmp("addDQ_up").show();
						Ext.getCmp("addDQ_de").show();
						//加载商机面板
						createStore_sj.load({params:{callId:callIds}});
						//到期提醒信息
						createStore_dq.load({params:{callReport_custId:custId}});
						//大额流失
						createStore_ls.load({params:{callReport_custId:custId}});
						//联系内容概览
						createStore_asn.load({params:{callReport_custId:custId}});
					}
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
			 		if(resultArray == 403) {
		           		Ext.Msg.alert('提示', response.responseText);
			 		}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	 				}
				}
			});
		}
}]
},{
	title : '新增商机_修改',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : true},
	            {name : 'CALL_ID',hidden : true},
	            {name : 'PRODUCT_ID',text  : '产品编号',translateType:'CALLREPORT_PRODUCT_NAME',allowBlank: false},
	            {name : 'BUSI_NAME',text  : '商机名称',hidden : false},
//	            {name : 'PRODUCT_NAME',text  : '产品编码',dataType:'productChoose',hiddenName:'PRODUCT_ID',searchField: true,singleSelect:true,allowBlank: false},
				{name : 'SALES_STAGE',text  : '销售阶段',hidden : false,translateType:'CALLREPORT_SAVES_STAGE',allowBlank: false,
					listeners:{
			    		select:function(combo,record){
			    			var v = this.getValue();
			    			if(v=='6'){//6失败
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(true);
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
			    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(true);
			    			}else{
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
			    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(false);
			    			}
						}
	        	}},
				{name : 'AMOUNT',text  : '金额(元)',hidden : false,xtype:'numberfield',allowBlank: false},
				{name : 'ESTIMATED_TIME',text  : '预计成交时间',hidden : false,format:'Y-m-d',xtype:'datefield',allowBlank: false}
				],
			fn : function(ID,CALL_ID,PRODUCT_ID,PSALES_STAGE,AMOUNT,ESTIMATED_TIME,FAIL_REASON,REMARK,BUSI_NAME){
				return [ID,CALL_ID,PRODUCT_ID,SALES_STAGE,AMOUNT,ESTIMATED_TIME,FAIL_REASON,REMARK,BUSI_NAME];
			}
	},{
		columnCount :1 ,
		fields : [{name : 'FAIL_REASON',text  : '失败原因',hidden : true,translateType:'CALLREPORT_FAIL_REASON'},
				{name : 'REMARK',text  : '备注',hidden : true,maxLength:500,xtype:'textarea'}],
		fn : function(FAIL_REASON,REMARK){
			return [FAIL_REASON,REMARK];
		}
   }],
formButtons:[{
	text : '保存',
	//保存数据					 
	fn : function(formPanel,baseform){						
		if(!baseform.isValid())
			{
				Ext.Msg.alert('提示','请输入完整！');
				return false;
			}
		var callId = this.contentPanel.getForm().getValues().CALL_ID;
		var busiName = this.contentPanel.getForm().getValues().BUSI_NAME;
		var productId = this.contentPanel.getForm().getValues().PRODUCT_ID;
		var salesStage = this.contentPanel.getForm().getValues().SALES_STAGE;
		var amount = this.contentPanel.getForm().getValues().AMOUNT;
		var estimatedTime = this.contentPanel.getForm().getValues().ESTIMATED_TIME;
		Ext.Ajax.request({
			url : basepath + '/ocrmFSeCallreportBusi!saveBusi.json',
			method : 'POST',
			params : {
				'callId' : callId,
				'busiName' : busiName,
				'productId' : productId,
				'salesStage' : salesStage,
				'amount' : amount,
				'estimatedTime' : estimatedTime
			},
			success : function() {
				 Ext.Msg.alert('提示','操作成功！');
//				 reloadCurrentData();
				 showEditView();
			},
			failure : function(response) {
				Ext.Msg.alert('提示','操作失败！');
				 showEditView();
			}
		});
	}
},{
	text : '返回',
	fn : function(formPanel){
		showEditView();
	}
}]
},{
	title : '修改商机_新增面板',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : false},
	            {name : 'CALL_ID',hidden : false},
	            {name : 'BUSI_NAME',text:'商机名称',hidden : false,readOnly:true,cls:'x-readOnly'},
	            {name : 'PRODUCT_ID',text  : '产品编号',readOnly:true,cls:'x-readOnly',translateType:'CALLREPORT_PRODUCT_NAME',allowBlank: false},
				{name : 'SALES_STAGE',text  : '销售阶段',readOnly:true,cls:'x-readOnly',hidden : false,translateType:'CALLREPORT_SAVES_STAGE',allowBlank: false,
					listeners:{
			    		select:function(combo,record){
			    			var v = this.getValue();
			    			if(v=='6'){//6失败
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(true);
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
			    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(true);
			    			}else{
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
			    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(false);
			    			}
						}
	        	}},
				{name : 'AMOUNT',text  : '金额(元)',hidden : false,allowBlank: false},
				{name : 'ESTIMATED_TIME',text  : '预计成交时间',hidden : false,xtype:'datefield',allowBlank: false}
				],
			fn : function(ID,CALL_ID,PRODUCT_ID,SALES_STAGE,AMOUNT,ESTIMATED_TIME,BUSI_NAME){
				return [ID,CALL_ID,PRODUCT_ID,SALES_STAGE,AMOUNT,ESTIMATED_TIME,BUSI_NAME];
			}
	},{
		columnCount :1 ,
		fields : [{name : 'FAIL_REASON',text  : '失败原因',hidden : true,translateType:'CALLREPORT_FAIL_REASON',allowBlank : true},
				{name : 'REMARK',text  : '备注',hidden : true,maxLength:500,xtype:'textarea' , allowBlank : true}],
		fn : function(FAIL_REASON,REMARK){
			return [FAIL_REASON,REMARK];
		}
   }],
formButtons:[{
	text : '保存',
	//保存数据					 
	fn : function(formPanel,baseform){	
			if (!formPanel.getForm().isValid()) {
		        Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		        return false;
		    };
		    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在提交数据，请稍等...','提示');
		    Ext.Ajax.request({
 				url : basepath + '/ocrmFSeCallreportBusi.json',
 				method : 'POST',
 				params : commintData,
				success : function() {
					Ext.Msg.alert('提示',"操作成功!");
					showCreateView();
					//加载商机面板
					createStore_sj.load({params:{callId:callIds}});
					//回写新增面板
					Ext.Ajax.request({
		 				url : basepath + '/ocrmFSeCallreport.json?callIds='+callIds,
		 				method : 'GET',
						success : function(response) {
							var resultArray = Ext.util.JSON.decode(response.responseText)
							getCreateView().contentPanel.getForm().findField('CALL_ID').setValue(resultArray.json.data[0].CALL_ID);
							getCreateView().contentPanel.getForm().findField('CUST_ID').setValue(resultArray.json.data[0].CUST_ID);
							getCreateView().contentPanel.getForm().findField('CUST_NAME').setValue(resultArray.json.data[0].CUST_NAME);
							getCreateView().contentPanel.getForm().findField('CUST_TYPE').setValue(resultArray.json.data[0].CUST_TYPE);
							getCreateView().contentPanel.getForm().findField('VISIT_DATE').setValue(resultArray.json.data[0].VISIT_DATE);
							getCreateView().contentPanel.getForm().findField('BEGIN_DATE').setValue(resultArray.json.data[0].BEGIN_DATE);
							getCreateView().contentPanel.getForm().findField('END_DATE').setValue(resultArray.json.data[0].END_DATE);
							getCreateView().contentPanel.getForm().findField('LINK_PHONE').setValue(resultArray.json.data[0].LINK_PHONE);
							getCreateView().contentPanel.getForm().findField('VISIT_WAY').setValue(resultArray.json.data[0].VISIT_WAY);
							getCreateView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(resultArray.json.data[0].CUST_CHANNEL);
							getCreateView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue(resultArray.json.data[0].RECOMMEND_CUST_ID);
							getCreateView().contentPanel.getForm().findField('VISIT_CONTENT').setValue(resultArray.json.data[0].VISIT_CONTENT);
							getCreateView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue(resultArray.json.data[0].NEXT_VISIT_DATE);
							getCreateView().contentPanel.getForm().findField('NEXT_VISIT_WAY').setValue(resultArray.json.data[0].NEXT_VISIT_WAY);
							getCreateView().contentPanel.getForm().findField('MKT_BAK_NOTE').setValue(resultArray.json.data[0].MKT_BAK_NOTE);
							getCreateView().contentPanel.getForm().findField('IDENT_TYPE').setValue(resultArray.json.data[0].IDENT_TYPE);
							getCreateView().contentPanel.getForm().findField('IDENT_NO').setValue(resultArray.json.data[0].IDENT_NO);
						}
					});
					if(cID.CUST_ID != undefined){
						var custId = cID.CUST_ID;
						Ext.getCmp("addSJ").show();
//						Ext.getCmp("addLS").show();
						Ext.getCmp("addSJ_Edits").show();
						Ext.getCmp("addSJ_Del").show();
						Ext.getCmp("addSJ_Next").show();
						Ext.getCmp("addLS_Edits").show();
						Ext.getCmp("addDQ_up").show();
						Ext.getCmp("addDQ_de").show();
						//加载商机面板
						createStore_sj.load({params:{callId:callIds}});
						//到期提醒信息
						createStore_dq.load({params:{callReport_custId:custId}});
						//大额流失
						createStore_ls.load({params:{callReport_custId:custId}});
						//联系内容概览
						createStore_asn.load({params:{callReport_custId:custId}});
					}
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
			 		if(resultArray == 403) {
		           		Ext.Msg.alert('提示', response.responseText);
			 		}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	 				}
				}
			});
		}
},{
	text : '返回',
	//保存数据					 
	fn : function(formPanel,baseform){
		showCreateView();
		//加载商机面板
		createStore_sj.load({params:{callId:callIds}});
		//回写新增面板
		Ext.Ajax.request({
				url : basepath + '/ocrmFSeCallreport.json?callIds='+callIds,
				method : 'GET',
			success : function(response) {
				var resultArray = Ext.util.JSON.decode(response.responseText)
				getCreateView().contentPanel.getForm().findField('CALL_ID').setValue(resultArray.json.data[0].CALL_ID);
				getCreateView().contentPanel.getForm().findField('CUST_ID').setValue(resultArray.json.data[0].CUST_ID);
				getCreateView().contentPanel.getForm().findField('CUST_NAME').setValue(resultArray.json.data[0].CUST_NAME);
				getCreateView().contentPanel.getForm().findField('CUST_TYPE').setValue(resultArray.json.data[0].CUST_TYPE);
				getCreateView().contentPanel.getForm().findField('VISIT_DATE').setValue(resultArray.json.data[0].VISIT_DATE);
				getCreateView().contentPanel.getForm().findField('BEGIN_DATE').setValue(resultArray.json.data[0].BEGIN_DATE);
				getCreateView().contentPanel.getForm().findField('END_DATE').setValue(resultArray.json.data[0].END_DATE);
				getCreateView().contentPanel.getForm().findField('LINK_PHONE').setValue(resultArray.json.data[0].LINK_PHONE);
				getCreateView().contentPanel.getForm().findField('VISIT_WAY').setValue(resultArray.json.data[0].VISIT_WAY);
				getCreateView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(resultArray.json.data[0].CUST_CHANNEL);
				getCreateView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue(resultArray.json.data[0].RECOMMEND_CUST_ID);
				getCreateView().contentPanel.getForm().findField('VISIT_CONTENT').setValue(resultArray.json.data[0].VISIT_CONTENT);
				getCreateView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue(resultArray.json.data[0].NEXT_VISIT_DATE);
				getCreateView().contentPanel.getForm().findField('NEXT_VISIT_WAY').setValue(resultArray.json.data[0].NEXT_VISIT_WAY);
				getCreateView().contentPanel.getForm().findField('MKT_BAK_NOTE').setValue(resultArray.json.data[0].MKT_BAK_NOTE);
				getCreateView().contentPanel.getForm().findField('IDENT_TYPE').setValue(resultArray.json.data[0].IDENT_TYPE);
				getCreateView().contentPanel.getForm().findField('IDENT_NO').setValue(resultArray.json.data[0].IDENT_NO);
			}
		});
		if(cID.CUST_ID != undefined){
			var custId = cID.CUST_ID;
			Ext.getCmp("addSJ").show();
//			Ext.getCmp("addLS").show();
			Ext.getCmp("addSJ_Edits").show();
			Ext.getCmp("addSJ_Del").show();
			Ext.getCmp("addSJ_Next").show();
			Ext.getCmp("addLS_Edits").show();
			Ext.getCmp("addDQ_up").show();
			Ext.getCmp("addDQ_de").show();
			//加载商机面板
			createStore_sj.load({params:{callId:callIds}});
			//到期提醒信息
			createStore_dq.load({params:{callReport_custId:custId}});
			//大额流失
			createStore_ls.load({params:{callReport_custId:custId}});
			//联系内容概览
			createStore_asn.load({params:{callReport_custId:custId}});
		}
	}
}]
},{
	title : '修改商机',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : false},
	            {name : 'CALL_ID',hidden : false},
	            {name : 'BUSI_NAME',text:'商机名称',hidden : false,readOnly:true,cls:'x-readOnly'},
	            {name : 'PRODUCT_ID',text  : '产品编号',readOnly:true,cls:'x-readOnly',translateType:'CALLREPORT_PRODUCT_NAME',allowBlank: false},
//	            {name : 'PRODUCT_NAME',text  : '产品编码',readOnly:true,cls:'x-readOnly'},
				{name : 'SALES_STAGE',text  : '销售阶段',readOnly:true,cls:'x-readOnly',hidden : false,translateType:'CALLREPORT_SAVES_STAGE',allowBlank: false,
					listeners:{
			    		select:function(combo,record){
			    			var v = this.getValue();
			    			if(v=='6'){//6失败
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(true);
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
			    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(true);
			    			}else{
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
			    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(false);
			    			}
						}
	        	}},
				{name : 'AMOUNT',text  : '金额(元)',hidden : false,allowBlank: false},
				{name : 'ESTIMATED_TIME',text  : '预计成交时间',hidden : false,xtype:'datefield',allowBlank: false}
				],
			fn : function(ID,CALL_ID,PRODUCT_ID,SALES_STAGE,AMOUNT,ESTIMATED_TIME,BUSI_NAME){
				return [ID,CALL_ID,PRODUCT_ID,SALES_STAGE,AMOUNT,ESTIMATED_TIME,BUSI_NAME];
			}
	},{
		columnCount :1 ,
		fields : [{name : 'FAIL_REASON',text  : '失败原因',hidden : true,translateType:'CALLREPORT_FAIL_REASON',allowBlank : true},
				{name : 'REMARK',text  : '备注',hidden : true,maxLength:500,xtype:'textarea' , allowBlank : true}],
		fn : function(FAIL_REASON,REMARK){
			return [FAIL_REASON,REMARK];
		}
   }],
formButtons:[{
	text : '保存',
	//保存数据					 
	fn : function(formPanel,baseform){					
//		   if(!baseform.isValid())
//			{
//				Ext.Msg.alert('提示','请输入完整！');
//				return false;
//			}
			if (!formPanel.getForm().isValid()) {
		        Ext.MessageBox.alert('提示','输入有误,请检查输入项');
		        return false;
		    };
		    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在提交数据，请稍等...','提示');
		    Ext.Ajax.request({
 				url : basepath + '/ocrmFSeCallreportBusi.json',
 				method : 'POST',
 				params : commintData,
				success : function() {
					Ext.Msg.alert('提示',"操作成功!");
					showEditView();
					//加载商机面板
//					createStore_sj.load({params:{callId:commintData.callId}});
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
			 		if(resultArray == 403) {
		           		Ext.Msg.alert('提示', response.responseText);
			 		}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	 				}
				}
			});
		}
},{
	text : '关闭',
	fn : function(formPanel){
		showEditView();
	}
}]
},{
	title : '新增商机_下一步',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : false},
	            {name : 'CALL_ID',hidden : false},
	            {name : 'BUSI_NAME',text  : '商机名称',hidden : false,readOnly:true,cls:'x-readOnly'},
	            {name : 'PRODUCT_ID',text  : '产品编号',readOnly:true,cls:'x-readOnly',translateType:'CALLREPORT_PRODUCT_NAME',allowBlank: false},
//	            {name : 'PRODUCT_NAME',text  : '产品编码',readOnly:true,cls:'x-readOnly'},
				{name : 'SALES_STAGE',text  : '销售阶段',hidden : false,translateType:'CALLREPORT_SAVES_STAGE',allowBlank: false,
					listeners:{
			    		select:function(combo,record){
			    			var v = this.getValue();
			    			if(v=='6'){//6失败
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(true);
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
			    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(true);
			    			}else{
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
			    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(false);
			    			}
						}
	        	}},
				{name : 'AMOUNT',text  : '金额(元)',hidden : false,renderer:money('0,000.00'),allowBlank: false},
				{name : 'ESTIMATED_TIME',text  : '预计成交时间',hidden : false,xtype:'datefield',allowBlank: false}
				],
			fn : function(ID,CALL_ID,PRODUCT_ID,SALES_STAGE,AMOUNT,ESTIMATED_TIME,FAIL_REASON,REMARK,BUSI_NAME){
				return [ID,CALL_ID,PRODUCT_ID,SALES_STAGE,AMOUNT,ESTIMATED_TIME,FAIL_REASON,REMARK,BUSI_NAME];
			}
	},{
		columnCount :1 ,
		fields : [{name : 'FAIL_REASON',text  : '失败原因',hidden : true,translateType:'CALLREPORT_FAIL_REASON'},
				{name : 'REMARK',text  : '备注',hidden : true,maxLength:500,xtype:'textarea'}],
		fn : function(FAIL_REASON,REMARK){
			return [FAIL_REASON,REMARK];
		}
   }],
formButtons:[{
	text : '保存',
	//保存数据					 
	fn : function(formPanel,baseform){
		if(!baseform.isValid())
			{
				Ext.Msg.alert('提示','请完善信息！');
				return false;
			}
		//检验商机不可逆
  			var salesstage_pro = createPanel_SJ.getSelectionModel().getSelections()[0].get("SALES_STAGE");//获取修改前商机阶段
  			var salesstage_off = getCurrentView().contentPanel.getForm().findField('SALES_STAGE').value;//获取修改后商机阶段
	    Ext.Ajax.request({
            url: basepath+'/ocrmFSeCallreportBusi!busiNoBack.json',                                
            mthod : 'POST',
            params : {
	            'salesstagePro': salesstage_pro,
	            'salesstageOff' : salesstage_off
            }, 
            success : function(){
    		    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
    			Ext.Msg.wait('正在提交数据，请稍等...','提示');
    		    Ext.Ajax.request({
     				url : basepath + '/ocrmFSeCallreportBusi.json',
     				method : 'POST',
     				params : commintData,
    				success : function() {
    					Ext.Msg.alert('提示',"操作成功!");
    					showCreateView();
    					//加载商机面板
    					createStore_sj.load({params:{callId:callIds}});
    					//回写新增面板
    					Ext.Ajax.request({
    		 				url : basepath + '/ocrmFSeCallreport.json?callIds='+callIds,
    		 				method : 'GET',
    						success : function(response) {
    							var resultArray = Ext.util.JSON.decode(response.responseText)
    							getCreateView().contentPanel.getForm().findField('CALL_ID').setValue(resultArray.json.data[0].CALL_ID);
    							getCreateView().contentPanel.getForm().findField('CUST_ID').setValue(resultArray.json.data[0].CUST_ID);
    							getCreateView().contentPanel.getForm().findField('CUST_NAME').setValue(resultArray.json.data[0].CUST_NAME);
    							getCreateView().contentPanel.getForm().findField('CUST_TYPE').setValue(resultArray.json.data[0].CUST_TYPE);
    							getCreateView().contentPanel.getForm().findField('VISIT_DATE').setValue(resultArray.json.data[0].VISIT_DATE);
    							getCreateView().contentPanel.getForm().findField('BEGIN_DATE').setValue(resultArray.json.data[0].BEGIN_DATE);
    							getCreateView().contentPanel.getForm().findField('END_DATE').setValue(resultArray.json.data[0].END_DATE);
    							getCreateView().contentPanel.getForm().findField('LINK_PHONE').setValue(resultArray.json.data[0].LINK_PHONE);
    							getCreateView().contentPanel.getForm().findField('VISIT_WAY').setValue(resultArray.json.data[0].VISIT_WAY);
    							getCreateView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(resultArray.json.data[0].CUST_CHANNEL);
    							getCreateView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue(resultArray.json.data[0].RECOMMEND_CUST_ID);
    							getCreateView().contentPanel.getForm().findField('VISIT_CONTENT').setValue(resultArray.json.data[0].VISIT_CONTENT);
    							getCreateView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue(resultArray.json.data[0].NEXT_VISIT_DATE);
    							getCreateView().contentPanel.getForm().findField('NEXT_VISIT_WAY').setValue(resultArray.json.data[0].NEXT_VISIT_WAY);
    							getCreateView().contentPanel.getForm().findField('MKT_BAK_NOTE').setValue(resultArray.json.data[0].MKT_BAK_NOTE);
    							getCreateView().contentPanel.getForm().findField('IDENT_TYPE').setValue(resultArray.json.data[0].IDENT_TYPE);
    							getCreateView().contentPanel.getForm().findField('IDENT_NO').setValue(resultArray.json.data[0].IDENT_NO);
    						}
    					});
    					if(cID.CUST_ID != undefined){
    						var custId = cID.CUST_ID;
    						Ext.getCmp("addSJ").show();
//    						Ext.getCmp("addLS").show();
    						Ext.getCmp("addSJ_Edits").show();
    						Ext.getCmp("addSJ_Del").show();
    						Ext.getCmp("addSJ_Next").show();
    						Ext.getCmp("addLS_Edits").show();
    						Ext.getCmp("addDQ_up").show();
    						Ext.getCmp("addDQ_de").show();
    						//加载商机面板
    						createStore_sj.load({params:{callId:callIds}});
    						//到期提醒信息
    						createStore_dq.load({params:{callReport_custId:custId}});
    						//大额流失
    						createStore_ls.load({params:{callReport_custId:custId}});
    						//联系内容概览
    						createStore_asn.load({params:{callReport_custId:custId}});
    					}
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
            },
            failure : function(){
                Ext.Msg.alert('提示', '操作失败');
                reloadCurrentData();
            }
        });		
		}
},{
	text : '返回',
	//保存数据					 
	fn : function(formPanel,baseform){
		showCreateView();
		//加载商机面板
		createStore_sj.load({params:{callId:callIds}});
		//回写新增面板
		Ext.Ajax.request({
				url : basepath + '/ocrmFSeCallreport.json?callIds='+callIds,
				method : 'GET',
			success : function(response) {
				var resultArray = Ext.util.JSON.decode(response.responseText)
				getCreateView().contentPanel.getForm().findField('CALL_ID').setValue(resultArray.json.data[0].CALL_ID);
				getCreateView().contentPanel.getForm().findField('CUST_ID').setValue(resultArray.json.data[0].CUST_ID);
				getCreateView().contentPanel.getForm().findField('CUST_NAME').setValue(resultArray.json.data[0].CUST_NAME);
				getCreateView().contentPanel.getForm().findField('CUST_TYPE').setValue(resultArray.json.data[0].CUST_TYPE);
				getCreateView().contentPanel.getForm().findField('VISIT_DATE').setValue(resultArray.json.data[0].VISIT_DATE);
				getCreateView().contentPanel.getForm().findField('BEGIN_DATE').setValue(resultArray.json.data[0].BEGIN_DATE);
				getCreateView().contentPanel.getForm().findField('END_DATE').setValue(resultArray.json.data[0].END_DATE);
				getCreateView().contentPanel.getForm().findField('LINK_PHONE').setValue(resultArray.json.data[0].LINK_PHONE);
				getCreateView().contentPanel.getForm().findField('VISIT_WAY').setValue(resultArray.json.data[0].VISIT_WAY);
				getCreateView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(resultArray.json.data[0].CUST_CHANNEL);
				getCreateView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue(resultArray.json.data[0].RECOMMEND_CUST_ID);
				getCreateView().contentPanel.getForm().findField('VISIT_CONTENT').setValue(resultArray.json.data[0].VISIT_CONTENT);
				getCreateView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue(resultArray.json.data[0].NEXT_VISIT_DATE);
				getCreateView().contentPanel.getForm().findField('NEXT_VISIT_WAY').setValue(resultArray.json.data[0].NEXT_VISIT_WAY);
				getCreateView().contentPanel.getForm().findField('MKT_BAK_NOTE').setValue(resultArray.json.data[0].MKT_BAK_NOTE);
				getCreateView().contentPanel.getForm().findField('IDENT_TYPE').setValue(resultArray.json.data[0].IDENT_TYPE);
				getCreateView().contentPanel.getForm().findField('IDENT_NO').setValue(resultArray.json.data[0].IDENT_NO);
			}
		});
		if(cID.CUST_ID != undefined){
			var custId = cID.CUST_ID;
			Ext.getCmp("addSJ").show();
//			Ext.getCmp("addLS").show();
			Ext.getCmp("addSJ_Edits").show();
			Ext.getCmp("addSJ_Del").show();
			Ext.getCmp("addSJ_Next").show();
			Ext.getCmp("addLS_Edits").show();
			Ext.getCmp("addDQ_up").show();
			Ext.getCmp("addDQ_de").show();
			//加载商机面板
			createStore_sj.load({params:{callId:callIds}});
			//到期提醒信息
			createStore_dq.load({params:{callReport_custId:custId}});
			//大额流失
			createStore_ls.load({params:{callReport_custId:custId}});
			//联系内容概览
			createStore_asn.load({params:{callReport_custId:custId}});
		}
	}
}]
},{
	title : '修改商机_下一步',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : false},
	            {name : 'CALL_ID',hidden : false},
	            {name : 'BUSI_NAME',text  : '商机名称',hidden : false,readOnly:true,cls:'x-readOnly'},
	            {name : 'PRODUCT_ID',text  : '产品编号',readOnly:true,cls:'x-readOnly',translateType:'CALLREPORT_PRODUCT_NAME',allowBlank: false},
//	            {name : 'PRODUCT_NAME',text  : '产品编码',readOnly:true,cls:'x-readOnly'},
				{name : 'SALES_STAGE',text  : '销售阶段',hidden : false,translateType:'CALLREPORT_SAVES_STAGE',allowBlank: false,
					listeners:{
			    		select:function(combo,record){
			    			var v = this.getValue();
			    			if(v=='6'){//6失败
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(true);
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
			    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(true);
			    			}else{
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').setVisible(false);
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
			    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(false);
			    			}
						}
	        	}},
				{name : 'AMOUNT',text  : '金额(元)',hidden : false,renderer:money('0,000.00'),allowBlank: false},
				{name : 'ESTIMATED_TIME',text  : '预计成交时间',hidden : false,xtype:'datefield',allowBlank: false}
				],
			fn : function(ID,CALL_ID,PRODUCT_ID,SALES_STAGE,AMOUNT,ESTIMATED_TIME,FAIL_REASON,REMARK,BUSI_NAME){
				return [ID,CALL_ID,PRODUCT_ID,SALES_STAGE,AMOUNT,ESTIMATED_TIME,FAIL_REASON,REMARK,BUSI_NAME];
			}
	},{
		columnCount :1 ,
		fields : [{name : 'FAIL_REASON',text  : '失败原因',hidden : true,translateType:'CALLREPORT_FAIL_REASON'},
				{name : 'REMARK',text  : '备注',hidden : true,maxLength:500,xtype:'textarea'}],
		fn : function(FAIL_REASON,REMARK){
			return [FAIL_REASON,REMARK];
		}
   }],
formButtons:[{
	text : '保存',
	//保存数据					 
	fn : function(formPanel,baseform){
		if(!baseform.isValid())
			{
				Ext.Msg.alert('提示','请完善信息！');
				return false;
			}
		//检验商机不可逆
  			var salesstage_pro = editPanel_SJ.getSelectionModel().getSelections()[0].get("SALES_STAGE");//获取修改前商机阶段
  			var salesstage_off = getCurrentView().contentPanel.getForm().findField('SALES_STAGE').value;//获取修改后商机阶段
	    Ext.Ajax.request({
            url: basepath+'/ocrmFSeCallreportBusi!busiNoBack.json',                                
            mthod : 'POST',
            params : {
	            'salesstagePro': salesstage_pro,
	            'salesstageOff' : salesstage_off
            }, 
            success : function(){
    		    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
    			Ext.Msg.wait('正在提交数据，请稍等...','提示');
    		    Ext.Ajax.request({
     				url : basepath + '/ocrmFSeCallreportBusi.json',
     				method : 'POST',
     				params : commintData,
    				success : function() {
    					Ext.Msg.alert('提示',"操作成功!");
    					showEditView();
    				},
    				failure : function(response) {
    					var resultArray = Ext.util.JSON.decode(response.status);
    			 		if(resultArray == 403) {
    		           		Ext.Msg.alert('提示', response.responseText);
    			 		}else{
    						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
    	 				}
    				}
    			});
            },
            failure : function(){
                Ext.Msg.alert('提示', '操作失败');
                reloadCurrentData();
            }
        });		
		}
},{
	text : '返回',
	fn : function(formPanel){
		showEditView();
	}
}]
},{
	title : '修改到期提醒_新增',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : false},
	            {name : 'CALL_ID',hidden : false},
	            {name: 'CUST_ID',hidden:true},
	            {name : 'PRODUCT_ID',text  : '产品编号',hidden : false,readOnly:true,cls:'x-readOnly'},
	            {name : 'PRODUCT_NAME',text  : '产品编码',dataType:'productChoose',hiddenName:'PRODUCT_ID',searchField: true,singleSelect:true,allowBlank: false,readOnly:true,cls:'x-readOnly'},
				{name : 'AMOUNT',text  : '金额(元)',hidden : false,renderer:money('0,000.00'),allowBlank: false,readOnly:true,cls:'x-readOnly'},
				{name : 'END_DATE1',text  : '到期日',hidden : false,dataType:'date',allowBlank: false,readOnly:true,cls:'x-readOnly'},
				{name : 'SEQUEL_STAGE',text  : '是否续作',hidden : false,translateType:'CALLREPORT_SEQUEL_STAGE',allowBlank: false,listeners:{
			    		select:function(combo,record){
			    			var v = this.getValue();
			    			if(v=='2'||v=='3'){//2部分续作，3不续作
			    				getCurrentView().contentPanel.getForm().findField('OUT_AMOUNT').allowBlank = false;
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
			    			}else{
			    				getCurrentView().contentPanel.getForm().findField('OUT_AMOUNT').allowBlank = true;
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
			    			}
						}
		    	}},
				{name : 'SEQUEL_AMOUNT',text  : '续增金额(元)',hidden : false,renderer:money('0,000.00'),allowBlank: true},
				{name : 'OUT_AMOUNT',text  : '出账金额(元)',hidden : false,renderer:money('0,000.00')},
				{name : 'CASE_STAGE',text  : '是否结案',hidden : false,translateType:'CALLREPORT_CASE_STAGE', xtype:'datefield',allowBlank: false}
				],
			fn : function(ID,CALL_ID,CUST_ID,PRODUCT_ID,PRODUCT_NAME,AMOUNT,END_DATE1,SEQUEL_STAGE,SEQUEL_AMOUNT,OUT_AMOUNT,CASE_STAGE){
				return [ID,CALL_ID,CUST_ID,PRODUCT_ID,PRODUCT_NAME,AMOUNT,END_DATE1,SEQUEL_STAGE,SEQUEL_AMOUNT,OUT_AMOUNT,CASE_STAGE];
			}
	},{
		columnCount :0.95 ,
		fields : [{name : 'FAIL_REASON',text  : '出账原因',hidden : true,translateType:'CALLREPORT_DQ_REASON',
			listeners:{
	    		select:function(combo,record){
	    			var v = this.getValue();
	    			if(v=='8'){//8其他原因
	    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(true);
	    			}else{
	    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(false);
	    			}
				}
    	}}],
		fn : function(FAIL_REASON){
			return [FAIL_REASON];
		}
   },{
		columnCount :0.95 ,
		fields : [{name : 'REMARK',text  : '备注',hidden : true,xtype:'textarea'}],
		fn : function(REMARK){
			return [REMARK];
		}
   }],
formButtons:[{
	text : '保存',
	//保存数据					 
	fn : function(formPanel,baseform){	
		if(!baseform.isValid())
			{
				Ext.Msg.alert('提示','请输入完整！');
				return false;
			}
		    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在提交数据，请稍等...','提示');
		    Ext.Ajax.request({
 				url : basepath + '/ocrmFSeCallreportN.json',
 				method : 'POST',
 				params : commintData,
				success : function() {
					Ext.Msg.alert('提示',"操作成功!");
					showCreateView();
					//加载商机面板
					createStore_sj.load({params:{callId:callIds}});
					//回写新增面板
					Ext.Ajax.request({
							url : basepath + '/ocrmFSeCallreport.json?callIds='+callIds,
							method : 'GET',
						success : function(response) {
							var resultArray = Ext.util.JSON.decode(response.responseText)
							getCreateView().contentPanel.getForm().findField('CALL_ID').setValue(resultArray.json.data[0].CALL_ID);
							getCreateView().contentPanel.getForm().findField('CUST_ID').setValue(resultArray.json.data[0].CUST_ID);
							getCreateView().contentPanel.getForm().findField('CUST_NAME').setValue(resultArray.json.data[0].CUST_NAME);
							getCreateView().contentPanel.getForm().findField('CUST_TYPE').setValue(resultArray.json.data[0].CUST_TYPE);
							getCreateView().contentPanel.getForm().findField('VISIT_DATE').setValue(resultArray.json.data[0].VISIT_DATE);
							getCreateView().contentPanel.getForm().findField('BEGIN_DATE').setValue(resultArray.json.data[0].BEGIN_DATE);
							getCreateView().contentPanel.getForm().findField('END_DATE').setValue(resultArray.json.data[0].END_DATE);
							getCreateView().contentPanel.getForm().findField('LINK_PHONE').setValue(resultArray.json.data[0].LINK_PHONE);
							getCreateView().contentPanel.getForm().findField('VISIT_WAY').setValue(resultArray.json.data[0].VISIT_WAY);
							getCreateView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(resultArray.json.data[0].CUST_CHANNEL);
							getCreateView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue(resultArray.json.data[0].RECOMMEND_CUST_ID);
							getCreateView().contentPanel.getForm().findField('VISIT_CONTENT').setValue(resultArray.json.data[0].VISIT_CONTENT);
							getCreateView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue(resultArray.json.data[0].NEXT_VISIT_DATE);
							getCreateView().contentPanel.getForm().findField('NEXT_VISIT_WAY').setValue(resultArray.json.data[0].NEXT_VISIT_WAY);
							getCreateView().contentPanel.getForm().findField('MKT_BAK_NOTE').setValue(resultArray.json.data[0].MKT_BAK_NOTE);
							getCreateView().contentPanel.getForm().findField('IDENT_TYPE').setValue(resultArray.json.data[0].IDENT_TYPE);
							getCreateView().contentPanel.getForm().findField('IDENT_NO').setValue(resultArray.json.data[0].IDENT_NO);
						}
					});
					if(cID.CUST_ID != undefined){
						var custId = cID.CUST_ID;
						Ext.getCmp("addSJ").show();
//						Ext.getCmp("addLS").show();
						Ext.getCmp("addSJ_Edits").show();
						Ext.getCmp("addSJ_Del").show();
						Ext.getCmp("addSJ_Next").show();
						Ext.getCmp("addLS_Edits").show();
						Ext.getCmp("addDQ_up").show();
						Ext.getCmp("addDQ_de").show();
						//加载商机面板
						createStore_sj.load({params:{callId:callIds}});
						//到期提醒信息
						createStore_dq.load({params:{callReport_custId:custId}});
						//大额流失
						createStore_ls.load({params:{callReport_custId:custId}});
						//联系内容概览
						createStore_asn.load({params:{callReport_custId:custId}});
					}
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
			 		if(resultArray == 403) {
		           		Ext.Msg.alert('提示', response.responseText);
			 		}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	 				}
				}
			});
		}
}]
},{
	title : '修改到期提醒',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : false},
	            {name : 'CALL_ID',hidden : false},
	            {name: 'CUST_ID',hidden:true},
	            {name : 'PRODUCT_ID',text  : '产品编号',hidden : false,readOnly:true,cls:'x-readOnly'},
	            {name : 'PRODUCT_NAME',text  : '产品编码',dataType:'productChoose',hiddenName:'PRODUCT_ID',searchField: true,singleSelect:true,allowBlank: false,readOnly:true,cls:'x-readOnly'},
				{name : 'AMOUNT',text  : '金额(元)',hidden : false,renderer:money('0,000.00'),allowBlank: false,readOnly:true,cls:'x-readOnly'},
				{name : 'END_DATE1',text  : '到期日',hidden : false,dataType:'date',allowBlank: false,readOnly:true,cls:'x-readOnly'},
				{name : 'SEQUEL_STAGE',text  : '是否续作',hidden : false,translateType:'CALLREPORT_SEQUEL_STAGE',allowBlank: false,listeners:{
			    		select:function(combo,record){
			    			var v = this.getValue();
			    			if(v=='2'||v=='3'){//2部分续作，3不续作
			    				getCurrentView().contentPanel.getForm().findField('OUT_AMOUNT').allowBlank = false;
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = false;
			    			}else{
			    				getCurrentView().contentPanel.getForm().findField('OUT_AMOUNT').allowBlank = true;
			    				getCurrentView().contentPanel.getForm().findField('FAIL_REASON').allowBlank = true;
			    			}
						}
		    	}},
				{name : 'SEQUEL_AMOUNT',text  : '续增金额(元)',hidden : false,renderer:money('0,000.00'),allowBlank: true},
				{name : 'OUT_AMOUNT',text  : '出账金额(元)',hidden : false,renderer:money('0,000.00')},
				{name : 'CASE_STAGE',text  : '是否结案',hidden : false,translateType:'CALLREPORT_CASE_STAGE', xtype:'datefield',allowBlank: false}
				],
			fn : function(ID,CALL_ID,CUST_ID,PRODUCT_ID,PRODUCT_NAME,AMOUNT,END_DATE1,SEQUEL_STAGE,SEQUEL_AMOUNT,OUT_AMOUNT,CASE_STAGE){
				return [ID,CALL_ID,CUST_ID,PRODUCT_ID,PRODUCT_NAME,AMOUNT,END_DATE1,SEQUEL_STAGE,SEQUEL_AMOUNT,OUT_AMOUNT,CASE_STAGE];
			}
	},{
		columnCount :0.95 ,
		fields : [{name : 'FAIL_REASON',text  : '出账原因',hidden : true,translateType:'CALLREPORT_DQ_REASON',
			listeners:{
	    		select:function(combo,record){
	    			var v = this.getValue();
	    			if(v=='8'){//8其他原因
	    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(true);
	    			}else{
	    				getCurrentView().contentPanel.getForm().findField('REMARK').setVisible(false);
	    			}
				}
    	}}],
		fn : function(FAIL_REASON){
			return [FAIL_REASON];
		}
   },{
		columnCount :0.95 ,
		fields : [{name : 'REMARK',text  : '备注',hidden : true,xtype:'textarea'}],
		fn : function(REMARK){
			return [REMARK];
		}
   }],
formButtons:[{
	text : '保存',
	//保存数据					 
	fn : function(formPanel,baseform){	
		if(!baseform.isValid())
			{
				Ext.Msg.alert('提示','请输入完整！');
				return false;
			}
		    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在提交数据，请稍等...','提示');
		    Ext.Ajax.request({
 				url : basepath + '/ocrmFSeCallreportN.json',
 				method : 'POST',
 				params : commintData,
				success : function() {
					Ext.Msg.alert('提示',"操作成功!");
					showEditView();
					//加载提醒面板
//					createStore_dq.load({params:{callId:commintData.callId}});
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
			 		if(resultArray == 403) {
		           		Ext.Msg.alert('提示', response.responseText);
			 		}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	 				}
				}
			});
		}
}]
},{
	title : '新增大额流失',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : false,
	frame : true,
	groups : [{
		columnCount : 2 ,
		fields : [	
		            {name : 'ID',hidden : false},
		            {name : 'CALL_ID',hidden : false},
		            {name : 'CUST_ID',hidden : false},
		            {name : 'REMIND_DATE',text  : '汇款时间',hidden : false,xtype:'datefield'},
		            {name : 'REMIND_AMOUNT',text  : '汇款金额(元)',renderer:money('0,000.00'),allowBlank: false},
					{name : 'RECEIVER',text  : '收款人',hidden : false,allowBlank: false},
					{name : 'RECEIVE_BANK',text  : '收款行',hidden : false,allowBlank: false},
					{name : 'BACKFLOW_DATE',text  : '预计回流时间',hidden : false,xtype:'datefield'},
					{name : 'BACKFLOW_AMOUNT',text  : '预计回流金额(元)',hidden : false,renderer:money('0,000.00')},
					{name : 'REMIND_CASE_STAGE',text  : '是否结案',hidden : false,translateType:'CALLREPORT_CASE_STAGE',allowBlank: false}
					],
				fn : function(ID,CUST_ID,CALL_ID,REMIND_DATE,REMIND_AMOUNT,RECEIVER,RECEIVE_BANK,BACKFLOW_DATE,BACKFLOW_AMOUNT,REMIND_CASE_STAGE){
					REMIND_DATE.readOnly = true;
					REMIND_DATE.cls = 'x-readOnly';
					REMIND_AMOUNT.readOnly = true;
					REMIND_AMOUNT.cls = 'x-readOnly';
					RECEIVER.readOnly = true;
					RECEIVER.cls = 'x-readOnly';
					RECEIVE_BANK.readOnly = true;
					RECEIVE_BANK.cls = 'x-readOnly';
					return [ID,CUST_ID,CALL_ID,REMIND_DATE,REMIND_AMOUNT,RECEIVER,RECEIVE_BANK,BACKFLOW_DATE,BACKFLOW_AMOUNT,REMIND_CASE_STAGE];
				}
		},{
			columnCount :0.95 ,
			fields : [{name : 'REMIND_REASON',text  : '汇款原因',hidden : true,translateType:'CALLREPORT_REMIND_REASON',allowBlank: false,
				listeners:{
		    		select:function(combo,record){
		    			var v = this.getValue();
		    			if(v=='5'){//5其他原因
		    				getCurrentView().contentPanel.getForm().findField('REMIND_REMARK').setVisible(true);
		    			}else{
		    				getCurrentView().contentPanel.getForm().findField('REMIND_REMARK').setVisible(false);
		    			}
					}
	    	}}],
			fn : function(REMIND_REASON){
				return [REMIND_REASON];
			}
	   },{
			columnCount :0.95 ,
			fields : [{name : 'REMIND_REMARK',text  : '备注',hidden : true,xtype:'textarea'}],
			fn : function(REMIND_REMARK){
				return [REMIND_REMARK];
			}
	   }],
formButtons:[{
	text : '保存',
	//保存数据					 
	fn : function(formPanel,baseform){	
		if(!baseform.isValid())
			{
				Ext.Msg.alert('提示','请输入完整！');
				return false;
			}
		    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在提交数据，请稍等...','提示');
		    Ext.Ajax.request({
 				url : basepath + '/ocrmFSeCallreportRemind.json?callIds='+callIds,
 				method : 'POST',
 				params : commintData,
				success : function() {
					Ext.Msg.alert('提示',"操作成功!");
					showCreateView();
					//回写新增面板
					Ext.Ajax.request({
		 				url : basepath + '/ocrmFSeCallreport.json?callIds='+callIds,
		 				method : 'GET',
						success : function(response) {
							var resultArray = Ext.util.JSON.decode(response.responseText)
							getCreateView().contentPanel.getForm().findField('CALL_ID').setValue(resultArray.json.data[0].CALL_ID);
							getCreateView().contentPanel.getForm().findField('CUST_ID').setValue(resultArray.json.data[0].CUST_ID);
							getCreateView().contentPanel.getForm().findField('CUST_NAME').setValue(resultArray.json.data[0].CUST_NAME);
							getCreateView().contentPanel.getForm().findField('CUST_TYPE').setValue(resultArray.json.data[0].CUST_TYPE);
							getCreateView().contentPanel.getForm().findField('VISIT_DATE').setValue(resultArray.json.data[0].VISIT_DATE);
							getCreateView().contentPanel.getForm().findField('BEGIN_DATE').setValue(resultArray.json.data[0].BEGIN_DATE);
							getCreateView().contentPanel.getForm().findField('END_DATE').setValue(resultArray.json.data[0].END_DATE);
							getCreateView().contentPanel.getForm().findField('LINK_PHONE').setValue(resultArray.json.data[0].LINK_PHONE);
							getCreateView().contentPanel.getForm().findField('VISIT_WAY').setValue(resultArray.json.data[0].VISIT_WAY);
							getCreateView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(resultArray.json.data[0].CUST_CHANNEL);
							getCreateView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue(resultArray.json.data[0].RECOMMEND_CUST_ID);
							getCreateView().contentPanel.getForm().findField('VISIT_CONTENT').setValue(resultArray.json.data[0].VISIT_CONTENT);
							getCreateView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue(resultArray.json.data[0].NEXT_VISIT_DATE);
							getCreateView().contentPanel.getForm().findField('NEXT_VISIT_WAY').setValue(resultArray.json.data[0].NEXT_VISIT_WAY);
							getCreateView().contentPanel.getForm().findField('MKT_BAK_NOTE').setValue(resultArray.json.data[0].MKT_BAK_NOTE);
							getCreateView().contentPanel.getForm().findField('IDENT_TYPE').setValue(resultArray.json.data[0].IDENT_TYPE);
							getCreateView().contentPanel.getForm().findField('IDENT_NO').setValue(resultArray.json.data[0].IDENT_NO);
						}
					});
					if(cID.CUST_ID != undefined){
						Ext.getCmp("addSJ").show();
//						Ext.getCmp("addLS").show();
						Ext.getCmp("addSJ_Edits").show();
						Ext.getCmp("addSJ_Del").show();
						Ext.getCmp("addSJ_Next").show();
						Ext.getCmp("addLS_Edits").show();
						Ext.getCmp("addDQ_up").show();
						Ext.getCmp("addDQ_de").show();
						//加载商机面板
						createStore_sj.load({params:{callId:callIds}});
						//到期提醒信息
						createStore_dq.load({params:{callReport_custId:cID.CUST_ID}});
						//大额流失
						createStore_ls.load({params:{callReport_custId:cID.CUST_ID}});
						//联系内容概览
						createStore_asn.load({params:{callReport_custId:cID.CUST_ID}});
					}
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
			 		if(resultArray == 403) {
		           		Ext.Msg.alert('提示', response.responseText);
			 		}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	 				}
				}
			});
		}
},{
	text : '返回',
	//保存数据					 
	fn : function(formPanel,baseform){
		showCreateView();
		//加载商机面板
		createStore_sj.load({params:{callId:callIds}});
		//回写新增面板
		Ext.Ajax.request({
				url : basepath + '/ocrmFSeCallreport.json?callIds='+callIds,
				method : 'GET',
			success : function(response) {
				var resultArray = Ext.util.JSON.decode(response.responseText)
				getCreateView().contentPanel.getForm().findField('CALL_ID').setValue(resultArray.json.data[0].CALL_ID);
				getCreateView().contentPanel.getForm().findField('CUST_ID').setValue(resultArray.json.data[0].CUST_ID);
				getCreateView().contentPanel.getForm().findField('CUST_NAME').setValue(resultArray.json.data[0].CUST_NAME);
				getCreateView().contentPanel.getForm().findField('CUST_TYPE').setValue(resultArray.json.data[0].CUST_TYPE);
				getCreateView().contentPanel.getForm().findField('VISIT_DATE').setValue(resultArray.json.data[0].VISIT_DATE);
				getCreateView().contentPanel.getForm().findField('BEGIN_DATE').setValue(resultArray.json.data[0].BEGIN_DATE);
				getCreateView().contentPanel.getForm().findField('END_DATE').setValue(resultArray.json.data[0].END_DATE);
				getCreateView().contentPanel.getForm().findField('LINK_PHONE').setValue(resultArray.json.data[0].LINK_PHONE);
				getCreateView().contentPanel.getForm().findField('VISIT_WAY').setValue(resultArray.json.data[0].VISIT_WAY);
				getCreateView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(resultArray.json.data[0].CUST_CHANNEL);
				getCreateView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue(resultArray.json.data[0].RECOMMEND_CUST_ID);
				getCreateView().contentPanel.getForm().findField('VISIT_CONTENT').setValue(resultArray.json.data[0].VISIT_CONTENT);
				getCreateView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue(resultArray.json.data[0].NEXT_VISIT_DATE);
				getCreateView().contentPanel.getForm().findField('NEXT_VISIT_WAY').setValue(resultArray.json.data[0].NEXT_VISIT_WAY);
				getCreateView().contentPanel.getForm().findField('MKT_BAK_NOTE').setValue(resultArray.json.data[0].MKT_BAK_NOTE);
				getCreateView().contentPanel.getForm().findField('IDENT_TYPE').setValue(resultArray.json.data[0].IDENT_TYPE);
				getCreateView().contentPanel.getForm().findField('IDENT_NO').setValue(resultArray.json.data[0].IDENT_NO);
			}
		});
		if(cID.CUST_ID != undefined){
			var custId = cID.CUST_ID;
			Ext.getCmp("addSJ").show();
//			Ext.getCmp("addLS").show();
			Ext.getCmp("addSJ_Edits").show();
			Ext.getCmp("addSJ_Del").show();
			Ext.getCmp("addSJ_Next").show();
			Ext.getCmp("addLS_Edits").show();
			Ext.getCmp("addDQ_up").show();
			Ext.getCmp("addDQ_de").show();
			//加载商机面板
			createStore_sj.load({params:{callId:callIds}});
			//到期提醒信息
			createStore_dq.load({params:{callReport_custId:custId}});
			//大额流失
			createStore_ls.load({params:{callReport_custId:custId}});
			//联系内容概览
			createStore_asn.load({params:{callReport_custId:custId}});
		}
	}
}]
}
//,{
//	title : '新增大额流失_修改',
//	hideTitle : true,
//	type : 'form',
//	autoLoadSeleted : true,
//	frame : true,
//	groups : [{
//		columnCount : 2 ,
//		fields : [	
//		            {name : 'ID',hidden : false},
//		            {name : 'CALL_ID',hidden : false},
//		            {name : 'CUST_ID',hidden : false},
//		            {name : 'REMIND_DATE',text  : '汇款时间',hidden : false,xtype:'datefield'},
//		            {name : 'REMIND_AMOUNT',text  : '汇款金额(元)',renderer:money('0,000.00'),allowBlank: false},
//					{name : 'RECEIVER',text  : '收款人',hidden : false,allowBlank: false},
//					{name : 'RECEIVE_BANK',text  : '收款行',hidden : false,allowBlank: false},
//					{name : 'BACKFLOW_DATE',text  : '预计回流时间',hidden : false,xtype:'datefield'},
//					{name : 'BACKFLOW_AMOUNT',text  : '预计回流金额(元)',hidden : false,renderer:money('0,000.00')},
//					{name : 'REMIND_CASE_STAGE',text  : '是否结案',hidden : false,translateType:'CALLREPORT_CASE_STAGE',allowBlank: false}
//					],
//				fn : function(ID,CUST_ID,CALL_ID,REMIND_DATE,REMIND_AMOUNT,RECEIVER,RECEIVE_BANK,BACKFLOW_DATE,BACKFLOW_AMOUNT,REMIND_CASE_STAGE){
//					return [ID,CUST_ID,CALL_ID,REMIND_DATE,REMIND_AMOUNT,RECEIVER,RECEIVE_BANK,BACKFLOW_DATE,BACKFLOW_AMOUNT,REMIND_CASE_STAGE];
//				}
//		},{
//			columnCount :0.95 ,
//			fields : [{name : 'REMIND_REASON',text  : '汇款原因',hidden : true,translateType:'CALLREPORT_REMIND_REASON',allowBlank: false,
//				listeners:{
//		    		select:function(combo,record){
//		    			var v = this.getValue();
//		    			if(v=='5'){//5其他原因
//		    				getCurrentView().contentPanel.getForm().findField('REMIND_REMARK').setVisible(true);
//		    			}else{
//		    				getCurrentView().contentPanel.getForm().findField('REMIND_REMARK').setVisible(false);
//		    			}
//					}
//	    	}}],
//			fn : function(REMIND_REASON){
//				return [REMIND_REASON];
//			}
//	   },{
//			columnCount :0.95 ,
//			fields : [{name : 'REMIND_REMARK',text  : '备注',hidden : true,xtype:'textarea'}],
//			fn : function(REMIND_REMARK){
//				return [REMIND_REMARK];
//			}
//	   }],
//formButtons:[{
//	text : '保存',
//	//保存数据					 
//	fn : function(formPanel,baseform){						
//		if(!baseform.isValid())
//			{
//				Ext.Msg.alert('提示','请输入完整！');
//				return false;
//			}
//		    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
//			Ext.Msg.wait('正在提交数据，请稍等...','提示');
//		    Ext.Ajax.request({
// 				url : basepath + '/ocrmFSeCallreportRemind.json?callIds='+callIds,
// 				method : 'POST',
// 				params : commintData,
//				success : function() {
//					Ext.Msg.alert('提示',"操作成功!");
//					showEditView();
//				},
//				failure : function(response) {
//					var resultArray = Ext.util.JSON.decode(response.status);
//			 		if(resultArray == 403) {
//		           		Ext.Msg.alert('提示', response.responseText);
//			 		}else{
//						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
//	 				}
//				}
//			});
//		}
//}]
//}
,{
	title : '修改大额流失',
	hideTitle : true,
	type : 'form',
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	            {name : 'ID',hidden : false},
	            {name : 'CALL_ID',hidden : false},
	            {name : 'CUST_ID',hidden : false},
	            {name : 'REMIND_DATE',text  : '汇款时间',hidden : false,xtype:'datefield'},
	            {name : 'REMIND_AMOUNT',text  : '汇款金额(元)',renderer:money('0,000.00'),allowBlank: false},
				{name : 'RECEIVER',text  : '收款人',hidden : false,allowBlank: false},
				{name : 'RECEIVE_BANK',text  : '收款行',hidden : false,allowBlank: false},
				{name : 'BACKFLOW_DATE',text  : '预计回流时间',hidden : false,xtype:'datefield'},
				{name : 'BACKFLOW_AMOUNT',text  : '预计回流金额(元)',hidden : false,renderer:money('0,000.00')},
				{name : 'REMIND_CASE_STAGE',text  : '是否结案',hidden : false,translateType:'CALLREPORT_CASE_STAGE',allowBlank: false}
				],
			fn : function(ID,CALL_ID,CUST_ID,REMIND_DATE,REMIND_AMOUNT,RECEIVER,RECEIVE_BANK,BACKFLOW_DATE,BACKFLOW_AMOUNT,REMIND_CASE_STAGE){
				REMIND_DATE.readOnly = true;
				REMIND_DATE.cls = 'x-readOnly';
				REMIND_AMOUNT.readOnly = true;
				REMIND_AMOUNT.cls = 'x-readOnly';
				RECEIVER.readOnly = true;
				RECEIVER.cls = 'x-readOnly';
				RECEIVE_BANK.readOnly = true;
				RECEIVE_BANK.cls = 'x-readOnly';
				return [ID,CALL_ID,CUST_ID,REMIND_DATE,REMIND_AMOUNT,RECEIVER,RECEIVE_BANK,BACKFLOW_DATE,BACKFLOW_AMOUNT,REMIND_CASE_STAGE];
			}
//	},{
//		columnCount :0.95 ,
//		fields : [{name : 'REMIND_REASON',text  : '汇款原因',hidden : true,translateType:'CALLREPORT_REMIND_REASON',allowBlank: false,
//			listeners:{
//	    		select:function(combo,record){
//	    			var v = this.getValue();
//	    			if(v=='5'){//5其他原因
//	    				getCurrentView().contentPanel.getForm().findField('REMIND_REMARK').setVisible(true);
//	    			}else{
//	    				getCurrentView().contentPanel.getForm().findField('REMIND_REMARK').setVisible(false);
//	    			}
//				}
//    	}}],
//		fn : function(REMIND_REASON){
//			return [REMIND_REASON];
//		}
   },{
		columnCount :0.95 ,
		fields : [{name : 'REMIND_REASON',text  : '汇款原因',hidden : true,translateType:'CALLREPORT_REMIND_REASON',allowBlank: false,
			listeners:{
	    		select:function(combo,record){
	    			var v = this.getValue();
	    			if(v=='5'){//5其他原因
	    				getCurrentView().contentPanel.getForm().findField('REMIND_REMARK').setVisible(true);
	    			}else{
	    				getCurrentView().contentPanel.getForm().findField('REMIND_REMARK').setVisible(false);
	    			}
				}
    	}}],
		fn : function(REMIND_REASON){
			return [REMIND_REASON];
		}
   },{
		columnCount :0.95 ,
		fields : [{name : 'REMIND_REMARK',text  : '备注',hidden : true,xtype:'textarea'}],
		fn : function(REMIND_REMARK){
			return [REMIND_REMARK];
		}
   }],
formButtons:[{
	text : '保存',
	//保存数据					 
	fn : function(formPanel,baseform){						
		if(!baseform.isValid())
			{
				Ext.Msg.alert('提示','请输入完整！');
				return false;
			}
		    var commintData = translateDataKey(baseform.getFieldValues(),_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('正在提交数据，请稍等...','提示');
		    Ext.Ajax.request({
 				url : basepath + '/ocrmFSeCallreportRemind.json',
 				method : 'POST',
 				params : commintData,
				success : function() {
					Ext.Msg.alert('提示',"操作成功!");
					showEditView();
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
			 		if(resultArray == 403) {
		           		Ext.Msg.alert('提示', response.responseText);
			 		}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
	 				}
				}
			});
		}
},{
	text : '返回',
	fn : function(formPanel){
		showEditView();
	}
}]
}
,{
	title : '联系内容详情',
	hideTitle : true,
	type : 'form',
	suspendWidth: 900,
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	          {name: 'CUST_ID',text:'客户编号',gridField:true,readOnly:true,cls:'x-readOnly'},
	  		  {name: 'CUST_TYPE',text :'客户类型',translateType:'CALLREPORT_CUST_TYPE',readOnly:true,cls:'x-readOnly'},
		      {name: 'CUST_NAME',text:'客户名称',readOnly:true,cls:'x-readOnly'},
		      {name: 'LINK_PHONE',text:'联系电话',resutlWidth:100,readOnly:true,cls:'x-readOnly'},
		      {name: 'CUST_CHANNEL',text:'客户渠道',resutlWidth:80,translateType:'CALLREPORT_CUST_CHANNEL',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'VISIT_DATE',text :'拜访日期',format:'Y-m-d',xtype:'datefield',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'BEGIN_DATE',text :'起始时间',format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'17:45',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'END_DATE',text :'结束时间',format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'18:00',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'VISIT_WAY',text:'拜访方式',translateType:'CALLREPORT_VISIT_TYPE',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'RECOMMEND_CUST_ID',text : 'MGM推荐客户',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'NEXT_VISIT_DATE',text:'下次拜访时间',resutlWidth:60,format:'Y-m-d',xtype:'datefield',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'NEXT_VISIT_WAY',text:'下次拜访方式',resutlWidth:60,translateType:'CALLREPORT_VISIT_TYPE',readOnly:true,cls:'x-readOnly'}
	  		    
				],
			fn : function(CUST_ID,CUST_TYPE,CUST_NAME,LINK_PHONE,CUST_CHANNEL,VISIT_DATE,BEGIN_DATE,END_DATE,VISIT_WAY,RECOMMEND_CUST_ID,
					NEXT_VISIT_DATE,NEXT_VISIT_WAY){
				return [CUST_ID,CUST_TYPE,CUST_NAME,LINK_PHONE,CUST_CHANNEL,VISIT_DATE,BEGIN_DATE,END_DATE,VISIT_WAY,RECOMMEND_CUST_ID,
						NEXT_VISIT_DATE,NEXT_VISIT_WAY];
			}
	},{
		columnCount : 0.95,
		fields : [{name: 'VISIT_CONTENT', text : '访谈结果',translateType:'INTERVIEW_RESULTS',readOnly:true,cls:'x-readOnly'}],
//			[{name:'VISIT_CONTENT',text:'访谈内容',readOnly:true,cls:'x-readOnly',xtype:'textarea'}],
				fn : function(VISIT_CONTENT){
					return [VISIT_CONTENT];
				}
	},{
		columnCount : 0.95,
		fields : [{name:'MKT_BAK_NOTE',text:'访谈内容',xtype:'textarea',readOnly:true,cls:'x-readOnly'}],
				fn : function(MKT_BAK_NOTE){
					return [MKT_BAK_NOTE];
				}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [detailPanel_SJ];//商机信息
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [detailPanel_DQ];//到期通知信息
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [detailPanel_LS];//大额流失
		}
	}],
formButtons:[{
	text : '返回',
	fn : function(grid){
		showEditView();
	}
}]
},{
	title : '联系内容详情_新增面板',
	hideTitle : true,
	type : 'form',
	suspendWidth: 900,
	autoLoadSeleted : true,
	frame : true,
	groups : [{
	columnCount : 2 ,
	fields : [	
	          {name: 'CUST_ID',text:'客户编号',gridField:true,readOnly:true,cls:'x-readOnly'},
	  		  {name: 'CUST_TYPE',text :'客户类型',translateType:'CALLREPORT_CUST_TYPE',readOnly:true,cls:'x-readOnly'},
		      {name: 'CUST_NAME',text:'客户名称',readOnly:true,cls:'x-readOnly'},
		      {name: 'LINK_PHONE',text:'联系电话',resutlWidth:100,readOnly:true,cls:'x-readOnly'},
		      {name: 'CUST_CHANNEL',text:'客户渠道',resutlWidth:80,translateType:'CALLREPORT_CUST_CHANNEL',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'VISIT_DATE',text :'拜访日期',format:'Y-m-d',xtype:'datefield',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'BEGIN_DATE',text :'起始时间',format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'17:45',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'END_DATE',text :'结束时间',format:'H:i',xtype:'timefield',minValue:'09:00',maxValue:'18:00',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'VISIT_WAY',text:'拜访方式',translateType:'CALLREPORT_VISIT_TYPE',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'RECOMMEND_CUST_ID',text : 'MGM推荐客户',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'NEXT_VISIT_DATE',text:'下次拜访时间',resutlWidth:60,format:'Y-m-d',xtype:'datefield',readOnly:true,cls:'x-readOnly'},
	  		  {name: 'NEXT_VISIT_WAY',text:'下次拜访方式',resutlWidth:60,translateType:'CALLREPORT_VISIT_TYPE',readOnly:true,cls:'x-readOnly'}
	  		    
				],
			fn : function(CUST_ID,CUST_TYPE,CUST_NAME,LINK_PHONE,CUST_CHANNEL,VISIT_DATE,BEGIN_DATE,END_DATE,VISIT_WAY,RECOMMEND_CUST_ID,
					NEXT_VISIT_DATE,NEXT_VISIT_WAY){
				return [CUST_ID,CUST_TYPE,CUST_NAME,LINK_PHONE,CUST_CHANNEL,VISIT_DATE,BEGIN_DATE,END_DATE,VISIT_WAY,RECOMMEND_CUST_ID,
						NEXT_VISIT_DATE,NEXT_VISIT_WAY];
			}
	},{
		columnCount : 0.95,
		fields : [{name: 'VISIT_CONTENT', text : '访谈结果',translateType:'INTERVIEW_RESULTS',readOnly:true,cls:'x-readOnly'}],
//			[{name:'VISIT_CONTENT',text:'访谈内容',readOnly:true,cls:'x-readOnly',xtype:'textarea'}],
				fn : function(VISIT_CONTENT){
					return [VISIT_CONTENT];
				}
	},{
		columnCount : 0.95,
		fields : [{name:'MKT_BAK_NOTE',text:'访谈内容',xtype:'textarea',readOnly:true,cls:'x-readOnly'}],
				fn : function(MKT_BAK_NOTE){
					return [MKT_BAK_NOTE];
				}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [detailPanel_SJ2];//商机信息
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [detailPanel_DQ2];//到期通知信息
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [detailPanel_LS2];//大额流失
		}
	}],
formButtons:[{
	text : '返回',
	fn : function(grid){
		showCreateView();
		//加载商机面板
		createStore_sj.load({params:{callId:callIds}});
		//回写新增面板
		Ext.Ajax.request({
				url : basepath + '/ocrmFSeCallreport.json?callIds='+callIds,
				method : 'GET',
			success : function(response) {
				var resultArray = Ext.util.JSON.decode(response.responseText)
				getCreateView().contentPanel.getForm().findField('CALL_ID').setValue(resultArray.json.data[0].CALL_ID);
				getCreateView().contentPanel.getForm().findField('CUST_ID').setValue(resultArray.json.data[0].CUST_ID);
				getCreateView().contentPanel.getForm().findField('CUST_NAME').setValue(resultArray.json.data[0].CUST_NAME);
				getCreateView().contentPanel.getForm().findField('CUST_TYPE').setValue(resultArray.json.data[0].CUST_TYPE);
				getCreateView().contentPanel.getForm().findField('VISIT_DATE').setValue(resultArray.json.data[0].VISIT_DATE);
				getCreateView().contentPanel.getForm().findField('BEGIN_DATE').setValue(resultArray.json.data[0].BEGIN_DATE);
				getCreateView().contentPanel.getForm().findField('END_DATE').setValue(resultArray.json.data[0].END_DATE);
				getCreateView().contentPanel.getForm().findField('LINK_PHONE').setValue(resultArray.json.data[0].LINK_PHONE);
				getCreateView().contentPanel.getForm().findField('VISIT_WAY').setValue(resultArray.json.data[0].VISIT_WAY);
				getCreateView().contentPanel.getForm().findField('CUST_CHANNEL').setValue(resultArray.json.data[0].CUST_CHANNEL);
				getCreateView().contentPanel.getForm().findField('RECOMMEND_CUST_ID').setValue(resultArray.json.data[0].RECOMMEND_CUST_ID);
				getCreateView().contentPanel.getForm().findField('VISIT_CONTENT').setValue(resultArray.json.data[0].VISIT_CONTENT);
				getCreateView().contentPanel.getForm().findField('NEXT_VISIT_DATE').setValue(resultArray.json.data[0].NEXT_VISIT_DATE);
				getCreateView().contentPanel.getForm().findField('NEXT_VISIT_WAY').setValue(resultArray.json.data[0].NEXT_VISIT_WAY);
				getCreateView().contentPanel.getForm().findField('MKT_BAK_NOTE').setValue(resultArray.json.data[0].MKT_BAK_NOTE);
				getCreateView().contentPanel.getForm().findField('IDENT_TYPE').setValue(resultArray.json.data[0].IDENT_TYPE);
				getCreateView().contentPanel.getForm().findField('IDENT_NO').setValue(resultArray.json.data[0].IDENT_NO);
			}
		});
		if(cID.CUST_ID != undefined){
			var custId = cID.CUST_ID;
			Ext.getCmp("addSJ").show();
//			Ext.getCmp("addLS").show();
			Ext.getCmp("addSJ_Edits").show();
			Ext.getCmp("addSJ_Del").show();
			Ext.getCmp("addSJ_Next").show();
			Ext.getCmp("addLS_Edits").show();
			Ext.getCmp("addDQ_up").show();
			Ext.getCmp("addDQ_de").show();
			//加载商机面板
			createStore_sj.load({params:{callId:callIds}});
			//到期提醒信息
			createStore_dq.load({params:{callReport_custId:custId}});
			//大额流失
			createStore_ls.load({params:{callReport_custId:custId}});
			//联系内容概览
			createStore_asn.load({params:{callReport_custId:custId}});
		}
	}
}]
}];
/*
 * 处理其它页面跳转过来时参数处理
 */
var fnCondisDecide = function() {
	var parms = '';
	if (window.location.search) {
		parms = Ext.urlDecode(window.location.search);
	}
	var condis = parms['?condis']?parms['?condis']:parms['condis'];
	if (typeof condis != "undefined") {
		getConditionField('CUST_ID').setValue(condis);
		setSearchParams({CUST_ID:condis});
	}else{
		setSearchParams({});
	}
};

var afterinit = function(){
	fnCondisDecide();
};