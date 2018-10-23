// 定义自动当前页行号
var planRownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

var planProdColumns = new Ext.grid.ColumnModel([planRownum,
                                            	{
	header : '考核月份',
	width : 150,
	align : 'left',
	dataIndex : 'productDetailId',
	sortable : true
}, {
	header : '执行对象名称 ',
	width : 180,
	align : 'left',
	dataIndex : 'productId',
	sortable : true
},{
	header : '指标项名称',
	width : 120,
	align : 'left',
	dataIndex : 'createUser',
	sortable : true
}, {
	header : '上级指标任务',
	width : 120,
	align : 'left',
	dataIndex : 'createDate',
	sortable : true
},{
	header : '指标完成值',
	width : 120,
	align : 'left',
	dataIndex : 'createDate1',
	sortable : true
}]
);

var planProdRecord = Ext.data.Record.create([{
            name : 'productDetailId',mapping : 'PPDE_ID'
        },{
            name : 'productId',mapping : 'PRODUCT_ID'
        },{
            name : 'productName',mapping : 'PRODUCT_NAME'
        }, {
            name : 'createUser',mapping : 'USERNAME'
        }, {
            name : 'createDate',mapping : 'CREATE_DATE'
        },{
            name : 'planId',mapping : 'PLAN_ID'
        },{
            name : 'createDate1',mapping : 'CREATE_DATE1'
        },]);

