/**
 * 资源授权控制
 * @author wz
 */
//获取角色url
    var roleProxy = new Ext.data.HttpProxy({
        url : basepath + '/roleManagerQuery!getAuthRoles.json'
    });
    
    //角色记录
    var RoleRecord = Ext.data.Record.create([
        {name: 'id', mapping: 'ID'},
        {name: 'roleCode', mapping: 'ROLE_CODE'},                                   
        {name: 'roleId', mapping: 'ROLE_ID'},  
        {name: 'roleName', mapping: 'ROLE_NAME'},
        {name: 'roleType', mapping: 'ROLE_TYPE'},
        {name: 'roleTypeOra', mapping: 'ROLE_TYPE_ORA'},
        {name: 'accountId', mapping: 'ACCOUNT_ID'},
        {name: 'appId', mapping: 'APP_ID'}
    ]);
    
    //角色数据读取
    var roleReader = new Ext.data.JsonReader({
    	successProperty: 'success',
    	idProperty: 'id',
    	messageProperty: 'message',
    	totalProperty: 'json.count',
    	root : 'json.data'
    },RoleRecord);
	
    //角色store
    var roleStore = new Ext.data.Store({
        restful : true,
        proxy : roleProxy,
        reader : roleReader,
        recordType:RoleRecord
    });
    
