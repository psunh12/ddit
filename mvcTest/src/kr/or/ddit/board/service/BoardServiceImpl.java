package kr.or.ddit.board.service;

import java.util.List;

import kr.or.ddit.board.dao.BoardDaoImpl;
import kr.or.ddit.board.dao.IBoardDao;
import kr.or.ddit.vo.BoardVO;

public class BoardServiceImpl implements IBoardService {
	
	private IBoardDao dao;		// DAO객체가 저장될 변수 선언
	
	// 싱글톤~
	// 1번
	private static BoardServiceImpl service;

	// 2번
	private BoardServiceImpl() {
		dao = BoardDaoImpl.getInstance();	// 싱글톤으로 dao객체 생성
	}
	
	// 3번
	public static BoardServiceImpl getInstance() {
		if(service == null) service = new BoardServiceImpl();
		return service;
	}
	
	@Override
	public int insertBoard(BoardVO boardVo) {
		return dao.insertBoard(boardVo);
	}

	@Override
	public int updateBoard(BoardVO boardVo) {
		return dao.updateBoard(boardVo);
	}

	@Override
	public int deleteBoard(int boardNo) {
		return dao.deleteBoard(boardNo);
	}

	@Override
	public List<BoardVO> getAllBoard() {
		return dao.getAllBoard();
	}

	@Override
	public List<BoardVO> getSearchBoard(String title) {
		return dao.getSearchBoard(title);
	}

	@Override
	public BoardVO getBoard(int boardNo) {
		// 게시글 내용을 가져올 때 조회수를 증가하는 작업이 선행되어야 하기 때문에
		// 이 작업을 service의 메서드에서 함꼐 처리한다.
		int cnt = dao.setCountIncrement(boardNo);
		
		if(cnt == 0 ) return null;
		
		return dao.getBoard(boardNo);
	}

	@Override
	public int setCountIncrement(int boardNo) {
		return dao.setCountIncrement(boardNo);
	}

}
