//   scripts/edit.js 
/*
 * 笔记编辑界面的JS脚本
 * 
 */
//定义一个全局的数据模型model
var model={
		notebooks:[],
		currentNotebook:{},
		notes:[],
		noteBookIndex:0,
		noteIndex:0,
		currentNote:{},
		gettedNote:{}//获取的笔记本的内容等信息
};

$(function(){
	//页面加载完毕后可以获取登录用户的ID
	var userId=getCookie("userId");
	listNoteBook(userId);
	/*
	 * 向保存笔记id=save_note按钮添加点击事件
	 */
	$("#save_note").click(editNote);
	//网页加载完毕后，将增加笔记按钮增加点击事件
	$("#add_note").click(addNoteAction);
})
function listNoteBook(userId){
	/**
	 * 页面加载完毕之后利用ajax发送异步请求，通过UserID获取笔记本列表
	 */
//	$.ajax({
//		url:"notebook/list.do?userId="+userId,
//		type:"get",
//		//data:userId,
//		dataType:"json",
//		success:function(result){
//			if(result.state==SUCCESS){
//				var list=result.data;
//				for(var i=0;i<list.length;i++){
//					console.log(list[i])
//				}
//			}else{
//				alert(result.message);
//			}
//		}
//	});
	
	//jQuery中有getJSON(url,function(){})这样的简洁方式从服务器发送请求获取数据
	var url="notebook/list.do?userId="+userId;
	$.getJSON(url,function(result){
		if(result.state==SUCCESS){
			var list=result.data;
			//更新数据模型
			model.notebooks=list;
			//刷新笔记本列表的显示
			paintnotebooks();
		}else{
			alert(result.message);
		}
	});
}
/**
 * 该方法用于将数据模型中的数据更新显示到页面
 * <li class="online">
   <a  class='checked'>
   <i class="fa fa-book" title="online" rel="tooltip-bottom">
   </i> 默认笔记本</a></li>
 */
function paintnotebooks(){
	var list=model.notebooks;
	var ul=$("#notebooks");
	ul.empty();
	for(var i=0;i<list.length;i++){
		var notebook=list[i];
		var li='<li class="online">'+
					'<a>'+
					'<i class="fa fa-book" title="online" rel="tooltip-bottom"></i>'+
					notebook.notebookName+
					'</a>'+
				'</li>';
		//e.data()向元素绑定数据
		li=$(li).data("index",i);
		li.click(function(){
			//$(this)获取的是li元素
			//每进行点击的时候应该将上一次选中的情况取消掉
			$(this).parent().find("a").removeClass("checked");
			//点击时选中该li元素
			$(this).children("a").addClass("checked");
			var index=$(this).data("index");
			//将点击的该noteBook的Index存到数据模型中
			model.noteBookIndex=index;
			//console.log(index);
			//点击哪个笔记本，获取该笔记本
			var notebook=model.notebooks[index];
			model.currentNotebook=notebook;
			listCurrentNotesAction();
		});
		ul.append(li);
	}
}
//控制器方法：获取数据，更行模型
function listCurrentNotesAction(){
	var nid=model.currentNotebook.bookId;
	var url="note/listNote.do?nid="+nid;
	//发送异步请求更新model.notes
	$.getJSON(url,function(result){
		if(result.state==SUCCESS){
			var notes=result.data;
			//将返回的notes（是一个Json对象）
			model.notes=notes;
			//调用paintNotes()
			paintNotes();
		}else{
			alert(result.message);
		}
	});
	
}

