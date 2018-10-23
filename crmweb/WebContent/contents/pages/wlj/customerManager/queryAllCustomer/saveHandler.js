/**
 * 客户信息管理页面的保存方法
 * @author sunjing5
 * 2017-05-26
 */
var submitFlag=false;//是否提交
var firstadd=false;        //第一页机构表未修改
/**
 * 
 * helin 添加隐藏主键字段
 * @param {}            perModel 要添加进的修改列表
 * @param {}        _tempCustId 客户号
 * @param {}          formpanel form面板
 * @param {}            key 字段
 * @param {}            fieldLabel 字段label
 */
var addKeyFn = function(perModel, _tempCustId, formpanel, key, fieldLabel) {
	var field = formpanel.getForm().findField(key);
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = field.getValue();
	pcbhModel.updateAfCont = field.getValue();
	pcbhModel.updateAfContView = field.getValue();
	pcbhModel.updateItem = fieldLabel;
	pcbhModel.updateItemEn = key;
	pcbhModel.fieldType = '1';
	pcbhModel.updateTableId = '1';
	perModel.push(pcbhModel);
};
/**
 * 
 * helin 添加隐藏主键字段并设定主键值
 * @param {}            perModel 要添加进的修改列表
 * @param {}        _tempCustId 客户号
 * @param {}          formpanel form面板
 * @param {}            key 字段
 * @param {}            fieldLabel 字段label
 */
var addKeyValueFn = function(perModel, _tempCustId, formpanel, key, fieldLabel) {
	var field = formpanel.getForm().findField(key);
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = field.getValue();
	pcbhModel.updateAfCont = _tempCustId;
	pcbhModel.updateAfContView = _tempCustId;
	pcbhModel.updateItem = fieldLabel;
	pcbhModel.updateItemEn = key;
	pcbhModel.fieldType = '1';
	pcbhModel.updateTableId = '1';
	perModel.push(pcbhModel);
};

/**
 * helin 添加隐藏字段
 * 
 * @param {}            perModel 要添加进的修改列表
 * @param {}            _tempCustId 客户号
 * @param {}            beforeValue 修改前值
 * @param {}            afterValue 修改后值
 * @param {}            key 字段
 * @param {}            updateTableId 是否主键字段:1是，''否
 * @param {}            fieldType 字段类型:1文本框，'2'日期框
 */
var addFieldFn = function(perModel, _tempCustId, beforeValue, afterValue, key,
		updateTableId, fieldType) {
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = beforeValue;
	pcbhModel.updateAfCont = afterValue;
	pcbhModel.updateAfContView = afterValue;
	pcbhModel.updateItem = '';
	pcbhModel.updateItemEn = key;
	pcbhModel.fieldType = fieldType == "2" ? "2" : "1";
	pcbhModel.updateTableId = updateTableId == "1" ? "1" : "";
	perModel.push(pcbhModel);
};

/**
 * 添加变更历史字段可显示
 * 
 * @param {}            perModel 要添加进的修改列表
 * @param {}            _tempCustId 客户号
 * @param {}            beforeValue 修改前值
 * @param {}            afterValue 修改后值
 * @param {}            key 字段
 * @param {}            updateTableId 是否主键字段:1是，''否
 * @param {}            fieldType 字段类型:1文本框，'2'日期框
 * @param {}            afterValueView 修改后显示值
 * @param {}            updateItem 修改项目
 */
var addFieldViewFn = function(perModel, _tempCustId, beforeValue, afterValue,
		key, updateTableId, fieldType, afterValueView, updateItem) {
	var pcbhModel = {};
	pcbhModel.custId = _tempCustId;
	pcbhModel.updateBeCont = beforeValue;
	pcbhModel.updateAfCont = afterValue;
	pcbhModel.updateAfContView = afterValueView;
	pcbhModel.updateItem = updateItem;
	pcbhModel.updateItemEn = key;
	pcbhModel.fieldType = fieldType == "2" ? "2" : "1";
	pcbhModel.updateTableId = updateTableId == "1" ? "1" : "";
	perModel.push(pcbhModel);
}
/**
 * 获取对公客户信息管理第一页基本信息变更历史 
 * @return firstPerModel
 */
