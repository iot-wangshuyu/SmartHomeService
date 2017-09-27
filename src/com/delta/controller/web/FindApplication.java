package com.delta.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.delta.bean.Application;
import com.delta.bean.ApplicationUnion;
import com.delta.dao.ApplicationDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 网页查询以创建的应用
 * 
 * @author Shuyu.Wang
 *
 */
public class FindApplication implements Controller {
	private static final long serialVersionUID = 1L;
	private static final int PER_PAGE = 3;
	private static Logger logger = Logger.getLogger(FindApplication.class);
	private ApplicationDao applicationDao;

	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "3600");
		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		logger.info("ip:" + value);

		List<ApplicationUnion> list = applicationDao.findAll();
		if (list == null || list.size() == 0) { // 判断resultList是否有值
			logger.info("No application created!");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "没有创建应用");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		} else {
			// 分页 数据源 当前得到第几页的记录 每页显示多少条
			int page = Integer.parseInt(request.getParameter("page"));
			int totalpage = 0;
			if (list.size() % PER_PAGE == 0) {
				totalpage = list.size() / PER_PAGE;
			} else {
				totalpage = (list.size() / PER_PAGE) + 1;
			}
			int length = 0;// 记录当前拿了多少条
			StringBuffer sb = new StringBuffer();
			sb.append("[");
			String message = null;
			if (page >= 1 && page <= totalpage) {
				for (int i = (page - 1) * PER_PAGE; i < list.size() && length < PER_PAGE; i++) {
					ApplicationUnion u = list.get(i);
					sb.append(u.toString() + ",");
					length++;
				}
				if (length > 0) {
					message = sb.substring(0, sb.length() - 1) + "]";
				} else {
					message = sb.toString() + "]";
				}
			} else {
				logger.info("Pagination error!");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "分页错误");
				JSONObject ja = JSONObject.fromObject(modle);
				return JsonView.Render(ja, response);
			}
			// Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd
			// HH:mm:ss.S").create();
			// String str = json.toJson(list);
			JSONArray jsonArray = JSONArray.fromObject(message);
			logger.info("ApplicationList:" + message);
			Map<String, Object> modle = new HashMap<String, Object>();
			modle.put("totalpage", totalpage);
			modle.put("ApplicationList", jsonArray);
			JSONObject ja = JSONObject.fromObject(modle);
			return JsonView.Render(ja, response);

		}
	}

}
