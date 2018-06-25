package cn.tedu.note.dao;

import java.util.List;

import cn.tedu.note.entity.User;

public interface UserDao {
	void saveUser(User user);
	User findById(String id);
	User findByName(String name);
	List<User> findAll();
	void modify(User user);
	void deleteOne(String name);
	
}
