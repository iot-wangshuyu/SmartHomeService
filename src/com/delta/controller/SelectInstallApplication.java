package com.delta.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 查找已安装的应用列表
 * 
 * @author Shuyu.Wang
 *
 */
public class SelectInstallApplication implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(SelectInstallApplication.class);
	private InstallApplicationDao installApplicationDao;
	private UserDao userDao;
	private static final int PER_PAGE = 3;

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
		System.out.println("用户名"+user_id);
		User us = userDao.selectByName(user);
		System.out.println(us.getUserName());
		
		InstallApplication installApplication = new InstallApplication();
		installApplication.setUser_id(us.getId());
		logger.info("Find the devices in the database");
		List<InstallApplication> resultList = installApplicationDao.select(installApplication);

		if (resultList == null || resultList.size() == 0) { // 判断resultList是否有值
			logger.info("No application installed!");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "没有安装应用");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		} else {
			if (request.getParameter("page") == null) {
				logger.info("No request.getParameter!");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "没有页数请求");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			} else {
				// 分页 数据源 当前得到第几页的记录 每页显示多少条
				int page = Integer.parseInt(request.getParameter("page"));

				// int page = 2;
				int length = 0;// 记录当前拿了多少条
				StringBuffer sb = new StringBuffer();
				sb.append("[");
				String message = null;
				if (page >= 1 && page <= 3) {
					for (int i = (page - 1) * PER_PAGE; i < resultList.size() && length < PER_PAGE; i++) {
						InstallApplication u = resultList.get(i);
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
				JSONArray jsonArray = JSONArray.fromObject(message);
				logger.info("The data returned is:" + message);
				Map<String, Object> modle = new HashMap<String, Object>();
				modle.put("applicationList", jsonArray);
				JSONObject ja = JSONObject.fromObject(modle);
				return JsonView.Render(ja, response);

			}

		}

	}

}
