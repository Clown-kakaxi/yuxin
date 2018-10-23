package com.yuchengtech.bcrm.sales.marketTask.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTask;
import com.yuchengtech.bcrm.sales.marketTask.model.OcrmFMmTaskTarget;
import com.yuchengtech.bob.common.CommonService;
import com.yuchengtech.bob.common.JPABaseDAO;
import com.yuchengtech.bob.vo.AuthUser;

/**
 * @describtion: 我的营销任务
 *
 * @author : helin
 * @date : 2014-07-03 10:25:20
 */
@Service
public class MyMarketTaskService extends CommonService {
    public MyMarketTaskService(){
        JPABaseDAO<OcrmFMmTask, Long> baseDao = new JPABaseDAO<OcrmFMmTask, Long>(OcrmFMmTask.class);
        super.setBaseDAO(baseDao);
    }
    
    /**
     * create or update myMarketTask
     */
    public Object save(Object obj) {
        OcrmFMmTask myMarketTask = (OcrmFMmTask)obj;
        return super.save(myMarketTask);
    }
    
    /**
     * bacth delete myMarketTask
     */
    public void batchRemove(String ids) {
        Map<String, Object> values = new HashMap<String, Object>();
        super.batchUpdateByName("DELETE FROM OcrmFMmTask t WHERE t.id IN("+ ids +")", values);
    }
    
    /**
     * 拼装指标周期类型
     * @return
     */
    public List<Map> queryTargetCycle(){
    	List<Map> result = new ArrayList<Map>();
    	Map<String, String> map = new HashMap<String, String>();
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat monthFormat = new SimpleDateFormat("MM-dd");
    	
        //1 周
        map.put("CODE", "1");
        map.put("NAME", "周");
        map.put("PARENT", "0");
        result.add(map);
        Calendar weekFirst = Calendar.getInstance();
        for(int i=0;i<7;i++){
        	weekFirst.add(Calendar.DATE, 1);
        	int day = weekFirst.get(Calendar.DAY_OF_WEEK);
        	if(day == 2){
        		break;
        	}
        }
        Calendar weekLast = (Calendar) weekFirst.clone();
        weekLast.add(Calendar.DATE, 6);
        for(int i=0;i<6;i++){
        	map = new HashMap<String, String>();
            map.put("CODE", "1#"+format.format(weekFirst.getTime())+"#"+format.format(weekLast.getTime()));
            map.put("NAME", "周("+monthFormat.format(weekFirst.getTime())+"—"+monthFormat.format(weekLast.getTime())+")");
            map.put("PARENT", "1");
            result.add(map);
            weekFirst.add(Calendar.DATE, 7);
        	weekLast.add(Calendar.DATE, 7);
        }
        
        //2 月
        map = new HashMap<String, String>();
        map.put("CODE", "2");
        map.put("NAME", "月");
        map.put("PARENT", "0");
        result.add(map);
        Calendar first = Calendar.getInstance();
        first.set(Calendar.DAY_OF_MONTH, 1);
        Calendar last = Calendar.getInstance();    
        last.set(Calendar.DAY_OF_MONTH, last.getActualMaximum(Calendar.DAY_OF_MONTH));
        for(int i=0;i<6;i++){
        	first.add(Calendar.MONTH, 1);
        	last.add(Calendar.MONTH, 1);
        	first.set(Calendar.DAY_OF_MONTH, 1);
        	last.set(Calendar.DAY_OF_MONTH, last.getActualMaximum(Calendar.DAY_OF_MONTH));
        	map = new HashMap<String, String>();
            map.put("CODE", "2#"+format.format(first.getTime())+"#"+format.format(last.getTime()));
            map.put("NAME", first.get(Calendar.YEAR)+"年"+(first.get(Calendar.MONTH)+1)+"月");
            map.put("PARENT", "2");
            result.add(map);
        }
        
        
        //3 季度
        map = new HashMap<String, String>();
        map.put("CODE", "3");
        map.put("NAME", "季度");
        map.put("PARENT", "0");
        result.add(map);
    	int year = Calendar.getInstance().get(Calendar.YEAR);
    	int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
    	if(month <= 3){
    		map = new HashMap<String, String>();
    		map.put("CODE", "3#"+year+"-03-01#"+year+"-06-30");
            map.put("NAME", year+"年2季度");
            map.put("PARENT", "3");
            result.add(map);
    	}
    	if(month <= 6){
    		map = new HashMap<String, String>();
    		map.put("CODE", "3#"+year+"-06-01#"+year+"-09-30");
            map.put("NAME", year+"年3季度");
            map.put("PARENT", "3");
            result.add(map);
    	}
    	if(month <=9){
    		map = new HashMap<String, String>();
    		map.put("CODE", "3#"+year+"-09-01#"+year+"-12-31");
            map.put("NAME", year+"年4季度");
            map.put("PARENT", "3");
            result.add(map);
    	}
    		
    	year = year + 1;
    	map = new HashMap<String, String>();
    	map.put("CODE", "3#"+year+"-01-01#"+year+"-03-31");
        map.put("NAME", year+"年1季度");
        map.put("PARENT", "3");
        result.add(map);
        
        map = new HashMap<String, String>();
        map.put("CODE", "3#"+year+"-03-01#"+year+"-06-30");
        map.put("NAME", year+"年2季度");
        map.put("PARENT", "3");
        result.add(map);
        
        map = new HashMap<String, String>();
        map.put("CODE", "3#"+year+"-06-01#"+year+"-09-30");
        map.put("NAME", year+"年3季度");
        map.put("PARENT", "3");
        result.add(map);
        
        map = new HashMap<String, String>();
        map.put("CODE", "3#"+year+"-09-01#"+year+"-12-31");
        map.put("NAME", year+"年4季度");
        map.put("PARENT", "3");
        result.add(map);
        
        year = year + 1;
        if(month > 3){
        	map = new HashMap<String, String>();
        	map.put("CODE", "3#"+year+"-01-01#"+year+"-03-31");
            map.put("NAME", year+"年1季度");
            map.put("PARENT", "3");
            result.add(map);
        }
        if(month > 6 ){
            map = new HashMap<String, String>();
            map.put("CODE", "3#"+year+"-03-01#"+year+"-06-30");
            map.put("NAME", year+"年2季度");
            map.put("PARENT", "3");
            result.add(map);
        }
        if(month >9){
            map = new HashMap<String, String>();
            map.put("CODE", "3#"+year+"-06-01#"+year+"-09-30");
            map.put("NAME", year+"年3季度");
            map.put("PARENT", "3");
            result.add(map);
        }
        
		//4 年
        map = new HashMap<String, String>();
        map.put("CODE", "4");
        map.put("NAME", "年");
        map.put("PARENT", "0");
        result.add(map);
        
		year = Calendar.getInstance().get(Calendar.YEAR);
		for(int i=1;i<=5;i++){
			map = new HashMap<String, String>();
			map.put("CODE", "4#"+(year + i)+"-01-31#"+(year + i)+"-12-31");
			map.put("NAME", (year + i) + "年");
			map.put("PARENT", "4");
			result.add(map);
		}
		return result;
    }
    
