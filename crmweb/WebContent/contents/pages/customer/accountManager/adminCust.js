/**
 * 客户经理业务视图-管辖客户
 * @author songxs
 * @since 2012-12-10
 */

	var mrgid = viewWindow.mrgIds;


	var rownum = new Ext.grid.RowNumberer({// 定义自动当前页行号
		header : 'No.',
		width : 28
	});
	
	var adminCustColumns = new Ext.grid.ColumnModel([rownum,
	                                                 {header :'ID',dataIndex:'id',sortable : true,hidden:true},
	                                                 {header :'客户经理ID',dataIndex:'mgrId',sortable:true,hidden:true},
	                                                 {header :'客户号',dataIndex:'custId',sortable:true,width:190,renderer:function(val){
	                                                	  return '<span style="color:red;"><u>' + val + '</u></span>';
	                                                 }},
	                                                 {header :'客户名称',dataIndex:'custName',sortable:true,width:200},
	                                                 {header :'主协办类型',dataIndex:'mainTypeOra',sortabel:true,width:200},
	                                                 {header :'分配日期',dataIndex:'assignDate',sortabel:true,width:200},
	                                                 {header :'客户类型',dataIndex:'cusTyp',sortabel:true,width:200,hidden:true}
	                                                 ]);

	var adminCustRecord =  new Ext.data.Record.create([                                               
	                                                   {name:'id',mapping:'ID'},
	                                                   {name:'mgrId',mapping:'MGR_ID'},
	                                                   {name:'custId',mapping:'CUST_ID'},
	                                                   {name:'custName',mapping:'CUST_NAME'},
	                                                   {name:'mainType',mapping:'MAIN_TYPE'},
	                                                   {name:'mainTypeOra',mapping:'MAIN_TYPE_ORA'},
	                                                   {name:'assignDate',mapping:'ASSIGN_DATE'},
	                                                   {name:'cusTyp',mapping:'CUST_TYP'}
	                                                   ]);
		
	var adminCustReader = new Ext.data.JsonReader({//读取json数据的panel
		totalProperty:'json.count',
		root:'json.data'
		},adminCustRecord);
	
	var adminCustStore = new Ext.data.Store({
		
		proxy:new Ext.data.HttpProxy({
			url:basepath+'/adminCustQuery-Action.json',
			method:'GET'
		}),
		reader:adminCustReader
		});
	
	var spagesize_combo = new Ext.form.ComboBox({// 每页显示条数下拉选择框
		name : 'pagesize',
		triggerAction : 'all',
		mode : 'local',
		store : new Ext.data.ArrayStore({
			fields : [ 'value', 'text' ],
			data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
			         [ 100, '100条/页' ], [ 250, '250条/页' ],
			         [ 500, '500条/页' ] ]
		}),
		valueField : 'value',
		displayField : 'text',
		value : '20',
		forceSelection : true,
		width : 85
		});
	
	spagesize_combo.on("select", function(comboBox) {	// 改变每页显示条数reload数据
		sbbar.pageSize = parseInt(spagesize_combo.getValue()),
		adminCustStore.reload({
			params : {
			start : 0,
			limit : parseInt(spagesize_combo.getValue())
		}
		});
	});	
	
	adminCustStore.load({params:{	
		'custManagerId' : mrgid,
		start:0,
		limit: parseInt(spagesize_combo.getValue())
		}});
	
	var sbbar = new Ext.PagingToolbar({// 分页工具栏
		pageSize : parseInt(spagesize_combo.getValue()),
		store : adminCustStore,
		displayInfo : true,
		displayMsg : '显示{0}条到{1}条,共{2}条',
		emptyMsg : "没有符合条件的记录",
		items : [ '-', '&nbsp;&nbsp;', spagesize_combo ]
	});
	
	var adminCustGrid =  new Ext.grid.GridPanel({//日程查询列表数据grid
		
		id:'数据集关联关系列表',
		id:'adminCustGrid',
		store:adminCustStore,
		region:'center',
		frame:true,
		height:document.body.scrollHeight-38,
		width : document.body.clientWidth-220,
		loadMask:true,
		cm :adminCustColumns,
		bbar:sbbar,
			stripeRows : true,
			loadMask : {
			msg : '正在加载表格数据,请稍等...'
	}
	});
	adminCustGrid.on('cellclick',function(grid,row,col){//获取编辑的行数，从0开始，
		debugger;
		if(col=2){
	        var checkedNodes = grid.getSelectionModel().selections.items;
	        var viewWindows = new Com.yucheng.crm.cust.ViewWindow({
				id:'viewWindows',
				custId:checkedNodes[0].data.custId,
				custName:checkedNodes[0].data.custName,
				custTyp:checkedNodes[0].data.cusTyp
			});
			
			Ext.Ajax.request({
				url : basepath + '/commsearch!isMainType.json',
				method : 'GET',
				params : {
				'mgrId' : checkedNodes[0].data.mgrId,
				'custId' : checkedNodes[0].data.custId
			},
			success : function(response) {
				var anaExeArray = Ext.util.JSON.decode(response.responseText); 
			if(anaExeArray.json != null){
				if(anaExeArray.json.MAIN_TYPE=='1'){
					oCustInfo.omain_type=true;
				}else{
					oCustInfo.omain_type=false;
				}
			}
			else {
				oCustInfo.omain_type=false;
			}
				oCustInfo.cust_id = checkedNodes[0].data.custId;
				oCustInfo.cust_name = checkedNodes[0].data.custName;
				oCustInfo.cust_type = checkedNodes[0].data.cusTyp;
			//	viewWindow.hide();

				viewWindows.show();
			
			},
			failure : function(form, action) {}
			});
		}	
	});
	var viewport_center = new Ext.Panel({
		renderTo:'viewport_centers',
		layout:'fit',
		autoScroll:true,
		items: [adminCustGrid] 
	});	
	