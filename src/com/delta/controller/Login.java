package com.delta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.delta.bean.User;
import com.delta.dao.UserDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import com.delta.util.RequestInputStream;
import com.google.gson.Gson;
import net.sf.json.JSONObject;

public class Login implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Login.class);
	private UserDao userDao;
	private String successPage;
	private String errorPage;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public String getSuccessPage() {
		return successPage;
	}

	public void setSuccessPage(String successPage) {
		this.successPage = successPage;
	}

	public String getErrorPage() {
		return errorPage;
	}

	public void setErrorPage(String errorPage) {
		this.errorPage = errorPage;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "3600");
		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		logger.info("ip:" + value);

		User user = null;
		RequestInputStream requestInputStream = new RequestInputStream();
		String requestString = requestInputStream.requestInput(request);
		logger.info("Login data is:" + requestString.toString());
		Gson gson = new Gson();
		try {
			user = gson.fromJson(requestString, User.class);
		} catch (Exception e) {			
			logger.info(e.toString());
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求数据格式错误");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}
		if (user.getUserName() == null || user.getPassWord() == null) {
			logger.info("The input data is invalid!");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求值无效");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		} else {
			List<User> user1 = userDao.select(user);
			User user2 = userDao.selectByName(user);
			if (user1 == null || user1.size() == 0) {
				logger.info("用户名不存在");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "用户名不存在");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			} else if (user2 != null) {
				logger.info("用户" + user2.getUserName() + "登录成功");
				Integer userId = user2.getId();				
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "登录成功");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			} else {
				logger.info("用户名和密码不匹配");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "用户名密码不匹配");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			}

		}
	}

}
