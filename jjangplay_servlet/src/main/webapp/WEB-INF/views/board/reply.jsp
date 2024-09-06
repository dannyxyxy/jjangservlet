<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="pageNav" tagdir="/WEB-INF/tags"%>

 <div class="card">
   <div class="card-header"><span class="btn btn-primary float-right"
    id="replyWriteBtn">등록</span><h3>댓글리스트</h3></div>
   <div class="card-body">
 		<c:if test="${empty replyList }">
 			데이터가 존재하지 않습니다.
 		</c:if>
 		<c:if test="${!empty replyList }">
 			<c:forEach items="${replyList }" var="replyVO">
 			<div class="card replyDataRow" data-rno="${replyVO.rno}" style="margin: 5px 0;">
 				 <div class="card-header">
 				 	<span class="float-right">${replyVO.writeDate}</span>
 				 	<b class="replyWriter">${replyVO.writer}</b>
 				 </div>
 				 <div class="card-body">
 				 	<pre class="replyContent">${replyVO.content }</pre>
 				 </div>
 				 <div class="card-footer">
 				 	<button class="btn btn-sucess replyUpdateBtn">수정</button>
 				 	<button class="btn btn-danger replyDeleteBtn">삭제</button>
 				 </div>
 			</div>
 			</c:forEach>
 		</c:if>
 	</div>
 </div> 
 <div class="card-footer">
 	<pageNav:replayPageNav listURI="view.do" pageObject="${replyPageObject}"></pageNav:replayPageNav>
 </div>

<!-- 댓글등록/수정/삭제를 위한 모달 -->  <!-- The Modal -->
 <div class="modal fade" id="boardReplyModal">
   <div class="modal-dialog">
     <div class="modal-content">
     
       <!-- Modal Header -->
       <div class="modal-header">
         <h4 class="modal-title">댓글 작성</h4>
         <button type="button" class="close" data-dismiss="modal">&times;</button>
       </div>
       
       <!-- Modal body -->
      <form action="" id="boardReplyForm" method="post">
         	<input type="hidden" name="rno" id="rno">
         	<input type="hidden" name="no" value="${param.no}">
         	<input type="hidden" name="page" value="${param.page}">
         	<input type="hidden" name="perPageNum" value="${param.perPageNum}">
       <div class="modal-body">
         
         <div class="form-group" id="contentDiv">
         	<label for="content">내용</label>
         	<textarea class="form-group" rows="3" id="content" name="content"></textarea>
         </div>
         <div class="form-group" id="writerDiv">
         	<label for="writer">작성자</label>
         	<input type="text" class="form-control" id="writer" name="writer">
         </div>
         <div class="form-group" id="pwDiv">
         	<label for="pwr">비밀번호</label>
         	<input type="password" class="form-control" id="pwr" name="pw">
         </div>
       </div>
       
       <!-- Modal footer -->
       <div class="modal-footer">
       	<button type="button" class="btn btn-primary" id="replyModalWriteBtn">등록</button>
       	<button type="button" class="btn btn-success" id="replyModalUpdateBtn">수정</button>
       	<button type="button" class="btn btn-danger" id="replyModalDeleteBtn">삭제</button>
         <button type="button" class="btn btn-warning" data-dismiss="modal">취소</button>
       </div>
       </form>
     </div>
   </div>
</div>
 
 
 <script type="text/javascript">
$(function(){
	$("#replyWriteBtn").click(function(){
		$("#boardReplyModal").find(".modal-title").text("댓글등록");
		$("#boardReplyForm").find(".form-group").show();
		$("#boardReplyForm").find(".form-group->input .form-group->textarea").val("");
		$("#boardReplyForm button").show();
		$("#replyModalUpdateBtn, #replyModalDeleteBtn").hide();
		$("#boardReplyModal").modal("show");
	});
	$(".replyUpdateBtn").click(function(){
		$("#boardReplyModal").find(".modal-title").text("댓글수정");
		$("#boardReplyForm").find(".form-group").show();
		$("#boardReplyForm").find(".form-group->input .form-group->textarea").val("");
		
		let replyDataRow = $(this).closest(".replyDataRow");
		let rno = replyDataRow.data("rno");
		let content = replyDataRow.find(".replyContent").text();
		let writer = replyDataRow.find(".replyWrtier").text();
		
		console.log("rno="+rno);
		
		$("#rno").val(rno);
		$("#content").val(content);
		$("#writer").val(writer);
		
		$("#boardReplyForm button").show();
		$("#replyModalWriteBtn, #replyModalDeleteBtn").hide();
		$("#boardReplyModal").modal("show");
	});
	$(".replyDeleteBtn").click(function(){
		$("#boardReplyModal").find(".modal-title").text("댓글삭제");
		$("#boardReplyForm").find(".form-group").hide();
		$("#pwDiv").show();
		$("#pwr").show();
		$("#boardReplyForm").find(".form-group->input .form-group->textarea").val("");
		
		let replyDataRow = $(this).closest(".replyDataRow");
		let rno = replyDataRow.data("rno");
		$("#rno").val(rno);
		
		$("#boardReplyForm button").show();
		$("#replyModalWriteBtn, #replyModalUpdateBtn").hide();
		$("#boardReplyModal").modal("show");
	});
	// 모달창안에 버튼을 클릭했을때 처리
	$("#replyModalWriteBtn").click(function(){
		$("#boardReplyForm").attr("action","/boardreply/write.do");
		$("#boardReplyForm").submit();
	});
	$("#replyModalUpdateBtn").click(function(){
		$("#boardReplyForm").attr("action","/boardreply/update.do");
		$("#boardReplyForm").submit();
	});
	$("#replyModalDeleteBtn").click(function(){
		$("#boardReplyForm").attr("action","/boardreply/delete.do");
		$("#boardReplyForm").submit();
	});
	
});
</script>
 
 