package com.delta.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.delta.util.GetIp;
import com.delta.util.JsonView;

import net.sf.json.JSONObject;

public class Tips implements Controller{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Tips.class);	

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		logger.info("ip:" + value);
		
		 Map<String, String> modle = new HashMap<String, String>();
			modle.put("Tips", "天气转冷，请您注意增添衣服，防止感冒。");
			JSONObject jarr=JSONObject.fromObject(modle);			
			return JsonView.Render(jarr, response);
	}

}
