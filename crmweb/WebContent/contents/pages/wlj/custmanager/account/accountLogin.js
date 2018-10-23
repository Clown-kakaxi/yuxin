/**
 * @description 一键开户
 * @author ganyu
 * @since 2017-07-17
*/
imports([
    '/contents/commonjs/jquery-1.5.2.min.js',
    '/contents/pages/com.yucheng.bcrm/com.yucheng.bcrm.js',
    '/contents/pages/wlj/custmanager/account/accountSecond.js'
]);
/**
 * 用于存储各种数据
 * @type Object
 */
var loginData = {};
/**
 * 高拍仪扫描数据
 * @type Object
 */
var scanData = {};

/**
 * 放大字体14px
 * @param {} text
 * @return {} 放大后的字体
 */
function convertFontCss(text){
    return '<font style="font-size:14px;">'+text+'</font>';
 }

/**
 * [showScanResult 反显高拍仪数据]
 * @param  {[type]} result [description]
 * @return {[type]}        [description]
 */
function showScanResult(result){
    alert('反显高拍仪数据');
    alert(reult);
};
var visitJson = {};
var serializeId = getSerializeId();//唯一交易流水号
/**
 * 生成交易流水号,格式为：年+月+日+时+分+秒+毫秒+去掉字母的员工号
 * @return {} String 交易流水号
 */
function getSerializeId(){
    var date = new Date();
    var yyyy = date.getFullYear();//年
    var MM = date.getMonth() + 1;//月
    if(MM < 10){
        MM = "0" + MM;
    }
    var dd = date.getDate();//日
    if(dd < 10){
        dd = "0" + dd;
    }
    var HH = date.getHours();//时
    if(HH < 10){
        HH = "0" + HH;
    }
    var mm = date.getMinutes();//分
    if(mm < 10){
        mm = "0" + mm;
    }
    var ss = date.getSeconds();//秒
    if(ss < 10){
        ss = "0" + ss;
    }
    var ms = date.getMilliseconds();//毫秒
    if(ms < 10){
        ms = "0" + ms;
    }
    //如果员工号长度大于等于8，则包含了字母，去掉字母(暂不清楚原因)
    if(__userId.length >= 8){
        var serUserId = __userId.substring(0, 3) + __userId.substring(4, 8);
    }
    return yyyy + "" + MM + "" + dd + "" + HH + "" + mm + "" + ss + "" + ms + "" + serUserId;
}
/**
 * 在预约开户的【客户证件号】后面插入提示html
 * 在临柜开户的【客户姓名】后面插入【联名户】复选框和提示信息
 */
function initLayout() {
    //在预约开户的【客户证件号】后面插入提示html
    Ext.get("reviewIdentNumLogin").parent("div").insertHtml("beforeEnd",'<span style="color:blue;">&nbsp;&nbsp;&nbsp;<font style="font-size:14px;">(请您填写预约时的开户证件号,若为联名户请填写主户证件号)</font></span>');
    //在临柜开户的【客户姓名】后面插入【联名户】复选框和提示信息
    var html = [
       '<div style="display:inline-block;padding:0 10px;">',
           '<input id="checkbox" type="checkbox" onclick="javascript:cutTextField();">',
           '<span>',
               '<font style="font-size:14px;"> 联名户  </font>',
               '<span style="color:blue;">',
                   '<font style="font-size:14px;">联名户温馨提示：</font>',
               '</span>',
            '</span>',
            '<img src="'+basepath+'/contents/images/tip2.png" style="cursor:pointer;" title="联名户姓名输入方式：主户名/从户名；\n证件类型1为主户名证件，证件类型2为从户名证件；\n申请开通联名户前提是双人在我行已开通个人账户 "/>',
        '</div>'
    ];
    Ext.get("custName").parent("div").insertHtml("beforeEnd",html.join(''));
}
/**
 * 联名户复选框被点击后触发的事件函数
 */
function cutTextField() {
    //重置表单输入项
    cust_form.getForm().reset();
    //如果复选框选中了，即为联名户，联动证件控件的显示和隐藏，只显示证件1和证件2(还有客户名称)
    if($("#checkbox")[0] && $("#checkbox")[0].checked){
        Ext.getCmp('identType').setVisible(false);
        Ext.getCmp('identNum').setVisible(false);
        Ext.getCmp('twIdentNum').setVisible(false);
        Ext.getCmp('gaIdentNum').setVisible(false);
        Ext.getCmp('identType1').setVisible(true);
        Ext.getCmp('identType2').setVisible(true);
        Ext.getCmp('identNum1').setVisible(true);
        Ext.getCmp('identNum2').setVisible(true);
    }else{//非联名户，只显示：证件类型，证件号码(还有客户名称)
        Ext.getCmp('identType').setVisible(true);
        Ext.getCmp('identNum').setVisible(true);
        Ext.getCmp('identType1').setVisible(false);
        Ext.getCmp('identType2').setVisible(false);
        Ext.getCmp('identNum1').setVisible(false);
        Ext.getCmp('identNum2').setVisible(false);
        Ext.getCmp('twIdentNum1').setVisible(false);
        Ext.getCmp('twIdentNum2').setVisible(false);
        Ext.getCmp('gaIdentNum1').setVisible(false);
        Ext.getCmp('gaIdentNum2').setVisible(false);
    }
}

//证件类型的下拉选项
var store_zhjlx=new Ext.data.Store( {
    restful : true,
    autoLoad : true,
    sortInfo : {
            field:'key',
            direction:'ASC'
        },
    proxy : new Ext.data.HttpProxy( {
        url : basepath + '/lookup.json?name=XD000368'
    }),
    reader : new Ext.data.JsonReader( {
        root : 'JSON'
    },['key','value'])
});

//屏蔽旅行证和临时台胞证
store_zhjlx.on('load',function(){
    this.each(function(result){
        if(result.get("key") == 'X3' || result.get("key") == 'X24'){
            store_zhjlx.remove(result);
        }
    });
});


/**
 * 预约开户,点击进入按钮触发事件函数
 */
function visitAccount(){
    visitJson.fromReview = true;
    //从临柜进入
    if(visitJson.fromLinGui == true){
        Ext.getCmp("reviewBtn").disable();
        openCustInfoPanel();
        /* var checkParams = {
            'logId' : serializeId,
            'jointaccount':visitJson.jointaccount,
            'custNm' : visitJson.customername,//客户姓名
            'certtype' :visitJson.certtype,//客户证件类型
            'certid' :  visitJson.certid//客户证件号码
            //'twIdentNum' : visitJson.twcertid//辅助证件号码
        };*/
        
        //根据核查结果判断是否具有开户资格
        //黑名单联网核查
        //NetCheckAndBlackOrderCheck(checkParams);  
    }else{//从预约直接进入
        //合并高拍仪扫描数据
        Ext.apply(visitJson,scanData);
        var myMask;
        //查询客户预约信息
        if(!Ext.getCmp("reviewNumLogin").getValue() || !Ext.getCmp("reviewIdentNumLogin").getValue()){
            Ext.Msg.alert('预约信息查询', '查询预约信息时需要预约号和证件号');
            return;
        }
        Ext.getCmp("reviewBtn").disable();
        visitJson.reviewNumLogin = Ext.getCmp("reviewNumLogin").getValue();//客户预约号
        visitJson.reviewIdentNumLogin = Ext.getCmp("reviewIdentNumLogin").getValue();//客户证件号
        $.ajax({
            url : basepath + '/oneKeyAccountAction!checkCustOrderInfo.json?tm='+Date.now(),
            type : "GET",
            timeout : 180000,
            dataType : "json",
            data : {
                'serializeId' : serializeId,
                'flag' : '2',//查询方式   1：根据姓名证件号证件类型查询 2：根据预约号和证件号查询
                'orderNo' : visitJson.reviewNumLogin,
                'custName' : '',
                'certtype' : '',
                'certid' : visitJson.reviewIdentNumLogin
            },
            beforeSend : function(){
                myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在查询客户预约信息，请稍等..."});
                myMask.show();
            },
            success : function(result) {
                if(myMask){
                    myMask.hide();
                    myMask.destroy();
                    myMask = null;
                }
                if(result.JSON.status == 'success'){
                    var appStatus = result.JSON.custInfo.appStatus;//预约状态 0-取消预约 1-预约成功
                    if(appStatus == '1'){
                       //预约信息返显
                        reviewShow(result);
                        var checkParams = {
                            'logId' : serializeId,
                            'jointaccount':visitJson.jointaccount,
                            'custNm' : visitJson.customername,//客户姓名
                            'certtype' :visitJson.certtype,//客户证件类型
                            'certid' :  visitJson.certid,//客户证件号码
                            'certtype2' :visitJson.certtype2,//从户证件类型
                            'certid2' :  visitJson.certid2//从户证件号码
                        };
                        //根据核查结果判断是否具有开户资格
                        //黑名单联网核查
                        NetCheckAndBlackOrderCheck(checkParams);
                    }else{
                        Ext.Msg.alert('提示', "您取消了预约~");
                        Ext.getCmp("reviewBtn").enable();
                        return;
                    }
                }else{
                    Ext.Msg.alert('提示', result.JSON.msg);
                    Ext.getCmp("reviewBtn").enable();
                    return;
                }
            },
            complete : function(response, status){
                if(myMask){
                    myMask.hide();
                    myMask.destroy();
                    myMask = null;
                }
                if(status && status == 'timeout'){//超时
                    Ext.Msg.alert('预约信息查询', '核查超时，请联系管理员');
                    Ext.getCmp("reviewBtn").enable();
                    return;     
                }
            }
        });
    }
}

/**
 * 预约信息返显,解析预约查询返回的预约信息
 * @param {} result
 */
