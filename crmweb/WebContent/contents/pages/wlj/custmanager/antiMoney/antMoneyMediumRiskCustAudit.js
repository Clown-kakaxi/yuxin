/**
 * 
 * @Description: 中风险反洗钱客户审核
 * @author wangmk 
 * @date 2015-12-14 
 *
 */
imports([
 '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
 '/contents/pages/common/Com.yucheng.bcrm.common.OrgField.js', // 机构放大镜
 '/contents/pages/common/Com.yucheng.crm.common.OrgUserManage.js',	//用户放大镜
 '/contents/pages/wlj/customerManager/queryAllCustomer/allLookup.js', //所有数据字典定义
 '/contents/pages/common/Com.yucheng.bcrm.common.CustGroup.js'
]);

var url=basepath+"/customerAntMoneyAuditQuery.json?riskLevel=M";

var _custId;
var comitUrl=false;
var createView = false;
var editView = false;
var detailView = false;
var _sysCurrDate = new Date().format('Y-m-d');

/**
 * 数据字典初始化
 */
/*var lookupTypes = ['IF_FLAG','XD000286','CUST_CLASS','CALL_RESULT','CHECK_STAT',
					'CUST_SOURCE','TEL_CONTACTER','CUST_REVENUE','CALL_PURPOSE',
					'CUST_BUSI_CONDITION','COLLATERAL_CONDITION','CUS_OWNBUSI'];*/
var lookupTypes=[
     'IF_FLAG',//是否选项
     'XD000080',//客户类别
     'XD000040', //证件类别
     'FXQ_RISK_LEVEL',//反洗钱风险等级
     'XD000081',//客户状态
     'IF_FLAG'
 ];

var localLookup = {
	'FXQ_CHECK_STATUS' : [{
		key : '0',
		value : '未审核'
	}, {
		key : '1',
		value : '审核中'
	}, {
		key : '2',
		value : '已审核'
	}]
};


//反洗钱风险等级--数据字典
var riskgradeStoreH =  new Ext.data.SimpleStore( {
    fields : [ 'value', 'key' ],
    data : [[ '高风险', 'H' ],[ '中风险', 'M' ],[ '低风险', 'L' ]]
});

//FXQ_check_status
//联系人的身份-对公 --数据字典
var zlFXQ_DQSH028Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FXQ_DQSH028'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
//客户办理业务 对私  --数据字典
var zlFXQ007Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FXQ007'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});
//客户办理业务 对公 --数据字典
var zlFXQ025Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FXQ025'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

var zlFXQ021Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FXQ021'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

//客户的股权或控制权结构 数据字典
var zlFXQ023Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FXQ023'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

var zlFXQ_DQSH004Store =  new Ext.data.Store({
	restful : true,
	autoLoad : true,
	sortInfo : {
            field:'key',
            direction:'ASC' //加载数据后 根据key升序排列
        },
	proxy : new Ext.data.HttpProxy({
		url : basepath + '/lookup.json?name=FXQ_DQSH004'
	}),
	reader : new Ext.data.JsonReader( {
		root : 'JSON'
	},['key','value'])
});

var fields=[
    {name:'CUST_TYPE',text:'客户类型',resutlWidth:80,translateType:'XD000080',searchField:true},
    {name:'CUST_ID',text:'客户编号',resutlWidth:120,searchField:true,dataType:'string'},
    {name:'CORE_NO',text:'核心客户号',resutlWidth:120,searchField:true,dataType:'string'},
    {name:'CUST_NAME',text:'客户名称',resutlWidth:150,searchField:true,dataType:'string'},
    {name:'CUST_GRADE',text:'当前洗钱风险等级',resutlWidth:120,translateType:'FXQ_RISK_LEVEL',gridField:true},
    {name:'CHECK_STATUS',text:'审核状态',resutlWidth:120,translateType:'FXQ_CHECK_STATUS',gridField:true},          
    {name:'AUDIT_END_DATE',text:'审核截至日期',resultWidth:120,dataType:'date'}
];


/**
 * 查询条件域对象初始化之后触发，此时对象尚未渲染；
 * params ：con：查询条件面板对象；
 * 			app：当前APP对象；
 */
var beforeconditioninit = function(panel, app){
	app.pageSize = 100;//设置默认每页显示条数

	var myDate = new Date();
    var month = myDate.getMonth()+1;//myDate.getMonth()获取到的是数组形式下标从0开始
    //每年只开放9月份
    if(month != 9){
    	app.autoLoadGrid = false;
        app.url = app.url + "&permission=false"
        Ext.Msg.alert('提示', "此功能权限暂未开放");
    }
	//updated by liuyx 20171116 //根据权限来决定是否开启
    /*if(JsContext.checkGrant("_fxqdqsh_m")){
       app.autoLoadGrid = false;
       app.url = app.url + "&permission=false"
       Ext.Msg.alert('提示', "此功能权限暂未开放");
    }*/
};


//个人（审核根据客户状态）
/*重要性
A:"是"-高风险；
B:"是"-中风险；
C:"是">2-高风险；*/
function perFXQLevel(n){
	
	var a=0;
	var b=0;
	var c=0;
	
				//清空审核结果
				fxqLevelAuditPanel.form.findField("CUST_GRADE_CHECK").setValue("");
					//a
					var dqsh024= fxqLevelAuditPanel.form.findField("DQSH024").getValue();
					var fxq009= fxqLevelAuditPanel.form.findField("FXQ009").getValue(); //	客户或其亲属、关系密切人是否属于外国政要       A            FXQ009
					var dqsh007= fxqLevelAuditPanel.form.findField("DQSH007").getValue();//	客户是否无正当理由拒绝更新证件                          B            DQSH007
					if(dqsh024=="1"||dqsh007=="1"||fxq009=="1"){
						riskgradeStoreH.removeAll();
						 var da = [ [ '高风险', 'H' ]];
						 riskgradeStoreH.loadData(da);
						return false;
				    	}
					//c
					var dqsh001= fxqLevelAuditPanel.form.findField("DQSH001").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH001").getValue()*1;//C    证件是否过期            DQSH001
					var dqsh002= fxqLevelAuditPanel.form.findField("DQSH002").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH002").getValue()*1;//C    客户是否无法取得联系    DQSH002
					var dqsh009= fxqLevelAuditPanel.form.findField("DQSH009").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH009").getValue()*1;//C    账户是否频繁发生大额现金交易    DQSH009
					var dqsh010= fxqLevelAuditPanel.form.findField("DQSH010").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH010").getValue()*1;//C    账户是否频繁发生外币现钞存取业务 DQSH010
					var dqsh011= fxqLevelAuditPanel.form.findField("DQSH011").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH011").getValue()*1;//C    账户现金交易是否与客户职业特性不符  DQSH011
					var dqsh012= fxqLevelAuditPanel.form.findField("DQSH012").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH012").getValue()*1;//C    账户是否频繁发生大额的网上银行交易   DQSH012
					var dqsh013= fxqLevelAuditPanel.form.findField("DQSH013").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH013").getValue()*1;//C    账户是否与公司账户之间发生频繁或大额的交易    DQSH013
					var dqsh014= fxqLevelAuditPanel.form.findField("DQSH014").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH014").getValue()*1;//C    账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符    DQSH014
					var dqsh015= fxqLevelAuditPanel.form.findField("DQSH015").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH015").getValue()*1;//C    账户资金是否快进快出，不留余额或少留余额    DQSH015
					var dqsh016= fxqLevelAuditPanel.form.findField("DQSH016").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH016").getValue()*1;//C    账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准    DQSH016
					var dqsh017= fxqLevelAuditPanel.form.findField("DQSH017").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH017").getValue()*1;//C    账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付    DQSH017
					var dqsh018= fxqLevelAuditPanel.form.findField("DQSH018").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH018").getValue()*1;//C    账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付    DQSH018
					var dqsh020= fxqLevelAuditPanel.form.findField("DQSH020").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH020").getValue()*1;//C    账户是否频繁发生跨境交易，且金额大于1万美元    DQSH020
					var dqsh021= fxqLevelAuditPanel.form.findField("DQSH021").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH021").getValue()*1;//C    账户是否经常由他人代为办理业务    DQSH021
					var dqsh022= fxqLevelAuditPanel.form.findField("DQSH022").getValue()*1==""?0:fxqLevelAuditPanel.form.findField("DQSH022").getValue()*1;//C    客户是否提前偿还贷款，且与其财务状况明显不符    DQSH022
					var c=dqsh001+dqsh002+dqsh009+dqsh010+dqsh011+dqsh012+dqsh013+dqsh014+dqsh015+dqsh016+dqsh017+dqsh018+dqsh020+dqsh021+dqsh022;
					if(c>10){
						riskgradeStoreH.removeAll();
						 var da = [ [ '高风险', 'H' ]];
						 riskgradeStoreH.loadData(da);
						return false;
					}else if(c>5){
						riskgradeStoreH.removeAll();
						 var da = [ [ '高风险', 'H' ],[ '中风险', 'M' ]];
						 riskgradeStoreH.loadData(da);
						return false;
					}
					//b
					var fxq008= fxqLevelAuditPanel.form.findField("FXQ008").getValue();//	客户是否涉及风险提示信息或权威媒体报道信息    B            FXQ008
					//var fxq009= fxqLevelAuditPanel.form.findField("FXQ009").getValue();//	客户或其亲属、关系密切人是否属于外国政要        B            FXQ009
					
					var dqsh008= fxqLevelAuditPanel.form.findField("DQSH008").getValue();//	客户留存的证件及信息是否存在疑点或矛盾            B            DQSH008
					var dqsh019= fxqLevelAuditPanel.form.findField("DQSH019").getValue();//	客户留存的证件及信息是否存在疑点或矛盾            B            DQSH008
					if(fxq008=="1"||dqsh008=="1"||dqsh019=="1"){
						riskgradeStoreH.removeAll();
						 var da = [ [ '高风险', 'H' ],[ '中风险', 'M' ]];
						 riskgradeStoreH.loadData(da);
						return false;
					}
	
	riskgradeStoreH.removeAll();
	 var da = [ [ '高风险', 'H' ],[ '中风险', 'M' ],[ '低风险', 'L' ]];
	 riskgradeStoreH.loadData(da);
	return false; 
}



