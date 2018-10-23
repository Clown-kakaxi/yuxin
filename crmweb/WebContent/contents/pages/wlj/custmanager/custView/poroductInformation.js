/***
 * 产品信息展示：修改为根据配置方案动态展示
 * @author luyy
 * @since 2014-07-17
 */

	imports([
	    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	]);
	
	var _custId;
	var _busiId;
	var flag=false;//产品信息数据显示标识，false为只显示已激活状态的数据，true为全部显示
	var value="";
	var viewId = "";//产品视图展示方案
	var cust_id = _custId =='null'?"":_custId ;//客户id
	var base_id = _busiId=='null'?"":_busiId;//客户群id
	var viewType = '';	//视图类型，0表示客户视图，1表示客户群视图，2表示集团视图，3表示客户经理视图
	var tempViewType = JsContext._resId.split('$-$');
	if(tempViewType.length > 2 ){
		viewType = tempViewType[1];
	}

	//数据字典定义
	var lookupTypes = [
		{
			TYPE : 'CATL_CODE',//产品类别
			url : '/product-list!searchPlan.json',
			key : 'KEY',
			value : 'VALUE',
			root : 'data'
		}
	];
	/**
	 * 树形结构的loader对象配置
	 */
	var treeLoaders = [{
		key : 'PRODUCT_TYPE_LOADER',
		url : basepath + '/customerProductTree.json?cust_id='+cust_id+"&base_id="+base_id+"&viewType="+viewType,
		parentAttr : 'CATL_PARENT',
		locateAttr : 'CATL_CODE',
		jsonRoot:'JSON.data',
		rootValue : '0',
		textField : 'CATL_NAME',
		idProperties : 'CATL_CODE'
	}];
	/**
	 * 树形面板对象预配置
	 */
	var treeCfgs = [{
		key : 'PRODUCT_TYPE_TREE',
		loaderKey : 'PRODUCT_TYPE_LOADER',
		autoScroll:true,
		rootCfg : {
			expanded:true,
			id:'0',
			text:'银行产品树',
			autoScroll:true,
			children:[]
		},
		clickFn : function(node){
			if(!node.isLeaf()){
				return ;
			}
			 value = node.attributes.VIEW_DETAIL;
			if(value == null || value == '' || value == undefined){
				Ext.Msg.alert('提示', '该产品类别没有配置[产品视图展示方案]！');
				return false;
			}
				flag=false;
				//首先处理字段展示
				getCloumnShow(value);
				//然后重新查询
				setSearchParams({
					planId: value,
					cust_id:cust_id,
					base_id:base_id,
					viewType:viewType,
					flag:flag
				});
				getConditionField('planId').setValue(value);
				getConditionField('cust_id').setValue(cust_id);
				getConditionField('base_id').setValue(base_id);
				getConditionField('viewType').setValue(viewType);
				getConditionField('flag').setValue(flag);
		}
	}];

	
	var url = basepath+'/getViewInfo.json';
	var autoLoadGrid = false;
	var fields = [
	{name:'ID'},
	{name: 'planId',text:'备用planId',searchField: true,hidden:true},
	{name: 'cust_id',text:'备用custId2',searchField: true,hidden:true},
	{name: 'base_id',text:'备用base_id2',searchField: true,hidden:true},
	{name: 'viewType',text:'备用viewType2',searchField: true,hidden:true},
	{name: 'flag',text:'备用flag2',searchField: true,hidden:true}
	];//由于本字段必须
	
	var needCondition = false;
	var tbar=[{
		  text:'已激活',
		  hidden:JsContext.checkGrant('activeProductViewInfo'),
		  handler:function(){//查询该产品与客户的已经激活状态的产品数据
		  	flag=false;
		  	if(value==""){
		  		Ext.Msg.alert('提示', '产品信息展示错误，当前产品无任何产品信息！');
    			return false;
		  	}
	  		getCloumnShow(value);
			//然后重新查询
			setSearchParams({
				planId: value,
				cust_id:cust_id,
				base_id:base_id,
				viewType:viewType,
				flag:flag
			});
			getConditionField('planId').setValue(value);
			getConditionField('cust_id').setValue(cust_id);
			getConditionField('base_id').setValue(base_id);
			getConditionField('viewType').setValue(viewType);
			getConditionField('flag').setValue(flag);
		  	
		  }
	    },{
	    	text:'全部显示',
	    	hidden:JsContext.checkGrant('allProductViewInfo'),
	    	handler:function(){//查询该产品与客户的所以产品数据
	    		flag=true;
	    		//然后重新查询
	    		if(value==""){
			  		Ext.Msg.alert('提示', '产品信息展示错误，当前产品无任何产品信息！');
	    			return false;
			  	}
			setSearchParams({
				planId: value,
				cust_id:cust_id,
				base_id:base_id,
				viewType:viewType,
				flag:flag
			});
			
			getConditionField('planId').setValue(value);
			getConditionField('cust_id').setValue(cust_id);
			getConditionField('base_id').setValue(base_id);
			getConditionField('viewType').setValue(viewType);
			getConditionField('flag').setValue(flag);
	    		
	    	}
	    }]
	//边缘面板配置
	var edgeVies = {
		left : {
			width : 200,
			layout : 'form',
			items : [TreeManager.createTree('PRODUCT_TYPE_TREE')]
		}
	};
	
	//储存表模型数据的store
	var storeInfo = new Ext.data.Store({
		restful : true,
		proxy : new Ext.data.HttpProxy(
				{
					url : basepath + '/getViewInfo!getData.json',
					method:'get'
				}),
				reader : new Ext.data.JsonReader( {
		        	root:'data'
				}, [{name:'TABLE_OTH_NAME'},
				    {name:'COLUMN_NAME'},
				    {name:'COLUMN_OTH_NAME'},
				    {name:'COLUMN_TYPE'},
				    {name:'ALIGN_TYPE'},
				    {name:'DIC_NAME'},
				    {name:'SHOW_WIDTH'}])
	});
	
	//字段显示方法
	function getCloumnShow(viewNow){
		if(viewNow != viewId){//进行了修改，重新绘制列表
			viewId = viewNow;
			//1.删除现有字段显示
			var fields = getFieldsCopy();
			for(var i=0;i<fields.length;i++){
				removeMetaField(fields[i].name+"");
			}
			//2.重新加载字段信息
			storeInfo.baseParams={
				planId :viewId,
				flag:flag
			};
			storeInfo.load({
		    	params:{
		    		planId:viewId,
		    		flag:flag
		    	},
		    	callback:function(){
		    		if(storeInfo.getCount() != 0){//展示新字段
		    			storeInfo.each(function(e){
		    				if(e.get('COLUMN_NAME') == 'CATL_CODE'){//产品分类需要特殊处理
		    					addMetaField({name:'CATL_CODE',text:'产品分类',translateType : 'CATL_CODE',resutlWidth:e.get("SHOW_WIDTH")});
		    				}else{
		    					if(e.get('COLUMN_TYPE') == 3 ){//数值型
			    					addMetaField({name:e.get('COLUMN_NAME'),text:e.get('COLUMN_OTH_NAME'),dataType : 'number',resutlWidth:e.get("SHOW_WIDTH")});
			    				}else if(e.get('COLUMN_TYPE') == 2 ){//字典映射
			    					addMetaField({name:e.get('COLUMN_NAME')+'_ORA',text:e.get('COLUMN_OTH_NAME'),dataType : 'string',resutlWidth:e.get("SHOW_WIDTH")});
			    				}if(e.get('COLUMN_TYPE') == 1 ){//字符串
			    					addMetaField({name:e.get('COLUMN_NAME'),text:e.get('COLUMN_OTH_NAME'),dataType : 'string',resutlWidth:e.get("SHOW_WIDTH")});
			    				}
		    				}
		    			});
		    		}else{
		    			Ext.Msg.alert('提示', '产品视图展示方案配置错误,没有展示属性列信息！');
		    			return false;
		    		}
		    	}
		    	});
		}
	};
	
	
