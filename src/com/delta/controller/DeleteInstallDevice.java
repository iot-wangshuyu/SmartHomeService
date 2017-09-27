package com.delta.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.delta.bean.QrCodeInfo;
import com.delta.bean.User;
import com.delta.dao.InstallDeviceDao;
import com.delta.dao.UserDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import com.delta.util.RequestInputStream;

import net.sf.json.JSONObject;

public class DeleteInstallDevice implements Controller{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DeleteInstallDevice.class);
	private InstallDeviceDao installDeviceDao;
	private UserDao userDao;
	public InstallDeviceDao getInstallDeviceDao() {
		return installDeviceDao;
	}
	public void setInstallDeviceDao(InstallDeviceDao installDeviceDao) {
		this.installDeviceDao = installDeviceDao;
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
		JSONObject jo = null;
		logger.info("request data:"+requestString);
		try {
			jo = JSONObject.fromObject(requestString);			
		} catch (Exception e) {
			logger.info(e.toString());
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求数据格式错误");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}
		if(jo.containsKey("device_id")){
			logger.info("Request id is:" + jo.getString("device_id"));
			QrCodeInfo qrCodeInfo = new QrCodeInfo();
			qrCodeInfo.setUser_id(u.getId());
			qrCodeInfo.setId(Integer.valueOf(jo.getString("device_id")));
			boolean flag = installDeviceDao.delete(qrCodeInfo);
			if (!flag) {
				logger.info("successfully deleted");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "删除成功");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);

			} else {
				logger.info("failed to delete");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "删除失败");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			}
		}else {
			logger.info("The input data is invalid!");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求值无效");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}
	}

}
