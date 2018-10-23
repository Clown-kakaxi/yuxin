/**
 * @description 营销指标
 * @author helin
 * @since 2014-07-01
 */

Ext.QuickTips.init();

//数据字典定义
var lookupTypes = [
	'TARGET_TYPE'     //营销指标类型
];

var url = basepath+'/marketTarget.json';
var comitUrl = basepath+'/marketTarget.json';

var fields = [
    {name: 'TARGET_CODE', text : '指标编号', searchField: true,allowBlank: false},
    {name: 'TARGET_NAME', text : '指标名称', searchField: true,allowBlank: false},
    {name: 'TARGET_TYPE',text:'指标类型',translateType : 'TARGET_TYPE', resutlWidth: 100,allowBlank: false}, 
	{name: 'TARGET_CYCLE',text:'指标周期',hidden:true,resutlWidth: 100},
    {name: 'UPDATE_USER_NAME',text:'最近维护人', resutlWidth: 100},
    {name: 'UPDATE_USER',text:'最近维护人ID',hidden:true, resutlWidth: 100},
    {name: 'UPDATE_DATE',text:'最近维护日期', resutlWidth: 100,xtype:'datefield',format:'Y-m-d'}
];

var createView = true;
var editView = true;
var detailView = true;

/**
 * 新增、修改、详情设计
 */
var createFormViewer = [{
	columnCount : 1,
	fields : ['TARGET_CODE','TARGET_NAME','TARGET_CYCLE','TARGET_TYPE','UPDATE_USER','UPDATE_DATE'],
	fn : function(TARGET_CODE,TARGET_NAME,TARGET_CYCLE,TARGET_TYPE,UPDATE_USER,UPDATE_DATE){
		UPDATE_USER.hidden=true;
		UPDATE_DATE.hidden=true;
		TARGET_TYPE.readOnly = true;
		TARGET_TYPE.cls = 'x-readOnly';
		TARGET_TYPE.value = '02';
		return [TARGET_CODE,TARGET_NAME,TARGET_CYCLE,TARGET_TYPE,UPDATE_USER,UPDATE_DATE];
	}
}];

//注：由于目前把update_user,update_date都添加至面板发现，
//修改时并不调用后台，这里update_date与update_user每次都要更新的故暂不添加至此
var editFormViewer = [{
	columnCount : 1,
	fields : ['TARGET_CODE','TARGET_NAME','TARGET_CYCLE','TARGET_TYPE','UPDATE_USER','UPDATE_DATE'],
	fn : function(TARGET_CODE,TARGET_NAME,TARGET_CYCLE,TARGET_TYPE,UPDATE_USER,UPDATE_DATE){
		TARGET_CODE.readOnly = true;
		TARGET_CODE.cls = "x-readOnly";
		
		UPDATE_USER.hidden = true;
		UPDATE_DATE.hidden = true;
		TARGET_TYPE.readOnly = true;
		TARGET_TYPE.cls = 'x-readOnly';
		return [TARGET_CODE,TARGET_NAME,TARGET_CYCLE,TARGET_TYPE,UPDATE_USER,UPDATE_DATE];
	}
}];

var detailFormViewer = [{
	columnCount : 1,
	fields : ['TARGET_CODE','TARGET_NAME','TARGET_CYCLE','TARGET_TYPE','UPDATE_USER_NAME','UPDATE_DATE'],
	fn : function(TARGET_CODE,TARGET_NAME,TARGET_CYCLE,TARGET_TYPE,UPDATE_USER_NAME,UPDATE_DATE){
		TARGET_CODE.readOnly = true;
		TARGET_CODE.cls = "x-readOnly";
		TARGET_NAME.readOnly = true;
		TARGET_NAME.cls = "x-readOnly";
		TARGET_CYCLE.readOnly = true;
		TARGET_CYCLE.cls = "x-readOnly";
		TARGET_TYPE.readOnly = true;
		TARGET_TYPE.cls = "x-readOnly";
		UPDATE_USER_NAME.readOnly = true;
		UPDATE_USER_NAME.cls = "x-readOnly";
		UPDATE_DATE.readOnly = true;
		UPDATE_DATE.cls = "x-readOnly";
		return [TARGET_CODE,TARGET_NAME,TARGET_CYCLE,TARGET_TYPE,UPDATE_USER_NAME,UPDATE_DATE];
	}
}];


/**
 * 自定义工具条上按钮
 * 注：批量选择未实现,目前只支持单条删除
 */
var tbar = [{
	/**
	 * 营销指标删除
	 */
	text : '删除',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			var selectRecords = getAllSelects();
			var ids = "'"+selectRecords[0].data.TARGET_CODE+"'";
			if(selectRecords[0].data.TARGET_TYPE == '01'){
				Ext.Msg.alert('提示','周期性指标不允许删除！');
				return false;
			}
			for(var i=1;i<selectRecords.length;i++){
				if(selectRecords[i].data.TARGET_TYPE == '01'){
					Ext.Msg.alert('提示','周期性指标不允许删除！');
					return false;
				}
				ids += ",'" + selectRecords[i].data.TARGET_CODE+"'";
			}
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return;
				}  
				Ext.Ajax.request({
					url: basepath + '/marketTarget!batchDestroy.json',
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
	                params: {
                        'idStr': ids
                    },
					success : function() {
                        Ext.Msg.alert('提示', '删除成功' );
						reloadCurrentData();
					},
					failure : function() {
						Ext.Msg.alert('提示', '删除失败' );
						reloadCurrentData();
					}
				});
			});
		}
	}
}];

/**
 * 结果域面板滑入前触发,系统提供listener事件方法
 * @param {} view
 * @return {Boolean}
 */
var beforeviewshow = function(view){
	if(view.baseType != 'createView'){
		if(!getSelectedData()){ //注：beforeviewshow事件不包含进入列表，因此可以此调用
			Ext.Msg.alert('提示','请选择一条数据进行操作！');
			return false;
		}
	}
};
