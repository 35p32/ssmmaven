package org.gl.test;
import org.gl.bean.Department;
import org.gl.dao.DepartmentMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MapperTest2 {
	public static void main(String[] args) {
		ApplicationContext ac  = new ClassPathXmlApplicationContext("applicationContext.xml");
		DepartmentMapper temp =  ac.getBean(DepartmentMapper.class);
		temp.insert(new Department(3,"qunimabi"));
	}
}	
