Ext.onReady(function() {
	Ext.QuickTips.init();
		// 客户编号
		var cust_id = oCustInfo.cust_id;
		var boxstore4 = new Ext.data.ArrayStore({
			fields : ['name', 'code'],
			data : [['网站', '1'],['客服呼叫中心', '2']]
		});
		// 客户维系记录
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "客户维系记录",
			// 主键
			primary : "serviceId",
			// 是否需要双击显示详情，默认为显示，定义为false后，无此功能
			dbclick : false,
			// 查询路径设置
			stUrl : basepath + '/custServiceQuery!queryCsInfo.json?custId='+ cust_id,
			// 定义查询条件Form的高度
			seFormHeight : 0,
			// 定义增删详情页面弹出窗口高度
			winHeight : 450,
			// 定义增删详情页面弹出窗口宽度
			winWidth : 900,
			// 设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			// 定义高度
//			height : document.body.clientHeight,
			// 定义宽度
//			gridWidth : document.body.clientWidth - 220,
//			// 定义显示结果列表高度
//			gridHeight : document.body.clientHeight - 100,
			frame : true,
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {name : 'serviceId'}, 
			          {name : 'serviceStat',header : '服务状态',width : 80	}, 
			          {name : 'serviceCont',header : '服务内容',width : 100	}, 
			          {name : 'needResource',header : '所需资源',width : 100	}, 
			          {name : 'serviceKind',header : '服务类别',	width : 80}, 
			          {name : 'cantactChannel',header : '接触渠道',	width : 80	}, 
			          {name : 'aimProd',header : '目标营销产品',	width : 100	},
			          {name : 'pStartDate',header : '服务开始时间',	type : 'date',	width : 100	},
			          {name : 'pEndDate',header : '服务结束时间',	type : 'date',	width : 100	},
			          {name : 'serviceResult',header : '客户服务结果', width : 100	},
			          {name : 'needEvent',header : '待跟进事项', width : 100	}
		    ]

		});
		
		//预约服务信息
		var listPanel3 = new Mis.Ext.CrudPanel( {
			id : "listPanel3",
			title : "业务申请信息",
			// 主键
			primary : "id",
			// 是否需要双击显示详情，默认为显示，定义为false后，无此功能
			dbclick : true,
			checkbox : true,
			// 查询路径设置
			stUrl : basepath + '/custBusinessApply!indexPage.json?custId=' + cust_id,
			//addUrl : basepath + '/custBusinessApply.json',
			//updateUrl : basepath + '/custBusinessApply.json',
			//deUrl : basepath + '/custBusinessApply!batchDestroy.json',
			// 定义查询条件Form的高度
			seFormHeight : 0,
			// 定义增删详情页面弹出窗口高度
			winHeight : 300,
			// 定义增删详情页面弹出窗口宽度
			winWidth : 800,
			// 设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			// 定义高度
			// 定义宽度
//			gridWidth : document.body.clientWidth - 220,
//			// 定义显示结果列表高度
//			gridHeight : document.body.clientHeight - 100,
			frame : true,
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {name : 'id'},
					  {name : 'custId',header : '客户编号',width : 100},
			          {name : 'custName',header : '客户名称',width : 100	}, 
			          {name : 'channelSource',header : '渠道来源',type : 'mapping',store : boxstore4, mappingkey : 'code',mappingvalue : 'name',width : 100}, 
			          {name : 'officeAddress',header : '办公地址', width : 100}, 
			          {name : 'contactor',header : '联系人',	width : 100}, 
			          {name : 'contactWay',header : '联系方式',width : 100}, 
			          {name : 'applyBusinessType',header : '申请业务品种',width : 100	},
			          {name : 'applyTime',header : '申请时间',type : 'date',width : 100},
			          {name : 'dealOrg',header : '建议经办机构',width : 100},
			          {name : 'remark',header : '备注',	width : 100	}],
			formColums : function(){
				return new Ext.form.FieldSet({items:[
					util.layout._tr(
							[util.form._td({name : 'custName',xtype : 'textfield',fieldLabel : '客户名称'})],
							[util.form._td({name : 'channelSource',xtype : 'combo',fieldLabel : '渠道来源',store : boxstore4,valueField : 'code',displayField : 'name'})]
					),
					util.layout._tr(
							[util.form._td({name : 'officeAddress',xtype : 'textfield',fieldLabel : '办公地址'})],
							[util.form._td({name : 'contactor',xtype : 'textfield',fieldLabel : '联系人'})]
					),
					util.layout._tr(
							[util.form._td({name : 'contactWay',xtype : 'textfield',fieldLabel : '联系方式'})],
							[util.form._td({name : 'applyBusinessType',xtype : 'textfield',fieldLabel : '申请业务品种'})]
					),
					util.layout._tr(
							[util.form._td({id : 'applyTime',name : 'applyTime',xtype : 'datefield',fieldLabel : '申请时间'})],
							[util.form._td({name : 'dealOrg',xtype : 'textfield',fieldLabel : '建议经办机构'})]
							
					),
					util.layout._tr([util.form._td({name : 'remark',xtype : 'textarea',fieldLabel : '备注'})],
						[util.form._td({id : 'custId',name : 'custId',xtype : 'hidden'})]
					),
					util.layout._tr(
						[util.form._td({name : 'id',xtype : 'hidden'})]
					)
				]});
			},
			afterSeOneFun : function(b) {
				Ext.getCmp('applyTime').setValue(new Date(b.applyTime.time));
			},
			createFun : function(){
				Ext.getCmp('custId').setValue(cust_id);
			}

		});
		var tabPanel = new Ext.TabPanel({
			renderTo:'viewport_center',
			id : 'tabPanel',
			activeTab : 0,//判断是否是从首页链接过来
			tabPosition : 'top',
			height : document.body.clientHeight - 30,
			items : [{
				title : '客户维系记录',
				forceFit : false,
				autoScroll : true,
				layout : 'fit',
				items : [listPanel]
			}, {
				title : '业务申请信息',
				forceFit : false,
				autoScroll : true,
				layout : 'fit',
				items : [listPanel3]
			}]
		});
				
	/*	var editPlanPanel = new Ext.Panel({
			renderTo:'viewport_center',
			width:document.body.scrollWidth-220,
			labelWidth : 250,
			layout : 'fit',
			primary : "id",
			buttonAlign : "center",
			items : [ tabPanel ]
		});*/

	});