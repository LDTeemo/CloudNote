package cn.tedu.note.web;

import cn.tedu.note.entity.User;
import cn.tedu.note.service.UserService;
import cn.tedu.note.util.ImageUtil;
import cn.tedu.note.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private UserService userService;

	@RequestMapping("/login.do")
	@ResponseBody
	public JsonResult<User> login(String name,String pwd,HttpServletRequest req,HttpServletResponse res){
		User user=userService.login(name,pwd);
		//登录时保存cookie，cookie一定在response中，所以加一个参数
		//利用user-Agent创建token这个cookie
		String userAgent=req.getHeader("User-Agent");
		long now=System.currentTimeMillis();
		//利用userAgent和now加盐使用MD5摘要出该token
		String md5=MD5.saltMd5(userAgent+now);
		Cookie cookie=new Cookie("token",now+"|"+md5);
		cookie.setPath("/");//把cookie中放到根目录下（方便网址中其他路径情况下取得该cookie，才能上传）
		res.addCookie(cookie);
		return new JsonResult<User>(user);
	}
	@RequestMapping("/regist.do")
	@ResponseBody
	public JsonResult<User> regist(String name,String password,String nick ){
		User user=userService.regist(name, password, nick);
		System.out.println(user);
		return new JsonResult<User>(user);
	}

	/**
	 * 验证码生成
	 * @throws IOException
	 */
	@RequestMapping(value="/code.do",produces="image/png")
	@ResponseBody
	public byte[]code(HttpServletRequest req) throws IOException{
		byte[] buf;
		Object []obj=ImageUtil.createImage();
		String code=(String)obj[0];
		//每生成一次一个验证码就存到session里
		req.getSession().setAttribute("code", code);
		BufferedImage img=(BufferedImage)obj[1];
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		ImageIO.write(img, "png", out);
		out.close();
		buf=out.toByteArray();
		return buf;
	}
	/*
	 * 检查输入的验证码与生成验证码是否一致
	 */
	@RequestMapping("/checkCode.do")
	@ResponseBody
	public JsonResult<Boolean> checkCode(String inputCode,HttpServletRequest req){
		return null;
	}
}
