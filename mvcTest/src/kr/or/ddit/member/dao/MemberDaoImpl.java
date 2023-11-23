package kr.or.ddit.member.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kr.or.ddit.util.DBUtil3;
import kr.or.ddit.vo.MemberVO;

public class MemberDaoImpl implements IMemberDao {
	// 1번
	private static MemberDaoImpl dao;
	
	// 2번
	private MemberDaoImpl() {}
	
	// 3번
	public static MemberDaoImpl getInstance(){
		if(dao ==null) dao = new MemberDaoImpl();
		
		return dao;
	}
	@Override
	public int insertMember(MemberVO memVo) {
		Connection conn =null;
		PreparedStatement pstmt =null;
		int cnt = 0;	// 반환값이 저장될 변수
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "insert into mymember(mem_id, mem_pass, mem_name, mem_tel, mem_addr) "
					+ " values( ?, ?, ?, ?, ? )";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memVo.getMem_id());
			pstmt.setString(2, memVo.getMem_pass());
			pstmt.setString(3, memVo.getMem_name());
			pstmt.setString(4, memVo.getMem_tel());
			pstmt.setString(5, memVo.getMem_addr());
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) { }
			if(conn!=null) try { conn.close(); }catch(SQLException e) { }
		}
		
		return cnt;
	}

	@Override
	public int deleteMember(String memId) {
		Connection conn =null;
		PreparedStatement pstmt =null;
		int cnt = 0;	// 반환값이 저장될 변수
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "delete from mymember where mem_id = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			
			cnt = pstmt.executeUpdate();
						
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
		
		return cnt;
	}

	@Override
	public int updateMember(MemberVO memVo) {
		
		// 전역변수로 해도 되지만 지역변수로 하는 이유
		// finally에서 null 처리를 하니까..?
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;	// 반환값이 저장될 변수
		
		try {
			conn = DBUtil3.getConnection();
			
			String sql = "update mymember set mem_pass = ?, mem_name = ?,  "
					+ "	mem_tel = ? , mem_addr = ? "
					+ " where mem_id = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memVo.getMem_id());
			pstmt.setString(2, memVo.getMem_pass());
			pstmt.setString(3, memVo.getMem_name());
			pstmt.setString(4, memVo.getMem_tel());
			pstmt.setString(5, memVo.getMem_addr());
			
			cnt = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
	
		return cnt;
	}

	@Override
	public List<MemberVO> getAllMember() {
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		List<MemberVO> memList =null;	// 반환값이 저장될 변수
		
		try {
			conn =DBUtil3.getConnection();
			String sql = "select * from mymember ";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			memList =new ArrayList<MemberVO>();	// List객체 생성
			while(rs.next()) {
				// 1개의 레코드가 저장될 변수 (Member VO객체 변수) 생성
				MemberVO memvo = new MemberVO();
				memvo.setMem_id(rs.getString("mem_id"));
				memvo.setMem_pass(rs.getString("mem_pass"));
				memvo.setMem_name(rs.getString("mem_name"));
				memvo.setMem_tel(rs.getString("mem_tel"));
				memvo.setMem_addr(rs.getString("mem_addr"));
				
				memList.add(memvo);		// List에 VO객체 추가하기
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}
			
		return memList;
	}

	@Override
	public int getMemberCount(String memId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int count = 0;	// 반환값이 저장될 변수

		try {
			conn = DBUtil3.getConnection();

			String sql = "select count(*) cnt from mymember where mem_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);

			rs = pstmt.executeQuery();

			if(rs.next()) {
				count = rs.getInt("cnt");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) try { rs.close(); }catch(SQLException e) {}
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}

		return count;
	}

	@Override
	public int updateMember2(Map<String, String> paramMap) {
		// key값 정보 ==> 회원ID (MEMID), 수정할 컬럼명(FIELD), 수정할 데이터(DATA)
		Connection conn = null;
		PreparedStatement pstmt = null;
		int cnt = 0;
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "update mymember set " + paramMap.get("FIELD") + " = ? "
					+ " where mem_id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, paramMap.get("DATA"));
			pstmt.setString(2, paramMap.get("MEMID"));
			
			cnt =pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) try { pstmt.close(); }catch(SQLException e) {}
			if(conn!=null) try { conn.close(); }catch(SQLException e) {}
		}

		return cnt;
	}

}


