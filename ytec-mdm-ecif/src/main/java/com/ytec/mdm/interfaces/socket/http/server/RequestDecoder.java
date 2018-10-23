/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.http.server
 * @文件名：RequestDecoder.java
 * @版本信息：1.0.0
 * @日期：2014-5-30-11:57:36
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.http.server;

import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.*;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpVersion.HTTP_1_0;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpVersion.HTTP_1_1;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ytec.mdm.interfaces.socket.http.tools.HttpMethod;
import com.ytec.mdm.interfaces.socket.http.tools.HttpRequest;
import com.ytec.mdm.interfaces.socket.http.tools.HttpUtils;
import com.ytec.mdm.interfaces.socket.http.tools.HttpVersion;
import com.ytec.mdm.interfaces.socket.http.tools.RequestTooLargeException;

/**
 * The Class RequestDecoder.
 * 
 * @项目名称：ytec-mdm-ecif
 * @类名称： RequestDecoder
 * @类描述：
 * @功能描述:
 * @创建人： wangzy1@yuchengtech.com
 * @创建时间：2014-5-30 11:57:34
 * @修改人： wangzy1@yuchengtech.com
 * @修改时间：2014-5-30 11:57:34
 * @修改备注：
 * @修改日期 修改人员 修改原因
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 */
public class RequestDecoder {
	
	/**
	 * The log.
	 * 
	 * @属性描述:
	 */
	private static Logger log = LoggerFactory.getLogger(RequestDecoder.class);
    
    /**
	 * The Enum State.
	 * 
	 * @项目名称：ytec-mdm-ecif
	 * @类名称： RequestDecoder
	 * @类描述：
	 * @功能描述:
	 * @创建人： wangzy1@yuchengtech.com
	 * @创建时间：2014-5-30 11:57:34
	 * @修改人： wangzy1@yuchengtech.com
	 * @修改时间：2014-5-30 11:57:34
	 * @修改备注：
	 * @修改日期 修改人员 修改原因
	 * @version 1.0.0
	 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
	 */
    public enum State {
        
        /**
		 * The AL l_ read.
		 * 
		 * @属性描述:
		 */
        ALL_READ, 
 /**
	 * The REA d_ initial.
	 * 
	 * @属性描述:
	 */
 READ_INITIAL, 
 /**
	 * The REA d_ header.
	 * 
	 * @属性描述:
	 */
 READ_HEADER, 
 /**
	 * The REA d_ fixe d_ lengt h_ content.
	 * 
	 * @属性描述:
	 */
 READ_FIXED_LENGTH_CONTENT, 
 /**
	 * The REA d_ chun k_ size.
	 * 
	 * @属性描述:
	 */
 READ_CHUNK_SIZE, 
 /**
	 * The REA d_ chunke d_ content.
	 * 
	 * @属性描述:
	 */
 READ_CHUNKED_CONTENT, 
 /**
	 * The REA d_ chun k_ footer.
	 * 
	 * @属性描述:
	 */
 READ_CHUNK_FOOTER, 
 /**
	 * The REA d_ chun k_ delimiter.
	 * 
	 * @属性描述:
	 */
 READ_CHUNK_DELIMITER
    }

    /**
	 * The state.
	 * 
	 * @属性描述:
	 */
    private State state = State.READ_INITIAL;
    
    /**
	 * The read remaining.
	 * 
	 * @属性描述:
	 */
    private int readRemaining = 0; 	// bytes need read
    
    /**
	 * The read count.
	 * 
	 * @属性描述:
	 */
    private int readCount = 0; 		// already read bytes count

    /**
	 * The request.
	 * 
	 * @属性描述:
	 */
    HttpRequest request; // package visible
    
    /**
	 * The headers.
	 * 
	 * @属性描述:
	 */
    private Map<String, String> headers = new TreeMap<String, String>();
    
    /**
	 * The content.
	 * 
	 * @属性描述:
	 */
    byte[] content;

    /**
	 * The line buffer idx.
	 * 
	 * @属性描述:
	 */
    int lineBufferIdx = 0;
    // 1k buffer, increase as necessary;
    /**
	 * The line buffer.
	 * 
	 * @属性描述:
	 */
    private byte[] lineBuffer = new byte[1024];

    /**
	 * The max body.
	 * 
	 * @属性描述:
	 */
    private final int maxBody;
    
    /**
	 * The max line.
	 * 
	 * @属性描述:
	 */
    private final int maxLine;

