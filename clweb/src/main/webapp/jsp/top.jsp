<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<header class="main-header">
    <nav class="logoContainer">
        <img src="${ctx}/assets/images/login/logo2.gif" style="margin: 10px 15px;">
    </nav>
    <nav class="navbar navbar-static-top" role="navigation">
        <ul class="myMenu">
            <c:forEach var="entity" items="${privileges}">
                <li id="rootNode${entity.functionId}" onclick="loadSecondMenu(${entity.functionId})">
                    <img src="${entity.picUrl}">
                        <%--<span class="glyphicon glyphicon-star-empty"></span>--%>
                    <span>${entity.functionName}</span>
                </li>
            </c:forEach>
            <span class="mySetting">
                        <img src="${ctx}/assets/images/common/setting.gif">
                        <ul>
                            <%--<li>修改联系方式</li>--%>
                            <li><a href="#" id="modifyPassword">修改密码</a></li>
                            <li><a href="#" id="logout">退出</a></li>
                        </ul>
                    </span>
        </ul>
    </nav>
</header>
<script>
    $("#logout").on("click", function (e) {
        $.ajax({
            type: "get",
            url: "${ctx}/logout",
            dataType: "json",
            success: function (json) {
                if ("1" === json["result"]) {
                    window.location.href = "${ctx}/jsp/login.jsp";
                } else {
                    alert("退出失败!");
                }
            },
            error: function (json) {
                alert("退出失败!");
            }
        });
        e.preventDefault;
    });

    $("#modifyPassword").on("click", function (e) {
//            loadSecondMenu(-1,checkMenu);
        //loadSecondPage('/ejiazi-merchant/jsp/rbac/changePassword.html');
    });
    function checkMenu() {
        $(".leftMenu li:eq(0)").click();
    }
</script>