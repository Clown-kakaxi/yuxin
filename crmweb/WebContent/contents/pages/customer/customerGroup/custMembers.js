var memberSearch=new Ext.FormPanel({
			title:'成员企业信息',
			hight:180,
			frame:true,
			border:false,
			buttonAlign:'center',
			labelAlign:'right',
			items : [{
						layout:'column',
						items : [
								{
									columnWidth : .33,
									layout : 'form',
									items : [{ 
										xtype : 'textfield',
										fieldLabel : '客户名称',
										name:'CUST_ZH_NAME',
										labelStyle:{
											width:'120px'
										},	
										anchor : '90%'
									}
									/*,{ 
										xtype : 'textfield',
										hidden :true,
										fieldLabel : 'id',
										Width:'100',
										id : 'cbid2',
										name : 'id',
										anchor : '90%'
									}*/
									]
								}, 
								{
									columnWidth : .33,
									layout : 'form',
									items : [{ 
										xtype : 'textfield',
										fieldLabel : '证件号码',
										name:'CERT_NUM',
										labelStyle:{
											width:'120px'
										},	
									
										anchor : '90%'
									}]
								},{
									columnWidth : .33,
									layout : 'form',
									items : [{ 
										xtype : 'datefield',
										name:'RELA_CREATE_DT',
										fieldLabel : '加入群日期',
										 format:'Y-m-d', 
										labelStyle:{
											width:'120px'
										},	
										
										anchor : '90%'
									}]
								}]
					}],
					//buttonAlign:'center',
					buttons:[{
						text:'查询',
						handler:function()
						{ cusstoreDetail.load(
						/*		{ params : {
	                                   start : 0,
	                                   limit : cusGroupbbar.pageSize }} */
								
						
						);}
					},{
						text:'重置',
						handler : function() {
							memberSearch.getForm().reset();
						}
					}]
		});
var cusrownumDetail = new Ext.grid.RowNumberer({
	header : 'No',
	width : 28
});

// 定义列模型
var cuscmDetail = new Ext.grid.ColumnModel([
 cusrownumDetail,
// {header : 'id', dataIndex : 'ID',sortable : true,width : 150,hidden:true}, 
{header : '客户号', dataIndex : 'CUST_ID',sortable : true,width : 150},
{header : '客户名称', dataIndex : 'CUST_ZH_NAME',sortable : true,width : 100 },
{header : '证件类型',dataIndex : 'CERT_TYPE',sortable : true,width : 100},
 {header : '证件号码',dataIndex : 'CERT_NUM',sortable : true,width : 100},
{header:'加入群组日期',dataIndex:'RELA_CREATE_DT',sortable : true}
	]);	

var cusstoreDetail = new Ext.data.Store({
restful:true,
proxy : new Ext.data.HttpProxy({url:basepath+'/querycustomerbase2.json'
}
),
reader: new Ext.data.JsonReader({
totalProperty : 'json.count',
root:'json.data'
//totalProperty : 'list'
}, [ 'CUST_ID','CUST_ZH_NAME','CERT_TYPE','CERT_NUM','RELA_CREATE_DT'])
});
cusstoreDetail.on('beforeload', function() {
   var conditionStr =  memberSearch.getForm().getValues(false);
  // var checkedNodes = cusGroupGrid.getSelectionModel().selections.items;
   this.baseParams = {
		  start : 0,
          limit : parseInt(cuspagesize_comboDetail.getValue()),
          "condition":Ext.encode(conditionStr),
          cbid:oBaseInfo.base_id
  };});

// 每页显示条数下拉选择框
var cuspagesize_comboDetail = new Ext.form.ComboBox({
			name : 'pagesize',
			triggerAction : 'all',
			mode : 'local',
			store : new Ext.data.ArrayStore({
						fields : ['value', 'text'],
						data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
									[ 100, '100条/页' ], [ 250, '250条/页' ],
									[ 500, '500条/页' ] ]
					}),
			valueField : 'value',
			displayField : 'text',
			value : '20',
			editable : false,
			width : 85
		});
var cusrownumDetail = parseInt(cuspagesize_comboDetail.getValue());
// 改变每页显示条数reload数据
cuspagesize_comboDetail.on("select", function(comboBox) {
cusbbarDetail.pageSize = parseInt(cuspagesize_comboDetail.getValue()),
cusstoreDetail.reload({
    params : {
        start : 0,
        limit : parseInt(cuspagesize_comboDetail.getValue())
    }
});
});
// 分页工具栏
var cusbbarDetail = new Ext.PagingToolbar({
			pageSize : cusrownumDetail,
			store : cusstoreDetail,
			displayInfo : true,
			displayMsg : '显示{0}条到{1}条,共{2}条',
			//plugins : new Ext.ux.ProgressBarPager(), // 分页进度条
			emptyMsg : "没有符合条件的记录",
			items : ['-', '&nbsp;&nbsp;', cuspagesize_comboDetail]
		});
// create the Grid
var cusGridDetail = new Ext.grid.GridPanel({
	id:'cusGrid',
	tbar:[ 
	        	{
					text : '创建商机',
					iconCls:'addIconCss',
					handler : function() {
					resetAddForm();
					addMyBusOpportInit();	
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
    store: cusstoreDetail,
	cm : cuscmDetail,
	height :document.body.scrollHeight-128,
//	sm:cussmDetail,
	bbar : cusbbarDetail,
	selModel:new Ext.grid.RowSelectionModel({
			singleSelect:true
			}),
    stripeRows: true,
   listeners:{
   	rowdblclick:function()
   	{
   		//window.location="../customerManager/customerViewIndex.html";
   	}
   },
    width: '100%'
});
cusGridDetail.on('rowdblclick', function(cusGridDetail, rowIndex, event) {

	custwindow(window.Ext.getCmp('cusGrid').getSelectionModel().selections.items[0].data.CUST_ID);
});
// 布局模型
var custMembersPanel = new Ext.Panel( {
	renderTo:'viewport_center',
	height:document.body.scrollHeight-30,
	width:document.body.scrollWidth-160,
	//layout : 'fit',
	autoScroll:true,
	items : [memberSearch,cusGridDetail]
});