package com.ssm.Service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssm.Mapper.UserMapper;
import com.ssm.model.User;
import com.ssm.vo.PageParameter;

@Service
public class UserService {
	@Autowired
   private UserMapper  userMapper;
	
	/**
	 * 演示自动回滚
	 * @param user
	 */
	@Transactional(rollbackFor=RuntimeException.class)
	public void add(User user){
		userMapper.save(user);
		System.out.println("保存一个用户");
		throw new RuntimeException("请不要恶意添加用户，请狗带好吗");
	}
	
	
	
	/**
	 * 演示事务中只读属性
	 * @param userid
	 * @return
	 */
	public User getUser(int  userid){
		System.out.println("我只找一下用户");
		return userMapper.selectByid(userid);
	}
	
	/**演示自动回滚**/
	public void removeUser(int  userId){
		userMapper.delete(userId);
		System.out.println("已使用户ID："+userId+"狗带");
		throw new RuntimeException("请自动回滚，Mybatis，谢谢");
	}
	
	public void saveUser(User user){
		userMapper.save(user);
	}
	public void delete(int userId){
		userMapper.delete(userId);
	}
	
	public void update(User user){
		userMapper.update(user);
	}


	/**
	 * 用户列表分页
	 * @param parameter
	 * @return
	 */
	public PageParameter<User> getUserListByPaging(PageParameter<User> parameter) {
		//设置从表中的第几个数据开始取,如果是第一页，说明需要从第一个数据开始取，如果是第二页，说明要在1*每页个数开始取。故此减1
		parameter.setFrom((parameter.getCurrentPage()-1)*parameter.getOffset());
		//得到数据库的记录总数，除以每页个数，既是总页数
		BigDecimal pageSize = new BigDecimal(userMapper.getUserCount()).divide(new BigDecimal(parameter.getOffset()));
		if(pageSize.precision() ==1){
			parameter.setPageSize(pageSize.intValue());
		}else{
			parameter.setPageSize(pageSize.intValue()+1);
		}
		//取出分页后的数据
		List<User> list = userMapper.getUserByPaging(parameter);
		parameter.setList(list);
		return parameter;
	}
	
	/**
	 * 根据所给参数计算总页数
	 * @param parameter
	 * @return
	 */
	public int getPageSize(PageParameter<User> parameter){
		//得到数据库的记录总数，除以每页个数，既是总页数
		BigDecimal count  = new BigDecimal(userMapper.getUserCount());
		BigDecimal offset = new BigDecimal(parameter.getOffset());
		BigDecimal pageSize = count.divide(offset);
		//如果商的精度是1，表示没有小数，那么总页数刚好就是商，否则总页数需要+1
		if(pageSize.precision() ==1){
			return pageSize.intValue();
		}else{
			return pageSize.intValue()+1;
		}
	}
	
}
