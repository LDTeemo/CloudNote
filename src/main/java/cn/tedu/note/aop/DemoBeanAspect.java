package cn.tedu.note.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
//@Aspect
public class DemoBeanAspect {
	//DemoBean的方法执行之前
	@Before("bean(demoBean)")
	public void start(){
		System.out.println("开始");
	}

	@AfterReturning("bean(demoBean)")
	public void end(){
		System.out.println("结束");
	}

	@AfterThrowing(throwing="e",pointcut="bean(demoBean)")
	public void error(Throwable e){
		System.out.println("异常"+e.getMessage());
	}

	@After("bean(demoBean)")
	public void over(){
		System.out.println("最终");
	}
}
