Ext.onReady(function() {
	Ext.QuickTips.init();
			var grade_Store = new Ext.data.SimpleStore({
									fields : ['key', 'value'],
									data : [['高', '高'], ['中', '中'],['低', '低']]
							});
			var cycle_Store = new Ext.data.SimpleStore({
								fields : ['key', 'value'],
								data : [['一个月以内', '一个月以内'], ['一季度以内', '一季度以内'],['半年以内', '半年以内'],['一年以内', '一年以内'],['一年以上', '一年以上']]
							});
		// 最终展现的panel
		var listPanel = new Mis.Ext.CrudPanel( {
			id : "listPanel",
			title : "客户存款流失预警设置",
			stUrl : basepath + '/LossAmountWarn!indexPage.json',
			//新增URL，如果不定义则不出现新增按钮
			addUrl : basepath + '/LossAmountWarn.json',
			updateUrl : basepath + '/LossAmountWarn.json',
			deUrl : basepath + '/LossAmountWarn!batchDestroy.json',
			primary : "id",
			checkbox : true,
			//定义查询条件Form的高度
			seFormHeight : 100,
			//定义增删详情页面弹出窗口高度
			winHeight : 200,
			//宽度
			winWidth : 600,
			//设置分页每页显示条数，若不设置则不出现分页栏
			pagesize : 20,
			// 查询字段定义，若不定义则不出现查询条件From
			selectItems :new Ext.form.FieldSet({items:[
				util.layout._tr([util.form._td({name : 'transTimes',xtype : 'textfield',fieldLabel : '交易次数'})],
								[util.form._td({name : 'alertLevel',xtype : 'combo',fieldLabel : '预警等级',store : grade_Store,valueField : 'key',displayField : 'value'})]
								)
			]}),
	
			//查询列表字段定义，有header属性则在页面显示
			//如果需要做映射需要定义store , mappingkey ,mappingvalue 三个属性
			gclms : [ 
			    {name : 'id'	},  
			    {name : 'alertLevel',header : '预警等级',type :'mapping',store : grade_Store, mappingkey : 'key',mappingvalue : 'value'}, 
			    {name : 'transTerm',header : '交易周期',type :'mapping',store : cycle_Store, mappingkey : 'key',mappingvalue : 'value'}, 
				{name : 'transAmtFrom',header : '交易金额',renderer :function(v1,c,record){
				         if(v1 !=null&&v1!=''){
				         	var v2 = record.json.transAmtTo;
				         	return  v1 = '从'+v1+'至'+v2+'之间';
				         }
				        }},
				{name : 'transAmtTo'},
				{name : 'transTimes',header : '交易次数'}
			],
			
			// 新增、修改、详情的form的字段
			formColums :function(){
				return new Ext.form.FieldSet({items:[
					util.layout._tr([util.form._td({name : 'alertLevel',fieldLabel : '预警等级',xtype : 'combo',store : grade_Store, valueField : 'key',displayField : 'value',allowBlank :false,blankText :'该项为必填项'})],
									[util.form._td({name : 'transTerm',fieldLabel : '交易周期',xtype : 'combo',store : cycle_Store, valueField : 'key',displayField : 'value'})]
									),
					util.layout._tr([util.form._td({id :'transAmtFrom',name : 'transAmtFrom',fieldLabel : '交易金额',xtype : 'numberfield',maxLength : 15,allowBlank :false,blankText :'该项为必填项'})],
									[util.form._td({name : 'transAmtTo',labelWidth:60,fieldLabel : '至',xtype : 'numberfield',maxLength : 15,vtype : 'value_rule',vtypeText :'上限值小于下限值，请检查！',allowBlank :false,blankText :'该项为必填项'})]
									),
					util.layout._tr([util.form._td({name : 'transTimes',fieldLabel : '交易次数',	value:0,xtype : 'numberfield'})],
									[util.form._td({xtype : 'hidden'})]
					),
					util.layout._tr([util.form._td({name : 'id',xtype : 'hidden'})]
					)
			]})}

		});
			Ext.apply(Ext.form.VTypes,   
			{  
  			value_rule:function(val,field){
				var v1 = Ext.getCmp('transAmtFrom').getValue();
				if(v1=='')v1=0;
				if(val=='')val=0;
				if(val<v1){
					return false;
				}
				return true;
				} 
		});  
		// 布局模型
		var viewport = new Ext.Viewport( {
			layout : 'fit',
			items : [ listPanel ]
		});
		
		
		
	});
