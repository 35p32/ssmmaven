package org.gl.test;

import java.util.List;

import org.gl.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;

/**
 * ʹ��Spring����ģ�飬ģ������ĳһ��controller
 * @author 35.32
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {"classpath:applicationContext.xml","classpath:applicationContext-controller.xml"})
public class MVCTest {
	@Autowired
	WebApplicationContext context;
	
	//�½�һ�������mvc����Ҳ����ҳ����ת����
	MockMvc mockMvc;
	@Before
	public  void initmocMvc() {
		System.out.println("��ʼ����mockMvc");
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	@Test
	public void testpageInfo() throws Exception {
		System.out.println("��ʼִ�в���");
		MvcResult  mvcResult  = mockMvc.perform(MockMvcRequestBuilders.get("comtroller/emps").param("pn", "1")).andReturn();
		System.out.println("��ȡrequest");
		MockHttpServletRequest request = mvcResult.getRequest();
		System.out.println("��ȡpageinfo");
		PageInfo pageinfo = (PageInfo) request.getAttribute("pageinfo");
		if(pageinfo==null) {
			System.out.println("pageinfo�ǿյ�");
		}if(pageinfo!=null) {
			System.out.println("��ǰҳ����: "+pageinfo.getPageNum());
			System.out.println("��ҳ����:"+ pageinfo.getPages());
			System.out.println("�ܵļ�¼����:"+pageinfo.getTotal());
			System.out.println("��ҳ��������ʾ��ҳ����:");
			int[]  nums = pageinfo.getNavigatepageNums();
			for(int i:nums) {
				System.out.println(" "+i);
			}
			//��ȡԱ������
			List<Employee> emps = pageinfo.getList();
			for(Employee employee:emps) {
				System.out.println("ID:"+employee.getEmpId()+"����:"+employee.getEmpName());
			}
		}	
	}
}
