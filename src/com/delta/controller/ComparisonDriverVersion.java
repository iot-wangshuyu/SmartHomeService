package com.delta.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.delta.bean.DeviceDriver;
import com.delta.bean.InstallDeviceDriver;
import com.delta.dao.DeviceDriverDao;
import com.delta.dao.InstallDeviceDriverDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import com.delta.util.RequestInputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.sf.json.JSONObject;

/**
 * 驱动版本比较
 * 
 * @author Shuyu.Wang
 *
 */
public class ComparisonDriverVersion implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ComparisonDriverVersion.class);
	private DeviceDriverDao deviceDriverDao;
	private InstallDeviceDriverDao installDeviceDriverDao;
	public DeviceDriverDao getDeviceDriverDao() {
		return deviceDriverDao;
	}
	public void setDeviceDriverDao(DeviceDriverDao deviceDriverDao) {
		this.deviceDriverDao = deviceDriverDao;
	}
	public InstallDeviceDriverDao getInstallDeviceDriverDao() {
		return installDeviceDriverDao;
	}
	public void setInstallDeviceDriverDao(InstallDeviceDriverDao installDeviceDriverDao) {
		this.installDeviceDriverDao = installDeviceDriverDao;
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		String strm[] = value.split("\\.");	
		Integer user_id = Integer.valueOf(strm[strm.length - 1]);	
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
		if (jo.containsKey("id")) {
		
			InstallDeviceDriver installDeviceDriver = new InstallDeviceDriver();
			installDeviceDriver.setId(Integer.valueOf(jo.getString("id")));
			installDeviceDriver = installDeviceDriverDao.selectVersion(installDeviceDriver);//查询安装的驱动的版本号
			
			DeviceDriver deviceDriver = new DeviceDriver();
			deviceDriver.setId(installDeviceDriver.getDriver_id());
			deviceDriver = deviceDriverDao.selectVersion(deviceDriver);//查询数据库中驱动的版本号
			
			
			float vs = Float.parseFloat(deviceDriver.getVersion());
			float ves = Float.parseFloat(installDeviceDriver.getDriver_version());
			if (vs > ves) {
				logger.info(installDeviceDriver.getDriver_id() + "drivers are updated");
				Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss.S").create();
				String str = json.toJson(deviceDriver);
				JSONObject ja = JSONObject.fromObject(str);
				return JsonView.Render(ja, response);
			} else {
				logger.info("The driver is not updated!");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "驱动没有更新");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			}
		} else {
			logger.info("The input value is invalid!");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "输入值无效");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}

	}
}
