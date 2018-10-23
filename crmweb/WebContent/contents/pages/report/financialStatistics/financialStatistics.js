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
		    	 text:"理财报表统计分析",
		    	 children:[
		    	           {text:"个人理财产品",id:"r1",leaf:false,
		    	        	   children:[
		    	        	             {text:"个人理财产品发行募集及余额情况统计",id:'r11',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/perCollectSave.js',
		    	        	            	 listeners:{'click':{fn:r}}	 
		    	        	             },
		    	        	             {text:"个人理财产品到期收益情况汇总表",id:'r12',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/yieldCondition.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             },
		    	        	             {text:"个人理财亏损产品明细表",id:'r13',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/lossProduct.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             }
		    	        	             ]   
		    	           },
		    	           {text:"金融机构理财产品",id:"r2",leaf:false,
		    	        	   children:[
		    	        	             {text:"金融机构理财产品当季发售情况明细表",id:'r21',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/seasonSaleDetails.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             },
		    	        	             {text:"金融机构理财产品发售和到期情况汇总表",id:'r22',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/saleMaturity.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             },
		    	        	             {text:"金融机构理财产品当季募集资金投向结构表",id:'r23',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/seasonRaiseInvest.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             },
		    	        	             {text:"金融机构理财产品当季到期收益情况表",id:'r24',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/seasonYield.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             },
		    	        	             {text:"金融机构理财产品风险状况调查表",id:'r25',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/riskSurvey.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	      	  	             }
		    	        	             ]
		    	           },
		    	           {text:"商业银行个人理财产品",id:"r3",leaf:false,
		    	        	   children:[
		    	        	             {text:"商业银行非保本个人理财产品调查表",id:'r31',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/nonGuarantePer.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             },
		    	        	             {text:"商业银行个人理财产品调查表",id:'r32',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/perFinancialProd.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             },
		    	        	             {text:"商业银行个人理财产品统计表",id:'r33',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/perFinaProdCount.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             }
		    	        	             ]
		    	           },
		    	           {text:"理财业务统计",id:"r4",leaf:false,
		    	        	   children:[
		    	        	             {text:"理财业务统计表",id:'r41',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/manBussStatis.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             }
		    	        	             ]
		    	           },
		    	           {text:"商业银行理财产品运行数据",id:"r5",leaf:false,
		    	        	   children:[
		    	        	             {text:"资产池类封闭式商业银行理财产品运行数据信息统计表",id:'r51',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/assCloseComBak.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             },
		    	        	             {text:"资产池类开放式商业银行理财产品运行数据统计表",id:'r52',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/assOpenComBak.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             },
		    	        	             {text:"非资产池类封闭式商业银行理财产品运行数据统计表",id:'r53',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/noAssColseComBak.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             },
		    	        	             {text:"非资产池类开放式商业银行理财产品运行数据统计表",id:'r54',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/noAssOpenComBak.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             }
		    	        	             ]
		    	           },
		    	           {text:"理财产品金额份额",id:"r6",leaf:false,
		    	        	   children:[
		    	        	             {text:"理财产品起始募集金额份额",id:'r61',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/finStaProdStart.js',
		    	        	            	 listeners:{'click':{fn:r}} 
		    	        	             },
		    	        	             {text:"理财产品当期兑付金额份额",id:'r62',leaf:true,
		    	        	            	 url:basepath+'/contents/pages/report/financialStatistics/finStaProdCurrPay.js',
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
		listeners :{
		'load':function(){
			tree.expandAll();
			}
		},
		rootVisible:false//设为false将隐藏根节点，很多情况下，我们选择隐藏根节点增加美观性
	});
	var viewport_left = new Ext.Panel({
		frame:true,
		renderTo:'sena_tree',
//		height:document.body.scrollHeight,
		layout:'fit',
		autoScroll:true,
		title : '<span style="font-weight:normal">理财报表统计分析菜单</span>',
		items: [{ 
			margins: '0 0 0 0',
			items : [tree]
		}] 
	});
});