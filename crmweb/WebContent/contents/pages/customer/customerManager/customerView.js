Ext.onReady(function(){
/*	
	var from = Ext.get("wolegequ");
	
	
	*//**
	 *基本信息弹出
	 *//*
	var baseInfoPanel= new Ext.Window({
				//applyTo:'img1',
				title:'客户基本信息',
                layout:'fit',
                width:document.body.clientWidth-600,
                height:300,
				pageX:600,
				pageY:70,
                closeAction:'hide',
                plain: true,
				//applyTo: 'infoShow',
                items: new Ext.TabPanel({
                    deferredRender:false,
                    border:false,
					html:'<ul style="position:relative;top:20px;list-style:none;"><li>客户姓名：焦向波</li><li>客户编号：12345</li><li>证件类型：身份证</li><li>证件编号：430405198809281053</li><li>出生日期：1985.09.28</li><li>客户住址：北京市东城区地坛北里南区5号楼</li></ul>'
                }),

                buttons: [{
                    text:'Submit', 
                    disabled:true
                },{
                    text: 'Close',
                    handler: function(){
                        baseInfoPanel.hide();
                    }
                }]
	});	
	var div1 = Ext.get('div1');
	div1.addListener('mousemove',function(){baseInfoPanel.show(this);});
	//div1.addListener('mouseout',function(){baseInfoPanel.hide();});
	*//**
	 *归属信息弹出
	 *//*
	var belongInfoPanel= new Ext.Window({
				//applyTo:'img1',
				title:'客户归属信息',
                layout:'fit',
                width:document.body.clientWidth-600,
                height:300,
				pageX:600,
				pageY:70,
                closeAction:'hide',
                plain: true,
				//applyTo: 'infoShow',
                items: new Ext.TabPanel({
                    deferredRender:false,
                    border:false,
					html:'<ul style="position:relative;top:20px;list-style:none;"><li>客户一：余永智</li><li>归属：北京银行总行</li><li>客户二：黄智</li><li>归属：北京银行上海分行</li><li>客户三：杨阳</li><li>归属：北京银行上地支行</li><li>客户四：王彦</li><li>归属：北京银行天津分行</li></ul>'
                }),

                buttons: [{
                    text:'Submit', 
                    disabled:true
                },{
                    text: 'Close',
                    handler: function(){
                        belongInfoPanel.hide();
                    }
                }]
	});	
	var div2 = Ext.get('div2');
	div2.addListener('mousemove',function(){belongInfoPanel.show(this);});
	//div2.addListener('mouseout',function(){belongInfoPanel.hide();});
		*//**
	 *事件信息弹出
	 *//*
	var opInfoPanel= new Ext.Window({
				//applyTo:'img1',
				title:'客户事件信息',
                layout:'fit',
                width:document.body.clientWidth-600,
                height:300,
				pageX:600,
				pageY:70,
                closeAction:'hide',
                plain: true,
				//applyTo: 'infoShow',
                items: new Ext.TabPanel({
                    deferredRender:false,
                    border:false,
					html:'<ul style="position:relative;top:20px;list-style:none;"><li>客户一：焦向波</li><li>操作信息：增加数据</li><li>客户二：余永智</li><li>操作信息：修改数据</li><li>客户三：姚亮</li><li>操作信息：查询数据</li><li>客户四：汪占元</li><li>操作信息：删除数据</li></ul>'
                }),

                buttons: [{
                    text:'Submit', 
                    disabled:true
                },{
                    text: 'Close',
                    handler: function(){
                        opInfoPanel.hide();
                    }
                }]
	});	
	var div3 = Ext.get('div3');
	div3.addListener('mousemove',function(){opInfoPanel.show(this);});
	//div3.addListener('mouseout',function(){opInfoPanel.hide();});
	*//**
	 *联系信息弹出
	 *//*
	var contractInfoPanel= new Ext.Window({
				//applyTo:'img1',
				title:'客户联系信息',
                layout:'fit',
                width:document.body.clientWidth-600,
                height:300,
				pageX:600,
				pageY:70,
                closeAction:'hide',
                plain: true,
				//applyTo: 'infoShow',
                items: new Ext.TabPanel({
                    deferredRender:false,
                    border:false,
					html:' <ul style="position:relative;top:20px;list-style:none;"><li>联系人一：焦向波</li><li>电话：13900102342</li><li>联系人二：陈群</li><li>电话：010-2762389</li><li>联系人 三：姚亮</li><li>电话：15892637743</li><li>联系人四：汪占元</li><li>电话：13882733743</li></ul>'
                }),

                buttons: [{
                    text:'Submit', 
                    disabled:true
                },{
                    text: 'Close',
                    handler: function(){
                        contractInfoPanel.hide();
                    }
                }]
	});	
	var div4 = Ext.get('div4');
	div4.addListener('mousemove',function(){contractInfoPanel.show(this);});
	//div4.addListener('mouseout',function(){contractInfoPanel.hide();});
	*//**
	 *财务信息弹出
	 *//*
	var finInfoPanel= new Ext.Window({
				//applyTo:'img1',
				title:'客户财务信息',
                layout:'fit',
                width:document.body.clientWidth-600,
                height:300,
				pageX:600,
				pageY:70,
                closeAction:'hide',
                plain: true,
				//applyTo: 'infoShow',
                items: new Ext.TabPanel({
                    deferredRender:false,
                    border:false,
					html:'<ul style="position:relative;top:20px;list-style:none;"><li>客户一：焦向波</li><li>金额：1,000,000</li><li>客户二：余永智</li><li>金额：1,500,000</li><li>客户三：姚亮</li><li>金额：2,000,000</li><li>客户四：汪占元</li><li>金额：2,500,000</li></ul>'
                }),

                buttons: [{
                    text:'Submit', 
                    disabled:true
                },{
                    text: 'Close',
                    handler: function(){
                        finInfoPanel.hide();
                    }
                }]
	});	
	var div5 = Ext.get('div5');
	div5.addListener('mousemove',function(){finInfoPanel.show(this);});
	//div5.addListener('mouseout',function(){finInfoPanel.hide();});
	*//**
	 *事实余额信息弹出
	 *//*
	var RMInfoPanel= new Ext.Window({
				//applyTo:'img1',
				title:'实时余额信息',
                layout:'fit',
                width:document.body.clientWidth-600,
                height:300,
				pageX:600,
				pageY:70,
                closeAction:'hide',
                plain: true,
				//applyTo: 'infoShow',
                items: new Ext.TabPanel({
                    deferredRender:false,
                    border:false,
					html:'<table class="mytable"><tr><th style="text-align:center;" width=70px>类别</th><th style="text-align:center;width=70px;">账号</th><th style="text-align:center;"width=90px>余额</th><th style="text-align:center;" width=90;>变化额</th><th style="text-align:center;width:70px;">时间</th></tr><tr><th style="text-align:center;" scope="row">活期</th><td>2593209482940938234</td><td class="money"><span id="mt1" style="display:none;">13,000.00</span></td><td class="money"><span id="mt2" style="display:none;color:#F00">+1,000.00</span></td><td style="text-align:center;"><span id="mt3" style="text-align:center;display:none;">08:00</span></td></tr><tr><th scope="row" style="text-align:center;">活期</th><td>2593209482940938254</td><td class="money"><span id="mt4" style="display:none;">21,000.00</span></td><td class="money"><span id="mt5" style="display:none;color:#00F">-10,000.00</span></td><td style="text-align:center;"><span id="mt6" style="display:none;">08:30</span></td></tr><tr><td colspan="5" style="text-align:center"><span id="biandon" style="display:none;">变动账号共2个</span></td></tr></table>'
                }),

                buttons: [{
                    text:'Submit', 
                    disabled:true
                },{
                    text: 'Close',
                    handler: function(){
                        RMInfoPanel.hide();
                    }
                }]
	});	
	var div6 = Ext.get('div6');
	div6.addListener('mousemove',function(){RMInfoPanel.show(this);});
	//div6.addListener('mouseout',function(){RMInfoPanel.hide();});
	*/
});