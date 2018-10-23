   //定义树的跟节点
   var root=new Ext.tree.TreeNode({
          id:"root",//根节点id
   draggable:false,
          text:"菜单"
    });
    //定义树节点
	var p1=new Ext.tree.TreeNode({
	id:'p1',//子结点id
	text:'客户基本信息'
	});
	 p1.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/customerBaseInformation/perCustomerBase.js');
	 });
	var p2=new Ext.tree.TreeNode({
	id:'p2',//子结点id
	text:'客户家庭信息'
	});
	 p2.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/customerBaseInformation/perCustomerFamilyInfo.js');
	 });
	/*
	var p3=new Ext.tree.TreeNode({
	  id:'p3',//子结点id
	  text:'重要社会关系人信息'
	});
	*/
	var p4=new Ext.tree.TreeNode({
	  id:'p4',//子结点id
	  text:'客户财务信息'
	});
	 p4.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/perCustomerFinancialInformation.js');
	 });
	var p5=new Ext.tree.TreeNode({
	  id:'p5',//子结点id
	  text:'归属信息'
	});
	p5.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/customerBelongInfo.js');
	 });
	var p7=new Ext.tree.TreeNode({
		  id:'p7',//子结点id
		  text:'关联客户信息'
		});
	 p7.on('click', function(){
		 if(Ext.getCmp('rel_form_1')){
		 	Ext.getCmp('rel_form_1').destroy();
		 }
		  if(Ext.getCmp('rel_form_2')){
		 	Ext.getCmp('rel_form_2').destroy();
		 }
		  if(Ext.getCmp('rel_form_3')){
		 	Ext.getCmp('rel_form_3').destroy();
		 }
		  if(Ext.getCmp('rel_form_4')){
		 	Ext.getCmp('rel_form_4').destroy();
		 }
		  if(Ext.getCmp('rel_form_5')){
		 	Ext.getCmp('rel_form_5').destroy();
		 }
		 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/relCustInfo.js');
	 });
	var p6=new Ext.tree.TreeNode({
	  id:'p6',//子结点id
	  text:'客户业务信息'
	});
	
	var p60=new Ext.tree.TreeNode({
		  id:'p60',//子结点id
		  text:'客户业务概览'
		});
	p60.on('click', function(){ 
		fElementRemove();
		fnViewLoader(basepath+'/contents/pages/customer/customerManager/customBusinessView.js');
	});

	var p61=new Ext.tree.TreeNode({
		  id:'p61',
		  text:'账户信息'
	});
	p61.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/informationOnBusinessCooperation.js');
	 });
	
	var p62=new Ext.tree.TreeNode({
	  id:'p62',
	  text:'产品信息'
	});	
	p62.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/customerBaseInformation/customerPoroductInformation2.js');
	 });
	var p63=new Ext.tree.TreeNode({
		  id:'p63',//子结点id
		  text:'贷记卡信息'
	});
	p63.on('click', function(){ 
//		alert(Ext.getCmp('listPanel'));
		 if(Ext.getCmp('listPanel')){
			 	Ext.getCmp('listPanel').destroy();
			 }
		fElementRemove();
		fnViewLoader(basepath+'/contents/pages/customer/customerManager/perCustomerCardInformation.js');
	});
	var p64=new Ext.tree.TreeNode({
		  id:'p64',
		  text:'授信信息'
	});
	p64.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/customerBaseInformation/customerCredit.js');
	 });
	var p65=new Ext.tree.TreeNode({
		  id:'p65',
		  text:'担保信息'
	});
	p65.on('click', function(){
		 document.getElementById('viewport_center').innerHTML="";
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/warrant.js');
	 });
	var p66=new Ext.tree.TreeNode({
		  id:'p66',
		  text:'存贷比信息'
		});
	p66.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/customerBaseInformation/depositCreditInfromattion.js');
	 });
	var p67=new Ext.tree.TreeNode({
		  id:'p67',//子结点id
		  text:'存贷挂钩信息'
	});
	p67.on('click', function(){ 
		fElementRemove();
		fnViewLoader(basepath+'/contents/pages/customer/customerManager/depLnAsoct.js');
	});
	
	var p68=new Ext.tree.TreeNode({
		  id:'p68',//子结点id
		  text:'客户业务渠道分析'
	});
	p68.on('click', function(){ 
		fElementRemove();
		fnViewLoader(basepath+'/contents/pages/customer/customerManager/channelAnalysis.js');
	});
	var p69=new Ext.tree.TreeNode({
		  id:'p69',//子结点id
		  text:'客户支付管理信息'
	});	
	p69.on('click', function(){ 
		fElementRemove();
		fnViewLoader(basepath+'/contents/pages/customer/customerManager/payManager.js');
	});
	
	/*var p66=new Ext.tree.TreeNode({
	  id:'p66',
	  text:'签约信息'
	});*/
	
	var p8=new Ext.tree.TreeNode({
		  id:'p8',//子结点id
		  text:'客户积分信息'
		});
	p8.on('click', function(){ 
		fElementRemove();
		fnViewLoader(basepath+'/contents/pages/customer/customerManager/custIntegralQuery.js');
	});
	var p9=new Ext.tree.TreeNode({
		  id:'p9',//子结点id
		  text:'客户评级信息'
		});
	p9.on('click', function(){ 
		fElementRemove();
		fnViewLoader(basepath+'/contents/pages/customer/customerManager/customerBaseInformation/clientGradeInformation.js');
	});
	var p10=new Ext.tree.TreeNode({
		  id:'p10',//子结点id
		  text:'客户需求信息'
		});
	p10.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/customerBaseInformation/perCustomerNeedInformation.js');
	 });
	var p11=new Ext.tree.TreeNode({
	  id:'p11',//子结点id
	  text:'客户贡献度信息'
	});
	p11.on('click', function(){ 
		 fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/contribution.js');
	 });
	var p12=new Ext.tree.TreeNode({
		  id:'p12',//子结点id
		  text:'客户他行信息'
		});
	p12.on('click', function(){
		 if(Ext.getCmp('panel2012')){
			 	Ext.getCmp('panel2012').destroy();
			 }
		 document.getElementById('viewport_center').innerHTML="";
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/customerBaseInformation/otherBank.js');
	});	
	var p13=new Ext.tree.TreeNode({
		  id:'p13',//子结点id
		  text:'客户诉讼信息'
	});
	p13.on('click', function(){
		 document.getElementById('viewport_center').innerHTML="";
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/customerBaseInformation/customerLawInfoShow.js');
	});	
	var p14=new Ext.tree.TreeNode({
	  id:'p14',//子结点id
	  text:'客户电子管理信息'
	});
	p14.on('click', function(){
		fElementRemove();
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/elecMgrInfo.js');
	 });
	var p21=new Ext.tree.TreeNode({
		  id:'p21',//子结点id
		  text:'客户事件信息'
		});
	p21.on('click', function(){
		 document.getElementById('viewport_center').innerHTML="";
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/customerBaseInformation/eventInformation.js');
	 });		
	var p22=new Ext.tree.TreeNode({
	  id:'p22',//子结点id
	  text:'黑名单客户信息'
	});

	p22.on('click', function(){
		 document.getElementById('viewport_center').innerHTML="";
		 fnViewLoader(basepath+'/contents/pages/customer/customerManager/customerBaseInformation/customerBlackListShow.js');
	 });
	root.appendChild(p1);//为根节点增加子结点p1
	root.appendChild(p2);
	//root.appendChild(p3);
	root.appendChild(p4);
	root.appendChild(p5);
	root.appendChild(p6);
	root.appendChild(p7);
	root.appendChild(p8);
	root.appendChild(p9);
	root.appendChild(p10);
	root.appendChild(p11);
	root.appendChild(p12);
	root.appendChild(p13);
	root.appendChild(p14);
	root.appendChild(p21);
	root.appendChild(p22);
	p6.appendChild(p60);
	p6.appendChild(p61);
	p6.appendChild(p62);
	p6.appendChild(p63);
	p6.appendChild(p64);
	p6.appendChild(p65);
	p6.appendChild(p66);
	p6.appendChild(p67);
	p6.appendChild(p68);
	p6.appendChild(p69);

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
		title : '<span style="font-weight:normal">对私客户全景视图菜单</span>',
			items: [{ 
			    margins: '0 0 0 0',
			    items : [tree]
		    }] 
		});
	
	root.on('beforechildrenrendered', function(){
		 fElementRemove();
		 fnViewLoader('customerBaseInformation/perCustomerBase.js');
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
