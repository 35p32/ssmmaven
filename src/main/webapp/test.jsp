<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<script src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
	<%
		pageContext.setAttribute("APP_PATH",request.getContextPath());
	%>
	<!-- 引入bootstrap样式 -->
	<link href="${APP_PATH }/static/bootstrap/css/bootstrap.min.css" rel="stylesheet" >
	<script src="${APP_PATH }/static/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>

	<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1>wdnmd</h1>
				</div>
				<div class=" col-md-offset-10">
					<button class="btn btn-primary">新增</button>				
					<button class="btn btn-danger">删除</button>
				</div>
			</div>

			
			<div class="col-md-12">
				<table class="table table-hover">
					<tr>
						<th>#</th>
						<th>empname</th>
						<th>gender</th>
						<th>email</th>
						<th>deptName</th>
						<th>操作</th>
					</tr>
					<tr>
						<th>1</th>
						<th>zs</th>
						<th>男</th>
						<th>@asd.com</th>
						<th>测试部</th>
						<th>
							<button class="btn btn-primary btn-sm" >
								<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
								编辑
							</button>
							<button class="btn btn-danger btn-sm" >
								<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
								删除
							</button>
						</th>
					</tr>
				</table>
			</div>
        <!-- 显示分页信息 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6">分页文字信息</div>		
			<!-- 分页条信息 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
				  <ul class="pagination">
				    <li><a href="#">首页</a></li>
				    <li>
				      <a href="#" aria-label="Previous">
				        <span aria-hidden="true">&laquo;</span>
				      </a>
				    </li>
				    <li><a href="#">1</a></li>
				    <li><a href="#">2</a></li>
				    <li><a href="#">3</a></li>
				    <li><a href="#">4</a></li>
				    <li><a href="#">5</a></li>
				    <li>
				      <a href="#" aria-label="Next">
				        <span aria-hidden="true">&raquo;</span>
				      </a>
				    </li>
				    <li><a href="#">末页</a></li>
				  </ul>
				</nav>			
			</div>
		</div>
	
	
	</div>

</body>
</html>