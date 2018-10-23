/*
 * 主页面
 * 创建人：KM
 * 时间：2012-06-27
 */
	Ext.onReady(function(){
		 var myData = [
					['CNJ2013021800001','南京春辉科技实业有限公司', '10168592','南京春辉科技实业有限公司','定期三年存款','小商品贸易','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.15%','21350000','3000000','40000000','50000000','34','34'],
					['CNJ2013021800002','南京光浦电光源材料有限公司', '10168593','南京光浦电光源材料有限公司','定期三年存款','小商品贸易','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.15%','21350000','3000000','40000000','50000000','34','34'],
					['CNJ2013021800037','南京苏迪电源有限公司', '10168628','南京苏迪电源有限公司','定期三年存款','小商品贸易','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.15%','21350000','3000000','40000000','50000000','34','34'],
					['CNJ2013021800038','镇江市大江灯泡材料厂', '10168629','镇江市大江灯泡材料厂','定期三年存款','小商品贸易','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.15%','21350000','3000000','40000000','50000000','34','34'],
					['CNJ2013021800039','南京白石传媒制作有限公司', '10168639','南京白石传媒制作有限公司','定期三年存款','小商品贸易','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.15%','21350000','3000000','40000000','50000000','34','34'],
					['CNJ2013021800040','宁伟广告公司', '10168631','宁伟广告公司','定期三年存款','小商品贸易','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.15%','21350000','3000000','40000000','50000000','34','34'],
					['CNJ2013021800041','南京绍博光电技术开发中心', '10168632','南京绍博光电技术开发中心','定期两年存款','小商品贸易','定期','正常','南京银行光华支行','2011-02-12','2012-02-14','0.15%','0.15%','21350000','3000000','40000000','50000000','34','34']
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
		                  {name: 'a19'}
		               ]
		           });
		            cusStore.loadData(myData);
		             var customergrid = new Ext.grid.GridPanel({
		               layout:'fit',
		               store: cusStore,
		               viewConfig:{
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
		                         {header:'客户号',width:300,dataIndex:'a1'},
		                         {header:'客户名称',width:300,dataIndex:'a2'},
		                         {header:'账号',width:300,dataIndex:'a3'},
		                         {header:'账户名称',width:300,dataIndex:'a4'},
		                         {header:'产品名称',width:300,dataIndex:'a5'},
		                         {header:'产品分类名称',width:300,dataIndex:'a6'},
		                         {header:'账户性质',width:300,dataIndex:'a7'},
		                         {header:'账户状态',width:300,dataIndex:'a8'},
		                         {header:'开户网点名称',width:300,dataIndex:'a9'},
		                         {header:'开户日期',width:300,dataIndex:'a10'},
		                         {header:'销户日期',width:300,dataIndex:'a11'},
		                         {header:'计息方式',width:300,dataIndex:'a12'},
		                         {header:'基准利率',width:300,dataIndex:'a13'},
		                         {header:'执行利率',width:300,dataIndex:'a14'},
		                         {header:'账户月日均',width:300,dataIndex:'a15'},
		                         {header:'账户年日均',width:300,dataIndex:'a16'},
		                         {header:'账户季日均',width:300,dataIndex:'a17'},
		                         {header:'贡献度',width:300,dataIndex:'a18'},
		                         {header:'模拟利润',width:300,dataIndex:'a19'}
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
                    title: '存款业务分析--列表',
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
                   html:'<iframe id="contentFrame1" name="content1" height="300" frameborder="no" width="100%" src=\"../customerManager/customerBaseInformation/fusionchartsDemo/DragableCharts/DragCol2.html\" "/> scrolling="no"> </iframe>'
                }]
              },{
              columnWidth:.5,
              collapsible:true,
              items:[{
                    collapsible:true,
                    style:'padding:0px 0px 0px 0px',
                    height:date,
                    width : (document.body.clientWidth-180)/2.1,
                   html:'<iframe id="contentFrame1" name="content1" height="300" frameborder="no" width="100%" src=\"../customerManager/customerBaseInformation/fusionchartsDemo/DragableCharts/DragCol3.html\" "/> scrolling="no"> </iframe>'
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
              }]
            } ]
	});    	
});