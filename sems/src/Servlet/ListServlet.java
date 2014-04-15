package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/subject/list.bit")
@SuppressWarnings("serial")
public class ListServlet extends HttpServlet{
	PreparedStatement stmt = null;

	public Connection getConnection() throws Throwable{
		Class.forName("com.mysql.jdbc.Driver");

		return DriverManager.getConnection(																									
				"jdbc:mysql://localhost:3306/studydb?useUnicode=true&characterEncoding=UTF-8",
				"study", "study"); 
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			Connection con = getConnection();
			PreparedStatement stmt = con.prepareStatement(
					"select SNO,TITLE from SE_SUBJS order by SNO asc limit ?,?");
			ResultSet rs = null;

			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");

			stmt.setInt(1,Integer.parseInt(pageNo));
			stmt.setInt(2, Integer.parseInt(pageSize));
			rs = stmt.executeQuery();

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();

			out.println("<html><head>");
			out.println("<style>tr{border:black solid 1px;}");
			out.println("table{border:black solid 1px; background-color: lightblue;}");
			out.println("table{text-align: center;}");
			out.println("th{border:black solid 1px;}"); 
			out.println("th:hover{color: white;}");
			out.println("td{border:black solid 1px; }");
			out.println("td:hover{color: white;}");
			
			out.println("</style></head>");
			out.println("<body><h1>과목정보</h1>");
			out.println("<table><tr><th>번호</th><th>과목명</th></tr>");
			while(rs.next()){
				out.println("<tr><td>" + rs.getInt("SNO") + "</td><td>" + 
						rs.getString("TITLE") + "</td></tr>");
			}
			out.println("</table></body></html>");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}