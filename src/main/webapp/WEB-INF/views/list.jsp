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
						<th>empName</th>
						<th>gender</th>
						<th>email</th>
						<th>deptName</th>
						<th>操作</th>
					</tr>
					<c:forEach items="${pageinfo.list }" var="emp">
						<tr>
							<th>${emp.empId }</th>
							<th>${emp.empName }</th>
							<th>${emp.gender=="M"?"男":"女" }</th>
							<th>${emp.email }</th>
							<th>${emp.department.deptName }</th>
							<th>
								<button class="btn btn-primary btn-sm">
									<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
									编辑
								</button>
								<button class="btn btn-danger btn-sm">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
									删除
								</button>
							</th>
						</tr>
					</c:forEach>
				</table>
			</div>
        <!-- 显示分页信息 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6">
				当前第${pageinfo.pageNum }页,总共有${pageinfo.pages }页,共${pageinfo.total }条记录
			</div>	
			
			
				
			<!-- 分页条信息 -->
			<div class="col-md-6">
				<nav aria-label="Page navigation">
				  <ul class="pagination">
				    <li><a href="${APP_PATH }/emps?pn=1">首页</a></li>
					<!-- 用于实现上一页逻辑判断 -->
				    <c:if test="${pageinfo.hasPreviousPage }">
						<li>
					      <a href="${APP_PATH }/emps?pn=${pageInfo.pageNum-1}" aria-label="Previous">
					        <span aria-hidden="true">&laquo;</span>
					      </a>
				    	</li>
					</c:if>
				    <!-- 用于实现页码高亮和页面跳转 -->
				   <c:forEach items="${pageinfo.navigatepageNums }" var="page_Num">
						<c:if test="${page_Num == pageinfo.pageNum }">
							<li class="active"><a href="#">${page_Num }</a></li>
						</c:if>
						<c:if test="${page_Num != pageinfo.pageNum }">
							<li><a href="${APP_PATH }/emps?pn=${page_Num }">${page_Num }</a></li>
						</c:if>
					</c:forEach>
					<!--实现下一页逻辑判断 -->
				   <c:if test="${pageinfo.hasNextPage }">
						<li><a href="${APP_PATH }/emps?pn=${pageinfo.pageNum+1 }"
							aria-label="Next"> <span aria-hidden="true">&raquo;</span>
						</a></li>
					</c:if>
				    <li><a href="${APP_PATH }/emps?pn=${ pageinfo.pages}">末页</a></li>
				  </ul>
				</nav>			
			</div>
		</div>
	
	
	</div>

</body>
</html>