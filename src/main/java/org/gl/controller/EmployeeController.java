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
 * ����Ա������
 * @author 35.32
 *
 */		
@Controller
public class EmployeeController {
		
	@Autowired
	EmployeeService employeeService;

	
	//�µ�emps������
	@RequestMapping("/emps")         //����������
	@ResponseBody
	public Msg getemps(@RequestParam(value="pn",defaultValue = "1")  Integer pn) { //�������ڷ�ҳ��ѯ,��������1
		//Ϊ�˷�ҳ��ѯ���㣬����PageHelper��ҳ���
		String orderBy = "emp_Id asc";
		PageHelper.startPage(pn,5,orderBy); //�ӵ�pnҳ��ʼ�飬һҳ��5��
		List<Employee> emps =  employeeService.getAll();
		if(emps==null) {
			System.out.println("������ǿյ��˰�");
		}
		for(Employee e:emps) {
			System.out.println(e.getEmpId()+" "+e.getEmpName());
		}
		System.out.println("===========================================================");
		//�ٴ�ʹ��pagehelper�����ֱ�Ӱ����ݽ��а�װ,ֻ�ý�pageinfo����ǰ̨������
		PageInfo pageinfo =  new PageInfo(emps,5); //��һ�����������ݼ��ϣ��ڶ���������������ʾ��ҳ��
		return Msg.success().add("pageinfo",pageinfo);
	}
	
	
	
	
//	����ʮ�ţ�ʵ���µ�emps�������� ���ַ������ض���views����� listҳ�档	
//	@RequestMapping("/emps")         //����������
//	public String getemps(@RequestParam(value="pn",defaultValue = "1")  Integer pn, Model model) { //�������ڷ�ҳ��ѯ,��������1
//		System.out.println("a");
//		//Ϊ�˷�ҳ��ѯ���㣬����PageHelper��ҳ���
//		PageHelper.startPage(pn,5); //�ӵ�pnҳ��ʼ�飬һҳ��5��
//		System.out.println("===========================================================");
//		List<Employee> emps =  employeeService.getAll();
//		if(emps==null) {
//			System.out.println("������ǿյ��˰�");
//		}
//		for(Employee e:emps) {
//			System.out.println(e.getEmpName());
//		}
//		System.out.println("===========================================================");
//		//�ٴ�ʹ��pagehelper�����ֱ�Ӱ����ݽ��а�װ,ֻ�ý�pageinfo����ǰ̨������
//		PageInfo pageinfo =  new PageInfo(emps,5); //��һ�����������ݼ��ϣ��ڶ���������������ʾ��ҳ��
//		model.addAttribute("pageinfo",pageinfo);
//		
//		return "list";
//	}
	/**
	 * ����Ա������
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
				System.out.println("�����ֶ�����:"+fielderror.getField());
				System.out.println("������Ϣ:"+fielderror.getDefaultMessage());
				errormap.put(fielderror.getField(), fielderror.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", errormap);
		}else {
			employeeService.saveEmp(employee);
			return Msg.success();
		}
	}
	
	//���Ա�������ǲ����Ѿ�����
	@ResponseBody
	@RequestMapping("/checkuser")
	public Msg checkuser(@RequestParam("empName") String empname) {
		
		String regx = "((^[a-zA-Z0-9_-]{6,16}$)|(^[\\u2E80-\\u9FFF]{2,5}))";
		//�ȼ����û�����ʽ
		if(!empname.matches(regx)) {
			return Msg.fail().add("va_msg", "�û���������6-16λ���ֵ���ϻ���2-5λ����");
		}
		//�ټ���ͬ�����ǲ����Ѿ�����
		boolean f =  employeeService.checkUser(empname);
		if(f) {
			return Msg.success();
		}else {
			return Msg.fail().add("va_msg","�û���������");
		}
	}
	@ResponseBody
	@RequestMapping("/checkemail")
	public Msg checkemail(@RequestParam("email") String email) {
		
		String regx = "(^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$)";
		//�ȼ��������ʽ
		if(!email.matches(regx)) {
			return Msg.fail().add("va_msg2", "����������ʽ");
		}
			return Msg.success();
	}
	
	
	//����id��ѯԱ��
	@ResponseBody
	@RequestMapping(value="/oneemp/{id}",method=RequestMethod.GET)
	public Msg getEmp(@PathVariable("id")  Integer id ) {
		Employee employee =  employeeService.getEmp(id);
		System.out.println(employee.getEmpName());
		return Msg.success().add("emp", employee);
	}
	
	
	
	//Ա������
	@ResponseBody
	@RequestMapping(value="/updateemp/{empId}" , method = RequestMethod.PUT)
	public Msg updateEmp(Employee employee) {
		System.out.println("��ʼ����");
		employeeService.updateEmp(employee);
		return Msg.success();
	}
	
	//ɾ��Ա��
	@ResponseBody
	@RequestMapping(value="/deleteemp/{ids}",method=RequestMethod.DELETE)
	public Msg deleteEmp(@PathVariable("ids")  String  ids) {
		//����ɾ��
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
		System.out.println("���һ�����ǣ�"+employee.getEmpName());
		return Msg.success().add("emp", employee);
	}
	
}
