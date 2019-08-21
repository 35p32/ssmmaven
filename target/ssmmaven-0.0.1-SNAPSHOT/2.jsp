<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<%
		pageContext.setAttribute("APP_PATH",request.getContextPath());
	%>
	<script src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
	<!-- 引入bootstrap样式 -->
	<link href="${APP_PATH }/static/bootstrap/css/bootstrap.min.css" rel="stylesheet" >
	<script src="${APP_PATH }/static/bootstrap/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- 新增学生的模态框 -->
	<div class="modal fade" id="empAddModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">员工添加</h4>
	      </div>
	      <div class="modal-body"><!-- 注册的表单 -->
			       	<form class="form-horizontal">
					  <!-- 姓名 -->
					  <div class="form-group">
					    <label  class="col-sm-2 control-label">empName</label>
					    <div class="col-sm-10">
					      <input type="text" class="form-control"    name="empName"      id="empName_add_input" placeholder="empName">
					    	<span id="" class="help-block"></span>
					    </div>
					  </div>
					  <!-- 邮箱 -->
					  <div class="form-group">
					    <label class="col-sm-2 control-label">email</label>
					    <div class="col-sm-10">
					      <input type="text" class="form-control"  name="email"      id="email_add_input" placeholder="xxx@qq.com">
					    	<span id="" class="help-block"></span>
					    </div>
					  </div>
					   <!-- 性别 -->
					   <div class="form-group">
					    <label  class="col-sm-2 control-label">gender</label>
					    <div class="col-sm-10">
					     	<label class="radio-inline">
							 <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男 
							</label>
							<label class="radio-inline">
							  <input type="radio" name="gender" id="gender2_add_input" value="F"> 女
							</label>
					    </div>
					  </div>
					   <!-- 部门 -->
					   <div class="form-group">
					    <label  class="col-sm-2 control-label">deptName</label>
					    <div class="col-sm-4">
							<!-- 部门列表显示出来 -->
							<select class="form-control"  id="dept_add_select" name="dId">
																
							</select>
					    </div>
					  </div>
					</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button type="button" class="btn btn-primary" id="emp_save_btn">保存</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="container">
			<div class="row">
				<div class="col-md-12">
					<h1>wdnmd</h1>
				</div>
				<div class=" col-md-offset-10">
					<button class="btn btn-primary" id="emp_add_modal_btn">新增</button>				
					<button class="btn btn-danger">删除</button>
				</div>
			</div>

			<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12">
				<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th>#</th>
							<th>empName</th>
							<th>gender</th>
							<th>email</th>
							<th>deptName</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
					
					</tbody>
				</table>
			</div>
		</div>
			
        <!-- 显示分页信息 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6" id="page_info_area">
			</div>
				
			<!-- 分页条信息 -->
			<div class="col-md-6" id="page_nav_area">
			</div>
		</div>
	
	
	</div>

	<!-- js -->
	<script type="text/javascript">
		//1.页面加载完成后，直接用Ajax去后台请求数据
		$(function(){
			to_page(1);
		});
		var totalempscount;
		function to_page(pn){
			$.ajax({
				url:"${APP_PATH}/emps",
				data:"pn="+pn,  //此处别写错
				type:"GET",
				success:function(result){
					//1.解析并且显示员工信息
					build_emps_table(result);
					//2.解析并且显示分页信息
					build_page_info(result);
					build_page_nav(result);
					
				}
			});
		}
		
		//1.解析并且显示员工信息的函数
		function build_emps_table(result){
			$("#emps_table tbody").empty();
			var emps =  result.extend.pageinfo.list;
			$.each(emps,function(index,item){
				var empIdTd = $("<td></td>").append(item.empId);
				var empNameTd = $("<td></td>").append(item.empName);
				var empGenderTd = $("<td></td>").append(item.gender.toLowerCase()=="m"?"男":"女");
				var empEmailTd = $("<td></td>").append(item.email);
				var deptNameTd = $("<td></td>").append(item.department.deptName);
				var editBtn = $("<button></button>").addClass("btn btn-primary btn-sm edit_btn").append($("<span></span>").addClass("glyphicon glyphicon-pencil")).append("编辑");
				editBtn.attr("edit-id",item.empId);
				var delBtn =  $("<button></button>").addClass("btn btn-danger btn-sm delete_btn").append($("<span></span>").addClass("glyphicon glyphicon-trash")).append("删除");
				delBtn.attr("del-id",item.empId);
				var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
				$("<tr> </tr>").append(empIdTd).append(empNameTd).append(empGenderTd).append(empEmailTd).append(deptNameTd).append(btnTd).appendTo("#emps_table tbody");
			});
		}		
		//2.解析并且显示分页信息的函数
		//2.1分页信息
		function build_page_info(result){
			$("#page_info_area").empty();
			$("#page_info_area").append("当前第 "+result.extend.pageinfo.pageNum+" 页, 总共有 "+result.extend.pageinfo.pages+" 页,共存在 "+"<span id='countemps'>"+result.extend.pageinfo.total+"</span>"+" 条记录");
			totalempscount = result.extend.pageinfo.total+1;
		}
		//2.2 分页条
		function build_page_nav(result){
			$("#page_nav_area").empty();
			
			var ul =  $("<ul></ul>").addClass("pagination");
			var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href","#"));
			var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;"));
			//构建之后，根据页码进行能否点击的判断
						if(result.extend.pageinfo.hasPreviousPage == false){
							firstPageLi.addClass("disabled");
							prePageLi.addClass("disabled");
						}else{
							//为元素添加点击翻页的事件
							firstPageLi.click(function(){
								to_page(1);
							});
							prePageLi.click(function(){
								to_page(result.extend.pageinfo.pageNum -1);
							});
						}
			var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;"));
			var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href","#"));
			//构建之后，根据页码进行能否点击的判断
						if(result.extend.pageinfo.hasNextPage == false){
							nextPageLi.addClass("disabled");
							lastPageLi.addClass("disabled");
						}else{
							nextPageLi.click(function(){
								to_page(result.extend.pageinfo.pageNum +1);
							});
							lastPageLi.click(function(){
								to_page(result.extend.pageinfo.pages);
							});
						}
			ul.append(firstPageLi).append(prePageLi);
			
			//接下来处理 分页的数字
			$.each(result.extend.pageinfo.navigatepageNums,function(index,item){ //页码数组
				var numli = $("<li></li>").append($("<a></a>").append(item));
				if(result.extend.pageinfo.pageNum == item){
					numli.addClass("active");
				}
				numli.click(function(){
					to_page(item);
				});	
				ul.append(numli);
			});
			ul.append(nextPageLi);
			ul.append(lastPageLi);
			var navEle =$("<nav></nav>").append(ul);	
			navEle.appendTo("#page_nav_area");
		}
		//点击新增按钮 弹出模态框
		$("#emp_add_modal_btn").click(function(){
			//通过Ajax 查询出来部门信息，显示在部门列表中	
			getDepts("#dept_add_select");
			$("#empAddModel").modal({
				backdrop:"static"
			});			
		});
		//查出所有的部门信息
		function getDepts(ele){
			$(ele).empty();
			$.ajax({
				url:"${APP_PATH}/depts",
				type:"GET",
				success:function(result){
					$.each(result.extend.depts,function(){
						var optionEle = $("<option></option>").append(this.deptName).attr("value",this.deptId); 
						optionEle.appendTo("#dept_add_select");
					});
				}
			});
		}
		
		
		//手写校验方法
		function  validate_add_form(){
			//1、拿到要校验的数据，使用正则表达式
			var empName = $("#empName_add_input").val();
			var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
			if(!regName.test(empName)){
				//alert("用户名可以是2-5位中文或者6-16位英文和数字的组合");
				show_validate_msg("#empName_add_input", "error", "用户名可以是2-5位中文或者6-16位英文和数字的组合");
				/*$("#empName_add_input").parent().addClass("has-error");
				$("#empName_add_input").next("span").text("用户名可以是2-5位中文或者6-16位英文和数字的组合");*/
				return false;
			}else{
				/*$("#empName_add_input").parent().addClass("has-success");
				$("#empName_add_input").next("span").text("");*/
				show_validate_msg("#empName_add_input", "success", "");
			};
			
			//2、校验邮箱信息
			var email = $("#email_add_input").val();
			var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if(!regEmail.test(email)){
				//alert("邮箱格式不正确");
				//应该清空这个元素之前的样式
				show_validate_msg("#email_add_input", "error", "邮箱格式不正确");
				 /*$("#email_add_input").parent().addClass("has-error");
				$("#email_add_input").next("span").text("邮箱格式不正确"); */
				return false;
			}else{
				/* $("#email_add_input").parent().addClass("has-success");
				 $("#email_add_input").next("span").text(""); */
				show_validate_msg("#email_add_input", "success", "");
			}
			return true;
		}
		function show_validate_msg(ele,status,msg){
			$(ele).parent().removeClass("has-success has-error");
			$(ele).next("span").text("");
			if("success"==status){
				$(ele).parent().addClass("has-success");
				$(ele).next("span").text(msg);
			}else if("error" == status){
				$(ele).parent().addClass("has-error");
				$(ele).next("span").text(msg);
			}
		}
		//为员工姓名的输入框绑定一个change事件
		$("#empName_add_input").change(function (){
			var empName = this.value;
			$.ajax({
				url: "${APP_PATH}/checkuser",
				data: "empName="+empName,
				type: "POST",
				success: function(result){
					if(result.code == 100){
						show_validate_msg("#empName_add_input","success","用户名可用");					
						$("#emp_save_btn").attr("ajax-va","success");
					}else{
						show_validate_msg("#empName_add_input","error","用户名不可用");	
						 $("#emp_save_btn").attr("ajax-va","error");
					}
				}
			});
		});
		
		//点击保存
		$("#emp_save_btn").click(function(){
			//将模态框中填写的表单数据提交给服务器端
			//先对格式进行校验
			if(!validate_add_form()){
				return false;
			}
			//再看看同户名是不是重复的
			if($(this).attr("ajax-va")=="error"){
				return false;
			}else{
				alert("成功了");
			}
			$.ajax({
				url:"${APP_PATH}/emp",
				type:"POST",
				data: "empId="+totalempscount+"&"+$("#empAddModel form").serialize(),
				success:function(result){
					// 在加入了成员之后
					// 1.关闭模态框
					$("#empAddModel").modal('hide');
					// 2.来到最后一页，显示刚才保存的数据,发送ajax请求找到最后一页
					to_page(totalempscount);
				}
			});
			//alert((totalempscount+1));
			//alert("empId="+(parseInt($("#countemps").text())+1)+"&"+$("#empAddModel form").serialize());
		});
		
		
	</script>


</body>
</html>