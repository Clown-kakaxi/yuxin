	
	var cussmGroupMemberCheck = new Ext.grid.CheckboxSelectionModel();
	
	var cusGrouprownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});			
			
	var groupMemberCol = new Ext.grid.ColumnModel(
			[
				cusGrouprownum,cussmGroupMemberCheck,
//				{
//					dataIndex:'affichename',
//					header:'客户群名称',
//					id:'affichename'
//				},
	           {
				header : '客户名称', // 列标题
				dataIndex : 'cusNames', // 数据索引:和Store模型对应
				sortable : true,
				width : 100
				// 是否可排序
		    },
		    {
		    	header:'组织机构代码',
		    	dataIndex:'customerCode',
		    	id:'customerCode'
		    },
		    {
		    
		    	header:'加入群组日期',
		    	dataIndex:'addDate',
		    	id:'addDate'
		    }
//		    {
//				header : '客户类型',
//				dataIndex : 'cusTypes',
//				sortable : true
////				width : 200
//			},{
//				header : '客户级别',
//				dataIndex : 'lev',
//				sortable : true
////				width : 200
//			}, {
//				header : '机构名称',
//				dataIndex : 'depandNames'
////				width :200
//			}, {
//				header : '客户贡献度',
//				dataIndex : 'levels'
////				width : 200
//			}
//			,
//			{
//				header : '是否集团客户',
//				dataIndex : 'isGroup'
//				width : 200
//			}
			]);			
	var custGroupStore = new Ext.data.Store({
				// 获取数据的方式
				//proxy : new Ext.data.HttpProxy({
						//	url : 'gridDemo.ered?reqCode=querySfxmDatas'
						//}),
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [
								{name:"affichename"},
								{
									name : 'cusNames' // Json中的属性Key值
								}, {
									name : 'cusTypes'
								},{
									name:'lev'
								}, {
									name : 'depandNames'
								}, {
									name : 'levels'
								},{
									name:'isGroup'
								},
								{
									name:'customerCode'
								},
								{
									name:'addDate'
								}
							])
			});
	
	var cusGroupMemberData= {
			TOTALCOUNT:3,
			rows:[
			{"affichename":"北京银行中关村支行客户群000001","cusNames":"北分图森（北京）科技发展有限公司","customerCode":"0101010101","addDate":"2011-06-01","cusTypes":"私营企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"affichename":"北京银行中关村支行客户群000001","cusNames":"阿贵家 ( 北京 ) 餐饮管理有限公司","customerCode":"0101010101","addDate":"2011-06-01","cusTypes":"国有企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"affichename":"北京银行中关村支行客户群000001","cusNames":"中政红旗(北京)文化传播有限公司","customerCode":"0101010101","addDate":"2011-06-01","cusTypes":"国有企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"affichename":"北京银行中关村支行客户群000001","cusNames":"中节能（天津）投资集团有限公司","customerCode":"0101010101","addDate":"2011-06-01","cusTypes":"国有企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"affichename":"北京银行中关村支行客户群000001","cusNames":"未来科讯信息技术(北京)有限公司","customerCode":"0101010101","addDate":"2011-06-01","cusTypes":"国有企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"affichename":"北京银行中关村支行客户群000001","cusNames":"威克瑞电线电缆有司电缆销售分司","customerCode":"0101010101","addDate":"2011-06-01","cusTypes":"私营企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"affichename":"北京银行中关村支行客户群000001","cusNames":"太通建设有限公司北京第八分公司","customerCode":"0101010101","addDate":"2011-06-01","cusTypes":"私营企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"affichename":"北京银行中关村支行客户群000001","cusNames":"尚恩优品(北京)科技发展有限公司","customerCode":"0101010101","addDate":"2011-06-01","cusTypes":"私营企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"},
			{"affichename":"北京银行中关村支行客户群000001","cusNames":"宁波保税区理工监测设备有限公司","customerCode":"0101010101","addDate":"2011-06-01","cusTypes":"私营企业","lev":"普通","depandNames":"北京银行","levels":"70%","isGroup":"是"}
			]
		};
		
		custGroupStore.loadData(cusGroupMemberData);
	// 分页工具栏
	var cusGroupBbar = new Ext.PagingToolbar({
						pageSize : 10,
						store : custGroupStore,
						displayInfo : true,
						displayMsg : '显示{0}条到{1}条,共{2}条',
						//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
						emptyMsg : "没有符合条件的记录"
						
					});
					
	    var cusGroupMemeberGrid = new Ext.grid.GridPanel({
	   	
	   	//width:400,
	   	split:true,
    	tbar:[
    	{
    		'text':'移除客户群',
    		handler:function()
    		{
    			
    		}
    	}
    	],
    	title:'客户群成员列表',
        store: custGroupStore,
		cm : groupMemberCol,
		bbar : cusGroupBbar,
		sm:cussmGroupMemberCheck,
		selModel:new Ext.grid.RowSelectionModel({
				singleSelect:true
				}),
        stripeRows: true,
        height: document.documentElement.clientHeight-180,
        width: '100%'
    });
    