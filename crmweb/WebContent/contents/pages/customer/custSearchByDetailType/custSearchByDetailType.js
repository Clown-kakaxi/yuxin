	//查询条件个数
	var title_count = 0;
	var DBTABLE_ID = '';
	//列模型数组
	var colMArray = new Array();
	var pramas ='';//用字符串来表示查询条件，以:分割
	//所选择的客户id
	var idStr = '';
	
Ext.onReady(function() {
	
	//获取查询条件
	function getParm(){
		var title = null;
		var type = null;
		var prama = '';//用来拼接复选下拉框组取值
		var fieldStart = '';//输入框取值
		var fieldEnd = '';
		var arr ;//复选下拉框数组
		pramas ='';//先清空查询条件
		for ( var j = 0; j < title_count; j++) {
			title = title_store.getAt(j);
			type = title.json.type;
			if(type=='dic'){
				prama = searchForm.form.findField('panel'+j).getValue();
				if(prama=='')
					pramas+='empty:';	//当未使用该查询条件时以empty占位
				else
					pramas+=prama.replace(/,/g,'|')+':';//当使用该查询条件时该复选下拉框结果为：a|b形式
				prama='';//重新清空prama
			}else if(type=='num'){
				fieldStart = searchForm.form.findField('tf'+j+'start').getValue();
				fieldEnd = searchForm.form.findField('tf'+j+'end').getValue();
				//当使用该查询条件时该输入框组结果为：a|b形式
				if(fieldStart=='')
					pramas+='empty|';	//当未使用该查询条件时以empty占位
				else
					pramas+=fieldStart+'|';
				if(fieldEnd=='')
					pramas+='empty:';	//当未使用该查询条件时以empty占位
				else
					pramas+=fieldEnd+':';
			}
		}
		
	}
	
	var typeStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 45273, '零售客户细分' ], [ 48531, '小企业客户细分' ],[48273,'对公客户细分'] ]
	});
	
	var type_combo = new Ext.form.ComboBox({
		store : typeStore ,
		xtype : 'combo',
		resizable : true,
		name : 'DBTABLE_ID',
		hiddenName : 'DBTABLE_ID',
		fieldLabel : '数据集',
		valueField : 'key',
		displayField : 'value',
		mode : 'local',
		triggerAction : 'all',
		emptyText : '请选择',
		selectOnFocus : true,
		anchor : '90%'
	
});
	
	//细分选择panel
	var  typePanel = new Ext.form.FormPanel( {
		labelWidth : 100,
		labelAlign : 'right',
		frame : true,
		region : 'north',
		autoScroll : true,
		layout : 'column',
		items : [{
			columnWidth : .25,
			layout : 'form',
			items : [type_combo]
		}]
	});
	
	
	// 查询条件
	var title_record = Ext.data.Record.create( [ {
		name : 'colNameE',
		mapping : 'colNameE'
	}, {
		name : 'colNameC',
		mapping : 'colNameC'
	}, {
		name : 'type',
		mapping : 'type'
	} ,{
		name : 'fCodeL',
		mapping : 'F_CODE'
	} ]);
	
	//查询条件的store
	var title_store = new Ext.data.Store( {
		restful : true,
		proxy : new Ext.data.HttpProxy({url : basepath + '/getSearchCol!loadTitleRs.json',
	        	success : function(response) {
//					Ext.Msg.alert('提示', response.responseText);
				}
	        }),
		reader : new Ext.data.JsonReader( {
			successProperty : 'success',
			messageProperty : 'message',
			root : 'data',
			totalProperty : 'count'
		}, title_record)
	});
	
	
	
	var rd_set = new Ext.form.FieldSet( {
		xtype : 'fieldset',
		title : '细分维度',
		collapsible : true,
		autoScroll : true,
		labelWidth : 80, // 标签宽度
		items : []
	});
	
	//查询条件
	var searchForm = new Ext.FormPanel( {
		id : 'searchForm',
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		frame : true,
		autoScroll : true,
		items : [ rd_set ],
		 buttons : [{
				text : '查询',
				handler : function() {
					getParm();
					//请求获取需要的字段，拼接新的cm
					Ext.Ajax.request({
						url : basepath + '/getSearchCol!getResult.json',
						params:{'pramas':pramas,
		    				'DBTABLE_ID':DBTABLE_ID},
		    				method : 'GET',
						waitMsg : '正在查询数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success :checkResult,
						failure :checkResult
					});
					
					
					function checkResult(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if (resultArray == 200 ||resultArray == 201) {
							var colM =  Ext.util.JSON.decode(response.responseText).cols;
						    var colMArr = colM.split("|");
						    var field =  Ext.util.JSON.decode(response.responseText).fileds;
						    var fieldArray = field.split("|");
						    var type =  Ext.util.JSON.decode(response.responseText).types;
						    var typeArray = type.split("|");
						    var colLength = colMArr.length;
						    //因为会多次操作所以需要先清空cm的数组
						    colMArray.length=0;
						    colMArray.push(new Ext.grid.RowNumberer());
						    colMArray.push(sm);
						    for(var i=0; i<colLength; i++) {
						    	if(typeArray[i]=='t')
						    		colMArray.push({header:colMArr[i],width: 80,dataIndex:fieldArray[i],menuDisabled: true,align : 'center'});
						    	else if(typeArray[i]=='n')
						    		colMArray.push({header:colMArr[i],width: 80,dataIndex:fieldArray[i], align : 'right',menuDisabled: true,renderer : money('0,000.00')});
						    	
						    }
						    
						    //重新绑定store和cm
							resultGrid.reconfigure( new Ext.data.Store({//动态数据存储
								restful:true,   
								autoLoad :true,
								proxy : new Ext.data.HttpProxy({
									url :basepath+'/getSearchCol.json'
								}),
								reader : new Ext.data.JsonReader({
									root : 'json.data',
									totalProperty : 'json.count'
								}, fieldArray)
							}),new Ext.grid.ColumnModel(colMArray));
							var store = resultGrid.getStore();
							bbar.bind(store);
							resultGrid.render();
							store.on('beforeload', function() {
					    		this.baseParams = {
					    				'pramas':pramas,
					    				'DBTABLE_ID':DBTABLE_ID,
					    				start : 0,
				                        limit : bbar.pageSize
					    				
					    		};
					    	});
							// 改变每页显示条数reload数据
							pagesize_combo.on("select", function(comboBox) {
								bbar.pageSize = parseInt(pagesize_combo.getValue()), store
										.reload({
											params : {
												start : 0,
												limit : parseInt(pagesize_combo.getValue())
											}
										});
							});
							
						} else {
							Ext.Msg.alert('提示', '操作失败' );
						}
					};
					
					
					
					
				}},{
					text : '重置',
				     handler : function() {
				    	 searchForm.form.reset();
							}
				}]
	});
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	var sm = new Ext.grid.CheckboxSelectionModel();
	
	var record = Ext.data.Record.create([ {
		name : 'CUST_ID',
		mapping : 'CUST_ID'
	}, 
	 {
		name : 'CUST_ZH_NAME',
		mapping : 'CUST_ZH_NAME'
	}, 
	 {
		name : 'SEX',
		mapping : 'SEX'
	}]);

	//定义列模型(作为点击查询之前的默认列模型)

	var cm = new Ext.grid.ColumnModel([ rownum,sm,{
		header : '客户号',
		width : 80,
		align : 'center',
		dataIndex : 'CUST_ID',
		sortable : true
	},{
		header : '客户名称',
		width : 80,
		align : 'center',
		dataIndex : 'CUST_ZH_NAME',
		sortable : true
	}]);

	/**
	 * 数据存储(作为点击查询之前的默认数据存储)
	 */

	var editStore = new Ext.data.Store({
		restful : true,
		 proxy : new Ext.data.HttpProxy({url:basepath + '/getSearchCol.json'
		  }),
		reader : new Ext.data.JsonReader({
			successProperty : 'success',
			idProperty : 'ID',
			totalProperty : 'json.count',
			root:'json.data'
		}, record)
	});
	editStore.on('beforeload', function() {
		this.baseParams = {
				'DBTABLE_ID':DBTABLE_ID,
				start : 0,
                limit : bbar.pageSize
				
		};
	});

	//每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 20, '20条/页' ], [ 50, '50条/页' ],
					[ 100, '100条/页' ], [ 200, '200条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		editable : false,
		width : 85
	});
	

	
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
		pageSize : parseInt(pagesize_combo.getValue()),
		store : editStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	});


	var resultGrid = new Ext.grid.GridPanel({
		title : '结果列表',
		name:'grid',
		frame : true,
		autoScroll : true,
		store : editStore,
		stripeRows : true, // 斑马线
		sm : sm,
		cm : cm, // 列模型
		tbar : [
		        //继承CommonAction的(“automation服务器不能创建对象”问题解决：IE的安全设置“不允许运行未标记为安全的activeX控件”启用即可)
//		  new Com.yucheng.bob.ExcelButton({
//				id:'exp',
//		        iconCls:'exportIconCss',
//		        expGrid:'resultGrid'
//				}) 
		  		//继承BaseQueryAction的
		    new Com.yucheng.bob.ExpButton({
		    formPanel : 'searchForm',//虽然本按钮的参数是在click方法是添加的，但是因为导出增加了查询条件控制，无此参数将会直接返回
			id:'exp',
            iconCls:'exportIconCss',
            url : basepath+'/smartcustomerquery.json'
		    }),'-',{
			text:'创建客户群',
			iconCls:'addIconCss',
			handler : function() {
				var store = resultGrid.getStore();
				 var selectLength = resultGrid.getSelectionModel().getSelections().length;
				 var selectRe;
				 var tempId;
				if(selectLength < 1){
					Ext.Msg.alert('提示','请选择需要操作的记录!');
				} else {
					for(var i = 0; i<selectLength;i++)
					{
						selectRe = resultGrid.getSelectionModel().getSelections()[i];
						tempId = selectRe.data.CUST_ID;
						idStr += tempId;
						if( i != selectLength-1)
							idStr += ',';
					}
					choseWin.show();
					choseWayForm.form.findField('custGroup').setVisible(false);
					choseWayForm.getForm().reset();
			}
		}
		}],
		bbar : bbar,// 分页工具栏
		viewConfig : {},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	Ext.getCmp('exp').addListener('click',function(){
		getParm();
		Ext.getCmp('exp').url=basepath+'/smartcustomerquery.json?DBTABLE_ID='+DBTABLE_ID+'&pramas='+pramas;
	});
	//下方card布局
	var cardPanel=new Ext.Panel({
		layout:"card",
		activeItem: 0,
		autoScroll:true,
		layoutConfig: {
		animate: true 
		},
		items:[{title:"细分查询",html:"<font color='red'>请先选择查询数据集种类<font>"},
		 {title:"具体查询",layout:'border',items:[
												{
													region : 'west',
													width : 400,
													layout : 'fit',
													items : [ searchForm ]
												}
												,
												{
													region : 'center',
													layout : 'fit',
													items : [ resultGrid ]
												}
              		                          ] }
]
});
	//动态生成查询panel
	type_combo.on("select", function(comboBox) {    
		DBTABLE_ID = typePanel.form.findField('DBTABLE_ID').getValue();
		
		 if(DBTABLE_ID=='45273')
			 groupMemberType='1';//成员类型   对私
		 else
			 groupMemberType = '2';//成员类型   对公
		
		//参数：数据集类型
		title_store.on('beforeload', function() {
			this.baseParams = {
					'DBTABLE_ID':DBTABLE_ID
			};
		});
		
		//将editStore清空，恢复默认绑定的store和cm
		editStore.removeAll();
		resultGrid.reconfigure( editStore,cm);
		bbar.bind(editStore);
		resultGrid.render();
		
		//load之后拼接rd_set的部分
		title_store.load({callback:function(){
			//先清空
			for ( var i = 0; i < title_count; i++) 
			rd_set.remove(Ext.getCmp('panel' + i));
			
			//重新拼接
			var title = null;
			var title_rs = null;
			var rs = null;
			var type = null;
			var store1 = null;
			title_count = title_store.getCount();
			for ( var i = 0; i < title_count; i++) {
				title = title_store.getAt(i);
				type = title.json.type;
				if(type=='dic'){
					title_rs = [];
					for ( var b = 0; b < title.json.fCodeL.length; b++) {
						rs = title.json.fCodeL[b];
						title_rs.push([rs.fCode,rs.fValue]);
					}
					var store1 = new Ext.data.ArrayStore({
						fields : [ 'key', 'value'  ],
						data : title_rs
					});
					 new Ext.ux.form.LovCombo({
						 	id : 'panel'+i,
			    			fieldLabel : title.json.colNameC,
			    			labelStyle: 'text-align:right;',
			    			triggerAction : 'all',
			    			store : store1,
			    			displayField : 'value',
			    			valueField : 'key',
			    			mode : 'local',
			    			emptyText:'请选择 ',
			    			resizable : true,
						    hideOnSelect:false,
						    triggerAction:'all',
						    allowBlank:true,
						    editable:true,
						    anchor : '90%'
				         });
							        
				}else if(type=='num'){
					new Ext.Panel({
						id : 'panel' + i,
						layout : 'column',
						items : [{
							columnWidth : .5,
							layout : 'form',
							items : [new Ext.form.NumberField({
								fieldLabel : title.json.colNameC+'从',
								name : 'tf'+i+'start',
								labelStyle: 'text-align:right;',
								labelWidth : 120,
								anchor : '95%'
							})]
						},{
							columnWidth : .5,
							layout : 'form',
							items : [new Ext.form.NumberField({
								fieldLabel : title.json.colNameC+'到',
								labelWidth : 120,
								labelStyle: 'text-align:right;',
								name : 'tf'+i+'end',
								anchor : '90%'
							})]
						}]
					});
				}
				
				rd_set.add(Ext.getCmp('panel' + i));
				rd_set.doLayout();
			}
	}});
		cardPanel.getLayout().setActiveItem(1);
	});
	
	//主页面
	var view = new Ext.Viewport( {
		layout : "fit",
		frame : true,
		items : [ {
			layout : 'border',
			items : [
					{
						region : 'north',
						id : 'north-panel',
						title : "查询数据集",
						height : 80,
						layout : 'fit',
						items : [ typePanel ]
					}
					,{
						region : 'center',
						id : 'center-panel',
						layout : 'fit',
						items : [ cardPanel ]
					}
			]
		} ]
	});
}); 