function reviewShow(result){
   //姓名
   visitJson.customername = result.JSON.custInfo.customername;
   //预约证件类型1为临时台胞证和旅行证件
   if(result.JSON.custInfo.certtype == 'X24' || result.JSON.custInfo.certtype == 'X3'){
        visitJson.certtype = 'X2';//则主证件类型设置为台湾身份证
        visitJson.identTypeJoint = result.JSON.custInfo.certtype;//联名户证件类型为预约证件类型
        visitJson.fzCertType = result.JSON.custInfo.certtype;//辅证件类型为预约证件类型
   }else{
        //台湾身份证
        if(result.JSON.custInfo.certtype == 'X2' ){
            visitJson.certtype = result.JSON.custInfo.certtype;//主证件类型为台湾身份证
            visitJson.identTypeJoint = '6';//联名户证件类型为台胞证
            visitJson.fzCertType = '6';//辅证件类型为台胞证
        }else if(result.JSON.custInfo.certtype == 'X1' ){//港澳身份证
            visitJson.certtype = result.JSON.custInfo.certtype;//主证件类型为港澳身份证
            visitJson.identTypeJoint = '5';//联名户证件类型为港澳通行证
            visitJson.fzCertType = '5';//辅证件类型为港澳通行证
        }else{
            visitJson.certtype = result.JSON.custInfo.certtype;//主证件类型为预约时的证件类型
            visitJson.identTypeJoint = result.JSON.custInfo.certtype;//联名户证件类型为预约时的证件类型
            visitJson.fzCertType = result.JSON.custInfo.certtype;//辅证件类型为预约时的证件类型
        }
   }
  
   //证件号码
   visitJson.certid = result.JSON.custInfo.certid;
   //台湾身份证号 
   visitJson.twcertid = result.JSON.custInfo.twcertid;
   visitJson.fzCertNo = result.JSON.custInfo.twcertid;
   //国家代码
   visitJson.mobilephonecoun = result.JSON.custInfo.mobilephonecoun;
   //地区代码
   visitJson.mobilecouncode = result.JSON.custInfo.mobilecouncode;
   //手机号
   visitJson.mobilephoneno = result.JSON.custInfo.mobilephoneno;
   //预约日期
   visitJson.orderDate = result.JSON.custInfo.orderdate;
   //预约网点
   visitJson.orderDept = result.JSON.custInfo.inputorg;
   //预约时间段
   visitJson.orderTime = result.JSON.custInfo.pretime;
   //来源渠道
   visitJson.channelNo = result.JSON.custInfo.channel;
   //是否是联名户
   if(result.JSON.custInfo.jointaccount == 'Y' || result.JSON.custInfo.jointaccount == '1'){
        visitJson.jointaccount = '1';
   }else{
        visitJson.jointaccount = '0';
   }
   //visitJson.jointaccount = result.JSON.custInfo.jointaccount;
   //预约证件类型2为临时台胞证、旅行证件
   if(result.JSON.custInfo.certtype2 == 'X24' || result.JSON.custInfo.certtype2 == 'X3'){
        visitJson.certtype2 = 'X2';//证件2类型为台湾身份证
        visitJson.fzCertType2 = result.JSON.custInfo.certtype2;//证件2辅助证件为预约证件类型2
   }else{
        visitJson.certtype2 = result.JSON.custInfo.certtype2;//证件类型2为预约证件类型2
        if(result.JSON.custInfo.certtype2 = 'X2'){//台湾身份证
            visitJson.fzCertType2 = '6';//台胞证
        }else if(result.JSON.custInfo.certtype2 = 'X1'){//港澳身份证
            visitJson.fzCertType2 = '5';//港澳通行证
        }else{
            visitJson.fzCertType2 = result.JSON.custInfo.certtype2;
        }
   }
   //visitJson.certtype2 = result.JSON.custInfo.certtype2;
   //证件号码
   visitJson.certid2 = result.JSON.custInfo.certid2;
   //台湾身份证号 
   visitJson.twcertid2 = result.JSON.custInfo.twcertid2;
   visitJson.fzCertNo2 = result.JSON.custInfo.twcertid2;
   //是预约客户的标识--主要是为了后面数据返显得时候的预约网点，预约时间的返显
   visitJson.isReviewCus = true;
}

/**
 * 查询客户预约信息
 */
function checkCustOrderInfo(){
    var reviewCerttype = visitJson.certtype;
    //如果是旅行证或者临时台胞证，使用辅助证件查询预约信息，如果是台湾往来通行证，则使用台湾身份证
    if(visitJson.certtype == 'X2' && visitJson.fzCertType != '6'){
        reviewCerttype = visitJson.fzCertType;
    }
    
    var myMask;
    $.ajax({//查询客户预约信息
        url : basepath + '/oneKeyAccountAction!checkCustOrderInfo.json?tm='+Date.now(),
        type : "GET",
        timeout : 180000,
        dataType : "json",
        data : {
            'serializeId' : serializeId,
            'flag' : '1',//查询方式   1：根据姓名证件号证件类型查询 2：根据预约号和证件号查询
            'orderNo' : '',
            'custName' : visitJson.customername,
            'certtype' : reviewCerttype,
            'certid' : visitJson.certid
        },
        beforeSend : function(){
            myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在查询客户预约信息，请稍等..."});
            myMask.show();
        },
        success : function(result) {
            if(result.JSON.status == 'success'){
                var appStatus = result.JSON.custInfo.appStatus;//预约状态 0-取消预约 1-预约成功
                if(appStatus == '1'){//预约客户
                         //预约信息返显
                         reviewShow(result);
                         //预约号
                         visitJson.sOrderSerialNo = result.JSON.custInfo.sorderserialno;
                         Ext.getCmp('reviewNumLogin').setValue(visitJson.sOrderSerialNo);
                         //证件号码
                         visitJson.certid = result.JSON.custInfo.certid;
                         Ext.getCmp('reviewIdentNumLogin').setValue(visitJson.certid);
                         
                         if($("#checkbox")[0] && $("#checkbox")[0].checked){//有联名户
                               $("#checkbox")[0].checked = false;
                               Ext.getCmp('custName').setValue("");
                               Ext.getCmp('identType1').setValue("");
                               Ext.getCmp('identNum1').setValue("");
                               Ext.getCmp('twIdentNum1').setValue("");
                               Ext.getCmp('gaIdentNum1').setValue("");
                               Ext.getCmp('identType2').setValue("");
                               Ext.getCmp('identNum2').setValue("");
                               Ext.getCmp('twIdentNum2').setValue("");
                               Ext.getCmp('gaIdentNum2').setValue("");
                        }else{
                               Ext.getCmp('custName').setValue("");
                               Ext.getCmp('identType').setValue("");
                               Ext.getCmp('identNum').setValue("");
                               Ext.getCmp('twIdentNum').setValue("");
                               Ext.getCmp('gaIdentNum').setValue("");
                        }
                       visitJson.fromLinGui = true;
                       Ext.getCmp("linguiBtn").enable();
                }else{//非预约客户
                    /*var checkParams = {
                        'logId' : serializeId,
                        'jointaccount':visitJson.jointaccount,
                        'custNm' : visitJson.customername,//客户姓名
                        'certtype' :visitJson.certtype,//主户证件类型
                        'certid' :  visitJson.certid,//主户证件号码
                        //'twIdentNum' : visitJson.twcertid,//主户辅助证件号码
                        'certtype2' :visitJson.certtype2,//从户证件类型
                        'certid2' :  visitJson.certid2,//从户证件号码
                        'jointaccount' : visitJson.jointaccount //是否联名户的标志
                        //'twIdentNum2' : visitJson.fzCertNo2//从户辅助证件号码
                    };
                    //根据核查结果判断是否具有开户资格
                    //黑名单联网核查
                    NetCheckAndBlackOrderCheck(checkParams);*/
                    
                    openCustInfoPanel();
            }
            }else{
                 /*var checkParams = {
                    'logId' : serializeId,
                    'jointaccount':visitJson.jointaccount,
                    'custNm' : visitJson.customername,//客户姓名
                    'certtype' :visitJson.certtype,//客户证件类型
                    'certid' :  visitJson.certid,//客户证件号码
                    //'twIdentNum' : visitJson.fzCertNo,//辅助证件号码
                    'certtype2' :visitJson.certtype2,//从户证件类型
                    'certid2' :  visitJson.certid2,//从户证件号码
                    'jointaccount' : visitJson.jointaccount //是否联名户的标志
                    //'twIdentNum2' : visitJson.fzCertNo2//从户辅助证件号码
                };
                //根据核查结果判断是否具有开户资格
                NetCheckAndBlackOrderCheck(checkParams);*/
                openCustInfoPanel();
            }
        },
        complete : function(request, status){
            if(myMask){
                myMask.hide();
            }
            if(status && status == 'timeout'){//超时
                Ext.Msg.alert('预约信息查询', '核查超时，请联系管理员');
                Ext.getCmp("linguiBtn").enable();
                return;     
            }
        }
    });
}

/**
 * 临柜开户入口
 * @return {Boolean}
 */