//将数据模型的notes显示到界面
function paintNotes(){
	
	var list=model.notes;
	var ul=$("#notes");
	ul.empty();
	//console.log(list.length);
	for(var i=0;i<list.length;i++){
		//console.log(list[i].noteTitle);
		var li='<li class="online">'+
		'<a>'+
		'<i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>'+
		list[i].noteTitle+
		'<button type="button" disabled="disabled" class="btn btn-default btn-xs btn_position btn_slide_down">'+
		'<i class="fa fa-chevron-down"></i>'+
		'</button></a>'+
		'<div class="note_menu" tabindex="-1"><dl>'+
		'<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>'+
		'<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>'+
		'<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>'+
		'</dl></div></li>'
		//向li元素绑定角标，点击哪个li就绑定哪个角标
		li=$(li).data("index",i);
		//向每一个li增加点击事件，点击后执行里头的函数
		li.click(function(){
			//点击要选中，所以增加样式。选中的是'a'这个标签，但在点击之前要将上次所选中的取消掉，索性就全部取消
			$(this).parent().find("a").removeClass("checked");
			//由于菜单按钮默认是disabled，点击选定该笔记后应该，将该disabled取消掉
			$(this).parent().find("li>a>button").attr("disabled","disabled");
			$(this).find("button").removeAttr("disabled");
			//重新点击后菜单也要收进去
			$(this).parent().parent().find(".note_menu").slideUp();
			//点击之后要选中该元素，即增加样式
			$(this).children("a").addClass("checked");
			//获取点击的角标，用于获取到该条笔记
			var index=$(this).data("index");
			//把点击的该条笔记的角标更新到数据模型中
			model.noteIndex=index;
			//console.log(index);
			//获取该条笔记，存到数据模型中
			var note=list[index];
			//把当前点击的笔记也更新到数据模型中
			model.currentNote=note;
			//调用showCurrentNoteAction()
			showCurrentNoteAction();
			//console.log(note);
			//绑定每一条笔记旁的菜单中的每一按钮的点击事件-删除
			$(this).find(".btn_delete").unbind("click").click(function(){
				//console.log("选中了");
				removeNote();
				return false;//防止点击事件传播
			});
			return false;
		});
		ul.append(li);
		//绑定每一条笔记旁边的菜单的点击事件
		li.children("a").children("button").click(function(){
			console.log("Hi");
			$(this).parent().parent().children(".note_menu").slideToggle(200);
			return false;//用于阻止事件传播
		});
		//菜单里的移动按钮
		li.find(".btn_move").click(function(){
			moveNoteAction();
			return false;
		});
	}
	//将第一个笔记设置为默认状态---没有必要
	//$("#notes").children("li").first().find("a").addClass("checked");
	//$("#notes").children("li").first().trigger("click");
}
//显示当前选中的笔记的内容
function showCurrentNoteAction(){
	var note=model.currentNote;
	//console.log(note)
	var noteId=note.noteId;
	var noteTitle=note.noteTitle;
	//console.log(noteId);
	//发送ajax异步请求
	//e7cbaf1a-591f-4c61-8517-01f8c160e23e
	var url="note/listNoteContent.do?noteId="+noteId;
	$.getJSON(url,function(result){
		if(result.state==SUCCESS){
			//console.log(result.data);
			//将获得数据添加到数据迷行Model中去
			model.gettedNote=result.data;
			//console.log(model.gettedNote);
			paintNoteContent();
		}
	});
}
//显示笔记内容
function paintNoteContent(){
	//获取数据模型中的返回的该条笔记
	var note=model.gettedNote;
	//console.log(note.noteTitle);
	//写在对应的位置 title在id=input_note_title的input元素中
	$("#input_note_title").val(note.noteTitle);
	//使用超文本编辑器um对象。在html页面已经定义，直接使用即可
	//$("#myEditor").html(note.noteBody);
	um.setContent(note.noteBody);
}

//修改笔记内容
function editNote(){
	//获取到当前正显示的笔记
	var note=model.gettedNote;
	console.log("noteId:"+note.noteId);
	//比较旧笔记的标题和内容与从元素中获取的是否有变动
	var title=$("#input_note_title").val();
	//console.log(title);
	var noteBody=um.getContent();
	if(note.noteTitle==title&&note.noteBody==noteBody){
		//console.log("dddddd")
		return;
	}
	var data={"noteId":note.noteId,"noteTitle":title,"noteBody":noteBody};
	//一下设置是，在点击后，显示“保存ing”，保存寸功后，又恢复原状
	$("save_note").html("保存中...").attr("disabled","disabled");
	$.ajax({
		url:"note/modifyNote.do",
		type:"post",
		data:data,
		dataType:"json",
		success:function(result){
			if(result.state==SUCCESS){
				//console.log("成功");
				$("save_note").html("保存笔记").removeAttr("disabled");
				//保存成功后将笔记名所在的列表中的笔记名字改为修改后的
				//由于之前已经在model中保存了当前点击的那一条笔记的基表所以直接取出来即可
				model.currentNote.noteTitle=title;
				//console.log("ceshi:"+model.currentNote.noteTitle);
				paintNotes();
			}
		}
			
	});
	
}

//增加笔记
function addNoteAction(){
	//添加对话框
	var url="alert/alert_addNote.html";
	$("#can").load(url,function(response,status,xhr){
		if(status=="success"){
			//绑定按钮事件
			$("#can .cancle,#can .close").click(function(){
				$("#can>div").slideUp(500,function(){$("#can").empty()});
			});
			//再给确定按钮绑定函数，点击后执行
			$("#can .sure").click(addNote);
			$("#can>div").slideDown(500);
		}else{
			$("#can").empty();
		}
	});
}

