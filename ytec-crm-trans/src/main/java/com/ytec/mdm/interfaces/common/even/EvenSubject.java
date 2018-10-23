/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.common.even
 * @文件名：EvenSubject.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:32:31
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
package com.ytec.mdm.interfaces.common.even;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ytec.mdm.base.bo.EcifData;


/**
 * @项目名称：ytec-mdm-ecif
 * @类名称：EvenSubject
 * @类描述：事件通知
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:32:31
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:32:31
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 *
 */
public class EvenSubject implements Subject {
	private static Subject evenSubject=new EvenSubject();
	private List<Observer> observerList=new ArrayList<Observer>();

	public EvenSubject() {
	}

	public void init(Map arg) throws Exception{
		Collection<String> c = arg.values();
		Iterator<String> it = c.iterator();
		evenSubject.clearObserver();
		Observer observer=null;
		while(it.hasNext()) {
			observer=(Observer)Class.forName(it.next()).newInstance();
			observer.init();
			evenSubject.addObserver(observer);
		}
	}

	public static Subject getInstance(){
		return evenSubject;
	}

	@Override
	public void addObserver(Observer o) {
		// TODO Auto-generated method stub
		observerList.add(o);
	}



	@Override
	public void eventNotify(EcifData ecifData) {
		// TODO Auto-generated method stub
		for(int i = 0,len=observerList.size(); i < len; i++) {
			observerList.get(i).executeObserver(ecifData);
		}
	}

	/* (non-Javadoc)
	 * @see com.ytec.mdm.interfaces.common.even.Subject#clearObserver()
	 */
	@Override
	public void clearObserver() {
		// TODO Auto-generated method stub
		observerList.clear();
	}

}
