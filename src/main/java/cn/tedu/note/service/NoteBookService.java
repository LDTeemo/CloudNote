package cn.tedu.note.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface NoteBookService extends Serializable {
	List<Map<String,Object>> getNoteBooks(String userId);
}
