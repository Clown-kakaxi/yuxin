/*
 * 主页面
 * 创建人：KM
 * 时间：2012-06-27
 */
	Ext.onReady(function(){
		 var myData = [
				['CNJ2013021800024','圆胜电子科技(昆山)有限公司', '10168615','圆胜电子科技(昆山)有限公司','抵押贷款','小商品贸易','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.15%','0.16%','21350000','3000000','40000000','50000000','正常','100291','10000','2000000','21','222']
				 ,['CNJ2013021800025','苏州宇鑫电子有限公司南京办事处', '10168615','苏州宇鑫电子有限公司南京办事处','担保贷款','小商品贸易','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.16%','0.15%','21350000','3000000','40000000','50000000','次级','2998700','200000','2340000','34','2234']
				 ,['CNJ2013021800026','南京新月桑拿泳池设备有限公司', '10168615','南京新月桑拿泳池设备有限公司','担保贷款','小商品贸易','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.16%','0.15%','21350000','3000000','40000000','50000000','次级','2998700','200000','2340000','34','2234','3.50%']
				 ,['CNJ2013021800027','南京策杰贸易有限公司', '10168616','南京策杰贸易有限公司','定期三年存款','担保贷款','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.16%','0.15%','21350000','3000000','40000000','50000000','次级','2998700','200000','2340000','34','2234','3.50%']
				 ,['CNJ2013021800028','南京百世照明电器有限公司', '10168618','南京百世照明电器有限公司','助学贷款','小商品贸易','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.16%','0.15%','21350000','3000000','40000000','50000000','次级','2998700','200000','2340000','34','2234','3.50%']
				 ,['CNJ2013021800029','南京多美达广告展览有限责任公司', '10168619','南京多美达广告展览有限责任公司','公积金贷款','小商品贸易','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.16%','0.15%','21350000','3000000','40000000','50000000','次级','2998700','200000','2340000','34','2234','3.50%']
				 ,['CNJ2013021800030','南京望凯电光源有限公司', '10168621','南京望凯电光源有限公司','定期三年存款','小额贷款','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.16%','0.15%','21350000','3000000','40000000','50000000','次级','2998700','200000','2340000','34','2234','3.50%']
				 ];
				var cusStore = new Ext.data.ArrayStore({
		               fields: [
		                  {name: 'a1'},
		                  {name: 'a2'},
		                  {name: 'a3'},
		                  {name: 'a4'},
		                  {name: 'a5'},
		                  {name: 'a6'},
		                  {name: 'a7'},
		                  {name: 'a8'},
		                  {name: 'a9'},
		                  {name: 'a10'},
		                  {name: 'a11'},
		                  {name: 'a12'},
		                  {name: 'a13'},
		                  {name: 'a14'},
		                  {name: 'a15'},
		                  {name: 'a16'},
		                  {name: 'a17'},
		                  {name: 'a18'},
		                  {name: 'a19'},
		                  {name: 'a20'},
		                  {name: 'a21'},
		                  {name: 'a22'},
		                  {name: 'a23'},
		                  {name: 'a24'},
		                  {name: 'a25'}
		               ]
		           });
		            cusStore.loadData(myData);
		             var customergrid = new Ext.grid.GridPanel({
		             	layout:'fit',
		               store: cusStore,
		               viewConfig:{
//						   forceFit:true,
						   autoScroll:true
						},tbar:[ 
					        	{
									text : '创建商机',
									iconCls:'addIconCss',
									handler : function() {
									resetAddForm();
									addMyBusOpportInit01();	
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
						{header:'客户号',width:120,dataIndex:'a1'},
						{header:'客户名称',width:120,dataIndex:'a2'},
						{header:'账号',width:120,dataIndex:'a3'},
						{header:'账户名称',width:120,dataIndex:'a4'},
						{header:'产品名称',width:120,dataIndex:'a5'},
						{header:'产品分类名称',width:120,dataIndex:'a6'},
						{header:'账户性质',width:120,dataIndex:'a7'},
						{header:'账户状态',width:120,dataIndex:'a8'},
						{header:'开户网点名称',width:120,dataIndex:'a9'},
						{header:'开户日期',width:120,dataIndex:'a10'},
						{header:'销户日期',width:120,dataIndex:'a11'},
						{header:'计息方式',width:120,dataIndex:'a12'},
						{header:'基准利率',width:120,dataIndex:'a13'},
						{header:'执行利率',width:120,dataIndex:'a14'},
						{header:'账户余额',width:120,align:'right',renderer: money('0,000.00'),dataIndex:'a15'},
						{header:'账户月日均',width:120,align:'right',renderer: money('0,000.00'),dataIndex:'a16'},
						{header:'账户年日均',width:120,align:'right',renderer: money('0,000.00'),dataIndex:'a17'},
						{header:'账户季日均',width:120,align:'right',renderer: money('0,000.00'),dataIndex:'a18'},
						{header:'五级分类',width:120,dataIndex:'a19'},
						{header:'年累计利息收入',width:120,align:'right',renderer: money('0,000.00'),dataIndex:'a20'},
						{header:'月累计利息收入',width:120,align:'right',renderer: money('0,000.00'),dataIndex:'a21'},
						{header:'季累计利息收入',width:120,align:'right',renderer: money('0,000.00'),dataIndex:'a22'},
						{header:'贡献度',width:120,dataIndex:'a23'},
						{header:'模拟利润',width:120,dataIndex:'a24'},
						{header:'贴现利率',width:120,dataIndex:'a25'}
		               ],
		               stripeRows: true,
		               width: '150%',
		               height:200
		           });

	    
    var date= document.documentElement.clientHeight/1.6;
  
	// 布局模型
	var viewport = new Ext.Panel({
		 renderTo:'group_viewport_center',
		 height:document.body.scrollHeight-30,
		 width : document.body.clientWidth-200,
		 autoScroll:true,
		 items : [ {
			  xtype:'portal',
            	id:'center',
            	region:'center',
                items:[{
                    title: '贷款业务分析--列表',
                    columnWidth:1,
                	collapsible:true,
                    items:[customergrid]
                },{
              columnWidth:.5,
              collapsible:true,
              items:[{
                    collapsible:true,
                    style:'padding:0px 0px 0px 0px',
                    height:date,
                    width : (document.body.clientWidth-180)/2.1,
                   html:'<iframe id="contentFrame1" name="content1" height="300" frameborder="no" width="100%" src=\"../customerManager/customerBaseInformation/fusionchartsDemo/DragableCharts/LoanCol2.html\" "/> scrolling="no"> </iframe>'
                }]
              },{
              columnWidth:.5,
              collapsible:true,
              items:[{
                    collapsible:true,
                    style:'padding:0px 0px 0px 0px',
                    height:date,
                    width : (document.body.clientWidth-180)/2.1,
                   html:'<iframe id="contentFrame1" name="content1" height="300" frameborder="no" width="100%" src=\"../customerManager/customerBaseInformation/fusionchartsDemo/DragableCharts/LoanCol3.html\" "/> scrolling="no"> </iframe>'
                }]
              },{
              columnWidth:.5,
              collapsible:true,
              items:[{
                    collapsible:true,
                    style:'padding:0px 0px 0px 0px',
                    height:date,
                    width : (document.body.clientWidth-180)/2.1,
                   html:'<iframe id="contentFrame1" name="content1" height="300" frameborder="no" width="100%" src=\"../customerManager/customerBaseInformation/fusionchartsDemo/DragableCharts/GXD.html\" "/> scrolling="no"> </iframe>'
                }]
              },{
              columnWidth:.5,
              collapsible:true,
              items:[{
                    collapsible:true,
                    style:'padding:0px 0px 0px 0px',
                    height:date,
                    width : (document.body.clientWidth-180)/2.1,
                   html:'<iframe id="contentFrame1" name="content1" height="300" frameborder="no" width="100%" src=\"../customerManager/customerBaseInformation/fusionchartsDemo/DragableCharts/5LevB.html\" "/> scrolling="no"> </iframe>'
                }]
              },{
              columnWidth:.5,
              collapsible:true,
              items:[{
                    collapsible:true,
                    style:'padding:0px 0px 0px 0px',
                    height:date,
                    width : (document.body.clientWidth-180)/2.1,
                   html:'<iframe id="contentFrame1" name="content1" height="300" frameborder="no" width="100%" src=\"../customerManager/customerBaseInformation/fusionchartsDemo/DragableCharts/5LevKind.html\" "/> scrolling="no"> </iframe>'
                }]
              }]
            } ]
	});    	
});