package com.yuchengtech.bcrm.nioclient;

import static com.yuchengtech.bcrm.http.HttpUtils.BUFFER_SIZE;
import static com.yuchengtech.bcrm.http.HttpUtils.CHUNKED;
import static com.yuchengtech.bcrm.http.HttpUtils.CONTENT_LENGTH;
import static com.yuchengtech.bcrm.http.HttpUtils.CR;
import static com.yuchengtech.bcrm.http.HttpUtils.LF;
import static com.yuchengtech.bcrm.http.HttpUtils.TRANSFER_ENCODING;
import static com.yuchengtech.bcrm.http.HttpUtils.findEndOfString;
import static com.yuchengtech.bcrm.http.HttpUtils.findNonWhitespace;
import static com.yuchengtech.bcrm.http.HttpUtils.findWhitespace;
import static com.yuchengtech.bcrm.http.HttpUtils.getChunkSize;
import static com.yuchengtech.bcrm.http.HttpVersion.HTTP_1_0;
import static com.yuchengtech.bcrm.http.HttpVersion.HTTP_1_1;

import java.nio.ByteBuffer;
import java.util.Map;
import java.util.TreeMap;

import com.yuchengtech.bcrm.http.DynamicBytes;
import com.yuchengtech.bcrm.http.HttpStatus;
import com.yuchengtech.bcrm.http.HttpUtils;
import com.yuchengtech.bcrm.http.HttpVersion;
import com.yuchengtech.bcrm.http.ProtocolException;


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
	
	
	private DynamicBytes body = new DynamicBytes(MAX_LINE);
	public int contentLength = 0;
	private HttpStatus resStatus;

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
				resStatus = s;
				state = State.READ_HEADER;
			} catch (NumberFormatException e) {
				throw new ProtocolException("not http protocol? " + sb);
			}
		} else {
			throw new ProtocolException("not http protocol? " + sb);
		}
	}

	public boolean decode(ByteBuffer buffer) throws RequestTooLargeException, ProtocolException {
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

	public String  getBody() {
		if (body != null) {
    		return new String(body.get(),0,body.length());
    	}
    	return null;
	}

	public int getContentLength() {
		return contentLength;
	}

	public HttpStatus getResStatus() {
		return resStatus;
	}

}
