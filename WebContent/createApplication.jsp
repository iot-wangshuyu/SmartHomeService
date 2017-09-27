<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>创建应用</title>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.8.1.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.css" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrapValidator.js"></script>
<script type="text/javascript">
	var num = 0;
	$(function() {
		$('#sbutton')
				.click(
						function() {
							num = num + 1;
							$
									.ajax({
										type : "get", // 或者 "get"            														
										url : "<%=request.getContextPath() %>/selectDeviceDriver.do",
										dataType : "text",
										// crossDomain:true,
										success : function(str) {
											console.log(str);
											var data = JSON.parse(str);
											var posArray = data.driverList;
											$('#sele')
													.append(
															'<div id=driv'+num+'><input type="button" value="删除" onclick="deleted('
																	+ num
																	+ ');"/><select id=op'+num+' name="id"><option selected>点击选择</option>');
											for (var i = 0; i < posArray.length; i++) {
												$('#op' + num)
														.append(
																'<option  value='+ posArray[i].id+ '> '
																		+ posArray[i].name
																		+ ' </option>');
											}
											$('#sele')
													.append('</select></div>');

										},
										error : function() {
											alert("请求失败");
										}
									});
						});

	});
	/*   删除选择的驱动 */
	function deleted(arg) {
		var obj = arg;
		$('#driv'+ obj + '  input').remove();
		$('#driv'+ obj + '  select').remove();
	}

	$(document)
			.ready(
					function() {

						$('#defaultForm').bootstrapValidator('validate');//判断执行语句

						$.fn.serializeObject = function() //form表单转换成json
						{
							var o = {};
							var a = this.serializeArray();
							$.each(a, function() {
								if (o[this.name]) {
									if (!o[this.name].push) {
										o[this.name] = [ o[this.name] ];
									}
									o[this.name].push(this.value || '');
								} else {
									o[this.name] = this.value || '';
								}
							});
							return o;
						};

						// Validate the form manually
						$('#validateBtn')
								.click(
										function() {
											var jsonuserinfo = $('#defaultForm')
													.serializeObject('validate');
											console.log(JSON
													.stringify(jsonuserinfo));
											var formData = new FormData(); //可以把form中所有表单元素的name与value组成一个queryString，提交到后台
											formData
													.append(
															"file",
															$("#upload_file")[0].files[0]);
											formData
													.append(
															"file",
															$("#upload_photo")[0].files[0]);
											formData.append("json", JSON
													.stringify(jsonuserinfo));
											console.log(formData);

											$
													.ajax({
														type : "post", // 或者 "get"            														
														url : "<%=request.getContextPath() %>/createApplication.do",
														dataType : "text",
														data : formData,
														cache : false,
														contentType : false,
														crossDomain : true,//跨域
														processData : false,
														success : function(str) {
															var data = JSON
																	.parse(str);
															console
																	.log(data.info);
															var str1 = 200;
															var str2 = data.info;
															if (str1 != str2) {
																alert("提示信息:"
																		+ data.info);

															} else {
																window.location.href = "<%=request.getContextPath() %>/index.jsp";
															}
														},
														error : function() {
															alert("创建失败，请重试");
														}
													});

										});

						$('#resetBtn').click(
								function() {
									$('#defaultForm')
											.data('bootstrapValidator')
											.resetForm(true);
								});
					});
</script>
<body>
	<div class="container">
		<div class="row">
			<!-- form: -->
			<section>
			<div class="col-lg-8 col-lg-offset-2">
				<div class="page-header">
					<h2>创建应用</h2>
				</div>
				<iframe name="upload" style="display: none"></iframe>
				<form name="defaultForm" id="defaultForm" method="post"
					target="upload" class="form-horizontal" action="">

					<div class="form-group">
						<label class="col-lg-3 control-label">选择驱动<input
							type="button" id="sbutton" value="添加"></label>
						<div class="col-lg-5">
							<div id="sele"></div>
						</div>
					</div>

					<div class="form-group">
						<label class="col-lg-3 control-label">应用名称</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" name="name" id="name" />
						</div>
					</div>

					<div class="form-group">
						<label class="col-lg-3 control-label">应用描述</label>
						<div class="col-lg-5">
							<input type="textarea" class="form-control" name="describes"
								id="describes" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">创建人</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" name="author" id="author" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">推荐位置</label>
						<div class="col-lg-5">
							<div class="checkbox">
								<label> <input type="checkbox" name="position"
									value="主卧" /> 主卧
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="position"
									value="次卧" />次卧
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="position"
									value="客厅" />客厅
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="position"
									value="厨房" />厨房
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="position"
									value="卫生间" />卫生间
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="position"
									value="阳台" />阳台
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="position"
									value="楼道" />楼道
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="position"
									value="门厅" />门厅
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="position"
									value="其他" />其他
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">设备响应速度</label>
						<div class="col-lg-5">
							<div class="checkbox">
								<label> <input type="checkbox" name="speed" value="迅速" />迅速
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="speed" value="快" />快
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="speed" value="一般" />一般
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="speed" value="慢" />慢
								</label>
							</div>
							<div class="checkbox">
								<label> <input type="checkbox" name="speed" value="很慢" />很慢
								</label>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">版本</label>
						<div class="col-lg-5">
							<input type="text" class="form-control" name="version"
								id="version" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">文件</label>
						<div class="col-lg-5">
							<input type="file" class="form-control" name="upload_file"
								id="upload_file" />
						</div>
					</div>
					<div class="form-group">
						<label class="col-lg-3 control-label">图片</label>
						<div class="col-lg-5">
							<input type="file" class="form-control" name="upload_photo"
								value="选择图片" id="upload_photo" />
						</div>
					</div>

					<div class="form-group">
						<div class="col-lg-9 col-lg-offset-3">

							<button type="button" class="btn btn-info" id="validateBtn">创建</button>
							<button type="button" class="btn btn-info" id="resetBtn">重置</button>
						</div>
					</div>
				</form>
			</div>
			</section>
			<!-- :form -->

		</div>
		<div class="row">
			<hr>
			<div class="hint"></div>
		</div>
	</div>

</body>
</html>