var dealFirstJson = function(str,identArr0,	count2,	identFlag0,
		count,	count1,	addArr0,	address0,	addArr1,	address1,
		count3,	identObu,	obuFlag,
		openLicense,	openFlag,	count4,
		regisCode,	regisFlag,	count5,
		legalArr,	legalFlag,	count6,
		orgArr,orgFlag,count7,
		regisinfoArr,regisinfoFlag,count8,
		AreaRegArr,areaRegFlag,count9,
		NationRegArr,nationRegFlag,count10,
		memArr,memFlag,count11,
		saleArr,saleFlag,count12){
	var judge,judge1 = false;
//	var json2=qzCombaseInfo.form.getValues(false);
	var ident_type=qzCombaseInfo.form.findField('IDENT_TYPE').getValue();// 获取证件类型

	
 
	var comFirst = [];
	var json1 ={};
	if (str == 'update') {
		json1 = qzComStore.getAt(0).json;
		if(json1['IDENT_TYPE']!='2X'){//原来为境内，修改为境外
			qzCombaseInfo.form.findField('IDENT_NO2').setValue('');
		}
		
	} 
	
	var json2=qzCombaseInfo.form.getFieldValues(false);
	for ( var key in json2) {
		var field = qzCombaseInfo.getForm().findField(key);
		var Type = field.getXType() == 'datefield' ? '2': '1';
		if(Type=='2'&&(json2[key]!=''&&json2[key]!=null)){//日期类型
			field.setValue(json2[key].format('Y-m-d'));//给日期控件重新赋值；
			json2[key]=json2[key].format('Y-m-d');
		}

		var pcbhModel = {};
		field = qzCombaseInfo.getForm().findField(key);
		judge=(field != null && field.getValue() != null && field.getValue() != "");
		if (str == 'add') {//新增
			if (key == 'MGR_ID') {
//				addKeyFn(comFirst,custId,qzCombaseInfo,'MGR_KEY_ID','主管客户经理ID');//给主键赋值
//				addFieldFn(comFirst, custId, custId,custId, 'MGR_CUST_ID');
//				addFieldFn(comFirst, custId, '', JsContext._orgId,'BELONG_ORG');
//				addFieldFn(comFirst, custId, '', JsContext._userId,'BELONG_RM');
//				addFieldFn(comFirst, custId, '',_sysCurrDate,'EFFECT_DATE','','2');
//				addFieldFn(comFirst, custId, '','1','MAIN_TYPE');
			} 
			if (key == 'UNITID') {
				addKeyFn(comFirst,custId,qzCombaseInfo,'BRANCH_ID','归属机构主键ID');//给主键赋值
				addFieldFn(comFirst, custId, custId,custId, 'BRANCH_CUST_ID');
				addFieldFn(comFirst, custId, '', JsContext._orgId,'UNIT_BELONG_ORG');
			}
			//修改控件值变动标志
			judge1 = (field != null && field.getValue() != null && field.getValue() != "");
		} else {//修改
			//修改控件值变动标志
			if(Type=='2'){//日期类型
				judge1 = (!((json1[key] == field.value) || (null == json1[key] && (null == field.getValue()||field.getValue()==''))));
			}
			else{
				judge1 = (!((json1[key] == field.getValue()) || (null == json1[key] && (null == field.getValue()||field.getValue()==''))));
			}
		}
		if (judge) {
			// 主键的处理
			if (key == 'CUST_ID') {
//				addKeyFn(comFirst, custId, qzCombaseInfo, 'CUST_ID', '客户编号');		
//			} else if (key == 'SALE_CCY') {
//				addKeyValueFn(comFirst, custId, qzCombaseInfo, 'BUSIINFO_CUST_ID','经营id');//给主键赋值
//			} else if (key == 'BELONG_GROUP') {
//				addFieldFn(comFirst, custId, custId, custId,'GROUP_CUST_ID');
//				addKeyFn(comFirst, custId, qzCombaseInfo, 'MEMBER_ID', '集团id');
//			} else if (key == 'BELONG_RM') {
//				addKeyValueFn(comFirst, custId, qzCombaseInfo, custId,'MGR_CUST_ID');//给主键赋值
			} 
		}
		if (judge1) {
			if(key=='LOAN_CUST_STAT'){
				if(qzCombaseInfo.form.findField('LOAN_CUST_STAT').getValue()=='01'){//新增信贷临时户，记录开户时间
					addFieldFn(comFirst, custId, '',_sysCurrDate,'FIRST_LOAN_DATE','','2');
				}
			}
			// 放大镜必须单独处理
			if (key == 'IN_CLL_TYPE_ID' || key == 'REGISTER_AREA_ID'
					|| key == 'GROUP_NO'||key == 'REGISTER_TYPE_ID'
						||key=='MGR_ID'||key=='MGR_ID1'||key=='UNITID') {
				
				continue;// 放大镜隐藏字段不处理
			} else if (key == 'IN_CLL_TYPE' || key == 'REGISTER_AREA'
					|| key == 'BELONG_GROUP'||key == 'REGISTER_TYPE'
						||key=='BELONG_RM'||key=='LAST_UPDATE_USER'||key=='BELONG_ORG') {
					var tempkey = field.hiddenName ? field.hiddenName : key;
					var tempField = tempkey == key ? field : qzCombaseInfo
							.getForm().findField(tempkey);
					if (!(null == tempField.getValue())) {
						var pcbhModel = {};
    					pcbhModel.custId = custId;
    					// modify by liuming 20170824
    					// pcbhModel.updateBeCont = '';
    					pcbhModel.updateBeCont = json1[key]
    					pcbhModel.updateAfCont = tempField.getValue();
    					pcbhModel.updateAfContView = field.getValue();
    					pcbhModel.updateItem = field.fieldLabel;
    					pcbhModel.updateItemEn = field.name;// 映射表里的字段为界面上的字段，而并非隐藏项
    					pcbhModel.fieldType = '1';
    					pcbhModel.updateTableId = '';
    					comFirst.push(pcbhModel);
						// 记录用于判断集团是否修改memArr,memFlag,count11
    					if(memArr.indexOf(key) > -1 && count11 == 0) {
    						memFlag = true;
    					} else {
    						memFlag = false;
    					}
    					if(memFlag) {
    						addKeyFn(comFirst, custId, qzCombaseInfo, 'MEMBER_ID', '成员ID');
    						addFieldFn(comFirst, custId, custId, custId, 'GROUP_CUST_ID');
    						count11++;
    					}
						// 记录存于regisinfo表的字段是否修改
    					if(regisinfoArr.indexOf(key) > -1 && count8 == 0) {
    						regisinfoFlag = true;
    					} else {
    						regisinfoFlag = false;
    					}
    					if(regisinfoFlag) {
    						addKeyValueFn(comFirst, custId, qzCombaseInfo, 'REGISTER_CUST_ID', '注册id');// 给主键赋固定值
    						count8++;
    					}
						
					}
				continue;
			}
			// 对下拉框的处理
			if (field.getXType() == 'combo') {
				var s = field.getValue();
				if (null != s) {
					pcbhModel.custId = custId;
					// modify by liuming 20170824
					// pcbhModel.updateBeCont = '';
					pcbhModel.updateBeCont = getStoreFieldValue(field.store, 'key', json1[key], 'value');
					pcbhModel.updateAfCont = s;
					pcbhModel.updateAfContView = field.getRawValue();// getStoreFieldValue(field.store,'key',s,'value');
					pcbhModel.updateItem = field.fieldLabel;
					pcbhModel.updateItemEn = field.name;
					pcbhModel.fieldType = '1';
					comFirst.push(pcbhModel);
					// 记录用于判断证件类型是否修改
					if(identArr0.indexOf(key) > -1 && count2 == 0) {
						identFlag0 = true;
					} else {
						identFlag0 = false;
					}
					// 记录用于判断经营地址是否修改
					if(addArr0.indexOf(key) > -1 && count == 0) {
						address0 = true;
					} else {
						address0 = false;
					}
					// 记录用于判断注册地址是否修改
					if(addArr1.indexOf(key) > -1 && count1 == 0) {
						address1 = true;
					} else {
						address1 = false;
					}
					// 记录用于判断Obu Code是否修改
					if(identObu.indexOf(key) > -1 && count3 == 0) {
						obuFlag = true;
					} else {
						obuFlag = false;
					}
					// 记录用于判断开户许可证核准号是否修改
					if(openLicense.indexOf(key) > -1 && count4 == 0) {
						openFlag = true;
					} else {
						openFlag = false;
					}
					// 记录用于判断税务登记编号是否修改
					if(regisCode.indexOf(key) > -1 && count5 == 0) {
						regisFlag = true;
					} else {
						regisFlag = false;
					}
					// 记录用于判断地税税务登记编号是否修改
					if(AreaRegArr.indexOf(key) > -1 && count9 == 0) {
						areaRegFlag = true;
					} else {
						areaRegFlag = false;
					}
					// 记录用于判断国税税务登记编号是否修改
					if(NationRegArr.indexOf(key) > -1 && count10 == 0) {
						nationRegFlag = true;
					} else {
						nationRegFlag = false;
					}
					// 记录用于判断法人信息是否修改
					if(legalArr.indexOf(key) > -1 && count6 == 0) {
						legalFlag = true;
					} else {
						legalFlag = false;
					}
					// 记录存于org表的字段是否修改
					if(orgArr.indexOf(key) > -1 && count7 == 0) {
						orgFlag = true;
						firstadd = true;
					} else {
						orgFlag = false;
					}
					// 记录存于regisinfo表的字段是否修改
					if(regisinfoArr.indexOf(key) > -1 && count8 == 0) {
						regisinfoFlag = true;
					} else {
						regisinfoFlag = false;
					}
					// 记录存于busiinfo表的字段是否修改
					if(saleArr.indexOf(key) > -1 && count12 == 0) {
						saleFlag = true;
					} else {
						saleFlag = false;
					}
				}
			} else {
				pcbhModel.custId = custId;
				// modify by liuming 20170824
				pcbhModel.updateBeCont = json1[key];
				pcbhModel.updateAfCont = json2[key];
				pcbhModel.updateAfContView = json2[key];
				pcbhModel.updateItem = field.fieldLabel;
				pcbhModel.updateItemEn = key;
				pcbhModel.fieldType = field.getXType() == 'datefield' ? '2' : '1';
				pcbhModel.updateTableId = '';
				comFirst.push(pcbhModel);

				// 记录用于判断证件类型是否修改
				if(identArr0.indexOf(key) > -1 && count2 == 0) {
					identFlag0 = true;
				} else {
					identFlag0 = false;
				}
				// 记录用于判断经营地址是否修改
				if(addArr0.indexOf(key) > -1 && count == 0) {
					address0 = true;
				} else {
					address0 = false;
				}
				// 记录用于判断注册地址是否修改
				if(addArr1.indexOf(key) > -1 && count1 == 0) {
					address1 = true;
				} else {
					address1 = false;
				}
				// 记录用于判断Obu Code是否修改
				if(identObu.indexOf(key) > -1 && count3 == 0) {
					obuFlag = true;
				} else {
					obuFlag = false;
				}
				// 记录用于判断开户许可证核准号是否修改
				if(openLicense.indexOf(key) > -1 && count4 == 0) {
					openFlag = true;
				} else {
					openFlag = false;
				}
				// 记录用于判断税务登记编号是否修改
				if(regisCode.indexOf(key) > -1 && count5 == 0) {
					regisFlag = true;
				} else {
					regisFlag = false;
				}
				// 记录用于判断地税税务登记编号是否修改
				if(AreaRegArr.indexOf(key) > -1 && count9 == 0) {
					areaRegFlag = true;
				} else {
					areaRegFlag = false;
				}
				// 记录用于判断国税税务登记编号是否修改
				if(NationRegArr.indexOf(key) > -1 && count10 == 0) {
					nationRegFlag = true;
				} else {
					nationRegFlag = false;
				}
				// 记录用于判断法人信息是否修改
				if(legalArr.indexOf(key) > -1 && count6 == 0) {
					legalFlag = true;
				} else {
					legalFlag = false;
				}
				// 记录存于org表的字段是否修改
				if(orgArr.indexOf(key) > -1 && count7 == 0) {
					orgFlag = true;
					firstadd = true;
				} else {
					orgFlag = false;
				}
				// 记录存于regisinfo表的字段是否修改
				if(regisinfoArr.indexOf(key) > -1 && count8 == 0) {
					regisinfoFlag = true;
				} else {
					regisinfoFlag = false;
				}
				// 记录存于busiinfo表的字段是否修改
				if(saleArr.indexOf(key) > -1 && count12 == 0) {
					saleFlag = true;
				} else {
					saleFlag = false;
				}
			}
			if(identFlag0) {
				addKeyFn(comFirst, custId, qzCombaseInfo, 'IDENT_ID', '证件ID');
				addFieldFn(comFirst, custId, custId, custId, 'IDENT_CUST_ID');
				addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('CUST_NAME').getValue(), 'IDENT_CUST_NAME');
				// 级联更新证件号码与证件类型
				addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('IDENT_TYPE').getValue(), 'IDENT_IDENT_TYPE');
				addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('IDENT_NO').getValue(), 'IDENT_IDENT_NO');
				addFieldFn(comFirst, custId, '', 'Y', 'IS_OPEN_ACC_IDENT');
				addFieldFn(comFirst, custId, '', '1', 'OPEN_ACC_IDENT_MODIFIED_FLAG');
				addFieldFn(comFirst, custId, '', _sysCurrDate, 'IDENT_MODIFIED_TIME', '', '2');
				addFieldFn(comFirst, custId, '', 'CRM', 'IDENT_LAST_UPDATE_SYS', '');
				addFieldFn(comFirst, custId, '', JsContext._userId, 'IDENT_LAST_UPDATE_USER', '');
				addFieldFn(comFirst, custId, '', _sysCurrDate, 'IDENT_LAST_UPDATE_TM', '', '2');
				count2++;
			}
			if(openFlag) {
				addKeyFn(comFirst, custId, qzCombaseInfo, 'OPEN_ID', 'OPEN证件ID');
				addFieldFn(comFirst, custId, custId, custId, 'OPEN_CUST_ID');
				addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('CUST_NAME').getValue(), 'OPEN_CUST_NAME');
				// 级联更新证件号码与证件类型
				addFieldFn(comFirst, custId, '', 'Z', 'OPEN_IDENT_TYPE');
				// addFieldFn(comFirst, custId, '',
				// qzCombaseInfo.getForm().findField('ACC_OPEN_LICENSE').getValue(),'ACC_OPEN_LICENSE');
				addFieldFn(comFirst, custId, '', _sysCurrDate, 'OPEN_IDENT_MODIFIED_TIME', '', '2');
				addFieldFn(comFirst, custId, '', 'CRM', 'OPEN_LAST_UPDATE_SYS', '');
				addFieldFn(comFirst, custId, '', JsContext._userId, 'OPEN_LAST_UPDATE_USER', '');
				addFieldFn(comFirst, custId, '', _sysCurrDate, 'OPEN_LAST_UPDATE_TM', '', '2');
				count4++;
			}
			if(obuFlag) {
				// 级联更新证件号码与证件类型
				if(str == 'update') {
					if(json1['IDENT_TYPE'] != '2X') {// 原来为境内，修改为境外

						count3++;
					} else {// 修改为境外
						addKeyFn(comFirst, custId, qzCombaseInfo, 'OBU_ID', 'OBU证件ID');
						addFieldFn(comFirst, custId, custId, custId, 'OBU_CUST_ID');
						addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('CUST_NAME').getValue(), 'OBU_CUST_NAME');
						addFieldFn(comFirst, custId, '', '15X', 'OBU_IDENT_TYPE');// 新增
						addFieldFn(comFirst, custId, '', _sysCurrDate, 'OBU_IDENT_MODIFIED_TIME', '', '2');
						addFieldFn(comFirst, custId, '', 'CRM', 'OBU_LAST_UPDATE_SYS', '');
						addFieldFn(comFirst, custId, '', JsContext._userId, 'OBU_LAST_UPDATE_USER', '');
						addFieldFn(comFirst, custId, '', _sysCurrDate, 'OBU_LAST_UPDATE_TM', '', '2');
						count3++;
					}
				} else {
					addKeyFn(comFirst, custId, qzCombaseInfo, 'OBU_ID', 'OBU证件ID');
					addFieldFn(comFirst, custId, custId, custId, 'OBU_CUST_ID');
					addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('CUST_NAME').getValue(), 'OBU_CUST_NAME');
					addFieldFn(comFirst, custId, '', '15X', 'OBU_IDENT_TYPE');// 新增
					addFieldFn(comFirst, custId, '', _sysCurrDate, 'OBU_IDENT_MODIFIED_TIME', '', '2');
					addFieldFn(comFirst, custId, '', 'CRM', 'OBU_LAST_UPDATE_SYS', '');
					addFieldFn(comFirst, custId, '', JsContext._userId, 'OBU_LAST_UPDATE_USER', '');
					addFieldFn(comFirst, custId, '', _sysCurrDate, 'OBU_LAST_UPDATE_TM', '', '2');
					count3++;
				}
			}
			if(regisFlag) {
				addKeyFn(comFirst, custId, qzCombaseInfo, 'REGIS_ID', 'REGIS证件ID');
				addFieldFn(comFirst, custId, custId, custId, 'REGIS_CUST_ID');
				addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('CUST_NAME').getValue(), 'REGIS_CUST_NAME');
				// 级联更新证件号码与证件类型
				addFieldFn(comFirst, custId, '', 'V', 'REGIS_IDENT_TYPE');
				// addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('SW_REGIS_CODE').getValue(),'SW_REGIS_CODE');
				addFieldFn(comFirst, custId, '', _sysCurrDate, 'REGIS_IDENT_MODIFIED_TIME', '', '2');
				addFieldFn(comFirst, custId, '', 'CRM', 'REGIS_LAST_UPDATE_SYS', '');
				addFieldFn(comFirst, custId, '', JsContext._userId, 'REGIS_LAST_UPDATE_USER', '');
				addFieldFn(comFirst, custId, '', _sysCurrDate, 'REGIS_LAST_UPDATE_TM', '', '2');
				count4++;
			}
			if(areaRegFlag) {// 地税
				addKeyFn(comFirst, custId, qzCombaseInfo, 'AREA_REG_ID', '地税证件ID');
				addFieldFn(comFirst, custId, custId, custId, 'AREA_CUST_ID');
				addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('CUST_NAME').getValue(), 'AREA_CUST_NAME');
				// 级联更新证件号码与证件类型
				addFieldFn(comFirst, custId, '', 'Y', 'AREA_IDENT_TYPE');
				addFieldFn(comFirst, custId, '', _sysCurrDate, 'AREA_IDENT_MODIFIED_TIME', '', '2');
				addFieldFn(comFirst, custId, '', 'CRM', 'AREA_LAST_UPDATE_SYS', '');
				addFieldFn(comFirst, custId, '', JsContext._userId, 'AREA_LAST_UPDATE_USER', '');
				addFieldFn(comFirst, custId, '', _sysCurrDate, 'AREA_LAST_UPDATE_TM', '', '2');
				count9++;
			}
			if(nationRegFlag) {// 国税
				addKeyFn(comFirst, custId, qzCombaseInfo, 'NATION_REG_ID', '国税证件ID');
				addFieldFn(comFirst, custId, custId, custId, 'NATION_CUST_ID');
				addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('CUST_NAME').getValue(), 'NATION_CUST_NAME');
				// 级联更新证件号码与证件类型
				addFieldFn(comFirst, custId, '', 'W', 'NATION_IDENT_TYPE');
				addFieldFn(comFirst, custId, '', _sysCurrDate, 'NATION_IDENT_MODIFIED_TIME', '', '2');
				addFieldFn(comFirst, custId, '', 'CRM', 'NATION_LAST_UPDATE_SYS', '');
				addFieldFn(comFirst, custId, '', JsContext._userId, 'NATION_LAST_UPDATE_USER', '');
				addFieldFn(comFirst, custId, '', _sysCurrDate, 'NATION_LAST_UPDATE_TM', '', '2');
				count10++;
			}
			if(address0) {
				addKeyFn(comFirst, custId, qzCombaseInfo, 'ADDR_ID0', '地址0ID');
				addFieldFn(comFirst, custId, '', custId, 'ADDRESS_CUST_ID0');
				// addFieldFn(comFirst, custId, '',
				// qzCombaseInfo.getForm().findField('ADDR0').getValue(),'ADDR0');//
				// 经营地址，08
				addFieldFn(comFirst, custId, '', '08', 'ADDR_TYPE0');
				// addFieldFn(comFirst, custId, '',
				// qzCombaseInfo.getForm().findField('ADMIN_ZONE').getValue(),'ADMIN_ZONE0');
				addFieldFn(comFirst, custId, '', 'CRM', 'ADDRESS_LAST_UPDATE_SYS0', '');
				addFieldFn(comFirst, custId, '', JsContext._userId, 'ADDRESS_LAST_UPDATE_USER0', '');
				addFieldFn(comFirst, custId, '', _sysCurrDate, 'ADDRESS_LAST_UPDATE_TM0', '', '2');
				count++;
			}
			if(address1) {
				addKeyFn(comFirst, custId, qzCombaseInfo, 'ADDR_ID1', '地址1ID');
				addFieldFn(comFirst, custId, '', custId, 'ADDRESS_CUST_ID1');
				addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('REGISTER_ADDR').getValue(), 'ADDR1');// 注册地址，07
				addFieldFn(comFirst, custId, '', '07', 'ADDR_TYPE1');
				// addFieldFn(comFirst, custId, '',
				// qzCombaseInfo.getForm().findField('ADMIN_ZONE1').getValue(),'ADMIN_ZONE1');
				addFieldFn(comFirst, custId, '', 'CRM', 'ADDRESS_LAST_UPDATE_SYS1', '');
				addFieldFn(comFirst, custId, '', JsContext._userId, 'ADDRESS_LAST_UPDATE_USER1', '');
				addFieldFn(comFirst, custId, '', _sysCurrDate, 'ADDRESS_LAST_UPDATE_TM1', '', '2');
				count1++;
			}
			if(legalFlag) {
				addKeyFn(comFirst, custId, qzCombaseInfo, 'LEGAL_LINKMAN_ID', '法人id');
				addFieldFn(comFirst, custId, '', custId, 'LEGAL_ORG_CUST_ID');
				// addFieldFn(comFirst, custId, '',
				// qzCombaseInfo.getForm().findField('LEGAL_REPR_IDENT_NO').getValue(),
				// 'LEGAL_REPR_IDENT_NO');// 法人证件号码
				addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('LEGAL_REPR_IDENT_TYPE').getValue(), 'LEGAL_REPR_IDENT_TYPE1');// 法人证件类型
				addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('LEGAL_REPR_IDENT_NO').getValue(), 'LEGAL_REPR_IDENT_NO1');// 法人证件号码
				addFieldFn(comFirst, custId, '', qzCombaseInfo.getForm().findField('LEGAL_REPR_NAME').getValue(), 'LEGAL_REPR_NAME1');// 法人姓名
				addFieldFn(comFirst, custId, '', '5', 'LEGAL_LINKMAN_TYPE');// 联系人类型为法人，5
				addFieldFn(comFirst, custId, '', 'CRM', 'LEGAL_LAST_UPDATE_SYS', '');
				addFieldFn(comFirst, custId, '', JsContext._userId, 'LEGAL_LAST_UPDATE_USER', '');
				addFieldFn(comFirst, custId, '', _sysCurrDate, 'LEGAL_LAST_UPDATE_TM', '', '2');
				count6++;
			}
			if(orgFlag) {
				addKeyValueFn(comFirst, custId, qzCombaseInfo, 'ORG1_CUST_ID', '机构id');// 给主键赋固定值
				count7++;
			}
			if(regisinfoFlag) {
				addKeyValueFn(comFirst, custId, qzCombaseInfo, 'REGISTER_CUST_ID', '注册id');// 给主键赋固定值
				count8++;
			}
			if(saleFlag) {
				addKeyValueFn(comFirst, custId, qzCombaseInfo, 'BUSIINFO_CUST_ID', '经营id');// 给主键赋值
				count12++;
			}
		}
	}
	return comFirst;
}
/**
 * 处理第一页json数据
 * @param {} custId 客户号
 * @return {Object}
 */
