<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="container">
	<h3>로그인 폼</h3>
	<form action="login.do" method="post">
		<div class="form-group">
			<label for="id">ID</label>
			<input type="text" class="form-control" placeholder="ID입력" id="id" name="id" autocomplete="none">
		</div>
		<div class="form-group">
			<label for="pw">ID</label>
			<input type="password" class="form-control" placeholder="pw입력" id="pw" name="pw" autocomplete="none">
		</div>
		<button type="submit" class="btn btn-primary">login</button>
	</form>
</div>
</body>
</html>