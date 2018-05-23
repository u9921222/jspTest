package mysqlTest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class HrDao extends HttpServlet {
	
	public HrDao() {
	
	}

	

	public static ArrayList<ArrayList<String>> getEmployees() {
	    String selectAll = "select name from EMPLOYEE";
        ArrayList<String> resultArgs = new ArrayList<>();
        resultArgs.add("name");
        ArrayList<ArrayList<String>> results = MySqlExecuter.executeQuery(selectAll,resultArgs);
        return results;
	}

	public static ArrayList<ArrayList<String>> getLeaveType() {
		String selectAll = "select chinese_desc from leave_type";
		ArrayList<String> resultArgs = new ArrayList<>();
		resultArgs.add("chinese_desc");
		ArrayList<ArrayList<String>> results = MySqlExecuter.executeQuery(selectAll, resultArgs);
		return results;
	}


	
	String leave_type_id,empl_id;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String tag = req.getParameter("tag");
		if(tag == null )tag = "";
		
////addEmp
		if (tag.equals("addEmp")) {
			System.out.println("addEmp");
			String name = req.getParameter("name");
			String impl_id = req.getParameter("impl_id");
			String department = req.getParameter("department");
			String e_mail = req.getParameter("email");
			String insertInto = "insert into EMPLOYEE(empl_id,name,department,email)" + "values (\" " + impl_id
					+ "\",\"" + name + "\",\"" + department + "\",\"" + e_mail + "\")";
			if(MySqlExecuter.executeUpdate(insertInto)) {
				req.setAttribute("addEmpStatus", "yay");
				req.setAttribute("message", "success");
				try {
					getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
////getEmp
		} else if (tag.equals("getEmp")) {
			System.out.println("getEmp");
			String selectAll = "select * from EMPLOYEE";
			ArrayList<String> resultArgs = new ArrayList<>();
			resultArgs.add("empl_id");
			resultArgs.add("name");
			resultArgs.add("department");
			resultArgs.add("email");
			ArrayList<ArrayList<String>> results = MySqlExecuter.executeQuery(selectAll, resultArgs);
			if(results!=null) {
				req.setAttribute("getEmpStatus", "yay");
				req.setAttribute("message",results);
				try {
					getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
////sentLeave
		} else if (tag.equals("sentLeave")) {
			System.out.println("sentLeave");
			String name = req.getParameter("employees");
			String chinese_desc = req.getParameter("leavestats");
			String start_dt = req.getParameter("start_dt");
			String end_dt = req.getParameter("end_dt");
			// name = employeeMap.get(name);
			// chinese_desc = leaveStatMap.get(chinese_desc);
			// System.out.println(employeeMap.size()+" "+leaveStatMap.size());
			System.out.println(name + " " + chinese_desc);

			String select_leave_type_id = "select leave_type_id from Leave_type where chinese_desc = \"" + chinese_desc
					+ "\"";
			ArrayList<String> resultArgs = new ArrayList<>();
			resultArgs.add("leave_type_id");
			
			ArrayList<ArrayList<String>> results = MySqlExecuter.executeQuery(select_leave_type_id, resultArgs);
			if(results != null) {
				System.out.println(results.get(0).get(0));
				
				String select_empl_id = "select empl_id from employee where name = \"" + name + "\"";
				ArrayList<String> resultArgs1 = new ArrayList<>();
				resultArgs1.add("empl_id");
				
				ArrayList<ArrayList<String>> results1 = MySqlExecuter.executeQuery(select_empl_id, resultArgs1);
				if(results1 != null) {
					System.out.println(results1.get(0).get(0));
					String insert_into_leave_stat = "insert into leave_stat(empl_id,leave_type_id,start_dt,end_dt)" + "values (\"" + results1.get(0).get(0)
							+ "\",\"" + results.get(0).get(0) + "\",\"" + start_dt + "\",\"" + end_dt + "\")";
				
				
					if(MySqlExecuter.executeUpdate(insert_into_leave_stat)) {
						req.setAttribute("sentLeave", "yay");
						req.setAttribute("message", "success");
						try {
							getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
						} catch (ServletException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		}
		out.close();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws  ServletException, IOException {
	  
	}


	public static void main(String[] args) {

	}
	
}
