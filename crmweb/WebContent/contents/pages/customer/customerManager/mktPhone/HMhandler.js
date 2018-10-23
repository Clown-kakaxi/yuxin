/**
 * 设备话媒的事件相应
 */

var putDownStat = '';//读电话线上摘/挂机状态
var phoneStat='';//读外接电话机状态
var callStat= '';//判断是否有来电号码
var callNum = '';//读来电号码
var ifFirstPhoneUp = true;//来电接听是后保持接听状态，即putDownStat在接听时一直为 1 所以只在第一次判断为1时处理
//HM轮循来电状态
		function getInLineInfo(){
			putDownStat = PhoneOcx.CheckPutDown();//读电话线上摘/挂机状态
			phoneStat=PhoneOcx.ReadPhoneStatus();//读外接电话机状态
			callStat= PhoneOcx.CheckCallStatus();//判断是否有来电号码
			callNum = PhoneOcx.ReadstrCallNmb();//读来电号码
			if(callStat==1){//来电话了
				phoneNum = callNum;
				ifCalling = true;
				//查找匹配客户
				HMcheckNumForCust(phoneNum);
			}
			if(ifCalling&&putDownStat==1){//来电状态，摘机
				if(ifFirstPhoneUp){//第一次判断到摘机时处理
					ifFirstPhoneUp=false;
					HMphoneOn();
				}
			}
			if(ifTouch&&putDownStat==0){//通话状态，挂机
					HMphoneOff();
			}
		}
		

//我行客户通话处理
function dealOwnUser(){
	sTime = null;
	eTime = null;//记录通话结束时间
		//保存通话记录并且返回id
		Ext.Ajax.request({
			url : basepath + '/ocrmFMmTelMain!saveData.json',
			params : {
			'operate':'inadd',//表示呼入新增
			'custId':custId
			},
			method : 'POST',
			waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
			success : function() {
				//获取新id
				Ext.Ajax.request({
			         url: basepath +'/ocrmFMmTelMain!getPid.json',
				         success:function(response){
				        	 phoneId = Ext.util.JSON.decode(response.responseText).pid;
				        	 	PhoneOcx.PickUp();//接听电话
				        	 	PhoneOcx.StartReadSoundCStrF("C:\\PhoneOcx.wav");
				        	 	startTime();
								sTime = null;
								eTime = null;//记录通话结束时间
				        	 	//接听电话页面
				        	 	CustdataInfo.custId=custId;
				        		CustdataInfo.custZhName=custName;
				        		CustdataInfo.custTyp=custTyp;
				        		if(ifContact=='true')
				        			ContactInfo = ifContact+'#'+infoList[5]+'#'+infoList[6];
				        		else
				        			ContactInfo = false;
				        		CustdataInfo.ContactInfo= ContactInfo;
								initdata(CustdataInfo,phoneId);
								winMasking.close();
								
								ifWindow = false;
				         }
				 });
			},
			failure : function(response) {
				Ext.Msg.alert('提示', '新增通话记录失败');
			}
		});
	

}
 
