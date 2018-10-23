/**
 * 客户经理快捷工作台
 * @author wz
 */
	var globleId = "";//选中节点的节点ID
	var globleNode = "";//选中节点
	var childrenData = "";//节点data
	//页面跳转
	function replacePage(url){
//		parent.booter.indexLocate(400,url);
		
		debugger;
		parent.parent.booter.indexLocate(238);
	}
	
	/**
	 * 右键菜单的移动到群组刷新用函数
	 * */
	function moveRefreshFunc() {
	var a;
	//移动到群组
	rightButtionMenu.findById('moveTo').menu.removeAll();
	for(a = 0;a<childrenData.length;a++){//循环向‘移动到群组’中添加子菜单
		rightButtionMenu.findById('moveTo').menu.add({
			text:childrenData[a].text,
			id:childrenData[a].id+'_move'
		});
		Ext.getCmp(childrenData[a].id+'_move').on('click',function(e){//为每个菜单项添加点击事件
			//参数为，点击节点的旧父节点 和 点击节点的新父节点
			rightMove(globleNode.parentNode,e);
		});
	}
	
	//加入到群组
	rightButtionMenu.findById('addToGroup').menu.removeAll();
	for(a = 0;a<childrenData.length;a++){//循环向‘加入到群组’中添加子菜单
		rightButtionMenu.findById('addToGroup').menu.add({
			text:childrenData[a].text,
			id:childrenData[a].id+'_add'
		});
		Ext.getCmp(childrenData[a].id+'_add').on('click',function(e){//为每个菜单项添加点击事件
			//参数为，点击节点的旧父节点 和 点击节点的新父节点
			rightAddGroup(e);
		});
	}
	}
	
	/**
	 * 刷新节点树用
	 * */
	function refreshFunc() {
		Ext.Ajax.request({//刷新树用
			url : basepath + '/myCustomerGroupQueryAction.json',
			method:'GET',
			success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
			loader1.nodeArray = nodeArra;// 获取后台返回的数据
			loader1.refreshCache();//清空loader中缓存
			childrenData = loader1.loadAll();//新的数据
			accordionTree.root.removeAll(true);//移除树中旧的数据
			accordionTree.root.appendChild(childrenData);//添加新的数据
			moveRefreshFunc();
			addWin.hide();
		}
		});
	}
	
	/**
	 * 新增群组函数
	 * */
	function addGroupFunc(){
		Ext.Ajax.request({//新增群组用
			url : basepath + '/myCustomerGroupAction.json',
			method:'POST',
			form : addForm.getForm().id,
			success:function(response){
			Ext.getCmp('groupNameId').setValue('');
			refreshFunc();//刷新树用
		}
		});
	}
	
	/**
	 * 修改群组名称函数
	 * */
	function modGroupFunc(){
	Ext.Ajax.request({//新增群组用
		url : basepath + '/myCustomerGroupAction!modNode.json',
		method:'POST',
		params : {
		nodeId : globleId,
		nodeName : Ext.getCmp('modgroupNameId').getValue()
	},
	success:function(response){
		Ext.getCmp('modgroupNameId').setValue('');
		globleNode.text = "";
		modWin.hide();
		refreshFunc();//刷新树用
	}
	});
	}
	
	/**
	 * 删除群组函数
	 * */
	function delFunc(){
		if(globleId != null && globleId != ""){
			if(globleNode.attributes.children != undefined){
				if(globleNode.attributes.children.length > 0){
					Ext.Msg.alert('提示','群组中存在客户，无法删除！');
					return false;
				}
			}
			Ext.Msg.confirm('提示','是否删除！',function(btn){
				if(btn == 'yes'){
					Ext.Ajax.request({//删除节点用
						url : basepath + '/myCustomerGroupAction!delNode.json',
						method:'POST',
						params : {
						nodeId : globleId
					},
					success:function(response){
						refreshFunc();//刷新树用
					}
					});
				}else{
					return false;
				}
			});
		}else{
			Ext.Msg.alert('提示','请选择要删除的群组！');
		}
	}
	
	/**
	 * 拖动客户节点用
	 * */
	function move(tree,node,oldParent,newParent,index) {
		Ext.Ajax.request({
			url:basepath+'/myCustomerGroupAction!moveNodeSave.json',
			method:'POST',
			params:{
			nodeId:node.id,
			oldParent:oldParent.id,
			newParent:newParent.id
		},
		success:function(){
			refreshFunc();//刷新树用
		},
		failure:function(){
			Ext.Msg.alert('提示','操作失败，请稍后再试！');
		}
		});
	}
	
	/**
	 * 右键移动客户节点用
	 * */
	function rightMove(oldParent,newParent) {
		if(globleId != null && globleId != ""){
			Ext.Ajax.request({
				url:basepath+'/myCustomerGroupAction!moveNodeSave.json',
				method:'POST',
				params:{
				nodeId:globleNode.id,
				oldParent:oldParent.id,
				newParent:newParent.id
			},
			success:function(){
				refreshFunc();//刷新树用
			},
			failure:function(){
				Ext.Msg.alert('提示','操作失败，请稍后再试！');
			}
			});
		}else{
			Ext.Msg.alert('提示','请选择要移动的客户！');
		}
	}
	
	/**
	 * 右键加入到群组节点用
	 * */
	function rightAddGroup(newParent) {
		if(globleId != null && globleId != ""){
			Ext.Ajax.request({
				url:basepath+'/myCustomerGroupAction!addNodeSave.json',
				method:'POST',
				params:{
				nodeId:globleId,
				newParent:newParent.id
			},
			success:function(){
				refreshFunc();//刷新树用
			},
			failure:function(){
				Ext.Msg.alert('提示','操作失败，请稍后再试！');
			}
			});
		}else{
			Ext.Msg.alert('提示','请选择要移动的客户！');
		}
	}
	
	//右键菜单展示内容
	var rightButtionMenu = new Ext.menu.Menu({
		id:'rightMenu',
		style:{
		overflow:'visible'
	},
	items:[
	       {
	    	   id:'custView',
	    	   text:'客户视图',
	    	   handler:function(){
	    	   alert('客户视图');
	       }
	       },'-',{
	    	   id:'createBusi',
	    	   text:'创建商机',
	    	   handler:function(){
	    	   alert('创建商机');
	       }
	       },'-',{
	    	   id:'sendMsg',
	    	   text:'发送信息',
	    	   handler:function(){
	    	   alert('发送信息');
	       }
	       },'-',{
	    	   id:'setAtt',
	    	   text:'设置关注',
	    	   handler:function(){
	    	   alert('设置关注');
	       }
	       },'-',{
	    	   id:'addGroup',
	    	   text:'新增群组',
	    	   handler:function(){
	    	   addWin.show();
	       }
	       },'-',{
	    	   id:'modGroup',
	    	   text:'修改群组',
	    	   handler:function(){
	    	   if(globleId != null && globleId != ""){
	    		   Ext.getCmp('modgroupNameId').setValue(globleNode.text);//群组名称
	    		   modWin.show();
	    	   }else {
	    		   Ext.Msg.alert('提示','请选择群组！');
	    	   }
	       }
	       },'-',{
	    	   id:'delGroup',
	    	   text:'删除群组',
	    	   handler:function(){
	    	   delFunc();
	       }
	       },'-',{
	    	   id:'moveTo',
	    	   text:'移动到群组',
	    	   menu:{}
	       },'-',{
	    	   id:'addToGroup',
	    	   text:'加入到群组',
	    	   menu:{}
	       }
	       ]
	});
	
	//我的客户基本树load
	var loader1 = new Com.yucheng.bcrm.ArrayTreeLoader({//功能模块选择的树加载数据
		parentAttr : 'PARENT_ID',	//指向父节点的属性列
		locateAttr : 'ID',			//节点定位属性列，也是父属性所指向的列
		rootValue : "0",			//虚拟根节点id 若果select的值为root则为根节点
		textField : 'NAME',			//用于展示节点名称的属性列
		idProperties : 'ID'			//指定节点ID的属性列
	});
	
	//我的客户基本树
	var accordionTree = new Com.yucheng.bcrm.TreePanel({
		title : '客户',
		id : 'accordionId',
		width:200,
		autoScroll:true,
		ddGroup: 'gridDDGroup',
		enableDD  : true,
		animate : false,
		useArrows : false,
		border : false,
		rootVisible:true,
		
		root: new Ext.tree.AsyncTreeNode({//虚拟树形根节点
			id:'root',
			text:'我的客户',
			autoScroll:true,
			expanded:true,
			leaf:false,
			children:[]
		}),
		resloader: loader1,
		split:true,
		listeners:{
		'nodedragover':function(e){//修正节点可以拖动到叶子节点下
		if(e.data.node.attributes.PARENT_ID == '0'){
//					Ext.Msg.alert('提示','群组不可移动到其他群组中！');
			return false;
		}
		if(e.target.attributes.PARENT_ID != '0'){
//					Ext.Msg.alert('提示','只可拖动到客户群组中！');
			if(e.target.id == 'root'){
				Ext.Msg.alert('提示','只能向群组中拖动客户！');
				return false;
			}
			return false;
		}
		e.target.leaf = false;
	},
	'click':function(node){//点击事件
//		Ext.Ajax.request({
//			url : basepath + '/myCustomerGroupQueryAction.json',
//			method:'GET',
//			success:function(response){
//				var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
//				loader1.nodeArray = nodeArra;
//				childrenData = loader1.loadAll();
//				Ext.each(nodeArra,function(n){//添加图标
//					if(n.CUST_TYP == '1'){//对私客户
//						n.icon = basepath+'/'+'/contents/images/fw/icon_menu_016.gif';
//					}else if(n.CUST_TYP == '2'){//对公客户
//						n.icon = basepath+'/'+'/contents/images/fw/icon_menu_156.gif';
//					}
//				});
//				accordionTree.appendChild(childrenData);
//				debugger;
//				moveRefreshFunc();
//			}
//		});
		globleId = node.attributes.CUST_ID;
		globleNode = node;
	},
	'startdrag':function(){//开始拖动节点时事件
		
	},
	'enddrag':function(){//拖动节点结束时事件
		
	},
	'movenode':function(tree,node,oldParent,newParent,index){//移动节点事件
		move(tree,node,oldParent,newParent,index);
	}
	}
	});
	
	//右键展示菜单控制
	accordionTree.on("contextmenu",function(node,e){
		Ext.getCmp('moveTo').disable();//移动到群组
		Ext.getCmp('addGroup').disable();//新增群组
		Ext.getCmp('modGroup').disable();//修改群组
		Ext.getCmp('delGroup').disable();//删除群组
		Ext.getCmp('custView').disable();//客户视图
		Ext.getCmp('createBusi').disable();//创建商机
		Ext.getCmp('sendMsg').disable();//发送信息
		Ext.getCmp('setAtt').disable();//设置关注
		Ext.getCmp('addToGroup').disable();//加入到群组
		if(node.id == 'root'){//根节点操作：新建群组
			Ext.getCmp('addGroup').enable();
		}else{
			if(node.attributes.PARENT_ID == '0'){//群组上操作：修改群组，删除群组
				Ext.getCmp('modGroup').enable();
				Ext.getCmp('delGroup').enable();
			}else{//客户上操作：客户视图，创建商机，发送信息，设置关注，加入到群组，移动到群组
				Ext.getCmp('custView').enable();
				Ext.getCmp('createBusi').enable();
				Ext.getCmp('sendMsg').enable();
				Ext.getCmp('setAtt').enable();
				Ext.getCmp('moveTo').enable();
				Ext.getCmp('addToGroup').enable();
			}
		}
		rightButtionMenu.showAt(e.getXY());
	});
	
	//新增群组Form
	var addForm = new Ext.form.FormPanel({
		id:'addFormId',
		labelWidth:90,
		frame:true,
		labelAlign:'middle',
		buttonAlign:'center',
		weight:150,
		height:70,
		items:[{
			fieldLabel:'群组名称',
			id:'groupNameId',
			xtype:'textfield',
			name:'groupName',
			labelStyle:'text-align:right;',
			anchor:'80%'
		}],
		buttons:[{
			text:'保存',
			handler:function(){
			addGroupFunc();
		}
		}]
	});
	
	//新增群组窗口
	var addWin = new Ext.Window({
		id:'addID',
		width:300,
		height:100,
		closeAction:'hide',
		closable:true,
		maximizable:true,
		animCollapse:false,
		constrainHeader:true,
		items:[addForm]
	});
	
	//修改群组Form
	var modForm = new Ext.form.FormPanel({
		id:'modFormId',
		labelWidth:90,
		frame:true,
		labelAlign:'middle',
		buttonAlign:'center',
		weight:150,
		height:70,
		items:[{
			fieldLabel:'群组名称',
			id:'modgroupNameId',
			xtype:'textfield',
			name:'groupName',
			labelStyle:'text-align:right;',
			anchor:'80%'
		}],
		buttons:[{
			text:'保存',
			handler:function(){
			modGroupFunc();
		}
		}]
	});
	
	//修改群组窗口
	var modWin = new Ext.Window({
		id:'modID',
		width:300,
		height:100,
		closeAction:'hide',
		closable:true,
		maximizable:true,
		animCollapse:false,
		constrainHeader:true,
		items:[modForm]
	});
	
	//工具窗口
	var accordionWindow = new Ext.Window({
		id: 'acc-win',
		title: '客户经理快捷工作台',
		width:220,
		height:400,
		pageX : 800,
		pageY : 92,
		iconCls: 'accordion',
		shim:false,
		closeAction:'hide',
		closable:true,
		maximizable : true,
		collapsible : true,
//        tbar:[{
//            text:'Rich',
//        	handler:function(){
//    		alert('Rich');
//    	}
//        },'-',{
//        	text:'新增群组',
//        	handler:function(){
//        		addWin.show();
//        	}
//        },'-',{
//        	text:'删除群组',
//        	handler:function(){
//    		    delFunc();
//    	}
//        }],
		
		layout:'accordion',
		border:false,
		layoutConfig : {
		animate : false
	},
	items: [accordionTree, {
	        	title: '我的任务',
	        	html:'<p><br/>待办任务：<a href=\"javascript:;\" onclick=\"parent.parent.booter.indexLocate(640);\">6</a><br/>'
	        	+'	点击数字进入待办任务模块<br/><br/></p>',
	        	autoScroll:true
	        },{
	        	title: '我的线索商机',
	        	html : '<p><br/>新分配商机 ：<a href=\"javascript:;\" onclick=\"parent.parent.booter.indexLocate(333);\">6</a><br/><br/>'
	        		+'开发中商机：<a href=\"javascript:;\" onclick=\"parent.parent.booter.indexLocate(333);\">3</a><br/><br/>      '
	        		+'成功关闭商机：<a href=\"javascript:;\" onclick=\"parent.parent.booter.indexLocate(333);\">14</a><br/><br/>    '
	        		+'失败关闭商机：<a href=\"javascript:;\" onclick=\"parent.parent.booter.indexLocate(333);\">2</a><br/><br/>     '
	        		+'点击数字进入商机线索模块</p>'
	        },{
	        	title: '我的业绩',
	        	html : '<p><br/>本月新增贷款：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;888,888<br/><br/>'
	        	+'本年新增贷款：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;888,888<br/><br/>'
	        	+'当前贷款余额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;888,888<br/><br/>'
	        	+'本月新增存款：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;888,888<br/><br/>'
	        	+'当前存款余额：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;888,888<br/><br/>'
	        	+'当前存款日均：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;888,888<br/><br/>'
	        	+'本月理财产品销售：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;888,888<br/><br/>'
	        	+'本年理财产品销售：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;888,888<br/><br/>'
	        	+'本月新增客户：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;289<br/></p>'
	        }]
	});
	
	//默认我的客户查询Ajax请求
	accordionTree.on('afterrender',function(){
		Ext.Ajax.request({
			url : basepath + '/myCustomerGroupQueryAction.json',
			method:'GET',
			success:function(response){
				var nodeArra = Ext.util.JSON.decode(response.responseText).json.data;
				loader1.nodeArray = nodeArra;
				childrenData = loader1.loadAll();
				Ext.each(nodeArra,function(n){//添加图标
					if(n.CUST_TYP == '1'){//对私客户
						n.icon = basepath+'/'+'/contents/images/fw/icon_menu_016.gif';
					}else if(n.CUST_TYP == '2'){//对公客户
						n.icon = basepath+'/'+'/contents/images/fw/icon_menu_156.gif';
					}
				});
				accordionTree.appendChild(childrenData);
				moveRefreshFunc();
			}
		});
	});
