/**
 * 营销短信模板处理
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'//产品放大镜
		        ]);
    
    var url = basepath + '/ocrmFMmSysType.json';
    var comitUrl = basepath + '/ocrmFMmSysType!saveData.json';
    var needCondition = true;
    var needGrid = true;
    
    var createView = !JsContext.checkGrant('msgModel_create');
    var editView = !JsContext.checkGrant('msgModel_modify');
    var detailView = !JsContext.checkGrant('msgModel_detail');
	var lookupTypes=[
	                 'MODEL_TYPE',
	                 'MODEL_STATE'
	                 ];
	var nodeId='';
	var nodeText='';
    
	var treeLoaders = [{
		key : 'PRODUCTLOADER',
		url : basepath + '/perProductCatlTreeAction.json',
		parentAttr : 'CATL_PARENT',
		locateAttr : 'CATL_CODE',
		rootValue : '0',
		textField : 'CATL_NAME',
		idProperties : 'CATL_CODE',
		jsonRoot:'json.data'
	}];
	
	var treeCfgs = [{
		key : 'PRODUCTTREE',
		loaderKey : 'PRODUCTLOADER',
		autoScroll:true,
		rootCfg : {
			expanded:true,
			text:'富邦华一银行产品',
			id:'root',
			autoScroll:true,
			children:[]
		},
		listeners:{
		  beforeclick: function(node, e ){
		  	if(!node.isLeaf()){
		  		return false;
		  	}else{
		  		nodeId=node.id;
		  		nodeText=node.text;
		  	}
		  }
		},
		clickFn:function(node){//单击事件
			/** 设置查询条件 */
			setSearchParams({
				catlCode : node.id
			});
	 	}
	},{
		key : 'DATAPRODUCTTREE',
		loaderKey : 'PRODUCTLOADER',
		autoScroll:true,
		rootCfg : {
			expanded:true,
			text:'全部产品',
			id:'root',
			autoScroll:true,
			children:[]
		},listeners:{
		  beforeclick: function(node, e ){
		  	if(!node.isLeaf()){
		  		return false;
		  	}else{
		  	}
		  }
		},clickFn : function(node){
				parentSection = node.id;
				/**修改页面 或新增页面 设置隐藏域的值**/
				if(getCurrentView() == getEditView()||getCurrentView() == getCreateView()){
					getCurrentView().setValues({
						 CATL_NAME: node.text,
						 CATL_CODE: node.id
					});
				}
		}
	}];


	// 复选框
	var sm = new Ext.grid.CheckboxSelectionModel();
   
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	var fields = [
				  {name:'ID',hidden:true},
//				  {name:'CATL_NAME',text:'产品名称',hidden : false,searchField: true,allowBlank:false,xtype : 'wcombotree',innerTree:'DATAPRODUCTTREE',showField:'text',hideField:'id',allowBlank:false,
//		  		    	viewFn : function(data){return '<b>'+data+'</b>';}},
//	              {name:'CATL_CODE',text:'产品类别id',dataType:'string',hidden : true,searchField:true,allowBlank:true},
	              {name:'MODEL_NAME',text:'模板名称',searchField: true,allowBlank:false,maxLength:200},
	              {name:'MODEL_INFO',text:'模板内容',xtype:'textarea',searchField: false,allowBlank:false,maxLength:500},
	              {name:'IS_ENABLE',text:'是否启用',translateType:'MODEL_STATE',searchField: false,allowBlank:false},
	              {name:'MODEL_TYPE',text:'适合的渠道',translateType:'MODEL_TYPE',multiSelect:true,multiSeparator:',',searchField: true,allowBlank:false},
	              {name:'CATL_CODE',text:'所属产品ID',searchField: false,hidden:true},
	              //{name:'CATL_NAME',text:'所属产品',searchField: false,dataType:'productChoose',hiddenName:'CATL_CODE',singleSelect:true},
	              {name:'CATL_NAME',text:'所属产品',searchField: false,xtype : 'wcombotree',innerTree:'DATAPRODUCTTREE',showField:'text',hideField:'UNITID',editable:false},
	              {name:'CREAT_USER',text:'创建人ID',dataType:'string',searchField: false,hidden:true},
	              {name:'CREAT_USER_NAME',text:'创建人',dataType:'string',searchField: false},
	              {name:'UPDATA_USER',text:'最近维护人ID',dataType:'string',searchField: false,hidden:true},
	              {name:'UPDATA_USER_NAME',text:'最近维护人',dataType:'string',searchField: false},
	              {name:'UPDATA_DATE',text:'最近维护时间',dataType:'date',searchField: false}
	              ];
	
	var edgeVies = {
			left : {
				width : 200,
				layout : 'form',
				autoScroll:true,
				title:'产品树',
				items : [TreeManager.createTree('PRODUCTTREE')]
			}
		};

	// 定义列模型
	
	
	
	
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
		hidden:JsContext.checkGrant('msgModel_delete'),
		handler : function(){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据！');
				return false;
			}else{
				var json = {'id' : []};
//				var modelId=getSelectedData().data.ID;
//				json.id.push(modelId);
				var selectRecords = getAllSelects();
				var id = '';
				for(var i=0;i<selectRecords.length;i++){
					if(i == 0){
						id = selectRecords[i].data.ID;
					}else{
						id +=","+ selectRecords[i].data.ID;
					}
				}

				Ext.MessageBox.confirm('提示','确定删除吗?',function(buttonId){
					if(buttonId.toLowerCase() == "no"){
					return false;
					} 
				    Ext.Ajax.request({
				    	url : basepath + '/ocrmFMmSysType!batchDel.json',  
				    	params : {
								//ids : Ext.encode(json)
				    			ids:id
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
	}
	,new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('msgModel_export'),
        formPanel : 'searchCondition',
        url :  basepath + '/ocrmFMmSysType.json'
    })
	];
	
	
	var createFormViewer =[{
		columnCount:1,
		fields : ['MODEL_INFO'],
		fn:function(MODEL_INFO){
			var panel = new Ext.Panel({html:'<div style="line-height:150%"> &ensp;&ensp;&ensp;&ensp;短信内容填写说明:模板中的客户名称等信息请使用约定符号代替!<p/>&ensp;&ensp;&ensp;&ensp;符号说明如下：<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;客户名称：@custName <p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品编号：@productId<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品名称：@prodName<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品发布日期：@prodStartDate<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品截止日期：@prodEndDate<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品利率：@rate<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品费率：@castRate<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;客户经理：@manger<p/>'+
				'&ensp;&ensp;&ensp;&ensp;例如：<p/>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;'+
				'@custName先生/女士你好，我行将于@prodStartDate发售新产品@prodName，请关注。联系客户经理：@manger。</div>'});
			return [panel];
		}
		
	},{
		fields : ['MODEL_NAME','CATL_NAME','MODEL_TYPE','IS_ENABLE'],
		fn : function(MODEL_NAME,CATL_NAME,MODEL_TYPE,IS_ENABLE){
			return [MODEL_NAME,CATL_NAME,MODEL_TYPE,IS_ENABLE];
		}
	},{
		columnCount : 0.95 ,
		fields : ['MODEL_INFO'],
		fn : function(MODEL_INFO){
			return [MODEL_INFO];
		}
	}];
	
	var editFormViewer = [{
		columnCount:1,
		fields : ['MODEL_INFO'],
		fn:function(MODEL_INFO){
			var panel = new Ext.Panel({html:'<div style="line-height:150%"> &ensp;&ensp;&ensp;&ensp;短信内容填写说明:模板中的客户名称等信息请使用约定符号代替!<p/>&ensp;&ensp;&ensp;&ensp;符号说明如下：<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;客户名称：@custName <p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品编号：@productId<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品名称：@prodName<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品发布日期：@prodStartDate<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品截止日期：@prodEndDate<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品利率：@rate<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;产品费率：@castRate<p/>'+
				'&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;客户经理：@manger<p/>'+
				'&ensp;&ensp;&ensp;&ensp;例如：<p/>&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;'+
				'@custName先生/女士你好，我行将于@prodStartDate发售新产品@prodName，请关注。联系客户经理：@manger。</div>'});
			return [panel];
		}
		
	},
	{
		fields : ['ID','MODEL_NAME','CATL_CODE','CATL_NAME','MODEL_TYPE','IS_ENABLE'],
		fn : function(ID,MODEL_NAME,CATL_CODE,CATL_NAME,MODEL_TYPE,IS_ENABLE){
			return [ID,MODEL_NAME,CATL_CODE,CATL_NAME,MODEL_TYPE,IS_ENABLE];
		}
	},{
		columnCount : 0.95 ,
		fields : ['MODEL_INFO'],
		fn : function(MODEL_INFO){
			return [MODEL_INFO];
		}
	}];
	
	var detailFormViewer = [{
		fields : ['MODEL_NAME','MODEL_TYPE','IS_ENABLE','CREAT_USER_NAME','UPDATA_USER_NAME','UPDATA_DATE'],
		fn : function(MODEL_NAME,MODEL_TYPE,IS_ENABLE,CREAT_USER_NAME,UPDATA_USER_NAME,UPDATA_DATE){
			MODEL_NAME.readOnly=true;
			MODEL_NAME.cls='x-readOnly';
			MODEL_TYPE.readOnly=true;
			MODEL_TYPE.cls='x-readOnly';
			IS_ENABLE.readOnly=true;
			IS_ENABLE.cls='x-readOnly';
			CREAT_USER_NAME.readOnly=true;
			CREAT_USER_NAME.cls='x-readOnly';
			UPDATA_USER_NAME.readOnly=true;
			UPDATA_USER_NAME.cls='x-readOnly';
			UPDATA_DATE.readOnly=true;
			UPDATA_DATE.cls='x-readOnly';
			return [MODEL_NAME,MODEL_TYPE,IS_ENABLE,CREAT_USER_NAME,UPDATA_USER_NAME,UPDATA_DATE];
		}
	},{
		columnCount : 0.95 ,
		fields : ['MODEL_INFO'],
		fn : function(MODEL_INFO){
			MODEL_INFO.readOnly=true;
			MODEL_INFO.cls='x-readOnly';
			return [MODEL_INFO];
		}
	}];
	
	var beforeviewshow=function(view){
		if(view == getEditView()||view == getDetailView()){
			if(getSelectedData() == false || getAllSelects().length > 1){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}
		
		}
	}
	var viewshow=function(view){
		if(view==getCreateView()){
			view.contentPanel.getForm().findField('CATL_NAME').setValue(nodeText);
			view.contentPanel.getForm().findField('CATL_CODE').setValue(nodeId);
		}
	}
