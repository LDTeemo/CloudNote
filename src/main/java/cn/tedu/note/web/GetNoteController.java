package cn.tedu.note.web;

import cn.tedu.note.entity.Note;
import cn.tedu.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/note")

public class GetNoteController {
	@Autowired
	private NoteService noteService;

	@RequestMapping("/listNote.do")
	@ResponseBody
	public JsonResult<List<Map<String,Object>>> getNotesByNid(String nid){
		List<Map<String,Object>> list=noteService.listNotes(nid);
		return new JsonResult<List<Map<String,Object>>>(list);
	}
	@RequestMapping("listNoteContent.do")
	@ResponseBody
	public JsonResult<Note> toGetNoteContent(String noteId){
//		try {
		Note note=noteService.getNoteContent(noteId);

		//System.out.println("找到的笔记"+note);
		return new JsonResult<Note>(note);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new JsonResult<Note>(e.getMessage());
//		}
	}
	/*
	 * 编辑笔记
	 */
	@RequestMapping("modifyNote.do")
	@ResponseBody
	public JsonResult<Object> toModifyNote(String noteId, String noteTitle, String noteBody){
//		try {
		noteService.modifyNoteGo(noteId, noteTitle, noteBody);
		return new JsonResult<Object>(0,"修改成功");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new JsonResult<Object>(e.getMessage());
//		}
	}

	/*
	 * 增加笔记
	 *
	 */
	@RequestMapping("addNote.do")
	@ResponseBody
	public JsonResult<Note> toAddNote(String noteTitle, String userId, String noteBookId){
//		try {
		Note note=noteService.doAddNote(noteTitle, userId, noteBookId);
		return new JsonResult<Note>(note);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new JsonResult<Note>(e.getMessage());
//		}

	}
	/*
	 * 删除笔记
	 */
	@RequestMapping("deleteNote.do")
	@ResponseBody
	public JsonResult<Note> toDeleteNote(String noteId){
//		try {
		//System.out.println("删除笔记上传的笔记Id"+noteId);
		Note note=noteService.doDeleteNote(noteId);
		return new JsonResult<Note>(note);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new JsonResult<Note>(e.getMessage());
//		}

	}

	/*
	 * 移动笔记
	 */
	@RequestMapping("removeNote.do")
	@ResponseBody
	public JsonResult<Note> toRemoveNote(String noteId,String noteBookId ){
//		try {
		System.out.println("笔记ID："+noteId);
		System.out.println("目标笔记本ID："+noteBookId);
		Note note=noteService.doRemoveNote(noteId, noteBookId);
		return new JsonResult<Note>(note);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new JsonResult<Note>(e.getMessage());
//		}
	}
	/*
	 * 页面的搜索功能
	 */
	@RequestMapping("search.do")
	@ResponseBody
	public JsonResult<List<Map<String,Object>>> tosearchNotes(String key,int num,int size){
		List<Map<String,Object>> list=noteService.searchNotes(key, num, size);
		return new JsonResult<List<Map<String,Object>>>(list);
	}
}
