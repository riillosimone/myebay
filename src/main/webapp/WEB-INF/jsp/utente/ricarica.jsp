<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	 	 <style>
		    .error_field {
		        color: red; 
		    }
		</style>
	   
	   <title>Ricarica Credito</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<%-- se l'attributo in request ha errori --%>
						<%-- alert errori --%>
						
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Ricarica Credito</h5> 
				    </div>
				    <div class='card-body'>
		
							<dl class="row">
						<dt class="col-sm-3 text-right">Credito Residuo:</dt>
						<dd class="col-sm-9">${credito_utente_attr.creditoResiduo}</dd>
					</dl>
		
							<form method="post" action="${pageContext.request.contextPath}/admin/ricarica" novalidate="novalidate" class="row g-3">
							
								<div class="col-md-6">
									<label for="creditoDaRicaricare" class="form-label">Ricarica Credito</label>
										<input type="number" name="creditoDaRicaricare" id="creditoDaRicaricare"  placeholder="Inserire credito da ricaricare"  required>
								</div>
								
								 
								
								
								
								
								<div class="col-12">
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
									<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
									<a class="btn btn-outline-secondary ml-2" href="${pageContext.request.contextPath }/secured/home">Back</a>
								</div>
		
						</form>
  
				    
				    
					<!-- end card-body -->			   
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