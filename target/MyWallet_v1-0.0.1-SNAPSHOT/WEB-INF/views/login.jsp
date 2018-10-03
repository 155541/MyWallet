<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/views/header.jsp"%>
<body>

	<%@ include file="/WEB-INF/views/nav-simple.jsp"%>

	<div class="container">
		<div class="row mt-5">
			<h1 class="display-5 font-weight-light">Set your credentials</h1>
			<div class="line"></div>
		</div>
		<div class="row mt-5">
			<div class="col-xs-0 col-sm-2 col-md-3 col-lg-4"></div>
			<div class="col-xs-12 col-sm-8 col-sm-offset-2 col-md-6 col-md-offset-3 col-lg-4 col-lg-offset-3">
				<ul class="nav nav-tabs mb-2"
					role="tablist">
					<li class="nav-item">
						<a	class="nav-link active"
							data-toggle="tab"
							role="tab"
							href="#signin"
							aria-selected="true">Sign in</a>
					</li>
					<li class="nav-item">
						<a	class="nav-link" 
							data-toggle="tab"
							role="tab"
							href="#signup">Sign up</a>
					</li>
				</ul>

				<div class="tab-content rounded-bottom border border-top-0 p-4">
					<div class="tab-pane fade show active"
						 id="signin">
						
						<p>Enter your user name / email and your password</p>
						<c:url 	var="loginUrl" value="/login"/>
						<form 	class="mt-4"
								role="Form"
								method="post"
								action="${loginUrl}"
								accept-charset="UTF-8">
								
							<c:if test="${param.error != null}">
                                <div class="alert alert-danger">
                                    <p>Invalid username and password.</p>
                                </div>
                            </c:if>
                            
                            <c:if test="${param.logout != null}">
                                <div class="alert alert-success">
                                    <p>You have been logged out successfully.</p>
                                </div>
                            </c:if>
                             
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-prepend" >
	                                	<span	class="input-group-text"
	                                			id="icUser">
	                                			<i class="fa fa-user"></i>
	                                	</span>
	                                </div>
									<input	class="form-control"
											type="text"
											id="username"
											name="username"
											placeholder="Username..."
											aria-describedby="icUser"
											required>
	                            </div>
							</div>
							<div class="form-group">
								<div class="input-group mb-3">
									<div class="input-group-prepend">
										<span	class="input-group-text"
												id="icPassword">
												<i class="fa fa-lock"></i>
										</span>
									</div>
									<input 	class="form-control"
											type="password" 
											name="password"
											placeholder="Password..." 
											aria-describedby="icPassword" 
											required/>
								</div>
							</div>
							<input 	type="hidden" 
									name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							<div class="form-group">
								<label> 
									<input 
										type="checkbox"
										id="rememberme"
										name="remember-me">
									Remember me
								</label>
							</div>
							<div class="form-actions">
								<button class="btn btn-outline-primary"
										type="submit">Sign in</button>
							</div>
						</form>
					</div>
					
					<div class="tab-pane fade" id="signup">
						<p>You will receive an email to verify your account.</p>
						<c:url 	var="reg" value="/register"/>
						<form:form 	role="Form" 
									method="post"
									modelAttribute="appUser"
									action="${reg}"
									accept-charset="UTF-8">
									
							<small><form:errors path="name" class="help-inline mt-2 text-danger"/></small>
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-prepend">
										<span	class="input-group-text"
												id="icUserSignup">
												<i class="fa fa-user"></i>
										</span>
									</div>
									<form:input class="form-control"
												type="text"
												name="name"
												path="name"
												placeholder="User..."
												aria-describedby="icUserSignup"/>
								</div>
							</div>
							
							<small><form:errors path="email" class="help-inline mt-2 text-danger"/></small>
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-prepend">
										<span	class="input-group-text"
												id="icMailSignup">
												<i class="fa fa-envelope"></i>
										</span>
									</div>
									<form:input class="form-control"
												type="text"
												name="email"
												path="email"
												placeholder="Email..."
												aria-describedby="icMailSignup"/>
								</div>
							</div>
							<small><form:errors path="password" class="help-inline text-danger"/></small>
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-prepend">
										<span	class="input-group-text"
												id="icPassSignup">
												<i class="fa fa-lock"></i>
										</span>
									</div>
									<form:input	class="form-control"
												type="password"
												name="password"
												path="password"
												placeholder="Password..."
												aria-describedby="icPassSignup"/>
								</div>
							</div>
							<div class="form-group">
								<div class="input-group">
									<div class="input-group-prepend">
										<span	class="input-group-text"
												id="icPassConfirmSignup">
												<i class="fa fa-lock"></i>
										</span>
									</div>
									<input class="form-control"
												type="password"
												name="password2"
												placeholder="Verify password..."
												aria-describedby="icPassConfirmSignup">
								</div>
							</div>
							<div class="form-group">
								<button class="btn btn-outline-primary"
										name="bt_signup"
										type="submit"> Sign up </button>
							</div>
						</form:form>
					</div>
				</div>
			</div>
			<div class="col-xs-0 col-sm-2 col-md-3 col-lg-4">
			</div>
		</div>
	</div>
</body>