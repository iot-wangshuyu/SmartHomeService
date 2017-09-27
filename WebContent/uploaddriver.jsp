<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String user = (String) session.getAttribute("user");
%>
<html>
<head>
<title>驱动上传</title>
<meta http-equiv="Content-Type" content="multipart/form-data">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.8.1.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.css" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrapValidator.js"></script>
<script type="text/javascript">
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
											var formData = new FormData(); //可以把form中所有表单元素的name与value组成一个queryString，提交到后台
											formData
													.append(
															"file",
															$("#upload_file")[0].files[0]);
											formData.append("json", JSON
													.stringify(jsonuserinfo));
											console.log(formData);

											$
													.ajax({
														type : "post", // 或者 "get"            											
														url : "<%=request.getContextPath() %>/uploadDriver.do",
														dataType : "html",
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
																window.location.href = "<%=request.getContextPath() %>/createApplication.jsp";
															}

														},
														error : function() {

															alert("上传失败，请重试");

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
</head>
<body>
	<div class="container">
		<div class="row">
			<!-- form: -->
			<section>
				<div class="col-lg-8 col-lg-offset-2">
					<div class="page-header">
						<h2>驱动上传</h2>
					</div>
					<iframe name="upload" style="display: none"></iframe>
					<form name="defaultForm" id="defaultForm" method="post"
						target="upload" class="form-horizontal" action=""
						encType="multipart/form-data">


						<div class="form-group">
							<label class="col-lg-3 control-label">驱动名称</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="name" id="name" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">驱动版本</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="version"
									id="version" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">作者</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="author"
									id="author" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-lg-3 control-label">设备厂商</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="deviceVendor"
									id="deviceVendor" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">设备类型</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="deviceType"
									id="deviceType" />
							</div>
						</div>


						<div class="form-group">
							<label class="col-lg-3 control-label">设备协议</label>
							<div class="col-lg-5">
								<div class="radio">
									<label> <input type="radio" name="deviceProtocol"
										id="deviceProtocol" value="WiFi" /> WIFI
									</label>
								</div>
								<div class="radio">
									<label> <input type="radio" name="deviceProtocol"
										id="deviceProtocol" value="Bluetooth" /> Bluetooth
									</label>
								</div>
								<div class="radio">
									<label> <input type="radio" name="deviceProtocol"
										id="deviceProtocol" value="ZigBee" /> ZigBee
									</label>
								</div>
							</div>
						</div>


						<div class="form-group">
							<label class="col-lg-3 control-label">驱动文件</label>
							<div class="col-lg-5">
								<input type="file" class="form-control" name="upload_file"
									id="upload_file" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-lg-9 col-lg-offset-3">

								<button type="button" class="btn btn-info" id="validateBtn">上传</button>
								<button type="button" class="btn btn-info" id="resetBtn">重置</button>
							</div>
						</div>
					</form>
				</div>
			</section>
			<!-- :form -->
		</div>
	</div>


</body>
</html>