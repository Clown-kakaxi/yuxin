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
		
		var loveChannelCkgOld = new UX.form.CheckboxGroup({
			columns:1,
			boxName:'accountContentsOld',
//			fieldLabel: '境内客户',
			labelStyle: 'text-align:right;',
			labelWidth : 150,
			dataUrl : basepath + '/lookup.json?name=OPEN_ACCOUNT_STATE_JN',
			anchor : '95%'
		});
		var loveBusiTypeCkgOld = new UX.form.CheckboxGroup({
			columns:1,
			boxName:'accountContentsOld',
//			fieldLabel: '境外客户',
			labelStyle: 'text-align:right;disabled:true',
			labelWidth : 150,
			dataUrl : basepath + '/lookup.json?name=OPEN_ACCOUNT_STATE_JW',
			anchor : '95%'
		});
		
		var loveChannelCkg = new UX.form.CheckboxGroup({
			columns:1,
			boxName:'accountContents',
//			fieldLabel: '境内客户',
			labelStyle: 'text-align:right;',
			labelWidth : 150,
			dataUrl : basepath + '/lookup.json?name=OPEN_ACCOUNT_STATE_JN',
			anchor : '95%'
		});
		var loveBusiTypeCkg = new UX.form.CheckboxGroup({
			columns:1,
			boxName:'accountContents',
//			fieldLabel: '境外客户',
			labelStyle: 'text-align:right;disabled:true',
			labelWidth : 150,
			dataUrl : basepath + '/lookup.json?name=OPEN_ACCOUNT_STATE_JW',
			anchor : '95%'
		});
		
		
		var rsRecord = new Ext.data.Record.create([
		                                           {name:'custId',mapping:'CUST_ID'},
		                                           {name:'accountContents',mapping:'ACCOUNT_CONTENTS',readOnly:true},
		                                           {name:'isDomesticCust',mapping:'IS_DOMESTIC_CUST',readOnly:true},
		                                           {name:'accountContentsOld',mapping:'ACCOUNT_CONTENTS_OLD',readOnly:true},
		                                           {name:'isDomesticCustOld',mapping:'IS_DOMESTIC_CUST_OLD',readOnly:true},
		                                           {name:'state',mapping:'STATE'}
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
				title:'开通账户信息[修改后]',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				anchor : '95%',
				items:[ 
				       {id:'idjn',xtype:'radio',fieldLabel:'境内客户',name:'jn',labelWidth : 140},
				       loveChannelCkg,        	
				       {id:'idjw',xtype:'radio',fieldLabel:'境外客户',name:'jw',labelWidth : 140},
				       loveBusiTypeCkg
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
			   	title:'开通账户信息[修改前]',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				anchor : '95%',
				items:[ 
				        {id:'idjnOld',xtype:'radio',fieldLabel:'境内客户',name:'jnOld',labelWidth : 140},
				        loveChannelCkgOld,        	
						{id:'idjwOld',xtype:'radio',fieldLabel:'境外客户',name:'jwOld',labelWidth : 140},
						loveBusiTypeCkgOld
					   ]
				
			}]
		});
		
		var store = new Ext.data.Store({
			restful:true,
		    proxy : new Ext.data.HttpProxy({
				url:basepath + '/acrmFCiAccountInfo.json'
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
						var jOld = data.isDomesticCustOld;
						var j = data.isDomesticCust;
						if(j=='0'){//境内
							opForm.getForm().findField('jn').setValue(true);
							loveChannelCkg.setValue(data.accountContents);
							loveChannelCkg.setDisabled(true);
							loveBusiTypeCkg.setDisabled(true);
						}else{//境外
							opForm.getForm().findField('jw').setValue(true);
							loveBusiTypeCkg.setValue(data.accountContents);
							loveChannelCkg.setDisabled(true);
							loveBusiTypeCkg.setDisabled(true);
						}
						if(jOld=='0'){//境内
							opFormOld.getForm().findField('jnOld').setValue(true);
							loveChannelCkgOld.setValue(data.accountContentsOld);
							loveChannelCkgOld.setDisabled(true);
							loveBusiTypeCkgOld.setDisabled(true);
						}else{//境外
							opFormOld.getForm().findField('jwOld').setValue(true);
							loveBusiTypeCkgOld.setValue(data.accountContentsOld);
							loveChannelCkgOld.setDisabled(true);
							loveBusiTypeCkgOld.setDisabled(true);
						}
					}
				}
			}
		};
	});
