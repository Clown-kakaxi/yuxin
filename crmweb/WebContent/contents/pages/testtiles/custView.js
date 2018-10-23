Ext.onReady(function() {
	

	var bodysize = Ext.getBody().getViewSize();
	
	var tileWidth = parseInt((bodysize.width - 16)/136);
	
	
	var panel = new Ext.Panel({
		frame : true,
		height : bodysize.height -128,
		items : []
	});
	
	var t1 = new Wlj.widgets.search.tile.ResizeTile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 0,
						TY : 0,
						TW : 1.5,
						TH : 1
					},
					html: '<a href="#"><img src="chart/pic.jpg" ></img></a>',
					listeners : {
						afterrender : function(){
						this.el.on('click',function(){
							t4.remove(t4.items.last());
							t4.doLayout();
						});
						}
					}
				});
				
	var t2 = new Wlj.widgets.search.tile.NegativeTile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					interval : 5000,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 1.5,
						TY : 0,
						TW : 1.5,
						TH : 1
					},
					items : [{
						html : '<div style="width:100%;height:100%;background-color:rgb(27, 150, 209)" class="tile_chart_tit"><p class="tit" ><b>身份证号:</b>23232***323<br/><b> 生日：</b>1969年4月2日<br/><b> 电话：</b>13830****323<br/><b> 评级：</b>私人银行客户<br/></div>'
					},{
						id :'t2hw',
						html :'hello world!'
					}]
				});			
				
	var t3 = new Wlj.widgets.search.tile.EditTile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 3,
						TY : 0,
						TW : 1.5,
						TH : 1
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(27, 150, 209)" class="tile_chart_tit"><p class="tit" ><b>公司名称：</b>上海远洋集团<br/><b>职务：</b>CFO <br/><b>公司地址：</b>上海浦东区世纪大道8号<br/><br/></div>'
				});	
	var t4 = new Wlj.widgets.search.tile.NegativeTile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 4.5,
						TY : 0,
						TW : 2,
						TH : 1
					},
					items : [{
						html: '<div style="width:100%;height:100%;background-color:rgb(27, 150, 209)" class="tile_chart_tit"><p class="tit" ><b>拜访：</b>2013年7月20日<br/><b>定期理财存款：</b>30天<br/><b>大额变动：</b>2013-9-20<br/><b>支出：</b>23，323.00元<br/></div>'
					},{
						id :'t4hw1',
						html : 'hello world1'
					},{
						id :'t4hw2',
						html : 'hello world2'
					},{
						id :'t4hw3',
						html : 'hello world3'
					}]
				});	
var t5 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 3,
						TY : 1,
						TW : 1.75,
						TH : 1
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(111, 150, 209)" class="tile_chart_tit"><p class="tit" ><b>家庭住址：</b>上海徐汇区XXX大道2号<br/><b>家庭电话：</b>021-92323982<br/><b>家庭年度总收入：</b>400万以上<br/></div>'
				});
var t6 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 4.75,
						TY : 1,
						TW : 1.75,
						TH : 1
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(111, 150, 209)" class="tile_chart_tit"><p class="tit" ><b>家庭成员：</b>3人               <b>是否户主：</b> 是<br/><b>家庭评级：</b>  私人银行客户<br/><b>家庭总资产：</b>233，320，430.00<br/><b>家庭总负责：</b>839，32.00<br/></div>'
				});	
				
				
var t7 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 0,
						TY : 1,
						TW : 3,
						TH : 2
					},
					html: '<a href="#"><img src="chart/aa.jpg" ></img></a>'
				});	
var t8 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 3,
						TY : 3,
						TW : 1.75,
						TH : 1
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(250, 150, 209)" class="tile_chart_tit"><p class="tit" ><b><font size="4">存款</font></b><br/><b><font>账户数：</b>5个<br/><b>活期存款时点：</b>230，389.98<br/><b>定期存款时点：</b>1，369，387.92<br/><br/></font></div>'
				});	
var t9 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 4.75,
						TY : 3,
						TW : 1.75,
						TH : 1
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(250, 150, 209)" class="tile_chart_tit"><p class="tit" ><b><font size="4" >理财产品</font></b><br/><b><font  >当前持有产品数：</b>5个   <b>最多时：</b>20个<br/><b>总份数：</b>20份，<b>规模：</b>1000万 ， <br/><b>最长期限：</b>12个月，<b>最短期限：</b>30天<br/></font></div>'
				});	
				
	var r1 = new Wlj.widgets.search.tile.Tile({
					ownerW : tileWidth,
					ownerH : 10,
					position : 'absolute',
					removeable : false,
					pos_size : {
						TX : 0,
						TY : 0,
						TW : tileWidth,
						TH : 1
					},
					items : [t1,t2,t3,t4,t5,t6,t7,t8,t9]
				});			
				

var t11 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 3,
						TY : 0,
						TW : 1.75,
						TH : 1
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(250, 150, 209)" class="tile_chart_tit"><p class="tit" ><b><font size="4" >贷款</font></b><br/><b><font >账户数：</b>2个<br/><b>个人消费贷款时点：</b>2，389.98<br/><b>个人房产贷款时点：</b>2，590，922.00<br/></font></div>'
				});	
