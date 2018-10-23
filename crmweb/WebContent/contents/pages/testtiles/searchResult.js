
function cWindow(){
	//华一银行客户视图
	parent.Wlj.ViewMgr.openViewWindow(0,'706','对公客户7');
	
	//东亚版客户视图
	openCustomerView('100');
	
//	debugger;
//	parent._APP.openWindow({
//		resId : 'jiarukehuqun',
//		action : basepath + '/contents/pages/testtiles/custView.jsp',
//		name : '客户视图',
//		id : 'jiarukehuqun'
//	});
}
Ext.onReady(function() {
	

	var bodysize = Ext.getBody().getViewSize();
	
	var tileWidth = parseInt((bodysize.width - 16)/136);
	
	function showMark(tile){
		var vs = tile.el.getViewSize();
		tile.opGuard.dom.style.display = 'block';
		tile.opGuard.animate(
			{	
				height : {
					to : vs.height - 20,
					from : 0
				},
				top : {
					to : 20,
					from : vs.height
				}
			},
			.35,
			null,
			'easeOut',
			'run'
		);
	}
	function hideMark(tile){
		var vs = tile.el.getViewSize();
		tile.opGuard.animate({	
				height : {
					from : vs.height - 20,
					to : 0
				},
				top : {
					from : 20,
					to : vs.height
				}
			},
			.35,
			null,
			'easeOut',
			'run'
		);
		setTimeout(function(){
			tile.opGuard.dom.style.display = 'none';
		},150);
	
	}
	function loadData(){
		panel.removeAll();
		panel.doLayout();
		Ext.Ajax.request({
			url:basepath+'/customerBaseInformation.json',
			method:'get',
			params : {
                start : 0,
                limit : 10/*,
                userId:Ext.encode(userId.aId)*/
             },
			success:function(response){
				var data = Ext.decode(response.responseText).json.data;
				for(var i=1;i<10;i++){
					var d = data[i];
					var name = d.CUST_NAME;
					var sex = '男';
					var cLevel = '私人';
					var sLevel = 4;
					var compny = '上海东方集团';
					var position = 'CFO';
					var pName = '高级经济师';
					var birth = '1967年3月12日 ';
					var age = '54岁';
					var industry = '运输行业';
					var org = '上海世纪大道支行';
					var man = '刘桦';
					var date = '开户日期：1998年2月28日';
					var AUM = 'AUM: 6,483,438.99';
					var cur = '储蓄存款：983,329.09';
					var cur2 = '定期存款：2,000,000.00';
					var fin = '理财存款： 4,000,000.00';
					
					var cls = i%2==1?'search_in':'search_or';
					var inforClor =  i%2==1? '#EEE8AA':'#FF8C00';
					
					
					var c1 = new Wlj.widgets.search.tile.Tile({
						cls : cls,
						ownerW : tileWidth*2,
						removeable : false,
						baseSize : 68,
						baseMargin : 1,
						pos_size : {
							TX : 0 ,
							TY : 0,
							TW : .5,
							TH : 1
						},
						html: '<div style="width:100%;height:100%;text-align:center;"><br><a href="#">'+i+'</a>'+'<br><br>'+'<input type=checkbox /></div>'
					});
					
					var c2 = new Wlj.widgets.search.tile.Tile({
						cls : cls,
						
						ownerW :  tileWidth*2,
						removeable : false,
						baseSize : 68,
						baseMargin : 1,
						pos_size : {
							TX : .5 ,
							TY : 0,
							TW : 2,
							TH : 1
						},
						html : '<div style="width:100%;height:100%;margin-top:5px;"><a style="" href="javascript:cWindow()">'+name+'</a>'+
						'<br><br>&nbsp;&nbsp;&nbsp;&nbsp;'+sex+'&nbsp;&nbsp;&nbsp;'+cLevel + '<br><br>'+ '&nbsp;&nbsp;&nbsp;<font color=red>'+ sLevel + '星级客户</font>',
						listeners : {
							afterrender : function(ccc){
							
								var vs = ccc.el.getViewSize();
								ccc.opGuard = ccc.el.createChild({
									tag:'div',
									style:'position:absolute;top:'+vs.height+'px;background-color:#9ACD32;width:100%;display:none;'
								});
								ccc.addGroup = ccc.opGuard.createChild({
									tag:'div',
									html:'<br><a href="#">加入客户群</a>&nbsp;&nbsp;<a href="#">设置关注</a>'+
									'<br><br><a href="#" >客户预约</a>&nbsp;&nbsp;<a href="#">产品推荐</a>'
								});
								ccc.addGroup.on('click',function(event,dom){
								});
								ccc.el.on('mouseenter',function(event,dom){
									showMark(ccc);
								});
								ccc.el.on('mouseleave',function(event,dom){
									hideMark(ccc);
								})
							}
						}
					});
					
					var c3 = new Wlj.widgets.search.tile.Tile({
						
						cls : cls,
						
						ownerW :  tileWidth*2,
						removeable : false,
						baseSize : 68,
						baseMargin : 1,
						pos_size : {
							TX : 2.5 ,
							TY : 0,
							TW : 3,
							TH : 1
						},
						html: '<div style="width:100%;hieght:100%;margin-top:5px;margin-left:5px;">'+ compny + '&nbsp;&nbsp;' + position +  '&nbsp;&nbsp;' + pName + '<br><br>' + birth + '&nbsp;&nbsp;' + age + '&nbsp;&nbsp;' + industry+'</div>'
					});
					
					
					var c4 = new Wlj.widgets.search.tile.Tile({
						cls : cls,
						
						ownerW :  tileWidth*2,
						
						removeable : false,
						baseSize : 68,
						baseMargin : 1,
						pos_size : {
							TX : 5.5 ,
							TY : 0,
							TW : 3,
							TH : 1
						},
						html: '<div style="width:100%;hieght:100%;margin-top:5px;margin-left:5px;">'+org +  '&nbsp;'+ man + '<br><br>' + date+'</div>',
						listeners : {
							afterrender : function(ccc){
								var vs = ccc.el.getViewSize();
								
								var inner = '1、预约： 2013年9月26日，艺术品鉴赏会<br>'+
								'2、生日： 2013年10月27日<br>'+
								'电话： 138232***89';

								
								ccc.opGuard = ccc.el.createChild({
									tag:'div',
									html:inner,
									style:'position:absolute;top:'+vs.height+'px;background-color:#EEE8AA;border:1px solid #000;width:100%;display:none;'
								});
								ccc.el.on('mouseenter',function(event,dom){
									showMark(ccc);
								});
								ccc.el.on('mouseleave',function(event,dom){
									hideMark(ccc);
								})
							}
						}
					});
					
					var c5 = new Wlj.widgets.search.tile.Tile({
						cls : cls,
						
						ownerW :  tileWidth*2,
						
						removeable : false,
						baseSize : 68,
						baseMargin : 1,
						pos_size : {
							TX : 8.5 ,
							TY : 0,
							TW : 5,
							TH : 1
						},
						html:  '<div style="width:100%;hieght:100%;margin-top:5px;margin-left:5px;">'+AUM + '&nbsp;' + cur + '<BR><BR>' + cur2 + '&nbsp;' + fin+'</div>'
					});
					
					var t1 = new Wlj.widgets.search.tile.Tile({
						ownerW : 10,
						ownerH : 10,
						baseSize : 70,
						baseWidth : bodysize.width,
						position : 'absolute',
						removeable : false,
						pos_size : {
							TX : 1,
							TY : i-1,
							TW : 1,
							TH : 1
						},
						items : [c1,c2,c3,c4,c5],
						listeners : {
							afterrender : function( ttt ){
								setTimeout(function(){
									ttt.moveToPoint({
										x : 0,
										y : ttt.pos_size.TY
									});
								},ttt.ownerCt.items.indexOf(ttt)*300);
							}
						}
					});
					panel.add(t1);
				}
				panel.setHeight(128*10);
				panel.doLayout();
			}
		});
	}
	var panel = new Ext.Panel({
		frame : true,
		height : bodysize.height -128,
		items : []
	});
	var fexed = false;
	
	var searchComponent = new Wlj.widgets.search.search.SearchComponent({
		style : {
			marginTop :0,
			marginLeft : 0,
			top:0,
			left : 0
		},
		height: 128,
		width : 494,
		doSearch:function(){
			loadData();
		},
		comfex : true,
		comfexFn : function(){
			if(fexed){
				fexed = false;
				panelgaoji.body.applyStyles({
					height : 0,
					width : 0
				});
				panelgaoji.el.animate(
						{	
							height : {
								to : 0,
								from : 200
							}
						},
						1,
						null,
						'easeOut',
						'run'
					);
				panelgaoji.removeAll(true);
			}else {
				fexed = true;
				panelgaoji.el.animate(
						{	
							height : {
								to : 200,
								from : 0
							}
						},
						1,
						null,
						'easeOut',
						'run'
				);
				
					var addcon = new Ext.form.FormPanel({
						height :200,
						frame : true,
						width : '100%',
						layout : 'column',
						items : [{
							columnWidth : .2,
							layout : 'form',
							labelWidth : 70, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
								fieldLabel : '客户号',
								name : 'CUST_ID',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							},{
								fieldLabel : '职业',
								name : 'CUST_ID',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%'
							}]
						},{
							columnWidth : .2,
							layout : 'form',
							labelWidth: 90, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
								fieldLabel : '客户名称',
								name : 'CUST_ZH_NAME',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%',
								enableKeyEvents:true
							},{
								fieldLabel : '所属公司',
								name : 'CUST_ZH_NAME',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%',
								enableKeyEvents:true
							}]
						},{
							columnWidth : .2,
							layout : 'form',
							labelWidth: 90, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [{
								fieldLabel : '客户名称',
								name : 'CUST_ZH_NAME',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%',
								enableKeyEvents:true
							},{
								fieldLabel : '性别',
								name : 'CUST_ZH_NAME',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%',
								enableKeyEvents:true
							}]
						},{
							columnWidth : .4,
							layout : 'form',
							labelWidth: 90, // 标签宽度
							defaultType : 'textfield',
							border : false,
							items : [new Ext.Panel({
								height : 153,
								items : [
								    new Wlj.widgets.search.tile.Tile({
						        	 cls : 'search_level',
						        	 ownerW :  10,
						        	 defaultDDGroup : 'addcontarget',
						        	 removeable : false,
						        	 labelText : 'AUM',
						        	 baseSize : 68,
						        	 baseHeight : 34,
						        	 baseMargin : 1,
						        	 pos_size : {
						        	 	TX : 0,
						        	 	TY : 0,
						        	 	TW : 1,
						        	 	TH : 1
						         	},
						         	html:  '<div style="width:100%;hieght:100%;margin-top:5px;margin-left:5px;">AUM值</div>'
						         }), 
						         new Wlj.widgets.search.tile.Tile({
						        	 cls : 'search_level',
						        	 ownerW :  10,
						        	 defaultDDGroup : 'addcontarget',
						        	 removeable : false,
						        	 labelText : '存款余额',
						        	 baseSize : 68,
						        	 baseHeight : 34,
						        	 baseMargin : 1,
						        	 pos_size : {
						        	 	TX : 1,
						        	 	TY : 0,
						        	 	TW : 1,
						        	 	TH : 1
						         	},
						         	html:  '<div style="width:100%;hieght:100%;margin-top:5px;margin-left:5px;">存款余额</div>'
						         }),new Wlj.widgets.search.tile.Tile({
						        	 cls : 'search_level',
						        	 ownerW :  10,
						        	 defaultDDGroup : 'addcontarget',
						        	 removeable : false,
						        	 labelText : '贷款余额',
						        	 baseSize : 68,
						        	 baseHeight : 34,
						        	 baseMargin : 1,
						        	 pos_size : {
						        	 	TX : 2,
						        	 	TY : 0,
						        	 	TW : 1,
						        	 	TH : 1
						         	},
						         	html:  '<div style="width:100%;hieght:100%;margin-top:5px;margin-left:5px;">贷款余额</div>'
						         })]
							})]
						}],
						buttonAlign : 'center',
						buttons : [{
							text : '查询',
							handler : function(){
								loadData();
							}
						},{
							text : '重置'
						}]
					});
				
					
				setTimeout(function(){
					var pelvs = panelgaoji.el.getViewSize();
					panelgaoji.body.applyStyles({
						width : pelvs.width,
						height : pelvs.height
					});
					panelgaoji.add(addcon);
					panelgaoji.doLayout();
					var dt = new Ext.dd.DropTarget(addcon.el.dom, {
						ddGroup : 'addcontarget',
						notifyEnter : function(ddSource, e, data) {
						},
						notifyDrop  : function(ddSource, e, data){//拖拽到正确的区域时给要编辑的节点的模块功能赋值
							addcon.items.items[0].add({
								fieldLabel : ddSource.tile.labelText,
								name : 'CUST_ZH_NAME',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%',
								enableKeyEvents:true
							});
							addcon.items.items[1].add({
								fieldLabel : '至',
								name : 'CUST_ZH_NAME',
								xtype : 'textfield', // 设置为数字输入框类型
								labelStyle: 'text-align:right;',
								anchor : '90%',
								enableKeyEvents:true
							});
							addcon.doLayout();
							ddSource.tile.removeThis();
						}
						
					})
				},1000);
			}
		},
		listeners : {
			afterrender : function(){
				this.appObject = parent._APP;
			}
		}
	});
	
	var panelgaoji = new Ext.Panel({
		height: 0,
		layout:'fit'
	});
	
	var panel1 = new Ext.Panel({
		height : 128,
		frame : true,
		items : [searchComponent]
	});
	var view = new Ext.Viewport({
		layout:'form',
		style : {
			overflowY:'scroll'
		},
		items : [panel1,panelgaoji,panel]
	});
	searchComponent.setValue(conditionP);
	loadData();
});