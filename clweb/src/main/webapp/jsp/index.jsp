<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>e家子商户管理平台</title>
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="${ctx}/assets/bootstrap/css/bootstrap.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${ctx}/assets/css/AdminLTE.css">
    <link rel="stylesheet" href="${ctx}/assets/css/areaSelect.css">
    <link rel="icon" href="${ctx}/assets/images/logo_red.jpg">
    <!--[if lt IE 9]>
    <script src="${ctx}/assets/js/respond.min.js"></script>
    <script src="${ctx}/assets/js/html5shiv.min.js"></script>
    <![endif]-->

   <script src="<%=request.getContextPath()%>/assets/js/jQuery-2.1.4.min.js" type="text/javascript"></script>

    <script>
        function onLoad() {
            var merchant =${merchantViewDto};
            $("#merchantuser").html($.param(merchant));
        }
    </script>
</head>

<body onload="onLoad()">
<div id="merchantuser">init</div>

<div class="vFlexContainer">
    <!--头部logo和一级导航菜单-->
    <div id="headerContainer"></div>
    <div class="content-wrapper flexContainer">
        <!--左侧菜单-->
        <div id="leftMenu" class="leftMenu">
        </div>
        <!--主内容区域-->
        <div id="rightContainer" class="rightContainer"></div>
    </div><!-- ./wrapper -->
</div>

</body>
<script src="${ctx}/assets/js/jQuery-2.1.4.min.js"></script>
<script src="${ctx}/assets/js/jquery.validate.min.js"></script>
<script src="${ctx}/assets/js/underscore/underscore-min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="${ctx}/assets/js/bootstrap.min.js"></script>
<!-- Sparkline -->
<script src="${ctx}/assets/js/layer/layer.js"></script>
<script src="${ctx}/assets/js/laydate/laydate.js"></script>
<script src="${ctx}/assets/js/underscore/underscore.js"></script>
<script src="${ctx}/assets/js/customer.js"></script>
<script src="${ctx}/assets/js/custome/main.js"></script>
<script src="${ctx}/assets/js/angular.min.js"></script>
<script src="${ctx}/assets/js/angular-messages.js"></script>
</html>