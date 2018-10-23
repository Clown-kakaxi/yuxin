Ext.onReady(function() {
	var statusStore = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','全辖对公客户'],['2','新开对公客户']]
	});
	var boxstore8 = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','基本户'],['2','一般结算户'],['3','专用存款户'],['4','临时存款户']]
	});
	
	var boxstore9 = new Ext.data.ArrayStore({
		fields:['key','value'],
	    data:[['1','存款余额规模'],['2','存款日均规模']]
	});
	
/**********************************************************/

		var qForm = new Ext.form.FormPanel({
			id : "qfrom",
			labelWidth : 90, // 标签宽度
//			frame : true, //是否渲染表单面板背景色
			labelAlign : 'middle', // 标签对齐方式
			//bodyStyle : 'padding:3 5 0', // 表单元素和表单面板的边距
			buttonAlign : 'center',
			height : 240,
//			height: document.body.clientHeight-46,
			items :[{
	           autoHeight:true,
	           items :[{
	        	   	height:78,
					layout : 'column',
					items : [ {
						layout : 'form',
						columnWidth : .5,
						labelWidth : 120,
						items : [ {
							id:'oddate',
							name:'oddate',
							fieldLabel:'数据日期',
							xtype:'datefield',
							value:' ',
							format:'Y-m-d',
							allowBlank : false,
							labelStyle : 'text-align:right;',
							anchor:'99%'
						} ]
					   }, {
						layout : 'form',
						columnWidth : .5,
						labelWidth : 120,
						items : [ {
							xtype : 'combo',
							store : statusStore,
							fieldLabel : '客户类型',
							hiddenName : 'cust_type',
							triggerAction : 'all',
							valueField : 'key',
							displayField : 'value',
							editable : false,
							mode:'local',
//							allowBlank : false,
							emptyText : '请选择',
							mode : 'local',
							labelStyle : 'text-align:right;',
					//		readOnly : true,
							anchor : '99%'
						}]
					}, {
						layout : 'form',
						columnWidth : .5,
						labelWidth : 120,
						items : [ {
							xtype : 'combo',
							store : boxstore8,
							fieldLabel : '开户性质',
							hiddenName : 'cust_open_type',
							valueField : 'key',
							displayField : 'value',
							triggerAction : 'all',
							mode:'local',
//							allowBlank : false,
							editable : false,
							emptyText : '请选择',
							labelStyle : 'text-align:right;',
						//	readOnly : true,
							anchor : '99%'
						}]
					}, {
						layout : 'form',
						columnWidth : .5,
						labelWidth : 120,
						items : [ {
							xtype : 'combo',
							store : boxstore9,
							fieldLabel : '分析类型',
							hiddenName : 'analyse_type',
							valueField : 'key',
							displayField : 'value',
							triggerAction : 'all',
							mode:'local',
//							allowBlank : false,
							editable : false,
							emptyText : '请选择',
							labelStyle : 'text-align:right;',
						//	readOnly : true,
							anchor : '99%'
						}]
					}, {
						layout : 'form',
						columnWidth : .5,
						labelWidth : 120,
						items : [new Com.yucheng.bcrm.common.OrgField({
							searchType:'SUBTREE',/*指定查询机构范围属性  SUBTREE（子机构树）SUBORGS（直接子机构）PARENT（父机构）PARPATH （所有父、祖机构）ALLORG（所有机构）*/
							fieldLabel : '<font color=red>*</font>机构',
							labelStyle : 'text-align:right;',
							id : 'CUST_ORG', //放大镜组件ID，用于在重置清空时获取句柄
							name : 'CUST_ORG', 
							hiddenName: 'instncode',   //后台获取的参数名称
							allowBlank:false,
							anchor : '99%',
							checkBox:true //复选标志
						})]
					} ]
				
				
	           },{
	        	   columnWidth : .95,
	        		layout : 'form',
	        		items: [{
	        			xtype: 'fieldset',
	        			title: '客户类别',
	        	//		autoHeight:true,
	        			height : 55,
	        			collapsible : true,
	        			defaultType: 'checkbox',
	        			hideLabels: true,
	        			hiddenname : 'cust_type1',
	        			layout : 'column',
	        			items: [
	        				{columnWidth : .25,boxLabel: '企业客户', name: 'checkbox', inputValue: '1'},
	        				{columnWidth : .25,boxLabel: '机关事业单位', name: 'checkbox', inputValue: '2'},
	        				{columnWidth : .25,boxLabel: '同业客户', name: 'checkbox', inputValue: '3'}
	        			]
	        		}]
	           }]
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
							var parameters = qForm.getForm()
									.getValues(false);
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
			var start = Ext.getCmp('oddate').getValue();
			var org_diString = Ext.getCmp("CUST_ORG").hiddenField.getValue();//机构
			
			 if (start == '') {
				Ext.Msg.alert('消息框',	'请先选择查询日期！');
				return;
			}
			
		    var bDate=Ext.util.Format.date(start,'Y-m-d');
		
			var winWidth = screen.width - 10;
			var winHeight = screen.height - 60;
			var winFeatures = "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,";
			winFeatures += "top=0,left=0,height="
					+ winHeight + ",width=" + winWidth;
			var url=basepath+'/reportJsp/showReport.jsp?raq=/report_dep.raq&oddate='+bDate+'&org_id=211111'+'&uid='+__units+'&cust_type=1'+'&cust_type1=26'+'&cust_open_type=1'+'&analyse_type=1';
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
			title: "报表管理->统计报表->统计分析->存款规模报表分析",
			renderTo : 'viewport_center',
			frame : true,
			width:600,
			height:300,
					items: [{   
					    hidden:false,
					    margins: '0 0 0 0',
						items:[fpanel]
				     }] 
				});
	    
}); 