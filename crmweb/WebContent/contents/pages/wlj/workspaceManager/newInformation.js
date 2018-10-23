/**
*@description 知识库发布页面
*@author:luyy
*@since:2014-04-23
*@checkedby:
*/
imports([
        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
        '/contents/pages/common/Com.yucheng.bcrm.common.OrgFieldx.js' // 机构放大镜
		        ]);
 
	 	/**变量定义部分**/
var createAnna;  //新增面板中的附件列表部分
var editAnna;    //修改面板中的附件列表部分
var detailAnna;  //详情面板中的附件列表部分

var parentSection = null;//在点击新增或修改栏目里的下拉树时，对其赋值选中树的节点ID，作为父节点ID
var sectionName = null;//选中节点名称
var sectionCategory = null;//父节点名称
var sectionId = null;//选中节点ID
var thisNode = null;//选中的节点

WLJUTIL.alwaysLockCurrentView = true;//由于在保存之后，还需要操作附件列表，所以本功能页面锁定悬浮面板滑出
var createView = !JsContext.checkGrant('information_create');
var editView = !JsContext.checkGrant('information_modify');
var detailView = !JsContext.checkGrant('information_detail');
var lookupTypes = ['SHARE_FLAG'];//共享范围

var url = basepath+'/workingplatformInfo.json';

var fields = [
  		    {name: 'MESSAGE_ID',hidden : true},
  		    {name: 'PRODUCT_TYPE', text : '所属栏目名称', hidden:true}, 
  		    {name: 'MESSAGE_TYPE_VALUE', text : '所属栏目', searchField: true, 
  		    	xtype : 'wcombotree',innerTree:'DATAKNOWLEDGETREE',showField:'text',hideField:'id',allowBlank:false}, 
  		    {name: 'MESSAGE_TYPE', text : '所属栏目编号', hidden:true, searchField: true},                                   
  		    {name: 'MESSAGE_TITLE',text:'文档名称',allowBlank:false,searchField: true},  
  		    {name: 'MESSAGE_SUMMARY', text:'文档描述',resutlWidth:350,xtype : 'textarea'},
  		    {name: 'PUBLISH_USER', text : '发布者',resutlFloat:'right',resutlWidth:120},
  		    {name: 'PUBLISH_DATE', text : '发布日期',resutlWidth:70,xtype:'datefield',searchField: false,format:'Y-m-d',dataType:'date'},
  		    {name: 'PUBLIC_TYPE',  text : '共享范围',searchField:false,translateType:'SHARE_FLAG',gridField:false,allowBlank:false},
  		    {name: 'ORG_NAME',  text : '发布机构',searchField:false,xtype:'orgchoose',hiddenName:'ORG_ID',searchType:'SUBTREE',gridField:false},
  		    {name: 'TEST',hidden:true}
  		];

var createFormViewer =[{
	fields : ['MESSAGE_ID','PRODUCT_TYPE','MESSAGE_TYPE_VALUE','MESSAGE_TITLE','PUBLISH_USER','PUBLISH_DATE','PUBLIC_TYPE'],
	fn : function(MESSAGE_ID,PRODUCT_TYPE,MESSAGE_TYPE_VALUE,MESSAGE_TITLE,PUBLISH_USER,PUBLISH_DATE,PUBLIC_TYPE){
		MESSAGE_TYPE_VALUE.value = '';
		PUBLISH_USER.hidden = true;
		PUBLISH_DATE.hidden = true;
//		PUBLISH_ORG.hidden = true;
		PUBLIC_TYPE.gridField = true;
		return [MESSAGE_ID,PRODUCT_TYPE,MESSAGE_TYPE_VALUE,MESSAGE_TITLE,PUBLISH_USER,PUBLISH_DATE,PUBLIC_TYPE];
	}
},{
	columnCount : 0.95 ,
	fields : ['MESSAGE_SUMMARY'],
	fn : function(MESSAGE_SUMMARY){
		return [MESSAGE_SUMMARY];
	}
},{
	columnCount:0.94,
	fields:['TEST'],
	fn:function(TEST){
		createAnna = createAnnGrid(false,true,false,'<font color="red">(保存信息后可操作附件列表)</font>');
		return [createAnna];
	}
}];

