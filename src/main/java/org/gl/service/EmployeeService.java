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
	//service�����mapper��ѯ����Ա��
	
	
	public List<Employee>  getAll(){
//		List<Employee> emps = new ArrayList<Employee>();
//		emps.add(employeeMapper.selectByPrimaryKey(1));
//		return emps;
		return (List<Employee>) employeeMapper.selectByExampleWithDept(null);
	}

/**
 * Ա������
 * @param employee
 */
	public void saveEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.insertSelective(employee);
	}
	
	
	//�����û����Ƿ����ظ���
	public boolean checkUser(String name) {
		EmployeeExample ee = new EmployeeExample();
		Criteria criteria = ee.createCriteria();
		criteria.andEmpNameEqualTo(name);
		
		long count = employeeMapper.countByExample(ee);
		return count==0;
	}
	//����Ա��id ��Ա��
	public Employee getEmp(Integer id) {
		Employee employee =  employeeMapper.selectByPrimaryKey(id);
		return employee;
	}
	//Ա������
	public void updateEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.updateByPrimaryKeySelective(employee);
	}
	//ɾ��ĳ��Ա��
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}
	//����ɾ��Ա��
	public void deleteBatch(List<Integer> ids) {
		EmployeeExample ee = new EmployeeExample();
		Criteria criteria = ee.createCriteria();
		criteria.andEmpIdIn(ids);
		employeeMapper.deleteByExample(ee);
	}
	//��ȡ���һ��Ա��
	public Employee getLastEmp() {
		// TODO Auto-generated method stub
		Employee employee =  employeeMapper.selectLastEmp();
		return employee;
	}}
