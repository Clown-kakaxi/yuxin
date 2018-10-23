/*
 * 姚亮
 * 新增客户群
 * 2011-06-09
 * */
	//客户群查询条件
	 var simple = new Ext.FormPanel({
	        frame:true,
	        region:'north',
	        id:'queryGroup',
	        bodyStyle:'padding:5px 5px 0',
	        split:true,
			height:100,
	        items: [{
	           // xtype:'fieldset',
	           // title: '查询条件',
	          // autoHeight:true,
	            items :[{ 
	            		layout:'column',
	                     items:[{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '客户群名称',
	                             name: 'first',
	                             anchor:'90%'
	                         }]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'datefield',
	                             fieldLabel: '客户群创建日期',
	                             format:'Y-m-d', //日期格式化
	                             name: 'last',
	                             anchor:'90%'
	                         }]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '客户群客户数',
	                             name: 'last1',
	                             anchor:'90%'
	                         }]
	                     },{
	                         columnWidth:.25,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '客户描述',
	                             name: 'last2',
	                             anchor:'90%'
	                         }]
	                     }
	            ]}
	            ]}],
			buttonAlign:'center',
	        buttons: [{
	            text: '查询',
	            region : 'center'
	        },{
	            text: '重置',
	            region : 'center'
	        }]
	    });
	
	//客户信息显示
	    
	    //新增客户群成员

	var cussm = new Ext.grid.CheckboxSelectionModel();
	var cusrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

 
	  var addCustomerGroup=new Ext.FormPanel({
		//layout:'fit',
	  	title : '新增客户群',
		frame:true,
		border:false,
		style:'padding:10 10 10 10',
		items : [
				{
					layout:'column',
					items : [
							{
								columnWidth : .5,
								layout : 'form',
								items : [{ 
									xtype : 'textfield',
									fieldLabel : '客户群名称',
									labelStyle:{
										width:'120px'
									},	
									Width:'100',
									value:'北京银行中关村支行客户群',
									id : 'affichenamesAdd',
									name : 'affichenamesAdd',
									anchor : '90%'
								}]
							}, 
							{
								columnWidth : .5,
								layout : 'form',
								items : [{ 
									xtype : 'textfield',
									fieldLabel : '客户群编号',
									labelStyle:{
										width:'120px'
									},	
									value:'000001',
									disabled:true,
									Width:'100',
									id : 'levelssId',
									name : 'levelsId',
									anchor : '90%'
								}]
							}]
				}
				,
				{
					layout:'form',
					items:{
						id : 'htmleditorMemo',
						name : 'htmleditorMemo',
						anchor:'80%',
	//					height:'100%',
	//					width:'80%',
						xtype:'textarea',
	//					xtype : 'htmleditor',
						fieldLabel : '客户群描述'
					}
				}
				]
	});
    
    
 // 表格工具栏
	var tbar = new Ext.Toolbar({
		items : [ {
					text : '新增客户群',
					//iconCls : 'page_findIcon',
					handler : function() {
						var win=new Ext.Window({
							layout : 'fit',
									closable : true,
									resizable : false,
									collapsible : false,
									height:300,
									width:700,
									draggable : true,
									closeAction : 'hide',
									
									//titleCollapse : false,
									modal : true, // 模态窗口 
									//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
									animCollapse : false,
//									maximizable : true,
									border : false,
									closable : true,
									animateTarget : Ext.getBody(),
									constrain : true,
									items : [addCustomerGroup],
									buttonAlign:'center',
									buttons:[{
										text:'保存',
										handler:function()
										{
											win.hide();
										}
									},{
	          				  			text: '取消',
	           				  			handler:function(){
	            			 		    win.hide();
	            						}
	       			 				}]	
							});
						win.show();
					}
				}
//				,
//				{
//					text:'查看群成员',
//					handler:function() {
//						winMemberList.show();
//					}
//
//					
//				}
				]
	});
	
	
	//客户群表格面板
	
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm,
	           {
				header : '客户群名称', // 列标题
				dataIndex : 'affichename', // 数据索引:和Store模型对应
				sortable : true,
				id:'affichename'
				//width : 300
				// 是否可排序
		    }, {
				header : '创建客户群日期',
				dataIndex : 'person',
				sortable : true,
				width : 250
			}, {
				header : '客户群客户数',
				dataIndex : 'releasebranch',
				width : 250
			}, {
				header : '客户群描述',
				dataIndex : 'inceptbranch',
				width : 350
			}
			]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
				// 获取数据的方式
				//proxy : new Ext.data.HttpProxy({
						//	url : 'gridDemo.ered?reqCode=querySfxmDatas'
						//}),
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'affichename' // Json中的属性Key值
								}, {
									name : 'person'
								}, {
									name : 'releasebranch'
								}, {
									name : 'inceptbranch'
								}
								])
			});
	
	var memberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","affichename":"北京银行中关村支行客户群000001","person":"2011-05-05","releasebranch":"100","inceptbranch":"中关村科技园支行"},
			{"rownum":"2","affichename":"北京银行中关村支行客户群000002","person":"2011-05-05","releasebranch":"100","inceptbranch":"中关村科技园支行"},
			{"rownum":"3","affichename":"北京银行中关村支行客户群000003","person":"2011-05-05","releasebranch":"100","inceptbranch":"中关村科技园支行"},
			{"rownum":"4","affichename":"北京银行中关村支行客户群000004","person":"2011-05-05","releasebranch":"100","inceptbranch":"中关村科技园支行"},
			{"rownum":"5","affichename":"北京银行中关村支行客户群000005","person":"2011-05-05","releasebranch":"100","inceptbranch":"中关村科技园支行"},
			{"rownum":"6","affichename":"北京银行中关村支行客户群000006","person":"2011-05-05","releasebranch":"100","inceptbranch":"中关村科技园支行"},
			{"rownum":"7","affichename":"北京银行中关村支行客户群000007","person":"2011-05-05","releasebranch":"100","inceptbranch":"中关村科技园支行"},
			{"rownum":"8","affichename":"北京银行中关村支行客户群000008","person":"2011-05-05","releasebranch":"100","inceptbranch":"中关村科技园支行"},
			{"rownum":"9","affichename":"北京银行中关村支行客户群000009","person":"2011-05-05","releasebranch":"100","inceptbranch":"中关村科技园支行"}
			]
		};
		store.loadData(memberData);

	var bbar = new Ext.PagingToolbar({
						pageSize : '10',
						store : store,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;',{xtype:'textfield',value:'10'} ]
					});
					
    var cusGroupGrid = new Ext.grid.GridPanel({
    	region:'center',
    	title:'客户群列表',
    	tbar:tbar,
        store: store,
		cm : cm,
		autoExpandColumn:'affichename',
		sm:sm,
		bbar : bbar,
		selModel:new Ext.grid.RowSelectionModel({
				singleSelect:true
				})
      //  stripeRows: true
      //  height:document.documentElement.clientHeight-135,
       
    });
    
    var cusGroupQueryWind = new Ext.Window({
   	height:'500',
   	width:'1200',
   	closable:true,
   	closeAction:'hide',
   	frame:true,
//   	maximized:true,
   	maximizable:true,
    layout:'border',

	items:[
//		   		region:'north',
////			   	height:100,
//			   	items:simple,
//			   	split:true
//			 },
			   simple,
			   cusGroupGrid
			 ],

   	buttonAlign:'center',
   	buttons:[
   	{
   		text:'加入该群',
   		handler:function()
   		{
   			alert('操作成功');
   			cusGroupQueryWind.hide();
   		}
   	},   	
   	{
   		text:'返 回',
   		handler:function()
   		{
   			cusGroupQueryWind.hide();
   		}
   	}
   	]
   });
