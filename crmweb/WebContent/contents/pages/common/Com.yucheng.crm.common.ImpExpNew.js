Ext.ns('Com.yucheng.crm.common');
/**
 * 导入查询控件，用于批量导入查询
 * @class Com.yucheng.crm.common.ImportSearchManager
 * @extends Ext.util.Observable
 */
Com.yucheng.crm.common.ImportSearchManager = Ext.extend(Ext.util.Observable,{
	/**
	 * 是否是导入查询
	 * @type Boolean true:是导入查询，false：导入
	 */
	impSearch   : true,
	/**
	 * 导出Excel第一行表头配置
	 * 例：{text: '核心客户号',name: 'CORE_NO'},{text : '性别',name:'SEX',translateType:'XD000209'},{text: '校验信息',name:'IMP_MSG'}
	 * @type Array
	 */
	fields : [],
	/**
	 * 导入模板名称
	 * @type String
	 */
	templateFile   : null,
	/**
	 * 导入交易码
	 * @type 
	 */
    tradeCode   : null,
    /**
     * 错误信息导出地址
     * @type String
     */
    url : null,
    /**
     * 错误导出地址
     * @type String
     */
    expUrl : null,
    /**
     * 导出错误信息时调用的方法,默认值为export
     * @type String
     */
    exportMethod    : 'exportFalse',
    /**
     * 导出模板时，打开的新的下载窗口的配置信息
     * @type String
     */
    winPara : 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no',
    /**
     * 等待进度窗口
     * @type Ext.Window
     */
    progressWin : null,
    progressBar : null,
    /**
     * 导入窗口
     * @type Ext.Window
     */
    importWindow  : null,
    /**
     * 导入窗口下的表单面板
     * @type Ext.FormPanel
     */
    importForm  : null,
    /**
     * 预留字段，暂未用上
     * @deprecated
     * @type Boolean
     */
    importState : false,
    constructor: function(config){
        Ext.apply(this, config);
		this.addEvents({
			beforeImport	: true,
			afterImport		: true,
			beforeExport	: true,
			afterExport		: true,
			importException	: true,
			exportException	: true,
			/**
			 * 点击确认查询按钮后触发的事件,在执行查询动作之前触发，如果返回false,则停止查询事件
			 */
			beforeSearch   : true
		});
        //调用我们父类的构造器来完成构造过程。
        Com.yucheng.crm.common.ImportSearchManager.superclass.constructor.call(this);
        this.init();
    },
    init : function(){
    	var me = this;
    	//默认执行export方法，CommonAction里有公共的export方法，但是此方法是针对表格导出的，不符合错误导出逻辑，因此需要在业务action里自定义export方法
        if(!this.exportMethod){
          this.exportMethod = 'export';
        }
        if(!me.expUrl){
          me.expUrl = me.url;
        }
        if(me.expUrl.indexOf('!')==-1){
            me.expUrl = me.expUrl.substring(0,me.expUrl.indexOf('.json'))+'!'+me.exportMethod+me.expUrl.substring(me.expUrl.indexOf('.json'));
        }
        if(!this.successMsg){
			  if(this.impSearch){
			  	this.successMsg = "是否查询？";
			  }else{
			  	this.successMsg = "执行成功！";
			  }      
        }
        if(!this.progressWin){
            this.progressWin = new Ext.Window({
				width		: 300,
				hideMode	: 'offsets',
				closable	: true,
				modal		: true,
				autoHeight	: true,
				closeAction	: 'hide',
				items		: []
			});
        };
        // 导入窗口里的表单面板
        if(!this.importWindow){
            var importForm = new Ext.FormPanel({
                height : 200,
                width : '100%',
                title : '文件导入',
                fileUpload : true,
                dataName : 'file',
                frame : true,
                /** 是否显示导入状态 */
                importStateInfo : '',
                importStateMsg : function(state) {
                    var titleArray = [ 'excel数据转化为SQL脚本', '执行SQL脚本...','正在将临时表数据导入到业务表中...', '导入成功！' ];
                    this.importStateInfo = "当前状态为：[<font color='red'>"+ titleArray[state] + "</font>];<br>";
                },
                /** 是否显示 当前excel数据转化为SQL脚本成功记录数 */
                curRecordNumInfo : '',
                curRecordNumMsg : function(o) {
                    importForm.curRecordNumInfo = "当前excel数据转化为SQL脚本成功记录数[<font color='red'>"+ o + "</font>];<br>";
                },
                /** 是否显示 当前sheet页签记录数 */
                curSheetRecordNumInfo : '',
                curSheetRecordNumMsg : function(o) {
                    importForm.curSheetRecordNumInfo = "当前sheet页签记录数：[<font color='red'>"+ o + "</font>];<br>";
                },
                /** 是否显示 当前sheet页签号 */
                sheetNumInfo : '',
                sheetNumMsg : function(o) {
                    importForm.sheetNumInfo = "当前sheet页签号为：[<font color='red'>" + o+ "</font>];<br>";
                },
                /** 是否显示 sheet页签总数 */
                totalSheetNumInfo : '',
                totalSheetNumMsg : function(o) {
                    importForm.totalSheetNumInfo = "sheet页签总数：[<font color='red'>" + o+ "</font>];<br>";
                },
                /** 是否显示 已导入完成sheet数 */
                finishSheetNumInfo : '',
                finishSheetNumMsg : function(o) {
                    importForm.finishSheetNumInfo = "已导入完成sheet数[<font color='red'>" + o+ "</font>];<br>";
                },
                /** 是否显示 已导入完成记录数 */
                finishRecordNumInfo : '',
                finishRecordNumMsg : function(o) {
                    importForm.finishRecordNumInfo = "已导入完成记录数[<font color='red'>" + o+ "</font>];<br>";
                },
                /** 当前excel数据转化为SQL脚本成功记录数 */
                curRecordNum : 0,
                /** 导入总数 */
                totalNum : 1,
                /** 进度条信息 */
                progressBarText : '',
                /** 进度条Msg */
                progressBartitle : '',
                proxyStore : undefined,
                items : [{
                    xtype       : 'textfield',
                    id          : 'file_1',
                    inputType   : 'file',
                    fieldLabel  : '文件名称',
                    anchor      : '90%'
                }],
                /** 进度条 */
                progressBar : null,
                /**
                 * 导入请求成功后调用的函数，主要用于显示服务端当前导入任务的进度信息
                 */
                importSuccessHandler : function(json) {
                    if (json != null) {
                        if (typeof (json.curRecordNum) != 'undefined'&& importForm.curRecordNumMsg) {
                            importForm.curRecordNumMsg(json.curRecordNum);
                            importForm.curRecordNum = json.curRecordNum;
                        }
                        if (typeof (json.importState) != 'undefined'&& importForm.importStateMsg) {
                            importForm.importStateMsg(json.importState);
                        }
                        if (typeof (json.curSheetRecordNum) != 'undefined'&& importForm.curSheetRecordNumMsg) {
                            importForm.curSheetRecordNumMsg(json.curSheetRecordNum);
                        }
                        if (typeof (json.sheetNum) != 'undefined'&& importForm.sheetNumMsg) {
                            importForm.sheetNumMsg(json.sheetNum);
                        }
                        if (typeof (json.totalSheetNum) != 'undefined'&& importForm.totalSheetNumMsg) {
                            importForm.totalSheetNumMsg(json.totalSheetNum);
                        }
                        if (typeof (json.finishSheetNum) != 'undefined'&& importForm.finishSheetNumMsg) {
                            importForm.finishSheetNumMsg(json.finishSheetNum);
                        }
                        if (typeof (json.finishRecordNum) != 'undefined'&& importForm.finishRecordNumMsg) {
                            importForm.finishRecordNumMsg(json.finishRecordNum);
                        }
                    } else {
                        importForm.curRecordNumInfo = '';
                        importForm.importStateInfo = '';
                        importForm.curSheetRecordNumInfo = '';
                        importForm.sheetNumInfo = '';
                        importForm.totalSheetNumInfo = '';
                        importForm.finishSheetNumInfo = '';
                        importForm.finishRecordNumInfo = '';
                    }
        
                    importForm.progressBartitle = '';
                    /** 进度条Msg信息配置：各信息项目显示内容由各自方法配置 */
                    importForm.progressBartitle += importForm.curRecordNumMsg ? importForm.curRecordNumInfo: '';
                    importForm.progressBartitle += importForm.importStateMsg ? importForm.importStateInfo: '';
                    importForm.progressBartitle += importForm.curSheetRecordNumMsg ? importForm.curSheetRecordNumInfo: '';
                    importForm.progressBartitle += importForm.sheetNumMsg ? importForm.sheetNumInfo: '';
                    importForm.progressBartitle += importForm.totalSheetNumMsg ? importForm.totalSheetNumInfo: '';
                    importForm.progressBartitle += importForm.finishSheetNumMsg ? importForm.finishSheetNumInfo: '';
                    importForm.progressBartitle += importForm.finishRecordNumMsg ? importForm.finishRecordNumInfo: '';
                    me.showProgressBar(importForm.totalNum, importForm.curRecordNum,importForm.progressBarText, importForm.progressBartitle,"上传成功，正在导入数据，请稍候");
                },
                buttons : [ {
                    text : '导入文件',
                    handler : function() {
                        var proxyStorePS = importForm.proxyStore;
                        var proxyHttpPH = importForm.proxyHttp;
                        if (me.tradeCode == undefined || me.tradeCode == '') {
                            Ext.MessageBox.alert('Debugging！','You forgot to define the tradeCode for the import form!');
                            return false;
                        }
                        var impRefreshHandler = 0;
                        if (importForm.getForm().isValid()) {
                            //导入文件的名称
                            var fileName = importForm.getForm().findField("file_1").getValue();
                            var fileType = fileName.substring(fileName.lastIndexOf('.'));
                            //根据文件后缀校验文件类型
                            if (fileType == '.xls' || fileType == '.XLS'|| fileType == '.xlsx' || fileType == '.XLSX') {
                                me.importWindow.hide();
                            } else {
                                Ext.MessageBox.alert("文件错误", "导入文件不是XLS或XLSX文件。");
                                return false;
                            }
                            me.showProgressBar(1, 0, '', '', '正在上传文件...');
                            //提交表单，上传需要导入的文件到服务器的导入临时文件目录，并返回服务器上的文件的文件名
                            importForm.getForm().submit({
                                url : basepath+ '/FileUpload?isImport=true',
                                success : function(form, o) {
                                    //服务器上的 文件名称
                                    var _tempImpFileName = Ext.decode(o.response.responseText).realFileName;
                                    //导入请求所需参数
                                    var condi = {
                                        'filename'  : _tempImpFileName,//文件名称
                                        //导入交易吗，用于根据ImportTradeDefine.xml里的配置，在数据成功进入临时表后调用校验类进行校验操作
                                        'tradecode' : me.tradeCode
                                    };
                                    //上传文件成功后进行导入操作,将Excel数据导入到临时表
                                    Ext.Ajax.request({
                                        url : basepath+ "/ImportAction.json",
                                        method : 'GET',
                                        params : {
                                            "condition" : Ext.encode(condi)
                                        },
                                        success : function(response, opts) {
                                            //显示进度窗口，首次调用默认刚开始，还未结束，因此没有详细信息，直接传null即可
                                            importForm.importSuccessHandler(null);
                                            //定时任务内触发的函数，每隔1000毫秒，即1秒发送一次请求去查询目前导入任务的进度，直到导入任务成功结束才会取消定时任务
                                            var importFresh = function() {
                                                //发送请求，获取导入任务当前进度信息
                                                Ext.Ajax.request({
                                                    url : basepath+ "/ImportAction!refresh.json",
                                                    method : 'GET',
                                                    success : function(a) {
                                                        //http请求响应的状态为200或201，请求成功
                                                        if (a.status == '200'|| a.status == '201') {
                                                            var res = Ext.decode(a.responseText);
                                                            //开始判断导入任务的状态，成功：200;导入进行中：999;导入 失败：900
                                                            if (res.json.result != undefined&& res.json.result == '200') {
                                                                //导入任务处理成功，清除定时器
                                                                window.clearInterval(impRefreshHandler);
                                                                if (res.json.BACK_IMPORT_ERROR&& res.json.BACK_IMPORT_ERROR == 'FILE_ERROR') {
                                                                    Ext.Msg.alert("提示","导出文件格式有误，请下载导入模版。");
                                                                    return;
                                                                }
                                                                //如果配置了代理store,则加载代理store数据
                                                                if (proxyStorePS != undefined) {
                                                                    proxyStorePS.load({
                                                                        params : {
                                                                            pkHead : res.json.PK_HEAD
                                                                        }
                                                                    });
                                                                } else {
                                                                    //显示导入进度信息
                                                                    importForm.importSuccessHandler(res.json);
                                                                    me.showSuccessWinImp(res.json);// 导入数据条数
                                                                }
                                                            } else if (res.json.result != undefined&& res.json.result == '900') {
                                                                //导入存在错误，清除定时器
                                                                window.clearInterval(impRefreshHandler);
                                                                if(res.json.BACK_RUN_ERROR==true){
                                                                    Ext.Msg.alert("导入失败","失败原因：\n"+ res.json.BACK_IMPORT_ERROR);
                                                                }else{
                                                                    me.showFailuerWinImp(res.json);
                                                                }
                                                            } else if (res.json.result != undefined&& res.json.result == '999') {
                                                                //导入任务还在进行中，显示当前 导入进度状态，等待定时任务继续查询
                                                                importForm.importSuccessHandler(res.json);
                                                            }
                                                        }
                                                    }
                                                });
                                            };
                                            impRefreshHandler = window.setInterval(importFresh,1000);
                                        },
                                        failure : function() {
                                        }
                                    });
                                },
                                failure : function(form, o) {
                                    Ext.Msg.show({
                                        title : 'Result',
                                        msg : '数据文件上传失败，请稍后重试!',
                                        buttons : Ext.Msg.OK,
                                        icon : Ext.Msg.ERROR
                                    });
                                }
                            });
                        }
                    }
                } ]
            });
            this.importForm = importForm;
            this.importWindow = new Ext.Window({
                width       : 700,
                hideMode    : 'offsets',
                modal       : false,
                height      : 250,
                closeAction : 'hide',
                items       : [importForm]
            });
        }
    },
    /**
     * 等待进度条
     * @param count int 总数据条数
     * @param curnum int 已处理数据条数
     * @param progressBarText String 进度条信息
     * @param progressBarTitle String 进度条标题信息
     * @param title String 等待窗口标题
     */
    showProgressBar : function(count, curnum, progressBarText, progressBarTitle, title) {
    	var me = this;
    	var progressBar = new Ext.ProgressBar({
                width : 285
            });
        this.progressBar = progressBar;
        me.progressBar.wait({
            interval : 200, // 每次更新的间隔周期
            duration : 5000, // 进度条运作时候的长度，单位是毫秒
            increment : 5, // 进度条每次更新的幅度大小，默示走完一轮要几次（默认为10）。
            fn : function() { // 当进度条完成主动更新后履行的回调函数。该函数没有参数。
                me.progressBar.reset();
            }
        });
        this.progressWin.removeAll();
        this.progressWin.setTitle(title);
        if (progressBarTitle.length == 0) {
            progressBarTitle = '正在处理，请稍后。。。';
        }
        progressBarText = progressBarText || progressBarTitle;
        var importContext = new Ext.Panel({
            title       : '',
            frame       : true,
            region      : 'center',
            height      : 100,
            width       : '100%',
            autoScroll  : true,
            html        : '<span>' + progressBarText + '</span>'
        });
        this.progressWin.add(importContext);
        this.progressWin.add(progressBar);
        this.progressWin.doLayout();
        this.progressWin.show();
    },
    /**
     * 导入成功后显示的导入查询窗口
     * @param json Object
     * @param json[curRecordNum] String 当前导入成功的条数
     */
    showSuccessWinImp   : function(json) {
    	var me = this;
        var curRecordNum = json.curRecordNum;
        this.progressWin.removeAll();
        this.progressWin.setTitle("成功导入记录数为[" + curRecordNum + "]");
        var left = (300-me.successMsg.length*20)/2;
        this.progressWin.add(new Ext.Panel({
            title : '',
            width : 300,
            layout : 'fit',
            autoHeight : true,
            bodyStyle : 'text-align:center',
            html : '<div style="width:128px;height:128px;"><span style="position:relative;top:57px;left:'+left+'px;font-size:20px;">'+me.successMsg+'</span></div>',
            buttonAlign : 'center',
            buttons : [{
                text : me.impSearch ? '确认查询':'确定',
                handler : function(){
                    var result = me.fireEvent("beforeSearch");
                    if(result!=false){
                        me.progressWin.hide();//因此当前窗口
                    }
                }
            }]
        }));
        this.progressWin.doLayout();
        this.progressWin.show();
        this.progressWin.doLayout();
    },
    /**
     * 服务端导出任务导出成功后显示的窗口，用于下载服务端已生成的导出文件
     * @param curRecordNum int 当前已处理条数
     * @param filePath String 服务端生成的导出文件名称
     */
    showSuccessWinExp   : function(curRecordNum,filePath) {
        var me = this;
        this.progressWin.removeAll();
        this.progressWin.setTitle("成功导出记录数为["+curRecordNum+"]");
        this.progressWin.add(new Ext.Panel({
            title:'',
            width:210,
            layout : 'fit',
            autoHeight : true,
            bodyStyle:'text-align:center',
            html: '<img src="'+basepath+'/contents/img/UltraMix55.gif" />',
            buttonAlign: 'center',
            buttons: [
                new Ext.Button({
                  text:'下载',
                  handler:function(){
                      window.location.href = filePath;
                  }
                })]
        }));
        this.progressWin.doLayout();
        this.progressWin.show();
    },
    /**
     * 导入失败后显示的导出窗口,可以点击导出按钮导出错误的数据
     * @param json Object
     * @param json[pkHead] String 导入标识
     * @param json[msg] String 导入失败信息
     */
    showFailuerWinImp   : function(json) {
    	var me = this;
        var pkHead = json.pkHead;
        var msg = json.msg;
        //importState = false;
        this.progressWin.removeAll();//清除提示窗口里的所有内容
        this.progressWin.setTitle(msg||"导入失败，请点击导出按钮导出错误数据");//设置提示窗口标题围 错误信息
        this.progressWin.add(new Ext.Panel({
            title : '',
            width : 300,
            layout : 'fit',
            autoHeight : true,
            bodyStyle : 'text-align:center',
            html : '<img src="' + basepath + '/contents/img/UltraMix55.gif" />',
            buttonAlign : 'center',
            buttons : [{
                text    : '导出',
                url : me.url,
                params  : {},//参数
                handler : function(){
                    //导出Excel的第一行标题栏配置
                    var fieldArray = me.fields||[];
                    var fieldMap = {};
                    var translateMap = {};//记录需要转换数据字典的类型
                    Ext.each(fieldArray,function(item){
                        var header = "";
                        var mapping = "";
                        //配置了name属性，并且不隐藏
                        if(item.name&&item.hidden!=false){
                            if(item.text&&item.text!="NO"){
                                header = item.text;
                                mapping = item.name;
                                //处理数据字典转换，默认转换后的数据的key为原来的key后面加"_ORA"
                                if(item.translateType){
                                    mapping = item.name + '_ORA';
                                    //记录需要转换的类型
                                    translateMap[item.name] = item.translateType;
                                }
                                //记录key与中文对应关系
                                fieldMap[mapping]=header;
                            }
                        }
                    });
                    var expPar = {
                        'fieldMap'  : Ext.encode(fieldMap),
                        'translateMap'  : Ext.encode(translateMap),
                        'pkHead'   : pkHead,
                        'menuId'    : __resId
                    };
                    /**
                     * 带上自定义参数
                     */
                    Ext.apply(expPar,me.params);
                    if(me.url=='#'){
                        /**
                         * For debug.
                         */
                        Ext.MessageBox.alert('Debugging！','You forgot to define the url for the ExpButton!');
                    }
                    var refreshHandler = 0;
                    //进度title
                    var progressBarTitle = '正在导出数据...';
                    //当前导出进度信息
                    var getMsg = function(expRecNum, total) {
                        return '当前导出'+expRecNum+'条；<br>共导出'+total+'条。';
                    };
                    /**
                     * 执行导出请求
                     */
                    Ext.Ajax.request({
                        url:me.expUrl,
                        method:'POST',
                        params:expPar,
                        success:function(a,b){
                            me.showProgressBar(1,0,'','正在导出，请稍后...','导出文件');
                            //生产查询导出状态的请求地址，默认执行refresh方法，此方法在CommonAction里定义
                            var refreshUrl = b.url.replace(me.exportMethod,'refresh');
                            //定时器，每隔1秒去后台查询导出任务是否已经结束
                            var freshFish = function(){
                                Ext.Ajax.request({
                                    url:refreshUrl,
                                    method:'GET',
                                    success:function(a){
                                        //http请求响应的状态为200或201，请求成功
                                        if(a.status == '200' || a.status=='201'){
                                            var res = Ext.decode(a.responseText);
                                            //返回了服务段生成的导出文件名，即导出任务已经执行 完成
                                            if(res.json.filename){
                                                //服务端导出成功，隐藏导出等待窗口
                                                //me.progressWin.hide();
                                                //显示导出窗口，清除定时器
                                                me.showSuccessWinExp(res.json.expRecNum,basepath+'/FileDownload?filename='+res.json.filename);
                                                if(refreshHandler != 0){
                                                    window.clearInterval(refreshHandler);
                                                }
                                            } else {//导出任务还没有执行完，继续等待，定时器任务继续刷新
                                                if ((typeof res.json.expRecNum) != 'undefined' && (typeof res.json.total) != 'undefined') {
                                                    //显示当前导出任务进度窗口
                                                    me.showProgressBar(res.json.total,res.json.expRecNum,'',getMsg(res.json.expRecNum,res.json.total),progressBarTitle);
                                                } else {
                                                     //me.progressBar.hide();
                                                     Ext.MessageBox.alert('提示！','下载进度信息获取失败!');
                                                }
                                            }
                                        } 
                                    }
                                });
                            };
                            refreshHandler = window.setInterval(freshFish, 1000);
                        },
                        failure:function(a,b){
                            Ext.MessageBox.alert('失败！','请等待当前下载任务完成!');
                        }
                    });
                }
            }]
        }));
        this.progressWin.doLayout();
        this.progressWin.show();
    },
    /**
     * 模板下载
     * @param fileName String 模板名称
     */
    downloadTemplate    : function(){
        var uploadUrl = basepath + '/TempDownload?filename=' + this.templateFile;
        window.open(uploadUrl, '', this.winPara);
    },
    /**
     * 导入查询
     */
    importSearch  : function() {
        var fileName = this.importForm.getForm().findField("file_1");
        if(fileName){
            fileName.setValue('');
        }
        this.importWindow.show();
    }
});

