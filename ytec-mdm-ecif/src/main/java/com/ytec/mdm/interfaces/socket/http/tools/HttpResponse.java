package com.ytec.mdm.interfaces.socket.http.tools;

public class HttpResponse {
	public static final int OK = 200;
	public static final int Bad_Request = 400;
	public static final int Unauthorized = 401;
	public static final int Forbidden = 403;
	public static final int Not_Found = 404;
	public static final int Request_Entity_Too_Large = 413;
	public static final int Internal_Server_Error = 500;
	public static final int Not_Implemented = 501;
	public static final int Service_Unavailable = 503;
	
	public static String describe(int code) {
		switch(code) {
		case OK:
			return "OK";
		case Bad_Request:
			return "Bad Request";
		case Unauthorized:
			return "Unauthorized";
		case Forbidden:
			return "Forbidden";
		case Not_Found:
			return "Not Found";
		case Request_Entity_Too_Large:
			return "Request Entity Too Large";
		case Internal_Server_Error:
			return "Internal Server Error";
		case Not_Implemented:
			return "Not Implemented";
		case Service_Unavailable:
			return "Service Unavailable";
		default:
			return "Unknown Code";
		}
	}
}
