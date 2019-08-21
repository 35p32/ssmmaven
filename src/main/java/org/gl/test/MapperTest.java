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

//测试 各种sql语句的mapper
@Component
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class MapperTest {
	//直接使用Spring特性自动注入对象
	@Autowired
	public DepartmentMapper departmentMapper;
	@Autowired
	public EmployeeMapper empolyeeMapper;
	@Autowired
	SqlSession sqlsession;
	@Test
	public void a1a234r() {
		//departmentMapper.insertSelective(new Department(1,"调研部"));
		empolyeeMapper.insertSelective(new Employee(4566,"ls","m","xxxx",1));
//		EmployeeMapper mapper = sqlsession.getMapper(EmployeeMapper.class);
//		for(int i=0;i<1000;i++) {
//			mapper.insertSelective(new Employee(i+7,"第"+i+"个弟弟","f",i+"@gl.com",1));
//		}
//		System.out.println("批量完成");
		
	}
}