/*
 * 表格导出按钮,不使用浏览器插件
 */
Com.yucheng.crm.common.NewExcelButton = Ext.extend(Ext.Button,{
    url:'#',
    exParams : false,//新增数据参数，当设定的条件form为空的，则调用此属性作为导出数据查询条件
    backgroundExport : false,//是否后台导出方式：true是为后台导出方式，false为原版方式(前台导出)
    text:'导出',
    handler:function(){
        var fieldArray = window.CUSTVIEW.grid.store.fields.items || this.ownerCt.ownerCt.store.fields.items;
        var fieldMap = {};
        var translateMap = {};
        Ext.each(fieldArray,function(item){
            var header = "";
            var mapping = "";
            if(item.name!=undefined&&item.name!=""&&(item.hidden==undefined||!item.hidden)){
                if(item.text!=undefined&&item.text!=""&&item.text!="NO"
                    &&(item.gridField==undefined||item.gridField)){
                    header = item.text;
                    mapping = item.name;
                    if(item.translateType && item.translateType != ""){
                    	mapping = item.name + '_ORA';
                    	translateMap[item.name] = item.translateType;
                    }
                    fieldMap[mapping]=header;
                    if(mapping!=undefined&&mapping!=""){
                        fieldMap[mapping]=header;
                    }else{
                    	fieldMap[item.name]=header;
                    }
                }
            }
        });
        var tmpFormPanel;
        var conditionString;
        var requestParams = {};
        
        var fieldMapString = Ext.encode(fieldMap);
        var expPar = {};
        expPar.fieldMap = fieldMapString;
        expPar.translateMap = Ext.encode(translateMap);
//        conditionString = Ext.encode(_app.searchDomain.items.items[0].getForm().getFieldValues());
        if(conditionString!=undefined){
        	expPar.condition = conditionString;
        	expPar.menuId = __resId;
        }
        Ext.apply(expPar,this.exParams);
        
        if(this.url=='#'){
            /**
             * For debug.
             */
            Ext.MessageBox.alert('Debugging！','You forgot to define the url for the ExpButton!');
        }
        var refreshHandler = 0;
        var backgroundExport = this.backgroundExport;
        var expUrl = this.url.replace('.json','!export.json');
        
        //进度title
        var progressBarTitle = '正在导出数据...';
        //当前导出进度信息
        var getMsg = function(expRecNum, total) {
        	return '当前导出'+expRecNum+'条；<br>共导出'+total+'条。';
        };      
        Ext.Ajax.request({
            url:expUrl,
            method:'POST',
            params:expPar,
            success:function(a,b){
        		if (backgroundExport){
        			Ext.MessageBox.alert('提示！','下载启动成功，请在下载中心下导出文件!');
        		} else {
        			showProgressBar(1,0,'','正在导出，请稍后...','导出文件');
                    var refreshUrl = b.url.replace('export','refresh');
                    var freshFish = function(){
                        Ext.Ajax.request({
                            url:refreshUrl,
                            method:'GET',
                            success:function(a){
                                if(a.status == '200' || a.status=='201'){
                                    var res = Ext.util.JSON.decode(a.responseText);
                                    if(res.json.filename){
                                    	showSuccessWin(res.json.expRecNum,basepath+'/FileDownload?filename='+res.json.filename);
                                        if(refreshHandler != 0){
                                            window.clearInterval(refreshHandler);
                                        }
                                    } else {
                                    	var res = Ext.util.JSON.decode(a.responseText);
                                    	//Ext.MessageBox.hide();
                                    	if ((typeof res.json.expRecNum) != 'undefined' && (typeof res.json.total) != 'undefined') {
                                    		showProgressBar(res.json.total,res.json.expRecNum,'',getMsg(res.json.expRecNum,res.json.total),progressBarTitle);
                                    	} else {
                                    		 progressBar.hide();
                                             Ext.MessageBox.alert('提示！','下载进度信息获取失败!');
                                    	}
                                    }
                                } 
                            }
                        });
                    };
                    refreshHandler = window.setInterval(freshFish, 1000);
        		}
            },
            failure:function(a,b){
            	if (!backgroundExport){progressBar.hide();}
                Ext.MessageBox.alert('失败！','请等待当前下载任务完成!');
            }
        });
    }
});

