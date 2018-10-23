/**
 * 指标数据
 */
var targets = {};
targets.orgTargets = [];
targets.userTargets = [];
targets.custTargets = [];
/**
 * 机构类指标
 */
targets.orgTargets = [
{index:1,type:'负债类',name:'存款日均'},
{index:2,type:'负债类',name:'存款余额'},
{index:3,type:'负债类',name:'存款贡献度'},
{index:4,type:'负债类',name:'存款模拟利润'},
{index:5,type:'负债类',name:'理财存款日均'},
{index:6,type:'负债类',name:'理财存款余额'},
{index:7,type:'负债类',name:'保证金存款日均'},
{index:8,type:'负债类',name:'保证金存款余额'},
{index:9,type:'资产类',name:'贷款日均'},
{index:10,type:'资产类',name:'贷款余额'},
{index:11,type:'资产类',name:'贷款贡献度'},
{index:12,type:'资产类',name:'贷款模拟利润'},
{index:13,type:'资产类',name:'贴现日均'},
{index:14,type:'资产类',name:'贴现余额'},
{index:15,type:'资产类',name:'授信吸存率'},
{index:16,type:'客户类',name:'网银签约客户数'},
{index:17,type:'客户类',name:'总客户数'},
{index:18,type:'客户类',name:'有效客户数'},
{index:19,type:'客户类',name:'私人银行客户数'},
{index:20,type:'客户类',name:'重点客户数'},
{index:21,type:'客户类',name:'优质客户数'},
{index:22,type:'客户类',name:'普通客户数'},
{index:23,type:'客户类',name:'潜力客户数'},
{index:24,type:'客户类',name:'新增客户数'},
{index:25,type:'中间业务类',name:'中间业务收入额度'},
{index:26,type:'中间业务类',name:'电子银行业务量'},
{index:27,type:'中间业务类',name:'票据承兑额度'},
{index:28,type:'中间业务类',name:'代理业务额度'} ];

/**
 * 客户经理类指标
 */
targets.userTargets = [{index:1,type:'负债类',name:'存款日均'},
{index:2,type:'负债类',name:'存款余额'},
{index:3,type:'负债类',name:'存款贡献度'},
{index:4,type:'负债类',name:'存款模拟利润'},
{index:5,type:'负债类',name:'理财存款日均'},
{index:6,type:'负债类',name:'理财存款余额'},
{index:7,type:'负债类',name:'保证金存款日均'},
{index:8,type:'负债类',name:'保证金存款余额'},
{index:9,type:'资产类',name:'贷款日均'},
{index:10,type:'资产类',name:'贷款余额'},
{index:11,type:'资产类',name:'贷款贡献度'},
{index:12,type:'资产类',name:'贷款模拟利润'},
{index:13,type:'资产类',name:'贴现日均'},
{index:14,type:'资产类',name:'贴现余额'},
{index:15,type:'资产类',name:'授信吸存率'},
{index:16,type:'客户类',name:'网银签约客户数'},
{index:17,type:'客户类',name:'总客户数'},
{index:18,type:'客户类',name:'有效客户数'},
{index:19,type:'客户类',name:'私人银行客户数'},
{index:20,type:'客户类',name:'重点客户数'},
{index:21,type:'客户类',name:'优质客户数'},
{index:22,type:'客户类',name:'普通客户数'},
{index:23,type:'客户类',name:'潜力客户数'},
{index:24,type:'客户类',name:'新增客户数'},
{index:25,type:'中间业务类',name:'中间业务收入额度'},
{index:26,type:'中间业务类',name:'电子银行业务量'},
{index:27,type:'中间业务类',name:'票据承兑额度'},
{index:28,type:'中间业务类',name:'代理业务额度'}];

/**
 * 客户类指标
 */
