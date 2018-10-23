/**
 * 
 * 
 */

// 投资总揽 详情页面
var showPanelBase = new Ext.form.FormPanel({
	title : '',
	region : 'north',
	labelWidth : 150,// label的宽度
	labelAlign : 'right',
	frame : true,
	autoScroll : true,
	region : 'north',
	bodyStyle : "padding:5px 5px 5px 5px",
	split : true,
	items : [{
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			// items : charPanel
			items : [{
				title : '管理资产占比',
				height : 250,
				width : 350,
				html : '<div id="charDiv1" style="width:100%;height:100%"></div>'
			}]
		}, {
			columnWidth : .5,
			layout : 'form',
			// items : linePanel
			items : [{
				title : '投资规模',
				height : 250,
				width : 400,
				html : '<div id="charLine1" style="width:100%;height:100%"></div>'
			}]
		}]
	}, {
		layout : 'column',
		items : [{
					columnWidth : .5,
					layout : 'form',
					items : radarPanel_02
				}, {
					columnWidth : .5,
					layout : 'form',
					items : [{
						title : '投资效率',
						height : 250,
						width : 400,
						html : '<div id="chartdiv2" style="width:100%;height:100%"></div>'
					}]
				}]
	}]
});

// 资产结构详情
var assesPanel = new Ext.form.FormPanel({
	title : '',
	region : 'north',
	labelWidth : 150,// label的宽度
	labelAlign : 'right',
	frame : true,
	autoScroll : true,
	region : 'north',
	bodyStyle : "padding:5px 5px 5px 5px",
	split : true,
	items : [{
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			// items : charPanel1
			items : [{
				title : '管理资产占比',
				height : 250,
				width : 350,
				html : '<div id="charDiv3" style="width:100%;height:100%"></div>'
			}]
		}, {
			columnWidth : .5,
			layout : 'form',
			// items: charPanel2
			items : [{
				title : '投资风险占比',
				height : 250,
				width : 400,
				html : '<div id="charDiv4" style="width:100%;height:100%"></div>'
			}]
		}]
	}, {
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			// items:charPanel3
			items : [{
				title : '资产分类',
				height : 250,
				width : 350,
				html : '<div id="charDiv5" style="width:100%;height:100%"></div>'
			}]
		}, {
			columnWidth : .5,
			layout : 'form'
		}]
	}]
});

// 产品结构详情
var prodPanel = new Ext.form.FormPanel({
	title : '',
	region : 'north',
	labelWidth : 150,// label的宽度
	labelAlign : 'right',
	frame : true,
	autoScroll : true,
	region : 'north',
	bodyStyle : "padding:5px 5px 5px 5px",
	split : true,
	items : [{
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			// items : charPanel8
			items : [{
				title : '管理资产占比',
				height : 250,
				width : 350,
				html : '<div id="charDiv6" style="width:100%;height:100%"></div>'
			}]
		}, {
			columnWidth : .5,
			layout : 'form',
			// items: charPanel9
			items : [{
				title : '产品占比',
				height : 250,
				width : 400,
				html : '<div id="charDiv7" style="width:100%;height:100%"></div>'
			}]
		}]
	}, {
		layout : 'column',
		items : [{
			columnWidth : .5,
			layout : 'form',
			// items: charPanel10
			items : [{
				title : '资产分类',
				height : 250,
				width : 350,
				html : '<div id="charDiv8" style="width:100%;height:100%"></div>'
			}]
		}, {
			columnWidth : .5,
			layout : 'form'
		}]
	}]
});

var showTabpanel = new Ext.TabPanel({
			region : 'center',
			deferredRender : false,
			margins : '0 4 4 0',
			activeTab : 0,
			items : [{
						title : '投资总揽',
						autoScroll : true,
						items : showPanelBase
					}, {
						title : '资产结构',
						autoScroll : true,
						items : assesPanel
					}, {
						title : '产品结构',
						autoScroll : true,
						items : prodPanel
					}]

		});

var showWinBase = new Ext.Window({
			resizable : false,
			collapsible : false,
			draggable : true,
			closeAction : 'hide',
			modal : true, // 模态窗口
			animCollapse : false,
			border : false,
			loadMask : true,
			closable : true,
			constrain : true,
			layout : 'fit',
			width : 800,
			height : 480,
			buttonAlign : "center",
			title : '客户投资总揽',
			items : [showTabpanel]
		});