Ext.reg('crm.newexcelbutton', Com.yucheng.crm.common.NewExcelButton);

Com.yucheng.crm.common.NewExpButton = Ext.extend(Ext.Button,{
    url:'#',
    exParams : false,//新增数据参数，当设定的条件form为空的，则调用此属性作为导出数据查询条件
    backgroundExport : false,//是否后台导出方式：true是为后台导出方式，false为原版方式(前台导出)
    text:'导出',
    handler:function(){
        var fieldArray = this.ownerCt.ownerCt.store.fields.items;
        var fieldMap = {};
        var translateMap = {};
        Ext.each(fieldArray,function(item){
            var header = "";
            var mapping = "";
            if(item.name!=undefined&&item.name!=""&&(item.hidden==undefined||!item.hidden)){
                if(item.text!=undefined&&item.text!=""&&item.text!="NO"
                    &&(item.gridField==undefined||item.gridField)){
                    header = item.text;
                    mapping = item.name;
                    if(item.translateType && item.translateType != ""){
                    	mapping = item.name + '_ORA';
                    	translateMap[item.name] = item.translateType;
                    }
                    fieldMap[mapping]=header;
                    if(mapping!=undefined&&mapping!=""){
                        fieldMap[mapping]=header;
                    }else{
                    	fieldMap[item.name]=header;
                    }
                }
            }
        });
        var tmpFormPanel;
        var conditionString;
        var requestParams = {};
        
        var fieldMapString = Ext.encode(fieldMap);
        var expPar = {};
        expPar.fieldMap = fieldMapString;
        expPar.translateMap = Ext.encode(translateMap);
        conditionString = Ext.encode(_app.searchDomain.items.items[0].getForm().getFieldValues());
        if(conditionString!=undefined){
        	expPar.condition = conditionString;
        	expPar.menuId = __resId;
        }
        Ext.apply(expPar,this.exParams);
        
        if(this.url=='#'){
            /**
             * For debug.
             */
            Ext.MessageBox.alert('Debugging！','You forgot to define the url for the ExpButton!');
        }
        var refreshHandler = 0;
        var backgroundExport = this.backgroundExport;
        var expUrl = this.url.replace('.json','!export.json');
        
        //进度title
        var progressBarTitle = '正在导出数据...';
        //当前导出进度信息
        var getMsg = function(expRecNum, total) {
        	return '当前导出'+expRecNum+'条；<br>共导出'+total+'条。';
        };      
        Ext.Ajax.request({
            url:expUrl,
            method:'POST',
            params:expPar,
            success:function(a,b){
        		if (backgroundExport){
        			Ext.MessageBox.alert('提示！','下载启动成功，请在下载中心下导出文件!');
        		} else {
        			showProgressBar(1,0,'','正在导出，请稍后...','导出文件');
                    var refreshUrl = b.url.replace('export','refresh');
                    var freshFish = function(){
                        Ext.Ajax.request({
                            url:refreshUrl,
                            method:'GET',
                            success:function(a){
                                if(a.status == '200' || a.status=='201'){
                                    var res = Ext.util.JSON.decode(a.responseText);
                                    if(res.json.filename){
                                    	showSuccessWin(res.json.expRecNum,basepath+'/FileDownload?filename='+res.json.filename);
                                        if(refreshHandler != 0){
                                            window.clearInterval(refreshHandler);
                                        }
                                    } else {
                                    	var res = Ext.util.JSON.decode(a.responseText);
                                    	//Ext.MessageBox.hide();
                                    	if ((typeof res.json.expRecNum) != 'undefined' && (typeof res.json.total) != 'undefined') {
                                    		showProgressBar(res.json.total,res.json.expRecNum,'',getMsg(res.json.expRecNum,res.json.total),progressBarTitle);
                                    	} else {
                                    		 progressBar.hide();
                                             Ext.MessageBox.alert('提示！','下载进度信息获取失败!');
                                    	}
                                    }
                                } 
                            }
                        });
                    };
                    refreshHandler = window.setInterval(freshFish, 1000);
        		}
            },
            failure:function(a,b){
            	if (!backgroundExport){progressBar.hide();}
                Ext.MessageBox.alert('失败！','请等待当前下载任务完成!');
            }
        });
    }
});

