package com.yuchengtech.emp.ecif.base.util;

import java.io.BufferedReader;  
import java.io.IOException; 
import java.io.InputStream;  
import java.io.InputStreamReader;  
import ch.ethz.ssh2.Connection; 
import ch.ethz.ssh2.Session;  
import ch.ethz.ssh2.StreamGobbler;  
public class LogonSSH  
{  
public static void main(String[] args)  
{  
String hostname = "192.168.1.98";  
String username = "ecif";  
String password = "ecif";  
try  
{  
/* Create a connection instance */  
Connection conn = new Connection(hostname);  
/* Now connect */  
conn.connect();  
/* Authenticate */  
boolean isAuthenticated = conn.authenticateWithPassword(username, password);  
if (isAuthenticated == false)  
throw new IOException("Authentication failed.");  
/* Create a session */  
Session sess = conn.openSession();  
//sess.execCommand("uname -a && date && uptime && who");  
sess.execCommand("ps aux ");  
System.out.println("Here is some information about the remote host:");  
InputStream stdout = new StreamGobbler(sess.getStdout());  
BufferedReader br = new BufferedReader(new InputStreamReader(stdout));  
while (true)  
{  
String line = br.readLine();  
if (line == null)  
break;  
System.out.println(line);  
}  
/* Show exit status, if available (otherwise "null") */  
System.out.println("ExitCode: " + sess.getExitStatus());  
/* Close this session */  
sess.close();  
/* Close the connection */  
conn.close();  
}  
catch (IOException e)  
{  
e.printStackTrace(System.err); System.exit(2);  
}  
}  
}