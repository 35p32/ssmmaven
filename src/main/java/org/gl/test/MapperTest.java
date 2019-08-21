package org.gl.test;

import org.apache.ibatis.session.SqlSession;
import org.gl.bean.Department;
import org.gl.bean.Employee;
import org.gl.dao.DepartmentMapper;
import org.gl.dao.EmployeeMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//���� ����sql����mapper
@Component
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class MapperTest {
	//ֱ��ʹ��Spring�����Զ�ע�����
	@Autowired
	public DepartmentMapper departmentMapper;
	@Autowired
	public EmployeeMapper empolyeeMapper;
	@Autowired
	SqlSession sqlsession;
	@Test
	public void a1a234r() {
		//departmentMapper.insertSelective(new Department(1,"���в�"));
		empolyeeMapper.insertSelective(new Employee(4566,"ls","m","xxxx",1));
//		EmployeeMapper mapper = sqlsession.getMapper(EmployeeMapper.class);
//		for(int i=0;i<1000;i++) {
//			mapper.insertSelective(new Employee(i+7,"��"+i+"���ܵ�","f",i+"@gl.com",1));
//		}
//		System.out.println("�������");
		
	}
}