Ext.reg('crm.expbutton', Com.yucheng.crm.common.NewExpButton);
//Ext.reg('bob.excelbutton', Com.yucheng.bob.ExcelButton);
/**继承commonAction时用到的导出功能**********************************************************************/

var idTmr  =  "";
/**
 * grid : expGrid
 */
Com.yucheng.crm.common.ExcelButton = Ext.extend(Ext.Button,{
	expGrid : false,
	text:'导出',
	handler: function(){
		if(this.expGrid != false){
			  var cm = this.ownerCt.ownerCt.getColumnModel();   
			  var store = this.ownerCt.ownerCt.getStore();   
			     
			  var it = store.data.items;   
			  var rows = it.length;   
			     
			  var   oXL   =   new   ActiveXObject("Excel.application");        
			  var   oWB   =   oXL.Workbooks.Add();        
			  var   oSheet   =   oWB.ActiveSheet;    
			     
			  for (var i = 2; i < cm.getColumnCount(); i++) {   
			      
			   if (!cm.isHidden(i)) {   
			    oSheet.Cells(1, i - 1).value = cm.getColumnHeader(i);   
			   }   
			      
			   for (var j = 0; j < rows; j++) {
				   if(cm.getDataIndex(i) != ''){
					   r = it[j].data;   
					    var v = r[cm.getDataIndex(i)];  
//					    var fld = store.recordType.prototype.fields.get(cm.getDataIndex(i));   
					    var fld = cm.config[i];
					    if(null != v){
					    	if("object"==typeof(v) && v.time != undefined)//日期类型   
						    {
						    	v = new Date(v.time);
						    }
					    	if("NaN" != parseInt(v))//数字类型  前加转义字符" \t"
					    	{
					    		v = "\t"+v;
					    	}
					    	if(cm.config[i].type != undefined){
						    	if(cm.config[i].type == 'mapping'){//mapping类型
						    		for(var r = 0; r < cm.config[i].store.data.items.length; r++){
						    			var mappingCode = cm.config[i].store.data.items[r].data.code;
						    			var mappingName = cm.config[i].store.data.items[r].data.name;
						    			if ( parseInt(mappingCode) == parseInt(v) ){
						    				v = mappingName;
						    			}
						    		}
							    }
					    	}
					    }
					    oSheet.Cells(2 + j, i - 1).value = v;
				   }
			   }   
			  }   
			  oXL.DisplayAlerts = false;   
			  oXL.Save();   
			  oXL.DisplayAlerts = true;                       
			  oXL.Quit();   
			  oXL = null;   
			  idTmr = window.setInterval("Cleanup();",1);   
			  
		}else{
			Ext.MessageBox.alert('Debugging！','Please enter the expGrid params!');
		}
	}
});
function Cleanup() {   
    window.clearInterval(idTmr);   
    CollectGarbage();   
}  

