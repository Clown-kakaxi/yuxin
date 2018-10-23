	 //主体card布局
	var cardPanel=new Ext.Panel({
		layout:"card",
		id:'card',
		activeItem: 0,
		autoScroll:true,
		layoutConfig: {
		animate: true 
		},
		items:[
//		{title:"规划愿景",layout:'border',items:[{xtype:'portal',
//            id:'center',
//            region:'center',
//            items:[{
//                collapsible:true,
//                layout:'fit',
//                style:'padding:0px 0px 0px 0px',
//                html:' <p style="line-height:30px;font-size:14px;">&nbsp;&nbsp;&nbsp;&nbsp;您是否还在为孩子的教育费用而发愁，您是否还在为家庭收支平衡问题而忧心，您是否还在为以后的退休生活而担忧，您的家庭与个人财务需求会随着不同的人生阶段而变化，因此您需要尽早规划未来，充分利用现有资源。XX银行财富管理中心采用理财顾问式服务聆听您的财富需求，精心挑选适合您在各个人生阶段中不同的综合理财规划方案。我们因您而更加专业，您因我们而更加富裕、快乐，给我们一个机会，助您把握明天、成就未来。</p>'
//            }]
//		}],
//		buttonAlign : 'center',
//		buttons : [{text : '规划创建',
//			handler : function() {
//				cardPanel.getLayout().setActiveItem(1);
//			}}]
//		},
		{title:"规划创建",layout:'border',items:[{xtype:'portal',
            id:'center',
            region:'center',
            items:[{
           	 columnWidth:1,
             border:false,
             autoHeight:true,
                 items:[infoForm]
     }]
		}],
		buttonAlign : 'center',
		buttons : [/*{text : '上一步',
			handler : function() {
				cardPanel.getLayout().setActiveItem(0);
			}},*/{text : '保存并下一步',
			handler : function() {
				cardPanel.getLayout().setActiveItem(1);
			}}]
		},{title:"客户生活目标",layout:'border',items:[{xtype:'portal',
            id:'center',
            region:'center',
            items:[{
           	 columnWidth:1,
             border:false,
             autoHeight:true,
                 items:[targetForm]
     }]
		}],
		buttonAlign : 'center',
		buttons : [{text : '上一步',
			handler : function() {
				cardPanel.getLayout().setActiveItem(0);
			}},{text : '保存并下一步',
			handler : function() {
				cardPanel.getLayout().setActiveItem(2);
			}}]
		},
		{title:"策略选择",layout:'border',items:[{xtype:'portal',
            id:'center',
            region:'center',
            items:[{
           	 columnWidth:1,
             border:false,
             autoHeight:true,
                 items:[wayForm]
     },{
       	 columnWidth:1,
         border:false,
         autoHeight:true,
         items:[{
             collapsible:true,
             layout:'fit',
             title:'现金流分析',
             style:'padding:0px 0px 0px 0px',
             html:'<iframe id="contentFrame" name="content" height="290" frameborder="no" width="100%" src= \"../investPlan/chart/b.html\"  " scrolling="no"> </iframe>'
         }]
 }]
		}],
		buttonAlign : 'center',
		buttons : [{text : '上一步',
			handler : function() {
				cardPanel.getLayout().setActiveItem(1);
			}},{text : '保存并下一步',
			handler : function() {
				cardPanel.getLayout().setActiveItem(3);
			}}]
		},{
			title:"资产配置调整",layout:'border',items:[{xtype:'portal',
	            id:'center',
	            region:'center',
	            items:[{
	           	 columnWidth:1,
	             border:false,
	             autoHeight:true,
	                 items:[adjustForm]
	     },{
	       	 columnWidth:1,
	         border:false,
	         autoHeight:true,
	         items:[{
	             collapsible:true,
	             layout:'fit',
	             style:'padding:0px 0px 0px 0px',
	             html:'<iframe id="contentFrame" name="content" height="290" frameborder="no" width="100%" src=\"../investPlan/chart/a.html\"  " scrolling="no"> </iframe>'
	         }]
	 }]
			}],
			buttonAlign : 'center',
			buttons : [{text : '上一步',
				handler : function() {
					cardPanel.getLayout().setActiveItem(2);
				}},{text : '保存并下一步',
				handler : function() {
					cardPanel.getLayout().setActiveItem(4);
				}}]
			
		},{

			title:"产品推荐",layout:'border',items:[{
				xtype:'portal',
	            id:'center',
	            region:'center',
	            items:[{
	            	 columnWidth:1,
		                border:false,
		                autoHeight:true,
		                    items:[productGrid]
		   }],
			buttonAlign : 'center',
			buttons : [{text : '上一步',
				handler : function() {
					cardPanel.getLayout().setActiveItem(3);
				}},{text : '保存',
					handler : function() {
						Ext.Msg.alert('提示','保存成功!');
					}},{
			  			text:'生成报告',
						handler:function(){
							window.open( basepath+'/TempDownload?filename=personReport.pdf','', 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
						}
			  }]
			}] 
				
			
		}
		]
	});
	
	 var addWindow = new Ext.Window({
			layout : 'fit',
			title:'综合规划新增',
			 autoScroll : true,
			    draggable : true,// 是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
		    modal : true,
		    width : 600,
		    height : 400,
		    loadMask : true,
		    border : false,
		    items : [ {
		        buttonAlign : "center",
		        layout : 'fit',
		        items : [cardPanel]
		    }]
		});