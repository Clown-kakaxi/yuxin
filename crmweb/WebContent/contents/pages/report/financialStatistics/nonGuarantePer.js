/**
 * 商业银行非保本个人理财产品调查表
 * @author zhangsxin
 * @since 2012-12-18
 */
Ext.onReady(function() {

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
    		        	        	 fieldLabel : '数据日期',
    		        	        	 id:'qDate',
    		        	        	 name:'qDate',
    		        	        	 format:"Y-m-d", 
    		        	        	 labelStyle : 'text-align:right;',
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
    	var start = qForm.getForm().findField('qDate').getValue();
    	var bDate=Ext.util.Format.date(start,'Y-m-d');
    	if(start==''){
    		Ext.Msg.alert("提醒","请填写必填项");
    		return;
    	}
    	var winWidth = screen.width - 10;
    	var winHeight = screen.height - 60;
    	var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
    	winFeatures += "top=0,left=0,height="
    		+ winHeight + ",width=" + winWidth;
    	if(start!='')
    	{
    		qForm.getForm().findField('qDate').setValue('');
    		var url=basepath+'/reportJsp/showReport.jsp?raq=/report_3.raq&qdate='+bDate;
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
    	title:'理财报表统计->附件银行个人理财产品->商业银行非保本个人理财产品调查表',
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