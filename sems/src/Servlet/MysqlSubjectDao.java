package Servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//SubjectVo의 setter/getter사용
public class MysqlSubjectDao implements SubjectDao {
	DBConnectionPool dbConnectionPool;
	
	// 외부에서 DBConnectionPool 객체를 주입할 수 있도록 함
	public void setDBConnectionPool(DBConnectionPool dbConnectionPool){
		this.dbConnectionPool = dbConnectionPool;
	}
	
	public void insert(SubjectVo subject) throws Throwable {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = dbConnectionPool.getConnection();
			stmt = con.prepareStatement(
					"insert SE_SUBJS(TITLE, DEST) values(?, ?)");
			stmt.setString(1, subject.getTitle());
			stmt.setString(2, subject.getDescription());
			int result = stmt.executeUpdate();
			if(result ==0){
			  throw new Exception("System.error");
			}	
		} catch (Throwable e) {
			throw e;
		} finally { 
			try {stmt.close();} catch (Throwable e2) {}
			dbConnectionPool.returnConnection(con);
		}
	}
	
	public List<SubjectVo> list(int pageNo, int pageSize) throws Throwable {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = dbConnectionPool.getConnection();
			stmt = con.prepareStatement(
					"select SNO, TITLE, DEST from SE_SUBJS" + " order by SNO desc" 
					+ " limit ?, ?");
			stmt.setInt(1, (pageNo - 1) * pageSize);
			stmt.setInt(2, pageSize);
			rs = stmt.executeQuery();
			
			ArrayList<SubjectVo> list = new ArrayList<SubjectVo>();
			
			while(rs.next()) {
				list.add(new SubjectVo()
				.setDescription(rs.getString("SNO"))
				.setTitle(rs.getString("TITLE")));
			}
			return list;
		} catch (Throwable e) {
			throw e;
		} finally { 
			try {rs.close();} catch (Throwable e2) {}
			try {stmt.close();} catch (Throwable e2) {}
			dbConnectionPool.returnConnection(con);
		}
	}
	
	public SubjectVo detail(int no) throws Throwable {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			con = dbConnectionPool.getConnection();
			stmt = con.prepareStatement(
					"select SNO, TITLE, DEST from SE_SUBJS" + " where SNO=?");
			stmt.setInt(1, no);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				return  new SubjectVo()
				.setNo(rs.getInt("SNO"))
			  .setTitle(rs.getString("TITLE"))
				.setDescription(rs.getString("DEST"));
			} else {
				throw new Exception("해당 과목을 찾을 수 없습니다.");
			}
		} catch (Throwable e) {
			throw e;
		} finally { 
			try {rs.close();} catch (Throwable e2) {}
			try {stmt.close();} catch (Throwable e2) {}
			dbConnectionPool.returnConnection(con);
		}
	}
	
	public void update(SubjectVo subject) throws Throwable {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = dbConnectionPool.getConnection();
			stmt = con.prepareStatement(
					"update SE_SUBJS set" + " TITLE=?" + ", DEST=?" + " where SNO=?");
			stmt.setString(1, subject.getTitle());
			stmt.setString(2, subject.getDescription());
			stmt.setInt(3, subject.getNo());
			int result = stmt.executeUpdate();
			if(result ==0){
			  throw new Exception("System.error");
			}	
		} catch (Throwable e) {
			throw e;
		} finally { 
			try {stmt.close();} catch (Throwable e2) {}
			dbConnectionPool.returnConnection(con);
		}
	}
	
	public void delete(int no) throws Throwable {
		Connection con = null;
		PreparedStatement stmt = null;
		try {
			con = dbConnectionPool.getConnection();
			stmt = con.prepareStatement(
					"delete from SE_SUBJS where SNO=?"	);
			stmt.setInt(1, no);
			stmt.executeUpdate();
		} catch (Throwable e) {
			throw e;
		} finally { 
			try {stmt.close();} catch (Throwable e2) {}
			dbConnectionPool.returnConnection(con);
		}
	}
	
}






