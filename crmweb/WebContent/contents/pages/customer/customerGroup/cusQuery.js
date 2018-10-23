
Ext.onReady(function(){
	
	 
	var data1 = {
	        num : 1,
	        rows : [
	                	{
	                                "left" : "",
	                                "tarName" : "存款时点余额",
	                                "con" : ">",
	                                "zhi" : "1000000",
	                                "right" : "",
	                                "join" : "AND"
	                        },{
	                            "left" : "",
	                            "tarName" : "持有产品数量",
	                            "con" : ">",
	                            "zhi" : "3",
	                            "right" : "",
	                            "join" :""
	                    }]

	};
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

var tarDictColumns = new Ext.grid.ColumnModel({
    columns : [
               {
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
                    }, 
                    {
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

                }, 
                {
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
            },
            {
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
                    },
                    {
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
                }
                    ]
});
	
	var tarDictListPanel = new Ext.grid.EditorGridPanel({
		region : 'center',
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
	
//	var tarDictListPanelnew = new Ext.grid.EditorGridPanel({
//		region : 'south',
//		tbar : [{
//            text : '新增',
//            iconCls:'addIconCss',
//            handler:function() {
//            onAdd();
//        }}
//            ,
//            {
//                text : '删除',
//                iconCls:'deleteIconCss',
//                handler:function() {
//                    onDelete();
//                },
//                scope: this
//                }
//
//  ],
//		height : 200,
//		//store : tarDictStore,
//		frame : true,
//		cm : tarDictColumns,
//		stripeRows : true,
//		clicksToEdit : 1
//	});
	
//	var tarDictListPanelnew2 = new Ext.Panel
//	(
//		{
//			id:'tarDictListPanelnew2',
//			region : 'south',
//			height : 300,
//			html: '111111111111'
//		}
//	);
	 
	
//	var tarDictListPanelnew2 = new Ext.grid.EditorGridPanel({
//		id:'tarDictListPanelnew2',
//		height : 400,
//		region : 'south',
//		tbar : [{
//            text : '新增',
//            iconCls:'addIconCss',
//            handler:function() {
//            onAdd();
//        }}
//            ,
//            {
//                text : '删除',
//                iconCls:'deleteIconCss',
//                handler:function() {
//                    onDelete();
//                },
//                scope: this
//                }
//
//  ],
//		//height : 200,
//		store : tarDictStore,
//		frame : true,
//		cm : tarDictColumns,
//		stripeRows : true,
//		clicksToEdit : 1
//	});
  var onchangecust = function(){
//    	 var wrc = Ext.getCmp('tarDictListPanelnew1');
//    	 wrc.removeAll();
//    	 wrc.add(tarDictListPanelnew2);
//    	 wrc.doLayout();
	  //debugger;
//	  alert(Ext.getCmp('addCustomerGroup'));
//	  alert(Ext.getCmp('addCustomerGroup').findField('shareFlag11'));
	  var selected = Ext.getCmp('addCustomerGroup').form.findField('custFrom').getValue();
	  
	 
	  //Ext.getCmp('shareFlagt11').getValue ();
	  if(selected == '1')
	  {
		  
		  Ext.getCmp('custFromName3').setValue('客户筛选');
		  var wrc = Ext.getCmp('tarDictListPanelnew1');
	    	 wrc.removeAll();
	    	 //wrc.add(tarDictListPanelnew1);
//	    	 wrc.add({
//					id:'tarDictListPanelnew2',
//					region : 'south',
//					height : 300,
//					html: '1111111'
//				});
	    	 wrc.doLayout();
	  }
	  else if(selected == '2')
	  {	    	 
		  Ext.getCmp('custFromName3').setValue('规则自动筛选');
	    	 var wrc = Ext.getCmp('tarDictListPanelnew1');
	    	 wrc.removeAll();
	    		var record111 = Ext.data.Record.create([{
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
	    		var reader1 = new Ext.data.JsonReader({
	    	        totalProperty : 'num',
	    	        idProperty : 'yearNum',
	    	        root : 'rows'
	    	}, record111);
	    	var tarDictStore1 = new Ext.data.Store({
	    	        reader : reader1
	    	});

	    	var tarDictColumns1 = new Ext.grid.ColumnModel({
	    	    columns : [
	    	               {
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
	    	                    }, 
	    	                    {
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

	    	                }, 
	    	                {
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
	    	            },
	    	            {
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
	    	                    },
	    	                    {
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
	    	                }
	    	                    ]
	    	});
	    		var tarDictListPanelnew2 = new Ext.grid.EditorGridPanel
	    		(
	    			{
	    				id:'tarDictListPanelnew2',
	    				height : 400,
	    				//region : 'south',
	    				tbar : 
	    				[
	    				 	{
			    	            text : '新增',
			    	            iconCls:'addIconCss',
			    	            handler:function() 
			    	            {
			    	            	onAdd1();
			    	            }
	    				 	},
		    	            {
		    	                text : '删除',
		    	                iconCls:'deleteIconCss',
		    	                handler:function() 
		    	                {
		    	                    onDelete1();
		    	                },
		    	                scope: this
	    	                }

	    				 ],
		    			store : tarDictStore1,
		    			frame : true,
		    			cm : tarDictColumns1,
		    			stripeRows : true,
		    			clicksToEdit : 1
	    			}
	    		);
	    		wrc.add(tarDictListPanelnew2);
	    	 
//	    	 wrc.add({
//					id:'tarDictListPanelnew2',
//					region : 'south',
//					//height : 300,
//					html: '1111111'
//				});
//	    	 wrc.add(new Ext.grid.EditorGridPanel({
//	    			id:'tarDictListPanelnew2',
//	    			height : 300,
//	    			region : 'south',
//	    			tbar : [{
//	    	            text : '新增',
//	    	            iconCls:'addIconCss',
//	    	            handler:function() {
//	    	            onAdd();
//	    	        }}
//	    	            ,
//	    	            {
//	    	                text : '删除',
//	    	                iconCls:'deleteIconCss',
//	    	                handler:function() {
//	    	                    onDelete();
//	    	                },
//	    	                scope: this
//	    	                }
//
//	    	  ],
//	    			height : 200,
//	    			store : tarDictStore,
//	    			frame : true,
//	    			cm : tarDictColumns,
//	    			stripeRows : true,
//	    			clicksToEdit : 1
//	    		}));
	    	 wrc.doLayout();
	  }
	  
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
     
     var onDelete = function(){
         var index = tarDictListPanel.getSelectionModel().getSelectedCell();
         if (!index) {
         	alert("请选择一条记录");
             return false;
         }
         var rec = tarDictStore.getAt(index[0]);
         tarDictStore.remove(rec);
     };
     
     var onAdd1 = function(){
         var u = new tarDictStore.recordType({
         	"left" : "",
             "tarName" : "存款时点余额",
             "con" : ">",
             "zhi" : "1000000",
             "right" : "",
             "join" : "AND"
         });
         var tarDictListPanel3 = Ext.getCmp('tarDictListPanelnew2');
         tarDictListPanel3.stopEditing();
         tarDictListPanel3.store.insert(0, u);
         tarDictListPanel3.startEditing(0, 1);
     };
     
     var onDelete1 = function(){
    	 
    	 var tarDictListPanel3 = Ext.getCmp('tarDictListPanelnew2');
         var index = tarDictListPanel3.getSelectionModel().getSelectedCell();
         if (!index) {
         	alert("请选择一条记录");
             return false;
         }
         var rec = tarDictStore.getAt(index[0]);
         tarDictStore1.remove(rec);
     };
     
     
	

	var papersStore = new Ext.data.Store( {
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=PAPERS_TYPE'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var marrgStatusType = new Ext.data.Store( {
		restful : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=DEM0100003'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	
	var custCreditLevel = new Ext.data.Store( {//信用等级
		restful : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=DEM0100007'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	custCreditLevel.load();
	
	var folk = new Ext.data.Store( {//名族代码
		restful : true,
		sortInfo : {
		field : 'key',
			direction : 'ASC'
	},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=DEM0100001'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	folk.load();  
	
	var custTypStore = new Ext.data.Store( {//客户类型代码
		restful : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=XD000080'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	folk.load();  
	
	var position = new Ext.data.Store( {//职称下拉框
 		restful : true,
 		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=DEM0100006'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	position.load();
	var tlpos = new Ext.data.Store( {//职务下拉框
		restful : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=DEM0100006'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	tlpos.load();
	/*******客户状态**********/
	var custStat = new Ext.data.Store( {
		restful : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=CUSTOMER_STATUS'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	custStat.load();
	
	var eduLevel = new Ext.data.Store( {//最高学历
		restful : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=DEM0100007'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	eduLevel.load();

	var custCreditLevel = new Ext.data.Store( {//信用等级
		restful : true,
		sortInfo : {
			field : 'key',
			direction : 'ASC'
		},
		autoLoad : true,
		proxy : new Ext.data.HttpProxy( {
			url : basepath + '/lookup.json?name=DEM0100007'
		}),
		reader : new Ext.data.JsonReader( {
			root : 'JSON',
			totalProperty : 'list'
		}, [ 'key', 'value' ])
	});
	
	var shareStore = new Ext.data.ArrayStore({//共享范围store
        fields:['myId','displayText'],
        data : [['0','私有'], ['1','全行共享' ],['2','本级机构共享'],['3','辖内机构共享']]
	});
	
	var custfrom = new Ext.data.ArrayStore({//共享范围store
        fields:['myId','displayText'],
        data : [ ['1','客户筛选' ],['2','规则自动筛选']]
	});
	
	 var search = new Ext.FormPanel({
		 frame:true,
		 title : '<span style="font-weight:normal">高级查询条件</span>',
		 bodyStyle:'padding:5px 5px 0',
		 width: 800,
		 autoHeight:true,
		 layout : 'column',
		 items : [ {
			 layout : 'form',
			 columnWidth : .25,
			 labelWidth : 120,
			 items : [ {
				 xtype : 'textfield',
				 fieldLabel : '客户号',
				 name : 'custId',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '客户名称',
				 name : 'custZhName',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '英文名称',
				 name : 'custEnName',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 xtype : 'combo',
				 fieldLabel : '客户状态',
				 labelStyle : 'text-align:right;',
				 hiddenName : 'custStat',
				 store : custStat,
				 emptyText:'请选择',
				 displayField : 'value',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '区域ID',
				 name : 'areaId',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 },{
				 xtype : 'combo',
				 width : 200,
				 fieldLabel : '性别',
				 labelStyle : 'text-align:right;',
				 forceSelection:true,
				 triggerAction:'all',
				 mode:'local',
				 store:new Ext.data.ArrayStore({
					 fields:['myId','displayText'],
					 data:[[0,'请选择'],[1,'男'],[2,'女']]
				 }),
				 valueField:'myId',
				 displayField:'displayText',
				 name : 'sex',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '单位所属行业',
				 name : 'unitAffiCalling',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '工资账号开户银行',
				 name : 'wagesAccOpenBnk',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 } , {
				 xtype : 'textfield',
				 fieldLabel : '接受渠道',
				 name : 'wagesAccOpenBnk',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }]
		 }, {
			 layout : 'form',
			 columnWidth : .25,
			 labelWidth : 120,
			 items : [ {
				 xtype : 'textfield',
				 fieldLabel : '其他名称',
				 name : 'otherName',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '邮政编码',
				 name : 'postNo',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '国家或地区',
				 name : 'contryArea',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '所属客户经理',
				 name : 'affiCustManager',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 } , {
				 xtype : 'textfield',
				 fieldLabel : '合并标志',
				 name : 'hbFlag',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 },{
				 xtype : 'combo',
				 store : marrgStatusType,
				 fieldLabel : '婚姻状况',
				 hiddenName : 'marrgStatus',
				 valueField : 'key',
				 displayField : 'value',
				 labelStyle : 'text-align:right;',
				 triggerAction:'all',
				 forceSelection : true,
				 emptyText:'请选择',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '单位地址',
				 name : 'unitAddr',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 } , {
				 xtype : 'textfield',
				 fieldLabel : '授信额度',
				 name : 'unitAddr',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '贡献度',
				 name : 'unitAddr',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }]
		 }, {
			 layout : 'form',
			 columnWidth : .25,
			 labelWidth : 120,
			 items : [ {
				 xtype : 'textfield',
				 fieldLabel : '客户大类',
				 name : 'custTyp',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 xtype : 'combo',
				 fieldLabel : '客户信用等级',
				 store:custCreditLevel,
				 name : 'custCreditLevel',
				 emptyText : '请选择',
				 labelStyle : 'text-align:right;',
				 valueField : 'key',
				 displayField : 'value',
				 mode : 'local',
				 typeAhead : true,
				 forceSelection : true,
				 triggerAction : 'all',
				 emptyText : '请选择',
				 selectOnFocus : true,
				 width : '100',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '联系电话',
				 name : 'linkPhone',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 store : papersStore,
				 xtype : 'combo',
				 resizable : true,
				 fieldLabel : '证件类型',
				 labelStyle : 'text-align:right;',
				 editable:false,
				 name : 'docuType',
				 hiddenName : 'docuType',
				 valueField : 'key',
				 displayField : 'value',
				 mode : 'local',
				 typeAhead : true,
				 forceSelection : true,
				 triggerAction : 'all',
				 emptyText : '请选择',
				 selectOnFocus : true,
				 width : '100',
				 anchor : '99%'
			 },{
				 xtype : 'combo',
				 store : eduLevel,
				 fieldLabel : '学历',
				 hiddenName : 'eduLevel',
				 valueField : 'key',
				 displayField : 'value',
				 labelStyle : 'text-align:right;',
				 triggerAction:'all',
				 forceSelection : true,
				 emptyText:'请选择',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '职业',
				 name : 'workTyp',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '年收入',
				 name : 'yearIncome',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '剩余余额',
				 name : 'unitAddr',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }, {
				 xtype : 'textfield',
				 fieldLabel : '兴趣爱好',
				 name : 'unitAddr',
				 labelStyle : 'text-align:right;',
				 anchor : '99%'
			 }]
		 }, {
			 layout : 'form',
			 columnWidth : .25,
				labelWidth : 120,
				items : [ {
					xtype : 'textfield',
					fieldLabel : '联系人',
					name : 'linkMan',
					labelStyle : 'text-align:right;',
					anchor : '99%'
				}, {
					xtype : 'textfield',
					fieldLabel : '手机号码',
					name : 'telephoneNum',
					labelStyle : 'text-align:right;',
					anchor : '99%'
				}, {
					xtype : 'textfield',
					fieldLabel : '通讯地址',
					name : 'commuAddr',
					labelStyle : 'text-align:right;',
					anchor : '99%'
				},{
					xtype : 'textfield',
					fieldLabel : '证件号码',
					name : 'certNum',
					labelStyle : 'text-align:right;',
					anchor : '99%'
				},{
					xtype : 'textfield',
					fieldLabel : '工作单位',
					name : 'wrkUnit',
					labelStyle : 'text-align:right;',
					anchor : '99%'
				}, {
					xtype : 'combo',
					store : position,
					fieldLabel : '职称',
					hiddenName : 'position',
					valueField : 'key',
					displayField : 'value',
					labelStyle : 'text-align:right;',
					triggerAction:'all',
					forceSelection : true,
					emptyText:'请选择',
					anchor : '99%'
				}, {
					xtype : 'combo',
					store : folk,
					fieldLabel : '民族',
					hiddenName : 'folk',
					valueField : 'key',
					displayField : 'value',
					labelStyle : 'text-align:right;',
					triggerAction:'all',
					forceSelection : true,
					emptyText:'请选择',
					anchor : '99%'
				} , {
					xtype : 'textfield',
					fieldLabel : '持有产品',
					name : 'unitAddr',
					labelStyle : 'text-align:right;',
					anchor : '99%'
				}]
		 }]
	 });
	 var addRoleWindow = new Ext.Window({
			layout : 'fit',
			width : 1000,
			height : 400,
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
			items : [search],
			buttons : [
			           {
			        	   text : '查询',
			        	   handler : function() {
			        	   var conditionStr1 =  search.getForm().getValues(false);
			        	   store.baseParams={
			        			   'condition':Ext.encode(conditionStr1)								
			        	   };
			        	   store.reload({
			        		   params : {
			        		   start : 0,
			        		   limit : bbar.pageSize
			        	   	}
			        	   }); 
			        	   addRoleWindow.hide();
			           }
			           }, {
			        	   text : '重置',
			        	   handler : function() {
			        	   search.getForm().reset();
			           }
			           }, {
			        	   text : '关闭',
			        	   handler : function() {
			        	   search.getForm().reset();
			        	   addRoleWindow.hide();
			  	        }
			           }]
			});
	//客户群查询条件
	var simple = new Ext.FormPanel({
	    region: 'north',
	    height:80,
		frame:true,
		id:'queryGroup',
		width: '100%',
		labelAlign:'center',
		layout:'column',
		items:[{
		    columnWidth:.25,
		    layout: 'form',
		    items: [{
		        xtype:'textfield',
		        fieldLabel: '客户群编号',
		        labelStyle: 'text-align:right;',
		        name:'CUST_BASE_NUMBER',
		        anchor:'90%'
		    }]
		}, {
		    columnWidth:.25,
		    layout: 'form',
		    items: [{
		        xtype:'textfield',
		        fieldLabel: '客户群名称',
		        labelStyle: 'text-align:right;',
		        name: 'CUST_BASE_NAME',
		        anchor:'90%'
		    }]
		},{
		    columnWidth:.25,
		    layout: 'form',
		    items: [{
		        xtype:'datefield',
		        fieldLabel: '客户群创建日期',
		        labelStyle: 'text-align:right;',
		        format:'Y-m-d', //日期格式化
		        name: 'CUST_BASE_CREATE_DATE',
		        anchor:'90%'
		    }]
		}],
		buttonAlign:'center',
		buttons: [{
			text: '查询',
			handler : function() {
    			store.load({
    			    params : {
    			        start : 0,
    			        limit : bbar.pageSize 
    			    }
    			});
		    }
		},{
			text: '重置',
			handler : function() {
			    simple.getForm().reset();   
		    }
		}]
	});
	var qForm2=new Ext.FormPanel({
		title:'客户查询',
		frame:true,
		border:false,
		id : 'qForm2',
		labelAlign:'right',
		hidden : true,
		items : []
	});
	
	//新增客户群成员
	var addCustomer=new Ext.FormPanel({
	    title:'客户查询',
	    frame:true,
		border:false,
		labelAlign:'right',
		layout:'column',
		items : [{
		    columnWidth : .33,
		    labelWidth : 80, // 标签宽度
		    layout : 'form',
		    items : [{ 
		        xtype : 'textfield',
		        fieldLabel : '客户名称',
		        name : 'CUST_ZH_NAME',
		        anchor : '95%'
		    }]
		},{
		    columnWidth : .33,
		    labelWidth : 80, // 标签宽度
		    layout : 'form',
		    items : [{
		        xtype : 'textfield',
		        fieldLabel : '证件号码',
		        name : 'CERT_NUM',
		        anchor : '95%'
		    }]
		},{
		    columnWidth : .33,
		    labelWidth : 80, // 标签宽度
		    layout : 'form',
		    items : [{
		        xtype : 'combo',
		        store : custTypStore,
		        fieldLabel : '客户类型',   
		        hiddenName : 'CUST_TYP',
		        name:'CUST_TYP',
		        valueField : 'key',
		        displayField : 'value',
		        labelStyle : 'text-align:right;',
		        triggerAction:'all',
		        forceSelection : true,
		        anchor : '95%'
		    }]
		},{
		    columnWidth : .33,
		    layout : 'form',
		    items : [{ 
		        xtype : 'textfield',
		        hidden :true,
		        fieldLabel : 'id',
		        labelStyle:{
		            width:'120px'
		        },	
		        Width:'100',
		        id : 'cbid',
		        name : 'id',
		        anchor : '90%'
		    }]
		}],
		buttonAlign:'center',
		buttons:[{
		    text:'查询',
		    width:80,
		    handler:function(){
		        var conditionStr =  addCustomer.getForm().getValues(false);
			    cusstore.baseParams = {
					"condition":Ext.encode(conditionStr)
			    };
			    cusstore.reload({ 
			        params : {
						start : 0,
						limit : cusbbar.pageSize 
					}
			    });
		    }
		}, {
		    text : '高级查询',
		    handler : function() {
		        addRoleWindow.show();
		    }
		},{
		    text:'重置',
		    handler : function() {
		        addCustomer.getForm().reset();
	        }
		}]
	});
	
	//新增客户群成员的表格面板 
	var custbar = new Ext.Toolbar({
		items:[{
		    text:'归入客户群',
		    iconCls:'guiRuIconCss',
		    handler:function(){
		        batchAdd();
		    }
		}]
	});
	var cussm = new Ext.grid.CheckboxSelectionModel();
	var cusrownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});

	// 定义列模型
	var cuscm = new Ext.grid.ColumnModel([cusrownum,cussm,
        {header : '客户号', dataIndex : 'CUST_ID',sortable : true,width : 150}, 
        {header : '客户名称', dataIndex : 'CUST_ZH_NAME',sortable : true,width : 150 }, 
        {header : '证件类型',dataIndex : 'CERT_TYPE',sortable : true,width : 150},
        {header : '证件号码',dataIndex : 'CERT_NUM',sortable : true,width : 150}, 
        {header : '客户类型',dataIndex : 'CUST_TYP',sortable : true,width : 150,
            renderer : function(value){
            if(!value)
                return '';
            else if(custTypStore.query('key',value,false,true).first()==undefined)
                return '';
            else	
                return custTypStore.query('key',value,false,true).first().get('value');
            }
        }
    ]);

	/**
	 * 数据存储
	 */
	var cusstore = new Ext.data.Store({
		restful:true,	
		proxy : new Ext.data.HttpProxy({url:basepath+'/querycustomerdescription.json',
			failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if(resultArray == 403) {
				Ext.Msg.alert('提示','您没有此权限!');
			} 
		}
		}),
		reader: new Ext.data.JsonReader({
			totalProperty : 'json.count',
			root:'json.data'
		}, [ 'CUST_ID','CUST_ZH_NAME','CERT_TYPE','CERT_NUM','CUST_LEV','CUST_TYP'])
	});

	
	// 每页显示条数下拉选择框
	var cuspagesize_combo = new Ext.form.ComboBox({
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
	var cusnumber = parseInt(cuspagesize_combo.getValue());
	cuspagesize_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
		cusbbar.pageSize = parseInt(cuspagesize_combo.getValue());
		cusstore.reload({
			params : {
			start : 0,
			limit : parseInt(cuspagesize_combo.getValue())
		}
		});
	});
	
	var cusbbar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : cusnumber,
		store : cusstore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : ['-', '&nbsp;&nbsp;', cuspagesize_combo]
	});

	var cusGrid = new Ext.grid.GridPanel({
		height: document.body.scrollHeight-155,
		id:'viewgrid',
		frame : true,
		autoScroll : true,
		store : cusstore, // 数据存储
		stripeRows : true, // 斑马线
		cm : cuscm, // 列模型
		sm : cussm, // 复选框
		bbar : cusbbar,
        tbar:custbar,
        viewConfig : {
		},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	cusGrid.on('rowdblclick', function(cusGrid, rowIndex, event) {
		custwindow(window.Ext.getCmp('viewgrid').getSelectionModel().selections.items[0].data.CUST_ID);

	});
	
	
//
//	var addCustomerGroup1=new Ext.FormPanel({ //新增客户群
//	    name:'addCustomerGroup',
//	    id:'addCustomerGroup',
//	    region : 'center',
//	    labelAlign:'right',
//	    frame:true,
//	    border:false,
//	    split:true,
//	    items:[{
//            layout:'column',
//            items : [{
//                columnWidth : .3333,
//                layout : 'form',
//                items : [{ 
//                	xtype : 'textfield',
//                	fieldLabel : '*客户群名称',
//                    allowBlank : false,
//                    maxLength :20,
//                    labelStyle: 'text-align:right;',
//                    Width:'100',
//                    name : 'customerBaseName',
//                    anchor : '90%'
//                }]
//            },{ columnWidth : .3333,
//                layout : 'form',
//                items : [{ 
//                	id : 'shareFlagt1',
//                    fieldLabel: '共享范围',
//                    xtype : 'combo',
//                    editable : false,
//                    name : 'shareFlag',
//                    hiddenName : 'shareFlag',
//                    mode : 'local',
//                    triggerAction:'all',
//                    store:shareStore,
//                    valueField:'myId',
//                    displayField:'displayText',
//                    anchor:'90%'
//                }]
//            },{ columnWidth : .3333,
//                layout : 'form',
//                items : [{ 
//                	id : 'shareFlagt11',
//                    fieldLabel: '客户来源',
//                    xtype : 'combo',
//                    editable : false,
//                    name : 'shareFlag',
//                    hiddenName : 'shareFlag',
//                    mode : 'local',
//                    triggerAction:'all',
//                    store:custfrom,
//                    valueField:'myId',
//                    displayField:'displayText',
//                    anchor:'90%',
//                    listeners:{
//                        //scope: '1',
//                        'select': onchangecust
//                   }
//                }]
//            },{
//                columnWidth : .00000001,
//                layout : 'form',
//                items : [{ 
//                	xtype : 'textfield',
//                	hidden:true,
//                    fieldLabel : '客户群编号',
//                    labelStyle: 'text-align:right;',
//                    Width:'100',
//                    name : 'asdf',
//                    anchor : '90%'
//                }]
//            },{
//                layout : 'form',
//                items : [{ 
//                	hidden :true,
//                	xtype : 'textfield',
//                    fieldLabel : 'id',
//                    labelStyle:{
//                        width:'120px'
//                    },	
//                    labelStyle: 'text-align:right;',
//                    Width:'100',
//                    name : 'id',
//                    anchor : '90%'
//                }]
//            }]
//        
//    },{
//        layout:'form',
//        items:[{
//            name : 'customerBaseDescribe',
//            anchor:'95%',
//            xtype:'textarea',
//            maxLength :360,
//            fieldLabel : '客户群描述'
//        }]
//    }]
//	});
	
	var updateCustomerGroup=new Ext.FormPanel({ //修改客户群
	    labelAlign:'right',
        frame:true,
        border:false,
        split:true,
	    items:[{
        layout:'column',
        items : [{
        	columnWidth : .5,
        	layout : 'form',
        	items : [{ 
        		xtype : 'textfield',
        		fieldLabel : '*客户群名称',
        		allowBlank : false,
        		maxLength:20,
        		labelStyle: 'text-align:right;',
        		Width:'100',
        		name : 'customerBaseName',
        		anchor : '90%'
        	}]
        	},{
                columnWidth : .5,
                layout : 'form',
                items : [{ 
        		fieldLabel: '共享范围',
        		xtype : 'combo',
        		editable : false,
        		name : 'shareFlag',
        		hiddenName : 'shareFlag',
        		mode : 'local',
        		triggerAction:'all',
        		store:shareStore,
        		valueField:'myId',
        		displayField:'displayText',
        		anchor:'90%'
        	}]
        },{
        	columnWidth : .5,
        	layout : 'form',
        	items : [{ 
        		xtype : 'textfield',
        		hidden:true,
        		fieldLabel : '客户群编号',
        		labelStyle: 'text-align:right;',
        		Width:'100',
        		name : 'sdf',
        		anchor : '90%'
        	}]
        },{
        	layout : 'form',
        	items : [{ 
        		hidden :true,
        		xtype : 'textfield',
        		fieldLabel : 'id',
        		labelStyle:{
        		    width:'120px'
        	    },	
        	    Width:'100',
        	    name : 'id',
        	    anchor : '90%'
        	}]
        }]
	},{
	    layout:'form',
	    items:[{
	        name : 'customerBaseDescribe',
	        anchor:'95%',
	        maxLength :360,
	        xtype:'textarea',
	        fieldLabel : '客户群描述'
	    }]
	}]
	  });
	
	var updateCustomerGroupz=new Ext.FormPanel({ //修改客户群
		
		region: 'north',
	    height:80,
		frame:true,
		//id:'queryGroup',
		width: '100%',
		//labelAlign:'center',
		//layout:'column',
	    labelAlign:'right',
        frame:true,
        border:false,
        split:true,
	    items:[{
        layout:'column',
        items : [{
        	columnWidth : .5,
        	layout : 'form',
        	items : [{ 
        		xtype : 'textfield',
        		fieldLabel : '*客户群名称',
        		allowBlank : false,
        		maxLength:20,
        		labelStyle: 'text-align:right;',
        		Width:'100',
        		name : 'customerBaseName',
        		anchor : '90%'
        	}]
        	},{
                columnWidth : .5,
                layout : 'form',
                items : [{ 
        		fieldLabel: '共享范围',
        		xtype : 'combo',
        		editable : false,
        		name : 'shareFlag',
        		hiddenName : 'shareFlag',
        		mode : 'local',
        		triggerAction:'all',
        		store:shareStore,
        		valueField:'myId',
        		displayField:'displayText',
        		anchor:'90%'
        	}]
        },{
        	columnWidth : .5,
        	layout : 'form',
        	items : [{ 
        		xtype : 'textfield',
        		hidden:true,
        		fieldLabel : '客户群编号',
        		labelStyle: 'text-align:right;',
        		Width:'100',
        		name : 'sdf',
        		anchor : '90%'
        	}]
        },{
        	layout : 'form',
        	items : [{ 
        		hidden :true,
        		xtype : 'textfield',
        		fieldLabel : 'id',
        		labelStyle:{
        		    width:'120px'
        	    },	
        	    Width:'100',
        	    name : 'id',
        	    anchor : '90%'
        	}]
        }]
	},{
	    layout:'form',
	    items:[{
	        name : 'customerBaseDescribe',
	        anchor:'95%',
	        maxLength :360,
	        xtype:'textarea',
	        fieldLabel : '客户群描述'
	    }]
	}]
	  });
	
	
	
	
	  var sm4 = new Ext.grid.CheckboxSelectionModel();//复选框

	  var rownum4 = new Ext.grid.RowNumberer({ // 定义自动当前页行号
		  header : 'No.',
		  width : 28
	  });
	  
	  var cm4 = new Ext.grid.ColumnModel([rownum4,  // 定义列模型
	                                      {header : 'id',dataIndex : 'id',sortable : true,hidden :true},
	                                      {header : '客户名称',dataIndex :'CUST_NAME',sortable : true}
	                                      ]);

	  var store4 = new Ext.data.Store({//数据存储
		  restful:true,	
		  proxy : new Ext.data.HttpProxy({url:basepath+'/queryimportquery.json'}),
		  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [
		      {name:'CUST_ID'},
		      {name:'CUST_ZH_NAME'}
		      ])
	  });
	  var pagesize_combo4 = new Ext.form.ComboBox({
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
	  var number4 = parseInt(pagesize_combo4.getValue());
	 
	  pagesize_combo4.on("select", function(comboBox) { // 改变每页显示条数reload数据
	  			bbar4.pageSize = parseInt(pagesize_combo4.getValue());
	  			store4.reload({
	  						params : {
	  							start : 0,
	  							limit : parseInt(pagesize_combo4.getValue())
	  						}
	  					});
	  		});
	 
	  var bbar4 = new Ext.PagingToolbar({ //分页工具栏
		  pageSize : number4,
		  store : store4,
		  displayInfo : true,
		  displayMsg : '显示{0}条到{1}条,共{2}条',
		  emptyMsg : "没有符合条件的记录",
		  items : ['-', '&nbsp;&nbsp;', pagesize_combo4]
	  });
	  
	  var grid4 = new Ext.grid.GridPanel({//信贷台账
		  height :215,
		  frame : true,
		  autoScroll : true,
		  store : store4, // 数据存储
		  stripeRows : true, // 斑马线
		  cm : cm4, // 列模型
		  bbar : bbar4,// 分页工具栏
		  loadMask : {
		  msg : '正在加载表格数据,请稍等...'
	  }
	  });
		
	  var updatewin=new Ext.Window({
		  title:"修改客户群信息",
		  layout : 'fit',
		  width : 600,
		  height :180,
		  closable : true,
		  resizable : false,
		  collapsible : false,
		  draggable : true,
		  closeAction : 'hide',
		  modal : true, // 模态窗口 
		  animCollapse : false,//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
		  maximizable : true,
		  border : false,
		  closable : true,
		  animateTarget : Ext.getBody(),
		  constrain : true,
		  items : [updateCustomerGroup],
		  buttonAlign:'center',
		  buttons:[{
			  text:'保存',
			  handler:function(){
			  	if(!updateCustomerGroup.getForm().isValid())
			  	{ 
			  		Ext.Msg.alert('提示','输入有误!');
			  		return false;
			  	}
			  	Ext.Ajax.request({
			  		url:basepath+'/customerBase.json',
			  		method: 'POST',
			  		form:updateCustomerGroup.getForm().id,
			  		success : function(response) {
			  		Ext.Msg.alert('提示', '修改成功');
			  		store.load(
			  				{ params : {
			  					start : 0,
			  					limit : bbar.pageSize }} );
			  	},
			  	failure : function(response) {
			  		var resultArray = Ext.util.JSON.decode(response.status);
			  		if(resultArray == 403) {
			  			Ext.Msg.alert('提示','您没有此权限!');
			  		} else {
			  			Ext.Msg.alert('提示','修改失败!');
			  		}
			  	},
			  	params : {
			  		'operate':'update'
			  	}
			  	});
			  	updatewin.hide();
		  }
		  },{
			  text: '取消',
			  handler:function(){
			  updatewin.hide();
		  }
		  }]	
	  });
	  
	  
	  var updatewinz=new Ext.Window({
		  title:"修改客户群信息",
		  layout : 'border',
		  width : 600,
		  height :380,
		  closable : true,
		  resizable : false,
		  collapsible : false,
		  draggable : true,
		  closeAction : 'hide',
		  modal : true, // 模态窗口 
		  animCollapse : false,//下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
		  maximizable : true,
		  border : false,
		  closable : true,
		  animateTarget : Ext.getBody(),
		  constrain : true,
		  items : 
			  [
			   	updateCustomerGroupz,
			   	tarDictListPanel
			  ],
		  buttonAlign:'center',
		  buttons:[{
			  text:'保存',
			  handler:function(){
			  	if(!updateCustomerGroupz.getForm().isValid())
			  	{ 
			  		Ext.Msg.alert('提示','输入有误!');
			  		return false;
			  	}
			  	Ext.Ajax.request({
			  		url:basepath+'/customerBase.json',
			  		method: 'POST',
			  		form:updateCustomerGroupz.getForm().id,
			  		success : function(response) {
			  		Ext.Msg.alert('提示', '修改成功');
			  		store.load(
			  				{ params : {
			  					start : 0,
			  					limit : bbar.pageSize }} );
			  	},
			  	failure : function(response) {
			  		var resultArray = Ext.util.JSON.decode(response.status);
			  		if(resultArray == 403) {
			  			Ext.Msg.alert('提示','您没有此权限!');
			  		} else {
			  			Ext.Msg.alert('提示','修改失败!');
			  		}
			  	},
			  	params : {
			  		'operate':'update'
			  	}
			  	});
			  	updatewin.hide();
		  }
		  },{
			  text: '取消',
			  handler:function(){
			  updatewin.hide();
		  }
		  }]	
	  });
	  
	  
	  var cussmGroupMemberCheck = new Ext.grid.CheckboxSelectionModel();//复选框
	  
	  var cusGrouprownum = new Ext.grid.RowNumberer({
					header : 'No.',
					width : 28
				});			
				
	  var groupMemberCol = new Ext.grid.ColumnModel([
	                                                 cusGrouprownum,cussmGroupMemberCheck,
	                                                 {header : 'id', dataIndex : 'ID',sortable : true,width : 150,hidden:true}, 
	                                                 {header : '客户号', dataIndex : 'CUST_ID',sortable : true,width : 150},
	                                                 {header : '客户名称', dataIndex : 'CUST_ZH_NAME',sortable : true,width : 100 }, 
	                                                 {header : '证件类型', dataIndex : 'CERT_TYPE',sortable : true,width : 150},
	                                                 {header : '证件号码',dataIndex : 'CERT_NUM',sortable : true,width : 100}, 
	                                                 {header:'加入群组日期',dataIndex:'RELA_CREATE_DT',sortable : true}
	                                                 ]);	
	  var custGroupStore = new Ext.data.Store({
		  restful:true,
		  proxy : new Ext.data.HttpProxy({
			  url:basepath+'/querycustomerbase2.json'}),
		  reader: new Ext.data.JsonReader({
			  root:'json.data',
			  totalProperty : 'json.count'
		  }, [ 'ID','CUST_ID','CUST_ZH_NAME','CERT_TYPE','CERT_NUM','RELA_CREATE_DT'])//数据映射
			});
		
	  custGroupStore.on('beforeload', function() {
		  this.baseParams = {
				  cbid: Ext.getCmp('cbid').getValue()
		  };});
		
	  var cusGroupcombo = new Ext.form.ComboBox({
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
		
	  var cusGroupnumber = parseInt(cusGroupcombo.getValue());
	
	  cusGroupcombo.on("select", function(comboBox) {    // 改变每页显示条数reload数据
		  cusGroupBbar.pageSize = parseInt(cusGroupcombo.getValue()),
		  custGroupStore.reload({
			  params : {
			  start : 0,
			  limit : parseInt(cusGroupcombo.getValue())
		  }
		  });
	  });
	  var cusGroupBbar = new Ext.PagingToolbar({
		  pageSize : cusGroupnumber,
		  store : custGroupStore,
		  displayInfo : true,
		  displayMsg : '显示{0}条到{1}条,共{2}条',
	       //plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
		  emptyMsg : "没有符合条件的记录",
		  items : ['-', '&nbsp;&nbsp;', cusGroupcombo
		           ]
	  });
						
	  var cusGroupMemeberGrid = new Ext.grid.GridPanel({
		  title:'客户群成员列表',
		  frame : true,
		  height: document.body.scrollHeight-58,
		  autoScroll : true,
		  store: custGroupStore,
		  stripeRows : true, // 斑马线
		  cm : groupMemberCol,
		  sm:cussmGroupMemberCheck,
		  tbar:[{'text':'移除客户群',iconCls:'deleteIconCss',handler:function(){
			  batchDelete();
		  }},
		  new Com.yucheng.bob.ExpButton({
			  formPanel : 'qForm2',
			  iconCls:'exportIconCss',
			  id:'exportbt',
			  url : basepath+'/querycustomerbase2.json?'
		  	})					 
		  ],
		  bbar : cusGroupBbar,
		  viewConfig : {},
		  loadMask : {
			  msg : '正在加载表格数据,请稍等...'
		  }
	  });
	    
	  var newmember=new Ext.Window(
				{
					layout : 'fit',
					width:1000,
					height :420,
					closable : true,
					resizable : false,
					collapsible : false,
					maximizable: true,
					maximized:true,
					draggable : true,
					closeAction : 'hide',
					title : '客户筛选',
					buttonAlign:'center',
					modal : true, // 模态窗口 
					animCollapse : false,
					border : false,
					closable : true,
					animateTarget : Ext.getBody(),
					constrain : true,
					items : [{
						layout : 'column',
						border : false,
						items : [
						         {
						        	 columnWidth : .53,
						        	 layout : 'form',
						        	 border : false,
						        	 items : [{
						                 region : 'north',
						                 id : 'north-panel',
						                 height : 120,
						                 layout : 'fit',
						                 items : [ addCustomer ]
						             },{
						                 region : 'center',
						                 id : 'center-panel',
						                 layout : 'fit',
						                 height : 300,
						                 items : [ cusGrid ]
						             }]
						         },{
						        	 columnWidth : .47,
						        	 layout : 'form',
						        	 border : false,
						        	 items : [{
						                 region : 'north',
						                 id : 'north-panel1',
						                 layout : 'fit',
						                 items : [ qForm2 ]
						             },{
						                 region : 'center',
						                 id : 'center-panel1',
						                 layout : 'fit',
						                 height : 420,
						                 items : [ cusGroupMemeberGrid ]
						             }]
						         }
						         ]
					}],
					buttonAlign:'center',
					buttons:[{
						text: '关闭',
						handler:function(){
						store.load(
								{ params : {
									start : 0,
									limit : bbar.pageSize }} );
						newmember.hide();
					}
					}]	
				});
	  
	 

 // 表格工具栏
	  var tbar = new Ext.Toolbar({
		  items : [ 
		            {
			  text : '新增客户群',
			  iconCls:'addIconCss',
			  handler : function() 
			  {
				  
				 
				  var addCustomerGroup=new Ext.FormPanel({ //新增客户群
					    name:'addCustomerGroup',
					    id:'addCustomerGroup',
					    region : 'center',
					    labelAlign:'right',
					    frame:true,
					    border:false,
					    split:true,
					    items:
					    [
							{
							    layout:'form',
							    items:[
//							           {
//							        name : 'customerBaseDescribe',
//							        anchor:'95%',
//							        xtype:'textarea',
//							        maxLength :360,
//							        fieldLabel : '客户群描述'
//							    }
							    { 
		     		 		 		xtype : 'textfield',
		     		 		 		fieldLabel : '*客户群名称',
		     		 		 		allowBlank : false,
		     		 		 		maxLength :20,
		     		 		 		labelStyle: 'text-align:right;',
		     		 		 		Width:'100',
		     		 		 		name : 'customerBaseName',
		     		 		 		anchor : '95%'
		     		 		 	}
							    ]
							},
					     	{
					     		layout:'column',
					     		items : 
					     		[
					     		 	{
					     		 		columnWidth : .5,
					     		 		layout : 'form',
					     		 		items : 
					     		 		[
//					     		 		 	{ 
//					     		 		 		xtype : 'textfield',
//					     		 		 		fieldLabel : '*客户群名称',
//					     		 		 		allowBlank : false,
//					     		 		 		maxLength :20,
//					     		 		 		labelStyle: 'text-align:right;',
//					     		 		 		Width:'100',
//					     		 		 		name : 'customerBaseName',
//					     		 		 		anchor : '90%'
//					     		 		 	},
					     		 		 	{ 
							                	//id : 'shareFlagt1',
							                    fieldLabel: '共享范围',
							                    xtype : 'combo',
							                    editable : false,
							                    name : 'shareFlag1',
							                    hiddenName : 'shareFlag',
							                    mode : 'local',
							                    triggerAction:'all',
							                    store:shareStore,
							                    valueField:'myId',
							                    value:'0',
							                    displayField:'displayText',
							                    anchor:'90%'
							                }
					     		 		 ]
					     		 	},
					     		 	{
					     		 		columnWidth : .5,
					     		 		layout : 'form',
					     		 		items : 
					     		 		[
//					     		 		 	{ 
//					     		 		 		//id : 'shareFlagt1',
//							                    fieldLabel: '共享范围1111111111',
//							                    xtype : 'hidden',
//							                    editable : false,
//							                    name : 'shareFlag',
//							                    hiddenName : 'shareFlag',
//							                    mode : 'local',
//							                    triggerAction:'all',
//							                    store:shareStore,
//							                    valueField:'myId',
//							                    value:'0',
//							                    displayField:'displayText',
//							                    anchor:'90%'
//							                },
							                { 
							                	//id : 'shareFlagt11',
							                    fieldLabel: '客户来源',
							                    xtype : 'combo',
							                    editable : false,
							                    value:'1',
							                    name : 'custFromName1',
							                    hiddenName : 'custFrom',
							                    mode : 'local',
							                    triggerAction:'all',
							                    store:custfrom,
							                    valueField:'myId',
							                    displayField:'displayText',
							                    anchor:'90%',
							                    listeners:{
							                        //scope: '1',
							                        'select': onchangecust
							                   }
							                }
					     		 		 ]
					     		 	},
//					     		 	{ 
//					     		 		columnWidth : .3333,
//					     		 		layout : 'form',
//					     		 		items : [{ 
//				                	//id : 'shareFlagt11',
//				                    fieldLabel: '客户来源',
//				                    xtype : 'combo',
//				                    editable : false,
//				                    value:'1',
//				                    name : 'shareFlag11',
//				                    hiddenName : 'shareFlag11',
//				                    mode : 'local',
//				                    triggerAction:'all',
//				                    store:custfrom,
//				                    valueField:'myId',
//				                    displayField:'displayText',
//				                    anchor:'90%',
//				                    listeners:{
//				                        //scope: '1',
//				                        'select': onchangecust
//				                   }
//				                }]
//				            },
				            {
				                columnWidth : .2,
				                layout : 'form',
				                items : [{ 
				                	xtype : 'textfield',
				                	hidden:true,
				                    fieldLabel : '客户群编号',
				                    labelStyle: 'text-align:right;',
				                    Width:'100',
				                    name : 'asdf',
				                    anchor : '90%'
				                }]
				            },
				            {
				                columnWidth : .2,
				                layout : 'form',
				                items : [{ 
				                	id:'custFromName3',
				                	xtype : 'textfield',
				                	hidden:true,
				                    //fieldLabel : '客户群编号',
				                    labelStyle: 'text-align:right;',
				                    Width:'100',
				                    value:'客户筛选',
				                    name : 'custFromName',
				                    anchor : '90%'
				                }]
				            },
				            {
				                layout : 'form',
				                items : [{ 
				                	hidden :true,
				                	xtype : 'textfield',
				                    fieldLabel : 'id',
				                    labelStyle:{
				                        width:'120px'
				                    },	
				                    labelStyle: 'text-align:right;',
				                    Width:'100',
				                    name : 'id',
				                    anchor : '90%'
				                }]
				            }]
				        
				    },{
				        layout:'form',
				        items:[{
				            name : 'customerBaseDescribe',
				            anchor:'95%',
				            xtype:'textarea',
				            maxLength :360,
				            fieldLabel : '客户群描述'
				        }]
				    }]
					});
//				  Ext.getCmp('shareFlagt1').setValue('0');
//				  Ext.getCmp('shareFlagt11').setValue('1');
				  
				  var tarDictListPanelnew1 = new Ext.Panel
					(
						{
							id:'tarDictListPanelnew1',
							region : 'south',
							height : 250,
							html: ''
						}
					);
				  
				  
				  
				  var win=new Ext.Window
				  (
						 {
							 title:"新增客户群",
							 layout : 'border',
							 width : 700,
							 height :480,
							 //closable : true,
							 closable : false,
							 resizable : false,
							 collapsible : false,
							 draggable : true,
							 closeAction : 'hide',
							 modal : true, // 模态窗口 
							  //下拉层的动画效果必须关闭,否则将出现Flash图标下拉动画过场异常的现象
							 animCollapse : false,
							 maximizable : true,
							 border : false,
							 closable : true,
							 animateTarget : Ext.getBody(),
							 constrain : true,
							 items : [addCustomerGroup,tarDictListPanelnew1],
							 buttonAlign:'center',
							 listeners:
							 {
								 "hide":function()
								 {
									 addCustomerGroup.getForm().reset();
								 }
							 }, 
			  	buttons:[{
			  		text:'保存',
			  		handler:function(){
			  		if(!addCustomerGroup.getForm().isValid())
			  		{ 
			  			Ext.Msg.alert('提示','输入信息有误!');
			  			return false;
			  		}
			  		Ext.Ajax.request({
			  			url:basepath+'/customerBase.json',
			  			method: 'POST',
			  			form:addCustomerGroup.getForm().id,
			  			success : function(response) {
			  			Ext.Msg.alert('提示', '成功');
			  			store.load(
			  					{ params : {
			  						start : 0,
			  						limit : bbar.pageSize }} );
			  		},
			  		failure : function(response) {
			  			var resultArray = Ext.util.JSON.decode(response.status);
			  			if(resultArray == 403) {
			  				Ext.Msg.alert('提示','您没有此权限!');
			  			} else {
			  				Ext.Msg.alert('提示','新增失败!');
			  			}
			  		},
			  		params : {
			  			'operate':'add'
			  			}
			  		});
			  		//win.hide();
			  		win.close();
				  	}
			  	},{
			  		text: '取消',
			  		handler:function(){
			  		win.close();
			  	}
			  	}]
			  });
			  win.show();
		  }
		  },'-',{
			  text:'客户筛选',
			  iconCls:'custSelectIconCss',
			  handler : function() {
				  
			  var _record = cusGroupGrid.getSelectionModel().getSelected();
			  var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
			  if (!_record||checkedNodes.length>1) {
				  Ext.MessageBox.alert('修改操作', '请选择要操作的一列！');
			  } else {
				  
				  if(checkedNodes[0].data.customerFrom=='2')
				  {
					  Ext.MessageBox.alert('修改操作', '规则自动生成的客户群不能筛选客户！');
				  }
				  else if(checkedNodes[0].data.customerFrom=='1')
				  {
				  
					  addCustomer.getForm().loadRecord(_record);
					  var temp=checkedNodes[0].data.id;
					  newmember.show();
					  custGroupStore.load({
								  params : {
								  cbid: Ext.getCmp('cbid').getValue()
					  		}
					  });
					  Ext.getCmp("exportbt").url=basepath+'/querycustomerbase2.json?cbid='+temp;
					  addCustomer.getForm().findField('CUST_TYP').setValue(1);
				  
				  }
				  
			  }
		  }
		  },'-',{
			  text:'群成员视图',
			  iconCls:'custGroupMemIconCss',
			  handler : function() {
			  var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
			  if(checkedNodes.length==0)
			  {
				  Ext.Msg.alert('提示', '未选择任何客户群');
				  return ;
			  }
			  else if(checkedNodes.length>1)
			  {
				  Ext.Msg.alert('提示', '您只能选中一个客户群进行查看');
				  return ;
			  }
			  var viewWindow = new Ext.Window(
					  {
						  layout : 'fit',
						  id:'viewWindow',
						  draggable : true,//是否可以拖动
						  closable : true,// 是否可关闭
						  modal : true,
						  closeAction : 'close',
						  maximized:true,
						  titleCollapse : true,
						  buttonAlign : 'center',
						  border : false,
						  animCollapse : true,
						  animateTarget : Ext.getBody(),
						  constrain : true,
						  items : [{ 
							  html:' <div style="width:100%;height:100%;"><div style="position:absolute; left:0px; top:0px; width:180px" id=\'sena_tree\'></div><div style="position:absolute; left:180px; top:0px;height:100%; " id=\'viewport_center\'></div></div>'}  
						  ]});
			  viewWindow.title='您所浏览的客户群为：'+checkedNodes[0].data.customerBaseName;
			  oBaseInfo.base_id=checkedNodes[0].data.id;
			  ScriptMgr = new ScriptLoaderMgr();
			  ScriptMgr.load({        
				  scripts: ['menuOfCustomersBase.js'],        
				  callback: function(){  
			  }
			  }); 
			  viewWindow.show();
		  }
		  },'-',{
			  text:'修改客户群',
			  iconCls:'editIconCss',
			  handler : function() {
				  
			  		updateCustomerBase(cusGroupGrid);
		  }
		  },'-',{
			  text:'删除客户群',
			  iconCls:'deleteIconCss',
			  handler:function()
			  {
			  deleteCustomerBase(cusGroupGrid);
			  store.load(
					  { params : {
						  start : 0,
						  limit : bbar.pageSize }} );
			  }
		  },'-',{
				text : '批量创建商机',
				iconCls:'addIconCss',
				handler : function() {
				resetAddForm();
				addMyBusOpportInit01(cusGroupGrid);	
				}
			},{
	            text : '生成营销活动',
	            iconCls:'addIconCss',
	            handler : function() {
	            	
				addActivityForm.form.reset();
				addActivityProdForm.form.reset();
				addActivityCustForm.form.reset();
				addActivityForm.form.findField('createUser').setValue(__userId);
				addActivityForm.form.findField('test').setValue(__userName);
				addActivityForm.form.findField('createDate').setValue(new Date());
				addActivityForm.form.findField('mktActiStat').setValue(1);
				addActivityForm.form.findField('mktActiName').setValue('小企业扶持贷款推广');
				addActivityForm.form.findField('mktActiType').setValue('推广活动');
				addActivityForm.form.findField('mktActiMode').setValue('宣传');
				addActivityForm.form.findField('mktActiTeam').setValue('小企业贷款组');
				addActivityForm.form.findField('mktActiCost').setValue('1000');
				addActivityForm.form.findField('mktActiAddr').setValue('南京市建邺区应天西路所叶路20号');
				addActivityForm.form.findField('mktActiCont').setValue('宣传小企业的扶持贷款政策，吸引贷款');
				addActivityForm.form.findField('actiCustDesc').setValue('该工业园区的小企业');
				addActivityForm.form.findField('actiOperDesc').setValue('本行支行客户经理');
				addActivityForm.form.findField('actiProdDesc').setValue('小企业扶持到款');
				addActivityForm.form.findField('mktActiAim').setValue('推广');
				addActivityForm.form.findField('actiRemark').setValue('无');
						 				
				addActivityWindow.show();

	            }}]
	  });
	
	  var sm = new Ext.grid.CheckboxSelectionModel();
	  var cusGroupsm = new Ext.grid.CheckboxSelectionModel();
	  var rownum = new Ext.grid.RowNumberer({
		  header : 'No.',
		  width : 28
	  });

	  var cm = new Ext.grid.ColumnModel([rownum,sm,	// 定义列模型
	                                     {header : 'id', dataIndex : 'id',sortable : true,width : 120,hidden : true}, 
	                                     {header : '共享范围',dataIndex : 'shareFlag',sortable : true,width : 135,hidden : true},
	                                     {header : '共享范围',dataIndex : 'shareFlagName',sortable : true,width : 135},
	                                     {header : '客户群编号', dataIndex : 'customerBaseNumber',sortable : true,width : 120 }, 
	                                     {header : '客户群名称',dataIndex : 'customerBaseName',sortable : true,width : 120}, 
	                                     {
	                                    	 header : '客户群成员数', 
	                                    	 dataIndex : 'customerBaseMemberNum',
	                                    	 renderer:function(value)
	                                    	 {
								          	     if(value==''){
								          	    	 return "0";
								          	     }else{
								          	    	 return value;
								          	     }
	                                    	 },
	                                    	 sortable : true,
	                                    	 width : 120 
	                                     },
	                                     {header : '客户群成员来源标识',dataIndex : 'customerFrom',sortable : true,width : 120,hidden : true},
	                                     {header : '客户群成员来源',dataIndex : 'customerFromName',sortable : true,width : 120},
	                                     {header : '客户群描述',dataIndex : 'customerBaseDescribe',sortable : true,width : 120},
	                                     {header : '创建人ID',dataIndex : 'cust_base_create_name',sortable : true,width: 120,hidden:true},
	                                     {header : '创建人',dataIndex : 'createName',sortable : true,width: 120},
	                                     {header : '创建机构ID',dataIndex : 'custBaseCreateOrg',sortable : true,width : 120,hidden : true},
	                                     {header : '创建机构',dataIndex : 'custBaseCreateOrgName',sortable : true,width : 120},
	                                     {header : '客户群创建日期',dataIndex : 'customerBaseCreateDate',sortable : true,width : 120}
	                                     ]);

	  var store = new Ext.data.Store({//数据存储
		  restful:true,
		  proxy : new Ext.data.HttpProxy({url:basepath+'/querycustomerbase.json'
		  }),
		  reader: new Ext.data.JsonReader({
			  totalProperty : 'json.count',
			  root:'json.data'
		  }, [{name: 'id', mapping: 'ID'},
		      {name: 'customerBaseNumber', mapping: 'CUST_BASE_NUMBER'},
		      {name: 'customerBaseName', mapping: 'CUST_BASE_NAME'},
		      {name: 'createName',mapping:'CREATENAME'},
		      {name: 'customerBaseCreateDate', mapping: 'CUST_BASE_CREATE_DATE'},
		      {name: 'customerBaseMemberNum', mapping: 'MEMBERSNUM'},
		      {name: 'customerFrom', mapping: 'CUST_FROM'},
		      {name: 'customerFromName', mapping: 'CUST_FROM_NAME'},
		      {name: 'customerBaseDescribe', mapping: 'CUST_BASE_DESC'},
		      {name: 'shareFlag',mapping :'SHARE_FLAG'},
		      {name: 'shareFlagName',mapping : 'SHARE_FLAG_NAME'},
		      {name: 'custBaseCreateOrg',mapping : 'CUST_BASE_CREATE_ORG'},
		      {name: 'custBaseCreateOrgName',mapping : 'CUST_BASE_CREATE_ORG_NAME'}
		      ])
	  });
	  store.on('beforeload', function() {
		  var conditionStr =  simple.getForm().getValues(false);
		  this.baseParams = {
				  "condition":Ext.encode(conditionStr)
		  };
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
	  pagesize_combo.on("select", function(comboBox) {// 改变每页显示条数reload数据
		  bbar.pageSize = parseInt(pagesize_combo.getValue()),
		  store.reload({
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
	
	  var cusGroupGrid = new Ext.grid.GridPanel({
	      layout:'fit',
		  frame : true,
//		  height: document.body.scrollHeight-100,
		  autoScroll : true,
		  region : 'center', // 返回给页面的div
		  store: store,
		  stripeRows : true, // 斑马线
		  cm : cm,
		  sm:sm,
		  tbar:tbar,
		  bbar : bbar,
		  viewConfig : {},
		  loadMask : {
			  msg : '正在加载表格数据,请稍等...'
		  }
	  });
	  var viewport = new Ext.Viewport({// 布局模型
		  layout : 'fit',
		  frame : true,
		  items:[{
			  layout : 'border',
			  items: [simple,cusGroupGrid]
			  }] 
	  });
	  var updateCustomerBase = function(cusGroupGrid) {
		  
		  
		  
		  var _record = cusGroupGrid.getSelectionModel().getSelected();
		  var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
		  if (!_record||checkedNodes.length>1) {
			  Ext.MessageBox.alert('修改操作', '请选择要操作的一列！');
		  } else {
			  
			  if(checkedNodes[0].data.customerFrom=='2')
			  {
				  //Ext.MessageBox.alert('修改操作', '规则自动生成的客户群不能筛选客户！');
				  var record = cusGroupGrid.getSelectionModel().getSelected();
				  updateCustomerGroupz.getForm().loadRecord(record);
				  tarDictStore.loadData(data1);
				  updatewinz.show();
				  
			  }
			  else if(checkedNodes[0].data.customerFrom=='1')
			  {
			  
			  
				  var record = cusGroupGrid.getSelectionModel().getSelected();
				  updateCustomerGroup.getForm().loadRecord(record);
				  updatewin.show();
			  }
			  
			  
		  }
		  
		  
		  
	  };
	  var deleteCustomerBase = function(cusGroupGrid) {
		  var _record = cusGroupGrid.getSelectionModel().getSelected();
		  if (!_record) {
			  Ext.MessageBox.alert('删除操作', '请选择要操作的一列！');
		  } else {
			  var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
			  var json={'id':[]};
			  for(var i=0;i<checkedNodes.length;i++)
			  {
				  json.id.push(checkedNodes[i].data.id);
			  }
			  Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				  if(buttonId.toLowerCase() == "no"){
					  return;
				  } 
				  Ext.Ajax.request({
					  url:basepath+'/customerBase.json',
					  method: 'POST',
					  success : function(response) {
					  		Ext.Msg.alert('提示', '成功');
					  			store.load();
				  		},
				  		failure : function(response) {
				  			var resultArray = Ext.util.JSON.decode(response.status);
				  			if(resultArray == 403) {
				  				Ext.Msg.alert('提示','您没有此权限!');
				  			} else {
				  				Ext.Msg.alert('提示','失败!');
				  			}
				  		},
				  		params : {
				  			cbid:Ext.encode(json),
				  			'operate':'delete'
				  		}
				  });
			  });
		  }
	  };
	  var batchAdd= function(){
		  var cbid= Ext.getCmp('cbid').getValue();
		  var checkedNodes = cusGrid.getSelectionModel().selections.items;
		  var json={'cust_id':[]};
		  var json2={'cert_num':[]};
		  var json3={'cert_type':[]};
		  var json4={'cust_zh_name':[]};
		  var json5={'rela_create_dt':[]};
		  if(checkedNodes.length==0)
		  {
			  Ext.Msg.alert('提示', '未选择任何客户');
			  return ;
		  }
		  for(var i=0;i<checkedNodes.length;i++)
		  {
			  json.cust_id.push(checkedNodes[i].data.CUST_ID);
			  json2.cert_num.push(checkedNodes[i].data.CERT_NUM);
			  json3.cert_type.push(checkedNodes[i].data.CERT_TYPE);
			  json4.cust_zh_name.push(checkedNodes[i].data.CUST_ZH_NAME);
			  json5.rela_create_dt.push(checkedNodes[i].data.RELA_CREATE_DT);
		  }
		  Ext.Ajax.request({
			  url:basepath+'/customerrelatecustomerbase.json',
			  method: 'POST',
			  success : function(response) {
			  Ext.Msg.alert('提示', '加入成功');
			  custGroupStore.load(
					  {
						  params : {
						  cbid: Ext.getCmp('cbid').getValue()
					  		}
					  }
			  );
		  },	
		  failure : function(response) {
			  var resultArray = Ext.util.JSON.decode(response.status);
			  if(resultArray == 403) {
				  Ext.Msg.alert('提示','您没有此权限!');
			  } else {
				  Ext.Msg.alert('提示','加入失败!');
			  }
		  },
		  params : {
			  'cust_id': Ext.encode(json),
			  'cert_num': Ext.encode(json2),
			  'cert_type': Ext.encode(json3),
			  'cust_zh_name': Ext.encode(json4),
			  'rela_create_dt': Ext.encode(json5),
			  'cbid': cbid,
			  'operate': 'add'
		  }});
	  };
	  var batchDelete=function(){
		  var checkedNodes = cusGroupMemeberGrid.getSelectionModel().selections.items;
				if(checkedNodes.length==0)
				{
					Ext.Msg.alert('提示', '未选择任何客户');
					return ;
				}
				var json={'id':[]};
				for(var i=0;i<checkedNodes.length;i++)
				{
					json.id.push(checkedNodes[i].data.ID);
				}
				var id =checkedNodes[0].data.ID;
				Ext.Ajax.request({url: basepath+'/customerrelatecustomerbase.json',
					method: 'POST',
					success : function(response) {
					Ext.Msg.alert('提示', '删除成功');
					custGroupStore.load(
							{
								params : {
									cbid: Ext.getCmp('cbid').getValue()
								}
							}
					);
				},
				failure : function(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
					if(resultArray == 403) {
						Ext.Msg.alert('提示','您没有此权限!');
					} else {
						Ext.Msg.alert('提示','删除失败!');
					}
				},
				params : {
					'cbid':Ext.encode(json),
					'operate': 'delete'
				}
				});
	  };
	
	  var custwindow=function(custid){
		  var id=custid;
		  var viewpanel = new Ext.Panel({
			  layout : 'fit',
			  items: [{ 
				  layout : 'fit',
				  margins: '0 0 0 0',
				  items : [{ html:'<iframe id="content" name="content2" height="100%" frameborder="no" width="100%" src=\"../customerManager/customerBaseInformation2.jsp?customerId='+id+'\" "/> scrolling="auto"> </iframe>'
				  }]
			  }] 
		  });
		  var viewWindow = new Ext.Window(
				  {
					  layout : 'fit',
					  width : 1000,
					  height : 400,
					  draggable : true,//是否可以拖动
					  closable : true,// 是否可关闭
					  modal : true,
					  closeAction : 'hide',
					  maximized:true,
					  titleCollapse : true,
					  buttonAlign : 'center',
					  border : false,
					  animCollapse : true,
					  animateTarget : Ext.getBody(),
					  constrain : true,
					  items : [viewpanel]
				  });
		  viewWindow.show();
	  };
	});

