package com.delta.controller.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.delta.bean.Application;
import com.delta.dao.ApplicationDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import com.google.gson.Gson;

import net.sf.json.JSONObject;

/**
 * 更新功能
 * @author Shuyu.Wang
 *
 */
public class UpdateApplication implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UpdateApplication.class);
	private static final String fileRoot;
	private final String savePath = "/iOS/Applicationplug-ins/";
	private ApplicationDao applicationDao;


	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	static {
		fileRoot = "D:\\\\SmartHomeiOS\\\\Applicationplug-ins";
		File file = new File(fileRoot);
		if (!file.exists() || !file.isDirectory()) {
			file.delete();
			file.mkdirs();
		}
	}

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "3600");

		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		logger.info("ip:" + value);

		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = new ArrayList<>();
		String fileName = null;
		InputStream fileInputStream = null;
		String fValue = null;
		JSONObject jo = null;
		try {
			items = upload.parseRequest(request);
			Iterator<FileItem> it = items.iterator();// 得到所有的文件
			while (it.hasNext()) {
				FileItem fItem = (FileItem) it.next();
				if (fItem.isFormField()) { // 普通文本框的值ֵ
					fValue = fItem.getString("UTF-8");
					logger.info("Driver Information:" + fValue.toString());
					Gson gson = new Gson(); // 使用Gson获取json数据
					try {
						jo = JSONObject.fromObject(fValue);
					} catch (Exception e) {
						// TODO: handle exception
						logger.info(e.toString());
						Map<String, String> modle = new HashMap<String, String>();
						modle.put("info", "请求数据格式错误");
						JSONObject jarr = JSONObject.fromObject(modle);
						return JsonView.Render(jarr, response);

					}
				} else {
					fileName = fItem.getName(); // 获取上传文件的数据
					fileInputStream = fItem.getInputStream();
				}
			}

		} catch (Exception e) {
			logger.info(e.toString());
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求数据格式错误");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}
		if (jo.containsKey("id") && jo.containsKey("version")) {
			if (fileName == null) {
				logger.info("The input data is invalid!");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "上传的文件不合法");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			} else {
				/* 保存驱动文件到本地磁盘 */
				fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);

				int hashcode = fileName.hashCode();
				int dir1 = hashcode & 0xf; // 0--15
				int dir2 = (hashcode & 0xf0) >> 4; // 0-15
				String filePath = fileRoot + "\\" + dir1 + "\\" + dir2 + "\\";
				File fileSave = new File(filePath);
				// 如果目录不存在
				if (!fileSave.exists()) {
					// 创建目录
					fileSave.mkdirs();
				}
				String realFilePath = filePath + "\\" + fileName;
				String dataSavePath = savePath + dir1 + "/" + dir2 + "/" + fileName;

				File file = new File(realFilePath);
				if (file.isFile() && file.exists()) {
					file.delete();
				}
				FileOutputStream fos = new FileOutputStream(realFilePath);

				try {
					int read = 0;
					byte[] bytes = new byte[1024];
					while ((read = fileInputStream.read(bytes)) != -1) {
						fos.write(bytes, 0, read);
					}
				} catch (IOException ie) {
					logger.info(ie.toString());
					Map<String, String> modle = new HashMap<String, String>();
					modle.put("info", "文件上传失败");
					JSONObject jarr = JSONObject.fromObject(modle);
					return JsonView.Render(jarr, response);
				} finally {

					fos.flush();
					fos.close();

				}
				
				Application application = new Application();
				application.setId(jo.getInt("id"));
				application.setFileName(fileName);
				application.setVersion(jo.getString("version"));
				application.setAddress(dataSavePath);
				application.setBuildTime(new Timestamp(System.currentTimeMillis()));
				boolean flag = applicationDao.update(application);

				if (!flag) {
					logger.info("应用附件更新成功！");
					Map<String, String> modle = new HashMap<String, String>();
					modle.put("info", "200");
					JSONObject jarr = JSONObject.fromObject(modle);
					return JsonView.Render(jarr, response);
				} else {
					File files = new File(realFilePath);
					// 路径为文件且不为空则进行删除
					if (files.isFile() && files.exists()) {
						files.delete();
					}
					Map<String, String> modle = new HashMap<String, String>();
					modle.put("info", "更新信息保存失败，请重试");
					JSONObject jarr = JSONObject.fromObject(modle);
					return JsonView.Render(jarr, response);
				}

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
