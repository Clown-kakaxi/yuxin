	debugger;
	var typeStore =  new Ext.data.ArrayStore({
		fields : [ 'key', 'value'  ],
		data : [ [ 1, '保守型' ], [ 2, '稳健型' ],[3,'平衡型'], [4,'成长型'],[5,'进取型']]
	});
	

	//客户信息form		
	var infoForm = new Ext.form.FormPanel( {
		labelWidth : 100,
		labelAlign : 'right',
		height:60,
		frame : true,
		region : 'north',
		autoScroll : true,
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			items : [new Com.yucheng.bcrm.common.CustomerQueryField({
				fieldLabel : '客户名称',
				labelWidth : 100,
				name : 'custName',
				custtype : '',// 客户类型:1:对私,2:对公,不设默认全部
				custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
				singleSelected : true,// 单选复选标志
				editable : false,
				allowBlank : false,
				anchor : '90%',
				hiddenName : 'custId',
				value:'李晓丽'
			})]
		},{
			columnWidth : .5,
			layout : 'form',
			items : [{
				fieldLabel : '规划名称',
				name : 'q3',
				value:'李晓丽_保险规划',
				xtype : 'textfield', 
				anchor : '90%'
			}]
		}]
	});
	
	
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
//		       {title:"",layout:'border',items:[{
//			xtype:'portal',
//            id:'center',
//            region:'center',
//            items:[{
//           	 columnWidth:1,
//             border:false,
//             autoHeight:true,
//             items:[{
//                 title: '',
//                 collapsible:true,
//                 layout:'fit',
//                 style:'padding:0px 0px 0px 0px',
//                 items:[infoForm]
//             }]
//            },{
//	            	 columnWidth:.25,
//		                border:false,
//		                autoHeight:true,
//		                items:[{
//		                    title: '养老保障',
//		                    collapsible:true,
//		                    layout:'fit',
//		                    style:'padding:0px 0px 0px 0px',
//		                    html:'<a href="#"  onClick="gotoPage(1)" ><img src=\"a.jpg\" />  </a>'
////		                    html:'<iframe id="contentFrame" name="content" height="330" frameborder="no" width="100%" src=\"a.jpg\"  " scrolling="no"> </iframe>'
//		                }]
//	            },{
//	            	 columnWidth:.25,
//		                border:false,
//		                autoHeight:true,
//		                items:[{
//		                    title: '教育保障',
//		                    collapsible:true,
//		                    layout:'fit',
//		                    style:'padding:0px 0px 0px 0px',
//		                    html:'<a href="#"  onClick="gotoPage(2)" ><img src=\"b.jpg\" />  </a>'
////		                    html:'<iframe id="contentFrame" name="content" height="330" frameborder="no" width="100%" src=\"b.jpg\" " scrolling="no"> </iframe>'
//		                }]
//	            },{
//	            	 columnWidth:.25,
//		                border:false,
//		                autoHeight:true,
//		                items:[{
//		                    title: '医疗保障',
//		                    collapsible:true,
//		                    layout:'fit',
//		                    style:'padding:0px 0px 0px 0px',
//		                    html:'<a href="#"  onClick="gotoPage(3)" ><img src=\"c.jpg\" />  </a>'
////		                    html:'<iframe id="contentFrame" name="content" height="330" frameborder="no" width="100%" src=\"c.jpg\" " scrolling="no"> </iframe>'
//		                }]
//	            },{
//	            	 columnWidth:.25,
//		                border:false,
//		                autoHeight:true,
//		                items:[{
//		                    title: '意外保障',
//		                    collapsible:true,
//		                    layout:'fit',
//		                    style:'padding:0px 0px 0px 0px',
//		                    html:'<a href="#"  onClick="gotoPage(4)" ><img src=\"d.jpg\" />  </a>'
////		                    html:'<iframe id="contentFrame" name="content" height="330" frameborder="no" width="100%" src=\"d.jpg\" " scrolling="no"> </iframe>'
//		                }]
//	            }
//            	
//            ]
//			
//		}]
//		},
	{
			title:"养老保障",layout:'border',items:[{
				xtype:'portal',
	            id:'center',
	            region:'center',
	            items:[{
	            	 columnWidth:1,
		                border:false,
		                autoHeight:true,
		                items:[{
		                	title:'介绍',
		                    collapsible:true,
		                    layout:'fit',
		                    style:'padding:0px 0px 0px 0px',
		                    html:' <p style="line-height:30px;font-size:14px;">&nbsp;&nbsp;&nbsp;&nbsp;养老保险，是为保障老年生活需求，提供养老金的退休养老保险类产品。 XXX养老险您量身定做最合适的养老保险计划，为您的晚年幸福生活提供最坚实的财务保障......</p>'
		    }]},{
	            	 columnWidth:1,
		                border:false,
		                autoHeight:true,
		               items:[oldform]
		    }],
			buttonAlign : 'center',
			buttons : [{text : '保存并下一步',
				handler : function() {
					cardPanel.getLayout().setActiveItem(1);
				}}]
			}] 
				
			},{
				title:"教育保障",layout:'border',items:[{
					xtype:'portal',
		            id:'center',
		            region:'center',
		            items:[{
		            	 columnWidth:1,
			                border:false,
			                title:'各国学费介绍',
			                autoHeight:true,
			                    items:[eduGrid]
			    },{
		            	 columnWidth:1,
			                border:false,
			                autoHeight:true,
			               items:[eduInfo]
			    }],
				buttonAlign : 'center',
				buttons : [
				           {text : '上一步',
				handler : function() {
					cardPanel.getLayout().setActiveItem(0);
				}},{text : '保存并下一步',
					handler : function() {
						cardPanel.getLayout().setActiveItem(2);
					}}]
				}] 
					
				},{
					title:"医疗保障",layout:'border',items:[{
						xtype:'portal',
			            id:'center',
			            region:'center',
			            items:[{
			            	 columnWidth:1,
				                border:false,
				                autoHeight:true,
				                items:[{
				                	title:'介绍',
				                    collapsible:true,
				                    layout:'fit',
				                    style:'padding:0px 0px 0px 0px',
				                    html:' <p style="line-height:30px;font-size:14px;">&nbsp;&nbsp;&nbsp;&nbsp;医疗保险，是为保障家庭医疗需求，提供医疗金的保险类产品。 XXX医疗保险为您量身定做最合适的医疗保险计划，为您的家庭幸福生活提供最坚实的财务保障......</p>'
				    }]},{
			            	 columnWidth:1,
				                border:false,
				                autoHeight:true,
				                    items:[healthform]
				    }],
					buttonAlign : 'center',
					buttons : [{text : '上一步',
						handler : function() {
							cardPanel.getLayout().setActiveItem(1);
						}},{text : '保存并下一步',
						handler : function() {
							cardPanel.getLayout().setActiveItem(3);
						}}]
					}] 
						
					},{
						title:"意外保障",layout:'border',items:[{
							xtype:'portal',
				            id:'center',
				            region:'center',
				            items:[{
				            	 columnWidth:1,
					                border:false,
					                autoHeight:true,
					                items:[{
					                	title:'介绍',
					                    collapsible:true,
					                    layout:'fit',
					                    style:'padding:0px 0px 0px 0px',
					                    html:' <p style="line-height:30px;font-size:14px;">&nbsp;&nbsp;&nbsp;&nbsp;意外保险，是为保障家庭成员各种意外情况下的费用需求，提供医疗金，理赔金等保障的保险类产品。 XXX意外保险为您量身定做最合适的意外保险计划，为您的家庭幸福生活提供最坚实的财务保障......</p>'
					    }]},{
				            	 columnWidth:1,
					                border:false,
					                autoHeight:true,
					                    items:[accidentform]
					   }],
						buttonAlign : 'center',
						buttons : [{text : '上一步',
							handler : function() {
								cardPanel.getLayout().setActiveItem(2);
							}},{text : '保存并下一步',
							handler : function() {
								cardPanel.getLayout().setActiveItem(4);
							}}]
						}] 
							
						},{
							title:"投保回顾",layout:'border',items:[{
								xtype:'portal',
					            id:'center',
					            region:'center',
					            items:[{
					            	 columnWidth:1,
						                border:false,
						                autoHeight:true,
						                    items:[insuranceform]
						    },{
					            	 columnWidth:.6,
						                border:false,
//						                height : 200,
						                title:"已有保障情况",
						                items:[{
						                    collapsible:true,
						                    layout:'fit',
						                    height : 200,
						                    style:'padding:0px 0px 0px 0px',
						                    items:[insuranceGrid]
						    }]
						                    
						    },{
				            	 columnWidth:.4,
					                border:false,
					                autoHeight:true,
					                items:[{
					                    collapsible:true,
					                    layout:'fit',
					                    style:'padding:0px 0px 0px 0px',
					                    html:'<iframe id="contentFrame" name="content" height="200" frameborder="no" width="100%" src=\"chart/b.html\" " scrolling="no"> </iframe>'
					    }]},{
			            	 columnWidth:1,
				                border:false,
				                autoHeight:true,
				                items:[{
				                	title:'说明',
				                    collapsible:true,
				                    layout:'fit',
				                    style:'padding:0px 0px 0px 0px',
				                    html:'&nbsp;&nbsp;&nbsp;&nbsp;欠缺 - 您还没有考虑该项责任</p>&nbsp;&nbsp;&nbsp;&nbsp;不足 - 您的实际保额小于建议保额</p>&nbsp;&nbsp;&nbsp;&nbsp;合理 - 您的实际保额与建议保额相符</p>&nbsp;&nbsp;&nbsp;&nbsp;充足 - 您的实际保额大于建议保额</p>'
				    }]}],
							buttonAlign : 'center',
							buttons : [
							           {text : '上一步',
										handler : function() {
											cardPanel.getLayout().setActiveItem(3);
										}},
									{text : '保存并下一步',
									handler : function() {
									cardPanel.getLayout().setActiveItem(5);
								}}]
							}] 
								
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
						        		   cardPanel.getLayout().setActiveItem(4);
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
	var addWindow = new Ext.Window({
		layout : 'fit',
		title:'保险规划新增',
		 autoScroll : true,
		    draggable : true,// 是否可以拖动
			closable : true,// 是否可关闭
			modal : true,
			closeAction : 'hide',
	    modal : true,
	    width : 800,
	    height : 410,
	    loadMask : true,
	    border : false,
	    items : [ {
	        buttonAlign : "center",
	        layout : 'fit',
	        items : [cardPanel]
	    }]
	});
