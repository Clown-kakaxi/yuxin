Ext.onReady(function() {

	var fields1 = [ {
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	}];



	var columns1 = new Ext.grid.ColumnModel([{
		dataIndex : 'a0',
		header : '名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '代码',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}, {
		dataIndex : 'a2',
		header : '荐/新/惠',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}]);

	var data1 = [
	            ['得利宝天添利A款','Lc001','新'],
	            ['得利宝天添利B款','Lc002','新'],
	            ['得利宝天添利C款','Lc003','荐'],
	            ['得利宝天添利D款','Lc004','惠'],
	            ['得利宝天添利E款','Lc005','惠']
	];



	var store1 = new Ext.data.ArrayStore({
		fields : fields1,
		data : data1
	});

	
	var lcGrid = new Ext.grid.EditorGridPanel({
		region : 'center',
		height:150,
		frame : true,
		store : store1,
		cm : columns1
});
	
	
	var fields2 = [ {
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	}];



	var columns2 = new Ext.grid.ColumnModel([{
		dataIndex : 'a0',
		header : '名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '代码',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}, {
		dataIndex : 'a2',
		header : '荐/新/惠',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}]);

	var data2 = [
	            ['沪深300价值','Jj001','新'],
	            ['中小板A','Jj002','新'],
	            ['中小板B','Jj003','荐'],
	            ['胜利精选','Jj004','惠'],
	            ['收益宝B','Jj005','惠']
	];



	var store2 = new Ext.data.ArrayStore({
		fields : fields2,
		data : data2
	});

	
	var jjGrid = new Ext.grid.EditorGridPanel({
		region : 'center',
		height:150,
		frame : true,
		store : store2,
		cm : columns2
});
	
	var fields3 = [ {
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	}];



	var columns3 = new Ext.grid.ColumnModel([{
		dataIndex : 'a0',
		header : '名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '代码',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}, {
		dataIndex : 'a2',
		header : '荐/新/惠',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}]);

	var data3 = [
	            ['06鲁高速','Zj001','新'],
	            ['民生转债','Zj002','新'],
	            ['总行转债','Zj003','荐'],
	            ['11海城债','Zj004','惠'],
	            ['国电转债','Zj005','惠']
	];



	var store3 = new Ext.data.ArrayStore({
		fields : fields3,
		data : data3
	});

	
	var zjGrid = new Ext.grid.EditorGridPanel({
		region : 'center',
		height:150,
		frame : true,
		store : store3,
		cm : columns3
});
	
	var fields4 = [ {
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	}];



	var columns4 = new Ext.grid.ColumnModel([{
		dataIndex : 'a0',
		header : '名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '代码',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}, {
		dataIndex : 'a2',
		header : '荐/新/惠',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}]);

	var data4 = [
	            ['金创黄金50g','Hj001','新'],
	            ['金创黄金100g','Hj002','新'],
	            ['金创黄金200g','Hj003','荐'],
	            ['熊猫金币100g','Hj004','惠'],
	            ['熊猫金币200g','HJ005','惠']
	];



	var store4 = new Ext.data.ArrayStore({
		fields : fields4,
		data : data4
	});

	
	var hjGrid = new Ext.grid.EditorGridPanel({
		region : 'center',
		height:150,
		frame : true,
		store : store4,
		cm : columns4
});
	
	var fields5 = [ {
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	}];



	var columns5 = new Ext.grid.ColumnModel([{
		dataIndex : 'a0',
		header : '名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '代码',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}, {
		dataIndex : 'a2',
		header : '荐/新/惠',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}]);

	var data5 = [
	            ['通知存款','Qt001','新'],
	            ['协议存款','Qt002','新'],
	            ['协定户存款','Qt003','荐'],
	            ['证劵交易保证金','Qt004','惠'],
	            ['贷记卡存款','Qt005','惠']
	];



	var store5 = new Ext.data.ArrayStore({
		fields : fields5,
		data : data5
	});

	
	var qtGrid = new Ext.grid.EditorGridPanel({
		region : 'center',
		height:150,
		frame : true,
		store : store5,
		cm : columns5
});
	
	var mainPanel = new Ext.Panel({
	    layout : "fit",
		frame : true,
		region:'center',
		title:"销售首页",
		items:[{xtype:'portal',
		        region:'center',
		        items:[{
		       	 columnWidth:.4,
		         border:false,
		         autoHeight:true,
		         items:[{
		             collapsible:true,
		             layout:'fit',
		             title:'理财产品',
		             style:'padding:0px 0px 0px 0px',
		             items:[lcGrid]
		         		}]
		        },{
			       	 columnWidth:.4,
			         border:false,
			         autoHeight:true,
			         items:[{
			             collapsible:true,
			             layout:'fit',
			             title:'基金',
			             style:'padding:0px 0px 0px 0px',
			             items:[jjGrid]
			         		}]
			        },{
			   	 columnWidth:.2,
			     border:false,
			     autoHeight:true,
			     items:[{
			    	 title:'收益排名前十的基金',
			    	    style:{
			    	        margin:'0px 0 0px 0'
			    	    },
			    	    bodyStyle:'background-color:#fff',    		
			    	    frame:true,
			    	    collapsible:true,
			    	    html:"<div class='info_box' style='height:130px'>"+
			    	         "<ul>" +
			    	         "<marquee onmouseover=stop(); onmouseout=start(); direction='up' align='top' style='height:100%;' scrollamount=2>"+
			    	         "<li id='xinxitixing1' >沪深300价值</li>" +
			    	         "<li id='xinxitixing2' >沪深300增强</li>" +
			    	         "<li id='xinxitixing3' >中小板A</li>" +
			    	         "<li id='xinxitixing4' >中小板B</li>" +
			    	         "<li id='xinxitixing5' >新经济</li>" +
			    	         "<li id='xinxitixing6' >收益宝A</li>" +
			    	         "<li id='xinxitixing7' >收益宝B</li>" +
			    	         "<li id='xinxitixing7' >胜利精选</li>" +
			    	         "<li id='xinxitixing7' >深成收益</li>" +
			    	         "<li id='xinxitixing7' >收益宝B</li>" +
			    	         "</marquee>"+
			    	         "</ul>" +
			    	         "</div>"
			    	}]
		       },{
			       	 columnWidth:.4,
			         border:false,
			         autoHeight:true,
			         items:[{
			             collapsible:true,
			             layout:'fit',
			             title:'债券',
			             style:'padding:0px 0px 0px 0px',
			             items:[zjGrid]
			         		}]
			        },{
				       	 columnWidth:.4,
				         border:false,
				         autoHeight:true,
				         items:[{
				             collapsible:true,
				             layout:'fit',
				             title:'黄金',
				             style:'padding:0px 0px 0px 0px',
				             items:[hjGrid]
				         		}]
				        },{
				   	 columnWidth:.2,
				     border:false,
				     autoHeight:true,
				     items:[{
				    	 title:'最新利率',
				    	    style:{
				    	        margin:'0px 0 0px 0'
				    	    },
				    	    bodyStyle:'background-color:#fff',    		
				    	    frame:true,
				    	    collapsible:true,
				    	    html:"<div class='info_box' style='height:130px'>"+
				    	         "<ul>" +
				    	         "<marquee onmouseover=stop(); onmouseout=start(); direction='up' align='top' style='height:100%;' scrollamount=2>"+
				    	         "<li id='xinxitixing1' >年利率 :3.8%</li>" +
				    	         "<li id='xinxitixing2' >季利率:3.0%</li>" +
				    	         "<li id='xinxitixing3' >月利率:2.5%</li>" +
				    	         "<li id='xinxitixing4' >基金1利率:5%</li>" +
				    	         "<li id='xinxitixing5' >基金2利率:5%</li>" +
				    	         "<li id='xinxitixing1' >年利率 :3.8%</li>" +
				    	         "<li id='xinxitixing2' >季利率:3.0%</li>" +
				    	         "<li id='xinxitixing3' >月利率:2.5%</li>" +
				    	         "</marquee>"+
				    	         "</ul>" +
				    	         "</div>"
				    	}]
			       
		       },{
			       	 columnWidth:.4,
			         border:false,
			         autoHeight:true,
			         items:[{
			             collapsible:true,
			             layout:'fit',
			             title:'其它',
			             style:'padding:0px 0px 0px 0px',
			             items:[qtGrid]
			         		}]
			        }]
		}]
		});
	
//页面布局
var view = new Ext.Viewport( {
	layout : "fit",
	frame : true,
	items : [ {
		layout : 'border',
		items : [
				{
					region : 'center',
					id : 'center-panel',
					layout : 'fit',
					items : [ mainPanel ]
				}
		]
	} ]
});

});