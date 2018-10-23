/**
 * 
* @Description:客户经理概览信息
* @author geyu
* @date 2014-08-08
*
*/
Ext.onReady(function(){	
	/**数据字典**/
	//持卡情况
	var cardStore = new Ext.data.Store({
		restful:true,   
		autoLoad :true,
		proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000026'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});   
	
	var custMgrBaseInfoPanel= new Ext.FormPanel({
		autoHeight:true,
		labelWidth:220,//label的宽度
		labelAlign:'right',
		frame:false,
		autoScroll : true,
		buttonAlign:'center',
		items:[{
			autoHeight : true,
			layout:'column',
			items:[{
				columnWidth:.5,  
				layout:'form',
				items:[
					 {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'客户经理号',name:'CUST_MANAGER_ID'},
					 {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'归属机构',name:'ORG_NAME'},
					 {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'学历',name:'EDUCATION'},
					 {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'入行日期',name:'ENTRANTS_DATE'},
					 {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'金融从业时间',name:'FINANCIAL_JOB_TIME'},
					 {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'历年考评结果',name:'FINANCIAL_JOB_TIME'},
					 {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'所获奖励',name:'AWARD'},
					 {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'资格证书',name:'CERTIFICATE'}
		              ]
				},{
				columnWidth:.5,  
				layout:'form',
				items:[
				       {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'客户经理名称',name:'CUST_MANAGER_NAME'},
				       {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'归属部门',name:'DPT_NAME'},
				       {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'出生日期',name:'BIRTHDAY'},
				       {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'任职日期',name:'POSITION_TIME'},
				       {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'客户经理分级',name:'CUST_MANAGER_LEVEL'},
				       {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'工作表现',name:'WORK_PERFORMANCE'},
				       {xtype:'textfield',readOnly:true,anchor : '95%',fieldLabel:'岗位变动',name:'POSITION_CHANGE'}
			           ]
			}]
		}]
	});
   var _height= 260;	    
	//页面视图根面板
	   var viewport = new Ext.Viewport({
		layout : 'border',
		// border:false,
		items : [ {
			xtype : 'portal',
			id : 'center',
			region : 'center',
			// margins:'5 5 5 5',
			items : [ {
				columnWidth : 1,
				autoHeight : true,
				// layout:'fit',
				border : false,
				items : [ {
					title : '客户经理基本信息',
					layout : 'fit',
					style : 'padding:5px 5px 5px 5px',
					columnWidth : 1,
					autoHeight : true,
					collapsible : true,
					items : [ custMgrBaseInfoPanel ]
				} ]
			} ]
		} ]
	});
   // record
  var record=Ext.data.Record.create(
		  [
		   {name: 'CUST_MANAGER_ID'}
		  ,{name: 'CUST_MANAGER_NAME'}
		  ,{name: 'ORG_NAME'}
		  ,{name: 'DPT_NAME'}
		  ,{name: 'EDUCATION'}
		  ,{name: 'BIRTHDAY'} 	
		  ,{name: 'ENTRANTS_DATE'}
		  ,{name: 'POSITION_TIME'}
		  ,{name: 'FINANCIAL_JOB_TIME'}
		  ,{name: 'CUST_MANAGER_LEVEL'}
		  ,{name: 'EVA_RESULT'}
		  ,{name: 'WORK_PERFORMANCE'}
		  ,{name: 'AWARD'}
		  ,{name: 'POSITION_CHANGE'}
		  ,{name: 'CERTIFICATE'}
		   ]);

  //基本信息reader
  custMgrBaseInfoPanel.getForm().reader=new Ext.data.JsonReader({
	  root:'data'
  },record);
   
   //customerBaseInfoPanel加载数据
  custMgrBaseInfoPanel.getForm().load({
   	url: basepath+'/CustomerManagerInfoAction1!getMgrInfo.json',
   	method:'get',
   	params : {
   		'mgrId':_busiId
       }
   });
   
});


