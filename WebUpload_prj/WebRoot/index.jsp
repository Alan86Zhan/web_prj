<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body style="background: url(res/login10.png);background-size:cover;font-family: 微软雅黑;">
  <div id="main">
		<center><h3>请上传文件(please upload file)</h3></center>
   <form action="${pageContext.request.contextPath}/servlet/UploadHandleServlet" enctype="multipart/form-data" method="post">
     <br/>
     <br/>
     <center>用户名(username)：<input type="text" name="username"><br/></center>
     <br/>
     <br/>
     <center>.pem文件(.pem file)：<input type="file" name="file1"><br/></center>
     <br/>
     <br/>
  <center><input type="submit" value="submit"></center>
 </form>
     <br/>
     <br/>
     <br/>
     <br/>
     <br/>
     <br/>
     <br/>
     <br/>
     <br/>
     <br/>
 <a style="margin-left: 300px;"><font size="2"><i>注意：请对应输入以.pem结尾的文件和打包为以.zip结尾的文件
 (Note:Please ensure input file's name end with .pem or.zip)。</i></font></a>
 </div>	
  </body>
</html>
