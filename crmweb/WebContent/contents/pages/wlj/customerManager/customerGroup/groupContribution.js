/**
 * 客户群视图   客户贡献度信息
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
    '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
    '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
    ]);
    
	var groupId=_busiId;//获取客户群ID
    
    var url = basepath+'/custContriInfoQuery.json?groupId='+groupId;
    var needCondition = false;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = true;
    var lookupTypes=[
                     'REMIND_TYPE'
    ]
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	var fields = [
	  {name:'ID',hidden:true},
	  {name:'CUST_ID',text:'客户号'},
	  {name:'CUST_NAME',text:'客户名称'},
	  {name:'CONTRIBUTION_CUST',text:'综合贡献度',searchField: false,viewFn:money('0,000.00')},
	  {name:'ETL_DATE',text:'统计时间',searchField: false}
      ];
	
	var tbar = [
	new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('custContriInfo_export'),
        formPanel : 'searchCondition',
        url :basepath+'/custContriInfoQuery.json?groupId='+groupId
    })];
	
	
	var customerView=[{
		title:'综合贡献度明细',
		//hideTitle:JsContext.checkGrant('allContriInfo'),
		type:'grid',
		isCsm:false,
		url:basepath + '/custContriDetailInfoQuery.json',
		frame : true,
		fields:{
			fields:[  
				{name:'CUST_ID',hidden:true},
	          	{name:'CONTRI_TYPE',text:'贡献度类型',width:260},
	          	{name:'CONTRI',text:'贡献度（折人民币）',width:260,renderer:money('0,000.00')},
			  	{name:'ETL_DATE',text:'统计时间',width:260}
        ]},
		gridButtons:[{
			text:'产品贡献度明细',
			fn:function(grid){
				var selectLength = grid.getSelectionModel().getSelections().length;
				 var selectRe= grid.getSelectionModel().getSelections()[0];
				 if (selectLength < 1) {
						Ext.Msg.alert('提示', '请先选择一条记录！');
						return false;
				 } else if (selectLength > 1) {
						Ext.Msg.alert('提示', '只能选择一条记录！');
						return false;
				 }
				 contriType=selectRe.data.CONTRI_TYPE;
				 showCustomerViewByTitle('产品贡献度明细');
			}
		}]                
	},{
		title:'产品贡献度明细',
		type:'grid',
		isCsm:false,
		hideTitle:true,
		url:basepath + '/custProdContriDetailInfoQuery.json?',
		frame : true,
		fields:{
			fields:[  
	          	{name:'PROD_NAME',text:'产品名',width:260},
	          	{name:'AMOUNT',text:'贡献度（折人民币）',width:260,renderer:money('0,000.00')},
			  	{name:'ETL_DATE',text:'统计时间',width:260}
        ]}
	}];
	
	var beforeviewshow=function(view){
		if(view._defaultTitle=='综合贡献度明细'){
			if(!getSelectedData()){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			};
			var etlDate=getSelectedData().data.ETL_DATE;
			var custId = getSelectedData().data.CUST_ID;
			view.setParameters ({
				custId : custId,
				etlDate:etlDate
    		});
		}
		
		if(view._defaultTitle=='产品贡献度明细'){
			var etlDate=getCustomerViewByTitle("综合贡献度明细").grid.getSelectionModel().getSelections()[0].data.ETL_DATE;
			var contriType=getCustomerViewByTitle("综合贡献度明细").grid.getSelectionModel().getSelections()[0].data.CONTRI_TYPE;
			var custId = getCustomerViewByTitle("综合贡献度明细").grid.getSelectionModel().getSelections()[0].data.CUSTOM_ID;
			view.setParameters ({
				custId : custId,
				etlDate: etlDate,
				contriType:contriType
    		});
		}
		
	};
	
	
	
