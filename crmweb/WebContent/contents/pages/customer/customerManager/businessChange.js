Ext.onReady(function(){
Ext.QuickTips.init(); 


var vStore = new Ext.data.ArrayStore({
    fields:['myId','displayText'],
    data:[['2008','2008'],['2009','2009'],['2010','2010'],['2011','2011'],['2012','2012']]
});
var businessSearchPanel = new Ext.form.FormPanel({//查询panel

	title:'业务变化统计',
	height:100,
	width : 300,
//	buttonAlign:'center',
	labelWidth:100,//label的宽度
	labelAlign:'right',
	frame:true,
	autoScroll : true,
	region:'north',
	split:true,
	items:[
			{
				layout:'column',
				items:[
		
				 {
				 	columnWidth:.33,
				 	layout:'form',
				 	items:[
					{
					name:'year',
					anchor:'100%',
					format:'Y',
					fieldLabel:'年度查询',
					xtype : 'combo',
					editable : false,
					emptyText:'请选择',
					mode : 'local',
					triggerAction:'all',
					store:vStore,
					valueField:'myId',
					displayField:'displayText',
					anchor:'95%'	
			
					}
				 	]
				 }


				 
				]
			}
			],
	buttonAlign:'center',
	buttons:[
	{
		text:'查询'

	},{
		text:'重置',
		handler:function(){
		businessSearchPanel.getForm().reset();
           
		}
	}
	]

});
// 每页显示条数下拉选择框
var pagesize_combo = new Ext.form.ComboBox({
    name : 'pagesize',
    triggerAction : 'all',
    mode : 'local',
    store : new Ext.data.ArrayStore({
        fields : ['value', 'text'],
        data : [[100, '100条/页'], [200, '200条/页'], [500, '500条/页'], [1000, '1000条/页']]
    }),
    valueField : 'value',
    displayField : 'text',
    value : '100',
    editable : false,
    width : 85
});
var number = parseInt(pagesize_combo.getValue());
// 改变每页显示条数reload数据
pagesize_combo.on("select", function(comboBox) {
    bbar.pageSize = parseInt(comboBox.getValue());
    number = parseInt(comboBox.getValue());
    store.reload({
        params : {
            start : 0,
            limit : parseInt(pagesize_combo.getValue())
        }
    });
});
 // 分页工具栏
var bbar = new Ext.PagingToolbar({
    pageSize : number,
    store : tb_store,
    displayInfo : true,
    displayMsg : '显示{0}条到{1}条,共{2}条',
    //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
    emptyMsg : "没有符合条件的记录",
    items : ['-', '&nbsp;&nbsp;', pagesize_combo
             ]
});
var rownums = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});

var businessInfoColumns = new Ext.grid.ColumnModel([
                                                    rownums,
                                                {header :'业务分类',dataIndex:'a1',width:150,sortable : true},
                                               	{header:'上年末',dataIndex:'a2',sortable : true,width:150,align:'right',renderer: money('0,000.00')},
                                               	{header:'一月',dataIndex:'a3',sortable : true,width:150,align:'right',renderer: money('0,000.00')},
                                               	{header:'二月',dataIndex:'a4',sortable : true,width:150,align:'right',renderer: money('0,000.00')},
                                               	{header:'三月',dataIndex:'a5',sortable : true,width:150,align:'right',renderer: money('0,000.00')},
                                               	{header:'四月',dataIndex:'a6',sortable : true,width:150,align:'right',renderer: money('0,000.00')},
                                               	{header:'五月',dataIndex:'a7',sortable : true,width:150,align:'right',renderer: money('0,000.00')},
                                               	{header:'六月',dataIndex:'a8',sortable : true,width:150,align:'right',renderer: money('0,000.00')},
                                               	{header:'七月',dataIndex:'a9',sortable : true,width:150,align:'right',renderer: money('0,000.00')},
                                               	{header:'八月',dataIndex:'a10',sortable : true,width:150,align:'right',renderer: money('0,000.00')},
                                               	{header:'九月',dataIndex:'a11',sortable : true,width:150,align:'right',renderer: money('0,000.00')},
                                               	{header:'十月',dataIndex:'a12',sortable : true,width:150,align:'right',renderer: money('0,000.00')},
                                               	{header:'十一月',dataIndex:'a13',sortable : true,width:150,align:'right',renderer: money('0,000.00')},
                                               	{header:'十二月',dataIndex:'a14',sortable : true,width:150,align:'right',renderer: money('0,000.00')}
                                               

                                               	]);




