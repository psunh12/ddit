package kr.or.ddit.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import kr.or.ddit.member.service.IMemberService;
import kr.or.ddit.member.service.MemberServiceImpl;
import kr.or.ddit.vo.MemberVO;

public class MemberController {

	private Scanner scan;
	private IMemberService service;		// Service객체가 저장될 변수 선언
	
	// 생성자
	public MemberController() {
		scan = new Scanner(System.in);
//		service = new MemberServiceImpl() ;			// Service객체 생성
		service = MemberServiceImpl.getInstance();	// Service객체 생성(싱글톤)
	}
	
	public static void main(String[] args) {
		new MemberController().memberStart();
	}
	// 시작 메서드
	public void memberStart() {
		while(true) {
			int choice = displayMenu();
			
			switch(choice) {
				case 1 : 	// 추가
					insertMember(); break;
				case 2 :	// 삭제
					deleteMember(); break;
				case 3 :	// 수정
					updateMember(); break;
				case 4 :	// 전체 출력
					displayMember(); break;
				case 5 :	// 수정2
					updateMember2(); break;
				case 0 :	// 종료
					System.out.println("작업을 마칩니다...");
					return;
				default : 
					System.out.println("작업 번호를 잘못 입력했습니다.");
					System.out.println("다시 입력하세요...");
			}
			
		}
		
	} // 시작 메서드 끝...
	
	// insert메서드
	private void insertMember() {
		System.out.println();
		System.out.println("추가할 회원 정보를 입력하세요...");
		
		int count = 0;
		String memId = null; 	// 입력 받은 회원ID가 저장될 변수
		do {
			System.out.print("회원ID 입력 >> ");
			memId = scan.next();
			
			count = service.getMemberCount(memId);
			
			if(count>0) {
				System.out.println(memId + "은(는) 이미 등록된 회원ID 입니다.");
				System.out.println("다른 회원ID를 입력 하세요...");
			}
			
		}while(count>0);
		
		System.out.print("비밀번호 입력 >> ");
		String memPass = scan.next();
		
		System.out.print("회원이름 입력 >> ");
		String memName = scan.next();
		
		System.out.print("전화번호 입력 >> ");
		String memTel = scan.next();
		
		scan.nextLine(); // 입력 버퍼 비우기
		System.out.print("회원주소 입력 >> ");
		String memAddr = scan.nextLine();
		
		// 입력이 완료되면 입력한 데이터를 VO객체에 저장한다.
		MemberVO memVo =new MemberVO();
		memVo.setMem_id(memId);
		memVo.setMem_pass(memPass);
		memVo.setMem_name(memName);
		memVo.setMem_tel(memTel);
		memVo.setMem_addr(memAddr);
		
		int cnt =service.insertMember(memVo);
		
		if(cnt>0) {
			System.out.println(memId+" 회원 정보 추가 성공!!!");
		}else {
			System.out.println(memId+" 회원 정보 추가 실패 ~~~");
		}
	}

	// delete 메서드
	private void deleteMember() {
		
		System.out.println();
		System.out.println("삭제할 회원 정보를 입력하세요...");
		
		System.out.print("삭제할 회원ID 입력 >> ");
		String memId = scan.next();
				
		int cnt =service.deleteMember(memId);
		
		if(cnt>0) {
			System.out.println(memId + " 회원 정보 삭제 성공!!!");
		}else {
			System.out.println(memId + " 회원 정보 삭제 실패~~~");
		}
	}
	
	// update 메서드
	private void updateMember() {
		System.out.println();
		System.out.println("수정할 회원 정보를 입력하세요...");
		System.out.println("수정할 회원ID 입력 >> ");
		String memId = scan.next();
		
		int count = service.getMemberCount(memId);
		if(count==0) {
			System.out.println(memId + "는(은) 없는 회원ID 입니다...");
			System.out.println("수정 작업을 종료합니다...");
			return;
		}
		
		System.out.println();
		System.out.println("수정할 내용을 입력하세요...");
		System.out.print("새로운 비밀번호 입력 >> ");
		String newPass = scan.next();
		
		System.out.print("새로운 회원이름 입력 >> ");
		String newName = scan.next();
		
		System.out.print("새로운 전화번호 입력 >> ");
		String newTel = scan.next();
		
		scan.nextLine();  // 입력 버퍼 비우기
		System.out.print("새로운 회원주소 입력 >> ");
		String newAddr = scan.nextLine();
		
		// 입력한 자료를 VO객체에 저장한다.
		MemberVO memVo = new MemberVO();
		memVo.setMem_id(memId);
		memVo.setMem_pass(newPass);
		memVo.setMem_name(newName);
		memVo.setMem_tel(newTel);
		memVo.setMem_addr(newAddr);
		
		int cnt = service.updateMember(memVo);
		
		if(cnt>0) {
			System.out.println(memId + " 회원 정보 수정 성공!!!");
		}else {
			System.out.println(memId + " 회원 정보 수정 실패~~~");
		}
		
	}
	
