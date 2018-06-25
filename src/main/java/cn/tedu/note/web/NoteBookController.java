package cn.tedu.note.web;

import cn.tedu.note.service.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/notebook")
public class NoteBookController implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private NoteBookService noteBookService;

	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult<List<Map<String,Object>>> listNoteBook(String userId){
		try {
			List<Map<String,Object>> list=noteBookService.getNoteBooks(userId);
			return new JsonResult<List<Map<String,Object>>>(list);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult<List<Map<String,Object>>>(e.getMessage());
		}

	}
}
