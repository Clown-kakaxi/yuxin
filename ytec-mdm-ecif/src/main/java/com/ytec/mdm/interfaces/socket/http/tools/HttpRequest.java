package com.ytec.mdm.interfaces.socket.http.tools;


import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.Map;

import com.ytec.mdm.base.util.MdmConstants;
import com.ytec.mdm.interfaces.socket.http.tools.BytesInputStream;
import com.ytec.mdm.interfaces.socket.http.tools.HttpMethod;
import com.ytec.mdm.interfaces.socket.http.tools.HttpUtils;
import com.ytec.mdm.interfaces.socket.http.tools.HttpVersion;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.CHARSET;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.CONNECTION;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.CONTENT_TYPE;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpVersion.HTTP_1_1;



public class HttpRequest {
    public final String queryString;
    public final String uri;
    public final HttpMethod method;
    public final HttpVersion version;

    private byte[] body;

    // package visible
    private int serverPort = 80;
    private String serverName;
    public Map<String, String> headers;
    public int contentLength = 0;
    private String contentType;
    private String charset = MdmConstants.TX_XML_ENCODING;
    public boolean isKeepAlive = false;
    boolean isWebSocket = false;
    
    InetSocketAddress remoteAddr;
   // AsyncChannel channel;

    public HttpRequest(HttpMethod method, String url, HttpVersion version) {
        this.method = method;
        this.version = version;
        int idx = url.indexOf('?');
        if (idx > 0) {
            uri = url.substring(0, idx);
            queryString = url.substring(idx + 1);
        } else {
            uri = url;
            queryString = null;
        }
    }

    public InputStream getBody() {
        if (body != null) {
            return new BytesInputStream(body, contentLength);
        }
        return null;
    }
    
    public String getBodyString(){
    	if(body != null){
    		try {
    			if(charset!=null){
    				return new String(body,charset);
    			}else{
    				return new String(body,MdmConstants.TX_XML_ENCODING);
    			}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				return new String(body);
			}
    	}
    	return null;
    }

    public String getRemoteAddr() {
        String h = headers.get(HttpUtils.X_FORWARDED_FOR);
        if (null != h) {
            int idx = h.indexOf(',');
            if (idx == -1) {
                return h;
            } else {
                // X-Forwarded-For: client, proxy1, proxy2
                return h.substring(0, idx);
            }
        } else {
            return remoteAddr.getAddress().getHostAddress();
        }
    }

    public void setBody(byte[] body, int count) {
        this.body = body;
        this.contentLength = count;
    }

    public void setHeaders(Map<String, String> headers) {
        String h = headers.get("host");
        if (h != null) {
            int idx = h.lastIndexOf(':');
            if (idx != -1) {
                this.serverName = h.substring(0, idx);
                serverPort = Integer.valueOf(h.substring(idx + 1));
            } else {
                this.serverName = h;
            }
        }

        String ct = headers.get(CONTENT_TYPE);
        if (ct != null) {
            int idx = ct.indexOf(";");
            if (idx != -1) {
                int cidx = ct.indexOf(CHARSET, idx);
                if (cidx != -1) {
                    contentType = ct.substring(0, idx);
                    charset = ct.substring(cidx + CHARSET.length());
                } else {
                    contentType = ct;
                }
            } else {
                contentType = ct;
            }
        }

        String con = headers.get(CONNECTION);
        if (con != null) {
            con = con.toLowerCase();
        }

        isKeepAlive = (version == HTTP_1_1 && !"close".equals(con)) || "keep-alive".equals(con);
        isWebSocket = "websocket".equalsIgnoreCase(headers.get("upgrade"));
        this.headers = headers;
    }

	public void setRemoteAddr(InetSocketAddress remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public String getContentType() {
		return contentType;
	}

	public String getCharset() {
		return charset;
	}
	
}
