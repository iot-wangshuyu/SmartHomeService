package com.delta.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
public class RequestInputStream {
	
	public String requestInput(HttpServletRequest request){	
		
		if (request.getContentLength() <= 0) {			
			
		}
		BufferedReader in = null;
		try {
			request.setCharacterEncoding("UTF-8");
			in = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}	
			// logger.info("Request data is:" + sb.toString());			
		} catch (Exception e) {
			e.printStackTrace();
			// logger.info(e.toString());
		}
		
		return sb.toString();
		
	}

}
