package com.jjangplay.image.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jjangplay.board.service.BoardDeleteService;
import com.jjangplay.board.service.BoardListService;
import com.jjangplay.board.service.BoardUpdateService;
import com.jjangplay.board.service.BoardViewService;
import com.jjangplay.board.service.BoardWriteService;
import com.jjangplay.board.vo.BoardVO;
import com.jjangplay.image.vo.ImageVO;
import com.jjangplay.main.controller.Init;
import com.jjangplay.member.vo.LoginVO;
import com.jjangplay.util.exe.Execute;
import com.jjangplay.util.io.BoardPrint;
import com.jjangplay.util.io.In;
import com.jjangplay.util.page.PageObject;
import com.jjangplay.util.page.ReplyPageObject;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

// Board(일반게시판) 의 메뉴를 선택하고, 데이터 수집(기능별), 예외처리
public class ImageController {

	@SuppressWarnings("unchecked")
	public String execute(HttpServletRequest request) {
		System.out.println("ImageController");

	
			// 메뉴입력(uri)
			String uri = request.getRequestURI();
			System.out.println("uri="+uri);
			
			// 데이터 수집을 위한 객체선언
			// 초기값 null 을 주어서 데이터를 받았는지 체크하고 처리한다.
			Object result = null;
			
			Long no = 0L;
			
			// 이동할 jsp 주소를 담아놀 변수
			String jsp = null;
			
			HttpSession session = request.getSession();
			LoginVO loginVO = (LoginVO)session.getAttribute("login");
			
			String id = null;
			if(loginVO!=null) id=loginVO.getId();
			
			String savePath = "/upload/image";
			String realSavePath = request.getServletContext().getRealPath(savePath);
			int sizeLimit = 100*1024*1024;
			
			try {
			
				switch (uri) {
				case "/image/list.do":
					System.out.println("1. 이미지게시판 리스트");
					System.out.println("----주소창에 localhost/image/list.do 했을때 (Start)----");
					
					// 페이지 처리를 위한 객체, 넘어오는 페이지와 검색정보를 세팅
					PageObject pageObject = PageObject.getInstance(request);
					String strPerPageNum = request.getParameter("perPageNum");
					if(strPerPageNum==null) pageObject.setPerPageNum(6);
					
					// Init.get(uri) : /image/list.do 키값을 가지고 service 주소를 가져온다.
					result = Execute.execute(Init.get(uri), pageObject);
					System.out.println("ImageController.execute().pageObject = "+pageObject);
					// 가져온 데이터를 request에 담는다.
					request.setAttribute("list", result);
					request.setAttribute("pageObject", pageObject);
					
					System.out.println("----주소창에 localhost/image/list.do 했을때 (End)----");
					// "/WEB-INF/views/" + board/list +".jsp"
					jsp = "image/list";
					break;
				case "/image/view.do":
					System.out.println("2. 일반게시판 글보기");
					// 조회수증가, 상세글보기
					//[BoardController] -> (Execute) ->
					// BoardViewService -> BoardDAO.increase()
					// BoardViewService -> BoardDAO.view()
					no = Long.parseLong(request.getParameter("no"));
					Long inc = Long.parseLong(request.getParameter("inc"));
					
					// 전달데이터는 글번호, 증가를 위한 값 1 (1:증가, 0:증가안함)
					result = Execute.execute(Init.get(uri),new Long[]{no, inc});
					request.setAttribute("vo", result);
					
					//댓글 페이지객체 데이터전달
					ReplyPageObject replyPageObject = ReplyPageObject.getInstance(request);
					request.setAttribute("replyList", 
					Execute.execute(Init.get("/imagereply/list.do"), replyPageObject));
					
					
					jsp="board/view";
					break;
				case "/image/writeForm.do":
					System.out.println("3. 이미지게시판 글쓰기폼");
					jsp="image/writeForm";
					break;
				case "/image/write.do":
					System.out.println("3. 이미지게시판 업데이트");
					
					MultipartRequest multi = new MultipartRequest(request, realSavePath,sizeLimit,
							"utf-8",new DefaultFileRenamePolicy());
					
					// 데이터 수집(키보드) : 제목, 내용, 작성자, 비밀번호
					String title = multi.getParameter("title");
					String content = multi.getParameter("content");
					String fileName = multi.getParameter("imageFile");
					
					
					// 입력받은 데이터를 BoardVO 안에 저장(세팅) => DB에 넘겨주기위한
					ImageVO vo = new ImageVO();
					vo.setTitle(title);
					vo.setContent(content);
					vo.setFileName(savePath+"/"+fileName);
					vo.setId(id);
					
					//[BoardController] -> (Execute) ->
					// BoardWriteService -> BoardDAO.write()
					Execute.execute(Init.get(uri), vo);
					//jsp정보앞에 "redirect:"가 붙어있으면 redirect로 처리, 없으면 forword
					jsp="redirect:list.do";
					break;
				case "/image/updateForm.do":
					System.out.println("4. 글수정폼");
					no=Long.parseLong(request.getParameter("no"));
					inc=0L;
					result = Execute.execute(Init.get("/image/view.do"),new Long[]{no, inc});
					request.setAttribute("vo", result);
					jsp="board/updateForm";
					break;
					
				case "/image/update.do":
					System.out.println("4. 일반게시판 글수정");
					
					// updateForm에서 적은 데이터가져옴
					no = Long.parseLong(request.getParameter("no"));
					title=request.getParameter("title");
					content=request.getParameter("content");
					//writer=request.getParameter("writer");
					//pw=request.getParameter("pw");
					
					vo=new ImageVO();
					vo.setNo(no);
					vo.setTitle(title);
					vo.setContent(content);
					//vo.setWriter(writer);
					//vo.setPw(pw);
					
					Execute.execute(Init.get(uri), vo);
					jsp="redirect:view.do?no="+no+"&inc=0";
					
					break;
				case "/image/delete.do":
					System.out.println("5. 일반게시판 글삭제");
					// 데이터 수집 : 삭제할 글번호, 확인용 비밀번호
					vo = new ImageVO();
					
					vo.setNo(Long.parseLong(request.getParameter("no")));
					//vo.setPw(request.getParameter("pw"));
					
					// DB처리
					result =Execute.execute(Init.get(uri), vo);
					
					// 결과표시
					if ((Integer) result == 1) {
						System.out.println();
						System.out.println("***********************");
						System.out.println("** " + vo.getNo() + "번 글이 삭제되었습니다.");
						System.out.println("***********************");
					}
					else {
						System.out.println();
						System.out.println("########################");
						System.out.println("## " + vo.getNo() + "번 글이 삭제되지 않았습니다.");
						System.out.println("########################");
					}
					jsp="redirect:list.do";
					break;

				default:
					System.out.println("잘못된 메뉴를 선택하셨습니다.===");
					System.out.println("[0~5] 번호를 선택해야 합니다.===");
				} // end of switch
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
				System.out.println("$%@    <오류 출력> ");
				System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
				// getSimpleName() : 클래스 이름만 보여주는 메서드(패키지는 안보여준다)
				System.out.println("$%@ 타입 : " + e.getClass().getSimpleName());
				// getMessage() : 예외의 내용을 보여주는 메서드
				System.out.println("$%@ 내용 : " + e.getMessage() );
				System.out.println("$%@ 조치 : 데이터 확인해 보세요");
				System.out.println("$%@       계속 오류가 나면 전산담당자에게 문의하세요.");
				System.out.println("$%@$%@$%@$%@$%@$%@$%@$%@$%@$%@");
			}
		
			return jsp;
	} // end of execute
} // end of class
