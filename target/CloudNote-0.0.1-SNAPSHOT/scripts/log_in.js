//登录界面中执行的脚本程序
//网页加载以后执行
$(function(){
	//为login绑定按钮事件
	$("#login").click(loginAction);
	$("#regist_button").click(registAction);
	//为验证码框添加时间
	$("#checkCode").blur(checkCode);
	//点击验证码事件
	$("#codeForCheck img").click(changeImg);
})
function loginAction(){
	/*
	 * 1.检查表单中数据的正确性
	 * 2.将表单数据发送到服务器
	 * 3.利用CallBack处理返回结果：如果成功就跳转到主页，如果失败，就显示错误消息
	 * \w{3,10}表示单字3-10个
	 */
	var name=$("#count").val();
	var password=$("#password").val();
	var reg=/^\w{3,10}$/;
	$("#count").removeClass("error");
	$("#password").removeClass("error");
	var pass=true;
	if(!reg.test(name)){
		$("#count").addClass("error");
		pass=false;
	}
	if(!reg.test(password)){
		$("#password").addClass("error");
		pass=false;
	}
	if(!pass){
		return false;
	}
	//用户输入格式正确后
	var data={"name":name,"pwd":password};
	$.ajax({
		url:"account/login.do",
		method:"post",
		dataType:"json",
		data:data,
		success:function(result){
			console.log(result);
			if(result.state==SUCCESS){
				//SetCookie，登录成功后将下述信息存到cookie用于其他业务
				SetCookie("userId", result.data.id);
				SetCookie("userName", result.data.name);
				SetCookie("userNick", result.data.nick);
				window.location="edit.html";
			}else{
				alert(result.message);
			}
		}
	});
}


function registAction(){
	var name=$("#regist_username").val();
	var nick=$("#nickname").val();
	var password=$("#regist_password").val();
	var confirm=$("#final_password").val();
	var pass=true;
	var nameReg=/^\w{3,10}$/;
	var nickReg=/^.{3,10}$/;
	//刚以进入页面不该闪烁
	//所有输入框的内容重置为空
	$("input").removeClass("error");
	$(".warning").hide();
	if(!nameReg.test(name)){
		//$("#warning_1").show();
		//或者
		pass=false;
		//如果不符合规则，将输入框变红
		$("#regist_username").addClass("error");
		$("#warning_1>span").html("3-8字符").parent().show();
	}
	//昵称格式检测
	if(!nickReg.test(nick)){
		pass=false;
		//如果不符合规则，将输入框变红
		$("#nickname").addClass("error");
		$("#warning_4>span").html("3-8字符").parent().show();
	}
	//密码格式检测
	if(!nameReg.test(password)){
		pass=false;
		$("#regist_password").addClass("error");
		$("#warning_2>span").html("6-10字符").parent().show();
	}
	//两次输入密码进行比较
	if(password!=confirm){
		pass=false;
		$("#final_password").addClass("error").next().show().html('<span>两次输入密码不一致</span>');
	}
	var data={
			"name":name,
			"password":password,
			"nick":nick
	}
	if(!pass){
		return false;
	}
	$.ajax({
		url:"account/regist.do",
		type:"post",
		dataType:"json",
		data:data,
		success:function(result){
			if(result.state==SUCCESS){
				//注册成功的话，获取到回退按钮，模拟点击动作
				$("input[type=text],input[type=password]").val("");
				$("#count").val(result.data.name);
				$("#password").focus();
				$("#back").click();
			}else{
				$("#warning_1>span").html(result.message).parent().show();
			}
		}
	});
}

//验证码检查
function checkCode(){
	//console.log("进来了");
	var codeInput=$("#checkCode").val();
	//console.log(codeInput);
	//向服务器发送异步请求
	var url="account/checkCode.do?inputCode="+codeInput;
	$.getJSON(url,function(result){
		if(result.state==SUCCESS){
			$("#checkCode").removeClass("error");
		}else{
			$("#checkCode").addClass("error");
		}
	});
}
//更换验证码
function changeImg(){
	//console.log("jinlai");
	var url="/CloudNote/account/code.do?num="+Math.random();
	$("#codeForCheck img").attr("src",url);
}