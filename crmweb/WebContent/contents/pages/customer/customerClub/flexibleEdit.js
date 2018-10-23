	Ext.onReady(function(){
	var rowNo1=-1;
	var myData = [ ['CNJ2013021800024','圆胜电子科技(昆山)有限公司','营业执照','MW-098334','对公','3987','秦青','19908','南京银行光华支行'] 
		                ,['CNJ2013021800025','苏州宇鑫电子有限公司南京办事','营业执照','VFZ-03438384','对公','3987','秦青','19908','南京银行光华支行']
		                ,['CNJ2013021800026','南京新月桑拿泳池设备有限公司','营业执照','TT-0934384','对公','3987','秦青','19908','南京银行光华支行']
		                ,['CNJ2013021800027','南京策杰贸易有限公司','营业执照','PHZ-234384', '对公','3987','秦青','19908','南京银行光华支行']
		                ,['CNJ2013021800028','南京百世照明电器有限公司','营业执照','H7N9-098322','对公','3987','秦青','19908','南京银行光华支行' ]
		                ,['CNJ2013021800029','南京多美达广告展览有限责任公司','营业执照','AZJ-092280','对公','3987','秦青','19908','南京银行光华支行']
		                ,['CNJ2013021800030','南京望凯电光源有限公司','营业执照','HW-0393382', '对公','3987','秦青','19908','南京银行光华支行']
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
		               title : '关联客户信息',
		               viewConfig:{
//						   forceFit:true,
						   autoScroll:true
						},
		               columns: [
						{header:'客户编号',width:100,dataIndex:'a1'},
						{header:'客户名称',width:120,dataIndex:'a2'},
						{header:'证件类型',width:80,dataIndex:'a3'},
						{header:'证件号码',width:120,dataIndex:'a4'},
						{header:'客户类型',width:80,dataIndex:'a5'},
						{header:'归属客户经理ID',width:120,dataIndex:'a6'},
						{header:'归属客户经理',width:100,dataIndex:'a7'},
						{header:'归属机构ID',width:100,dataIndex:'a8'},
						{header:'归属机构',width:120,dataIndex:'a9'}
		               ],
		               stripeRows: true,
		               width: '150%',
		               height : 450
		           });
	
	 // 试运算展示窗口
        var viewWindow = new Ext.Window( {
            title : '客户信息列表',
            plain : true,
            layout : 'fit',
            width : 1000,
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
            border : false,
            items : [ customergrid ]
        });
	
	var searchIndex1 = new Com.yucheng.crm.common.IndexField({ 
					xtype:'userchoose',
					fieldLabel : '指标列表', 
					id:'searchIndex1',
					name:'searchIndex1',
					hiddenName:'searchIndex11',
					labelStyle: 'text-align:right;',
					singleSelect:true,
					anchor : '90%',
					callback :function(a,b,c,d){
					var mgr_namess = null;
					records1 = tarDictListPanel1.getSelectionModel().selection;
					var mgrIds1 = '';
					mgr_namess = Ext.getCmp('searchIndex1').getValue();
					if (mgr_namess != null && mgr_namess != ''){
		            partitionRuleStore.getAt(rowNo1).data.indexId =this.ID;
		            partitionRuleStore.getAt(rowNo1).data.indexName =this.NAME;
		            tarDictListPanel1.getView().refresh(false);
						}
						}
					});

	var fiexibleSearchPanel = new Ext.form.FormPanel({
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
                     dataIndex : 'leftPartList',
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
								]})
						}),
                     sortable : true
             	}, {
	                header : '指标',
	                width : 200,
	                align : 'center',
	                dataIndex : 'indexName',
	                sortable : true,
 					editor : searchIndex1}
 					,{
	                header : '指标ID',
	                width : 200,
	                hidden:true,
	                align : 'center',
	                dataIndex : 'indexId',
	                sortable : true},
					{
                    header : '条件',
                    width : 50,
                    align : 'center',
                    dataIndex : 'condition',
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
								]})
 					})},
 				 {
                  header : '值',
                  width : 100,
                  align : 'center',
                   dataIndex : 'threshold',
                  sortable : true,
                  editor : new Ext.form.Field()
             		},{
                 header : '右括号',
                 width : 100,
                 align : 'center',
                 dataIndex : 'rightPartList',
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
							]})
				}),
                 sortable : true
              },{
                 header : '连接符',
                 width : 100,
                 align : 'center',
                 dataIndex : 'connector',
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
										]})
						}),
                 sortable : true
                 }]
 				});			
			
	var partitionRuleStore = new Ext.data.Store({//数据存储
				  	restful:true,
				  	proxy : new Ext.data.HttpProxy({url:basepath+'/partitionruleedit.json'
				  	}),
				  	  reader: new Ext.data.JsonReader({
					  totalProperty : 'json.count',
					  root:'json.data'
				  },  [{name: 'id', mapping: 'ID'}
				      ,{name: 'condition', mapping: 'CONDITION'}
				      ,{name: 'connector', mapping: 'CONNECTOR'}
				      ,{name: 'createDate',mapping:'CUST_MANAGER_TYPE'}
				      ,{name: 'createOrg', mapping: 'CREATE_ORG'}
				      ,{name: 'createUserId', mapping: 'CREATE_USER_ID'}
				      ,{name: 'custBaseId', mapping: 'CUST_BASE_ID'}
				      ,{name: 'indexId',mapping:'INDEX_ID'}
				      ,{name: 'leftPartList',mapping:'LEFT_PART_LIST'}
				      ,{name: 'rightPartList',mapping:'RIGHT_PART_LIST'}
				      ,{name: 'sqlStr', mapping: 'SQL_STR'}
				      ,{name: 'threshold', mapping: 'THRESHOLD'}
				      ,{name: 'indexName', mapping: 'INDEX_NAME'}
				      ])
			 		  });
			 		  
			    partitionRuleStore.on('beforeload', function() {
				this.baseParams = {
					'groupId':oCustInfo.groupId
				  };
			    });
			    partitionRuleStore.load();
			
		var tarDictListPanel1 = new Ext.grid.EditorGridPanel({
				tbar : [{
		            text : '新增',
		            iconCls:'addIconCss',
		            handler:function() {
		            onAdd();
		        }},{
                text : '删除',
                iconCls:'deleteIconCss',
                handler:function() {
                    onDelete();
                },
                scope: this
                }
	  		],
			height : 300,
			store : partitionRuleStore,
			frame : true,
			cm : tarDictColumns,
			stripeRows : true,
			clicksToEdit : 1
			});
		
		var checkResult = function() {
				if(partitionRuleStore.getCount()<1){
					Ext.Msg.alert('系统提示','您没有添加任何规则');
					return;
				}else{
				for(var i=0;i<partitionRuleStore.getCount();i++){
	                    var temp=partitionRuleStore.getAt(i);
	                    	if(""!=temp.data.leftPartList&&null!=temp.data.leftPartList){
	                        }else{
	                        Ext.Msg.alert('系统提示','左括号不能为空');
	                        return false;
	                        }
	                        if(""!=temp.data.indexId&&null!=temp.data.indexId){
//				                         json2.INDEX_ID.push(temp.data.indexId);
	                        }else{
	                        Ext.Msg.alert('系统提示','指标不能为空');
	                        return false;
	                        }if(""!=temp.data.condition&&null!=temp.data.condition){
//				                          json3.CONDITION.push(temp.data.condition);
	                        }else{
	                        Ext.Msg.alert('系统提示','条件不能为空');
	                        return false;
	                        }if(""!=temp.data.threshold&&null!=temp.data.threshold){
//				                           json4.THRESHOLD.push(temp.data.threshold);
	                        }else{
	                        Ext.Msg.alert('系统提示','阈值不能为空');
	                        return false;
	                        }if(""!=temp.data.rightPartList&&null!=temp.data.rightPartList){
//				                          json5.RIGHT_PART_LIST.push(temp.data.rightPartList);
	                        }else{
	                        Ext.Msg.alert('系统提示','右括号不能为空');
	                        return false;
	                        }if(""!=temp.data.connector&&null!=temp.data.connector){
//				                           json6.CONNECTOR.push(temp.data.connector);
	                        }else{
	                        Ext.Msg.alert('系统提示','连接符不能为空');
	                        return false;
	                        }
	                        return true;
			                    
			                }
						}
						};
			
     // 设置指标展示的from
    	var editDictForm = new Ext.form.FormPanel({
    		labelWidth : 150,
    		height : 300,
    		frame : true,
    		autoScroll : true,
    		buttonAlign : "center",
    		items : [
    				tarDictListPanel1
    					],
    					buttons : [
    						{
    						text : '规则校验',
    						handler : function(){
    						if(checkResult()){
    						 Ext.Msg.alert('系统提示','校验通过');
    						};
    						}
    					},
    					{
    						text : '保  存',
    						handler : function() {
							 var json0 = {'ID':[]};
							 var json1 = {'LEFT_PART_LIST':[]};
							 var json2 = {'INDEX_ID':[]};
							 var json3 = {'CONDITION':[]};
							 var json4 = {'THRESHOLD':[]};
							 var json5 = {'RIGHT_PART_LIST':[]};
							 var json6 = {'CONNECTOR':[]};
							 var countNum=0;
							 var json7={'SQL_STR':[]};
							 var ss = checkResult();
							 if(ss){
							for(var i=0;i<partitionRuleStore.getCount();i++){
				                    var temp=partitionRuleStore.getAt(i);
				                    if(temp.data.leftPartList!=''){
				                        json1.LEFT_PART_LIST.push(temp.data.leftPartList);
				                        json2.INDEX_ID.push(temp.data.indexId);
				                        json3.CONDITION.push(temp.data.condition);
				                        json4.THRESHOLD.push(temp.data.threshold);
				                        json5.RIGHT_PART_LIST.push(temp.data.rightPartList);
				                        json6.CONNECTOR.push(temp.data.connector);
				                        json7.SQL_STR.push(temp.data.connector + ' '
											+ temp.data.leftPartList + ' '
											+ temp.data.indexId + ' ' + temp.data.condition
											+ ' ' + temp.data.threshold + ' '
											+ temp.data.rightPartList + ' ');
				                    }
				                }
				                Ext.Msg.wait('正在保存，请稍后......','系统提示');
							Ext.Ajax.request({
				                    url : basepath + '/partitionruleedit!saveData.json',
				                    method : 'POST',
				                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
				                    params:{
				                        'LEFT_PART_LIST':Ext.encode(json1),
				                        'INDEX_ID':Ext.encode(json2),
				                        'CONDITION':Ext.encode(json3),
				                        'THRESHOLD':Ext.encode(json4),
				                        'RIGHT_PART_LIST':Ext.encode(json5),
				                        'CONNECTOR':Ext.encode(json6),
				                        'groupId':oCustInfo.groupId,
				                        'SQL_STR':Ext.encode(json7)
				                    },
				                    success : function() {
				                        Ext.Msg.alert('提示', '操作成功');
				                    },
				                    failure : function(response) {
				                        Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
				                    }
				                });
    						}else{
    						 Ext.Msg.alert('系统提示', '校验未通过，请检查');
    						}
							}

    					},{
    						text : '重    置',
    						handler : function() {
    							partitionRuleStore.load();
    						}
    					},{
    						text : '试运算',
    						handler : function(){
    						if(!checkResult()){
    						 Ext.Msg.alert('系统提示','未通过校验');
    						}else{
    						viewWindow.show();
    						};
    						}
    					} ]

    	});
        
        tarDictListPanel1.on('cellclick',function(grid,row,col){//获取编辑的行数，从0开始，
				rowNo1=row;	
			});
 	   //客户群维护窗口展示的from
    var fiexibleEditPanel = new Ext.Panel({
        renderTo:'group_viewport_center',
        autoScroll : true,
        buttonAlign : "center",
        items : [{
    			 layout : 'form',
	        	 border : false,
	        	 items : [/*{
		             region : 'north',
		             height : 80,
		             layout : 'fit',
		             items : [ fiexibleSearchPanel ]
		         		},*/{
		             region : 'center',
		             layout : 'fit',
		             height : document.body.scrollHeight-30,
		             items : [ editDictForm]
		             	}]
					}]
    });
            var onDelete = function(){
            var index = tarDictListPanel1.getSelectionModel().getSelectedCell();
            if (!index) {
            	alert("请选择一条记录");
                return false;
            }
            var rec = partitionRuleStore.getAt(index[0]);
            partitionRuleStore.remove(rec);
        	};
    
            var onAdd = function(){
            var u = new partitionRuleStore.recordType({
            	"id" :"",             
				"condition" :"",
				"connector" :"",
				"createDate":"",
				"createOrg" :"",
				"createUserId" :"",
				"custBaseId" :"",
				"indexId":"",     
				"leftPartList":"",
				"rightPartList":"",
				"sqlStr" :"", 
				"threshold" :""
            });
            tarDictListPanel1.stopEditing();
            partitionRuleStore.insert(0, u);
            tarDictListPanel1.startEditing(0, 0);
        };

	});