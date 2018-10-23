/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket
 * @�ļ�����ExtNioServer.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2013-12-17-11:45:28
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
package com.ytec.mdm.interfaces.socket.ext;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import com.ytec.mdm.base.util.StringUtil;
import com.ytec.mdm.interfaces.socket.IoSession;
import com.ytec.mdm.interfaces.socket.NioServer;
import com.ytec.mdm.interfaces.socket.Server_I;
import com.ytec.mdm.server.common.ServerConfiger;

/**
 * @��Ŀ���ƣ�ytec-mdm-ecif 
 * @�����ƣ�ExtNioServer
 * @�������� һ�����񣬼�������˿ڣ��ò�ͬ�Ľ�����������ͬ�Ľӿڱ���
 * @��������:
 * @�����ˣ�wangzy1@yuchengtech.com
 * @����ʱ�䣺2013-12-17 ����11:45:40   
 * @�޸��ˣ�wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2013-12-17 ����11:45:40
 * @�޸ı�ע��
 * @�޸�����		�޸���Ա		�޸�ԭ��
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013���������׳ϿƼ����޹�˾-��Ȩ����
 * 
 */
public class ExtNioServer extends NioServer{
	/**
	 * @��������:adapterMap
	 * @��������:�˿���Э�������������ϵ
	 * @since 1.0.0
	 */
	private Map<Integer,Class> adapterMap=new TreeMap<Integer,Class>();
	/***
	 * ���캯��
	 */
	public ExtNioServer(){
		super();
	}

	public void init(Map arg) throws Exception {
		// TODO Auto-generated method stub
		this.selectSize=Integer.parseInt((String)arg.get("nioServerMaxSelects"));
		this.poolSize=Integer.parseInt((String)arg.get("nioServerMaxExecuter"));
		if(this.selectSize==0){
			this.selectSize=DEFAULT_SIZE;
		}
		this.ip=(String)arg.get("nioServerIp");
		if(StringUtil.isEmpty(this.ip)){
			this.ip=StringUtil.getLocalIp();
		}
		//��ȡ�˿ڼ�Э����������Ӧ��ϵ
		int portLenth=0;
		String adapters=(String)arg.get("adapters");
		if(!StringUtil.isEmpty(adapters)){
			String[] adapter=adapters.split("\\,");
			for(String adapter_i:adapter){
				String[] adapter_j=adapter_i.split("\\:");
				adapterMap.put(Integer.valueOf(adapter_j[0]), Class.forName(adapter_j[1]));
			}
		}
		
		String ports=(String)arg.get("nioServerPort");
		if(!StringUtil.isEmpty(ports)){
			String[] port_i=ports.split("\\,");
			int port_;
			for(String port_j:port_i){
				port_=Integer.valueOf(port_j);
				if(!adapterMap.containsKey(port_)){
					adapterMap.put(port_,  Class.forName(ServerConfiger.adapter));
				}
			}
		}
		portLenth=adapterMap.size();
		if(portLenth>0){
			this.port=new int[portLenth];
			Set<Integer> keySet=adapterMap.keySet();
			int i=0;
			for(int p:keySet){
				this.port[i++]=p;
			}
		}else{
			throw new Exception("û�����ö˿ں�");
		}
	}
	
	protected Server_I getServer_I(){
		return new ExtServer_I(this.poolSize);
	}
	
	protected IoSession buildIoSession(SocketChannel client,ServerSocketChannel server) throws IOException{
		ExtIoSession session=new ExtIoSession(client,server.socket().getLocalPort());
		session.setClazz(adapterMap.get(session.getServerAcceptPort()));
		return session;
	}
}





