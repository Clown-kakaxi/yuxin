/**
 * 客户基本信息
 */
Ext.onReady(function() {

		

	



			
	// 定义自动当前页行号
	var arownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

		// 定义列模型
	var acm = new Ext.grid.ColumnModel([arownum, 
	           {
				header : '主题', // 列标题
				dataIndex : 'b1', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				//align:'right',
				width : 150
		    }, {
				header : '内容',
				dataIndex : 'b2',
				//align:'right',
				sortable : true,
				width : 250
			}, {
				header : '时间',
				dataIndex : 'b3',
				//align:'right',
				sortable : true,
				width : 150
			},{
				header : '主办机构',
				//align:'right',
				dataIndex : 'b4',
				width : 150
			},{
				header : '行方领导',
				//align:'right',
				dataIndex : 'b5',
				width : 150
			},{
				header : '客户方领导',
				//align:'right',
				dataIndex : 'b6',
				width : 150
			},{
				header : '备注',
				//align:'right',
				dataIndex : 'b7',
				width : 150
			}
			]);

	/**
	 * 数据存储
	 */
	var astore = new Ext.data.Store({
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'b1' // Json中的属性Key值
								}, {
									name : 'b2'
								}, {
									name : 'b3'
								}, {
									name : 'b4'
								}, {
									name : 'b5'
								}, {
									name : 'b6'
								}, {
									name : 'b7'
								}
								])
			});

	var amemberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","b1":"金秋营销","b2":"优惠促销赠礼活动","b3":"2011-9-26至2011-10-31","b4":"北京银行上地支行","b5":"姚亮","b6":"焦向波","b7":"无"},
			{"rownum":"2","b1":"时尚西城","b2":"一卡畅享多家商场优惠礼遇","b3":"2011-6-26至2011-7-31","b4":"北京银行总行","b5":"姚亮","b6":"焦向波","b7":"无"}
		/*	{"rownum":"2","customer_name":"源进征信（北京）有限公司","relation_name":"控股关系","relation_describe":"股东", "founder":"管理员2",  "date_created":"2010-05-24"},
			{"rownum":"3","customer_name":"深圳中航幕墙工程有限公司","relation_name":"伙伴关系","relation_describe":"业务往来","founder":"管理员3",  "date_created":"2009-07-21"},			
			{"rownum":"4","customer_name":"北京宝嘉行商贸有限责任公司","relation_name":"上下游关系","relation_describe":"无","founder":"管理员4","date_created":"2009-12-01"}		*/		
			]
		};
	
	astore.loadData(amemberData);
// 表格实例
	var agrid = new Ext.grid.GridPanel({
				height : 500,
				frame : true,
				autoScroll : true,
				store :astore, // 数据存储
				stripeRows : true, // 斑马线
				cm : acm, // 列模型
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	 //复选框
	var xsm = new Ext.grid.CheckboxSelectionModel({singleSelect:true}); 

	// 定义自动当前页行号
	var xrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

		// 定义列模型
	var xcm = new Ext.grid.ColumnModel([xrownum,xsm, 
	           {
				header : '客户名称', // 列标题
				dataIndex : 'customer_name', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    }, {
				header : '关系名称',
				dataIndex : 'relation_name',
				sortable : true,
				width : 150
			}, {
				header : '关系描述',
				dataIndex : 'relation_describe',
				sortable : true,
				width : 150
			},{
				header : '创建人',
				dataIndex : 'founder'
			}, {
				header : '创建日期',
				dataIndex : 'date_created'
			}
			]);

	/**
	 * 数据存储
	 */
	var xstore = new Ext.data.Store({
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'customer_name' // Json中的属性Key值
								}, {
									name : 'relation_name'
								}, {
									name : 'relation_describe'
								}, {
									name : 'founder'
								}, {
									name : 'date_created'
								}
								])
			});

	var xmemberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","customer_name":"中节能（天津）投资集团有限公司","relation_name":"合作关系","relation_describe":"业务往来","founder":"管理员1",  "date_created":"2011-04-12"},
			{"rownum":"2","customer_name":"源进征信（北京）有限公司","relation_name":"控股关系","relation_describe":"股东", "founder":"管理员2",  "date_created":"2010-05-24"},
			{"rownum":"3","customer_name":"深圳中航幕墙工程有限公司","relation_name":"伙伴关系","relation_describe":"业务往来","founder":"管理员3",  "date_created":"2009-07-21"},			
			{"rownum":"4","customer_name":"北京宝嘉行商贸有限责任公司","relation_name":"上下游关系","relation_describe":"无","founder":"管理员4","date_created":"2009-12-01"}				
			]
		};
	
	xstore.loadData(xmemberData);
