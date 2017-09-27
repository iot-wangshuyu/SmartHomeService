package com.delta.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.delta.bean.Application;
import com.delta.bean.ApplicationDescription2;
import com.delta.bean.ApplicationDeviceDriver;
import com.delta.bean.ApplicationPosition;
import com.delta.bean.ApplicationSpeed;
import com.delta.bean.InstallDeviceDriver;
import com.delta.bean.User;
import com.delta.dao.AccessDao;
import com.delta.dao.ApplicationDao;
import com.delta.dao.ApplicationDescriptionDao2;
import com.delta.dao.ApplicationDeviceDriverDao;
import com.delta.dao.ApplicationPositionDao;
import com.delta.dao.ApplicationSpeedDao;
import com.delta.dao.InstallDeviceDriverDao;
import com.delta.dao.UserDao;
import com.delta.util.GetIp;
import com.delta.util.JsonView;
import com.delta.util.RequestInputStream;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 引导接口
 * @author Shuyu.Wang
 *
 */
public class Find implements Controller {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(Find.class);
	private ApplicationSpeedDao applicationSpeedDao;
	private ApplicationDescriptionDao2 applicationDescriptionDao2;
	private ApplicationDeviceDriverDao applicationDeviceDriverDao;
	private ApplicationPositionDao applicationPositionDao;
	private InstallDeviceDriverDao installDeviceDriverDao;
	private AccessDao accessDao;
    private ApplicationDao applicationDao;
    private UserDao userDao;
	public ApplicationSpeedDao getApplicationSpeedDao() {
		return applicationSpeedDao;
	}

