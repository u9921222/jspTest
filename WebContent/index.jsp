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
	
	function handleClick(clickedId) {
        alert('javaScriptTest');
    }
</script>
<script type="text/javascript"> 
jQuery().ready(function (){
jQuery("#list1").jqGrid({
    url:'server.php?q=1',
    datatype: "xml",
    colNames:['Inv No','Date', 'Client', 'Amount','Tax','Total','Notes'],
    colModel:[
        {name:'id',index:'id', width:75},
        {name:'invdate',index:'invdate', width:90},
        {name:'name',index:'name', width:100},
        {name:'amount',index:'amount', width:80, align:"right"},
        {name:'tax',index:'tax', width:80, align:"right"},      
        {name:'total',index:'total', width:80,align:"right"},       
        {name:'note',index:'note', width:150, sortable:false}       
    ],
    rowNum:10,
    autowidth: true,
    rowList:[10,20,30],
    pager: jQuery('#pager1'),
    sortname: 'id',
    viewrecords: true,
    sortorder: "desc",
    caption:"XML Example"
}).navGrid('#pager1',{edit:false,add:false,del:false});                 
</script> 

<style>
#text_name_addemployee {
	background-color: #dddd55;
}
</style>


<link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen" href="js/src/plugins/jquery.searchFilter.css" />
<script src="js/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery.jqGrid.src.js"></script>
<script src="js/src/grid.loader.js" type="text/javascript"></script>

</head>
<body>
    <table id="list1"></table>
    <div id="pager1"></div>
    <br>

    
	新增員工資料:
	<br />
	<form method="post" action="/jspTest/servletHome" id="addEmp">
		姓名: <input type="text" id="text_name_addemployee" name="name" /> 
		id： <input type="text" name="impl_id" style="background-color:#dddd55"/> 
		部門： <input type="text" name="department" />
		E-mail: <input type="text" name="email" /><br /> 
		<input type="submit" value="提交" />
		<input type="hidden" name="tag"value="addEmp" />
	</form>
	<%
		if (request.getAttribute("addEmpStatus") != null) {
	                out.println("<h1>" + request.getAttribute("message") + "</h1>");
	            }
	%>
	<br /> 全部員工資料:
	<br />
	<form method="post" action="/jspTest/servletHome">
		<input type="submit" value="獲得" />
		 <input type="hidden" name="tag"
			value="getEmp">
	</form>
	<%
		if (request.getAttribute("getEmpStatus") != null) {
	                out.println("<table bgcolor=\"9999dd\" border=\"1\">");
	                ArrayList<ArrayList<String>> result = (ArrayList) request.getAttribute("message");
	                for (int row = 0; row < result.size(); row++) {
	                    out.println("<tr>");
	                    for (int column = 0; column < result.get(row).size() + 1; column++) {
	                        if (column == 0)
	                            out.println("<td><input TYPE=checkbox name=fruit VALUE=apples></td>");
	                        else {
	                            out.println("<td>");
	                            out.println(result.get(row).get(column - 1));
	                            out.println("</td>");
	                        }
	                    }
	                    out.println("</tr>");
	                }
	                out.println("</table>");
	            }
	%>
	<br /> 請假申請：
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
		</select> 從：<input type="text" id="datepickerStart" name="start_dt"> 到：<input
			type="text" id="datepickerEnd" name="end_dt"> <br> <input
			type="hidden" name="tag" value="sentLeave"> <input
			type="submit" value="提交"> <br>
	</form>
	<%
		if (request.getAttribute("sentLeave") != null) {
	                String result = (String) request.getAttribute("message");

	                out.println("<h1>" + result + "<br>" + "</h1>");
	            }
	%>
    
	<input type="button" onclick="javascript:handleClick()"
        name="javaScriptTest" value="jsButton"><br>
        
    <form method="get" action="/jspTest/servletHome">
        <input type="hidden" name="tag" value="sessionTest">
        <input type="submit" method="post" value="session test"/>
    </form>
    <br>
    <% out.println(session.getAttribute("sessionTest"));%>
</body>
</html>
