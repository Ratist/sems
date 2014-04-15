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

@WebServlet("/subject/update.bit")
@SuppressWarnings("serial")

public class UpdateServlet extends HttpServlet{
	PreparedStatement stmt = null;
	SubjectDao dao;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		DBConnectionPool dbConnectionPool = new DBConnectionPool();
		dao = new MysqlSubjectDao();
		((MysqlSubjectDao)dao).setDBConnectionPool(dbConnectionPool);

		response.setContentType("text/html;charset=UTF-8");
	
		String no = request.getParameter("no");
		String title = request.getParameter("title");
		String description = request.getParameter("dest");
		request.setCharacterEncoding("UTF-8");

		try{
			dao.update(new SubjectVo().setNo(Integer.parseInt(no))
					           .setTitle(title).setDescription(description));
			PrintWriter out = response.getWriter();
			out.println("<html><head><title> Insert </title></head>");
			out.println("<body><h1>변경성공</h1></body></html>");
		}catch(Throwable e){
			PrintWriter out = response.getWriter();
			out.println("<html><head><title> Insert </title></head>");
			out.println("<body><h1>변경실패</h1></body></html>");
		}
	}
}


