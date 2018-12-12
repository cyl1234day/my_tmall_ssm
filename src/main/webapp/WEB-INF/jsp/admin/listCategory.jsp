<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
$(function(){
	
	$("#addForm").submit(function(){
		if(!checkEmpty("name","分类名称"))
			return false;
		if(!checkEmpty("categoryPic","分类上传图片"))
			return false;		
		return true;
	});
});

</script>

<!-- 显示在浏览器顶部的文字 -->
<title>分类管理</title>

<div class="workingArea">
	<h1 class="label label-info" >分类管理</h1>
	<br>
	<br>
	
	<div class="listDataTableDiv">
		<table class="table table-striped table-bordered table-hover  table-condensed">
			<thead>
				<tr class="success">
					<th>ID</th>
					<th>图片</th>
					<th>分类名称</th>
					<th>属性管理</th>
					<th>产品管理</th>
					<th>编辑</th>
					<th>删除</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${categorys}" var="c">
					<tr>
						<th>${c.id}</th>
						<th><img height="40px" src="img/category/${c.id}.jpg"/></th>
						<th>${c.name}</th>
						<th><a href="admin_property_list?cid=${c.id}"><span class="glyphicon glyphicon-th-list"></span> </a></th>
						<th><a href="admin_product_list?cid=${c.id}"><span class="glyphicon glyphicon-shopping-cart"></span> </a></th>
						<th><a href="admin_category_edit?id=${c.id}"><span class="glyphicon glyphicon-edit"></span> </a></th>
						<th><a href="admin_category_delete?id=${c.id}" deleteLink="true"><span class="glyphicon glyphicon-trash"></span> </a></th>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<div class="pageDiv">
		<%@include file="../include/admin/adminPage.jsp" %>
	</div>
	
	<div class="panel panel-warning addDiv">
	  <div class="panel-heading">新增分类</div>
	  <div class="panel-body">
	    	<form method="post" id="addForm" action="admin_category_add" enctype="multipart/form-data">
	    		<table class="addTable">
	    			<tr>
	    				<td>分类名称</td>
	    				<td><input type="text" name="name" id="name"/></td>
	    			</tr>
	    			<tr>
	    				<td>分类图片</td>
	    				<td><input type="file" name="image" id="categoryPic"/></td>
	    			</tr>
	    			<tr>
	    				<td colspan="2" align="center">
	    					<input type="submit" class="btn btn-success" value="提交"/>
	    				</td>
	    			</tr>
	    		</table>
	    	</form>
	  </div>
	</div>
	
</div>

<%@include file="../include/admin/adminFooter.jsp"%>