// 表格实例
	var xgrid = new Ext.grid.GridPanel({
		collapsible : true,// 是否可收缩
				height : 200,
				frame : true,
				autoScroll : true,
				store : xstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : xcm, // 列模型
				sm:xsm,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});
	 //复选框
	var ysm = new Ext.grid.CheckboxSelectionModel({singleSelect:true}); 

	// 定义自动当前页行号
	var yrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

		// 定义列模型
	var ycm = new Ext.grid.ColumnModel([yrownum,ysm, 
	           {
				header : '高管姓名', // 列标题
				dataIndex : 'r1', // 数据索引:和Store模型对应
				sortable : true,// 是否可排序
				width : 150
		    }, {
				header : '性别',
				dataIndex : 'r2',
				sortable : true,
				width : 150
			}, {
				header : '年龄',
				dataIndex : 'r3',
				sortable : true,
				width : 150
			},{
				header : '职务',
				dataIndex : 'r4'
			}, {
				header : '部门',
				dataIndex : 'r5'
			}, {
				header : '办公电话',
				dataIndex : 'r6',
				sortable : true,
				width : 150
			},{
				header : '手机号码',
				dataIndex : 'r7'
			}, {
				header : '传真',
				dataIndex : 'r8'
			}, {
				header : '邮件',
				dataIndex : 'r9',
				sortable : true,
				width : 150
			},{
				header : '主管工作',
				dataIndex : 'r10'
			}
			]);

	/**
	 * 数据存储
	 */
	var ystore = new Ext.data.Store({
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'r1' // Json中的属性Key值
								}, {
									name : 'r2'
								}, {
									name : 'r3'
								}, {
									name : 'r4'
								}, {
									name : 'r5'
								}, {
									name : 'r6'
								}, {
									name : 'r7'
								}, {
									name : 'r8'
								}, {
									name : 'r9'
								}, {
									name : 'r10'
								}
								])
			});

	var ymemberData= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","r1":"李强","r2":"男","r3":"45","r4":"总经理",  "r5":"业务部门",  "r6":"010-2432523",  "r7":"139023443385",  "r8":"2345235",  "r9":"sdfsdg@126.com",  "r10":"财务"},
			{"rownum":"2","r1":"黄明","r2":"男","r3":"47", "r4":"副总经理", "r5":"业务部门",  "r6":"010-5723873",  "r7":"13823523423",  "r8":"235434",  "r9":"sdfgadg@163.com",  "r10":"财务"},
			{"rownum":"3","r1":"张鸿","r2":"女","r3":"51","r4":"副总经理", "r5":"业务部门",  "r6":"010-3726374",  "r7":"158234523423",  "r8":"2342534",  "r9":"567546778@qq.com",  "r10":"投资"},			
			{"rownum":"4","r1":"王燕","r2":"女","r3":"50","r4":"副总经理", "r5":"业务部门",  "r6":"010-3728332",  "r7":"13723425235",  "r8":"34526243",  "r9":"werwgsd@126.com",  "r10":"投资"}				
			]
		};
	
	ystore.loadData(ymemberData);
