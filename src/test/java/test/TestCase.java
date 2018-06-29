package test;

import cn.tedu.blog.dao.PostDao;
import cn.tedu.blog.entity.Post;
import cn.tedu.note.dao.NoteBookDao;
import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.dao.UserDao;
import cn.tedu.note.entity.Note;
import cn.tedu.note.entity.User;
import cn.tedu.note.service.DemoService;
import cn.tedu.note.service.NoteBookService;
import cn.tedu.note.service.NoteService;
import cn.tedu.note.service.UserService;
import cn.tedu.note.util.MD5;
import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class TestCase {
	private ApplicationContext ac;
	private UserDao dao;
	private UserService userService;
	private NoteBookDao notebookDao;
	@Before
	public void init(){
		ac=new ClassPathXmlApplicationContext("spring-mybatis.xml",
				"spring-service.xml");
		dao=ac.getBean("userDao",UserDao.class);
		userService=ac.getBean("userService",UserService.class);
		notebookDao=ac.getBean("noteBookDao",NoteBookDao.class);
	}
	/**
	 * 测试mybatis配置是否配备完毕
	 * 测试MapperScanner
	 */
	@Test
	public void test1(){
		Object obj=ac.getBean("mapperScanner");
		System.out.println(obj);
	}
	/**
	 * 测试UserDAO的save方法
	 */
	@Test
	public void test2(){

		User u=new User();
		String id=UUID.randomUUID().toString();
		System.out.println(id);
		u.setId(id);
		u.setName("caocao");
		u.setPassword("123");
		u.setNick("cao");
		u.setToken("");
		dao.saveUser(u);
	}
	/**
	 * 测试FindByName()
	 */
	@Test
	public void test3(){
		User u=dao.findById("284a3287-500d-4f23-ac1f-a402a9690c88");
		System.out.println(u);
	}
	/**
	 * 测试查询所有
	 */
	@Test
	public void testFindAll(){
		List<User> list=dao.findAll();
		for (User user : list) {
			System.out.println(user);
		}
	}

	/**
	 * 测试删除
	 */
	@Test
	public void testDeleteOne(){
		dao.deleteOne("caocao");
		List<User> list=dao.findAll();
		for (User user : list) {
			System.out.println(user);
		}
	}

	/**
	 * 测试修改
	 */
	@Test
	public void testModify(){
		User u=dao.findByName("lix");
		u.setPassword("456");
		u.setNick("Teemo");
		dao.modify(u);
		u=dao.findByName("lix");
		System.out.println(u);
	}

	/**
	 * 测试业务层是否
	 */
	@Test
	public void testUserService(){
		String name="lix";
		String pwd="456";
		UserService us=ac.getBean("userService",UserService.class);
		User user=us.login(name, pwd);
		System.out.println(user);
	}
	/**
	 * 所有的密码都不能明文保存，利用摘要算法对密码取得其摘要
	 * MessageDigest封装了复杂的消息摘要算法
	 * 参数“MD5”摘要算法的版本号
	 */
	@Test
	public void testMd(){
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			//md.update(数据);提交数据可以多次调用，多次调用就是对一堆数据进行摘要
			//大数据处理：md.update(0-99个字节),md.update(100-199个字节),byte[] 摘要=md.digest();
			//密码处理：摘要=md.digest(数据)
			String pwd="123456";
			byte[] data=pwd.getBytes("utf-8");
			byte[] md5=md.digest(data);

			/*
			 * 直接显示byte麻烦，不易阅读，使用commons-codecAPI的Hex.encodeHexString()
			 * 将byte组数的每一个元素转换为16进制
			 */
			String hex=Hex.encodeHexString(md5);
			System.out.println(hex);
			/*
			 * Base64
			 * 将二进制的数组存的数据转换为文本格式
			 */
			String base64=Base64.encodeBase64String(md5);
			System.out.println(base64);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void test4(){

		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			String pwd="abc123efg";
			byte[]data=pwd.getBytes();
			byte[] md5=md.digest(data);

		} catch (Exception e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Test
	public void test5(){
		String data=MD5.md5("123456");
		System.out.println(data);
	}
	@Test
	public void test6(){
		User u=dao.findByName("lix");
		String pwd=u.getPassword();
		System.out.println(pwd);
		pwd="123";
		pwd=MD5.saltMd5(pwd);
		u.setPassword(pwd);
		dao.modify(u);
	}
	@Test
	public void test7(){
		List<User> list=dao.findAll();
		for (User u : list) {
			String pwd=u.getPassword();
			pwd=MD5.saltMd5(pwd);
			u.setPassword(pwd);
			//System.out.println(pwd);
			dao.modify(u);
		}
	}
	@Test
	public void test8(){
		User u=dao.findByName("lix");
		System.out.println(u);
	}
	@Test
	public void testRegist(){
		String name="caocao2";
		String password="caocao123";
		String nick="曹操曹操";
		User user=userService.regist(name, password, nick);
		System.out.println(user);
	}
	/**
	 * 测试查询笔记本--持久层
	 */
	@Test
	public void testFindNoteBookByUserId(){

		User u=dao.findById("39295a3d-cc9b-42b4-b206-a2e7fab7e77c");
		System.out.println(u.getName());
	}
	/**
	 * 测试查询笔记本--业务层
	 */
	@Test
	public void testFindNoteBookByUserIdService(){
		NoteBookService getListService=ac.getBean("noteBookService",NoteBookService.class);
		List<User> list=dao.findAll();
		String userId=list.get(0).getId();
		System.out.println(userId);
		List<Map<String,Object>> listNoteBook=getListService.getNoteBooks(userId);
		for (Map<String, Object> map : listNoteBook) {
			System.out.println(map);
		}
	}
	@Test
	public void testModifyPwd(){
		User u=dao.findByName("zhoujia");
		String pwd="123";
		pwd=MD5.saltMd5(pwd);

		u.setPassword(pwd);
		u.setNick("Teemo");
		dao.modify(u);
		u=dao.findByName("zhoujia");
		System.out.println(u);
	}
	/**
	 * 查询各笔记的业务
	 * 测试业务层和持久层
	 */
	@Test
	public void testFindNote(){
		String nid="516f6f4f-eaa3-4c76-84ff-530b92c7f64d";
		NoteService noteService=ac.getBean("noteService",NoteService.class);
		List<Map<String,Object>> list=noteService.listNotes(nid);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}

	}
	@Test
	public void testgetNote(){
		NoteDao dao=ac.getBean("noteDao",NoteDao.class);
		List<Map<String,Object>> list=dao.getNotes("516f6f4f-eaa3-4c76-84ff-530b92c7f64d");
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	/*
	 * 查询笔记内容
	 * 持久层
	 */
	@Test
	public void testQueryContent(){
		NoteDao dao=ac.getBean("noteDao",NoteDao.class);
		Note note=dao.queryContent("b5fc7da0-b5e8-4792-8f1f-9fd4b347a64f");
		System.out.println(note);

	}
	/*
	 * 查询笔记内容
	 * 业务层
	 */
	@Test
	public void testQueryContent2(){
		NoteService ns=ac.getBean("noteService",NoteService.class);
		Note note=ns.getNoteContent("b5fc7da0-b5e8-4792-8f1f-9fd4b347a64f");
		System.out.println(note);

	}
	/*
	 * 测试修改笔记
	 * 持久层
	 */
	@Test
	public void testModifyNote(){
		NoteDao dao=ac.getBean("noteDao",NoteDao.class);
		Note note=dao.queryContent("b5fc7da0-b5e8-4792-8f1f-9fd4b347a64f");
		System.out.println("旧笔记"+note);
		note.setNoteTitle("这是新笔记");
		note.setNoteBody("心比心机新笔记令狐冲令狐冲");
		Date date=new Date();
		Long lastModifyTime=date.getTime();
		note.setLastModifyTime(lastModifyTime);
		//正式调用dao.的更新笔记方法
		dao.modifyNote(note);
		//显示该笔记
		note=dao.queryContent("b5fc7da0-b5e8-4792-8f1f-9fd4b347a64f");
		System.out.println("新笔记"+note);
	}
	/*
	 * 测试修改笔记
	 * 业务层层
	 */
	@Test
	public void testModifyNote2(){
		NoteService ns=ac.getBean("noteService",NoteService.class);
		ns.modifyNoteGo("b5fc7da0-b5e8-4792-8f1f-9fd4b347a64f", "业务层修改", "业务层修改的令狐冲");
		Note note=ns.getNoteContent("b5fc7da0-b5e8-4792-8f1f-9fd4b347a64f");
		System.out.println(note);
	}
	/*
	 * 增加一个笔记
	 */
	@Test
	public void testAddNote(){
		NoteDao dao=ac.getBean("noteDao",NoteDao.class);
		Note note=new Note();
		note=dao.queryContent("d3ded3ca-4e79-48fd-a501-ea4df79936c1");
		System.out.println("旧笔记"+note);

		String id=UUID.randomUUID().toString();
		//System.out.println(id);
		note.setNoteId(id);
		note.setNoteBookId("0b11444a-a6d6-45ff-8d46-282afaa6a655");
		note.setNoteTitle("大费");
		note.setNoteBody("OPPO哦哦破皮IE就爱看");
		note.setNoteCreatTime(System.currentTimeMillis());
		note.setLastModifyTime(note.getNoteCreatTime());
		//note.set
		dao.addNote(note);
		Note note1=new Note();
		note1=dao.queryContent(id);
		System.out.println("xin:"+note1);


	}
	@Test
	public void testModifyNoteAllItems(){
		NoteDao dao=ac.getBean("noteDao",NoteDao.class);
		Note note=dao.queryContent("b5fc7da0-b5e8-4792-8f1f-9fd4b347a64f");
		note.setNoteCreatTime(new Date().getTime());
		dao.updateNote(note);

	}
	@Test
	public void testDeleteNote(){
		NoteDao dao=ac.getBean("noteDao",NoteDao.class);
		System.out.println(dao);
		dao.removeNote("b5fc7da0-b5e8-4792-8f1f-9fd4b347a64f");
		Note note=dao.queryContent("b5fc7da0-b5e8-4792-8f1f-9fd4b347a64f");
		System.out.println(note);
	}
	@Test
	public void testBatchRegist(){
		DemoService ds=ac.getBean("demoService",DemoService.class);
		List<String> list=ds.batchRegist0("Feige","Lee","Teemo","Mac","Tom","zhoujia");
		System.out.println(list);
	}
	@Test
	public void testUpdateNoteDelType(){
		NoteDao noteDao=ac.getBean("noteDao",NoteDao.class);
//		noteDao.updateNoteDelType("003ec2a1-f975-4322-8e4d-dfd206d6ac0c",
//				"019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0",
//				"01c74a5b-24b8-4716-9c99-e23324d9df09",
//				"01da5d69-89d5-4140-9585-b559a97f9cb0",
//				"046b0110-67f9-48c3-bef3-b0b23bda9d4e",
//				"051538a6-0f8e-472c-8765-251a795bc88f");
		List<String> list=new ArrayList<String>();
		list.add("019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0");
		list.add("01c74a5b-24b8-4716-9c99-e23324d9df09");
		list.add("046b0110-67f9-48c3-bef3-b0b23bda9d4e");
		list.add("0a652205-c8af-41e0-986a-80d0cdecc996");
		list.add("0a233251-8991-493c-87c8-d36e9433eae3");
		list.add("09f60aeb-a573-4fcf-b39f-903e1536e762");
		int num=noteDao.countNormalNote(list);
		System.out.println(num);
	}
	@Test
	public void testUpdateNotes(){
		NoteDao noteDao=ac.getBean("noteDao",NoteDao.class);
		String[]ids={"003ec2a1-f975-4322-8e4d-dfd206d6ac0c",
				"019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0",
				"01c74a5b-24b8-4716-9c99-e23324d9df09",
				"01da5d69-89d5-4140-9585-b559a97f9cb0",
				"046b0110-67f9-48c3-bef3-b0b23bda9d4e"};

		Map<String,Object> map=new HashMap<String, Object>();
		map.put("noteTypeId", "1");
		map.put("lastModifyTime", System.currentTimeMillis());
		map.put("ids", ids);
		noteDao.updateNotes(map);
	}
	@Test
	public void deleteNotes(){
		NoteDao noteDao=ac.getBean("noteDao",NoteDao.class);
		noteDao.deleteNotes("003ec2a1-f975-4322-8e4d-dfd206d6ac0c","019cd9e1-b629-4d8d-afd7-2aa9e2d6afe0");
	}
	/*
	 * 模糊查询
	 *
	 */
	@Test
	public void testFindByKes(){
		NoteDao noteDao=ac.getBean("noteDao",NoteDao.class);
		Map<String, Object> keys=new HashMap<String, Object>();
		//keys.put("noteTitle", "%新增%");
		//keys.put("noteTitle", "%新增%");
		keys.put("start", 10);
		keys.put("length", 10);
		List<Map<String,Object>> list=noteDao.findAllByKeys(keys);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	@Test
	public void testSearchNotes(){
		NoteService noteService=ac.getBean("noteService",NoteService.class);
		List<Map<String,Object>> list=noteService.searchNotes("", 0, 20);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	/*
	 * 用于测试关联查询（查询两个表）
	 */
	@Test
	public void testFindPostById(){
		PostDao dao=ac.getBean("postDao",PostDao.class);
		Post post=dao.findPostById("2");
		System.out.println(post);
	}
}
