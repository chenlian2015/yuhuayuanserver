<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here xuz</title>

	<link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">  
	<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
	<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<script
	src="<%=request.getContextPath()%>/js/common.js"></script>
	
<script>
function deleteOneGoods(goodsId)
{
	MakeFormNameValue('goodsid', goodsId, '<%=request.getContextPath()%>/delgoods.do');
}

</script>
</head>
<body>

<button type="button" class="btn btn-success" onclick="window.location='<%=request.getContextPath()%>/jsp/addgoods.jsp'">添加商品</button>
<table class="table">
	<caption>服务列表</caption>
	<thead>
		<tr>
		    <th>图片</th>
			<th>产品名</th>
			<th>产品原价</th>
			<th>产品折扣价</th>
			<th>描述</th>
			<th>产品状态</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${listGoods}" var="goods"> 
		<tr class="active">
		<td><img height="50" width="50" src="<c:out value="${goods.goodsimageurl}"/>" /></td>
			<td><c:out value="${goods.goodsname}"/></td>
			<td><c:out value="${goods.goodsorignprice}"/></td>
			<td><c:out value="${goods.goodsellprice}"/></td>
			<td><c:out value="${goods.goodsdescribe}"/></td>
			<td><c:out value="${goods.goodsstatus}"/></td>
			<td><button onclick="deleteOneGoods(<c:out value="${goods.id}"/>)">删除</button></td>
		</tr>
	</c:forEach>  
	
	</tbody>
</table>
</body>
</html>