targets.custTargets = [{index:1,type:'客户类',name:'签约产品数'},
                       {index:2,type:'客户类',name:'客户综合贡献度'},
                       {index:3,type:'客户类',name:'客户AUM值'},
                       {index:4,type:'客户类',name:'客户综合评级'},
                       {index:5,type:'客户类',name:'客户信用评级'},
                       {index:6,type:'客户类',name:'风险评级'},
                       {index:7,type:'客户类',name:'私人银行客户'},
                       {index:8,type:'客户类',name:'资产总额'},
                       {index:9,type:'客户类',name:'风险评级'},
                       {index:10,type:'负债类',name:'存款日均'},
                       {index:11,type:'负债类',name:'存款余额'},
                       {index:12,type:'负债类',name:'存款贡献度'},
                       {index:13,type:'负债类',name:'存款模拟利润'},
                       {index:14,type:'负债类',name:'理财存款日均'},
                       {index:15,type:'负债类',name:'理财存款余额'},
                       {index:16,type:'负债类',name:'保证金存款日均'},
                       {index:17,type:'负债类',name:'保证金存款余额'},
                       {index:18,type:'资产类',name:'消费贷款日均'},
                       {index:19,type:'资产类',name:'消费贷款余额'},
                       {index:20,type:'资产类',name:'贷款贡献度'},
                       {index:21,type:'资产类',name:'贷款模拟利润'},
                       {index:22,type:'资产类',name:'按揭贷款日均'},
                       {index:23,type:'资产类',name:'按揭贷款余额'},
                       {index:24,type:'资产类',name:'授信吸存率'},
                       {index:25,type:'中间业务类',name:'中间业务收入额度'},
                       {index:26,type:'中间业务类',name:'电子银行业务量'},
                       {index:27,type:'中间业务类',name:'票据承兑额度'},
                       {index:28,type:'中间业务类',name:'代理业务额度'}
];
/**
 * 主题
 */
var major = [{key:'cust',value:'客户'},
         {key:'prod',value:'产品'},
         {key:'orga',value:'机构'},
         {key:'user',value:'员工'},
         {key:'sale',value:'营销'}];

/**
 * 范围
 */
var incl = [{key:'orga',value:'机构'},
            {key:'mana',value:'客户经理'},
            {key:'cust',value:'客户'}];
/**
 * 图类型
 */
var chartType = [{key:'pie',value:'饼状图'},
                 {key:'tie',value:'柱状图'},
                 {key:'qushi',value:'趋势图'}/*,
                 {key:'rada',value:'雷达图'}*/
                 ];
/**
 * 统计粒度
 */
var tinaType = [{key:'day',value:'日'},
                {key:'month',value:'月'},
                {key:'quart',value:'季'},
                {key:'year',value:'年'}];

/**
 * 饼图列表数据，数据同pie.xml
 */
var pieData = [
               {target:'私人银行客户数',number:'8892',rate:'4%'},
               {target:'重点客户数',number:'20489',rate:'9%'},
               {target:'优质客户数',number:'30488',rate:'13%'},
               {target:'普通客户数',number:'60903',rate:'26%'},
               {target:'潜力客户数',number:'110293',rate:'48%'}
               ];

/**
 * 柱状图列表数据，数据同mscolumn.xml
 */
var columnData = [{target:'存款日均',number:'304'},
                  {target:'存款余额',number:'389'},
                  {target:'消费贷款日均',number:'34'},
                  {target:'消费贷款余额',number:'43'},
                  {target:'按揭贷款日均',number:'180'},
                  {target:'按揭贷款余额',number:'202'}
                  ];
/**
 * 趋势图数据，数据同msline.xml。
 */
var lineData = [{target:'存款日均',number1:'120',number2:'132',number3:'145',number4:'123',number5:'114',number6:'167'},
				{target:'存款余额',number1:'132',number2:'156',number3:'178',number4:'80',number5:'60',number6:'300'},
				{target:'贷款日均',number1:'23',number2:'26',number3:'15',number4:'32',number5:'67',number6:'80'},
				{target:'贷款余额',number1:'45',number2:'32',number3:'9',number4:'55',number5:'121',number6:'132'}
                ];