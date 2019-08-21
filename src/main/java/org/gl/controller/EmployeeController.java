package org.gl.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.gl.bean.Employee;
import org.gl.bean.EmployeeExample;
import org.gl.bean.EmployeeExample.Criteria;
import org.gl.bean.Msg;
import org.gl.dao.EmployeeMapper;
import org.gl.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 处理员工请求
 * @author 35.32
 *
 */		
@Controller
public class EmployeeController {
		
	@Autowired
	EmployeeService employeeService;

	
	//新的emps控制器
	@RequestMapping("/emps")         //可能有问题
	@ResponseBody
	public Msg getemps(@RequestParam(value="pn",defaultValue = "1")  Integer pn) { //参数用于分页查询,不传就是1
		//为了分页查询方便，引入PageHelper分页插件
		String orderBy = "emp_Id asc";
		PageHelper.startPage(pn,5,orderBy); //从第pn页开始查，一页有5个
		List<Employee> emps =  employeeService.getAll();
		if(emps==null) {
			System.out.println("很早就是空的了啊");
		}
		for(Employee e:emps) {
			System.out.println(e.getEmpId()+" "+e.getEmpName());
		}
		System.out.println("===========================================================");
		//再次使用pagehelper插件，直接把数据进行包装,只用将pageinfo交给前台就行了
		PageInfo pageinfo =  new PageInfo(emps,5); //第一个参数是数据集合，第二个参数是连续显示的页数
		return Msg.success().add("pageinfo",pageinfo);
	}
	
	
	
	
//	八月十九，实现新的emps控制器， 这种方法是重定向到views下面的 list页面。	
//	@RequestMapping("/emps")         //可能有问题
//	public String getemps(@RequestParam(value="pn",defaultValue = "1")  Integer pn, Model model) { //参数用于分页查询,不传就是1
//		System.out.println("a");
//		//为了分页查询方便，引入PageHelper分页插件
//		PageHelper.startPage(pn,5); //从第pn页开始查，一页有5个
//		System.out.println("===========================================================");
//		List<Employee> emps =  employeeService.getAll();
//		if(emps==null) {
//			System.out.println("很早就是空的了啊");
//		}
//		for(Employee e:emps) {
//			System.out.println(e.getEmpName());
//		}
//		System.out.println("===========================================================");
//		//再次使用pagehelper插件，直接把数据进行包装,只用将pageinfo交给前台就行了
//		PageInfo pageinfo =  new PageInfo(emps,5); //第一个参数是数据集合，第二个参数是连续显示的页数
//		model.addAttribute("pageinfo",pageinfo);
//		
//		return "list";
//	}
	/**
	 * 单个员工保存
	 * @return
	 */
	@RequestMapping(value="/emp",method=RequestMethod.POST)
	@ResponseBody
	public Msg saveEmp(@Valid Employee employee,BindingResult bindingresult) {
		/*System.out.println(employee.getEmpId());
		System.out.println(employee.getEmpName());
		System.out.println(employee.getGender());
		System.out.println(employee.getEmail());
		System.out.println(employee.getDepartment());*/
		if(bindingresult.hasErrors()) {
			List<FieldError> errors = bindingresult.getFieldErrors();
			Map<String,Object> errormap = new HashMap<String, Object>();
			for(FieldError fielderror:errors) {
				System.out.println("错误字段名是:"+fielderror.getField());
				System.out.println("错误信息:"+fielderror.getDefaultMessage());
				errormap.put(fielderror.getField(), fielderror.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", errormap);
		}else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}
	}
	
	//检查员工姓名是不是已经存在
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName") String empname) {
		
		String regx = "((^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}))";
		//先检验用户名格式
		if(!empname.matches(regx)) {
			return Msg.fail().add("va_msg", "用户名必须是6-16位数字的组合或者2-5位中文");
		}
		//再检验同户名是不是已经存在
		boolean f =  employeeService.checkUser(empname);
		if(f) {
			return Msg.success();
		}else {
			return Msg.fail().add("va_msg","用户名不可用");
		}
	}
	@ResponseBody
	@RequestMapping("/checkemail")
	public Msg checkemail(@RequestParam("email") String email) {
		
		String regx = "(^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$)";
		//先检验邮箱格式
		if(!email.matches(regx)) {
			return Msg.fail().add("va_msg2", "请更正邮箱格式");
		}
			return Msg.success();
	}
	
	
	//根据id查询员工
	@ResponseBody
	@RequestMapping(value="/oneemp/{id}",method=RequestMethod.GET)
	public Msg getEmp(@PathVariable("id")  Integer id ) {
		Employee employee =  employeeService.getEmp(id);
		System.out.println(employee.getEmpName());
		return Msg.success().add("emp", employee);
	}
	
	
	
	//员工更新
	@ResponseBody
	@RequestMapping(value="/updateemp/{empId}" , method = RequestMethod.PUT)
	public Msg updateEmp(Employee employee) {
		System.out.println("开始更新");
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	//删除员工
	@ResponseBody
	@RequestMapping(value="/deleteemp/{ids}",method=RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("ids")  String  ids) {
		//批量删除
		if(ids.contains("-")) {
			List<Integer> list = new ArrayList<Integer>();
			String[] allid = ids.split("-");
			for(String id : allid) {
				list.add(Integer.parseInt(id));
			}
			employeeService.deleteBatch(list);
		}else {
			int id=Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Msg.success();
	}
	
	@ResponseBody
	@RequestMapping(value="/lastemp")
	public Msg deleteEmp() {
		Employee employee =  employeeService.getLastEmp();
		System.out.println("最后一个人是："+employee.getEmpName());
		return Msg.success().add("emp", employee);
	}
	
}