function visitingCustAccount() {
    visitJson = {};
    Ext.apply(visitJson, scanData);//整合高拍仪扫描到的数据
    // 非联名户证件
    var visitCustName = Ext.getCmp('custName').getValue();// 客户姓名
    var identType = Ext.getCmp('identType').getValue();// 客户证件类型
    var identNum = Ext.getCmp('identNum').getValue();// 客户证件号码
    var twIdentNum = Ext.getCmp('twIdentNum').getValue();// 台湾国民身份证号码
    var gaIdentNum = Ext.getCmp('gaIdentNum').getValue();// 港澳通行证件号

    // 联名户证件1,2
    var identType1 = Ext.getCmp('identType1').getValue();// 证件类型1
    var identNum1 = Ext.getCmp('identNum1').getValue();// 证件号码1
    var twIdentNum1 = Ext.getCmp('twIdentNum1').getValue();// 台湾国名身份证号码1
    var gaIdentNum1 = Ext.getCmp('gaIdentNum1').getValue();// 港澳通行证件号1

    var identType2 = Ext.getCmp('identType2').getValue();// 证件类型2
    var identNum2 = Ext.getCmp('identNum2').getValue();// 证件号码2
    var twIdentNum2 = Ext.getCmp('twIdentNum2').getValue();// 台湾国名身份证号码2
    var gaIdentNum2 = Ext.getCmp('gaIdentNum2').getValue();// 港澳通行证件号2

    var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/; // 身份证格式
    var reg1 = /^[0-9]{8}$/; // 台胞证格式
    var reg2 = /^[A-Z]{1}[0-9]{9}$/; // 台湾身份证格式
    var reg3 = /^[^\u4E00-\u9FA5=']{1,}$/;// 其他证件格式
    var reg4 = /^[^\u4E00-\u9FA5]{0,}$/;// 不能输入汉字

    if(visitCustName == '') {
        Ext.Msg.alert('提示', '请填写客户姓名！');
        return false;
    }

    if(!Ext.getCmp("custName").isValid()) {
        Ext.Msg.alert('提示', '客户姓名输入格式错误！');
        return;
    }
    visitJson.customername = visitCustName;
    //联名户处理
    if($("#checkbox")[0] && $("#checkbox")[0].checked) {// 有联名户
        visitJson.jointaccount = 1;//联名户标志，1：联名户；0：非联名户
        //联名户格式校验
        if(visitCustName.indexOf("/") < 0 || visitCustName.indexOf("/") == 0 || visitCustName.indexOf("/") == visitCustName.length - 1) {
            Ext.Msg.alert('提示', '联名户姓名输入格式错误，例：张三/李四');
            return false;
        } else {
            visitCustName.substring(0, visitCustName.indexOf("/"));
        }
        //证件类型1校验
        if(identType1 == '') {
            Ext.Msg.alert('提示', '请选择客户证件类型1！');
            return false;
        } else {
            if(!Ext.getCmp('identType1').isValid()) {
                Ext.Msg.alert('提示', '客户证件类型1不符合规范！');
                return;
            }
        }
        //证件号码1校验
        if(identNum1 == '') {
            Ext.Msg.alert('提示', '请填写客户证件号码1');
            return;
        } else {
            if(!Ext.getCmp('identNum1').isValid()) {
                Ext.Msg.alert('提示', '客户证件号码1不符合规范！');
                return;
            }
            visitJson.certid = identNum1;
            visitJson.certtype = identType1;
        }
        //台胞证、临时台胞证、旅行证，如果主证件，即证件1选择了这3个中的一个，则将证件1作为辅证件，台湾身份证作为开户证件
        if(identType1 == '6' || identType1 == 'X24' || identType1 == 'X3') {// 台湾同胞来往内地通行证1
            if(twIdentNum1 == '') {
                Ext.Msg.alert('提示', '请填写台湾身份证号码1');
                return;
            } else {
                if(!Ext.getCmp('twIdentNum1').isValid()) {
                    Ext.Msg.alert('提示', '台湾身份证号码1不符合规范！');
                    return;
                }
                visitJson.certid = twIdentNum1;//开户证件号码为台湾身份证号码
                visitJson.certtype = 'X2';//开户证件类型为台湾国民身份证
                visitJson.fzCertType = identType1;//辅证件类型为证件1的类型
                visitJson.fzCertNo = identNum1;//辅证件号码为证件1的号码
            }
        } else if(identType1 == '5') {// 港澳通行证件号1
            //如果证件1选择了港澳通行证，则将证件1作为辅证件，港澳身份证作为开户证件
            if(gaIdentNum1 == '') {
                Ext.Msg.alert('提示', '请填写港澳身份证号码1');
                return;
            } else {
                if(!Ext.getCmp('gaIdentNum1').isValid()) {
                    Ext.Msg.alert('提示', '港澳身份证号码1不符合规范！');
                    return;
                }
                visitJson.certid = gaIdentNum1;//开户证件号码为港澳证号码
                visitJson.certtype = 'X1';//开户证件类型为港澳身份证
                visitJson.fzCertType = identType1;//辅证件类型为证件1的类型
                visitJson.fzCertNo = identNum1;//辅证件号码为证件1的号码
            }
        }

        if(identType2 == '') {
            Ext.Msg.alert('提示', '请选择客户证件类型2！');
            return false;
        } else {
            if(!Ext.getCmp('identType2').isValid()) {
                Ext.Msg.alert('提示', '客户证件类型2不符合规范！');
                return;
            }
            if(identNum2 == '') {
                Ext.Msg.alert('提示', '请填写客户证件号码2');
                return;
            } else {
                if(!Ext.getCmp('identNum2').isValid()) {
                    Ext.Msg.alert('提示', '客户证件号码2不符合规范！');
                    return;
                }
                visitJson.certid2 = identNum2;//证件2号码
                visitJson.certtype2 = identType2;//证件2类型
            }
            //如果证件2为：台胞证、临时台胞证、旅行证，则证件2为辅助证件，港澳身份证为开户证件
            if(identType2 == '6' || identType2 == 'X24' || identType2 == 'X3') {
                if(twIdentNum2 == '') {
                    Ext.Msg.alert('提示', '请填写台湾身份证号码2');
                    return;
                } else {
                    if(!Ext.getCmp('twIdentNum2').isValid()) {
                        Ext.Msg.alert('提示', '台湾身份证号码2不符合规范！');
                        return;
                    }
                    visitJson.certid2 = twIdentNum2;//开户证件号码为台湾身份证号码
                    visitJson.certtype2 = 'X2';//开户证件类型为台湾国民身份证
                    visitJson.fzCertType2 = identType2;//辅证件类型为证件2的类型
                    visitJson.fzCertNo2 = identNum2;//辅证件号码为证件2的号码
                }
            } else if(identType2 == '5') {// 港澳通行证件号
                //如果证件2选择了港澳通行证，则将证件2作为辅证件，港澳身份证作为开户证件
                if(gaIdentNum2 == '') {
                    Ext.Msg.alert('提示', '请填写港澳身份证号码2');
                    return;
                } else {
                    if(!Ext.getCmp('gaIdentNum2').isValid()) {
                        Ext.Msg.alert('提示', '港澳身份证号码2不符合规范！');
                        return;
                    }
                    visitJson.certid2 = gaIdentNum2;//开户证件号码为港澳身份证号码
                    visitJson.certtype2 = 'X1';//开户证件类型为港澳身份证
                    visitJson.fzCertType2 = identType2;//辅证件类型为证件1的类型
                    visitJson.fzCertNo2 = identNum2;//辅证件号码为证件1的号码
                }
            }
        }

        // 主从户相同证件类型条件下证件号码不能相同
        if(identType1 == identType2 && identNum1 == identNum2) {
            Ext.Msg.alert('提示', '主从户证件类型相同情况下证件号码不能相同！');
            return false;
        }
        //联名户的证件类型为证件1的证件类型
        visitJson.identTypeJoint = Ext.getCmp('identType1').getValue();
    } else {// 非联名户
        visitJson.identTypeJoint = Ext.getCmp('identType').getValue();
        visitJson.jointaccount = 0;//是否联名户标志，1：联名户；0：非联名户
        if(identType == '') {
            Ext.Msg.alert('提示', '请选择客户证件类型！');
            return false;
        } else {
            if(!Ext.getCmp('identType').isValid()) {
                Ext.Msg.alert('提示', '客户证件类型不符合规范！');
                return;
            }
            if(identNum == '') {
                Ext.Msg.alert('提示', '请填写客户证件号码');
                return;
            } else {
                if(!Ext.getCmp("identNum").isValid()) {
                    Ext.Msg.alert('提示', '客户证件号码不符合规范！');
                    return;
                }
                visitJson.certid = identNum;//证件号码
                visitJson.certtype = identType;//证件类型
            }
            //台湾同胞来往内地通行证||临时台胞证||旅行证件,则开户证件为台湾身份证，原来的证件为辅助证件
            if(identType == '6' || identType == 'X24' || identType == 'X3') {
                if(twIdentNum == '') {
                    Ext.Msg.alert('提示', '请填写台湾身份证号码');
                    return;
                } else {
                    if(!Ext.getCmp("twIdentNum").isValid()) {
                        Ext.Msg.alert('提示', '台湾身份证号码不符合规范！');
                        return;
                    }
                    visitJson.certid = twIdentNum;//开户证件号码为台湾身份证号码
                    visitJson.certtype = 'X2';//开户证件类型为台湾国民身份证
                    visitJson.fzCertType = identType;//辅证件类型为原证件的类型
                    visitJson.fzCertNo = identNum;//辅证件号码为原证件的号码
                }
            } else if(identType == '5') {
                //原证件为：港澳通行证，则将原证件作为辅证件，港澳身份证作为开户证件
                if(gaIdentNum == '') {
                    Ext.Msg.alert('提示', '请填写港澳身份证号码');
                    return;
                } else {
                    if(!Ext.getCmp("gaIdentNum").isValid()) {
                        Ext.Msg.alert('提示', '港澳身份证号码不符合规范！');
                        return;
                    }
                    visitJson.certid = gaIdentNum;//开户证件号码为港澳身份证号码
                    visitJson.certtype = 'X1';//开户证件类型为港澳身份证
                    visitJson.fzCertType = identType;//辅证件类型为原证件的类型
                    visitJson.fzCertNo = identNum;//辅证件号码为原证件的号码
                }
            }
        }
    }
    
    //证件类型：身份证、台胞证、临时台胞证、旅行证件  证件号码格式校验
    if(identType == '0') {
        if(reg.test(identNum) == false) {
            Ext.Msg.alert('提示', '身份证证件号码不符合规范');
            return false;
        }
    } else if(identType == '6' || identType == 'X24') {
        if(reg1.test(identNum) == false) {
            Ext.Msg.alert('提示', '台胞证证件号码不符合规范');
            return false;
        };
        if(reg2.test(twIdentNum) == false) {
            Ext.Msg.alert('提示', '台湾身份证证件号码不符合规范');
            return false;
        };
    } else if(identType == 'X3') {
        if(reg2.test(twIdentNum) == false) {
                Ext.Msg.alert('提示', '台湾身份证证件号码不符合规范');
            return false;
        }
    }
    // 证件类型1：身份证、台胞证、临时台胞证、旅行证件 证件号码格式校验
    if(identType1 == '0') {
        if(reg.test(identNum1) == false) {
            Ext.Msg.alert('提示', '身份证证件号码1不符合规范');
            return false;
        }
    } else if(identType1 == '6' || identType1 == 'X24') {
        if(reg1.test(identNum1) == false) {
            Ext.Msg.alert('提示', '台胞证证件号码1不符合规范');
            return false;
        };
        if(reg2.test(twIdentNum1) == false) {
            Ext.Msg.alert('提示', '台湾身份证证件号码1不符合规范');
            return false;
        };
    } else if(identType1 == 'X3') {
        if(reg2.test(twIdentNum1) == false) {
            Ext.Msg.alert('提示', '台湾身份证证件号码1不符合规范');
            return false;
        }
    }
    // 证件类型2：身份证、台胞证、临时台胞证、旅行证件 证件号码格式校验
    if(identType2 == '0') {
        if(reg.test(identNum2) == false) {
            Ext.Msg.alert('提示', '身份证证件号码2不符合规范');
            return false;
        }
    } else if(identType2 == '6' || identType2 == 'X24') {
        if(reg1.test(identNum2) == false) {
            Ext.Msg.alert('提示', '台胞证证件号码2不符合规范');
            return false;
        }
        if(reg2.test(twIdentNum2) == false) {
            Ext.Msg.alert('提示', '台湾身份证证件号码2不符合规范');
            return false;
        }
    } else if(identType2 == 'X3') {
        if(reg2.test(twIdentNum2) == false) {
            Ext.Msg.alert('提示', '台湾身份证证件号码2不符合规范');
            return false;
        }
    }
    // 身份证号码校验
    if(identType == '0') {
        var bb = CheckLicense(identNum);
        if(bb == false) {
            Ext.Msg.alert('提示', '身份证证件号码不符合规范');
            return false;
        }
    }
    if(identType1 == '0') {
        var bb = CheckLicense(identNum1);
        if(bb == false) {
            Ext.Msg.alert('提示', '身份证证件号码1不符合规范');
            return false;
        }
    }
    if(identType2 == '0') {
        var bb = CheckLicense(identNum2);
        if(bb == false) {
            Ext.Msg.alert('提示', '身份证证件号码2不符合规范');
            return false;
        }
    }
    //其他证件证件号码校验
    if(identType != '0' &&  identType != '6' && identType != 'X3' && identType != 'X24' && identType != ''&&identType!=null &&  identType != '3' ){
        if(reg3.test(identNum) == false){
            Ext.Msg.alert('提示','证件号码不符合规范'); 
            return false;
        }
    }
    if(identType1 != '0'  && identType1 != '6' && identType1 != 'X3' && identType1 != 'X24' && identType1 != ''&&identType1 !=null){
        if(reg3.test(identNum1) == false){
            Ext.Msg.alert('提示','证件号码1不符合规范'); 
            return false;
        }
    }
    if(identType2 != '0'  && identType2 != '6' && identType2 != 'X3' && identType2 != 'X24' && identType2 != '' &&identType2 !=null){
        if(reg3.test(identNum2) == false){
            Ext.Msg.alert('提示','证件号码2不符合规范'); 
            return false;
        }
    }

    //第一步：调用预约系统查询该用户是否已经有预约
    //根据预约系统查询结果判断是否是预约开户
    //第二步：调用联网核查和电信黑名单核查
    Ext.getCmp("linguiBtn").disable();
    
    //联网核查--核心--ECIF--预约
    var checkParams = {
        'logId' : serializeId,
        'jointaccount':visitJson.jointaccount,
        'custNm' : visitJson.customername,//客户姓名
        'certtype' :visitJson.certtype,//主户证件类型
        'certid' :  visitJson.certid,//主户证件号码
        'certtype2' :visitJson.certtype2,//从户证件类型
        'certid2' :  visitJson.certid2//从户证件号码
        //'jointaccount' : visitJson.jointaccount //是否联名户的标志
    };
//    NetCheckAndBlackOrderCheck(checkParams);
    if(ifNetCheckAndBlackOrderCheck){
    	NetCheckAndBlackOrderCheck(checkParams);
    }else{
    	openCustInfoPanel();//屏蔽联网核查
    }
}


/**
 * [identifyByChip 芯片识别身份证]
 * @return {[type]} [description]
 */
function identifyByChip(){
    var frameHtml = '<iframe id="chipIdentFrame" name="chipIdentFrame" width="500px" height="400px" style="display:block" src="/crmweb/contents/pages/wlj/custmanager/account/identityScan/chipCertificate.html"></iframe>';
    if(loginData.chipIdentFrame){
        loginData.chipIdentFrame.remove();
    }
    loginData.chipIdentFrame = $(frameHtml);
    $('body').append(loginData.chipIdentFrame);
    loginData.chipIdentFrame.show();
}

/**
 * 打开高拍仪界面cameraScan.html进行证件扫描
 * 扫描结束后会进行信息识别，最终调用本页面的函数getIdentInfoByCamera处理高拍仪识别结果
 * @return {[type]} [description]
 */
function identifyByCamera(){
    var frameHtml = '<iframe id="cameraIdentFrame" name="cameraIdentFrame" width="0px" height="0px" style="display:none" src="/crmweb/contents/pages/wlj/custmanager/account/identityScan/cameraScan.html"></iframe>';
    if(loginData.cameraIdentFrame){
        loginData.cameraIdentFrame.remove();
    }
    loginData.cameraIdentFrame = $(frameHtml);
    $('body').append(loginData.cameraIdentFrame);
    loginData.cameraIdentFrame.show();
}

/**
 * [getIdentInfoByChip 根据芯片识别获取证件信息]
 * @param  {[type]} identInfo [description]
 * @return {[type]}           [description]
 */
function getIdentInfoByChip(identInfo){
    if(identInfo){
        if(typeof identInfo == "object"){
            Ext.Msg.alert(JSON.stringify(identInfo));
        }else{
            Ext.Msg.alert(identInfo);
        }
    }
    if(loginData.chipIdentFrame){
        loginData.chipIdentFrame.remove();
        loginData.chipIdentFrame = null;
    }
}


/**
 * 高拍仪证件类型转换
 * @param  {[type]} origIdentityType [description]
 * @return {[type]}                  [description]
 */
function transIdentityTypeOCR(origIdentityType){
    var checkedType = '99';
    if(origIdentityType){
        if(origIdentityType == '2'||origIdentityType == '3'){//境内居民身份证
            checkedType = '0';
        }else if(origIdentityType == '16'){//户口簿
            checkedType = '1';
        }else if(origIdentityType == '13'){//境外公民护照，经业务(朱萍)确认，去掉需求里的境内居民护照
            checkedType = '2';
        }else if(origIdentityType == '7'){//军官证
            checkedType = '3';
        }else if(origIdentityType == '14'||origIdentityType == '15'||origIdentityType == '22'){//港澳居民来往内地通行证
            checkedType = '5';
        }else if(origIdentityType == '1001'||origIdentityType == '1005'){//港澳居民身份证
            checkedType = 'X1';
        }else if(origIdentityType == '10'||origIdentityType == '25'||origIdentityType == '26' ){//台湾同胞来往内地通行证
            checkedType = '6';
        }else if(origIdentityType == '1031'||origIdentityType == '1032'){//台湾居民身份证
            checkedType = 'X2';
        }else if(origIdentityType == '4'){//临时身份证
            checkedType = '7';
        }else if(origIdentityType == '1003'||origIdentityType == '1004'){//边民出入境通行证
            checkedType = 'X5';
        }/*else if(origIdentityType == ''){//临时台胞证，经业务(朱萍)确认，去掉该需求，业务不允许该证件开户
            checkedType = 'X24';
        }else if(origIdentityType == ''){//旅行证，高拍仪不支持，经业务(朱萍)确认，去掉该需求
            checkedType = 'X3';
        }else if(origIdentityType == '1000'){//外国人居留证,高拍仪暂不支持
            checkedType = '8';
        }else if(origIdentityType == '8'){//武警身份证件，军人的身份证件分为 7 军官证和 8 士兵证，其中8士兵证暂不支持
            checkedType = 'X14';
        }*/
    }
    return checkedType;
}


/**
 * [getIdentInfoByCamera 根据摄像头扫描获取证件信息]
 * 单幅图像事件格式为：图像路径|识别结果
 * 多幅图像事件格式为：图像路径1;图像路径2|识别结果1;识别结果2
 * @return {[type]} [description]
 */
function getIdentInfoByCamera(identInfoResult){
    var identInfo = identInfoResult.split("|")[1];
    if(identInfo){
        var identInfosList = identInfo.split(";");
        for(var j = 0; j < identInfosList.length; j++){
            var identInfos = Ext.decode(identInfosList[j]);
            var cardStatus = identInfos["data"]["message"]["status"];
            var cardItems = identInfos["data"]["cardsinfo"]["card"]["item"];
            var cardType = identInfos["data"]["cardsinfo"]["card"]["type"];
            //调试使用
        	for(var i = 0; i < cardItems.length; i++){
        		var cardDesc = cardItems[i]["desc"];
            	var cardContent = cardItems[i]["content"];
        		//console.info("选项："+cardDesc +",选项的值："+cardContent+" , 卡片类型："+cardType);
        	}
            var transIdent = transIdentityTypeOCR(cardType);//将高拍仪的证件类型转换成CRM对应的证件类型
            if(transIdent == '0'){//身份证
                scanData.certtype = transIdent;//开户主证件类型
                if(cardStatus >= 0){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        if(cardDesc == '姓名'){
                            scanData.customername = cardContent;
                        }else if(cardDesc == '性别'){//男1女2
                            if(cardContent == '男'){
                                scanData.sex = '1'
                            }else if(cardContent == '女'){
                                scanData.sex = '2';
                            }
                        }else if(cardDesc == '民族'){
                            scanData.nation = cardContent;
                        }else if(cardDesc == '出生'){
                            scanData.birthday = cardContent;
                        }else if(cardDesc == '住址'){
                            scanData.address = cardContent;
                        }else if(cardDesc == '公民身份号码'){
                            scanData.certid = cardContent;//开户主证件类型
                        }else if(cardDesc == '签发机关'){
                            scanData.qianfajiguan = cardContent;
                        }else if(cardDesc == '有效期限'){//20140630-20240630
                            scanData.useDate = cardContent;
                        }else if(cardDesc == '签发日期'){//2014-06-30
                            scanData.qifaDate = cardContent;
                        }else if(cardDesc == '有效期至'){//2024-06-30
                            scanData.LEGAL_EXPIRED_DATE = cardContent;
                            scanData.expiredDate = cardContent;
                        }
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '身份证信息获取失败！');
                    return false;
                }
            }else if(transIdent == '1'){//户口簿
                scanData.certtype = transIdent;
                if(cardStatus >= 0){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        if(cardDesc == '公民身份证件编号'){
                            scanData.certid = cardContent;
                        }else if(cardDesc == '姓名'){
                            scanData.customername = cardContent;
                        }else if(cardDesc == '性别'){//男1女2
                            if(cardContent == '男'){
                                scanData.sex = '1'
                            }else if(cardContent == '女'){
                                scanData.sex = '2';
                            }
                        }else if(cardDesc == '民族'){
                            scanData.nation = cardContent;
                        }else if(cardDesc == '出生日期'){
                           	scanData.birthday = cardContent;
                        }
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '户口簿证件信息获取失败！');
                    return false;
                }
            }else if(transIdent == '2'){//境外公民护照
                scanData.certtype = transIdent;
                if(cardStatus >= 0){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        if(cardDesc == '护照号码'){
                            scanData.certid = cardContent;
                        }else if(cardDesc == '本国姓名'){
                            scanData.customername = cardContent;
                        }else if(cardDesc == '性别'){//男1女2
                            if(cardContent == '男'){
                                scanData.sex = '1'
                            }else if(cardContent == '女'){
                                scanData.sex = '2';
                            }
                        }else if(cardDesc == '有效期至'){//2024-06-30
                            scanData.expiredDate = cardContent;
                        }else if(cardDesc == '出生日期'){
                           	scanData.birthday = cardContent;
                        }else if(cardDesc == '出生地点'){
                            scanData.address = cardContent;
                        }else if(cardDesc == '英文姓名'&&cardContent!=''&cardContent!=null){
                           scanData.customPinYin = cardContent;
                        }
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '境外公民护照证件信息获取失败！');
                    return false;
                }
            }else if(transIdent == '3'){//军官证
                scanData.certtype = transIdent;
                if(cardStatus >= 0){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        if(cardDesc == '编号'){
                            scanData.certid = cardContent;
                        }else if(cardDesc == '姓名'){
                           scanData.customername = cardContent;
                        }else if(cardDesc == '性别'){//男1女2
                            if(cardContent == '男'){
                                scanData.sex = '1'
                            }else if(cardContent == '女'){
                                scanData.sex = '2';
                            }
                        }else if(cardDesc == '出生日期'){
                           	scanData.birthday = cardContent;
                        }else if(cardDesc == '民族'){
                            scanData.nation = cardContent;
                        }
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '军官证证件信息获取失败！');
                    return false;
                }
            }else if(transIdent == '5'){//港澳居民来往内地通行证
                scanData.certtype = 'X1';
                scanData.certtypefz = transIdent;
                if(cardStatus >= 0){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        if(cardDesc == '证件号码'&&cardType=='14'){//只取正面的证件吗，正面的1-8位为港澳身份证号码
//                            scanData.certid = cardContent.substring(1,8);
                            scanData.certidfz = cardContent;
                        }
                        if(cardDesc == '性别'){//男1女2
                            if(cardContent == '男'){
                                scanData.sex = '1'
                            }else if(cardContent == '女'){
                                scanData.sex = '2';
                            }
                        }else if(cardDesc == '中文姓名'){
                        	if(cardContent!=null&&cardContent!=''){
                        		scanData.customername = cardContent;
                        	}
                        }else if(cardDesc == '出生日期'){
                        	if(cardContent!=null&&cardContent!=''){
                        		scanData.birthday = cardContent;
                        	}
                        }else if(cardDesc == '本证有效期至'){
                        	if(cardContent!=null&&cardContent!=''){
                        		scanData.expiredDate = cardContent;
                        	}
                        }else if(cardDesc == '拼音姓名'&&cardContent!=''&cardContent!=null){  //卡式2013版
                           scanData.customPinYin = cardContent;
                        }else if(cardDesc == '英文姓名'&&cardContent!=''&cardContent!=null){  //1999版
                           scanData.customPinYin = cardContent;
                        }
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '港澳居民来往内地通行证证件信息获取失败！');
                    return false;
                }
            }else if(transIdent == 'X1'){//港澳居民身份证，这是两个证件（香港永久性居民身份证和澳门身份证）
                scanData.certtype = transIdent;
                if(cardStatus >= 0){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        if(cardDesc == '身份证证件号'){
                            scanData.certid = cardContent;
                        }/*else if(cardDesc == '有效期至'){
                           scanData.gaExpiredDate = cardContent;
                        }else if(cardDesc == '拼音姓名'&&cardContent!=''&cardContent!=null){
                           scanData.customPinYin = cardContent;
                        }*/
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '港澳居民身份证证件信息获取失败！');
                    return false;
                }
            }else if(transIdent == '6'){//台湾同胞来往内地通行证
                scanData.certtype = 'X2';
                scanData.certtypefz = transIdent;
                if(cardStatus >= 0){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        //console.info("选项："+cardDesc+" , 选项的值："+cardContent +" , 卡片类型：" + cardType + " , 证件号码是否为空："+(!scanData.certidfz))
                        if(cardDesc == '证件号码'){//新版的正反面都有（反面26的不处理），因此只设置一次，旧版的不影响
                            if(cardType!='26'){
	                            scanData.certidfz = cardContent;
                            }
                        }else if(cardDesc == '身份证号码'){//旧版
                            scanData.certid = cardContent;
                        }else if(cardDesc == '身份证件号码'){//新版
                            scanData.certid = cardContent;
                        }else if(cardDesc == '性别'){//男1女2
                            if(cardContent == '男'){
                                scanData.sex = '1'
                            }else if(cardContent == '女'){
                                scanData.sex = '2';
                            }
                        }else if(cardDesc == '中文姓名'){//新版
                           scanData.customername = cardContent;
                        }else if(cardDesc == '姓名'){//旧版
                           scanData.customername = cardContent;
                        }else if(cardDesc == '出生日期'){
                           	scanData.birthday = cardContent;
                        	if(scanData.birthday){//将.转换为-
                        		scanData.birthday = scanData.birthday.replace(/\./g,"-");
                        	}
                        }else if(cardDesc == '本证有效期至'){
                           scanData.expiredDate = cardContent;
                        }else if(cardDesc == '英文姓名'&&cardContent!=''&cardContent!=null){
                           scanData.customPinYin = cardContent;
                        }
                        
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '台湾同胞来往内地通行证证件信息获取失败！');
                    return false;
                }
            }else if(transIdent == 'X2'){//台湾居民身份证 
                scanData.certtype = transIdent;
                if(cardStatus >= 0){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        if(cardDesc == '统一编号'){
                            scanData.certid = cardContent;
//                            scanData.certidfz = cardContent;
                        }/*else if(cardDesc == '性别'){//男1女2
                            if(cardContent == '男'){
                                scanData.sex = '1'
                            }else if(cardContent == '女'){
                                scanData.sex = '2';
                            }
                        }else if(cardDesc == '姓名'){
                           scanData.customername = cardContent;
                        }else if(cardDesc == '住址'){
                            scanData.address = cardContent;
                        }*/
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '台湾居民身份证证件信息获取失败！');
                    return false;
                }
            }else if(transIdent == '7'){//临时身份证
                scanData.certtype = transIdent;
                if(cardStatus >= 0){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        if(cardDesc == '公民身份号码'){
                            scanData.certid = cardContent;
                        }else if(cardDesc == '性别'){//男1女2
                            if(cardContent == '男'){
                                scanData.sex = '1'
                            }else if(cardContent == '女'){
                                scanData.sex = '2';
                            }
                        }else if(cardDesc == '姓名'){
                           scanData.customername = cardContent;
                        }else if(cardDesc == '出生'){
                           	scanData.birthday = cardContent;
                        }else if(cardDesc == '民族'){
                            scanData.nation = cardContent;
                        }else if(cardDesc == '住址'){
                            scanData.address = cardContent;
                        }else if(cardDesc == '签发机关'){
                            scanData.qianfajiguan = cardContent;
                        }else if(cardDesc == '有效期限'){//20140630-20240630
                            scanData.useDate = cardContent;
                        }else if(cardDesc == '签发日期'){//2014-06-30
                            scanData.qifaDate = cardContent;
                        }else if(cardDesc == '有效期至'){//2024-06-30
                            scanData.LEGAL_EXPIRED_DATE = cardContent;
                            scanData.expiredDate = cardContent;
                        }
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '临时身份证证件信息获取失败！');
                    return false;
                }
            }else if(transIdent == 'X5'){//边民出入境通行证
                scanData.certtype = transIdent;
                if(cardStatus >= 0){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        if(cardDesc == '证件号码'){
                            scanData.certid = cardContent;
                        }else if(cardDesc == '性别'){//男1女2
                            if(cardContent == '男'||cardContent == 'M'){
                                scanData.sex = '1'
                            }else if(cardContent == '女'||cardContent == 'F'){
                                scanData.sex = '2';
                            }
                        }else if(cardDesc == '姓名'){
                           scanData.customername = cardContent;
                        }
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '边民出入境通行证件信息获取失败！');
                    return false;
                }
            }/*else if(transIdent == 'X24'){//临时台胞证
                //scanData.certtype = transIdent;
                if(cardStatus == '2'){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        if(cardDesc == '临时台胞证证件号'){
                            //scanData.certid = cardContent;
                        }
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '临时台胞证证件信息获取失败！');
                    return false;
                }
            }else if(transIdent == 'X3'){//旅行证
                //scanData.certtype = transIdent;
                if(cardStatus == '2'){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        if(cardDesc == '旅行证证件号'){
                            //scanData.certid = cardContent;
                        }
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '旅行证证件信息获取失败！');
                    return false;
                }
            }else if(transIdent == '8'){//外国人居留证 
                scanData.certtype = transIdent;
                if(cardStatus >= 0){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        if(cardDesc == '外国人居留证 证件号'){
                            scanData.certid = cardContent;
                        }
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '外国人居留证证件信息获取失败！');
                    return false;
                }
            }else if(transIdent == 'X14'){//武警身份证件
                scanData.certtype = transIdent;
                if(cardStatus >= 0){//正常识别
                    for(var i = 0; i < cardItems.length; i++){
                        var cardDesc = cardItems[i]["desc"];
                        var cardContent = cardItems[i]["content"];
                        if(cardDesc == '武警身份证件 证件号'){
                            scanData.certid = cardContent;
                        }
                    }
                }else{
                    Ext.Msg.alert('证件扫描结果', '武警身份证件证件信息获取失败！');
                    return false;
                }
            }*/
        }
        if(transIdent == '99'){
            Ext.Msg.alert('提示', '暂不支持此证件类型');
            return;
        }
        var myMask;
        $.ajax({//向ECIF发送交易请求，查询客户预约信息，交易代码：queryCustOrderInfo
            url : basepath + '/oneKeyAccountAction!checkCustOrderInfo.json?tm='+Date.now(),
            type : "GET",
            timeout : 180000,
            dataType : "json",
            data : {
                'serializeId' : serializeId,
                'flag' : '1',//查询方式   1：根据姓名证件号证件类型查询 2：根据预约号和证件号查询
                'orderNo' : '',
                'custName' : scanData.customername,
                'certtype' : scanData.certtype,//开户主证件类型
                'certid' : scanData.certid//开户主证件号
            },
            beforeSend : function(){
                myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在查询客户预约信息，请稍等..."});
                myMask.show();
            },
            success : function(result) {
                if(result.JSON.status == 'success'){
                    var appStatus = result.JSON.custInfo.appStatus;//预约状态 0-取消预约 1-预约成功
                    if(appStatus == '1'){//预约客户
                         //预约信息返显
                         reviewShow(result);
                         //预约号
                         visitJson.sOrderSerialNo = result.JSON.custInfo.sorderserialno;
                         Ext.getCmp('reviewNumLogin').setValue(visitJson.sOrderSerialNo);
                         //证件号码
                         visitJson.certid = result.JSON.custInfo.certid;
                         Ext.getCmp('reviewIdentNumLogin').setValue(visitJson.certid);
                    }else{//非预约客户
                       Ext.getCmp('custName').setValue(scanData.customername);
                       
                       if(scanData.certtype == 'X1'){//港澳居民身份证
                            Ext.getCmp('identType').setValue(scanData.certtypefz);
                            Ext.getCmp('identNum').setValue(scanData.certidfz);
                            Ext.getCmp('gaIdentNum').setVisible(true);
                            Ext.getCmp('gaIdentNum').setValue(scanData.certid);
                       }else if(scanData.certtype == 'X2'){//台湾居民身份证
                            Ext.getCmp('identType').setValue(scanData.certtypefz);
                            Ext.getCmp('identNum').setValue(scanData.certidfz);
                            Ext.getCmp('twIdentNum').setVisible(true);
                            Ext.getCmp('twIdentNum').setValue(scanData.certid);
                       }else{
                            Ext.getCmp('identType').setValue(scanData.certtype);
                            Ext.getCmp('identNum').setValue(scanData.certid);
                       }
                    }
                }else{
                       Ext.getCmp('custName').setValue(scanData.customername);
                       if(scanData.certtype == 'X1'){//港澳居民身份证
                            Ext.getCmp('identType').setValue(scanData.certtypefz);
                            Ext.getCmp('identNum').setValue(scanData.certidfz);
                            Ext.getCmp('gaIdentNum').setVisible(true);
                            Ext.getCmp('gaIdentNum').setValue(scanData.certid);
                       }else if(scanData.certtype == 'X2'){//台湾居民身份证
                            Ext.getCmp('identType').setValue(scanData.certtypefz);
                            Ext.getCmp('identNum').setValue(scanData.certidfz);
                            Ext.getCmp('twIdentNum').setVisible(true);
                            Ext.getCmp('twIdentNum').setValue(scanData.certid);
                       }else{
                            Ext.getCmp('identType').setValue(scanData.certtype);
                            Ext.getCmp('identNum').setValue(scanData.certid);
                       }
                }
            },
            complete : function(request, status){
                if(myMask){//隐藏等待遮罩
                    myMask.hide();
                }
                if(status && status == 'timeout'){//超时
                    Ext.Msg.alert('预约信息查询', '核查超时，请联系管理员');
                    Ext.getCmp("linguiBtn").enable();
                    return;     
                }
            }
        });
    }
    if(loginData.cameraIdentFrame){
        loginData.cameraIdentFrame.remove();
        loginData.cameraIdentFrame = null;
    }
}


