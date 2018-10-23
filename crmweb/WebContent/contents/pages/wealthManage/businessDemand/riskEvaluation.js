/**
 * 财富管理->业务需求->风险评测：JS文件；wzy；2013-09-03
 */
Ext.onReady(function() {

	// 客户选择组件
	var custSelectPartAdd = new Com.yucheng.bcrm.common.CustomerQueryField({
				fieldLabel : '*客户名称',
				labelWidth : 100,
				name : 'custName',
				custtype : '1',// 客户类型:1:对私,2:对公,不设默认全部
				custStat : '',// 客户状态:1:正式,2:潜在,不设默认全部
				singleSelected : true,// 单选复选标志
				editable : false,
				allowBlank : false,
				blankText : '此项为必填项，请检查！',
				anchor : '90%',
				hiddenName : 'custId',
				callback : function() {// 回调方法，给其它字段设置相关属性值
					custForm.form.findField('custContactName')
							.setValue(custSelectPartAdd.mobileNum);// 联系方式
					custForm.form.findField('certType')
							.setValue(custSelectPartAdd.certTypeOra);// 证件类型
					custForm.form.findField('certNum')
							.setValue(custSelectPartAdd.certNum);// 证件号码
				}
			});

	// 客户信息定义
	var custForm = new Ext.FormPanel({
		frame : true,
		buttonAlign : "center",
		width : '100%',
		labelAlign : 'center',
		items : [{
			items : [{
						layout : 'column',
						items : [{
									columnWidth : .5,
									layout : 'form',
									items : [custSelectPartAdd]
								}, {
									columnWidth : .5,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '联系方式',
												labelStyle : 'text-align:right;',
												name : 'custContactName',
												anchor : '90%',
												labelSeparator : ''
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '证件类别',
												labelStyle : 'text-align:right;',
												name : 'certType',
												anchor : '90%',
//												disabled : true,
												labelSeparator : ''
											}]
								}, {
									columnWidth : .5,
									layout : 'form',
									items : [{
												xtype : 'textfield',
												fieldLabel : '证件号码',
												labelStyle : 'text-align:right;',
												name : 'certNum',
												anchor : '90%',
												value : "",
//												disabled : true,
												labelSeparator : ''
											}]
								}]
					}]
		}]
	});

	// 客户信息
	var rd_set_01 = new Ext.form.FieldSet({
				xtype : 'fieldset',
				title : '客户信息',
				labelWidth : 250,
				labelAlign : 'right',
				collapsible : true,
				itemCls : 'x-check-group-alt',
				items : [custForm]
			});

	// 试题元素定义
	var title_rs_01 = new Array();
	title_rs_01.push(new Ext.form.Radio({
				boxLabel : '18-30',
				name : 'ts_name_01_01',
				inputValue : 'ts_id_01_01'
			}));
	title_rs_01.push(new Ext.form.Radio({
				boxLabel : '31-50',
				name : 'ts_name_01_01',
				inputValue : 'ts_id_01_02'
			}));
	title_rs_01.push(new Ext.form.Radio({
				boxLabel : '51-60',
				name : 'ts_name_01_01',
				inputValue : 'ts_id_01_03'
			}));
	title_rs_01.push(new Ext.form.Radio({
				boxLabel : '高于60岁',
				name : 'ts_name_01_01',
				inputValue : 'ts_id_01_04'
			}));
	var rg_01 = new Ext.form.RadioGroup({
				id : 'rg_id_01',
				fieldLabel : '1.您的年龄是？',
				name : 'rg_name_01',
				items : [title_rs_01]
			});
	var title_rs_02 = new Array();
	title_rs_02.push(new Ext.form.Radio({
				boxLabel : '5万元以下',
				name : 'ts_name_02_01',
				inputValue : 'ts_id_02_01'
			}));
	title_rs_02.push(new Ext.form.Radio({
				boxLabel : '5-20万元',
				name : 'ts_name_02_01',
				inputValue : 'ts_id_02_02'
			}));
	title_rs_02.push(new Ext.form.Radio({
				boxLabel : '20-50万元',
				name : 'ts_name_02_01',
				inputValue : 'ts_id_02_03'
			}));
	title_rs_02.push(new Ext.form.Radio({
				boxLabel : '50-100万元',
				name : 'ts_name_02_01',
				inputValue : 'ts_id_02_04'
			}));
	var rg_02 = new Ext.form.RadioGroup({
				id : 'rg_id_02',
				fieldLabel : '2.您的家庭年收入为（折合人民币）？',
				name : 'rg_name_02',
				items : [title_rs_02]
			});
	var title_rs_03 = new Array();
	title_rs_03.push(new Ext.form.Radio({
				boxLabel : '小于10%',
				name : 'ts_name_03_01',
				inputValue : 'ts_id_03_01'
			}));
	title_rs_03.push(new Ext.form.Radio({
				boxLabel : '10%至25%',
				name : 'ts_name_03_01',
				inputValue : 'ts_id_03_02'
			}));
	title_rs_03.push(new Ext.form.Radio({
				boxLabel : '25%至50%',
				name : 'ts_name_03_01',
				inputValue : 'ts_id_03_03'
			}));
	title_rs_03.push(new Ext.form.Radio({
				boxLabel : '大于50%',
				name : 'ts_name_03_01',
				inputValue : 'ts_id_03_04'
			}));
	var rg_03 = new Ext.form.RadioGroup({
				id : 'rg_id_03',
				fieldLabel : '3.在您每年的家庭收入中，可用于金融投资（储蓄存款除外）的比例为？',
				name : 'rg_name_03',
				items : [title_rs_03]
			});
	var title_rs_04 = new Array();
	title_rs_04.push(new Ext.form.Radio({
				boxLabel : '除存款、国债外，我几乎不投资其他金融产品',
				name : 'ts_name_04_01',
				inputValue : 'ts_id_04_01'
			}));
	title_rs_04.push(new Ext.form.Radio({
				boxLabel : '大部分投资于存款、国债等，较少投资于股票、基金等风险产品',
				name : 'ts_name_04_01',
				inputValue : 'ts_id_04_02'
			}));
	title_rs_04.push(new Ext.form.Radio({
				boxLabel : '资产均衡地分布于存款、国债、银行理财产品、信托产品、股票、基金等',
				name : 'ts_name_04_01',
				inputValue : 'ts_id_04_03'
			}));
	title_rs_04.push(new Ext.form.Radio({
				boxLabel : '大部分投资于股票、基金、外汇等高风险产品，较少投资于存款、国债',
				name : 'ts_name_04_01',
				inputValue : 'ts_id_04_04'
			}));
	var rg_04 = new Ext.form.RadioGroup({
				id : 'rg_id_04',
				fieldLabel : '4.以下哪项最能说明您的投资经验？',
				name : 'rg_name_04',
				items : [title_rs_04]
			});
	var title_rs_05 = new Array();
	title_rs_05.push(new Ext.form.Radio({
				boxLabel : '没有经验',
				name : 'ts_name_05_01',
				inputValue : 'ts_id_05_01'
			}));
	title_rs_05.push(new Ext.form.Radio({
				boxLabel : '少于2年',
				name : 'ts_name_05_01',
				inputValue : 'ts_id_05_02'
			}));
	title_rs_05.push(new Ext.form.Radio({
				boxLabel : '2至5年',
				name : 'ts_name_05_01',
				inputValue : 'ts_id_05_03'
			}));
	title_rs_05.push(new Ext.form.Radio({
				boxLabel : '5至8年',
				name : 'ts_name_05_01',
				inputValue : 'ts_id_05_04'
			}));
	title_rs_05.push(new Ext.form.Radio({
				boxLabel : '8年以上',
				name : 'ts_name_05_01',
				inputValue : 'ts_id_05_05'
			}));
	var rg_05 = new Ext.form.RadioGroup({
				id : 'rg_id_05',
				fieldLabel : '5.您有多少年投资股票、基金、外汇、金融衍生产品等风险投资品的经验？',
				name : 'rg_name_05',
				items : [title_rs_05]
			});
	var title_rs_06 = new Array();
	title_rs_06.push(new Ext.form.Radio({
				boxLabel : '厌恶风险，不希望本金损失，希望获得稳定回报',
				name : 'ts_name_06_01',
				inputValue : 'ts_id_06_01'
			}));
	title_rs_06.push(new Ext.form.Radio({
				boxLabel : '保守投资，不希望本金损失，愿意承担一定幅度的收益波动',
				name : 'ts_name_06_01',
				inputValue : 'ts_id_06_02'
			}));
	title_rs_06.push(new Ext.form.Radio({
				boxLabel : '寻求资金的较高收益和成长性，愿意为此承担有限本金损失',
				name : 'ts_name_06_01',
				inputValue : 'ts_id_06_03'
			}));
	title_rs_06.push(new Ext.form.Radio({
				boxLabel : '希望赚取高回报，愿意为此承担较大本金损失',
				name : 'ts_name_06_01',
				inputValue : 'ts_id_06_04'
			}));
	var rg_06 = new Ext.form.RadioGroup({
				id : 'rg_id_06',
				fieldLabel : '6．以下哪项描述最符合您的投资态度？',
				name : 'rg_name_06',
				items : [title_rs_06]
			});
	var title_rs_07 = new Array();
	title_rs_07.push(new Ext.form.Radio({
				boxLabel : '有100%的机会赢取1000元现金',
				name : 'ts_name_07_01',
				inputValue : 'ts_id_07_01'
			}));
	title_rs_07.push(new Ext.form.Radio({
				boxLabel : '有50%的机会赢取5万元现金',
				name : 'ts_name_07_01',
				inputValue : 'ts_id_07_02'
			}));
	title_rs_07.push(new Ext.form.Radio({
				boxLabel : '有25%的机会赢取50万元现金',
				name : 'ts_name_07_01',
				inputValue : 'ts_id_07_03'
			}));
	title_rs_07.push(new Ext.form.Radio({
				boxLabel : '有10%的机会赢取100万元现金',
				name : 'ts_name_07_01',
				inputValue : 'ts_id_07_04'
			}));
	var rg_07 = new Ext.form.RadioGroup({
				id : 'rg_id_07',
				fieldLabel : '7.以下情况，您会选择哪一种？',
				name : 'rg_name_07',
				items : [title_rs_07]
			});
	var title_rs_08 = new Array();
	title_rs_08.push(new Ext.form.Radio({
				boxLabel : '1年以下',
				name : 'ts_name_08_01',
				inputValue : 'ts_id_08_01'
			}));
	title_rs_08.push(new Ext.form.Radio({
				boxLabel : '1－3年',
				name : 'ts_name_08_01',
				inputValue : 'ts_id_08_02'
			}));
	title_rs_08.push(new Ext.form.Radio({
				boxLabel : '3—5年',
				name : 'ts_name_08_01',
				inputValue : 'ts_id_08_03'
			}));
	title_rs_08.push(new Ext.form.Radio({
				boxLabel : '5年以上',
				name : 'ts_name_08_01',
				inputValue : 'ts_id_08_04'
			}));
	var rg_08 = new Ext.form.RadioGroup({
				id : 'rg_id_08',
				fieldLabel : '8.您计划的投资期限是多久？',
				name : 'rg_name_08',
				items : [title_rs_08]
			});
	var title_rs_09 = new Array();
	title_rs_09.push(new Ext.form.Radio({
				boxLabel : '资产保值',
				name : 'ts_name_09_01',
				inputValue : 'ts_id_09_01'
			}));
	title_rs_09.push(new Ext.form.Radio({
				boxLabel : '资产稳健增长',
				name : 'ts_name_09_01',
				inputValue : 'ts_id_09_02'
			}));
	title_rs_09.push(new Ext.form.Radio({
				boxLabel : '资产迅速增长',
				name : 'ts_name_09_01',
				inputValue : 'ts_id_09_03'
			}));
	var rg_09 = new Ext.form.RadioGroup({
				id : 'rg_id_09',
				fieldLabel : '9.您的投资目的是？',
				name : 'rg_name_09',
				items : [title_rs_09]
			});
	var title_rs_10 = new Array();
	title_rs_10.push(new Ext.form.Radio({
				boxLabel : '本金无损失，但收益未达预期',
				name : 'ts_name_10_01',
				inputValue : 'ts_id_10_01'
			}));
	title_rs_10.push(new Ext.form.Radio({
				boxLabel : '出现轻微本金损失',
				name : 'ts_name_10_01',
				inputValue : 'ts_id_10_02'
			}));
	title_rs_10.push(new Ext.form.Radio({
				boxLabel : '本金10％以内的损失',
				name : 'ts_name_10_01',
				inputValue : 'ts_id_10_03'
			}));
	title_rs_10.push(new Ext.form.Radio({
				boxLabel : '本金20-50％的损失',
				name : 'ts_name_10_01',
				inputValue : 'ts_id_10_04'
			}));
	title_rs_10.push(new Ext.form.Radio({
				boxLabel : '本金50％以上损失',
				name : 'ts_name_10_01',
				inputValue : 'ts_id_10_05'
			}));
	var rg_10 = new Ext.form.RadioGroup({
				id : 'rg_id_10',
				fieldLabel : '10.您的投资出现何种程度的波动时，您会呈现明显的焦虑？',
				name : 'rg_name_10',
				items : [title_rs_10]
			});

	// 评测试题定义
	var rd_set_02 = new Ext.form.FieldSet({
				xtype : 'fieldset',
				title : '评测试题',
				labelWidth : 420,
				labelAlign : 'right',
				collapsible : true,
				itemCls : 'x-check-group-alt',
				items : [rg_01, rg_02, rg_03, rg_04, rg_05, rg_06, rg_07,
						rg_08, rg_09, rg_10]
			});

	// 评测结果定义
	var rd_set_03 = new Ext.form.FieldSet({
				xtype : 'fieldset',
				title : '评测结果',
				labelWidth : 150,
				labelAlign : 'right',
				collapsible : true,
				items : [{
							id : 'indageteQaScoring',
							xtype : 'textfield',
							fieldLabel : '评测结果(客户风险等级)',
							labelStyle : 'text-align:right;',
							name : 'indageteQaScoring',
							anchor : '50%',
//							disabled : true,
							labelSeparator : ''
						}]
			});

	// 装载页面元素的容器
	var opForm = new Ext.Panel({
				id : 'opForm',
				layout : 'form',
				autoScroll : true,
				labelAlign : 'right',
				frame : true,
				buttonAlign : "center",
				items : [rd_set_01, rd_set_02, rd_set_03],
				buttons : [{
					text : '提交',
					handler : function() {
						// 检查是否选择了客户
						var custContactName = custForm.form
								.findField('custName').getValue();
						if (custContactName == null || custContactName == '') {
							Ext.Msg.alert('提示', '请先选择客户！');
							return false;
						}
						// 检查是否都选择了答案
						for (var j = 1; j < 11; j++) {
							if (j != 10) {
								if (Ext.getCmp('rg_id_0' + j).getValue() == null) {
									Ext.Msg.alert('提示', '第【' + j + '】题没有选择答案！');
									return false;
								}
							} else {
								if (Ext.getCmp('rg_id_' + j).getValue() == null) {
									Ext.Msg.alert('提示', '第' + j + '题没有选择答案！');
									return false;
								}
							}
						}
						// 提交前，展开全部信息
						opForm.items.each(function(item, index, length) {
									item.expand();
								});
						Ext.getCmp('indageteQaScoring').setValue('平衡型');// 赋值
						// 滚动条到最下面
						var d = opForm.body.dom;
						d.scrollTop = d.scrollHeight - d.offsetHeight;
					}
				}, {
					text : '重置',
					handler : function() {
						for (var j = 1; j < 11; j++) {
							if (j != 10) {
								Ext.getCmp('rg_id_0' + j).reset();
							} else {
								Ext.getCmp('rg_id_' + j).reset();
							}
						}
						custForm.getForm().reset();
						Ext.getCmp('indageteQaScoring').setValue('');// 赋值
					}
				}, {
					text : '打印',
					handler : function() {
						// 判断是否进行了风险评测
						var indageteQaScoringValue = Ext
								.getCmp('indageteQaScoring').getValue();
						if (indageteQaScoringValue == null
								|| indageteQaScoringValue == '') {
							Ext.Msg.alert('提示', '请先进行风险评测，然后进行打印！');
							return false;
						}
						// 打印前，展开全部信息
						opForm.items.each(function(item, index, length) {
									item.expand();
								});
						window.print();
					}
				}]
			});

	// 页面布局
	var mainView = new Ext.Viewport({
				closable : true,
				plain : true,
				resizable : false,
				collapsible : false,
				height : 500,
				width : 400,
				draggable : false,
				closeAction : 'hide',
				modal : true,
				border : false,
				autoScroll : true,
				closable : true,
				animateTarget : Ext.getBody(),
				constrain : true,
				layout : 'fit',
				items : [opForm]
			});
});