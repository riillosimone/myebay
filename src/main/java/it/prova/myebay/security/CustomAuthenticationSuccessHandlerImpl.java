package it.prova.myebay.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import it.prova.myebay.dto.UtenteDTO;
import it.prova.myebay.model.Utente;
import it.prova.myebay.repository.utente.UtenteRepository;

@Component
public class CustomAuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

	@Autowired
	private UtenteRepository utenteRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// voglio mettere in sessione uno userInfo perché spring security mette solo un
		// principal da cui attingere username
		Utente utenteFromDb = utenteRepository.findByUsername(authentication.getName()).orElseThrow(
				() -> new UsernameNotFoundException("Username " + authentication.getName() + " not found"));
		UtenteDTO utenteParziale = new UtenteDTO();

		utenteParziale.setNome(utenteFromDb.getNome());
		utenteParziale.setCognome(utenteFromDb.getCognome());
		utenteParziale.setUsername(utenteFromDb.getUsername());
		request.getSession().setAttribute("userInfo", utenteParziale);
//		
		//dopo utente parziale
		SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		if (savedRequest != null) {
			String savedRequestUrl = savedRequest.getRedirectUrl();
			response.sendRedirect(savedRequestUrl);
			return;
		}
//			SavedRequest savedRequest = new DefaultSavedRequest(request, new PortResolverImpl());
//
//	        request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST", savedRequest);
//	        String savedRequestUrl = savedRequest.getRedirectUrl();
//			response.sendRedirect(savedRequestUrl);
//			return;
//			response.sendRedirect(request.getHeader("referer"));
//			response.sendRedirect(HttpHeaders.REFERER);

		
		response.sendRedirect("secured/home");

	}

}
