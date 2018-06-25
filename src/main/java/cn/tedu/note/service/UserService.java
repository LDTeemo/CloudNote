package cn.tedu.note.service;

import java.io.Serializable;

import cn.tedu.note.entity.User;

public interface UserService extends Serializable{
	/**
	 * 登录方法，
	 * @param name
	 * @param pwd
	 * @return 登录成功后返回用户信息，失败抛异常
	 * @throws ServiceException 用户名或密码错误
	 */
	User login(String name,String pwd)throws ServiceException;
	/**
	 * 注册功能
	 * @param name
	 * @param password
	 * @param nick
	 * @throws UserExistException
	 */
	User regist(String name,String password,String nick) throws UserExistException;
}
