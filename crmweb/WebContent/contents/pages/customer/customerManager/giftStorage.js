Ext.onReady(function() {
	var tmepMethod = 'add';
	var sOrgIdJson={'orgid':[]};
	Ext.override(Ext.form.ComboBox, {
	      onViewClick : function(doFocus) {
	        var index = this.view.getSelectedIndexes()[0], s = this.store, r = s.getAt(index);
	        if (r) {
	          this.onSelect(r, index);
	        } else if (s.getCount() === 0) {
	          this.collapse();
	              
	        }
	        if (doFocus !== false) {
	          this.el.focus();
	        }
	      }
	    });

	/**
	 * 数据存储
	 */
	var unitStore =  new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','件'],['2','元'],['3','箱'],['4','只'],['5','个'],['6','套'],['7','公斤'],['8','米']]
	});
	var giftTypeStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','家用电器'],['2','手机数码'],['3','图书期刊'],['4','音像制品'],['5','汽车用品'],['6','运动健康'],['7','医疗保健'],['8','箱包服饰'],['9','食品饮料'],['10','消费卡类']]
	});
	     

	 var addPotentialCustomerPanel = new Ext.FormPanel({
		 id:'add',
		  frame:true,
	        bodyStyle:'padding:5px 5px 0',
	        width: '100%',
	           autoHeight:true,
	            layout:'column',
	                     items:[{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '礼品名称',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             allowBlank : false,
	                             id: 'giftName',
	                             name: 'giftName',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
	 							name : 'giftType',
	 							id:'giftType',
								fieldLabel : '礼品种类',
								labelStyle: 'text-align:right;',
								triggerAction : 'all',
								store : giftTypeStore,
								displayField : 'value',
								allowBlank : false,
								valueField : 'key',
								mode : 'local',
								forceSelection : true,
								typeAhead : true,
								emptyText:'请选择',
								resizable : true,
								anchor : '95%'
							}),{
	                             xtype:'textfield',
	                             fieldLabel: '礼品编号',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             hidden : true,
	                             id: 'giftId',
	                             name: 'giftId',
	                             anchor:'95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '礼品条码',
	                             maxLength:50,
	                             allowBlank : false,
	                             labelStyle: 'text-align:right;',
	                             id: 'giftCode',
	                             name: 'giftCode',
	                             anchor:'95%'
							}]
	                     },{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '礼品数量',
	                             maxLength:50,
	                             allowBlank : false,
	                             labelStyle: 'text-align:right;',
	                             id: 'giftSum',
	                             name: 'giftSum',
	                             anchor:'95%'
	                         },new Ext.form.ComboBox({
		 							name : 'giftUnit',
		 							id:'giftUnit',
									fieldLabel : '礼品单位',
									labelStyle: 'text-align:right;',
									triggerAction : 'all',
									store : unitStore,
									displayField : 'value',
									allowBlank : false,
									valueField : 'key',
									mode : 'local',
									forceSelection : true,
									typeAhead : true,
									emptyText:'请选择',
									resizable : true,
									anchor : '95%'
								})]
	                     },{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [{
	                            	xtype:'datefield',
	                 				format:'Y-m-d',
	                 				fieldLabel : '入库日期',
	                 				labelStyle: 'text-align:right;',
	                 				name : 'storageDate',
	                 				anchor : '95%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '入库人',
	                             labelStyle: 'text-align:right;',
	                             maxLength:50,
	                             id: 'storager',
	                             name: 'storager',
	                             anchor:'95%'
	                         }]
	           }]
	    });
	    //礼品入库
	var addPotentialCustomerWindow = new Ext.Window({
	    layout : 'fit',
	    width : 700,
	    autoHeight:true,
        draggable : true,//是否可以拖动
        closable : true,// 是否可关闭
        modal : true,
        closeAction : 'hide',
        titleCollapse : true,
        buttonAlign : 'center',
        border : false,
        animCollapse : true,
        animateTarget : Ext.getBody(),
        constrain : true,
        items : [addPotentialCustomerPanel],
        buttons : [{
            text : '保存',
			handler : function(){
				if(!addPotentialCustomerPanel.getForm().isValid()){
					Ext.Msg.alert("系统提醒","输入有误，请重新输入!");
				return false;
				}
				Ext.Msg.alert('提示', '操作成功');
				addPotentialCustomerWindow.hide();
			}
		}, {
			text : '重置',
			id : 'btnReset',
			handler : function() {
			addPotentialCustomerPanel.getForm().reset();   
				//clearForm(addRoleFormPanel.getForm());
			}
		}, {
			text : '关闭',
			handler : function() {
		        addPotentialCustomerWindow.hide();
			}
		}]
	});
	    

