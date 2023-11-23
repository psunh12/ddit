package kr.or.ddit.board.controller;

import java.util.List;
import java.util.Scanner;

import kr.or.ddit.board.service.BoardServiceImpl;
import kr.or.ddit.board.service.IBoardService;
import kr.or.ddit.vo.BoardVO;

public class BoardController {

	private IBoardService service;		// Service객체가 저장될 변수
	private Scanner scan;
	
	// 생성자
	public BoardController() {
		service = BoardServiceImpl.getInstance();	// Service객체 생성(싱글톤)
		scan = new Scanner(System.in);
	}
	
	public static void main(String[] args) {
		new BoardController().startBoard();
	}
	
	// 시작 메서드
	public void startBoard() {
		String title = null;
		
		while(true) {
			int choice = displayMenu(title);
			title =null;
			
			switch (choice) {
			case 1:	insertBoard(); break;	// 1.새글 작성
			case 2:	viewBoard(); break;	// 2.게시글 보기
			case 3:	title=searchBoard(); break;	// 3.검색
			case 0:			// 0.종료
				System.out.println("게시판 프로그램 종료...");
				return;
			default:
				System.out.println("작업 번호를 잘못 입력했습니다.");
				System.out.println("다시 입력하세요...");
			}
		}
	}
	
	// 1. 새글 작성
	private void insertBoard() {
		System.out.println();
		scan.nextLine();	// 입력버퍼 비우기
		System.out.println(" 새 글 작성하기");
		System.out.println("-----------------------------------");
		System.out.print("- 제  목  : ");
		String title = scan.nextLine();
		
		System.out.print("- 작성자 : ");
		String writer = scan.next();
		
		scan.nextLine();
		System.out.print("- 내  용  : ");
		String content = scan.nextLine();
		
		// 입력 받은 정보를 VO객체에 저장하기
		BoardVO boardVo= new BoardVO();
		
		boardVo.setBoard_title(title);
		boardVo.setBoard_writer(writer);
		boardVo.setBoard_content(content);
		int cnt =service.insertBoard(boardVo);
		
		if(cnt>0) {
			System.out.println(title+" 게시글 추가 성공!!!");
		}else {
			System.out.println(title+" 게시글 추가 실패 ~~~");
		}
	}
	
	// 게시글 내용을 보여주는 메서드
	private void viewBoard() {
		System.out.println();
		System.out.print("보기를 원하는 게시물  번호 입력 >> ");
		int num = scan.nextInt();
		
		// 입력한 게시글 번호에 맞는 게시글 정보 가져오기
		BoardVO boardVo = service.getBoard(num);
		
		if(boardVo==null) {
			System.out.println(num+"번의 게시글이 존재하지 않습니다...");
			return;
		}
		System.out.println();
		System.out.println(boardVo.getBoard_no()+"번 글 내용");
		System.out.println("------------------------------------");
		System.out.println("- 제  목  : "+boardVo.getBoard_title());
		System.out.println("- 작성자 : "+boardVo.getBoard_writer());
		System.out.println("- 내  용  : "+boardVo.getBoard_content());
		System.out.println("- 작성일 : "+boardVo.getBoard_date());
		System.out.println("- 조회수 : "+boardVo.getBoard_cnt());
		System.out.println("------------------------------------");
		System.out.println("메뉴 : 1. 수정    2. 삭제    3. 리스트로 가기");
		
		int choice= scan.nextInt();
		
		switch(choice) {
		case 1 : updateBoard(num); break; 	// 수정
		case 2 : deleteBoard(num); break; 	// 삭제
		case 3 : return; 	// 리스트로 가기
		}
	}
	
	private void deleteBoard(int num) {
		int cnt =service.deleteBoard(num);
		
		if(cnt>0){
			System.out.println(num+"번 글이 삭제 되었습니다...");
		}else {
			System.out.println(num+"번 글이 삭제 되지 않았습니다...");
		}
	}

	private void updateBoard(int num) {
		System.out.println();
		scan.nextLine();		// 버퍼 비우기
		
		System.out.println(" 수정 작업하기");
		System.out.println("------------------------------------");
		System.out.print("- 제  목  : ");
		String title= scan.nextLine();

		System.out.print("- 내  용  : ");
		String content= scan.nextLine();

		// 입력한 데이터 VO에 저장하기
		BoardVO boardVo = new BoardVO();
		boardVo.setBoard_no(num);
		boardVo.setBoard_title(title);
		boardVo.setBoard_content(content);
		
		int cnt= service.updateBoard(boardVo);
		
		if(cnt>0) {
			System.out.println(num+"번 글이 수정되었습니다~!");
		} else {
			System.out.println(num+"번 글이 수정되지 않았습니다~!~");
		}
	}
	
	// 게시글의 검색 단어를 입력받아 반환하는 메서드
	private String searchBoard() {
		System.out.println();
		scan.nextLine();	// 버퍼 비우기
		
		System.out.println(" 검색 작업");
		System.out.println("-------------------------------------------------------------");
		System.out.print(" 검색할 제목 입력 >> ");
		return scan.nextLine();
	}

	// 게시판 목록을 보여주고 메뉴를 나타내며 사용자가 입력한 작업번호를 반환하는 메서드
	public int displayMenu(String title) {
		List<BoardVO> boardList;
		if(title==null) {
			// 전체 데이터 가져오기
			boardList = service.getAllBoard();
		} else {
			boardList = service.getSearchBoard(title);
		}
		// 전체 데이터 가져오기
		
		System.out.println();
		System.out.println("-------------------------------------------------------------");
		System.out.println(" No	        제 목            작성자 	        조회수   ");
		System.out.println("-------------------------------------------------------------");
		if(boardList == null || boardList.size() ==0) {
			System.out.println("\t출력할 게시글이 하나도 없습니다...");
		}else {
			for (BoardVO boardVo : boardList) {
				System.out.println(boardVo.getBoard_no()+"\t"+
						boardVo.getBoard_title()+"\t"+
						boardVo.getBoard_writer()+"\t"+
						boardVo.getBoard_cnt());
			}
		}
		
		// 게시판 출력
		System.out.println("-------------------------------------------------------------");
		System.out.println("메뉴 : 1. 새글작성     2. 게시글보기    3. 검색    0. 작업끝");
		System.out.print("작업 선택 >> ");
		
		return scan.nextInt();
	}

}
