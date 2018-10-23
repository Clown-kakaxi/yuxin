Ext.onReady(function() {
	//时间类型
    var typeStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','年-月-日'],['2','年']]
	}); 
  //年类型
    var yearStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','2008'],['2','2009'],['3','2010'],['4','2011'],['5','2012']]
	});

/**********************************************************/

		var qForm = new Ext.form.FormPanel({
			id : "qfrom",
			labelWidth : 100, // 标签宽度
//			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height:240,
			//width:600,
//			height: document.body.clientHeight-40,
			items :[{
				layout : 'column',
				border : false,
	           items :[  
	                     
	                     {
						layout : 'form',
						columnWidth : 1,
						border : false,
							items : [
							         {
											xtype : 'combo',
											store : typeStore,
											fieldLabel : '时间类型',
											id:'selectType',
											hiddenName : 'deposit_average',
											valueField : 'key',
											displayField : 'value',
											triggerAction : 'all',
											mode:'local',
											allowBlank : false,
											value :'',
											editable : false,
											emptyText : '请选择',
											labelStyle : 'text-align:right;',
											anchor : '90%',
											listeners :{'select' : function(){
							        	 if(Ext.getCmp('selectType').getValue()==1){
							        		 Ext.getCmp('StartDate').show();
							        		 Ext.getCmp('yearType').hide();
							        	 }
							        	 else if(Ext.getCmp('selectType').getValue()==2){
							        		 Ext.getCmp('yearType').show();
							        		 Ext.getCmp('StartDate').hide();
							        	 }
							         }}
										       },{
										    	   xtype : 'datefield',
				 									fieldLabel : '数据日期',
				 									id:'StartDate',
				 									hidden : true,
				 									name:'StartDate',
				 									format:"Y-m-d", 
				 									labelStyle : 'text-align:right;',
				 									anchor : '90%' 
										       },{
										    	   xtype : 'combo',
													store : yearStore,
													fieldLabel : '年',
													id:'yearType',
													hidden : true,
													hiddenName : 'deposit_average',
													valueField : 'key',
													displayField : 'value',
													triggerAction : 'all',
													mode:'local',
													editable : false,
													emptyText : '请选择',
													labelStyle : 'text-align:right;',
													anchor : '90%'
										       },new Com.yucheng.bcrm.common.OrgField({
													searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
													fieldLabel : '<font color=red>*</font>机构',
													labelStyle : 'text-align:right;',
													id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
													name : 'CUST_ORG',
													allowBlank : false,
													hiddenName: 'instncode',   //后台获取的参数名称
													anchor : '90%',
													checkBox:true //复选标志
												})
							    ]
	                         }

	                 
	                   ]
	           }],
	        buttonAlign : 'center',
			buttons : [
					{
						text : '查询',
						handler : function() {
					
						if(!qForm.getForm().isValid()){
							Ext.Msg.alert("提醒","请填写必填项");
							return false;
						}
						select();
//							report4ShowWindow.show();
							var parameters = qForm.getForm().getValues(false);
						}
					}, {
						text : '重置',
						handler : function() {
							qForm.getForm().reset();
							Ext.getCmp("CUST_ORG").setValue('');
						}
					} ] 	     
		});
		function select(){
			var start = qForm.getForm().findField('StartDate').getValue();
			var org_diString = Ext.getCmp("CUST_ORG").hiddenField.getValue();//机构ID
			var dateYear = Ext.getCmp("yearType").getValue();
		    var bDate=Ext.util.Format.date(start,'Y-m-d');
		    if(start==''&&dateYear==''){
		    	Ext.Msg.alert("提醒","请填写必填项");
		    	return;
		    }
				var winWidth = screen.width - 10;
				var winHeight = screen.height - 60;
				var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
				winFeatures += "top=0,left=0,height="
						+ winHeight + ",width=" + winWidth;
				if(start!='')
				{
					qForm.getForm().findField('StartDate').setValue('');
					var url=basepath+'/reportJsp/showReport.jsp?raq=/report_52day.raq&qdate='+bDate+'&uid='+__units+'&org_id='+org_diString;
				}
				else if(dateYear=='1')
				{   
					Ext.getCmp("yearType").setValue('');
					var url=basepath+'/reportJsp/showReport.jsp?raq=/report_53year.raq&qdate='+'2008'+'&uid='+__units+'&org_id='+org_diString;
				}
				else if(dateYear=='2')
				{   
					Ext.getCmp("yearType").setValue('');
					var url=basepath+'/reportJsp/showReport.jsp?raq=/report_53year.raq&qdate='+'2009'+'&uid='+__units+'&org_id='+org_diString;
				}
				else if(dateYear=='3')
				{   
					var url=basepath+'/reportJsp/showReport.jsp?raq=/report_53year.raq&qdate='+'2010'+'&uid='+__units+'&org_id='+org_diString;
				}
				else if(dateYear=='4')
				{   
					Ext.getCmp("yearType").setValue('');
					var url=basepath+'/reportJsp/showReport.jsp?raq=/report_53year.raq&qdate='+'2011'+'&uid='+__units+'&org_id='+org_diString;
				}
				else if(dateYear=='5')
				{   
					Ext.getCmp("yearType").setValue('');
					var url=basepath+'/reportJsp/showReport.jsp?raq=/report_53year.raq&qdate='+'2012'+'&uid='+__units+'&org_id='+org_diString;
				}
				var winOpen = window.open(url,'chat' + new Date().getTime(),winFeatures);
			}
		var fpanel = new Ext.Panel({
			id : "fpanel",
			//width:600,
			//height:300,
			//labelWidth : 130, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
			//height: document.body.clientHeight,
			items :[qForm]
			        });
	// 布局模型
	var viewport = new Ext.Panel({
		title:'统计报表->客户结构分析->全辖客户数量及分类',
		renderTo : 'viewport_center',
		width:400,
		height:300,
		//layout : 'fit',
		frame : true,
				items: [{   
					//region : 'center',
					//layout : 'fit',
				    hidden:false,
				    margins: '0 0 0 0',
					items:[fpanel]
			     }] 
			});
	
}); 