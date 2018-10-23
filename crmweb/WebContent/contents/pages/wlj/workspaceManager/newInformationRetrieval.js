/**
*@description 知识库检索
*@author:luyy
*@since:2014-04-23
*@checkedby:
*/

imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
        '/contents/pages/common/Com.yucheng.bcrm.common.Annacommit.js',//附件信息
        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
        '/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldx.js' // 机构放大镜
        ]);

// 机构树加载条件
var condition = {
	searchType : 'SUBTREE' // 查询子机构
};

var detailAnna;  //详情面板中的附件列表部分

var detailView = !JsContext.checkGrant('retrieval_detail');

var url = basepath+'/workingplatformInfo.json';

var lookupTypes = ['CHECKTYPE'];
//var localLookup = {
//		'CHECKTYPE':[
//		           {key : '01',value : '全部'},
//		           {key : '02',value : '标题'}, 
//		           {key : '03',value : '内容'}
//		]
//};
var fields = [
            {name: 'CHECKTITLE', text : '检索范围',searchField: true,translateType : 'CHECKTYPE',gridField:false},
            {name: 'CHECKMSG', text : '检索信息',searchField: true,gridField:false},
  		    {name: 'MESSAGE_ID',hidden : true},
  		    {name: 'MESSAGE_TYPE_VALUE', text : '归属模块', searchField: true,editable:false,  
  		    	xtype : 'wcombotree',innerTree:'DATAKNOWLEDGETREE',showField:'text',hideField:'id',allowBlank:false,
  		    	viewFn : function(data){return '<b>'+data+'</b>';}}, 
  		    {name: 'PRODUCT_TYPE', text : '所属栏目名称', hidden:true}, 
	  		{name: 'MESSAGE_TYPE', text : '所属栏目编号', hidden:true},                                      
  		    {name: 'MESSAGE_TITLE',text:'文档标题', searchField: false},  
  		    {name: 'MESSAGE_SUMMARY', text:'文档摘要', resutlFloat:'right',xtype : 'textarea',searchField: false,resutlWidth:350},
  		    {name: 'PUBLISH_DATE', text : '发布时间',xtype:'datefield',format:'Y-m-d',editable:false,searchField: true,resutlWidth:70},
  		    {name: 'PUBLISH_DATE_END', text : '发布时间至',xtype:'datefield',format:'Y-m-d',editable:false,searchField: true,gridField:false},
  		    {name: 'PUBLISH_USER', text : '发布者',searchField: true,resutlWidth:100},
  		    {name: 'ORG_NAME', text : '发布机构',searchField: true,xtype:'orgchoose',hiddenName:'ORG_ID',searchType:'SUBTREE',allowBlank: false,resutlWidth:100},
  		    {name: 'TEST'}
  		    
  		];

var detailFormViewer = [{
	fields : ['MESSAGE_ID','MESSAGE_TYPE','MESSAGE_TITLE','PUBLISH_USER','PUBLISH_DATE','ORG_NAME'],
	fn : function(MESSAGE_ID,MESSAGE_TYPE,MESSAGE_TITLE,PUBLISH_USER,PUBLISH_DATE,ORG_NAME){
		MESSAGE_TYPE.readOnly = true;MESSAGE_TITLE.readOnly = true;PUBLISH_USER.readOnly = true;
		PUBLISH_DATE.readOnly = true;ORG_NAME.readOnly = true;
		
		MESSAGE_TYPE.cls = 'x-readOnly';MESSAGE_TITLE.cls = 'x-readOnly';PUBLISH_USER.cls = 'x-readOnly';
		PUBLISH_DATE.cls = 'x-readOnly';ORG_NAME.cls = 'x-readOnly';
		
		return [MESSAGE_ID,MESSAGE_TYPE,MESSAGE_TITLE,PUBLISH_USER,PUBLISH_DATE,ORG_NAME];
	}
},{
	columnCount : 0.95 ,
	fields : ['MESSAGE_SUMMARY'],
	fn : function(MESSAGE_SUMMARY){
		MESSAGE_SUMMARY.readOnly = true;
		MESSAGE_SUMMARY.cls = 'x-readOnly';
		return [MESSAGE_SUMMARY];
	}
},{
	columnCount:0.94,
	fields:['TEST'],
	fn:function(TEST){
		detailAnna = createAnnGrid(true,false,true,false);
		return [detailAnna];
	}
}];

var tbar = [{
	text : '收起',
	handler : function(){
		collapseSearchPanel();
	}
},{
	text : '展开',
	handler : function(){
		expandSearchPanel();
	}
}];

var treeLoaders = [{
	key : 'KNOWLEDGELOADER',
	url : basepath + '/workplatforminfosection!indexAll.json',
	parentAttr : 'parentSection',
	locateAttr : 'sectionId',
	rootValue : 'root',
	textField : 'sectionName',
	idProperties : 'sectionId'
},{
	key : 'DATASETMANAGERLOADER1',
	url : basepath + '/commsearch.json?condition=' + Ext.encode(condition),
	parentAttr : 'SUPERUNITID',
	locateAttr : 'UNITID',
	jsonRoot : 'json.data',
	rootValue : JsContext._orgId,
	textField : 'UNITNAME',
	idProperties : 'UNITID'
}];

var treeCfgs = [{
    			key : 'DATAKNOWLEDGETREE',
    			loaderKey : 'KNOWLEDGELOADER',
    			autoScroll:true,
    			rootCfg : {
    				expanded:true,
    				text:'全部栏目',
    				id:'root',
    				autoScroll:true,
    				children:[]
    			},
    			clickFn : function(node){}
},{
	key : 'DATASETMANAGERTREE2',
	loaderKey : 'DATASETMANAGERLOADER1',
	autoScroll : true,
	rootCfg : {
		expanded:true,
		id:JsContext._orgId,
		text:JsContext._unitname,
		autoScroll:true,
		children:[],
		UNITID:JsContext._orgId,
		UNITNAME:JsContext._unitname
	}
}];

/**详情面板滑入之前判断是否选择了数据，如果以选择，加载查询附件列表信息**/
var beforeviewshow = function(view){
	if(view == getDetailView()){
			if(getSelectedData() == false || getAllSelects().length > 1){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
		}else{//加载数据
			var	messageIdStr = getSelectedData().data.MESSAGE_ID;
		    uploadForm.relaId = messageIdStr;
            uploadForm.modinfo = 'infomation';
            var condi = {};
            condi['relationInfo'] = messageIdStr;
            condi['relationMod'] = 'infomation';
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
                    	detailAnna.store.loadData(anaExeArray.json.data);
                        detailAnna.getView().refresh();
                }
            });
		}		
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