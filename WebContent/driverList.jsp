<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>以上传的驱动</title>
<link href="tablecloth/tablecloth.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet" href="tablecloth/table.css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrapValidator.js"></script>
<script type="text/javascript">
var paging =1;
var totalpage=0;
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
	   var str=theRequest.paging;  
	   if(str==null){
			$
			.ajax({
				type : "get", // 或者 "get"            														
				url : "<%=request.getContextPath() %>/findDeviceDriver.do?page="+1,
				dataType : "text",
				// crossDomain:true,
				success : function(obj) {
					    console.log(obj);
					    var data =JSON.parse(obj);											    
						var posArray =data.deviceDriverList;
						totalpage=data.totalpage;
					    for(i=0;i<posArray.length;i++)  
					    {  
					    	 $('#url_list').append('<tr><td style="width:100px;">'+ posArray[i].id+'</td><td>' + posArray[i].name + '</td><td> '+ posArray[i].version+ '</td><td>'+ posArray[i].author +'</td><td>'+ posArray[i].deviceName +'</td><td>'+ posArray[i].deviceVendor +'</td><td>'+ posArray[i].deviceType +'</td><td>'+ posArray[i].deviceProtocol+'</td><td>'+ posArray[i].updateTime +'</td><td><form name="defaultForm" id="defaultForm" method="post"><input type="button" value="更新驱动" onclick="addButton('+posArray[i].id +');"/></form></td></tr>');			    	
					    	 
					    }  

				},
				error : function() {
					alert("请求失败");
				}
			});

	   }else{
		   paging=str;
		   $
			.ajax({
				type : "get", // 或者 "get"            														
				url : "<%=request.getContextPath() %>/findDeviceDriver.do?page="+str,
				dataType : "text",
				// crossDomain:true,
				success : function(obj) {              
					    console.log(obj);
					    var data =JSON.parse(obj);											    
						var posArray =data.deviceDriverList;
						totalpage=data.totalpage;
					    for(i=0;i<posArray.length;i++)  
					    {  
					    	 $('#url_list').append('<tr><td style="width:100px;">'+ posArray[i].id+'</td><td>' + posArray[i].name + '</td><td> '+ posArray[i].version+ '</td><td>'+ posArray[i].author +'</td><td>'+ posArray[i].deviceName +'</td><td>'+ posArray[i].deviceVendor +'</td><td>'+ posArray[i].deviceType +'</td><td>'+ posArray[i].deviceProtocol+'</td><td>'+ posArray[i].updateTime +'</td><td><form name="defaultForm" id="defaultForm" method="post"><input type="button" value="更新驱动" onclick="addButton('+posArray[i].id +');"/></form></td></tr>');			    	
					    	 
					    }  

				},
				error : function() {
					alert("请求失败");
				}
			});
	   }
			
		   
	   
	});
function page(p){   
    if(p == 'next' && paging < totalpage){  
      paging ++;  
      if(paging > 1){  
        $(":button:eq(0)").removeAttr('disabled');  
      }  
      if(paging == 3){  
        $(":button:eq(1)").attr('disabled','disabled');  
      }   
     }else if(p == 'last' && paging > 1){  
        paging --;  
         $(":button:eq(1)").removeAttr('disabled');  
        if(paging == 1){  
         $(":button:eq(0)").attr('disabled','disabled');  
        }   
     } 
    window.location.href='<%=request.getContextPath() %>/driverList.jsp?paging='+paging;
 } 
function addButton(arg){
	  var obj=arg;
		 window.location.href='<%=request.getContextPath() %>/updateDriver.jsp?id='+obj ;
		}
   
</script>
</head>
<body>
<div id="container">
	<h2>驱动列表</h2>
	<div id="content">	
		<table id="url_list" name="ApplicationList" width="900px" cellspacing="0" cellpadding="0">
		<tr><th style="width:100px;">id</th><th style="width:100px;">驱动名称</th><th style="width:100px;">版本</th><th style="width:100px;">作者</th><th style="width:100px;">文件名</th><th style="width:100px;">厂商</th><th style="width:100px;">设备类型</th><th style="width:100px;">通信协议</th><th style="width:100px;">更新时间</th><th style="width:100px;">更新</th></tr>
			</table>
			   <input type="button"  value="上一页" onclick="page('last');"/><input type="button" value="下一页" onclick = "page('next');"/>  
	</div> <!--  disabled="disabled" -->
</div>
</body>
</html>