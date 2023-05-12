<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!doctype html>
<html lang="it" class="h-100" >
<head>
	<jsp:include page="../header.jsp" />
	<title>Ricerca</title>
	
    
</head>
<body class="d-flex flex-column h-100">
	<!-- Fixed navbar -->
	<jsp:include page="../navbar.jsp"></jsp:include>
	
	<!-- Begin page content -->
	<main class="flex-shrink-0">
	  <div class="container">
	
			<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': ''}" role="alert">
			  ${errorMessage}
			  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
			    <span aria-hidden="true">&times;</span>
			  </button>
			</div>
			
			
			
			<div class='card'>
			    <div class='card-header'>
			        <h5>Ricerca elementi</h5> 
			    </div>
			    <div class='card-body'>
	
						<form:form modelAttribute="search_annuncio_attr" method="post" action="${pageContext.request.contextPath}/annuncio/list" class="row g-3">
						
							<div class="col-md-6">
								<label for="testoAnnuncio" class="form-label">Testo Annuncio</label>
								<input type="text" name="testoAnnuncio" id="testoAnnuncio" class="form-control" placeholder="Inserire il testo dell'annuncio" >
							</div>
							
							<div class="col-md-6">
								<label for="prezzo" class="form-label">Prezzo</label>
								<input type="number" name="prezzo" id="prezzo" class="form-control" placeholder="Inserire il prezzo" >
							</div>
							
							
							
							<div class="col-md-6 form-check" id="categorieDivId">
									<p>Categorie:</p>
									<form:checkboxes itemValue="id" itemLabel="descrizione"  element="div class='form-check'" items="${categorie_totali_attr}" path="categorieIds" />
								</div>
								<script>
									$(document).ready(function(){
										
										$("#categorieDivId :input").each(function () {
											$(this).addClass('form-check-input'); 
										});
										$("#categorieDivId label").each(function () {
											$(this).addClass('form-check-label'); 
										});
										
									});
								</script>
								<%-- fine checkbox ruoli 	--%>
							
							
							
							
							<div class="col-12">	
								<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
								<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
							</div>
	
							
						</form:form>
			    
				<!-- end card-body -->			   
			    </div>
			</div>	
	
		</div>
	<!-- end container -->	
	</main>
	<jsp:include page="../footer.jsp" />
	
</body>
</html>