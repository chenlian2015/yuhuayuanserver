<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="initial-scale=1.0, user-scalable=no, width=device-width">
<title>善美服务</title>
<style>
*, html {
	padding: 0px;
	margin: 0px;
	font-size: 62.5%;
}

body {
	font-size: 1rem;
	background: #f5f5f5;
}

.header, .header img {
	width: 100%;
	vertical-align: top;
}


a, a:hover {
	color: #000;
	text-decoration: none;
}

.container {
	display: flex;
	align-items: center;
	text-align: left;
	justify-content: space-between;
	align-content: center;
	flex-direction: row;
	flex-wrap: wrap;
	padding: 10px;;
}

.item {
	-webkit-flex-grow: 0;
	flex-grow: 0;
	flex-shrink: 0;
	box-sizing: border-box;
	border: 0px solid #fc8c84;
	background: #fff;;
	width: 48.5%;
	margin-bottom: 10px;;
	text-align: center;
}

.goodsImage {
	margin: 0px auto;
	width: 100%;
	position: relative;
}

.goodsImage img {
	width: 100%;
}

.title {
	text-align: left;
	padding-left: 1rem;
	overflow-x: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	font-size: 1.5rem;
	padding: 0.5rem 1rem 0.5rem 1rem;
	line-height: 2rem;
	height: 4rem;;
}

.price {
	display: flex;
	justify-content: space-around;
	padding: 1rem 0rem;
}

.currentPrice {
	font-family: "微软雅黑 Light";
	font-size: 1.5rem;
	color: #eb7978;
	font-weight: 700;
}

.oldPrice {
	text-decoration: line-through;
	color: #d3d3d3;
	font-size: 1.3rem;
}

.discount {
	position: absolute;
	right: 1rem;
	height: 4.5rem;
	line-height: 4.5rem;
	width: 4.5rem;
	display: inline-block;
	background-size: contain;
	border-radius: 50%;
	bottom: 0;
	color: #fff;
	font-family: myFontDin;
	font-size: 2rem;
}

.discount i {
	font-style: normal;
	font-size: 1rem;
	font-family: myFontDin;
}
</style>

</head>
<body>
	<div class="container">

		<c:forEach items="${listGoods}" var="goods">
			<div class="item">
				<a href="">
					<div class="goodsImage">
						<img src="http://ejiaziimg.goodaa.com.cn/activity/20160812/H5_07.png" alt=""> <span
							class="discount"><c:out value="${goods.goodsid}" /><i>折</i></span>
					</div>
					<p class="title">
						<c:out value="${goods.goodsname}" />
						<br />
						<c:out value="${goods.goodsdescribe}" />
					</p>
					<div class="price">
						<span class="currentPrice">￥<c:out
								value="${goods.goodsellprice}" /></span> <span class="oldPrice">原价:￥<c:out
								value="${goods.goodsorignprice}" /></span>
					</div>
				</a>
			</div>
		</c:forEach>
	</div>
</body>
</html>