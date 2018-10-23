/**
 * 金融机构理财产品当季发售情况明细表
 * @author zhangsxin
 * @since 2012-12-18
 */
Ext.onReady(function() {

    //年类型
    var yearStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','2009'],['2','2010'],['3','2011'],['4','2012'],['5','2013']]
	});
    //季度类型 
    var seasonStore = new Ext.data.ArrayStore({
    	fields:['key','value'],
    	data:[['1','第一季度'],['2','第二季度'],['3','第三季度'],['4','第四季度']]
    });
	
/**********************************************************/
    var qForm = new Ext.form.FormPanel({
    	id : "qfrom",
    	labelWidth : 100, // 标签宽度
    	labelAlign : 'middle', // 标签对齐方式
    	buttonAlign : 'center',
    	height:150,
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
    		        	        	 xtype : 'combo',
    		        	        	 store : yearStore,
    		        	        	 fieldLabel : '年',
    		        	        	 id:'qyear',
    		        	        	 name:'qyear',
    		        	        	 hiddenName : 'deposit_average',
    		        	        	 valueField : 'key',
    		        	        	 displayField : 'value',
    		        	        	 triggerAction : 'all',
    		        	        	 mode:'local',
    		        	        	 editable : false,
    		        	        	 emptyText : '请选择',
    		        	        	 labelStyle : 'text-align:right;',
    		        	        	 anchor : '90%'
    		        	         },
    		        	         {
    		        	        	 xtype : 'combo',
    		        	        	 store : seasonStore,
    		        	        	 fieldLabel : '季度',
    		        	        	 id:'seasonType',
    		        	        	 name:'seasonType',
    		        	        	 hiddenName : 'deposit_average',
    		        	        	 valueField : 'key',
    		        	        	 displayField : 'value',
    		        	        	 triggerAction : 'all',
    		        	        	 mode:'local',
    		        	        	 editable : false,
    		        	        	 emptyText : '请选择',
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
    	var dateyear=Ext.getCmp("qyear").getValue();
    	var dateseason = qForm.getForm().findField('seasonType').getValue();
        if(dateyear==''&&dateseason==''){
	    	Ext.Msg.alert("提醒","请填写必填项");
	    	return;
	    }
    	
    	var winWidth = screen.width - 10;
    	var winHeight = screen.height - 60;
    	var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
    	winFeatures += "top=0,left=0,height="
    		+ winHeight + ",width=" + winWidth;
    	if(dateyear=='1')
		{   
			Ext.getCmp("qyear").setValue('');
			Ext.getCmp("seasonType").setValue('');
			var url=basepath+'/reportJsp/showReport.jsp?raq=/report_2.1.raq&qdate='+'2009'+'&seasonType='+dateseason;
		}
		else if(dateyear=='2')
		{   
			Ext.getCmp("seasonType").setValue('');
			Ext.getCmp("qyear").setValue('');
			var url=basepath+'/reportJsp/showReport.jsp?raq=/report_2.1.raq&qdate='+'2010'+'&seasonType='+dateseason;
		}
		else if(dateyear=='3')
		{   
			Ext.getCmp("seasonType").setValue('');
			Ext.getCmp("qyear").setValue('');
			var url=basepath+'/reportJsp/showReport.jsp?raq=/report_2.1.raq&qdate='+'2011'+'&seasonType='+dateseason;
		}
		else if(dateyear=='4')
		{   
			Ext.getCmp("seasonType").setValue('');
			Ext.getCmp("qyear").setValue('');
			var url=basepath+'/reportJsp/showReport.jsp?raq=/report_2.1.raq&qdate='+'2012'+'&seasonType='+dateseason;
		}
		else if(dateyear=='5')
		{   
			Ext.getCmp("seasonType").setValue('');
			Ext.getCmp("qyear").setValue('');
			var url=basepath+'/reportJsp/showReport.jsp?raq=/report_2.1.raq&qdate='+'2013'+'&seasonType='+dateseason;
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
    	title:'理财报表统计->附件银行个人理财产品->金融机构理财产品当季发售情况明细表',
    	renderTo : 'viewport_center',
    	width:600,
    	height:200,
    	frame : true,
    	items: [{   
    		hidden:false,
    		margins: '0 0 0 0',
    		items:[fpanel]
    	}] 
    });
	
}); 