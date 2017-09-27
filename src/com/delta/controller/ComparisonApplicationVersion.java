package com.delta.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.delta.bean.Application;
import com.delta.bean.InstallApplication;
import com.delta.dao.ApplicationDao;
import com.delta.dao.InstallApplicationDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import com.delta.util.RequestInputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.json.JSONObject;

/**
 * 功能版本比较
 * @author Shuyu.Wang
 *
 */
public class ComparisonApplicationVersion implements Controller{
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ComparisonApplicationVersion.class);
	private ApplicationDao applicationDao;
    private InstallApplicationDao installApplicationDao;

	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

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
		String strm[] = value.split("\\.");;		
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
		if(jo.containsKey("id")){
			InstallApplication installApplication=new InstallApplication();
			installApplication.setId(jo.getInt("id"));			
			installApplication=installApplicationDao.selectVersion(installApplication);//查询安装的功能的版本号
			
			Application application=new Application();
			application.setId(installApplication.getApplication_id());
			application=applicationDao.selectVersion(application);//查询数据库中功能的版本号
			
	
			float vs=Float.parseFloat(application.getVersion());
			float ves=Float.parseFloat(installApplication.getApplication_version());
			if(vs>ves){
				logger.info(installApplication.getApplication_id()+"drivers are updated");
				Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss.S").create();
				String str = json.toJson(application);
				JSONObject ja=JSONObject.fromObject(str);	
			    return JsonView.Render(ja,response);
			}else {
				logger.info("The driver is not updated!");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "应用插件没有更新");
				JSONObject jarr=JSONObject.fromObject(modle);				
				return JsonView.Render(jarr, response);
			}
		}else {
			logger.info("The input value is invalid!");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "输入值无效");
			JSONObject jarr=JSONObject.fromObject(modle);				
			return JsonView.Render(jarr, response);
		}
	
	}
}
