Ext.onReady(function(){

	
	var leftTreeForShow = new Ext.tree.TreePanel({//左边的树
		title:'金融工具箱',
		width:220,
		autoScroll:true,
		root: new Ext.tree.AsyncTreeNode({//树的定义
			id:'root',
			expanded:true,
			text:'金融工具箱',
			autoScroll:true,
			children:[{
				id:'dep',
				expanded:true,
				text:'存款类',
				autoScroll:true,
				children:[{
					text:'整存零取计数器',
					leaf:true,
					listeners:{
						click:function(){
							jumpToCon('zhengcunlingqu_hel.jsp');
						}
					}
				},{
					text:'整存整取计算器',
					leaf:true,
					listeners:{
						click:function(){
							jumpToCon('zhengcunzhengqu_hel.jsp');
						}
					}
				},{
					text:'零存整取计算器',
					leaf:true,
					listeners:{
						click:function(){
							jumpToCon('lingcunzhengqu_hel.jsp');
						}
					}
				},{
					text:'通知存款计算器',
					leaf:true,
					listeners:{
						click:function(){
							jumpToCon('tongzhichunkuan_hel.jsp');
						}
					}
				},{
					text:'定活两便计算器',
					leaf:true,
					listeners:{
						click:function(){
							jumpToCon('dinghuoliangbian_hel.jsp');
						}
					}
				},{
					text:'存本取息计算器',
					leaf:true,
					listeners:{
						click:function(){
							jumpToCon('cunbenquxi_hel.jsp');
						}
					}
				}]
			},{
				id:'cre',
				expanded:true,
				text:'贷款类',
				autoScroll:true,
				children:[{
					text:'个人贷款计算器',
					leaf:true,
					listeners:{
						click:function(){
							jumpToCon('daikuan_hel.jsp');
						}
					}
				},{
					text:'提前还款计算器',
					leaf:true,
					listeners:{
						click:function(){
							jumpToCon('tiqianhuankuan_hel.jsp');
						}
					}
				}]
			},{
				id:'fund',
				expanded:true,
				text:'基金类',
				autoScroll:true,
				children:[{
					text:'基金申（认）购费用计算器',
					leaf:true,
					listeners:{
						click:function(){
							jumpToCon('fundSGRG_hel.jsp');
						}
					}
				},{
					text:'基金赎回费用计算器',
					leaf:true,
					listeners:{
						click:function(){
							jumpToCon('fundSH_hel.jsp');
						}
					}
				},{
					text:'基金收益费用计算器',
					leaf:true,
					listeners:{
						click:function(){
							jumpToCon('fundSY_hel.jsp');
						}
					}
				}]
			},{
				id:'bond',
				expanded:true,
				text:'债券类',
				autoScroll:true,
				children:[{
					text:'国债买卖计算器',
					leaf:true,
					listeners:{
						click:function(){
							jumpToCon('bondGzBuy_hel.jsp');
						}
					}
				},{
					text:'国债收益计算器',
					leaf:true,
					listeners:{
						click:function(){
							jumpToCon('bondGzWin_hel.jsp');
						}
					}
				}]
//			},{
//				id:'lilu',
//				expanded:true,
//				text:'利率查询',
//				autoScroll:true,
//				children:[{
//					text:'人民币存款利率',
//					leaf:true,
//					listeners:{
//						click:function(){
//							jumpToCon('renmingbicunkuan_hel.jsp');
//						}
//					}
//				},{
//					text:'人民币贷款利率',
//					leaf:true,
//					listeners:{
//						click:function(){
//							jumpToCon('renmingbidaikuan_hel.jsp');
//						}
//					}
//				}]
			}
				
//				},{
//					text:'债券到期收益率计算器',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('zhaijiandaoqushouyilu.jsp');
//						}
//					}
//				},{
//					text:'债券认购收益率计算器',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('zhaijianrengoushouyilu.jsp');
//						}
//					}
//				},{
//					text:'债券买卖比较器',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('zhaijianmaimaibijiaoqu.jsp');
//						}
//					}
//				},{
//					text:'开基赎回金额计算器',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('kaijishuhuijijin.jsp');
//						}
//					}
//				},{
//					text:'开放式基金申购计算器',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('kaifangshijijinshengou.jsp');
//						}
//					}
//				},{
//					text:'封基投资损益计算器',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('fengzhitouzhishunyi.jsp');
//						}
//					}
//				},{
//					text:'股票交易手续费计算器',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('gupiaojiaoyishoushufei.jsp');
//						}
//					}
//				},{
//					text:'商贷和公积金贷款比较器',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('shangdaihegongjijindaikuanbijiaoqi.jsp');
//						}
//					}
				
//				},{
//					text:'购房与租房净资产比较器',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('goufangyufangzhijinzhichan.jsp');
//						}
//					}
//				},{
//					text:'利率变动后还款计算器',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('lilubiandonghouhuankuan.jsp');
//						}
//					}
//				},{
//					text:'外币兑换计算器',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('waibituohuanjisuanqi.jsp');
//						}
//					}
//				},{
//					text:'最佳存款组合计算器',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('zuijiacunkuanzhuhe.jsp');
//						}
//					}
				
//				},{
//					text:'活期储蓄计算器',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('huoqishuxu.jsp');
//						}
//					}
//				},{
//					text:'部分提支与全额提支比较',
//					leaf:true,//标记 是叶子
//					listeners:{
//						click:function(){
//							jumpToCon('bufengtiquyuquanertiqu.jsp');
//						}
//					}
//				}
			]
		 
		}),
		region:'west',
//		collapsible:true,
//		loader:new Ext.tree.TreeLoader(),
		split:true
	});
	
	var conPanel = new Ext.Panel({
        collapsible:true,
//        renderTo: 'pan',
        width:'100%',
        html:'<iframe id="conFrame"  height="100%" frameborder="no" width="100%" src="" scrolling="auto"> </iframe>'
    });

	var view = new Ext.Viewport({//页面展示
		layout:'border',
		items:[
			leftTreeForShow,{
				region:'center',
				layout:'fit',
				items:[conPanel]				
			}
		]
	});	

});