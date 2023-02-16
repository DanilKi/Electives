<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="ctl" uri="/WEB-INF/customtags.tld" %>
		<div class="container-fluid">
			<div class="row">
				<div class="col-6">
					<p>Electives web-app &#169; 2022 @DanilKi</p>
				</div>
				<div class="col-6 text-lg-end">
                    <fmt:message key="footer_jsp.time"/> <ctl:printtime/>
                </div>
			</div>
		</div>