var editFormViewer = [{
	fields : ['MESSAGE_ID','MESSAGE_TYPE_VALUE','PRODUCT_TYPE','MESSAGE_TYPE','MESSAGE_TITLE','PUBLISH_USER','PUBLIC_TYPE'],
	fn : function(MESSAGE_ID,MESSAGE_TYPE_VALUE,PRODUCT_TYPE,MESSAGE_TYPE,MESSAGE_TITLE,PUBLISH_USER,PUBLIC_TYPE){
		PUBLISH_USER.hidden = true;
		PUBLIC_TYPE.gridField = true;
		return [MESSAGE_ID,MESSAGE_TYPE_VALUE,PRODUCT_TYPE,MESSAGE_TYPE,MESSAGE_TITLE,PUBLISH_USER,PUBLIC_TYPE];
	}
},{
	columnCount : 0.95 ,
	fields : ['MESSAGE_SUMMARY'],
	fn : function(MESSAGE_SUMMARY){
		return [MESSAGE_SUMMARY];
	}
},{
	columnCount:0.94,
	fields:['TEST'],
	fn:function(TEST){
		editAnna = createAnnGrid(false,true,false,false);
		return [editAnna];
	}
}];

var detailFormViewer = [{
	fields : ['MESSAGE_ID','MESSAGE_TYPE_VALUE','PRODUCT_TYPE','MESSAGE_TYPE','MESSAGE_TITLE','PUBLISH_USER','PUBLISH_DATE','PUBLIC_TYPE','ORG_NAME'],
	fn : function(MESSAGE_ID,MESSAGE_TYPE_VALUE,PRODUCT_TYPE,MESSAGE_TYPE,MESSAGE_TITLE,PUBLISH_USER,PUBLISH_DATE,PUBLIC_TYPE,ORG_NAME){
		PUBLISH_USER.hidden = false;
		PUBLIC_TYPE.gridField = true;
		ORG_NAME.gridField = true;
		
		MESSAGE_TYPE_VALUE.readOnly = true;PRODUCT_TYPE.readOnly = true;MESSAGE_TYPE.readOnly = true;
		MESSAGE_TITLE.readOnly = true;PUBLISH_USER.readOnly = true;PUBLISH_DATE.readOnly = true;PUBLIC_TYPE.readOnly = true;ORG_NAME.readOnly = true;
		
		MESSAGE_TYPE_VALUE.cls = 'x-readOnly';PRODUCT_TYPE.cls = 'x-readOnly';MESSAGE_TYPE.cls = 'x-readOnly';
		MESSAGE_TITLE.cls = 'x-readOnly';PUBLISH_USER.cls = 'x-readOnly';PUBLISH_DATE.cls = 'x-readOnly';
		PUBLIC_TYPE.cls = 'x-readOnly';ORG_NAME.cls = 'x-readOnly';
		return [MESSAGE_ID,MESSAGE_TYPE_VALUE,PRODUCT_TYPE,MESSAGE_TYPE,MESSAGE_TITLE,PUBLISH_USER,PUBLISH_DATE,PUBLIC_TYPE,ORG_NAME];
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

var treeLoaders = [{
	key : 'KNOWLEDGELOADER',
	url : basepath + '/workplatforminfosection!indexAll.json',
	parentAttr : 'parentSection',
	locateAttr : 'sectionId',
	rootValue : 'root',
	textField : 'sectionName',
	idProperties : 'sectionId'
}];
var treeCfgs = [{
	key : 'KNOWLEDGETREE',
	loaderKey : 'KNOWLEDGELOADER',
	autoScroll:true,
	tbar :[ {
		text : '新增栏目',
		hidden:JsContext.checkGrant('tree_create'),
		handler:function(){
		showCustomerViewByTitle('新增栏目');		
	}
	},'-', {
		text : '编辑',
		hidden:JsContext.checkGrant('tree_modify'),
		handler : function() {
		showCustomerViewByTitle('编辑');
	}
    	},'-',{
		text:'删除',
		hidden:JsContext.checkGrant('tree_delete'),
		handler:function(){
			if (sectionId == null) {
				Ext.MessageBox.alert('提示', '请先选择要删除的栏目!');
			}else if(sectionId == 'root'){
				Ext.MessageBox.alert('提示', '不能删除根节点!');
				return false;
			}else{
					Ext.Msg.confirm(
							'请确认',
							'<b>提示!:</b><span  style="color:red" >删除栏目将同时删除栏目下的主题,请慎重! </span> <br/>继续删除吗?',
							function(btn, text) {
								if (btn == 'yes') {
									Ext.Ajax.request({
										url : basepath + '/workplatforminfosection!batchDestroy.json',
										method : 'POST',
										params : {
										'sectionId' : sectionId
										},
										waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
										scope : this,
										success : function() {
								        	TreeManager.loaders.KNOWLEDGELOADER.deleteNode(thisNode); //删除目录树节点
											Ext.Msg.alert('提示','操作成功！');
											reloadCurrentData();
										},
										    failure : function() {
											Ext.Msg.alert('提示', '操作失败');
											reloadCurrentData();
										}
								        });
									}
								});
					}
    	}
		}],
	rootCfg : {
		expanded:true,
		text:'全部目录',
		id:'root',
		autoScroll:true,
		children:[]
	},
	clickFn : function(node){
		/** 设置查询条件 */
		setSearchParams({
			MESSAGE_TYPE : node.id
		});
		/**给查询域设置值**/
		getConditionField('MESSAGE_TYPE').setValue(node.id);
		/**设置栏目相关参数 **/
		if(node.id!='root'){
			sectionCategory = node.parentNode.text;
			parentSection = node.parentNode.id;
		}
		sectionName = node.text;
		sectionId = node.id;
		thisNode = node;
	}
},
{
	key : 'DATAKNOWLEDGETREE',
	loaderKey : 'KNOWLEDGELOADER',
	autoScroll:true,
	rootCfg : {
		expanded:true,
		text:'全部栏目',
		id:'root',
		autoScroll:true,
		children:[]
	},clickFn : function(node){
			parentSection = node.id;
			/**修改页面 或新增页面 设置隐藏域的值**/
			if(getCurrentView() == getEditView()||getCurrentView() == getCreateView()){
				getCurrentView().setValues({
					 MESSAGE_TYPE: node.id,
					 PRODUCT_TYPE: node.text
				});
			}
	}
}];

var edgeVies = {
		left : {
			width : 200,
			layout : 'form',
			title:'资讯文档树',
			items : [TreeManager.createTree('KNOWLEDGETREE')]
		}
	};

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
},{
	text : '删除',
	hidden:JsContext.checkGrant('information_delete'),
	handler : function(){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据！');
			return false;
		}else{
			var publishUser=getSelectedData().data.PUBLISH_USER;
			var messageId=getSelectedData().data.MESSAGE_ID;
			if(publishUser != __userName){
				Ext.Msg.alert('提示','发布者才能删除！');
				return false;
			}
			Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
				return false;
				} 
			    Ext.Ajax.request({
                    url: basepath+'/workingplatformInfo!batchDestroy.json?messageId='+messageId,                                
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
},{
	text : '初始化索引',
	hidden:JsContext.checkGrant('information_index'),
	handler : function(){
		Ext.MessageBox.confirm('提示','确定初始化索引吗?',function(buttonId){
		if(buttonId.toLowerCase() == "no"){
		return false;
		} 
		Ext.MessageBox.wait('正在初始化索引','请稍后...');
	    Ext.Ajax.request({
            url: basepath+'/workingplatformInfo!createIndexFiles.json', 
            success : function(){
                Ext.Msg.alert('提示', '初始化成功');
                reloadCurrentData();
            },
            failure : function(){
                Ext.Msg.alert('提示', '初始化失败');
                reloadCurrentData();
            }
        });
      });
    }
},new Com.yucheng.crm.common.NewExpButton({
    formPanel : 'searchCondition',
    hidden:JsContext.checkGrant('information_export'),
    url : basepath+'/workingplatformInfo.json'
})];

var customerView = [{
	title : '新增栏目',
	type : 'form',
	hideTitle : true,
	autoLoadSeleted : true,
	groups : [{
		columnCount : 1 ,
		fields : [{
			name : 'SECTION_NAME',
			xtype : 'textfield',
			text : '栏目名称'
		},{
			name : 'SECTION_CATEGORY',
			text : '父节点名称',
			xtype : 'wcombotree',
			innerTree:'DATAKNOWLEDGETREE',
			showField:'text',
			hideField:'ID',
			editable:true	
		}],
		fn : function(SECTION_NAME,SECTION_CATEGORY){
			return [SECTION_NAME,SECTION_CATEGORY];
		}
	}],
	formButtons : [{
		text : '保存',
		fn : function(formPanel,basicForm){
			var sectionName = this.contentPanel.getForm().getValues().SECTION_NAME;
			var sectionCategory = this.contentPanel.getForm().getValues().SECTION_CATEGORY;
			Ext.Ajax.request({
				url : basepath + '/workplatforminfosection.json',
				method : 'POST',
				params : {
					'parentSection' :parentSection,
					'sectionName' : sectionName,
					'sectionCategory' :sectionCategory
				},
				success : function() {
					 Ext.Ajax.request({
				         url: basepath +'/workplatforminfosection!getPid.json',
					         success:function(response){
					        	 /** 添加新增的节点**/
					        	 var node = {};
					        	 node.sectionId = Ext.util.JSON.decode(response.responseText).pid;
					        	 node.sectionName = sectionName;
					        	 node.parentSection = parentSection;
					        	 TreeManager.loaders.KNOWLEDGELOADER.addNode(node);
						 	}
						 });
					 Ext.Msg.alert('提示','操作成功！');
					 reloadCurrentData();
					 hideCurrentView();//隐藏当前展示信息VIEW对象
				}
			});
		}
	}]
},{
	title : '编辑',
	type : 'form',
	hideTitle : true,
	autoLoadSeleted : true,
	groups : [{
		columnCount : 1 ,
		fields : [{
			name : 'SECTION_NAME',
			xtype : 'textfield',
			text : '栏目名称'
		},{
			name : 'SECTION_CATEGORY',
			text : '父节点名称',
			xtype : 'wcombotree',
			innerTree:'DATAKNOWLEDGETREE',
			showField:'text',
			hideField:'ID',
			editable:false	
		}],
		fn : function(SECTION_NAME,SECTION_CATEGORY){
			return [SECTION_NAME,SECTION_CATEGORY];
		}
	}],
	formButtons : [{
		text : '保存',
		fn : function(formPanel,basicForm){
			var sectionName = this.contentPanel.getForm().getValues().SECTION_NAME;
			var sectionCategory = this.contentPanel.getForm().getValues().SECTION_CATEGORY;
			Ext.Ajax.request({
				url : basepath + '/workplatforminfosection!update_new.json',
				method : 'POST',
				params : {
					'sectionName' : sectionName,
					'parentSection':parentSection,
					'sectionId' :sectionId,
					'sectionCategory' :sectionCategory
				},
				success : function() {
					/**修改节点**/
					 var node = {};
					 node.id = sectionId;
		        	 node.text = sectionName;
		        	 node.parentSection = parentSection;
		        	 node.sectionCategory = sectionCategory;
		        	 TreeManager.loaders.KNOWLEDGELOADER.editNode(node);
					
					Ext.Msg.alert('提示','操作成功！');
					reloadCurrentData();
				}
			});
		}
	}]
}];

/**修改和详情面板滑入之前判断是否选择了数据，如果以选择，加载查询附件列表信息**/
var beforeviewshow = function(view){
	if(view == getEditView()||view == getDetailView()){
			if(getSelectedData() == false || getAllSelects().length > 1){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}else{//加载数据
				if(view == getEditView()){
					var publishUser=getSelectedData().data.PUBLISH_USER;
					if(publishUser != __userName){
						Ext.Msg.alert('提示','发布者才能修改！');
						return false;
					}
				}
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
                        if(view == getEditView()){
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
	if(view == getCreateView()){
		uploadForm.relaId = '';
		uploadForm.modinfo = 'infomation';
		createAnna.tbar.setDisplayed(false);
	}
	/** 栏目选择控制 ：1.必须选择 2.不能为根节点**/
	if(view._defaultTitle=='编辑'){
		if(sectionId == null){
			Ext.Msg.alert('提示','请选择要编辑的栏目！');
			return false;
		}else if(sectionId == 'root'){
			Ext.Msg.alert('提示','不能编辑跟节点！');
			return false;
		}
	}
};	

/**栏目编辑面板滑入之后，设置栏目信息值**/
var viewshow = function(view){
	if(view._defaultTitle == '新增栏目'){
		view.contentPanel.getForm().findField('SECTION_CATEGORY').setValue('');
	}
	if(view._defaultTitle == '编辑'){
		view.contentPanel.getForm().findField('SECTION_NAME').setValue(sectionName);
		view.contentPanel.getForm().findField('SECTION_CATEGORY').setValue(sectionCategory);
	}
};

/**数据提交之后：锁定结果列表，如果是新增面板，返回新增的信息id，显示附件列表的tbar**/
var afertcommit = function(){
	lockGrid();//锁定结果列表
	if(getCurrentView() == getCreateView()){
		Ext.Ajax.request({
			url : basepath+'/session-info!getPid.json',
			method : 'GET',
			success : function(a,b,v) {
			    var noticeIdStr = Ext.decode(a.responseText).pid;
			    getCurrentView().setValues({
			    	MESSAGE_ID:noticeIdStr
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