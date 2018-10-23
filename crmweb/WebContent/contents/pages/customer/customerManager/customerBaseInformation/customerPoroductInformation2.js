	var sProductId='';
	var sCatlCode="";
	var cust_id ="";
	var base_id="";
	var isOmainType = "0";
	var mgrid = "";
	try{
		mgrid = viewWindow.mrgIds;
	}catch(e){}
	 
	if(oCustInfo.omain_type==true)
	{
		isOmainType = "1";
	}
	var fnJudgeEntrance= function(){
		if(!oCustInfo.cust_id&&!mgrid){
			base_id=oCustInfo.groupId;
		}
		else if(oCustInfo.cust_id!=false)
			cust_id=oCustInfo.cust_id;
	};
	fnJudgeEntrance();
	
	//币种
	var bZStore = new Ext.data.Store({
		restful : true,
		autoLoad : true,
		proxy : new Ext.data.HttpProxy({
			url : basepath + '/lookup.json?name=ACC1300012'
		}),
		reader : new Ext.data.JsonReader({
			root : 'JSON'
		}, [ 'key', 'value' ])
	});
	 //复选框
	var lsm = new Ext.grid.CheckboxSelectionModel();

	// 定义自动当前页行号
	var lrownum = new Ext.grid.RowNumberer({
				header : 'No.',
				width : 28
			});
	//保险列模型
	var  insurancecm = new Ext.grid.ColumnModel([
	   lrownum,lsm, 
       {header : '客户编号',dataIndex : 'CUST_NO',sortable : true,width : 100},
       {header : '客户名称',dataIndex : 'CUST_NAME',width : 100},
       {header : '账号',dataIndex : 'ACCOUNT',width : 100},
       {header : '账号名称',dataIndex : 'ACCOUNT_NAME'},
       {header : '保险金额',dataIndex : 'MONEY',width : 100,align:'right',renderer:money('0,000.00')}
	]);
	var insurancestore = new Ext.data.Store({
		restful:true,	
		autoLoad:false,
        proxy : new Ext.data.HttpProxy({url:basepath+'/productInsuranceQuery.json?cust_id='+cust_id+"&base_id="+base_id+"&mgrid="+mgrid
        }),
       reader: new Ext.data.JsonReader({
       totalProperty : 'json.count',
       root:'json.data'
		     },
				[{name : 'CUST_NO'},
				 {name : 'CUST_NAME'},
				 {name : 'ACCOUNT'},
				 {name : 'ACCOUNT_NAME'},
				 {name : 'MONEY'}])
	});
	insurancestore.on('beforeload', function(){
    	this.baseParams = {
        	"catlCode":sCatlCode
      };
});
	
	//代收代付列模型
	var  collectionAndPaymentcm = new Ext.grid.ColumnModel([
	   lrownum,lsm, 
       {header : '客户编号',dataIndex : 'CUST_NO',sortable : true,width : 100},
       {header : '客户名称',dataIndex : 'CUST_NAME',width : 100},
       {header : '账号',dataIndex : 'ACCOUNT',width : 100},
       {header : '账号名称',dataIndex : 'ACCOUNT_NAME'},
       {header : '是否开立',dataIndex : 'IS_OPEN',width : 100},
       {header : '代理业务名称',dataIndex : 'PAY_TYP',width : 100},
       {header : '交易金额',dataIndex : 'MONEY',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '费用金额',dataIndex : 'a8',width : 100,align:'right',renderer:money('0,000.00')}
	]);
	var collectionAndPaymentstore = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/productCollectionAndPaymentQuery.json?cust_id='+cust_id+"&base_id="+base_id+"&mgrid="+mgrid
        }),
       reader: new Ext.data.JsonReader({
       totalProperty : 'json.count',
       root:'json.data'
		     },
				[{name : 'CUST_NO'},
				 {name : 'CUST_NAME'},
				 {name : 'ACCOUNT'},
				 {name : 'ACCOUNT_NAME'},
				 {name : 'IS_OPEN'}, 
				 {name : 'PAY_TYP'},
				 {name : 'MONEY'},
				 {name : 'a8'}])
	});
	collectionAndPaymentstore.on('beforeload', function(){
    	this.baseParams = {
        	"catlCode":sCatlCode
      };
});
	
	//贵金属列模型
	var  raremetalcm = new Ext.grid.ColumnModel([
	   lrownum,lsm, 
       {header : '客户编号',dataIndex : 'CUST_NO',sortable : true,width : 100},
       {header : '客户名称',dataIndex : 'CUST_NAME',width : 100},
       {header : '账号',dataIndex : 'ACCOUNT',width : 100},
       {header : '账号名称',dataIndex : 'ACCOUNT_NAME'},
       {header : '客户购买数量(克)',dataIndex : 'CUST_BUY_COUNT',width : 100},
       {header : '购买金额(元)',dataIndex : 'MONEY',width : 100,align:'right',renderer:money('0,000.00')}
	]);
	var raremetalstore = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/productRarementalsQuery.json?cust_id='+cust_id+"&base_id="+base_id+"&mgrid="+mgrid
        }),
       reader: new Ext.data.JsonReader({
       totalProperty : 'json.count',
       root:'json.data'
		     },[{name : 'CUST_NO'},
				 {name : 'CUST_NAME'},
				 {name : 'ACCOUNT'},
				 {name : 'ACCOUNT_NAME'},
				 {name : 'CUST_BUY_COUNT'}, 
				 {name : 'MONEY'}])
	});
	raremetalstore.on('beforeload', function(){
    	this.baseParams = {
        	"catlCode":sCatlCode
      };
});
	//理财产品列模型
	var  managefinancescm = new Ext.grid.ColumnModel([
	   lrownum,lsm, 
       {header : '客户编号',dataIndex : 'CUST_NO',sortable : true,width : 100},
       {header : '客户名称',dataIndex : 'CUST_NAME',width : 100},
       {header : '产品代码',dataIndex : 'PRODUCT_ID',width : 100},
       {header : '产品名称',dataIndex : 'PROD_NAME'},
       {header : '账号',dataIndex : 'ACCOUNT',width : 100},
       {header : '币种',dataIndex : 'CNCY',width : 100},
       {header : '当前余额',dataIndex : 'CURRE_AMOUNT',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '预计收益',dataIndex : 'PRE_INCOME',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '起息日',dataIndex : 'START_DATE',width : 100,align:'right'},
       {header : '到期日',dataIndex : 'END_DATE',width : 100,align:'right'}
	]);
	var managefinancesstore = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/productManageFinancesQuery.json?cust_id='+cust_id+"&base_id="+base_id+"&mgrid="+mgrid
        }),
       reader: new Ext.data.JsonReader({
           totalProperty : 'json.count',
           root:'json.data'
       }, [
           {name : 'CUST_NO'},
           {name : 'CUST_NAME'},
           {name : 'PRODUCT_ID'},
           {name : 'PROD_NAME'},
           {name : 'ACCOUNT'}, 
           {name : 'CNCY'},
           {name : 'CURRE_AMOUNT'},
           {name : 'PRE_INCOME'},
           {name : 'START_DATE'},
           {name : 'END_DATE'}])
	});
	managefinancesstore.on('beforeload', function(){
    	this.baseParams = {
        	"catlCode":sCatlCode
      };
});

	// 国际业务产品列模型
	var internationalcm = new Ext.grid.ColumnModel([
	   lrownum,lsm, 
       {header : '客户编号',dataIndex : 'CUSTOM_ID',sortable : true,width : 100},
       {header : '业务类型',dataIndex : 'BUSI_TYPE',width : 100},
       {header : '业务名称',dataIndex : 'BUSI_NAME',width : 100},
       {header : '金额币种',dataIndex : 'MONEY_CNCY_ORA'},
       {header : '金额',dataIndex : 'MONEY',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '手续费币种',dataIndex : 'POUNDAGE_CNCY_ORA',width : 100},
       {header : '手续费',dataIndex : 'POUNDAGE_MONEY',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '交易日期',dataIndex : 'TRADE_DATE',width : 100,align:'right'},
       {header : '交易机构',dataIndex : 'TRADE_INST',width : 100},
       {header : '交易机构名称',dataIndex : 'TRADE_INST_NAME',width : 100}
	]);
	var internationalstore = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/productInternationalQuery.json?cust_id='+cust_id+"&base_id="+base_id+"&mgrid="+mgrid
        }),
       reader: new Ext.data.JsonReader({
       totalProperty : 'json.count',
       root:'json.data'
		},[{name : 'CUST_NO'},
				 {name : 'CUSTOM_ID'},
				 {name : 'BUSI_NAME'},
				 {name : 'MONEY_CNCY_ORA'},
				 {name : 'MONEY'}, 
				 {name : 'BUSI_TYPE'}, 
				 {name : 'POUNDAGE_CNCY_ORA'},
				 {name : 'POUNDAGE_MONEY'},
				 {name : 'TRADE_DATE'},
				 {name : 'TRADE_INST'},
				 {name : 'TRADE_INST_NAME'}])
	});
	internationalstore.on('beforeload', function(){
    	this.baseParams = {
        	"catlCode":sCatlCode
      };
});
	// 存款列模型
	var depositcm = new Ext.grid.ColumnModel([
	   lrownum,lsm, 
       {header : '账号',dataIndex : 'ODS_ACCT_NO',sortable : true,width : 100},
       {header : '户名',dataIndex : 'CUST_ZH_NAME',width : 100},
       {header : '账户类型',dataIndex : 'ACCONT_TYPE_ORA',width : 100},
       {header : '存款状态',dataIndex : 'ACCT_STATUS_ORA',width : 100},
       {header : '产品一级分类',dataIndex : 'PROD_FDKIND',width : 100},
       {header : '产品二级分类',dataIndex : 'PROD_SDKIND',width : 100},
       {header : '产品三级分类',dataIndex : 'PROD_TDKIND',width : 100},
       {header : '产品名称',dataIndex : 'PROD_NAME',width : 100},
       {header : '业务类别',dataIndex : 'BSKD',width : 100,hidden:true},
       {header : '币种',dataIndex : 'CYNO_ORA',width : 100},
       {header : '本币余额',dataIndex : 'MS_AC_BAL',align:'right',width : 100,renderer:money('0,000.00')},
       {header : '折人民币余额',dataIndex : 'BAL_RMB',align:'right',width : 100,renderer:money('0,000.00')},
       {header : '折美元余额',dataIndex : 'BAL_US',align:'right',width : 100,renderer:money('0,000.00')},
       {header : '年日均',dataIndex : 'YEAR_AVG',align:'right',width : 100,renderer:money('0,000.00')},
       {header : '月日均',dataIndex : 'MONTH_AVG',align:'right',width : 100,renderer:money('0,000.00')},
       {header : '季日均',dataIndex : 'QUARTER_DAILY',align:'right',width : 100,renderer:money('0,000.00')},
       {header : '期限',dataIndex : 'PERD_ORA',width : 100},
       {header : '计息期限',dataIndex : 'INTE_BEAR_TERM_ORA',width : 100},
       {header : '计息方式',dataIndex : 'INTE_BEAR_MODE_ORA',width : 100},
       {header : '结息方式',dataIndex : 'INTEREST_SETTLEMENT_ORA',width : 100},
       {header : '缴存存款准备金方式',dataIndex : 'DEPOSIT_RESE_REQ_ORA',width : 100},
       {header : '开户日期',dataIndex : 'OPEN_DT',width : 100,align:'right'},
       {header : '开户机构',dataIndex : 'ORG_NAME',width : 100},
       {header : '执行利率',dataIndex : 'INRT',width : 100,align:'right',renderer:money('0,000.000000')},
       {header : '利率方式',dataIndex : 'TD_IR_TP_ORA',width : 100},
       {header : '协议利率',dataIndex : 'AGREENMENT_RATE',width : 100,align:'right',renderer:money('0,000.000000')}
	]);
	var depositstore = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/productDepositQuery.json?cust_id='+cust_id+"&base_id="+base_id+"&omain_type="+isOmainType+"&mgrid="+mgrid
        }),
       reader: new Ext.data.JsonReader({
       totalProperty : 'json.count',
        root:'json.data'
        }, [    {name : 'ODS_ACCT_NO'},
                {name : 'CUST_ZH_NAME'  } ,
                {name : 'ACCONT_TYPE_ORA'} ,
                {name : 'ACCT_STATUS_ORA'},
                {name :'PROD_FDKIND'},
                {name : 'PROD_SDKIND'},
                {name : 'PROD_TDKIND'},
                {name : 'PROD_NAME'},
                {name : 'BSKD'},
                {name : 'CYNO_ORA'},
                {name :'MS_AC_BAL'},
                {name :'BAL_RMB'},
                {name :'BAL_US'},
                {name :'YEAR_AVG'},
                {name :'MONTH_AVG'},
                {name :'QUARTER_DAILY'},
                {name :'AGREENMENT_RATE'},
                {name : 'PERD_ORA'},
                {name : 'INTE_BEAR_TERM_ORA'},
                {name : 'INTE_BEAR_MODE_ORA'},
                {name :'INTEREST_SETTLEMENT_ORA'},
                {name :'DEPOSIT_RESE_REQ_ORA'},
                {name :'OPEN_DT'},
                {name :'ORG_NAME'},
                {name : 'TD_IR_TP_ORA'},
                {name :'INRT'}])
	});
	depositstore.on('beforeload', function(){
	    	this.baseParams = {
	        	"catlCode":sCatlCode
	      };
	});
	
	// 电子银行列模型
	var eBankcm = new Ext.grid.ColumnModel([
	   lrownum,lsm, 
       {header : '企业网银客户号',dataIndex : 'CUST_ID',sortable : true,width : 100},
       {header : '统计日期',dataIndex : 'ETL_DATE',width : 100,hidden:true,align:'right'},
       {header : '客户名称',dataIndex : 'CUST_ZH_NAME',width : 100},
       {header : '机构',dataIndex : 'UNITNAME',width : 100},
       {header : '开户网点',dataIndex : 'ACC_OPEN_OUTLETS',width : 100},
       {header : '企业网银签约日期',dataIndex : 'CONTRACT_DATE',width : 100,align:'right'},
       {header : '企业网银解约日期',dataIndex : 'TERMIN_DATE',width : 100,align:'right'},
       {header : '签约账号',dataIndex : 'CONTRACT_ACCNO'},
       {header : '网银交易额',dataIndex : 'TRANS_BAL',align:'right',width : 100,renderer:money('0,000.00')},
       {header : '网银交易笔数',dataIndex : 'TRANS_SUM',align:'right',width : 100},
       {header : '登录次数',dataIndex : 'LONIN_TIMES',align:'right',width : 100},
       {header : '开户性质',dataIndex : 'ACC_NATRUE',align:'right',width : 100},
       {header : '行业',dataIndex : 'HY_CLASS',align:'right',width : 100},
       {header : '联系地址',dataIndex : 'LINK_ADDR',align:'right',width : 100},
       {header : '联系人姓名',dataIndex : 'LINKER_NAME',width : 100},
       {header : '邮编',dataIndex : 'POST_CODING',width : 100},
       {header : 'email',dataIndex : 'EMAIL',width : 100},
       {header : '联系电话',dataIndex : 'LINK_PHONE',width : 100},
       {header : '手机',dataIndex : 'MOBIE_PHONE',width : 100},
       {header : '客户经理代码',dataIndex : 'CUSTMGR_CODE',width : 100},
       {header : '客户经理姓名',dataIndex : 'CUSTMGR_NAME',width : 100},
       {header : '存款余额',dataIndex : 'DEPOSIT_CUR_BAL',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '存款日均',dataIndex : 'DEPOSIT_AVG_BAL',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '网银交易减免收入',dataIndex : 'CON_INCONME_REDU',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '网银交易应收收入',dataIndex : 'CON_INCONME_RECI',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '网银交易实收收入',dataIndex : 'CON_INCONME_ACTU',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '客户是否解约',dataIndex : 'CUST_IS_TERMIN',width : 100},
       {header : '集团客户标注签约子公司客户数',dataIndex : 'GMC_CHICOCU_SUM',width : 100},
       {header : '签约代发工资',dataIndex : 'CONT_INS_WAGES',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '证书申请日期',dataIndex : 'CERT_APPLY_DATE',width : 100,align:'right'},
       {header : '使用日期',dataIndex : 'USED_DATE',width : 100,align:'right'},
       {header : '停用日期',dataIndex : 'DISABLE_DATE',width : 100,align:'right'},
       {header : '签约网银业务种类',dataIndex : 'CONIB_BUSI_KIND',width : 100}
	]);
	
	var eBankstore = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/productEBankQuery.json?cust_id='+cust_id+"&base_id="+base_id+"&mgrid="+mgrid
        }),
       reader: new Ext.data.JsonReader({
       totalProperty : 'json.count',
        root:'json.data'
        }, [    {name : 'CUST_ID'},
                {name : 'ETL_DATE'},
                {name : 'ACC_NATRUE'},
                {name : 'ACC_OPEN_OUTLETS' } ,
                {name : 'CERT_APPLY_DATE'},
                {name : 'CON_INCONME_ACTU' } ,
                {name : 'CON_INCONME_RECI'},
                {name : 'CON_INCONME_REDU' } ,
                {name : 'CONIB_BUSI_KIND'},
                {name : 'CONT_INS_WAGES' } ,
                {name : 'CONTRACT_ACCNO'},
                {name : 'CONTRACT_DATE' } ,
                {name : 'CUST_IS_TERMIN' } ,
                {name : 'CUST_ZH_NAME'},
                {name : 'CUSTMGR_CODE' } ,
                {name : 'CUSTMGR_NAME'},
                {name : 'DEPOSIT_AVG_BAL' } ,
                {name : 'DEPOSIT_CUR_BAL'},
                {name : 'DISABLE_DATE' } ,
                {name : 'EMAIL' } ,
                {name : 'GMC_CHICOCU_SUM'},
                {name : 'HY_CLASS' } ,
                {name : 'LINK_ADDR'},
                {name : 'LINK_PHONE' } ,
                {name : 'LINKER_NAME'},
                {name : 'LONIN_TIMES' } ,
                {name : 'MOBIE_PHONE' } ,
                {name : 'UNITNAME'},
                {name : 'POST_CODING' } ,
                {name : 'TERMIN_DATE'},
                {name : 'TRANS_BAL' } ,
                {name : 'TRANS_SUM'},
                {name : 'USED_DATE' }
                ])
	});
	eBankstore.on('beforeload', function(){
	    	this.baseParams = {
	        	"catlCode":sCatlCode
	      };
	});
	
	// 中间业务列模型
	var midBusicm = new Ext.grid.ColumnModel([
	   lrownum,lsm, 
       {header : '科目号',dataIndex : 'SUBJECT_NO',sortable : true,width : 100},
       {header : '科目名称',dataIndex : 'SUBJECT_NAME',width : 100},
       {header : '本月累计收入',dataIndex : 'CURMON_INCOME',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '本季累计收入',dataIndex : 'CURQUA_INCOME',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '本年累计收入',dataIndex : 'CURYEAR_INCOME',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '统计日期',dataIndex : 'ETL_DATE',width : 100,hidden:true,align:'right'}
	]);
	var midBusistore = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/productMidBusiQuery.json?cust_id='+cust_id+"&base_id="+base_id+"&mgrid="+mgrid
        }),
       reader: new Ext.data.JsonReader({
       totalProperty : 'json.count',
        root:'json.data'
        }, [    {name : 'SUBJECT_NO'},
                {name : 'SUBJECT_NAME'  } ,
                {name : 'CURMON_INCOME'} ,
                {name : 'CURQUA_INCOME'},
                {name :'CURYEAR_INCOME'},
                {name : 'ETL_DATE'}])
	});
	midBusistore.on('beforeload', function(){
	    	this.baseParams = {
	        	"catlCode":sCatlCode
	      };
	});
	
	// 贷款列模型
	var loancm = new Ext.grid.ColumnModel([
	   lrownum,lsm, 
       {header : '贷款形式',dataIndex : 'LOAN_TYP_ORA',width : 100},
       {header : '贷款性质',dataIndex : 'LOAN_QUAL_ORA',width : 100},
       {header : '委托贷款种类',dataIndex : 'ENT_LOAN_TYP_ORA',width : 100},
       {header : '贷款政策性质分类',dataIndex : 'LOAN_POL_PRO_CLASS_ORA',width : 100},
       {header : '特殊贷款类型',dataIndex : 'SPEC_LOAN_TYE_ORA',width : 100},
       {header : '担保方式',dataIndex : 'GRNT_TYP_ORA',width : 100},
       {header : '贷款投向',dataIndex : 'LOAN_INVEST_ORA',width : 100},
       {header : '币种',dataIndex : 'CURR_ORA',width : 100},
       {header : '汇率',dataIndex : 'FOREXCH_RATE',width : 100,align:'right',renderer:money('0,000.000000')},
       {header : '合同金额(元)',dataIndex : 'CONT_AMT',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '发放金额',dataIndex : 'GIVE_OUT_AMT',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '回收金额',dataIndex : 'RECOVER_AMT',width : 100,align:'right',renderer:money('0,000.00')},
       {header : '起贷日期',dataIndex : 'BEGIN_DATE',width : 100,align:'right'},
       {header : '止贷日期',dataIndex : 'END_DATE',width : 100,align:'right'},
       {header : '基准月利率(‰)',dataIndex : 'BM_MOT',width : 100,align:'right',renderer:money('0,000.000000')},
       {header : '浮动比',dataIndex : 'FLOAT_THAN',width : 100,align:'right',renderer:money('0,000.000000')},
       {header : '月利率',dataIndex : 'MON_INTE_RATE',width : 100,align:'right',renderer:money('0,000.000000')},
       {header : '逾期月利率',dataIndex : 'LATE_MOT',width : 100,align:'right',renderer:money('0,000.000000')},
       {header : '违约月利率',dataIndex : 'DEFUT_MOT',width : 100,align:'right',renderer:money('0,000.000000')},
       {header : '还款方式',dataIndex : 'REPAY_MODE_ORA',width : 100},
       {header : '还款周期',dataIndex : 'REPAY_CYCL_ORA',width : 100},
       {header : '计息周期',dataIndex : 'INTC_CYCL_ORA',width : 100},
       {header : '利率调整方式',dataIndex : 'INTR_CORR_MODE_ORA',width : 100},
       {header : '是否贴息',dataIndex : 'YN_DISC_ORA',width : 100},
       {header : '借款用途',dataIndex : 'LOAN_USE',width : 100},
       {header : '还款来源',dataIndex : 'REPAY_ORG',width : 100}
	]);
	/**
	 * 数据存储
	 */
	var loanstore = new Ext.data.Store({
		restful:true,	
        proxy : new Ext.data.HttpProxy({url:basepath+'/productLoanQuery.json?cust_id='+cust_id+"&base_id="+base_id+"&mgrid="+mgrid
        	/*,
        	success : function(response) {
				Ext.Msg.alert('提示', response.responseText);
			}*/
        }),
       reader: new Ext.data.JsonReader({
       totalProperty : 'json.count',
        root:'json.data'
        }, [     {name : 'CURR_ORA'},
                 {name : 'LOAN_TYP_ORA'},
                 {name : 'LOAN_QUAL_ORA'},
                 {name : 'ENT_LOAN_TYP_ORA'},
                 {name : 'LOAN_POL_PRO_CLASS_ORA'},
                 {name : 'SPEC_LOAN_TYE_ORA'},
                 {name : 'GRNT_TYP_ORA'},
                 {name : 'LOAN_INVEST_ORA'},
                 {name : 'FOREXCH_RATE'},
                 {name : 'CONT_AMT'},
                 {name : 'GIVE_OUT_AMT'},
                 {name : 'RECOVER_AMT'},
                 {name : 'BEGIN_DATE'},
                 {name : 'END_DATE'},
                 {name : 'BM_MOT'},
                 {name : 'FLOAT_THAN'},
                 {name : 'MON_INTE_RATE'},
                 {name :'LATE_MOT'},
                 {name :'DEFUT_MOT'},
                 {name :'REPAY_MODE_ORA'},
                 {name :'REPAY_CYCL_ORA'},
                 {name :'INTC_CYCL_ORA'},
                 {name :'INTR_CORR_MODE_ORA'},
                 {name :'YN_DISC_ORA'},
                 {name :'LOAN_USE'},
                 {name :'REPAY_ORG'}])
	});
	loanstore.on('beforeload', function(){
    	this.baseParams = {
        	"catlCode":sCatlCode
      };
});
	

	// 表格实例
	var oPoroductGrid = new Ext.grid.GridPanel({
		        title : '<span style="font-weight:normal">贷款产品</span>',
				height:document.body.scrollHeight-59,
				frame : true,
				//region : 'center', // 返回给页面的div
				store : loanstore, // 数据存储
				stripeRows : true, // 斑马线
				cm : loancm, // 列模型
				sm : lsm, // 复选框
				//tbar : tbar, // 表格工具栏
				//bbar : bbar,// 分页工具栏
				viewConfig : {
				},
				loadMask : {
					msg : '正在加载表格数据,请稍等...'
				}
				
			});
	var loader = new Com.yucheng.bcrm.ArrayTreeLoader({
//		/**节点数组，可以改为从后台读取*/
//		nodeArray :nodeArra,
		/**指向父节点的属性列*/
		parentAttr : 'CATL_PARENT',
		/**节点定位属性列，也是父属性所指向的列*/
		locateAttr : 'CATL_CODE',
		/**虚拟根节点id*/
		rootValue : '0',
		/**用于展示节点名称的属性列*/
		textField : 'CATL_NAME',
		/**指定节点ID的属性列*/
		idProperties : 'CATL_CODE',
		/**节点点击事件句柄*/
		clickFn:function(node){
			//Ext.getCmp('parentSection').setValue(node.id);
		}
	});
	Ext.Ajax.request({
		url : basepath + '/customerProductTree.json?cust_id='+cust_id+"&base_id="+base_id+"&mgrid="+mgrid,
		method:'GET',
		success:function(response){
			var nodeArra = Ext.util.JSON.decode(response.responseText);
			loader.nodeArray = nodeArra.JSON.data;
			var children = loader.loadAll();
			treeOfPoroduct.appendChild(children);
			treeOfPoroduct.expandAll();
			if(nodeArra.JSON.data.length != 0){
			fnRecursionFristTreeNode(loader.nodeArray[0]);
			fnToDecideType(oNodeLeaf);}
		}
	});
	
	var treeOfPoroduct = new Com.yucheng.bcrm.TreePanel({
				title:'产品目录',
				width:200,
				autoScroll:true,
				 //rootVisible:false,
				//tbar:tbar,
				/**虚拟树形根节点*/
				root: new Ext.tree.AsyncTreeNode({
					id:'root',
					expanded:true,
					text:'银行产品',
					autoScroll:true,
					children:[]
				}),
				resloader:loader,
				clickFn:function(node){
		        var oClickNode=node.attributes;
		    	if (oClickNode.leaf){
		        fnToDecideType(oClickNode);}
		
	},
				split:true
			});

	 var treeContainer = new Ext.Panel({
			frame:true,
			height:document.body.scrollHeight-59,
			layout:'fit',
			autoScroll:true,
				items: [treeOfPoroduct] });

	// 布局模型
	var tree_panel = new Ext.Panel({
		renderTo : oCustInfo.view_source,
		title : '<span style="font-weight:normal">产品信息</span>',
		layout : 'column',
		border : false,
		width : document.body.clientWidth-180,
		items : [{	
			columnWidth : .25,
			border : false,
			items : [treeContainer]
		},{	
			columnWidth : .75,
			border : false,
			layout : 'fit',
			items : [oPoroductGrid]
		}]
			});
	var oNodeLeaf=false;
	//取第一个叶子节点
	var fnRecursionFristTreeNode =function(aNodeArray){
		//判断节点的是否叶子节点
		if (!aNodeArray.leaf){
		//递归
			fnRecursionFristTreeNode(aNodeArray.children[0]);
		}
		else{
		//取出第一个叶子节点
			oNodeLeaf=aNodeArray;
		}
	};
	var fnToDecideType=function(oNode){
		//产品id
		
		sCatlCode=oNode.CATL_CODE;
		debugger;
		switch(oNode.VIEW_DETAIL){
		//贷款
		case 'B':{
			oPoroductGrid.setTitle('<span style="font-weight:normal">'+oNode.text+'</span>');
			oPoroductGrid.reconfigure( loanstore, loancm );
			loanstore.load();
			}
			break;
		//存款
		case 'A':{
			oPoroductGrid.setTitle('<span style="font-weight:normal">'+oNode.text+'</span>');
			oPoroductGrid.reconfigure( depositstore, depositcm );
			depositstore.load();
			}
			break;
		//电子银行业务
		case 'H':{
			oPoroductGrid.setTitle('<span style="font-weight:normal">'+oNode.text+'</span>');
			oPoroductGrid.reconfigure( eBankstore, eBankcm );
			eBankstore.load();
			}
			break;
			//电子银行业务
		case 'I':{
			oPoroductGrid.setTitle('<span style="font-weight:normal">'+oNode.text+'</span>');
			oPoroductGrid.reconfigure( midBusistore, midBusicm );
			midBusistore.load();
			}
			break;
		//国际业务产品
		case 'C':{
			oPoroductGrid.setTitle('<span style="font-weight:normal">'+oNode.text+'</span>');
			oPoroductGrid.reconfigure( internationalstore, internationalcm );
			internationalstore.load();
			}
		break;
		//理财产品
		case 'E':{
			oPoroductGrid.setTitle('<span style="font-weight:normal">'+oNode.text+'</span>');
			oPoroductGrid.reconfigure( managefinancesstore, managefinancescm );
			managefinancesstore.load();
			}
		break;
		//贵金属
		case 'D':{
			oPoroductGrid.setTitle('<span style="font-weight:normal">'+oNode.text+'</span>');
			oPoroductGrid.reconfigure( raremetalstore, raremetalcm );
			raremetalstore.load();
			}
		break;
		//代收代付
		case 'G':{
			oPoroductGrid.setTitle('<span style="font-weight:normal">'+oNode.text+'</span>');
			oPoroductGrid.reconfigure( collectionAndPaymentstore, collectionAndPaymentcm );
			collectionAndPaymentstore.load();	
		}
		break;
	/*	//代理收费
		case 'G':{
			oPoroductGrid.setTitle('<span style="font-weight:normal">'+oNode.text+'</span>');
			oPoroductGrid.reconfigure( collectionstore, collectioncm );
			}
		break;*/
		//保险
		case 'F':{
			oPoroductGrid.setTitle('<span style="font-weight:normal">'+oNode.text+'</span>');
			oPoroductGrid.reconfigure( insurancestore, insurancecm );
			insurancestore.load();
			}
		break;
		default:{Ext.Msg.alert('提示', '没有找到这个产品的类型！');}
		break;
		}
	};
      
