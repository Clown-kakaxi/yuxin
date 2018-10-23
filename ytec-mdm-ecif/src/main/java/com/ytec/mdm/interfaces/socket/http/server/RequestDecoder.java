/**
 * @��Ŀ����ytec-mdm-ecif
 * @������com.ytec.mdm.interfaces.socket.http.server
 * @�ļ�����RequestDecoder.java
 * @�汾��Ϣ��1.0.0
 * @���ڣ�2014-5-30-11:57:36
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
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
 * @��Ŀ���ƣ�ytec-mdm-ecif
 * @�����ƣ� RequestDecoder
 * @��������
 * @��������:
 * @�����ˣ� wangzy1@yuchengtech.com
 * @����ʱ�䣺2014-5-30 11:57:34
 * @�޸��ˣ� wangzy1@yuchengtech.com
 * @�޸�ʱ�䣺2014-5-30 11:57:34
 * @�޸ı�ע��
 * @�޸����� �޸���Ա �޸�ԭ��
 * @version 1.0.0
 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
 */
public class RequestDecoder {
	
	/**
	 * The log.
	 * 
	 * @��������:
	 */
	private static Logger log = LoggerFactory.getLogger(RequestDecoder.class);
    
    /**
	 * The Enum State.
	 * 
	 * @��Ŀ���ƣ�ytec-mdm-ecif
	 * @�����ƣ� RequestDecoder
	 * @��������
	 * @��������:
	 * @�����ˣ� wangzy1@yuchengtech.com
	 * @����ʱ�䣺2014-5-30 11:57:34
	 * @�޸��ˣ� wangzy1@yuchengtech.com
	 * @�޸�ʱ�䣺2014-5-30 11:57:34
	 * @�޸ı�ע��
	 * @�޸����� �޸���Ա �޸�ԭ��
	 * @version 1.0.0
	 * @Copyright (c) 2014���������׳ϿƼ����޹�˾-��Ȩ����
	 */
    public enum State {
        
        /**
		 * The AL l_ read.
		 * 
		 * @��������:
		 */
        ALL_READ, 
 /**
	 * The REA d_ initial.
	 * 
	 * @��������:
	 */
 READ_INITIAL, 
 /**
	 * The REA d_ header.
	 * 
	 * @��������:
	 */
 READ_HEADER, 
 /**
	 * The REA d_ fixe d_ lengt h_ content.
	 * 
	 * @��������:
	 */
 READ_FIXED_LENGTH_CONTENT, 
 /**
	 * The REA d_ chun k_ size.
	 * 
	 * @��������:
	 */
 READ_CHUNK_SIZE, 
 /**
	 * The REA d_ chunke d_ content.
	 * 
	 * @��������:
	 */
 READ_CHUNKED_CONTENT, 
 /**
	 * The REA d_ chun k_ footer.
	 * 
	 * @��������:
	 */
 READ_CHUNK_FOOTER, 
 /**
	 * The REA d_ chun k_ delimiter.
	 * 
	 * @��������:
	 */
 READ_CHUNK_DELIMITER
    }

    /**
	 * The state.
	 * 
	 * @��������:
	 */
    private State state = State.READ_INITIAL;
    
    /**
	 * The read remaining.
	 * 
	 * @��������:
	 */
    private int readRemaining = 0; 	// bytes need read
    
    /**
	 * The read count.
	 * 
	 * @��������:
	 */
    private int readCount = 0; 		// already read bytes count

    /**
	 * The request.
	 * 
	 * @��������:
	 */
    HttpRequest request; // package visible
    
    /**
	 * The headers.
	 * 
	 * @��������:
	 */
    private Map<String, String> headers = new TreeMap<String, String>();
    
    /**
	 * The content.
	 * 
	 * @��������:
	 */
    byte[] content;

    /**
	 * The line buffer idx.
	 * 
	 * @��������:
	 */
    int lineBufferIdx = 0;
    // 1k buffer, increase as necessary;
    /**
	 * The line buffer.
	 * 
	 * @��������:
	 */
    private byte[] lineBuffer = new byte[1024];

    /**
	 * The max body.
	 * 
	 * @��������:
	 */
    private final int maxBody;
    
    /**
	 * The max line.
	 * 
	 * @��������:
	 */
    private final int maxLine;

    /**
	 * The Constructor.
	 * 
	 * @param maxBody
	 *            the max body
	 * @param maxLine
	 *            the max line
	 * @���캯�� request decoder
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
	 * @��������:void createRequest(String sb)
	 * @��������:
	 * @�����뷵��˵��: void createRequest(String sb)
	 * @�㷨����:
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
	 * @��������:HttpRequest decode(ByteBuffer buffer)
	 * @��������:
	 * @�����뷵��˵��: HttpRequest decode(ByteBuffer buffer)
	 * @�㷨����:
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
	 * @��������:void finish()
	 * @��������:
	 * @�����뷵��˵��: void finish()
	 * @�㷨����:
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
	 * @��������:void readEmptyLine(ByteBuffer buffer)
	 * @��������:
	 * @�����뷵��˵��: void readEmptyLine(ByteBuffer buffer)
	 * @�㷨����:
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
	 * @��������:void readFixedLength(ByteBuffer buffer)
	 * @��������:
	 * @�����뷵��˵��: void readFixedLength(ByteBuffer buffer)
	 * @�㷨����:
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
	 * @��������:void readHeaders(ByteBuffer buffer)
	 * @��������:
	 * @�����뷵��˵��: void readHeaders(ByteBuffer buffer)
	 * @�㷨����:
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
	 * @��������:String readLine(ByteBuffer buffer)
	 * @��������:
	 * @�����뷵��˵��: String readLine(ByteBuffer buffer)
	 * @�㷨����:
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
	 * @��������:void reset()
	 * @��������:
	 * @�����뷵��˵��: void reset()
	 * @�㷨����:
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
	 * @��������:void throwIfBodyIsTooLarge()
	 * @��������:
	 * @�����뷵��˵��: void throwIfBodyIsTooLarge()
	 * @�㷨����:
	 */
    private void throwIfBodyIsTooLarge() throws Exception {
        if (readCount + readRemaining > maxBody) {
            throw new RequestTooLargeException("request body " + (readCount + readRemaining)
                    + "; max request body " + maxBody);
        }
    }
}
