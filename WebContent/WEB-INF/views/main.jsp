<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/header.jsp"%>
<body>

	<%-- URLS --%>
	<c:url var="urlMove" value="/move" />
	<c:url var="urlMove" value="/move" />
	<c:url var="urlMoveBack" value="/moveBack" />
	<c:url var="urlMoveSubmit" value="/moveSubmit" />
	<c:url var="urlInto" value="/getInto"/>
	<c:url var="urlAddDir" value="/addDir"/>
	<c:url var="urlAddAccnt" value="/addAccount"/>
	<c:url var="urlGet" value="/getItem"/>
	<c:url var="urlPostDir" value="/editDir"/>
	<c:url var="urlPostAccnt" value="/editAccnt"/>
	<c:url var="tryShareUrl" value="/shareAccnt"/>

	<%@ include file="/WEB-INF/views/nav-main_v2.jsp"%>

	<div class="content">
	
		<div class="row">
			<div class="col-xs-0 col-md-1 mt-5"></div>
			<div class="col-xs-12 col-md-10 mt-5 d-flex justify-content-center">

				<nav class="breadcrumb" style="width:90%;">
					<c:forEach items="${paths}" var="d">
						<c:if test="${curr_d.equals(d)}">
							<span class="breadcrumb-item active">${d}</span>
						</c:if>
						<c:if test="${!curr_d.equals(d)}">
							<a class="breadcrumb-item" href="${pageContext.request.contextPath}/?d=${d}">${d}</a>
						</c:if>
					</c:forEach>
				</nav>
			</div>
			<div class="col-xs-0 col-md-1 mt-5"></div>
		</div>
		<div class="row">
		
			<div class="col-xs-0 col-md-1 mt-3"></div>
		
			<div class="col-xs-12 col-md-5 mt-3">
				<div class="list-group float-right" id="list-tab" role="tablist" style="width:90%;">
					
					<c:if test="${dirs.size() == 0 && accnts.size() == 0}">

						<ul class="list-group list-group-flush w-50">
							<li class="list-group-item">
								<div class="d-flex">
									<i class="fas fa-folder fa-2x align-self-center" style="opacity: 0.75;"></i>
									<button type="button" onclick="createDir()" class="btn btn-link font-weight-light ml-4 align-self-center">
										Add new directory
									</button>
								</div>
							</li>
							<li class="list-group-item">
								<div class="d-flex">
									<i class="fas fa-key fa-lg align-self-center" style="opacity: 0.75;"></i>
									<button type="button" onclick="createAccnt()" class="btn btn-link font-weight-light ml-4 align-self-center">
										Add new account
									</button>
								</div>
							</li>
						</ul>
					</c:if>
					
					<c:forEach items="${dirs}" var="dir">
						
						<div 
							class="list-group-item list-group-item-action list-group-item-secondary d-flex"
							id="list-dir-${dir.id}"
							data-toggle="list"
							role="tab"
							aria-controls="${dir.id}">
						
							<i class="fas fa-folder fa-2x align-self-center"></i>
							<p class="align-self-center ml-4 mt-4">${dir.name}</p>
							<span class="badge badge-warning badge-pill ml-auto align-self-center">${ counter[dir.id] }</span>
						
						</div>
						
						<form:form	id="openDir-${dir.id}"
									method="post"
									action="${urlInto}"
									accept-charset="UTF-8">
							<input type="hidden" name="dir" value="${dir.id}"/>
						</form:form>
						
						<script>
							$(document).ready(function() {
								$("#list-dir-${dir.id}").dblclick(function() {
									$("#openDir-${dir.id}").submit();
								})
							})
						</script>
					</c:forEach>
					
					<c:forEach items="${accnts}" var="accnt">
					
						<a class="list-group-item list-group-item-action list-group-item-secondary"
							id="list-accnt-${accnt.id}"
							data-toggle="list"
							href="#content-${accnt.id}"
							role="tab"
							aria-controls="${accnt.id}">
							
							<i class="fas fa-key ml-2 mr-4"></i>
							${accnt.name}
							<c:if test="${ accntShared[accnt.id] }">
								<i class="fas fa-share-alt text-success mt-1 mr-2 float-right"></i>
							</c:if>
						</a> 
					</c:forEach>
					
				</div>
			</div>
			
			<div class="col-xs-12 col-md-5 mt-3">
				<div class="tab-content " id="nav-tabContent">
				
					<c:forEach items="${accnts}" var="accnt">	
						<div class="tab-pane fade" id="content-${accnt.id}" role="tabpanel" aria-labelledby="list-accnt-${accnt.id}"
									style="margin: auto;" >
							<div class="card" style="width:90%;">
																
								<div class="card-header">
									<div class="row">
										<div class="col-6">
											<h3 class="font-weight-light mt-2">${accnt.name}</h3>
										</div>
										<div class="col-6">
											<h6 class="font-italic text-right">
												<small id="creationText-${accnt.id}" class="font-weight-light"></small>
											</h6>
											<script>
											var d = ${accnt.creationDate};
											document.getElementById("creationText-${accnt.id}").innerHTML = "Created: " + new Date(d).toLocaleDateString();
											</script>
											
											<h6 class="card-text font-italic text-right mt-3">
												<small id="updatedText-${accnt.id}" class="font-weight-light"></small>
											</h6>
											<c:if test="${accnt.updatedDate != null && accnt.updatedDate != ''}">
												<script>
												var d = ${accnt.updatedDate};
												document.getElementById("updatedText-${accnt.id}").innerHTML = "Updated: " + new Date(d).toLocaleDateString();
												</script>
											</c:if>
										</div>
									</div>
								</div>
								
								
								<div class="card-body">
									<p class="card-title font-weight-light mt-3">Email: ${accnt.email}</p>
									<p class="card-title font-weight-light">Key: ${accnt.key}</p>
									<p class="card-text font-weight-light mb-3">Description: ${accnt.description}</p>
								</div>
								
								<div class="card-footer">
									<button type="button" onclick="edit()" class="btn btn-warning">
										<i class="fas fa-edit fa-lg my-1 ml-1 text-dark" style="opacity:0.5;"></i>
									</button>
									<button type="button" onclick="move('Home')" class="btn btn-warning ml-3">
										<i class="fas fa-sign-out-alt fa-lg my-1 ml-1 text-dark" style="opacity:0.5;"></i>
									</button>

									<a href="#deleteSelectedModal" data-toggle="modal" class="btn btn-danger float-right"> 
										<i class="fas fa-trash-alt fa-lg my-1" style="opacity: 0.9;"></i>
									</a>
								</div>
								
							</div>
						</div>
					</c:forEach>
					
				</div>
			</div>
			
			<div class="col-xs-0 col-md-1 mt-3"></div>
		</div>
	</div>
	
	<%-- Modals --%>
	
	<div class="modal fade" id="directoryModal" tabindex="-1" role="dialog" aria-labelledby="titleDirectoryModal" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="titleDirectoryModal"></h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form:form	method="post"
							action="${addDirUrl}"
							modelAttribute="directory"
							accept-charset="UTF-8"
							id="formDirectory">
							
					<div class="modal-body">
						<div class="form-group">
							<form:label for="nameDirectory" path="name" class="col-form-label">Name:</form:label>
							<form:input type="text" path="name" class="form-control" id="nameDirectory"/>
							<input type="hidden" class="form-control" id="idDirectory" name="idDirectory" />
						</div>	
					</div>
					<div class="modal-footer">
						<div class="form-group">
							<button id="modalDirButton" type="submit" class="btn btn-warning"></button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>

	<div class="modal fade" id="accountModal" tabindex="-1" role="dialog" aria-labelledby="titleAccountModal" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="titleAccountModal"></h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form:form	id="formAccount"
							role="Form" 
						   	method="post"
						   	action="${urlAddAccnt}"
						   	modelAttribute="account"
						   	accept-charset="UTF-8">
					<div class="modal-body">
						<input type="hidden" class="form-control" id="idAccount" name="idAccount" />
						<div class="form-group">
							<label for="nameAccount" class="col-form-label">Name:</label>
							<form:input type="text" path="name" class="form-control" id="nameAccount"></form:input>
						</div>
						<div class="form-group">
							<label for="emailAccount" class="col-form-label">Email:</label>
							<form:input type="email" path="email" class="form-control" id="emailAccount"/>
						</div>	
						<div class="form-group">
							<label for="descriptionAccount" class="col-form-label">Description:</label>
							<form:input type="text" path="description" class="form-control" id="descriptionAccount"></form:input>
						</div>
						
						<label for="contentAccount" class="col-form-label">Key:</label>
						<div class="form-group form-inline">
							<form:input type="text" path="key" class="form-control" id="contentAccount" size="40"></form:input>
							<button type="button" onclick="generate()" id="buttonGenerate" class="btn btn-warning ml-3">Generate</button>
						</div>
						
						<div class="form-group">
							<label for="lengthAccount" class="col-form-label">Length:</label>
							<input type="number" class="form-control" id="lengthAccount" name = "lengthAccount" />
						</div>
						
						<div class="form-group">
							<label for="controlSelectComplexity">Complexity:</label> 
							<select class="form-control" id="controlSelectComplexity" name = "controlSelectComplexity">
								<option value=23>Both</option>
								<option value=12>Just number</option>
								<option value=37>Just characters</option>
							</select>
						</div>
					</div>
						
					<div class="modal-footer">
						<div class="form-group">
							<button type="submit" id="modalAccntButton" class="btn btn-warning"></button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	
	<div class="modal fade" id="moveItemModal" tabindex="-1" role="dialog" aria-labelledby="titleMoveItemModal" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="titleMoveItemModal">Move to...</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="list-group" id="modal-list-tab" role="tablist">
				    </div>
				</div>		
				<div class="modal-footer">
					<div class="form-group">
						<button type="button" id="backButton" onclick="moveBack()"
								class="btn btn-warning mr-3">Back</button>
						<button type="button" id="modalAccntButton" class="btn btn-warning" onclick="moveSubmit()">Move</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%-- #deleteSelectedModal --%>
	<div class="modal fade" id="deleteSelectedModal" tabindex="-1"
		role="dialog" aria-labelledby="exampleModalCenterTitle"
		aria-hidden="true">
		<div class="modal-dialog modal-dialog-centered" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="deleteModalLongTitle">Confirmation</h5>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<p id="deleteText"></p>
					<c:url var="deleteItem" value="/deleteItem"/>
					<form:form	id = "deleteItemForm"
								method="post"
								action="${deleteItem}"
								accept-charset="UTF-8">
						<input type="hidden" name="itemToDelete" id="itemToDelete"/>
						<input type="hidden" name="typeToDelete" id="typeToDelete"/>
					</form:form>
				</div>
				<div class="modal-footer">
					<button id="btDelete" type="button" class="btn btn-danger" onclick="clickDelete()">Delete item</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="shareModal" tabindex="-1" role="dialog" aria-labelledby="titleShareModal" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="titleDirectoryModal">
						Share account
					</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form method="post"
					action="${tryShareUrl}"
					accept-charset="UTF-8"
					id="formShare">
							
					<div class="modal-body">
						<div class="form-group">
							<label for="nameDirectory" class="col-form-label">Enter the username of you want to share with:</label>
							<input type="text" class="form-control" name="name" id="nameUserShare"/>
							<input 	type="hidden" 
									name="${_csrf.parameterName}"
									value="${_csrf.token}" />
						</div>	
					</div>
					<div class="modal-footer">
						<div class="form-group">
							<button id="modalDirButton" type="submit" class="btn btn-warning">Share</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<%-- CONTEXT MENU --%>
	<div id="contextMenu" class="dropdown-menu" role="menu" style="display:none">
		<button type="button" class="dropdown-item">
			<div class="row mt-2">
				<div class="col-1"><i class="fas fa-folder" style="opacity:0.75;"></i></div>
				<div class="col-1 ml-2"><h6 class="font-weight-light">Add directory</h6></div>
			</div>
		</button>
		<button type="button" class="dropdown-item">
			<div class="row">
				<div class="col-1"><i class="fas fa-key" style="opacity:0.75;"></i></div>
				<div class="col-1 ml-2"><h6 class="font-weight-light">Add account</h6></div>
			</div>
		</button>
		<div class="border-top mx-2 mt-2 mb-3"></div>
		<button type="button" class="dropdown-item">
			<div class="row">
				<div class="col-1"><i class="fas fa-edit" style="opacity:0.75;"></i></div>
				<div class="col-1 ml-2"><h6 class="font-weight-light">Edit item</h6></div>
			</div>
		</button>
		<button type="button" class="dropdown-item">
			<div class="row">
				<div class="col-1"><i class="fas fa-sign-out-alt" style="opacity:0.75;"></i></div>
				<div class="col-1 ml-2"><h6 class="font-weight-light">Move item</h6></div>
			</div>
		</button>
		<button type="button" class="dropdown-item">
			<div class="row">
				<div class="col-1"><i class="fas fa-share-square" style="opacity:0.75;"></i></div>
				<div class="col-1 ml-2"><h6 class="font-weight-light">Share</h6></div>
			</div>
		</button>
		<div class="border-top mx-2 mt-2 mb-3"></div>
		<button type="button" class="dropdown-item">
			<div class="row">
				<div class="col-1"><i class="fas fa-trash-alt text-danger" style="opacity:0.75;"></i></div>
				<div class="col-1 ml-2"><h6 class="font-weight-light text-danger">Delete</h6></div>
			</div>
		</button>
	</div>

	<%-- Scripts --%>
	
	<script>
	
	// -------------------
	// ------ SCOPE ------
	// -------------------
	
	var itemToMove_type;
	var itemToMove_id;
	var canMove = true;
	
	// -----------------------
	// ------ FUNCTIONS ------
	// -----------------------

	// FUNC:: CREATE MODAL
	function createDir() {
		document.getElementById("titleDirectoryModal").innerHTML = "Create directory";
		document.getElementById("modalDirButton").innerHTML = "Add directory";
		document.getElementById("formDirectory").action = "${urlAddDir}";
		document.getElementById("idDirectory").value = "-1";
		document.getElementById("nameDirectory").value = "";
		$("#directoryModal").modal("show");
	}
	
	// FUNC:: CREATE MODAL
	function createAccnt() {
		document.getElementById("titleAccountModal").innerHTML = "Create account";
		document.getElementById("modalAccntButton").innerHTML = "Add account";
		document.getElementById("formAccount").action = "${urlAddAccnt}";
			
		document.getElementById("idAccount").value = "-1";
		document.getElementById("nameAccount").value = "";
		document.getElementById("emailAccount").value = "";
		document.getElementById("descriptionAccount").value = "";
		document.getElementById("contentAccount").value = "";
		document.getElementById("lengthAccount").value = "";
		document.getElementById("controlSelectComplexity").value = 23;
		$("#accountModal").modal("show");
	}
	
	// FUNC:: EDIT MODAL
	function edit() {
		var items = document.getElementsByClassName("list-group-item active");
		if (items[0] != null)
		{
			var id = items[0].id.split("-")[2];
			var type = items[0].id.split("-")[1];
			$.get("${urlGet}", {type: type, id: id} , function(item)
			{
				if (type != null && type == "dir")
				{
					document.getElementById("titleDirectoryModal").innerHTML = "Modify directory";
					document.getElementById("modalDirButton").innerHTML = "Update directory";
					document.getElementById("formDirectory").action = "${urlPostDir}";
					
					document.getElementById("idDirectory").value = item.split('$$')[0];
					document.getElementById("nameDirectory").value = item.split('$$')[1];
					$("#directoryModal").modal("show");
				}
				else if (type != null && type == "accnt")
				{
					document.getElementById("titleAccountModal").innerHTML = "Modify account";
					document.getElementById("modalAccntButton").innerHTML = "Update account";
					document.getElementById("formAccount").action = "${urlPostAccnt}";
						
					document.getElementById("idAccount").value = item.split('$$')[0];
					document.getElementById("nameAccount").value = item.split('$$')[1];
					document.getElementById("emailAccount").value = item.split('$$')[2];
					document.getElementById("descriptionAccount").value = item.split('$$')[3];
					document.getElementById("contentAccount").value = item.split('$$')[4];
					if(item.split("$$")[5].split(".").length == 2)
					{
						document.getElementById("lengthAccount").value = item.split('$$')[5].split(".")[0];
						document.getElementById("controlSelectComplexity").value = item.split('$$')[5].split(".")[1];
					}
					$("#accountModal").modal("show");
				}
			});
		}
		else
		{
			alert("You must select one item from the list");	
		}
	};
	
	// FUNC:: MOVE MODAL
	function move(name) {
		
		if (name == "Home")
		{
			var dirs = document.getElementsByClassName("list-group-item list-group-item-action list-group-item-secondary d-flex active");
			var accnts =  document.getElementsByClassName("list-group-item list-group-item-action list-group-item-secondary active");
			var id;
			
			if (dirs.length == 0 && accnts.length == 0)
			{
				alert("You must select one item from the list");
				canMove = false;
			}
			else
			{
				if (dirs.length != 0) {
					id = dirs[0].getAttribute("id");
				}
				else {
					id = accnts[0].getAttribute("id");
				}
				itemToMove_type = id.split("-")[1];
				itemToMove_id = id.split("-")[2];
				canMove = true;
			}
		}
				
		if (name != null && name != "" && canMove)
		{
			$.get("${urlMove}", {dir: name}, function(data){
				
				var splitted = data.split("___");
				var nodeRoot = document.getElementById("modal-list-tab");
				
				var nodes = nodeRoot.childNodes;
				while (nodes.length > 0)
				{
					nodes[0].remove();		
				}
				
				if (data == "NoData")
				{
					var div = document.createElement("div");
					div.className = "d-flex justify-content-center p-2 text-danger";
					var text1 = document.createTextNode("There is no directories");
					div.appendChild(text1);
					nodeRoot.appendChild(div);
				}
				else if ( splitted != null && splitted.length > 0)
				{
					for (var i = 0 ; i < splitted.length && splitted[i] != "" ; i++)
					{
						
						var id = splitted[i].split("__")[1];
						var name = splitted[i].split("__")[0];
						var count = splitted[i].split("__")[2];
							
						var div = document.createElement("div");
						div.className = "list-group-item list-group-item-action list-group-item-secondary d-flex";
						div.setAttribute("data-toggle","list");
						div.setAttribute("role","tab");
						div.setAttribute("id","modallist-dir-"+id);
						div.setAttribute("ondblclick", "click2x('"+ name +"')");
						
						var icon = document.createElement("i");
						icon.className = "fas fa-folder fa-2x align-self-center";
						
						var p = document.createElement("p");
						p.className = "align-self-center ml-4 mt-3";
						var text1 = document.createTextNode(name);
						p.appendChild(text1);
						
						var badge = document.createElement("span");
						badge.className="badge badge-warning badge-pill ml-auto align-self-center";
						var text2 = document.createTextNode(count);
						badge.appendChild(text2);
						
						var form = document.createElement("form");
						form.setAttribute("method","POST");
						form.setAttribute("action","${urlMove}");
						form.setAttribute("accept-charset","UTF-8");

						var input = document.createElement("input");
						input.setAttribute("type","hidden");
						input.setAttribute("name","id");
						input.value = id;
						
						form.appendChild(input);
						
						
						div.appendChild(icon);
						div.appendChild(p);
						div.appendChild(badge);
						
						nodeRoot.appendChild(div);
						nodeRoot.appendChild(form);
					}
				}

				$("#moveItemModal").modal("show");
			});
		}
		else if(canMove)
		{
			alert("Name is blank or null");
		}
	};
	
	// FUNC:: MOVE MODAL
	async function click2x(name){
		$("#moveItemModal").modal("hide");
		await sleep();
		move(name); 
	};
	
	// FUNC:: MOVE MODAL
	function moveBack() {
		$.get("${urlMoveBack}", async function(name){
			$("#moveItemModal").modal("hide");
			await sleep();
			move(name);
		});
	};
	
	// FUNC:: MOVE MODAL
	function moveSubmit() {
		$.post("${urlMoveSubmit}",
				{id:itemToMove_id, type: itemToMove_type, "${_csrf.parameterName}": "${_csrf.token}" },
				function() { 
					$("#moveItemModal").modal("hide");
					location.reload();
				});
	};
	
	// FUNC:: DELETE MODAL
	function clickDelete() {
		var items = document.getElementsByClassName("list-group-item active");
		if (items[0] != null)
		{
			document.getElementById("itemToDelete").value = items[0].id.split("-")[2];
			document.getElementById("typeToDelete").value = items[0].id.split("-")[1];
			document.getElementById("deleteItemForm").submit();
		}
	};
	
	// FUNC:: INNER - GENERATE ID
	function generate() {
		document.getElementById("contentAccount").value = 
			makeId(document.getElementById("lengthAccount").value, document.getElementById("controlSelectComplexity").value);
	};
	
	// FUNC:: INNER - GENERATE ID
	function makeId(length, type) {
		
		if (length == null || length == "")
		{ 
			length = 16;
			document.getElementById("lengthAccount").value = 16;
		}
		
		if (type == null || type == "")
		{
			type = 23;
			document.getElementById("controlSelectComplexity").value = 23;
		}
		
		var possible = "";
		switch (type) {
		case 12:
		case "12":
			possible = "6241793508"
			break;
		case 37:
		case "37":
			possible = "Awx\lm|#yz!·$%BchUefgLMV@CDjkE-noPQp;:dK_Ç*IJ}{[NOrRqstuv/(FGH)=.,STYZab^]WXi}"
			break;
		case 23:
		case "23":
			possible = "Aw27x\|@C5D#yz0!·$%B3i4jWclmJNn1op;KL:dMefH)=g}X{hkE-_Ç*ItuOPrsQ8Rqv/(6FG.,SYZab^[TU9V]"
			break;
		}
		var text = "";
		for (var i = 0; i < length; i++)
			text += possible.charAt(Math.floor(Math.random() * possible.length));

		return text;
	};
	
	// FUNC:: INNER - CONTEXT MENU [EVENT SHOW]
	(function ($, window) {
	    $.fn.contextMenu = function (settings)
	    {
	        return this.each(function () {
	            // Open context menu
	            
	            $(this).on("contextmenu", function (e) {
	                // return native menu if pressing control
	                if (e.ctrlKey) return;
	                
	                //open menu
	                var $menu = $(settings.menuSelector)
	                    .data("invokedOn", $(e.target))
	                    .show()
	                    .css({
	                        position: "absolute",
	                        left: getMenuPosition(e.clientX, 'width', 'scrollLeft'),
	                        top: getMenuPosition(e.clientY, 'height', 'scrollTop')
	                    })
	                    .off('click')
	                    .on('click', 'button', function (e) {
	                        $menu.hide();
	                
	                        var $invokedOn = $menu.data("invokedOn");
	                        var $selectedMenu = $(e.target);
	                        
	                        settings.menuSelected.call(this, $invokedOn, $selectedMenu);
	                    });
	                
	                return false;
	            });

	            $('body').click(function () {
	                $(settings.menuSelector).hide();
	            });
	        });
	        
	        function getMenuPosition(mouse, direction, scrollDir) {
	            var win = $(window)[direction](),
	                scroll = $(window)[scrollDir](),
	                menu = $(settings.menuSelector)[direction](),
	                position = mouse + scroll;
	                        
	            if (mouse + menu > win && menu < mouse) 
	                position -= menu;
	            
	            return position;
	        }    

	    };
	})(jQuery, window);
	
	$('document').ready(function(){
		$("#list-tab").contextMenu({
		    menuSelector: "#contextMenu",
		    menuSelected: function (invokedOn, selectedMenu) {
		    	
		        var clazz = invokedOn.attr("class");
		        var activeClazz = clazz + " active";
		      	var items = document.getElementsByClassName(activeClazz);
		      	for (var i = 0 ; i < items.length ; i++)
	    		{
					items[i].className = clazz;
				}
		      	invokedOn.attr("class", activeClazz); 
		      	
		        switch(selectedMenu.text())
		        {
			        case "Add directory":
			        	createDir()
			        	break;
			        case "Add account":
			        	createAccnt()
			        	break;
			        case "Edit item":
			        	edit();
			        	break;
		        	case "Move item":
						move("Home");
		        		break;
		        	case "Share":
		        		var idString = invokedOn.attr("id");
		        		var id = idString.split("-")[2];
		        		var type = idString.split("-")[1];
		        		
		        		if (type != null && type == "accnt")
		        		{
		        			
		        			var form = document.getElementById("formShare");
		        			var input = document.createElement("input");
							input.setAttribute("type","hidden");
							input.setAttribute("name","idAccnt");
							input.value = id;							
							form.appendChild(input);
							
		        			$("#shareModal").modal("show");
		        			
		        		}
		        		else
		        		{
		        			alert("Directories can not be shared, this option is just for accounts");
		        		}
		       			break;
		        	case "Delete":
		        		$("#deleteSelectedModal").modal("show");
		        		break;
		        }
		    }
		});
	});
	
	// FUNC:: INNER - CONTEXT MENU [EVENT SHOW]
	
	
	
	
	// FUNC:: INNER - SLEEP
	function sleep() {
		return new Promise(resolve => setTimeout(resolve, 300));
	};
	
	// --------------------
	// ------ EVENTS ------
	// --------------------
	
	$('#deleteSelectedModal').on('show.bs.modal', function (e) {
		var items = document.getElementsByClassName("list-group-item active");
		if (items[0] != null)
		{
			var type = items[0].id.split("-")[1] == "accnt" ? "account" : "directory";
			document.getElementById("deleteText").innerHTML = 
				"Are you sure you want to delete this " + type + " ?";
		}
		else
		{
			alert("You must select one item from the list");	
		}
	})
	
	</script>
</body>