package cn.tedu.note.aop;

import org.springframework.stereotype.Component;

@Component
//@Aspect//该注解必须配合spring-aop.xml中的<aop:aspectj-autoproxy>自动代理才能生效
public class DemoAspect {
	/*
	 * 切入点：cn.tedu.note.web包中的AccountController类的所有方法
	 * @before用于通知调用上述包的方法之前要先执行hello()方法
	 * 对应的有一个after注解表示在。。。之后
	 */
	//@Before("within(cn.tedu.note.web.AccountController)")
	//@Before("bean(*Dao)")
	//@Before("execution(* code(HttpServletRequest req))")
	public void hello(){
		System.out.println("hello my dear");
	}
}
