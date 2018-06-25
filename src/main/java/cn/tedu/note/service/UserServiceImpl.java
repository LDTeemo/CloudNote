package cn.tedu.note.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.User;
import cn.tedu.note.util.MD5;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final long serialVersionUID = 1L;
	@Autowired
	private UserDao dao;

	public User login(String name, String pwd) throws ServiceException {
		//入口参数检查，为了程序的严谨性
		if(name==null||name.trim().isEmpty()){
			throw new ServiceException("用户名不能为空");
		}
		if(pwd==null||pwd.trim().isEmpty()){
			throw new ServiceException("密码不能为空");
		}
		User user=dao.findByName(name);
		if(user==null){
			throw new ServiceException("用户名或密码错误");
		}
		if(user.getPassword().equals(MD5.saltMd5(pwd))){
			return user;
		}

		throw new ServiceException("用户名或密码错误");

	}

	public User regist(String name, String password, String nick) throws UserExistException {
		//入口参数检查
		String rule="^\\w{3,10}$";
		if(name==null||name.trim().isEmpty()){
			throw new ServiceException("账户名不能为空");
		}
		if(!name.matches(rule)){
			throw new ServiceException("账户名格式错误，3-10位数字字母下划线");
		}
		if(password==null||password.trim().isEmpty()){
			throw new ServiceException("密码不能为空");
		}
		if(!password.matches(rule)){
			throw new ServiceException("密码格式错误，3-10位数字字母下划线");
		}
		if(nick==null||nick.trim().isEmpty()){
			throw new ServiceException("昵称名不能为空");
		}
		rule="^.{3,10}$";
		if(!nick.matches(rule)){
			throw new ServiceException("昵称不合格");
		}
		User user=dao.findByName(name);
		if(user!=null){
			throw new ServiceException("账户名已存在");
		}
		String id=UUID.randomUUID().toString();
		password=MD5.saltMd5(password);
		user=new User(id, name, password, "", nick);
		dao.saveUser(user);
		return user;
	}

}
