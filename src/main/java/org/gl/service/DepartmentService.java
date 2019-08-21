package org.gl.service;

import java.util.List;

import org.gl.bean.Department;
import org.gl.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service //����һ��ҵ���߼����,�ϲ��ǿ�����
public class DepartmentService {
	@Autowired
	DepartmentMapper departmentMapper;
	public List<Department> getDepts() {
		// TODO Auto-generated method stub
		return departmentMapper.selectByExample(null);
	}
}