	public void setApplicationSpeedDao(ApplicationSpeedDao applicationSpeedDao) {
		this.applicationSpeedDao = applicationSpeedDao;
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

	public ApplicationDescriptionDao2 getApplicationDescriptionDao2() {
		return applicationDescriptionDao2;
	}

	public void setApplicationDescriptionDao2(ApplicationDescriptionDao2 applicationDescriptionDao2) {
		this.applicationDescriptionDao2 = applicationDescriptionDao2;
	}

	public AccessDao getAccessDao() {
		return accessDao;
	}

	public void setAccessDao(AccessDao accessDao) {
		this.accessDao = accessDao;
	}
	public InstallDeviceDriverDao getInstallDeviceDriverDao() {
		return installDeviceDriverDao;
	}

	public void setInstallDeviceDriverDao(InstallDeviceDriverDao installDeviceDriverDao) {
		this.installDeviceDriverDao = installDeviceDriverDao;
	}

	public ApplicationDao getApplicationDao() {
		return applicationDao;
	}

	public void setApplicationDao(ApplicationDao applicationDao) {
		this.applicationDao = applicationDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		GetIp getIp = new GetIp();
		String value = getIp.getIpAddr(request);// 访问者ip
		logger.info("ip:" + value);
		String strm[] = value.split("\\.");	
		Integer user_id = Integer.valueOf(strm[strm.length - 1]);
		
		User user = new User();
		user.setUserName(String.valueOf(user_id));
		user.setPassWord("111");
		User u=userDao.selectByName(user);
		
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

		if (jo.containsKey("driver_id") && jo.containsKey("driver_version")) {
			accessDao.delete(u.getId());
			logger.info("Request data driver_id is :" + requestString.toString());
			//查找判断应用是否安装过，如果没有则插入记录
			InstallDeviceDriver installDeviceDriver = new InstallDeviceDriver();
			installDeviceDriver.setUser_id(u.getId());
			installDeviceDriver.setDriver_id(Integer.valueOf(jo.getString("driver_id")));
			List<InstallDeviceDriver> installDevice = installDeviceDriverDao.selectBy(installDeviceDriver);
			if (installDevice == null||installDevice.size()==0) {
				logger.info("Install the driver for the first time");
				installDeviceDriver.setDriver_version(jo.getString("driver_version"));
				installDeviceDriver.setInstallTime(new Timestamp(System.currentTimeMillis()));
				logger.info("Save the device driver to the database");
				boolean flag = installDeviceDriverDao.insert(installDeviceDriver);
				//保存记录成功以后查找驱动对应的应用id
				if (!flag) {
					ApplicationDeviceDriver applicationDeviceDriver = new ApplicationDeviceDriver();
					applicationDeviceDriver.setDriver_id(Integer.valueOf(jo.getString("driver_id")));

					logger.info("Find the corresponding application");
					List<ApplicationDeviceDriver> applicationDeviceDriverlist = applicationDeviceDriverDao// 查询应用驱动连接表中对应应用的id
							.select(applicationDeviceDriver);
					StringBuffer buf = new StringBuffer("");
					for (ApplicationDeviceDriver al : applicationDeviceDriverlist) {
						buf.append(al.getApplication_id());
						buf.append(", ");
					}
					if (buf.length() < 1) {
						logger.info("Information saved failed");
						Map<String, String> modle = new HashMap<String, String>();
						modle.put("info", "没有匹配的数据");
						JSONObject jarr = JSONObject.fromObject(modle);
						return JsonView.Render(jarr, response);
					} else {
						//查找n个应用对应的位置信息
						buf.deleteCharAt(buf.length() - 1);
						List<ApplicationPosition> list = applicationPositionDao.selects(buf.toString());// 查询Position表中应用对应的推荐安装位置
						Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss.S").create();
						String jsonStr = json.toJson(list);
						JSONObject obj = new JSONObject();
						try {
							JSONArray jrr = JSONArray.fromObject(jsonStr);
							ArrayList<String> arlist = new ArrayList<String>();
							for (int i = 0; i < jrr.size(); i++) {
								arlist.add(jrr.getJSONObject(i).getString("position"));
							}
							obj.put("description", arlist);

						} catch (Exception e) {						
							e.printStackTrace();
						}
						logger.info("The position data returned is:" + obj.toString());
						JSONObject jarr = JSONObject.fromObject(obj);
						return JsonView.Render(jarr, response);

					}
				} else {
					logger.info("Information saved failed");
					Map<String, String> modle = new HashMap<String, String>();
					modle.put("info", "驱动信息保存失败");
					JSONObject jarr = JSONObject.fromObject(modle);
					return JsonView.Render(jarr, response);
				}
			} else {
				//如果设备安装过，直接查找驱动对应应用的id
				logger.info("The driver already exists");
				ApplicationDeviceDriver applicationDeviceDriver = new ApplicationDeviceDriver();
				applicationDeviceDriver.setDriver_id(Integer.valueOf(jo.getString("driver_id")));
				logger.info("Find the corresponding application");
				List<ApplicationDeviceDriver> applicationDeviceDriverlist = applicationDeviceDriverDao// 查询应用驱动连接表中对应应用的id
						.select(applicationDeviceDriver);
				StringBuffer buf = new StringBuffer("");
				for (ApplicationDeviceDriver al : applicationDeviceDriverlist) {
					buf.append(al.getApplication_id());
					buf.append(", ");
				}
				if (buf.length() < 1) {
					logger.info("Information saved failed");
					Map<String, String> modle = new HashMap<String, String>();
					modle.put("info", "没有匹配的数据");
					JSONObject jarr = JSONObject.fromObject(modle);
					return JsonView.Render(jarr, response);
				} else {
					buf.deleteCharAt(buf.length() - 1);
					List<ApplicationPosition> list = applicationPositionDao.selects(buf.toString());// 查询Position表中应用对应的推荐安装位置
					Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss.S").create();
					String jsonStr = json.toJson(list);
					JSONObject obj = new JSONObject();
					try {
						JSONArray jrr = JSONArray.fromObject(jsonStr);
						ArrayList<String> arlist = new ArrayList<String>();
						for (int i = 0; i < jrr.size(); i++) {
							arlist.add(jrr.getJSONObject(i).getString("position"));
						}
						obj.put("description", arlist);

					} catch (Exception e) {						
						e.printStackTrace();
					}
					logger.info("The position data returned is:" + obj.toString());
					JSONObject jarr = JSONObject.fromObject(obj);
					return JsonView.Render(jarr, response);

				}
			}
			
		} else if (jo.containsKey("description")) {
			boolean flag = accessDao.insert(u.getId());
			if (!flag) {
				//用户第二次访问引导，根据用户选择的位置，查找这些位置对应的应用的id，然后用这些id查询设备响应速度
				if (1 == accessDao.select(u.getId())) {
					logger.info("Request data description is :" +requestString.toString());
					ApplicationPosition applicationPosition = new ApplicationPosition();
					applicationPosition.setPosition(jo.getString("description"));
					logger.info("Find the corresponding application");
					List<ApplicationPosition> idList = applicationPositionDao.selectId(applicationPosition);// 查找位置表中对应的应用的id
					StringBuffer buf = new StringBuffer("");
					for (ApplicationPosition al : idList) {
						buf.append(al.getApplication_id());
						buf.append(", ");
					}
					if (buf.length() < 1) {
						logger.info("Information saved failed");
						Map<String, String> modle = new HashMap<String, String>();
						modle.put("info", "没有匹配的数据");
						JSONObject jarr = JSONObject.fromObject(modle);
						return JsonView.Render(jarr, response);
					} else {
						buf.deleteCharAt(buf.length() - 1);
						List<ApplicationSpeed> list = applicationSpeedDao.selects(buf.toString()); // 查询描述表一中与应用id对应的描述
						Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss.S").create();
						String jsonStr = json.toJson(list);	
						JSONObject obj = new JSONObject();
						try {
							JSONArray jrr = JSONArray.fromObject(jsonStr);
							ArrayList<String> arlist = new ArrayList<String>();
							for (int i = 0; i < jrr.size(); i++) {
								arlist.add(jrr.getJSONObject(i).getString("speed"));//返回响应速度
							}
							obj.put("description", arlist);

						} catch (Exception e) {							
							e.printStackTrace();
						}
						logger.info("The description data returned is:" + obj.toString());
						JSONObject jarr = JSONObject.fromObject(obj);
						return JsonView.Render(jarr, response);
					}

				} 
				//TODO 加入第三个限制字段
		/*		else if (2 == accessDao.select(u.getId())) {
					//根据用户选择的speed，查找对应的应用id，根据应用id再去查找另一描述字段
					logger.info("Request data description is :" + requestString.toString());
					ApplicationSpeed applicationDescription = new ApplicationSpeed();
					applicationDescription.setSpeed(jo.getString("description"));
					logger.info("Find the corresponding application");
					List<ApplicationSpeed> idList = applicationSpeedDao.selectId(applicationDescription);// 查找描述表1中对应的应用的id
					StringBuffer buf = new StringBuffer("");
					for (ApplicationSpeed al : idList) {
						buf.append(al.getApplication_id());
						buf.append(", ");

					}
					if (buf.length() < 1) {
						logger.info("Information saved failed");
						Map<String, String> modle = new HashMap<String, String>();
						modle.put("info", "没有匹配的数据");
						JSONObject jarr = JSONObject.fromObject(modle);
						return JsonView.Render(jarr, response);
					} else {
						buf.deleteCharAt(buf.length() - 1);
						List<ApplicationDescription2> list = applicationDescriptionDao2.selects(buf.toString()); // 查找描述表2中与id对应的描述
						Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss.S").create();
						String jsonStr = json.toJson(list);
						JSONObject obj = new JSONObject();
						try {
							JSONArray jrr = JSONArray.fromObject(jsonStr);
							ArrayList<String> arlist = new ArrayList<String>();
							for (int i = 0; i < jrr.size(); i++) {
								arlist.add(jrr.getJSONObject(i).getString("description"));
							}
							obj.put("description", arlist);

						} catch (Exception e) {							
							e.printStackTrace();
						}
						logger.info("The description2 data returned is:" + obj.toString());
						JSONObject jarr = JSONObject.fromObject(obj);
						return JsonView.Render(jarr, response);
					}

				} 
				else if (accessDao.select(u.getId()) == 3) {
					// 查找对应的应用 
					logger.info("Request data description is :" + requestString.toString());
					ApplicationDescription2 applicationDescription = new ApplicationDescription2();
					applicationDescription.setDescription(jo.getString("description"));
					logger.info("Find the corresponding application");
					List<ApplicationDescription2> idList = applicationDescriptionDao2.selectId(applicationDescription);// 查找描述表1中对应的应用的id
					StringBuffer buf = new StringBuffer("");
					for (ApplicationDescription2 al : idList) {
						buf.append(al.getApplication_id());
						buf.append(", ");
					}
					if (buf.length() < 1) {
						logger.info("Information saved failed");
						Map<String, String> modle = new HashMap<String, String>();
						modle.put("info", "没有匹配的数据");
						JSONObject jarr = JSONObject.fromObject(modle);
						return JsonView.Render(jarr, response);
					} else {
						buf.deleteCharAt(buf.length() - 1);
						List<Map<String, Object>> list = applicationFunctionDao.selectf(buf.toString());
						Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss.S").create();
						String jsonStr = json.toJson(list);
						JSONObject obj = new JSONObject();
						try {
							JSONArray jrr = JSONArray.fromObject(jsonStr);
							ArrayList<String> arlist = new ArrayList<String>();
							for (int i = 0; i < jrr.size(); i++) {
								arlist.add(jrr.getJSONObject(i).getString("functions"));
							}
							obj.put("functions", arlist);
						} catch (Exception e) {							
							e.printStackTrace();
						}
						logger.info("The  data returned is:" + obj.toString());
						JSONObject jarr = JSONObject.fromObject(obj);
						return JsonView.Render(jarr, response);
					}

				}*/ 
				
				
				else if (2 == accessDao.select(u.getId())) {
					logger.info("Request data description is :" + requestString.toString());
					ApplicationSpeed applicationDescription = new ApplicationSpeed();
					applicationDescription.setSpeed(jo.getString("description"));
					logger.info("Find the corresponding application");
					List<ApplicationSpeed> idList = applicationSpeedDao.selectId(applicationDescription);// 查找描述表1中对应的应用的id
					StringBuffer buf = new StringBuffer("");
					for (ApplicationSpeed al : idList) {
						buf.append(al.getApplication_id());
						buf.append(", ");

					}
					if (buf.length() < 1) {
						logger.info("Information saved failed");
						Map<String, String> modle = new HashMap<String, String>();
						modle.put("info", "没有匹配的数据");
						JSONObject jarr = JSONObject.fromObject(modle);
						return JsonView.Render(jarr, response);
					} else {
						buf.deleteCharAt(buf.length() - 1);
						List<Application> list = applicationDao.selectName(buf.toString());
						Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss.S").create();
						String jsonStr = json.toJson(list);
						JSONObject obj = new JSONObject();
						try {
							JSONArray jrr = JSONArray.fromObject(jsonStr);
							ArrayList<String> arlist = new ArrayList<String>();
							for (int i = 0; i < jrr.size(); i++) {
								arlist.add(jrr.getJSONObject(i).getString("name"));
							}
							obj.put("appliactions", arlist);
						} catch (Exception e) {							
							e.printStackTrace();
						}
						logger.info("The  data returned is:" + obj.toString());
						JSONObject jarr = JSONObject.fromObject(obj);
						return JsonView.Render(jarr, response);
					}

				}
			else {

					if (accessDao.select(u.getId()) == 3) {
						accessDao.delete(u.getId()); // 删除用户访问记录
						List<Application> addressList = applicationDao.selectadd(jo.getString("description"));
						Gson json = new GsonBuilder().setDateFormat("yyyy-MM-dd  HH:mm:ss.S").create();
						String str = json.toJson(addressList);
						logger.info("Application information is:"+str);
						str = str.replace("[", "");
						str = str.replace("]", "");
						JSONObject ja = JSONObject.fromObject(str);
						Map<String, Object> modle = new HashMap<String, Object>();
						modle.put("appliaction",ja);
						modle.put("time",new Timestamp(System.currentTimeMillis()));						
					    String strs = json.toJson(modle);
						JSONObject jas = JSONObject.fromObject(strs);
						return JsonView.Render(jas, response);

					} else {
						accessDao.delete(u.getId()); // 删除用户访问记录
						Map<String, String> modle = new HashMap<String, String>();
						modle.put("info", "设置错误，请重新设置");
						JSONObject jarr = JSONObject.fromObject(modle);
						return JsonView.Render(jarr, response);
					}

				}

			} else {
				logger.info("Information saved failed");
				Map<String, String> modle = new HashMap<String, String>();
				modle.put("info", "数据保存失败");
				JSONObject jarr = JSONObject.fromObject(modle);
				return JsonView.Render(jarr, response);
			}

		} else {
			Map<String, String> modle = new HashMap<String, String>();
			modle.put("info", "请求参数无效");
			JSONObject jarr = JSONObject.fromObject(modle);
			return JsonView.Render(jarr, response);
		}

	}

}
