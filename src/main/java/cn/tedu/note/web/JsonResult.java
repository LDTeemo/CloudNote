package cn.tedu.note.web;

import java.io.Serializable;

/**
 * json返回值，用于Json返回数据的对象类
 * @author tarena
 *
 */
public class JsonResult <T> implements Serializable{
	public static final int SUCCESS=0;
	public static final int ERROR=1;

	private int state;
	private String message;
	private T data;

	public JsonResult() {

	}
	public JsonResult(String errorMessage) {
		this(ERROR,errorMessage,null);
	}
	public JsonResult(int state,String message){
		this(state,message,null);
	}
	public JsonResult(T data){
		this(SUCCESS,"",data);
	}
	public JsonResult(int state, String message, T data) {
		super();
		this.state = state;
		this.message = message;
		this.data = data;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

}
