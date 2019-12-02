<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
        <title>찾아줘 왓슨!</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <script src="http://code.jquery.com/jquery-3.4.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        
        <link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="resources/css/mainStyle.css">

        <style>
            #category{width: 100%; height: 100%;}
            .search-box>*{width: 100%;}
            .write-box>*{width: 100%;}
            #article, .line{
                border: 0.5px solid lightgray;
            }
        </style>
</head>
<body>
    <div class="container">
            <jsp:include page="../standard/header.jsp" />  
            <!--            -->
            <div class="row">
                <div class="col-12 mb-3" id="article">
                    <div class="row">
                        <div id="article-middle" class="col-12 mt-2">
                           <div class="row mb-3 p-1 text-center">
                                <h3 id="board-top" class="col-auto col-sm-4 m-0">질문게시판</h3>
                                <span class="col-auto col-sm-8 mt-2">반려동물에 대해 질문하는 게시판 입니다.</span>
                            </div>
                            <div class="row line">
                <div class="col-1 d-none d-md-block"></div>
                <div class="col-md-2 d-none d-md-block">
                    말머리
                </div>
                <div class="col-md-5 d-none d-md-block">
                    제목
                </div>
                <div class="col-md-2 d-none d-md-block">
                    작성자
                </div>
                <div class="col-md-2 d-none d-md-block">
                    작성날짜
                </div>
            </div>
            <!-- 게시글 목록 -->
			<c:choose>
				<c:when test="${list.size() == 0}">게시물이 없습니다.</c:when>
				<c:when test="${list.size() > 0}">
					<c:forEach items="${list}" var="dto">
						<div class="row line">
			                <div class="col-1 d-none d-md-block">1</div>
			                <div class="col-md-2 d-none d-md-block">
			                	${dto.header}
			                </div>
			                <div class="col-8 col-md-5 text-left">
			                    ${dto.title}
			                </div>
			                <!-- 모바일에서만 보이는 div -->
			                <div class="col-4 d-block d-md-none">${dto.animalHeader}</div>
			                <!-- 모바일에서만 보이는 div -->
			                <div class="col-6 col-md-2 text-left">
			                   	${dto.writer}
			                </div>
			                <div class="col-6 col-md-2 text-center">
			                    ${dto.writeDate}
			                </div>
			            </div>
					</c:forEach>
				</c:when>	            
			</c:choose>          
            <div class="row">
	            <div class="col-12 text-center">
	            	${pageNavi}
	            </div>
            </div>
            <!-- -->
                            <div class="row mb-2">
                                <div class="col-auto col-sm-2 p-1">
                                    <select id="category">
                                        <option value="">제목</option>
                                    </select>
                                </div>
                                <div class="search-box col-auto col-sm-6 p-1">
                                    <input type="text" class="" id="search" name="search" value="">
                                </div>
                                <div class="search-box col-auto col-sm-2 p-1">
                                    <button class="btn btn-sm btn-outline-secondary">검색</button>
                                </div>
                                <div class="write-box col-auto col-sm-2 text-center p-1">
                                    <button id="toWriteBtn" class="btn btn-sm btn-outline-secondary">글쓰기</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--            -->
           <jsp:include page="../standard/footer.jsp" />  
        </div>
        <script>
        	$("#toWriteBtn").on("click",function(){
        		location.href="boardWriteFree.jsp";
        	});
        </script>
</body>
</html>