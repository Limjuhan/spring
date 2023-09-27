<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>게시판(글내용)</title>
    <link rel="stylesheet" href="<c:url value='/css/menu.css'/>">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
<div id="menu">
    <ul>
        <li id="logo">게시판(글내용)</li>
        <li><a href="<c:url value='/'/>">Home</a></li>
        <li><a href="<c:url value='/board/list'/>">Board</a></li>
        <li><a href="<c:url value='/login/login'/>">login</a></li>
        <li><a href="<c:url value='/register/add'/>">Sign in</a></li>
        <li><a href=""><i class="fas fa-search small"></i></a></li>
    </ul>
</div>
<script>
    let msg="${msg}";
    if(msg=="WRT_ERR") alert("게시물등록에 실패했습니다. 다시 시도해주세요")
</script>

<div style="text-align:center">
    <h2 id="boardtitle">게시물 ${mode=="new" ? "글쓰기" : "읽기"}</h2>
    <form id="form" >
        <input type="hidden" name="bno" value="${boardDto.bno}" >
        <input type="text" name="title" value="${boardDto.title}" ${mode=="new" ? '': 'readonly="readonly"'}>
        <textarea name="content" id="" cols="30" rows="10" ${mode=="new" ? '': 'readonly="readonly"'}>${boardDto.content}</textarea>
        <button type="button" id="writebtn" class="btn">새글쓰기</button>
        <button type="button" id="modifybtn" class="btn">수정</button>
        <button type="button" id="removebtn" class="btn">삭제</button>
        <button type="button" id="listbtn" class="btn">목록</button>
    </form>
</div>
<script>
    $(document).ready(function () { // main()
        //목록버튼클릭
        $('#listbtn').on("click", function () {

           location.href = "<c:url value='/board/list'/>?page=${page}&pageSize=${pageSize}";
            // return false;//button타입 button으로 지정안하면 form태그 submit돼서 false로 submit막았었음.
        });

        //수정버튼클릭
        $('#modifybtn').on("click", function () {
            //1.읽기상태이면 수정상태로 변경
            let form = $('#form');
            let isReadOnly = $("input[name=title]").attr('readonly');

            if(isReadOnly == 'readonly') {
                $("input[name=title]").attr('readonly', false); //title
                $("textarea[name=content]").attr('readonly', false); //content
                $("#modifybtn").html("등록");
                $("#boardtitle").html("게시물 수정");
                return;
            }


            //2.수정상태이면, 수정된 내용을 서버로 전송
            form.attr("action", "<c:url value='/board/modify'/>?page=${page}&pageSize=${pageSize}");
            form.attr("method", "post");
            form.submit();
        });

        //삭제버튼 클릭
        $('#removebtn').on("click", function () {
               if(!confirm("삭제하시겠습니까?"))
                   return;

                let form = $('#form');
                form.attr("action", "<c:url value='/board/remove'/>?page=${page}&pageSize=${pageSize}");
                form.attr("method", "post");
                form.submit();

            // return false;
        });

        $('#writebtn').on("click", function () {

            let form = $('#form');
            form.attr("action", "<c:url value='/board/write'/>");
            form.attr("method", "post");
            form.submit();

            // return false;
        });
    });
</script>
</body>
</html>