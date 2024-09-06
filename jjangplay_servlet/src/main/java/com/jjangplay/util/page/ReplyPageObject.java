package com.jjangplay.util.page;

import javax.servlet.http.HttpServletRequest;

public class ReplyPageObject {
	
	//게시판 페이지리스트 처리를 위한 데이터변수선언
	PageObject pageObject = null;
	private long no;
	PageObject replyPageObject = null;
	
	public static ReplyPageObject getInstance(HttpServletRequest request) throws Exception {
		ReplyPageObject replyPageObject = new ReplyPageObject();
		System.out.println("ReplyPageObject.getInstance()");
		replyPageObject.pageObject = PageObject.getInstance(request);
		//글번호세팅
		replyPageObject.no = Long.parseLong(request.getParameter("no"));
		System.out.println("replyPageObject.no = "+replyPageObject.no);
		
		replyPageObject.replyPageObject = PageObject.getInstance(request, "replyPage", "replyPerPageNum");
		
		return replyPageObject;
	}

	public PageObject getPageObject() {
		return pageObject;
	}

	public long getNo() {
		return no;
	}

	public PageObject getReplyPageObject() {
		return replyPageObject;
	}
	
	public void setTotalRow(Long totalRow) {
		replyPageObject.setTotalRow(totalRow);
	}
	
	public Long getTotalRow() {
		return replyPageObject.getTotalRow();
	}
	
	public Long getStartRow() {
		return replyPageObject.getStartRow();
	}
	
	public Long getEndRow() {
		return replyPageObject.getEndRow();
	}
	
	public String getNotPageQuery() {
		return ""
				+ "replyPerPageNum = "+replyPageObject.getPerPageNum();
	}
	
	public String getPageQuery() {
		return ""
				+ "replyPage = "+replyPageObject.getPage()
				+ "&" + getNotPageQuery();
	}

	@Override
	public String toString() {
		return "ReplyPageObject [pageObject=" + pageObject + ", no=" + no + ", replyPageObject=" + replyPageObject
				+ "]";
	}
	
}
