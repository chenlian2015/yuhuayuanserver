<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Bootstrap 实例 - 基本表单</title>
<link rel="stylesheet"
	href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<script
	src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="<%=request.getContextPath()%>/js/common.js"></script>

<script type="text/javascript">
        function submitForm() {
        	
            console.log("submit event");
            var fd = new FormData(document.getElementById("goodsimage"));
            fd.append("label", "WEBUPLOAD");
            $.ajax({
            url: "<%=request.getContextPath()%>/uploadOneFile",
			type : "POST",
			data : fd,
			processData : false, // tell jQuery not to process the data
			contentType : false
		// tell jQuery not to set contentType
		}).done(function(data) {
			$("#goodsimageurl").attr('src', data);
		});
		return false;
	}


	
	
</script>
</head>
<body ng-app="myApp" ng-controller="userCtrl">

	<div class="container">
		<div class="row clearfix">
			<div class="col-md-12 column">

				<div class="form-group">
					<label class="sr-only" for="goodsellprice">折后价格</label> <input
						type="text" class="form-control" id="goodsellprice"
						name="goodsellprice" placeholder="请输入折扣后价格">
				</div>

				<div class="form-group">
					<label class="sr-only" for="goodsorignprice">打折前价格</label> <input
						type="text" class="form-control" id="goodsorignprice"
						name="goodsorignprice" placeholder="请输入当前打折前价格">
				</div>

				<div class="form-group">
					<label class="sr-only" for="goodsname">商品名称</label> <input
						type="text" class="form-control" id="goodsname" name="goodsname"
						placeholder="请输入商品名称">
				</div>

				<div class="form-group">
					<label class="sr-only" for="goodsdescribe">商品描述</label> <input
						type="text" class="form-control" id="goodsdescribe"
						name="goodsdescribe" placeholder="请输入商品描述">
				</div>

				<div class="form-group">
					<img src="" id="goodsimageurl" name="goodsimageurl" />
					<form method="post" id="goodsimage" name="goodsimage">
						<label>选择商品图片:</label><br> <input type="file"
							onchange="return submitForm();" name="file" required></input>
					</form>
				</div>

				<div class="checkbox">
					<label><input type="checkbox" id="wheatheronline"
						name="wheatheronline" />是否上架</label>
				</div>


				<button type="submit" class="btn btn-default"
					onclick="MakeForm(new Array('goodsellprice','goodsorignprice','goodsname','goodsdescribe','goodsimageurl','wheatheronline'),'<%=request.getContextPath()%>/addgoods.do')">添加该商品</button>


			</div>
		</div>
	</div>

</body>
</html>
