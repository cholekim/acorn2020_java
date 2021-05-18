package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import test.dto.FoodDto;
import test.util.DBConnect;

public class FoodDao {
	private static FoodDao dao;
	
	private FoodDao() {}
	public static FoodDao getInstance() {
		if(dao==null) {
			dao=new FoodDao();
		}
		return dao;
	}
	public boolean insert(FoodDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int flag = 0;
		try {
			conn = new DBConnect().getConn();
			//실행할 sql(INSERT OR UPDATE OR DELETE)문 작성
			String sql = "INSERT INTO food"
					+ " (num, name, country)"
					+ " VALUES(Food_seq.NEXTVAL, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			// ?에 바인딩할 내용이 있으면 여기서 한다.
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getCountry());
			flag = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		if (flag > 0) {
			return true;
		} else {
			return false;
		}
	}
}