var getComFirstModel = function(custId) {
	var viewtitle = getCustomerViewByIndex(0).title;
	var identArr0 = [ 'IDENT_TYPE', 'IDENT_NO', 'IDENT_END_DATE','IDENT_CUST_ID' ];// 证件存储时的格式
	var count2 = 0; 
	var identFlag0 = false; // 证件是否修改的标志
	// 地址信息的同步更新
	var count = 0, count1 = 0;
	var addArr0 = [ 'ADDR0' ];// 经营地址
	var address0 = false;
	var addArr1 = [ 'REGISTER_ADDR' ];// 注册地址
	var address1 = false;

	//Obu Code 的同步更新
	var count3=0;
	var identObu=['IDENT_NO2','OBU_CUST_ID'];//IDENT_NO2
	var obuFlag=false;//obu是否修改的标志

	//开户许可证核准号的同步更新
	var openLicense = [ 'ACC_OPEN_LICENSE'];
	var openFlag = false;
	var count4=0;
	
	//税务登记证编号的同步更新
	var regisCode = [ 'SW_REGIS_CODE'];
	var regisFlag = false;
	var count5=0;
	
	//地税税务登记证编号的同步更新
	var AreaRegArr = [ 'AREA_REG_CODE'];
	var areaRegFlag = false;
	var count9=0;
	
	//国税税务登记证编号的同步更新
	var NationRegArr = [ 'NATION_REG_CODE'];
	var nationRegFlag = false;
	var count10=0;
	
	// 法人信息的同步更新
	var legalArr = [ 'LEGAL_REPR_NAME','LEGAL_REPR_IDENT_NO', 'LEGAL_REPR_IDENT_TYPE' ,'LEGAL_IDENT_EXPIRED_DATE'];
	var legalFlag = false;
	var count6=0;
	
	//机构表的字段更新
	var orgArr=['ORG_CUST_TYPE', 'FLAG_CAP_DTL', 'LOAN_ORG_TYPE', 'NATION_CODE', 'IN_CLL_TYPE', 'EMPLOYEE_SCALE',
	            'INVEST_TYPE', 'ORG_TYPE', 'ENT_SCALE', 'MAIN_BUSINESS', 'CREDIT_CODE', 'HQ_NATION_CODE', 'ENT_PROPERTY', 
	            'COM_HOLD_TYPE', 'BUILD_DATE', 'ENT_SCALE_CK', 'MINOR_BUSINESS', 'TOTAL_ASSETS', 'LEGAL_REPR_NAME', 
	            'LEGAL_REPR_IDENT_NO', 'LEGAL_REPR_IDENT_TYPE', 'ANNUAL_INCOME', 'LOAN_CARD_NO', 'BUSI_LIC_NO', 'REMARK', 'AREA_CODE' ];
	var orgFlag=false;
    var count7=0;
	
	//注册信息的字段更新
	var regisinfoArr=['REGISTER_DATE','REGISTER_AREA','REGISTER_CAPITAL_CURR','END_DATE','REGISTER_NO','REGISTER_ADDR',
	              'REG_CODE_TYPE','REGISTER_TYPE','REGISTER_CAPITAL'];
	var regisinfoFlag=false;
	var count8=0;
	
	//集团信息是否修改
	var memArr=['BELONG_GROUP'];
	var memFlag=false;
	var count11=0;
	
	//年销售额币别
	var saleArr=['SALE_CCY','SALE_AMT'];
	var saleFlag=false;
	var count12=0;
	
	//归属客户经理
//	var belongArr=['BELONG_ORG','BELONG_RM'];
//	var belongFalg=false;
//	var count13=0;
	

	//CUST_ID
	qzCombaseInfo.form.findField('CUST_ID').setValue(custId);
	if(qzComStore.data.length==0){//新增
		comFirst=dealFirstJson('add',identArr0,	count2,	identFlag0,
				count,	count1,	addArr0,	address0,	addArr1,	address1,
				count3,	identObu,	obuFlag,
				openLicense,	openFlag,	count4,
				regisCode,	regisFlag,	count5,
				legalArr,	legalFlag,	count6,
				orgArr,orgFlag,count7,
				regisinfoArr,regisinfoFlag,count8,
				AreaRegArr,areaRegFlag,count9,
				NationRegArr,nationRegFlag,count10,
				memArr,memFlag,count11,
				saleArr,saleFlag,count12);
	}
	else{//非新增
		comFirst=dealFirstJson('update',identArr0,	count2,	identFlag0,
				count,	count1,	addArr0,	address0,	addArr1,	address1,
				count3,	identObu,	obuFlag,
				openLicense,	openFlag,	count4,
				regisCode,	regisFlag,	count5,
				legalArr,	legalFlag,	count6,
				orgArr,orgFlag,count7,
				regisinfoArr,regisinfoFlag,count8,
				AreaRegArr,areaRegFlag,count9,
				NationRegArr,nationRegFlag,count10,
				memArr,memFlag,count11,
				saleArr,saleFlag,count12);
	}
	
	return comFirst;
};
/**
 * 获取对公客户信息管理第二页基本信息变更历史
 * 
 * @return firstPerModel
 */
