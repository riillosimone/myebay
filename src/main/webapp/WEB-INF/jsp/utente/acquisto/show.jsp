<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100">
<head>

<!-- Common imports in pages -->
<jsp:include page="../header.jsp" />

<title>Visualizza Elemento</title>

</head>
<body class="d-flex flex-column h-100">

	 <sec:authorize access="isAuthenticated()" var="isAutenticato"></sec:authorize>
<c:choose>
   <c:when test="${isAutenticato}"><jsp:include page="../navbar.jsp"></jsp:include></c:when>
   <c:otherwise><jsp:include page="./navbar.jsp"></jsp:include>
	</c:otherwise>
</c:choose>

	<!-- Begin page content -->
	<main class="flex-shrink-0">
		<div class="container">

<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
			  ${errorMessage}
			  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
			</div>
			<div class='card'>
				<div class='card-header'>
					<h5>Visualizza dettaglio</h5>
				</div>


				<div class='card-body'>

					<dl class="row">
						<dt class="col-sm-3 text-right">Descrizione:</dt>
						<dd class="col-sm-9">${show_acquisto_attr.descrizione}</dd>
					</dl>

					<dl class="row">
						<dt class="col-sm-3 text-right">Prezzo:</dt>
						<dd class="col-sm-9">${show_acquisto_attr.prezzo}</dd>
					</dl>

					
					<dl class="row">
						<dt class="col-sm-3 text-right">Data Acquisto:</dt>
						<dd class="col-sm-9">
							<fmt:parseDate value="${show_acquisto_attr.dataAcquisto}"
								pattern="yyyy-MM-dd" var="localDateToBeParsed" type="date" />
							<fmt:formatDate pattern="dd/MM/yyyy"
								value="${localDateToBeParsed}" />
						</dd>
					</dl>

					

				</div>

				<div class='card-footer'>
					 <sec:authentication property="principal.username" var="utenteInPagina"/>
					
					<a
						href="${pageContext.request.contextPath}/acquisto/listaacquisti"
						class='btn btn-outline-secondary' style='width: 80px;'> <i
						class='fa fa-chevron-left'></i> Back
					</a>
				</div>
				<!-- end card -->
			</div>


			<!-- end container -->
		</div>

	</main>

	<!-- Footer -->
	<jsp:include page="../footer.jsp" />
</body>
</html>