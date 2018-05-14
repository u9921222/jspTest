<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="mysqlTest.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=BIG5">
<title>Insert title here</title>
</head>
<body>
	新增員工資料:
	<br />
	<form method="post" action="/jspTest/jdbcmysql" id="addEmp">
		姓名: <input type="text" name="name" /> id： <input type="text"
			name="impl_id" /> 部門： <input type="text" name="department" />
		E-mail: <input type="text" name="email" /><br /> <input
			type="submit" value="提交" /> <input type="hidden" name="tag"
			value="addEmp" />
	</form>
	<%
		if (request.getAttribute("addEmpStatus") != null) {
			out.println("<h1>" + request.getAttribute("message") + "</h1>");
		}
	%>
	<br />全部員工資料:
	<br />
	<form method="post" action="/jspTest/jdbcmysql">
		<input type="submit" value="獲得" /> <input type="hidden" name="tag"
			value="getEmp">
	</form>
	<%
		if (request.getAttribute("getEmpStatus") != null) {
			ArrayList<String> result = (ArrayList) request.getAttribute("message");
			for (int i = 0; i < result.size(); i++)
				out.println("<h1>" + result.get(i) + "<br>" + "</h1>");
		}
	%>
	<!--請假欄位-->

	<br />請假申請：
	<br />
	<form method="post" action="/jspTest/jdbcmysql">
		<select name="employees">
			<%
				Jdbcmysql jdbcmysql = new Jdbcmysql();
				ArrayList<String> employees = jdbcmysql.getEmployees();
				for (int count = 0; count < employees.size(); count++) {
			%>
			<option value="<%=employees.get(count)%>"><%=employees.get(count)%></option>
			<%
				}
			%></select> 
		<select name="leavestats">
			<%
				ArrayList<String> leavetypes = jdbcmysql.getLeaveType();
				for (int count = 0; count < leavetypes.size(); count++) {
			%>
			<option value="<%=leavetypes.get(count)%>"><%=leavetypes.get(count)%></option>
			<%
				}
			%>
		</select>
		從: <input type="text" name="start_dt" /> 到： <input type="text"
			name="end_dt" />
		 <br /> 
		<input type="submit" value="獲得" /> 
		<input type="hidden"
			name="tag" value="sentLeave">
	</form>
	<%
		if (request.getAttribute("sentLeave") != null) {
			String result = (String) request.getAttribute("message");
			out.println("<h1>" + result + "<br>" + "</h1>");
		}
	%>
</body>
</html>