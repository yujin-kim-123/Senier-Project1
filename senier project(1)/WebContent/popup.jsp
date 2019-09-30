<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="ssh.demo.main.SSHDemoMain"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>스마트카 상태 정보 예측</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");

		String car_capacity = request.getParameter("car_capacity");
		String car_age = request.getParameter("car_age");
		String car_model = request.getParameter("car_model");

		String tire1 = request.getParameter("tire1");
		String tire2 = request.getParameter("tire2");
		String tire3 = request.getParameter("tire3");
		String tire4 = request.getParameter("tire4");

		String light1 = request.getParameter("light1");
		String light2 = request.getParameter("light2");
		String light3 = request.getParameter("light3");
		String light4 = request.getParameter("light4");

		String engine = request.getParameter("engine");
		String brake = request.getParameter("brake");
		String battery = request.getParameter("battery");
		
		SSHDemoMain SDM = new SSHDemoMain();
		String status_result = SDM.input(car_capacity, car_age, car_model, tire1, tire2, tire3, tire4, light1, light2, light3, light4, engine, brake, battery);
	%>


	<h1><%=status_result%></h1>
	<!-- window.close()의 경우 크로보가 윈도우 창의 결과 다를 수 있음. 테스트 필요 -->
	<input type="button" value="닫기" onclick="window.close()" ; />


</body>
</html>