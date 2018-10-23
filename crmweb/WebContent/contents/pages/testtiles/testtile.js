(function(){
	
	function testTileCb(c,responseText){
		
		var data = Ext.decode(responseText).json.data;
		
		c.body.dom.innerHTML += data[0].CUST_ZH_NAME+= data[1].CUST_ZH_NAME+= data[2].CUST_ZH_NAME;
	}
	
	 var store = new Ext.data.Store({
			restful:true,	
	        proxy : new Ext.data.HttpProxy({url:basepath+'/customerBaseInformation.json'}),
	        reader: new Ext.data.JsonReader({
	        	totalProperty : 'json.count',
	        	root:'json.data'
	        }, [
				{name: 'custI d',mapping :'CUST_ID'},
				{name: 'custZhName',mapping :'CUST_ZH_NAME'},
				{name: 'CERT_TYPE_ORA'},
				{name:'CUST_STAT_ORA'},
				{name: 'CUST_TYP_ORA'},
				{name: 'CUST_LEV_ORA'},
				{name:'certType',mapping: 'CERT_TYPE'},
				{name:'custStat',mapping: 'CUST_STAT'},
				{name:'custTyp',mapping: 'CUST_TYP'},
				{name:'custLev',mapping: 'CUST_LEV'},
//				{name: 'EN_ABBR'},
				{name: 'INSTITUTION_CODE'},
				{name: 'INSTITUTION_NAME'},
//				{name: 'BGN_DT'},
				{name: 'MGR_ID'},
				{name: 'MGR_NAME'},
				{name: 'custEnName',mapping :'CUST_EN_NAME'},//英文名
				{name: 'otherName',mapping :'OTHER_NAME'},//其他名
				{name: 'certNum',mapping :'CERT_NUM'},//证件号码
				{name: 'linkPhone',mapping :'LINK_PHONE'},//联系电话
				{name: 'postNo',mapping :'POST_NO'},//邮编
				{name: 'addr',mapping :'ADDR'},//地址
				{name: 'linkUser',mapping :'LINK_USER'}//联系人
				
			])
		});
	
		var rownum = new Ext.grid.RowNumberer({
					header : 'No.',
					width : 28
				});
		// 定义列模型
		var cm = new Ext.grid.ColumnModel([
		        {header : '客户号',dataIndex : 'custId',sortable : true,width : 150},
			    {header : '客户名称',dataIndex : 'custZhName',width : 200,sortable : true}
			    
				]);
	 
	 
	var grid = new Ext.grid.GridPanel({
		title : 'cust',
		frame : true,
		autoScroll : true,
		region : 'center', // 和VIEWPORT布局模型对应，充当center区域布局
		store : store, // 数据存储
		stripeRows : true, // 斑马线
		cm : cm, // 列模型
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		},
		listeners : {
			afterrender : function(c){
				Wlj.TileMgr.addDataCfg({
					method:'get',
					url : basepath + '/customerBaseInformation.json',
					controlPanel : _this,
					//dataCb : testTileCb,
					store : store
				});
			}
		}
	});
	
	
	return grid;
//	new Ext.Panel({
//		frame : true,
//		html : 'Hello World, Tiles!',
//		buttons : [{
//			text : 'button',
//			handler : function(){
//				_APP.openWindow({
//					name : 'hello world!'
//				});
//			}
//		}],
//		listeners : {
//			afterrender : function(c){
//				var _this = this;
//				var vs = c.container.getViewSize();
//				var xy = c.ownerCt.el.getXY();
//				c.body.dom.innerHTML += '<br> My height is :'+vs.height +'<br> My width is :'+ vs.width
//				+'<br> My X is : '+xy[0]+'<br> My Y is :'+xy[1];
//				Wlj.TileMgr.addDataCfg({
//					method:'get',
//					url : basepath + '/customerBaseInformation.json',
//					controlPanel : _this,
//					dataCb : testTileCb
//				});
//				
////				Ext.Ajax.request({
////					method:'get',
////					url : basepath + '/customerBaseInformation.json',
////					success : function(response){
////						var t = response.responseText;
////						debugger;
////						var data = Ext.decode(t).json.data;
////						
////						_this.body.dom.innerHTML += data[0].CUST_ZH_NAME+= data[1].CUST_ZH_NAME+= data[2].CUST_ZH_NAME;
////					}
////				});
//			}
//		}
//	});
})();