/********************************************************************************/


/**
 * A file upload form panel.
 * That's a beautiful panel.
 * Maybe I still need to create a upload field object.
 */
Com.yucheng.crm.common.UpLoadPanel = Ext.extend(Ext.FormPanel,{
    title:'文件上传',
    fileUpload : true, 
    dataName:'file',
    frame:true,
    relaId:'',/**关联数据ID*/
    modinfo:'notice',/**modinfo: notice:公告;*/
   /** upField : new Ext.form.FileUploadField({
        emptyText : '请选择一个文件......' ,
        fieldLabel : '附件' ,
        name : this.dataName ,
        buttonText : '选择文件',
        labelWidth : 50,
        width : '100%'
    }),*/
    upField : new Ext.form.TextField({
        name:'annexeName',
        inputType:'file',
        fieldLabel : '附件名称',
        anchor : '90%'
    }),
    onRender : function(ct, position){
        this.add(this.upField);
        Com.yucheng.crm.common.UpLoadPanel.superclass.onRender.call(this, ct, position);
    },
    buttons : [{
        text : '导入',
        handler : function() {
            var mods = this.ownerCt.ownerCt.modinfo;
            var reins = this.ownerCt.ownerCt.relaId;
            if(mods==undefined ||mods==''){
                Ext.MessageBox.alert('Debugging！','You forgot to define the modinfo for the upload form!');
                return false;
            }
            if (this.ownerCt.ownerCt.getForm().isValid()){
                this.ownerCt.ownerCt.ownerCt.hide();
                this.ownerCt.ownerCt.getForm().submit({
                    url : basepath + '/FileUpload',
                    success : function(form,o){
                        var fileName =form.items.items[0].getValue();
                        var simpleFileName = fileName.substring(12,fileName.length);
                        Ext.Ajax.request({
                            url:basepath+'/workplatannexe.json',
                            method:'POST',
                            params: {
                                relationInfo : reins,
                                annexeName : simpleFileName,
                                relationMod : mods
                            },
                            success : function(a,b){},
                            failure : function(a,b){}
                        });
                        
                        Ext.Ajax.request({
                            url: basepath+'/UploadStatus',
                            method:'GET',
                            success:function(response,d){
                                Ext.Msg.show({
                                    title : '上传成功',
                                    msg : response.responseText,
                                    buttons : Ext.Msg.OK,
                                    icon : Ext.Msg.INFO
                                });
                                Ext.Ajax.request({
                                    url:basepath+'/workplatannexe.json?relationInfo='+reins,
                                    method : 'GET',
                                    failure : function(){
                                        Ext.MessageBox.alert('查询异常', '查询失败！');
                                    },
                                    success : function(response){
                                        var anaExeArray = Ext.util.JSON.decode(response.responseText);
                                        appendixStore.loadData(anaExeArray);
                                        appendixGridPanel.getView().refresh();
                                    }
                                });
                            },
                            failure:function(a,b){
                            }
                        });
                    },
                    failure : function(form, o){
                        Ext.Msg.show({
                            title : 'Result',
                            msg : o.result.error,
                            buttons : Ext.Msg.OK,
                            icon : Ext.Msg.ERROR
                        });
                    }
                });
            }
        }
    }]
});
Ext.reg('crm.uploadform' , Com.yucheng.crm.common.UpLoadPanel);