var dealSecondJson=function(str,keyArr,keyFlag,count,
		stockArr,stockFlag,count1){
	var comSecond = [];
	var judge,judge1=false;
//	var json2 = qzComLists.form.getValues(false);
	var json2=qzComLists.form.getFieldValues(false);
	var json1 = {};
	if(str=='update'){
		json1 = qzComListsStore.getAt(0).json;
	}
	for ( var key in json2) {
		var pcbhModel = {};
		var field = qzComLists.getForm().findField(key);
		var Type = field.getXType() == 'datefield' ? '2': '1';
		if(Type=='2'&&(json2[key]!=''&&json2[key]!=null)){//日期类型
			field.setValue(json2[key].format('Y-m-d'));//给日期控件重新赋值；
			json2[key]=json2[key].format('Y-m-d');
		}
		judge=(field != null && field.getValue() != null&& field.getValue() != "");
		if(str=='add'){
			judge1=(field != null && field.getValue() != null&& field.getValue() != "");
		}
		else{
			if(Type=='2'){//日期类型
				judge1 = (!((json1[key] == field.value) || (null == json1[key] && (null == field.getValue()||field.getValue()==''))));
			}
			else{
				judge1 = (!((json1[key] == field.getValue()) || (null == json1[key] && (null == field.getValue()||field.getValue()==''))));
			}
		}
		// 有值时的处理
		if (judge) {
			// 主键的处理
		    if (key == 'SCIENTIFIC_RANGE') {				
				addKeyValueFn(comSecond, custId, qzComLists,'SCIENCE_CUST_ID', '科技型id');//给主键赋值
			} 
		}
		// 放大镜必须单独处理:无，略
		// 对下拉框的处理
		if (judge1) {
			if (field.getXType() == 'combo') {
				var s = field.getValue();
				if (null != s) {
					pcbhModel.custId = custId;
					//modify by liuming 20170824
//					pcbhModel.updateBeCont = '';
					pcbhModel.updateBeCont = getStoreFieldValue(field.store,'key',json1[key],'value');
					pcbhModel.updateAfCont = s;
					pcbhModel.updateAfContView = field.getRawValue();// getStoreFieldValue(field.store,'key',s,'value');
					pcbhModel.updateItem = field.fieldLabel;
					pcbhModel.updateItemEn = field.name;
					pcbhModel.fieldType = '1';
					comSecond.push(pcbhModel);
					 //记录用于判断特许行业许可证是否修改且为“是”
					 if(keyArr.indexOf(key) > - 1 &&count==0){
						 keyFlag = true;
					 }
					 else{keyFlag = false;}
					 //记录用于判断股票与上市地是否修改且为“是”
					 if(stockArr.indexOf(key) > - 1 &&count1==0){
						 stockFlag = true;
					 }
					 else{stockFlag = false;}
				}
			} else {
				pcbhModel.custId = custId;
				//modify by liuming 20170824
				pcbhModel.updateBeCont = json1[key];
				pcbhModel.updateAfCont = json2[key];
				pcbhModel.updateAfContView = json2[key];
				pcbhModel.updateItem = field.fieldLabel;
				pcbhModel.updateItemEn = key;
				pcbhModel.fieldType = field.getXType() == 'datefield' ? '2': '1';
				pcbhModel.updateTableId = '';
				comSecond.push(pcbhModel);
				 //记录用于判断特许行业许可证是否修改且为“是”
				 if(keyArr.indexOf(key) > - 1 &&count==0){
					 keyFlag = true;
				 }
				 else{keyFlag = false;}
				 //记录用于判断股票与上市地是否修改且为“是”
				 if(stockArr.indexOf(key) > - 1 &&count1==0){
					 stockFlag = true;
				 }
				 else{stockFlag = false;}
			}if(keyFlag){
				addKeyValueFn(comSecond, custId, qzComLists,'KEY_CUST_ID', '重要标识id');
				count++;
			}if(stockFlag){
				addFieldFn(comSecond, custId, '', custId,'STOCK_CUST_ID');
				addKeyFn(comSecond, custId, qzComLists, 'ISSUE_STOCK_ID','股票id');
				count1++;
			}
		}
	}
	return comSecond;	
};
var getComSecondModel = function(custId) {
	var viewtitle = getCustomerViewByIndex(0).title;
	var comSecond = [];

	//keyflag信息的同步
	var keyArr=['IS_LISTED_CORP','IS_NOT_LOCAL_ENT','IS_STEEL_ENT','IS_FAX_TRANS_CUST','IS_MATERIAL_RISK','IS_RURAL','IS_SCIENCE_TECH',
	            'ENERGY_SAVING','IS_TAIWAN_CORP','IS_NEW_CORP','SHIPPING_IND','ENVIRO_PENALTIES','IS_HIGH_POLLUTE'];
	var keyFlag=false;
	var count = 0;

	//上市地等信息的同步
	var stockArr=['STOCK_CODE','MARKET_PLACE'];
	var stockFlag=false;
	var count1=0;

	if(qzComListsStore.data.length==0){//新增
		comSecond=dealSecondJson('add',keyArr,keyFlag,count,
				stockArr,stockFlag,count1);
	}
	else{//非新增
		comSecond=dealSecondJson('update',keyArr,keyFlag,count,
				stockArr,stockFlag,count1);
	}
	return comSecond;
};
/**
 * 获取对公客户信息管理第四页基本信息变更历史
 * 
 * @return firstPerModel
 */
var dealForthJson=function(str,agentArr,agentFlag,count
		,orgArr,orgFlag,count1
		){
//	
	var orgcustId=qzCombaseInfo.form.findField('ORG1_CUST_ID').getValue();
	
	var comFourth = [];

//	var json2 = qzComOther1Info.form.getValues(false);
	var json2=qzComOther1Info.form.getFieldValues(false);
	
	var judge,judge1=false;
	var json1 = {};
	if(str=='update'){
		json1 = qzComOther1InfoStore.getAt(0).json;
	}
	for ( var key in json2) {
		var pcbhModel = {};
		var field = qzComOther1Info.getForm().findField(key);
		var Type = field.getXType() == 'datefield' ? '2': '1';
		if(Type=='2'&&(json2[key]!=''&&json2[key]!=null)){//日期类型
			field.setValue(json2[key].format('Y-m-d'));//给日期控件重新赋值；
			json2[key]=json2[key].format('Y-m-d');
		}
		judge=(field != null && field.getValue() != null
				&& field.getValue() != "");
		// 有值时的处理
		if(str=='add'){
			judge1=(field != null && field.getValue() != null
					&& field.getValue() != "");
		}else{
			if(Type=='2'){//日期类型
				judge1 = (!((json1[key] == field.value) || (null == json1[key] && (null == field.getValue()||field.getValue()==''))));
			}
			else{
				judge1 = (!((json1[key] == field.getValue()) || (null == json1[key] && (null == field.getValue()||field.getValue()==''))));
			}
		}
		if (judge) {
			// 主键的处理
		}
		// 对下拉框的处理
		if(judge1){
			if (field.getXType() == 'combo') {
				var s = field.getValue();
				if (null != s) {
					pcbhModel.custId = custId;
					//modify by liuming 20170824
//					pcbhModel.updateBeCont = '';
					pcbhModel.updateBeCont = getStoreFieldValue(field.store,'key',json1[key],'value');
					pcbhModel.updateAfCont = s;
					pcbhModel.updateAfContView = field.getRawValue();// getStoreFieldValue(field.store,'key',s,'value');
					pcbhModel.updateItem = field.fieldLabel;
					pcbhModel.updateItemEn = field.name;
					pcbhModel.fieldType = '1';
					comFourth.push(pcbhModel);
					 //记录用于判断代理人是否修改
					 if(agentArr.indexOf(key) > - 1 &&count==0){
						 agentFlag = true;
					 }
					 else{agentFlag = false;}
					//记录用于判断机构是否修改
					 if(orgArr.indexOf(key) > - 1 &&count1==0&&orgcustId==''&&	firstadd==false){
						 orgFlag = true;
					 }
					 else{orgFlag = false;}
				}
			} else {
				pcbhModel.custId = custId;
				//modify by liuming 20170824
				pcbhModel.updateBeCont = json1[key];
				pcbhModel.updateAfCont = json2[key];
				pcbhModel.updateAfContView = json2[key];
				pcbhModel.updateItem = field.fieldLabel;
				pcbhModel.updateItemEn = key;
				pcbhModel.fieldType = field.getXType() == 'datefield' ? '2'
						: '1';
				pcbhModel.updateTableId = '';
				comFourth.push(pcbhModel);
				//记录用于判断代理人是否修改
				 if(agentArr.indexOf(key) > - 1 &&count==0){
					 agentFlag = true;
				 }
				 else{agentFlag = false;}
				
				//记录用于判断机构是否修改
				 if(orgArr.indexOf(key) > - 1 &&count1==0&&orgcustId==''&&firstadd==false){
					 orgFlag = true;
				 }
				 else{orgFlag = false;}
			}
			if(agentFlag){
				addFieldFn(comFourth, custId, '', custId,'GRADE_CUST_ID');
				addKeyFn(comFourth, custId, qzComOther1Info, 'AGENT_ID','代理人id');
				count++;
			}
			if(orgFlag){
				addKeyValueFn(comFourth, custId, qzComOther1Info, 'ORG1_CUST_ID','机构客户id');
				count1++;
			}
		}	
	}
	return comFourth;
}

var getComFourthModel = function(custId) {
//	
	var viewtitle = getCustomerViewByIndex(0).title;
	//	qzComOther1Info.form.findField('GRADE_CUST_ID').setValue(custId);
	//代理人信息的同步
	var agentArr=["AGENT_NATION_CODE","AGENT_ID","GRADE_IDENT_TYPE","TEL","AGENT_NAME","GRADE_IDENT_NO"];
	var agentFlag=false;
	var count = 0;
	
	//机构表信息的同步
	var orgArr=["SUPER_DEPT","ORG_STATE","YEAR_RATE","ENT_BELONG","BAS_CUS_STATE"];
	var orgFlag=false;
	var count1 = 0;

	var comFourth = [];
	if(qzComOther1InfoStore.data.length==0){//新增
		comFourth=dealForthJson('add',agentArr,agentFlag,count
				,orgArr,orgFlag,count1
				);
	}
	else{//非新增
		comFourth=dealForthJson('update',agentArr,agentFlag,count
				,orgArr,orgFlag,count1
				);
	}
	return comFourth;
};


