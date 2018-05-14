package mysqlTest;

import java.sql.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class Jdbcmysql extends HttpServlet {

	private Connection con = null; // Database objects
	// 連接object
	private Statement stat = null;
	// 執行,傳入之sql為完整字串
	private ResultSet rs = null;
	// 結果集
	private PreparedStatement pst = null;
	// 執行,傳入之sql為預儲之字申,需要傳入變數之位置

	public Jdbcmysql() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String jdbcUrl = "jdbc:mysql://localhost:3306/javabase?serverTimezone=UTC&useUnicode=true&characterEncoding=Big5";
			// "jdbc:mysql://localhost/javabase?useUnicode=true&characterEncoding=Big5"
			// 註冊driver
			con = DriverManager.getConnection(jdbcUrl, "root", "admin");
			// 取得connection

			// jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=Big5
			// localhost是主機名,test是database名
			// useUnicode=true&characterEncoding=Big5使用的編碼
			System.out.println("JDBC Initialize OK");
		} catch (ClassNotFoundException e) {
			System.out.println("DriverClassNotFound :" + e.toString());
		} // 有可能會產生sqlexception
		catch (SQLException x) {
			System.out.println("Exception :" + x.toString());
		}

	}

	ArrayList<String> employees = null;

	public ArrayList<String> getEmployees() {
		String selectAll = "select name from EMPLOYEE";
		ArrayList<String> resultArgs = new ArrayList<>();
		resultArgs.add("name");
		this.MySQLexecuteQuery(selectAll, resultArgs, new MySQLCallBack() {
			@Override
			public void onSuccess(Object returnObject) {
				employees = (ArrayList<String>) returnObject;
			}

			@Override
			public void onFail() {
				// TODO Auto-generated method stub

			}

		});
		return employees;
	}

	ArrayList<String> leavetypes = null;

	public ArrayList<String> getLeaveType() {
		String selectAll = "select chinese_desc from leave_type";
		ArrayList<String> resultArgs = new ArrayList<>();
		resultArgs.add("chinese_desc");
		this.MySQLexecuteQuery(selectAll, resultArgs, new MySQLCallBack() {
			@Override
			public void onSuccess(Object returnObject) {
				leavetypes = (ArrayList<String>) returnObject;
			}

			@Override
			public void onFail() {
				// TODO Auto-generated method stub

			}

		});
		return leavetypes;
	}

	HashMap<String, String> employeeMap = new HashMap<>();

	public void getEmployeeMap() {
		String selectAll = "select * from EMPLOYEE";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectAll);
			if (rs != null) {
				while (rs.next()) {
					String key = rs.getString("name");
					String value = rs.getString("empl_id");
					System.out.println("getEmployeeMap" + key + value);
					employeeMap.put(key, value);
					System.out.println("getEmployeeMap" + employeeMap.get(key));
				}
			}
		} catch (SQLException e) {
			System.out.println("MySQL Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	HashMap<String, String> leaveStatMap = new HashMap<>();

	public void getLeaveStatMap() {
		System.out.println("getLeaveStatMap1");
		String selectAll = "select * from leave_type";
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(selectAll);
			if (rs != null) {
				while (rs.next()) {
					String key = rs.getString("chinese_desc");
					String value = rs.getString("leave_type_id");
					System.out.println("getLeaveStatMap" + key + value);
					leaveStatMap.put(key, value);
				}
			}
		} catch (SQLException e) {
			System.out.println("MySQL Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	private void MySQLexecuteUpdate(String mySQLSyntax, ArrayList<String> prepareArgs, MySQLCallBack callback) {
		try {
			if (prepareArgs != null) {
				pst = con.prepareStatement(mySQLSyntax);
				for (int i = 0; i < prepareArgs.size(); i++) {
					pst.setString(i, prepareArgs.get(i));
				}
				pst.executeUpdate();
			} else {
				stat = con.createStatement();
				stat.executeUpdate(mySQLSyntax);
			}
			System.out.println("executeUpdateOK");
			callback.onSuccess(null);
		} catch (SQLException e) {
			callback.onFail();
			System.out.println("MySQL Exception :" + e.toString());
		} finally {
			Close();
		}
	}

	private void MySQLexecuteQuery(String mySQLSyntax, ArrayList<String> resultArgs, MySQLCallBack callback) {
		ArrayList<String> results = new ArrayList<>();
		boolean success = true;
		try {
			stat = con.createStatement();
			rs = stat.executeQuery(mySQLSyntax);
			if (rs != null) {
				while (rs.next()) {
					String result = "";
					for (int i = 0; i < resultArgs.size(); i++) {
						result = result + rs.getString(resultArgs.get(i)) + " ";
					}
					results.add(result);
				}
			}
		} catch (SQLException e) {
			results = null;
			success = false;
			System.out.println("MySQL Exception :" + e.toString());
		} finally {
			Close();
		}
		if (success)
			callback.onSuccess(results);
		else
			callback.onFail();
	}

	String leave_type_id,empl_id;
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("doPost1");
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String tag = req.getParameter("tag");

		if (tag.equals("addEmp")) {
			System.out.println("addEmp");
			String name = req.getParameter("name");
			String impl_id = req.getParameter("impl_id");
			String department = req.getParameter("department");
			String e_mail = req.getParameter("email");
			String insertInto = "insert into EMPLOYEE(empl_id,name,department,email)" + "values (\" " + impl_id
					+ "\",\"" + name + "\",\"" + department + "\",\"" + e_mail + "\")";
			this.MySQLexecuteUpdate(insertInto, null, new MySQLCallBack() {
				@Override
				public void onSuccess(Object obj) {
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

				@Override
				public void onFail() {
					// TODO Auto-generated method stub

				}
			});
		} else if (tag.equals("getEmp")) {
			System.out.println("getEmp");
			String selectAll = "select * from EMPLOYEE";
			ArrayList<String> resultArgs = new ArrayList<>();
			resultArgs.add("empl_id");
			resultArgs.add("name");
			resultArgs.add("department");
			resultArgs.add("email");
			this.MySQLexecuteQuery(selectAll, resultArgs, new MySQLCallBack() {
				@Override
				public void onSuccess(Object obj) {
					req.setAttribute("getEmpStatus", "yay");
					req.setAttribute("message", (ArrayList<String>) obj);
					System.out.println(((ArrayList<String>) obj).get(0));
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

				@Override
				public void onFail() {
					// TODO Auto-generated method stub
				}
			});
		} else if (tag.equals("sentLeave")) {
			System.out.println("sentLeave");
			// getEmployeeMap();
			// getLeaveStatMap();
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
			this.MySQLexecuteQuery(select_leave_type_id, resultArgs, new MySQLCallBack() {
				@Override
				public void onSuccess(Object obj) {
					leave_type_id = ((ArrayList<String>) obj).get(0);
					System.out.println(((ArrayList<String>) obj).get(0));

					String select_empl_id = "select empl_id from employee where name = \"" + name + "\"";
					ArrayList<String> resultArgs = new ArrayList<>();
					resultArgs.add("empl_id");
					Jdbcmysql.this.MySQLexecuteQuery(select_empl_id, resultArgs, new MySQLCallBack() {
						@Override
						public void onSuccess(Object obj) {
							empl_id = ((ArrayList<String>) obj).get(0);
							System.out.println(((ArrayList<String>) obj).get(0));
							String insert_into_leave_stat = "insert into leave_stat(empl_id,leave_type_id,start_dt,end_dt)" + "values (\"" + empl_id
									+ "\",\"" + leave_type_id + "\",\"" + start_dt + "\",\"" + end_dt + "\")";
							Jdbcmysql.this.MySQLexecuteUpdate(insert_into_leave_stat, null, new MySQLCallBack() {
								@Override
								public void onSuccess(Object obj) {
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
								@Override
								public void onFail() {
								}
							});
						}
						@Override
						public void onFail() {
						}
					});
				}
				@Override
				public void onFail() {
				}
			});
		}
		out.close();
	}

	// 完整使用完資料庫後,記得要關閉所有Object
	// 否則在等待Timeout時,可能會有Connection poor的狀況
	private void Close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (stat != null) {
				stat.close();
				stat = null;
			}
			if (pst != null) {
				pst.close();
				pst = null;
			}
		} catch (SQLException e) {
			System.out.println("Close Exception :" + e.toString());
		}
	}

	public static void main(String[] args) {

		Jdbcmysql test = new Jdbcmysql();
		// String selectAll = "select * from EMPLOYEE";
		// ArrayList<String> resultArgs = new ArrayList<>();
		// resultArgs.add("empl_id");
		// resultArgs.add("name");
		// resultArgs.add("department");
		// resultArgs.add("email");
		// test.MySQLexecuteQuery(selectAll, resultArgs);
		// String insertInto = "insert into EMPLOYEE(empl_id,name,department,email)
		// values (\"1\",\"2\",\"3\",\"4\")";
		// test.MySQLexecuteUpdate(insertInto,null);
	}

	public abstract class MySQLCallBack {
		public abstract void onSuccess(Object returnObject);

		public abstract void onFail();
	}
}
