Ext.onReady(function(){
	window._APP = new Wlj.search.App();
    //初始化dwr
    //dwr.engine.setActiveReverseAjax(true);  //在web.xml配置无效只好在此配置
    /**
     * 接受推送信息的函数
     * @param {} userId 员工号
     * @param {} msg 推送信息
     */
    /*window._showServerMsg = function(args){
        if(args.reciveUserId&&args.reciveUserId=='ALL'){
        	var autoHide = args.autoHide;
        	if(!autoHide||autoHide==false||autoHide=="false"){
        	   autoHide = false;
        	}else{
        	   autoHide = 1*autoHide;
        	}
        	new Com.yucheng.bcrm.common.TipsWindow({
                title : '提示',
                autoHide : autoHide,
                html : args.pushMsg
        	}).show();
        }
    }*/
});