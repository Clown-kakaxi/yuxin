<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ page import="com.yuchengtech.bcrm.custview.action.PersonalAssetsPlanningAction" %>

<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Personal Asset Planning</title>


    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jQuery/css/default.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jQuery/css/component.css"/>
    <link href="<%=request.getContextPath()%>/jQuery/css/fliptimer.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jQuery/js/modernizr.custom.js"></script>

    <style type="text/css">

        body {
            font-family: 华文楷体;
        }

        #print {
            height: 10mm;
            width: 100%;

        }

        #frame {

            margin-left: auto;
            margin-right: auto;




        }

        #pageOne {

            height: 297mm;
            width: 210mm;
            margin-left: auto;
            margin-right: auto;

        }

        #pageTwo {

            height: 297mm;
            width: 210mm;
            margin-left: auto;
            margin-right: auto;
        }

        #pageThree {

            height: 297mm;
            width: 210mm;
            margin-left: auto;
            margin-right: auto;
        }

        #pageFour {


            width: 210mm;
            margin-left: 100px;
            margin-right: auto;
            margin-top: 15mm;
            color: #000;




        }

        #pageFive {

            height: 297mm;
            width: 210mm;
            margin-left: 100px;
            margin-right: auto;


        }

        #div1 {

            height: 267mm;
            width: 180mm;
            margin-left: auto;
            margin-right: auto;
            margin-top: 15mm;
            color: #000;

        }

        #div2 {

            height: 267mm;
            width: 180mm;
            margin-left: auto;
            margin-right: auto;
            margin-top: 15mm;
            color: #000;

        }

        #div3 {

            height: 267mm;
            width: 180mm;
            margin-left: auto;
            margin-right: auto;
            margin-top: 15mm;
            color: #000;

        }

        #No1 {
            height: 30px;
            width: 210mm;
            margin-left: auto;
            margin-right: auto;
            margin-top: 15mm;
            color: #000;
        }

        #div4 {

            height: 267mm;
            width: 210mm;
            margin-left: auto;
            margin-right: auto;
            margin-top: 15mm;
            color: #000;

        }

        #div5 {

            height: 267mm;
            width: 210mm;
            margin-left: auto;
            margin-right: auto;
            margin-top: 15mm;
            color: #000;

        }

        .name {

            margin: 0 auto;
            text-align: center;
            position: absolute;
            margin-left: 150px;
            margin-top: 150px;
            margin-top: 50mm;

        }

        #p1 {

            word-spacing: 10px;
            letter-spacing: 3px;
            line-height: 30px;
            display: inline;
            float: left;

            font-size: 15px;
        }

        .p2 {

            letter-spacing: 3px;
            line-height: 1.5;
            display: inline;
            text-align: center;

        }

        #b1 {
            font-size: 20px !important;
        }

        .b2 {

            color: #8e6408;
        }

        li {

            font-size: 18px;
        }

    </style>

    <style type="text/css" media=print>
        .noprint {
            display: none
        }
    </style>
    <script type="text/javascript">
        <%
        String custId = request.getParameter("custId");
        String financeId  = request.getParameter("financeId");


        %>



        // 打印
        function PrintPage() {

            window.print();

        }


    </script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/echarts-2.2.7/build/dist/echarts.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jQuery/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jQuery/js/jquery.fliptimer.js"></script>
</head>
<body style="background-color:white">


