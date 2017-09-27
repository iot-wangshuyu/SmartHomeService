<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>更新应用</title>
<meta http-equiv="Content-Type" content="multipart/form-data">
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.8.1.min.js"></script>
<link rel="stylesheet" href="css/bootstrap.css" />
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrapValidator.js"></script>
<script type="text/javascript">
$(document).ready(function GetRequest() {

	   var url = location.search; //获取url中"?"符后的字串

	   var theRequest = new Object();

	   if (url.indexOf("?") != -1) {

	      var str = url.substr(1);

	      strs = str.split("&");

	      for(var i = 0; i < strs.length; i ++) {

	    	  theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);

	      }

	   }
	   console.log( theRequest);
	   var str=theRequest.id;
	   document.getElementById("id").value =str;
	   console.log(str)

	});
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
														url : "<%=request.getContextPath() %>/updateApplication.do",
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
																window.location.href = "<%=request.getContextPath() %>/index.jsp";
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
						<h2>功能插件更新</h2>
					</div>
					<iframe name="upload" style="display: none"></iframe>
					<form name="defaultForm" id="defaultForm" method="post"
						target="upload" class="form-horizontal" action=""
						encType="multipart/form-data">
					
								<input type="hidden" class="form-control" name="id" id="id"  value="" />

						<div class="form-group">
							<label class="col-lg-3 control-label">功能版本</label>
							<div class="col-lg-5">
								<input type="text" class="form-control" name="version"
									id="version" />
							</div>
						</div>


						<div class="form-group">
							<label class="col-lg-3 control-label">功能文件</label>
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