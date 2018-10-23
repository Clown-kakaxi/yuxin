/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket
 * @文件名：ExtNioServer.java
 * @版本信息：1.0.0
 * @日期：2013-12-17-11:45:28
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
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
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ExtNioServer
 * @类描述： 一个服务，监听多个端口，用不同的解析器解析不同的接口报文
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2013-12-17 上午11:45:40   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2013-12-17 上午11:45:40
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    --------		----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2013北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ExtNioServer extends NioServer{
	/**
	 * @属性名称:adapterMap
	 * @属性描述:端口与协议解析适配器关系
	 * @since 1.0.0
	 */
	private Map<Integer,Class> adapterMap=new TreeMap<Integer,Class>();
	/***
	 * 构造函数
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
		//获取端口及协议适配器对应关系
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
			throw new Exception("没有配置端口号");
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





