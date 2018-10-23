Ext.onReady(function(){
   //定义树的跟节点
   var root=new Ext.tree.TreeNode({
          id:"root",//根节点id
   draggable:false,
          text:"供应链视图"
    });
    //定义树节点
	var c1=new Ext.tree.TreeNode({
	id:'c1',//子结点id
	text:'供应链基本信息'
	});
	 c1.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/offerLinkMkt/offerLinkInfo.js');
	 });
	 
	 
	var c2=new Ext.tree.TreeNode({
	id:'c2',//子结点id
	text:'成员信息'
	});
	 c2.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/offerLinkMkt/offerLinkMemberInfo.js');
	 });
	var c3=new Ext.tree.TreeNode({
	  id:'c3',//子结点id
	  text:'授信信息'
	});
	 c3.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/offerLinkMkt/offerLinkGuarInfo.js');
	 });
	 var c4=new Ext.tree.TreeNode({
		  id:'c4',//子结点id
		  text:'成员企业关系图'
		});
	 c4.on('click', function(){ 
			fElementRemove();
			fnViewLoader(basepath+'/contents/pages/customer/offerLinkMkt/membersRelation.js');
		 });
	 var c8=new Ext.tree.TreeNode({
		  id:'c8',//子结点id
		  text:'管理小组信息'
		});
	 c8.on('click', function(){ 
			fElementRemove();
			fnViewLoader(basepath+'/contents/pages/customer/offerLinkMkt/memberManaRelation.js');
		 });
	
		 var c5=new Ext.tree.TreeNode({
			  id:'c5',//子结点id
			  text:'产品推荐'
			});
		 var c6=new Ext.tree.TreeNode({
			  id:'c6',//子结点id
			  text:'热销产品'
			});
		 c6.on('click', function(){ 
				fElementRemove();
				fnViewLoader(basepath+'/contents/pages/customer/customerGroup/demo/productAdvise1.js');
			 });
		 var c7=new Ext.tree.TreeNode({
			  id:'c7',//子结点id
			  text:'适合产品'
			});
		 c7.on('click', function(){ 
				fElementRemove();
				fnViewLoader(basepath+'/contents/pages/customer/customerGroup/demo/productAdvise.js');
			 });

		
		root.appendChild(c1);
		root.appendChild(c2);
		root.appendChild(c3);
		root.appendChild(c4);
		root.appendChild(c8);
		root.appendChild(c5);
		c5.appendChild(c6);
		c5.appendChild(c7);
		
	//生成树形面板
	var tree=new Ext.tree.TreePanel({
		autoScroll:true,
	    root:root,//定位到根节点
	    animate:true,//开启动画效果
	    enableDD:false,//允许子节点拖动
	    border:false,//没有边框
	    containerScroll: true,
	    rootVisible:false//设为false将隐藏根节点，很多情况下，我们选择隐藏根节点增加美观性
	 });
	var viewport_left = new Ext.Panel({
		frame:true,
		renderTo:'sena_tree',
		height:document.body.scrollHeight-61,
		//layout:'fit',
		autoScroll:true,
		title : '<span style="font-weight:normal">供应链视图菜单</span>',
			items: [{ 
			    margins: '0 0 0 0',
			    items : [tree]
		    }]
	
		});
	var sJsName="";
	 
	 var fnViewLoader= function(sJsName){
		 ScriptMgr = new ScriptLoaderMgr();
		    ScriptMgr.load({        
			scripts: [sJsName],        
			callback: function() {
			}    
		    }); 
		};
		var	fElementRemove=function(){
			 document.getElementById('viewport_center').innerHTML="";
		};
		
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/offerLinkMkt/offerLinkInfo.js');
	
});