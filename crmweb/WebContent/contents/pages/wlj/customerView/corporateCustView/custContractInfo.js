/**
 * @description 对公客户签约信息
 * @author likai
 * @since 2014-07-25
 */
imports([
	'/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
    '/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js',//附件信息
    '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',//导出
	'/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldx.js' // 机构放大镜
]);

Ext.QuickTips.init();//提示信息

var _custId;
var url = basepath + '/ocrmFCiContractInfo.json?custId='+_custId; 
var needCondition = true;
var needGrid = true;

var createView = !JsContext.checkGrant('_contractInfoCreate');
var editView = !JsContext.checkGrant('_contractInfoEdit');
var detailView = !JsContext.checkGrant('_contractInfoDetail');

WLJUTIL.alwaysLockCurrentView = true;//由于在保存之后，还需要操作附件列表，所以本功能页面锁定悬浮面板滑出

// 定义自动当前页行号
var rownum = new Ext.grid.RowNumberer({
	header : 'No.',
	width : 28
});
 
var fields = [
 	{name: 'ID',hidden: true},
	{name: 'SIGN_NAME', text: '签约名称', dataType: 'string', searchField: true, allowBlank: false},
	{name: 'SIGN_ORG', text: '签约机构', searchType:'SUBTREE',xtype: 'orgchoose', hiddenName: 'ORG_ID',searchField: true, allowBlank: false},
	{name: 'SIGN_DATE', text: '签约日期', format:'Y-m-d',xtype:'datefield',dataType: 'date', searchField: true, allowBlank: false},
	{name: 'SIGN_CHANEL', text: '签约渠道', dataType: 'string', searchField: true, allowBlank: false},
	{name: 'SIGN_END_DATE',text: '签约到期日期', format:'Y-m-d',xtype:'datefield',dataType: 'date', searchField: false},
    {name: 'OGR_NAME',text: '机构名称', dataType: 'string', searchField: false},
    {name: 'ATTN',text: '经办人', dataType: 'string', searchField: false},
    {name: 'SIGN_STS',text: '签约状态', dataType: 'string', searchField: false}
];


//新增面板
var createFormViewer = [{
	fields : ['ID','SIGN_NAME','SIGN_ORG','SIGN_DATE','SIGN_CHANEL','SIGN_END_DATE','OGR_NAME','ATTN','SIGN_STS'],
	fn : function(ID,SIGN_NAME,SIGN_ORG,SIGN_DATE,SIGN_CHANEL,SIGN_END_DATE,OGR_NAME,ATTN,SIGN_STS){
		return [ID,SIGN_NAME,SIGN_ORG,SIGN_DATE,SIGN_CHANEL,SIGN_END_DATE,OGR_NAME,ATTN,SIGN_STS];
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
	fields : ['ID','SIGN_NAME','SIGN_ORG','SIGN_DATE','SIGN_CHANEL','SIGN_END_DATE','OGR_NAME','ATTN','SIGN_STS'],
	fn : function(ID,SIGN_NAME,SIGN_ORG,SIGN_DATE,SIGN_CHANEL,SIGN_END_DATE,OGR_NAME,ATTN,SIGN_STS){
		return [ID,SIGN_NAME,SIGN_ORG,SIGN_DATE,SIGN_CHANEL,SIGN_END_DATE,OGR_NAME,ATTN,SIGN_STS];
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
	fields : ['SIGN_NAME','SIGN_ORG','SIGN_DATE','SIGN_CHANEL','SIGN_END_DATE','OGR_NAME','ATTN','SIGN_STS'],
	fn : function(SIGN_NAME,SIGN_ORG,SIGN_DATE,SIGN_CHANEL,SIGN_END_DATE,OGR_NAME,ATTN,SIGN_STS){
		SIGN_NAME.disabled = true;
		SIGN_ORG.disabled = true;
		SIGN_DATE.disabled = true;
		SIGN_CHANEL.disabled = true;
		SIGN_END_DATE.disabled = true;
        OGR_NAME.disabled = true;
        ATTN.disabled = true;
		SIGN_STS.disabled = true;
		SIGN_NAME.cls = 'x-readOnly';
		SIGN_ORG.cls = 'x-readOnly';
		SIGN_DATE.cls = 'x-readOnly';
		SIGN_CHANEL.cls = 'x-readOnly';
		SIGN_END_DATE.cls = 'x-readOnly';
        OGR_NAME.cls = 'x-readOnly';
        ATTN.cls = 'x-readOnly';
		SIGN_STS.cls = 'x-readOnly';
		return [SIGN_NAME,SIGN_ORG,SIGN_DATE,SIGN_CHANEL,SIGN_END_DATE,OGR_NAME,ATTN,SIGN_STS];
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
		hidden :JsContext.checkGrant('_contractInfoDelete'),
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
				    	url : basepath + '/ocrmFCiContractInfo!batchDel.json',  
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
		hidden:JsContext.checkGrant('_contractInfoExcel'),
        formPanel : 'searchCondition',
        url :  basepath + '/ocrmFCiContractInfo.json?custId='+_custId
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
                uploadForm.modinfo = 'custContract';
                var condi = {};
                condi['relationInfo'] = noticeIdStr;
                condi['relationMod'] = 'custContract';
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
		uploadForm.modinfo = 'custContract';
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
            uploadForm.modinfo = 'custContract';
            var condi = {};
            condi['relationInfo'] = noticeId;
            condi['relationMod'] = 'custContract';
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
		if(data["SIGN_END_DATE"] != null && data["SIGN_END_DATE"] != ""){
			if(data["SIGN_DATE"]>data["SIGN_END_DATE"]){
				 Ext.MessageBox.alert('条件异常', '开始时间应该小于等于结束时间！');
                 return false;
			}
		}else{
			return;
		}
	}
};
