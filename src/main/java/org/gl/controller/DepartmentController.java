package org.gl.controller;

import java.util.List;

import org.gl.bean.Department;
import org.gl.bean.Msg;
import org.gl.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理和部门有关的请求
 * @author 35.32
 *
 */
@Controller
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	
	/**
	 * 返回所有的部门信息
	 */
	@ResponseBody
	@RequestMapping("/depts")
	public Msg  getDepts() {
		List<Department> list = departmentService.getDepts();
		return Msg.success().add("depts",list);
	}
}
