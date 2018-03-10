package com.cnsyear.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HttpUrlConnectServlet
 */
@WebServlet("/HttpUrlConnectServlet")
public class HttpUrlConnectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String name = request.getParameter("name");
		 System.out.println(name);
		 
		 String data = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:say xmlns:ns2=\"http://server.cnsyear.com/\"><arg0>"+name+"</arg0></ns2:say></soap:Body></soap:Envelope>";
		 URL url = new URL("http://localhost:8080/day19/helloWs");
		 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		 connection.setRequestMethod("POST");
		 connection.setDoInput(true);
		 connection.setDoOutput(true);
		 connection.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
		 
		 OutputStream os = connection.getOutputStream();
		 os.write(data.getBytes("utf-8"));
		 
		 int code = connection.getResponseCode();
		 if(code == 200) {
			 InputStream is = connection.getInputStream();//String xml 
			 System.out.println("available ::"+is.available());
			 
			 response.setContentType("text/text;charset=utf-8");
			 ServletOutputStream outputStream = response.getOutputStream();

			 byte[] buffer = new byte[1024];
			 int len = 0;
			 while((len = is.read(buffer))>0) {
				 outputStream.write(buffer,0,len);
			 }
			 
			 outputStream.flush();
			 
		 }
		 
		 
		 
	}

}