	/**
     * 营销任务反馈
     * @param marketTask
     */
    public void feedBackadTask(OcrmFMmTask marketTask){
    	ActionContext ctx = ActionContext.getContext();
    	HttpServletRequest request =(HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
    	AuthUser auth = (AuthUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	String operObjIds = marketTask.getOperObjId();	//执行对象ID集合
    	String targetIds = request.getParameter("targetIds");// 指标ID集合
    	String cycleIds = request.getParameter("cycleIds");// 指标ID集合
		String targetValueData = request.getParameter("targetDataValue");// 填写的指标值
		String achieveRemark=request.getParameter("achieveRemark");
		String[] targetValueArr = null;
		String[] targetValueDataArr = targetValueData.split(";"); //即使最后多一组空,也可不用处理,因为指标只有那么多组
		//根据任务ID,查找数据库任务ID
    	OcrmFMmTask tempTask = em.find(OcrmFMmTask.class,marketTask.getTaskId());
    	tempTask.setRecentlyUpdateDate(new Date());
		tempTask.setRecentlyUpdateId(auth.getUserId());
		tempTask.setRecentlyUpdateName(auth.getUsername());
		super.save(tempTask);	//更新主任务信息
		//sql查询的顺序一定要注意,不然会影响调整的对象指标对不上
			
		targetValueArr = targetValueDataArr[0].split(",");
		//更新子任务指标
		List<OcrmFMmTaskTarget> subTargetList = this.em.createQuery("select t from OcrmFMmTaskTarget t where t.taskId = '"+tempTask.getTaskId()+"' order by t.targetCode,t.cycleType,t.endDate asc").getResultList();
		for(int j=0;j<subTargetList.size();j++){
			OcrmFMmTaskTarget subTarget = subTargetList.get(j);
			subTarget.setRecentlyUpdateDate(tempTask.getRecentlyUpdateDate());
			subTarget.setRecentlyUpdateId(auth.getUserId());
			subTarget.setRecentlyUpdateName(auth.getUsername());
			subTarget.setAchieveValue(new BigDecimal(targetValueArr[j]));//调整目标完成值
			subTarget.setAchieveRemark(achieveRemark);
			if(subTarget.getTargetValue().longValue() <=0 ){
				subTarget.setAchievePercent(new BigDecimal(0));
			}else{
				//subTarget.setAchievePercent(new BigDecimal(subTarget.getAchieveValue().doubleValue()/subTarget.getTargetValue().doubleValue()));
			}
			subTarget.setAchieveValueState("1");
			super.save(subTarget);
		}
    }
    
}