//增加笔记
function addNote(){
	//console.log("addNote()")
	//获取当前需要添加笔记所在的笔记本Id
	var currentNoteBook=model.currentNotebook;
	//console.log("点击了该笔记本："+currentNoteBook);
	//获取到了所在笔记本才能添加笔记，否则无法添加
	if(currentNoteBook.bookId){
		console.log("点击了该笔记本："+currentNoteBook.bookId);
		var noteTitle=$("#input_note").val();
		//console.log(noteTitile);
		var data={
				"noteBookId":currentNoteBook.bookId,
				"userId":getCookie("userId"),
				"noteTitle":noteTitle
		}
		var url="note/addNote.do"
		$.post(url,data,function(result){
			if(result.state==SUCCESS){
				//添加成功后在页面显示保存成功，然后再关闭弹出框
				$("#can").load("alert/alert_success.html",function(){
					$("#can div").show();
					console.log("保存成功");
				});
				setTimeout(function(){
					$("#can").empty();
					//重新更新笔记列表
					//listCurrentNotesAction();添加成功后不应该用发送请求重新，浪费流量
					//返回的笔记存到数据模型中
					//console.log(result.data.noteId);
					//console.log(model.notes);
					var arr=[{"noteId":result.data.noteId,"noteTitle":result.data.noteTitle}];
					model.notes=arr.concat(model.notes);
					//console.log(model.notes);
					paintNotes();
				}, 300)
			}
		})
	}else{
		//加载错误提示页面
		$("#can").load("alert/alert_error.html",function(response,status,xhr){
			//在错误提示页面输入错误提示信息
			$("#can>div").show();//因为我们在main.css中增加了一个样式diaplay:none,所有要显示
			$("#can .modal-body").html("请先选中笔记本！");
			//给错误提示窗口的按钮添加点击事件
			$("#can .cancle,#can .close").click(function(){
				$("#can").empty();
			});
		});
		
	}
	
}

//移除笔记
function removeNote(){
	//console.log("removeNote()");
	//console.log(model.currentNote.noteId);
	//console.log(li);
	var noteId=model.currentNote.noteId;
	var url="note/deleteNote.do?noteId="+noteId;
	$.getJSON(url,function(result){
		if(result.state==SUCCESS){
			//console.log("已经更改");
			var notes=model.notes;
			var index=model.noteIndex;
			notes.splice(index, 1);
			model.notes=notes;
			paintNotes();
			
			
		}
	});
}

//移动笔记至其他笔记本
function moveNoteAction(){
	//console.log("进来了吗");
	var url="alert/alert_move.html?t="+Math.random();
	$("#can").load(url,function(){
		var currentNote=model.currentNote;
		//console.log(currentNote.noteId);
		$("#can #modalBasicLabel_11").html("移动笔记:	《"+currentNote.noteTitle+"》");
		var noteBooks=model.notebooks;
		//打印下拉选择列表
		for(var i=0;i<noteBooks.length;i++){
			$("#can #moveSelect").append($("<option></option>")
					.val(noteBooks[i].bookId).html(noteBooks[i].notebookName));
		}
		$("#can div").show();
		//给弹出的对话框的取消标签添加点击事件让$("#can).empty()
		$("#can .cancle,#can .close").click(function(){
			$("#can div").hide(function(){
				$("#can").empty();
			});
		});
		//给弹出对话框的确定标签设定点击事件
		
		$("#can .sure").click(function(){
			moveNoteAjaxAction();
			$("#can div").hide(function(){
				$("#can").empty();
			});
		});
	});
	return false;
}

//移动笔记发送Ajax请求-异步请求
function moveNoteAjaxAction(){
	//console.log("进来了！");
	var data={};
	//变量notes和index用于将被移动的笔记从当前笔记数组中删除
	var notes=model.notes;
	var index=model.noteIndex;
	data.noteId=model.currentNote.noteId;
	console.log("当前笔记ID："+data.noteId);
	//var slectedBookId = $('#moveSelect').val();
	data.noteBookId=$("#can .form-control").find("option:selected").val();
	//如果目标笔记本的ID跟当前笔记本的ID相等则返回
	var currentBookId=model.currentNotebook.bookId;
	console.log("当前笔记本ID"+currentBookId);
	console.log("目标笔记本ID"+data.noteBookId);
	if(currentBookId==data.noteBookId){
		return;
	}
	var url="note/removeNote.do";
	$.post(url,data,function(result){
		if(result.state==SUCCESS){
			//移动成功了，把当前的笔记在
			notes.splice(index, 1);
			model.notes=notes;
			paintNotes();
			return true;
		}else{
			alert(result.message);
		}
	});
}