/**
 * 电话接听页面
 * @author luyy
 */
this.serverURL = __serverIP;//服务器URL（根据实际情况配置）
this.MType = 0;//电脑连接哪种设备  0：没有连接设备  1：爬山虎设备  2：话媒设备
this.HMState = 1;//话媒设备状态  1：设备未连接；0：正常连接
this.PSHState = 1;//爬山虎设备状态  1：设备未连接；0：正常连接
this.ifTouch = false; //是否正在通话  
this.ifCalling = false; //是否来电  
this.ifWindow = false; //是否弹出来电提示窗口（其实就是匹配客户动作是否完成）
this.hdlID = -1;//爬山虎句柄号
		
this.phoneNum = '';//保存当前的电话号码
this.CustdataInfo =
	{custId:false,//客户id
	custZhName:false,//客户名称
	custTyp:false,
	ContactInfo:false
	};//保存当前客户信息
this.phoneId = "";//当前通话记录的id
this.sTime = null;//记录通话开始时间
this.eTime = null;//记录通话结束时间
this.recoreTime = "";//记录录音时间
//客户信息部分的参数
this.infoList = '';
this.ifCust = '';
this.custId = '';
this.custName = '';
this.contactId = '';
this.contactName = '';
this.ifContact = '';
this.meg = '';
this.custTyp = '';
this.winMasking = null;

//爬山虎参数定义
var  CODER_ALAW		= 38;
var  CODER_PCM		= 1;
var	 CODER_G729		= 3;
var	 CODER_SPEEX		= 20;
var	 CODER_ULAW		= 100;
var   UBOX_MODE_RECORD	= 0;		//录音模式， 通常使用的模式
var	  UBOX_MODE_DIAG		= 1;		//诊断模式， 用于捕获线路信息，供信号分析之用，支持的语音编码方式是CODER_PCM
var	  UBOX_MODE_CONFIG	= 2;		//配置模式，
var     UBOX_STATE_RESET     = 1;			//复位态，表示既非振铃也非摘机的状态。如果此前为振铃态，则此状态表示振铃已停止，如果此前为摘机态，则此状态表示已挂机。
var		UBOX_STATE_RINGING   = 2;			//振铃态，表示已检测到线路振铃信号，如果振铃停止，则将触发 UBOX_EVENT_RESET 事件，并汇报 UBOX_STATE_RESET 状态。
var		UBOX_STATE_HOOK_OFF  = 3;			//摘机态，
var		UBOX_STATE_HANG		 = 4;			  //悬空态，
var     UBOX_STATE_IDLE     = 5;
var		UBOX_STATE_REVERSE_HOOKOFF = 6;     //反向摘机，指软件摘机
var		UBOX_STATE_POSITIVIE_HOOKOFF = 7;   //正向摘机，指软件摘机


//日期格式化
Date.prototype.format =function(format)
	{
		var o = {
		"M+" : this.getMonth()+1, //month
		"d+" : this.getDate(), //day
		"h+" : this.getHours(), //hour
		"m+" : this.getMinutes(), //minute
		"s+" : this.getSeconds(), //second
		"q+" : Math.floor((this.getMonth()+3)/3), //quarter
		"S" : this.getMilliseconds() //millisecond
		};
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
};


