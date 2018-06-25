package cn.tedu.note.web;

import cn.tedu.note.util.MD5;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessFilter implements Filter {

	public void destroy() {


	}
	/**
	 * 用于防止cookie欺骗，和登录控制
	 */
	public void doFilter(ServletRequest arg1, ServletResponse arg2, FilterChain chain)
			throws IOException, ServletException {
		/*
		 * ServletRequest要做类型转换
		 */
		HttpServletRequest req=(HttpServletRequest)arg1;
		HttpServletResponse res=(HttpServletResponse)arg2;
		//String path=req.getServletPath();
		/*
		 * 除了log_in.html其他的HTML都拦截
		 * 以及*.do
		 */
		StringBuffer url=req.getRequestURL();
		//System.out.println("url:"+url);
		String path=url.toString();
		if(path.endsWith(".html")||path.endsWith(".do")){
			if(path.endsWith("log_in.html")){
				chain.doFilter(req, res);
				return;
			}
			//与account有关的请求都是登录或者注册，不用拦截
			if(path.indexOf("/account/")>0){
				chain.doFilter(req, res);
				return;
			}
			//如果没有登录的话就转到重定向登录页面
			processAccessControl(req,res,chain);
			return;
		}
		//其他的请求就直接放过（即请求继续传递不做处理）
		chain.doFilter(req, res);
	}
	/*
	 * 访问控制逻辑
	 */
	private void processAccessControl(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
		//检查cookie中是否存有token,没有就到登录页面
		Cookie []cookie=req.getCookies();
		Cookie token=null;
		//获取传递过来的token这个cookie
		for (Cookie c : cookie) {
			if("token".equals(c.getName())){
				token=c;
				break;
			}
		}
		if(token==null){
			//没有token跳转
			String path=req.getContextPath();
			String login=path+"/log_in.html";
			res.sendRedirect(login);
			return;
		}
		//处理token是否合格
		//System.out.println("token:"+token);
		String value=token.getValue();
		String[]data=value.split("\\|");
		String time=data[0];
		String md5=data[1];
		String userAgent=req.getHeader("User-Agent");
		if(md5.equals(MD5.saltMd5(userAgent+time))){
			chain.doFilter(req, res);
			return;
		}
		String path=req.getContextPath();
		String login=path+"/log_in.html";
		res.sendRedirect(login);
	}

	public void init(FilterConfig arg0) throws ServletException {


	}

}
