<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/views/header.jsp"%>
<body>
	<%@ include file="/WEB-INF/views/nav-main_v2.jsp"%>
	<c:url var="urlSharedAccount" value="/share/accnts" />
	<div class="content">

		<div class="row">
			
			<c:forEach items="${usrs}" var="usr">
				<div class="col-sm-4" style="display: flex;align-items: center;justify-content: center;height: 100%;">
					<div class="card w-75 border-dark text-center mt-5">
						<div class="card-header">
							<div class="d-flex justify-content-between mt-1">
								<i class="fas fa-share-alt fa-lg text-success mt-1"></i>
								<h3 class="font-weight-light mr-2">${ usr.name }</h3>
								<p></p>
							</div>
						</div>
						<div class="card-body">
							<i class="fas fa-user fa-3x m-2" style="opacity:0.75;"></i>
							<p class="card-text mt-2">${ counts[usr.id] } accounts shared</p>
							<div class="d-flex justify-content-end">
								<a href="${urlSharedAccount}/?id=${usr.id}" class="btn btn-warning mt-2">Open</a>
							</div>
						</div>
						<div id="last-share-${usr.id}" class="card-footer text-muted"></div>
						<script>
							var d = ${dates[usr.id]};
							document.getElementById("last-share-${usr.id}").innerHTML = "Last share: " + new Date(d).toLocaleDateString();
						</script>
					</div>
				</div>
			</c:forEach>	
						
		</div>
	</div>
</body>