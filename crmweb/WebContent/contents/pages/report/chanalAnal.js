Ext.onReady(function() {
	// //////////////////////////////最低还款额客户统计FORM///////////////////////////
	
	
	var form4 = new Ext.form.FormPanel(
			{
				title : '渠道使用分析',
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
							fieldLabel : '机构',
							labelStyle : 'text-align:right;',
							id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
							name : 'CUST_ORG', 
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
							fieldLabel : '查询日期',
							width : '100',
							anchor : '99%'
						} ]
					} ]
				} ],
				buttonAlign : 'center',
				buttons : [
						{
							text : '查询',
							handler : function() {
							select();
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
		var winWidth = screen.width - 10;
		var winHeight = screen.height - 60;
		var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
		winFeatures += "top=0,left=0,height="
				+ winHeight + ",width=" + winWidth;
		var url=basepath+'/reportJsp/showReport.jsp?raq=/report_1.raq';
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