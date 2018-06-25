package cn.tedu.note.web;

import javax.servlet.*;
import java.io.IOException;

public class ContentTypeFilter implements Filter {
	private String contentType;
	public void destroy() {


	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		//打印输出下看是否筛选器已经工作
		res.setContentType(contentType);
		chain.doFilter(req, res);
	}

	public void init(FilterConfig cfg) throws ServletException {
		contentType=cfg.getInitParameter("ContentType");

	}

}
