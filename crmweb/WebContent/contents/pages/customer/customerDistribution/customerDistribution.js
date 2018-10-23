Ext.onReady(function(){
    
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
	var win;
    
    var tools = [{
        id:'gear',
        handler: function(){
            Ext.Msg.alert('Message', 'The Settings tool was clicked.');
        }
    },{
        id:'close',
        handler: function(e, target, panel){
            panel.ownerCt.remove(panel, true);
        }
    }];
	
	//增加客户经理选择列表
	var msForm = new Ext.form.FormPanel({
        items:[{
            xtype: 'multiselect',
            fieldLabel: '客户经理选择',
            name: 'multiselect',
            width: '100%',
            height: 300,
            allowBlank:false,
            store: [['M001','杨奇'],
					['M001','赵瑞'],
					['M002','王东']],
			tbar:[{
                text: '清除',
                handler: function(){
	                msForm.getForm().findField('multiselect').reset();
	            }
            }],
            ddReorder: true
        }]    
    });
	
	
	//增加客户经理菜单
	var action = new Ext.Action({
        text: '增加客户经理',
        handler: function(){
			if(!win){
				win = new Ext.Window({
					layout:'fit',
					width:500,
					height:300,
					closeAction:'hide',
					plain: true,
					items: msForm,
					buttons: [{
						text:'增加',
						handler: function(){
							if(msForm.getForm().isValid()){
								Ext.Msg.alert('Submitted Values', 'The following will be sent to the server: <br />'+
								msForm.getForm().getValues(true));
							}
						}
					},{
						text: '关闭',
						handler: function(){
							win.hide();
						}
					}]
				});
			}
			win.show(this);
            
        },
        iconCls: 'blist'
    });

	var tip = new Ext.slider.Tip({
        getText: function(thumb){
			var pbar=Ext.getCmp('Pbar');
			var i = thumb.value/100;
			pbar.updateProgress(i, '已分配'+thumb.value+'%');
			var FV=Ext.getCmp('fv');
			FV.setValue(thumb.value);
            return String.format('<b>{0}% complete</b>', thumb.value);
        }
    });
	 var myData = [
        ['M001','杨奇'],
		['M001','赵瑞'],
		['M002','王东']
    ];
	var store = new Ext.data.SimpleStore({
        fields: [
           {name: 'MGID'},
           {name: 'MGNM'}
        ],
        sortInfo: {
            field: 'MGID', direction: 'ASC'
        }
    });
	store.loadData(myData);

	var listView = new Ext.list.ListView({
        store: store,
        multiSelect: true,
        emptyText: 'No MG to display',
        reserveScrollOffset: true,
		border:false,
        columns: [
            {id:'MGID',header: "客户经理编号", width: .4, sortable: true, dataIndex: 'MGID'},
            {header: "客户经理名称", width: .5, sortable: true, dataIndex: 'MGNM'}
        ]
    });

	var tree = new Ext.tree.TreePanel({
        autoScroll: true,
        enableDD: true,
		border:false
    });

    var root = new Ext.tree.TreeNode({
        text: '客户A',
        expanded: true
    });
    tree.setRootNode(root);

    root.appendChild(new Ext.tree.TreeNode({text: '中间业务'}));
    var node0 = new Ext.tree.TreeNode({text: '贷款业务'});
	var node1 = new Ext.tree.TreeNode({text: '存款业务'});
    node0.appendChild(new Ext.tree.TreeNode({text: 'A00101001'}));
	node0.appendChild(new Ext.tree.TreeNode({text: 'A00101002'}));
	node1.appendChild(new Ext.tree.TreeNode({text: 'B00101001'}));
	node1.appendChild(new Ext.tree.TreeNode({text: 'B00101002'}));
    root.appendChild(node0);
	root.appendChild(node1);
	
	
	
	//grid
	var myData1 = [
        ['杨奇','贷款','A00101001','02382717','姚亮',30],
		['赵瑞','贷款','A00101001','02382714','焦向波',30],
		['王东','存款','A00101002','02382723','余勇智',40]
    ];
	//客户经理列表弹出框
	var myData2 = [
        ['M001','100182','孔令涛',1],
		['M001','100232','史金刚',2],
		['M002','200834','黄维',3]
    ];
    function pctChange(val){
        if(val <50){
            return '<span style="color:green;">' + val + '%</span>';
        }else {
            return '<span style="color:red;">' + val + '%</span>';
        }
    }

    // create the data store
    var store1 = new Ext.data.ArrayStore({
        fields: [
           {name: 'm1'},
           {name: 'm2'},
           {name: 'm3'},
           {name: 'm4'},
           {name: 'm5'},
           {name: 'pctChange', type: 'float'},
        ]
    });
	  var store2 = new Ext.data.ArrayStore({
        fields: [
           {name: 'MGID'},
           {name: 'MGNM'},
           {name: 'pNO'},
         ,
        ]
    });
    // manually load local data
    store1.loadData(myData1);
	 store2.loadData(myData2);
    // create the Grid
    var grid = new Ext.grid.GridPanel({
        store: store1,
        columns: [
            {id:'MGID',header: '客户名称', width: 75, sortable: true, dataIndex: 'm1'},
            {header: '产品类型', width: 100, sortable: true, dataIndex: 'm2'},
            {header: '客户账号', width: 100, sortable: true, dataIndex: 'm3'},
            {header: '客户经理ID', width: 100, sortable: true, dataIndex: 'm4'},
            {header: '客户经理名称', width: 100, sortable: true, dataIndex: 'm5'},
            {header: '分配比例%', sortable: true, renderer: pctChange, dataIndex: 'pctChange'}
        ],
		tbar:[ 
      	{
      		text:'增加客户经理',
      		iconCls:'page_addIcon',
      		handler:function()
      		{
      			addMan.show(this);
      		}
      	}],		
        stripeRows: true,
        //autoExpandColumn: 'MGID',
        // config options for stateful behavior
        stateful: true,
        stateId: 'grid'        
    });
		 //新增客户经理列表
    var addManGrid = new Ext.grid.GridPanel({
        store: store2,
        columns: [
				{id:'MGID',header: '机构名称', width: 75, sortable: true, dataIndex: 'MGID'},
				{header: '员工号', width: 100, sortable: true, dataIndex: 'MGNM'},
				{header: '客户经理名称', width: 100, sortable: true, dataIndex: 'pNO'}
        ],
		height:300,
        stripeRows: true,
        stateful: true,
        stateId: 'grid'        
    });
	
	/*新增WINDOW*/
	var addMan = new Ext.Window({
		 title: '客户经理列表',
         collapsible:true,
         layout:'column',
         width:800,
         height:500,
         maximizable: true,
         closeAction : 'hide',
         buttonAlign : 'center',
		 items:[
			new Ext.form.FormPanel({
				labelWidth : 90, // 标签宽度
				frame : true, //是否渲染表单面板背景色
				labelAlign : 'middle', // 标签对齐方式
				//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
				buttonAlign : 'right',
				margins : '12 0',
				buttonAlign : 'center',
				height : 75,
				items : [{
				layout : 'column',
				border : false,
				items : [{
							columnWidth : .25,
							layout : 'form',
							labelWidth : 80, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										fieldLabel : '机构名称',
										name : 'c1',
										xtype : 'textfield', // 设置为数字输入框类型
										labelStyle: 'text-align:right;',
										anchor : '90%'
									}]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 80, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										fieldLabel : '员工号', // 标签
										name : 'c4', // name:后台根据此name属性取值
										allowBlank : true, // 是否允许为空
										labelStyle: 'text-align:right;',
										anchor : '90%' // 宽度百分比
									}]
						}, {
							columnWidth : .25,
							layout : 'form',
							labelWidth : 80, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
										fieldLabel : '客户经理名称', // 标签
										name : 'c7', // name:后台根据此name属性取值
										allowBlank : true, // 是否允许为空
										labelStyle: 'text-align:right;',
										anchor : '90%' // 宽度百分比
									}]
						}]
			}],
		buttons : [{
					text : '查询',
					iconCls : 'previewIcon' 
					/*handler : function() {
						queryBalanceInfo(qForm.getForm());
					}*/
				}]
			}),addManGrid],
		 	buttons : [{
					text : '选择添加'
					/*handler : function() {
						queryBalanceInfo(qForm.getForm());
					}*/
				}, {
					text : '退出',
					handler : function() 
					{
						addMan.hide();
					}
				}]
	});
	
    var viewport = new Ext.Viewport({
        layout:'border',
        items:[/*{
             region:'east',
            id:'east-panel',
            title:'客户经理',
			layout: 'fit',
            split:true,
            width: '400',
            minSize: 200,
            maxSize: 400,
			//collapseMode: 'mini',
            collapsible: true,
            layoutConfig:{
                animate:true
            },
            items: [{
				xtype: 'panel',
				layout:'fit',
				//border:false,
				tbar: [action],
				items: listView
			}]
        },*/
           
               {
            region:'center',
			//title:'业绩分配',
			layout: 'fit',
			 buttonAlign : 'center',
			//autoScroll:true,
			items:[{
				xtype: 'panel',
				layout: 'border',
				items:[{
					//title: 'North',
					region: 'north',
					layout: 'border',
					height: 200,
					items:[
					{
						region : 'center',
						title: '分配列表',
						layout:'fit',
						collapsible: false,
						items:grid
						//width:'70%',
						//height:'100%'
					}]
				},{
					
					region: 'center',
					//width: '100%',
					//height:'50%',
					autoScroll :true,
					layout: 'border',
					items:[{
						region: 'east',
						//xtype: 'panel',
						width:'50%',
						collapsible: false,
						title: '分配图',
						height:'100%',
						//autoScroll :true,
						html:'<iframe id="reporter-iframe" src="togglePieSlice.html" width="100%" height="300px" name="main"  frameborder="0" scrolling="auto"></iframe>'
					},{
						region : 'center',
						//layout:'fit',
						width:'50%',
						xtype: 'form',
						title: '分配条',
						 vertical: true,
						bodyStyle: {padding: '15px,5px,15px,5px'},
						//defaultType: 'sliderfield',
						items: [
						{
							xtype: 'progress',
							id:'Pbar',
							value: 0,
							text: '已分配',
							 vertical: true
						},{
							xtype: 'slider',
							fieldLabel: '分配',
							value: 0,
							name: 'fx',
							minValue: 0,
							axValue: 100,
							plugins: tip
							
						},{
							labelWidth: 40,
							id:'fv',
							fieldLabel: '分配比(%)',
							xtype: 'textfield',
							emptyText: '请输入百分比',
							itemCls: 'x-form-required'
						}]
					}]
				}]
			}],buttons : [{
					text : '保存'
					/*handler : function() {
						queryBalanceInfo(qForm.getForm());
					}*/
				}, {
					text : '返回',
					handler : function() 
					{
					window.location.href = '../customerManager/beforeAchievementQuery.html' ;
					}
				}]  
        },{
           
				 region:'west',
            id:'west-panel',
            title:'客户账户信息',
			layout: 'fit',
            split:true,
            width: 200,
            minSize: 300,
            maxSize: 500,
            collapsible: true,
			//collapseMode: 'mini',
            layoutConfig:{
                animate:true
            },
            items: [tree
			]
        }
		]
    });
});