var progressBar = {};
var importState = false;
var progressWin = new Ext.Window({     
    width : 220,
    hideMode : 'offsets',
    closable : true,
    modal : true,
    autoHeight : true,
    closeAction:'hide',
    items : [],
    listeners :{
		'beforehide': function(){
			return importState;
		}
	}
});
function showProgressBar(count,curnum,bartext,msg,title) {
	importState = false;
	progressBar = new Ext.ProgressBar({width : 205 });
	progressBar.wait({
        interval: 200,          	//每次更新的间隔周期
        duration: 5000,             //进度条运作时候的长度，单位是毫秒
        increment: 5,               //进度条每次更新的幅度大小，默示走完一轮要几次（默认为10）。
        fn: function () {           //当进度条完成主动更新后履行的回调函数。该函数没有参数。
			progressBar.reset();
        }
    });
	progressWin.removeAll();
	progressWin.setTitle(title);
	if (msg.length == 0) {
		msg = '正在导出...';
	}
	var importContext = new Ext.Panel({
								title: '',
								frame : true,
								region :'center',
								height : 100,
								width : '100%',
								autoScroll:true,
								html : '<span>'+ msg +'</span>'
							});
	progressWin.add(importContext);
	progressWin.add(progressBar);
	progressWin.doLayout();
	progressWin.show();
	
}
function showSuccessWin(curRecordNum,filePath) {
	importState = true;
	progressWin.removeAll();
	progressWin.setTitle("成功导出记录数为["+curRecordNum+"]");
	progressWin.add(new Ext.Panel({
		title:'',
		width:210,
		layout : 'fit',
		autoHeight : true,
		bodyStyle:'text-align:center',
		html: '<img src="'+basepath+'/contents/img/UltraMix55.gif" />',
		buttonAlign: 'center',
		buttons: [
			new Ext.Button({
			  text:'下载',
			  handler:function(){
			      window.location.href = filePath;
			  }
			})]
	}));
	progressWin.doLayout();
	progressWin.show();
}
