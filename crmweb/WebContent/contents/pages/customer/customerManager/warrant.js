Ext.onReady(function() {
			
	var custId =oCustInfo.cust_id;
	var custName =oCustInfo.cust_name;
	
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			//列模型
			var columns1 = new Ext.grid.ColumnModel([ rownum,{
				header : '编号', // 列标题
				dataIndex : 'id', // 数据索引:和Store模型对应
				sortable : true,
				align:'left',
				hidden : true,
				width : 100
			},{
				header : '统计日期', // 列标题
				dataIndex : 'custId', // 数据索引:和Store模型对应
				align:'right',
				sortable : true,
				width : 100,
				renderer:function(){return '2012-06-29';}
			// 是否可排序
			}, {
				header : '业务流水号', // 列标题
				dataIndex : 'busiSerialNum', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '合同编号',
				dataIndex : 'contractNo',
				align:'left',
				sortable : true,
				width : 100
				// 是否可排序
			}, {
				header : '管理编号 ', // 列标题
				dataIndex : 'mgrNo', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '抵押物编号 ', // 列标题
				dataIndex : 'mortThingNo', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '抵质押物名称 ', // 列标题
				dataIndex : 'mortThingName', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '借款人名称 ', // 列标题
				dataIndex : 'borrowerName', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '借款人客户名称', // 列标题
				dataIndex : 'borrowCustName', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '权属证件类型', // 列标题
				dataIndex : 'warrantCardTypeORA', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '权属证件号码', // 列标题
				dataIndex : 'warrantCardNo', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '抵押物类型', // 列标题
				dataIndex : 'mortThingTypeORA', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '所有权人', // 列标题
				dataIndex : 'ownerUser', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '所有权人证件类型', // 列标题
				dataIndex : 'ownerCardType', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '所有权人证件号码', // 列标题
				dataIndex : 'ownerCardId', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '所有权人贷款卡号', // 列标题
				dataIndex : 'ownerLoanCardId', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '数量', // 列标题
				dataIndex : 'count', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '评估金额（元）', // 列标题
				dataIndex : 'evaluateMoney', // 数据索引:和Store模型对应
				align:'right',
				renderer:money('0,000.00'),
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '认定金额（元）', // 列标题
				dataIndex : 'findingMoney', // 数据索引:和Store模型对应
				align:'right',
				renderer:money('0,000.00'),
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '最高可担保金额', // 列标题
				dataIndex : 'assureUplimitMoney', // 数据索引:和Store模型对应
				align:'right',
				renderer:money('0,000.00'),
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '担保金额（元）', // 列标题
				dataIndex : 'assureMoney', // 数据索引:和Store模型对应
				align:'right',
				renderer:money('0,000.00'),
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '担保起始日期', // 列标题
				dataIndex : 'assureStartDate', // 数据索引:和Store模型对应
				align:'right',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '担保到期日期', // 列标题
				dataIndex : 'assureEndDate', // 数据索引:和Store模型对应
				align:'right',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '登记编号', // 列标题
				dataIndex : 'regNo', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '抵押登记机关', // 列标题
				dataIndex : 'mortRegInst', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '抵押登记日期', // 列标题
				dataIndex : 'mortRegDate', // 数据索引:和Store模型对应
				align:'right',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '登记状态', // 列标题
				dataIndex : 'regStat', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '公证情况', // 列标题
				dataIndex : 'notariThing', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '备注', // 列标题
				dataIndex : 'remark', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '入账状态', // 列标题
				dataIndex : 'postingStat', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '状态', // 列标题
				dataIndex : 'status', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '经办机构', // 列标题
				dataIndex : 'managementInst', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '管理机构', // 列标题
				dataIndex : 'mgrInst', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '登记人', // 列标题
				dataIndex : 'regUser', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '登记日期', // 列标题
				dataIndex : 'regDate', // 数据索引:和Store模型对应
				align:'right',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '最后修改日期', // 列标题
				dataIndex : 'lastUpdateDate', // 数据索引:和Store模型对应
				align:'right',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '第一责任人', // 列标题
				dataIndex : 'firstResponsible', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '第二责任人', // 列标题
				dataIndex : 'sedResponsible', // 数据索引:和Store模型对应
				align:'left',
				sortable : true,
				width : 100
			// 是否可排序
			}
			]);

			var record1 = Ext.data.Record.create([
			 {name: 'id', mapping: 'ID'},                                    
             {name: 'custId', mapping: 'CUST_ID'},
             {name: 'busiSerialNum', mapping: 'BUSI_SERIAL_NUM'},                                   
             {name: 'mgrNo', mapping: 'MGR_NO'},  
             {name: 'contractNo', mapping: 'CONTRACT_NO'},
             {name: 'mortThingNo', mapping: 'MORT_THING_NO'},
             {name: 'mortThingName', mapping: 'MORT_THING_NAME'},
             {name: 'borrowerName', mapping: 'BORROWER_NAME'},
             {name: 'borrowCustName', mapping: 'BORROW_CUST_NAME'},
             {name: 'warrantCardType', mapping: 'WARRANT_CARD_TYPE'},
             {name: 'warrantCardTypeORA', mapping: 'WARRANT_CARD_TYPE_ORA'},
             {name: 'warrantCardNo', mapping: 'WARRANT_CARD_NO'},
             {name: 'mortThingType', mapping: 'MORT_THING_TYPE'},
             {name: 'mortThingTypeORA', mapping: 'MORT_THING_TYPE_ORA'},
             {name: 'ownerUser', mapping: 'OWNER_USER'},
             {name: 'ownerCardType', mapping: 'OWNER_CARD_TYPE_ORA'},
             {name: 'ownerCardId', mapping: 'OWNER_CARD_ID'},
             {name: 'ownerLoanCardId', mapping: 'OWNER_LOAN_CARD_ID'},
             {name: 'count', mapping: 'COUNT'},
             {name: 'evaluateMoney', mapping: 'EVALUATE_MONEY'},
             {name: 'findingMoney', mapping: 'FINDING_MONEY'},
             {name: 'assureUplimitMoney', mapping: 'ASSURE_UPLIMIT_MONEY'},
             {name: 'assureMoney', mapping: 'ASSURE_MONEY'},
             {name: 'assureStartDate', mapping: 'ASSURE_START_DATE'},
             {name: 'assureEndDate', mapping: 'ASSURE_END_DATE'},
             {name: 'regNo', mapping: 'REG_NO'},
             {name: 'mortRegInst', mapping: 'MORT_REG_INST'},
             {name: 'mortRegDate', mapping: 'MORT_REG_DATE'},
             {name: 'regStat', mapping: 'REG_STAT_ORA'},
             {name: 'notariThing', mapping: 'NOTARI_THING'},
             {name: 'remark', mapping: 'REMARK'},
             {name: 'postingStat', mapping: 'POSTING_STAT_ORA'},
             {name: 'status', mapping: 'STATUS_ORA'},
             {name: 'managementInst', mapping: 'MANAGEMENT_INST'},
             {name: 'mgrInst', mapping: 'MGR_INST'},
             {name: 'regUser', mapping: 'REG_USER'},
             {name: 'regDate', mapping: 'REG_DATE'},
             {name: 'lastUpdateDate', mapping: 'LAST_UPDATE_DATE'},
             {name: 'firstResponsible', mapping: 'FIRST_RESPONSIBLE'},
             {name: 'sedResponsible', mapping: 'SED_RESPONSIBLE'}
             ]);

			var store1 = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/loanMortInformation-info.json?cust_id='+custId
//					success :function(response){
//					Ext.Msg.alert("123",response.responseText);
//				}
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'ID',
			        messageProperty: 'message',
					root : 'json.data',
					totalProperty: 'json.count'
				}, record1)
			});

			// 每页显示条数下拉选择框
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
							[ 100, '100条/页' ], [ 250, '250条/页' ],
							[ 455, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '10',
				editable : false,
				width : 85
			});

			// 默认加载数据
			store1.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar1.pageSize = parseInt(pagesize_combo.getValue()),
				store1.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar1 = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store1,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});

			var listPanel1 = new Ext.grid.GridPanel(
					{
						title : '抵押物信息',
						//autoScroll : true,
						height:document.body.clientHeight-65,
						width : document.body.scrollWidth-230,
						gridHeight : document.body.clientHeight-100,
						store : store1,
						frame : true,
						cm : columns1,
						stripeRows : true,
						//region : 'center',
						frame : true,
						bbar : bbar1,// 分页工具栏
						viewConfig:{
							   forceFit:false,
							   autoScroll:true
							},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});

			//列模型
			var columns2 = new Ext.grid.ColumnModel([ rownum,{
				header : '编号', // 列标题
				dataIndex : 'id', // 数据索引:和Store模型对应
				sortable : true,
				hidden : true,
				width : 100
			},{
				header : '统计日期', // 列标题
				dataIndex : 'custId', // 数据索引:和Store模型对应
				sortable : true,
				width : 100,
				renderer:function(){return '2012-06-29';}
			// 是否可排序
			},{
				header : '业务流水号 ', // 列标题
				dataIndex : 'busiSerialNum', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '合同编号 ', // 列标题
				dataIndex : 'contractNo', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '担保起始日期',
				dataIndex : 'assureStartDate',
				sortable : true,
				width : 100
				// 是否可排序
			},{
				header : '担保到期日期', // 列标题
				dataIndex : 'assureEndDate', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '担保金额', // 列标题
				dataIndex : 'assureMoney', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},  {
				header : '借款人客户名称 ', // 列标题
				dataIndex : 'borrowerCustName', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '数量 ', // 列标题
				dataIndex : 'count', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '汇率', // 列标题
				dataIndex : 'exchangeRate', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '认定金额（元）', // 列标题
				dataIndex : 'findingMoney', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '第一责任人', // 列标题
				dataIndex : 'firstResponsible', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '冻结状态', // 列标题
				dataIndex : 'freezeStat', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '质押无效原因', // 列标题
				dataIndex : 'impaCantReason', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '质押是否有效', // 列标题
				dataIndex : 'impaIsEffect', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '质押物名称', // 列标题
				dataIndex : 'impaName', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '质押率', // 列标题
				dataIndex : 'impaRate', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '质押物编号', // 列标题
				dataIndex : 'impaThingNo', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '质押物类型', // 列标题
				dataIndex : 'impaType', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '最高可质押金额（元）', // 列标题
				dataIndex : 'impaUplimitMoney', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '最后修改日期', // 列标题
				dataIndex : 'lastUpdateDate', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '经办机构', // 列标题
				dataIndex : 'managementInst', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '管理机构', // 列标题
				dataIndex : 'mgrInst', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '管理编号', // 列标题
				dataIndex : 'mgrNo', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '币种', // 列标题
				dataIndex : 'moneyType', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '公证情况', // 列标题
				dataIndex : 'notariThing', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '所有权人证件号码', // 列标题
				dataIndex : 'ownerCardId', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '所有权人证件类型', // 列标题
				dataIndex : 'ownerCardType', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '所有权人贷款卡号', // 列标题
				dataIndex : 'ownerLoanCardId', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '所有权人', // 列标题
				dataIndex : 'ownerUser', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '入账状态', // 列标题
				dataIndex : 'postingStat', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '登记人', // 列标题
				dataIndex : 'regUser', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '备注', // 列标题
				dataIndex : 'remark', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '第二责任人', // 列标题
				dataIndex : 'sedResponsible', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '状态', // 列标题
				dataIndex : 'status', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '权证号码', // 列标题
				dataIndex : 'warrantId', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '权证名称', // 列标题
				dataIndex : 'warrantName', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}
			]);

			var record2 = Ext.data.Record.create([
			 {name: 'id', mapping: 'ID'},                                    
             {name: 'assureEndDate', mapping: 'ASSURE_END_DATE'},
             {name: 'assureMoney', mapping: 'ASSURE_MONEY'},                                   
             {name: 'assureStartDate', mapping: 'ASSURE_START_DATE'},  
             {name: 'borrowerCustName', mapping: 'BORROWER_CUST_NAME'},
             {name: 'busiSerialNum', mapping: 'BUSI_SERIAL_NUM'},
             {name: 'contractNo', mapping: 'CONTRACT_NO'},
             {name: 'count', mapping: 'COUNT'},
             {name: 'custId', mapping: 'CUST_ID'},
             {name: 'custName', mapping: 'CUST_NAME'},
             {name: 'exchangeRate', mapping: 'EXCHANGE_RATE'},
             {name: 'findingMoney', mapping: 'FINDING_MONEY'},
             {name: 'firstResponsible', mapping: 'FIRST_RESPONSIBLE'},
             {name: 'freezeStat', mapping: 'FREEZE_STAT'},
             {name: 'impaCantReason', mapping: 'IMPA_CANT_REASON'},
             {name: 'impaIsEffect', mapping: 'IMPA_IS_EFFECT'},
             {name: 'impaName', mapping: 'IMPA_NAME'},
             {name: 'impaRate', mapping: 'IMPA_RATE'},
             {name: 'impaThingNo', mapping: 'IMPA_THING_NO'},
             {name: 'impaType', mapping: 'IMPA_TYPE'},
             {name: 'impaUplimitMoney', mapping: 'IMPA_UPLIMIT_MONEY'},
             {name: 'lastUpdateDate', mapping: 'LAST_UPDATE_DATE'},
             {name: 'managementInst', mapping: 'MANAGEMENT_INST'},
             {name: 'mgrInst', mapping: 'MGR_INST'},
             {name: 'mgrNo', mapping: 'MGR_NO'},
             {name: 'moneyType', mapping: 'MONEY_TYPE'},
             {name: 'notariThing', mapping: 'NOTARI_THING'},
             {name: 'ownerCardId', mapping: 'OWNER_CARD_ID'},
             {name: 'ownerCardType', mapping: 'OWNER_CARD_TYPE'},
             {name: 'ownerLoanCardId', mapping: 'OWNER_LOAN_CARD_ID'},
             {name: 'ownerUser', mapping: 'OWNER_USER'},
             {name: 'postingStat', mapping: 'POSTING_STAT'},
             {name: 'regDate', mapping: 'REG_DATE'},
             {name: 'regUser', mapping: 'REG_USER'},
             {name: 'remark', mapping: 'REMARK'},
             {name: 'sedResponsible', mapping: 'SED_RESPONSIBLE'},
             {name: 'status', mapping: 'STATUS'},
             {name: 'warrantId', mapping: 'WARRANT_ID'},
             {name: 'warrantName', mapping: 'WARRANT_NAME'}
             ]);

			var store2 = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/loanImpaInformation-info.json?cust_id='+custId
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'ID',
			        messageProperty: 'message',
					root : 'json.data',
					totalProperty: 'json.count'
				}, record2)
			});
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
							[ 100, '100条/页' ], [ 250, '250条/页' ],
							[ 455, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '10',
				editable : false,
				width : 85
			});

			// 默认加载数据
			store2.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar2.pageSize = parseInt(pagesize_combo.getValue()),
				store2.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar2 = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store2,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			var listPanel2 = new Ext.grid.GridPanel(
					{
						title:'质押物信息',
//						autoScroll : true,
						height:document.body.clientHeight-65,
						width : document.body.scrollWidth-230,
						gridHeight : document.body.clientHeight-100,
						store : store2,
						cm : columns2,
						stripeRows : true,
						region : 'center',
						frame : true,
						bbar : bbar2,// 分页工具栏
				        viewConfig:{
						   forceFit:false,
						   autoScroll:true
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
			//列模型
			var columns3 = new Ext.grid.ColumnModel([ rownum,{
				header : '统计日期', // 列标题
				dataIndex : 'custId', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '编号', // 列标题
				dataIndex : 'id', // 数据索引:和Store模型对应
				sortable : true,
				hidden : true,
				width : 100
			},{
				header : '保证人', // 列标题
				dataIndex : 'assurer', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '保证人名称', // 列标题
				dataIndex : 'assurerName', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '保证人类型', // 列标题
				dataIndex : 'assurerType', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '借款人客户名称', // 列标题
				dataIndex : 'borrowerCustName', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '业务流水号', // 列标题
				dataIndex : 'busiSerialNum', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '合同编号', // 列标题
				dataIndex : 'contractNo', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '担保能力测算报告标识', // 列标题
				dataIndex : 'assCapaRepId', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '保证时效到期日', // 列标题
				dataIndex : 'assureAgingEndDate', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '可担保金额测算',
				dataIndex : 'assureCanMonMea',
				sortable : true,
				width : 100
				// 是否可排序
			}, {
				header : '保证合同编号 ', // 列标题
				dataIndex : 'assureConNo', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '担保到期日期 ', // 列标题
				dataIndex : 'assureEndDate', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '保证是否有效 ', // 列标题
				dataIndex : 'assureIsEffect', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '担保金额（元） ', // 列标题
				dataIndex : 'assureMoney', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}, {
				header : '保证人资产在他行已抵押金额', // 列标题
				dataIndex : 'assureOthBankMon', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '担保关系', // 列标题
				dataIndex : 'assureRela', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '担保起始日期', // 列标题
				dataIndex : 'assureStartDate', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '保证担保形式', // 列标题
				dataIndex : 'assureType', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '担保方式', // 列标题
				dataIndex : 'assureWay', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '保证人证件号码', // 列标题
				dataIndex : 'assurerCardNum', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '保证人证件类型', // 列标题
				dataIndex : 'assurerCardType', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '保证人贷款卡号', // 列标题
				dataIndex : 'assurerLoanCardId', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '保证人资产在他行已担保金额', // 列标题
				dataIndex : 'gtrOthBankGrntAmt', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '无效原因', // 列标题
				dataIndex : 'invalidReason', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '管理编号', // 列标题
				dataIndex : 'mgrNo', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			},{
				header : '备注', // 列标题
				dataIndex : 'remark', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
			// 是否可排序
			}
			]);

			var record3 = Ext.data.Record.create([
			 {name: 'id', mapping: 'ID'},                                    
             {name: 'assCapaRepId', mapping: 'ASS_CAPA_REP_ID'},
             {name: 'assureAgingEndDate', mapping: 'ASSURE_AGING_END_DATE'},                                   
             {name: 'assureCanMonMea', mapping: 'ASSURE_CAN_MON_MEA'},  
             {name: 'assureConNo', mapping: 'ASSURE_CON_NO'},
             {name: 'assureEndDate', mapping: 'ASSURE_END_DATE'},
             {name: 'assureIsEffect', mapping: 'ASSURE_IS_EFFECT'},
             {name: 'assureMoney', mapping: 'ASSURE_MONEY'},
             {name: 'assureOthBankMon', mapping: 'ASSURE_OTH_BANK_MON'},
             {name: 'assureRela', mapping: 'ASSURE_RELA'},
             {name: 'assureStartDate', mapping: 'ASSURE_START_DATE'},
             {name: 'assureType', mapping: 'ASSURE_TYPE'},
             {name: 'assureWay', mapping: 'ASSURE_WAY'},
             {name: 'assurer', mapping: 'ASSURER'},
             {name: 'assurerCardNum', mapping: 'ASSURER_CARD_NUM'},
             {name: 'assurerCardType', mapping: 'ASSURER_CARD_TYPE'},
             {name: 'assurerLoanCardId', mapping: 'ASSURER_LOAN_CARD_ID'},
             {name: 'assurerName', mapping: 'ASSURER_NAME'},
             {name: 'assurerType', mapping: 'ASSURER_TYPE'},
             {name: 'borrowerCustName', mapping: 'BORROWER_CUST_NAME'},
             {name: 'busiSerialNum', mapping: 'BUSI_SERIAL_NUM'},
             {name: 'contractNo', mapping: 'CONTRACT_NO'},
             {name: 'custId', mapping: 'CUST_ID'},
             {name: 'custName', mapping: 'CUST_NAME'},
             {name: 'gtrOthBankGrntAmt', mapping: 'GTR_OTH_BANK_GRNT_AMT'},
             {name: 'invalidReason', mapping: 'INVALID_REASON'},
             {name: 'mgrNo', mapping: 'MGR_NO'},
             {name: 'remark', mapping: 'REMARK'}
             ]);

			var store3 = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/assurerInformation-info.json?cust_id='+custId
//					success :function(response){
//					Ext.Msg.alert("123",response.responseText);
//					debugger;
//				}
				}),
				reader : new Ext.data.JsonReader({
					successProperty: 'success',
			        idProperty: 'ID',
			        messageProperty: 'message',
					root : 'json.data',
					totalProperty: 'json.count'
				}, record3)
			});
            
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
							[ 100, '100条/页' ], [ 250, '250条/页' ],
							[ 455, '500条/页' ] ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '10',
				editable : false,
				width : 85
			});
			// 默认加载数据
			store3.load({
				params : {
					start : 0,
					limit : parseInt(pagesize_combo.getValue())
				}
			});

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar3.pageSize = parseInt(pagesize_combo.getValue()),
				store3.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar3 = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store3,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			var listPanel3 = new Ext.grid.GridPanel(
					{
						title : '保证人信息',
//						autoScroll : true,
						height:document.body.clientHeight-65,
						width : document.body.scrollWidth-230,
						gridHeight : document.body.clientHeight-100,
						store : store3,
						cm : columns3,
						stripeRows : true,
						region : 'center',
						frame : true,
						bbar : bbar3,// 分页工具栏
						viewConfig:{
						   forceFit:false,
						   autoScroll:true
						},
						loadMask : {
							msg : '正在加载表格数据,请稍等...'
						}
					});
			
			// 定义展示窗口的tabPanel
			var tokenDelimiter = ':';
			var listPanel = new Ext.TabPanel({
				id : 'listPanel',
				activeTab : 0,
				tabPosition : 'top',
				items : [ {
					title : '抵押物信息',
					listeners : {
						'activate' : function() {
					        store1.load({
								params : {
									start : 0
									//marketTeamId : document.getElementById('marketTeamId').value
								}
							});
							
						}
					},
					items : [ listPanel1 ]
				},{
					title : '质押物信息',
					listeners : {
						'activate' : function() {
					store2.load({
								params : {
									start : 0
									//marketTeamId : document.getElementById('marketTeamId').value
								}
							});
							
						}
					},
					items : [ listPanel2 ]
				}, {
					title : '保证人信息',
					listeners : {
						'activate' : function() {
					store3.load({
								params : {
									//marketTeamId : document.getElementById('marketTeamId').value
								}
							});
						}
					},
					items : [ listPanel3 ]
				} ]

			});
			

			var editPlanPanel = new Ext.Panel({
				height:document.body.clientHeight-30,
				layout : 'fit',
				primary : "id",
		//		autoScroll : true,
				buttonAlign : "center",
				items : [ listPanel ]
			});
			
					var viewport_center = new Ext.Panel({
						autoScroll : true,
						renderTo:'viewport_center',
						height:document.body.clientHeight-30,
						layout : 'fit',
						items: [{   
				//	            autoScroll:true,
//			                    region:'center',
						        margins: '0 0 0 0',
						    	items:[editPlanPanel]}
						] 
					});
				});


