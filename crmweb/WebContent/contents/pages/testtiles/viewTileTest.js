(function(){
	/***
	 * 磁贴中url中请将客户id条件值拼接上
	 * 客户ID值为：_custId
	 */
	var tileBox = new Ext.Panel({
		html : '<div style="width:100%;height:100%;" class="tile_c1">'
		 + '<div class="yc-cvtcTitle">'
		 + '<img alt="" src="../../../wljFrontFrame/styles/search/searchthemes/blue/pics/ico/tile/ico_15.png"/>'
		 + '基本信息custId=['+_custId+']'
		 + '</div>'
		 + '<div class="yc-cvtcContent">'
		 + '<div class="yc-cvtcList1" title="XXX">性别：男</div>'
	 	 + '<div class="yc-cvtcList1" title="XXX">户籍地址：上海</div>'
		 + '<div class="yc-cvtcList1" title="XXX">民族：汉族</div>'
		 + '<div class="yc-cvtcList1" title="XXX">政治面貌：党员</div>'
		 + '<div class="yc-cvtcList1" title="XXX">固定电话：010-2342343</div>'
		 + '<div class="yc-cvtcList1" title="XXX">移动电话：13800138764</div>'
		 + '<div class="yc-cvtcList" title="XXX">户籍地址：北京市武侯区万寿桥路68号</div>'
		 + '<div class="yc-cvtcList" title="XXX">现居住地：四川省成都市武侯区双楠少陵路245号 </div>'
		 + '</div>'
		 + '</div>'
	});
	return tileBox;
})();