/**
 * [CheckAccountLimitFromCB 请求核心查询是否允许开户]
 * @param {[type]} custNm    [description]
 * @param {[type]} identType [description]
 * @param {[type]} identNo   [description]
 * @param {[type]} twIdentNo   [description]
 */
function CheckAccountLimitFromCB(checkParams){
    var myMask;
    $.ajax({
        url : basepath + '/checkHXOpenAccountAction!checkHXAccount.json?tm='+Date.now(),
        type : "GET",
        timeout : 300000,
        dataType : "json",
        data : checkParams,
        beforeSend : function(){
            myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在请求核心查询是否允许开户，请稍等..."});
            myMask.show();
        },
        success : function(result){
            if(result){
                if(myMask){
                    myMask.hide();
                }
                if(result.success){
                    var certNo3 = checkParams.certid;
                    //如果返回了联名户证件号
                    if(result.certNo3){
                        certNo3 = result.certNo3;
                    }
                    var checkParams2 = {
                        'logId' : serializeId,
                        'custNm' : checkParams.custNm,//客户姓名
                        'certtype' : checkParams.certtype,//客户证件类型
                        'certid' :  certNo3,//客户证件号码
                        'certtype2' :checkParams.certtype2,//从户证件类型
                        'certid2' :  checkParams.certid2,//从户证件号码
                        'jointaccount' : checkParams.jointaccount, //是否联名户的标志
                        //'certid3' : result.certNo3,//带*号的主户证件
                        'ecifIsOpen': result.ecifIsOpen//联名户是否已经ECIF开户
                    };
                    // createUserInfoWindow(checkParams);
                    CheckCustomerInfo(checkParams2);
                }else{
                    if(result.msg){
                        Ext.MessageBox.alert('检查核心是否允许开户',result.msg);
                        Ext.getCmp("linguiBtn").enable();
                        Ext.getCmp("reviewBtn").enable();
                        return;
                    }
                }
            }
            
            
        },
        complete : function(request, status){
            if(myMask){
                myMask.hide();
            }
        }
    });
}


