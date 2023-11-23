package kr.or.ddit.board.dao;

import java.util.List;

import kr.or.ddit.vo.BoardVO;

public interface IBoardDao {
	
	/**
	 * 게시판 테이블에 추가할 데이터가 저장된 BoardVO객체를 매개변수로 받아서
	 * DB에 insert하는 메서드
	 * 
	 * @param boardVo insert할 데이터가 저장된 boardVo객체
	 * @return 작업 성공 : 1, 작업 실패 : 0
	 */
	public int insertBoard(BoardVO boardVo);
	
	/**
	 * BoardVO 자료를 이용하여 DB 자료를 update하는 메서드
	 * 
	 * @param boardVo update할 자료가 저장된 BoardVO객체
	 * @return 작업 성공 : 1, 작업 실패 : 0
	 */
	public int updateBoard(BoardVO boardVo);
	
	
	/**
	 * 게시글 번호를 매개변수로 받아서 해당 게시글을 삭제하는 메서드 
	 *
	 * @param boardNo 삭제할 게시글 번호
	 * @return 작업 성공 : 1, 작업 실패 : 0
	 */
	
	public int deleteBoard(int boardNo);
	
	/**
	 * jdbc_board테이블의 전체 데이터를 가져와서 List에 담아서 반환하는 메서드
	 * 
	 * @return BoardVO객체를 담고 있는 List객체
	 */
	public List<BoardVO> getAllBoard();
	
	/**
	 * 게시글의 제목을 매개변수로 받아서 해당 게시글을 검색하는 메서드
	 * 
	 * @param title 검색할 게시글 제목
	 * @return 검색한 결과가 저장된 List객체
	 */
	public List<BoardVO> getSearchBoard(String title);

	/**
	 * 개시글 번호를 매개변수로 받아서 해당 게시글 정보를 가져와 반환하는 메서드
	 * 
	 * @param boardNo 게시글 번호
	 * @return 게시글 번호에 맞는 자료가 있으면 해당 게시글 정보가 저장된 BoardVO객체,
	 * 			자료가 없으면 null반환
	 */
	public BoardVO getBoard(int boardNo);
	
	/**
	 * 게시글 번호를 매개변수로 받아서 해당 게시글의 조회수를 증가시키는 메서드
	 * 
	 * @param boardNo 조회수를 증가할 게시글 번호
	 * @return 작업 성공 : 1, 작업 실패 : 0
	 */
	public int setCountIncrement(int boardNo);
	


}