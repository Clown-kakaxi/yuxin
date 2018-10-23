/**
 * 
* @Description: 对私客户信息首页
* @author wangmk1 
* @date 2014-7-23
*
*/
Ext.onReady(function(){
	//活期存款、定期存款及通知存款、结构性理财、大额存单；需等待这些数据全部查询完毕才能计算总额
	//每查询出一个，depositTotalAmtReadyCount值加1，如果等于5，则开始计算总额
	var depType; //存款类型
	var depositTotalAmtReadyCount = 0;
	var depositTotalAmtError = 0;
	Ext.Msg.wait("正在查询，请稍后","系统消息");
	/**
	 * 按币种汇总余额工具函数
	 */
	function sumByCyc(data, keys, store, cycName , amtName){
	   for(var i = 0;i < store.getCount();i++) {
            var accCcy = store.getAt(i).get(cycName);
            if(!accCcy){
                accCcy = '未知';            
            }
            var accAval = store.getAt(i).get(amtName);
            if(Ext.isArray(accCcy)) {
                accCcy = accCcy[0];
            }
            if(Ext.isArray(accAval)) {
                accAval = accAval[0];
            }
            if(accAval) {
                accAval = 1 * accAval.replace("+", "");
            }
            if(keys.indexOf(accCcy) != -1) {
                data[accCcy] = data[accCcy] + accAval;
            } else {
                keys.push(accCcy);
                data[accCcy] = accAval;
            }
        }
	}
	/**
	 * 处理存款总额
	 */
	function calcDepositTotalAmt() {
		if(depositTotalAmtReadyCount != 5) {
			setTimeout(calcDepositTotalAmt, 1000);
		} else {
			if(depositTotalAmtError==0){
    			Ext.Msg.hide();
			}
			// 计算总额
			totalStore.removeAll();
			var data = {}, keys = [];
			// 处理活期存款
			sumByCyc(data, keys, currentStore, "accCcy", "accAval");
			// 处理定期存款
			sumByCyc(data, keys, termDepositStore, "accCcy", "accAval");
			// 处理通知存款
			sumByCyc(data, keys, noticeDepositStore, "accCcy", "accAval");
			// 处理结构性理财
			sumByCyc(data, keys, productStore, "CCY", "ORDER_BALANCE");
			// 处理大额存单
			sumByCyc(data, keys, CDsStore, "ccycode", "osamt");
			
			Ext.each(keys, function(key) {
				var record = new totalStore.recordType({
					accCcy	: key,
					accAval	: 1*data[key]
				});
				totalStore.add(record);
			});
		}
	}
	setTimeout(calcDepositTotalAmt,1000);
	/**
	 * 鼠标右键菜单
	 * 
	 * @param {}
	 *            e 事件
	 * @param {}
	 *            added 额外的菜单配置
	 */
    function onContextMenu(e,added){
        var windowMenu = Wlj.frame.functions.app.Util.contextMenus.window;
        for(var key in windowMenu){
            var omenu = {};
            omenu.text = windowMenu[key].text;
            omenu.handler = windowMenu[key].fn.createDelegate(this);
            added.push(omenu);
        }
        if(!window.contextmenu){
            window.contextmenu = new Ext.menu.Menu({
                items: added
            });
        }
        window.contextmenu.showAt(e.getPoint())
    }
    //右键菜单
    Ext.getBody().on("contextmenu",function(e){
        e.preventDefault();
        onContextMenu(e,["-"]);
    });
	/**数据字典**/
	//持卡情况
	var cardStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000026'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});   
	//国籍
	var citizenshipStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000025'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	}); 
	//在我行开立账户情况
	var accountStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000013'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	}); 
	//婚姻状况
	var marriageStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000024'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	}); 
	//职业状况career_stat
	var careerStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000046'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	}); 
	//年收入范围
	var incomeStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000149'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	}); 
	//是否开通借记卡
	var ifcardStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000327'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	}); 
	//风险等级
	var riskStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000083'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	}); 
	//客户来源渠道
	var custSourceStore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=XD000353'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
	}); 
	//客户满意度    暂无映射
	//客户满意度
	var data=[{'key':'1','value':'非常满意'},
		  {'key':'2','value':'较为满意'},
		  {'key':'3','value':'不满意'},
		  {'key':'4','value':'很不满意'},
		  {'key':'9','value':'不详'}
		  ];
	var satisfyStore = new Ext.data.JsonStore({
			data:data,
			fields:['key','value']
	});
	//拜访方式
	var visitStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		url :basepath+'/lookup.json?name=VISIT_TYPE',
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	}); 
	//给label 添加click事件，这一段一定要放在label之前
	Ext.form.Label.prototype.afterRender = Ext.form.Label.prototype.afterRender
     .createSequence(function() {
        this.relayEvents(this.el, ['click']);
       });
       
	var customerBaseInfoPanel= new Ext.FormPanel({
		autoHeight:true,
		labelWidth:220,//label的宽度
		labelAlign:'right',
		frame:false,
		autoScroll : true,
		buttonAlign:'center',
		items : [ {
		xtype : 'fieldset',
		title : '基本信息',
		titleCollapse : true,
		collapsible : true,
		autoHeight:true,
		items:[{
//			autoHeight : true,
			layout:'column',
			items:[{
				columnWidth:.33,  
				layout:'form',
				items:[
						{xtype:'displayfield',anchor:'95%',fieldLabel:'客户名称',name:'PERSONAL_NAME',readOnly:true},
						{xtype:'displayfield',anchor:'95%',fieldLabel:'开户日期',name:'CREATE_DATE',readOnly:true},
						{xtype:'displayfield',anchor:'95%',fieldLabel:'国籍',name:'CITIZENSHIP',readOnly:true},
			            {xtype:'displayfield',anchor:'95%',fieldLabel:'婚姻状态',name:'MARRIAGE',readOnly:true},
		              	{xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'职业类别',name:'CAREER_TYPE'},
		              	{xtype:'displayfield',readOnly:true,anchor : '95%',fieldLabel:'卡别',name:'HOLD_CARD_ORA'}
		            	 
					 /*{xtype:'displayfield',width:250,fieldLabel:'客户编号',name:'CUST_ID'},
		              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'客户姓氏',name:'SUR_NAME'},
		              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'客户名字',name:'PERSONAL_NAME'},
		              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'职业',name:'CAREER_TYPE'},
		              {xtype:'displayfield',readOnly:true,anchor : '95%',fieldLabel:'持卡情况',name:'HOLD_CARD_ORA'
//		            	  ,store:cardStore,resizable : false,valueField : 'key',displayField : 'value',
//		            	  mode : 'local',editable : false,emptyText : '未知',selectOnFocus : true
		            	  },
		              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'工作单位',name:'UNIT_NAME'},
		              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'手机号码',name:'MOBILE_PHONE'},
		              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'邮件地址',name:'EMAIL'},
		              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'住宅地址',name:'HOME_ADDR'},
		              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'传真号码',name:'UNIT_FEX'},
		              {xtype:'displayfield',readOnly:true,anchor : '95%',fieldLabel:'在我行开立账户情况',name:'HOLD_ACCT_ORA'
//		             	  ,store:accountStore,resizable : false,valueField : 'key',displayField : 'value',
//		            	  mode : 'local',editable : false,emptyText : '未知',selectOnFocus : true
		            	  }*/
		              ]
				},{
				columnWidth:.33,  
				layout:'form',
				items:[
						{xtype:'displayfield',anchor:'95%',fieldLabel:'核心客户号',name:'CORE_NO',readOnly:true},
						{xtype:'displayfield',anchor:'95%',fieldLabel:'港澳通行证/台胞证号',name:'IDENT_NO2',readOnly:true},
						{xtype : 'displayfield',name : 'IDENT_EXPIRED_DATE2',fieldLabel : '港澳通行证/台胞证到期日',format:'Y-m-d',anchor : '95%',readOnly:true},
						{xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'出生日期',name:'BIRTHDAY'},
						{xtype:'displayfield',anchor:'95%',fieldLabel:'服务单位',name:'UNIT_NAME',readOnly:true},
						{xtype:'displayfield',anchor:'95%',fieldLabel:'邮箱',name:'EMAIL',readOnly:true},
						{xtype:'displayfield',anchor:'95%',fieldLabel:'客户来源',name:'SOURCE_CHANNEL',readOnly:true}
						//{xtype:'displayfield',readOnly:true,anchor : '95%',fieldLabel:'账户状况',name:'HOLD_ACCT_ORA'}
				       /*{xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'客户称谓',name:'PERSON_TITLE'},
			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'国籍',name:'CITIZENSHIP'
//			            	  ,store:citizenshipStore,resizable : false,valueField : 'key',displayField : 'value',
//			            	  mode : 'local',editable : false,emptyText : '未知',selectOnFocus : true
			              },
			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'婚姻状况',name:'MARRIAGE'
//			           		  ,store:marriageStore,resizable : false,valueField : 'key',displayField : 'value',
//			            	  mode : 'local',editable : false,emptyText : '未知',selectOnFocus : true
			            	  },
			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'出生日期',name:'BIRTHDAY'},
			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'联系电话',name:'POST_PHONE'},
			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'联络频率',name:'CONTACT_FREQ_PREFER'},
			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'单位联系人',name:'CNT_NAME'},
			              {xtype:'displayfield',readOnly:true,anchor : '95%',fieldLabel:'家庭年收入',name:'FAMILY_ANN_INC_SCOPE'
//			            	  ,store:incomeStore,resizable : false,valueField : 'key',displayField : 'value',
//			            	  mode : 'local',editable : false,emptyText : '未知',selectOnFocus : true
			            	  },
			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'个人年收入(万元)',name:'ANNUAL_INCOME'},   
			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'最后更新时间',name:'LAST_UPDATE_TM'}*/
			              ]
			},{
				columnWidth:.33,  
				layout:'form',
				items:[
						{xtype:'displayfield',anchor:'95%',fieldLabel:'开户证件号码',name:'IDENT_NO',readOnly:true},
						{xtype : 'displayfield',name : 'IDENT_EXPIRED_DATE',fieldLabel : '开户证件到期日',format:'Y-m-d',anchor : '95%',readOnly:true},
						{xtype:'displayfield',anchor:'95%',fieldLabel:'风险等级',name:'RISK_LEVEL',readOnly:true},
						{xtype:'displayfield',anchor:'95%',fieldLabel:'风险等级到期日',name:'RISK_VALID_DATE',readOnly:true},
		            	{xtype:'displayfield',anchor:'95%',fieldLabel:'手机号码',name:'MOBILE_PHONE',readOnly:true},
		            	{xtype:'displayfield',anchor:'95%',fieldLabel:'拨薪户',name:'IS_PAYROLL_CUST',readOnly:true}
				]
			},{
				columnWidth:.5,  
				layout:'form',
				items:[{xtype:'displayfield',anchor:'95%',fieldLabel:'住宅地址',name:'HOME_ADDR',readOnly:true},
				       {xtype:'displayfield',anchor:'95%',fieldLabel:'客户号',name:'CUST_ID',readOnly:true,hidden:true}]
			}]
		}]
//		,
//		afterRender:function(){
//			var a = customerBaseInfoPanel.getForm().findField('HOME_ADDR').getValue();
//		}
		}]
	});
	
	Ext.override(Ext.form.DisplayField, {
  getValue: function () {
    return this.value;
  },
  setValue: function (v) {
    this.value = v;
    this.setRawValue(this.formatValue(v));
    return this;
  },
  formatValue: function (v) {
    if (this.dateFormat && Ext.isDate(v)) {
      return v.dateFormat(this.dateFormat);
    }
    if (this.numberFormat && typeof v == 'number') {
      return Ext.util.Format.number(v, this.numberFormat);
    }
    return v;
  }
});
	// 存款总额
	var totalStore = new Ext.data.JsonStore({
		autoLoad		: false,
		restful		    : true,
		successProperty	: 'success',
		messageProperty	: 'message',
		idProperty		: 'id',
		totalProperty	: 'json.count',
		root		    : 'json.data',
		fields		    : [{
			name	: 'accCcy'
		}, {
			name	: 'accAval'
		}]
	});
		
	var totalCm = new Ext.grid.ColumnModel([{
		header		: '币种',
		dataIndex	: 'accCcy',
		width		: 260
	}, {
		header		: '余额',
		dataIndex	: 'accAval',
		width		: 260,
		align		: 'right',
		renderer	: function(value) {
			if(value) {
				return Ext.util.Format.number(1 * value / 100, '0,0.00');
			} else {
				return value;
			}
		}
	}]);	
	/**
	 * 存款总额表格
	 */
	var totalGrid = new Ext.grid.GridPanel({
		itemId		: 'totalGrid',
		frame		: true,
		title		: '存款总额',
		width		: 520,
		hideLabel	: false,
		autoScroll	: true,
		stripeRows	: true,
		region		: 'north',
		store		: totalStore,
		cm		   : totalCm,
		autoHeight	: true,
		viewConfig	: {
			forceFit	: true
		}
	})
	/**
	 * 贷款余额数据集
	 */
	var loanBalanceStore = new Ext.data.JsonStore({
		autoLoad		: false,
		restful		    : true,
		successProperty	: 'success',
		messageProperty	: 'message',
		idProperty		: 'id',
		totalProperty	: 'json.count',
		root		    : 'json.data',
		fields		    : [{
			name	: 'accCcy'
		}, {
			name	: 'accAval'
		}]
	});
	/**
	 * 贷款余额列模式
	 */
	var loanBalanceCm = new Ext.grid.ColumnModel([{
		header		: '币种',
		dataIndex	: 'accCcy',
		width		: 260
	}, {
		header		: '余额',
		dataIndex	: 'accAval',
		width		: 260,
		align		: 'right',
		renderer	: function(value) {
			if(value) {
				return Ext.util.Format.number(1 * value / 100, '0,0.00');
			} else {
				return value;
			}
		}
	}]);	
	/**
	 * 贷款余额表格
	 */
	var loanBalanceGrid = new Ext.grid.GridPanel({
		style       : 'margin-left:1px;',
		itemId		: 'loanBalanceGrid',
		frame		: true,
		title		: '贷款余额',
		width		: 520,
		hideLabel	: false,
		autoScroll	: true,
		stripeRows	: true,
		region		: 'north',
		store		: loanBalanceStore,
		cm		   : loanBalanceCm,
		autoHeight	: true,
		viewConfig	: {
			forceFit	: true
		}
	})
	// 活期存款
	//TODO 目前核心没有给出区分定期还是活期，定期活期混在一起
	var currentStore = new Ext.data.JsonStore({
		autoLoad : true,
		restful:true,
		url:basepath + '/accountQuery!perdeposit.json?custId='+_custId+"&depType=SA",
		successProperty : 'success',
		messageProperty : 'message',
		idProperty : 'id',
		totalProperty : 'json.count',
		root:'json.data',
		fields	: [
					{name: 'accCcy'},
					{name: 'accNo'},
					{name:'cardNo'},
                    {name:'flstscd'},
					{name: 'batp'},
					{name: 'accAval'},
					{name: 'acctype'}
				   ],
		listeners:{
			load:function(_this,records){
				depositTotalAmtReadyCount ++ ;
				/*this.each(function(rec){
                	if(rec.get("acctype")!="SA"){
                		_this.remove(rec);
                	}
                });*/
			},
			loadexception : function(){
				Ext.Msg.alert("系统消息","查询活期存款数据失败");
				depositTotalAmtReadyCount ++ ;
				depositTotalAmtError ++ ;
			}
		}
	});
		
	var currentCm= new Ext.grid.ColumnModel([
		{header:'币种',dataIndex:'accCcy',width:260},
		{header:'账号',dataIndex:'accNo',width:360},
		{header:'卡号',dataIndex:'cardNo',width:260},
        {header:'账户状态',dataIndex:'flstscd',width:260},
		{header:'钞汇标志',dataIndex:'batp',width:260},
		{header:'余额',dataIndex:'accAval',width:260,align:'right',renderer:function(value){
				if(value != null && value != ''){
					if(typeof value == 'object' && value.length>0){
						var tempVal = value[0];
						tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal.substring(0,tempVal.length-1));
						tempVal = tempVal/100;
						return Ext.util.Format.number(tempVal, '0,0.00');
					}else{
						var tempVal = value;
						tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal);
						tempVal = tempVal/100;
						return Ext.util.Format.number(tempVal, '0,0.00');
					}
				}else{
					return value;
				}
			}
		}
	]);	
	
	var currentGrid = new Ext.grid.GridPanel({
		itemId : 'currentGrid',
		frame: true,
		title: '活期存款',
		hideLabel:false,
	    autoScroll: true,
	    stripeRows: true,
	    region : 'north',
	    store: currentStore,
	    cm : currentCm,
	    autoHeight: true,
	    viewConfig:{
	    	forceFit : true
	    }
	})
	//贷款
	var loanStore = new Ext.data.JsonStore({
		autoLoad : true,
		restful:true,
		url:basepath + '/accountQuery!perloan.json?custId='+_custId,
		successProperty : 'success',
		messageProperty : 'message',
		idProperty : 'id',
		totalProperty : 'json.count',
		root:'json.data',
		fields	: [
		         {name:'accName'}
		         ,{name:'accCcy'}
				,{name:'accNo'}
				,{name:'accAval'}
			],
        listeners:{
            load:function(_this,records){
                loanBalanceStore.removeAll();
                var data = {}, keys = [];
                sumByCyc(data, keys, _this, "accCcy", "accAval");
                Ext.each(keys, function(key) {
                    var record = new loanBalanceStore.recordType({
                        accCcy  : key,
                        accAval : 1*data[key]
                    });
                    loanBalanceStore.add(record);
                })
            }
        }
	});
	
	var loanCm= new Ext.grid.ColumnModel([
		{header:'账户名称',dataIndex:'accName',width:260,hidden:true},
		{header:'币种',dataIndex:'accCcy',width:260},
		{header:'账号',dataIndex:'accNo',width:360},
		{header:'余额',dataIndex:'accAval',width:260,align:'right',renderer:function(value){
				if(value != null && value != ''){
					if(typeof value == 'object' && value.length>0){
						var tempVal = value[0];
						tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal.substring(0,tempVal.length-1));
						tempVal = tempVal/100;
						return Ext.util.Format.number(tempVal, '0,0.00');
					}else{
						var tempVal = value;
						tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal);
						tempVal = tempVal/100;
						return Ext.util.Format.number(tempVal, '0,0.00');
					}
				}else{
					return value;
				}
			}
		}
	]);
	
	var loanGrid = new Ext.grid.GridPanel({
		itemId : 'loanGrid',
		frame: true,
		title: '贷款',
		hideLabel:false,
	    autoScroll: true,
	    stripeRows: true,
	    region : 'north',
	    store: loanStore,
	    cm : loanCm,
	    autoHeight: true,
	    viewConfig:{
	    	forceFit : true
	    }
	});
	//定期存款
	//TODO 目前核心没有给出区分定期还是活期，定期活期混在一起
	var termDepositStore = new Ext.data.JsonStore({
		autoLoad : true,
		restful:true,
		url:basepath + '/accountQuery!perdeposit.json?custId='+_custId+"&depType=FD",
		successProperty : 'success',
		messageProperty : 'message',
		idProperty : 'id',
		totalProperty : 'json.count',
		root:'json.data',
		fields	: [
					{name:'accCcy'}
					,{name:'cardNo'}
                    ,{name:'flstscd'}
					,{name:'accNo'}
					,{name:'depositNumber'}
					,{name:'term'}
					,{name:'START_INTER_DATE'}
					,{name:'MATURE_DATE'}
					,{name:'RATE'}
					,{name:'accAval'}
					,{name:'batp'}
					,{name:'acctype'}
				   ],
        listeners:{
            load:function(_this,records){
                depositTotalAmtReadyCount ++ ;
                /*this.each(function(rec){
                	if(rec.get("acctype")!="FD"){
                		_this.remove(rec);
                	}
                });*/
            },
            loadexception : function(){
            	Ext.Msg.alert("系统消息","查询定期存款数据失败");
                depositTotalAmtReadyCount ++ ;
                depositTotalAmtError ++ ;
            }
        }
	});
		
	var termDepositCm= new Ext.grid.ColumnModel([
		{header:'币种',dataIndex:'accCcy',width:260},
		{header:'账号',dataIndex:'accNo',width:360},
		{header:'卡号',dataIndex:'cardNo',width:260},
        {header:'账户状态',dataIndex:'flstscd',width:260},
//		{header:'钞汇标志',dataIndex:'batp',width:260},
		{header:'存单号',dataIndex:'depositNumber',width:260},
		{header:'期限',dataIndex:'term',width:260},
		{header:'起息日',dataIndex:'START_INTER_DATE',width:260},
		{header:'到期日',dataIndex:'MATURE_DATE',width:260},
		{header:'实际利率（%）',dataIndex:'RATE',width:260},
		{header:'余额',dataIndex:'accAval',width:260,align:'right',renderer:function(value){
                if(value){
                    if(Ext.isArray(value)){
                        var tempVal = value[0];
                        tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal.substring(0,tempVal.length-1));
                        tempVal = tempVal/100;
                        return Ext.util.Format.number(tempVal, '0,0.00');
                    }else{
                        var tempVal = value;
                        tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal);
                        tempVal = tempVal/100;
                        return Ext.util.Format.number(tempVal, '0,0.00');
                    }
                }else{
                    return value;
                }
            }
        }
	]);	
	
	var termDepositGrid = new Ext.grid.GridPanel({
		itemId : 'termDepositGrid',
		frame: true,
		title: '定期存款',
		hideLabel:false,
	    autoScroll: true,
	    stripeRows: true,
	  //  region : 'north',
	    store: termDepositStore,
	    cm : termDepositCm,
	    autoHeight: true,
	    viewConfig:{
	    	forceFit : true
	    }
	})
	
	//通知存款
	//TODO 目前核心没有给出区分定期还是活期，定期活期混在一起
	var noticeDepositStore = new Ext.data.JsonStore({
		autoLoad : true,
		restful:true,
		url:basepath + '/accountQuery!perdeposit.json?custId='+_custId+"&depType=FC",
		successProperty : 'success',
		messageProperty : 'message',
		idProperty : 'id',
		totalProperty : 'json.count',
		root:'json.data',
		fields	: [
					{name:'accCcy'}
					,{name:'cardNo'}
                    ,{name:'flstscd'}
					,{name:'accNo'}
					,{name:'depositNumber'}
					,{name:'term'}
					,{name:'START_INTER_DATE'}
					,{name:'MATURE_DATE'}
					,{name:'RATE'}
					,{name:'accAval'}
					,{name:'acctype'}
				   ],
        listeners:{
            load:function(_this,records){
                depositTotalAmtReadyCount ++ ;
                /*this.each(function(rec){
                	if(rec.get("acctype")!="FC"){
                		_this.remove(rec);
                	}
                });*/
            },
            loadexception : function(){
            	Ext.Msg.alert("系统消息","查询通知存款数据失败");
                depositTotalAmtReadyCount ++ ;
                depositTotalAmtError ++ ;
            }
        }
	});
		
	var noticeDepositCm= new Ext.grid.ColumnModel([
		{header:'币种',dataIndex:'accCcy',width:260},
		{header:'账号',dataIndex:'accNo',width:360},
		{header:'卡号',dataIndex:'cardNo',width:260},
        {header:'账户状态',dataIndex:'flstscd',width:260},
//		{header:'钞汇标志',dataIndex:'batp',width:260},
		{header:'存单号',dataIndex:'depositNumber',width:260},
		{header:'期限',dataIndex:'term',width:260},
		{header:'起息日',dataIndex:'START_INTER_DATE',width:260},
		{header:'到期日',dataIndex:'MATURE_DATE',width:260},
		{header:'实际利率（%）',dataIndex:'RATE',width:260},
		{header:'余额',dataIndex:'accAval',width:260,align:'right',renderer:function(value){
                if(value){
                    if(Ext.isArray(value)){
                        var tempVal = value[0];
                        tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal.substring(0,tempVal.length-1));
                        tempVal = tempVal/100;
                        return Ext.util.Format.number(tempVal, '0,0.00');
                    }else{
                        var tempVal = value;
                        tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal);
                        tempVal = tempVal/100;
                        return Ext.util.Format.number(tempVal, '0,0.00');
                    }
                }else{
                    return value;
                }
            }
        }
	]);	
	var noticeDepositGrid = new Ext.grid.GridPanel({
		itemId : 'noticeDepositGrid',
		frame: true,
		title: '通知存款',
		hideLabel:false,
	    autoScroll: true,
	    stripeRows: true,
	    region : 'north',
	    store: noticeDepositStore,
	    cm : noticeDepositCm,
	    autoHeight: true,
	    viewConfig:{
	    	forceFit : true
	    }
	})
	//结构性理财产品
	var productStore = new Ext.data.JsonStore({
		autoLoad : true,
		restful:true,
		url:basepath+'/accountQuery!WMSAccount.json?custId='+_custId,
//	    url:basepath+'/accountQuery!CRMWMSAccount.json?custId='+_custId,
		successProperty : 'success',
		messageProperty : 'message',
		idProperty : 'id',
		totalProperty : 'json.count',
		root:'json.data',
		fields	: [
		         {name:'CCY'}
		         ,{name:'ACCT_NO'}
		         ,{name:'PRO_TENOR'}
		         ,{name:'VALUE_DATE'}
		         ,{name:'END_DATE'}
		         ,{name:'ANTI_RAT'}
		         ,{name:'ORDER_BALANCE'}
			],
        listeners:{
            load:function(_this,records){
                depositTotalAmtReadyCount ++ ;
            },
            loadexception : function(){
            	Ext.Msg.alert("系统消息","查询结构性理财产品数据失败");
                depositTotalAmtReadyCount ++ ;
                depositTotalAmtError ++ ;
            }
        }
	});
		
	var productCm= new Ext.grid.ColumnModel([
		{header:'币种',dataIndex:'CCY',width:260},
		{header:'扣款账号',dataIndex:'ACCT_NO',width:260},
		{header:'期限(天)',dataIndex:'PRO_TENOR',width:260},
		{header:'起息日',dataIndex:'VALUE_DATE',width:260},
		{header:'到期日',dataIndex:'END_DATE',width:260},
		{header:'预计收益率（%）',dataIndex:'ANTI_RAT',width:260,renderer:function(value){
				if(value != null && value != ''){
					if(typeof value == 'object' && value.length>0){
						var tempVal = value[0];
//						tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal.substring(0,tempVal.length-1));
//						tempVal = tempVal/100;
						return Ext.util.Format.number(tempVal, '0,0.00');
					}else{
						var tempVal = value;
//						tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal);
//						tempVal = tempVal/100;
						return Ext.util.Format.number(tempVal, '0,0.00');
					}
				}else{
					return value;
				}
			}
		},
		{header:'余额',dataIndex:'ORDER_BALANCE',width:260,align:'right',renderer:function(value){
				if(value != null && value != ''){
					if(typeof value == 'object' && value.length>0){
						var tempVal = value[0];
//						tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal.substring(0,tempVal.length-1));
//						tempVal = tempVal/100;
						return Ext.util.Format.number(tempVal, '0,0.00');
					}else{
						var tempVal = value;
//						tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal);
//						tempVal = tempVal/100;
						return Ext.util.Format.number(tempVal, '0,0.00');
					}
				}else{
					return value;
				}
			}
		}
	]);	
	
	var productGrid = new Ext.grid.GridPanel({
		itemId : 'productGrid',
		frame: true,
		title: '结构性理财产品',
		hideLabel:false,
	    autoScroll: true,
	    stripeRows: true,
	    region : 'north',
	    store: productStore,
	    cm : productCm,
	    autoHeight: true,
	    viewConfig:{
	    	forceFit : true
	    }
	});
	
	//大额存单
	//TODO 目前还未给出接口,需要等待核心给出接口
	var CDsStore = new Ext.data.JsonStore({
		autoLoad : true,
		restful:true,
		url:basepath+'/accountQuery!cerdeposit.json?custId='+_custId,
//		url:basepath+'#',
		successProperty : 'success',
		messageProperty : 'message',
		idProperty : 'id',
		totalProperty : 'json.count',
		root:'json.data',
		fields	: [
		         {name:'ccycode'}
		         ,{name:'draccno'}
		         ,{name:'tenor'}
		         ,{name:'valdate'}
		         ,{name:'matdate'}
		         ,{name:'intrate'}
		         ,{name:'osamt'}
			],
        listeners:{
            load:function(_this,records){
                depositTotalAmtReadyCount ++ ;
            },
            loadexception : function(){
            	Ext.Msg.alert("系统消息","查询大额存单数据失败");
                depositTotalAmtReadyCount ++ ;
                depositTotalAmtError ++ ;
            }
        }
	});
		/**
		 * 预期收益率:004 000 0000 共10位，7位小数
		 * 余额:000 000 200 000 00+ 共15位，2位小数，末尾带正负号需要提前，然后两位小数
		 * 
		 */
	var CDsCm= new Ext.grid.ColumnModel([
		{header:'币种',dataIndex:'ccycode',width:260},
		{header:'扣款账号',dataIndex:'draccno',width:260},
		{header:'期限',dataIndex:'tenor',width:260,renderer:function(value){
				if(value != null && value != ''){
					if(typeof value == 'object' && value.length>0){
						var tempVal = value[0];
						return Number(tempVal);
					}else{
						var tempVal = value;
						return Number(tempVal);
					}
				}else{
					return value;
				}
			}
		},
		{header:'起息日',dataIndex:'valdate',width:260},
		{header:'到期日',dataIndex:'matdate',width:260},
		{header:'预计收益率（%）',dataIndex:'intrate',width:260,renderer:function(value){
				if(value != null && value != ''){
					if(typeof value == 'object' && value.length>0){
						var tempVal = value[0];
						tempVal = Number(tempVal)/10000000;  //转位Number类型，并去除前面的零，7位小数
//						tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal.substring(0,tempVal.length-1));
//						tempVal = tempVal/100;
						return Ext.util.Format.number(tempVal, '0,0.00');  //保留两位小数
					}else{
						var tempVal = value;
//						tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal);
//						tempVal = tempVal/100;
						return Ext.util.Format.number(tempVal, '0,0.00');
					}
				}else{
					return value;
				}
			}
		},
		{header:'余额',dataIndex:'osamt',width:260,align:'right',renderer:function(value){
				if(value != null && value != ''){
					if(typeof value == 'object' && value.length>0){
						var tempVal = value[0];
						tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal.substring(0,tempVal.length-1));
						tempVal = tempVal/100;
						return Ext.util.Format.number(tempVal, '0,0.00');//保留两位小数
					}else{
						var tempVal = value;
						tempVal = Number(tempVal.substring(tempVal.length-1)+tempVal);
						tempVal = tempVal/100;
						return Ext.util.Format.number(tempVal, '0,0.00'); //保留两位小数
					}
				}else{
					return value;
				}
			}
		}
	]);	
	
	var CDsGrid = new Ext.grid.GridPanel({
		itemId : 'CDsGrid',
		frame: true,
		title: '大额存单',
		hideLabel:false,
	    autoScroll: true,
	    stripeRows: true,
	    region : 'north',
	    store: CDsStore,
	    cm : CDsCm,
	    autoHeight: true,
	    viewConfig:{
	    	forceFit : true
	    }
	});

	var customerContactInfoPanel = new Ext.FormPanel({
		autoHeight	: true,
		labelWidth	: 220,// label的宽度
		labelAlign	: 'right',
		frame		: false,
		autoScroll	: true,
		buttonAlign	: 'center',
		items		: [{
			xtype			: 'fieldset',
			title			: '往来信息',
			titleCollapse	: true,
			collapsible		: true,
			autoHeight		: true,
			items			: [{
			 layout  : 'column',
			 autoHeight  : true,
			 items   : [totalGrid, loanBalanceGrid]
			}, currentGrid, termDepositGrid, noticeDepositGrid,loanGrid, productGrid, CDsGrid
			/*{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
						   {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'存款总额（人民币）',name:'AUM_BALANCE'},
						   {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'存款总额（美元）',name:''}
//						 {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'客户经理',name:'MGR_NAME'},
//			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'AUM(人民币)',name:'AUM_BALANCE'},
//			              {xtype:'displayfield',readOnly:true,anchor : '95%',fieldLabel:'风险评级',name:'RISK_LEVEL'
////			              ,store:riskStore,resizable : false,valueField : 'key',displayField : 'value',
////				            	  mode : 'local',editable : false,emptyText : '未知',selectOnFocus : true
//				            	  },
//			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'风险评级有效期',name:'RISK_VALID_DATE'},
//			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'产品持有数',name:'HOLDINGS'},
//			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'往来存款类/资管计划总计',name:'DEPOSIT_BALANCE'},
//			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'按揭贷款',name:'MORT_LOAN'},
//			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'抵押性贷款',name:'COLL_LOAN'},
//			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'往来贷款产品总计',name:'LOAN_BALANCE'},
//			              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'内保外贷（现金质押）',name:'BW_LOANS'}
			              ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
							{xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'活期存款余额',name:'CURRENT_BALANCE'},
				            {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'定期存款余额',name:'FIX_PERIOD_BALANCE'},
				            {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'理财存款余额',name:'FINAN_DEPOSIT_BALANCE'}
//					       {xtype:'displayfield',readOnly:true,anchor : '95%',fieldLabel:'是否持有借记卡',name:'IS_CARD'
//					       		,store:marriageStore,resizable : false,valueField : 'key',displayField : 'value',
//				            	mode : 'local',editable : false,emptyText : '未知',selectOnFocus : true
//				            	},
//				            	{xtype:'displayfield',readOnly:true,anchor : '95%',fieldLabel:'是否开通网银',name:'IS_NETBANK'},
//				              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'活期存款余额',name:'CURRENT_BALANCE'},
//				              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'定期存款余额',name:'FIX_PERIOD_BALANCE'},
//				              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'理财存款余额',name:'FINAN_DEPOSIT_BALANCE'},
//				              {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'一般贷款',name:'LOAN_AMOUNT'},
//				              {xtype:'displayfield',readOnly:true,anchor : '95%',fieldLabel:'客户来源渠道',name:'SOURCE_CHANNEL'
////				            	  ,store:custSourceStore,resizable : false,valueField : 'key',displayField : 'value',
////				            		mode : 'local',editable : false,emptyText : '未知',selectOnFocus : true
//				            		},
//				              {xtype : 'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'客户满意度',name:'SATISFY_TYPE_ORA',mode : 'local',store:satisfyStore,triggerAction : 'all',displayField:'value',valueField:'key',border:false}//用cust_id连接表OCRM_F_SE_CUST_SATISFY_LIST				              
				              ]
				}]
			}*/]
		}]
	});
	
	/*var customerStrategyPanel= new Ext.FormPanel({
		autoHeight:true,
		labelWidth:220,//label的宽度
		labelAlign:'right',
		frame:false,
		autoScroll : true,
		buttonAlign:'center',
		items : [ {
			xtype : 'fieldset',
			title : '策略',
			titleCollapse : true,
			collapsible : true,
			autoHeight:true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'账户经营策略',name:'ACC_RUN_STRATEGY'},
					       {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'建议投资产品及营销策略',name:'PROD_ADVICE'}
			              ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
//					       {xtype:'label', id : 'mylabel1', html : '<a href="http://www.baidu.com/">资产配置建议书</a> '}
							{xtype:'label',id : 'mylabel1',html:'资产配置建议书',listeners : {
         							 'click' : {
											fn : function(field) {
												parent.parent._APP.openWindow({
													name : '顾问式理财服务',
													action : basepath
															+ '/crmweb/contents/pages/wlj/wealthManager/consultantFinancialNEW.js?condis='
															+ _custId,
													resId : 111194,
													id : 'task_111194',
													serviceObject : false
												});
											},
											scope : this
										}
								}
							}
						]
				}]
			}]
		}]
	});*/
	/*var customerFinancialInfoPanel= new Ext.FormPanel({
		autoHeight:true,
		labelWidth:220,//label的宽度
		labelAlign:'right',
		frame:false,
		autoScroll : true,
		buttonAlign:'center',
		items : [ {
			xtype : 'fieldset',
			title : '客户财务目标与财务信息',
			titleCollapse : true,
			collapsible : true,
			autoHeight:true,
			items:[{
				layout:'column',
				items:[{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'退休计划',name:'IS_RETIRE_PLAN'},
					       {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'购房计划',name:'IS_HOUSE_PLAN'},
					       {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'子女教育计划',name:'IS_EDU_PLAN'},
					       {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'资产总值（不含房产）',name:'ASSET_AMT'},
					       {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'房产信息',name:'HOUSE_INFO'}
			              ]
					},{
					columnWidth:.5,  
					layout:'form',
					items:[
					       {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'预计退休时间',name:'IS_RETIRE_PLAN'},
					       {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'预计购房时间',name:'IS_HOUSE_PLAN'},
					       {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'预计子女出国时间',name:'IS_EDU_PLAN'},
					       {xtype:'displayfield',width:250,readOnly:true,anchor : '95%',fieldLabel:'是否按揭',name:'IS_MORT'}
				          ]
				}]
			}]
		}]
	});*/
	