/**
 * 返回处罚发生日期信息变更历史
 * @return allUpdateModelArr
 */
window.getComModel_Happendate = function(custId) {
	var allUpdateModelArr = [];
	addHappenStore.each(function(record) {
				// 1新增,0修改
				if (record.data.IS_ADD_FLAG == '1') {
					var perModel = [];
					addFieldFn(perModel, custId, '', '', 'HAPPEN_ID', '1');// 主键字段
					addFieldFn(perModel, custId, custId, custId,'HAPPEN_CUST_ID', '');
					if (record.data.PENALIZE_TYPE != '')
						addFieldViewFn(perModel, custId, '',record.data.PENALIZE_TYPE, 'PENALIZE_TYPE', '', '1',record.data.PENALIZE_TYPE_ORA, '是否发生处罚');
					if (record.data.HAPPEN_DATE != '')
						addFieldViewFn(perModel, custId, '',record.data.HAPPEN_DATE, 'HAPPEN_DATE', '', '2',record.data.HAPPEN_DATE, '发生日期');
					allUpdateModelArr.push({
						IS_ADD_FLAG : '1',
						permodel : perModel
					});
				} else if (record.data.IS_ADD_FLAG == '0') {
					var isActUpdateFlag = false;
					var tempData = tempHappenDateStore.getAt(tempHappenDateStore.findExact('HAPPEN_ID', record.data.HAPPEN_ID)).data;
					var perModel = [];
					addFieldFn(perModel, custId, tempData.ADDR_ID,tempData.HAPPEN_ID, 'HAPPEN_ID', '1');// 主键字段
					addFieldFn(perModel, custId, custId, custId,'HAPPEN_CUST_ID', '');
					if (record.data.PENALIZE_TYPE != tempData.PENALIZE_TYPE) {
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId,tempData.PENALIZE_TYPE_ORA, record.data.ADDR_TYPE,'PENALIZE_TYPE', '', '1',record.data.PENALIZE_TYPE_ORA, '是否发生处罚');
					}
					if (record.data.HAPPEN_DATE != tempData.HAPPEN_DATE) {
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId, tempData.ZIPCODE,record.data.HAPPEN_DATE, 'HAPPEN_DATE', '', '2',	record.data.ZIPCODE, '发生日期');
					}
					if (isActUpdateFlag) {
						allUpdateModelArr.push({
							IS_ADD_FLAG : '0',
							permodel : perModel
						});
					}
				}
			});
	return allUpdateModelArr;
};

/**
 * 返回地址信息变更历史
 * @return allUpdateModelArr
 */
window.getComModel_Address = function(custId) {
	var allUpdateModelArr = [];
	addrCustInfoStore.each(function(record) {
				// 1新增,0修改
				if (record.data.IS_ADD_FLAG == '1') {
					var perModel = [];
					addFieldFn(perModel, custId, '', '', 'ADDR_ID', '1');// 主键字段
					addFieldFn(perModel, custId, custId, custId,'ADDRESS_CUST_ID', '');
					if (record.data.ADDR_TYPE != '')
						addFieldViewFn(perModel, custId, '',record.data.ADDR_TYPE, 'ADDR_TYPE', '', '1',record.data.ADDR_TYPE_ORA, '地址类型');
					if (record.data.ZIPCODE != '')
						addFieldViewFn(perModel, custId, '',record.data.ZIPCODE, 'ZIPCODE', '', '1',record.data.ZIPCODE, '邮政编码');
					if (record.data.ADDR != '')
						addFieldViewFn(perModel, custId, '', record.data.ADDR,'ADDR', '', '1', record.data.ADDR, '地址');
					if (record.data.ADDR_COUNTRY != '')
						addFieldViewFn(perModel, custId, '',record.data.ADDR_COUNTRY, 'ADDR_COUNTRY', '', '1',record.data.ADDR_COUNTRY_ORA, '国别');
					if (record.data.ADMIN_ZONE_ID != '')
						addFieldViewFn(perModel, custId, '',record.data.ADMIN_ZONE_ID, 'ADMIN_ZONE_ID', '', '1',record.data.ADMIN_ZONE, '行政区划');
					addFieldFn(perModel, custId, '',record.data.ADDRESS_LAST_UPDATE_SYS,'ADDRESS_LAST_UPDATE_SYS', '');
					addFieldFn(perModel, custId, '',record.data.ADDRESS_LAST_UPDATE_USER,'ADDRESS_LAST_UPDATE_USER', '');
					addFieldFn(perModel, custId, '',record.data.ADDRESS_LAST_UPDATE_TM,'ADDRESS_LAST_UPDATE_TM', '', '2');
					allUpdateModelArr.push({
						IS_ADD_FLAG : '1',
						permodel : perModel
					});
				} else if (record.data.IS_ADD_FLAG == '0') {
					var isActUpdateFlag = false;
					var tempData = tempComAddrStore.getAt(tempComAddrStore.findExact('ADDR_ID', record.data.ADDR_ID)).data;
					var perModel = [];
					addFieldFn(perModel, custId, tempData.ADDR_ID,tempData.ADDR_ID, 'ADDR_ID', '1');// 主键字段
					addFieldFn(perModel, custId, custId, custId,'ADDRESS_CUST_ID', '');
					if (record.data.ADDR_TYPE != tempData.ADDR_TYPE) {
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId,tempData.ADDR_TYPE_ORA, record.data.ADDR_TYPE,'ADDR_TYPE', '', '1',record.data.ADDR_TYPE_ORA, '地址类型');
					}
					if (record.data.ZIPCODE != tempData.ZIPCODE) {
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId, tempData.ZIPCODE,record.data.ZIPCODE, 'ZIPCODE', '', '1',	record.data.ZIPCODE, '邮政编码');
					}
					if (record.data.ADDR != tempData.ADDR) {
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId, tempData.ADDR,record.data.ADDR, 'ADDR', '', '1',record.data.ADDR, '地址');
					}
					if (record.data.ADDR_COUNTRY !=tempData.ADDR_COUNTRY){
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId, tempData.ADDR_COUNTRY,record.data.ADDR_COUNTRY, 'ADDR_COUNTRY', '', '1',record.data.ADDR_COUNTRY_ORA, '国别');
					}	
					if (record.data.ADMIN_ZONE_ID != tempData.ADMIN_ZONE_ID){
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId, tempData.ADMIN_ZONE_ID,record.data.ADMIN_ZONE_ID, 'ADMIN_ZONE_ID', '', '1',record.data.ADMIN_ZONE, '行政区划');
					}
					addFieldFn(perModel, custId,tempData.ADDRESS_LAST_UPDATE_SYS,record.data.ADDRESS_LAST_UPDATE_SYS,'ADDRESS_LAST_UPDATE_SYS', '');
					addFieldFn(perModel, custId,tempData.ADDRESS_LAST_UPDATE_USER,record.data.ADDRESS_LAST_UPDATE_USER,'ADDRESS_LAST_UPDATE_USER', '');
					addFieldFn(perModel, custId,tempData.ADDRESS_LAST_UPDATE_TM,record.data.ADDRESS_LAST_UPDATE_TM,'ADDRESS_LAST_UPDATE_TM', '', '2');
					if (isActUpdateFlag) {
						allUpdateModelArr.push({
							IS_ADD_FLAG : '0',
							permodel : perModel
						});
					}
				}
			});
	return allUpdateModelArr;
};
/**
 * 返回联系人信息变更历史
 * 
 * @return allUpdateModelArr
 */
