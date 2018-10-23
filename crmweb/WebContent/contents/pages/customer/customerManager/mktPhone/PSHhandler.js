/**
 * 设备爬山虎的事件相应
 */


//匹配客户，弹性提示事件
function PSHcheckNumForCust(phoneNum){
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
					ifContact = infoList[4];
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
}
//挂机事件
function Ubox_hookon(uboxHandle){
	 if(ifTouch){
		   ifTouch = false;
		   Phonic_usb.StopRecord(uboxHandle);
		 //记录结束时间
			eTime = new Date();
			stopTime();
			recoreTime = currentTime;
			//更新时间
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
					//上传录音文件
//					Phonic_usb.UploadFile(serverURL, basepath + '/FileUpload?phoneId='+phoneId+'&MType='+MType, "C:\\PhoneOcx.mp3");
					Phonic_usb.UploadFileEx("http://"+serverURL+basepath + '/FileUpload?phoneId='+phoneId+'&MType='+MType,'',"C:\\PhoneOcx.mp3");
					addFile(phoneId,MType);
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '通话结束处理失败');
				}
			});
	   }else{
		   return ;
	   }	
}
//来电事件
function Ubox_CallId(uboxHandle,callerNumber,callerTime,callerName){
	phoneNum = callerNumber;
	PSHcheckNumForCust(phoneNum);
}


var PSHcicleId = 0;//轮询函数id
//轮询提示窗口状态
function cicleWinState(){
	if(ifWindow){//已经弹出提示
		doCallThing(PSHcicleId,hdlID);
	}
	
}
//接听电话响应处理
function doCallThing(PSHcicleId,uboxHandle){
	clearInterval(PSHcicleId);//停止轮询ifWindow状态
	//已经弹出提示
	if(ifCust=='true'){//是我行客户
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
					        	 //开始录音
								  Phonic_usb.RecordFile(uboxHandle, "C:\\PhoneOcx.mp3", CODER_ALAW);
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
		
	
	}else{//不是我行客户
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
					        	 Phonic_usb.RecordFile(uboxHandle, "C:\\PhoneOcx.mp3", CODER_ALAW);
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
									
									ifWindow = false;
					         }
					 });
				},
				failure : function(response) {
					Ext.Msg.alert('提示', '新增通话记录失败');
				}
			});
		
	
	}
	
}
//摘机事件
function Ubox_hookoff(uboxHandle){
	if(ifCalling){
		ifCalling = false;
		ifTouch = true;
		PSHcicleId = setInterval(cicleWinState,1000);//等待弹出提示,然后进行处理
	}else return;
}

