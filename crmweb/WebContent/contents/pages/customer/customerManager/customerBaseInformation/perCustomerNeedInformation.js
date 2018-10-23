Ext.onReady(function() {
	Ext.QuickTips.init();
	// 得到当前用户的客户号
		var cust_id = oCustInfo.cust_id;
		// 加载页面时候发送请求
		var exp_financ_ser_1 = '';// 希望得到的理财服务
		var exp_fina_mgr_link_way_1='';// 希望理财客户经理的联系途径
		var exp_rec_finan_info_1='';// 希望得到的理财资讯
		var exp_join_salon_activ_1='';// 希望参加的沙龙活动
		var person_hobby_1='';// 个人兴趣爱好
		var exp_link_time_1='';// 希望的联系时间
		var store = Ext.data.Store;
		debugger;
		var myform = new Ext.FormPanel( {
			 autoHeight:true,
			//autoWidth :true,
			frame : true,
			autoScroll : true,
			title : "客户需求信息",
			items : [{
				//autoHeight:true,
				xtype : 'fieldset',
				title : '客户喜好',
				titleCollapse : true,
				labelWidth : 100,
				height:255,
				items : [ {
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					fieldLabel : '希望得到的理财服务',
					isFormField : true, // 非常重要,否则panel默认不显示fieldLabel
					items : [{
						columnWidth : .60,
						xtype : 'checkboxgroup',
						id : 'cg1',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [ {
							boxLabel : '理财产品推荐',
							name : 'cb-auto1-1',
							inputValue : "lctj"
						}, {
							boxLabel : '市场信息',
							name : 'cb-auto-1-2',
							inputValue : "scxx"
						}, {
							boxLabel : '理财咨询',
							name : 'cb-auto-1-3',
							inputValue : "lczx"
						}, {
							boxLabel : '外汇投资',
							name : 'cb-auto-1-4',
							inputValue : "whtz"
						}, {
							boxLabel : '家庭理财',
							name : 'cb-auto-1-5',
							inputValue : "jtzc"
						}, {
							boxLabel : '股市行情',
							name : 'cb-auto-1-6',
							inputValue : "gshq"
						} ]
					}, {
						columnWidth : .40,
						layout : 'form',
						labelWidth : 35,
						items : [ {
							id : 'other_1',
							value:' ',
							xtype : 'textfield',
							fieldLabel : '其它',
							anchor : '60%'
						} ,{
							//id
							id:'pk_id',
							fieldLabel : 'id',
							name:'id',
							xtype : 'textfield',
							hidden:true
						}]
					} ]
				},{
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					fieldLabel : '希望理财客户经理的联系方式',
					isFormField : true, // 非常重要,否则panel默认不显示fieldLabel
					items : [ {
						columnWidth : .60,
						xtype : 'checkboxgroup',
						id:'cg2',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [ {
							boxLabel : '电话',
							name : 'cb-auto-2-1',
							inputValue:'dh'
						}, {
							boxLabel : '电子邮件',
							name : 'cb-auto-2-2',
							inputValue:'dzyj'
						}, {
							boxLabel : 'QQ',
							name : 'cb-auto-2-3',
							inputValue:'qq'
						}, {
							boxLabel : 'MSN',
							name : 'cb-auto-2-4',
							inputValue:'msm'
						}, {
							boxLabel : '短信',
							inputValue:'dx',
							name : 'cb-auto-2-5'
						}, {
							boxLabel : '信函',
							name : 'cb-auto-2-6',
							inputValue:'xh'
						} ]
					}, {
						columnWidth : .40,
						layout : 'form',
						labelWidth : 35,
						items : [ {
							xtype : 'textfield',
							id:'other_2',
							value:' ',
							fieldLabel : '其它',
							anchor : '60%'
						} ]
					} ]
				}, {
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					fieldLabel : '希望得到的理财资讯',
					isFormField : true, // 非常重要,否则panel默认不显示fieldLabel
					items : [ {
						id:'cg3',
						columnWidth : .60,
						xtype : 'checkboxgroup',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [ {
							boxLabel : '基金',
							inputValue:'jj',
							name : 'cb-auto-3-1'
							
						}, {
							boxLabel : '外汇',
							name : 'cb-auto-3-2',
							inputValue:'wh'
						}, {
							boxLabel : '信托',
							inputValue:'xt',
							name : 'cb-auto-3-3'
						}, {
							boxLabel : '银行理财产品',
							inputValue:'yhlccp',
							name : 'cb-auto-3-4'
						}, {
							boxLabel : '宏观经济',
							inputValue:'hgjj',
							name : 'cb-auto-3-5'
						}, {
							boxLabel : '证券市场',
							inputValue:'zqsc',
							name : 'cb-auto-3-6'
						} ]
					}, {
						columnWidth : .40,
						layout : 'form',
						labelWidth : 35,
						items : [ {
							id:'other_3',
							xtype : 'textfield',
							fieldLabel : '其它',
							value:' ',
							anchor : '60%'
						} ]
					} ]
				}, {
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					fieldLabel : '希望参加的沙龙活动',
					isFormField : true, // 非常重要,否则panel默认不显示fieldLabel
					items : [ {
						id:'cg4',
						columnWidth : .60,
						xtype : 'checkboxgroup',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [ {
							boxLabel : '理财',
							inputValue:'lc',
							name : 'cb-auto-4-1'
						}, {
							boxLabel : '名车鉴赏',
							inputValue:'mcjs',
							name : 'cb-auto-4-2'
						}, {
							boxLabel : '品酒',
							inputValue:'pj',
							name : 'cb-auto-4-3'
								
						}, {
							boxLabel : '厨艺',
							inputValue:'cy',
							name : 'cb-auto-4-4'
						}, {
							boxLabel : '美容',
							inputValue:'mr',
							name : 'cb-auto-4-5'
						}, {
							boxLabel : '养生',
							inputValue:'ys',
							name : 'cb-auto-4-6'
						} ]
					}, {
						columnWidth : .40,
						layout : 'form',
						labelWidth : 35,
						items : [ {
							id:'other_4',
							xtype : 'textfield',
							value:' ',
							fieldLabel : '其它',
							anchor : '60%'
						} ]
					} ]
				}, {
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					fieldLabel : '个人兴趣爱好',
					isFormField : true, // 非常重要,否则panel默认不显示fieldLabel
					items : [ {
						id:'cg5',
						columnWidth : .60,
						xtype : 'checkboxgroup',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [ {
							boxLabel : '音乐',
							inputValue:'1',
							name : 'cb-auto-5-1'
						}, {
							boxLabel : '美术',
							inputValue:'2',
							name : 'cb-auto-5-2'
						}, {
							boxLabel : '名车鉴赏',
							inputValue:'3',
							name : 'cb-auto-5-3'
						}, {
							boxLabel : '品酒',
							inputValue:'4',
							name : 'cb-auto-5-4'
						}, {
							boxLabel : '厨艺',
							inputValue:'5',
							name : 'cb-auto-5-5'
						}, {
							boxLabel : '养生/美容',
							inputValue:'6',
							name : 'cb-auto-5-6'
						} ]
					}, {
						columnWidth : .40,
						layout : 'form',
						labelWidth : 35,
						items : [ {
							id:'other_5',
							xtype : 'textfield',
							value:' ',
							fieldLabel : '其它',
							anchor : '60%'
						} ]
					} ]
				}, {
					xtype : "panel",
					layout : "column", // 也可以是table,实现多列布局
					fieldLabel : '希望联系的时间',
					isFormField : true, // 非常重要,否则pane
					items : [ {
						id:'cg6',
						columnWidth : .30,
						xtype : 'checkboxgroup',
						fieldLabel : 'Multi-Column (horizontal)',
						items : [ {
							boxLabel : '上午',
							inputValue:'sw',
							name : 'cb-auto-6-1'
						}, {
							boxLabel : '中午',
							inputValue:'zw',
							name : 'cb-auto-6-2'
						}, {
							boxLabel : '下午',
							inputValue:'xw',
							name : 'cb-auto-6-3'
						} ]
					}
					// allowBlank : true,
					// xtype : 'combo',
					// triggerAction : 'all',
					// mode : 'local',
					// store : new Ext.data.ArrayStore( {
					// id : 0,
					// fields : [ 'myId', 'displayText' ],
					// data : [ [ 0, '早上' ], [ 1, '中午' ], [ 2, '下午' ] ]
					// }),
					// valueField : 'myId',
					// displayField : 'displayText',

					// anchor : '20%'// 宽度百分比
					]
				} ]
			}, {
				items : [ {
					xtype : 'fieldset',
					title : '礼品接收人信息',
					titleCollapse : true,
					layout : 'column',
					items : [ {
						columnWidth : .25,
						labelWidth : 100,
						layout : 'form',
						items : [ {
							id : 'presentRecName',
							name : 'presentRecName',
							labelWidth : 100,
							fieldLabel : '姓名',
							xtype : 'textfield',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .25,
						labelWidth : 60,
						layout : 'form',
						items : [ {
							id : 'presentRecLinkPhon',
							name : 'presentRecLinkPhon',
							fieldLabel : '联系电话',
							xtype : 'textfield',
							anchor : '90%'
						} ]
					}, {
						columnWidth : .50,
						labelWidth : 35,
						layout : 'form',
						items : [ {
							id:'presentRecAddr',
							name : 'presentRecAddr',
							fieldLabel : '地址',
							labelWidth : 50,
							xtype : 'textfield',
							anchor : '70%'
						} ]
					} ]

				} ]
			}, {
				items : [ {
					xtype : 'fieldset',
					title : '其他信息',
					titleCollapse : true,
					items : [ {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							id:'especialDemand',
							layout : "column",
							name:'especialDemand',
							xtype : 'textfield',
							fieldLabel : '特别需求',
							anchor : '40%'
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							id:'taboo',
							name:'taboo',
							layout : "column",
							xtype : 'textfield',
							fieldLabel : '忌讳',
							anchor : '40%'
						} ]
					}, {
						columnWidth : .25,
						layout : 'form',
						items : [ {
							id:'remark',
							layout : "column",
							name:'remark',
							xtype : 'textarea',
							fieldLabel : '备注',
							anchor : '40%'
						} ]
					} ]

				} ]

			} ],
			buttonAlign : 'center',
			buttons : [ {
				text : '确认',
				handler : function() {
				var exp_financ_ser = '';// 希望得到的理财服务
				var exp_fina_mgr_link_way='';// 希望理财客户经理的联系途径
				var exp_rec_finan_info='';// 希望得到的理财资讯
				var exp_join_salon_activ='';// 希望参加的沙龙活动
				var person_hobby='';// 个人兴趣爱好
				var exp_link_time='';// 希望的联系时间
					var cg_arr1 = Ext.getCmp('cg1').items.items;
					for ( var i = 0; i < cg_arr1.length; i++) {
						if (cg_arr1[i].checked == true) {
							exp_financ_ser += cg_arr1[i].inputValue + ',';
						}
					}
					var other_1 = Ext.getCmp("other_1").getValue();
					if (other_1 != null && other_1 != '') {
						exp_financ_ser = exp_financ_ser	+ Ext.getCmp("other_1").getValue();
					}
					//*********************
					var cg_arr2 = Ext.getCmp('cg2').items.items;
					for ( var i = 0; i < cg_arr2.length; i++) {
						if (cg_arr2[i].checked == true) {
							exp_fina_mgr_link_way += cg_arr2[i].inputValue + ',';
						}
					}
					var other_2 = Ext.getCmp("other_2").getValue();
					if (other_2 != null && other_2 != '') {
						exp_fina_mgr_link_way = exp_fina_mgr_link_way+ Ext.getCmp("other_2").getValue();
					}
					var cg_arr3 = Ext.getCmp('cg3').items.items;
					for ( var i = 0; i < cg_arr3.length; i++) {
						if (cg_arr3[i].checked == true) {
							exp_rec_finan_info += cg_arr3[i].inputValue + ',';
						}
					}
					var other_3 = Ext.getCmp("other_3").getValue();
					if (other_3 != null && other_3 != '') {
						exp_rec_finan_info = exp_rec_finan_info+ Ext.getCmp("other_3").getValue();
					}
					var cg_arr4 = Ext.getCmp('cg4').items.items;
					for ( var i = 0; i < cg_arr4.length; i++) {
						if (cg_arr4[i].checked == true) {
							exp_join_salon_activ += cg_arr4[i].inputValue + ',';
						}
					}
					var other_3 = Ext.getCmp("other_4").getValue();
					if (other_3 != null && other_3 != '') {
						exp_join_salon_activ = exp_join_salon_activ+ Ext.getCmp("other_4").getValue();
					}
					var cg_arr5 = Ext.getCmp('cg5').items.items;
					for ( var i = 0; i < cg_arr5.length; i++) {
						if (cg_arr5[i].checked == true) {
							person_hobby += cg_arr5[i].inputValue + ',';
						}
					}
					var other_5 = Ext.getCmp("other_5").getValue();
					if (other_5 != null && other_5 != '') {
						person_hobby = person_hobby+ Ext.getCmp("other_5").getValue();
					}
					var cg_arr6 = Ext.getCmp('cg6').items.items;
					for ( var i = 0; i < cg_arr6.length; i++) {
						if (cg_arr6[i].checked == true) {
							exp_link_time+= cg_arr6[i].inputValue + ',';
						}
					}
					//发送ajax请求 保存或者修改客户的需求信息
					Ext.Ajax.request({
						url : basepath + '/acrmFCiDemandInfo-info.json',
						method : 'POST',
						params : {
						'custId':cust_id,
						'expFinancSer' : exp_financ_ser,
						'expFinaMgrLinkWay' : exp_fina_mgr_link_way,
						'expRecFinanInfo' : exp_rec_finan_info,
						'expJoinSalonActiv':exp_join_salon_activ,
						'personHobby':person_hobby,
						'expLinkTime':exp_link_time,
						'id':Ext.getCmp('pk_id').getValue(),
						//礼物收件人的姓名
						'presentRecName':Ext.getCmp('presentRecName').getValue(),
						//礼物收件人的地址
						'presentRecAddr':Ext.getCmp('presentRecAddr').getValue(),
						//礼物收件人的电话
						'presentRecLinkPhon':Ext.getCmp('presentRecLinkPhon').getValue(),
						//特别需求
						'especialDemand':Ext.getCmp('especialDemand').getValue(),
						//忌讳
						'taboo':Ext.getCmp('taboo').getValue(),
						//备注
						'remark':Ext.getCmp('remark').getValue()
						//'messageId':Ext.getCmp('messageId').getValue()
						},
						waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
						scope : this,
						success : function() {
							Ext.Msg.alert('提示', '操作成功');
						},
						failure : function() {
							Ext.Msg.alert('提示', '操作失败');
						}
						}
					  );
				}
			} ]

		});
		debugger;
		var viewport = new Ext.Panel( {
			renderTo : 'viewport_center',
			height : document.body.scrollHeight - 30,
			layout : 'fit',
			autoScroll : true,
			items : [ myform ]
		});
		Ext.Ajax.request({
			url : basepath + '/acrmFCiDemandInfo-info!indexPage.json?cust_id='+cust_id,
			method:'GET',
			success:function(response){
				var nodeArra = Ext.util.JSON.decode(response.responseText);
				store.data = nodeArra;
				//alert(store.data.json.data[0].custId);
				if(store.data.json.count!=0){
				Ext.getCmp('remark').setValue(store.data.json.data[0].remark);
				Ext.getCmp('pk_id').setValue(store.data.json.data[0].id);
				Ext.getCmp('presentRecLinkPhon').setValue(store.data.json.data[0].presentRecLinkPhon);
				Ext.getCmp('especialDemand').setValue(store.data.json.data[0].especialDemand);
				Ext.getCmp('taboo').setValue(store.data.json.data[0].taboo);
				Ext.getCmp('presentRecAddr').setValue(store.data.json.data[0].presentRecAddr);
				Ext.getCmp('presentRecName').setValue(store.data.json.data[0].presentRecName);
				
				exp_financ_ser_1 = store.data.json.data[0].expFinancSer;
				var arr_1 = exp_financ_ser_1.split(',');
				var cg_arr_1 = Ext.getCmp('cg1').items.items;
				Ext.getCmp('other_1').setValue(arr_1[arr_1.length-1]);
				debugger;
				for(var i=0;i<=arr_1.length-1;i++){
					for(var j=0 ;j<cg_arr_1.length;j++){
						if(arr_1[i]==cg_arr_1[j].inputValue){
							Ext.getCmp('cg1').items.items[j].setValue(true);
						}
					}
				}
				exp_fina_mgr_link_way_1=store.data.json.data[0].expFinaMgrLinkWay;
				var arr_2 = exp_fina_mgr_link_way_1.split(',');
				var cg_arr2_2 = Ext.getCmp('cg2').items.items;
				Ext.getCmp('other_2').setValue(arr_2[arr_2.length-1]);
				for(var i=0;i<arr_2.length-1;i++){
					for(var j=0 ;j<cg_arr2_2.length;j++){
						if(arr_2[i]==cg_arr2_2[j].inputValue){
							Ext.getCmp('cg2').items.items[j].setValue(true);
						}
					}
				}
				exp_rec_finan_info_1 = store.data.json.data[0].expRecFinanInfo;
				var arr_3 = exp_rec_finan_info_1.split(',');
				Ext.getCmp('other_3').setValue(arr_3[arr_3.length-1]);
				var cg_arr3_3 = Ext.getCmp('cg3').items.items;
				for(var i=0;i<arr_3.length-1;i++){
					for(var j=0 ;j<cg_arr3_3.length;j++){
						if(arr_3[i]==cg_arr3_3[j].inputValue){
							Ext.getCmp('cg3').items.items[j].setValue(true);
						}
					}
				}
				exp_join_salon_activ_1=store.data.json.data[0].expJoinSalonActiv;
				var arr_4 = exp_join_salon_activ_1.split(',');
				Ext.getCmp('other_4').setValue(arr_4[arr_4.length-1]);
				var cg_arr4_4 = Ext.getCmp('cg4').items.items;
				for(var i=0;i<arr_4.length-1;i++){
					for(var j=0 ;j<cg_arr4_4.length;j++){
						if(arr_4[i]==cg_arr4_4[j].inputValue){
							Ext.getCmp('cg4').items.items[j].setValue(true);
						}
					}
				}
				person_hobby_1 = store.data.json.data[0].personHobby;
				var arr_5 = person_hobby_1.split(',');
				Ext.getCmp('other_5').setValue(arr_5[arr_5.length-1]);
				var cg_arr5_5 = Ext.getCmp('cg5').items.items;
				for(var i=0;i<arr_5.length-1;i++){
					for(var j=0 ;j<cg_arr5_5.length;j++){
						if(arr_5[i]==cg_arr5_5[j].inputValue){
							Ext.getCmp('cg5').items.items[j].setValue(true);
						}
					}
				}
				 exp_link_time_1=store.data.json.data[0].expLinkTime;
				 var arr_6 = exp_link_time_1.split(',');
				 var cg_arr6_6 = Ext.getCmp('cg6').items.items;
				 for(var i=0;i<arr_6.length;i++){
						for(var j=0 ;j<cg_arr6_6.length;j++){
							if(arr_6[i]==cg_arr6_6[j].inputValue){
								Ext.getCmp('cg6').items.items[j].setValue(true);
							}
						}
					}
				};
			}
		});
	});