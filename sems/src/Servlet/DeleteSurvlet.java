package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/subject/delete.bit")
@SuppressWarnings("serial")
public class DeleteSurvlet extends HttpServlet{
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

		try{
			dao.delete(Integer.parseInt(no));
			PrintWriter out = response.getWriter();
			out.println("<html><head><title> Delete </title></head>");
			out.println("<body><h1>삭제성공</h1></body></html>");
		}catch(Throwable e){
			PrintWriter out = response.getWriter();
			out.println("<html><head><title> Delete </title></head>");
			out.println("<body><h1>삭제실패</h1></body></html>");
		}
	}
}