	// 전체 회원 출력
	private void displayMember() {
		System.out.println();
		
		// 전체 회원 정보 가져오기
		List<MemberVO> memList = service.getAllMember();
		
		System.out.println("-------------------------------------------------------------");
		System.out.println(" ID   비밀번호    이름      전화번호          주소");
		System.out.println("-------------------------------------------------------------");
		if(memList==null || memList.size()==0) {
			System.out.println("\t등록된 회원 정보가 하나도 없습니다...");
		}else {
			for(MemberVO memVo : memList) {
				String memId = memVo.getMem_id();
				String memPass = memVo.getMem_pass();
				String memName = memVo.getMem_name();
				String memTel = memVo.getMem_tel();
				String memAddr = memVo.getMem_addr();
				
				System.out.println(memId + "\t" + memPass + "\t" + 
							memName + "\t" + memTel + "\t" + memAddr );
			}
		}
		System.out.println("-------------------------------------------------------------");
		
		
	}
	
	// update메서드2 ==> 원하는 컬럼 변경하기
	private void updateMember2() {
		System.out.println();
		System.out.println("수정할 회원 정보를 입력하세요...");
		System.out.print("수정할 회원ID 입력 >> ");
		String memId = scan.next();
		
		int count = service.getMemberCount(memId);
		if(count==0) {
			System.out.println(memId + "는(은) 없는 회원ID 입니다...");
			System.out.println("수정 작업을 종료합니다...");
			return;
		}	
		
		int num ;  // 수정할 항목 중 선택한 항목의 번호가 저장될 변수
		String updateField = null;		// 수정할 컬럼명이 저장될 변수
		String updateTitle = null;		// 
		
		do {
			System.out.println();
			System.out.println("수정할 항목을 선택하세요...");
			System.out.println("1.비밀번호   2.회원이름   3.전화번호   4.회원주소");
			System.out.println("------------------------------------------------");
			System.out.print("수정할 항목 선택 >> ");
			num = scan.nextInt();
			if(num<1 || num>4) {
				System.out.println("수정할 항목을 잘못 선택했습니다. 다시 선택하세요...");
			}
		}while(num<1 || num>4);
		
		switch(num) {
			case 1 : updateField = "mem_pass"; updateTitle = "비밀번호"; break;
			case 2 : updateField = "mem_name"; updateTitle = "회원이름"; break;
			case 3 : updateField = "mem_tel"; updateTitle = "전화번호"; break;
			case 4 : updateField = "mem_addr"; updateTitle = "회원주소"; break;
		}
		
		scan.nextLine(); // 입력 버퍼 비우기
		System.out.println();
		System.out.print("새로운 " + updateTitle + " 입력 >> ");
		String updateData = scan.nextLine();
		
		// 수정할 정보를 Map에 저장한다.
		// key값 정보 ==> 회원ID (MEMID), 수정할 컬럼명 (FIELD), 수정할 데이터(DATA)
		Map<String, String> paramMap = new HashMap<String, String>();
		
		paramMap.put("MEMID", memId);
		paramMap.put("FIELD", updateField);
		paramMap.put("DATA", updateData);
		
		int cnt = service.updateMember2(paramMap);
		
		if(cnt>0) {
			System.out.println(memId + "회원 정보 수정 작업 완료!!!");
		}else {
			System.out.println(memId + "회원 정보 수정 작업 실패~~~");
		}	
		
	}	
	// 메뉴를 출력하고 입력 받은 작업 번호를 반환하는 메서드
	private int displayMenu() {
		System.out.println();
		System.out.println("------------------");
		System.out.println("1. 자료 추가	");
		System.out.println("2. 자료 삭제	");
		System.out.println("3. 자료 수정");
		System.out.println("4. 전체 자료 출력");
		System.out.println("5. 자료 수정2");		// 원하는 항목 1개만 수정하기
		System.out.println("0. 작업 끝...");
		System.out.println("------------------");
		System.out.print("원하는 작업 선택 >> ");
		return scan.nextInt();
	}
}
