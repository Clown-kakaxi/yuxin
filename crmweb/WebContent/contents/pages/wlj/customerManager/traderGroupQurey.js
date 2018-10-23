/**
 * 
* @Description: 买售商查询
* @author wangmk1  
* @date 2014-7-16
* 
* 修改：2015-03-10
*
 */
 
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
]);

var url=basepath+'/ocrmFCiBuygroupInfo.json';
var autoLoadGrid = true;

var lookupTypes = [
	'XD000226',
	'XD000271'
];

var fields=[
        {name:'SERNO',text:'业务流水号',gridField:true,searchField:true,resutlWidth:120},
        {name:'GRP_NAME',text:'买受商集团名称',gridField:true,searchField:true,resutlWidth:120},
        {name:'GRP_NO',text:'买受商集团编号',gridField:true,resutlWidth:120},
		{name:'PARENT_CUS_NAME',text:'主申请（集团）名称',gridField:false},
		{name:'MAIN_BR_ID',text:'授信主办行编号',gridField:false},
		{name:'MAIN_BR_NAME',text:'授信主办行名称',gridField:false},
		{name:'CUS_MANAGER',text:'GAO编号',gridField:false},
		{name:'CUS_MANAGER_NAME',text:'GAO名称',gridField:false},
		{name:'CREDIT_TOTAL',text:'授信总额',gridField:true,viewFn: money('0,000.00'),resutlWidth:120},
		{name:'CREDIT_CURRENCY',text:'授信币种',gridField:true,resutlWidth:120,translateType:'XD000226'},
		{name:'CREDIT_DUEDATE',text:'额度到期日',gridField:true,resutlWidth:120},
		{name:'AGREEMENT_RATE',text:'协定汇率',gridField:false},
		{name:'CREDIT_REMAINDER',text:'可用余额',gridField:false},
		{name:'INPUT_USER_ID',text:'创建人编号',gridField:false},
		{name:'INPUT_USER_NAME',text:'创建人名称',gridField:false},
		{name:'INPUT_DATE',text:'创建日期',gridField:false}
]

var tbar = [{
	text:'明细',
	handler : function(){
		showCustomerViewByTitle('买售商额度使用明细');
	}
}];

var customerView = [{
	title:'买售商额度使用明细',
	hideTitle:true,
	type:'form',
	suspendWidth:1000,
	autoLoadSeleted : true,
	groups : [{
		fields:['GRP_NAME','CREDIT_TOTAL','CREDIT_REMAINDER','CREDIT_DUEDATE','CREDIT_CURRENCY','AGREEMENT_RATE'],
		fn:function(GRP_NAME,CREDIT_TOTAL,CREDIT_REMAINDER,CREDIT_DUEDATE,CREDIT_CURRENCY,AGREEMENT_RATE){
			GRP_NAME.readOnly=true;CREDIT_TOTAL.readOnly=true;CREDIT_REMAINDER.readOnly=true;
			CREDIT_DUEDATE.readOnly=true;CREDIT_CURRENCY.readOnly=true;AGREEMENT_RATE.readOnly=true;
			GRP_NAME.cls='x-readOnly';CREDIT_TOTAL.cls='x-readOnly';CREDIT_REMAINDER.cls='x-readOnly';
			CREDIT_DUEDATE.cls='x-readOnly';CREDIT_CURRENCY.cls='x-readOnly';AGREEMENT_RATE.cls='x-readOnly';
			CREDIT_TOTAL.viewFn=money('0,000.00');
			return [GRP_NAME,CREDIT_TOTAL,CREDIT_REMAINDER,CREDIT_DUEDATE,CREDIT_CURRENCY,AGREEMENT_RATE]
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [generalbusiPanel];//一般业务占用明细
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [pledgebusiPanel];//质押占用明细
		}
	},{
		columnCount:0.945,
		fields : ['TEST'],
		fn : function(TEST){
			return [discountbusiPanel];//贴现业务占用明细
		}
	}],
	formButtons:[{
		text:'关闭',
		fn : function(formPanel,basicForm){
			 reloadCurrentData();
			 hideCurrentView();
		}
	}]
}];