    /**
	 * The Constructor.
	 * 
	 * @param maxBody
	 *            the max body
	 * @param maxLine
	 *            the max line
	 * @构造函数 request decoder
	 */
    public RequestDecoder(int maxBody, int maxLine) {
        this.maxBody = maxBody;
        this.maxLine = maxLine;
    }

    /**
	 * Creates the request.
	 * 
	 * @param sb
	 *            the sb
	 * @throws Exception
	 *             the exception
	 * @函数名称:void createRequest(String sb)
	 * @函数描述:
	 * @参数与返回说明: void createRequest(String sb)
	 * @算法描述:
	 */
    private void createRequest(String sb) throws Exception {
        int aStart;
        int aEnd;
        int bStart;
        int bEnd;
        int cStart;
        int cEnd;

        aStart = findNonWhitespace(sb, 0);
        aEnd = findWhitespace(sb, aStart);

        bStart = findNonWhitespace(sb, aEnd);
        bEnd = findWhitespace(sb, bStart);

        cStart = findNonWhitespace(sb, bEnd);
        cEnd = findEndOfString(sb);
        
        if (cStart < cEnd) {
            try {
                HttpMethod method = HttpMethod.valueOf(sb.substring(aStart, aEnd).toUpperCase());
                HttpVersion version = HTTP_1_1;
                if ("HTTP/1.0".equals(sb.substring(cStart, cEnd))) {
                    version = HTTP_1_0;
                }
                request = new HttpRequest(method, sb.substring(bStart, bEnd), version);
            } catch (Exception e) {
            	log.error("method not understand", e);
                throw new Exception("method not understand");
            }
        } else {
            throw new Exception("not http?");
        }
    }

    /**
	 * Decode.
	 * 
	 * @param buffer
	 *            the buffer
	 * @return the http request
	 * @throws Exception
	 *             the exception
	 * @函数名称:HttpRequest decode(ByteBuffer buffer)
	 * @函数描述:
	 * @参数与返回说明: HttpRequest decode(ByteBuffer buffer)
	 * @算法描述:
	 */
    public HttpRequest decode(ByteBuffer buffer) throws Exception {
        String line;
        while (buffer.hasRemaining()) {
            switch (state) {
            case ALL_READ:
                return request;
            case READ_INITIAL:
                line = readLine(buffer);
                if (line != null && !line.isEmpty()) {
                    createRequest(line);
                    state = State.READ_HEADER;
                }
                break;
            case READ_HEADER:
                readHeaders(buffer);
                break;
            case READ_CHUNK_SIZE:
                line = readLine(buffer);
                if (line != null) {
                    readRemaining = getChunkSize(line);
                    if (readRemaining == 0) {
                        state = State.READ_CHUNK_FOOTER;
                    } else {
                        throwIfBodyIsTooLarge();
                        if (content == null) {
                            content = new byte[readRemaining];
                        } else if (content.length < readCount + readRemaining) {
                            // *1.3 to protect slow client
                            int newLength = (int) ((readRemaining + readCount) * 1.3);
                            content = Arrays.copyOf(content, newLength);
                        }
                        state = State.READ_CHUNKED_CONTENT;
                    }
                }
                break;
            case READ_FIXED_LENGTH_CONTENT:
                readFixedLength(buffer);
                if (readRemaining == 0) {
                    finish();
                }
                break;
            case READ_CHUNKED_CONTENT:
                readFixedLength(buffer);
                if (readRemaining == 0) {
                    state = State.READ_CHUNK_DELIMITER;
                }
                break;
            case READ_CHUNK_FOOTER:
                readEmptyLine(buffer);
                finish();
                break;
            case READ_CHUNK_DELIMITER:
                readEmptyLine(buffer);
                state = State.READ_CHUNK_SIZE;
                break;
            }
        }
        return state == State.ALL_READ ? request : null;
    }

    /**
	 * Finish.
	 * 
	 * @函数名称:void finish()
	 * @函数描述:
	 * @参数与返回说明: void finish()
	 * @算法描述:
	 */
    private void finish() {
        state = State.ALL_READ;
        request.setBody(content, readCount);
    }

