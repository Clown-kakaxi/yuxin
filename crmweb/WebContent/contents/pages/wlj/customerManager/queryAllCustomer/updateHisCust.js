/**
 * 客户信息变更历史查询
 * 注：调用此js必须保证有下述两个全局变量
 * custId 、custName
 * 
 * @author helin
 * @since 2014-11-02
 */
//注：此日期校验不通过的提示信息,不能移除
if(Ext.form.DateField){
   Ext.apply(Ext.form.DateField.prototype, {
      invalidText : "{0} 是无效的日期 - 必须符合格式： yyyy-mm-dd"
   });
}
var updateHisForm = new Ext.form.FormPanel({
    frame:true,
    labelWidth:100,
    labelAlign:'right',
    buttonAlign:'center',
    region:'north',
    height:110,
    items:[{
        layout:'column',
        items:[{
            columnWidth:.5,
            layout:'form',
            items:[
                {xtype: 'textfield',name: 'UPDATE_ITEM',fieldLabel: '修改项目',anchor:'95%'},
                {xtype: 'datefield',name: 'START_DATE',fieldLabel: '复核时间从',format:'Y-m-d',anchor: '95%'}
            ]
        },{
            columnWidth:.5,
            layout:'form',
            items:[
                {xtype: 'textfield',name: 'UPDATE_USER',fieldLabel: '修改人/复核人',anchor:'95%'},
                {xtype: 'datefield',name: 'END_DATE',fieldLabel: '至',format:'Y-m-d',anchor: '95%'}
            ]
        }]
    }],
    buttons:[{
        text:'查询',
        handler:function(){
        	if (!updateHisForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "查询条件格式错误，请重新输入！");
                return false;
            }
            var cond = updateHisForm.getForm().getValues(false);
            updateHisStore.on('beforeload',function(){
				updateHisStore.baseParams = {
					"condition": Ext.encode(cond),
					custId : custId
				};
			});
            updateHisStore.load({
                params:{
                    start: 0,
                    limit: parseInt(pagingComboUpdateHis.getValue())
                }
            });
        }
    },{
        text:'重置',
        handler:function(){
            updateHisForm.getForm().reset();
        }
    }]
});
var updateHisRn = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 35
});
var updateHisCm = new Ext.grid.ColumnModel([updateHisRn,
    {header : '客户编号',dataIndex : 'CUST_ID', sortable : 100, width : 100},
    {header : '修改项目', dataIndex : 'UPDATE_ITEM', sortable : true, width : 120 },
    {header : '修改前内容', dataIndex : 'UPDATE_BE_CONT', sortable : true, width : 120 },
    {header : '修改后内容', dataIndex : 'UPDATE_AF_CONT_VIEW', sortable : true, width : 120},
    {header : '修改人', dataIndex : 'USER_NAME',sortable : true, width : 100 },
    {header : '复核人', dataIndex : 'APPR_NAME',sortable : true, width : 100 },
    {header : '复核时间',dataIndex : 'APPR_DATE',sortable : true, width : 150}
]);
var updateHisRecord = new Ext.data.Record.create([
  {name:'CUST_ID'},
  {name:'UPDATE_ITEM'},
  {name:'UPDATE_BE_CONT'},
  {name:'UPDATE_AF_CONT_VIEW'},
  {name:'UPDATE_USER'},
  {name:'USER_NAME'},
  {name:'APPR_NAME'},
  {name:'APPR_DATE'}
]);
// create the data store
var updateHisStore = new Ext.data.Store({
    restful:true,
    proxy: new Ext.data.HttpProxy({
        url: basepath + '/dealWithFsx!queryCustUpdateHis.json'
    }),
    reader: new Ext.data.JsonReader({
        root:'json.data',
        totalProperty:'json.count'
    },updateHisRecord)
});

//create the page size combo
var pagingComboUpdateHis = new Ext.form.ComboBox({
    name: 'pagesize',
    triggerAction:'all',
    mode:'local',
    store:new Ext.data.ArrayStore({
        fields: ['value','text'],
        data: [['10','10条/页'],['20','20条/页'],['50','50条/页'],
            ['100','100条/页'],['250','250条/页'],['500','500条/页']]
    }),
    valueField: 'value',
    displayField: 'text',
    value: 20,
    editable: false,
    width: 85
});

//create the page toolbar
var pagingbarUpdateHis = new Ext.PagingToolbar({
    store: updateHisStore,
    pageSize: parseInt(pagingComboUpdateHis.getValue()),
    displayInfo: true,
    displayMsg: '显示{0}条到{1}条，共{2}条',
    emptyMsg: '没有符合条件的记录',
    items: ['-','&nbsp;&nbsp;',pagingComboUpdateHis ]
});

//reload data change combo value
pagingComboUpdateHis.on('select',function(comboBox){
    pagingbarUpdateHis.pageSize = parseInt(comboBox.getValue());
    updateHisStore.load({
        params:{
            start: 0,
            limit: parseInt(comboBox.getValue())
        }
    });
});
var updateHisGrid = new Ext.grid.GridPanel({
	frame: true,
	region:'center',
    autoScroll: true,
    stripeRows: true,
	bbar: pagingbarUpdateHis,
	store :updateHisStore, 	// 数据存储
	cm : updateHisCm, 		// 列模型o
	viewConfig:{},
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});
var updateHisWin = new Ext.Window({
	layout : 'border',
	closable : true,
	autoScroll : true,
	closeAction : 'hide',
	modal : true,
	loadMask : true,
	maximizable : true,
	width : 800,
	height : 400,
	buttonAlign : "center",
	title : '客户信息变更历史',
	items : [updateHisForm,updateHisGrid],
	listeners : {
		show : function(){
			updateHisStore.load({
				params : {
		            start : 0,
		            limit : parseInt(pagingComboUpdateHis.getValue())
		        }
			});
		}
	},
	buttons : [{
		text : '打印预览',
		handler : function(){
			if (!updateHisForm.getForm().isValid()) {
                Ext.Msg.alert("提示", "查询条件格式错误，请重新输入！");
                return false;
            }
			printUpdateHis();
		}
	},{
		text : '返回',
		handler : function(){
			updateHisWin.hide();
		}
	}]
});
var printUpdateHis = function(){
	var taskMgr = parent.Wlj?parent.Wlj.TaskMgr:undefined;
	var p = parent;
	for(var i=0;i<10 && !taskMgr;i++){
		p = p.parent;
		taskMgr = p.Wlj?p.Wlj.TaskMgr:undefined;
	}
	if(taskMgr.getTask('task_print_1')){
		taskMgr.getTask('task_print_1').close();
	}
	var cond = updateHisForm.getForm().getValues(false);
	var turl = '?custId='+custId+'&custName='+custName+'&UPDATE_ITEM='+cond.UPDATE_ITEM+'&UPDATE_USER='+cond.UPDATE_USER+'&START_DATE='+cond.START_DATE+'&END_DATE='+cond.END_DATE;
	var tempApp = parent._APP ? parent._APP : parent.parent._APP;
	tempApp.openWindow({
		name : '打印预览',
		action : basepath + '/contents/pages/wlj/printManager/printCustUpdateHis.jsp'+turl,
		resId : 'task_print_1',
		id : 'task_print_1',
		serviceObject : false
	});
};
updateHisStore.on('beforeload',function(){
	updateHisStore.baseParams = {
		custId : custId
	};
});