<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/header.jsp"%>
<body>

	<%@ include file="/WEB-INF/views/nav-main_v2.jsp"%>

	<div class="row">
		<c:set var="i" value="0" scope="page" />
		
		<c:forEach items="${accounts}" var="account">
			
			<c:if test="${i%2 == 0}">
				<div class="col-xs-12 col-sm-1"></div>
			</c:if>
			
			<div class="col-xs-12 col-sm-5" style="display: flex; align-items: center; justify-content: center; height: 100%;">
				<div class="card w-100 border-dark mt-5">
					<div class="card-header text-center">
						<div class="d-flex justify-content-between mt-1">
							<c:if test="${account.isOwner(idUser)}">
								<i class="far fa-edit fa-sm fa-2x text-secondary"></i>
							</c:if>
							<c:if test="${!account.isOwner(idUser)}">
								<i class="fas fa-share-alt fa-sm fa-2x text-success"></i>
							</c:if>
							<h1 class="font-weight-light"> ${account.accountShared.name} </h1>
							<p> </p>
						</div>
					</div>
					<div class="card-body">
						<p class="card-title font-weight-light">Email: ${account.accountShared.email}</p>
						<p class="card-title font-weight-light">Key: ${account.accountShared.key}</p>
						<p class="card-text font-weight-light">Description: ${account.accountShared.description}</p>
	
						<button type="button" onclick="move('Home')" class="btn btn-warning mt-4">Move</button>
						<button type="button" onclick="edit()" class="btn btn-warning  ml-2 mt-4">Edit</button>
						<a href="#deleteSelectedModal" data-toggle="modal" class="btn btn-danger float-right mt-4">Delete</a>
					</div>
					<div id="last-share-${usr.id}" class="card-footer text-muted"></div>
				</div>
			</div>
			
			<c:if test="${i%2 != 0}">
				<div class="col-xs-12 col-sm-1"></div>
			</c:if>
			
			<c:set var="i" value="${i + 1}" scope="page"/>
		</c:forEach>
	</div>
</body>