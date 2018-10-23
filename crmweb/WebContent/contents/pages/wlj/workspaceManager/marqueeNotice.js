/**
 * @describtion 跑马灯信息维护--业务逻辑层
 * @author liuyx
 * @date: 2017年10月18日 下午5:03:54 
 */

//查询地址
var url = basepath + '/ocrmSysMarqueeNoticeAction.json';

var fields = [{
    name    : 'ID',
    text    : '',
    hidden  : true,
    xtype   : 'hidden'
},{
    name    : 'TEXT',
    resutlWidth : 600,
    text    : '内容',
    searchField : true,
    allowBlank  : false,
    blankText   : '该选项为必输项',
    xtype   : 'textfield'
},{
    name    : 'VALID_DT',
    text    : '有效时间'
},{
    name    : 'VALID_DT_DATE',
    text    : '有效日期',
    format  : 'Y-m-d',
    gridField   : false,
    editable    : false,
    allowBlank  : false,
    blankText   : '该选项为必输项',
    xtype   : 'datefield'
},{
    name    : 'VALID_DT_TIME',
    text    : '有效时间',
    format  : 'H:i',
    gridField   : false,
    editable    : false,
    allowBlank  : false,
    blankText   : '该选项为必输项',
    xtype   : 'timefield'
},{
    name    : 'CREATE_DT',
    text    : '创建时间',
    xtype   : 'textfield'
},{
    name    : 'CREATE_USER',
    text    : '创建人',
    xtype   : 'textfield'
}];

var createView = true;
var editView = true;
var detailView = true;
//新增
var createFormViewer = [{
    fields  : ['ID', 'TEXT','VALID_DT_DATE', 'VALID_DT_TIME', 'CREATE_DT', 'CREATE_USER'],
    fn      : function(ID, TEXT,VALID_DT_DATE, VALID_DT_TIME, CREATE_DT, CREATE_USER){
    	TEXT.xtype = "textarea";
    	CREATE_DT.hidden  = true;
    	CREATE_USER.hidden  = true;
        return [ID, TEXT,VALID_DT_DATE, VALID_DT_TIME, CREATE_DT, CREATE_USER]
    },
    columnCount : 1
}];
//修改
var formViewers = [{
    fields  : ['ID', 'TEXT','VALID_DT_DATE', 'VALID_DT_TIME', 'CREATE_DT', 'CREATE_USER'],
    fn      : function(ID, TEXT,VALID_DT_DATE, VALID_DT_TIME, CREATE_DT, CREATE_USER){
    	TEXT.xtype = "textarea";
    	CREATE_DT.hidden  = false;
        CREATE_USER.hidden  = false;
        CREATE_DT.readOnly = true;
        CREATE_USER.readOnly = true;
        return [ID, TEXT,VALID_DT_DATE, VALID_DT_TIME, CREATE_DT, CREATE_USER]
    },
    columnCount : 1
}];

var tbar = [{
    text    : '删除',
    handler : function(theview){
        if(theview.baseType != 'createView'){
            if(!getSelectedData()){
                Ext.Msg.alert("提示 ","请选择一条数据进行操作")
                return false;
            }
        }
        var id = getSelectedData().get("ID");
        Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
            if(buttonId.toLowerCase() == "no"){
                return;
            }
            Ext.Ajax.request({
                url : basepath + '/ocrmSysMarqueeNoticeAction!batchDestroy.json',
                waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                params  : {
                    'idStr'   : id
                },
                success : function() {
                    Ext.Msg.alert('提示', '删除成功!' );
                    reloadCurrentData();
                },
                failure : function() {
                    Ext.Msg.alert('提示', '删除失败!' );
                    reloadCurrentData();
                }
            });
        });
    }
}];

/**
 * 结果域面板滑入前触发：
 * params：theview : 当前滑入面板；
 * return： false，阻止面板滑入操作；默认为true；
 */
var beforeviewshow = function(theview){
	if(theview.baseType != 'createView'){
        if(!getSelectedData()){
        	Ext.Msg.alert("提示 ","请选择一条数据进行操作")
            return false;
        }
	}
}