var t12 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 4.75,
						TY : 0,
						TW : 1.75,
						TH : 1
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(250, 150, 209)" class="tile_chart_tit"><p class="tit" ><b><font size="4"  >贵金属及其他</font></b><br/><b><font >账户数：</b>2个<br/><b>黄金业务：</b>245，769.00<br/><b>基金业务：</b>4，874，232.00<br/><br/></font></div>'
				});					
				
				var t13 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 0,
						TY : 0,
						TW : 3,
						TH : 3
					},
					html: '<div style="background-color:#cce1f6" ><p class="tit" ><b><font size="4" color="black">&nbsp;&nbsp;资产</font></b></div><font color="black">'+
							'<table width="100%" height="100%" align="center" bgcolor=#cce1f6 >'+
							'<tr style="background:#cceef6"><th width="40%" ><b>类别</b></th><th  width="40%"><b>项目</b></th><th width="20%"><b>价值</b></th></tr>'+
							'<tr><td width="20%">房地产</td><td width="40%">住宅</td><td width="40%" style="text-align:right;">5,600,000</td></tr>'+
							'<tr style="background:#cceef6"><td width="20%">房地产</td><td width="40%">商铺</td><td width="40%" style="text-align:right;">12,550,000</td></tr>'+
							'<tr><td width="20%">投资</td><td width="40%">退休帐户</td><td width="40%" style="text-align:right;">980,000</td></tr>'+
							'<tr style="background:#cceef6"><td width="20%">投资</td><td width="40%">股票</td><td width="40%" style="text-align:right;">530,000</td></tr>'+
							'<tr><td width="20%">投资</td><td width="40%">债券</td><td width="40%" style="text-align:right;">250,000</td></tr>'+
							'<tr style="background:#cceef6"><td width="20%">投资</td><td width="40%">共同基金</td><td width="40%" style="text-align:right;">330,000</td></tr>'+
							'<tr><td width="20%">投资</td><td width="40%">信托基金</td><td width="40%" style="text-align:right;">250,000</td></tr>'+
							'<tr style="background:#cceef6"><td width="20%">现金</td><td width="40%">活期存款帐户</td><td width="40%" style="text-align:right;">145,000</td></tr>'+
							'<tr><td width="20%">现金</td><td width="40%">其他</td><td width="40%" style="text-align:right;">20,000</td></tr>'+
							'<tr style="background:#cceef6"><td width="20%">投资</td><td width="40%">债券</td><td width="40%" style="text-align:right;">250,000</td></tr>'+
							'<tr><td width="20%">个人财产</td><td width="40%">家具陈设</td><td width="40%" style="text-align:right;">1,000,000</td></tr>'+
							'<tr style="background:#cceef6"><td width="20%">个人财产</td><td width="40%">汽车</td><td width="40%" style="text-align:right;">550,000</td></tr>'+
							'</table></font>'
				});	
				var t14 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 3,
						TY : 1,
						TW : 3.5,
						TH : 2
					},
					html: '<div style="background-color:#ebd3f2"><b><font size="4" color="black">&nbsp;&nbsp;负债</font></b></div>'+
							'<font  color="black"><table width="100%" height="100%" bgcolor=#ebd3f2>'+
							'<tr style="background:#ebddf2"><th width="20%"><b>类别</b></th><th width="30%"style="text-align:center;"><b>价值</b></th><th width="50%"><b></b></th></tr>'+
							'<tr><td width="20%">抵押贷款</td><td width="30%" style="text-align:right;">4,000,000</td><td width="50%"><b></b></td></tr>'+
							'<tr style="background:#ebddf2"> <td width="30%">住宅权益贷款</td><td width="50%" style="text-align:right;">500,000</td><td></td></tr>'+
							'<tr><td width="30%">汽车贷款</td><td width="50%" style="text-align:right;">300,000</td></tr>'+
							'<tr style="background:#ebddf2"><td width="30%">个人贷款</td><td width="50%" style="text-align:right;">0</td><td></td></tr>'+
							'<tr><td width="30%">学生贷款</td><td width="50%" style="text-align:right;">100,000</td></tr>'+
							'<tr style="background:#ebddf2"><td width="30%">投资贷款</td><td width="50%" style="text-align:right;">200,000</td><td></td></tr>'+
							'<tr><td width="30%">其他分期贷款</td><td width="50%" style="text-align:right;">100,000</td></tr>'+
							'</table></font>'
				});	

				var r2 = new Wlj.widgets.search.tile.Tile({
					ownerW : tileWidth,
					ownerH : 10,
					position : 'absolute',
					removeable : false,
					pos_size : {
						TX : 0,
						TY : 3,
						TW : tileWidth,
						TH : 1
					},
					items : [t13,t11,t12,t14]
				});	



				panel.add(r1);
				panel.add(r2);
				
			panel.setHeight(128*10);
			panel.doLayout();
	
	
	
	var panel1 = new Ext.Panel({
		height : 128,
		frame : true,
		items : []
	});
	
	var nume1 = new Wlj.widgets.search.tile.Tile({
					//cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					style:{
					backgroundColor:'rgb(27, 150, 209)'
					},
					tileName :'客户首页',
					tileLogo :'/contents/pages/testtiles/chart/top.jpg',
					pos_size : {
						TX : 0,
						TY : 0,
						TW : 1,
						TH : 1
					},
					html: '',
					clickFire : function(){
						panel.show();
						panelInfo.hide();
						panelGx.hide();
						panelYeWu.hide();
						panelLiCai.hide();
					}

				});
	var nume2 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					tileName :'基础信息',
					style:{
					backgroundColor:'rgb(27, 150, 209)'
					},
					tileLogo :'/contents/pages/testtiles/chart/2.jpg',
					pos_size : {
						TX : 1,
						TY : 0,
						TW : 1,
						TH : 1
					},
					html: '',
					clickFire : function(){
					panelInfo.show();
					panelGx.hide();
						panel.hide();
						panelYeWu.hide();
						panelLiCai.hide();
					}
				});
				var nume3 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					tileName :'业务信息',
					style:{
					backgroundColor:'rgb(27, 150, 209)'
					},
					tileLogo :'/contents/pages/testtiles/chart/3.jpg',
					pos_size : {
						TX : 2,
						TY : 0,
						TW : 1,
						TH : 1
					},
					html: '',
					clickFire : function(){
						panel.hide();
						panelInfo.hide();
						panelGx.hide();
						panelYeWu.show();
						panelLiCai.hide();
					}
				});
				var nume4 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					tileName :'关系信息',
					style:{
					backgroundColor:'rgb(27, 150, 209)'
					},
					tileLogo :'/contents/pages/testtiles/chart/4.jpg',
					pos_size : {
						TX : 3,
						TY : 0,
						TW : 1,
						TH : 1
					},
					html: '',
					clickFire : function(){
					panelGx.show();
					panelInfo.hide();
						panel.hide();
						panelYeWu.hide();
						panelLiCai.hide();
												
					}
					
				});
				var nume5 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					tileName :'理财产品',
					style:{
					backgroundColor:'rgb(27, 150, 209)'
					},
					tileLogo :'/contents/pages/testtiles/chart/5.jpg',
					pos_size : {
						TX : 4,
						TY : 0,
						TW : 1,
						TH : 1
					},
					html: '',
					clickFire : function(){
						panel.hide();
						panelInfo.hide();
						panelGx.hide();
						panelYeWu.hide();
						panelLiCai.show();
					}
				});
	panel1.add(nume1);
	panel1.add(nume2);
	panel1.add(nume3);
	panel1.add(nume4);
	panel1.add(nume5);
	
	var panelInfo = new Ext.Panel({
		height : bodysize.height +128,
		frame : true,
		hidden:true,
		items : []
	});
	var info1 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 0,
						TY : 0,
						TW : 2,
						TH : 1.5
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(27, 150, 209)" class="tile_chart_tit"><p class="tit" ><b>身份证号：23232***323<br/>年龄：32岁    性别：男<br/>出生：1969年4月2日<br/>籍贯：山东省<br/>从业年限： 23年<br/>学历：博士<br/>毕业学校：中央财经大学<br/></div>'
				});	
				var info2 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 2,
						TY : 0,
						TW : 2,
						TH : 1.5
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(27, 150, 209)" class="tile_chart_tit"><p class="tit" ><b>公司名称：上海远洋集团<br/>职务：CFO 职称：高级经济师<br/>持股信息：320，323股<br/>职业：财务会计<br/>公司所属行业：运输业<br/>入职日期：1999年2月<br/>公司地址：上海浦东区世纪大道8号<br/><br/></div>'
				});	
				var info3 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 4,
						TY : 0,
						TW : 2,
						TH : 1.5
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(27, 150, 209)" class="tile_chart_tit"><p class="tit" ><b>个人偏好： 音乐、旅游、艺术、收藏<br/>客户预约：1、拜访：2013年7月20日<br/>提醒信息：<br/>1、生日  ：  3天<br/>2、定期理财存款：  30天<br/>3、大额变动：2013年9月20日，<br/>支出：23，323.00元<br/></div>'
				});	
				var info11 = new Wlj.widgets.search.tile.Tile({
					ownerW : tileWidth,
					ownerH : 10,
					position : 'absolute',
					removeable : false,
					pos_size : {
						TX : 0,
						TY : 0,
						TW : tileWidth,
						TH : 1
					},
					items : [info1,info2,info3]
				});	
				var info4 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 0,
						TY : 1.5,
						TW : 3,
						TH : 2
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(111, 150, 209)" class="tile_chart_tit"><p class="tit" ><b>家庭住址：上海徐汇区XXX大道2号<br/>家庭电话：021-92323982<br/>家庭月收入：30万元以上<br/>个人月收入：25万以上<br/>家庭年度总收入：400万以上<br/>家庭房产：1000万以上<br/>家庭现金类资产：   800万<br/>家庭投资：2000万<br/>家庭月支出：5万～10万<br/><br/></div>'
				});	
				var info5 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 3,
						TY : 1.5,
						TW : 3,
						TH : 2
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(111, 150, 209)" class="tile_chart_tit"><p class="tit" ><b>家庭成员：3人               是否户主： 是<br/>是否家庭财务主管：  是<br/>家庭评级：  私人银行客户<br/>家庭总资产：233，320，430.00<br/>家庭总负责：839，32.00<br/>成员明细：<br/>1、刘雅灵     女   34岁  夫妻 1382320**23<br/>2、王辉        男    9岁     父子 <br/> <br/></div>'
				});	
	
	
	var info12 = new Wlj.widgets.search.tile.Tile({
					ownerW : tileWidth,
					ownerH : 10,
					position : 'absolute',
					removeable : false,
					pos_size : {
						TX : 0,
						TY : .5,
						TW : tileWidth,
						TH : 1
					},
					items : [info4,info5]
				});	
				var info6 =  new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 0,
						TY : 0,
						TW : 2.5,
						TH : 1.3
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(250, 150, 209)" class="tile_chart_tit"><p class="tit" ><b><font size="4" color="black">通讯</font></b><br/><b><font color="black">手机：13892***8232<br/>座机： 021-61823829<br/>邮箱：wangdy@sina.com<br/>住址：上海浦东世纪大道<br/><br/></font></div>'
				});	
				var info7 =  new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 2.5,
						TY : 0,
						TW : 3.5,
						TH : 1.3
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(250, 150, 209)" class="tile_chart_tit"><p class="tit" ><b><font size="4" color="black">个人履历</font></b><br/><b><font color="black">1、上海中国石油公司    CFO   1992年<br/>2、北京中国石化公司    财务主管   1980年<br/>3、北京大学        博士        1976年<br/>4、哈佛大学       博士         1979年<br/></font></div>'
				});	
	var info13 = new Wlj.widgets.search.tile.Tile({
					ownerW : tileWidth,
					ownerH : 10,
					position : 'absolute',
					removeable : false,
					pos_size : {
						TX : 0,
						TY : 3.5,
						TW : tileWidth,
						TH : 1.3
					},
					items : [info6,info7]
				});
	panelInfo.add(info11);
	panelInfo.add(info12);
	panelInfo.add(info13);
	
	
	
	var panelGx = new Ext.Panel({
		height : bodysize.height +128,
		frame : true,
		hidden:true,
		items : []
	});
	var Gx1 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 0,
						TY : 0,
						TW : 5,
						TH : 1.2
					},
					html: '<div style="width:100%;height:100%;background-color:rgb(27, 150, 209)" class="tile_chart_tit"><p class="tit" ><b><font size="4" color="black">关系信息</b><font color="black"><table width="100%">'+
					'<tr class="in_sys_line" style="background:rgb(27, 170, 209)"><td width="5%">1</td><td width="15%">刘忻</td><td width="15%">同学</td><td width="5%">男</td><td width="30%">1星级</td><td width="30%">13888888888</td></tr>'+
					'<tr class="in_sys_line"><td>2</td><td>张宇</td><td>同事</td><td>男</td><td>2星级</td><td>13888888888</td></tr>'+
					'<tr class="in_sys_line" style="background:rgb(27, 170, 209)"><td>3</td><td>赵婷婷</td><td>亲戚</td><td>女</td><td>3星级</td><td>13888888888</td></tr>'+
					'<tr class="in_sys_line"><td>4</td><td>王乐</td><td>同事</td><td>女</td><td>潜在</td><td>13888888888</td></tr>'+
					'</table></font></div>'
				});	
				var Gx2 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 0,
						TY : 1.2,
						TW : 5,
						TH : 1.2
					},
					html: '<a href="#"><img src="chart/bb.jpg" ></img></a>'
				});	
			
	panelGx.add(Gx1);	
	panelGx.add(Gx2);
	
	//理财产品的panel	
	var panelLiCai =new Ext.Panel({
		frame : true,
		height : bodysize.height -128,
		items : []
	});

	var LiCaiT = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 0,
						TY : 0,
						TW : 9,
						TH : 2.5
					},
					html: '<div class="mainbox blue7" width="100%",height="100%">'+
						  '<div class="mainbox_tit"><div class="tit2 wdkh">理财产品</div></div><div><a href="#"><img src="chart/but_1.jpg" ></img></a>&nbsp&nbsp'+
							'<a href="#"><img src="chart/but_2.jpg" ></img></a>&nbsp&nbsp<a href="#"><img src="chart/but_3.jpg" ></img></a></div><div class="mainbox_con">'+
						  '<div class="ph_table_div"><table class="ph_table" border="0" cellspacing="0" cellpadding="0"><thead>'+
                         ' <tr><td><div class="wr60">产品名称</div></td><td><div class="wr80">产品类型</div></td>'+
                          '<td><div class="wr70">业务类型</div></td><td><div class="wr70">购买日期</div></td>'+
							'<td><div class="wr50">期限</div></td><td><div class="wr60">年化收益率</div></td>'+
							'<td><div class="wr70">到期日</div></td><td><div class="wr70">份额</div></td><td><div class="wr70">总额度</div></td>'+
							'<td><div class="wr80">预计客户收益</div></td><td><div class="wr60">贡献度</div></td></tr></thead><tbody>'+
                          '<tr class="bg1"><td><div class="wr60"><p title="">易达盈16</p></div></td><td><div class="wr80"><p title="">境内理财产品</p></div></td>'+
							 '<td><div class="wr70"><p title="">保本</p></div></td><td><div class="wr70"><p title="">2013-4-1</p></div></td>'+
							 '<td><div class="wr50"><p title="">30天</p></div></td><td><div class="wr60" redr><p title="">4.1%</p></div></td>'+
							 '<td><div class="wr70"><p title="">2013-5-1</p></div></td><td><div class="wr70 redr"><p>200,000</p></div></td>'+
                          '<td><div class="wr70 redr"><p>200,000</p></div></td><td><div class="wr80 redr"><p>683</p></div></td>'+
                          '<td><div class="wr60 redr"><p>250</p></div></td></tr>'+
                          '<tr class="bg2">'+
                          '<td><div class="wr60"><p title="">步步为盈27</p></div></td>'+
							'<td><div class="wr80"><p title="">境内理财产品</p></div></td>'+
							'<td><div class="wr70"><p title="">保本</p></div></td>'+
							'<td><div class="wr70"><p title="">2013-4-1</p></div></td>'+
							'<td><div class="wr50"><p title="">30天</p></div></td>'+
							'<td><div class="wr60"><p title="">4.1%</p></div></td>'+
							'<td><div class="wr70"><p title="">2013-5-1</p></div></td>'+
							'<td><div class="wr70 redr"><p>400,000</p></div></td>'+
                          '<td><div class="wr70 redr"><p>400000</p></div></td>'+
                          '<td><div class="wr80 redr"><p>1,367</p></div></td>'+
                         ' <td><div class="wr60 redr"><p>410</p></div></td>'+
                          '</tr>'+
							 '<tr class="bg1"><td><div class="wr60"><p title="">易达盈28</p></div></td><td><div class="wr80"><p title="">境内理财产品</p></div></td>'+
							 '<td><div class="wr70"><p title="">保本</p></div></td><td><div class="wr70"><p title="">2013-4-1</p></div></td>'+
							 '<td><div class="wr50"><p title="">30天</p></div></td><td><div class="wr60" redr><p title="">4.1%</p></div></td>'+
							 '<td><div class="wr70"><p title="">2013-5-1</p></div></td><td><div class="wr70 redr"><p>200,000</p></div></td>'+
                          '<td><div class="wr70 redr"><p>200,000</p></div></td><td><div class="wr80 redr"><p>683</p></div></td>'+
                          '<td><div class="wr60 redr"><p>250</p></div></td></tr>'+
                          '<tr class="bg2">'+
                          '<td><div class="wr60"><p title="">步步为盈29</p></div></td>'+
							'<td><div class="wr80"><p title="">境内理财产品</p></div></td>'+
							'<td><div class="wr70"><p title="">保本</p></div></td>'+
							'<td><div class="wr70"><p title="">2013-4-1</p></div></td>'+
							'<td><div class="wr50"><p title="">30天</p></div></td>'+
							'<td><div class="wr60"><p title="">4.3%</p></div></td>'+
							'<td><div class="wr70"><p title="">2013-5-1</p></div></td>'+
							'<td><div class="wr70 redr"><p>200,000</p></div></td>'+
                          '<td><div class="wr70 redr"><p>200,000</p></div></td>'+
                          '<td><div class="wr80 redr"><p>1,367</p></div></td>'+
                         ' <td><div class="wr60 redr"><p>410</p></div></td>'+
                          '</tr>'+
							 '<tr class="bg1"><td><div class="wr60"><p title="">易达盈30</p></div></td><td><div class="wr80"><p title="">境内理财产品</p></div></td>'+
							 '<td><div class="wr70"><p title="">保本</p></div></td><td><div class="wr70"><p title="">2013-10-3</p></div></td>'+
							 '<td><div class="wr50"><p title="">3个月</p></div></td><td><div class="wr60" redr><p title="">4.5%</p></div></td>'+
							 '<td><div class="wr70"><p title="">2014-1-3</p></div></td><td><div class="wr70 redr"><p>2120,000</p></div></td>'+
                          '<td><div class="wr70 redr"><p>2230,000</p></div></td><td><div class="wr80 redr"><p>1683</p></div></td>'+
                          '<td><div class="wr60 redr"><p>2250</p></div></td></tr>'+
                          '<tr class="bg2">'+
                          '<td><div class="wr60"><p title="">涨跌易达盈1</p></div></td>'+
							'<td><div class="wr80"><p title="">境内理财产品</p></div></td>'+
							'<td><div class="wr70"><p title="">保本</p></div></td>'+
							'<td><div class="wr70"><p title="">2013-4-1</p></div></td>'+
							'<td><div class="wr50"><p title="">30天</p></div></td>'+
							'<td><div class="wr60"><p title="">4.1%</p></div></td>'+
							'<td><div class="wr70"><p title="">2013-5-1</p></div></td>'+
							'<td><div class="wr70 redr"><p>400,000</p></div></td>'+
                          '<td><div class="wr70 redr"><p>400000</p></div></td>'+
                          '<td><div class="wr80 redr"><p>1,367</p></div></td>'+
                         ' <td><div class="wr60 redr"><p>410</p></div></td>'+
                          '</tr>'+
							 '<tr class="bg1"><td><div class="wr60"><p title="">施罗德基金</p></div></td><td><div class="wr80"><p title="">境内理财产品</p></div></td>'+
							 '<td><div class="wr70"><p title="">保本</p></div></td><td><div class="wr70"><p title="">2013-4-1</p></div></td>'+
							 '<td><div class="wr50"><p title="">30天</p></div></td><td><div class="wr60" redr><p title="">4.1%</p></div></td>'+
							 '<td><div class="wr70"><p title="">2013-5-1</p></div></td><td><div class="wr70 redr"><p>200,000</p></div></td>'+
                          '<td><div class="wr70 redr"><p>200,000</p></div></td><td><div class="wr80 redr"><p>683</p></div></td>'+
                          '<td><div class="wr60 redr"><p>250</p></div></td></tr>'+
                          '<tr class="bg2">'+
                          '<td><div class="wr60"><p title="">步步为盈27</p></div></td>'+
							'<td><div class="wr80"><p title="">境内理财产品</p></div></td>'+
							'<td><div class="wr70"><p title="">非保本</p></div></td>'+
							'<td><div class="wr70"><p title="">2013-4-1</p></div></td>'+
							'<td><div class="wr50"><p title="">30天</p></div></td>'+
							'<td><div class="wr60"><p title="">4.1%</p></div></td>'+
							'<td><div class="wr70"><p title="">2013-5-1</p></div></td>'+
							'<td><div class="wr70 redr"><p>400,000</p></div></td>'+
                          '<td><div class="wr70 redr"><p>400,000</p></div></td>'+
                          '<td><div class="wr80 redr"><p>1,367</p></div></td>'+
                         ' <td><div class="wr60 redr"><p>410</p></div></td>'+
                          '</tr>'+
							 '<tr class="bg1"><td><div class="wr60"><p title="">易达盈16</p></div></td><td><div class="wr80"><p title="">境内理财产品</p></div></td>'+
							 '<td><div class="wr70"><p title="">保本</p></div></td><td><div class="wr70"><p title="">2013-8-20</p></div></td>'+
							 '<td><div class="wr50"><p title="">180天</p></div></td><td><div class="wr60" redr><p title="">4.8%</p></div></td>'+
							 '<td><div class="wr70"><p title="">2014-2-18</p></div></td><td><div class="wr70 redr"><p>60,000</p></div></td>'+
                          '<td><div class="wr70 redr"><p>200,000</p></div></td><td><div class="wr80 redr"><p>683</p></div></td>'+
                          '<td><div class="wr60 redr"><p>250</p></div></td></tr>'+
                          '<tr class="bg2">'+
                          '<td><div class="wr60"><p title="">东亚联丰亚洲债券</p></div></td>'+
							'<td><div class="wr80"><p title="">境内理财产品</p></div></td>'+
							'<td><div class="wr70"><p title="">非保本</p></div></td>'+
							'<td><div class="wr70"><p title="">2013-4-1</p></div></td>'+
							'<td><div class="wr50"><p title="">30天</p></div></td>'+
							'<td><div class="wr60"><p title="">4.1%</p></div></td>'+
							'<td><div class="wr70"><p title="">2013-5-1</p></div></td>'+
							'<td><div class="wr70 redr"><p>400,000</p></div></td>'+
                          '<td><div class="wr70 redr"><p>400,000</p></div></td>'+
                          '<td><div class="wr80 redr"><p>1,367</p></div></td>'+
                         ' <td><div class="wr60 redr"><p>410</p></div></td>'+
                          '</tr>'+
							 '<tr class="bg1"><td><div class="wr60"><p title="">利财通1</p></div></td><td><div class="wr80"><p title="">QDII境外理财产品</p></div></td>'+
							 '<td><div class="wr70"><p title="">保本</p></div></td><td><div class="wr70"><p title="">2013-3-1</p></div></td>'+
							 '<td><div class="wr50"><p title="">234天</p></div></td><td><div class="wr60" redr><p title="">4.1%</p></div></td>'+
							 '<td><div class="wr70"><p title="">2013-3-1</p></div></td><td><div class="wr70 redr"><p>200,000</p></div></td>'+
                          '<td><div class="wr70 redr"><p>200,000</p></div></td><td><div class="wr80 redr"><p>683</p></div></td>'+
                          '<td><div class="wr60 redr"><p>250</p></div></td></tr>'+
                          '<tr class="bg2">'+
                          '<td><div class="wr60"><p title="">利财通2</p></div></td>'+
							'<td><div class="wr80"><p title="">QDII境外理财产品</p></div></td>'+
							'<td><div class="wr70"><p title="">非保本</p></div></td>'+
							'<td><div class="wr70"><p title="">2013-4-1</p></div></td>'+
							'<td><div class="wr50"><p title="">30天</p></div></td>'+
							'<td><div class="wr60"><p title="">4.1%</p></div></td>'+
							'<td><div class="wr70"><p title="">2013-5-1</p></div></td>'+
							'<td><div class="wr70 redr"><p>400,000</p></div></td>'+
                          '<td><div class="wr70 redr"><p>400,000</p></div></td>'+
                          '<td><div class="wr80 redr"><p>1,367</p></div></td>'+
                         ' <td><div class="wr60 redr"><p>410</p></div></td>'+
                          '</tr>'+
							 '<tr class="bg1"><td><div class="wr60"><p title="">利财通3</p></div></td><td><div class="wr80"><p title="">QDII境外理财产品</p></div></td>'+
							 '<td><div class="wr70"><p title="">非结构性</p></div></td><td><div class="wr70"><p title="">2013-4-1</p></div></td>'+
							 '<td><div class="wr50"><p title="">30天</p></div></td><td><div class="wr60" redr><p title="">4.1%</p></div></td>'+
							 '<td><div class="wr70"><p title="">2013-5-1</p></div></td><td><div class="wr70 redr"><p>200,000</p></div></td>'+
                          '<td><div class="wr70 redr"><p>200,000</p></div></td><td><div class="wr80 redr"><p>683</p></div></td>'+
                          '<td><div class="wr60 redr"><p>250</p></div></td></tr>'+
                          '</tbody>'+
                          '</table>'+
                          '</div>'+
                          '</div>'+
                          '</div>'
				});	
	var LiCaiR = new Wlj.widgets.search.tile.Tile({
					ownerW : tileWidth,
					ownerH : 10,
					position : 'absolute',
					removeable : false,
					pos_size : {
						TX : 0,
						TY : 0,
						TW : tileWidth,
						TH : 1
					},
					items : [LiCaiT]
				});	
			panelLiCai.add(LiCaiR);
			panelLiCai.setHeight(128*10);
			panelLiCai.doLayout();
