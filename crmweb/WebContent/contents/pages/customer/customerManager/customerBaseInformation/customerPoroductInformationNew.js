/***
 * 产品信息展示：修改为根据配置方案动态展示
 * @author luyy
 * @since 2014-05-15
 */
 	var sProductId='';
	var sCatlCode="";//所选类别
	var sView = "";//产品视图展示方案
	var cust_id ="";//客户id
	var base_id="";//客户群id
	var isOmainType = "0";//是否主办
	var colMArray = new Array();//列模型的数组对象
	var fieldArray = [];//store的rocode
	var mgrid = "";
	var oNodeLeaf=false;//第一个叶子节点
	try{
		mgrid = viewWindow.mrgIds;
	}catch(e){}
	 
	if(oCustInfo.omain_type==true)
	{
		isOmainType = "1";
	}
	var fnJudgeEntrance= function(){
		if(!oCustInfo.cust_id&&!mgrid){
			base_id=oCustInfo.groupId;
		}
		else if(oCustInfo.cust_id!=false)
			cust_id=oCustInfo.cust_id;
	};
	fnJudgeEntrance();
	
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
	
	//节点响应事件处理
	function fnToDecideType(oNode){
		sCatlCode=oNode.CATL_CODE;
		sView = oNode.VIEW_DETAIL;
		if(sView == null||sView == ''||sView == undefined){
			Ext.Msg.alert('提示', '产品类型:'+oNode.text+'没有配置产品视图展示方案！');
			return false;
		}
		fieldArray.length=0;
		colMArray.length=0;
	    colMArray.push(rownum);
	    colMArray.push(sm);
		//1.首先根据sView查询产品视图方案
	    storeInfo.load({
	    	params:{
	    		planId:sView
	    	},
	    	callback:function(){
	    		//组装cm信息	，组装fieldArray
	    		if(storeInfo.getCount() != 0){
	    			storeInfo.each(function(e){
	    				if(e.get('COLUMN_TYPE') == 3 ){//数值型
	    					fieldArray.push({name:e.get('COLUMN_NAME')});
	    					colMArray.push({header:e.get('COLUMN_OTH_NAME'),width: e.get('SHOW_WIDTH'),dataIndex:e.get('COLUMN_NAME'),menuDisabled: true,align : e.get('ALIGN_TYPE'),renderer:money('0,000.00')});
	    				}else if(e.get('COLUMN_TYPE') == 2 ){//字典映射
	    					fieldArray.push({name:e.get('COLUMN_NAME')+'_ORA'});
	    					colMArray.push({header:e.get('COLUMN_OTH_NAME'),width: e.get('SHOW_WIDTH'),dataIndex:e.get('COLUMN_NAME')+'_ORA',menuDisabled: true,align : e.get('ALIGN_TYPE')});
	    				}if(e.get('COLUMN_TYPE') == 1 ){//字符串
	    					fieldArray.push({name:e.get('COLUMN_NAME')});
	    					colMArray.push({header:e.get('COLUMN_OTH_NAME'),width: e.get('SHOW_WIDTH'),dataIndex:e.get('COLUMN_NAME'),menuDisabled: true,align : e.get('ALIGN_TYPE')});
	    				}
	    			});
	    			oPoroductGrid.setTitle('<span style="font-weight:normal">'+oNode.text+'</span>');
	    			 //重新绑定store和cm
	    			oPoroductGrid.reconfigure( 
							new Ext.data.Store({//动态数据存储
								restful:true,   
								autoLoad :true,
								proxy : new Ext.data.HttpProxy({
									url :basepath+'/getViewInfo.json?planId='+sView+'&cust_id='+cust_id+"&base_id="+base_id+"&mgrid="+mgrid
								}),
							reader : new Ext.data.JsonReader({
								root : 'json.data'
							}, fieldArray)
							}),
							new Ext.grid.ColumnModel(colMArray));
	    			
	    		}else{
	    			Ext.Msg.alert('提示', '产品类型:'+oNode.text+'产品视图展示方案配置错误！');
	    			return false;
	    		}
	    	}
	    });
	};
	
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
		/**指向父节点的属性列*/
		parentAttr : 'CATL_PARENT',
		/**节点定位属性列，也是父属性所指向的列*/
		locateAttr : 'CATL_CODE',
		/**虚拟根节点id*/
		rootValue : '0',
		/**用于展示节点名称的属性列*/
		textField : 'CATL_NAME',
		/**指定节点ID的属性列*/
		idProperties : 'CATL_CODE'
	});
	Ext.Ajax.request({
		url : basepath + '/customerProductTree.json?cust_id='+cust_id+"&base_id="+base_id+"&mgrid="+mgrid,
		method:'GET',
		success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			loader.nodeArray = nodeArra.JSON.data;
			var children = loader.loadAll();
			treeOfPoroduct.appendChild(children);
			treeOfPoroduct.expandAll();
			if(nodeArra.JSON.data.length != 0){
			fnRecursionFristTreeNode(loader.nodeArray[0]);
			fnToDecideType(oNodeLeaf);}
		}
	});
	
	var treeOfPoroduct = new Com.yucheng.bcrm.TreePanel({
				title:'产品目录',
				width:200,
				autoScroll:true,
				/**虚拟树形根节点*/
				root: new Ext.tree.AsyncTreeNode({
					id:'root',
					expanded:true,
					text:'银行产品',
					autoScroll:true,
					children:[]
				}),
				resloader:loader,
				clickFn:function(node){
			        var oClickNode=node.attributes;
			    	if (oClickNode.leaf){
			    		fnToDecideType(oClickNode);
			    	}
				},
				split:true
			});

	 var treeContainer = new Ext.Panel({
			frame:true,
			height:document.body.scrollHeight-59,
			layout:'fit',
			autoScroll:true,
				items: [treeOfPoroduct] });
	
	 	//复选框
		var sm = new Ext.grid.CheckboxSelectionModel();

		// 定义自动当前页行号
		var rownum = new Ext.grid.RowNumberer({
					header : 'No.',
					width : 28
				});
		var  cm = new Ext.grid.ColumnModel([rownum,sm]);
	 
	// 表格实例
		var oPoroductGrid = new Ext.grid.GridPanel({
			        title : '<span style="font-weight:normal">产品种类</span>',
					height:document.body.scrollHeight-59,
					frame : true,
					store : new Ext.data.ArrayStore({}), // 数据存储
					stripeRows : true, // 斑马线
					cm : cm, // 列模型
					sm : sm, // 复选框
					viewConfig : {
					},
					loadMask : {
						msg : '正在加载表格数据,请稍等...'
					}
				});
		
		
	// 布局模型
	var tree_panel = new Ext.Panel({
		renderTo : oCustInfo.view_source,
		title : '<span style="font-weight:normal">产品信息</span>',
		layout : 'column',
		border : false,
		width : document.body.clientWidth-180,
		items : [{	
			columnWidth : .25,
			border : false,
			items : [treeContainer]
		},{	
			columnWidth : .75,
			border : false,
			layout : 'fit',
			items : [oPoroductGrid]
		}]
});
	
	
	//取第一个叶子节点
	var fnRecursionFristTreeNode =function(aNodeArray){
		//判断节点的是否叶子节点
		if (!aNodeArray.leaf){
		//递归
			fnRecursionFristTreeNode(aNodeArray.children[0]);
		}
		else{
		//取出第一个叶子节点
			oNodeLeaf=aNodeArray;
		}
	};