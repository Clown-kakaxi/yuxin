	
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
//                html:' <p style="line-height:30px;font-size:14px;">&nbsp;&nbsp;&nbsp;&nbsp;个人理财是指根据财务状况，建立合理的个人财务规划，并适当参与投资活动。个人理财的投资包括：股票、基金、国债、储蓄等八个内容。现代意义的个人理财，不同于单纯的储蓄或投资，它不仅包括财富的积累，而且还囊括了财富的保障和安排。财富保障的核心是对风险的管理和控制，也就是当自己的生命和健康出现了意外，或个人所处的经济环境发生了重大不利变化，如恶性通货膨胀、汇率大幅降低等问题时，自己和家人的生活水平不致于受到严重的影响。</p>'
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
		},{title:"客户投资方式",layout:'border',items:[{xtype:'portal',
            id:'center',
            region:'center',
            items:[{
           	 columnWidth:1,
             border:false,
             autoHeight:true,
                 items:[investForm]
     },{
       	 columnWidth:1,
         border:false,
         autoHeight:true,
         items:[{
             collapsible:true,
             layout:'fit',
             title:'现金流分析',
             style:'padding:0px 0px 0px 0px',
             html:'<iframe id="contentFrame" name="content" height="290" frameborder="no" width="100%" src=\"chart/b.html\"  " scrolling="no"> </iframe>'
         }]
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
	             html:'<iframe id="contentFrame" name="content" height="290" frameborder="no" width="100%" src=\"chart/a.html\"  " scrolling="no"> </iframe>'
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
					cardPanel.getLayout().setActiveItem(2);
				}},{text : '保存',
					handler : function() {
						Ext.Msg.alert('提示','保存成功!');
					}},{
			  			text:'生成报告',
						handler:function(){
							var winPara = 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no,'+ ' scrollbars=no, resizable=no,location=no, status=no';
							var fileName = 'readme.txt';// 电子杂志文件名称
							var uploadUrl = basepath + '/AnnexeDownload?filename='+ fileName + '&annexeName=' + fileName;
							window.open(uploadUrl, '', winPara);
						}
			  }]
			}] 
				
			}
		]
		});
	//页面布局
	var addWindow = new Ext.Window({
		layout : 'fit',
		title:'投资规划新增',
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

