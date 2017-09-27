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
import com.delta.bean.ApplicationDeviceDriver;
import com.delta.bean.ApplicationPosition;
import com.delta.bean.ApplicationSpeed;
import com.delta.dao.ApplicationDao;
import com.delta.dao.ApplicationDeviceDriverDao;
import com.delta.dao.ApplicationPositionDao;
import com.delta.dao.ApplicationSpeedDao;
import com.delta.dao.DeviceDriverDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 添加设备功能
 * 
 * @author Shuyu.Wang
 *
 */
public class CreateApplication implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(CreateApplication.class);
	private static final String fileRoot;
	private static final String picRoot;
	private final String savePath = "/iOS/Applicationplug-ins/";
	private final String picPath = "/iOS/pic/";
	private ApplicationDao applicationDao;
	private ApplicationDeviceDriverDao applicationDeviceDriverDao;
	private ApplicationPositionDao applicationPositionDao;
	private ApplicationSpeedDao applicationSpeedDao;
	private DeviceDriverDao deviceDriverDao;

	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	public ApplicationDeviceDriverDao getApplicationDeviceDriverDao() {
		return applicationDeviceDriverDao;
	}

	public void setApplicationDeviceDriverDao(ApplicationDeviceDriverDao applicationDeviceDriverDao) {
		this.applicationDeviceDriverDao = applicationDeviceDriverDao;
	}

	public ApplicationPositionDao getApplicationPositionDao() {
		return applicationPositionDao;
	}

	public void setApplicationPositionDao(ApplicationPositionDao applicationPositionDao) {
		this.applicationPositionDao = applicationPositionDao;
	}

	public DeviceDriverDao getDeviceDriverDao() {
		return deviceDriverDao;
	}

	public void setDeviceDriverDao(DeviceDriverDao deviceDriverDao) {
		this.deviceDriverDao = deviceDriverDao;
	}

	public ApplicationSpeedDao getApplicationSpeedDao() {
		return applicationSpeedDao;
	}

	public void setApplicationSpeedDao(ApplicationSpeedDao applicationSpeedDao) {
		this.applicationSpeedDao = applicationSpeedDao;
	}

	static {
		fileRoot = "D:\\\\SmartHomeiOS\\\\Applicationplug-ins";
		File file = new File(fileRoot);
		if (!file.exists() || !file.isDirectory()) {
			file.delete();
			file.mkdirs();
		}
		picRoot = "D:\\\\SmartHomeiOS\\\\pic";
		File files = new File(picRoot);
		if (!files.exists() || !files.isDirectory()) {
			files.delete();
			files.mkdirs();
		}
	}

	String path = "D:/pic_location.png";
	private int[] driverIds;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		String imgName = null;
		InputStream fileInputStream = null;
		InputStream imageInputStream = null;
		String fValue = null;
		JSONObject jo = null;
		int i = 0;
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
						logger.info(e.toString());
						Map<String, String> modle = new HashMap<String, String>();
						modle.put("info", "请求数据格式错误");
						JSONObject jarr = JSONObject.fromObject(modle);
						return JsonView.Render(jarr, response);

					}
				} else {
					i = i + 1;
					if (i == 1) {
						fileName = fItem.getName(); // 获取上传文件的数据
						fileInputStream = fItem.getInputStream();
					} else {
						imgName = fItem.getName();
						imageInputStream = fItem.getInputStream();
					}

				}
			}

		} catch (Exception e) {
			logger.info(e.toString());
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求数据格式错误");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}
		if (jo.containsKey("id") && jo.containsKey("name") && jo.containsKey("describes") && jo.containsKey("author")
				&& jo.containsKey("version") && jo.containsKey("position") && jo.containsKey("speed")) {
			if (fileName == null || imgName == null) {
				logger.info("The input data is invalid!");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "上传的文件不合法");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			} else {
				// 文件上传，hash打乱文件夹
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

					// 图片上传
					imgName = imgName.substring(imgName.lastIndexOf(File.separator) + 1);
					int hashcodes = imgName.hashCode();
					int dir3 = hashcodes & 0xf; // 0--15
					int dir4 = (hashcodes & 0xf0) >> 4; // 0-15
					String imgPath = picRoot + "\\" + dir3 + "\\" + dir4 + "\\";
					File imgSave = new File(imgPath);
					// 如果目录不存在
					if (!imgSave.exists()) {
						// 创建目录
						imgSave.mkdirs();
					}
					String realImgPath = imgPath + "\\" + imgName;
					String ImgSavePath = picPath + dir3 + "/" + dir4 + "/" + imgName;
					File image = new File(realImgPath);

					if (image.exists()) {
						File files = new File(realFilePath);
						// 路径为文件且不为空则进行删除
						if (files.isFile() && files.exists()) {
							files.delete();
						}
						logger.info("File name already exists");
						Map<String, String> modle = new HashMap<String, String>();
						modle.put("info", "图片名已存在,请重新命名");
						JSONObject jarr = JSONObject.fromObject(modle);
						return JsonView.Render(jarr, response);
					} else {
						FileOutputStream foss = new FileOutputStream(realImgPath);
						try {
							int read = 0;
							byte[] bytes = new byte[1024];
							while ((read = imageInputStream.read(bytes)) != -1) {
								foss.write(bytes, 0, read);
							}
						} catch (IOException ie) {
							File files = new File(realFilePath);
							// 路径为文件且不为空则进行删除
							if (files.isFile() && files.exists()) {
								files.delete();
							}
							logger.info(ie.toString());
							Map<String, String> modle = new HashMap<String, String>();
							modle.put("info", "图片上传失败");
							JSONObject jarr = JSONObject.fromObject(modle);
							return JsonView.Render(jarr, response);
						} finally {
							foss.flush();
							foss.close();

						}

						/*
						 * 图片直接保存到数据库 ImageUtil imageUtil = new ImageUtil();
						 * FileInputStream in = imageUtil.readImage(path);
						 * applicationFunction.setPhoto(in);
						 */
						// 把应用数据保存到数据库
						Application application = new Application();
						application.setName(jo.getString("name"));
						application.setDescribes(jo.getString("describes"));
						application.setAuthor(jo.getString("author"));
						application.setVersion(jo.getString("version"));
						application.setFileName(fileName.substring(0,fileName.lastIndexOf(".")));
						application.setImage(ImgSavePath);
						application.setAddress(dataSavePath);
						application.setBuildTime(new Timestamp(System.currentTimeMillis()));
						boolean flag = applicationDao.insert(application);
						boolean flag1 = true;
						if (!flag) {
							// 查找刚才保存的应用id
							application = applicationDao.selectId();
							// 应用和驱动关系保存
							String jString = jo.getString("id");							
							String fir = jString.substring(0, 1);// 获取字符串的第一个字符
							String pdString="[";
							System.out.println(fir );
							if (fir.equals(pdString)) {								
								JSONArray id = jo.getJSONArray("id"); // 得到position数组
								ApplicationDeviceDriver applicationDeviceDriver = new ApplicationDeviceDriver();
								for (int num = 0; num < id.size(); num++) {
									// driverIds[0]=id.getInt(num);
									applicationDeviceDriver.setApplication_id(application.getId());
									applicationDeviceDriver.setDriver_id(id.getInt(num));
									applicationDeviceDriver.setBuildTime(new Timestamp(System.currentTimeMillis()));
									flag1 = applicationDeviceDriverDao.insert(applicationDeviceDriver);
								}
							} else {								
								ApplicationDeviceDriver applicationDeviceDriver = new ApplicationDeviceDriver();
								// driverIds[0]=id.getInt(num);
								applicationDeviceDriver.setApplication_id(application.getId());
								applicationDeviceDriver.setDriver_id(jo.getInt("id"));
								applicationDeviceDriver.setBuildTime(new Timestamp(System.currentTimeMillis()));
								flag1 = applicationDeviceDriverDao.insert(applicationDeviceDriver);

							}

							if (!flag1) {
								// 把选择的位置和对应的应用id保存到数据库
								String jstring = jo.getString("position");									
								String firs = jstring.substring(0, 1);// 获取字符串的第一个字符
								String pdstring="[";
								boolean flag2 = true;
								if (firs.equals(pdstring)) {
									JSONArray position = jo.getJSONArray("position"); // 得到position数组
									for (int num = 0; num < position.size(); num++) {
										ApplicationPosition applicationPosition = new ApplicationPosition();
										applicationPosition.setApplication_id(application.getId());
										applicationPosition.setBuildTime(new Timestamp(System.currentTimeMillis()));
										applicationPosition.setPosition(position.getString(num));
										flag2 = applicationPositionDao.insert(applicationPosition);
									}
								}else {
									ApplicationPosition applicationPosition = new ApplicationPosition();
									applicationPosition.setApplication_id(application.getId());
									applicationPosition.setBuildTime(new Timestamp(System.currentTimeMillis()));
									applicationPosition.setPosition(jo.getString("position"));
									flag2 = applicationPositionDao.insert(applicationPosition);
								}								
								if (!flag2) {
									// 吧设备和响应速度保存
									String jptring = jo.getString("speed");									
									String firp = jptring.substring(0, 1);// 获取字符串的第一个字符
									String pstring="[";
									boolean flag3=true;
									if (firp.equals(pstring)) {
										JSONArray speed = jo.getJSONArray("speed"); // 得到position数组
										for (int num = 0; num < speed.size(); num++) {
											ApplicationSpeed applicationSpeed = new ApplicationSpeed();
											applicationSpeed.setApplication_id(application.getId());
											applicationSpeed.setSpeed(speed.getString(num));
											application.setBuildTime(new Timestamp(System.currentTimeMillis()));
											flag3 = applicationSpeedDao.insert(applicationSpeed);
										}
									} else {
										ApplicationSpeed applicationSpeed = new ApplicationSpeed();
										applicationSpeed.setApplication_id(application.getId());
										applicationSpeed.setSpeed(jo.getString("speed"));
										application.setBuildTime(new Timestamp(System.currentTimeMillis()));
										flag3 = applicationSpeedDao.insert(applicationSpeed);
									}
									
									if (!flag3) {
										logger.info("应用创建成功！");
										Map<String, String> modle = new HashMap<String, String>();
										modle.put("info", "200");
										JSONObject jarr = JSONObject.fromObject(modle);
										return JsonView.Render(jarr, response);
									} else {
										// 把上传的文件和图片删除
										File files = new File(realFilePath);
										if (files.isFile() && files.exists()) {
											files.delete();
										}
										File imgs = new File(realImgPath);
										if (imgs.isFile() && imgs.exists()) {
											imgs.delete();
										}
										// 删除应用、应用和驱动关系的数据
										applicationDao.delete(application);
										// TODO删除刚才添加的记录，需要先遍历一下记录的id
										// for(int a=0;a<driverIds.length;a++)
										// {
										// applicationDeviceDriverDao.delete(applicationDeviceDriver);
										// }
										logger.info("响应速度保存失败！");
										Map<String, String> modle = new HashMap<String, String>();
										modle.put("info", "响应速度保存失败，请重试！");
										JSONObject jarr = JSONObject.fromObject(modle);
										return JsonView.Render(jarr, response);
									}

								} else {
									// 把上传的文件和图片删除
									File files = new File(realFilePath);
									if (files.isFile() && files.exists()) {
										files.delete();
									}
									File imgs = new File(realImgPath);
									if (imgs.isFile() && imgs.exists()) {
										imgs.delete();
									}
									// 删除应用、应用和驱动关系的数据
									applicationDao.delete(application);
									// TODO删除刚才添加的记录，需要先遍历一下记录的id
									// for(int a=0;a<driverIds.length;a++)
									// {
									// applicationDeviceDriverDao.delete(applicationDeviceDriver);
									// }
									logger.info("位置保存失败！");
									Map<String, String> modle = new HashMap<String, String>();
									modle.put("info", "位置保存失败，请重试！");
									JSONObject jarr = JSONObject.fromObject(modle);
									return JsonView.Render(jarr, response);
								}

							} else {
								// 把上传的文件和图片删除
								File files = new File(realFilePath);
								if (files.isFile() && files.exists()) {
									files.delete();
								}
								File imgs = new File(realImgPath);
								if (imgs.isFile() && imgs.exists()) {
									imgs.delete();
								}
								// 删除应用的记录
								applicationDao.delete(application);
								logger.info("应用创建失败！");
								Map<String, String> modle = new HashMap<String, String>();
								modle.put("info", "驱动和应用关系保存失败，请重试");
								JSONObject jarr = JSONObject.fromObject(modle);
								return JsonView.Render(jarr, response);
							}

						} else {
							// 把上传的文件和图片删除
							File files = new File(realFilePath);
							if (files.isFile() && files.exists()) {
								files.delete();
							}
							File imgs = new File(realImgPath);
							if (imgs.isFile() && imgs.exists()) {
								imgs.delete();
							}
							logger.info("应用创建失败！");
							Map<String, String> modle = new HashMap<String, String>();
							modle.put("info", "应用数据保存失败，请重试");
							JSONObject jarr = JSONObject.fromObject(modle);
							return JsonView.Render(jarr, response);
						}

					}

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