//===========================================一般业务占用明细=============================================//
var newSm_1 = new Ext.grid.CheckboxSelectionModel();

var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 35
});

var newCm_1 =  new Ext.grid.ColumnModel([
		 editRrownum,	
		 
	     {header:'买售商集团编号',dataIndex:'GRP_NO',sortable:true,width:120,hidden:true},
	     {header:'卖方客户名称',dataIndex:'cus_name',sortable:true,width:120},
	     {header:'所属机构名称',dataIndex:'org_name',sortable:true,width:150,renderer:function(value){
  			var val = translateLookupByKey("XD000271",value);
			return val?val:"";}},
	     {header:'授信额度（元）',dataIndex:'crd_lmt',sortable:true,width:120,renderer:function cc(s){
	    	 var a=s.substring(0,4);
	    	 s=s.substring(4);
	    	    if(/[^0-9\.]/.test(s)) return "invalid value";
	    	    s=s.replace(/^(\d*)$/,"$1.");
	    	    s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
	    	    s=s.replace(".",",");
	    	    var re=/(\d)(\d{3},)/;
	    	    while(re.test(s))
	    	            s=s.replace(re,"$1,$2");
	    	    s=s.replace(/,(\d\d)$/,".$1");
	    	    return  a +s.replace(/^\./,"0.")
	    	    }},
	     {header:'启用额度（元）',dataIndex:'start_using_amt',sortable:true,width:120,renderer:function cc(s){
s.value;
	 	    	 var a=s.substring(0,4);
	 	    	 s=s.substring(4);
	 	    	    if(/[^0-9\.]/.test(s)) return "invalid value";
	 	    	    s=s.replace(/^(\d*)$/,"$1.");
	 	    	    s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
	 	    	    s=s.replace(".",",");
	 	    	    var re=/(\d)(\d{3},)/;
	 	    	    while(re.test(s))
	 	    	            s=s.replace(re,"$1,$2");
	 	    	    s=s.replace(/,(\d\d)$/,".$1");
	 	    	    return  a +s.replace(/^\./,"0.")
	 	    	    }},
	     {header:'目前余额（元）',dataIndex:'sum_balance',sortable:true,width:120,renderer:function cc(s){
	 	    	 var a=s.substring(0,4);
	 	    	 s=s.substring(4);
	 	    	    if(/[^0-9\.]/.test(s)) return "invalid value";
	 	    	    s=s.replace(/^(\d*)$/,"$1.");
	 	    	    s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
	 	    	    s=s.replace(".",",");
	 	    	    var re=/(\d)(\d{3},)/;
	 	    	    while(re.test(s))
	 	    	            s=s.replace(re,"$1,$2");
	 	    	    s=s.replace(/,(\d\d)$/,".$1");
	 	    	    return  a +s.replace(/^\./,"0.")
	 	    	    }},
	     {header:'额度到期日',dataIndex:'end_date',sortable:true,width:120},
	     {header:'买方',dataIndex:'borrow_cus',sortable:true,width:120}
]); 

var newPanelStore_1 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
		{
			url : basepath + '/ocrmFCiGroupGeneralbusi!Loan.json' 
		}),
		reader : new Ext.data.JsonReader({
			root : 'json.data'
		}, [{name:'GRP_NO'},
			{name:'cus_name'},
		    {name:'org_name'},
		    {name:'crd_lmt'},
		    {name:'start_using_amt'},
		    {name:'sum_balance'},
		    {name:'end_date'},
		    {name:'borrow_cus'}
	     ])
});	

var generalbusiPanel = new Ext.grid.GridPanel({
	title : '一般业务占用明细',
	autoScroll: true,
	height:200,
	store : newPanelStore_1,
	frame : true,
	sm:newSm_1,
	cm:newCm_1,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

//===========================================质押占用明细=============================================//
var newSm_2 = new Ext.grid.CheckboxSelectionModel();

var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 35
});

