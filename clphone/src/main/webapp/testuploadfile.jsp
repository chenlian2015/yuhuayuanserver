<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8"> 
	<title>Bootstrap 实例 - 基本表单</title>
	<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	   <script type="text/javascript">
        function submitForm() {
            console.log("submit event");
            var fd = new FormData(document.getElementById("goodsimagex"));
            fd.append("label", "WEBUPLOAD");
            $.ajax({
              url: "uploadOneFile",
              type: "POST",
              data: fd,
              processData: false,  // tell jQuery not to process the data
              contentType: false   // tell jQuery not to set contentType
            }).done(function( data ) {
            	alert(data,data);
            	$("#goodsimage").attr('src',data); 
            });
            return false;
        }
    </script>
    
</head>
<body ng-app="myApp" ng-controller="userCtrl">

<img src="" id="goodsimage"/>
  <form method="post" id="goodsimagex" name="goodsimagex" onsubmit="return submitForm();">
    	<label>选择商品文件:</label><br>
    	<input type="file" name="file" required />
		<input type="submit" value="Upload" />
    </form>
    <div id="output"></div>

</body>
</html>
