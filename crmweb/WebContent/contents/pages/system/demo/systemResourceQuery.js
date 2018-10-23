Ext.onReady(function() {


	var custContrastRecord = Ext.data.Record.create(
			[
			 {name:'remindType',mapping:'REMIND_TYPE'},
			 {name:'remindText',mapping:'REMIND_TEXT'},
			 {name:'remindTime',mapping:'REMIND_TIME'}
			 ]
	);

	var custContrastStore = new Ext.data.Store({
		reader : new Ext.data.JsonReader({
										root : 'rows',
										totalProperty : 'num'
										 }, 
										 custContrastRecord
		)
	});

	var memberData = {
			TOTALCOUNT : 3,
			rows : [ {
				"REMIND_TYPE" : "CPU使用率报警",
				"REMIND_TEXT" : '<span style="color:red;">CPU使用率超过90%请检查</span>',
				"REMIND_TIME" : "2013-03-12 12:35:20"
			}, {
				"REMIND_TYPE" : "硬盘使用率报警",
				"REMIND_TEXT" : '<span style="color:red;">硬盘资源超过总空间的60%请检查</span>',
				"REMIND_TIME" : "2013-02-28 10:13:15"
			}]
		};
	custContrastStore.loadData(memberData);




	 /*************************************列模型***********************************************/
	 // 定义自动当前页行号
	 var rownum = new Ext.grid.RowNumberer( {
	     header : 'No.',
	     width : 28
	 });
	 var sm = new Ext.grid.CheckboxSelectionModel();
	 var custContrastColumns = new Ext.grid.ColumnModel([rownum,
	                                     				{ header:'监控类型',dataIndex:'remindType',sortable:true,width:150},
	                                     				{ header:'报警时间',dataIndex:'remindTime',sortable:true,width:150},
	                                     				{ header:'报警提示',dataIndex:'remindText',sortable:true,width:350}
	                                     				]
	                                      );
	 var custContrastGrid = new Ext.grid.EditorGridPanel({			
			store:custContrastStore, 
			frame:true,
			height : 200,
			cm:custContrastColumns,
			region:'center',
//			sm:sm,
	        viewConfig : {// 不产横向生滚动条, 各列自动扩展自动压缩, 适用于列数比较少的情况
		    },
		    loadMask : {
			  msg : '正在加载表格数据,请稍等...'
		    }
	 });
		
		var FormatCnMoney = function(v) {
			return Ext.util.Format.number(v, '0,000.00');
		};
		
		var date= document.documentElement.clientHeight/1.8;

		
		var viewport = new Ext.Viewport({
			layout : 'fit',
			autoScroll:true,
			items:[{
	            xtype:'portal',
	            id:'center',
	            region:'center',
			items: [{
	              columnWidth:1,
	              collapsible:true,
	              items:[custContrastGrid]
	              },
	              {
	              columnWidth:.25,
	              collapsible:true,
	              items:[{
	                    title: '内存使用率监控',
	                    collapsible:true,
//	                    layout:'fit',
	                    style:'padding:0px 0px 0px 0px',
	                    height:date,
	                    width : (document.body.clientWidth-180)/2.1,
	                    html:'<iframe id="contentFrame3" name="content5" height="220" frameborder="no" width="100%" src=\"../../customer/customerManager/customerBaseInformation/fusionchartsDemo/sysDemo/NC.html\" "/> scrolling="no"> </iframe>'
	                }]
	              },{
	                  columnWidth:.25,
	                  collapsible:true,
	                  items:[{
	                        title: 'I/O使用率监控',
	                        collapsible:true,
//	                        layout:'fit',
	                        style:'padding:0px 0px 0px 0px',
	                        height:date,
	                        width : (document.body.clientWidth-180)/2.1,
	                        html:'<iframe id="contentFrame3" name="content5" height="220" frameborder="no" width="100%" src=\"../../customer/customerManager/customerBaseInformation/fusionchartsDemo/sysDemo/IO.html\" "/> scrolling="no"> </iframe>'
	                    }]
	              },{
	                  columnWidth:.25,
	                  collapsible:true,
	                  items:[{
	                        title: 'CPU使用率监控',
	                        collapsible:true,
//	                        layout:'fit',
	                        style:'padding:0px 0px 0px 0px',
	                        height:date,
	                        width : (document.body.clientWidth-180)/2.1,
	                        html:'<iframe id="contentFrame3" name="content5" height="220" frameborder="no" width="100%" src=\"../../customer/customerManager/customerBaseInformation/fusionchartsDemo/sysDemo/CPU.html\" "/> scrolling="no"> </iframe>'
	                    }]
	               },{
	                   columnWidth:.25,
	                   collapsible:true,
	                   items:[{
	                         title: '硬盘剩余空间监控',
	                         collapsible:true,
//	                         layout:'fit',
	                         style:'padding:0px 0px 0px 0px',
	                         height:date,
	                         width : (document.body.clientWidth-180)/2.1,
	                         html:'<iframe id="contentFrame3" name="content5" height="220" frameborder="no" width="100%" src=\"../../customer/customerManager/customerBaseInformation/fusionchartsDemo/sysDemo/DISK.html\" "/> scrolling="no"> </iframe>'
	                     }]
	                }] 
			}]
		});
		

	
});