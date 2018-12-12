<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix='fmt' %> 

<html>

<head>
	<script src="js/jquery/2.0.0/jquery.min.js"></script>
	<link href="css/bootstrap/3.3.6/bootstrap.min.css" rel="stylesheet">
	<script src="js/bootstrap/3.3.6/bootstrap.min.js"></script>
	<link href="css/back/style.css" rel="stylesheet">
	
<script>
/* 判断输入的内容是否为空 */
function checkEmpty(id, name){
	
	var value = $("#"+id).val();
	if(value.length == 0){
		alert(name+"不能为空");
		$("#"+id)[0].focus();
		return false;
	} else {
		return true;
	}
}	
/* function checkEmpty(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		// 为空则焦点显示
		$("#"+id)[0].focus();
		return false;
	}
	return true; 
}*/

/* 判断输入内容是否是数字 */
function checkNumber(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id)[0].focus();
		return false;
	}
	if(isNaN(value)){
		alert(name+ "必须是数字");
		$("#"+id)[0].focus();
		return false;
	}
	
	return true;
}

/* 判断输入的内容是否是整数 */
function checkInt(id, name){
	var value = $("#"+id).val();
	if(value.length==0){
		alert(name+ "不能为空");
		$("#"+id)[0].focus();
		return false;
	}
	if(parseInt(value)!=value){
		alert(name+ "必须是整数");
		$("#"+id)[0].focus();
		return false;
	}
	
	return true;
}


/* 删除的链接都要确认一下是否的确要删除 */
$(function(){
	$("a").click(function(){
		//attr()的返回值是字符串
		var deleteLink = $(this).attr("deleteLink");
		console.log(deleteLink);
		if("true"==deleteLink){
			//confirm()表示一个弹出框，返回值表示确认或取消
			var confirmDelete = confirm("确认要删除");
			if(confirmDelete)
				return true;
			return false;
			
		}
	});
})
</script>	
</head>
<body>

