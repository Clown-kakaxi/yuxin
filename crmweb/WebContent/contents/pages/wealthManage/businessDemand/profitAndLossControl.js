 Ext.onReady(function(){
	        Ext.QuickTips.init(); 
	        debugger;
	    	//证件类型数据集
	    	var certstore = new Ext.data.Store({  
	    		restful:true,   
	    		autoLoad :true,
	    		proxy : new Ext.data.HttpProxy({
	    				url :basepath+'/lookup.json?name=XD000040'
	    		}),
	    		reader : new Ext.data.JsonReader({
	    			root : 'JSON'
	    		}, [ 'key', 'value' ])
	    	});
	    	var custSelectPartAdd = new Com.yucheng.bcrm.common.CustomerQueryField({
	    		fieldLabel : '客户名称',
	    		labelWidth : 100,
	    		name : 'custName',
	    		custtype : '1',// 客户类型:1:对私,2:对公,不设默认全部
	    		custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
	    		singleSelected : true,// 单选复选标志
	    		editable : false,
	    		allowBlank : false,
	    		blankText : '此项为必填项，请检查！',
	    		anchor : '95%',
	    		hiddenName : 'custId'
	    	});
	    	var currentPanel =new Ext.form.FormPanel({//实时监控查询			    
				title:'条件查询',
		       // labelWidth:100,//label的宽度
		        //layout:'fit',
		        height:'475',
		        region:'center',
		        labelAlign:'right',
		        frame:true,
		        autoScroll : true,
		        items:[
		                {
		                    layout:'column',
		                    items:[{
		                    	layout:'form',
		                    	columnWidth:.5,
		                    	items:[custSelectPartAdd]
		                    }]
		                }
		                ],
		        buttonAlign:'center',
		        buttons:[
		        {
		            text:'盈亏监控',
		            handler:function(){
		            	win.show();
		            }
		        }
		        ]

		    
			});
	    	
	//******************************************************************
	    	  var search =  new Ext.form.FormPanel({
			        autoScroll : true,
		        	buttonAlign : "center",
		        	//autoHeight:true,
		        	region:'center',
		        	height:110,
					frame :true,
					items:[{
						layout:'column',
						items:[{
							layout:'form',
							columnWidth:.25,
							items:[{
								xtype:'textfield',
								name:'custName1',
								labelStyle : 'text-align:right;',
								fieldLabel:'客户名称',
								anchor:'90%'
							},{
								xtype:'combo',
								name:'ruleType1',
								triggerAction:'all',
								labelStyle : 'text-align:right;',
								anchor:'90%',
							//	lazyRender:true,
								fieldLabel:'盈亏方向',
								mode:'local',
								store: new Ext.data.ArrayStore({
						        id: 0,
						        fields: ['value','displayText'],
						        data: [[1, '盈'], [2, '亏']]
						               }),
						       valueField:'value',
						       displayField:'displayText'	
							}]
						},{
							layout:'form',
							columnWidth:.25,
							items:[{
								xtype:'textfield',
								name:'allMoney1',
								fieldLabel:'客户编号',
								labelStyle : 'text-align:right;',
								anchor:'90%'
							},{
								xtype:'numberfield',
								name:'perfitOrlossMoney1',
								labelStyle : 'text-align:right;',
								fieldLabel:'盈亏区间',
								anchor:'90%'
							}]
						
						},{
							layout:'form',
							columnWidth:.25,
							items:[{
			                    fieldLabel : '证件类型',
			                    name : 'EDUCATION',
			                    id : 'EDUCATION',
			                    forceSelection : true,
			                    resizable : true,
			                    xtype : 'combo',
			                    labelStyle : 'text-align:right;',
			                    triggerAction : 'all',
			                    mode : 'local',
			                    store : certstore,
			                    valueField : 'key',
			                    displayField : 'value',
			                    emptyText : '请选择',
			                    anchor : '90%'
			                },{
								xtype:'numberfield',
								name:'perfitOrlossMoney1',
								labelStyle : 'text-align:right;',
								fieldLabel:'到',
								anchor:'90%'
							}]
						
						},{
							layout:'form',
							columnWidth:.25,
							items:[{
								xtype:'textfield',
								name:'allMoney1',
								labelStyle : 'text-align:right;',
								fieldLabel:'证件编号',
								anchor:'90%'
							}]
						
						}]
					}],
					buttons:[{
						text:'查询',
						handler:function(){
							
						}
					},{
						text:'重置',
						handler:function(){
							search.getForm().reset();
						}
					}]
				});
		    var sm = new Ext.grid.CheckboxSelectionModel();
			// 定义自动当前页行号
			var rownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			var record= Ext.data.Record.create([
			                 {name:'custId'},
			                 {name:'custname'},
			                 {name:'parpersType'},
			                 {name:'parperNum'},
			                 {name:'allMoney'},
			                 {name:'benefitMoney'},
			                 {name:'benefitScale'}
			                                    ]);
			var cm = new Ext.grid.ColumnModel([
			                  rownum,sm,
			                  {header:'客户号',dataIndex:'custId',align:'right',sortable:true,width:100},
			                  {header:'客户名称',dataIndex:'custname',sortable:true,width:130},
			                  {header:'证件类型',dataIndex:'parpersType',sortable:true,width:200},
			                  {header:'证件编号',dataIndex:'parperNum',align:'right',sortable:true,width:200},
			                  {header:'总投资金额(元)',dataIndex:'allMoney',align:'right',sortable:true,width:150},
			                  {header:'投资效益金额(元)',dataIndex:'benefitMoney',align:'right',sortable:true,width:150},
			                  {header:'投资效益率(%)',dataIndex:'benefitScale',align:'right',sortable:true,width:150}			                  
			                               ]);
			cm.setRenderer(7, getColor);  
		    cm.setRenderer(8, getColor1);  
			  function getColor(val) {  
			      if (val>0) {  
			          return '<font color=blue></font><span style="color:red;">' + Ext.util.Format.usMoney(val) + '</span>';  
			      }else{
			    	  return '<font color=blue></font><span style="color:green;">' + Ext.util.Format.usMoney(val) + '</span>'; 
			      } 
			  }  
			  function getColor1(val) {  
			      if (val>0) {  
			          return '<font color=blue></font><span style="color:red;">'+val+'</span>';  
			      }else{
			    	  return '<font color=blue></font><span style="color:green;">'+val+'</span>';
			      }
			  } 

			/**
			 * 数据存储
			 */
			var data=[
			          ['10021','张三','居民身份证','45294039232342','1232000','32000','2.5'],
			          ['13232','王武','居民身份证','45294663198920','320000','12000','1.8'],
			          ['12322','胡为','居民身份证','45294031990220','232000','-9000','-1.7'],
			          ['32323','欧弱','居民身份证','45294039232423','4332000','62000','2.3'],
			          ['10027','冠家','居民身份证','45294039232222','6732000','122000','2.6'],
			          ['11132','任海','居民身份证','45294039232323','2232000','-70000','-3.3'],
			          ['34323','丛珊','居民身份证','45294039232343','1432000','36000','2.6']			     
			          ];
			var store = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(data),
				reader : new Ext.data.ArrayReader({}, record)
			});
			// 每页显示条数下拉选择框
			var pagesize_combo = new Ext.form.ComboBox({
				name : 'pagesize',
				triggerAction : 'all',
				mode : 'local',
				store : new Ext.data.ArrayStore({
					fields : [ 'value', 'text' ],
					data : [ [ 100, '100条/页' ], [ 200, '200条/页' ],
							[ 500, '500条/页' ],[ 1000, '1000条/页' ]  ]
				}),
				valueField : 'value',
				displayField : 'text',
				value : '100',
				resizable : true,
				width : 85
			});

			// 默认加载数据
			store.load(data);

			// 改变每页显示条数reload数据
			pagesize_combo.on("select", function(comboBox) {
				bbar.pageSize = parseInt(pagesize_combo.getValue()),
				store.reload({
					params : {
						start : 0,
						limit : parseInt(pagesize_combo.getValue())
					}
				});
			});
			// 分页工具栏
			var bbar = new Ext.PagingToolbar({
				pageSize : parseInt(pagesize_combo.getValue()),
				store : store,
				displayInfo : true,
				displayMsg : '显示{0}条到{1}条,共{2}条',
				emptyMsg : "没有符合条件的记录",
				items : [ '-', '&nbsp;&nbsp;', pagesize_combo ]
			});
			var grid = new Ext.grid.GridPanel({
				title:'客户信息列表',
				frame:true,
				autoScroll:true,
				//region:'center',
				store:store,
				height:370,
				stripeRows:true,
				cm:cm,
				sm:sm,
				bbar:bbar,
				loadMask:{
					msg:'正在加载表格数据，请等候。。。。'
				},
				tbar:[{
					text:'盈亏监控',
					iconCls:'addIconCss',
					handler:function(){
						var record = grid.getSelectionModel().getSelected();
						if (record==null || record=="undefined") {
							Ext.Msg.alert('提示','请选择一条记录!');
						} else {
						         win.show();
						}
					}
				}]
			});

	        var addTapPanel = new Ext.TabPanel({
				activeTab : 0,
				tabPosition : 'top',// 控制tab页签显示的位置（顶部：top；底部：bottom）
				//height : 440,
				//layout:'fit',
				buttonAlign : "center",
				items : [{
							title : '日间批量监控',
							items : [search,grid]
						}, {
							title : '实时盈亏监控',
							items : [currentPanel]
						}]
			});
	        var custPanel =  new Ext.form.FormPanel({
		        autoScroll : true,
	        	buttonAlign : "center",
	        	//autoHeight:true,
	        	region:'center',
	        	height:410,
				frame :true,
				items:[{
					layout:'column',
					items:[{
						layout:'form',
						columnWidth:.5,
						items:[{
							xtype:'textfield',
							name:'custName',
							fieldLabel:'客户名称',
							value:'张三',
							anchor:'90%'
						},{
							xtype:'textfield',
							name:'perfitOrlossMoney',
							fieldLabel:'投资效益金额(元)',
							value:'32,000',
							anchor:'90%',
							renderer:function(val){
								if (val>0) {  
							          return '<font color=blue></font><span style="color:red;">' + Ext.util.Format.usMoney(val) + '</span>';  
							      }else{
							    	  return '<font color=blue></font><span style="color:green;">' + Ext.util.Format.usMoney(val) + '</span>'; 
							      } 
							}
						}]
					},{
						layout:'form',
						columnWidth:.5,
						items:[{
							xtype:'textfield',
							name:'allMoney',
							fieldLabel:'总投资金额(元)',
							value:'1,232,000',
							anchor:'90%'
						},{
							xtype:'numberfield',
							name:'perfitOrlossMoney',
							fieldLabel:'投资效益率(%)',
							value:'2.5',
							anchor:'90%'
						}]
					
					}]
				}]
			});
	        var sm1 = new Ext.grid.CheckboxSelectionModel();
			// 定义自动当前页行号
			var rownum1 = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});

			var record1= Ext.data.Record.create([
			                 {name:'accountName'},
			                 {name:'accountId'},
			                 {name:'accountType'},
			                 {name:'benefitMoney'},
			                 {name:'benefitScale'}
			                                    ]);
			var cm1 = new Ext.grid.ColumnModel([
			                  rownum1,sm1,
			                  {header:'账户名称',dataIndex:'accountName',align:'right',sortable:true,width:100},
			                  {header:'账户号',dataIndex:'accountId',sortable:true,width:200},
			                  {header:'账户类型',dataIndex:'accountType',sortable:true,width:140},
			                  {header:'投资效益金额(元)',dataIndex:'benefitMoney',align:'right',sortable:true,width:140},
			                  {header:'投资效益率(%)',dataIndex:'benefitScale',align:'right',sortable:true,width:140}			                  
			                               ]);
			cm1.setRenderer(5, getColor);  
		    cm1.setRenderer(6, getColor1); 
			/**
			 * 数据存储
			 */
			var data1=[
			          ['张三','45294039232342','个人银行结算账户','32000','2.5'],
			          ['张三','45294663198920','基本账户','12000','1.8'],
			          ['张三','45294031990220','一般存款账户','9000','1.7']     
			          ];
			var store1 = new Ext.data.Store({
				proxy : new Ext.data.MemoryProxy(data1),
				reader : new Ext.data.ArrayReader({}, record1)
			});
			// 默认加载数据
			store1.load(data1);
			var accountGrid = new Ext.grid.GridPanel({
				//title:'客户信息列表',
				frame:true,
				autoScroll:true,
				region:'center',
				store:store1,
				//layout:'fit',
				height:410,
				stripeRows:true,
				cm:cm1,
				sm:sm1,
				loadMask:{
					msg:'正在加载表格数据，请等候。。。。'
				}
			});
			//*****************************************************
			 var sm2 = new Ext.grid.CheckboxSelectionModel();
				// 定义自动当前页行号
				var rownum2 = new Ext.grid.RowNumberer({
					header : 'No.',
					width : 28
				});

				var record2= Ext.data.Record.create([
				                 {name:'prodName'},
				                 {name:'keepNum'},
				                 {name:'buyPrice'},
				                 {name:'buyMoney'},
				                 {name:'buyTime'},
				                 {name:'marketPrice'},
				                 {name:'marketMoney'},
				                 {name:'limitTime'},
				                 {name:'benefitMoney'},
				                 {name:'benefitScale'}
				                                    ]);
				var cm2 = new Ext.grid.ColumnModel([
				                  rownum2,sm2,
				                  {header:'产品名称',dataIndex:'prodName',align:'right',sortable:true,width:100},
				                  {header:'持有份额',dataIndex:'keepNum',sortable:true,width:100},
				                  {header:'入手单价',dataIndex:'buyPrice',renderer:function(val){ 
									          return  Ext.util.Format.usMoney(val);  
									},sortable:true,width:100},
				                  {header:'购买金额(元)',dataIndex:'buyMoney',align:'right',sortable:true,width:140},
				                  {header:'购买日期',dataIndex:'buyTime',sortable:true,width:100},
				                  {header:'市值单价',dataIndex:'marketPrice',renderer:function(val){
										if (val>100) {  
									          return '<font color=blue></font><span style="color:red;">' + Ext.util.Format.usMoney(val) + '</span>';  
									      }else if(val<100){
									    	  return '<font color=blue></font><span style="color:green;">' + Ext.util.Format.usMoney(val) + '</span>'; 
									      } 
									},sortable:true,width:100},
				                  {header:'市值金额',dataIndex:'marketMoney',sortable:true,width:100},
				                  {header:'到期日期',dataIndex:'limitTime',sortable:true,width:100},
				                  {header:'效益金额',dataIndex:'benefitMoney',sortable:true,width:100},
				                  {header:'投资效益率(%)',dataIndex:'benefitScale',align:'right',sortable:true,width:140}			                  
				                               ]);
				cm2.setRenderer(10, getColor);  
			    cm2.setRenderer(11, getColor1); 
				/**
				 * 数据存储
				 */
				var data2=[
				          ['100G黄金理财','4532','100','32000','2012-10-23','99','32000','2015-10-23','3000','2.4'],
				          ['100G黄金理财','4532','100','32000','2012-10-23','101','32000','2015-10-23','3000','2.4'],
				          ['100G黄金理财','4532','100','32000','2012-10-23','101','32000','2015-10-23','-3000','-2.4'] 
				          ];
				var store2 = new Ext.data.Store({
					proxy : new Ext.data.MemoryProxy(data2),
					reader : new Ext.data.ArrayReader({}, record2)
				});
				// 默认加载数据
				store2.load(data2);
				var prodGrid = new Ext.grid.GridPanel({
					//title:'客户信息列表',
					frame:true,
					autoScroll:true,
					region:'center',
					store:store2,
					//layout:'fit',
					height:410,
					stripeRows:true,
					cm:cm2,
					sm:sm2,
					loadMask:{
						msg:'正在加载表格数据，请等候。。。。'
					}
				});
				
	        var tabPanel2 = new Ext.TabPanel({//盈亏监控
	            width:'100%',
	            heignt:'100%',
	            activeTab: 0,
	            frame:true,
	            defaults:{autoHeight: true},
	            resizeTabs:true, // turn on tab resizing
	            preferredTabWidth:150,	        
	            items:[
	            	{ 
	    				title: '<span style=\'text-align:center;\'>客户</span>',
	    				items:[custPanel]
	    			},
	                { 
	    				title: '<span style=\'text-align:center;\' >账户</span>',
	    				items:[accountGrid]
	    			},{ 
	    				title: '<span style=\'text-align:center;\' >产品</span>',
	    				items:[prodGrid]
	    			}
	            ]
	        });	
			var win = new Ext.Window(
					{
						title:'盈亏监控',
						width:800,
						height:500,
						closeAction:'hide',
						closable:true,
						maximizable:true,
						buttonAlign:'center',
						border:false,
						layout:'fit',
						draggable:true,
						collapsible:true,
						titleCollapse:true,
						items:[tabPanel2],
						buttons:[{
							text:'返  回',
							handler:function(){
								win.hide();
							}
						}]
					}
					);
			var view = new Ext.Viewport({
				layout:'fit',
				items:[addTapPanel]				
			});
});