// 表格实例
	var ygrid = new Ext.grid.GridPanel({
				height : 500,
				frame : true,
				autoScroll : true,
				store : ystore, // 数据存储
				stripeRows : true, // 斑马线
				cm : ycm, // 列模型
				sm:ysm,
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
			});

    
    var cm = new Ext.grid.ColumnModel
    ([
           {header:'控制人名称', dataIndex:'id',width:270},
           {header:'控制人类型', dataIndex:'name',width:270},
           {header:'控制人证件种类',dataIndex:'descn',width:270},
           {header:'授权人姓名',dataIndex:'sq',width:270},
           {header:'控股比例',dataIndex:'kg',width:270}
           ]);
                                       
   var data = [
        ['姓名1','机构','身份证','授权人姓名1','3%'],
        ['姓名2','个人','户口本','授权人姓名2','2%'],
        ['姓名3','个人','身份证','授权人姓名3','4%'],
        ['姓名4','机构','护照','授权人姓名4','3%'],
        ['姓名5','机构','身份证','授权人姓名5','2%']
       ];
                                       
   var store = new Ext.data.Store({
	    proxy:new Ext.data.MemoryProxy(data),
	    reader: new Ext.data.ArrayReader({},[
	    {name:'id',mapping:0},
	    {name:'name',mapping:1},
	    {name:'descn',mapping:2},
	    {name:'sq',mapping:3},
	    {name:'kg',mapping:4}
	    ]) 
   });
      store.load();
                                       
       var grid = new Ext.grid.GridPanel({
    	   height: 600,
           width: '100%',
        store:store,
        cm:cm
       });

       grid.getStore().loadData(data);
   var cm1 = new Ext.grid.ColumnModel([
//                                {header:'姓名', dataIndex:'id1',width:270,align:'center'},
//                              {header:'客户编号', dataIndex:'name1',width:270,align:'center'},
//                              {header:'出生日期',dataIndex:'descn1',width:270,align:'center'},
//                              {header:'证件号码',dataIndex:'sq1',width:270,align:'center'},
//                              {header:'客户住址',dataIndex:'kg1',width:270,align:'center'}
{header:'姓名', dataIndex:'id1',width:270},
{header:'客户编号', dataIndex:'name1',width:270},
{header:'出生日期',dataIndex:'descn1',width:270},
{header:'证件号码',dataIndex:'sq1',width:270},
{header:'客户住址',dataIndex:'kg1',width:270}
                        

		]);
                              
                              var data1 = [
                               ['姓名1','1232131','1986.01.23','2435245675675','北京市海淀区'],
                               ['姓名2','1232132','1981.05.02','8904563546346','北京市东城区'],
                               ['姓名3','1232133','1970.10.23','3234234234234','北京市朝阳区'],
                               ['姓名4','1232134','1977.11.07','2372275465835','北京市西城区'],
                               ['姓名5','1232135','1960.11.17','2435241412124','北京市丰台区']
                              ];
                              
                              var store1 = new Ext.data.Store({
                               proxy:new Ext.data.MemoryProxy(data1),
                               reader: new Ext.data.ArrayReader({},[
                               {name:'id1',mapping:0},
                               {name:'name1',mapping:1},
                               {name:'descn1',mapping:2},
                               {name:'sq1',mapping:3},
                               {name:'kg1',mapping:4}
                                       ]) 
                                      });
                                      store1.load();
                                      
                                      var grid1 = new Ext.grid.GridPanel({
                                   	   height: 440,
                                          width: '100%',
                                       store:store1,
                                       cm:cm1
                                      });

                                      grid1.getStore().loadData(data1);
     
                                      
                                      
                                      var cm2 = new Ext.grid.ColumnModel
                                      ([
                                             {header:'高管姓名', dataIndex:'e1'},
                                             {header:'英文名', dataIndex:'e2'},
                                             {header:'是否法人',dataIndex:'e3'},
                                             {header:'公司职务',dataIndex:'e4'},
                                             {header:'担任职务时间',dataIndex:'e5'},
                                              {header:'现单位工作时间', dataIndex:'e6'},
                                             {header:'重要程度', dataIndex:'e7'},
                                             {header:'是否我行个人客户',dataIndex:'e8'},
                                             {header:'营销主要关键要素',dataIndex:'e9'},
                                             {header:'关系背景',dataIndex:'e10'}, 
                                             {header:'持股情况', dataIndex:'e11'},
                                             {header:'个人爱好', dataIndex:'e12'},
                                             {header:'国籍',dataIndex:'e13'},
                                             {header:'出生日期',dataIndex:'e14'},
                                             {header:'性别',dataIndex:'e15'}, 
                                             {header:'民族', dataIndex:'e16'},
                                             {header:'宗教信仰', dataIndex:'e17'},
                                             {header:'婚姻状况',dataIndex:'e18'},
                                             {header:'最高学历',dataIndex:'e19'},
                                             {header:'毕业院校',dataIndex:'e20'},
                                             {header:'证件类型',dataIndex:'e21'},
                                             {header:'证件号码',dataIndex:'e22'},
                                             {header:'职称',dataIndex:'e23'},
                                              {header:'相关行业从业年限', dataIndex:'e24'},
                                             {header:'月收入(元)', dataIndex:'e25'},
                                             {header:'主要经济来源',dataIndex:'e26'},
                                             {header:'其他经济来源',dataIndex:'e27'},
                                             {header:'社会职务',dataIndex:'e28'}, 
                                             {header:'邮箱地址', dataIndex:'e29'},
                                             {header:'工作地址', dataIndex:'e30'},
                                             {header:'家庭地址',dataIndex:'e31'},
                                             {header:'移动电话',dataIndex:'e32'},
                                             {header:'办公电话',dataIndex:'e33'}, 
                                             {header:'家庭电话', dataIndex:'e34'},
                                             {header:'备注', dataIndex:'e35'},
                                             {header:'家庭成员名称',dataIndex:'e36'},
                                             {header:'家庭关系',dataIndex:'e37'},
                                             {header:'家族成员所在企业名称',dataIndex:'e38'},
                                              {header:'家族关系', dataIndex:'e39'},
                                             {header:'证件号码', dataIndex:'e40'},
                                             {header:'证件类型',dataIndex:'e41'},
                                             {header:'家庭供养人口',dataIndex:'e42'}
                                             ]);
                                                                         
                                /*     var data2 = [
                                          ['姓名1','1232131','1986.01.23','2435245675675','北京市海淀区'],
                               ['姓名2','1232132','1981.05.02','8904563546346','北京市东城区'],
                               ['姓名3','1232133','1970.10.23','3234234234234','北京市朝阳区'],
                               ['姓名4','1232134','1977.11.07','2372275465835','北京市西城区'],
                               ['姓名5','1232135','1960.11.17','2435241412124','北京市丰台区']
                                         ];*/
               var store2 = new Ext.data.Store({
				// 数据读取器
				reader : new Ext.data.JsonReader({
							totalProperty:'num',// 记录总数
							//idIndex:'blocName', 
							root:'rows'// Json中的列表数据根节点
						}, [{
									name : 'e1' // Json中的属性Key值
								}, {
									name : 'e2'
								}, {
									name : 'e3'
								}, {
									name : 'e4'
								}, {
									name : 'e5'
								}, {
									name : 'e6'
								}, {
									name : 'e7'
								}, {
									name : 'e8'
								}, {
									name : 'e9'
								}, {
									name : 'e10'
								}, {
									name : 'e11'
								}, {
									name : 'e12'
								}, {
									name : 'e13'
								}, {
									name : 'e14'
								}, {
									name : 'e15'
								}, {
									name : 'e16'
								}, {
									name : 'e17'
								}, {
									name : 'e18'
								}, {
									name : 'e19'
								}, {
									name : 'e20'
								}, {
									name : 'e21'
								}, {
									name : 'e22'
								}, {
									name : 'e23'
								}, {
									name : 'e24'
								}, {
									name : 'e25'
								}, {
									name : 'e26'
								}, {
									name : 'e27'
								}, {
									name : 'e28'
								}, {
									name : 'e29'
								}, {
									name : 'e30'
								}, {
									name : 'e31'
								}, {
									name : 'e32'
								}, {
									name : 'e33'
								}, {
									name : 'e34'
								}, {
									name : 'e35'
								}, {
									name : 'e36'
								}, {
									name : 'e37'
								}, {
									name : 'e38'
								}, {
									name : 'e39'
								}, {
									name : 'e40'
								}, {
									name : 'e41'
								}, {
									name : 'e42'
								}
								])
			});
			var data2= {
			TOTALCOUNT:3,
			rows:[
			{"rownum":"1","e1":"王刚","e2":"Tom"  ,"e3":"是","e4":"总经理"   ,"e5":"5年","e6":"重要","e7":"是","e8":"无","e9":"1","e10":"1","e11":"1","e12":"1","e13":"1","e14":"1","e15":"1","e16":"1","e17":"1","e18":"1","e19":"1","e20":"1","e21":"1","e22":"1","e23":"1","e24":"1","e25":"1","e26":"1","e27":"1","e28":"1","e29":"1","e30":"1","e31":"1","e32":"1","e33":"1","e34":"1","e35":"1","e36":"1","e37":"1","e38":"1","e39":"1","e40":"1","e41":"1","e42":"1"},
			{"rownum":"2","e1":"李强","e2":"nigel","e3":"否","e4":"副总经理" ,"e5":"3年","e6":"重要","e7":"否","e8":"无","e9":"1","e10":"1","e11":"1","e12":"1","e13":"1","e14":"1","e15":"1","e16":"1","e17":"1","e18":"1","e19":"1","e20":"1","e21":"1","e22":"1","e23":"1","e24":"1","e25":"1","e26":"1","e27":"1","e28":"1","e29":"1","e30":"1","e31":"1","e32":"1","e33":"1","e34":"1","e35":"1","e36":"1","e37":"1","e38":"1","e39":"1","e40":"1","e41":"1","e42":"1"},
			{"rownum":"3","e1":"张鸿","e2":"lily" ,"e3":"否","e4":"副总经理" ,"e5":"4年","e6":"重要","e7":"否","e8":"无","e9":"1","e10":"1","e11":"1","e12":"1","e13":"1","e14":"1","e15":"1","e16":"1","e17":"1","e18":"1","e19":"1","e20":"1","e21":"1","e22":"1","e23":"1","e24":"1","e25":"1","e26":"1","e27":"1","e28":"1","e29":"1","e30":"1","e31":"1","e32":"1","e33":"1","e34":"1","e35":"1","e36":"1","e37":"1","e38":"1","e39":"1","e40":"1","e41":"1","e42":"1"},			
			{"rownum":"4","e1":"王燕","e2":"lucy" ,"e3":"否","e4":"副总经理" ,"e5":"4年","e6":"重要","e7":"否","e8":"无","e9":"1","e10":"1","e11":"1","e12":"1","e13":"1","e14":"1","e15":"1","e16":"1","e17":"1","e18":"1","e19":"1","e20":"1","e21":"1","e22":"1","e23":"1","e24":"1","e25":"1","e26":"1","e27":"1","e28":"1","e29":"1","e30":"1","e31":"1","e32":"1","e33":"1","e34":"1","e35":"1","e36":"1","e37":"1","e38":"1","e39":"1","e40":"1","e41":"1","e42":"1"}				
			]
		};
                                                                         
                                         var grid2 = new Ext.grid.GridPanel({
                                      	   height: 600,
                                             width: '100%',
                                          store:store2,
                                          cm:cm2
                                         });
                                         
         var panel2 = new Ext.FormPanel({ 
                                              	        frame:true,
                                              	        bodyStyle:'padding:5px 5px 0',
                                              	        width: '100%',
                                              	       height:600,

                                              	        items: [{
                                              	        	//columnWidth:.5,
                                              	            	//xtype:'fieldset',
                                              	            //checkboxToggle:true,
                                              	          //  title: '查询条件',
                                              	           autoHeight:true,
                                              	           // defaults: {width:'33.3%'},
                                              	            //defaultType: 'textfield',
                                              	            //collapsed: true,
                                              	            //layout:'column',
                                              	            items :[{ layout:'column',
                                              	                     items:[{
                                              	                         columnWidth:.33,
                                              	                         layout: 'form',
                                              	                         items: [{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '高管名称',
                                              	                             value :'李强',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             name: 'first',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '公司职务',
                                              	                             name: 'state',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'总经理',
                                              	                             anchor:'90%'
                                              	                         },{
                                              	                         	xtype:'textfield',
                                              	                         	fieldLabel:'是否我行个人用户',
                                              	                         	value:'是',
                                              	                         	 labelStyle: 'text-align:right;',
                                              	                         	name:'date',
                                              	                         	anchor:'90%'
                                              	                         
                                              	                         },{
                                              	                         	xtype:'textfield',
                                              	                         	fieldLabel:'职务',
                                              	                         	value:'',
                                              	                         	 labelStyle: 'text-align:right;',
                                              	                         	name:'startdate',
                                              	                         	anchor:'90%'
                                              	                         
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '民族',
                                              	                             name: 'setupInter',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'汉',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '婚姻状况',
                                              	                             name: 'trade',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'已婚',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '主要经济来源',
                                              	                             name: 'ismarket',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'工资',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '邮箱地址',
                                              	                             name: 'Companytype',
                                              	                             value :'2342432@qq.com',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '移动电话',
                                              	                             name: 'newdate',
                                              	                             value :'139023772',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '家庭成员名称',
                                              	                             name: 'newdate',
                                              	                             value :'张清',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '家族关系',
                                              	                             name: 'newdate',
                                              	                             value :'母子',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '家庭供养人员',
                                              	                             name: 'newdate',
                                              	                             value :'',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '联系电话',
                                              	                             name: 'newdate',
                                              	                             value :'010-2342342',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '持股情况',
                                              	                             name: 'newdate',
                                              	                             value :'19%',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }]
                                              	                     },{
                                              	                         columnWidth:.33,
                                              	                         layout: 'form',
                                              	                         items: [{
                                              	                             xtype:'textfield',
                                              	                              fieldLabel: '英文名',
                                              	                               labelStyle: 'text-align:right;',
                                              	                             name: 'orgid',
                                              	                             value :'tom',
                                              	                             anchor:'90%'
                                              	                         },{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '现单位工作时间',
                                              	                             name: 'baseid',
                                              	                             value :'6年',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         },{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '证件类型',
                                              	                             name: 'lendCount',
                                              	                             value :'身份证',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         },{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '性别',
                                              	                             name: 'Cuscontribute',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'男',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '国籍',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             name: '中国',
                                              	                             value :'公司',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '毕业院校',
                                              	                             name: 'CusSize',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'清华大学',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '其他经济来源',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             name: 'CusType',
                                              	                             value :'投资',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '工作地址',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             name: 'ispartner',
                                              	                             value :'北京海淀',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '办公电话',
                                              	                             name: 'orgId',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'010-23423523',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '家庭关系',
                                              	                             name: 'newdate',
                                              	                             value :'',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '证件号码',
                                              	                             name: 'newdate',
                                              	                             value :'130284937288472834',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '学历',
                                              	                             name: 'newdate',
                                              	                             value :'本科',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '担任职务时间',
                                              	                             name: 'newdate',
                                              	                             value :'3年',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }]
                                              	                     },{
                                              	                         columnWidth:.34,
                                              	                         layout: 'form',
                                              	                         items: [{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '是否法人',
                                              	                             value :'是',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             name: 'lendid',
                                              	                             anchor:'90%'
                                              	                         },{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '重要程度',
                                              	                             name: 'basebank',
                                              	                             value :'重要',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         },{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '证件号码',
                                              	                             name: 'myCount',
                                              	                             value :'1302837583764479',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         },{
                                              	                             xtype:'datefield',
                                              	                             fieldLabel: '出生日期',
                                              	                             name: 'setupDate',
                                              	                             value :'05/13/2011',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '宗教信仰',
                                              	                             name: 'property',
                                              	                             value :'无',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '月收入(元)',
                                              	                             name: 'isgroup',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'50000元',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '社会职务',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             name: 'CusLever',
                                              	                             value :'无',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '家庭住址',
                                              	                             name: 'isnew',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'北京东城区',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '备注',
                                              	                             name: 'enddate',
                                              	                             value :'无',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '家族成员所在企业名称',
                                              	                             name: 'businessId',
                                              	                             value :'xx银行',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '证件类型',
                                              	                             name: 'newdate',
                                              	                             value :'1928773991773924',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '工作简历',
                                              	                             name: 'newdate',
                                              	                             value :'无',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '相关行业从业年限',
                                              	                             name: 'newdate',
                                              	                             value :'3年',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'90%'
                                              	                         }]
                                              	                     }
                                              	            ]}
                                              	            ]}]
                                              	    });
	var addRoleWindow = new Ext.Window(
			{
				//layout : 'fit',
				width : 800,
				height : 400,
				//resizable : false,//是否允许缩放
				draggable : true,//是否可以拖动
				closable : true,// 是否可关闭
				modal : true,
				closeAction : 'hide',
				title : '<span style="font-weight:normal">客户分配</span>',
				// iconCls : 'page_addIcon',
				//maximizable: true,
				//maximized:true,
				collapsible : true,// 是否可收缩
				titleCollapse : true,
				buttonAlign : 'right',
				border : false,
				animCollapse : true,
				pageY : 20,
				//pageX : document.body.clientWidth / 2 - 420 / 2,
				animateTarget : Ext.getBody(),
				constrain : true,
				items : [panel2]
			});
	
	
             grid2.getStore().loadData(data2);
             ygrid.on('rowdblclick', function(grid, rowIndex, event) {
             addRoleWindow.show();
                });
             
                                         
                                         
                                         var cm3 = new Ext.grid.ColumnModel
                                         ([
                                                {header:'经营名称', dataIndex:'id',width:270},
                                                {header:'经营类型', dataIndex:'name',width:270},
                                                {header:'高管职位',dataIndex:'descn',width:270},
                                                {header:'姓名',dataIndex:'sq',width:270},
                                                {header:'控股比例',dataIndex:'kg',width:270}
                                                ]);
                                                                            
                                        var data3 = [
                                             ['姓名1','机构','身份证','授权人姓名1','3%'],
                                             ['姓名2','个人','户口本','授权人姓名2','2%'],
                                             ['姓名3','个人','身份证','授权人姓名3','4%'],
                                             ['姓名4','机构','护照','授权人姓名4','3%'],
                                             ['姓名5','机构','身份证','授权人姓名5','2%']
                                            ];
                                                                            
                                        var store3 = new Ext.data.Store({
                                     	    proxy:new Ext.data.MemoryProxy(data),
                                     	    reader: new Ext.data.ArrayReader({},[
                                     	    {name:'id',mapping:0},
                                     	    {name:'name',mapping:1},
                                     	    {name:'descn',mapping:2},
                                     	    {name:'sq',mapping:3},
                                     	    {name:'kg',mapping:4}
                                     	    ]) 
                                        });
                                           store3.load();
                                                                            
                                            var grid3 = new Ext.grid.GridPanel({
                                         	   height: 600,
                                                width: '100%',
                                             store:store3,
                                             cm:cm3
                                            });

                                            grid3.getStore().loadData(data3);
                                            
                                            
                                            
                                            var cm4 = new Ext.grid.ColumnModel
                                            ([
                                                   {header:'客户名称', dataIndex:'id',width:270},
                                                   {header:'客户类型', dataIndex:'name',width:270},
                                                   {header:'高管职位',dataIndex:'descn',width:270},
                                                   {header:'姓名',dataIndex:'sq',width:270},
                                                   {header:'客户归属',dataIndex:'kg',width:270}
                                                   ]);
                                                                               
                                           var data4 = [
                                                ['姓名1','机构','身份证','授权人姓名1','3%'],
                                                ['姓名2','个人','户口本','授权人姓名2','2%'],
                                                ['姓名3','个人','身份证','授权人姓名3','4%'],
                                                ['姓名4','机构','护照','授权人姓名4','3%'],
                                                ['姓名5','机构','身份证','授权人姓名5','2%']
                                               ];
                                                                               
                                           var store4 = new Ext.data.Store({
                                        	    proxy:new Ext.data.MemoryProxy(data),
                                        	    reader: new Ext.data.ArrayReader({},[
                                        	    {name:'id',mapping:0},
                                        	    {name:'name',mapping:1},
                                        	    {name:'descn',mapping:2},
                                        	    {name:'sq',mapping:3},
                                        	    {name:'kg',mapping:4}
                                        	    ]) 
                                           });
                                              store4.load();
                                                                               
                                               var grid4 = new Ext.grid.GridPanel({
                                            	   height: 600,
                                                   width: '100%',
                                                store:store4,
                                                cm:cm4
                                               });

                                               grid4.getStore().loadData(data4);
   
                                             //客户信息面板视图
                                               var cuspanel = new Ext.FormPanel({ 
                                              	        frame:true,
                                              	        bodyStyle:'padding:5px 5px 0',
                                              	        width: '100%',
                                              	       height:600,

                                              	        items: [{
                                              	        	//columnWidth:.5,
                                              	            	//xtype:'fieldset',
                                              	            //checkboxToggle:true,
                                              	          //  title: '查询条件',
                                              	           autoHeight:true,
                                              	           // defaults: {width:'33.3%'},
                                              	            //defaultType: 'textfield',
                                              	            //collapsed: true,
                                              	            //layout:'column',
                                              	            items :[{ layout:'column',
                                              	                     items:[{
                                              	                         columnWidth:.33,
                                              	                         layout: 'form',
                                              	                         items: [{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '客户姓名',
                                              	                             value :'华夏基金有限公司',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             name: 'first',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '贷款卡状态',
                                              	                             name: 'state',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'已贷款',
                                              	                             anchor:'70%'
                                              	                         },{
                                              	                         	xtype:'datefield',
                                              	                         	fieldLabel:'与我行建立信贷关系的时间',
                                              	                         	value:'05/13/2011',
                                              	                         	 labelStyle: 'text-align:right;',
                                              	                         	name:'date',
                                              	                         	anchor:'70%'
                                              	                         
                                              	                         },{
                                              	                         	xtype:'datefield',
                                              	                         	fieldLabel:'与我行首次开立账户时间',
                                              	                         	value:'05/13/2011',
                                              	                         	 labelStyle: 'text-align:right;',
                                              	                         	name:'startdate',
                                              	                         	anchor:'70%'
                                              	                         
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '建立网点',
                                              	                             name: 'setupInter',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'北京市海淀区中关村南大街',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '行业',
                                              	                             name: 'trade',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'金融业',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '是否上市公司',
                                              	                             name: 'ismarket',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'是',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '企业类型',
                                              	                             name: 'Companytype',
                                              	                             value :'私营',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'datefield',
                                              	                             fieldLabel: '工商执照最新年检时间',
                                              	                             name: 'newdate',
                                              	                             value :'05/13/2011',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'70%'
                                              	                         }]
                                              	                     },{
                                              	                         columnWidth:.33,
                                              	                         layout: 'form',
                                              	                         items: [{
                                              	                             xtype:'textfield',
                                              	                              fieldLabel: '机构号',
                                              	                               labelStyle: 'text-align:right;',
                                              	                             name: 'orgid',
                                              	                             value :'10101',
                                              	                             anchor:'70%'
                                              	                         },{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '基本账户号',
                                              	                             name: 'baseid',
                                              	                             value :'6226347590871233',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'70%'
                                              	                         },{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '负债总额',
                                              	                             name: 'lendCount',
                                              	                             value :'62,590,871,233',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'70%'
                                              	                         },{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '客户贡献度',
                                              	                             name: 'Cuscontribute',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'70%',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '经济组织类型',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             name: 'ecnomicOrgType',
                                              	                             value :'公司',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '客户规模',
                                              	                             name: 'CusSize',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'中型企业',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '客户类型',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             name: 'CusType',
                                              	                             value :'集团客户',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '是否本行股东',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             name: 'ispartner',
                                              	                             value :'是',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '组织机构代码',
                                              	                             name: 'orgId',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'A123456',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '税务证号',
                                              	                             name: 'revenueId',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'Z-2123456',
                                              	                             anchor:'70%'
                                              	                         }]
                                              	                     },{
                                              	                         columnWidth:.34,
                                              	                         layout: 'form',
                                              	                         items: [{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '贷款卡号',
                                              	                             value :'6226347590871233',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             name: 'lendid',
                                              	                             anchor:'70%'
                                              	                         },{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '基本账户银行',
                                              	                             name: 'basebank',
                                              	                             value :'北京银行',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'70%'
                                              	                         },{
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '资产总额',
                                              	                             name: 'myCount',
                                              	                             value :'62,590,871,233',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'70%'
                                              	                         },{
                                              	                             xtype:'datefield',
                                              	                             fieldLabel: '建立日期',
                                              	                             name: 'setupDate',
                                              	                             value :'05/13/2011',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '所有者性质',
                                              	                             name: 'property',
                                              	                             value :'私营企业',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '是否集团客户',
                                              	                             name: 'isgroup',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'是',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '客户级别',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             name: 'CusLever',
                                              	                             value :'已贷款',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '是否高新科技企业',
                                              	                             name: 'isnew',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             value :'否',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'datefield',
                                              	                             fieldLabel: '工商执照到期日',
                                              	                             name: 'enddate',
                                              	                             value :'05/13/2011',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'70%'
                                              	                         }, {
                                              	                             xtype:'textfield',
                                              	                             fieldLabel: '营业执照号',
                                              	                             name: 'businessId',
                                              	                             value :'A123456',
                                              	                              labelStyle: 'text-align:right;',
                                              	                             anchor:'70%'
                                              	                         }]
                                              	                     }
                                              	            ]}
                                              	            ]}]
                                              	    });
    //Ext.History.init();
    var tokenDelimiter = ':';
    /*
    var scrollerMenu = new Ext.ux.TabScrollerMenu({
		maxText  : 15,
		pageSize : 5
	});*/
    var tp = new Ext.TabPanel({
        //renderTo: 'cbi',
        id: 'main-tabs',
        
		//resizeTabs      : true,
        activeTab: 0,
        tabPosition: 'bottom',
     //   maximizable: true,  
        //height: 500,
        	//document.body.clientHeight-300,
        //animScroll:true,
			//margins: '0 0 100 0',
       // style:'padding-bottom:500',
        //autoScroll:true,
        //border : false,
        //plugins : [ scrollerMenu ],
        //minTabWidth:200,
       // width: '100%',
//     items: [
//     {
//            xtype: 'tabpanel',           
//            id: 'tab1',
//            activeTab: 0,
//            tabPosition: 'bottom',
//            //plugins : [ scrollerMenu ],
            enableTabScroll : true,
            items: [{
                title: '1.客户基本信息',
                id: 'subtab1',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/customerBase.jsp\" "/> scrolling="auto"> </iframe>'
                    
            },
			{
                title: '2.账户信息',
                id: 'subtab2',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/informationOnBusinessCooperation.jsp\" "/> scrolling="auto"> </iframe>'
            },
          /*  {
                title: '3.产品信息',
                id: 'subtab3',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/customerPoroductInformation.html\" "/> scrolling="auto"> </iframe>'
            },*/
          /*  {
                title: '4.担保信息',
                id: 'subtab4',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/assureInformation.jsp\" "/> scrolling="auto"> </iframe>'
            },*/
            {
                title: '3.签约信息',
                id: 'subtab5',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/signInformation.jsp\" "/> scrolling="auto"> </iframe>'
            },{
                title: '4.客户贡献度',
                id: 'subtab6', 
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/degreeOfContribution.jsp\" "/> scrolling="auto"> </iframe>'
            },
         /*   {
                title: '7.授信信息',
                id: 'subtab7',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/creditGranting.jsp\" "/> scrolling="auto"> </iframe>'
            },*/
            {
                title: '5.归属信息',
                id: 'subtab8',
               html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/affiliationInformation.jsp\" "/> scrolling="auto"> </iframe>'
             }
          /*  ,{
                 title: '6.客户间关系',
                 id: 'subtab9',
                 html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/viewAndGrid.jsp\" "/> scrolling="auto"> </iframe>'            
             }*/
            ,{
                title: '6.联系人信息',
                id: 'subtab10',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/contactPerson.jsp\" "/> scrolling="auto"> </iframe>'
            },{
                title: '7.高管信息',
                id: 'subtab11',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/leaderInformation.jsp\" "/> scrolling="auto"> </iframe>'
            }
          /*  ,{
                title: '12.财务信息',
                id: 'subtab12'
                //items:[grid4]
            }*/
          /*  ,{
                title: '13.营销信息',
                id: 'subtab13' ,
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/marketInformation.jsp\" "/> scrolling="auto"> </iframe>' 
            }*/
            ,{
                title: '8.客户事件信息',
                id: 'subtab14',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/eventInformation.jsp\" "/> scrolling="auto"> </iframe>' 
            }
          /*  ,{
                title: '15.客户预警信息',
                id: 'subtab15',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/warningInformation.html\" "/> scrolling="auto"> </iframe>'              
            }*/
            ,{
                title: '9.客户他行信息',
                id: 'subtab16' ,
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/otherBank.jsp\" "/> scrolling="auto"> </iframe>'              
            }
         /* ,{
                title: '17.客户评级信息',
                id: 'subtab17',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/ratingInformation.jsp\" "/> scrolling="auto"> </iframe>'              
                	   
            }*/
            ,{
                title: '10.股东信息',
                id: 'subtab18',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/partnerInformation.jsp\" "/> scrolling="auto"> </iframe>'
            },{
                title: '11.对外股权投资情况',
                id: 'subtab19',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/equityStake.jsp\" "/> scrolling="auto"> </iframe>'            
            },{
                title: '12.股票发行信息',
                id: 'subtab20',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/issueInformation.jsp\" "/> scrolling="auto"> </iframe>'            
            },{
                title: '13.债券发行信息',
                id: 'subtab21',
                html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerBaseInformation/bondInformation.jsp\" "/> scrolling="auto"> </iframe>'            
            }
            ] ,
            
    listeners: {
            'tabchange': function(tabPanel, tab){
                Ext.History.add(tabPanel.id + tokenDelimiter + tab.id);
            }
        }
    
    
//    listeners: {
//        'tabchange': function(tabPanel, tab){
//            if(tab.id != 'tab1'){
//                Ext.History.add(tabPanel.id + tokenDelimiter + tab.id);
//            }
//        }
//    }
});
    Ext.History.on('change', function(token){
        if(token){
            var parts = token.split(tokenDelimiter);
            var tabPanel = Ext.getCmp(parts[0]);
            var tabId = parts[1];
            
            tabPanel.show();
            tabPanel.setActiveTab(tabId);
        }else{
         
            tp.setActiveTab(0);
            tp.getItem(0).setActiveTab(0);
        }
    });
 var data = [ 
	 
			{"companyForAud":"1.客户基本信息","_id":1,"_parent":null,"_level":1,"_lft":1,"_rgt":98,"_is_leaf":true},
			{"companyForAud":"2.账户信息","_id":2,"_parent":null,"_level":1,"_lft":2,"_rgt":2,"_is_leaf":true},
			//{"companyForAud":"3.产品信息","_id":3,"_parent":null,"_level":1,"_lft":3,"_rgt":3,"_is_leaf":true},
			//{"companyForAud":"4.担保信息","_id":4,"_parent":1,"_level":1,"_lft":4,"_rgt":9,"_is_leaf":true},
			{"companyForAud":"3.签约信息","_id":5,"_parent":4,"_level":1,"_lft":5,"_rgt":5,"_is_leaf":true},
			{"companyForAud":"4.客户贡献度","_id":6,"_parent":4,"_level":1,"_lft":6,"_rgt":6,"_is_leaf":true},
			//{"companyForAud":"7.授信信息","_id":7,"_parent":4,"_level":1,"_lft":7,"_rgt":7,"_is_leaf":true},
			{"companyForAud":"5.归属信息","_id":8,"_parent":4,"_level":1,"_lft":8,"_rgt":8,"_is_leaf":true},
			//{"companyForAud":"9.客户间关系","_id":9,"_parent":1,"_level":1,"_lft":10,"_rgt":14,"_is_leaf":true},
			{"companyForAud":"6.联系人信息","_id":10,"_parent":9,"_level":1,"_lft":11,"_rgt":11,"_is_leaf":true},
			{"companyForAud":"7.高管信息","_id":11,"_parent":9,"_level":1,"_lft":12,"_rgt":12,"_is_leaf":true},
			//{"companyForAud":"12.财务信息","_id":12,"_parent":9,"_level":1,"_lft":13,"_rgt":13,"_is_leaf":true},
			//{"companyForAud":"13.营销信息","_id":13,"_parent":9,"_level":1,"_lft":14,"_rgt":13,"_is_leaf":true},   
	        {"companyForAud":"8.客户事件信息","_id":14,"_parent":9,"_level":1,"_lft":15,"_rgt":13,"_is_leaf":true},	   
	       // {"companyForAud":"15.客户预警信息","_id":15,"_parent":9,"_level":1,"_lft":16,"_rgt":13,"_is_leaf":true},   
        	{"companyForAud":"9.客户他行信息","_id":16,"_parent":9,"_level":1,"_lft":13,"_rgt":13,"_is_leaf":true},  
	       // {"companyForAud":"17.客户评级信息","_id":17,"_parent":9,"_level":1,"_lft":17,"_rgt":13,"_is_leaf":true},  
	        {"companyForAud":"10.股东信息","_id":18,"_parent":9,"_level":1,"_lft":18,"_rgt":13,"_is_leaf":true},	   
	        {"companyForAud":"11.对外股权投资情况","_id":19,"_parent":9,"_level":1,"_lft":19,"_rgt":13,"_is_leaf":true},   
	        {"companyForAud":"12.股票发行信息","_id":20,"_parent":9,"_level":1,"_lft":20,"_rgt":13,"_is_leaf":true},	   
	        {"companyForAud":"13.债券发行信息","_id":21,"_parent":9,"_level":1,"_lft":21,"_rgt":13,"_is_leaf":true}	   
	    	
		];
   
		for (var i = 0; i < data.length; i++) {
    	data[i].desc = 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.<br/><br/>Aliquam commodo ullamcorper erat. Nullam vel justo in neque porttitor laoreet. Aenean lacus dui, consequat eu, adipiscing eget, nonummy non, nisi. Morbi nunc est, dignissim non, ornare sed, luctus eu, massa. Vivamus eget quam. Vivamus tincidunt diam nec urna. Curabitur velit.';
    	data[i]._is_loaded = true;
		}
    var record = Ext.data.Record.create([
  		{name: 'companyForAud'},
     	{name: '_id', type: 'int'},
     	{name: '_level', type: 'int'},
     	{name: '_lft', type: 'int'},
     	{name: '_rgt', type: 'int'},
     	{name: '_is_leaf', type: 'bool'}

   	]);
    debugger;
    var store = new Ext.ux.maximgb.tg.NestedSetStore({
    	//	autoLoad : true,
			reader: new Ext.data.JsonReader({id: '_id'}, record),
			proxy: new Ext.data.MemoryProxy(data)
    });
    

    var blocCreValAppAudPanel = new Ext.ux.maximgb.tg.EditorGridPanel({
      store: store,
	  region:'center',
	  	selModel:new Ext.grid.RowSelectionModel({
					
					singleSelect:true,
					listeners:{
					'rowselect':function( model,  rowIndex,  record )
					{
						var n=rowIndex;
                         tp.setActiveTab( n);
						//if(rowIndex==0){
							  // var s = Ext.getCmp('south-panel');
					        /*    // expand or collapse that Panel based on its collapsed property state
					            w.collapsed ? w.expand() : w.collapse();
					            south-panel*/
								//alert('23424');
								//tabPanel.setActiveTab(n);
                          // tp.setActiveTab(1);
						//tp.setActiveTab(20);
					       
						
					}
					}
				}),
	  split:true,
      master_column_id : 'companyForAud',
      columns: [
      	{
            id:'companyForAud',
            header: "菜单", 
            width: 250, 
 //           sortable: true, 
            dataIndex: 'companyForAud'
            
        } 
      ],
      stripeRows: true, 
      viewConfig : {
      	enableRowBody : true,
      	getRowClass:function(record,rowIndex,rp,ds)
      	{
      		
      		if(!record.get("_is_leaf"))
      		{
      			return 'blocLoanAndDepositNodeClass';
      		}
      	}
      }
    });
      store.load();
      store.expandAll();
  
  
	// 布局模型
	var viewport = new Ext.Viewport({
				

layout: 'border',
items: [
{   region: 'east', 
    id: 'east-panel',
	collapseMode: 'mini',
	collapsible: true,
	//title: "企业成员", 
	split: true, 
	width: 155, 
    minSize: 105,
    maxSize: 400,
    margins: '0 0 0 0',
    layout: 'fit',
	items:[blocCreValAppAudPanel]
    },
    
    
{   region:'center',
    id: 'center-panel',
	//collapsible: true,
     //html:'<img src="客户基本信息.JPG"/>', 
  layout : 'fit',
				items:[tp]
    }] 
    
    
      });
	  
		/*
			var eastpanle = Ext.get('east-panel-xcollapsed');
			var eastpanlex = Ext.get('east-panel-xsplit');
			
			if(eastpanle!=null){
				eastpanle.on('mousemove', function(){
			                var e = Ext.getCmp('east-panel');
			               e.collapsed ? e.expand() : e.collapse();
		  });
	  }else
	  {
	  eastpanlex.on('mousemove', function(){
			                var e = Ext.getCmp('east-panel');
			               e.collapsed ? e.expand() : e.collapse();}};

				})
	  
	  }

*/


    
});