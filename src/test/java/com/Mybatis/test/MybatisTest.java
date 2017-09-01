package com.Mybatis.test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ssm.Mapper.UserMapper;
import com.ssm.Service.UserService;
import com.ssm.model.User;

public class MybatisTest {
	public static void main(String[] args) throws IOException {
		ApplicationContext	ctx=new ClassPathXmlApplicationContext("beans.xml");
		UserMapper um=ctx.getBean("userMapper",UserMapper.class);
		//查询用户表中主键为2的用户
		User user=um.selectByid(2);																			
		System.out.println("用户名为："+user.getUsername());
		
		addUser(ctx);
	}
	
	public static  void deleteTest(ApplicationContext ctx){
		UserService service = ctx.getBean(UserService.class);
		service.removeUser(2);
	}
	
	public static void addUser(ApplicationContext ctx){
		UserService service = ctx.getBean(UserService.class);
		User user = new User ();
		user.setId(1024);
		user.setUsername("嘿客");
		service.add(user);
	}
}
