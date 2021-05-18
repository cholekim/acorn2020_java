package test.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import test.dto.BirthDto;
import test.util.DBConnect;

public class BirthDao {
	//2. 자신의 참조값을 저장할 static 필드 정의
	private static BirthDao dao;
	
	//1. 외부에서 객체 생성하지 못하도록
	private BirthDao() {}
	
	//3. 자신의 참조값을 리턴해주는 공개 static 메서드
	public static BirthDao getInstance() {
		//최초 호출될 때는 dao가 null이므로
		if(dao==null) {
			//객체를 생성해서 static 필드에 저장해 놓는다.
			dao=new BirthDao(); //클래스 안에서는 private 접근지정자로 된 생성자 호출 가능
		}
		return dao;
	}
	//생일 정보를 저장하는 메서드 (작업성공 여부는 boolean으로 리턴)
	public boolean insert(BirthDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int flag = 0;
		try {
			conn=new DBConnect().getConn();
			//실행할 sql(INSERT OR UPDATE OR DELETE)문 작성
			String sql="INSERT INTO birth"
					+ " (num, name, birthday)"
					+ " VALUES(Birth_seq.NEXTVAL, ?, ?)";
			pstmt=conn.prepareStatement(sql);
			//?에 바인딩할 내용이 있으면 여기서 한다.
			
			//1번째 ?에 BirthDto 객체에 있는 이름을 불러와서 바인딩
			pstmt.setString(1, dto.getName());
			//2번째 ?에 BirthDto 객체에 있는 생일을 불러와서 바인딩
			pstmt.setString(2, dto.getBirth());
			//PreparedStatement 객체의 메서드를 이용해서 sql문을 실행하고 수정된 row의 갯수를 리턴한다.
			//1개의 row가 추가되었으므로 1을 리턴하게 된다.
			flag=pstmt.executeUpdate();
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
			if (flag > 0) {
				return true;
			} else {
				return false;
			}
		}
	}
	//생일 정보를 수정하는 메서드
	public boolean update(BirthDto dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int flag = 0;
		try {
			conn = new DBConnect().getConn();
			//실행할 sql(INSERT OR UPDATE OR DELETE)문 작성
			String sql = "UPDATE birth"
					+ " SET name=?, birthday=?"
					+ " WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			//?에 바인딩할 내용이 있으면 여기서 한다.
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getBirth());
			pstmt.setInt(3, dto.getNum());
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
	//생일 정보를 삭제하는 메서드
	public boolean delete(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int flag = 0;
		try {
			conn = new DBConnect().getConn();
			//실행할 sql(INSERT OR UPDATE OR DELETE)문 작성
			String sql = "DELETE FROM birth"
					+ " WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			//?에 바인딩할 내용이 있으면 여기서 한다.
			pstmt.setInt(1, num);
			//삭제된 row의 갯수가 리턴된다.
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
	//한명의 생일 정보를 리턴하는 메서드
	public BirthDto getData(int num) {
		//BirthDto 객체의 참조값을 담을 지역변수 선언
		BirthDto dto=null;
		
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			conn=new DBConnect().getConn();
			//실행할 SELECT문
			String sql= "SELECT name, birthday"
					+ " FROM birth"
					+ " WHERE num=?";
			pstmt=conn.prepareStatement(sql);
			//?에 바인딩할 내용은 여기서 바인딩한다.
			pstmt.setInt(1, num);
			rs= pstmt.executeQuery();
			while (rs.next()) {
				//SELECT된 결과를 여기서 추출해서 객체에 담는다.
				String name=rs.getString("name");
				String birthday=rs.getString("birthday");
				//미리 만들어진 지역변수에 BirthDto객체의 참조값을 담는다.
				dto=new BirthDto(num, name, birthday);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs!=null)
					rs.close();
				if (pstmt!=null)
					pstmt.close();
				if (conn!=null)
					conn.close();
			} catch (Exception e) {
			}
		}
		//BirthDto 객체의 참조값을 리턴해준다.
		return dto;
	}
	//모든 생일정보를 리턴하는 메서드
	public List<BirthDto> getList() {
		//BirthDto 객체를 누적시킬 ArrayList 객체를 미리 생성한다.
		List<BirthDto> list=new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = new DBConnect().getConn();
			//실행할 SELECT문
			String sql = "SELECT num, name, TO_CHAR(birthday, 'YYYY.MM.DD') AS birthday"
					+ " FROM birth"
					+ " ORDER BY num ASC";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while (rs.next()) {
				//SELECT된 결과를 여기서 추출해서 객체에 담는다.
				int num=rs.getInt("num");
				String name=rs.getString("name");
				String birthday=rs.getString("birthday");
				//BirthDto 객체에 한명의 생일 정보를 담고
				BirthDto dto=new BirthDto(num, name, birthday);
				//List에 누적시킨다.
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
			}
		}
		return list;
	}
}