var tb_store = new Ext.data.Store({
	reader : new Ext.data.JsonReader({
				totalProperty:'num',// 记录总数
				root:'rows'// Json中的列表数据根节点
			}, [{name:'a1'},
            	{name:'a2'},
            	{name:'a3'},
            	{name:'a4'},
            	{name:'a5'},
            	{name:'a6'},
            	{name:'a7'},
            	{name:'a8'},
            	{name:'a9'},
            	{name:'a10'},
            	{name:'a11'},
            	{name:'a12'},
            	{name:'a13'},
            	{name:'a14'}
])
});
var data= {
		num:3,
		rows:[
		{"rownum":"1","a1":"存款余额","a2":"11111","a3":"22222","a4":"33333","a5":"44444",
			"a6":"55555","a7":"66666","a8":"77777","a9":"88888","a10":"99999","a11":"11111",
			"a12":"11111","a13":"64811","a14":"64845"},
			{"rownum":"2","a1":"存款日均","a2":"15000","a3":"25835","a4":"20722","a5":"17557",
				"a6":"12633","a7":"64845","a8":"64845","a9":"20722","a10":"25835","a11":"12633",
				"a12":"17557","a13":"15000","a14":"20722"},
				{"rownum":"3","a1":"贷款余额","a2":"37401","a3":"41941","a4":"15000","a5":"82632",
					"a6":"12342","a7":"65835","a8":"24113","a9":"57622","a10":"34523","a11":"53113",
					"a12":"53113","a13":"84234","a14":"10832"},
					{"rownum":"4","a1":"承兑余额","a2":"23232","a3":"32323","a4":"42312","a5":"44211",
						"a6":"10235","a7":"23232","a8":"72322","a9":"26493","a10":"32823","a11":"12323",
						"a12":"23415","a13":"24144","a14":"22431"},
						{"rownum":"5","a1":"承兑累计","a2":"32341","a3":"31342","a4":"83231","a5":"23415",
							"a6":"10235","a7":"26493","a8":"21213","a9":"13123","a10":"51441","a11":"73211",
							"a12":"52321","a13":"54141","a14":"80746"},
							{"rownum":"6","a1":"贴现余额","a2":"21313","a3":"86482","a4":"39475","a5":"89264",
								"a6":"10273","a7":"20832","a8":"30843","a9":"34743","a10":"27402","a11":"28402",
								"a12":"28402","a13":"37648","a14":"74972"},
								{"rownum":"7","a1":"中间业务收入","a2":"57040","a3":"41941","a4":"45263","a5":"57320",
									"a6":"45263","a7":"41941","a8":"89264","a9":"28402","a10":"10273","a11":"10273",
									"a12":"39475","a13":"57320","a14":"10273"},
									{"rownum":"2","a1":"利润贡献度","a2":"88630","a3":"72939","a4":"12925","a5":"82536",
										"a6":"92633","a7":"18932","a8":"63623","a9":"23241","a10":"23156","a11":"98743",
										"a12":"90783","a13":"23132","a14":"32322"}
		]
	};
                     tb_store.loadData(data);
                     
                     
   
      
                     
                     
         var windows = new Ext.Window({
        	                id:windows,
	                       // layout : 'fit',
	                    	renderTo:'viewport_center',
	                    	width : document.body.clientWidth-180,
	    					height : document.body.clientHeight-30,
	                       
	                        closable : false,
                            resizable : false,
	                        collapsible : false,
	                        maximizable : true,
	                        closeAction : 'hide',
	                        closable : true,
	                        constrain : true,
	                        modal : true,
	                        autoScroll : true,
	                        animCollapse : false,
	                        border : true,
	                        animateTarget : Ext.getBody(),
	                        constrain : true,
	                        items:[
	                               {layout:'fit',
	           	                	style:'padding:8px 0px 0px 0px',
	           	             	width : document.body.clientWidth-100,
		    					height : document.body.clientHeight-50,
		                       
	        	                	collapsible:true,
	        	                    title: '业务变化统计趋势图',
	        	                    //height:date,
	                       		    html:'<iframe id="contentFrame1" name="content1" height="500" frameborder="no" width="100%" src=\"customerBaseInformation/fusionchartsDemo/lookgrade/businessChart.html\" "/> scrolling="no"> </iframe>'}
	                               ],
	                               buttonAlign:'center',
	                               buttons:[{text:'返回',
	                                       handler: function(){
	                            	   windows.hide();
	                               }}
	                                       ]
                               });


var businessGrid = new Ext.grid.GridPanel({
	height : 400,
	//title :'业务变化统计',
	frame : true,
	overflow :'auto',
	autoScroll : true,
	region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
	store : tb_store, // 数据存储
	stripeRows : true, // 斑马线
	cm : businessInfoColumns, // 列模型
	//sm : tb_sm, // 复选框
   tbar:[	{
 		text:'查看趋势图',
  		handler:function()
  		{      			
  		windows.show();
  		}
  	}],
	bbar :bbar,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
var viewport_center = new Ext.Panel({
	autoScroll : true,
	renderTo:'viewport_center',
	height:document.body.scrollHeight-30,
	layout : 'fit',

	items:[
			{
					region:'center',
					layout:'border',
				    margins: '0 0 0 0',

					items:[
					businessSearchPanel,
					{
						region:'center',
						layout:'fit',
						
						items:[businessGrid]
					}
					]				
			}
		
		]
  });

});