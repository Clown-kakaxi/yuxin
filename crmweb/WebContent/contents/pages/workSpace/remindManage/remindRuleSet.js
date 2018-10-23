Ext.onReady(function() {
	
    Ext.QuickTips.init();   
    
    //五个表单存储状态，-1表示数据库中无对应项
    var index1=-1;
    
    var index2=-1;
    
    var index3=-1;
    
    var index4=-1;
    
    var index5=-1;
    
    
    var proxy = new Ext.data.HttpProxy({
        url : basepath+'/queryremindrule.json',
        failure : function(response) {
            var resultArray = Ext.util.JSON.decode(response.status);
            if(resultArray == 403) {
                Ext.Msg.alert('提示', response.responseText);
            }
        }/*,
        success : function(response) {
            var resultArray = Ext.util.JSON.decode(response.responseText);
            Ext.Msg.alert('提示', response.responseText);
        }*/
    });

    var reader = new Ext.data.JsonReader(
            {
                successProperty: 'success',
                root:'json.data',
                totalProperty: 'json.count'
            },[
               {name:'CREATOR'},
               {name:'SECTION_TYPE'},
               {name:'RULE_NAME'},
               {name:'BEFOREHEAD_DAY'},
               {name:'REMIND_MODE'},
               {name:'RULE_ID'},
               {name:'CHANGE_AMOUNT'}
               ]
        );
    
    var store=new Ext.data.Store({
        restful : true,
        proxy : proxy,
        reader : reader
    });
    
    //CREATOR,接受session传来的userId
//    var CREATOR='10091';
    
    
	//账户到期类提醒规则设置

    var accountExpireForm = new Ext.form.FormPanel({
        frame : true, //是否渲染表单面板背景色
        labelWidth:100,
        title:'账户到期类提醒规则设置',
        labelAlign:'right',
        height:100,
        labelAlign:'right',
        buttons:[{
            text:'保存'   ,
            handler:function()
            {
                if(!accountExpireForm.getForm().isValid())
                { 
                    alert('请填写正确信息');
                    return false;
                }
                Ext.Ajax.request({
                    url: basepath+'/workplatremindrule.json',
                    method: 'POST',
//                    form:'panel2',
                    params:accountExpireForm.getForm().getFieldValues(),
                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                    success : checkResult,
                    failure : checkResult
                });
            }
        },
        {
            text:'重置'   ,
            handler:function()
            {
                if(index1!=-1){
                    var id= store.getAt(index1).get('RULE_ID');
                    if(confirm("确定删除吗?")){
                        Ext.Ajax.request({
                            url : basepath+'/workplatremindrule/'
                                    +id+'.json',
                            method : 'DELETE',        
                            waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                            success : checkResult,
                            failure : checkResult
                        });
                    }
            }
        }}],
        buttonAlign:'center',       
        items : [{
            items:[{
                items:[
                       {
                           xtype:'textfield',
                           name:'RULE_ID',
                           hidden:true,
                           anchor:'90%'
                       }
                       ]
            },
                   {
                       xtype:'textfield',
                       name:'CREATOR',
                       //value:CREATOR,
                       hidden:true,
                       anchor:'90%'
                   }
                   ]
        },{
            items:[
                   {
                       xtype:'textfield',
                       name:'SECTION_TYPE',
                       value:'1000000001',
                       hidden:true,
                       id:'SECTION_TYPE1',
                       anchor:'90%'
                   }
                   ]
       },{
                    layout : 'column',
                    items : [
                    {
                        columnWidth:.25,
                        layout:'form',
                        items:[
                        {
                            xtype:'textfield',
                            name:'RULE_NAME',
                            blankText:'请输入100字符内的规则名称',
                            maxLength:100,
                            maxLengthText:'规则名称长度应小于100',
                            allowBlank:false,
                            fieldLabel:'名称',
                            anchor:'90%'
                        }
                        ]
                    },                  
                    {
                        columnWidth:.25,
                        layout:'form',
                        items:[
                           {
                               xtype:'numberfield',
                               minValue:0,
                               maxValue:90,
                               minText:'最小天数为0',
                               decimalPrecision:0,
                               name:'BEFOREHEAD_DAY',
                               allowBlank:false,
                               fieldLabel:'提醒提前天数',
                               blankText:'请输入提醒提前天数',
                               anchor:'90%'
                           }]
                        
                    },
                    
                    {
                                columnWidth : .25,
                                layout : 'form',
                                defaultType : 'textfield',
                                items : [
                                    {
                                            fieldLabel : '提醒方式', // 标签
                                            allowBlank : true,
                                            forceSelection : true,
                                            xtype:'combo',
                                            name:'REMIND_MODE',
                                            triggerAction:'all',
                                            mode:'local',
                                            store:new Ext.data.ArrayStore({
                                                id:0,
                                                fields:['myId','displayText'],
                                                data:[[0,'站内'],[1,'短信'],[2,'邮件']]
                                            }),
                                            valueField:'myId',
                                            displayField:'displayText',
                                            value:0,
                                            anchor : '90%'// 宽度百分比
                                    }
                                
                                ]
                            }

                            ]
                }]
    }); 
	
//账户结息提醒规则设置
	var accountExpireInterestForm = new Ext.form.FormPanel({
		frame : true, //是否渲染表单面板背景色
		labelWidth:100,
		title:'账户结息提醒规则设置',
		labelAlign:'right',
		height:100,
		labelAlign:'right',
		buttons:[{
			text:'保存'	,
			handler:function()
            {
			    if(!accountExpireInterestForm.getForm().isValid())
                { 
                    alert('请填写正确信息');
                    return false;
                }
                Ext.Ajax.request({
                    url: basepath+'/workplatremindrule.json',
                    method: 'POST',
//                    form:'panel2',
                    params:accountExpireInterestForm.getForm().getFieldValues(),
                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                    success : checkResult,
                    failure : checkResult
                });
                    
            }
		},
        {
            text:'重置'   ,
            handler:function()
            {
                if(index2!=-1){
                    var id= store.getAt(index2).get('RULE_ID');
                    if(confirm("确定删除吗?")){
                        Ext.Ajax.request({
                            url : basepath+'/workplatremindrule/'
                                    +id+'.json',
                            method : 'DELETE',        
                            waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                            success : checkResult,
                            failure : checkResult
                        });
                    }
                }
            }
        }],
		buttonAlign:'center',		
		items : [{
            items:[{
                items:[
                       {
                           xtype:'textfield',
                           name:'RULE_ID',
                           hidden:true,
                           anchor:'90%'
                       }
                       ]
            },
                   {
                       xtype:'textfield',
                       name:'CREATOR',
                       //value:CREATOR,
                       hidden:true,
                       anchor:'90%'
                   }
                   ]
        },{
            items:[
                   {
                       xtype:'textfield',
                       name:'SECTION_TYPE',
                       value:'1000000002',
                       hidden:true,
                       id:'SECTION_TYPE2',
                       anchor:'90%'
                   }
                   ]
       },{
					layout : 'column',
					items : [
					{
						columnWidth:.25,
						layout:'form',
						items:[
						{
							xtype:'textfield',
							name:'RULE_NAME',
                            blankText:'请输入100字符内的规则名称',
                            maxLength:100,
                            maxLengthText:'规则名称长度应小于100',
                            allowBlank:false,
							fieldLabel:'名称',
							anchor:'90%'
						}
						]
					},					
					{
						columnWidth:.25,
						layout:'form',
						items:[
                               {
                                   xtype:'numberfield',
                                   minValue:0,
                                   maxValue:90,
                                   minText:'最小天数为0',
                                   decimalPrecision:0,
                                   name:'BEFOREHEAD_DAY',
                                   allowBlank:false,
                                   fieldLabel:'提醒提前天数',
                                   blankText:'请输入提醒提前天数',
                                   anchor:'90%'
                               }]
					},
					{
								columnWidth : .25,
								layout : 'form',
								defaultType : 'textfield',
								items : [
									{
											fieldLabel : '提醒方式', // 标签
											allowBlank : true,
                                            forceSelection : true,
											xtype:'combo',
											name:'REMIND_MODE',
											triggerAction:'all',
											mode:'local',
											store:new Ext.data.ArrayStore({
												id:0,
												fields:['myId','displayText'],
												data:[[0,'站内'],[1,'短信'],[2,'邮件']]
											}),
											valueField:'myId',
											displayField:'displayText',
											value:0,
											anchor : '90%'// 宽度百分比
									}
								
								]
							}

							]
				}]
	});	
	//账户余额变动提醒规则设置
	var accountChangeForm = new Ext.form.FormPanel({
		frame : true, //是否渲染表单面板背景色
		labelWidth:100,
		title:'账户余额变动提醒规则设置',
		labelAlign:'right',
		height:100,
		labelAlign:'right',
		buttons:[{
			text:'保存'	,
			handler:function()
            {
			    if(!accountChangeForm.getForm().isValid())
                { 
                    alert('请填写正确信息');
                    return false;
                }
                Ext.Ajax.request({
                    url: basepath+'/workplatremindrule.json',
                    method: 'POST',
//                    form:'panel2',
                    params:accountChangeForm.getForm().getFieldValues(),
                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                    success : checkResult,
                    failure : checkResult
                });
            }
		},
        {
            text:'重置'   ,
            handler:function()
            {
                if(index3!=-1){
                    var id= store.getAt(index3).get('RULE_ID');
                    if(confirm("确定删除吗?")){
                        Ext.Ajax.request({
                            url : basepath+'/workplatremindrule/'
                                    +id+'.json',
                            method : 'DELETE',        
                            waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                            success : checkResult,
                            failure : checkResult
                        });
                    }
                }
            }
        }],
		buttonAlign:'center',		
		items : [{
            items:[
                   {
                       xtype:'textfield',
                       name:'RULE_ID',
                       hidden:true,
                       anchor:'90%'
                   }
                   ]
        },{
            items:[
                   {
                       xtype:'textfield',
                       name:'CREATOR',
                       //value:CREATOR,
                       hidden:true,
                       anchor:'90%'
                   }
                   ]
        },{
            items:[
                   {
                       xtype:'textfield',
                       name:'SECTION_TYPE',
                       value:'1000000003',
                       hidden:true,
                       id:'SECTION_TYPE3',
                       anchor:'90%'
                   }
                   ]
       },{
					layout : 'column',
					items : [
					{
						columnWidth:.25,
						layout:'form',
						items:[
						{
							xtype:'textfield',
							name:'RULE_NAME',
                            blankText:'请输入100字符内的规则名称',
                            maxLength:100,
                            maxLengthText:'规则名称长度应小于100',
                            allowBlank:false,
							fieldLabel:'名称',
							anchor:'90%'
						}
						]
					},
					{
						columnWidth:.25,
						layout:'form',
						items:[
	                        {
	                            fieldLabel:'变动金额(万元)',
	                            xtype:'numberfield',
	                            minValue:0,
	                            minText:'最小金额为0',
	                            blankText:'请输入金额',
	                            decimalPrecision:2,
	                            name:'CHANGE_AMOUNT',
	                            format:Ext.util.Format.usMoney,
	                            allowBlank:false,
	                            maxLength:24,
	                            anchor:'90%'
	                        }]
						
					},					
					{
						columnWidth:.25,
						layout:'form',
						items:[
                           {
                               xtype:'numberfield',
                               minValue:0,
                               maxValue:90,
                               minText:'最小天数为0',
                               decimalPrecision:0,
                               name:'BEFOREHEAD_DAY',
                               allowBlank:false,
                               fieldLabel:'提醒提前天数',
                               blankText:'请输入提醒提前天数',
                               anchor:'90%'
                           }]
					},
					{
								columnWidth : .25,
								layout : 'form',
								defaultType : 'textfield',
								items : [
									{
											fieldLabel : '提醒方式', // 标签
											allowBlank : true,
                                            forceSelection : true,
											xtype:'combo',
											name:'REMIND_MODE',
											triggerAction:'all',
											mode:'local',
											store:new Ext.data.ArrayStore({
												id:0,
												fields:['myId','displayText'],
												data:[[0,'站内'],[1,'短信'],[2,'邮件']]
											}),
											valueField:'myId',
											displayField:'displayText',
                                            value:0,
											
											anchor : '90%'// 宽度百分比
									}
								
								]
							}

							]
				}]
	});	
	
//高管生日提醒规则设置
	var highProfilePersonForm = new Ext.form.FormPanel({
		frame : true, //是否渲染表单面板背景色
		labelWidth:100,
		height:100,
		labelAlign:'right',
		buttons:[{
			text:'保存'	,
			handler:function()
            {
			    if(!highProfilePersonForm.getForm().isValid())
                { 
                    alert('请填写正确信息');
                    return false;
                }
                Ext.Ajax.request({
                    url: basepath+'/workplatremindrule.json',
                    method: 'POST',
//                    form:'panel2',
                    params:highProfilePersonForm.getForm().getFieldValues(),
                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                    success : checkResult,
                    failure : checkResult
                });
            }
		},
        {
            text:'重置'   ,
            handler:function()
            {
                if(index4!=-1){
                    var id= store.getAt(index4).get('RULE_ID');
                    if(confirm("确定删除吗?")){
                        Ext.Ajax.request({
                            url : basepath+'/workplatremindrule/'
                                    +id+'.json',
                            method : 'DELETE',        
                            waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                            success : checkResult,
                            failure : checkResult
                        });
                    }
                }
            }
        }],
		buttonAlign:'center',		
		title:'高管生日提醒规则设置',
		labelAlign:'right',
		items : [{
            items:[
                   {
                       xtype:'textfield',
                       name:'RULE_ID',
                       hidden:true,
                       anchor:'90%'
                   }
                   ]
        },{
            items:[
                   {
                       xtype:'textfield',
                       name:'CREATOR',
                       //value:CREATOR,
                       hidden:true,
                       anchor:'90%'
                   }
                   ]
        },{
            items:[
                   {
                       xtype:'textfield',
                       name:'SECTION_TYPE',
                       value:'1000000004',
                       hidden:true,
                       id:'SECTION_TYPE4',
                       anchor:'90%'
                   }
                   ]
       },{
					layout : 'column',
					items : [
					{
						columnWidth:.25,
						layout:'form',
						items:[
						{
							xtype:'textfield',
							name:'RULE_NAME',
                            blankText:'请输入100字符内的规则名称',
                            maxLength:100,
                            maxLengthText:'规则名称长度应小于100',
                            allowBlank:false,
							fieldLabel:'名称',
							anchor:'90%'
						}
						]
					},					
					{
						columnWidth:.25,
						layout:'form',
						items:[
                           {
                               xtype:'numberfield',
                               minValue:0,
                               maxValue:90,
                               minText:'最小天数为0',
                               decimalPrecision:0,
                               name:'BEFOREHEAD_DAY',
                               allowBlank:false,
                               fieldLabel:'提醒提前天数',
                               blankText:'请输入提醒提前天数',
                               anchor:'90%'
                           }]
					},
					{
								columnWidth : .25,
								layout : 'form',
								defaultType : 'textfield',
								items : [
									{
											fieldLabel : '提醒方式', // 标签
											allowBlank : true,
                                            forceSelection : true,
											xtype:'combo',
											name:'REMIND_MODE',
											triggerAction:'all',
											mode:'local',
											store:new Ext.data.ArrayStore({
												id:0,
												fields:['myId','displayText'],
												data:[[0,'站内'],[1,'短信'],[2,'邮件']]
											}),
											valueField:'myId',
											displayField:'displayText',
                                            value:0,
											
											anchor : '90%'// 宽度百分比
									}
								
								]
							}

							]
				}]
	});		
////客户事件提醒规则设置
	var custEventForm = new Ext.form.FormPanel({
		frame : true, //是否渲染表单面板背景色
		labelWidth:100,
		height:100,
		labelAlign:'right',
		buttons:[{
			text:'保存',
			handler:function()
                    {
                if(!custEventForm.getForm().isValid())
                { 
                    alert('请填写正确信息');
                    return false;
                }
                Ext.Ajax.request({
                    url: basepath+'/workplatremindrule.json',
                    method: 'POST',
//                    form:'panel2',
                    params:custEventForm.getForm().getFieldValues(),
                    waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                    success : checkResult,
                    failure : checkResult
                });
            }
		},
        {
            text:'重置'   ,
            handler:function()
            {
                if(index5!=-1){
                    var id= store.getAt(index5).get('RULE_ID');
                    if(confirm("确定删除吗?")){
                        Ext.Ajax.request({
                            url : basepath+'/workplatremindrule/'
                                    +id+'.json',
                            method : 'DELETE',        
                            waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
                            success : checkResult,
                            failure : checkResult
                        });
                    }
                }
            }
        }],
		buttonAlign:'center',		
		title:'客户事件提醒规则设置',
		labelAlign:'right',
		items : [{
            items:[
                   {
                       xtype:'textfield',
                       name:'RULE_ID',
                       hidden:true,
                       anchor:'90%'
                   }
                   ]
        },{
            items:[
                   {
                       xtype:'textfield',
                       name:'CREATOR',
                       //value:CREATOR,
                       hidden:true,
                       anchor:'90%'
                   }
                   ]
        },{
            items:[
                   {
                       xtype:'textfield',
                       name:'SECTION_TYPE',
                       value:'1000000005',
                       hidden:true,
                       id:'SECTION_TYPE5',
                       anchor:'90%'
                   }
                   ]
       },{
					layout : 'column',
					items : [
					{
						columnWidth:.25,
						layout:'form',
						items:[
						{
							xtype:'textfield',
							name:'RULE_NAME',
                            blankText:'请输入100字符内的规则名称',
                            maxLength:100,
                            maxLengthText:'规则名称长度应小于100',
                            allowBlank:false,
							fieldLabel:'名称',
							anchor:'90%'
						}
						]
					},					
					{
						columnWidth:.25,
						layout:'form',
						items:[
                           {
                               xtype:'numberfield',
                               minValue:0,
                               maxValue:90,
                               minText:'最小天数为0',
                               decimalPrecision:0,
                               name:'BEFOREHEAD_DAY',
                               allowBlank:false,
                               fieldLabel:'提醒提前天数',
                               blankText:'请输入提醒提前天数',
                               anchor:'90%'
                           }]
					},
					{
								columnWidth : .25,
								layout : 'form',
								defaultType : 'textfield',
								items : [
									{
											fieldLabel : '提醒方式', // 标签
											allowBlank : true,
                                            forceSelection : true,
											xtype:'combo',
											name:'REMIND_MODE',
											triggerAction:'all',
											mode:'local',
											store:new Ext.data.ArrayStore({
												id:0,
												fields:['myId','displayText'],
												data:[[0,'站内'],[1,'短信'],[2,'邮件']]
											}),
											valueField:'myId',
											displayField:'displayText',
                                            value:0,
											
											anchor : '90%'// 宽度百分比
									}
								
								]
							}

							]
				}]
	});		
	
	
	
	
	//在表单中填入查询到的数据
	function findRecord(){
	    store.load({callback : function(){
	        //查询已存在的规则
	          index1 = store.findBy(function(record,id){
	              return record.get('SECTION_TYPE')=='1000000001';
	          } );
	          
	          index2 = store.findBy(function(record,id){
	              return record.get('SECTION_TYPE')=='1000000002';
	          } );
	          
	          index3 = store.findBy(function(record,id){
	              return record.get('SECTION_TYPE')=='1000000003';
	          } );
	          
	          index4 = store.findBy(function(record,id){
	              return record.get('SECTION_TYPE')=='1000000004';
	          } );
	          
	          index5 = store.findBy(function(record,id){
	              return record.get('SECTION_TYPE')=='1000000005';
	          } );
	          

	          if(index1!==-1){
	              accountExpireForm.getForm().loadRecord(store.getAt(index1));
	          }else{
	              accountExpireForm.getForm().reset();
	          }
	          
	          if(index2!==-1){
	              accountExpireInterestForm.getForm().loadRecord(store.getAt(index2));
	          }else{
	              accountExpireInterestForm.getForm().reset();
              }
	          
	          if(index3!==-1){
	              accountChangeForm.getForm().loadRecord(store.getAt(index3));
              }else{
                  accountChangeForm.getForm().reset();
              }
	          
	          if(index4!==-1){
	              highProfilePersonForm.getForm().loadRecord(store.getAt(index4));
              }else{
                  highProfilePersonForm.getForm().reset();
              }
	          
	          if(index5!==-1){
	              custEventForm.getForm().loadRecord(store.getAt(index5));
              }else{
                  custEventForm.getForm().reset();
              }
	          
	         }

	          });
	}
	
	findRecord();
	
	var rulesSetView = new Ext.Viewport({
		items:[
			accountExpireForm,
			accountExpireInterestForm,
			accountChangeForm,
			highProfilePersonForm,
			custEventForm
		]	
		
	});	
	
	function checkResult(response) {
        var resultArray = Ext.util.JSON.decode(response.status);
        var resultError = response.responseText;
        
        if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
            Ext.Msg.alert('提示', '操作成功');
            findRecord();
        } else {
            if(resultArray == 403) {
                window.location = basepath + '/403.jsp';
            }else{
            Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
            findRecord();
            }
        }
    }
	
});