var planProdStore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/planProductQuery.json',
		failure : function(response) {
			var resultArray = Ext.util.JSON.decode(response.status);
			if(resultArray == 403) {
				Ext.Msg.alert('提示', response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader({
		totalProperty:'num',// 记录总数
		root:'rows'// Json中的列表数据根节点
	}, planProdRecord)
});
var memberData= {
		TOTALCOUNT:3,
		rows:[
		{"rownum":"1","PPDE_ID":"201201","PRODUCT_ID":"北京分行"," PRODUCT_NAME":"002342","USERNAME":"存款日均","CREATE_DATE":"8,000,000,000.00","CREATE_DATE1":"9,000,000,000.00","PLAN_ID":"2342342"},
		{"rownum":"2","PPDE_ID":"201202","PRODUCT_ID":"北京分行"," PRODUCT_NAME":"002342","USERNAME":"存款日均","CREATE_DATE":"8,000,000,000.00","CREATE_DATE1":"7,000,000,000.00","PLAN_ID":"2342342"},
		{"rownum":"3","PPDE_ID":"201203","PRODUCT_ID":"北京分行"," PRODUCT_NAME":"002342","USERNAME":"存款日均","CREATE_DATE":"8,000,000,000.00","CREATE_DATE1":"8,000,000,111.00","PLAN_ID":"2342342"},			
		{"rownum":"4","PPDE_ID":"201204","PRODUCT_ID":"北京分行"," PRODUCT_NAME":"002342","USERNAME":"存款日均","CREATE_DATE":"8,000,000,000.00","CREATE_DATE1":"5,000,000,000.00","PLAN_ID":"2342342"},		
		{"rownum":"5","PPDE_ID":"201205","PRODUCT_ID":"北京分行"," PRODUCT_NAME":"002342","USERNAME":"存款日均","CREATE_DATE":"8,000,000,000.00","CREATE_DATE1":"7,000,000,000.00","PLAN_ID":"2342342"},	
		{"rownum":"6","PPDE_ID":"201206","PRODUCT_ID":"北京分行"," PRODUCT_NAME":"003554","USERNAME":"存款日均","CREATE_DATE":"8,000,000,000.00","CREATE_DATE1":"9,000,000,000.00","PLAN_ID":"2342342"},	
		{"rownum":"7","PPDE_ID":"201207","PRODUCT_ID":"北京分行"," PRODUCT_NAME":"002342","USERNAME":"存款日均","CREATE_DATE":"8,000,000,000.00","CREATE_DATE1":"8,000,000,000.00","PLAN_ID":"2342342"},	
		{"rownum":"8","PPDE_ID":"201208","PRODUCT_ID":"北京分行"," PRODUCT_NAME":"002342","USERNAME":"存款日均","CREATE_DATE":"8,000,000,000.00","CREATE_DATE1":"9,000,000,000.00","PLAN_ID":"2342342"},	
		{"rownum":"9","PPDE_ID":"201209","PRODUCT_ID":"北京分行"," PRODUCT_NAME":"002342","USERNAME":"存款日均","CREATE_DATE":"8,000,000,000.00","CREATE_DATE1":"9,000,000,000.00","PLAN_ID":"2342342"},
		{"rownum":"7","PPDE_ID":"201210","PRODUCT_ID":"北京分行"," PRODUCT_NAME":"002342","USERNAME":"存款日均","CREATE_DATE":"8,000,000,000.00","CREATE_DATE1":"8,000,000,000.00","PLAN_ID":"2342342"},	
		{"rownum":"8","PPDE_ID":"201211","PRODUCT_ID":"北京分行"," PRODUCT_NAME":"002342","USERNAME":"存款日均","CREATE_DATE":"8,000,000,000.00","CREATE_DATE1":"9,000,000,000.00","PLAN_ID":"2342342"},	
		{"rownum":"9","PPDE_ID":"201212","PRODUCT_ID":"北京分行"," PRODUCT_NAME":"002342","USERNAME":"存款日均","CREATE_DATE":"8,000,000,000.00","CREATE_DATE1":"5,000,000,000.00","PLAN_ID":"2342342"}
		
		]
	};
planProdStore.loadData(memberData);
var mktMonthSearch = new Ext.FormPanel({
	// layout:'fit',
	//title : '产品查询',
	frame : true,
	border : false,
	labelAlign : 'right',
	items : [ {
		layout : 'column',
		items : [ {
			columnWidth : .50,
			labelWidth : 100, // 标签宽度
			layout : 'form',
			items : [ {
				xtype : 'textfield',
				fieldLabel : '月度考核',
				name : 'PRODUCT_NAME',
				anchor : '95%'
			} ]
		}, {
			columnWidth : .50,
			labelWidth : 100, // 标签宽度
			layout : 'form',
			items : [{
				xtype : 'textfield',
				fieldLabel : '机构',
				name : 'PRODUCT_NAME',
				anchor : '95%'
			}  ]
		} ]
	} ],
	buttonAlign : 'center',
	buttons : [ {
		text : '查询',
		handler : function() {
			var planId=Ext.getCmp('listPanel').getSelectionModel().getSelected().get('planId');
			planProdStore.reload({
				params : {
					start : 0,
					rollId : document.getElementById('planIdStr').value,
					limit : proDetailbbar.pageSize,
					planId : planId
				}
			});

		},
		width : 80
	}, {
		text : '重置',
		handler : function() {
			mktSearch.getForm().reset();
		}

	} ]
});

var proDetailpagesize_combo = new Ext.form.ComboBox({
	name : 'pagesize',
	triggerAction : 'all',
	mode : 'local',
	store : new Ext.data.ArrayStore({
		fields : [ 'value', 'text' ],
		data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
				[ 500, '500条/页' ],[ 1000, '1000条/页' ]  ]
	}),
	valueField : 'value',
	displayField : 'text',
	value : '100',
	resizable : true,
	width : 85
});

var proDetailnumber = parseInt(proDetailpagesize_combo.getValue());

planProdStore.on('beforeload', function() {
	   var conditionStr =  mktSearch.getForm().getValues(false);
this.baseParams = {
      "condition":Ext.encode(conditionStr),
      planId : document.getElementById('planIdStr').value
};});

proDetailpagesize_combo.on("select", function(comboBox) {
	proDetailbbar.pageSize = parseInt(proDetailpagesize_combo.getValue());
	// number = parseInt(comboBox.getValue());
	planProdStore.reload({
		params : {
			start : 0,
			rollId : document.getElementById('planIdStr').value,
			planId : document.getElementById('planIdStr').value,
			limit : proDetailbbar.pageSize
		}
	});
});

var proDetailbbar = new Ext.PagingToolbar({
	pageSize : proDetailnumber,
	store : planProdStore,
	displayInfo : true,
	displayMsg : '显示{0}条到{1}条,共{2}条',
	// plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
	emptyMsg : "没有符合条件的记录",
	items : [ '-', '&nbsp;&nbsp;', proDetailpagesize_combo ]
});

var mktAssuMonthListPanel = new Ext.grid.GridPanel({
            height : 245,
            store : planProdStore,
            frame : true,
            cm : planProdColumns,             //prodColumns,  
            stripeRows : true,
            bbar : proDetailbbar
        });
