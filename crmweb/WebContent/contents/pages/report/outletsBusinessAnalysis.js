Ext.onReady(function() {
    var dataType = '1';

	//业务类型
    var analysisTypeStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','客户规模'],['2','行业门类'],['3','所有制'],['4','组织类别'],['5','客户类别'],['6','开户性质']]
	}); 

/**********************************************************/

		var qForm = new Ext.form.FormPanel({
			id : "qfrom",
			labelWidth : 100, // 标签宽度
//			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height:240,
//			height: document.body.clientHeight-40,
			items :[{
				layout : 'column',
				border : false,
	           items :[  
	                     
	                     {
						layout : 'form',
						columnWidth : .9,
						labelWidth : 120,
						border : false,
							items : [
							    {
								xtype : 'datefield',
								fieldLabel : '数据日期',
								name:'StartDate',
								allowBlank : false,
								format:"Y-m-d", 
								allowBlank:false,
								value:'',
								labelStyle : 'text-align:right;',
								anchor : '99%'
							}]
	                         },
	                     {
	 						layout : 'form',
	 						columnWidth : .9,
	 						labelWidth : 120,
	 						border : false,
	 							items : [new Com.yucheng.bcrm.common.OrgField({
									searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
									fieldLabel : '<font color=red>*</font>营业网点',
									labelStyle : 'text-align:right;',
									id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
									name : 'CUST_ORG', 
									hiddenName: 'instncode',   //后台获取的参数名称
									allowBlank:false,
									anchor : '99%',
									checkBox:true //复选标志
								})]
	 	                         },
	                     {
	 						layout : 'form',
	 						columnWidth : .9,
	 						labelWidth : 120,
	 						border : false,
 							items : [
 							         
							  {
								xtype : 'radiogroup',
								labelStyle : 'text-align:right;',
								fieldLabel : '数据类型',
								id : 'dataType',
//								columns : 2,
								items : [{boxLabel : '汇总数据', name :'dataType', inputValue : '1',checked :true},
									      {boxLabel : '明细数据', name : 'dataType', inputValue : '2'}
								],
								//以下是监听事件
								
								listeners : {
								           'change' : function(radiofield,oldvalue){//这事件是当radiogroup的值发生改变时进入
								            dataType = radiofield.getValue().inputValue;
								          
								           }
								}
							
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
//							report4ShowWindow.show();
							var parameters = qForm.getForm().getValues(false);
						}
					}, {
						text : '重置',
						handler : function() {
							qForm.getForm().reset();
							Ext.getCmp("CUST_ORG").setValue('');
						}
					} ] 	     
		});
		
		function select(){
			var start = qForm.getForm().findField('StartDate').getValue();
			var org_diString = Ext.getCmp("CUST_ORG").hiddenField.getValue();//机构ID
			//       dataType  为数据类型的值
			// alert(dataType+'----------');//输出选中的值
			
		    var bDate=Ext.util.Format.date(start,'Y-m-d');
		    
		    var winWidth = screen.width - 10;
				var winHeight = screen.height - 60;
				var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
				winFeatures += "top=0,left=0,height="
						+ winHeight + ",width=" + winWidth;
				if(dataType==1){
					var url=basepath+'/reportJsp/showReport.jsp?raq=/A4.raq&oddate='+bDate+'&uid='+__units+'&org_id='+org_diString;	
				}
				else
				{
					var url=basepath+'/reportJsp/showReport.jsp?raq=/A4_2.raq&oddate='+bDate+'&uid='+__units+'&org_id='+org_diString;
				}
				
				var winOpen = window.open(url,'chat' + new Date().getTime(),winFeatures);
			}
		var fpanel = new Ext.Panel({
			id : "fpanel",
//			labelWidth : 90, // 标签宽度
			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			buttonAlign : 'center',
//			height: document.body.clientHeight,
			items :[qForm]
			        });
	// 布局模型
	var viewport = new Ext.Panel({
		title:'统计报表->客户结构分析->营业网点业务情况分析',
		renderTo : 'viewport_center',
		frame : true,
		width:400,
		height:300,
				items: [{   
				    hidden:false,
				    margins: '0 0 0 0',
					items:[fpanel]
			     }] 
			});
	
}); 