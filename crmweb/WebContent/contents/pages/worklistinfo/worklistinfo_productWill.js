Ext.ns('UX.form');
UX.form.CheckboxGroup=Ext.extend(Ext.form.CheckboxGroup,{
	//每行显示多少列，默认3列
	columns:1,
	//数据URL
	dataUrl:'',
	//请求数据URL参数
	params:{},
	//boxName
	boxName:'',//具体每个check的name
	//checkbox对应的inputValue值字段
	labelField:'key',
	//checkbox对应的boxLabel值字段
	valueField:'value',
	/**
	 * 
	 * @param {} val 如：  '1,2,3'
	 * @return {}
	 */
	setValue: function(val){
		//注：此处的空字符是保存时产生的，故要先替换掉
		val = val.replace(/ /g,'')
        val = String(val).split(',');
        this.eachItem(function(item){
            if(val.indexOf(item.inputValue)> -1){
                item.setValue(true);
            }else{
            	item.setValue(false);
            }
        });
        return this;
    },

	/**
	 * 初始化自定义组件
	 */
	initComponent:function(){
		var _this = this;
		if(_this.items==undefined && _this.item==undefined){
			_this.items=[{boxLabel: '',name: _this.name||'',hidden:true, inputValue: ''}];
			_this.initStore();
		}
		UX.form.CheckboxGroup.superclass.initComponent.call(_this);
	},
	/**
	 * 渲染checkboxgroup对应的checkbox项
	 */
	initStore:function(){
		var _this = this;
		if(_this.dataUrl != ""){
			new Ext.data.Store({
		        restful : true,
		        autoLoad : true,
		        sortInfo : {
		            field:_this.valueField,
		            direction:'ASC'
		        },
		        proxy : new Ext.data.HttpProxy({
		            url : _this.dataUrl
		        }),
		        reader : new Ext.data.JsonReader({
		            root : 'JSON'
		        }, [ _this.labelField, _this.valueField ]),
		        listeners:{
					load:function(store,records){
						var columns =_this.panel.items;
						for(var i=0;i<columns.items.length;i++){
							var column = columns.items[i];
							column.removeAll();
						}
						_this.items.clear();
						for(var i=0,k=0;i<records.length;i++){
							var d = records[i].data;
							var chk = new Ext.form.Checkbox({boxLabel: d[_this.valueField],name: _this.boxName||'',hideMode:'display', inputValue: d[_this.labelField]});
							var column=columns.items[k];
							k++;
							if(k >= columns.items.length) k=0;
							checkbox = column.add(chk);
							_this.items.add(checkbox);
						}
						_this.doLayout();
						_total_ckg = _total_ckg - 1;
					}
				}
		    });
		}
	}
});

//定义总共有多少个checkboxgroup，
var _total_ckg = 0;//默认为0，具体使用时赋值，当使其全部load完毕之后	