    /**
	 * Read empty line.
	 * 
	 * @param buffer
	 *            the buffer
	 * @函数名称:void readEmptyLine(ByteBuffer buffer)
	 * @函数描述:
	 * @参数与返回说明: void readEmptyLine(ByteBuffer buffer)
	 * @算法描述:
	 */
    void readEmptyLine(ByteBuffer buffer) {
        byte b = buffer.get();
        if (b == CR && buffer.hasRemaining()) {
            buffer.get(); // should be LF
        }
    }

    /**
	 * Read fixed length.
	 * 
	 * @param buffer
	 *            the buffer
	 * @函数名称:void readFixedLength(ByteBuffer buffer)
	 * @函数描述:
	 * @参数与返回说明: void readFixedLength(ByteBuffer buffer)
	 * @算法描述:
	 */
    void readFixedLength(ByteBuffer buffer) {
        int toRead = Math.min(buffer.remaining(), readRemaining);
        buffer.get(content, readCount, toRead);
        readRemaining -= toRead;
        readCount += toRead;
    }

    /**
	 * Read headers.
	 * 
	 * @param buffer
	 *            the buffer
	 * @throws Exception
	 *             the exception
	 * @函数名称:void readHeaders(ByteBuffer buffer)
	 * @函数描述:
	 * @参数与返回说明: void readHeaders(ByteBuffer buffer)
	 * @算法描述:
	 */
    private void readHeaders(ByteBuffer buffer) throws Exception {
        String line = readLine(buffer);
        while (line != null && !line.isEmpty()) {
            HttpUtils.splitAndAddHeader(line, headers);
            line = readLine(buffer);
        }

        if (line == null) {
            return;
        }

        request.setHeaders(headers);

        String te = headers.get(TRANSFER_ENCODING);
        if (CHUNKED.equals(te)) {
            state = State.READ_CHUNK_SIZE;
        } else {
            String cl = headers.get(CONTENT_LENGTH);
            if (cl != null) {
                try {
                    readRemaining = Integer.parseInt(cl);
                    if (readRemaining > 0) {
                        throwIfBodyIsTooLarge();
                        content = new byte[readRemaining];
                        state = State.READ_FIXED_LENGTH_CONTENT;
                    } else {
                        state = State.ALL_READ;
                    }
                } catch (NumberFormatException e) {
                    throw new Exception(e.getMessage());
                }
            } else {
                state = State.ALL_READ;
            }
        }
    }

    /**
	 * Read line.
	 * 
	 * @param buffer
	 *            the buffer
	 * @return the string
	 * @throws Exception
	 *             the exception
	 * @函数名称:String readLine(ByteBuffer buffer)
	 * @函数描述:
	 * @参数与返回说明: String readLine(ByteBuffer buffer)
	 * @算法描述:
	 */
    String readLine(ByteBuffer buffer) throws Exception {
        byte b;
        boolean more = true;
        while (buffer.hasRemaining() && more) {
            b = buffer.get();
            if (b == CR) {
                if (buffer.hasRemaining() && buffer.get() == LF) {
                    more = false;
                }
            } else if (b == LF) {
                more = false;
            } else {
                if (lineBufferIdx == maxLine - 2) {
                    throw new RequestTooLargeException("line length exceed " + lineBuffer.length);
                }
                if (lineBufferIdx == lineBuffer.length) {
                    lineBuffer = Arrays.copyOf(lineBuffer, lineBuffer.length * 2);
                }
                lineBuffer[lineBufferIdx] = b;
                ++lineBufferIdx;
            }
        }
        String line = null;
        if (!more) {
            line = new String(lineBuffer, 0, lineBufferIdx);
            lineBufferIdx = 0;
        }
        return line;
    }

    /**
	 * Reset.
	 * 
	 * @函数名称:void reset()
	 * @函数描述:
	 * @参数与返回说明: void reset()
	 * @算法描述:
	 */
    public void reset() {
        state = State.READ_INITIAL;
        headers = new TreeMap<String, String>();
        readCount = 0;
        content = null;
        lineBufferIdx = 0;
        request = null;
    }

    /**
	 * Throw if body is too large.
	 * 
	 * @throws Exception
	 *             the exception
	 * @函数名称:void throwIfBodyIsTooLarge()
	 * @函数描述:
	 * @参数与返回说明: void throwIfBodyIsTooLarge()
	 * @算法描述:
	 */
    private void throwIfBodyIsTooLarge() throws Exception {
        if (readCount + readRemaining > maxBody) {
            throw new RequestTooLargeException("request body " + (readCount + readRemaining)
                    + "; max request body " + maxBody);
        }
    }
}
