Ext.onReady(function(){
		 var myData = [
		               ['CNJ2013021800137',' 南京维尔威思企业管理咨询有限公司', '贷款','税联贷','小额贷款','正常','某某银行某某支行','2011-02-12','2012-02-14','121,350,000','3,000,000','人民币','80,000,000','24,350,000','18,000,000'],
		               ['CNJ2013021800050','南京珈雅科技开发有限公司', '贷款','厂商银','小额贷款','正常','某某银行某某支行','2011-02-12','2012-02-14','121,350,000','3,000,000','人民币','80,000,000','24,350,000','18,000,000']
				          ];
		           var cusStore = new Ext.data.ArrayStore({
		               fields: [
		                  {name: 'a1'},
		                  {name: 'a2'},
		                  {name: 'a3'},
		                  {name: 'a4'},
		                  {name: 'a5'},
		                  {name: 'a6'},
		                  {name: 'a7'},
		                  {name: 'a8'},
		                  {name: 'a9'},
		                  {name: 'a10'},
		                  {name: 'a11'},
		                  {name: 'a12'},
		                  {name: 'a13'},
		                  {name: 'a14'}
		               ]
		           });
		            cusStore.loadData(myData);
		             var customergrid = new Ext.grid.GridPanel({
		             	layout:'fit',
		               store: cusStore,
		               viewConfig:{
						   autoScroll:true
						},
		               columns: [
		                         {header:'客户号',width:100,dataIndex:'a1'},
		                         {header:'客户名称',width:100,dataIndex:'a2'},
		                         {header:'业务类型',width:100,dataIndex:'a3'},
		                         {header:'产品名称',width:100,dataIndex:'a4'},
		                         {header:'产品分类名称',width:100,dataIndex:'a5'},
		                         {header:'状态',width:100,dataIndex:'a6'},
		                         {header:'开户网点名称',width:100,dataIndex:'a7'},
		                         {header:'开户日期',width:100,dataIndex:'a8'},
		                         {header:'业务发生额',width:100,align:'right',dataIndex:'a9'},
		                         {header:'中间业务收入',width:100,align:'right',dataIndex:'a10'},
		                         {header:'融资余额',width:100,align:'right',dataIndex:'a11'},
		                         {header:'币种',width:100,dataIndex:'a12'},
		                         {header:'贡献度',width:100,align:'right',dataIndex:'a13'},
		                         {header:'模拟利润',width:100,align:'right',dataIndex:'a14'}
		               ],
		               stripeRows: true,
		               width: '150%',
		               height:200
		           });


	// 布局模型
	var viewport = new Ext.Panel({
		 renderTo:'group_viewport_center',
		 height:document.body.scrollHeight-30,
		 layout : 'fit',
		 autoScroll:true,
		 items : [ {
			 
                columnWidth:1,
                autoHeight:true,
                //layout:'fit',
                border:false,
                items:[{
                    title: '国际业务',
//                    layout:'fit',
//                    style:'padding:0px 0px 0px 0px',
                    height:220,
                	collapsible:true,
                	autoScroll:true,
                    items:[customergrid]
                }]
            }, 
            
            {
                 autoHeight:true,
                 border:false,
                 items:[{
                	 title: '国结业务量客户规模分析',
                 	layout:'fit',
                 	collapsible:true,
            		    html:'<iframe id="contentFrame2" name="content2" height="300" frameborder="no" width="100%" src=\"custBusiAnal/fusionchartsDemo/DragableCharts/national1.html\" "/> scrolling="no"> </iframe>'
                 },{
                	title: '国结与融资量客户规模分析',
                	layout:'fit',
                	collapsible:true,
           		    html:'<iframe id="contentFrame1" name="content1" height="300" frameborder="no" width="100%" src=\"custBusiAnal/fusionchartsDemo/DragableCharts/national2.html\" "/> scrolling="no"> </iframe>'
                }]
             }]
	});    	
});