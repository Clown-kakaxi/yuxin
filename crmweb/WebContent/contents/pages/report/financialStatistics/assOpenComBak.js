/**
 * 资产池类封闭式商业银行理财产品运行数据信息统计表
 * @author zhangxd
 * @since 2012-12-21
 */
Ext.onReady(function() {
	
	//发行币种
	 var colCurrStore = new Ext.data.ArrayStore({
			fields:['key','value'],
		    data:[['1','RMB'],['2','外币']]
	});

/**********************************************************/
    var qForm = new Ext.form.FormPanel({
    	id : "qfrom",
    	labelWidth : 100, // 标签宽度
    	labelAlign : 'middle', // 标签对齐方式
    	buttonAlign : 'center',
    	height:100,
    	items :[{
    		layout : 'column',
    		border : false,
    		items :[  
    		        {
    		        	layout : 'form',
    		        	columnWidth : 1,
    		        	border : false,
    		        	items : [
    		        	         {
    		        	        	 xtype : 'datefield',
    		        	        	 fieldLabel : '产品起始日期',
    		        	        	 id:'startTime',
    		        	        	 name:'startTime',
    		        	        	 format:"Y-m-d", 
    		        	        	 labelStyle : 'text-align:right;',
    		        	        	 anchor : '90%' 
    		        	         },
    		        	         {
    		        	        	 xtype : 'combo',
    		        	        	 fieldLabel : '募集币种',
    		        	        	 id:'colCurr',
    		        	        	 name:'colCurr',
    		        	        	 store:colCurrStore,
    		        	        	 labelStyle : 'text-align:right;',
    		        	        	 valueField : 'key',
    		        	        	 displayField : 'value',
    		        	        	 triggerAction : 'all',
    		        	        	 mode:'local',
    		        	        	 editable : false,
    		        	        	 emptyText : '请选择',
    		        	        	 anchor : '90%' 
    		        	         }
    		        	         ]	
    		        }
    		        ]
    	}],
    	buttonAlign : 'center',
    	buttons : [
    	           {
    	        	   text : '查询',
    	        	   handler : function() {
    	        	   if(!qForm.getForm().isValid()){
    	        		   Ext.Msg.alert("提醒","请填写必填项");
    	        		   return false;
    	        	   }
    	        	   select();
    	        	   var parameters = qForm.getForm().getValues(false);
    	           }
    	           }, {
    	        	   text : '重置',
    	        	   handler : function() {
    	        	   qForm.getForm().reset();
    	           }
    	           } ] 	     
    });
    function select(){
    	debugger;
    	var start = qForm.getForm().findField('startTime').getValue();
    	var bDate=Ext.util.Format.date(start,'Y-m-d');
    	var colCurr = Ext.getCmp("colCurr").getValue();
    	var winWidth = screen.width - 10;
    	var winHeight = screen.height - 60;
    	var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
    	winFeatures += "top=0,left=0,height="
    		+ winHeight + ",width=" + winWidth;
    	if(start==''&& colCurr!=''){
    		if(colCurr=='1')
    		{   
    			Ext.getCmp("colCurr").setValue('');
    			var url=basepath+'/reportJsp/showReport.jsp?raq=/report_7.2.raq&colCurr='+'RMB';
    		}
        	else if(colCurr=='2'){
        		Ext.getCmp("colCurr").setValue('');
        		var url=basepath+'/reportJsp/showReport.jsp?raq=/report_7.2.raq&colCurr='+'外币';
    		}
    	} 
    	else if(colCurr==''&& start!=''){
    		Ext.getCmp("startTime").setValue('');
			var url=basepath+'/reportJsp/showReport.jsp?raq=/report_7.2.raq&startTime='+bDate;
    	}
    	else{
    		Ext.Msg.alert("提醒","请选择其中一项");
    		return;
    	}
    	var winOpen = window.open(url,'chat' + new Date().getTime(),winFeatures);
    }
    var fpanel = new Ext.Panel({
    	id : "fpanel",
    	frame : true, //是否渲染表单面板背景色
    	labelAlign : 'middle', // 标签对齐方式
    	buttonAlign : 'center',
    	items :[qForm]
			        });
    // 布局模型
    var viewport = new Ext.Panel({
    	title:'理财报表统计->附件银行个人理财产品->资产池类开放式商业银行理财产品运行数据信息统计表',
    	renderTo : 'viewport_center',
    	width:600,
    	height:150,
    	frame : true,
    	items: [{   
    		hidden:false,
    		margins: '0 0 0 0',
    		items:[fpanel]
    	}] 
    });
	
}); 