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
import com.delta.bean.InstallApplicationDevice;
import com.delta.bean.User;
import com.delta.dao.InstallApplicationDao;
import com.delta.dao.InstallApplicationDeviceDao;
import com.delta.dao.UserDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import com.delta.util.RequestInputStream;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

/**
 * 保存安装的功能信息
 * @author Shuyu.Wang
 *
 */
public class SaveInstallApplication implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SaveInstallApplication.class);
	private InstallApplicationDao installApplicationDao;
	private InstallApplicationDeviceDao installApplicationDeviceDao;
	private UserDao userDao;
	public InstallApplicationDao getInstallApplicationDao() {
		return installApplicationDao;
	}
	public void setInstallApplicationDao(InstallApplicationDao installApplicationDao) {
		this.installApplicationDao = installApplicationDao;
	}


	public InstallApplicationDeviceDao getInstallApplicationDeviceDao() {
		return installApplicationDeviceDao;
	}
	public void setInstallApplicationDeviceDao(InstallApplicationDeviceDao installApplicationDeviceDao) {
		this.installApplicationDeviceDao = installApplicationDeviceDao;
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
		InstallApplication installApplication = null;
		logger.info("request data:"+requestString);
		Gson gson = new Gson();
		JSONObject jo = JSONObject.fromObject(requestString);
		try {
			installApplication = gson.fromJson(requestString, InstallApplication.class);
		} catch (Exception e) {			
			logger.info(e.toString());
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求数据格式错误");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}
		if (installApplication == null || installApplication.getPosition() == null|| installApplication.getSpeed() == null 
				||installApplication.getApplication_id() == null ||installApplication.getApplication_version() == null||!jo.containsKey("deviceID")) {
			System.out.println(installApplication.getPosition()+ installApplication.getSpeed()+installApplication.getApplication_id()+installApplication.getApplication_version());
			logger.info("The input data is invalid!");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求值无效!");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		} else {
			installApplication.setUser_id(u.getId());
			installApplication.setSetupTime(new Timestamp(System.currentTimeMillis()));
			boolean flag = installApplicationDao.insert(installApplication);
			if (!flag) {								
				InstallApplication id=installApplicationDao.selectId();
				InstallApplicationDevice installApplicationDevice=new InstallApplicationDevice();
				installApplicationDevice.setUser_id(u.getId());
				//TODO  判断deviceID字段是否存在
				installApplicationDevice.setDevice_ID(jo.getString("deviceID"));
				installApplicationDevice.setInstallApplication_id(id.getId());
				boolean flags=installApplicationDeviceDao.insert(installApplicationDevice);
				
				if (!flags) {
					logger.info("The application information is saved successfully！");
					Map<String, String> modle = new HashMap<String, String>();
					modle.put("info", "数据保存成功 ");// 保存失败
					JSONObject jarr = JSONObject.fromObject(modle);
					return JsonView.Render(jarr, response);
				} else {
					installApplicationDao.delete(id);
					logger.info("Application information failed to be saved！");
					Map<String, String> modle = new HashMap<String, String>();
					modle.put("info", "数据保存失败 ");// 保存失败
					JSONObject jarr = JSONObject.fromObject(modle);
					return JsonView.Render(jarr, response);
				}
				
			} else {
				logger.info("Application information failed to be saved！");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "diyitiao数据保存失败 ");// 保存失败
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			}

		}

	}

}
