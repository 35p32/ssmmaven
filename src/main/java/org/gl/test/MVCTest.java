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
 * 使用Spring测试模块，模拟请求某一个controller
 * @author 35.32
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations= {"classpath:applicationContext.xml","classpath:applicationContext-controller.xml"})
public class MVCTest {
	@Autowired
	WebApplicationContext context;
	
	//新建一个虚拟的mvc请求，也就是页面跳转请求
	MockMvc mockMvc;
	@Before
	public  void initmocMvc() {
		System.out.println("初始化了mockMvc");
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	@Test
	public void testpageInfo() throws Exception {
		System.out.println("开始执行测试");
		MvcResult  mvcResult  = mockMvc.perform(MockMvcRequestBuilders.get("comtroller/emps").param("pn", "1")).andReturn();
		System.out.println("获取request");
		MockHttpServletRequest request = mvcResult.getRequest();
		System.out.println("获取pageinfo");
		PageInfo pageinfo = (PageInfo) request.getAttribute("pageinfo");
		if(pageinfo==null) {
			System.out.println("pageinfo是空的");
		}if(pageinfo!=null) {
			System.out.println("当前页码是: "+pageinfo.getPageNum());
			System.out.println("总页码是:"+ pageinfo.getPages());
			System.out.println("总的记录数是:"+pageinfo.getTotal());
			System.out.println("再页面连续显示的页码是:");
			int[]  nums = pageinfo.getNavigatepageNums();
			for(int i:nums) {
				System.out.println(" "+i);
			}
			//获取员工数据
			List<Employee> emps = pageinfo.getList();
			for(Employee employee:emps) {
				System.out.println("ID:"+employee.getEmpId()+"姓名:"+employee.getEmpName());
			}
		}	
	}
}
