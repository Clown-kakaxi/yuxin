Ext.onReady(function() {
	Ext.QuickTips.init();
		// 客户编号
		var cust_id = oCustInfo.cust_id;

		// 客户维系记录
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "",
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
//			width : document.body.clientWidth - 240,
			// 定义显示结果列表高度
//			gridHeight : document.body.clientHeight - 80,
			frame : true,
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {name : 'serviceId'}, 
			          {name : 'serviceStat',header : '服务状态',width : 100	}, 
			          {name : 'serviceCont',header : '服务内容',width : 100	}, 
			          {name : 'needResource',header : '所需资源',width : 100	}, 
			          {name : 'serviceKind',header : '服务类别',	width : 100}, 
			          {name : 'cantactChannel',header : '接触渠道',	width : 100	}, 
			          {name : 'aimProd',header : '目标营销产品',	width : 100	},
			          {name : 'pStartDate',header : '服务开始时间',	type : 'date',	width : 100	},
			          {name : 'pEndDate',header : '服务结束时间',	type : 'date',	width : 100	},
			          {name : 'serviceResult',header : '客户服务结果', width : 100	},
			          {name : 'needEvent',header : '待跟进事项', width : 100	}
		    ]

		});
		//被动服务信息 : 客服中心业务处理单信息
		var listPanel2 = new Mis.Ext.CrudPanel( {
			id : "listPanel2",
			title : "",
			// 主键
			primary : "id",
			// 是否需要双击显示详情，默认为显示，定义为false后，无此功能
			dbclick : false,
			// 查询路径设置
			stUrl : basepath + '/custServiceQuery!queryCscInfo.json?custId='+ cust_id,
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
//			width : document.body.clientWidth - 240,
			// 定义显示结果列表高度
//			gridHeight : document.body.clientHeight - 115,
			frame : true,
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {name : 'id'}, 
			          {name : 'acceptNo',header : '受理单号',width : 100	}, 
			          {name : 'account',header : '账卡号',width : 100	}, 
			          {name : 'accessType',header : '接入方式',width : 100	}, 
			          {name : 'contacterName',header : '联系人姓名',	width : 100	}, 
			          {name : 'contacterPhone',header : '联系电话',	width : 100}, 
			          {name : 'contacterSex',header : '联系人性别',	width : 100	},
			          {name : 'processingOrg',header : '处理机构',	width : 100	},
			          {name : 'customerType',header : '客户类型',	width : 100	},
			          {name : 'associationNetwork',header : '关联网点',	width : 100	},
			          {name : 'complaintsRep',header : '重复投诉标志',	width : 100	},
			          {name : 'complaintsConfirm',header : '投诉认定',	width : 100	},
			          {name : 'eventType',header : '事件类型',	width : 100	},
			          {name : 'eventItem',header : '事件项',		width : 100	},
			          {name : 'accidentType',header : '卡折机具情况',width : 100	},
			          {name : 'eventDesc',header : '事件简述',		width : 100	},
			          {name : 'cardType',header : '被吞卡种类',		width : 100	},
			          {name : 'acceptContent',header : '受理内容',		width : 100	},
			          {name : 'acceptTime',header : '受理时间',	type : 'date',	width : 100	},
			          {name : 'accepter',header : '受理人员',		width : 100	},
			          {name : 'serviceCenterOpinion',header : '客服中心意见',		width : 100	},
			          {name : 'sentTime',header : '派单时间',type : 'date',width : 100	},
			          {name : 'senter',header : '派单人员',		width : 100	},
			          {name : 'processingReply',header : '处理机构回复',		width : 100	},
			          {name : 'processingTime',header : '处理回复时间',	type : 'date',width : 100	},
			          {name : 'handler',header : '处理回复人员',		width : 100	},
			          {name : 'auditOpinion',header : '审核意见',		width : 100	},
			          {name : 'auditTime',header : '审核时间',	type : 'date',width : 100	},
			          {name : 'auditor',header : '审核人员',		width : 100	},
			          {name : 'reviewOpinion',header : '复核意见',		width : 100	},
			          {name : 'reviewTime',header : '复核时间',	type : 'date',	width : 100	},
			          {name : 'reviewer',header : '复核人员',		width : 100	}
			          ]

		});
		//被动服务信息 : 信用卡业务处理单信息
		var listPanel22 = new Mis.Ext.CrudPanel( {
			id : "listPanel22",
			title : "",
			// 主键
			primary : "id",
			// 是否需要双击显示详情，默认为显示，定义为false后，无此功能
			dbclick : false,
			// 查询路径设置
			stUrl : basepath + '/custServiceQuery!queryCardInfo.json?custId='+ cust_id,
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
//			width : document.body.clientWidth - 240,
			// 定义显示结果列表高度
//			gridHeight : document.body.clientHeight - 115,
			frame : true,
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {name : 'id'}, 
			          {name : 'acceptNo',header : '受理单号',width : 100	}, 
			          {name : 'account',header : '账卡号',width : 100	}, 
			          {name : 'accessType',header : '接入方式',width : 100	}, 
			          {name : 'contacterName',header : '联系人姓名',	width : 100	}, 
			          {name : 'contacterPhone',header : '联系电话',	width : 100}, 
			          {name : 'contacterSex',header : '联系人性别',		width : 100	},
			          {name : 'processingOrg',header : '处理机构',		width : 100	},
			          {name : 'customerType',header : '客户类型',		width : 100	},
			          {name : 'associationNetwork',header : '关联网点',		width : 100	},
			          {name : 'complaintsRep',header : '重复投诉标志',		width : 100	},
			          {name : 'complaintsConfirm',header : '投诉认定',		width : 100	},
			          {name : 'eventType',header : '事件类型',		width : 100	},
			          {name : 'eventItem',header : '事件项',		width : 100	},
			          {name : 'eventFineItem',header : '事件细项',		width : 100	},
			          {name : 'eventDesc',header : '事件简述',		width : 100	},
			          {name : 'acceptContent',header : '受理内容',		width : 100	},
			          {name : 'acceptTime',header : '受理时间',	type : 'date',	width : 100	},
			          {name : 'accepter',header : '受理人员',		width : 100	},
			          {name : 'serviceCenterOpinion',header : '客服中心意见',		width : 100	},
			          {name : 'sentTime',header : '派单时间',	type : 'date',	width : 100	},
			          {name : 'senter',header : '派单人员',		width : 100	},
			          {name : 'processingReply',header : '处理机构回复',		width : 100	},
			          {name : 'processingTime',header : '处理回复时间',	type : 'date',	width : 100	},
			          {name : 'handler',header : '处理回复人员',		width : 100	},
			          {name : 'auditOpinion',header : '审核意见',		width : 100	},
			          {name : 'auditTime',header : '审核时间',	type : 'date',	width : 100	},
			          {name : 'auditor',header : '审核人员',		width : 100	},
			          {name : 'reviewOpinion',header : '复核意见',		width : 100	},
			          {name : 'reviewTime',header : '复核时间',	type : 'date',	width : 100	},
			          {name : 'reviewer',header : '复核人员',		width : 100	}
			          ]

		});
		//预约服务信息
		var listPanel3 = new Mis.Ext.CrudPanel( {
			id : "listPanel3",
			title : "",
			// 主键
			primary : "id",
			// 是否需要双击显示详情，默认为显示，定义为false后，无此功能
			dbclick : false,
			// 查询路径设置
			stUrl : basepath + '/custServiceQuery!queryAppointmentInfo.json?custId=' + cust_id,
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
//			width : document.body.clientWidth - 240,
			// 定义显示结果列表高度
//			gridHeight : document.body.clientHeight - 80,
			frame : true,
			// 查询列表字段定义，有header属性则在页面显示
			// 如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ {name : 'appointId'}, 
			          {name : 'telephone',header : '客户联系电话',width : 100	}, 
			          {name : 'appointEvent',header : '预约事项',width : 100	}, 
			          {name : 'appointTime',header : '预约时间',type : 'date', width : 100	}, 
			          {name : 'appointOrg',header : '预约网点',	width : 100}, 
			          {name : 'appointResult',header : '预约结果',width : 100	}, 
			          {name : 'handleUser',header : '处理人',		width : 100	}]

		});
	
		var subTabPanel = new Ext.TabPanel({
			id : 'listPanel',
	    	activeTab : 0,
			tabPosition : 'top',
			height:document.body.clientHeight,
			items : [ {
				title : '客服中心业务',
				forceFit : false,
				autoScroll : true,
				layout : 'fit',
				items : [listPanel2]
			},{
				title : '信用卡业务处',
				forceFit : false,
				autoScroll : true,
				layout : 'fit',
				items : [listPanel22]
			}
			]
		});
		
		
		var listPanel = new Ext.TabPanel({
			renderTo:'viewport_center',
			id : 'listPanel',
			activeTab : 0,
			tabPosition : 'top',
			height : document.body.clientHeight - 30,
			items : [{
				title : '客户维系记录',
				forceFit : false,
				autoScroll : true,
				layout : 'fit',
				items : [listPanel]
			}, {
				title : '被动服务信息',
				forceFit : false,
				autoScroll : true,
				layout : 'fit',
				items : [subTabPanel]
			}, {
				title : '预约服务信息',
				forceFit : false,
				autoScroll : true,
				layout : 'fit',
				items : [listPanel3]
			}]
		});

	});