/**********************************************/
//业务信息
var panelYeWu =new Ext.Panel({
		frame : true,
		height : bodysize.height -128,
		items : []
	});

	var YeWuT1 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 0,
						TY : 0,
						TW : 9,
						TH : 1.9
					},
					html: '<div class="mainbox blue7" width="100%",height="100%">'+
						  '<div class="mainbox_tit"><div class="tit2 wdkh">贷款账户</div></div><div><a href="#"><img src="chart/but_6.jpg" ></img></a>&nbsp&nbsp'+
							'<a href="#"><img src="chart/but_7.jpg" ></img></a></div><div class="mainbox_con">'+
						  '<div class="ph_table_div"><table class="ph_table" border="0" cellspacing="0" cellpadding="0"><thead>'+
                         ' <tr><td><div class="wr60">序号</div></td><td><div class="wr80">贷款账号</div></td>'+
                          '<td><div class="wr80">关联存款账号</div></td><td><div class="wr70">开户网点</div></td>'+
							'<td><div class="wr50">开户日期</div></td><td><div class="wr60">贷款类型</div></td>'+
							'<td><div class="wr70">贷款用途</div></td><td><div class="wr70">贷款日期</div></td><td><div class="wr70">贷款状态</div></td>'+
							'<td><div class="wr80">贷款金额</div></td><td><div class="wr60">到期日</div></td><td><div class="wr60">还款方式</div></td></tr></thead><tbody>'+
                          '<tr class="bg1"><td><div class="wr60"><p title="">1</p></div></td><td><div class="wr80"><p title="">839221382948394</p></div></td>'+
							 '<td><div class="wr70"><p title="">128023920320129112</p></div></td><td><div class="wr70"><p title="">上海世纪大道支行</p></div></td>'+
							 '<td><div class="wr50"><p title="">2013年8月2日</p></div></td><td><div class="wr60" redr><p title="">消费贷款</p></div></td>'+
							 '<td><div class="wr70"><p title="">消费</p></div></td><td><div class="wr70 redr"><p>2013年8月2日</p></div></td>'+
                          '<td><div class="wr70 redr"><p>正常</p></div></td><td><div class="wr80 redr"><p>300,000</p></div></td>'+
                          '<td><div class="wr60 redr"><p>2014-8-2</p></div></td><td><div class="wr60 redr"><p>一次性本息</p></div></td></tr>'+
                          '<tr class="bg2">'+
                         ' <td><div class="wr60"><p title="">2</p></div></td><td><div class="wr80"><p title="">839232718274378</p></div></td>'+
							 '<td><div class="wr70"><p title="">128023920320129112</p></div></td><td><div class="wr70"><p title="">上海世纪大道支行</p></div></td>'+
							 '<td><div class="wr50"><p title="">2013年9月9日</p></div></td><td><div class="wr60" redr><p title="">消费贷款</p></div></td>'+
							 '<td><div class="wr70"><p title="">消费</p></div></td><td><div class="wr70 redr"><p>2013年9月9日</p></div></td>'+
                          '<td><div class="wr70 redr"><p>正常</p></div></td><td><div class="wr80 redr"><p>2,000,000</p></div></td>'+
                          '<td><div class="wr60 redr"><p>2014-9-9</p></div></td><td><div class="wr60 redr"><p>一次性本息</p></div></td>'+
                          '</tr>'+
							 '<tr class="bg1"><td><div class="wr60"><p title="">3</p></div></td><td><div class="wr80"><p title="">839221382948366</p></div></td>'+
							 '<td><div class="wr70"><p title="">128023920320129112</p></div></td><td><div class="wr70"><p title="">上海世纪大道支行</p></div></td>'+
							 '<td><div class="wr50"><p title="">2013年8月5日</p></div></td><td><div class="wr60" redr><p title="">消费贷款</p></div></td>'+
							 '<td><div class="wr70"><p title="">消费</p></div></td><td><div class="wr70 redr"><p>2013年8月5日</p></div></td>'+
                          '<td><div class="wr70 redr"><p>正常</p></div></td><td><div class="wr80 redr"><p>320,000</p></div></td>'+
                          '<td><div class="wr60 redr"><p>2014-8-5</p></div></td><td><div class="wr60 redr"><p>一次性本息</p></div></td></tr>'+
                          '<tr class="bg2">'+
                         ' <td><div class="wr60"><p title="">4</p></div></td><td><div class="wr80"><p title="">839221382948123</p></div></td>'+
							 '<td><div class="wr70"><p title="">128023920320129114</p></div></td><td><div class="wr70"><p title="">上海世纪大道支行</p></div></td>'+
							 '<td><div class="wr50"><p title="">2013年5月2日</p></div></td><td><div class="wr60" redr><p title="">消费贷款</p></div></td>'+
							 '<td><div class="wr70"><p title="">消费</p></div></td><td><div class="wr70 redr"><p>2013年5月2日</p></div></td>'+
                          '<td><div class="wr70 redr"><p>正常</p></div></td><td><div class="wr80 redr"><p>100,000</p></div></td>'+
                          '<td><div class="wr60 redr"><p>2014-5-2</p></div></td><td><div class="wr60 redr"><p>一次性本息</p></div></td>'+
                          '</tr>'+
							 '<tr class="bg1"><td><div class="wr60"><p title="">5</p></div></td><td><div class="wr80"><p title="">839221382948394</p></div></td>'+
							 '<td><div class="wr70"><p title="">128023920320129112</p></div></td><td><div class="wr70"><p title="">上海世纪大道支行</p></div></td>'+
							 '<td><div class="wr50"><p title="">2013年8月12日</p></div></td><td><div class="wr60" redr><p title="">消费贷款</p></div></td>'+
							 '<td><div class="wr70"><p title="">消费</p></div></td><td><div class="wr70 redr"><p>2013年8月12日</p></div></td>'+
                          '<td><div class="wr70 redr"><p>正常</p></div></td><td><div class="wr80 redr"><p>300,000</p></div></td>'+
                          '<td><div class="wr60 redr"><p>2014-8-12</p></div></td><td><div class="wr60 redr"><p>一次性本息</p></div></td></tr>'+
                          '<tr class="bg2">'+
                         ' <td><div class="wr60"><p title="">6</p></div></td><td><div class="wr80"><p title="">839221382948394</p></div></td>'+
							 '<td><div class="wr70"><p title="">128023920320129112</p></div></td><td><div class="wr70"><p title="">北京东方广场支行</p></div></td>'+
							 '<td><div class="wr50"><p title="">2013年8月2日</p></div></td><td><div class="wr60" redr><p title="">消费贷款</p></div></td>'+
							 '<td><div class="wr70"><p title="">消费</p></div></td><td><div class="wr70 redr"><p>2013年8月2日</p></div></td>'+
                          '<td><div class="wr70 redr"><p>正常</p></div></td><td><div class="wr80 redr"><p>500,000</p></div></td>'+
                          '<td><div class="wr60 redr"><p>2014-8-2</p></div></td><td><div class="wr60 redr"><p>一次性本息</p></div></td>'+
                          '</tr>'+
							 '<tr class="bg1"><td><div class="wr60"><p title="">7</p></div></td><td><div class="wr80"><p title="">839221382948394</p></div></td>'+
							 '<td><div class="wr70"><p title="">128023920320129112</p></div></td><td><div class="wr70"><p title="">上海世纪大道支行</p></div></td>'+
							 '<td><div class="wr50"><p title="">2013年8月21日</p></div></td><td><div class="wr60" redr><p title="">消费贷款</p></div></td>'+
							 '<td><div class="wr70"><p title="">消费</p></div></td><td><div class="wr70 redr"><p>2013年8月21日</p></div></td>'+
                          '<td><div class="wr70 redr"><p>正常</p></div></td><td><div class="wr80 redr"><p>230,000</p></div></td>'+
                          '<td><div class="wr60 redr"><p>2014-8-21</p></div></td><td><div class="wr60 redr"><p>一次性本息</p></div></td></tr>'+
                          '<tr class="bg2">'+
                         ' <td><div class="wr60"><p title="">8</p></div></td><td><div class="wr80"><p title="">839221382948394</p></div></td>'+
							 '<td><div class="wr70"><p title="">128023920320129112</p></div></td><td><div class="wr70"><p title="">北京东方广场支行</p></div></td>'+
							 '<td><div class="wr50"><p title="">2013年8月2日</p></div></td><td><div class="wr60" redr><p title="">消费贷款</p></div></td>'+
							 '<td><div class="wr70"><p title="">消费</p></div></td><td><div class="wr70 redr"><p>2013年8月2日</p></div></td>'+
                          '<td><div class="wr70 redr"><p>正常</p></div></td><td><div class="wr80 redr"><p>120,000</p></div></td>'+
                          '<td><div class="wr60 redr"><p>2014-8-2</p></div></td><td><div class="wr60 redr"><p>一次性本息</p></div></td>'+
                          '</tr>'+
                          '</tbody>'+
                          '</table>'+
							'<div><a href="#"><img src="chart/but_4.jpg" ></img></a>&nbsp&nbsp'+
							'<a href="#"><img src="chart/but_5.jpg" ></img></a></div>'+
                          '</div>'+
                          '</div>'+
                          '</div>'
				});	
				var YeWuT2 = new Wlj.widgets.search.tile.Tile({
					cls : 'search_cust',
					ownerW : tileWidth,
					removeable : true,
					baseSize : 128,
					baseMargin : 3,
					pos_size : {
						TX : 0,
						TY : 0,
						TW : 9,
						TH : 1.9
					},
					html: '<div class="mainbox blue7" width="100%",height="100%">'+
						  '<div class="mainbox_tit"><div class="tit2 wdkh">贷款账户</div></div><div><a href="#"><img src="chart/but_6.jpg" ></img></a>&nbsp&nbsp'+
							'<a href="#"><img src="chart/but_7.jpg" ></img></a></div><div class="mainbox_con">'+
						  '<div class="ph_table_div"><table class="ph_table" border="0" cellspacing="0" cellpadding="0"><thead>'+
                         ' <tr><td><div class="wr60">序号</div></td><td><div class="wr80">存款款账号</div></td>'+
                          '<td><div class="wr80">账户类型</div></td><td><div class="wr70">币种</div></td>'+
							'<td><div class="wr50">存款余额</div></td><td><div class="wr60">存款年日均</div></td>'+
							'<td><div class="wr70">存款月均</div></td><td><div class="wr70">存款季均</div></td><td><div class="wr70">开户网点</div></td>'+
							'<td><div class="wr80">开户日期</div></td><td><div class="wr60">利率</div></td><td><div class="wr60">期限</div></td></tr></thead><tbody>'+
                          '<tr class="bg1"><td><div class="wr60"><p title="">1</p></div></td><td><div class="wr80"><p title="">839221382948394</p></div></td>'+
							 '<td><div class="wr70"><p title="">储蓄存款账户</p></div></td><td><div class="wr70"><p title="">人民币</p></div></td>'+
							 '<td><div class="wr60 right redr"><p title="">590,323</p></div></td><td><div class="wr60 right redr"><p title="">504,903</p></div></td>'+
							 '<td><div class="wr70 right redr"><p title="">560,239</p></div></td><td><div class="wr60 right redr"><p>530,239</p></div></td>'+
                          '<td><div class="wr70"><p>上海世纪大道支行</p></div></td><td><div class="wr80"><p>2013年8月2日</p></div></td>'+
                          '<td><div class="wr60 right redr"><p>0.36%</p></div></td><td><div class="wr60"><p>活期</p></div></td></tr>'+
                          '<tr class="bg2">'+
                         ' <td><div class="wr60"><p title="">2</p></div></td><td><div class="wr80"><p title="">839232718274378</p></div></td>'+
							 '<td><div class="wr70"><p title="">定期存款户</p></div></td><td><div class="wr70"><p title="">人民币</p></div></td>'+
							 '<td><div class="wr50 right redr"><p title="">2,000,000</p></div></td><td><div class="wr60 right redr"><p title="">2,000,000</p></div></td>'+
							 '<td><div class="wr70 right redr"><p title="">2,,000,000</p></div></td><td><div class="wr60 right redr"><p>2,000,000</p></div></td>'+
                          '<td><div class="wr70"><p>上海世纪大道支行</p></div></td><td><div class="wr80"><p>2013年9月9日</p></div></td>'+
                          '<td><div class="wr60 right redr"><p>2.20%</p></div></td><td><div class="wr60"><p>1年</p></div></td>'+
                          '</tr>'+
							 '<tr class="bg1"><td><div class="wr60"><p title="">1</p></div></td><td><div class="wr80"><p title="">839221382948394</p></div></td>'+
							 '<td><div class="wr70"><p title="">储蓄存款账户</p></div></td><td><div class="wr70"><p title="">人民币</p></div></td>'+
							 '<td><div class="wr60 right redr"><p title="">590,323</p></div></td><td><div class="wr60 right redr"><p title="">504,903</p></div></td>'+
							 '<td><div class="wr70 right redr"><p title="">560,232</p></div></td><td><div class="wr60 right redr"><p>530,239</p></div></td>'+
                          '<td><div class="wr70"><p>上海世纪大道支行</p></div></td><td><div class="wr80"><p>2013年8月2日</p></div></td>'+
                          '<td><div class="wr60 right redr"><p>0.36%</p></div></td><td><div class="wr60"><p>活期</p></div></td></tr>'+
                          '<tr class="bg2">'+
                         ' <td><div class="wr60"><p title="">2</p></div></td><td><div class="wr80"><p title="">839232718274378</p></div></td>'+
							 '<td><div class="wr70"><p title="">定期存款户</p></div></td><td><div class="wr70"><p title="">人民币</p></div></td>'+
							 '<td><div class="wr50 right redr"><p title="">2,000,000</p></div></td><td><div class="wr60 right redr"><p title="">2,000,000</p></div></td>'+
							 '<td><div class="wr70 right redr"><p title="">2,,000,000</p></div></td><td><div class="wr60 right redr"><p>2,000,000</p></div></td>'+
                          '<td><div class="wr70"><p>上海世纪大道支行</p></div></td><td><div class="wr80"><p>2013年9月9日</p></div></td>'+
                          '<td><div class="wr60 right redr"><p>2.20%</p></div></td><td><div class="wr60"><p>1年</p></div></td>'+
                          '</tr>'+
							 '<tr class="bg1"><td><div class="wr60"><p title="">1</p></div></td><td><div class="wr80"><p title="">839221382948394</p></div></td>'+
							 '<td><div class="wr70"><p title="">储蓄存款账户</p></div></td><td><div class="wr70"><p title="">人民币</p></div></td>'+
							 '<td><div class="wr60 right redr"><p title="">590,323</p></div></td><td><div class="wr60 right redr"><p title="">504,903</p></div></td>'+
							 '<td><div class="wr70 right redr"><p title="">560,232</p></div></td><td><div class="wr60 right redr"><p>530,239</p></div></td>'+
                          '<td><div class="wr70"><p>上海世纪大道支行</p></div></td><td><div class="wr80"><p>2013年8月2日</p></div></td>'+
                          '<td><div class="wr60 right redr"><p>0.36%</p></div></td><td><div class="wr60"><p>活期</p></div></td></tr>'+
                          '<tr class="bg2">'+
                         ' <td><div class="wr60"><p title="">2</p></div></td><td><div class="wr80"><p title="">839232718274378</p></div></td>'+
							 '<td><div class="wr70"><p title="">定期存款户</p></div></td><td><div class="wr70"><p title="">人民币</p></div></td>'+
							 '<td><div class="wr50 right redr"><p title="">2,000,000</p></div></td><td><div class="wr60 right redr"><p title="">2,000,000</p></div></td>'+
							 '<td><div class="wr70 right redr"><p title="">2,,000,000</p></div></td><td><div class="wr60 right redr"><p>2,000,000</p></div></td>'+
                          '<td><div class="wr70"><p>上海世纪大道支行</p></div></td><td><div class="wr80"><p>2013年9月9日</p></div></td>'+
                          '<td><div class="wr60 right redr"><p>2.20%</p></div></td><td><div class="wr60"><p>3年</p></div></td>'+
                          '</tr>'+
							 '<tr class="bg1"><td><div class="wr60"><p title="">1</p></div></td><td><div class="wr80"><p title="">839221382948394</p></div></td>'+
							 '<td><div class="wr70"><p title="">储蓄存款账户</p></div></td><td><div class="wr70"><p title="">人民币</p></div></td>'+
							 '<td><div class="wr60 right redr"><p title="">590,323</p></div></td><td><div class="wr60 right redr"><p title="">504,903</p></div></td>'+
							 '<td><div class="wr70 right redr"><p title="">560,232</p></div></td><td><div class="wr60 right redr"><p>530,239</p></div></td>'+
                          '<td><div class="wr70"><p>上海世纪大道支行</p></div></td><td><div class="wr80"><p>2013年8月2日</p></div></td>'+
                          '<td><div class="wr60 right redr"><p>0.36%</p></div></td><td><div class="wr60"><p>活期</p></div></td></tr>'+
                          '<tr class="bg2">'+
                         ' <td><div class="wr60"><p title="">2</p></div></td><td><div class="wr80"><p title="">839232718274378</p></div></td>'+
							 '<td><div class="wr70"><p title="">定期存款户</p></div></td><td><div class="wr70"><p title="">人民币</p></div></td>'+
							 '<td><div class="wr50 right redr"><p title="">2,000,000</p></div></td><td><div class="wr60 right redr"><p title="">2,000,000</p></div></td>'+
							 '<td><div class="wr70 right redr"><p title="">2,,000,000</p></div></td><td><div class="wr60 right redr"><p>2,000,000</p></div></td>'+
                          '<td><div class="wr70"><p>上海世纪大道支行</p></div></td><td><div class="wr80"><p>2013年9月9日</p></div></td>'+
                          '<td><div class="wr60 right redr"><p>2.20%</p></div></td><td><div class="wr60"><p>4年</p></div></td>'+
                          '</tr>'+
							 '<tr class="bg1"><td><div class="wr60"><p title="">1</p></div></td><td><div class="wr80"><p title="">839221382948394</p></div></td>'+
							 '<td><div class="wr70"><p title="">储蓄存款账户</p></div></td><td><div class="wr70"><p title="">人民币</p></div></td>'+
							 '<td><div class="wr60 right redr"><p title="">590,323</p></div></td><td><div class="wr60 right redr"><p title="">504,903</p></div></td>'+
							 '<td><div class="wr70 right redr"><p title="">560,232</p></div></td><td><div class="wr60 right redr"><p>530,239</p></div></td>'+
                          '<td><div class="wr70"><p>上海世纪大道支行</p></div></td><td><div class="wr80"><p>2013年8月2日</p></div></td>'+
                          '<td><div class="wr60 right redr"><p>0.36%</p></div></td><td><div class="wr60"><p>2年</p></div></td></tr>'+
                          '<tr class="bg2">'+
                         ' <td><div class="wr60"><p title="">2</p></div></td><td><div class="wr80"><p title="">839232718274378</p></div></td>'+
							 '<td><div class="wr70"><p title="">定期存款户</p></div></td><td><div class="wr70"><p title="">人民币</p></div></td>'+
							 '<td><div class="wr50 right redr"><p title="">2,000,000</p></div></td><td><div class="wr60 right redr"><p title="">2,000,000</p></div></td>'+
							 '<td><div class="wr70 right redr"><p title="">2,,000,000</p></div></td><td><div class="wr60 right redr"><p>2,000,000</p></div></td>'+
                          '<td><div class="wr70"><p>上海世纪大道支行</p></div></td><td><div class="wr80"><p>2013年9月9日</p></div></td>'+
                          '<td><div class="wr60 right redr"><p>2.20%</p></div></td><td><div class="wr60"><p>2年</p></div></td>'+
                          '</tr>'+
                          '</tbody>'+
                          '</table>'+
							'<div><a href="#"><img src="chart/but_4.jpg" ></img></a>&nbsp&nbsp'+
							'<a href="#"><img src="chart/but_5.jpg" ></img></a></div>'+
                          '</div>'+
                          '</div>'+
                          '</div>'
				});	
	var YeWuR = new Wlj.widgets.search.tile.Tile({
					ownerW : tileWidth,
					ownerH : 10,
					position : 'absolute',
					removeable : false,
					pos_size : {
						TX : 0,
						TY : 0,
						TW : tileWidth,
						TH : 1
					},
					items : [YeWuT1]
				});	
						var YeWuR1 = new Wlj.widgets.search.tile.Tile({
					ownerW : tileWidth,
					ownerH : 10,
					position : 'absolute',
					removeable : false,
					pos_size : {
						TX : 0,
						TY : 2.5,
						TW : tileWidth,
						TH : 1
					},
					items : [YeWuT2]
				});	
			panelYeWu.add(YeWuR);
			panelYeWu.add(YeWuR1);
			panelYeWu.setHeight(127*10);
			panelYeWu.doLayout();
/************************************************************/	
	var view = new Ext.Viewport({
		layout:'form',
		style : {
			overflowY:'scroll'
		},
		items : [panel1,panel,panelInfo,panelGx,panelYeWu,panelLiCai]
	});
	
	
});