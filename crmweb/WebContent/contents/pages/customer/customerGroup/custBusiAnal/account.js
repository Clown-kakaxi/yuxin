Ext.onReady(function(){
		 var myData = [
		               ['国际结算','301,650,000.00', '0.18','23,000,000.00','0.4','180,000,000.00','0.23','3,000,000.00'],
		               ['票据业务','101,630,000.00', '0.14','22,000,000.00','0.5','120,000,000.00','0.17','2,000,000.00'],
		               ['网上银行','201,630,000.00', '0.15','25,000,000.00','0.3','150,000,000.00','0.18','21,800,000.00'],
		               ['辖内转账','251,670,000.00', '0.15','25,000,000.00','0.3','150,000,000.00','0.18','21,800,000.00'], 
		               ['大小额汇款','291,670,000.00', '0.19','22,000,000.00','0.3','290,000,000.00','0.18','34,800,000.00'],
		               ['同城业务','321,670,000.00', '0.26','22,000,000.00','0.4','170,000,000.00','0.18','14,800,000.00']
		               ];
		           var cusStore = new Ext.data.ArrayStore({
		               fields: [
		                  {name: 'a0'},      
		                  {name: 'a1'},
		                  {name: 'a2'},
		                  {name: 'a3'},
		                  {name: 'a4'},
		                  {name: 'a5'},
		                  {name: 'a6'},
		                  {name: 'a7'}
		               ]
		           });
		            cusStore.loadData(myData);
		             var customergrid = new Ext.grid.GridPanel({
		             	layout:'fit',
		               store: cusStore,
		               viewConfig:{
						   autoScroll:true
						},
						tbar:[ 
					        	{
					text : '创建商机',
					iconCls:'addIconCss',
					handler : function() {
					resetAddForm();
					addMyBusOpportInit02();	
					}
				},{
		            text : '生成营销活动',
		            iconCls:'addIconCss',
		            handler : function() {
		            	
					addActivityForm.form.reset();
					addActivityProdForm.form.reset();
					addActivityCustForm.form.reset();
					addActivityForm.form.findField('createUser').setValue(__userId);
					addActivityForm.form.findField('test').setValue(__userName);
					addActivityForm.form.findField('createDate').setValue(new Date());
					addActivityForm.form.findField('mktActiStat').setValue(1);
					addActivityForm.form.findField('mktActiName').setValue('小企业扶持贷款推广');
					addActivityForm.form.findField('mktActiType').setValue('推广活动');
					addActivityForm.form.findField('mktActiMode').setValue('宣传');
					addActivityForm.form.findField('mktActiTeam').setValue('小企业贷款组');
					addActivityForm.form.findField('mktActiCost').setValue('1000');
					addActivityForm.form.findField('mktActiAddr').setValue('南京市建邺区应天西路所叶路20号');
					addActivityForm.form.findField('mktActiCont').setValue('宣传小企业的扶持贷款政策，吸引贷款');
					addActivityForm.form.findField('actiCustDesc').setValue('该工业园区的小企业');
					addActivityForm.form.findField('actiOperDesc').setValue('本行支行客户经理');
					addActivityForm.form.findField('actiProdDesc').setValue('小企业扶持到款');
					addActivityForm.form.findField('mktActiAim').setValue('推广');
					addActivityForm.form.findField('actiRemark').setValue('无');
							 				
					addActivityWindow.show();

		            }}
					      	],
		               columns: [
		                         {header:'业务品种',width:100,dataIndex:'a0'},
		                         {header:'业务发生额',width:150,align:'right',renderer: money('0,000.00'),dataIndex:'a1'},
		                         {header:'发生额占比',width:100,dataIndex:'a2'},
		                         {header:'中间业务收入',width:100,align:'right',renderer: money('0,000.00'),dataIndex:'a3'},
		                         {header:'中间业务收入占比',width:150,dataIndex:'a4'},
		                         {header:'贡献度',width:100,align:'right',dataIndex:'a5'},
		                         {header:'贡献度占比',width:100,dataIndex:'a6'},
		                         {header:'客户数量',width:100,align:'right',dataIndex:'a7'}
		                         
		               ],
		               stripeRows: true,
		               height:200
		           });


	// 布局模型
	var viewport = new Ext.Panel({
		 renderTo:'group_viewport_center',
		 height:document.body.scrollHeight-30,
		  width : document.body.clientWidth-200,
		 layout : 'fit',
		 autoScroll:true,
		 items : [ {
			 
                columnWidth:1,
                autoHeight:true,
                //layout:'fit',
                border:false,
                items:[{
                    title: '结算业务',
//                    layout:'fit',
//                    style:'padding:0px 0px 0px 0px',
                    height:250,
                	collapsible:true,
                	autoScroll:true,
                    items:[customergrid]
                }]
            }, 
            
            {
              	 columnWidth:1,
                 autoHeight:true,
                 border:false,
                 items:[{
                	 title: '客户结算业务规模分析',
                 	layout:'fit',
                 	collapsible:true,
            		    html:'<iframe id="contentFrame2" name="content2" height="300" frameborder="no" width="100%" src=\"../customerGroup/custBusiAnal/fusionchartsDemo/DragableCharts/account.html\" "/> scrolling="no"> </iframe>'
                 }]
             }]
	});    	
});