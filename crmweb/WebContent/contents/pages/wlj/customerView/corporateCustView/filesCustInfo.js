/**
 * 对公客户档案信息
 * 2014/04/29
 */
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
        '/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js'//附件信息
        ]);
var needGrid = true;
var createView = !JsContext.checkGrant('corFilesCust_create');//是否启用新增面板
var editView = !JsContext.checkGrant('corFilesCust_modify');//是否启用修改面板
var detailView = !JsContext.checkGrant('corFilesCust_detail');//是否启用详情面板
var formViewers = true;
WLJUTIL.alwaysLockCurrentView = true;//由于在保存之后，还需要操作附件列表，所以本功能页面锁定悬浮面板滑出
var lookupTypes = ['FILE_TYPE'];//档案类型

var custId =_custId;
var url = basepath+'/filesCustInfo.json?custId='+custId;

var fields = [
  		     {name: 'FILE_TYPE',text:'档案类型',searchField:true,translateType:'FILE_TYPE',hidden:false,allowBlank:false},  
  		     {name: 'ARCHIVE_DATE',text:'档案归档时间',allowBlank:false,searchField:false,xtype:'datefield',format:'Y-m-d',dataType:'date',hidden:false},  
  		     {name: 'FILE_NAME',text:'档案名称',allowBlank:false,searchField:true},  
  		     {name: 'DOCU_ADDR',text:'档案地址',hidden:true},  
  		     {name: 'CREATE_DATE',text:'档案创建时间',xtype:'datefield',format:'Y-m-d',dataType:'date'},  
  		     {name: 'CREATE_USER',text:'档案录入人',xtype:'textfield'}, 
  		     {name: 'CUST_ID',hidden:true}, 
  		     {name: 'LOAN_CONTRACT',hidden:true},  
  		     {name: 'TEST',hidden:true},  
  		     {name: 'ID',hidden:true} 
  		     ];

/*******************新增面板********************/
var createFormViewer = [{
	fields : ['ID','FILE_TYPE','FILE_NAME','ARCHIVE_DATE'],
	fn : function(ID,FILE_TYPE,FILE_NAME,ARCHIVE_DATE){
		return [ID,FILE_TYPE,FILE_NAME,ARCHIVE_DATE];
	}
},{/**附件信息**/
	columnCount:0.945,
	fields:['TEST'],
	fn:function(TEST){
		createAnna = createAnnGrid(false,true,false,'<font color="red">(保存信息后可操作附件列表)</font>');
		return [createAnna];
	}
}];

/*******************修改面板********************/
var editFormViewer = [{
	fields : ['ID','FILE_TYPE','FILE_NAME','ARCHIVE_DATE'],
	fn : function(ID,FILE_TYPE,FILE_NAME,ARCHIVE_DATE){
		return [ID,FILE_TYPE,FILE_NAME,ARCHIVE_DATE];
	}
},{/**附件信息**/
	columnCount:0.945,
	fields:['TEST'],
	fn:function(TEST){
		editAnna = createAnnGrid(false,true,false,false);
		return [editAnna];
	}
}];
/*******************详情面板********************/
var detailFormViewer = [{
	columnCount : 2 ,
	fields : ['FILE_TYPE','FILE_NAME','ARCHIVE_DATE','CREATE_DATE','CREATE_USER'],
	fn : function(FILE_TYPE,FILE_NAME,ARCHIVE_DATE,CREATE_DATE,CREATE_USER){
		FILE_TYPE.readOnly = true;FILE_NAME.readOnly = true;ARCHIVE_DATE.readOnly = true;CREATE_DATE.readOnly = true;CREATE_USER.readOnly = true;
		FILE_TYPE.cls = 'x-readOnly';FILE_NAME.cls = 'x-readOnly';ARCHIVE_DATE.cls = 'x-readOnly';CREATE_DATE.cls = 'x-readOnly';CREATE_USER.cls = 'x-readOnly';
		return [FILE_TYPE,FILE_NAME,ARCHIVE_DATE,CREATE_DATE,CREATE_USER];
	}
},{/**附件信息**/
	columnCount:0.945,
	fields:['TEST'],
	fn:function(TEST){
		detailAnna = createAnnGrid(true,false,true,false);
		return [detailAnna];
	}
}];

