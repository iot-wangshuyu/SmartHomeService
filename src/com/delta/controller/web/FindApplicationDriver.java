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

import com.delta.bean.ApplicationDeviceDriver;
import com.delta.bean.DeviceDriver;
import com.delta.dao.ApplicationDeviceDriverDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import com.delta.util.RequestInputStream;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FindApplicationDriver implements Controller{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(FindApplicationDriver.class);
	private ApplicationDeviceDriverDao applicationDeviceDriverDao;
	public ApplicationDeviceDriverDao getApplicationDeviceDriverDao() {
		return applicationDeviceDriverDao;
	}
	public void setApplicationDeviceDriverDao(ApplicationDeviceDriverDao applicationDeviceDriverDao) {
		this.applicationDeviceDriverDao = applicationDeviceDriverDao;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "3600");
		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		logger.info("ip:" + value);
		
		RequestInputStream requestInputStream = new RequestInputStream();
		String requestString = requestInputStream.requestInput(request);
		JSONObject jo = null;
		logger.info("request data:"+requestString);
		//判断数据类型
		try {
			jo = JSONObject.fromObject(requestString);
		} catch (Exception e) {			
			logger.info(e.toString());
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求数据格式错误");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}
		if(jo.containsKey("application_id")){
			List<DeviceDriver> list=applicationDeviceDriverDao.selectByA(jo.getInt("application_id"));
			JSONObject obj = new JSONObject();
			try {
				JSONArray jrr = JSONArray.fromObject(list);
				ArrayList<String> arlist = new ArrayList<String>();
				for (int i = 0; i < jrr.size(); i++) {
					arlist.add(jrr.getJSONObject(i).getString("name"));
				}
				obj.put("drivers", arlist);
			} catch (Exception e) {						
				e.printStackTrace();
			}
			logger.info("The position data returned is:" + obj.toString());
			JSONObject jarr = JSONObject.fromObject(obj);
			return JsonView.Render(jarr, response);
		}else {
			logger.info("Information saved failed");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求值无效");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}
	}
	

}
