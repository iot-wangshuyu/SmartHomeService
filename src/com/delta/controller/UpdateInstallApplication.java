package com.delta.controller;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.delta.bean.InstallApplication;
import com.delta.bean.User;
import com.delta.dao.InstallApplicationDao;
import com.delta.dao.UserDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import com.delta.util.RequestInputStream;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

/**
 * 更新安装的应用
 * @author Shuyu.Wang
 *
 */
public class UpdateInstallApplication implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UpdateInstallApplication.class);
	private InstallApplicationDao installApplicationDao;
	private UserDao userDao;
	public InstallApplicationDao getInstallApplicationDao() {
		return installApplicationDao;
	}

	public void setInstallApplicationDao(InstallApplicationDao installApplicationDao) {
		this.installApplicationDao = installApplicationDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		logger.info("ip:" + value);
		String strm[] = value.split("\\.");	
		Integer user_id = Integer.valueOf(strm[strm.length - 1]);
		User user = new User();
		user.setUserName(String.valueOf(user_id));
		user.setPassWord("111");
		User u=userDao.selectByName(user);
		RequestInputStream requestInputStream = new RequestInputStream();
		String requestString = requestInputStream.requestInput(request);
		InstallApplication installApplication = new InstallApplication();
		logger.info("request data:"+requestString);
		Gson gson = new Gson();
		try {
			installApplication = gson.fromJson(requestString, InstallApplication.class);
		} catch (Exception e) {			
			logger.info(e.toString());
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求数据格式错误");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}
		if (installApplication==null||installApplication.getId() == null||installApplication.getApplication_version() == null) {
			logger.info("The input data is invalid!");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求值无效!");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		} else {
			installApplication.setUser_id(user_id);
			installApplication.setSetupTime(new Timestamp(System.currentTimeMillis()));
			logger.info("Update the driver version in the database!");
			boolean flag = installApplicationDao.updateVersion(installApplication);//更新应用插件版本
			if (!flag) {
				logger.info("The application plug-ins update was successful!");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "应用插件更新成功");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			} else {
				logger.info("Application plug-ins update failed!");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "应用插件更新失败");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			}
		}
	}
}