//	客户AUM滚动月日均值趋势图（连续30天）
	/*Ext.Ajax.request({
		url : basepath + '/privateCustChart!searchMonthAum.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = "";
			xml +=  "<chart  palette='2' showValues = '0' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13'> ";
			xml +=	"<categories> ";
			for(var i=0;i<data.length;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(5,2)+data[i].ETL_DATE.substr(8,2)+ "'/>   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='AUM滚动日均值' dragBorderColor='0099FF'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].INDEX7+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", "custMonthAumChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custMonthAumChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});*/
	//客户AUM滚动月日均值趋势图（连续12个月月末）
	/*Ext.Ajax.request({
		url : basepath + '/privateCustChart!searchYearAum.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = "";
			xml +=  "<chart  palette='2' showValues = '0' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13'> ";
			xml +=	"<categories> ";
			for(var i=0;i<data.length;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(0,4)+"-"+data[i].ETL_DATE.substr(5,2)+"'/>   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='AUM滚动月均值' dragBorderColor='0099FF'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].INDEX7+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", "custYearAumChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custYearAumChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});*/
	//客户贷款余额时点值趋势图（连续30天）
	/*Ext.Ajax.request({
		url : basepath + '/privateCustChart!searchMonthLoan.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = "";
			xml +=  "<chart  palette='2' showValues = '0' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13'> ";
			xml +=	"<categories> ";
			for(var i=0;i<data.length;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(5,2)+data[i].ETL_DATE.substr(8,2)+ "'/>   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='贷款余额时点' dragBorderColor='0099FF'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].INDEX4+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", "custMonthLoanChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custMonthLoanChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});*/

	//客户贷款余额时点值趋势图（连续12个月月末）
		/*Ext.Ajax.request({
		url : basepath + '/privateCustChart!searchYearLoan.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = "";
			xml +=  "<chart  palette='2' showValues = '0' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13'> ";
			xml +=	"<categories> ";
			for(var i=0;i<data.length;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(0,4)+"-"+data[i].ETL_DATE.substr(5,2)+"'/>   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='贷款余额时点值' dragBorderColor='0099FF'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].INDEX4+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", "custYearLoanChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custYearLoanChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});*/
	//客户贡献度时点数趋势图（连续30天）
		/*Ext.Ajax.request({
		url : basepath + '/privateCustChart!searchMonthContr.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = "";
			xml +=  "<chart  palette='2' showValues = '0' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13'> ";
			xml +=	"<categories> ";
			for(var i=0;i<data.length;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(5,2)+data[i].ETL_DATE.substr(8,2)+ "'/>   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='贡献度时点数' dragBorderColor='0099FF'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].INDEX8+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", "custMonthContrChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custMonthContrChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});*/
	//客户贡献度时点数趋势图（连续12个月月末）
		/*Ext.Ajax.request({
		url : basepath + '/privateCustChart!searchYearContr.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = "";
			xml +=  "<chart  palette='2' showValues = '0' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13'> ";
			xml +=	"<categories> ";
			for(var i=0;i<data.length;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(0,4)+"-"+data[i].ETL_DATE.substr(5,2)+"'/>   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='贡献度时点数' dragBorderColor='0099FF'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].INDEX8+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", "custYearContrChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custYearContrChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});*/
	//客户理财产品总和时点趋势图（连续30天）
	/*Ext.Ajax.request({
		url : basepath + '/privateCustChart!searchMonthFina.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = "";
			xml +=  "<chart  palette='2' showValues = '0' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13'> ";
			xml +=	"<categories> ";
			for(var i=0;i<data.length;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(5,2)+data[i].ETL_DATE.substr(8,2)+ "'/>   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='理财产品总和时点' dragBorderColor='0099FF'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].INDEX9+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", "custMonthFinaChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custMonthFinaChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});*/
	//客户理财产品总和时点趋势图（连续12个月月末）
		/*Ext.Ajax.request({
		url : basepath + '/privateCustChart!searchYearFina.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = "";
			xml +=  "<chart  palette='2' showValues = '0' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13'> ";
			xml +=	"<categories> ";
			for(var i=0;i<data.length;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(0,4)+"-"+data[i].ETL_DATE.substr(5,2)+"'/>   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='理财产品总和时点' dragBorderColor='0099FF'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].INDEX9+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", "custYearFinaChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custYearFinaChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});*/
	//客户积分时点数趋势图（连续30天）
		/*Ext.Ajax.request({
		url : basepath + '/privateCustChart!searchMonthPoints.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = "";
			xml +=  "<chart  palette='2' showValues = '0' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13'> ";
			xml +=	"<categories> ";
			for(var i=0;i<data.length;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(5,2)+data[i].ETL_DATE.substr(8,2)+ "'/>   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='客户积分时点' dragBorderColor='0099FF'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].DAYPOINTS+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", "custMonthPointsChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custMonthPointsChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});*/
	//客户积分时点数趋势图（连续12个月月末）
	/*Ext.Ajax.request({
		url : basepath + '/privateCustChart!searchYearPoints.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = "";
			xml +=  "<chart  palette='2' showValues = '0' xAxisName='' yAxisName='单位（元）' formatNumberScale='0' baseFontSize='13'> ";
			xml +=	"<categories> ";
			for(var i=0;i<data.length;i++){
				xml+="  <category label='"+data[i].ETL_DATE.substr(0,4)+"-"+data[i].ETL_DATE.substr(5,2)+"'/>   ";
			}
			xml+=" </categories>";
			xml+="<dataset id='last' seriesName='客户积分时点' dragBorderColor='0099FF'>";
			for(var i=0;i<data.length;i++){
				xml+="  <set value='"+data[i].MONTHPOINTS+"' allowDrag='0'/>  ";
			}
			xml+="</dataset>";
			xml+="</chart>";
			var myChart = new FusionCharts(basepath+"/FusionCharts/MSLine.swf", "custYearPointsChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custYearPointsChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});*/
	//	个人管理总资产时点值
	/*Ext.Ajax.request({
		url : basepath + '/privateCustChart!searchAssetsInfo.json?custId='+_custId,
		method : 'GET',
		success : function(response) {
			var data = Ext.util.JSON.decode(response.responseText).data;
			var xml = '';
			if(data.length >0){
				data = data[0];
		        xml +=  '<chart bgColor="#E8E8D0" caption="个人管理总资产时点值(单位:元)" subcaption="'+ (data.ETL_DATE).substr(0,4) + '年'+ (data.ETL_DATE).substr(5,2) + '月'+ (data.ETL_DATE).substr(8,2)+ '日'+'" formatNumberScale="0" baseFontSize="13">';
		        xml += '<set label="存款余额" value="'+ data.DEPOSIT_BALANCE +'" />';
		        xml += '<set label="理财产品余额" value="'+ data.FINANPRD_BALANCE +'" />';
		    	//xml += '<set label="基金产品余额" value="'+ data.FUNDPRD_BALANCE +'" />';
		     	xml += '<set label="保险余额" value="'+ data.ASSURANCE_BALANCE +'" />';
		     	//xml += '<set label="国债余额" value="'+ data.BOND_BALANCE +'" />';
		    	//xml += '<set label="三方存管余额" value="'+ data.THIRD_BALANCE +'" />';
		    	//xml += '<set label="贵金属余额" value="'+ data.NOBLEMETAL_BALANCE +'" />';
		    	//xml += '<set label="管理总资产AUM" value="'+ data.AUM_BALANCE +'" />';
				xml += '</chart>';
			}
			var myChart = new FusionCharts(basepath+"/FusionCharts/Pie3D.swf", "custAssetsChart_01", "100%", "100%", "0", "0");
			myChart.setDataXML(xml);
			myChart.render("custAssetsChart");
		},
		failure : function(response) {
			alert("读取数据失败");
		}
	});*/
