/**
 * @项目名：ytec-mdm-ecif
 * @包名：com.ytec.mdm.interfaces.socket.http.client
 * @文件名：ResponseDecoder.java
 * @版本信息：1.0.0
 * @日期：2014-5-30-11:57:12
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
package com.ytec.mdm.interfaces.socket.http.client;

import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.BUFFER_SIZE;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.CHUNKED;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.CONTENT_LENGTH;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.CR;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.LF;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.TRANSFER_ENCODING;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.findEndOfString;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.findNonWhitespace;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.findWhitespace;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpUtils.getChunkSize;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpVersion.HTTP_1_0;
import static com.ytec.mdm.interfaces.socket.http.tools.HttpVersion.HTTP_1_1;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.TreeMap;
import com.ytec.mdm.interfaces.socket.http.tools.DynamicBytes;
import com.ytec.mdm.interfaces.socket.http.tools.HttpStatus;
import com.ytec.mdm.interfaces.socket.http.tools.HttpUtils;
import com.ytec.mdm.interfaces.socket.http.tools.HttpVersion;
import com.ytec.mdm.interfaces.socket.http.tools.ProtocolException;
import com.ytec.mdm.interfaces.socket.http.tools.RequestTooLargeException;

/**
 * @项目名称：ytec-mdm-ecif 
 * @类名称：ResponseDecoder
 * @类描述：HTTP解析
 * @功能描述:
 * @创建人：wangzy1@yuchengtech.com
 * @创建时间：2014-5-30 上午11:56:49   
 * @修改人：wangzy1@yuchengtech.com
 * @修改时间：2014-5-30 上午11:56:49
 * @修改备注：
 * @修改日期		修改人员		修改原因
 * --------    	--------	----------------------------------------
 * @version 1.0.0
 * @Copyright (c) 2014北京宇信易诚科技有限公司-版权所有
 * 
 */
public class ResponseDecoder {
	public static final int MAX_LINE = 4096;
	public enum State {
		ALL_READ, READ_CHUNK_DELIMITER, READ_CHUNK_FOOTER, READ_CHUNK_SIZE, READ_CHUNKED_CONTENT, READ_FIXED_LENGTH_CONTENT, READ_HEADER, READ_INITIAL, READ_VARIABLE_LENGTH_CONTENT
	}
	private final Map<String, String> headers = new TreeMap<String, String>();
	final byte[] lineBuffer = new byte[MAX_LINE];
	int lineBufferCnt = 0;
	int readRemaining = 0;
	private State state = State.READ_INITIAL;
	
	
	private DynamicBytes body=new DynamicBytes(MAX_LINE);
	public int contentLength = 0;
	private HttpStatus resStatus;
	private Charset charSet;

	public ResponseDecoder(){
	}

	private void parseInitialLine(String sb) throws ProtocolException{
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

		if ((cStart < cEnd)
				|| (cStart == cEnd && bStart < bEnd)){
			try {
				int status = Integer.parseInt(sb.substring(bStart, bEnd));
				HttpStatus s = HttpStatus.valueOf(status);
				HttpVersion version = HTTP_1_1;
				if ("HTTP/1.0".equals(sb.substring(aStart, aEnd))) {
					version = HTTP_1_0;
				}
				resStatus=s;
				state = State.READ_HEADER;
			} catch (NumberFormatException e) {
				throw new ProtocolException("not http protocol? " + sb);
			}
		} else {
			throw new ProtocolException("not http protocol? " + sb);
		}
	}

	public boolean decode(ByteBuffer buffer) throws RequestTooLargeException, ProtocolException{
		String line;
		int toRead;
		byte[] bodyBuffer = new byte[BUFFER_SIZE];
		while (buffer.hasRemaining() && state != State.ALL_READ) {
			switch (state) {
			case READ_INITIAL:
				line = readLine(buffer);
				if (line != null) {
					parseInitialLine(line);
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
						state = State.READ_CHUNKED_CONTENT;
					}
				}

				break;
			case READ_FIXED_LENGTH_CONTENT:
				toRead = Math.min(buffer.remaining(), readRemaining);
				buffer.get(bodyBuffer, 0, toRead);
				readRemaining -= toRead;
				if (readRemaining == 0) {
					state = State.ALL_READ;
				}
				body.append(bodyBuffer, toRead);
				break;
			case READ_CHUNKED_CONTENT:
				toRead = Math.min(buffer.remaining(), readRemaining);
				buffer.get(bodyBuffer, 0, toRead);
				readRemaining -= toRead;
				if (readRemaining == 0) {
					state = State.READ_CHUNK_DELIMITER;
				}
				body.append(bodyBuffer, toRead);
				break;
			case READ_CHUNK_FOOTER:
				readEmptyLine(buffer);
				state = State.ALL_READ;
				break;
			case READ_CHUNK_DELIMITER:
				readEmptyLine(buffer);
				state = State.READ_CHUNK_SIZE;
				break;
			case READ_VARIABLE_LENGTH_CONTENT:
				toRead = buffer.remaining();
				buffer.get(bodyBuffer, 0, toRead);
				break;
			}
		}
		if(state == State.ALL_READ){
			charSet=HttpUtils.detectCharset(headers,body);
		}
		return state == State.ALL_READ ? true : false;
	}

	void readEmptyLine(ByteBuffer buffer) {
		byte b = buffer.get();
		if (b == CR && buffer.hasRemaining()) {
			buffer.get(); // should be LF
		}
	}

	private void readHeaders(ByteBuffer buffer) throws RequestTooLargeException {
		String line = readLine(buffer);
		while (line != null && !line.isEmpty()) {
			HttpUtils.splitAndAddHeader(line, headers);
			line = readLine(buffer);
		}
		if (line == null)
			return;

		String te = headers.get(TRANSFER_ENCODING);
		if (CHUNKED.equals(te)) {
			state = State.READ_CHUNK_SIZE;
		} else {
			String cl = headers.get(CONTENT_LENGTH);
			if (cl != null) {
				readRemaining = Integer.parseInt(cl);
				contentLength=readRemaining;
				if (readRemaining == 0) {
					state = State.ALL_READ;
				} else {
					state = State.READ_FIXED_LENGTH_CONTENT;
				}
			} else {
				state = State.READ_VARIABLE_LENGTH_CONTENT;
			}
		}

	}

	String readLine(ByteBuffer buffer) throws RequestTooLargeException {
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
				lineBuffer[lineBufferCnt] = b;
				++lineBufferCnt;
				if (lineBufferCnt >= MAX_LINE) {
					throw new RequestTooLargeException("exceed max line " + MAX_LINE);
				}
			}
		}
		String line = null;
		if (!more) {
			line = new String(lineBuffer, 0, lineBufferCnt);
			lineBufferCnt = 0;
		}
		return line;
	}

	public String  getBody(String charSet) throws Exception {
		if(body.length()>0){
			if(this.charSet!=null){
				return new String(body.get(),0,body.length(),this.charSet);
			}else{
				return new String(body.get(),0,body.length(),charSet);
			}
    	}
    	return null;
	}

	public int getContentLength() {
		return contentLength;
	}

	public HttpStatus getResStatus() {
		return resStatus;
	}

	public Charset getCharSet() {
		return charSet;
	}

}
