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

import com.delta.bean.DeviceDriver;
import com.delta.bean.QrCodeInfo;
import com.delta.bean.User;
import com.delta.dao.DeviceDriverDao;
import com.delta.dao.InstallDeviceDao;
import com.delta.dao.UserDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import com.delta.util.RequestInputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;

/**
 * 驱动下载
 * 
 * @author Shuyu.Wang
 *
 */
public class Download implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Download.class);
	private UserDao userDao;

	private DeviceDriverDao deviceDriverDao;
	private InstallDeviceDao installDeviceDao;

	public DeviceDriverDao getDeviceDriverDao() {
		return deviceDriverDao;
	}

	public void setDeviceDriverDao(DeviceDriverDao deviceDriverDao) {
		this.deviceDriverDao = deviceDriverDao;
	}

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

		HttpServletResponse res = (HttpServletResponse) response;// 跨域访问
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "3600");

		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		logger.info("ip:" + value);
		String strm[] = value.split("\\.");
		Integer user_id = Integer.valueOf(strm[strm.length - 1]);
		User user = new User();
		user.setUserName(String.valueOf(user_id));
		user.setPassWord("111");
		user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
		List<User> listUser = userDao.select(user);
		if (listUser == null || listUser.size() == 0) {
			userDao.insert(user);
		}
		User u=userDao.selectByName(user);
		QrCodeInfo qrCodeInfo = null;
		RequestInputStream requestInputStream = new RequestInputStream();
		String requestString = requestInputStream.requestInput(request);
		logger.info("Request data is:" + requestString);
		Gson gson = new Gson();
		try {
			qrCodeInfo = gson.fromJson(requestString, QrCodeInfo.class);
		} catch (Exception e) {
			logger.info(e.toString());
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求数据格式错误");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}

		if (qrCodeInfo == null || qrCodeInfo.getDevice_ID() == null || qrCodeInfo.getDevice_NAME() == null
				|| qrCodeInfo.getVersion() == null || qrCodeInfo.getDescribes() == null || qrCodeInfo.getMac() == null
				|| qrCodeInfo.getType() == null || qrCodeInfo.getVendor() == null || qrCodeInfo.getProtocol() == null) {
			logger.info("The input data is invalid!");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求值无效");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		} else {
			DeviceDriver deviceDriver = new DeviceDriver(); // 由qrCodeInfo映射到deviceDriver
			deviceDriver.setDeviceType(qrCodeInfo.getType());
			deviceDriver.setDeviceVendor(qrCodeInfo.getVendor());
			deviceDriver.setDeviceProtocol(qrCodeInfo.getProtocol());

			logger.info("From the database to find the corresponding results list!");
			List<DeviceDriver> resultList = deviceDriverDao.select(deviceDriver);
			if (resultList == null || resultList.size() == 0) { // 判断resultList是否有值
				logger.info("No matching drivers!");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "没有匹配的驱动");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			} else {
				Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss.S").create();
				String str = json.toJson(resultList);
				qrCodeInfo.setUser_id(u.getId());
				List<QrCodeInfo> result = installDeviceDao.selectBy(qrCodeInfo);
				if (result == null || result.size() == 0) {
					boolean flag = installDeviceDao.insert(qrCodeInfo);
					if (!flag) {
						logger.info("The data returned is:" + str);
						str = str.replace("[", "");
						str = str.replace("]", "");
						JSONObject ja = JSONObject.fromObject(str);
						Map<String, Object> modle = new HashMap<String, Object>();
						modle.put("driver", ja);
						modle.put("time", new Timestamp(System.currentTimeMillis()));
						String strs = json.toJson(modle);
						JSONObject jas = JSONObject.fromObject(strs);
						return JsonView.Render(jas, response);
					} else {
						logger.info("No matching drivers!");
						Map<String, String> modle = new HashMap<String, String>();
						modle.put("info", "设备添加失败");
						JSONObject jarr = JSONObject.fromObject(modle);
						return JsonView.Render(jarr, response);
					}
				} else {
					// 判断设备是否添加过
					// Map<String, String> modle = new HashMap<String,
					// String>();
					// modle.put("info", "该设备已经添加过，不可重复添加");
					// JSONObject jarr = JSONObject.fromObject(modle);
					// return JsonView.Render(jarr, response);

					logger.info("The data returned is:" + str);
					str = str.replace("[", "");
					str = str.replace("]", "");
					JSONObject ja = JSONObject.fromObject(str);
					Map<String, Object> modle = new HashMap<String, Object>();
					modle.put("driver", ja);
					modle.put("time", new Timestamp(System.currentTimeMillis()));
					String strs = json.toJson(modle);
					JSONObject jas = JSONObject.fromObject(strs);
					return JsonView.Render(jas, response);
				}

			}

		}

	}

}
