/***
 * 产品信息展示：修改为根据配置方案动态展示
 * @since 2014-07-17
 */

	imports([
	    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js'
	]);

	var _custId;
	var _busiId;
	var viewId = "";//产品视图展示方案
	var cust_id = _custId =='null'?"":_custId ;//客户id
	var base_id = "";//客户群id
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
   alert(_busiId);
	/**
	 * 树形结构的loader对象配置
	 */
	var treeLoaders = [{
		key : 'PRODUCT_TYPE_LOADER',
		url : basepath + '/customerProductTree.json?cust_id='+cust_id+"&base_id="+base_id+"&viewType="+viewType+"&mgrid="+_busiId,
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
			var value = node.attributes.VIEW_DETAIL;
			if(value == null || value == '' || value == undefined){
				Ext.Msg.alert('提示', '该产品类别没有配置[产品视图展示方案]！');
				return false;
			}
				//首先处理字段展示
				getCloumnShow(value);
				//然后重新查询
				setSearchParams({
					planId: value,
					cust_id:cust_id,
					base_id:base_id,
					viewType:viewType
				});
		}
	}];

	
	var url = basepath+'/getViewInfo.json';
	var autoLoadGrid = false;
	var fields = [{name:'ID'}];//由于本字段必须
	
	var needCondition = false;
	
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
			storeInfo.load({
		    	params:{
		    		planId:viewId
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
	
	
