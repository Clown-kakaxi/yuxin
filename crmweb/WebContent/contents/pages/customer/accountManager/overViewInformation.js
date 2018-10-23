
/**
 * 客户经理业务全景视图--概览信息
 * @author songxs
 * @since 2012-12-7
 */
	debugger;
	var mrgid = viewWindow.mrgIds;
	var custManagerRecord = new Ext.data.Record.create([
	                                                    {name:'custNum',mapping:'CUST_NUM'},
	                                                    {name:'mainCustNum',mapping:'MAIN_CUST_NUM'},
	                                                    {name:'omainCustNum',mapping:'OMAIN_CUST_NUM'},
	                                                    {name:'saveYearAvg',mapping:'SAVE_YEAR_AVG'},
	                                                    {name:'saveBl',mapping:'SAVE_BL'},
	                                                    {name:'loanYearAvg',mapping:'LOAN_YEAR_AVG'},
	                                                    {name:'loanBl',mapping:'LOAN_BL'},
	                                                    {name:'newCreateCust',mapping:'NEW_CREATE_CUST'},
	                                                    {name:'custAum',mapping:'CUST_AUM'}
	                                                    ]);
	
	var custManagerReader = new Ext.data.JsonReader({//读取json数据的panel
 		idProperties:'custId',
 		root : 'json.data'
 	},custManagerRecord);
	
