<!-- 
		在WebContent目录下新建admin目录
		接着在admin目录下新建index.jsp文件
		这个文件的作用是访问如下地址的时候：
		http://127.0.0.1:8080/tmall_ssh/admin
		
		会自动跳转到
		http://127.0.0.1:8080/tmall_ssh/admin_category_list
 -->
<% 
	response.sendRedirect("../admin_category_list");
%>