var tbar = [{
	text : '删除',
	hidden:JsContext.checkGrant('corFilesCust_delete'),
	handler : function(){
	if(getSelectedData() == false){
		Ext.Msg.alert('提示','请选择一条数据！');
		return false;
	}else{
		var selectRecords = getAllSelects();
		var ID = '';
		for(var i=0;i<selectRecords.length;i++){
			if(i == 0){
				ID = selectRecords[i].data.ID;
			}else{
				ID +=","+ selectRecords[i].data.ID;
			}
		}
		Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
			if(buttonId.toLowerCase() == "no"){
			return false;
			} 
		    Ext.Ajax.request({
                url: basepath+'/filesCustInfo!batchDestroy.json?idStr='+ID,                                
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
}},new Com.yucheng.crm.common.NewExpButton({
    formPanel : 'searchCondition',
    hidden:JsContext.checkGrant('corFilesCust_export'),
    url : basepath+'/filesCustInfo.json?custId='+custId
})];

/**view 滑入前控制**/
var beforeviewshow = function(view){
	/*修改查询面板滑入时，做相应判断，并加载相关附件信息*/
	if(view.baseType=='editView'||view.baseType == 'detailView'){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}else{//加载数据
				var	noticeIdStr=getSelectedData().data.ID;
			    uploadForm.relaId = noticeIdStr;
                uploadForm.modinfo = 'customerFiles';
                var condi = {};
                condi['relationInfo'] = noticeIdStr;
                condi['relationMod'] = 'customerFiles';
                Ext.Ajax.request({
                    url:basepath+'/queryanna.json',
                    method : 'GET',
                    params : {
                        "condition":Ext.encode(condi)
                    },
                    failure : function(a,b,c){
                        Ext.MessageBox.alert('查询异常', '查询失败！');
                    },
                    success : function(response){
                        var anaExeArray = Ext.util.JSON.decode(response.responseText);
                        if(view.baseType=='editView'){
                        	editAnna.store.loadData(anaExeArray.json.data);
	                        editAnna.getView().refresh();
                        }else{
                        	detailAnna.store.loadData(anaExeArray.json.data);
	                        detailAnna.getView().refresh();
                        }
                    }
                });
			}		
	}
	/**新增面板滑入时清空附件列表*/
	if(view.baseType=='createView'){
		uploadForm.relaId = '';
		uploadForm.modinfo = 'customerFiles';
		createAnna.store.removeAll();
		createAnna.tbar.setDisplayed(false);
	}
	/*附件信息列表滑入时加载相关数据附件信息*/
	if(view._defaultTitle=='附件信息'){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			var noticeId=getSelectedData().data.ID;
		    uploadForm.relaId = noticeId;
            uploadForm.modinfo = 'customerFiles';
            var condi = {};
            condi['relationInfo'] = noticeId;
            condi['relationMod'] = 'customerFiles';
            view.store.load({
            	 params : {
               "condition":Ext.encode(condi)
                 }
            });
	    }
	}
};	

/********新增面板保存数据后激活附件面板***********/
var afertcommit = function(){
	lockGrid();//锁定结果列表
	if(getCurrentView().baseType=='createView'){
		Ext.Ajax.request({
			url : basepath+'/session-info!getPid.json',
			method : 'GET',
			success : function(a,b,v) {//返回id值，显示tbar
			    var noticeIdStr = Ext.decode(a.responseText).pid;
			    getCurrentView().setValues({
			    	ID:noticeIdStr
			    });
			    uploadForm.relaId = noticeIdStr;
			    createAnna.tbar.setDisplayed(true);
			}
		});
	}
};
var formCfgs = {
		formButtons : [{
			text : '关闭',
			fn : function(formPanel){
				 hideCurrentView();
			}
		}]
};