Ext.onReady(function(){
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
		for(var i=0;i<arr.length;i++){
			if(arr[i].RES_CODE==id){
				return true;
			}
		}
		return false;
	};
	
	/**
	 * 全局变量
	 * */
	var roleNameGloble = '';//选择的角色名称  全局变量
	var roleCodeGloble = '';//选择的角色编码  全局变量
    var menuCodeGloble = '';//选择的菜单编码  全局变量
	var record ='';//选择的角色编码
	var delGrantsMap = [];//del功能按钮编码
	var addGrantsMap = [];//add功能按钮编码
	var delGrantsStr = '';//del功能按钮编码
	var addGrantsStr = '';//add功能按钮编码
	//删除数组
	var delStr = [];
	//新增数组
	var addStr = [];
	//删除数组
	var delMenuStr = '';
	//新增数组
	var addMenuStr = '';
	var treeLoader='';
	var responseTemp = {};
	/**
	 * deal Controllers data
	 */
	function beforeSaveSet(){
		for(var i=0;i<delGrantsMap.length;i++){
			var obj = {};
			obj.menuCode = delGrantsMap[i].attributes.PARENT_ID;
			obj.opCode   = delGrantsMap[i].attributes.OPCODE;
			if (delGrantsStr.length == 0) {
				delGrantsStr = obj.menuCode + ' ' +obj.opCode;
			} else {
				delGrantsStr += ',' + obj.menuCode + ' ' +obj.opCode;
			}
		}
		for(var i=0;i<addGrantsMap.length;i++){
			var obj = {};
			obj.menuCode  = addGrantsMap[i].attributes.PARENT_ID;
			obj.opCode    = addGrantsMap[i].attributes.OPCODE;
			if (addGrantsStr.length == 0) {
				addGrantsStr = obj.menuCode + ' ' +obj.opCode;
			} else {
				addGrantsStr += ',' + obj.menuCode + ' ' +obj.opCode;
			}
		}
		
		for(var i=0;i<addStr.length;i++){
			if(addMenuStr == '') {
				addMenuStr = addStr[i];
			} else {
				addMenuStr = addMenuStr + ',' + addStr[i];
			}
		}
		
		for(var i=0;i<delStr.length;i++){
			if(delMenuStr == '') {
				delMenuStr = delStr[i];
			} else {
				delMenuStr = delMenuStr + ',' + delStr[i];
			}
		}
		
		
	};
	/**
	 * 保存设置
	 */
	function saveSet(){
		if(roleCodeGloble == undefined || roleCodeGloble == "" || roleCodeGloble == null){//未选择角色编码时，提示必选信息
			Ext.Msg.alert('提示','请选择角色！');
			return ;
		}
		beforeSaveSet();
		Ext.Ajax.request({//执行保存设置
			//增量数据操作url
			url : basepath + '/roleMenuOptionAction!saveOptionSet.json',
//			?addStr='+addStr+'&delStr='+delStr+
//							 '&roleCodeGloble='+roleCodeGloble+'&menuCodeGloble='+menuCodeGloble
//							 +'&addGrantsStr='+addGrantsStr+'&delGrantsStr='+delGrantsStr,
			method:'POST',
			params :{
				addStr : addMenuStr,
				delStr : delMenuStr,
				roleCodeGloble : roleCodeGloble,
				menuCodeGloble : menuCodeGloble,
				addGrantsStr : addGrantsStr,
				delGrantsStr : delGrantsStr
			},
			success:function(response){
				Ext.Msg.alert('提示','操作成功！');
    			if(record != undefined){
    				roleCodeGloble = record.data.id;
    				Ext.Ajax.request({
    					url:basepath + '/roleMenuQuery.json?roleId='+record.data.id,
    					method:'GET',
    					success:function(response){
    						refreshCheckItem(response);
    					},
	    				failure:function(){
	    				}
    				});
    			}
			},
			failure:function(){
				Ext.Msg.alert('提示','操作失败！');
			}
		});
	}
	
//	/**
//	 * 保存设置
//	 */
//	function save(){
//		addStr=[];
//		delStr=[];
//		menuLoader.loadAll();
//		if(roleCodeGloble == undefined || roleCodeGloble == "" || roleCodeGloble == null){//未选择角色编码时，提示必选信息
//			Ext.Msg.alert('提示','请选择角色！');
//			return ;
//		}
//		if((menuCodeGloble == undefined || menuCodeGloble == "" || menuCodeGloble == null)){//为选择菜单编码时，提示必选信息
//			Ext.Msg.alert('提示','请选择菜单！');
//			return ;
//		}
//		Ext.Ajax.request({//执行保存设置
//			//增量数据操作url
//			url : basepath + '/roleMenuOptionAction!saveOptionSet.json?addStr='+addStr+'&delStr='+delStr+'&roleCodeGloble='+roleCodeGloble+'&menuCodeGloble='+menuCodeGloble,
//			method:'GET',
//			success:function(response){
//				Ext.Msg.alert('提示','操作成功！');
//				menuLoader.loadAll();
//			},
//			failure:function(){
//				Ext.Msg.alert('提示','操作失败！');
//			}
//		});
//	}
	
    //角色栏列模型
    var roleCm = new Ext.grid.ColumnModel([{
    	hidden : true,
    	dataIndex : 'id'
    },{
    	dataIndex : 'roleId', 
    	hidden : true
    },{
    	dataIndex : 'roleCode',
    	hidden:true
    },{
    	hidden :true,
    	dataIndex : 'roleType'
    },{
    	header : '角色名称',
    	sortable : true,
    	width : 230,
    	dataIndex : 'roleName'
    }]);
  
    var roleTbar = new Ext.Toolbar({
		items : [{
			text : '导出授权信息',
			iconCls:'editIconCss',
			handler : function() {
				var rolesStr = '';
				var roleArr  = roleStore.reader.jsonData.json.data;
				for(var i = 0; i < roleArr.length; i++) {
					if (rolesStr.length == 0) {
						rolesStr = '\''+roleArr[i].ID+'\'';
					} else {
						rolesStr += ',\''+roleArr[i].ID+'\'';
					}
				}
				Ext.Ajax.request({
					url:basepath + '/menuInitAuthortication!exportAuthInfo.json?rolesStr='+rolesStr,
					method:'GET',
					success:function(response){
						var res = Ext.util.JSON.decode(response.responseText);
						if(res.filename){
                        	window.location.href = basepath+'/FileDownload?filename='+res.filename;
                        }
					},
    				failure:function(){
    				}
				});
			}
		},'-',{
			text : '复制角色',
			iconCls:'resetIconCss ',
			handler : function() {
				if(roleCodeGloble == undefined || roleCodeGloble == "" || roleCodeGloble == null){//未选择角色编码时，提示必选信息
					Ext.Msg.alert('提示','请选择角色！');
					return ;
				}
				copyToNewRoleForm.getForm().reset();
				Ext.getCmp('copyRoleWin').setTitle("复制角色: "+ roleNameGloble);
				_roleCodeGloble = roleCodeGloble;
				// 去掉复制源角色
				_roleStore.on('load',function(a,b,c,d){
					var dataArr = _roleStore.data.items;
					for ( var i = 0; i < dataArr.length; i++) {
						if(dataArr[i].data.id == _roleCodeGloble)
						{
							_roleStore.remove(dataArr[i]);
						}
					}
				});
				_roleStore.load();
				
				copyRoleWin.show();
			}
		}
		]
	});
  //角色栏grid
    var roleGrid = new Ext.grid.GridPanel({
    	title : '角色栏',
        store : roleStore, 
        tbar : roleTbar,
        region : 'west',
        stripeRows : true, 
        cm : roleCm,
        width:234,
        viewConfig : {
        }
    });
    
    
    //菜单树加载数据
    var menuLoader = new Com.yucheng.bcrm.ArrayTreeLoader({
		parentAttr : 'PARENT_ID', 	 						//指向父节点的属性列
		locateAttr : 'ID',       	 						//节点定位属性列，也是父属性所指向的列
		rootValue : "0",          	 						//虚拟根节点id 若果select的值为root则为根节点
		textField : 'COUNTNAME',			 				//用于展示节点名称的属性列
		idProperties : 'ID'          						//指定节点ID的属性列
	});
    
    
	var tbar = new Ext.Toolbar({
		items : [{
			text : '保存设置',
			iconCls:'completeIconCss',
			handler : function() {
				saveSet();
			}
		},'-',{
			text : '清空',
			iconCls:'maintainIconCss',
			handler : function() {
				for(var i=0;i<leftTreeForShow.root.childNodes.length;i++){//清空原角色选择情况
					leftTreeForShow.root.childNodes[i].fireEvent('checkchange',leftTreeForShow.root.childNodes[i],false,undefined);
				}
			}
		},'-',{
			text : '重置',
			iconCls:'shenpiIconCss',
			handler : function() {
				if(responseTemp != undefined)
					refreshCheckItem(responseTemp);
			}
		}]
	});
    //左边的tree的形状
	var leftTreeForShow = new Com.yucheng.bcrm.TreePanel({
		title: '菜单栏',
		width:300,
		checkBox:true, 
		resloader:menuLoader, 
		loadMaskWorking : true,
		expandedCount : 0,
		autoScroll:true,
		region:'center',
		tbar:tbar,
		rootVisible : false,
		/**虚拟树形根节点*/
		root: new Ext.tree.AsyncTreeNode({
			id:'root',
			expanded:true,
			text:'主菜单',
			children:[]
		})
		,
		listeners:{
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
//			'click':function(node){
//				if(treeLoader!==''){
//					if(node.id!=='root'){
//						//事件在节点被单击时触发,判断该节点是否被选中
//						if(!node.getUI().checkbox.checked){
//							Ext.Msg.alert('提示','当前角色未获得此菜单授权');
//							return;
//						}
//						menuCodeGloble = node.id;//向后台提交用
//					}
//				}else{
//					Ext.Msg.alert('提示','请选择授权角色');
//					return;
//				}
//			},
			'checkchange' : function(node, checked, source) {
				
				if(treeLoader!==''){
					//if (node.attributes.NODETYPE == '0') {
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
					//}
					if (node.attributes.NODETYPE == '1') {
						if (checked) {
							delGrantsMap.remove(node);
        					addGrantsMap.push(node);
        				} else {
        					delGrantsMap.push(node);
        					addGrantsMap.remove(node);
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
		        			if (node.attributes.NODETYPE == '0') { //控制点勾选特殊处理
		        				node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');//若存在父节点，则传递参数以触发相应监听事件
							} else {
								if (checked) {
									delGrantsMap.remove(node);
		        					addGrantsMap.push(node);
		        					node.parentNode.fireEvent('checkchange',node.parentNode,checked,'2');//若存在父节点，则传递参数以触发相应监听事件
		        				} else {
		        					delGrantsMap.push(node);
		        					addGrantsMap.remove(node);
		        				}
							}
		        		}
		        	}else if(source == '1'){								//操作节点状态，并调用子节点事件
		        		node.getUI().checkbox.indeterminate=false;
		        		node.getUI().toggleCheck(checked);
		        		if(node.childNodes){
		        			Ext.each(node.childNodes,function(cn){
		        				cn.fireEvent('checkchange',cn,checked,'1');//若存在子节点，则传递参数以触发相应checkChange监听事件
		        			});
		        		}
		        	}else if(source == '2'){
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
			}
		}
	});	
	
	//遮罩层定义
	var lm = new Ext.LoadMask (document.body,{
	   	msg : '正在加载菜单数据,请稍等...'
	});
	    
	lm.show();																//节点全部展开前，打开遮罩层
	
	Ext.Ajax.request({														//左侧菜单项树的Ajax请求事件
		url : basepath + '/menuInitAuthortication.json',
		method:'GET',
		success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			Ext.each(nodeArra.json.data,function(n){
				if(n.ICON){
					var test;
					test = Ext.util.Format.substr(n.ICON,0,1);
					if(test=='/')
					{n.icon = basepath+'/'+'contents/'+n.ICON;//对从台读取图标数据进行处理，变成绝对路径，那样才能显示图标
					}else{
						n.icon = '';
					}
				}
			});
			menuLoader.nodeArray = nodeArra.json.data;
			var children = menuLoader.loadAll();
			leftTreeForShow.appendChild(children);
			leftTreeForShow.expandAll();//默认展开树，解决前台取不到子节点，从而不能递归check问题
		}
	});
	
    roleStore.load({										//默认角色栏数据加载
    	callback:function(a,b,c,d){							//角色加载完后，选择任意角色，在菜单栏中进行相应数据勾选操作
    		roleGrid.on('click',function(btn){
    			record = roleGrid.getSelectionModel().getSelected();
    			if(record != undefined){
    				roleCodeGloble = record.data.id;
    				roleNameGloble = record.data.roleName;
    				Ext.Ajax.request({
    					url:basepath + '/roleMenuQuery.json?roleId='+record.data.id,
    					method:'GET',
    					success:function(response){
    						responseTemp = response;// 暂存角色菜单权限，提供给‘重复制’按钮使用
    						refreshCheckItem(response);
    					},
	    				failure:function(){
	    				}
    				});
    			}
    		});
    	}
    });
    var refreshCheckItem = function (response){
		treeLoader = Ext.util.JSON.decode(response.responseText).json.data;//获取选择角色的菜单项
		for(var i=0;i<leftTreeForShow.root.childNodes.length;i++){//清空原角色选择情况
			leftTreeForShow.root.childNodes[i].fireEvent('checkchange',leftTreeForShow.root.childNodes[i],false,undefined);
		}
		for(var i=0;i<treeLoader.length;i++){
			var tn = leftTreeForShow.root.findChild('id',treeLoader[i].RES_CODE,true);
			if (tn!=undefined && tn.attributes.LEAF_FLAG == 1) {
				tn.fireEvent('checkchange',tn,true);
				for(var j=0; j<tn.childNodes.length;j++){
    				tn.childNodes[j].getUI().toggleCheck(false);
    			}
			}
			if(tn!=undefined && treeLoader[i].OPERATE_KEY != '["VIEW","AUTH_PERMISSION"]'){
				for(var j=0; j<tn.childNodes.length;j++){
    				if(treeLoader[i].OPERATE_KEY.indexOf('\"'+tn.childNodes[j].attributes.OPCODE+'\"') != -1){
    					tn.childNodes[j].getUI().toggleCheck(true);
    				} 
    			}
			}
			
		}
		addStr = [];
		delStr = [];
		addMenuStr = '';
		delMenuStr = '';
		delGrantsMap = [];
		addGrantsMap = [];
		addGrantsStr = '';
		delGrantsStr = '';		
	
    };
    
    //前台展示布局
	var authorticationView = new Ext.Viewport({
		split:true,
		layout : 'fit',
		items : [{
			layout:'border',
			items : [ roleGrid,leftTreeForShow]
		}]
	});
	
	copyRoleWin.on('hide',function(){
		roleStore.load();
	});
});