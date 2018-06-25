package cn.tedu.note.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.User;
import cn.tedu.note.util.MD5;

@Service("demoService")
public class DemoServiceImpl implements DemoService {
	@Resource
	private UserDao userDao;

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
	 * 事务的处理会被合并成一个
	 * 事务传播规则
	 * 1.propagation=propagation.REQUIRED(必须):如果当前方法在一个有事务的方法中被调用，则参与到当前事务，作为一个
	 * 事务；如果当前方法在没有事务的方法中被调用，则开启一个事务；这是最为常用的规则
	 * 2.propagation=propagation.SUPPORT(支持):如果当前方法在一个有事务的方法中被调用，则参与到当前事务，作为一个
	 * 事务；如果当前方法在没有事务的方法中被调用，【不会】开启事务
	 * 3.propagation=propagation.MANDATORY:如果当前方法在一个有事务的方法中被调用，则参与到当前事务，作为一个
	 * 事务；如果当前方法在没有事务的方法中被调用，则抛出【异常】，表示该方法不能单独使用，只能在其他事务方法中调用
	 * 是一个完整事务过程的参与者。
	 * 4.propagation=propagation.REQUEST_NEW：如果当前方法在一个有事务的方法中被调用，则【挂起】当前事务，
	 * 且为方法单读开启一个新事物；如果当前方法在没有事务的方法中被调用，也【单独开启】一个事务
	 * 5.propagation=propagation.NOT_SUPPORT：如果当前方法在一个有事务的方法中被调用，挂起当前事务，
	 * 当作方法处理期间没有事务
	 * 6.NEVER：如果当前方法在一个有事务的方法中被调用，则抛出异常
	 * 7.NESTED(嵌入)：规则类似于REQUIRED，但内部有多个回滚点
	 * @Transactional(readOnly=true)该属性只用于查询事务用于提高效率
	 *
	 * 事务的四大特性：
	 * 原子性A：不可再分割的一个最小单元。在软件数据中不可再分的最小操作范围称为原子操作范围；
	 * 		      如:汇款 操作范围--源账户取钱，目标账户存钱；这两步操作不可再分割，不能只执行一半。
	 * 一致性C：数据操作前后其数据值是一致的；体现了借贷平衡。交易发生之前和之后无论交易是否正常完成
	 * 		      数据总额要一致不变；
	 * 隔离性I：体现在并发操作中：
	 * @Transactional(isolation=Isolation.READ_COMMITTED)
	 * 1.READ_COMMITTED可以读取调教之后的数据，在事务操作期间（没有提交）的数据不能被另外一个线程读取
	 * 2.DEFAULT同READ_COMMITTED；
	 * 3.SERIALIZABLE：性能最差，一般不用，完全隔离。一个线程操作事务期间，另外一个线程只能等待，上一次
	 * 提交的数据也不能看；
	 * 4.REPEATABLE_READ：可以重复读，保证每次读取的数据和上一次读取的数据是一致的；
	 * 5.READ_UNCOMMITTED：完全不隔离，可以读取到正常处理的事务数据，性能最好，但不用！
	 * 持久性D：事务结束后数据永久保存；
	 *
	 * 常见数据库都支持上述特性：Oracle，IBM DB2，SQLServer
	 * MySQL：MyISAM引擎不支持ACID，不支持事务，INNODB引擎支持ACID，MySQL5以后默认的是INNODB引擎；
	 * 批量注册
	 * @param name
	 * @return随机密码
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public User regist(String name,String password,String nick){
		if(name==null||name.trim().isEmpty()){
			throw new ServiceException("名字不能为空");
		}
		if(password==null||password.trim().isEmpty()){
			throw new ServiceException("名字不能为空");
		}
		if(nick==null||nick.trim().isEmpty()){
			nick=name;
		}
		User user=userDao.findByName(name);
		if(user!=null){
			throw new ServiceException(user+"用户名已被占用！");
		}
		String id=UUID.randomUUID().toString();
		String pwd=MD5.saltMd5(password);
		user=new User(id, name, pwd, "", nick);
		user=userDao.findById(id);
		if(user==null){
			throw new ServiceException("注册失败");
		}
		return user;
	}
	@Transactional
	public List<String> batchRegist0(String ... userName ){
		List<String> list=new ArrayList<String>();
		for (String name : userName) {
			String pwd=randomPassWord(3);
			regist(name, pwd, name);
			list.add(pwd);
		}
		return list;
	}
	private String randomPassWord(int n) {
		char[] pwd=new char[n];
		String str="123456789abcdefghijklmnopq";
		for (int i = 0; i < pwd.length; i++) {
			int index=(int)(Math.random()*str.length());
			pwd[i]=str.charAt(index);
		}
		return new String(pwd);
	}

	@Transactional
	public List<String> batchRegist(String ... userName){
		List<String> list=new ArrayList<String>();
		for (String name : userName) {
			User user=userDao.findByName(name);
			if(user!=null){
				throw new ServiceException("该用户名已经被占用");
			}
			String id=UUID.randomUUID().toString();
			String password=id.substring(id.length()-6, id.length());
			String pwdMD5=MD5.saltMd5(password);
			user=new User(id, name, password, "", name);
			userDao.saveUser(user);
			list.add(password);
		}
		return list;

	}
}
