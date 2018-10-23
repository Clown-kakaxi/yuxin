Ext.onReady(function() { 
   
	/*得到昨天的日期*/
	var today =  new Date();
	var year1 = today.getYear();
	var mon1 = today.getMonth()+1;
	var date1 = today.getDate(); 
	
	var lastDate = new Date(year1,mon1,date1-1);
	
	var year = lastDate.getYear();
	var month = lastDate.getMonth();
	var day = lastDate.getDate();
	var monthStr =month+""; 
	var dayStr=""+day ;
	if(day<10){
		dayStr=  "0"+dayStr; 
	}
	if(month<10){
		monthStr = "0"+month;
	}
	
	var lastDay = year+"-"+monthStr+"-"+dayStr;	 	
    var record2 = Ext.data.Record.create([ 
  		{name: 'companyName',mapping:'cust_zh_name'},
     	{name: 'companyCode',mapping:'cust_zzdm'},
     	
     	{name: 'dep_bal'},
     	{name: 'dep_avg'},
     	{name: 'dep_qua'},  
     	{name: 'dep_year'},
     	{name: 'lon_bal'},
     	{name: 'lon_avg'}, 
     	{name: 'lon_qua'},
     	{name: 'lon_year'},
     	{name: 'dep_bal_sum'},
     	{name: 'lon_bal_sum'},
     	{name: 'dep_cy_sum'},
     	{name: 'lon_cy_sum'},  
     	{name: 'dep_cq_sum'},
     	{name: 'lon_cq_sum'},
     	{name: 'dep_cm_sum'}, 
     	{name: 'lon_cm_sum'},
     	{name: 'dep_cd_sum'},
     	{name: 'lon_cd_sum'},
     	{name: 'ccon'},
     	{name: 'dcon'},     	

     	{name: 'desc'},
     	{name: '_id', type: 'int'},
     	{name: '_level', type: 'int'},
     	{name: '_parent', type: 'int'},
     	{name: '_lft', type: 'int'},
     	{name: '_rgt', type: 'int'},
     	{name: '_is_leaf', type: 'bool'}

   	]);
    var store2 = new Ext.ux.maximgb.tg.NestedSetStore({
    	//	autoLoad : true,
//			reader: new Ext.data.JsonReader(
//			{
//			id: '_id',
//			root:'depAndLonTreeList'
//			}, record2),
//			proxy: new Ext.data.HttpProxy({
//				url:basepath+"/client-dep-and-lon-tree",
//				method:'GET'
//			}),
//			listeners:{
//				'load':function(){
//					store2.expandAll();					
//				}
//			}		
    	reader : new Ext.data.JsonReader({
			root : 'rows',
			totalProperty : 'num'
		 }, 
		 record2
)
    });
    
    
    
   var groupNo =  parent.document.getElementById("groupNo").value;
//    store2.load({    
//    	params:{
//    		'condition':'{"group_no":"'+groupNo+'"}'
//    	}
//    });
    
   var exportFormTemp = new Ext.form.FormPanel({
   	 id:'exportFormTemp',
   	 hidden:true,
	 items:[
	 {
	 	name:'group_no',
	 	value:groupNo,
	 	hidden:true,
	 	xtype:'textfield'
	 },{
	 	
	 	name:'crm_dt',
	 	hidden:true,
	 	xtype:'textfield',
	 	id:'crm_dt'
	 },
	 {
	 	name:'exportFlag',
	 	hidden:true,
	 	value:'YES',
	 	xtype:'textfield'
	 }	 
	 ]
   	 
   });
   
    var blocCreValAppAudPanel2 = new Ext.ux.maximgb.tg.EditorGridPanel({
      store: store2,
	  split:true,
      master_column_id : 'companyName',
      loadMask:{
      	msg:'数据正在加载,请稍后...'
      },

      tbar:[
      		'->',
			new Ext.form.Label({
				text:'统计日期:'
			}),
			{
				xtype:'datefield',
				value:lastDay,
				name:'crm_dt_temp',
				width:120,
				format:'Y-m-d',
				id:'crm_dt_temp'
    		},		    
		    {
		    	width:80,
				text : '查询',
				handler : function() {
				var crm_dt = Ext.getCmp("crm_dt_temp").getValue();
				
				if(crm_dt==null || crm_dt==""||crm_dt=="undefined"){
					
					Ext.MessageBox.alert('提示',"请选择统计日期.");
					return;
				}
				var year = crm_dt.getYear();
				var month = crm_dt.getMonth()+1	;
				var day = crm_dt.getDate();
				var monthStr = ""+month;
				var dayStr = ""+day;
				if(month<10){
					monthStr = "0"+month;
				}if(day<10){
					dayStr="0"+day;
				}
				
				var crmDay = year+"-"+monthStr+"-"+dayStr;
				
				Ext.getCmp("crm_dt").setValue(crmDay);
				
//				store2.load({
//					params:{
//						start:0,
//						limit:9999999,
//						'condition':'{"group_no":"'+groupNo+'","crm_dt":"'+crmDay+'"}'
//					}
//				});
		    }
		},
		'-',
		new Com.yucheng.bob.ExpButton({
		width:60,
		formPanel:'exportFormTemp',
		url:basepath+'/client-dep-and-lon-tree.json'
		})			
		],   	   
	  selModel:new Ext.grid.RowSelectionModel({
	  	singleSelection:true
	  }),      
      columns: 
      [
      		new Ext.grid.RowNumberer({
      			header:'NO',
      			width:30
      		}),    
      		
      		{
            id:'companyName',
            header: "客户名称", 
            sortable: true, 
            width:200,
//            hidden:true,
            dataIndex: 'companyName'
            
      		},
      		{
            id:'companyCode',
            header: "组织机构代码", 
            width: 100, 
            sortable: true, 
            dataIndex: 'companyCode'
           
        	},        
        {
            header: "存款:时点余额", 
            width: 150, 
            sortable: true, 
            dataIndex: 'dep_bal',
			align:'right',renderer: money('0,000.00')
        },
        {
            header: "存款:月均余额", 
            width: 150, 
            sortable: true, 
            dataIndex: 'dep_avg',
            align:'right',renderer: money('0,000.00')
         },
         {
            header: "存款:季均余额", 
            width: 150, 
            sortable: true,
             dataIndex: 'dep_qua',
             align:'right',renderer: money('0,000.00')
        },
 		{
            header: "存款:年均余额", 
            width: 150, 
            align:'right',
            dataIndex: 'dep_year',
            align:'right',renderer: money('0,000.00')
     	 },        
 		{
            header: "存款:较昨日",
// 			header: "存款时点较昨日",
            width: 150, 
            sortable: true,
            dataIndex: 'dep_cd_sum',
            align:'right',renderer: money('0,000.00')
        }, 
 		{
            header: "存款:较上月", 
            width: 150, 
            sortable: true,
            dataIndex: 'dep_cm_sum',
            align:'right',renderer: money('0,000.00')
        },       
        
        {dataIndex: 'dep_cq_sum',width:150,header:'存款:较上季',align:'right',renderer: money('0,000.00')},
     	{dataIndex: 'dep_cy_sum',width:150,header:'存款:较年初',align:'right',renderer: money('0,000.00')},
     	{dataIndex: 'ccon',width:150,header:'存款:贡献度(模拟利润)',align:'right',renderer: money('0,000.00')},
        {
            header: "贷款:时点余额", 
            width: 100, 
            sortable: true, 
            dataIndex: 'lon_bal',
            align:'right',renderer: money('0,000.00')
        },
      	{
            header: "贷款:月均余额", 
            width: 160, 
            sortable: true, 
            dataIndex: 'lon_avg',
            align:'right',renderer: money('0,000.00')
            
        },        
      	{
            header: "贷款:季均余额", 
            width: 160, 
            sortable: true, 
            dataIndex: 'lon_qua',
            align:'right',renderer: money('0,000.00')
            
        },
      	{
            header: "贷款:年均余额", 
            width: 160, 
            sortable: true, 
            dataIndex: 'lon_year',
            align:'right',renderer: money('0,000.00')
            
        },     
      	{
            header: "贷款:较昨日", 
            width: 160, 
            sortable: true, 
            dataIndex: 'lon_cd_sum',
            align:'right',renderer: money('0,000.00')
            
        },
      	{
            header: "贷款:较上月", 
            width: 160, 
            sortable: true, 
            dataIndex: 'lon_cm_sum',
            align:'right',renderer: money('0,000.00')
            
        },        
      	{
            header: "贷款:较上季", 
            width: 160, 
            sortable: true, 
            dataIndex: 'lon_cq_sum',
            align:'right',renderer: money('0,000.00')
            
        },   
      	{
            header: "贷款:较年初", 
            width: 160, 
            sortable: true, 
            dataIndex: 'lon_cy_sum',
            align:'right',renderer: money('0,000.00')
            
        },
      	{
            header: "贷款:贡献度(模拟利润)", 
            width: 160, 
            sortable: true, 
            dataIndex: 'dcon',
            align:'right',renderer: money('0,000.00')
        }        
      ],
      stripeRows: true, 
      id:'blocLoanAndDepositPanel',
      loadMask:{
      	msg:"数据正在加载,请稍后..."
      },
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

	var memberData= {
			TOTALCOUNT:2,
			rows:[{"cust_zh_name":"擎宇数据有限公司",
				"cust_zzdm":"3434521",
				"dep_bal":"165432",
				"dep_avg":"134556",
				"dep_qua":"345634",
				"dep_year":"23245667",
				"lon_bal":"34322",
				"lon_avg":"45662",
				"lon_qua":"663224",
				"lon_year":"8",
				"dep_bal_sum":"34672454",
				"lon_bal_sum":"34566777",
				"dep_cy_sum":"773434342",
				"lon_cy_sum":"677733456",
				"dep_cq_sum":"777733455",
				"lon_cq_sum":"457874546",
				"dep_cm_sum":"345275687",
				"lon_cm_sum":"45578932",
				"dep_cd_sum":"234566777",
				"lon_cd_sum":"13245788",
				"ccon":"345647657",
				"dcon":"3425342657",
				"desc":"无",
				"_id":"2345512",
				"_level":"1",
				"_parent":"0",
				"_lft":"1",
				"_rgt":"0",
				"_is_leaf":"1"}	,
				{"cust_zh_name":"擎宇科技有限公司",
					"cust_zzdm":"3434521",
					"dep_bal":"165432",
					"dep_avg":"134556",
					"dep_qua":"345634",
					"dep_year":"23245667",
					"lon_bal":"34322",
					"lon_avg":"45662",
					"lon_qua":"663224",
					"lon_year":"8",
					"dep_bal_sum":"34672454",
					"lon_bal_sum":"34566777",
					"dep_cy_sum":"773434342",
					"lon_cy_sum":"677733456",
					"dep_cq_sum":"777733455",
					"lon_cq_sum":"457874546",
					"dep_cm_sum":"345275687",
					"lon_cm_sum":"45578932",
					"dep_cd_sum":"234566777",
					"lon_cd_sum":"13245788",
					"ccon":"345647657",
					"dcon":"3425342657",
					"desc":"无",
					"_id":"2345513",
					"_level":"2",
					"_parent":"1",
					"_lft":"1",
					"_rgt":"0",
					"_is_leaf":"1"}	
			]
		};
	store2.loadData(memberData);

     var blocLoanAndDepositView = new Ext.Viewport({
     	layout:'fit',
     	items:[
     	    //new Ext.Panel({})
     		blocCreValAppAudPanel2
     	]
     	
     	
     });
     
     
});     
