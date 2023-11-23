package kr.or.ddit.board.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.ddit.util.DBUtil3;
import kr.or.ddit.vo.BoardVO;

public class BoardDaoImpl implements IBoardDao {
	// DB연동용 객체 변수 선언
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//------------------------------------------------------
	
	// 싱글톤
	// 1번 : 자기자신의 참조값이 저장될 클래스를 private static으로 만듬
	private static BoardDaoImpl dao;
	
	// 2번 : 빈 생성자?
	private BoardDaoImpl() {}
	
	// 3번 : 
	public static BoardDaoImpl getInstance() {
		if(dao == null) dao = new BoardDaoImpl();
		
		return dao;
	}
	
	// 자원 반납용 메서드 : 사용하기 위해서는 전역변수를 선언해야함
	private void disConnect() {
		if(rs!=null) try { rs.close(); } catch(SQLException e) {}
		if(pstmt!=null) try { pstmt.close(); } catch(SQLException e) {}
		if(conn!=null) try { conn.close(); } catch(SQLException e) {}
	}
	
	@Override
	public int insertBoard(BoardVO boardVo) {
		int cnt = 0;	// 반환값이 저장될 변수
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "insert into jdbc_board (board_no, board_title, board_writer, board_date, board_cnt, board_content) " + 
					"VALUES (board_seq.nextval, ?, ?,sysdate, 0, ? ) ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardVo.getBoard_title());
			pstmt.setString(2, boardVo.getBoard_writer());
			pstmt.setString(3, boardVo.getBoard_content());
			
			cnt = pstmt.executeUpdate();
			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disConnect();
			}
			
			return cnt;	// 반환값
		}

	@Override
	public int updateBoard(BoardVO boardVo) {
		int cnt = 0 ;
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "update jdbc_board set "
						+"board_title = ?, board_content = ? "
						+"where board_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, boardVo.getBoard_title());
			pstmt.setString(2, boardVo.getBoard_content());
			pstmt.setInt(3, boardVo.getBoard_no());
			
			cnt = pstmt.executeUpdate();
			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disConnect();
			}
			
			return cnt;	// 반환값
		}

	@Override
	public int deleteBoard(int boardNo) {
		int cnt = 0 ;
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "delete from jdbc_board where board_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			cnt = pstmt.executeUpdate();
			
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				disConnect();
			}
			
			return cnt;	// 반환값
		}

	@Override
	public List<BoardVO> getAllBoard() {
		List<BoardVO> boardList = null;		// 반환값이 저장될 변수 선언
		try {
			conn = DBUtil3.getConnection();
			
			String sql = "select board_no, board_title, board_writer,"
					+ "to_char(board_date, 'yyyy-mm-dd') board_date, board_cnt, board_content "
					+ "from jdbc_board ";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			boardList= new ArrayList<BoardVO>();
			
			while(rs.next()) {
				// 하나의 레코드를 VO객체에 저장한 후 저장 ~
				BoardVO boardVo = new BoardVO();
				boardVo.setBoard_no(rs.getInt("board_no"));
				boardVo.setBoard_title(rs.getString("board_title"));
				boardVo.setBoard_writer(rs.getString("board_writer"));
				boardVo.setBoard_date(rs.getString("board_date"));
				boardVo.setBoard_cnt(rs.getInt("board_cnt"));
				boardVo.setBoard_content(rs.getString("board_content"));
				
				boardList.add(boardVo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disConnect();
		}
		
		return boardList;
	}

	@Override
	public List<BoardVO> getSearchBoard(String title) {
		List<BoardVO> boardList = null;		// 반환값이 저장될 변수 선언
		try {
			conn = DBUtil3.getConnection();
			
			String sql = "select board_no, board_title, board_writer,"
					+ "to_char(board_date, 'yyyy-mm-dd') board_date, board_cnt, board_content "
					+ "from jdbc_board "
					+ "where board_title like '%' || ? || '%' "
					+ "order by board_no desc ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			
			rs = pstmt.executeQuery();
			
			boardList= new ArrayList<BoardVO>();
			
			while(rs.next()) {
				// 하나의 레코드를 VO객체에 저장한 후 저장 ~
				BoardVO boardVo = new BoardVO();
				boardVo.setBoard_no(rs.getInt("board_no"));
				boardVo.setBoard_title(rs.getString("board_title"));
				boardVo.setBoard_writer(rs.getString("board_writer"));
				boardVo.setBoard_date(rs.getString("board_date"));
				boardVo.setBoard_cnt(rs.getInt("board_cnt"));
				boardVo.setBoard_content(rs.getString("board_content"));
				
				boardList.add(boardVo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			disConnect();
		}
		
		return boardList;
	}

	@Override
	public BoardVO getBoard(int boardNo) {
		BoardVO boardVo = null;
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "select board_no, board_title, board_writer,"
					+ "to_char(board_date, 'yyyy-mm-dd') board_date, board_cnt, board_content "
					+ "from jdbc_board "
					+ "where board_no = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				boardVo = new BoardVO();

				boardVo.setBoard_no(rs.getInt("board_no"));
				boardVo.setBoard_title(rs.getString("board_title"));
				boardVo.setBoard_writer(rs.getString("board_writer"));
				boardVo.setBoard_date(rs.getString("board_date"));
				boardVo.setBoard_cnt(rs.getInt("board_cnt"));
				boardVo.setBoard_content(rs.getString("board_content"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			disConnect();
		}
		
		return boardVo;
	}

	@Override
	public int setCountIncrement(int boardNo) {
		int cnt = 0;
		
		try {
			conn = DBUtil3.getConnection();
			String sql = "update jdbc_board set board_cnt = board_cnt +1 "
					+"where board_no = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			
			cnt = pstmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			disConnect();
		}
		
		return cnt;
	}

}