window.getComModel_LINKMAN = function(custId) {
//	
	var allUpdateModelArr = [];
	comContactPersonStore.each(function(record) {
		// 1新增,0修改
		if (record.data.PER_IS_ADD_FLAG == '1') {
			var perModel = [];
			addFieldFn(perModel, custId, '', '','LINKMAN_ID', '1');// 主键字段
			addFieldFn(perModel, custId, custId, custId, 'ORG_CUST_ID', '');
			if (record.data.LINKMAN_TYPE != '')
				addFieldViewFn(perModel, custId, '', record.data.LINKMAN_TYPE,'LINKMAN_TYPE', '', '1', record.data.LINKMAN_TYPE_ORA,'联系人类型');
			if (record.data.LINKMAN_NAME != '')
				addFieldViewFn(perModel, custId, '', record.data.LINKMAN_NAME,'LINKMAN_NAME', '', '1', record.data.LINKMAN_NAME,'联系人姓名');
			if (record.data.IDENT_TYPE != '')
				addFieldViewFn(perModel, custId, '', record.data.IDENT_TYPE,'IDENT_TYPE', '', '1', record.data.IDENT_TYPE_ORA,'联系人证件类型');
			if (record.data.IDENT_NO != '')
				addFieldViewFn(perModel, custId, '', record.data.IDENT_NO,'IDENT_NO', '', '1', record.data.IDENT_NO, '联系人证件号码');
			if (record.data.IDENT_EXPIRED_DATE != '')
				addFieldViewFn(perModel, custId, '',record.data.IDENT_EXPIRED_DATE, 'IDENT_EXPIRED_DATE','', '2', record.data.IDENT_EXPIRED_DATE, '联系人证件失效日期');
			if (record.data.GENDER != '')
				addFieldViewFn(perModel, custId, '', record.data.GENDER,
						'GENDER', '', '1', record.data.GENDER_ORA, '联系人性别');
			if (record.data.BIRTHDAY != '')
				addFieldViewFn(perModel, custId, '', record.data.BIRTHDAY,
						'BIRTHDAY', '', '2', record.data.BIRTHDAY, '联系人出生日期');
			if (record.data.LINKMAN_TITLE != '')
				addFieldViewFn(perModel, custId, '', record.data.LINKMAN_TITLE,
						'LINKMAN_TITLE', '', '1',
						record.data.LINKMAN_TITLE_ORA, '联系人称谓');
			if (record.data.FEX != '')
				addFieldViewFn(perModel, custId, '', record.data.FEX, 'FEX',
						'', '1', record.data.FEX, '联系人传真号码');
			if (record.data.HOME_TEL != '')
				addFieldViewFn(perModel, custId, '', record.data.HOME_TEL,
						'HOME_TEL', '', '1', record.data.HOME_TEL, '联系人家庭电话');
			if (record.data.MOBILE != '')
				addFieldViewFn(perModel, custId, '', record.data.MOBILE,
						'MOBILE', '', '1', record.data.MOBILE, '联系人手机号码');
			if (record.data.MOBILE2 != '')
				addFieldViewFn(perModel, custId, '', record.data.MOBILE2,
						'MOBILE2', '', '1', record.data.MOBILE2, '联系人手机号码2');
			if (record.data.OFFICE_TEL != '')
				addFieldViewFn(perModel, custId, '', record.data.OFFICE_TEL,
						'OFFICE_TEL', '', '1', record.data.OFFICE_TEL,
						'联系人办公电话');
			if (record.data.EMAIL != '')
				addFieldViewFn(perModel, custId, '', record.data.EMAIL,
						'EMAIL', '', '1', record.data.EMAIL, '联系人电子邮件');
			addFieldFn(perModel, custId, '', record.data.PER_LAST_UPDATE_SYS,
					'PER_LAST_UPDATE_SYS', '');
			addFieldFn(perModel, custId, '', record.data.PER_LAST_UPDATE_USER,
					'PER_LAST_UPDATE_USER', '');
			addFieldFn(perModel, custId, '', record.data.PER_LAST_UPDATE_TM,
					'PER_LAST_UPDATE_TM', '', '2');
			allUpdateModelArr.push({
				PER_IS_ADD_FLAG : '1',
				permodel : perModel
			});
		} else if (record.data.PER_IS_ADD_FLAG == '0') {
			var isActUpdateFlag = false;
			var tempData = tempComContactPersonStore
					.getAt(tempComContactPersonStore.findExact('LINKMAN_ID',
							record.data.LINKMAN_ID)).data;
			var perModel = [];
			addFieldFn(perModel, custId, tempData.LINKMAN_ID,
					tempData.LINKMAN_ID, 'LINKMAN_ID', '1');// 主键字段
			addFieldFn(perModel, custId, custId, custId, 'LINKMAN_CUST_ID',
					'');
			if (record.data.LINKMAN_TYPE != tempData.LINKMAN_TYPE) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.LINKMAN_TYPE,
						record.data.LINKMAN_TYPE, 'LINKMAN_TYPE', '', '1',
						record.data.LINKMAN_TYPE_ORA, '联系人类型');
			}
			if (record.data.LINKMAN_NAME != tempData.LINKMAN_NAME) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.LINKMAN_NAME,
						record.data.LINKMAN_NAME, 'LINKMAN_NAME', '', '1',
						record.data.LINKMAN_NAME, '联系人姓名');
			}
			if (record.data.IDENT_TYPE != tempData.IDENT_TYPE) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.IDENT_TYPE,
						record.data.IDENT_TYPE, 'IDENT_TYPE', '', '1',
						record.data.IDENT_TYPE_ORA, '联系人证件类型');
			}
			if (record.data.IDENT_NO != tempData.IDENT_NO) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.IDENT_NO,
						record.data.IDENT_NO, 'IDENT_NO', '', '1',
						record.data.IDENT_NO, '联系人证件号码');
			}
			if (record.data.IDENT_EXPIRED_DATE != tempData.IDENT_EXPIRED_DATE) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.IDENT_EXPIRED_DATE,
						record.data.IDENT_EXPIRED_DATE,
						'IDENT_EXPIRED_DATE', '', '2',
						record.data.IDENT_EXPIRED_DATE, '联系人证件失效日期');
			}
			if (record.data.GENDER != tempData.GENDER) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.GENDER_ORA,
						record.data.GENDER, 'GENDER', '', '1',
						record.data.GENDER_ORA, '联系人性别');
			}
			if (record.data.BIRTHDAY != tempData.BIRTHDAY) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.BIRTHDAY,
						record.data.BIRTHDAY, 'BIRTHDAY', '', '2',
						record.data.BIRTHDAY, '联系人出生日期');
			}
			if (record.data.LINKMAN_TITLE != tempData.LINKMAN_TITLE) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.LINKMAN_TITLE,
						record.data.LINKMAN_TITLE, 'LINKMAN_TITLE', '',
						'1', record.data.LINKMAN_TITLE_ORA, '联系人称谓');
			}
			if (record.data.FEX != tempData.FEX) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.FEX, record.data.FEX,
						'FEX', '', '1', record.data.FEX, '联系人传真号码');
			}
			if (record.data.HOME_TEL != tempData.HOME_TEL) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.HOME_TEL,
						record.data.HOME_TEL, 'HOME_TEL', '', '1',
						record.data.HOME_TEL, '联系人家庭电话');
			}
			if (record.data.MOBILE != tempData.MOBILE) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.MOBILE,
						record.data.MOBILE, 'MOBILE', '', '1',
						record.data.MOBILE, '联系人手机号码');
			}
			if (record.data.MOBILE2 != tempData.MOBILE2) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.MOBILE2,
						record.data.MOBILE2, 'MOBILE2', '', '1',
						record.data.MOBILE2, '联系人手机号码2');
			}
			if (record.data.OFFICE_TEL != tempData.OFFICE_TEL) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.OFFICE_TEL,
						record.data.OFFICE_TEL, 'OFFICE_TEL', '', '1',
						record.data.OFFICE_TEL, '联系人办公电话');
			}
			if (record.data.EMAIL != tempData.EMAIL) {
				isActUpdateFlag = true;
				addFieldViewFn(perModel, custId, tempData.EMAIL,
						record.data.EMAIL, 'EMAIL', '', '1',
						record.data.EMAIL, '联系人电子邮件');
			}
			addFieldFn(perModel, custId, '', record.data.PER_LAST_UPDATE_SYS,
					'PER_LAST_UPDATE_SYS', '');
			addFieldFn(perModel, custId, '', record.data.PER_LAST_UPDATE_USER,
					'PER_LAST_UPDATE_USER', '');
			addFieldFn(perModel, custId, '', record.data.PER_LAST_UPDATE_TM,
					'PER_LAST_UPDATE_TM', '', '2');
			if (isActUpdateFlag) {
				allUpdateModelArr.push({
					PER_IS_ADD_FLAG : '0',
					permodel : perModel
				});
			}
		}
	});
	return allUpdateModelArr;
};
/**
 * 返回联系信息变更历史
 * 
 * @return allUpdateModelArr
 */
window.getComModel_Contact = function(custId) {
	var allUpdateModelArr = [];
	comContactInfoStore
			.each(function(record) {
				// 1新增,0修改
				if (record.data.IS_ADD_FLAG == '1') {
					var perModel = [];
					addFieldFn(perModel, custId, '', '', 'CONTMETH_ID', '1');// 主键字段
					addFieldFn(perModel, custId, custId, custId,'CONTMETH_CUST_ID', '');
					if (record.data.CONTMETH_TYPE != '')
						addFieldViewFn(perModel, custId, '',
								record.data.CONTMETH_TYPE, 'CONTMETH_TYPE', '',
								'1', record.data.CONTMETH_TYPE_ORA, '联系方式类型');
					if (record.data.IS_PRIORI != '')
						addFieldViewFn(perModel, custId, '',
								record.data.IS_PRIORI, 'IS_PRIORI', '', '1',
								record.data.IS_PRIORI_ORA, '是否首选');
					if (record.data.STAT != '')
						addFieldViewFn(perModel, custId, '', record.data.STAT,
								'STAT', '', '1', record.data.STAT, '记录状态');
					if (record.data.CONTMETH_INFO != '')
						addFieldViewFn(perModel, custId, '',
								record.data.CONTMETH_INFO, 'CONTMETH_INFO', '',
								'1', record.data.CONTMETH_INFO, '联系方式内容');
					if (record.data.REMARK != '')
						addFieldViewFn(perModel, custId, '',
								record.data.REMARK, 'REMARK', '',
								'1', record.data.REMARK, '备注');
					addFieldFn(perModel, custId, '',
							record.data.CONTMETH_LAST_UPDATE_SYS,
							'CONTMETH_LAST_UPDATE_SYS', '');
					addFieldFn(perModel, custId, '',
							record.data.CONTMETH_LAST_UPDATE_USER,
							'CONTMETH_LAST_UPDATE_USER', '');
					addFieldFn(perModel, custId, '',
							record.data.CONTMETH_LAST_UPDATE_TM,
							'CONTMETH_LAST_UPDATE_TM', '', '2');
					allUpdateModelArr.push({
						IS_ADD_FLAG : '1',
						permodel : perModel
					});
				} else if (record.data.IS_ADD_FLAG == '0') {
					var isActUpdateFlag = false;
					var tempData = tempComContactInfoStore
							.getAt(tempComContactInfoStore.findExact(
									'CONTMETH_ID', record.data.CONTMETH_ID)).data;
					var perModel = [];
					addFieldFn(perModel, custId, tempData.CONTMETH_ID,tempData.CONTMETH_ID, 'CONTMETH_ID', '1');// 主键字段
					addFieldFn(perModel, custId, custId, custId,'CONTMETH_CUST_ID', '');
					if (record.data.CONTMETH_TYPE != tempData.CONTMETH_TYPE) {
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId,
								tempData.CONTMETH_TYPE_ORA,
								record.data.CONTMETH_TYPE, 'CONTMETH_TYPE', '',
								'1', record.data.CONTMETH_TYPE_ORA, '联系方式类型');
					}
					if (record.data.IS_PRIORI != tempData.IS_PRIORI) {
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId,
								tempData.IS_PRIORI_ORA, record.data.IS_PRIORI,
								'IS_PRIORI', '', '1',
								record.data.IS_PRIORI_ORA, '是否首选');
					}
					if (record.data.CONTMETH_INFO != tempData.CONTMETH_INFO) {
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId,
								tempData.CONTMETH_INFO,
								record.data.CONTMETH_INFO, 'CONTMETH_INFO', '',
								'1', record.data.CONTMETH_INFO, '联系方式内容');
					}
					if (record.data.REMARK != tempData.REMARK){
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId, tempData.REMARK, record.data.REMARK,
								'REMARK', '', '1', record.data.REMARK, '备注');
					}					
					addFieldViewFn(perModel, custId, '', record.data.STAT,
							'STAT', '', '1', record.data.STAT, '记录状态');
					addFieldFn(perModel, custId, tempData.LAST_UPDATE_SYS,
							record.data.CONTMETH_LAST_UPDATE_SYS,
							'CONTMETH_LAST_UPDATE_SYS', '');
					addFieldFn(perModel, custId, tempData.LAST_UPDATE_USER,
							record.data.CONTMETH_LAST_UPDATE_USER,
							'CONTMETH_LAST_UPDATE_USER', '');
					addFieldFn(perModel, custId, tempData.LAST_UPDATE_TM,
							record.data.CONTMETH_LAST_UPDATE_TM,
							'CONTMETH_LAST_UPDATE_TM', '', '2');
					if (isActUpdateFlag) {
						allUpdateModelArr.push({
							IS_ADD_FLAG : '0',
							permodel : perModel
						});
					}
				}
			});
	return allUpdateModelArr;
};
/**
 * 返回证件信息变更历史
 * 
 * @return allUpdateModelArr
 */
