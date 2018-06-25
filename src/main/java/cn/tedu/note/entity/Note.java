package cn.tedu.note.entity;

import java.io.Serializable;

public class Note implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String noteId;
	private String noteBookId;
	private String userId;
	private String noteStatusId;
	private String noteTypeId;
	private String noteTitle;
	private String noteBody;
	private Long noteCreatTime;
	private Long lastModifyTime;
	public Note() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Note [noteId=" + noteId + ", noteBookId=" + noteBookId + ", userId=" + userId + ", noteStatusId="
				+ noteStatusId + ", noteTypeId=" + noteTypeId + ", noteTitle=" + noteTitle + ", noteBody=" + noteBody
				+ ", noteCreatTime=" + noteCreatTime + ", lastModifyTime=" + lastModifyTime + "]";
	}
	public Note(String noteId, String noteBookId, String userId, String noteStatusId, String noteTypeId,
			String noteTitle, String noteBody, Long noteCreatTime, Long lastModifyTime) {
		super();
		this.noteId = noteId;
		this.noteBookId = noteBookId;
		this.userId = userId;
		this.noteStatusId = noteStatusId;
		this.noteTypeId = noteTypeId;
		this.noteTitle = noteTitle;
		this.noteBody = noteBody;
		this.noteCreatTime = noteCreatTime;
		this.lastModifyTime = lastModifyTime;
	}
	public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	public String getNoteBookId() {
		return noteBookId;
	}
	public void setNoteBookId(String noteBookId) {
		this.noteBookId = noteBookId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNoteStatusId() {
		return noteStatusId;
	}
	public void setNoteStatusId(String noteStatusId) {
		this.noteStatusId = noteStatusId;
	}
	public String getNoteTypeId() {
		return noteTypeId;
	}
	public void setNoteTypeId(String noteTypeId) {
		this.noteTypeId = noteTypeId;
	}
	public String getNoteTitle() {
		return noteTitle;
	}
	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}
	public String getNoteBody() {
		return noteBody;
	}
	public void setNoteBody(String noteBody) {
		this.noteBody = noteBody;
	}
	public long getNoteCreatTime() {
		return noteCreatTime;
	}
	public void setNoteCreatTime(Long noteCreatTime) {
		this.noteCreatTime = noteCreatTime;
	}
	public long getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(Long lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((noteId == null) ? 0 : noteId.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Note other = (Note) obj;
		if (noteId == null) {
			if (other.noteId != null)
				return false;
		} else if (!noteId.equals(other.noteId))
			return false;
		return true;
	}
	
	
	
	
}
