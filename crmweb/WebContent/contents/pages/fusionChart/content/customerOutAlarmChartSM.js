Ext.onReady(function() {
    /*得到昨天的日期*/
    var today =  new Date();
    var year1 = today.getYear();
    var mon1 = today.getMonth()+1;
    var date1 = today.getDate(); 
    
    var thisDate = new Date(year1,mon1,date1-1);
    
    var year = thisDate.getYear();
    var month = thisDate.getMonth();
    var day = thisDate.getDate();if(day/10<1){day = "0"+day;}if(month/10<1){month = "0"+month;}
    var thisDay = year+"-"+month+"-"+day;
    
    var thisYear = year+"-01-01";
	
			var searchPanel = new Ext.form.FormPanel({
								labelWidth : 105,
								labelAlign : 'right',
								height : 100,
								frame : true,
								region : 'north',
								title : "查询",
								autoScroll : true,
								items : [
									    	{
											    layout : 'column',
											    items : [
		    	    	{
				   		    columnWidth : .5,
							layout : 'column',
							xtype : 'panel',
							items : [
										{
											columnWidth : .5,
											layout : 'form',
											labelWidth : 60,
											items : 
													{
														fieldLabel : '统计日期',
														xtype : 'datefield',
														id : 'crmDtStart',
														name : 'crmDtStart',
														format : 'Y-m',
														value : thisYear,
														editable : false,
														anchor : '100%'
													}
										},
										{
											columnWidth : .5,
											layout : 'form',
											labelStyle : 'text-align:center',
											labelAlign : 'right',
											labelSeparator : '',
											labelWidth : 30,
											items : {
												xtype : 'datefield',
												fieldLabel : '至',
												id : 'crmDtEnd',
												name : 'crmDtEnd',
												value:thisDay,
												format : 'Y-m',
												editable : false,
												anchor : '100%'
											}
										}
							]
					    }
														]
											}
											
										],
								buttonAlign : 'center',
								buttons : [ 
										    {
												text : '查询',
												handler : function() {
													if(searchPanel.getForm().findField("crmDtStart").value != undefined) {
														document.getElementById("crmDtStart").value = searchPanel.getForm().findField("crmDtStart").value;
													}
													if(searchPanel.getForm().findField("crmDtEnd").value != undefined) {
														document.getElementById("crmDtEnd").value = searchPanel.getForm().findField("crmDtEnd").value;
													}
													load();
												}
											 },
											 {
												text : '重置',
												handler : function() {
								                	searchPanel.getForm().reset();
												}
											 }
											 ,
											 {
												text : '返回',
												handler : function() {
													window.location.href="../../smallEnterprise/SMELoseWarning.jsp";
												}
											 }
								 		  ]
							});
	
			var view = new Ext.Viewport({
				layout : 'border',
				items : [ 
						  {
							region : 'north',
							id : 'north-panel',
							height : 105,
							layout : 'fit',
							items : [ searchPanel ]
					      },
					      {
							region : 'center',
							id : 'center-panel',
							layout : 'fit',
							html:'<div id="chartdiv"></div>' 
						  }
						]
			});

		})