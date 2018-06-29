package cn.tedu.note.service;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tedu.note.dao.NoteBookDao;
@Service("noteBookService")
public class NoteBookServiceImpl implements NoteBookService {


	@Autowired
	private NoteBookDao notebookDao;
	public List<Map<String, Object>> getNoteBooks(String userId) {
		//入口参数必须做判断！可以有效避免空指针异常
		if(userId==null||userId.trim().isEmpty()){
			throw new ServiceException("userId不能为空");
		}
		return notebookDao.findNoteBookByUserId(userId);
	}

}