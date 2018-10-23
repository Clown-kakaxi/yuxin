/*
 * 主页面
 * 创建人：KM
 * 时间：2012-06-27
 */
	Ext.onReady(function(){
		 var myData = [
		               ['王一平', '身份证','380612198009094736','男',32,'IT高管','XX软件公司','部门经理','B']
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
		                         {header:'证件类型',width:100,dataIndex:'a2'},
		                         {header:'证件号码',width:150,dataIndex:'a3'},
		                         {header:'性别',width:100,dataIndex:'a4'},
		                         {header:'年龄',width:100,dataIndex:'a5'},
		                         {header:'职业',width:100,dataIndex:'a6'},
		                         {header:'工作单位',width:150,dataIndex:'a7'},
		                         {header:'职务',width:100,dataIndex:'a8'},
		                         {header:'客户级别',width:100,dataIndex:'a9'}
		               ],
		               stripeRows: true,
		               width: '100%',
		               height:200
		           });


   var date= 260;
	    
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
	   	                columnWidth: 1,
		                autoHeight:true,
		                //layout:'fit',
		                border:false,
		                items:[{
		                    title: '客户概览信息',
		                    layout:'fit',
		                    style:'padding:5px 5px 5px 5px',
		                    columnWidth: 1,
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
	                    html:'<iframe id="contentFrame" name="content" height="250" frameborder="no" width="100%" src=\"fusionchartsDemo/lookgrade/b.html\" "/> scrolling="no"> </iframe>'
	                },{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '客户产品占比统计',
	                    height:date,
               		    html:'<iframe id="contentFrame4" name="content4" height="250" frameborder="no" width="100%" src=\"fusionchartsDemo/contribute/a.html\" "/> scrolling="no"> </iframe>'
	                },{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '客户积分趋势图',
	                    height:date,
               		    html:'<iframe id="contentFrame2" name="content2" height="250" frameborder="no" width="100%" src=\"fusionchartsDemo/lookgrade/p2.html\" "/> scrolling="no"> </iframe>'
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
	                    title: '客户AUM值趋势图',
	                    height:date,
               		    html:'<iframe id="contentFrame1" name="content1" height="250" frameborder="no" width="100%" src=\"fusionchartsDemo/lookgrade/p1.html\" "/> scrolling="no"> </iframe>'
	                },{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '客户存贷款业务量',
	                    height:date,
               		    html:'<iframe id="contentFrame3" name="content3" height="250" frameborder="no" width="100%" src=\"fusionchartsDemo/DragableCharts/DragCol1.html\" "/> scrolling="no"> </iframe>'
	                }/*,{
	                    title: '客户概览信息',
	                    layout:'fit',
	                    style:'padding:0px 0px 0px 0px',
	                    columnWidth:.5,
	                    height:date,
	                	collapsible:true,
	                    items:[customergrid]
	                }*/,{
	                	layout:'fit',
	                	style:'padding:0px 0px 0px 0px',
	                	collapsible:true,
	                    title: '客户存贷款趋势图',
	                    height:date,
               		    html:'<iframe id="contentFrame5" name="content1" height="250" frameborder="no" width="100%" src=\"fusionchartsDemo/lookgrade/p3.html\" "/> scrolling="no"> </iframe>'
	                }]
	                
	            }] 
	        }]
	    });
	    
	   
	
	
});