<%


    PersonalAssetsPlanningAction papa = new PersonalAssetsPlanningAction();
    List<HashMap<String, Object>> searchCustNameAndMgrName = papa.searchCustNameAndMgrName(custId);
    String custName = null;
    String mgrName = null;
    String gender = null;
    //创建日期
    String creatDate = null;


    String genderTail = null;
    for (HashMap<String, Object> map : searchCustNameAndMgrName) {
        custName = (String) map.get("CUST_NAME");
        mgrName = (String) map.get("MGR_NAME");
        gender = (String) map.get("GENDER");
        creatDate = (String) map.get("CREATE_DATE");
        if (gender.equals("女")) {
            gender = gender + "士";
        } else if (gender.equals("2")) {
            gender = "女士";
        } else if (gender.equals("男")) {
            gender = "先生";
        } else if (gender.equals("1")) {
            gender = "先生";
        } else {
            gender = "先生/女士";
        }
    }

    Calendar cal = Calendar.getInstance();// 取当前日期。
    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");//注意格式化的表达式
    String date = format.format(cal.getTime());


    request.setAttribute("searchCustNameAndMgrName", searchCustNameAndMgrName);
    List<HashMap<String, Object>> searchAssetAndLoan = papa.searchAssetAndLoan(custId);

    //活期存款
    double HQCKYE_AMT = 0;
    //通知存款
    double TZCK_AMT = 0;
    //定期存款
    double DQCKYE_AMT = 0;
    //月得盈
    double YDY_AMT = 0;
    //汇得利
    double HDL_AMT = 0;
    //福股利
    double FGL_AMT = 0;
    //保险
    double BX_AMT = 0;
    //安富尊荣
    double AFZR_AMT = 0;
    //双元货币
    double DD_AMT = 0;
    //富汇盈
    double FHY_AMT = 0;
    //信用贷款
    double XYDK_AMT = 0;
    //个人房屋抵押消费贷款
    double GRXFDK_AMT = 0;
    //个人按揭贷款
    double GRAJDK_AMT = 0;
    //个人质押贷款
    double GRZYDK_AMT = 0;
    //个人经营性贷款
    double GRJYXDK_AMT = 0;
    //内保外贷
    double NBWD_AMT = 0;
    //资管计划
    double ZGJH_AMT=0;
    //资产总计
    double assetTotal = 0;
    //贷款总计
    double loanTotal = 0;


    for (HashMap<String, Object> map : searchAssetAndLoan) {

        HQCKYE_AMT = Double.parseDouble((String) map.get("HQCKYE_AMT"));


        TZCK_AMT = Double.parseDouble((String) map.get("TZCK_AMT"));
        DQCKYE_AMT = Double.parseDouble((String) map.get("DQCKYE_AMT"));

        YDY_AMT = Double.parseDouble((String) map.get("YDY_AMT"));
        FGL_AMT = Double.parseDouble((String) map.get("FGL_AMT"));
        HDL_AMT = Double.parseDouble((String) map.get("HDL_AMT"));

        BX_AMT = Double.parseDouble((String) map.get("BX_AMT"));
        AFZR_AMT = Double.parseDouble((String) map.get("AFZR_AMT"));
        DD_AMT = Double.parseDouble((String) map.get("DD_AMT"));
        FHY_AMT = Double.parseDouble((String) map.get("FHY_AMT"));

        XYDK_AMT = Double.parseDouble((String) map.get("XYDK_AMT"));

        GRXFDK_AMT = Double.parseDouble((String) map.get("GRXFDK_AMT"));
        GRAJDK_AMT = Double.parseDouble((String) map.get("GRAJDK_AMT"));
        GRZYDK_AMT = Double.parseDouble((String) map.get("GRZYDK_AMT"));

        GRJYXDK_AMT = Double.parseDouble((String) map.get("GRJYXDK_AMT"));
        NBWD_AMT = Double.parseDouble((String) map.get("NBWD_AMT"));
        ZGJH_AMT=Double.parseDouble((String) map.get("ZGJH_AMT"));


    }

    request.setAttribute("searchAssetAndLoan", searchAssetAndLoan);
    assetTotal = HQCKYE_AMT + TZCK_AMT + DQCKYE_AMT + YDY_AMT + HDL_AMT + FGL_AMT + BX_AMT + AFZR_AMT + DD_AMT + FHY_AMT+ZGJH_AMT;


    loanTotal = XYDK_AMT + GRXFDK_AMT + GRAJDK_AMT + GRZYDK_AMT + GRJYXDK_AMT + NBWD_AMT;


    String hqckye_rate = papa.getPercent(HQCKYE_AMT, assetTotal);


    String tzck_rate = papa.getPercent(TZCK_AMT, assetTotal);

    String dqckye_rate = papa.getPercent(DQCKYE_AMT, assetTotal);


    String ydy_rate = papa.getPercent(YDY_AMT, assetTotal);
    String hdl_rate = papa.getPercent(HDL_AMT, assetTotal);
    String fgl_rate = papa.getPercent(FGL_AMT, assetTotal);
    String bx_rate = papa.getPercent(BX_AMT, assetTotal);
    String afzr_rate = papa.getPercent(AFZR_AMT, assetTotal);
    String dd_rate = papa.getPercent(DD_AMT, assetTotal);
    String fhy_rate = papa.getPercent(FHY_AMT, assetTotal);
    
    String zgjh_rate= papa.getPercent(ZGJH_AMT, assetTotal);

    String xydk_rate = papa.getPercent(XYDK_AMT, loanTotal);
    String grxfdk_rate = papa.getPercent(GRXFDK_AMT, loanTotal);
    String grajdk_rate = papa.getPercent(GRAJDK_AMT, loanTotal);
    String grzydk_rate = papa.getPercent(GRZYDK_AMT, loanTotal);
    String grjyxdk_rate = papa.getPercent(GRJYXDK_AMT, loanTotal);
    String nbwd_rate = papa.getPercent(NBWD_AMT, loanTotal);
    String at = papa.getPercent(assetTotal, assetTotal);
    String lt = papa.getPercent(loanTotal, loanTotal);

    String hqckye_format = papa.getMoney(HQCKYE_AMT);
    String tzckye_format = papa.getMoney(TZCK_AMT);
    String dqckye_format = papa.getMoney(DQCKYE_AMT);
    String ydy_format = papa.getMoney(YDY_AMT);
    String hdl_format = papa.getMoney(HDL_AMT);
    String fgl_format = papa.getMoney(FGL_AMT);
    String bx_format = papa.getMoney(BX_AMT);
    String afzr_format = papa.getMoney(AFZR_AMT);
    String dd_format = papa.getMoney(DD_AMT);
    String fhy_format = papa.getMoney(FHY_AMT);
	String zgjh_format=papa.getMoney(ZGJH_AMT);

    String xydk_format = papa.getMoney(XYDK_AMT);
    String grxfdk_format = papa.getMoney(GRXFDK_AMT);
    String grajdk_format = papa.getMoney(GRAJDK_AMT);
    String grzydk_format = papa.getMoney(GRZYDK_AMT);
    String grjyxdk_format = papa.getMoney(GRJYXDK_AMT);
    String nbwd_format = papa.getMoney(NBWD_AMT);
    String at_format = papa.getMoney(assetTotal);
    String lt_format = papa.getMoney(loanTotal);


    List<HashMap<String, Object>> financeProducts = papa.financialProducts(financeId);
    //产品名称
    String prodName = null;
    //产品类型
    String prodType = null;
    //理财产品
    String finance = "";
    //保险产品
    String insurance = "";
    //贷款产品
    String loan = "";

    String ee = "";


    for (HashMap<String, Object> map : financeProducts) {

        prodName = (String) map.get("PRODUCT_NAME");
        prodType = (String) map.get("PARENT_ID");


        String[] sg = prodName.split(",");


        for (int i = 0; i < sg.length; i++) {

            if (prodType == "113" || prodType.equals("113")) {

                if (sg[i].equals("") || sg[i] == "") {
                    finance = "";
                } else {

                    if (i < sg.length - 1) {
                        finance += sg[i] + "、 ";
                    } else {
                        finance += sg[i];

                        // finance = finance.substring(0, finance.length() - 2);

                    }


                }
            }


            if (prodType == "115" || prodType.equals("115")) {


                insurance += sg[i] + "<br/>";

            }
            if (prodType == "100" || prodType.equals("100")) {


                loan += sg[i] + "<br/>";

            }
        }

    }
    request.setAttribute("financeProducts", financeProducts);


    List<HashMap<String, Object>> totalScore = papa.scoreAll(custId);

    //产品名称
    String scoreAll = null;
    //风险类型
    String type = null;
    //风险承受能力
    String riskTolerance = null;
    //描述
    String discription = null;

    for (HashMap<String, Object> map : totalScore) {

        scoreAll = (String) map.get("SCORE_ALL");
        if (scoreAll.equals("")) {
            scoreAll = "0";
        }

    }


    int score = 0;
    score = Integer.parseInt(scoreAll);

    if (score <= 0) {
        type = "";
        riskTolerance = "";
        discription = "该客户未进行风险评估。";

    }
    if (score > 0 && score < 16) {
        type = "保守型";
        riskTolerance = "低风险";
        discription = "在投资规划中，客户不愿意承担丝毫投资风险，最关注的是本金安全，希望在确保本金安全的情况获得一定的收益。";
    }
    if (15 < score && score < 36) {
        type = "稳健型";
        riskTolerance = "中低风险";
        discription = "在投资规划中，客户的主要目的是在风险极小的情况下获得一定的收益，通常只愿意承担投资本金的微小损失，总体来看，客户只能承受低于市场平均水平的风险。";
    }
    if (35 < score && score < 61) {
        type = "平衡型";
        riskTolerance = "中风险";
        discription = "在投资规划中，客户的主要目的是在风险较小的情况下获得一定的收益。客户在做投资决定时，通常可接受本金面临一定的损失，且会对将要面临的风险进行认真的分析。";
    }
    if (60 < score && score < 81) {
        type = "成长型";
        riskTolerance = "中高风险";
        discription = "在投资规划中，客户渴望有较高的投资收益，可以承受一定的投资波动，但又不愿意承受较大的风险，希望自己的投资风险小于市场的整体风险。客户有较高的收益目标，且对风险有清醒的认识。";
    }
    if (80 < score && score < 101) {
        type = "进取型";
        riskTolerance = "高风险";
        discription = "在投资规划中，客户通常专注于投资的长期增值，并愿意为此承受较大的风险。短期的投资收益波动并不会对造成太大的影响，追求超高的回报才是客户关注的目标。";
    }


    request.setAttribute("totalScore", totalScore);