/**
 * [CheckCustomerInfo 查询客户信息]
 * @param {[type]} checkParams [description]
 */
function CheckCustomerInfo(checkParams){
    var myMask;
    $.ajax({
        url : basepath + '/oneKeyAccountAction!validateCustomerInfo.json?tm='+Date.now(),
        type : "GET",
        timeout : 180000,
        dataType : "json",
        data : checkParams,
        beforeSend : function(){
            myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在检查客户信息，请稍等..."});
            myMask.show();
        },
        success : function(result) {
            if(result.status == 'success'){
                var ECIFData =  result.ECIFData;
                //根据证件类型和证件号码没有查询到客户信息，则认为ECIF没有开户
                if(result.ECIFData == null){//新户
                    visitJson.ecifIsOpen = false;
                    //openCustInfoPanel();
                }else{//已经EICF开户,反显客户信息
                    visitJson.customer = ECIFData.customer;//客户信息
                    visitJson.personInfo =  ECIFData.personInfo;//个人信息
                    visitJson.identInfoList = ECIFData.identInfoList;//证件信息
                    visitJson.contMethInfoList = ECIFData.contMethInfoList;//联系方式信息
                    visitJson.taxMainInfo =  ECIFData.taxMainInfo;//个人声明信息
                    visitJson.taxSubInfoList = ECIFData.taxSubInfoList;//纳税信息
                    visitJson.openInfo = ECIFData.openInfo;//卡账信息
                    visitJson.belongManagerInfo = ECIFData.belongManagerInfo;//归属客户经理信息
                    visitJson.addrInfoList = ECIFData.addrInfoList;//地址信息
                    visitJson.accountInfoList = ECIFData.accountInfoList;//银行服务信息
                    visitJson.contactorInfo = ECIFData.contactorInfo;//关联人信息
                    visitJson.lianmingInfo = ECIFData.lianmingInfo;//联名户信息
                    visitJson.familyInfo = ECIFData.familyInfo;//个人家庭信息
                    
                    //判断是否是联名户
                    if(visitJson.jointaccount == 1){
                        if(checkParams.ecifIsOpen){
                            visitJson.ecifIsOpen = checkParams.ecifIsOpen;//核心ecif是否已经开户
                        }else{
                            visitJson.ecifIsOpen = false;//核心ecif是否已经开户
                        }
                    }else{
                        visitJson.ecifIsOpen = true;//核心ecif是否已经开户
                    }
                    visitJson.custId1 = ECIFData.custId1;//主户客户号
                    visitJson.customer2 = ECIFData.customer2;//从户信息
                    visitJson.personInfo2 = ECIFData.personInfo2;//从户个人信息
                    visitJson.identInfoList2 = ECIFData.identInfoList2;//从户证件信息
                    visitJson.taxMainInfo2 =  ECIFData.taxMainInfo2;//从户个人声明信息
                    visitJson.taxSubInfoList2 = ECIFData.taxSubInfoList2;//从户纳税信息
                    visitJson.perkeyflagInfo = ECIFData.perkeyflagInfo;//在我行无关联人信息
                    //openCustInfoPanel();
                }
                if(visitJson.fromReview == true){
                    openCustInfoPanel();
                }else{
                    if(visitJson.ecifIsOpen == false){
                        //查询是否已经预约
                        checkCustOrderInfo();
                    }else{
                        openCustInfoPanel();
                    }
                }
            }else{
                if(result.msg){
                    Ext.Msg.alert('检查客户信息', result.msg);
                    Ext.getCmp("linguiBtn").enable();
                    Ext.getCmp("reviewBtn").enable();
                }
                return;
            }
        },
        complete : function(request, status){
            if(myMask){
                myMask.hide();
            }
            if(status && status == 'timeout'){//超时
                Ext.Msg.alert('检查客户信息', '核查超时，请联系管理员');
                Ext.getCmp("linguiBtn").enable();
                Ext.getCmp("reviewBtn").enable();
                return;     
            }
        }
    });
}