//不是我行客户通话处理
function dealNotUser(){
	sTime = null;
	eTime = null;//记录通话结束时间
		//保存通话记录并且返回id
		Ext.Ajax.request({
			url : basepath + '/ocrmFMmTelMain!saveData.json',
			params : {
			'operate':'inadd',//表示呼入新增
			'custId':custId
			},
			method : 'POST',
			waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
			success : function() {
				//获取新id
				Ext.Ajax.request({
			         url: basepath +'/ocrmFMmTelMain!getPid.json',
				         success:function(response){
				        	 phoneId = Ext.util.JSON.decode(response.responseText).pid;
				        	 	PhoneOcx.PickUp();//接听电话
				        	 	PhoneOcx.StartReadSoundCStrF("C:\\PhoneOcx.wav");
				        	 	startTime();
				        	 	//接听中，选择处理方式
				        	 	var winActionWindow = new top.Ext.Window({
				        	 		title: '接听来电......',  
								    layout: 'fit',  
								    width: 350, 
								    height: 150,  
								    html:meg,
								    buttonAlign:'center',
								    buttons:[{
								    	text:'新建潜在客户',
								    	handler:function(){
								    		newCust(phoneId);
								    		winActionWindow.hide();
								    	}
								    },{
								    	text:'选择已知客户',
								    	handler:function(){
								    		choseCust(phoneId);
								    		winActionWindow.hide();
								    	}
								    }]
				        	 	});
				        	 	winActionWindow.show();
								winMasking.close();
								ifTouch = false;
				         }
				 });
			},
			failure : function(response) {
				Ext.Msg.alert('提示', '新增通话记录失败');
			}
		});
	

}
//根据电话号码匹配客户
	function HMcheckNumForCust(phoneNum){
		Ext.Ajax.request({
			url : basepath + '/ocrmFMmTelMain!checkNum.json',
			params : {
			'num':phoneNum
			},
			success : function(response) {
				var info =  response.responseText;
				infoList = info.split('#');
				ifCust = infoList[0];
				custId = '';
				custName = '';
				contactId = '';
				contactName = '';
				meg = '';
				custTyp = '';
				if(ifCust=='false'){//不是我行客户
					meg = '陌生来电,号码：'+phoneNum;
					winMasking = new top.Ext.Window({  
					    title: '来电信息......',  
					    layout: 'fit',  
					    width: 350, 
					    height: 150,  
					    html:meg,
					    buttonAlign:'center',
					    buttons:[{
					    		text:'接听',
					    		handler:function(){
					    			ifCalling = false;
					    			ifTouch = true;
					    			dealNotUser();
					    		}},
					    		{
						    		text:'挂断',
						    		handler:function(){
						    			PhoneOcx.PickUp();
						    			PhoneOcx.TelSleep(1500);//摘机之后需要一个延时
						    			PhoneOcx.PutDown();
						    			winMasking.close();
						    		}}],
					    resizable: false,   
					    id:'winMask',  
					    modal:true  
					    });  
					
					 winMasking.show(); 
					 
					 ifWindow = true;
				}else{
					var ifIndiv = infoList[1];
					if(ifIndiv=='false'){//对公客户
						custTyp = '2';
						custId = infoList[8];
						custName = infoList[9];
						meg = '对公客户('+custName+')来电,号码：'+phoneNum;
					}else{//对私客户
						custTyp = '1';
						custId = infoList[2];
						custName = infoList[3];
						meg = '零售客户('+custName+')来电,号码：'+phoneNum;
						var ifContact = infoList[4];
						if(ifContact=='true'){
							contactId = infoList[5];
							contactName = infoList[6];
						}
					}
					winMasking = new top.Ext.Window({  
					    title: '来电信息......',  
					    layout: 'fit',  
					    width: 350, 
					    height: 150,  
					    html:meg,
					    buttons:[{
					    		text:'接听',
					    		handler:function(){
					    			ifCalling = false;
					    			ifTouch = true;
					    			dealOwnUser();
					    		}},
					    		{
						    		text:'挂断',
						    		handler:function(){
						    			PhoneOcx.PickUp();
						    			PhoneOcx.TelSleep(1500);
						    			PhoneOcx.PutDown();
						    			winMasking.close();
						    		}}],
					    resizable: false,   
					    id:'winMask',  
					    modal:true  
					    });  
					
					 winMasking.show(); 
					 ifWindow = true;
				}
				
			},
			failure : function(response) {
				Ext.Msg.alert('提示', '根据号码匹配客户失败');
			}
		});
	};
	var HMcicleId = 0;//轮询函数id
	//轮询提示窗口状态
	function HMcicleWinState(){
		if(ifWindow){//已经弹出提示
			HMdoCallThing(HMcicleId);
		}
	};
	//接听电话响应处理
	function HMdoCallThing(HMcicleId){
		clearInterval(HMcicleId);//停止轮询ifWindow状态
		if(ifCust=='true'){//是我行客户
			//我行客户通话处理
			dealOwnUser();
		}else{
			//非我行客户通话处理
			dealNotUser();
		}
	};
	//来电状态，摘机
	function HMphoneOn(){
		//等待弹出提示（等待号码匹配完成）,然后进行处理
		ifCalling = false;
		ifTouch = true;
		HMcicleId = setInterval(HMcicleWinState,1000);
	};
	//通话状态，挂机
	function HMphoneOff(){
			ifTouch = false;
			PhoneOcx.StopReadSound();//结束录音
			PhoneOcx.TelSleep(200);
			PhoneOcx.PutDown();
			//记录结束时间
			eTime = new Date();
			stopTime();
			recoreTime = currentTime;
			
			 
		//更新时间，上传录音文件
			Ext.Ajax.request({
			url : basepath + '/ocrmFMmTelMain!saveData.json',
			params : {
			'operate':'timeUpdate',//时间处理
			sTime:sTime==null?"":sTime.format('yyyy-MM-dd hh:mm:ss'),//开始时间
			eTime:eTime.format('yyyy-MM-dd hh:mm:ss'),//结束时间
			timeLong:recoreTime,//通话时间
			phoneId:phoneId
			},
			method : 'POST',
			success : function() {
//				Phonic_usb.UploadFile(serverURL, basepath + '/FileUpload?phoneId='+phoneId+'&MType='+MType, "C:\\PhoneOcx.wav");
				Phonic_usb.UploadFileEx("http://"+serverURL+basepath + '/FileUpload?phoneId='+phoneId+'&MType='+MType,'',"C:\\PhoneOcx.wav");
				addFile(phoneId,MType);
			},
			failure : function(response) {
				Ext.Msg.alert('提示', '通话结束处理失败');
			}
		});
	}
	