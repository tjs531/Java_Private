<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	Test02 페이지는 보일까? 
	<!-- html의 jsp태그 사용으로 forward -->
	<jsp:forward page="Test02_result.jsp">
		<jsp:param name = "user_age" value = "25" />
	</jsp:forward>
</body>
</html>