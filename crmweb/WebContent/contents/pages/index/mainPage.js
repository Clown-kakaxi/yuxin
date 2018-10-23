Ext.onReady(function(){
    
	/**************************日程安排数据源**************************/
    var scheduleStore = new Ext.data.Store({
        id: 'schedule',
        restful : true,     
        proxy : new Ext.data.HttpProxy({
            url : basepath+'/scheduleforcal.json'
        }),
        reader : new Ext.data.JsonReader({
            successProperty: 'success',
            idProperty: 'SCHEDULE_ID',
            messageProperty: 'message',
            totalProperty: 'json.count',
            root : 'json.data'
        },Ext.data.Record.create([
            {name:'scheduleId', mapping:'SCHEDULE_ID'},
            {name:'scheduleTitle',mapping:'SCHEDULE_TITLE'},
            {name:'startDt',mapping:'START_DATE'} 
        ]))
    });
    
	 var crmPoStore = new Ext.data.ArrayStore({
	        fields:['myId','displayText'],
	        data:[['1','客户名称'],['2','证件号码']]
	    });
	 
	 
    scheduleStore.load({
        params : {
            start : 0,
            limit : 5
        }
    });
    /***********************信息提醒数据源************************/
    
    var remindArray ;
    
    Ext.Ajax.request({
        url : basepath+'/queryremindinfoind.json',
        method : 'GET',
        success : function(response){
            var returns = Ext.util.JSON.decode(response.responseText);
            remindArray = returns.json.data;
            for(var i=0;i<remindArray.length;i++){
                switch(remindArray[i].MSG_TYP){
                case '101' : 
                	document.getElementById('xinxitixing1').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=101',"+remindArray[i].COUNT+");\">账户余额变动（转出）（"+remindArray[i].COUNT+"）</a>";	
                	break;				
                case '102' : 
                	document.getElementById('xinxitixing2').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=102',"+remindArray[i].COUNT+");\">账户余额变动（转入）（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '201' : 
                	document.getElementById('xinxitixing3').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=201',"+remindArray[i].COUNT+");\">新产品发售提醒（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '202' :
                 	document.getElementById('xinxitixing4').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=202',"+remindArray[i].COUNT+");\">定期存款到期（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '203' :
                 	document.getElementById('xinxitixing5').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=203',"+remindArray[i].COUNT+");\">理财产品到期（"+remindArray[i].COUNT+"）</a>";	
                	break;				
                case '204' :
                 	document.getElementById('xinxitixing6').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=204',"+remindArray[i].COUNT+");\">理财产品购买（"+remindArray[i].COUNT+"）</a>";	
                	break;				
                case '205' : 
                	document.getElementById('xinxitixing7').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=205',"+remindArray[i].COUNT+");\">基金赎回提醒（"+remindArray[i].COUNT+"）</a>";	
                	break;				
                case '206' : 
                	document.getElementById('xinxitixing8').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=206',"+remindArray[i].COUNT+");\">基金认（申）购提醒（"+remindArray[i].COUNT+"）</a>";	
                	break;				
                case '207' : 	
                	document.getElementById('xinxitixing9').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=207',"+remindArray[i].COUNT+");\">新开基金账户提醒（"+remindArray[i].COUNT+"）</a>";	
                break;				
                case '208' : 	
                	document.getElementById('xinxitixing10').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=208',"+remindArray[i].COUNT+");\">基金定投扣款提醒（"+remindArray[i].COUNT+"）</a>";	
                break;				
                case '301' : 	
                	document.getElementById('xinxitixing11').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=301',"+remindArray[i].COUNT+");\">客户生日（"+remindArray[i].COUNT+"）</a>";	
                break;				
                case '302' : 
                	document.getElementById('xinxitixing12').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=302',"+remindArray[i].COUNT+");\">春节（"+remindArray[i].COUNT+"）</a>";	
                	break;				
                case '303' :
                 	document.getElementById('xinxitixing13').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=303',"+remindArray[i].COUNT+");\">中秋节（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '304' :
                 	document.getElementById('xinxitixing14').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=304',"+remindArray[i].COUNT+");\">重阳节（"+remindArray[i].COUNT+"）</a>";	
                	break;				
                case '305' :
                 	document.getElementById('xinxitixing15').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=305',"+remindArray[i].COUNT+");\">妇女节（"+remindArray[i].COUNT+"）</a>";	
                	break;				
                case '401' :
                 	document.getElementById('xinxitixing16').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=401',"+remindArray[i].COUNT+");\">客户自动调入提醒（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '402' :
                 	document.getElementById('xinxitixing17').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=402',"+remindArray[i].COUNT+");\">客户自动调出提醒（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '403' :
                 	document.getElementById('xinxitixing18').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=403',"+remindArray[i].COUNT+");\">客户手工调入提醒（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '404' :
                 	document.getElementById('xinxitixing19').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=404',"+remindArray[i].COUNT+");\">客户手工调出提醒（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '405' :
                 	document.getElementById('xinxitixing20').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=405',"+remindArray[i].COUNT+");\">客户等级升级提醒（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '406' :
                 	document.getElementById('xinxitixing21').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=406',"+remindArray[i].COUNT+");\">客户等级降级提醒（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '407' :
                 	document.getElementById('xinxitixing22').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=407',"+remindArray[i].COUNT+");\">客户积分增加提醒（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '408' : 
                	document.getElementById('xinxitixing23').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=408',"+remindArray[i].COUNT+");\">客户积分减少提醒（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '409' :
                 	document.getElementById('xinxitixing24').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=409',"+remindArray[i].COUNT+");\">潜在客户转为我行客户变动提醒（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '410' : 
                	document.getElementById('xinxitixing25').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=410',"+remindArray[i].COUNT+");\">客户销户提醒（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '501' :
                 	document.getElementById('xinxitixing26').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=501',"+remindArray[i].COUNT+");\">一个月未联系的客户（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '502' :
                 	document.getElementById('xinxitixing27').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=502',"+remindArray[i].COUNT+");\">三个月未联系的客户（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '503' :
                 	document.getElementById('xinxitixing28').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=503',"+remindArray[i].COUNT+");\">管理资产连续2个月减少的客户（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '504' :
                 	document.getElementById('xinxitixing29').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=504',"+remindArray[i].COUNT+");\">参加活动预约的客户（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '505' :
                 	document.getElementById('xinxitixing30').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=505',"+remindArray[i].COUNT+");\">风险能力需评估的客户（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '506' :
                 	document.getElementById('xinxitixing31').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=506',"+remindArray[i].COUNT+");\">投诉的客户（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '507' :
                 	document.getElementById('xinxitixing32').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=507',"+remindArray[i].COUNT+");\">客户推荐客户的客户（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '508' :
                 	document.getElementById('xinxitixing33').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=508',"+remindArray[i].COUNT+");\">未接来电客户提醒（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '601' :
                 	document.getElementById('xinxitixing34').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=601',"+remindArray[i].COUNT+");\">到期前贷款余额小于授信额度的客户（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '602' :
                 	document.getElementById('xinxitixing35').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=602',"+remindArray[i].COUNT+");\">贷款到期提醒（"+remindArray[i].COUNT+"）</a>";
                	break;				
                case '603' :
                 	document.getElementById('xinxitixing36').innerHTML = "<a href=\"javascript:;\" onclick=\"text('"+ basepath+"/contents/pages/workSpace/remindManage/remindListNew.jsp?ruleCode=603',"+remindArray[i].COUNT+");\">贷款逾期（欠息）客户（"+remindArray[i].COUNT+"）</a>";
                	break;				

                default :
                    break;                        
                }
            }
        },
        failure:function(){
            Ext.Msg.alert('提示','信息提醒数据查询失败');
        }
    });
    /**************************公告数据源*****************************/
    var noticeStore = new Ext.data.Store({
        id: 'notice',
        restful : true,     
        proxy : new Ext.data.HttpProxy({
            url : basepath+'/noticequery.json'
        }),
        reader : new Ext.data.JsonReader({
            successProperty: 'success',
            idProperty: 'NOTICE_ID',
            messageProperty: 'message',
            totalProperty: 'json.count',
            root : 'json.data'
        },Ext.data.Record.create([
            {name: 'noticeId', mapping: 'NOTICE_ID'},
            {name: 'noticeTitle', mapping: 'NOTICE_TITLE'},
            {name: 'noticeLevel', mapping: 'NOTICE_LEVEL'}
        ]))
    });
    var indexNotice = {isRead:'red002'};
    noticeStore.on('beforeload', function() {
        this.baseParams = {
                "condition":Ext.encode(indexNotice)
        };
    });  
    noticeStore.load({
        params : {
            start : 0,
            limit : 5  
        }
    });
    /************快捷功能区关闭工具***********************/
    var tools = [{
        id:'close',
        handler: function(e, target, panel){
            panel.ownerCt.remove(panel, true);
        }
    }];
   /*************左边快捷功能区面板************************/
    var westpanel=new Ext.Panel({
        bodyStyle:'background-color:#fff',	
        items:[{
            title:'客户快速查询',
            tools:tools,
            frame:true,
            collapsible: true,
            items:[
                new Ext.FormPanel({
//                    defaultType: 'textfield',
//                    labelStyle : {
//						width : '10px'
//					},
                	columnWidth : .99,
					layout : 'form',
					labelWidth : 60, // 标签宽度
					defaultType : 'textfield',
					border : false,
                    items:[
   						{	
   	                        fieldLabel: '查询方式',
   	                        name: 'qStyle',
   	                        id:'qStyle',
   	                        forceSelection : true,
   							resizable:true,
   	                        xtype:'combo',
   	                        editable:false,
   	                        triggerAction:'all',
   	                        mode:'local',
   	                        value:1,
   	                        store:crmPoStore,
   	                        valueField:'myId',
   	                        displayField:'displayText',
   	                        emptyText:'请选择',
   	                        anchor : '90%',
   	                        labelStyle: 'text-align:right;'
   	                    },{
   	                        fieldLabel:"查询条件",
   	                        name:"name",
   	                        anchor:'90%',
   	                        labelStyle: 'text-align:right;'
   	                    }],
                    buttons:[
                        {
                            text : '快速查询',
                            handler: function(){
                                var condis = this.ownerCt.ownerCt.getForm().getValues(false);
                                if(!condis.name){
                                    Ext.Msg.alert('提示','请输入查询条件!'); 
                                    return false;
                                }
                                condis.mod = "quick";
                                var tempSign = Ext.getCmp('qStyle').getValue();
                                if(tempSign =="1"){
                                	var url = basepath + "/contents/pages/customer/customerManager/customerQuery.jsp?condis="+condis.name;
                                	parent.booter.indexLocate(212,url);
                                }
                                if(tempSign == "2"){
                                	 var url=basepath + "/contents/pages/customer/customerManager/customerQuery.jsp?qStyle="+condis.name;
                                	 parent.booter.indexLocate(212,url);
                                }
                                	var conditionStr = Ext.encode(condis);
                            }
                        }
                    ]
                })
            ]
    	},{
    	    title:'最新公告',
    	    style:{marginTop:'0px'},
    	    bodyStyle:'background-color:#fff',	
    	    tools:tools,
    	    frame:true,
    	    collapsible: true,
    	    items: [
    	        new Ext.grid.GridPanel({
    	            store : noticeStore,
    	            height:100,
    	            hideHeaders :true,
    	            cm : new Ext.grid.ColumnModel([
    	                {
    	                    header : '公告ID', 
    	                    dataIndex : 'noticeId', 
    	                    sortable : true,
    	                    width : 150,
    	                    hidden:true
    	                },{
    	                    dataIndex : 'noticeTitle', 
    	                    width : 170,
    	                    renderer: function(v,p,record){
    	                        var nl = record.data.noticeLevel;
    	                        if(nl == 'lev001'){
    	                            return '<font color="red"><b>'+v+'</b></font>';
    	                        }else return v;
    	                    }
    	                }
    	                ]),
    	                stripeRows : true, 
    	                loadMask : {
    	                    msg : '正在加载表格数据,请稍等...'
    	                },
    	                listeners :{
    	                    'rowdblclick' : function(){
    	                        //window.location.href= basepath+'/contents/pages/workSpace/afficheManage/affiche.jsp';
    	                		parent.booter.indexLocate(238);
    	                	}
    	                }
    		    })
    		 ]
    	},{
    	    title:'信息提醒',
    	    style:{
    	        margin:'0px 0 0px 0'
    	    },
    	    bodyStyle:'background-color:#fff',    		
    	    tools:tools,
    	    frame:true,
    	    collapsible:true,
    	    html:"<div class='info_box' style='height:100px'>"+
	         "<ul>" +
	         "<marquee onmouseover=stop(); onmouseout=start(); direction='up' align='top' style='100px;' scrollamount=2>"+
	         "<li id='xinxitixing1' >	账户余额变动（转出）	（0）</li>" +
	         "<li id='xinxitixing2' >	账户余额变动（转入）	（0）</li>" +
	         "<li id='xinxitixing3' >	新产品发售提醒	（0）</li>" +
	         "<li id='xinxitixing4' >	定期存款到期	（0）</li>" +
	         "<li id='xinxitixing5' >	理财产品到期	（0）</li>" +
	         "<li id='xinxitixing6' >	理财产品购买	（0）</li>" +
	         "<li id='xinxitixing7' >	基金赎回提醒	（0）</li>" +
	         "<li id='xinxitixing8' >	基金认（申）购提醒	（0）</li>" +
	         "<li id='xinxitixing9' >	新开基金账户提醒	（0）</li>" +
	         "<li id='xinxitixing10' >	基金定投扣款提醒	（0）</li>" +
	         "<li id='xinxitixing11' >	客户生日	（0）</li>" +
	         "<li id='xinxitixing12' >	春节	（0）</li>" +
	         "<li id='xinxitixing13' >	中秋节	（0）</li>" +
	         "<li id='xinxitixing14' >	重阳节	（0）</li>" +
	         "<li id='xinxitixing15' >	妇女节	（0）</li>" +
	         "<li id='xinxitixing16' >	客户自动调入提醒	（0）</li>" +
	         "<li id='xinxitixing17' >	客户自动调出提醒	（0）</li>" +
	         "<li id='xinxitixing18' >	客户手工调入提醒	（0）</li>" +
	         "<li id='xinxitixing19' >	客户手工调出提醒	（0）</li>" +
	         "<li id='xinxitixing20' >	客户等级升级提醒	（0）</li>" +
	         "<li id='xinxitixing21' >	客户等级降级提醒	（0）</li>" +
	         "<li id='xinxitixing22' >	客户积分增加提醒	（0）</li>" +
	         "<li id='xinxitixing23' >	客户积分减少提醒	（0）</li>" +
	         "<li id='xinxitixing24' >	潜在客户转为我行客户变动提醒	（0）</li>" +
	         "<li id='xinxitixing25' >	客户销户提醒	（0）</li>" +
	         "<li id='xinxitixing26' >	一个月未联系的客户	（0）</li>" +
	         "<li id='xinxitixing27' >	三个月未联系的客户	（0）</li>" +
	         "<li id='xinxitixing28' >	管理资产连续2个月减少的客户	（0）</li>" +
	         "<li id='xinxitixing29' >	参加活动预约的客户	（0）</li>" +
	         "<li id='xinxitixing30' >	风险能力需评估的客户	（0）</li>" +
	         "<li id='xinxitixing31' >	投诉的客户	（0）</li>" +
	         "<li id='xinxitixing32' >	客户推荐客户的客户	（0）</li>" +
	         "<li id='xinxitixing33' >	未接来电客户提醒	（0）</li>" +
	         "<li id='xinxitixing34' >	到期前贷款余额小于授信额度的客户	（0）</li>" +
	         "<li id='xinxitixing35' >	贷款到期提醒	（0）</li>" +
	         "<li id='xinxitixing36' >	贷款逾期（欠息）客户	（0）</li>" +
	         "</marquee>"+
	         "</ul>" +
	         "</div>"
    	},{
    	    title:'日程安排',
    	    style:{marginTop:'0px'},
    	    bodyStyle:'background-color:#fff',
    	    frame:true,
    	    tools:tools,
    	    layout:'fit',
    	    height:140,
    	    collapsible: true,
    	    items:[
    	        new Ext.grid.GridPanel({
    	            store : scheduleStore,
    	            hideHeaders :true,
    	            cm : new Ext.grid.ColumnModel([
    	                {
    	                    dataIndex : 'scheduleId',
    	                    hidden:true
    	                },{
    	                    dataIndex : 'startDt',
    	                    width : 70,
    	                    renderer: function(v){
	                        return v.substring(0,10);
	                        
	                    }
    	                },{
    	                    dataIndex : 'scheduleTitle',
    	                    width : 120
    	                }]),
    	            stripeRows : true,
    	            loadMask : {
    	                msg : '正在加载表格数据,请稍等...'
    	            },
    	            listeners :{
    	                'rowdblclick' : function(){
    	                    //window.location.href= basepath+'/contents/pages/workSpace/calendarManager/schedulePlanIndex.jsp';
    	                    parent.booter.indexLocate(13102);
    	                }
    	            }
    	        })
    	     ]
    	}]
    });
    
    
    /******************************首页整体布局*******************************/
    var viewport = new Ext.Viewport({
        layout:'border',
        autoScroll:true,
        items:[{
            xtype:'portal',
            region:'west',
            id:'west-panel',
            layout:'fit',
            title:'快捷功能',
            collapseMode: 'mini',
            frame:true,
            split:true,
            style:'background-color:#000;',
            width: 200,
            minSize: 175,
            maxSize: 200,
            collapsible: true,
            margins:'5 0 5 5 ',
            cmargins:'5 5 5 5',
            items:[westpanel]
        },
        new Com.yucheng.crm.index.MainViewPanel({
            id:'mainViewPanel',
            region:'center',
            width: document.body.clientWidth - 210,
            autoScroll:true
        })
        ]
    });
    /*****************功能概览区数据源*********************/
    Ext.Ajax.request({
        url : basepath+'/indexset.json',
        method : "GET",
        success : function(response){
            userSetting = Ext.util.JSON.decode(response.responseText);
            if(!userSetting.returns.layoutId){
                /**
                 * TODO default Layout.
                 */
                var _resultP = Ext.getCmp('mainViewPanel');
                _resultP.doUserLayout();
                return;
            }else{
                /**
                 * TODO the UserSetted Layout.
                 */
                var userLayout = userSetting.returns.layoutId;
                var userModule = userSetting.returns.data;
                var _resultP = Ext.getCmp('mainViewPanel');
                _resultP.userLayout = userLayout;
                _resultP.userModule = userModule;                 
                _resultP.doUserLayout();
                return;
            }
        },
        failure : function(action,form){
            Ext.Msg.alert('提示','你的首页设置信息查询失败，将为您初始化默认配置'); 
            /**
             * TODO default layout.
             */
            var _resultP = Ext.getCmp('mainViewPanel');
            _resultP.doUserLayout();
        }
    });
});