  	var pid='';//定义custId传递参数
    
	 var approveHistoryRecord = Ext.data.Record.create(
	    		[
	    		 {name:'actiCheckId',mapping:'ACTI_CHECK_ID'},
	    		 {name:'checkDate',mapping:'CHECK_DATE'},
	    		 {name:'checkStatus',mapping:'CHECK_STATUS'},
	    		 {name:'checkIdea',mapping:'CHECK_IDEA'},
	    		 {name:'checkUserName',mapping:'CHECK_USER_NAME'},
	    		 {name:'appUserName',mapping:'APP_USER_NAME'},
	    		 {name:'appReason',mapping:'APP_REASON'},
	    		 {name:'checkUser',mapping:'CHECK_USER'},
	    		 {name:'mktActiId',mapping:'MKT_ACTI_ID'}
	    		 ]
	    );
var approveHistoryReader = new Ext.data.JsonReader(//读取jsonReader
 		{
 			successProperty : 'success',
 			idProperty : 'ID',
 			totalProperty : 'json.count',
 			root:'json.data'
 		},approveHistoryRecord
	);
	var approveHistoryStore = new Ext.data.Store({//产品对照关系store
     restful : true, 
     proxy : new Ext.data.HttpProxy({ 
     	url:basepath+'/mktapphistoryqueryaction.json'
     }),
		reader:approveHistoryReader
		
});
	 var approveHistoryColumns = new Ext.grid.ColumnModel(
				{
					columns:[
					{ header:'审批单编号',dataIndex:'actiCheckId',sortable:true,hidden:true,width:100},
					{ header:'申请人',dataIndex:'appUserName',sortable:true,width:100},
					{ header:'申请理由',dataIndex:'appReason',sortable:true,width:150},
					{ header:'审批人',dataIndex:'checkUserName',sortable:true,width:100},
					{ header:'审批状态',dataIndex:'checkStatus',width:80,sortable:true,renderer : function(value, p, r) {
						if (value == "2")
							return "<span style='color:blue;'>待审批</span>";
						else if (value == "1")
							return "<span style='color:green;'>审批通过</span>";
						else if (value == "0")
							return "<span style='color:red;'>审批拒绝</span>";
					}},
					{ header:'审批意见',dataIndex:'checkIdea',width:150,sortable:true},
					{ header:'审批时间',dataIndex:'checkDate',width:80,sortable:true}
					]
				}
	 );
	 var approveHistoryGrid = new Ext.grid.EditorGridPanel({			
			store:approveHistoryStore, 
			frame:true,
			height : 200,
			cm:approveHistoryColumns,
			region:'center',
			viewConfig : {
		   	},
		   	loadMask : {
			  msg : '正在加载表格数据,请稍等...'
		   	}
	 });
	
