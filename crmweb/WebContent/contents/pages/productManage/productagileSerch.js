	var custCharacSm4 = new Ext.grid.CheckboxSelectionModel();
   	var targetCustColumns = new Ext.grid.ColumnModel(//产品特性项列数
		[
			new Ext.grid.RowNumberer(),
			custCharacSm4,
			{ header:'客户号',dataIndex:'custId',sortable:true},
			{ header:'客户名称',dataIndex:'custZHName',sortable:true,width:100},
			{ header:'客户级别',dataIndex:'custLevOra',sortable:true,width:100},
			{ header:'主办机构',dataIndex:'institutionName',sortable:true},
			{ header:'主办客户经理',dataIndex:'mgrName',sortable:true},
			{ header:'是否买该产品',dataIndex:'isBuyTheProdOra',sortable:true}
			
		]
	);

	var targetCustRecord= Ext.data.Record.create(//特性项记录（record）
			[
			{name:'custId',mapping:'CUST_ID'},
			{name:'custZHName',mapping:'CUST_ZH_NAME'},
			{name:'custLev',mapping:'CUST_LEV'},
			{name:'custLevOra',mapping:'CUST_LEV_ORA'},
			{name:'institutionName',mapping:'INSTITUTION_NAME'},
			{name:'mgrName',mapping:'MGR_NAME'},
			{name:'isBuyTheProdOra',mapping:'IS_BUY_THE_PROD_ORA'}
			]
		);
	var targetCustReader = new Ext.data.JsonReader(//读取特性项数据的jsonReader
			{
			    totalProperty:'json.count',
			    root:'json.data'
			    },targetCustRecord
		);	

	var targetCustStore = new Ext.data.Store({//特性项数据的store
	        proxy:new Ext.data.HttpProxy({
	        url:basepath+'/product-targetCust.json',
	        method:'GET'
	        }),
		reader:targetCustReader
	});
   // 每页显示条数下拉选择框
    var spagesize_combo3 = new Ext.form.ComboBox({
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
    // 改变每页显示条数reload数据
    spagesize_combo3.on("select", function(comboBox) {
    	bbar3.pageSize = parseInt(spagesize_combo3.getValue()),
    	targetCustStore.reload({
            params : {
                start : 0,
                limit : parseInt(spagesize_combo3.getValue())
            }
        });
    });
	  var bbar3 = new Ext.PagingToolbar({
          pageSize : parseInt(spagesize_combo3.getValue()),
          store : targetCustStore,
          displayInfo : true,
          displayMsg : '显示{0}条到{1}条,共{2}条',
          emptyMsg : "没有符合条件的记录",
          items : [ '-', '&nbsp;&nbsp;', spagesize_combo3 ]
      });
var targetCustGrid = new Ext.grid.GridPanel({//不可修改的gridTable
		
		title:'目标客户清单',
		store:targetCustStore, 
		frame:true,
		cm:targetCustColumns,
		region:'center',
		sm:custCharacSm4,
      	stripeRows: true,
      	tbar:[ 
        {
			text:'生成商机',
			 iconCls:'addIconCss',
			handler:function(){
        	busiOpportAddWindowInit();
        	//	window.location.href = '../mktManage/mktChanceManage/mktChanceList.jsp';
        	//addChanceWindow.show();
            }
	    },'-',  {
            text : '生成营销活动',
            iconCls:'addIconCss',
            handler : function() {
            	
	    	addActivityForm.form.reset();
	    	addActivityProdForm.form.reset();
	    	addActivityCustForm.form.reset();
	    	addActivityForm.form.findField('createUser').setValue(__userId);
	    	addActivityForm.form.findField('test').setValue(__userName);
	    	addActivityForm.form.findField('createDate').setValue(new Date());
	    	addActivityForm.form.findField('mktActiStat').setValue(1);
	    	addActivityForm.form.findField('mktActiName').setValue('小企业扶持贷款推广');
	    	addActivityForm.form.findField('mktActiType').setValue('推广活动');
	    	addActivityForm.form.findField('mktActiMode').setValue('宣传');
	    	addActivityForm.form.findField('mktActiTeam').setValue('小企业贷款组');
	    	addActivityForm.form.findField('mktActiCost').setValue('1000');
	    	addActivityForm.form.findField('mktActiAddr').setValue('南京市建邺区应天西路所叶路20号');
	    	addActivityForm.form.findField('mktActiCont').setValue('宣传小企业的扶持贷款政策，吸引贷款');
	    	addActivityForm.form.findField('actiCustDesc').setValue('该工业园区的小企业');
	    	addActivityForm.form.findField('actiOperDesc').setValue('本行支行客户经理');
	    	addActivityForm.form.findField('actiProdDesc').setValue('小企业扶持到款');
	    	addActivityForm.form.findField('mktActiAim').setValue('推广');
	    	addActivityForm.form.findField('actiRemark').setValue('无');
	    			 				
	    	addActivityWindow.show();

            }}
      	],
 //      autoExpandColumn:'productName',
      		bbar:bbar3
	});

//查找目标客户
	var targetCustDetail = new Ext.Window({
		closeAction:'hide',
		height:400,
		width:630,
		buttonAlign:'center',
		layout:'fit',
		buttons:[
		{
			text:'关闭',
			handler:function()
			{
			targetCustDetail.hide();
			}
		}
		],
		items:targetCustGrid
	});