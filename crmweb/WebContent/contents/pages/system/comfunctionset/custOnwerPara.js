/***
 * 客户归属参数设置
 */
Ext.onReady(function() {
			Ext.QuickTips.init(); 
			//是否
			var yesnoStore = new Ext.data.ArrayStore(
			{
				fields : [ 'value', 'text' ],
				data : [ [ 1, '是' ], [ 2, '否' ] ]
			});
			var loginIpStore = new Ext.data.ArrayStore(
			{
				fields : [ 'value', 'text' ],
				data : [ [ 1, '全行客户' ], [ 2, '零售客户' ], [ 3, '公司客户' ] ]
			});
			var loginIpStore = new Ext.data.ArrayStore(
			{
				fields : [ 'value', 'text' ],
				data : [ 
		           [ 1, '模式一：客户归属多个机构，每个归属机构下允许分配多个客户经理' ],
		           [ 2, '模式二：客户归属多个机构，每个归属机构下仅允许分配单一客户经理' ], 
		           [ 3, '模式三：客户归属单一机构，归属机构允分配许多个客户经理' ],
		           [ 4, '模式四：客户归属单一机构，归属机构下仅允许分配唯一客户经理' ]
				]
			});
			var loginIpStore = new Ext.data.ArrayStore(
			{
				fields : [ 'value', 'text' ],
				data : [ [ 1, '自动分配' ], [ 2, '手工分配' ], [ 3, '自动分配+手工分配' ] ]
			});
			var loginIpStore = new Ext.data.ArrayStore(
			{
				fields : [ 'value', 'text' ],
				data : [ [ 1, '最早开户的机构' ], 
				         [ 2, '最晚开户的机构' ], 
				         [ 3, '存款账户余额最大的机构' ], 
				         [ 4, '贷款账户余额最大的机构' ], 
				         [ 5, 'AUM之最大的机构' ] ]
			});
			var qForm = new Ext.form.FormPanel({
				title : "客户归属参数设置",
				labelWidth : 15, // 标签宽度
//				autoScroll : true,
				frame : true, // 是否渲染表单面板背景色
				labelAlign : 'middle', // 标签对齐方式
				buttonAlign : 'center',
				region:'center',
				split:true,
					layout : 'column',
					items : [  {
						columnWidth : 1,
						layout : 'form',
						items : [ 
							{
							    title: '客户类型',
							    xtype: 'fieldset',
							    id:'fs1',
							    height: 70,
							    items:[
							       {
							           xtype: 'radiogroup',
							           id:'para1',
							           name:'CustOnwerPara1',
							           columns: [.2,.2,.2],
							           items: [
						                   {name:'para1',boxLabel: '全行客户', checked: true,  inputValue :'1'},
						                   {name:'para1',boxLabel: '零售客户',  inputValue :'2'},
						                   {name:'para1',boxLabel: '公司客户',  inputValue :'3'}
							           ]
							       }
							    ]
							},{
							    title: '客户归属管理模式',
							    xtype: 'fieldset',
							    id:'fs2',
							    height: 90,
							    items:[
							       {
							           xtype: 'radiogroup',
							           id:'para2',
							           name:'CustOnwerPara2',
							           columns: [400,400,400,400],
							           items: [
						                   {inputValue :'1',name:'para2',boxLabel: '模式一：客户归属多个机构，每个归属机构下允许分配多个客户经理', checked: true},
						                   {inputValue :'2',name:'para2',boxLabel: '模式二：客户归属多个机构，每个归属机构下仅允许分配单一客户经理'},
						                   {inputValue :'3',name:'para2',boxLabel: '模式三：客户归属单一机构，归属机构允分配许多个客户经理'},
						                   {inputValue :'4',name:'para2',boxLabel: '模式四：客户归属单一机构，归属机构下仅允许分配唯一客户经理'}
							           ]
							       }
							    ]
							},{
							    title: '归属机构的分配模式',
							    xtype: 'fieldset',
							    id:'fs3',
							    height: 80,
							    items:[
							       {
							           xtype: 'radiogroup',
							           id:'para3',
							           name:'CustOnwerPara3',
							           columns: [.2,.2,.2],
							           items: [
						                   {inputValue :'1',name:'para3',boxLabel: '自动分配', checked: true},
						                   {inputValue :'2',name:'para3',boxLabel: '手工分配'},
						                   {inputValue :'3',name:'para3',boxLabel: '自动分配+手工分配'}
							           ]
							       }
							    ]
							},{
							    title: '自动分配主办机构业务规则',
							    xtype: 'fieldset',
							    id:'fs4',
							    height: 80,
							    items:[
							       {
							           xtype: 'radiogroup',
							           id:'para4',
							           name:'CustOnwerPara4',
							           columns: [.2,.2,.2,.2,.2],
							           items: [
						                   {inputValue :'1',name:'para4',boxLabel: '最早开户的机构', checked: true},
						                   {inputValue :'2',name:'para4',boxLabel: '最晚开户的机构'},
						                   {inputValue :'3',name:'para4',boxLabel: '存款账户余额最大的机构'},
						                   {inputValue :'4',name:'para4',boxLabel: '贷款账户余额最大的机构'},
						                   {inputValue :'5',name:'para4',boxLabel: 'AUM值最大的机构'}
							           ]    
							       }
							    ]
							},{
							    title: '分配客户经理参数',
							    xtype: 'fieldset',
							    id:'fs5',
							    height: 80,
							    items:[
							       {
							           xtype: 'checkboxgroup',
							           id:'para5',
							           name:'CustOnwerPara5',
							           columns: [.2,.2,.2],
							           items: [
						                   {id:'checkbox1',inputValue :'1',boxLabel: '是否启用虚拟客户经理'},
						                   {id:'checkbox2',inputValue :'2',boxLabel: '是否启用账户分配'},
						                   {id:'checkbox3',inputValue :'3',boxLabel: '是否启用账户分润比例'}]
							       } ]
					} ]
				} ],
				buttons : [ {
					text : '保存',
					handler : function() {
						var v1 = Ext.getCmp('para1').getValue();
						var v2 = Ext.getCmp('para2').getValue();
						var v3 = Ext.getCmp('para3').getValue();
						var v4 = Ext.getCmp('para4').getValue();
						var saveStr = "";
						var a1 = Ext.getCmp('para1').getName()+","+Ext.getCmp('fs1').title+","+v1.inputValue+","+v1.boxLabel+";";
						var a2 = Ext.getCmp('para2').getName()+","+Ext.getCmp('fs2').title+","+v2.inputValue+","+v2.boxLabel+";";
						var a3 = Ext.getCmp('para3').getName()+","+Ext.getCmp('fs3').title+","+v3.inputValue+","+v3.boxLabel+";";
						var a4 = Ext.getCmp('para4').getName()+","+Ext.getCmp('fs4').title+","+v4.inputValue+","+v4.boxLabel+";";
						saveStr = a1+a2+a3+a4;
						var detail = Ext.getCmp('para5').getValue();
						var tempId ="",tempName=""; 
						if(detail.length==0)
						{
							Ext.Msg.alert('提示','请勾选 分配客户经理参数 的参数值');
							return false;
						}
						for(var j = 0;j<detail.length;j++)
						{
							if(j==0)
							{
								tempId = tempId+detail[j].inputValue;
								tempName = tempName+detail[j].boxLabel;
							}
							else
							{
								tempId = tempId+'/'+detail[j].inputValue;
								tempName = tempName+'/'+detail[j].boxLabel;
							}
						}
						saveStr = saveStr+Ext.getCmp('para5').getName()+","+Ext.getCmp('fs5').title+","+
									tempId+','+tempName+';';
						
						Ext.Ajax.request({
						    url:basepath+'/custOnwerPara!saveData.json',
						    method: 'POST',
						    params : {
								'saveStr' : saveStr
							},
							success : function(response) {
    							Ext.Msg.alert('提示', '保存成功');
    							store.reload();
    						},
    						failure : function(response) {
    							Ext.Msg.alert('提示', response.responseText);
    						}
						});
						
					}
				
				},{
					text : '重置',
					handler : function() {
	                	qForm.getForm().reset();
	                	store.reload();
					}

				} ]
			});
			
			var record = Ext.data.Record.create([ {
				name : 'ID',
				mapping : 'ID'
			}, {
				name : 'VERSION',
				mapping : 'VERSION'//版本
			},{
				name : 'APP_ID',
				mapping : 'APP_ID'//应用ID
			},{
				name : 'PROP_NAME',
				mapping : 'PROP_NAME'//策略ID
			},{
				name : 'PROP_DESC',
				mapping : 'PROP_DESC'//策略中文
			},{
				name : 'PROP_VALUE',
				mapping : 'PROP_VALUE'//策略值
			},{
				name : 'REMARK',
				mapping : 'REMARK'//策略值中文
			}]);

			/**
			 * 数据存储
			 */
			var store = new Ext.data.Store({
				restful : true,
				proxy : new Ext.data.HttpProxy({
					url : basepath + '/custOnwerParaQuery.json'
				}),
				reader : new Ext.data.JsonReader({
					successProperty : 'success',
					idProperty : 'F_ID',
					messageProperty : 'message',
					root : 'json.data',
					totalProperty : 'json.count'
				}, record)
			});
			store.on('load',function(store,records,options){
				var dataArr = store.data.items;
				for(var i=0;i<dataArr.length;i++)
				{
					var _id = dataArr[i].data.PROP_NAME.split('CustOnwerPara')[1];
					var rec = dataArr[i].data;
					switch (_id-0) {
						case 1:
							Ext.getCmp('para1').setValue(rec.PROP_VALUE);
							break;
						case 2:
							
							Ext.getCmp('para2').setValue(rec.PROP_VALUE);
							break;
						case 3:
							Ext.getCmp('para3').setValue(rec.PROP_VALUE);
							break;
						case 4:
							Ext.getCmp('para4').setValue(rec.PROP_VALUE);
							break;
						
						case 5:
							var temp = rec.PROP_VALUE.split('/');
							for(var j=0;j<temp.length;j++)
							{
								if(Ext.getCmp('checkbox'+temp[j])!= undefined)
									Ext.getCmp('checkbox'+temp[j]).setValue(1);
							}
//							for(var j=0;j<temp.length;j++)
//							{
//								if(Ext.getCmp(temp[j])!= undefined)
//									Ext.getCmp(temp[j]).setValue(1);
//							}
							break;
						
						default:
							break;
					}
				}
			});
			store.load();

			// 布局模型
			var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [ {
					layout:'border',
					items : [ qForm ]
				}]
			});
		});