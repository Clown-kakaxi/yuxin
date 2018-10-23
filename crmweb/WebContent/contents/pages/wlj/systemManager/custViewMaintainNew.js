/**
 * @description 客户视图项设置
 * @author helin
 * @since 2014-06-26
 */
imports([
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
]);

Ext.QuickTips.init();

//本地数据字典定义
var localLookup = {
	'VIEW_TYPE' : [
		{key : '1',value : '对私'},
	    {key : '2',value : '对公'},
	    {key : '3',value : '客户群'},
	    {key : '4',value : '集团客户'},
	    {key : '5',value : '客户经理'}
	]
};

//下拉视图树
var treeLoaders = [{
	key : 'CUSTVIEWLOADER',
	url : basepath + '/parentidQuery.json',  //viewtype 不传表未后台接收的为null,这样可将所有的视图树查询出来,
	parentAttr : 'PARENTID',
	locateAttr : 'ID',
	jsonRoot:'json.data',
	rootValue : '0',
	textField : 'NAME',
	idProperties : 'ID'
}];
var treeCfgs = [{
	key : 'CUSTVIEWTREE',
	loaderKey : 'CUSTVIEWLOADER',
	autoScroll:true,
	rootVisible : false,//根结点不可见
	rootCfg : {
		expanded:true,
		id:'root',
		autoScroll:true,
		children:[]
	},
	clickFn : function(node){
	}
}];

var url = basepath+'/CustViewMaintainInfo-action.json';
var comitUrl = basepath+'/CustViewMaintainInfo-action.json';

var fields = [
    {name: 'ID',hidden : true},
    {name: 'NAME',text: '名称', searchField: true,allowBlank: false},
    {name: 'ADDR',text:'链接地址', resutlWidth:240},
    {name: 'PARENT_NAME',text:'父节点'},
    {name: 'PARENTID',text:'父节点',hidden:true,xtype: 'wcombotree',innerTree:'CUSTVIEWTREE',allowBlank:false,showField:'text',hideField:'ID',editable:false},
    {name: 'ORDERS',text:'顺序号', resutlWidth:100},
    {name: 'VIEWTYPE',text:'客户类型',translateType:'VIEW_TYPE',searchField: true,resutlWidth:100,allowBlank: false}
];

var createView = true;
var editView = true;
var detailView = true;

/**
 * 新增、修改、详情设计
 * @type 
 */
var formViewers = [{
	columnCount : 2,
	fields : ['ID','NAME','VIEWTYPE','PARENTID','ORDERS'],
	fn : function(ID,NAME,VIEWTYPE,PARENTID,ORDERS){
		PARENTID.hidden = false;
		return [ID,NAME,VIEWTYPE,PARENTID,ORDERS];
	}
},{
	columnCount : 1,
	fields : ['ADDR'],
	fn : function(ADDR){
		return [ADDR];
	}
}];

var detailFormViewer = [{
	columnCount : 2,
	fields : ['ID','NAME','VIEWTYPE','PARENTID','ORDERS'],
	fn : function(ID,NAME,VIEWTYPE,PARENTID,ORDERS){
		NAME.cls = 'x-readOnly';
		NAME.disabled = true;
		VIEWTYPE.cls = 'x-readOnly';
		VIEWTYPE.disabled = true;
		PARENTID.cls = 'x-readOnly';
		PARENTID.disabled = true;
		ORDERS.cls = 'x-readOnly';
		ORDERS.disabled = true;
		PARENTID.hidden = false;
		return [ID,NAME,VIEWTYPE,PARENTID,ORDERS];
	}
},{
	columnCount : 1,
	fields : ['ADDR'],
	fn : function(ADDR){
		ADDR.cls = 'x-readOnly';
		ADDR.disabled = true;
		return [ADDR];
	}
}];

var tbar =[{
	/**
	 * 视图项删除
	 */
	text : '删除',
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			var ids = '';
			var selectRecords = getAllSelects();
			for(var i=0;i<selectRecords.length;i++){
				if(i == 0){
					ids += selectRecords[i].data.ID;
				}else{
					ids += ',' + selectRecords[i].data.ID;
				}
			}
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return;
				}
				Ext.Ajax.request({
					url: basepath+'/CustViewMaintainInfo-action!destroy.json',
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