//企业（审核根据客户状态）
/*重要性
A:"是"-高风险；
B:"是"-中风险；
C:"是">10-高风险；
C:"是">5-中风险；*/
function orgFXQLevel(n){
	
	var a=0;
	var b=0;
	var c=0;
	
	//清空审核结果
	fxqORGLevelAuditPanel.form.findField("CUST_GRADE_CHECK").setValue("");
					//a
					var dqsh024= fxqORGLevelAuditPanel.form.findField("DQSH024").getValue();
					var fxq009= fxqORGLevelAuditPanel.form.findField("FXQ009").getValue(); //	客户实际受益人、实际控制人或者其亲属、关系密切人等是否属于外国政要      A    FXQ009
					var dqsh007= fxqORGLevelAuditPanel.form.findField("DQSH007").getValue();//	客户是否无正当理由拒绝更新证件                          B            DQSH007
					if(dqsh024=="1"||dqsh007=="1"||fxq009=="1"){
						riskgradeStoreH.removeAll();
						 var da = [ [ '高风险', 'H' ]];
						 riskgradeStoreH.loadData(da);
						return false;
				    	}
					//c
					var dqsh025= fxqORGLevelAuditPanel.form.findField("DQSH025").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH025").getValue()*1;//					C    企业证件是否过期      DQSH025
					var dqsh026= fxqORGLevelAuditPanel.form.findField("DQSH026").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH026").getValue()*1;//					C    法定代表人证件是否过期  DQSH026
					var dqsh027= fxqORGLevelAuditPanel.form.findField("DQSH027").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH027").getValue()*1;//					C    联系人证件是否过期   DQSH027
					var dqsh002= fxqORGLevelAuditPanel.form.findField("DQSH002").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH002").getValue()*1;//					C    客户是否无法取得联系   DQSH002
					var dqsh009= fxqORGLevelAuditPanel.form.findField("DQSH009").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH009").getValue()*1;//					C    账户是否频繁发生大额现金交易    DQSH009
					var dqsh029= fxqORGLevelAuditPanel.form.findField("DQSH029").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH029").getValue()*1;//					C    账户是否与自然人账户之间发生频繁或大额的交易 DQSH029
					var dqsh030= fxqORGLevelAuditPanel.form.findField("DQSH030").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH030").getValue()*1;//					C    账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符    DQSH030
					var dqsh015= fxqORGLevelAuditPanel.form.findField("DQSH015").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH015").getValue()*1;//					C    账户资金是否快进快出，不留余额或少留余额    DQSH015
					var dqsh016= fxqORGLevelAuditPanel.form.findField("DQSH016").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH016").getValue()*1;//					C   账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准    DQSH016
					var dqsh031= fxqORGLevelAuditPanel.form.findField("DQSH031").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH031").getValue()*1;//					C   账户是否频繁收取与其经营业务明显无关的汇款    DQSH031
					var dqsh032= fxqORGLevelAuditPanel.form.findField("DQSH032").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH032").getValue()*1;//					C    账户资金交易频度、金额是否与其经营背景不符    DQSH032、
					var dqsh033= fxqORGLevelAuditPanel.form.findField("DQSH033").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH033").getValue()*1;//					C    账户交易对手及资金用途是否与其经营背景不符    DQSH033
					var dqsh017= fxqORGLevelAuditPanel.form.findField("DQSH017").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH017").getValue()*1;//					C    账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付    DQSH017
					var dqsh018= fxqORGLevelAuditPanel.form.findField("DQSH018").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH018").getValue()*1;//					C    账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付 DQSH018
					var dqsh034= fxqORGLevelAuditPanel.form.findField("DQSH034").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH034").getValue()*1;//					C    账户是否与关联企业之间频繁发生大额交易    DQSH034
					var dqsh022= fxqORGLevelAuditPanel.form.findField("DQSH022").getValue()==""?0:fxqORGLevelAuditPanel.form.findField("DQSH022").getValue()*1;//					C      客户是否提前偿还贷款，且与其财务状况明显不符 DQSH022
				
					var c=dqsh025+dqsh026+dqsh027+dqsh002+dqsh009+dqsh029+dqsh030+dqsh015+dqsh016+dqsh031+dqsh032+dqsh033+dqsh017+dqsh018+dqsh034+dqsh022;
					if(c>10){
						riskgradeStoreH.removeAll();
						 var da = [ [ '高风险', 'H' ]];
						 riskgradeStoreH.loadData(da);
						return false;
					}else if(c>5){
						riskgradeStoreH.removeAll();
						 var da = [ [ '高风险', 'H' ],[ '中风险', 'M' ]];
						 riskgradeStoreH.loadData(da);
						return false;
					}
					//b
					var fxq008= fxqORGLevelAuditPanel.form.findField("FXQ008").getValue();//	客户是否涉及风险提示信息或权威媒体报道信息    B            FXQ008
					var dqsh008= fxqORGLevelAuditPanel.form.findField("DQSH008").getValue();//	客户留存的证件及信息是否存在疑点或矛盾            B            DQSH008
					var dqsh019= fxqORGLevelAuditPanel.form.findField("DQSH019").getValue();//	客户留存的证件及信息是否存在疑点或矛盾            B            DQSH008
					if(fxq008=="1"||dqsh019=="1"||dqsh008=="1"){
						riskgradeStoreH.removeAll();
						 var da = [ [ '高风险', 'H' ],[ '中风险', 'M' ]];
						 riskgradeStoreH.loadData(da);
						return false;
					}
	
	riskgradeStoreH.removeAll();
	 var da = [ [ '高风险', 'H' ],[ '中风险', 'M' ],[ '低风险', 'L' ]];
	 riskgradeStoreH.loadData(da);
	return false; 
}



//月份在六个月以内为真，在六个月以后为假  参数1为当前时间，参数2为预计更新证件时间
function getMonthNumberBoolean(date1){
	//默认格式为"20030303",根据自己需要改格式和方法// 如果像 “201411”这样的日期，在后面自己补上“01” 
	var myDate = new Date();//当前时间
	var year1 =  date1.substr(0,4);  
	var year2 =  myDate.getFullYear();//当前年份   
	var month1 = date1.substr(5,2);  
	var month2 = myDate.getMonth() + 1;//当前月份  
	var day1 = date1.substr(8,2);
	var day2 = myDate.getDate();//当前日
	
	var len=(year1-year2)*12+(month1-month2);
	if(day1>day2){
		len+=1;
	}
	return len>6?true:false; 
}


