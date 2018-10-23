/**
 *@description 新增产品监控页面
 *@author: luyy
 *@since: 2014-06-30
 */	

	imports([
	         '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
			]);
	var createView = false;
	var editView = false;
	var detailView = false;
	
	var id = '';
	var catl = '';
	var url = basepath+'/productNewCatl.json';
	var lookupTypes = ['PROD_STATE'];

	var fields = [
		          {name: 'PRODUCT_ID',text:'产品编号',allowBlank:false,searchField: true}, 
		          {name: 'PROD_NAME',text:'产品名称',allowBlank:false,searchField: true}, 
		          {name: 'PROD_START_DATE', text : '产品发布日期',xtype:'datefield',format:'Y-m-d',editable : false},
		          {name: 'PROD_END_DATE', text : '产品截止日期',xtype:'datefield',format:'Y-m-d',editable : false},
		          {name: 'RATE', text : '利率',hidden:false}, 
		          {name: 'COST_RATE', text : '费率',  hidden:false}, 
		          {name: 'LIMIT_TIME', text : '期限',readOnly : true},
		          {name: 'PROD_STATE', text : '产品状态', resutlWidth: 80,translateType : 'PROD_STATE', allowBlank: false}];
	
	var treeLoaders = [{
		key : 'PRODUCTLOADER',
		url : basepath + '/productCatlTreeAction.json',
		parentAttr : 'CATL_PARENT',
		locateAttr : 'CATL_CODE',
		jsonRoot : 'json.data',
		rootValue : '0',
		textField : 'CATL_NAME',
		idProperties : 'CATL_CODE'
		}];
		
		//面板左边的配置信息		
		var treeCfgs = [{
			key : 'PRODUCTTREE',
			loaderKey : 'PRODUCTLOADER',
			autoScroll:true,
			rootCfg : {
				expanded:true,
				text:'银行产品树',
				id:'0',
				autoScroll:true,
				children:[]
			},
			clickFn : function(node){
				if(!node.isLeaf()){
					Ext.Msg.alert('提示','只能选择叶子节点');
					return false;
				}else{
					catl = node.attributes.CATL_CODE;
				}
			}
		}];
		
	var customerView = [{
		title:'关联类别',
		xtype:'panel',
		items:[TreeManager.createTree('PRODUCTTREE')],
		buttonAlign:'center',
		buttons:[{
			text:'确定',
			handler:function(){
				if(catl == ''){
					Ext.Msg.alert('提示','请选择产品类别');
					return false;
				}
				 Ext.Ajax.request({
	                    url:basepath+'/productNewCatl!setCatl.json',
	                    params : {
	                        "id":id,
	                        "catl":catl
	                    },
	                    failure : function(a,b,c){
	                        Ext.MessageBox.alert('提示', '关联失败！');
	                    },
	                    success : function(response){
	                    	 Ext.MessageBox.alert('提示', '关联成功！');
	                    	 reloadCurrentData();
	                    }
	                });
			}
		}]
	}];
	
	var beforeviewshow = function(view){
		if(getSelectedData() == false){
			Ext.Msg.alert('提示','请选择一条数据');
			return false;
		}else{
			id = getSelectedData().data.PRODUCT_ID;
		}
	};
	
	var beforeviewhide = function(){
		id = '';
		catl = '';
	};