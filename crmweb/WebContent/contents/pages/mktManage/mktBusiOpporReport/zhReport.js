/**
 * 支行报表JS，wzy，2013-04-02
 */
Ext.onReady(function() {
	var form4 = new Ext.form.FormPanel({
				title : '支行报表',
				height : 180,
				width : document.body.clientWidth - 280,
				buttonAlign : 'center',
				labelWidth : 160,
				labelAlign : 'right',
				frame : true,
				region : 'north',
				split : true,
				items : [{
							layout : 'column',
							items : [{
										columnWidth : .25,
										layout : 'form',
										labelWidth : 90,
										border : false,
										items : [{
													id : 'form4Date_S',
													name : 'form4Date_S',
													format : 'Y-m-d',
													xtype : 'datefield',
													resizable : true,
													fieldLabel : '商机日期',
													width : '100',
													anchor : '99%'
												}]
									}]
						}],
				buttonAlign : 'center',
				buttons : [{
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
						}]
			});

	// 查询
	function select() {
		var winWidth = screen.width - 10;
		var winHeight = screen.height - 60;
		var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
		winFeatures += "top=0,left=0,height=" + winHeight + ",width="
				+ winWidth;
		var url = basepath + '/reportJsp/showReport.jsp?raq=/busioppor_zh.raq';
		var winOpen = window.open(url, 'chat' + new Date().getTime(),
				winFeatures);
	}

	// 右边报表查询页面的布局
	var formPanel = new Ext.Panel({
				border : false,
				frame : true,
				items : [{
							border : false,
							layout : 'fit',
							items : [form4]
						}]
			});

	// 布局模型
	var viewport = new Ext.Viewport({
				layout : 'fit',
				items : [formPanel]
			});
});