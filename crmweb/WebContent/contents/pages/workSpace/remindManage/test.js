
	var searchPanel1 = new Ext.form.FormPanel({
        labelWidth : 100,
        height : 50,
        frame : true,
        labelAlign:'right',
        region : 'north',
        autoScroll : true,
        items : [{
                layout : 'column',
                items : [ {
              	  columnWidth : .50,
                  layout : 'form',
                  items : [{
                    xtype : 'textfield',
                    labelStyle : {
                            width : '120px'
                    },
                    Width : '100',
                    name : 'shemeName',
                    fieldLabel : '客户群规则名称',
                    anchor : '90%'
                  }]
            }, {
                columnWidth : .50,
                layout : 'form',
                items : [{

                        xtype : 'combo',
                        name : 'yearNum',
                        fieldLabel : '公开级别',
                        labelStyle : {
                                width : '120px'
                        },
                        width : '100',
                        mode : 'local',
                        store : new Ext.data.ArrayStore({
                                                fields : ['myId', 'displayText'],
                                                data : [[1, '私有'], [2, '辖内'],
                                                        [3, '全行']]
                                        }),
                        valueField : 'myId',
                        displayField : 'displayText',
                        anchor : '90%'
                }]
        }]
        }]

}); 
	
	var tarDictColumns = new Ext.grid.ColumnModel({
         columns : [{
                                 header : '左括号',
                                 width : 100,
                                 align : 'center',
                                 dataIndex : 'left',
                                 editor : new Ext.form.ComboBox({
          							typeAhead : true,
          							triggerAction : 'all',
          							lazyRender : true,
          							listClass : 'x-combo-list-small',
          							mode : 'local',
          							valueField : 'myId1',
          							displayField : 'displayText1',
          							store : new Ext.data.ArrayStore({
          										id : 'tarName',
          										fields : ['myId1', 'displayText1'],
          										data : [['(', '('],
          										       ['((', '(('],
          										       ['(((', '(((']
          												]
          									})
          						}),
                                 sortable : true
                         }, {
                             header : '指标',
                             width : 200,
                             align : 'center',
                             dataIndex : 'tarName',
                             sortable : true,
             				editor : new Com.yucheng.crm.common.IndexField({ 
								xtype:'userchoose',
								fieldLabel : '指标列表', 
								id:'CUST_MANAGER',
								labelStyle: 'text-align:right;',
								name : 'CUST_MANAGER',
								hiddenName:'custMgrId',
								searchRoleType:('127,47'),  //指定查询角色属性 ,默认全部角色
								searchType:'SUBTREE',/* 允许空，默认辖内机构用户，指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
								singleSelect:false,
								anchor : '90%'
								})

                     }, {
                         header : '条件',
                         width : 50,
                         align : 'center',
                         dataIndex : 'con',
                         sortable : true,
         				editor : new Ext.form.ComboBox({
 							typeAhead : true,
 							triggerAction : 'all',
 							lazyRender : true,
 							listClass : 'x-combo-list-small',
 							mode : 'local',
 							valueField : 'myId1',
 							displayField : 'displayText1',
 							store : new Ext.data.ArrayStore({
 										id : 'tarName',
 										fields : ['myId1', 'displayText1'],
 										data : [['>', '大于'],
 										       ['<', '小于'],
 										       ['=', '等于']
 												]
 									})
 						})
                 },{
                     header : '值',
                     width : 100,
                     align : 'center',
                     dataIndex : 'zhi',
                     sortable : true,
                     editor : new Ext.form.Field()
             }, 
                         {
                                 header : '右括号',
                                 width : 100,
                                 align : 'center',
                                 dataIndex : 'right',
                                 editor : new Ext.form.ComboBox({
          							typeAhead : true,
          							triggerAction : 'all',
          							lazyRender : true,
          							listClass : 'x-combo-list-small',
          							mode : 'local',
          							valueField : 'myId1',
          							displayField : 'displayText1',
          							store : new Ext.data.ArrayStore({
          										id : 'tarName',
          										fields : ['myId1', 'displayText1'],
          										data : [[')', ')'],
          										       ['))', '))'],
          										       [')))', ')))']
          												]
          									})
          						}),
                                 sortable : true
                         },{
                             header : '连接符',
                             width : 100,
                             align : 'center',
                             dataIndex : 'join',
                             editor : new Ext.form.ComboBox({
      							typeAhead : true,
      							triggerAction : 'all',
      							lazyRender : true,
      							listClass : 'x-combo-list-small',
      							mode : 'local',
      							valueField : 'myId1',
      							displayField : 'displayText1',
      							store : new Ext.data.ArrayStore({
      										id : 'tarName',
      										fields : ['myId1', 'displayText1'],
      										data : [['AND', 'AND'],
      										       ['OR', 'OR']
      												]
      									})
      						}),
                             sortable : true
                     }]
 });

var record11 = Ext.data.Record.create([{
         name : 'left'
 }, {
         name : 'tarName'
 }, {
         name : 'con'
 }, {
         name : 'zhi'
 }, {
 }, {
         name : 'right'
 }, {
         name : 'join'
 }]);


var reader = new Ext.data.JsonReader({
         totalProperty : 'num',
         idProperty : 'yearNum',
         root : 'rows'
 }, record11);
var tarDictStore = new Ext.data.Store({
         reader : reader
 });

var data1 = {
        num : 1,
        rows : [{
                                "left" : "",
                                "tarName" : "存款时点余额",
                                "con" : ">",
                                "zhi" : "1000000",
                                "right" : "",
                                "join" : "AND"
                        },{
                            "left" : "",
                            "tarName" : "存款时点余额",
                            "con" : ">",
                            "zhi" : "10000",
                            "right" : "",
                            "join" :""
                    }]

};
tarDictStore.loadData(data1);	
	
	var tarDictListPanel = new Ext.grid.EditorGridPanel({
		tbar : [{
            text : '新增',
            iconCls:'addIconCss',
            handler:function() {
            onAdd();
        }}
            ,
            {
                text : '删除',
                iconCls:'deleteIconCss',
                handler:function() {
                    onDelete();
                },
                scope: this
                }

  ],
		height : 200,
		store : tarDictStore,
		frame : true,
		cm : tarDictColumns,
		stripeRows : true,
		clicksToEdit : 1
	});
	
        var searchPanel = new Ext.form.FormPanel({
                                labelWidth : 100,
                                height : 100,
                                frame : true,
                                labelAlign:'right',
                                region : 'north',
                                autoScroll : true,
                                items : [{
                                        layout : 'column',
                                        items : [ {
                                        	  columnWidth : .22,
                                              layout : 'form',
                                              items : [{
                                                xtype : 'textfield',
                                                labelStyle : {
                                                        width : '120px'
                                                },
                                                Width : '100',
                                                id : 'shemeName',
                                                name : 'shemeName',
                                                fieldLabel : '客户群规则名称',
                                                anchor : '90%'
                                              }]
                                        }, {
                                            columnWidth : .22,
                                            layout : 'form',
                                            items : [{

                                                    xtype : 'combo',
                                                    id : 'yearNum',
                                                    name : 'yearNum',
                                                    fieldLabel : '公开级别',
                                                    labelStyle : {
                                                            width : '120px'
                                                    },
                                                    width : '100',
                                                    mode : 'local',
                                                    store : new Ext.data.ArrayStore({
                                                                            fields : ['myId', 'displayText'],
                                                                            data : [[1, '私有'], [2, '辖内'],
                                                                                    [3, '全行']]
                                                                    }),
                                                    valueField : 'myId',
                                                    displayField : 'displayText',
                                                    anchor : '90%'
                                            }]
                                    }, {
            							columnWidth : .22,
            							layout : 'form',
            							items : [{
            								fieldLabel : '维护日期',
            								xtype : 'datefield',
            								id : 'createDateS',
            								format : 'Y-m-d',
            								editable : false,
            								name : 'createDateS',
            								anchor : '90%'
            							}]
            						},{
            						    columnWidth : .22,
                                        layout : 'form',
                                        labelWidth: 20,
            							items : [{
            								xtype : 'datefield',
            								format : 'Y-m-d',
            								editable : false,
            								fieldLabel : '至',
            								name : 'createDateE',
            								id : 'createDateE',
            								anchor : '65%'
            							}]
            				},

                                                        {
                                                                columnWidth : .1,
                                                                layout : 'form',
                                                                items : [{
                                                                                        xtype : 'button',
                                                                                        text : '查   询',
                                                                                        handler : function() {

                                                                                        },
                                                                                        anchor : '50%'
                                                                                }]
                                                        }]
                                }]

                        });

        var columns = new Ext.grid.ColumnModel({
                                columns : [{
                                                        header : '客户群规则编号',
                                                        width : 170,
                                                        align : 'center',
                                                        dataIndex : 'yearNum',
                                                        sortable : true
                                                }, {
                                                        header : '客户群规则名称',
                                                        width : 175,
                                                        align : 'center',
                                                        dataIndex : 'orgName',
                                                        sortable : true
                                                }, {
                                                        header : '公开级别',
                                                        width : 200,
                                                        align : 'center',
                                                        dataIndex : 'shemeName',
                                                        sortable : true
                                                }, {
                                                    header : '维护人',
                                                    width : 150,
                                                    align : 'center',
                                                    dataIndex : 'createUser',
                                                    sortable : true
                                            },
                                                {
                                                        header : '维护日期',
                                                        width : 150,
                                                        align : 'center',
                                                        dataIndex : 'createDate',
                                                        sortable : true
                                                }]
                        });

        var record = Ext.data.Record.create([{
                                name : 'yearNum'
                        }, {
                                name : 'orgName'
                        }, {
                                name : 'shemeName'
                        }, {
                                name : 'createDate'
                        }, {
                        }, {
                                name : 'createUser'
                        }]);

        var data = {
                num : 1,
                rows : [{
                                        "yearNum" : "vip2013019212",
                                        "orgName" : "VIP客户",
                                        "shemeName" : "全行",
                                        "createUser" : "micky",
                                        "createDate" : "2013-01-01"
                                },{
                                    "yearNum" : "national2013018212",
                                    "orgName" : "国结客户",
                                    "shemeName" : "全行",
                                    "createUser" : "vicky",
                                    "createDate" : "2013-02-01"
                            }]

        };
        var reader = new Ext.data.JsonReader({
                                totalProperty : 'num',
                                idProperty : 'yearNum',
                                root : 'rows'
                        }, record);
        var store = new Ext.data.Store({
                                reader : reader
                        });

        store.loadData(data);

        var listPanel = new Ext.grid.GridPanel({
                                store : store,
                                frame : true,
                                cm : columns,
                                stripeRows : true,
                                tbar : [{
                                                        text : '客户群规则设置',
                                                        iconCls:'editIconCss',
                                                        handler : function() {
                                                                editDictInit();
                                                        }

                                                }
                                              ],
                                store : store,
                                frame : true,
                                bbar : {
                                        xtype : 'paging',
                                        pageSize : 10,
                                        store : store,
                                        displayInfo : true,
                                        displayMsg : '显示1条到10条,共3条',
                                        emptyMsg : "没有符合条件的记录",
                                        items : ['-', '&nbsp;&nbsp;', {
                                                                xtype : 'textfield',
                                                                value : '10'
                                                        }]
                                }
                        });

    	
        // 设置指标展示的from
    	var editDictForm = new Ext.form.FormPanel({
    		labelWidth : 150,
    		height : 250,
    		frame : true,
    		autoScroll : true,
    		buttonAlign : "center",
    		items : [
    				tarDictListPanel
    					],
    					
    					buttons : [
    					{

    						text : '保  存',
    						handler : function() {
    							alert("规则设置成功！");
    							editDictWindow.hide();
    						}

    					},
    						{
    						text : '取  消',
    						handler : function() {
    							editDictWindow.hide();
    						}
    					} ]

    	});

    	// 定义指标设置窗口
    	var editDictWindow = new Ext.Window({
    		title : '客户群规则设置',
    		plain : true,
    		layout : 'form',
    		width : 800,
    		height : 350,
    		resizable : true,
    		draggable : true,
    		closable : true,
    		closeAction : 'hide',
    		modal : true, // 模态窗口
    		loadMask : true,
    		maximizable : true,
    		collapsible : true,
    		titleCollapse : true,
    		buttonAlign : 'right',
    		border : false,
    		items : [ searchPanel1,editDictForm ]
    	});
  
        // 展示指标设置窗口
        function editDictInit() {
        	editDictWindow.show();

        }
        
        
        /**
         * onDelete
         */
        var onDelete = function(){
            var index = tarDictListPanel.getSelectionModel().getSelectedCell();
            if (!index) {
            	alert("请选择一条记录");
                return false;
            }
            var rec = tarDictStore.getAt(index[0]);
            tarDictStore.remove(rec);
        };
        
        var onAdd = function(){
            var u = new tarDictStore.recordType({
            	"left" : "",
                "tarName" : "存款时点余额",
                "con" : ">",
                "zhi" : "1000000",
                "right" : "",
                "join" : "AND"
            });
            tarDictListPanel.stopEditing();
            tarDictListPanel.store.insert(0, u);
            tarDictListPanel.startEditing(0, 1);
        };