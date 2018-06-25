package cn.tedu.note.aop;

import org.springframework.stereotype.Component;

@Component
public class DemoBean {

	public void test(String str){
		System.out.println(str.length());
	}
	public void demo(String str){
		System.out.println("demo:"+str.length());
	}
}
