<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header>
  <!-- Fixed navbar -->
 <nav class="navbar navbar-expand-lg navbar-dark bg-primary" aria-label="Eighth navbar example">
    <div class="container">
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarsExample07" aria-controls="navbarsExample07" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExample07">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <sec:authorize access="isAuthenticated()" var="isAutenticato"></sec:authorize>
      <c:choose>
   		<c:when test="${isAutenticato}"><li class="nav-item">
          
            <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/secured/home">Home</a>
          </li></c:when>
		   <c:otherwise><li class="nav-item">
          
            <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/public/home">Home</a>
          </li>
			</c:otherwise>
		</c:choose>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown07" data-bs-toggle="dropdown" aria-expanded="false">Dropdown</a>
            <ul class="dropdown-menu" aria-labelledby="dropdown07">
            <sec:authentication property="principal.username" var="utenteInPagina"/>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/secured/home">Home</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/utente/ricarica">Ricarica credito</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/annuncio/listaannunci">Gestione annunci</a></li>
              <li><a class="dropdown-item" href="${pageContext.request.contextPath}/acquisto/listaacquisti">I tuoi acquisti</a></li>
            </ul> 
          </li>
           <sec:authorize access="hasRole('ADMIN')">
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="dropdown01" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Gestione Utenze</a>
		        <div class="dropdown-menu" aria-labelledby="dropdown01">
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/search">Ricerca Utenti</a>
		          <a class="dropdown-item" href="${pageContext.request.contextPath}/admin/insert">Inserisci Utente</a>
		        </div>
		      </li>
		   </sec:authorize>
        </ul>
      </div>
      <sec:authorize access="isAuthenticated()" var="isAutenticato"></sec:authorize>
      <c:choose>
   		<c:when test="${isAutenticato}"><div class="col-md text-end">   
	         <p class="navbar-text">Utente: ${userInfo.username }(${userInfo.nome } ${userInfo.cognome })
    	 <a href="${pageContext.request.contextPath}/executeLogout">Logout</a></p>
    	 </div></c:when>
		   <c:otherwise><div class="col-md text-end">  <h6 class="navbar-text">
		    	 <a href="${pageContext.request.contextPath}/login">Login</a></h6>
		    	 </div>
			</c:otherwise>
		</c:choose>
    </div>
  </nav>
  
  
</header>