//性别
var sexstore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=DEM0100005'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});
//类型
var typstore = new Ext.data.Store({
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=XD000080'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var induCodeStore = new Ext.data.Store({//所属单位行业的store
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
		url :basepath+'/lookup.json?name=PAR2100001'
	}),	
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var entScaleStore = new Ext.data.Store({//企业规模store
	restful:true,   
	autoLoad :true,
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=DEM0200004'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var telStore = new Ext.data.Store({//通话类型
	restful:true,   
	autoLoad :true,
	sortInfo: {
    	field: 'key',
    	direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=TEL_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var bsiStore = new Ext.data.Store({//业务类型
	restful:true,   
	autoLoad :true,
	sortInfo: {
    	field: 'key',
    	direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=BIS_TYPE'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});

var followStore = new Ext.data.Store({//后续处理
	restful:true,   
	autoLoad :true,
	sortInfo: {
    	field: 'key',
    	direction: 'ASC' // or 'DESC' (case sensitive for local sorting)
	},
	proxy : new Ext.data.HttpProxy({
			url :basepath+'/lookup.json?name=FOLLOW_DO'
	}),
	reader : new Ext.data.JsonReader({
		root : 'JSON'
	}, [ 'key', 'value' ])
});


/**
 * 计时器处理
 */

var currentTime = '0时0分0秒';
var timePanel = new Ext.Panel({
	html:'<div id ="timeInfo"><font size="5" color="red">通话计时：'+currentTime+'</div>'
});
var theTime = 0;//计时的秒数
var timeCicle = 0;
var ifShowTime = false;
//计时方法
function timer(){
	theTime = theTime+1;//加一秒
	
	var theTime0 = parseInt(theTime);// 秒 
	var theTime1 = 0;// 分 
	var theTime2 = 0;// 小时 
	if(theTime0 > 60) { 
	theTime1 = parseInt(theTime0/60); 
	theTime0 = parseInt(theTime0%60); 
	if(theTime1 > 60) { 
	theTime2 = parseInt(theTime1/60); 
	theTime1 = parseInt(theTime1%60); 
	} 
	} 
	currentTime = theTime2+"时"+theTime1+'分'+theTime0+'秒';
	if(ifShowTime)
		document.getElementById('timeInfo').innerHTML = '<font size="5" color="red">通话计时：'+currentTime;
	
}
//开始计时
function startTime(){
	theTime = 0;
	currentTime = '0时0分0秒';
	timeCicle = setInterval(timer,1000);
}
//停止计时
function stopTime(){
	clearInterval(timeCicle);
}


//进入本页面的初始化过程
function initdata(CustdataInfo,phoneId){
	
//对私客户基本信息
var ncFormIndiv = new Ext.form.FormPanel({
	labelWidth : 90,                       // 标签宽度
	frame : true,                          // 是否渲染表单面板背景色
	labelAlign : 'middle',                 // 标签对齐方式
	items: [
    	  {
        items :[
        	{  
        		 layout:'column',
                 items:[{
                     columnWidth:.28,
                     layout: 'form',
                     items: [
                 	 {
                         xtype:'textfield',
                         fieldLabel: '客户姓名',
                         name: 'custZhName',
                         labelStyle:'text-align:right;',
                         anchor:'90%'
                     }, {
                         xtype:'textfield',
                         fieldLabel: '客户号',
                         labelStyle:'text-align:right;',
                         name: 'custId',
                         anchor:'90%'
                     }, {
                         	xtype:'textfield',
                         	fieldLabel:  '证件号码',
                         	labelStyle:'text-align:right;',
                         	name:'certNum',
                         	anchor:'90%'
                     }	
                     ]
                 },{
                     columnWidth:.28,
                     layout: 'form',
                     items: [new Ext.form.ComboBox({
							hiddenName : 'sex',
							fieldLabel : '性别',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							name:'sex',
							store : sexstore,
							displayField : 'value',
							valueField : 'key',
							mode : 'local',
							forceSelection : true,
							typeAhead : true,
							emptyText:'请选择',
							resizable : true,
							anchor : '90%'
						}),  new Ext.form.ComboBox({
							hiddenName : 'custTyp',
							fieldLabel : '客户类型',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							name:'custTyp',
							store : typstore,
							displayField : 'value',
							valueField : 'key',
							mode : 'local',
							forceSelection : true,
							typeAhead : true,
							emptyText:'请选择',
							resizable : true,
							anchor : '90%'
						}),	{
                             xtype:'textfield',
                             fieldLabel: '单位名称',
                             labelStyle:'text-align:right;',
                             name: 'workUnit',
                             anchor:'90%'
                         }
                    ]}, {
                         columnWidth:.28,
                         layout: 'form',
                         items: [{
	                             xtype:'datefield',
	                             fieldLabel: '出生日期',
	                             name: 'birthday',
	                             labelStyle:'text-align:right;',
	                             format:'Y-m-d',
	                             anchor:'90%'
                             },	{xtype : 'combo',
                            	 store:induCodeStore,
                            	 resizable : true,
                            	 name : 'induCode',
                            	 hiddenName : 'induCode', 
                            	 fieldLabel :'所属单位行业',
                            	 valueField : 'key',
                            	 displayField : 'value',
                            	 mode : 'local',
                            	 editable : false,
						   		 typeAhead : true,
						   		 forceSelection : true,
						   		 triggerAction : 'all',
						   		 emptyText : '请选择',
						   		 labelStyle:'text-align:right;',
						   		 selectOnFocus : true,
						   		 anchor : '90%'},	{
	                             xtype:'textfield',
	                             fieldLabel: '备注',
	                             labelStyle:'text-align:right;',
	                             name: 'remark',
	                             anchor:'90%'
                             }                        
                         ]
                 }, {
                     columnWidth:.16,
                     layout: 'form',
                     items : [{
							xtype : 'button',
							text : '转对公',
							width : 60,
							hidden:CustdataInfo.ContactInfo?false:true,
							style : {
								marginLeft : '10px',// 距左边宽度
								marginRight : '10px'// 距右边宽度
							},
							handler : function() {

				        		var contactId = ContactInfo.split('#')[1];//关联对公客户
								var contactName = ContactInfo.split('#')[2];
								//修改记录
								Ext.Ajax.request({
									url : basepath + '/ocrmFMmTelMain!saveData.json',
									params : {
									'operate':'updateCust',//表示修改为对公客户
									phoneId:phoneId,
									custId:contactId
									},
									method : 'POST',
									waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
									success : function() {
										//关闭当前窗口，用新的客户号打开新窗口
										phoneWindow.hide();
										CustdataInfo.custId=contactId;
						        		CustdataInfo.custZhName=contactName;
						        		CustdataInfo.custTyp='2';
						        		CustdataInfo.ContactInfo = false;
										initdata(CustdataInfo,phoneId);
									},
									failure : function(response) {
										Ext.Msg.alert('提示', '为对公客户失败');
									}
								});
				        	
							}
						}]
                     }
        ]}
        ]
        }           
        ]
});

//对公客户基本信息
var ncFormCom = new Ext.form.FormPanel({
	labelWidth : 90,                       // 标签宽度
	frame : true,                          // 是否渲染表单面板背景色
	labelAlign : 'middle',                 // 标签对齐方式
	items: [
    	  {
        items :[
        	{  
        		 layout:'column',
                 items:[{
                     columnWidth:.33,
                     layout: 'form',
                     items: [
                 	 {
                         xtype:'textfield',
                         fieldLabel: '客户姓名',
                         labelStyle:'text-align:right;',
                         name: 'custZhName',
                         anchor:'80%'
                     }, {
                         xtype:'textfield',
                         fieldLabel: '客户号',
                         labelStyle:'text-align:right;',
                         name: 'custId',
                         anchor:'80%'
                     }, {
                         	xtype:'textfield',
                         	fieldLabel:  '客户法人姓名',
                         	labelStyle:'text-align:right;',
                         	name:'entMaster',
                         	anchor:'80%'
                     }	
                     ]
                 } ,{
                     columnWidth:.33,
                     layout: 'form',
                     items: [{
                             xtype:'textfield',
                             fieldLabel: '资产总额',
                             labelStyle:'text-align:right;',
                             name: 'entAssets',
                             anchor:'80%'
                         },  new Ext.form.ComboBox({
							hiddenName : 'custTyp',
							fieldLabel : '客户类型',
							labelStyle: 'text-align:right;',
							triggerAction : 'all',
							name:'custTyp',
							store : typstore,
							displayField : 'value',
							valueField : 'key',
							mode : 'local',
							forceSelection : true,
							typeAhead : true,
							emptyText:'请选择',
							resizable : true,
							anchor : '80%'
						}),{ xtype : 'combo',
                        	 store:entScaleStore,
                        	 resizable : true,
                        	 name : 'entScale',
                        	 hiddenName : 'entScale', 
                        	 fieldLabel :'企业规模',
                        	 valueField : 'key',
                        	 displayField : 'value',
                        	 mode : 'local',
                        	 editable : false,
						     typeAhead : true,
						     forceSelection : true,
						     triggerAction : 'all',
						     emptyText : '请选择',
						     labelStyle:'text-align:right;',
						     selectOnFocus : true,
						     anchor : '80%'}
                    ]}
                 ,{
                         columnWidth:.34,
                         layout: 'form',
                         items: [{  xtype:'textfield',
                        	 fieldLabel:'注册地址',
                        	 name:'entRegAddr',
                        	 labelStyle:'text-align:right;',
                        	 anchor:'80%'
                        	 },{
	                             xtype:'textfield',
	                             fieldLabel: '经营范围',
	                             labelStyle:'text-align:right;',
	                             name: 'busiRage',
	                             anchor:'80%'
                             },	{
	                             xtype:'textfield',
	                             fieldLabel: '备注',
	                             labelStyle:'text-align:right;',
	                             name: 'remark',
	                             anchor:'80%'
                             }                        
                         ]
                 }
        ]}
        ]
        }           
        ]
});



//对私客户信息
var storeIndiv = new Ext.data.Store( {
	  restful:true,
	  proxy : new Ext.data.HttpProxy({url:basepath+'/custIndiv.json'
	  }),
	  reader: new Ext.data.JsonReader({
		  totalProperty : 'json.count',
		  root:'json.data'
	  }, [{name: 'custZhName', mapping: 'CUST_ZH_NAME'},
	      {name: 'custId', mapping: 'CUST_ID'},
	      {name: 'certNum', mapping: 'CERT_NUM'},
	      {name: 'sex', mapping: 'SEX'},
	      {name: 'custTyp', mapping: 'CUST_TYP'},
	      {name: 'workUnit', mapping: 'WORK_UNIT'},
	      {name: 'birthday', mapping: 'BIRTHDAY'},
	      {name: 'induCode', mapping: 'INDU_CODE'},
	      {name: 'remark', mapping: 'REMARK'}
	      
	      ])
});

//对公客户信息
var storeCom = new Ext.data.Store( {
	  restful:true,
	  proxy : new Ext.data.HttpProxy({url:basepath+'/custCom.json'
	  }),
	  reader: new Ext.data.JsonReader({
		  totalProperty : 'json.count',
		  root:'json.data'
	  }, [{name: 'custZhName', mapping: 'CUST_ZH_NAME'},
	      {name: 'custId', mapping: 'CUST_ID'},
	      {name: 'entMaster', mapping: 'ENT_MASTER'},
	      {name: 'entAssets', mapping: 'ENT_ASSETS'},
	      {name: 'custTyp', mapping: 'CUST_TYP'},
	      {name: 'entScale', mapping: 'ENT_SCALE'},
	      {name: 'entRegAddr', mapping: 'ENT_REG_ADDR'},
	      {name: 'busiRage', mapping: 'BUSIRAGE'},
	      {name: 'remark', mapping: 'REMARK'}
	      ])
});

//通话记录部分**********
var recordStore = new Ext.data.Store( {
	  restful:true,
	  proxy : new Ext.data.HttpProxy({url:basepath+'/ocrmFMmTelMain.json'
	  }),
	  reader: new Ext.data.JsonReader({
		  totalProperty : 'json.count',
		  root:'json.data'
	  }, [{name: 'id', mapping: 'ID'},
	      {name: 'userId', mapping: 'USER_ID'},
	      {name: 'userName', mapping: 'USER_NAME'},
	      {name: 'startTime', mapping: 'TIME_S'},
	      {name: 'endTime', mapping: 'TIME_E'},
	      {name: 'timeLong', mapping: 'TIME_LONG'},
	      {name: 'bsiTypeName', mapping: 'BIS_TYPE_NAME'},
	      {name:'physicalAddress',mapping:'PHYSICAL_ADDRESS'}
	      ])
});

 var recordcm =  new Ext.grid.ColumnModel([
                               	        {header : '流水号',dataIndex : 'id',sortable : true,width : 150},
                               	        {header : '开始时间',dataIndex : 'startTime',sortable : true,width : 150},
                               	        {header : '结束时间',dataIndex : 'endTime',sortable : true,width : 150},
                               	        {header : '录音时长',dataIndex : 'timeLong',sortable : true,width : 150},
                               	        {header : '业务类型',dataIndex : 'bsiTypeName',sortable : true,width : 150},
                               	        {header : '通话客户经理',dataIndex : 'userName',sortable : true,width : 150},
                               	        {header:'physicalAddress',dataIndex:'physicalAddress',hidden:true},
                               	        {header : '操作',dataIndex : '',sortable : true,width : 150,renderer:function(v,p,record){
                               	        	var nl = record.data.physicalAddress;
                               	        	if(nl==null||nl=='')
                               	        		return  '<font color="red"><b>暂无录音</b></font>';
                               	        	else
                               	        		return '<font color="green"><b>双击播放录音</b></font>'; 
                               	        	}}
                            			]);
 var phonePagesize_combo = new Ext.form.ComboBox({
     name : 'pagesize',
     triggerAction : 'all',
     mode : 'local',
     store : new Ext.data.ArrayStore({
         fields : ['value', 'text'],
         data : [ [ 10, '10条/页' ], [ 20, '20条/页' ], [ 50, '50条/页' ],
				[ 100, '100条/页' ], [ 250, '250条/页' ],
				[ 500, '500条/页' ] ]
     }),
     valueField : 'value',
     displayField : 'text',
     value : '20',
     editable : false,
     width : 85
 });
 var phoneNumber = parseInt(phonePagesize_combo.getValue());
 phonePagesize_combo.on("select", function(comboBox) {
	 phoneBar.pageSize = parseInt(phonePagesize_combo.getValue()),
	 recordStore.load({
				params : {
					start : 0,
					limit : parseInt(phonePagesize_combo.getValue())
				}
			});
});
 var phoneBar = new Ext.PagingToolbar({
     pageSize : phoneNumber,
     store : recordStore,
     displayInfo : true,
     displayMsg : '显示{0}条到{1}条,共{2}条',
     emptyMsg : "没有符合条件的记录",
     items : ['-', '&nbsp;&nbsp;', phonePagesize_combo]
 });
 var recordGrid = new Ext.grid.GridPanel({
	 	layout:'fit',
		frame : true,
		autoScroll : true,
		store : recordStore, // 数据存储
		stripeRows : true, // 斑马线
		cm : recordcm, // 列模型
		bbar:phoneBar,
		viewConfig:{
			   forceFit:false,
			   autoScroll:true
			},
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
		});
 var ifNeedDel = false;
 
//双击播放录音
 recordGrid.on('rowdblclick', function(recordGrid, rowIndex, event) {
		var selectRe = recordGrid.getSelectionModel().getSelections()[0];
		if (selectRe == null|| selectRe == "undefined") {
			Ext.Msg.alert('提示','请选择一条记录!');
		} else if(selectRe.data.physicalAddress==''||selectRe.data.physicalAddress==null){
			Ext.Msg.alert('提示','该次通话没有录音文件!');
		}else {
			if(selectRe.data.physicalAddress.indexOf('.wav')!=-1){//wav播放
				ifNeedDel = true;
				//复制音频文件
				Ext.Ajax.request({
					url : basepath + '/ocrmFMmTelMain!copyFile.json',
					params : {
					'file':selectRe.data.physicalAddress
					},
					success : function(response) {
						var exit = 'no';//文件是否存在
						var file = selectRe.data.physicalAddress;//文件名
						exit =  response.responseText;
						if(exit=='no'){
							Ext.Msg.alert('提示', '音频文件已转存，如需访问请联系科技人员，文件名：'+file);
						}else
						 window.open( basepath+'/contents/pages/customer/customerManager/mktPhone/'+selectRe.data.physicalAddress,''
				    			 , 'height=100, width=200, top=300, left=500, toolbar=no,menubar=no, scrollbars=no, resizable=no,location=no, status=no');
					},
					failure : function(response) {
						Ext.Msg.alert('提示', '获取录音文件失败');
					}
				});
			}
			else{//mp3播放
				ifNeedDel = false;
				var playerWindow = new Ext.Window({
					title : '播放录音',
					closeAction : 'close',
					constrain : true,
					modal : true,
					width : 325,
					height : 160,
					draggable : true,
					layout : 'fit',
					html:'<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" id="Mp3WavPlayer" width="100%" height="100%" codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">'
						+'<param name="movie" value="'+basepath+'/flex-debug/Mp3WavPlayer.swf" />'
						+'<param name="quality" value="high" />'
						+'<param name="wmode" value="opaque" />'
						+'<param name="bgcolor" value="#ffffff" />'
						+'<param name="allowScriptAccess" value="sameDomain" />'
						+'<param name="flashVars" value="basepath='+basepath+'&busiId='+basepath+'/flexHandlerAction!playRecorder.json?filepath='+__upFile+'/'+selectRe.data.physicalAddress+'"/>'
						+'<embed src="'+basepath+'/flex-debug/Mp3WavPlayer.swf" quality="high" bgcolor="#ffffff"' 
						+'	width="100%" height="100%" name="Mp3WavPlayer" align="middle" flashVars="basepath='+basepath+'&busiId='+basepath+'/flexHandlerAction!playRecorder.json?filepath='+__upFile+'/'+selectRe.data.physicalAddress+'" '
						+'	play="true" loop="false" quality="high" allowScriptAccess="sameDomain" type="application/x-shockwave-flash"'
						+'	pluginspage="http://www.adobe.com/go/getflashplayer">'
						+'</embed>'
						+'</object>'
				});
				playerWindow.show();
			}
	    	 
		}
	});

//通话记录部分**********

	
	//电话号码展示部分
	var phonePanel = new Ext.Panel({
		height: 100,
		width:'100%',
		frame : true,            
		layout:'form',
		items:[timePanel,new Ext.Panel({
			layout:'form',
			items:[{
				xtype:'textfield',
				fieldLabel:'电话号码',
				value:phoneNum,
				disabled:true
			}]
		})],
		buttonAlign:'center'
//     	,buttons:[{
//     		text:'重播',
//     		hidden:MType==1?true:false,
//     		handler:function(){
//     			PhoneOcx.PutDown();
//     			PhoneOcx.PickUp();
//     			PhoneOcx.TelSleep(700);
//     			PhoneOcx.DialNmbCStr(phoneNum);
//				//记录新通话时间
//				sTime = new Date();
//				eTime = null;//记录通话结束时间
//				PhoneOcx.StartReadSoundCStrF("C:\\PhoneOcx.wav");//默认开始录音
//				ifTouch = true;
//				startTime();
//     		}
//     	},{
//     		text:'挂机',
//     		hidden:MType==1?true:false,
//     		handler:function(){
//     			HMphoneOff();
//     		}
//     	}]
	});
	//通话内容panel
	var recordPanel = new  Ext.form.FormPanel({
		labelWidth : 90,                       // 标签宽度
		frame : true,                          // 是否渲染表单面板背景色
		labelAlign : 'middle',                 // 标签对齐方式
		items: [{
	        items :[
	            	{  
	            		 layout:'column',
	                     items:[{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [new Ext.form.ComboBox({
	    							hiddenName : 'telType',
	    							fieldLabel : '通话类型',
	    							labelStyle: 'text-align:right;',
	    							triggerAction : 'all',
	    							name:'telType',
	    							store : telStore,
	    							displayField : 'value',
	    							valueField : 'key',
	    							mode : 'local',
	    							forceSelection : true,
	    							allowBlank:false,
	    							typeAhead : true,
	    							emptyText:'请选择',
	    							resizable : true,
	    							anchor : '100%',
	    							listeners:{
	    								'select':function(){
	    									bsiStore.proxy.conn.url = basepath + '/lookup.json?name='+this.getValue();;
	    									bsiStore.load();
	    								}
	    							}
	    						})]
	                     },{
	                         columnWidth:.33,
	                         layout: 'form',
	                         items: [new Ext.form.ComboBox({
	    							hiddenName : 'bisType',
	    							fieldLabel : '业务类型',
	    							labelStyle: 'text-align:right;',
	    							triggerAction : 'all',
	    							name:'bisType',
	    							store : bsiStore,
	    							displayField : 'value',
	    							valueField : 'key',
	    							mode : 'local',
	    							forceSelection : true,
	    							typeAhead : true,
	    							emptyText:'请选择',
	    							allowBlank:false,
	    							resizable : true,
	    							anchor : '100%'
	    						})]}, {
	                             columnWidth:.34,
	                             layout: 'form',
	                             items: [new Ext.form.ComboBox({
		    							hiddenName : 'followDo',
		    							fieldLabel : '后续处理',
		    							labelStyle: 'text-align:right;',
		    							triggerAction : 'all',
		    							name:'followDo',
		    							store : followStore,
		    							displayField : 'value',
		    							valueField : 'key',
		    							mode : 'local',
		    							forceSelection : true,
		    							allowBlank:false,
		    							typeAhead : true,
		    							emptyText:'请选择',
		    							resizable : true,
		    							anchor : '100%'
		    						})]},
		    						{
			                             columnWidth:1,
			                             layout: 'form',
			                             items: [{
			                            	 xtype:'textarea',
			                            	 name:'content',
			                            	 fieldLabel : '业务处理备注',
			             					 anchor : '100%'
			                             }]}
	            ]}]} ],
	            buttonAlign:'center',
	        	buttons:[{
	        		text:'保存',
	        		handler: function(){
	   	        	 if (!recordPanel.getForm().isValid()) {
	   	        		 Ext.MessageBox.alert('系统提示信息', '请正确输入各项必要信息！');
	   	        		 return false;
	   	        	 }
	   	        	Ext.Ajax.request({
						url : basepath + '/ocrmFMmTelMain!saveData.json',
						params : {
						'operate':'update',//表示修改
						phoneId:phoneId
						},
						method : 'POST',
						form : recordPanel.getForm().id,
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '保存成功');
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '新增通话记录失败');
						}
					});
	   	        	 
	        		}
	        	}]
	    });
	//客户视图链接
	var url = basepath+"/contents/pages/customer/customerManager/mktPhone/CommonCustomerView.jsp?resId="+__resId
	+"&custId="+CustdataInfo.custId
	+"&custName="+CustdataInfo.custZhName
	+"&custTyp="+CustdataInfo.custTyp;
	
	var url1 = basepath+"/contents/pages/customer/customerManager/customerBaseInformation/productAdvise1.jsp?custid="+CustdataInfo.custId;
	
	var url2 = basepath+"/contents/pages/workSpace/workCalendar/workCalendar.jsp?custName="+CustdataInfo.custZhName+"&phoneNum="+phoneNum;

var tabmain = new Ext.TabPanel({
id:'tabmain',
activeTab: 3,
frame:true,
autoScroll:true,
defaults:{autoHeight: true},
items:[
    { title: '客户视图',items:[{
				columnWidth : .55,
				items : [{
				region: 'center',
				width:'100%',
				collapsible: false,
				items:{
					collapsible: false,
					height:350,
					html:'<iframe id="reporter-iframes" src='+url+' width="100%" height="100%" name="main"  frameborder="0" scrolling="auto"></iframe>'
				}
			}]}]},
    { title: '通话记录',items:[{
				columnWidth : .55,
				items : [{
				layout:'fit',
				region: 'center',
				width:'100%',
				height:350,
				collapsible: false,
				items:[recordGrid]
			}]}]},
    { title: '推荐产品',items:[{
		columnWidth : .55,
		items : [{
		region: 'east',
		width:'100%',
		collapsible: false,
		height:340,
		html:'<iframe id="reporter-iframes" src='+url1+' width="100%" height="100%" name="main"  frameborder="0" scrolling="auto"></iframe>'
	}]}]},
    { title: '通话概要',items:[{
				columnWidth : .55,
				items : [{
				region: 'east',
				width:'100%',
				collapsible: false,
				height:350,
				items:[recordPanel]
			}]}]},
    { title: '日程安排',items:[{
				columnWidth : .55,
				items : [{
				region: 'east',
				width:'100%',
				collapsible: false,
				height:350,
				html:'<iframe id="reporter-iframes" src="'+basepath+'/contents/pages/workSpace/calendarManager/schedulePlanIndex.jsp" width="100%" height="100%" name="main"  frameborder="0" scrolling="auto"></iframe>'
			}]}]}
],
listeners:{
	'tabchange':function(){
		if (tabmain.getActiveTab().title == '通话记录') {
			recordStore.reload();
		}
	}
}
});

//布局模型
var phoneWindow = new top.Ext.Window({
		title : '电话营销',
		closeAction:'hide',
		constrain:true,
//		maximized:true,									//默认最大化
		modal:true,
		width:1150,
		height:550,
		draggable : true,
		 layout:'fit',
		    items:[{
					layout : 'border',
					items: [{   
						region: 'north',
					    id: 'north-panels', 
					    height: 100,
					    hidden:false,
					    layout:"column",
						items:[{
							columnWidth:.7 ,
							items:[ncFormIndiv,ncFormCom]
							},{columnWidth:.3 ,
							items:[{
								 height: 100,
								 width:document.body.scrollWidth*.3,
							items:[phonePanel]
						}]}]
				    } ,{   
						region: 'center',
					    id: 'center-panels',
					    height:document.body.scrollHeight-100,
					    hidden:false,
						items:[tabmain]
				    }]
		    }],
    buttonAlign:'center',
    buttons:[
    {
    	text:'关闭页面',
    	handler:function()
    	{
    		phoneWindow.hide();
    	}
    }
    ]
	});

	//关闭窗口时删除文件
	phoneWindow.on('hide', function() {
		ifShowTime = false;
		if(ifNeedDel){
			Ext.Ajax.request({
				url : basepath + '/ocrmFMmTelMain!deleteFile.json',
				params : {
					'file':'PHONE'+phoneId+'.wav'
				},
				success : function(response) {
//					Ext.Msg.alert('提示', '成功');
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '删除临时文件失败');
				}
			});
		}
		
	});
	
    phoneWindow.show();
    document.getElementById('timeInfo').innerHTML = '<font size="5" color="red">通话计时：'+currentTime;
    ifShowTime = true;
    
//数据初始化********************	
    recordStore.on('beforeload', function() {
        this.baseParams = {
                "custId":CustdataInfo.custId
        };
	});
    recordStore.reload();
    
	if(CustdataInfo.custTyp=='1'){//对私客户
		ncFormCom.hide();
		ncFormIndiv.show();
		storeIndiv.on('beforeload', function() {
            this.baseParams = {
                    "custId":CustdataInfo.custId
            };
		});
		storeIndiv.load({
			  callback : function(){
				  if(storeIndiv.getCount()!=0){
					  ncFormIndiv.getForm().loadRecord(storeIndiv.getAt(0));
		        	}
			  }});
		
	}else if(CustdataInfo.custTyp=='2'){//对公客户
		ncFormCom.show();
		ncFormIndiv.hide();
		storeCom.on('beforeload', function() {
            this.baseParams = {
                    "custId":CustdataInfo.custId
            };
		});
		storeCom.load({
			  callback : function(){
				  if(storeCom.getCount()!=0){
					  ncFormCom.getForm().loadRecord(storeCom.getAt(0));
		        	}
			  }});
	}
	//数据初始化********************	
}
