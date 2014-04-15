package Servlet;

import java.awt.TextField;
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

@WebServlet("/subject/detail.bit")
@SuppressWarnings("serial")
public class DetailServlet extends HttpServlet{
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
	    PreparedStatement stmt = con.prepareStatement("select SNO, TITLE, DEST from SE_SUBJS"
					+ " where SNO=?");
	    ResultSet rs = null;
	    
	    int no = Integer.parseInt(request.getParameter("no"));
	    	    
	    System.out.println(no);
	    	    
	    stmt.setInt(1,no);
	    
	    rs = stmt.executeQuery();
	    
	    response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			
			 out.println("<html><head>");
			 out.println("<style>tr{border:black solid 1px;}");
			 out.println("table{border:black solid 1px;}");
			 out.println("td{border:black solid 1px;}");
			 out.println("</style></head>");
			 out.println("<body><h1>과목상세정보</h1>");
			 if (rs.next()){
				 out.println("<table><tr><th>번호</th><td>" + rs.getInt("SNO")+"</td></tr><br>");
				 out.println("<tr><th>과목명</th>" + "<td>" + rs.getString("TITLE")+ "</td></tr><br>"); 
				 out.println("<tr><th>설명</th>"+ "<td><textarea rows=5 cols=30 name=contents>" +  
				             rs.getString("DEST")+"</textarea></td></tr><br>");
				}else{
					throw new Exception("해당 과목을 찾을 수 없습니다.");
				}
			 out.println("</table></body></html>");
    } catch (Throwable e) {
	    e.printStackTrace();
    }
	
	}
}
