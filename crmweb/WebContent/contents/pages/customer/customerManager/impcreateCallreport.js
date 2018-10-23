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
    }],
	store : createStore_sj,
	frame : true,
	sm:createSm,
	cm : createCm,
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
    tbar : [],
	store : createStore_dq,
	frame : true,
	sm:createSm_dq,
	cm : createCm_dq,
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
//	 tbar : [{
//	    	text : '新增',
//			id:'addLS',
//			 handler:function() {
//				showCustomerViewByTitle('新增大额流失');
//			}
//	    }],
	store : createStore_ls,
	frame : true,
	sm:createSm_ls,
	cm : createCm_ls,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

//====================联系内容==============================
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
				url : basepath + '/ocrmFSeCallreport.json?showAll=showAll'
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
			    {name:'LAST_UPDATE_TM'}
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
        	feedback_asn('联系内容详情',createPanel_SAN);
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
}