var newCm_2 =  new Ext.grid.ColumnModel([
		 editRrownum,	
	     {header:'买售商集团编号',dataIndex:'GRP_NO',sortable:true,width:120,hidden:true},
	     {header:'质押物编号',dataIndex:'pledge_id',sortable:true,width:120},
	     {header:'买方客户编号',dataIndex:'borrow_cus_id',sortable:true,width:120},
	     {header:'买方客户名称',dataIndex:'borrow_cus_name',sortable:true,width:120},
	     {header:'币种',dataIndex:'currency',sortable:true,width:120,renderer:function(value){
         			var val = translateLookupByKey("XD000226",value);
        			return val?val:"";
		 }},
	     {header:'占用金额',dataIndex:'occur_amt',sortable:true,width:120,renderer:function cc(s){

	    	    if(/[^0-9\.]/.test(s)) return "invalid value";
	    	    s=s.replace(/^(\d*)$/,"$1.");
	    	    s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
	    	    s=s.replace(".",",");
	    	    var re=/(\d)(\d{3},)/;
	    	    while(re.test(s))
	    	            s=s.replace(re,"$1,$2");
	    	    s=s.replace(/,(\d\d)$/,".$1");
	    	    return  s.replace(/^\./,"0.")
	    	    }},
	     {header:'占用日期',dataIndex:'occur_date',sortable:true,width:120}
]); 

var newPanelStore_2 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
		{
			url : basepath + '/ocrmFCiGroupGeneralbusi!Loan2.json' 
		}),
		reader : new Ext.data.JsonReader({
			root : 'json.data'
		}, [{name:'GRP_NO'},
			{name:'pledge_id'},
		    {name:'borrow_cus_id'},
		    {name:'borrow_cus_name'},
		    {name:'currency'},
		    {name:'occur_amt'},
		    {name:'occur_date'}
	     ])
});	

var pledgebusiPanel = new Ext.grid.GridPanel({
	title : '质押占用明细',
	autoScroll: true,
	height:200,
	store : newPanelStore_2,
	frame : true,
	sm:newSm_2,
	cm:newCm_2,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});

//===========================================贴现业务占用明细=============================================//
var newSm_3 = new Ext.grid.CheckboxSelectionModel();

var editRrownum = new Ext.grid.RowNumberer({
	  header : 'No.',
	  width : 35
});

var newCm_3 =  new Ext.grid.ColumnModel([
		 editRrownum,
	     {header:'买售商集团编号',dataIndex:'GRP_NO',sortable:true,width:120,hidden:true},
	     {header:'卖方客户名称',dataIndex:'cus_name_dis',sortable:true,width:120},
	     {header:'所属机构名称',dataIndex:'org_name_dis',sortable:true,width:150,renderer:function(value){
	  			var val = translateLookupByKey("XD000271",value);
				return val?val:"";}},
	     {header:'授信额度（元）',dataIndex:'crd_lmt_dis',sortable:true,width:120,renderer:function cc(s){
	    	 var a=s.substring(0,4);
	    	 s=s.substring(4);
	    	    if(/[^0-9\.]/.test(s)) return "invalid value";
	    	    s=s.replace(/^(\d*)$/,"$1.");
	    	    s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
	    	    s=s.replace(".",",");
	    	    var re=/(\d)(\d{3},)/;
	    	    while(re.test(s))
	    	            s=s.replace(re,"$1,$2");
	    	    s=s.replace(/,(\d\d)$/,".$1");
	    	    return  a +s.replace(/^\./,"0.")
	    	    }},
	     {header:'启用额度（元）',dataIndex:'start_amt',sortable:true,width:120,renderer:function cc(s){
	    	 var a=s.substring(0,4);
	    	 s=s.substring(4);
	    	    if(/[^0-9\.]/.test(s)) return "invalid value";
	    	    s=s.replace(/^(\d*)$/,"$1.");
	    	    s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
	    	    s=s.replace(".",",");
	    	    var re=/(\d)(\d{3},)/;
	    	    while(re.test(s))
	    	            s=s.replace(re,"$1,$2");
	    	    s=s.replace(/,(\d\d)$/,".$1");
	    	    return  a +s.replace(/^\./,"0.")
	    	    }},
	     {header:'目前余额（元）',dataIndex:'sum_balance_dis',sortable:true,width:120,renderer:function cc(s){
	    	 var a=s.substring(0,4);
	    	 s=s.substring(4);
	    	    if(/[^0-9\.]/.test(s)) return "invalid value";
	    	    s=s.replace(/^(\d*)$/,"$1.");
	    	    s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
	    	    s=s.replace(".",",");
	    	    var re=/(\d)(\d{3},)/;
	    	    while(re.test(s))
	    	            s=s.replace(re,"$1,$2");
	    	    s=s.replace(/,(\d\d)$/,".$1");
	    	    return  a +s.replace(/^\./,"0.")
	    	    }},
	     {header:'额度到期日',dataIndex:'end_date_dis',sortable:true,width:120},
	     {header:'买方',dataIndex:'brdis_cus_name',sortable:true,width:120}
]); 

