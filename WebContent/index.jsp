<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="mysqlTest.ServletHome"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="http://code.jquery.com/jquery-1.10.2.js"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<style>
</style>
<script>
	$(function() {
		$("#datepickerStart").datepicker({
			//可使用下拉式選單 - 月份
			changeMonth : true,
			//可使用下拉式選單 - 年份
			changeYear : true,
			//設定 下拉式選單月份 在 年份的後面
			showMonthAfterYear : true
		});
		$("#datepickerEnd").datepicker({
			//可使用下拉式選單 - 月份
			changeMonth : true,
			//可使用下拉式選單 - 年份
			changeYear : true,
			//設定 下拉式選單月份 在 年份的後面
			showMonthAfterYear : true
		});
	});
</script>


</head>
<body>
	新增員工資料:
	<br />
	<form method="post" action="/jspTest/servletHome" id="addEmp">
		姓名:	 <input type="text" name="name" /> 
		id：	 <input type="text" name="impl_id" /> 
		部門：	 <input type="text" name="department" />
		E-mail: <input type="text" name="email" /><br /> 
			<input	type="submit" value="提交" /> 
			<input type="hidden" name="tag"	value="addEmp" />
	</form>
	<%
		if (request.getAttribute("addEmpStatus") != null) {
			out.println("<h1>" + request.getAttribute("message") + "</h1>");
		}
	%>
	<br /> 
	
	
	全部員工資料:
	<br />
	<form method="post" action="/jspTest/servletHome">
		<input type="submit" value="獲得" />
		<input type="hidden" name="tag"	value="getEmp">
	</form>
	<%
		if (request.getAttribute("getEmpStatus") != null) {
			out.println("<table bgcolor=\"9999dd\" border=\"1\">");
			ArrayList<ArrayList<String>> result = (ArrayList) request.getAttribute("message");
			for (int row = 0; row < result.size(); row++) {
				out.println("<tr>");
				for (int column = 0; column < result.get(row).size(); column++) {
					out.println("<td>");
					out.println(result.get(row).get(column));
					out.println("</td>");
				}
				out.println("</tr>");
			}
			out.println("</table>");
		}
	%>
	<br />
	
	請假申請：
	<br />
	<form method="post" action="/jspTest/servletHome">
		<select name="employees">
			<%
				ArrayList<ArrayList<String>> employees = ServletHome.getEmployees();
				for (int count = 0; count < employees.size(); count++) {
			%>
			<option value="<%=employees.get(count).get(0)%>"><%=employees.get(count).get(0)%></option>
			<%
				}
			%>
		</select> <select name="leavestats">
			<%
				ArrayList<ArrayList<String>> leavetypes = ServletHome.getLeaveType();
				for (int count = 0; count < leavetypes.size(); count++) {
			%>
			<option value="<%=leavetypes.get(count).get(0)%>"><%=leavetypes.get(count).get(0)%></option>
			<%
				}
			%>
		</select>
				從：<input type="text" id="datepickerStart" name="start_dt">
				到：<input type="text" id="datepickerEnd" name="end_dt">
		</br>
		<input type="hidden" name = "tag" value = "sentLeave">
		<input type="submit" value = "提交">
		</br>
	</form>
	<%
		if (request.getAttribute("sentLeave") != null) {
			String result = (String) request.getAttribute("message");

			out.println("<h1>" + result + "<br>" + "</h1>");
		}
	%>
</body>
</html>