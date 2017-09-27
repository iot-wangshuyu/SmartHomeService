package com.delta.controller.web;

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
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 网页查找已上传的设备驱动
 * 
 * @author Shuyu.Wang
 *
 */
public class FindDeviceDriver implements Controller {
	private static final long serialVersionUID = 1L;
	private static final int PER_PAGE =3;
	private static Logger logger = Logger.getLogger(FindDeviceDriver.class);
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
		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		logger.info("ip:" + value);

		List<DeviceDriver> deviceDriver = deviceDriverDao.findAll();

		if (deviceDriver == null || deviceDriver.size() == 0) { // 判断resultList是否有值
			logger.info("No device drivers installed!");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "没有上传过驱动");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		} else {

			// 分页 数据源 当前得到第几页的记录 每页显示多少条
			int page = Integer.parseInt(request.getParameter("page"));
			int totalpage = 0;
			if (deviceDriver.size() % PER_PAGE == 0) {
				totalpage = deviceDriver.size() / PER_PAGE;
			} else {
				totalpage = (deviceDriver.size() / PER_PAGE) + 1;
			}
			int length = 0;// 记录当前拿了多少条
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			String message = null;
			if (page >= 1 && page <= 3) {
				for (int i = (page - 1) * PER_PAGE; i < deviceDriver.size() && length < PER_PAGE; i++) {
					DeviceDriver u = deviceDriver.get(i);
					sb.append(u.toString() + ",");
					length++;
				}
				if (length > 0) {
					message = sb.substring(0, sb.length() - 1) + "]";
				} else {
					message = sb.toString() + "]";
				}
			} else {
				logger.info("No application installed!");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "分页错误");
				JSONObject ja = JSONObject.fromObject(modle);
				return JsonView.Render(ja, response);
			}
			// Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd
			// HH:mm:ss.S").create();
			// String str = json.toJson(message);
			JSONArray jsonArray = JSONArray.fromObject(message);
			logger.info("The data returned is:" + message);
			Map<String, Object> modle = new HashMap<String, Object>();
			modle.put("deviceDriverList", jsonArray);
			JSONObject ja = JSONObject.fromObject(modle);
			return JsonView.Render(ja, response);

		}
	}
}