window.getComModel_Ident = function(custId) {
	
	var allUpdateModelArr = [];
	identInfoStore.each(function(record) {
				// 1新增,0修改
				if (record.data.IS_ADD_FLAG == '1') {
					var perModel = [];
					addFieldFn(perModel, custId, '', '', 'LIST_IDENT_ID', '1');// 主键字段
					addFieldFn(perModel, custId, custId, custId,'IDENT_CUST_ID', '');
					if (record.data.IDENT_TYPE != '')
						addFieldViewFn(perModel, custId, '',record.data.LIST_IDENT_TYPE, 'LIST_IDENT_TYPE','', '1', record.data.LIST_IDENT_TYPE_ORA,'证件类型');
					if (record.data.COUNTRY_OR_REGION != '')
						addFieldViewFn(perModel, custId, '',record.data.COUNTRY_OR_REGION,'COUNTRY_OR_REGION', '', '1',record.data.COUNTRY_OR_REGION_ORA, '发证机关所在地');
					if (record.data.IS_OPEN_ACC_IDENT != ''){
						addFieldViewFn(perModel, custId, '',record.data.IS_OPEN_ACC_IDENT,'IS_OPEN_ACC_IDENT', '', '1',record.data.IS_OPEN_ACC_IDENT_ORA, '是否开户证件');
//						if(record.data.IS_OPEN_ACC_IDENT == 'Y'){
//							addFieldFn(perModel, custId, '', '', 'CUST_ID', '1');// 主键字段
//							addFieldViewFn(perModel, custId, '',record.data.LIST_IDENT_TYPE,'IDENT_TYPE', '', '1',record.data.LIST_IDENT_TYPE_ORA, '开户证件类型');
//							addFieldViewFn(perModel, custId, '',record.data.LIST_IDENT_NO,'IDENT_NO', '', '1',record.data.LIST_IDENT_NO_ORA, '开户证件号码');
//						}
					}	
					if (record.data.LIST_IDENT_NO != '')
						addFieldViewFn(perModel, custId, '',record.data.LIST_IDENT_NO, 'LIST_IDENT_NO', '','1', record.data.LIST_IDENT_NO, '证件号码');
					addFieldViewFn(perModel, custId, '',record.data.IDENT_CUST_NAME, 'IDENT_CUST_NAME', '','1', record.data.IDENT_CUST_NAME, '证件人姓名');
					addFieldViewFn(perModel, custId, '',record.data.IDEN_REG_DATE, 'IDEN_REG_DATE', '','2', record.data.IDEN_REG_DATE, '证件颁发日期');
					addFieldViewFn(perModel, custId, '',record.data.IDENT_VALID_PERIOD,'IDENT_VALID_PERIOD', '', '1',record.data.IDENT_VALID_PERIOD, '证件有效期');
					addFieldViewFn(perModel, custId, '',record.data.LIST_EXPIRED_DATE, 'LIST_EXPIRED_DATE','', '2', record.data.LIST_EXPIRED_DATE, '证件失效日期');
					addFieldViewFn(perModel, custId, '',record.data.IDENT_CHECKING_DATE,'IDENT_CHECKING_DATE', '', '2',record.data.IDENT_CHECKING_DATE, '年检到期日');
					addFieldViewFn(perModel, custId, '', record.data.IDENT_ORG,'IDENT_ORG', '', '1', record.data.IDENT_ORG,'证件颁发机关');
					addFieldFn(perModel, custId, '',record.data.IDENT_LAST_UPDATE_SYS,'IDENT_LAST_UPDATE_SYS', '');
					addFieldFn(perModel, custId, '',record.data.IDENT_LAST_UPDATE_USER,'IDENT_LAST_UPDATE_USER', '');
					addFieldFn(perModel, custId, '',record.data.IDENT_LAST_UPDATE_TM,'IDENT_LAST_UPDATE_TM', '', '2');
					allUpdateModelArr.push({
						IS_ADD_FLAG : '1',
						permodel : perModel
					});
				} else if (record.data.IS_ADD_FLAG == '0') {
					var isActUpdateFlag = false;
					var tempData = tempComIdentStore.getAt(tempComIdentStore.findExact('LIST_IDENT_ID',
									record.data.LIST_IDENT_ID)).data;
					var perModel = [];
					addFieldFn(perModel, custId, tempData.LIST_IDENT_ID,tempData.LIST_IDENT_ID, 'LIST_IDENT_ID', '1');// 主键字段
					addFieldFn(perModel, custId, custId, custId,'IDENT_CUST_ID', '');
					if (record.data.LIST_IDENT_TYPE != tempData.LIST_IDENT_TYPE) {
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId,
								tempData.LIST_IDENT_TYPE_ORA,
								record.data.LIST_IDENT_TYPE, 'LIST_IDENT_TYPE',
								'', '1', record.data.LIST_IDENT_TYPE_ORA,
								'证件类型');
					}
					if (record.data.COUNTRY_OR_REGION != tempData.COUNTRY_OR_REGION) {
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId,
								tempData.COUNTRY_OR_REGION_ORA,
								record.data.COUNTRY_OR_REGION,
								'COUNTRY_OR_REGION', '', '1',
								record.data.COUNTRY_OR_REGION_ORA, '发证机关所在地');
					}
					if (record.data.IS_OPEN_ACC_IDENT != tempData.IS_OPEN_ACC_IDENT) {
						isActUpdateFlag = true;
						addFieldViewFn(perModel, custId,
								tempData.IS_OPEN_ACC_IDENT_ORA,
								record.data.IS_OPEN_ACC_IDENT,
								'IS_OPEN_ACC_IDENT', '', '1',
								record.data.IS_OPEN_ACC_IDENT, '是否开户证件');
						if(record.data.IS_OPEN_ACC_IDENT == 'Y'){//开户证件
							isActUpdateFlag = true;
							addFieldFn(perModel, custId, custId, custId, 'CUST_ID', '1');// 主键字段
							addFieldViewFn(perModel, custId, tempData.LIST_IDENT_TYPE,record.data.LIST_IDENT_TYPE,'CUS_IDENT_TYPE', '', '1',record.data.LIST_IDENT_TYPE_ORA, '开户证件类型');
							addFieldViewFn(perModel, custId, tempData.LIST_IDENT_NO,record.data.LIST_IDENT_NO,'CUS_IDENT_NO', '', '1',record.data.LIST_IDENT_NO_ORA, '开户证件号码');
						}					
					}
					if (record.data.LIST_IDENT_NO != tempData.LIST_IDENT_NO) {
						isActUpdateFlag = true;			
						addFieldViewFn(perModel, custId, '', record.data.LIST_IDENT_NO,
								'LIST_IDENT_NO', '', '1',
								record.data.LIST_IDENT_NO, '证件号码');
					}	
					addFieldViewFn(perModel, custId, '',
							record.data.IDENT_CUST_NAME, 'IDENT_CUST_NAME', '',
							'1', record.data.IDENT_CUST_NAME, '证件人姓名');
					if (record.data.IDEN_REG_DATE != tempData.IDEN_REG_DATE) {
						isActUpdateFlag = true;	
						addFieldViewFn(perModel, custId, '',
								record.data.IDEN_REG_DATE, 'IDEN_REG_DATE', '',
								'2', record.data.IDEN_REG_DATE, '证件颁发日期');
					}
					
					if (record.data.IDENT_VALID_PERIOD != tempData.IDENT_VALID_PERIOD) {
						isActUpdateFlag = true;	
						addFieldViewFn(perModel, custId, '',
								record.data.IDENT_VALID_PERIOD,
								'IDENT_VALID_PERIOD', '', '1',
								record.data.IDENT_VALID_PERIOD, '证件有效期');
					}
					
					if (record.data.LIST_EXPIRED_DATE != tempData.LIST_EXPIRED_DATE) {
						isActUpdateFlag = true;	
						addFieldViewFn(perModel, custId, '',
								record.data.LIST_EXPIRED_DATE, 'LIST_EXPIRED_DATE',
								'', '2', record.data.LIST_EXPIRED_DATE, '证件失效日期');
					}
					
					if (record.data.IDENT_CHECKING_DATE != tempData.IDENT_CHECKING_DATE) {
						isActUpdateFlag = true;	
						addFieldViewFn(perModel, custId, '',
								record.data.IDENT_CHECKING_DATE,
								'IDENT_CHECKING_DATE', '', '2',
								record.data.IDENT_CHECKING_DATE, '年检到期日');
					}
			
					if (record.data.IDENT_ORG != tempData.IDENT_ORG) {
						isActUpdateFlag = true;	
						addFieldViewFn(perModel, custId, '', record.data.IDENT_ORG,
								'IDENT_ORG', '', '1', record.data.IDENT_ORG,
								'证件颁发机关');
					}
					
					addFieldFn(perModel, custId, tempData.LAST_UPDATE_SYS,
							record.data.IDENT_LAST_UPDATE_SYS,
							'IDENT_LAST_UPDATE_SYS', '');
					addFieldFn(perModel, custId, tempData.LAST_UPDATE_USER,
							record.data.IDENT_LAST_UPDATE_USER,
							'IDENT_LAST_UPDATE_USER', '');
					addFieldFn(perModel, custId, tempData.LAST_UPDATE_TM,
							record.data.IDENT_LAST_UPDATE_TM,
							'IDENT_LAST_UPDATE_TM', '', '2');
					if (isActUpdateFlag) {
						allUpdateModelArr.push({
							IS_ADD_FLAG : '0',
							permodel : perModel
						});
					}
				}
			});
	return allUpdateModelArr;
};


/**
 * 此方法可以新增潜在客户，用于第一页、第二页\第三页、第四页
 * 修改潜在客户所有页面
 * 新增临时户所有页面
 */
