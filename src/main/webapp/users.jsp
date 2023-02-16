<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="backAddress" value="${requestScope['javax.servlet.forward.servlet_path']}?${pageContext.request.queryString}" scope="session"/>
<html>
<head>
    <title><fmt:message key="users_jsp.title"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <script defer src="js/jquery.min.js"></script>
    <script defer src="js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-dark">
		<%@ include file="/_menu.jsp" %>
	</nav>
	<header class="header">
	</header>
	<main class="main">
		<div class="container-fluid">
			<div class="row">
			    <div class="col-md-4 col-sm-0"></div>
				<div class="col-md-4 mainframe">
					<h3><fmt:message key="users_jsp.h3" /></h3>
					<div>
						<ul class="filter-list">
							<li class=${param.tab == "1" ? "filter-list-active" : "filter-list-item"}>
								<a href="front?command=getallusers&tab=1&sort=${param.sort}&page=1" class="filter-list-link"><fmt:message key="users_jsp.teachers" /></a>
							</li>
							<li class=${param.tab == "2" ? "filter-list-active" : "filter-list-item"}>
								<a href="front?command=getallusers&tab=2&sort=${param.sort}&page=1" class="filter-list-link"><fmt:message key="users_jsp.students" /></a>
							</li>
						</ul>
					</div>
					<div>
						<fmt:message key="users_jsp.sort" /> <a class="btn btn-dark" href="front?command=getallusers&tab=${param.tab}&sort=az&page=${param.page}" role="button">A-Z</a> |
						<a class="btn btn-dark" href="front?command=getallusers&tab=${param.tab}&sort=za&page=${param.page}" role="button">Z-A</a>
					</div>
					<div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead class="thead-dark">
                            <tr>
                                <th scope="col"><fmt:message key="users_jsp.lastname" /></th> <th scope="col"><fmt:message key="users_jsp.firstname" /></th> <th scope="col"> </th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${list}" var="user">
                                <tr>
                                    <td>${user.lastName}</td>
                                    <td>${user.firstName}</td>
                                    <td><a href="front?command=edituser&id=${user.id}" class="mainlink" title="Edit this user"><fmt:message key="users_jsp.edit" /></a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table></div>
        			<nav aria-label="Pagination of data">
        			    <ul class="pagination">
                            <c:if test="${page != 1}">
                    		    <li class="page-item"><a href="front?command=getallusers&tab=${param.tab}&sort=${param.sort}&page=${page - 1}" class="page-link mainlink"><fmt:message key="pagination.prev" /></a></li>
                    		</c:if>
                    		<c:forEach var="i" begin="1" end="${numOfPages}">
                    		    <c:choose>
                                    <c:when test="${page == i}">
                                        <li class="page-item active"><a class="page-link">${i}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class="page-item"><a href="front?command=getallusers&tab=${param.tab}&sort=${param.sort}&page=${i}" class="page-link mainlink">${i}</a></li>
                                    </c:otherwise>
                                </c:choose>
                    		</c:forEach>
                    		<c:if test="${page < numOfPages}">
                    		    <li class="page-item"><a href="front?command=getallusers&tab=${param.tab}&sort=${param.sort}&page=${page + 1}" class="page-link mainlink"><fmt:message key="pagination.next" /></a></li>
                    		</c:if>
                    	</ul>
                        <div>${nothingFound}</div>
                    </nav>
				</div>
				<div class="col-md-4 col-sm-0"></div>
			</div>
		</div>
	</main>
	<footer class="fixed-bottom bg-dark footer">
		<%@ include file="/_footer.jsp" %>
	</footer>
</body>
</html>