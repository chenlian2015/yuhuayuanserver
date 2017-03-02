<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="icon" href="<%=request.getContextPath()%>/images/favicon.ico"
          mce_href="<%=request.getContextPath()%>/images/favicon.ico" type="image/x-icon">
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico"
          mce_href="<%=request.getContextPath()%>/images/favicon.ico" type="image/x-icon">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css"/>
    <script src="<%=request.getContextPath()%>/js/jquery-1.12.0.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/login.js"></script>
</head>
<body>
<script>
var merchant =${merchantViewDto};
$("#merchantuser").innerText(JSON.stringify(merchant));
</script>
<div id="merchantuser"></div>
</body>
</html>