var newPanelStore_3 = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy(
		{
		//	url : basepath + '/ocrmFCiGroupDiscountbusi.json' 
			url : basepath + '/ocrmFCiGroupGeneralbusi!Loan3.json' 
		}),
		reader : new Ext.data.JsonReader({
			root : 'json.data'
		}, [{name:'GRP_NO'},
			{name:'cus_name_dis'},
		    {name:'org_name_dis'},
		    {name:'crd_lmt_dis'},
		    {name:'start_amt'},
		    {name:'sum_balance_dis'},
		    {name:'end_date_dis'},
		    {name:'brdis_cus_name'}
	     ])
});	

var discountbusiPanel = new Ext.grid.GridPanel({
	title : '贴现业务占用明细',
	autoScroll: true,
	height:200,
	store : newPanelStore_3,
	frame : true,
	sm:newSm_3,
	cm:newCm_3,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});



var beforeviewshow= function(view){
	if(view._defaultTitle=='买售商额度使用明细'){
		if (!getSelectedData()||getAllSelects().length > 1) {
			Ext.Msg.alert('提示', '请选择一条数据进行操作！');
			return false;
		}
		//一般业务占用明细
		newPanelStore_1.load({params:{GRP_NO:getSelectedData().data.GRP_NO}});
		//质押占用明细
		newPanelStore_2.load({params:{GRP_NO:getSelectedData().data.GRP_NO}});
		//贴现业务占用明细
		newPanelStore_3.load({params:{GRP_NO:getSelectedData().data.GRP_NO}});

	}
};

function cc(s){
    if(/[^0-9\.]/.test(s)) return "invalid value";
    s=s.replace(/^(\d*)$/,"$1.");
    s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
    s=s.replace(".",",");
    var re=/(\d)(\d{3},)/;
    while(re.test(s))
            s=s.replace(re,"$1,$2");
    s=s.replace(/,(\d\d)$/,".$1");
    return s.replace(/^\./,"0.")
    };
    
var viewshow = function(view){
	if(view._defaultTitle == '买售商额度使用明细'){
		Ext.Ajax.request({
			url : basepath + '/ocrmFCiGroupGeneralbusi!Loan4.json' ,
			method : 'GET',
			params : {GRP_NO:getSelectedData().data.GRP_NO},
			success : function(response) {
				 var ret = Ext.decode(response.responseText);
				var balances=cc(ret.json[0].crd_lmt_balance);
				var credittotal=cc(getSelectedData().data.CREDIT_TOTAL);

				 if(balances != null && balances != ""){
					 getCurrentView().contentPanel.form.findField("CREDIT_REMAINDER").setValue(balances);
					 getCurrentView().contentPanel.form.findField("CREDIT_TOTAL").setValue(credittotal);
				 }
			}
		})
		getCurrentView().contentPanel.form.findField("CREDIT_REMAINDER").readOnly=true;
		getCurrentView().contentPanel.form.findField("CREDIT_REMAINDER").cls='x-readOnly';
	}
};

                     