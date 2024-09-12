/**
 * 
 */

//이벤트처리
$(function(){
	$(".dataRow").click(function(){
		let no=$(this).find(".no").text();	//글번호수집
		if($(this).hasClass("board")){	//모듈체크
			location="/board/view.do?no="+no+"&inc=1";
		} else if($(this).hasClass("notice")){	//모듈체크
			location="/notice/view.do?no="+no;
		} else if($(this).hasClass("image")){	//모듈체크
			location="/image/view.do?no="+no;
		}
	});
});