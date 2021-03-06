package com.delta.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.delta.bean.InstallApplication;
import com.delta.dao.InstallApplicationDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import com.delta.util.RequestInputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.json.JSONObject;

/**
 * 查看应用详情
 * 
 * @author Shuyu.Wang
 *
 */
public class ApplicationDetails implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ApplicationDetails.class);
	private InstallApplicationDao installApplicationDao;


	public InstallApplicationDao getInstallApplicationDao() {
		return installApplicationDao;
	}


	public void setInstallApplicationDao(InstallApplicationDao installApplicationDao) {
		this.installApplicationDao = installApplicationDao;
	}


	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		logger.info("ip:" + value);
		String strm[] = value.split("\\.");	
		Integer user_id = Integer.valueOf(strm[strm.length - 1]);
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
		if (jo.containsKey("id")) {
			InstallApplication installApplication = installApplicationDao.selectD(Integer.valueOf(jo.getString("id")));
			if (installApplication == null) { // 判断resultList是否有值
				logger.info("Details failed!");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "详情获取失败");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			} else {
				Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss.S").create();
				String str = json.toJson(installApplication);
				logger.info("The data returned is:" + str);
				str = str.replace("[", "");
				str = str.replace("]", "");
				JSONObject ja = JSONObject.fromObject(str);
				return JsonView.Render(ja, response);
			}
		} else {
			logger.info("The input data is invalid!");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求值无效");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}
	}
}
