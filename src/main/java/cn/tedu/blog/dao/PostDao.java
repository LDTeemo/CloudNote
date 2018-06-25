package cn.tedu.blog.dao;

import cn.tedu.blog.entity.Post;

public interface PostDao {
	Post findPostById(String id);
}
