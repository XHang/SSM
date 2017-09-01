package com.ssm.Mapper;

import java.util.List;

import com.ssm.model.User;
import com.ssm.vo.PageParameter;

public interface UserMapper {
	/**
	 * 根据主键查询用户
	 * @param userid
	 * @return
	 */
	public User selectByid (int userid);
	
	/**
	 * 保存用户
	 * @param user
	 */
	public void save(User user);
	
	/**
	 * 根据用户ID删除用户
	 * @param userId
	 */
	public void delete(int  userId);

	/**
	 * 根据用户对象更新整个对象
	 * @param user
	 */
	public void update(User user);
	
	/**
	 *  通过分页获取用户列表
	 * @param parameter
	 * @return
	 */
	public List<User> getUserByPaging(PageParameter<User> parameter);
	/**
	 *  获取所有用户总数
	 * @return
	 */
	public int getUserCount();
}