var tbar =[{
    text:'反洗钱风险等级审核',
    hidden:JsContext.checkGrant('_fxqdqsh_m'),
    handler:function(){
    	/**
    	 * 定期审核限制
    	 */
    /*	var myDate = new Date();
    	var month = myDate.getMonth()+1;//myDate.getMonth()获取到的是数组形式下标从0开始
    	if(month != 3 && month != 9){
    		Ext.Msg.alert('提示', "此月份不开通此功能");	
    		return;
    	}*/
    	//判断是否选中行
		if(!getSelectedData()){
		Ext.Msg.alert('提示','请选择一条数据');
		return false;
		}
		
		if('2'==getSelectedData().data.CHECK_STATUS){
			Ext.Msg.alert('提示','此客户已审核');
			return false;
		}
		
    	//预定义加载数据方法
    	var fsqStore = new Ext.data.Store({
    		restful:true,
    		proxy : new Ext.data.HttpProxy({
    			url:basepath + '/customerAntMoneyAuditQuery!getFXQCustInfo.json',
    			method:'get'
    		}),
    		reader: new Ext.data.JsonReader({
    			totalProperty : 'json.count',
    			root:'json.data'
    		}, [
    			'CUST_ID'		//客户号
    			,'CORE_NO'		//核心客户号
    			,'CUST_NAME'	//客户名称
    			,'CUST_TYPE'	//客户类型	
    			,'IDENT_TYPE1'	//证证件类型1
    			,'INDENT_NO1'	//证件号1
    			,'IDENT_EXPIRED_DATE1'//证件1到期日
    			,'IDENT_TYPE2'	//证件类型2
    			,'INDENT_NO2'	//证件号2
    			,'IDENT_EXPIRED_DATE2'		//证件2到期日
    			,'CREATE_DATE'		//客户创建日期
    			,'CUST_STAT'		//客户状态
    			,'ORG_NAME'		//所属机构名称
    			,'MGR_NAME'		//归属客户经理名称
    			,'BELONG_TEAM_HEAD_NAME'	//归属team head名称（法金）
    			,'CITIZENSHIP'	//国籍
    			,'CAREER_TYPE'	//职业
    			,'BIRTHDAY'	//出生日期
    			,'if_org_sub_type_per'	//是否自贸区(对私)
    			
    			,'BUILD_DATE'	//成立日期
    			,'NATION_CODE'	//国家或地区代码
    			,'if_org_sub_type_ORG'	//是否自贸区(对私)
    			,'In_Cll_Type'	//行业分类
    			,'ENT_SCALE_CK'	//企业规模
    			,'AGENT_NAME'	//代理人姓名
    			,'AGENT_NATION_CODE'	//代理人国家代码
    			,'AGE_IDENT_TYPE'	//代理人证件类型
    			,'AGE_IDENT_NO'	//代理人证件号码
    			,'TEL'	//代理人联系电话
    			,'FXQ007'	//客户办理的业务(对私)
    			,'FXQ008'	//是否涉及风险提示信息或权威媒体报道信息
    			,'FXQ009'	//客户或其亲属、关系密切人等是否属于外国政要
    			
    			// FLAG_AGENT-FXQ016  于保存隐藏数据
    			,'FLAG_AGENT'	//客户或其亲属、关系密切人等是否属于外国政要
    			,'FXQ010'	//客户或其亲属、关系密切人等是否属于外国政要
    			,'FXQ011'	//客户或其亲属、关系密切人等是否属于外国政要
    			,'FXQ012'	//客户或其亲属、关系密切人等是否属于外国政要
    			,'FXQ013'	//客户或其亲属、关系密切人等是否属于外国政要
    			,'FXQ014'	//客户或其亲属、关系密切人等是否属于外国政要
    			,'FXQ015'	//客户或其亲属、关系密切人等是否属于外国政要
    			,'FXQ016'	//客户或其亲属、关系密切人等是否属于外国政要
    			,'FXQCODE'
    			
    			
    			,'FXQ021'	//与客户建立业务关系的渠道
    			,'FXQ022'	//是否在规范证券市场上市
    			,'FXQ023'	//客户的股权或控制权结构
    			,'FXQ024'	//客户是否存在隐名股东或匿名股东
    			,'FXQ025'	//客户办理的业务(对公) 
    			,'DQSH001'	//证件是否过期
    			,'DQSH002'	//客户是否无法取得联系
    			,'DQSH003'	//联系时间
    			,'DQSH004'	//联系人与帐户持有人的关系
    			,'DQSH037'	//联系人与帐户持有人的关系说明
    			,'DQSH005'	//预计证件更新时间
    			,'DQSH006'	//未及时更新证件的理由
    			,'DQSH007'	//客户是否无正当理由拒绝更新证件
    			,'DQSH008'	//客户留存的证件及信息是否存在疑点或矛盾
    			,'DQSH009'	//账户是否频繁发生大额现金交易
    			,'DQSH010'	//账户是否频繁发生外币现钞存取业务
    			,'DQSH011'	//账户现金交易是否与客户职业特性不符
    			,'DQSH012'	//账户是否频繁发生大额的网上银行交易
    			,'DQSH013'	//账户是否与公司账户之间发生频繁或大额的交易
    			,'DQSH014'	//账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符
    			,'DQSH015'	//账户资金是否快进快出，不留余额或少留余额
    			,'DQSH016'	//账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准
    			,'DQSH017'	//账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付
    			,'DQSH018'	//账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付
    			,'DQSH019'	//账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心
    			,'DQSH020'	//账户是否频繁发生跨境交易，且金额大于1万美元
    			,'DQSH021'	//账户是否经常由他人代为办理业务
    			,'DQSH022'	//客户是否提前偿还贷款，且与其财务状况明显不符
    			,'DQSH023'	//当前账户状态是否正常
    			,'CURRENT_AUM'	//AUM(人民币)(20160318新增)
    			,'DQSH024'	//AUM(人民币)	改为  客户是否涉及反洗钱黑名单
    			,'DQSH025'	//企业证件是否过期
    			,'DQSH026'	//法定代表人证件是否过期
    			,'DQSH027'	//联系人证件是否过期
    			,'DQSH028'	//联系人的身份
    			,'DQSH038'	 //联系人的身份说明
    			,'DQSH029'	//账户是否与自然人账户之间发生频繁或大额的交易
    			,'DQSH030'	//账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符
    			,'DQSH031'	//账户是否频繁收取与其经营业务明显无关的汇款
    			,'DQSH032'	//账户资金交易频度、金额是否与其经营背景不符
    			,'DQSH033'	//账户交易对手及资金用途是否与其经营背景不符
    			,'DQSH034'	//账户是否与关联企业之间频繁发生大额交易
    			,'DQSH035'	//客户行为是否存在异常
    			,'DQSH036'	//账户交易是否存在异常
    			,'CUST_GRADE'	//客户反洗钱等级
    			,'INSTANCEID'	//审批流程ID
    			,'INSTRUCTION'	//审核结果说明
    			,'CUST_GRADE_CHECK'	//定期审核等级
    			,'AUDIT_END_DATE'	//审核截止日期
    			,'GRADE_ID' 	//审核结果说明表ID
    			,'FLAG'
	
    		])
    	});
    	//主动加载表单
    	var CUST_ID =getSelectedData().data.CUST_ID;
    	var CUST_GRADE =getSelectedData().data.CUST_GRADE;
    	var CUST_TYPE=getSelectedData().data.CUST_TYPE;
    	//CUST_TYPE ==1 是企业，2是个人
    	
    	if(CUST_TYPE=="1"){
    		fsqStore.load({
        		params : {
        			'CUST_GRADE':CUST_GRADE,
        				'CUST_ID':CUST_ID
        		},
        		callback:function(){
        			if(fsqStore.getCount()!=0){
        				fxqORGLevelAuditPanel.getForm().loadRecord(fsqStore.getAt(0));
        			}
        		}
        	});
        	//显示企业客户信息面板
        	showCustomerViewByIndex(1);
    		
    	}else if(CUST_TYPE=="2"){
	        	fsqStore.load({
	        		params : {
	        			'CUST_GRADE':CUST_GRADE,
	        				'CUST_ID':CUST_ID
	        		},
	        		callback:function(){
	        			if(fsqStore.getCount()!=0){
	        				fxqLevelAuditPanel.getForm().loadRecord(fsqStore.getAt(0));
	        			}
	        		}
	        	});
	        	//显示自定义面板
	        	showCustomerViewByIndex(0);
    	}

    }
}];