//联网核查及黑名单校验
/**
 * [NetCheckAndBlackOrderCheck 联网核查及黑名单校验]
 * @param {[type]} checkParams [description]
 */
function NetCheckAndBlackOrderCheck(checkParams){
    var myMask;
    //Ext.Msg.wait("正在进行联网及电信黑名单核查，请稍等...","系统消息");
    $.ajax({
        url : basepath + '/oneKeyAccountAction!netCheckAndBlackOrderCheck.json?tm='+Date.now(),
        type : "GET",
        timeout : 300000,
        dataType : "json",
        data : checkParams,
        beforeSend : function(){
            myMask = new Ext.LoadMask(Ext.getBody(), {msg:"正在进行联网及电信黑名单核查，请稍等..."});
            myMask.show();
        },
        success : function(result) {
        	/*if(myMask){
                myMask.hide();
            }*/
        	var msg1 = '';
        	var msg2 = '';
        	var msg3 = '';
        	var endMsg = "";

        	//如果是联名户，提示从户信息
        	if(checkParams.jointaccount == '1'||checkParams.jointaccount == 'Y'){
        		if(result.status1 == 'success' && result.status2 == 'success' && result.status3 == 'success'
        			&&result.status1_2 == 'success' && result.status2_2 == 'success' && result.status3_2 == 'success'){
        			//两个校验都通过就不显示信息
        			CheckAccountLimitFromCB(checkParams);
        			return;
        		}
        		if(result.status1 != 'success'){
        			endMsg += '联网核查：主户'+result.msg1+'<br/>是否继续开户？';
        		}
        		if(result.status2 != 'success'){
        			endMsg += '电信黑名单：主户'+result.msg2+'<br/>是否继续开户？';
        		}
        		if(result.status3 != 'success'){
        			endMsg += '本地黑名单校验：主户'+result.msg3+'<br/>是否继续开户？';
        		}
        		if(result.status1_2 != 'success'){
        			endMsg += '联网核查：从户'+result.msg1_2+'<br/>是否继续开户？';
        		}
        		if(result.status2_2 != 'success'){
        			endMsg += '电信黑名单：从户'+result.msg2_2+'<br/>是否继续开户？';
        		}
        		if(result.status3_2 != 'success'){
        			endMsg += '本地黑名单校验：从户'+result.msg3_2+'<br/>是否继续开户？';
        		}
        	}else{
        		if(result.status1 == 'success' && result.status2 == 'success' && result.status3 == 'success'){//s_status1
        			//两个校验都通过就不显示信息
        			CheckAccountLimitFromCB(checkParams);
        			return;
        		}
        		if(result.status1 != 'success'){
        			endMsg += '联网核查：'+result.msg1+'<br/>是否继续开户？';
        		}
        		if(result.status2 != 'success'){
        			endMsg += '电信黑名单：'+result.msg2+'<br/>是否继续开户？';
        		}
        		if(result.status3 != 'success'){
        			endMsg += '本地黑名单校验：'+result.msg3+'<br/>是否继续开户？';
        		}
        	}

        	if(endMsg){
        		Ext.Msg.confirm('核查结果',endMsg,function(btn){
        			if(btn == 'yes'){
        				//核心检验是否可以开户
        				CheckAccountLimitFromCB(checkParams);
        				//不进行核心检验是否可以开户
        				//CheckCustomerInfo(checkParams);
        			}else{
        				Ext.getCmp("linguiBtn").enable();
        				Ext.getCmp("reviewBtn").enable();
        				return;
        			}
        		},this);
        	}
        },
        complete : function(request, status){
            if(myMask){
                myMask.hide();
                myMask.destroy();
                myMask = null;
            }
            if(status && status == 'timeout'){//超时
                Ext.Msg.alert('提示', '请求超时，联网核查、黑名单校验失败');
                Ext.getCmp("linguiBtn").enable();
                Ext.getCmp("reviewBtn").enable();
                return;     
            }
        }
    });
}

