<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>

		<div class="container-fluid">
			<div class="row justify-content-lg-center">
				<div class="col-12 text-center">
				    <c:choose>
				        <c:when test="${empty authorizedUser}">
					        <h1><fmt:message key="header_jsp.welcome" /></h1>
						    <div class="h3">
						        <fmt:message key="header_jsp.welcome_message1" />
                                <tf:printdate/>
                                <fmt:message key="header_jsp.welcome_message2" />
						    </div>
                    	</c:when>
                    	<c:otherwise>
                    	    <div class="h3"><fmt:message key="header_jsp.usemenu" /></div>
                    	</c:otherwise>
                    </c:choose>
				</div>
			</div>
		</div>