/******************************查询面板*******************************************/
		var qForm = new Ext.form.FormPanel({
			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
			region: 'north',
		    title: "服务管理->积分管理->礼品管理->礼品查询", 
		    height: 120,
			layout : 'column',
			items : [{
			    columnWidth : .25,
        		layout : 'form',
        		border : false,
        		items : [{
            		fieldLabel : '礼品名称',
            		name : 'CUST_ID',
            		xtype : 'textfield', // 设置为数字输入框类型
            		labelStyle: 'text-align:right;',
            		anchor : '90%'
            	}]
        	}, {
        		columnWidth : .25,
        		layout : 'form',
        		border : false,
        		items : [new Ext.form.ComboBox({
        		    hiddenName : 'CUST_TYP',
					fieldLabel : '礼品种类',
					labelStyle: 'text-align:right;',
					triggerAction : 'all',
					store : giftTypeStore,
					displayField : 'value',
					valueField : 'key',
					mode : 'local',
					forceSelection : true,
					typeAhead : true,
					emptyText:'请选择',
					resizable : true,
					anchor : '90%'
				})]
        	}, {
        		columnWidth : .25,
        		layout : 'form',
        		border : false,
        		items : [{
        			fieldLabel : '礼品条码',
        			name : 'CERT_NUM',
        			id:'CERT_NUM',
        			xtype : 'textfield', // 设置为数字输入框类型
        			labelStyle: 'text-align:right;',
        			anchor : '90%'
        		}]
        	}],
		buttons : [{
					text : '查询',
					handler : function() {
			store.loadData(storeData);
		}},{
					text : '重置',
					     handler : function() {
					    	 qForm.getForm().reset();
//					    	 orgTreePanel.root.getUI().toggleCheck(false);
						}
					}]
		});
	 //复选框
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
	        {header : '礼品名称',dataIndex : 'giftName',sortable : true,width : 150},
		    {header : '礼品种类',dataIndex : 'giftType',width : 200,sortable : true},
		    {header : '礼品编号',dataIndex : 'giftId',width : 150,sortable : true},
		    {header : '礼品条码',dataIndex : 'giftCode',width : 150,sortable : true},
		    {header : '礼品数量',dataIndex : 'giftSum',width : 150,sortable : true},
		    {header : '入库人',dataIndex : 'storager',width : 200,sortable : true},
		    {header : '入库日期',dataIndex : 'storageDate',width : 200,sortable : true},
		    {header : '复核人',dataIndex : 'reviewer',width : 200,sortable : true},
		    {header : '复核时间',dataIndex : 'reviewTime',sortable : true},
		    {header : '入库状态',dataIndex : 'status',width : 150,sortable : true}
		    
		    
			]);

	var storeData = {//特性项数据
			num:6,
			rows:[
			{"giftName":"洗衣机","giftType":"家用电器","giftId":"000000001","giftCode":"11092837463762534","giftSum":"12","storager":"李毅","storageDate":"2012-08-16","reviewer":"","reviewTime":"","status":"待复核"},
			{"giftName":"手机","giftType":"手机数码","giftId":"000000002","giftCode":"111212121262534","giftSum":"2","storager":"李蕾","storageDate":"2012-08-16","reviewer":"韩梅梅","reviewTime":"2012-08-16","status":"已复核"},
			{"giftName":"耳机","giftType":"手机数码","giftId":"000000003","giftCode":"546928243762534","giftSum":"32","storager":"王一平","storageDate":"2012-08-16","reviewer":"亨利","reviewTime":"2012-08-16","status":"已复核"},
			{"giftName":"篮球","giftType":"运动健康","giftId":"000000004","giftCode":"233754463762534","giftSum":"12","storager":"李毅","storageDate":"2012-08-16","reviewer":"亨利","reviewTime":"2012-08-16","status":"已复核"}
			]
			
		}; 
	var storeRecord = Ext.data.Record.create(//特性项记录（record）
			[
				{name:'giftName'},
				{name:'giftType'},
				{name:'giftId'},
				{name:'giftCode'},
				{name:'giftSum'},
				{name:'storager'},
				{name:'storageDate'},
				{name:'reviewer'},
				{name:'reviewTime'},
				{name:'status'}
				]
			);
	var storerReader = new Ext.data.JsonReader(//读取特性项数据的jsonReader
			{
				totalProperty:'num',
				root:'rows'
			},storeRecord
		);
	var store = new Ext.data.Store({//特性项数据的store
		reader:storerReader
	});
	
     var pagesize_combo = new Ext.form.ComboBox({
         name : 'pagesize',
         triggerAction : 'all',
         mode : 'local',
         store : new Ext.data.ArrayStore({
             fields : ['value', 'text'],
             data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 250, '250条/页' ],
					[ 500, '500条/页' ] ]
         }),
         valueField : 'value',
         displayField : 'text',
         value : '20',
         editable : false,
         width : 85
     });
    var number = parseInt(pagesize_combo.getValue());
    pagesize_combo.on("select", function(comboBox) {
    	  bbar.pageSize = parseInt(pagesize_combo.getValue()),
		store.load({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
	});
	var bbar = new Ext.PagingToolbar({
        pageSize : number,
        store : store,
        displayInfo : true,
        displayMsg : '显示{0}条到{1}条,共{2}条',
        emptyMsg : "没有符合条件的记录",
        items : ['-', '&nbsp;&nbsp;', pagesize_combo
                 ]
    });
	var checkedNodessd = '';
	// 表格工具栏

	var tbar = new Ext.Toolbar({

				items : [{
					text : '礼品入库',
					iconCls:'addIconCss',
					id:'addPer',
					handler : function() {
					addPotentialCustomerPanel.getForm().reset();
					addPotentialCustomerWindow.show();
					}
				},'-',{
					text : '礼品出库',
					iconCls : 'deleteIconCss',
					id:'del',
					handler : function() {
					addPotentialCustomerPanel.getForm().reset();
					addPotentialCustomerWindow.show();
					}
				}]
			});


/******************************表格显示******************************************/
	var grid = new Ext.grid.GridPanel({
				frame : true,
				autoScroll : true,
				region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
				store : store, // 数据存储
				stripeRows : true, // 斑马线
				cm : cm, // 列模型
				sm : sm, // 复选框
				tbar : [tbar,{
					text : '礼品复核',
					iconCls:'editIconCss',
					handler : function() {

					var columns = grid.getSelectionModel().getSelections();
					if(columns.length == 1){
						Ext.Msg.alert('提示', '操作成功');
					}else{
						Ext.Msg.alert('提示','请选择一条记录进行修改');
						return ;
					}
				
					Ext.Msg.alert('提示', '操作成功');
					
				}
				}], // 表格工具栏
				bbar:bbar,
				viewConfig:{
					   forceFit:false,
					   autoScroll:true
					},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});


   
	// 布局模型
	    
	var viewport = new Ext.Viewport({
		layout:'fit',
		items:[{
		layout : 'border',
		items: [qForm,grid] 
		}]
	});

}); 