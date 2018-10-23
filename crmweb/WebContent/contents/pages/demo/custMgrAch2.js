
Ext.onReady(function(){

    Ext.QuickTips.init();

    var xg = Ext.grid;

	var custMgrAchRecord = new Ext.data.Record.create([	
	 	{name:'custmanagername',mapping:'CUST_MANAGER_NAME'},
	 	{name:'depositaverage',mapping:'DEP_AVG_BAL'},
	 	{name:'loanaverage',mapping:'LOAN_AVG_BAL'},
	 	{name:'discountaverage',mapping:'DISCOUNT_AVG_BAL'},
	 	{name:'countofcustforcor',mapping:'COM_CUST_SUM'},
	 	{name:'midbusiness',mapping:'MIDBU_INCOME'}
	 	]);
	                                                	
	var custMgrAchReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
	},custMgrAchRecord);
	                                                	
	var custMgrAchStore = new Ext.data.Store(
		{
			proxy:new Ext.data.HttpProxy({
				url:basepath+'/custmanagerachievequery2.json',
				failure : function(response){
				var resultArray = Ext.util.JSON.decode(response.status);
				if(resultArray == 403) {
					Ext.Msg.alert('提示', response.responseText);
				}
			},
			method:'GET'
			}),
			reader:custMgrAchReader
			}
		);
    
	custMgrAchStore.load();
    var grid = new xg.GridPanel({
        store: custMgrAchStore,
        columns: [
            {header: "客户经理姓名", width: 150, sortable: true, dataIndex: 'custmanagername'},
            {header: "存款日均", width: 90, sortable: true, dataIndex: 'depositaverage'},
            {header: "贷款日均", width: 90, sortable: true, dataIndex: 'loanaverage'},
            {header: "贴现日均", width:80, sortable: true, dataIndex: 'discountaverage',renderer:function(value){
            	if(value<0){
            		value = '<span style="color:red" >'+value+'</span>';
            	}
            	return value;
            }},
            {header: "对公客户数量", width: 80, sortable: true, dataIndex: 'countofcustforcor'},
            {header: "中间业务收入", width: 80, sortable: true,  dataIndex: 'midbusiness'}
        ],
        frame:true,
        width: 480,
        height: 230,
        collapsible: true,
        animCollapse: false,
        renderTo: document.body
    });
	var viewport = new Ext.Viewport({
		layout : 'fit',
		items: [grid] 
	});
});

// add in some dummy descriptions
//for(var i = 0; i < Ext.grid.dummyData.length; i++){
//    Ext.grid.dummyData[i].push('Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.<br/><br/>Aliquam commodo ullamcorper erat. Nullam vel justo in neque porttitor laoreet. Aenean lacus dui, consequat eu, adipiscing eget, nonummy non, nisi. Morbi nunc est, dignissim non, ornare sed, luctus eu, massa. Vivamus eget quam. Vivamus tincidunt diam nec urna. Curabitur velit.');
//}