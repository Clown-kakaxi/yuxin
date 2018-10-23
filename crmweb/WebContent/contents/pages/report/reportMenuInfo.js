Ext.onReady(function(){
	
	
	var sJsName="";
	 
	 var fnViewLoader= function(sJsName){
		 Ext.ScriptLoader.loadScript({        
				scripts: [sJsName]
			}); 	
	 };
	 
   //定义树的跟节点
   var root=new Ext.tree.AsyncTreeNode({
	    id:"root",
	    draggable : false,
	    expanded : true,
	    expandable: true,
	    text:"菜单",
	    children:[
	     {
	      text:"统计分析",
	      children:[
	       {text:"客户结构分析",id:"r1",leaf:false,
	    	   		children:[
	    	    	     {text:"全辖客户数量及分类",id:'r11',leaf:true,
	    	    	    	 url:basepath+'/contents/pages/report/customerAccountType.js',
	    	    	    	 listeners:{'click':{fn:r}}	 
	    	    	     },
	    	    	      {text:"全辖客户结构分析",id:'r12',leaf:true,
	    	    	    	 url:basepath+'/contents/pages/report/customerFullJurisdictAnalysis.js',
	    	    	    	 listeners:{'click':{fn:r}} 
	    	    	      },
	    	    	      {text:"营业网点业务情况分析",id:'r13',leaf:true,
	    	    	    	  url:basepath+'/contents/pages/report/outletsBusinessAnalysis.js',
		    	    	    	 listeners:{'click':{fn:r}} 
		    	    	      }
	    	    	      ]   
	       },
	       {text:"有效户分析",id:"r2",leaf:false,
	    	        children:[
     /*	    	{text:"设定有效客户标准",id:'r21',leaf:true,
	    	    	    	 url:basepath+'/contents/pages/report/effectiveCustomerStandards.js',
	    	    	    	 listeners:{'click':{fn:r}}	 
    	    	     },*/	
	    	    	      {text:"有效户客户分析",id:'r22',leaf:true,
	    	    	    	 url:basepath+'/contents/pages/report/effectiveCustomerAnalysis.js',
	    	    	    	 listeners:{'click':{fn:r}} 
	    	    	      },
	    	    	      {text:"新增有效户统计分析表",id:'r23',leaf:true,
	    	    	    	  url:basepath+'/contents/pages/report/addEffectiveCustomerAnalysis.js',
		    	    	    	 listeners:{'click':{fn:r}} 
		    	    	  }/*,
	    	    	      {text:"退化有效户统计分析表",id:'r24',leaf:true,
		    	    		  url:basepath+'/contents/pages/report/degenerationEffectiveCustomerAnalysis.js',
		    	    	    	 listeners:{'click':{fn:r}} 
		    	    	      }*/
	    	    	      ]
	    	   },
	    	   {text:"存款规模",id:"r3",leaf:false,
	    	        children:[
	    	    	      {text:"存款规模报表分析",id:'r31',leaf:true,
	    	    	    	 url:basepath+'/contents/pages/report/org_dep.js',
	    	    	    	 listeners:{'click':{fn:r}} 
	    	    	      }
	    	    	      ]
	    	   }
	      ]
	     }
	    ]
	   });

   
		   function r(node,event){
			   Ext.fly('viewport_center').dom.innerHTML='';
			   fnViewLoader(node.attributes.url);
			   }
		   
	

		
			//生成树形面板
			var tree=new Ext.tree.TreePanel({
				autoScroll:true,
			    root:root,//定位到根节点
			    animate:true,//开启动画效果
			    enableDD:false,//允许子节点拖动
			    border:false,//没有边框
			    containerScroll: true,
			    height:400,
			    listeners :{
				'load':function()
				{tree.expandAll();}
			      }
			    	,
			    rootVisible:false//设为false将隐藏根节点，很多情况下，我们选择隐藏根节点增加美观性
			 });
			var viewport_left = new Ext.Panel({
				frame:true,
				renderTo:'sena_tree',
//				height:document.body.scrollHeight,
				layout:'fit',
				autoScroll:true,
				title : '<span style="font-weight:normal">统计报表菜单</span>',
					items: [{ 
					    margins: '0 0 0 0',
					    items : [tree]
				    }] 
				});
	
});