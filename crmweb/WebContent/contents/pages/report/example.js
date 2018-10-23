Ext.onReady(function() {
	// //////////////////////////////最低还款额客户统计FORM///////////////////////////
	
	
	var form4 = new Ext.form.FormPanel(
			{
				title : '全辖客户数量及分类',
				height : 180,
				width : document.body.clientWidth - 280,
				buttonAlign : 'center',
				labelWidth : 160,// label的宽度
				labelAlign : 'right',
				frame : true,
				region : 'north',
				split : true,
				items : [ {
					layout : 'column',
					items : [ {
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90,
						border : false,
						items : [new Com.yucheng.bcrm.common.OrgField({
							searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
							fieldLabel : '<font color=red>*</font>机构',
							labelStyle : 'text-align:right;',
							id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
							name : 'CUST_ORG', 
							allowBlank : false,
							hiddenName: 'instncode',   //后台获取的参数名称
							anchor : '99%',
							checkBox:true //复选标志
						})
						]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 90,
						border : false,
						items : [ {
							id : 'form4Date_S',
							name : 'form4Date_S',
							format : 'Y-m-d',
							xtype : 'datefield',
							resizable : true,
							fieldLabel : '<font color=red>*</font>查询日期',
							width : '100',
							allowBlank : false,
							anchor : '99%'
						} ]
					} ]
				} ],
				buttonAlign : 'center',
				buttons : [
						{
							text : '查询',
							handler : function() {
							if(!form4.getForm().isValid()){
								Ext.Msg.alert("提醒","请填写必填项");
								return false;
							}
							select();
//								report4ShowWindow.show();
								var parameters = form4.getForm()
										.getValues(false);
							}
						}, {
							text : '重置',
							handler : function() {
								form4.getForm().reset();
								Ext.getCmp("CUST_ORG").setValue('');
							}
						} ]
			});
	
	function select(){
		
		var start = Ext.getCmp('form4Date_S').getValue();
		
		var org_diString = Ext.getCmp("CUST_ORG").hiddenField.getValue();//机构
		
		if(org_diString== ''){
			Ext.Msg.alert('消息框',	'请选择机构！');
			return;
		}else if (start == '') {
			Ext.Msg.alert('消息框',	'请先选择查询日期！');
			return;
		}
		
	var bDate=Ext.util.Format.date(start,'Y-m-d');
		
		
		var orgStr='';
		if (org_diString != ''){
			orgStr=org_diString;
		}
		var orgarr = orgStr.split(',');
		var ostr ="";
		
		for(var i =0;i<orgarr.length;i++){
			ostr+="'"+orgarr[i]+"',";
		}
		orgStr = ostr.substring(0, ostr.length-1);
		debugger;
		var winWidth = screen.width - 10;
		var winHeight = screen.height - 60;
		var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
		winFeatures += "top=0,left=0,height="
				+ winHeight + ",width=" + winWidth;
		var url=basepath+'/reportJsp/showReport.jsp?raq=/report_ca.raq&qdate='+bDate+'&org_id=211111';
		var winOpen = window.open(url,'chat' + new Date().getTime(),winFeatures);
	}
	

	
	//右边报表查询页面的布局
	var formPanel = new Ext.Panel( {

		border : false,
		frame:true,
		items : [{
			border : false,
			layout : 'fit',
			items : [form4]
		} ]
	});
	// 布局模型
	var viewport = new Ext.Viewport( {
		layout : 'fit',
		items : [formPanel]
	});
});