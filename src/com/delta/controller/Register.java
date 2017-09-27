package com.delta.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class Register implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Register.class);
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		logger.info("ip:" + value);
		User user = null;
		RequestInputStream requestInputStream = new RequestInputStream();
		String requestString = requestInputStream.requestInput(request);
		logger.info("register data is:" + requestString.toString());
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
			user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
			List<User> listUser = userDao.select(user);
			if (listUser == null || listUser.size() == 0) {
				boolean flag = userDao.insert(user);
				if (!flag) {
					logger.info("用户注册成功！");
					Map<String, String> modle = new HashMap<String, String>();
					modle.put("info", "用户注册成功");
					JSONObject jarr = JSONObject.fromObject(modle);
					return JsonView.Render(jarr, response);
				} else {
					logger.info("注册失败！");
					Map<String, String> modle = new HashMap<String, String>();
					modle.put("info", "204 ");
					JSONObject jarr = JSONObject.fromObject(modle);
					return JsonView.Render(jarr, response);
				}

			} else {
				logger.info("用户名已存在！");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "应户名已存在");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			}

		}
	}

}
