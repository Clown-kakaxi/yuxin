
Ext.onReady(function(){

    Ext.QuickTips.init();

    var xg = Ext.grid;

	var custMgrAchRecord = new Ext.data.Record.create([	
	 	{name:'projectname',mapping:'ORG_ID'},
	 	{name:'current',mapping:'DEP_CURR_BAL'},
	 	{name:'today',mapping:'DEP_AVG_BAL'},
	 	{name:'change',mapping:'LOAN_CURR_BAL'},
	 	{name:'index',mapping:'LOAN_AVG_BAL'},
	 	{name:'target',mapping:'DISCOUNT_CURR_BAL'},
	 	{name:'targetrate',mapping:'DISCOUNT_AVG_BAL'}
	 	]);
	                                                	
	var custMgrAchReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
	},custMgrAchRecord);
	                                                	
	var custMgrAchStore = new Ext.data.Store(
		{
			proxy:new Ext.data.HttpProxy({
				url:basepath+'/custmanagerachievequery.json',
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
            {header: "项目名称", width: 100, sortable: true, dataIndex: 'projectname'},
            {id:'company',header: "当前业绩", width: 80, sortable: true, dataIndex: 'current'},
            {header: "上日业绩", width: 80, sortable: true, dataIndex: 'today'},
            {header: "较上日增量", width:80, sortable: true, dataIndex: 'change',renderer:function(value){
            	if(value<0){
            		value = '<span style="color:red" >'+value+'</span>';
            	}
            	return value;
            }},
            {header: "指标", width: 80, sortable: true, dataIndex: 'index'},
            {header: "业绩目标值", width: 80, sortable: true,  dataIndex: 'target'},
            {header: "指标完成率", width:80, sortable: true,  dataIndex: 'targetrate'}
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