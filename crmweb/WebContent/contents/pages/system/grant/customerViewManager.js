Ext.onReady(function() {
	
	/**
	 * 重写Ext.tree.TreeNodeUI.toggleCheck方法，使其不再触发TreeNode的checkchange事件！
	 * 仅限本页面使用。 
	 */
	Ext.override(Ext.tree.TreeNodeUI,{
		toggleCheck : function(value){
        	var cb = this.checkbox;
        	if(cb){
        		cb.checked = (value === undefined ? !cb.checked : value);
        	}
        	this.checkbox.defaultChecked = value;
        	this.node.attributes.checked = value;
    	}	
	});
	/**
	 * 根据菜单ID判断是否在数组arr中存在
	 */
	function includeControll(arr,id){
		debugger;
		for(var i=0;i<arr.length;i++){
			if(arr[i].VIEW_ID==id){
				return true;
			}
		}
		return false;
	};
	var record ='';//选择的角色编码
	//删除数组
	var delStr = [];
	//新增数组
	var addStr = [];
	var treeLoader='';
	var index = [];//控制点授权数组
	var listenNode = true;
	var sUserid = '';
	var roleCodeGloble='';
	function saveSet(){
		if(sUserid == undefined || sUserid == "" || sUserid == null){//未选择角色编码时，提示必选信息
			Ext.Msg.alert('提示','请选择角色！');
			return false ;
		}
		var optionCode = '';//所选全部功能按钮编码
		Ext.Ajax.request({//执行保存设置
			url :basepath + '/userviewrelation.json?addStr='+addStr+'&delStr='+delStr+'&user_id='+sUserid,
			method:'POST',
			success:function(response){
			Ext.Ajax.request( {
				url : basepath
				+ '/queryCustViewAuthorize!queryAuthorizeData.json?role_id='
				+ sUserid,
				method : 'GET',
				success : function(response) {
				treeLoader = Ext.util.JSON.decode(response.responseText).JSON.data;//获取选择角色的菜单项
				for(var i=0;i<treeOfPoroduct.root.childNodes.length;i++){//清空原角色选择情况
					treeOfPoroduct.root.childNodes[i].fireEvent('checkchange',treeOfPoroduct.root.childNodes[i],false,undefined);
				}
				for(var i=0;i<treeLoader.length;i++){
					var tn = treeOfPoroduct.root.findChild('id',treeLoader[i].VIEW_ID,true);
					if(tn!=undefined && tn.childNodes.length == 0){
						tn.fireEvent('checkchange',tn,true);
					}
				}
				addStr = [];
				delStr = [];
				Ext.Msg.alert('提示','保存成功！');
			}
			});
		},
		failure:function(){
			Ext.Msg.alert('提示','操作失败！');
		}
		});
	}
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader( {
			
		/** 指向父节点的属性列 */
		parentAttr : 'PARENTID',
		/** 节点定位属性列，也是父属性所指向的列 */
		locateAttr : 'ID',
		/** 虚拟根节点id */
		rootValue : '0',
		/** 用于展示节点名称的属性列 */
		textField : 'NAME',
		/** 指定节点ID的属性列 */
		idProperties : 'ID',
		/** 节点点击事件句柄 */
		clickFn : function(node) {
	}
	});
	Ext.Ajax.request( {
		url : basepath + '/queryCustViewAuthorize.json',
		method : 'GET',
		success : function(response) {
		var nodeArra = Ext.util.JSON.decode(response.responseText);
		loader.nodeArray = nodeArra.JSON.data;
		var children = loader.loadAll();
		treeOfPoroduct.appendChild(children);
					treeOfPoroduct.expandAll();
	}
	});
	
	var treeOfPoroduct = new Com.yucheng.bcrm.TreePanel({
		title : '客户视图',
		layout:'fit',
		region:'center',
		autoScroll : true,
		rootVisible : false,
		split : true,
		checkBox : true,
		/** 虚拟树形根节点 */
		root : new Ext.tree.AsyncTreeNode( {
			id : 'root',
			expanded : true,
			text : '客户视图',
			autoScroll : true,
			children : []
		}),
		resloader : loader,
		listeners : {
			'expandnode':function(node){
				if(!this.loadMaskWorking){
					return;
				}
				this.expandedCount++;
				if(this.expandedCount >= this.resloader.nodeArray.length){
					lm.hide();
					this.loadMaskWorking = false;
					return;
				}
	},
			'click':function(node){},
			'checkchange' : function(node, checked, source) {
					if(sUserid!=''){
						if(treeLoader!==''){
							if(checked){
								delStr.remove(node.id);
								if(addStr.indexOf(node.id)<0 && !includeControll(treeLoader, node.id)){
									addStr.push(node.id);
								}
							}else{
								addStr.remove(node.id);
								if(delStr.indexOf(node.id)<0 && includeControll(treeLoader, node.id)){
									delStr.push(node.id);
								}
							}
							if(source==undefined){									//操作节点状态，并调用父节点和子节点事件
								node.getUI().checkbox.indeterminate=false;
								node.getUI().toggleCheck(checked);
				        		if(node.childNodes){
				        			Ext.each(node.childNodes,function(cn){
				        				cn.fireEvent('checkchange',cn,checked,'1');//若存在子节点，则传递参数以触发相应checkChange监听事件
				        			});
				        		}
				        		if(node.parentNode && node.parentNode !== this.root){
				        			node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');//若存在父节点，则传递参数以触发相应监听事件
				        		}
				        	}else if(source == '1'){								//操作节点状态，并调用子节点事件
				        		node.getUI().checkbox.indeterminate=false;
				        		node.getUI().toggleCheck(checked);
				        		if(node.childNodes){
				        			Ext.each(node.childNodes,function(cn){
				        				cn.fireEvent('checkchange',cn,checked,'1');//若存在子节点，则传递参数以触发相应checkChange监听事件
				        			});
				        		}
				        	}else if(source == '2'){								//操作节点状态，并调用父节点事件
				        		if(node.childNodes && node.childNodes.length > 0){
				        			var checkcount = 0;
				        			var indeterminate = false;
				        			for(var i=0; i<node.childNodes.length;i++){
				        				if(node.childNodes[i].getUI().checkbox.indeterminate){
				        					indeterminate = true;
				        					break;
				        				}
				        				if(node.childNodes[i].getUI().checkbox.checked){
				        					checkcount ++;
				        				}
				        			}
				        			if(!indeterminate && checkcount==0){
				        				node.getUI().checkbox.indeterminate = false;
				        				node.getUI().toggleCheck(false);
				        			}else if(indeterminate || checkcount < node.childNodes.length){
				        				node.getUI().checkbox.indeterminate = true;
				        				delStr.remove(node.id);
				        				if(!includeControll(treeLoader, node.id) && addStr.indexOf(node.id) < 0){
				        					addStr.push(node.id);
				        				}
				        			}else if(checkcount == node.childNodes.length){
				        				node.getUI().checkbox.indeterminate = false;
				        				node.getUI().toggleCheck(true);
				        			}
				        		}
				        		if(node.parentNode && node.parentNode != this.root){//若存在父节点，则传递参数以触发相应监听事件
				        			node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');
				        		}
				        	}
			        	}else{
			        		Ext.Msg.alert('提示','请选择授权角色');
			        		return;
			        	}
					
					}else{
						
						Ext.Msg.alert('提示','请选择授权角色');
						node.getUI().toggleCheck(false);
						return;
					}
				}
			}
			});

	var leftTreeForShow = new Ext.tree.TreePanel({// 左边的产品树
		title : '银行角色树',
		rootVisible : false,
		width : 300,
		region:'west',
		layout:'fit',
		autoScroll : true,
		animate : true,
		split : true,
		root : new Ext.tree.AsyncTreeNode( {
			// id:'A2 ',
			id : "null",
			text : '银行角色树',
			hidden : true,
			expanded : false
		}),
		loader : new Ext.tree.TreeLoader( {
			baseAttrs : {},
			dataUrl : basepath + '/sysRole-kind-tree.json',
			requestMethod : 'GET',
			callback:function(a,b,c,d){
			}
		}),
		listeners : {
		'click' : function(node) {
		sUserid = node.id;
		roleCodeGloble=sUserid;
		if(node != undefined){
			Ext.Ajax.request( {
				url : basepath
				+ '/queryCustViewAuthorize!queryAuthorizeData.json?role_id='
				+ sUserid,
				method : 'GET',
				success : function(response) {
				
					treeLoader = Ext.util.JSON.decode(response.responseText).JSON.data;//获取选择角色的菜单项
					for(var i=0;i<treeOfPoroduct.root.childNodes.length;i++){//清空原角色选择情况
						treeOfPoroduct.root.childNodes[i].fireEvent('checkchange',treeOfPoroduct.root.childNodes[i],false,undefined);
					}
					for(var i=0;i<treeLoader.length;i++){
						var tn = treeOfPoroduct.root.findChild('id',treeLoader[i].VIEW_ID,true);
						if(tn!=undefined && tn.childNodes.length == 0){
							tn.fireEvent('checkchange',tn,true);
						}
					}
					addStr = [];
					delStr = [];
						
			}
			});
		}
	}
	}
	});
			
	var view = new Ext.Viewport({
		layout:'fit',
		items:[{
			layout : 'border',
			items : [leftTreeForShow, treeOfPoroduct],
			buttonAlign : 'center',
			buttons : [ {
				text : '保存',
				handler : function() {
				saveSet();
			}
			} ]
		}]
	});
});