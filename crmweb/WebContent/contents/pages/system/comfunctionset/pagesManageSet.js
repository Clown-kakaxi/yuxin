
/**
 * @author songxs
 * @since 2013-2-9
 * 页面操作设置
 *  
 */
Ext.onReady(function(){
	Ext.QuickTips.init(); 
	var shorts = parent.INDEXUTIL.shortCuts;//获取Com.yuchengtech.crm.index.js右上角的快捷图标的配置
	ElementCheckBox=Ext.extend(Ext.form.FieldSet,{//重新一个类型为checkboxgroup的fieldset
		constructor:function(){
		ElementCheckBox.superclass.constructor.call(this,{
			layout:'column',
			xtype:'checkboxgroup'
		});
		}
	});	
	var checkElement =  new ElementCheckBox({//继承重写的fieldset,定义一个checkboxgroup
		layout:'column',
		xtype:'checkboxgroup'
	});
	function generateCheckbox(){//动态生成checkBox,展示右上角的快捷图标的配置
		for(var i=0;i<shorts.length;i++){
			if(shorts[i].id != 'preGroup'&&shorts[i].id != 'nextGroup'&&shorts[i].beControlled == true){
				var _checkbox=new  Ext.form.Checkbox({   
					id:shorts[i].id,               
					name:shorts[i].id,
					boxLabel : shorts[i].title,                  
					inputValue : shorts[i].id,
					checked:shorts[i].enable
				});
				checkElement.add(_checkbox);
		}
		}
	}
	generateCheckbox();
	var reOpenStore  = new Ext.data.ArrayStore({//超过最大页签限制时动作Store
		fields : [ 'value', 'text' ],
		data : [ [ 0, '关闭最早页签，打开新页签' ], [ 1, '提示页签超限，不打开新页签' ] ]
	});
	function convertValue(value){//转换值方法，如果value为true,则返回的realValue为0，若为false,则返回的realValue为1
		if(value == true){
			realValue = '0';
		}else if(value == false){
			realValue = '1';
		}
		return value;
	}
	var pageSetForm =new Ext.form.FormPanel({//展示的formPanel
		title:"页面操作设置",
		labelWidth : 15, // 标签宽度
		autoScroll : true,
		frame : true, // 是否渲染表单面板背景色
		labelAlign : 'middle', // 标签对齐方式
		buttonAlign : 'center',
		region:'center',
		split:true,
		layout:'column',
		style:"padding:5px", 
		items : [{
			columnWidth : .50,
			layout : 'form',
			items:[{
				title: '是否设置最大页签数',
				id:'pageSetFlag1',
				xtype: 'fieldset',
				checkboxToggle: true,
				animCollapse :true,
				hideParent : false,
				height: 100,
				anchor:'98%',
				items:[{
					columnWidth : .9,
					layout : 'form',
					items:[{
						xtype: 'compositefield',
						fieldLabel: '',
						combineErrors: false,
						items:[{
							xtype:'displayfield',
							value:'最大页签数'
						},{
							xtype:'numberfield',
			    			id:'tabMaxCount',
			    			width: 80,
			    			minValue :1,//最大值
			    			allowNegative:false,//正数
			    			decimalPrecision:0,//整数
			    			allowBlank: true,
			    			anchor:'80%'
						},{
							xtype: 'compositefield',
							fieldLabel: '',
							combineErrors: false,
							items:[{
								xtype:'displayfield',
								value:'(最大页签个数，小于1时，为无上限)'
							}]
						}]
					}]
				},{
					columnWidth : .9,
					layout : 'form',
					items:[{   
						xtype: 'compositefield',
						fieldLabel: '',
						combineErrors: false,
						items:[{
							xtype:'displayfield',
							value:'超过最大页签限制时将执行            '
						},{
							xtype : 'combo',
							width:180,
							anchor : '90%',
							name : 'reopenOrAlert',
							id:'reopenOrAlert',
							store : reOpenStore,
							valueField : 'value',
							displayField : 'text',
							triggerAction : 'all',
							mode : 'local',
							listeners:{
							select : function() {
								var t = Ext.getCmp('reopenOrAlert').getValue();
								if(t == '1'){
									Ext.getCmp('composit').show();
								}else{
									Ext.getCmp('composit').hide();}
						}
						}
						},{
							xtype:'displayfield',
							value:'动作'
						}]
					},{
						xtype: 'compositefield',
						id:'composit',
                        fieldLabel: '',
                        hidden:true,
                        combineErrors: false,
                        items:[{
                        	id:'t1',
                        	xtype:'displayfield',
                        	value:'超过页签最大个数时，提示信息'
                        },{
                        	xtype:'textfield',
                        	id:'countOverMsg',
                        	width: 180,
                        	allowBlank: true,
                        	anchor:'80%',
                        	value:'已超过最大页签数'
                        }]
					}]
				}]
			},{
				title: '右键点击页签开关',
				id:'indexCfg_2',
				xtype: 'fieldset',
				animCollapse :true,
				checkboxToggle: true,
				height: 40,
				anchor:'98%',
				items:[{
					xtype:'displayfield',
					value:'右键点击页签成功开启'
				}]
			},{
				title: '点击“首页”一级菜单是否刷新首页',
				id:'indexCfg_3',
				xtype: 'fieldset',
				checkboxToggle: true,
				animCollapse :true,
				height: 40,
				anchor:'98%',
				items:[{
					xtype:'displayfield',
					value:'点击“首页”一级菜单刷新首页'
				}]
			},{
				title:'首页面内容URL',
				xtype:'fieldset',
				height: 70,
				anchor:'98%',
				items:[{
					xtype: 'compositefield',
					fieldLabel: '',
					combineErrors: false,
					items:[{
						xtype:'displayfield',
						value:'首页面内容URL'
					},{
						xtype:'textfield',
		    			id:'indexContentUrl',
		    			width: 250,
		    			allowBlank: false,
		    			anchor:'80%'
					}]
				}]
			},{
				title:'打开已开链接刷新该页面',
				id:'indexCfg_4',
				xtype:'fieldset',
				checkboxToggle: true,
				animCollapse :true,
				height: 50,
				anchor:'98%',
				items:[{
					xtype: 'compositefield',
					fieldLabel: '',
					combineErrors: false,
					items:[{
						xtype:'displayfield',
						value:'打开已开链接刷新该页面'
					}]
				
			}]
				},{
				title:'是否启用页签标题栏拖动功能',
				id:'indexCfg_5',
				xtype:'fieldset',
				checkboxToggle: true,
				animCollapse :true,
				height: 40,
				anchor:'98%',
				items:[{
					xtype:'displayfield',
					value:'启用页签标题栏拖动功能'
				}]
			},{
				title:'页签名称过长时，是否截取tab名称，以“...”补充',
				id:'indexCfg_6',
				xtype:'fieldset',
				checkboxToggle: true,
				animCollapse :true,
				height: 50,
				anchor:'98%',
				items:[{	
			    		xtype: 'compositefield',
			    		fieldLabel: '',
			    		combineErrors: false,
			    		items:[{
			    			xtype:'displayfield',
			    			value:'页签名称最大长度'
			    		},{
			    			xtype:'numberfield',
				    		id:'tabNameMaxLen',
				    		width: 50,
			    			minValue :1,//最小值
			    			allowNegative:false,//正数
			    			decimalPrecision:0,//整数
			    			allowBlank: true,
			    			value:'0',
			    			anchor:'80%'
			    		}]
			    	}]
			},{
				title:'子菜单名字过长时，是否截取菜单名称，以“...”代替',
				id:'indexCfg_7',
				xtype:'fieldset',
				checkboxToggle: true,
				animCollapse :true,
				height:'50',
				anchor:'98%',
				items:[{
					xtype: 'compositefield',
		    		fieldLabel: '',
		    		combineErrors: false,
		    		items:[{
		    			xtype:'displayfield',
		    			value:'截取子菜单名称长度'
		    		},{
		    			xtype:'numberfield',
			    		id:'subMenusNameMaxLen',
			    		width: 50,
		    			minValue :1,//最小值
		    			allowNegative:false,//正数
		    			decimalPrecision:0,//整数
		    			allowBlank: true,
		    			value:'0',
		    			anchor:'80%'
		    		}]
				}]
			}]

		},{
			columnWidth : .50,
			layout : 'form',
			items:[{
				title:'二级菜单展开设置',
				id:'hhhh',
				xtype:'fieldset',
				anchor:'98%',
		    	items:[{
		    		xtype: 'radiogroup',
		    		id:'indexCfg_8',
		    		name:'expandingSubMenuMode',
		    		columns: [.5,.5],
		    		items:[
		    		       {inputValue :'1',name:'indexCfg_8',boxLabel: '展开二级菜单时，合上其他二级菜单',checked:true},
		    		       {inputValue :'2',name:'indexCfg_8',boxLabel: '二级菜单展示各不干扰'}
		    		       ]
		    	}]
			},{
				title:'启用左侧子菜单隐藏功能	',
				id:'indexCfg_9',
				xtype:'fieldset',
				checkboxToggle: true,
		    	animCollapse :true,
		    	height: 40,
		    	anchor:'98%',
		    	items:[{
		    		xtype:'displayfield',
		    		value:'启用左侧子菜单隐藏功能'
		    	}]
			},{
				title:'是否启用二级菜单悬浮策略',
				id:'indexCfg_10',
				xtype:'fieldset',
	    		checkboxToggle: true,
		    	animCollapse :true,
		    	height: 40,
		    	anchor:'98%',
		    	items:[{
		    		xtype:'displayfield',
		    		value:'启用二级菜单悬浮策略'
		    	}]
			},{
				title:'点击一级菜单是否打开默认页面',
				id:'indexCfg_11',
				xtype:'fieldset',
	    		checkboxToggle: true,
	    		animCollapse :true,
		    	height: 40,
		    	anchor:'98%',
		    	items:[{
		    		xtype:'displayfield',
		    		value:'点击一级菜单打开默认页面'
		    	}]
			},{
				title:'菜单初始化失败，是否重新查询菜单信息',
				id:'indexCfg_12',
				xtype:'fieldset',
				checkboxToggle: true,
				animCollapse :true,
		    	height: 80,
		    	anchor:'98%',
		    	items:[{
		    		xtype: 'compositefield',
		    		fieldLabel: '',
                    combineErrors: false,
                    items:[{
                    	xtype:'displayfield',
                    	value:'菜单初始化失败，重新加载菜单信息次数'
                    },{
                    	xtype:'numberfield',
                    	id:'menuReloadCount',
		    			width: 50,
		    			minValue :1,//最小值
		    			allowNegative:false,//正数
		    			decimalPrecision:0,//整数
		    			allowBlank: true,
		    			anchor:'80%'
		    			//value:3
                    }]
		    	},{
		    		xtype: 'compositefield',
                    fieldLabel: '',
                    combineErrors: false,
                    items:[{
                    	xtype:'displayfield',
                    	value:'重新加载菜单时间间隔'
                    },{
                    	xtype:'numberfield',
			    		id:'menuReloadDelayMs',
			    		width: 50,
			    		allowNegative:false,//正数
		    			decimalPrecision:0,//整数
		    			allowBlank: true,
		    			anchor:'80%'
                    },{
                    	xtype:'displayfield',
                    	value:'(ms)'
                    }]
		    	}]
			},{
				title:'加载等待提示信息',
				xtype:'fieldset',
	    		checkboxToggle: true,
		    	animCollapse :true,
		    	height: 50,
		    	anchor:'98%',
		    	items:[{
		    		xtype: 'compositefield',
		    		fieldLabel: '',
                    combineErrors: false,
                    items:[{
                    	xtype:'displayfield',
                    	value:'加载等待提示信息'
                    	
                    },{
                    	xtype:'textfield',
			    		id:'waitMsg',
		    			width: 150,
		    			allowBlank: true,
		    			anchor:'80%'
                    }]
		    	}]
			},{
				title:'是否启用页签栏的快捷功能',
				id:'indexCfg_13',
				xtype:'fieldset',
	    		checkboxToggle: true,
		    	animCollapse :true,
		    	height: 40,
		    	anchor:'98%',
		    	items:[{
		    		xtype:'displayfield',
		    		value:'启用页签栏的快捷功能'
		    	}]
			},{
				title:'对右上角快捷方式控制的设置',
				id:'beControlled',
				xtype:'fieldset',
			    height: 80,
			    anchor:'98%',
			    items:[
			           checkElement
			    ]
			}]
			}],
			tbar:[{
				text:'保存',
				iconCls: 'editIconCss',
				height :'30',
				handler:function(){
				var vModel = [];//向后台传的数组
				var firstModel = [];
				firstModel = ['tabMaxCount','reopenOrAlert','countOverMsg','indexContentUrl','tabNameMaxLen','subMenusNameMaxLen','menuReloadCount',
						'menuReloadDelayMs','waitMsg'];//将一些特殊书的属性放入一个数组，这样循环起来比较简单
				for(var h = 0;h<firstModel.length;h++){
					var pareModel ={};
					var firstValue = Ext.getCmp(firstModel[h]).getValue();//取相应属性的值
					pareModel.value = firstValue;
					pareModel.indexCfgName = 'indexCfg_'+firstModel[h];
					vModel.push(pareModel);//将每组数都放入一个数组中
				}
				for(var i = 2;i<=13;i++){//针对那些fieldset展示的属性，取值的处理
					var perModel = {};
					if(i==8){
						perModel.value = Ext.getCmp('indexCfg_'+i).getValue().inputValue;
					}else{
						var value = !Ext.getCmp('indexCfg_'+i).collapsed;
						convertValue(value);//调用自定义的convertValue()的方法进行数值转换
						perModel.value = realValue;//将转换后的数值进行赋值
					}
					switch(i){
						case 1:
							perModel.indexCfgName = 'indexCfg_tabMaxCount';

						case 2:
							perModel.indexCfgName = 'indexCfg_rigthKeyEnable';
							break;
						case 3:
							perModel.indexCfgName = 'indexCfg_refreshMainPage';
							break;
						case 4:
							perModel.indexCfgName = 'indexCfg_pageReloadable';
							break;
						case 5:
							perModel.indexCfgName = 'indexCfg_titleDraggable';
							break;
						case 6:
							perModel.indexCfgName = 'indexCfg_tabNameShort';
							break;
						case 7:
							perModel.indexCfgName = 'indexCfg_subMenusNameShort';
							break;
						case 8 :
							perModel.indexCfgName = 'indexCfg_expandingSubMenuMode';
							break;
						case 9 :
							perModel.indexCfgName = 'indexCfg_subMenuHidable';
							break;
						case 10:
							perModel.indexCfgName = 'indexCfg_subSuspensionable';
							break;
						case 11:
							perModel.indexCfgName = 'indexCfg_mainMenuPage';
							break;
						case 12:
							perModel.indexCfgName = 'indexCfg_menuReloadable';
							break;
						case 13:
							perModel.indexCfgName = 'indexCfg_titleButtons';
							break;
						default: break;

				
							

					}
				vModel.push(perModel);
				}
				for(var j = 0;j<checkElement.items.length;j++){//对右上角快捷图标的属性值的处理
					var perModel = {};
					perModel.indexCfgName = 'indexCfg_'+ checkElement.items.items[j].id;
					var value = Ext.getCmp(checkElement.items.items[j].id).getValue();
					convertValue(value);
					perModel.value =  realValue; 
					vModel.push(perModel);

				}
				Ext.Ajax.request( {
					url : basepath + '/pageSetManageAction!updatePageSet.json',
					method : 'POST',
					params : {
						'models' : Ext.encode(vModel)
					},
					success : checkResult,
					failure : checkResult
				});
				function checkResult(response) {
					var resultArray = Ext.util.JSON.decode(response.status);
					var resultError = response.responseText;
					if ((resultArray == 200 || resultArray == 201)&& resultError == '') {
						Ext.Msg.alert('系统提示信息', '<span  style="color:red" >操作成功，如需生效，请刷新整体页面！</span>');
					} else {
						if (resultArray == 403) {
							Ext.Msg.alert('系统提示信息',response.responseText);
						} else {
							Ext.Msg.alert('系统提示信息','操作失败,失败原因:' + resultError);
						}
					}
				}
			}
			}]
	});
	function giveValue (){//进入页面后的数值初始化
		if(parent.INDEXUTIL.tabMaxCount>0){//如果设置了最大页签数
			Ext.getCmp('pageSetFlag1').collapsed = false;//是否设置最大页签数的标志	
			Ext.getCmp('tabMaxCount').setValue(parent.INDEXUTIL.tabMaxCount);//设置最大页签数的数量
			var t1 = parent.INDEXUTIL.reopenOrAlert; // 超过最大页签限制时动作
			var t2;
			if(t1== true){//初始化时，最大页签数传过来的是true or false,但是展示的时候用的是store下拉框，需要进行值转换
				t2 = '0';
			}else if(t1 == false){
				t2 = '1';
			}
			Ext.getCmp('reopenOrAlert').setValue(t2);//将转换的后的值给reopenOrAlert赋值
			if(t1 == false){//如果超过最大页签限制动作设置为false的时候，即动作为"提示页签超限，不打开新页签"
				Ext.getCmp('countOverMsg').setValue(parent.INDEXUTIL.countOverMsg);//设置提示信息
				Ext.getCmp('composit').show();
			}
		}else{
			Ext.getCmp('pageSetFlag1').collapsed = true;
		}
		Ext.getCmp('indexCfg_2').collapsed = !parent.INDEXUTIL.rigthKeyEnable;//右键点击页签开关
		Ext.getCmp('indexCfg_3').collapsed = !parent.INDEXUTIL.refreshMainPage;//点击“首页”一级菜单是否刷新首页
		var str = parent.INDEXUTIL.indexContentUrl;//首页面内容URL
		Ext.getCmp('indexContentUrl').setValue(str.substring(basepath.length));//首页面内容URL
		Ext.getCmp('indexCfg_4').collapsed = !parent.INDEXUTIL.pageReloadable;//打开已开链接是否刷新该页面
		Ext.getCmp('indexCfg_5').collapsed = !parent.INDEXUTIL.titleDraggable;// 是否启用页签标题栏拖动功能
		Ext.getCmp('indexCfg_6').collapsed = !parent.INDEXUTIL.tabNameShort;//页签名称过长时，是否截取tab名称，以“...”补充
		if(parent.INDEXUTIL.tabNameShort){//如果tabNameShort为true,则要设置页签名称最大长度
			Ext.getCmp('tabNameMaxLen').setValue(parent.INDEXUTIL.tabNameMaxLen);//页签名称最大长度，在tabNameShort为true时生效
		}
		Ext.getCmp('indexCfg_7').collapsed = !parent.INDEXUTIL.subMenusNameShort;//子菜单名字过长时，是否截取菜单名称，以“...”代替
		if(parent.INDEXUTIL.subMenusNameShort){//如果sunMenusNameShort为true时，则要设置截取菜单名称长度，
			Ext.getCmp('subMenusNameMaxLen').setValue(parent.INDEXUTIL.subMenusNameMaxLen);//截取菜单名称长度，subMenusNameShort为true时生效,例如，若设置长度为9，二、三级菜单名称最大长度为9，往下逐级减1.
		}
		var expandingSubMenuMode_1 = parent.INDEXUTIL.expandingSubMenuMode;//二级菜单展开设置
		if(expandingSubMenuMode_1){
			Ext.getCmp('indexCfg_8').setValue('1');
		}else{
			Ext.getCmp('indexCfg_8').setValue('2');
		}
		Ext.getCmp('indexCfg_9').collapsed = !parent.INDEXUTIL.subMenuHidable;//启用左侧子菜单隐藏功能	
		Ext.getCmp('indexCfg_10').collapsed = !parent.INDEXUTIL.subSuspensionable;//是否启用二级菜单悬浮策略
		Ext.getCmp('indexCfg_11').collapsed = !parent.INDEXUTIL.mainMenuPage;//点击一级菜单是否打开默认页面
		Ext.getCmp('indexCfg_12').collapsed = !parent.INDEXUTIL.menuReloadable;//菜单初始化失败，是否重新查询菜单信息
		if(parent.INDEXUTIL.menuReloadable){//如果menuReloadable为true,则需要设置菜单初始化失败，重新加载菜单信息次数以及重新加载菜单时间间隔
			Ext.getCmp('menuReloadCount').setValue(parent.INDEXUTIL.menuReloadCount);//重新加载菜单信息次数
	    	Ext.getCmp('menuReloadDelayMs').setValue(parent.INDEXUTIL.menuReloadDelayMs);//重新加载菜单时间间隔
		}
		Ext.getCmp('waitMsg').setValue(parent.INDEXUTIL.waitMsg);//加载等待提示信息
		Ext.getCmp('indexCfg_13').collapsed = !parent.INDEXUTIL.titleButtons;//是否启用页签栏的快捷功能
		}
	giveValue();//进入该页面的初始化方法
	var viewport = new Ext.Viewport({	// 布局模型

		layout : 'fit',
		items : [ {
			layout:'border',
			items : [ pageSetForm ]
		}]
	});

});