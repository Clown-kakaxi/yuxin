Ext.onReady(function(){
	 var simple = new Ext.FormPanel({
	        frame:true,
	        reader: new Ext.data.JsonReader({
                root:'json.data'
                }, ['CUST_NAME','CUST_ZZDM','BELONG_INSTN','HY_CLASS','CUST_SCOPE','CUST_BIG_LEV','CUST_SMALL_LEV','OFFICE_ADDR','OPAC_DT']),
	        items: [{
	           xtype:'fieldset',
	           title: '基本信息',
	           autoHeight:true,
               items :[{ layout:'column',
	                     items:[{
	                         layout: 'form',
	                         items: [{
	                             xtype:'textfield',
	                             fieldLabel: '客户名称',
	                             name: 'CUST_NAME',
	                             anchor:'90%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '组织机构代码',
	                             name: 'CUST_ZZDM',
	                             anchor:'90%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '归属网点',
	                             name: 'BELONG_INSTN',
	                             disabled : true,
	                             anchor:'90%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '行业',
	                             name: 'HY_CLASS',
	                             anchor:'90%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '客户规模',
	                             name: 'CUST_SCOPE',
	                             anchor:'90%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '经营地址',
	                             name: 'OFFICE_ADDR',
	                             anchor:'90%'
	                         },{
	                             xtype:'textfield',
	                             fieldLabel: '合作年限',
	                             name: 'OPAC_DT',
	                             anchor:'90%'
	                         }]
	                     }
	            ]}
	            ]},{
	            xtype:'fieldset',
	            title: '合作信息',
	            autoHeight:true,
	            items :[{ layout:'column',
                 items:[{
                     layout: 'form',
                     items: [ {
                         xtype:'textfield',
                         fieldLabel: '存款时点',
                         name: 'DEP_BAL',
                         style:'text-align:right;',
                         anchor:'90%'
                     }, {
                         xtype:'textfield',
                         fieldLabel: '存款年均',
                         name: 'DEP_YEAR_AVG',
                         style:'text-align:right;',
                         anchor:'90%'
                     }, {
                         xtype:'textfield',
                         fieldLabel: '贷款时点',
                         name: 'LON_BAL',
                         style:'text-align:right;',
                         anchor:'90%'
                     }, {
                         xtype:'textfield',
                         fieldLabel: '贷款年均',
                         name: 'LON_YEAR_AVG',
                         style:'text-align:right;',
                         anchor:'90%'
                     }, {
                         xtype:'textfield',
                         fieldLabel: '产品数量',
                         name: 'RPD_NUM',
                         style:'text-align:right;',
                
                         anchor:'90%'
                     }, {
                         xtype:'textfield',
                         fieldLabel: '利息收入',
                         name: 'LXRCV',
                         style:'text-align:right;',
                
                         anchor:'90%'
                     }, {
                         xtype:'textfield',
                         fieldLabel: '非息收入',
                         name: 'FXRCV',
                         style:'text-align:right;',
                
                         anchor:'90%'
                     },{
                         xtype:'textfield',
                         fieldLabel: '授信总额',
                         name: 'CRESUM',
                         style:'text-align:right;',
               
                         anchor:'90%'
                     },{
                         xtype:'textfield',
                         fieldLabel: '综合贡献度',
                         name: 'MNJCON',
                         anchor:'90%'
                     }
                     ]
                 }
        ]}
        ]}]
	    });
	 var p = new Ext.Panel({
	        //collapsible:true,
		    //autoHeight : true,
	       //width:600,
	        height:510,
	        html:'<iframe id="contentFrame" name="content" height="100%" frameborder="no" width="100%" src=\"customerView.html\" "/> scrolling="auto"> </iframe>'
	    });
	 
	 var panel = new Ext.Panel({
	        //title: '客户管理->客户视图',
	        //collapsible:true,
	        renderTo: 'pan',
	        width:'100%',
	        //height:550,
	    	items : [
	             {
				layout : 'column',
				border : false,
	        items:[{
				columnWidth : .25,
				layout : 'form',
				//labelWidth : 60, // 标签宽度
				//defaultType : 'textfield',
				border : false,
				items : [simple]}
	       , {
					columnWidth : .75,
					layout : 'fit',
					//labelWidth : 60, // 标签宽度
					//defaultType : 'textfield',
					border : false,
					items : [p]
				}] }
	    	         
	    	         ]
	          });
	 
	 
	 simple.getForm().load({
            //waitMsg: '正在加载数据',
    	        //waitTitle: '提示',
    	     
	 restful:true,	
     url:basepath+'/querycustomerviewindex.json?customerId='+window.location.href.split("customerId=")[1],
     method: 'GET'
  /*   success : function(form,action) {
 		//var resultArray = Ext.util.JSON.decode(response.responseText);
			Ext.Msg.alert('提示',action.result);
		}*/
       });
		//Ext.Msg.alert('提示', window.location.href);
				//.split("x=")[1]

});