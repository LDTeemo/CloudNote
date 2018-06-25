package cn.tedu.note.dao;

import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Note;

public interface NoteDao {
	//516f6f4f-eaa3-4c76-84ff-530b92c7f64d
	List<Map<String,Object>> getNotes(String nid);
	//noteId
	//e7cbaf1a-591f-4c61-8517-01f8c160e23e
	Note queryContent(String noteId);
	void modifyNote(Note note);
	void updateNote(Note note);
	void addNote(Note note);
	void removeNote(String noteId);
	/**
	 * 数组作为参数的更新方法
	 */
	void updateNoteDelType(String ... ids);
	/**
	 * 利用List作为动态查询参数
	 */
	Integer countNormalNote(List<String> ids);
	/*
	 * 使用Map作为动态参数，批量更新note的多个属性
	 * map={typeId:"2",lastModifyTime=1234556}
	 */
	void updateNotes(Map<String,Object> map);
	/*
	 * map={typeId:"2",lastModifyTime=1234556}
	 */
	void countNotes(Map<String,Object> map);
	void deleteNotes(String ... ids);
	List<Map<String,Object>> findAllByKeys(Map<String,Object> keys);
	/*
	 * 分页查询笔记列表
	 */
}
