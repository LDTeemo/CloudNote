package cn.tedu.note.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import cn.tedu.note.web.JsonResult;

/**
 * 利用Spring AOP统一处理控制器异常
 * @author tarena
 *
 */
@Component
@Aspect

public class ControllerExceptionAspect {

	@Around("bean(accountController)||bean(noteBookController)||bean(getNoteController)")
	public Object process(ProceedingJoinPoint joinPoint){
		try {
			System.out.println("开始调用控制器");
			Object val=joinPoint.proceed();
			System.out.println("正常结束");
			return val;
		} catch (Throwable e) {
			e.printStackTrace();
			System.out.println("异常："+e);
			return new JsonResult(1,e.getMessage());
		}
	}
}