/**
 * [CheckLicense 检验身份证]
 * @param {[type]} identNum [description]
 */
function CheckLicense(identNum){   
    var checkedValue = identNum;        
    //checkedValue = checkedValue.trim();
    if (checkedValue.length != 15 && checkedValue.length != 18){
        Ext.Msg.alert('提示','身份证证件号码不符合规范'); 
        return false;
    }
    var dateValue;
    if (checkedValue.length == 15){
        dateValue = "19" + checkedValue.substring(6, 12);
    }       
    else{
        dateValue = checkedValue.substring(6, 14);
    }
    if (!checkDate(dateValue)){
        Ext.Msg.alert('提示','身份证证件号码不符合规范'); 
        return false;
    }
    if (checkedValue.length == 18)  {   
        var aa= checkPersonId(checkedValue);
        if(aa == false){
            Ext.Msg.alert('提示','身份证证件号码不符合规范'); 
            return false;
        }
    }   
}
/**
 * 身份证证件号码校验
 * @param {} personId 身份证号码
 * @return {}
 */
function checkPersonId(personId){
    var strJiaoYan =["1","0","X","9","8","7","6","5","4","3","2"];
    var intQuan = [7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2,1];
    var intTemp = 0;
    for (var i = 0; i < personId.length - 1; i++){
            intTemp += personId.substring(i, i + 1) * intQuan[i];
    }
    intTemp %= 11;
    //System.out.println(personId.substring(personId.length - 1)+"**********"+strJiaoYan[intTemp]);
    return personId.substring(personId.length - 1)== strJiaoYan[intTemp];
}   
/**
 * 身份证日期校验
 * @param {} sDate
 * @return {Boolean}
 */
function checkDate(sDate){
        var checkedDate = sDate;
        var year="",month="",day="";    
        //日期为空 长度不等于8或14 返回错误
        var maxDay =[0,31,29,31,30,31,30,31,31,30,31,30,31];
        checkedDate = checkedDate.trim();
        if (checkedDate.length != 8 && checkedDate.length != 14) {
            Ext.Msg.alert('提示','身份证证件号码不符合规范'); 
            return false;
        }
        year = checkedDate.substring(0, 4).trim();
        month = checkedDate.substring(4, 6).trim();
        day = checkedDate.substring(6, 8).trim();
        
        // 日期中1至4位 年小于1900 返回错误
        if(year!=null && ""!= year){
            if (year < 1900) {
                Ext.Msg.alert('提示','身份证证件号码不符合规范'); 
                return false;
            }
            
            // 日期中5至6位 月在1至12区间之外 返回错误
            if(month!=null && ""!=month){
                if (month < 1 || month > 12) {
                    Ext.Msg.alert('提示','身份证证件号码不符合规范'); 
                    return false;
                }
                
                // 日期中7至8位 日在1至maxDay[month]区间之外 返回错误
                if(day!=null && ""!=day){
                    if (day > maxDay[month] || day == 0) {
                        Ext.Msg.alert('提示','身份证证件号码不符合规范'); 
                        return false;
                    }
                    // 非闰年2月份日期大于29
                    if (day == 29 && month == 2 && 
                            (year % 4 != 0 || year % 100 == 0) && 
                            (year % 4 != 0 || year % 400 != 0)){
                        Ext.Msg.alert('提示','身份证证件号码不符合规范'); 
                        return false;
                    }
                }
            }
        }

        if (checkedDate.length == 14) {
            // 日期长度为14位
            var hour = checkedDate.substring(8, 10);
            var miniute = checkedDate.substring(10, 12);
            var second = checkedDate.substring(12, 14);
            
            // 日期中9至10位 小时在0至23区间之外 返回错误
            if(hour!=null && ""!=hour){
                if (hour > 23 || hour < 0) {
                    Ext.Msg.alert('提示','身份证证件号码不符合规范'); 
                    return false;
                }
            }
            
            // 日期中11至12位 分钟在0至59区间之外 返回错误
            if(miniute!=null &&""!=miniute){
                if (miniute > 59 || miniute < 0) {
                    Ext.Msg.alert('提示','身份证证件号码不符合规范'); 
                    return false;
                }
            }
            
            // 日期中13至14位 秒在0至59区间之外 返回错误
            if(second!=null && ""!=second){
                if (second > 59 || second < 0) {
                    Ext.Msg.alert('提示','身份证证件号码不符合规范'); 
                    return false;
                }
            }
            
        }
        return true;
    }

/**
 * 证件类型转换
 * @param  {[type]} origIdentityType [description]
 * @return {[type]}                  [description]
 */
function transIdentityType(origIdentityType){
    var checkedType = '99';
    if(!origIdentityType){
        return '99';
    }else{
        if(origIdentityType == '0'){//境内居民身份证
            checkedType = '01';
        }else if(origIdentityType == '1'){//户口簿
            checkedType = '06';
        }else if(origIdentityType == '2'){//境外公民护照
            checkedType = '03';
        }else if(origIdentityType == '3'){//军官证
            checkedType = '04';
        }else if(origIdentityType == '5'){//港澳居民来往内地通行证
            checkedType = '07';
        }else if(origIdentityType == '6' || origIdentityType == 'X24'){//台湾同胞来往内地通行证||临时台胞证
            checkedType = '10';
        }else if(origIdentityType == '7'){//临时身份证
            checkedType = '11';
        }else if(origIdentityType == '8'){//外国人居留证 X14
            checkedType = '12';
        }else if(origIdentityType == 'X14'){//武警身份证件
            checkedType = '08';
        }else{
            return "99";
        }
        return checkedType;
    }
}
/**
 * 打开开户详细信息页面
 */
function openCustInfoPanel(){
    initPage();//该函数在accountSecond.js里
}

var ifNetCheckAndBlackOrderCheck = false;

/**
 * 个金一键开户进入页面表单面板
 */
