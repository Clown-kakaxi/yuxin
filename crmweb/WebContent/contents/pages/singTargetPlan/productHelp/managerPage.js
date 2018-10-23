Ext.onReady(function() {
	//产品推荐
	var fields1 = [ {
		name : 'a0'
	}, {
		name : 'a1'
	}, {
		name : 'a2'
	}];



	var columns1 = new Ext.grid.ColumnModel([{
		dataIndex : 'a0',
		header : '产品名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '上架日期',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}, {
		dataIndex : 'a2',
		header : '风险等级',
		sortable : true,
		width : 300,
		editor : new Ext.form.Field()
	}]);

	var data1 = [
	            ['得利宝天添利A款','2013-09-27','中'],
	            ['得利宝天添利B款','2013-08-20','中'],
	            ['得利宝天添利C款','2013-09-27','中'],
	            ['得利宝天添利D款','2013-09-27','低'],
	            ['得利宝天添利E款','2013-09-27','高']
	];



	var store1 = new Ext.data.ArrayStore({
		fields : fields1,
		data : data1
	});

	
	var newList = new Ext.grid.EditorGridPanel({
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
		header : '产品名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '推荐结束日期',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}, {
		dataIndex : 'a2',
		header : '风险等级',
		sortable : true,
		width : 300,
		editor : new Ext.form.Field()
	}]);

	var data2 = [
	            ['得利宝天添利A款','2013-09-27','中'],
	            ['得利宝天添利B款','2013-08-20','中'],
	            ['得利宝天添利C款','2013-09-27','中'],
	            ['得利宝天添利D款','2013-09-27','低'],
	            ['得利宝天添利E款','2013-09-27','高']
	];



	var store2 = new Ext.data.ArrayStore({
		fields : fields2,
		data : data2
	});

	
	var goodList = new Ext.grid.EditorGridPanel({
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
		header : '产品名称',
		sortable : true,
		width : 140
	}, {
		dataIndex : 'a1',
		header : '到期日',
		sortable : true,
		width : 140,
		editor : new Ext.form.Field()
	}, {
		dataIndex : 'a2',
		header : '风险等级',
		sortable : true,
		width : 300,
		editor : new Ext.form.Field()
	}]);

	var data3 = [
	            ['得利宝天添利A款','2013-09-27','中'],
	            ['得利宝天添利B款','2013-09-20','中'],
	            ['得利宝天添利C款','2013-09-12','中'],
	            ['得利宝天添利D款','2013-09-21','低'],
	            ['得利宝天添利E款','2013-09-12','高']
	];



	var store3 = new Ext.data.ArrayStore({
		fields : fields2,
		data : data2
	});

	
	var delayList = new Ext.grid.EditorGridPanel({
		region : 'center',
		height:150,
		frame : true,
		store : store3,
		cm : columns3
});
	
var mainPanel = new Ext.Panel({
    layout : "fit",
	frame : true,
	region:'center',
	title:"客户经理首页",
	items:[{xtype:'portal',
	        region:'center',
	        items:[{
	       	 columnWidth:.7,
	         border:false,
	         autoHeight:true,
	         items:[{
	             collapsible:true,
	             layout:'fit',
	             title:'新品推荐列表',
	             style:'padding:0px 0px 0px 0px',
	             items:[newList]
	         		}]
	        },{
		   	 columnWidth:.3,
		     border:false,
		     autoHeight:true,
		     items:[{
		    	 title:'产品公告',
		    	    style:{
		    	        margin:'0px 0 0px 0'
		    	    },
		    	    bodyStyle:'background-color:#fff',    		
		    	    frame:true,
		    	    collapsible:true,
		    	    html:"<div class='info_box' style='height:130px'>"+
		    	         "<ul>" +
		    	         "<marquee onmouseover=stop(); onmouseout=start(); direction='up' align='top' style='height:100%;' scrollamount=2>"+
		    	         "<li id='xinxitixing1' >黄金50g新到发放中</li>" +
		    	         "<li id='xinxitixing2' >理财125号发行中</li>" +
		    	         "<li id='xinxitixing3' >创业黄金产品即将到期</li>" +
		    	         "<li id='xinxitixing4' >黄金50g新到发放中</li>" +
		    	         "<li id='xinxitixing5' >理财125号发行中</li>" +
		    	         "<li id='xinxitixing6' >创业黄金产品即将到期</li>" +
		    	         "<li id='xinxitixing7' >黄金50g新到发放中</li>" +
		    	         "</marquee>"+
		    	         "</ul>" +
		    	         "</div>"
		    	}]
	       },{
		       	 columnWidth:.7,
		         border:false,
		         autoHeight:true,
		         items:[{
		             collapsible:true,
		             layout:'fit',
		             title:'推荐产品列表',
		             style:'padding:0px 0px 0px 0px',
		             items:[goodList]
		         		}]
		        },{
			   	 columnWidth:.3,
			     border:false,
			     autoHeight:true,
			     items:[{
			    	 title:'优惠产品列表',
			    	    style:{
			    	        margin:'0px 0 0px 0'
			    	    },
			    	    bodyStyle:'background-color:#fff',    		
			    	    frame:true,
			    	    collapsible:true,
			    	    html:"<div class='info_box' style='height:130px'>"+
			    	         "<ul>" +
			    	         "<marquee onmouseover=stop(); onmouseout=start(); direction='up' align='top' style='height:100%;' scrollamount=2>"+
			    	         "<li id='xinxitixing1' >黄金50g</li>" +
			    	         "<li id='xinxitixing2' >理财125号</li>" +
			    	         "<li id='xinxitixing3' >创业黄金</li>" +
			    	         "<li id='xinxitixing4' >黄金50g</li>" +
			    	         "<li id='xinxitixing5' >理财125号</li>" +
			    	         "<li id='xinxitixing6' >创业黄金产品</li>" +
			    	         "<li id='xinxitixing7' >黄金50g</li>" +
			    	         "</marquee>"+
			    	         "</ul>" +
			    	         "</div>"
			    	}]
		       
	       },{
		       	 columnWidth:.7,
		         border:false,
		         autoHeight:true,
		         items:[{
		             collapsible:true,
		             layout:'fit',
		             title:'最近10天到期产品列表',
		             style:'padding:0px 0px 0px 0px',
		             items:[delayList]
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