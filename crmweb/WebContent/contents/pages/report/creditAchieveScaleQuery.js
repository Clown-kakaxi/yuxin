Ext.onReady(function() {
/**********************************************************/
		var qForm = new Ext.form.FormPanel({
			id : "qfrom",
			labelWidth : 90, // 标签宽度
			 title: "客户管理->客户信息查询->信贷业务变化检索->到达一定规模", 
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
	   				layout : 'column',
	   				border : false,
	   	           items :[{
	   	        	   columnWidth : .5,
	   	        	   layout : 'form',
	   	        	   border : false,
	   	        	   labelWidth : 200,
						items : [ {
							id:'oddate',
							name:'oddate',
							fieldLabel:'数据日期',
							xtype:'datefield',
							value:' ',
							format:'Y-m-d',
							allowBlank : false,
							labelStyle : 'text-align:right;',
							anchor:'85%'
						},{
							xtype : 'numberfield',
							name : 'effectAmount',
							fieldLabel : '有效授信额度>=(<font color="red">单位：万元</font>)',
							labelStyle : 'text-align:right;',
							anchor : '85%'
						}]
					   },  {
						layout : 'form',
						columnWidth : .5,
						labelWidth : 160,
						items : [new Com.yucheng.bcrm.common.OrgField({
							searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
							fieldLabel : '所属机构',
							labelStyle : 'text-align:right;',
							id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
							name : 'CUST_ORG', 
							hiddenName: 'instncode',   //后台获取的参数名称
							anchor : '85%',
							checkBox:true //复选标志
						}),{
							xtype : 'numberfield',
							name : 'moneyBalance',
							fieldLabel : '用信余额>=(<font color="red">单位：万元</font>)',
							labelStyle : 'text-align:right;',
							anchor : '85%'
						} ]
		           }],
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
							var parameters = qForm.getForm()
									.getValues(false);
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
			var start = Ext.getCmp('oddate').getValue();
			var moneyBalance = 	qForm.getForm().findField("moneyBalance").getValue();
			var effectAmount = qForm.getForm().findField("effectAmount").getValue();
			var org_diString = Ext.getCmp("CUST_ORG").getValue();//机构
		if(moneyBalance*1==0){
			moneyBalance=0;
		}if(effectAmount*1==0){
			effectAmount=0;
		}
			
		    var bDate=Ext.util.Format.date(start,'Y-m-d');
		
				var winWidth = screen.width - 10;
				var winHeight = screen.height - 60;
				var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
				winFeatures += "top=0,left=0,height="
						+ winHeight + ",width=" + winWidth;
				var url=basepath+'/reportJsp/showReport.jsp?raq=/report_48.raq&qdate='+bDate+'&uid='+__units+'&org_id='+org_diString+'&amt1='+(moneyBalance*10000)+'&amt2='+(effectAmount*10000);
				var winOpen = window.open(url,'chat' + new Date().getTime(),winFeatures);
			}

		// 布局模型
		var viewport = new Ext.Viewport({
			layout:'fit',
			items: [qForm] 
		});
	
}); 