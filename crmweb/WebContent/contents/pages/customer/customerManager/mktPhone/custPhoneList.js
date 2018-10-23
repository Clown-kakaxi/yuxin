/**
 * 客户电话信息列表弹出窗口
 * 
 */


function newWindow(data,store){
	var mesgFields = [ {name : 'type'}, {name : 'num'}];


	var numSm = new Ext.grid.CheckboxSelectionModel({
		singleSelect : true//单选
	});

	var numColumns = new Ext.grid.ColumnModel([numSm,
			{header:'电话类型',dataIndex:'type'},
			{header:'号码',dataIndex:'num',
				editor : new Ext.form.TextField({})}  		
		]);
	
	
	var mesgData = [];
	//客户电话信息
//	if(data.telephoneNum!='')
		mesgData.push(['手机号码',data.telephoneNum]);
//	if(data.officePhone!='')
		mesgData.push(['办公电话',data.officePhone]);
//	if(data.linkPhone!='')
		mesgData.push(['联系电话',data.linkPhone]);
	
	var numStore = new Ext.data.ArrayStore({
		fields : mesgFields,
		data : mesgData
	});
	
	var numGrid = new Ext.grid.EditorGridPanel({
		frame : true,
		autoScroll : true,
		store : numStore,
		stripeRows : true, // 斑马线
		sm:numSm,
		cm : numColumns, // 列模型
		viewConfig : {},
		layout : 'fit',
		loadMask : {
			msg : '正在加载表格数据,请稍等...'
		}
	});
	
	//电话信息弹出窗口
	var numWindow = new Ext.Window({
		title : '客户电话信息',
		plain : true,
		layout : 'fit',
		width : 300,
		height : 200,
		resizable : false,
		draggable : true,
		closable : true,
		closeAction : 'hide',
		modal : true, // 模态窗口
		loadMask : true,
		maximizable : false,
		collapsible : false,
		titleCollapse : true,
		border : false,
		items : [ {
			layout : 'border',
			items : [
					{
						region : 'center',
						layout : 'fit',
						items : [ numGrid ]
					}
			]
		} ],
		buttonAlign:'center',
		buttons:[{text:'保存',
				handler:function(){
					var nums = '';
					debugger;
					for(var i=0;i<numStore.getCount();i++){
					    var temp=numStore.getAt(i);
					    	nums+=(temp.data.num==''?'empty':temp.data.num)+'#';
					}
					Ext.Ajax.request({
						url : basepath + '/ocrmFMmTelMain!updateNum.json',
						params : {
							num:nums,
						'custId':data.custId
						},
						method : 'POST',
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						success : function() {
							Ext.Msg.alert('提示', '保存成功');
							store.reload({
								  params : {
				                     start : 0,
				                     limit : 20}});
						},
						failure : function(response) {
							Ext.Msg.alert('提示', '更新联系方式失败');
						}
					});
						
				
			}},{text:'呼出',
				disabled:parent.MType==0?true:false,//根据接入设备控制按钮是否可用
	        	 handler: function(){
	        		 var checkedNodes = numGrid.getSelectionModel().selections.items;
						if(checkedNodes.length==0)
							{
								Ext.Msg.alert('提示', '请选择电话号码');
								return ;
							}
					var numdata = checkedNodes[0].data;
					parent.phoneNum = numdata.num;
					if(parent.MType==1){//爬山虎处理
						var state = parent.Phonic_usb.GetLineState(parent.hdlID);
						if( (state== parent.UBOX_STATE_HOOK_OFF )||(state== parent.UBOX_STATE_REVERSE_HOOKOFF )||(state== parent.UBOX_STATE_POSITIVIE_HOOKOFF )){   
						  if(parent.Phonic_usb.SendDtmf(parent.hdlID,parent.phoneNum) == 0){
							  parent.ifTouch = true;
							  //开始录音
							  parent.Phonic_usb.RecordFile(parent.hdlID, "C:\\PhoneOcx.mp3", parent.CODER_ALAW);
						  }else{
								  Ext.Msg.alert('提示', '拨号失败');
								  return ;
							  }
						}else{
							Ext.Msg.alert('提示', '请摘起电话机');
							  return ;
						}
					}
					if(parent.MType==2){//话媒处理
						parent.PhoneOcx.PickUp();
						parent.PhoneOcx.TelSleep(700);
						var phoneState = parent.PhoneOcx.DialNmbCStr(parent.phoneNum);
						parent.PhoneOcx.StartReadSoundCStrF("C:\\PhoneOcx.wav");//默认开始录音
						parent.ifTouch = true;
					}
					parent.startTime();
					parent.sTime = null;
					parent.eTime = null;//记录通话结束时间
						//保存通话记录并且返回id
						Ext.Ajax.request({
							url : basepath + '/ocrmFMmTelMain!saveData.json',
							params : {
							'operate':'outadd',//表示呼出新增
							'custId':data.custId
							},
							method : 'POST',
							waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
							success : function() {
								//获取新id
								Ext.Ajax.request({
		    				         url: basepath +'/ocrmFMmTelMain!getPid.json',
		    					         success:function(response){
		    					        	 parent.phoneId = Ext.util.JSON.decode(response.responseText).pid;
		    					        	 	//接听电话页面
		    					        	 parent.CustdataInfo.custId=data.custId;
		    					        	 parent.CustdataInfo.custZhName=data.custZhName;
		    					        	 parent.CustdataInfo.custTyp=data.custTyp;
		    					        	 parent.CustdataInfo.ContactInfo = false;
		    					        		parent.initdata(parent.CustdataInfo,parent.phoneId);
		    									numWindow.hide();
		    					         }
	   						 });
							},
							failure : function(response) {
								Ext.Msg.alert('提示', '新增通话记录失败');
							}
						});
					
	        	 }
		}]
	});
	numWindow.show();
	
}