/*	function RMBMoney(v){   
	    v = (Math.round((v-0)*100))/100;   
	    v = (v == Math.floor(v)) ? v + ".00" : ((v*10 == Math.floor(v*10)) ? v + "0" : v);   
	    v = String(v);   
	    var ps = v.split('.');   
	    var whole = ps[0];   
	    var sub = ps[1] ? '.'+ ps[1] : '.00';   
	    var r = /(\d+)(\d{3})/;   
	    while (r.test(whole)) {   
	        whole = whole.replace(r, '$1' + ',' + '$2');   
	    }   
	    v = whole + sub;   
	    if(v.charAt(0) == '-'){   
	        return '-￥' + v.substr(1);   
	    }   
	    return  v;   
	};*/
	var custManagerPanel = new Ext.FormPanel({
		title : '客户经理概览信息',
		reader  : custManagerReader,
		frame : true,
		 height : 200,
		autoScroll : true,
		region:'center',
		width:'100%',
		items:[{
			layout:'column',
			items:[{
				layout : 'form',
				columnWidth : .45,
				labelWidth : 120,
				items:[{
					xtype:'displayfield',
					name:'custNum',
					fieldLabel:'管辖客户数',
					labelStyle:'text-align:right;',
					readOnly:true,
					format:money,
					anchor:'90%'
				},{
					xtype:'displayfield',
					name:'omainCustNum',
					fieldLabel:'协办客户数',
					labelStyle:'text-align:right',
					readOnly:'true',
					anchor:'90%'
				},{
					xtype:'displayfield',
					name:'saveYearAvg',
					fieldLabel:'存款日均',
					labelStyle:'text-align:right;',
					readOnly:true,
					anchor:'90%'
				},{
					xtype:'displayfield',
					name:'loanYearAvg',
					fieldLabel:'贷款日均',
					labelStyle:'text-align:right;',
					readOnly:true,
					anchor:'90%'
				},{
					xtype:'displayfield',
					name:'custAum',
					fieldLabel:'客户AUM',
					labelStyle:'text-align:right;',
					readOnly:true,
					anchor:'90%'
				},{
					xtype:'displayfield',
					id:'interBus',
					fieldLabel:'本年中间业务',
					labelStyle:'text-align:right;',
					value:'120833333',
					readOnly:true,
					anchor:'90%'
				}]
			},{
				layout : 'form',
				columnWidth : .45,
				labelWidth : 120,
				items:[{
					xtype:'displayfield',
					name:'mainCustNum',
					fieldLabel:'主办客户数',
					labelStyle:'text-align:right;',
					readOnly:true,
					anchor:'90%'
				},{	
					xtype:'displayfield',
					name:'newCreateCust',
					fieldLabel:'本年新增开户',
					labelStyle:'text-align:right',
					readOnly:'true',
					anchor:'90%'
				},{
					xtype:'displayfield',
					name:'saveBl',
					fieldLabel:'存款时点',
					labelStyle:'text-align:right',
					readOnly:'true',
					anchor:'90%'
				},{
					xtype:'displayfield',
					name:'loanBl',
					fieldLabel:'贷款时点',
					labelStyle:'text-align:right',
					readOnly:'true',
					anchor:'90%'
				},{
					xtype:'displayfield',
					id:'financialProd',
					fieldLabel:'理财产品',
					labelStyle:'text-align:right;',
					value:'322232000',
					anchor:'90%'
				},{
					xtype:'displayfield',
					fieldLabel:'数据日期',
					labelStyle:'text-align:right;',
					value:'2012-12-10',
					anchor:'90%'
				
				}]
			}]
		}]
	});
    // 修改窗口展示的from
    var custManagerPanel1 = new Ext.Panel( {
        labelWidth : 140,
        height : 200,
//        layout : 'fit',
        autoScroll : true,
        buttonAlign : "center",
        items : [ custManagerPanel ]
    });
	
	var FormatCnMoney = function(v) {
		return Ext.util.Format.number(v, '0,000.00');
	};
	custManagerPanel.getForm().load({//comCustomerInfo FormPanel加载数据
		restful : true,
		url : basepath + '/custViewQuery-Action.json',
		method : 'GET',
		params : {
			'custManagerId' : mrgid
		},success:function(response){
			custManagerPanel.getForm().findField('loanYearAvg').setValue(FormatCnMoney('2900000'));
			custManagerPanel.getForm().findField('saveYearAvg').setValue(FormatCnMoney('38000000'));
			custManagerPanel.getForm().findField('custAum').setValue(FormatCnMoney(custManagerPanel.getForm().findField('custAum').getValue()));
			custManagerPanel.getForm().findField('saveBl').setValue(FormatCnMoney('3999999999'));
			custManagerPanel.getForm().findField('loanBl').setValue(FormatCnMoney('400000000000'));

			Ext.getCmp('interBus').setValue(FormatCnMoney(Ext.getCmp('interBus').getValue()));
			Ext.getCmp('financialProd').setValue(FormatCnMoney(Ext.getCmp('financialProd').getValue()));

			custManagerPanel.getForm().findField('custNum').setValue('<span style="color:red;"><u>'+custManagerPanel.getForm().findField('custNum').getValue()+ '</u></span>');
			custManagerPanel.getForm().findField('omainCustNum').setValue('<span style="color:red;">'+custManagerPanel.getForm().findField('omainCustNum').getValue()+'</span>');
			custManagerPanel.getForm().findField('mainCustNum').setValue('<span style="color:red;">'+custManagerPanel.getForm().findField('mainCustNum').getValue()+'</span>');
			custManagerPanel.getForm().findField('custNum').el.on('click',function(){
				debugger;
				treeOfCustManager.clickFn(treeOfCustManager.root.childNodes[1]);
			});
		}
	});
	var date= document.documentElement.clientHeight/1.8;
	
	var viewport_center = new Ext.Panel({
		renderTo:'viewport_centers',
		height:document.body.scrollHeight-30,
		width : document.body.clientWidth-220,
//		layout:'fit',
		autoScroll:true,
		items:[{
            xtype:'portal',
            id:'center',
            region:'center',
		items: [{
              columnWidth:1,
              collapsible:true,
              items:[custManagerPanel1]
              },
              {
              columnWidth:.5,
              collapsible:true,
              items:[{
                    title: '贷款业务（贷款余额）变化趋势（以月为单位）',
                    collapsible:true,
//                    layout:'fit',
                    style:'padding:0px 0px 0px 0px',
                    height:date,
                    width : (document.body.clientWidth-180)/2.1,
                    html:'<iframe id="contentFrame1" name="content" height="220" frameborder="no" width="100%" src=\"../customerManager/customerBaseInformation/fusionchartsDemo/lookgrade/b_dk.html\" "/> scrolling="no"> </iframe>'
                }]
              },{
                  columnWidth:.5,
                  collapsible:true,
                  items:[{
                        title: '存款业务（存款余额、存款日均）变化趋势（以月为单位）',
                        collapsible:true,
//                        layout:'fit',
                        style:'padding:0px 0px 0px 0px',
                        height:date,
                        width : (document.body.clientWidth-180)/2.1,
                        html:'<iframe id="contentFrame2" name="content" height="220" frameborder="no" width="100%" src=\"../customerManager/customerBaseInformation/fusionchartsDemo/lookgrade/b_ck.html\" "/> scrolling="no"> </iframe>'
                    }]
              },{
                  columnWidth:.5,
                  collapsible:true,
                  items:[{
                        title: '客户等级分布饼图',
                        collapsible:true,
//                        layout:'fit',
                        style:'padding:0px 0px 0px 0px',
                        height:date,
                        width : (document.body.clientWidth-180)/2.1,
                        html:'<iframe id="contentFrame3" name="content5" height="220" frameborder="no" width="100%" src=\"../customerManager/customerBaseInformation/fusionchartsDemo/contribute/khfb.html\" "/> scrolling="no"> </iframe>'
                    }]
               },{
                   columnWidth:.5,
                   collapsible:true,
                   items:[{
                         title: '客户数变化趋势',
                         collapsible:true,
//                         layout:'fit',
                         style:'padding:0px 0px 0px 0px',
                         height:date,
                         width : (document.body.clientWidth-180)/2.1,
                         html:'<iframe id="contentFrame4" name="content" height="220" frameborder="no" width="100%" src=\"../customerManager/customerBaseInformation/fusionchartsDemo/lookgrade/khs.html\" "/> scrolling="no"> </iframe>'
                     }]
                }] 
		}]
	});
	
