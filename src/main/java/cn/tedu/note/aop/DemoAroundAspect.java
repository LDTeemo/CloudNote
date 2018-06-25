package cn.tedu.note.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class DemoAroundAspect {

	@Around("bean(demoBean)")
	public Object execute(ProceedingJoinPoint joinPoint){
		try {
			System.out.println("开始");
			Object obj=joinPoint.proceed();//调用被代理的目标方法
			System.out.println("结束");
			return obj;
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("有异常");
			throw new RuntimeException();
		}
	}
}
