Ext.onReady(function(){
   //定义树的跟节点
   var root=new Ext.tree.TreeNode({
          id:"root",//根节点id
   draggable:false,
          text:"菜单"
    });
    //定义树节点
	var c1=new Ext.tree.TreeNode({
	id:'c1',//子结点id
	text:'查看群成员'
	});
	 c1.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader('custMembers.js');
	 });
	 
	//定义“成员关系图”功能菜单节点
	var rg=new Ext.tree.TreeNode({
		id : 'rg',//子结点id
		text : '成员关系图'
	});
	rg.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath + "/contents/commonjs/mxGraphLocal/mxclient-ie1.8.js");
		 fnViewLoader(basepath + "/contents/commonjs/mxGraphLocal/mxGrapth-Crm-locale-ext-v1.000.js");
		 fnViewLoader(basepath + "/contents/commonjs/mxGraphLocal/crm-mxGraph-api-v1.000.js");
		 fnViewLoader('custMembersRelation.js');
	 });
	 
	var c2=new Ext.tree.TreeNode({
	id:'c2',//子结点id
	text:'账户信息'
	});
	 c2.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader('../customerManager/informationOnBusinessCooperation.js');
	 });
	var c3=new Ext.tree.TreeNode({
	  id:'c3',//子结点id
	  text:'产品信息'
	});
	 c3.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader('../customerManager/customerBaseInformation/customerPoroductInformation2.js');
	 });
	 var c4=new Ext.tree.TreeNode({
		  id:'c4',//子结点id
		  text:'客户群业务分析'
		});
	 
	 var c5=new Ext.tree.TreeNode({
		  id:'c5',//子结点id
		  text:'存款业务分析'
		});
		 c5.on('click', function(){ 
			 fElementRemove();
			 fnViewLoader('demo/customerBaseDep.js');
		 });
	 var c6=new Ext.tree.TreeNode({
		  id:'c6',//子结点id
		  text:'贷款业务分析'
		});
		 c6.on('click', function(){ 
			 fElementRemove();
			 fnViewLoader('demo/customerBaseLoan.js');
		 });
		 var c7=new Ext.tree.TreeNode({
			  id:'c7',//子结点id
			  text:'贴现业务分析'
			});
		 c7.on('click', function(){ 
			 fElementRemove();
			 fnViewLoader('demo/customerBaseTX.js');
		 });
		 var c8=new Ext.tree.TreeNode({
			  id:'c8',//子结点id
			  text:'票据业务分析'
			});
		 c8.on('click', function(){ 
			 fElementRemove();
			 fnViewLoader('demo/customerBasePJ.js');
		 });
		 var c9=new Ext.tree.TreeNode({
			  id:'c9',//子结点id
			  text:'理财业务分析'
			});
		 c9.on('click', function(){ 
			 fElementRemove();
			 fnViewLoader('demo/customerBasePJ1.js');
		 });
		 var c10=new Ext.tree.TreeNode({
			  id:'c10',//子结点id
			  text:'国际业务分析'
			});
		 c10.on('click', function(){ 
			 fElementRemove();
			 fnViewLoader('custBusiAnal/national.js');
		 });
		 var c11=new Ext.tree.TreeNode({
			  id:'c11',//子结点id
			  text:'结算业务分析'
			});
		 c11.on('click', function(){ 
			 fElementRemove();
			 fnViewLoader('custBusiAnal/account.js');
		 });
		 
		 var c12=new Ext.tree.TreeNode({
			  id:'c12',//子结点id
			  text:'产品推荐'
			});
		 var c13=new Ext.tree.TreeNode({
			  id:'c13',//子结点id
			  text:'热销产品'
			});
		 c13.on('click', function(){ 
			 fElementRemove();
			 fnViewLoader('demo/productAdvise1.js');
		 });
		 var c14=new Ext.tree.TreeNode({
			  id:'c14',//子结点id
			  text:'适合的产品'
			});
		 c14.on('click', function(){ 
			 fElementRemove();
			 fnViewLoader('demo/productAdvise.js');
		 });
		 var c15=new Ext.tree.TreeNode({
			  id:'c15',//子结点id
			  text:'贡献度信息'
			});
			 c15.on('click', function(){ 
				 fElementRemove();
				 fnViewLoader('contribution.js');
			 });
			 

		root.appendChild(c1);
		root.appendChild(rg);
		root.appendChild(c2);
		root.appendChild(c3);
		root.appendChild(c15);
		root.appendChild(c12);
		root.appendChild(c4);
		
		c4.appendChild(c5);
		c4.appendChild(c6);
		c4.appendChild(c7);
		c4.appendChild(c8);
		c4.appendChild(c9);
		c4.appendChild(c10);
		c4.appendChild(c11);
		c12.appendChild(c13);
		c12.appendChild(c14);
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
		title : '<span style="font-weight:normal">客户群成员视图菜单</span>',
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
			/* debugger;
			 if(Ext.fly('viewport_center').child('*')!=null)
				 Ext.fly('viewport_center').child('*').remove();*/
			 document.getElementById('viewport_center').innerHTML="";
		};
	
});