<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
%>
<style>
    html, body {
        padding: 0px;
        margin: 0px;
        position: relative;
        height: 100%;
        width: 100%;
    }
</style>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <link rel="icon" href="<%=request.getContextPath()%>/assets/images/logo_red.jpg">
    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/assets/css/login.css">
</head>
<body class="login">
<div class="header">
    <div class="container">
        <div class="logo-box">
            <img src="<%=request.getContextPath()%>/assets/images/login/logo2.gif" alt="" class="logo">

        </div>
    </div>
</div>
<div class="container clearfix main">
    <div class="leftpanelinner fl" style="display: inline-block;">
        <div style="background:url('<%=request.getContextPath()%>/assets/images/login/leftbg.png');width:530px;height:380px;"></div>
    </div>
    <div class="mainpanel fr" style="display: inline-block;position: relative;right: 0px;left: 30px;;">
        <div class="login-form-box">
            <form method="POST" id="inputForm">
                <div class="title">
                    <span>用户登录</span>
                </div>
                <div class="body">
                    <div class="username">
                        <input type="text" id="userCode" name="userCode"
                               style="background-color: #fff!important;border: none;outline: none">
                    </div>
                    <div class="password" style="width: 100%;">
                        <div class="left" style="width: 100%;">
                            <input type="password" id="password"
                                   style="background-color: #fff!important;border: none;outline: none" value=""
                                   name="password">
                        </div>
                    </div>
                    <button type="button" class="login-btn" id="login_submit">登录</button>
                    <%--<div class="tip">--%>
                    <%--您还没有账号？<a href="<%=basePath%>merchantUser/signup">立即注册</a>--%>
                    <%--</div>--%>
                </div>
                <div class="footer">
                    <span class="l">联系客服：</span>
                    <span class="color-39aa8f">4008-231-212</span>
                </div>
            </form>
        </div>
    </div>
</div>
<div class="main-footer">
    copyright by ejiazi.com
</div>
<script src="<%=request.getContextPath()%>/assets/js/jQuery-2.1.4.min.js"></script>
<script type="text/javascript">

    $("#login_submit").on("click", function () {
        if ($("#userCode").val() == '') {
            alert("请填写用户名");
            return;
        }
        if ($("#password").val() == '') {
            alert("请填写密码");
            return;
        }
        $.ajax({
            type: "post",
            url: "<%=request.getContextPath()%>/login",
            data: $('#inputForm').serialize(),
            dataType: "json",
            success: function (json) {
                if ("200" === json["code"]) {
                    window.location.href = "<%=request.getContextPath()%>/index.do";
                } else {
                    alert("用户名或者密码错误!");
                }
            },
            error: function (json) {
                alert("登录失败!");
            }
        });
    });
</script>
</body>
</html>