//******************************企业***************************************
//自定义form表单
var fxqORGLevelAuditPanel = new Ext.form.FormPanel({

	id : 'fxqORGLevelAuditPanel',//当前组件唯一的id
	labelWidth: 100,	// 标签宽度
	frame: true,	// 是否渲染表单面板背景色
	labelAlign: 'middle',	// 标签对齐方式
	autoScroll:true, 
	labelWidth:200,
	buttonAlign: 'center',
	
	height: 200,
	items:[{
			layout : 'column',//横排列
			
		    items:[
		           {
		        	   labelWidth:200,
		        	   columnWidth : .48,
						layout : 'form',//竖向排列
						items :[
								{labelStyle:"padding-left:30px",xtype: 'textfield',name : 'CUST_ID', fieldLabel : '客户编号',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								,{labelStyle:"padding-left:30px",xtype: 'textfield',name : 'CORE_NO', fieldLabel : '核心客户号',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								,{labelStyle:"padding-left:30px",name : 'BUILD_DATE', fieldLabel : '成立日期',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								,{labelStyle:"padding-left:30px",name : 'INDENT_NO1', fieldLabel : '证件号1',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								,{labelStyle:"padding-left:30px",name : 'IDENT_TYPE1', fieldLabel : '证件类型1',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								,{labelStyle:"padding-left:30px",name : 'IDENT_EXPIRED_DATE1', fieldLabel : '证件1到期日',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								,{labelStyle:"padding-left:30px",name : 'In_Cll_Type', fieldLabel : '行业分类',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}

								]
		           },
		           {
		        	   labelWidth:200,
		        	   columnWidth : .48,
						layout : 'form',
						items :[
						        {labelStyle:"padding-left:30px",name : 'CUST_NAME', fieldLabel : '客户名称',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								,{labelStyle:"padding-left:30px",xtype : 'combo',name : 'NATION_CODE',hiddenName : 'NATION_CODE',fieldLabel : '注册地',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',cls:'x-readOnly',readOnly:true}
								,{labelStyle:"padding-left:30px",xtype : 'combo',name : 'ENT_SCALE_CK',hiddenName : 'ENT_SCALE_CK',fieldLabel : '企业规模',store : entScaleRhStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',cls:'x-readOnly',readOnly:true}	
								,{labelStyle:"padding-left:30px",name : 'INDENT_NO2', fieldLabel : '证件号2',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        ,{labelStyle:"padding-left:30px",name : 'IDENT_TYPE2', fieldLabel : '证件类型2',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        ,{labelStyle:"padding-left:30px",name : 'IDENT_EXPIRED_DATE2', fieldLabel : '证件2到期日',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        ,{labelStyle:"padding-left:30px",xtype : 'combo',name : 'if_org_sub_type_ORG',hiddenName : 'if_org_sub_type_ORG',fieldLabel : '客户是否为自贸区客户',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
						       
								]
		           },
 
		           {
		        	   labelWidth:200,
		        	   columnWidth : .48,
						layout : 'form',
						items :[
						        {labelStyle:"padding-left:30px",xtype : 'combo',name : 'FLAG_AGENT',hiddenName : 'FLAG_AGENT',fieldLabel : '客户是否为代理开户',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
						        ,{labelStyle:"padding-left:30px",name : 'AGE_IDENT_TYPE', fieldLabel : '代理人证件类型',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        ,{labelStyle:"padding-left:30px",name : 'AGE_IDENT_NO', fieldLabel : '代理人证件号码',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						         ]
		           },
		           {
		        	   labelWidth:200,
		        	   columnWidth : .48,
						layout : 'form',
						items :[
						        {labelStyle:"padding-left:30px",name : 'AGENT_NAME', fieldLabel : '代理人姓名',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        ,{labelStyle:"padding-left:30px",xtype : 'combo',name : 'AGENT_NATION_CODE',hiddenName : 'AGENT_NATION_CODE',fieldLabel : '代理人国籍',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',cls:'x-readOnly',readOnly:true}
						        ,{labelStyle:"padding-left:30px",name : 'TEL', fieldLabel : '代理人联系方式',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						          ]
		           }
		           
		    ]},
		    {
		      	layout : 'column',//横排列
			    items:[
			           {
			        	   labelWidth:200,
			        	   columnWidth : .48,
							layout : 'form',
							items :[
									{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'FXQ022',hiddenName : 'FXQ022',fieldLabel : '<font color="red">*</font>是否在规范证券市场上市',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
							        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'FXQ021',hiddenName : 'FXQ021',fieldLabel : '<font color="red">*</font>与客户建立业务关系的渠道',store : zlFXQ021Store,resizable : true,valueField : 'key',displayField : 'value',
							        	mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
							        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'FXQ023',hiddenName : 'FXQ023',fieldLabel : '<font color="red">*</font>客户的股权或控制权结构',store : zlFXQ023Store,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
							       
							        ]
			           }
			           ,{
			        	   labelWidth:200,
			        	   columnWidth : .48,
							layout : 'form',
							items :[
									{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH024',hiddenName : 'DQSH024',fieldLabel : '<font color="red">*</font>客户是否涉及反洗钱黑名单',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
											,listeners : {
                                                select : function(n) {
                                                	//对公
                                                	var c =n.value;
                                                	orgFXQLevel(c)
                                                }
                                            }//listeners	
									
									}
							     	,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'FXQ024',hiddenName : 'FXQ024',fieldLabel : '<font color="red">*</font>客户是否存在隐名股东或匿名股东',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'	
							     	
							     	}
							        
							        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'FXQ008',hiddenName : 'FXQ008',fieldLabel : '<font color="red">*</font>客户是否涉及风险提示信息或权威媒体报道信息',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
											,listeners : {
                                                select : function(n) {
                                                	//对公
                                                	var c =n.value;
                                                	orgFXQLevel(c)
                                                }
                                            }//listeners	
									}
							       
							        ]
			           }
			           ,{
			        	   labelWidth:200,
			        	   columnWidth : .8,
							layout : 'form',
							items :[
							        {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'lovcombo',name : 'FXQ025',multi: true,multiSeparator:',',hiddenName : 'FXQ025',fieldLabel : '<font color="red">*</font>客户办理的业务',store : zlFXQ025Store,resizable : true,valueField : 'key',displayField : 'value',
							        	mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
							        
							        ]
			           }
			           
			           
			           
			          
		    	 
		    ]}
		     ,
		    {
		      	layout : 'column',//横排列
			    items:[
			           {
			        	   labelWidth:200,
			        	   columnWidth : .48,
							layout : 'form',
							items :[
							        {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH025',hiddenName : 'DQSH025',fieldLabel : '<font color="red">*</font>企业证件是否过期',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
											,listeners : {
												select : function(n) {
											
		                                                	
													//对公
                                                	var c =n.value;
                                                	orgFXQLevel(c)
                                                	
													var dqsh025= fxqORGLevelAuditPanel.form.findField("DQSH025").getValue();//企业证件是否过期
													var dqsh026=fxqORGLevelAuditPanel.form.findField("DQSH026").getValue();//法定代表人证件是否过期
													var dqsh027=fxqORGLevelAuditPanel.form.findField("DQSH027").getValue();//联系人证件是否过期
													var dqsh005id=fxqORGLevelAuditPanel.form.findField("DQSH005").id;
													var dqsh006id=fxqORGLevelAuditPanel.form.findField("DQSH006").id;

													if (dqsh025 == '1'||dqsh026 == '1'||dqsh027 == '1') {// 
														fxqORGLevelAuditPanel.form.findField("DQSH006").allowBlank = false;
														fxqORGLevelAuditPanel.form.findField("DQSH005").allowBlank = false;
														document.getElementById(dqsh005id).parentNode.parentNode.parentNode.childNodes.item(0).innerHTML='<font color="red">*</font>预计证件更新时间';
														document.getElementById(dqsh006id).parentNode.parentNode.childNodes.item(0).innerHTML='<font color="red">*</font>未及时更新证件的理由';
													} else {// 
														fxqORGLevelAuditPanel.form.findField("DQSH005").allowBlank = true;
														fxqORGLevelAuditPanel.form.findField("DQSH006").allowBlank = true;
														document.getElementById(dqsh005id).parentNode.parentNode.parentNode.childNodes.item(0).innerHTML='预计证件更新时间';
														document.getElementById(dqsh006id).parentNode.parentNode.childNodes.item(0).innerHTML='未及时更新证件的理由';
													}
												}
											}						
							        }
							        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH002',hiddenName : 'DQSH002',fieldLabel : '<font color="red">*</font>客户是否无法取得联系',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',listeners : {
                                            select : function(n) {
                                            	//对公
                                            	var c =n.value;
                                            	orgFXQLevel(c)
                                            }
                                        }//listeners	
						       }
							      
							        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH028',hiddenName : 'DQSH028',fieldLabel : '<font color="red">*</font>联系人的身份',store : zlFXQ_DQSH028Store,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
							       
							        ]
			           }
			           ,{
			        	   labelWidth:200,
			        	   columnWidth : .48,
							layout : 'form',
							items :[
							            //  {xtype : 'datefield',name : '',fieldLabel : '：',format:'Y-m-d',anchor : '90%'}
							       {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH027',hiddenName : 'DQSH027',fieldLabel : '<font color="red">*</font>联系人证件是否过期',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
											,listeners : {
												select : function(n) {
													//对公
                                                	var c =n.value;
                                                	orgFXQLevel(c)
													var dqsh025= fxqORGLevelAuditPanel.form.findField("DQSH025").getValue();//企业证件是否过期
													var dqsh026=fxqORGLevelAuditPanel.form.findField("DQSH026").getValue();//法定代表人证件是否过期
													var dqsh027=fxqORGLevelAuditPanel.form.findField("DQSH027").getValue();//联系人证件是否过期
													var dqsh005id=fxqORGLevelAuditPanel.form.findField("DQSH005").id;
													var dqsh006id=fxqORGLevelAuditPanel.form.findField("DQSH006").id;

													if (dqsh025 == '1'||dqsh026 == '1'||dqsh027 == '1') {// 
														fxqORGLevelAuditPanel.form.findField("DQSH006").allowBlank = false;
														fxqORGLevelAuditPanel.form.findField("DQSH005").allowBlank = false;
														document.getElementById(dqsh005id).parentNode.parentNode.parentNode.childNodes.item(0).innerHTML='<font color="red">*</font>预计证件更新时间';
														document.getElementById(dqsh006id).parentNode.parentNode.childNodes.item(0).innerHTML='<font color="red">*</font>未及时更新证件的理由';
													} else {// 
														fxqORGLevelAuditPanel.form.findField("DQSH005").allowBlank = true;
														fxqORGLevelAuditPanel.form.findField("DQSH006").allowBlank = true;
														document.getElementById(dqsh005id).parentNode.parentNode.parentNode.childNodes.item(0).innerHTML='预计证件更新时间';
														document.getElementById(dqsh006id).parentNode.parentNode.childNodes.item(0).innerHTML='未及时更新证件的理由';
													}
												}
											}			
							       }
							       ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'datefield',name : 'DQSH003',fieldLabel : '<font color="red">*</font>联系时间',format:'Y-m-d',anchor : '90%'}
							       ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH026',hiddenName : 'DQSH026',fieldLabel : '<font color="red">*</font>法定代表人证件是否过期',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
											,listeners : {
												select : function(n) {
													//对公
                                                	var c =n.value;
                                                	orgFXQLevel(c)													
                                                	var dqsh025= fxqORGLevelAuditPanel.form.findField("DQSH025").getValue();//企业证件是否过期
													var dqsh026=fxqORGLevelAuditPanel.form.findField("DQSH026").getValue();//法定代表人证件是否过期
													var dqsh027=fxqORGLevelAuditPanel.form.findField("DQSH027").getValue();//联系人证件是否过期
													var dqsh005id=fxqORGLevelAuditPanel.form.findField("DQSH005").id;
													var dqsh006id=fxqORGLevelAuditPanel.form.findField("DQSH006").id;

													if (dqsh025 == '1'||dqsh026 == '1'||dqsh027 == '1') {// 
														fxqORGLevelAuditPanel.form.findField("DQSH006").allowBlank = false;
														fxqORGLevelAuditPanel.form.findField("DQSH005").allowBlank = false;
														document.getElementById(dqsh005id).parentNode.parentNode.parentNode.childNodes.item(0).innerHTML='<font color="red">*</font>预计证件更新时间';
														document.getElementById(dqsh006id).parentNode.parentNode.childNodes.item(0).innerHTML='<font color="red">*</font>未及时更新证件的理由';
													} else {// 
														fxqORGLevelAuditPanel.form.findField("DQSH005").allowBlank = true;
														fxqORGLevelAuditPanel.form.findField("DQSH006").allowBlank = true;
														document.getElementById(dqsh005id).parentNode.parentNode.parentNode.childNodes.item(0).innerHTML='预计证件更新时间';
														document.getElementById(dqsh006id).parentNode.parentNode.childNodes.item(0).innerHTML='未及时更新证件的理由';
													}
												}
											}			
							       }
							        
							       
								    ]
			           },{
			        	   labelWidth:200,
			        	   columnWidth : .8,
							layout : 'form',
							items :[
							        {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'textarea',fieldLabel : '<font color="red">*</font>联系人的身份说明',name : 'DQSH038',maxLength: 100,anchor : '95%'}
							        ]
			           }
		    	 
		    ]}
		    ,
			    {
			      	layout : 'column',//横排列
				    items:[
				           {
				        	   labelWidth:200,
				        	   columnWidth : .48,
								layout : 'form',
								items :[
								        {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'datefield',name : 'DQSH005',fieldLabel : '预计证件更新时间',format:'Y-m-d',anchor : '90%'
								        	,listeners: {
								        		change: function (n) { 
								        			var dqsh005a=n.value;
								        			if(getMonthNumberBoolean(dqsh005a)){
								        				document.getElementById(n.id).value="";//获取页面元素
								        				Ext.Msg.alert("提示","预计更新时间不能大于6个月");
								        			}
								        		}
								        	}
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH007',hiddenName : 'DQSH007',fieldLabel : '<font color="red">*</font>客户是否无正当理由拒绝更新证件',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH008',hiddenName : 'DQSH008',fieldLabel : '<font color="red">*</font>客户留存的证件及信息是否存在疑点或矛盾',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH009',hiddenName : 'DQSH009',fieldLabel : '<font color="red">*</font>账户是否频繁发生大额现金交易',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH032',hiddenName : 'DQSH032',fieldLabel : '<font color="red">*</font>账户资金交易频度、金额是否与其经营背景不符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH029',hiddenName : 'DQSH029',fieldLabel : '<font color="red">*</font>账户是否与自然人账户之间发生频繁或大额的交易',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH030',hiddenName : 'DQSH030',fieldLabel : '<font color="red">*</font>账户是否存在分散转入集中转出或集中转入分散转出，且与经营背景、财务状况明显不符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH015',hiddenName : 'DQSH015',fieldLabel : '<font color="red">*</font>账户资金是否快进快出，不留余额或少留余额',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH016',hiddenName : 'DQSH016',fieldLabel : '<font color="red">*</font>账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH022',hiddenName : 'DQSH022',fieldLabel : '<font color="red">*</font>客户是否提前偿还贷款，且与其财务状况明显不符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								       
								        ]
				           }
				           ,{
				        	   labelWidth:200,
				        	   columnWidth : .48,
								layout : 'form',
								items :[
								        {labelStyle:"padding-left:30px",name : 'DQSH006', fieldLabel : '未及时更新证件的理由',anchor : '90%',xtype: 'textfield'}
								       ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH023',hiddenName : 'DQSH023',fieldLabel : '<font color="red">*</font>当前账户状态是否正常',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH031',hiddenName : 'DQSH031',fieldLabel : '<font color="red">*</font>账户是否频繁收取与其经营业务明显无关的汇款',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'FXQ009',hiddenName : 'FXQ009',fieldLabel : '<font color="red">*</font>客户实际受益人、实际控制人或者其亲属、关系密切人等是否属于外国政要',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								          ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH033',hiddenName : 'DQSH033',fieldLabel : '<font color="red">*</font>账户交易对手及资金用途是否与其经营背景不符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH017',hiddenName : 'DQSH017',fieldLabel : '<font color="red">*</font>账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH018',hiddenName : 'DQSH018',fieldLabel : '<font color="red">*</font>账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH019',hiddenName : 'DQSH019',fieldLabel : '<font color="red">*</font>账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH034',hiddenName : 'DQSH034',fieldLabel : '<font color="red">*</font>账户是否与关联企业之间频繁发生大额交易',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	//对公
	                                                	var c =n.value;
	                                                	orgFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",name : 'CURRENT_AUM', fieldLabel : 'AUM(人民币)',anchor : '90%',xtype: 'textfield',readOnly:true,cls:'x-readOnly'}
									     
									    ]
				           }
			    	 
			    ]}
			    ,
			    {
			      	layout : 'column',//横排列
				    items:[
				           {
				        	   	columnWidth : .48,
								layout : 'form',
								items :[
								        {labelStyle:"padding-left:30px",xtype : 'combo',name : 'CUST_GRADE',hiddenName : 'CUST_GRADE',fieldLabel : '当前客户洗钱风险等级',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
								    ]
				           }
				           ,{
				        	  	columnWidth : .48,
								layout : 'form',
								items :[
								        {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'CUST_GRADE_CHECK',hiddenName : 'CUST_GRADE_CHECK',fieldLabel : '<font color="red">*</font>审核结果',store : riskgradeStoreH,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
								        ]
				           }
				           ,{
				        	   columnWidth : .8,
								layout : 'form',
								items :[
								        {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'textarea',fieldLabel : '<font color="red">*</font>审核意见',name : 'INSTRUCTION',maxLength: 100,anchor : '95%'}
								        ,{name : 'CUST_TYPE', fieldLabel :'客户类型',anchor : '90%',xtype: 'textfield',hidden : true}
								        , {name : 'FLAG',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								        , {name : 'GRADE_ID',fieldLabel : '审核结果说明表ID',anchor : '90%',xtype: 'textfield',hidden : true}
									       
								        ]
				           }

				           ,{
				        	   columnWidth : .8,
								layout : 'form',
								items :[
								        //以下为企业 隐藏数据
								          {name : 'FLAG_AGENT',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ007',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          //,{name : 'FXQ008',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}DQSH010
								         // ,{name : 'DQSH010',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ010',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ011',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ012',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ013',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ014',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ015',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ016',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQCODE',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{hidden:true,labelStyle:"padding-left:30px",xtype : 'combo',name : 'DQSH035',hiddenName : 'DQSH035',fieldLabel : '<font color="red">*</font>客户行为是否存在异常',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
												mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
								          ,{hidden:true,labelStyle:"padding-left:30px",xtype : 'combo',name : 'DQSH036',hiddenName : 'DQSH036',fieldLabel : '<font color="red">*</font>账户交易是否存在异常',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
												mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
									      								      
							 
								        ]
				           }
			    	 
			    ]}
	
	
	],//items 综合
	buttons:[{
		text:'保存',
		handler:function(){
			if(!fxqORGLevelAuditPanel.form.isValid()){
				Ext.Msg.alert('提示','字段校验失败，请检查输入项！');
	            return;
	        }
			//var formValues=formpanel.getForm().getValues(); //获取表单中的所有Name键/值对对象
			//alert(formValues["firstname"]); //输出表单中 firstname 字段的值
			//console.log(formValues); //输出结果是表单中的所有Name键/值对的一个对象
			var formValues=fxqORGLevelAuditPanel.getForm().getValues();
//
//			window.console = window.console || {};
//			console.log || (console.log = opera.postError)；
			
			var commitData=translateDataKey(formValues,_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('请稍等候,正在保存中...','提示')
			Ext.Ajax.request({
	        	url : basepath + '/customerAntMoneyAuditQuery!save.json',//【非授信信息维护页面】 dealWithFsx!initFlowPersx.json 对私客户授信信息修改提交审批
	            method : 'POST',
	            params : commitData,
	            success : function(response) {
	            	
	            	var ret = Ext.decode(response.responseText);
					if(ret.existTask == 'existTask'){
						Ext.Msg.alert('提示', "当前客户已存在审核流程！");
					}else{
						Ext.Msg.alert('提示','保存成功！');
						reloadCurrentData();
						showCustomerViewByTitle('反洗钱风险等级审核-企业');
					}
					
	            },
	            failure : function(response) {
	                Ext.Msg.alert('提示', '操作失败!');
	            }
	       });
		}
	}
	,{
		text:'提交',
		waitMsg : '正在保存数据,请等待...',
		handler : function(formPanel){
//			var id = fxqLevelAuditPanel.getForm().getValues().CUST_ID;
//			var custName = fxqLevelAuditPanel.getForm().getValues().CUST_NAME;
//			var fxqRiskLevel = fxqLevelAuditPanel.getForm().getValues().FXQ_RISK_LEVEL;
//			var fxqRiskLevelAfter = fxqLevelAuditPanel.getForm().getValues().FXQ_RISK_LEVEL;
			if(!fxqORGLevelAuditPanel.form.isValid()){
				Ext.Msg.alert('提示','字段校验失败，请检查输入项！');
	            return;
	        }
			Ext.MessageBox.confirm('提示','确定执行吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return;
				} 
				
				var formValues=fxqORGLevelAuditPanel.getForm().getValues();
				var fxqRiskLevelAfter=fxqORGLevelAuditPanel.getForm().getValues().CUST_GRADE_CHECK;
				var fxqRiskLevel=fxqORGLevelAuditPanel.getForm().getValues().CUST_GRADE;
				
				var commitData=translateDataKey(formValues,_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('请稍等候,正在提交中...','提示')
				Ext.Ajax.request({
					 url : basepath+ '/customerAntMoneyAuditQuery!saveRiskLevel.json',
					 params : commitData,
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function(response) {
						
						var ret = Ext.decode(response.responseText);
						if(ret.existTask == 'existTask'){
							Ext.Msg.alert('提示', "当前客户已存在审核流程！");
						}else{
							var instanceid = ret.instanceid;//流程实例ID
							
							if(fxqRiskLevel == 'H' || fxqRiskLevelAfter == 'H'){
								selectUserList(instanceid,"131_a3","131_a8");
							}else{
								selectUserList(instanceid,"131_a3","131_a4");
							}
						}
						
						
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						} else {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
							reloadCurrentData();
						}
					}
				});
			});
		}
	},
	{
		text : '关闭',
		handler : function(formPanel) {
			hideCurrentView();
		}
	}
	
	]
	});
//*******************************************对公************************************************





































//****************************************对私*********************************************
//自定义form表单对私
var fxqLevelAuditPanel = new Ext.form.FormPanel({

	id : 'fxqLevelAuditPanel',//当前组件唯一的id
	frame: true,	// 是否渲染表单面板背景色
	labelAlign: 'middle',	// 标签对齐方式
	autoScroll:true, 
	labelWidth:200,
	buttonAlign: 'center',
	xtypeWidth:200,
	bodyStyle:"text-align:center",
	height: 200,
	items:[
	       {
	    	
			layout : 'column',//横排列
		    items:[
		           {
		        	   columnWidth : .48,
						layout : 'form',//竖向排列
						items :[
								{labelStyle:"padding-left:30px",xtype: 'textfield',name : 'CUST_ID', fieldLabel : '客户编号',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								,{labelStyle:"padding-left:30px",xtype: 'textfield',name : 'CORE_NO', fieldLabel : '核心客户号',readOnly:true,cls:'x-readOnly',anchor : '90%'}
								,{labelStyle:"padding-left:30px",name : 'BIRTHDAY', fieldLabel : '出生日期 ',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						    ]
		           },
		           {
		        	   columnWidth : .48,
						layout : 'form',
						items :[
						        {labelStyle:"padding-left:30px",name : 'CUST_NAME', fieldLabel : '客户名称',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								,{labelStyle:"padding-left:30px",xtype : 'combo',name : 'CITIZENSHIP',hiddenName : 'CITIZENSHIP',fieldLabel : '国籍',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',cls:'x-readOnly',readOnly:true}
								,{labelStyle:"padding-left:30px",xtype : 'combo',name : 'CAREER_TYPE',hiddenName : 'CAREER_TYPE',fieldLabel : '职业',store : careerTypeStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',cls:'x-readOnly',readOnly:true}
								]
		           },
		           {
		  
		        	   columnWidth : .48,
						layout : 'form',
						items :[
								{labelStyle:"padding-left:30px",name : 'IDENT_TYPE1', fieldLabel : '证件类型1',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								,{labelStyle:"padding-left:30px",name : 'INDENT_NO1', fieldLabel : '证件号1',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								,{labelStyle:"padding-left:30px",name : 'IDENT_EXPIRED_DATE1', fieldLabel : '证件1到期日',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
								]
		           },
		           {
		        	   columnWidth : .48,
						layout : 'form',
						items :[
						       {labelStyle:"padding-left:30px",name : 'IDENT_TYPE2', fieldLabel : '证件类型2',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						       ,{labelStyle:"padding-left:30px",name : 'INDENT_NO2', fieldLabel : '证件号2',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						         ,{labelStyle:"padding-left:30px",name : 'IDENT_EXPIRED_DATE2', fieldLabel : '证件2到期日',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						         ]
		           }
		           ,
		           {
		        	   columnWidth : .48,
						layout : 'form',
						items :[
						     
						        	{labelStyle:"padding-left:30px",xtype : 'combo',name : 'FLAG_AGENT',hiddenName : 'FLAG_AGENT',fieldLabel : '客户是否为代理开户',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly',anchor : '90%'}
							      ,{labelStyle:"padding-left:30px",name : 'AGE_IDENT_TYPE', fieldLabel : '代理人证件类型',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        ,{labelStyle:"padding-left:30px",name : 'AGE_IDENT_NO', fieldLabel : '代理人证件号码',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						         ]
		           },
		           {
		        	   columnWidth : .48,
						layout : 'form',
						items :[
						        {labelStyle:"padding-left:30px",name : 'AGENT_NAME', fieldLabel : '代理人姓名',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						        ,{labelStyle:"padding-left:30px",xtype : 'combo',name : 'AGENT_NATION_CODE',hiddenName : 'AGENT_NATION_CODE',fieldLabel : '代理人国籍',store : conStore,resizable : true,valueField : 'key',displayField : 'value',
									mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',cls:'x-readOnly',readOnly:true}
						        ,{labelStyle:"padding-left:30px",name : 'TEL', fieldLabel : '代理人联系电话',readOnly:true,cls:'x-readOnly',anchor : '90%',xtype: 'textfield'}
						          
						        ]
		           }
		           
		    ]},
		    {
		      	layout : 'column',//横排列
			    items:[
			           {
			        	   columnWidth : .48,
							layout : 'form',
							items :[
									{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH024',hiddenName : 'DQSH024',fieldLabel : '<font color="red">*</font>客户是否涉及反洗钱黑名单',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
											,listeners : {
                                                select : function(n) {
                                                	//对私
                                                	var c =n.value;
                                                	perFXQLevel(c)
                                                }
                                            }//listeners	
									
									}
								  ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'FXQ009',hiddenName : 'FXQ009',fieldLabel : '<font color="red">*</font>客户或其亲属、关系密切人等是否属于外国政要',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
											,listeners : {
                                                select : function(n) {
                                                	
                                                	//对私
                                                	var c =n.value;
                                                	perFXQLevel(c);
                                                }
                                            }//listeners
							        }
							         ]
			           }
			           ,{
			        	   columnWidth : .48,
							layout : 'form',
							items :[
								        {labelStyle:"padding-left:30px",xtype : 'combo',name : 'if_org_sub_type_per',hiddenName : 'if_org_sub_type_per',fieldLabel : '客户是否为自贸区客户',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%',readOnly:true,cls:'x-readOnly'}
								
							        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'FXQ008',hiddenName : 'FXQ008',fieldLabel : '<font color="red">*</font>客户是否涉及风险提示信息或权威媒体报道信息',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
											,listeners : {
                                                select : function(n) {
                                                	
                                                	//对私
                                                	var c =n.value;
                                                	perFXQLevel(c)
                                                }
                                            }//listeners	
							        }
							        	      	  ]	    
			           },{
			        	   columnWidth : .8,
							layout : 'form',
							items :[
									   
									  	{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'lovcombo',name : 'FXQ007',multi: true,multiSeparator:',',hiddenName : 'FXQ007',fieldLabel : '<font color="red">*</font>客户办理的业务',store : zlFXQ007Store,resizable : true,valueField : 'key',displayField : 'value',
							        	mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
									]
			           }
		    	 
		    ]}
		     ,
		    {
		      	layout : 'column',//横排列
			    items:[
			           {
			        	   labelWidth:200,
			        	   columnWidth : .48,
							layout : 'form',
							items :[
							        {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH001',hiddenName : 'DQSH001',fieldLabel : '<font color="red">*</font>证件是否过期',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
											,listeners : {
												select : function(n) {
													
													var c =n.value;
                                                	perFXQLevel(c)
													var dqsh001= fxqLevelAuditPanel.form.findField("DQSH001").getValue();//证件是否过期
									
													var dqsh005id=fxqLevelAuditPanel.form.findField("DQSH005").id;
													var dqsh006id=fxqLevelAuditPanel.form.findField("DQSH006").id;
													if (dqsh001 == '1') {//证件 1=过期 0=未过期
														fxqLevelAuditPanel.form.findField("DQSH005").allowBlank = false;
														document.getElementById(dqsh005id).parentNode.parentNode.parentNode.childNodes.item(0).innerHTML='<font color="red">*</font>预计证件更新时间';
														fxqLevelAuditPanel.form.findField("DQSH006").allowBlank = false;
														document.getElementById(dqsh006id).parentNode.parentNode.childNodes.item(0).innerHTML='<font color="red">*</font>未及时更新证件的理由';
													} else {
														fxqLevelAuditPanel.form.findField("DQSH005").allowBlank = true;
														document.getElementById(dqsh005id).parentNode.parentNode.parentNode.childNodes.item(0).innerHTML='预计证件更新时间';
														fxqLevelAuditPanel.form.findField("DQSH006").allowBlank = true;
														document.getElementById(dqsh006id).parentNode.parentNode.childNodes.item(0).innerHTML='未及时更新证件的理由';
													}
												}
											}				
							        }
							        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH004',fieldLabel : '<font color="red">*</font>联系人与帐户持有人的关系',store :zlFXQ_DQSH004Store,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
								     
							        ]
			           }
			           ,{
			        	   labelWidth:200,
			        	   columnWidth : .48,
							layout : 'form',
							items :[
							        {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'datefield',name : 'DQSH003',fieldLabel : '<font color="red">*</font>联系时间',format:'Y-m-d',anchor : '90%'}
							       ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH002',hiddenName : 'DQSH002',fieldLabel : '<font color="red">*</font>客户是否无法取得联系',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
										mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
											,listeners : {
                                                select : function(n) {
                                                	
                                                	//对私
                                                	var c =n.value;
                                                	perFXQLevel(c)
                                                }
                                            }//listeners	
							       }
							       
								    ]
			           },{
			        	   labelWidth:200,
			        	   columnWidth : .8,
							layout : 'form',
							items :[
							        {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'textarea',name : 'DQSH037',fieldLabel : '<font color="red">*</font>联系人与帐户持有人的关系说明',name : 'DQSH037',maxLength: 100,anchor : '95%'}
							        ]
			           }
		    	 
		    ]}
		     ,
			    {
			      	layout : 'column',//横排列
				    items:[
				           {
				        	   labelWidth:200,
				        	   columnWidth : .48,
								layout : 'form',
								items :[
					 					{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'datefield',name : 'DQSH005',fieldLabel : '预计证件更新时间',format:'Y-m-d',anchor : '90%'
					 						,listeners: {
								        		change: function (n) { 
								        			
								        			var dqsh005a=n.value;
								        			if(getMonthNumberBoolean(dqsh005a)){
								        				document.getElementById(n.id).value="";//获取页面元素
								        				Ext.Msg.alert("提示","预计更新时间不能大于6个月");
								        			}
								        		/*	//数据测试
								        		 * //var dqsh005= fxqLevelAuditPanel.getForm().getValues().DQSH005;//预计证件更新时间
								        			
								        		 * var dqsh027=fxqORGLevelAuditPanel.form.findField("DQSH005").value;
							        				var c=fxqORGLevelAuditPanel.form.findField("DQSH005");
							        				var a=fxqORGLevelAuditPanel.form;
							        				fxqORGLevelAuditPanel.form.findField("DQSH005").value="";
							        				var cc= n.value;
							        				n.value="2";
							        				var cs= n.value;
							        				var s=n.id;
							        				Ext.get(n.id).value="66";
							        				var gg =n.value;*/	
								        		}
								        	
								        	}
					 					}
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH007',hiddenName : 'DQSH007',fieldLabel : '<font color="red">*</font>客户是否无正当理由拒绝更新证件',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH008',hiddenName : 'DQSH008',fieldLabel : '<font color="red">*</font>客户留存的证件及信息是否存在疑点或矛盾',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners
												
								        }
									     
								        ]
				           }
				           ,{
				        	   labelWidth:200,
				        	   columnWidth : .48,
								layout : 'form',
								items :[
								        {labelStyle:"padding-left:30px",name : 'DQSH006', fieldLabel : '未及时更新证件的理由',anchor : '90%',xtype: 'textfield'}
								      
								        
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH009',hiddenName : 'DQSH009',fieldLabel : '<font color="red">*</font>账户是否频繁发生大额现金交易',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners
								        }
								        , {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH010',hiddenName : 'DQSH010',fieldLabel : '<font color="red">*</font>账户是否频繁发生外币现钞存取业务',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								         
									    ]
				           }
			    	 
			    ]} 
		     ,
			    {
			      	layout : 'column',//横排列
				    items:[
				           {
				        	   labelWidth:200,
				        	   columnWidth : .48,
								layout : 'form',
								items :[
								        {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH011',hiddenName : 'DQSH011',fieldLabel : '<font color="red">*</font>账户现金交易是否与客户职业特性不符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners
								        }
								        ,{labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH013',hiddenName : 'DQSH013',fieldLabel : '<font color="red">*</font>账户是否与公司账户之间发生频繁或大额的交易',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        , {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH014',hiddenName : 'DQSH014',fieldLabel : '<font color="red">*</font>账户是否存在分散转入集中转出或集中转入分散转出，且与客户身份、财务状况明显不符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners
								        }
								        , {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH017',hiddenName : 'DQSH017',fieldLabel : '<font color="red">*</font>账户是否长期闲置不明原因地突然启用，且短期内出现大量资金收付',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners
								        }
								         , {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH021',hiddenName : 'DQSH021',fieldLabel : '<font color="red">*</font>账户是否经常由他人代为办理业务',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners
								         }
								        , {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH020',hiddenName : 'DQSH020',fieldLabel : '<font color="red">*</font>账户是否频繁发生跨境交易，且金额大于1万美元',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners
								        }
								      
								        , {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH019',hiddenName : 'DQSH019',fieldLabel : '<font color="red">*</font>账户的跨境交易是否涉及贩毒、走私、恐怖活动、赌博严重地区或避税型离岸金融中心',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners
								        }
								       
								        ]
				           }
				           ,{
				        	   labelWidth:200,
				        	   columnWidth : .48,
								layout : 'form',
								items :[
								        {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH012',hiddenName : 'DQSH012',fieldLabel : '<font color="red">*</font>账户是否频繁发生大额的网上银行交易',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        , {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH015',hiddenName : 'DQSH015',fieldLabel : '<font color="red">*</font>账户资金是否快进快出，不留余额或少留余额',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        , {labelStyle:"padding-left:30px",allowBlank:false,allowBlank:false,xtype : 'combo',name : 'DQSH018',hiddenName : 'DQSH018',fieldLabel : '<font color="red">*</font>账户是否平常资金流量小，突然有异常资金流入，且短期内出现大量资金收付',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        , {labelStyle:"padding-left:30px",labelStyle:"padding-left:30px",allowBlank:false,allowBlank:false,xtype : 'combo',name : 'DQSH016',hiddenName : 'DQSH016',fieldLabel : '<font color="red">*</font>账户是否存在相同收付款人之间频繁发生交易，且金额接近大额交易标准',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners
								        }
								        , {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH023',hiddenName : 'DQSH023',fieldLabel : '<font color="red">*</font>当前账户状态是否正常',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
								        , {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'DQSH022',hiddenName : 'DQSH022',fieldLabel : '<font color="red">*</font>客户是否提前偿还贷款，且与其财务状况明显不符',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'
												,listeners : {
	                                                select : function(n) {
	                                                	
	                                                	//对私
	                                                	var c =n.value;
	                                                	perFXQLevel(c)
	                                                }
	                                            }//listeners	
								        }
								        ,{labelStyle:"padding-left:30px",name : 'CURRENT_AUM', fieldLabel : 'AUM(人民币)',anchor : '90%',xtype: 'textfield',readOnly:true,cls:'x-readOnly'}
									       
									    ]
				           }
			    	 
			    ]}
			  ,
			    {
			      	layout : 'column',//横排列
				    items:[
				           {
				        	   	columnWidth : .48,
								layout : 'form',
								items :[
								        {labelStyle:"padding-left:30px",xtype : 'combo',name : 'CUST_GRADE',hiddenName : 'CUST_GRADE',fieldLabel : '当前客户洗钱风险等级',store : riskgradeStore,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,readOnly:true,cls:'x-readOnly',triggerAction : 'all',anchor : '90%'}
								    ]
				           }
				           ,{
				        	  	columnWidth : .48,
								layout : 'form',
								items :[
								        {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'combo',name : 'CUST_GRADE_CHECK',hiddenName : 'CUST_GRADE_CHECK',fieldLabel : '<font color="red">*</font>审核结果',store : riskgradeStoreH,resizable : true,valueField : 'key',displayField : 'value',
											mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
								        ]
				           }
				           ,{
				        	   columnWidth : .8,
								layout : 'form',	
								items :[
								        {labelStyle:"padding-left:30px",allowBlank:false,xtype : 'textarea',fieldLabel : '<font color="red">*</font>审核意见',name : 'INSTRUCTION',maxLength: 100,anchor : '95%'}
								       ,
								       {name : 'CUST_TYPE', fieldLabel : '<font color="red">*</font>客户类型',anchor : '90%',xtype: 'textfield',hidden : true}
								       , {name : 'FLAG',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								       , {name : 'GRADE_ID',fieldLabel : '审核结果说明表ID',anchor : '90%',xtype: 'textfield',hidden : true}
									     
								       ]
				           },{
				        	   columnWidth : .8,
								layout : 'form',
								items :[
								        //以下为企业 隐藏数据
								         {name : 'FXQ010',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ011',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ012',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ013',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ014',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ015',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ016',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ021',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ022',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ023',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ024',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQ025',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{name : 'FXQCODE',fieldLabel : '客户标示',anchor : '90%',xtype: 'textfield',hidden : true}
								          ,{hidden:true,labelStyle:"padding-left:30px",xtype : 'combo',name : 'DQSH035',hiddenName : 'DQSH035',fieldLabel : '<font color="red">*</font>客户行为是否存在异常',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
												mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
								          	,{hidden:true,labelStyle:"padding-left:30px",xtype : 'combo',name : 'DQSH036',hiddenName : 'DQSH036',fieldLabel : '<font color="red">*</font>账户交易是否存在异常',store : ifStore,resizable : true,valueField : 'key',displayField : 'value',
												mode : 'local',forceSelection : true,triggerAction : 'all',anchor : '90%'}
									      								      
							
								        ]
				           }
			    	 
			    ]}
	
	
	],//items 综合
	buttons:[{
		text:'保存',
		handler:function(){
			
			if(!fxqLevelAuditPanel.form.isValid()){
				Ext.Msg.alert('提示','字段校验失败，请检查输入项！');
	            return;
	        }
			//var formValues=formpanel.getForm().getValues(); //获取表单中的所有Name键/值对对象
			//alert(formValues["firstname"]); //输出表单中 firstname 字段的值
			//console.log(formValues); //输出结果是表单中的所有Name键/值对的一个对象
			
			var formValues=fxqLevelAuditPanel.getForm().getValues();		
			var commitData=translateDataKey(formValues,_app.VIEWCOMMITTRANS);
			Ext.Msg.wait('请稍等候,正在保存中...','提示')
			Ext.Ajax.request({
	        	url : basepath + '/customerAntMoneyAuditQuery!save.json',//【非授信信息维护页面】 dealWithFsx!initFlowPersx.json 对私客户授信信息修改提交审批
	            method : 'POST',
	            params : commitData,
	            success : function(response) {
	            	
	            	var ret = Ext.decode(response.responseText);
					if(ret.existTask == 'existTask'){
						Ext.Msg.alert('提示', "当前客户已存在审核流程,不允许修改信息！");
					}else{
						Ext.Msg.alert('提示','保存成功！');
						//reloadCurrentData();
						showCustomerViewByTitle('反洗钱风险等级审核-个人');
					}
	            },
	            failure : function(response) {
	                Ext.Msg.alert('提示', '操作失败!');
	                
	            }
	       });
		}
	},{
		text:'提交',
		waitMsg : '正在保存数据,请等待...',
		handler : function(formPanel){
			if(!fxqLevelAuditPanel.form.isValid()){
				Ext.Msg.alert('提示','字段校验失败，请检查输入项！');
	            return;
	        }
//			var id = fxqLevelAuditPanel.getForm().getValues().CUST_ID;
//			var custName = fxqLevelAuditPanel.getForm().getValues().CUST_NAME;
//			var fxqRiskLevel = fxqLevelAuditPanel.getForm().getValues().FXQ_RISK_LEVEL;
//			var fxqRiskLevelAfter = fxqLevelAuditPanel.getForm().getValues().FXQ_RISK_LEVEL;
			Ext.MessageBox.confirm('提示','确定执行吗?',function(buttonId){
				if(buttonId.toLowerCase() == "no"){
					return;
				} 
				
				var formValues=fxqLevelAuditPanel.getForm().getValues();
				var fxqRiskLevelAfter=fxqLevelAuditPanel.getForm().getValues().CUST_GRADE_CHECK;
				var fxqRiskLevel=fxqLevelAuditPanel.getForm().getValues().CUST_GRADE;
				var commitData=translateDataKey(formValues,_app.VIEWCOMMITTRANS);
				Ext.Msg.wait('请稍等候,正在提交中...','提示')
				Ext.Ajax.request({
					 url : basepath+ '/customerAntMoneyAuditQuery!saveRiskLevel.json',
					 params : commitData,
					waitMsg : '正在保存数据,请等待...', // 显示读盘的动画效果，执行完成后效果消失
					success : function(response) {
						
						
						var ret = Ext.decode(response.responseText);
						if(ret.existTask == 'existTask'){
							Ext.Msg.alert('提示', "当前客户已存在审核流程！");
						}else{
							var instanceid = ret.instanceid;//流程实例ID
							
							if(fxqRiskLevel == 'H' || fxqRiskLevelAfter == 'H'){
								selectUserList(instanceid,"131_a3","131_a8");
							}else{
								selectUserList(instanceid,"131_a3","131_a4");
							}
						}
						
						
					},
					failure : function(response) {
						var resultArray = Ext.util.JSON.decode(response.status);
						if(resultArray == 403) {
							Ext.Msg.alert('提示', response.responseText);
						} else {
							Ext.Msg.alert('提示', '操作失败,失败原因:' + response.responseText);
							reloadCurrentData();
						}
					}
				});
			});
		}
	},{
		text:'关闭',
		handler : function(formPanel){
			hideCurrentView();
		}
	}
	]
	})
//*********************************对私结束********************************
//客户信息变更JsonReader
var customerView = [
{
  	title:'反洗钱风险等级审核-个人',
	hideTitle:true,//隐藏抬头
	type: 'form',
	suspendWidth: 1,
	items:[fxqLevelAuditPanel]
},
{
  	title:'反洗钱风险等级审核-企业',
  	
	hideTitle:true,//隐藏抬头
	type: 'form',
	suspendWidth: 1,
	items:[fxqORGLevelAuditPanel]
}
];

//******************************************************
/**
 * APP初始化之后触发；
 * params ： app：当前APP对象；
 */
var afterinit = function(theview){
}