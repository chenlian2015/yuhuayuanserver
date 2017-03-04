<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
</body>
</html>