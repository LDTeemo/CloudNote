package cn.tedu.note.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.tedu.note.entity.Note;


public interface NoteService extends Serializable {
	String NORMAL_TYPE="1";
	String DELETE_TYPE="2";
	String DISABLED_TYPE="3";
	
	String NORMAL_STATUS="1";
	String FAVOURITE_STATUS="2";
	List<Map<String,Object>> listNotes(String nid);
	Note getNoteContent(String noteId);
	void modifyNoteGo(String noteId,String noteTitle,String noteBody);
	Note doAddNote(String noteTitle,String userId,String noteBookId);
	Note doDeleteNote(String noteId);
	Note doRemoveNote(String noteId,String noteBookId);
	List<Map<String,Object>> searchNotes(String key,int pagNum,int PageSize);
}
