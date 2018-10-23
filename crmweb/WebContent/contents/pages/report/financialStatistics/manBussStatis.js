/**
 * 商业银行个人理财产品统计表
 * @author zhangxd
 * @since 2012-12-24
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
    		        	        	 fieldLabel : '填报日期',
    		        	        	 id:'fillTime',
    		        	        	 name:'fillTime',
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
    	debugger;
    	var start = qForm.getForm().findField('fillTime').getValue();
    	var bDate=Ext.util.Format.date(start,'Y-m-d');
    	var winWidth = screen.width - 10;
    	var winHeight = screen.height - 60;
    	var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
    	winFeatures += "top=0,left=0,height="
    		+ winHeight + ",width=" + winWidth;
    	if(start!=''){
    		Ext.getCmp("fillTime").setValue('');
			var url=basepath+'/reportJsp/showReport.jsp?raq=/report_5_G06.raq&fillTime='+bDate;
    	}
    	else{
    		Ext.Msg.alert("提醒","请填写必选项");
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
    	title:'理财报表统计->附件银行个人理财产品->G06理财业务统计表',
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