Ext.onReady(function() {
	var h= document.body.clientHeight-170;
	var qForm = new Ext.form.FormPanel({
		labelWidth : 90, // 标签宽度
		frame : true, //是否渲染表单面板背景色
		 title: "营销管理->营销统计", 
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		height : 120,
		region:'north',
		items : [{
					layout : 'column',
					border : false,
					items : [{
								columnWidth : .33,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '起始日期',
											name : 'e1',
											xtype : 'datefield', // 设置为数字输入框类型
											labelStyle: 'text-align:right;',
											anchor : '80%'
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
									xtype:'datefield',
											fieldLabel : '截止日期', // 标签
											id : 'e4',
											name : 'e4', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											labelStyle: 'text-align:right;',
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										}]
							}, {
								columnWidth : .33,
								layout : 'form',
								labelWidth : 80, // 标签宽度
								defaultType : 'textfield',
								border : false,
								items : [{
											fieldLabel : '机构号', // 标签
											id : 'e7',
											name : 'e7', // name:后台根据此name属性取值
											allowBlank : true, // 是否允许为空
											labelStyle: 'text-align:right;',
											//maxLength : 6, // 可输入的最大文本长度,不区分中英文字符
											anchor : '80%' // 宽度百分比
										}]
							}
//							, {
//								columnWidth : .25,
//								layout : 'form',
//								labelWidth : 80, // 标签宽度
//								defaultType : 'textfield',
//								border : false,
//								items : []
//							}
							]
				}],
		buttons : [{
					text : '查询'
					/*handler : function() {
						queryBalanceInfo(qForm.getForm());
					}*/
				}, {
					text : '重置'
					/*handler : function() {
						qForm.getForm().reset();
					}*/
				}]
	});
	 /*******************************************************************/
	var fields = [],
	    columns = [],
	    data = [],
	    continentGroupRow = [],
	    
	continentGroupRow = [
	{header: '', colspan: 2, align: 'center'},
	{header: '营销计划', colspan: 3, align: 'center'},
	{header: '营销活动', colspan: 3, align: 'center'},
	{header: '商机', colspan: 8, align: 'center'}
	];
	    var group = new Ext.ux.grid.ColumnHeaderGroup({
	        rows: [continentGroupRow]
	    });
	    
	    
	      fields =
	      [
	          {name: 'a'},
	          {name: 'b'},
	          {name: 'c'},
	          {name: 'd'},
	          {name: 'e'},
	          {name: 'f'},
	          {name: 'g'},
	          {name: 'h'},
	          {name: 'i'},
	          {name: 'j'},
	          {name: 'k'},
	          {name: 'l'},
	          {name: 'm'},
	          {name: 'n'},
	          {name: 'o'},
	          {name: 'p'}
	      ];
	      
	      columns =
	      [
	          {dataIndex: 'a', header: '机构号',sortable:true},
	          {dataIndex: 'b', header: '机构名称',sortable:true},
	          {dataIndex: 'c', header: '数量',sortable:true},
	          {dataIndex: 'd', header: '完成数量',sortable:true},
	          {dataIndex: 'e', header: '完成比率',sortable:true},
	          {dataIndex: 'f', header: '数量',sortable:true},
	          {dataIndex: 'g', header: '完成数量',sortable:true},
	          {dataIndex: 'h', header: '完成比率',sortable:true},
	          {dataIndex: 'i', header: '资产类数量',sortable:true},
	          {dataIndex: 'j', header: '资产类完成量',sortable:true},
	          {dataIndex: 'k', header: '负债类数量',sortable:true},
	          {dataIndex: 'l', header: '负债类完成量',sortable:true},
	          {dataIndex: 'm', header: '中间业务数量',sortable:true},
	          {dataIndex: 'n', header: '中间业务类完成量',sortable:true},
	          {dataIndex: 'o', header: '理财存款类数量',sortable:true},
	          {dataIndex: 'p', header: '理财存款类完成量',sortable:true}
	      ];
	      data = [
	              ['101','某某银行1',30,20,0.66,40,30,0.75,35,30,40,33,24,20,59,40],
	              ['102','某某银行2',60,40,0.66,80,60,0.75,34,65,12,3,55,12,559,324],
	              ['103','某某银行3',90,60,0.66,60,45,0.75,23,55,56,54,12,32,86,54],
	              ['104','某某银行4',15,10,0.66,20,15,0.75,75,23,23,12,73,54,65,65],
	              ['105','某某银行5',30,20,0.66,400,300,0.75,66,43,53,35,35,20,12,23],
	              ['106','某某银行6',30,20,0.66,200,150,0.75,87,23,12,8,23,20,345,254]
	              ];
	      var store = new Ext.data.ArrayStore({
	            fields: fields,
	            data: data
	        });
	      var pagesize_combo = new Ext.form.ComboBox({
	  		name : 'pagesize',
	  		triggerAction : 'all',
	  		mode : 'local',
	  		store : new Ext.data.ArrayStore({
	  			fields : [ 'value', 'text' ],
	  			data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
	  					[ 500, '500条/页' ], [ 1000, '1000条/页' ] ]
	  		}),
	  		valueField : 'value',
	  		displayField : 'text',
	  		value : '100',
	  		editable : false,
	  		width : 85
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
	  	// 分页工具栏
	  	var bbar = new Ext.PagingToolbar({
	  		pageSize : parseInt(pagesize_combo.getValue()),
	  		store : store,
	  		displayInfo : true,
	  		displayMsg : '显示{0}条到{1}条,共{2}条',
	  		emptyMsg : "没有符合条件的记录",
	  		items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
	  	});
	  	
	  	
	    var grid = new Ext.grid.GridPanel({
//	        height: h,
	        store: store,
	        bbar : bbar,
	        stripeRows:true,
	        columns: columns,
	        region:'center',
	        autoScroll : true,
//	        viewConfig: {
//	            forceFit: true
//	        },
	        plugins: group
	    });
	 /*******************************************************************/
	// 布局模型
	var viewport = new Ext.Viewport({
		layout : 'fit',
		frame : true,
		items : [{
				layout : 'border',
				items: [qForm,grid] 
		}]
			});
}); 