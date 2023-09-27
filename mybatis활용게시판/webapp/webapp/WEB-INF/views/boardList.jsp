<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판(글 목록)</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">게시판(글목록)</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li><a href="<c:url value='/login/login'/>">login</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
        <li><a href=""><i class="fas fa-search small"></i></a></li>
    </ul>
</div>
<script>
    let msg = "${msg}"//컨트롤러에서 모델에담은 msg를 jsp에서 받기({param.msg})-> rattr에 담는걸로 바꾸면서 {param.msg}->{msg}로 변경
    if(msg=="WRT_OK") alert("게시물이 등록되었습니다..")
    if(msg=="DEL_OK") alert("성공적으로 삭제되었습니다.")
    if(msg=="DEL_ERR") alert("삭제에 실패하였습니다..")
</script>

<%--
tr-테이블의 한줄영역을 뜻함
<tr> </tr>
<tr> </tr>

이럼 2줄인거임
<th>테이블헤더는 이런 <tr>테이블로우의 맨위 헤더를 표현
그리고 데이터내용들은 <td>테이블데이터로 표현

<table border="1"은 테두리를 뜻함. 숫자가 커질수록 테두리가 커짐
--%>
<div style="text-align:center">
    <button type="button" id="writeBtn" onclick="location.href='<c:url value="/board/write"/>'">글쓰기</button>
    <table border="1">
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>이름</th>
            <th>등록일</th>
            <th>조회수</th>
        </tr>
        <c:forEach var="boardDto" items="${list}">
        <tr>
            <td>${boardDto.bno}</td>
            <td><a href="<c:url value='/board/read?bno=${boardDto.bno}&page=${page}&pageSize=${pageSize}'/>">${boardDto.title}</a></td>
            <td>${boardDto.writer}</td>
            <td>${boardDto.reg_date}</td>
            <td>${boardDto.view_cnt}</td>
        </tr>
        </c:forEach>
    </table>
    <br>
    <div>
        <c:if test="${ph.showPrev}">
            <a href="<c:url value='/board/list?page=${ph.beginPage-1}&pageSize=${ph.pageSize}'/>">&lt;</a>
        </c:if>
        <c:forEach var="i" begin="${ph.beginPage}" end="${ph.endPage}">
            <a href="<c:url value='/board/list?page=${i}&pageSize=${ph.pageSize}'/>">${i}</a>
        </c:forEach>
        <c:if test="${ph.showNext}">
            <a href="<c:url value='/board/list?page=${ph.endPage+1}&pageSize=${ph.pageSize}'/>">&gt;</a>

        </c:if>
    </div>
</div>
</body>
</html>