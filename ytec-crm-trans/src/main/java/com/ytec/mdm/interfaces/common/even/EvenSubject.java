/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.common.even
 * @�ļ�����EvenSubject.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:32:31
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ�EvenSubject
 * @���������¼�֪ͨ
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:32:31
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:32:31
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
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
