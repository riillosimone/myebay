<!doctype html>
<html lang="it">
	<head>
	  <meta charset="utf-8">
		<title>Accedi</title>
	
		<!-- Common imports in pages -->
	 	<jsp:include page="./header.jsp" />
	

	
		 <!-- Custom styles for login -->
	    <link href="assets/css/signin.css" rel="stylesheet">
	</head>
	
	<body class="text-center">
		<main class="form-signin">
			<form class="form-signin" name='login' action="login" method='POST' novalidate="novalidate">
		   	
			   	<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none': ''}" role="alert">
				  ${errorMessage}
				</div>
				
				<div class="alert alert-info alert-dismissible fade show ${infoMessage==null?'d-none': ''}" role="alert">
				  ${infoMessage}
				</div>
				
				
<script src="https://unpkg.com/@lottiefiles/lottie-player@latest/dist/lottie-player.js"></script>
<lottie-player src="https://assets8.lottiefiles.com/private_files/lf30_iraugwwv.json"  background="transparent" speed="1"  style="width: 17em; height: 17em;"  loop autoplay></lottie-player>

				<h1 class="h3 mb-3 fw-normal">Please sign in</h1>
		    	
		    	
			  	<div class="form-floating">
			      <input type="text" name="username" class="form-control" id="inputUsername" placeholder="username">
			      <label for="inputUsername">Username</label>
			    </div>
			    <div class="form-floating">
			      <input type="password" name="password" class="form-control" id="inputPassword" placeholder="Password">
			      <label for="inputPassword">Password</label>
			    </div>
			
			    <div class="checkbox mb-3">
			      <label>
			        <input type="checkbox" value="remember-me"> Remember me
			      </label>
			    </div>
			    
			    <button class="w-50 btn btn-lg btn-primary" type="submit">Sign in</button>
			    <a class="w-45 btn btn-outline-primary btn-lg" href="${pageContext.request.contextPath}/signup/registrati">Sign up</a>
			    </form>
			    <input type='hidden' name='redirect_to' value='"${header.referer}"'>
			    <a class="btn btn-outline-secondary btn-md" href="${pageContext.request.contextPath}/public/home">Torna a MyEbay</a>
			    <p class="mt-5 mb-3 text-muted">&copy; 2017-2021</p>
			  
			  
			  
			
		</main>
	</body>
</html>