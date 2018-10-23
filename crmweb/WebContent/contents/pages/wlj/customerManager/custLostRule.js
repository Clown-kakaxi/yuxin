/**
 * 大额流失预警提醒参数设置
 */
//初始化提示框
Ext.QuickTips.init();
var url= basepath +'/custLostRule.json';

var comitUrl= basepath +'/custLostRule!save.json';

var needCondition = false;

var lookupTypes = [{
	TYPE : 'REMIND_ROLES',
	url : '/custLostRule!custLostRule.json',
	key : 'KEY',
	value : 'VALUE',
	root : 'data'
}]

var fields = [{name:'ID',text:'ID',gridField:false},
              {name:'REMIND_ROLES',translateType:'REMIND_ROLES',text:'提醒角色',multiSelect:true,resutlWidth:200,allowBlank:false},
              {name:'AMOUNT',text:'阈值(万元)',dataType:'number',allowBlank:false},
              {name:'CREATE_NAME',text:'设置人'}
              ]

var createView = true;
var editView = true;

var tbar=[{
	text:'删除',
	handler:function(){
		var records =getAllSelects();// 得到被选择的行的数组
		var selectLength = getAllSelects().length;// 得到行数组的长度
		if (selectLength < 1) {
			Ext.Msg.alert('提示信息', '请至少选择一条记录！');
		} else {
			var serviceId;
			var idStr = '';
			for ( var i = 0; i < selectLength; i++) {
				selectRe = records[i];
				serviceId = selectRe.data.ID;// 获得选中记录的id
				idStr += serviceId;
				if (i != selectLength - 1)
					idStr += ',';
			};
			if (idStr != '') {
				Ext.MessageBox.confirm('系统提示信息','确认删除操作吗？',
				function(buttonobj) {
					if (buttonobj == 'yes'){
					Ext.Ajax.request({
						url : basepath+ '/custLostRule!batchDestroy.json',
						params : {
							idStr : idStr
						},
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						method : 'GET',
						scope : this,
						success : function() {
							Ext.Msg.alert('提示信息', '操作成功！');
							reloadCurrentData();
						}
					});}
			 }, this);
			}
		}
	
	}
}];

var createFormViewer =[{
	fields : ['REMIND_ROLES','AMOUNT'],
	fn : function(REMIND_ROLES,AMOUNT){
		return [REMIND_ROLES,AMOUNT];
	}
}];

var editFormViewer =[{
	fields : ['REMIND_ROLES','AMOUNT','ID'],
	fn : function(REMIND_ROLES,AMOUNT,ID){
		ID.hidden = true;
		return [REMIND_ROLES,AMOUNT,ID];
	}
}];

var beforeviewshow = function(view){
	if(view._defaultTitle=='修改'){
		if(view == getEditView()){
			if(getSelectedData()==false || getAllSelects().length > 1){
				Ext.Msg.alert('提示','请选择一条数据!');
				return false;
			}
		}
	}
};