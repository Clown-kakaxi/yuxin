/***
 * 日志管理
 * @author CHANGZH
 * @date 2014-04-28
 */
/**引入公共JS文件*/
imports(['/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
         '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'
]);
var detailView = false;
var url = basepath+'/AdminLogQuery.json';
//远程数据字典定义
var lookupTypes = [{
	TYPE : 'LOG_TYPE',
	url : '/AdminLogType.json',
	key : 'key',
	value : 'value',
	root : 'JSON'
}];
//本地数据字典定义
var localLookup = {
	'TIME_SORT' : [
		{key : '1',value : '时间顺序'},
	    {key : '2',value : '时间逆序'}
	]
};
var fields = [
	  {name: 'ID',hidden : true},
	  {name: 'LOG_TYPE', text : '日志类型', searchField: true, translateType:'LOG_TYPE', 
	   viewFn : function(data){return '<b>'+data+'</b>';}
	  },
	  {name: 'APP_ID',hidden : true},
	  {name: 'USERNAME', text : '操作用户', searchField: true},                                   
	  {name: 'CONTENT',text:'操作信息', searchField: false,  resutlWidth:200},                                   
	  {name: 'AFTER_VALUE',text:'参数', searchField: false, resutlWidth:250},                                   
	  {name: 'LOGIN_IP',text:'登陆IP地址', searchField: false, resutlWidth:150},			   
	  {name: 'OPER_TIME',text:'操作时间', xtype:'datefield', format:'Y-m-d', resutlWidth:120}
	  
	  //{name: 'START_TIME',text:'开始时间', searchField: true,hidden:true, xtype:'datefield', format:'Y-m-d', resutlWidth:120},
	 //{name: 'END_TIME',text:'结束时间', searchField: true,hidden:true, xtype:'datefield', format:'Y-m-d', resutlWidth:120}
	];
/**
 * 导出按钮
 * 删除按钮
 */
var tbar = [
	new Com.yucheng.crm.common.NewExpButton({ //导出按钮
	    formPanel : 'searchCondition',
	    url : basepath + '/AdminLogQuery.json'
	})
,'-', {
	/**
	 * 日志删除
	 */
	text : '删除',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选中要删除的日志记录！');
			return false;
		}else{
			var logId = getSelectedData().data.ID;
			Ext.MessageBox.confirm('提示','确定删除吗该日志吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return;
				}  
        		Ext.Ajax.request({
	    			url : basepath+'/AdminLogManagerAction/'+logId+'.json?idStr='+logId,
	    			method : 'DELETE',        
	    			waitMsg : '正在删除数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
	    			success : function() {
	    				Ext.Msg.alert('提示', '操作成功!');
	    				reloadCurrentData();
					},
					failure : function() {
						Ext.Msg.alert('提示', '操作失败!');
					}
        		});
			});
		}
	}
},'-',{
    text    : '查看日志文件',
    handler : function(){
        logFileWin.show();
        logFileStore.load({
            params  : {
                start   : 0,
                limit   : 10
            }
        });
    }
}];
var logFileCols = [
    {name: 'logFileName'},
    {name: 'logFileSize'},
    {name: 'logFilePath'},
    {name: 'logFileText'}
];
var logFileStore = new Ext.data.JsonStore({
	restful         : true,
    url             : basepath + '/adminLogFileAction!listLogFile.json',
    root            : "data",
    totalProperty   : "total",
    remoteSort      : false,
    fields          : logFileCols
});
var logFileCols = new Ext.grid.ColumnModel({
    columns : [
        {header: "文件", dataIndex: 'logFileName'},
        {header: "文件大小", dataIndex: 'logFileSize'},
        {header: "路径", dataIndex: 'logFilePath'},
        {header: "下载", dataIndex: 'logFileName',renderer : function(data, metadata, record, rowIndex, columnIndex, store){
            return '<img style="width:16px;height:16px;align:center;cursor:pointer;" ' +
                    ' onclick=logFileDownload("'+data+'")' +
                    ' src="'+basepath+'/contents/img/UltraMix55.gif" />';
        }}
    ]
});
/**
 * 日志表格
 */
var logFileGrid = new Ext.grid.GridPanel({
    stripeRows      : false,
    style           : 'padding:5px;',
    bodyStyle       : 'border:none;border-bottom:3px solid #E9E9E9;',
    region          : 'center',
    id              : 'logFileGrid',
    store           : logFileStore,
    cm              : logFileCols,
    loadMask        : true,
    selModel        : new Ext.grid.RowSelectionModel({
                        singleSelect : true
                      }),
    viewConfig  : {
        forceFit : true     
    },
    bbar        : new Ext.PagingToolbar({
        pageSize    : 10,//分页大小
        store       : logFileStore,
        grid        : logFileGrid,
        displayInfo : true,
        displayMsg  : '<font style="color:gray;">当前显示 {0} - {1}条记录&nbsp;&nbsp;共有 {2} 条记录</font>',
        emptyMsg    : '<font style="color:gray;">没有记录</font>'
    })
});

/**
 * 日志文件窗口
 */
var logFileWin = new Ext.Window({
    title   : '日志文件',
    width   : 800,
    height  : 600,
    maximized   : true,
    closable    : false,
    modal   : false,
    closeAction : 'hide',
    layout  : 'fit',
    items   : [logFileGrid],
    buttonAlign : 'center',
    buttons : [{
        text    : '关闭',
        handler : function(){
            logFileWin.hide();
        }
    },{
        text    : '刷新',
        handler : function(){
            logFileStore.load({
                params  : {
                    start   : 0,
                    limit   : 10
                }
            });
        }
    }]  
});
//下载文件时使用的form
var html2 = ['<div id="grid-export-form" class="x-hidden">',
'<form method="POST" name="GridExportForm">', 
'<input id = "fileName" name = "fileName" type="hidden"  />',
'</form>',
'</div>   '].join('');
Ext.DomHelper.append(document.body, {
    html : html2
});

/**
 * 下载文件触发的函数
 */
window.logFileDownload = function(fileName){
    document.GridExportForm.action = basepath + '/adminLogFileAction!downloadLogFile.json';
    document.getElementById("fileName").value = fileName;
    document.GridExportForm.submit();
}