var secondAddSave = function(custid, custName, identNo, identType, viewtitle,firstjson,secondjson) {
	Ext.Msg.wait("正在处理，请稍后","系统提示");
	var record = getAllSelects()[0]; // 获取选择记录
//	if(custid==''||custid==null){// 新增：
		//1、校验：客户识别，是否已存在该客户
        //验证是否已存在该客户:1、是否重名,2、该证件类型与号码是否已存在（必须保证一个企业只能用一个证件开户）
		Ext.Ajax.request({
			url		: basepath + '/customerManagerNew!doParameterCheck.json',
		    method	: 'GET',
			params  : {
				custName	: custName,
    			identNo		: identNo,
    			identType	: identType,
    			custid		: custid
			},
			success : function(response) {
				Ext.MessageBox.hide();
				var ret = Ext.decode(response.responseText);
				custId = ret.custId;
				var nametype = ret.type;//1.既有客户，2.已有潜在客户
				var nametype1 = ret.type1;
				var mgrId = ret.mgrId;
				var mgrName = ret.mgrName;
				var userName = ret.userName;
				identTypeVar= qzCombaseInfo.form.findField('IDENT_TYPE').lastSelectionText;
				identNoVar= qzCombaseInfo.form.findField('IDENT_NO').getValue();
				if (nametype == '1') {//既有客户
					if(nametype == '1' && nametype1 == '5') {
    					Ext.MessageBox.alert('提示', '客户【' + userName + '】已存在(既有客户)，归属于客户经理：' + mgrId + '【' + mgrName + '】，如有需要，请联系此客户经理！');
    					enableSaveBtn();
    					return false;
    				} else if(nametype == '1' && nametype1 == '6') {
    					Ext.MessageBox.alert('提示', '【证件类型：' + identTypeVar + '，证件号码：' + identNoVar + '】已被客户【' + userName + '】(既有客户)使用，归属于客户经理：' + mgrId + '【' + mgrName + '】，如有需要，请联系此客户经理！');
    					enableSaveBtn();
    					return false;
    				}
				} else if (nametype == '2' &&nametype1 == '5') {
					Ext.MessageBox.alert('提示', '客户【' + userName + '】已存在(潜在客户)，归属于客户经理：' + mgrId + '【' + mgrName + '】，如有需要，请联系此客户经理！');
    				enableSaveBtn();
    				return false;
				} else if (nametype == '2'&&nametype1 == '6' ){
					Ext.MessageBox.alert('提示', '【证件类型：' + identTypeVar + '，证件号码：' + identNoVar + '】已被客户【' + userName + '】(潜在客户)使用，归属于客户经理：' + mgrId + '【' + mgrName + '】，如有需要，请联系此客户经理！');
    				enableSaveBtn();
    				return false;
				} else if (nametype == '4'){
					Ext.Msg.wait("正在处理，请稍后","系统提示");
					//校验是否有重复中征码
					Ext.Ajax.request({
						url : basepath + '/customerManagerNew!doCodeCheck.json',
						method : 'GET',
						async : false,
						params : {
							'code' : qzCombaseInfo.form.findField('LOAN_CARD_NO').getValue(),
							'custId':qzCombaseInfo.form.findField('CUST_ID').getValue()
						},
						success : function(response) {
							Ext.MessageBox.hide();
							var ret = Ext.decode(response.responseText);
							var allow = ret.allow;//从后台获取今年的数据
							if(allow == '2') {
								codeflag = true;
    							var Industry = '';
    							var Employee = '';
    							var AnnualIncome = '';
    							var TotalAssets = '';
								// 2、判断是否有客户号：若有，即是修改，若无，则新增
								if (custid == '' || custid == null) {//无，新增
									Ext.Msg.wait("正在处理，请稍后","系统提示");
									Ext.Ajax.request({
										url : basepath + '/customerManagerNew!create1.json',
										method : 'GET',
										params : {
											custId : custId,
											custName : custName,
											identNo : identNo,
											identType : identType,
											custType : '1',
											firstjson:Ext.encode(firstjson),//第一页表单数据
											secondjson:Ext.encode(secondjson)//第二页表单数据
										},
										success : function(response) {
											Ext.MessageBox.hide();
											var ret = Ext.decode(response.responseText);
											custid = ret.custId;
											qzCombaseInfo.form.findField('CUST_ID').setValue(custid);
											custId = custid;
											if (custid == '') {
												Ext.MessageBox.alert('提示', '接口未调通，客户编号未生成！');
												enableSaveBtn();
												return false;
											} else {
												Ext.Msg.wait('正在处理，请稍后......', '系统提示');
												var comFirst = getComFirstModel(custid); //处理第一页json							
												var comSecond = getComSecondModel(custid); //处理第二页json	
												var comSecond2 =window.getComModel_Happendate(custid);//处理第三页json
												var qzThreeAddress = window.getComModel_Address(custId);//处理第三页地址信息json
												var qzThreeLinkman = window.getComModel_LINKMAN(custId); //处理第三页联系人信息json
												var qzThreeContact = window.getComModel_Contact(custId); //处理第三页联系信息json
												var qzThreeIdent = window.getComModel_Ident(custId); //处理第三页证件信息json
												var qzFourth = getComFourthModel(custId); //处理第四页信息json
												//查询企业规模
//												qzCombaseInfo.form.findField('ENT_SCALE').getValue();//企业规模（银监）
											if(!(viewtitle.indexOf('正式')>-1)){
												if(qzCombaseInfo.form.findField('IN_CLL_TYPE_ID')==null){
													Industry='';
												}else{
													Industry=qzCombaseInfo.form.findField('IN_CLL_TYPE_ID').getValue();//行业类别
												}
												Employee= qzCombaseInfo.form.findField('EMPLOYEE_SCALE').getValue();//从业人数
												AnnualIncome= qzCombaseInfo.form.findField('ANNUAL_INCOME').getValue();//预计营业收入
												TotalAssets= qzCombaseInfo.form.findField('TOTAL_ASSETS').getValue();//资产总额
											}
											//新潜在客户或者信贷临时户时，是没有核心客户号的，即不是既有客户，无需走流程
											submitFlag = false;
											// qzCombaseInfo.form.findField('CUST_ID').setValue(custId);
												/////////////////////////////////////////////////////////////////////////////3、新增时修改数据，调接口，同步给ecif
												Ext.Ajax.request({
													url : basepath + '/dealWithCom!saveData.json',
													method : 'POST',
													params : {
													    'custState' : '',
														'comFirst' : Ext.encode(comFirst),
														'comSecond' : Ext.encode(comSecond),
														'comSecond2' : Ext.encode(comSecond2),
														'comThirdAddress' : Ext.encode(qzThreeAddress),
														'comThirdLinkman' : Ext.encode(qzThreeLinkman),
														'comThreeContact' : Ext.encode(qzThreeContact),
														'comThreeIdent' : Ext.encode(qzThreeIdent),
														'comFourth' : Ext.encode(qzFourth),
														'custId' : custId,
														'custName' : custName,
														'submitFlag':submitFlag,
														'Industry':Industry,
														'Employee':Employee,
														'AnnualIncome':AnnualIncome,
														'TotalAssets':TotalAssets
													},
													success : function(response) {
														Ext.Msg.alert('提示', '保存成功!');
//													    enableSaveBtn();
//														window.loadAllData(custid);
								                    	reloaddata();
													},
													failure : function() {
														Ext.Msg.alert('提示', '保存失败!');
													    enableSaveBtn();
								                    	reloaddata();
													}
												});
											}
										}
									});
								}else{//4、  修改页面，判断是否既有客户 ，若是需要走流程进行审批；       
									//有客户号
									//第一页时已保存
									custId = custid;
//									if(qzCombaseInfo.form.findField('GROUP_NO').getValue()==''||qzCombaseInfo.form.findField('GROUP_NO').getValue()==null){
//										qzCombaseInfo.form.findField('GROUP_NO').setValue('999');
//									}
									if(!(viewtitle.indexOf('正式')>-1)){
										if(qzCombaseInfo.form.findField('IN_CLL_TYPE_ID')==null){
											Industry='';
										}else{
											Industry=qzCombaseInfo.form.findField('IN_CLL_TYPE_ID').getValue();//行业类别
										}
										Employee= qzCombaseInfo.form.findField('EMPLOYEE_SCALE').getValue();//从业人数
										AnnualIncome= qzCombaseInfo.form.findField('ANNUAL_INCOME').getValue();//预计营业收入
										TotalAssets= qzCombaseInfo.form.findField('TOTAL_ASSETS').getValue();//资产总额
									}
									/////////////////////////////////////////////////////////////////////////////4.1、从第一页取出客户号
									var comFirst = getComFirstModel(custid); //处理第一页json		
									var comSecond = getComSecondModel(custid); //处理第二页json	
									var comSecond2 =window.getComModel_Happendate(custId);//处理第三页json
									var qzThreeAddress = window.getComModel_Address(custId); //处理第三页地址信息json
									var qzThreeLinkman = window.getComModel_LINKMAN(custId); //处理第三页联系人信息json
									var qzThreeContact = window.getComModel_Contact(custId); //处理第三页联系信息json
									var qzThreeIdent = window.getComModel_Ident(custId); //处理第三页证件信息json
									var qzFourth = getComFourthModel(custId); //处理第四页信息json
									//第一和第二页有默认值
									if(comFirst.length==0 && comSecond.length==0 && comSecond2.length==0 &&
											qzThreeAddress.length==0 && qzThreeLinkman.length==0 && qzThreeContact.length==0&& qzThreeIdent.length==0&&
											qzFourth.length==0){
										Ext.Msg.alert('提示', '没有修改任何数据!');
										enableSaveBtn();
										return false;
									}
									Ext.Msg.wait('正在处理，请稍后......', '系统提示');
									//4.2、判断是否既有客户，若是需要走流程
									//若不是既有客户，修改数据，调接口，同步给ecif
									if(viewtitle.indexOf('既有客户')>-1 ||viewtitle.indexOf('准正式户转正式户')>-1){
										submitFlag=true;
									}else{
										submitFlag=false;
									}
									Ext.Ajax.request({
    									url		: basepath + '/dealWithCom!saveData.json',
    									method	: 'POST',
    									params	: {
    										'custState'         : record.get("CUST_STAT"),
    										'comFirst'			: Ext.encode(comFirst),
    										'comSecond'			: Ext.encode(comSecond),
    										'comSecond2'		: Ext.encode(comSecond2),
    										'comThirdAddress'	: Ext.encode(qzThreeAddress),
    										'comThirdLinkman'	: Ext.encode(qzThreeLinkman),
    										'comThreeContact'	: Ext.encode(qzThreeContact),
    										'comThreeIdent'		: Ext.encode(qzThreeIdent),
    										'comFourth'			: Ext.encode(qzFourth),
    										'custId'			: custId,
    										'custName'			: qzCombaseInfo.form.findField('CUST_NAME').getValue(),
    										'submitFlag'		: submitFlag,
    										'Industry'			: Industry,// 行业类别
    										'Employee'			: Employee,// 从业人数
    										'AnnualIncome'		: AnnualIncome,// 预计营业收入
    										'TotalAssets'		: TotalAssets
    										// 资产总额
    									},
    									success	: function(response) {
    										Ext.MessageBox.hide();
    										if(submitFlag) {
    											var ret = Ext.decode(response.responseText);
    											var instanceid = ret[0].instanceid;// 流程实例ID
    											var currNode = ret[0].currNode;// 当前节点
    											var nextNode = ret[0].nextNode;// 下一步节点
    											window.selectUserListNew(ret, instanceid, currNode, nextNode, function() {
    												// infoPanel.layout.setActiveItem(0);
    												// qzComInfo.setActiveTab(0);
    											});// 选择下一步办理人}
    											enableSaveBtn();
    										} else {
    											Ext.Msg.alert('提示', '保存成功!');
    											enableSaveBtn();
    											window.loadAllData(custid);
    											// reloaddata();
    										}
    									},
    									failure	: function() {
    										if(submitFlag) {
    											Ext.Msg.alert('提示', '操作失败!');
    											enableSaveBtn();
    											window.loadAllData(custid);
    											reloaddata();
    										} else {
    											Ext.Msg.alert('提示', '保存失败!');
    											enableSaveBtn();
    											window.loadAllData(custid);
    											reloaddata();
    										}
    									}
    								});		
								}
							}else{
								Ext.Msg.alert('提示','第一页中征码信息校验失败，已有客户使用了该中征码');
								enableSaveBtn();
								codeflag= false;
								return false;
							}
						}
					});
				}
			}
		});
//	}
//	else{//修改
//		
//	}
};
var reloaddata  = function(){
// 	 setTimeout(function () {      	 
 		 reloadCurrentData();
// 	 },1000); 
}
