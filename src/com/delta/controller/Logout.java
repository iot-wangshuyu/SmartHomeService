package com.delta.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.delta.util.GetIp;
import com.delta.util.JsonView;
import net.sf.json.JSONObject;

public class Logout implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Logout.class);
	private HttpSession session;

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		logger.info("ip:" + value);

		// HttpSession session = request.getSession(true);
		// // Integer userId=(Integer)session.getAttribute("sessionId");
		// Integer userId = 1;
		// System.out.println(userId);
		//
		// if (userId != null) {
		// System.out.println("移除1");
		// String at = String.valueOf(userId);
		// session.removeAttribute(at);
		// Map<String, String> modle = new HashMap<String, String>();
		// modle.put("info", "退出成功");
		// JSONObject jarr = JSONObject.fromObject(modle);
		// return JsonView.Render(jarr, response);
		//
		// }else {
		// Map<String, String> modle = new HashMap<String, String>();
		// modle.put("info", "用户尚未登录");
		// JSONObject jarr = JSONObject.fromObject(modle);
		// return JsonView.Render(jarr, response);
		// }

		session = request.getSession(false);
		if(session == null){
			String para = request.getParameter("sessionId");
			System.out.println(para);
			String attr = session.getAttribute("sessionId").toString();
			System.out.println(attr);
			
			
			
//			String sessionId = request.getParameter("sessionId");
//			System.out.println(sessionId);
//			HttpSession session = MySessionContext.getSession(sessionId);
//			System.out.println(session);
//			
			if (para != null && para.equals(attr)) {// 如果相等说明是有效的token
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "用户以登录");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			} else {
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "用户没有登录");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			}
		}else {
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "session为空");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}
		

	}

}
