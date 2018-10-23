Ext.onReady(function(){
	 var simple = new Ext.FormPanel({
	        //labelWidth: 75, 
	        frame:true,
	        bodyStyle:'padding:5px 5px 0',
	        width: '100%',
	       

	        items: [{
	        	//columnWidth:.5,
	            	xtype:'fieldset',
	            //checkboxToggle:true,
	            title: '序号条件',
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
	                             fieldLabel: '条件1',
	                             name: 'first',
	                             anchor:'70%'
	                         }, {
	                             xtype:'textfield',
	                             fieldLabel: '条件4',
	                             name: 'company',
	                             anchor:'70%'
	                         }]
	                     },{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '条件2',
	                             name: 'last',
	                             anchor:'70%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '条件5',
	                             name: 'email',
	                             anchor:'70%'
	                         }]
	                     },{
	                         columnWidth:.34,
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '条件3',
	                             name: 'last1',
	                             anchor:'70%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '条件6',
	                             name: 'email1',
	                             anchor:'70%'
	                         }]
	                     }
	            ]}
	            ]},{
	            xtype:'fieldset',
	            title: '账户名称',
	            collapsible: true,
	            autoHeight:true,
	            width: '100%',
	            	//	defaultType: 'textfield',
	            items :[{ layout:'column',
                    items:[{
                        columnWidth:.33,
                        layout: 'form',
                        items: [{
                            xtype:'textfield',
                            fieldLabel: '条件1',
                            name: 'first',
                            anchor:'70%'
                        }, {
                            xtype:'textfield',
                            fieldLabel: '条件4',
                            name: 'company',
                            anchor:'70%'
                        }]
                    },{
                        columnWidth:.33,
                        layout: 'form',
                        items: [{
                            xtype:'textfield',
                            fieldLabel: '条件2',
                            name: 'last',
                            anchor:'70%'
                        },{
                            xtype:'textfield',
                            fieldLabel: '条件5',
                            name: 'email',
                            anchor:'70%'
                        }]
                    },{
                        columnWidth:.34,
                        layout: 'form',
                        items: [{
                            xtype:'textfield',
                            fieldLabel: '条件3',
                            name: 'last1',
                            anchor:'70%'
                        },{
                            xtype:'textfield',
                            fieldLabel: '条件6',
                            name: 'email1',
                            anchor:'70%'
                        }]
                    }
           ]}
           ]},{
	            xtype:'fieldset',
	            title: '贷款信息',
	            checkboxToggle:true,
	            //collapsible: true,
	            autoHeight:true,
	            width: '100%',
	            items :[{ layout:'column',
                    items:[{
                        columnWidth:.33,
                        layout: 'form',
                        items: [{
                            xtype:'textfield',
                            fieldLabel: '条件1',
                            name: 'first',
                            anchor:'70%'
                        }, {
                            xtype:'textfield',
                            fieldLabel: '条件4',
                            name: 'company',
                            anchor:'70%'
                        }]
                    },{
                        columnWidth:.33,
                        layout: 'form',
                        items: [{
                            xtype:'textfield',
                            fieldLabel: '条件2',
                            name: 'last',
                            anchor:'70%'
                        },{
                            xtype:'textfield',
                            fieldLabel: '条件5',
                            name: 'email',
                            anchor:'70%'
                        }]
                    },{
                        columnWidth:.34,
                        layout: 'form',
                        items: [{
                            xtype:'textfield',
                            fieldLabel: '条件3',
                            name: 'last1',
                            anchor:'70%'
                        },{
                            xtype:'textfield',
                            fieldLabel: '条件6',
                            name: 'email1',
                            anchor:'70%'
                        }]
                    }
           ]}
           ]}],

	        buttons: [{
	            text: '查询'
	        },{
	            text: '重置'
	        }]
	    });
	 	var p = new Ext.Panel({
	        title: '客户群高级查询',
	        collapsible:true,
	        renderTo: 'panel',
	        width:'100%',
	       // simple:simple
	        items:[simple] //管理
	    });

	   // simple.render(document.body);
});