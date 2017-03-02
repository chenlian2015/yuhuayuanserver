<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="icon" href="<%=request.getContextPath()%>/images/favicon.ico" mce_href="<%=request.getContextPath()%>/images/favicon.ico" type="image/x-icon">
<link rel="shortcut icon" href="<%=request.getContextPath()%>/images/favicon.ico" mce_href="<%=request.getContextPath()%>/images/favicon.ico" type="image/x-icon">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css"/>
<script src="<%=request.getContextPath()%>/js/jquery-1.12.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/login.js"></script>
</head>
<body>
<div id="home">
    <form id="login" class="current1" method="post" action="<%=request.getContextPath()%>/login">           
        <h3>用户登入</h3>
        <img class="avator" src="<%=request.getContextPath()%>/images/login.jpg" width="96" height="96"/>
        <label>邮箱/名称<input type="text" name="username" style="width:215px;" /><span>邮箱为空</span></label>
        <label>密码<input type="password" name="password"  /><span>密码为空</span></label>
        <button type="submit" >登入</button>   
    </form> 
</div>
</body>
</html>