var cust_form = new Ext.FormPanel({
    title       : '个金一键开户',
    collapsible : true,
    autoHeight  : true,
    autoWidth   : true,
    labelWidth  : 200,// label的宽度
    labelAlign  : 'left',
    frame       : false,
    autoScroll  : true,
    buttonAlign : 'center',
    anchor      : '95%',
    items : [{
        layout : 'column',
        items:[{
            title : '<font style="font-size:16px;">证件扫描</font>',
            columnWidth:1,
            xtype : 'fieldset',
            style:'margin:0 10px;padding-bottom:15px;',
            items:[{
                 xtype : 'button',
                 width : '250',
                 height:'30',
                 text : '开始扫描',
                 style:'margin-left:'+($(window).width()/2 - 175)+'px;',
                 handler: function(){
                    identifyByCamera();
                 }
            }]
        },{
            title       : '<font style="font-size:16px;">客户开户预约进入</font>',
            columnWidth : 1,
            xtype       : 'fieldset',
            style       : 'margin:0 10px;padding:10px 0;',
            layout      : 'fit',
            items:[{
                 layout : 'column',
                 items:[{
                    columnWidth : 1,
                    layout      : 'form',
                    items:[{
                        id          : 'reviewNumLogin',
                        xtype       : 'textfield',
                        width       : '165',
                        maxLength   : '30',
                        regex       : /^[^\u4E00-\u9FA5]{0,}$/,
                        fieldLabel  : convertFontCss('客户预约号'),
                        labelStyle  : 'margin-left:' + ($(window).width() / 2 - 350) + 'px;'
                    }]
                }]
            },{
                 layout : 'column',
                 items:[{
                    columnWidth:1,
                    layout : 'form',
                    items:[{
                         id : 'reviewIdentNumLogin',
                         xtype : 'textfield',
                         width : '165',
                         maxLength : '30',
                         regex:/^[^\u4E00-\u9FA5]{0,}$/,
                         fieldLabel : convertFontCss('客户证件号'),
                         labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;'
                    }]
                }]
            },{
                 layout : 'column',
                 items:[{
                    columnWidth:1,
                    layout : 'form',
                    items:[{
                        xtype       : 'button',
                        width       : '125',
                        text        : '进入',
                        id          : 'reviewBtn',
                        style:'margin-left:'+($(window).width()/2 - 120)+'px;margin-top:15px;',
                        listeners:{
                            'click': visitAccount
                        }
                    }]
                }]
            }]
        },{
            title : '<font style="font-size:16px;">客户临柜开户进入</font>',
            columnWidth:1,
            xtype : 'fieldset',
            layout : 'form',
            style:'margin:0 10px;padding:10px 0;',
            items:[{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            xtype : 'textfield',
                            id :  'custName',
                            width : '165',
                            maxLength : '128',
                            fieldLabel : convertFontCss('客户姓名'),
                            regex:/^[a-zA-Z\u4e00-\u9fa5\/\s\(\)\-\（\）]+$/,
                            labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;'
                        }]
                 }]
            },{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            id : 'identType',
                            xtype:'combo',
                            readOnly:false,
                            fieldLabel:convertFontCss('客户证件类型'),
                            name:'IDENT_TYPE',
                            mode : 'local',
                            store :store_zhjlx,
                            triggerAction : 'all',
                            displayField : 'value',
                            valueField : 'key',
                            valueNotFoundText :"",
                            labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;',
                            listeners:{
                                'select':function(a,b,c){
                                    //台湾同胞来往内地通行证||临时台胞证||旅行证件
                                    if(a.value == '6' || a.value == 'X24' || a.value == 'X3'){
                                        Ext.getCmp('twIdentNum').setVisible(true);
                                        Ext.getCmp('gaIdentNum').setVisible(false);
                                    }else if(a.value == '5'){//港澳居民来往内地通行证
                                        Ext.getCmp('gaIdentNum').setVisible(true);
                                        Ext.getCmp('twIdentNum').setVisible(false);
                                    }else{
                                        Ext.getCmp('twIdentNum').setVisible(false);
                                        Ext.getCmp('gaIdentNum').setVisible(false);
                                    };
                                },
                                'focus': {//获取焦点时自动展开并查询,即鼠标点击的时候就可以展开  
                                    fn: function(e) {  
                                        e.expand();  
                                        this.doQuery(this.allQuery, true);  
                                    },  
                                    buffer:200  
                                }
                            }
                        }]
                 }]
            },{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            id : 'identNum',
                            xtype : 'textfield',
                            width : '165',
                            fieldLabel : convertFontCss('客户证件号码'),
                            maxLength : '30',
                            //regex:/^[^\u4E00-\u9FA5]{0,}$/,
                            labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;'
                        }]
                 }]
            },{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            id : 'twIdentNum',
                            xtype : 'textfield',
                            width : '165',
                            fieldLabel : convertFontCss('台湾身份证'),
                            maxLength : '30',
                            regex:/^[^\u4E00-\u9FA5]{0,}$/,
                            labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;'
                        }]
                 }]
            },{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            id : 'gaIdentNum',
                            xtype : 'textfield',
                            width : '165',
                            fieldLabel : convertFontCss('港澳身份证'),
                            maxLength : '30',
                            regex:/^[^\u4E00-\u9FA5]{0,}$/,
                            labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;'
                        }]
                 }]
             },{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            id : 'identType1',
                            xtype:'combo',
                            readOnly:false,
                            maxLength:30,
                            fieldLabel:convertFontCss('客户证件类型1'),
                            name:'IDENT_TYPE1',
                            mode : 'local',
                            store :store_zhjlx ,
                            triggerAction : 'all',
                            displayField : 'value',
                            valueField : 'key',
                            valueNotFoundText :"",
                            labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;',
                            listeners:{
                                'select':function(a,b,c){
                                    //台湾同胞来往内地通行证||临时台胞证||旅行证件
                                    if(a.value == '6' || a.value == 'X24' || a.value == 'X3'){
                                        Ext.getCmp('twIdentNum1').setVisible(true);
                                        Ext.getCmp('gaIdentNum1').setVisible(false);
                                    }else if(a.value == '5'){//港澳居民来往内地通行证
                                        Ext.getCmp('gaIdentNum1').setVisible(true);
                                        Ext.getCmp('twIdentNum1').setVisible(false);
                                    }else{
                                        Ext.getCmp('twIdentNum1').setVisible(false);
                                        Ext.getCmp('gaIdentNum1').setVisible(false);
                                    }
                                },
                                'focus': {//获取焦点时自动展开并查询,即鼠标点击的时候就可以展开  
                                    fn: function(e) {  
                                        e.expand();  
                                        this.doQuery(this.allQuery, true);  
                                    },  
                                    buffer:200  
                                }
                            }
                        }]
                 }]
            },{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            id : 'identNum1',
                            xtype : 'textfield',
                            width : '165',
                            fieldLabel : convertFontCss('客户证件号码1'),
                            maxLength : '30',
                            regex:/^[^\u4E00-\u9FA5]{0,}$/,
                            labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;'
                        }]
                 }]
            },{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            id : 'twIdentNum1',
                            xtype : 'textfield',
                            width : '165',
                            fieldLabel : convertFontCss('台湾身份证1'),
                            maxLength : '30',
                            regex:/^[^\u4E00-\u9FA5]{0,}$/,
                            labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;'
                        }]
                 }]
            },{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            id : 'gaIdentNum1',
                            xtype : 'textfield',
                            width : '165',
                            maxLength : '30',
                            regex:/^[^\u4E00-\u9FA5]{0,}$/,
                            fieldLabel : convertFontCss('港澳身份证1'),
                            labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;'
                        }]
                 }]
            },{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            id : 'identType2',
                            xtype:'combo',
                            readOnly:false,
                            maxLength:30,
                            fieldLabel:convertFontCss('客户证件类型2'),
                            name:'identType2',
                            mode : 'local',
                            store :store_zhjlx ,
                            triggerAction : 'all',
                            displayField : 'value',
                            valueField : 'key',
                            valueNotFoundText :"",
                            labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;',
                            listeners:{
                                'select':function(a,b,c){
                                    //台湾同胞来往内地通行证||临时台胞证||旅行证件
                                    if(a.value == '6' || a.value == 'X24' || a.value == 'X3'){
                                        Ext.getCmp('twIdentNum2').setVisible(true);
                                        Ext.getCmp('gaIdentNum2').setVisible(false);
                                    }else if(a.value == '5'){//港澳居民来往内地通行证
                                        Ext.getCmp('gaIdentNum2').setVisible(true);
                                        Ext.getCmp('twIdentNum2').setVisible(false);
                                    }else {
                                        Ext.getCmp('twIdentNum2').setVisible(false);
                                        Ext.getCmp('gaIdentNum2').setVisible(false);
                                    }
                                },
                                'focus': {//获取焦点时自动展开并查询,即鼠标点击的时候就可以展开  
                                    fn: function(e) {  
                                        e.expand();  
                                        this.doQuery(this.allQuery, true);  
                                    },  
                                    buffer:200  
                                }
                            }
                        }]
                 }]
            },{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            id : 'identNum2',
                            xtype : 'textfield',
                            width : '165',
                            maxLength : '30',
                            regex:/^[^\u4E00-\u9FA5]{0,}$/,
                            fieldLabel : convertFontCss('客户证件号码2'),
                            labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;'
                        }]
                 }]
            },{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            id : 'twIdentNum2',
                            xtype : 'textfield',
                            width : '165',
                            maxLength : '30',
                            regex:/^[^\u4E00-\u9FA5]{0,}$/,
                            fieldLabel : convertFontCss('台湾身份证2'),
                            labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;'
                        }]
                 }]
            },{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            id : 'gaIdentNum2',
                            xtype : 'textfield',
                            width : '165',
                            maxLength : '30',
                            regex:/^[^\u4E00-\u9FA5]{0,}$/,
                            fieldLabel : convertFontCss('港澳身份证2'),
                            labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;'
                        }]
                 }]
            },{
                layout : 'column',
                items:[{
                       columnWidth:1,
                       layout : 'form',
                       items:[{
							xtype		: 'checkbox',
							id			: 'netCheckAndBlackOrderCheck',
							boxLabel	: convertFontCss("进行联网和黑名单核查"),
							labelStyle:'margin-left:'+($(window).width()/2 - 350)+'px;',
							listeners	: {
								'check'		: function(obj, ischeck) {
									if(ischeck == true) {
										ifNetCheckAndBlackOrderCheck = true;
									} else {
										ifNetCheckAndBlackOrderCheck = false;
									}
								}
							}
						}]
                }]
           },{
                 layout : 'column',
                 items:[{
                        columnWidth:1,
                        layout : 'form',
                        items:[{
                            xtype : 'button',
                            width : '125',
                            text : '进入',
                            id : 'linguiBtn',
                            style:'margin-left:'+($(window).width()/2 - 120)+'px;margin-top:15px;',
                            listeners : {
                                'click' : visitingCustAccount
                            }
                        }]
                 }]
            }]
        }]
    }]
});
/**
 * 个金一键开户进入页面面板
 */
var panel1 = new Ext.Panel({
    layout      : 'form',
    autoScroll  : true,
    buttonAlign : 'center',
    items       : [cust_form]
});     
/**
 * 鼠标右键菜单
 * @param {} e 事件
 * @param {} added 额外的菜单配置
 */
function onContextMenu(e,added){
    var windowMenu = Wlj.frame.functions.app.Util.contextMenus.window;
    for(var key in windowMenu){
        var omenu = {};
        omenu.text = windowMenu[key].text;
        omenu.handler = windowMenu[key].fn.createDelegate(this);
        added.push(omenu);
    }
    if(!window.contextmenu){
        window.contextmenu = new Ext.menu.Menu({
            items: added
        });
    }
    window.contextmenu.showAt(e.getPoint())
}
//viewport视图，2个js页面公用,每次都重新创建
var viewport = null;
Ext.onReady(function(){
    Ext.QuickTips.init();
    viewport = new Ext.Viewport({
        layout  : 'fit',
        items   : [panel1]
    });
    viewport.show();
    //首次进入只显示：客户姓名、客户证件类型、客户证件号码。其他全部隐藏
    Ext.getCmp('identType1').setVisible(false);
    Ext.getCmp('identType2').setVisible(false);
    Ext.getCmp('identNum1').setVisible(false);
    Ext.getCmp('identNum2').setVisible(false);
    Ext.getCmp('twIdentNum').setVisible(false);
    Ext.getCmp('twIdentNum1').setVisible(false);
    Ext.getCmp('twIdentNum2').setVisible(false);
    Ext.getCmp('gaIdentNum').setVisible(false);
    Ext.getCmp('gaIdentNum1').setVisible(false);
    Ext.getCmp('gaIdentNum2').setVisible(false);
    initLayout();
    //右键菜单
    Ext.getBody().on("contextmenu",function(e){
        e.preventDefault();
        onContextMenu(e,["-"]);
    });
});