%>


<!--<div id="print" class="noprint" onclick="PrintPage()" >
    <img src="/crmweb/contents/images/softphone/phone/print.png" alt="打印PDF" style="float: right"/>
</div> -->

<div id="frame">
    <!--
    <div id="pageOne">
        <div id="div1">
            <div>
                <img src="/crmweb/contents/images/softphone/phone/Fubon.png" alt="富邦华一银行" width="200" height="30"/>
            </div>
            <div class="name" style=" width: 100mm">

                <h1>富邦华一银行<br>
                    个人理财规划书</h1><br><br><br>


                <h2>此致 &nbsp;
                    <%=custName%><%=gender%>
                </h2>

                <br>

                <h3> 规划日期：<%=date%>
                </h3>
                <br><br><br><br><br> <br><br><br><br><br>

                <div style="float: right ;"> 理财规划:    <%=mgrName%>
                </div>

            </div>
        </div>
    </div>
    <div id="pageTwo">
        <div id="div2">
            <div>
                <img src="/crmweb/contents/images/softphone/phone/Fubon.png" alt="富邦华一银行" width="200" height="30"/>
            </div>
            <br><br><br>

            <div align="center">
                <h2>致客户函</h2>
                <br><br><br>

                <p style="text-align:left" id="p1">
                    尊敬的<b id="b1"><%=custName%>
                </b><%=gender%>：您好!<br>
                    &nbsp;&nbsp;以「成为亚洲一流的金融机构」为发展愿景的富邦金控，拥有最完整多元的金融产品与服务，经营绩效耀眼，连续六年为台湾金融业获利第一，为台湾第二大上市金融控股公司。本行是首家由海峡两岸共同出资组建的商业银行，1997年6月，在上海浦东新区正式开业。19年来，本行秉承
                    "立足海峡两岸，积极服务台商"
                    之立行宗旨，以广大台商为中心，市场定位清晰、业务特色鲜明，服务质量和营运模式赢得了业界的认同，不仅扮演着两岸三地资金流通的桥梁，在增进两岸经济金融交流合作方面积极发挥作用。
                    <br>&nbsp;&nbsp;非常荣幸能为您呈上理财规划建议书。本规划书旨在帮助您明确财务需求及目标，协助您更好地决策。制作是基于您在我行的资产信息、交易往来与风险偏好，在加入现行的经济状况和合理的数据假设，可能与您的真实情况存在一定误差。对于您的个人状况和财务信息，我们将秉承一贯的职业道德标准，仅用于做理财规划之用，绝不会外泄给第三人，严格保密。
                    因为环境与社会是不断变化的，我们的人生也一直在往前发展，相应的财务状况与目标也会有所调整，所以我们希望与您建立一个长期关系，定期进行回顾与跟踪财务需求及目标的实现。
                    <br>&nbsp;&nbsp;祝健康、幸福！
                </p>

                <div style="float: right; "><br>
                    理财规划师<br>

                    <p><%=mgrName%> &nbsp;<%=date%>
                    </p>

                </div>


            </div>
        </div>
    </div>
    <div id="pageThree">
        <div id="div3">
            <div>
                <img src="/crmweb/contents/images/softphone/phone/Fubon.png" alt="富邦华一银行" width="200" height="30"/>
            </div>
            <div style="height: 237mm; width: 140mm;margin-left: auto;margin-right: auto; margin-top: 15mm;">
                <div>
                    <h2 style="color: #2d8aea">目录</h2>

                    <h2>第一部分 客户于我行资产/贷款配置情况</h2>

                    <h2>第二部分 客户的风险属性</h2>

                    <h2>第三部分 产品建议</h2>
                    <br><br><br>
                </div>
                <div>
                    <center>
                        <img src="/crmweb/contents/images/softphone/phone/AssetCondition.png" alt="资产情况" width="500"
                             height="350"/>
                    </center>
                </div>
            </div>
        </div>
    </div>

    -->


    <div id="pageFour">

        <div id="div4">
            <!--    <div>

                    <img src="/crmweb/contents/images/softphone/phone/Fubon.png" alt="富邦华一银行" width="200" height="30"/>

                </div>

                -->

            <div id="No1">
                1.客户资产、贷款配置情况

            </div>

            <div>


                <table >
                    <tr>
                        <td style="padding: inherit">
                            <div>
                                <div id="main" style="height: 400px;width: 550px; "></div>
                            </div>
                        </td>
                        <td>
                            <div>
                                <div id="main1" style="height: 400px;width: 550px; "></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td valign="top" align=center>
                            <div>


                                <table id="tableAsset" rules="none" style="text-align:center;border:1px solid #000;
                           font-size : 13px;color: #0a3172 ; ">


                                    <tr>
                                        <td colspan="3" height="50px">资产配置</td>
                                    </tr>
                                    <tr>
                                        <td bgcolor="#D6E3F2" height="35px" width="100px">流动资产</td>
                                        <td bgcolor="#D6E3F2" height="35px" width="100px">金额(元)</td>
                                        <td bgcolor="#D6E3F2" height="35px" width="100px">占总资产比</td>
                                    </tr>
                                    <tr>
                                        <td height="35px" width="100px">活期存款</td>
                                        <td height="35px" width="100px"><%=hqckye_format%>
                                        </td>
                                        <td height="35px" width="100px"><%=hqckye_rate%>
                                        </td>

                                    </tr>
                                    <tr>
                                        <td height="35px" width="100px">通知存款</td>
                                        <td height="35px" width="100px"><%=tzckye_format%>
                                        </td>
                                        <td height="35px" width="100px"><%=tzck_rate%>
                                        </td>

                                    </tr>
                                    <tr>
                                        <td bgcolor="#D6E3F2" height="35px" width="100px">投资资产</td>
                                        <td bgcolor="#D6E3F2" height="35px" width="100px">金额(元)</td>
                                        <td bgcolor="#D6E3F2" height="35px" width="100px">占总资产比</td>

                                    </tr>
                                    <tr>
                                        <td height="35px" width="100px">定期存款</td>
                                        <td height="35px" width="100px"><%=dqckye_format%>
                                        </td>
                                        <td height="35px" width="100px"><%=dqckye_rate%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="35px" width="100px">月得盈</td>
                                        <td height="35px" width="100px"><%=ydy_format%>
                                        </td>
                                        <td height="35px" width="100px"><%=ydy_rate%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="35px" width="100px">汇得利</td>
                                        <td height="35px" width="100px"><%=hdl_format%>
                                        </td>
                                        <td height="35px" width="100px"><%=hdl_rate%>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td height="35px" width="100px">富股利</td>
                                        <td height="35px" width="100px"><%=fgl_format%>
                                        </td>
                                        <td height="35px" width="100px"><%=fgl_rate%>
                                        </td>

                                    </tr>
                                     <tr>
                                        <td height="35px" width="100px">富汇盈</td>
                                        <td height="35px" width="100px"><%=fhy_format%>
                                        </td>
                                        <td height="35px" width="100px"><%=fhy_rate%>
                                        </td>

                                    </tr>
                                      <tr>
                                        <td height="35px" width="100px">安富尊荣</td>
                                        <td height="35px" width="100px"><%=afzr_format%>
                                        </td>
                                        <td height="35px" width="100px"><%=afzr_rate%>
                                        </td>

                                    </tr>
                                    <tr>
                                        <td height="35px" width="100px">保险</td>
                                        <td height="35px" width="100px"><%=bx_format%>
                                        </td>
                                        <td height="35px" width="100px"><%=bx_rate%>
                                        </td>

                                    </tr>
                                     <tr>
                                        <td height="35px" width="100px">资管计划</td>
                                        <td height="35px" width="100px"><%=zgjh_format%>
                                        </td>
                                        <td height="35px" width="100px"><%=zgjh_rate%>
                                        </td>

                                    </tr>
                                  
                                    <tr>
                                        <td height="35px" width="100px">双元货币</td>
                                        <td height="35px" width="100px"><%=dd_format%>
                                        </td>
                                        <td height="35px" width="100px"><%=dd_rate%>
                                        </td>

                                    </tr>
                                   
                                    <tr>
                                        <td bgcolor="#D6E3F2" height="35px" width="100px">资产总计</td>
                                        <td bgcolor="#D6E3F2" height="35px" width="100px"><%=at_format%>
                                        </td>
                                        <td bgcolor="#D6E3F2" height="35px" width="100px"><%=at%>
                                        </td>

                                    </tr>
                                </table>
                            </div>
                        </td>
                        <td valign="top" align=center>
                            <table id="tableLoan" rules="none" style="text-align:center;border:1px solid #000;
                        font-size : 13px;  color: #0a3172" margin-top: 15mm;>
                                <tr>
                                    <td colspan="3" height="50px">贷款配置</td>
                                </tr>
                                <tr>
                                    <td bgcolor="#D6E3F2" height="35px" width="100px">贷款产品</td>
                                    <td bgcolor="#D6E3F2" height="35px" width="100px">金额(元)</td>
                                    <td bgcolor="#D6E3F2" height="35px" width="100px">占总负债比</td>
                                </tr>
                                <tr>
                                    <td height="35px" width="100px">信用贷款</td>
                                    <td height="35px" width="100px"><%=xydk_format%>
                                    </td>
                                    <td height="35px" width="100px"><%=xydk_rate%>
                                    </td>
                                </tr>
                                <tr>
                                    <td height="35px" width="100px">个人房屋抵押消费贷款</td>
                                    <td height="35px" width="100px"><%=grxfdk_format%>
                                    </td>
                                    <td height="35px" width="100px"><%=grxfdk_rate%>
                                    </td>
                                </tr>
                                <tr>
                                    <td height="35px" width="100px">个人经营性贷款</td>
                                    <td height="35px" width="100px"><%=grjyxdk_format%>
                                    </td>
                                    <td height="35px" width="100px"><%=grjyxdk_rate%>
                                    </td>
                                </tr>
                                <tr>
                                    <td height="35px" width="100px">个人房屋抵押按揭贷款</td>
                                    <td height="35px" width="100px"><%=grajdk_format%>
                                    </td>
                                    <td height="35px" width="100px"><%=grajdk_rate%>
                                    </td>
                                </tr>

                                <tr>
                                    <td height="35px" width="100px">个人质押贷款</td>
                                    <td height="35px" width="100px"><%=grzydk_format%>
                                    </td>
                                    <td height="35px" width="100px"><%=grzydk_rate%>
                                    </td>
                                </tr>
                                <tr>
                                    <td height="35px" width="100px">内保外贷</td>
                                    <td height="35px" width="100px"><%=nbwd_format%>
                                    </td>
                                    <td height="35px" width="100px"><%=nbwd_rate%>
                                    </td>
                                </tr>
                                <tr>
                                    <td bgcolor="#D6E3F2" height="35px" width="100px">贷款总计</td>
                                    <td bgcolor="#D6E3F2" height="35px" width="100px"><%=lt_format%>
                                    </td>
                                    <td bgcolor="#D6E3F2" height="35px" width="100px"><%=lt%>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <div id="pageFive">
        <div id="div5">
            <!--  <div>
                  <img src="/crmweb/contents/images/softphone/phone/Fubon.png" alt="富邦华一银行" width="200" height="30"/>
              </div>
   <div>
                  <img src="/crmweb/contents/images/softphone/phone/CustRiskAttribute.png" alt="客户风险属性" width="230"
                       height="30"/>
              </div> -->


            <div>
                <div>
                    2. 客户风险属性

                </div>

                <table style=" color : #000">

                    <tr>
                        <td>
                            <%if (score <= 0) {%>
                            &nbsp;
                            <% } else {%>

                            <p> 客户的风险测评承受能力的测评分数为:<b class="b2"><%=scoreAll%>
                            </b> , &nbsp;风险承受能力为 <b class="b2"><%=riskTolerance%>


                            </b>,属于 <b class="b2"><%=type%>
                            </b>型投资者.<br><br>

                                    <%}%>

                            <div><%=discription%>
                            </div>


                            <br><br>
                            <table rules="none" style="border: 0px ;text-align:center; ">

                                <tr>


                                    <td>
                                        <% if (score > 0 && score < 16) {%>
                                        <img src="/crmweb/contents/images/softphone/phone/BSX.png"
                                             width="650px"
                                             height="150px">
                                        <%}%>
                                    </td>
                                    <td>
                                        <% if (15 < score && score < 36) {%>
                                        <img src="/crmweb/contents/images/softphone/phone/WJX.png"
                                             width="650px"
                                             height="150px">
                                        <%}%>
                                    </td>
                                    <td>
                                        <%if (35 < score && score < 61) {%>
                                        <img src="/crmweb/contents/images/softphone/phone/PHX.png"
                                             width="650px"
                                             height="150px">
                                        <%}%>
                                    </td>
                                    <td>
                                        <%if (60 < score && score < 81) {%>
                                        <img src="/crmweb/contents/images/softphone/phone/CZX.png"
                                             width="650px"
                                             height="150px">
                                        <%}%>
                                    </td>
                                    <td>
                                        <%if (80 < score && score < 101) {%>
                                        <img src="/crmweb/contents/images/softphone/phone/JQX.png"
                                             width="650px"
                                             height="150px">
                                        <%}%>
                                    </td>


                                    <!--<td><img src="/crmweb/contents/images/softphone/phone/ConservativeType.png"
                                             width="130px"
                                             height="30px"></td>
                                    <td><img src="/crmweb/contents/images/softphone/phone/SteadyType.png" width="130"
                                             height="30"></td>
                                    <td><img src="/crmweb/contents/images/softphone/phone/BalancedType.png" width="130"
                                             height="30"></td>
                                    <td><img src="/crmweb/contents/images/softphone/phone/GrowupType.png" width="130"
                                             height="30"></td>
                                    <td><img src="/crmweb/contents/images/softphone/phone/AggressiveType.png"
                                             width="130"
                                             height="30"></td> -->
                                </tr>

                            </table>
                            <br><br>


                        </td>
                    </tr>

                </table>
            </div>

            <br><br><br>


            <div>
                <div>
                    3. 综合资产、贷款产品推荐
                </div>
                <br>
                <table style="font-weight:normal;text-align:center; border: 0px solid #FFFFFF ">
                    <tr>
                        <td bgcolor="#00aed1" height="45px" width="100px" style="color: white">产品种类</td>
                        <td class="col1" bgcolor="#00aed1" height="45px" width="450px"
                            style="color: white">产品细项
                        </td>
                    </tr>

                    <tr>
                        <td bgcolor="#00aed1" width="100px" height="45px" style="color: white">理财类</td>
                        <td name="col1" style="background: #e5f1f4 " height="45px" width="450px"
                            onmouseover="this.style.backgroundColor='#ecfbd4'"
                            onmouseout="this.style.backgroundColor='#e5f1f4'"
                            onclick="this.style.backgroundColor='#bce774'">

                            <p class="p2">


                                <%=finance%>
                            </p>


                        </td>
                    </tr>

                    <!--   <tr>
            <td bgcolor="#D6E3F2"  width="40px" >理財產品</td>
            <td bgcolor="#D6E3F2" height="40px" width="40px" style="text-decoration:underline">低风险</td>
            <td bgcolor="#D6E3F2"  width="40px"></td>
        </tr>

      <tr>

            <td bgcolor="#D6E3F2" height="40" style="text-decoration:underline">中低风险</td>
            <td bgcolor="#D6E3F2" ></td>
        </tr>
        <tr>

            <td bgcolor="#D6E3F2" height="40px" style="text-decoration:underline">中等风险</td>
            <td bgcolor="#D6E3F2" height="40px">非保本浮动收益产品（安富尊荣）、债券型基金、富股利、富汇盈
            </td>
        </tr>
        <tr>

            <td bgcolor="#D6E3F2" height="40px" style="text-decoration:underline">中高风险</td>
            <td bgcolor="#D6E3F2" height="40px"></td>
        </tr>
        <tr>

            <td bgcolor="#D6E3F2" height="40px" style="text-decoration:underline">高风险</td>
            <td bgcolor="#D6E3F2" height="40px"></td>
        </tr> -->
                    <tr>
                        <td bgcolor="#00aed1" height="45px" width="100px" style="color: white">保险类</td>
                        <td name="col1" bgcolor="#f8fbfc" width="450px"
                            onmouseover="this.style.backgroundColor='#ecfbd4'"
                            onmouseout="this.style.backgroundColor='#f8fbfc'"
                            onclick="this.style.backgroundColor='#bce774'">

                            <p class="p2">
                                <%=insurance%>
                            </p>


                        </td>
                    </tr>
                    <tr>
                        <td bgcolor="#00aed1" height="45px" width="100px" style="color: white">贷款类</td>
                        <td name="col1" bgcolor="#e5f1f4" width="450px"
                            onmouseover="this.style.backgroundColor='#ecfbd4'"
                            onmouseout="this.style.backgroundColor='#e5f1f4'"
                            onclick="this.style.backgroundColor='#bce774'">

                            <p class="p2">
                                <%=loan%>
                            </p>


                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    window.onload = function () {

        $(function () {
            $('.dowebok').flipTimer({
                seconds: true
            });
        });
        //路径配置
        require.config({
            paths: {
                echarts: "/crmweb/echarts-2.2.7/build/dist"
            }
        });
        require([
                    'echarts',
                    'echarts/theme/macarons',
                    'echarts/chart/pie'
                ],
                function (ec, theme) {

                    var myChart = ec.init(document.getElementById('main'), theme);
                    var myChart1 = ec.init(document.getElementById('main1'), theme);
                    var optionMain1 = {

                        <%--    title: {
                                text: '某站点用户访问来源',
                                subtext: '纯属虚构',
                                x: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            legend: {
                                orient: 'vertical',
                                x: 'left',
                                data: []
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    mark: {show: true},
                                    dataView: {show: true, readOnly: false},
                                    magicType: {
                                        show: true,
                                        type: ['pie', 'funnel'],
                                        option: {
                                            funnel: {
                                                x: '25%',
                                                width: '50%',
                                                funnelAlign: 'left',
                                                max: 1548
                                            }
                                        }
                                    },
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },--%>
                        calculable: true,
                        series: [

                            {

                                name: '访问来源',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: [
                                    {
                                        value: <%=HQCKYE_AMT%>, name: '活期存款\n<%=hqckye_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=hqckye_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=hqckye_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=TZCK_AMT%>, name: '通知存款\n<%=tzck_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=tzckye_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=tzckye_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=DQCKYE_AMT%>, name: '定期存款\n<%=dqckye_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=dqckye_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=dqckye_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=YDY_AMT%>, name: '月得盈\n<%=ydy_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=ydy_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=ydy_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=HDL_AMT%>, name: '汇得利\n<%=hdl_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=hdl_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=hdl_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=FGL_AMT%>, name: '富股利\n<%=fgl_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=fgl_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=fgl_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=BX_AMT%>, name: '保险\n<%=bx_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=bx_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=bx_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=AFZR_AMT%>, name: '安富尊荣\n<%=afzr_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=afzr_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=afzr_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=DD_AMT%>, name: '双元货币\n<%=dd_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=dd_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=dd_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=FHY_AMT%>, name: '富汇盈\n<%=fhy_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=fhy_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=fhy_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=ZGJH_AMT%>, name: '资管计划\n<%=zgjh_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=zgjh_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=zgjh_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    }
                                ]
                            }

                        ]
                    };
                    var optionMain2 = {
                        calculable: true,
                        series: [
                            {
                                name: '访问来源',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: [
                                    {
                                        value: <%=XYDK_AMT%>, name: '信用貸款\n<%=xydk_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=xydk_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=xydk_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=GRXFDK_AMT%>, name: '个人房屋抵押消费贷款\n<%=grxfdk_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=grxfdk_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=grxfdk_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=GRJYXDK_AMT%>, name: '个人经营性贷款\n<%=grjyxdk_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=grjyxdk_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=grjyxdk_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=GRAJDK_AMT%>, name: '个人按揭贷款\n<%=grajdk_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=grajdk_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=grajdk_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=GRZYDK_AMT%>, name: '个人质押贷款\n<%=grzydk_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=grzydk_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=grzydk_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    },
                                    {
                                        value: <%=NBWD_AMT%>, name: '内保外贷\n<%=nbwd_rate%>', itemStyle: {
                                        normal: {
                                            label: {
                                                show: function () {
                                                    if ('<%=nbwd_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            },
                                            labelLine: {
                                                show: function () {
                                                    if ('<%=nbwd_format%>' == '0') {
                                                        return false;
                                                    }
                                                }()

                                            }
                                        }
                                    }
                                    }
                                ]
                            }
                        ]
                    };


                    myChart.setOption(optionMain1);
                    myChart1.setOption(optionMain2);

                });
    }

</script>

</body>

</html>
