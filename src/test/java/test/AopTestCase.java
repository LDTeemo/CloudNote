package test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.tedu.note.aop.DemoBean;

public class AopTestCase {
	ApplicationContext ctx;
	@Before
	public void init(){
		ctx=new ClassPathXmlApplicationContext("spring-aop.xml");
	}
	@Test
	public void test(){
		DemoBean demo=ctx.getBean("demoBean",DemoBean.class);
		demo.test(null);
	}
	@Test
	public void testAround(){
		DemoBean demo=ctx.getBean("demoBean",DemoBean.class);
		demo.test(null);
	}
}
