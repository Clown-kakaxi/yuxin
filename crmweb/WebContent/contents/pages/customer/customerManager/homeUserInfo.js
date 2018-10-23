Ext.onReady(function() {
	 var boxstore = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '0000'], ['丈夫', '02'],['妻子','03'],['长子','04'],['长女','05'],['次子','06'],['此女','07'],['媳妇','08'],['女婿','09'],['孙子','10'],['孙女','11'],['父亲','12'],['母亲','13'],['其他','14']]
				});
	 var boxstore0 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '0000'], ['户主', '01'], ['丈夫', '02'],['妻子','03'],['长子','04'],['长女','05'],['次子','06'],['此女','07'],['媳妇','08'],['女婿','09'],['孙子','10'],['孙女','11'],['父亲','12'],['母亲','13'],['其他','14']]
				});
//	 var  boxstore = new Ext.data.Store( {
//			restful : true,
//			sortInfo : {
//				field : 'key',
//				direction : 'ASC'
//			},
//			autoLoad : true,
//			proxy : new Ext.data.HttpProxy( {
//				url : basepath + '/lookup.json?name=CUS0100038'
//			}),
//			reader : new Ext.data.JsonReader( {
//				root : 'JSON',
//				totalProperty : 'list'
//			}, [ 'name', 'code' ])
//		});
	 
	 var boxstore1 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '0000'],['博士及以上', '01'], ['研究生', '02'],['本科生','03'],['专科生','04'],['大专','05'],['中专','06'],['技术学校','07'],['高中','08'],['初中','09'],['小学','10'],['文盲或半文盲','11'],['未知','12']]
				});
	 
	 var boxstore2 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '0000'],['未婚', '01'], ['已婚', '02'],['离异','03'],['其他','04']]
				});
	 
	 var boxstore3 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '0000'],['良好', '01'], ['其他', '02']]
				});
	 
	 var boxstore4 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '0000'],['工人', '01'], ['农民', '02'],['下岗','03'],['现役军人','04'],['退役军人','05'],['医生','06'],['教师','07'],['公务员','08'],['事业单位','09'],['金融机构','10'],['学生','11'],['外企','12'],['国企','13'],['退离休人员','14'],['个体户','15'],['无业人员','16'],['私营企业主','17'],['其他','18']]
				});
	 
	 var boxstore5 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '00'],['农保', '01'], ['城保', '02'],['无投保','03'],['其他','04']]
				});
	 
	 var boxstore6 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '00'],['好', '01'], ['一般', '02'],['差','03']]
				});
	 
	 var boxstore7 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '00'],['赌博', '01'], ['前科', '02'],['高利贷','03'],['欠债不还','04'],['其他','05']]
				});
	 
	 var boxstore8 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '00'],['A', '01'], ['B', '02'],['C','03'],['D','04'],['E','05']]
				});
	 
	 var boxstore9 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['请选择合适的值', '00'],['否', '01'], ['是', '02']]
				});

	 var boxstore10 = new Ext.data.SimpleStore({
			fields : ['name', 'code'],
			data : [['全部', '00'],['10(含)万以下', '01'], ['10万-20万（含）', '02'],['20万-30万（含）','03'],['30万-50万（含）','04'],['50万以上','05']]
				});
	 
	 
	 var qForm = new Ext.form.FormPanel({
			//title : '<span style="font-weight:normal">查询条件<span>',
			//border : true,
			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height : 100,
			items : [{
						layout : 'column',
						border : false,
						items : [{
									columnWidth : .15,
									layout : 'column',
									labelWidth : 80, // 标签宽度
									border : false,
									items : [{
										layout:'form',
										items:[{
											xtype:'textfield',
											fieldLabel:'收入区间',
											id:'yeIncomeSId',
											name:'yeIncomeS',
											anchor:'90%'
										},{
											xtype:'textfield',
											fieldLabel:'授信额度',
											id:'awardYnLineSId',
											name:'awardYnLineS',
											anchor:'90%'
										}]
											
									}]
								},{
									columnWidth:.15,
									layout:'form',
									labelWidth:50,
									border:false,
									items:[
//									{
//										
//										hideLabel:true,
//										fieldLabel:' ',
//										height:23,
//										labelSeparator:'',
//										anchor:'80%'
//									},
                                    {
                                    	xtype:'hidden',
										fieldLabel:'村委会',
										anchor:'90%'
									},
									{
										xtype:'textfield',
										fieldLabel:'-',
										labelStyle:'text-align:center',
										labelSeparator:'',
										id:'yeIncomeEId',
										name:'yeIncomeE',
										anchor:'90%'
									},{
										xtype:'textfield',
										fieldLabel:'-',
										labelStyle:'text-align:center',
										labelSeparator:'',
										id:'awardYnLineEId',
										name:'awardYnLineE',
										anchor:'90%'
									} ]
								},{
									columnWidth:.2,
									layout:'form',
									labelWidth:60,
									border:false,
									items:[new Ext.form.ComboBox({
										id : 'profeKindId',
										hiddenName : 'profeKind',
										fieldLabel : '客户职业',
										labelStyle: 'text-align:left;',
										triggerAction : 'all',
										store : boxstore4,
										displayField : 'name',
										valueField : 'code',
										mode : 'local',
										listWidth : 170, // 下拉列表的宽度,默认为下拉选择框的宽度
										forceSelection : true,
										typeAhead : true,
										resizable : true,
										anchor : '90%'
									}), 
									new Ext.form.ComboBox({
										id : 'nowDepBalId',
										hiddenName : 'nowDepBal',
										fieldLabel : '存款分段',
										labelStyle: 'text-align:center;',
										triggerAction : 'all',
										store : boxstore10,
										displayField : 'name',
										valueField : 'code',
										mode : 'local',
										listWidth : 170, // 下拉列表的宽度,默认为下拉选择框的宽度
										forceSelection : true,
										typeAhead : true,
										resizable : true,
										anchor : '90%'
									})]
								},{
									columnWidth:.2,
									layout:'form',
									labelWidth:60,
									border:false,
									items:[{
                                    	xtype:'textfield',
										fieldLabel:'自然村',
										id:'villaNameId',
										name:'villaName',
										anchor:'90%'
									},{
										xtype:'datefield',
										fieldLabel:'年月',
										format:'Y-m',
										id:'odsStDateId',
										name:'odsStDate',
										anchor:'90%'
									}]
								},{
									columnWidth : .15,
									layout : 'column',
									labelWidth : 80, // 标签宽度
									border : false,
									items : [{
										layout:'form',
										items:[{
											xtype:'textfield',
											fieldLabel:'客户年龄段',
											id:'ageSId',
											name:'ageS',
											anchor:'90%'
										}]
											
									}]
								},{
									columnWidth:.15,
									layout:'form',
									labelWidth:50,
									border:false,
									items:[{
										xtype:'textfield',
										fieldLabel:'-',
										labelStyle:'text-align:center',
										labelSeparator:'',
										id:'ageEId',
										name:'ageE',
										anchor:'90%'
									}
									       ]
								}]
					}],
			buttons : [{
						text : '查询',
						handler : function() {
							store.load({      
								  params : {
                                  start : 0,
                                  limit : bbar.pageSize}});
						}
					},{
						text : '重置',
							handler : function() {
								qForm.getForm().reset();   
							}
					}]
		});
	var sm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

	// 定义列模型
	var cm = new Ext.grid.ColumnModel([rownum,sm, 
	           {
				header : '管理机构', // 列标题
				dataIndex : 'manageBran', // 数据索引:和Store模型对应
				sortable : true,	// 是否可排序
				align :'center'
             },{
				header : '村编号', // 列标题
				dataIndex : 'villaNo', // 数据索引:和Store模型对应
				sortable : true,	// 是否可排序
				align :'center'
             },{
				header : '村名', // 列标题
				dataIndex : 'villaName', // 数据索引:和Store模型对应
				sortable : true,	// 是否可排序
				align :'center'
             },{
				header : '户号代码', // 列标题
				dataIndex : 'houseCode', // 数据索引:和Store模型对应
				sortable : true,	// 是否可排序
				align :'center'
             },{
				header : '户主名', // 列标题
				dataIndex : 'houseMaster', // 数据索引:和Store模型对应
				sortable : true,	// 是否可排序
				align :'center'
             },{
				header : '户主职业', // 列标题
				dataIndex : 'profeKind', // 数据索引:和Store模型对应
				sortable : true,	// 是否可排序
				align :'center'
             },{
				header : '片区', // 列标题
				dataIndex : 'area', // 数据索引:和Store模型对应
				sortable : true,	// 是否可排序
				align :'center'
             },{
				header : '家庭地址', // 列标题
				dataIndex : 'liveAddr', // 数据索引:和Store模型对应
				sortable : true,	// 是否可排序
				align :'center'
             },{
				header : '姓名', // 列标题
				dataIndex : 'name', // 数据索引:和Store模型对应
				sortable : true,	// 是否可排序
				align :'center'
             },{
				header : '身份证号', // 列标题
				dataIndex : 'identiCardNo', // 数据索引:和Store模型对应
				sortable : true,	// 是否可排序
				align :'center'
             },{
				header : '固定电话', // 列标题
				dataIndex : 'phoneNo', // 数据索引:和Store模型对应
				sortable : true,	// 是否可排序
				align :'center'
             },{
				header : '手机号码', // 列标题
				dataIndex : 'mobile', // 数据索引:和Store模型对应
				sortable : true,	// 是否可排序
				align :'center'
             },{
				header : '存款余额', // 列标题
				dataIndex : 'nowDepBal', // 数据索引:和Store模型对应
				sortable : true,	// 是否可排序
				align :'center'
             }
			]);

	/**
	 * 数据存储
	 */
	var store = new Ext.data.Store({
		restful:true,
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({url:basepath+'/homeUserInfoAction.json'
       /* 	success : function(response) {
				var resultArray = Ext.util.JSON.decode(response.responseText);
				Ext.Msg.alert('提示', response.responseText);
			}*/
        }),
        reader: new Ext.data.JsonReader({
        totalProperty : 'json.count',
        root:'json.data'
        }, [{name: 'manageBran', mapping: 'MANAGE_BRAN'},{name: 'villaNo', mapping: 'VILLA_NO'},{name: 'villaName', mapping: 'VILLA_NAME'},
            {name: 'houseCode', mapping: 'HOUSE_CODE'},{name: 'houseMaster', mapping: 'HOUSE_MASTER'},{name: 'profeKind', mapping: 'PROFE_KIND'},
            {name: 'area', mapping: 'AREA'},{name: 'liveAddr', mapping: 'LIVE_ADDR'},{name: 'name', mapping: 'NAME'},
            {name: 'identiCardNo', mapping: 'IDENTI_CARD_NO'},{name: 'phoneNo', mapping: 'PHONE_NO'},{name: 'mobile', mapping: 'MOBILE'},
            {name: 'nowDepBal', mapping: 'SUM_NOW_DEP_BAL'}])
	});
	store.on('beforeload', function() {
    	var conditionStr =  qForm.getForm().getValues(false);
        this.baseParams = {
                "condition":Ext.encode(conditionStr)
        };
     });

	// 每页显示条数下拉选择框
	var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
							fields : ['value', 'text'],
							data : [[10, '10条/页'], [20, '20条/页'], [50, '50条/页'], [100, '100条/页'], [250, '250条/页'], [500, '500条/页']]
						}),
				valueField : 'value',
				displayField : 'text',
				value : '20',
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
										limit : bbar.pageSize
									}
								});
					});
	// 分页工具栏
	var bbar = new Ext.PagingToolbar({
						pageSize : number,
						store : store,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录",
						items : ['-', '&nbsp;&nbsp;', pagesize_combo]
					});

	//表格工具栏
	var tbar = new Ext.Toolbar({
		items : [{
			text : '新增',
			iconCls:'addIconCss ',
			handler : function() {
				window.location="homeUserAddInfo.jsp";
			}
		},{
			text : '修改',
			iconCls:'editIconCss',
			handler : function() {
				updategrid(grid);
			}
		},{
			text : '删除',
			iconCls:'deleteIconCss',
			handler : function() {
				deletegrid(grid);
			}
		},{
			text : '家庭成员维护',
			iconCls:'dailyDetailIconCss',
			handler : function() {
				 modifygrid(grid);
			}
		}
//		,new Com.yucheng.bob.ExpButton({
//            formPanel : 'qForm',
//            iconCls:'exportIconCss',
//            url : basepath + '/homeUserInfoAction.json'
//        }),{
//			text:'发送短信',
//			iconCls:'closeIconCss',
//			handler:function(){
//				
//			}
//		}
		]
	});
	var grid = new Ext.grid.GridPanel({
			height :document.documentElement.clientHeight-120,
			title :'家庭信息列表',
			frame : true,
			autoScroll : true,
			region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
			store : store, // 数据存储
			stripeRows : true, // 斑马线
			cm : cm, // 列模型
			sm : sm, // 复选框
			tbar:tbar,
			bbar : bbar,// 分页工具栏
			loadMask : {
				msg : '正在加载表格数据,请稍等...'
			}
			});

	// 布局模型 
	var viewport = new Ext.Viewport({
				layout : 'border',
				items: [{   
					region: 'north',
				    id: 'north-panel',
				    title: "小贷客户管理->家庭信息维护",  
				    height: 120,
				    hidden:false,
				    margins: '0 0 0 0',
				    //layout: 'fit',
					items:[qForm]
			     },{  
			    	region:'center',
				    id: 'center-panel',
//				    height:document.getElementsByTagName("body")[0].clientHeight-150,
					margins: '0 0 0 0',
				    items : [grid]
			    }]
			});
	//家庭维护首页中的修改
	var updategrid = function(grid) {
        var _record = grid.getSelectionModel().getSelected();
	 	var checkedNodes = grid.getSelectionModel().selections.items;
        if (!_record||checkedNodes.length>1) {
        	Ext.MessageBox.alert('修改操作', '请选择要操作的一列！');
        } else {
          var record = grid.getSelectionModel().getSelected();
          var custId = record.json.CUST_ID;
          window.location="homeUserUpdateInfo.jsp?custId="+custId;
        }
    };
    
    //家庭成员维护首页中的删除
    var deletegrid = function(grid) {
        var _record = grid.getSelectionModel().getSelected();
        if (!_record) {
        	Ext.MessageBox.alert('删除操作', '请选择要操作的一列！');
        } else {
    	 	var checkedNodes = grid.getSelectionModel().selections.items;
    		var json={'cust_id':[]};
    		for(var i=0;i<checkedNodes.length;i++)
    			{
    				json.cust_id.push(checkedNodes[i].json.CUST_ID);
    			}
    		Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return;
				} 
    			Ext.Ajax.request({
    						url:basepath+'/homeUserDel.json',
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
    						}});
        	
    		});
        }
    };
    
    //家庭成员维护的listpanel
    var listpanel = new Mis.Ext.CrudPanel({ 
		id : "listPanel",
		title : "家庭成员维护",
		stUrl : basepath + '/homeMemberQuery.json',
		addUrl: basepath + '/homeMemberInfo.json',
		updateUrl: basepath + '/homeMemberInfo.json',
//		deUrl: basepath + '/homeMemberInfo!batchDestroy.json',
		primary : "custId",
		checkbox : true,
		//定义查询条件Form的高度
//		seFormHeight : 10,
		//定义增删详情页面弹出窗口高度
		winHeight : 325,
		//宽度
		winWidth : 500,
		//设置分页每页显示条数，若不设置则不出现分页栏
		pagesize : 20,
		dbclick : false,
		
//		selectItems :new Ext.form.FieldSet({
//			items:[util.layout._tr([util.form._td({id:'houseId',name : 'houseCode',mapping:'HOUSE_CODE',xtype : 'textfield',fieldLabel : '户号代码'})])]
//		}),
		gclms : [ //{name : 'villaNo',mapping:'VILLA_NO',header : '村编号',align :'center'},
		          //{name : 'villaName',mapping:'VILLA_NAME',header : '村名',align :'center'},
		          {name : 'custId',mapping:'CUST_ID',header : '客户编号',align :'center'}, 
		          {name : 'name',mapping:'NAME',header : '姓名',align :'center'},
		          //{name : 'liveAddr',mapping:'LIVE_ADDR',header : '家庭地址',align :'center'},
		          {name : 'houseCode',mapping:'HOUSE_CODE',header : '户号代码',align :'center'}, 
		          //{name : 'houseMaster',mapping:'HOUSE_MASTER',header : '户主名',align :'center'},
		          //{name : 'masterRelt',mapping:'MASTER_RELT',header : '与户主关系',align :'center'},
		          {name : 'masterRelt',mapping:'MASTER_RELT',header : '与户主关系',align :'center',type : 'mapping',store:boxstore0,mappingkey : 'code',mappingvalue : 'name'},
		          {name : 'identiCardNo',mapping:'IDENTI_CARD_NO',header : '身份证号码',align :'center'},
		          {name : 'remark',mapping:'REMARK',header : '备注',align :'center'}
		        ],
		        
        formColums :function(){
					return new Ext.form.FieldSet({items:[
//						util.layout._tr([util.form._td({name : 'villaNo',xtype : 'textfield',fieldLabel : '村编号',allowBlank:false,blankText:"此项非空，请填写"})],
//										[util.form._td({name : 'villaName',xtype : 'textfield',fieldLabel : '村名'})]
//										),
						util.layout._tr([util.form._td({name : 'identiCardNo',fieldLabel : '身份证号码',xtype : 'textfield'})],
								        [util.form._td({name : 'name',xtype : 'textfield',fieldLabel : '姓名'})]
										),
						util.layout._tr([util.form._td({name : 'liveAddr',fieldLabel : '家庭地址',xtype : 'textfield'})],
								        [util.form._td({name : 'houseCode',xtype : 'textfield',fieldLabel : '户号代码',id:'houseCodeId',readOnly:'true',allowBlank:false,blankText:"此项非空，请填写"})]
						),
						util.layout._tr([util.form._td({name : 'mobile',fieldLabel : '手机号码',xtype : 'textfield'})],
										//[util.form._td({name : 'houseMaster',fieldLabel : '户主名',xtype : 'textfield',id:'houseMasterN',readOnly:'true'})],
								        [util.form._td({name : 'masterRelt',xtype : 'combo',fieldLabel : '与户主关系',allowBlank:false,blankText:"此项非空，请填写",store:boxstore,valueField : 'code',displayField : 'name',editable:false})]
						),
						util.layout._tr([util.form._td({name : 'eduLevel',fieldLabel : '文化程度',xtype : 'combo',store : boxstore1,valueField : 'code',displayField : 'name'})],
						        		[util.form._td({name : 'marrgStatus',xtype : 'combo',fieldLabel : '婚姻状况',store : boxstore2,valueField : 'code',displayField : 'name'})]
								),
						util.layout._tr([util.form._td({name : 'healthStatus',fieldLabel : '健康状况',xtype : 'combo',store : boxstore6,valueField : 'code',displayField : 'name'})],
				        				[util.form._td({name : 'profeKind',xtype : 'combo',fieldLabel : '职业大类',store : boxstore4,valueField : 'code',displayField : 'name'})]
						),
						util.layout._tr([util.form._td({name : 'manageItem',fieldLabel : '职业（经营项目）',xtype : 'textfield'})],
		        						[util.form._td({name : 'yeIncome',xtype : 'textfield',fieldLabel : '年收入',allowBlank:false})]
						),
						util.layout._tr([util.form._td({name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',hidden:'true'})]
						),
						util.layout._tr([util.form._td({name : 'remark',xtype : 'textarea',   fieldLabel : '备注'})]
						)
				]});},
				
//		fclms:[ {
//			layout : 'form',
//			labelAlign:'right',
//			items : [ {
//				columnWidth : .5,
//				labelWidth : 60,
//				layout : 'form',
//				items : [ {name : 'identiCardNo',fieldLabel : '身份证号码',xtype : 'textfield' } ]
//			}, { name : 'name',xtype : 'textfield',fieldLabel : '姓名'}, 
//			   { name : 'liveAddr',fieldLabel : '家庭地址',xtype : 'textfield'} , 
//			   { name : 'houseCode',xtype : 'textfield',fieldLabel : '户号代码',id:'houseCodeId',readOnly:'true',allowBlank:false,blankText:"此项非空，请填写"}, 
//			   { name : 'mobile',fieldLabel : '手机号码',xtype : 'textfield'},
//			   { name : 'masterRelt',xtype : 'combo',fieldLabel : '与户主关系',allowBlank:false,blankText:"此项非空，请填写",store:boxstore,valueField : 'code',displayField : 'name',editable:false,resizable : true,mode : 'local',typeAhead : true,forceSelection : true,triggerAction : 'all',selectOnFocus : true},
//			   { name : 'eduLevel',fieldLabel : '文化程度',xtype : 'combo',store : boxstore1,valueField : 'code',displayField : 'name'},
//			   { name : 'marrgStatus',xtype : 'combo',fieldLabel : '婚姻状况',store : boxstore2,valueField : 'code',displayField : 'name'},
//			   { name : 'healthStatus',fieldLabel : '健康状况',xtype : 'combo',store : boxstore6,valueField : 'code',displayField : 'name'},
//			   { name : 'profeKind',xtype : 'combo',fieldLabel : '职业大类',store : boxstore4,valueField : 'code',displayField : 'name'},
//			   { name : 'manageItem',fieldLabel : '职业（经营项目）',xtype : 'textfield'},
//			   { name : 'yeIncome',xtype : 'textfield',fieldLabel : '年收入',allowBlank:false},
//			   { name : 'custId',xtype : 'textfield',fieldLabel : '客户编号',hidden:'true'},
//			   { name : 'remark',xtype : 'textarea',   fieldLabel : '备注'}]
//		}],		
				
				
				
		createFun:function(){
					Ext.getCmp("houseCodeId").setValue(houseId);
				},
		buts:[{
				text : '删除',
				iconCls:'deleteIconCss',
				handler : function() {
					deleteMemGrid(listpanel.grid);
				}
		}]
    });
    
    //家庭成员维护 窗口
    var modifyWindow = new Ext.Window({
				//layout : 'border',
				id:'modifyWindow',
				width : 800,
				height : 430,
				resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
//				maximized:true,
				collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'center',
//				border : false,
				animCollapse : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [listpanel]
		});
    
    var houseId ;
//    listpanel.grid.getStore().on('beforeload',function(){
//    listpanel.grid.getStore().baseParams={
//    		'houseId': houseId
//    	};
//    });
    
    var modifygrid = function(grid) {
        var _record = grid.getSelectionModel().getSelected();
	 	var checkedNodes = grid.getSelectionModel().selections.items;
        if (!_record||checkedNodes.length>1) {
        	Ext.MessageBox.alert('修改操作', '请选择要操作的一列！');
        } else {
        	var record = grid.getSelectionModel().getSelected();
        	houseId = record.json.HOUSE_CODE;
//        	Ext.getCmp("houseCodeId").setValue(houseId);
        	//listpanel.formColums().findById("houseCodeId").setValue(houseId);
        	//alert(listpanel.formColums().findById("houseCodeId").getValue());
        	modifyWindow.show();
//        	modifyWindow.addListener('show',function(){
        	listpanel.grid.getStore().load();
//        	});
        }
    };
    //家庭成员维护中的修改
    var deleteMemGrid = function(grid) {
        var _record = grid.getSelectionModel().getSelected();
        if (!_record) {
        	Ext.MessageBox.alert('删除操作', '请选择要操作的一列！');
        } else {
    	 	var checkedNodes = grid.getSelectionModel().selections.items;
    		var json={'cust_id':[]};
    		for(var i=0;i<checkedNodes.length;i++)
    			{
    				json.cust_id.push(checkedNodes[i].json.CUST_ID);
    			}
    		Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return;
				} 
    			Ext.Ajax.request({
    						url:basepath+'/homeUserDel.json',
                            method: 'POST',
    						success : function(response) {
    							Ext.Msg.alert('提示', '成功');
    							listpanel.grid.getStore().load();
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
    						}});
        	
    		});
        }
    };
}) ;
