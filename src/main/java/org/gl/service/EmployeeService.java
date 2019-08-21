package org.gl.service;

import java.util.ArrayList;
import java.util.List;

import org.gl.bean.Employee;
import org.gl.bean.EmployeeExample;
import org.gl.bean.Msg;
import org.gl.bean.EmployeeExample.Criteria;
import org.gl.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class EmployeeService {
	@Autowired
	EmployeeMapper employeeMapper;
	//service层调用mapper查询所有员工
	
	
	public List<Employee>  getAll(){
//		List<Employee> emps = new ArrayList<Employee>();
//		emps.add(employeeMapper.selectByPrimaryKey(1));
//		return emps;
		return (List<Employee>) employeeMapper.selectByExampleWithDept(null);
	}

/**
 * 员工保存
 * @param employee
 */
	public void saveEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.insertSelective(employee);
	}
	
	
	//检验用户名是否是重复的
	public boolean checkUser(String name) {
		EmployeeExample ee = new EmployeeExample();
		Criteria criteria = ee.createCriteria();
		criteria.andEmpNameEqualTo(name);
		
		long count = employeeMapper.countByExample(ee);
		return count==0;
	}
	//按照员工id 找员工
	public Employee getEmp(Integer id) {
		Employee employee =  employeeMapper.selectByPrimaryKey(id);
		return employee;
	}
	//员工更新
	public void updateEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.updateByPrimaryKeySelective(employee);
	}
	//删除某个员工
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}
	//批量删除员工
	public void deleteBatch(List<Integer> ids) {
		EmployeeExample ee = new EmployeeExample();
		Criteria criteria = ee.createCriteria();
		criteria.andEmpIdIn(ids);
		employeeMapper.deleteByExample(ee);
	}
	//获取最后一个员工
	public Employee getLastEmp() {
		// TODO Auto-generated method stub
		Employee employee =  employeeMapper.selectLastEmp();
		return employee;
	}}
