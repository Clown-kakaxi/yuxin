/**
 * 渠道自动营销管理-自动推送历史
 */
    imports([
		        '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
		        '/contents/pages/common/Com.yucheng.crm.common.ImpExpNew.js',
		        '/contents/pages/common/Com.yucheng.bcrm.common.ProductManage.js'
		        ]);
    
    var url = basepath+'/ocrmFMmSysMsg.json';
    var needCondition = true;
    var needGrid = true;
    
    var createView = true;
    var editView = true;
    var detailView = !JsContext.checkGrant('msgModelPast_detail');
    var lookupTypes=[
                     'MODEL_TYPE',
                     'MODEL_SOURCE'
                     ]
    
	// 定义自动当前页行号
	var rownum = new Ext.grid.RowNumberer({
		header : 'No.',
		width : 28
	});
	
	var fields = [
				  {name:'ID',hidden:true},
				  {name:'CUST_ID',text:'客户编号',searchField: true,allowBlank:false},
				  {name:'CUST_NAME',text:'客户名称',searchField: true,allowBlank:false},
//				  {name:'PROD_NAME',text:'产品名称',hidden : false,searchField: true,allowBlank:false,xtype : 'wcombotree',innerTree:'DATAPRODUCTTREE',showField:'text',hideField:'CATL_CODE',allowBlank:false,
//		  		    	viewFn : function(data){return '<b>'+data+'</b>';}},
//		  		  {name:'PROD_ID',text:'产品类别id',dataType:'string',hidden : true,searchField:true,allowBlank:true},
				  {name: 'PROD_ID',dataType:'string',text:"产品编号",hidden:true},	
				  {name: 'PROD_NAME',dataType:'productChoose',hiddenName:'PROD_ID',singleSelect:true,
  		        	  text:"产品名称",searchField : true,prodState:(''),riskLevel:('')},
  		          {name:'MODEL_SOURCE',text:'推送内容来源',translateType:'MODEL_SOURCE',multiSelect:true,multiSeparator:',',searchField:false,allowBlank:true},
	              {name:'MODEL_TYPE',text:'推送渠道',viewFn:function(val){
		            	if(val=='1'){
		            		return "短信";
		            	}else if(val=='2'){
		            		return '邮件';
		            	}else if(val=='3'){
		            		return '微信';
		            	}else if(val=='1,2'||val=='2,1'){
		            		return '短信,邮件';
		            	}else if(val=='3,2'||val=='2,3'){
		            		return '邮件,微信';
		            	}else if(val=='1,3'||val=='3,1'){
		            		return '短信,微信';
		            	}else if(val=='1,2,3'||val=='3,2,1'||val=='2,1,3'||
		            			val=='2,3,1'||val=='1,3,2'||val=='3,1,2'){
		            		return '短信,邮件,微信';
		            	}
		            		
		            }},
	              {name:'MODEL_NAME',text:'营销模板',dataType:'string',searchField:false,allowBlank:true},
	              {name:'MESSAGE_REMARK',text:'营销内容',xtype:'textarea',searchField:false,allowBlank:true},
	              {name:'CRT_DATE',text:'发送时间',dataType:'date',searchField:false,allowBlank:true}
	              ];
	
	var treeLoaders = [{
		key : 'PRODUCTLOADER',
		url : basepath + '/productCatlTreeAction.json',
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
		clickFn:function(node){//单击事件
			/** 设置查询条件 */
			setSearchParams({
				PROD_ID : node.id
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
	},new Com.yucheng.crm.common.NewExpButton({
		hidden:JsContext.checkGrant('msgModelPast_export'),
        formPanel : 'searchCondition',
//        url : basepath+'/workingplatformInfo.json'
        url : basepath+'/ocrmFMmSysMsg.json'
    })];
	
	var detailFormViewer = [{
		columnCount : 1 ,
		fields : ['MESSAGE_REMARK'],
		fn : function(MESSAGE_REMARK){
			MESSAGE_REMARK.readOnly=true;
			return [MESSAGE_REMARK];
		}
	}];
	
	var beforeviewshow=function(view){
		if(view == getEditView()||view == getDetailView()){
			if(getSelectedData() == false){
				Ext.Msg.alert('提示','请选择一条数据');
				return false;
			}
		
		}
	}	