Ext.onReady(function() {
	    _total_ckg = 2;
		Ext.QuickTips.init();
		var instanceid = curNodeObj.instanceid;
		var id = instanceid.split('_')[1];
		var nodeid = curNodeObj.nodeid;
		
		var collateralOld = new UX.form.CheckboxGroup({
			columns:5,
			boxName:'collateralOld',
			fieldLabel: '担保品',
			labelStyle: 'text-align:right;',
			labelWidth : 150,
			dataUrl : basepath + '/lookup.json?name=COLLATERAL_TYPE',
			anchor : '95%'
		});

		var productOld = new UX.form.CheckboxGroup({
			columns:5,
			boxName:'productTypeOld',
			fieldLabel: '产品类型',
			labelStyle: 'text-align:right;',
			labelWidth : 150,
			dataUrl : basepath + '/lookup.json?name=PRODUCT_WILL_TYPE',
			anchor : '95%'
		});

		var collateral = new UX.form.CheckboxGroup({
			columns:5,
			boxName:'collateral',
			fieldLabel: '担保品',
			labelStyle: 'text-align:right;',
			labelWidth : 150,
			dataUrl : basepath + '/lookup.json?name=COLLATERAL_TYPE',
			anchor : '95%'
		});

		var product = new UX.form.CheckboxGroup({
			columns:5,
			boxName:'productType',
			fieldLabel: '产品类型',
			labelStyle: 'text-align:right;',
			labelWidth : 150,
			dataUrl : basepath + '/lookup.json?name=PRODUCT_WILL_TYPE',
			anchor : '95%'
		});

		
		var rsRecord = new Ext.data.Record.create([
		                                           {name:'custId',mapping:'CUST_ID'},
		                                           {name:'financialProductsOld',mapping:'FINANCIAL_PRODUCTS_OLD'},//理财产品
		                                           {name:'loanTypeOld',mapping:'LOAN_TYPE_OLD'},//贷款类型
		                                           {name:'collateralOld',mapping:'COLLATERAL_OLD'},//担保品
		                                           {name:'productTypeOld',mapping:'PRODUCT_TYPE_OLD'},//产品类型
		                                           
		                                           {name:'financialProducts',mapping:'FINANCIAL_PRODUCTS'},//理财产品
		                                           {name:'loanType',mapping:'LOAN_TYPE'},//贷款类型
		                                           {name:'collateral',mapping:'COLLATERAL'},//担保品
		                                           {name:'productType',mapping:'PRODUCT_TYPE'},//产品类型
		                                           {name:'test',mapping:''}
		                                       ]);
		var rsreader = new Ext.data.JsonReader( {
			root : 'json.data',
			totalProperty : 'json.count'
		}, rsRecord);

		var opForm = new Ext.form.FormPanel({
			id : 'opForm',
			layout : 'form',
			labelAlign : 'center',
			autoScroll : true,
			frame : true,
			buttonAlign : "center",
			items:[{
				xtype:'fieldset',
				title:'客户产品意愿[修改后]',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				anchor : '95%',
				items:[ 
						{xtype:'checkbox',fieldLabel:'理财产品',name:'financialProducts',labelWidth : 140,inputValue:1},
						{xtype:'radiogroup',fieldLabel:'贷款',name:'loanType', width: 200,
							items:[{boxLabel:'按揭贷款',name:'loanType',inputValue:1},
							       {boxLabel:'抵押(质)贷款',name:'loanType',inputValue:2}
							       ]
						},
						collateral,
						product
				       ]
			
			}]
		});
		
		var opFormOld = new Ext.form.FormPanel({
			id : 'opFormOld',
			layout : 'form',
			labelAlign : 'center',
			autoScroll : true,
			frame : true,
			buttonAlign : "center",
			items:[{
				xtype:'fieldset',
			   	title:'客户产品意愿[修改前]',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				anchor : '95%',
				items:[ 
						{xtype:'checkbox',fieldLabel:'理财产品',name:'financialProductsOld',labelWidth : 140,inputValue:1},
						{xtype:'radiogroup',fieldLabel:'贷款',name:'loanTypeOld', width: 200,
							items:[{boxLabel:'按揭贷款',name:'loanTypeOld',inputValue:1},
							       {boxLabel:'抵押(质)贷款',name:'loanTypeOld',inputValue:2}
							       ]
						},
						collateralOld,
						productOld
					   ]
				
			}]
		});
		
		var store = new Ext.data.Store({
			restful:true,
		    proxy : new Ext.data.HttpProxy({
				url:basepath + '/acrmFCiProductWill.json'
			}),
		    reader: rsreader
		});
		
	    var applyPanel = new Ext.Panel({
	    	autoScroll : true,
	    	layout : 'column',
	    	items : [  {columnWidth : .5,
	    	            items :[opFormOld]
	    	           },{columnWidth : .5,
	    	            items :[opForm]
	    	           } ]
	    	});
	    var bussFieldSetGrid = new Ext.form.FieldSet({
		    animCollapse :true,
		    collapsible:true,
		    title: '流程业务信息',
		    items:[applyPanel]
	   }); 
		var EchainPanel = new Mis.Echain.EchainPanel({
			instanceID:instanceid,
			nodeId:nodeid,
			nodeName:curNodeObj.nodeName,
			fOpinionFlag:curNodeObj.fOpinionFlag,
			approvalHistoryFlag:curNodeObj.approvalHistoryFlag,
			WindowIdclode:curNodeObj.windowid,
			callbackCustomFun:'3_a10##1'
		});
		var view = new Ext.Panel( {
			renderTo : 'viewEChian',
			 frame : true,
			width : document.body.scrollWidth,
			height : document.body.scrollHeight-40,
			autoScroll : true,
			layout : 'form',
			items : [bussFieldSetGrid,EchainPanel]

		});
		
		store.load({
			params : {
				custId : id,
				old:'1'
			},
			method : 'GET',
			callback:function(){
				window.__setFormValue();
			}
		});
		window.__setFormValue = function(){
			if(_total_ckg > 0){
				setTimeout('window.__setFormValue(\''+0+'\');',100);
			}else{
				if(store.getCount() != 0){
					for(var i=0;i<store.getCount();i++){
						var data = store.getAt(i).data;
						opForm.getForm().loadRecord(store.getAt(i));
						collateral.setValue(data.collateral);
			    		product.setValue(data.productType);
			    		opForm.getForm().findField('financialProducts').setValue(data.financialProducts);
			    		opForm.getForm().findField('loanType').setValue(data.loanType);
			    		
			    		collateralOld.setValue(data.collateralOld);
			    		productOld.setValue(data.productTypeOld);
			    		opFormOld.getForm().findField('financialProductsOld').setValue(data.financialProductsOld);
			    		opFormOld.getForm().findField('loanTypeOld').setValue(data.loanTypeOld);
					}
				}
			}
		};
	});
