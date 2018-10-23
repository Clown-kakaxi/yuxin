/**
 * @description 贡献度参数设置页面
 * @author luyy
 * @since 2014-07-14
 */

var url = basepath+'/contribution.json';
var comitUrl = basepath+'/contribution!save.json';

var createView = true;
var editView = true;
var detailView = true;

var lookupTypes = ['PARAM_CODE'];

var fields = [
	{name:'PARAM_ID',hidden:true},
  	{name:'PARAM_CODE',text:'参数名称',translateType:'PARAM_CODE',searchField:true,allowBlank:false},
  	{name:'PARAM_VALUE',text:'参数取值(%)',dataType:'number',allowBlank:false},
  	{name:'USER_ID',hidden:true},
  	{name:'USER_NAME',text:'设置人',dataType:'string'},
  	{name:'PARAM_DATE',text:'修改日期',dataType:'date'}
];

var tbar  = [{
	text:'删除',
	handler:function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择数据！');
			return false;
		}else{
			var ID = '';
			for (var i=0;i<getAllSelects().length;i++){
				ID += getAllSelects()[i].data.PARAM_ID;
				ID += ",";
			}
			ID = ID.substring(0, ID.length-1);
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/contribution!batchDel.json?idStr='+ID,                                
                    success : function(){
                        Ext.Msg.alert('提示', '删除成功');
                        reloadCurrentData();
                    },
                    failure : function(){
                        Ext.Msg.alert('提示', '删除失败');
                        reloadCurrentData();
                    }
                });
			});			
		}
	
	}
}];

var createFormViewer =[{
	fields : ['PARAM_CODE','PARAM_VALUE'],
	fn : function(PARAM_CODE,ARAM_VALUE){
		return [PARAM_CODE,ARAM_VALUE];
	}
}];

var editFormViewer =[{
	fields : ['PARAM_ID','PARAM_CODE','PARAM_VALUE'],
	fn : function(PARAM_ID,PARAM_CODE,ARAM_VALUE){
		PARAM_CODE.readOnly = true;
		PARAM_CODE.cls = 'x-readOnly';
		return [PARAM_CODE,ARAM_VALUE,PARAM_ID];
	}
}];

var detailFormViewer =[{
	fields : ['PARAM_CODE','PARAM_VALUE','USER_NAME','PARAM_DATE'],
	fn : function(PARAM_CODE,ARAM_VALUE,USER_NAME,PARAM_DATE){
		PARAM_CODE.readOnly = true;
		ARAM_VALUE.readOnly = true;
		USER_NAME.readOnly = true;
		PARAM_DATE.readOnly = true;
		PARAM_CODE.cls = 'x-readOnly';
		ARAM_VALUE.cls = 'x-readOnly';
		USER_NAME.cls = 'x-readOnly';
		PARAM_DATE.cls = 'x-readOnly';
		return [PARAM_CODE,ARAM_VALUE,USER_NAME,PARAM_DATE];
	}
}];


var beforeviewshow = function(view){
	if(view == getEditView()||view == getDetailView()){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}
	}
};

var beforecommit = function(data,url){
	Ext.Ajax.request({
		url : basepath + '/contribution!ifExit.json',
		params : {
			'code':data.PARAM_CODE
		},
		success : function(response) {
			var info =  response.responseText;
			if(info == 'yes'){
				Ext.Msg.alert('提示', '该参数已设置，请勿重复设置');
				getCurrentView().setValues({
					PARAM_CODE : ''
				});
				return false;
			}
		},
		failure : function(){
            Ext.Msg.alert('提示', '判断参数是否存在失败');
        }
	});
};
