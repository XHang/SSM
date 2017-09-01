package com.ssm.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.Service.UserService;
import com.ssm.model.User;
import com.ssm.vo.PageParameter;

/**
 * 处理用户信息的控制器
 * 实现最基础的增删改查
 * @author Mr-hang
 *
 */

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	/**
	 * 查
	 * 
	 * 通过用户ID取到用户，并展现在页面上
	 * @param model
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getUser")
	public String getUsers(ModelMap model,String userId){
		model.addAttribute("user", userService.getUser(Integer.parseInt(userId)));
		return "example";
	}
	
	
	
	/**
	 * 增
	 * 保存一个用户对象
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addUser")
	public String add (User user){
		userService.saveUser(user);
		return "恭喜你生下了一个大胖子";		
	}
	
	/**
	 * 删
	 * 根据ID删除用户对象
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public String delete (int id){
		userService.delete(id);
		return "一杀";
	}
	
	/**
	 * 改
	 * 传个用户对象并更新
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/update")
	public String update (User user){
		userService.update(user);
		return "教它做人！";
	}
	
	/**
	 * 实现分页查询，可以指定查询页数，每页多少数据
	 * @return
	 */
	@RequestMapping("/UserListPaging")
	@ResponseBody
	public PageParameter<User> UserListPaging( PageParameter<User> parameter,HttpServletRequest request,HttpServletResponse response){
		Cookie [] cookies = request.getCookies();
		Cookie pageNoCookie = getCookieByNmae(cookies,"PageNo");
		Cookie offsetCookie = getCookieByNmae(cookies,"offset");
		//如果用户有传分页信息：每页大小和当前页面，则用此数据进行查询，否则取最后一次设置的值或者设置默认值
		if(isEmpty(parameter)){
			parameter = new PageParameter<User>();
			//如果用户最后一次离开有设置页面大小和当前页面，则取出，否则设置默认值
			if(pageNoCookie!=null && offsetCookie!=null){
				parameter.setCurrentPage(Integer.parseInt(pageNoCookie.getValue()));
				parameter.setOffset(Integer.parseInt(offsetCookie.getValue()));
			}else{
				parameter.setCurrentPage(1);
				parameter.setOffset(5);
			}
		}else{
			//如果数据是用户传过来的话需要判断有效性
			check(parameter);
		}
		parameter = userService.getUserListByPaging(parameter);
		//取到数据后设置cookie
		response.addCookie(new Cookie("PageNo", parameter.getCurrentPage()+""));
		response.addCookie(new Cookie("offset", parameter.getOffset()+""));
		return parameter;
	}
	
	/**
	 * 检测请求的数据是否合理
	 * 包括当前页数是否大于总页数
	 * 以及当前页数和页面数据个数是否为0或者负数
	 * @param parameter
	 */
	private void check(PageParameter<User> parameter) {
		int pageSize = userService.getPageSize(parameter);
		if(parameter.getCurrentPage()> pageSize){
			throw new RuntimeException("没有那么多页，请回吧");
		}
		parameter.setPageSize(pageSize);
		if(parameter.getCurrentPage()<=0 || parameter.getCurrentPage() <=0){
			throw new RuntimeException("别想搞事情，想看第零业或者看零条数据，没门！");
		}
	}



	/**
	 * 根据cookie的名字从cookie集合中取出来
	 * @param cookies
	 * @return
	 */
	private Cookie getCookieByNmae(Cookie[] cookies,String cookieName){
		if(cookies ==null){
			return null;
		}
		for(Cookie cookie:cookies){
			if(cookieName.equals(cookie.getName())){
				return cookie;
			}
		}
		return null;
	}
	
	/**
	 * 判断parameter参数是否为null.为适应项目使用，简易判断。
	 * @param parameter
	 * @return
	 */
	private boolean isEmpty(PageParameter<User> parameter){
		if(parameter.getCurrentPage() ==0 && parameter.getOffset() ==0){
			return true;
		}
		return false;
		
	}
	
}
