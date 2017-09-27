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
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import com.delta.bean.DeviceDriver;
import com.delta.dao.DeviceDriverDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import com.google.gson.Gson;
import net.sf.json.JSONObject;

public class UploadDriver implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UploadDriver.class);
	private DeviceDriverDao deviceDriverDao;
	private static final String fileRoot;
	private final String savePath = "/iOS/DeviceDriver/";
	public DeviceDriverDao getDeviceDriverDao() {
		return deviceDriverDao;
	}

	public void setDeviceDriverDao(DeviceDriverDao deviceDriverDao) {
		this.deviceDriverDao = deviceDriverDao;
	}
	static {
		fileRoot = "D:\\\\SmartHomeiOS\\\\DeviceDriver";
		File file = new File(fileRoot);
		if (!file.exists() || !file.isDirectory()) {
			file.delete();
			file.mkdirs();
		}
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		res.setHeader("Access-Control-Max-Age", "3600");
		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		logger.info("ip:" + value);

		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
		// upload.setFileSizeMax(1024 * 1024);
		// 设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
		// upload.setSizeMax(1024 * 1024 * 10);
		List<FileItem> items = new ArrayList<>();
		DeviceDriver deviceDriver = null;
		String fileName = null;
		InputStream fileInputStream = null;
		String fValue = null;
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
						deviceDriver = gson.fromJson(fValue, DeviceDriver.class); // 将http请求中的Json转换成UploadFile对象
						
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
					logger.info("file name is:" + fileName);
				}
			}
		} catch (Exception e) {
			logger.info(e.toString());
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求数据格式错误");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}
		if (deviceDriver.getName() == null || deviceDriver.version == null || deviceDriver.getAuthor() == null
				|| deviceDriver.getDeviceVendor()==null || deviceDriver.getDeviceType()==null || deviceDriver.getDeviceProtocol()==null) {
			logger.info("The input data is invalid!");
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求值无效");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}else {
			if(fileName==null){
				logger.info("The input data is invalid!");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "没有有效文件名");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			}else {
				/* 保存驱动文件到本地磁盘 */
				fileName = fileName.substring(fileName.lastIndexOf(File.separator) + 1);
				// 得到文件的保存目录
				// 为防止一个目录下面出现太多文件，要使用hash算法打散存储
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
				if (file.exists()) {
					logger.info("File name already exists");
					Map<String, String> modle = new HashMap<String, String>();
					modle.put("info", "文件名已存在,请重新命名");
					JSONObject jarr = JSONObject.fromObject(modle);
					return JsonView.Render(jarr, response);
				} else {
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
					logger.info("File saved successfully");

					/* 保存驱动描述信息到数据库中 */
					deviceDriver.setAddress(dataSavePath);
					deviceDriver.setDeviceName(fileName.substring(0,fileName.lastIndexOf(".")));
					deviceDriver.setUpdateTime(new Timestamp(System.currentTimeMillis()));
					logger.info("Information is successfully saved to the database");
					boolean flag = deviceDriverDao.insert(deviceDriver);
					if (!flag) {
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
						modle.put("info", "信息保存失败，请重试");
						JSONObject jarr = JSONObject.fromObject(modle);
						return JsonView.Render(jarr, response);
					}

				}
			}
			
			
		}
		
	}

}
