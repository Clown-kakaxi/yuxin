/**
 * 营销管理->商机管理->销售漏斗图片展示及列表 入口JS文件 wzy，2013-02-20
 */

/** ***************************全局变量定义*********开始**************** */
// 页面布局
var viewport = null;
/** ***************************全局变量定义*********结束**************** */

/** ***************************业务逻辑执行*********开始**************** */
Ext.onReady(function() {

			Ext.QuickTips.init();// 提示信息组件初始化

			viewport = new Ext.Viewport({
						layout : 'fit',
						items : [{
									plain : true,
									resizable : false,
									collapsible : false,
									animateTarget : Ext.getBody(),
									constrain : true,
									layout : 'border',
									items : [qForm, {
												region : 'center',
												layout : 'border',
												items : [funnelPicPanel, grid]
											}]
								}]

					});
		});
/** ***************************业务逻辑执行*********结束**************** */
