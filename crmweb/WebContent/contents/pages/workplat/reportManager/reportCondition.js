var mycondition = {
        layout : 'column',
		border : false,
		items : [{
		    columnWidth : .25,
			defaultType : 'textfield',
			layout : 'form',
			labelWidth : 80,
			border : false,
			items : [{
				fieldLabel : '报表编码',
				name : 'REPORT_CODE',
				xtype : 'textfield',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}]
		},{
		    columnWidth : .25,
			defaultType : 'textfield',
			layout : 'form',
			labelWidth : 80,
			border : false,
			items : [{
				fieldLabel : '报表名称',
				name : 'REPORT_NAME',
				xtype : 'textfield',
				labelStyle : 'text-align:right;',
				anchor : '90%'
			}]
		}]
	};

var getQueryConditionsByJs = function (reportCode) {
	if (reportCode == '71') {//71为report_Code报表编码
		return mycondition;
	} else {
		return null;
	}
};



