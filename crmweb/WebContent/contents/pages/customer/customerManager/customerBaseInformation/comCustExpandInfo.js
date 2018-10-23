Ext.onReady(function() {
	Ext.QuickTips.init(); 
	var custid =oCustInfo.cust_id;//当前用户所查看的客户的客户号
 	
	var entHoldingTypeStore = new Ext.data.Store({//客户控股类型store
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
				url :basepath+'/lookup.json?name=CDE0100015'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	var comCustomerInfo = new Ext.FormPanel({//对公客户基本信息PANEL
 		title : '客户扩展信息',
		frame : true,
		autoScroll : true,
		region:'center',
		buttonAlign:'center',
		items : [ {
				xtype : 'fieldset',
				title : '基本信息',
				titleCollapse : true,
				collapsible : true,
				autoHeight : true,
				anchor : '95%',
				items : [ {
					layout : 'column',
					items : [{
						layout : 'form',
						columnWidth : .50,
						labelWidth : 120,
						items : [
						         {	xtype:'textfield',fieldLabel:'客户标识',name:'custId',labelStyle:'text-align:right;',value:custid,readOnly:true,anchor:'90%'},
						         {	xtype:'textfield',fieldLabel:'主要服务',name : 'mainService',labelStyle:'text-align:right;',anchor:'90%'},
						         {  xtype:'textfield',fieldLabel:'经济区编码',name : 'zoneCode',labelStyle:'text-align:right;',anchor:'90%'},
						         {	xtype:'textfield',fieldLabel:'主营业务',name:'mainBusiness',labelStyle:'text-align:right;',anchor:'90%'}
						        	]
					},{
						layout : 'form',
						columnWidth : .50,
						labelWidth : 120,
						items : [   {	xtype:'textfield',fieldLabel:'主要客户',name:'mainCust',labelStyle:'text-align:right;',anchor:'90%'},
							         {	xtype:'textfield',fieldLabel:'主要产品',name:'mainProduct',labelStyle:'text-align:right;',anchor:'90%'},
							         {	xtype:'textfield',fieldLabel:'员工规模',name : 'employeeScale',labelStyle:'text-align:right;',anchor:'90%'},
							         {  xtype:'textfield',fieldLabel:'资产规模',name : 'assetsScale',labelStyle:'text-align:right;',anchor:'90%'}]
					}
					]
				}]
		}
		],
		buttons : [{
		 	text : '保存',
			handler : function(){
			var updArray={};
			updArray.custId=comCustomerInfo.getForm().findField("custId").getValue();
			updArray.mainProduct=comCustomerInfo.getForm().findField("mainProduct").getValue();
			updArray.mainCust=comCustomerInfo.getForm().findField("mainCust").getValue();
			updArray.mainService=comCustomerInfo.getForm().findField("mainService").getValue();
			updArray.zoneCode=comCustomerInfo.getForm().findField("zoneCode").getValue();
			updArray.mainBusiness=comCustomerInfo.getForm().findField("mainBusiness").getValue();
			updArray.employeeScale=comCustomerInfo.getForm().findField("employeeScale").getValue();
			updArray.assetsScale=comCustomerInfo.getForm().findField("assetsScale").getValue();
			debugger;
			Ext.Ajax.request( {
				url : basepath + '/comCertInfoAction!update.json',
				method : 'POST',
				params :{
				'updArray':Ext.encode(updArray)
			} 	,
				success : function(response){
				debugger;
				var resultArray = Ext.util.JSON.decode(response.status);
				var resultError = response.responseText;
				if ((resultArray == 200 ||resultArray == 201)&&resultError=='') {
					loadData();
					Ext.Msg.alert('提示', '操作成功');
				} else {
					if(resultArray == 403){
						Ext.Msg.alert('提示', response.responseText);
					}else{
						Ext.Msg.alert('提示', '操作失败,失败原因:' + resultError);
					}
				};
			}			
			});
		}	
		}]
	});

	var viewport_center = new Ext.Panel({
		renderTo:'viewport_center',
		height:document.body.scrollHeight-30,
		width : document.body.clientWidth-223,
		layout:'fit',
		autoScroll:true,
		items: [comCustomerInfo] 
	});
	function loadData(){
		   Ext.Ajax.request({
		        url: basepath + '/comCertInfoAction!searchOrgCustInfo.json?custId='+custid,
		        method: 'GET',
		        success: function(response) {
		    	debugger;
		            var nodeArra = Ext.util.JSON.decode(response.responseText);
		            comCustomerInfo.getForm().findField("mainProduct").setValue(nodeArra.mainProduct);
		            comCustomerInfo.getForm().findField("mainCust").setValue(nodeArra.mainCust);
		            comCustomerInfo.getForm().findField("mainService").setValue(nodeArra.mainService);
		            comCustomerInfo.getForm().findField("zoneCode").setValue(nodeArra.zoneCode);
		            comCustomerInfo.getForm().findField("mainBusiness").setValue(nodeArra.mainBusiness);
		            comCustomerInfo.getForm().findField("employeeScale").setValue(nodeArra.employeeScale);
		            comCustomerInfo.getForm().findField("assetsScale").setValue(nodeArra.assetsScale);
		        },
		        failure: function(a, b, c) {}
		    });
	}
	loadData();

});