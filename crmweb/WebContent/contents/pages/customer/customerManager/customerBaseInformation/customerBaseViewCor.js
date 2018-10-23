/*
 * 主页面
 * 创建人：KM
 * 时间：2012-06-27
 */
	Ext.onReady(function(){
		 var myData = [
		               ['文博贸易有限公司', '11200302-Z','贸易类','小商品贸易',4355643.65,139572.86,1000,'私企','A']
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
		                  {name: 'a9'}
		               ]
		           });
		            cusStore.loadData(myData);
		             var customergrid = new Ext.grid.GridPanel({
		             	layout:'fit',
		               store: cusStore,
		               viewConfig:{
						   forceFit:true,
						   autoScroll:true
						},
		               columns: [
		                         {header:'客户名称',width:100,dataIndex:'a1'},
		                         {header:'组织机构代码',width:100,dataIndex:'a2'},
		                         {header:'行业一级分类',width:100,dataIndex:'a3'},
		                         {header:'行业二级分类',width:100,dataIndex:'a4'},
		                         {header:'注册资本',width:100,dataIndex:'a5'},
		                         {header:'年营业额',width:100,dataIndex:'a6'},
		                         {header:'员工人数',width:100,dataIndex:'a7'},
		                         {header:'企业性质',width:100,dataIndex:'a8'},
		                         {header:'客户分类',width:100,dataIndex:'a9'}
		               ],
		               stripeRows: true,
		               width: '100%',
		               height:200
		           });


   var date= document.documentElement.clientHeight/1.8;
	    
		//主页面视图面板
	    var viewport = new Ext.Viewport({
	        layout:'border',
	       // border:false,
	        items:[{
	            xtype:'portal',
	            id:'center',
	            region:'center',
	            //margins:'5 5 5 5',
	            items:[
	                   {

	   	                columnWidth:1,
	   	                autoHeight:true,
	   	                //layout:'fit',
	   	                border:false,
	   	                items:[{
	   	                    title: '客户概览信息',
	   	                    layout:'fit',
	   	                    style:'padding:0px 0px 0px 0px',
	   	                    columnWidth:1,
	   	                    height:110,
	   	                	collapsible:true,
	   	                    items:[customergrid]
	   	                }]
	   	                
	   	              
	                   },
	            	{
	                columnWidth:.5,
	                border:false,
	                autoHeight:true,
	                id:'cus',
	                //layout:'fit',
	                items:[{
	                    title: '客户贡献度趋势图',
	                    collapsible:true,
	                    layout:'fit',
	                    style:'padding:0px 0px 0px 0px',
	                    height:date,
	                    html:'<iframe id="contentFrame" name="content" height="220" frameborder="no" width="100%" src=\"fusionchartsDemo/lookgrade/b.html\" "/> scrolling="no"> </iframe>'
	                },{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '客户持有产品统计',
	                    height:date,
               		    html:'<iframe id="contentFrame4" name="content4" height="220" frameborder="no" width="100%" src=\"fusionchartsDemo/DragableCharts/DragCol2.html\" "/> scrolling="no"> </iframe>'
	                },{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '客户贷款业务趋势图',
	                    height:date,
               		    html:'<iframe id="contentFrame2" name="content2" height="220" frameborder="no" width="100%" src=\"fusionchartsDemo/lookgrade/b2.html\" "/> scrolling="no"> </iframe>'
	                }]
	            },{
	                columnWidth:.5,
	                autoHeight:true,
	                //layout:'fit',
	                border:false,
	                items:[{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '客户存贷款业务量',
	                    height:date,
               		    html:'<iframe id="contentFrame3" name="content3" height="220" frameborder="no" width="100%" src=\"fusionchartsDemo/DragableCharts/DragCol1.html\" "/> scrolling="no"> </iframe>'
	                },{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '客户存款业务趋势图',
	                    height:date,
               		    html:'<iframe id="contentFrame1" name="content1" height="220" frameborder="no" width="100%" src=\"fusionchartsDemo/lookgrade/b1.html\" "/> scrolling="no"> </iframe>'
	                },{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '分类存款占比图',
	                    height:date,
               		    html:'<iframe id="contentFrame5" name="content5" height="220" frameborder="no" width="100%" src=\"fusionchartsDemo/contribute/a2.html\" "/> scrolling="no"> </iframe>'
	                }]
	                
	            }] 
	        }]
	    });
	    
	   
	
	
});