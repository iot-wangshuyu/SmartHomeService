package com.delta.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.delta.bean.DeviceDriver;
import com.delta.dao.DeviceDriverDao;
import com.delta.util.JsonView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 创建应用时查找所有设备驱动
 * 
 * @author Shuyu.Wang
 *
 */
public class SelectDeviceDriver implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SelectDeviceDriver.class);
	private DeviceDriverDao deviceDriverDao;

	public DeviceDriverDao getDeviceDriverDao() {
		return deviceDriverDao;
	}

	public void setDeviceDriverDao(DeviceDriverDao deviceDriverDao) {
		this.deviceDriverDao = deviceDriverDao;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "3600");
		logger.info("Find ");
		List<DeviceDriver> deviceDriver = deviceDriverDao.findAll();

		if (deviceDriver == null || deviceDriver.size() == 0) {
			logger.info("No device drivers installed!");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "没有设备驱动");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		} else {
			Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss.S").create();
			String str = json.toJson(deviceDriver);
/*
			JSONObject obj = new JSONObject();

			try {
				JSONArray jrr = JSONArray.fromObject(str);
				ArrayList<String> list = new ArrayList<String>();
				for (int i = 0; i < jrr.size(); i++) {
					list.add(jrr.getJSONObject(i).getString("name"));
				}
				obj.put("driverName", list);
			} catch (Exception e) {				
				e.printStackTrace();
			}*/
			JSONArray jsonArray=JSONArray.fromObject(str);
			logger.info("The data returned is:" + str);
			Map<String, Object> modle = new HashMap<String,Object>();
			modle.put("driverList",jsonArray);
			JSONObject ja = JSONObject.fromObject(modle);
			return JsonView.Render(ja, response);
		}

	}

}
