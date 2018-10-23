/**
 * @description 客户股票信息
 * @author likai
 * @since 2014-07-23
 */

imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
	'/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldx.js', // 机构放大镜
    '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js'//导出
]);
 
Ext.QuickTips.init();//提示信息
var _custId;
var url = basepath + '/ocrmFCiOrgPrjInfo.json?custId='+_custId;
var needCondition = true;
var needGrid = true;

var createView = !JsContext.checkGrant('_projectInfoCreate');
var editView = !JsContext.checkGrant('_projectInfoEdit');
var detailView = !JsContext.checkGrant('_projectInfoDetail');

WLJUTIL.alwaysLockCurrentView = true;//由于在保存之后，还需要操作附件列表，所以本功能页面锁定悬浮面板滑出

// 复选框
var sm = new Ext.grid.CheckboxSelectionModel();
   
// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});
 
var fields = [
 	{name: 'ID',hidden: true},
	{name: 'PRJ_NAME', text: '项目名称', dataType: 'string', searchField: true, allowBlank: false},
	{name: 'USER_ID', text: '创建人', dataType: 'string', searchField: false, hidden: true},
	{name: 'USER_NAME', text: '创建人名', dataType: 'string', searchField: false},
	{name: 'CREAT_DATE',text: '创建时间', dataType: 'date', searchField: false},
    {name: 'ORG_ID',text: '创建机构', dataType: 'string', searchField: false, hidden: true},
    {name: 'ORG_NAME',text: '创建机构名称', xtype: 'orgchoose', hiddenName: 'ORG_ID', searchField: false}
];

//新增面板
var createFormViewer =[{
	fields : ['ID','PRJ_NAME','USER_ID','USER_NAME','CREAT_DATE','ORG_ID','ORG_NAME'],
		fn : function(ID,PRJ_NAME,USER_ID,USER_NAME,CREAT_DATE,ORG_ID,ORG_NAME){
			return [ID,PRJ_NAME,USER_ID,USER_NAME,CREAT_DATE,ORG_ID,ORG_NAME];
		}
	},{/**附件信息**/
	columnCount:1,
	fields:['TEST'],
	fn:function(TEST){
		createAnna = createAnnGrid(false,true,false,'<font color="red">(保存信息后可操作附件列表)</font>');
		return [createAnna];
	}
}];

//修改面板
var editFormViewer = [{
	fields : ['ID','PRJ_NAME','USER_ID','USER_NAME','CREAT_DATE','ORG_ID','ORG_NAME'],
		fn : function(ID,PRJ_NAME,USER_ID,USER_NAME,CREAT_DATE,ORG_ID,ORG_NAME){
			return [ID,PRJ_NAME,USER_ID,USER_NAME,CREAT_DATE,ORG_ID,ORG_NAME];
		}
	},{/**附件信息**/
	columnCount:1,
	fields:['TEST'],
	fn:function(TEST){
		editAnna = createAnnGrid(false,true,false,false);
		return [editAnna];
		}
}];

//详情面板
var detailFormViewer = [{
	columnCount:1.5,
	fields : ['PRJ_NAME'],
		fn : function(PRJ_NAME){
			PRJ_NAME.disabled = true;
			PRJ_NAME.cls = 'x-readOnly';
			return [PRJ_NAME];
		}
	},{/**附件信息**/
	columnCount:1,
	fields:['TEST'],
	fn:function(TEST){
		detailAnna = createAnnGrid(true,false,true,false);
		return [detailAnna];
		}
}];

//用户扩展工具栏按钮
var tbar = [
	{
		text : '删除',
		hidden : JsContext.checkGrant('_projectInfoDelete'),
		handler : function(){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据！');
				return false;
			}else{
				Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
					return false;
					} 
					var selectRecords = getAllSelects();
					var id = '';
					for(var i=0;i<selectRecords.length;i++){
						if(i == 0){
							id = selectRecords[i].data.ID;
						}else{
							id +=","+ selectRecords[i].data.ID;
						}
					}
				    Ext.Ajax.request({
				    	url : basepath + '/ocrmFCiOrgPrjInfo!batchDel.json',  
				    	params : {
								id : id
							},
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
	},
	//导出按钮
	new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('_projectInfoExcel'),
        formPanel : 'searchCondition',
        url :  basepath + '/ocrmFCiOrgPrjInfo.json?custId='+_custId
    })
];

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
                uploadForm.modinfo = 'custProject';
                var condi = {};
                condi['relationInfo'] = noticeIdStr;
                condi['relationMod'] = 'custProject';
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
		uploadForm.modinfo = 'custProject';
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
            uploadForm.modinfo = 'custProject';
            var condi = {};
            condi['relationInfo'] = noticeId;
            condi['relationMod'] = 'custProject';
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

/**验证输入条件是否满足**/
var beforesetsearchparams = function(data){
	for(var key in data){
		if(data[key] instanceof Date){
			data[key] = data[key].format('Y-m-d');
		}
	}
};

