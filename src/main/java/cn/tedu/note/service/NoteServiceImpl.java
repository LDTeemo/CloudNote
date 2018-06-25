package cn.tedu.note.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.tedu.note.dao.NoteDao;
import cn.tedu.note.entity.Note;

@Service("noteService")
public class NoteServiceImpl implements NoteService {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private NoteDao noteDao;
	@Transactional(readOnly=true)
	public List<Map<String, Object>> listNotes(String nid) {
		//入口参数检查
		if(nid==null||nid.trim().isEmpty()){
			throw new ServiceException("笔记本ID不能为空");
		}
		return noteDao.getNotes(nid);
	}
	@Transactional(readOnly=true)
	public Note getNoteContent(String noteId) {
		if(noteId==null||noteId.trim().isEmpty()){
			throw new ServiceException("笔记ID为空");
		}
		return noteDao.queryContent(noteId);
	}
	/*
	 * 编辑笔记（只修改了标题，内容，和最后创建时间）
	 * (non-Javadoc)
	 * @see cn.tedu.note.service.NoteService#modifyNoteGo(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	public void modifyNoteGo(String noteId, String noteTitle, String noteBody) {
		//检查入口参数
		if(noteId==null||noteId.trim().isEmpty()){
			throw new ServiceException("笔记Id为空");
		}
		if(noteTitle==null){
			noteTitle="";
		}
		if(noteBody==null){
			noteBody="";
		}
		//根据传进来的noteId先将note查出来
		Note note=getNoteContent(noteId);
		Long lastModifyTime=System.currentTimeMillis();
		note.setNoteTitle(noteTitle);
		note.setNoteBody(noteBody);
		note.setLastModifyTime(lastModifyTime);
		noteDao.modifyNote(note);
	}



	/*
	 * 添加一条笔记
	 * (non-Javadoc)
	 * @see cn.tedu.note.service.NoteService#addNote(cn.tedu.note.entity.Note)
	 */
	@Transactional
	public Note doAddNote(String noteTitle, String userId, String noteBookId) {
		//入口参数检查
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("用户Id为空");
		}
		if(noteBookId==null||noteBookId.trim().isEmpty()){
			throw new ServiceException("笔记本Id为空");
		}
		if(noteTitle==null||noteTitle.trim().isEmpty()){
			noteTitle="未命名";
		}
		Note note=new Note();
		note.setNoteBookId(noteBookId);
		Long time=new Date().getTime();
		note.setNoteCreatTime(time);
		note.setNoteBody("");
		note.setNoteId(UUID.randomUUID().toString());
		note.setUserId(userId);
		note.setNoteStatusId(NORMAL_STATUS);//正常状态
		note.setNoteTitle(noteTitle);
		note.setNoteTypeId(NORMAL_TYPE);//正常类型
		note.setLastModifyTime(time);
		//System.out.println("业务层创建的note对象："+note);
		noteDao.addNote(note);
		return note;
	}
	/**
	 * 更新笔记的各个字段的值
	 */
	@Transactional
	public Note doDeleteNote(String noteId) {
		if(noteId==null||noteId.trim().isEmpty()){
			throw new ServiceException("笔记Id不能为空");
		}
		Note note=noteDao.queryContent(noteId);
		if(note==null){
			throw new ServiceException("不存在该笔记");
		}
		if(!note.getNoteTypeId().equals(DELETE_TYPE)){
			note.setNoteTypeId(DELETE_TYPE);
			note.setLastModifyTime(System.currentTimeMillis());
			noteDao.updateNote(note);
			return note;
		}else{
			throw new ServiceException("只能删除已存在笔记");
		}
	}
	/**
	 * 移动笔记至其他笔记本
	 */
	@Transactional
	public Note doRemoveNote(String noteId, String noteBookId) {
		if(noteId==null||noteBookId.trim().isEmpty()){
			throw new ServiceException("笔记ID为空");
		}
		if(noteBookId==null||noteBookId.trim().isEmpty()){
			throw new ServiceException("笔记本ID为空");
		}
		Note note=noteDao.queryContent(noteId);
		if(note==null){
			throw new ServiceException("笔记Id无效");
		}
		note.setNoteBookId(noteBookId);
		note.setLastModifyTime(System.currentTimeMillis());
		noteDao.updateNote(note);
		return note;
	}
	/**
	 * key查找的关键字
	 * 页码，页面显示条数
	 */
	@Transactional(readOnly=true)
	public List<Map<String,Object>> searchNotes(String key, int pageNum, int PageSize) {
		int start=PageSize*pageNum;
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("start", start);
		map.put("length", PageSize);
		if(key!=null){
			key=key.trim();
			if(!key.isEmpty()){
				key="%"+key+"%";
				map.put("noteTitle", key);
				map.put("noteBody", key);
			}
		}
		List<Map<String,Object>> list=noteDao.findAllByKeys(map);
		return list;
	}
}
