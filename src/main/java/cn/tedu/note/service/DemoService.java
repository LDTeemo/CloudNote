package cn.tedu.note.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.entity.User;

public interface DemoService {

	/**
	 * Spring提供了事务管理接口，利用事务管理器，就可以自动地管理事务。事务管理器是一个接口
	 * 数据源事务管理器可以用于spring-jdbc和mybatis的事务管理
	 * 1.创建 数据源事务管理bean，在spring的配置文件中使用<bean>标签，数据源事务管理器必须注入数据源对象
	 * 2.在Spring配置文件中声明使用注解管理事务
	 * 3.在需要开启事务管理的方法上使用事务注解
	 *
	 * 如果在当前的线程中开启了新的线程，新的线程中的数据操作的事务并不输入当前事务操作，除非新线程中
	 * 执行的方法由事务管理即有注解@transactional;
	 *
	 * 事务的传播
	 * 业务重构时，会有业务方法调用另外一个业务方法的情况，这时就需要将多个业务方法合并成一个事务进行处理
	 * 例如：批量注册账号，可以每次循环时调用单次注册的业务方法，单次注册的方法是有事务处理的（有@Transactional）
	 *
	 * 批量注册
	 * @param name
	 * @return随机密码
	 */
	User regist(String name, String password, String nick);

	List<String> batchRegist0(String... userName);

	List<String> batchRegist(String... userName);

}