/**
 * 与其他银行关系
 */
/*var lvrownum = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 35
});
var lvcm = new Ext.grid.ColumnModel( [
    lvrownum,
    {header:'拜访方式', dataIndex:'VISIT_TYPE', sortable:true, align:'left', width : 120},
    {header:'联系电话', dataIndex:'PHONE', sortable:true, align:'left', width : 120},
    {header:'拜访人', dataIndex:'VISITOR2', sortable:true, align:'left', width : 120},
    {header:'拜访状态', dataIndex:'VISIT_STAT', sortable:true, align:'left', width : 120},
    {header:'客户联系和拜访情况', dataIndex:'VISIT_NOTE', sortable:true, align:'left', width : 120},
    {header:'任务安排人', dataIndex:'ARANGE_ID2', sortable:true, align:'left', width : 120},
    {header:'客户经理编号', dataIndex:'USER_ID', sortable:true, align:'left', width : 120},
    {header:'客户经理姓名', dataIndex:'USER_NAME1', sortable:true, align:'left', width : 120},
    {header:'开始时间', dataIndex:'SCH_START_TIME', sortable:true, align:'left', width : 120},
    {header:'完成时间', dataIndex:'SCH_EDN_TIME', sortable:true, align:'left', width : 120}
]);
var lvstore = new Ext.data.Store({
	restful : true,
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/privateCustInfo!searchVisit.json?custId='+_custId,
		method: 'POST',
		failure : function(response) {//状态码解码.
			var resultArray = Ext.util.JSON
					.decode(response.status);
			if (resultArray == 403) {
				Ext.Msg.alert('提示', response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader( {
		successProperty : 'success',
		totalProperty : 'count',
		root : 'data'
	}, [ 
	    {name : 'VISIT_TYPE',mapping:'VISIT_TYPE_ORA'},
		{name : 'PHONE'},
		{name : 'VISITOR2'},
		{name : 'VISIT_STAT',mapping:'VISIT_STAT_ORA'},
		{name : 'VISIT_NOTE'},
		{name : 'ARANGE_ID2'},
		{name : 'USER_ID'},
		{name : 'USER_NAME1'},
		{name : 'SCH_START_TIME'},
		{name : 'SCH_EDN_TIME'}
	])
});
lvstore.load();
var lvGrid = new Ext.grid.GridPanel( {
	title : '拜访记录（最近3次）',
	height : 180,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	collapsible : true,
	store : lvstore, // 数据存储
	stripeRows : true, // 斑马线
	cm : lvcm, // 列模型
	//bbar : bbar,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});	
	
*//**
 * 与其他银行关系
 *//*
var obrownum = new Ext.grid.RowNumberer( {
	header : 'No.',
	width : 35
});
var obcm = new Ext.grid.ColumnModel( [
    obrownum,
    {header:'金融机构名称', dataIndex:'INSTN_NAME', sortable:true, align:'left', width : 120},
    {header:'贷款时点余额', dataIndex:'LON_VAL', sortable:true, align:'left', viewFn: money('0,000.00'),width : 120},
    {header:'授信贷款时点余额', dataIndex:'CRED_AMT', sortable:true, align:'left', viewFn: money('0,000.00'),width : 120},
    {header:'定期存款时点余额', dataIndex:'PERIODCIAL_VAL', sortable:true, align:'left', viewFn: money('0,000.00'),width : 120},
    {header:'产品使用情况', dataIndex:'PRD_USE', sortable:true, align:'left', width : 120},
    {header:'活期存款时点余额', dataIndex:'CURRENT_VAL', sortable:true, align:'left', viewFn: money('0,000.00'),width : 120},
    {header:'是否基本户开户行', dataIndex:'IS_BASIC_BANK', sortable:true, align:'left', width : 120},
    {header:'与银行关系', dataIndex:'BANK_REL', sortable:true, align:'left', width : 120},
    {header:'授信期限', dataIndex:'CRED_LIMIT', sortable:true, align:'left', width : 120},
    {header:'备注', dataIndex:'REMARK', sortable:true, align:'left', width : 120}
]);
var obstore = new Ext.data.Store( {
	restful : true,
	proxy : new Ext.data.HttpProxy( {
		url : basepath + '/privateCustInfo!searchBank.json?custId='+_custId,
		method: 'POST',
		failure : function(response) {//状态码解码.
			var resultArray = Ext.util.JSON
					.decode(response.status);
			if (resultArray == 403) {
				Ext.Msg.alert('提示', response.responseText);
			}
		}
	}),
	reader : new Ext.data.JsonReader( {
		successProperty : 'success',
		totalProperty : 'count',
		root : 'data'
	}, [ 
	    {name : 'INSTN_NAME'},
		{name : 'LON_VAL'},
		{name : 'CRED_AMT'},
		{name : 'PERIODCIAL_VAL'},
		{name : 'PRD_USE'},
		{name : 'CURRENT_VAL'},
		{name : 'IS_BASIC_BANK'},
		{name : 'CRED_LIMIT'},
		{name : 'BANK_REL'},
		{name : 'REMARK'}
	])
});
obstore.load();
var obGrid = new Ext.grid.GridPanel( {
	title : '与其他银行关系',
	height : 180,
	frame : true,
	overflow : 'auto',
	autoScroll : true,
	collapsible : true,
	store : obstore, // 数据存储
	stripeRows : true, // 斑马线
	cm : obcm, // 列模型
	//bbar : bbar,
	loadMask : {
		msg : '正在加载表格数据,请稍等...'
	}
});*/
	
	
	
	
   var _height= 225;	    
	//页面视图根面板
   var viewport = new Ext.Viewport({
	    layout:'border',
	   // border:false,
	    items:[{
	        xtype:'portal',
	        id:'center',
	        region:'center',
	        //margins:'5 5 5 5',
	        title:'客户信息首页',
	        items:[{
	     	 	xtype : 'fieldset',
		        columnWidth: 1,
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
	        	items:[	
	        		 {
	              		columnWidth: 1,
	              		autoHeight:true,
	              		anchor:'98%',
	             		items:[customerBaseInfoPanel]
	          	 	 },{
     					columnWidth: 1,
	              	 	autoHeight:true,
	              	 	anchor:'98%',
	               		items:[customerContactInfoPanel]
	           		 }/*,{
	           		 	columnWidth: 1,
	              	 	autoHeight:true,
	              	 	anchor:'98%',
						items:[customerFinancialInfoPanel]
	           		 },{
	           		 	columnWidth: 1,
	              	 	autoHeight:true,
	              	 	anchor:'98%',
	           		 	items:[customerStrategyPanel]
	           		 },{
		            	autoHeight:true,
	              	 	anchor:'98%',
	           		 	items:[lvGrid]
		            },{
		            	autoHeight:true,
	              	 	anchor:'98%',
	           		 	items:[obGrid]
		            }*/]
	        }/*,{
	            columnWidth:.5,
	            border:false,
	            autoHeight:true,
	            id:'cus',
	            items:[{
	            	layout:'fit',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	                title: '客户贷款余额时点值趋势图（连续12个月月末）',
	                height:_height,
	                draggable:false,
	                html :'<div id="custYearLoanChart"></div>'
	            },{
	            	layout:'fit',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	                title: '客户AUM滚动月日均值趋势图（连续12个月月末）',
	                height:_height,
	                draggable:false,
	                html :'<div id="custYearAumChart"></div>'
	            },{
	            	layout:'fit',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	                title: '客户积分时点数趋势图（连续12个月月末）',
	                height:_height,
	                draggable:false,
	                html :'<div id="custYearPointsChart"></div>'
	            }]
	        },{
	            columnWidth:.5,
	            autoHeight:true,
	            id:'cus2',
	            //layout:'fit',
	            border:false,
	            items:[{
	            	layout:'fit',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	                title: '客户贡献度时点数趋势图（连续12个月月末）',
	                height:_height,
	                draggable:false,
	                html :'<div id="custYearContrChart"></div>'
	            },{
	            	layout:'fit',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	                title: '客户理财产品总和时点趋势图（连续12个月月末）',
	                height:_height,
	                draggable:false,
	                html :'<div id="custYearFinaChart"></div>'
	            },{
	               	layout:'fit',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	            	height:_height,
	            	draggable:false,
	            	title: '个人管理总资产时点值',
					html :'<div id="custAssetsChart"></div>'
	            }]	                
	        },{
	            columnWidth:1,
	            border:false,
	            autoHeight:true,
	            id:'cus3',
	            items:[{
     				layout:'fit',
	                title: '客户AUM滚动月日均值趋势图（连续30天）',
	                collapsible:true,
	                style:'padding:0px 0px 0px 0px',
	                height:_height,
	                draggable:false,
	                html :'<div id="custMonthAumChart"></div>'
	            } ] 
	        },{
	            columnWidth:1,
	            border:false,
	            autoHeight:true,
	            id:'cus4',
	            items:[{
	            	layout:'fit',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	                title: '客户贷款余额时点值趋势图（连续30天）',
	                height:_height,
	                draggable:false,
	                html :'<div id="custMonthLoanChart"></div>'
	            } ]  
	        },{
	            columnWidth:1,
	            border:false,
	            autoHeight:true,
	            id:'cus5',
	            items:[{
	            	layout:'fit',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	                title: '客户贡献度时点数趋势图（连续30天）',
	                height:_height,
	                draggable:false,
	                html :'<div id="custMonthContrChart"></div>'
	            } ]  
	        },{
	            columnWidth:1,
	            border:false,
	            autoHeight:true,
	            items:[{
	            	layout:'fit',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	                title: '客户理财产品总和时点趋势图（连续30天）',
	                height:_height,
	                draggable:false,
	                html :'<div id="custMonthFinaChart"></div>'
	            } ]  
	         },{
	            columnWidth:1,
	            border:false,
	            autoHeight:true,
	            items:[{
	            	layout:'fit',
	            	style:'padding:0px 0px 0px 0px',
	            	collapsible:true,
	                title: '客户积分时点数趋势图（连续30天）',
	                height:_height,
	                draggable:false,
	                html :'<div id="custMonthPointsChart"></div>'

	            } ]  
	         }*/]
	    }]
   });
   //record
  var record=Ext.data.Record.create(
		  [//个人客户信息表字段 ACRM_F_CI_PERSON 
		   {name: 'CUST_ID'}
		  ,{name: 'SUR_NAME'}
		  ,{name: 'PERSONAL_NAME'}
		  ,{name: 'HOLD_ACCT_ORA',mapping:'HOLD_ACCT_ORA'} 	
		  ,{name: 'HOLD_CARD_ORA',mapping:'HOLD_CARD_ORA'}
		  ,{name: 'PERSON_TITLE'}
		  ,{name: 'BIRTHDAY'}
		  ,{name: 'CITIZENSHIP',mapping:'CITIZENSHIP_ORA'}
		  ,{name: 'MARRIAGE',mapping:'MARRIAGE_ORA'}
		  ,{name: 'LAST_UPDATE_TM',mapping:'LUTIME'}
		  ,{name: 'MOBILE_PHONE'}
		  ,{name: 'EMAIL'}
		  ,{name: 'HOME_ADDR'}
		  ,{name: 'CAREER_TYPE',mapping:'CAREER_TYPE_ORA'}
		  ,{name: 'UNIT_FEX'}
		  ,{name: 'POST_PHONE'}
		  ,{name: 'CNT_NAME'}
		  ,{name: 'FAMILY_ANN_INC_SCOPE',maping:'FAMILY_ANN_INC_SCOPE_ORA'}
		  ,{name: 'ANNUAL_INCOME',mapping:'PER_ANNUAL_INCOME'}
		  ,{name: 'UNIT_NAME'}
		  ,{name: 'CONTACT_FREQ_PREFER'}
  		  //客户信息表字段ACRM_F_CI_CUSTOMER
		  //		  risk_level 风险等级
		  //		  RISK_DATE 风险等级有效期

//		  ,{name: 'RISK_LEVEL',mapping:'RISK_LEVEL_ORA'}
		  ,{name: 'RISK_VALID_DATE'}
		  ,{name: 'SOURCE_CHANNEL',mapping:'SOURCE_CHANNEL_ORA'}
		  //归属客户经理信息表OCRM_F_CI_BELONG_CUSTMGR字段    客户经理
		  
		  ,{name: 'MGR_NAME'}
		  //管理总资产（AUM）汇总表 ACRM_A_CI_ASSET_AUM字段  AUM
		  ,{name: 'AUM_BALANCE'}
		  
		  //ACRM_F_AG_AGREEMENT count(cust_id)对应 产品持有数  HOLDINGS
		  ,{name: 'HOLDINGS'}
		  //ACRM_A_CI_GATH_BUSINESS 客户业务汇总表
			//		  是否开通网银
			//		  是否持有借记卡
			//		  活期存款余额
			//		  定期存款余额
			//		  理财存款余额
		  	//		  一般贷款
		  ,{name: 'IS_NETBANK',mapping:'IS_NETBANK_ORA'}
		  ,{name: 'IS_CARD',mapping:'IS_CARD_ORA'}
		  ,{name: 'CURRENT_BALANCE'}
		  ,{name: 'FIX_PERIOD_BALANCE'}
		  ,{name: 'FINAN_DEPOSIT_BALANCE'}
		  ,{name: 'LOAN_AMOUNT'}       
		  ,{name: 'SATISFY_TYPE_ORA'}   //  用cust_id连接表OCRM_F_SE_CUST_SATISFY_LIST
		  //ACRM_A_CI_GATH_BUSINESS
			//按揭贷款
			//往来存款类/资管计划总计
			//往来贷款产品总计
			//内保外贷款（现金质押）
//		  ,{name: 'Q'}  
		  //OCRM_F_CI_OTHER_BANK客户他行信息
//		   ,{name: 'BANK_REL'}
     	,{name: 'DEPOSIT_BALANCE'}//往来存款类
     	,{name: 'MORT_LOAN'}//按揭贷款
     	,{name: 'COLL_LOAN'}//抵押性贷款
     	,{name: 'LOAN_BALANCE'}//往来贷款总计
     	,{name: 'BW_LOANS'}//内保外贷
     	,{name: 'IS_RETIRE_PLAN'}//退休计划
     	,{name: 'IS_HOUSE_PLAN'}//购房计划
     	,{name: 'IS_EDU_PLAN'}//子女教育
     	,{name: 'ASSET_AMT'}//资产总值
     	,{name: 'HOUSE_INFO'}//房产信息
     	,{name: 'RETIRE_PLAN'}//预计退休时间
     	,{name: 'HOUSE_PLAN'}//预计购房时间
     	,{name: 'ABROAD_PLAN'}//预计出国时间
     	,{name: 'IS_MORT'}//是否按揭
     	,{name: 'ACC_RUN_STRATEGY'}//账户经营策略
     	,{name: 'PROD_ADVICE'}//建议投资产品
     	,{name: 'IDENT_EXPIRED_DATE'}//证件到期日
     	,{name: 'IDENT_NO'}//开户证件号
     	,{name: 'CORE_NO'}//核心客户号
     	,{name: 'RISK_LEVEL'}//风险等级
     	,{name: 'UNIT_NAME'}//单位地址
     		//是否是拨薪户
		,{name: 'IS_PAYROLL_CUST'}//是否是拨薪户
		,{name: 'CREATE_DATE'}//开户日期
		,{name: 'RISK_VALID_DATE'}//风险等级到期日
		,{name: 'IDENT_NO2'}//港澳身份证/台胞证号
		,{name: 'IDENT_EXPIRED_DATE2'}//港澳身份证/台胞证到期日
		   ]);
		   
  //基本信息reader
   customerBaseInfoPanel.getForm().reader=new Ext.data.JsonReader({
        root:'data'
    },record);
   //customerBaseInfoPanel加载数据
   customerBaseInfoPanel.getForm().load({
   	
   	url: basepath + '/privateCustInfo!searchBasicInfo.json',
   	method:'get',
   	params : {
   		'custId':_custId
    },
	success : function(form,action){
		//以下手机修改
		var motoNum = form.findField('MOBILE_PHONE').getValue();
		if(motoNum.indexOf("/",0)>1){
			var TelM = motoNum.split("/");
			var ak = TelM[0]+"-"+TelM[1];
			form.findField('MOBILE_PHONE').setValue(ak);
		}
		
		//以下地址修改：
		var motoAdd = form.findField('HOME_ADDR').getValue();
		if(motoNum.indexOf("/",0)>0){
			var AddM = motoAdd.split("/");
			var bk = AddM[0]+"，"+AddM[1];
			form.findField('HOME_ADDR').setValue(bk);
		}
		
		//以下联系电话修改
//		var motoTel = form.findField('POST_PHONE').getValue();
//		if(motoNum.indexOf("/",0)>1){
//			var TelX = motoTel.split("/");
//			var ck = TelX[0]+"-"+TelX[1];
//			form.findField('POST_PHONE').setValue(ck);
//		}
//		
	}
});

   //往来信息reader
   customerContactInfoPanel.getForm().reader=new Ext.data.JsonReader({
       root:'data'
   },record);
  //customerContactInfoPanel加载数据
  customerContactInfoPanel.getForm().load({
  	url: basepath + '/privateCustInfo!searchContactInfo.json',
  	method:'get',
  	params : {
  		'custId':_custId
      }
  });
  
	/*//策略信息reader
	customerStrategyPanel.getForm().reader=new Ext.data.JsonReader({
	       root:'data'
	   },record);
	//加载数据
	customerStrategyPanel.getForm().load({
	  	url: basepath + '/privateCustInfo!searchStrategyInfo.json',
	  	method:'get',
	  	params : {
	  		'custId':_custId
	    }
	});
	
	//客户财务信息reader
	customerFinancialInfoPanel.getForm().reader=new Ext.data.JsonReader({
	       root:'data'
	   },record);
	//加载数据
	customerFinancialInfoPanel.getForm().load({
	  	url: basepath + '/privateCustInfo!searchFinInfo.json',
	  	method:'get',
	  	params : {
	  		'custId':_custId
	    }
	});*/
});
