Ext.ns('Wlj.view');
/**
 * 视图工厂
 * @class Wlj.view.View
 * @extends Ext.util.Observable
 */
Wlj.view.View = Ext.extend(Ext.util.Observable,{
	viewId: 'view$-$',
	resId : 0,
	viewType: 0, //视图类型：0客户视图,1客户群视图,2集团客户视图,3客户经理视图,4callreport视图,6连接callreport
	viewUrl: '',
	constructor: function(config){
        Wlj.view.View.superclass.constructor.call(this, config);
    },
    /**
     * 打开视图方法
     * @param {} viewType 视图类型: 0客户视图,1客户群视图,2集团客户视图,3客户经理视图,4个人账户变更申请表
     * @param {} id	客户号或客户群号
     * @param {} name 客户名称或客户群名称
     * @param {} type 客户类型
     */
	openViewWindow : function(viewType,id,name,type,params){
		this.viewType = viewType;
		this.resId = this.viewId + this.viewType +'$-$' + id+'$-$'+type;
		_APP.taskBar.openWindow({
			name : Wlj.view.View.VIEW_PRE_NAME[this.viewType] + name,
			action : basepath + Wlj.view.View.VIEW_BASE_URL[this.viewType],
			resId : this.resId,
			id : 'task_'+this.resId,
			serviceObject : false,
			params : params
		});
	}
});

Wlj.view.ViewController = new Wlj.view.View();
Wlj.ViewMgr = Wlj.view.ViewController;

Wlj.view.View.VIEW_BASE_URL = [
	'/contents/frameControllers/view/Wlj-custview-base.jsp',
	'/contents/frameControllers/view/Wlj-custgroup-base.jsp',
	'/contents/frameControllers/view/Wlj-custgroup-base.jsp',
	'/contents/frameControllers/view/Wlj-custgroup-base.jsp',
	'/contents/frameControllers/view/Wlj-applyview-base.jsp',
	'/contents/frameControllers/view/Wlj-callreportview-base.jsp',
	//2018-02-02崔恒薇新增：客户经理名单下发-连接callreport页面
	'/contents/frameControllers/view/Wlj-conncallreportview-base.jsp'
	
];
Wlj.view.View.VIEW_PRE_NAME = [
	'客户：',
	'客户群：',
	'集团：',
	'客户经理：',
	'客户：',
	'callreport',
	'连接callreport'
];